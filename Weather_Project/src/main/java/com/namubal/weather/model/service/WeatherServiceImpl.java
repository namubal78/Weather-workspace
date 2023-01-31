package com.namubal.weather.model.service;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.namubal.weather.model.dao.WeatherDao;
import com.namubal.weather.model.vo.Weather;

@Service
public class WeatherServiceImpl implements WeatherService {

	@Autowired
	private SqlSessionTemplate sqlSession;
	
	@Autowired
	private WeatherDao weatherDao;
	
	@Override
	public int checkWeather(String weatherNo) {
		
		return weatherDao.checkWeather(sqlSession, weatherNo);
	}

	@Override
	public int insertWeather(Weather weather) {
		
		return weatherDao.insertWeather(sqlSession, weather);
	}

	@Override
	public Weather selectWeather(Weather weather) {
		return weatherDao.selectWeather(sqlSession, weather);
	}

}
