package test;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.ResponseBody;

public class OkHttp3Service {
	
	/*
	 * okHttp3
	 * 
	 * @param address
	 */
	
	public String getHttpInfo(String url) throws Exception{

		// OkHttp 클라이언트 생성
		OkHttpClient client = new OkHttpClient();
		
		// GET 요청 객체 생성
		Request request = new Request.Builder()
				.url(url)
				.get()
				.build();
		
		// OkHttp 클라이언트로 GET 요청 객체 전송
        Response response = client.newCall(request).execute();
        if (response.isSuccessful()) {
            // 응답 Body
            ResponseBody body = response.body();
            return body.string();
        } else
            System.err.println("Error Occurred");
        
        return null;
	}
}
