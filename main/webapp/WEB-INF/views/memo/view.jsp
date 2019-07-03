<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>메모 보기</title>
<%@ include file="../include/header.jsp" %>
<script type="text/javascript">

$(function(){
	$("#btnUpdate").click(function(){
		document.form1.action="${path}/memo/update/${dto.idx}";
		document.form1.submit();
	});
	
	$("#btnDelete").click(function(){
		if(confirm("진짜 삭제합니다?")){
			document.form1.action="${path}/memo/delete/${dto.idx}";
			document.form1.submit();
		}
	});
	
});

</script>
</head>
<body>
<%@ include file="../include/menu.jsp" %>

<h2>메모 보기</h2>

<form name="form1" method="post">
	<table>
		<tr>
			<th>번호</th>
			<th>${dto.idx }</th>
		</tr>
		<tr>
			<th><label for="writer">이름</label></th>
			<td><input name="writer" id="writer" value="${dto.writer }">
		</tr>
		<tr>
			<th><label for="memo">메모</label></th>
			<td><input name="memo" id="memo" value="${dto.memo }" size="50">
		</tr>
		<tr>
			<th>날짜</th>
			<th><fmt:formatDate value="${dto.post_date }" pattern="yyyy-MM-dd hh:mm:ss E"/></th>
		</tr>
		<tr>
			<th colspan="2">
				<input type="hidden" value="${dto.idx }" name="idx">
				<input type="button" id="btnUpdate" value="수정">
				<input type="button" id="btnDelete" value="삭제">
			</th>
		</tr>
	</table>
</form>

</body>
</html>