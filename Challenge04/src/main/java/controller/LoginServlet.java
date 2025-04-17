package controller;

import java.io.IOException;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import dao.StaffDao;
import exception.CampusException;
import model.Staff;

/**
 * Servlet implementation class LoginServlet
 */
@WebServlet("/LoginServlet")
public class LoginServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		HttpSession session = request.getSession();
		session.invalidate();
		
		request.setAttribute("message", "ログアウトしました");
		
		RequestDispatcher rd = request.getRequestDispatcher("login.jsp");
		rd.forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		int loginId = Integer.parseInt(request.getParameter("loginId"));
		String  password = request.getParameter("password");
		String nextpage = null;
		
		try {
			StaffDao sd = new StaffDao();
			Staff staff = sd.doLogin(loginId, password);
			
			HttpSession session = request.getSession();
			session.setAttribute("staff", staff);
			
			nextpage = "StudentServlet";
		}catch (CampusException e) {
			// TODO: handle exception
			String message = e.getMessage();
			request.setAttribute("message", message);
			request.setAttribute("error", "ture");
			
			nextpage = "login.jsp";
		}
		
		RequestDispatcher rd = request.getRequestDispatcher(nextpage);
		rd.forward(request, response);
	}

}
