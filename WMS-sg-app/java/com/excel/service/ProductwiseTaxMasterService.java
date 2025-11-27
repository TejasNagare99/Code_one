package com.excel.service;

import java.util.List;

import javax.servlet.http.HttpSession;

import com.excel.bean.ProductBean;
import com.excel.bean.ProductwiseTaxMasterBean;
import com.excel.model.ProductwiseTaxMaster;
import com.excel.model.SmpItem;

public interface ProductwiseTaxMasterService {

	public Object saveProduct(ProductwiseTaxMasterBean pwtb, HttpSession session, List<Object> stateIdList, List<Object> taxCodeList);

	ProductwiseTaxMaster getObjectById(String prodCode) throws Exception;

	Boolean checkDetail(String prodCode, String Company);

}
