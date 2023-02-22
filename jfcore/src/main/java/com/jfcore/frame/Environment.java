package com.jfcore.frame;

import java.io.IOException;
import java.lang.management.ManagementFactory;

import com.jfcore.tools.AesHelp;
import com.jfcore.tools.PropertiesHelp;

import io.netty.util.internal.MacAddressUtil;

public class Environment {
	

	
	private static Boolean _isDev=null;
	
	private static Boolean _isWindow=null;
	
   	
	public static Boolean isDev()
	{
		if(_isDev==null)
		{
			try {
								 
				if(PropertiesHelp.getApplicationConf("_isDev")!=null && !PropertiesHelp.getApplicationConf("_isDev").isEmpty())
				{
					_isDev=Boolean.parseBoolean(PropertiesHelp.getApplicationConf("_isDev"));
				}
				else
				{
					String osName = System.getProperties().getProperty("os.name");
					if(osName.toLowerCase().startsWith("windows"))
					{
						_isDev= true;
					}
					else
					{
						_isDev= false;
					}
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return _isDev;
	}
	
	
	
	public static Boolean isWindow()
	{
		if(_isWindow==null)
		{
			String osName = System.getProperties().getProperty("os.name");
			if(osName.toLowerCase().startsWith("windows"))
			{
				_isWindow= true;
			}
			else
			{
				_isWindow= false;
			}
		}
		return _isWindow;
	}
	
	 static String addZeroForNum(String str,int strLength) {  
		  int strLen =str.length();  
		  if (strLen <strLength) {  
		   while (strLen< strLength) {  
		    StringBuffer sb = new StringBuffer();  
		    sb.append("0").append(str);//左补0  
//		    sb.append(str).append("0");//右补0  
		    str= sb.toString();  
		    strLen= str.length();  
		   }  
		  }  

		  return str;  
		 }  
	
	public static String getCurrentGroupId()
	{
		String machineKey =addZeroForNum(AesHelp.parseByte2HexStr(MacAddressUtil.defaultMachineId()),16) ;

		String name = ManagementFactory.getRuntimeMXBean().getName();

		int pid = Integer.valueOf(name.split("@")[0]) ;
		
		String pidKey =addZeroForNum(AesHelp.intToHex(pid),8);
		
		return (machineKey+"-"+pidKey).toLowerCase();
	}

}
