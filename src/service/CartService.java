package service;

import java.util.List;

import dao.CartItemDao;
import dao.OrderDao;
import dao.OrderItemDao;
import dao.PointHistoryDao;
import dao.ProductDao;
import dao.UserDao;
import dto.CartItemDto;
import dto.CartItemListDto;
import vo.CartItem;
import vo.Order;
import vo.OrderItem;
import vo.PointHistory;
import vo.Product;
import vo.User;

public class CartService {

	private CartItemDao cartItemDao = new CartItemDao();
	private OrderDao orderDao = new OrderDao();
	private OrderItemDao orderItemDao = new OrderItemDao();
	private ProductDao productDao = new ProductDao();
	private PointHistoryDao pointHistoryDao = new PointHistoryDao();
	private UserDao userDao = new UserDao();
	
	public void addCartItem(CartItem cartItem) {
		
		// 중복여부가 먼저 체크됨
		CartItem savedCartItem = cartItemDao.getCartItems(cartItem.getUserNo(), cartItem.getProductNo());
		if(savedCartItem != null) {
			throw new RuntimeException("장바구니에 동일한 상품이 이미 저장되어 있습니다.");
		}
		
		cartItemDao.insertCartItem(cartItem);
		
	}

	public CartItemListDto getMyCartItems(int userNo){
		List<CartItemDto> dtos = cartItemDao.getCartItemDtoByUserNo(userNo);
	
		CartItemListDto listDto = new CartItemListDto();
		listDto.setDtos(dtos);
		
		return listDto;
	}
	
	public void clearMyCartItems (int userNo) {
		
		cartItemDao.deleteCartItemsByUserNo(userNo);
	}

	
	
	public void buy(int userNo) {
		CartItemListDto cartItemListDto = this.getMyCartItems(userNo);
		
		int orderNo = orderDao.getSequence();
		
		// 주문자 정보 저장하기
		int totalOrderPrice = cartItemListDto.getTotalOrderPrice();
		int usedPoint = 0;
		int totalCreditPrice = cartItemListDto.getTotalOrderPrice();
		int depositPoint = (int) (totalCreditPrice*0.01);
		
		Order order = new Order();
		order.setNo(orderNo);
		order.setTotalOrderPrice(totalOrderPrice);
		order.setUsedPoint(usedPoint);
		order.setTotalCreditPrice(totalCreditPrice);
		order.setDepositPoint(depositPoint);
		order.setUserNo(userNo);
		
		orderDao.insertOrder(order);
		
		// 주문상품정보 저장하기
		List<CartItemDto> dtos = cartItemListDto.getDtos();
		for(CartItemDto item : dtos) {
			int productNo = item.getProductNo();
			
			OrderItem orderItem = new OrderItem();
			orderItem.setOrderNo(orderNo);
			orderItem.setProductNo(productNo);
			orderItem.setAmount(item.getItemAmount());
			orderItem.setPrice(item.getProductPrice());
			
			orderItemDao.insertOrderItem(orderItem);
			
			
			//상품정보의 재고수량 변경하기
			Product product = productDao.getProductByNo(productNo);
			product.setStock(product.getStock() - item.getItemAmount());
			
			productDao.updateProduct(product);
			
		}

		// 사용자 포인트 변경하기
		User user = userDao.getUserByNo(userNo);
		user.setPoint(user.getPoint() + depositPoint);
		userDao.updateUser(user);
		
		// 포인트 변경이력정보 저장하기
		PointHistory history = new PointHistory();
		history.setUserNo(userNo);
		history.setOrderNo(orderNo);
	//	history.setUserNo(userNo);
		history.setDepositPoint(depositPoint);
		history.setCurrentPoint(user.getPoint());
		
		pointHistoryDao.insertHistory(history);
		
		// 장바구니 비우기
		this.clearMyCartItems(userNo);
	}
	
	
	
}
