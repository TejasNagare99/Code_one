package com.excel.service;

import java.util.List;

import com.excel.model.ItemMasterAuditTrail;

public interface Product_Master_audit_trail_reportService {
	
	String generate_product_Master_audit_trail_report(List<ItemMasterAuditTrail>list,String username,Long prod_type,String empId)throws Exception;
}
