package com.mypro.serviceOne.dataAccess;

 

import com.jfcore.orm.DbSetMy;
import com.jfcore.orm.MybatisUtils;
import com.mypro.serviceOne.dataAccess.maps.IUserMapper;
import com.mypro.serviceOne.dataAccess.tables.User;

 

public class _userDb {
	
	
	static String getConnKey()
	{
		return "account";
	}
	
	
	public static DbSetMy<User> getUserSet()
	{
				
		return new DbSetMy<User>(getConnKey(),User.class);
		 
	}
	
	public static final IUserMapper getUserMapper()
	{
		IUserMapper sqlMapper = MybatisUtils.getProxyMapper(getConnKey(),IUserMapper.class);
		
		return sqlMapper;
	}


}
