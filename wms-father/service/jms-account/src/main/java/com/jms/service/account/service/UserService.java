package com.jms.service.account.service;

import java.util.Date;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.jfcore.frame.Result;
import com.jfcore.tools.PropertiesHelp;
import com.jms.service.account.dataAccess._userDb;
import com.jms.service.account.dataAccess.tables.Login;
import com.jms.service.account.dataAccess.tables.User;

import lombok.Getter;
import lombok.Setter;

@Service
public class UserService {
	
	@Getter @Setter
	public static class UserInfo
	{
		private String userName;
		private String phone;
		
	}
 
	
	public Result ssoLogin(String token,String browser)
	{
		 		
		//调用主系统的login 验证
		
		String url= PropertiesHelp.getAppConf("sys.main")+"/api/user/getInfo?token=";
		
		ResponseEntity<UserInfo> userInfoRes=new RestTemplate().getForEntity(url+token, UserService.UserInfo.class);
		
		if(userInfoRes==null || userInfoRes.getBody()==null)
		{
			return Result.failure("单点登录失败");
		}
		
		UserInfo userInfo = userInfoRes.getBody();
		
		User user = getByPhone(userInfo.getPhone());
		
		if(user==null)
		{
			return Result.failure("登录失败,用户不存在");
		}
		
		Login login = new Login();
		
		login.setBrowser(browser);
		login.setCtime(new Date());
		login.setLoginKey(token);
		login.setUid(user.getId());
		
		_userDb.getLoginSet().Add(login);
		
		
		return Result.succeed(token);
		
	}

	public User getByPhone(String phone) {
		User user = _userDb.getUserSet().Where("phone=?", phone).First();
		return user;
	}
	
	
	public User getByToken(String token) {
		
		Login login =_userDb.getLoginSet().Where("login_key=?", token).First();
		
		if(login==null)
		{
			return null;
		}
		
		User user = _userDb.getUserSet().Get(login.getUid());
		
		if(user.getStatus()!=1)
		{
			return null;
		}
		
		return user;
	}
	
	
	

}
