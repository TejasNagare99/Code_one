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
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.excel.bean.AllocationBean;
import com.excel.bean.AllocationConsolidationBean;
import com.excel.bean.Parameter;
import com.excel.model.AllocConHd;
import com.excel.model.AllocConHd_;
import com.excel.model.AspDetail;
import com.excel.model.AspDetail_;
import com.excel.model.AspHeader;
import com.excel.model.DivMaster;
import com.excel.model.V_Emp_Div_Access;
import com.excel.model.V_Emp_Div_Access_;
import com.excel.utility.MedicoConstants;
import com.excel.utility.Utility;

@Repository
public class AllocConsolidationRepositoryImpl implements AllocConsolidationRepository,MedicoConstants{
	
	@Autowired EntityManagerFactory emf;
	@PersistenceContext private EntityManager entityManager;
	
	@Override
	public AllocConHd getObjectAllocConHeader(Long headerId) throws Exception {
		return this.entityManager.find(AllocConHd.class, headerId);
	}
	
	@Override
	public List<AllocationConsolidationBean> getAllocConHeaderByType(String alloc_con_type) throws Exception {
		List<AllocationConsolidationBean> list = null;
		EntityManager em = null;
		try {
			StringBuffer sb = new StringBuffer();
			sb.append(" SELECT ACH.DIV_ID,ACH.ALLOC_CON_ID,ISNULL(CONVERT(VARCHAR,ACH.ALLOC_CON_DATE,103),'NA') DATE_CONSOLIDATED,ACH.FILE_DOWNLOAD,ACH.FILE_UPLOAD,ACH.ALLOC_CON_DOCNO,ACH.DIVISION");
			sb.append(" from ALLOC_CON_HD ACH where ACH.STATUS='A' and ACH.ALLOC_CON_TYPE in('XM','XA')");
			Query query = entityManager.createNativeQuery(sb.toString(), Tuple.class);
			//query.setParameter("year", year);
			List<Tuple> tuples = query.getResultList();
			if (tuples != null && !tuples.isEmpty()) {
				list = new ArrayList<>();
				AllocationConsolidationBean object = null;
				for (Tuple t : tuples) {
					object = new AllocationConsolidationBean();
					object.setAllocConId(t.get("ALLOC_CON_ID", BigDecimal.class)==null?0L:Long.valueOf(t.get("ALLOC_CON_ID", BigDecimal.class).toString()));
					object.setDocumentNumber(t.get("ALLOC_CON_DOCNO", String.class));
					object.setAllocationDate(t.get("DATE_CONSOLIDATED", String.class));
					object.setDivision(t.get("DIVISION", String.class));
					object.setFile_download(t.get("FILE_DOWNLOAD", Character.class));
					object.setFile_upload(t.get("FILE_UPLOAD", Character.class));
					object.setDivId(t.get("DIV_ID", Integer.class)==null?0L:Long.valueOf(t.get("DIV_ID", Integer.class)));
					list.add(object);
				}
			if(!list.isEmpty() && list.size() > 0)
				return list;
	
			}
		}finally {
			if (em != null) {
				em.close();
			}
		}
		return list;
	}
	
	
	@Override
	public List<Object> getAnnualPlanEnteredManagerByDivision(String empId,Long divId,String year,String status,String compcode) throws Exception{
		EntityManager em = null;
		List<Object> list = null;
		System.out.println("year "+year);
		try {
			
			StringBuffer sb = new StringBuffer();
			sb.append(" SELECT USERID,PROD_MGR,MGR_EMP_ID,ALLOC_CON_ID_T,ASP_ID,ALLOC_DOC_NUM,STATUS, DIVISION, DATE_GENERATED from");
			sb.append(" ( SELECT USERID,PROD_MGR,MGR_EMP_ID,ALLOC_CON_ID_T,ASP_ID,ALLOC_DOC_NUM,STATUS, DIVISION, DATE_GENERATED");
			sb.append(" FROM ( SELECT SKU.USERID,CONCAT(USR.EMP_FNM,ISNULL(USR.EMP_MNM,''),USR.EMP_LNM) PROD_MGR, DM.DIV_ID,DM.DIV_DISP_NM DIVISION,");
			sb.append(" CASE WHEN ASP.ASP_DIV_ID = DM.DIV_ID THEN ASP.MGR_EMP_ID ELSE ' ' END MGR_EMP_ID,");
			sb.append(" CASE WHEN ASP.ASP_DIV_ID = DM.DIV_ID THEN ASP.ALLOC_CON_ID_T  ELSE 0 END ALLOC_CON_ID_T,");
			sb.append(" CASE WHEN ASP.ASP_DIV_ID = DM.DIV_ID THEN ASP.ASP_ID  ELSE 0 END ASP_ID,");
			sb.append(" CASE WHEN ASP.ASP_DIV_ID = DM.DIV_ID THEN ASP.ASP_ALLOC_NUMBER ELSE 'Not Submitted' END AS ALLOC_DOC_NUM,");
			sb.append(" CASE WHEN ASP.ASP_DIV_ID = DM.DIV_ID AND ASP.asp_appr_status='A' THEN 'APPROVED' ELSE 'PENDING' END AS STATUS,");
			sb.append(" CASE WHEN ASP.ASP_DIV_ID = DM.DIV_ID THEN isnull(CONVERT(varchar,ASP.ASP_INS_DT,105),'') ELSE ' ' END DATE_GENERATED");
			sb.append(" FROM USER_SKU_MAP SKU");
			sb.append(" LEFT OUTER   JOIN  ASP_HEADER ASP ON SKU.USERID = ASP.ASP_BRAND_MGRID AND ASP.ASP_status = 'A'    ");
			sb.append(" AND ASP.ASP_FIN_YEAR= :year AND ASP.ALLOC_CON_DTL_ID_C IS NULL AND ASP.ALLOC_CON_DTL_ID_T IS NULL");
			sb.append(" left outer JOIN am_m_login_detail LD ON SKU.USERID = LD.LD_ID");
			sb.append(" left outer join  am_m_emp_div_access div on div.ediv_emp_id = ld.ld_emp_cb_id AND div.ediv_div_id = ASP.ASP_DIV_ID");
			sb.append(" left outer JOIN DIV_MASTER DM ON DM.DIV_ID = DIV.EDIV_DIV_ID");
			sb.append(" left outer JOIN  hr_m_employee USR ON LD.ld_emp_cb_id = USR.EMP_ID");
			if(compcode.trim().equals("SER")) {
				sb.append(" WHERE  USR.emp_egrp_id = 'EG0016' ") ;
			}
			if(compcode.trim().equals("PFZ")) {
				sb.append(" WHERE  USR.emp_egrp_id = 'EG0020' ") ;
			}
			sb.append(" AND LD.LD_EXEC_ASST_IND = 'N' AND ");
			if(divId!=0)
					sb.append(" DM.DIV_ID=:divId");
			 else {
				sb.append(" DM.DIV_ID in(select div_id from  DIV_MASTER  ");
				sb.append(" WHERE div_id in (select ediv_div_id from am_m_emp_div_access where ediv_emp_id=:empId and ediv_status='A')  AND DIV_status = 'A')");
			}
			sb.append(" AND DIV_ID IN");
			sb.append(" ( SELECT ASP_DIV_ID FROM ASP_HEADER ASP WHERE SKU.USERID = ASP.ASP_BRAND_MGRID AND ASP.ASP_status = 'A'");
			sb.append(" AND ASP.ASP_FIN_YEAR= :year AND ASP.ALLOC_CON_DTL_ID_C IS NULL AND ASP.ALLOC_CON_DTL_ID_T IS NULL ) ) D");
			sb.append(" GROUP BY USERID,PROD_MGR,DIVISION,MGR_EMP_ID,ALLOC_CON_ID_T,ASP_ID,ALLOC_DOC_NUM, STATUS,DATE_GENERATED");
			sb.append(" union all");
			sb.append(" select ld.ld_id USERID, CONCAT(USR.EMP_FNM,ISNULL(USR.EMP_MNM,''),USR.EMP_LNM) PROD_MGR,ld.ld_emp_cb_id MGR_EMP_ID,");
			sb.append(" null ALLOC_CON_ID_T,0 ASP_ID,null ALLOC_DOC_NUM,'PENDING' STATUS, DM.DIV_DISP_NM DIVISION, null DATE_GENERATED");
			sb.append(" from am_m_login_detail ld, am_m_emp_div_access div , hr_m_employee usr ,DIV_MASTER DM");
			if(compcode.trim().equals("SER")) {
				sb.append(" where usr.emp_egrp_id = 'EG0016' ");
			}
			if(compcode.trim().equals("PFZ")) {
				sb.append(" where usr.emp_egrp_id = 'EG0020' ");
			}
			sb.append(" and LD.ld_emp_cb_id = USR.EMP_ID");
			sb.append(" and div.ediv_emp_id = ld.ld_emp_cb_id AND ");
			if(divId!=0)
				sb.append(" div.ediv_div_id=:divId");
			 else {
				sb.append(" div.ediv_div_id in(select div_id from  DIV_MASTER  ");
				sb.append(" WHERE div_id in (select ediv_div_id from am_m_emp_div_access where ediv_emp_id=:empId and ediv_status='A')  AND DIV_status = 'A')");
			}
			sb.append(" and DM.DIV_ID = DIV.EDIV_DIV_ID");
			sb.append(" and ld.ld_id not in ( select asp_brand_mgrid from ASP_HEADER where ASP_status = 'A' and ASP_FIN_YEAR=:year and ");
			if(divId!=0)
				sb.append(" ASP_DIV_ID=:divId");
			else {
				sb.append(" ASP_DIV_ID in(select div_id from  DIV_MASTER  ");
				sb.append(" WHERE div_id in (select ediv_div_id from am_m_emp_div_access where ediv_emp_id=:empId and ediv_status='A')  AND DIV_status = 'A')");
			}
			sb.append(" ) AND LD.ld_emp_cb_id IN ( select mgr_ld_emp_cb_id from USER_MGR_ASST )  ) dt");
			sb.append(" order by dt.DIVISION,prod_mgr ");

//			sb.append(" SELECT USERID,PROD_MGR,MGR_EMP_ID,ALLOC_CON_ID_T,ASP_ID,ALLOC_DOC_NUM,STATUS,");
//			sb.append(" DIVISION, DATE_GENERATED");
//			sb.append(" FROM (");
//			sb.append(" SELECT SKU.USERID,CONCAT(USR.EMP_FNM,ISNULL(USR.EMP_MNM,''),USR.EMP_LNM) PROD_MGR,");
//			sb.append(" DM.DIV_ID,DM.DIV_DISP_NM DIVISION,");
//			 sb.append(" CASE WHEN ASP.ASP_DIV_ID = DM.DIV_ID THEN ASP.MGR_EMP_ID ELSE ' ' END MGR_EMP_ID,");
//			 sb.append(" CASE WHEN ASP.ASP_DIV_ID = DM.DIV_ID THEN ASP.ALLOC_CON_ID_T  ELSE 0 END ALLOC_CON_ID_T,");
//			 sb.append(" CASE WHEN ASP.ASP_DIV_ID = DM.DIV_ID THEN ASP.ASP_ID  ELSE 0 END ASP_ID,");
//			 sb.append(" CASE WHEN ASP.ASP_DIV_ID = DM.DIV_ID THEN ASP.ASP_ALLOC_NUMBER ELSE 'Not Submitted' END AS ALLOC_DOC_NUM,");
//			 sb.append(" CASE WHEN ASP.ASP_DIV_ID = DM.DIV_ID AND ASP.asp_appr_status='A' THEN 'COMPLETED' ELSE 'PENDING' END AS STATUS,");
//			 sb.append(" CASE WHEN ASP.ASP_DIV_ID = DM.DIV_ID THEN isnull(CONVERT(varchar,ASP.ASP_INS_DT,105),'') ELSE ' ' END DATE_GENERATED");
//			 sb.append(" FROM USER_SKU_MAP SKU");
//			 sb.append(" LEFT OUTER  ");
//			 sb.append(" JOIN  ASP_HEADER ASP ON SKU.USERID = ASP.ASP_BRAND_MGRID");
//			 sb.append(" AND ASP.ASP_status = 'A' AND ASP.ASP_FIN_YEAR= '2020' AND ASP.ALLOC_CON_DTL_ID_C IS NULL AND ASP.ALLOC_CON_DTL_ID_T IS NULL");
//			 sb.append(" JOIN am_m_login_detail LD ON SKU.USERID = LD.LD_ID");
//			 sb.append(" join  am_m_emp_div_access div on div.ediv_emp_id = ld.ld_emp_cb_id AND div.ediv_div_id = ASP.ASP_DIV_ID");
//			 sb.append(" JOIN DIV_MASTER DM ON DM.DIV_ID = DIV.EDIV_DIV_ID");
//			 sb.append(" JOIN  hr_m_employee USR ON LD.ld_emp_cb_id = USR.EMP_ID");
//			 sb.append(" WHERE  USR.emp_egrp_id = 'EG0020' AND LD.LD_EXEC_ASST_IND = 'N'");
//			 if(divId!=0)
//					sb.append(" AND DM.DIV_ID=:divId");
//			 else {
//				sb.append(" AND DM.DIV_ID in(select div_id from  DIV_MASTER  ");
//				sb.append(" WHERE div_id in (select ediv_div_id from am_m_emp_div_access where ediv_emp_id=:empId and ediv_status='A')  AND DIV_status = 'A')");
//			 }
//			 sb.append(" AND DIV_ID IN ( SELECT ASP_DIV_ID FROM ASP_HEADER ASP WHERE SKU.USERID = ASP.ASP_BRAND_MGRID");
//			 sb.append(" AND ASP.ASP_status = 'A' AND ASP.ASP_FIN_YEAR= '2020' AND ASP.ALLOC_CON_DTL_ID_C IS NULL AND ASP.ALLOC_CON_DTL_ID_T IS NULL )");
//			 sb.append(" ) D where (D.ALLOC_CON_ID_T=0 or D.ALLOC_CON_ID_T is null)");
//			 sb.append(" GROUP BY USERID,PROD_MGR,DIVISION,MGR_EMP_ID,ALLOC_CON_ID_T,ASP_ID,ALLOC_DOC_NUM,");
//			 sb.append(" STATUS,DATE_GENERATED order by D.DIVISION,D.DATE_GENERATED");

			Query query = entityManager.createNativeQuery(sb.toString(), Tuple.class);
			query.setParameter("year", year);
			if(divId!=0)
				query.setParameter("divId", divId);
			else
				query.setParameter("empId", empId);
			List<Tuple> tuples = query.getResultList();
			if (tuples != null && !tuples.isEmpty()) {
				list = new ArrayList<>();
				AllocationConsolidationBean object = null;
				for (Tuple t : tuples) {
					object = new AllocationConsolidationBean();
					object.setAspId(t.get("ASP_ID", Integer.class)==null?0L:Long.valueOf(t.get("ASP_ID", Integer.class)));
					object.setDocumentNumber(t.get("ALLOC_DOC_NUM", String.class));
					object.setAllocationDate(t.get("DATE_GENERATED", String.class));
					object.setManagerName(t.get("PROD_MGR", String.class));
					object.setDivision(t.get("DIVISION", String.class));
					object.setStatus(t.get("STATUS", String.class));
					if(object.getAspId()!=0L) {
						object.setBrandsCovered(this.getAnnualPlanEnteredBrandsByHeader(object.getAspId()));
						object.setPlanType(this.getAnnualPlanEnteredPlanTypeByAspHeader(object.getAspId()));
					}
					list.add(object);
				}
			if(!list.isEmpty() && list.size() > 0)
				return list;
	
			}
	
		} finally {
			if (em != null) { em.close(); }
		}
		return list;
	}
	
	@Override
	public String getAnnualPlanEnteredBrandsByHeader(Long aspId) throws Exception{
		EntityManager em = null;
		String brandsCovered="";
		String temp="";
		try {
			StringBuffer sb = new StringBuffer();
			sb.append(" SELECT DISTINCT ASP.ASP_ID,AD.ASP_BRAND_ID,SG.SA_GROUP_NAME AS BRANDS");
			sb.append(" from ASP_HEADER ASP JOIN ASP_DETAIL AD ON ASP.ASP_ID = AD.ASP_ID");
			sb.append(" JOIN SAPRODGRP SG ON AD.ASP_BRAND_ID = SG.SA_PROD_GROUP");
			sb.append(" WHERE ASP.ASP_ID = :aspId");
			Query query = entityManager.createNativeQuery(sb.toString(), Tuple.class);
			query.setParameter("aspId", aspId);
			List<Tuple> tuples = query.getResultList();
			if (tuples != null && !tuples.isEmpty()) {
				Parameter object = null;
				for (Tuple t : tuples) {
					brandsCovered=t.get("BRANDS", String.class)+" , "+temp;
					temp=brandsCovered;
				}

	
			}
	
		} finally {
			if (em != null) { em.close(); }
		}
		return brandsCovered;
	}
	
	
	@Override
	public String getAnnualPlanEnteredBrands(Long alloc_con_id) throws Exception{
		EntityManager em = null;
		String brandsCovered="";
		String temp="";
		try {
			StringBuffer sb = new StringBuffer();
			sb.append(" SELECT DISTINCT ASP.ASP_ID,AD.ASP_BRAND_ID,SG.SA_GROUP_NAME AS BRANDS");
			sb.append(" from ASP_HEADER ASP JOIN ASP_DETAIL AD ON ASP.ASP_ID = AD.ASP_ID");
			sb.append(" JOIN SAPRODGRP SG ON AD.ASP_BRAND_ID = SG.SA_PROD_GROUP");
			sb.append(" WHERE ASP.ASP_ID  in (SELECT ALLOC_GEN_ID from ALLOC_CON_DT where ALLOC_CON_ID=:alloc_con_id)");
			Query query = entityManager.createNativeQuery(sb.toString(), Tuple.class);
			query.setParameter("alloc_con_id", alloc_con_id);
			List<Tuple> tuples = query.getResultList();
			if (tuples != null && !tuples.isEmpty()) {
				Parameter object = null;
				for (Tuple t : tuples) {
					brandsCovered=t.get("BRANDS", String.class)+" , "+temp;
					temp=brandsCovered;
				}

	
			}
	
		} finally {
			if (em != null) { em.close(); }
		}
		return brandsCovered;
	}
	
	@Override
	public String getAnnualPlanEnteredPlanTypeByAspHeader(Long aspId) throws Exception{
		EntityManager em = null;
		String planType="";
		String temp="";
		try {
			StringBuffer sb = new StringBuffer();
			sb.append("SELECT DISTINCT ASP.ASP_ID,AD.ASP_PROD_TYPE,PT.SGPRMDET_DISP_NM AS PLANNING_TYPE");
			sb.append(" from ASP_HEADER ASP JOIN ASP_DETAIL AD ON ASP.ASP_ID = AD.ASP_ID");
			sb.append(" JOIN SG_d_parameters_details PT ON AD.ASP_PROD_TYPE = PT.SGPRMDET_ID");
			sb.append(" WHERE ASP.ASP_ID =:aspId");
			Query query = entityManager.createNativeQuery(sb.toString(), Tuple.class);
			query.setParameter("aspId", aspId);
			List<Tuple> tuples = query.getResultList();
			if (tuples != null && !tuples.isEmpty()) {
				Parameter object = null;
				for (Tuple t : tuples) {
					planType=t.get("PLANNING_TYPE", String.class).charAt(0)+t.get("PLANNING_TYPE", String.class).substring(t.get("PLANNING_TYPE", String.class).length()-2)+" , "+temp;
					temp=planType;
				}
			}
	
		} finally {
			if (em != null) { em.close(); }
		}
		return planType;
	}
	
	@Override
	public String getAnnualPlanEnteredPlanType(Long alloc_con_id) throws Exception{
		EntityManager em = null;
		String planType="";
		String temp="";
		try {
			StringBuffer sb = new StringBuffer();
			sb.append("SELECT DISTINCT ASP.ASP_ID,AD.ASP_PROD_TYPE,PT.SGPRMDET_DISP_NM AS PLANNING_TYPE");
			sb.append(" from ASP_HEADER ASP JOIN ASP_DETAIL AD ON ASP.ASP_ID = AD.ASP_ID");
			sb.append(" JOIN SG_d_parameters_details PT ON AD.ASP_PROD_TYPE = PT.SGPRMDET_ID");
			sb.append(" WHERE ASP.ASP_ID in (SELECT ALLOC_GEN_ID from ALLOC_CON_DT where ALLOC_CON_ID=:alloc_con_id)");
			Query query = entityManager.createNativeQuery(sb.toString(), Tuple.class);
			query.setParameter("alloc_con_id", alloc_con_id);
			List<Tuple> tuples = query.getResultList();
			if (tuples != null && !tuples.isEmpty()) {
				Parameter object = null;
				for (Tuple t : tuples) {
					planType=t.get("PLANNING_TYPE", String.class)+" , "+temp;
					temp=planType;
				}
			}
	
		} finally {
			if (em != null) { em.close(); }
		}
		return planType;
	}
	
	@Override
	public AllocConHd getAllocConHdForAnnualPlan(Long divId,String fin_year,String allocConType){
		EntityManager em = null;
		List<AllocConHd>  list=null;
		try {
			em = emf.createEntityManager();
			CriteriaBuilder builder = em.getCriteriaBuilder();
			CriteriaQuery<AllocConHd> criteriaQuery = builder.createQuery(AllocConHd.class);
			Root<AllocConHd> root = criteriaQuery.from(AllocConHd.class);
			List<Predicate> subqueryPerdicates = new ArrayList<>();
			subqueryPerdicates.add(builder.equal(root.get(AllocConHd_.alloc_con_type), allocConType));
			subqueryPerdicates.add(builder.equal(root.get(AllocConHd_.fin_year),fin_year));
			subqueryPerdicates.add(builder.equal(root.get(AllocConHd_.status), 'A'));
			if(allocConType.equals(ANNUAL_TEAM_DOC_TYPE)) {
				subqueryPerdicates.add(builder.equal(root.get(AllocConHd_.div_id), divId));
			}
			else if(allocConType.equals(ANNUAL_COMPANY_DOC_TYPE)) {
				
			}
		    criteriaQuery.select(root).where(subqueryPerdicates.toArray(new Predicate[] {}));
			list = em.createQuery(criteriaQuery).getResultList();
			if(list != null && list.size()>0) {
				return list.get(0);
			}
				
		} finally {
			if (em != null) { em.close(); }
		}
		return null;
	}
	
	@Override
	public AllocConHd getAllocConHdForMonthlyAllocation(Long divId,String fin_year,String allocConType,String allocMonth,String allocType){
		EntityManager em = null;
		List<AllocConHd>  list=null;
		System.out.println("allocConType "+allocConType);
		System.out.println("fin_year "+fin_year);
		System.out.println("allocType "+allocType);
		System.out.println("allocType "+allocType);
		System.out.println("allocMonth "+allocMonth);
		try {
			em = emf.createEntityManager();
			CriteriaBuilder builder = em.getCriteriaBuilder();
			CriteriaQuery<AllocConHd> criteriaQuery = builder.createQuery(AllocConHd.class);
			Root<AllocConHd> root = criteriaQuery.from(AllocConHd.class);
			List<Predicate> subqueryPerdicates = new ArrayList<>();
			subqueryPerdicates.add(builder.equal(root.get(AllocConHd_.alloc_con_type), allocConType));
			subqueryPerdicates.add(builder.equal(root.get(AllocConHd_.fin_year),fin_year));
			subqueryPerdicates.add(builder.equal(root.get(AllocConHd_.status), 'A'));
			subqueryPerdicates.add(builder.equal(root.get(AllocConHd_.alloc_type),allocType));
			subqueryPerdicates.add(builder.equal(root.get(AllocConHd_.alloc_month),allocMonth));
			subqueryPerdicates.add(builder.equal(root.get(AllocConHd_.div_id),divId));
		    criteriaQuery.select(root).where(subqueryPerdicates.toArray(new Predicate[] {}));
			list = em.createQuery(criteriaQuery).getResultList();
			if(list != null && list.size()>0) {
				return list.get(0);
			}
				
		} finally {
			if (em != null) { em.close(); }
		}
		return null;
	}
	@Override
	public List<Object> getConsolatedAnnualPlan(Long LocId,String year) throws Exception{
		EntityManager em = null;
		List<Object> list = null;
		try {
			StringBuffer sb = new StringBuffer();
			sb.append(" SELECT ACH.ALLOC_CON_ID,DM.DIV_DISP_NM DIVISION,ISNULL(CONVERT(VARCHAR,ACH.DATE_CONSOLIDATED,103),'NA') DATE_CONSOLIDATED,");
			sb.append(" ISNULL(ACH.DOC_NUMBER,'Not Submitted') ALLOC_DOC_NUM,");
			sb.append(" CASE WHEN ALLOC_APPR_STATUS = 'A' THEN 'APPROVED' ELSE 'PENDING' END STATUS");
			sb.append(" FROM");
			sb.append(" DIV_MASTER DM LEFT OUTER JOIN ( SELECT ACH.ALLOC_CON_ID,ACH.DIV_ID,ACH.ALLOC_CON_DOCNO DOC_NUMBER,ACH.ALLOC_CON_DATE DATE_CONSOLIDATED,");
			sb.append(" ALLOC_APPR_STATUS FROM");
			sb.append(" ALLOC_CON_HD ACH WHERE ACH.FIN_YEAR =:year  AND ACH.STATUS = 'A'");
			sb.append(" AND ACH.ALLOC_CON_TYPE = 'XNT' AND ALLOC_TYPE = 'N' ) ACH ON ACH.DIV_ID = DM.DIV_ID WHERE DM.DIV_status = 'A'");
			sb.append(" ORDER BY DM.DIV_DISP_NM");
			Query query = entityManager.createNativeQuery(sb.toString(), Tuple.class);
			query.setParameter("year", year);
			//query.setParameter("year", year);
			List<Tuple> tuples = query.getResultList();
			if (tuples != null && !tuples.isEmpty()) {
				list = new ArrayList<>();
				AllocationConsolidationBean object = null;
				for (Tuple t : tuples) {
					object = new AllocationConsolidationBean();
					object.setAllocConId(t.get("ALLOC_CON_ID", BigDecimal.class)==null?0L:Long.valueOf(t.get("ALLOC_CON_ID", BigDecimal.class).toString()));
					object.setDocumentNumber(t.get("ALLOC_DOC_NUM", String.class));
					object.setAllocationDate(t.get("DATE_CONSOLIDATED", String.class));
					object.setDivision(t.get("DIVISION", String.class));
					object.setStatus(t.get("STATUS", String.class));
					if(object.getAllocConId()!=0L) {
						object.setBrandsCovered(this.getAnnualPlanEnteredBrands(object.getAllocConId()));
					}
					list.add(object);
				}
			if(!list.isEmpty() && list.size() > 0)
				return list;
	
			}
	
		} finally {
			if (em != null) { em.close(); }
		}
		return list;
	}
	

	@Override
	public List<Object> getMonthlyAllocationEnteredManagerByDivision(Long divId,String year,String allocMonth,String allocType,String monthOfUse,String status,String compcode) throws Exception{
		EntityManager em = null;
		List<Object> list = null;
		System.out.println("Alloc "+allocType+" monthOfUse "+monthOfUse+allocMonth);
		try {
		  StringBuffer sb = new StringBuffer();
		  sb.append(" SELECT USERID,PROD_MGR,MGR_ID,ALLOC_CON_ID,ALLOC_GEN_ID,ALLOC_DOC_NUM,STATUS,DIVISION,DIV_ID,");
		  sb.append(" DATE_GENERATED");
		  sb.append(" FROM (");
		  sb.append(" SELECT SKU.USERID,CONCAT(USR.EMP_FNM,ISNULL(USR.EMP_MNM,''),USR.EMP_LNM) PROD_MGR,");
		  sb.append(" CASE WHEN AH.DIV_ID = DM.DIV_ID THEN AH.MGR_ID ELSE ' ' END MGR_ID,");
		  sb.append(" CASE WHEN AH.DIV_ID = DM.DIV_ID THEN AH.ALLOC_CON_ID  ELSE 0 END ALLOC_CON_ID,");
		  sb.append(" CASE WHEN AH.DIV_ID = DM.DIV_ID THEN AH.ALLOC_GEN_ID  ELSE 0 END ALLOC_GEN_ID,");
		  sb.append(" CASE WHEN AH.DIV_ID = DM.DIV_ID THEN AH.ALLOC_DOC_NO ELSE 'Not Submitted' END AS ALLOC_DOC_NUM,");
		  sb.append(" CASE WHEN AH.DIV_ID = DM.DIV_ID AND AH.ALLOC_APPR_STATUS='A' THEN 'APPROVED' ELSE 'PENDING' END AS STATUS,DM.DIV_ID,DM.DIV_DISP_NM DIVISION,");
		  sb.append(" CASE WHEN AH.DIV_ID = DM.DIV_ID THEN isnull(CONVERT(varchar,AH.ALLOC_GEN_DATE,105),'') ELSE ' ' END DATE_GENERATED");
		  sb.append(" FROM USER_SKU_MAP SKU ");
		  sb.append(" JOIN am_m_login_detail LD ON SKU.USERID = LD.LD_ID");
		  sb.append(" LEFT OUTER JOIN  ALLOC_GEN_HD AH ON LD.LD_EMP_CB_ID = AH.USER_ID");
		  sb.append(" AND  AH.ALLOC_TYPE=:allocType AND AH.ALLOC_MONTH=:allocMonth   AND AH.ALLOC_USE_MONTH=:alloc_use_month AND AH.FIN_YEAR=:year AND AH.status = 'A' ");
		  sb.append(" join  am_m_emp_div_access div on div.ediv_emp_id = ld.ld_emp_cb_id");
		  sb.append(" JOIN DIV_MASTER DM ON DM.DIV_ID = DIV.EDIV_DIV_ID");
		  sb.append(" JOIN  hr_m_employee USR ON LD.ld_emp_cb_id = USR.EMP_ID");
		  if(compcode.trim().equals("SER")){
			  sb.append(" WHERE  USR.emp_egrp_id = 'EG0016' AND LD.LD_EXEC_ASST_IND = 'N'");
		  }
		  if(compcode.trim().equals("PFZ")) {
			  sb.append(" WHERE  USR.emp_egrp_id = 'EG0020' AND LD.LD_EXEC_ASST_IND = 'N'");  
		  }
		  if(divId!=0)
			  sb.append(" AND DM.DIV_ID =:divId");
		  sb.append(" ) D where (D.ALLOC_CON_ID=0 or D.ALLOC_CON_ID is null)");
		  sb.append(" GROUP BY USERID,PROD_MGR,MGR_ID,ALLOC_CON_ID,ALLOC_GEN_ID,ALLOC_DOC_NUM,STATUS,DIV_ID,DIVISION,DATE_GENERATED order by D.DIVISION");
		  Query query = entityManager.createNativeQuery(sb.toString(), Tuple.class);
			query.setParameter("allocType", allocType);
			query.setParameter("alloc_use_month", monthOfUse);
			query.setParameter("allocMonth", allocMonth);
			query.setParameter("year", year);
			if(divId!=0)
				query.setParameter("divId", divId);
			List<Tuple> tuples = query.getResultList();
			if (tuples != null && !tuples.isEmpty()) {
				list = new ArrayList<>();
				AllocationConsolidationBean object = null;
				for (Tuple t : tuples) {
					object = new AllocationConsolidationBean();
					object.setAllocGenId(t.get("ALLOC_GEN_ID", Integer.class)==null?0L:Long.valueOf(t.get("ALLOC_GEN_ID", Integer.class)));
					object.setDocumentNumber(t.get("ALLOC_DOC_NUM", String.class));
					object.setAllocationDate(t.get("DATE_GENERATED", String.class));
					object.setManagerName(t.get("PROD_MGR", String.class));
					object.setDivision(t.get("DIVISION", String.class));
					object.setDivId(t.get("DIV_ID", Integer.class)==null?0L:Long.valueOf(t.get("DIV_ID", Integer.class)));
					object.setStatus(t.get("STATUS", String.class));
					object.setAllocConId(t.get("ALLOC_CON_ID", BigDecimal.class)==null?0L:Long.valueOf(t.get("ALLOC_CON_ID", BigDecimal.class).toString()));
					if(object.getAllocGenId()!=0L) {
						object.setBrandsCovered(this.getMonthlyEnteredBrands(object.getAllocGenId()));
						object.setPlanType(this.getMonthlyEnteredPlanTypeByAspHeader(object.getAllocGenId()));
					}
					list.add(object);
				}
			if(!list.isEmpty() && list.size() > 0)
				return list;
	
			}
	
		} finally {
			if (em != null) { em.close(); }
		}
		return list;
	}
	@Override
	public String getMonthlyEnteredPlanTypeByAspHeader(Long allocGenId) throws Exception{
		EntityManager em = null;
		String planType="";
		String temp="";
		try {
			StringBuffer sb = new StringBuffer();
			sb.append("select distinct PRODUCT_TYPE from ALLOC_GEN_ENT where alloc_gen_id =:allocGenId");
			Query query = entityManager.createNativeQuery(sb.toString(), Tuple.class);
			query.setParameter("allocGenId", allocGenId);
			List<Tuple> tuples = query.getResultList();
			if (tuples != null && !tuples.isEmpty()) {
				Parameter object = null;
				for (Tuple t : tuples) {
					planType=t.get("PRODUCT_TYPE", String.class).charAt(0)+t.get("PRODUCT_TYPE", String.class).substring(t.get("PRODUCT_TYPE", String.class).length()-2)+" , "+temp;
					temp=planType;
				}

	
			}
	
		} finally {
			if (em != null) { em.close(); }
		}
		return planType;
	}
	
	
	@Override
	public String getMonthlyEnteredBrands(Long allocGenId) throws Exception{
		System.out.println("alloc_con_id "+allocGenId);
		EntityManager em = null;
		String brandsCovered="";
		String temp="";
		try {
			StringBuffer sb = new StringBuffer();
			sb.append(" SELECT DISTINCT AD.ALLOC_SMP_SA_PROD_GROUP_ID,SG.SA_GROUP_NAME AS BRANDS");
			sb.append(" from Alloc_gen_hd AH JOIN Alloc_gen_ent AD ON AH.ALLOC_GEN_ID = AD.ALLOC_GEN_ID");
			sb.append(" JOIN SAPRODGRP SG ON AD.ALLOC_SMP_SA_PROD_GROUP_ID = SG.SA_PROD_GROUP");
			sb.append(" WHERE AH.ALLOC_GEN_ID =:allocGenId  and AD.ALLOC_QTY_TOTAL!=0");
			Query query = entityManager.createNativeQuery(sb.toString(), Tuple.class);
			query.setParameter("allocGenId", allocGenId);
			List<Tuple> tuples = query.getResultList();
			if (tuples != null && !tuples.isEmpty()) {
				Parameter object = null;
				for (Tuple t : tuples) {
					brandsCovered=t.get("BRANDS", String.class)+" , "+temp;
					temp=brandsCovered;
				}

	
			}
	
		} finally {
			if (em != null) { em.close(); }
		}
		return brandsCovered;
	}
	

	@Override
	public List<DivMaster> getDivisionsEnteredForMonthly(String allocMonth,String year,String allocType) throws Exception {
		EntityManager em = null;
		List<DivMaster> divList = new ArrayList<DivMaster>();
		System.out.println(allocMonth+year+allocType);
		try {
			em = emf.createEntityManager();
			StringBuffer sb = new StringBuffer();
			sb.append(" SELECT DISTINCT DM.DIV_ID, DM.DIV_DISP_NM FROM DIV_MASTER DM JOIN ALLOC_GEN_HD AH ON DM.DIV_ID = AH.DIV_ID");
			sb.append(" WHERE AH.FIN_YEAR =:year AND AH.ALLOC_MONTH =:allocMonth AND AH.ALLOC_TYPE =:allocType AND AH.ALLOC_CON_ID is null AND AH.STATUS='A' order by div_disp_nm ");
			Query query = em.createNativeQuery(sb.toString(), Tuple.class);
			query.setParameter("year", year);
			query.setParameter("allocMonth", allocMonth);
			query.setParameter("allocType", allocType);
			List<Tuple> tuples = query.getResultList();
			if (tuples != null && !tuples.isEmpty()) {
				for(Tuple t : tuples) {
					DivMaster dm=new DivMaster();
					dm.setDiv_id(Long.valueOf(t.get("div_id", Integer.class)));
					dm.setDiv_disp_nm(t.get("div_disp_nm", String.class));
					divList.add(dm);
				}
			}
		} finally {
			if (em != null) { em.close(); }
		}
	
		return divList;
	}

	@Override
	public List<DivMaster> getDivisionsEnteredForAnnualPlan(String year,String empId) throws Exception {
		EntityManager em = null;
		List<DivMaster> divList = new ArrayList<DivMaster>();
		try {
			em = emf.createEntityManager();
			StringBuffer sb = new StringBuffer();
			sb.append(" SELECT DISTINCT DM.DIV_ID, DM.DIV_DISP_NM FROM DIV_MASTER DM JOIN ASP_HEADER AH ON DM.DIV_ID = AH.ASP_DIV_ID");
			sb.append(" WHERE AH.ASP_FIN_YEAR = :year AND AH.ALLOC_CON_DOCNO_T is  null AND AH.asp_status='A' ");
			sb.append(" AND AH.ASP_DIV_ID in(select div_id from  DIV_MASTER  ");
			sb.append(" WHERE div_id in (select ediv_div_id from am_m_emp_div_access where ediv_emp_id=:empId and ediv_status='A')  AND DIV_status = 'A') order by div_disp_nm");
			Query query = em.createNativeQuery(sb.toString(), Tuple.class);
			query.setParameter("year", year);
			query.setParameter("empId", empId);
			List<Tuple> tuples = query.getResultList();
			if (tuples != null && !tuples.isEmpty()) {
				for(Tuple t : tuples) {
					DivMaster dm=new DivMaster();
					dm.setDiv_id(Long.valueOf(t.get("div_id", Integer.class)));
					dm.setDiv_disp_nm(t.get("div_disp_nm", String.class));
					divList.add(dm);
				}
			}
		} finally {
			if (em != null) { em.close(); }
		}
	
		return divList;
	}
	
//	@Override
//	public List<Object> getAnnualPlanEnteredManagerByDivision(Long divId,String year,String status) throws Exception{
//		EntityManager em = null;
//		List<Object> list = null;
//		try {
//			StringBuffer sb = new StringBuffer();
//			sb.append(" SELECT DISTINCT ASP_ID,PROD_MGR,ALLOC_DOC_NUM,DATE_GENERATED FROM (SELECT ASP.ASP_ID,ASP.ASP_FIN_YEAR,ASP.ASP_DIV_ID,ASP.ASP_status,ASP.ASP_APPR_STATUS, ");
//			sb.append(" ASP.MGR_EMP_ID,CONCAT(USR.EMP_FNM,ISNULL(USR.EMP_MNM,''),USR.EMP_LNM) PROD_MGR,ASP.ASP_ALLOC_NUMBER AS ALLOC_DOC_NUM,AD.ASP_PROD_ID, ");
//			sb.append(" CONVERT(DATE,ASP.ASP_INS_DT,105) DATE_GENERATED ");
//			sb.append(" FROM ASP_HEADER ASP JOIN ASP_DETAIL AD ON ASP.ASP_ID = AD.ASP_ID ");
//			sb.append(" JOIN HR_M_EMPLOYEE USR ON USR.EMP_ID = ASP.MGR_EMP_ID  ");
//			sb.append(" ) DT WHERE ASP_DIV_ID =:divId AND ASP_FIN_YEAR =:year and ASP_status='A' and ASP_APPR_STATUS='F' ");
//			sb.append(" ORDER BY DATE_GENERATED ");
//			Query query = entityManager.createNativeQuery(sb.toString(), Tuple.class);
//			query.setParameter("divId", divId);
//			query.setParameter("year", year);
//			List<Tuple> tuples = query.getResultList();
//			if (tuples != null && !tuples.isEmpty()) {
//				list = new ArrayList<>();
//				AllocationConsolidationBean object = null;
//				for (Tuple t : tuples) {
//					object = new AllocationConsolidationBean();
//					object.setAspId(Long.valueOf(t.get("ASP_ID", Integer.class)));
//					object.setDocumentNumber(t.get("ALLOC_DOC_NUM", String.class));
//					object.setAllocationDate(Utility.convertDatetoString(t.get("DATE_GENERATED", Date.class)));
//					object.setManagerName(t.get("PROD_MGR", String.class));
//					object.setBrandsCovered(this.getAnnualPlanEnteredBrandsByHeader(Long.valueOf(t.get("ASP_ID", Integer.class))));
//					object.setPlanType(this.getAnnualPlanEnteredPlanType(Long.valueOf(t.get("ASP_ID", Integer.class))));
//					list.add(object);
//				}
//			if(!list.isEmpty() && list.size() > 0)
//				return list;
//	
//			}
//
//		} finally {
//			if (em != null) { em.close(); }
//		}
//		return list;
//	}



}
