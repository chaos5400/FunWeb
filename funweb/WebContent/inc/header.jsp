<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<header>
	<!-- 로그인 여부에 따라 나뉜다. -->
	<div id="login">
	<c:if test="${sessionScope.userId eq null}"><a href="login.do">login</a> | <a href="join.do">join</a></c:if>
	<c:if test="${sessionScope.userRole eq 'ADMIN'}">${sessionScope.userRole} | </c:if><c:if test="${sessionScope.userId ne null}">
	<a href="#" id="logout">logout</a> | <a href="info.do">${sessionScope.userId}</a>
	<script type="text/javascript">
	/* logout 링크를 누르면 '정말로 로그아웃하시겠습니까?' 확인창을 띄어주고
	      확인 버튼을 클릭하면 logout.do로 이동한다. */
	document.getElementById("logout").onclick = function() {
		if(confirm("정말로 로그아웃하시겠습니까?")) {
			location.href = "logout.do";
		}
	}
	</script></c:if>
	</div>
	<!-- 로그인 여부에 따라 나뉜다. -->
	<div class="clear"></div>
	<!-- 로고들어가는 곳 -->
	<div id="logo">
		<img src="${pageContext.request.contextPath}/images/logo.gif" width="265" height="62" alt="Fun Web">
	</div>
	<!-- 로고들어가는 곳 -->
	<nav id="top_menu">
		<ul>
			<li><a href="index.do">HOME</a></li>
			<li><a href="welcome.do">COMPANY</a></li>
			<li><a href="#">SOLUTIONS</a></li>
			<li><a href="notice.do">CUSTOMER CENTER</a></li>
			<li><a href="#">CONTACT US</a></li>
		</ul>
	</nav>
</header>
