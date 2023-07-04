package com.dealsdate.repositories;

import java.util.Optional;

import org.springframework.data.repository.CrudRepository;

import com.dealsdate.entity.User;

public interface UserRepository extends CrudRepository<User, Integer>{
	
	public Optional<User> findByUserName(String userName);

}
