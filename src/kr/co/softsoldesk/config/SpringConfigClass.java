package kr.co.softsoldesk.config;

import javax.servlet.Filter;
import javax.servlet.FilterRegistration;
import javax.servlet.MultipartConfigElement;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletRegistration;

import org.springframework.web.context.ContextLoaderListener;
import org.springframework.web.context.support.AnnotationConfigWebApplicationContext;
import org.springframework.web.filter.CharacterEncodingFilter;
import org.springframework.web.WebApplicationInitializer;
import org.springframework.web.servlet.DispatcherServlet;
import org.springframework.web.servlet.support.AbstractAnnotationConfigDispatcherServletInitializer;


public class SpringConfigClass implements WebApplicationInitializer {
	//자바 코드로 web.xml을 대체
	//웹어플리케이션 서버(톰켓)가 최초 구동 시 각종 설정 정의
	
	@Override
	public void onStartup(ServletContext servletContext) throws ServletException {
		//System.out.println("onstart");
		
		//web.xml <servlet>
		AnnotationConfigWebApplicationContext servletAppContext = new AnnotationConfigWebApplicationContext();
		//Spring MVC 프로젝트 설정을 위해 작성하는 클래스의 객체를 생성(스프링 컨테이너)
		
		servletAppContext.register(ServletAppContext.class);
		//ServletAppContext를 애플리케이션 컨텍스트 등록
		
		DispatcherServlet dispatcherServlet = new DispatcherServlet(servletAppContext);
		//요청 정보 가공처리 해서 뷰로 보냄
		ServletRegistration.Dynamic servlet = servletContext.addServlet("dispatcher", dispatcherServlet);
		
		servlet.setLoadOnStartup(1);
		servlet.addMapping("/");
		
		AnnotationConfigWebApplicationContext rootAppContext = new AnnotationConfigWebApplicationContext();
		rootAppContext.register(RootAppContext.class);
		//Bean을 정의하는 클래스 지정
		
		ContextLoaderListener listener = new ContextLoaderListener(rootAppContext);
		servletContext.addListener(listener);
		//web.xml에서 <listener>
		
		FilterRegistration.Dynamic filter = servletContext.addFilter("endcodingFilter", CharacterEncodingFilter.class);
		filter.setInitParameter("encoding", "UTF-8");
	    //web.xml에서 <filter>
		
		filter.addMappingForServletNames(null, false, "dispatcher");
		//dispatcher에 의해 추가된 Servlet에서 UTF-8로 인코딩
		
		MultipartConfigElement multipartConfigElement = new MultipartConfigElement(null, 52428800, 52428800, 0);
		
		servlet.setMultipartConfig(multipartConfigElement);
	
	}
	

}
