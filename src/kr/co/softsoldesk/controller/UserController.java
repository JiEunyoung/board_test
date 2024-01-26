package kr.co.softsoldesk.controller;

import javax.annotation.Resource;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import kr.co.softsoldesk.Validator.UserValidator;
import kr.co.softsoldesk.beans.UserBean;
import kr.co.softsoldesk.service.UserService;

@Controller
@RequestMapping("/user")
public class UserController {
	
	@Autowired
	private UserService userService;
	
	@Resource(name="loginUserBean")
	private UserBean loginUserBean;

	@GetMapping("/login") //값 입력 받은거 받아서 login_pro로 보내기 위해 @ModelAttribute 사용
	public String login(@ModelAttribute("tempLoginUserBean") UserBean tempLoginUserBean,
						@RequestParam(value="fail", defaultValue = "false") boolean fail,
						Model model) {
		
		model.addAttribute("fail", fail);
		return "user/login";
	}
	
	@PostMapping("/login_pro")    //검증 위해 사용                                                                                      
	public String login_pro(@Valid @ModelAttribute("tempLoginUserBean") UserBean tempLoginUserBean, BindingResult result) {
		                  //@Valid 붙이면 validator 클래스로 무조건 넘어감
		if(result.hasErrors()) { //로그인 실패
			return "user/login";
		}
		
		userService.getLoginUserInfo(tempLoginUserBean); //db에 값이 있으면 해당 함수 수행
		
		if(loginUserBean.isUserLogin() == true) {
			return "user/login_success"; 
		}else {
			return "user/login_fail";
		}
		
	}
	
	@GetMapping("/not_login")
	public String not_login() {
		
		return "user/not_login";
		
	}
	
	@GetMapping("/logout")
	public String logout() {
		
		loginUserBean.setUserLogin(false);
		return "user/logout";
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
	public String modify(@ModelAttribute("modifyUserBean") UserBean modifyUserBean) {
		
		userService.getModifyUserInfo(modifyUserBean);
		
		return "user/modify";
	}
	
	@PostMapping("/modify_pro")
	public String modify_pro(@Valid @ModelAttribute("modifyUserBean") UserBean modifyUserBean, 
							BindingResult result) {
		
		if(result.hasErrors()) {
			return "user/modify";
		}
		
		userService.modifyUserInfo(modifyUserBean);
		
		return "user/modify_success";
	}
	
	@InitBinder
	public void initBinder(WebDataBinder blinder){
	     UserValidator validator1 = new UserValidator();
	     blinder.addValidators(validator1);
	}
}
