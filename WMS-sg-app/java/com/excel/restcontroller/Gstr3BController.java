package com.excel.restcontroller;

import java.io.File;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.excel.bean.ReportBean;
import com.excel.model.Company;
import com.excel.model.GST3B_5;
import com.excel.model.GST3B_Eligible_Itc;
import com.excel.model.GST3B_Supplies_Liable_Bean;
import com.excel.model.GST3B_Supplies_Liable_Bean_2;
import com.excel.model.Location;
import com.excel.model.Period;
import com.excel.service.Gst_3B_Service;
import com.excel.service.LocationService;
import com.excel.service.PeriodMasterService;
import com.excel.utility.CodifyErrors;
import com.excel.utility.MedicoConstants;
import com.excel.utility.MedicoResources;

@RestController
@RequestMapping("/rest")
public class Gstr3BController implements MedicoConstants{
	@Autowired Gst_3B_Service gst_3b_service;
	@Autowired PeriodMasterService periodMasterService;
	@Autowired LocationService locationService;
	private final SimpleDateFormat sdf_yyyy_mm_dd = new SimpleDateFormat("yyyy-MM-dd");
	

	
	@PostMapping("/get-gst3B-report-data")
	public Map<String, Object> getGst3BReport(@RequestBody ReportBean bean,HttpSession session,HttpServletRequest request) {
		Map<String, Object> map = new HashMap<>();
		String userId=bean.getEmp_id();// get it from session
		bean.setUserId(userId);
		String companyname = ((Company) session.getServletContext().getAttribute(COMPANY_DATA)).getCompany_name();
		String compnayCode =  (String) session.getServletContext().getAttribute(COMPANY_CODE);
		String realPath = MedicoConstants.PDF_FILE_PATH+"files//";
		try {
			
			String finyear=bean.getFinancialYear();
			String periodCode=bean.getForPeriod();
			Long loc_id=Long.valueOf(bean.getLocation());
			
			System.out.println("flag " + bean.getFinYear());
			System.out.println("finyear " + finyear);
			System.out.println("compnayCode " + compnayCode);
			System.out.println("Location " + bean.getLocation());	
			System.out.println("periodCode " + periodCode);
			
			Period period = this.periodMasterService.getPeriodData(finyear,periodCode);
			String start_date = MedicoResources.getRptDateFormat_(period.getPeriod_start_date());
			String end_date = MedicoResources.getRptDateFormat_(period.getPeriod_end_date());
			System.out.println("period start date " + start_date);
			System.out.println("period end date " + end_date);
			
			List<GST3B_Supplies_Liable_Bean> sup_liab = gst_3b_service
					.getDataForSuppliesLiable(bean.getFinYear(), finyear, compnayCode, start_date, end_date, bean.getLocation());
			
			List<GST3B_Supplies_Liable_Bean_2> sup_liab_table2 = gst_3b_service
					.getDataForSuppliesLiableTable2(bean.getFinYear(), finyear, compnayCode, start_date, end_date, bean.getLocation());
			List<GST3B_Eligible_Itc> eligible_itc_list = gst_3b_service
					.getDataForEligibleItc(bean.getFinYear(), finyear, compnayCode, start_date, end_date, bean.getLocation());
			List<GST3B_5> exempt_inward = gst_3b_service.getDataForExemptInwardSupplies(bean.getFinYear(), finyear, compnayCode, start_date, end_date, bean.getLocation());
			
			Location location = locationService.getObjectById(loc_id);
			System.out.println("Reg no " + location.getGst_reg_no());
			
			System.out.println("list size 1 : " + sup_liab.size());
			System.out.println("list size 2 : " + sup_liab_table2.size());
			System.out.println("list size 3 : " + eligible_itc_list.size());
			System.out.println("list size 4 : " + exempt_inward.size());
			
			//pdf download
			File docfile = gst_3b_service.generatePdfForDownload(sup_liab, sup_liab_table2, eligible_itc_list,exempt_inward,
					periodCode, location.getGst_reg_no(), period.getPeriod_name(), realPath, finyear,location.getLoc_nm());
			
			//json download
			String path = gst_3b_service.generateJsonForDownload(sup_liab, sup_liab_table2, eligible_itc_list,exempt_inward,
					periodCode, location.getGst_reg_no(), period.getPeriod_name(), realPath, finyear);
			
			List<GST3B_Eligible_Itc> eligible_alist = null;
			List<GST3B_Eligible_Itc> eligible_alist_B = null;
			List<GST3B_Eligible_Itc> eligible_alist_D1 = null;
			
			if(eligible_itc_list!= null &&  eligible_itc_list.size()>0) {
			    eligible_alist = 
						eligible_itc_list.stream().filter(val -> val.getInd().contains("A") ).collect(Collectors.toList());
			    eligible_alist_B = 
						eligible_itc_list.stream().filter(val -> val.getInd().contains("B") ).collect(Collectors.toList());
			    eligible_alist_D1 = 
						eligible_itc_list.stream().filter(val -> val.getInd().contains("D") ).collect(Collectors.toList());
			}
			
			// For A Indicator Calculation
			
			
			BigDecimal intTax = BigDecimal.ZERO;
			BigDecimal centralTax = BigDecimal.ZERO;
			BigDecimal stateTax = BigDecimal.ZERO;
			BigDecimal cess = BigDecimal.ZERO;
			for (int i = 0; i < eligible_alist.size(); i++) 
			{
				intTax = intTax.add(new BigDecimal(eligible_alist.get(i).getIgst().toString()));
				centralTax = centralTax.add(new BigDecimal(eligible_alist.get(i).getCgst().toString()));
				stateTax = stateTax.add(new BigDecimal(eligible_alist.get(i).getSgst().toString()));
				cess = cess.add(new BigDecimal(eligible_alist.get(i).getCess().toString()));
			}
			
			GST3B_Eligible_Itc eligible_alist1 = new  GST3B_Eligible_Itc();
			eligible_alist1.setIgst(intTax);
			eligible_alist1.setCgst(centralTax);
			eligible_alist1.setSgst(stateTax);
			eligible_alist1.setCess(cess);
			eligible_alist1.setInd("T");
			eligible_alist1.setDetails("(A) ITC Available (Whether in full or part)");
			eligible_alist.add(eligible_alist1);
			
			System.out.println("intTax " + intTax);
			System.out.println("centralTax " + centralTax);
			System.out.println("stateTax " + stateTax);
			System.out.println("cess " + cess);
			
			//For B indicator Calculation
			
			BigDecimal intTax1 = BigDecimal.ZERO;
			BigDecimal centralTax1 = BigDecimal.ZERO;
			BigDecimal stateTax1 = BigDecimal.ZERO;
			BigDecimal cess1 = BigDecimal.ZERO;
			
			for (int i = 0; i < eligible_alist_B.size(); i++) 
			{
				intTax1 = intTax1.add(new BigDecimal(eligible_alist_B.get(i).getIgst().toString()));
				centralTax1 = centralTax1.add(new BigDecimal(eligible_alist_B.get(i).getCgst().toString()));
				stateTax1 = stateTax1.add(new BigDecimal(eligible_alist_B.get(i).getSgst().toString()));
				cess1 = cess1.add(new BigDecimal(eligible_alist_B.get(i).getCess().toString()));
			}
			
			GST3B_Eligible_Itc eligible_alist_B1 = new  GST3B_Eligible_Itc();
			eligible_alist_B1.setIgst(intTax1);
			eligible_alist_B1.setCgst(centralTax1);
			eligible_alist_B1.setSgst(stateTax1);
			eligible_alist_B1.setInd("T");
			eligible_alist_B1.setCess(cess1);
			eligible_alist_B1.setDetails(" (B) ITC Reversed");
			eligible_alist_B.add(eligible_alist_B1);
			
			
			//For C Calculation
			
			BigDecimal intTax2=intTax.subtract(intTax1);
			BigDecimal centralTax2=centralTax.subtract(centralTax1);
			BigDecimal stateTax2=stateTax.subtract(stateTax1);
			BigDecimal cess2=cess.subtract(cess1);
			List<GST3B_Eligible_Itc> eligible_alist_CList = new ArrayList<>();
			
			GST3B_Eligible_Itc eligible_alist_C = new  GST3B_Eligible_Itc();
			eligible_alist_C.setIgst(intTax2);
			eligible_alist_C.setCgst(centralTax2);
			eligible_alist_C.setSgst(stateTax2);
			eligible_alist_C.setInd("T");
			eligible_alist_C.setCess(cess2);
			eligible_alist_C.setDetails(" (C) Net ITC Available (A)-(B)");
			
			
			GST3B_Eligible_Itc eligible_alist_D = new  GST3B_Eligible_Itc();
			eligible_alist_D.setIgst(BigDecimal.ZERO);
			eligible_alist_D.setCgst(BigDecimal.ZERO);
			eligible_alist_D.setSgst(BigDecimal.ZERO);
			eligible_alist_D.setCess(BigDecimal.ZERO);
			eligible_alist_D.setInd("N");
			eligible_alist_D.setDetails("(D) Other Details");	
			
			eligible_alist_CList.add(eligible_alist_C);
			eligible_alist_CList.add(eligible_alist_D);
			eligible_alist_CList.addAll(eligible_alist_D1);
			
			eligible_itc_list=new ArrayList<>();
			
			eligible_itc_list.addAll(eligible_alist);
			eligible_itc_list.addAll(eligible_alist_B);
			eligible_itc_list.addAll(eligible_alist_CList);
			

			map.put("sup_liab", sup_liab);
			map.put("sup_liab_table2", sup_liab_table2);
			map.put("eligible_itc_list", eligible_itc_list);
			map.put("exempt_inward", exempt_inward);
			map.put("filename", docfile.getName());
			map.put("jsonResponse",path);

			
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("Error Occurred :" +e.getMessage());// uncomment asneeded --;
		}
		return map;
	}

}
