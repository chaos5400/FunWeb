<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>FunWeb</title>
<link href="${pageContext.request.contextPath}/css/default.css" rel="stylesheet" type="text/css">
<link href="${pageContext.request.contextPath}/css/front.css" rel="stylesheet" type="text/css">

<!--[if lt IE 9]>
<script src="http://ie7-js.googlecode.com/svn/version/2.1(beta4)/IE9.js" type="text/javascript"></script>
<script src="http://ie7-js.googlecode.com/svn/version/2.1(beta4)/ie7-squish.js" type="text/javascript"></script>
<script src="http://html5shim.googlecode.com/svn/trunk/html5.js" type="text/javascript"></script>
<![endif]-->

<!--[if IE 6]>
 <script src="script/DD_belatedPNG_0.0.8a.js"></script>
 <script>
   /* EXAMPLE */
   DD_belatedPNG.fix('#wrap');
   DD_belatedPNG.fix('#main_img');   

 </script>
 <![endif]-->


</head>
<body>
	<div id="wrap">
		<!-- 헤더파일들어가는 곳 -->
		<jsp:include page="/inc/header.jsp" />
		<!-- 헤더파일들어가는 곳 -->
		<!-- 메인이미지 들어가는곳 -->
		<div class="clear"></div>
		<div id="main_img">
			<img src="${pageContext.request.contextPath}/images/main_img.jpg" width="971" height="282">
		</div>
		<!-- 메인이미지 들어가는곳 -->
		<!-- 메인 콘텐츠 들어가는 곳 -->
		<article id="front">
			<div id="solution">
				<div id="hosting">
					<h3>Web Hosting Solution</h3>
					<p>Lorem impsun Lorem impsunLorem impsunLorem impsunLorem
						impsunLorem impsunLorem impsunLorem impsunLorem impsunLorem
						impsun....</p>
				</div>
				<div id="security">
					<h3>Web Security Solution</h3>
					<p>Lorem impsun Lorem impsunLorem impsunLorem impsunLorem
						impsunLorem impsunLorem impsunLorem impsunLorem impsunLorem
						impsun....</p>
				</div>
				<div id="payment">
					<h3>Web Payment Solution</h3>
					<p>Lorem impsun Lorem impsunLorem impsunLorem impsunLorem
						impsunLorem impsunLorem impsunLorem impsunLorem impsunLorem
						impsun....</p>
				</div>
			</div>
			<div class="clear"></div>
			<div id="sec_news">
				<h3>
					<span class="orange">Security</span> News
				</h3>
				<dl>
					<dt>Vivamus id ligula....</dt>
					<dd>Proin quis ante Proin quis anteProin quis anteProin quis
						anteProin quis anteProin quis ante......</dd>
				</dl>
				<dl>
					<dt>Vivamus id ligula....</dt>
					<dd>Proin quis ante Proin quis anteProin quis anteProin quis
						anteProin quis anteProin quis ante......</dd>
				</dl>
			</div>
			
			
			
			<!-- News & Notice 를 출력한다. -->
			<div id="news_notice">
				<h3 class="brown">News &amp; Notice</h3>
				<c:if test="${news_notice ne null}">
				<table>
					<c:forEach items="${news_notice}" var="list">
					<tr>
						<td class="contxt"><a href="${list.readPage}?no=${list.no}">${list.title}</a></td>
						<td>${list.date}</td>
					</tr>
					</c:forEach>
				</table>
				</c:if>
			</div>
			<!-- News & Notice 를 출력한다. -->
			
			
			
		</article>
		<!-- 메인 콘텐츠 들어가는 곳 -->
		<div class="clear"></div>
		<!-- 푸터 들어가는 곳 -->
		<jsp:include page="/inc/bottom.jsp" />
		<!-- 푸터 들어가는 곳 -->
	</div>
</body>
</html>