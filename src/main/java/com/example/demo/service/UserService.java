package com.example.demo.service;

import java.util.Collection;
import java.util.Optional;

import com.example.demo.model.User;

public interface UserService {
	
	Optional<User> getUser(Integer userId);
    
	Collection<User> getUsers();
	
	User createUser(User user);
	
	User editUser(User user);
	
	void deleteUser(Integer userId);
	
	void updatePartially(User currentUser, int userId);
}
