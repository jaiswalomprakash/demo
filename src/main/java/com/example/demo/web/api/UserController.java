package com.example.demo.web.api;



import java.net.URI;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.example.demo.model.User;
import com.example.demo.service.UserService;

@RestController
public class UserController {
	
	
	@Autowired
	UserService userService;
	
	//@RequestMapping(value="api/user{id}", method=RequestMethod.GET,produces=MediaType.APPLICATION_JSON_VALUE)
	@GetMapping("/user/{userId}")
    public ResponseEntity<User> getUserById(@PathVariable("userId") Integer userId){
		Optional<User> user = userService.getUser(userId);
		if (!user.isPresent())
			throw new RuntimeException("id-" + userId);		
		return new ResponseEntity<>(user.get(),HttpStatus.OK);	   
   }
	
	@DeleteMapping("/user/{userId}")
	public ResponseEntity<Integer>  deleteStudent(@PathVariable Integer userId) {
		userService.deleteUser(userId);
		return new ResponseEntity<>(userId,HttpStatus.OK);	  
	}
	
	@PostMapping("/user")
	public ResponseEntity<Void> createStudent(@RequestBody User user ) {
		User createdUser = userService.createUser(user);

		URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{userId}")
				.buildAndExpand(createdUser.getId()).toUri();

		return ResponseEntity.created(location).build();

	}
	
	@PutMapping("/user/{userId}")
	public ResponseEntity<Void> updateStudent(@RequestBody User user, @PathVariable Integer userId) {
		Optional<User> userOptional = userService.getUser(userId);
		if (!userOptional.isPresent())
			return ResponseEntity.notFound().build();
		user.setId(userId);		
		userService.createUser(user);
		return ResponseEntity.noContent().build();
	}
	
	

}
