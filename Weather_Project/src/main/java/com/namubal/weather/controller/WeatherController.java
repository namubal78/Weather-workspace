package com.namubal.weather.controller;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.namubal.weather.model.service.WeatherService;
import com.namubal.weather.model.vo.Weather;

@Controller
public class WeatherController {

	@Autowired
	private WeatherService weatherService;
	
	public static final String SERVICEKEY = "ydPY7ABO4D6zNxj8P%2B6%2FsD%2B78wn8BM65XurwyxF8ETDo2SH2Qv644PoIWsq%2BpZK86nYY6YnLiL3XtzyEqfzn%2Fw%3D%3D";

	enum WeatherValue {
        TMP, UUU, VVV, VEC, WSD, SKY, PTY, POP, WAV, REH, TMN, TMX, PCP, SNO
    }
	
	@RequestMapping(value="weather.do", produces="application/json; charset=UTF-8")
	public String weather(int nx, int ny, String base_date, String base_time, String current_time, Model model) throws Exception {
		
		Weather weather = new Weather();
		
		System.out.println(nx);
		System.out.println(ny);
		System.out.println("base_date: " + base_date);
		System.out.println("base_time: " + base_time);
		System.out.println("current_time: " + current_time);
		
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
		urlConnection.setRequestProperty("Content-type", "application/json");

		BufferedReader br = new BufferedReader(new InputStreamReader(urlConnection.getInputStream()));
		
		String responseText = "";
		String line;
		
		while((line = br.readLine()) != null) {
			// System.out.println("line: " + line);
			responseText += line; // 고쳐야할 곳. line 하나마다 재가공해서 table 에 insert
		}
		
		// System.out.println("responseText: " + responseText);
		
		br.close();
		urlConnection.disconnect();
				
        JSONParser jsonParser = new JSONParser();
        JSONObject jsonObject = (JSONObject) jsonParser.parse(responseText);
        JSONObject parse_response = (JSONObject) jsonObject.get("response");
        JSONObject parse_body = (JSONObject) parse_response.get("body"); // response 로 부터 body 찾아오기
        JSONObject parse_items = (JSONObject) parse_body.get("items"); // body 로 부터 items 받아오기
        // items 로 부터 itemList : 뒤에 [ 로 시작하므로 jsonArray 이다.
        JSONArray parse_item = (JSONArray) parse_items.get("item");
        
        System.out.println("parse_item: " + parse_item);
        
                
        // jsonArray를 반복자로 반복
        for (int i = 0; i < 14; i++) { // 시각당 최대 14 종류 코드값이므로 14개까지 조회
            
            // item 들을 담은 List 를 반복자 안에서 사용하기 위해 미리 명시
            JSONObject object;
            // item 내부의 category 를 보고 사용하기 위해서 사용
            String category = "";
            Double value = -100.0; // 존재할 수 없는 코드값 할당
            String PCPValue = null;
            String SNOValue = null;
        	
        	object = (JSONObject) parse_item.get(i);
            category = (String) object.get("category"); // item 에서 카테고리를 검색

            if(category.equals("PCP")) {
            	
            	PCPValue = (String) object.get("fcstValue");
            	
            } else if(category.equals("SNO")) {
            	
            	SNOValue = (String) object.get("fcstValue");
            	
            } else if (category.equals("TMP")) {
            	
            	if(weather.getTMP() == 0.0) {
            		
                	value = Double.parseDouble((String) object.get("fcstValue"));
            	
            	} else {
            		
            		value = weather.getTMP();
            		
            	}
            	
            } else if (category.equals("UUU")) {
            	
            	if(weather.getUUU() == 0.0) {
            		
                	value = Double.parseDouble((String) object.get("fcstValue"));
                	
            	} else {
            		
            		value = weather.getUUU();
            	}
            	
            } else {

            	value = Double.parseDouble((String) object.get("fcstValue"));
            
            }
            
            WeatherValue weatherValue = WeatherValue.valueOf(category);
            
            switch (weatherValue) {
                case TMP:
            		weather.setTMP(value);
                    break;
                case UUU:
                    weather.setUUU(value);
                    break;
                case VVV:
                    weather.setVVV(value);
                    break;
                case VEC:
                    weather.setVEC(value);
                    break;
                case WSD:
                    weather.setWSD(value);
                    break;
                case SKY:
                    weather.setSKY(value);
                    break;
                case PTY:
                    weather.setPTY(value);
                    break;
                case POP:
                    weather.setPOP(value);
                    break;
                case WAV:
                    weather.setWAV(value);
                    break;
                case REH:
                    weather.setREH(value);
                    break;                              
                case TMN:
                    weather.setTMN(value);
                    break;                
                case TMX:
                    weather.setTMX(value);
                    break;
                case PCP:
                    weather.setPCP(PCPValue);
                    break;    
                case SNO:
                    weather.setSNO(SNOValue);
                    break;  
                default:
                    break;
            }
        }
        
        weather.setBaseDate(base_date);
        weather.setBaseTime(base_time);
        weather.setFcstDate(base_date);
        weather.setFcstTime(current_time);
        
        String weatherNo = base_date + base_time;
        weather.setWeatherNo(weatherNo);
        
        System.out.println("weather: " + weather);
        
        int result = weatherService.checkWeather(weatherNo);
        
        if(result != 0) { // 이미 insert 했던 예보
        	
        } else {
        	weatherService.insertWeather(weather);
        }
        
        Weather weatherInfo = weatherService.selectWeather(weather);        
        model.addAttribute("weatherInfo", weatherInfo);       
        System.out.println("weatherInfo: " + weatherInfo);
        
        return "weatherCurrentInfo";
        
	}
}
