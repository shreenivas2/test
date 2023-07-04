package com.dealsdate.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.dealsdate.entity.Address;
import com.dealsdate.entity.Customer;
import com.dealsdate.exception.AddressNotFoundException;
import com.dealsdate.exception.CustomerNotFoundException;
import com.dealsdate.repositories.AddressRepository;

import jakarta.transaction.Transactional;

@Service
@Transactional
public class AddressServiceImpl implements AddressService {
	
	@Autowired
	AddressRepository addrRepo;

	@Override
	public Address addAddress(Address address) {
		return addrRepo.save(address);
	}
	@Override
	public Address updateAddress(Address address) throws AddressNotFoundException {
		if(addrRepo.existsById(address.getAddressId())) {
			return addrRepo.save(address);
		}
		throw new AddressNotFoundException();
	}

	@Override
	public String deleteAddressById(Integer addressId) throws AddressNotFoundException {
		if(addrRepo.existsById(addressId)) {
			addrRepo.deleteById(addressId);
			return "Address deleted successfully";
		}
		throw new AddressNotFoundException();
	}

	@Override
	public Address getAddressById(Integer id) throws AddressNotFoundException {
		if(addrRepo.existsById(id)) {
			return addrRepo.findById(id).get();
		}
		throw new AddressNotFoundException();
	}

	@Override
	public Customer getCustomerByAddressId(Integer id) throws CustomerNotFoundException {
		if(addrRepo.existsById(id)) {
			return addrRepo.findById(id).get().getCustomer();
		}
		throw new CustomerNotFoundException();
	}

}
