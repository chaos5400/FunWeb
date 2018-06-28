<%@ page language="java" contentType="text/html; charset=UTF-8"	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!-- ckeditor 폼 -->
<script src="${pageContext.request.contextPath}/ckeditor/ckeditor.js"></script>
<form action="${proc}?sequence=${sequence}" method="post" id="register">
	<input type="text" name="title" id="title" maxlength="100" style="width:100%" 
		placeholder=" 제목을 입력하세요. 최소 5자이상 입력하세요."/><br><br>
	<textarea name='content' id="content"></textarea><br>
	패스워드 : <input type="password" name="pass" maxlength="20" style="width:100px"/><br>
	<div id="buttons" align="center">
		<input type="button" id="submit_proc" value="등록" onclick="checkSubmit(this.form);" class="btn">&nbsp;&nbsp;
		<input type="button" id="cancel" value="취소" class="btn">
	</div>
</form>
<!-- ckeditor 폼 -->
<script>
CKEDITOR.replace('content', {
	enterMode : CKEDITOR.ENTER_BR, // Line-Break 시 <p> 태그 대신 <br> 태그를 사용한다.
	filebrowserImageUploadUrl: 'imageUpload.do?sequence=${sequence}' // 이미지 업로드 처리를 할 uri 주소
});

function checkSubmit(f) {
	
	var x = document.getElementById('title').value.length;
	if(x < 5) { // 제목이 5자 미만이라면
		alert('제목을 5자 이상 입력하십시오.');
	 	return;
	}
	
	var ckeData = CKEDITOR.instances.content.getData();
	
	if(ckeData == "") { // 내용을 입력하지 않았으면
		alert('내용을 입력하십시오.');
		return;
	}
	
	var pass = document.getElementsByName('pass')[0].value;
	
	if(pass.length < 4) {
		alert('패스워드를 4자이상 입력하십시오.');
		return;
	}
	
	window.onbeforeunload = null;
	
	// CKEditor에 입력된 내용을 textarea에 쓴 후
	document.getElementById('content').value = ckeData;
	
	// 서버로 전송하여 DB에 저장한다.
	f.submit();
	
}

document.getElementById('cancel').onclick = function() {
	if(confirm('정말 취소 하시겠습니까?')) {
		window.onbeforeunload = null;
		history.back();
	}
}

window.onbeforeunload = function() {
    return "변경사항이 저장되지 않을 수 있습니다.";
};
</script>
