package kr.co.softsoldesk.controller;

import java.util.List;

import javax.annotation.Resource;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import kr.co.softsoldesk.beans.ContentBean;
import kr.co.softsoldesk.beans.PageBean;
import kr.co.softsoldesk.beans.UserBean;
import kr.co.softsoldesk.service.BoardService;

@Controller
@RequestMapping("/board") // /board 생략하기 위함
public class BoardController {
	
	@Autowired
	BoardService boardService;
	
	@Resource(name="loginUserBean")
	private UserBean loginUserBean;
	
	@GetMapping("/main") // /board/main
	public String main(@RequestParam("board_info_idx") int board_info_idx, Model model,
					   @RequestParam(value = "page", defaultValue = "1") int page) { //1페이지부터 나오도록 세팅
		model.addAttribute("board_info_idx", board_info_idx);
		
		String boardInfoName = boardService.getBoardInfoName(board_info_idx);
		model.addAttribute("boardInfoName", boardInfoName);
		
		List<ContentBean> contentList = boardService.getContentList(board_info_idx, page);
		model.addAttribute("contentList", contentList);
		
		PageBean pageBean = boardService.getContent(board_info_idx, page);
		model.addAttribute("pageBean", pageBean); //model에 담아 jsp에 뿌림
		//파라미터로 받은 현재 페이지와 게시판 번호를 매개변수로 현재 페이지에 대한 정보를 반환 후 board/main.jsp로 리턴
		
		return "board/main";
	}
	
	@GetMapping("/read") //하나의 객체로 담아 보낼 때 Model 사용
	public String read(@RequestParam("board_info_idx") int board_info_idx,
					   @RequestParam("content_idx") int content_idx, Model model) {
		model.addAttribute("board_info_idx", board_info_idx);
		
		ContentBean readContentBean = boardService.getContentInfo(content_idx);
		model.addAttribute("readContentBean", readContentBean);   //read.jsp로 보냄
		
		model.addAttribute("content_idx", content_idx);
		
		model.addAttribute("loginUserBean", loginUserBean);
		
		return "board/read";
	}
	
	@GetMapping("/write") // /board/main
	public String write(@ModelAttribute("writeContentBean") ContentBean writeContentBean,
					    @RequestParam("board_info_idx") int board_info_idx) {
		
		writeContentBean.setContent_board_idx(board_info_idx);
		return "board/write";
	}
	
	@PostMapping("/write_pro")
	public String write_pro(@Valid @ModelAttribute("writeContentBean") ContentBean writeContentBean, BindingResult result) {
		
		if(result.hasErrors()) {
			return "board/write";
		}
		
		boardService.addContentInfo(writeContentBean); //제목, 내용, 파일 크기 값 있음
		
		
		return "board/write_success";
	}
	
	@GetMapping("/modify") // /board/main
	public String modify(@RequestParam("board_info_idx") int board_info_idx, 
						 @RequestParam("content_idx") int content_idx, 
						 @ModelAttribute("modifyContentBean") ContentBean modifyContentBean,
						 Model model) {
		
		model.addAttribute("board_info_idx", board_info_idx);
		model.addAttribute("content_idx", content_idx); //수정 페이지로 보냄
		
		//정보 가져옴 => 수정할 하나의 게시글 정보 가져옴
		ContentBean tempContentBean = boardService.getContentInfo(content_idx);
		
		modifyContentBean.setContent_writer_name(tempContentBean.getContent_writer_name());
		modifyContentBean.setContent_date(tempContentBean.getContent_date());
		modifyContentBean.setContent_subject(tempContentBean.getContent_subject());
		modifyContentBean.setContent_text(tempContentBean.getContent_text());
		modifyContentBean.setContent_file(tempContentBean.getContent_file());
		
		modifyContentBean.setContent_board_idx(board_info_idx);
		modifyContentBean.setContent_idx(content_idx);
		
		return "board/modify";
	}
	
	@PostMapping("/modify_pro")
	public String modify_pro(@Valid @ModelAttribute("modifyContentBean") ContentBean modifyContentBean,
								BindingResult result) {
		
		if(result.hasErrors()) {
			return "board/modify";
		}
		
		boardService.modifyContentInfo(modifyContentBean); //service, dao, mappper 껴서 db 수정
		return "board/modify_success";
		
	}
	
	@GetMapping("/not_writer")
	public String not_writer() {
		return "board/not_writer";
	}
	
	@GetMapping("/delete")
	public String delete(@RequestParam("board_info_idx") int board_info_idx,
			 			 @RequestParam("content_idx") int content_idx,
			 			 Model model) {
		boardService.deleteContentInfo(content_idx);
		model.addAttribute("board_info_idx", board_info_idx);
		
		return "board/delete";
		
	}
}
