package com.jms.gateway.controller;

import java.lang.reflect.Method;
import java.util.concurrent.CompletableFuture;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.LocalVariableTableParameterNameDiscoverer;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import com.alibaba.fastjson.JSONObject;
import com.jms.facebean.service.serviceOne.IInfo;
import com.jms.facebean.service.serviceOne.IInfo.InfoEnt;


@RestController
@RequestMapping("test")
public class Test {
	
	
	@Autowired
    private RestTemplate restTemplate;
	
	@Autowired
    private IInfo info;
	
	
	
	
	@RequestMapping("/demo1")
	public InfoEnt demo1()
	{
 
		
		 
		
		String s1 = info.getInfo("ddd", 33,3.45d);
		
		System.out.println(s1);
		
		
		
		InfoEnt infoEntRes = info.getEntInfo(new InfoEnt());
		
		System.out.println(JSONObject.toJSON(infoEntRes));
	
		
		return infoEntRes;
		
	}
	
	@RequestMapping("/demo2")
	public String demo2(String ss)
	{
		return ss;
		
	}
	
	@RequestMapping("/demo3")
	public String demo3()
	{
 
		
		return null;
		
	}

}
