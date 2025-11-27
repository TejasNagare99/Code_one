package com.excel.service;

import java.util.List;

import javax.servlet.http.HttpSession;


import com.excel.bean.CrmSchemeMasterBean;
import com.excel.model.CrmSchemeMaster;
import com.excel.model.SG_d_parameters_details;


public interface CrmSchemeMasterService {
	
	public CrmSchemeMaster SaveCRMMaster(CrmSchemeMasterBean bean, HttpSession session) throws Exception;
	public List<SG_d_parameters_details> getDocClass()throws Exception;
	public List<SG_d_parameters_details> getSchemeType()throws Exception;
	public List<CrmSchemeMaster> getScheme_Code()throws Exception;

}
