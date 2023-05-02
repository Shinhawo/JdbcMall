package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import util.ConnUtil;
import vo.Product;

public class ProductDao {

	 public List<Product> getAllProduct() {
		String sql = "SELECT PRODUCT_NO, "
				    +"PRODUCT_NAME, PRODUCT_MAKER, PRODUCT_PRICE, "
				    +"PRDOUCT_DISCOUNT_RATE, PRDOUCT_STOCK, PRDOUCT_CREATE_DATE "
		            +"FROM SAMPLE_PRODUCTS "
				    +"ORDER BY PRODUCT_NO ASC";
		
		List<Product> products = new ArrayList<>();
		
		try {
		Connection conn = ConnUtil.getConnection();
		PreparedStatement pstmt = conn.prepareStatement(sql);
		} catch (SQLException ex) {
			new RuntimeException(ex);
		}
		return products;
	 }
	
}
