package com.excel.repository;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.excel.bean.RuleBasedAllocationBean;
import com.excel.model.Pg_Doc_Alloc_Template;
import com.excel.model.Pg_Doc_Alloc_Template_Error;


public interface DoctorUploadRepository {

	void generateAllocationPg(String comp_cd, String finyr, String period_cd, String alloc_uniq_no, String userid,
			String userip) throws Exception;

	Long getAllocationCycleNumberPg(String period_code, String fin_year) throws Exception;

	void deleteDoctorUploadByUniqueNumber(Long alloc_uniq_no) throws Exception;

	List<RuleBasedAllocationBean> approvalLisForSelfAppr(String empId, String user_role, String locId);

	Map<String, Object> getProductWiseStock(long division, long period, String up_status, String ucycle,
			Long alloctempHdId) throws Exception;

	void updateApprovalStatus(Long alloc_uniq_no, String status) throws Exception;

	Pg_Doc_Alloc_Template getObjectById(Long slno) throws Exception;

	void updateAllocDetailApprovalStatus(Long alloc_uniq_no, String status) throws Exception;

	List<Pg_Doc_Alloc_Template> getListofPgDocAllocTemp(String allocuniquenum)throws Exception;
	List<Pg_Doc_Alloc_Template_Error> getListofPgDocAllocTempErrorList(String allocuniquenum)throws Exception;

	List<RuleBasedAllocationBean> approvalDetails(Long period, Long unique_number) throws Exception;
	
	void updateDocFsIdsforDoctorUpload(Long divId,String uniqueno)throws Exception;
}
