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
		
		String beanName = errors.getObjectName(); //ȸ�����Կ� ���� bean�� ��츸 �ش� ���� ����
		/*ŸŶ���� �޾ƿ� ��ü�� joinUserBean�� �ƴϸ� ����
		 * �α��� ��ü�� @vliad�� �־� Ÿ������ �Ѿ���� �����ϱ� ����*/
		
		if(beanName.equals("joinUserBean") || beanName.equals("modifyUserBean")) {
			//���ǹ��� ����� ȸ������ ��ü�� �Ѿ�� ���� ��ȿ���˻�
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
