package com.dealsdate.service;

import com.dealsdate.entity.Address;
import com.dealsdate.entity.Customer;
import com.dealsdate.exception.AddressNotFoundException;
import com.dealsdate.exception.CustomerNotFoundException;

public interface AddressService {
	
public Address addAddress(Address address);
	
	public Address updateAddress(Address address) throws AddressNotFoundException;
	
	public String deleteAddressById(Integer addressId)  throws AddressNotFoundException;
	
	public Address getAddressById(Integer id) throws AddressNotFoundException;
	
	public Customer getCustomerByAddressId(Integer id) throws CustomerNotFoundException;

}
