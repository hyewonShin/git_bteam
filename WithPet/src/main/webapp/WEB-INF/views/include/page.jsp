<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<div class="page_list">
<c:if test="${page.curBlock gt 1 }">
	<a class="page_first" onclick="go_page(1)">처음</a>
	<a class="page_prev" onclick="go_page(${page.beginPage - page.blockPage})">이전</a>
</c:if>

<c:forEach var="no" begin="${page.beginPage }" end="${page.endPage }" step="1">
	<c:if test="${no eq page.curPage }">
		<span class="page_on">[${no }]</span>
	</c:if>
	<c:if test="${no ne page.curPage }">
		<a class="page_off" onclick="go_page(${no})">[${no }]</a>
	</c:if>
</c:forEach>
<c:if test="${page.curBlock lt page.totalBlock }">
	<a class="page_next" onclick="go_page(${page.endPage+1})">다음</a>
	<a class="page_last" onclick="go_page(${page.totalPage})">마지막</a>
</c:if>
</div>
<style>
.page_on, .page_off{min-width: 22px; padding: 0 5px 2px;}
.page_on, .page_off, .page_next, .page_last, .page_prev, .page_first{display: inline-block; line-height: 30px; margin: 0; }
.page_on{border: 1px solid gray; background-color: gray; color: #fff; font-weight: bold;}
.page_next, .page_last, .page_prev, .page_first{text-indent: -9999999px; border: 1px solid #d0d0d0; width: 30px;}
.page_next{background: url('img/page_next.jpg') center no-repeat;}
.page_last{background: url('img/page_last.jpg') center no-repeat;}
.page_prev{background: url('img/page_prev.jpg') center no-repeat;}
.page_first{background: url('img/page_first.jpg') center no-repeat;}
</style>

<script>
function go_page(no){
	$('[name=curPage]').val(no);
	$('[name=keyword]').val('${page.keyword}');
	$('#list').submit();
}
</script>