<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<% request.setCharacterEncoding("UTF-8"); %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>회원가입</title>
<link href="${pageContext.request.contextPath}/css/default.css" rel="stylesheet" type="text/css">
<link href="${pageContext.request.contextPath}/css/subpage.css" rel="stylesheet" type="text/css">
<script src="http://code.jquery.com/jquery-1.10.2.js"></script>
<script src="${pageContext.request.contextPath}/script/join.js?v=231"></script>
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
<style type="text/css">
form#join #fname {
	width: 135px
}

form#join #lname {
	width: 135px
}
</style>
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
			<h1>Join Us</h1>
			<form action="join_proc.do" id="join" method="post">
				<fieldset>
					<legend>Basic Info</legend>
					
					<label for="id">User ID(E-Mail)</label><br><br>
					<input type="text" name="id" id="id" maxlength="100"><br>
					<span id="errorMsgId"></span><br>
					
					<label for="pass">Password</label><br><br>
					<input type="password" name="pass" id="pass" maxlength="20"><br>
					<span id="errorMsgPass"></span><br>
					
					<label for="pass2">Retype Password</label><br><br>
					<input type="password" id="pass2" maxlength="20"><br>
					<span id="errorMsgPass2"></span><br>
					
					<label for="fname">Fitst Name</label> 
					<label for="lname">Last Name</label><br><br>
					<input type="text" name="fname" id="fname" maxlength="20"> 
					<input type="text" name="lname" id="lname" maxlength="20"><br>
					<span id="errorMsgName"></span><br>
					
				</fieldset>
				<fieldset>
					<legend>Optional</legend>
					<label>Address</label><br><br>
					<!-- 우편번호 검색 서비스 -->
					<jsp:include page="post.jsp" /><br><br>
					<!-- 우편번호 검색 서비스 -->
					<label for="phone">Phone Number</label> <input type="text" name="phone" id="phone" maxlength="20"><br>
					<label for="mobile">Mobile Phone Number</label> <input type="text" name="mobile" id="mobile" maxlength="20">
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

<script type="text/javascript">

/*********************************************************
 * 
 * 회원가입 입력 폼에 대한 필수 태그들에 대한 이벤트를 등록하기 위해
 * 해당 input 태그들의 id와 에러메시지를 출력할 span태그의 id
 * 그리고 유효성 검사를 수행할 정규표현식을 2차원 배열의 형태로 저장한다.
 *
 *********************************************************/
var arraysForRegister = [
 	["id", "errorMsgId", /^[A-Za-z0-9]+@([A-Za-z0-9]+\.)+[A-Za-z]{2,4}$/],        		// User ID 태그에 대한 이벤트를 등록한다.                 
 	["pass", "errorMsgPass", /^[A-Za-z](?=.*[a-zA-Z])(?=.*\d{3,})(?=.*\W).{5,19}$/],	// Password 태그에 대한 이벤트를 등록한다.
 	["pass2", "errorMsgPass2", null],													// Retype Password 태그에 대한 이벤트를 등록한다.
 	["fname", "errorMsgName", null],													// First Name 태그에 대한 이벤트를 등록한다.
 	["lname", "errorMsgName", null]														// Last Name 태그에 대한 이벤트를 등록한다.
];





/*********************************************************
 * 
 * 회원가입 폼 작성시 에러메시지 출력을 위한 에러메시지를 저장한다.
 *
 *********************************************************/
 var emptyErrorMsg = "필수 입력란입니다.";
 
 var idInvalidErrorMsg = [
 	[ "id", "올바른 이메일 주소를 입력하여 주십시오." ],
 	[ "pass", "비밀번호는 영문자로 시작하여야 하며, 영대소문자와 3개 이상의 숫자와 1개 이상의 특수문자를 포함하여야 합니다. "
 	  + "6~20자 사이로 입력하여야 합니다." ]
 ]
 
 var idDuplicateErrorMsg = "이미 존재하는 ID입니다. 다른 ID를 입력해주세요.";
 
 var idCheckErrorMsg = "중복 체크 도중 알 수 없는 에러가 발생하였습니다. " +
  					   "페이지를 새로고침하여 다시 시도해 보십시오. " + 
  					   "그래도 문제가 계속된다면 관리자에게 연락하십시오.";
 
 var bindingTags = [ 
	[ "fname", "lname" ],
 ];
 
 var retypePass = [ "pass", "pass2" ];
 var retypePassErrorMsg = "비밀번호가 일치하지 않습니다. 다시 입력해 주십시오.";
 
 
 
 
 
 /*********************************************************
  * 
  * 회원가입 폼에 대한 이벤트를 등록하고 에러메시지 등록, 에러메시지 색상,
  * Submit 버튼에 대한 이벤트, Cancel 버튼에 대한 이벤트를 등록한다.
  *
  *********************************************************/
 
 	/* Register EVENT */
 	registerEventByArray(arraysForRegister);	
	
 	/* Register ID Tag For Duplicate Check */
 	registerDuplicateCheckTag("id", "checkSignup.do", idDuplicateErrorMsg, idCheckErrorMsg);
	
 	/* Bind fname and lname */
 	bindTags(bindingTags);
 	
 	/* SET EmptyErrorMessage */
 	setEmptyErrorMsgAll(emptyErrorMsg);
	
 	/* SET InvalidErrorMessage */
	setInvalidErrorMsgByArray(idInvalidErrorMsg);
 	
 	/* Register Submit Event */
 	registerSubmitEvent("submit_proc", "join", "회원가입을 완료하시겠습니까?");
	
 	/* Register Cancel Event */
 	registerCancelEvent("cancel", "index.do", "회원가입을 취소 하시겠습니까?");

 	/* Bind Retype Tags */
 	bindRetypeTag(retypePass, retypePassErrorMsg);
 	
</script>
</body>
</html>