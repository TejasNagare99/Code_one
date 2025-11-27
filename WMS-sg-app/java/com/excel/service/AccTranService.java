package com.excel.service;

import java.util.List;

import com.excel.bean.CustomAccTranGeneration;
import com.excel.model.Acc_Tran_Type;
import com.excel.model.Acc_Tran_Type.TranNameEnum;


public interface AccTranService {
	String getMaxOfAccTranType(String companyCode) throws Exception;
	
	List<CustomAccTranGeneration> getAccTranTypeByLocId(Long loc_id) throws Exception;
	
	void saveApprTransForLocation(Long loc_id,String approver_id,String companyCd) throws Exception;
	
	Acc_Tran_Type getAccRecordByFullTranNameAndLocId(TranNameEnum full_tran_name, Long loc_id) throws Exception;
}
