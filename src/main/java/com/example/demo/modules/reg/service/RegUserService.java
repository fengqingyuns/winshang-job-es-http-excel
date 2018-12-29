package com.example.demo.modules.reg.service;

import com.example.demo.modules.user.entity.User;

public interface RegUserService {

	public User findUserAcctByUserName(User user);
	
	public void addUser(User user);
	
	
	
	
}
