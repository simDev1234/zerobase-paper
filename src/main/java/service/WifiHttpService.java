package service;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class WifiHttpService {
	
	public static String getJson(String apiUrl) {
		String jsonStr = "";
		
		// HTTP 연결 
		try {
			
			URL url = new URL(apiUrl);
			HttpURLConnection httpUrlConnection = (HttpURLConnection)url.openConnection(); // 연결
			int responseCode = httpUrlConnection.getResponseCode(); // 연결 응답 코드
			
			BufferedReader br = null;
			
			if (responseCode == 200) {
				br = new BufferedReader(new InputStreamReader(httpUrlConnection.getInputStream(), "UTF-8"));
			} else {
				br = new BufferedReader(new InputStreamReader(httpUrlConnection.getErrorStream(), "UTF-8"));
			}
			
			jsonStr = br.readLine();
			
		} catch (Exception e) {
			System.out.println("HTTP 연결 오류");
			e.getStackTrace();
		}
		
		return jsonStr;
	}
	
}
