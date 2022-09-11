<%@page import="model.WifiVo"%>
<%@page import="java.util.List"%>
<%@page import="service.WifiDBService"%>
<%@page import="test.OkHttp3Service"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>와이파이 정보 구하기</title>
<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.4.1/css/bootstrap.min.css">
<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.6.0/jquery.min.js"></script>
<script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.4.1/js/bootstrap.min.js"></script>
<script src="https://code.jquery.com/jquery-3.6.0.js"></script>
<style>
	body { width : 90%; margin : auto; }
	h1 { font-weight:800; padding-inline-start:10px; margin-top : 50px; margin-bottom:20px;}
	ul.navi { list-style:none; padding-inline-start:10px; }
	ul.navi > li { display : inline-block; border-right : 1px solid black; padding-right : 8px; margin-right : 5px;}
	ul.navi > li:last-child { border-right : none; }
	ul.navi > li > a{ display:inline-block; width : 100%; }
	div.userRequestBox{ padding-inline-start:10px; padding-top:10px;}
	div.tb_container{ width : 100%; margin-top : 20px;}
	td#no_data{ text-align: center;}
	div.pg_container{ width : 100%; text-align: center }
	ul.pagination > li.on > a { color : red; }
	div.popup{ width : 600px; position:absolute; top:25%; left:50%; transform : translate(-50%, -50%)}
	button.popup-x{ position:absolute; right : 10px;  }
	.snail { position:absolute; }
</style>
<script>

	var pg_start = <%= request.getAttribute("pg_start") == null? "1" : request.getAttribute("pg_start") %>;
	var pg_first = pg_start % 10 == 0? pg_start - 9 : (pg_start - pg_start % 10) + 1; 
	console.log(pg_first);

	function page_numbering(){
		for (var i = 0; i < 10; i++) {
			$(".pg").children().eq(i).html(pg_first + i);
		}
	}
	
	function page_css(){
		$("ul.pagination > li.on").removeClass("on");
		$(".pg").eq(pg_start % 10 - 1).addClass("on");
	}

	// 현재 나의 위치
	function getLocation(){
		
		if (navigator.geolocation){ // GPS 지원시
			navigator.geolocation.getCurrentPosition(function(position) {
			  $("#LAT").attr("value", position.coords.latitude);
		      $("#LNT").attr("value", position.coords.longitude);
		    }, function(error) {
		      console.error(error);
		    }, {
		      enableHighAccuracy: false,
		      maximumAge: 0,
		      timeout: Infinity
		    });
		}
		
	}
	
	// 조회버튼 선택시
	function search(){
		
		$LAT = $("#LAT").val();
		$LNT = $("#LNT").val();
		
		// 위치 정보 미입력시 알럿
		if ($LAT.trim() == '' && $LNT.trim() == '') {
			alert("위치 정보를 먼저 입력해주세요.");
			$("#LAT").focus();
			return;
		} 
		
		$("#pg_start").val(pg_start);
		//$("#isLogPoint").val(true); 
		$("#wifiSearchForm").attr("action","wifiSaveLog.do");	
		$("#wifiSearchForm").submit(); 
		
	}
	
	// 위치 정보에 따라 목록 출력
	function printPage(){
		
		$LAT = $("#LAT").val();
		$LNT = $("#LNT").val();
		
		// 위치 정보 미입력시 알럿
		if ($LAT.trim() == '' && $LNT.trim() == '') {
			alert("위치 정보를 먼저 입력해주세요.");
			$("#LAT").focus();
			return;
		} 
		
		$("#pg_start").val(pg_start);
		$("#wifiSearchForm").attr("action","wifiSearch.do");
		$("#wifiSearchForm").submit();
	}
	
	// 팝업
	function popup(){
		
		$(".popup").show();
		
		location.href='httpGet.do';
		
	}
	
	function popup_close(){
		$(".popup").hide();
	}
	
	// 윈도우창 내에서
	$(document).ready(function(){
		
		// 팝업
		$(".popup").hide();
		
		page_css();
		page_numbering();
		
		// pagination 디폴트
		$("ul.pagination > li > a").click(function(){
			
			// 페이지 선택 지점 
			var curPg = $(this).html();
			
			if (curPg == "&lt;") {
				pg_start = parseInt(pg_start) - 1;
				
				if (pg_start < 0) {
					pg_start = 1;
				}
			} else if (curPg == "&gt;"){
				pg_start = parseInt(pg_start) + 1;
				
				if (pg_start > $(".pg").children().eq(9).html()) {
					pg_first = pg_start + 1;
				}
			} else {
				pg_start = parseInt(curPg);
			}
			
			// 파라미터 전달 위치
			$("#pg_start").val(pg_start);
			
			page_numbering();
			page_css();
			
			printPage();
			
		});
		
		// pagination 숨김/보이기
		if ($("#LAT").val().trim() == '' && $("#LNT").val().trim() == ''){
			$(".pg_container").hide();
		} else {
			$(".pg_container").show();
		}
		
	});
	
</script>
</head>
<body>
	<h1>와이파이 정보 구하기</h1>
	<ul class = "navi">
		<li>
			<a href = "index.jsp">홈</a>
		</li>
		<li>
			<a href = "searchLog.do">위치 히스토리 목록</a>
		</li>
		<li>
			<a style = "cursor:pointer" onclick = "popup();">Open API 와이파이 정보 가져오기</a>
		</li>
	</ul>
	<form id = "wifiSearchForm">
		<div class = "userRequestBox">
		  	<label>LAT&nbsp;:&nbsp;</label>
			<input class = "pos_text" id = "LAT" name = "LAT" value = <%= request.getAttribute("myLAT")==null ? "" : request.getAttribute("myLAT")%>>            
			<label>,&nbsp;LNT&nbsp;:&nbsp;</label>
			<input class = "pos_text" id = "LNT" name = "LNT" value = <%= request.getAttribute("myLNT")==null ? "" : request.getAttribute("myLNT")%>> 
			<input type = "button" value = "내 위치 가져오기" onclick = "return getLocation();">
			<input type = "hidden" id = "pg_start" name = "pg_start">
			<input type = "button" id = "search_btn" value = "조회" onclick = "search()">
		</div>  
	</form>
	<div class="tb_container">       
	  <table class="table table-striped">
	    <thead>
	      <tr>
	        <th>거리</th>
	        <th>관리번호</th>
	        <th>자치구</th>
	        <th>와이파이명</th>
	        <th>도로명주소</th>
	        <th>상세주소</th>
	        <th>설치위치(층)</th>
	        <th>설치유형</th>
	        <th>설치기관</th>
	        <th>서비스구분</th>
	        <th>망종류</th>
	        <th>설치년도</th>
	        <th>실내외구분</th>
	        <th>WIFI접속환경</th>
	        <th>X좌표</th>
	        <th>Y좌표</th>
	        <th>작업일자</th>
	      </tr>
	    </thead>
	    <tbody> 
	      <% 
	         List<WifiVo> wifiList = (List)request.getAttribute("wifiList");
	         if (wifiList == null) { 
	      %> 
	    	 <tr>
		      	<td id = "no_data" colspan = "17">위치 정보를 입력한 후에 조회해 주세요.</td>
		     </tr>  
	      <% } else { 
	    	  for (WifiVo vo : wifiList){	      
	      %>
	    	 <tr>
		        <td><%= String.format("%.4f", vo.getDISTANCE()) %></td>
		        <td><%= vo.getX_SWIFI_MGR_NO() %></td>
		        <td><%= vo.getX_SWIFI_WRDOFC() %></td>
		        <td><%= vo.getX_SWIFI_MAIN_NM() %></td>
		        <td><%= vo.getX_SWIFI_ADRES1() %></td>
		        <td><%= vo.getX_SWIFI_ADRES2() %></td>
		        <td><%= vo.getX_SWIFI_INSTL_FLOOR() %></td>
		        <td><%= vo.getX_SWIFI_INSTL_TY() %></td>
		        <td><%= vo.getX_SWIFI_INSTL_MBY() %></td>
		        <td><%= vo.getX_SWIFI_SVC_SE() %></td>
		        <td><%= vo.getX_SWIFI_CMCWR() %></td>
		        <td><%= vo.getX_SWIFI_CNSTC_YEAR() %></td>
		        <td><%= vo.getX_SWIFI_INOUT_DOOR() %></td>
		        <td><%= vo.getX_SWIFI_REMARS3() %></td>
		        <td><%= String.format("%.6f", vo.getLNT())%></td>
		        <td><%= String.format("%.6f", vo.getLAT())%></td>
		        <td><%= vo.getWORK_DTTM() %></td>
		      </tr>	    	  
	      <% }} %>
	    </tbody>
	  </table>
	</div>
	<div class="pg_container">
	  <ul class="pagination">
	    <li class = "prev"><a href="#">&lt</a></li>
	    <li class = "pg" class = "on"><a href="#">1</a></li>
	    <li class = "pg"><a href="#">2</a></li>
	    <li class = "pg"><a href="#">3</a></li>
	    <li class = "pg"><a href="#">4</a></li>
	    <li class = "pg"><a href="#">5</a></li>
	    <li class = "pg"><a href="#">6</a></li>
	    <li class = "pg"><a href="#">7</a></li>
	    <li class = "pg"><a href="#">8</a></li>
	    <li class = "pg"><a href="#">9</a></li>
	    <li class = "pg"><a href="#">10</a></li>
	    <li id = "last"><a href="#">&gt</a></li>
	  </ul>
	</div>
	<div class="popup panel panel-default">
	  <div class="panel-heading">와이파이 정보 다운 받는 중...📡📡<button class = "popup-x" onclick = "return popup_close();">X</button></div>
	  <div class="panel-body">조금만 기다려보장<img src = "https://blog.kakaocdn.net/dn/c6pAkf/btrn4rgw5la/7m06GPUMq1155ou7EEYo21/img.gif"/></div>
	</div>
</body>
</html>