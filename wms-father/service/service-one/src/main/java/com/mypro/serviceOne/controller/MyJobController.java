package com.mypro.serviceOne.controller;

import java.util.HashMap;
import java.util.List;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.jfcore.frame.Result;
import com.jfcore.job.JobContext;
import com.jms.facebean.service.serviceOne.IMyJob;

@RestController
@RequestMapping("IMyJob")
public class MyJobController implements IMyJob {

	
	@RequestMapping("/getIntervalSeconds")
	@Override
	public Integer getIntervalSeconds() {
 
		return 20;
	}

	@RequestMapping("/getStartAtSeconds")
	@Override
	public Integer getStartAtSeconds() {
 
		return 10;
	}

	@RequestMapping("/execute")
	@Override
	public Result execute(JobContext context) {
 
		System.out.println("IMyJob");
		
		
		return Result.succeed();
	}

	@RequestMapping("/getParList")
	@Override
	public List<HashMap<String, Object>> getParList() {
		return null;
	}

}
