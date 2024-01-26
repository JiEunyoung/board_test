package kr.co.softsoldesk.controller;

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
import kr.co.softsoldesk.service.BoardService;

@Controller
@RequestMapping("/board") // /board 생략하기 위함
public class BoardController {
	
	@Autowired
	BoardService boardService;
	
	@GetMapping("/main") // /board/main
	public String main(@RequestParam("board_info_idx") int board_info_idx, Model model) {
		model.addAttribute("board_info_idx", board_info_idx);
		return "board/main";
	}
	
	@GetMapping("/read") 
	public String read() {
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
	public String modify() {
		return "board/modify";
	}
}
