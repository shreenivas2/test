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

import com.dealsdate.dto.LoginRequestDto;
import com.dealsdate.entity.Customer;
import com.dealsdate.entity.User;
import com.dealsdate.exception.UserNotFoundException;
import com.dealsdate.service.UserService;

import jakarta.validation.Valid;

@RestController
@CrossOrigin("*")
public class UserController {
	
	Logger logger = Logger.getLogger(UserController.class.getName());

	public UserController() {
		logger.log(Level.INFO,"-----> UserController is Working!");
		
	}

	@Autowired
	UserService userSer;
	
	//http://localhost:8080/adduser
	@PostMapping("/adduser")
	public User addUser(@Valid @RequestBody User user) {
	    return userSer.addUser(user);
	}
	
	//http://localhost:8080/loginuser
	@PostMapping("/loginuser")
	public User loginUser(@Valid @RequestBody LoginRequestDto user) throws UserNotFoundException {
		return userSer.loginUser(user);
	}
	
	//http://localhost:8080/getallusers
	@GetMapping("/getallusers")
	public List<User> getAllUsers() {
		return userSer.getAllUsers();
	}
	
	@GetMapping("/getcustbyuserid/{userId}")
	public Customer getCustByUserId(@PathVariable("userId") Integer userId) {
		return userSer.getCustomerByUserId(userId);
	}
}
