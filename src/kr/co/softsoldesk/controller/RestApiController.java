package kr.co.softsoldesk.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import kr.co.softsoldesk.service.UserService;

@RestController //data를 뿌릴 때 사용
public class RestApiController {
	
	@Autowired
	private UserService userService;
	
	@GetMapping("/user/checkUserIdExist/{user_id}")
	public String checkUserExist(@PathVariable String user_id) {
		
		boolean chk = userService.checkuserIdExist(user_id);
		
		return chk+""; //강제로 형 변환 됨(String형)
		//db에 있으면 false, 없으면 true
	}

}
