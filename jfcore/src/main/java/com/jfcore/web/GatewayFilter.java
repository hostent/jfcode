package com.jfcore.web;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Enumeration;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletInputStream;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;
import com.alibaba.fastjson.JSONObject;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.jfcore.frame.CallerContext;
import com.jfcore.frame.Result;
 

 
/*

http://127.0.0.1:8001/api/v1/service-one/myinfo/getInfo 注意大小写

*/

//@WebFilter(filterName = "GatewayFilter", urlPatterns = "/api/v1/*")
public class GatewayFilter  implements Filter {
	
	String pathRoot = "";
	
	private static Logger logger = LoggerFactory.getLogger(GatewayFilter.class);
	
	
	@Autowired
    private RestTemplate restTemplate;
	
	

	public void init(FilterConfig filterConfig) throws ServletException {
		pathRoot = this.getClass().getAnnotation(WebFilter.class).urlPatterns()[0].replace("*", "");
		
	}
	
	public  Result befour(ServletRequest request)
	{
		return Result.succeed();
		
	}
	
	public  Result after(ServletRequest request,ServletResponse response,String body)
	{
		return Result.succeed();
		
	}

	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		
		CallerContext.setCallerID();
		
		try {
			
			
			if(!((HttpServletRequest) request).getMethod().toLowerCase().equals("post"))
			{
				outPutResponse(response,"只允许post调用");				
				return ;
			}
			

			String url =  ((HttpServletRequest) request).getRequestURI();
			
			
			Enumeration<?> enuHead=((HttpServletRequest) request).getHeaderNames();
			
			
			String microUrl =String.format("http://%s", url.replaceFirst(pathRoot, ""));
			
	
			MultiValueMap<String, String> headers = new LinkedMultiValueMap<String, String>();
			while(enuHead.hasMoreElements()) {
				 String paraName=(String)enuHead.nextElement();  
				 headers.add(paraName, request.getParameter(paraName));
			}
			 
			String json =getJsonBody(request);
			
			if(json == null )
			{				
				outPutResponse(response,"post 参数不合法,必须满足RequestData结构");				
				return ;
			}
			RequestData requestData = JSONObject.parseObject(json, RequestData.class);
			if(requestData==null)
			{
				outPutResponse(response,"post 参数不合法,必须满足RequestData结构");				
				return ;
			}
			
			Result resBefour =befour(request);
			
			if(!resBefour.isSucceed())
			{
				 outPutError(response,resBefour);
				 return;
			}
			
			requestData.setCallId(CallerContext.getCallerID());
	    	
			@SuppressWarnings("unchecked")
			ResponseEntity<String>  res = (ResponseEntity<String>) Face.CallService(restTemplate, microUrl, headers, JSONObject.toJSONString(requestData),String.class);
				
			
			if(res.getStatusCode()!=HttpStatus.OK)
			{
 				outPutError(response,res.getStatusCodeValue()); 
				return;
			}
	    	
	    	outPutResponse(response,res.getBody());
	    	
	    	//如果是文件下载，可以把文件上传到文件站，返回地址
	    	//返回值也是需要统一的，后续处理 TODO
	    	
	    	
	    	after(request,response,res.getBody());


		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			outPutError(response,500);
		}
		finally {
			CallerContext.dispose();
		}
	}

 
	
	private String getJsonBody(ServletRequest request)
	{
		try {
			
			ServletInputStream requestInputStream = request.getInputStream();
			//将字节流转换为字符流,并设置字符编码为utf-8
			InputStreamReader ir = new InputStreamReader(requestInputStream,"utf-8");
			//使用字符缓冲流进行读取
			BufferedReader br = new BufferedReader(ir);
			//开始拼装json字符串
			String line = null;
			StringBuilder sb = new StringBuilder();
			while((line = br.readLine())!=null) {
				sb.append(line);
			}
			
			return sb.toString();
			
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
		return null;
		
 
	}
	
	
	private void outPutError(ServletResponse response, int code) 
			throws JsonProcessingException, IOException{
		Result result = new Result();
		result.setStatus(code);
		result.setMessage("调用异常:"+CallerContext.getCallerID());
		
		outPutResponse(response,JSONObject.toJSONString(result));
	}
	
	private void outPutError(ServletResponse response, Result result) throws JsonProcessingException, IOException {
		 
		
		outPutResponse(response,JSONObject.toJSONString(result));
	}
	
	private void outPutResponse(ServletResponse response, String json)
			throws JsonProcessingException, IOException {
 
		//String json = JSON.toJSONString(apiResponse);

		response.setContentType("application/json;charset=UTF-8");
		
		response.getWriter().write(json);
 
		response.flushBuffer();
	}

	public void destroy() {
		 
		
	}

}
