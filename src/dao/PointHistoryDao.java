package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import util.ConnUtil;
import vo.PointHistory;

public class PointHistoryDao {

	public List<PointHistory> getHistoryByUserNo(int userNo){
		
		String sql = "select * "
				   + "from sample_point_histories "
				   + "where user_no = ? "
				   + "order by create_date desc";
		
		try {
			List<PointHistory> histories = new ArrayList<>();
			
			Connection con = ConnUtil.getConnection();
			PreparedStatement pstmt = con.prepareStatement(sql);
			pstmt.setInt(1, userNo);
			
			ResultSet rs = pstmt.executeQuery();
			while(rs.next()) {
				PointHistory history = new PointHistory();
				history.setUserNo(rs.getInt("user_no"));
				history.setOrderNo(rs.getInt("order_no"));
				history.setDepositPoint(rs.getInt("deposit_point"));
				history.setCurrentPoint(rs.getInt("current_point"));
				history.setCreateDate(rs.getDate("create_date"));
				
				histories.add(history);
			}
			rs.close();
			pstmt.close();
			con.close();
			
			return histories;
			
		} catch (SQLException ex) {
			throw new RuntimeException(ex);
		}
	}
	
	
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
