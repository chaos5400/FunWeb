<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>이메일 인증</title>
<link href="${pageContext.request.contextPath}/css/default.css" rel="stylesheet" type="text/css">
<link href="${pageContext.request.contextPath}/css/subpage.css" rel="stylesheet" type="text/css">
<script src="http://code.jquery.com/jquery-1.10.2.js"></script>
<!--[if lt IE 9]>
<script src="http://ie7-js.googlecode.com/svn/version/2.1(beta4)/IE9.js" type="text/javascript"></script>
<script src="http://ie7-js.googlecode.com/svn/version/2.1(beta4)/ie7-squish.js" type="text/javascript"></script>
<script src="http://html5shim.googlecode.com/svn/trunk/html5.js" type="text/javascript"></script>
<![endif]-->
<!--[if IE 6]>
 <script src="../script/DD_belatedPNG_0.0.8a.js"></script>
 <script>
   /* EXAMPLE */
   DD_belatedPNG.fix('#wrap');
   DD_belatedPNG.fix('#main_img');   

 </script>
 <![endif]-->
</head>
<body>
	<div id="wrap">
		<!-- 헤더들어가는 곳 -->
		<jsp:include page="/inc/header.jsp" />
		<!-- 헤더들어가는 곳 -->

		<!-- 본문들어가는 곳 -->
		<!-- 본문메인이미지 -->
		<div id="sub_img_member"></div>
		<!-- 본문메인이미지 -->
		
		<!-- 왼쪽메뉴 -->
		<!-- 왼쪽메뉴 -->
		<!-- 본문내용 -->
		<article>
			<h1>이메일 인증</h1>
			<p> 
			이메일 인증이 완료되지 않았습니다. 계정을 활성화 시키려면 이메일 인증을 완료하여 주십시오.<br>
			인증메일이 오지 않았다면 아래 링크를 클릭하여 인증메일을 재발송 하십시오.<br>
			<a href="#" id="resend">메일 재발송</a><br>	
			</p>
		</article>
		<!-- 본문내용 -->
		<!-- 본문들어가는 곳 -->

		<div class="clear"></div>
		<!-- 푸터들어가는 곳 -->
		<jsp:include page="/inc/bottom.jsp" />
		<!-- 푸터들어가는 곳 -->
	</div>
<script type="text/javascript">
// ajax를 이용하여 메일 재발송 기능을 구현한다.
document.getElementById("resend").onclick = function() {
	$.ajax({
		type: "POST",
		url: "emailresend.do",
		data: {
			"id" : '${param.id}',		// 계정의 id값을 넘겨준다.
		},
		success : function() {
			alert("이메일 발송에는 몇 분이 소요될 수 있습니다.\n만약 몇 분이 지나도 메일이 오지 않는다면 메일 재발송을 실시해 주십시오.");
		},
		error : function() {
			alert("메일 발송에 실패하였습니다. 잠시 후 다시 시도하여 주십시오.");
		}
	});
}
</script>
</body>
</html>