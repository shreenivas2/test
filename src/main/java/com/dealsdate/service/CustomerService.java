package com.dealsdate.service;

import java.util.List;

import com.dealsdate.entity.Cart;
import com.dealsdate.entity.Customer;
import com.dealsdate.entity.Order;
import com.dealsdate.entity.Wishlist;
import com.dealsdate.exception.CustomerAlreadyExistsException;
import com.dealsdate.exception.CustomerNotFoundException;

public interface CustomerService {
    public Customer addCustomer(Customer customer) throws CustomerAlreadyExistsException;
	
	public Customer getCustomerById(Integer custId) throws CustomerNotFoundException;
	
	public List<Customer> getAllCustomers();
	
	public Customer updateCustomer(Customer cust) throws CustomerNotFoundException;
	
	public String deleteCustomer(Customer cust) throws CustomerNotFoundException;
	
	public String deleteCustomerById(Integer custId) throws CustomerNotFoundException;
	
	public List<Order> getOrdersByCustomerId(Integer custId) throws CustomerNotFoundException;
	
	public Cart getCartByCustId(Integer custId) throws CustomerNotFoundException;
	
	public Wishlist getWishListByCustId(Integer id) throws CustomerNotFoundException;
}
