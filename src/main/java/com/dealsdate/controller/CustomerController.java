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
import org.springframework.web.bind.annotation.RestController;

import com.dealsdate.entity.Cart;
import com.dealsdate.entity.Customer;
import com.dealsdate.entity.Order;
import com.dealsdate.entity.Wishlist;
import com.dealsdate.exception.CustomerAlreadyExistsException;
import com.dealsdate.exception.CustomerNotFoundException;
import com.dealsdate.exception.EmptyCartException;
import com.dealsdate.exception.EmptyWishListException;
import com.dealsdate.service.CustomerService;

@RestController
@CrossOrigin("*")
public class CustomerController {
	
	Logger logger = Logger.getLogger(CustomerController.class.getName());

	public CustomerController() {
		logger.log(Level.INFO,"-----> CustomerController is Working!");
		
	}
	
	@Autowired
	CustomerService custSer;

	// http://localhost:8080/addcust
	@PostMapping("/addcust")
	public Customer addCust(@RequestBody Customer cust) throws CustomerAlreadyExistsException {
//		CustomerModel customer = custMapper.toEntity(cust);
		return custSer.addCustomer(cust);
	}

	/**
	 *  http://localhost:8080/getcustomerbyid/{id}
	 
	 */
	@GetMapping("/getcustomerbyid/{id}")
	public Customer getCustomerById(@PathVariable("id") Integer custId) throws CustomerNotFoundException {
		return custSer.getCustomerById(custId);
	}

	// http://localhost:8080/getallcustomers
	@GetMapping("/getallcustomers")
	public List<Customer> getAllCustomers() {
		return custSer.getAllCustomers();
	}

	// http://localhost:8080/updatecustomer
	@PostMapping("/updatecustomer")
	public Customer updateCustomer(@RequestBody Customer cust) throws CustomerNotFoundException {
		return custSer.updateCustomer(cust);
	}

	// http://localhost:8080/deletecustomer
	@PostMapping("/deletecustomer")
	public String deleteCustomer(@RequestBody Customer cust) throws CustomerNotFoundException {
		return custSer.deleteCustomer(cust);
	}

	/**
	 *  http://localhost:8080/deletecust/{id}
	
	 */
	@GetMapping("/deletecust/{id}")
	public String deleteCustomerById(@PathVariable("id") Integer custId) throws CustomerNotFoundException {
		return custSer.deleteCustomerById(custId);
	}
	
	/**
	 *  http://localhost:8080/getordersbycustid/{id}
	 
	 */
	@GetMapping("/getordersbycustid/{id}")
	public List<Order> getOrdersByCustomerId(@PathVariable("id") Integer custId) throws CustomerNotFoundException {
		return custSer.getOrdersByCustomerId(custId);
	}
	
	/**
	 *  http://localhost:8080/getcartbycustid/{id}

	 */
	@GetMapping("/getcartbycustid/{id}")
	public Cart getCartByCustId(@PathVariable("id") Integer custId) throws EmptyCartException, CustomerNotFoundException {
		return custSer.getCartByCustId(custId);
	}
	
	/**
	 *  http://localhost:8080/getwishlistbycustid/{id}
	
	 */
	@GetMapping("/getwishlistbycustid/{id}")
	public Wishlist getWishListByCustId(@PathVariable("id") Integer id) throws EmptyWishListException, CustomerNotFoundException {
		return custSer.getWishListByCustId(id);
	}

}
