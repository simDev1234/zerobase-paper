package service;

import java.util.ArrayList;
import java.util.List;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import model.WifiVo;

public class WifiHttpLoad {
	
	public JsonObject getAllWifiData(int start, int end) {
		// HTTP 통해 json data 가져오기
		String jsonStr = WifiHttpService.getJson(
				"http://openapi.seoul.go.kr:8088/"
				+ "5a4b72775773696d3332464e676c74/json/TbPublicWifiInfo/" + start + "/" + end);
		
		// JsonParser로 파싱하기
		JsonElement element = JsonParser.parseString(jsonStr);
		JsonObject object = element.getAsJsonObject();
		JsonObject allData = object.get("TbPublicWifiInfo").getAsJsonObject();
		
		return allData;
	}
	
	public int getWifiDataMax() {
		
		// 공공데이터의 전체 데이터양
		int totalCnt = getAllWifiData(1,1).get("list_total_count").getAsInt();
		
		return totalCnt;
	}
	
	public List<WifiVo> getWifiList(int start, int end) {
		
		JsonArray wifiJsonArray = getAllWifiData(start, end).get("row").getAsJsonArray();
		List<WifiVo> list = new ArrayList<WifiVo>();
		
		for (int i = 0; i < wifiJsonArray.size(); i++) {
			
			JsonObject wifi = wifiJsonArray.get(i).getAsJsonObject();
			
			WifiVo vo = new WifiVo(
					wifi.get("X_SWIFI_MGR_NO").getAsString(), 
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
			
			list.add(vo);
		}
		
		return list;
	}
}
