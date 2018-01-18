package com.itheima.filter;

import java.io.UnsupportedEncodingException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;


public class MyHttpServletRequestWrapper extends HttpServletRequestWrapper{

	private HttpServletRequest request;

	public MyHttpServletRequestWrapper(HttpServletRequest request) {
		super(request);
		this.request = request;
	}

	@Override
	public String getParameter(String name) {
		String method = request.getMethod();
		if("get".equalsIgnoreCase(method)){
			String value = null;
			try {
				value = new String(request.getParameter(name).getBytes("ISO-8859-1"),"UTF-8");
			} catch (UnsupportedEncodingException e) {
				e.printStackTrace();
			} 
			return myFilter(value);//基情
		}else if("post".equalsIgnoreCase(method)){
			try {
				request.setCharacterEncoding("UTF-8");
			} catch (UnsupportedEncodingException e) {
				e.printStackTrace();
			}
		}
		return super.getParameter(name);
	}
	
	private String myFilter(String info){
		String goodInfo="";
		String[] badInfo={"基情","sb"};
		for (String inf : badInfo) {
			if(info.contains(inf)){
				goodInfo=info.replaceAll(inf, "***");
			}
		}
		return goodInfo;
	}

}

