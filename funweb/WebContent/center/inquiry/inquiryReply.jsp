<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<article>

<h1>고객 문의 답글 작성</h1>

<c:set var="proc" value="inquiryReplyProc.do?no=${param.no}&" scope="request"/>
<jsp:include page="/board/writeWithPass.jsp" />

</article>
