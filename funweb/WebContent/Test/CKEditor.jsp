<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<script src="${pageContext.request.contextPath}/ckeditor/ckeditor.js"></script>
<title>Insert title here</title>
</head>
<body>
<div style='width:670px'>
	<textarea class='ckeditor' name='ckeditor' id="ckeditor"></textarea>
</div>
<script>
	CKEDITOR.replace('ckeditor', {
		filebrowserImageUploadUrl: 'imageUpload.do'
	});
</script>
</body>
</html>