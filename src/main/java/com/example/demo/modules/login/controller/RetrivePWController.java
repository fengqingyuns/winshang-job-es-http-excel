package com.example.demo.modules.login.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.example.demo.modules.login.service.LoginUserService;
import com.example.demo.modules.user.entity.User;
import com.example.demo.modules.util.AJAXResult;
import com.example.demo.modules.util.MD5Util;

@Controller
public class RetrivePWController {
	  private static final Logger LOGGER = LoggerFactory.getLogger(RetrivePWController.class);
	@Autowired
	private LoginUserService loginUserService;
	@RequestMapping("/retrivepw")
	public String toRetrivepw() {
		return "login/retrivepw";
	}
	@RequestMapping("/checkUserAndPw")
	@ResponseBody
	public AJAXResult checkUserAndPw(User user) {
		AJAXResult result = new AJAXResult();
		if(StringUtils.isEmpty(user)){
			result.setSuccess(false);
			result.setMsg("数据为空");
			return result;
		}
		LOGGER.info("检查用户---user", user);
		try {
			User dbuser = loginUserService.checkUserByAcctAndPw(user);
			if(dbuser!=null){
				result.setMsg("请输入新密码");
				result.setSuccess(true);
				
			}else{
				result.setSuccess(false);
				result.setMsg("用户不存在或手机号错误");
			}
			LOGGER.info("dbuser--",dbuser);
		} catch (Exception e) {
			// TODO: handle exception
			System.out.println(e);
		}
		return result;
	}
	@RequestMapping("/updatePw")
	@ResponseBody
	public AJAXResult updatePw(User user) {
		AJAXResult result = new AJAXResult();
		if(StringUtils.isEmpty(user)){
			result.setSuccess(false);
			result.setMsg("数据为空");
			return result;
		}
		try {
			user.setUserpswd(MD5Util.digest(user.getUserpswd()));
			loginUserService.updateUserPw(user);
			result.setSuccess(true);
			result.setMsg("修改密码成功");
		} catch (Exception e) {
			// TODO: handle exception
			LOGGER.error("修改密码失败", e);
		}
		return result;
		
		
	}
}
