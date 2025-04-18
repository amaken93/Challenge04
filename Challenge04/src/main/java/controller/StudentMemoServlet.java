package controller;

import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import dao.StudentDao;
import exception.CampusException;
import model.Staff;
import model.StudentMemo;

/**
 * Servlet implementation class StudentMemoServlet
 */
@WebServlet("/StudentMemoServlet")
public class StudentMemoServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		response.getWriter().append("Served at: ").append(request.getContextPath());
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		HttpSession session = request.getSession(false);
		Staff staff = (Staff) session.getAttribute("staff");
		int staffId = staff.getStaffId();
		String staffName = staff.getStaffName();

		request.setCharacterEncoding("UTF-8");
		String studentNumber = request.getParameter("student_number");
		String studentName = request.getParameter("student_name");
		String memo = request.getParameter("memo");
		
		LocalDate now = LocalDate.now();
		DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd");
		String snow =now.format(dtf);
		System.out.println(snow);

		String message = null;
		try {
			StudentDao studentDao = new StudentDao();
			int memoId = studentDao.findMemoId(studentNumber);
			System.out.println(memoId);

			StudentMemo studentMemo = new StudentMemo(studentNumber, studentName, staffId, staffName, memoId, memo, snow);

			if (memoId == 0) {
				studentDao.insertStudent(studentMemo);
				System.out.println(studentMemo.getDate());
				System.out.println("insert");
				message = "学生情報を登録しました";
			} else {
				studentDao.updateMemo(studentMemo, memoId);
				message = "メモを更新しました";
			}
			request.setAttribute("studentMemo", studentMemo);
		} catch (CampusException e) {
			// TODO: handle exception
			message = e.getMessage();
			request.setAttribute("error", "true");
			e.printStackTrace();
		}
		request.setAttribute("message", message);
		request.getRequestDispatcher("detail.jsp").forward(request, response);
	}

}
