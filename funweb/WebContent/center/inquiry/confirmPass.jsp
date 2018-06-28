<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!-- 게시글 패스워드 확인 -->
<article>
	<h1>게시글 비밀번호 확인</h1>
	<form action="inquiryPassCheck.do?no=${param.no}&page=${param.page}" method="post">
		비밀번호 입력 : <input type="password" name="pass"/>
		<input type="button" value="비밀번호 확인" onclick="confirmPassword(this.form);"/>
	</form>
	<div align=right>
	<br><input type="button" value="목록으로" id="list" />
	</div>
</article>
<!-- 게시글 패스워드 확인 -->

<script type="text/javascript">
document.getElementById("list").onclick = function() {
	location.href = "inquiry.do?page=${param.page}";
}

function confirmPassword(form) {
	if(document.getElementsByName("pass")[0].value.length < 1) {
		alert("패스워드를 입력하십시오.");
		return;
	} 
	
	form.submit(); // 패스워드 확인 작업 수행 후 일치한다면 게시글 읽기 페이지 이동
}
</script>