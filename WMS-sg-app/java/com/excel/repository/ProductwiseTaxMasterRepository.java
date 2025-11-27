package com.excel.repository;

import java.util.List;

import com.excel.model.ProductwiseTaxMaster;

public interface ProductwiseTaxMasterRepository {

	ProductwiseTaxMaster checkData(String prodCode, String stateId, String taxCode);

	Boolean checkDetail(String prodCode, String Company);

	Boolean checktaxansateExistForproduct(String taxCode, String sateCode, String prodcode);

}
