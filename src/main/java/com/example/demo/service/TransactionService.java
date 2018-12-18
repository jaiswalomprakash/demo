package com.example.demo.service;

import java.util.Collection;

import com.example.demo.model.User;

public interface TransactionService {
	
	Collection<User> getUsers();
	
	User createUser(User user);
	
	User createUserRequired(User user);
	
	User getUser(Integer userId);
}
