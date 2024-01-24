package kr.co.softsoldesk.controller;

import javax.annotation.Resource;
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
	
	@Resource(name="loginUserBean")
	private UserBean loginUserBean;

	@GetMapping("/login") //�� �Է� ������ �޾Ƽ� login_pro�� ������ ���� @ModelAttribute ���
	public String login(@ModelAttribute("tempLoginUserBean") UserBean tempLoginUserBean) {
		return "user/login";
	}
	
	@PostMapping("/login_pro")    //���� ���� ���                                                                                      
	public String login_pro(@Valid @ModelAttribute("tempLoginUserBean") UserBean tempLoginUserBean, BindingResult result) {
		
		if(result.hasErrors()) { //�α��� ����
			return "user/login";
		}
		
		userService.getLoginUserInfo(tempLoginUserBean); //db�� ���� ������ �ش� �Լ� ����
		
		if(loginUserBean.isUserLogin() == true) {
			return "user/login_success"; 
		}else {
			return "user/login_fail";
		}
		
		
	}
	
	@GetMapping("/join")
	public String join(@ModelAttribute("joinUserBean") UserBean joinUserBean) {
		return "user/join";
	}
	
	@PostMapping("/join_pro") //��û ����
	public String join_pro(@Valid @ModelAttribute("joinUserBean") UserBean joinUserBean, BindingResult result) {
		if(result.hasErrors()) {
			return "user/join";
		}
		
		//join_success�� ������ �̵� ���� db�� �� ����
		userService.addUserInfo(joinUserBean);
		return "user/join_success"; //��ȿ�� �˻縦 ����ϸ� �������� ������
		
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
