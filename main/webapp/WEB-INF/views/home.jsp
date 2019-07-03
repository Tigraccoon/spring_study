<%@ page language="java" contentType="text/html; charset=UTF-8"
pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!-- 세션 사용 여부 -->
<%@ page session="true" %>
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<title>Home</title>
<%@ include file="include/header.jsp" %>
</head>
<body>
<%@ include file="include/menu.jsp" %>

<!-- 세션이 있으면? -->

<c:if test="${sessionScope.userid != null }">
	<h2>
		${sessionScope.name }(${sessionScope.userid })  의 방문을 환영해요!
	</h2>
</c:if>

<h1>
	Hello world!
</h1>

<P>  The time on the server is ${serverTime}. </P>

<!-- 실제 서비스되는 배포 디렉토리 확인 -->
<%=application.getRealPath("/WEB-INF/views/images/") %>

</body>
</html>
