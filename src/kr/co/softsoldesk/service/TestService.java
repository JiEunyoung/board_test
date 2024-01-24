package kr.co.softsoldesk.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import kr.co.softsoldesk.dao.TestDAO;

@Service //@Component�� ���
public class TestService {
	
	@Autowired
	private TestDAO dao; //dao���� ������ �� ����
	
	public String testServiceMethod() {
		
		String str = dao.testDaoMethod();
		
		return str; //��Ʈ�ѷ��� ���� ��
	}
}
