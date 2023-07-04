package com.dealsdate.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import com.dealsdate.entity.Product;

public interface ProductRepository extends CrudRepository<Product, Integer>{
	
	public Product findByBrand(String brand);
	
	public Product findByProductName(String name);
	
	public Product findByDimension(String dimension);
	
	public Product findByColour(String color);
	
	@Query(value = "SELECT p FROM Product p ORDER BY price")
	public List<Product> findAllSortedByPrice();
	
	public List<Product> findAllByBrand(String brand);
	
	public List<Product> findAllByColour(String colour);
	
	public List<Product> findAllByDimension(String dimension);

}
