<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<footer>
	<hr>
	<div id="copy">
		All contents Copyright 2011 FunWeb 2011 FunWeb Inc. all rights
		reserved<br> Contact mail:funweb@funwebbiz.com Tel +82 64 123
		4315 Fax +82 64 123 4321
	</div>
	<div id="social">
		<img src="${pageContext.request.contextPath}/images/facebook.gif" width="33" height="33"
			alt="Facebook"> <img src="${pageContext.request.contextPath}/images/twitter.gif" width="34"
			height="34" alt="Twitter">
	</div>
</footer>


<c:if test="${param.message ne null}">
<script type="text/javascript">

window.onload = function() {
	if (performance.navigation.type != 1) {
		alert(changeBrToNewLine('${param.message}'));
		function changeBrToNewLine(msg) {
			return msg.replace(/<br>/gi, '\n');
		}
	}
}
</script>
</c:if>


<c:if test="${message ne null}">
<script type="text/javascript">
window.onload = function() {
	if (performance.navigation.type != 1) {
		alert(changeBrToNewLine('${message}'));
		function changeBrToNewLine(msg) {
			return msg.replace(/<br>/gi, '\n');
		}
	}
}
</script>
</c:if>

