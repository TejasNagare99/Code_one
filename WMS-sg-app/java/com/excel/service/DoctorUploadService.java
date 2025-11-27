package com.excel.service;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.excel.bean.AllocationUploadBean;
import com.excel.bean.DoctorUploadXlsxBean;
import com.excel.bean.RuleBasedAllocationBean;
import com.excel.model.DoctorMaster;
import com.excel.model.Pg_Doc_Alloc_Template;
import com.excel.model.Pg_Doc_Alloc_Template_Error;

public interface DoctorUploadService {

	Map<String, Object> saveuploadalDoctorAllocation(AllocationUploadBean bean)throws Exception;

	void createAllocation(AllocationUploadBean bean, String alloc_uniq_no) throws Exception;

	Map<String, Object> saveuploadalDoctorAllocationXlsx(AllocationUploadBean bean)throws Exception;

	void deleteDoctorUploadByUniqueNumber(Long alloc_uniq_no) throws Exception;

	List<RuleBasedAllocationBean> approvalListForSelfAppr(String empId, String user_role, String locId);

	Map<String, Object> getProductWiseStock(long division, long period, String up_status, String ucycle,Long alloctempHdId) throws Exception;

	void updateApprovalStatus(Long alloc_uniq_no, String status) throws Exception;

	List<Pg_Doc_Alloc_Template> getListofPgDocAllocTemp(String allocuniquenum)throws Exception;
	
	List<Pg_Doc_Alloc_Template_Error> getListofPgDocAllocTempErrorList(String allocuniquenum)throws Exception;
	
	String generateDoctorUploadReport(List<Pg_Doc_Alloc_Template> list,List<Pg_Doc_Alloc_Template_Error> Errorlist, String companyname) throws Exception;

	void writeErrorLog(DoctorUploadXlsxBean bean,String msg) throws Exception;

	void updateAddress(DoctorMaster doctor) throws Exception;

	List<RuleBasedAllocationBean> approvalDetails(Long period, Long unique_number) throws Exception;
	
	Map<String,Object> validateDoctorallocation(AllocationUploadBean Bean)throws SQLException,Exception;

	void updateDocFsIdsforDoctorUpload(Long divId,String uniqueno)throws Exception;
}
