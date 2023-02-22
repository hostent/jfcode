package com.mypro.serviceOne;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

import com.jfcore.frame.Result;
import com.jfcore.orm.MybatisUtils;
import com.mypro.serviceOne.dataAccess._userDb;
import com.mypro.serviceOne.dataAccess.tables.User;

import java.util.Date;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


@ServletComponentScan
@EnableDiscoveryClient  
@SpringBootApplication
public class ServerOneApp 
{
	
	private static Logger logger = LoggerFactory.getLogger(ServerOneApp.class);
	
	
    public static void main( String[] args )
    {
    	SpringApplication sa= new SpringApplication(ServerOneApp.class);
    	
    	sa.run(args);
    	
    	
    	
    	
    	
    	
    	
/* 	
    	 _userDb.getUserMapper().getUserList();
    	
    	logger.info("开始 mybatis");
    	for (int i = 0; i < 1000; i++) {
    		
    		//User list = _userDb.getUserMapper().getUser(i);
    		 _userDb.getUserMapper().getUserList();  //16s
    		 
    		
    	}
    	logger.info("结束 mybatis");
    	
    	//System.out.println(list.size());
    	
    	
    	    	
    	
    	//--------------------------   
    	_userDb.getUserSet().ToList();
    	logger.info("开始 jform");
    	
    	//MybatisUtils.beginTran();
    	for (int i = 0; i < 1000; i++) {
    		 //User user = _userDb.getUserSet().Limit(i, 1).First();
    		 
    		_userDb.getUserSet().ToList(); //28
 
		}
    	//MybatisUtils.commit();
    	logger.info("结束 jform");
    	
    	
    	//--------------------------   
    	_userDb.getUserSet().ToList();
    	logger.info("开始 jform");
    	
    	//MybatisUtils.beginTran();
    	for (int i = 0; i < 10000; i++) {
   		 	//User user = _userDb.getUserSet().Limit(i, 1).First();
   		 
    		_userDb.getUserSet().Limit(0, 100).ToList();

		}
    	//MybatisUtils.commit();
    	logger.info("结束 jform");
    	
 */	
    	
    	
    	
    	

    	

 
    	
    	//--------------------------
//    	logger.info("开始");
//    	
//    	for (int i = 0; i < 3000; i++) {
//    		
//    		User user =new User();
//    		
//    		user.setName("李四"+i);
//    		user.setPoint( Double.parseDouble(String.valueOf(i)) );
//    		user.setRegDate(new Date());
// 
//    		 _userDb.getUserSet().Add(user);
//			
//		}
//    	
//    	logger.info("结束");
    	
    	//System.out.println("查询："+userList.size());
    	
    	logger.info("---------->>>>测试测试");
    	
    	 
    	
    }
}
