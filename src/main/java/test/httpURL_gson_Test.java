package test;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import model.WifiVo;
import service.WifiDBService;

public class httpURL_gson_Test {
	public static void main(String[] args) {
      
		String jsonStr = "";
		
		// HTTP 연결 
		try {
			// 방법1 : URL과 HttpURLConnection으로 GET
			// - tcp/ip : 연결 -> 데이터 요청 -> 데이터 수신
			String key = "5a4b72775773696d3332464e676c74";
			String url = "http://openapi.seoul.go.kr:8088/" + key + "/json/TbPublicWifiInfo/1/10";
			
			URL api_url = new URL(url);
			HttpURLConnection httpUrlConnection = (HttpURLConnection)api_url.openConnection(); // 연결
			int responseCode = httpUrlConnection.getResponseCode(); // 연결 응답 코드
			
			BufferedReader br = null;
			
			if (responseCode == 200) {
				br = new BufferedReader(new InputStreamReader(httpUrlConnection.getInputStream(), "UTF-8"));
			} else {
				br = new BufferedReader(new InputStreamReader(httpUrlConnection.getErrorStream(), "UTF-8"));
			}
			
			jsonStr = br.readLine();
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		// 총 입력받은 갯수
		long totalCnt = 0; 
		
		// 기존 DB의 데이터와 비교
		WifiDBService wifiDBService = new WifiDBService();
		List<WifiVo> dbWifiList = wifiDBService.search(5);
		
		// JSON 방식으로 데이터 파싱 (Gson 라이브러리 사용)
		try {
			
			// 문자열 parsing
			JsonElement element = JsonParser.parseString(jsonStr);
			
			// element가 Object인 경우 jsonObject타입으로 변환
			JsonObject object = element.getAsJsonObject();
			JsonObject allData = object.get("TbPublicWifiInfo").getAsJsonObject();
			
			// 객체 및 배열 가져오기
			JsonArray wifiJsonArray = allData.get("row").getAsJsonArray();
			
			for (int i = 0; i < wifiJsonArray.size(); i++) {
				JsonObject wifi = wifiJsonArray.get(i).getAsJsonObject();
				
				String pk = wifi.get("X_SWIFI_MGR_NO").getAsString();
				
				boolean hasWifi = wifiDBService.hasWifi(pk);
				
				if (hasWifi) {
					continue;
				}
				
				totalCnt++;
				
				WifiVo vo = new WifiVo(
						pk, 
						wifi.get("X_SWIFI_WRDOFC").getAsString(), 
						wifi.get("X_SWIFI_MAIN_NM").getAsString(), 
						wifi.get("X_SWIFI_ADRES1").getAsString(),
						wifi.get("X_SWIFI_ADRES2").getAsString(), 
						wifi.get("X_SWIFI_INSTL_FLOOR").getAsString(), 
						wifi.get("X_SWIFI_INSTL_TY").getAsString(), 
						wifi.get("X_SWIFI_INSTL_MBY").getAsString(),
						wifi.get("X_SWIFI_SVC_SE").getAsString(), 
						wifi.get("X_SWIFI_CMCWR").getAsString(), 
						wifi.get("X_SWIFI_CNSTC_YEAR").getAsString(), 
						wifi.get("X_SWIFI_INOUT_DOOR").getAsString(),
						wifi.get("X_SWIFI_REMARS3").getAsString(), 
						wifi.get("LAT").getAsDouble(), 
						wifi.get("LNT").getAsDouble(), 
						wifi.get("WORK_DTTM").getAsString()	
				);
				
				System.out.println(pk);
				
				//wifiDBService.insert(vo);
			}
			
			System.out.println("totalCnt : " + totalCnt);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
