package com.excel.service;

import java.util.List;

import com.excel.bean.UsageInstructionBean;
import com.excel.model.DivMaster;
import com.excel.model.SmpItem;
import com.excel.model.Usage_Instruction;

public interface Product_Usage_InstructionService{

	
	List<DivMaster> getActivedivisionlist()throws Exception;
	
	List<SmpItem> getproductsbydiv(String divId)throws Exception;
	
	Usage_Instruction checkusageinstruction(String prod_id,String monthofuse,String finyr,String companycode)throws Exception;
	
	String SaveUsageinstruction(UsageInstructionBean bean)throws Exception;
}
