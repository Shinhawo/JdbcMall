package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import dto.CartItemDto;
import oracle.net.nt.TcpMultiplexer;
import util.ConnUtil;
import vo.CartItem;

public class CartItemDao {

	
	public void insertCartItem(CartItem cartItem) {
		String sql = "INSERT INTO SAMPLE_CART_ITEMS "
	               + "(USER_NO, PRODUCT_NO, ITEM_AMOUNT) "
				   + "VALUES (?,?,?) ";
		
		try {
			Connection conn = ConnUtil.getConnection();
			PreparedStatement pstmt = conn.prepareStatement(sql);
			
			pstmt.setInt(1, cartItem.getUserNo());
			pstmt.setInt(2, cartItem.getProductNo());
			pstmt.setInt(3, cartItem.getAmount());
			
			pstmt.executeUpdate();
			
			pstmt.close();
			conn.close();
			
		} catch (SQLException ex) {
			throw new RuntimeException(ex);
		}
	
	} // 메서드 끝
	
	
	public CartItem getCartItem(int userNo, int productNo) {
		
		String sql = "SELECT * "
				   + "FROM SAMPLE_CART_ITEMS "
				   + "WHERE USER_NO = ? "
				   + "AND PRODUCT_NO = ?";
		
		try {
			CartItem cartItem = null;
			
			Connection conn = ConnUtil.getConnection();
			PreparedStatement pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, userNo);
			pstmt.setInt(2, productNo);
			
			ResultSet rs = pstmt.executeQuery();
			if(rs.next()) {
				cartItem = new CartItem();
				cartItem.setUserNo(rs.getInt("user_no"));
				cartItem.setProductNo(rs.getInt("product_no"));
				cartItem.setProductNo(rs.getInt("item_amount"));
			}
			
			rs.close();
			pstmt.close();
			conn.close();
			
			return cartItem;
			
		} catch (SQLException ex) {
			throw new RuntimeException(ex);
		}
		
	}// 메서드 끝
	
	
	public List<CartItemDto> getCartItemDtoByUserNo(int userNo){
		
		String sql = "SELECT C.PRODUCT_NO, P.PRODUCT_PRICE, C.ITEM_AMOUNT, "
				   + "P.PRODUCT_NAME "
				   + "FROM SAMPLE_CART_ITEMS C, SAMPLE_PRODUCTS P "
				   + "WHERE C.PRODUCT_NO = P.PRODUCT_NO "
				   + "AND C.USER_NO = ?";
		
		try {
			List<CartItemDto> dtos = new ArrayList<>();
			
			Connection conn = ConnUtil.getConnection();
			PreparedStatement pstmt = conn.prepareStatement(sql);
			
			pstmt.setInt(1, userNo);
			
			ResultSet rs = pstmt.executeQuery();
			while(rs.next()) {
				CartItemDto dto = new CartItemDto();
				dto.setProductNo(rs.getInt("product_no"));
				dto.setProductPrice(rs.getInt("product_price"));
				dto.setItemAmount(rs.getInt("item_amount"));
				dto.setName(rs.getString("product_name"));
				
				dtos.add(dto);
			}
			rs.close();
			pstmt.close();
			conn.close();
			
			return dtos;
			
		} catch (SQLException ex) {
			throw new RuntimeException(ex);
		}
		
	}// 메서드 끝
	
	
	public void deleteCartItemsByUserNo(int userNo) {
	
		String sql = "DELETE FROM SAMPLE_CART_ITEMS "
				   + "WHERE USER_NO = ? ";
		
		try {
		
			Connection conn = ConnUtil.getConnection();
			PreparedStatement pstmt = conn.prepareStatement(sql);
			
			pstmt.setInt(1, userNo);
			
			pstmt.executeUpdate();
			
			pstmt.close();
			conn.close();
			
		} catch (SQLException ex) {
			throw new RuntimeException(ex);
		}
	
	}
	
	
}
