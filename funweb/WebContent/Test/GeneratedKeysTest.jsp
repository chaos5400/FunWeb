<%@page import="com.funweb.web.test.DBTest"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    
<% 
	DBTest dt = new DBTest();
	dt.testGeneratedKeys();
%>
