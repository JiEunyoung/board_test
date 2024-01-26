package kr.co.softsoldesk.mapper;

import org.apache.ibatis.annotations.Insert;

import kr.co.softsoldesk.beans.ContentBean;

public interface BoardMapper {

	@Insert("insert into content_table(content_idx, content_subject, content_text, content_file,"
			+ "content_writer_idx, content_board_idx, content_date)"
			+ "values(content_seq.nextval, #{content_subject}, #{content_text}, "
			+ "#{content_file, jdbcType=VARCHAR}, #{content_writer_idx}, #{content_board_idx}, sysdate)")
	void addContentInfo(ContentBean writeContentBean);
	
	//#{content_file, jdbcType=VARCHAR}: 널 값 들어가도 오류 발생하지 않도록 함
				
}
