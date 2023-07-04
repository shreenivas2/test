package com.dealsdate.service;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.dealsdate.entity.Product;
import com.dealsdate.entity.Wishlist;
import com.dealsdate.exception.ProductAlreadyExistsException;
import com.dealsdate.exception.ProductNotFoundException;
import com.dealsdate.repositories.ProductRepository;
import com.dealsdate.repositories.WishlistRepository;

import jakarta.transaction.Transactional;

@Service
@Transactional
public class WishlistServiceImpl implements WishlistService {


	@Autowired
	WishlistRepository wishRepo;
	
	@Autowired
	ProductRepository prodRepo;

	@Override
	public Wishlist viewWishlist(Integer id) {
		return wishRepo.findById(id).get();
	}

	@Override
	public List<Product> addProductToWishlist(Integer wishId, Integer prodId) throws ProductNotFoundException, ProductAlreadyExistsException {
		Optional<Product> optProd = prodRepo.findById(prodId);
		Optional<Wishlist> optWish = wishRepo.findById(wishId);
		if(optProd.isEmpty()) {
			throw new ProductNotFoundException();
		}
		if(optWish.isPresent()) {
			Product prod = optProd.get();
			Wishlist wish = optWish.get();
			List<Product> prods = wish.getProducts();
			if(prods.contains(prod)) {
				throw new ProductAlreadyExistsException();
			}
			prods.add(prod);
			wish.setProducts(prods);
			wish.setQuantity(prods.size());
			Wishlist w = wishRepo.save(wish);
			return w.getProducts();
		}
		return Collections.emptyList();
	}

	@Override
	public List<Product> deleteProductFromWishlist(Integer wishId, Integer prodId)
			throws ProductNotFoundException {
		
		Optional<Product> optProd = prodRepo.findById(prodId);
		if(optProd.isEmpty()) {
			throw new ProductNotFoundException();
		}
		Optional<Wishlist> optWish = wishRepo.findById(wishId);
		if(optWish.isPresent()) {
			Wishlist wish = optWish.get();
			Product prod = optProd.get();
			List<Product> prods = wish.getProducts();
			if(prods.contains(prod)) {
				prods.remove(prod);
			} else {
				throw new ProductNotFoundException();
			}
			wish.setProducts(prods);
			wish.setQuantity(prods.size());
			Wishlist w = wishRepo.save(wish);
			return w.getProducts();
		}
		return Collections.emptyList();
	}

	@Override
	public List<Product> getAllWishProducts(Integer wishId) {
		Optional<Wishlist> optWish = wishRepo.findById(wishId);
		if(optWish.isPresent()) {
			Wishlist wish = optWish.get();
			return wish.getProducts();
		}
		return Collections.emptyList();
	}
}
