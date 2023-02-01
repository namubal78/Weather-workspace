package com.namubal.weather.model.service;

import com.namubal.weather.model.vo.Weather;

public interface WeatherService {

	// 날씨 insert 전 중복체크용 select 서비스
	int checkWeather(String weatherNo);

	// 날씨 insert 서비스
	int insertWeather(Weather weather);

	// 날씨 select 서비스
	Weather selectWeather(Weather weather);

}
