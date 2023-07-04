package com.dealsdate.service;

import java.util.List;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.dealsdate.dto.LoginRequestDto;
import com.dealsdate.entity.Customer;
import com.dealsdate.entity.User;
import com.dealsdate.exception.UserNotFoundException;
import com.dealsdate.repositories.UserRepository;

import jakarta.transaction.Transactional;

@Service
@Transactional
public class UserServiceImpl implements UserService {

	@Autowired
	UserRepository userRepo;
	
	@Override
	public User addUser(User user) {
		return userRepo.save(user);
	}

	@Override
	public User loginUser(LoginRequestDto user) throws UserNotFoundException {
		Optional<User> foundUser = userRepo.findByUserName(user.getUserName());
		if(foundUser.isPresent()) {
			if((foundUser.get().getUserPassword().equals(user.getPassword()))) {
				return foundUser.get();
			} else {
				throw new UserNotFoundException("Password does not match! Try again.");
			}
		} else {
			throw new UserNotFoundException("UserName not Found! Try again or Create a new Account");
		}
	}

	@Override
	public List<User> getAllUsers() {
		return (List<User>) userRepo.findAll();
	}

	@Override
	public Customer getCustomerByUserId(Integer userId) {
		Optional<User> optUser = userRepo.findById(userId);
		if(optUser.isPresent()) {
			User user = optUser.get();
			return user.getCustomer();
		}
		return null;
	}
}
