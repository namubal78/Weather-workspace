package com.namubal.weather.model.dao;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.stereotype.Repository;

import com.namubal.weather.model.vo.Weather;

@Repository
public class WeatherDao {

	public int checkWeather(SqlSessionTemplate sqlSession, String weatherNo) {
		return sqlSession.selectOne("weatherMapper.checkWeather", weatherNo);
	}

	public int insertWeather(SqlSessionTemplate sqlSession, Weather weather) {
		return sqlSession.insert("weatherMapper.insertWeather", weather);
	}

	public Weather selectWeather(SqlSessionTemplate sqlSession, Weather weather) {
		return sqlSession.selectOne("weatherMapper.selectWeather", weather);
	}

}
