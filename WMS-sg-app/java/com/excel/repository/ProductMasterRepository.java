package com.excel.repository;

import java.math.BigDecimal;
import java.util.List;

import com.excel.bean.Hsn_and_Tax_Bean;
import com.excel.bean.ProductBean;
import com.excel.bean.ProductMasterBean;
import com.excel.model.SmpItem;

public interface ProductMasterRepository {

	List<SmpItem> getProductListForGRN(String companyCode, Long subCompId, Long prodType, Long prodGroup, Long divId)
			throws Exception;

	List<ProductMasterBean> getProductDetailByProdId(String companyCode, Long locId, Long ProdId,String stkType) throws Exception;

	BigDecimal getRateByProdId(Long ProdId) throws Exception;

	SmpItem getDivIdByProdId(Long ProdId) throws Exception;

	List<SmpItem> getProdListByProdIds(List<Long> ProdIds) throws Exception;

	SmpItem getObjectById(Long smpProdId) throws Exception;

	List<ProductBean> getUmo() throws Exception;

	List<ProductMasterBean> getPack();

	

	List<Object> getProductDetailForProductwiseTaxMaster(String divId, String prodTypeId, String subCompId,String hsnCode);

	List<Object> getTaxDetailForProductwiseTaxMaster(String prodCode, String compCode, String hsnCode);

	List<Object> getHSNCode(String prodCode, String Company);

	List<Object> getAllActiveProductDetailForProductModify();

	List<Object> getProductDetailByTextParameterForProductModify(String name, String parameter, String text);

	List<SmpItem> getProductDetailById(String id);

	List<Object> getSampleProductList(Long divId,String compcd) throws Exception;
	
	List<SmpItem> getProdListByDivPrdType(String comp_cd, Long prod_type, Long div_id)throws Exception;
	
	
	
	//Itemwise Stock Ledger(28-4-2020)
		List<SmpItem> getAllActiveItemList();

		List<SmpItem> getItemListByDivId(String divId);
		
		List<SmpItem> getItemByCode(String prodCode);

		SmpItem getProductMasterObjByCode(String product_code) throws Exception;

		String genereateProductCode(String sibCompyCode, String date) throws Exception;

		void deleteLockProduct(String user_id) throws Exception;
		
		List<SmpItem> checkUniqueErpProductCode(String erpprodCode,String subCompany);

		List<SmpItem> checkUniqueProductCode(String prodCode, String subCompany);
		
		public List<Object> getSampleProductListEmergency(Long divId, String compcode) throws Exception;
		
		public SmpItem getProductIdBySmpProdCode(String smp_prod_code) throws Exception;

		void deleteproductwise_tax_master_by_procode(String product_code) throws Exception;

		List<Hsn_and_Tax_Bean> getAllHsnCode();
}
