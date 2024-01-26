package kr.co.softsoldesk.Validator;

import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import kr.co.softsoldesk.beans.UserBean;

public class UserValidator implements Validator{

	@Override
	public boolean supports(Class<?> clazz) {
		return UserBean.class.isAssignableFrom(clazz);
	}

	@Override
	public void validate(Object target, Errors errors) {
		
		UserBean userBean = (UserBean)target;
		
		String beanName = errors.getObjectName(); //회원가입에 대한 bean일 경우만 해당 로직 수행
		/*타킷으로 받아온 객체가 joinUserBean이 아니면 에러
		 * 로그인 객체도 @vliad가 있어 타깃으로 넘어옴을 방지하기 위함*/
		
		if(beanName.equals("joinUserBean") || beanName.equals("modifyUserBean")) {
			//조건문을 만들어 회원가입 객체가 넘어올 때만 유효성검사
			if(userBean.getUser_pw().equals(userBean.getUser_pw2()) == false) {
				errors.rejectValue("user_pw", "NotEquals");
				errors.rejectValue("user_pw2", "NotEquals");
			}
			if(beanName.equals("joinUserBean")) {
				if(userBean.isUserIdExist() == false) {
					errors.rejectValue("user_id", "DontCheckUserIdExist");
				}
			}	
		}
	}
	
}
