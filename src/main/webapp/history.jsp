<%@page import="java.util.ArrayList"%>
<%@page import="model.WifiHistoryVo"%>
<%@page import="java.util.List"%>
<%@ page language="java" contentType="text/html; charset=EUC-KR"
    pageEncoding="EUC-KR"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="EUC-KR">
<title>�������� ���� ���ϱ�</title>
<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.4.1/css/bootstrap.min.css">
<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.6.0/jquery.min.js"></script>
<script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.4.1/js/bootstrap.min.js"></script>
<script src="https://code.jquery.com/jquery-3.6.0.js"></script>
<style>
	body { width : 90%; margin : auto; }
	h1 { font-weight:800; padding-inline-start:10px; margin-top : 50px; margin-bottom:20px;}
	ul { list-style:none; padding-inline-start:10px; }
	li { display : inline-block; border-right : 1px solid black; padding-right : 8px; margin-right : 5px;}
	li:last-child { border-right : none; }
	a{ display:inline-block; width : 100%; }
	.container{ width : 100%; margin-top : 20px;}	
	td#no_history{ text-align: center;}
</style>
</head>
<body>
    <%
       List<WifiHistoryVo> wifiHistoryList = (List)request.getAttribute("wifiHistoryList");
    %>
	<h1>�������� ���� ���ϱ�</h1>
	<ul>
		<li>
			<a href = "index.jsp">Ȩ���� ���ư���</a>
		</li>
	</ul>
	<div class="container">       
	  <table class="table table-striped">
	    <thead>
	      <tr>
	        <th>ID</th>
	        <th>X��ǥ</th>
	        <th>Y��ǥ</th>
	        <th>��ȸ����</th>
	        <th>���</th>
	      </tr>
	    </thead>
	    <tbody> 
	      <%
	      	 if (wifiHistoryList == null) {
	      %>
	        <tr>
	        	<td id = "no_history" colspan = "5">�˻� �����丮�� �����ϴ�.</td>
	        </tr>
	      <%
	      	 } else {
	      %>
	      <% for (WifiHistoryVo vo : wifiHistoryList){%>
	        <form action = "deleteLog.do" method = "GET">
	        	<tr>
			    	<td id = "idx"><%= vo.getIdx() %></td>
			    	<td><%= vo.getLAT() %></td>
			    	<td><%= vo.getLNT() %></td>
			    	<td><%= vo.getSearch_dttm() %></td>
			    	<input type = "hidden" name = "idx" value = "<%= vo.getIdx() %>">
			    	<td><input type = "submit" value = "����"></td>
		    	</tr>
	        </form>
	      <% }} %>
	    </tbody>
	  </table>
	</div>
</body>
</html>