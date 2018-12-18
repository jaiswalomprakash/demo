package com.example.demo.service;

import java.util.Collection;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.model.User;
import com.example.demo.repository.UserRepository;

@Service
public class UserServiceImpl implements UserService,DemoService {
	
	@Autowired
	UserRepository userRepository; 
	
	@Override
	public Optional<User> getUser(Integer userId) {	 
		return Optional.ofNullable(userRepository.findOne(userId));
	}

	@Override
	public Collection<User> getUsers() {		
		return DemoService.makeCollection(userRepository.findAll());
	}

	

	@Override
	public User createUser(User user) {		
		return userRepository.save(user);
	}

	@Override
	public User editUser(User user) {
		return userRepository.save(user);
	}

	@Override
	public void deleteUser(Integer userId) {
		 userRepository.delete(userId);
	}
	
	

}
