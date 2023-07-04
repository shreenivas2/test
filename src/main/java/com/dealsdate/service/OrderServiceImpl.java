package com.dealsdate.service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;



import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.dealsdate.entity.Cart;
import com.dealsdate.entity.Order;
import com.dealsdate.entity.Product;
import com.dealsdate.exception.OrderNotFoundException;
import com.dealsdate.exception.ProductNotFoundException;
import com.dealsdate.repositories.CartRepository;
import com.dealsdate.repositories.OrderRepository;
import com.dealsdate.repositories.ProductRepository;

import jakarta.transaction.Transactional;

@Service
@Transactional
public class OrderServiceImpl implements OrderService {

	@Autowired
	OrderRepository orderRepo;

	@Autowired
	CartService cartSer;

	@Autowired
	CartRepository cartRepo;

	@Autowired
	ProductRepository prodRepo;

	@Override
	public Order addOrder(Integer cartId, Integer totalPrice) {
		Cart cart = cartSer.viewCartById(cartId);
		LocalDate today = LocalDate.now();
		Order order = new Order();
		order.setCustomer(cart.getCustomer());
		List<Product> prods = new ArrayList<>(cart.getProducts());
		order.setProducts(prods);
		order.setTotalPrice((double) totalPrice);
		order.setTotalQuantity(cart.getQuantity());
		order.setOrderDispatchDate(today.plusDays(1));
		order.setOrderDeliveryDate(today.plusDays(3));
		cart.setProducts(Collections.emptyList());
		cartRepo.save(cart);
		return orderRepo.save(order);
	}

	@Override
	public String cancelOrder(Order order) throws OrderNotFoundException {
		if (orderRepo.existsById(order.getOrderId())) {
			orderRepo.delete(order);
			return "Order deleted";
		}
		throw new OrderNotFoundException();
	}

	@Override
	public String cancelAProduct(Integer orderId, Integer productId)
			throws OrderNotFoundException, ProductNotFoundException {
		if (orderRepo.existsById(orderId)) {
			Order ord = orderRepo.findById(orderId).get();
			List<Product> prods = ord.getProducts();
			int size = prods.size();
			for (Iterator<Product> iterator = prods.iterator(); iterator.hasNext();) {
				Product productModel = iterator.next();
				if (productModel.getProductId().equals(productId)) {
					iterator.remove();
				}
			}
			if (prods.size() != size - 1) {
				throw new ProductNotFoundException();
			}
			ord.setProducts(prods);
			orderRepo.save(ord);
			return "Product deleted";
		}
		throw new OrderNotFoundException();
	}

	@Override
	public Order updateOrder(Order order) throws OrderNotFoundException {
		if (orderRepo.existsById(order.getOrderId())) {
			orderRepo.save(order);
			return order;
		}
		throw new OrderNotFoundException();
	}

	@Override
	public Order getOrderById(Integer orderId) throws OrderNotFoundException {
		if (orderRepo.existsById(orderId)) {
			return orderRepo.findById(orderId).get();
		}
		throw new OrderNotFoundException();
	}

//	@Override
//	public List<OrderModel> getAllOrders() {
//		return (List<OrderModel>) orderDao.findAll();
//	}

	@Override
	public String cancelOrderById(Integer id) throws OrderNotFoundException {
		if (orderRepo.existsById(id)) {
			Order order = orderRepo.findById(id).get();
			order.setCustomer(null);
			orderRepo.deleteById(id);
			return "Order deleted by ID";
		}
		throw new OrderNotFoundException();
	}

}
