package com.excel.repository;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.Tuple;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.excel.bean.ArtSchemeBeanForApprNewLogic;
import com.excel.bean.ArtScmBeanForApprovals;
import com.excel.bean.ArticleSchemeDetailsBean;
import com.excel.bean.ArticleSchemeDetailsBean.ArticleSchemeTrdDetails;
import com.excel.bean.ArticleSchemeEditBean;
import com.excel.bean.SapSalesInvoiceData;
import com.excel.model.ArticleSchemeRequestDetail;
import com.excel.model.ArticleSchemeRequestDetail_;
import com.excel.model.ArticleSchemeRequestHeader;
import com.excel.model.ArticleSchemeRequestHeader_;
import com.excel.model.CustLock;

@Repository
public class ArtSchemeDelRepoImpl implements ArticleSchemeDeliveryRepo {

	@Autowired
	EntityManagerFactory emf;
	@PersistenceContext
	private EntityManager entityManager;

	@Override
	public List<ArticleSchemeDetailsBean> getArticleSchemeDetailsForEntry(String sub_comp_code, String invoiceDate,
			Long loc_id) throws Exception {

		// ===========================this method not in use
		// anymore=======================================

		EntityManager em = null;
		List<ArticleSchemeDetailsBean> schemeDetails = null;
		ArticleSchemeDetailsBean artSchmObject = null;
		try {
			em = this.emf.createEntityManager();
			schemeDetails = new ArrayList<ArticleSchemeDetailsBean>();

			StringBuffer sb = new StringBuffer();
			sb.append(" SELECT S.TRD_SCH_SLNO,S.SCHEME_APPTYPE,ISNULL(L.LOC_NM,'ALL') LOCATION,S.TRD_SCHEME_ID,");
			sb.append(" S.TRD_SCHEME_CODE,S.TRD_SCHEME_NAME,S.TRD_SCHEME_DESCR,");
			sb.append(" CONVERT(DATE,VALID_FROM_DT) VALID_FROM_DT,CONVERT(DATE,VALID_TO_DT) VALID_TO_DT,");
			sb.append(" CASE WHEN S.TRD_SCH_TYPE = 'A' THEN 'Articles' ELSE 'Gift' END TRD_SCH_TYPE,S.INVOICE_TYPE,");
			sb.append(" S.SALE_PROD_CODE_SG,SL.SALE_PROD_NAME,");
			sb.append(" SL.SALE_MAP_CODE1 SALE_PROD_CODE_ERP,S.ARTICLE_PROD_CD,SMP.SMP_PROD_NAME ARTICLE_PROD_NAME,");
			sb.append(
					" S.ARTICLE_PROD_QTY,S.ARTICLE_PROD_RATE,S.PER_SALE_QTY_BILLED,S.PER_SALE_QTY_FREE,S.PER_SALE_QTY_TOT,S.SALE_PROD_ID ,S.ARTICLE_PROD_ID ");
			sb.append(
					" FROM TRD_SCHEME_MST_HDR S LEFT OUTER JOIN location L ON L.loc_id = S.LOC_ID , SALEPROD SL , SMPITEM SMP");
			sb.append(" WHERE S.TRD_SCH_STATUS = 'A' AND S.SUB_COMP_CD = :compCode");
			sb.append(" AND CONVERT( DATE,S.VALID_FROM_DT ) <= :invDate");
			sb.append(" AND CONVERT( DATE,S.VALID_TO_DT ) >= :invDate");
			sb.append(" AND RTRIM(S.SALE_PROD_ID) = RTRIM(SL.SALE_PROD_ID)");
			sb.append(" AND RTRIM(S.ARTICLE_PROD_ID) = RTRIM(SMP.SMP_PROD_ID)");
			sb.append(" AND ( S.LOC_ID = :loc_id OR ISNULL(S.LOC_ID,0) = 0 ) ORDER BY TRD_SCH_SLNO");

			Query query = em.createNativeQuery(sb.toString(), Tuple.class);
			query.setParameter("compCode", sub_comp_code);
			query.setParameter("invDate", invoiceDate);
			query.setParameter("loc_id", loc_id);

			List<Tuple> list = query.getResultList();
//			if (list != null && list.size() > 0) {
//				System.out.println("article scheme list size :::::::::::: " + list.size());
//				for (Tuple t : list) {
//					artSchmObject = new ArticleSchemeDetailsBean();
//					artSchmObject.setTrd_sch_slno(t.get("TRD_SCH_SLNO", Integer.class));
//					artSchmObject.setScheme_apptype(t.get("SCHEME_APPTYPE", String.class));
//					artSchmObject.setLocation(t.get("LOCATION", String.class));
//					artSchmObject.setTrd_scheme_id(t.get("TRD_SCHEME_ID", BigDecimal.class).intValue());
//					artSchmObject.setTrd_scheme_code(t.get("TRD_SCHEME_CODE", String.class));
//					artSchmObject.setTrd_scheme_name(t.get("TRD_SCHEME_NAME", String.class));
//					artSchmObject.setTrd_scheme_descr(t.get("TRD_SCHEME_DESCR", String.class));
//					artSchmObject.setValid_from_dt(t.get("VALID_FROM_DT", Date.class));
//					artSchmObject.setValid_to_dt(t.get("VALID_TO_DT", Date.class));
//					artSchmObject.setTrd_sch_type(t.get("TRD_SCH_TYPE", String.class));
//					artSchmObject.setInvoice_type(t.get("INVOICE_TYPE", Character.class).toString());
//					artSchmObject.setSale_prod_code_sg(t.get("SALE_PROD_CODE_SG", String.class));
//					artSchmObject.setSale_prod_name(t.get("SALE_PROD_NAME", String.class));
//					artSchmObject.setSale_prod_code_erp(t.get("SALE_PROD_CODE_ERP", String.class));
//					artSchmObject.setArticle_prod_cd(t.get("ARTICLE_PROD_CD", String.class));
//					artSchmObject.setArticle_prod_qty(t.get("ARTICLE_PROD_QTY", BigDecimal.class).intValue());
//					artSchmObject.setArticle_prod_rate(t.get("ARTICLE_PROD_RATE", BigDecimal.class));
//					artSchmObject.setArticle_prod_name(t.get("ARTICLE_PROD_NAME", String.class));
//					artSchmObject.setPer_sale_qty_billed(t.get("PER_SALE_QTY_BILLED", BigDecimal.class).intValue());
//					artSchmObject.setPer_sale_qty_free(t.get("PER_SALE_QTY_FREE", BigDecimal.class).intValue());
//					artSchmObject.setPer_sale_qty_tot(t.get("PER_SALE_QTY_TOT", BigDecimal.class).intValue());
//
//					artSchmObject.setArticle_prod_id(t.get("ARTICLE_PROD_ID", BigDecimal.class).intValue());
//					artSchmObject.setSale_prod_id(t.get("SALE_PROD_ID", BigDecimal.class).intValue());
//
//					System.out.println("list" + artSchmObject.toString());
//
//					schemeDetails.add(artSchmObject);
//				}
//
//			}
		} finally {
			if (em != null)
				em.close();
		}

		return schemeDetails;
	}

	@Override
	public ArticleSchemeRequestHeader getHeaderById(Long id) throws Exception {
		return this.entityManager.find(ArticleSchemeRequestHeader.class, id);
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public void deleteDetailsById(Long id) throws Exception {
		StringBuffer buffer = new StringBuffer();
		buffer.append(" UPDATE ARTICLE_SCHREQ_DTL SET ARTSCH_DT_status='I' where ARTICLE_REQ_ID=" + id);
		Query query = entityManager.createNativeQuery(buffer.toString());
		query.executeUpdate();
	}

	@Override
	public List<ArticleSchemeRequestHeader> getUnConfirmedReqHdrs(Long loc_id, String userId) throws Exception {
		EntityManager em = null;
		List<ArticleSchemeRequestHeader> list = null;
		try {
			em = this.emf.createEntityManager();
			StringBuffer buffer = new StringBuffer();
			buffer.append(
					" SELECT ah.ARTICLE_REQ_ID ,ah.REQUEST_NO, ah.ARTICLE_REQ_DATE , cs.CUST_NAME_BILLTO as CUST_NM,");
			buffer.append(" ah.INVOICE_NO , ah.INVOICE_DATE ,ah.LR_NUMBER , ah.LR_DATE, ah.ARTSCH_TOTAL_VALUE");
			buffer.append(" FROM ARTICLE_SCHREQ_HDR ah inner join  CUSTOMER_MASTER cs on ah.CUST_ID = cs.CUST_ID");
			buffer.append(" WHERE ah.ARTSCH_APPR_STATUS IN ('E') and ah.ARTSCH_status = 'A'");
			buffer.append(
					" and ah.loc_id= :loc_id and ah.ARTSCH_ins_user_id = :userId order by ARTICLE_REQ_ID,ARTICLE_REQ_DATE desc");

			Query query = em.createNativeQuery(buffer.toString(), Tuple.class);
			query.setParameter("loc_id", loc_id);
			query.setParameter("userId", userId);
			List<Tuple> tuple_list = query.getResultList();
			if (tuple_list != null && tuple_list.size() > 0) {
				list = new ArrayList<ArticleSchemeRequestHeader>();
				for (Tuple t : tuple_list) {
					ArticleSchemeRequestHeader art_scm = new ArticleSchemeRequestHeader();
					art_scm.setArticle_req_id(t.get("ARTICLE_REQ_ID", BigDecimal.class).longValue());
					art_scm.setRequest_no(t.get("REQUEST_NO", String.class));
					art_scm.setArticle_req_date(t.get("ARTICLE_REQ_DATE", Date.class));
					art_scm.setCustomer_name(t.get("CUST_NM", String.class));
					art_scm.setInvoice_no(t.get("INVOICE_NO", String.class));
					art_scm.setInvoice_date(t.get("INVOICE_DATE", Date.class));
					art_scm.setLr_number(t.get("LR_NUMBER", String.class));
					art_scm.setLr_date(t.get("LR_DATE", Date.class));
					art_scm.setArtsch_total_value(t.get("ARTSCH_TOTAL_VALUE", BigDecimal.class));
					list.add(art_scm);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		} finally {
			if (em != null)
				em.close();
		}
		return list;
	}

	@Override
	public List<ArticleSchemeDetailsBean> getAllSchemeDetailsForEntry(String sub_comp_code, Long loc_id, String finyear)
			throws Exception {
		EntityManager em = null;
		List<ArticleSchemeDetailsBean> schemeDetails = null;
		ArticleSchemeDetailsBean artSchmObject = null;
		try {
			em = this.emf.createEntityManager();
			schemeDetails = new ArrayList<ArticleSchemeDetailsBean>();

			StringBuffer sb = new StringBuffer();
//			sb.append(" SELECT S.TRD_SCH_SLNO,S.SCHEME_APPTYPE,ISNULL(L.LOC_NM,'ALL') LOCATION,S.TRD_SCHEME_ID,");
//			sb.append(" S.TRD_SCHEME_CODE,S.TRD_SCHEME_NAME,S.TRD_SCHEME_DESCR,");
//			sb.append(" CONVERT(DATE,VALID_FROM_DT) VALID_FROM_DT,CONVERT(DATE,VALID_TO_DT) VALID_TO_DT,");
//			sb.append(" CASE WHEN S.TRD_SCH_TYPE = 'A' THEN 'Articles' ELSE 'Gift' END TRD_SCH_TYPE,S.INVOICE_TYPE,");
//			sb.append(" S.SALE_PROD_CODE_SG,SL.SALE_PROD_NAME,");
//			sb.append(" SL.SALE_MAP_CODE1 SALE_PROD_CODE_ERP,S.ARTICLE_PROD_CD,SMP.SMP_PROD_NAME ARTICLE_PROD_NAME,");
//			sb.append(
//					" S.ARTICLE_PROD_QTY,S.ARTICLE_PROD_RATE,S.PER_SALE_QTY_BILLED,S.PER_SALE_QTY_FREE,S.PER_SALE_QTY_TOT,S.SALE_PROD_ID ,S.ARTICLE_PROD_ID ");
//			sb.append(
//					" FROM TRD_SCHEME_MST_HDR S LEFT OUTER JOIN location L ON L.loc_id = S.LOC_ID , SALEPROD SL , SMPITEM SMP");
//			sb.append(" WHERE S.TRD_SCH_STATUS = 'A' AND S.SUB_COMP_CD = :compCode");
//			sb.append(" AND RTRIM(S.SALE_PROD_ID) = RTRIM(SL.SALE_PROD_ID)");
//			sb.append(" AND RTRIM(S.ARTICLE_PROD_ID) = RTRIM(SMP.SMP_PROD_ID)");
//			sb.append(" AND ( S.LOC_ID = :loc_id OR ISNULL(S.LOC_ID,0) = 0 )");

			sb.append(" SELECT S.TRD_SCH_SLNO,S.SCHEME_APPTYPE,ISNULL(L.LOC_NM,'ALL') LOCATION,");
			sb.append(
					" S.TRD_SCHEME_CODE,S.TRD_SCHEME_NAME,S.TRD_SCHEME_DESCR,CONVERT(DATE,S.VALID_FROM_DT) VALID_FROM_DT,");
			sb.append(" CONVERT(DATE,S.VALID_TO_DT) VALID_TO_DT,S.APPLY_TO,S.BILLED_VALUE,");
			sb.append(" CASE WHEN S.TRD_SCH_TYPE = 'A' THEN 'Articles' ELSE 'Gift' END TRD_SCH_TYPE,S.INVOICE_TYPE,");
			sb.append(
					" S.ARTICLE_PROD_ID,S.ARTICLE_PROD_CD,SMP.SMP_PROD_NAME ARTICLE_PROD_NAME,S.ARTICLE_PROD_RATE,S.ARTICLE_QTY_PER_TOT_VAL,");
			sb.append(
					" DTL.TRD_SCHEME_DTL_ID,DTL.SALE_PROD_ID,SL.SALE_PROD_NAME,SL.SALE_MAP_CODE1 SALE_PROD_CODE_ERP,DTL.PER_SALE_QTY_BILLED,");
			sb.append(
					" DTL.PER_SALE_QTY_FREE,DTL.ARTICLE_QTY,S.APPLY_INV_FROM,S.CLOSURE_DATE FROM TRD_SCHEME_MST_HDR S");
			sb.append(" INNER JOIN TRD_SCHEME_MST_DTL dtl on s.TRD_SCH_SLNO = dtl.TRD_SCH_SLNO");
			sb.append(" LEFT OUTER JOIN location L ON L.loc_id = S.LOC_ID , SALEPROD SL , SMPITEM SMP");
//			sb.append(
//					" WHERE S.TRD_SCH_STATUS = 'A' AND S.SUB_COMP_CD = :compCode AND S.FINYEAR=:finyear AND RTRIM(S.ARTICLE_PROD_ID) = RTRIM(SMP.SMP_PROD_ID)");
			
			sb.append(
					" WHERE S.TRD_SCH_STATUS = 'A' AND S.SUB_COMP_CD = :compCode  AND RTRIM(S.ARTICLE_PROD_ID) = RTRIM(SMP.SMP_PROD_ID)");
			sb.append(
					" AND RTRIM(DTL.SALE_PROD_ID) = RTRIM(SL.SALE_PROD_ID) AND ( S.LOC_ID = :loc_id OR ISNULL(S.LOC_ID,0) = 0 ) ORDER BY S.TRD_SCH_SLNO");

			Query query = em.createNativeQuery(sb.toString(), Tuple.class);
			query.setParameter("compCode", sub_comp_code);
			query.setParameter("loc_id", loc_id);
//			query.setParameter("finyear", finyear);

			List<Tuple> list = query.getResultList();
			if (list != null && list.size() > 0) {
				System.out.println("article scheme list size :::::::::::: " + list.size());
				Integer last_scheme_slno = 0;
				Integer current_scheme_slno = 0;
				boolean first_time_flag = true;
				for (Tuple t : list) {
//					artSchmObject = new ArticleSchemeDetailsBean();
//					artSchmObject.setTrd_sch_slno(t.get("TRD_SCH_SLNO", Integer.class));
//					artSchmObject.setScheme_apptype(t.get("SCHEME_APPTYPE", String.class));
//					artSchmObject.setLocation(t.get("LOCATION", String.class));
//					artSchmObject.setTrd_scheme_id(t.get("TRD_SCHEME_ID", BigDecimal.class).intValue());
//					artSchmObject.setTrd_scheme_code(t.get("TRD_SCHEME_CODE", String.class));
//					artSchmObject.setTrd_scheme_name(t.get("TRD_SCHEME_NAME", String.class));
//					artSchmObject.setTrd_scheme_descr(t.get("TRD_SCHEME_DESCR", String.class));
//					artSchmObject.setValid_from_dt(t.get("VALID_FROM_DT", Date.class));
//					artSchmObject.setValid_to_dt(t.get("VALID_TO_DT", Date.class));
//					artSchmObject.setTrd_sch_type(t.get("TRD_SCH_TYPE", String.class));
//					artSchmObject.setInvoice_type(t.get("INVOICE_TYPE", Character.class).toString());
//					artSchmObject.setSale_prod_code_sg(t.get("SALE_PROD_CODE_SG", String.class));
//					artSchmObject.setSale_prod_name(t.get("SALE_PROD_NAME", String.class));
//					artSchmObject.setSale_prod_code_erp(t.get("SALE_PROD_CODE_ERP", String.class));
//					artSchmObject.setArticle_prod_cd(t.get("ARTICLE_PROD_CD", String.class));
//					artSchmObject.setArticle_prod_qty(t.get("ARTICLE_PROD_QTY", BigDecimal.class).intValue());
//					artSchmObject.setArticle_prod_rate(t.get("ARTICLE_PROD_RATE", BigDecimal.class));
//					artSchmObject.setArticle_prod_name(t.get("ARTICLE_PROD_NAME", String.class));
//					artSchmObject.setPer_sale_qty_billed(t.get("PER_SALE_QTY_BILLED", BigDecimal.class).intValue());
//					artSchmObject.setPer_sale_qty_free(t.get("PER_SALE_QTY_FREE", BigDecimal.class).intValue());
//					artSchmObject.setPer_sale_qty_tot(t.get("PER_SALE_QTY_TOT", BigDecimal.class).intValue());
//
//					artSchmObject.setArticle_prod_id(t.get("ARTICLE_PROD_ID", BigDecimal.class).intValue());
//					artSchmObject.setSale_prod_id(t.get("SALE_PROD_ID", BigDecimal.class).intValue());

					current_scheme_slno = t.get("TRD_SCH_SLNO", Integer.class);
					if (!current_scheme_slno.equals(last_scheme_slno)) {
						if (!first_time_flag)
							schemeDetails.add(artSchmObject);
						artSchmObject = new ArticleSchemeDetailsBean();
						artSchmObject.setTrd_scheme_id(current_scheme_slno);
						artSchmObject.setTrd_scheme_code(t.get("TRD_SCHEME_CODE", String.class));
						artSchmObject.setTrd_scheme_name(t.get("TRD_SCHEME_NAME", String.class));
						artSchmObject.setTrd_scheme_descr(t.get("TRD_SCHEME_DESCR", String.class));
						artSchmObject.setValid_from_dt(t.get("VALID_FROM_DT", Date.class));
						artSchmObject.setValid_to_dt(t.get("VALID_TO_DT", Date.class));
						artSchmObject.setApply_to(t.get("APPLY_TO", Character.class).toString());
						artSchmObject.setBilled_value(t.get("Billed_value", BigDecimal.class));
						artSchmObject.setArticle_prod_id(t.get("ARTICLE_PROD_ID", BigDecimal.class).intValue());
						artSchmObject.setArticle_prod_cd(t.get("ARTICLE_PROD_CD", String.class));
						artSchmObject.setArticle_prod_descr(t.get("ARTICLE_PROD_NAME", String.class));
						artSchmObject.setArticle_prod_rate(t.get("ARTICLE_PROD_RATE", BigDecimal.class));
						artSchmObject.setArticle_qty_per_tot_val(
								t.get("ARTICLE_QTY_PER_TOT_VAL", BigDecimal.class).intValue());
						artSchmObject.setApply_inv_from(t.get("APPLY_INV_FROM", Date.class));
						artSchmObject.setClosure_date(
								t.get("CLOSURE_DATE") != null ? t.get("CLOSURE_DATE", Date.class) : null);
						// will be default zero while entry
						artSchmObject.setTot_billed_value_entered(BigDecimal.ZERO);
						artSchmObject.setArticle_prod_qty_tot(0);
						artSchmObject.setArticle_prod_value(BigDecimal.ZERO);

						artSchmObject.setDetailList(new ArrayList<ArticleSchemeTrdDetails>());
					}
					ArticleSchemeTrdDetails dtls = new ArticleSchemeTrdDetails();
					dtls.setTrd_scheme_dtl_id(t.get("TRD_SCHEME_DTL_ID", Integer.class));
					dtls.setSale_prod_id(t.get("SALE_PROD_ID", BigDecimal.class).intValue());
					dtls.setSale_prod_name(t.get("SALE_PROD_NAME", String.class));
					dtls.setSale_prod_code_erp(t.get("SALE_PROD_CODE_ERP", String.class));
					dtls.setPer_sale_qty_billed(t.get("PER_SALE_QTY_BILLED", BigDecimal.class).intValue());
					dtls.setPer_sale_qty_free(t.get("PER_SALE_QTY_FREE", BigDecimal.class).intValue());
					dtls.setPer_article_prod_qty(t.get("ARTICLE_QTY", BigDecimal.class).intValue());
					// default zero during entry
					dtls.setPer_sale_qty_billed_entered(0);
					dtls.setPer_billed_value_entered(BigDecimal.ZERO);
					dtls.setPer_article_prod_qty_updated(0);

					artSchmObject.getDetailList().add(dtls);

					last_scheme_slno = current_scheme_slno;
					first_time_flag = false;
				}
				schemeDetails.add(artSchmObject);
			}
		} finally {
			if (em != null)
				em.close();
		}

		return schemeDetails;
	}

	@Override
	public List<ArticleSchemeEditBean> getArticleSchemeDetailsForModifyByArtReqId(Long art_req_id, String sub_comp_code,
			Long loc_id) throws Exception {
		EntityManager em = null;
		List<ArticleSchemeEditBean> schemeDetails = null;
		ArticleSchemeEditBean artSchmObject = null;
		try {
			em = this.emf.createEntityManager();
			schemeDetails = new ArrayList<ArticleSchemeEditBean>();

			StringBuffer sb = new StringBuffer();
			sb.append(
					" select hd.ARTICLE_REQ_ID,hd.ARTICLE_REQ_DATE,hd.FIN_YEAR,hd.PERIOD_CODE,hd.REQUEST_NO,hd.CUST_ID,hd.INVOICE_NO,");
			sb.append(" hd.INVOICE_DATE,hd.LR_NUMBER,hd.LR_DATE,hd.ARTSCH_TOTAL_VALUE,");
			sb.append(" dt.TRD_SCHEME_ID,td.TRD_SCHEME_NAME,td.TRD_SCHEME_CODE,td.VALID_FROM_DT,td.VALID_TO_DT,");
			sb.append(
					" dt.SALE_PROD_CODE_SG,dt.SALE_PROD_ID,dt.SALE_PROD_QTY_BILLED,dt.SALE_PROD_QTY_FREE,sp.SALE_PROD_NAME,");
			sb.append(
					" dt.ARTICLE_PROD_CD,dt.ARTICLE_PROD_ID,dt.ARTICLE_PROD_QTY,dt.ARTICLE_PROD_RATE,dt.ARTICLE_PROD_VALUE,sm.SMP_PROD_NAME");
			sb.append(" from ARTICLE_SCHREQ_HDR hd");
			sb.append(" inner join ARTICLE_SCHREQ_DTL dt on hd.ARTICLE_REQ_ID = dt.ARTICLE_REQ_ID");
			sb.append(" inner join TRD_SCHEME_MST_HDR td on td.TRD_SCH_SLNO = dt.TRD_SCHEME_ID");
			sb.append(" inner join SALEPROD sp on sp.SALE_PROD_ID = dt.SALE_PROD_ID");
			sb.append(" inner join SMPITEM sm on sm.smp_prod_id = dt.article_prod_id");
			sb.append(
					" where hd.artsch_appr_status IN ('E') and dt.artsch_dt_status = 'A' and sp.sale_status = 'A' and sm.SMP_status = 'A'");
			sb.append(
					" and hd.ARTICLE_REQ_ID = :art_req_id and hd.LOC_ID = :loc_id and hd.SUB_COMP_CD = :sub_comp_code ");

			Query query = em.createNativeQuery(sb.toString(), Tuple.class);
			query.setParameter("sub_comp_code", sub_comp_code);
			query.setParameter("art_req_id", art_req_id);
			query.setParameter("loc_id", loc_id);

			List<Tuple> list = query.getResultList();
			if (list != null && list.size() > 0) {
				System.out.println("article scheme list size :::::::::::: " + list.size());
				for (Tuple t : list) {
					artSchmObject = new ArticleSchemeEditBean();
					artSchmObject.setArticle_req_id(t.get("ARTICLE_REQ_ID", BigDecimal.class).longValue());
					artSchmObject.setArticle_req_date(t.get("ARTICLE_REQ_DATE", Date.class));
					artSchmObject.setFinYear(t.get("FIN_YEAR", String.class));
					artSchmObject.setPeriod_code(t.get("PERIOD_CODE", String.class));
					artSchmObject.setReq_no(t.get("REQUEST_NO", String.class));
					artSchmObject.setCust_id(t.get("CUST_ID", BigDecimal.class).longValue());
					artSchmObject.setInvoice_no(t.get("INVOICE_NO", String.class));
					artSchmObject.setInv_date(t.get("INVOICE_DATE", Date.class));
					artSchmObject.setLr_no(t.get("LR_NUMBER", String.class));
					artSchmObject.setLr_date(t.get("LR_DATE", Date.class));
					artSchmObject.setArt_sch_tot_val(t.get("ARTSCH_TOTAL_VALUE", BigDecimal.class));
					artSchmObject.setTrd_scheme_id(t.get("TRD_SCHEME_ID", BigDecimal.class).longValue());
					artSchmObject.setTrd_sch_name(t.get("TRD_SCHEME_NAME", String.class));
					artSchmObject.setTrd_scheme_code(t.get("TRD_SCHEME_CODE", String.class));
					artSchmObject.setValid_from_dt(t.get("VALID_FROM_DT", Date.class));
					artSchmObject.setValid_to_dt(t.get("VALID_TO_DT", Date.class));
					artSchmObject.setSale_prod_code_sg(t.get("SALE_PROD_CODE_SG", String.class));
					artSchmObject.setSale_prod_id(t.get("SALE_PROD_ID", BigDecimal.class).longValue());
					artSchmObject.setSale_qty_billed(t.get("SALE_PROD_QTY_BILLED", BigDecimal.class).longValue());
					artSchmObject.setSale_qty_free(t.get("SALE_PROD_QTY_FREE", BigDecimal.class).longValue());
					artSchmObject.setSale_prod_name(t.get("SALE_PROD_NAME", String.class));
					artSchmObject.setArticle_prod_cd(t.get("ARTICLE_PROD_CD", String.class));
					artSchmObject.setArticle_prod_id(t.get("ARTICLE_PROD_ID", BigDecimal.class).longValue());
					artSchmObject.setArticle_prod_qty(t.get("ARTICLE_PROD_QTY", BigDecimal.class).longValue());
					artSchmObject.setArticle_prod_value(t.get("ARTICLE_PROD_VALUE", BigDecimal.class));
					artSchmObject.setArticle_prod_rarte(t.get("ARTICLE_PROD_RATE", BigDecimal.class));
					artSchmObject.setArticle_prod_name(t.get("SMP_PROD_NAME", String.class));

					System.out.println("list" + artSchmObject.toString());

					schemeDetails.add(artSchmObject);
				}

			}
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		} finally {
			if (em != null)
				em.close();
		}

		return schemeDetails;
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public void deleteHeaderById(Long id) throws Exception {
		StringBuffer buffer = new StringBuffer();
		buffer.append(
				" UPDATE ARTICLE_SCHREQ_HDR SET ARTSCH_APPR_STATUS='D',ARTSCH_status='I' where ARTICLE_REQ_ID=" + id);
		Query query = entityManager.createNativeQuery(buffer.toString());
		query.executeUpdate();
	}

	@Override
	public Boolean checkIfInvoiceExists(String invoice_no, Long loc_id) throws Exception {
		EntityManager em = null;
		try {
			em = this.emf.createEntityManager();

			StringBuffer sb = new StringBuffer();
			sb.append(
					" select ARTICLE_REQ_ID from ARTICLE_SCHREQ_HDR where INVOICE_NO = :invoice_no and LOC_ID = :loc_id");
			sb.append(" AND ARTSCH_APPR_STATUS <> 'D'");
			sb.append(" AND ARTSCH_status <> 'I'");

			Query query = em.createNativeQuery(sb.toString());
			query.setParameter("invoice_no", invoice_no);
			query.setParameter("loc_id", loc_id);

			BigDecimal duplicate_id = (BigDecimal) query.getSingleResult();
			if (duplicate_id != null && duplicate_id.compareTo(BigDecimal.ZERO) > 0) {
				return false;
			} else {
				return true;
			}
		} catch (NoResultException nre) {
			return true;

		} catch (javax.persistence.NonUniqueResultException nre) {
			return false;
		} catch (Exception e) {
			throw e;
		} finally {
			if (em != null)
				em.close();
		}
	}

	@Override
	public List<ArticleSchemeRequestDetail> getDetailsByHdrReqId(Long art_req_hdr_id) throws Exception {
		List<ArticleSchemeRequestDetail> list = null;
		EntityManager em = null;
		try {
			list = new ArrayList<ArticleSchemeRequestDetail>();
			em = emf.createEntityManager();
			CriteriaBuilder builder = em.getCriteriaBuilder();
			CriteriaQuery<ArticleSchemeRequestDetail> criteriaQuery = builder
					.createQuery(ArticleSchemeRequestDetail.class);
			Root<ArticleSchemeRequestDetail> root = criteriaQuery.from(ArticleSchemeRequestDetail.class);
			criteriaQuery.select(root).where(
					builder.and(builder.equal(root.get(ArticleSchemeRequestDetail_.article_req_id), art_req_hdr_id)));
			list = em.createQuery(criteriaQuery).getResultList();
			return list;
		} finally {
			if (em != null) {
				em.close();
			}
		}
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public void callFinalApprovalProcedure(Long art_req_hdr_id, String userId, String ipAddr) throws Exception {
		try {
			StringBuffer sb = new StringBuffer();
			sb.append("exec ALLOCATION_FINAL_APPROVAL_DIRECT_TO_STOCKIST_SINGLE :art_req_hdr_id,:userid,:user_ip");
			Query query = entityManager.createNativeQuery(sb.toString());
			query.setParameter("art_req_hdr_id", art_req_hdr_id);
			query.setParameter("userid", userId.trim());
			query.setParameter("user_ip", ipAddr.trim());
			query.executeUpdate();
		} catch (Exception e) {
			throw e;
		}
	}

	@Override
	public List<ArticleSchemeRequestHeader> getArtReqNosForAutoDispatch(String finYear, String period_code, Long div_id,
			Long loc_id, String empId) throws Exception {
		EntityManager em = null;
		StringBuffer sb = null;
		List<ArticleSchemeRequestHeader> returnList = null;
		System.out.println("finyr:" + finYear + "\nperCode:" + period_code + "\ndivId" + div_id + "\nloc_id" + loc_id
				+ "\nempId" + empId);
		try {
			sb = new StringBuffer();
			em = this.emf.createEntityManager();

//			sb.append(" SELECT bh_req_no + ' ( '+ Rtrim(CONVERT(VARCHAR, total_req-Isnull(dsp_count, 0))) + ' ) ' AS BH_REQ_NO,ah_blk_req_id,");
//			sb.append(" total_req - Isnull(dsp_count, 0) AS PENDING");
//			sb.append(" FROM   (SELECT request_no BH_REQ_NO,ah.blk_hcp_req_id AH_BLK_REQ_ID,Count(*) AS TOTAL_REQ");
//			sb.append(" FROM alloc_header ah JOIN article_schreq_hdr bh ON bh.[article_req_id] = ah.blk_hcp_req_id");
//			sb.append(" JOIN ALLOC_DETAIL AD ON AD.ALLOCDTL_ALLOC_ID=AH.ALLOC_ID JOIN fieldstaff FS");
//			sb.append(" ON FS.fstaff_id = BH.fs_id WHERE  Isnull(ah.blk_hcp_req_id, 0) <> 0");
//			sb.append(" AND bh.artsch_status = 'A' AND ah.alloc_appr_status = 'A'");
//			sb.append(" AND AH.alloc_type = 'S' AND ah.entry_type = 'M'");
//			sb.append(" AND FS.fstaff_div_id = :divId AND AH.alloc_status = 'A'");
//			sb.append(" AND AH.alloc_fin_year = :finYear AND AH.alloc_period_code = :perCode");
//			sb.append(" AND bh.artsch_ins_user_id = :userId AND BH.loc_id = :loc_id");
//			sb.append(" AND ( AD.ALLOCDTL_CURR_ALLOC_QTY - AD.ALLOCDTL_SUPPLY_QTY>0) GROUP BY request_no,ah.blk_hcp_req_id)DTL");
//			sb.append(" LEFT JOIN (SELECT DH.blk_hcp_req_id,Count(*) DSP_COUNT");
//			sb.append(" FROM dispatch_header DH WHERE DH.blk_hcp_req_id <> 0");
//			sb.append(" AND DH.dsp_div_id = :divId AND DH.dsp_fin_year = :finYear");
//			sb.append(" AND DH.dsp_period_code = :perCode AND DH.entry_type = 'M'");
//			sb.append(" GROUP  BY DH.blk_hcp_req_id)DH ON DH.blk_hcp_req_id = DTL.ah_blk_req_id");
//			sb.append(" WHERE  total_req - Isnull(dsp_count, 0) >= 0");

			sb.append(" SELECT bh_req_no AS BH_REQ_NO,ah_blk_req_id,");
			sb.append(
					" total_req - Isnull(dsp_count, 0) AS PENDING,DTL.CUST_NAME_SHIPTO,DTL.INVOICE_NO,DTL.INVOICE_DATE,DTL.DESTINATION_SHIPTO,DTL.corr_ind as CORR_IND");
			sb.append(" FROM   (SELECT request_no BH_REQ_NO,ah.blk_hcp_req_id AH_BLK_REQ_ID,Count(*) AS TOTAL_REQ,");
			sb.append(" cm.CUST_NAME_SHIPTO,bh.INVOICE_NO,bh.INVOICE_DATE,cm.DESTINATION_SHIPTO,ah.corr_ind");
			sb.append(" FROM alloc_header ah JOIN article_schreq_hdr bh ON bh.[article_req_id] = ah.blk_hcp_req_id");
			sb.append(" JOIN CUSTOMER_MASTER CM ON CM.CUST_ID = BH.CUST_ID");
			sb.append(" JOIN ALLOC_DETAIL AD ON AD.ALLOCDTL_ALLOC_ID=AH.ALLOC_ID JOIN fieldstaff FS");
			sb.append(" ON FS.fstaff_id = BH.fs_id WHERE  Isnull(ah.blk_hcp_req_id, 0) <> 0");
			sb.append(" AND bh.artsch_status = 'A' AND ah.alloc_appr_status = 'A'");
			sb.append(" AND AH.alloc_type = 'S' AND ah.entry_type = 'M'");
			sb.append(" AND FS.fstaff_div_id = :divId AND AH.alloc_status = 'A'");
			sb.append(" AND AH.alloc_fin_year = :finYear AND AH.alloc_period_code = :perCode");
			sb.append(" AND AH.alloc_fin_year = :finYear");
			sb.append(" AND bh.artsch_ins_user_id = :userId AND BH.loc_id = :loc_id");
			sb.append(
					" AND ( AD.ALLOCDTL_CURR_ALLOC_QTY - AD.ALLOCDTL_SUPPLY_QTY>0) GROUP BY request_no,ah.blk_hcp_req_id,");
			sb.append(" bh.INVOICE_NO,bh.INVOICE_DATE,cm.CUST_NAME_SHIPTO,cm.DESTINATION_SHIPTO,ah.corr_ind)DTL");
			sb.append(" LEFT JOIN (SELECT DH.blk_hcp_req_id,Count(*) DSP_COUNT");
			sb.append(" FROM dispatch_header DH WHERE DH.blk_hcp_req_id <> 0");
			sb.append(" AND DH.dsp_div_id = :divId AND DH.dsp_fin_year = :finYear");
			sb.append(" AND DH.dsp_period_code = :perCode AND DH.entry_type = 'M'");
			sb.append("  AND DH.entry_type = 'M' AND DH.dsp_loc_id=:loc_id");
			sb.append(" GROUP  BY DH.blk_hcp_req_id)DH ON DH.blk_hcp_req_id = DTL.ah_blk_req_id");
			sb.append(" WHERE  total_req - Isnull(dsp_count, 0) <> 0");

			Query query = em.createNativeQuery(sb.toString(), Tuple.class);
			query.setParameter("finYear", finYear);
			query.setParameter("perCode", period_code);
			query.setParameter("divId", div_id);
			query.setParameter("loc_id", loc_id);
			query.setParameter("userId", empId);

			List<Tuple> list = query.getResultList();
			System.out.println("results size::" + list.size());
			if (list != null && list.size() > 0) {
				returnList = new ArrayList<ArticleSchemeRequestHeader>();
				for (Tuple t : list) {
					ArticleSchemeRequestHeader obj = new ArticleSchemeRequestHeader();
					obj.setRequest_no(t.get("BH_REQ_NO", String.class));
					obj.setArticle_req_id(t.get("ah_blk_req_id", BigDecimal.class).longValue());
					obj.setCustomer_name(t.get("CUST_NAME_SHIPTO", String.class));
					obj.setInvoice_no(t.get("INVOICE_NO", String.class));
					obj.setInvoice_date(t.get("INVOICE_DATE", Date.class));
					obj.setDestination_ship_to(t.get("DESTINATION_SHIPTO", String.class));
					obj.setCorr_ind(
							t.get("CORR_IND", Character.class) != null ? t.get("CORR_IND", Character.class).toString()
									: "");
					returnList.add(obj);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
		return returnList;
	}

	@Override
	public ArticleSchemeRequestHeader getHeaderByReqNo(String req_no) throws Exception {
		ArticleSchemeRequestHeader artReqHdr = null;
		EntityManager em = null;
		try {
			em = emf.createEntityManager();
			CriteriaBuilder builder = em.getCriteriaBuilder();
			CriteriaQuery<ArticleSchemeRequestHeader> criteriaQuery = builder
					.createQuery(ArticleSchemeRequestHeader.class);
			Root<ArticleSchemeRequestHeader> root = criteriaQuery.from(ArticleSchemeRequestHeader.class);
			criteriaQuery.select(root)
					.where(builder.and(builder.equal(root.get(ArticleSchemeRequestHeader_.request_no), req_no)));
			artReqHdr = em.createQuery(criteriaQuery).getSingleResult();
			return artReqHdr;
		} finally {
			if (em != null) {
				em.close();
			}
		}
	}

	@Override
	public List<ArtScmBeanForApprovals> getDataForApprovals(Long art_sch_req_hdr, String finYear, String companyCode,
			String status) throws Exception {
		EntityManager em = null;
		List<ArtScmBeanForApprovals> list = null;
		try {
			em = this.emf.createEntityManager();
			StringBuffer sbuffer = new StringBuffer();
			sbuffer.append(" select ah.ARTICLE_REQ_ID,ah.ARTICLE_REQ_DATE,ah.FIN_YEAR,ah.REQUEST_NO,");
			sbuffer.append(" ah.INVOICE_NO,ah.INVOICE_DATE,ah.LR_NUMBER,ah.LR_DATE,ah.ARTSCH_TOTAL_VALUE,");
			sbuffer.append(" cm.CUST_NAME_BILLTO,cm.ERP_CUST_CD,cm.ADDR1_SHIPTO,cm.ADDR2_SHIPTO,");
			sbuffer.append(" cm.ADDR3_SHIPTO,cm.ADDR4_SHIPTO,cm.DESTINATION,cm.GSTIN_NO,");
			sbuffer.append(" dtl.ARTICLE_REQ_DTL_ID,tr.TRD_SCHEME_CODE,tr.TRD_SCHEME_NAME,");
			sbuffer.append(" sp.SALE_PROD_NAME,sp.SALE_PROD_CD,dtl.SALE_PROD_QTY_BILLED,");
			sbuffer.append(" sm.SMP_PROD_NAME , sm.SMP_ERP_PROD_CD,dtl.ARTICLE_PROD_QTY,");
			sbuffer.append(" dtl.ARTICLE_PROD_RATE,dtl.ARTICLE_PROD_VALUE from ARTICLE_SCHREQ_HDR ah");
			sbuffer.append(" inner join CUSTOMER_MASTER cm on ah.CUST_ID = cm.CUST_ID");
			sbuffer.append(" inner join ARTICLE_SCHREQ_DTL dtl on ah.ARTICLE_REQ_ID = dtl.ARTICLE_REQ_ID");
			sbuffer.append(" inner join TRD_SCHEME_MST_HDR tr on tr.TRD_SCH_SLNO = dtl.TRD_SCH_SLNO");
			sbuffer.append(" inner join smpitem sm on sm.smp_prod_id = dtl.ARTICLE_PROD_ID");
			sbuffer.append(" inner join SALEPROD sp on sp.SALE_PROD_ID = dtl.SALE_PROD_ID");
			sbuffer.append(" where ah.ARTICLE_REQ_ID = :art_sch_hdr_req_id");
			sbuffer.append(" and ah.ARTSCH_APPR_STATUS = :status and ah.ARTSCH_status = 'A'");
			sbuffer.append(" and ah.FIN_YEAR = :finYear and ah.ALLOC_TYPE = 'S'");
			sbuffer.append(" and ah.COMPANY = :company and dtl.ARTSCH_DT_status = 'A' ");

			Query query = em.createNativeQuery(sbuffer.toString(), Tuple.class);
			query.setParameter("art_sch_hdr_req_id", art_sch_req_hdr);
			query.setParameter("finYear", finYear);
			query.setParameter("company", companyCode);
			query.setParameter("status", status);
			List<Tuple> tuple_list = query.getResultList();
			if (tuple_list != null && tuple_list.size() > 0) {
				list = new ArrayList<ArtScmBeanForApprovals>();
				for (Tuple intuple : tuple_list) {
					ArtScmBeanForApprovals bean_for_approval = new ArtScmBeanForApprovals();
					bean_for_approval.setArticle_req_id(intuple.get("ARTICLE_REQ_ID", BigDecimal.class).longValue());
					bean_for_approval.setArticle_req_date(intuple.get("ARTICLE_REQ_DATE", Date.class).toString());
					bean_for_approval.setFin_year(intuple.get("FIN_YEAR", String.class));
					bean_for_approval.setRequest_no(intuple.get("REQUEST_NO", String.class));
					bean_for_approval.setInvoice_no(intuple.get("INVOICE_NO", String.class));
					bean_for_approval.setInvoice_date(intuple.get("INVOICE_DATE", Date.class).toString());
					bean_for_approval.setLr_number(intuple.get("LR_NUMBER", String.class));
					bean_for_approval.setLr_date(
							intuple.get("LR_DATE", Date.class) != null ? intuple.get("LR_DATE", Date.class).toString()
									: "");
					bean_for_approval.setArtsch_total_value(intuple.get("ARTSCH_TOTAL_VALUE", BigDecimal.class));
					bean_for_approval.setCust_name_billto(intuple.get("CUST_NAME_BILLTO", String.class));
					bean_for_approval.setErp_cust_cd(intuple.get("ERP_CUST_CD", String.class));
					bean_for_approval.setAddr1_shipto(intuple.get("ADDR1_SHIPTO", String.class));
					bean_for_approval.setAddr2_shipto(intuple.get("ADDR2_SHIPTO", String.class));
					bean_for_approval.setAddr3_shipto(intuple.get("ADDR3_SHIPTO", String.class));
					bean_for_approval.setAddr4_shipto(intuple.get("ADDR4_SHIPTO", String.class));
					bean_for_approval.setDestination(intuple.get("DESTINATION", String.class));
					bean_for_approval.setGstin_no(intuple.get("GSTIN_NO", String.class));
					bean_for_approval
							.setArticle_req_dtl_id(intuple.get("ARTICLE_REQ_DTL_ID", BigDecimal.class).longValue());
					bean_for_approval.setTrd_scheme_code(intuple.get("TRD_SCHEME_CODE", String.class));
					bean_for_approval.setTrd_scheme_name(intuple.get("TRD_SCHEME_NAME", String.class));
					bean_for_approval.setSale_prod_name(intuple.get("SALE_PROD_NAME", String.class));
					bean_for_approval.setSale_prod_cd(intuple.get("SALE_PROD_CD", String.class));
					bean_for_approval
							.setSale_prod_qty_billed(intuple.get("SALE_PROD_QTY_BILLED", BigDecimal.class).longValue());
					bean_for_approval.setSmp_prod_name(intuple.get("SMP_PROD_NAME", String.class));
					bean_for_approval.setSmp_erp_prod_cd(intuple.get("SMP_ERP_PROD_CD", String.class));
					bean_for_approval
							.setArticle_prod_qty(intuple.get("ARTICLE_PROD_QTY", BigDecimal.class).longValue());
					bean_for_approval.setArticle_prod_rate(intuple.get("ARTICLE_PROD_RATE", BigDecimal.class));
					bean_for_approval.setArticle_prod_value(intuple.get("ARTICLE_PROD_VALUE", BigDecimal.class));

					list.add(bean_for_approval);
				}
			} else {
				throw new Exception("No data found for approvals / to send mail");
			}

		} finally {
			if (em != null)
				em.close();
		}
		return list;
	}

	SimpleDateFormat full_datetime_format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");

	@Override
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public void lockCustForArticleReqEntry(Long loc_id, Long cust_id, String userId) throws Exception {
		StringBuffer buffer = new StringBuffer();
		buffer.append(
				" INSERT INTO CUST_LOCK (LOC_ID, CUST_ID,USER_ID,LOG_TIME) VALUES (:loc_id, :cust_id,:userId,:datetime_string)");
		Query query = entityManager.createNativeQuery(buffer.toString());
		query.setParameter("loc_id", loc_id);
		query.setParameter("cust_id", cust_id);
		query.setParameter("userId", userId.trim());
		query.setParameter("datetime_string", full_datetime_format.format(new Date()));
		query.executeUpdate();
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public void releaseLockedCustomer(Long loc_id, Long cust_id, String userId) throws Exception {
		StringBuffer buffer = new StringBuffer();
		buffer.append("DELETE FROM CUST_LOCK WHERE LOC_ID = :loc_id AND CUST_ID = :cust_id AND USER_ID = :userId");
		Query query = entityManager.createNativeQuery(buffer.toString());
		query.setParameter("loc_id", loc_id);
		query.setParameter("cust_id", cust_id);
		query.setParameter("userId", userId.trim());
		query.executeUpdate();
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public void unlockAllCustByUserId(String userId) throws Exception {
		StringBuffer buffer = new StringBuffer();
		buffer.append("DELETE FROM CUST_LOCK WHERE USER_ID = :userId");
		Query query = entityManager.createNativeQuery(buffer.toString());
		query.setParameter("userId", userId.trim());
		query.executeUpdate();
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public void deleteProductsById(Long id) throws Exception {
		StringBuffer buffer = new StringBuffer();
		buffer.append(" UPDATE ARTICLE_SCHREQ_PROD SET ARTSCH_PRD_status='I' where ARTICLE_REQ_ID=" + id);
		Query query = entityManager.createNativeQuery(buffer.toString());
		query.executeUpdate();
	}

	@Override
	public List<ArticleSchemeDetailsBean> getAllEnteredDetails(Long loc_id, String sub_comp_code, Long article_req_id,
			String appr_status) {
		List<ArticleSchemeDetailsBean> enteredDetails = null;
		ArticleSchemeDetailsBean artSchmObject = null;
		EntityManager em = null;
		try {
			em = this.emf.createEntityManager();
			StringBuffer sb = new StringBuffer();
			sb.append(
					" select dtl.TRD_SCH_SLNO,sch_hdr.TRD_SCHEME_CODE,sch_hdr.TRD_SCHEME_NAME,sch_hdr.TRD_SCHEME_DESCR,sch_hdr.VALID_FROM_DT,");
			sb.append(
					" sch_hdr.VALID_TO_DT,sch_hdr.APPLY_TO,sch_hdr.BILLED_VALUE,dtl.BILLING_VALUE as TOT_BILLED_VALUE_ENTERED,");
			sb.append(
					" dtl.ARTICLE_PROD_ID,smp.SMP_ERP_PROD_CD as ARTICLE_PROD_CD,smp.SMP_PROD_NAME as ARTICLE_PROD_NAME,dtl.ARTICLE_PROD_QTY as ARTICLE_PROD_QTY_TOT,");
			sb.append(" DTL.ARTICLE_PROD_VALUE,smp.smp_rate as ARTICLE_PROD_RATE,sch_hdr.ARTICLE_QTY_PER_TOT_VAL,");
			sb.append(
					" prd.TRD_SCHEME_DTL_ID,slp.SALE_PROD_ID,slp.SALE_PROD_NAME,slp.SALE_MAP_CODE1 as SALE_PROD_CODE_ERP,");
			sb.append(" sch_dtl.PER_SALE_QTY_BILLED, prd.SALE_PROD_QTY_BILLED as PER_SALE_QTY_BILLED_ENTERED,");
			sb.append(
					" sch_dtl.PER_SALE_QTY_FREE,prd.SALE_BILLED_VALUE as PER_BILLED_VALUE_ENTERED,sch_dtl.ARTICLE_QTY as PER_ARTICLE_PROD_QTY,");
			sb.append(" prd.ARTICLE_QTY as PER_ARTICLE_PROD_QTY_UPDATED, sch_hdr.APPLY_INV_FROM");
			sb.append(
					" from ARTICLE_SCHREQ_HDR hdr inner join ARTICLE_SCHREQ_DTL dtl on hdr.ARTICLE_REQ_ID = dtl.ARTICLE_REQ_ID");
			sb.append(" inner join TRD_SCHEME_MST_HDR sch_hdr on dtl.TRD_SCH_SLNO = sch_hdr.TRD_SCH_SLNO");
			sb.append(" inner join ARTICLE_SCHREQ_PROD prd on dtl.ARTICLE_REQ_DTL_ID = prd.ARTICLE_REQ_DTL_ID");
			sb.append(" inner join TRD_SCHEME_MST_DTL sch_dtl on sch_dtl.TRD_SCHEME_DTL_ID = prd.TRD_SCHEME_DTL_ID");
			sb.append(" inner join SMPITEM smp on smp.SMP_PROD_ID = dtl.ARTICLE_PROD_ID");
			sb.append(" inner join SALEPROD slp on slp.SALE_PROD_ID = prd.SALE_PROD_ID_SG");
			sb.append(" where hdr.ARTSCH_APPR_STATUS = :appr_status and hdr.ARTSCH_status = 'A'");
			sb.append(" and dtl.ARTSCH_DT_status = 'A' and prd.ARTSCH_PRD_status = 'A'");
			sb.append(
					" AND hdr.ARTICLE_REQ_ID = :article_req_id and hdr.LOC_ID = :loc_id and hdr.SUB_COMP_CD = :sub_comp_code ORDER BY dtl.TRD_SCH_SLNO");

			Query query = em.createNativeQuery(sb.toString(), Tuple.class);
			query.setParameter("article_req_id", article_req_id);
			query.setParameter("loc_id", loc_id);
			query.setParameter("sub_comp_code", sub_comp_code);
			query.setParameter("appr_status", appr_status);

			List<Tuple> list = query.getResultList();
			if (list != null && list.size() > 0) {
				enteredDetails = new ArrayList<ArticleSchemeDetailsBean>();
				System.out.println("article scheme list size :::::::::::: " + list.size());
				Integer last_scheme_slno = 0;
				Integer current_scheme_slno = 0;
				boolean first_time_flag = true;
				for (Tuple t : list) {

					current_scheme_slno = t.get("TRD_SCH_SLNO", Integer.class);
					if (!current_scheme_slno.equals(last_scheme_slno)) {
						if (!first_time_flag)
							enteredDetails.add(artSchmObject);
						artSchmObject = new ArticleSchemeDetailsBean();
						artSchmObject.setTrd_scheme_id(current_scheme_slno);
						artSchmObject.setTrd_scheme_code(t.get("TRD_SCHEME_CODE", String.class));
						artSchmObject.setTrd_scheme_name(t.get("TRD_SCHEME_NAME", String.class));
						artSchmObject.setTrd_scheme_descr(t.get("TRD_SCHEME_DESCR", String.class));
						artSchmObject.setValid_from_dt(t.get("VALID_FROM_DT", Date.class));
						artSchmObject.setValid_to_dt(t.get("VALID_TO_DT", Date.class));
						artSchmObject.setApply_to(t.get("APPLY_TO", Character.class).toString());
						artSchmObject.setBilled_value(t.get("BILLED_VALUE", BigDecimal.class));
						artSchmObject.setTot_billed_value_entered(t.get("TOT_BILLED_VALUE_ENTERED", BigDecimal.class));
						artSchmObject.setArticle_prod_id(t.get("ARTICLE_PROD_ID", BigDecimal.class).intValue());
						artSchmObject.setArticle_prod_cd(t.get("ARTICLE_PROD_CD", String.class));
						artSchmObject.setArticle_prod_descr(t.get("ARTICLE_PROD_NAME", String.class));
						artSchmObject
								.setArticle_prod_qty_tot(t.get("ARTICLE_PROD_QTY_TOT", BigDecimal.class).intValue());
						artSchmObject.setArticle_prod_value(t.get("ARTICLE_PROD_VALUE", BigDecimal.class));
						artSchmObject.setArticle_prod_rate(t.get("ARTICLE_PROD_RATE", BigDecimal.class));
						artSchmObject.setArticle_qty_per_tot_val(
								t.get("ARTICLE_QTY_PER_TOT_VAL", BigDecimal.class).intValue());
						artSchmObject.setApply_inv_from(t.get("APPLY_INV_FROM", Date.class));
						artSchmObject.setDetailList(new ArrayList<ArticleSchemeTrdDetails>());
					}
					ArticleSchemeTrdDetails dtls = new ArticleSchemeTrdDetails();
					dtls.setTrd_scheme_dtl_id(t.get("TRD_SCHEME_DTL_ID", Integer.class));
					dtls.setSale_prod_id(t.get("SALE_PROD_ID", Integer.class));
					dtls.setSale_prod_name(t.get("SALE_PROD_NAME", String.class));
					dtls.setSale_prod_code_erp(t.get("SALE_PROD_CODE_ERP", String.class));
					dtls.setPer_sale_qty_billed(t.get("PER_SALE_QTY_BILLED", BigDecimal.class).intValue());
					dtls.setPer_sale_qty_billed_entered(
							t.get("PER_SALE_QTY_BILLED_ENTERED", BigDecimal.class).intValue());
					dtls.setPer_sale_qty_free(t.get("PER_SALE_QTY_FREE", BigDecimal.class).intValue());
					dtls.setPer_billed_value_entered(t.get("PER_BILLED_VALUE_ENTERED", BigDecimal.class));
					dtls.setPer_article_prod_qty(t.get("PER_ARTICLE_PROD_QTY", BigDecimal.class).intValue());
					dtls.setPer_article_prod_qty_updated(
							t.get("PER_ARTICLE_PROD_QTY_UPDATED", BigDecimal.class).intValue());

					artSchmObject.getDetailList().add(dtls);

					last_scheme_slno = current_scheme_slno;
					first_time_flag = false;
				}
				enteredDetails.add(artSchmObject);
			}
		} finally {
			if (em != null)
				em.close();
		}
		return enteredDetails;
	}

	@Override
	public CustLock getLockedCustomer(Long cust_id, Long loc_id) throws Exception {
		EntityManager em = null;
		CustLock custLock_obj = null;
		try {
			em = this.emf.createEntityManager();
			CriteriaBuilder builder = em.getCriteriaBuilder();
			CriteriaQuery<CustLock> query = builder.createQuery(CustLock.class);
			Root<CustLock> root = query.from(CustLock.class);
			query.select(root).where(builder.and(builder.equal(root.get("loc_id"), loc_id),
					builder.equal(root.get("cust_id"), cust_id)));
			custLock_obj = em.createQuery(query).getSingleResult();
		} catch (Exception e) {

		}
		return custLock_obj;
	}

	@Override
	public List<ArtSchemeBeanForApprNewLogic> getArticleSchemeForApprovalData(Long art_sch_req_hdr, String finYear,
			String companyCode, String status) throws Exception {
		EntityManager em = null;
		List<ArtSchemeBeanForApprNewLogic> list = null;
		try {
			em = this.emf.createEntityManager();
			StringBuffer sb = new StringBuffer();
			sb.append(
					" select ah.ARTICLE_REQ_ID,ah.ARTICLE_REQ_DATE,ah.REQUEST_NO,ah.INVOICE_NO,ah.INVOICE_DATE,ah.LR_NUMBER,ah.LR_DATE,ah.ARTSCH_TOTAL_VALUE, ");
			sb.append(
					" cm.ERP_CUST_CD,cm.CUST_NAME_BILLTO,cm.ADDR1_SHIPTO,cm.ADDR2_SHIPTO,cm.ADDR3_SHIPTO,cm.ADDR4_SHIPTO,cm.DESTINATION_SHIPTO,");
			sb.append(
					" dtl.SCHEME_APPLIED_TO,dtl.TRD_SCH_SLNO,tr.TRD_SCHEME_CODE,tr.TRD_SCHEME_NAME,sm.smp_prod_name,tr.BILLED_VALUE,");
			sb.append(" dtl.BILLING_VALUE as BILLED_VAL_ENTERED_TOT,dtl.ARTICLE_PROD_QTY as ARTICLES_TO_GIVE,");
			sb.append(
					" tr_dtl.TRD_SCHEME_DTL_ID,sp.SALE_PROD_NAME,tr_dtl.PER_SALE_QTY_BILLED,tr_dtl.PER_SALE_QTY_FREE,prd.ARTICLE_QTY,prd.ARTICLE_REQ_PRD_ID,dtl.ARTICLE_REQ_DTL_ID");
			sb.append(
					" ,prd.SALE_BILLED_VALUE as per_sale_billed_val,prd.SALE_PROD_QTY_TOT as per_sale_prod_qty_entered,sp.SALE_MAP_CODE1,sm.SMP_ERP_PROD_CD");
			sb.append(" from ARTICLE_SCHREQ_HDR ah");
			sb.append(" inner join CUSTOMER_MASTER cm on ah.CUST_ID = cm.CUST_ID");
			sb.append(" inner join ARTICLE_SCHREQ_DTL dtl on ah.ARTICLE_REQ_ID = dtl.ARTICLE_REQ_ID");
			sb.append(" inner join ARTICLE_SCHREQ_PROD prd on prd.ARTICLE_REQ_DTL_ID = dtl.ARTICLE_REQ_DTL_ID");
			sb.append(" inner join TRD_SCHEME_MST_HDR tr on tr.TRD_SCH_SLNO = dtl.TRD_SCH_SLNO");
			sb.append(" inner join TRD_SCHEME_MST_DTL tr_dtl on tr_dtl.TRD_SCHEME_DTL_ID = prd.TRD_SCHEME_DTL_ID");
			sb.append(" inner join smpitem sm on sm.smp_prod_id = dtl.ARTICLE_PROD_ID");
			sb.append(" inner join SALEPROD sp on sp.SALE_PROD_ID = prd.SALE_PROD_ID_SG");
			sb.append(" where ah.ARTICLE_REQ_ID = :art_req_id");
			sb.append(" and ah.ARTSCH_APPR_STATUS = :status and ah.ARTSCH_status = 'A'");
			sb.append(" and ah.FIN_YEAR = :finYear and ah.ALLOC_TYPE = 'S'");
			sb.append(" and ah.COMPANY = :companyCode and dtl.ARTSCH_DT_status = 'A'");
			sb.append(" and prd.ARTSCH_PRD_status = 'A'");

			Query q = em.createNativeQuery(sb.toString(), Tuple.class);
			q.setParameter("art_req_id", art_sch_req_hdr);
			q.setParameter("status", status);
			q.setParameter("finYear", finYear);
			q.setParameter("companyCode", companyCode);

			List<Tuple> tuple_list = q.getResultList();
			System.out.println("tuple_list:::" + tuple_list.size());
			if (tuple_list != null && tuple_list.size() > 0) {

				list = new ArrayList<ArtSchemeBeanForApprNewLogic>();
				for (Tuple t : tuple_list) {
					ArtSchemeBeanForApprNewLogic bean = new ArtSchemeBeanForApprNewLogic();
					bean.setAddr1_shipto(t.get("ADDR1_SHIPTO", String.class));
					bean.setAddr2_shipto(t.get("ADDR2_SHIPTO", String.class));
					bean.setAddr3_shipto(t.get("ADDR3_SHIPTO", String.class));
					bean.setAddr4_shipto(t.get("ADDR4_SHIPTO", String.class));
					bean.setArticle_qty(t.get("ARTICLE_QTY", BigDecimal.class).longValue());
					bean.setArticle_req_date(t.get("ARTICLE_REQ_DATE", Date.class));
					bean.setArticle_req_id(t.get("ARTICLE_REQ_ID", BigDecimal.class).longValue());
					bean.setArticle_req_dtl_id(t.get("ARTICLE_REQ_DTL_ID", BigDecimal.class).longValue());
					bean.setArticle_req_prd_id(t.get("ARTICLE_REQ_PRD_ID", Integer.class).longValue());
					bean.setArticles_to_give(t.get("ARTICLES_TO_GIVE", BigDecimal.class).longValue());
					bean.setArtsch_total_value(t.get("ARTSCH_TOTAL_VALUE", BigDecimal.class));
					bean.setBilled_val_entered_tot(t.get("BILLED_VAL_ENTERED_TOT", BigDecimal.class));
					bean.setBilled_value(t.get("BILLED_VALUE", BigDecimal.class));
					bean.setCust_name_billto(t.get("CUST_NAME_BILLTO", String.class));
					bean.setDestination_shipto(t.get("DESTINATION_SHIPTO", String.class));
					bean.setErp_cust_cd(t.get("ERP_CUST_CD", String.class));
					bean.setInvoice_date(t.get("INVOICE_DATE", Date.class));
					bean.setInvoice_no(t.get("INVOICE_NO", String.class));
					bean.setLr_date(t.get("LR_DATE", Date.class));
					bean.setLr_number(t.get("LR_NUMBER", String.class));
					bean.setPer_sale_billed_val(t.get("per_sale_billed_val", BigDecimal.class));
					bean.setPer_sale_prod_qty_entered(t.get("per_sale_prod_qty_entered", BigDecimal.class).longValue());
					bean.setPer_sale_qty_billed(t.get("PER_SALE_QTY_BILLED", BigDecimal.class).longValue());
					bean.setPer_sale_qty_free(t.get("PER_SALE_QTY_FREE", BigDecimal.class).longValue());
					bean.setRequest_no(t.get("REQUEST_NO", String.class));
					bean.setSale_prod_name(t.get("SALE_PROD_NAME", String.class));
					bean.setScheme_applied_to(t.get("SCHEME_APPLIED_TO", Character.class).toString());
					bean.setSmp_prod_name(t.get("SMP_PROD_NAME", String.class));
					bean.setTrd_sch_slno(t.get("TRD_SCH_SLNO", Integer.class).longValue());
					bean.setTrd_scheme_code(t.get("TRD_SCHEME_CODE", String.class));
					bean.setTrd_scheme_dtl_id(t.get("TRD_SCHEME_DTL_ID", Integer.class).longValue());
					bean.setTrd_scheme_name(t.get("TRD_SCHEME_NAME", String.class));
					bean.setSale_prod_code(t.get("SALE_MAP_CODE1", String.class));
					bean.setSmp_erp_prod_cd(t.get("SMP_ERP_PROD_CD", String.class));
					list.add(bean);
				}
			}
		} finally {
			if (em != null)
				em.close();
		}
		return list;
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public void dispatch_to_194R_push() throws Exception {
		String sqlString = "EXEC TDS_194R_DATA_UPDATE";
		Query query = this.entityManager.createNativeQuery(sqlString);
		query.executeUpdate();
	}

	@Override
	public Boolean checkIfInvoiceExistsInSalesData(Long loc_id, String entered_invoice_no) throws Exception {
		EntityManager em = null;
		Integer count = 0;
		try {
			em = this.emf.createEntityManager();
			StringBuffer sql = new StringBuffer();
			sql.append(" select count(*) as count from SALE_DATA_UPLOAD SDATA LEFT JOIN SALEPROD SP ");
			sql.append(" ON SP.SALE_MAP_CODE1=SDATA.SAP_Sales_Product_Code");
			sql.append(" JOIN LOCATION L ON L.SAP_LOC_MAP_CD=SDATA.SAP_Plant_Code");
			sql.append(" WHERE SP.SALE_MAP_CODE1 IS NOT NULL");
			sql.append(" AND L.LOC_ID=:loc_id and SAP_INVOICE_NUMBER = :entered_inv_no");

			Query query = em.createNativeQuery(sql.toString());
			query.setParameter("loc_id", loc_id);
			query.setParameter("entered_inv_no", entered_invoice_no);
			count = (Integer) query.getSingleResult();
			if (count.compareTo(0) > 0) {
				return true;
			} else
				return false;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		} finally {
			if (em != null)
				em.close();
		}
	}

	@Override
	public List<SapSalesInvoiceData> getSalesProductDataFromInvoice(String sap_invoice_no) throws Exception {
		EntityManager em = null;
		List<SapSalesInvoiceData> list = null;
		try {
			em = this.emf.createEntityManager();
			StringBuffer sb = new StringBuffer();
			sb.append(
					" select cm.cust_id,sd.SAP_Invoice_Date,sd.LR_Number,sd.LR_Date,sp.SALE_PROD_ID,sp.SALE_PROD_CD,sd.Billed_Qty,sd.Billing_Value");
			sb.append(
					" from SALE_DATA_UPLOAD sd inner join SALEPROD sp on sp.SALE_MAP_CODE1 = sd.SAP_Sales_Product_Code");
			sb.append(
					" inner join CUSTOMER_MASTER cm on cm.ERP_CUST_CD = sd.SAP_Customer_Code where SAP_Invoice_Number = :sap_inv_no");
			Query query = em.createNativeQuery(sb.toString(), Tuple.class);
			query.setParameter("sap_inv_no", sap_invoice_no);
			List<Tuple> tuple_list = query.getResultList();
			if (tuple_list != null && tuple_list.size() > 0) {
				list = new ArrayList<SapSalesInvoiceData>();
				for (Tuple tup : tuple_list) {
					SapSalesInvoiceData sap_sl_inv_data = new SapSalesInvoiceData(
							tup.get("cust_id", BigDecimal.class).longValue(), tup.get("SAP_Invoice_Date", Date.class),
							tup.get("LR_Number", String.class), tup.get("LR_Date", Date.class),
							tup.get("SALE_PROD_ID", Integer.class).longValue(), tup.get("SALE_PROD_CD", String.class),
							tup.get("Billed_Qty", BigDecimal.class).longValue(),
							tup.get("Billing_Value", BigDecimal.class));
					list.add(sap_sl_inv_data);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (em != null)
				em.close();
		}
		return list;
	}

	@Override
	public BigDecimal getDistanceForChallan(Long dsp_id) throws Exception {
		String sqlString = "select DISTANCE from ARTICLE_SCHREQ_HDR where ARTICLE_REQ_ID = "
				+ " (select BLK_HCP_REQ_ID from dispatch_header where dsp_id = :dsp_id)";
		Query update_query = this.entityManager.createNativeQuery(sqlString);
		update_query.setParameter("dsp_id", dsp_id);
		return (BigDecimal) update_query.getSingleResult();
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public void updateArtReqDistance(Long dsp_id, BigDecimal distance) throws Exception {
		String sqlString = "update ARTICLE_SCHREQ_HDR set DISTANCE = :distance  where ARTICLE_REQ_ID = "
				+ " (select BLK_HCP_REQ_ID from dispatch_header where dsp_id = :dsp_id)";
		Query update_query = this.entityManager.createNativeQuery(sqlString);
		update_query.setParameter("distance", distance);
		update_query.setParameter("dsp_id", dsp_id);
		update_query.executeUpdate();
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public void unlockAllCustomers() throws Exception {
		StringBuffer buffer = new StringBuffer();
		buffer.append("DELETE FROM CUST_LOCK");
		Query query = entityManager.createNativeQuery(buffer.toString());
		query.executeUpdate();
	}

	@Override
	public List<ArticleSchemeDetailsBean> get_duplecate_invoice(String articleRequestId, String sapInvNo) {

		List<ArticleSchemeDetailsBean> enteredDetails = new ArrayList<>();
		ArticleSchemeDetailsBean artSchmObject = null;
		EntityManager em = null;
		try {
			em = this.emf.createEntityManager();
			StringBuffer sb = new StringBuffer();

			sb.append(" select  tm.TRD_SCHEME_NAME, dtl.TRD_SCH_SLNO   from ARTICLE_SCHREQ_HDR ah");
			sb.append(" inner join ARTICLE_SCHREQ_DTL dtl on dtl.ARTICLE_REQ_ID = ah.ARTICLE_REQ_ID");
			sb.append(" inner join TRD_SCHEME_MST_HDR tm on tm.TRD_SCH_SLNO = dtl.TRD_SCH_SLNO");
			sb.append(" where ah.INVOICE_NO = :invoice_no and ah.ARTICLE_REQ_ID <> :articleRequestId");

			Query query = em.createNativeQuery(sb.toString(), Tuple.class);
			query.setParameter("invoice_no", sapInvNo);
			query.setParameter("articleRequestId", articleRequestId);

			List<Tuple> list = query.getResultList();
			if (list != null && list.size() > 0) {

				for (Tuple t : list) {
					artSchmObject = new ArticleSchemeDetailsBean();
					artSchmObject.setTrd_scheme_name(t.get("TRD_SCHEME_NAME", String.class));
					artSchmObject.setTrd_scheme_id(t.get("TRD_SCH_SLNO", Integer.class));
					enteredDetails.add(artSchmObject);
				}

			}

		} catch (Exception e) {
			e.printStackTrace();

		}

		return enteredDetails;

	}

	@Override
	public List<ArticleSchemeDetailsBean> get_Scheme_detail(String invoice_no) {
		List<ArticleSchemeDetailsBean> enteredDetails = new ArrayList<>();
		ArticleSchemeDetailsBean artSchmObject = null;
		EntityManager em = null;
		try {
			em = this.emf.createEntityManager();
			StringBuffer sb = new StringBuffer();
			sb.append(" select tm.TRD_SCHEME_NAME, dtl.TRD_SCH_SLNO,prd.SALE_PROD_ID_SG from ARTICLE_SCHREQ_HDR ah");
			sb.append(" inner join ARTICLE_SCHREQ_DTL dtl on dtl.ARTICLE_REQ_ID = ah.ARTICLE_REQ_ID");
			sb.append(" inner join ARTICLE_SCHREQ_PROD prd on dtl.ARTICLE_REQ_DTL_ID = prd.ARTICLE_REQ_DTL_ID");
			sb.append(" inner join TRD_SCHEME_MST_HDR tm on tm.TRD_SCH_SLNO = dtl.TRD_SCH_SLNO");
			sb.append(" where ah.INVOICE_NO =:invoice_no AND ah.ARTSCH_status = 'A' and dtl.ARTSCH_DT_STATUS = 'A'");
			Query query = em.createNativeQuery(sb.toString(), Tuple.class);
			query.setParameter("invoice_no", invoice_no);
			List<Tuple> list = query.getResultList();
			if (list != null && list.size() > 0) {
				for (Tuple t : list) {
					artSchmObject = new ArticleSchemeDetailsBean();
					artSchmObject.setTrd_scheme_name(t.get("TRD_SCHEME_NAME", String.class));
					artSchmObject.setTrd_scheme_id(t.get("TRD_SCH_SLNO", Integer.class));
					artSchmObject.setSale_prod_id_for_dupl_check(t.get("SALE_PROD_ID_SG", Integer.class));
					enteredDetails.add(artSchmObject);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
		return enteredDetails;
	}

	@Override
	@Transactional(rollbackFor = Exception.class)
	public void updateAlcHdMonthChange(String finyear, String period_cd, Long alloc_smp_id, Long article_req_id)
			throws Exception {
		StringBuffer sb = new StringBuffer().append(
				"UPDATE AH SET AH.ALLOC_SMP_ID=:alloc_smp_id,AH.ALLOC_FIN_YEAR=:finyear,AH.ALLOC_PERIOD_CODE=:period_cd")
				.append(" FROM ALLOC_HEADER AH")
				.append(" JOIN ARTICLE_SCHREQ_HDR ASH ON ASH.ARTICLE_REQ_ID= AH.BLK_HCP_REQ_ID")
				.append(" JOIN ALLOC_DETAIL AD ON AD.ALLOCDTL_ALLOC_ID=AH.ALLOC_ID")
				.append(" WHERE (ad.ALLOCDTL_CURR_ALLOC_QTY - ad.ALLOCDTL_SUPPLY_QTY)>0")
				.append(" AND MONTH(ASH.ARTICLE_REQ_DATE )<> month(getdate())")
				.append(" AND year(ASH.ARTICLE_REQ_DATE )=year(getdate())")
				.append(" and ash.ARTICLE_REQ_ID=:article_req_id");
		Query query = entityManager.createNativeQuery(sb.toString(), Tuple.class);
		query.setParameter("finyear", finyear);
		query.setParameter("period_cd", period_cd);
		query.setParameter("alloc_smp_id", alloc_smp_id);
		query.setParameter("article_req_id", article_req_id);
		query.executeUpdate();
	}

	@Override
	@Transactional(rollbackFor = Exception.class)
	public void updateAlcDetailsMonthChange(String finyear, String period_cd, Long alloc_smp_id, Long article_req_id)
			throws Exception {
		StringBuffer sb = new StringBuffer().append(
				"UPDATE AD SET AD.ALLOCDTL_SMPD_ID=:alloc_smp_id ,AD.ALLOCDTL_FIN_YEAR=:finyear , AD.ALLOCDTL_PERIOD_CODE=:period_cd")
				.append(" FROM ALLOC_HEADER AH")
				.append(" JOIN ARTICLE_SCHREQ_HDR ASH ON ASH.ARTICLE_REQ_ID= AH.BLK_HCP_REQ_ID")
				.append(" JOIN ALLOC_DETAIL AD ON AD.ALLOCDTL_ALLOC_ID=AH.ALLOC_ID")
				.append(" WHERE (ad.ALLOCDTL_CURR_ALLOC_QTY - ad.ALLOCDTL_SUPPLY_QTY)>0")
				.append(" AND MONTH(ASH.ARTICLE_REQ_DATE )<> month(getdate())")
				.append(" AND year(ASH.ARTICLE_REQ_DATE )=year(getdate())")
				.append(" and ash.ARTICLE_REQ_ID=:article_req_id");
		Query query = entityManager.createNativeQuery(sb.toString(), Tuple.class);
		query.setParameter("finyear", finyear);
		query.setParameter("period_cd", period_cd);
		query.setParameter("alloc_smp_id", alloc_smp_id);
		query.setParameter("article_req_id", article_req_id);
		query.executeUpdate();
	}

}
