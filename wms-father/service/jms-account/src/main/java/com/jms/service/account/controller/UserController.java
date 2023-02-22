package com.jms.service.account.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cglib.beans.BeanCopier;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.jfcore.frame.PageData;
import com.jfcore.frame.PagePars;
import com.jfcore.frame.Result;
import com.jms.facebean.service.account.IUser;
import com.jms.service.account.dataAccess._userDb;
import com.jms.service.account.dataAccess.tables.User;
import com.jms.service.account.service.UserService;
 

 

@RestController
@RequestMapping("IUser")
public class UserController implements IUser {
	
	@Autowired
	UserService userService;
	
	@Autowired
    HttpServletRequest httpServletRequest;
	
	
	
 


	@RequestMapping("/ssoLogin")
	@Override
	public Result ssoLogin(String token) {
		 
		return userService.ssoLogin(token, httpServletRequest.getHeader("User-Agent"));
	}





	@RequestMapping("/getUserInfo")
	@Override
	public UserInfo getUserInfo(String token) {
		
		User user = userService.getByToken(token);
		if(user==null)
		{
			return null;
		}
		
		BeanCopier copier = BeanCopier.create(User.class, UserInfo.class, false);
		UserInfo userVo = new UserInfo();
        copier.copy(user, userVo, null);
 
		
		return userVo;
		 
	}
	

	
	@Override
	@RequestMapping("/searchUserList")
	public PageData searchUserList()
	{
		PagePars pagePars = new PagePars(httpServletRequest);
		
		
		PageData pd = new PageData();
		
		pd.setRows(_userDb.getUserMapper().getUserPage(pagePars.getPars(), pagePars.getFrom(), pagePars.getPageSize()));
		pd.setTotal(_userDb.getUserMapper().getUserCount(pagePars.getPars()));
		
		return pd;
	}
	
}
