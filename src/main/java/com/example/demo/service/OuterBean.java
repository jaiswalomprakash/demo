package com.example.demo.service;

import com.example.demo.model.User;

public interface OuterBean {

	User testRequired(User user);
	
	User testRequiresNew(User user);

}
