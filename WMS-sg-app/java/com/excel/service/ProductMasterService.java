package com.excel.service;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import com.excel.bean.ProductBean;
import com.excel.bean.ProductMasterBean;
import com.excel.model.SmpItem;


public interface ProductMasterService {
	
	SmpItem getObjectById(Long smpProdId) throws Exception;
	List<SmpItem> getProductListForGRN(String companyCode, Long subCompId, Long prodType, Long prodGroup, Long divId)
			throws Exception;
	List<ProductMasterBean> getProductDetailByProdId(String companyCode, Long locId, Long ProdId,String stkType) throws Exception;
	List<SmpItem> getProdListByProdIds(List<Long> ProdIds) throws Exception;
	List<ProductBean> getUmo() throws Exception;
	List<ProductMasterBean> getPack();
	
	public void saveProduct(ProductBean pb,String empId,HttpSession session,HttpServletRequest request) throws Exception;
	
	List<Object> getProductDetailForProductwiseTaxMaster(String divId, String prodTypeId, String subCompId,
			String hsnCode) throws Exception;
	List<Object> getTaxDetailForProductwiseTaxMaster(String prodCode, String compCode, String hsnCode);
	List<Object> getHSNCode(String prodCode, String Company);
	List<Object> getAllActiveProductDetailForProductModify();
	List<Object> getProductDetailByTextParameterForProductModify(String name, String parameter, String text);
	List<SmpItem> getProductDetailById(String id);
	void updateProduct(String prodId, ProductBean pb, String empId, HttpSession session, HttpServletRequest request) throws Exception;
	List<Object> getSampleProductList(Long divId,String compcd) throws Exception;
	List<SmpItem> getProdListByDivPrdType(String comp_cd, Long prod_type, Long div_id)throws Exception;
	
	//Itemwise Stock Ledger(28-4-2020)
		List<SmpItem> getAllActiveItemList();
		List<SmpItem> getItemListByDivId(String divId);
		List<SmpItem> getItemByCode(String prodCode);
		String genereateProductCode(String subCompId,String productId) throws Exception;
		void deleteLockProduct(String user_id) throws Exception;
		
		List<SmpItem> checkUniqueErpProductCode(String erpprodCode,String subCompany);
		List<SmpItem> checkUniqueProductCode(String prodCode,String subCompany);
		
		List<Object> getSampleProductListEmergency(Long divId,String compcode) throws Exception;
		
		public SmpItem getProductIdBySmpProdCode(String smp_prod_code) throws Exception;
		List<com.excel.bean.Hsn_and_Tax_Bean> getAllHsnCode();
		Object getProductListForGRN_for_medley(String trim, Long subCompId, Long prodType, Long prodGroup, Long divId) throws Exception;
}
