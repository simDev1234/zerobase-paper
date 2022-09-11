package controller;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import model.WifiHistoryVo;
import service.WifiDBService;

/**
 * Servlet implementation class WifiSearchLogAction
 */
@WebServlet("/searchLog.do")
public class WifiSearchLogServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public WifiSearchLogServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#service(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		WifiDBService wifiService = new WifiDBService();
		List<WifiHistoryVo> wifiHistoryList = wifiService.searchLog();
		request.setAttribute("wifiHistoryList", wifiHistoryList);
		request.getRequestDispatcher("history.jsp").forward(request, response);
	}

}
