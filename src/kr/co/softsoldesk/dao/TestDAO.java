package kr.co.softsoldesk.dao;

import org.springframework.stereotype.Repository;
//dao: db���� �����Ͽ� ������ ������ ��ü ����

@Repository //@Component�� ���
public class TestDAO {

	public String testDaoMethod() {
		return "���ڿ�";
	}
}
