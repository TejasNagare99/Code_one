package com.excel.repository;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
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

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.excel.bean.AllocationBean;
import com.excel.bean.Parameter;
import com.excel.bean.PolicyHeaderBean;
import com.excel.model.AllocConHd;
import com.excel.model.AllocConHd_;
import com.excel.model.Alloc_Temp;
import com.excel.model.Alloc_Temp_Header;
import com.excel.model.Alloc_Temp_Header_;
import com.excel.model.Alloc_gen_dt;
import com.excel.model.Alloc_gen_dt_;
import com.excel.model.Alloc_gen_ent;
import com.excel.model.Alloc_gen_ent_;
import com.excel.model.Alloc_gen_hd;
import com.excel.model.Alloc_gen_hd_;
import com.excel.model.AnnualAllocationPreviousYearData;
import com.excel.model.AnnualPlanPreviousYearCount;
import com.excel.model.AspDetail;
import com.excel.model.AspDetail_;
import com.excel.model.AspHeader;
import com.excel.model.AspHeaderArc;
import com.excel.model.AspHeader_;
import com.excel.model.AspHeader_Arc_;
import com.excel.model.SG_d_parameters_details;
import com.excel.model.UserMgrAsst;
import com.excel.model.V_Dispatch_Period;
import com.excel.model.V_Dispatch_Period_;
import com.excel.utility.CodifyErrors;
import com.excel.utility.MedicoConstants;

@Repository
public class AllocationRepositoryImpl implements AllocationRepository, MedicoConstants {

	@Autowired
	EntityManagerFactory emf;
	@PersistenceContext
	private EntityManager entityManager;

	@Override
	public AspHeader getObjectAspHeader(Long headerId) throws Exception {
		return this.entityManager.find(AspHeader.class, headerId);
	}

	@Override
	public AspDetail getObjectAspDetail(Long headerId) throws Exception {
		return this.entityManager.find(AspDetail.class, headerId);
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public void deleteProductFromAnnualPlan(Long asp_detail_id) throws Exception {

		EntityManager em = null;
		try {
			StringBuffer buffer = new StringBuffer();
			buffer.append(" DELETE from  ASP_DETAIL  where ASP_DTL_ID=" + asp_detail_id);
			Query query = entityManager.createNativeQuery(buffer.toString());
			query.executeUpdate();

		} catch (Exception e) {
			System.out.println("Error Occurred :" + e.getMessage());// uncomment asneeded --;
			throw e;
		} finally {
			if (em != null) {
				em.close();
			}
		}
	}

	@Override
	public List<Object> getTeamForMonthly(String empId) throws Exception {
		EntityManager em = null;
		List<Object> list = null;
		try {
			StringBuffer sb = new StringBuffer();
			sb.append(
					"select distinct da.ediv_div_id ,dv.DIV_DISP_NM,count(TR.TERR_CODE) fm_cnt, DF.LEVEL_NAME,dv.TEAM_REQD_IND from am_m_emp_div_access da ");
			sb.append(
					", div_master dv,div_field df,(select T.TERR_DIV_ID,T.TERR_CODE from TERR_MASTER T where T.TERR_LEVEL_CODE = '001' group ");
			sb.append("by T.TERR_DIV_ID,T.TERR_CODE ) tr where da.ediv_emp_id =:empId and dv.div_status= 'A' ");
			sb.append("and da.ediv_div_id = dv.DIV_ID and da.ediv_div_id = TR.TERR_DIV_ID ");
			sb.append("and (DF.div_id=dv.div_id and DF.level_code='001'and DF.FS_CATEGORY = 'F') ");
			sb.append("group by da.ediv_div_id ,dv.DIV_DISP_NM,dv.TEAM_REQD_IND,DF.LEVEL_NAME  ");
			sb.append("UNION ALL ");
			sb.append(
					"select da.ediv_div_id ,dv.DIV_DISP_NM,count(TR.TERR_CODE) fm_cnt, DF.LEVEL_NAME,dv.TEAM_REQD_IND from ");
			sb.append(
					"am_m_emp_div_access da join ( select T.FSTAFF_DIV_ID TERR_DIV_ID,T.FSTAFF_CODE TERR_CODE from FIELDSTAFF T where T.FSTAFF_LEVEL_CODE = '001' ");
			sb.append(
					"AND T.FSTAFF_SAMP_DISP_IND = 'Y' AND T.FSTAFF_LEAVING_DATE IS NULL AND T.FSTAFF_STATUS = 'A' AND T.FS_CATEGORY = 'S' ");
			sb.append("group by T.FSTAFF_DIV_ID,T.FSTAFF_CODE ) tr on da.ediv_div_id = TR.TERR_DIV_ID ");
			sb.append(", div_master dv,div_field df ");
			sb.append("where da.ediv_emp_id =:empId ");
			sb.append("and dv.div_status= 'A' and da.ediv_div_id = dv.DIV_ID ");
			sb.append(
					"and (DF.div_id=dv.div_id and DF.level_code='001'and DF.FS_CATEGORY = 'S' AND DF.LEVEL_NAME = 'DISTRIBUTION') group by ");
			sb.append("da.ediv_div_id ,dv.DIV_DISP_NM,dv.TEAM_REQD_IND,DF.LEVEL_NAME ");
			sb.append("Order by dv.DIV_DISP_NM ");
			Query query = entityManager.createNativeQuery(sb.toString(), Tuple.class);
			query.setParameter("empId", empId);
			List<Tuple> tuples = query.getResultList();
			if (tuples != null && !tuples.isEmpty()) {
				list = new ArrayList<>();
				AllocationBean object = null;
				for (Tuple t : tuples) {
					object = new AllocationBean();
					object.setEdiv_div_id(Long.valueOf(t.get("ediv_div_id", Integer.class)));
					object.setDiv_disp_nm(t.get("DIV_DISP_NM", String.class));
					object.setFm_cnt(Long.valueOf(t.get("fm_cnt", Integer.class)));
					object.setLevelName(t.get("LEVEL_NAME", String.class));
					object.setTeam_req_ind(t.get("TEAM_REQD_IND", Character.class).toString());
					list.add(object);
				}
				System.out.println("getTeamWithCount" + list.size());
				if (!list.isEmpty() && list.size() > 0)
					return list;
			}

		} finally {
			if (em != null) {
				em.close();
			}
		}
		return list;
	}

	@Override
	public List<Object> getTeamWithCount(String empId) throws Exception {
		EntityManager em = null;
		List<Object> list = null;
		try {
			StringBuffer sb = new StringBuffer();
			sb.append(
					" select da.ediv_div_id ,dv.DIV_DISP_NM,count(TR.TERR_CODE) fm_cnt, DF.LEVEL_NAME,dv.TEAM_REQD_IND");
			sb.append(
					" from am_m_emp_div_access da , div_master dv,div_field df,(select T.TERR_DIV_ID,T.TERR_CODE from TERR_MASTER T");
			sb.append(" where T.TERR_LEVEL_CODE = '001'");
			sb.append(" group by T.TERR_DIV_ID,T.TERR_CODE");
			sb.append(" ) tr");
			sb.append(
					" where da.ediv_emp_id =:empId and dv.div_status='A' and da.ediv_div_id = dv.DIV_ID and da.ediv_div_id = TR.TERR_DIV_ID");// and
																																				// da.ediv_div_id
																																				// =
																																				// TR.TERR_DIV_ID
			sb.append(" and (DF.div_id=dv.div_id and DF.level_code='001'and DF.FS_CATEGORY = 'F')");
			sb.append(
					" group by da.ediv_div_id ,dv.DIV_DISP_NM ,DF.LEVEL_NAME,dv.TEAM_REQD_IND Order by dv.DIV_DISP_NM");
			Query query = entityManager.createNativeQuery(sb.toString(), Tuple.class);
			query.setParameter("empId", empId);
			List<Tuple> tuples = query.getResultList();
			if (tuples != null && !tuples.isEmpty()) {
				list = new ArrayList<>();
				AllocationBean object = null;
				for (Tuple t : tuples) {
					object = new AllocationBean();
					object.setEdiv_div_id(Long.valueOf(t.get("ediv_div_id", Integer.class)));
					object.setDiv_disp_nm(t.get("DIV_DISP_NM", String.class));
					object.setFm_cnt(Long.valueOf(t.get("fm_cnt", Integer.class)));
					object.setLevelName(t.get("LEVEL_NAME", String.class));
					object.setTeam_req_ind(t.get("TEAM_REQD_IND", Character.class).toString());
					list.add(object);
				}
				System.out.println("getTeamWithCount" + list.size());
				if (!list.isEmpty() && list.size() > 0)
					return list;
			}

		} finally {
			if (em != null) {
				em.close();
			}
		}
		return list;
	}

	@Override
	public List<AllocationBean> getTeamWithPriviousYearCount(String empId, String divId, String year) throws Exception {
		EntityManager em = null;
		List<AllocationBean> list = null;
		try {
			StringBuffer sb = new StringBuffer();
			sb.append(" select da.ediv_div_id ,dv.DIV_DISP_NM,count(TR.FSTAFF_TERR_CODE) fm_cnt,");
			sb.append(" DF.LEVEL_NAME from am_m_emp_div_access da , div_master dv,div_field df,");
			sb.append(
					" (select T.FSTAFF_DIV_ID,T.FSTAFF_TERR_CODE from FIELDSTAFF_ARC T where T.FSTAFF_LEVEL_CODE = '001' AND FSTAFF_SAMP_DISP_IND='Y'");
			sb.append(" AND FSTAFF_LEAVING_DATE IS NULL");
			sb.append(" AND FIN_YEAR=:year");
			sb.append(" group by T.FSTAFF_DIV_ID,T.FSTAFF_TERR_CODE ) tr");
			sb.append(" where da.ediv_emp_id =:empId");
			sb.append(" and dv.div_status='A' and da.ediv_div_id = dv.DIV_ID ");
			sb.append(" and da.ediv_div_id = TR.FSTAFF_DIV_ID and (DF.div_id=dv.div_id");
			sb.append(" and DF.level_code='001'and DF.FS_CATEGORY = 'F') and da.ediv_div_id=:divId");
			sb.append(" group by da.ediv_div_id ,dv.DIV_DISP_NM ,DF.LEVEL_NAME");
			sb.append(" Order by dv.DIV_DISP_NM");
			Query query = entityManager.createNativeQuery(sb.toString(), Tuple.class);
			query.setParameter("empId", empId);
			query.setParameter("year", Integer.valueOf(year) - 1);
			query.setParameter("divId", divId);
			List<Tuple> tuples = query.getResultList();
			if (tuples != null && !tuples.isEmpty()) {
				list = new ArrayList<>();
				AllocationBean object = null;
				for (Tuple t : tuples) {
					object = new AllocationBean();
					object.setEdiv_div_id(Long.valueOf(t.get("ediv_div_id", Integer.class)));
					object.setDiv_disp_nm(t.get("DIV_DISP_NM", String.class));
					object.setFm_cnt(Long.valueOf(t.get("fm_cnt", Integer.class)));
					object.setLevelName(t.get("LEVEL_NAME", String.class));
					object.setActual_team_size(Long.valueOf(t.get("fm_cnt", Integer.class)));
					list.add(object);
				}
				System.out.println("getTeamWithCount" + list.size());
				if (!list.isEmpty() && list.size() > 0)
					return list;
			}

		} finally {
			if (em != null) {
				em.close();
			}
		}
		return list;
	}

	@Override
	public List<Object> getBrandsForTeam(String empId, String teamId, String planType, String subTeamCode)
			throws Exception {
		EntityManager em = null;
		List<Object> list = null;
		Query query = null;
		System.out
				.println("EMPID" + empId + " teamId" + teamId + " planType" + planType + "subTeamCode :" + subTeamCode);
		try {
			StringBuffer sb = new StringBuffer();
			if (!subTeamCode.equals("0")) {
				sb.append("  select us.SMP_SA_PROD_GROUP,sg.SA_GROUP_NAME  ,DTB.TEAM_ID,TM.TEAM_CODE");
				sb.append(
						" from am_m_login_detail lg , USER_SKU_MAP us , SAPRODGRP sg , SMPITEM P ,DIV_TEAM_BRAND DTB, TEAM TM");
				sb.append(" where lg.ld_emp_cb_id =:empId");
				sb.append(" and p.PROD_SUB_TYPE_ID=:prod_sub_type_id");
				sb.append(" and lg.ld_id = us.USERID");
				sb.append(" and us.SMP_SA_PROD_GROUP = sg.SA_PROD_GROUP");
				sb.append(" AND us.SMP_SA_PROD_GROUP = P.SMP_SA_PROD_GROUP");
				sb.append(
						" AND TM.DIV_ID=:teamId AND  DTB.SA_PROD_GROUP=SG.SA_PROD_GROUP AND TM.TEAM_ID=DTB.TEAM_ID AND TM.TEAM_CODE=:subTeamCode  ");
				sb.append(
						" AND (P.SMP_STD_DIV_ID IN (select prod_div_id from divmap where base_div_id=:teamId ) OR P.SMP_STD_DIV_ID=:teamId)");
				sb.append(" group by us.SMP_SA_PROD_GROUP,sg.SA_GROUP_NAME,DTB.TEAM_ID,TM.TEAM_CODE");
				sb.append(" order by TM.TEAM_CODE,sg.SA_GROUP_NAME");
				query = entityManager.createNativeQuery(sb.toString(), Tuple.class);
				query.setParameter("empId", empId);
				query.setParameter("teamId", teamId);
				query.setParameter("prod_sub_type_id", planType);
				query.setParameter("subTeamCode", subTeamCode);
			} else {
				sb.append(" select us.SMP_SA_PROD_GROUP,sg.SA_GROUP_NAME ");
				sb.append(" from am_m_login_detail lg , USER_SKU_MAP us , SAPRODGRP sg , SMPITEM P ");
				sb.append(
						" where lg.ld_emp_cb_id =:empId and p.PROD_SUB_TYPE_ID=:prod_sub_type_id and lg.ld_id = us.USERID ");
				sb.append(
						" and us.SMP_SA_PROD_GROUP = sg.SA_PROD_GROUP AND us.SMP_SA_PROD_GROUP = P.SMP_SA_PROD_GROUP ");
				sb.append(
						" AND (P.SMP_STD_DIV_ID IN (select prod_div_id from divmap where base_div_id=:teamId ) OR P.SMP_STD_DIV_ID=:teamId) group by us.SMP_SA_PROD_GROUP,sg.SA_GROUP_NAME order by sg.SA_GROUP_NAME");
				query = entityManager.createNativeQuery(sb.toString(), Tuple.class);
				query.setParameter("empId", empId);
				query.setParameter("teamId", teamId);
				query.setParameter("prod_sub_type_id", planType);
			}
			List<Tuple> tuples = query.getResultList();
			if (tuples != null && !tuples.isEmpty()) {
				list = new ArrayList<>();
				AllocationBean object = null;
				for (Tuple t : tuples) {
					object = new AllocationBean();
					object.setSmp_sa_prod_group(Long.valueOf(t.get("SMP_SA_PROD_GROUP", Integer.class)));
					object.setSa_group_name(t.get("SA_GROUP_NAME", String.class));
					list.add(object);
				}
				if (!list.isEmpty() && list.size() > 0)
					return list;
			}

		} catch (Exception e) {
			System.out.println("Error Occurred :" + e.getMessage());// uncomment asneeded --;
		} finally {
			if (em != null) {
				em.close();
			}
		}
		return list;
	}

	@Override
	public List<Object> getPlanningType(String empId) throws Exception {
		EntityManager em = null;
		List<Object> list = null;
		try {
			StringBuffer sb = new StringBuffer();
			sb.append("select sgprmdet_id,sgprmdet_disp_nm from sg_d_parameters_details   ");
			sb.append("join SG_M_PARAMETERS sgm on sgm.sgprm_id=sgprmdet_sgprm_id ");
			sb.append("where upper(sgprm_disp_nm)='PRODUCT_SUB_TYPE' ");
			// sb.append("AND sgprmdet_id IN ( 1503,1504 ) ");
			sb.append("and sgprmdet_sgprm_id=sgm.sgprm_id ");
			sb.append("order by sgprmdet_id ");
			Query query = entityManager.createNativeQuery(sb.toString(), Tuple.class);
			List<Tuple> tuples = query.getResultList();
			if (tuples != null && !tuples.isEmpty()) {
				list = new ArrayList<>();
				AllocationBean object = null;
				for (Tuple t : tuples) {
					object = new AllocationBean();
					object.setSmp_prod_type_id(Long.valueOf(t.get("sgprmdet_id", Integer.class)));
					object.setSgprmdet_disp_nm(t.get("sgprmdet_disp_nm", String.class));
					list.add(object);
				}
				System.out.println("getSampleProductType" + list.size());
				if (!list.isEmpty() && list.size() > 0)
					return list;

			}

		} finally {
			if (em != null) {
				em.close();
			}
		}
		return list;
	}

	@Override
	public List<Object> getSampleProductList(String divId, List<String> prodGroup, String prodType) throws Exception {
		EntityManager em = null;
		List<Object> list = null;
		try {
			StringBuffer sb = new StringBuffer();
			sb.append(" select smp_prod_id,SMP_PROD_NAME from smpitem where SMP_SA_PROD_GROUP in(:smp_sa_prod_group) ");
			sb.append(" AND SMP_STATUS='A' AND PROD_SUB_TYPE_ID=:prod_sub_type_id");
			sb.append(" order by smp_prod_name ");
			Query query = entityManager.createNativeQuery(sb.toString(), Tuple.class);
			query.setParameter("smp_sa_prod_group", prodGroup);
			query.setParameter("prod_sub_type_id", prodType);
			List<Tuple> tuples = query.getResultList();
			if (tuples != null && !tuples.isEmpty()) {
				list = new ArrayList<>();
				AllocationBean object = null;
				for (Tuple t : tuples) {
					object = new AllocationBean();
					object.setSmp_prod_id(Long.valueOf(t.get("smp_prod_id", Integer.class)));
					object.setSmp_prod_name(t.get("SMP_PROD_NAME", String.class));
					list.add(object);
				}
				System.out.println("Supplier" + list.size());
				if (!list.isEmpty() && list.size() > 0)
					return list;

			}

		} finally {
			if (em != null) {
				em.close();
			}
		}
		return list;
	}

	@Override
	public List<Object> getSampleProductListForAnnualPlan(String divId, List<String> prodGroup, String prodType,
			String alloc_number) throws Exception {
		EntityManager em = null;
		List<Object> list = null;
		try {
			StringBuffer sb = new StringBuffer();
			sb.append(
					" select smp_prod_id,SMP_PROD_NAME from smpitem where SMP_SA_PROD_GROUP in (:smp_sa_prod_group) AND SMP_STATUS='A' ");
			sb.append(" AND PROD_SUB_TYPE_ID=:prod_sub_type_id AND SMP_SubComp_id in(1,3)");
			sb.append(" and smp_prod_id  not in ");
			sb.append(
					" (select dt.smp_prod_id from (select smp_prod_id,SMP_PROD_NAME,SMP_SA_PROD_GROUP,smp_status,PROD_SUB_TYPE_ID from smpitem si)dt ");
			sb.append(" left join asp_detail ad on ad.asp_prod_id=dt.smp_prod_id ");
			sb.append(" left join asp_header ah on ah.asp_id=ad.asp_id ");
			sb.append(" where ");
			sb.append(" SMP_SA_PROD_GROUP in (:smp_sa_prod_group) ");
			sb.append(" AND  SMP_STATUS='A' ");
			sb.append(" AND  PROD_SUB_TYPE_ID=:prod_sub_type_id ");
			sb.append(" and ah.ASP_DIV_ID = :divId) order by SMP_PROD_NAME");
			Query query = entityManager.createNativeQuery(sb.toString(), Tuple.class);
			query.setParameter("smp_sa_prod_group", prodGroup);
			query.setParameter("prod_sub_type_id", prodType);
			query.setParameter("divId", divId);
			List<Tuple> tuples = query.getResultList();
			if (tuples != null && !tuples.isEmpty()) {
				list = new ArrayList<>();
				AllocationBean object = null;
				for (Tuple t : tuples) {
					object = new AllocationBean();
					object.setSmp_prod_id(Long.valueOf(t.get("smp_prod_id", Integer.class)));
					object.setSmp_prod_name(t.get("SMP_PROD_NAME", String.class));
					list.add(object);
				}
				System.out.println("Supplier" + list.size());
				if (!list.isEmpty() && list.size() > 0)
					return list;

			}

		} finally {
			if (em != null) {
				em.close();
			}
		}
		return list;
	}

	@Override
	public List<Object> getPreviousYearPeriodWiseDetails(String prodId, String divId, String prodGroup, String prodType,
			String year) throws Exception {
		EntityManager em = null;
		List<Object> list = null;
		Long currentYear = Long.valueOf(year);
		Long previousYear = Long.valueOf(year);
		try {
			StringBuffer sb = new StringBuffer();
			sb.append(
					" SELECT P.PERIOD_NAME,DTL1.PERIOD_CODE,ISNULL(DTL1.DSPDTL_FIN_YEAR,'') DSPDTL_FIN_YEAR,ISNULL(DTL1.DSPDTL_PERIOD_CODE,'') DSPDTL_PERIOD_CODE,ISNULL(DTL1.smp_prod_id,0) smp_prod_id,ISNULL(DTL1.SMP_PROD_NAME,'') SMP_PROD_NAME,ISNULL(DTL1.DSP_QTY,0) DSP_QTY FROM ");
			sb.append(" ( SELECT PR.PERIOD_CODE,DTL.* FROM ");
			sb.append(
					" (SELECT DSPDTL_FIN_YEAR,DSPDTL_PERIOD_CODE,smp_prod_id,SMP_PROD_NAME,sum( ISNULL(d.DSPDTL_DISP_QTY,0) ) dsp_qty ");
			sb.append(" FROM SMPITEM p join ");
			sb.append(
					" (SELECT DSPDTL_FIN_YEAR,DSPDTL_PERIOD_CODE,DSPDTL_PROD_ID,DSPDTL_FSTAFF_ID,SUM(DSPDTL_DISP_QTY) DSPDTL_DISP_QTY, ");
			sb.append(
					" 0 as LASTYRPERPSO, 0 AS LASTYRTOTUNIT  FROM Dispatch_detail JOIN DISPATCH_HEADER ON DSP_ID=DSPDTL_DSP_ID WHERE DSPDTL_FIN_YEAR = 2019 AND DSPDTL_DIV_ID=:smp_std_div_id ");
			sb.append(" AND DSPDTL_STATUS='A'   AND DSP_APPR_STATUS='A' ");
			sb.append(
					" GROUP BY DSPDTL_FIN_YEAR,DSPDTL_PERIOD_CODE,DSPDTL_PROD_ID,DSPDTL_FSTAFF_ID ) d on d.DSPDTL_PROD_ID = p.SMP_PROD_ID ");
			sb.append(" where smp_prod_id=:prodId ");
			sb.append(" group by DSPDTL_FIN_YEAR,DSPDTL_PERIOD_CODE,smp_prod_id,SMP_PROD_NAME ");
			sb.append(" HAVING smp_prod_id=:prodId)DTL ");
			sb.append(
					" FULL OUTER JOIN PERIOD PR ON (DSPDTL_FIN_YEAR+DSPDTL_PERIOD_CODE)=(PERIOD_FIN_YEAR+PERIOD_CODE) ");
			sb.append(" WHERE PR.PERIOD_FIN_YEAR='2019')DTL1 ");
			sb.append(" JOIN PERIOD P ON P.PERIOD_FIN_YEAR IN ('2020','2021') AND P.PERIOD_CODE=DTL1.PERIOD_CODE ");
			sb.append(" ORDER BY P.PERIOD_FIN_YEAR,P.PERIOD_CODE ");
			Query query = entityManager.createNativeQuery(sb.toString(), Tuple.class);
			query.setParameter("prodId", prodId);
			query.setParameter("smp_std_div_id", divId);
			// query.setParameter("smp_sa_prod_group", prodGroup);
			// query.setParameter("smp_prod_type_id", prodType);
			List<Tuple> tuples = query.getResultList();
			System.out.println("Okkkkkkkkkkkkkkkk");
			if (tuples != null && !tuples.isEmpty()) {
				list = new ArrayList<>();
				AllocationBean object = null;
				BigDecimal tempDsp = BigDecimal.ZERO;
				for (Tuple t : tuples) {
					object = new AllocationBean();
					object.setPeriod_name(t.get("PERIOD_NAME", String.class));
					object.setDspdtl_fin_year(t.get("DSPDTL_FIN_YEAR", String.class));
					object.setSmp_prod_id(Long.valueOf(t.get("smp_prod_id", Integer.class)));
					if (t.get("DSPDTL_PERIOD_CODE", String.class) != "")
						object.setDspdtl_period_code(t.get("DSPDTL_PERIOD_CODE", String.class));
					object.setSmp_prod_id(Long.valueOf(t.get("smp_prod_id", Integer.class)));
					if (t.get("SMP_PROD_NAME", String.class) != "")
						object.setSmp_prod_name(t.get("SMP_PROD_NAME", String.class));
					object.setDsp_qty(t.get("dsp_qty", BigDecimal.class));
					object.setCommutibeDispatch(object.getDsp_qty().add(tempDsp));
					list.add(object);

					tempDsp = object.getCommutibeDispatch();
				}
				System.out.println("Supplier" + list.size());
				if (!list.isEmpty() && list.size() > 0)
					return list;

			}

		} finally {
			if (em != null) {
				em.close();
			}
		}
		return list;
	}

	@Override
	public List<AllocationBean> getProductDetails(Long prod_id, String year) {
		EntityManager em = null;
		List<AllocationBean> list = null;
		try {
			StringBuffer sb = new StringBuffer();
			sb.append(
					" SELECT CONVERT(INT,GETDATE()-SMP_LAUNCH_DT)/365 YEARS,SGprmdet_disp_nm SUPPLY_TYPE,SMP_COG_RATE,SMP_PROD_CD,SMP_SA_PROD_GROUP");
			sb.append(" FROM SMPITEM, SG_d_parameters_details  WHERE SMP_PROD_ID=:prodId");
			sb.append(" AND SMP_SUPPLY_TYPE_ID=SGPRMDET_ID ");
			Query query = entityManager.createNativeQuery(sb.toString(), Tuple.class);
			query.setParameter("prodId", prod_id);
			List<Tuple> tuples = query.getResultList();
			if (tuples != null && !tuples.isEmpty()) {
				list = new ArrayList<>();
				AllocationBean object = null;
				for (Tuple t : tuples) {
					object = new AllocationBean();
					if (t.get("YEARS", Integer.class) != null)
						object.setPresentInIndia(Long.valueOf(t.get("YEARS", Integer.class)));
					else
						object.setPresentInIndia(Long.valueOf(0L));
					object.setSupplyType(t.get("SUPPLY_TYPE", String.class));
					object.setSmp_cog_rate(t.get("SMP_COG_RATE", BigDecimal.class).setScale(2));
					object.setSmp_erp_prod_cd(t.get("SMP_PROD_CD", String.class));
					object.setSmp_sa_prod_group(Long.valueOf(t.get("SMP_SA_PROD_GROUP", Integer.class)));
					list.add(object);
				}
				System.out.println("getProductDetails " + list.size());
				if (!list.isEmpty() && list.size() > 0)
					return list;

			}

		} finally {
			if (em != null) {
				em.close();
			}
		}
		return list;
	}

	@Override
	public List<AllocationBean> getPreviousYearCount(Long prod_id, Long divId, String year) {
		EntityManager em = null;
		List<AllocationBean> list = null;
		try {
			StringBuffer sb = new StringBuffer();
			sb.append(
					" SELECT  DSPDTL_PROD_ID,smp_erp_prod_cd, SUM(DSPDTL_DISP_QTY) PRVYRDSP_UNIT, COUNT( distinct DSPDTL_FSTAFF_ID) PRVYRDSPCNT, ");
			sb.append(
					" CONVERT(NUMERIC(10,2),SUM(DSPDTL_DISP_QTY) / COUNT( distinct DSPDTL_FSTAFF_ID)) PRVYRDSP_PERPSO ");
			sb.append(
					" FROM ( SELECT DSPDTL_DSP_ID,DSPDTL_FSTAFF_ID,DSPDTL_PROD_ID,DSPDTL_DISP_QTY FROM DISPATCH_DETAIL ");
			sb.append(" JOIN DISPATCH_HEADER ON DSP_ID=DSPDTL_DSP_ID ");
			sb.append(
					" WHERE DSPDTL_FIN_YEAR=:year AND  DSPDTL_STATUS='A' AND DSP_APPR_STATUS='A' AND  DSPDTL_PROD_ID=:prodId AND DSPDTL_DIV_ID=:smp_std_div_id ");
			sb.append(
					" )DSPDTL	JOIN DISPATCH_HEADER ON DSP_ID=DSPDTL.DSPDTL_DSP_ID join smpitem on smp_prod_id=dspdtl_prod_id ");
			sb.append(" WHERE DSP_APPR_STATUS='A' GROUP BY DSPDTL.DSPDTL_PROD_ID,smp_erp_prod_cd  ");

			Query query = entityManager.createNativeQuery(sb.toString(), Tuple.class);
			query.setParameter("prodId", prod_id);
			query.setParameter("smp_std_div_id", divId);
			query.setParameter("year", Long.valueOf(year) - 1);
			List<Tuple> tuples = query.getResultList();
			if (tuples != null && !tuples.isEmpty()) {
				list = new ArrayList<>();
				AllocationBean object = null;
				for (Tuple t : tuples) {
					object = new AllocationBean();
					object.setSmp_erp_prod_cd(t.get("smp_erp_prod_cd", String.class));
					object.setPrvyrdsp_unit(t.get("PRVYRDSP_UNIT", BigDecimal.class));
					object.setPrvyrdspcnt(Long.valueOf(t.get("PRVYRDSPCNT", Integer.class)));
					object.setPrvdsp_perpso((t.get("PRVYRDSP_PERPSO", BigDecimal.class)));
					list.add(object);
				}
				if (!list.isEmpty() && list.size() > 0)
					return list;

			}

		} finally {
			if (em != null) {
				em.close();
			}
		}
		return list;
	}

	@Override
	public List<AllocationBean> getSummaryDetailsAnnualPlan(Long aspId, String year, String status) throws Exception {
		EntityManager em = null;
		List<AllocationBean> list = null;
		try {
			StringBuffer sb = new StringBuffer();

			sb.append(" select SA_GROUP_NAME,VALUE_INR,UNITS,");
			sb.append(" CASE WHEN FREQUENCY='M' THEN CONVERT(NUMERIC(12,2),VALUE_INR/12)");
			sb.append(" WHEN FREQUENCY='B' THEN CONVERT(NUMERIC(12,2),VALUE_INR/6)");
			sb.append(" WHEN FREQUENCY='Q' THEN CONVERT(NUMERIC(12,2),VALUE_INR/4) END AVG_MTH,");
			sb.append(" CASE WHEN FREQUENCY='M' THEN 'Monthly'");
			sb.append(" WHEN FREQUENCY='B' THEN 'BI-Monthly'");
			sb.append(" WHEN FREQUENCY='Q' THEN 'Quarterly' END FREQUENCY,");
			sb.append(" ASP_ALLOC_NUMBER,ASP_FIN_YEAR,MGR_EMP_ID");
			sb.append(" from (");
			sb.append(" select sgr.SA_GROUP_NAME,");
			sb.append(
					" sum((ASPDT.ASP_PRDQTY01+ASPDT.ASP_PRDQTY02+ASPDT.ASP_PRDQTY03+ASPDT.ASP_PRDQTY04+ASPDT.ASP_PRDQTY05+ASPDT.ASP_PRDQTY06+  ASPDT.ASP_PRDQTY07+ASPDT.ASP_PRDQTY08+ASPDT.ASP_PRDQTY09+ASPDT.ASP_PRDQTY10+ASPDT.ASP_PRDQTY11+ASPDT.ASP_PRDQTY12)*(ASPDT.ASP_TEAM_SIZE)) UNITS,");
			sb.append(
					" sum((ASPDT.ASP_PRDQTY01+ASPDT.ASP_PRDQTY02+ASPDT.ASP_PRDQTY03+ASPDT.ASP_PRDQTY04+ASPDT.ASP_PRDQTY05+ASPDT.ASP_PRDQTY06+  ASPDT.ASP_PRDQTY07+ASPDT.ASP_PRDQTY08+ASPDT.ASP_PRDQTY09+ASPDT.ASP_PRDQTY10+ASPDT.ASP_PRDQTY11+ASPDT.ASP_PRDQTY12)*(ASPDT.ASP_COG)*(ASPDT.ASP_TEAM_SIZE)) VALUE_INR,");
			sb.append(
					" ASPDT.ASP_FREQUENCY AS Frequency,ASPHD.ASP_ALLOC_NUMBER,ASPHD.ASP_FIN_YEAR,ASPHD.MGR_EMP_ID   FROM ASP_HEADER ASPHD");
			sb.append(" JOIN ASP_DETAIL ASPDT ON ASPHD.ASP_ID=ASPDT.ASP_ID");
			sb.append(" JOIN SMPITEM SI ON SI.SMP_PROD_ID = ASPDT.ASP_PROD_ID");
			sb.append(" JOIN SAPRODGRP SGR ON SGR.SA_PROD_GROUP = SI.SMP_SA_PROD_GROUP");
			sb.append(" WHERE asphd.asp_fin_year=:year AND asphd.ASP_STATUS ='A' AND asphd.ASP_ID=:aspId");
			sb.append(
					" GROUP BY sgr.SA_GROUP_NAME,ASPHD.ASP_FIN_YEAR ,ASPDT.ASP_FREQUENCY,ASPHD.ASP_ALLOC_NUMBER,ASPDT.ASP_TEAM_SIZE,ASPHD.MGR_EMP_ID");
			sb.append(" ) dtl");
			sb.append(" ORDER BY SA_GROUP_NAME,ASP_FIN_YEAR,ASP_ALLOC_NUMBER;");
			Query query = entityManager.createNativeQuery(sb.toString(), Tuple.class);
			query.setParameter("year", year);
			query.setParameter("aspId", aspId);
			List<Tuple> tuples = query.getResultList();
			if (tuples != null && !tuples.isEmpty()) {
				list = new ArrayList<>();
				AllocationBean object = null;
				for (Tuple t : tuples) {
					object = new AllocationBean();
					object.setSa_group_name(t.get("SA_GROUP_NAME", String.class));
					// object.setTotalCost(t.get("VALUE_INR",
					// BigDecimal.class).multiply(BigDecimal.valueOf(Long.valueOf(t.get("AVG_MTH",
					// Integer.class))).multiply(BigDecimal.valueOf(12L))));
					object.setTotalCost(t.get("VALUE_INR", BigDecimal.class));
					object.setTotalUnits(Long.valueOf(t.get("UNITS", Integer.class)));
					object.setAvgMonth(t.get("AVG_MTH", BigDecimal.class));
					object.setFrequency(t.get("FREQUENCY", String.class));
					object.setAsp_alloc_number(t.get("ASP_ALLOC_NUMBER", String.class));
					object.setAsp_fin_year(t.get("ASP_FIN_YEAR", String.class));
					object.setMgrEmpId(t.get("MGR_EMP_ID", String.class));
					list.add(object);
				}
				if (!list.isEmpty() && list.size() > 0)
					return list;

			}

		} finally {
			if (em != null) {
				em.close();
			}
		}
		return list;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<AnnualAllocationPreviousYearData> getPreviousYearDataAnnualPlan(String divId, String prodId,
			String year) throws Exception {
		List<AnnualAllocationPreviousYearData> data = null;
		System.out.println("divId " + divId);
		System.out.println("prodId " + prodId);
		System.out.println("year " + year);
		EntityManager em = null;
		try {
			System.out.println("year " + year);
			em = emf.createEntityManager();
			StoredProcedureQuery storedprocquery = em
					.createNamedStoredProcedureQuery("callANNUAL_PLAN_ENTRY_PRV_YEAR_DATA");
			storedprocquery.setParameter("DIVID", divId);
			storedprocquery.setParameter("PRODID", prodId);
			storedprocquery.setParameter("PYEAR", year);
			data = storedprocquery.getResultList();
		} finally {
			if (em != null) {
				em.close();
			}
		}
		return data;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<AnnualPlanPreviousYearCount> getPreviousYearCountProcedure(Long divId, Long prodId, String year)
			throws Exception {
		List<AnnualPlanPreviousYearCount> data = null;
		EntityManager em = null;
		try {
			em = emf.createEntityManager();
			StoredProcedureQuery storedprocquery = em.createNamedStoredProcedureQuery("callANNUAL_PLAN_COUNT");
			storedprocquery.setParameter("DIVID", divId.toString());
			storedprocquery.setParameter("PRODID", prodId.toString());
			storedprocquery.setParameter("YEAR", year);
			data = storedprocquery.getResultList();

			if (data.size() > 0)
				return data;
		} finally {
			if (em != null) {
				em.close();
			}
		}
		return null;
	}

	@Override
	public boolean checkProductPresent(Long prodId, Long divId, String fin_year) {
		EntityManager em = null;
		List<AspDetail> list = null;
		System.out.println(prodId + " divId " + divId + " fin_year " + fin_year);
		try {
			em = emf.createEntityManager();
			CriteriaBuilder builder = em.getCriteriaBuilder();
			CriteriaQuery<AspDetail> criteriaQuery = builder.createQuery(AspDetail.class);
			Root<AspDetail> root = criteriaQuery.from(AspDetail.class);
			criteriaQuery.select(root).where(builder.equal(root.get(AspDetail_.asp_div_id), divId),
					builder.equal(root.get(AspDetail_.asp_dtl_fin_year), fin_year),
					builder.notEqual(root.get(AspDetail_.asp_status), "D"),
					builder.equal(root.get(AspDetail_.asp_prod_id), prodId));
			list = em.createQuery(criteriaQuery).getResultList();
			if (list != null && list.size() > 0) {
				return true;
			} else {
				return false;
			}
		} finally {
			if (em != null) {
				em.close();
			}
		}
	}

	@Override
	public AspHeader getAspHeaderByManagerAndUserId(Long mgrId, Long teamId, String fin_year, String sub_team_code) {
		EntityManager em = null;
		List<AspHeader> list = null;
		System.out.println("mgrId" + mgrId + " divId " + teamId + " fin_year " + fin_year);
		try {
			CriteriaBuilder builder = entityManager.getCriteriaBuilder();
			CriteriaQuery<AspHeader> criteriaQuery = builder.createQuery(AspHeader.class);
			Root<AspHeader> root = criteriaQuery.from(AspHeader.class);
			criteriaQuery.select(root).where(builder.equal(root.get(AspHeader_.asp_brand_mgrId), mgrId),
					builder.equal(root.get(AspHeader_.asp_fin_year), fin_year),
					builder.equal(root.get(AspHeader_.asp_div_id), teamId),
					builder.notEqual(root.get(AspHeader_.asp_status), "D"));
			list = entityManager.createQuery(criteriaQuery).getResultList();
			if (list != null && list.size() > 0) {
				return list.get(0);
			} else {
				return null;
			}
		} finally {
			if (em != null) {
				em.close();
			}
		}
	}

	@Override
	public AspHeaderArc getPreviousYearSamplePlan(Long userId, Long mgrId, String fin_year) {
		EntityManager em = null;
		List<AspHeaderArc> list = null;
		System.out.println(userId + " divId " + userId + " fin_year " + fin_year);
		try {
			CriteriaBuilder builder = entityManager.getCriteriaBuilder();
			CriteriaQuery<AspHeaderArc> criteriaQuery = builder.createQuery(AspHeaderArc.class);
			Root<AspHeaderArc> root = criteriaQuery.from(AspHeaderArc.class);
			criteriaQuery.select(root).where(builder.equal(root.get(AspHeader_Arc_.asp_userId), userId),
					builder.equal(root.get(AspHeader_Arc_.asp_brand_mgrId), mgrId),
					builder.equal(root.get(AspHeader_Arc_.asp_fin_year), fin_year));
			list = entityManager.createQuery(criteriaQuery).getResultList();
			if (list != null && list.size() > 0) {
				return list.get(0);
			} else {
				return null;
			}
		} finally {
			if (em != null) {
				em.close();
			}
		}
	}

	@Override
	public List<AllocationBean> getManagerListForAssistant(String asst_user_id) throws Exception {
		EntityManager em = null;
		List<AllocationBean> allocBeanlist = new ArrayList<>();
		List<UserMgrAsst> list = null;
		AllocationBean alloc = null;
		System.out.println(" divId " + asst_user_id);
		try {
			em = emf.createEntityManager();
			StringBuffer sb = new StringBuffer();
			sb.append(
					" select assist.mgr_user_id,assist.mgr_ld_emp_cb_id,RTRIM(HR.EMP_FNM)+' '+RTRIM(HR.emp_lnm) MANAGER_NAME ");
			sb.append(
					" from USER_MGR_ASST assist join hr_m_employee HR on  hr.emp_id=assist.MGR_LD_EMP_CB_ID where assist.asst_ld_emp_cb_id=:assist_id");
			Query query = em.createNativeQuery(sb.toString(), Tuple.class);
			query.setParameter("assist_id", asst_user_id);
			List<Tuple> tuples = query.getResultList();
			if (tuples != null && !tuples.isEmpty()) {
				list = new ArrayList<>();
				AllocationBean object = null;
				for (Tuple t : tuples) {
					alloc = new AllocationBean();
					alloc.setMgr_id(Long.valueOf(t.get("mgr_user_id", Integer.class)));
					alloc.setMgrEmpId(t.get("mgr_ld_emp_cb_id", String.class));
					alloc.setMgr_name(t.get("MANAGER_NAME", String.class) + "-" + alloc.getMgrEmpId());
					allocBeanlist.add(alloc);

				}
				System.out.println("getSampleProductType" + list.size());
				if (!allocBeanlist.isEmpty() && allocBeanlist.size() > 0)
					return allocBeanlist;

			}
		} finally {
			if (em != null) {
				em.close();
			}
		}
		return null;
	}

	@Override
	public List<AllocationBean> getAnnualPlanEnteredProductDetail(Long divId, Long brandId, String year,
			String userId) {
		EntityManager em = null;
		List<AllocationBean> list = null;
		System.out.println("divId " + divId);
		System.out.println("brandId " + brandId);
		System.out.println("year " + year);
		System.out.println("userId " + userId);

		try {
			StringBuffer sb = new StringBuffer();
//			sb.append(" select hr.emp_id user_emp_id,AH.ASP_USERID,rtrim(hr.emp_fnm)+' '+rtrim(hr.emp_lnm) user_name,asd.asp_id,asd.ASP_DTL_ID,asd.ASP_DTL_FIN_YEAR,dv.DIV_DISP_NM,asd.ASP_TEAM_SIZE, ");
//			sb.append(" asd.ASP_DIV_ID,asd.ASP_PROD_ID,SI.SMP_SA_PROD_GROUP,SA_GROUP_NAME,SI.SMP_PROD_NAME,ASP_COG, ");
//			sb.append(" ASP_PRDQTY01,ASP_PRDQTY02,ASP_PRDQTY03,ASP_PRDQTY04,ASP_PRDQTY05,ASP_PRDQTY06, ");
//			sb.append(" ASP_PRDQTY07,ASP_PRDQTY08,ASP_PRDQTY09,ASP_PRDQTY10,ASP_PRDQTY11,ASP_PRDQTY12,asd.asp_appr_status ");
//			sb.append(" from asp_detail asd join asp_header ah on ah.asp_id=asd.asp_id ");
//			sb.append(" join  smpitem si on si.smp_prod_id=asd.asp_prod_id ");
//			sb.append(" join div_master dv on dv.div_id=asd.asp_div_id ");
//			sb.append(" join saprodgrp sgp on sgp.SA_PROD_GROUP=si.SMP_SA_PROD_GROUP ");
//			sb.append(" join am_m_login_detail al on al.ld_id=ah.asp_userid ");
//			sb.append(" join hr_m_employee hr on hr.emp_id=al.ld_emp_cb_id ");
//			sb.append(" WHERE ");
//		
//			sb.append(" AH.ASP_BRAND_MGRID=:userId ");
//			sb.append(" and asd.asp_dtl_fin_year=:year ");
//			sb.append(" AND ASD.ASP_DIV_ID=:divId ");
//			sb.append(" AND ah.ASP_STATUS <>'D' ");
//			sb.append(" ORDER BY asd.asp_appr_status,SI.SMP_PROD_NAME ");

			sb.append(
					" select hr.emp_id user_emp_id,AH.ASP_USERID,rtrim(hr.emp_fnm)+' '+rtrim(hr.emp_lnm) user_name,asd.asp_id,");
			sb.append(" asd.ASP_DTL_ID,asd.ASP_DTL_FIN_YEAR,dv.DIV_DISP_NM,asd.ASP_TEAM_SIZE,  asd.ASP_DIV_ID,");
			sb.append(" asd.ASP_PROD_ID,SI.SMP_SA_PROD_GROUP,SA_GROUP_NAME,SI.SMP_PROD_NAME,ASP_COG,");
			sb.append(
					" (ASP_PRDQTY01*ASP_TEAM_SIZE) ASP_PRDQTY01 ,(ASP_PRDQTY02*ASP_TEAM_SIZE) ASP_PRDQTY02 ,(ASP_PRDQTY03*ASP_TEAM_SIZE) ASP_PRDQTY03 ,(ASP_PRDQTY04*ASP_TEAM_SIZE) ASP_PRDQTY04 ,(ASP_PRDQTY05*ASP_TEAM_SIZE) ASP_PRDQTY05 ,(ASP_PRDQTY06*ASP_TEAM_SIZE) ASP_PRDQTY06 ,");
			sb.append(
					" (ASP_PRDQTY07*ASP_TEAM_SIZE) ASP_PRDQTY07 ,(ASP_PRDQTY08*ASP_TEAM_SIZE) ASP_PRDQTY08 ,(ASP_PRDQTY09*ASP_TEAM_SIZE) ASP_PRDQTY09 ,(ASP_PRDQTY10*ASP_TEAM_SIZE) ASP_PRDQTY10 ,(ASP_PRDQTY11*ASP_TEAM_SIZE) ASP_PRDQTY11 ,(ASP_PRDQTY12*ASP_TEAM_SIZE) ASP_PRDQTY12 ,");
			sb.append(
					" asd.asp_appr_status  from asp_detail asd join asp_header ah on ah.asp_id=asd.asp_id  join  smpitem si on si.smp_prod_id=asd.asp_prod_id  join div_master dv on dv.div_id=asd.asp_div_id  join saprodgrp sgp on sgp.SA_PROD_GROUP=si.SMP_SA_PROD_GROUP  ");
			sb.append(
					" join am_m_login_detail al on al.ld_id=ah.asp_userid  join hr_m_employee hr on hr.emp_id=al.ld_emp_cb_id");
			sb.append(" WHERE  AH.ASP_BRAND_MGRID=:userId  and asd.asp_dtl_fin_year=:year  ");
			sb.append(" AND ASD.ASP_DIV_ID=:divId");
			sb.append(" AND ah.ASP_STATUS <>'D'  ORDER BY asd.asp_appr_status,SI.SMP_PROD_NAME ");

			Query query = entityManager.createNativeQuery(sb.toString(), Tuple.class);
			query.setParameter("divId", divId);
			query.setParameter("year", year);
			query.setParameter("userId", userId);
			List<Tuple> tuples = query.getResultList();
			if (tuples != null && !tuples.isEmpty()) {
				list = new ArrayList<>();
				AllocationBean object = null;
				for (Tuple t : tuples) {
					object = new AllocationBean();
					object.setSa_group_id(Long.valueOf(t.get("SMP_SA_PROD_GROUP", Integer.class)));
					object.setSa_group_name(t.get("SA_GROUP_NAME", String.class));
					object.setSmp_prod_name(t.get("SMP_PROD_NAME", String.class));
					object.setSmp_prod_id(Long.valueOf(t.get("ASP_PROD_ID", Integer.class)));
					object.setAsp_dtl_id(Long.valueOf(t.get("ASP_DTL_ID", Integer.class)));
					object.setCog(t.get("ASP_COG", BigDecimal.class));
					object.setEdiv_div_id(Long.valueOf(t.get("ASP_DIV_ID", Integer.class)));
					object.setFinYearId(t.get("ASP_DTL_FIN_YEAR", String.class).toString());
					object.setDec(Long.valueOf(t.get("ASP_PRDQTY01", Integer.class)));
					object.setJan(Long.valueOf(t.get("ASP_PRDQTY02", Integer.class)));
					object.setFeb(Long.valueOf(t.get("ASP_PRDQTY03", Integer.class)));
					object.setMarch(Long.valueOf(t.get("ASP_PRDQTY04", Integer.class)));
					object.setApril(Long.valueOf(t.get("ASP_PRDQTY05", Integer.class)));
					object.setMay(Long.valueOf(t.get("ASP_PRDQTY06", Integer.class)));
					object.setJune(Long.valueOf(t.get("ASP_PRDQTY07", Integer.class)));
					object.setJuly(Long.valueOf(t.get("ASP_PRDQTY08", Integer.class)));
					object.setAug(Long.valueOf(t.get("ASP_PRDQTY09", Integer.class)));
					object.setSept(Long.valueOf(t.get("ASP_PRDQTY10", Integer.class)));
					object.setOct(Long.valueOf(t.get("ASP_PRDQTY11", Integer.class)));
					object.setNov(Long.valueOf(t.get("ASP_PRDQTY12", Integer.class)));
					object.setTotalUnits(object.getDec() + object.getJan() + object.getFeb() + object.getMarch()
							+ object.getApril() + object.getMay() + object.getJune() + object.getJuly()
							+ object.getAug() + object.getSept() + object.getOct() + object.getNov());
					object.setTotalCost(object.getCog().multiply(BigDecimal.valueOf(object.getTotalUnits())));
					object.setStatus(t.get("asp_appr_status", Character.class).toString());
					list.add(object);
				}
				System.out.println("LIST " + list.size());
				if (!list.isEmpty() && list.size() > 0)
					return list;

			}

		} finally {
			if (em != null) {
				em.close();
			}
		}
		return list;
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public void approveAnnualAllocation(String status, Long aspId) throws Exception {
		EntityManager em = null;
		try {
			StringBuffer buffer = new StringBuffer();
			buffer.append(" UPDATE ASP_DETAIL set ASP_APPR_STATUS='" + status + "' where ASP_ID=" + aspId);
			Query query = entityManager.createNativeQuery(buffer.toString());
			query.executeUpdate();

		} catch (Exception e) {
			System.out.println("Error Occurred :" + e.getMessage());// uncomment asneeded --;
			throw e;
		} finally {
			if (em != null) {
				em.close();
			}
		}
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public void discardAnnualAllocation(String status, Long aspId) throws Exception {

		EntityManager em = null;
		try {
			StringBuffer buffer = new StringBuffer();
			buffer.append(" UPDATE ASP_DETAIL set ASP_STATUS='" + status + "',ASP_APPR_STATUS='" + status
					+ "' where ASP_ID=" + aspId);
			Query query = entityManager.createNativeQuery(buffer.toString());
			query.executeUpdate();

		} catch (Exception e) {
			System.out.println("Error Occurred :" + e.getMessage());// uncomment asneeded --;
			throw e;
		} finally {
			if (em != null) {
				em.close();
			}
		}
	}

	/* --------------------------monthly Allocation Entry----------------------- */
	@Override
	public List<Object> getPlanningTypeMonthly() throws Exception {
		EntityManager em = null;
		List<Object> list = null;
		try {
			em = emf.createEntityManager();
			StringBuffer sb = new StringBuffer();
			sb.append(" select sgprmdet_id,sgprmdet_disp_nm from sg_d_parameters_details ");
			sb.append(" join SG_M_PARAMETERS sgm on sgm.sgprm_id=sgprmdet_sgprm_id ");
			sb.append(" where sgprm_disp_nm='PRODUCT_SUB_TYPE' ");
			Query query = em.createNativeQuery(sb.toString(), Tuple.class);
			List<Tuple> tuples = query.getResultList();
			if (tuples != null && !tuples.isEmpty()) {
				list = new ArrayList<>();
				AllocationBean object = null;
				for (Tuple t : tuples) {
					object = new AllocationBean();
					object.setSgprmdet_id(Long.valueOf(t.get("sgprmdet_id", Integer.class)));
					object.setSgprmdet_disp_nm(t.get("sgprmdet_disp_nm", String.class));
					list.add(object);
				}
				System.out.println("getSampleProductType" + list.size());
				if (!list.isEmpty() && list.size() > 0)
					return list;

			}

		} finally {
			if (em != null) {
				em.close();
			}
		}
		return list;
	}

	/// Not in use
	@Override
	public List<Object> getProductListForMonthlyAllocation(String prod_sub_type_id, List<String> smp_sa_prod_group,
			String year) throws Exception {
		EntityManager em = null;
		List<Object> list = null;
		try {
			em = emf.createEntityManager();
			StringBuffer sb = new StringBuffer();
			sb.append(
					" SELECT  SMP_PROD_ID,SMP_PROD_CD,SMP_PROD_NAME,SMP_PACK_ID,PACK_DISP_NM,SMP_MIN_ALLOC_QTY,isnull(BATCH_PROD_ID,0)BATCH_PROD_ID, isnull(stock,0) stock,isnull(in_transit,0)in_transit,isnull(so_far_qty,0) so_far_qty,sa_group_name ");
			sb.append(" from ( ");
			sb.append(
					" SELECT SMP_PROD_ID,SMP_PROD_CD,SMP_PROD_NAME,SMP_PACK_ID,PACK_DISP_NM, SMP_MIN_ALLOC_QTY,sb.BATCH_PROD_ID, ");
			sb.append(
					" SUM( sb.BATCH_OPEN_STOCK+sb.BATCH_IN_STOCK-sb.BATCH_OUT_STOCK ) stock , GD.in_transit in_transit, ");
			sb.append(
					" ad.so_far_qty so_far_qty,spg.sa_group_name FROM packmaster pk , SMPITEM sm left outer join SMPBatch sb on  sm.SMP_PROD_ID=sb.batch_prod_id ");
			sb.append(
					" AND convert(date,ISNULL(BATCH_EXPIRY_DT,'2099-01-01'),105) > convert(date,getdate()+smp_short_expiry_days,105) ");
			sb.append(" left outer join ( select D.GRND_PROD_ID,sum( D.GRND_QTY ) in_transit ");
			sb.append(" from GRN_DETAILS D, GRN_HEADER H  where H.GRN_IN_TRANSIT = 'Y' and H.GRN_id = D.GRND_GRN_id ");
			sb.append(" group by D.GRND_PROD_ID ) GD on gd.GRND_PROD_ID = sm.SMP_PROD_ID ");
			sb.append(" LEFT OUTER JOIN (SELECT SUM(ALLOCDTL_CURR_ALLOC_QTY) so_far_qty, ALLOCDTL_PROD_ID ");
			sb.append(" FROM alloc_detail WHERE ALLOCDTL_PERIOD_CODE = '04' AND ALLOCDTL_FIN_YEAR  = '" + year + "' ");
			sb.append(" GROUP BY ALLOCDTL_PROD_ID) ad ON ad.ALLOCDTL_PROD_ID = sm.SMP_PROD_ID ");
			sb.append(" LEFT OUTER JOIN saprodgrp spg on spg.SA_PROD_GROUP=sm.SMP_SA_PROD_GROUP ");
			sb.append(" where sm.SMP_PACK_ID = pk.pack_id and sm.smp_status='A' ");
			sb.append(" and sm.prod_sub_type_id =:prod_sub_type_id ");
			sb.append(" and sm.SMP_SA_PROD_GROUP in(:smp_sa_prod_group) ");
			sb.append(" and (sm.SMP_STD_DIV_ID = 1 OR sm.SMP_STD_DIV_ID in ");
			sb.append(" ( select dv.PROD_DIV_ID from DIVMAP dv where dv.BASE_DIV_ID =1 and dv.CORE_IND = 'A' ) ) ");
			sb.append(
					" group by SMP_PROD_ID,SMP_PROD_CD,SMP_PROD_NAME,SMP_PACK_ID,PACK_DISP_NM,SMP_MIN_ALLOC_QTY,sb.BATCH_PROD_ID,GD.in_transit, ad.so_far_qty,spg.sa_group_name  ) dt ");
			sb.append(" where stock != 0 order by SA_GROUP_NAME,SMP_PROD_NAME");
			Query query = em.createNativeQuery(sb.toString(), Tuple.class);
			query.setParameter("prod_sub_type_id", prod_sub_type_id);
			query.setParameter("smp_sa_prod_group", smp_sa_prod_group);
			List<Tuple> tuples = query.getResultList();
			if (tuples != null && !tuples.isEmpty()) {
				list = new ArrayList<>();
				AllocationBean object = null;
				for (Tuple t : tuples) {
					object = new AllocationBean();
					object.setSmp_prod_id(Long.valueOf(t.get("SMP_PROD_ID", Integer.class)));
					object.setSmp_prod_cd(t.get("SMP_PROD_CD", String.class));
					object.setSmp_prod_name(t.get("SMP_PROD_NAME", String.class));
					object.setSmp_pack_id(Long.valueOf(t.get("SMP_PACK_ID", Integer.class)));
					object.setPack_disp_nm(t.get("PACK_DISP_NM", String.class));
					object.setSmp_min_alloc_qty(t.get("SMP_MIN_ALLOC_QTY", BigDecimal.class));
					object.setBatch_prod_id(Long.valueOf(t.get("BATCH_PROD_ID", Integer.class)));
					object.setStock(t.get("stock", BigDecimal.class));
					object.setIntransit(t.get("in_transit", BigDecimal.class));
					object.setSo_far_qty(t.get("so_far_qty", BigDecimal.class));
					object.setSa_group_name(t.get("sa_group_name", String.class));

					list.add(object);
				}
				System.out.println("Product List" + list.size());
				if (!list.isEmpty() && list.size() > 0)
					return list;

			}

		} finally {
			if (em != null) {
				em.close();
			}
		}
		return list;
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public List<Object> getProductListForMonthlyAllocation(String prodType, List<String> smp_sa_prod_group,
			String discProd, String zeroStock, String div_id, String pmtTeam, String period_code, String core,
			String fin_year, String isPrevious, String selectedPeriodCode, String selectedYear, String allocType,
			String userEmpId) throws Exception {
		System.out.println("Period_code " + period_code);
		EntityManager em = null;
		List<Object> list = null;
		System.out.println("div_id :" + div_id);
		System.out.println("smp_sa_prod_group :" + smp_sa_prod_group);
		System.out.println("isPrevious :" + isPrevious);
		System.out.println("selectedPeriodCode :" + selectedPeriodCode);
		System.out.println("selectedYear :" + selectedYear);
		System.out.println("fin_year :" + fin_year);
		System.out.println("period_code :" + period_code);
		System.out.println("zeroStock :" + zeroStock);

		try {
			em = emf.createEntityManager();
			System.out.println("SmpItemDao.getProducts() " + prodType);
			StringBuffer sb = new StringBuffer();

			sb.append(
					"  SELECT  SMP_PROD_ID,SMP_PROD_CD,SMP_PROD_NAME,SMP_PACK_ID,PACK_DISP_NM,SMP_MIN_ALLOC_QTY,isnull(BATCH_PROD_ID,0)BATCH_PROD_ID, sum(isnull(stock,0)) stock,");
			sb.append(
					"  isnull(in_transit,0)in_transit,isnull(so_far_qty,0) so_far_qty,isnull(plan_qty,0) as plan_qty, SA_GROUP_NAME,SA_PROD_GROUP  ");
			sb.append(
					"  from (  SELECT sm.SMP_PROD_ID,sm.SMP_PROD_CD,sm.SMP_PROD_NAME,sm.SMP_PACK_ID,pk.PACK_DISP_NM, sm.SMP_MIN_ALLOC_QTY,sb.BATCH_PROD_ID,");
			sb.append(
					"  ( sb.BATCH_OPEN_STOCK+sb.BATCH_IN_STOCK-sb.BATCH_OUT_STOCK - sb.BATCH_WITH_HELD_QTY ) stock , GD.in_transit in_transit, ad.so_far_qty so_far_qty,");
			sb.append("  isnull(pln.plan_qty,mnth_plan_qty) as plan_qty, spg.SA_GROUP_NAME,spg.SA_PROD_GROUP  ");
			sb.append("  FROM packmaster AS pk INNER JOIN  SMPITEM AS sm  ON pk.pack_id=sm.SMP_PACK_ID "); // changes
			sb.append(
					"  left outer join SMPBatch sb on  sm.SMP_PROD_ID=sb.batch_prod_id  AND convert(date,ISNULL(BATCH_EXPIRY_DT,'2099-01-01'),105) > convert(date,getdate()+smp_short_expiry_days,105)");
			sb.append(
					"  left outer join ( select D.GRND_PROD_ID,sum( D.GRND_QTY ) in_transit     from GRN_DETAILS D, GRN_HEADER H  where H.GRN_IN_TRANSIT = 'Y'");
			sb.append(
					"  and H.GRN_id = D.GRND_GRN_id  group by D.GRND_PROD_ID ) GD on gd.GRND_PROD_ID = sm.SMP_PROD_ID  ");
			sb.append(
					"  LEFT OUTER JOIN (SELECT SUM(ALLOCDTL_CURR_ALLOC_QTY-ALLOCDTL_SUPPLY_QTY) so_far_qty, ALLOCDTL_PROD_ID  ");
			sb.append(
					"  FROM alloc_detail WHERE ALLOCDTL_PERIOD_CODE = '" + period_code + "' AND ALLOCDTL_FIN_YEAR  = '"
							+ fin_year + "' GROUP BY ALLOCDTL_PROD_ID) ad ON ad.ALLOCDTL_PROD_ID = sm.SMP_PROD_ID");

			sb.append("  LEFT OUTER JOIN (SELECT     SUM(ASP_PRDQTY" + period_code
					+ ") AS mnth_plan_qty, ASP_PROD_ID FROM ASP_DETAIL           ");
			sb.append("  WHERE       ASP_DTL_FIN_YEAR ='" + fin_year
					+ "' GROUP BY ASP_PROD_ID) AS mnthpln ON mnthpln.ASP_PROD_ID = sm.SMP_PROD_ID  ");

			if (isPrevious == null || isPrevious.equals("N")) {
				sb.append(
						"  LEFT OUTER JOIN (select PROD_ID ASP_PROD_ID ,MAX(ALLOC_QTY_MSR) plan_qty from ALLOC_GEN_ent    ");
				sb.append("  where fin_year = '" + selectedYear + "' and period_code = '" + selectedPeriodCode
						+ "' and alloc_type = 'M'  ");
				sb.append("  and ALLOC_QTY_MSR > 0   ");
				sb.append("  group by PROD_ID    ");
				sb.append("  ) AS pln ON pln.ASP_PROD_ID = sm.SMP_PROD_ID");
			} else {
				sb.append(
						"  LEFT OUTER JOIN (select PROD_ID ASP_PROD_ID ,MAX(ALLOC_QTY_MSR) plan_qty from ALLOC_GEN_ENT_ARC ");
				sb.append("  where fin_year = '" + selectedYear + "' and period_code = '" + selectedPeriodCode
						+ "'and alloc_type = 'M' ");
				sb.append("  and ALLOC_QTY_MSR > 0   ");
				sb.append("  group by PROD_ID   ");
				sb.append("  ) AS pln ON pln.ASP_PROD_ID = sm.SMP_PROD_ID ");
			}

			sb.append("  LEFT OUTER JOIN SAPRODGRP AS spg ON spg.SA_PROD_GROUP = sm.SMP_SA_PROD_GROUP    "); // chnages
			sb.append("  where  sm.smp_status='A'   ");
			if (!prodType.equalsIgnoreCase("ALL")) {
				sb.append(" and sm.PROD_SUB_TYPE_ID = ").append(prodType.split("_")[0]).append(" ");
			}
			if (!pmtTeam.equalsIgnoreCase("0")) {
				sb.append(" AND sm.SMP_PMT_GRP_ID = ").append(pmtTeam);
			}

			if (smp_sa_prod_group != null && smp_sa_prod_group.size() > 0) {
				sb.append(" AND sm.SMP_SA_PROD_GROUP in(:smp_sa_prod_group)");
			}
			if (discProd.equalsIgnoreCase("Y")) {
				sb.append(" AND sm.SMP_DISCONT_DT is NULL ");
			}
			sb.append(
					"  and (sm.SMP_STD_DIV_ID =:div_id OR sm.SMP_STD_DIV_ID in ( select dv.PROD_DIV_ID from DIVMAP dv where dv.BASE_DIV_ID =:div_id and dv.CORE_IND = 'A' ) )");
			sb.append("  and sm.SMP_PROD_ID not in ( select prod_id from PROD_LOCK where TEAM_DIV_ID =:div_id )");
			if (allocType.equals("M")) {
				sb.append(
						"  AND SM.SMP_PROD_ID NOT IN ( SELECT PROD_ID FROM ALLOC_GEN_ENT WHERE fin_year = '" + fin_year
								+ "' and period_code = '" + period_code + "' and alloc_type = '" + allocType + "'   ");
				sb.append("  AND DIV_ID=:div_id AND status='A' AND ALLOC_QTY_MSR > 0 )");
			}
			sb.append("  ) dt");
			sb.append(
					"  group by SMP_PROD_ID,SMP_PROD_CD,SMP_PROD_NAME,SMP_PACK_ID,PACK_DISP_NM,SMP_MIN_ALLOC_QTY,isnull(BATCH_PROD_ID,0), isnull(in_transit,0),");
			sb.append("  isnull(so_far_qty,0) ,isnull(plan_qty,0) , SA_GROUP_NAME,SA_PROD_GROUP  ");
			if (zeroStock.trim().equals("Y")) {
				sb.append("  having sum(stock) != 0  ");
			}
			sb.append("  order by SA_GROUP_NAME,SMP_PROD_NAME");

			System.out.println("QUERY:::::::::::" + sb.toString());
			Query query = em.createNativeQuery(sb.toString(), Tuple.class);
			query.setParameter("smp_sa_prod_group", smp_sa_prod_group);
			query.setParameter("div_id", div_id);
			List<Tuple> tuples = query.getResultList();
			if (tuples != null && !tuples.isEmpty()) {
				list = new ArrayList<>();
				AllocationBean object = null;
				for (Tuple t : tuples) {
					object = new AllocationBean();
					object.setSmp_prod_id(Long.valueOf(t.get("SMP_PROD_ID", Integer.class)));
					object.setSmp_prod_cd(t.get("SMP_PROD_CD", String.class));
					object.setSmp_prod_name(t.get("SMP_PROD_NAME", String.class));
					object.setSmp_pack_id(Long.valueOf(t.get("SMP_PACK_ID", Integer.class)));
					object.setPack_disp_nm(t.get("PACK_DISP_NM", String.class));
					object.setSmp_min_alloc_qty(t.get("SMP_MIN_ALLOC_QTY", BigDecimal.class));
					object.setBatch_prod_id(Long.valueOf(t.get("BATCH_PROD_ID", Integer.class)));
					object.setStock(t.get("stock", BigDecimal.class));
					object.setIntransit(t.get("in_transit", BigDecimal.class));
					object.setSo_far_qty(t.get("so_far_qty", BigDecimal.class));
					object.setSa_group_name(t.get("sa_group_name", String.class));
					object.setPlan_qty(Long.valueOf(t.get("plan_qty", BigDecimal.class).setScale(0).toString()));
					object.setSa_group_id(Long.valueOf(t.get("SA_PROD_GROUP", Integer.class)));

					list.add(object);
					this.lockProduct(0l, Long.valueOf(t.get("SMP_PROD_ID", Integer.class)), userEmpId, "0",
							Long.valueOf(div_id));
				}
				System.out.println("Product List" + list.size());
				if (!list.isEmpty() && list.size() > 0)
					return list;

			}

		} finally {
			if (em != null) {
				em.close();
			}
		}
		return list;
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public void lockProduct(Long loc_id, Long prod_id, String user_id, String log_time, Long div_id) throws Exception {
		try {
			StringBuffer buffer = new StringBuffer();
			buffer.append("insert into Prod_lock(loc_id	, prod_id, ").append("user_id, log_time, team_div_id) ")
					.append("values(:loc_id, :prod_id, :user_id, :log_time, :team_div_id) ");

			Query query = entityManager.createNativeQuery(buffer.toString());
			query.setParameter("loc_id", loc_id).setParameter("prod_id", prod_id).setParameter("user_id", user_id)
					.setParameter("log_time", new Date()).setParameter("team_div_id", div_id);
			query.executeUpdate();

		} catch (Exception e) {
			throw e;
		}
	}

	@Override
	public List<Object> getProductListForMonthlyAllocationPriviousYear(String prodType, List<String> smp_sa_prod_group,
			String discProd, String zeroStock, String div_id, String pmtTeam, String period_code, String core,
			String fin_year, String isPrevious) throws Exception {
		System.out.println("Period_code " + period_code);
		EntityManager em = null;
		List<Object> list = null;
		System.out.println("smp_sa_prod_group :" + smp_sa_prod_group);
		System.out.println("isPrevious :" + isPrevious);
		try {
			em = emf.createEntityManager();
			System.out.println("SmpItemDao.getProducts() " + prodType);
			StringBuffer sb = new StringBuffer();
			sb.append(
					"SELECT  SMP_PROD_ID,SMP_PROD_CD,SMP_PROD_NAME,SMP_PACK_ID,PACK_DISP_NM,SMP_MIN_ALLOC_QTY,isnull(BATCH_PROD_ID,0)BATCH_PROD_ID, isnull(stock,0) stock,isnull(in_transit,0)in_transit,isnull(so_far_qty,0) so_far_qty,isnull(plan_qty,0) as plan_qty, SA_GROUP_NAME,SA_PROD_GROUP ");
			sb.append(" from ( ");
			sb.append(" SELECT sm.SMP_PROD_ID,sm.SMP_PROD_CD,sm.SMP_PROD_NAME,sm.SMP_PACK_ID,pk.PACK_DISP_NM,");
			sb.append(
					" sm.SMP_MIN_ALLOC_QTY,sb.BATCH_PROD_ID,SUM( sb.BATCH_OPEN_STOCK+sb.BATCH_IN_STOCK-sb.BATCH_OUT_STOCK - sb.BATCH_WITH_HELD_QTY ) stock , GD.in_transit in_transit, ad.so_far_qty so_far_qty,isnull(pln.plan_qty,0) as plan_qty, spg.SA_GROUP_NAME,spg.SA_PROD_GROUP ");
			sb.append(
					" FROM packmaster AS pk INNER JOIN  SMPITEM AS sm  left outer join SMPBatch sb on  sm.SMP_PROD_ID=sb.batch_prod_id ");
			sb.append(
					" AND convert(date,ISNULL(BATCH_EXPIRY_DT,'2099-01-01'),105) > convert(date,getdate()+smp_short_expiry_days,105)");
			sb.append(" left outer join ( select D.GRND_PROD_ID,sum( D.GRND_QTY ) in_transit     ");
			sb.append("	from GRN_DETAILS D, GRN_HEADER H  where H.GRN_IN_TRANSIT = 'Y' and H.GRN_id = D.GRND_GRN_id ");
			sb.append(" group by D.GRND_PROD_ID ) GD on gd.GRND_PROD_ID = sm.SMP_PROD_ID ");
			sb.append(
					" LEFT OUTER JOIN (SELECT SUM(ALLOCDTL_CURR_ALLOC_QTY-ALLOCDTL_SUPPLY_QTY) so_far_qty, ALLOCDTL_PROD_ID ");
			sb.append(" FROM alloc_detail WHERE ALLOCDTL_PERIOD_CODE = '").append(period_code).append("'");
			sb.append(" AND ALLOCDTL_FIN_YEAR  = '").append(fin_year).append("'");
			sb.append(" GROUP BY ALLOCDTL_PROD_ID) ad ON ad.ALLOCDTL_PROD_ID = sm.SMP_PROD_ID ");
			// sb.append(" LEFT OUTER JOIN (SELECT SUM(ASP_PRDQTY05) AS plan_qt y,
			// ASP_PROD_ID FROM ASP_DETAIL WHERE ASP_DTL_FIN_YEAR ='"+fin_year+"' GROUP BY
			// ASP_PROD_ID) AS pln ON pln.ASP_PROD_ID = sm.SMP_PROD_ID ");

			// privous YEar
			if (isPrevious.equals("Y")) {
				sb.append(
						" LEFT OUTER JOIN (select PROD_ID ASP_PROD_ID ,ALLOC_QTY_MSR plan_qty from ALLOC_GEN_ENT_ARC ");
				sb.append(" where fin_year =" + fin_year + " and period_code ='" + period_code
						+ "' and alloc_type = 'M'");
				sb.append(" ) AS pln ON pln.ASP_PROD_ID = sm.SMP_PROD_ID ");
			} else {
				// Current YEar
				sb.append(" LEFT OUTER JOIN (select PROD_ID ASP_PROD_ID ,ALLOC_QTY_MSR plan_qty from ALLOC_GEN_ent");
				sb.append(" where fin_year =" + fin_year + " and period_code ='" + period_code
						+ "' and alloc_type = 'M' ");
				sb.append(" ) AS pln ON pln.ASP_PROD_ID = sm.SMP_PROD_ID ");
			}
			sb.append(
					"  LEFT OUTER JOIN SAPRODGRP AS spg ON spg.SA_PROD_GROUP = sm.SMP_SA_PROD_GROUP ON pk.pack_id=sm.SMP_PACK_ID   ");
			sb.append(" where  sm.smp_status='A'");
			if (!prodType.equalsIgnoreCase("ALL")) {
				sb.append(" and sm.PROD_SUB_TYPE_ID = ").append(prodType.split("_")[0]).append(" ");
			}
			if (!pmtTeam.equalsIgnoreCase("0")) {
				sb.append(" AND sm.SMP_PMT_GRP_ID = ").append(pmtTeam);
			}

			if (!smp_sa_prod_group.equals("0")) {
				sb.append(" AND sm.SMP_SA_PROD_GROUP in(:smp_sa_prod_group)");
			}
			if (discProd.equalsIgnoreCase("Y")) {
				sb.append(" AND sm.SMP_DISCONT_DT is NULL ");
			}
			sb.append(" and (sm.SMP_STD_DIV_ID = ").append(div_id);
			sb.append(" OR sm.SMP_STD_DIV_ID in ( select dv.PROD_DIV_ID from DIVMAP dv where dv.BASE_DIV_ID =")
					.append(div_id);
			sb.append(" and dv.CORE_IND = '").append(core).append("' ) )");
			sb.append(
					" group by SMP_PROD_ID,SMP_PROD_CD,SMP_PROD_NAME,SMP_PACK_ID,PACK_DISP_NM,SMP_MIN_ALLOC_QTY,sb.BATCH_PROD_ID,GD.in_transit, ad.so_far_qty,pln.plan_qty,spg.SA_GROUP_NAME ,spg.SA_PROD_GROUP");
			sb.append(" ) dt");
			if (zeroStock.equalsIgnoreCase("Y")) {
				// sb.append(" having SUM(
				// sb.BATCH_OPEN_STOCK+sb.BATCH_IN_STOCK-sb.BATCH_OUT_STOCK ) != 0 ");
				sb.append(" where stock != 0 ");
			}
			sb.append(" order by SA_GROUP_NAME,SMP_PROD_NAME");
			System.out.println("QUERY:::::::::::" + sb.toString());
			Query query = em.createNativeQuery(sb.toString(), Tuple.class);
			query.setParameter("smp_sa_prod_group", smp_sa_prod_group);
			List<Tuple> tuples = query.getResultList();
			if (tuples != null && !tuples.isEmpty()) {
				list = new ArrayList<>();
				AllocationBean object = null;
				for (Tuple t : tuples) {
					object = new AllocationBean();
					object.setSmp_prod_id(Long.valueOf(t.get("SMP_PROD_ID", Integer.class)));
					object.setSmp_prod_cd(t.get("SMP_PROD_CD", String.class));
					object.setSmp_prod_name(t.get("SMP_PROD_NAME", String.class));
					object.setSmp_pack_id(Long.valueOf(t.get("SMP_PACK_ID", Integer.class)));
					object.setPack_disp_nm(t.get("PACK_DISP_NM", String.class));
					object.setSmp_min_alloc_qty(t.get("SMP_MIN_ALLOC_QTY", BigDecimal.class));
					object.setBatch_prod_id(Long.valueOf(t.get("BATCH_PROD_ID", Integer.class)));
					object.setStock(t.get("stock", BigDecimal.class));
					object.setIntransit(t.get("in_transit", BigDecimal.class));
					object.setSo_far_qty(t.get("so_far_qty", BigDecimal.class));
					object.setSa_group_name(t.get("sa_group_name", String.class));
					object.setPlan_qty(Long.valueOf(t.get("plan_qty", BigDecimal.class).setScale(0).toString()));
					object.setSa_group_id(Long.valueOf(t.get("SA_PROD_GROUP", Integer.class)));

					list.add(object);
				}
				System.out.println("Product List" + list.size());
				if (!list.isEmpty() && list.size() > 0)
					return list;

			}

		} finally {
			if (em != null) {
				em.close();
			}
		}
		return list;
	}

	@Override
	public V_Dispatch_Period getDispatchPeriod() {
		EntityManager em = null;
		V_Dispatch_Period dispatchPeriod = null;
		try {
			em = emf.createEntityManager();
			CriteriaBuilder builder = em.getCriteriaBuilder();
			CriteriaQuery<V_Dispatch_Period> criteriaQuery = builder.createQuery(V_Dispatch_Period.class);
			Root<V_Dispatch_Period> root = criteriaQuery.from(V_Dispatch_Period.class);
			criteriaQuery.select(root).where(builder.equal(root.get(V_Dispatch_Period_.period_status), "A"));
			dispatchPeriod = em.createQuery(criteriaQuery).getResultList().get(0);
		} finally {
			if (em != null) {
				em.close();
			}
		}
		return dispatchPeriod;

	}

	@Override
	public Long getAllocationByCount(String divId, String staff) throws Exception {
		EntityManager em = null;
		Long count = null;
		try {
			StringBuffer sb = new StringBuffer();
			if (staff.equalsIgnoreCase("pso"))
				sb.append(" select count(distinct this_.FSTAFF_ID) as y0_ from FIELDSTAFF ");
			else if (staff.equalsIgnoreCase("dm"))
				sb.append(" select count(distinct this_.FSTAFF_MGR1_ID) as y0_ from FIELDSTAFF ");
			else
				sb.append(" select count(distinct this_.FSTAFF_MGR2_ID) as y0_ from FIELDSTAFF  ");

			sb.append(
					" this_ where this_.FSTAFF_DIV_ID='01' and this_.FSTAFF_LEVEL_CODE='001' and this_.FSTAFF_SAMP_DISP_IND='Y' ");
			sb.append(
					" and this_.FSTAFF_status='A' and this_.FSTAFF_LEAVING_DATE is null and this_.FSTAFF_ZONE_ID=:FSTAFF_ZONE_ID and this_.FSTAFF_TRAINING='F'  ");
			;
			Query query = entityManager.createNativeQuery(sb.toString());
			query.setParameter("FSTAFF_ZONE_ID", divId);
			count = Long.valueOf(query.getSingleResult().toString());

		} finally {
			if (em != null) {
				em.close();
			}
		}
		return count;
	}

	@Override
	public Long getAllocationCycleNumber(String div_id, String period_code, String fin_year, String mgrId)
			throws Exception {
		EntityManager em = null;
		Long count = null;
		try {

			StringBuffer sb = new StringBuffer();
			sb.append(" select ISNULL(max(a.ALLOC_CYCLE),0) from Alloc_gen_hd a where  ");
			sb.append(" FIN_YEAR=:year and DIV_ID=:div_id  and  ALLOC_MONTH=:month AND MGR_ID=:mgrId");
			Query query = entityManager.createNativeQuery(sb.toString());
			query.setParameter("year", fin_year);
			query.setParameter("div_id", div_id);
			query.setParameter("month", period_code);
			query.setParameter("mgrId", mgrId);
			count = Long.valueOf(query.getSingleResult().toString());

			System.out.println("Count " + count);

		} catch (Exception e) {
			System.out.println("Error Occurred :" + e.getMessage());// uncomment asneeded --;
		} finally {
			if (em != null) {
				em.close();
			}
		}
		return count + 1;
	}

	@Override
	public Alloc_gen_hd getAllocationHeader(String div_id, String period_code, String fin_year, String frequency,
			String mgrId) throws Exception {
		EntityManager em = null;
		List<Alloc_gen_hd> list = null;
		System.out.println("div_id " + div_id);
		System.out.println("fin_year " + fin_year);
		System.out.println("alloc_month " + period_code);
		System.out.println("disp_advice " + frequency);
		System.out.println("mgrId " + mgrId);
		try {
			CriteriaBuilder builder = entityManager.getCriteriaBuilder();
			CriteriaQuery<Alloc_gen_hd> criteriaQuery = builder.createQuery(Alloc_gen_hd.class);
			Root<Alloc_gen_hd> root = criteriaQuery.from(Alloc_gen_hd.class);
			criteriaQuery.select(root).where(builder.equal(root.get(Alloc_gen_hd_.div_id), Long.valueOf(div_id)),
					builder.equal(root.get(Alloc_gen_hd_.fin_year), fin_year),
					builder.equal(root.get(Alloc_gen_hd_.mgr_id), mgrId),
					builder.equal(root.get(Alloc_gen_hd_.alloc_type), frequency),
					builder.equal(root.get(Alloc_gen_hd_.status), A),
					builder.equal(root.get(Alloc_gen_hd_.alloc_month), period_code),
					builder.equal(root.get(Alloc_gen_hd_.alloc_approval_status), "G"));
			list = entityManager.createQuery(criteriaQuery).getResultList();
			if (!list.isEmpty() && list.size() > 0)
				return list.get(0);
		} finally {
			if (em != null) {
				em.close();
			}
		}
		return null;

	}

	@Override
	public Map<String, Object> getAllocationEnt(Long alloc_gen_id) throws Exception {
		EntityManager em = null;
		List<Object> list = null;
		List<Object> enteredBrandList = null;
		Long i = 0L;
		AllocationBean object = null;
		Map<String, String> eneteredBrands = new HashMap<>();
		Map<String, Object> map = new HashMap<>();

		try {
			System.out.println("alloc_gen_id " + alloc_gen_id);
			StringBuffer sb = new StringBuffer();
			sb.append(
					"SELECT     alhd.FIN_YEAR, alhd.ALLOC_MONTH, alhd.DIVISION, alhd.ALLOC_USE_MONTH, alhd.ALLOC_CYCLE, ");
			sb.append("alhd.ALLOC_DOC_NO, si.SMP_PROD_NAME,  alent.ALLOC_GEN_ENT_ID, alent.ALLOC_GEN_ID, ");
			sb.append("alent.ALLOC_GEN_DATE, alent.FIN_YEAR AS FIN_YEAR_ENT, alent.PERIOD_CODE, alent.COMPANY, ");
			sb.append("alent.DIV_ID DIV_ID_ENT,  alent.CFA_DESTINATION_ID, alent.ZONE_ID, ");
			sb.append("alent.STATE_ID, alent.RBM_ID, alent.ABM_ID, alent.MSR_ID, alent.PROD_ID, ");
			sb.append("ISNULL(alent.ALT_DIV,'') ALT_DIV,  alent.ALLOC_CYCLE AS ALLOC_CYCLE_ENT , ");
			sb.append(
					"alent.ALLOC_RATE,alent.ALLOC_SMP_SA_PROD_GROUP_ID, CONVERT(INT,alent.ALLOC_QTY_MSR) AS ALLOC_QTY_MSR, ");
			sb.append("CONVERT(INT,alent.ALLOC_QTY_ABM) AS ALLOC_QTY_ABM ,   ");
			sb.append("CONVERT(INT, alent.ALLOC_QTY_RBM) AS ALLOC_QTY_RBM, ");
			sb.append("CONVERT(INT,alent.ALLOC_QTY_TOTAL) AS ALLOC_QTY_TOTAL, ");
			sb.append("alent.USER_ID, alent.UPD_DATE, alent.UPD_IP_ADD, ");
			sb.append("alent.status, alent.ALLOC_GEN_DTL_ID, alent.ALLOC_MODE, ");
			sb.append("ISNULL(alent.ALT_DIV_ID,0) ALT_DIV_ID , alent.DIVISION AS DIVISION_ENT, ");
			sb.append("alent.FSTAFF_TRAINING, alent.PRODUCT_TYPE, alent.ALLOC_QTY_CM, alent.ALLOC_QTY_TM, ");
			sb.append("alent.ALLOC_FILTER_TYPE,   SGP.SA_GROUP_NAME,ISNULL(SGDES.SGprmdet_disp_nm,'') CFA_NAME, ");
			sb.append("ISNULL(SGDZ.SGprmdet_disp_nm,'') ZONE_NAME,ISNULL(SGDS.SGprmdet_disp_nm,'') STATE_NAME, ");
			sb.append("ISNULL(RBM.FSTAFF_NAME,'') RBM_NAME,ISNULL(ABM.FSTAFF_NAME,'') ABM_NAME, ");
			sb.append("ISNULL(MSR.FSTAFF_NAME,'') MSR_NAME  , ");
			sb.append(
					"CASE WHEN LEN(ISNULL(RTRIM(SGDES.SGprmdet_disp_nm),'')+ ISNULL(RTRIM(SGDZ.SGprmdet_disp_nm),'')+ ");
			sb.append(
					"ISNULL(RTRIM(SGDS.SGprmdet_disp_nm),'')+ISNULL(RTRIM(RBM.FSTAFF_NAME),'') +ISNULL(RTRIM(ABM.FSTAFF_NAME),'') + ");
			sb.append(
					"ISNULL(RTRIM(MSR.FSTAFF_NAME),'')) = 0THEN 'ALL' ELSE ISNULL(RTRIM(SGDES.SGprmdet_disp_nm),'')+ISNULL(RTRIM(SGDZ.SGprmdet_disp_nm),'')+ ");
			sb.append(
					"ISNULL(RTRIM(SGDS.SGprmdet_disp_nm),'')+ISNULL(RTRIM(RBM.FSTAFF_NAME),'') +ISNULL(RTRIM(ABM.FSTAFF_NAME),'') + ");
			sb.append("ISNULL(RTRIM(MSR.FSTAFF_NAME),'') ");
			sb.append("END AS DISPLAY_NAME   ");
			sb.append(
					"FROM   Alloc_gen_hd AS alhd INNER JOIN   ALLOC_GEN_ENT AS  alent ON alent.ALLOC_GEN_ID = alhd.ALLOC_GEN_ID   ");
			sb.append("JOIN SMPITEM  AS si ON si.SMP_PROD_ID = alent.PROD_ID ");
			sb.append("JOIN SAPRODGRP AS  SGP ON SGP.SA_PROD_GROUP = SI.SMP_SA_PROD_GROUP   ");
			sb.append(
					"LEFT JOIN (select distinct dptdestination_ID from DEPOT_LOCATIONS )  DPT ON DPT.dptdestination_ID=ALENT.CFA_DESTINATION_ID ");
			sb.append("LEFT JOIN SG_d_parameters_details SGDZ ON SGDZ.sgprmdet_id=ALENT.ZONE_ID ");
			sb.append("LEFT JOIN SG_d_parameters_details SGDS ON SGDS.sgprmdet_id=ALENT.STATE_ID ");
			sb.append("LEFT JOIN SG_d_parameters_details SGDES ON SGDES.sgprmdet_id=ALENT.CFA_DESTINATION_ID ");
			sb.append("LEFT JOIN FIELDSTAFF RBM ON RBM.FSTAFF_ID=ALENT.RBM_ID   ");
			sb.append("LEFT JOIN FIELDSTAFF ABM ON ABM.FSTAFF_ID=ALENT.ABM_ID   ");
			sb.append("LEFT JOIN FIELDSTAFF MSR ON MSR.FSTAFF_ID=ALENT.MSR_ID ");
			sb.append("WHERE     (alhd.ALLOC_GEN_ID =:alloc_gen_id) ");
			sb.append("AND (alent.ALLOC_GEN_ID =:alloc_gen_id)   ");
			sb.append("ORDER BY alent.ALLOC_GEN_ENT_ID, si.SMP_PROD_NAME  ");
			System.out.println("query " + sb.toString() + alloc_gen_id);
			Query query = entityManager.createNativeQuery(sb.toString(), Tuple.class);
			query.setParameter("alloc_gen_id", alloc_gen_id);
			List<Tuple> queryList = query.getResultList();
			if (queryList != null && !queryList.isEmpty()) {
				list = new ArrayList<>();

				for (Tuple t : queryList) {
					object = new AllocationBean();
					object.setSmp_prod_name(t.get("smp_prod_name", String.class));
					object.setSa_group_name(t.get("SA_GROUP_NAME", String.class));
					object.setTeQty(t.get("ALLOC_QTY_MSR", Integer.class).toString());
					object.setDmQty(t.get("ALLOC_QTY_ABM", Integer.class).toString());
					object.setRmQty(t.get("ALLOC_QTY_RBM", Integer.class).toString());
					object.setTmQty(t.get("ALLOC_QTY_TM", Integer.class).toString());
					object.setSmQty(t.get("ALLOC_QTY_CM", Integer.class).toString());
					object.setSmQty(t.get("ALLOC_QTY_CM", Integer.class).toString());
					object.setAllocDocNo(t.get("ALLOC_DOC_NO", String.class).toString());
					object.setAllocationMonth(t.get("ALLOC_MONTH", String.class).toString());
					object.setAlloc_gen_ent_id(Long.valueOf(t.get("ALLOC_GEN_ENT_ID", Integer.class)));
					if (t.get("DISPLAY_NAME", String.class).toString() != null
							&& !t.get("DISPLAY_NAME", String.class).isEmpty())
						object.setAllocationByName(t.get("DISPLAY_NAME", String.class));
					if (t.get("ZONE_NAME", String.class).toString() != null
							&& !t.get("ZONE_NAME", String.class).isEmpty())
						object.setAllocationByName("Zone-" + t.get("ZONE_NAME", String.class));
					if (t.get("CFA_NAME", String.class).toString() != null
							&& !t.get("CFA_NAME", String.class).isEmpty())
						object.setAllocationByName("CFA-" + t.get("CFA_NAME", String.class));
					if (t.get("STATE_NAME", String.class).toString() != null
							&& !t.get("STATE_NAME", String.class).isEmpty())
						object.setAllocationByName("State-" + t.get("STATE_NAME", String.class));
					if (t.get("RBM_NAME", String.class).toString() != null
							&& !t.get("RBM_NAME", String.class).isEmpty())
						object.setAllocationByName("RM-" + t.get("RBM_NAME", String.class));
					if (t.get("ABM_NAME", String.class).toString() != null
							&& !t.get("ABM_NAME", String.class).isEmpty())
						object.setAllocationByName("AM-" + t.get("ABM_NAME", String.class));
					if (t.get("MSR_NAME", String.class).toString() != null
							&& !t.get("MSR_NAME", String.class).isEmpty())
						object.setAllocationByName("MSR-" + t.get("MSR_NAME", String.class));

					object.setAllocationMode(t.get("ALLOC_MODE", String.class));
					object.setSa_group_id(Long.valueOf(t.get("ALLOC_SMP_SA_PROD_GROUP_ID", Integer.class)));
					object.setIndex(i);
					eneteredBrands.put(object.getSa_group_id().toString(), object.getSa_group_name());
					list.add(object);
					i++;
				}
			}
			enteredBrandList = new ArrayList<>();
			Iterator<Map.Entry<String, String>> itr = eneteredBrands.entrySet().iterator();

			while (itr.hasNext()) {
				object = new AllocationBean();
				Map.Entry<String, String> entry = itr.next();
				object.setSa_group_id(Long.valueOf(entry.getKey()));
				object.setSa_group_name(entry.getValue());
				enteredBrandList.add(object);
			}
			map.put("entList", list);
			map.put("enteredBrands", enteredBrandList);

		} finally {
			if (em != null) {
				em.close();
			}
		}
		return map;
	}

	@Override
	public Alloc_gen_ent getObjectById(Long entId) throws Exception {
		return this.entityManager.find(Alloc_gen_ent.class, entId);
	}

	@Override
	public List<Object> getEnteredAllocationById(String allocationId, String allocationMode, String column,
			String alloc_smp_sa_prod_group_id) {
		EntityManager em = null;
		List<Object> list = new ArrayList<>();
		try {

			StringBuffer sb = new StringBuffer();
			sb.append(" select ");
			sb.append(column);
			sb.append(
					" from ALLOC_GEN_ENT where ALLOC_GEN_ID=:alloc_gen_id and ALLOC_MODE=:alloc_mode and ALLOC_SMP_SA_PROD_GROUP_ID=:alloc_smp_sa_prod_group_id");
			Query query = entityManager.createNativeQuery(sb.toString());
			query.setParameter("alloc_gen_id", allocationId);
			query.setParameter("alloc_mode", allocationMode);
			query.setParameter("alloc_smp_sa_prod_group_id", alloc_smp_sa_prod_group_id);

			System.out.println("Enetered " + sb.toString());
			list = query.getResultList();

		} catch (Exception e) {
			System.out.println("Error Occurred :" + e.getMessage());// uncomment asneeded --;
		} finally {
			if (em != null) {
				em.close();
			}
		}
		return list;
	}

	@Override
	public List<Object> getSelfApprovalDataMonthly(String user_id, String ind) {
		EntityManager em = null;
		List<Object> list = new ArrayList<>();
		try {

			StringBuffer sb = new StringBuffer();
			sb.append(" select alloc_gen_id,convert(varchar(10),alloc_gen_date,103) alloc_gen_date_disp,DIVISION, ");
			sb.append(" ALLOC_DOC_NO,INPUT_TYPE,sgd.SGprmdet_disp_nm,company,mgr_id,fin_year  from Alloc_gen_hd  ah ");
			sb.append("	join SG_d_parameters_details sgd on SGprmdet_id=ah.INPUT_TYPE  ");
			if (ind.equals("Y"))
				sb.append(
						" where user_id=:user_id and ALLOC_APPR_STATUS='E' and status='A' and ASSISTANT_APPR_STATUS ='E'");
			else
				sb.append(" where mgr_id=:user_id and ALLOC_APPR_STATUS='E' and status='A'");
			sb.append("   order by alloc_gen_date,ALLOC_DOC_NO ");
			Query query = entityManager.createNativeQuery(sb.toString(), Tuple.class);
			query.setParameter("user_id", user_id);
			System.out.println("Query:::::::::::::: " + sb.toString());
			List<Tuple> tuples = query.getResultList();
			if (tuples != null && !tuples.isEmpty()) {
				list = new ArrayList<>();
				AllocationBean object = null;
				for (Tuple t : tuples) {
					object = new AllocationBean();
					object.setAllocationId((t.get("alloc_gen_id", Integer.class).toString()));
					object.setAllocationDate(t.get("alloc_gen_date_disp", String.class).toString());
					object.setDiv_disp_nm(t.get("DIVISION", String.class));
					object.setAllocDocNo(t.get("ALLOC_DOC_NO", String.class));
					object.setCompanyName(t.get("company", String.class));
					object.setMgrEmpId(t.get("mgr_id", String.class));
					object.setFinYearId(t.get("fin_year", String.class));

					list.add(object);
				}
				System.out.println("Product List" + list.size());
				if (!list.isEmpty() && list.size() > 0)
					return list;

			}

		} catch (Exception e) {
			System.out.println("Error Occurred :" + e.getMessage());// uncomment asneeded --;
		} finally {
			if (em != null) {
				em.close();
			}
		}
		return list;
	}

	@Override
	public List<Alloc_gen_hd> getAllocGenratedDetails(String user_id) {
		EntityManager em = null;
		List<Alloc_gen_hd> list = null;
		try {
			CriteriaBuilder builder = entityManager.getCriteriaBuilder();
			CriteriaQuery<Alloc_gen_hd> criteriaQuery = builder.createQuery(Alloc_gen_hd.class);
			Root<Alloc_gen_hd> root = criteriaQuery.from(Alloc_gen_hd.class);
			criteriaQuery
					.multiselect(root.get(Alloc_gen_hd_.alloc_mode), root.get(Alloc_gen_hd_.alloc_gen_id),
							root.get(Alloc_gen_hd_.alloc_doc_no), root.get(Alloc_gen_hd_.alloc_type))
					.where(builder.equal(root.get(Alloc_gen_hd_.mgr_id), user_id),
							builder.equal(root.get(Alloc_gen_hd_.alloc_approval_status), "E"),
							builder.equal(root.get(Alloc_gen_hd_.status), "A"));
			list = entityManager.createQuery(criteriaQuery).getResultList();
		} finally {
			if (em != null) {
				em.close();
			}
		}

		return list;
	}

	@Override
	public List<AllocationBean> getAllocEnteredBrands(String alloc_gen_id) {
		EntityManager em = null;
		List<AllocationBean> list = null;
		try {
			StringBuffer sb = new StringBuffer();
			sb.append(" select  DISTINCT spg.sa_group_name,spg.sa_prod_group ");
			sb.append(" from alloc_gen_hd alhd join alloc_gen_dt alent ");
			sb.append(" on alent.ALLOC_GEN_ID=alhd.ALLOC_GEN_ID ");
			sb.append(" join smpitem si on si.smp_prod_id=alent.prod_id ");
			sb.append(" join saprodgrp spg on spg.sa_prod_group=si.smp_sa_prod_group ");
			sb.append(" left join fieldstaff msr on msr.fstaff_id=alent.msr_id ");
			sb.append(" left join fieldstaff abm on abm.fstaff_id=alent.abm_id ");
			sb.append(" left join fieldstaff rbm on rbm.fstaff_id=alent.rbm_id ");
			sb.append(" where alent.ALLOC_GEN_ID>=:alloc_gen_id and  alent.ALLOC_GEN_ID<=:alloc_gen_id ");
			sb.append(" and  (alent.msr_id<>0  or alent.abm_id<>0 or alent.rbm_id<>0) ");

			Query query = entityManager.createNativeQuery(sb.toString(), Tuple.class);
			query.setParameter("alloc_gen_id", alloc_gen_id);
			List<Tuple> tuples = query.getResultList();
			if (tuples != null && !tuples.isEmpty()) {
				list = new ArrayList<>();
				AllocationBean object = null;
				for (Tuple t : tuples) {
					object = new AllocationBean();
					object.setSa_group_id(Long.valueOf(t.get("sa_prod_group", Integer.class)));
					object.setSa_group_name(t.get("sa_group_name", String.class));
					list.add(object);
				}
				System.out.println("getProductDetails " + list.size());
				if (!list.isEmpty() && list.size() > 0)
					return list;

			}

		} finally {
			if (em != null) {
				em.close();
			}
		}
		return list;
	}

	@Override
	public List<AllocationBean> getAllocEnteredProducts(String alloc_gen_id, String prodType) {
		EntityManager em = null;
		List<AllocationBean> list = null;
		try {
			StringBuffer sb = new StringBuffer();
			sb.append(" select  DISTINCT spg.sa_group_name,si.smp_prod_name,si.smp_prod_id ");
			sb.append(" from alloc_gen_hd alhd join alloc_gen_dt alent ");
			sb.append(" on alent.ALLOC_GEN_ID=alhd.ALLOC_GEN_ID ");
			sb.append(" join smpitem si on si.smp_prod_id=alent.prod_id ");
			sb.append(" join saprodgrp spg on spg.sa_prod_group=si.smp_sa_prod_group ");
			sb.append(" left join fieldstaff msr on msr.fstaff_id=alent.msr_id ");
			sb.append(" left join fieldstaff abm on abm.fstaff_id=alent.abm_id ");
			sb.append(" left join fieldstaff rbm on rbm.fstaff_id=alent.rbm_id ");
			sb.append(
					" where alent.ALLOC_GEN_ID>=:alloc_gen_id and  alent.ALLOC_GEN_ID<=:alloc_gen_id and SI.PROD_SUB_TYPE_ID =:prodType");
			sb.append(" and  (alent.msr_id<>0  or alent.abm_id<>0 or alent.rbm_id<>0) ");

			Query query = entityManager.createNativeQuery(sb.toString(), Tuple.class);
			query.setParameter("alloc_gen_id", alloc_gen_id);
			query.setParameter("prodType", prodType);
			List<Tuple> tuples = query.getResultList();
			if (tuples != null && !tuples.isEmpty()) {
				list = new ArrayList<>();
				AllocationBean object = null;
				for (Tuple t : tuples) {
					object = new AllocationBean();
					object.setSa_group_name(t.get("sa_group_name", String.class));
					object.setProductId(Long.valueOf(t.get("smp_prod_id", Integer.class)));
					object.setSmp_prod_name(t.get("smp_prod_name", String.class));
					list.add(object);
				}
				System.out.println("getProductDetails " + list.size());
				if (!list.isEmpty() && list.size() > 0)
					return list;

			}

		} finally {
			if (em != null) {
				em.close();
			}
		}
		return list;
	}

	@Override
	public List<AllocationBean> getEnteredStaffDetails(List<String> prodId, String allocationId) {
		System.out.println("prodId " + prodId);
		System.out.println("allocationId " + allocationId);
		EntityManager em = null;
		List<AllocationBean> list = null;
		Long i = 0L;
		try {
			StringBuffer sb = new StringBuffer();
			sb.append(
					" select alhd.fin_year,alhd.alloc_month,alhd.division,alhd.alloc_use_month,alhd.alloc_cycle,alhd.alloc_doc_no, ");
			sb.append(" si.smp_prod_name, ");
			sb.append(" case when MSR_ID <> 0 THEN msr.fstaff_name WHEN ABM_ID <> 0 THEN abm.fstaff_name ");
			sb.append(" WHEN RBM_ID <> 0 THEN rbm.fstaff_name ELSE '' END fstaff_name, ");
			sb.append(" case when MSR_ID <> 0 THEN msr.fstaff_map_code1 WHEN ABM_ID <> 0 THEN abm.fstaff_map_code1 ");
			sb.append(" WHEN RBM_ID <> 0 THEN rbm.fstaff_map_code1 ELSE '' END fstaff_map_code1, ");
			sb.append(
					" spg.sa_group_name,  alent.*,  CASE WHEN MSR_ID<>0 THEN '001'  WHEN ABM_ID<>0 THEN '002'  WHEN RBM_ID<>0 THEN '003' ELSE '' END  AS FLD_LEVEL,   ");
			sb.append(
					" CASE WHEN MSR_ID<>0 THEN MSR_ID  WHEN ABM_ID<>0 THEN ABM_ID  WHEN RBM_ID<>0 THEN RBM_ID ELSE '' END  AS FSTAFF_ID,   ");
			sb.append(
					" (ALENT.ALLOC_QTY_MSR+ALENT.ALLOC_QTY_ABM+ALENT.ALLOC_QTY_RBM) AS ALLOC_QTY, alent.abm_id,abm.FSTAFF_NAME abm_name,alent.rbm_id,rbm.FSTAFF_NAME rbm_name, ");
			sb.append(
					" ISNULL( msr.FSTAFF_MGR3_ID,0) zm_id, ISNULL( zm.fstaff_name,'' ) zm_name, ISNULL( msr.FSTAFF_MGR4_ID,0) sm_id, ISNULL( sm.fstaff_name,'' ) sm_name, ");
			sb.append(
					" ISNULL( abm.FSTAFF_DISTRICTCD,'' ) district , ISNULL( rbm.FSTAFF_REGIONCD,'') region, isnull( msr.FSTAFF_ZONENAME,'') zone, ");
			sb.append(
					" ISNULL( st.SGprmdet_disp_nm,'') state, ISNULL( ds.SGprmdet_disp_nm,'') cfa_destination,si.smp_prod_name product  ");
			sb.append(" from alloc_gen_hd alhd ");
			sb.append(
					" join alloc_gen_dt alent  on alent.ALLOC_GEN_ID=alhd.ALLOC_GEN_ID join smpitem si on si.smp_prod_id=alent.prod_id   ");
			sb.append(
					" join saprodgrp spg on spg.sa_prod_group=si.smp_sa_prod_group left join fieldstaff msr on msr.fstaff_id=CASE WHEN MSR_ID<>0 ");
			sb.append(" THEN MSR_ID  WHEN ABM_ID<>0 THEN ABM_ID  WHEN RBM_ID<>0 THEN RBM_ID ELSE '' END   ");
			sb.append(
					" left join fieldstaff abm on abm.fstaff_id=msr.FSTAFF_MGR1_ID left join fieldstaff rbm on rbm.fstaff_id=msr.FSTAFF_MGR2_ID ");
			sb.append(
					" left join FIELDSTAFF zm  on zm.FSTAFF_ID = msr.FSTAFF_MGR3_ID left join FIELDSTAFF sm  on sm.FSTAFF_ID = msr.FSTAFF_MGR4_ID ");
			sb.append(" left join SG_d_parameters_details st on st.SGprmdet_id = msr.FSTAFF_STATE_ID ");
			sb.append(
					" left join SG_d_parameters_details DS on DS.SGprmdet_id = alent.CFA_DESTINATION_ID where  alhd.ALLOC_GEN_ID>=:alloc_gen_id ");
			sb.append(" and  alhd.ALLOC_GEN_ID<=:alloc_gen_id ");
			sb.append(" and  alent.prod_id in (:prodId) ");
			sb.append(" and  (alent.msr_id<>0  or alent.abm_id<>0 or alent.rbm_id<>0)  ");
			sb.append(" order by alhd.ALLOC_GEN_ID,si.smp_prod_name,fstaff_map_code1 ");
			Query query = entityManager.createNativeQuery(sb.toString(), Tuple.class);
			query.setParameter("alloc_gen_id", allocationId);
			query.setParameter("prodId", prodId);
			System.out.println("Query :::::::::: " + sb.toString());
			List<Tuple> tuples = query.getResultList();
			if (tuples != null && !tuples.isEmpty()) {
				list = new ArrayList<>();
				AllocationBean object = null;
				for (Tuple t : tuples) {
					object = new AllocationBean();

					object.setSa_group_name(t.get("sa_group_name", String.class));
					object.setStaffId(Long.valueOf(t.get("FSTAFF_ID", Integer.class)));
					object.setStaffName(t.get("fstaff_name", String.class));
					object.setStaffMapCode(t.get("fstaff_map_code1", String.class));
					object.setStaffAllocatedQty(t.get("ALLOC_QTY", BigDecimal.class));
					object.setLevelCode(t.get("FLD_LEVEL", String.class));
					object.setAllocDetailId(Long.valueOf(t.get("ALLOC_GEN_DTL_ID", Integer.class)));
					object.setState(t.get("state", String.class));
					object.setCfa_destination(t.get("cfa_destination", String.class));
					object.setZone(t.get("zone", String.class));
					object.setRigion(t.get("region", String.class));
					object.setDistrict(t.get("district", String.class));
					object.setProd_name(t.get("product", String.class));
					object.setIndex(i);

					list.add(object);
					i++;
					System.out.println("Code" + object.getStaffMapCode());
				}
				if (!list.isEmpty() && list.size() > 0)
					return list;

			}

		} finally {
			if (em != null) {
				em.close();
			}
		}
		return list;
	}

	@Override
	public Alloc_gen_dt getObjectByAllocDtlId(Long dtlId) throws Exception {
		return this.entityManager.find(Alloc_gen_dt.class, dtlId);
	}

	@Override
	public List<AllocationBean> getAllocationDetail(Long allocationId, String year) {
		System.out.println("allocationId " + allocationId);
		EntityManager em = null;
		List<AllocationBean> list = null;
		Long i = 0L;
		try {

			StringBuffer sb = new StringBuffer();
			sb.append(
					" select alhd.fin_year,alhd.alloc_month,alhd.division,alhd.alloc_use_month,alhd.alloc_cycle,alhd.alloc_doc_no,  ");
			sb.append(" alent.prod_id,si.smp_prod_name, ");
			sb.append(" spg.sa_group_name, ");
			sb.append(" SUM(isnull(pln.plan_qty,0)) as PLAN_QTY, ");
			sb.append(" SUM(ALENT.ALLOC_QTY_MSR) AS ALLOC_QTY_MSR, ");
			sb.append(" SUM(ALENT.ALLOC_QTY_ABM) AS ALLOC_QTY_ABM, ");
			sb.append(" SUM(ALENT.ALLOC_QTY_RBM) AS ALLOC_QTY_RBM, ");
			sb.append(" SUM(ALENT.ALLOC_QTY_MSR+ALENT.ALLOC_QTY_ABM+ALENT.ALLOC_QTY_RBM) AS TOTAL_ALLOC_QTY, ");
			sb.append(" isnull(SB.stock,0) STOCK ");
			sb.append(" from alloc_gen_hd alhd join alloc_gen_dt alent ");
			sb.append(" on alent.ALLOC_GEN_ID=alhd.ALLOC_GEN_ID ");
			sb.append(" join smpitem si on si.smp_prod_id=alent.prod_id ");
			sb.append(" join saprodgrp spg on spg.sa_prod_group=si.smp_sa_prod_group ");
			sb.append(" left outer JOIN   ");
			sb.append(" ( ");
			sb.append(" SELECT BATCH_PROD_ID,SUM(BATCH_OPEN_STOCK + BATCH_IN_STOCK - BATCH_OUT_STOCK) AS stock ");
			sb.append(" FROM SMPBatch ");
			sb.append(" JOIN SMPITEM SI ON SI.SMP_PROD_ID=BATCH_PROD_ID ");
			sb.append(" WHERE ");
			sb.append(
					" CONVERT(date, ISNULL(BATCH_EXPIRY_DT, '2099-01-01'), 105) > CONVERT(date,GETDATE() + si.SMP_SHORT_EXPIRY_DAYS, 105) ");
			sb.append(" GROUP BY  BATCH_PROD_ID )SB  ON si.SMP_PROD_ID = sb.BATCH_PROD_ID ");
			sb.append(" LEFT OUTER JOIN (SELECT     SUM(ASP_PRDQTY05) AS plan_qty, ASP_PROD_ID ");
			sb.append("     FROM          ASP_DETAIL ");
			sb.append("      WHERE       ASP_DTL_FIN_YEAR =:year ");
			sb.append("      GROUP BY ASP_PROD_ID) AS pln ");
			sb.append(" ON pln.ASP_PROD_ID = si.SMP_PROD_ID ");
			sb.append(" where ");
			sb.append(" alent.ALLOC_GEN_ID>=:allocationId   ");
			sb.append(" and  alent.ALLOC_GEN_ID<=:allocationId ");
			sb.append(" and  (alent.msr_id<>0  or alent.abm_id<>0 or alent.rbm_id<>0) ");
			sb.append(
					" group by alhd.fin_year,alhd.alloc_month,alhd.division,alhd.alloc_use_month,alhd.alloc_cycle,alhd.alloc_doc_no,alent.prod_id,si.smp_prod_name ,spg.sa_group_name,SB.STOCK ");
			sb.append(
					" order by alhd.fin_year,alhd.alloc_month,alhd.division,alhd.alloc_use_month,alhd.alloc_cycle,alhd.alloc_doc_no,spg.sa_group_name,si.smp_prod_name ");
			System.out.println("Sb " + sb.toString());
			Query query = entityManager.createNativeQuery(sb.toString(), Tuple.class);
			query.setParameter("allocationId", allocationId);
			query.setParameter("year", year);
			System.out.println("Query :::::::::: " + sb.toString());
			List<Tuple> tuples = query.getResultList();
			if (tuples != null && !tuples.isEmpty()) {
				list = new ArrayList<>();
				AllocationBean object = null;
				for (Tuple t : tuples) {
					object = new AllocationBean();

					object.setFinYearId((t.get("fin_year", String.class)).toString());
					object.setAllocationMonth(t.get("alloc_month", String.class));
					object.setDiv_disp_nm(t.get("division", String.class));
					object.setMonthOfUsePeriodName(t.get("alloc_use_month", String.class));
					object.setAllocDocNo(t.get("alloc_doc_no", String.class));
					object.setSmp_prod_name(t.get("smp_prod_name", String.class));
					object.setProductId(Long.valueOf(t.get("prod_id", Integer.class)));
					object.setSa_group_name(t.get("sa_group_name", String.class));
					object.setPlan_qty(Long.valueOf(t.get("PLAN_QTY", Integer.class)));
					object.setTeQty(String.valueOf((t.get("ALLOC_QTY_MSR", BigDecimal.class).intValue())));
					object.setDmQty(String.valueOf((t.get("ALLOC_QTY_ABM", BigDecimal.class).intValue())));
					object.setRmQty(String.valueOf((t.get("ALLOC_QTY_RBM", BigDecimal.class).intValue())));
					object.setTotalAllocQty(t.get("TOTAL_ALLOC_QTY", BigDecimal.class));
					object.setStock(t.get("STOCK", BigDecimal.class));

					list.add(object);
					i++;
				}
				System.out.println("List ::::::::::::::::: " + list.size());
				if (!list.isEmpty() && list.size() > 0)
					return list;

			}

		} finally {
			if (em != null) {
				em.close();
			}
		}
		return list;
	}

	@Override
	public List<Alloc_gen_dt> getAllocationDetail(Long allocId) {
		EntityManager em = null;
		List<Alloc_gen_dt> list = null;
		try {
			em = emf.createEntityManager();
			CriteriaBuilder builder = em.getCriteriaBuilder();
			CriteriaQuery<Alloc_gen_dt> criteriaQuery = builder.createQuery(Alloc_gen_dt.class);
			Root<Alloc_gen_dt> root = criteriaQuery.from(Alloc_gen_dt.class);
			criteriaQuery.select(root).where(builder.equal(root.get(Alloc_gen_dt_.alloc_gen_id), allocId));
			list = em.createQuery(criteriaQuery).getResultList();
			if (list != null && list.size() > 0) {
				return list;
			} else {
				return list;
			}
		} finally {
			if (em != null) {
				em.close();
			}
		}
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public void deleteAllocationByBrandId(Long allocId, Long brandId, String year) throws Exception {
		this.entityManager.createQuery(
				"DELETE FROM Alloc_gen_ent AS e WHERE e.alloc_gen_id.alloc_gen_id = :allocId and e.alloc_smp_sa_prod_group_id=:brandId and e.fin_year=:year")
				.setParameter("allocId", allocId).setParameter("brandId", brandId.toString()).setParameter("year", year)
				.executeUpdate();
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public void changeApprovalStatus(Long allocId, String status) throws Exception {
		this.entityManager
				.createQuery(
						"UPDATE Alloc_gen_hd set alloc_approval_status='" + status + "' WHERE alloc_gen_id=:allocId")
				.setParameter("allocId", allocId).executeUpdate();
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public void changeApprovalStatusForAssistand(Long allocId, String year, String status) throws Exception {
		this.entityManager
				.createQuery(
						"UPDATE Alloc_gen_hd set assistant_appr_status='" + status + "' WHERE alloc_gen_id=:allocId")
				.setParameter("allocId", allocId).executeUpdate();
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public void deleteAllocationDetails(Long allocId) throws Exception {
		this.entityManager.createQuery("DELETE FROM Alloc_gen_dt AS e WHERE e.alloc_gen_id.alloc_gen_id = :allocId ")
				.setParameter("allocId", allocId).executeUpdate();
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public void discardAllocation(Long allocId, Alloc_gen_hd hd) throws Exception {
		this.entityManager.createQuery("UPDATE Alloc_gen_hd set status='D' WHERE alloc_gen_id=:allocId")
				.setParameter("allocId", allocId).executeUpdate();
		this.entityManager.createQuery("UPDATE Alloc_gen_ent set status='D' WHERE alloc_gen_id=:alloc_gen_id")
				.setParameter("alloc_gen_id", hd).executeUpdate();
	}

	@Override
	public Long getSmpId() throws Exception {
		EntityManager em = null;
		int smpId = 0;
		try {
			StringBuffer sb = new StringBuffer();
			sb.append("select max(smp_id) as smp_id from v_sample_policy_header");
			Query query = entityManager.createNativeQuery(sb.toString());
			smpId = (int) query.getSingleResult();
		} finally {
			if (em != null) {
				em.close();
			}
		}
		return Long.valueOf(smpId);
	}

	@Override
	public boolean checkEnetedBrandByProductType(String product_type, List<String> smp_sa_prod_group, String year,
			Long alloc_gen_id) throws Exception {
		EntityManager em = null;
		List<Alloc_gen_ent> list = null;
		try {
			em = emf.createEntityManager();
			CriteriaBuilder builder = em.getCriteriaBuilder();
			CriteriaQuery<Alloc_gen_ent> criteriaQuery = builder.createQuery(Alloc_gen_ent.class);
			Root<Alloc_gen_ent> root = criteriaQuery.from(Alloc_gen_ent.class);
			criteriaQuery.select(root).where(builder.equal(root.get(Alloc_gen_ent_.alloc_gen_id), alloc_gen_id),
					builder.equal(root.get(Alloc_gen_ent_.fin_year), year),
					builder.equal(root.get(Alloc_gen_ent_.product_type), product_type.split("_")[1]),
					builder.and(root.get(Alloc_gen_ent_.alloc_smp_sa_prod_group_id).in(smp_sa_prod_group)));
			list = em.createQuery(criteriaQuery).getResultList();
			if (list != null && list.size() > 0) {
				return true;
			} else {
				return false;
			}
		} finally {
			if (em != null) {
				em.close();
			}
		}
	}

	@Override
	public List<AllocConHd> getAllocConHdUserId(String user_id) {
		EntityManager em = null;
		List<AllocConHd> list = null;
		System.out.println("user_id " + user_id);
		try {
			CriteriaBuilder builder = entityManager.getCriteriaBuilder();
			CriteriaQuery<AllocConHd> criteriaQuery = builder.createQuery(AllocConHd.class);
			Root<AllocConHd> root = criteriaQuery.from(AllocConHd.class);
			criteriaQuery.select(root).distinct(true).where(builder.equal(root.get(AllocConHd_.file_upload), 'N'));
			list = entityManager.createQuery(criteriaQuery).getResultList();
			if (list != null && list.size() > 0) {
				return list;
			} else {
				return null;
			}
		} finally {
			if (em != null) {
				em.close();
			}
		}
	}

	@Override
	public List<Alloc_gen_hd> getAllocGenHdUserId(String user_id) {
		EntityManager em = null;
		List<Alloc_gen_hd> list = null;
		System.out.println("user_id " + user_id);
		try {
			CriteriaBuilder builder = entityManager.getCriteriaBuilder();
			CriteriaQuery<Alloc_gen_hd> criteriaQuery = builder.createQuery(Alloc_gen_hd.class);
			Root<Alloc_gen_hd> root = criteriaQuery.from(Alloc_gen_hd.class);
			criteriaQuery.select(root).distinct(true)
					.where(builder.equal(root.get(Alloc_gen_hd_.file_upload), "N"),
							builder.equal(root.get(Alloc_gen_hd_.user_id), user_id),
							builder.equal(root.get(Alloc_gen_hd_.alloc_approval_status), "E"))
					.orderBy(builder.desc(root.get(Alloc_gen_hd_.alloc_gen_id)));
			list = entityManager.createQuery(criteriaQuery).getResultList();
			if (list != null && list.size() > 0) {
				return list;
			} else {
				return null;
			}
		} finally {
			if (em != null) {
				em.close();
			}
		}
	}

	@Override
	public List<AllocationBean> getSummaryDetailsAnnualPlanByDivision(Long alloc_con_id, String year, String type)
			throws Exception {
		EntityManager em = null;
		List<AllocationBean> list = null;
		try {
			StringBuffer sb = new StringBuffer();
//			sb.append(" select sgr.SA_GROUP_NAME,");
//			sb.append(" CONVERT(NUMERIC(12,2),SUM((ASPDT.ASP_COG))) VALUE_INR ,");
//			sb.append(" SUM(ASPDT.ASP_PRDQTY01+ASPDT.ASP_PRDQTY02+ASPDT.ASP_PRDQTY03+ASPDT.ASP_PRDQTY04+ASPDT.ASP_PRDQTY05+ASPDT.ASP_PRDQTY06+  ASPDT.ASP_PRDQTY07+ASPDT.ASP_PRDQTY08+ASPDT.ASP_PRDQTY09+ASPDT.ASP_PRDQTY10+ASPDT.ASP_PRDQTY11+ASPDT.ASP_PRDQTY12)*(ASPDT.ASP_TEAM_SIZE) UNITS,");
//			sb.append(" (SUM(ASPDT.ASP_PRDQTY01+ASPDT.ASP_PRDQTY02+ASPDT.ASP_PRDQTY03+ASPDT.ASP_PRDQTY04+ASPDT.ASP_PRDQTY05+ASPDT.ASP_PRDQTY06+  ASPDT.ASP_PRDQTY07+ASPDT.ASP_PRDQTY08+ASPDT.ASP_PRDQTY09+ASPDT.ASP_PRDQTY10+ASPDT.ASP_PRDQTY11+ASPDT.ASP_PRDQTY12)/12)*(ASPDT.ASP_TEAM_SIZE) AVG_MTH,");
//			sb.append(" 'Every Month' as Frequency,");
//			sb.append(" ASPHD.ASP_ALLOC_NUMBER,ASPHD.ASP_FIN_YEAR,ASPHD.MGR_EMP_ID   FROM ASP_HEADER ASPHD");
//			sb.append(" JOIN ASP_DETAIL ASPDT ON ASPHD.ASP_ID=ASPDT.ASP_ID ");
//			sb.append(" JOIN SMPITEM SI ON SI.SMP_PROD_ID = ASPDT.ASP_PROD_ID ");
//			sb.append(" JOIN SAPRODGRP SGR ON SGR.SA_PROD_GROUP = SI.SMP_SA_PROD_GROUP");
//			sb.append(" WHERE asphd.asp_fin_year=:year AND asphd.ASP_STATUS ='A' AND asphd.ASP_ID in(select ALLOC_GEN_ID from ALLOC_CON_DT where ALLOC_CON_ID=:alloc_con_id)");
//			sb.append(" GROUP BY sgr.SA_GROUP_NAME,ASPHD.ASP_FIN_YEAR ,ASPHD.ASP_ALLOC_NUMBER,ASPDT.ASP_TEAM_SIZE,ASPHD.MGR_EMP_ID");
//			sb.append(" ORDER BY sgr.SA_GROUP_NAME,ASPHD.ASP_FIN_YEAR ,ASPHD.ASP_ALLOC_NUMBER");

			sb.append(" select SA_GROUP_NAME,VALUE_INR,UNITS,");
			sb.append(" CASE WHEN FREQUENCY='M' THEN CONVERT(NUMERIC(12,2),VALUE_INR/12)");
			sb.append(" WHEN FREQUENCY='B' THEN CONVERT(NUMERIC(12,2),VALUE_INR/6)");
			sb.append(" WHEN FREQUENCY='Q' THEN CONVERT(NUMERIC(12,2),VALUE_INR/4) END AVG_MTH,");
			sb.append(" CASE WHEN FREQUENCY='M' THEN 'Monthly'");
			sb.append(" WHEN FREQUENCY='B' THEN 'BI-Monthly'");
			sb.append(" WHEN FREQUENCY='Q' THEN 'Quarterly' END FREQUENCY,");
			sb.append(" ASP_ALLOC_NUMBER,ALLOC_CON_DOCNO_T,ASP_FIN_YEAR,MGR_EMP_ID");
			sb.append(" from (");
			sb.append(" select sgr.SA_GROUP_NAME,");
			sb.append(
					" sum((ASPDT.ASP_PRDQTY01+ASPDT.ASP_PRDQTY02+ASPDT.ASP_PRDQTY03+ASPDT.ASP_PRDQTY04+ASPDT.ASP_PRDQTY05+ASPDT.ASP_PRDQTY06+  ASPDT.ASP_PRDQTY07+ASPDT.ASP_PRDQTY08+ASPDT.ASP_PRDQTY09+ASPDT.ASP_PRDQTY10+ASPDT.ASP_PRDQTY11+ASPDT.ASP_PRDQTY12)*(ASPDT.ASP_TEAM_SIZE)) UNITS,");
			sb.append(
					" sum((ASPDT.ASP_PRDQTY01+ASPDT.ASP_PRDQTY02+ASPDT.ASP_PRDQTY03+ASPDT.ASP_PRDQTY04+ASPDT.ASP_PRDQTY05+ASPDT.ASP_PRDQTY06+  ASPDT.ASP_PRDQTY07+ASPDT.ASP_PRDQTY08+ASPDT.ASP_PRDQTY09+ASPDT.ASP_PRDQTY10+ASPDT.ASP_PRDQTY11+ASPDT.ASP_PRDQTY12)*(ASPDT.ASP_COG)*(ASPDT.ASP_TEAM_SIZE)) VALUE_INR,");
			sb.append(
					" ASPDT.ASP_FREQUENCY AS Frequency,ASPHD.ASP_ALLOC_NUMBER,ASPHD.ALLOC_CON_DOCNO_T,ASPHD.ASP_FIN_YEAR,ASPHD.MGR_EMP_ID   FROM ASP_HEADER ASPHD");
			sb.append(" JOIN ASP_DETAIL ASPDT ON ASPHD.ASP_ID=ASPDT.ASP_ID");
			sb.append(" JOIN SMPITEM SI ON SI.SMP_PROD_ID = ASPDT.ASP_PROD_ID");
			sb.append(" JOIN SAPRODGRP SGR ON SGR.SA_PROD_GROUP = SI.SMP_SA_PROD_GROUP");
			sb.append(
					" WHERE asphd.asp_fin_year=:year AND asphd.ASP_STATUS ='A' AND asphd.ASP_ID in(select ALLOC_GEN_ID from ALLOC_CON_DT where ALLOC_CON_ID=:alloc_con_id)");
			sb.append(
					" GROUP BY sgr.SA_GROUP_NAME,ASPHD.ASP_FIN_YEAR ,ASPDT.ASP_FREQUENCY,ASPHD.ASP_ALLOC_NUMBER,ASPHD.ALLOC_CON_DOCNO_T,ASPDT.ASP_TEAM_SIZE,ASPHD.MGR_EMP_ID");
			sb.append(" ) dtl");
			sb.append(" ORDER BY SA_GROUP_NAME,ASP_FIN_YEAR,ASP_ALLOC_NUMBER;");
			Query query = entityManager.createNativeQuery(sb.toString(), Tuple.class);
			query.setParameter("year", year);
			query.setParameter("alloc_con_id", alloc_con_id);
			List<Tuple> tuples = query.getResultList();
			if (tuples != null && !tuples.isEmpty()) {
				list = new ArrayList<>();
				AllocationBean object = null;
				for (Tuple t : tuples) {
					object = new AllocationBean();
					object.setSa_group_name(t.get("SA_GROUP_NAME", String.class));
					// object.setTotalCost(t.get("VALUE_INR",
					// BigDecimal.class).multiply(BigDecimal.valueOf(Long.valueOf(t.get("AVG_MTH",
					// Integer.class))).multiply(BigDecimal.valueOf(12L))));
					object.setTotalCost(t.get("VALUE_INR", BigDecimal.class));
					object.setTotalUnits(Long.valueOf(t.get("UNITS", Integer.class)));
					object.setAvgMonth(t.get("AVG_MTH", BigDecimal.class));
					object.setFrequency(t.get("FREQUENCY", String.class));
					object.setAsp_alloc_number(t.get("ALLOC_CON_DOCNO_T", String.class));
					object.setAsp_fin_year(t.get("ASP_FIN_YEAR", String.class));
					object.setMgrEmpId(t.get("MGR_EMP_ID", String.class));
					list.add(object);
				}
				if (!list.isEmpty() && list.size() > 0)
					return list;

			}

		} finally {
			if (em != null) {
				em.close();
			}
		}
		return list;
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public void updateAllocDt(AllocConHd header, Long alloc_con_dtl_id, Alloc_gen_hd alloc_gen_id) {
		this.entityManager.createQuery(
				" UPDATE Alloc_gen_dt set alloc_con_id = :alloc_con_id,alloc_con_dtl_id=:alloc_con_dtl_id,alloc_con_docno=:alloc_con_docno,alloc_con_date=:alloc_con_date where alloc_gen_id=:alloc_gen_id ")
				.setParameter("alloc_con_id", header.getAlloc_con_id())
				.setParameter("alloc_con_dtl_id", alloc_con_dtl_id)
				.setParameter("alloc_con_docno", header.getAlloc_con_docno())
				.setParameter("alloc_con_date", header.getAlloc_con_date()).setParameter("alloc_gen_id", alloc_gen_id)
				.executeUpdate();
	}

	@Override
	public PolicyHeaderBean getCurrSamplePolHdData() throws Exception {
		EntityManager em = null;
		PolicyHeaderBean bean = null;
		try {
			em = this.emf.createEntityManager();
			String sql = "select SMP_ID,SMP_FIN_YEAR,SMP_ALLOC_PERIOD_CODE from SAMPLE_POLICY_HEADER order by smp_id desc";
			Query query = em.createNativeQuery(sql, Tuple.class);
			query.setMaxResults(1);

			List<Tuple> tuple_list = query.getResultList();
			if (tuple_list != null && tuple_list.size() > 0) {
				bean = new PolicyHeaderBean();
				bean.setFin_year(tuple_list.get(0).get("SMP_FIN_YEAR", String.class));
				bean.setPeriod_code(tuple_list.get(0).get("SMP_ALLOC_PERIOD_CODE", String.class));
				bean.setSmp_id(tuple_list.get(0).get("SMP_ID", Integer.class));
			} else {
				System.out.println("Failed to fetch Sample Policy Header");
				throw new Exception("Failed to fetch Sample Policy Header");
			}
		} finally {
			if (em != null)
				em.close();
		}
		return bean;
	}

	@Override
	public Object getallocgenrateddetails_upload_modify(String empId) {
		EntityManager em = null;
		List<Alloc_Temp_Header> list = null;
		try {
			CriteriaBuilder builder = entityManager.getCriteriaBuilder();
			CriteriaQuery<Alloc_Temp_Header> criteriaQuery = builder.createQuery(Alloc_Temp_Header.class);
			Root<Alloc_Temp_Header> root = criteriaQuery.from(Alloc_Temp_Header.class);
			criteriaQuery
					.multiselect(root.get(Alloc_Temp_Header_.csv_file_name),
							root.get(Alloc_Temp_Header_.alloc_header_id), root.get(Alloc_Temp_Header_.alloc_temp_hd_id),
							root.get(Alloc_Temp_Header_.alloc_type))

					.where(builder.equal(root.get(Alloc_Temp_Header_.ins_usr_id), empId),
							builder.equal(root.get(Alloc_Temp_Header_.approval_status), 'N'));

			list = entityManager.createQuery(criteriaQuery).getResultList();
		} finally {
			if (em != null) {
				em.close();
			}
		}

		return list;
	}

	@Override
	public Alloc_Temp_Header getHeaderDetailsBeforeApprove(Long valueOf) {
		// TODO Auto-generated method stub
		return this.entityManager.find(Alloc_Temp_Header.class, valueOf);
	}

	@Override
	public List<AllocationBean> getAllocEnteredProducts_before_approve(String allocId, String prodType) {
		EntityManager em = null;
		List<AllocationBean> list = null;
		try {
			StringBuffer sb = new StringBuffer();
			sb.append("	select  DISTINCT spg.sa_group_name,si.smp_prod_name,si.smp_prod_id ");
			sb.append("	 from ALLOC_TEMP_HEADER alhd join Alloc_temp alent ");
			sb.append("		 on alent.alloc_temp_hd_id=alhd.ALLOC_TEMP_HD_ID ");
			sb.append("	 join smpitem si on si.smp_prod_id=alent.prod_id ");
			sb.append("	 join saprodgrp spg on spg.sa_prod_group=si.smp_sa_prod_group  ");
			sb.append("		where alent.alloc_temp_hd_id>=:allocId and  alent.alloc_temp_hd_id<=:allocId ");
			sb.append("	and SI.PROD_SUB_TYPE_ID =:prodType ");

			Query query = entityManager.createNativeQuery(sb.toString(), Tuple.class);
			query.setParameter("allocId", allocId);
			query.setParameter("prodType", prodType);
			List<Tuple> tuples = query.getResultList();
			if (tuples != null && !tuples.isEmpty()) {
				list = new ArrayList<>();
				AllocationBean object = null;
				for (Tuple t : tuples) {
					object = new AllocationBean();
					object.setSa_group_name(t.get("sa_group_name", String.class));
					object.setProductId(Long.valueOf(t.get("smp_prod_id", Integer.class)));
					object.setSmp_prod_name(t.get("smp_prod_name", String.class));
					list.add(object);
				}
				System.out.println("getProductDetails " + list.size());
				if (!list.isEmpty() && list.size() > 0)
					return list;

			}

		} finally {
			if (em != null) {
				em.close();
			}
		}
		return list;
	}

	@Override
	public Object getEnteredStaffDetails_for_modify(String prodId, String allocationId) {
		System.out.println("prodId " + prodId);
		System.out.println("allocationId " + allocationId);
		EntityManager em = null;
		List<AllocationBean> list = null;
		Long i = 0L;
		try {
			StringBuffer sb = new StringBuffer();
			sb.append(
					" SELECT  al.*,st.SGprmdet_disp_nm  as state, isnull( msr.FSTAFF_ZONENAME,'') zone,tm.TERR_DISTRICTCD,tm.TERR_REGIONCD,");
			sb.append(" ISNULL(abm.FSTAFF_DISTRICTCD,'' ) district , ISNULL( rbm.FSTAFF_REGIONCD,'') region");
			sb.append(" from Alloc_temp al  left join fieldstaff msr on msr.fstaff_id=al.fstaff_id ");
			sb.append(" left join SG_d_parameters_details st on st.SGprmdet_id = msr.FSTAFF_STATE_ID  ");
			sb.append(" left join terr_master tm on TERR_CODE = msr.FSTAFF_TERR_CODE  ");
			sb.append(" left join fieldstaff abm on abm.fstaff_id=msr.FSTAFF_MGR1_ID");
			sb.append(" left join fieldstaff rbm on rbm.fstaff_id=msr.FSTAFF_MGR2_ID ");
			sb.append("	 where alloc_temp_hd_id=:allocationId   and PROD_ID in(:prodId)");

			Query query = entityManager.createNativeQuery(sb.toString(), Tuple.class);
			query.setParameter("allocationId", allocationId);
			query.setParameter("prodId", prodId);
			System.out.println("Query :::::::::: " + sb.toString());
			List<Tuple> tuples = query.getResultList();
			if (tuples != null && !tuples.isEmpty()) {
				list = new ArrayList<>();
				AllocationBean object = null;
				for (Tuple t : tuples) {
					object = new AllocationBean();

					object.setSa_group_name(t.get("PROD_NAME", String.class));
					object.setStaffId(Long.valueOf(t.get("FSTAFF_ID", Integer.class)));
					object.setStaffName(t.get("FSNAME", String.class));
					object.setStaffMapCode(t.get("FSCODE", String.class));
					object.setStaffAllocatedQty(t.get("FINAL_ALLOCQTY", BigDecimal.class));
					object.setAllocDetailId(Long.valueOf(t.get("ALLOC_TEMP_ID", Integer.class)));
					object.setState(t.get("state", String.class));
//					object.setCfa_destination(t.get("cfa_destination", String.class));
					object.setZone(t.get("zone", String.class));
					object.setRigion(t.get("region", String.class));
					object.setDistrict(t.get("district", String.class));
					object.setProd_name(t.get("PROD_NAME", String.class));
					object.setIndex(i);

					list.add(object);
					i++;
					System.out.println("Code" + object.getStaffMapCode());
				}
				if (!list.isEmpty() && list.size() > 0)
					return list;

			}

		} finally {
			if (em != null) {
				em.close();
			}
		}
		return list;
	}

	@Override
	public Boolean saveLogFor_Alloc_temp(Long alloc_temp_header_id, String alloc_tem_prod_id,Long alloc_tem_id) throws Exception {

		EntityManager em = null;

		int i = 0;
		try {
			StringBuffer sb = new StringBuffer();

			sb.append(" SET IDENTITY_INSERT  alloc_temp_modify_log OFF; ");
			sb.append(" insert INTO  alloc_temp_modify_log");
			sb.append(
					" (ALLOC_TEMP_ID,FIN_YEAR,PERIOD_CODE,COMPANY,FSCODE,FSNAME,FSTAFF_ID,DIV_ID,PROD_ID,PROD_CODE,PROD_NAME,");
			sb.append(
					" ALLOC_TYPE,ALLOC_CYCLE,ALLOC_RATE,ALLOC_QTY,MSR_STOCK,FINAL_ALLOCQTY,ALT_DIV_ID,INV_GRP,ins_usr_id,ins_dt,ins_ip_add,");
			sb.append(
					" status,UPLOAD_STATUS,UPLOAD_CYCLE,Alloc_gen_hd_id,csv_file_name,alloc_temp_hd_id,TEAM_CODE,ALLOCDTL_ID,ALLOCDTL_ALLOC_ID,ins_dt_LOG,mod_usr_id,mod_dt,mod_ip_add)");
			sb.append(
					" select ALLOC_TEMP_ID,FIN_YEAR,PERIOD_CODE,COMPANY,FSCODE,FSNAME,FSTAFF_ID,DIV_ID,PROD_ID,PROD_CODE,PROD_NAME,");
			sb.append(
					" ALLOC_TYPE,ALLOC_CYCLE,ALLOC_RATE,ALLOC_QTY,MSR_STOCK,FINAL_ALLOCQTY,ALT_DIV_ID,INV_GRP,ins_usr_id,GETDATE(),ins_ip_add,");
			sb.append(
					" status,UPLOAD_STATUS,UPLOAD_CYCLE,Alloc_gen_hd_id,csv_file_name,alloc_temp_hd_id,TEAM_CODE,ALLOCDTL_ID,ALLOCDTL_ALLOC_ID ,GETDATE(),mod_usr_id,mod_dt,mod_ip_add");
			sb.append("	 from  alloc_temp  where  alloc_temp_hd_id=:allocationId   and PROD_ID =:prodId   and Alloc_temp_id=:alloc_tem_id ");

			Query query = entityManager.createNativeQuery(sb.toString());
			query.setParameter("allocationId", alloc_temp_header_id);
			query.setParameter("prodId", alloc_tem_prod_id);
			query.setParameter("alloc_tem_id", alloc_tem_id);
			System.out.println("Query :::::::::: " + sb.toString());
			i = query.executeUpdate();

		} finally {
			if (em != null) {
				em.close();
			}
		}

		if (i > 0) {
			return true;
		}
		return false;

	}

	@Override
	public Alloc_Temp getObjectByAllocTemp_detail(Long detail_id) {
		return this.entityManager.find(Alloc_Temp.class, detail_id);
	}

	@Override
	public List<AllocationBean> getAllocation_modified_Log(Long header_id) {
		EntityManager em = null;
		List<AllocationBean> list = null;
		try {
			em = emf.createEntityManager();
			StringBuffer sb = new StringBuffer();
			sb.append(
					" select sgr.SGprmdet_disp_nm as state, atl.alloc_temp_id,atl.alloc_temp_hd_id,atl.alloc_gen_hd_id, atl.csv_file_name,");
			sb.append(
					" at.fstaff_id as atl_fstaff_id, atl.FSCODE,atl.FSNAME, atl.prod_id  as atl_prod_id,atl.PROD_NAME,");
			sb.append(
					" atl.FINAL_ALLOCQTY as atl_final_allocqty ,at.FINAL_ALLOCQTY as at_final_allocqty, atl.ins_dt_log, at.mod_usr_id,");
			sb.append(
					" atl.mod_dt,at.mod_ip_add,h1.emp_fnm+ '' +h1.emp_lnm  as ins_fs_name, h2.emp_fnm+ '' +h2.emp_lnm  as mod_fs_name");
			sb.append(
					" ,at.mod_dt at_mod_dt from alloc_temp at join alloc_temp_modify_log atl on at.alloc_temp_hd_id =atl.alloc_temp_hd_id");
			sb.append(
					" join hr_m_employee h1 on h1.emp_id = at.mod_usr_id join hr_m_employee h2 on h2.emp_id = at.ins_usr_id");
			sb.append(" join fieldstaff fs on fs.fstaff_id=at.FSTAFF_ID");
			sb.append(" join SG_d_parameters_details sgr   on sgr.SGprmdet_id = fs.FSTAFF_STATE_ID");
			sb.append(
					" where  at.FINAL_ALLOCQTY <>atl.FINAL_ALLOCQTY and at.FINAL_ALLOCQTY<>0  and at.alloc_temp_hd_id=:header_id and");
			sb.append(
					" atl.alloc_temp_hd_id=:header_id and (atl.PROD_ID = at.prod_id ) and at.fstaff_id=atl.fstaff_id order by atl.FSNAME,atl.SL_NO");

			Query query = em.createNativeQuery(sb.toString(), Tuple.class);
			query.setParameter("header_id", header_id);
			List<Tuple> tuples = query.getResultList();
			if (tuples != null && !tuples.isEmpty()) {
				list = new ArrayList<>();
				AllocationBean object = null;
				for (Tuple t : tuples) {
					object = new AllocationBean();
					object.setStaffName(t.get("FSNAME", String.class));
					object.setFscode(t.get("FSCODE", String.class));
					object.setProd_name(t.get("PROD_NAME", String.class));
					object.setProductId(t.get("atl_prod_id", Integer.class).longValue());
					object.setState(t.get("State", String.class));
					object.setModified_final_qty(t.get("atl_final_allocqty", BigDecimal.class).longValue());
					object.setStaffId(t.get("atl_fstaff_id", Integer.class).longValue());
					object.setGen_hd_id(t.get("alloc_gen_hd_id", BigInteger.class).longValue());
					object.setCurrent_final_qty(t.get("at_final_allocqty",BigDecimal.class).longValue());
					object.setModified_date(t.get("mod_dt", Date.class));
					object.setCurrent_modified_date(t.get("at_mod_dt", Date.class));
					object.setModified_user_name(t.get("mod_fs_name", String.class));
					object.setFileName(t.get("csv_file_name", String.class));
					object.setInsertUserName(t.get("ins_fs_name", String.class));
					list.add(object);
				}
				if (!list.isEmpty() && list.size() > 0)
					return list;
			}
		} catch (Exception e) {
e.printStackTrace();
			// TODO: handle exception
		}

		finally {
			if (em != null) {
				em.close();
			}

		}

		return list;
	}

}
