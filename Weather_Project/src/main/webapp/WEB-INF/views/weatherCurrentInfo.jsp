<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Insert title here</title>
    <script src="https://kit.fontawesome.com/6cda7ccd12.js" crossorigin="anonymous"></script>
</head>
<body>

    <div style="background-color: rgb(55, 102, 160); border-radius: 10px; height: 320px; padding: 10px;">
        <div style="color: lightgray; margin: 10px; font-size: 20px;" >현재 시각 날씨</div>
        <div style="color: lightgray; margin: 10px; font-size: 13px;">       
            
            <c:set var="now" value="<%=new java.util.Date()%>" />
            <c:set var="sysTime"><fmt:formatDate value="${now}" pattern="hh:dd" /></c:set> 
            <c:out value="${sysTime}" />

            (예보 시각: ${weatherInfo.baseTime.substring(0, 2)}:00)
        </div>

        <div style="display: block;">
            <div style="display: inline; float: left; width: 25%; margin-top: 25px; color: rgb(41, 41, 41);" align="center">
                
                <c:choose>
                    <c:when test="${weatherInfo.SKY.intValue() == 1}">
                    <i class="fa-regular fa-sun fa-5x"></i>
                    </c:when>                
                    <c:when test="${weatherInfo.SKY.intValue() == 3}">
                        <i class="fa-solid fa-cloud-sun fa-5x"></i>                
                    </c:when>                
                    <c:when test="${weatherInfo.SKY.intValue() == 4}">
                        <i class="fa-solid fa-cloud fa-5x"></i>
                    </c:when>
                    <c:when test="{weatherInfo.PTY.intValue() == 1}">
                        <i class="fa-solid fa-cloud-rain fa-5x"></i>
                    </c:when>                 
                    <c:when test="{weatherInfo.PTY.intValue() == 2}">
                        <i class="fa-solid fa-cloud-meatball fa-5x"></i>
                    </c:when>                 
                    <c:when test="{weatherInfo.PTY.intValue() == 3}">
                        <i class="fa-regular fa-snowflake fa-5x"></i>
                    </c:when>                 
                    <c:when test="{weatherInfo.PTY.intValue() == 4}">
                        <i class="fa-solid fa-cloud-showers-heavy fa-5x"></i>
                    </c:when>
                </c:choose>

            </div>
            <div style="display: inline; float: left; width: 25%;" align="center"><p style="font-size: 40px; color: lightgray;">${weatherInfo.TMP.intValue()}'C</p></div>
            <div style="display: inline; float: left; width: 25%;" align="left">
                <c:choose>
                    <c:when test="${weatherInfo.SKY.intValue() == 1}">
                        <p style="font-size: 40px; color: lightgray;">맑음</p>
                    </c:when>                
                    <c:when test="${weatherInfo.SKY.intValue() == 3}">
                        <p style="font-size: 40px; color: lightgray;">구름</p>
                    </c:when>                
                    <c:when test="${weatherInfo.SKY.intValue() == 4}">
                        <p style="font-size: 40px; color: lightgray;">흐림</p>
                    </c:when>
                    <c:when test="{weatherInfo.PTY.intValue() == 1}">
                        <p style="font-size: 40px; color: lightgray;">비</p>
                    </c:when>                 
                    <c:when test="{weatherInfo.PTY.intValue() == 2}">
                        <p style="font-size: 40px; color: lightgray;">비/눈</p>
                    </c:when>                 
                    <c:when test="{weatherInfo.PTY.intValue() == 3}">
                        <p style="font-size: 40px; color: lightgray;">눈</p>
                    </c:when>                 
                    <c:when test="{weatherInfo.PTY.intValue() == 4}">
                        <p style="font-size: 40px; color: lightgray;">소나기</p>
                    </c:when>
                </c:choose>
            </div>
            <div style="display: inline; float: left; width: 25%;" align="left"><p style="font-size: 40px; color: rgb(55, 102, 160);">공백</p></div>
        </div>

        <div style="color: lightgray; margin: 10px; font-size: 20px;">
            ${weatherInfo.fcstDate.substring(4, 6)}월             
            ${weatherInfo.fcstDate.substring(6, 8)}일 최고 기온은 
            <span style="color: red; font-size: 20px;">${Integer.valueOf(weatherMaxMin.get(0))}'C</span> 이고,
            최저 기온은
            <span style="color: blue; font-size: 20px;">${Integer.valueOf(weatherMaxMin.get(1))}'C</span> 입니다.
        </div>

        <div style="display: block;">
        
            <div style="display: inline; float: left; width: 25%;" align="center">
                <p style="font-size: 15px; color: lightgray;">
                    &nbsp;<i class="fa-solid fa-umbrella" style="color: orange;"></i>&nbsp;&nbsp;강수확률: ${weatherInfo.POP.intValue()} %
                </p>
            </div>
            <div style="display: inline; float: left; width: 25%;" align="center">
                <p style="font-size: 15px; color: lightgray;">
                    &nbsp;<i class="fa-solid fa-droplet" style="color:aquamarine"></i>&nbsp;&nbsp;습도: ${weatherInfo.REH.intValue()} %
                </p>
            </div>
            <div style="display: inline; float: left; width: 25%;" align="center">
                <p style="font-size: 15px; color: lightgray;">
                    &nbsp;<i class="fa-solid fa-water" style="color: darkcyan;"></i>&nbsp;&nbsp;파고: ${weatherInfo.WAV.intValue()} M
                </p>
            </div>
            <div style="display: inline; float: left; width: 25%;" align="center">
                <p style="font-size: 15px; color: lightgray;">
                    &nbsp;<i class="fa-solid fa-wind" style="color: lightskyblue"></i>&nbsp;&nbsp;풍속: ${weatherInfo.WSD.intValue()} m/s
                </p>
            </div>
        
        </div>
    </div>
    <br>
    <div style="background-color: rgb(55, 102, 160); border-radius: 10px; height: 180px; padding: 10px;">
        <div style="color: lightgray; margin-left: 10px; margin-top: 10px; font-size: 20px;">단기 날씨 예보</div>
        <div>
            <div style="display: inline; float: left; width: 25%; height: 80px;" align="center">
                <p style="font-size: 35px; color: lightgray;">${weatherFcstInfo.get(0)}'C <span style="color: black; font-size: 35px;">(-)</span>
                </p>
            </div>
            <div style="display: inline; float: left; width: 25%; height: 80px;" align="center">
                <p style="font-size: 35px; color: lightgray;">${weatherFcstInfo.get(1)}'C
                    <c:choose>
                        <c:when test="${weatherFcstInfo.get(1) - weatherFcstInfo.get(0) > 0 }">
                            <span style="color: red; font-size: 35px;">(+${weatherFcstInfo.get(1) - weatherFcstInfo.get(0)})</span>
                        </c:when>                    
                        <c:when test="${weatherFcstInfo.get(1) - weatherFcstInfo.get(0) == 0 }">
                            <span style="color: black; font-size: 35px;">(-)</span>
                        </c:when>                    
                        <c:when test="${weatherFcstInfo.get(1) - weatherFcstInfo.get(0) < 0 }">
                            <span style="color: blue; font-size: 35px;">(${weatherFcstInfo.get(1) - weatherFcstInfo.get(0)})</span>
                        </c:when>
                    </c:choose>
                </p>
            </div>
            <div style="display: inline; float: left; width: 25%; height: 80px;" align="center">
                <p style="font-size: 35px; color: lightgray;">${weatherFcstInfo.get(2)}'C
                    <c:choose>
                        <c:when test="${weatherFcstInfo.get(2) - weatherFcstInfo.get(1) > 0 }">
                            <span style="color: red; font-size: 35px;">(+${weatherFcstInfo.get(2) - weatherFcstInfo.get(1)})</span>
                        </c:when>                    
                        <c:when test="${weatherFcstInfo.get(2) - weatherFcstInfo.get(1) == 0 }">
                            <span style="color: black; font-size: 35px;">(-)</span>
                        </c:when>                    
                        <c:when test="${weatherFcstInfo.get(2) - weatherFcstInfo.get(1) < 0 }">
                            <span style="color: blue; font-size: 35px;">(${weatherFcstInfo.get(2) - weatherFcstInfo.get(1)})</span>
                        </c:when>
                    </c:choose>
                </p>
            </div>
            <div style="display: inline; float: left; width: 25%; height: 80px;" align="center">
                <p style="font-size: 35px; color: lightgray;">${weatherFcstInfo.get(3)}'C
                    <c:choose>
                        <c:when test="${weatherFcstInfo.get(3) - weatherFcstInfo.get(2) > 0 }">
                            <span style="color: red; font-size: 35px;">(+${weatherFcstInfo.get(3) - weatherFcstInfo.get(2)})</span>
                        </c:when>                    
                        <c:when test="${weatherFcstInfo.get(3) - weatherFcstInfo.get(2) == 0 }">
                            <span style="color: black; font-size: 35px;">(-)</span>
                        </c:when>                    
                        <c:when test="${weatherFcstInfo.get(3) - weatherFcstInfo.get(2) < 0 }">
                            <span style="color: blue; font-size: 35px;">(${weatherFcstInfo.get(3) - weatherFcstInfo.get(2)})</span>
                        </c:when>
                    </c:choose>
                </p>
            </div>
        </div>
        <div>
            <div style="display: inline; float: left; width: 25%;" align="center"><p style="font-size: 15px; color: lightgray;">${weatherInfo.baseTime.substring(0, 2)}:00</p></div>
            <div style="display: inline; float: left; width: 25%;" align="center"><p style="font-size: 15px; color: lightgray;">${weatherInfo.baseTime.substring(0, 2)}:00 + 3H</p></div>
            <div style="display: inline; float: left; width: 25%;" align="center"><p style="font-size: 15px; color: lightgray;">${weatherInfo.baseTime.substring(0, 2)}:00 + 6H</p></div>
            <div style="display: inline; float: left; width: 25%;" align="center"><p style="font-size: 15px; color: lightgray;">${weatherInfo.baseTime.substring(0, 2)}:00 + 9H</p></div>
        </div>
    </div>



</body>
</html>