package com.dealsdate.controller;

import java.util.logging.Level;
import java.util.logging.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.dealsdate.entity.Order;
import com.dealsdate.exception.OrderNotFoundException;
import com.dealsdate.exception.ProductNotFoundException;
import com.dealsdate.service.OrderService;

@CrossOrigin("*")
@RestController
public class OrderController {
	
	Logger logger = Logger.getLogger(OrderController.class.getName());

	public OrderController() {
		logger.log(Level.INFO,"-----> OrderController is Working!");
		
	}

	@Autowired
	OrderService orderSer;

	// http://localhost:8080/addorder
	@PostMapping("/addorder/{cartId}/{total}")
	public Order addOrder(@PathVariable("cartId") Integer cartId, @PathVariable("total") Integer total) {
		return orderSer.addOrder(cartId, total);
	}

	// http://localhost:8080/cancelorder
	@PostMapping("/cancelorder")
	public String cancelOrder(@RequestBody Order order) throws OrderNotFoundException {
		return orderSer.cancelOrder(order);
	}

	/**
	 * http://localhost:8080/cancelorderbyid/{id}
	 */
	@GetMapping("/cancelorderbyid/{id}")
	public String cancelOrderById(@PathVariable("id") Integer id) throws OrderNotFoundException {
		return orderSer.cancelOrderById(id);
	}

	/**
	 * http://localhost:8080/cancelproduct/{orderid}/{productid}
	 * 
	 */
	@PostMapping("/cancelproduct/{orderid}/{productid}")
	public String cancelAProduct(@PathVariable("orderid") Integer orderId, @PathVariable("productid") Integer productId)
			throws OrderNotFoundException, ProductNotFoundException {
		return orderSer.cancelAProduct(orderId, productId);
	}

	// http://localhost:8080/updateorder
	@PostMapping("/updateorder")
	public Order updateOrder(@RequestBody Order order) throws OrderNotFoundException {
		return orderSer.updateOrder(order);
	}

	/**
	 * http://localhost:8080/getorderbyid/{id}
	 
	 */
	@GetMapping("/getorderbyid/{id}")
	public Order getOrderById(@PathVariable("id") Integer orderId) throws OrderNotFoundException {
		return orderSer.getOrderById(orderId);
	}

}
