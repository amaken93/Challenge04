/**
 * 
 */
package dao;

import java.sql.SQLException;

import exception.CampusException;
import model.Staff;

/**
 * 
 */
public class StaffDao extends BaseDao {
	public StaffDao() throws CampusException {
		super();
	}

	public Staff doLogin(int staffId, String loginPassword) throws CampusException {
		Staff loginUser = null;
		try {
			String sql = "SELECT * FROM staff WHERE staff_id = ? AND login_password = ?";
			ps = ct.prepareStatement(sql);
			ps.setInt(1, staffId);
			ps.setString(2, loginPassword);
			rs = ps.executeQuery();

			while (rs.next()) {
				int userId = rs.getInt("staff_id");
				String userName = rs.getString("staff_name");
				String password = rs.getString("login_password");

				loginUser = new Staff(userId, userName, password);
			}

			if (loginUser == null) {
				throw new CampusException("ログインできませんでした");
			}
		} catch (SQLException e) {
			// TODO: handle exception
			e.printStackTrace();
			throw new CampusException("SQL実行中に例外が発生しました");
		}

		return loginUser;
	}
}
