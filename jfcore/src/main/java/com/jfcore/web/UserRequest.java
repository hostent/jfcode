package com.jfcore.web;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.Collections;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ReadListener;
import javax.servlet.ServletInputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.alibaba.fastjson.JSONObject;

 
 

public class UserRequest extends HttpServletRequestWrapper {
	
 
	private static Logger logger = LoggerFactory.getLogger(UserRequest.class);
	
	HttpServletRequest httpServletRequest;

	public UserRequest(HttpServletRequest request) {
		super(request);
		
		
		httpServletRequest = request;
		
		
		//初始化
		
		
		String json = this.getBodyString(request);
		body = json.getBytes();
		
		logger.debug("调用地址:"+request.getRequestURI());
		logger.debug("调用参数:"+json);
		
		
		requestData = JSONObject.parseObject(json, RequestData.class);
		
		
	}
	

	 
	
	@Override
	public String getParameter(String name) {
		
		if(requestData.getPars().containsKey(name))
		{
			return  requestData.getPars().get(name).toString();
		}
		return null;
		
	}
	
	
 
	@Override
	public Map<String, String[]> getParameterMap() {
		
		Map<String, String[]> map = new HashMap<String, String[]>();
		
		for (String key : requestData.getPars().keySet()) {
			//map.put(key, new String[] { JSONObject.toJSONString(requestData.getPars().get(key))  });
			map.put(key, new String[] { requestData.getPars().get(key).toString() });
		}
		 
		return super.getParameterMap();
	}
	
	@Override
	public Enumeration<String> getParameterNames() {
		 		
		return Collections.enumeration(requestData.getPars().keySet());
	}
	
	@Override
	public String[] getParameterValues(String name) {
		
		String[] result = null;

        Object v = requestData.getPars().get(name);
        if (v == null) {
            result = null;
        } else if (v instanceof String[]) {
            result = (String[]) v;
        } else if (v instanceof String) {
            result = new String[] { (String) v };
        } else {
            result = new String[] { v.toString() };
        }

        return result;
		
	
		 
	}
	
	@Override
	public String getHeader(String name) {

		return super.getHeader(name);
	}
	
	@Override
	public Enumeration<String> getHeaderNames() {

		return super.getHeaderNames();
	}
	
	@Override
	public Enumeration<String> getHeaders(String name) {

		return super.getHeaders(name);
	}
	
	private byte[] body;
	
	
	public RequestData requestData;


    @Override
    public ServletInputStream getInputStream() throws IOException {
    	 
    	final ByteArrayInputStream bais = new ByteArrayInputStream(body);

        return new ServletInputStream() {
            @Override
            public boolean isFinished() {
                return false;
            }

            @Override
            public boolean isReady() {
                return false;
            }

            @Override
            public void setReadListener(ReadListener readListener) {

            }

            @Override
            public int read() throws IOException {
                return bais.read();
            }
        };

    }
	
    private String getBodyString(HttpServletRequest request)  {
		int totalbytes = request.getContentLength();
		if (totalbytes <= 0) {
			return "";
		}
				
	    BufferedReader br = null;
        StringBuilder sb = new StringBuilder("");
        try
        {
            br = request.getReader();
            String str;
            while ((str = br.readLine()) != null)
            {
                sb.append(str);
            }
            br.close();
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
        finally
        {
            if (null != br)
            {
                try
                {
                    br.close();
                }
                catch (IOException e)
                {
                    e.printStackTrace();
                }
            }
        }
        return sb.toString();
       
    }

}
