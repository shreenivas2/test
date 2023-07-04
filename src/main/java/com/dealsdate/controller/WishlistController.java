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

import com.dealsdate.entity.Product;
import com.dealsdate.entity.Wishlist;
import com.dealsdate.exception.ProductAlreadyExistsException;
import com.dealsdate.exception.ProductNotFoundException;
import com.dealsdate.service.WishlistService;

@RestController
@CrossOrigin("*")
public class WishlistController {
	
	Logger logger = Logger.getLogger(WishlistController.class.getName());

	public WishlistController() {
		logger.log(Level.INFO,"-----> WishlistController is Working!");
		
	}

	@Autowired
	WishlistService wishSer;
	
	// http://localhost:8080/getwishlist/{id}
	@GetMapping("/getwishlist/{id}")
	public Wishlist viewWishlist(@PathVariable("id") Integer id) {
		return wishSer.viewWishlist(id);
	}

	/**
	 * http://localhost:8080/addtowishlist/{wishId}/{cartId}
	 */
	@PostMapping("/addproducttowishlist/{wishId}/{prodId}")
	public List<Product> addProductToWishlist(@PathVariable("wishId") Integer wishId, @PathVariable("prodId") Integer prodId) throws ProductNotFoundException, ProductAlreadyExistsException {
		return wishSer.addProductToWishlist(wishId, prodId);
	}

	// Update Wishlist

	/**
	 * http://localhost:8080/addtowishlist/{wishId}/{cartId}
	 
	 */
	@PostMapping("/deleteproductfromwishlist/{wishId}/{prodId}")
	public List<Product> deleteProductFromWishlist(@PathVariable("wishId") Integer wishId, @PathVariable("prodId") Integer prodId) throws ProductNotFoundException {
		return wishSer.deleteProductFromWishlist(wishId, prodId);
	}

	// http://localhost:8080/getcartproducts/{cartid}
	@GetMapping("/getwishproducts/{wishid}")
	public List<Product> getWishListProducts(@PathVariable("wishid") Integer wishId) {
		return wishSer.getAllWishProducts(wishId);
	}

}
