<%@ page language="java" contentType="text/html; charset=EUC-KR"
    pageEncoding="EUC-KR"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="EUC-KR">
<title>와이파이 정보 구하기</title>
<style>
	div{ width : 1280px; margin : auto; text-align : center; }
	a { display: block; text-align : center; }
</style>
</head>
<body>
	<div>
		<p>WIFI 정보를 가져오던 중 오류가 발생했습니다.</p>
		<p>- Error Message : <%= request.getAttribute("ErrorMessage") %> -</p>
		<p>- 자세한 사항은 고객센터로 문의 해주세요. -</p>
		<a href = "index.jsp">홈으로 가기</a>
	</div>
</body>
</html>