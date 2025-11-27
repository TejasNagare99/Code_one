package com.excel.repository;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.Tuple;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.excel.model.CRMReqRequestsImage;
import com.excel.model.CRM_Req_Request;
import com.excel.model.CrmSchemeMaster;

@Repository
public class CrmSchemeRepositoryImpl implements CrmSchemeRepository {

	@Autowired
	EntityManagerFactory emf;
	@PersistenceContext
	private EntityManager entityManager;

	@Override
	public List<CrmSchemeMaster> getCRMSchemeDetails(String companyCode, String crm_sch_type, String curBiz,
			String expBiz, Long locId, String doccls, String docspl) throws Exception {
		EntityManager em = null;
		List<CrmSchemeMaster> list = null;
		System.out.println("companyCode :: " + companyCode);
		System.out.println("crm_sch_type :: " + crm_sch_type);
		System.out.println("curBiz :: " + curBiz);
		System.out.println("expBiz :: " + expBiz);
		System.out.println("locId :: " + locId);
		System.out.println("doccls :: " + doccls);
		System.out.println("docspl ::" + docspl);

		try {
			em = this.emf.createEntityManager();
			CriteriaBuilder builder = em.getCriteriaBuilder();
			CriteriaQuery<CrmSchemeMaster> criteriaQuery = builder.createQuery(CrmSchemeMaster.class);
			Root<CrmSchemeMaster> root = criteriaQuery.from(CrmSchemeMaster.class);
			criteriaQuery.select(root);
			if (crm_sch_type.equalsIgnoreCase("D")) {
				criteriaQuery.where(builder.and(builder.equal(root.get("company_cd"), companyCode),
						builder.equal(root.get("customer_type"), crm_sch_type),
						builder.equal(root.get("approval_status"), "A"),

						builder.equal(root.get("doctor_speciality"), docspl),
						builder.equal(root.get("doctor_type"), doccls),

						builder.le(root.get("doc_current_biz_from").as(Double.class), Double.valueOf(curBiz)),
						builder.gt(root.get("doc_current_biz_to").as(Double.class), Double.valueOf(curBiz)),
						builder.le(root.get("doc_expected_biz_from").as(Double.class), Double.valueOf(expBiz)),
						builder.gt(root.get("doc_expected_biz_to").as(Double.class), Double.valueOf(expBiz))));
			}
			else if(crm_sch_type.equalsIgnoreCase("W")) {
				criteriaQuery.where(builder.and(builder.equal(root.get("company_cd"), companyCode),
						builder.equal(root.get("customer_type"), crm_sch_type),
						builder.equal(root.get("approval_status"), "A"),
						builder.equal(root.get("loc_id"), locId),

						builder.le(root.get("cust_current_biz_from").as(Double.class), Double.valueOf(curBiz)),
						builder.gt(root.get("cust_current_biz_to").as(Double.class), Double.valueOf(curBiz)),
						builder.le(root.get("cust_expected_biz_from").as(Double.class), Double.valueOf(expBiz)),
						builder.gt(root.get("cust_expected_biz_to").as(Double.class), Double.valueOf(expBiz))));
			}

			list = em.createQuery(criteriaQuery).getResultList();

		} finally {
			if (em != null) {
				em.close();
			}
		}
		return list;
	}

	@Override
	public String getCRMReqno() throws Exception {
		EntityManager em = null;

		String result;
		try {
			em = emf.createEntityManager();
			StringBuffer sb = new StringBuffer();
			sb.append(" SELECT 'CRMEXP' + NUM  CRM_REQ_NO FROM ( ");
			sb.append(
					" SELECT FORMAT( CONVERT( NUMERIC , ISNULL(MAX( SUBSTRING( CRM_REQ_NO ,7,6 ) ) , '0' ) ) + 1 , '000000' ) NUM ");
			sb.append(" FROM CRM_REQ_REQUESTS ) DT  ");

			Query query = em.createNativeQuery(sb.toString());
			result = (String) query.getResultList().get(0);

			System.out.println("result :: " + result);
		} finally {
			if (em != null) {
				em.close();
			}
		}
		return result;
	}

	@Override
	public List<CRM_Req_Request> getCRMDetailsByids(List<Long> crm_req_id) throws Exception {
		List<CRM_Req_Request> crmlist = new ArrayList<>();
		System.out.println("crm_req_id :crm: " + crm_req_id);
		CriteriaBuilder builder = entityManager.getCriteriaBuilder();
		CriteriaQuery<CRM_Req_Request> criteriaQuery = builder.createQuery(CRM_Req_Request.class);
		Root<CRM_Req_Request> root = criteriaQuery.from(CRM_Req_Request.class);
		criteriaQuery.select(root).where(builder.and(builder.in(root.get("crm_req_id")).value(crm_req_id)));
		crmlist = entityManager.createQuery(criteriaQuery).getResultList();

		System.out.println("List size " + crmlist.size());
		return crmlist;

	}

	@Override
	public List<CRM_Req_Request> getCRMDetailsBySingleid(String crm_req_id) throws Exception {
		EntityManager em = null;
		List<CRM_Req_Request> list = null;
		System.out.println("crm_req_id :: " + crm_req_id);
		try {
			em = this.emf.createEntityManager();

			StringBuffer sb = new StringBuffer();
			sb.append("select * from  CRM_REQ_REQUESTS where crm_req_id in (" + crm_req_id + ") ");
			Query query = em.createNativeQuery(sb.toString(), CRM_Req_Request.class);
			list = query.getResultList();

		} finally {
			if (em != null) {
				em.close();
			}
		}
		return list;
	}

	@Override
	public List<CRMReqRequestsImage> geImageBycrmId(Long crmId) throws Exception {
		List<CRMReqRequestsImage> list = new ArrayList<CRMReqRequestsImage>();
		try {
			CriteriaBuilder builder = entityManager.getCriteriaBuilder();
			CriteriaQuery<CRMReqRequestsImage> criteriaQuery = builder.createQuery(CRMReqRequestsImage.class);
			Root<CRMReqRequestsImage> root = criteriaQuery.from(CRMReqRequestsImage.class);
			criteriaQuery.select(root).where(builder.equal(root.get("crm_id"), crmId));
			list = entityManager.createQuery(criteriaQuery).getResultList();
		}

		catch (Exception e) {
			throw e;
		}
		return list;
	}

	@Override
	public List<CRM_Req_Request> getviewlist(String userid) throws Exception {

		System.out.println("userid :: " + userid);
		List<CRM_Req_Request> list = null;
		try {
			StringBuffer sb = new StringBuffer();

			sb.append(
					" select req.crm_req_id,req.CRM_REQ_NO,convert(DATE,req.CRM_REQ_DATE,103) REQUEST_DATE,convert(DATE,req.CRM_SCHED_DATE,103) SCHEDULE_DATE, ");
			sb.append(
					" req.CRM_REQ_CRM_NAME,req.CRM_REQ_SCHEME_ID,scm.CRM_SCHEME_NAME,req.CRM_REQ_CUST_TYPE CUST_TYPE, ");
			sb.append(" req.CRM_REQ_QTY,req.CRM_REQ_RATE,req.CRM_REQ_VALUE,req.CRM_REQ_CONFIRM ");
			sb.append(" from CRM_SCHEME_MST_HDR scm,CRM_REQ_REQUESTS req ");
			sb.append(
					" where req.CRM_REQ_SCHEME_ID=scm.CRM_SCH_ID and req.USER_ID=:userid and crm_req_confirm='N'  order by req.CRM_REQ_ID desc ");

			Query query = entityManager.createNativeQuery(sb.toString(), Tuple.class);
			query.setParameter("userid", userid);

			List<Tuple> tuples = query.getResultList();

			if (tuples != null && !tuples.isEmpty()) {
				list = new ArrayList<>();
				CRM_Req_Request object = null;
				for (Tuple t : tuples) {
					object = new CRM_Req_Request();
					object.setCrm_req_id(Long.valueOf(t.get("crm_req_id", Integer.class)));
					object.setCrm_req_date(t.get("REQUEST_DATE", Date.class));
					object.setCrm_sched_date(t.get("SCHEDULE_DATE", Date.class));
					object.setCrm_req_crm_name(t.get("CRM_REQ_CRM_NAME", String.class));
					object.setCrm_req_scheme_id(Long.valueOf(t.get("CRM_REQ_SCHEME_ID", BigDecimal.class).toString()));
					object.setCrm_scheme_name(t.get("CRM_SCHEME_NAME", String.class));
					object.setCrm_req_qty(t.get("CRM_REQ_QTY", BigDecimal.class));
					object.setCrm_req_rate(t.get("CRM_REQ_RATE", BigDecimal.class));
					object.setCrm_req_value(t.get("CRM_REQ_VALUE", BigDecimal.class));
					object.setCrm_req_confirm(t.get("CRM_REQ_CONFIRM", Character.class).toString());
					object.setCust_type(t.get("CUST_TYPE", String.class));
					object.setCrm_req_no(t.get("CRM_REQ_NO", String.class));
					list.add(object);
				}
				System.out.println("get Address and contact :: " + list.size());
				if (!list.isEmpty() && list.size() > 0)
					return list;
			}
			if (list != null && !list.isEmpty()) {
				return list;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		return null;
	}


}
