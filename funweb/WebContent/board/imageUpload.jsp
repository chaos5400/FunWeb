<%@page import="com.funweb.web.daoimpl.TempImageDaoImpl"%>
<%@page import="com.funweb.web.util.RequestUtils"%>
<%@page import="com.funweb.web.util.FileUtils"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%
	
	request.setCharacterEncoding("UTF-8");
	
	// 파일을 업로드하고 업로드된 경로를 가져온다. 
	String uploadPath = new FileUtils(request, "/upload/images/").getUploadPath();
	
	// 업로드된 경로를 시퀀스와 함께 DB에 저장한다.
	// 글 저장을 하지 않을 시 서버에 잔존하는 
	// 사용되지 않는 이미지를 삭제하기 위해서다.
	new TempImageDaoImpl().insertTempImagePath(
			RequestUtils.getSequence(request),
			request.getServletContext().getRealPath(uploadPath));
	
	String webPath = request.getContextPath() + uploadPath;
	
	// 업로드된 이미지를 CKEditor에 적용시킨다.
	out.write("<script type='text/javascript'>window.parent.CKEDITOR.tools.callFunction("

               + request.getParameter("CKEditorFuncNum")

               + ",'"

               + webPath

               + "')</script>");
	
%>

