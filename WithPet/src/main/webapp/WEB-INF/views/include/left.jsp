<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<div class="category">
	<!-- 카테고리 siteinfo 누를시 -->
	<c:if test="${category eq 'si'}">
		<ul>
			<li><a>사이트 소개 </a></li>
			<li><a>사이트 연혁</a></li>
			<li><a>오시는길(?)</a></li>
		</ul>
	</c:if>
	
	<!-- 카테고리 customer 누를시 -->
	<c:if test="${login_info.id eq 'admmin' }">
		<c:if test="${category eq 'cu'}">
			<ul>
				<li><a>회원관리</a></li>
				<li><a>사이트 문의</a></li>
			</ul>
		</c:if>
	</c:if>
	
	<!-- 카테고리 community 누를시 -->
	<c:if test="${category eq 'co'}">
		<ul>
			<li><a>자유게시판</a></li>
			<li><a>반려동물 분양</a></li>
			<li><a>반려동물 연애</a></li>
		</ul>
	</c:if>
	
	<!-- 카테고리 feature 누를시 -->
	<c:if test="${category eq 'fe'}">
		<ul>
			<li><a>반려동물 특징</a></li>
			<li><a>반려동물 주의점 </a></li>
		</ul>
	</c:if>

	<!-- 카테고리 QnA누를시 -->
	<c:if test="${category eq 'qa'}">
		<ul>
			<li><a>전문가 QnA</a></li>
			<li><a>사이트 문의</a></li>
		</ul>
	</c:if>
	
	<!-- 카테고리 mypage 누를시 -->
	<c:if test="${category eq 'pa'}">
		<ul>
			<li><a>내 정보 </a></li>
			<li><a>수정</a></li>
			<li><a>탈퇴</a></li>
		</ul>
	</c:if>
	
	<!-- 카테고리 mypet 누를시 -->
	<c:if test="${category eq 'my'}">
		<ul>
			<li><a>내 동물정보 </a></li>
			<li><a>정보 수정(추가및 삭제)</a></li>
		</ul>
	</c:if>
</div>


<style>


</style>