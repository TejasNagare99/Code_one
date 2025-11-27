package com.excel.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.excel.bean.Parameter;
import com.excel.model.SamplProdGroup;
import com.excel.model.UserDepartment;
import com.excel.model.Usermaster;
import com.excel.repository.UserDepartmentRepository;
import com.excel.repository.UserMasterRepository;

@Service
public class UserDepartmentServiceImpl implements UserDepartmentService {
	@Autowired UserDepartmentRepository userDepartmentRepository;
	@Autowired UserMasterRepository userMasterRepository;
	
	@Override
	public List<UserDepartment> getDepartments() throws Exception {
		// TODO Auto-generated method stub
		return this.userDepartmentRepository.getDepartments();
	}

	@Override
	public List<Parameter> getActiveUserList() throws Exception {
		// TODO Auto-generated method stub
		return this.userDepartmentRepository.getActiveUserList();
	}

	@Override
	public List<Parameter> getActiveDesignation() throws Exception {
		// TODO Auto-generated method stub
		return this.userDepartmentRepository.getActiveDesignation();
	}

	@Override
	public Integer getMaxUserKey() throws Exception {
		// TODO Auto-generated method stub
		return this.userDepartmentRepository.getMaxUserKey();
	}

	@Override
	public List<Parameter> getActiveUserDtl() throws Exception {
		// TODO Auto-generated method stub
		return this.userDepartmentRepository.getActiveUserDtl();
	}

	@Override
	public List<Parameter> getUserByRole(String role) throws Exception {
		// TODO Auto-generated method stub
		return  this.userDepartmentRepository.getUserByRole(role);
	}

	@Override
	public List<Long> getUserExistingBrand(String empId) throws Exception {
		// TODO Auto-generated method stub
//		Usermaster user=this.userMasterRepository.getUserByEmpId(empId);
//		Long userId=user.getLd_id();
		return  this.userDepartmentRepository.getUserExistingBrand(empId);
	}

	@Override
	public List<SamplProdGroup> getBrandsByDivisionOfUser(String empId) throws Exception {
		// TODO Auto-generated method stub
		return  this.userDepartmentRepository.getBrandsByDivisionOfUser(empId);
	}

}
