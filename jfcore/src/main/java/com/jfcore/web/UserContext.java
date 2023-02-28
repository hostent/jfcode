package com.jfcore.web;

import org.springframework.context.annotation.Configuration;

import com.alibaba.fastjson.JSONObject;

//@Configuration
public class UserContext<T> {
	
	
	 private  static ThreadLocal<String> userInfo = new ThreadLocal<String>();
	 
	 public void setUserInfo(T value){
		 
		 String json = JSONObject.toJSONString(value);
		 
		 userInfo.set(json);
		 
	 }
	 
 
	 
	 public T getUserInfo(Class<T> cls){
		 
		 String json = userInfo.get();
		 
		 if(json==null)
		 {
			 return null;
		 }
		 
		 return JSONObject.parseObject(json, cls);
		 
	 }
	 
	 public static String getJson() {
		 String json = userInfo.get();
		 
		 return json;
	 }
	 
	 public static void setJson(String json) {
		 userInfo.set(json);
	 }
	 
	 
	
	 public static void dispose() {
		 
		 userInfo.remove();
		 
	 }
	

}
