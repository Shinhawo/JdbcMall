package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import util.ConnUtil;
import vo.PointHistory;

public class PointHistoryDao {

	public void insertHistory(PointHistory history) {
		String sql = "insert into sample_point_histories "
				   + "(user_no, order_no, deposit_point, current_point) "
				   + "values "
				   + "(?, ?, ?, ?)";
		
		try {
			Connection conn = ConnUtil.getConnection();
			PreparedStatement pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, history.getUserNo());
			pstmt.setInt(2, history.getOrderNo());
			pstmt.setInt(3, history.getDepositPoint());
			pstmt.setInt(4, history.getCurrentPoint());
			
			pstmt.executeQuery();
			
			pstmt.close();
			conn.close();
		
		} catch (SQLException ex) {
			throw new RuntimeException(ex);
		}
	}
	
	
}
