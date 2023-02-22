package com.mypro.serviceOne.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.jfcore.frame.PageData;
import com.jfcore.frame.PagePars;
import com.jfcore.frame.Result;
import com.jfcore.tools.IdGenerater;
import com.jms.facebean.service.serviceOne.IInfo;
import com.mypro.serviceOne.dataAccess._userDb;
import com.mypro.serviceOne.service.UserService;

 
 

@RestController
@RequestMapping("IInfo")
public class MyInfoController implements IInfo {
	

	@Value("${server.port}")
    private String serverPort;
	
	
	
	@Autowired
	UserService userService;
	
	
	@Autowired
    HttpServletRequest httpServletRequest;
	
	
	
	@RequestMapping("/getInfo")
	@Override
    public String getInfo(String list, Integer time, Double dd){
		
		System.out.println(list);
		System.out.println(time);
		System.out.println(dd);
		
        return "调用端口 " + serverPort + " 成功！";
        
        
    }
	
	@Override
	@RequestMapping("/searchMyData")
	public PageData searchMyData()
	{
		PagePars pagePars = new PagePars(httpServletRequest);
		
		
		PageData pd = new PageData();
		
		pd.setRows(_userDb.getUserMapper().getUserPage(pagePars.getPars(), pagePars.getFrom(), pagePars.getPageSize()));
		pd.setTotal(_userDb.getUserMapper().getUserCount(pagePars.getPars()));
		
		return pd;
	}
	
	
	@RequestMapping("/getEntInfo")
	@Override
    public InfoEnt getEntInfo(@RequestBody InfoEnt infoEnt1){
		

		infoEnt1.setList("调用端口 " + serverPort + " 成功！");
		infoEnt1.setTime(5656);
 
		
		
        return infoEnt1;
        
        
    }

}
