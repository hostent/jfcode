package com.jms.job;

import com.jfcore.job.JobHelp;
import com.jms.facebean.service.serviceOne.IMyJob;

public class JobMain {
	
	
	public static void init(){
		
		JobHelp.newMyJobSaas("job测试", IMyJob.class);
		
	}

}
