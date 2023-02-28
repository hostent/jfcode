package com.jfcore.web;



import java.lang.reflect.Type;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import com.alibaba.fastjson.JSONObject;
import com.jfcore.frame.CallerContext;

public class Face {
	
	private static Logger logger = LoggerFactory.getLogger(Face.class);
	
 
	
	
	public static ResponseEntity<?> CallService(
			RestTemplate rest ,
			String microUrl,
			MultiValueMap<String, String> headers,
			String JsonBody,
			Type resType)
	{
		 
		logger.debug("微服务地址:"+ microUrl );
		logger.debug("微服务参数:"+ JsonBody );
		
		
		//headers.add("callId", CallerContext.getCallerID());
		//headers.add("userInfo", UserContext.getJson());
		//headers.add("Content-type", "application/json;charset=UTF-8");
		
				
		RequestData requestData = JSONObject.parseObject(JsonBody, RequestData.class);
		requestData.setToken(UserContext.getJson());
		requestData.setCallId(CallerContext.getCallerID());
		
    	HttpEntity<RequestData> requestEntity = new HttpEntity<RequestData>(requestData,headers);
    	    	
    	ResponseEntity<?> res = rest.exchange(microUrl, HttpMethod.POST, requestEntity, ParameterizedTypeReference.forType(resType));
 
		return res;
		
	}
	
 

}
