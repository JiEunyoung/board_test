package kr.co.softsoldesk.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import kr.co.softsoldesk.beans.BoardInfoBean;
import kr.co.softsoldesk.service.TopMenuService;

//Mapper->DAO->service->controller

@Controller
public class MainController {
	
	/*
	@Autowired
	TopMenuService top;
	
	@GetMapping("/main")
	public String main(Model model) {
		
		List<BoardInfoBean> topMenuList = top.getTopMenuList();
		model.addAttribute("topMenuList", topMenuList);
		
		return "main";
	}
	*/
	
	@GetMapping("/main")
	public String main() {
		
		
		
		return "main";
	}
}
