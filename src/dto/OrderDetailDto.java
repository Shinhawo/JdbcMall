package dto;

import java.util.List;

import vo.Order;

public class OrderDetailDto {

	private Order order;
	private List<OrderItemDto> itemsDtos;
	
	public OrderDetailDto () {}

	public Order getOrder() {
		return order;
	}

	public void setOrder(Order order) {
		this.order = order;
	}

	public List<OrderItemDto> getItemsDtos() {
		return itemsDtos;
	}

	public void setItemsDtos(List<OrderItemDto> itemsDtos) {
		this.itemsDtos = itemsDtos;
	}
	
	
	
	
}
