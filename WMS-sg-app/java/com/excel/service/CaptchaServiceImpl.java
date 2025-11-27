package com.excel.service;

import java.io.ByteArrayOutputStream;
import java.io.FileOutputStream;
import java.util.Base64;

import javax.imageio.ImageIO;

import org.springframework.stereotype.Service;

import cn.apiclub.captcha.Captcha;
import cn.apiclub.captcha.backgrounds.GradiatedBackgroundProducer;

@Service
public class CaptchaServiceImpl implements CaptchaService {

	@Override
	public Captcha createCapctcha(int width,int height) throws Exception {
		Captcha cap = null;
		try{
			cap = new Captcha.Builder(width,height)
					.addBackground(new GradiatedBackgroundProducer())
					.addText()
					.addNoise()
					.build();
		}
		catch(Exception e){
			throw e;
		}
		return cap;
	}

	@Override
	public void createImage(Captcha captcha) throws Exception {
		FileOutputStream fos = null;
		try{
			ByteArrayOutputStream os =  new ByteArrayOutputStream();
			ImageIO.write(captcha.getImage(),"png",os);
			fos = new FileOutputStream("E:/captcha/mycp.png");
			fos.write(os.toByteArray());
		}
		catch(Exception e){
			throw e;
		}
		finally{
			if(fos!=null){
				fos.flush();
				fos.close();
			}
		}
	}

	@Override
	public String encodeCaptchaBase64(Captcha captcha) throws Exception {
		String imageData = null;
		try{
			ByteArrayOutputStream os =  new ByteArrayOutputStream();
			ImageIO.write(captcha.getImage(),"png",os);
			byte[] arr = Base64.getEncoder().encode(os.toByteArray());
			imageData = new String(arr);
		}
		catch(Exception e){
			throw e;
		}
		return imageData;
	}

}
