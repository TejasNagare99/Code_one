package com.excel.repository;

import java.util.List;

import com.excel.bean.AuditTrailBean;
import com.excel.model.Audit_log;
import com.excel.model.BatchMasterAuditTrailModel;
import com.excel.model.DispatchAuditTrailBean;
import com.excel.model.FieldMasterAttrib;
import com.excel.model.FinYear;
import com.excel.model.GrnAuditTrailBean;
import com.excel.model.HqMasterAuditTrailModel;
import com.excel.model.ItemMasterAuditTrail;
import com.excel.model.SupplierMasterModel;
import com.excel.model.ViewDownloadIaaAuditTrail;

public interface AuditTrailRepository {
	
	List<FieldMasterAttrib> getFieldMasterAuditTraildata(AuditTrailBean bean)throws Exception;
	List<ItemMasterAuditTrail> getProductMasterAudit_Trail_Report_Data(AuditTrailBean bean)throws Exception;
	List<FinYear> getConcatedFinYearListForAuditReport();
	List<SupplierMasterModel> getSupplierMaster_Reportdata(AuditTrailBean bean)throws Exception;
	//Hq Master Audit Trail
	List<HqMasterAuditTrailModel> getHqmasteraudittraildata(AuditTrailBean bean)throws Exception;
	List<BatchMasterAuditTrailModel> getBatchMasterData(AuditTrailBean bean)throws Exception;
	List<GrnAuditTrailBean> getGrnAuditDownloadData(AuditTrailBean bean) throws Exception;
	List<DispatchAuditTrailBean> getDispatchAuditDownloadData(AuditTrailBean bean) throws Exception;
	List<ViewDownloadIaaAuditTrail> getViewIAAVoucherPrintingdata(AuditTrailBean bean) throws Exception;
	
	List<GrnAuditTrailBean> getGrnAuditDownloadDataforPrevious(AuditTrailBean bean) throws Exception;
	List<DispatchAuditTrailBean> getDispatchAuditDownloadDataforPrevious(AuditTrailBean bean) throws Exception;
	
	List<Audit_log> getAuditLogDataFromProcedure(String startDate,String endDate,String finYear,String finYearFlag);
	List<FieldMasterAttrib> getFieldMasterResignedAuditTraildata(AuditTrailBean bean)throws Exception;

}
