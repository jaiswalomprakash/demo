package com.example.demo.service;

import java.util.Collection;

import com.example.demo.model.Greeting;
import com.example.demo.model.User;

public interface GreetingService {

    Collection<Greeting> findAll();

    Greeting findOne(Long id);

    Greeting create(Greeting greeting);

    Greeting update(Greeting greeting);

    void delete(Long id);

	User createUser(User user);
	
   public Collection<User> findAllUser();

}
