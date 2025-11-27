package com.excel.repository;

import com.excel.bean.TaxParamBean;

public interface CalculateGstRepository {

	TaxParamBean getTaxParamsByStateIdAndProdId(Long state_id, Long prod_id) throws Exception;

	String getGST_Doc_type_Para_code(String tran_type, String gst_doc_type) throws Exception;

}
