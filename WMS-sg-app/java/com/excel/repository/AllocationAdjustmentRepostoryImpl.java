package com.excel.repository;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.StoredProcedureQuery;
import javax.persistence.Tuple;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.excel.bean.AllocationAdjustmentBean;
import com.excel.bean.Parameter;
import com.excel.model.DivMaster;
import com.excel.model.SmpItem;
import com.excel.utility.CodifyErrors;
import com.excel.utility.MedicoConstants;

@Repository
public class AllocationAdjustmentRepostoryImpl implements AllocationAdjustmentRepostory,MedicoConstants{

	@Autowired EntityManagerFactory emf;
	@PersistenceContext private EntityManager entityManager;
	
	@Override
	public List<Parameter> getAllocList(String compcode) {

		EntityManager em = null;
		List<Parameter> list = null;
		try {
			StringBuffer sb = new StringBuffer();
			sb.append(" select smp_id, alloc_policy_name from v_SAMPLE_POLICY_HEADER");
			sb.append(" where smp_id in (select alloc_smp_id from alloc_header CROSS JOIN (select spd_value  " );
			sb.append(" from am_d_system_parameter join am_m_system_parameter on sp_id = spd_sp_id  " );
			sb.append(" and sp_name = 'GENERATE_ALLOCATION_CHECK') X  " );
			sb.append(" where (alloc_status = 'A' or alloc_status = X.spd_value))  " );
			sb.append(" and SMP_ALLOC_PERIOD_CODE = (select PERIOD_CODE from v_allocation_period  " );
			sb.append(" where PERIOD_COMPANY =:companyCode)  and SMP_DISPATCH_PERIOD_CODE =  " );
			sb.append(" (select PERIOD_CODE from V_DISPATCH_PERIOD where PERIOD_COMPANY=:companyCode )" );
			sb.append(" and SMP_COMPANY =:companyCode order by alloc_policy_name  " );
			Query query = entityManager.createNativeQuery(sb.toString(), Tuple.class);
			query.setParameter("companyCode", compcode);
			List<Tuple> tuples = query.getResultList();
			if (tuples != null && !tuples.isEmpty()) {
				list = new ArrayList<>();
				Parameter object = null;
				for (Tuple t : tuples) {
					object = new Parameter();
					object.setId(String.valueOf(t.get("smp_id", Integer.class)));
					object.setName(t.get("alloc_policy_name", String.class));
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
	public List<DivMaster> getDivForAllocAdj(String allocid,String compCode,String empId) {
		EntityManager em = null;
		List<DivMaster> list = null;
		try {
			StringBuffer sb = new StringBuffer();
			
			if(compCode.trim().equals(PAL)) {
				sb.append(" select div_id,DIV_DISP_NM from DIV_MASTER DM");
				sb.append(" JOIN am_m_emp_div_access AA ON EDIV_DIV_ID=DM.DIV_ID");
				sb.append(" where");
				sb.append(" DIV_ID in ( select distinct ALLOCDTL_DIV_ID from ALLOC_DETAIL ad");
				sb.append(" join dbo.Alloc_header ah on ALLOC_ID =ALLOCDTL_ALLOC_ID where ah.ALLOC_SMP_ID =:allocid");
				sb.append(" and ( (ALLOCDTL_CURR_ALLOC_QTY<>ALLOCDTL_SUPPLY_QTY))) ");
				sb.append(" AND  AA.ediv_emp_id=:empId");
				sb.append(" order by DIV_DISP_NM ");
			}
			else {
				sb.append(" select div_id,DIV_DISP_NM from DIV_MASTER where DIV_ID in ( select distinct ALLOCDTL_DIV_ID from ALLOC_DETAIL ad join dbo.Alloc_header ah on ALLOC_ID =ALLOCDTL_ALLOC_ID where ah.ALLOC_SMP_ID =:allocid");
				sb.append("  and ( (ALLOCDTL_CURR_ALLOC_QTY<>ALLOCDTL_SUPPLY_QTY)))  order by DIV_DISP_NM  " );
			}
			Query query = entityManager.createNativeQuery(sb.toString(), Tuple.class);
			if(compCode.trim().equals(PAL)) {
				query.setParameter("allocid", allocid);
				query.setParameter("empId", empId);
			}
			else {
				query.setParameter("allocid", allocid);
			}
			List<Tuple> tuples = query.getResultList();
			if (tuples != null && !tuples.isEmpty()) {
				list = new ArrayList<>();
				DivMaster object = null;
				for (Tuple t : tuples) {
					object = new DivMaster();
					object.setDiv_id(Long.valueOf(t.get("div_id", Integer.class)));
					object.setDiv_disp_nm(t.get("DIV_DISP_NM", String.class));
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
	public List<SmpItem> getProdList(String allocid, String divId) {

		EntityManager em = null;
		List<SmpItem> list = null;
		try {
			StringBuffer sb = new StringBuffer();
			sb.append(" select SMP_PROD_ID,SMP_PROD_NAME from SMPITEM where SMP_PROD_ID in (	select distinct ALLOCDTL_PROD_ID from ALLOC_DETAIL ad join dbo.Alloc_header ah on ALLOC_ID =ALLOCDTL_ALLOC_ID	where ah.ALLOC_SMP_ID =:allocid");
			sb.append(" and ad.ALLOCDTL_DIV_ID=:divId  and ( (ALLOCDTL_CURR_ALLOC_QTY<>ALLOCDTL_SUPPLY_QTY))) order by smp_prod_name " );

			Query query = entityManager.createNativeQuery(sb.toString(), Tuple.class);
			query.setParameter("allocid", allocid);
			query.setParameter("divId", divId);
			List<Tuple> tuples = query.getResultList();
			if (tuples != null && !tuples.isEmpty()) {
				list = new ArrayList<>();
				SmpItem object = null;
				for (Tuple t : tuples) {
					object = new SmpItem();
					object.setSmp_prod_id(Long.valueOf(t.get("SMP_PROD_ID", Integer.class)));
					object.setSmp_prod_name(t.get("SMP_PROD_NAME", String.class));
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
	
	@SuppressWarnings("unused")
	@Override
	public List<Parameter> getFSForAllocAdj(String allocid, String divId,
			String prodId, String alMode, String compcode) {

		System.out.println("allocid :" + allocid);
		System.out.println("divId :" + divId);
		System.out.println("prdId :" + prodId);
		System.out.println("alMode :" + alMode);
		System.out.println("compcode :" + compcode);
		List<Parameter> list =null;
		StringBuffer sql = new StringBuffer();
		try {
			if (alMode.equalsIgnoreCase("1")) {

				sql.append("select distinct DPTDESTINATION_ID,SGprmdet_disp_nm from fieldstaff f join FIELDSTAFF_Detail fd on f.fstaff_id=fd.FSTAFFD_FSTAFF_ID join DEPOT_LOCATIONS on DPTLOC_ID=FSTAFFD_LOC_ID  join SG_d_parameters_details on DPTDESTINATION_ID=SGprmdet_id	where fstaff_div_id =:divId ");
				System.out.println("sql : " + sql);
				Query query = entityManager.createNativeQuery(sql.toString(), Tuple.class);
				query.setParameter("divId", divId);
				List<Tuple> tuples = query.getResultList();
				if (tuples != null && !tuples.isEmpty()) {
					list = new ArrayList<>();
					Parameter object = null;
					for (Tuple t : tuples) {
						object = new Parameter();
						object.setId(String.valueOf(t.get("DPTDESTINATION_ID", Integer.class)));
						object.setName(t.get("SGprmdet_disp_nm", String.class));
						list.add(object);
					}
				}
			} else if (alMode.equalsIgnoreCase("2")) {

				sql.append(" SELECT  SGprmdet_id,  SGprmdet_disp_nm FROM  sg_d_parameters_details WHERE  SGprmdet_SGprm_id = 2 AND SGprmdet_id IN ( SELECT DISTINCT FSTAFF_STATE_ID FROM fieldstaff fs JOIN ALLOC_DETAIL ad ON ad.ALLOCDTL_FSTAFF_ID=fs.fstaff_id JOIN Alloc_header ah ON ah.ALLOC_ID =ad.ALLOCDTL_ALLOC_ID WHERE ah.ALLOC_SMP_ID =:allocId");
				sql.append("  AND ALLOCDTL_DIV_ID =:divId");
				sql.append("  AND ALLOCDTL_PROD_ID =:prodId");
				sql.append(" AND fs.FSTAFF_SAMP_DISP_IND ='Y' AND fs.fstaff_status ='A' AND fs.FSTAFF_LEAVING_DATE IS NULL AND  ( ALLOCDTL_CURR_ALLOC_QTY<>ALLOCDTL_SUPPLY_QTY OR ( ALLOCDTL_ALLOC_QTY>0 and ALLOCDTL_CURR_ALLOC_QTY=0 AND ALLOCDTL_SUPPLY_QTY =0 ))) ORDER BY SGprmdet_disp_nm ");

				System.out.println("sql : " + sql);
				Query query = entityManager.createNativeQuery(sql.toString(), Tuple.class);
				query.setParameter("divId", divId);
				query.setParameter("prodId", prodId);
				query.setParameter("allocId", allocid);
				List<Tuple> tuples = query.getResultList();
				if (tuples != null && !tuples.isEmpty()) {
					list = new ArrayList<>();
					Parameter object = null;
					for (Tuple t : tuples) {
						object = new Parameter();
						object.setId(String.valueOf(t.get("SGprmdet_id", Integer.class)));
						object.setName(t.get("SGprmdet_disp_nm", String.class));
						list.add(object);
					}
				}

			} else if (alMode.equalsIgnoreCase("3") || alMode.equalsIgnoreCase("4") || alMode.equalsIgnoreCase("5")) {

				sql.append(" select fs.fstaff_id,fs.FSTAFF_DISPLAY_NAME from  fieldstaff fs where FSTAFF_ID in ( select  distinct ");
				if (alMode.equalsIgnoreCase("3")) {
					sql.append(" fs.fstaff_mgr2_id ");
				}
				if (alMode.equalsIgnoreCase("4")) {
					sql.append(" fs.fstaff_mgr1_id ");
				}
				if (alMode.equalsIgnoreCase("5")) {
					sql.append(" fs.fstaff_id ");
				}
				sql.append(" from ALLOC_DETAIL ad join dbo.Alloc_header ah on ALLOC_ID =ALLOCDTL_ALLOC_ID join fieldstaff fs on ad.ALLOCDTL_FSTAFF_ID=fs.fstaff_id	where ah.ALLOC_SMP_ID =:allocId");
				sql.append("  AND ALLOCDTL_DIV_ID =:divId ");
				sql.append("  AND ALLOCDTL_PROD_ID =:prodId ");
				sql.append(" and	( (ALLOCDTL_CURR_ALLOC_QTY<>ALLOCDTL_SUPPLY_QTY)   or (ALLOCDTL_ALLOC_QTY>0 and ALLOCDTL_CURR_ALLOC_QTY=0 and ALLOCDTL_SUPPLY_QTY=0))) ");
				sql.append("  ORDER BY fs.FSTAFF_DISPLAY_NAME ASC");

				System.out.println("sql : " + sql);
				Query query = entityManager.createNativeQuery(sql.toString(), Tuple.class);
				query.setParameter("divId", divId);
				query.setParameter("prodId", prodId);
				query.setParameter("allocId", allocid);
				List<Tuple> tuples = query.getResultList();
				if (tuples != null && !tuples.isEmpty()) {
					list = new ArrayList<>();
					Parameter object = null;
					for (Tuple t : tuples) {
						object = new Parameter();
						object.setId(String.valueOf(t.get("fstaff_id", Integer.class)));
						object.setName(t.get("FSTAFF_DISPLAY_NAME", String.class));
						list.add(object);
					}
				}
			}
			System.out.println("List "+list.size());

		} catch (Exception e) {
			System.out.println("Error Occurred :" +e.getMessage());// uncomment asneeded --;
		}
		return list;
	}
	
	

	@Override
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public 	Map<String,Object> saveAdjustment(AllocationAdjustmentBean bean) {	
		String messege = "";
	 	EntityManager em = null;
	 	Map<String,Object> map=new HashMap<>();
	 	System.out.println("piSMP_ID"+bean.getAllocId());
	 	System.out.println("pcType"+ bean.getSaveMode());
	 	System.out.println("pivalue"+bean.getCfalocid());
	 	System.out.println("piadj_val"+bean.getAdjustType());
	 	System.out.println("pvusr_id"+bean.getUserid());
	 	System.out.println("pvip_add"+ bean.getIpAddress());
	 	System.out.println("pcfactor"+ bean.getAdjFctr());
	 	System.out.println("piprod_id"+ bean.getPrdId());
	 	System.out.println("pvdiv_id"+bean.getDivision_id());
		try {
			StoredProcedureQuery query  = entityManager.createNamedStoredProcedureQuery("callAllocationAdjustmentUtil");
			query.setParameter("piSMP_ID",Integer.valueOf(bean.getAllocId()));
			query.setParameter("pcType", bean.getSaveMode().charAt(0));
			query.setParameter("pivalue",Integer.valueOf(bean.getCfalocid()));
			query.setParameter("piadj_val",Integer.valueOf(bean.getAdjustType()));
			query.setParameter("pvusr_id", bean.getUserid());
			query.setParameter("pvip_add", bean.getIpAddress());
			query.setParameter("pcfactor", bean.getAdjFctr().charAt(0));
			query.setParameter("piprod_id",Integer.valueOf(bean.getPrdId()) );
			query.setParameter("pvdiv_id",Integer.valueOf(bean.getDivision_id()));
			query.getResultList();
			map.put(DATA_SAVED, true);
			map.put(RESPONSE_MESSAGE, "Saved Successfully");
		}  catch (Exception e) {
			map.put(RESPONSE_MESSAGE, "Allocation Adjustment Failed");
			map.put(DATA_SAVED, false);
			System.out.println("Error Occurred :" +e.getMessage());// uncomment asneeded --;
	
		}  finally {
			if (em != null) { em.close(); }
		}
	
		return map;
	}
	



}
