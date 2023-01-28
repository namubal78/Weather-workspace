<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
	<meta charset="UTF-8">
	<title>날씨</title>
	<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.6.1/jquery.min.js"></script>
</head>
<style>

	div {
		border: 1px solid black;
		box-sizing: border-box;
	}
	
</style>
<body>

	<div id="content">
	
		<div id="title">
			<h2>실시간 날씨 조회</h2>
		</div>

		<div id="weatherSelect">
			<p>주요 도시별 날씨</p>
			
			<select id="location">
				<option>서울</option>
				<option>부산</option>
				<option>대전</option>
				<option>제주</option>
			</select>
		
			<button id="btn1">해당 지역 날씨 보기</button>

			<br><br>
			
			<table id="result1" border="1" align="center">
				<thead>
					<tr>
						<th>info</th>
						<th>info</th>
						<th>info</th>
						<th>info</th>
						<th>info</th>
						<th>info</th>
						<th>info</th>
						<th>info</th>
					</tr>
				</thead>
				<tbody></tbody>	
			</table>

		</div>

		<div id="weatherCurrent">
			<p>현재 내 위치 날씨</p>
		</div>

		<div id="weatherForecast">
			<p>내 위치 날씨 예보</p>
		</div>

		<div id="weatherChart">
			<p>내 위치 날씨 차트</p>
		</div>

	</div>
	
	<script>
		
		$(function() {
			$("#btn1").click(() => {
								
				$.ajax({
					
					url: "weather.do",
					success: function() {
						console.log("ajax 성공")
					},
					error: function() {
						console.log("ajax 실패")
					}
				});
			});
		});
		
	</script>

</body>
</html>