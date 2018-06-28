<%@page import="com.funweb.web.dao.BoardDao"%>
<%@page import="com.funweb.web.dto.Notice"%>
<%@page import="java.util.List"%>
<%@page import="com.funweb.web.daoimpl.NoticeDaoImpl"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%

	BoardDao dao = new NoticeDaoImpl("YYYYMMDD");

	int count = dao.getCountRows();

	List<Notice> list = dao.getBoardList(0, 3);
	
%>

count : <%= count %><br>

<%= list.get(0).getNo() %>, <%= list.get(1).getNo() %>, <%= list.get(2).getNo() %>