package com.example.demo.modules.job.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.example.demo.modules.xiaohua.entity.Joke;
@Mapper
public interface JokeInputDao {

	public void addJoke(@Param("list") List<Joke> lists);
}
