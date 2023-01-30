package com.namubal.weather.controller;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class WeatherController {

	public static final String SERVICEKEY = "ydPY7ABO4D6zNxj8P%2B6%2FsD%2B78wn8BM65XurwyxF8ETDo2SH2Qv644PoIWsq%2BpZK86nYY6YnLiL3XtzyEqfzn%2Fw%3D%3D";
	
	@RequestMapping(value="weather.do", produces="application/json; charset=UTF-8")
	public String weather(int nx, int ny, String base_date, String base_time) throws IOException {
		
		// System.out.println(nx);
		// System.out.println(ny);
		System.out.println("base_date: " + base_date);
		System.out.println("base_time: " + base_time);
		
		String url = "http://apis.data.go.kr/1360000/VilageFcstInfoService_2.0/getVilageFcst";
			   url += "?serviceKey=" + SERVICEKEY;
			   url += "&numOfRows=500";
			   url += "&pageNo=1";
			   url += "&dataType=JSON";
			   url += "&base_date=" + base_date;
			   url += "&base_time=" + base_time;
			   url += "&nx=" + nx;
			   url += "&ny=" + ny;
		
		URL requestUrl = new URL(url);
		
		HttpURLConnection urlConnection = (HttpURLConnection)requestUrl.openConnection(); 
		
		urlConnection.setRequestMethod("GET");
		
		BufferedReader br = new BufferedReader(new InputStreamReader(urlConnection.getInputStream()));
		
		String responseText = "";
		String line;
		
		while((line = br.readLine()) != null) {
			// System.out.println("line: " + line);
			responseText += line; // 고쳐야할 곳. line 하나마다 재가공해서 table 에 insert
		}
		
		System.out.println("responseText: " + responseText);
		
		br.close();
		urlConnection.disconnect();
		
		return responseText;
		
	}
}
