<%@page import="com.funweb.web.dto.Notice"%>
<%@page import="com.funweb.web.daoimpl.NoticeDaoImpl"%>
<%@page import="com.funweb.web.model.PaginationResult"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<%	
	
	PaginationResult<Notice> paginationResult = new PaginationResult<Notice>(new NoticeDaoImpl("YYYYMMDD"), 1, 3, 10);
	
%>	

beginPage : <%= paginationResult.getBeginPage() %>, endPage : <%= paginationResult.getEndPage() %>
