package com.jfcore.web;

import java.io.IOException;
import java.net.URLDecoder;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.jfcore.frame.CallerContext;

public class ServiceFilter  implements Filter {
	
	private static Logger logger = LoggerFactory.getLogger(GatewayFilter.class);

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		
		
		//String callId = ((HttpServletRequest)request).getHeader("callId");
		
		//String userInfoJson = URLDecoder.decode(((HttpServletRequest)request).getHeader("userInfo"), "utf-8") ;
		
		//System.out.println(userInfoJson);
		
		
		//UserContext.setJson(userInfoJson);
		 

		try {
			
			UserRequest userRequest = new UserRequest((HttpServletRequest)request);
			
			CallerContext.setCallerID(userRequest.requestData.getCallId());
			UserContext.setJson(userRequest.requestData.getToken());
			
			
//			System.out.println("("+userRequest.getRequestURL()+")UserContext:"+UserContext.getJson() );
						
			chain.doFilter(userRequest, response);
			
			
		} catch (Exception e) {
			logger.error(e.getMessage(), e);				
			outPutResponse(response,e.getMessage());
		}		
		finally {
			CallerContext.dispose();
			UserContext.dispose();
		}
		
		
		
		
	}
	private void outPutResponse(ServletResponse response, String msg)
			throws  IOException {
 	
		((HttpServletResponse)response).setStatus(500);		

		response.setContentType("application/json;charset=UTF-8");
		
		response.getWriter().write(msg);
 
		response.flushBuffer();
	}

}
