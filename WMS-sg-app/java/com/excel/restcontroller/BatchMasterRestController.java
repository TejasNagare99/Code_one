package com.excel.restcontroller;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.excel.bean.BatchMasterBean;
import com.excel.model.Location;
import com.excel.model.SmpBatch;
import com.excel.model.SmpItem;
import com.excel.model.ViewSmpBatch;
import com.excel.service.BatchMasterService;
import com.excel.service.LocationService;
import com.excel.service.ParameterService;
import com.excel.service.ProductMasterService;
import com.excel.service.UserMasterService;
import com.excel.utility.CodifyErrors;
import com.excel.utility.MedicoConstants;

@RestController
@RequestMapping("/rest")
public class BatchMasterRestController implements MedicoConstants {
	@Autowired private UserMasterService userMasterService;
	@Autowired LocationService locationService;
	@Autowired ParameterService parameterService;
	@Autowired ProductMasterService productMasterService;
	@Autowired BatchMasterService batchMasterService;
	
	
	
	@GetMapping("/get-data-for-batch-entry")
	public Map<String, Object> getDataForBatchEntry(HttpServletRequest request ,
			HttpSession session) {
		Map<String, Object> map = new HashMap<>();
		try {
			
			String empId = this.userMasterService.getEmployeeIdFromRequest(request);
			
			Location location = this.locationService.getLocationNameByEmployeeId(empId);
			map.put("sendLocName", location.getLoc_nm());
			map.put("sendLocId", location.getLoc_id());
			map.put("sendSubCompId", location.getLoc_subcomp_id());
			//map.put("prodList", this.productMasterService.getAllActiveItemList());
			map.put("mfgLocations", this.parameterService.getParaDetailsByParaType("Manufacturing Location"));
			map.put("batchList", Arrays.asList("Dummy"));	
			map.put("purchase_rate_entry_indicator",this.parameterService.getParameterDataByDisplayNameWithJoin("ACCEPT_PUR_RATE_IN_GRN"));
		} catch (Exception e) {
			System.out.println("Error Occurred :" +e.getMessage());// uncomment asneeded --;
		}
		return map;
	}
	
	@GetMapping("/check-duplicate-batch")
	public Map<String, Object> checkDuplicateBatch(@RequestParam String locId,@RequestParam String prodId,@RequestParam String batchNo) {
		Map<String, Object> map = new HashMap<>();
		try {
			boolean flag=batchMasterService.checkDuplicateRecordStk(Long.valueOf(locId), batchNo, Long.valueOf(prodId));
			map.put("flag", flag == true ? 1 : 0);
		} catch (Exception e) {
			System.out.println("Error Occurred :" +e.getMessage());// uncomment asneeded --;
		}
		return map;
	}
	
	@PostMapping("/save-batch-entry")
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public Map<String, Object> saveBatch(@RequestBody BatchMasterBean bean, HttpSession session, HttpServletRequest request) {
		Map<String, Object> map = new HashMap<>();
		try {
			String empId = this.userMasterService.getEmployeeIdFromRequest(request);
			bean.setEmpId(empId);
			String companyCode = session.getServletContext().getAttribute(COMPANY_CODE).toString();
			bean.setIpAddress(request.getRemoteAddr());
			bean.setCompanyCode(companyCode);
			batchMasterService.saveBatch(bean);
			map.put("batchList", Arrays.asList("Dummy"));	
			map.put(RESPONSE_MESSAGE, "Record Added Successfully!");
			map.put(DATA_SAVED, true);
		} catch (Exception e) {
			e.printStackTrace();
			map.put(DATA_SAVED, false);
			map.put(RESPONSE_MESSAGE, "Error Occurred !! Error Code : "+new CodifyErrors().getErrorCode(e));
			System.out.println("Error Occurred :" +e.getMessage());// uncomment asneeded --;
		}
		return map;
	}
	
	@GetMapping("/search-batch")
	public Map<String, Object> searchBatchList(@RequestParam String search,@RequestParam String param,@RequestParam String text, @RequestParam int loc_id) {
		Map<String, Object> map = new HashMap<>();
		try {
			List<ViewSmpBatch> masters=this.batchMasterService.getBatchListByFilter(search, param, text, loc_id);
			map.put("prodList", masters);
		} catch (Exception e) {
			System.out.println("Error Occurred :" +e.getMessage());// uncomment asneeded --;
		}
		return map;
	}
	

	@GetMapping("/get-data-for-batch-modify")
	public Map<String, Object> getDataForBatchModify(HttpServletRequest request,@RequestParam String batchId,
			HttpSession session) {
		Map<String, Object> map = new HashMap<>();
		try {
			
			
			String empId = this.userMasterService.getEmployeeIdFromRequest(request);
			SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
			Location location = this.locationService.getLocationNameByEmployeeId(empId);
			map.put("sendLocName", location.getLoc_nm());
			map.put("sendLocId", location.getLoc_id());
			map.put("mfgLocations", this.parameterService.getParaDetailsByParaType("Manufacturing Location"));
			SmpBatch smpBatch=this.batchMasterService.getObjectById(Long.valueOf(batchId));
			map.put("batch", smpBatch);
			map.put("mfgdate", sdf.format(smpBatch.getBatch_mfg_dt()));
			map.put("expdate", sdf.format(smpBatch.getBatch_expiry_dt()));
			SmpItem smpItem=productMasterService.getObjectById(smpBatch.getBatch_prod_id());
			List<SmpItem> prodList=new ArrayList<SmpItem>();
			prodList.add(smpItem);
			map.put("prodList", prodList);
			map.put("purchase_rate_entry_indicator",this.parameterService.getParameterDataByDisplayNameWithJoin("ACCEPT_PUR_RATE_IN_GRN"));
			//map.put("prodList", this.productMasterService.getAllActiveItemList().stream().filter(x -> x.getSmp_prod_id().compareTo(smpBatch.getBatch_id()) != 0).collect(Collectors.toList()));
		} catch (Exception e) {
			System.out.println("Error Occurred :" +e.getMessage());// uncomment asneeded --;
		}
		return map;
	}

	@PostMapping("/save-batch-modify")
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public Map<String, Object> modifyBatch(@RequestBody BatchMasterBean bean, HttpSession session, HttpServletRequest request) {
		Map<String, Object> map = new HashMap<>();
		try {
			
			String empId = this.userMasterService.getEmployeeIdFromRequest(request);
			bean.setEmpId(empId);
			String companyCode = session.getServletContext().getAttribute(COMPANY_CODE).toString();
			bean.setIpAddress(request.getRemoteAddr());
			bean.setCompanyCode(companyCode);
			batchMasterService.modifyBatch(bean);
			map.put(RESPONSE_MESSAGE, "Record Modified Successfully!");
			map.put(DATA_SAVED, true);
		} catch (Exception e) {
			map.put(DATA_SAVED, false);
			map.put(RESPONSE_MESSAGE, "Error Occurred !! Error Code : "+new CodifyErrors().getErrorCode(e));
			System.out.println("Error Occurred :" +e.getMessage());// uncomment asneeded --;
		}
		return map;
	}
	
}
