<%@ page language="java" contentType="text/html; charset=EUC-KR"
    pageEncoding="EUC-KR"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="EUC-KR">
<title>와이파이 정보 구하기</title>
<style>
	div{ width : 1280px; margin : auto; text-align : center; }
	p { display: inline-block }
    a { display: block; text-align : center; }
</style>
</head>
<body>
	<div>
		<p><%= request.getAttribute("totalCnt") %></p>
		<p>개의 WIFI정보를 정상적으로 저장하였습니다.</p>
		<a href = "index.jsp">홈으로 가기</a>
	</div>
</body>
</html>