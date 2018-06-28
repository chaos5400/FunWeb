<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<link href="${pageContext.request.contextPath}/css/subpage.css?v=1" rel="stylesheet" type="text/css">
<div align="right"><input type="button" id="write" value="글쓰기" class="btn"/></div><br/>

<script type="text/javascript">
// 글쓰기 버튼 클릭시 이벤트 발생!
$('#write').click(function(){
	$(location).attr('href', '${writeDo}');
});
</script>