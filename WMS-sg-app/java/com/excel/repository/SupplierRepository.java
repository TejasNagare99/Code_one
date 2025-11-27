package com.excel.repository;

import java.util.List;

import com.excel.bean.SupplierBean;
import com.excel.model.Supplier;

public interface SupplierRepository {

	List<Supplier> getSuppliersBySupplierType(String supplierType, Long locId, Long subCompId) throws Exception;
	Supplier supplierStateGstNo(Long sup_id)  throws Exception;
	List<Object> getAllActiveSupplier(String subComp);
	Boolean checkUniqueSupplier(String SupName, String SupType, String SubCompany);
	Object checkUniqueGST(String Gst);
	List<Supplier> getAllActiveSupplierList(String status);
	List<Supplier> getTextParameterSupplierList(String status, String name, String parameter, String text);
	Supplier getObjectById(Long supId) throws Exception;
	List<Supplier> getSupplierDetailById(String supId);
	Boolean checkUniqueSupplierNameForModify(String SupName, String SupType, String SubCompany, String SupId);

}
