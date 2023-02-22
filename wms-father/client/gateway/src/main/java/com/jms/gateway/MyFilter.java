package com.jms.gateway;


import java.util.HashSet;
import java.util.Set;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import com.jfcore.frame.Result;
import com.jfcore.web.GatewayFilter;
import com.jms.facebean.service.account.IUser;
import com.jms.facebean.service.account.IUser.UserInfo;


@WebFilter(filterName = "GatewayFilter", urlPatterns = "/api/v1/*")
public class MyFilter extends GatewayFilter {
	
	public static  Set<String> noLoginFilter = new HashSet< String>();
	
	static {
		
		noLoginFilter.add("IUser/ssoLogin");
		
	}
		
	 
	
	@Autowired
	IUser userFace;
	
	@Override
	public Result befour(ServletRequest request) {
		
		String url =  ((HttpServletRequest) request).getRequestURI();
		
		for (String str : noLoginFilter) {
			
			if(url.toLowerCase().contains(str.toLowerCase()))
			{
				return Result.succeed();
			}
			
		}
		
		//判断用户登录
		
		String token = ((HttpServletRequest) request).getHeader("wms-token");
		
		UserInfo userInfo = userFace.getUserInfo(token);
		if(userInfo==null)
		{
			return Result.failure(-2, "未登录");
		}
		
		//权限判断，TODO ，采用接口地址判断
		
		
		return Result.succeed();
		
		
		
		 
	}
	
	
	@Override
	public Result after(ServletRequest request, ServletResponse response, String body) {
		return super.after(request, response, body);
		
	}
}





