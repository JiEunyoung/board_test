package kr.co.softsoldesk.interceptor;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.HandlerInterceptor;

import kr.co.softsoldesk.beans.BoardInfoBean;
import kr.co.softsoldesk.beans.UserBean;
import kr.co.softsoldesk.service.TopMenuService;

public class TopMenuInterceptor implements HandlerInterceptor{ //인터셉터 오토와일드 사용 x => 생성자로 주입

	private TopMenuService topMenuService;
	
	private UserBean loginUserBean;
	
	public TopMenuInterceptor(TopMenuService topMenuService, UserBean loginUserBean) {
		this.topMenuService = topMenuService;
		this.loginUserBean = loginUserBean;
		
	}

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		List<BoardInfoBean> topMenuList = topMenuService.getTopMenuList();
		request.setAttribute("topMenuList", topMenuList);
		
		request.setAttribute("loginUserBean", loginUserBean);
		
		return true;
	}
	
	//보통은 service에서 처리하지만 topMenu는 service 부분 없기 때문에 이곳에서 처리
	
	
}
