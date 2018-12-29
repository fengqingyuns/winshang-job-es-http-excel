package com.example.demo.modules.login.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.modules.login.dao.LoginDao;
import com.example.demo.modules.login.service.LoginUserService;
import com.example.demo.modules.user.entity.User;



@Service
public class LoginUserServiceImpl implements LoginUserService{

	@Autowired
	private LoginDao loginDao;
	@Override
	public User queryUser4Login(User user) {
		// TODO Auto-generated method stub
		return loginDao.queryUser4Login(user);
	}
	@Override
	public User checkUserLogin(User user) {
		// TODO Auto-generated method stub
		return loginDao.checkUserLogin(user);
	}
	@Override
	public User checkUserByAcctAndPw(User user) {
		// TODO Auto-generated method stub
		return loginDao.checkUserByAcctAndPw(user);
	}
	@Override
	public void updateUserPw(User user) {
		// TODO Auto-generated method stub
		loginDao.updateUserPw(user);
	}

	
}
