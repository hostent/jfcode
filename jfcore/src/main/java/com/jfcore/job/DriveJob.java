package com.jfcore.job;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.quartz.DisallowConcurrentExecution;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.quartz.SchedulerException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
 
import com.jfcore.frame.Result;
import com.jfcore.web.ServiceListener;

 

@DisallowConcurrentExecution
public class DriveJob implements Job {
	
	
	private static Logger logger = LoggerFactory.getLogger(DriveJob.class);

	public void start(String desc, Class<?> jobInterface) throws SchedulerException {

		if (IIntervalFaceJob.class.isAssignableFrom(jobInterface)) {

			IIntervalFaceJob faceJob =(IIntervalFaceJob) ServiceListener.getSingletonBean(jobInterface);

			int intervalSeconds = faceJob.getIntervalSeconds();
			int startAtSeconds = faceJob.getStartAtSeconds();

			List<HashMap<String, Object>> listArgs = faceJob.getParList();

			if (listArgs == null || listArgs.size() == 0) {
				Map<String, Object> args = new HashMap<String, Object>();
				args.put("args", null);
				args.put("ClassLoader", jobInterface);
				args.put("intervalSeconds",intervalSeconds);
				args.put("startAtSeconds",startAtSeconds);
				
				JobHelp.newRunningJob(desc, intervalSeconds, startAtSeconds, DriveJob.class, args);

			} else {
				for (Map<String, Object> map : listArgs) {

					Map<String, Object> args = new HashMap<String, Object>();
					args.put("args", map);
					args.put("ClassLoader", jobInterface);
					args.put("intervalSeconds",intervalSeconds);
					args.put("startAtSeconds",startAtSeconds);

					JobHelp.newRunningJob(desc, intervalSeconds, startAtSeconds, DriveJob.class, args);

				}
			}

		} else if (ICronFaceJob.class.isAssignableFrom(jobInterface)) {

			ICronFaceJob faceJob = (ICronFaceJob) ServiceListener.getSingletonBean(jobInterface);
			String cron = faceJob.getCron();

			List<HashMap<String, Object>> listArgs = faceJob.getParList();

			if (listArgs == null || listArgs.size() == 0) {
				Map<String, Object> args = new HashMap<String, Object>();
 
				args.put("args", null);
				args.put("ClassLoader", jobInterface);
				args.put("cron",cron);

				JobHelp.newRunningJob(desc, cron, DriveJob.class, args);
			} else {
				for (Map<String, Object> map : listArgs) {
					Map<String, Object> args = new HashMap<String, Object>();
 
					args.put("args", map);
					args.put("ClassLoader", jobInterface);
					args.put("cron",cron);
					
					JobHelp.newRunningJob(desc, cron, DriveJob.class, args);

				}
			}

		}

	}

	@Override
	public void execute(JobExecutionContext context) throws JobExecutionException {

		String desc = context.getJobDetail().getDescription();

		Class<?> classLoader = (Class<?>) context.getJobDetail().getJobDataMap().get("ClassLoader");
		
		IJobEvent jobEvent = ServiceListener.getSingletonBean(IJobEvent.class);

		@SuppressWarnings("unchecked")
		Map<String, Object> args = (Map<String, Object>) context.getJobDetail().getJobDataMap().get("args");
		Date from = new Date();
		try {

			IFaceJob faceJob = (IFaceJob) ServiceListener.getSingletonBean(classLoader); 

			JobContext jobContext = new JobContext();

			jobContext.setArgs(args);

			String argsStr = "";
			if (args != null) {
				argsStr = args.toString();
			}
			
			logger.info(String.format("执行Job("+desc+") 开始：%s,参数：%s", classLoader.getName(), argsStr) );
			
			Date start = new Date();

			Result jobResult = faceJob.execute(jobContext);
			
			Date end = new Date();

			if (jobResult == null) {
				logger.info( String.format("执行Job("+desc+") 错误：%s,参数：%s",
						classLoader.getName(), argsStr));

				if (jobEvent != null) {
					jobEvent.onExeced(context.getJobDetail().getKey().getName(), from, end, -1,
							context.getTrigger().getNextFireTime());
				}
			} else {
				if (jobEvent != null) {
					jobEvent.onExeced(context.getJobDetail().getKey().getName(), from,end,
							jobResult.getStatus(), context.getTrigger().getNextFireTime());
				}

			}

			logger.info( String.format("执行Job("+desc+") 耗时："+ (end.getTime()-start.getTime())  +" ms,参数："+argsStr+"，结果：%s,%s"
					, jobResult.getStatus(), jobResult.getMessage()));

		} catch (Exception e) {
			
			logger.error(e.getMessage(), e);
		 

			if (jobEvent != null) {
				jobEvent.onExeced(context.getJobDetail().getKey().getName(), from, new Date(), -1,
						context.getTrigger().getNextFireTime());
			}

		}
		
		
		try {
			

			//check
			if(IIntervalFaceJob.class.isAssignableFrom(classLoader))
			{
				IIntervalFaceJob faceJob =(IIntervalFaceJob) ServiceListener.getSingletonBean(classLoader);

				int intervalSeconds = faceJob.getIntervalSeconds();
				int startAtSeconds = faceJob.getStartAtSeconds();
				boolean check =context.getJobDetail().getJobDataMap().get("intervalSeconds").equals(intervalSeconds) &&
						context.getJobDetail().getJobDataMap().get("startAtSeconds").equals(startAtSeconds)	;
				
				if(!check) 
				{
					logger.info("重启job,"+context.getJobDetail().getKey());
					String desc1 =context.getJobDetail().getDescription();
					JobHelp.deleteMyjob(context.getJobDetail().getKey());
		
					JobHelp.newMyJobSaas(desc1, faceJob.getClass());
				}
				
			}
			else if(ICronFaceJob.class.isAssignableFrom(classLoader))
			{
				ICronFaceJob faceJob = (ICronFaceJob) ServiceListener.getSingletonBean(classLoader);
				String cron = faceJob.getCron();
				
				boolean check =context.getJobDetail().getJobDataMap().get("cron").equals(cron) 	;
				
				if(!check) 
				{
					logger.info("重启job"+context.getJobDetail().getKey());
					String desc1 =context.getJobDetail().getDescription();
					JobHelp.deleteMyjob(context.getJobDetail().getKey());
		
					JobHelp.newMyJobSaas(desc1, faceJob.getClass());
					
					
				}
				
				
			}
			
			
		} catch (Exception e) {
			 logger.error("重启Job失败", e);
		}
		
 

	}

}
