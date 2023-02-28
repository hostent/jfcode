package com.jms.facebean;

import com.jfcore.web.ServiceBeanContainer;
import com.jms.facebean.service.account.*;
import com.jms.facebean.service.serviceOne.IInfo;
import com.jms.facebean.service.serviceOne.IMyJob;

public class MyBeanContainer extends ServiceBeanContainer {
	
	
 
	@Override
	public void Init() {
		 
		this.addClass(IInfo.class);
		this.addClass(IUser.class);
 
		this.addClass(IMyJob.class);
		
		
	}
	
}
