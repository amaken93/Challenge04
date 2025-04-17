package dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import exception.CampusException;

public abstract class BaseDao {
	protected Connection ct = null;
	protected PreparedStatement ps = null;
	protected ResultSet rs = null;

	public BaseDao() throws CampusException {
		getConnection();
	}

	private void getConnection() throws CampusException {
		try {
			if (ct == null) {
				Class.forName("org.postgresql.Driver");

				String url = "jdbc:postgresql://127.0.0.1:5432/campus";
				String user = "postgres";
				String password = "";

				ct = DriverManager.getConnection(url, user, password);
				ct.setAutoCommit(false);
			}
		} catch (ClassNotFoundException e) {
			// TODO: handle exception
			e.printStackTrace();
			throw new CampusException("JDBCドライバが見つかりません");
		} catch (SQLException e) {
			// TODO: handle exception
			e.printStackTrace();
			throw new CampusException("SQL実行中に例外が発生しました");
		}
	}

	protected void close() throws CampusException {
		try {
			if (rs != null) {
				rs.close();
			}
			if (ps != null) {
				ps.close();
			}
			if (ct != null) {
				ct.close();
			}
		} catch (SQLException e) {
			// TODO: handle exception	
			e.printStackTrace();
			throw new CampusException("close処理中に例外が発生しました");
		}
	}
}
