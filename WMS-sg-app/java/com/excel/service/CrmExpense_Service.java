package com.excel.service;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.springframework.web.multipart.MultipartFile;

import com.excel.bean.CrmTdsBean;
import com.excel.model.Crm_GenHd;
import com.excel.model.Period_Tds;
import com.excel.model.SG_d_parameters_details;
import com.excel.model.Sfa_Field_Master;
import com.excel.model.Sfa_Geographical_Master;
import com.excel.model.Sfa_Retail_Consumer;
import com.excel.model.TdsApplicableProduct;
import com.excel.model.TdsApplicableStatementReport;
import com.excel.model.Tds_Crm_Expense;
import com.excel.model.Tds_crm_Images;

public interface CrmExpense_Service {
	
	List<Sfa_Geographical_Master>  gethq();
	List<Sfa_Field_Master> getse(Long geog_lvl1_hq);
	List<Sfa_Retail_Consumer> getAddress(Long se,String custType);
	
	public String SaveCRMEntry(CrmTdsBean bean)throws Exception;
	
	public List<Tds_Crm_Expense> getviewByEmpId(String empId)throws Exception;
	
	public String UpdateCRM(CrmTdsBean bean)throws Exception;
	
	public String confirmCrm(String ids)throws Exception;
	public Map<String, Object> uploadCrmEntryImage(List<MultipartFile> file)throws Exception;
	
	public List<Tds_crm_Images> geImageBycrmId(Long crmId)throws Exception;
	
	List<TdsApplicableStatementReport> getTdsSummaryReport(String sub_comp,String custType,Character tdsReq,String Pan_req);
	List<TdsApplicableProduct> getTdsDetail(String hcp_cust_id,String emp_id,HttpSession session) throws Exception;
	
	public List<SG_d_parameters_details> getTdsCrmPercentageParameter()throws Exception;
	public List<Sfa_Field_Master> getBulkse(String geog_lvl1_hq);
	public List<Sfa_Retail_Consumer> getBulkAddress(String se,String custType);
	
	public List<Sfa_Retail_Consumer> getDataForCrmTemplate(String custids,String se,String custType)throws Exception;
	
	public String GenerateCrmTemplateUpload(List<Sfa_Retail_Consumer> list,String custtype)throws Exception;
	
	public void writeCrmDownloadFileLog(List<Sfa_Retail_Consumer>list,String custtype,String empid,String filename)throws Exception;
	
	public String uploadCrmFile(MultipartFile file,String finyr,String company,String username)throws Exception;

	public List<Crm_GenHd> getCrmHdrByUserId(String userId) throws Exception;
	public List<Tds_Crm_Expense> getTdsExpsByCrmGenId(Long crmGenId) throws Exception;
	public void confirmTdsCrmExpensesByGenHdrAndDtl(Long crmGenId , List<Long> genDtlIdsList) throws Exception;
	
	//CrmDirect Upload
	public Map<String,Object> validateAndSetBeanForSave(MultipartFile file,String finyr,String company,String username)throws Exception;
	
	public Map<String,Object> SaveDirectUpload(List<CrmTdsBean> list,String filename)throws Exception;
}
