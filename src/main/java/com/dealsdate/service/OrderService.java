package com.dealsdate.service;

import com.dealsdate.entity.Order;
import com.dealsdate.exception.OrderNotFoundException;
import com.dealsdate.exception.ProductNotFoundException;

public interface OrderService {

public Order addOrder(Integer cartId, Integer totalPrice);
	
	public String cancelOrder(Order order) throws OrderNotFoundException;
	
	public String cancelOrderById(Integer id) throws OrderNotFoundException;
	
	public String cancelAProduct(Integer orderId, Integer productId) throws OrderNotFoundException, ProductNotFoundException;
	
	public Order updateOrder(Order order) throws OrderNotFoundException;
	
	public Order getOrderById(Integer orderId) throws OrderNotFoundException;
}
