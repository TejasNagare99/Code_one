package com.excel.configuration;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebMvcConfiguration implements WebMvcConfigurer {

	@Override
	public void addResourceHandlers(ResourceHandlerRegistry registry) {

		registry.addResourceHandler("/show-report/**").addResourceLocations("file:/D:/sg/reports/");
		registry.addResourceHandler("/show-pdf/**").addResourceLocations("file:/D:/sg/pdf/");
		registry.addResourceHandler("/show-grn/**").addResourceLocations("file:/D:/sg/grn/");
		registry.addResourceHandler("/show-allocation/**").addResourceLocations("file:/D:/sg/allocation/");
		registry.addResourceHandler("/show-stkwth/**").addResourceLocations("file:/D:/sg/stkwth/");
		registry.addResourceHandler("/show-iaa/**").addResourceLocations("file:/D:/sg/iaa/");
		registry.addResourceHandler("/show-crm/**").addResourceLocations("file:/D:/sg/crm/");
		registry.addResourceHandler("/show-article-docs/**").addResourceLocations("file:/D:/sg/files/");
		registry.addResourceHandler("/show-article-docs-zipped/**").addResourceLocations("file:/D:/sg/ZipFiles/");		
		
		
		
//		registry.addResourceHandler("/show-pdf/**").addResourceLocations("file:/E:/sg/pdf/");
//		registry.addResourceHandler("/show-grn/**").addResourceLocations("file:/E:/sg/grn/");
//		registry.addResourceHandler("/show-allocation/**").addResourceLocations("file:/E:/sg/allocation/");
//		registry.addResourceHandler("/show-stkwth/**").addResourceLocations("file:/E:/sg/stkwth/");
//		registry.addResourceHandler("/show-iaa/**").addResourceLocations("file:/E:/sg/iaa/");
//		registry.addResourceHandler("/show-crm/**").addResourceLocations("file:/E:/sg/crm/");
//		registry.addResourceHandler("/show-article-docs/**").addResourceLocations("file:/E:/sg/files/");
//		registry.addResourceHandler("/show-article-docs-zipped/**").addResourceLocations("file:/E:/sg/ZipFiles/");
	}

	
	
}
