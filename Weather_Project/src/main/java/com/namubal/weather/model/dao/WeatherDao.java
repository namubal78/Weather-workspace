package com.namubal.weather.model.dao;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.stereotype.Repository;

import com.namubal.weather.model.vo.Weather;

@Repository
public class WeatherDao {
	
	// 날씨 insert 전 중복체크용 select 
	public int checkWeather(SqlSessionTemplate sqlSession, String weatherNo) {
		return sqlSession.selectOne("weatherMapper.checkWeather", weatherNo);
	}

	// 날씨 insert
	public int insertWeather(SqlSessionTemplate sqlSession, Weather weather) {
		return sqlSession.insert("weatherMapper.insertWeather", weather);
	}

	// 날씨 select
	public Weather selectWeather(SqlSessionTemplate sqlSession, Weather weather) {
		return sqlSession.selectOne("weatherMapper.selectWeather", weather);
	}

}
