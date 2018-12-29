package com.example.demo.modules.login.controller;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.example.demo.modules.login.service.LoginUserService;
import com.example.demo.modules.user.entity.User;
import com.example.demo.modules.util.AJAXResult;
import com.example.demo.modules.util.MD5Util;
import com.example.demo.util.WebAppAESUtil;


@Controller
public class LoginController {

	@Autowired
	private LoginUserService loginUserService;
	/* @Autowired(required=true) 
	 protected RedisTemplate<String,String> redisTemplate;*/
	
	@RequestMapping("/tologin")
	public String login(){
		return "login/login";
	}
	//管理登录
	@SuppressWarnings("null")
	@RequestMapping("/doLogin")
	@ResponseBody
	public 	AJAXResult doLogin(User user,HttpSession session){
		AJAXResult result = new AJAXResult();
		try {
			if(null ==user){
				result.setMsg("权限错误");
				result.setSuccess(false);
				return result;
			}
			user.setLoginacct(WebAppAESUtil.decrypt(user.getLoginacct()));
			user.setUserpswd(MD5Util.digest(WebAppAESUtil.decrypt(user.getUserpswd())));
			
			User dbuser = null;
	
				dbuser = loginUserService.queryUser4Login(user);
				if(null==dbuser){
					result.setSuccess(false);
					result.setMsg("权限错误");
					return result;
			}
		
		
			
			result.setSuccess(true);
			result.setData(dbuser);
			
		} catch (Exception e) {
			System.out.println(e);
			result.setSuccess(false);
		}
		
		return result;
	}
	//去往会员中心
	@RequestMapping("/member")
	public String tomember(){
		
		return "member/index";
	}
	//去往主页面
	/*@RequestMapping("/main")
	public String tomain(){
		
		return "main";
	}*/
	//会员登录
	@RequestMapping("/doMemberLogin")
	@ResponseBody
	public AJAXResult doMemberLogin(User user,HttpSession session) throws Exception{
		AJAXResult result = new AJAXResult();
		if(null ==user){
			result.setSuccess(false);
		}
		try {
			String acct = WebAppAESUtil.decrypt(user.getLoginacct());
			user.setLoginacct(acct);
			user.setUserpswd(MD5Util.digest(WebAppAESUtil.decrypt(user.getUserpswd())));
			User dbuser = loginUserService.queryUser4Login(user);
			if(null ==dbuser){
				result.setSuccess(false);
				result.setMsg("权限错误");
				return result;
			}
			session.setAttribute("user", dbuser);
			result.setSuccess(true);
			result.setMsg("登录成功");
		} catch (Exception e) {
			// TODO: handle exception
			result.setSuccess(false);
			result.setMsg("用户名不存在");
		}
	
		return result;
	}
	@RequestMapping("/checkUserLogin")
	@ResponseBody
	public Object checkUserLogin(User user){
		AJAXResult result = new AJAXResult();
		try {
			if(StringUtils.isEmpty(user)){
				result.setMsg("2");
			}
			User checkUserLogin = loginUserService.checkUserLogin(user);
			if(null!=checkUserLogin){
				result.setMsg("1");
			}else{
				result.setMsg("2");
			}
		} catch (Exception e) {
			// TODO: handle exception
			result.setMsg("2");
		}
		return result;
		
	}
	
	
}
