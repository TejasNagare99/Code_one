package com.excel.service;

import java.util.List;

import com.excel.bean.Parameter;
import com.excel.model.SamplProdGroup;
import com.excel.model.UserDepartment;

public interface UserDepartmentService {

	List<UserDepartment> getDepartments() throws Exception;

	List<Parameter> getActiveUserList() throws Exception;

	List<Parameter> getActiveDesignation() throws Exception;

	Integer getMaxUserKey() throws Exception;
	
	List<Parameter> getActiveUserDtl() throws Exception;

	List<Long> getUserExistingBrand(String userId) throws Exception;

	List<SamplProdGroup> getBrandsByDivisionOfUser(String empId) throws Exception;

	List<Parameter> getUserByRole(String role) throws Exception;
}
