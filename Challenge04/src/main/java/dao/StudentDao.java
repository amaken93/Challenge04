package dao;

import java.sql.Date;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

import exception.CampusException;
import model.Student;
import model.StudentMemo;

public class StudentDao extends BaseDao {

	public StudentDao() throws CampusException {
		super();
		// TODO 自動生成されたコンストラクター・スタブ
	}
	
	public ArrayList<Student> findAllStudent() throws CampusException{
		ArrayList<Student> studentList = new ArrayList<Student>();
		try {
			String sql = "SELECT * FROM student";
			
			ps = ct.prepareStatement(sql);
			rs = ps.executeQuery();
			
			while(rs.next()) {
				String studentNumber = rs.getString("student_number");
				String studentName = rs.getString("student_name");
				
				Student student = new Student(studentNumber, studentName);
				
				studentList.add(student);
			}
		}catch (SQLException e) {
			// TODO: handle exception
			e.printStackTrace();
			throw new CampusException("学生情報の取得に失敗しました");
		}
		
		return studentList;
	}
	
	public Student findStudent(String findStudentNumber) throws CampusException {
		Student student = null;
		try {
			String sql = "SELECT * FROM student WHERE student_number = ?";
			
			ps = ct.prepareStatement(sql);
			ps.setString(1, findStudentNumber);
			rs = ps.executeQuery();
			
			while(rs.next()) {
				String studentNumber = rs.getString("student_number");
				String studentName = rs.getString("student_name");
				
				student = new Student(studentNumber, studentName);
			}
		}catch (SQLException e) {
			// TODO: handle exception
			e.printStackTrace();
			throw new CampusException("指定された学生情報の取得に失敗しました");
		}
		return student;
	}
	
	public StudentMemo findStudentMemo(String findStudentNumber) throws CampusException{
		StudentMemo studentMemo = null;
		try {
			String sql ="SELECT student.student_number, student.student_name, "
					+ "staff.staff_id, staff.staff_name, "
					+ "memo.memo_id, memo.memo, memo.update_date"
					+ "FROM memo "
					+ "INNER JOIN student ON memo.student_number = student.student_number "
					+ "INNER JOIN staff ON memo.updated_staff_id = staff.staff_id "
					+ "WHERE memo.student_number = ?";
			
			ps = ct.prepareStatement(sql);
			ps.setString(1, findStudentNumber);
			rs = ps.executeQuery();
			
			while (rs.next()) {
				String studentNumber = rs.getString("student_number");
				String studentName = rs.getString("student_name");
				int staffId = rs.getInt("staff_id");
				String staffName = rs.getString("staff_name");
				int memoId = rs.getInt("memo_id");
				String memo = rs.getString("memo");

				studentMemo = new StudentMemo(studentNumber, studentName, staffId, staffName, memoId, memo);
			}
		} catch (SQLException e) {
			// TODO: handle exception
			e.printStackTrace();
			throw new CampusException("指定された学生メモ情報の取得に失敗しました");
		}
		return studentMemo;
	}

	public void insertStudent(StudentMemo studentMemo) throws CampusException {
		String studentNumber = studentMemo.getStudentNumber();
		String studentName = studentMemo.getStudentName();
		int staffId = studentMemo.getStaffId();
		String memo = studentMemo.getMemo();

		try {
			String sql = "INSERT INTO student(student_number, student_name) VALUES(?, ?)";
			ps = ct.prepareStatement(sql);
			ps.setString(1, studentNumber);
			ps.setString(2, studentName);
			ps.executeUpdate();
			ct.commit();

			sql = "INSERT INTO memo(student_number, updated_staff_id, memo, update_date) VALUES(?, ?, ?, ?)";
			ps = ct.prepareStatement(sql);
			ps.setString(1, studentNumber);
			ps.setInt(2, staffId);
			ps.setString(3, memo);
			ps.setDate(4, Date.valueOf(now()));
			ps.executeUpdate();
			ct.commit();
		} catch (SQLException e) {
			// TODO: handle exception
			e.printStackTrace();
			throw new CampusException("学生情報の登録に失敗しました");
		}
	}

	public void updateMemo(StudentMemo studentMemo, int memoId) throws CampusException {
		String memo = studentMemo.getMemo();
		int staffId = studentMemo.getStaffId();
		try {
			String sql = "UPDATE memo SET memo = ?, updated_staff_id = ? update_date = ? WHERE memo_id = ?";
			ps = ct.prepareStatement(sql);
			ps.setString(1, memo);
			ps.setInt(2, staffId);
			ps.setInt(3, memoId);
			ps.setDate(4, Date.valueOf(now()));
			ps.executeUpdate();
			ct.commit();
		} catch (SQLException e) {
			// TODO: handle exception
			e.printStackTrace();
			throw new CampusException("メモの更新に失敗しました");
		}
	}

	public int findMemoId(String studentNumber) throws CampusException {
		int memoId = 0;
		try {
			String sql = "SELECT memo_id FROM memo WHERE student_number = ?";
			ps = ct.prepareStatement(sql);
			ps.setString(1, studentNumber);
			rs = ps.executeQuery();
			while (rs.next()) {
				memoId = rs.getInt("memo_id");
			}
		} catch (SQLException e) {
			// TODO: handle exception
			e.printStackTrace();
			throw new CampusException("メモIDの取得に失敗しました");
		}
		return memoId;
	}

	public String now() {
		LocalDateTime now = LocalDateTime.now();
		DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");
		return now.format(dtf);
	}
}
