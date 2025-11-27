package com.excel.service;

import cn.apiclub.captcha.Captcha;

public interface CaptchaService {
	
	Captcha createCapctcha(int width,int height) throws Exception;
	
	void createImage(Captcha captcha) throws Exception;
	
	String encodeCaptchaBase64(Captcha captcha) throws Exception;
}
