package com.dealsdate.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.dealsdate.entity.Order;
import com.dealsdate.entity.Product;
import com.dealsdate.exception.ProductAlreadyExistsException;
import com.dealsdate.exception.ProductNotFoundException;
import com.dealsdate.repositories.ProductRepository;

import jakarta.transaction.Transactional;

@Service
@Transactional
public class ProductServiceImpl implements ProductService {


	@Autowired
	ProductRepository prodRepo;

	@Override
	public Product addProduct(Product product) throws ProductAlreadyExistsException {
		if(prodRepo.findByProductName(product.getProductName()) != null) {
			throw new ProductAlreadyExistsException();
		}
		return prodRepo.save(product);
	}

	@Override
	public Product updateProduct(Product product) throws ProductNotFoundException {
		if(prodRepo.existsById(product.getProductId())) {
			return prodRepo.save(product);
		}
		throw new ProductNotFoundException();
	}
	@Override
	public String deleteProductById(Integer productId) throws ProductNotFoundException {
		if (prodRepo.existsById(productId)) {
			Product product = prodRepo.findById(productId).orElseThrow(ProductNotFoundException::new);
			// Remove the product from orders
			for (Order order : product.getOrders()) {
				order.getProducts().remove(product);
				System.out.println(order);
			}

			prodRepo.delete(product);
			return "Product deleted successfully.";
		}
		throw new ProductNotFoundException();
	}

	@Override
	public Product searchByProductName(String productName) throws ProductNotFoundException {
		Product prod = prodRepo.findByProductName(productName);
		if(prod != null) {
			return prod;
		}
		throw new ProductNotFoundException();
	}

	@Override
	public List<Product> searchByColour(String colour) {
		return prodRepo.findAllByColour(colour);
	}

	@Override
	public List<Product> searchByDimension(String dimension) {
		return prodRepo.findAllByDimension(dimension);
	}

	@Override
	public List<Product> filterByBrand(String brand) {
		return prodRepo.findAllByBrand(brand);
	}

	@Override
	public List<Product> sortByPrice() {
		return prodRepo.findAllSortedByPrice();
	}

	@Override
	public List<Product> getAllProducts() {
		return (List<Product>) prodRepo.findAll();
	}

	@Override
	public Product getProductById(Integer id) throws ProductNotFoundException {
		Optional<Product> optProd = prodRepo.findById(id);
		if(optProd.isPresent()) {
			Product prod = optProd.get();
			return prod;
		}
		throw new ProductNotFoundException();
	}
}
