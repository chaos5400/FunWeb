<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@page import="com.funweb.web.daoimpl.AccountDaoImpl"%>
<% request.setCharacterEncoding("UTF-8"); %>

<%
	out.println(new AccountDaoImpl().
			countRowsForUserId(
				request.getParameter("id")));
%>
