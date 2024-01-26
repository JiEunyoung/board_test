package kr.co.softsoldesk.config;

import javax.annotation.Resource;

import org.apache.commons.dbcp2.BasicDataSource;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.mapper.MapperFactoryBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.beans.factory.config.PropertyPlaceholderConfigurer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.InterceptorRegistration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.ViewResolverRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import kr.co.softsoldesk.beans.UserBean;
import kr.co.softsoldesk.interceptor.CheckLoginInterceptor;
import kr.co.softsoldesk.interceptor.TopMenuInterceptor;
import kr.co.softsoldesk.mapper.TopMenuMapper;
import kr.co.softsoldesk.mapper.UserMapper;
import kr.co.softsoldesk.service.TopMenuService;


@Configuration
@EnableWebMvc //스캔한 패키지 내부의 클래스 중 Controller 어노테이션을 가지고 있는 클래스들을 Controller로 등록
@ComponentScan("kr.co.softsoldesk.controller")
@ComponentScan("kr.co.softsoldesk.dao")
@ComponentScan("kr.co.softsoldesk.service")
@PropertySource("/WEB-INF/properties/db.properties")
public class ServletAppContext implements WebMvcConfigurer{
//Spring MVC 프로젝트에 관련된 설정을 하는 클래스
	
	@Value("${db.classname}")
	   private String db_classname;
	   
	   @Value("${db.url}")
	   private String db_url;
	   
	   @Value("${db.username}")
	   private String db_username;
	   
	   @Value("${db.password}")
	   private String db_password;
	   
	   @Autowired
	   private TopMenuService topMenuService;
	   
	   @Resource(name = "loginUserBean")
	   private UserBean loginUserBean;
	
	@Override
	public void addResourceHandlers(ResourceHandlerRegistry registry) {
	//정적 파일 경로 매핑
		WebMvcConfigurer.super.addResourceHandlers(registry);
		registry.addResourceHandler("/**").addResourceLocations("/resources/");
	}

	@Override
	public void configureViewResolvers(ViewResolverRegistry registry) {
	//Controller의 메서드가 반환하는 파일 앞뒤에 경로의 확장지 추가
		WebMvcConfigurer.super.configureViewResolvers(registry);
		registry.jsp("/WEB-INF/views/",".jsp");
	}

	@Bean
	public BasicDataSource dataSource() {
		BasicDataSource source = new BasicDataSource();
		source.setDriverClassName(db_classname);
		source.setUrl(db_url);
		source.setUsername(db_username);
		source.setPassword(db_password);
		
		return source;
	}
	
	@Bean
	public SqlSessionFactory factory(BasicDataSource source) throws Exception{
		
		SqlSessionFactoryBean factoryBean = new SqlSessionFactoryBean();
		factoryBean.setDataSource(source);
		SqlSessionFactory factory = factoryBean.getObject();
		
		return factory;
	}
	
	@Bean // 다른 Mapper <> 이 부분만 바꿔서 생성해주면 됨
	public MapperFactoryBean<TopMenuMapper> getTopMenuMapper(SqlSessionFactory factory) throws Exception{
		MapperFactoryBean<TopMenuMapper> factoryBean = new MapperFactoryBean<TopMenuMapper>(TopMenuMapper.class);
		factoryBean.setSqlSessionFactory(factory);
		
		return factoryBean;
	}
	
	@Bean // 다른 Mapper <> 이 부분만 바꿔서 생성해주면 됨
	public MapperFactoryBean<UserMapper> getUserMapper(SqlSessionFactory factory) throws Exception{
		MapperFactoryBean<UserMapper> factoryBean = new MapperFactoryBean<UserMapper>(UserMapper.class);
		factoryBean.setSqlSessionFactory(factory);
		
		return factoryBean;
	}

	@Override
	public void addInterceptors(InterceptorRegistry registry) {
		TopMenuInterceptor topMenuInterceptor = new TopMenuInterceptor(topMenuService, loginUserBean);
		CheckLoginInterceptor checkLoginInterceptor = new CheckLoginInterceptor(loginUserBean);
		
		InterceptorRegistration reg1 = registry.addInterceptor(topMenuInterceptor);
		InterceptorRegistration reg2 = registry.addInterceptor(checkLoginInterceptor);
		
		reg1.addPathPatterns("/**"); //모든 요청에서 동작
		reg2.addPathPatterns("/user/modify", "/user/logout", "/board/*");
		//수정 페이지, 로그아웃 페이지, 게시판 폴더의 페이지에 요청 시 인터셉터 / board에서 main은 예외
		reg2.excludePathPatterns("/board/main");
		//board에서 main은 예외
	}
	
	//메시자와의 충돌방지, 프로퍼티 파일과 메시지를 구분하여 별도로 관리
	@Bean
	public static PropertySourcesPlaceholderConfigurer placeholderConfigurer() {
		return new PropertySourcesPlaceholderConfigurer();
	}
	
	//에러 프로퍼티 파일을 메시지로 등록
	@Bean
	public ReloadableResourceBundleMessageSource messageSource() {
		ReloadableResourceBundleMessageSource res = new ReloadableResourceBundleMessageSource();
		res.setBasename("/WEB-INF/properties/error_message");
		return res;
	}
}
