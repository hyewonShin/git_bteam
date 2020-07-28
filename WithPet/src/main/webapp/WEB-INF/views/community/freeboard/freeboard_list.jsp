<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>
<h3>자유게시판</h3>

<table>
	<tr>
		<th class="w-px60">번호</th>
		<th>제목</th>
		<th class="w-px100">작성자</th>
		<th class="w-px120">작성일자</th>
		<th class="w-px60">첨부파일</th>
	</tr>
	<c:forEach items="${page.list }" var="vo">
		<tr>
			<td>${vo.no }</td>
			<td class="left">${vo.title }</td>
			<td>${vo.name }</td>
			<td>${vo.writedate }</td>
			<td>
				<c:if test="${!empty vo.filename }">
					<img alt="" src="img/attach.png" class="file-img"/>
				</c:if>
			</td>
		</tr>	
	</c:forEach>
</table>
</body>
</html>