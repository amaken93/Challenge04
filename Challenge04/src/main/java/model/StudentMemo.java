package model;

public class StudentMemo {
	private String studentNumber;
	private String studentName;
	private int staffId;
	private String staffName;
	private int memoId;
	private String memo;

	public StudentMemo(String studentNumber, String studentName, int staffId, String staffName, int memoId,
			String memo) {
		this.studentNumber = studentNumber;
		this.studentName = studentName;
		this.staffId = staffId;
		this.staffName = staffName;
		this.memoId = memoId;
		this.memo = memo;
	}

	public StudentMemo(int staffId, String staffName) {
		this.studentNumber = "";
		this.studentName = "";
		this.staffId = staffId;
		this.staffName = staffName;
		this.memoId = 0;
		this.memo = "";
	}

	public String getStudentNumber() {
		return studentNumber;
	}

	public void setStudentNumber(String studentNumber) {
		this.studentNumber = studentNumber;
	}

	public String getStudentName() {
		return studentName;
	}

	public void setStudentName(String studentName) {
		this.studentName = studentName;
	}

	public int getStaffId() {
		return staffId;
	}

	public void setStaffId(int staffId) {
		this.staffId = staffId;
	}

	public String getStaffName() {
		return staffName;
	}

	public void setStaffName(String staffName) {
		this.staffName = staffName;
	}

	public int getMemoId() {
		return memoId;
	}

	public void setMemoId(int memoId) {
		this.memoId = memoId;
	}

	public String getMemo() {
		return memo;
	}

	public void setMemo(String memo) {
		this.memo = memo;
	}

}
