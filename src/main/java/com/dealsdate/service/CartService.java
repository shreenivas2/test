package com.dealsdate.service;

import java.util.List;

import com.dealsdate.entity.Cart;
import com.dealsdate.entity.Product;
import com.dealsdate.exception.ProductNotFoundException;

public interface CartService {

	public Cart viewCartById(Integer id);

	public List<Product> addProductToCart(Integer cartId, Integer productId) throws ProductNotFoundException;

	public List<Product> deleteProductFromCart(Integer cartId, Integer productId) throws ProductNotFoundException;

	public List<Product> getAllCartProducts(Integer cartId);

}
