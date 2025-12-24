package com.ch.shop.config.spring;

import java.util.HashMap;
import java.util.Map;

import javax.naming.NamingException;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.jndi.JndiTemplate;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

import com.ch.shop.dto.OAuthClient;


// 이 클래스는 로직을 작성하기 위함이 아니라, 애플리케이션에서 사용할 Bean(객체)들 과 그 관계들(weaving)을 명시하기 위한 설정 목적의 클래스.
// 실제 쇼핑몰의 일반 유저들이 보게 되는 애플리케이션 쪽 빈들을 관리한다.

@Configuration    // 이 어노테이션을 붙이는 것이 곧 설정용 클래스다 라는 선언.
@EnableWebMvc    // 필수 설정. 스프링이 지원하는 MVC 프레임웤을 사용하기 위한 어노테이션

// 일일이 빈으로 등록할 필요가 없는 많이 알려진 빈들을 가리켜 스프링에서는 컴포넌트라 부른다.
// 또한 이 컴포넌트들은 패키지 위치만 설정 해 놓으면 스프링이 알아서 찾아내서 (검색) 인스턴스를 자동으로 만들어 준다.

// MVC 에서의 Controller 는 @Controller 를 붙이면 되고, MVC 에서의 DAO 는 @Repository 를 붙이면 된다.
// MVC 에서의 DAO 는 @Service 를 붙인다  => 이러면 자동으로 메모리에 올려줌.
// MVC 에서의 특정 분류가 딱히 없음에도 자동으로 메모리에 올리고 싶다면, @Component 를 붙이면 된다.

@ComponentScan(basePackages = {"com.ch.shop.controller.shop"})    // shop 컴포넌트가 연결되어있기 때문에 하단에 코드가 없어도 의미가 있는 클래스이다.
// 해당 컨트롤러에 @Controller 어노테이션이 선언되어 있으면 컴포넌트스캔으로 Bean을 자동생성
public class ShopWebConfig extends WebMvcConfigurerAdapter{

	// 정적 자원(CSS/JS/이미지) 매핑: /static/** -> /resources/
	@Override
	public void addResourceHandlers(ResourceHandlerRegistry registry) {
		registry.addResourceHandler("/static/**").addResourceLocations("/resources/");
		registry.addResourceHandler("/photo/**").addResourceLocations("file:/C:/shopdata/product/");
	}
	
	@Bean
	public RestTemplate restTemplate() {
		return new RestTemplate();
	}
	
	@Bean
	public JndiTemplate jndiTemplate() {
		// context.xml 등에 명시된 외부 자원을 JNDI 방식으로 읽어들일 수 있는 스프링의 객체.(중요)
		return new JndiTemplate();
	}
	
	/*-------------------------------------------
	 Google
	 --------------------------------------------*/
	@Bean
	public String googleClientId(JndiTemplate jndiTemplate) throws NamingException {
		return (String)jndiTemplate.lookup("java:comp/env/google/client/id");		// java:comp/env~
	}
	
	@Bean
	public String googleClientSecret(JndiTemplate jndiTemplate) throws NamingException {
		return (String)jndiTemplate.lookup("java:comp/env/google/client/secret");
	}

	// OAuth 로그인 시 사용되는 환경 변수(요청주소, 콜백주소 등) 는 객체로 담아서 관리하면 유지보수에 용이하다.
	// 지금의 경우, 구글 네이버 카카오, 여러 Provider 를 연동할 것이므로, OAuthClient 객체 여러개를 메모리에 보관하자.
	@Bean
	public Map<String, OAuthClient> oauthClients(
			@Qualifier("googleClientId") String googleClientId,
			@Qualifier("googleClientSecret") String googleClientSecret
			) {
		
		// 구글, 네이버, 카카오를 각각 OAuthClient 인스턴스에 담은 후 다시 Map 에 모아두자
		Map<String, OAuthClient> map = new HashMap<>();
		
		// 구글 등록
		OAuthClient google = new OAuthClient();
		google.setProvider("google");
		google.setClientId(googleClientId);
		google.setClientSecret(googleClientSecret);
		google.setAuthorizeUrl("https://accounts.google.com/o/oauth2/v2/auth"); 	// google api 문서에 나와있음
		google.setTokenUrl("https://oauth2.googleapis.com/token"); 	// 토큰을 요청할 주소
		google.setUserInfoUrl("https://openidconnect.googleapis.com/v1/userinfo");
		google.setScope("openid email profile"); 	// 사용자에 대한 정보의 접근 범위
		google.setRedirectUri("http://localhost:8888/login/callback/google");
		
		map.put("google", google);
		
		// 네이버 등록
		
		
		// 카카오 등록
		
		
		return map;
	}
	
}




