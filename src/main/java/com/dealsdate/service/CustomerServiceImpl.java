package com.dealsdate.service;

import java.util.ArrayList;
import java.util.List;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.dealsdate.entity.Cart;
import com.dealsdate.entity.Customer;
import com.dealsdate.entity.Order;
import com.dealsdate.entity.Product;
import com.dealsdate.entity.User;
import com.dealsdate.entity.Wishlist;
import com.dealsdate.exception.CustomerAlreadyExistsException;
import com.dealsdate.exception.CustomerNotFoundException;
import com.dealsdate.repositories.AddressRepository;
import com.dealsdate.repositories.CustomerRepository;

import jakarta.transaction.Transactional;

@Service
@Transactional
public class CustomerServiceImpl implements CustomerService {
    
	@Autowired
	CustomerRepository custRepo;
	
	@Autowired
	AddressRepository addrRepo;
	
	
	
	@Override
	public Customer addCustomer(Customer customer) throws CustomerAlreadyExistsException {
		
		if(custRepo.findByMobileNo(customer.getMobileNo()) != null) {
			throw new CustomerAlreadyExistsException();
		}
		
		List<Product> prods = new ArrayList<>();
		Wishlist wishList = new Wishlist();
		wishList.setProducts(prods);
		wishList.setQuantity(0);
		Cart cart = new Cart();
		cart.setProducts(prods);
		cart.setQuantity(0);
		User user = customer.getUser();
		user.setUserType("Customer");
		customer.setUser(user);
		customer.setWishlist(wishList);
		customer.setCart(cart);
		return custRepo.save(customer);
	}

	@Override
	public Customer getCustomerById(Integer custId) throws CustomerNotFoundException {
		if(custRepo.existsById(custId)) {
			return custRepo.findById(custId).get();
		}
		throw new CustomerNotFoundException();
	}

	@Override
	public List<Customer> getAllCustomers() {
		return (List<Customer>) custRepo.findAll();
	}

	@Override
	public Customer updateCustomer(Customer cust) throws CustomerNotFoundException {
		if(custRepo.existsById(cust.getCustomerId())) {
			Customer customer = custRepo.findById(cust.getCustomerId()).get();
			customer.setAddress(cust.getAddress());
			customer.setUser(cust.getUser());
			customer.setCustomerName(cust.getCustomerName());
			customer.setEmail(cust.getEmail());
			customer.setMobileNo(cust.getMobileNo());
			custRepo.save(customer);
			return customer;
		}
		throw new CustomerNotFoundException();
	}

	@Override
	public String deleteCustomer(Customer cust) throws CustomerNotFoundException {
		if(custRepo.existsById(cust.getCustomerId())) {
			custRepo.delete(cust);
			return "Customer Deleted";
		}
		throw new CustomerNotFoundException();
	}

	@Override
	public String deleteCustomerById(Integer custId) throws CustomerNotFoundException {
		if(custRepo.existsById(custId)) {
			custRepo.deleteById(custId);
			return "Deleted Customer by ID";
		}
		throw new CustomerNotFoundException();
	}

	@Override
	public List<Order> getOrdersByCustomerId(Integer custId) throws CustomerNotFoundException {
		if(!custRepo.existsById(custId)) {
			throw new CustomerNotFoundException();
		}
		if(custRepo.findById(custId).isPresent()) {
			return custRepo.findById(custId).get().getOrders();
		}
		throw new CustomerNotFoundException();
	}

	@Override
	public Cart getCartByCustId(Integer custId) throws CustomerNotFoundException {
		if(!custRepo.existsById(custId)) {
			throw new CustomerNotFoundException();
		}
		Cart cart = custRepo.findById(custId).get().getCart();
		return cart;
	}

	@Override
	public Wishlist getWishListByCustId(Integer id) throws CustomerNotFoundException {
		if(!custRepo.existsById(id)) {
			throw new CustomerNotFoundException();
		}
		return custRepo.findById(id).get().getWishlist();
	}

}
