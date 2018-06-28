package com.funweb.web.util;

import java.io.File;
import java.io.IOException;
import java.util.Enumeration;
import java.util.List;
import java.util.Vector;

import javax.servlet.http.HttpServletRequest;

import com.funweb.web.exception.UploadFailException;
import com.oreilly.servlet.MultipartRequest;
import com.oreilly.servlet.multipart.DefaultFileRenamePolicy;

public class FileUtils {
	
	private MultipartRequest multi;
	private String webPath;
	private Enumeration<String> files;
	
	
	
	
	
	public static void deleteFile(String path) {
		
        File file = new File(path);
        
        if( file.exists() ){
            file.delete();
        }
        
	}
	
	
	
	
	
	public static void deleteFiles(List<String> paths) {
		for(String s : paths) {
			FileUtils.deleteFile(s);
		}
	}
	
	
	
	
	
	@SuppressWarnings("unchecked")
	public FileUtils(HttpServletRequest request, String path, int max) throws UploadFailException {
		
		// 웹프로젝트의 파일 업로드 경로를 저장한다.
		this.webPath = path;
		
		// 실제 서버에서 업로드되는 경로를 구해온다.
		String realPath = request.getServletContext().getRealPath(webPath);
		System.out.println(realPath);
		
		try {
		// 파일업로드 및 업로드 후 파일명 가져옴
		this.multi = new MultipartRequest(
						request,
						realPath,
						max,
						"UTF-8",
						new DefaultFileRenamePolicy());
		} catch (IOException e) {
			throw new UploadFailException();
		}
		
		this.files = multi.getFileNames();
		
	}
	
	
	
	public FileUtils(HttpServletRequest request, String path) throws UploadFailException {
		this(request, path, 10*1024*1024); // 기본 파일 최대 용량은 10MB
	}
	
	
	
	
	
	public String getUploadPath() {
		if(files.hasMoreElements()) {
			String fileName = multi.getFilesystemName(files.nextElement());
		
			System.out.println(fileName);
			
			// 업로드된 경로와 파일명을 통해 이미지의 경로를 생성
			return this.webPath + fileName;
		} else {
			return null;
		}
		
	}
	
	
	
	
	public Vector<String> getUploadPaths() {
		Vector<String> v = null;
		while(true) {
			String tmp = this.getUploadPath();
			if(tmp == null) { break; }					// null 이 반환된다면 루프를 빠져나간다.
			if(v == null) { v = new Vector<String>(); }	// 요소가 존재한다면 초기화 작업 진행
			v.add(tmp);
		}
		return v;
	}

}
