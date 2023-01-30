package com.namubal.weather.model.vo;

public class Weather {

    private String baseDate; // base_date 호출 기준 날짜
    private String baseTime; // base_time 호출 기준 시각
    private String fcstDate; // 예보 대상 날짜
    private String fcstTime; // 예보 대상 시각
    
    private double TMP; // 1시간 기온(섭씨)
    private double UUU; // 동서성분 풍속(m/s)
    private double VVV; // 남북성분 풍속(m/s)
    private double VEC; // 풍향(deg)
    private double WSD; // 풍속(m/s)
    private double SKY; // 하늘상태(코드값)
    private double PTY; // 강수형태(코드값)
    private double POP; // 강수확률(%)
    private double WAV; // 파고(M)
    private double PCP; // 1시간 강수량(mm)
    private double REH; // 습도(%)
    private double SNO; // 1시간 신적설(cm)
    private double TMN; // 일 최저기온(섭씨)
    private double TMX; // 일 최고기온(섭씨)
    
}
