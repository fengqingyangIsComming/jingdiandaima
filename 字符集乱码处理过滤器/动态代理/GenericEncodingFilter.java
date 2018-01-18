package com.itheima.proxy.filter;

import java.io.IOException;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;


@WebFilter("/*")
public class GenericEncodingFilter implements Filter {

  

	
	public void destroy() {
		
	}

	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
		final HttpServletRequest req = (HttpServletRequest) request;
		HttpServletRequest myReq = (HttpServletRequest) Proxy.newProxyInstance(req.getClass().getClassLoader(), req.getClass().getInterfaces(), new InvocationHandler() {
			
			@Override
			public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
				if("getParameter".equals(method.getName())){
					String type = req.getMethod();
					if("get".equalsIgnoreCase(type)){
						String value = (String) method.invoke(req, args);
						value = new String(value.getBytes("ISO-8859-1"),"UTF-8");
						return value;
					}else if("post".equalsIgnoreCase(type)){
						req.setCharacterEncoding("UTF-8");
					}
				}
				return method.invoke(req, args);
			}
		});
		chain.doFilter(myReq, response);
	}

	
	public void init(FilterConfig fConfig) throws ServletException {
		
	}

}
