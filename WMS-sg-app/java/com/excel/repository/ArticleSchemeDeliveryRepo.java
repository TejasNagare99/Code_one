package com.excel.repository;

import java.math.BigDecimal;
import java.util.List;

import com.excel.bean.ArtSchemeBeanForApprNewLogic;
import com.excel.bean.ArtScmBeanForApprovals;
import com.excel.bean.ArticleSchemeDetailsBean;
import com.excel.bean.ArticleSchemeEditBean;
import com.excel.bean.SapSalesInvoiceData;
import com.excel.model.ArticleSchemeRequestDetail;
import com.excel.model.ArticleSchemeRequestHeader;
import com.excel.model.CustLock;

public interface ArticleSchemeDeliveryRepo {
	public List<ArticleSchemeDetailsBean> getArticleSchemeDetailsForEntry(String sub_comp_code , String invoiceDate,Long loc_id) 
			throws Exception;
	public List<ArticleSchemeDetailsBean> getAllSchemeDetailsForEntry(String sub_comp_code,Long loc_id,String finyear)
			throws Exception;
	public ArticleSchemeRequestHeader getHeaderById(Long id) throws Exception;
	public ArticleSchemeRequestHeader getHeaderByReqNo(String req_no) throws Exception;
	public void deleteProductsById(Long id) throws Exception;
	public void deleteDetailsById(Long id) throws Exception;
	public void deleteHeaderById(Long id) throws Exception;
	public List<ArticleSchemeRequestHeader> getUnConfirmedReqHdrs(Long loc_id,String userId) throws Exception;
	public List<ArticleSchemeEditBean> getArticleSchemeDetailsForModifyByArtReqId(Long art_req_id,String sub_comp_code,Long loc_id )
			throws Exception;
	public Boolean checkIfInvoiceExists(String invoice_no,Long loc_id) throws Exception;
	public List<ArticleSchemeRequestDetail> getDetailsByHdrReqId(Long art_req_hdr_id) throws Exception;
	public void callFinalApprovalProcedure(Long art_req_hdr_id , String userId , String ipAddr)throws Exception;
	public List<ArticleSchemeRequestHeader> getArtReqNosForAutoDispatch(String finYear,String period_code,
			Long div_id,Long loc_id,String empId)
			throws Exception;
	List<ArtScmBeanForApprovals> getDataForApprovals(Long art_sch_req_hdr,String finYear,String companyCode,String status) throws Exception;
	void lockCustForArticleReqEntry(Long loc_id,Long cust_id,String userId) throws Exception;
	void releaseLockedCustomer(Long loc_id,Long cust_id,String userId) throws Exception;
	void unlockAllCustByUserId(String userId) throws Exception;
	
	void unlockAllCustomers() throws Exception;
	
	//get object of cust lock 
	CustLock getLockedCustomer(Long cust_id,Long loc_id) throws Exception;
	
	//new logic
	public List<ArticleSchemeDetailsBean> getAllEnteredDetails(Long loc_id,String sub_comp_code,Long article_req_id,String status) throws Exception;

	List<ArtSchemeBeanForApprNewLogic> getArticleSchemeForApprovalData(Long art_sch_req_hdr, String finYear, String companyCode,String status) throws Exception;

	void dispatch_to_194R_push() throws Exception;
	
	Boolean checkIfInvoiceExistsInSalesData(Long loc_id,String entered_invoice_no) throws Exception;
	
	List<SapSalesInvoiceData> getSalesProductDataFromInvoice(String sap_invoice_no) throws Exception;
	
	BigDecimal getDistanceForChallan(Long dsp_id) throws Exception;
	
	void updateArtReqDistance(Long dsp_id,BigDecimal distance) throws Exception;
	public List<ArticleSchemeDetailsBean> get_duplecate_invoice(String articleRequestId, String sapInvNo);
	public List<ArticleSchemeDetailsBean> get_Scheme_detail(String invoice_no);
	
	void updateAlcHdMonthChange(String finyear,String period_cd,Long alloc_smp_id,Long article_req_id) throws Exception;
	void updateAlcDetailsMonthChange(String finyear,String period_cd,Long alloc_smp_id,Long article_req_id) throws Exception;
}
