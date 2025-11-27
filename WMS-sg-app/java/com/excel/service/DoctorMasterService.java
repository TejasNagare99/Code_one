package com.excel.service;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import com.excel.bean.BulkUpldBean;
import com.excel.bean.DoctorBean;
import com.excel.model.CustomerMaster;
import com.excel.model.DivMaster;
import com.excel.model.DoctorMaster;
import com.excel.model.EmployeeDetails;
import com.excel.model.FieldStaff;

public interface DoctorMasterService {

	public List<BulkUpldBean> getDoctorsForBulkUpload(String div_id,String fs_id,String compareString) throws Exception;
	public List<DoctorMaster> getDoctorsForFieldStaff(String emp_id) throws Exception;
	List<FieldStaff> getDoctorsForFieldStaffEntry(String divId);
	List<DivMaster> getStandardDivisionList() throws Exception;
	public DoctorMaster savDocDetail(DoctorBean dbBean)throws Exception;
	DoctorMaster getDoctorsDetails(Long docId) throws Exception;
	List<DoctorMaster> getAllActiveDoctorMasterDetail() throws Exception;
	public List<DoctorMaster> getDoctorTextParameterDetail(String name, String parameter, String text, String empId)throws Exception;
	DoctorMaster getDoctorDetailById(String doc_id)throws Exception;
	public void updateDoctorMaster(String doc_id, DoctorBean dbBean, String empId, HttpServletRequest request) throws Exception;
	
	FieldStaff getObjectfieldstaffbyId(Long fstaff_id) throws Exception;
	DivMaster  getObjectDivmasterbyId(Long div_id)throws Exception;
	public List<EmployeeDetails> getEmpIdByFStaffId(Long fstaff_id) throws Exception;
	EmployeeDetails getEmployeeDetails(String empId) throws Exception;
	DoctorMaster getDoctorsDetailsByRequestorId(Long docId, Long fs_id) throws Exception;
	public List<CustomerMaster> getDoctorsFromMdmBySpecialityAndClass(String doc_class, String doc_spec) throws Exception;
	public Object getProduct_details(String id);
}
