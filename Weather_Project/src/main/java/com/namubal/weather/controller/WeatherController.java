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
	public String weather() throws IOException {
				
		String url = "http://apis.data.go.kr/1360000/VilageFcstInfoService_2.0/getVilageFcst";
			   url += "?serviceKey=" + SERVICEKEY;
			   url += "&numOfRows=10";
			   url += "&pageNo=1";
			   url += "&dataType=JSON";
			   url += "&base_date=20230128";
			   url += "&base_time=0500";
			   url += "&nx=55";
			   url += "&ny=127";
		
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
