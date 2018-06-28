<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>정보 수정</title>
<link href="${pageContext.request.contextPath}/css/default.css" rel="stylesheet" type="text/css">
<link href="${pageContext.request.contextPath}/css/subpage.css" rel="stylesheet" type="text/css">
<script src="${pageContext.request.contextPath}/script/join.js"></script>
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
			<h1>비밀번호 변경</h1>
			<form action="modifyPass_proc.do" id="join" method="post">
				<fieldset>
					<label for="originPass">기존 비밀번호 입력</label> <input type="password" name="originPass" id="originPass"><br>
					<span id="errorMsgPass"></span><br>
					<label for="newPass">새 비밀번호 입력</label>  <input type="password" name="newPass" id="newPass"><br>
					<span id="errorMsgPass2"></span><br>
					<label for="newPass2">새 비밀번호 확인</label> <input type="password" id="newPass2"><br>
					<span id="errorMsgPass3"></span>
				</fieldset>
				<div class="clear"></div>
				<div id="buttons">
					<input type="button" value="비밀번호 변경" class="submit" id="submit_proc"> 
					<input type="button" value="취소" class="cancel" id="cancel">
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
<script type="text/javascript">

var arraysForRegister = [
    ["originPass", "errorMsgPass", null],													// originPass 태그에 대한 이벤트를 등록한다.	
	["newPass", "errorMsgPass2", /^[A-Za-z](?=.*[a-zA-Z])(?=.*\d{3,})(?=.*\W).{5,19}$/],	// newPass 태그에 대한 이벤트를 등록한다.
	["newPass2", "errorMsgPass3", null]														// newPass2 태그에 대한 이벤트를 등록한다.
];

var emptyErrorMsg = "필수 입력란입니다.";

var invalidErrorMsg = [
	[ "newPass", "비밀번호는 영문자로 시작하여야 하며, 영대소문자와 3개 이상의 숫자와 1개 이상의 특수문자를 포함하여야 합니다. "
	+ "6~20자 사이로 입력하여야 합니다." ]
]

var submitMsg = "비밀번호를 변경하시겠습니까?";

var cancelMsg = "비밀번호를 변경을 취소하시겠습니까?";


var retypePass = [ "newPass", "newPass2" ];
var retypePassErrorMsg = "비밀번호가 일치하지 않습니다. 다시 입력해 주십시오.";


	/* Register EVENT */
	registerEventByArray(arraysForRegister);	
	
	/* SET EmptyErrorMessage */
	setEmptyErrorMsgAll(emptyErrorMsg);
	
	/* SET InvalidErrorMessage */
	setInvalidErrorMsgByArray(invalidErrorMsg);
	
	/* Bind Retype Tags */
	bindRetypeTag(retypePass, retypePassErrorMsg);
	
	/* Register Submit Event */
	registerSubmitEvent("submit_proc", "join", submitMsg);
	
	/* Register Cancel Event */
	registerCancelEvent("cancel", "info.do", cancelMsg);

</script>
</body>
</html>