package com.excel.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.excel.model.SG_d_parameters_details;
import com.excel.repository.ParameterRepository;

@Service
public class ParameterServiceImpl implements ParameterService{
	
	@Autowired ParameterRepository parameterRepository;

	@Override
	public List<SG_d_parameters_details> getGrnTypes() throws Exception {
		return this.parameterRepository.getGrnTypes();
	}

	@Override
	public List<SG_d_parameters_details> getSupplierTypeByGrnType(String grnType) throws Exception {
		return this.parameterRepository.getSupplierTypeByGrnType(grnType);
	}

	@Override
	public List<SG_d_parameters_details> getProductTypeList() throws Exception {
		return this.parameterRepository.getProductTypeList();
	}

	@Override
	public List<SG_d_parameters_details> getStockTypes() throws Exception {
		return this.parameterRepository.getStockTypes();
	}
	@Override
	public List<SG_d_parameters_details> getState() throws Exception{
		return this.parameterRepository.getState();
	}
	@Override
	public List<SG_d_parameters_details> getSupplierTypeForSupplierEntry() {
		return this.parameterRepository.getSupplierTypeForSupplierEntry();
	}
	@Override
	public List<SG_d_parameters_details>  getForm() {
		return this.parameterRepository.getForm();
	}
	@Override
	public List<SG_d_parameters_details> getManufacturingLocation() {
		return this.parameterRepository.getManufacturingLocation();
	}
	@Override
	public List<SG_d_parameters_details> getTheropathyGroup() {
		return this.parameterRepository.getTheropathyGroup();
	}
	@Override
	public List<SG_d_parameters_details> getTheropathySubGroup() {
		return this.parameterRepository.getTheropathySubGroup();
	}
	@Override
	public List<SG_d_parameters_details> getMoleGroup() {
		return this.parameterRepository.getMoleGroup();
	}
	@Override
	public List<SG_d_parameters_details> getMoleSubGroup() {
		return this.parameterRepository.getMoleSubGroup();
	}
	@Override
	public List<SG_d_parameters_details> getPMTGroup() {
		return this.parameterRepository.getPMTGroup();
	}
	@Override
	public List<SG_d_parameters_details> getPMTSubGroup() {
		return this.parameterRepository.getPMTSubGroup();
	}
	@Override
	public List<SG_d_parameters_details> getStorageType() {
		return this.parameterRepository.getStorageType();
	}
	@Override
	public List<SG_d_parameters_details> getProductTypes() {
		return this.parameterRepository.getProductTypes();
	}
	@Override
	public List<SG_d_parameters_details> getProductSubType() {
		return this.parameterRepository.getProductSubType();
	}
	@Override
	public List<SG_d_parameters_details> getProductSubType(String shortName) {
		return this.parameterRepository.getProductSubType(shortName);
	}
	@Override
	public List<SG_d_parameters_details> getSampleSupplierType() {
		return this.parameterRepository.getSampleSupplierType();
	}
	@Override
	public List<SG_d_parameters_details> getCity() {
		return this.parameterRepository.getCity();
	}
	@Override
	public List<SG_d_parameters_details> getZone(Long divId) {
		return this.parameterRepository.getZone(divId);
	}
	@Override
	public List<SG_d_parameters_details> getFsType() {
		return this.parameterRepository.getFsType();
	}
	@Override
	public List<SG_d_parameters_details> getClassList() {
		return this.parameterRepository.getClassList();
	}
	@Override
	public List<SG_d_parameters_details> getFsTypeInd() {
		return this.parameterRepository.getFsTypeInd();
	}

	@Override
	public List<Object> getParameterIdName(String type,String text) {
		// TODO Auto-generated method stub
		return this.parameterRepository.getParameterIdName(type,text);
	}
	
	@Override
	public List<SG_d_parameters_details> getParaDetailsByParaType(String paraType) throws Exception {
		return parameterRepository.getParaDetailsByParaType(paraType);
	}
	
	@Override
	public List<SG_d_parameters_details> getActiveProductSubType() {
		return this.parameterRepository.getActiveProductSubType();
	}

	@Override
	public List<SG_d_parameters_details> getParameterDetailByDispNm(String paraType) {
		// TODO Auto-generated method stub
		return this.parameterRepository.getParameterDetailByDispNm(paraType);
	}

	@Override
	public List<SG_d_parameters_details> getDispatchParameters() {
		// TODO Auto-generated method stub
		return this.parameterRepository.getDispatchParameters();
	}

	@Override
	public List<SG_d_parameters_details> getRateParameters() {
		// TODO Auto-generated method stub
		return this.parameterRepository.getRateParameters();
	}

	@Override
	public List<String> getDefaultParameterEmailsByTranType(String trantype) throws Exception {
		// TODO Auto-generated method stub
		return parameterRepository.getDefaultParameterEmailsByTranType(trantype);
	}

	@Override
	public String IsOtp_Captcha_Req(String paratype) throws Exception {
		// TODO Auto-generated method stub
		return parameterRepository.IsOtp_Captcha_Req(paratype);
	}

	@Override
	public String getSrtAndEmailReqInd(String trantype) throws Exception {
		// TODO Auto-generated method stub
		return parameterRepository.getSrtAndEmailReqInd(trantype);
	}
	
	@Override
	public SG_d_parameters_details getParameterDataByDisplayNameWithJoin(String paraType) {
		return parameterRepository.getParameterDataByDisplayNameWithJoin(paraType);
	}

	@Override
	public String getGprmIndicator() throws Exception {

		return this.parameterRepository.getGprmIndicator();
	}

	@Override
	public String articleAutoApproval() throws Exception {
		return this.parameterRepository.articleAutoApproval();
	}

	@Override
	public String dispatchAutoApproval() throws Exception {
		return this.parameterRepository.dispatchAutoApproval();
	}

	@Override
	public String getGrnRefNoInd() throws Exception {
		return this.parameterRepository.getGrnRefNoInd();
	}
	
	@Override
	public List<SG_d_parameters_details> getVehicleRecdTime() throws Exception {
		return this.parameterRepository.getVehicleRecdTime();
	}

	
}
