package com.excel.repository;

import java.util.List;

import com.excel.bean.BulkUpldBean;
import com.excel.model.CustomerMaster;
import com.excel.model.DivMaster;
import com.excel.model.DoctorMaster;
import com.excel.model.EmployeeDetails;
import com.excel.model.FieldStaff;
import com.excel.model.Hcp_prod_details;

public interface DoctorRepository {

	public List<BulkUpldBean> getDoctorsForBulkUpload(String div_id, String fs_id, String compareString)
			throws Exception;
	
	List<DoctorMaster> getDoctorsForFieldStaff(String empId) throws Exception;
	
	List<FieldStaff> getDoctorsForFieldStaffEntry(String divId);

	DoctorMaster getDoctorsDetails(Long docId) throws Exception;

	List<DivMaster> getStandardDivisionList() throws Exception;
	

	List<DoctorMaster> getAllActiveDoctorMasterDetail() throws Exception;

	List<DoctorMaster> getDoctorTextParameterDetail(String name, String parameter, String text,String empId)throws Exception;

	DoctorMaster getDoctorDetailById(String doc_id) throws Exception;

	DoctorMaster getByObjectId(Long valueOf) throws Exception;

	List<EmployeeDetails> getEmpIdByFStaffId(Long fstaff_id) throws Exception;
	
	EmployeeDetails getEmployeeDetails(String empId) throws Exception;

	DoctorMaster getDoctorsDetailsByRequestorId(Long docId, Long requestor_id) throws Exception;

	DoctorMaster getDoctorsByMclNumber(Long mcl_no) throws Exception;
	
	List<CustomerMaster> getDoctorsFromMdmBySpecialityAndClass(String doc_class , String doc_spec) throws Exception;

	public  List<Hcp_prod_details> getProduct_details(String id);
}
