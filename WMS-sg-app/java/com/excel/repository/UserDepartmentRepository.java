package com.excel.repository;

import java.util.Date;
import java.util.List;

import com.excel.bean.Parameter;
import com.excel.model.SamplProdGroup;
import com.excel.model.UserDepartment;

public interface UserDepartmentRepository {

	List<UserDepartment> getDepartments() throws Exception;

	List<Parameter> getActiveUserList() throws Exception;

	List<Parameter> getActiveDesignation() throws Exception;

	Integer getMaxUserKey() throws Exception;

	List<Parameter> getActiveUserDtl() throws Exception;

	void insertEmpLocAccess(String emp_id, Long loc_id, String ins_user_id, Date ins_date, String ins_ip_addr)
			throws Exception;

	void insertEmpDivAccess(String emp_id, Long div_id, String ins_user_id, Date ins_date, String ins_ip_addr)
			throws Exception;

	void insertEmpDivReportAccess(String emp_id, Long div_id, String ins_user_id, Date ins_date, String ins_ip_addr)
			throws Exception;

	void saveProductType(String emp_id, String prod_type, Long prod_type_id, String ins_user_id, Date ins_date,
			String ins_ip_addr) throws Exception;

	List<Long> getEmpLocAccess(String emp_id) throws Exception;

	List<Long> getEmpDivReportAccess(String emp_id) throws Exception;

	List<Long> getEmpProdTypeAccess(String emp_id) throws Exception;

	void deleteProductType(String emp_id, List<String> del_id) throws Exception;

	void deleteEmpLocAccess(String emp_id, List<String> del_id) throws Exception;

	void deleteEmpDivAccess(String emp_id, List<String> del_id) throws Exception;

	List<Long> getEmpDivAccess(String emp_id) throws Exception;

	void deleteEmpDivReportAccess(String emp_id, List<String> del_id) throws Exception;

	List<Long> getUserExistingBrand(String empId) throws Exception;

	List<SamplProdGroup> getBrandsByDivisionOfUser(String empId) throws Exception;

	List<Parameter> getUserByRole(String role) throws Exception;

	void deleteEmpBrandAccess(Long userId) throws Exception;

	void getDispatchLockinRelease() throws Exception;

	String getDesignationById(String id)throws Exception;

	void getDispatch_specific_LockinRelease(List<Long> locId);
}
