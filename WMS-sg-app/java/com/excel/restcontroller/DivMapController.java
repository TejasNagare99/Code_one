package com.excel.restcontroller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.excel.bean.DivMapBean;
import com.excel.model.DivMaster;
import com.excel.service.DivMapService;
import com.excel.service.DivMasterService;
import com.excel.utility.MedicoConstants;


@RestController
@RequestMapping("/rest")
public class DivMapController implements MedicoConstants {
	
	@Autowired private DivMasterService divMasterService;
	
	@Autowired private DivMapService divMapService;
	
	@GetMapping("/get-onload-data-div-map")
	public Map<String,Object> getOnloadData(){
		
		List<DivMaster> divList = null;
		
		Map<String,Object> map = new HashMap<String,Object>();
		try {
			divList = this.divMasterService.getEntireDivMasterList();
			System.out.println("divList : "+divList.size());
			map.put("divList", divList);
		}
		catch(Exception e) {
			e.printStackTrace();
		}
		return map;
	}
	
	
	
	@GetMapping("/get-product-division")
	public Map<String,Object> getProductDivison(@RequestParam Long fsDivId){
		
		
		List<Long> prodDivListByFsDiv = null;
		
		System.out.println("fsDivId : "+fsDivId);
		
		Map<String,Object> map = new HashMap<String,Object>();
		try {
			prodDivListByFsDiv = this.divMapService.getDivMapByFsDivision(fsDivId);
			System.out.println("prodDivListByFsDiv : "+prodDivListByFsDiv.size());
			map.put("prodDivListByFsDiv", prodDivListByFsDiv);
			
		}
		catch(Exception e) {
			e.printStackTrace();
		}
		return map;
	}
	
	
	@PostMapping("/save-division-mapping")
	public Map<String, Object> saveSalesOrderProducts(@RequestBody DivMapBean bean, HttpServletRequest request) {
		Map<String, Object> map = new HashMap<>();

		System.out.println("fsDivId : "+bean.getFieldDivision());
		System.out.println("prodDivId : "+bean.getActiveDiv().size());
		System.out.println("remoteAddr : "+request.getRemoteAddr());

		map.put(DATA_SAVED, true);
		try {
			
			this.divMapService.saveDivisionMapping(bean, request);

			map.put(RESPONSE_MESSAGE, "Division Mapped successfully!");
		} catch (Exception e) {
			e.printStackTrace();
			map.put(DATA_SAVED, false);
			map.put(RESPONSE_MESSAGE, RESPONSE_ERROR_MESSAGE);
		}

		return map;
	}
	
}
