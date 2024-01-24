package kr.co.softsoldesk.dao;

import org.springframework.stereotype.Repository;
//dao: db에서 연동하여 가져온 값으로 객체 생성

@Repository //@Component와 비슷
public class TestDAO {

	public String testDaoMethod() {
		return "문자열";
	}
}
