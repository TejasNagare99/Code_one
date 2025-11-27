package com.excel.service;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import com.excel.bean.SupplierBean;
import com.excel.model.SG_d_parameters_details;
import com.excel.model.Supplier;

public interface SupplierService {

	List<Supplier> getSuppliersBySupplierType(String supplierType, Long locId, Long subCompId) throws Exception;
	
	List<Object> getAllActiveSupplier(String subComp);
	
	List<SG_d_parameters_details> getSupplierTypeForSupplierEntry();
	
	List<SG_d_parameters_details> getStateForSupplierEntry() throws Exception;
	
	Boolean checkUniqueSupplier(String SupName, String SupType, String SubCompany);

	Object checkUniqueGST(String Gst);

	void saveSupplier(SupplierBean supplierBean, HttpServletRequest request, String empId, String gst) throws Exception;

	List<Supplier> getAllActiveSupplierList(String status);

	List<Supplier> getTextParameterSupplierList(String status,String name, String parameter, String text);

	List<Supplier> getSupplierDetailById(String supId);

	void updateSupplier(String supId,SupplierBean supplierBean, HttpServletRequest request, String empId);

	Boolean checkUniqueSupplierNameForModify(String supName, String supType, String subCompany, String supId);

}
