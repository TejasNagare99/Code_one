package com.excel.restcontroller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.hc.core5.http.HttpRequest;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.excel.bean.ArticleSchemMasterCompanyData;
import com.excel.bean.ArticleSchemeMasterSALEPRODData;
import com.excel.bean.ArticleSchemeReportBean;
import com.excel.bean.Article_bean_for_modify;
import com.excel.bean.Article_save_as_bean;
import com.excel.bean.SchemeMasterSampleProductBean;
import com.excel.bean.SkuQtySalesProductBean;
import com.excel.bean.SpecificLocationBean;
import com.excel.bean.Validity_ext_bean;
import com.excel.configuration.HttpSessionInterceptor;
import com.excel.configuration.JwtProvider;
import com.excel.model.Art_Sch_Vld_Ext;
import com.excel.model.Artreq_To_Dispatch_Report_With_Param;
import com.excel.model.Company;
import com.excel.model.DivMaster;
import com.excel.model.LoginDetails;
import com.excel.model.New_Stockist_wiseScheme_request_model;
import com.excel.model.New_stockist_wise_scheme_request_supply_Detail_model;
import com.excel.model.SamplProdGroup;
import com.excel.model.Scheme_Desription_Bean;
import com.excel.model.TRD_SCH_MST_DOCS;
import com.excel.model.trd_scheme_mst_hdr;
import com.excel.repository.Article_Scheme_master_Repository;
import com.excel.service.Article_Scheme_master_Service;
import com.excel.service.CompanyMasterService;
import com.excel.service.UserMasterService;
import com.excel.utility.MedicoConstants;
import com.fasterxml.jackson.databind.ObjectMapper;

@RestController
@RequestMapping("/rest")
public class Article_Scheme_Master_RestController implements MedicoConstants {

	@Autowired
	private Article_Scheme_master_Service article_Scheme_master_Service;
	@Autowired
	private UserMasterService userMasterService;
	@Autowired
	private Article_Scheme_master_Repository article_Scheme_master_Repository;
	@Autowired
	private JwtProvider tokenProvider;
	@Autowired
	private CompanyMasterService companyMasterService;
	private static final Logger logger = LogManager.getLogger(HttpSessionInterceptor.class);

	@GetMapping("/get-data-for-article-scheme-master")
	public Map<String, Object> get_data_for_article_scheme_master() {

		Map<String, Object> map = new HashMap<>();
		List<ArticleSchemMasterCompanyData> companyData;
		try {
			companyData = article_Scheme_master_Service.getCompanyDataforSchemeMaster();

//		List<ArticleSchemeMasterSALEPRODData> SALEPRODUCT = article_Scheme_master_Service.get_SALEPROD_DataforSchemeMaster();
			map.put("CompanyData", companyData);
		} catch (Exception e) {
			logger.info("Error Occured During Getting Default Data For Article Scheme Master :" + e.getMessage());
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return map;
	}

	@GetMapping("/get-specfic-location-for-article-scheme-master")
	public Map<String, Object> get_specfic_location_for_article_scheme_master(
			@RequestParam("subCompanyId") String subCompanyId) {
		Map<String, Object> map = new HashMap<>();
		List<SpecificLocationBean> beans;
		try {
			beans = article_Scheme_master_Service.getSpecificLocation(subCompanyId);

			map.put("specificLocations", beans);
		} catch (Exception e) {
			// TODO Auto-generated catch block

			logger.info("Error Occured During Getting Specific Location For Article Scheme Master :" + e.getMessage());
			e.printStackTrace();
		}
		return map;

	}

	@GetMapping("/get-sample-item")
	public Map<String, List<SchemeMasterSampleProductBean>> get_sample_item(
			@RequestParam("subCompanyId") String subCompanyId) {
		Map<String, List<SchemeMasterSampleProductBean>> map = new HashMap<>();
		List<SchemeMasterSampleProductBean> beans;
		try {
			beans = article_Scheme_master_Service.get_sample_item(subCompanyId);

			System.out.println(beans);
			map.put("sample_item", beans);
		} catch (Exception e) {
			logger.info("Error Occured During Getting Article Products For Article Scheme Master :" + e.getMessage());
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return map;

	}

	@PostMapping(value = "/save-article-master", consumes = { MediaType.APPLICATION_JSON_VALUE,
			MediaType.MULTIPART_FORM_DATA_VALUE, MediaType.APPLICATION_OCTET_STREAM_VALUE })
	public Map<String, Object> save_article_master(@RequestPart("bean") Article_save_as_bean bean,
			@RequestPart("files") List<MultipartFile> files, HttpServletRequest request) {
		String empId = null;
		logger.info("Article Scheme Bean For Saveing = " + bean);
		try {
			 empId = this.userMasterService.getEmployeeIdFromRequest(request);
		} catch (Exception e) {
			e.printStackTrace();
		}
	
		bean.setCreated_by(empId);
		Map<String, Object> map = new HashMap<>();

		ObjectMapper objectMapper = new ObjectMapper();
		SkuQtySalesProductBean bean_obj = new SkuQtySalesProductBean();

		List<SkuQtySalesProductBean> arrayOfSalesProdbeans = new ArrayList<>();

//		System.out.println(bean);
//		System.out.println(files);
//		System.out.println(bean.getSales_prod_details());

		try {
			if (bean.getScheme_code() == null) {
				System.out.println("null");
				map = article_Scheme_master_Service.saveArticle_Scheme_master(bean, request, files);
			} else {
				System.out.println(" not null");
				map = article_Scheme_master_Service.update(bean, request, files);
			}

		} catch (Exception e) {
			e.printStackTrace();
			logger.info("Error Occured During Saveing  Scheme  For Article Scheme Master :" + e.getMessage());
		}
		return map;
	}

	@GetMapping("/get-approval-data")
	public Map<String, Object> get_approval_data( HttpServletRequest request) {
		Map<String, Object> map = new HashMap<>();
//		System.err.println(employeeId);
		List<Article_bean_for_modify> beans;
		try {
			
			String empId = this.userMasterService.getEmployeeIdFromRequest(request);
			beans = article_Scheme_master_Service.get_approval_data(empId);

			map.put("beans", beans);
		} catch (Exception e) {
			logger.info("Error Occured During Getting Approval Data  For Article Scheme Master :" + e.getMessage());
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return map;
	}

	@PostMapping("/delete-scheme/{trs_sl_no}")
	public Map<String, Object> deleteScheme(@PathVariable("trs_sl_no") String trs_sl_no) {

		Map<String, Object> map = new HashMap<>();
//		System.err.println(trs_sl_no);

		Boolean deleted = false;
		try {
			deleted = article_Scheme_master_Service.deleteScheme(trs_sl_no);
			map.put("deleted", deleted);
		} catch (Exception e) {

			map.put("deleted", false);

			logger.info("Error Occured During Deleting Scheme  For Article Scheme Master :" + e.getMessage());
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		System.out.println(deleted);

		return map;
	}

//	@PostMapping("/delete-doc/{docId}")
//	public Map<String, Object> delete_doc(@PathVariable("docId") String docId) {
//
//		Map<String, Object> map = new HashMap<>();
////		System.err.println(docId);
//
//		Boolean deleted = false;
//		try {
//			deleted = article_Scheme_master_Service.deleteDoc(docId);
//			map.put("deleted", deleted);
//		} catch (Exception e) {
//
//			logger.info("Error Occured During Deleting Documents  For Article Scheme Master :" + e.getMessage());
//			map.put("deleted", false);
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//
//		System.out.println(deleted);
//
//		return map;
//	}

	@PostMapping("/delete-doc")
	public Map<String, Object> delete_doc(@RequestParam String docId, @RequestParam String dataMode) {

		Map<String, Object> map = new HashMap<>();
		System.err.println(docId);
		System.err.println(dataMode);

		Boolean deleted = false;
		try {
			deleted = article_Scheme_master_Service.deleteDoc(docId, dataMode);
			map.put("deleted", deleted);
		} catch (Exception e) {

			logger.info("Error Occured During Deleting Documents  For Article Scheme Master :" + e.getMessage());
			map.put("deleted", false);
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		System.out.println(deleted);

		return map;
	}

	@PostMapping("/confirm-approval")
	public Map<String, Object> confirm_approval(@RequestParam("arrayOfslNumber") List<Long> arrayOfslNumber,
			HttpServletRequest request, HttpSession session) throws Exception {
		System.err.println(arrayOfslNumber);
		Map<String, Object> map = new HashMap<String, Object>();
		Boolean updated = false;
		try {
			
			String empId = this.userMasterService.getEmployeeIdFromRequest(request);
			
			map = article_Scheme_master_Service.confirm_approval(arrayOfslNumber, session, request, empId);
			updated = true;
			map.put("updated", updated);

		} catch (Exception e) {
			updated = false;
			map.put("updated", updated);
			e.printStackTrace();

		}

		return map;
	}

//	@GetMapping("/get-docs-data")
//	public Map<String, Object> get_Docs_Details(@RequestParam("schemeslno") String schemeslno,@RequestParam("trantype") String trantype) {
//
//		Map<String, Object> map = new HashMap<>();
//		try {
//			map = article_Scheme_master_Service.get_Docs_Details(schemeslno,trantype);
//		} catch (Exception e) {
//			logger.info("Error Occured During getting Documents  For Article Scheme Master :" + e.getMessage());
//			e.printStackTrace();
//		}
//
//		return map;
//	}

	@GetMapping("/get-docs-data")
	public Map<String, Object> get_Docs_Details(@RequestParam("schemeslno") String schemeslno,
			@RequestParam("trantype") String trantype, @RequestParam("dataMode") String dataMode) {

		System.err.println("dataMode::" + dataMode + "::::schemeslno" + schemeslno);

		Map<String, Object> map = new HashMap<>();
		try {
			map = article_Scheme_master_Service.get_Docs_Details(schemeslno, trantype, dataMode);
		} catch (Exception e) {
			logger.info("Error Occured During getting Documents  For Article Scheme Master :" + e.getMessage());
			e.printStackTrace();
		}

		return map;
	}

	@PostMapping("/download-zip")
	public Map<String, Object> downloadAsZipFile(@RequestBody List<String> ArrayOfFilesToZip) {

		String zippedFileName = "";
		Map<String, Object> map = new HashMap<>();
//		System.out.println(ArrayOfFilesToZip);
		try {
			zippedFileName = article_Scheme_master_Service.downloadAsZipFile(ArrayOfFilesToZip);
		} catch (Exception e) {

			logger.info("Error Occured During Downloading-Zip File For Article Scheme Master :" + e.getMessage());
			e.printStackTrace();
			map.put("FileName", "N");
			return map;
		}
//	 System.out.println(zippedFileName);
		map.put("FileName", zippedFileName);
		return map;
	}

	@PostMapping("/lock-article-product")
	public Map<String, Object> lock_article_product(@RequestParam("articleId") String articleId,
		HttpServletRequest request ){
		Map<String, Object> map = new HashMap<>();

		try {

			
			String empId = this.userMasterService.getEmployeeIdFromRequest(request);
			map = this.article_Scheme_master_Service.lock_article_product(articleId, empId);

		} catch (Exception e) {

			logger.info("Error Occured During Locking Article Product For Article Scheme Master :" + e.getMessage());
			e.printStackTrace();
		}

		return map;
	}

	@PostMapping("/search-article-product")
	public List<SchemeMasterSampleProductBean> search_Article_Product_By_Name(
			@RequestParam("search_value") String search_value, @RequestParam("subcompanyId") String subcompanyId) {

		List<SchemeMasterSampleProductBean> beans = new ArrayList<>();
//		System.out.println(search_value+":::::::"+subcompanyId);
		try {
			beans = this.article_Scheme_master_Service.search_Article_Product_By_Name(search_value, subcompanyId);
		} catch (Exception e) {
//			logger.info("Error Occured During Locking Article Product For Article Scheme Master :"+e.getMessage());
			e.printStackTrace();
		}

		return beans;
	}

	@PostMapping("/unlock-article-product")
	public void unlock_article_product(	HttpServletRequest request,HttpSession session) {

//		System.out.println(userId);
		try {

			Company company = (Company) session.getServletContext().getAttribute(COMPANY_DATA);
			String empId = this.userMasterService.getEmployeeIdFromRequest(request);
			
			if (company.getCfa_to_stk_ind().equals("Y")) {
				this.article_Scheme_master_Service.unlock_article_product(empId);
			}
		
		} catch (Exception e) {
			// TODO: handle exception
			logger.info("Error Occured During un-Locking Article Product For Article Scheme Master :" + e.getMessage());
			e.printStackTrace();
		}

	}

	@PostMapping("/get-article-top-product")
	public List<SchemeMasterSampleProductBean> get_top_Article_product(
			@RequestParam("subCompanyId") String subCompanyId) {

//		System.out.println(subCompanyId);
		List<SchemeMasterSampleProductBean> beans = new ArrayList<>();
		try {
			beans = this.article_Scheme_master_Service.get_top_Article_product(subCompanyId);
		} catch (Exception e) {

			logger.info("Error Occured During Get-Top Article Product For Article Scheme Master :" + e.getMessage());
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return beans;

	}

	@GetMapping("/get-topsales-product")
	public List<ArticleSchemeMasterSALEPRODData> get_top_sale_product() {
		List<ArticleSchemeMasterSALEPRODData> beans = new ArrayList<>();
		try {
			beans = article_Scheme_master_Service.get_top_sale_product();
		} catch (Exception e) {
			logger.info("Error Occured During Get-Top sale Product For Article Scheme Master :" + e.getMessage());
			// TODO: handle exception
			e.printStackTrace();
		}

		return beans;
	};

	@GetMapping("/get-brands")
	public List<SamplProdGroup> get_brands(@RequestParam("Sales_product_division") String Sales_product_division,
			@RequestParam("Scheme_division") String Scheme_division) {
		List<SamplProdGroup> beans = new ArrayList<>();
		System.err.println(Sales_product_division + "::::" + Scheme_division);
		try {
			beans = article_Scheme_master_Service.get_brands(Sales_product_division, Scheme_division);
		} catch (Exception e) {
			logger.info("Error Occured During Getting Brands For Article Scheme Master :" + e.getMessage());
			e.printStackTrace();
		}

		return beans;
	};

	@GetMapping("/get-saleProduct")
	public List<ArticleSchemeMasterSALEPRODData> get_saleProduct(
			@RequestParam("arrayOfbrandSelectedValues") List<Long> arrayOfbrandSelectedValues,
			@RequestParam("subcompanyId") long subcompanyId) {
		List<ArticleSchemeMasterSALEPRODData> beans = new ArrayList<>();
		System.out.println(arrayOfbrandSelectedValues);
		System.out.println(subcompanyId);
		try {
			beans = article_Scheme_master_Service.get_saleProduct(arrayOfbrandSelectedValues, subcompanyId);
		} catch (Exception e) {
			logger.info("Error Occured During Getting Sales Product In Article Scheme Master :" + e.getMessage());
			e.printStackTrace();
		}

		return beans;
	};

	@GetMapping("/get-Detail-Data-For-Edit")
	public Map<String, Object> get_Detail_Data_For_Edit(@RequestParam("trd_SCH_SLNO") String trd_SCH_SLNO)
			throws Exception {
		Map<String, Object> map = new HashMap<>();
		System.out.println(trd_SCH_SLNO);
		try {
			map = this.article_Scheme_master_Service.get_Detail_Data_For_Edit(trd_SCH_SLNO);
		} catch (Exception e) {
			logger.info("Error Occured During Getting  Data For Edit In Article Scheme Master :" + e.getMessage());

			e.printStackTrace();
			// TODO: handle exception
		}
		return map;

	};

	@PostMapping("/delete-article-product-by-id")
	public boolean deleteProductByUserId(	HttpServletRequest request) {

		
		try {
			String empId = this.userMasterService.getEmployeeIdFromRequest(request);
			this.article_Scheme_master_Service.delete_Article_Product_On_The_Product_Locking_Table(empId);
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			logger.info("Error Occured During Removing locked Product  Data For Edit In Article Scheme Master :"
					+ e.getMessage());
		}

		return true;
	}

//	
//	@GetMapping("/getMail")
//	public void generateMail() {
//		
//		System.out.println("calling");
//		try {
//			this.article_Scheme_master_Service.send_Mail_for_cfa_location("1654");
//		} catch (Exception e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//		
//	}

	@PostMapping("/generate-article-scheme-report")
	public Map<String, Object> generateArticleSchemeReport(@RequestBody ArticleSchemeReportBean bean,
			HttpSession session) {
		System.out.println(bean);

		logger.info("ArticleSchemeReportBean : " + bean);
		List<Artreq_To_Dispatch_Report_With_Param> list = null;
		Map<String, Object> map = new HashMap<>();
		List<Scheme_Desription_Bean> ListOfSchemDescription = null;
		String filename = null;
		try {
			list = article_Scheme_master_Service.getArticleSchemeReport(bean);
			ListOfSchemDescription = article_Scheme_master_Service.getSchemeDescription(bean);
			if (bean.getInd().equals("report") && list != null && list.size() != 0) {
				filename = article_Scheme_master_Service.generateArticleSchemeReport(list, bean, session,
						ListOfSchemDescription);

				map.put("filename", filename);
			} else if (bean.getInd().equals("params") && list != null && list.size() != 0) {

				map.put("list", list);
			}

		} catch (Exception e) {
			logger.info("Error Occured During generating ArticleScheme Report For Article Scheme Master :"
					+ e.getMessage());
			e.printStackTrace();

		}
		return map;
	}

	@PostMapping("/generate-article-scheme-Direct-To-Stockist-report")
	public Map<String, Object> generateArticleSchemeDirectToStockistReport(@RequestBody ArticleSchemeReportBean bean,
			HttpSession session) {
		Map<String, Object> map = new HashMap<>();
		logger.info("ArticleSchemeReportBean : " + bean);
		System.out.println(bean);
		List<Scheme_Desription_Bean> ListOfSchemDescription = null;
//		List<Artreq_To_Dispatch_Report_With_Param> sorted_list = new ArrayList<Artreq_To_Dispatch_Report_With_Param>();
		List<New_Stockist_wiseScheme_request_model> list = null;
		List<New_stockist_wise_scheme_request_supply_Detail_model> detaillist = null;
		List<Artreq_To_Dispatch_Report_With_Param> 	 sortList=null;
		String filename = null;
		try {

			
			list=article_Scheme_master_Service.getArticleSchemeDirectToStockistReportNewHeaderData(bean);
			
				detaillist=article_Scheme_master_Service.getDispatchDetailData();
			System.err.println("list::"+list.size());
			System.err.println("detaillist::"+detaillist.size());
			
			ListOfSchemDescription = article_Scheme_master_Service.getSchemeDescription(bean);
			if (bean.getInd().equals("report") && list != null && list.size() != 0) {
				
				
				
				filename = article_Scheme_master_Service.generateArticleSchemeReportHeadernew(list, bean, session,
						ListOfSchemDescription,detaillist);

				map.put("filename", filename);
		
				sortList=article_Scheme_master_Service.sortList(list, detaillist,bean);
				
				System.out.println(filename);
				map.put("sorted_list", sortList);

			} else if (bean.getInd().equals("params") && list != null && list.size() != 0) {

				map.put("list", list);
			}
		} catch (Exception e) {
			e.printStackTrace();
			logger.info(
					"Error Occured During generating ArticleScheme Direct to Stockist  Report For Article Scheme Master :"
							+ e.getMessage());

		}
		return map;
	}

	
	
	
	
	
	
	
	@GetMapping("/get-article-graph-data")
	public Map<String, Long> getArticleGraphData(HttpServletRequest request) {
		Map<String, Long> map = new HashMap<>();
		try {
			String uname = this.tokenProvider.getUsernameFromRequest(request);
			String empId = this.userMasterService.getEmployeeIdFromRequest(request);
			// get hrm-employee object
			LoginDetails lg_dtls = this.userMasterService.getLoginDetailsByUserName(uname);
			map = this.article_Scheme_master_Service.getArticleGraphData(empId, lg_dtls.getEmp_loc_id().toString(),
					lg_dtls.getLoc_SubComp_id().toString());
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return map;
	}

	// /get-Scheme-Division
	@GetMapping("/get-Scheme-Division")
	public ResponseEntity<List<DivMaster>> get_Scheme_Division() {
		List<DivMaster> divisions = new ArrayList<>();
		try {
			divisions = this.article_Scheme_master_Service.get_Scheme_Division();
			return ResponseEntity.ok(divisions);
		} catch (Exception e) {
			e.printStackTrace();
			return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body(null);
		}
	}

	@GetMapping("/get-schemes-for-validity-extension")
	public ResponseEntity<Map<String, Object>> getSchemesForValidityExtension(	HttpServletRequest request) {
		Map<String, Object> map = new HashMap<String, Object>();
		try {
			
			String empId = this.userMasterService.getEmployeeIdFromRequest(request);
			
			map.put("entryList", this.article_Scheme_master_Service.get_HDR_Data_For_Validity_Extension(empId));
			map.put("modifyList", this.article_Scheme_master_Service.getEnteredExtensions(empId));
			return ResponseEntity.ok(map);
		} catch (Exception e) {
			e.printStackTrace();
			return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body(null);
		}
	}

//	@PostMapping(value = "/save-ext-data", consumes = { MediaType.APPLICATION_JSON_VALUE,
//			MediaType.MULTIPART_FORM_DATA_VALUE, MediaType.APPLICATION_OCTET_STREAM_VALUE })
//	public ResponseEntity<Void> saveExtension(@RequestPart("bean") Validity_ext_bean bean,
//			@RequestPart("file") MultipartFile file, HttpServletRequest request, HttpSession session) {
//		try {
//			System.out.println(bean.toString());
//			
//			this.article_Scheme_master_Service.saveValidityExtension(bean, file, request, session);
//			
//			return ResponseEntity.ok(null);
//		} catch (Exception e) {
//			e.printStackTrace();
//			return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).build();
//		}
//	}

	@PostMapping(value = "/save-ext-data", consumes = { MediaType.APPLICATION_JSON_VALUE,
			MediaType.MULTIPART_FORM_DATA_VALUE, MediaType.APPLICATION_OCTET_STREAM_VALUE })
	public ResponseEntity<Void> saveExtension(@RequestPart("bean") Validity_ext_bean bean,
			@RequestPart("files") List<MultipartFile> files, HttpServletRequest request, HttpSession session) {
//		@RequestPart("files") List<MultipartFile> files, 
		try {
			String empId = this.userMasterService.getEmployeeIdFromRequest(request);
			bean.setEmpId(empId);
			
			System.out.println(bean.toString());
			System.out.println("files ---->" + files.size());

			this.article_Scheme_master_Service.saveValidityExtension(bean, files, request, session);
			
			return ResponseEntity.ok(null);
		} catch (Exception e) {
			e.printStackTrace();
			return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).build();
		}
	}

	@GetMapping("/get-docs-by-scheme")
	public ResponseEntity<List<TRD_SCH_MST_DOCS>> getDocsByScheme(@RequestParam Long sch_slno) {
		try {
			return ResponseEntity.ok(this.article_Scheme_master_Service.getDocsBySchemeSlno(sch_slno));
		} catch (Exception e) {
			e.printStackTrace();
			return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).build();
		}
	}

	@GetMapping("/get-vld-ext-data")
	public ResponseEntity<Art_Sch_Vld_Ext> getDataBySchSlno(@RequestParam Long vldExtSlno) {
		try {
			return ResponseEntity.ok(this.article_Scheme_master_Service.getVldExtenById(vldExtSlno));
		} catch (Exception e) {
			e.printStackTrace();
			return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).build();
		}
	}

//	@GetMapping("/retrigger-mails")
//	public void retrigger_approvalMails(@RequestParam String comp_cd, @RequestParam Long tranid,
//			@RequestParam String nextApprover) throws Exception {
//		List<trd_scheme_mst_hdr> hdrList = this.article_Scheme_master_Repository
//				.get_HDR_Data_For_Edit(tranid.toString());
//		System.out.println(hdrList.get(0));
//		article_Scheme_master_Service.sendmails(hdrList.get(0), SCM_MST_APPR, null, "nextApproval", comp_cd,
//				nextApprover, "", false, null);
//	}

	@GetMapping("/get-all-approved-scheme")
	public ResponseEntity<List<trd_scheme_mst_hdr>> get_all_scheme() {

		List<trd_scheme_mst_hdr> schemes = new ArrayList<>();
		try {
			schemes = this.article_Scheme_master_Service.get_all_scheme();
			return ResponseEntity.ok(schemes);
		} catch (Exception e) {
			e.printStackTrace();
			return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).build();
		}
	}

	@GetMapping("/gererate-schm-ext-report")
	public ResponseEntity<Map<String, Object>> generateArticleSchemeReportAndExtention(
			@RequestParam("trd_slno") Long trd_slno, HttpSession session) {

		Map<String, Object> map = new HashMap<>();
		try {
			Company company = companyMasterService.getCompanyDetails();

			
			map = this.article_Scheme_master_Service.generateArticleSchemeReportAndExtention(trd_slno, company.getWebsite());
			return ResponseEntity.ok(map);
		} catch (Exception e) {
			e.printStackTrace();
			return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).build();
		}
	}

	@GetMapping("/get-scheme-for-closure")
	public ResponseEntity<Map<String, Object>> get_scheme_for_closure(HttpServletRequest request) {

		Map<String, Object> map = new HashMap<>();
		List<trd_scheme_mst_hdr> schemeList = null;
		try {
			String empId = this.userMasterService.getEmployeeIdFromRequest(request);
			schemeList = this.article_Scheme_master_Service.get_scheme_for_closure(empId);

			map.put("schemeList", schemeList);
			return ResponseEntity.ok(map);
		} catch (Exception e) {
			e.printStackTrace();
			return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).build();
		}
	}

//	save-closure-date

	@PostMapping("/save-closure-date")
	public ResponseEntity<Map<String, Object>> save_closure_date(@RequestParam("closure_date") String closure_date,
			@RequestParam("trs_sl_no") String trs_sl_no) {

		Map<String, Object> map = new HashMap<>();

		System.err.println(closure_date + "::::::" + trs_sl_no);
		try {

			this.article_Scheme_master_Service.saveClosure_Date(trs_sl_no, closure_date);

			map.put("saved", true);
			return ResponseEntity.ok(map);
		} catch (Exception e) {
			e.printStackTrace();
			return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).build();
		}
	}

	@GetMapping("/get-schemes-by-empId-creator-approver")
	public ResponseEntity<List<trd_scheme_mst_hdr>> get_associated_schemes_by_empId(HttpServletRequest request)
			throws Exception {
		String empId = this.userMasterService.getEmployeeIdFromRequest(request);
		return ResponseEntity.ok(this.article_Scheme_master_Service.getAssociatedSchemsByEmpId(empId));
	}

}
