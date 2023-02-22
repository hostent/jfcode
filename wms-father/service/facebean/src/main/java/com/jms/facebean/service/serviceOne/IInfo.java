package com.jms.facebean.service.serviceOne;

import com.jfcore.frame.PageData;

public interface IInfo {
	
	String getInfo(String list, Integer time, Double dd);
	
	

	InfoEnt getEntInfo(InfoEnt infoEnt1);
	
	PageData searchMyData();

 
	
	public class InfoEnt
	{
		private String list;
		
		public String getList() {
			return list;
		}

		public void setList(String list) {
			this.list = list;
		}

		public Integer getTime() {
			return time;
		}

		public void setTime(Integer time) {
			this.time = time;
		}

		private Integer time;
	}




	
}


