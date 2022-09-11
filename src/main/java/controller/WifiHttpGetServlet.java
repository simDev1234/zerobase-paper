package controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import model.WifiVo;
import service.WifiDBService;
import service.WifiHttpLoad;
import service.WifiHttpService;

/**
 * Servlet implementation class WifiHttpGetAction
 */
@WebServlet("/httpGet.do")
public class WifiHttpGetServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public WifiHttpGetServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#service(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		WifiDBService wifiDBService = new WifiDBService();
		
		int totalCnt = 0;
		int start = 1;
		int success = 0;
		
		try {
			
			WifiHttpLoad wifiHttpLoad = new WifiHttpLoad();
			
			// 기존 DB 삭제
			wifiDBService.delete();
			
			// 공공데이터의 전체 데이터양
			totalCnt = wifiHttpLoad.getWifiDataMax();
			
			// 공공데이터의 데이터를 저장할 리스트 - 500개 단위로 저장
			List<WifiVo> wifiList = new ArrayList<>();
			
			// List에 담기
			while (start <= totalCnt) {
				
				if (start + 500 > totalCnt) {
					wifiList.addAll(wifiHttpLoad.getWifiList(start, totalCnt));
					break;
				}
				
				wifiList.addAll(wifiHttpLoad.getWifiList(start, start + 500));				
				start = start + 500;
			}
			
			// DB에 저장
			wifiDBService.insert(wifiList);
			
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("데이터 로드 실패");
		}
		
		// forward
		request.setAttribute("totalCnt", totalCnt);
		request.getRequestDispatcher("load-success.jsp").forward(request, response);
	}

}
