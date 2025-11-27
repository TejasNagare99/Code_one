package com.excel.service;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import com.excel.bean.TransporterBean;
import com.excel.model.Transporter_master;

public interface TransporterMasterService {
	
	public String saveTransDetail(TransporterBean transBean, String empId, HttpServletRequest request, String companyCode) throws Exception;
	
	List<Transporter_master> gettransportermaster_new() throws Exception;
	
	List<Transporter_master> gettransportermasterForLocation(Long loc_id) throws Exception;

}
