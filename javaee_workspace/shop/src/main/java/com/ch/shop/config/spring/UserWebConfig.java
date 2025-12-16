package com.ch.shop.config.spring;

import javax.naming.NamingException;
import javax.sql.DataSource;

import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
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

@ComponentScan(basePackages = {"com.ch.shop.controller", "com.ch.shop.model"})
// 해당 컨트롤러에 @Controller 어노테이션이 선언되어 있으면 컴포넌트스캔으로 Bean을 자동생성
public class UserWebConfig extends WebMvcConfigurerAdapter{
	
	// DispatcherServlet 이 하위 컨트롤러부터 반환받은 View형태의 결과페이지에 대한 정보("board/list")는 완전한 jsp 경로("WEB-INF/views/board/list.jsp)가 아니므로,
	// 이를 해석할 수 있는 ViewResolver 에게 맡겨야 한다. 이 ViewResolver 중 접두어와 접미어를 이해하는 ViewResolver 를 InternalResourceViewResolver 라고 한다.
	// 개발자는 이 객체에게 접두어와 접미어를 사전에 등록해 놓아야 한다.(지금의 경우, /WEB-INF/views 와 .jsp)
	
	@Bean
	public InternalResourceViewResolver viewResolver() {
		InternalResourceViewResolver irvr = new InternalResourceViewResolver();
		// 접두어 등록
		irvr.setPrefix("/WEB-INF/views/");
		
		// 접미어 등록
		irvr.setSuffix(".jsp");
		return irvr;
	}
	
	// 스프링이 MVC 프레임웤중 컨트롤러 영역만을 지원하는 것이 아니라 DB 관련 제어도 지원하므로, 
	// 기존에 사용해왔던 mybatis를 스프링이 지원하는 mybatis 로 전환하자.
	// 먼저 스프링이 지원하려는 DB 연동 기술을 사용하려면, spring jdbc 라이브러리를 추가해야 한다.
	
	
	// 1) 개발자가 사용하고 싶은 데이터소스를 결정. - 톰캣이 지원하는 JNDI 를 사용한다.
	
	public DataSource dataSource() throws NamingException {
		JndiTemplate jndi = new JndiTemplate();
		return jndi.lookup("java:comp/env/jndi/mysql", DataSource.class);
	}
	
	
	// 2) 트랜잭션 매니저 등록. - 스프링은 개발자가 사용하는 기술이 JDBC 이건, Mybatis 이건, Hibernate 이건, JPA 이건 상관없이
	//									  일관된 방법으로 트랜잭션을 처리할 수 있는 방법을 제공해주는데, 개발자는 자신이 사용하는 기술에 따라 적절한 트랜잭션 매니저를 등록해야 한다.
	//									  예) JDBC 사용 시 - DataSourceTransactionManager 를 Bean 으로 등록.
	//									  예) Hibernate 사용 시 - HibernateTransactionManager 를 Bean 으로 등록.
	//									  예) Mybatis 사용 시 - DataSourceTransactionManager 를 Bean 으로 등록.
	//									  어? 왜 Mybatis 는 JDBC랑 똑같지? > 사실 Mybatis 는 내부적으로 JDBC 를 사용해왔다는 개꿀잼몰카였던거임
	// 									  이 모든 트랜잭션 매니저의 최상단 객체가 바로 PlatformTransactionManager 이다

	@Bean
	public PlatformTransactionManager transactionManager(SqlSessionFactory sqlSessionFactory) {
		return new DataSourceTransactionManager(sqlSessionFactory.getConfiguration().getEnvironment().getDataSource());
	}
	
	
	// 3) SqlSession 을 관리하는 mybatis 의 SqlSessionFactory 를 빈으로 등록
	
	@Bean
	public SqlSessionFactory sqlSessionFactory() throws Exception {
		
		// 순수 mybatis 프레임웤 자체에서 지원하는 객체가 아닌, mybatis-spring 에서 지원하는 객체인
		// SqlSessionFactoryBean (끝에 Bean이 다름) 을 이용하여 설정 xml 파일을 로드한다
		
		SqlSessionFactoryBean sqlSessionFactoryBean = new SqlSessionFactoryBean();
		sqlSessionFactoryBean.setConfigLocation(new ClassPathResource("com/ch/shop/config/mybatis/config.xml"));
		// 패키지에 포함된 파일의 유형이 클래스가 아닌 경우(xml 같은) 패키지로 표현하지 말고 일반 디렉토리로 취급해야 하므로 경로가 . 이 아닌 / 로 표현.
		
		sqlSessionFactoryBean.setDataSource(dataSource());
		return sqlSessionFactoryBean.getObject();
	}
	
	
	// 4) SqlssesionTemplate 빈 등록 - 기존 mybatis 사용 시에는 쿼리문 수행을 위해 SqlSession 을 이용했으나, mybatis-spring 에서는 SqlSessionTemplate 객체를 사용한다.
	
	@Bean
	public SqlSessionTemplate sqlSessionTemplate() throws Exception {
		return new SqlSessionTemplate(sqlSessionFactory());
	}
	
	// 스프링 프레임웤의 개발원리 중 하나인 DI 를 구현하려면, 개발자는 사용할 객체들을 미리 Bean 으로 등록해놓아야 한다.
	
	
	// DispatcherServlet 은 컨트롤러에 대한 매핑만 수행해야하며 정적자원(html, css, js, imagage 등) 에 대해서는 직접 처리하면 안 된다.(스프링이 원래 그렇다)
	@Override
	public void addResourceHandlers(ResourceHandlerRegistry registry) {
		// registry.addResourceHandler("브라우저로 접근할 주소").addResourceLocations("웹애플리케이션을 기준으로 실제 정적 자원이 있는 위치");
		registry.addResourceHandler("/static/**").addResourceLocations("/resources/");
		
		// 이렇게 해놓으면 DispatcherServlet 이 정적자원은 안 맡는다.
	}
	
}




