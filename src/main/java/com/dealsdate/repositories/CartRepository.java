package com.dealsdate.repositories;

import org.springframework.data.repository.CrudRepository;

import com.dealsdate.entity.Cart;

public interface CartRepository extends CrudRepository<Cart, Integer>{

}
