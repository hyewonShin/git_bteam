<%@ page language="java" contentType="text/html; charset=EUC-KR"
    pageEncoding="EUC-KR"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html lang="ko">
<%-- lang="ko" �� �ð�������� �� �������� �� �� �־� ��õǾ� ������ �ش�� �˸°�
����(����, �Ҹ�)�Ǿ� �ùٸ� �ؼ����� ������ ������ �� �ְ� �ȴ�  --%>
<head>
<meta charset="EUC-KR">
<title>Ķ����</title>
<script type="text/javascript">

</script>

<style type="text/css">


</style>
</head>
<body>
<form name="calendar" id="calendar" method="post">
	<div class="calendar">
	
		<!-- ��¥ �׺���̼� -->
		<div class="navigation">
			
			<a class="before_after_year" href="calendar.do?year=${todya_info.search_year-1}
						&month=${today_info.search_month-1 }">
						&lt;&lt; <%-- �����ط� �����ϴ� a�±� --%>
			</a>
			
			<a class="before_after_month" href="calendar.do?year=${todya_info.search_year}
						&month=${today_info.before_month }">
						&lt;&lt; <%-- �����޷� �����ϴ� a�±� --%>
			</a>
			
			<span class="this_month">
				&nbsp;${today_info.search_year }.
				<c:if test="${today_info.search_month<10 }">0</c:if>${today_info.search_month }
			</span>
			
			<a class="before_after_month" 
				href="calendar.do?year=${today_info.after_year }&month=${today_info.after_month}">
				&gt; <%-- �����޷� �����ϴ� a�±� --%>
			</a>
			
			<a class="before_after_year" 
				href="calendar.do?year=${today_info.search_year+1 }&month=${today_info.search_month-1}">
				&gt;&gt; <!-- �����ط� ���� �ϴ� a�±� -->	
			</a>
		</div>
		
		<table class="calendar_body">
		
			<thead>
				<tr>
					<td class="day sun">��</td>
					<td class="day">��</td>
					<td class="day">ȭ</td>
					<td class="day">��</td>
					<td class="day">��</td>
					<td class="day">��</td>
					<td class="day sat">��</td>
				</tr>
			</thead>
		
			<tr>
				<c:forEach var="dateList" items="${dateList }" varStatus="date_status"></c:forEach>
				<%-- <c:foreach items="${����Ʈ�� �޾ƿ� �迭�̸�}" var="for�� ���ο��� ����� ����" varStatus="���¿� ����"> --%>
					<c:choose> 
					<%-- <c:choose><c:choose> �±״� ���ǿ� ���� ���������� �б� �����ϰ�, ������ ���� ���� ���� ��� �⺻ �б⸦ ������ �� �ֽ��ϴ�. --%>
					<%-- <c:if></c:if> �±״� ������ ������ �۵��ϸ� �ٸ� ������ �бⰡ �Ұ��� �ϴ�. --%>
					<%-- <c:when></c:when>�� <c:otherwise></c:otherwise>�� �бⰡ �����ϴ� --%>
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