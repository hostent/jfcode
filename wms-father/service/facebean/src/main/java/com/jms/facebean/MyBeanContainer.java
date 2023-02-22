package com.jms.facebean;

import com.jfcore.web.ServiceBeanContainer;
import com.jms.facebean.service.account.*;
import com.jms.facebean.service.serviceOne.IInfo;

public class MyBeanContainer extends ServiceBeanContainer {
	
	
 
	@Override
	public void Init() {
		 
		this.addClass(IInfo.class);
		this.addClass(IUser.class);
 
		
		
	}
	
}
