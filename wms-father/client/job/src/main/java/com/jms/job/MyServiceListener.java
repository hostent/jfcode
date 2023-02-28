package com.jms.job;



import org.springframework.context.annotation.Configuration;
import com.jfcore.web.ServiceBeanContainer;
import com.jfcore.web.ServiceListener;
import com.jms.facebean.MyBeanContainer;
 
 


@Configuration
public class MyServiceListener extends ServiceListener {

	@Override
	public ServiceBeanContainer regist() {		 
		return new MyBeanContainer();
	} 

}
