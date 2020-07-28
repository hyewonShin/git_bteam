<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<link rel="stylesheet" type="text/css" href="css/common.css?v=<%=new java.util.Date().getTime()%>">
<!--모든주소에서 자바 스크립트 가능하게함  -->
<script type="text/javascript" src="https://code.jquery.com/jquery-3.5.1.min.js"></script>

<style>
header ul, header ul li {margin: 0; padding: 0; display: inline;}
header .category {font-size: 18px;}
header .category ul li:not(:first-child) {padding-left: 30px;}
header .category ul li a:hover,
header .category ul li a.active {font-weight: bold; color: #FF7171;}
#userid, #userpw{width: 100px; height: 18px; font-size: 14px;}
header ul li input {display: block;}
</style>


<header style="border-bottom:1px solid #ccc; padding:15px 0; text-align:left;">
	<div class="category" style="margin-left:100px;">
	<ul>
		<li><a href="<c:url value='/'/>"><img src="img/withpet.png" alt="홈으로" width="120"></a></li>
		<c:if test="${login_info.id eq 'admin' }">
			<li><a href="list.cu" ${category eq 'cu' ? 'class="active"' : ''}>고객관리</a></li><!--admin일때만 나오기  -->
		</c:if>
		<li><a href="list.si" ${category eq 'si' ? 'class="active"' : ''}>사이트소개</a></li>
		<li><a href="list.fe" ${category eq 'fe' ? 'class="active"' : ''}>반려동물 특징</a></li>
		<li><a href="list.my" ${category eq 'my' ? 'class="active"' : ''}>내 동물정보</a></li><!-- 로그인시  -->
		<li><a href="freeboard_list.co" ${category eq 'co' ? 'class="active"' : ''}>커뮤니티</a></li>
		<li><a href="list.qa" ${category eq 'qa' ? 'class="active"' : ''}>QnA</a></li>
		<li><a href="list.pa" ${category eq 'pa' ? 'class="active"' : ''}>마이페이지</a></li>
	</ul>
	</div>
	
	<div style="position: absolute; right: 0; top: 25px; margin-right: 100px;">
	
	<!-- 로그인 시 -->
	<c:if test="${!empty login_info }">
		<ul>
			<li>${login_info.name } [${login_info.id }]</li>
			<li><a class="btn-fill" onclick="go_logout()">로그아웃</a></li>
		</ul>	
	</c:if>
	
	<!-- 비 로그인시 -->
	<c:if test="${empty login_info }">
		<ul>
			<li>
				<span style="position: absolute; top: -8px; left: -120px;">
					<!-- <input type="text" id="userid" placeholder="아이디" />
					<input type="password" onkeypress="if(event.keyCode == 13){go_login()}" id="userpw" placeholder="패스워드" /> -->
				</span>
			</li>
			<li><a class="btn-fill" onclick="go_login()">로그인</a></li>
			<li><a class="btn-fill" href="member">회원가입</a></li>
		</ul>
	</c:if>
	</div>
	
	
</header>

<!-- ajax통신 -->
<script>
function go_login(){
	if($('#userid').val()==''){
		alert('아이디를 입력하세요!');
		$('#userid').focus();
		return;
	}else if($('#userpw').val()==''){
		alert('비밀번호를 입력하세요!');
		$('#userpw').focus();
		return;
	}	

	$.ajax({
		type : 'post',
		url : 'login',
		data : {id:$('#userid').val(),pw:$('#userpw').val()},
		success : function( data ){
			alert(data);
			if(data == 'true'){
				location.reload();
			}else{
				alert('아이디나 비밀번호가 일치하지 않습니다!');
				$('#userid').focus();
			}
		},
		error : function(req, text){
			alert(text + ' : ' + req.staus);
		}

	});
}

function go_logout(){
	$.ajax({
		type : 'post',
		url : 'logout',
		success : function(){
			location.reload();
		},
		error : function(req, text){
			alert(text + ' : ' + req.staus);
		}
	});
}

</script>
