package com.excel.repository;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Date;
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

import org.apache.commons.collections4.map.HashedMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.excel.bean.BulkUpldBean;
import com.excel.bean.SpecialAllocationBean;
import com.excel.model.Blk_Challans_Generated_Log;
import com.excel.model.Blk_hcp_req_doctors;
import com.excel.model.Blk_hcp_req_hdr;
import com.excel.model.Blk_hcp_req_products;
import com.excel.model.Blk_hcq_req_temp;
import com.excel.model.Company;
import com.excel.model.SmpItem;
import com.excel.utility.MedicoConstants;

@Repository
public class DoctorBulkAllocUploadRepositoryImpl implements DoctorBulkAllocUploadRepository,MedicoConstants{
	
	@PersistenceContext private EntityManager entityManager;
	@Autowired private EntityManagerFactory emf;
	@Autowired private CompanyMasterRepository comp;

	
	@Override
	public List<SmpItem> getBrands(Long divId, Long prodtype,String userid) throws Exception {
		// TODO Auto-generated method stub
		EntityManager em = null;
		List<SmpItem> list = new ArrayList<SmpItem>();
		try {
			System.out.println("divid"+ divId);
			System.out.println("smpprodtype"+ prodtype);
			
			em = emf.createEntityManager();
			StringBuffer sb = new StringBuffer();
//			sb.append("SELECT PR.SMP_SA_PROD_GROUP,SA.SA_GROUP_NAME as BRAND FROM SMPITEM PR , SAPRODGRP SA");
//			sb.append(" WHERE PR.SMP_status = 'A'");
//			sb.append(" AND PR.SMP_STD_DIV_ID = :divid");
//			sb.append(" AND PR.PROD_SUB_TYPE_ID = :smpprodtype");
//			sb.append(" AND PR.SMP_SA_PROD_GROUP = SA.SA_PROD_GROUP AND SA.SA_status = 'A'");
//			sb.append(" GROUP BY PR.SMP_SA_PROD_GROUP,SA.SA_GROUP_NAME");
//			sb.append(" ORDER BY SA.SA_GROUP_NAME");
			

			 sb.append(" select us.SMP_SA_PROD_GROUP,sg.SA_GROUP_NAME as BRAND ");
			 sb.append(" from am_m_login_detail lg , USER_SKU_MAP us , SAPRODGRP sg , SMPITEM P ");
			 sb.append(" where");
			 sb.append(" lg.ld_emp_cb_id =:userid ");
			 sb.append(" and p.PROD_SUB_TYPE_ID=:smpprodtype");
			 sb.append(" and lg.ld_id = us.USERID ");
			 sb.append(" and us.SMP_SA_PROD_GROUP = sg.SA_PROD_GROUP ");
			 sb.append(" AND us.SMP_SA_PROD_GROUP = P.SMP_SA_PROD_GROUP ");
			 sb.append(" AND (P.SMP_STD_DIV_ID IN (select prod_div_id from ");
			 sb.append(" divmap where base_div_id=:divid ) ");
			 sb.append(" OR P.SMP_STD_DIV_ID=:divid)");
			 sb.append(" group by us.SMP_SA_PROD_GROUP,sg.SA_GROUP_NAME ");
			 sb.append(" order by sg.SA_GROUP_NAME");
			
			Query query = em.createNativeQuery(sb.toString(),Tuple.class);
			query.setParameter("divid", divId);
			query.setParameter("smpprodtype", prodtype);
			query.setParameter("userid",userid);
			List<Tuple> tuples = query.getResultList();
			
			if (tuples != null && !tuples.isEmpty()) {
				list = new ArrayList<>();
				SmpItem object = null;
				for (Tuple t : tuples) {
					object = new SmpItem();
					object.setSmp_sa_prod_group(Long.valueOf(t.get("SMP_SA_PROD_GROUP",Integer.class).toString()));
					object.setSa_group_name(t.get("BRAND",String.class));
					list.add(object);
				}
			if(!list.isEmpty() && list.size() > 0)
				return list;
			}
		
		}
		catch (Exception e) {
			// TODO: handle exception
			throw e;
		}
		finally {
			em.close();
		}
		return list;
	}


	@Override
	public List<SmpItem> getProductsForBulkUpload(Long divId, Long prodtype, String brands) throws Exception {
		// TODO Auto-generated method stub
		EntityManager em = null;
		List<SmpItem> list = new ArrayList<SmpItem>();
		try {
			//String[] arr = brands.split(",");
			
			
			
			em = emf.createEntityManager();
			StringBuffer sb = new StringBuffer();
//			sb.append("SELECT PR.smp_std_div_id,PR.smp_prod_id,PR.smp_prod_cd,PR.smp_erp_prod_cd,PR.smp_prod_name,");
//			sb.append(" PR.smp_prod_type,PR.smp_prod_type_id,PR.smp_sa_prod_group,grp.sa_group_name");
//			sb.append(" FROM SmpItem PR inner join SAPRODGRP grp on PR.smp_sa_prod_group = grp.SA_PROD_GROUP");
//			sb.append(" WHERE PR.smp_status = 'A'");
//			sb.append(" AND PR.smp_std_div_id = :divId");
//			sb.append(" AND PR.prod_sub_type_id = :prodtype");
//			sb.append(" AND PR.smp_sa_prod_group = :prodgroup");
//			sb.append(" ORDER BY PR.smp_prod_name");
			
		sb.append("SELECT SMP_STD_DIV_ID,smp_prod_id, smp_prod_cd,smp_erp_prod_cd, smp_prod_name, smp_prod_type,smp_prod_type_id,");
		sb.append(" sa_group_name,sa_prod_group,");
		sb.append(" Sum(Isnull(stock, 0))   stock_qty");
		sb.append(" FROM(SELECT sm.SMP_STD_DIV_ID, sm.smp_prod_id,sm.smp_prod_cd,sm.smp_erp_prod_cd, sm.smp_prod_name,");
		sb.append(" sm.smp_prod_type,smp_prod_type_id, sm.smp_pack_id,pk.pack_disp_nm, sm.smp_min_alloc_qty,");
		sb.append(" sb.batch_prod_id,( sb.batch_open_stock + sb.batch_in_stock - sb.batch_out_stock - sb.batch_with_held_qty ) stock,");
		sb.append(" GD.in_transit in_transit,spg.sa_group_name, spg.sa_prod_group");
		sb.append(" FROM packmaster AS pk");
		sb.append(" INNER JOIN smpitem AS sm");
		sb.append(" ON pk.pack_id = sm.smp_pack_id");
		sb.append(" LEFT OUTER JOIN smpbatch sb");
		sb.append(" ON sm.smp_prod_id = sb.batch_prod_id");
		sb.append(" AND CONVERT(DATE, Isnull(batch_expiry_dt,'2099-01-01'),105) >CONVERT(DATE,Getdate() + smp_short_expiry_days, 105 )");
		sb.append(" LEFT OUTER JOIN (SELECT D.grnd_prod_id,sum(D.grnd_qty) in_transit");
		sb.append(" FROM   grn_details D,grn_header H");
		sb.append(" WHERE  H.grn_in_transit = 'Y'");
		sb.append(" AND H.grn_id = D.grnd_grn_id");
		sb.append(" GROUP  BY D.grnd_prod_id) GD");
		sb.append(" ON gd.grnd_prod_id = sm.smp_prod_id");
		sb.append(" LEFT OUTER JOIN saprodgrp AS spg");
		sb.append(" ON spg.sa_prod_group = sm.smp_sa_prod_group");
		sb.append(" WHERE  sm.smp_status = 'A'");
		sb.append(" AND sm.prod_sub_type_id = :prodtype");
		sb.append(" AND sm.smp_sa_prod_group in(:prodgroup)");
		sb.append(" AND sm.smp_discont_dt IS NULL");
		sb.append(" AND ( sm.smp_std_div_id = :divId");
		sb.append(" OR sm.smp_std_div_id IN (SELECT dv.prod_div_id");
		sb.append(" FROM   divmap dv");
		sb.append(" WHERE  dv.base_div_id = :divId");
		sb.append(" AND dv.core_ind = 'A') )");
		sb.append(" AND sm.smp_prod_id NOT IN (SELECT prod_id");
		sb.append(" FROM   prod_lock");
		sb.append(" WHERE  team_div_id = :divId)) dt");
		sb.append(" GROUP  BY SMP_STD_DIV_ID,smp_prod_id,");
		sb.append(" smp_prod_cd,smp_erp_prod_cd,");
		sb.append(" smp_prod_name, smp_prod_type,smp_prod_type_id,");
		sb.append(" sa_group_name,");
		sb.append(" sa_prod_group");
		sb.append(" HAVING Sum(stock) != 0 ORDER BY sa_group_name,smp_prod_name");
			
			Query query = em.createNativeQuery(sb.toString(),Tuple.class);
			query.setParameter("divId", divId);
			query.setParameter("prodtype", prodtype);
			query.setParameter("prodgroup", brands);
			List<Tuple> tuples  = query.getResultList();
			
			if (tuples != null && !tuples.isEmpty()) {
				list = new ArrayList<>();
				SmpItem object = null;
				for (Tuple t : tuples) {
					object = new SmpItem();
					object.setSmp_std_div_id(Long.valueOf(t.get("smp_std_div_id",Integer.class).toString()));
					object.setSmp_prod_id(Long.valueOf(t.get("smp_prod_id",Integer.class).toString()));
					object.setSmp_prod_cd(t.get("smp_prod_cd",String.class));
					object.setErp_prod_cd(t.get("smp_erp_prod_cd",String.class));
					object.setSmp_prod_name(t.get("smp_prod_name",String.class));
					object.setSmp_prod_type(t.get("smp_prod_type",String.class));
					object.setSmp_sa_prod_group(Long.valueOf(t.get("sa_prod_group",Integer.class).toString()));
					object.setSa_group_name(t.get("sa_group_name",String.class));
					object.setStockQty(t.get("stock_qty",BigDecimal.class).longValue());	
					list.add(object);
				}
			if(!list.isEmpty() && list.size() > 0)
				return list;
			}
			
			System.out.println("list : "+list.size());
		}
		catch (Exception e) {
			// TODO: handle exception
			throw e;
		}
		finally {
			em.close();
		}
		return list;
	}


	@Override
	public List<Object[]> getHdrAndDocListForCsvGeneration(Long reqId) throws Exception {
		EntityManager em = null;
		List<Object[]> list = new ArrayList<Object[]>();
		try {
			em = emf.createEntityManager();
			StringBuffer sb = new StringBuffer();
			
			sb.append(" SELECT H.BLK_HCP_REQ_ID BULK_UNIQUE_ID,D.REQUESTOR_ID,D.HCP_Unique_ID,D.HCP_Name,");
			sb.append(" D.MCL_NO,D.SRT_Number,D.SRT_Date,D.SRT_Remarks,D.REQ_REMARK,H.COMPANY,DV.DIV_DISP_NM DIVISION,");
			sb.append(" H.BLK_HCP_REQ_NO REQUEST_NO,H.BLK_HCP_REQ_DATE Request_Date,F.FSTAFF_CODE FS_CODE,");
			sb.append(" F.FSTAFF_DISPLAY_NAME FS_Name,' ' Observer1_Email,' ' Observer2_Email,' ' Observer3_Email,");
			sb.append(" ' ' Observer4_Email,' ' Observer5_Email,F.FSTAFF_TERR_ID TERRITORY_ID,F.FSTAFF_TERRNAME TERRITORY_Name,");
			sb.append(" ISNULL(DM.DOC_ADDRESS1,' ' ) Address_1,ISNULL(DM.DOC_ADDRESS2,' ' ) Address_2,");
			sb.append(" ISNULL(DM.DOC_ADDRESS3,' ' ) Address_3,ISNULL(DM.DOC_ADDRESS4,' ' ) Address_4,");
			sb.append(" ISNULL(DM.DOC_CITY,' ' ) City,ISNULL(DM.DOC_PINCODE,' ' ) PIN_code,");
			sb.append(" ISNULL(DM.DOC_PHONE,' ' ) Mobile,ISNULL(DM.DOC_EMAIL,' ' ) HCP_Email,");
			sb.append(" ISNULL(RTRIM(SUBSTRING(POS.POSN_CD,4,10)),'')  MDM_TERR_CODE,ISNULL(F.FSTAFF_TERR_CODE,'') SG_TERR_CODE,D.BLK_REQ_EMPLOYEE_NO,D.BLK_MDM_ADDR_CHG,RTRIM(SUBSTRING(MAP.MDM_EMPLOYEE_NO,1,7)) AS MDM_EMPLOYEE_NO,");
			sb.append("  RTRIM(F.FSTAFF_EMPLOYEE_NO) AS SG_EMPLOYEE_NO,ISNULL(RTRIM(POS.POSN_NM),'') MDM_TERR_NAME");
//			sb.append(" FROM BLK_HCP_REQ_HEADER H INNER JOIN BLK_HCP_REQ_DOCTORS D ON D.BLK_HCP_REQ_ID = H.BLK_HCP_REQ_ID");
//			sb.append(" INNER JOIN DIV_MASTER DV ON DV.DIV_ID = H.DIV_ID INNER JOIN FIELDSTAFF F ON D.REQUESTOR_ID = F.FSTAFF_ID");
//			sb.append(" LEFT OUTER JOIN DOCTOR_MASTER_mdm DM ON DM.HCP_UNIQUE_ID = D.HCP_UNIQUE_ID");
//			sb.append(" LEFT JOIN dg_veeva_pos pos ON RTRIM(SUBSTRING(POS.POSN_CD,4,10))= RTRIM(F.FSTAFF_TERR_CODE)");
//			sb.append(" left join  DOCTOR_MASTER_MDM_TERR_MAP MAP on MAP.party_src_sys_id=dm.hcp_unique_id and MAP.posn_src_sys_id=pos.src_sys_id");
//			sb.append(" WHERE H.BLK_HCP_REQ_ID = :bulkReqHdrId");
			
			sb.append(" FROM BLK_HCP_REQ_HEADER H INNER JOIN BLK_HCP_REQ_DOCTORS D ON D.BLK_HCP_REQ_ID = H.BLK_HCP_REQ_ID");
			sb.append(" INNER JOIN DIV_MASTER DV ON DV.DIV_ID = H.DIV_ID");
			sb.append(" INNER JOIN FIELDSTAFF F ON D.REQUESTOR_ID = F.FSTAFF_ID");
			sb.append(" LEFT OUTER JOIN DOCTOR_MASTER_mdm DM ON DM.HCP_UNIQUE_ID = D.HCP_UNIQUE_ID");
			sb.append(" LEFT JOIN dg_veeva_pos pos ON RTRIM(SUBSTRING(POS.POSN_CD,4,10))= RTRIM(F.FSTAFF_TERR_CODE)");
			sb.append(" JOIN (SELECT SRC_SYS_ID,MAX(SLNO) MAX_SRC_SYS_ID FROM dg_veeva_pos POS1");
			sb.append(" GROUP BY SRC_SYS_ID) POS1 ON POS1.MAX_SRC_SYS_ID = POS.SLNO");
			sb.append(" left join  DOCTOR_MASTER_MDM_TERR_MAP MAP on MAP.party_src_sys_id=d.hcp_unique_id and MAP.posn_src_sys_id=pos.src_sys_id");
			sb.append(" WHERE RTRIM(SUBSTRING(POS.POSN_CD,4,10))= RTRIM(F.FSTAFF_TERR_CODE)");
			sb.append(" AND F.FSTAFF_status = 'A' AND POS.SOURCE = 'PA' AND MAP.SOURCE = 'PA'");
			sb.append(" AND H.BLK_HCP_REQ_ID = :bulkReqHdrId");
			
			Query query = em.createNativeQuery(sb.toString());
			query.setParameter("bulkReqHdrId", reqId);
			
			list  = query.getResultList();
		}
		catch(Exception e) {
			e.printStackTrace();
		}
		finally {
			em.close();
		}
		return list;
	}


	@Override
	public List<Object[]> getProdListForCsvGeneration(Long reqId) throws Exception {
		// TODO Auto-generated method stub
		EntityManager em = null;
		List<Object[]> list = new ArrayList<Object[]>();
		try {
			
			Company company = comp. getCompanyDetails();
			
			String tableName="";
			String coulmnName="";
		if(	company.getStock_at_cfa().equals("Y")) {
			
			tableName="BLK_STK_REQ_PRODUCTS";
			coulmnName="BLK_STK_REQ_ID";
		}else {
			
			tableName="BLK_HCP_REQ_PRODUCTS";
			coulmnName="BLK_HCP_REQ_ID";
			
		}
			
			em = emf.createEntityManager();
			StringBuffer sb = new StringBuffer();
			sb.append(" SELECT PROD_ID,PROD_NAME,SMP_PROD_CD,STD_QTY  FROM "+tableName+" P");
			sb.append(" WHERE P."+coulmnName+" = :bulkReqHdrId");
			
			
			Query query = em.createNativeQuery(sb.toString());
			query.setParameter("bulkReqHdrId", reqId);
			
			list  = query.getResultList();
		}
		catch(Exception e) {
			e.printStackTrace();
		}
		finally {
			em.close();
		}
		return list;
	}


	@Override
	public Blk_hcp_req_hdr getBlkHcpReqHdrById(Long hdrId) throws Exception {
		return this.entityManager.find(Blk_hcp_req_hdr.class, hdrId);
	}


	@Override
	public List<Blk_hcp_req_hdr> getRequestListFromBlkHdr(boolean isgen) throws Exception {
		// TODO Auto-generated method stub
		EntityManager em = null;
		List<Blk_hcp_req_hdr> list = new ArrayList<Blk_hcp_req_hdr>();
		try {
			em = emf.createEntityManager();
			StringBuffer sb = new StringBuffer();
			sb.append(" FROM Blk_hcp_req_hdr where blk_status = 'N' and blk_confirm_ind = 'A'");
			if(isgen) {
				sb.append(" and file_download='Y' and file_upload='Y'"); 
			}
			Query query = em.createQuery(sb.toString());
			
			list  = query.getResultList();
		}
		catch(Exception e) {
			e.printStackTrace();
		}
		finally {
			em.close();
		}
		return list;
	}


	@Override
	public List<Blk_hcq_req_temp> getBlkTempRecordsByBlkHdrId(Long bulkReqId) throws Exception {
		// TODO Auto-generated method stub
		EntityManager em = null;
		List<Blk_hcq_req_temp> list = new ArrayList<Blk_hcq_req_temp>();
		try {
			em = emf.createEntityManager();
			StringBuffer sb = new StringBuffer();
			sb.append(" FROM Blk_hcq_req_temp where blk_hcp_req_id = "+bulkReqId);
			Query query = em.createQuery(sb.toString());
			
			list  = query.getResultList();
		}
		catch(Exception e) {
			e.printStackTrace();
		}
		finally {
			em.close();
		}
		return list;
	}


	@Override
	public SpecialAllocationBean getProductDetails(Long divId, Long prodId, String companyCode) 
			throws Exception {
		EntityManager em = null;
		List<SpecialAllocationBean> list = null;
		try {
			StringBuffer sb = new StringBuffer();
			sb.append(" SELECT SGP.SA_GROUP_NAME,SI.SMP_PROD_ID,CASE WHEN SUBSTRING(SI.SMP_ERP_PROD_CD,1,1) = 'F' THEN SI.SMP_ERP_PROD_CD ELSE SI.SMP_PROD_CD END AS SWH_CODE,SI.SMP_ERP_PROD_CD FCODE,SI.SMP_PROD_NAME,");
			sb.append(" PK.PACK_DISP_NM PACK,SI.SMP_MIN_ALLOC_QTY,SI.SMP_RATE,SI.PROD_THRESHOLD,SI.PROD_HIGHVALUE,SI.PROD_SENSITIVE,SI.SMP_PROD_TYPE,SGD.SGprmdet_disp_nm PROD_SUB_TYPE,");
			sb.append(" CASE WHEN  RTRIM(SI.STORAGE_TYPE)='COLD CHAIN' THEN 'Y' ELSE 'N' END IS_COLD_CHAIN,SI.SMP_STD_DIV_ID ALT_DIV_ID");
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
					object.setSensitive(t.get("PROD_SENSITIVE", Character.class).toString());
					object.setProd_type(t.get("SMP_PROD_TYPE", String.class));
					object.setProd_sub_type(t.get("PROD_SUB_TYPE", String.class));
					object.setColdChain(t.get("IS_COLD_CHAIN", String.class));
					object.setDiv_id(t.get("ALT_DIV_ID", Integer.class).longValue());
					
					list.add(object);
				}
				System.out.println("List"+list.size());
			if(!list.isEmpty() && list.size() > 0)
				return list.get(0);
			}
			
	
		} finally {
			if (em != null) { em.close(); }
		}
		return null;
	}


	
	@Override
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public Map<String, Object> deleteBulkDoctorUploadBeforeModify(Long blkreqHdId) throws Exception {
		// TODO Auto-generated method stub
		Map<String, Object> map = new HashedMap<>();
	//	EntityManager em = null;
		try {
		//	em = emf.createEntityManager();
			StringBuffer sb = new StringBuffer();

			sb.append("delete BLK_HCP_REQ_DOCTORS where BLK_HCP_REQ_ID =:id ");
			Query query = entityManager.createNativeQuery(sb.toString(),Tuple.class);
			query.setParameter("id", blkreqHdId);
			query.executeUpdate();
			
			map.put("msg", "SUCCESS");
		}
		catch (Exception e) {
			// TODO: handle exception
			map.put("msg", "ERROR");
			throw e;
		}
		
		return map;
	}


	@Override
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public Map<String, Object> deleteBulkProductUploadBeforeModify(Long blkreqHdId) throws Exception {
		// TODO Auto-generated method stub
		Map<String, Object> map = new HashedMap<>();
	//	EntityManager em = null;
		try {
		//	em = emf.createEntityManager();
			StringBuffer sb = new StringBuffer();

			sb.append("delete BLK_HCP_REQ_PRODUCTS where BLK_HCP_REQ_ID =:id ");
			Query query = entityManager.createNativeQuery(sb.toString(),Tuple.class);
			query.setParameter("id", blkreqHdId);
			query.executeUpdate();
			
			map.put("msg", "SUCCESS");
		}
		catch (Exception e) {
			// TODO: handle exception
			map.put("msg", "ERROR");
			throw e;
		}
		
		return map;
	}


	@Override
	public Blk_hcp_req_hdr getObjById(Long Id) throws Exception {
		// TODO Auto-generated method stub
		return entityManager.find(Blk_hcp_req_hdr.class,Id);
	}
	
	@Override
	public List<Blk_hcp_req_doctors> getDoctorDetailsById(Long Id) throws Exception {
		// TODO Auto-generated method stub
		EntityManager em = null;
		List<Blk_hcp_req_doctors> list = new ArrayList<Blk_hcp_req_doctors>();
		try {
			em = emf.createEntityManager();
			StringBuffer sb = new StringBuffer();
			
			sb.append("select bld.hcp_name,bld.hcp_unique_id,bld.blk_hcp_req_id,bld.BLK_REQ_EMPLOYEE_NO,bld.blk_hcp_req_no,fs.FSTAFF_NAME,bld.doc_status,");
			sb.append(" bld.srt_number,bld.srt_date,bld.srt_remarks,bld.mcl_no,bld.blk_mdm_addr_chg");
			sb.append(" from BLK_HCP_REQ_DOCTORS bld");
			sb.append(" inner join FIELDSTAFF fs on fs.FSTAFF_ID = bld.REQUESTOR_ID");
			sb.append(" where bld.BLK_HCP_REQ_ID =:id");
			
			Query query = em.createNativeQuery(sb.toString(),Tuple.class);
			query.setParameter("id", Id);
			List<Tuple> tuples  = query.getResultList();
			
			if (tuples != null && !tuples.isEmpty()) {
				list = new ArrayList<>();
				Blk_hcp_req_doctors object = null;
				for (Tuple t : tuples) {
					object = new Blk_hcp_req_doctors();
					object.setHcp_name(t.get("hcp_name",String.class));
					object.setHcp_unique_id(t.get("hcp_unique_id",String.class));
					object.setBlk_hcp_req_id(t.get("blk_hcp_req_id",BigDecimal.class).longValue());
					object.setBlk_hcp_req_no(t.get("blk_hcp_req_no",String.class));
					object.setFsname(t.get("FSTAFF_NAME",String.class));
					object.setDoc_status(t.get("doc_status",Character.class).toString());
					object.setSrt_number(t.get("srt_number",String.class));
					object.setSrt_date(t.get("srt_date",Date.class));
					object.setMcl_no(t.get("mcl_no",String.class));
					object.setSrt_remarks(t.get("srt_remarks",String.class));
					object.setBlk_req_employee_no(t.get("BLK_REQ_EMPLOYEE_NO",String.class));
					object.setBlk_mdm_addr_chg(t.get("blk_mdm_addr_chg",Character.class).toString());
					list.add(object);
				}
			}
		}
		catch(Exception e) {
			throw e;
		}
		finally {
			if(em!=null)em.close();
		}
		return list;
	}


	@Override
	public List<Blk_hcp_req_products> getProductsDetailsById(Long Id) throws Exception {
		// TODO Auto-generated method stub
		List<Blk_hcp_req_products> list = new ArrayList<Blk_hcp_req_products>();
		EntityManager em = null;
		try {
			em = emf.createEntityManager();
			StringBuffer sb = new StringBuffer();
			
//			sb.append("select sm.SMP_PROD_ID,sm.smp_prod_cd,sm.smp_prod_name,sm.smp_prod_type,sag.SA_GROUP_NAME,");
//			sb.append(" blp.STD_QTY,sm.PROD_SUB_TYPE_ID");
//			sb.append(" from SMPITEM sm");
//			sb.append(" inner join BLK_HCP_REQ_PRODUCTS blp ");
//			sb.append(" on sm.SMP_PROD_ID = blp.PROD_ID");
//			sb.append(" inner join SAPRODGRP sag on sag.SA_PROD_GROUP = sm.SMP_SA_PROD_GROUP");
//			sb.append(" where blp.BLK_HCP_REQ_ID = :id");
//			sb.append(" order by sm.smp_prod_name,SA_GROUP_NAME");
			
			sb.append(" select sm.SMP_PROD_ID,sm.smp_prod_cd,sm.smp_prod_name,sm.smp_prod_type,sag.SA_GROUP_NAME, ");
			sb.append(" blp.STD_QTY,sm.PROD_SUB_TYPE_ID,sbstk.stk_qty");
			sb.append(" from SMPITEM sm");
			sb.append(" inner join BLK_HCP_REQ_PRODUCTS blp  ");
			sb.append(" on sm.SMP_PROD_ID = blp.PROD_ID");
			sb.append(" inner join SAPRODGRP sag on sag.SA_PROD_GROUP = sm.SMP_SA_PROD_GROUP");
			sb.append(" inner join (select sb.BATCH_PROD_ID,sum(sb.batch_open_stock + sb.batch_in_stock - sb.batch_out_stock - ");
			sb.append(" sb.batch_with_held_qty) stk_qty from smpbatch sb");
			sb.append(" join smpitem si on si.smp_prod_id=sb.batch_prod_id  ");
			sb.append(" AND CONVERT(DATE, Isnull(batch_expiry_dt,'2099-01-01'), 105) >CONVERT(DATE,Getdate() + smp_short_expiry_days, 105)");
			sb.append(" where sb.batch_status='A'");
			sb.append(" group by sb.BATCH_PROD_ID");
			sb.append(" ) sbstk on batch_prod_id=blp.prod_id");
			sb.append(" where blp.BLK_HCP_REQ_ID = :id");
			sb.append(" order by sm.smp_prod_name,SA_GROUP_NAME");

			Query query = em.createNativeQuery(sb.toString(),Tuple.class);
			query.setParameter("id", Id);
			List<Tuple> tuples  = query.getResultList();
			
			if (tuples != null && !tuples.isEmpty()) {
				list = new ArrayList<>();
				Blk_hcp_req_products object = null;
				for (Tuple t : tuples) {
					object = new Blk_hcp_req_products();
					object.setProd_id(t.get("SMP_PROD_ID",Integer.class).longValue());
					object.setSmp_prod_cd(t.get("smp_prod_cd",String.class));
					object.setProd_name(t.get("smp_prod_name",String.class));
					object.setSmp_prod_type(t.get("smp_prod_type",String.class));
					object.setSa_group_name(t.get("SA_GROUP_NAME",String.class));
					object.setStd_qty(t.get("STD_QTY",BigDecimal.class));
					object.setSmp_prod_type_id(t.get("PROD_SUB_TYPE_ID",Integer.class).longValue());
					object.setStockQty(t.get("stk_qty",BigDecimal.class).longValue());
					list.add(object);
				}
			}
		}
		catch(Exception e) {
			throw e;
		}
		finally {
			if(em!=null)em.close();
		}
		return list;
	}
	
	@Override
	public List<Blk_hcp_req_hdr> getReqNoByStatus(String status,String iscnfrmd) throws Exception {
		// TODO Auto-generated method stub
		List<Blk_hcp_req_hdr> list =new ArrayList<Blk_hcp_req_hdr>();
		try {
			CriteriaBuilder builder = entityManager.getCriteriaBuilder();
			CriteriaQuery<Blk_hcp_req_hdr> criteriaQuery = builder.createQuery(Blk_hcp_req_hdr.class);
			Root<Blk_hcp_req_hdr> root = criteriaQuery.from(Blk_hcp_req_hdr.class);
			criteriaQuery.select(root)//max upload_cycle
			.where(builder.equal(root.get("blk_appr_ind"), status),
					builder.equal(root.get("blk_confirm_ind"), iscnfrmd));
			list=entityManager.createQuery(criteriaQuery).getResultList();
		}
		catch (Exception e) {
			// TODO: handle exception
			throw e;
		}
		return list;
	}
	
	@Override
	public List<Object[]> checkUniqueHcpid(String hcp_unique_id) throws Exception {
		// TODO Auto-generated method stub
		EntityManager em = null;
		List<Object[]> list = new ArrayList<Object[]>();
		try {
			em = emf.createEntityManager();
			StringBuffer sb = new StringBuffer();
			sb.append("select * from DOCTOR_MASTER_MDM where HCP_UNIQUE_ID =:hcp_unique_id");
			Query query = em.createNativeQuery(sb.toString());
			query.setParameter("hcp_unique_id", hcp_unique_id);
			
			list  = query.getResultList();
			
		}
		catch(Exception e) {
			e.printStackTrace();
		}
		finally {
			em.close();
		}
		return list;
	}

	@Override
	public List<Object[]> checkFstaffByEmplNo(String fsEmplNo) throws Exception {
		// TODO Auto-generated method stub
		EntityManager em = null;
		List<Object[]> list = new ArrayList<Object[]>();
		try {
			em = emf.createEntityManager();
			StringBuffer sb = new StringBuffer();
			sb.append("select * from FIELDSTAFF where FSTAFF_EMPLOYEE_NO =:fstaff_empl_no and FS_CATEGORY = 'F' ");
			sb.append(" and FSTAFF_LEVEL_CODE = '001' and FSTAFF_LEAVING_DATE is null and FSTAFF_STATUS='A'");
			Query query = em.createNativeQuery(sb.toString());
			query.setParameter("fstaff_empl_no", fsEmplNo);
			list  = query.getResultList();
			
		}
		catch(Exception e) {
			e.printStackTrace();
		}
		finally {
			em.close();
		}
		return list;
	}


	@Override
	public List<BulkUpldBean> checklinkHcpandFstaff(String hcp_unique_id, String fs_id,String divId) throws Exception {
		// TODO Auto-generated method stub
		EntityManager em = null;
		List<BulkUpldBean> list = new ArrayList<BulkUpldBean>();
		try {
			em = emf.createEntityManager();
			StringBuffer sb = new StringBuffer();
			sb.append("select DM.SLNO AS SLNO,DM.MCL_NO as MCL_NO,DM.HCP_UNIQUE_ID AS HCP_UNIQUE_ID,CONCAT(DM.DOC_PREFIX,CONCAT(' ',DM.DOC_NAME)) ");
			sb.append("DOC_NM, DM.DOC_CITY,DM.DOC_PINCODE,FM.FSTAFF_EMPLOYEE_NO, FM.FSTAFF_DISPLAY_NAME,FM.FSTAFF_CODE ,MAP.FSTAFF_ID,DM.ACTIVE AS MDM_ACTIVE, ");
			sb.append("MAP.PRTYPOS_ACTIVE, FM.FSTAFF_TERRNAME from DOCTOR_MASTER_MDM DM LEFT JOIN DOCTOR_MASTER_MDM_TERR_MAP MAP ");
			sb.append("ON MAP.HCP_UNIQUE_ID=DM.HCP_UNIQUE_ID ,FIELDSTAFF FM ");
			sb.append("where FM.FSTAFF_ID = MAP.FSTAFF_ID AND FM.FSTAFF_DIV_ID = :divId ");
			sb.append("AND FM.FSTAFF_LEVEL_CODE = '001' AND FM.FS_CATEGORY = 'F' AND FM.FSTAFF_status = 'A' AND FM.FSTAFF_LEAVING_DATE is null ");
			sb.append("AND DM.HCP_UNIQUE_ID =:hcp_unique_id AND FM.FSTAFF_EMPLOYEE_NO =:fstaff_empl_no and MAP.PRTYPOS_ACTIVE = 'TRUE' ");
			Query query = em.createNativeQuery(sb.toString(),Tuple.class);
			query.setParameter("hcp_unique_id", hcp_unique_id);
			query.setParameter("fstaff_empl_no", fs_id);
			query.setParameter("divId", divId);
			List<Tuple> tuples = query.getResultList();
			System.out.println("doctorList.size:::"+tuples.size());
			for (Tuple t : tuples) {
				BulkUpldBean blkUpldBn=new BulkUpldBean();
				blkUpldBn.setSlno(Long.valueOf(t.get("SLNO",BigDecimal.class).toString()));
				blkUpldBn.setHcp_unique_id(t.get("HCP_UNIQUE_ID",String.class));
				blkUpldBn.setMcl_no(t.get("MCL_NO",String.class));
				blkUpldBn.setDoc_nm(t.get("DOC_NM",String.class));
				blkUpldBn.setDoc_city(t.get("DOC_CITY",String.class));
				blkUpldBn.setDoc_pincode(t.get("DOC_PINCODE",String.class));
				blkUpldBn.setFstaff_disp_name(t.get("FSTAFF_DISPLAY_NAME",String.class));
				blkUpldBn.setFstaff_code(t.get("FSTAFF_CODE",String.class));
				blkUpldBn.setFstaff_id(Long.valueOf(t.get("FSTAFF_ID",Integer.class).toString()));
				blkUpldBn.setFstaff_employee_no(t.get("FSTAFF_EMPLOYEE_NO",String.class));
				list.add(blkUpldBn);
			}
			
		}
		catch(Exception e) {
			e.printStackTrace();
		}
		finally {
			em.close();
		}
		return list;
	}


	@Override
	public void FinalAllocationApprovalProc(Long reqId, String userid, String Ipaddr) throws Exception {
		// TODO Auto-generated method stub
		try {
			StringBuffer sb = new StringBuffer();
			sb.append("exec ALLOCATION_FINAL_APPROVAL_DIRECT_TO_DOCTOR_BULK :blk_hcp_req_id,:userid,:user_ip");
			Query query = entityManager.createNativeQuery(sb.toString());
			query.setParameter("blk_hcp_req_id", reqId);
			query.setParameter("userid", userid.trim());
			query.setParameter("user_ip", Ipaddr.trim());
			query.executeUpdate();
		}
		catch (Exception e) {
			// TODO: handle exception
			throw e;
		}
	}


	@Override
	public List<Blk_hcp_req_hdr> getRequestListFromBlkHdrForUploaded(Long divId) throws Exception {
		// TODO Auto-generated method stub
		EntityManager em = null;
		List<Blk_hcp_req_hdr> hdrlist = new ArrayList<Blk_hcp_req_hdr>();
		try {
			em = emf.createEntityManager();
			StringBuffer sb = new StringBuffer();
		//	sb.append(" FROM Blk_hcp_req_hdr where blk_status = 'Y' and div_id=:divid");
			sb.append(" select BH_REQ_NO+' ( '+rtrim(convert(varchar, TOTAL_REQ-ISNULL(DSP_COUNT,0)))+' ) ' as BH_REQ_NO,AH_BLK_REQ_ID,");
			sb.append(" TOTAL_REQ-ISNULL(DSP_COUNT,0) AS PENDING");
			sb.append(" FROM(");
			sb.append(" select bh.BLK_HCP_REQ_NO BH_REQ_NO,ah.BLK_HCP_REQ_ID AH_BLK_REQ_ID,");
			sb.append(" count(*) AS TOTAL_REQ from ALLOC_header ah");
			sb.append(" join  BLK_HCP_REQ_HEADER  bh on bh.BLK_HCP_REQ_ID=ah.BLK_HCP_REQ_ID");
			sb.append(" WHERE  ISNULL(ah.BLK_HCP_REQ_ID,0) <>0 and ah.ALLOC_APPR_STATUS='A'");
			sb.append(" AND AH.ALLOC_TYPE='D'");
			sb.append(" AND BH.DIV_ID=:divId");
			sb.append(" group by  bh.BLK_HCP_REQ_NO,ah.BLK_HCP_REQ_ID");
			sb.append(" )DTL");
			sb.append(" LEFT JOIN");
			sb.append(" (");
			sb.append(" SELECT DH.BLK_HCP_REQ_ID , COUNT(*) DSP_COUNT");
			sb.append(" FROM DISPATCH_HEADER DH WHERE DH.BLK_HCP_REQ_ID<>0");
			sb.append(" AND DH.DSP_DIV_ID=:divId");
			sb.append(" GROUP BY DH.BLK_HCP_REQ_ID");
			sb.append(" )DH  ON DH.BLK_HCP_REQ_ID =  DTL.ah_blk_req_id");
			sb.append(" where TOTAL_REQ-ISNULL(DSP_COUNT,0)>0");
			
			Query query = em.createNativeQuery(sb.toString(),Tuple.class);
			query.setParameter("divId", divId);
			List<Tuple> list  = query.getResultList();
			if(list!=null && !list.isEmpty()) {
				Blk_hcp_req_hdr hdr= null;
				for(Tuple t :list) {
					hdr = new Blk_hcp_req_hdr(); 
					hdr.setBlk_hcp_req_no(t.get("BH_REQ_NO",String.class));
					hdr.setBlk_hcp_req_id(t.get("AH_BLK_REQ_ID",BigDecimal.class).longValue());
					hdrlist.add(hdr);
				}
			}
		}
		catch(Exception e) {
			e.printStackTrace();
		}
		finally {
			em.close();
		}
		return hdrlist;
	}


	@Override
	public List<Blk_hcq_req_temp> getBulkTempByBlkHdrId(Long bulkHdrId) throws Exception {
		// TODO Auto-generated method stub
		List<Blk_hcq_req_temp> bulkTempObj = null;
		EntityManager em = null;
		List<Blk_hcp_req_hdr> list = new ArrayList<Blk_hcp_req_hdr>();
		try {
			em = emf.createEntityManager();
			StringBuffer sb = new StringBuffer();
			sb.append(" FROM Blk_hcq_req_temp where blk_hcp_req_id = "+bulkHdrId);
			Query query = em.createQuery(sb.toString());
			
			bulkTempObj  =  query.getResultList();
		}
		catch(Exception e) {
			e.printStackTrace();
		}
		finally {
			em.close();
		}
		return bulkTempObj;
	}


	@Override
	public Blk_hcp_req_hdr getBulkRequestHdrByReqNo(String reqNo) throws Exception {
		Blk_hcp_req_hdr hdr =null;
		try {
			CriteriaBuilder builder = entityManager.getCriteriaBuilder();
			CriteriaQuery<Blk_hcp_req_hdr> criteriaQuery = builder.createQuery(Blk_hcp_req_hdr.class);
			Root<Blk_hcp_req_hdr> root = criteriaQuery.from(Blk_hcp_req_hdr.class);
			criteriaQuery.select(root)//max upload_cycle
			.where(builder.equal(root.get("blk_hcp_req_no"), reqNo));
			hdr=entityManager.createQuery(criteriaQuery).getSingleResult();
		}
		catch (Exception e) {
			// TODO: handle exception
			throw e;
		}
		return hdr;
		
	}


	@Override
	public List<Object[]> getGeneratedRequestList(Long blkReqId) throws Exception {
		// TODO Auto-generated method stub
		List<Object[]> lst =null;
		try {
			StringBuffer sb = new StringBuffer();
			sb.append("select ah.BLK_HCP_REQ_ID,  MIN(ah.request_no) AS from_req_no, max(ah.request_no) AS TO_REQ_NO from ALLOC_REQ_DET ad");
			sb.append(" left join  ALLOC_REQ_HDR ah on ah.alloc_req_id=ad.alloc_req_id");
			sb.append(" WHERE  ah.BLK_HCP_REQ_ID=:blkreqId");
			sb.append(" group by  ah.BLK_HCP_REQ_ID");
			
			Query query = entityManager.createNativeQuery(sb.toString());
			query.setParameter("blkreqId", blkReqId);
			
			lst = query.getResultList();
		}
		catch (Exception e) {
			// TODO: handle exception
			throw e;
		}
		
		return lst;
	}


	@Override
	public List<Object[]> checkFstaffByEmployeeNoAndTerritoryCode(String fs_empl_no,
			String terr_code,String upldType) throws Exception {
		// TODO Auto-generated method stub
		EntityManager em = null;
		List<Object[]> list = new ArrayList<Object[]>();
		try {
			em = emf.createEntityManager();
			StringBuffer sb = new StringBuffer();
			sb.append("select * from FIELDSTAFF where FSTAFF_EMPLOYEE_NO ='"+fs_empl_no+"' and FS_CATEGORY = 'F' and FSTAFF_STATUS ='A'");
			if(!upldType.equals(N)) {
				sb.append(" and FSTAFF_LEVEL_CODE = '001' ");
			}
			sb.append(" and FSTAFF_LEAVING_DATE is null and FSTAFF_TERR_CODE = '"+terr_code+"'");
			Query query = em.createNativeQuery(sb.toString());
			list  = query.getResultList();
			
		}
		catch(Exception e) {
			e.printStackTrace();
		}
		finally {
			em.close();
		}
		return list;
	}


	@Override
	public List<Object[]> checkFstaffByEmplNoAndDivCheck(String fs_id, String divId,String upldType) throws Exception {
		// TODO Auto-generated method stub
		EntityManager em = null;
		List<Object[]> list = new ArrayList<Object[]>();
		try {
			em = emf.createEntityManager();
			StringBuffer sb = new StringBuffer();
			sb.append("select * from FIELDSTAFF where FSTAFF_EMPLOYEE_NO ='"+fs_id+"' and FS_CATEGORY = 'F' ");
			if(!upldType.equals(N)) {
				sb.append(" and FSTAFF_LEVEL_CODE = '001' ");
			}
			sb.append(" and FSTAFF_LEAVING_DATE is null and FSTAFF_STATUS = 'A' and FSTAFF_DIV_ID ="+divId);
			Query query = em.createNativeQuery(sb.toString());
			list  = query.getResultList();
			
		}
		catch(Exception e) {
			e.printStackTrace();
		}
		finally {
			em.close();
		}
		return list;
	}
	
	
	@Override
	public List<Blk_Challans_Generated_Log> getBulkUploadLogDetailList(String finyr,String periodCd) throws Exception {
		
		EntityManager em = null;
		List<Blk_Challans_Generated_Log> list = new ArrayList<>();
		
		try {
			em = emf.createEntityManager();
			StringBuffer sb = new StringBuffer();
			sb.append(" select ROW_NUMBER() OVER(ORDER BY dsp_fin_year,PR.PERIOD_NAME,DIV_DISP_NM,bl.blk_hcp_req_no,bl.from_challan_no,AH.SRT_NUMBER) AS Row,");
			sb.append(" bl.dsp_fin_year,PR.PERIOD_NAME AS dsp_period_code,");
			sb.append(" dv.div_disp_nm,bl.blk_hcp_req_no,AH.SRT_NUMBER,AH.SRT_DATE,bl.total_requests,");
			sb.append(" bl.prod_type,bl.disp_to_type,bl.from_challan_no,bl.to_challan_no,bl.total_challans");
			sb.append(" from");
			sb.append(" ( select blk_hcp_req_no,SRT_NUMBER,SRT_DATE,1 AS totreq from alloc_header ah");
			sb.append(" where ah.alloc_fin_year=:finyr and ah.alloc_period_code=:periodCode");
			sb.append(" and ( ah.blk_hcp_req_no is not null and ah.blk_hcp_req_no <>''))ah");
			sb.append(" left join BLK_CHALLANS_GENERATED_LOG bl on bl.blk_hcp_req_NO=ah.blk_hcp_req_NO");
			sb.append(" left join div_master dv on dv.div_id=bl.dsp_div_id,");
			sb.append(" v_dispatch_period pr");
			sb.append(" where BL.DSP_FIN_YEAR=:finyr AND BL.DSP_PERIOD_CODE=:periodCode");
			sb.append(" ORDER BY dsp_fin_year,PR.PERIOD_NAME,DIV_DISP_NM,bl.blk_hcp_req_no,bl.from_challan_no,AH.SRT_NUMBER");
			
			Query query = em.createNativeQuery(sb.toString(),Tuple.class);
			query.setParameter("finyr", finyr);
			query.setParameter("periodCode", periodCd);
			List<Tuple> tuples  = query.getResultList();		
			
			if (tuples != null && !tuples.isEmpty()) {
				list = new ArrayList<>();
				Blk_Challans_Generated_Log object = null;
				
				for (Tuple t : tuples) {
					
					object = new Blk_Challans_Generated_Log();
					
					object.setRow(t.get("row",BigInteger.class).longValue());
					object.setDsp_fin_year(t.get("dsp_fin_year",String.class));
					object.setDsp_period_code(t.get("dsp_period_code",String.class));
					object.setDiv_disp_nm(t.get("div_disp_nm",String.class));
					object.setBlk_hcp_req_no(t.get("blk_hcp_req_no",String.class));
					object.setSrt_number(t.get("srt_number",String.class));
					object.setSrt_date(t.get("srt_date",Date.class));
					
					object.setTotal_requests(t.get("total_requests",Integer.class).longValue());
					object.setProd_type(t.get("prod_type",String.class));
					object.setDisp_to_type(t.get("disp_to_type",String.class));
					object.setFrom_challan_no(t.get("from_challan_no",String.class));
					object.setTo_challan_no(t.get("to_challan_no",String.class));
					object.setTotal_challans(t.get("total_challans",Integer.class).longValue());
					
					list.add(object);
					
				}
			
			
			}
			
			
		} catch (Exception e) {
			
			e.printStackTrace();
		} finally {
			
			em.close();
		}
		
		
		return list;
	}
}
