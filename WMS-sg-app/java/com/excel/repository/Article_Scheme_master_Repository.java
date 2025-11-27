package com.excel.repository;



import java.text.ParseException;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.web.multipart.MultipartFile;

import com.excel.bean.Articel_Scheme_Approved_Data_Bean;
import com.excel.bean.Articele_Scheme_Approved_Data;
import com.excel.bean.ArticleDocsBean;
import com.excel.bean.ArticleSchemMasterCompanyData;
import com.excel.bean.ArticleSchemeMasterSALEPRODData;
import com.excel.bean.ArticleSchemeReportBean;
import com.excel.bean.Article_Scheme_Extent_Bean;
import com.excel.bean.Article_bean_for_modify;
import com.excel.bean.Article_save_as_bean;
import com.excel.bean.SchemeDetailDataBean;
import com.excel.bean.SchemeMasterSampleProductBean;
import com.excel.bean.SpecificLocationBean;
import com.excel.model.ARTICLE_SCH_VALID_EXT_DOCS;
import com.excel.model.Art_Sch_Vld_Ext;
import com.excel.model.Artreq_To_Dispatch_Report_With_Param;
import com.excel.model.DivMaster;
import com.excel.model.FinYear;
import com.excel.model.New_Stockist_wiseScheme_request_model;
import com.excel.model.New_stockist_wise_scheme_request_supply_Detail_model;
import com.excel.model.SamplProdGroup;
import com.excel.model.Scheme_Desription_Bean;
import com.excel.model.SmpItem;
import com.excel.model.TRD_SCH_MST_DOCS;
import com.excel.model.trd_scheme_mst_dtl;
import com.excel.model.trd_scheme_mst_hdr;

public interface Article_Scheme_master_Repository {

	List<ArticleSchemMasterCompanyData> getCompanyDataForArticleSchemeMaster() throws Exception;

	List<ArticleSchemeMasterSALEPRODData> getSaleProductDataForArticleSchemeMaster();

	List<SpecificLocationBean> getSpecificLocation(String subCompanyId) throws Exception;

	List<SchemeMasterSampleProductBean> get_sample_item(String subCompanyId) throws Exception;

//	Map<String, Object> saveArticle_Scheme_master(List<trd_scheme_mst_hdr> scheme_mst_hdrs) throws Exception;

	long getId() throws Exception;

	String get_SaleProd_Code(String string) throws Exception;

	String get_Article_Prod_Code(String string) throws Exception ;

	String get_Subcompany_Code(String string) throws Exception;

//	List<Article_bean_for_modify> get_approval_date(String employeeId) throws Exception;

	Boolean deleteScheme(String trs_sl_no) throws Exception;

	Map<String, Object> update(Article_save_as_bean bean, String article_Prod_Code, HttpServletRequest request, List<MultipartFile> files) throws Exception;

	Boolean confirm_approval(String stringSlnForQuery);

	List<ArticleDocsBean> get_Docs_Details(String schemeslno) ;

	Boolean deleteDoc(String docId, String dataMode) throws Exception;

	 trd_scheme_mst_hdr getTrdSchemeMasterById(long l) ;

	boolean releasedLockedProduct(String userId);

	List<SchemeMasterSampleProductBean> search_Article_Product_By_Name(String search_value, String subcompanyId) throws Exception;

	void unlock_article_product(String userId);

	List<SchemeMasterSampleProductBean> get_top_Article_product(String subCompanyId) throws Exception;

	trd_scheme_mst_hdr getTrdSchemeMasterByIdForApproval(long l);

	List<ArticleSchemeMasterSALEPRODData> get_top_sale_product();

	List<SamplProdGroup> get_brands(String sales_product_division, String scheme_division);

	List<ArticleSchemeMasterSALEPRODData> get_saleProduct(String brandValues, long subcompanyId);

	List<trd_scheme_mst_dtl> get_Detail_Data_For_Edit(String trd_SCH_SLNO);

	List<trd_scheme_mst_hdr> get_HDR_Data_For_Edit(String trd_SCH_SLNO);

	List<ArticleSchemeMasterSALEPRODData> getSaleProductById(String sales_prod_id);

	List<SchemeDetailDataBean> getTrdSchemeDetailById(Long valueOf) throws ParseException;

	boolean check_If_The_Product_Already_Loaked(String userId, String articleId);

	List<String> get_Cclist(List<trd_scheme_mst_hdr> headerData);

	List<Artreq_To_Dispatch_Report_With_Param> getArticleSchemeReport(ArticleSchemeReportBean bean) throws Exception;

	List<Artreq_To_Dispatch_Report_With_Param> getArticleSchemeDirectToStokistReport(ArticleSchemeReportBean bean);

	List<Scheme_Desription_Bean> getArticleSchemeDescriptionReport(ArticleSchemeReportBean bean);

	List<Article_bean_for_modify> get_approval_data(String employeeId) throws Exception;

	Map<String, Long> getArticleGraphData(String emp_id, String emp_loc, String sub_company_code) throws Exception;

	List<DivMaster> get_Scheme_Division() throws Exception;
	
	List<trd_scheme_mst_hdr> get_Header_Scheme_By_Scheme_code(String schemecode) throws Exception;

	List<FinYear> getfinyear();

	List<String> get_ToList(List<trd_scheme_mst_hdr> headerData);

	List<trd_scheme_mst_hdr> get_HDR_Data_For_Validity_Extension(String empId);
	
	List<TRD_SCH_MST_DOCS> getDocsBySchemeSlno(Long trd_sch_slno) throws Exception;
	
	void deleteDocBySchAndSlno(Long sch_slno , Long doc_slno) throws Exception;

	List<Art_Sch_Vld_Ext> getEnteredExtensions(String empId) throws Exception;


	String getEmployeeDetailsByEmployeeID(String approver);

	List<String> getEmployeeEmail(String created_by);

	List<Articele_Scheme_Approved_Data> getApprovedData(String schemeDivId, String tranType, Long trd_SCH_SLNO);
	
	Integer getMaxApprLevelByTranTypeAndInvGrpId(String tran_type,Long inv_grp_id) throws Exception;

	void unlock_all_article_product();

	List<ArticleDocsBean> get_Docs_Details_For_EXT(String string);

	void deleteDocBySchemeSlnumber(Long scheme_slno) throws Exception;

	List<ARTICLE_SCH_VALID_EXT_DOCS> getarticleEsxTendedSchemeDocsData(Long slno) throws Exception;

	Long getSchemeCodeBySchemeExId(String schemeslno) throws Exception;

	List<trd_scheme_mst_hdr> get_all_scheme() throws Exception;

	Long get_Cycle_for_Scheme_Master(Long trd_slno) throws Exception;
	
	

	List<Articel_Scheme_Approved_Data_Bean> generateArticleSchemeDataHeadr(Long trd_slno, Long cycle) throws Exception;

	List<Articel_Scheme_Approved_Data_Bean> generateArticleSchemeData_Detail(Long trd_slno) throws Exception;

	List<Articel_Scheme_Approved_Data_Bean> generateArticleSchemeData_Docs(Long trd_slno, Long cycle) throws Exception;

	List<Article_Scheme_Extent_Bean> generateArticleSchemeEXTHeader(Long trd_slno);

	List<Article_Scheme_Extent_Bean> generateArticleSchemeEXT_Doc(Long trd_slno);

	String get_Article_Prod_Name(String article_PROD_ID);

	String get_sale_prod_name(String sale_PROD_ID);
	boolean CheckFileExistForTheScheme(String trs_sl_no) throws Exception ;

	List<trd_scheme_mst_hdr> get_scheme_for_closure(String empId);
	
	List<trd_scheme_mst_hdr> getAssociatedSchemsByEmpId(String empId) throws Exception;

	List<New_Stockist_wiseScheme_request_model> getArticleSchemeDirectToStockistReportNewHeaderData(
			ArticleSchemeReportBean bean) throws Exception;

	List<New_stockist_wise_scheme_request_supply_Detail_model> getArticleSchemeDirectToStockistReportnew_DetailData(
			String allocdtl_id, String article_prod_id);

	List<New_stockist_wise_scheme_request_supply_Detail_model> getDispatchDetailData();
}

