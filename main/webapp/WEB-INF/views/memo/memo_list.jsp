<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>메모장</title>
<%@ include file="../include/header.jsp" %>
<!-- include libraries(jQuery, bootstrap) -->
<link href="http://netdna.bootstrapcdn.com/bootstrap/3.3.5/css/bootstrap.css" rel="stylesheet">
<script src="http://cdnjs.cloudflare.com/ajax/libs/jquery/3.2.1/jquery.js"></script> 
<script src="http://netdna.bootstrapcdn.com/bootstrap/3.3.5/js/bootstrap.js"></script> 

<!-- include summernote css/js -->
<link href="http://cdnjs.cloudflare.com/ajax/libs/summernote/0.8.12/summernote.css" rel="stylesheet">
<script src="http://cdnjs.cloudflare.com/ajax/libs/summernote/0.8.12/summernote.js"></script>
<script type="text/javascript">

$(document).ready(function() {
	$("#memo").summernote({
		height : 150,
		width : 600
	});
});


function memo_view(idx){
	location.href="${path}/memo/view/"+idx;
	//REST(Representative Statv Transfer)방식, 또는 RESTful한 URI방식
	//게시물로 고유한 주소값을 가진다. 즉 개시물당 주소가 1개.
	//따라서 이런 방식은 스프링에서 @PathVariable 을 적용해야..
}

</script>
</head>
<body>
<%@ include file="../include/menu.jsp" %>

<form method="post" action="${path }/memo/insert.do">
	<label for="writer">이름</label>&nbsp;&nbsp;
	<input name="writer" id="writer" size="10"><br>
	<label for="memo">메모</label>&nbsp;&nbsp;
	<!-- <input name="memo" id="memo" size="50"> -->
	<textarea id="memo" name="memo" cols="50" rows="3"></textarea><br>
	<input type="submit" value="작성!">
</form>
<br><hr><br>
<table>
	<tr>
		<th>번호</th>
		<th>이름</th>
		<th>메모</th>
		<th>날짜</th>
	</tr>
	<c:forEach var="row" items="${list }">
		<tr>
			<td>${row.idx }</td>
			<td>${row.writer }</td>
			<td><a href="#" onclick="memo_view('${row.idx}')">${row.memo }</a></td>
			<td><fmt:formatDate value="${row.post_date }" pattern="yyyy-MM-dd hh:mm:ss E"/></td>
		</tr>
	</c:forEach>
</table>


</body>
</html>