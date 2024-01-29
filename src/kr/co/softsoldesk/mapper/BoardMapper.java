package kr.co.softsoldesk.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.SelectKey;

import kr.co.softsoldesk.beans.ContentBean;

public interface BoardMapper {

	@SelectKey(statement = "select content_seq.nextval from dual", keyProperty = "content_idx",
			  before=true, resultType = int.class) //이미 만들어진 시퀀스 주입
	//statement = "select content_seq.nextval from dual": 발생된 시퀀스 번호가 10이었으면 11을 새로 반환
	//keyProperty = "content_idx": writeContentBean이 가지고 있는 "content_idx" 속성에 담아
	//before=true: 아래의 쿼리문이 실행되기 전에 실행
	//resultType = int.class: 타입은 int로
	@Insert("insert into content_table(content_idx, content_subject, content_text, content_file,"
			+ "content_writer_idx, content_board_idx, content_date)"
			+ "values(#{content_idx}, #{content_subject}, #{content_text}, "
			+ "#{content_file, jdbcType=VARCHAR}, #{content_writer_idx}, #{content_board_idx}, sysdate)")
	void addContentInfo(ContentBean writeContentBean);
	
	//#{content_file, jdbcType=VARCHAR}: 널 값 들어가도 오류 발생하지 않도록 함
	
	@Select("select board_info_name from board_info_table where board_info_idx = #{board_info_idx}")
	String getBoardInfoName(int board_info_idx);
	
	@Select("SELECT a1.content_idx, a1.content_subject, a2.user_name AS content_writer_name, TO_CHAR(a1.content_date, 'yyyy-mm-dd') as content_date"
			+ " FROM content_table a1, user_table a2"
			+ " WHERE a1.content_writer_idx = a2.user_idx AND a1.content_board_idx = #{board_info_idx}"
			+ " ORDER BY a1.content_idx DESC")
	List<ContentBean> getContentList(int board_info_idx);
	
	@Select("SELECT a1.user_name AS content_writer_name, TO_CHAR(a2.content_date, 'yyyy-mm-dd') AS content_date,"
			+ "a2.content_subject, a2.content_text, a2.content_file, "
			+ "a2.content_writer_idx "
			+ "FROM user_table a1, content_table a2 "
			+ "WHERE a1.user_idx = a2.content_writer_idx "
			+ "AND content_idx = #{content_idx}")
	ContentBean getContentInfo(int content_idx);
	
}
