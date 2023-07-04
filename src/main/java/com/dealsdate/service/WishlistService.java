package com.dealsdate.service;

import java.util.List;

import com.dealsdate.entity.Product;
import com.dealsdate.entity.Wishlist;
import com.dealsdate.exception.ProductAlreadyExistsException;
import com.dealsdate.exception.ProductNotFoundException;

public interface WishlistService {


	public Wishlist viewWishlist(Integer id);
	
	public List<Product> addProductToWishlist(Integer prodId, Integer wishId) throws ProductNotFoundException, ProductAlreadyExistsException;
	
	public List<Product> deleteProductFromWishlist(Integer prodId, Integer wishlistId) throws ProductNotFoundException;

	public List<Product> getAllWishProducts(Integer wishId);
}
