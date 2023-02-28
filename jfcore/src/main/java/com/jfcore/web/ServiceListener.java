package com.jfcore.web;


import java.lang.reflect.Proxy;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.beans.factory.support.BeanDefinitionRegistryPostProcessor;

 
 

public abstract class ServiceListener implements  BeanDefinitionRegistryPostProcessor {

	private static Logger logger = LoggerFactory.getLogger(ServiceListener.class);
 
	private ServiceBeanContainer _serviceBeanContainer=null;
	
	private static ConfigurableListableBeanFactory _beanFactory;
	
	public abstract ServiceBeanContainer regist();
	
	
	public static <T> T getSingletonBean(Class<T> cls)
	{		
		return _beanFactory.getBean(cls);
		
	}
	
	
	public ServiceListener() {
		
		_serviceBeanContainer = regist();
		
		if(_serviceBeanContainer==null)
		{	
			logger.error("_serviceBeanContainer null , please regist");
		}		 

	}
	
	
	@Override
	public void postProcessBeanFactory(ConfigurableListableBeanFactory beanFactory) throws BeansException {
		
		_beanFactory=beanFactory;
		
		Set<Class<?>> classSet = _serviceBeanContainer.getClassSet();
				
		for (Class<?> cls : classSet) {

			beanFactory.registerSingleton(cls.getName(), getService(cls,beanFactory));
		}
		
	}

	
	private <T> T getService(Class<T> mapperInterface,ConfigurableListableBeanFactory beanFactory)
	{
		ClassLoader classLoader = mapperInterface.getClassLoader();
		
		Class<?>[] interfaces = new Class[] { mapperInterface };
		
		
		FaceHandler faceHandler=new FaceHandler(mapperInterface);
		
		@SuppressWarnings("unchecked")
		T t = (T)Proxy.newProxyInstance(classLoader, interfaces, faceHandler);
		 
		 return t;
		
		
	}

	@Override
	public void postProcessBeanDefinitionRegistry(BeanDefinitionRegistry registry) throws BeansException {

	}

}
