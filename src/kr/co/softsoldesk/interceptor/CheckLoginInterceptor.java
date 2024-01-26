package kr.co.softsoldesk.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.HandlerInterceptor;

import kr.co.softsoldesk.beans.UserBean;

public class CheckLoginInterceptor implements HandlerInterceptor{
	//���������ؽ�Ʈ���� ���ͼ��� ����ؾ� ��
	
	private UserBean loginUserBean;
	
	public CheckLoginInterceptor(UserBean loginUserBean) {
		
		this.loginUserBean = loginUserBean;
	}
	
	//��û ���� ���� ����ä�� => prehandle
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		
		if(loginUserBean.isUserLogin() == false) {
			
			String contextPath = request.getContextPath(); //��Ʈ URL
			response.sendRedirect(contextPath+"/user/not_login");
			//�α��εǾ� ���� ���� ����ڸ� user/not_login.jsp �������� �����̷�Ʈ
			return false;
					
		}
		return true;
		
	}
	

}
