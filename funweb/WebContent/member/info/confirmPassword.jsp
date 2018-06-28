<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>패스워드 재확인</title>
<link href="${pageContext.request.contextPath}/css/default.css" rel="stylesheet" type="text/css">
<link href="${pageContext.request.contextPath}/css/subpage.css" rel="stylesheet" type="text/css">
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
		<jsp:include page="/member/info/inc/submenu.jsp" />
		<!-- 왼쪽메뉴 -->
		<!-- 본문내용 -->
		<article>
			<h1>비밀번호 재확인</h1>
			<form action="deleteAccount_proc.do" method="post">
				비밀번호 입력 : <input type="password" name="pass"/>
				<input type="button" value="회원 탈퇴" onclick="confirmPassword(this.form);"/>
			</form>
		</article>
		<!-- 본문내용 -->
		<!-- 본문들어가는 곳 -->

		<div class="clear"></div>
		<!-- 푸터들어가는 곳 -->
		<jsp:include page="/inc/bottom.jsp" />
		<!-- 푸터들어가는 곳 -->
	</div>
<script type="text/javascript">
function confirmPassword(form) {
	if(document.getElementsByName("pass")[0].value.length < 1) {
		alert("패스워드를 입력하십시오.");
		return;
	} else {
		if(confirm("정말 회원 탈퇴 하시겠습니까?")) {
			form.submit(); // 회원 탈퇴 진행
		}
	}
}
</script>
</body>
</html>