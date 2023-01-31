package com.namubal.weather.model.service;

import com.namubal.weather.model.vo.Weather;

public interface WeatherService {

	int checkWeather(String weatherNo);

	int insertWeather(Weather weather);

	Weather selectWeather(Weather weather);

}
