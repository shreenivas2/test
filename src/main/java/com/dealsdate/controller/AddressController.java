package com.dealsdate.controller;


import java.util.logging.Level;
import java.util.logging.Logger;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import com.dealsdate.entity.Address;
import com.dealsdate.entity.Customer;
import com.dealsdate.exception.AddressNotFoundException;
import com.dealsdate.exception.CustomerNotFoundException;
import com.dealsdate.service.AddressService;

public class AddressController {
	
	Logger logger = Logger.getLogger(AddressController.class.getName());

	public AddressController() {
		logger.log(Level.INFO,"-----> AddressController is Working!");
		
	}
	
	
	@Autowired
	AddressService addrSer;

	// http://localhost:8080/addaddress
	@PostMapping("/addaddress")
	public Address addAddress(@RequestBody Address address) {
		return addrSer.addAddress(address);
	}

	// http://localhost:8080/updateaddress
	@PostMapping("/updateaddress")
	public Address updateAddress(@RequestBody Address address) throws AddressNotFoundException {
		return addrSer.updateAddress(address);
	}

	/**
	 * http://localhost:8080/deleteaddress/{id}
	 
	 */
	@GetMapping("/deleteaddress/{id}")
	public String deleteAddressById(@PathVariable("id") Integer addressId) throws AddressNotFoundException {
		return addrSer.deleteAddressById(addressId);
	}
	
	/**
	 * http://localhost:8080/getaddress/{id}
	
	 */
	@GetMapping("/getaddress/{id}")
	public Address getAddressById(@PathVariable("id") Integer id) throws AddressNotFoundException {
		return addrSer.getAddressById(id);
	}
	
	/**
	 * http://localhost:8080/getcustomerbyaddressid/{id}
	
	 */
	@GetMapping("/getcustomerbyaddressid/{id}")
	public Customer getCustomerByAddressId(@PathVariable("id") Integer id) throws CustomerNotFoundException {
		return addrSer.getCustomerByAddressId(id);
	}

}
