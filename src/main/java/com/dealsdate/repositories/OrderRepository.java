package com.dealsdate.repositories;

import org.springframework.data.repository.CrudRepository;

import com.dealsdate.entity.Order;

public interface OrderRepository extends CrudRepository<Order, Integer>{

}
