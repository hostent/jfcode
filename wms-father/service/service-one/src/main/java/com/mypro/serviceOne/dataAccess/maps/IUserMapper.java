package com.mypro.serviceOne.dataAccess.maps;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.mypro.serviceOne.dataAccess.tables.User;

public interface IUserMapper {
	
	List<User> getUserList();
	
	User getUser(Integer ind);
	
	List<Map<String, Object>> getUserPage(@Param("par") Map<String, Object> par,Integer from, Integer row);
	
	Integer getUserCount(@Param("par") Map<String, Object> par);

}
