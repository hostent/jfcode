package com.jfcore.web;

 
import java.util.HashSet;
import java.util.Set;

public abstract class ServiceBeanContainer {
	
	public ServiceBeanContainer() {
		Init();
	}
	
	static Set<Class<?>> beanSet = new HashSet<Class<?>>();
	
	
	public static Class<?> getClass32(String micro,String className)
	{
		Set<Class<?>> set = beanSet;
		return null;
		
		 
	}
	
	
	
	public void addClass(Class<?> classInfo)
	{
		beanSet.add(classInfo);
	}
	
	public Set<Class<?>> getClassSet()
	{
		return beanSet;
	}
	

	
	
	public abstract void Init();
	  

}
