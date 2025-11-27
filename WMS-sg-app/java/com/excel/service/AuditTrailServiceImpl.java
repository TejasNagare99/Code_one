package com.excel.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
import com.excel.repository.AuditTrailRepository;

@Service
public class AuditTrailServiceImpl implements AuditTrailService{

	@Autowired AuditTrailRepository auditTrailRepository;
	
	@Override
	public List<FieldMasterAttrib> getFieldMasterAuditTraildata(AuditTrailBean bean) throws Exception {
		// TODO Auto-generated method stub
		return auditTrailRepository.getFieldMasterAuditTraildata(bean);
	}
	@Override
	public List<ItemMasterAuditTrail> getProductMasterAudit_Trail_Report_Data(AuditTrailBean bean) throws Exception {
		// TODO Auto-generated method stub
		return auditTrailRepository.getProductMasterAudit_Trail_Report_Data(bean);
	}
	@Override
	public List<FinYear> getConcatedFinYearListForAuditReport(){
		return auditTrailRepository.getConcatedFinYearListForAuditReport();
	}
	@Override
	public List<SupplierMasterModel> getSupplierMaster_Reportdata(AuditTrailBean bean) throws Exception {
		// TODO Auto-generated method stub
		return auditTrailRepository.getSupplierMaster_Reportdata(bean);
	}
	@Override
	public List<HqMasterAuditTrailModel> getHqmasteraudittraildata(AuditTrailBean bean) throws Exception {
		return auditTrailRepository.getHqmasteraudittraildata(bean);
	}
	@Override
	public List<BatchMasterAuditTrailModel> getBatchMasterData(AuditTrailBean bean) throws Exception {
		// TODO Auto-generated method stub
		return auditTrailRepository.getBatchMasterData(bean);
	}
	@Override
	public List<GrnAuditTrailBean> getgrnAuditDownloadService(AuditTrailBean bean) throws Exception {
		return auditTrailRepository.getGrnAuditDownloadData(bean);
	}
	@Override
	public List<DispatchAuditTrailBean> getDispatchAuditDownloadService(AuditTrailBean bean) throws Exception {
		return auditTrailRepository.getDispatchAuditDownloadData(bean);
	}
	@Override
	public List<ViewDownloadIaaAuditTrail> getViewDownloadIaaAuditTrialData(AuditTrailBean bean) throws Exception {
		return auditTrailRepository.getViewIAAVoucherPrintingdata(bean);
	}
	
	@Override
	public List<GrnAuditTrailBean> getgrnAuditDownloadServicePrevious(AuditTrailBean bean) throws Exception {
		// TODO Auto-generated method stub
		return auditTrailRepository.getGrnAuditDownloadDataforPrevious(bean);
	}
	@Override
	public List<DispatchAuditTrailBean> getDispatchAuditDownloadServicePrevious(AuditTrailBean bean) throws Exception {
		// TODO Auto-generated method stub
		return auditTrailRepository.getDispatchAuditDownloadDataforPrevious(bean);
	}
	@Override
	public List<Audit_log> getAuditLogData(String startDate, String endDate, String finYear, String finYearFlag) {
		// TODO Auto-generated method stub
		return auditTrailRepository.getAuditLogDataFromProcedure( startDate, endDate, finYear, finYearFlag);
	}
	
	@Override
	public List<FieldMasterAttrib> getFieldMasterResignedAuditTraildata(AuditTrailBean bean) throws Exception {
		// TODO Auto-generated method stub
		return auditTrailRepository.getFieldMasterResignedAuditTraildata(bean);
	}
}
