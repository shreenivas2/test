package com.dealsdate.service;

import java.util.List;

import com.dealsdate.entity.Product;
import com.dealsdate.exception.ProductAlreadyExistsException;
import com.dealsdate.exception.ProductNotFoundException;

public interface ProductService {

public Product addProduct(Product product) throws ProductAlreadyExistsException;
	
	public Product updateProduct(Product product) throws ProductNotFoundException;
	
	public String deleteProductById(Integer productId) throws ProductNotFoundException;
	
	public Product searchByProductName(String productName) throws ProductNotFoundException;
	
	public List<Product> searchByColour(String colour);

	public List<Product> searchByDimension(String dimension);
	
	public List<Product> filterByBrand(String brand);
	
	public List<Product> sortByPrice();
	
	public List<Product> getAllProducts();
	
	public Product getProductById(Integer id) throws ProductNotFoundException;
}
