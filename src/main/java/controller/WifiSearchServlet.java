package controller;

import java.io.IOException;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import model.WifiVo;
import service.WifiDBService;
import test.OkHttp3Service;

/**
 * Servlet implementation class WifiSearchAction
 */
@WebServlet("/wifiSearch.do")
public class WifiSearchServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public WifiSearchServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#service(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		// 데이터 범위
		int pg_start = Integer.parseInt(request.getParameter("pg_start"));
		
		// 등록된 공공 와이파이 목록 검색
		WifiDBService wifiDBService = new WifiDBService();
		
		// 위도, 경도 정보를 통해 거리 구하기
		double myLAT = Double.parseDouble(request.getParameter("LAT"));
		double myLNT = Double.parseDouble(request.getParameter("LNT"));
		List<WifiVo> wifiList = wifiDBService.search(new double[]{myLAT, myLNT}, pg_start);
	   	
		/*
		 * for (WifiVo vo : wifiList) {
		 * 
		 * Double curLAT = vo.getLAT(); Double curLNT = vo.getLNT();
		 * 
		 * Double distance = Math.sin(curLAT * Math.PI / 180.0) * Math.sin(myLAT *
		 * Math.PI / 180.0) + Math.cos(curLNT * Math.PI / 180.0) * Math.cos(myLNT *
		 * Math.PI / 180.0) Math.cos((curLNT - myLNT) * Math.PI / 180.0);
		 * 
		 * vo.setDISTANCE(distance); }
		 */
	   	
	   	request.setAttribute("myLAT", myLAT);
	    request.setAttribute("myLNT", myLNT);
	    request.setAttribute("pg_start", pg_start);
	   	request.setAttribute("wifiList", wifiList);	
	   	request.setAttribute("size", wifiDBService.getSize());
	   	
		/*
		 * if (isLogPoint) { // wifiSaveLog.do로 이동
		 * request.getRequestDispatcher("wifiSaveLog.do").forward(request, response); }
		 * else { // 클라이언트로 이동
		 * request.getRequestDispatcher("index.jsp").forward(request, response); }
		 */
	   	request.getRequestDispatcher("index.jsp").forward(request, response);
	   	
		
	}

}
