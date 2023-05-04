package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import util.ConnUtil;
import vo.Order;

public class OrderDao {

	public Order getOrderByNo(int orderNo) {
	
		String sql = "select * "
				   + "from sample_orders "
				   + "where order_no = ? ";
		
		try {
			
			Order order = null;
			
			Connection conn = ConnUtil.getConnection();
			PreparedStatement pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, orderNo);
			
			ResultSet rs = pstmt.executeQuery();
			while(rs.next()) {
				order = new Order();
				
				order.setNo(rs.getInt("ORDER_NO"));
				order.setCreateDate(rs.getDate("ORDER_CREATE_DATE"));
				order.setStatus(rs.getString("order_status"));
				order.setTotalOrderPrice(rs.getInt("TOTAL_ORDER_PRICE"));
				order.setUsedPoint(rs.getInt("used_point"));
				order.setTotalCreditPrice(rs.getInt("total_credit_price"));
				order.setDepositPoint(rs.getInt("DEPOSIT_POINT"));
				order.setUserNo(rs.getInt("user_no"));
				}
			
			rs.close();
			pstmt.close();
			conn.close();
			
			return order;
			
		} catch (SQLException ex) {
			throw new RuntimeException(ex);
		}
		
	}
	
	
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
	
	
	public List<Order> getOrdersByUserNo(int userNo){
		
		String sql = "SELECT ORDER_NO, ORDER_CREATE_DATE, ORDER_STATUS, "
				   + "TOTAL_ORDER_PRICE, USED_POINT, TOTAL_CREDIT_PRICE, "
				   + "DEPOSIT_POINT, USER_NO "
				   + "FROM SAMPLE_orderS "
				   + "WHERE USER_NO = ? "
				   + "ORDER BY ORDER_NO DESC ";
		
		try {
			List<Order> orders = new ArrayList<>();
			
			Connection conn = ConnUtil.getConnection();
			PreparedStatement pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, userNo);
			
			ResultSet rs = pstmt.executeQuery();
			while(rs.next()) {
				Order order = new Order();
				
				order.setNo(rs.getInt("ORDER_NO"));
				order.setCreateDate(rs.getDate("ORDER_CREATE_DATE"));
				order.setStatus(rs.getString("order_status"));
				order.setTotalOrderPrice(rs.getInt("TOTAL_ORDER_PRICE"));
				order.setUsedPoint(rs.getInt("used_point"));
				order.setTotalCreditPrice(rs.getInt("total_credit_price"));
				order.setDepositPoint(rs.getInt("DEPOSIT_POINT"));
				order.setUserNo(rs.getInt("user_no"));
				
				orders.add(order);
			}
			
			rs.close();
			pstmt.close();
			conn.close();
			
			return orders;
			
		} catch (SQLException ex) {
			throw new RuntimeException(ex);
		}
	} // 메서드명


}
