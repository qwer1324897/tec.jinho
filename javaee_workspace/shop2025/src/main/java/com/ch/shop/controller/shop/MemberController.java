package com.ch.shop.controller.shop;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.client.RestTemplate;

import com.ch.shop.dto.GoogleUser;
import com.ch.shop.dto.OAuthClient;
import com.ch.shop.dto.OAuthTokenResponse;
import com.ch.shop.model.topcategory.TopCategoryService;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Controller
public class MemberController {

	@Autowired
	private TopCategoryService topCategoryService;
	
	@Autowired
	private Map<String, OAuthClient> oauthClients;
	
	@Autowired
	private RestTemplate restTemplate;
	
	// 회원 로그인 폼 요청 처리
	@GetMapping("/member/loginform")
	public String getLoginForm(Model model) {
		
		List topList = topCategoryService.getList();	// 3단계: 일 시키기
		// 4단계: 결과 페이지로 가져갈 것이 있다 > 저장
		model.addAttribute("topList", topList);
		
		return "shop/member/login";
	}
	
	// sns 로그인을 희망하는 유저들의 로그인 인증 요청 url 주소를 알려주는 컨트롤러 메서드
	@GetMapping("/oauth2/authorize/{provider}")
	@ResponseBody	// 이 애노테이션을 설정해야 DispatcherServlet 이 jsp 와의 매핑을 시도하지 않고 반환값 그대로를 응답정보로 보낸다.
	public String getAuthUrl(@PathVariable("provider") String provider) throws Exception {
		// @PathVariable("provider") - url 의 일부를 파라미터화 시키는 애노테이션. REST API 에 적용됨
		OAuthClient oAuthClient = oauthClients.get(provider);
		
		log.debug(provider + "의 로그인 요청 url은 " +  oAuthClient.getAuthorizeUrl());
		
		// 이 주소를 이용하여 브라우저 사용자는 프로바이더에게 로그인을 요청해야 하는데, 이 때 요청 파라미터를 갖춰야
		// 로그인 절차에 성공한다. 요청 시 지참할 파라미터에는 clientId, callback url, scope 등..
		
		StringBuffer stringBuffer = new StringBuffer();
		stringBuffer.append(oAuthClient.getAuthorizeUrl()).append("?")
		.append("response_type=code")	// 이 요청에 의해 인가 code 를 받을 것임을 알린다.
		.append("&client_id=").append(urlEncode(oAuthClient.getClientId()))
		.append("&redirect_uri=").append(urlEncode(oAuthClient.getRedirectUri()))
		.append("&scope=").append(urlEncode(oAuthClient.getScope()));
		
		return stringBuffer.toString();
	}
	
	// GET 방식같이 웹을 통해 파라미터 전송 시 문자열이 깨지지 않도록 인코딩 처리해주는 메서드
	private String urlEncode(String s) throws Exception {
		return URLEncoder.encode(s, "UTF-8");
	}
	
	// 클라이언트가 동의하면(최초사용자) 또는 로그인(기존) 요청이 들어오게 되고, Provider 가 이를 처리하는 과정에서
	// 개발자가 등록해놓은 callback 주소로 임시코드(Authorize Code)  를 발급한다.
	@GetMapping("/login/callback/{provider}")
	public String handleCallback(
			@PathVariable("provider") String provider,
			@RequestParam("code") String code
			) {
		// 구글이 보내온 인증 코드와, 나의 client id, client Secret 을 조합하여, token 을 요청하자
		// 결국 개발자가 원하는 것은 사용자의 정보이므로, 이 정보를 얻기 위해서는 토큰이 필요하다.

		log.debug(provider + "가 발급한 임시 코드는 " + code);
		
		OAuthClient google = oauthClients.get(provider);
		
		// 구글로부터 받은 임시코드와 나의 정보(client id, client secret) 을 조합하여 구글에게 보내자(토큰 받아야 하니까)
		// 이 때, 구글과 같은 프로바이더와 데이터를 주고받기 위해선 Http 통신 규격을 지켜서, 말을 걸 때는 머리, 몸을 구성하여 요청을 시도해야 한다.
		MultiValueMap<String, String> param = new LinkedMultiValueMap<String, String>();	// 몸체
		param.add("grant_type", "authorization_code");	// 임시코드를 이용하여 토큰을 요청하겠다는 것을 명시
		param.add("code", code); 	// 구글로부터 발급받은 임시코드를 그대로 추가
		param.add("client_id", google.getClientId());		// 클라이언트 아이디 추가
		param.add("client_secret", google.getClientSecret());		// 클라이언트 비밀번호 추가
		param.add("redirect_uri", google.getRedirectUri());		// 클라이언트 uri 추가
		
		HttpHeaders headers = new HttpHeaders();		// 머리
		// 아래와 같이 전송 파라미터에 대한 contentType 을 명시하면, key=value&key 방식의 데이터쌍으로 자동으로 변환
		headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
		
		// 머리와 몸(본문) 을 합쳐서 하나의 Http 요청 엔터티로 결합
		HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<MultiValueMap<String,String>>(param, headers);
		
		// 구글에 요청 시작. 스프링에서는 Http 요청 후 그 응답 정보를 java 객체와 자동으로 매핑해주는 편리한 객체를 지원해주는데,
		// 그 객체가 바로 RestTemplate (http 요청 능력 + jack 능력)
		
		ResponseEntity<OAuthTokenResponse> response = restTemplate.postForEntity(google.getTokenUrl(), request, OAuthTokenResponse.class);
		log.debug("구글로부터 받은 응답 정보는 " + response.getBody());
		
		// 얻어진 토큰으로 구글에 회원정보를 요청해보기
		OAuthTokenResponse responseBody = response.getBody();
		String access_token = responseBody.getAccess_token();
		
		log.debug("구글로부터 받은 엑세스 토큰은 " + access_token);
		
		// 회원정보 가져오기
		// 구글에 요청을 시도하려면 역시나 이번에도 Http 프로토콜의 형식을 갖춰야 함.
		HttpHeaders userInfoHeaders = new HttpHeaders();
		// 내가 바로 토큰을 가진 자임을 알리는 헤더 속성값을 넣어야 함
		userInfoHeaders.add("Authorization", "Bearer "+access_token);
		HttpEntity<String> userInfoRequest = new HttpEntity<>("", userInfoHeaders);
		
		ResponseEntity<GoogleUser> userInfoResponse = restTemplate.exchange(google.getUserInfoUrl(), HttpMethod.GET, userInfoRequest, GoogleUser.class);		// 서버로부터 데이터를 가져와야 하므로 exchange 메서드를 사용한다.
		
		log.debug("사용자 정보는 " + userInfoResponse);
		
		/*----------------------------------------------------
		 얻어진 유저 정보를 이용하여 할 일
		 
		 1) 얻어진 회원이 우리의 mysql 에 존재하는 따져서 있다면? 로그인 세션만 부여하고 홈페이지 메인으로 보내면 되고,
		 	없다면? member 테이블에 insert 하고 세션 부여한 다음 홈페이지 메인으로 보내면 된다.
		 	추강ㄴㄻㄴ
		 ------------------------------------------------------*/
		
		return null;
	}
	
	
}
