package com.jfcore.frame;

import java.util.Date;
import java.util.UUID;

import com.jfcore.tools.IdGenerater;

 

public class CallerContext {
	
	 private  static ThreadLocal<String> CallerID = new ThreadLocal<String>();
	 
	 private  static ThreadLocal<Date> CallerTime = new ThreadLocal<Date>();
	 
	 

	public static String getCallerID() {
		
		String callerid = CallerID.get();
		if(callerid==null)
		{
			return "-";
		}
		
		return callerid;
		
	}
	
	public static Date getBeginTime()
	{		
		return CallerTime.get();
	}

	public static void setCallerID() {
		
		CallerID.set(IdGenerater.newId());
		CallerTime.set(new Date());
		
 
	}
	
	public static void setCallerID(String callId) {
		if(callId==null || callId.isEmpty())
		{
			setCallerID();
		}
		else
		{
			CallerID.set(callId);
		}
				
		CallerTime.set(new Date());
		
 
	}
	
	
	public static void dispose()
	{
		CallerID.remove();
		
		CallerTime.remove();
 
	}
	   

}
