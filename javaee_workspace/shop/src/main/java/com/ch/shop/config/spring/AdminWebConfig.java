package com.ch.shop.config.spring;

import java.util.List;

import javax.naming.NamingException;
import javax.sql.DataSource;

import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.jndi.JndiTemplate;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import org.springframework.web.servlet.view.InternalResourceViewResolver;

// 이 클래스는 로직을 작성하기 위함이 아니라, 애플리케이션에서 사용할 Bean(객체)들 과 그 관계들(weaving)을 명시하기 위한 설정 목적의 클래스.
// 실제 쇼핑몰의 일반 유저들이 보게 되는 애플리케이션 쪽 빈들을 관리한다.

@Configuration    // 이 어노테이션을 붙이는 것이 곧 설정용 클래스다 라는 선언.
@EnableWebMvc    // 필수 설정. 스프링이 지원하는 MVC 프레임웤을 사용하기 위한 어노테이션

// 일일이 빈으로 등록할 필요가 없는 많이 알려진 빈들을 가리켜 스프링에서는 컴포넌트라 부른다.
// 또한 이 컴포넌트들은 패키지 위치만 설정 해 놓으면 스프링이 알아서 찾아내서 (검색) 인스턴스를 자동으로 만들어 준다.

// MVC 에서의 Controller 는 @Controller 를 붙이면 되고, MVC 에서의 DAO 는 @Repository 를 붙이면 된다.
// MVC 에서의 DAO 는 @Service 를 붙인다  => 이러면 자동으로 메모리에 올려줌.
// MVC 에서의 특정 분류가 딱히 없음에도 자동으로 메모리에 올리고 싶다면, @Component 를 붙이면 된다.

@ComponentScan(basePackages = {"com.ch.shop.controller.admin"})
// 해당 컨트롤러에 @Controller 어노테이션이 선언되어 있으면 컴포넌트스캔으로 Bean을 자동생성
public class AdminWebConfig extends WebMvcConfigurerAdapter{
	
	// DispatcherServlet 이 하위 컨트롤러부터 반환받은 View형태의 결과페이지에 대한 정보("board/list")는 완전한 jsp 경로("WEB-INF/views/board/list.jsp)가 아니므로,
	// 이를 해석할 수 있는 ViewResolver 에게 맡겨야 한다. 이 ViewResolver 중 접두어와 접미어를 이해하는 ViewResolver 를 InternalResourceViewResolver 라고 한다.
	// 개발자는 이 객체에게 접두어와 접미어를 사전에 등록해 놓아야 한다.(지금의 경우, /WEB-INF/views 와 .jsp)
	
	@Bean
	public InternalResourceViewResolver viewResolver() {	// 페이지에 대한 매핑
		InternalResourceViewResolver irvr = new InternalResourceViewResolver();
		// 접두어 등록
		irvr.setPrefix("/WEB-INF/views/");
		
		// 접미어 등록
		irvr.setSuffix(".jsp");
		return irvr;
	}
	
	// DispatcherServlet 은 컨트롤러에 대한 매핑만 수행해야하며 정적자원(html, css, js, imagage 등) 에 대해서는 직접 처리하면 안 된다.(스프링이 원래 그렇다)
	@Override
	public void addResourceHandlers(ResourceHandlerRegistry registry) {
		// registry.addResourceHandler("브라우저로 접근할 주소").addResourceLocations("웹애플리케이션을 기준으로 실제 정적 자원이 있는 위치");
		registry.addResourceHandler("/static/**").addResourceLocations("/resources/");
		
		// 이렇게 해놓으면 DispatcherServlet 이 정적자원은 안 맡는다.
	}
	
	// Jackson 라이브러리 사용을 설정
	
	@Override
	public void configureMessageConverters(List<HttpMessageConverter<?>> converters) {
		converters.add(new MappingJackson2HttpMessageConverter());	// Jackson 객체 넣기
	}
	
}




