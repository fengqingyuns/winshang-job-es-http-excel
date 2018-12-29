package com.example.demo.modules.job.handler;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.example.demo.util.DateCalcUtil;
import com.xxl.job.core.biz.model.ReturnT;
import com.xxl.job.core.handler.IJobHandler;
import com.xxl.job.core.handler.annotation.JobHander;
import com.xxl.job.core.log.XxlJobLogger;
import com.example.demo.modules.job.service.JokeInputService;
import com.example.demo.modules.util.AJAXResult;
import com.example.demo.modules.xiaohua.entity.Joke;
import com.example.demo.modules.xiaohua.service.*;
@JobHander(value="jokeHandler")
@Component
public class XiaoHuaHandler extends IJobHandler {
	private static final Logger LOGGER = LoggerFactory.getLogger(XiaoHuaHandler.class);
	@Autowired
	private JokeService jokeService;
	@Autowired
	private JokeInputService jokeInputService;
	private static int num = 1;

	@Override
	public ReturnT<String> execute(String... params) throws Exception {
		String page = null;
		String date = null;
		String pagesize = "20";
		String sort = "desc";
		Long time = null;
		num = jokeService.getNum();
		ArrayList<Joke> list = null;
		SimpleDateFormat df = new SimpleDateFormat("yyyyMMddHH");// 设置日期格式
		if (params != null) {
			if (params.length >= 1) {
				
				date = params[0];// new Date()为获取当前系统时间，也可使用当前时间戳
				XxlJobLogger.log("limitTime: {0}, releasenum: {1}", date);
			}

			if (params.length >= 2) {
				if (StringUtils.isNotBlank(params[1])) {
					page = params[1].trim();
					XxlJobLogger.log("limitTime: {0}, releasenum: {1}", page);
				}
			}
		}
		if (date == null) {
		//	date = df.format(new Date().getTime());// 获取前一天时间，也可使用当前时间戳
			

			time = System.currentTimeMillis();
			date = String.valueOf(time).substring(0, 10);
			XxlJobLogger.log("limitTime: {0}, releasenum: {1}", time);
		}
		// XxlJobLogger.log(JsonUtil.toJson(result));
		if (null == page) {
			page = num + "";
		}
		
		AJAXResult result = jokeService.getJokes(page, pagesize, date, sort);
		if (null != result) {
			try {
				list = (ArrayList<Joke>) result.getData();
				
				jokeInputService.addJoke(list);
				LOGGER.info("获取joke数据", list.toArray().toString());
			} catch (Exception e) {
				// TODO: handle exception
				LOGGER.error("获取joke数据失败",e);
			}
			
		}
		num++;
		jokeService.setNum(num);
		return ReturnT.SUCCESS;
	}

}
