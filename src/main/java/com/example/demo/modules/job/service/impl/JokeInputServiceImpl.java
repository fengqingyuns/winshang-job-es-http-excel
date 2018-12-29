package com.example.demo.modules.job.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.modules.job.dao.JokeInputDao;
import com.example.demo.modules.job.service.JokeInputService;
import com.example.demo.modules.xiaohua.entity.Joke;
@Service
public class JokeInputServiceImpl implements JokeInputService{

	@Autowired
	private JokeInputDao jokeInputDao;
	@Override
	public void addJoke(List<Joke> lists) {
		// TODO Auto-generated method stub
		jokeInputDao.addJoke(lists);
	}

}
