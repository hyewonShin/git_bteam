<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>${category eq 'cu' ? '고객관리' : (category eq 'my' ? 'MyPet' :(category eq 'si' ? '사이트소개' :(category eq 'fe' ? '반려동물' : (category eq 'co' ? '자유게시판' : (category eq 'qa' ? 'QnA' : (category eq 'join' ? '회원가입' : '')))))) } ${empty category ? '' : ' : '  } WithPet</title>
<link rel="icon" type="image/x-icon" href="img/withpet.ico">
<!--fontawesome  -->
<script type="text/javascript" src="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/5.13.1/js/all.min.js"></script>
</head>
<body>
<div><tiles:insertAttribute name="header" /></div>
<div style="overflow: hidden;">
	<div style="width: 20%; float: left;"><tiles:insertAttribute name="left" /></div>
	<div style="width: 60%; float: left;"><tiles:insertAttribute name="content" /></div>
</div>
<div><tiles:insertAttribute name="footer" /></div>
</body>

</html>