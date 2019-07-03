package com.example.spring02.controller.upload;

import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.io.PrintWriter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.multipart.MultipartFile;

@Controller
public class ImageUploadController {

	private static final Logger logger = LoggerFactory.getLogger(ImageUploadController.class);
	
	@RequestMapping("imageUpload.do")
	public void imageUpload(HttpServletRequest request, HttpServletResponse response, 
							MultipartFile upload) throws Exception{
		
		response.setCharacterEncoding("utf-8");
		
		response.setContentType("text/html; carset-utf-8");
		
		//업로드 파일 이름
		String fileName = upload.getOriginalFilename();
		//파일을 바이트배열로
		byte[] bytes = upload.getBytes();
		
		//이미지를 업로드할 디렉토리 설정(배포)
		String uploadPath = "D:\\work\\.metadata\\.plugins\\org.eclipse.wst.server.core\\tmp0\\wtpwebapps\\spring02\\WEB-INF\\views\\images\\";
		
		//클라이언트 -> 서버로 업로드(OutputStream을 사용)
		OutputStream out = new FileOutputStream(new File(uploadPath + fileName));
		
		//서버로 업로드
		out.write(bytes);
		
		//클라이언트에 결과 표시
		String callback = request.getParameter("CKEditorFuncNum");
		//서버 -> 클라이언트로 텍스트 전송(자바스크립트 실행)
		PrintWriter printWriter = response.getWriter();
		String fileUrl = request.getContextPath()+"/images/"+fileName;
		printWriter.println("<script>window.parent.CKEDITOR.tools.callFunction("
				+callback+",'"+fileUrl+"','이미지가 업로드되었습니다.')"
				+"</script>");
		
		printWriter.flush();
		
				
	}
	
}
