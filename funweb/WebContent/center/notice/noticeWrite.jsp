<%@ page language="java" contentType="text/html; charset=UTF-8"	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<article>

<h1>Notice</h1>

<c:set var="proc" value="noticeWriteProc.do" scope="request"/>
<jsp:include page="/board/write.jsp" />

</article>