<%@ page language="java" contentType="text/html; charset=UTF-8"	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<link href="${pageContext.request.contextPath}/css/subpage.css?v=1" rel="stylesheet" type="text/css">
<article>

<h1>Public News</h1>

<div id="writer" align="right">${read.writer}</div><br>
			
<div id="date" align="right">${read.date}</div><br><br>

<div id="title"><strong>제목 : ${read.title}</strong></div><br>

<div id="content">${read.content}</div><br><br>



<!-- [목록으로] 버튼 -->
<input type="button" id="list" value="목록으로" class="btn"/>

<script type="text/javascript">
document.getElementById("list").onclick = function() {
	location.href = "publicNews.do?page=${param.page}";
}
</script>
<!-- [목록으로] 버튼 -->



<!-- 게시글 작성자만 버튼을 클릭할 수 있다. -->
<c:if test="${isEqualUser eq true}">
<div align="right">
	<input type="button" id="delete" value="삭제" class="btn"/>
</div>

<script type="text/javascript">
document.getElementById("delete").onclick = function() {
	if(confirm("정말 삭제하시겠습니까?")) {
		location.href = "deletePublicNews.do?no=${param.no}";
	}
}
</script>
</c:if>
<!-- 게시글 작성자만 버튼을 클릭할 수 있다. -->



</article>