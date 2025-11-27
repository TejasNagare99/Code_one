package com.excel.service;

import java.util.List;

import com.excel.model.Company;
import com.excel.model.Sub_Company;


public interface CompanyMasterService {

	Company getCompanyDetails() throws Exception;
	List<Sub_Company> getSubCompByCompName(String subCompName) throws Exception;
	List<Sub_Company> getSubCompanyList(String empId);
	List<Sub_Company> getAllActiveSubCompanyList(String companyCode);

	Sub_Company getsubcomCompanyByCode(String subcompcode)throws Exception;
	void UpdateCompanydetails() throws Exception;
	
	Sub_Company getSubCompanyByEmpId(String empId) throws Exception;
}
