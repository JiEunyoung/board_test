package kr.co.softsoldesk.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.HandlerInterceptor;

import kr.co.softsoldesk.beans.UserBean;

public class CheckLoginInterceptor implements HandlerInterceptor{
	//서블릿앱콘텍스트에서 인터셉터 등록해야 함
	
	private UserBean loginUserBean;
	
	public CheckLoginInterceptor(UserBean loginUserBean) {
		
		this.loginUserBean = loginUserBean;
	}
	
	//요청 가기 전에 가로채기 => prehandle
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		
		if(loginUserBean.isUserLogin() == false) {
			
			String contextPath = request.getContextPath(); //루트 URL
			response.sendRedirect(contextPath+"/user/not_login");
			//로그인되어 있지 않은 사용자를 user/not_login.jsp 페이지로 리다이렉트
			return false;
					
		}
		return true;
		
	}
	

}
