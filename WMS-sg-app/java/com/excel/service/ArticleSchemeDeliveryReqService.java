package com.excel.service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import com.excel.bean.ArtSchemeBeanForApprNewLogic;
import com.excel.bean.ArticleSchemeBeanForSave;
import com.excel.bean.ArticleSchemeDetailsBean;
import com.excel.bean.ArticleSchemeEditBean;
import com.excel.bean.PushAlcToCurrMonth;
import com.excel.bean.SapSalesInvoiceData;
import com.excel.model.ArticleSchemeRequestHeader;
import com.excel.model.CustomerMaster;
import com.excel.model.Sub_Company;

public interface ArticleSchemeDeliveryReqService {
	public Sub_Company getSubCompanyById(Long sub_comp_id) throws Exception;

	public List<CustomerMaster> getCustomersForLocation(Long loc_id, String filter) throws Exception;
	
	public List<CustomerMaster> getCustomersByShipToCodeAndNameNotInCustLock(boolean isAllLoc, Long loc_id,
			String filter,String company_code) throws Exception;

	public List<ArticleSchemeDetailsBean> getArticleSchemeDetailsForEntry(String sub_comp_code, String invoiceDate,
			Long loc_id) throws Exception;

	public List<ArticleSchemeDetailsBean> getAllSchemeDetailsForEntry(String sub_comp_code, Long loc_id, String finyear)
			throws Exception;

	public ArticleSchemeRequestHeader saveArticleSchemeDelivery(ArticleSchemeBeanForSave bean) throws Exception;

	public ArticleSchemeRequestHeader getHeaderById(Long article_req_id) throws Exception;

	public List<ArticleSchemeRequestHeader> getUnConfirmedReqHdrs(Long loc_id, String userId) throws Exception;

	public List<ArticleSchemeEditBean> getArticleSchemeDetailsForModifyByArtReqId(Long art_req_id, String sub_comp_code,
			Long loc_id) throws Exception;

	public void delete(Long art_req_id) throws Exception;

	public Boolean checkIfInvoiceExists(String invoice_no, Long loc_id) throws Exception;

	public void finalApproval(Long art_req_id, String last_approved_by, String comp_cd, String isapproved,
			String motivation) throws Exception;

	public Map<String, String> approveArticelSchemes(List<Long> artReqIdsList, String isapproved,
			String last_approved_by, String comp_cd, String ipAddr);

	public List<ArticleSchemeRequestHeader> getArtReqNosForAutoDispatch(String finYear, String period_code, Long div_id,
			Long loc_id, String empId) throws Exception;

	public void sendArtRequestForApproval(String AUTO_APPR_IND,Long art_req_hdr_id, String user, String approver_email, String remark,
			HttpServletRequest request, HttpSession session) throws Exception;

	public Map<String, String> sendMultipeForApproval(List<String> artReqNosList, String user, String remark,
			HttpServletRequest request, HttpSession session) throws Exception;

	public List<ArticleSchemeDetailsBean> getDataForApprovals(Long art_sch_req_hdr, Long loc_id, String sub_comp_cd,
			String status) throws Exception;

	public void sendMailForArticleSchemeRequest(Long art_sch_req_hdr, String user, String apptovalType, String taskId,
			String status, String nextApprovar, Long year, String tranType, String companyCode, String ipaddr,
			ArticleSchemeRequestHeader hdr_for_notifi) throws Exception;

	boolean lockCustForArticleReqEntry(Long loc_id, Long cust_id, String userId) throws Exception;

	void releaseLockedCustomer(Long loc_id, Long cust_id, String userId) throws Exception;

	void unlockAllCustByUserId(String userId) throws Exception;
	
	void unlockAllCustomers() throws Exception;

	// new logic
	public List<ArticleSchemeDetailsBean> getAllEnteredDetailsForModify(Long loc_id, String sub_comp_code,
			Long article_req_id) throws Exception;

	CustomerMaster getCustomerById(Long cust_id) throws Exception;

	List<ArtSchemeBeanForApprNewLogic> getArticleSchemeForApprovalData(Long art_sch_req_hdr, String finYear,
			String companyCode, String status) throws Exception;

	void dispatch_to_194R_push() throws Exception;

	Boolean checkIfInvoiceExistsInSalesData(Long loc_id, String entered_invoice_no) throws Exception;

	List<SapSalesInvoiceData> getSalesProductDataFromInvoice(String sap_invoice_no) throws Exception;

	BigDecimal getDistanceForChallan(Long dsp_id) throws Exception;

	void updateArtReqDistance(Long dsp_id, BigDecimal distance) throws Exception;

	public List<ArticleSchemeDetailsBean> get_duplecate_invoice(String string, String invoice_no);

	public List<ArticleSchemeDetailsBean> get_Scheme_detail(String invoice_no);
	
	void pushAlcDatFromPrevToCurr(PushAlcToCurrMonth bean)throws Exception;
	
	void updateAlcForMonthChange(String finyear,String period_cd,Long alloc_smp_id,Long article_req_id) throws Exception;
}
