package com.excel.service;

import java.util.List;
import java.util.Map;

import org.springframework.web.multipart.MultipartFile;

import com.excel.bean.CRMExpenseBean;
import com.excel.model.CRMReqRequestsImage;
import com.excel.model.CRM_Req_Request;
import com.excel.model.CrmSchemeMaster;


public interface CrmSchemeService {
	public List<CrmSchemeMaster> getCRMSchemeDetails(String companyCode, String crm_sch_type, String curBiz, String expBiz,Long locId,
			String doccls,String docspl)
			throws Exception;
	public Map<String, Object> uploadCrmEntryImage(List<MultipartFile> files);
	public CRM_Req_Request save(CRMExpenseBean bean) throws Exception;
	public List<CRM_Req_Request> getviewlist(String userid) throws Exception;
	public Map<String, Object> crmConfirmation(List<Long> crm_req_id, String finYearId, String user_name,
			String companycode) throws Exception;
	public CRM_Req_Request getmodifyDetailsByid(String crm_req_id) throws Exception;
	public List<CRMReqRequestsImage> geImageBycrmId(Long crmId) throws Exception;
}
