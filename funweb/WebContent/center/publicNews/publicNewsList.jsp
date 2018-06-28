<%@ page language="java" contentType="text/html; charset=UTF-8"	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<article>

<h1>Public News</h1>

<!-- 글 제목 선택시 이동할 글 -->
<c:set var="readPage" value="publicNewsRead.do" scope="request" />

<!-- 페이지 목록에서 페이지 클릭시 이동할 페이지 -->
<c:set var="listPage" value="publicNews.do" scope="request" />

<!-- 페이지 정보 출력 -->
<jsp:include page="/board/inc/boardInfo.jsp" />
<!-- 페이지 정보 출력 -->



<!-- 관리자만 글을 쓸 수 있다. -->
<c:if test="${sessionScope.userRole eq 'ADMIN'}">

<!-- 글쓰기 버튼 클릭시 사용할 변수 -->
<c:set var="writeDo" value="publicNewsWrite.do" scope="request"/>

<!-- 관리자만 글쓰기 기능을 사용할 수 있다. -->
<jsp:include page="/center/inc/writeBtn.jsp" />
<!-- 관리자만 글쓰기 기능을 사용할 수 있다. -->

</c:if>



<!-- 글 목록 출력 -->
<jsp:include page="/board/inc/boardList1.jsp" />
<!-- 글 목록 출력 -->



<!-- 글 검색 기능 -->
<div id="table_search">
	<input type="text" name="search" class="input_box"> <input
		type="button" value="search" class="btn">
</div>
<!-- 글 검색 기능 -->



<!-- 페이지 목록 출력 -->
<jsp:include page="/board/inc/pagination.jsp" />
<!-- 페이지 목록 출력 -->

</article>