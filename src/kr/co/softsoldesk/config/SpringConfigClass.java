package kr.co.softsoldesk.config;

import javax.servlet.Filter;
import javax.servlet.FilterRegistration;
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
	//�ڹ� �ڵ�� web.xml�� ��ü
	//�����ø����̼� ����(����)�� ���� ���� �� ���� ���� ����
	
	@Override
	public void onStartup(ServletContext servletContext) throws ServletException {
		//System.out.println("onstart");
		
		//web.xml <servlet>
		AnnotationConfigWebApplicationContext servletAppContext = new AnnotationConfigWebApplicationContext();
		//Spring MVC ������Ʈ ������ ���� �ۼ��ϴ� Ŭ������ ��ü�� ����(������ �����̳�)
		
		servletAppContext.register(ServletAppContext.class);
		//ServletAppContext�� ���ø����̼� ���ؽ�Ʈ ���
		
		DispatcherServlet dispatcherServlet = new DispatcherServlet(servletAppContext);
		//��û ���� ����ó�� �ؼ� ��� ����
		ServletRegistration.Dynamic servlet = servletContext.addServlet("dispatcher", dispatcherServlet);
		
		servlet.setLoadOnStartup(1);
		servlet.addMapping("/");
		
		AnnotationConfigWebApplicationContext rootAppContext = new AnnotationConfigWebApplicationContext();
		rootAppContext.register(RootAppContext.class);
		//Bean�� �����ϴ� Ŭ���� ����
		
		ContextLoaderListener listener = new ContextLoaderListener(rootAppContext);
		servletContext.addListener(listener);
		//web.xml���� <listener>
		
		FilterRegistration.Dynamic filter = servletContext.addFilter("endcodingFilter", CharacterEncodingFilter.class);
		filter.setInitParameter("encoding", "UTF-8");
	    //web.xml���� <filter>
		
		filter.addMappingForServletNames(null, false, "dispatcher");
		//dispatcher�� ���� �߰��� Servlet���� UTF-8�� ���ڵ�
	
	}
	

}


/*
public class SpringConfigClass extends AbstractAnnotationConfigDispatcherServletInitializer{

	@Override
	protected Class<?>[] getRootConfigClasses() {
		return new Class[] { RootAppContext.class };
	}//������Ʈ���� ����� Bean���� �����ϱ� ���� Ŭ���� ����

	@Override
	protected Class<?>[] getServletConfigClasses() {
		return new Class[] { ServletAppContext.class };
	}//Spring MVC ������Ʈ ������ ���� Ŭ���� ����

	@Override
	protected String[] getServletMappings() {
		return new String[] {"/"};
	}//DispatcherServlet�� ������ ��û �ּҸ� ����

	@Override
	protected Filter[] getServletFilters() {
		CharacterEncodingFilter encodingFilter = new CharacterEncodingFilter();
		encodingFilter.setEncoding("UTF-8");
		return new Filter[] { encodingFilter };
	} //�Ķ���� ���ڵ� ���� ����
	
}
*/