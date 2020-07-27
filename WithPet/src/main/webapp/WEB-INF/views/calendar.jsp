<%@ page language="java" contentType="text/html; charset=EUC-KR"
    pageEncoding="EUC-KR"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html lang="ko">
<%-- lang="ko" → 시각장애인이 이 페이지를 볼 때 주언어가 명시되어 있으면 해당언어에 알맞게
번역(점자, 소리)되어 올바른 해석으로 정보를 전달할 수 있게 된다  --%>
<head>
<meta charset="EUC-KR">
<title>캘린더</title>
<script type="text/javascript">

</script>

<style type="text/css">


</style>
</head>
<body>
<form name="calendar" id="calendar" method="post">
	<div class="calendar">
	
		<!-- 날짜 네비게이션 -->
		<div class="navigation">
			
			<a class="before_after_year" href="calendar.do?year=${todya_info.search_year-1}
						&month=${today_info.search_month-1 }">
						&lt;&lt; <%-- 이전해로 가게하는 a태그 --%>
			</a>
			
			<a class="before_after_month" href="calendar.do?year=${todya_info.search_year}
						&month=${today_info.before_month }">
						&lt;&lt; <%-- 이전달로 가게하는 a태그 --%>
			</a>
			
			<span class="this_month">
				&nbsp;${today_info.search_year }.
				<c:if test="${today_info.search_month<10 }">0</c:if>${today_info.search_month }
			</span>
			
			<a class="before_after_month" 
				href="calendar.do?year=${today_info.after_year }&month=${today_info.after_month}">
				&gt; <%-- 다음달로 가게하는 a태그 --%>
			</a>
			
			<a class="before_after_year" 
				href="calendar.do?year=${today_info.search_year+1 }&month=${today_info.search_month-1}">
				&gt;&gt; <!-- 다음해로 가게 하는 a태그 -->	
			</a>
		</div>
		
		<table class="calendar_body">
		
			<thead>
				<tr>
					<td class="day sun">일</td>
					<td class="day">월</td>
					<td class="day">화</td>
					<td class="day">수</td>
					<td class="day">목</td>
					<td class="day">금</td>
					<td class="day sat">토</td>
				</tr>
			</thead>
		
			<tr>
				<c:forEach var="dateList" items="${dateList }" varStatus="date_status"></c:forEach>
				<%-- <c:foreach items="${리스트가 받아올 배열이름}" var="for문 내부에서 사용할 변수" varStatus="상태용 변수"> --%>
					<c:choose> 
					<%-- <c:choose><c:choose> 태그는 조건에 따른 여러곳으로 분기 가능하고, 조건이 맞은 것이 없을 경우 기본 분기를 제공할 수 있습니다. --%>
					<%-- <c:if></c:if> 태그는 조건이 맞으면 작동하며 다른 곳으로 분기가 불가능 하다. --%>
					<%-- <c:when></c:when>과 <c:otherwise></c:otherwise>로 분기가 가능하다 --%>
						<c:when test="${dateList.value == 'today'}">
							<td class="today">
								<div class="date">
									${dateList.date }
								</div>
								<div>
								
								</div>
							</td>
						</c:when>
						
						<c:when test="${date_status.index%7==6}">
							<td class="sat_day">
								<div class="sat">
									${dateList.date}
								</div>
								<div>
							
								</div>
							</td>
						</c:when>
						<c:when test="${date_status.index%7==0}">
			</tr>
			<tr>
				<td class="sun_day">
					<div class="sun">
						${dateList.date }
					</div>
					<div>
						
					</div>
				</td>
						</c:when>
						
						<c:otherwise>
				<td class="normal_day">
					<div class="date">
						${dateList.date }
					</div>
					<div>
					
					</div>
				</td>
						</c:otherwise>
					</c:choose>
			</tr>
		</table>
	
	</div>
</form>
</body>
</html>