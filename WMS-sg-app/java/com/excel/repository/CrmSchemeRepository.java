package com.excel.repository;

import java.util.List;

import com.excel.model.CRMReqRequestsImage;
import com.excel.model.CRM_Req_Request;
import com.excel.model.CrmSchemeMaster;

public interface CrmSchemeRepository {

	public List<CrmSchemeMaster> getCRMSchemeDetails(String companyCode, String crm_sch_type, String curBiz, String expBiz,Long locId,
			String doccls,String docspl)
			throws Exception;
	public String getCRMReqno() throws Exception;
	public List<CRM_Req_Request> getCRMDetailsByids(List<Long> crm_req_id) throws Exception;
	public List<CRM_Req_Request> getCRMDetailsBySingleid(String crm_req_id) throws Exception;
	public List<CRMReqRequestsImage> geImageBycrmId(Long crmId) throws Exception;
	public List<CRM_Req_Request> getviewlist(String userid) throws Exception;

}
