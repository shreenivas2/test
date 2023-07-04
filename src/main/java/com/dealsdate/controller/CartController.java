package com.dealsdate.controller;

import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import com.dealsdate.entity.Cart;
import com.dealsdate.entity.Product;
import com.dealsdate.exception.ProductNotFoundException;
import com.dealsdate.service.CartService;

@CrossOrigin("*")
@RestController
public class CartController {
	
	Logger logger = Logger.getLogger(CartController.class.getName());

	public CartController() {
		logger.log(Level.INFO,"-----> CartController is Working!");
		
	}

	@Autowired
	CartService cartSer;
	
	
	@GetMapping("/viewcart/{id}")
	public Cart viewCart(@PathVariable("id") Integer id) {
		return cartSer.viewCartById(id);
	}
	
	/**
	 *  http://localhost:8080/addproducttocart/{cartid}/{prodid}
	
	 */
	@PostMapping("/addproducttocart/{cartid}/{prodid}")
	public List<Product> addProdToCart(@PathVariable("cartid") Integer cartId, @PathVariable("prodid") Integer prodId) throws ProductNotFoundException {
		return cartSer.addProductToCart(cartId, prodId);
	}
	
	
	@PostMapping("/deleteproductfromcart/{cartid}/{prodid}")
	public List<Product> deleteProdFromCart(@PathVariable("cartid") Integer cartId, @PathVariable("prodid") Integer prodId) throws ProductNotFoundException {
		return cartSer.deleteProductFromCart(cartId, prodId);
	}
	
	// http://localhost:8080/getcartproducts/{cartid}
	@GetMapping("/getcartproducts/{cartid}")
	public List<Product> getCartProducts(@PathVariable("cartid") Integer cartId) {
		return cartSer.getAllCartProducts(cartId);
	}
}
