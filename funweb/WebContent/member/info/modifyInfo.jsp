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
		<jsp:include page="/member/info/inc/submenu.jsp" />
		<!-- 왼쪽메뉴 -->
		<!-- 본문내용 -->
		<article>
			<h1>정보 수정</h1>
			<form action="modifyInfo_proc.do" id="join" method="post">
				<fieldset>
					<legend>Basic Info</legend>
					
					<label for="id">아이디</label><br><br>
					${sessionScope.userId}<br><br><br>
					<label for="fname">이름</label> 
					<label for="lname">성</label><br><br>
					<input type="text" name="fname" id="fname" maxlength="20" value="${accountInfo.firstName}"> 
					<input type="text" name="lname" id="lname" maxlength="20" value="${accountInfo.lastName}"><br>
					<span id="errorMsgName"></span><br>
					
				</fieldset>
				<fieldset>
					<legend>Optional</legend>
					<label>Address</label><br><br>
					<jsp:include page="../post.jsp"/><br>
					<label for="phone">Phone Number</label> 
					<input type="text" name="phone" id="phone" maxlength="20" value="${accountInfo.phoneNumber}"><br>
					<label for="mobile">Mobile Phone Number</label> 
					<input type="text" name="mobile" id="mobile" maxlength="20" value="${accountInfo.mobilePhoneNumber}">
				</fieldset>
				<div class="clear"></div>
				<div id="buttons">
					<input type="button" value="정보 수정" class="submit" id="submit_proc">
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
 	["fname", "errorMsgName", null],		// First Name 태그에 대한 이벤트를 등록한다.
 	["lname", "errorMsgName", null]			// Last Name 태그에 대한 이벤트를 등록한다.
];

var emptyErrorMsg = "필수 입력란입니다.";

var submitMsg = "정보를 변경하시겠습니까?"

var cancelMsg = "정보 수정을 취소하시겠습니까?";

var bindingTags = [ 
[ "fname", "lname" ],
];
 
/* Register EVENT */
registerEventByArray(arraysForRegister);	

/* Bind fname and lname */
bindTags(bindingTags);

/* SET EmptyErrorMessage */
setEmptyErrorMsgAll(emptyErrorMsg);

/* Register Submit Event */
registerSubmitEvent("submit_proc", "join", submitMsg);

/* Register Cancel Event */
registerCancelEvent("cancel", "info.do", cancelMsg);

</script>
</body>
</html>