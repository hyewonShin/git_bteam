<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>
<h3>회원가입결과 - ${method }</h3>
성명 : ${name}<br/>
성별 : ${gender}<br/>
이메일 : ${email}<br/><br/>
<hr/>
성명 : ${vo.name}<br/>
성별 : ${vo.gender}<br/>
이메일 : ${vo.email}<br/><br/>
<a href='join'>가입화면으로</a>
</body>
</html>