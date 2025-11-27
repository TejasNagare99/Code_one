package com.excel.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.excel.model.V_GRN_Header;
import com.excel.repository.V_GRN_HeaderRepositoryNew;

@Service
public class V_GRN_HeaderServiceImpl implements V_GRN_HeaderService {

	@Autowired V_GRN_HeaderRepositoryNew v_GRN_HeaderRepositoryNew;

	@Override
	public List<V_GRN_Header> getGrnListByEmpIdAndStatus(String grnStatus, String grnApprStatus, String empId,
			String companyCode, String finYear) throws Exception {
		return this.v_GRN_HeaderRepositoryNew.getGrnListByEmpIdAndStatus(grnStatus, grnApprStatus, empId, companyCode, finYear);
	}

	@Override
	public List<V_GRN_Header> getGrnListByLocId(Long locId, String comapnyCode, String empId, String fromDate,
			String toDate) throws Exception {
		return this.v_GRN_HeaderRepositoryNew.getGrnListByLocId(locId, comapnyCode, empId, fromDate, toDate);
	}

	@Override
	public V_GRN_Header getObjectById(Long grnId) throws Exception {
		return this.v_GRN_HeaderRepositoryNew.getObjectById(grnId);
	}
	

}
