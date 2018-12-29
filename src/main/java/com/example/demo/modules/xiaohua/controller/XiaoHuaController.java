package com.example.demo.modules.xiaohua.controller;

import java.util.ArrayList;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.example.demo.config.UrlConfig;
import com.example.demo.http.HttpClient;
import com.example.demo.http.HttpClientResponse;
import com.example.demo.modules.util.AJAXResult;
import com.example.demo.modules.xiaohua.entity.Joke;

import net.sf.json.JSONObject;

@Controller
public class XiaoHuaController {
	private static final Logger LOGGER = LoggerFactory.getLogger(XiaoHuaController.class);
	
	@Autowired
	private JdbcTemplate jdbc;
	/** 连接超时时间，毫秒 */
	protected static final int HTTP_CONN_TIME_OUT = 10000;
	/** 读取超时时间，毫秒 */
	protected static final int HTTP_SOCKET_TIME_OUT = 20000;
	
	@RequestMapping("/xiaohua")
	public String toXiaoHua() {
		return "/xiaohua/xiaohua";
	}
	
	@RequestMapping("/getJokes")
	@ResponseBody
	public AJAXResult getJokes(String pagesize,String pageno) {
		AJAXResult result = new AJAXResult();
		try {
			List<Joke> list = jdbc.query("select * from t_joke limit()",new Object[]{}, new BeanPropertyRowMapper<Joke>(Joke.class));
			LOGGER.info("joke: {}", list);
			result.setData(list);
			result.setSuccess(true);
		} catch (Exception e) {
			// TODO: handle exception
			result.setMsg("error");
			result.setSuccess(false);
		}
		
		return result;
	}
	
}
