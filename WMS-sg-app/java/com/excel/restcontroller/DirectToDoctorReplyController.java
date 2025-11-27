package com.excel.restcontroller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.excel.service.DirectToDoctorService;
import com.excel.utility.CodifyErrors;

@RestController
@RequestMapping("/rest")
public class DirectToDoctorReplyController {
	
	@Autowired DirectToDoctorService directtodoctorservice;
	
	@GetMapping("/doctor-reply-service")
	public String getDoctorReply(String action,String dspId) {
		String msg = null;
		try {
			msg = directtodoctorservice.saveDoctorResponse(Long.valueOf(dspId), action);
		}
		catch (Exception e) {
			// TODO: handle exception
			msg = "Something Went Wrong Please Try Again After Some Time!!";
			System.out.println("Error Occurred :" +e.getMessage());// uncomment asneeded --;
		}
		return msg;
	}
	
	@GetMapping("/stockist-reply-service")
	public String getStockistReply(String action,String dspId) {
		String msg = null;
		try {
			msg = directtodoctorservice.saveStockistResponse(Long.valueOf(dspId), action);
		}
		catch (Exception e) {
			// TODO: handle exception
			msg = "Something Went Wrong Please Try Again After Some Time!!";
			System.out.println("Error Occurred :" +e.getMessage());// uncomment asneeded --;
		}
		return msg;
	}
}
