package controller;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import service.WifiDBService;

/**
 * Servlet implementation class WifiSaveLogServlet
 */
@WebServlet("/wifiSaveLog.do")
public class WifiSaveLogServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	@Override
	protected void service(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		double myLAT = Double.parseDouble(request.getParameter("LAT"));
		double myLNT = Double.parseDouble(request.getParameter("LNT"));

		// 위치 검색 히스토리에 와이파이 정보 입력
		new WifiDBService().saveLog(myLAT, myLNT);

		request.setAttribute("myLAT", myLAT);
		request.setAttribute("myLNT", myLNT);
		request.setAttribute("pg_start", request.getParameter("pg_start"));
		request.setAttribute("wifiList", request.getParameter("wifiList"));
		request.setAttribute("size", request.getParameter("size"));

		request.getRequestDispatcher("wifiSearch.do").forward(request, response);

		//response.sendRedirect("wifiSearch.do");
	}

}
