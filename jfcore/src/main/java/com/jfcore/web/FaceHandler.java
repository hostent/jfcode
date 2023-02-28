package com.jfcore.web;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.util.HashMap;
import java.util.List;

import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.ResponseEntity;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import com.alibaba.fastjson.JSONObject;
import com.jfcore.frame.CallerContext;

public class FaceHandler implements InvocationHandler {
	

	
	Class<?> _cls=null;
	
	
	public FaceHandler(Class<?> cls) {
		_cls =cls;

	}
	
 
	

	@Override
	public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {

		
		String packagePath = _cls.getPackage().getName();
		
		int ind = packagePath.indexOf(".service.")+9;
		
		String micro = packagePath.substring(ind, packagePath.length());
				
		String microUrl = "http://"+micro+"/"+ _cls.getSimpleName()+"/"+method.getName();
		
		
		MultiValueMap<String, String> headers = new LinkedMultiValueMap<String, String>();
		
		
		
		HashMap<String, Object> pars  =new HashMap<String, Object>();
		
		
		Parameter[] parameters = method.getParameters();
 		
		for (int i = 0; i < parameters.length; i++) {
			
			Parameter parameter = parameters[i];			
			pars.put(parameter.getName(), args[i]);
			
		}
		// 要获取真实的参数，必须在相关的jar项目的编译编辑加速参数 -parameters
		/*
		 * 确保 java8
		 *   <properties>
			    	<project.build.sourceEncoding>UTF-8</project.build.sourceEncoding>
			    	<project.reporting.outputEncoding>UTF-8</project.reporting.outputEncoding>
					<java.version>1.8</java.version>
					<maven.compiler.source>1.8</maven.compiler.source>
					<maven.compiler.target>1.8</maven.compiler.target>
			  </properties>
			  
		 * mvn编译插件
			  <build>
				<plugins>
			 
					<plugin>
						<groupId>org.apache.maven.plugins</groupId>
						<artifactId>maven-compiler-plugin</artifactId>
						<configuration>
							<source>1.8</source>
							<target>1.8</target>
							<compilerArgs>
								 <arg>-parameters</arg>
							</compilerArgs>
						</configuration>
			 
					</plugin>
			 
				</plugins>
			  </build>
		 */
				 
 
		RequestData requestData = new RequestData();
		requestData.setCallId(CallerContext.getCallerID());
		requestData.setPars(pars);
 
		
		RestTemplate restTemplate = ServiceListener.getSingletonBean(RestTemplate.class);
		ResponseEntity<?> res = Face.CallService(restTemplate , microUrl, headers, JSONObject.toJSONString(requestData),method.getGenericReturnType());
		
		
		
		//ParameterizedTypeReference tyoe = new ParameterizedTypeReference<T>() {
		//};
		
		//ParameterizedTypeReference.forType(method.getGenericReturnType())
		//new ParameterizedTypeReference<List<UtProjectAtt>>() {
        //}
		
		//Arrays.as
		//if()
		
		return res.getBody();
	}

}
