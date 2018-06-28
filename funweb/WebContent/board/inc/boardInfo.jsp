<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<tr>
	<td align=left>Total : ${paginationResult.totalRecord} Articles( 
		<font color=red> ${paginationResult.currentPage} / ${paginationResult.totalPage} Pages </font>)
	</td>
</tr>