package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import util.ConnUtil;
import vo.OrderItem;

public class OrderItemDao {

	// 주문상품정보
	public void insertOrderItem(OrderItem orderItem) {
		
		String sql = "insert into sample_order_items "
				   + "(order_no, product_no, product_amount, product_price) "
				   + "values "
				   + "(?, ?, ?, ?)";
		
		try {
			Connection conn = ConnUtil.getConnection();
			PreparedStatement pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, orderItem.getOrderNo());
			pstmt.setInt(2, orderItem.getProductNo());
			pstmt.setInt(3, orderItem.getAmount());
			pstmt.setInt(4, orderItem.getPrice());
			
			pstmt.executeQuery();
			
			pstmt.close();
			conn.close();
			
		} catch (SQLException ex) {
			throw new RuntimeException(ex);
		}
	}
	
	
	
}
