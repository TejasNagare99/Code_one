package com.excel.repository;

import java.math.BigDecimal;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.ParameterMode;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.StoredProcedureQuery;
import javax.persistence.Tuple;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.excel.bean.DashboardBean;
import com.excel.bean.StockTransferBean;
import com.excel.model.ActivityApproval;
import com.excel.model.ActivityNotification;
import com.excel.model.ApprovalTrackingData;
import com.excel.model.DashboardCharts;
import com.excel.model.DashboardCharts2;
import com.excel.model.DashboardChartsWithSku;
import com.excel.model.FieldStaff;
import com.excel.model.FsDispatchLandingPage;
import com.excel.model.Period;
import com.excel.model.SmpItem;
import com.excel.model.Transporter_master;
import com.excel.utility.CodifyErrors;

@Repository
public class DashBoardRepositoryImpl implements DashBoardRepository {
	
	@Autowired private EntityManagerFactory emf;
	@PersistenceContext private EntityManager entityManager;

	@Override
	public List<ActivityApproval> getTaskList(List<String> task_ids, String identitylink, String erp_cust_outstanding)
			throws ParseException {
		EntityManager em = null;
		List<ActivityApproval> list= null;
		try {
			System.out.println("List Ids"+ task_ids.toString().replace("]", "").replace("[", ""));
			em = emf.createEntityManager();
			StoredProcedureQuery query  = em.createNamedStoredProcedureQuery("callActivityApproval");
			query.setParameter("pcTaskId",task_ids.toString().replace("]", "").replace("[", ""));;
			list = query.getResultList();
			System.out.println("List Size  s4 "+list.size());
		} finally {
			if (em != null) { em.close(); }
		}
		return list;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<ActivityNotification> getNotification(String startedBy) throws Exception {
		List<ActivityNotification> list = null;
		EntityManager em = null;
		try {
			
			em = emf.createEntityManager();
			StoredProcedureQuery callActivityNotification  = em.createNamedStoredProcedureQuery("callAPPROVAL_NOTIFICATION_DISPLAY");
			callActivityNotification.setParameter("pUserid", startedBy);
			
			list = callActivityNotification.getResultList();

		} finally {
			if(em != null) { em.close(); }
		}
		if (list != null && !list.isEmpty()) {
			return list;
		}
		return null;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<DashboardCharts> getPIDashboardGraphData(Long locId,String empId) throws Exception {
		EntityManager em = null;
		List<DashboardCharts> list= null;
		try {
			em = emf.createEntityManager();
			StoredProcedureQuery query  = em.createNamedStoredProcedureQuery("callDBPI");
			query.setParameter("pLoc_id", locId);
			query.setParameter("puserid", empId);
			list = query.getResultList();
		} finally {
			if (em != null) { em.close(); }
		}
		return list;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<DashboardCharts> getSampleDashboardGraphData(Long locId,String empId) throws Exception {
		EntityManager em = null;
		List<DashboardCharts> list= null;
		try {
			em = emf.createEntityManager();
			StoredProcedureQuery query  = em.createNamedStoredProcedureQuery("callDBSample");
			query.setParameter("pLoc_id", locId);
			query.setParameter("puserid", empId);
			list = query.getResultList();
		} finally {
			if (em != null) { em.close(); }
		}
		return list;
	}

	@Override
	public List<Object[]> getDashboardPIDataForGRN(Long locId,Integer noOfDays,String empId) throws Exception {
		EntityManager em = null;
		List<Object[]> list= null;
		try {
			em = emf.createEntityManager();
//			StringBuffer sb = new StringBuffer();
//			sb.append(" SELECT SI.SMP_PROD_NAME,GH.GRN_PO_NO,SI.SMP_PROD_CD,UPPER(SPG.SA_GROUP_NAME) SA_GROUP_NAME ,SUM(GRND_QTY) AS GRN_QTY FROM GRN_DETAILS GD");
//			sb.append(" JOIN GRN_HEADER GH ON GH.GRN_ID=GD.GRND_GRN_ID");
//			sb.append(" JOIN SMPITEM SI ON SI.SMP_PROD_ID=GD.GRND_PROD_ID");
//			sb.append(" JOIN SAPRODGRP SPG ON SPG.SA_PROD_GROUP=SI.SMP_SA_PROD_GROUP");
//			sb.append(" WHERE");
//			sb.append(" GRN_DT>=CONVERT(DATE,GETDATE()-7)");
//			sb.append(" AND SI.SMP_PROD_TYPE <>'Sample'");
//			sb.append(" AND GH.GRN_APPR_STATUS='A' AND GH.GRN_STATUS='A' AND GD.GRND_STATUS='A' AND GD.GRND_APPR_STATUS='A'");
//			sb.append(" AND GH.GRN_LOC_ID=1");
//			sb.append(" GROUP BY SI.SMP_PROD_NAME,GH.GRN_PO_NO,SI.SMP_PROD_CD,UPPER(SPG.SA_GROUP_NAME)");
//			sb.append(" ORDER BY SI.SMP_PROD_NAME,SA_GROUP_NAME");
//			Query query = em.createNativeQuery(sb.toString());
//			list = query.getResultList();
			StoredProcedureQuery query = em.createStoredProcedureQuery("DBOARD_GRAPH_LASTGRN_RECD_BRANDWISE_QTY_PI");

			query.registerStoredProcedureParameter(1, Long.class, ParameterMode.IN);
			query.registerStoredProcedureParameter(2, Integer.class, ParameterMode.IN);
			query.registerStoredProcedureParameter(3, String.class, ParameterMode.IN);

			query.setParameter(1, locId);
			query.setParameter(2, noOfDays);
			query.setParameter(3, empId);
			list = query.getResultList();
		} finally {
			if (em != null) { em.close(); }
		}
		return list;
	}

	@Override
	public List<Object[]> getDashboardPIDetailDataForGRN(Long locId,Integer noOfDays,String empId,String grn_no,String product_code,String brand_name) throws Exception {
		EntityManager em = null;
		List<Object[]> list= null;
		try {
			em = emf.createEntityManager();
			StoredProcedureQuery query = em.createStoredProcedureQuery("DBOARD_GRAPH_LASTGRN_RECD_BRANDWISE_QTY_PI_GRNWISE");

			query.registerStoredProcedureParameter(1, Long.class, ParameterMode.IN);
			query.registerStoredProcedureParameter(2, Integer.class, ParameterMode.IN);
			query.registerStoredProcedureParameter(3, String.class, ParameterMode.IN);
			query.registerStoredProcedureParameter(4, String.class, ParameterMode.IN);
			query.registerStoredProcedureParameter(5, String.class, ParameterMode.IN);
			query.registerStoredProcedureParameter(6, String.class, ParameterMode.IN);

			query.setParameter(1, locId);
			query.setParameter(2, noOfDays);
			query.setParameter(3, empId);
			query.setParameter(4, grn_no);
			query.setParameter(5, product_code);
			query.setParameter(6, brand_name);
			list = query.getResultList();
		} finally {
			if (em != null) { em.close(); }
		}
		return list;
	}
	
	@Override
	public List<Object[]> getDashboardSampleDetailDataForGRN(Long locId,Integer noOfDays,String empId,String grn_no,String product_code,String brand_name) throws Exception {
		EntityManager em = null;
		List<Object[]> list= null;
		try {
			System.out.println("locId "+locId);
			System.out.println("noOfDays "+noOfDays);
			System.out.println("empId "+empId);
			System.out.println("grn_no "+grn_no);
			System.out.println("product_code "+product_code);
			System.out.println("brand_name "+brand_name);
			em = emf.createEntityManager();
			StoredProcedureQuery query = em.createStoredProcedureQuery("DBOARD_GRAPH_LASTGRN_RECD_BRANDWISE_QTY_SAMPLE_GRNWISE");

			query.registerStoredProcedureParameter(1, Long.class, ParameterMode.IN);
			query.registerStoredProcedureParameter(2, Integer.class, ParameterMode.IN);
			query.registerStoredProcedureParameter(3, String.class, ParameterMode.IN);
			query.registerStoredProcedureParameter(4, String.class, ParameterMode.IN);
			query.registerStoredProcedureParameter(5, String.class, ParameterMode.IN);
			query.registerStoredProcedureParameter(6, String.class, ParameterMode.IN);

			query.setParameter(1, locId);
			query.setParameter(2, noOfDays);
			query.setParameter(3, empId);
			query.setParameter(4, grn_no);
			query.setParameter(5, product_code);
			query.setParameter(6, brand_name);
			list = query.getResultList();
		} finally {
			if (em != null) { em.close(); }
		}
		return list;
	}
	@Override
	public List<Object[]> getDashboardSampleDataForGRN(Long locId,Integer noOfDays,String empId) throws Exception {
		EntityManager em = null;
		List<Object[]> list= null;
		try {
			em = emf.createEntityManager();
//			StringBuffer sb = new StringBuffer();
//			sb.append(" SELECT SI.SMP_PROD_NAME,GH.GRN_PO_NO,SI.SMP_ERP_PROD_CD FCODE,UPPER(SPG.SA_GROUP_NAME) SA_GROUP_NAME ,");
//			sb.append(" SUM(GRND_QTY) AS GRN_QTY FROM GRN_DETAILS GD JOIN GRN_HEADER GH ON GH.GRN_ID=GD.GRND_GRN_ID ");
//			sb.append("  JOIN SMPITEM SI ON SI.SMP_PROD_ID=GD.GRND_PROD_ID ");
//			sb.append("  JOIN SAPRODGRP SPG ON SPG.SA_PROD_GROUP=SI.SMP_SA_PROD_GROUP ");
//			sb.append("  JOIN SG_d_parameters_details sgd  on sgd.SGprmdet_id=si.PROD_SUB_TYPE_ID ");
//			sb.append("  join sg_m_parameters sgm on sgm.SGprm_id = sgd.SGprmdet_SGprm_id ");
//			sb.append("  where upper(sgm.SGprm_desc)='PRODUCT_SUB_TYPE' ");
//			sb.append("  AND  GRN_DT>=CONVERT(DATE,GETDATE()-7) ");
//			sb.append(" and SGD.sgprmdet_disp_nm in ");
//			sb.append(" ('Regular PS', ");
//			sb.append("  'Cold Chain PS', ");
//			sb.append(" 'Cold Chain PI') ");
//			sb.append("  AND GH.GRN_APPR_STATUS='A' AND GH.GRN_STATUS='A' AND GD.GRND_STATUS='A'  ");
//			sb.append("  AND GD.GRND_APPR_STATUS='A' AND GH.GRN_LOC_ID=1 ");
//			sb.append("  GROUP BY SI.SMP_PROD_NAME,GH.GRN_PO_NO,SI.SMP_ERP_PROD_CD,UPPER(SPG.SA_GROUP_NAME) ORDER BY SI.SMP_PROD_NAME,SA_GROUP_NAME ");
//			
//			Query query = em.createNativeQuery(sb.toString());
//			list = query.getResultList();
			
			StoredProcedureQuery query = em.createStoredProcedureQuery("DBOARD_GRAPH_LASTGRN_RECD_BRANDWISE_QTY_SAMPLE");

			query.registerStoredProcedureParameter(1, Long.class, ParameterMode.IN);
			query.registerStoredProcedureParameter(2, Integer.class, ParameterMode.IN);
			query.registerStoredProcedureParameter(3, String.class, ParameterMode.IN);

			query.setParameter(1, locId);
			query.setParameter(2, noOfDays);
			query.setParameter(3, empId);
			list = query.getResultList();
		} finally {
			if (em != null) { em.close(); }
		}
		return list;
	}

	
	@Override
	public List<DashboardChartsWithSku> getDashboardPlanDataWithSku(Long locId,String empId,String finyr,String periodCd,String div_id,Long brandid) throws Exception {
		EntityManager em = null;
		List<DashboardChartsWithSku> list= null;
		System.out.println("locId "+locId);
		System.out.println("empId "+empId);
		System.out.println("finyr "+finyr);
		System.out.println("periodCd "+periodCd);
		System.out.println("div_id "+div_id);
		System.out.println("brandid "+brandid);
		try {
			em = emf.createEntityManager();
			StoredProcedureQuery query  = em.createNamedStoredProcedureQuery("callAnnMonPlanDataWithSku");
			query.setParameter("pLoc_id", locId);
			query.setParameter("puserid", empId);
			query.setParameter("pfinyear", finyr);
			query.setParameter("pperiod_code", periodCd);
			query.setParameter("pdiv_id","0");
			query.setParameter("pbrand_id",brandid.toString());
			 
			list = query.getResultList();
		} finally {
			if (em != null) { em.close(); }
		}
		return list;
	}
	
	@Override
	public List<DashboardCharts2> getDashboardPlanData(Long locId,String empId,String finyr,String periodCd,String div_id) throws Exception {
		EntityManager em = null;
		List<DashboardCharts2> list= null;
		System.out.println("locId "+locId);
		System.out.println("empId "+empId);
		System.out.println("finyr "+finyr);
		System.out.println("periodCd "+periodCd);
		System.out.println("div_id "+div_id);
		try {
			em = emf.createEntityManager();
			StoredProcedureQuery query  = em.createNamedStoredProcedureQuery("callAnnMonPlanData");
			query.setParameter("pLoc_id", locId);
			query.setParameter("puserid", empId);
			query.setParameter("pfinyear", finyr);
			query.setParameter("pperiod_code", periodCd);
			query.setParameter("pdiv_id","0");
			list = query.getResultList();
		} finally {
			if (em != null) { em.close(); }
		}
		return list;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<FsDispatchLandingPage> getFsLandingPageData(String divId, String startPcode, String endPCode,
			String finYr, String repType, String logFsId, String prodId, String dmTer, String psoTer, String self)
			throws Exception {
		List<FsDispatchLandingPage> list = null;
		EntityManager em = null;
		try {
			
			System.out.println("div_id:"+divId);
			System.out.println("stprd:"+startPcode);
			System.out.println("endprd:"+endPCode);
			System.out.println("finyr:"+finYr);
			System.out.println("reptype:"+repType);
			System.out.println("Log_fsid:"+logFsId);
			System.out.println("prodid:"+prodId);
			System.out.println("dmterr:"+dmTer);
			System.out.println("psoterr:"+psoTer);
			System.out.println("self:"+self);
			
			em = emf.createEntityManager();
			StoredProcedureQuery query  = em.createNamedStoredProcedureQuery("CallFsDispatchLandingPage");
			query.setParameter("div_id", divId);
			query.setParameter("stprd", startPcode);
			query.setParameter("endprd", endPCode);
			query.setParameter("finyr", finYr);
			query.setParameter("reptype", repType);
			query.setParameter("Log_fsid", logFsId);
			query.setParameter("prodid", prodId);
			query.setParameter("dmterr", dmTer);
			query.setParameter("psoterr", psoTer);
			query.setParameter("self", self);
			list = query.getResultList();
		} finally {
			if(em != null) { em.close(); }
		}
		if (list != null && !list.isEmpty()) {
			return list;
		}
		return null;
	}

	@Override
	public List<DashboardBean> getFsDetailsByEmpId(String empId) throws Exception {
		List<DashboardBean> list = new ArrayList<DashboardBean>();
		EntityManager em = null;
		try {
			em = emf.createEntityManager();
			StringBuffer sb = new StringBuffer();
			sb.append("select f.fstaff_terrname,f.fstaff_div_id,f.fstaff_id,f.FSTAFF_NAME,f.FSTAFF_LEVEL_CODE,d.DIV_DISP_NM,f.FSTAFF_DESIG,f.FSTAFF_MOBILE,f.FSTAFF_ADDR1, ");
			sb.append("f.FSTAFF_ADDR2,f.FSTAFF_ADDR3,f.FSTAFF_ADDR4 from FIELDSTAFF f ,hr_m_employee h , ");
			sb.append("DIV_MASTER d where h.emp_id=:empId ");
			sb.append("and f.fstaff_id=h.fstaff_id and d.DIV_ID=f.FSTAFF_DIV_ID ");
			Query query = em.createNativeQuery(sb.toString(), Tuple.class);
			query.setParameter("empId", empId);
			List<Tuple> tuples = query.getResultList();
			DashboardBean d = null;
			if (tuples != null && !tuples.isEmpty()) {
				for(Tuple t : tuples) {
					d=new DashboardBean();
					d.setTerrname(t.get("fstaff_terrname", String.class));
					d.setDivId(Long.valueOf(t.get("fstaff_div_id", Integer.class)));
					d.setFstaff_id(Long.valueOf(t.get("fstaff_id", Integer.class)));
					d.setFname(t.get("FSTAFF_NAME", String.class));
					d.setLevel_code(t.get("FSTAFF_LEVEL_CODE", String.class));
					d.setDiv_nm(t.get("DIV_DISP_NM", String.class));
					d.setDesg(t.get("FSTAFF_DESIG", String.class));
					d.setMobileNo(t.get("FSTAFF_MOBILE", String.class));
					d.setAdd1(t.get("FSTAFF_ADDR1", String.class));
					d.setAdd2(t.get("FSTAFF_ADDR2", String.class));
					d.setAdd3(t.get("FSTAFF_ADDR3", String.class));
					d.setAdd4(t.get("FSTAFF_ADDR4", String.class));
					list.add(d);
				}
			}
		} catch (Exception e) {
			System.out.println("Error Occurred :" +e.getMessage());// uncomment asneeded --;
		} finally {
			if (em != null) { em.close(); }
		}
		return list;

	}

	@Override
	public List<FieldStaff> getFsdetailsByMgrId(String mgrId, String levelCode) throws Exception {
		EntityManager em = null;
		List<FieldStaff> list = new ArrayList<FieldStaff>();
		try {
			em = emf.createEntityManager();
			String q="";
			List<String> items=null;
			if(levelCode.equals("001")) {
			items = Arrays.asList(mgrId.split("\\s*,\\s*"));
			}
			
			if(levelCode.equals("002")) {
				q="select fstaff_id,FSTAFF_NAME,FSTAFF_SAMP_DISP_IND from FIELDSTAFF where FSTAFF_MGR2_ID=:mgrId and FSTAFF_LEVEL_CODE=:level order by FSTAFF_NAME";
			} else {
				q="select fstaff_id,FSTAFF_NAME,FSTAFF_SAMP_DISP_IND from FIELDSTAFF where FSTAFF_MGR1_ID in (:mgrId) and FSTAFF_LEVEL_CODE=:level order by FSTAFF_NAME";
			}
			Query query = em.createNativeQuery(q,Tuple.class);
			if(levelCode.equals("002")) {
				query.setParameter("mgrId", mgrId);
			} else {
				query.setParameter("mgrId", items);
			}
			query.setParameter("level", levelCode);
			List<Tuple> tuples = query.getResultList();
			if (tuples != null && !tuples.isEmpty()) {
				for (Tuple t : tuples) {
					FieldStaff fb=new FieldStaff();
					fb.setFstaff_id(Long.valueOf(t.get("FSTAFF_ID",Integer.class)));
					fb.setFstaff_name(t.get("FSTAFF_NAME",String.class) +" "+ (t.get("FSTAFF_SAMP_DISP_IND",Character.class).equals('Y')?"(ACTIVE)":"(INACTIVE)"));

					list.add(fb);
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
	public List<Period> getPeriodList(String finYear) throws Exception {
		EntityManager em = null;
		List<Period> list = new ArrayList<>();
		Period periodMaster = null;		
		try {
			em = emf.createEntityManager();
			StringBuffer sb = new StringBuffer();
			sb.append(" select period_id,PERIOD_CODE,PERIOD_NAME from PERIOD where PERIOD_FIN_YEAR=:finyr order by period_id");
			Query query = em.createNativeQuery(sb.toString(), Tuple.class);
			query.setParameter("finyr", finYear);
			List<Tuple> tuples = query.getResultList();
			if(tuples != null && !tuples.isEmpty()) {
				for(Tuple t : tuples) {
					periodMaster = new Period();
					periodMaster.setPeriod_code(t.get("PERIOD_CODE", String.class));
					periodMaster.setPeriod_name(t.get("PERIOD_NAME", String.class));
					list.add(periodMaster);
				}
			}
		} finally {
			if(em != null) { em.close(); }
		}
		return list;
	}

	@Override
	public List<DashboardBean> getFsMgrDetails(Long fsId) throws Exception {
		List<DashboardBean> list = new ArrayList<DashboardBean>();
		EntityManager em = null;
		try {
			em = emf.createEntityManager();
			StringBuffer sb = new StringBuffer();
			sb.append(" select f1.fstaff_id as mgr1Id,f1.FSTAFF_NAME as mgr1name,f2.fstaff_id  as mgr2Id,f2.FSTAFF_NAME as mgr2name from FIELDSTAFF f,");
			sb.append(" FIELDSTAFF f1,FIELDSTAFF f2 where f.fstaff_id=:fsId and  f.FSTAFF_MGR2_ID=f1.fstaff_id and  f.FSTAFF_MGR1_ID=f2.fstaff_id ");
			Query query = em.createNativeQuery(sb.toString(), Tuple.class);
			query.setParameter("fsId", fsId);
			List<Tuple> tuples = query.getResultList();
			DashboardBean d = null;
			if (tuples != null && !tuples.isEmpty()) {
				for(Tuple t : tuples) {
					d=new DashboardBean();
					d.setMgr1Id(Long.valueOf(t.get("mgr1Id", Integer.class)));
					d.setMgr1name(t.get("mgr1name", String.class));
					d.setMgr2Id(Long.valueOf(t.get("mgr2Id", Integer.class)));
					d.setMgr2name(t.get("mgr2name", String.class));
					list.add(d);
				}
			}
		} catch (Exception e) {
			System.out.println("Error Occurred :" +e.getMessage());// uncomment asneeded --;
		} finally {
			if (em != null) { em.close(); }
		}
		return list;

	}

	@Override
	public List<DashboardBean> getApprovalTrackerPeriods(String finyr) throws Exception {
		List<DashboardBean> list = new ArrayList<DashboardBean>();
		EntityManager em = null;
		try {
			em = emf.createEntityManager();
			StringBuffer sb = new StringBuffer();
			sb.append(" SELECT * FROM ( ");
			sb.append(" SELECT CONVERT(VARCHAR,GETDATE() - 30,103) PERIOD_START_DATE,CONVERT(VARCHAR,GETDATE(),103) PERIOD_END_DATE");
			sb.append("	UNION ALL");
			sb.append("	SELECT CONVERT(VARCHAR,PERIOD_START_DATE,103) PERIOD_START_DATE, CONVERT(VARCHAR,PERIOD_END_DATE,103) PERIOD_END_DATE");
			sb.append("	FROM PERIOD WHERE PERIOD_FIN_YEAR = :finyr");
			sb.append("	AND PERIOD_START_DATE BETWEEN");
			sb.append("	( DATEADD(DAY,1,EOMONTH((GETDATE() - 90), - 1 )) ) AND ( DATEADD(DAY, 1, EOMONTH(GETDATE(), -1)) )");
			sb.append("	) DT ORDER BY PERIOD_START_DATE DESC");
			Query query = em.createNativeQuery(sb.toString(), Tuple.class);
			query.setParameter("finyr", finyr);
			List<Tuple> tuples = query.getResultList();
			DashboardBean d = null;
			if (tuples != null && !tuples.isEmpty()) {
				for(Tuple t : tuples) {
					d=new DashboardBean();
					d.setSdate(t.get("PERIOD_START_DATE", String.class));
					d.setEdate(t.get("PERIOD_END_DATE", String.class));
					list.add(d);
				}
			}
		} catch (Exception e) {
			System.out.println("Error Occurred :" +e.getMessage());// uncomment asneeded --;
		} finally {
			if (em != null) { em.close(); }
		}
		return list;

	}

	@Override
	public List<ApprovalTrackingData> getApprovalTrackingData(String finyr, String sdate, String edate, String locId,String empId)
			throws Exception {
		EntityManager em = null;
		List<ApprovalTrackingData> list= null;
		try {
			em = emf.createEntityManager();
			StoredProcedureQuery query  = em.createNamedStoredProcedureQuery("callApprovalTracking");
			query.setParameter("FINYEAR", finyr);
			query.setParameter("STARTDT", sdate);
			query.setParameter("ENDDT", edate);
			query.setParameter("LOCID", locId);
			query.setParameter("USRID", empId);
			list = query.getResultList();
		} finally {
			if (em != null) { em.close(); }
		}
		return list;
	}
	
	
	@Override
	public List<SmpItem> getproductbyempid(String log_user_fstaff_id,String fin_yr,String frm_month,String to_month,String levelcode) {
		// TODO Auto-generated method stub
		EntityManager em = null;
		List<SmpItem> list= new ArrayList<>();
		
		try {
			StringBuffer sb = new StringBuffer();
			System.out.println("fs_ID :: "+log_user_fstaff_id);
			
			
//			sb.append("SELECT SMP_PROD_ID,SMP_PROD_NAME FROM ");
//			sb.append("SMPITEM WHERE SMP_STD_DIV_ID IN ( SELECT EDIV_DIV_ID FROM am_m_emp_div_access WHERE EDIV_EMP_ID =:empid ) ");
//			sb.append("AND SMP_STATUS = 'A' ");
//			sb.append("ORDER BY SMP_PROD_NAME ");
			
			sb.append("SELECT PR.SMP_PROD_ID,PR.SMP_PROD_NAME,PR.SMP_STD_DIV_ID ");
			sb.append("FROM SMPITEM PR , DISPATCH_DETAIL DD ,FIELDSTAFF FM ");
			sb.append("WHERE DD.DSPDTL_FIN_YEAR =:finyear ");
			sb.append("AND DD.DSPDTL_PERIOD_CODE >=:frommonth AND DD.DSPDTL_PERIOD_CODE <=:tomonth ");
			sb.append("AND PR.SMP_PROD_ID = DD.DSPDTL_PROD_ID ");
			sb.append("AND DSPDTL_FSTAFF_ID = FM.FSTAFF_ID ");
			
			if(levelcode.equalsIgnoreCase("003")) {
				System.out.println("For RM");
				sb.append("AND FM.FSTAFF_MGR2_ID =:log_user_fstaff_id ");
			}
			else if(levelcode.equalsIgnoreCase("002")) {
				System.out.println("For DM");
				sb.append("AND FSTAFF_MGR1_ID =:log_user_fstaff_id ");
			}
			else if(levelcode.equalsIgnoreCase("001")) {
				System.out.println("For PSO");
				sb.append("AND FSTAFF_ID =:log_user_fstaff_id  ");
			}	
			sb.append("GROUP BY PR.SMP_PROD_ID,PR.SMP_PROD_NAME ,PR.SMP_STD_DIV_ID ");
			sb.append("ORDER BY SMP_PROD_NAME ");
			
			em = emf.createEntityManager();
			Query query = em.createNativeQuery(sb.toString(), Tuple.class);
			query.setParameter("finyear", fin_yr);
			query.setParameter("frommonth", frm_month);
			query.setParameter("tomonth", to_month);
			query.setParameter("log_user_fstaff_id", log_user_fstaff_id);			
			List<Tuple> tuples = query.getResultList();
			System.out.println("prodlist :: "+tuples.size());
			SmpItem item = null;
			if (tuples != null && !tuples.isEmpty()) {
				for(Tuple t : tuples) {
					item = new SmpItem();			
					item.setSmp_prod_id(Long.valueOf(t.get("SMP_PROD_ID",Integer.class).toString()));
					item.setSmp_prod_name(t.get("SMP_PROD_NAME",String.class));
					list.add(item);
				}
				}
			
		}catch (Exception e) {
			// TODO: handle exception
			throw e;
		}
		finally {
			if (em != null) { em.close(); }
		}
		return list;
	}
	
	@Override
	public List<Transporter_master> getTransporterDetails() throws Exception {
		// TODO Auto-generated method stub
		EntityManager em = null;
		List<Transporter_master> list= new ArrayList<>();
		Transporter_master tm = null;
		try {
			em = emf.createEntityManager();
			StringBuffer sb = new StringBuffer();
			sb.append("select transporter_name,trans_pers_resp1,trans_pers_resp2,trans_email1,trans_email2,trans_contact_no1,trans_contact_no2,trans_website_name from transporter_Master");
			Query query = em.createNativeQuery(sb.toString(), Tuple.class);
			List<Tuple> tuples = query.getResultList();
			if(tuples != null && !tuples.isEmpty()) {
				for(Tuple t : tuples) {
					tm = new Transporter_master();
					
					tm.setTransporter_name(t.get("transporter_name",String.class));
					tm.setTrans_pers_resp1(t.get("trans_pers_resp1",String.class));
					tm.setTrans_pers_resp2(t.get("trans_pers_resp2",String.class));
					tm.setTrans_email1(t.get("trans_email1",String.class));
					tm.setTrans_email2(t.get("trans_email2",String.class));
					tm.setTrans_contact_no1(t.get("trans_contact_no1",String.class));
					tm.setTrans_contact_no2(t.get("trans_contact_no2",String.class));
					tm.setTrans_website_name(t.get("trans_website_name",String.class));
					
					list.add(tm);
					}
			}
		}catch (Exception e) {
			// TODO: handle exception
			throw e;
		}
		return list;
	}


}
