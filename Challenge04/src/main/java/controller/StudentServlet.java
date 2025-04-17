package controller;

import java.io.IOException;
import java.util.List;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import dao.StudentDao;
import exception.CampusException;
import model.Staff;
import model.Student;
import model.StudentMemo;

/**
 * Servlet implementation class StudentServlet
 */
@WebServlet("/StudentServlet")
public class StudentServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		String list = request.getParameter("list");
		if (list == null) {
			String findStudentNumber = request.getParameter("student_number");

			HttpSession session = request.getSession(false);
			Staff staff = (Staff) session.getAttribute("staff");
			StudentMemo studentMemo = null;
			try {
				if (findStudentNumber != null) {
					StudentDao studentDao = new StudentDao();
					studentMemo = studentDao.findStudentMemo(findStudentNumber);
				} else {
					int staffId = staff.getStaffId();
					String staffName = staff.getStaffName();
					studentMemo = new StudentMemo(staffId, staffName);
				}
				request.setAttribute("studentMemo", studentMemo);
			} catch (CampusException e) {
				// TODO: handle exception
				e.printStackTrace();
				String message = e.getMessage();
				request.setAttribute("message", message);
				request.setAttribute("error", "true");
			}
			RequestDispatcher rd = request.getRequestDispatcher("detail.jsp");
			rd.forward(request, response);
		}else {
			// backのリクエストがある場合は一覧画面を表示
			// 今回はdoPostメソッドに処理を移譲するようにしています
			doPost(request, response);
		}

	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		String nextPage = null;
		try {
			StudentDao studentDao = new StudentDao();
			List<Student> studentList = studentDao.findAllStudent();

			request.setAttribute("studentList", studentList);

			nextPage = "list.jsp";
		} catch (CampusException e) {
			// TODO: handle exception
			String message = e.getMessage();
			request.setAttribute("message", message);
			request.setAttribute("error", "true");

			nextPage = "login.jsp";
		}
		RequestDispatcher rd = request.getRequestDispatcher(nextPage);
		rd.forward(request, response);
	}

}
