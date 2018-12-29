package com.example.demo.modules.login.dao;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.session.SqlSessionFactory;

import org.mybatis.spring.support.SqlSessionDaoSupport;
import com.example.demo.modules.user.entity.User;
@Mapper
public interface LoginDao {

	public User queryUser4Login(User user);
	
	public User checkUserLogin(User user);
	
	public User checkUserByAcctAndPw(User user);
	
	public void updateUserPw(User user);
}
