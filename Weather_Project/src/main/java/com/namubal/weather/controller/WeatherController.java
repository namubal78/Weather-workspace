package com.namubal.weather.controller;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

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
	
	// 기상청_단기예보 공공데이터 인증키
	public static final String SERVICEKEY = "ydPY7ABO4D6zNxj8P%2B6%2FsD%2B78wn8BM65XurwyxF8ETDo2SH2Qv644PoIWsq%2BpZK86nYY6YnLiL3XtzyEqfzn%2Fw%3D%3D"; 

	// 날씨 조회용 value enum 생성
	enum WeatherValue {
        TMP, UUU, VVV, VEC, WSD, SKY, PTY, POP, WAV, REH, TMN, TMX, PCP, SNO
    }
	
	@RequestMapping(value="weather.do", produces="application/json; charset=UTF-8")
	public String weather(int nx, int ny, String base_date, String base_time, String current_time, Model model) throws Exception {
		
		Weather weather = new Weather();
		
		// System.out.println(nx); 
		// System.out.println(ny); 
		// System.out.println("base_date: " + base_date);
		// System.out.println("base_time: " + base_time);
		// System.out.println("current_time: " + current_time);
		
		String url = "http://apis.data.go.kr/1360000/VilageFcstInfoService_2.0/getVilageFcst";
			   url += "?serviceKey=" + SERVICEKEY; // 인증키
			   url += "&numOfRows=500"; // 한 페이지 결과 수(일 최고기온, 일 최저기온 값 받아야 하기 때문에 500 이상) 
			   url += "&pageNo=1"; // 페이지 번호
			   url += "&dataType=JSON"; // 응답자료형식
			   url += "&base_date=" + base_date; // 발표일자
			   url += "&base_time=" + base_time; // 발표시각
			   url += "&nx=" + nx; // 예보지점 X 좌표
			   url += "&ny=" + ny; // 예보지점 Y 좌표
		
		URL requestUrl = new URL(url);
		
		HttpURLConnection urlConnection = (HttpURLConnection)requestUrl.openConnection(); 
		
		urlConnection.setRequestMethod("GET");
		urlConnection.setRequestProperty("Content-type", "application/json");

		BufferedReader br = new BufferedReader(new InputStreamReader(urlConnection.getInputStream()));
		
		String responseText = "";
		String line;
		
		while((line = br.readLine()) != null) {
			// System.out.println("line: " + line);
			responseText += line;
		}
		
		// System.out.println("responseText: " + responseText);
		
		br.close();
		urlConnection.disconnect();
		
		// 파싱
        JSONParser jsonParser = new JSONParser();
        JSONObject jsonObject = (JSONObject) jsonParser.parse(responseText); // String -> JSONObject
        JSONObject parse_response = (JSONObject) jsonObject.get("response");
        JSONObject parse_body = (JSONObject) parse_response.get("body");
        JSONObject parse_items = (JSONObject) parse_body.get("items");
        JSONArray parse_item = (JSONArray) parse_items.get("item"); // JSONObject -> JSONArray
        // System.out.println("parse_item: " + parse_item);
        
        // 1. 현재 시각 날씨 정보들 조회
        for (int i = 0; i < 14; i++) { // 한 시각 당 최대 14종류 코드값이므로 14개까지 조회
            
            JSONObject object;
            String category = ""; // 종류 변수 선언
            Double value = -100.0;
            String PCPValue = null; // 강수량 뿐만 아니라 '강수없음' 값이 존재하므로, Double이 아닌 String
            String SNOValue = null; // 적설량 뿐만 아니라 '적설없음' 값이 존재하므로, Double이 아닌 String
        	
        	object = (JSONObject) parse_item.get(i);
            category = (String) object.get("category"); // JSONObject -> String

            // 종류 키에 맞는 값을 형변환해서 PCPValue, SNOValue, value에 담음
            if(category.equals("PCP")) {
            	PCPValue = (String) object.get("fcstValue");
            } else if(category.equals("SNO")) {
            	SNOValue = (String) object.get("fcstValue");
            } else if (category.equals("TMP")) { // 다음 시각의 TMP 코드값으로 재할당 되는 것 방지하는 조건문
            	if(weather.getTMP() == 0.0) { // weather 객체에 TMP 값 담겨있지 않음 -> 담기
                	value = Double.parseDouble((String) object.get("fcstValue"));
            	} else { // 담겨있음 -> 기존에 담긴 값 꺼내서 value에 다시 담기
            		value = weather.getTMP();
            	}
            } else if (category.equals("UUU")) { // 다음 시각의 UUU 코드값으로 재할당 되는 것 방지하는 조건문
            	if(weather.getUUU() == 0.0) { // weather 객체에 UUU 값 담겨있지 않음 -> 담기
                	value = Double.parseDouble((String) object.get("fcstValue"));
            	} else { // 담겨있음 -> 기존에 담긴 값 꺼내서 value에 다시 담기
            		value = weather.getUUU();
            	}
            } else { // 그 외 종류
            	value = Double.parseDouble((String) object.get("fcstValue"));
            }
            
            // value를 weather 객체에 담음
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
        
        // 그 외 value weather 객체에 담음
        weather.setBaseDate(base_date);
        weather.setBaseTime(base_time);
        weather.setFcstDate(base_date);
        weather.setFcstTime(current_time);
        // DB 조회용 주키 변수(weatherNo) 생성하고 담음
        String weatherNo = base_date + base_time; 
        weather.setWeatherNo(weatherNo);
        
        // System.out.println("weather: " + weather);
        
        int result = weatherService.checkWeather(weatherNo); // insert 전 중복체크용 메소드 호출
        
        if(result != 0) { // 이미 insert
        	
        } else { // 아직 insert 안 함
        	weatherService.insertWeather(weather); // insert 메소드 호출
        }
        
        Weather weatherInfo = weatherService.selectWeather(weather);        
        model.addAttribute("weatherInfo", weatherInfo);       

        // 2. 일 최고기온, 일 최저기온 조회        
    	ArrayList<Double> weatherMaxMin = new ArrayList<Double>(); // 조회용 ArrayList 생성
        
        for (int i = 0; i < 335; i++) { // 한 시각 당 최대 14종류 코드값 * 24 시간이므로 336개까지 조회
            
            JSONObject object;
            String category = ""; // 종류 변수 선언
            Double value = -100.0;
        	
        	object = (JSONObject) parse_item.get(i);
            category = (String) object.get("category");
        	
            // 일 최고기온, 일 최저기온 값을 형변환해서 value에 담음
        	if(category.equals("TMX")) {
        		value= Double.parseDouble((String)object.get("fcstValue"));
        		weatherMaxMin.add(value);
        	} else if (category.equals("TMN")) {
        		value= Double.parseDouble((String)object.get("fcstValue"));
        		weatherMaxMin.add(value);
        	}
        }
        
        model.addAttribute("weatherMaxMin", weatherMaxMin);
        
        // 3. 단기 날씨 예보용 기온 조회(현재, 3시간 후, 6시간 후, 9시간 후)
    	ArrayList<Integer> weatherFcst = new ArrayList<Integer>(); // 사전 조회용 ArryList 생성
        
        for (int i = 0; i < 126; i++) { // 한 시각 당 최대 14종류 코드값 * 9 시간이므로 126개까지 조회
            
            JSONObject object;
            String category = ""; // 종류 변수 선언
            int value = -100;
        	
        	object = (JSONObject) parse_item.get(i);
            category = (String) object.get("category");
        	
            // 현재 ~ 9시간 후까지의 매 시각 기온 값을 형변환해서 value에 담음
        	if(category.equals("TMP")) {
        		value= Integer.parseInt((String)object.get("fcstValue"));
        		weatherFcst.add(value);
        	}
        }
        
    	ArrayList<Integer> weatherFcstInfo = new ArrayList<Integer>(); // 조회용 ArrayList 생성
    	
    	weatherFcstInfo.add(weatherFcst.get(0)); // 현재 기온 값을 담음
    	weatherFcstInfo.add(weatherFcst.get(3)); // 3시간 후의 기온 값을 담음
    	weatherFcstInfo.add(weatherFcst.get(6)); // 6시간 후의 기온 값을 담음
    	weatherFcstInfo.add(weatherFcst.get(9)); // 9시간 후의 기온 값을 담음
        
        model.addAttribute("weatherFcstInfo", weatherFcstInfo);
        
        return "weatherCurrentInfo"; // Model addAttribute 했기 때문에, 조회용 jsp로 return 하지 않고 가공용 jsp로 return
        
	}
}
