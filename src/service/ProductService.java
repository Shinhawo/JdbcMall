package service;

import java.sql.SQLException;
import java.util.List;

import dao.ProductDao;
import vo.Product;

public class ProductService {

	//값을 담는 객체는 매번 새로 만들어야하지만 기능이 구현되는 객체는 하나만들어서 계속 사용
	ProductDao productDao = new ProductDao();
	
	public List<Product> getAllProducts() {
		return productDao.getProducts();
	}
	
	
	
	
}
