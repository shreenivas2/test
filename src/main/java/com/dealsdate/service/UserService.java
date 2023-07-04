package com.dealsdate.service;

import java.util.List;

import com.dealsdate.dto.LoginRequestDto;
import com.dealsdate.entity.Customer;
import com.dealsdate.entity.User;
import com.dealsdate.exception.UserNotFoundException;

public interface UserService {

public User addUser(User user);
	
	public User loginUser(LoginRequestDto user) throws UserNotFoundException;
	
	public Customer getCustomerByUserId(Integer userId);
	
	public List<User> getAllUsers();
}
