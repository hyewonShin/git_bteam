<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<left style="border-bottom:1px solid #ccc; padding:15px 0; text-align:center;">
<div class="category" style="width: 200px; margin: 0px 0px 0px calc(100% - 200px);">
	<!-- 카테고리 siteinfo 누를시 -->
	<c:if test="${category eq 'si'}">
		<div class="title">
			<ul>
				<li >사이트 소개</li>
				<li ><a>사이트 소개</a></li>
				<li><a>임원진 소개</a></li>
				<li><a>오시는길</a></li>
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
				<li ${conum eq 'freeboard' ?  'class="left_on"' : ''} ><a href="freeboard_list.co">자유게시판</a></li>
				<li ${conum eq 'sale' ?  'class="left_on"' : ''}><a href="sale.co">반려동물 분양</a></li>
				<li ${conum eq 'marry' ?  'class="left_on"' : ''}><a href="marry.co">반려동물 연애</a></li>
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
				<li><a>반려동물 심리테스트 </a></li>
			</ul>
		</div>
	</c:if>

	<!-- 카테고리 QnA누를시 -->
	<c:if test="${category eq 'qa'}">
		<div class="title" >
			<ul>
				<li>Q&nbsp;n&nbsp;A</li>
				<li ${qanum eq 'list' ?  'class="left_on"' : ''} ><a href="list.qa" >사이트 문의</a></li>
				<li ${qanum eq 'faq' ?  'class="left_on"' : ''} ><a href="faq.qa" >전문가 QnA</a></li>
			</ul>
		</div>
	</c:if>
	
	<!-- 카테고리 mypage 누를시 -->
	<c:if test="${category eq 'pa'}">
		<div class="title">
			<ul>
				<li>마이페이지</li>
				<li><a>내 정보 </a></li>
				<li><a>내가 쓴글</a></li>
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
				<li><a>내 동물 관리</a></li>
				<li><a>운동기록 </a></li>
				<li><a>검진기록 </a></li>
			</ul>
		</div>
	</c:if>
</div>
</left>

<style>
	/* .title ul li:hover{ background-color: #ffa241; color: white; } */
	left .category .title ul li a { display:block; padding: 13px 10px; text-align: right; }
	
	
	.title ul li:hover:not(:first-child), .left_on {
		background-color: #ffa241; 
		font-size: 1.2em;
		color: #FFFFFF;
		border-radius: 0 20px 20px 0; 
	}
	.left_on a, .title ul li a:hover { color: #FFFFFF; }
	
	.title ul li:hover{
		background-color: #ffa241; 
		color: #FFFFFF;
		cursor: pointer;
	}

	.title ul li:first-child {
		border-bottom: 1px solid #ccc;
		font-size: 1.5em;
		font-weight: bold;
		text-align: center;
	}
	.title ul li{
		/* margin: 0 auto; */
	}
	
	
</style>

<script>
function onClick(){
	$('.title ul li').click(function(){
		$(this).css('background-color','#ffa241');
		$(this).css('color','white');
		$(this).css('cursor','pointer');
	});
}
</script>