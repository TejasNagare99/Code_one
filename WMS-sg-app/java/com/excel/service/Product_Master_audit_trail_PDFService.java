package com.excel.service;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import com.excel.model.ItemMasterAuditTrail;

public interface Product_Master_audit_trail_PDFService {
	String generate_product_Master_audit_trail_pdf(List<ItemMasterAuditTrail>list,String companyname,String username,long div_id, long sub_comp_id,String brand_str,long prod_id,Long prod_type,boolean checkMe,String date,HttpSession session,HttpServletRequest request)throws Exception;
}
