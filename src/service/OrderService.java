package service;

import java.util.List;

import dao.OrderDao;
import dao.OrderItemDao;
import dao.PointHistoryDao;
import dao.ProductDao;
import dao.UserDao;
import dto.OrderDetailDto;
import dto.OrderItemDto;
import vo.Order;
import vo.OrderItem;
import vo.PointHistory;
import vo.Product;
import vo.User;

public class OrderService {

	private PointHistoryDao pointHistoryDao = new PointHistoryDao();
	private OrderDao orderDao = new OrderDao();
	private ProductDao productDao = new ProductDao();
	private OrderItemDao orderItemDao = new OrderItemDao();
	private UserDao userDao = new UserDao();
	
	public OrderDetailDto getOrderDetail(int orderNo, int userNo) {
		
		Order order = orderDao.getOrderByNo(orderNo);
		
		if(order == null) {
			throw new RuntimeException("주문정보가 존재하지 않습니다.");
		}
		
		if (order.getUserNo() != userNo) {
			throw new RuntimeException("다른 사용자의 주문정보는 조회할 수 없습니다.٩(๑'o'๑)۶");
		}

		List<OrderItemDto> items = orderItemDao.getOrderItemDtosByOrderNo(orderNo);
		
		OrderDetailDto dto = new OrderDetailDto();
		dto.setOrder(order);
		dto.setItemsDtos(items);
		
		return dto;
	}
	
	
	public void order(int productNo, int amount, int userNo) {		
		
		// 상품정보 조회
		Product product = productDao.getProductByNo(productNo);
		if(product == null) {
			throw new RuntimeException("상품정보가 존재하지 않습니다.");
		}
		
		// 신규 주문 일련번호 조회하기
		// 시퀀스쿼리를 실행해서 숫자를 하나 받아 놓는다. 그 숫자를 여기저기에 써먹을 것.
		int orderNo = orderDao.getSequence();
		
		// 주문정보 저장하기
		int totalOrderPrice = product.getPrice() * amount;
		int usedpoint = 0;
		int totalCreditPrice = product.getPrice() * amount;
		int depositPoint = (int)(totalCreditPrice*0.01);
		
		Order order = new Order();
		order.setNo(orderNo);
		order.setTotalOrderPrice(totalOrderPrice);
		order.setUsedPoint(usedpoint);
		order.setTotalCreditPrice(totalCreditPrice);
		order.setDepositPoint(depositPoint);
		order.setUserNo(userNo);
		
		orderDao.insertOrder(order);
		
		// 주문상품정보 저장하기
		OrderItem orderItem = new OrderItem();
		orderItem.setOrderNo(orderNo);
		orderItem.setProductNo(productNo);
		orderItem.setAmount(amount);
		orderItem.setPrice(product.getPrice());
		
		orderItemDao.insertOrderItem(orderItem);
		
		// 포인트변경이력정보 저장하기
		User user = userDao.getUserByNo(userNo);
		int currentDepositPoint = user.getPoint();
		currentDepositPoint += depositPoint;
		
		PointHistory history = new PointHistory();
		history.setOrderNo(orderNo);
		history.setUserNo(userNo);
		history.setDepositPoint(depositPoint);
		history.setCurrentPoint(currentDepositPoint);
		
		pointHistoryDao.insertHistory(history);
		
		// 상품의 재고수량 변경하기 -> update
		product.setStock(product.getStock() - amount);
		productDao.updateProduct(product);
		
		// 사용자의 포인트 변경하기 -> update
		user.setPoint(user.getPoint() + depositPoint);
		userDao.updateUser(user);
	}
	
	public List<Order> getMyOrders (int userNo){
		
		List<Order> orders = orderDao.getOrdersByUserNo(userNo);
		
		return orders;
	}
	
	public List<PointHistory> getMyPointHistories(int userNo){
		
		return pointHistoryDao.getHistoryByUserNo(userNo);
	}
	
}
