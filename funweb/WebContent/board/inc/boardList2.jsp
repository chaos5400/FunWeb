<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<table id="notice">
	<tr>
		<th class="tno">No.</th>
		<th class="ttitle">Title</th>
		<th class="twrite">Writer</th>
		<th class="tdate">Date</th>
		<th class="tread">Read</th>
	</tr>
	
	<!-- 글 리스트를 출력한다. -->
	<c:if test="${paginationResult.boardList ne null}">
		<c:forEach items="${paginationResult.boardList}" var="list">
			<tr>
				<td>${list.no}</td>
				<td class="left"><div class="title">
				
				
				<c:if test="${list.indent > 0}">
				<!-- 답변 글이라면 제목 앞에 ─을 넣어준다. -->
				<c:forEach begin="1" end="${list.indent}" step="1" varStatus="status">─</c:forEach>
				
				<!-- 답변 글이라면 [답변] 추가 -->
				[답변]
				<!-- 답변 글이라면 [답변] 추가 -->
				</c:if>
				
				<!-- 답변 글이라면 제목 앞에 ─을 넣어준다. -->
				
				<a href="${readPage}?no=${list.no}&page=${paginationResult.currentPage}">${list.title}</a></div></td>
				<td><div class="write">${list.writer}</div></td>
				<td>${list.date}</td>
				<td>${list.readCount}</td>
			</tr>
		</c:forEach>
	</c:if>
	<!-- 글 리스트를 출력한다. -->
</table>