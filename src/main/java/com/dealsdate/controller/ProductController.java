package com.dealsdate.controller;

import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.dealsdate.entity.Product;
import com.dealsdate.exception.ProductAlreadyExistsException;
import com.dealsdate.exception.ProductNotFoundException;
import com.dealsdate.service.ProductService;

@RestController
@CrossOrigin("*")
public class ProductController {
	
	Logger logger = Logger.getLogger(ProductController.class.getName());

	public ProductController() {
		logger.log(Level.INFO,"-----> ProductController is Working!");
		
	}

	@Autowired
	ProductService prodSer;

	// http://localhost:8080/addproduct
	@PostMapping("/addproduct")
	public Product addProduct(@RequestBody Product prod) throws ProductAlreadyExistsException {
		return prodSer.addProduct(prod);
	}

	// http://localhost:8080/updateproduct
	@PostMapping("/updateproduct")
	public Product updateproduct(@RequestBody Product prod) throws ProductNotFoundException {
		return prodSer.updateProduct(prod);
	}

	// http://localhost:8080/deleteproductbyid
	@GetMapping("/deleteproductbyid/{id}")
	public String deleteProductById(@PathVariable("id") Integer productId) throws ProductNotFoundException {
		return prodSer.deleteProductById(productId);
	}
	
	@GetMapping("/getproductbyid/{id}")
	public Product getProductById(@PathVariable("id") Integer id) throws ProductNotFoundException {
		return prodSer.getProductById(id);
	}

	// http://localhost:8080/searchbyproductname
	@GetMapping("/searchbyproductname")
	public Product searchByProductName(@RequestParam("productname") String productName) throws ProductNotFoundException {
		return prodSer.searchByProductName(productName);
	}

	// http://localhost:8080/searchbycolour
	@GetMapping("/searchbycolour")
	public List<Product> searchByColour(@RequestParam("colour") String colour) {
		return prodSer.searchByColour(colour);
	}

	// http://localhost:8080/searchbydimension
	@GetMapping("/searchbydimension")
	public List<Product> searchByDimension(@RequestParam("dimension") String dimension) {
		return prodSer.searchByDimension(dimension);
	}

	// http://localhost:8080/filterbybrand
	@GetMapping("/filterbybrand")
	public List<Product> filterByBrand(@RequestParam("brand") String brand) {
		return prodSer.filterByBrand(brand);
	}

	// http://localhost:8080/sortbyprice
	@GetMapping("/sortbyprice")
	public List<Product> sortByPrice() {
		return prodSer.sortByPrice();
	}
	
	// http://localhost:8080/allproducts
	@GetMapping("/allproducts")
	public List<Product> getAllProducts() {
		return prodSer.getAllProducts();
	}
}
