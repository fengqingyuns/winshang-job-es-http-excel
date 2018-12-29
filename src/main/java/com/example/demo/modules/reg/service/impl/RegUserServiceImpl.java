package com.example.demo.modules.reg.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.modules.reg.dao.RegUserDao;
import com.example.demo.modules.reg.service.RegUserService;
import com.example.demo.modules.user.entity.User;



@Service
public class RegUserServiceImpl implements RegUserService{

	@Autowired
	private RegUserDao regUserDao;
	@Override
	public User findUserAcctByUserName(User user) {
		// TODO Auto-generated method stub
		return regUserDao.findUserAcctByUserName(user);
	}
	@Override
	public void addUser(User user) {
		// TODO Auto-generated method stub
		regUserDao.addUser(user);
	}
	
}
