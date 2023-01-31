<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %> 
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<!DOCTYPE html>
<html>
<head>
	<meta charset="UTF-8">
	<title>날씨</title>
	<script src="https://kit.fontawesome.com/6cda7ccd12.js" crossorigin="anonymous"></script>
	<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.6.1/jquery.min.js"></script>
	<script src="http://code.jquery.com/jquery-latest.min.js"></script>
</head>
<style>

	div {
		/* border: 1px solid blue; */
		box-sizing: border-box;
	}

	#wrap {
		width: 612px;
		height: 612px;
		background-color: rgb(77, 127, 187);
		border-radius: 10px;
		margin: 15px;
		padding: 15px;
	}

	#wrap>div {
		width: 100%;
	}

	#title {
		height: 15%;
	}

	#title>p {
		font-size: 40px;
		color: lightgray;
		margin-left: 10px;
	}

	#content {
		height: 80%;
	}
	
	#content>div {
		width: 100%;
	}

	#weatherCurrent {
		height: 95%;
	}

</style>
<body>

	<div id="wrap">
	
		<div id="title">
			<p style="margin: 10px;">오늘의 날씨 조회</p>
			<br>
		</div>

		<div id="content">
			<div id="weatherCurrent">
				<br><br><br><br><br><br>
				<p style="font-size: 20px; color: lightgray;" align="center">loading now...</p>
			</div>	
		</div>		
    </div>

	<script>
	
		// 소스출처 : http://www.kma.go.kr/weather/forecast/digital_forecast.jsp  내부에 있음
		//
		// (사용 예)
		// var rs = dfs_xy_conv("toLL","60","127");
		// console.log(rs.lat, rs.lng);

	    // LCC DFS 좌표변환을 위한 기초 자료
	    //
	    var RE = 6371.00877; // 지구 반경(km)
	    var GRID = 5.0; // 격자 간격(km)
	    var SLAT1 = 30.0; // 투영 위도1(degree)
	    var SLAT2 = 60.0; // 투영 위도2(degree)
	    var OLON = 126.0; // 기준점 경도(degree)
	    var OLAT = 38.0; // 기준점 위도(degree)
	    var XO = 43; // 기준점 X좌표(GRID)
	    var YO = 136; // 기1준점 Y좌표(GRID)
	    //
	    // LCC DFS 좌표변환 ( code : "toXY"(위경도->좌표, v1:위도, v2:경도), "toLL"(좌표->위경도,v1:x, v2:y) )
	    //

	    function dfs_xy_conv(code, v1, v2) {
	        var DEGRAD = Math.PI / 180.0;
	        var RADDEG = 180.0 / Math.PI;

	        var re = RE / GRID;
	        var slat1 = SLAT1 * DEGRAD;
	        var slat2 = SLAT2 * DEGRAD;
	        var olon = OLON * DEGRAD;
	        var olat = OLAT * DEGRAD;

	        var sn = Math.tan(Math.PI * 0.25 + slat2 * 0.5) / Math.tan(Math.PI * 0.25 + slat1 * 0.5);
	        sn = Math.log(Math.cos(slat1) / Math.cos(slat2)) / Math.log(sn);
	        var sf = Math.tan(Math.PI * 0.25 + slat1 * 0.5);
	        sf = Math.pow(sf, sn) * Math.cos(slat1) / sn;
	        var ro = Math.tan(Math.PI * 0.25 + olat * 0.5);
	        ro = re * sf / Math.pow(ro, sn);
	        var rs = {};
	        if (code == "toXY") {
	            rs['lat'] = v1;
	            rs['lng'] = v2;
	            var ra = Math.tan(Math.PI * 0.25 + (v1) * DEGRAD * 0.5);
	            ra = re * sf / Math.pow(ra, sn);
	            var theta = v2 * DEGRAD - olon;
	            if (theta > Math.PI) theta -= 2.0 * Math.PI;
	            if (theta < -Math.PI) theta += 2.0 * Math.PI;
	            theta *= sn;
	            rs['x'] = Math.floor(ra * Math.sin(theta) + XO + 0.5);
	            rs['y'] = Math.floor(ro - ra * Math.cos(theta) + YO + 0.5);
	        }
	        else {
	            rs['x'] = v1;
	            rs['y'] = v2;
	            var xn = v1 - XO;
	            var yn = ro - v2 + YO;
	            ra = Math.sqrt(xn * xn + yn * yn);
	            if (sn < 0.0) - ra;
	            var alat = Math.pow((re * sf / ra), (1.0 / sn));
	            alat = 2.0 * Math.atan(alat) - Math.PI * 0.5;

	            if (Math.abs(xn) <= 0.0) {
	                theta = 0.0;
	            }
	            else {
	                if (Math.abs(yn) <= 0.0) {
	                    theta = Math.PI * 0.5;
	                    if (xn < 0.0) - theta;
	                }
	                else theta = Math.atan2(xn, yn);
	            }
	            var alon = theta / sn + olon;
	            rs['lat'] = alat * RADDEG;
	            rs['lng'] = alon * RADDEG;
	        }
	        return rs;
	    }
	
	</script>
	
	<script>
		
		$(window).load(function getLocation() {
						
			if(navigator.geolocation) {
				navigator.geolocation.getCurrentPosition(function(position) {

					// 위경도 -> 격자x,y
					let latitude = position.coords.latitude;
					let longitude = position.coords.longitude;
					
					var rs = dfs_xy_conv("toXY", latitude, longitude);
					
					let nx = rs.x;
					let ny = rs.y;
					
					// 현재 연월일 추출
					let today = new Date();   

					let year = String(today.getFullYear()); // 년도
					let month = today.getMonth() + 1;
					if(month < 10) {
						month = "0" + String(month);
					}
										
					let date = String(today.getDate());  // 날짜
					if(date < 10) {
						date = "0" + String(date);
					}
					
					let base_date = String(year) + String(month) + String(date);
					
					// 현재 시각 추출
					let hours = today.getHours(); // 시
					if(hours < 10) {
						hours = "0" + String(hours);
					}
					
					let minutes = today.getMinutes();  // 분
					if(minutes < 10) {
						minutes = "0" + String(minutes);
					}
					
					let current_time = String(hours) + String(minutes);

					// 베이스 시각 추출
					let base_time = "";
					
					if(parseInt(hours) < 2 ) {
												
						if(parseInt(date) == 1) {
							switch (parseInt(month)) {
								case 1:
									base_date = String(parseInt(year) - 1) + "1231";
									break;
								case 2:
								case 4:
								case 6:
								case 8:
								case 9:
									base_date = String(year) + "0" + String(parseInt(month) - 1) + "31";
									break;
								case 11:
									base_date = String(year) + String(parseInt(month) - 1) + "31";
									break;
								case 5:
								case 7:
								case 10:
									base_date = String(year) + "0" + String(parseInt(month) - 1) + "30";
									break;
								case 12:
									base_date = String(year) + String(parseInt(month) - 1) + "30";
									break;
								case 3:
									base_date = String(year) + "0" + String(parseInt(month) - 1) + "28";
									break;
							}
						} else if (parseInt(date) >= 2 && parseInt(date) <= 10) {					
							base_date = String(year) + String(month) + "0" + String(parseInt(date) - 1);
						} else {
							base_date = String(year) + String(month) + String(parseInt(date) - 1);
						}
												
						base_time = "2300";

					} else if (parseInt(hours) < 5 ) {
						base_time = "0200";
					} else if (parseInt(hours) < 8 ) {
						base_time = "0500";
					} else if (parseInt(hours) < 11 ) {
						base_time = "0800";
					} else if (parseInt(hours) < 14 ) {
						base_time = "1100";
					} else if (parseInt(hours) < 17 ) {
						base_time = "1400";
					} else if (parseInt(hours) < 20 ) {
						base_time = "1700";
					} else if (parseInt(hours) < 23 ) {
						base_time = "2000";
					} else {
						base_time = "2300";
					}
					
					$.ajax({
						
						url: "weather.do",
						data: {
							nx: nx,
							ny: ny,
							base_date: base_date,
							base_time: base_time,
							current_time: current_time
						},
						success: function(result) {
							console.log("ajax 성공");
							$("#weatherCurrent").html(result);
						},
						error: function() {
							console.log("ajax 실패");
						}
					});
					
				}),
				function(error) {
					console.log(error);
				},
				{
					enableHighAccuracy: false,
					maximumAge: 0,
					timeout: Infinity
				} 
			} else {
				alert('GPS를 지원하지 않습니다.');
			}			
		});
		
	</script>

</body>
</html>