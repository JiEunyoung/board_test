package kr.co.softsoldesk.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.HandlerInterceptor;

import kr.co.softsoldesk.beans.ContentBean;
import kr.co.softsoldesk.beans.UserBean;
import kr.co.softsoldesk.service.BoardService;

public class CheckWriterInterceptor implements HandlerInterceptor{

	private UserBean loginUserBean; //@Autowired로 불가능
	private BoardService boardService; //Service단에 글 내용 불러오는 함수 있음
	
	public CheckWriterInterceptor(UserBean longinUserBean, BoardService boardService){
		this.loginUserBean = longinUserBean;
		this.boardService = boardService;
	}

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		
		String str1 = request.getParameter("content_idx"); //주소에 붙여서 오는 값은 String
		int content_idx = Integer.parseInt(str1); //getContentInfo에 필요한 값은 int 형이기에 형 변환 필요
		
		ContentBean currentContentBean = boardService.getContentInfo(content_idx);
		
		if(currentContentBean.getContent_writer_idx() != loginUserBean.getUser_idx()) {
			String contextPath = request.getContextPath();
			response.sendRedirect(contextPath + "/board/not_writer");
			
			return false;
		}
		return true;
	}
	
	
}
