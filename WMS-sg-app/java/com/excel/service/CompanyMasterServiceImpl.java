package com.excel.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.stereotype.Service;

import com.excel.configuration.JpaDatabaseConfiguration;
import com.excel.model.Company;
import com.excel.model.Sub_Company;
import com.excel.repository.CompanyMasterRepository;
import com.excel.utility.MedicoConstants;

@Service
public class CompanyMasterServiceImpl implements CompanyMasterService,MedicoConstants {
	
	@Autowired private CompanyMasterRepository companyMasterRepository;
	@Autowired private JpaDatabaseConfiguration jpadatabaseconfiguration;
	
	@Override
	public Company getCompanyDetails() throws Exception {
		return companyMasterRepository.getCompanyDetails();
	}

	@Override
	public List<Sub_Company> getSubCompByCompName(String subCompName) throws Exception {
		return companyMasterRepository.getSubCompByCompName(subCompName);
	}
	
	@Override
	public List<Sub_Company> getSubCompanyList(String empId) {
		return companyMasterRepository.getSubCompanyList(empId);
	}
	@Override
	public List<Sub_Company> getAllActiveSubCompanyList(String companyCode) {
		return companyMasterRepository.getAllActiveSubCompanyList(companyCode);
	}
	
	@Override
	public void UpdateCompanydetails() throws Exception {
		// TODO Auto-generated method stub
		DriverManagerDataSource datasource = jpadatabaseconfiguration.getDataSource();
		if(!datasource.getUrl().equals(PAREKH_LIVE)) {
			System.out.println("Inside If");
			companyMasterRepository.UpdateCompanydetails();
		}
	}
	
	@Override
	public Sub_Company getsubcomCompanyByCode(String subcompcode) throws Exception {
		// TODO Auto-generated method stub
		return companyMasterRepository.getsubcomCompanyByCode(subcompcode);
	}

	@Override
	public Sub_Company getSubCompanyByEmpId(String empId) throws Exception {
		return companyMasterRepository.getSubCompanyByEmpId(empId);
	}
}
