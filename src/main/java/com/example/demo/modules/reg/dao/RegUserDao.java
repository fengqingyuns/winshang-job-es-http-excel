package com.example.demo.modules.reg.dao;

import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Component;

import com.example.demo.modules.user.entity.User;
@Mapper
public interface RegUserDao {
	
	public User findUserAcctByUserName(User user);
	
	public void addUser(User user);
	
	public User checkUserLogin(String useracct);
	

}
