package com.jms.facebean.service.account;

import com.jfcore.frame.PageData;
import com.jfcore.frame.Result;
import com.jfcore.orm.Column;

import lombok.Getter;
import lombok.Setter;

public interface IUser {
	
	Result ssoLogin(String token);
	
	UserInfo getUserInfo(String token);
	
	
	@Setter@Getter
	class UserInfo
	{
		@Column(name = "id")
		private Integer id;
		
		@Column(name = "name")
		private String name;
		
		@Column(name = "phone")
		private String phone;
		
		@Column(name = "department")
		private String department;
		
		@Column(name = "post")
		private String post;
		
		@Column(name = "admin_flag")
		private Integer adminFlag;
	}


	PageData searchUserList();
	

}
