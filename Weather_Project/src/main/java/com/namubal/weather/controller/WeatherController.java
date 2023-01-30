package com.namubal.weather.controller;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class WeatherController {

	public static final String SERVICEKEY = "ydPY7ABO4D6zNxj8P%2B6%2FsD%2B78wn8BM65XurwyxF8ETDo2SH2Qv644PoIWsq%2BpZK86nYY6YnLiL3XtzyEqfzn%2Fw%3D%3D";
	
	enum WeatherValue {
        TMP, UUU, VVV, VEC, WSD, POP, WAV, PCP, REH, SNO, TMN, TMX
    }
	
	@ResponseBody
	@RequestMapping(value="weather.do", produces="application/json; charset=UTF-8")
	public JSONArray weather(int nx, int ny, String base_date, String base_time) throws Exception {
		
		// System.out.println(nx);
		// System.out.println(ny);
		// System.out.println("base_date: " + base_date);
		// System.out.println("base_time: " + base_time);
		
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
        
        /* jsonArray를 반복자로 반복
        for (int i = 0; i < 13; i++) { // 시각당 최대 14 종류 코드값이므로 14개까지 조회
            object = (JSONObject) parse_item.get(i);
            category = (String) object.get("category"); // item 에서 카테고리를 검색

            // Error 발생할수도 있으며 받아온 정보를 double이 아니라 문자열로 읽으면 오류
            value = Double.parseDouble((String) object.get("obsrValue"));

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
                case POP:
                    weather.setPOP(value);
                    break;
                case WAV:
                    weather.setWAV(value);
                    break;
                case PCP:
                    weather.setPCP(value);
                    break;                
                case REH:
                    weather.setREH(value);
                    break;                
                case SNO:
                    weather.setSNO(value);
                    break;                
                case TMN:
                    weather.setTMN(value);
                    break;                
                case TMX:
                    weather.setTMX(value);
                    break;
                default:
                    break;
            }
        }
        weather.setDate(baseDate);
        weather.setTime(baseTime);
        weather.setTime(fcstDate);
        weather.setTime(fcstTime);
        // 잘 출력되는지 확인하고 싶으면 아래 주석 해제
        // System.out.println(weather);
        */
        
        return parse_item;
        
	}
}
