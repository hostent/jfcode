package com.jms.service.account.dataAccess.maps;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

 

public interface IUserMapper {
 
	
	List<Map<String, Object>> getUserPage(@Param("par") Map<String, Object> par,Integer from, Integer row);
	
	Integer getUserCount(@Param("par") Map<String, Object> par);

}
