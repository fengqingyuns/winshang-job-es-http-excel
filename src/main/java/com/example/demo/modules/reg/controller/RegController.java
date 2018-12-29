package com.example.demo.modules.reg.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.example.demo.modules.reg.service.RegUserService;
import com.example.demo.modules.user.entity.User;
import com.example.demo.modules.util.AJAXResult;
import com.example.demo.modules.util.MD5Util;




@Controller
public class RegController {

	@Autowired
	private RegUserService regUserService;
	//注册
		@RequestMapping("/reg")
		public String reg(){
			return "login/reg";
		}
		
		//验证用户名是否已注册
		@RequestMapping("/checkUser")
		@ResponseBody
		public AJAXResult checkUserRe(User user){
			AJAXResult result = new AJAXResult();
			if(StringUtils.isEmpty(user)){
				result.setMsg("数据为空");
				result.setSuccess(false);
				return result;
			}
			try {
				User tempuser = regUserService.findUserAcctByUserName(user);
				if(tempuser!=null){
					result.setSuccess(false);
					result.setMsg("用户名不可用");
				}else{
					result.setSuccess(true);
					result.setMsg("用户名可用");
				}
			} catch (Exception e) {
				// TODO: handle exception
			}
			return result;
		}
		
		@RequestMapping("/RegUser")
		@ResponseBody
		public AJAXResult RegUser(User user){
			AJAXResult result = new AJAXResult();
			try {
				if(StringUtils.isEmpty(user)){
					result.setSuccess(false);
					result.setMsg("输入为空");
					return result;
				}
				user.setUserpswd(MD5Util.digest(user.getUserpswd()));
				regUserService.addUser(user);
				result.setSuccess(true);
				result.setMsg("注册成功");
			} catch (Exception e) {
				// TODO: handle exception
				result.setSuccess(false);
			}
			return result;
		}
}
