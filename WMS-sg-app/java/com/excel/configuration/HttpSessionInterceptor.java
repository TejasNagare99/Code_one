package com.excel.configuration;

import java.text.SimpleDateFormat;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

@Component
public class HttpSessionInterceptor extends HandlerInterceptorAdapter {
	
	private static final Logger logger = LogManager.getLogger(HttpSessionInterceptor.class);
	private final SimpleDateFormat sdf = new SimpleDateFormat("yyyy-mm-dd HH:mm:ss");
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
		System.err.println("REQUEST = " + request.getRequestURI());
		
		StringBuffer sb = new StringBuffer();
		sb.append("IP =");
		sb.append(request.getRemoteAddr());
		sb.append(" TIME = ");
		try{
			sb.append(sdf.format(new Date()));
		}
		catch(Exception e) {}
		sb.append(" REQUEST = ");
		sb.append(request.getRequestURI());
		sb.append(" PARAMS ==> ");
		request.getParameterMap().forEach((key, value) -> {
			sb.append(" " + key + " : " + String.join(",", value));
        });
		
		logger.info(sb.toString());
		
		return true;
	}
	
	@Override
	public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
		//super.postHandle(request, response, handler, modelAndView);
	}

	@Override
	public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
		//super.afterCompletion(request, response, handler, ex);
	}

}
