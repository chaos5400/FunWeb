<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:if test="${paginationResult.totalPage ne 0}">
	<div class="clear"></div>
	<div id="page_control">
		
		<!-- 현재 블럭이 0보다 크다면 Prev 링크를 보여준다. -->
		<c:if test="${paginationResult.nowBlock gt 0}">
			<a href="${listPage}?page=${paginationResult.beginPage - 1}">Prev</a>
		</c:if>
		<!-- 현재 블럭이 0보다 크다면 Prev 링크를 보여준다. -->
	
		<!-- 페이지 목록을 출력한다. -->
		<c:forEach 	begin="${paginationResult.beginPage}"
					end="${paginationResult.endPage}"
					step="1" 
					varStatus="status">
					
			<!-- 현재 페이지이면 굵게 강조한다. -->
			<c:if test="${status.index eq paginationResult.currentPage}"><b></c:if>
			
			<a href="${listPage}?page=${status.index}">${status.index}</a>
			
			<c:if test="${status.index eq paginationResult.currentPage}"></b></c:if>
			<!-- 현재 페이지이면 굵게 강조한다. -->
		
		</c:forEach>
		<!-- 페이지 목록을 출력한다. -->
		
		<!-- 현재 블럭이 마지막 블럭보다 작다면 Next 링크를 보여준다. -->
		<c:if
			test="${paginationResult.nowBlock lt paginationResult.totalBlock}">
			<a href="${listPage}?page=${paginationResult.endPage + 1}">Next</a>
		</c:if>
		<!-- 현재 블럭이 마지막 블럭보다 작다면 Next 링크를 보여준다. -->
		
	</div>
</c:if>