<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<script type="text/javascript">
function go_submit(f){
	f.action = "joinPathVariable/" + f.name.value 
				+ "/" + f.gender.value + "/" + f.email.value;
}
</script>
</head>
<body>
<h3>회원가입화면</h3>
<form action="joinRequest" method="post">
<table border="1">
<tr>
	<th>성명</th>
	<td><input type="text" name="name"/></td>
</tr>
<tr>
	<th>성별</th>
	<td>
		<input type="radio" name="gender" value="여" checked="checked"/>여
		<input type="radio" name="gender" value="남"/>남
	</td>
<tr>
	<th>이메일</th>
	<td><input type="text" name="email"/></td>
</tr>
</table>
<br/>
<input type="submit" value="HttpServletRequest"/>
<input type="submit" value="@RequestParam" onclick="action='joinRequestParam'"/>
<input type="submit" value="데이터객체" onclick="action='joinDataObject'"/>
<input type="submit" value="@PathVariable" onclick="go_submit(this.form)"/>
</form>
</body>
</html>