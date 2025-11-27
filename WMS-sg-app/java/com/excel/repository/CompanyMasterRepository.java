package com.excel.repository;

import java.util.List;

import com.excel.model.Company;
import com.excel.model.Sub_Company;

public interface CompanyMasterRepository {
	
	Company getCompanyDetails() throws Exception;
	List<Sub_Company> getSubCompByCompName(String subCompName) throws Exception;
	Character checkMargin(String compCode);
	List<Sub_Company> getSubCompanyList(String empId);
	List<Sub_Company> getAllActiveSubCompanyList(String companyCode);
	Sub_Company getSubCompanyObjectById(Long headerId) throws Exception;
	List<Company> getCompanyList() throws Exception;
	void UpdateCompanydetails() throws Exception;
	Sub_Company getsubcomCompanyByCode(String subcompcode)throws Exception;
	String getSubComnpanyCodeByLocId(Long loc_id) throws Exception;
	
	Sub_Company getSubCompanyByEmpId(String empId) throws Exception;
}
