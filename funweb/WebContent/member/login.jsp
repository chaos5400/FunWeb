<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>로그인</title>
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
		<jsp:include page="/member/inc/submenu.jsp"/>
		<!-- 왼쪽메뉴 -->
		<!-- 본문내용 -->
		<article>
			<h1>Login</h1>
			<form action="checkLoginValidation.do" id="join" method="post">
				<fieldset>
					<legend>Login Info</legend>
					<label for="id">User ID</label> <input type="text" name="id" id="id" value="${param.id}">
					<span id="errorMsgId"></span><br>
					<label for="pass">Password</label> <input type="password" name="pass" id="pass">
					<span id="errorMsgPass"></span>
				</fieldset>
				<div class="clear"></div>
				<div id="buttons">
					<input type="button" value="Submit" class="submit" id="submit_proc"> 
					<input type="button" value="Cancel" class="cancel" id="cancel">
				</div>
			</form>
		</article>
		<!-- 본문내용 -->
		<!-- 본문들어가는 곳 -->

		<div class="clear"></div>
		<!-- 푸터들어가는 곳 -->
		<jsp:include page="/inc/bottom.jsp" />
		<!-- 푸터들어가는 곳 -->
	</div>
<script src="${pageContext.request.contextPath}/script/join.js"></script>
<script type="text/javascript">

var arraysForRegister = [
   	["id", "errorMsgId", null],     // User ID 태그에 대한 이벤트를 등록한다.                 
   	["pass", "errorMsgPass", null]	// Password 태그에 대한 이벤트를 등록한다.
];

var idEmptyErrorMsg = "아이디를 입력하여 주십시오.";
var passEmptyErrorMsg = "비밀번호를 입력하여 주십시오."

 	/* Register EVENT */
 	registerEventByArray(arraysForRegister);	

	/* SET EmptyErrorMessage */
	setEmptyErrorMsg("id", idEmptyErrorMsg);
	setEmptyErrorMsg("pass", passEmptyErrorMsg);
	
	/* SET ErrorMessage Color */
	
	/* Register Submit Event */
	registerSubmitEvent("submit_proc", "join");

	/* Register Cancel Event */
	registerCancelEvent("cancel", "index.do");

</script>

<!-- 이미 접속중인 계정이면 -->
<c:if test="${confirmBreakExistUserMsg ne null}">
<script type="text/javascript">
window.onload = function() {
	// 확인창을 띄어 기존 접속을 종료할 것인지 물어본다.
	if(confirm(changeBrToNewLine('${confirmBreakExistUserMsg}'))) {
		// 확인 버튼을 클릭했다면 기존 접속을 종료하고
		// 새로운 세션을 생성하여 접속한다.
		location.href = 'loginProc.do?id=${param.id}';
	}
	
	// 메시지의 <br>태그를 개행 문자 '\n'으로 변환한다.
	// 서버에서는 개행 문자 '\n'을 클라이언트로 보낼 수 없기 때문에 변환하여 사용한다.
	function changeBrToNewLine(msg) {
		return msg.replace(/<br>/gi, '\n');
	}
}
</script>
</c:if>
<!-- 이미 접속중인 계정이면 -->

</body>
</html>