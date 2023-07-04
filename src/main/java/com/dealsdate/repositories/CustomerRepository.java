package com.dealsdate.repositories;

import org.springframework.data.repository.CrudRepository;

import com.dealsdate.entity.Customer;

public interface CustomerRepository extends CrudRepository<Customer, Integer>{

	public Customer findByMobileNo(String mobileNo);

}
