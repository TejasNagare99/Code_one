package com.excel.restcontroller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.excel.bean.ReportBean;
import com.excel.model.Company;
import com.excel.model.GSTR_1_B2BA_INV;
import com.excel.model.GSTR_1_B2B_INV;
import com.excel.model.Location;
import com.excel.model.ViewGSTR_1_B2CLA_INV;
import com.excel.model.ViewGSTR_1_B2CL_INV;
import com.excel.model.ViewGSTR_1_B2CSA_INV;
import com.excel.model.ViewGSTR_1_B2CS_INV;
import com.excel.model.ViewGSTR_1_HSN_INV;
import com.excel.model.ViewGSTR_1_TRANSFER;
import com.excel.service.GstR1DownloadsService;
import com.excel.service.LocationService;
import com.excel.utility.CodifyErrors;
import com.excel.utility.MedicoConstants;
import com.excel.utility.MedicoResources;

@RestController
@RequestMapping("/rest")
public class GstReportRestController implements MedicoConstants {

	@Autowired
	private GstR1DownloadsService gstR1DownloadsService;
	
	@Autowired
	private LocationService locationService;

	@PostMapping("/get-gst-r1-report")
	public Map<String, Object> getGstr1Report(@RequestBody ReportBean bean, HttpSession session) throws NumberFormatException, Exception {
		
		
		String companyCode = session.getServletContext().getAttribute(COMPANY_CODE).toString();
		
		Location locObj = locationService.getObjectById(Long.valueOf(bean.getSendLocId()));
		
		bean.setLoc_name(locObj.getLoc_nm());
		
		bean.setCompcd(companyCode);
		
		System.out.println("FIN_YEAR_FLAG : "+ bean.getFinyear());
		System.out.println("FINID : "+ bean.getFinyearflag());     
		System.out.println("COMP_CD : "+ bean.getCompcd());        
		System.out.println("ST_DT : "+ MedicoResources.convertUtilDateToString(bean.getStartDate()));       
		System.out.println("EN_DT : "+ MedicoResources.convertUtilDateToString(bean.getEndDate()));         
		System.out.println("LOCID : "+ bean.getSendLocId());
		
		
		Map<String, Object> map = new HashMap<>();
		String realPath = MedicoConstants.REPORT_FILE_PATH;
		String companyname = ((Company) session.getServletContext().getAttribute(COMPANY_DATA)).getCompany_name();

		List<GSTR_1_B2BA_INV> gstr_1_b2ba_invList = null;
		List<GSTR_1_B2B_INV> gstr_1_b2b_invList = null;
		List<ViewGSTR_1_B2CS_INV> b2cs_inv = null;
		List<ViewGSTR_1_B2CL_INV> b2cl_inv = null;
		List<ViewGSTR_1_HSN_INV> hsn_inv = null;
		List<ViewGSTR_1_B2CLA_INV> b2cla_inv = null;
		List<ViewGSTR_1_TRANSFER> transfer = null;
		List<ViewGSTR_1_B2CSA_INV> gstr_1_b2csa_inv = null;

		String filename = "";
		try {

			gstr_1_b2ba_invList = gstR1DownloadsService.getB2BAINVReportData(bean);
			gstr_1_b2b_invList = gstR1DownloadsService.getB2BReportData(bean);
			gstr_1_b2csa_inv = gstR1DownloadsService.getGSTR_1_B2CSA_INVData(bean);
			b2cs_inv = gstR1DownloadsService.getB2CSReportData(bean);
			b2cl_inv = gstR1DownloadsService.getB2CLReportData(bean);
			hsn_inv = gstR1DownloadsService.getHSNReportData(bean);
			b2cla_inv = gstR1DownloadsService.getB2CLAReportData(bean);
			transfer = gstR1DownloadsService.getGstR1TransferData(bean);

			if(gstr_1_b2ba_invList != null && !gstr_1_b2ba_invList.isEmpty()) {
				filename = gstR1DownloadsService.getGstR1Reports(bean, realPath, companyname, b2cs_inv, b2cl_inv, hsn_inv,
						b2cla_inv, transfer, gstr_1_b2ba_invList, gstr_1_b2b_invList, gstr_1_b2csa_inv);
				map.put("filename", SPRING_RESOURCE_HANDLER_REPORT_FILE_PATH_PREFIX + "/" + filename);
				
				map.put("gstr_1_b2ba_invList", gstr_1_b2ba_invList);
				map.put("gstr_1_b2b_invList", gstr_1_b2b_invList);
				map.put("gstr_1_b2csa_inv", gstr_1_b2csa_inv);
				map.put("b2cs_inv", b2cs_inv);
				map.put("b2cl_inv", b2cl_inv);
				map.put("hsn_inv", hsn_inv);
				map.put("b2cla_inv", b2cla_inv);
				map.put("transfer", transfer);
			}
			
			
		} catch (Exception e) {
			System.out.println("Error Occurred :" +e.getMessage());// uncomment asneeded --;
		}
		return map;
	}
}
