package com.excel.sheduler;

import java.text.SimpleDateFormat;
import java.time.LocalTime;
import java.util.Date;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.excel.model.Company;
import com.excel.model.Erp_status_log;
import com.excel.model.Period;
import com.excel.repository.CompanyMasterRepository;
import com.excel.repository.ErpRepository;
import com.excel.repository.TransactionalRepository;
import com.excel.service.ErpService;
import com.excel.service.GoApptiveService;
import com.excel.service.PeriodMasterService;
import com.excel.service.UserMasterService;
import com.excel.utility.CodifyErrors;
import com.excel.utility.MedicoConstants;
import com.excel.utility.MedicoResources;

@Component
public class Scheduler implements MedicoConstants{
   @Autowired ErpService erpService;
   @Autowired PeriodMasterService periodMasterService;
   @Autowired TransactionalRepository transactionalRepository;
   @Autowired ErpRepository erpRepository;
   @Autowired CompanyMasterRepository companyMasterRepository;
   @Autowired UserMasterService usermasterservice;
   
   @Scheduled(fixedRate =600000)
   public void fixedRateSch() {
	   try {

		   this.usermasterservice.getSendEmailNotification();

	   }
	  catch(Exception e) {
		  System.out.println("Error Occurred :" +e.getMessage());// uncomment asneeded --;
	  }
      
   }


}
