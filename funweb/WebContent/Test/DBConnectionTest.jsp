<%@page import="java.sql.SQLException"%>
<%@page import="com.funweb.web.test.DBTest"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<%--
	데이터베이스 테스트를 수행하여 DB 연결 및 SQL 실행 작업에 문제가 있는지 확인한다.
	
	데이터베이스에 문제가 없다면 "DB Connection & DB SQL execution test 성공" 이라는 메시지를 
	웹 화면상에 표시한다.
	
	데이터베이스 관련 작업 도중 예외가 발생하였을 경우 "DB test 도중 에러 발생!!! 콘솔창을 확인 바랍니다." 라는 메시지를
	웹 화면상에 표시한다. 이 경우 콘솔창을 확인하여 문제 발생 원인을 파악하여 처리하여야 한다.
 --%>
<%

	DBTest dbTest = new DBTest();	
	
	try {
		dbTest.testConnectionAndDBSQLExecution();
		out.println("DB Connection & DB SQL execution test 성공");
	} catch (SQLException e) {
		out.println("DB test 도중 에러 발생!!! 콘솔창을 확인 바랍니다.");
	}
	
%>