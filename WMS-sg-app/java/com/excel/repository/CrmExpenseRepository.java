package com.excel.repository;

import java.util.Date;
import java.util.List;

import com.excel.bean.CrmTdsBean;
import com.excel.model.Crm_GenDtl;
import com.excel.model.Crm_GenHd;
import com.excel.model.Period;
import com.excel.model.Period_Tds;
import com.excel.model.Period_;
import com.excel.model.Sfa_Field_Master;
import com.excel.model.Sfa_Geographical_Master;
import com.excel.model.Sfa_Retail_Consumer;
import com.excel.model.TdsApplicableProduct;
import com.excel.model.TdsApplicableStatementReport;
import com.excel.model.Tds_Crm_Expense;
import com.excel.model.Tds_Crm_Upload;
import com.excel.model.Tds_crm_Images;

public interface CrmExpenseRepository {

	
	List<Sfa_Geographical_Master> getHQ();
	List<Sfa_Field_Master> getSe(Long loc_id);
	List<Sfa_Retail_Consumer> getcustomerAddRetailer(Long se,String custtype);
	Sfa_Field_Master getFsById(Long Id);
	void SaveTds_crm_master_annual_(Tds_Crm_Expense tdscrm,String Tablename,Period_Tds pr) throws Exception;
	
	Object findObjByCustType(String tablename,String custId,String custType)throws Exception; 
	void UpdateAnnualTdsCrm(String tablename,String custId,String custType,Object[]obj,Tds_Crm_Expense tdscrm,Period_Tds pr)throws Exception;
	public List<Tds_Crm_Expense> getviewByEmpId(String empId)throws Exception;
	public Tds_Crm_Expense getObjById(Long Id)throws Exception;
	public String UpdateCRM(CrmTdsBean bean) throws Exception;
	public List<Tds_crm_Images> geImageBycrmId(Long crmId) throws Exception;
	void deleteImagesByCrmId(Long crmId)throws Exception;
	List<TdsApplicableStatementReport> getTdsSummarryData(String sub_comp,String custType,Character tdsReq);
	List<TdsApplicableProduct> getTdsDetailData(String sub_comp,String hcp_cust_id,String finyear);
	List<Period_Tds> getPeriod(String period_fin_year);
	
	public List<Sfa_Retail_Consumer> getBulkAddress(String se,String custType);
	public List<Sfa_Retail_Consumer> getDataForCrmTemplate(String custids,String se,String custType)throws Exception;

	public Long getHqId(String hqname)throws Exception;
	public Long getFsId(String fsname)throws Exception;
	public Long getcustIdforHcpType(String hcpType,String hcpno,Long fsid)throws Exception;
	
	public Crm_GenHd getCrmGenhd(String filename)throws Exception;
	public List<Crm_GenDtl> getCrmGenDt(Long crmhdid)throws Exception;
	public Crm_GenDtl getCrmGenDtl(Long crmhdid,String custtype,Long hq,Long fsid,Long divid,Long custid)throws Exception;

	public List<Crm_GenHd> getCrmHdrByUserId(String userId) throws Exception;
	public List<Tds_Crm_Expense> getTdsExpsByCrmGenId(Long crmGenId) throws Exception;
	public Tds_Crm_Expense getTdsCrmExpenseGenDtlByDtlAndHdrId(Long crGenHdrId,Long crmGenDtlId) throws Exception;

	public String getMaxCrmSerialNumber(String checker)throws Exception;
	public List<Tds_Crm_Expense> getTdsCrmExpenseByGenHdrId(Long crGenHdrId) throws Exception;
	
	public void truncateCrmTemplate()throws Exception;
	public void setArchiveTable()throws Exception;
	public boolean checkuploadedstatus()throws Exception;
	
	public Long checkIfCustomerExists(String custCode) throws Exception;
	public Long checkIfDoctorExists(String custCode) throws Exception;
	public Long checkIfRetailerExists(String custCode) throws Exception;
	public void callcrmExpenseProcedure(String custtype,String subcomp,String finyear,Date crmdate,String userid)throws Exception;
	
	public void deleteTdsCrmExpenseOnFileName(String filename) throws Exception;
	
	public List<Tds_Crm_Upload> checkForUnuploadedRecords(String filename)throws Exception;
	
	public boolean checkfilename(String filename)throws Exception;
}
