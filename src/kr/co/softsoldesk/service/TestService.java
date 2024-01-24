package kr.co.softsoldesk.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import kr.co.softsoldesk.dao.TestDAO;

@Service //@Component와 비슷
public class TestService {
	
	@Autowired
	private TestDAO dao; //dao에서 가져온 값 가공
	
	public String testServiceMethod() {
		
		String str = dao.testDaoMethod();
		
		return str; //컨트롤러로 보낼 값
	}
}
