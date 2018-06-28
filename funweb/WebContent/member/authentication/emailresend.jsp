<%@page import="jdbccontext.exception.NoQueryDataException"%>
<%@page import="jdbccontext.creator.JdbcContextCreator"%>
<%@page import="jdbccontext.JdbcContext"%>
<%@page import="com.funweb.web.dao.AccountDao"%>
<%@page import="com.funweb.web.util.EMailUtils"%>
<%@page import="com.funweb.web.daoimpl.AccountDaoImpl"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<% request.setCharacterEncoding("UTF-8"); %>

<%
	String id = request.getParameter("id");
	
	// 메일 재발송을 위해서는 
	// 새로 생성한 토큰이 필요하다.
	JdbcContext jdbcContext = JdbcContextCreator.getJdbcContext();
	AccountDao dao = new AccountDaoImpl(jdbcContext);
	
	jdbcContext.setTransactinal(true);
	
	String token = null;
	try {
		token = dao.updateNewToken(id);	// 새 토큰으로 DB 업데이트 후 토큰 반환
		jdbcContext.commit();
	} catch (NoQueryDataException e) {
		jdbcContext.rollback();
		throw new NoQueryDataException();
	}
	
	/* Send E-Mail */
	EMailUtils eu = new EMailUtils(); 
	eu.sendMailForAuthentication(id, token, request.getContextPath());
%>