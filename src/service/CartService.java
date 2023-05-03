package service;

import java.util.List;

import dao.CartItemDao;
import dto.CartItemDto;
import dto.CartItemListDto;
import vo.CartItem;
import vo.Product;

public class CartService {

	private CartItemDao cartItemDao = new CartItemDao();
	
	public void addCartItem(CartItem cartItem) {
		
		// 중복여부가 먼저 체크됨
		CartItem savedCartItem = cartItemDao.getCartItem(cartItem.getUserNo(), cartItem.getProductNo());
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

}
