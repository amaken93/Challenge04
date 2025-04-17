package model;

public class Staff {
	private int staffId;
	private String staffName;
	private String  password;
	
	public Staff(int staffId, String staffName, String password) {
		this.staffId = staffId;
		this.staffName = staffName;
		this.password = password;
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

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}
}
