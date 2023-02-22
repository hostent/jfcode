package com.jms.service.account.dataAccess;

import com.jfcore.orm.DbSetMy;
import com.jfcore.orm.MybatisUtils;
import com.jms.service.account.dataAccess.maps.IUserMapper;
import com.jms.service.account.dataAccess.tables.*;
 

public class _userDb {
	
	
	static String getConnKey()
	{
		return "wms";
	}
	
	
	public static DbSetMy<User> getUserSet()
	{
				
		return new DbSetMy<User>(getConnKey(),User.class);
		 
	}
	
	public static DbSetMy<Login> getLoginSet()
	{
				
		return new DbSetMy<Login>(getConnKey(),Login.class);
		 
	}
	
	public static DbSetMy<Right> getRightSet()
	{
				
		return new DbSetMy<Right>(getConnKey(),Right.class);
		 
	}
	
	public static DbSetMy<Role> getRoleSet()
	{
				
		return new DbSetMy<Role>(getConnKey(),Role.class);
		 
	}
	
	public static DbSetMy<UserRole> getUserRoleSet()
	{
				
		return new DbSetMy<UserRole>(getConnKey(),UserRole.class);
		 
	}
	
	
	public static final IUserMapper getUserMapper()
	{
		IUserMapper sqlMapper = MybatisUtils.getProxyMapper(getConnKey(),IUserMapper.class);
		
		return sqlMapper;
	}

 
}
