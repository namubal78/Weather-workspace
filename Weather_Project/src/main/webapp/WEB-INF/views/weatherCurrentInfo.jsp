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

    <div style="background-color: rgb(49, 90, 141); border-radius: 10px; height: 270px; padding: 10px;">
        <div style="color: lightgray; margin: 10px; font-size: 20px;" >현재 날씨</div>
        <div style="color: lightgray; margin: 10px; font-size: 13px;">       
            
            <c:set var="now" value="<%=new java.util.Date()%>" />
            <c:set var="sysTime"><fmt:formatDate value="${now}" pattern="hh:dd" /></c:set> 
            <c:out value="${sysTime}" />

            (예보 시각: ${weatherInfo.baseTime.substring(0, 2)}:00)
        </div>

        <div style="display: block;">
            <div style="display: inline; float: left; width: 25%; margin-top: 25px;" align="center">
                
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
            <div style="display: inline; float: left; width: 25%;" align="center"><p style="font-size: 40px; color: lightgray;">${weatherInfo.TMP.intValue()} 'C</p></div>
            <div style="display: inline; float: left; width: 25%;" align="left">
                
                <c:choose>
                    <c:when test="${weatherInfo.SKY.intValue() == 1}">
                        <p style="font-size: 40px; color: lightgray;">맑음</p>
                    </c:when>                
                    <c:when test="${weatherInfo.SKY.intValue() == 3}">
                        <p style="font-size: 40px; color: lightgray;">구름많음</p>
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
            
            <div style="display: inline; float: left; width: 25%;" align="left"><p style="font-size: 40px; color: rgb(49, 90, 141);">공백</p></div>
        </div>
        <div style="display: block;">
        
            <div style="display: inline; float: left; width: 25%;" align="left">
                <p style="font-size: 15px; color: lightgray;">
                    &nbsp;<i class="fa-solid fa-umbrella"></i>&nbsp;강수확률: ${weatherInfo.POP.intValue()} %
                </p>
            </div>
            <div style="display: inline; float: left; width: 25%;" align="left">
                <p style="font-size: 15px; color: lightgray;">
                    &nbsp;<i class="fa-solid fa-droplet"></i>&nbsp;습도: ${weatherInfo.REH.intValue()} %
                </p>
            </div>
            <div style="display: inline; float: left; width: 25%;" align="left">
                <p style="font-size: 15px; color: lightgray;">
                    &nbsp;<i class="fa-solid fa-water"></i>&nbsp;파고: ${weatherInfo.WAV.intValue()} M
                </p>
            </div>
            <div style="display: inline; float: left; width: 25%;" align="left">
                <p style="font-size: 15px; color: lightgray;">
                    &nbsp;<i class="fa-solid fa-wind"></i>&nbsp;풍속: ${weatherInfo.WSD.intValue()} m/s
                </p>
            </div>
        
        </div>
    </div>
    <br>
    <div style="background-color: rgb(49, 90, 141); border-radius: 10px; height: 170px; padding: 10px;">
        <div style="color: lightgray; margin: 10px; font-size: 20px;">날씨 예보</div>
        <div>
            <div style="display: inline; float: left; width: 25%; border: 1px solid black;" align="left">1</div>
            <div style="display: inline; float: left; width: 25%;" align="left">2</div>
            <div style="display: inline; float: left; width: 25%;" align="left">3</div>
            <div style="display: inline; float: left; width: 25%;" align="left">4</div>
        </div>
    </div>



</body>
</html>