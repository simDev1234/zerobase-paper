<%@ page language="java" contentType="text/html; charset=EUC-KR"
    pageEncoding="EUC-KR"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="EUC-KR">
<title>�������� ���� ���ϱ�</title>
<style>
	div{ width : 1280px; margin : auto; text-align : center; }
	a { display: block; text-align : center; }
</style>
</head>
<body>
	<div>
		<p>WIFI ������ �������� �� ������ �߻��߽��ϴ�.</p>
		<p>- Error Message : <%= request.getAttribute("ErrorMessage") %> -</p>
		<p>- �ڼ��� ������ �����ͷ� ���� ���ּ���. -</p>
		<a href = "index.jsp">Ȩ���� ����</a>
	</div>
</body>
</html>