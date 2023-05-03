package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import util.ConnUtil;
import vo.Order;

public class OrderDao {

	public int getSequence() {
		String sql = "select sample_orders_seq.nextVal as seq from dual";
		
		try {
			int sequence = 0;
			
			Connection conn = ConnUtil.getConnection();
			PreparedStatement pstmt = conn.prepareStatement(sql);
			ResultSet rs = pstmt.executeQuery();
			
			// 시퀀스 sql을 실행하면 무조건 다음 행이 존재하기 때문에 if문을 사용하지 않았다.
			rs.next();
			
			sequence = rs.getInt("seq");
			
			rs.close();
			pstmt.close();
			conn.close();
			
			return sequence;
			
		} catch (SQLException ex) {
			throw new RuntimeException(ex);
		}
	}
	
	// 주문정보
	public void insertOrder(Order order) {
		String sql = "insert into sample_orders "
				   + "(order_no, total_order_price, used_point, total_credit_price, "
				   + "deposit_point, user_no) "
				   + "values "
				   + "(?, ?, ?, ?, ?, ?)" ;
		
		try {
			Connection conn = ConnUtil.getConnection();
			PreparedStatement pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, order.getNo());
			pstmt.setInt(2, order.getTotalOrderPrice());
			pstmt.setInt(3, order.getUsedPoint());
			pstmt.setInt(4, order.getTotalCreditPrice());
			pstmt.setInt(5, order.getDepositPoint());
			pstmt.setInt(6, order.getUserNo());
			
			pstmt.executeUpdate();
			
			pstmt.close();
			conn.close();
						
		} catch (SQLException ex) {
			throw new RuntimeException(ex);
		}
		
	}
	
	
	
}
