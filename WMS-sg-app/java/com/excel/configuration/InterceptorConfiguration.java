package com.excel.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class InterceptorConfiguration implements WebMvcConfigurer {
	
	@Bean
	public HttpSessionInterceptor httpSessionInterceptors () {
		return new HttpSessionInterceptor();
	}
	
	@Override
	public void addInterceptors (InterceptorRegistry registry) {
		registry.addInterceptor(httpSessionInterceptors());
	}

}
