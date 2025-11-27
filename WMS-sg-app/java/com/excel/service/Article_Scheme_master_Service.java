package com.excel.service;

import java.io.IOException;
import java.text.ParseException;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.web.multipart.MultipartFile;

import com.excel.bean.Articele_Scheme_Approved_Data;
import com.excel.bean.ArticleSchemMasterCompanyData;
import com.excel.bean.ArticleSchemeMasterSALEPRODData;
import com.excel.bean.ArticleSchemeReportBean;
import com.excel.bean.Article_bean_for_modify;
import com.excel.bean.Article_save_as_bean;
import com.excel.bean.SchemeMasterSampleProductBean;
import com.excel.bean.SpecificLocationBean;
import com.excel.bean.Validity_ext_bean;
import com.excel.model.Art_Sch_Vld_Ext;
import com.excel.model.Artreq_To_Dispatch_Report_With_Param;
import com.excel.model.DivMaster;
import com.excel.model.New_Stockist_wiseScheme_request_model;
import com.excel.model.New_stockist_wise_scheme_request_supply_Detail_model;
import com.excel.model.SamplProdGroup;
import com.excel.model.Scheme_Desription_Bean;
import com.excel.model.Scheme_Summary;
import com.excel.model.TRD_SCH_MST_DOCS;
import com.excel.model.trd_scheme_mst_hdr;

public interface Article_Scheme_master_Service {

	List<ArticleSchemMasterCompanyData> getCompanyDataforSchemeMaster() throws Exception;

	List<ArticleSchemeMasterSALEPRODData> get_SALEPROD_DataforSchemeMaster();

	List<SpecificLocationBean> getSpecificLocation(String subCompanyId) throws Exception;

	List<SchemeMasterSampleProductBean> get_sample_item(String subCompanyId) throws Exception;

	Map<String, Object> saveArticle_Scheme_master(Article_save_as_bean bean, HttpServletRequest request,
			List<MultipartFile> files) throws Exception;

	Boolean deleteScheme(String trs_sl_no) throws Exception;

	Map<String, Object> update(Article_save_as_bean bean, HttpServletRequest request, List<MultipartFile> files)
			throws Exception;

	Map<String, Object> confirm_approval(List<Long> arrayOfslNumber, HttpSession session, HttpServletRequest request,
			String userId) throws Exception;

	String downloadAsZipFile(List<String> arrayOfFilesToZip) throws Exception;

	Map<String, Object> get_Docs_Details(String schemeslno, String trantype, String dataMode) throws Exception;

	Boolean deleteDoc(String docId, String dataMode) throws Exception;

	public void finalApproval(Long scheme_slno, String last_approved_by, String comp_cd, String isapproved,
			String motivation) throws Exception;

	Map<String, Object> lock_article_product(String articleId, String userId) throws Exception;

	List<SchemeMasterSampleProductBean> search_Article_Product_By_Name(String search_value, String subcompanyId)
			throws Exception;

	void unlock_article_product(String userId);

	List<SchemeMasterSampleProductBean> get_top_Article_product(String subCompanyId) throws Exception;

	List<ArticleSchemeMasterSALEPRODData> get_top_sale_product();

	List<SamplProdGroup> get_brands(String sales_product_division, String scheme_division);

	List<ArticleSchemeMasterSALEPRODData> get_saleProduct(List<Long> arrayOfbrandSelectedValues, long subcompanyId);

	Map<String, Object> get_Detail_Data_For_Edit(String trd_SCH_SLNO) throws Exception;

	void delete_Article_Product_On_The_Product_Locking_Table(String userId);

	void send_Mail_for_cfa_location(String hdr_sl_no, String link, String discardLink, List<String> tolist,boolean isValidityExtension,
			Art_Sch_Vld_Ext artSchVldExt,Boolean discard,String Approver_name,boolean status)
			throws Exception;

	List<Artreq_To_Dispatch_Report_With_Param> getArticleSchemeReport(ArticleSchemeReportBean bean) throws Exception;

	String generateArticleSchemeReport(List<Artreq_To_Dispatch_Report_With_Param> list, ArticleSchemeReportBean bean,
			HttpSession session, List<Scheme_Desription_Bean> listOfSchemDescription) throws Exception;

	List<Artreq_To_Dispatch_Report_With_Param> getArticleSchemeDirectToStockistReport(ArticleSchemeReportBean bean);

	List<Scheme_Desription_Bean> getSchemeDescription(ArticleSchemeReportBean bean);

	List<Article_bean_for_modify> get_approval_data(String employeeId) throws Exception;

	Map<String, Long> getArticleGraphData(String emp_id, String emp_loc, String subcom_id) throws Exception;

	void sendmails(trd_scheme_mst_hdr hdr, String tranType, String taskId, String nextApprovar, String companyCode,
			String user, String ipaddr,boolean isValidityExtension,Art_Sch_Vld_Ext artSchVldExt) throws Exception;

	List<DivMaster> get_Scheme_Division() throws Exception;

	List<Scheme_Summary> get_article_Scheme_Summary_Home(String periodCd) throws Exception;

	List<trd_scheme_mst_hdr> get_HDR_Data_For_Validity_Extension(String empId);

	void saveValidityExtension(Validity_ext_bean bean, List<MultipartFile> files,
			HttpServletRequest request, HttpSession session) throws Exception;
	
	

	public void sendScmVldExtensionForApproval(trd_scheme_mst_hdr hdr,Art_Sch_Vld_Ext artSchVldExt, String user, String approver_email,
			String remark, HttpServletRequest request, HttpSession session) throws Exception;

	public void finalApproval_SchemeExtVld(Long scm_extvld_slno, String last_approved_by, String comp_cd,
			String isapproved, String motivation) throws Exception;
	
	List<TRD_SCH_MST_DOCS> getDocsBySchemeSlno(Long trd_sch_slno) throws Exception;
	
	Art_Sch_Vld_Ext getVldExtenById(Long slno) throws Exception;
	
	List<Art_Sch_Vld_Ext> getEnteredExtensions(String empId) throws Exception;
	
	List<Articele_Scheme_Approved_Data> getApprovedData(String schemeDivId, String tranType, Long trd_SCH_SLNO) throws Exception;

	List<Artreq_To_Dispatch_Report_With_Param> 	 sortList(List<New_Stockist_wiseScheme_request_model> list, List<New_stockist_wise_scheme_request_supply_Detail_model> detaillist, ArticleSchemeReportBean bean) throws ParseException;

	void unlock_all_article_product();

	List<trd_scheme_mst_hdr> get_all_scheme() throws Exception;


	Map<String, Object> generateArticleSchemeReportAndExtention(Long trd_slno, String website) throws Exception;
	List<Articele_Scheme_Approved_Data> getApprovedData_For_Log_Report(String schemeDivId, String tranType, Long trd_SCH_SLNO) throws Exception;
	
	List<trd_scheme_mst_hdr> get_scheme_for_closure(String empId);

	void saveClosure_Date(String trs_sl_no, String closure_date) throws Exception;
	
	public List<trd_scheme_mst_hdr> getAssociatedSchemsByEmpId(String empId) throws Exception;

	List<New_Stockist_wiseScheme_request_model> getArticleSchemeDirectToStockistReportNewHeaderData(
			ArticleSchemeReportBean bean) throws Exception;

	String generateArticleSchemeReportHeadernew(List<New_Stockist_wiseScheme_request_model> list,
			ArticleSchemeReportBean bean, HttpSession session, List<Scheme_Desription_Bean> listOfSchemDescription, List<New_stockist_wise_scheme_request_supply_Detail_model> detaillist) throws IOException;

	List<New_stockist_wise_scheme_request_supply_Detail_model> getDispatchDetailData();
}
