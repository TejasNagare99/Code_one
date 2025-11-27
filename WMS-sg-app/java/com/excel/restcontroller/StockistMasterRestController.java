package com.excel.restcontroller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.excel.bean.StockistMasterBean;
import com.excel.exception.MedicoException;
import com.excel.model.CustomerMaster;
import com.excel.model.Location;
import com.excel.model.Transporter_master;
import com.excel.service.LocationService;
import com.excel.service.LrEntryService;
import com.excel.service.ParameterService;
import com.excel.service.StockistMasterService;
import com.excel.utility.MedicoConstants;

@RestController
@RequestMapping("/rest")
public class StockistMasterRestController implements MedicoConstants{
	
	@Autowired ParameterService parameterService;
	@Autowired private StockistMasterService stockistmasterservice;
	@Autowired private LocationService locationService;
	@Autowired LrEntryService lrEntryService;
	

	@PostMapping("/save-stockist-master")
	public Map<String, Object> saveStockistMaster(@RequestBody StockistMasterBean bean, HttpSession session) {
		Map<String, Object> map = new HashMap<>();
		List<CustomerMaster> cust_name=null;
		try {
			String companyCode = session.getServletContext().getAttribute(COMPANY_CODE).toString();
			bean.setCompanyCode(companyCode);
			stockistmasterservice.save(bean);
			cust_name = stockistmasterservice.getCustnamesToCheck();
			if(bean.getMode().equalsIgnoreCase(MedicoConstants.ENTRY_MODE)) {
				map.put(RESPONSE_MESSAGE, "Stockist Master Saved Successfully!");
			} else {
				map.put(RESPONSE_MESSAGE, "Stockist Master updated successfully!");
			}
			map.put(DATA_SAVED, true);
			map.put("cust_name", cust_name);
		} catch (MedicoException e) {
			e.printStackTrace();
			map.put(LOAD_ERROR_MESSAGE, e.getMessage());
			System.out.println("e.getMessage()"+e.getMessage());
		} catch (Exception e) {
			e.printStackTrace();
			map.put(DATA_SAVED, false);
			map.put(RESPONSE_MESSAGE, RESPONSE_ERROR_MESSAGE);
		}
		return map;
	}
	
	@GetMapping("/get-data-for-stockist-master-entry")
	public Map<String, Object> getDataForStockistMaster(HttpSession session) {
		Map<String, Object> map = new HashMap<>();
		List<Location> locList = null;
		List<Transporter_master> transporter=null;
		List<CustomerMaster> cust_name=null;
		try {
			transporter =lrEntryService.gettransportermaster();
			locList = locationService.getAllLocations();
			cust_name = stockistmasterservice.getCustnamesToCheck();
			
			map.put("stateList", this.parameterService.getParaDetailsByParaType("State"));
			map.put("locationList", locList);
			map.put("transporter", transporter);
			map.put("cust_name", cust_name);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return map;
	}
	
	@GetMapping("/get-stockist-master-detail-by-cust-id")
	public Map<String, Object> getStockistMasterByCustId(@RequestParam Long custId, HttpSession session) {
		Map<String, Object> map = new HashMap<>();
		try {
//			String companyCode = session.getServletContext().getAttribute(COMPANY_CODE).toString();
			map.put("stockistMaster", stockistmasterservice.getById(custId));
		} catch (Exception e) {
			e.printStackTrace();
		}
		return map;
	}
	
	@GetMapping("/get-custName-for-stockist_master")
	public Map<String, Object> getCustNameForStockistMaster(HttpSession session) {
		Map<String, Object> map = new HashMap<>();
		try {
			String companyCode = session.getServletContext().getAttribute(COMPANY_CODE).toString();
			List<CustomerMaster> custNames = stockistmasterservice.getcustNameListByCmpCd(companyCode);
			map.put("custNames", custNames);
		}catch (Exception e) {
			e.printStackTrace();
		}
		return map;
		
	}

}
