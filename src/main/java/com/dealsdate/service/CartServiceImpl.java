package com.dealsdate.service;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.dealsdate.entity.Cart;
import com.dealsdate.entity.Product;
import com.dealsdate.exception.ProductNotFoundException;
import com.dealsdate.repositories.CartRepository;
import com.dealsdate.repositories.ProductRepository;

import jakarta.transaction.Transactional;
@Service
@Transactional
public class CartServiceImpl implements CartService {
	
	@Autowired
	CartRepository cartRepo;
	
	@Autowired
	ProductRepository prodRepo;

	@Override
	public Cart viewCartById(Integer id) {
		return cartRepo.findById(id).get();
	}

	@Override
	public List<Product> addProductToCart(Integer cartId, Integer productId) throws ProductNotFoundException {
		Optional<Product> optProd = prodRepo.findById(productId);
		if(optProd.isEmpty()) {
			throw new ProductNotFoundException();
		}
		Optional<Cart> optCart = cartRepo.findById(cartId);
		if(optCart.isPresent()) {
			Cart cart = optCart.get();
			Product prod = optProd.get();
			List<Product> prods = cart.getProducts();
			prods.add(prod);
			cart.setProducts(prods);
			cart.setQuantity(prods.size());
			Cart c = cartRepo.save(cart);
			return c.getProducts();
		}
		return Collections.emptyList();
	}

	// Update Cart
	public Cart updateCart(Cart cart) {
		return null;
	}

	// Delete product from Cart
	public List<Product> deleteProductFromCart(Integer cartId, Integer prodId) throws ProductNotFoundException {
		Optional<Product> optProd = prodRepo.findById(prodId);
		if(optProd.isEmpty()) {
			throw new ProductNotFoundException();
		}
		Optional<Cart> optCart = cartRepo.findById(cartId);
		if(optCart.isPresent()) {
			Cart cart = optCart.get();
			Product prod = optProd.get();
			List<Product> prods = cart.getProducts();
			if(prods.contains(prod)) {
				prods.remove(prod);
			} else {
				throw new ProductNotFoundException();
			}
			cart.setProducts(prods);
			cart.setQuantity(prods.size());
			Cart c = cartRepo.save(cart);
			return c.getProducts();
		}
		return Collections.emptyList();
	}

	@Override
	public List<Product> getAllCartProducts(Integer cartId) {
		Optional<Cart> optCart = cartRepo.findById(cartId);
		if(optCart.isPresent()) {
			Cart cart = optCart.get();
			return cart.getProducts();
		}
		return Collections.emptyList();
	}

}
