<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<left style="border-bottom:1px solid #ccc; padding:15px 0; text-align:center;">
<div class="category">
	<!-- 카테고리 siteinfo 누를시 -->
	<c:if test="${category eq 'si'}">
		<div class="title">
			<ul>
				<li>사이트 소개</li>
				<li><a>사이트 소개 </a></li>
				<li><a>사이트 연혁</a></li>
				<li><a>오시는길(?)</a></li>
			</ul>
		</div>
	</c:if>
	
	<!-- 카테고리 customer 누를시 -->
	<c:if test="${login_info.id eq 'admmin' }">
		<c:if test="${category eq 'cu'}">
			<div class="title">
				<ul>
					<li>고객관리</li>
					<li><a>회원관리</a></li>
					<li><a>사이트 문의</a></li>
				</ul>
			</div>
		</c:if>
	</c:if>
	
	<!-- 카테고리 community 누를시 -->
	<c:if test="${category eq 'co'}">
		<div class="title">
			<ul>
				<li>커뮤니티</li>
				<li><a>자유게시판</a></li>
				<li><a>반려동물 분양</a></li>
				<li><a>반려동물 연애</a></li>
			</ul>
		</div>
	</c:if>
	
	<!-- 카테고리 feature 누를시 -->
	<c:if test="${category eq 'fe'}">
		<div class="title">
			<ul>
				<li>반려동물</li>
				<li><a>반려동물 특징</a></li>
				<li><a>반려동물 주의점 </a></li>
			</ul>
		</div>
	</c:if>

	<!-- 카테고리 QnA누를시 -->
	<c:if test="${category eq 'qa'}">
		<div class="title" >
			<ul>
				<li>Q&nbsp;n&nbsp;A</li>
				<li><a href="list.qa" >사이트 문의</a></li>
				<li><a href="faq.qa" >전문가 QnA</a></li>
			</ul>
		</div>
	</c:if>
	
	<!-- 카테고리 mypage 누를시 -->
	<c:if test="${category eq 'pa'}">
		<div class="title">
			<ul>
				<li>마이페이지</li>
				<li><a>내 정보 </a></li>
				<li><a>수정</a></li>
				<li><a>탈퇴</a></li>
			</ul>
		</div>
	</c:if>
	
	<!-- 카테고리 mypet 누를시 -->
	<c:if test="${category eq 'my'}">
		<div class="title" >
			<ul>
				<li>내동물정보</li>
				<li><a>내 동물정보 </a></li>
				<li><a>운동기록 </a></li>
				<li><a>검진기록 </a></li>
				<li><a>정보 수정(추가및 삭제)</a></li>
			</ul>
		</div>
	</c:if>
</div>
</left>

<style>
	/* .title ul li:hover{ background-color: #ffa241; color: white; } */
	.title {}
	.title ul li:hover:not(:first-child){
		background-color: #ffa241; 
		color: white;
		font-size: 1.2em
	}
	.title ul li:first-child {
		border-bottom: 1px solid #ccc;
		font-size: 1.5em;
		font-weight: bold;
		text-align: center;
	}
	.title ul li{
		width: 250px;
		height: 50px;
		margin: 0 auto;
		line-height: 50px;
	}
	/* .title ul li a{font-weight: bold; color: white; background-color:#ffa241; } */
	
</style>

<script>
function clicked(){
	$('.title ul li a').click(function(){
		$(this).css('background-color',"#ffa241");
		$(this).css('color',"white");
	});
}
</script>