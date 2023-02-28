package com.jfcore.job;

import static org.quartz.JobBuilder.newJob;
import static org.quartz.SimpleScheduleBuilder.simpleSchedule;
import static org.quartz.TriggerBuilder.newTrigger;

import java.util.Date;
import java.util.Map;
import java.util.Set;

import org.quartz.CronScheduleBuilder;
import org.quartz.DateBuilder;
import org.quartz.Job;
import org.quartz.JobDetail;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.quartz.JobKey;
import org.quartz.JobListener;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.Trigger;
import org.quartz.DateBuilder.IntervalUnit;
import org.quartz.impl.StdSchedulerFactory;
import org.quartz.impl.matchers.GroupMatcher;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.alibaba.fastjson.JSONObject;
import com.jfcore.frame.CallerContext;
import com.jfcore.tools.IdGenerater;
import com.jfcore.web.ServiceListener;

 

 

public class JobHelp {
	
	private static Logger logger = LoggerFactory.getLogger(JobHelp.class);

	public static void main(String[] args) throws SchedulerException, InterruptedException {

	 

	}

	public static void newMyJobSaas(String JobDescription,Class<? extends IFaceJob> jobClass)   {

		try {
			new DriveJob().start(JobDescription, jobClass);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
 
	}

 
	public static Date newRunningJob(String JobDescription,int intervalSeconds, int startAtSeconds, Class<? extends Job> jobClass,
			Map<String, Object> par) throws SchedulerException {
		
		
		String className =jobClass.getName();
		if(par.containsKey("ClassLoader"))
		{
			Class<?> classLoader =(Class<?>) par.get("ClassLoader");
			
			className = classLoader.getName();
		}	

		String jobName = className;
		if (par != null) {
			jobName = jobName + "-" + par.hashCode();
		}

		logger.info("Job start(newRunningJob):[" + jobName + "]");

		JobDetail job = newJob(jobClass).withDescription(JobDescription).withIdentity(jobName, "group_" + jobName).build();
		if(par!=null)
		{
			job.getJobDataMap().putAll(par); 
		}
		
		Trigger trigger = newTrigger().withIdentity("trigger_" + jobName, "group_" + jobName)
				.startAt(new Date(new Date().getTime() + startAtSeconds * 1000))
				.withSchedule(simpleSchedule().withIntervalInSeconds(intervalSeconds).repeatForever()).build();
		
		
		
		IJobEvent jobEvent = ServiceListener.getSingletonBean(IJobEvent.class);   
		if(jobEvent!=null)
		{			
			jobEvent.onStart(jobName, JSONObject.toJSONString(par) , null, intervalSeconds, JobDescription, className, trigger.getNextFireTime());
		}	

		Scheduler scheduler = StdSchedulerFactory.getDefaultScheduler();

		scheduler.scheduleJob(job, trigger);

		scheduler.getListenerManager().addJobListener(new JobListener() {

			@Override
			public void jobWasExecuted(JobExecutionContext context, JobExecutionException jobException) {
				if (jobException != null) {
					
					logger.error("error:"+context.getJobDetail().getJobClass().getName(), jobException);
					 
				}

				CallerContext.dispose();
			}

			@Override
			public void jobToBeExecuted(JobExecutionContext context) {
				CallerContext.setCallerID("job:" + IdGenerater.newId());				 
			}

			@Override
			public void jobExecutionVetoed(JobExecutionContext context) {

			}

			@Override
			public String getName() {
				return "JobListener";
			}
		});

		if (!scheduler.isShutdown()) {
			scheduler.start();
		}

		return trigger.getNextFireTime();
		
	}
  

	public static Date newRunningJob(String JobDescription,String cron, Class<? extends Job> jobClass, Map<String, Object> par) throws SchedulerException {

		String className =jobClass.getName();
		if(par.containsKey("ClassLoader"))
		{
			Class<?> classLoader =(Class<?>) par.get("ClassLoader");
			
			className = classLoader.getName();
		}	

		String jobName = className  ;
		if (par != null) {
			jobName = jobName + "-" + par.hashCode();
		}

		JobDetail job = newJob(jobClass).withDescription(JobDescription).withIdentity(jobName, "group_" + jobName).build();

		 
		if(par!=null)
		{
			job.getJobDataMap().putAll(par); 
		}
 
		
		Trigger trigger = newTrigger().withIdentity("trigger_" + jobName, "group_" + jobName)
				// .startAt(new Date(new Date().getTime() + startAtSeconds * 1000))
				.startAt(DateBuilder.futureDate(1, IntervalUnit.SECOND))
				.withSchedule(CronScheduleBuilder.cronSchedule(cron)).startNow().build();

		IJobEvent jobEvent = ServiceListener.getSingletonBean(IJobEvent.class);  
		if(jobEvent!=null)
		{			
			jobEvent.onStart(jobName,JSONObject.toJSONString(par) , cron, null, JobDescription, className, trigger.getNextFireTime());
		}	
		
		Scheduler scheduler = StdSchedulerFactory.getDefaultScheduler();

		scheduler.scheduleJob(job, trigger);

		scheduler.getListenerManager().addJobListener(new JobListener() {

			@Override
			public void jobWasExecuted(JobExecutionContext context, JobExecutionException jobException) {
				if (jobException != null) {
					logger.error("error:"+context.getJobDetail().getJobClass().getName(), jobException);
				}

				CallerContext.dispose();
			}

			@Override
			public void jobToBeExecuted(JobExecutionContext context) {
				CallerContext.setCallerID("job:" + IdGenerater.newId());
			}

			@Override
			public void jobExecutionVetoed(JobExecutionContext context) {

			}

			@Override
			public String getName() {
				return "JobListener";
			}
		});

		if (!scheduler.isShutdown()) {
			scheduler.start();
		}

		return trigger.getNextFireTime();
	}

	public static void deleteMyjob(Class<? extends Job> jobClass) throws SchedulerException {

		Scheduler scheduler = StdSchedulerFactory.getDefaultScheduler();

		JobKey deleteJobKey = null;

		Set<JobKey> keys = scheduler.getJobKeys(GroupMatcher.anyGroup());

		for (JobKey jobKey : keys) {

			if (jobKey.getName().contains(jobClass.getName())) {
				deleteJobKey = jobKey;
			}
		}

		if (deleteJobKey != null) {
			deleteMyjob(deleteJobKey);
		}

	}

	public static void deleteMyjob(JobKey jobKey) throws SchedulerException {

		Scheduler scheduler = StdSchedulerFactory.getDefaultScheduler();

		scheduler.deleteJob(jobKey);

		logger.info("Job deleteJob:[" + jobKey.getName() + "]");
 
	}

}
