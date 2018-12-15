package com.example.demo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.example.demo.model.User;
import com.example.demo.repository.UserRepository;

@Service
public class OuterBeanImpl implements OuterBean {

	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private InnerBean innerBean;
	
	@Override
	@Transactional(propagation=Propagation.REQUIRED)
	public User testRequired(User user) {
		userRepository.save(user);
		try{
			innerBean.testRequired();
		} catch(RuntimeException e){
			// handle exception
		}
		return user;
	}
	
	@Override
	@Transactional(propagation=Propagation.REQUIRED)
	public User testRequiresNew(User user) {
		userRepository.save(user);
		try{
			innerBean.testRequiresNew();
		} catch(Exception e){
			// handle exception
		}
		return user;
	}

}
