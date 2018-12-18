package com.example.demo.service;

import java.util.ArrayList;
import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.example.demo.model.User;
import com.example.demo.repository.UserRepository;

@Service
public class TransactionServiceImpl implements TransactionService {
	@Autowired
	UserRepository userRepository;

	@Autowired
	OuterBean outerBean;

	@Autowired
	InnerBean innerBean;

	// https://www.byteslounge.com/tutorials/spring-transaction-propagation-tutorial
	
	@Override
	@Transactional(propagation = Propagation.REQUIRED)
	public User createUser(User user) {
		userRepository.save(user);
		try {
			innerBean.testRequired();
		} catch (Exception e) {
			// catch exception raised from transaction rollback
		}

		return user;
	}
	
	@Override
	@Transactional(propagation = Propagation.REQUIRED)
	public User createUserRequired(User user) {
		userRepository.save(user);
		
		try {
			innerBean.testRequiresNew();
		} catch (Exception e) {
			// catch exception raised from transaction rollback
		}

		return user;
	}

	

	@Override
	@Transactional(propagation = Propagation.REQUIRED)
	public Collection<User> getUsers() {

		return makeCollection(userRepository.findAll());
	}
	
	@Override	
	public User getUser(Integer userId) {

		return userRepository.findOne(userId);
	}

	public static <E> Collection<E> makeCollection(Iterable<E> iter) {
		Collection<E> list = new ArrayList<E>();
		for (E item : iter) {
			list.add(item);
		}
		return list;
	}

}