package com.jms.job;

import java.util.Date;

import com.jfcore.job.IJobEvent;

public class JobEvent implements IJobEvent {

	@Override
	public void onInit() {
 
		
	}

	@Override
	public void onStart(String jobName, String args, String cron, Integer intervalSeconds, String jobDesc,
			String jobInterface, Date nextRunTime) {
 
		
	}

	@Override
	public void onExeced(String jobName, Date lastRunTimeFrom, Date lastRunTimeTo, Integer lastSucceed,
			Date nextRunTime) {
 
		
	}

}
