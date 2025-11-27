package com.excel.service;

import java.util.List;

import com.excel.model.SG_d_parameters_details;

public interface ParameterService {
	
	List<SG_d_parameters_details> getGrnTypes() throws Exception;
	List<SG_d_parameters_details> getSupplierTypeByGrnType(String grnType) throws Exception;
	List<SG_d_parameters_details> getProductTypeList() throws Exception;
	List<SG_d_parameters_details> getStockTypes() throws Exception;
	List<SG_d_parameters_details> getState() throws Exception;
	List<SG_d_parameters_details> getSupplierTypeForSupplierEntry();
	List<SG_d_parameters_details> getForm();
	List<SG_d_parameters_details> getManufacturingLocation();
	SG_d_parameters_details getParameterDataByDisplayNameWithJoin(String paraType);
	List<SG_d_parameters_details> getTheropathyGroup();
	List<SG_d_parameters_details> getMoleGroup();
	List<SG_d_parameters_details> getPMTGroup();
	List<SG_d_parameters_details> getStorageType();
	List<SG_d_parameters_details> getProductSubType(String shortName);
	List<SG_d_parameters_details> getProductSubType();
	List<SG_d_parameters_details> getSampleSupplierType();
	List<SG_d_parameters_details> getCity();
	List<SG_d_parameters_details> getFsType();
	List<SG_d_parameters_details> getClassList();
	List<SG_d_parameters_details> getFsTypeInd();
	List<SG_d_parameters_details> getProductTypes();
	List<SG_d_parameters_details> getPMTSubGroup();
	List<SG_d_parameters_details> getMoleSubGroup();
	List<SG_d_parameters_details> getTheropathySubGroup();
	List<Object> getParameterIdName(String type, String text);
	List<SG_d_parameters_details> getParaDetailsByParaType(String paraType) throws Exception;
	List<SG_d_parameters_details> getActiveProductSubType();
	List<SG_d_parameters_details> getParameterDetailByDispNm(String paraType);
	List<SG_d_parameters_details> getZone(Long divId);
	List<SG_d_parameters_details> getDispatchParameters();

	List<SG_d_parameters_details> getRateParameters();
	List<String> getDefaultParameterEmailsByTranType(String trantype)throws Exception;
	
	public String IsOtp_Captcha_Req(String paratype)throws Exception;
	public String getSrtAndEmailReqInd(String trantype) throws Exception ;
	public String getGprmIndicator() throws Exception ; 
	
	public String articleAutoApproval() throws Exception;
	public String dispatchAutoApproval() throws Exception;
	public String getGrnRefNoInd() throws Exception;
	List<SG_d_parameters_details> getVehicleRecdTime() throws Exception;
	
}
