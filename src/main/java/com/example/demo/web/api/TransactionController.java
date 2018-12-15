package com.example.demo.web.api;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.model.User;
import com.example.demo.service.TransactionService;



@RestController
public class TransactionController {
	
	@Autowired
	TransactionService transactionService;
	
	@RequestMapping(value="tx/users" ,method =RequestMethod.GET,produces =MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Collection<User>> getUsers(){
		
		 Collection<User> users = transactionService.getUsers();
		
		return new ResponseEntity<Collection<User>>(users,HttpStatus.OK);
		
		
	}
	@RequestMapping(
			value="tx/users",
			method =RequestMethod.POST,
			produces = MediaType.APPLICATION_JSON_VALUE,
			consumes = MediaType.APPLICATION_JSON_VALUE
			)
	public ResponseEntity<User> createUser(@RequestBody User user){		
		User savedUser = transactionService.createUser(user);		
		return new ResponseEntity<User>(savedUser,HttpStatus.CREATED);
		
	}
	
	@RequestMapping(
			value="tx/required",
			method =RequestMethod.POST,
			produces = MediaType.APPLICATION_JSON_VALUE,
			consumes = MediaType.APPLICATION_JSON_VALUE
			)
	public ResponseEntity<User> createUserRequied(@RequestBody User user){		
		User savedUser = transactionService.createUserRequired(user);		
		return new ResponseEntity<User>(savedUser,HttpStatus.CREATED);
		
	}
	
	
	

}
