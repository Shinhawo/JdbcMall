package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import oracle.jdbc.proxy.annotation.Pre;
import util.ConnUtil;
import vo.Product;

public class ProductDao {

	 public List<Product> getProducts() {
		String sql = "SELECT * "
		            +"FROM SAMPLE_PRODUCTS "
				    +"ORDER BY PRODUCT_NO ASC";
		
		try {
		   	 List<Product> productlist = new ArrayList<>();
			 
			 Connection conn = ConnUtil.getConnection();
			 PreparedStatement pstmt = conn.prepareStatement(sql);
			 
			 ResultSet rs = pstmt.executeQuery();
			 
			 while(rs.next()) {	
				Product product = new Product();
				
				product.setNo(rs.getInt("product_no"));
				product.setName(rs.getString("product_name"));
				product.setMaker(rs.getString("product_maker"));
				product.setPrice(rs.getInt("product_price"));
				product.setDiscountRate(rs.getDouble("product_discount_rate"));
				product.setStock(rs.getInt("product_stock"));
				product.setCreateDate(rs.getDate("product_create_date"));
				
				productlist.add(product);
			 }
			 rs.close();
			 pstmt.close();
			 conn.close();
			 
			 return productlist;
			 
		 } catch (SQLException ex) {
			throw new RuntimeException(ex);
		}//catch
		
	 }// 메서드 끝
	 
	 
	 public Product getProductByNo(int productNo) {
		 String sql = "select * "
				    + "from sample_products "
				    + "where product_no = ? ";
		 
		 try {
			 Product product = null;
			 
			 Connection conn = ConnUtil.getConnection();
			 PreparedStatement pstmt = conn.prepareStatement(sql);
		     pstmt.setInt(1, productNo);
		     
		     ResultSet rs = pstmt.executeQuery();
		     
		     while(rs.next()) {
		    	product = new Product();
		    	product.setNo(rs.getInt("product_no"));
				product.setName(rs.getString("product_name"));
				product.setMaker(rs.getString("product_maker"));
				product.setPrice(rs.getInt("product_price"));
				product.setDiscountRate(rs.getDouble("product_discount_rate"));
				product.setStock(rs.getInt("product_stock"));
				product.setCreateDate(rs.getDate("product_create_date"));
		     }
		     
		     rs.close();
		     pstmt.close();
		     conn.close();
			 
			 return product;
			 
		} catch (SQLException ex) {
			throw new RuntimeException(ex);
		}
	 }
	 
	 
	 public void updateProduct(Product product) {
		 
		 String sql = "UPDATE SAMPLE_PRODUCTS "
				    + "SET "
				    + "		PRODUCT_PRICE = ?, "
				    + "		PRODUCT_DISCOUNT_RATE = ?, "
				    + "		PRODUCT_STOCK = ? "
				    + "WHERE PRODUCT_NO = ? ";
		 
		 try {
			Connection conn = ConnUtil.getConnection();
			PreparedStatement pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, product.getPrice());
			pstmt.setDouble(2, product.getDiscountRate());
			pstmt.setInt(3, product.getStock());
			pstmt.setInt(4, product.getNo());
			
			pstmt.executeUpdate();
			
			pstmt.close();
			conn.close();
			 
		} catch (SQLException ex) {
			throw new RuntimeException(ex);
		}
		 
	 } // 메서드 끝
	 
	 
	
}











