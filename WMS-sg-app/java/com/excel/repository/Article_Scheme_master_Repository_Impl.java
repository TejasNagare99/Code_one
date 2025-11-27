package com.excel.repository;

import java.math.BigDecimal;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.StoredProcedureQuery;
import javax.persistence.Tuple;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
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
import com.excel.bean.Article_save_as_bean.SalesProdDetails;
import com.excel.bean.SchemeDetailDataBean;
import com.excel.bean.SchemeMasterSampleProductBean;
import com.excel.bean.SpecificLocationBean;
import com.excel.model.ARTICLE_SCH_VALID_EXT_DOCS;
import com.excel.model.Art_Sch_Vld_Ext;
import com.excel.model.Article_Graph_data_model;
import com.excel.model.Artreq_To_Dispatch_Report_With_Param;
import com.excel.model.DivMaster;
import com.excel.model.FinYear;
import com.excel.model.New_Stockist_wiseScheme_request_model;
import com.excel.model.New_stockist_wise_scheme_request_supply_Detail_model;
import com.excel.model.SCM_MST_PROD_LOCK;
import com.excel.model.SamplProdGroup;
import com.excel.model.Scheme_Desription_Bean;
import com.excel.model.TRD_SCH_MST_DOCS;
import com.excel.model.trd_scheme_mst_dtl;
import com.excel.model.trd_scheme_mst_hdr;
import com.excel.utility.MedicoConstants;

@Repository
public class Article_Scheme_master_Repository_Impl implements Article_Scheme_master_Repository {

	@Autowired
	EntityManagerFactory emf;

	@PersistenceContext
	private EntityManager entityManager;
	@Autowired
	TransactionalRepository transactionalRepository;

	@Override
	public List<ArticleSchemMasterCompanyData> getCompanyDataForArticleSchemeMaster() throws Exception {
		List<ArticleSchemMasterCompanyData> beans = new ArrayList<>();
		Map<String, Object> map = new HashMap<>();

		Query query = null;

//			String q = "SELECT SubComp_id as SubComp_id,SubComp_Nm  as SubComp_Nm  FROM Sub_Company WHERE SubComp_status = 'A'";

		String q = " SELECT  SubComp_id as SubComp_id,SubComp_Nm  as SubComp_Nm, SubComp_code as  SubComp_code   FROM Sub_Company WHERE SubComp_status = 'A'";

		query = entityManager.createNativeQuery(q, Tuple.class);
		List<Tuple> tuples = query.getResultList();

		tuples.forEach(l -> {
			ArticleSchemMasterCompanyData bean = new ArticleSchemMasterCompanyData();

			bean.setSubComp_id(l.get("SubComp_id").toString());
			bean.setSubComp_Nm(l.get("SubComp_Nm").toString());
			bean.setSubComp_code(l.get("SubComp_code").toString().trim());

			beans.add(bean);
		});

		System.err.println(map.get("CompanyData"));

		return beans;
	}

	@Override
	public List<ArticleSchemeMasterSALEPRODData> getSaleProductDataForArticleSchemeMaster() {

		List<ArticleSchemeMasterSALEPRODData> beans = new ArrayList<>();
		try {

			Query query = null;

			String q = "select SALE_PROD_ID as SALE_PROD_ID,  SALE_PROD_NAME as SALE_PROD_NAME,SALE_PROD_CD as SALE_PROD_CD from SALEPROD WHERE SALE_STATUS = 'A'";
			query = entityManager.createNativeQuery(q, Tuple.class);
			List<Tuple> tuples = query.getResultList();

			tuples.forEach(l -> {

				ArticleSchemeMasterSALEPRODData bean = new ArticleSchemeMasterSALEPRODData();

				bean.setSALE_PROD_ID(l.get("SALE_PROD_ID").toString());
				bean.setSALE_PROD_NAME(l.get("SALE_PROD_NAME").toString().trim());
				bean.setSALE_PROD_CD(l.get("SALE_PROD_CD").toString().trim());

				beans.add(bean);
			});

			System.out.println(beans);

		} catch (Exception e) {
			e.printStackTrace();
		}

		return beans;
	}

	@Override
	public List<SpecificLocationBean> getSpecificLocation(String subCompanyId) throws Exception {

		List<SpecificLocationBean> beans = new ArrayList<>();

		Query query = null;
//		String q = "select loc_id as loc_id ,loc_nm  as loc_nm  from location where loc_status = 'A' and loc_SubComp_id =:subCompanyId order by loc_nm ";

		String q = "select concat(concat(dl.DPTDESTINATION,' - '),loc_nm) as loc_nm, loc_id as loc_id   from location loc\r\n"
				+ "inner join DEPOT_LOCATIONS dl on loc.LOC_LINK_DPTLOC_ID = dl.DPTLOC_ID\r\n"
				+ "where loc_status = 'A'\r\n" + "and loc_SubComp_id =:subCompanyId order by loc_nm ";
		query = entityManager.createNativeQuery(q, Tuple.class);

		query.setParameter("subCompanyId", subCompanyId);
		List<Tuple> tuples = query.getResultList();

		tuples.forEach(l -> {

			SpecificLocationBean bean = new SpecificLocationBean();
			bean.setLoc_id(l.get("loc_id").toString());
			bean.setLoc_nm(l.get("loc_nm").toString());

			beans.add(bean);

		});

		return beans;
	}

	@Override
	public List<SchemeMasterSampleProductBean> get_sample_item(String subCompanyId) throws Exception {

		List<SchemeMasterSampleProductBean> beans = new ArrayList<>();

		Query query = null;
//			String q = "	select SMP_PROD_ID as SMP_PROD_ID,SMP_PROD_NAME as SMP_PROD_NAME  from smpitem \r\n"
//					+ "		WHERE SMP_status = 'A' AND SMP_DISCONT_DT IS NULL\r\n"
//					+ "		AND SMP_SubComp_id =:subCompanyId \r\n" + "		order by smp_prod_name ";

//			String q="select SMP_PROD_ID as SMP_PROD_ID,SMP_PROD_NAME as SMP_PROD_NAME  ,SMP_ERP_PROD_CD as  SMP_ERP_PROD_CD from smpitem \r\n"
//					+ "		WHERE SMP_status = 'A' AND SMP_DISCONT_DT IS NULL\r\n"
//					+ "		AND SMP_SubComp_id=:subCompanyId \r\n"
//					+ "		order by smp_prod_name";

//		String q = "select SMP_PROD_ID as SMP_PROD_ID,SMP_PROD_NAME as SMP_PROD_NAME  ,SMP_ERP_PROD_CD as  SMP_ERP_PROD_CD ,SMP_RATE as SMP_RATE  from smpitem"
//				+ " WHERE SMP_status = 'A' AND SMP_DISCONT_DT IS NULL AND SMP_SubComp_id=:subCompanyId \r\n"
//				+ " order by smp_prod_name";

//		String q="  select SMP_PROD_ID as SMP_PROD_ID,SMP_PROD_NAME as SMP_PROD_NAME  ,SMP_ERP_PROD_CD as  SMP_ERP_PROD_CD ,SMP_RATE as SMP_RATE \r\n"
//				+ " from smpitem WHERE SMP_status = 'A' AND\r\n"
//				+ " SMP_DISCONT_DT IS NULL AND SMP_SubComp_id=:subCompanyId  \r\n"
//				+ " and smp_prod_id not in (select prod_id from SCM_MST_PROD_LOCK)\r\n"
//				+ " order by smp_prod_name ";

		String q = " select SMP_PROD_ID as SMP_PROD_ID,SMP_PROD_NAME as SMP_PROD_NAME  ,SMP_ERP_PROD_CD as  SMP_ERP_PROD_CD ,SMP_RATE as SMP_RATE \r\n"
				+ "				 from smpitem WHERE SMP_status = 'A'  \r\n"
				+ "				  AND SMP_SubComp_id=:subCompanyId\r\n" + "				order by smp_prod_name";

		query = entityManager.createNativeQuery(q, Tuple.class);
		query.setParameter("subCompanyId", subCompanyId);
		List<Tuple> tuples = query.getResultList();

		tuples.forEach(l -> {

			SchemeMasterSampleProductBean bean = new SchemeMasterSampleProductBean();
			bean.setSMP_PROD_ID(l.get("SMP_PROD_ID").toString());
			bean.setSMP_PROD_NAME(l.get("SMP_PROD_NAME").toString().trim());
			bean.setSMP_ERP_PROD_CD((l.get("SMP_ERP_PROD_CD") == null ? "" : l.get("SMP_ERP_PROD_CD").toString()));
			bean.setSMP_RATE(new BigDecimal(l.get("SMP_RATE").toString()));

			beans.add(bean);

		});

		return beans;
	}
//
//	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
//	@Override
//	public Map<String, Object> saveArticle_Scheme_master(List<trd_scheme_mst_hdr> scheme_mst_hdrs) throws Exception {
//		
//	List<String> scheme_code_List=new ArrayList<>() ;	
//	Map<String , Object> map =new HashMap<>();
//	String SavedSchemeCode="";
//	 Boolean Saved=false;
//		scheme_mst_hdrs.forEach(l->{
//		
//				transactionalRepository.persist(l);
//				scheme_code_List.add(l.getTrd_scheme_code());
//		
//		});
//		
//		Saved=true;
//		map.put("scheme_code_List", scheme_code_List);
//		map.put("Saved", Saved);
//		
//		return map;
//	}

	@Override
	public long getId() throws Exception {

		Integer sl_num = 0;

		Query query = null;
		String q = "select MAX(TRD_SCH_SLNO) from TRD_SCHEME_MST_HDR";

		query = entityManager.createNativeQuery(q);

		sl_num = (Integer) query.getSingleResult();

		if (sl_num == null || sl_num == 0) {
			sl_num = 0;
		}

		System.err.println(sl_num);

		return sl_num;
	}

	@Override
	public String get_SaleProd_Code(String sales_prod_id) throws Exception {

		String sale_prod_cd = "";

		Query query = null;
		String q = "select  SALE_PROD_CD from SALEPROD  WHERE  SALE_PROD_ID=" + sales_prod_id;

		query = entityManager.createNativeQuery(q);

		sale_prod_cd = (String) query.getSingleResult();

		System.err.println(sale_prod_cd);

		return sale_prod_cd;
	}

	@Override
	public String get_Article_Prod_Code(String article_id) {

		String SMP_PROD_CD = "";

		Query query = null;
		String q = "select SMP_PROD_CD from smpitem where SMP_PROD_ID=" + article_id;

		query = entityManager.createNativeQuery(q);

		SMP_PROD_CD = (String) query.getSingleResult();

		System.err.println(SMP_PROD_CD);

		return SMP_PROD_CD;
	}

	@Override
	public String get_Subcompany_Code(String sub_comany_id) throws Exception {

		String SubComp_code = "";

		Query query = null;
		String q = " SELECT  SubComp_code FROM Sub_Company WHERE  SubComp_id=" + sub_comany_id;

		query = entityManager.createNativeQuery(q);

		SubComp_code = (String) query.getSingleResult();

		System.err.println(SubComp_code);

		return SubComp_code;
	}

//	@Override
//	public List<Article_bean_for_modify> get_approval_date(String employeeId) throws Exception {
//
//		List<Article_bean_for_modify> beans = new ArrayList<>();
//
//		Query query = null;
////			String q=" SELECT  SubComp_code FROM Sub_Company WHERE  SubComp_id="+sub_comany_id;
//
////		String q = "SELECT S.TRD_SCH_SLNO,S.SCHEME_APPTYPE,ISNULL(L.LOC_NM,'ALL') LOCATION,S.TRD_SCHEME_ID,\r\n"
////				+ "S.TRD_SCHEME_CODE,S.TRD_SCHEME_NAME,S.TRD_SCHEME_DESCR,\r\n"
////				+ "CONVERT(DATE,VALID_FROM_DT) VALID_FROM_DT,CONVERT(DATE,VALID_TO_DT) VALID_TO_DT,\r\n"
////				+ "CASE WHEN S.TRD_SCH_TYPE = 'A' THEN 'Articles' ELSE 'Gift' END TRD_SCH_TYPE,S.INVOICE_TYPE,\r\n"
////				+ "S.SALE_PROD_CODE_SG,SL.SALE_PROD_NAME,\r\n"
////				+ "SL.SALE_MAP_CODE1 SALE_PROD_CODE_ERP,S.ARTICLE_PROD_CD,SMP.SMP_PROD_NAME ARTICLE_PROD_NAME,\r\n"
////				+ "S.ARTICLE_PROD_QTY,S.ARTICLE_PROD_RATE,S.PER_SALE_QTY_BILLED,S.PER_SALE_QTY_FREE,S.PER_SALE_QTY_TOT,S.SUB_COMP_ID,"
////				+ "s.SALE_PROD_ID,s.ARTICLE_PROD_ID,s.loc_id,s.REMARKS \r\n"
////				+ "FROM TRD_SCHEME_MST_HDR S LEFT OUTER JOIN location L ON L.loc_id = S.LOC_ID , SALEPROD SL , SMPITEM SMP\r\n"
////				+ "WHERE S.TRD_SCH_STATUS = 'E'\r\n" + "AND RTRIM(S.SALE_PROD_ID) = RTRIM(SL.SALE_PROD_ID)\r\n"
////				+ "AND RTRIM(S.ARTICLE_PROD_ID) = RTRIM(SMP.SMP_PROD_ID)\r\n" + "AND CREATED_BY ='" + employeeId
////				+ "'   order by  TRD_SCH_SLNO DESC";
//
////			String q="SELECT S.TRD_SCH_SLNO,S.SCHEME_APPTYPE,ISNULL(L.LOC_NM,'ALL') LOCATION,S.TRD_SCHEME_ID,\r\n"
////					+ "S.TRD_SCHEME_CODE,S.TRD_SCHEME_NAME,S.TRD_SCHEME_DESCR,\r\n"
////					+ "CONVERT(DATE,VALID_FROM_DT) VALID_FROM_DT,CONVERT(DATE,VALID_TO_DT) VALID_TO_DT,\r\n"
////					+ "CASE WHEN S.TRD_SCH_TYPE = 'A' THEN 'Articles' ELSE 'Gift' END TRD_SCH_TYPE,S.INVOICE_TYPE,\r\n"
////					+ "S.SALE_PROD_CODE_SG,SL.SALE_PROD_NAME,\r\n"
////					+ "SL.SALE_MAP_CODE1 SALE_PROD_CODE_ERP,S.ARTICLE_PROD_CD,SMP.SMP_PROD_NAME ARTICLE_PROD_NAME,\r\n"
////					+ "S.ARTICLE_PROD_QTY,S.ARTICLE_PROD_RATE,S.PER_SALE_QTY_BILLED,S.PER_SALE_QTY_FREE,S.PER_SALE_QTY_TOT,S.SUB_COMP_ID,"
////					+ "s.SALE_PROD_ID,s.ARTICLE_PROD_ID,s.loc_id\r\n"
////					+ "FROM TRD_SCHEME_MST_HDR S LEFT OUTER JOIN location L ON L.loc_id = S.LOC_ID , SALEPROD SL , SMPITEM SMP\r\n"
////					+ "WHERE RTRIM(S.SALE_PROD_ID) = RTRIM(SL.SALE_PROD_ID)\r\n"
////					+ "AND RTRIM(S.ARTICLE_PROD_ID) = RTRIM(SMP.SMP_PROD_ID)\r\n"
////					+ "AND CREATED_BY ='"+employeeId+"'";
//
//		String q = "\r\n" + "\r\n" + "SELECT S.TRD_SCH_SLNO,S.SCHEME_APPTYPE,ISNULL(L.LOC_NM,'ALL') LOCATION,\r\n"
//				+ "S.TRD_SCHEME_CODE,S.TRD_SCHEME_NAME,S.TRD_SCHEME_DESCR,CONVERT(DATE,S.VALID_FROM_DT) VALID_FROM_DT,\r\n"
//				+ "CONVERT(DATE,S.VALID_TO_DT) VALID_TO_DT,S.APPLY_TO,S.BILLED_VALUE,\r\n"
//				+ "CASE WHEN S.TRD_SCH_TYPE = 'A' THEN 'Articles' ELSE 'Gift' END TRD_SCH_TYPE,S.INVOICE_TYPE,\r\n"
//				+ "S.ARTICLE_PROD_ID,S.ARTICLE_PROD_CD,SMP.SMP_PROD_NAME ARTICLE_PROD_NAME,S.ARTICLE_PROD_RATE,S.ARTICLE_QTY_PER_TOT_VAL\r\n"
//				+ "FROM TRD_SCHEME_MST_HDR S\r\n"
//				+ "LEFT OUTER JOIN location L ON L.loc_id = S.LOC_ID , SMPITEM SMP\r\n"
//				+ "WHERE S.TRD_SCH_STATUS = 'E'  AND RTRIM(S.ARTICLE_PROD_ID) = RTRIM(SMP.SMP_PROD_ID)";
//
//		query = entityManager.createNativeQuery(q, Tuple.class);
//		List<Tuple> tuples = query.getResultList();
//
//		for (Tuple t : tuples) {
//
//			Article_bean_for_modify bean = new Article_bean_for_modify();
//
//			bean.setTRD_SCH_SLNO(Integer.parseInt(t.get(0).toString()));
//			bean.setSCHEME_APPTYPE(t.get(1).toString());
//			bean.setLOCATION((t.get(2).toString()));
//
//			bean.setTRD_SCHEME_CODE((t.get(3).toString()));
//			bean.setTRD_SCHEME_NAME((t.get(4).toString()));
//			bean.setTRD_SCHEME_DESCR((t.get(5)).toString());
//
//			Date str_dateFrom = t.get(6, Date.class);
//			Date str_dateTo = t.get(7, Date.class);
//
//			bean.setVALID_FROM_DT(str_dateFrom);
//			bean.setVALID_TO_DT(str_dateTo);
//
//			String dateStrFrom = str_dateFrom.toString();
//			String dateStrto = str_dateTo.toString();
//
//			DateFormat parser = new SimpleDateFormat("yyyy-MM-dd");
//			Date dateFrom = parser.parse(dateStrFrom);
//			Date dateTo = parser.parse(dateStrto);
//
//			DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
//			bean.setStringDateFrom(dateFormat.format(dateFrom));
//			bean.setStringDateTo(dateFormat.format(dateTo));
//
//			bean.setAPPLY_TO(t.get(8).toString());
//			bean.setBILLED_VALUE(Long.valueOf(t.get(9).toString()));
//			bean.setTRD_SCH_TYPE(t.get(10).toString());
//			bean.setINVOICE_TYPE(t.get(11).toString());
//			bean.setARTICLE_PROD_ID(Long.valueOf(t.get(12).toString()));
//			bean.setARTICLE_PROD_CD(t.get(13).toString());
//			bean.setARTICLE_PROD_NAME(t.get(14).toString());
//			bean.setARTICLE_PROD_RATE(new BigDecimal(t.get(15).toString()));
//			bean.setARTICLE_QTY_PER_TOT_VAL(Long.valueOf(t.get(16).toString()));
//
//			beans.add(bean);
//			System.out.println(bean);
//		}
//
//		return beans;
//	}

	@Override
	public List<Article_bean_for_modify> get_approval_data(String employeeId) throws Exception {

		List<Article_bean_for_modify> beans = new ArrayList<>();

		Query query = null;

		StringBuffer buffer = new StringBuffer();

		buffer.append("	SELECT S.trd_sch_slno,S.scheme_apptype, ");
		buffer.append(
				" ISNULL( ISNULL(dl.dptdestination,'ALL') +' - '+L.LOC_NM,'ALL')  AS LOCATION, S.trd_scheme_code,S.trd_scheme_name,S.trd_scheme_descr, ");
		buffer.append(
				"   CONVERT(DATE, S.valid_from_dt) VALID_FROM_DT, CONVERT(DATE, S.valid_to_dt) VALID_TO_DT,S.apply_to,S.billed_value,");
		buffer.append("   CASE WHEN S.trd_sch_type = 'A' THEN 'Articles' ELSE 'Gift' END  TRD_SCH_TYPE,");
		buffer.append(
				"   S.invoice_type,S.article_prod_id,S.article_prod_cd, SMP.smp_prod_name ARTICLE_PROD_NAME,S.article_prod_rate,");
		buffer.append(
				"   S.article_qty_per_tot_val,S.GRN_TOT_QTY  FROM   trd_scheme_mst_hdr S LEFT OUTER JOIN location L   ON L.loc_id = S.loc_id LEFT");
		buffer.append(
				" 	JOIN DEPOT_LOCATIONS dl on l.LOC_LINK_DPTLOC_ID = dl.DPTLOC_ID,smpitem SMP WHERE   S.trd_sch_status in ('E') ");
		buffer.append(
				"   AND Rtrim(S.article_prod_id) = Rtrim(SMP.smp_prod_id) AND S.created_by =:employeeId  ORDER  BY trd_sch_slno DESC");

		query = entityManager.createNativeQuery(buffer.toString(), Tuple.class);
		query.setParameter("employeeId", employeeId);
		List<Tuple> tuples = query.getResultList();

		for (Tuple t : tuples) {

			Article_bean_for_modify bean = new Article_bean_for_modify();

			bean.setTRD_SCH_SLNO(Integer.parseInt(t.get("trd_sch_slno").toString()));
			bean.setSCHEME_APPTYPE(t.get("scheme_apptype").toString());
			bean.setLOCATION((t.get("LOCATION").toString()));

			bean.setTRD_SCHEME_CODE((t.get("trd_scheme_code").toString()));
			bean.setTRD_SCHEME_NAME((t.get("trd_scheme_name").toString()));
			bean.setTRD_SCHEME_DESCR((t.get("trd_scheme_descr")).toString());

			Date str_dateFrom = t.get("VALID_FROM_DT", Date.class);
			Date str_dateTo = t.get("VALID_TO_DT", Date.class);

			bean.setVALID_FROM_DT(str_dateFrom);
			bean.setVALID_TO_DT(str_dateTo);

			String dateStrFrom = str_dateFrom.toString();
			String dateStrto = str_dateTo.toString();

			DateFormat parser = new SimpleDateFormat("yyyy-MM-dd");
			Date dateFrom = parser.parse(dateStrFrom);
			Date dateTo = parser.parse(dateStrto);

			DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");

			bean.setStringDateFrom(dateFormat.format(dateFrom));
			bean.setStringDateTo(dateFormat.format(dateTo));

			bean.setAPPLY_TO(t.get("apply_to").toString());
			bean.setBILLED_VALUE(Long.valueOf(t.get("billed_value").toString()));
			bean.setTRD_SCH_TYPE(t.get("TRD_SCH_TYPE").toString());
			bean.setINVOICE_TYPE(t.get("invoice_type").toString());
			bean.setARTICLE_PROD_ID(Long.valueOf(t.get("article_prod_id").toString()));
			bean.setARTICLE_PROD_CD(t.get("article_prod_cd").toString());
			bean.setARTICLE_PROD_NAME(t.get("ARTICLE_PROD_NAME").toString());
			bean.setARTICLE_PROD_RATE(new BigDecimal(t.get("article_prod_rate").toString()));
			bean.setARTICLE_QTY_PER_TOT_VAL(Long.valueOf(t.get("article_qty_per_tot_val").toString()));
			bean.setGrn_qty((t.get("GRN_TOT_QTY").toString()));

			beans.add(bean);
			System.out.println(bean);
		}

		return beans;
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public Boolean deleteScheme(String trs_sl_no) throws Exception {

		
//		 if(this.CheckFileExistForTheScheme(trs_sl_no)) {
//			 Boolean delecteDocsDetail_SachemeByslNoStat = delecteDocsDetail_SachemeByslNo(trs_sl_no);
//		 }
		 
		Boolean delecteSachemeByslNoStatHDR = delecteSachemeByslNo(trs_sl_no);
//		Boolean delecteSachemeByslNoStatDTL = delecteSachemeDTLByslNo(trs_sl_no);

		return delecteSachemeByslNoStatHDR ;
	}



	private Boolean delecteSachemeDTLByslNo(String trs_sl_no) {

		int i = 0;
		Boolean deleted = false;

		Query query = null;
		String q = "delete from TRD_SCHEME_MST_DTL where TRD_SCH_SLNO =" + trs_sl_no;

		query = entityManager.createNativeQuery(q);

		i = query.executeUpdate();

		System.out.println(i);
		if (i > 0) {
			deleted = true;
		} else {
			deleted = false;
		}

		return deleted;

	}

	private Boolean delecteDocsDetail_SachemeByslNo(String trs_sl_no) {
		int i = 0;
		Boolean deleted = false;
		Query query = null;
		String q = " delete from TRD_SCH_MST_DOCS where TRD_SCH_SLNO =" + trs_sl_no;
		query = entityManager.createNativeQuery(q);

		i = query.executeUpdate();

		System.out.println(i);
		if (i > 0) {
			deleted = true;
		} else {
			deleted = false;
		}

		return deleted;
	}

	private boolean delecteSachemeByslNo(String trs_sl_no) {

		int i = 0;
		Boolean deleted = false;

		Query query = null;
		String q = " update TRD_SCHEME_MST_HDR  set TRD_SCH_STATUS='I' where TRD_SCH_SLNO =" + trs_sl_no;

		query = entityManager.createNativeQuery(q);

		i = query.executeUpdate();

		System.out.println(i);
		if (i > 0) {
			deleted = true;
		} else {
			deleted = false;
		}

		return deleted;

	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public Map<String, Object> update(Article_save_as_bean bean, String article_Prod_Code, HttpServletRequest request,
			List<MultipartFile> filesList) throws Exception {
//		Date Apply_Invoice_From=MedicoResources.convert_DD_MM_YYYY_toDate(bean.getApply_invoice_from());
		String ip_addr = request.getRemoteAddr();
		Boolean Saved = false;
		Map<String, Object> map = new HashMap<>();
		trd_scheme_mst_hdr hdr = entityManager.find(trd_scheme_mst_hdr.class, Long.valueOf(bean.getTrd_sch_slno()));
		if (bean.getApply_Scheme_to().equals("V")) {
			hdr.setArticle_qty_per_tot_val(Long.valueOf(bean.getQty()));
			hdr.setBilled_value(Long.valueOf(bean.getValue_article()));
		} else {
			hdr.setArticle_qty_per_tot_val(0L);
			hdr.setBilled_value(0L);
		}
		hdr.setTrd_scheme_name(bean.getScheme_Name());
		hdr.setTrd_scheme_descr(bean.getScheme_Description());
		hdr.setValid_from_dt(bean.getValid_From());
		hdr.setApply_inv_from(bean.getApply_invoice_from());
		hdr.setValid_to_dt(bean.getValid_To());
		hdr.setTrd_sch_type(bean.getArticle_Type());
		hdr.setInvoice_type(bean.getApply_to_Individual_Invoice());
		hdr.setArticle_prod_cd(article_Prod_Code);
		hdr.setArticle_prod_id(Long.valueOf(bean.getArticle_id()));
		hdr.setArticle_prod_rate(bean.getRate());
		hdr.setLast_mod_by(bean.getCreated_by());
		hdr.setLast_mod_date(bean.getCreated_date());
		hdr.setRemarks(bean.getRemarks());
		hdr.setScheme_div_id(bean.getScheme_division());
		hdr.setSpecific_brand_scheme(bean.getSales_product_division());
		hdr.setGrn__tot_qty(bean.getGrn_qty());
		hdr.setClosure_date(bean.getValid_To());
		transactionalRepository.update(hdr);
	
		Boolean delted = this.deleteSchememasterDtlDelete(hdr.getTrd_sch_slno());
		SalesProdDetails[] beans = bean.getSales_prod_details();
		for (SalesProdDetails b : beans) {
			trd_scheme_mst_dtl dtl = new trd_scheme_mst_dtl();
			dtl.setCompany_cd(bean.getCompany_code());
			dtl.setScheme_apptype(bean.getArticle_Type());
			dtl.setTrd_sch_slno(hdr.getTrd_sch_slno());
			dtl.setTrd_scheme_code(hdr.getTrd_scheme_code());
			dtl.setValid_from_dt(bean.getValid_From());
			dtl.setValid_to_dt(bean.getValid_To());
			dtl.setApply_to(bean.getApply_Scheme_to());
			dtl.setSale_prod_id(Long.valueOf(b.getSale_PROD_ID()));
			dtl.setSale_prod_code_erp(b.getSales_product_erpcode());
			dtl.setSale_prod_code_sg(b.getSales_product_code());
			if (bean.getApply_Scheme_to().equals("V")) {
				dtl.setPer_sale_qty_billed(0L);
				dtl.setPer_sale_qty_free(0L);
				dtl.setPer_sale_qty_tot((0L));
				dtl.setArticle_qty(0L);
			} else if (bean.getApply_Scheme_to().equals("Q")) {
				if (b.getFree() == null) {
					b.setFree(0L);
				}
				dtl.setPer_sale_qty_billed(Long.valueOf(b.getBilled()));
				dtl.setPer_sale_qty_free(Long.valueOf(b.getFree()));
				dtl.setPer_sale_qty_tot(Long.valueOf(b.getBilled()) + Long.valueOf(b.getFree()));
				dtl.setArticle_qty(Long.valueOf(b.getArticle_Qty()));
			}
			dtl.setCreated_by(bean.getCreated_by());
			dtl.setCreated_date(bean.getCreated_date());
			dtl.setLast_mod_by(bean.getCreated_by());
			dtl.setLast_mod_date(bean.getCreated_date());
			this.transactionalRepository.persist(dtl);
		}

		List<MultipartFile> files = filesList;
		for (MultipartFile m : files) {
			String file_name=m.getOriginalFilename().replace(" ","_");
			byte[] bytes = m.getBytes();
			Path path = Paths.get(MedicoConstants.ARTICLE_SCHEME_MASTER_UPLOADED_FILES + file_name);
			Files.write(path, bytes);
			TRD_SCH_MST_DOCS masterBean = new TRD_SCH_MST_DOCS();
			masterBean.setDoc_ins_dt(bean.getCreated_date());
			masterBean.setDoc_ins_ip_addr(ip_addr);
			masterBean.setDoc_ins_usr_id(bean.getCreated_by());
			masterBean.setFilename(file_name);
			masterBean.setFilepath(MedicoConstants.ARTICLE_SCHEME_MASTER_UPLOADED_FILES + file_name);
			if (m.getContentType().equals("image/png") || m.getContentType().equals("image/jpeg")) {
				masterBean.setFiletype("I");
			} else {
				masterBean.setFiletype("D");
			}
			masterBean.setTrd_sch_slno(Long.valueOf(bean.getTrd_sch_slno()));
			System.out.println(masterBean);
			this.transactionalRepository.persist(masterBean);
		}
		Saved = true;
		map.put("Saved", Saved);
		return map;
	}

	private Boolean deleteSchememasterDtlDelete(Long trd_sch_slno) {

		int i = 0;
		Boolean deleted = false;

		Query query = null;
		String q = "delete from TRD_SCHEME_MST_DTL where TRD_SCH_SLNO =" + trd_sch_slno;
		query = entityManager.createNativeQuery(q);

		i = query.executeUpdate();

		System.out.println(i);
		if (i > 0) {
			deleted = true;
		} else {
			deleted = false;
		}

		return deleted;

	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public Boolean confirm_approval(String stringSlnForQuery) {

		int i = 0;
		Boolean Updated = false;

		String q = "UPDATE TRD_SCHEME_MST_HDR     \r\n" + "SET TRD_SCH_STATUS = 'A' where  TRD_SCH_SLNO in("
				+ stringSlnForQuery + ")   ";

		Query query = entityManager.createNativeQuery(q);

		i = query.executeUpdate();

		System.out.println(i);
		if (i > 0) {
			Updated = true;
		} else {
			Updated = false;
		}

		return Updated;
	}

	@Override
	public List<ArticleDocsBean> get_Docs_Details(String schemeslno) {

//		String q = "\r\n"
//				+ "select FILENAME as FILENAME, FILETYPE as FILETYPE   from TRD_SCH_MST_DOCS where  TRD_SCH_SLNO="
//				+ schemeslno;

		String q = "select FILENAME as FILENAME, FILETYPE as FILETYPE, SLNO as SLNO   from TRD_SCH_MST_DOCS where  TRD_SCH_SLNO="
				+ schemeslno;

		Query query = entityManager.createNativeQuery(q, Tuple.class);
		List<Tuple> tuples = query.getResultList();

		List<ArticleDocsBean> articleDocsBeans = new ArrayList<>();
		for (Tuple t : tuples) {

			ArticleDocsBean articleDocsBean = new ArticleDocsBean();
			articleDocsBean.setFileName(t.get("FILENAME").toString());
			articleDocsBean.setFileType(t.get("FILETYPE").toString());
			articleDocsBean.setSlNo(t.get("SLNO").toString());

			articleDocsBeans.add(articleDocsBean);
		}

		return articleDocsBeans;

	}

//	@Override
//	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
//	public Boolean deleteDoc(String docId) throws Exception {
//
//		int i = 0;
//		Boolean deleted = false;
//
//		Query query = null;
//		// String q = " delete from TRD_SCHEME_MST_HDR where TRD_SCH_SLNO=" + trs_sl_no;
//		String q = "delete from TRD_SCH_MST_DOCS where SLNO =" + docId;
//		query = entityManager.createNativeQuery(q);
//
//		i = query.executeUpdate();
//
//		System.out.println(i);
//		if (i > 0) {
//			deleted = true;
//		} else {
//			deleted = false;
//		}
//
//		return deleted;
//	}

	@Override
	public trd_scheme_mst_hdr getTrdSchemeMasterById(long l) {

//		String q="select TRD.TRD_SCHEME_CODE as TRD_SCHEME_CODE ,l.loc_nm as loc_nm  ,SP.SALE_PROD_NAME as SALE_PROD_NAME ,TRD.PER_SALE_QTY_FREE as free,\r\n"
//				+ "TRD.PER_SALE_QTY_BILLED as Billed, \r\n"
//				+ "SM.SMP_PROD_NAME as SMP_PROD_NAME  ,TRD.ARTICLE_PROD_QTY as Qty  ,TRD.ARTICLE_PROD_RATE as ARTICLE_PROD_RATE  from   TRD_SCHEME_MST_HDR TRD\r\n"
//				+ "INNER JOIN location l on l.loc_id =TRD.LOC_ID\r\n"
//				+ "INNER JOIN SMPITEM SM ON SM.SMP_PROD_ID = TRD.ARTICLE_PROD_ID\r\n"
//				+ "INNER JOIN SALEPROD SP ON SP.SALE_PROD_ID = TRD.SALE_PROD_ID\r\n"
//				+ " where TRD.TRD_SCH_SLNO="+l;

		String q = "\r\n" + " 	 SELECT S.TRD_SCH_SLNO,ISNULL(L.LOC_NM,'ALL') LOCATION,\r\n"
				+ "	 S.TRD_SCHEME_CODE,SL.SALE_PROD_NAME,SMP.SMP_PROD_NAME ARTICLE_PROD_NAME,\r\n"
				+ " S.ARTICLE_PROD_QTY,S.ARTICLE_PROD_RATE,S.PER_SALE_QTY_BILLED,S.PER_SALE_QTY_FREE  \r\n"
				+ " FROM TRD_SCHEME_MST_HDR S LEFT OUTER JOIN location L ON L.loc_id = S.LOC_ID , SALEPROD SL , SMPITEM SMP\r\n"
				+ "	 WHERE    S.TRD_SCH_SLNO=:slno\r\n" + "	 AND RTRIM(S.SALE_PROD_ID) = RTRIM(SL.SALE_PROD_ID)\r\n"
				+ "	 AND RTRIM(S.ARTICLE_PROD_ID) = RTRIM(SMP.SMP_PROD_ID)\r\n" + "	";

		Query query = entityManager.createNativeQuery(q, Tuple.class);

		query.setParameter("slno", l);

		List<Tuple> tuples = query.getResultList();

		trd_scheme_mst_hdr hdr = new trd_scheme_mst_hdr();
		for (Tuple t : tuples) {

			hdr.setTrd_scheme_code(t.get("TRD_SCHEME_CODE", String.class));
			hdr.setLoc_nm(t.get("LOCATION", String.class));
			hdr.setSALE_PROD_NAME(t.get("SALE_PROD_NAME", String.class));
//			 hdr.setPer_sale_qty_free(t.get("PER_SALE_QTY_FREE",BigDecimal.class).longValue());
//			 hdr.setPer_sale_qty_billed(t.get("PER_SALE_QTY_BILLED",BigDecimal.class).longValue());
//			 hdr.setSMP_PROD_NAM(t.get("ARTICLE_PROD_NAME",String.class));
//			 hdr.setArticle_prod_qty(t.get("ARTICLE_PROD_QTY",BigDecimal.class).longValue());
			hdr.setArticle_prod_rate(t.get("ARTICLE_PROD_RATE", BigDecimal.class));
		}
		return hdr;
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public boolean releasedLockedProduct(String userId) {

		int i = -1;

		String q = " delete from SCM_MST_PROD_LOCK where USER_ID =:userId";

		Query query = entityManager.createNativeQuery(q);
		query.setParameter("userId", userId);
		i = query.executeUpdate();

		System.out.println(i);

		if (i == 0 || i == 1) {

			return true;
		}

		return false;
	}

	@Override
	public List<SchemeMasterSampleProductBean> search_Article_Product_By_Name(String search_value, String subcompanyId)
			throws Exception {

//		String q="  select SMP_PROD_ID as SMP_PROD_ID,SMP_PROD_NAME as SMP_PROD_NAME  ,SMP_ERP_PROD_CD as  SMP_ERP_PROD_CD ,SMP_RATE as SMP_RATE \r\n"
//				+ " from smpitem WHERE SMP_status = 'A' AND\r\n"
//				+ " SMP_DISCONT_DT IS NULL AND SMP_SubComp_id=:subCompanyId  \r\n"
//				+ " and smp_prod_id not in (select prod_id from SCM_MST_PROD_LOCK)\r\n"
//				+ " and  SMP_PROD_NAME  LIKE '"+search_value.trim()+"%'\r\n"
//				+ " order by smp_prod_name";

		String q = "select SMP_PROD_ID as SMP_PROD_ID,SMP_PROD_NAME as SMP_PROD_NAME  ,SMP_ERP_PROD_CD as  SMP_ERP_PROD_CD ,SMP_RATE as SMP_RATE \r\n"
				+ " from smpitem WHERE SMP_status = 'A' AND\r\n"
				+ " SMP_DISCONT_DT IS NULL AND SMP_SubComp_id=:subCompanyId \r\n"
				+ " and smp_prod_id not in (select prod_id from SCM_MST_PROD_LOCK)\r\n"
				+ " and UPPER(LTRIM(RTRIM(SMP_PROD_NAME)))  LIKE '%" + search_value.trim() + "%'\r\n"
				+ " order by smp_prod_name";

		Query query = entityManager.createNativeQuery(q, Tuple.class);
		query.setParameter("subCompanyId", subcompanyId);
		List<Tuple> tuples = query.getResultList();
		List<SchemeMasterSampleProductBean> beans = new ArrayList<>();

		tuples.forEach(l -> {

			SchemeMasterSampleProductBean bean = new SchemeMasterSampleProductBean();
			bean.setSMP_PROD_ID(l.get("SMP_PROD_ID").toString());
			bean.setSMP_PROD_NAME(l.get("SMP_PROD_NAME").toString().trim());
			bean.setSMP_ERP_PROD_CD((l.get("SMP_ERP_PROD_CD") == null ? "" : l.get("SMP_ERP_PROD_CD").toString()));
			bean.setSMP_RATE(new BigDecimal(l.get("SMP_RATE").toString()));

			beans.add(bean);

		});

		return beans;
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public void unlock_article_product(String userId) {

		int i = -1;
		Boolean deleted = false;

		Query query = null;
		String q = "  delete from SCM_MST_PROD_LOCK where USER_ID =:userId";

		query = entityManager.createNativeQuery(q);

		query.setParameter("userId", userId.trim());
		i = query.executeUpdate();

		System.out.println(i);
	}

	@Override
	public List<SchemeMasterSampleProductBean> get_top_Article_product(String subCompanyId) throws Exception {

		String q = "select TOP 30  SMP_PROD_ID as SMP_PROD_ID,SMP_PROD_NAME as SMP_PROD_NAME  ,SMP_ERP_PROD_CD as  SMP_ERP_PROD_CD ,SMP_RATE as SMP_RATE \r\n"
				+ " from smpitem WHERE SMP_status = 'A' AND\r\n"
				+ " SMP_DISCONT_DT IS NULL AND SMP_SubComp_id=:subCompanyId \r\n"
				+ " and smp_prod_id not in (select prod_id from SCM_MST_PROD_LOCK)\r\n" + " order by smp_prod_name";

		Query query = entityManager.createNativeQuery(q, Tuple.class);
		query.setParameter("subCompanyId", subCompanyId);
		List<Tuple> tuples = query.getResultList();
		List<SchemeMasterSampleProductBean> beans = new ArrayList<>();

		tuples.forEach(l -> {

			SchemeMasterSampleProductBean bean = new SchemeMasterSampleProductBean();
			bean.setSMP_PROD_ID(l.get("SMP_PROD_ID").toString());
			bean.setSMP_PROD_NAME(l.get("SMP_PROD_NAME").toString().trim());
			bean.setSMP_ERP_PROD_CD((l.get("SMP_ERP_PROD_CD") == null ? "" : l.get("SMP_ERP_PROD_CD").toString()));
			bean.setSMP_RATE(new BigDecimal(l.get("SMP_RATE").toString()));

			beans.add(bean);

		});

		return beans;
	}

	@Override
	public trd_scheme_mst_hdr getTrdSchemeMasterByIdForApproval(long l) {
		// TODO Auto-generated method stub
		return entityManager.find(trd_scheme_mst_hdr.class, Long.valueOf(l));
	}

	@Override
	public List<ArticleSchemeMasterSALEPRODData> get_top_sale_product() {

		List<ArticleSchemeMasterSALEPRODData> beans = new ArrayList<>();

		Query query = null;

		String q = "select top 30 SALE_PROD_ID as SALE_PROD_ID,  SALE_PROD_NAME as SALE_PROD_NAME,SALE_PROD_CD as SALE_PROD_CD from SALEPROD WHERE SALE_STATUS = 'A'";
		query = entityManager.createNativeQuery(q, Tuple.class);
		List<Tuple> tuples = query.getResultList();

		tuples.forEach(l -> {

			ArticleSchemeMasterSALEPRODData bean = new ArticleSchemeMasterSALEPRODData();

			bean.setSALE_PROD_ID(l.get("SALE_PROD_ID").toString());
			bean.setSALE_PROD_NAME(l.get("SALE_PROD_NAME").toString().trim());
			bean.setSALE_PROD_CD(l.get("SALE_PROD_CD").toString().trim());

			beans.add(bean);
		});

		System.out.println(beans);

		return beans;
	}

//	
//	@Override
//	public List<SamplProdGroup> get_brands() {
//
//		EntityManager em = null;
//		List<SamplProdGroup> list = null;
//		try {
//			em = emf.createEntityManager();
//			CriteriaBuilder builder = em.getCriteriaBuilder();
//			CriteriaQuery<SamplProdGroup> criteriaQuery = builder.createQuery(SamplProdGroup.class);
//			Root<SamplProdGroup> root = criteriaQuery.from(SamplProdGroup.class);
//			criteriaQuery.multiselect(
//					root.get(SamplProdGroup_.sa_prod_group),
//					root.get(SamplProdGroup_.sa_group_name)
//					).where(builder.equal(root.get(SamplProdGroup_.status),MedicoConstants.A))
//			.orderBy(builder.asc(root.get(SamplProdGroup_.sa_group_name)));
//			list = em.createQuery(criteriaQuery).getResultList();
//		} finally {
//			if(em != null) { em.close(); }
//		}
//		if(list != null && !list.isEmpty()) {
//			return list;
//		}
//		return null;
//		
//	}

	@Override
	public List<SamplProdGroup> get_brands(String sales_product_division, String scheme_division) {

		Query query = null;

		String q = "";

		if (sales_product_division.equals("A")) {

			q = "select sa_prod_group,SA_GROUP_NAME from SAPRODGRP where sa_prod_group IN\r\n"
					+ "(select distinct sale_sa_prod_group from saleprod)  order by SA_GROUP_NAME ";
		} else {

			q = "select sa_prod_group,SA_GROUP_NAME from SAPRODGRP where sa_prod_group IN\r\n"
					+ "(select distinct sale_sa_prod_group from saleprod where sale_std_div_id=" + scheme_division
					+ ")  order by SA_GROUP_NAME";
		}

		query = entityManager.createNativeQuery(q, Tuple.class);

		List<SamplProdGroup> beans = new ArrayList<>();

		List<Tuple> tuples = query.getResultList();

		for (Tuple t : tuples) {

			SamplProdGroup bean = new SamplProdGroup();

			bean.setSa_prod_group(Long.valueOf(t.get("SA_PROD_GROUP").toString()));
			bean.setSa_group_name(t.get("SA_GROUP_NAME").toString());
			beans.add(bean);
		}

		System.out.println(beans);
		return beans;

	}

	@Override
	public List<ArticleSchemeMasterSALEPRODData> get_saleProduct(String brandValues, long subcompanyId) {

		Query query = null;

		String q = "select  SALE_PROD_ID AS SALE_PROD_ID, SALE_PROD_CD AS SALE_PROD_CD, SALE_PROD_NAME AS SALE_PROD_NAME ,SALE_MAP_CODE1 AS SALE_MAP_CODE1, sale_sa_prod_group as SALE_SA_PROD_GROUP  from SALEPROD where sale_sa_prod_group IN ("
				+ brandValues + ") and sale_status = 'A'";

		query = entityManager.createNativeQuery(q, Tuple.class);

		List<ArticleSchemeMasterSALEPRODData> beans = new ArrayList<>();

		List<Tuple> tuples = query.getResultList();

		for (Tuple t : tuples) {

			ArticleSchemeMasterSALEPRODData bean = new ArticleSchemeMasterSALEPRODData();
			bean.setSALE_MAP_CODE1(t.get("SALE_MAP_CODE1").toString());
			bean.setSALE_PROD_CD(t.get("SALE_PROD_CD").toString());
			bean.setSALE_PROD_NAME(t.get("SALE_PROD_NAME").toString());
			bean.setSALE_PROD_ID(t.get("SALE_PROD_ID").toString());
			bean.setSALE_SA_PROD_GROUP(t.get("SALE_SA_PROD_GROUP").toString());

			beans.add(bean);
		}

		System.out.println(beans);

		return beans;
	}

	@Override
	public List<trd_scheme_mst_dtl> get_Detail_Data_For_Edit(String trd_SCH_SLNO) {

		Query query = null;
		String q = "select * from TRD_SCHEME_MST_DTL where TRD_SCH_SLNO=" + trd_SCH_SLNO;
		query = entityManager.createNativeQuery(q, trd_scheme_mst_dtl.class);
		List<trd_scheme_mst_dtl> beans = query.getResultList();

		System.out.println(beans.size());

		return beans;
	}

	@Override
	public List<trd_scheme_mst_hdr> get_HDR_Data_For_Edit(String trd_SCH_SLNO) {

		Query query = null;
		String q = "select * from TRD_SCHEME_MST_HDR where TRD_SCH_SLNO =" + trd_SCH_SLNO;
		query = entityManager.createNativeQuery(q, trd_scheme_mst_hdr.class);
		List<trd_scheme_mst_hdr> beans = query.getResultList();

		System.out.println(beans.size());

		return beans;

	}

	@Override
	public List<ArticleSchemeMasterSALEPRODData> getSaleProductById(String sales_prod_id) {

		List<ArticleSchemeMasterSALEPRODData> beans = new ArrayList<>();
		Query query = null;
		String q = " select  SALE_PROD_ID AS SALE_PROD_ID, SALE_PROD_CD AS SALE_PROD_CD, SALE_PROD_NAME AS SALE_PROD_NAME ,SALE_MAP_CODE1 AS SALE_MAP_CODE1,SALE_SA_PROD_GROUP AS SALE_SA_PROD_GROUP  \r\n"
				+ " from SALEPROD where SALE_PROD_ID in(" + sales_prod_id + ")";
		query = entityManager.createNativeQuery(q, Tuple.class);
		List<Tuple> tupleBeans = query.getResultList();

		for (Tuple t : tupleBeans) {

			ArticleSchemeMasterSALEPRODData bean = new ArticleSchemeMasterSALEPRODData();
			bean.setSALE_MAP_CODE1(t.get("SALE_MAP_CODE1").toString());
			bean.setSALE_PROD_CD(t.get("SALE_PROD_CD").toString());
			bean.setSALE_PROD_NAME(t.get("SALE_PROD_NAME").toString());
			bean.setSALE_PROD_ID(t.get("SALE_PROD_ID").toString());
			bean.setSALE_SA_PROD_GROUP(t.get("SALE_SA_PROD_GROUP").toString());

			beans.add(bean);
		}
		return beans;

	}

//	@Override
//	public List<SchemeDetailDataBean> getTrdSchemeDetailById(Long schemeslno) throws ParseException {
//
//		Query query = null;
//		List<SchemeDetailDataBean> beans = new ArrayList<>();
//
//		StringBuffer buffer = new StringBuffer();
//
////			buffer.append("  select hdr.TRD_SCH_SLNO,ISNULL(L.LOC_NM,'ALL') LOCATION,hdr.TRD_SCHEME_CODE,hdr.VALID_FROM_DT,hdr.VALID_TO_DT,hdr.APPLY_TO,");
////			buffer.append("  hdr.ARTICLE_PROD_CD,smp.SMP_PROD_NAME,hdr.BILLED_VALUE,hdr.ARTICLE_QTY_PER_TOT_VAL,hdr.ARTICLE_PROD_RATE,");
////			buffer.append("  SMP.SMP_PROD_NAME ARTICLE_PROD_NAME, ");
////			buffer.append("  dtl.TRD_SCHEME_DTL_ID,dtl.SALE_PROD_CODE_ERP,sp.SALE_PROD_NAME,dtl.PER_SALE_QTY_BILLED,dtl.PER_SALE_QTY_FREE,dtl.ARTICLE_QTY ");
////			buffer.append("  ,hdr.TRD_SCHEME_NAME,hdr.APPLY_INV_FROM    from TRD_SCHEME_MST_HDR hdr");
////			buffer.append("  inner join trd_scheme_mst_dtl dtl on hdr.TRD_SCH_SLNO = dtl.TRD_SCH_SLNO ");
////			buffer.append("  left outer join location L ON L.loc_id = hdr.LOC_ID");
////			buffer.append("  inner join SALEPROD sp on sp.SALE_PROD_ID = dtl.SALE_PROD_ID");
////			buffer.append("  inner join SMPITEM smp on smp.SMP_PROD_ID = hdr.ARTICLE_PROD_ID  ");
//
//		buffer.append(
//				" select hdr.TRD_SCH_SLNO,hdr.SCHEME_DIV_ID,ISNULL(L.LOC_NM,'ALL') LOCATION,hdr.TRD_SCHEME_CODE,hdr.VALID_FROM_DT,hdr.VALID_TO_DT,hdr.APPLY_TO,");
//		buffer.append(
//				" hdr.ARTICLE_PROD_CD,smp.SMP_PROD_NAME,hdr.BILLED_VALUE,hdr.ARTICLE_QTY_PER_TOT_VAL,hdr.ARTICLE_PROD_RATE,");
//		buffer.append(" SMP.SMP_PROD_NAME ARTICLE_PROD_NAME, ");
//		buffer.append(
//				" dtl.TRD_SCHEME_DTL_ID,dtl.SALE_PROD_CODE_ERP,sp.SALE_PROD_NAME,dtl.PER_SALE_QTY_BILLED,dtl.PER_SALE_QTY_FREE,dtl.ARTICLE_QTY");
//		buffer.append(" ,hdr.TRD_SCHEME_NAME,hdr.APPLY_INV_FROM ,hdr.REMARKS   from TRD_SCHEME_MST_HDR hdr");
//		buffer.append(" inner join trd_scheme_mst_dtl dtl on hdr.TRD_SCH_SLNO = dtl.TRD_SCH_SLNO");
//		buffer.append(" left outer join location L ON L.loc_id = hdr.LOC_ID");
//		buffer.append(" inner join SALEPROD sp on sp.SALE_PROD_ID = dtl.SALE_PROD_ID");
//		buffer.append(" inner join SMPITEM smp on smp.SMP_PROD_ID = hdr.ARTICLE_PROD_ID");
//		buffer.append("  where hdr.TRD_SCH_SLNO =" + schemeslno);
//
//		query = entityManager.createNativeQuery(buffer.toString(), Tuple.class);
//		List<Tuple> tupleBeans = query.getResultList();
//
//		for (Tuple t : tupleBeans) {
//
//			SchemeDetailDataBean bean = new SchemeDetailDataBean();
//			bean.setTRD_SCH_SLNO(Long.valueOf(t.get("TRD_SCH_SLNO", Integer.class)));
//			bean.setSCHEME_DIV_ID(t.get("SCHEME_DIV_ID", Integer.class).toString());
//			bean.setLOCATION(t.get("LOCATION", String.class));
//			bean.setTRD_SCHEME_CODE(t.get("TRD_SCHEME_CODE", String.class));
//			bean.setAPPLY_TO(t.get("APPLY_TO").toString());
//			bean.setARTICLE_PROD_CD(t.get("ARTICLE_PROD_CD", String.class));
//			bean.setSMP_PROD_NAME(t.get("ARTICLE_PROD_CD").toString());
//			bean.setBILLED_VALUE((t.get("BILLED_VALUE", BigDecimal.class)));
//			bean.setARTICLE_QTY_PER_TOT_VAL(new BigDecimal(t.get("ARTICLE_QTY_PER_TOT_VAL").toString()));
//			bean.setARTICLE_PROD_RATE(t.get("ARTICLE_PROD_RATE", BigDecimal.class));
//			bean.setARTICLE_PROD_NAME(t.get("ARTICLE_PROD_NAME", String.class));
//			bean.setTRD_SCHEME_DTL_ID(Long.valueOf(t.get("TRD_SCHEME_DTL_ID", Integer.class)));
//			bean.setSALE_PROD_CODE_ERP(t.get("SALE_PROD_CODE_ERP", String.class));
//			bean.setSALE_PROD_NAME(t.get("SALE_PROD_NAME", String.class));
//			bean.setPER_SALE_QTY_BILLED(new BigDecimal(t.get("PER_SALE_QTY_BILLED").toString()));
//			bean.setPER_SALE_QTY_FREE(new BigDecimal(t.get("PER_SALE_QTY_FREE").toString()));
//			bean.setARTICLE_QTY(new BigDecimal(t.get("ARTICLE_QTY").toString()));
//			bean.setSCHEME_NAME(t.get("TRD_SCHEME_NAME").toString());
//			Date str_dateFrom = t.get("VALID_FROM_DT", Date.class);
//			Date str_dateTo = t.get("VALID_TO_DT", Date.class);
//			Date str_apply_invoice_from = t.get("APPLY_INV_FROM", Date.class);
//
//			bean.setVALID_FROM_DT(str_dateFrom);
//			bean.setVALID_TO_DT(str_dateTo);
//			bean.setAPPLY_INV_FROM(str_apply_invoice_from);
//
//			String dateStrFrom = str_dateFrom.toString();
//			String dateStrto = str_dateTo.toString();
//			String dateStr_apply_invoice_from = str_apply_invoice_from.toString();
//
//			DateFormat parser = new SimpleDateFormat("yyyy-MM-dd");
//			Date dateFrom = parser.parse(dateStrFrom);
//			Date dateTo = parser.parse(dateStrto);
//			Date dateTodateStr_apply_invoice_from = parser.parse(dateStr_apply_invoice_from);
//
//			DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
//			bean.setValidFromDateString(dateFormat.format(dateFrom));
//			bean.setValidToDateString(dateFormat.format(dateTo));
//			bean.setApply_inv_fromstring(dateFormat.format(dateTodateStr_apply_invoice_from));
//			bean.setRemarks(t.get("REMARKS", String.class));
//
//			beans.add(bean);
//
//		}
//
//		return beans;
//	}

	
	
	@Override
	public List<SchemeDetailDataBean> getTrdSchemeDetailById(Long schemeslno) throws ParseException {

		Query query = null;
		List<SchemeDetailDataBean> beans = new ArrayList<>();

		StringBuffer buffer = new StringBuffer();

//			buffer.append("  select hdr.TRD_SCH_SLNO,ISNULL(L.LOC_NM,'ALL') LOCATION,hdr.TRD_SCHEME_CODE,hdr.VALID_FROM_DT,hdr.VALID_TO_DT,hdr.APPLY_TO,");
//			buffer.append("  hdr.ARTICLE_PROD_CD,smp.SMP_PROD_NAME,hdr.BILLED_VALUE,hdr.ARTICLE_QTY_PER_TOT_VAL,hdr.ARTICLE_PROD_RATE,");
//			buffer.append("  SMP.SMP_PROD_NAME ARTICLE_PROD_NAME, ");
//			buffer.append("  dtl.TRD_SCHEME_DTL_ID,dtl.SALE_PROD_CODE_ERP,sp.SALE_PROD_NAME,dtl.PER_SALE_QTY_BILLED,dtl.PER_SALE_QTY_FREE,dtl.ARTICLE_QTY ");
//			buffer.append("  ,hdr.TRD_SCHEME_NAME,hdr.APPLY_INV_FROM    from TRD_SCHEME_MST_HDR hdr");
//			buffer.append("  inner join trd_scheme_mst_dtl dtl on hdr.TRD_SCH_SLNO = dtl.TRD_SCH_SLNO ");
//			buffer.append("  left outer join location L ON L.loc_id = hdr.LOC_ID");
//			buffer.append("  inner join SALEPROD sp on sp.SALE_PROD_ID = dtl.SALE_PROD_ID");
//			buffer.append("  inner join SMPITEM smp on smp.SMP_PROD_ID = hdr.ARTICLE_PROD_ID  ");

		buffer.append(
				" select hdr.TRD_SCH_SLNO,hdr.SCHEME_DIV_ID,ISNULL(L.LOC_NM,'ALL') LOCATION,hdr.TRD_SCHEME_CODE,hdr.VALID_FROM_DT,hdr.VALID_TO_DT,hdr.APPLY_TO,");
		buffer.append(
				" hdr.ARTICLE_PROD_CD,smp.SMP_PROD_NAME,hdr.BILLED_VALUE,hdr.ARTICLE_QTY_PER_TOT_VAL,hdr.ARTICLE_PROD_RATE,");
		buffer.append(" SMP.SMP_PROD_NAME ARTICLE_PROD_NAME, ");
		buffer.append(
				" dtl.TRD_SCHEME_DTL_ID,dtl.SALE_PROD_CODE_ERP,sp.SALE_PROD_NAME,dtl.PER_SALE_QTY_BILLED,dtl.PER_SALE_QTY_FREE,dtl.ARTICLE_QTY,hdr.CREATED_BY");
		buffer.append(" ,hdr.TRD_SCHEME_NAME,hdr.APPLY_INV_FROM ,hdr.REMARKS   from TRD_SCHEME_MST_HDR hdr");
		buffer.append(" inner join trd_scheme_mst_dtl dtl on hdr.TRD_SCH_SLNO = dtl.TRD_SCH_SLNO");
		buffer.append(" left outer join location L ON L.loc_id = hdr.LOC_ID");
		buffer.append(" inner join SALEPROD sp on sp.SALE_PROD_ID = dtl.SALE_PROD_ID");
		buffer.append(" inner join SMPITEM smp on smp.SMP_PROD_ID = hdr.ARTICLE_PROD_ID");
		buffer.append("  where hdr.TRD_SCH_SLNO =" + schemeslno);

		query = entityManager.createNativeQuery(buffer.toString(), Tuple.class);
		List<Tuple> tupleBeans = query.getResultList();

		for (Tuple t : tupleBeans) {

			SchemeDetailDataBean bean = new SchemeDetailDataBean();
			bean.setTRD_SCH_SLNO(Long.valueOf(t.get("TRD_SCH_SLNO", Integer.class)));
			bean.setSCHEME_DIV_ID(t.get("SCHEME_DIV_ID", Integer.class).toString());
			bean.setLOCATION(t.get("LOCATION", String.class));
			bean.setTRD_SCHEME_CODE(t.get("TRD_SCHEME_CODE", String.class));
			bean.setAPPLY_TO(t.get("APPLY_TO").toString());
			bean.setARTICLE_PROD_CD(t.get("ARTICLE_PROD_CD", String.class));
			bean.setSMP_PROD_NAME(t.get("ARTICLE_PROD_CD").toString());
			bean.setBILLED_VALUE((t.get("BILLED_VALUE", BigDecimal.class)));
			bean.setARTICLE_QTY_PER_TOT_VAL(new BigDecimal(t.get("ARTICLE_QTY_PER_TOT_VAL").toString()));
			bean.setARTICLE_PROD_RATE(t.get("ARTICLE_PROD_RATE", BigDecimal.class));
			bean.setARTICLE_PROD_NAME(t.get("ARTICLE_PROD_NAME", String.class));
			bean.setTRD_SCHEME_DTL_ID(Long.valueOf(t.get("TRD_SCHEME_DTL_ID", Integer.class)));
			bean.setSALE_PROD_CODE_ERP(t.get("SALE_PROD_CODE_ERP", String.class));
			bean.setSALE_PROD_NAME(t.get("SALE_PROD_NAME", String.class));
			bean.setPER_SALE_QTY_BILLED(new BigDecimal(t.get("PER_SALE_QTY_BILLED").toString()));
			bean.setPER_SALE_QTY_FREE(new BigDecimal(t.get("PER_SALE_QTY_FREE").toString()));
			bean.setARTICLE_QTY(new BigDecimal(t.get("ARTICLE_QTY").toString()));
			bean.setSCHEME_NAME(t.get("TRD_SCHEME_NAME").toString());
			bean.setCreatedBy(t.get("CREATED_BY").toString());
			Date str_dateFrom = t.get("VALID_FROM_DT", Date.class);
			Date str_dateTo = t.get("VALID_TO_DT", Date.class);
			Date str_apply_invoice_from = t.get("APPLY_INV_FROM", Date.class);

			bean.setVALID_FROM_DT(str_dateFrom);
			bean.setVALID_TO_DT(str_dateTo);
			bean.setAPPLY_INV_FROM(str_apply_invoice_from);

			String dateStrFrom = str_dateFrom.toString();
			String dateStrto = str_dateTo.toString();
			String dateStr_apply_invoice_from = str_apply_invoice_from.toString();

			DateFormat parser = new SimpleDateFormat("yyyy-MM-dd");
			Date dateFrom = parser.parse(dateStrFrom);
			Date dateTo = parser.parse(dateStrto);
			Date dateTodateStr_apply_invoice_from = parser.parse(dateStr_apply_invoice_from);

			DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
			bean.setValidFromDateString(dateFormat.format(dateFrom));
			bean.setValidToDateString(dateFormat.format(dateTo));
			bean.setApply_inv_fromstring(dateFormat.format(dateTodateStr_apply_invoice_from));
			bean.setRemarks(t.get("REMARKS", String.class));

			beans.add(bean);

		}

		return beans;
	}

	
	
	@Override
	public boolean check_If_The_Product_Already_Loaked(String userId, String articleId) {

//		System.out.println(userId+":::::::::"+articleId);

		List<SCM_MST_PROD_LOCK> beans = new ArrayList<>();

		Query query = null;
		String q = "select * from SCM_MST_PROD_LOCK where USER_ID !='" + userId + "' and PROD_ID=" + articleId.trim();
		query = entityManager.createNativeQuery(q, SCM_MST_PROD_LOCK.class);
		beans = query.getResultList();

		System.out.println(beans.size());
		if (beans.size() > 0) {
			return true;
		} else {

			return false;
		}
	}

	@Override
	public List<String> get_ToList(List<trd_scheme_mst_hdr> headerData) {

		List<String> beans = new ArrayList<>();

		Query query = null;

		String q = "";
		if (headerData.get(0).getLoc_id() == 0) {

			q = "select loc_emailid from location where loc_status = 'A'";
		} else {

			q = "select loc_emailid from location where loc_status = 'A' and loc_id =" + headerData.get(0).getLoc_id();
		}

		query = entityManager.createNativeQuery(q);
		beans = query.getResultList();

		return beans;
	}

	@Override
	public List<String> get_Cclist(List<trd_scheme_mst_hdr> headerData) {

		List<String> beans = new ArrayList<>();
		Query query = null;

		StringBuffer buffer = new StringBuffer();

		buffer.append("	select hrm.emp_email from ACC_TRAN_TYPE ac ");
		buffer.append("	inner join TASKS_MASTER tm on ac.TRAN_TYPE = tm.TRAN_TYPE");
		buffer.append("	inner join TASK_MASTER_DTL dt on tm.TASK_MASTER_ID = dt.TASK_ID");
		buffer.append("	inner join hr_m_employee hrm on hrm.emp_id = dt.IDENTITYLINKTYPE_VAL");
		buffer.append("	where ac.TRAN_TYPE = '32' and INV_GRP_ID =:schme_div_id");
		buffer.append("	union all ");
		buffer.append("	select hrm2.emp_email from hr_m_employee hrm2");
		buffer.append(
				"	where hrm2.emp_id = (select CREATED_BY from TRD_SCHEME_MST_HDR where TRD_SCH_SLNO = :trd_sch_slno)");

		query = entityManager.createNativeQuery(buffer.toString());
		query.setParameter("schme_div_id", headerData.get(0).getScheme_div_id());
		query.setParameter("trd_sch_slno", headerData.get(0).getTrd_sch_slno());
		beans = query.getResultList();

		return beans;
	}

	@Override
	public List<Artreq_To_Dispatch_Report_With_Param> getArticleSchemeReport(ArticleSchemeReportBean bean)
			throws Exception {
		// TODO Auto-generated method stub
		EntityManager em = null;
		List<Artreq_To_Dispatch_Report_With_Param> list = null;
		String stringLocation = "";

		try {

			em = emf.createEntityManager();
			StoredProcedureQuery procedurecall = em
					.createNamedStoredProcedureQuery("callartereq_to_dispatch_report_with_param");

			for (String location : bean.getLocation()) {
				stringLocation += location + ",";
			}

			System.err.println(stringLocation);

			procedurecall.setParameter("piLOC_id", stringLocation.substring(0, stringLocation.length() - 1));
			procedurecall.setParameter("startdate", bean.getStDt());
			procedurecall.setParameter("enddate", bean.getEnDt());
			procedurecall.setParameter("MCustomer_CD", bean.getCustomer());
			procedurecall.setParameter("MSAP_Invoice", bean.getInvoice());
			procedurecall.setParameter("MLR_No", bean.getLrNo());
			procedurecall.setParameter("MArticle_Scheme", bean.getArtScheme());
			procedurecall.setParameter("MArticle_PROD_CD", bean.getArtName());
			procedurecall.setParameter("MSales_Product", bean.getSalesProduct());
			list = procedurecall.getResultList();
			System.out.println("List :: " + list.size());
		} catch (Exception e) {
			// TODO: handle exception
			throw e;
		} finally {
			if (em != null) {
				em.close();
			}
		}
		return list;
	}

	@Override
	public List<Artreq_To_Dispatch_Report_With_Param> getArticleSchemeDirectToStokistReport(
			ArticleSchemeReportBean bean) {
		EntityManager em = null;
		List<Artreq_To_Dispatch_Report_With_Param> list = null;
		String stringLocation = "";

		try {

			em = emf.createEntityManager();
			StoredProcedureQuery procedurecall = em
					.createNamedStoredProcedureQuery("callartereq_to_dispatch_direct_to_stockist_report_with_param");

			for (String location : bean.getLocation()) {
				stringLocation += location + ",";
			}

//			System.err.println(stringLocation.substring(0, stringLocation.length()-1));			

			procedurecall.setParameter("piLOC_id", stringLocation.substring(0, stringLocation.length() - 1));
			procedurecall.setParameter("startdate", bean.getStDt());
			procedurecall.setParameter("enddate", bean.getEnDt());
			procedurecall.setParameter("MCustomer_CD", bean.getCustomer());
			procedurecall.setParameter("MSAP_Invoice", bean.getInvoice());
			procedurecall.setParameter("MLR_No", bean.getLrNo());
			procedurecall.setParameter("MArticle_Scheme", bean.getArtScheme());
			procedurecall.setParameter("MArticle_PROD_CD", bean.getArtName());
			procedurecall.setParameter("MSales_Product", bean.getSalesProduct());

			list = procedurecall.getResultList();
//			System.out.println("List :: " + list.size());
		} catch (Exception e) {
			e.printStackTrace();
			// TODO: handle exception
			throw e;
		} finally {
			if (em != null) {
				em.close();
			}
		}

//		System.err.println(list);
		return list;
	}

	@Override
	public List<Scheme_Desription_Bean> getArticleSchemeDescriptionReport(ArticleSchemeReportBean bean) {

		EntityManager em = null;
		String stringLocation = "";
		List<Scheme_Desription_Bean> list = null;

		try {

			em = emf.createEntityManager();
			StoredProcedureQuery procedurecall = em
					.createNamedStoredProcedureQuery("callARTREQ_TO_DISPATCH_SCHME_USED");

			for (String location : bean.getLocation()) {
				stringLocation += location + ",";
			}

			System.err.println("::::::::::::::::::" + stringLocation.substring(0, stringLocation.length() - 1));

			procedurecall.setParameter("piLOC_id", stringLocation.substring(0, stringLocation.length() - 1));
			procedurecall.setParameter("startdate", bean.getStDt());
			procedurecall.setParameter("enddate", bean.getEnDt());
			procedurecall.setParameter("MCustomer_CD", bean.getCustomer());
			procedurecall.setParameter("MSAP_Invoice", bean.getInvoice());
			procedurecall.setParameter("MLR_No", bean.getLrNo());
			procedurecall.setParameter("MArticle_Scheme", bean.getArtScheme());
			procedurecall.setParameter("MArticle_PROD_CD", bean.getArtName());
			procedurecall.setParameter("MSales_Product", bean.getSalesProduct());
			list = procedurecall.getResultList();
			System.out.println("List :: " + list.size());
		} catch (Exception e) {
			e.printStackTrace();
			// TODO: handle exception
			throw e;
		} finally {
			if (em != null) {
				em.close();
			}
		}

//		System.err.println(list);
		return list;
	}

	@Override
	public Map<String, Long> getArticleGraphData(String emp_id, String emp_loc, String sub_company_code)
			throws Exception {

		EntityManager em = null;
		List<Article_Graph_data_model> list = null;
		Map<String, Long> map = new HashMap<>();

		try {

			em = emf.createEntityManager();
			StoredProcedureQuery procedurecall = em.createNamedStoredProcedureQuery("callArticle_Graph_data_model");
			procedurecall.setParameter("pLoc_id", Long.valueOf(emp_loc));
			procedurecall.setParameter("puserid", emp_id);
			procedurecall.setParameter("psub_comp_cd", sub_company_code);

			list = procedurecall.getResultList();

			// seting graph values

			Long schemes_all_cfas = list.get(0).getCnt();
			Long schemes = list.get(1).getCnt();
			Long requests = list.get(2).getCnt();
			Long requests_approved = list.get(3).getCnt();
			Long invoice_generated = list.get(4).getCnt();
			Long invoice_delivered = list.get(5).getCnt();

			map.put("schemes_all_cfas", schemes_all_cfas);
			map.put("schemes", schemes);
			map.put("requests", requests);
			map.put("requests_approved", requests_approved);
			map.put("invoice_generated", invoice_generated);
			map.put("invoice_delivered", invoice_delivered);

			System.out.println("List :: " + list);
		} catch (Exception e) {
			// TODO: handle exception
			throw e;
		} finally {
			if (em != null) {
				em.close();
			}
		}

		return map;
	}

	@Override
	public List<DivMaster> get_Scheme_Division() throws Exception {
		List<DivMaster> beans = new ArrayList<>();
		Query query = null;
		String q = "select * from DIV_MASTER where DIV_status='A' and DIV_ID <> 33 order by DIV_DISP_NM";
		query = entityManager.createNativeQuery(q, DivMaster.class);
		beans = query.getResultList();

		System.err.println(beans);

		return beans;

	}

	@Override
	public List<trd_scheme_mst_hdr> get_Header_Scheme_By_Scheme_code(String schemecode) throws Exception {

//		select * from TRD_SCHEME_MST_HDR where TRD_SCHEME_CODE='SCM00000193'

		List<trd_scheme_mst_hdr> beans = new ArrayList<>();
		Query query = null;
		String q = "select * from TRD_SCHEME_MST_HDR where TRD_SCHEME_CODE=:schemecode";

		query = entityManager.createNativeQuery(q, trd_scheme_mst_hdr.class);
		query.setParameter("schemecode", schemecode);
		beans = query.getResultList();

		return beans;
	}

	@Override
	public List<FinYear> getfinyear() {

		List<Tuple> beans = new ArrayList<>();

		List<FinYear> FinYearbeans = new ArrayList<>();
		Query query = null;
		String q = "";
		try {
			q = "from  FinYear where  FIN_CURRENT ='Y' order by fin_year desc  ";
			query = entityManager.createQuery(q);
			FinYearbeans = query.getResultList();

		} catch (Exception e) {

			e.printStackTrace();// TODO: handle exception
			throw e;
		}

		System.out.println("FinYearbeans::::::" + FinYearbeans);
		return FinYearbeans;
	}

	@Override
	public List<trd_scheme_mst_hdr> get_HDR_Data_For_Validity_Extension(String empId) {

		Query query = null;
		StringBuffer sb = new StringBuffer();
		sb.append("from trd_scheme_mst_hdr t  where t.trd_sch_status='A' and t.created_by=:empId ");
		sb.append(" and t.trd_sch_slno not in (select distinct av.scheme_slno from Art_Sch_Vld_Ext as av where av.ext_appr_status in ('F','E') )");

		query = entityManager.createQuery(sb.toString(), trd_scheme_mst_hdr.class);
		query.setParameter("empId", empId);
		List<trd_scheme_mst_hdr> beans = query.getResultList();

		System.out.println(beans.size());

		return beans;

	}

	@Override
	public List<TRD_SCH_MST_DOCS> getDocsBySchemeSlno(Long trd_sch_slno) throws Exception {
		CriteriaBuilder builder = entityManager.getCriteriaBuilder();
		CriteriaQuery<TRD_SCH_MST_DOCS> query = builder.createQuery(TRD_SCH_MST_DOCS.class);
		Root<TRD_SCH_MST_DOCS> root = query.from(TRD_SCH_MST_DOCS.class);
		query.select(root).where(builder.equal(root.get("trd_sch_slno"), trd_sch_slno));
		return entityManager.createQuery(query).getResultList();
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public void deleteDocBySchAndSlno(Long sch_slno, Long doc_slno) throws Exception {

		Query query = null;
		String q = " delete from TRD_SCH_MST_DOCS where TRD_SCH_SLNO =:sch_slno and SLNO=:doc_slno";

		query = entityManager.createNativeQuery(q);
		query.setParameter("sch_slno", sch_slno);
		query.setParameter("doc_slno", doc_slno);

		query.executeUpdate();
	}

	
	
	@Override
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public Boolean deleteDoc(String docId, String dataMode) throws Exception {

		int i = 0;
		Boolean deleted = false;
		String table = "";

		if (dataMode.equalsIgnoreCase("x")) {
			table = "ARTICLE_SCH_VALID_EXT_DOCS";
		} else {
			table = "TRD_SCH_MST_DOCS";
		}
		Query query = null;
		// String q = " delete from TRD_SCHEME_MST_HDR where TRD_SCH_SLNO=" + trs_sl_no;
		String q = "delete from " + table + " where SLNO =" + docId;
		query = entityManager.createNativeQuery(q);

		i = query.executeUpdate();

		System.out.println(i);
		if (i > 0) {
			deleted = true;
		} else {
			deleted = false;
		}

		return deleted;
	}
	
	@Override
	public List<Art_Sch_Vld_Ext> getEnteredExtensions(String empId) throws Exception {
		List<Art_Sch_Vld_Ext> list = null;
		Query query = null;
		StringBuffer sb = new StringBuffer();
		sb.append(
				" select ext.*,trd.TRD_SCHEME_NAME,trd.APPLY_INV_FROM,trd.VALID_FROM_DT,trd.VALID_TO_DT from ARTICLE_SCH_VALID_EXT ext");
		sb.append(" inner join trd_scheme_mst_hdr trd on ext.SCHEME_SLNO = trd.TRD_SCH_SLNO");
		sb.append(" where EXT_APPR_STATUS = 'E' and EXT_ins_usr_id = :empId");
		query = entityManager.createNativeQuery(sb.toString(), Tuple.class);
		query.setParameter("empId", empId);
		List<Tuple> tuples = query.getResultList();
		if (tuples != null && tuples.size() > 0) {
			list = new ArrayList<Art_Sch_Vld_Ext>();
			for (Tuple tuple : tuples) {
				Art_Sch_Vld_Ext art_sch_vld_obj = new Art_Sch_Vld_Ext();
				art_sch_vld_obj.setDoc_to_replace(tuple.get("DOC_TO_REPLACE", BigDecimal.class) != null
						? tuple.get("DOC_TO_REPLACE", BigDecimal.class).longValue()
						: null);
				art_sch_vld_obj.setSlno(tuple.get("SLNO", BigDecimal.class).longValue());
				art_sch_vld_obj.setScheme_slno(tuple.get("SCHEME_SLNO", BigDecimal.class).longValue());
				art_sch_vld_obj.setValidity_ext_dt(tuple.get("VALIDITY_EXT_DT", Date.class));
				art_sch_vld_obj.setScheme_name(tuple.get("TRD_SCHEME_NAME", String.class));
				art_sch_vld_obj.setApply_invoice_from(tuple.get("APPLY_INV_FROM", Date.class));
				art_sch_vld_obj.setValid_from(tuple.get("VALID_FROM_DT", Date.class));
				art_sch_vld_obj.setValid_to(tuple.get("VALID_TO_DT", Date.class));
				art_sch_vld_obj.setRemarks(tuple.get("REMARKS", String.class));
				art_sch_vld_obj.setFilename(tuple.get("FILENAME", String.class));
				art_sch_vld_obj.setFile_path(tuple.get("FILE_PATH", String.class));
				list.add(art_sch_vld_obj);
			}
		}
		return list;
	}

	@Override
	public String getEmployeeDetailsByEmployeeID(String approver) {

		Object approver_name = "";
		Query query = null;

		String q = "select  CONCAT(emp_fnm +'  ',emp_lnm) from hr_m_employee  where  emp_id =:approver";

		query = entityManager.createNativeQuery(q);
		query.setParameter("approver", approver);
		approver_name = query.getSingleResult();

		System.err.println("approver_name " + approver_name.toString());

		return approver_name.toString();
	}

	@Override
	public List<String> getEmployeeEmail(String empID) {
		// TODO Auto-generated method stub
		List<String> toList = new ArrayList<>();
		Query query = null;

		String q = "select ld_email from am_m_login_detail  where  ld_emp_cb_id =:empID ";
		query = entityManager.createNativeQuery(q.toString());
		query.setParameter("empID", empID);
		toList = query.getResultList();

//		System.err.println("approver_name "+approver_names.toString());

		return toList;
	}

	@Override
	public List<Articele_Scheme_Approved_Data> getApprovedData(String schemeDivId, String tranType, Long hdr_slno) {
		List<Tuple> List = new ArrayList<>();
		Query query = null;

		List<Articele_Scheme_Approved_Data> Articele_Scheme_Approved_DataList = new ArrayList<>();

		StringBuffer sb = new StringBuffer();
//		sb.append(" select distinct cpi.tran_ref_id AS cpi_tran_ref_id,");
//		sb.append(" cpi.process_id AS cpi_process_id,");
//		sb.append(" cpi.completed_on AS cpi_completed_on,");
//		sb.append(" cpi.started_by AS cpi_started_by,");
//		sb.append(" cpi.status AS cpi_status,");
//		sb.append(" cpi.tran_type AS cpi_tran_type,");
//		sb.append(" tmd.TASK_MASTER_DTL_ID AS tmd_task_master_dtl_id,");
//		sb.append(" tmd.task_id AS tmd_task_id, cta.APPR_LEVEL as APPR_LEVEL, ");
//		sb.append(" tmd.IDENTITYLINKTYPE_VAL AS tmd_identitylinktype_val,");
//		sb.append(" CONCAT(CONCAT(hm.emp_fnm,' '),hm.emp_lnm) AS APPR_NM,");
//		sb.append(" cta.new_mod_date AS new_mod_date"); // Include the column used in ORDER BY
//		sb.append(" from CUSTOM_PROCESS_INSTANCE cpi");
//		sb.append(" join CUSTOM_TASK_archive cta on cta.process_id=cpi.process_id");
//		sb.append(" join tasks_master tm on tm.tran_type=cpi.TRAN_TYPE");
//		sb.append(" join task_master_dtl tmd on tmd.task_id=tm.task_master_id");
//		sb.append(" join hr_m_employee hm on hm.emp_id = tmd.IDENTITYLINKTYPE_VAL");
//		sb.append(" where cpi.tran_ref_id=:hdr_slno and cpi.tran_type =:tranType and tm.inv_grp_id=:schemeDivId ");
////		sb.append(" and cpi.completed_on is null");
//		sb.append(" order by cta.new_mod_date");
		
		
		sb.append(" select distinct cpi.tran_ref_id AS cpi_tran_ref_id,");
		sb.append(" cpi.process_id AS cpi_process_id,");
		sb.append(" cpi.completed_on AS cpi_completed_on,");
		sb.append(" cpi.started_by AS cpi_started_by,");
		sb.append(" cpi.status AS cpi_status,");
		sb.append(" cpi.tran_type AS cpi_tran_type,");
		sb.append(" tmd.TASK_MASTER_DTL_ID AS tmd_task_master_dtl_id,");
		sb.append(" tmd.task_id AS tmd_task_id, cta.APPR_LEVEL as APPR_LEVEL,");
		sb.append(" tmd.IDENTITYLINKTYPE_VAL AS tmd_identitylinktype_val,");
		sb.append(" CONCAT(CONCAT(hm.emp_fnm,' '),hm.emp_lnm) AS APPR_NM,");
		sb.append(" cta.new_mod_date AS new_mod_date");
		sb.append(" from CUSTOM_PROCESS_INSTANCE cpi");
		sb.append(" join CUSTOM_TASK_archive cta on cta.process_id=cpi.process_id");
		sb.append(" join tasks_master tm on tm.tran_type=cpi.TRAN_TYPE");
		sb.append(" join task_master_dtl tmd on tmd.task_id=tm.task_master_id");
		sb.append(" join hr_m_employee hm on hm.emp_id = tmd.IDENTITYLINKTYPE_VAL");
		sb.append(" where cpi.tran_ref_id=2 and cpi.tran_type =300 and tm.inv_grp_id=1");
		sb.append(" and cpi.PROCESS_ID = (");
		sb.append(" select max(cpi.PROCESS_ID)");
		sb.append(" from CUSTOM_PROCESS_INSTANCE cpi");
		sb.append(" join CUSTOM_TASK_archive cta on cta.process_id=cpi.process_id");
		sb.append(" join tasks_master tm on tm.tran_type=cpi.TRAN_TYPE");
		sb.append(" join task_master_dtl tmd on tmd.task_id=tm.task_master_id");
		sb.append(" join hr_m_employee hm on hm.emp_id = tmd.IDENTITYLINKTYPE_VAL");
		sb.append(" where cpi.tran_ref_id=:hdr_slno and cpi.tran_type =:tranType and tm.inv_grp_id=:schemeDivId ");
		sb.append(" group by cpi.TRAN_REF_ID )");
		sb.append(" order by cta.new_mod_date");
		
		query = entityManager.createNativeQuery(sb.toString(), Tuple.class);
		query.setParameter("schemeDivId", Long.valueOf(schemeDivId));
		query.setParameter("tranType", tranType);
		query.setParameter("hdr_slno", hdr_slno);
		List<Tuple> list = query.getResultList();

		if (list.size() > 0) {
			for (Tuple t : list) {
				Articele_Scheme_Approved_Data appr_data = new Articele_Scheme_Approved_Data();

				appr_data.setAPPR_LEVEL(t.get("APPR_LEVEL", BigDecimal.class).toString()); // Updated alias
				appr_data.setAPPR_NM(t.get("APPR_NM", String.class));
				appr_data.setNEW_MOD_DATE(t.get("NEW_MOD_DATE", Date.class));

				Articele_Scheme_Approved_DataList.add(appr_data);

			}

			System.err.println("Articele_Scheme_Approved_DataList::" + Articele_Scheme_Approved_DataList);

		}

		return Articele_Scheme_Approved_DataList;
	}

	@Override
	public Integer getMaxApprLevelByTranTypeAndInvGrpId(String tran_type, Long inv_grp_id) throws Exception {
		String qry_str = "select MAX(APPR_LEVEL) from TASKS_MASTER where INV_GRP_ID = :inv_grp_id and TRAN_TYPE = :tran_type";
		Query query = entityManager.createNativeQuery(qry_str);
		query.setParameter("tran_type", tran_type);
		query.setParameter("inv_grp_id", inv_grp_id);
		BigDecimal max_appr_lvl = (BigDecimal) query.getSingleResult();
		return max_appr_lvl.intValue();
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public void unlock_all_article_product() {
		Query query = null;
		String q = " delete from SCM_MST_PROD_LOCK";
		query = entityManager.createNativeQuery(q);
		query.executeUpdate();

	}
	@Override
	public List<ArticleDocsBean> get_Docs_Details_For_EXT(String schemeslno) {
	
		
		StringBuffer sb=new StringBuffer();

		sb.append(" select  SCHEME_SLNO as SCHEME_SLNO ,FILENAME as FILENAME, FILETYPE as FILETYPE, SLNO as SLNO ,FILE_PATH as FILE_PATH");
		sb.append(" from ARTICLE_SCH_VALID_EXT_DOCS where SCHEME_EXC_ID=:schemeslno");

		Query query = entityManager.createNativeQuery(sb.toString(), Tuple.class);
		
		query.setParameter("schemeslno", schemeslno);
		List<Tuple> tuples = query.getResultList();

		List<ArticleDocsBean> articleDocsBeans = new ArrayList<>();
		for (Tuple t : tuples) {

			ArticleDocsBean articleDocsBean = new ArticleDocsBean();
			articleDocsBean.setFileName(t.get("FILENAME").toString());
			articleDocsBean.setFileType(t.get("FILETYPE").toString());
			articleDocsBean.setSlNo(t.get("SLNO").toString());
			articleDocsBean.setFilePath(t.get("FILE_PATH").toString());
			articleDocsBean.setScheme_slno(t.get("SCHEME_SLNO").toString());

			articleDocsBeans.add(articleDocsBean);
		}

		return articleDocsBeans;

	


	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public void deleteDocBySchemeSlnumber(Long slno) throws Exception {
	
		
		

			
			Query query = null;
			String q = " delete from TRD_SCH_MST_DOCS where TRD_SCH_SLNO=:slno";
			query = entityManager.createNativeQuery(q);
			query.setParameter("slno", slno);
			int i=query.executeUpdate();
			
		
	}

	@Override
	public List<ARTICLE_SCH_VALID_EXT_DOCS> getarticleEsxTendedSchemeDocsData(Long schemeslno) {
	StringBuffer sb=new StringBuffer();
	List<ARTICLE_SCH_VALID_EXT_DOCS> articleDocsBeans = new ArrayList<>();
	
	try {
		
	
		sb.append("  from ARTICLE_SCH_VALID_EXT_DOCS where SCHEME_EXC_ID=:SCHEME_EXC_ID");

		Query 	query = entityManager.createQuery(sb.toString(), ARTICLE_SCH_VALID_EXT_DOCS.class);
		
		query.setParameter("SCHEME_EXC_ID", schemeslno);
		articleDocsBeans = query.getResultList();
		System.err.println(articleDocsBeans);
			
	}catch (Exception e) {
		// TODO: handle exception
//		e.printStackTrace();
		throw e;
	}
	
	return articleDocsBeans;
	}
	
	
	@Override
	public Long getSchemeCodeBySchemeExId(String ex_schemeslno)  throws Exception{

		String qry_str = "select SCHEME_SLNO from  ARTICLE_SCH_VALID_EXT where SLNO=:ex_schemeslno";
		Query query = entityManager.createNativeQuery(qry_str);
		query.setParameter("ex_schemeslno", ex_schemeslno);

		Object obj = query.getSingleResult();
		return Long.valueOf(obj.toString());

	}
	@Override
	public List<trd_scheme_mst_hdr> get_all_scheme() throws Exception {
		// TODO Auto-generated method stub
		StringBuffer sb = new StringBuffer();
		List<trd_scheme_mst_hdr> trd_scheme_mst_hdr =new ArrayList<>();



		sb.append("select TRD_SCH_SLNO,TRD_SCHEME_NAME from TRD_SCHEME_MST_HDR where TRD_SCH_STATUS='A'	");

		Query query = entityManager.createNativeQuery(sb.toString(), Tuple.class);
		
		List<Tuple> tuples = query.getResultList();

		for (Tuple t : tuples) {

			trd_scheme_mst_hdr articleBean = new trd_scheme_mst_hdr();
			articleBean.setTrd_sch_slno(Long.valueOf(t.get("TRD_SCH_SLNO",Integer.class)));
			articleBean.setTrd_scheme_name(t.get("TRD_SCHEME_NAME").toString());

			trd_scheme_mst_hdr.add(articleBean);
		}

		return trd_scheme_mst_hdr;

	}

	@Override
	public Long get_Cycle_for_Scheme_Master(Long trd_slno) throws Exception {
		

		String qry_str = "select cycle_no from TRD_SCHEME_MST_HDR where TRD_SCH_SLNO=:trd_slno";
		Query query = entityManager.createNativeQuery(qry_str);
		query.setParameter("trd_slno", trd_slno);

		Object obj = query.getSingleResult();
		return Long.valueOf(obj.toString());
		
		

	
}

	@Override
	public List<Articel_Scheme_Approved_Data_Bean> generateArticleSchemeDataHeadr(Long trd_slno,Long cycle) throws Exception {

		List<Tuple> tuples = new ArrayList<>();
		
		List<Articel_Scheme_Approved_Data_Bean> beansList=new ArrayList<>(); 

		try {

			StringBuffer sb = new StringBuffer();
			
			
			if(cycle==1) {
				
				sb.append("select * from   TRD_SCHEME_MST_HDR  where  TRD_SCH_SLNO=:trd_slno  and TRD_SCH_STATUS='A'  ");
			}else {
				sb.append("select * from   TRD_SCHEME_MST_HDR_ARCHIVE  where  TRD_SCH_SLNO=:trd_slno  and TRD_SCH_STATUS='A'  and cycle_no=1  ");
			}
			
			

			Query query = entityManager.createNativeQuery(sb.toString(), Tuple.class);
			query.setParameter("trd_slno", trd_slno);
			tuples = query.getResultList();

			if (tuples.size() > 0) {

				for (Tuple t : tuples) {

					Articel_Scheme_Approved_Data_Bean bean = new Articel_Scheme_Approved_Data_Bean();

					bean.setTRD_SCH_SLNO(t.get("TRD_SCH_SLNO",Integer.class).toString());
					bean.setCOMPANY_CD(t.get("COMPANY_CD",String.class));
					bean.setREMARKS(t.get("REMARKS",String.class));
					bean.setSUB_COMP_CD(t.get("SUB_COMP_CD",String.class));
					bean.setSUB_COMP_ID(t.get("SUB_COMP_ID",BigDecimal.class).toString());
					bean.setSCHEME_APPTYPE(t.get("SCHEME_APPTYPE",String.class));
					bean.setLOC_ID(t.get("LOC_ID",BigDecimal.class).toString());
					bean.setTRD_SCHEME_ID(t.get("TRD_SCHEME_ID",BigDecimal.class).toString());
					bean.setTRD_SCHEME_CODE(t.get("TRD_SCHEME_CODE",String.class));
					bean.setTRD_SCHEME_NAME(t.get("TRD_SCHEME_NAME",String.class));
					bean.setTRD_SCHEME_DESCR(t.get("TRD_SCHEME_DESCR",String.class));
					bean.setAPPLY_INV_FROM(t.get("APPLY_INV_FROM",Date.class));		
					bean.setVALID_FROM_DT(t.get("VALID_FROM_DT",Date.class));
					bean.setVALID_TO_DT(t.get("VALID_TO_DT",Date.class));
					bean.setAPPLY_TO(t.get("APPLY_TO",Character.class).toString());			
					bean.setTRD_SCH_TYPE(t.get("TRD_SCH_TYPE",Character.class).toString());
					bean.setINVOICE_TYPE(t.get("INVOICE_TYPE",Character.class).toString());
					bean.setARTICLE_PROD_CD(t.get("ARTICLE_PROD_CD",String.class));
					bean.setARTICLE_PROD_ID(t.get("ARTICLE_PROD_ID",BigDecimal.class).toString());			
					bean.setBILLED_VALUE(t.get("BILLED_VALUE",BigDecimal.class));
					bean.setARTICLE_QTY_PER_TOT_VAL(t.get("ARTICLE_QTY_PER_TOT_VAL",BigDecimal.class).longValue());
					bean.setARTICLE_PROD_RATE(t.get("ARTICLE_PROD_RATE",BigDecimal.class));
					
					String intput="";
					if(t.get("cycle_no")==null){
						intput=	"0";
					}
					else {
						intput=t.get("cycle_no").toString();
					}
					
					
					double doubleValue = Double.parseDouble(intput);
			        long longValue = Math.round(doubleValue);
			        
					bean.setCycle_no(longValue);	
					bean.setSCHEME_DIV_ID(t.get("SCHEME_DIV_ID",Integer.class).toString());
//					bean.setPER_SALE_QTY_BILLED(t.get("PER_SALE_QTY_BILLED",BigDecimal.class).longValue());
//					bean.setPER_SALE_QTY_FREE(t.get("PER_SALE_QTY_FREE",BigDecimal.class).longValue());
//					bean.setPER_SALE_QTY_TOT(t.get("PER_SALE_QTY_TOT",BigDecimal.class).longValue());
//					bean.setARTICLE_QTY(t.get("ARTICLE_QTY",BigDecimal.class).longValue());			
//					bean.setCREATED_BY(t.get("CREATED_BY",String.class));
//					bean.setCREATED_DATE(t.get("CREATED_DATE",Date.class));
//					bean.setLAST_MOD_BY(t.get("LAST_MOD_BY",String.class));
//					bean.setLAST_MOD_DATE(t.get("LAST_MOD_DATE",Date.class));
//					bean.setTRD_SCH_STATUS(t.get("TRD_SCH_STATUS",Character.class).toString());
//					bean.setREMARKS(t.get("REMARKS",String.class));
//					bean.setFINYEAR(t.get("FINYEAR",String.class));
//					bean.setSCHEME_DIV_ID(t.get("SCHEME_DIV_ID",Integer.class).toString());
//					bean.setSPECIFIC_BRAND_SCHEME(t.get("SPECIFIC_BRAND_SCHEME",Character.class).toString());			
					beansList.add(bean);

				}

			}
			
			
			
			System.err.println(beansList);
		}

		catch (Exception e) {
			e.printStackTrace();
			
		}

		return beansList;
	}

	
	
	
	@Override
	public List<Articel_Scheme_Approved_Data_Bean> generateArticleSchemeData_Detail(Long trd_slno) throws Exception {

		List<Tuple> tuples = new ArrayList<>();
		
		List<Articel_Scheme_Approved_Data_Bean> beansList=new ArrayList<>(); 

		try {

			StringBuffer sb = new StringBuffer();
			sb.append(
					" select * from TRD_SCHEME_MST_DTL where TRD_SCH_SLNO=:trd_slno");

			Query query = entityManager.createNativeQuery(sb.toString(), Tuple.class);
			query.setParameter("trd_slno", trd_slno);
			tuples = query.getResultList();

			if (tuples.size() > 0) {

				for (Tuple t : tuples) {

					Articel_Scheme_Approved_Data_Bean bean = new Articel_Scheme_Approved_Data_Bean();

					
					bean.setTRD_SCHEME_DTL_ID(t.get("TRD_SCHEME_DTL_ID",Integer.class).toString());
					bean.setSALE_PROD_ID(t.get("SALE_PROD_ID",BigDecimal.class).toString());
					bean.setSALE_PROD_CODE_SG(t.get("SALE_PROD_CODE_SG",String.class));
					bean.setSALE_PROD_CODE_ERP(t.get("SALE_PROD_CODE_ERP",String.class));
				
					bean.setPER_SALE_QTY_BILLED(t.get("PER_SALE_QTY_BILLED",BigDecimal.class).longValue());
					bean.setPER_SALE_QTY_FREE(t.get("PER_SALE_QTY_FREE",BigDecimal.class).longValue());
					bean.setPER_SALE_QTY_TOT(t.get("PER_SALE_QTY_TOT",BigDecimal.class).longValue());
					bean.setARTICLE_QTY(t.get("ARTICLE_QTY",BigDecimal.class).longValue());
				
					
		
					beansList.add(bean);

				}

			}
			
			
			
			System.err.println(beansList);
		}

		catch (Exception e) {
			e.printStackTrace();
			
		}

		return beansList;
	}

	
	
	
	@Override
	public List<Articel_Scheme_Approved_Data_Bean> generateArticleSchemeData_Docs(Long trd_slno, Long cycle) throws Exception {

		List<Tuple> tuples = new ArrayList<>();
		
		List<Articel_Scheme_Approved_Data_Bean> beansList=new ArrayList<>(); 

		try {

			StringBuffer sb = new StringBuffer();
			
			if(cycle==1) {
				sb.append("select * from  TRD_SCH_MST_DOCS  where TRD_SCH_SLNO=:trd_slno ");
			}
			else {
				sb.append("select * from   TRD_SCH_MST_DOCS_ARCHIVE  where  TRD_SCH_SLNO=:trd_slno and cycle_no =1 ");
			}
			

			Query query = entityManager.createNativeQuery(sb.toString(), Tuple.class);
			query.setParameter("trd_slno", trd_slno);
			tuples = query.getResultList();

			if (tuples.size() > 0) {

				for (Tuple t : tuples) {

					Articel_Scheme_Approved_Data_Bean bean = new Articel_Scheme_Approved_Data_Bean();

					
					bean.setFILENAME(t.get("FILENAME",String.class));
					bean.setFILEPATH(t.get("FILEPATH",String.class));
					bean.setFILETYPE(t.get("FILETYPE")==null?"":t.get("FILETYPE",Character.class).toString());
					
					
					System.err.println( t.get("cycle_no"));
					bean.setCycle_no((  t.get("cycle_no",BigDecimal.class).longValue()));
					bean.setDOC_ins_dt(t.get("DOC_ins_dt",Date.class));
					bean.setDOC_ins_usr_id(t.get("DOC_ins_usr_id",String.class));
					bean.setDOC_ins_ip_addr(t.get("DOC_ins_ip_addr",String.class));
					beansList.add(bean);

				}

			}
			
			
			
			System.err.println(beansList);
		}

		catch (Exception e) {
			e.printStackTrace();
			
		}

		return beansList;
	}

	
	
	@Override
	public List<Article_Scheme_Extent_Bean> generateArticleSchemeEXTHeader(Long trd_slno) {

		
		List<Tuple> tuples = new ArrayList<>();		
		List<Article_Scheme_Extent_Bean> Extent_BeanList=new ArrayList<>(); 
		StringBuffer sb=new StringBuffer();
		
		
		try {
		sb.append(" select * from ARTICLE_SCH_VALID_EXT where SCHEME_SLNO =:trd_slno  order by SLNO ");


		
		
		Query query = entityManager.createNativeQuery(sb.toString(), Tuple.class);
		query.setParameter("trd_slno", trd_slno);
		tuples = query.getResultList();

		if (tuples.size() > 0) {

			for (Tuple t : tuples) {

				Article_Scheme_Extent_Bean bean=new Article_Scheme_Extent_Bean();
				bean.setSLNO(t.get("SLNO",BigDecimal.class).longValue());
				bean.setSCHEME_SLNO(t.get("SCHEME_SLNO",BigDecimal.class).longValue());
				bean.setVALIDITY_EXT_DT(t.get("VALIDITY_EXT_DT",Date.class));
				bean.setREMARKS(t.get("REMARKS",String.class));
				bean.setFIN_YEAR(t.get("FIN_YEAR",String.class));
				bean.setCOMPANY_CD(t.get("COMPANY_CD",String.class));
				bean.setSCHEME_DIV_ID(t.get("SCHEME_DIV_ID",BigDecimal.class).toString());
				bean.setEXT_STATUS(t.get("EXT_STATUS",String.class));
				bean.setEXT_APPR_STATUS  (t.get("EXT_APPR_STATUS",String.class));
				bean.setEXT_APPR_STATUS  (t.get("EXT_APPR_STATUS",String.class));
				
				
				
				
//				bean.setFILENAME(t.get("FILENAME",String.class));
//				bean.setFILETYPE(t.get("FILETYPE",String.class));
//				bean.setFILE_PATH(t.get("FILE_PATH",String.class));
//				bean.setDOC_ins_usr_id(t.get("DOC_ins_usr_id",String.class));
//				bean.setDOC_ins_dt(t.get("DOC_ins_dt",Date.class));
//				bean.setDOC_mod_usr_id(t.get("DOC_mod_usr_id",String.class));
//				bean.setDOC_mod_usr_dt(t.get("DOC_mod_usr_dt",Date.class));
//				bean.setINS_ip_addr(t.get("INS_ip_addr",String.class));
				
				Extent_BeanList.add(bean);
			}

		}
		
		
		
		System.err.println(Extent_BeanList);
	}

	catch (Exception e) {
		e.printStackTrace();
	}

	return Extent_BeanList;
		
	}

	
	@Override
	public String get_Article_Prod_Name(String article_PROD_ID) {
		

		String qry_str = "select SMP_PROD_NAME from smpitem  where SMP_PROD_ID=:article_PROD_ID ";
		Query query = entityManager.createNativeQuery(qry_str);
		query.setParameter("article_PROD_ID", article_PROD_ID);

		Object obj = query.getSingleResult();
		return obj.toString();
		
		
	}

	@Override
	public String get_sale_prod_name(String sale_PROD_ID) {
		// TODO Auto-generated method stub

		String qry_str = "select SALE_PROD_NAME from SALEPROD where SALE_PROD_ID=:sale_PROD_ID";
		Query query = entityManager.createNativeQuery(qry_str);
		query.setParameter("sale_PROD_ID", sale_PROD_ID);

		Object obj = query.getSingleResult();
		return obj.toString();
	}

	@Override
	public List<Article_Scheme_Extent_Bean> generateArticleSchemeEXT_Doc(Long trd_slno) {
	
		
		List<Tuple> tuples = new ArrayList<>();		
		List<Article_Scheme_Extent_Bean> Extent_BeanList=new ArrayList<>(); 
		StringBuffer sb=new StringBuffer();
		try {
		sb.append(" select * from ARTICLE_SCH_VALID_EXT_DOCS where SCHEME_SLNO =:trd_slno");
		Query query = entityManager.createNativeQuery(sb.toString(), Tuple.class);
		query.setParameter("trd_slno", trd_slno);
		tuples = query.getResultList();

		if (tuples.size() > 0) {

			for (Tuple t : tuples) {

				Article_Scheme_Extent_Bean bean=new Article_Scheme_Extent_Bean();
//				bean.setSLNO(t.get("SLNO",BigDecimal.class).longValue());
//				bean.setSCHEME_SLNO(t.get("SCHEME_SLNO",BigDecimal.class).longValue());
//				bean.setVALIDITY_EXT_DT(t.get("VALIDITY_EXT_DT",Date.class));
//				bean.setREMARKS(t.get("REMARKS",String.class));
//				bean.setFIN_YEAR(t.get("FIN_YEAR",String.class));
//				bean.setCOMPANY_CD(t.get("COMPANY_CD",String.class));
//				bean.setSCHEME_DIV_ID(t.get("SCHEME_DIV_ID",BigDecimal.class).toString());
//				bean.setEXT_STATUS(t.get("EXT_STATUS",String.class));
//				bean.setEXT_APPR_STATUS  (t.get("EXT_APPR_STATUS",String.class));
				
				bean.setFILENAME(t.get("FILENAME",String.class));
				bean.setFILETYPE(t.get("FILETYPE",String.class).toString());
				bean.setFILE_PATH(t.get("FILE_PATH",String.class));
				
				Extent_BeanList.add(bean);
			}

		}
		
		
		
		System.err.println(Extent_BeanList);
	}

	catch (Exception e) {
		e.printStackTrace();
	}

	return Extent_BeanList;
	}
	
	@Override
	public boolean CheckFileExistForTheScheme(String hdr_slno) throws Exception {

		StringBuffer sb = new StringBuffer();
		List<TRD_SCH_MST_DOCS> trd_sch_mst_docs = new ArrayList<>();

		try {

			sb.append("  from TRD_SCH_MST_DOCS where TRD_SCH_SLNO=:hdr_slno");

			Query query = entityManager.createQuery(sb.toString(), TRD_SCH_MST_DOCS.class);

			query.setParameter("hdr_slno", hdr_slno);
			trd_sch_mst_docs = query.getResultList();

			if (trd_sch_mst_docs.size() > 0) {
				return true;
			}

		} catch (Exception e) {
			// TODO: handle exception

			e.printStackTrace();
		}

		return false;

	}
	
	
	@Override
	public List<trd_scheme_mst_hdr> get_scheme_for_closure(String emp_id) {
		StringBuffer sb = new StringBuffer();
		List<trd_scheme_mst_hdr> trd_scheme_mst_hdr = new ArrayList<>();
		try {
			sb.append("  from trd_scheme_mst_hdr where trd_sch_status ='A'and created_by =:emp_id and closure_date is null order by trd_scheme_name ");
			Query query = entityManager.createQuery(sb.toString(), trd_scheme_mst_hdr.class);
			query.setParameter("emp_id", emp_id);
			trd_scheme_mst_hdr = query.getResultList();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return trd_scheme_mst_hdr;
	}

	@Override
	public List<trd_scheme_mst_hdr> getAssociatedSchemsByEmpId(String empId) throws Exception {
		StringBuffer sb = null;
		List<trd_scheme_mst_hdr> trd_scheme_mst_hdr = null;
		EntityManager em = null;
		try {
			em = this.emf.createEntityManager();
			sb = new StringBuffer();
			sb.append(" select TRD_SCH_SLNO , TRD_SCHEME_NAME from TRD_SCHEME_MST_HDR,");
			sb.append(" hr_m_employee hrm where hrm.emp_id = :emp_id ");
			sb.append(" and ((CREATED_BY = :emp_id)");
			sb.append(" OR (SCHEME_DIV_ID IN (");
			sb.append(" select distinct INV_GRP_ID from tasks_master tm");
			sb.append(" inner join TASK_MASTER_DTL dt ");
			sb.append(" on dt.TASK_ID = tm.TASK_MASTER_ID");
			sb.append(" where tm.TRAN_TYPE = ");
			sb.append(" (select TRAN_TYPE from ACC_TRAN_TYPE where FULL_TRAN_NAME = 'SCM')");
			sb.append(" and IDENTITYLINKTYPE_VAL = :emp_id))");
			sb.append(" OR (hrm.emp_egrp_id NOT IN (");
			sb.append(" 'EG0033','EG0032','EG0031',");
			sb.append(" 'EG0030','EG0021','EG0020')))");
			sb.append("OR (hrm.emp_id = 'E00672')");
			
			Query query = entityManager.createNativeQuery(sb.toString(), Tuple.class);
			query.setParameter("emp_id", empId);
			
			List<Tuple> list = query.getResultList();
			if(list!=null && list.size()>0) {
				
				trd_scheme_mst_hdr = new ArrayList<>();
				for(Tuple t : list) {
					trd_scheme_mst_hdr trd_scheme_mst_hdr_obj = new trd_scheme_mst_hdr();
					trd_scheme_mst_hdr_obj.setTrd_sch_slno(t.get("TRD_SCH_SLNO",Integer.class).longValue());
					trd_scheme_mst_hdr_obj.setTrd_scheme_name(t.get("TRD_SCHEME_NAME",String.class));
					trd_scheme_mst_hdr.add(trd_scheme_mst_hdr_obj);
				}
			}
		}
		finally {
			if(em!=null)em.close();
		}
		return trd_scheme_mst_hdr;
	}

	@Override
	public List<New_Stockist_wiseScheme_request_model> getArticleSchemeDirectToStockistReportNewHeaderData(
			ArticleSchemeReportBean bean) throws Exception {

		EntityManager em = null;
		List<New_Stockist_wiseScheme_request_model> list = null;
		String stringLocation = "";

		try {

			em = emf.createEntityManager();
			StoredProcedureQuery procedurecall = em
					.createNamedStoredProcedureQuery("callartereq_to_dispatch_direct_to_stockist_report_with_paramNewHeader");

			for (String location : bean.getLocation()) {
				stringLocation += location + ",";
			}

			System.err.println(stringLocation.substring(0, stringLocation.length()-1));			

			
	
	            
	            // Create a SimpleDateFormat object to parse the original date format
	            SimpleDateFormat inputFormat = new SimpleDateFormat("dd/MM/yyyy");
	            
	            // Parse the original string into a Date object
	            Date stdate = inputFormat.parse( bean.getStDt());
	            Date enddate = inputFormat.parse( bean.getEnDt());
	            
	            // Create another SimpleDateFormat object to format the Date object to the desired format
	            SimpleDateFormat outputFormat = new SimpleDateFormat("yyyy-MM-dd");
	            
	            // Format the Date object into the new format
	            String formattedDate = outputFormat.format(stdate);
	            
	            Date parsedDate = outputFormat.parse(formattedDate);
			
	            
	            System.out.println(outputFormat.format(stdate)+":::gfdgdf::::"+outputFormat.format(enddate) +"MArticle_SchemeID  "+bean.getArtScheme());
			
			
			procedurecall.setParameter("piLOC_id", stringLocation.substring(0, stringLocation.length() - 1));
			procedurecall.setParameter("startdate", outputFormat.format(stdate));
			procedurecall.setParameter("enddate",outputFormat.format(enddate));
			procedurecall.setParameter("MCustomer_CD", bean.getCustomer());
			procedurecall.setParameter("MSAP_Invoice", bean.getInvoice());
			procedurecall.setParameter("MLR_No", bean.getLrNo());
			procedurecall.setParameter("MArticle_SchemeID", bean.getArtScheme());
			procedurecall.setParameter("MArticle_PROD_CD", bean.getArtName());
			procedurecall.setParameter("MSales_Product", bean.getSalesProduct());
			procedurecall.setParameter("FIN_YEAR_FLAG", bean.getFinyearflag());
			procedurecall.setParameter("FIN_YEAR_ID", bean.getFinyer_id());

			list = procedurecall.getResultList();
//			System.out.println("List :: " + list.size());
		} catch (Exception e) {
			e.printStackTrace();
			// TODO: handle exception
			throw e;
		} finally {
			if (em != null) {
				em.close();
			}
		}

//		System.err.println(list);
		return list;
	}


	@Override
	public List<New_stockist_wise_scheme_request_supply_Detail_model> getArticleSchemeDirectToStockistReportnew_DetailData(String allocdtl_id, String article_prod_id) {
		

		
		System.err.println(allocdtl_id);
		System.err.println(article_prod_id);
		System.err.println("its calling");
		EntityManager em = null;
		List<New_stockist_wise_scheme_request_supply_Detail_model> list = null;
		String stringLocation = "";

		try {

			em = emf.createEntityManager();
			StoredProcedureQuery procedurecall = em
					.createNamedStoredProcedureQuery("callartereq_to_dispatch_direct_to_stockist_report_with_paramNewDetail");
			procedurecall.setParameter("ALLOCID", allocdtl_id);
			procedurecall.setParameter("ART_PRDID", article_prod_id);

			list = procedurecall.getResultList();
//			System.out.println("List :: " + list.size());
		} catch (Exception e) {
			e.printStackTrace();
			// TODO: handle exception
			throw e;
		} finally {
			if (em != null) {
				em.close();
			}
		}

//		System.err.println(list);
		return list;
		
		
	}

	@Override
	public List<New_stockist_wise_scheme_request_supply_Detail_model> getDispatchDetailData() {
		// TODO Auto-generated method stub
	
		StringBuffer sb = null;
		List<New_stockist_wise_scheme_request_supply_Detail_model> details  = new ArrayList<>();
		EntityManager em = null;
		try {
			em = this.emf.createEntityManager();
			sb = new StringBuffer();
			sb.append("  SELECT * FROM ALLOCID_DSP ");

			Query query = entityManager.createNativeQuery(sb.toString(), Tuple.class);
			
			List<Tuple> list = query.getResultList();
			if(list!=null && list.size()>0) {
				
				
				for(Tuple t : list) {
					New_stockist_wise_scheme_request_supply_Detail_model detail = new New_stockist_wise_scheme_request_supply_Detail_model();
					
					
					detail.setAllocdtl_alloc_id(  t.get("ALLOCDTL_ALLOC_ID").toString());
		
	
					detail.setAllocdtl_id(t.get("ALLOCDTL_ID").toString());
					if(t.get("DSP_CHALLAN_NO") != null) {
						detail.setDsp_challan_no(t.get("DSP_CHALLAN_NO").toString());
					}
				
					
					if(t.get("DSP_DT") != null) {
						detail.setDsp_dt(t.get("DSP_DT").toString());
					}
					
				
				detail.setDsp_lr_no(t.get("DSP_LR_NO").toString());
				detail.setDsp_lr_dt(	t.get("DSP_LR_DT").toString());
				
				if(t.get("PROD_ID") != null) {
					detail.setProd_id(	t.get("PROD_ID").toString());
				}
				
				
				if(t.get("PROD_NAME") != null) {
					detail.setProd_name(	t.get("PROD_NAME").toString());
				}
				
				
				
				if(t.get("QTY_SUPPLIED") != null) {
					detail.setQty_supplied((t.get("QTY_SUPPLIED",BigDecimal.class).longValue()));
				}
				
				
				if(t.get("PENDING_QTY") != null) {
					detail.setPending_qty( t.get("PENDING_QTY",BigDecimal.class).longValue());
				}
				
				
				if(t.get("E_INV_NO") != null) {
					detail.setE_inv_no(t.get("E_INV_NO").toString());
				}
				
				
				if(t.get("E_INV_DATE") != null) {
					detail.setE_inv_date(t.get("E_INV_DATE").toString());
				}
				
				
				if(t.get("TRN_EWAYBILLNO") != null) {
					detail.setTrn_ewaybillno(	t.get("TRN_EWAYBILLNO").toString());
				}
				
				
				if(t.get("TRN_EWAYBILLDT") != null) {
					detail.setTrn_ewaybilldt(	t.get("TRN_EWAYBILLDT").toString());
				}
				
				
				
				
			
				
				
				

				details.add(detail);
				}
			}
		}
		finally {
			if(em!=null)em.close();
		}
		return details;
	}
}
