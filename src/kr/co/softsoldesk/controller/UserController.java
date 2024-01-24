package kr.co.softsoldesk.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import kr.co.softsoldesk.Validator.UserValidator;
import kr.co.softsoldesk.beans.UserBean;
import kr.co.softsoldesk.service.UserService;

@Controller
@RequestMapping("/user")
public class UserController {
	
	@Autowired
	private UserService userService;

	@GetMapping("/login")
	public String login() {
		return "user/login";
	}
	
	@GetMapping("/join")
	public String join(@ModelAttribute("joinUserBean") UserBean joinUserBean) {
		return "user/join";
	}
	
	@PostMapping("/join_pro") //요청 받음
	public String join_pro(@Valid @ModelAttribute("joinUserBean") UserBean joinUserBean, BindingResult result) {
		if(result.hasErrors()) {
			return "user/join";
		}
		
		//join_success로 페이지 이동 전에 db에 값 저장
		userService.addUserInfo(joinUserBean);
		return "user/join_success"; //유효성 검사를 통과하면 보내지는 페이지
		
	}
	
	@GetMapping("/modify")
	public String modify() {
		return "user/modify";
	}
	
	@InitBinder
	public void initBinder(WebDataBinder blinder){
	     UserValidator validator1 = new UserValidator();
	     blinder.addValidators(validator1);
	}
}
