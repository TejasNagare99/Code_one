
package com.excel.repository;

import java.math.BigDecimal;
import java.sql.Date;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

import com.excel.bean.AllocationBean;
import com.excel.bean.SpecialAllocationBean;
import com.excel.exception.MedicoException;
import com.excel.model.AllocReqDet;
import com.excel.model.AllocReqDet_;
import com.excel.model.AllocReqHeader;
import com.excel.model.AllocReqImages;
import com.excel.model.AllocReqImages_;
import com.excel.utility.MedicoConstants;


@Repository
public class SpecialAllocationRepositoryImpl implements SpecialAllocationRepository,MedicoConstants {

	@Autowired EntityManagerFactory emf;
	@PersistenceContext private EntityManager entityManager;
	
	@Override
	public AllocReqHeader getObjectById(Long allocId) throws Exception {
		return this.entityManager.find(AllocReqHeader.class, allocId);
	}
	
	@Override
	public AllocReqImages getObjectByImageId(Long imageId) throws Exception {
		EntityManager em = null;
		AllocReqImages list = null;
		try {
			em = emf.createEntityManager();
			CriteriaBuilder builder = em.getCriteriaBuilder();
			CriteriaQuery<AllocReqImages> criteriaQuery = builder.createQuery(AllocReqImages.class);
			Root<AllocReqImages> root = criteriaQuery.from(AllocReqImages.class);
			criteriaQuery.select(root).where(builder.equal(root.get(AllocReqImages_.alloc_req_id), imageId));
			list = em.createQuery(criteriaQuery).getSingleResult();
		} finally {
			if (em != null) { em.close(); }
		}
		return list;
	}
	@Override
	public AllocReqDet getObjectByDetailId(Long detailId) throws Exception {
		return this.entityManager.find(AllocReqDet.class, detailId);
	}
	
	@Override
	public List<AllocReqDet> getDetailListByReqId(Long alloc_req_id) throws Exception {
		EntityManager em = null;
		List<AllocReqDet> list = null;
		try {
			em = emf.createEntityManager();
			CriteriaBuilder builder = em.getCriteriaBuilder();
			CriteriaQuery<AllocReqDet> criteriaQuery = builder.createQuery(AllocReqDet.class);
			Root<AllocReqDet> root = criteriaQuery.from(AllocReqDet.class);
			criteriaQuery.select(root).where(builder.equal(root.get(AllocReqDet_.alloc_req_id), alloc_req_id));
			list = em.createQuery(criteriaQuery).getResultList();
			if(list!=null && list.size()>0)
				return list;
		} finally {
			if (em != null) { em.close(); }
		}
		return list;
	}
	
	
	
	@Override
	public List<Object> getFieldStaffDetails(String empId,Long fs_id) throws Exception{
		EntityManager em = null;
		List<Object> list = null;
		System.out.println("empId "+empId);
		System.out.println("fs_id "+fs_id);
		
		try {
			StringBuffer sb = new StringBuffer();
	       sb.append(" SELECT fsREQ.fstaff_div_id,dv.div_disp_nm,fsreq.fstaff_name fstaff_requestor_name,fsreq.fstaff_id fstaff_requestor_id,");
	       sb.append(" FSREQ.fstaff_map_code1 TERRITORY_CODE,fsreq.fstaff_terr_id,FSREQ.fstaff_terrname FSTAFF_TERRNAME,");
	       sb.append(" tm.terr_code,tm.terr_name,fsreq.fstaff_display_name  reqestor_display_name,fsreq.fstaff_email  requestor_email_id,");
	       sb.append(" Isnull(am.terr_id, 0)  dsm_terr_id,Isnull(am.terr_name, '') dsm_terr_name,Isnull(fsam.fstaff_name, '') fstaff_name_DSM,");
	       sb.append(" Isnull(fsam.fstaff_display_name, '')  fstaff_display_name_DSM,");
	       sb.append(" Isnull(fsam.fstaff_email, '') fstaff_email_DSM,Isnull(rm.terr_id, 0) rm_terr_id,");
	       sb.append(" Isnull(rm.terr_name, '') rm_terr_name,Isnull(fsrm.fstaff_name, '') fstaff_name_RM,");
	       sb.append(" Isnull (fsrm.fstaff_display_name, '') fstaff_display_name_RM,Isnull(fsrm.fstaff_email, '') fstaff_email_RM,");
	       sb.append(" Isnull(zm.terr_id, 0) zm_terr_id,Isnull(zm.terr_name, '') zm_terr_name,Isnull(fszm.fstaff_name, '') fstaff_name_ZM,");
	       sb.append(" Isnull (fszm.fstaff_display_name, '') fstaff_display_name_ZM,Isnull(fszm.fstaff_email, '') fstaff_email_ZM,");
	       sb.append(" Isnull(sm.terr_id, 0) sm_terr_id,Isnull(sm.terr_name, '') sm__terr_name,Isnull(fssm.fstaff_name, '') fstaff_name_SM,");
	       sb.append(" Isnull (fssm.fstaff_display_name, '') fstaff_display_name_SM,Isnull(fssm.fstaff_email, '') fstaff_email_SM,");
	       sb.append(" hrm.emp_id requestor_emp_id,hrm.gco_emp_id requestor_gco_emp_id,");
	       sb.append(" Rtrim(hrmgso.emp_fnm) + ' '+ Rtrim(hrmgso.emp_mnm) + ' '+ Rtrim(hrmgso.emp_lnm) + ' '         AS requestor_GCO_NAME,");
	       sb.append(" hrmgso.emp_email requestor_gco_email,Rtrim(hrmzm.emp_fnm) + ' '+ Rtrim(hrmzm.emp_mnm) + ' '+ Rtrim(hrmzm.emp_lnm) + ' '          AS requestor_ZM_NAME,");
	       sb.append(" hrmzm.emp_email requestor_zm_email,Rtrim(hrmsm.emp_fnm) + ' '+ Rtrim(hrmsm.emp_mnm) + ' '+ Rtrim(hrmsm.emp_lnm) + ' '          AS requestor_SM_NAME,");
	       sb.append(" hrmsm.emp_email requestor_sm_email,Isnull(Rtrim(lc.loc_nm), '') WAREHOUSE,hrm.zm_emp_id requestor_zm_emp_id,hrm.sm_emp_id requestor_sm_emp_id,");
	       sb.append(" HR_RM.rm_emp_id,HR_DM.dm_emp_id");
		   sb.append(" FROM   fieldstaff fsreq");
	       sb.append(" JOIN div_master dv ON dv.div_id = fsREQ.fstaff_div_id");
	       sb.append(" JOIN terr_master tm ON tm.terr_id = fsreq.fstaff_terr_id");
	       sb.append(" LEFT JOIN terr_master am ON am.terr_id = tm.terr_mgr1_id");
	       sb.append(" LEFT JOIN terr_master rm ON rm.terr_id = tm.terr_mgr2_id");
	       sb.append(" LEFT JOIN terr_master zm ON zm.terr_id = tm.terr_mgr3_id");
	       sb.append(" LEFT JOIN terr_master sm ON sm.terr_id = tm.terr_mgr4_id");
	       sb.append(" LEFT JOIN hr_m_employee hrm ON hrm.fstaff_id = fsreq.fstaff_id");
	       sb.append(" LEFT JOIN fieldstaff fsam ON fsam.fstaff_terr_id = am.terr_id");
	       sb.append(" LEFT JOIN fieldstaff fsrm ON fsrm.fstaff_terr_id = rm.terr_id");
	       sb.append(" LEFT JOIN fieldstaff fszm ON fszm.fstaff_terr_id = zm.terr_id");
	       sb.append(" LEFT JOIN fieldstaff fssm ON fssm.fstaff_terr_id = sm.terr_id");
	       sb.append(" LEFT JOIN hr_m_employee hrmgso ON hrmgso.emp_id = hrm.gco_emp_id");
	       sb.append(" LEFT JOIN hr_m_employee hrmzm ON hrmzm.emp_id = hrm.zm_emp_id");
	       sb.append(" LEFT JOIN hr_m_employee hrmsm ON hrmsm.emp_id = hrm.sm_emp_id");
	       sb.append(" LEFT JOIN hr_m_employee hr_rm ON hr_rm.rm_emp_id = HRM.rm_emp_id");
	       sb.append(" LEFT JOIN hr_m_employee hr_DM ON hr_DM.dm_emp_id = HRM.dm_emp_id");
	       sb.append(" LEFT JOIN location lc ON lc.loc_id = hrm.emp_loc_id");
	       sb.append(" where fsreq.fstaff_terr_id<>0");
			 if(empId!=null)
			    sb.append(" and hrm.emp_id=:empId");
			 if(fs_id!=null)
				    sb.append(" and fsreq.fstaff_id=:fs_id");
	       sb.append(" ORDER  BY fsREQ.fstaff_div_id,dv.div_disp_nm");
			Query query = entityManager.createNativeQuery(sb.toString(), Tuple.class);
			if(empId!=null)
				query.setParameter("empId", empId);
			if(fs_id!=null)
				query.setParameter("fs_id", fs_id);
			List<Tuple> tuples = query.getResultList();
			if (tuples != null && !tuples.isEmpty()) {
				list = new ArrayList<>();
				SpecialAllocationBean object = null;
				for (Tuple t : tuples) {
					object = new SpecialAllocationBean();
					object.setDiv_id(Long.valueOf(t.get("fstaff_div_id", Integer.class)));
					object.setDiv_name(t.get("DIV_DISP_NM", String.class));
					object.setFieldStaffId(Long.valueOf(t.get("fstaff_requestor_id", Integer.class)));
					object.setFieldStaffName(t.get("fstaff_requestor_name", String.class));
					object.setStaffTerritoryCode(t.get("TERRITORY_CODE", String.class));
					object.setStaffTerritoryId(Long.valueOf(t.get("fstaff_terr_id", Integer.class)));
					object.setStaffTerritoryName(t.get("FSTAFF_TERRNAME", String.class));
					object.setDsmName(t.get("fstaff_display_name_DSM", String.class));
					object.setDsmEmpId(t.get("DM_EMP_ID", String.class));
					object.setZmName(t.get("requestor_ZM_NAME", String.class));
					object.setZmEmpId(t.get("requestor_zm_emp_id", String.class));
					object.setRmName(t.get("fstaff_display_name_RM", String.class));
					object.setRmEmpId(t.get("RM_EMP_ID", String.class));
					object.setSmName(t.get("requestor_SM_NAME", String.class));
					object.setSmEmpId(t.get("requestor_sm_emp_id", String.class));
					object.setGco(t.get("requestor_GCO_NAME", String.class));
					object.setGcoEmpId(t.get("requestor_gco_emp_id", String.class));
					object.setWareHouse(t.get("WAREHOUSE", String.class));
					object.setWareEmpId("");
					object.setApprovalLevels(t.get("DM_EMP_ID", String.class)+","+t.get("RM_EMP_ID", String.class)+","+t.get("requestor_sm_emp_id", String.class)+","+t.get("requestor_gco_emp_id", String.class));
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
	public List<Object> getDoctorDetails(Long docId) throws Exception{
		EntityManager em = null;
		List<Object> list = null;
		try {
			StringBuffer sb = new StringBuffer();
			sb.append(" select fsREQ.fstaff_div_id,dv.DIV_DISP_NM,fsdoc.DOC_id,fsdoc.DOC_name DOCTOR_NAME,");
			sb.append(" fsdoc.MCL_NO,fsdoc.DOC_ADDRESS1,fsdoc.DOC_ADDRESS2,");
			sb.append(" fsdoc.DOC_ADDRESS3,fsdoc.DOC_ADDRESS4,");
			sb.append(" fsdoc.DOC_CITY,fsdoc.DOC_PINCODE,fsdoc.DOC_PHONE,fsdoc.DOC_EMAIL,");
			sb.append(" fsreq.fstaff_name fstaff_requestor_name,fsreq.fstaff_id fstaff_requestor_id,FSREQ.FSTAFF_MAP_CODE1");
			sb.append(" TERRITORY_CODE,fsreq.fstaff_terr_id,");
			sb.append(" tm.TERR_CODE,tm.TERR_NAME,isnull(am.terr_name,'') dsm_name,isnull(rm.terr_name,'') rm_name,isnull(zm.terr_name,'') zm_name");
			sb.append(" from DOCTOR_MASTER fsdoc");
			sb.append(" join fieldstaff fsreq on  fsdoc.FSTAFF_ID=fsreq.fstaff_id");
			sb.append(" join div_master dv on dv.div_id=fsREQ.fstaff_div_id");
			sb.append(" join terr_master tm on tm.terr_id=fsreq.fstaff_terr_id");
			sb.append(" join terr_master am on am.terr_id=tm.TERR_MGR1_ID");
			sb.append(" join terr_master rm on rm.terr_id=tm.TERR_MGR2_ID");
			sb.append(" left join terr_master zm on zm.terr_id=tm.TERR_MGR3_ID");
			sb.append(" where  fsdoc.FSTAFF_ID<>0 and fsreq.fstaff_terr_id<>0");
			sb.append(" and fsdoc.fstaff_id=:docId");
			sb.append(" order by fsREQ.fstaff_div_id,dv.DIV_DISP_NM,FSDOC.doc_id");
			Query query = entityManager.createNativeQuery(sb.toString(), Tuple.class);
			query.setParameter("docId", docId);
			List<Tuple> tuples = query.getResultList();
			if (tuples != null && !tuples.isEmpty()) {
				list = new ArrayList<>();
				SpecialAllocationBean object = null;
				for (Tuple t : tuples) {
					object = new SpecialAllocationBean();
					object.setDiv_id(Long.valueOf(t.get("fstaff_div_id", Integer.class)));
					object.setDiv_name(t.get("DIV_DISP_NM", String.class));
					object .setRequestorName(t.get("DOCTOR_NAME", String.class));
					object.setMclNo(t.get("MCL_NO", String.class));
					object.setAddress1(t.get("DOC_ADDRESS1", String.class));
					object.setAddress2(t.get("DOC_ADDRESS2", String.class));
					object.setAddress3(t.get("DOC_ADDRESS3", String.class));
					object.setAddress4(t.get("DOC_ADDRESS4", String.class));
					object.setCity(t.get("DOC_CITY", String.class));
					object.setPincode(t.get("DOC_PINCODE", String.class));
					object.setPhone(t.get("DOC_PHONE", String.class));
					object.setEmail(t.get("DOC_EMAIL", String.class));
					list.add(object);
				}
				System.out.println("List"+list.size());
			if(!list.isEmpty() && list.size() > 0)
				return list;
			}
			
	
		} finally {
			if (em != null) { em.close(); }
		}
		return list;
	}
	
	
	@Override
	public List<Object> getProductDetails(Long divId,Long prodId,String companyCode) throws Exception{
		EntityManager em = null;
		List<Object> list = null;
		try {
			StringBuffer sb = new StringBuffer();
			sb.append(" SELECT SGP.SA_GROUP_NAME,SI.SMP_PROD_ID,CASE WHEN SUBSTRING(SI.SMP_ERP_PROD_CD,1,1) = 'F' THEN SI.SMP_ERP_PROD_CD ELSE SI.SMP_PROD_CD END AS SWH_CODE,SI.SMP_ERP_PROD_CD FCODE,SI.SMP_PROD_NAME,");
			sb.append(" PK.PACK_DISP_NM PACK,SI.SMP_MIN_ALLOC_QTY,SI.SMP_RATE,SI.PROD_THRESHOLD,SI.PROD_HIGHVALUE,SI.PROD_SENSITIVE,SI.SMP_PROD_TYPE,SGD.SGprmdet_disp_nm PROD_SUB_TYPE,");
			sb.append(" CASE WHEN  RTRIM(SI.STORAGE_TYPE)='COLD CHAIN' THEN 'Y' ELSE 'N' END IS_COLD_CHAIN");
			sb.append(" FROM SMPITEM SI JOIN SAPRODGRP SGP ON SGP.SA_PROD_GROUP=SI.SMP_SA_PROD_GROUP");
			sb.append(" JOIN PACKMASTER PK ON PK.PACK_ID=SI.SMP_PACK_ID");
			sb.append(" JOIN SG_d_parameters_details SGD ON SGD. SGprmdet_id=SI.PROD_SUB_TYPE_ID");
			sb.append(" WHERE ( SI.SMP_STD_DIV_ID=:divId OR SI.SMP_STD_DIV_ID");
			if(companyCode.trim().equals(PAL)) {
				sb.append(" IN (SELECT PROD_DIV_ID FROM DIVMAP_ES WHERE BASE_DIV_ID=:divId))");
			}
			else {
				sb.append(" IN (SELECT PROD_DIV_ID FROM DIVMAP WHERE BASE_DIV_ID=:divId))");
			}
				//sb.append(" AND SMP_PROD_TYPE='SAMPLE'");
			sb.append(" AND SMP_PROD_ID=:prodId  ");
			sb.append(" ORDER BY SI.SMP_PROD_NAME,SGP.SA_GROUP_NAME");
			Query query = entityManager.createNativeQuery(sb.toString(), Tuple.class);
			query.setParameter("prodId",prodId);
			query.setParameter("divId",divId);
			List<Tuple> tuples = query.getResultList();
			if (tuples != null && !tuples.isEmpty()) {
				list = new ArrayList<>();
				SpecialAllocationBean object = null;
				for (Tuple t : tuples) {
					object = new SpecialAllocationBean();
					object.setBrand_name(t.get("SA_GROUP_NAME", String.class));
					object.setSwh_code(t.get("SWH_CODE", String.class));
					object.setErp_prod_code(t.get("FCODE", String.class));
					object.setProd_id(Long.valueOf(t.get("SMP_PROD_ID", Integer.class)));
					object.setProd_name(t.get("SMP_PROD_NAME", String.class));
					object.setPack_name(t.get("PACK", String.class));
					object.setMin_alloc_qty(t.get("SMP_MIN_ALLOC_QTY", BigDecimal.class).toString());
					object.setSmp_rate(t.get("SMP_RATE", BigDecimal.class));
					object.setThreshold(t.get("PROD_THRESHOLD", String.class));
					object.setHinghvalue(t.get("PROD_HIGHVALUE", Character.class).toString());
					object.setSensitive(t.get("PROD_HIGHVALUE", Character.class).toString()+"/"+t.get("PROD_SENSITIVE", Character.class).toString());
					object.setProd_type(t.get("SMP_PROD_TYPE", String.class));
					object.setProd_sub_type(t.get("PROD_SUB_TYPE", String.class));
					object.setColdChain(t.get("IS_COLD_CHAIN", String.class));
					
					list.add(object);
				}
				System.out.println("List"+list.size());
			if(!list.isEmpty() && list.size() > 0)
				return list;
			}
			
	
		} finally {
			if (em != null) { em.close(); }
		}
		return list;
	}
	
	
	@Override
	public List<SpecialAllocationBean> specialAllocationDetails(Long fs_id,Long requestorId,Long year,String status,String requestType,String recipientStringId){
		EntityManager em = null;
		List<SpecialAllocationBean> list = null;
		System.out.println("fsId "+fs_id);
		System.out.println("requestorId "+requestorId);
		System.out.println("year "+year);
		System.out.println("status "+status);
		System.out.println("requestType "+requestType);
		int i=0;
		try {
			StringBuffer sb = new StringBuffer();
			sb.append(" SELECT ARH.service_req_no, ARH.SRT_NUMBER,ARH.SRT_DATE,ARH.SRT_REMARK,ARH.ALLOC_REQ_ID,convert(varchar(10),ARH.ALLOC_REQ_DATE,103) ALLOC_REQ_DATE ,ARH.FIN_YEAR,");
			sb.append(" ARH.PERIOD_CODE,ARH.COMPANY,ARH.DIVISION,ARH.REQUESTOR_ID,ARH.FS_ID,ARH.TERRITORY_ID,ARH.REQUEST_NO,");
			sb.append(" ARH.ALLOC_TYPE,isnull(ARH.REQUEST_TYPE,'') REQUEST_TYPE,ARH.SUPPLY_DATE ,");
			sb.append(" ARH.MEETING_DATE,isnull(ARH.FREIGHT_TYPE,'') FREIGHT_TYPE,ISNULL(ARH.FREIGHT_VALUE,0) FREIGHT_VALUE,");
			sb.append(" ISNULL(ARH.RECIPIENT_TYPE,'') RECIPIENT_TYPE,ISNULL(ARH.RECIPIENT_NAME,'')  RECIPIENT_NAME,");
			sb.append(" ARH.MCL_NUMBER,ARH.RECIPIENT_ADDRESS1,ARH.RECIPIENT_ADDRESS2,ARH.RECIPIENT_ADDRESS3,ARH.RECIPIENT_ADDRESS4,");
			sb.append(" ARH.RECIPIENT_CITY,ARH.RECIPIENT_PIN,ARH.RECIPIENT_PHONE,ARH.RECIPIENT_EMAIL,ARH.MEETING_VENUE,ARH.MEETING_ADDRESS,");
			sb.append(" ARH.REQ_REMARKS,ARH.REQ_TOTAL_VALUE,ARH.REQ_status,ARH.ALLOC_APPR_STATUS,ARD.ALLOC_REQ_DTL_ID,ARD.ALLOC_REQ_ID ALLOC_REQ_ID_D,");
			sb.append(" ARD.ALLOC_REQ_DATE ALLOC_REQ_DATE_D,ARD.FIN_YEAR FIN_YEAR_D,ARD.PERIOD_CODE PERIOD_CODE_D,ARD.COMPANY COMPANY_D,");
			sb.append(" SI.SMP_MIN_ALLOC_QTY, CASE WHEN  RTRIM(SI.STORAGE_TYPE)='COLD CHAIN' THEN 'Y' ELSE 'N' END IS_COLD_CHAIN,");
			sb.append(" ARD.DIVISION DIVISION_D,ARD.FS_ID FS_ID_D,ARD.TERRITORY_ID TERRITORY_ID_D,ARD.PROD_ID,ARD.PROD_TYPE,ARD.PROD_SUB_TYPE,");
			sb.append(" ARD.PROD_THRESHOLD,ARD.PROD_HIGHVALUE,ARD.PROD_SENSITIVE,ARD.ALT_DIV_ID ALT_DIV_ID_D,ARD.ALLOC_CYCLE ALLOC_CYCLE_D,");
			sb.append(" CONVERT(NUMERIC(12,2),ARD.ALLOC_RATE ) ALLOC_RATE ,CONVERT(NUMERIC(12,2),ARD.ALLOC_QTY) ALLOC_QTY,");
			sb.append(" CONVERT(NUMERIC(12,2),ARD.ALLOC_VALUE) ALLOC_VALUE,ARD.REQDT_status, ");
			sb.append(" fsreq.fstaff_name fstaff_terr_name,fsreq.FSTAFF_DISPLAY_NAME requestor_name,fsreq.fstaff_id");
			sb.append(" fstaff_requester_id,FSREQ.FSTAFF_MAP_CODE1 TERRITORY_CODE,");
			sb.append(" SGP.SA_GROUP_NAME,SI.SMP_PROD_ID,SI.SMP_PROD_CD SWH_CODE,SI.SMP_ERP_PROD_CD FCODE,SI.SMP_PROD_NAME,");
			sb.append(" DV.DIV_DISP_NM,PK.PACK_DISP_NM PACK,ARI.IMAGE_FOLDER,ARI.IMAGE_NAME,ARI.ALLOC_REQ_IMAGE_ID");
			sb.append(" FROM ALLOC_REQ_HDR ARH");
			sb.append(" JOIN ALLOC_REQ_DET ARD ON ARH.ALLOC_REQ_ID=ARD.ALLOC_REQ_ID");
			sb.append(" JOIN FIELDSTAFF fsreq ON  fsreq.FSTAFF_ID=ARH.REQUESTOR_ID");
			sb.append(" JOIN SMPITEM SI ON SI.SMP_PROD_ID=ARD.PROD_ID");
			sb.append(" JOIN SAPRODGRP SGP ON SGP.SA_PROD_GROUP=SI.SMP_SA_PROD_GROUP");
			sb.append(" JOIN DIV_MASTER DV ON DV.DIV_ID=FSREQ.FSTAFF_DIV_ID");
			sb.append(" JOIN PACKMASTER PK ON PK.PACK_ID=SI.SMP_PACK_ID");
			sb.append(" JOIN ALLOC_REQ_IMAGES ARI ON ARI.ALLOC_REQ_ID = ARH.ALLOC_REQ_ID");
			sb.append(" WHERE");
			Query query=null;
			if(status.equals("E")) {
				sb.append(" ARH.FS_ID=:fsId  ");
				sb.append(" AND ARH.REQ_STATUS=:req_status  ");
				sb.append(" AND ARH.REQUESTOR_ID=:requestorId");
				sb.append(" AND ARH.FIN_YEAR=:finYear");
				sb.append(" AND ARH.ALLOC_APPR_STATUS=:status");
				sb.append(" AND ARH.RECIPIENT_TYPE=:recipient_type");
				query = entityManager.createNativeQuery(sb.toString(), Tuple.class);
				query.setParameter("fsId",fs_id);
				query.setParameter("requestorId",requestorId);
				query.setParameter("finYear",year);
				query.setParameter("status",status);
				query.setParameter("recipient_type",recipientStringId);
				query.setParameter("req_status",'A');
			}
			else {
				sb.append(" ARH.ALLOC_REQ_ID=:alloc_req_id  ");
				query = entityManager.createNativeQuery(sb.toString(), Tuple.class);
				query.setParameter("alloc_req_id",fs_id);
			}
			@SuppressWarnings("unchecked")
			List<Tuple> tuples = query.getResultList();
			if (tuples != null && !tuples.isEmpty()) {
				list = new ArrayList<>();
				SpecialAllocationBean object = null;
				for (Tuple t : tuples) {
					object = new SpecialAllocationBean();
					if(i==0) {
						object.setAlloc_req_id(Long.valueOf(t.get("ALLOC_REQ_ID", BigDecimal.class).toString()));
						object.setAlloc_req_date(t.get("ALLOC_REQ_DATE", String.class));
						object.setFinYear(t.get("FIN_YEAR", String.class));
						object.setPeriodCode(t.get("PERIOD_CODE", String.class));
						object.setCompanyCode(t.get("COMPANY", String.class));
						object.setDiv_name(t.get("DIVISION", String.class));
						object.setRequestorId(Long.valueOf(t.get("REQUESTOR_ID", BigDecimal.class).toString()));
						object.setTerritoryId(Long.valueOf(t.get("TERRITORY_ID", BigDecimal.class).toString()));
						object.setRequestNumber(t.get("REQUEST_NO", String.class));
						object.setAllocType(t.get("ALLOC_TYPE", Character.class).toString());
						object.setRequestType(t.get("REQUEST_TYPE", String.class));
						object.setMeetingDate(t.get("MEETING_DATE", Date.class)==null ?"":t.get("MEETING_DATE", Date.class).toString());
						object.setSupplyDate(t.get("SUPPLY_DATE", Date.class)==null ?"":t.get("SUPPLY_DATE", Date.class).toString());
						object.setRecipient_type(t.get("RECIPIENT_TYPE", String.class));
						object.setRecipientName(t.get("RECIPIENT_NAME", String.class));
						object.setMclNo(t.get("MCL_NUMBER", String.class));
						object.setAddress1(t.get("RECIPIENT_ADDRESS1", String.class));
						object.setAddress2(t.get("RECIPIENT_ADDRESS2", String.class));
						object.setAddress3(t.get("RECIPIENT_ADDRESS3", String.class));
						object.setAddress4(t.get("RECIPIENT_ADDRESS4", String.class));
						object.setCity(t.get("RECIPIENT_CITY", String.class));
						object.setPincode(t.get("RECIPIENT_PIN", String.class));
						object.setPhone(t.get("RECIPIENT_PHONE", String.class));
						object.setEmail(t.get("RECIPIENT_EMAIL", String.class));
						object.setTotalValue(t.get("REQ_TOTAL_VALUE", BigDecimal.class));
						object.setDiv_id(Long.valueOf(t.get("ALT_DIV_ID_D", Integer.class)));
						object.setFileName(t.get("IMAGE_NAME", String.class));
						object.setFilePath(t.get("IMAGE_FOLDER", String.class));
						object.setMeetingVenue(t.get("MEETING_VENUE", String.class));
						object.setMeetingAddress(t.get("MEETING_ADDRESS", String.class));
						object.setAlloc_req_date(t.get("ALLOC_REQ_DATE", String.class));
						object.setSrtNo(t.get("SRT_NUMBER", String.class));
						object.setSrtDate(t.get("SRT_DATE", Date.class)==null ? "":t.get("SRT_DATE",Date.class).toString()); //changed
						object.setSrtremark(t.get("SRT_REMARK", String.class));
						object.setServiceNo(t.get("service_req_no",String.class)==null ?"":t.get("service_req_no",String.class));
					}
					
					object.setProd_id(Long.valueOf(t.get("PROD_ID", Integer.class)));
					object.setSwh_code(t.get("SWH_CODE", String.class));
					object.setBrand_name(t.get("SA_GROUP_NAME", String.class));
					object.setMin_alloc_qty(t.get("SMP_MIN_ALLOC_QTY", BigDecimal.class).toString());
					object.setPack_name(t.get("PACK", String.class));
					object.setColdChain(t.get("IS_COLD_CHAIN", String.class));
					object.setSmp_rate(t.get("ALLOC_RATE", BigDecimal.class));
					object.setThreshold(t.get("PROD_THRESHOLD", String.class));
//					object.setSensitive(t.get("PROD_SENSITIVE", Character.class).toString());
					object.setSensitive(t.get("PROD_HIGHVALUE", Character.class).toString()+"/"+t.get("PROD_SENSITIVE", Character.class).toString());
					object.setProd_type(t.get("PROD_TYPE", String.class));
					object.setProd_sub_type(t.get("PROD_SUB_TYPE", String.class));
					object.setProd_name(t.get("SMP_PROD_NAME", String.class));
					object.setAllocQty(t.get("ALLOC_QTY", BigDecimal.class));
					object.setAllocValue(t.get("ALLOC_VALUE", BigDecimal.class));
					object.setDetailId(Long.valueOf(t.get("ALLOC_REQ_DTL_ID", BigDecimal.class).toString()));
					list.add(object);
					
					i++;
				}
				System.out.println("List"+list.size());
			if(!list.isEmpty() && list.size() > 0)
				return list;;
			}
			
	
		} finally {
			if (em != null) { em.close(); }
		}
		return null;
	}
	@Override
	public List<SpecialAllocationBean> specialAllocationDetailsById(Long alloc_req_id,Long finYear,String status){
		EntityManager em = null;
		List<SpecialAllocationBean> list = null;
		System.out.println("alloc_req_id "+alloc_req_id);
		System.out.println("year "+finYear);
		System.out.println("status"+status);
		int i=0;
		try {
			StringBuffer sb = new StringBuffer();
			sb.append(" SELECT ARH.ALLOC_REQ_ID,convert(varchar(10),ARH.ALLOC_REQ_DATE,103) ALLOC_REQ_DATE ,ARH.FIN_YEAR,");
			sb.append(" ARH.PERIOD_CODE,ARH.COMPANY,ARH.DIVISION,ARH.REQUESTOR_ID,ARH.FS_ID,ARH.TERRITORY_ID,ARH.REQUEST_NO,");
			sb.append(" ARH.ALLOC_TYPE,isnull(ARH.REQUEST_TYPE,'') REQUEST_TYPE,convert(varchar(10),ARH.SUPPLY_DATE,103) SUPPLY_DATE ,");
			sb.append(" convert(varchar(10),ARH.MEETING_DATE,103) MEETING_DATE,isnull(ARH.FREIGHT_TYPE,'') FREIGHT_TYPE,ISNULL(ARH.FREIGHT_VALUE,0) FREIGHT_VALUE,");
			sb.append(" ISNULL(ARH.RECIPIENT_TYPE,'') RECIPIENT_TYPE,ISNULL(ARH.RECIPIENT_NAME,'')  RECIPIENT_NAME,");
			sb.append(" ARH.MCL_NUMBER,ARH.RECIPIENT_ADDRESS1,ARH.RECIPIENT_ADDRESS2,ARH.RECIPIENT_ADDRESS3,ARH.RECIPIENT_ADDRESS4,");
			sb.append(" ARH.RECIPIENT_CITY,ARH.RECIPIENT_PIN,ARH.RECIPIENT_PHONE,ARH.RECIPIENT_EMAIL,ARH.MEETING_VENUE,ARH.MEETING_ADDRESS,");
			sb.append(" ARH.REQ_REMARKS,ARH.REQ_TOTAL_VALUE,ARH.REQ_status,ARH.ALLOC_APPR_STATUS,ARD.ALLOC_REQ_DTL_ID,ARD.ALLOC_REQ_ID ALLOC_REQ_ID_D,");
			sb.append(" ARD.ALLOC_REQ_DATE ALLOC_REQ_DATE_D,ARD.FIN_YEAR FIN_YEAR_D,ARD.PERIOD_CODE PERIOD_CODE_D,ARD.COMPANY COMPANY_D,");
			sb.append(" SI.SMP_MIN_ALLOC_QTY, CASE WHEN  RTRIM(SI.STORAGE_TYPE)='COLD CHAIN' THEN 'Y' ELSE 'N' END IS_COLD_CHAIN,");
			sb.append(" ARD.DIVISION DIVISION_D,ARD.FS_ID FS_ID_D,ARD.TERRITORY_ID TERRITORY_ID_D,ARD.PROD_ID,ARD.PROD_TYPE,ARD.PROD_SUB_TYPE,");
			sb.append(" ARD.PROD_THRESHOLD,ARD.PROD_HIGHVALUE,ARD.PROD_SENSITIVE,ARD.ALT_DIV_ID ALT_DIV_ID_D,ARD.ALLOC_CYCLE ALLOC_CYCLE_D,");
			sb.append(" CONVERT(NUMERIC(12,2),ARD.ALLOC_RATE ) ALLOC_RATE ,CONVERT(NUMERIC(12,2),ARD.ALLOC_QTY) ALLOC_QTY,");
			sb.append(" CONVERT(NUMERIC(12,2),ARD.ALLOC_VALUE) ALLOC_VALUE,ARD.REQDT_status,");
			sb.append(" fsreq.fstaff_name fstaff_terr_name,fsreq.FSTAFF_DISPLAY_NAME requestor_name,fsreq.fstaff_id");
			sb.append(" fstaff_requester_id,FSREQ.FSTAFF_MAP_CODE1 TERRITORY_CODE,");
			sb.append(" SGP.SA_GROUP_NAME,SI.SMP_PROD_ID,SI.SMP_PROD_CD SWH_CODE,SI.SMP_ERP_PROD_CD FCODE,SI.SMP_PROD_NAME,");
			sb.append(" DV.DIV_DISP_NM,PK.PACK_DISP_NM PACK,ARI.IMAGE_FOLDER,ARI.IMAGE_NAME,ARI.ALLOC_REQ_IMAGE_ID");
			sb.append(" FROM ALLOC_REQ_HDR ARH");
			sb.append(" JOIN ALLOC_REQ_DET ARD ON ARH.ALLOC_REQ_ID=ARD.ALLOC_REQ_ID");
			sb.append(" JOIN FIELDSTAFF fsreq ON  fsreq.FSTAFF_ID=ARH.REQUESTOR_ID");
			sb.append(" JOIN SMPITEM SI ON SI.SMP_PROD_ID=ARD.PROD_ID");
			sb.append(" JOIN SAPRODGRP SGP ON SGP.SA_PROD_GROUP=SI.SMP_SA_PROD_GROUP");
			sb.append(" JOIN DIV_MASTER DV ON DV.DIV_ID=FSREQ.FSTAFF_DIV_ID");
			sb.append(" JOIN PACKMASTER PK ON PK.PACK_ID=SI.SMP_PACK_ID");
			sb.append(" JOIN ALLOC_REQ_IMAGES ARI ON ARI.ALLOC_REQ_ID = ARH.ALLOC_REQ_ID");
			sb.append(" WHERE");
			sb.append(" ARH.ALLOC_REQ_ID=:alloc_req_id");
		//	sb.append(" AND ARH.ALLOC_APPR_STATUS=:status");
			sb.append(" AND ARH.FIN_YEAR=:finYear");
		    Query query = entityManager.createNativeQuery(sb.toString(), Tuple.class);
				  query.setParameter("alloc_req_id",alloc_req_id);
		//		  query.setParameter("status",status);
				  query.setParameter("finYear",finYear);

			List<Tuple> tuples = query.getResultList();
			if (tuples != null && !tuples.isEmpty()) {
				list = new ArrayList<>();
				SpecialAllocationBean object = null;
				for (Tuple t : tuples) {
					object = new SpecialAllocationBean();
					if(i==0) {
						object.setAlloc_req_id(Long.valueOf(t.get("ALLOC_REQ_ID", BigDecimal.class).toString()));
						object.setAlloc_req_date(t.get("ALLOC_REQ_DATE", String.class));
						object.setFinYear(t.get("FIN_YEAR", String.class));
						object.setPeriodCode(t.get("PERIOD_CODE", String.class));
						object.setCompanyCode(t.get("COMPANY", String.class));
						object.setDiv_name(t.get("DIVISION", String.class));
						object.setRequestorId(Long.valueOf(t.get("REQUESTOR_ID", BigDecimal.class).toString()));
						object.setTerritoryId(t.get("TERRITORY_ID", BigDecimal.class)==null?0l:Long.valueOf(t.get("TERRITORY_ID", BigDecimal.class).toString()));
						object.setRequestNumber(t.get("REQUEST_NO", String.class));
						object.setAllocType(t.get("ALLOC_TYPE", Character.class).toString());
						object.setRequestType(t.get("REQUEST_TYPE", String.class));
						object.setMeetingDate(t.get("MEETING_DATE", String.class));
						object.setSupplyDate(t.get("SUPPLY_DATE", String.class));
						object.setRecipient_type(t.get("RECIPIENT_TYPE", String.class));
						object.setRecipientName(t.get("RECIPIENT_NAME", String.class));
						object.setMclNo(t.get("MCL_NUMBER", String.class));
						object.setAddress1(t.get("RECIPIENT_ADDRESS1", String.class));
						object.setAddress2(t.get("RECIPIENT_ADDRESS2", String.class));
						object.setAddress3(t.get("RECIPIENT_ADDRESS3", String.class));
						object.setAddress4(t.get("RECIPIENT_ADDRESS4", String.class));
						object.setCity(t.get("RECIPIENT_CITY", String.class));
						object.setPincode(t.get("RECIPIENT_PIN", String.class));
						object.setPhone(t.get("RECIPIENT_PHONE", String.class));
						object.setEmail(t.get("RECIPIENT_EMAIL", String.class));
						object.setTotalValue(t.get("REQ_TOTAL_VALUE", BigDecimal.class));
						object.setDiv_id(Long.valueOf(t.get("ALT_DIV_ID_D", Integer.class)));
						object.setFileName(t.get("IMAGE_NAME", String.class));
						object.setFilePath(t.get("IMAGE_FOLDER", String.class));
						object.setFrieghtType(t.get("FREIGHT_TYPE", String.class));
						object.setFrieghtValue(t.get("FREIGHT_VALUE", BigDecimal.class));
						object.setRequestorName(t.get("REQUESTOR_NAME", String.class));
						object.setAddress(object.getAddress1()+" , "+object.getAddress2()+" , "+object.getAddress3()+" , "+object.getAddress4()+" Pincode :"+object.getPincode());
					}
					
					object.setProd_id(Long.valueOf(t.get("PROD_ID", Integer.class)));
					object.setSwh_code(t.get("SWH_CODE", String.class));
					object.setBrand_name(t.get("SA_GROUP_NAME", String.class));
					object.setMin_alloc_qty(t.get("SMP_MIN_ALLOC_QTY", BigDecimal.class).toString());
					object.setPack_name(t.get("PACK", String.class));
					object.setColdChain(t.get("IS_COLD_CHAIN", String.class));
					object.setSmp_rate(t.get("ALLOC_RATE", BigDecimal.class));
					object.setThreshold(t.get("PROD_THRESHOLD", String.class));
					object.setSensitive(t.get("PROD_SENSITIVE", Character.class).toString());
					object.setProd_type(t.get("PROD_TYPE", String.class));
					object.setProd_name(t.get("SMP_PROD_NAME", String.class));
					object.setProd_sub_type(t.get("PROD_SUB_TYPE", String.class));
					object.setAllocQty(t.get("ALLOC_QTY", BigDecimal.class));
					object.setAllocValue(t.get("ALLOC_VALUE", BigDecimal.class));
					object.setDetailId(Long.valueOf(t.get("ALLOC_REQ_DTL_ID", BigDecimal.class).toString()));
					list.add(object);
					
					i++;
				}
				System.out.println("List"+list.size());
			if(!list.isEmpty() && list.size() > 0)
				return list;;
			}
			
	
		} finally {
			if (em != null) { em.close(); }
		}
		return null;
	}
	@Override
	public List<Object> getLevels(Long divId) throws Exception{
		EntityManager em = null;
		List<Object> list = null;
		try {
			StringBuffer sb = new StringBuffer();
			sb.append(" SELECT LEVEL_NAME,LEVEL_CODE,FS_CATEGORY FROM DIV_FIELD WHERE DIV_ID=:divId");
			sb.append(" AND FS_CATEGORY IN ('F','T') ORDER BY FS_CATEGORY,LEVEL_CODE" );
			Query query = entityManager.createNativeQuery(sb.toString(), Tuple.class);
			query.setParameter("divId", divId);
			List<Tuple> tuples = query.getResultList();
			if (tuples != null && !tuples.isEmpty()) {
				list = new ArrayList<>();
				SpecialAllocationBean object = null;
				for (Tuple t : tuples) {
					object = new SpecialAllocationBean();
					object.setLevelcode(t.get("LEVEL_CODE", String.class));
					object.setLevelName(t.get("LEVEL_NAME", String.class));
					list.add(object);
				}
				System.out.println("getTeamWithCount"+list.size());
			if(!list.isEmpty() && list.size() > 0)
				return list;
			}
			
	
		} finally {
			if (em != null) { em.close(); }
		}
		return list;
	}

	@Override
	public String getServiceNumList(String serviceNo) throws Exception {
		// TODO Auto-generated method stub
		EntityManager em = null;
		List<AllocReqHeader> serList = new ArrayList<AllocReqHeader>();
		try {
			em = emf.createEntityManager();
			StringBuffer sb = new StringBuffer();
			sb.append(" select * from ALLOC_REQ_HDR WHERE service_req_no=:serviceNo");
			Query query = em.createNativeQuery(sb.toString(), Tuple.class);
			query.setParameter("serviceNo", serviceNo);
			List<Tuple> tuples = query.getResultList();
			if (tuples != null && !tuples.isEmpty()) {
				for(Tuple t : tuples) {
					AllocReqHeader dm=new AllocReqHeader();
					dm.setService_req_no(t.get("service_req_no", String.class));
					serList.add(dm);
				}
				System.out.println("serviceNo::"+serList.get(0).getService_req_no());
			}		
		}
		catch(Exception e) {
			throw e;
		}
		 finally {
				if (em != null) { em.close(); }
			}
		
		return serList.get(0).getService_req_no();
	}

	@Override
	public List<AllocReqHeader> getAllocReqHdsBy_BlkReqNo(String blkReqNo) throws Exception {
		// TODO Auto-generated method stub
		EntityManager em = null;
		List<AllocReqHeader> serList = new ArrayList<AllocReqHeader>();
		try {
			em = emf.createEntityManager();
			CriteriaBuilder builder = em.getCriteriaBuilder();
			CriteriaQuery<AllocReqHeader> criteriaQuery = builder.createQuery(AllocReqHeader.class);
			Root<AllocReqHeader> root = criteriaQuery.from(AllocReqHeader.class);
			criteriaQuery.select(root).where(builder.equal(root.get("blk_hcp_req_no"), blkReqNo));
			serList = em.createQuery(criteriaQuery).getResultList();
			if(serList!=null && serList.size()>0)
				return serList;
		}catch(Exception e) {
			throw e;
		}
		 finally {
				if (em != null) { em.close(); }
			}
		return null;
	}

	@Override
	public List<SpecialAllocationBean> getExistingSpecialAllocation(String requestType) throws Exception {
		EntityManager em = null;
		List<SpecialAllocationBean> list = null;
		int i=0;
		try {
			StringBuffer sb = new StringBuffer();
			if(requestType.equalsIgnoreCase("M")) {
				sb.append(" SELECT ARH.REQUESTOR_ID, ARH.DIVISION, ARH.TERRITORY_ID, ARH.REQUEST_NO, ARH.RECIPIENT_TYPE, ARH.REQUEST_TYPE, FS.FSTAFF_DISPLAY_NAME, ");
				sb.append(" FS.FSTAFF_LEVEL_CODE, FS.FSTAFF_DESIG, FS.FSTAFF_TERRNAME, FS.FSTAFF_TERR_CODE FROM ALLOC_REQ_HDR ARH JOIN FIELDSTAFF FS ");
				sb.append(" ON ARH.REQUESTOR_ID = FS.FSTAFF_ID WHERE ARH.ALLOC_APPR_STATUS = 'E' AND ");
				sb.append(" ARH.REQUEST_TYPE IN ('E', 'T')  ");
			}else if (requestType.equalsIgnoreCase("D")) {
				sb.append(" SELECT ARH.REQUESTOR_ID, ARH.DIVISION, ARH.TERRITORY_ID, ARH.REQUEST_NO, ARH.RECIPIENT_TYPE, ARH.REQUEST_TYPE, FS.FSTAFF_DISPLAY_NAME, ");
				sb.append(" FS.FSTAFF_LEVEL_CODE, FS.FSTAFF_DESIG, FS.FSTAFF_TERRNAME, FS.FSTAFF_TERR_CODE, ARH.RECIPIENT_NAME, ARH.MCL_NUMBER, ARH.RECIPIENT_PHONE ");
				sb.append(" FROM ALLOC_REQ_HDR ARH JOIN FIELDSTAFF FS ON ARH.REQUESTOR_ID = FS.FSTAFF_ID ");
				sb.append(" WHERE ARH.ALLOC_APPR_STATUS = 'E' AND ARH.REQUEST_TYPE = 'D' ");
			}
			sb.append(" ORDER BY ALLOC_REQ_ID DESC ");
			
		    Query query = entityManager.createNativeQuery(sb.toString(), Tuple.class);


			List<Tuple> tuples = query.getResultList();
			if (tuples != null && !tuples.isEmpty()) {
				list = new ArrayList<>();
				SpecialAllocationBean object = null;
				
				for (Tuple t : tuples) {
					object = new SpecialAllocationBean();
					object.setRequestorId(Long.valueOf(t.get("REQUESTOR_ID", BigDecimal.class).toString()));
					object.setDiv_name(t.get("DIVISION", String.class));
					object.setTerritoryId(Long.valueOf(t.get("TERRITORY_ID", BigDecimal.class).toString()));
					object.setRequestNumber(t.get("REQUEST_NO", String.class));
					object.setRecipient_type(t.get("RECIPIENT_TYPE", String.class));
					
					object.setFieldStaffName(t.get("FSTAFF_DISPLAY_NAME", String.class));
					object.setFstaff_desig(t.get("FSTAFF_DESIG", String.class));
					object.setStaffTerritoryName(t.get("FSTAFF_TERRNAME", String.class));
					object.setStaffTerritoryCode(t.get("FSTAFF_TERR_CODE", String.class));
					object.setRequestType(t.get("REQUEST_TYPE", String.class));
					object.setLevelcode(t.get("FSTAFF_LEVEL_CODE", String.class));
					
					if(requestType.equalsIgnoreCase("D")) {
						object.setRecipientName(t.get("RECIPIENT_NAME", String.class));
						object.setMclNo(t.get("MCL_NUMBER", String.class));
						object.setRecipient_phone(t.get("RECIPIENT_PHONE", String.class));
					}
					
					list.add(object);
				}
				
				System.out.println("List"+list.size());
			if(!list.isEmpty() && list.size() > 0)
				return list;;
			}
			
	
		} finally {
			if (em != null) { em.close(); }
		}
		return null;
	}
	
//	@Override
//	public List<Object> recipientType(){
//		EntityManager em = null;
//		List<Object> list = null;
//		try {
//			StringBuffer sb = new StringBuffer();
//			sb.append(" SELECT SGprmdet_id,SGprmdet_NM,  SGprmdet_disp_nm");
//			sb.append(" FROM  SG_d_parameters_details");
//			sb.append(" JOIN [dbo].[SG_m_parameters] ON SGprmdet_SGprm_id=[SGprm_id]");
//			sb.append(" WHERE SGPRM_DISP_NM='RECIPIENT_TYPE'");
//			sb.append(" AND SGPRMDET_STATUS='A'");
//			sb.append(" ORDER BY  SGprmdet_NM");
//			Query query = entityManager.createNativeQuery(sb.toString(), Tuple.class);
//			List<Tuple> tuples = query.getResultList();
//			if (tuples != null && !tuples.isEmpty()) {
//				list = new ArrayList<>();
//				SpecialAllocationBean object = null;
//				for (Tuple t : tuples) {
//					object = new SpecialAllocationBean();
//					object.setRecipientId(Long.valueOf(t.get("SGprmdet_id", Integer.class)));
//					object.setRecipientType(t.get("SGprmdet_disp_nm", String.class));
//					list.add(object);
//				}
//			if(!list.isEmpty() && list.size() > 0)
//				return list;
//			}
//			
//	
//		} finally {
//			if (em != null) { em.close(); }
//		}
//		return list;
//	}
//	SELECT     SGprmdet_id,SGprmdet_NM,  SGprmdet_disp_nm
//	FROM         SG_d_parameters_details
//	JOIN [dbo].[SG_m_parameters] ON SGprmdet_SGprm_id=[SGprm_id]
//	WHERE SGPRM_DISP_NM='REQUEST_TYPE'
//	AND  SGprmdet_Text1='M'  -- pass M for Special Meeting/Emergency Supply Allocation
//	AND SGPRMDET_STATUS='A'
//	ORDER BY  SGprmdet_disp_nm
}
