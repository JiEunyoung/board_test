package kr.co.softsoldesk.service;

import java.io.File;

import javax.annotation.Resource;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;
import org.apache.ibatis.reflection.SystemMetaObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import kr.co.softsoldesk.beans.ContentBean;
import kr.co.softsoldesk.beans.UserBean;
import kr.co.softsoldesk.dao.BoardDAO;

@Service
@PropertySource("/WEB-INF/properties/option.properties")
public class BoardService {
	
	@Autowired
	private BoardDAO boardDao;
	
	@Resource(name = "loginUserBean")
	private UserBean loginUserBean;
	
	@Value("${path.upload}")
	private String path_upload;
	
	public String saveUploadFile(MultipartFile upload_file) {
		
		String file_name = System.currentTimeMillis() + "_" + 
		FilenameUtils.getBaseName(upload_file.getOriginalFilename()) + "." +    //파일 이름
		FilenameUtils.getExtension(upload_file.getOriginalFilename());          //확장자
		
		try {
			upload_file.transferTo(new File(path_upload + "/" + file_name));
		} catch (Exception e) {
			e.printStackTrace();
		}
		return file_name;
	}

	public void addContentInfo(ContentBean writeContentBean) {
		/*
		System.out.println(writeContentBean.getContent_subject());
		System.out.println(writeContentBean.getContent_text());
		System.out.println(writeContentBean.getUpload_file().getSize());
		*/
		
		MultipartFile upload_file = writeContentBean.getUpload_file();
		
		if(upload_file.getSize() > 0) { //파일이 하나 들어오면
			String file_name = saveUploadFile(upload_file);
			System.out.println(file_name);
			writeContentBean.setContent_file(file_name); //db로 보내기 위한 값 주입
		}
		
		writeContentBean.setContent_writer_idx(loginUserBean.getUser_idx());
		boardDao.addContentInfo(writeContentBean);
		
	}
	 
}
