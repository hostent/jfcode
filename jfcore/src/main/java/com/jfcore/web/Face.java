package com.jfcore.web;



import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpEntity;
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
			Class<?> returnCls)
	{
		 
		logger.info("微服务地址:"+ microUrl );
		logger.info("微服务参数:"+ JsonBody );
		
		
		headers.add("callId", CallerContext.getCallerID());
		
				
		RequestData requestData = JSONObject.parseObject(JsonBody, RequestData.class);
		
    	HttpEntity<RequestData> requestEntity = new HttpEntity<RequestData>(requestData,headers);

    	
    	ResponseEntity<?> res = rest.postForEntity(microUrl, requestEntity, returnCls);
    	
    	 
		return res;
		
	}
	
 

}
