package com.example.demo.modules.login.service;

import com.example.demo.modules.user.entity.User;

public interface LoginUserService {

	public User queryUser4Login(User user);
	
	public User checkUserLogin(User user);
	
	public User checkUserByAcctAndPw(User user);
	
	public void updateUserPw(User user);
}
