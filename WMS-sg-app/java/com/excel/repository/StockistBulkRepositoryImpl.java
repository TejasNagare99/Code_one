package com.excel.repository;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

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

import com.excel.model.BlkStkReqProducts;
import com.excel.model.BlkStkReqTemp;
import com.excel.model.Blk_hcp_req_hdr;
import com.excel.model.Blk_hcq_req_temp;
import com.excel.model.BulkStkReqHeader;
import com.excel.model.BulkStkReqStockists;
import com.excel.model.FieldStaff;
import com.excel.model.FieldStaff_;

@Repository
public class StockistBulkRepositoryImpl implements StockistBulkRepo {

	@Autowired
	EntityManagerFactory emf;
	@PersistenceContext
	private EntityManager entityManager;
	
	SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");

	@Override
	public Optional<FieldStaff> getFieldStaffAttchedToLocation(Long loc_id, Long div_id) throws Exception {
		Optional<FieldStaff> vendor_fstaff = null;
		CriteriaBuilder builder = entityManager.getCriteriaBuilder();
		CriteriaQuery<FieldStaff> query = builder.createQuery(FieldStaff.class);
		Root<FieldStaff> root = query.from(FieldStaff.class);
		query.where(builder.and(builder.equal(root.get(FieldStaff_.fs_div_id), div_id)),
				builder.and(builder.equal(root.get(FieldStaff_.fstaff_loc_id), loc_id)),
				builder.and(builder.equal(root.get(FieldStaff_.fs_category), "F")),
				builder.and(builder.equal(root.get(FieldStaff_.status), "A")));
		vendor_fstaff = Optional.ofNullable(entityManager.createQuery(query).getSingleResult());
		return vendor_fstaff;
	}

	@Override
	public BulkStkReqHeader getObjById(Long Id) throws Exception {
		return entityManager.find(BulkStkReqHeader.class, Id);
	}

	@Override
	public void deleteBulkStockistUploadBeforeModify(Long blkreqHdId) throws Exception {
		try {
			StringBuffer sb = new StringBuffer();
			sb.append("delete BLK_STK_REQ_STOCKISTS where BLK_STK_REQ_ID =:id ");
			Query query = entityManager.createNativeQuery(sb.toString(), Tuple.class);
			query.setParameter("id", blkreqHdId);
			query.executeUpdate();
		} catch (Exception e) {
			throw e;
		}
	}

	@Override
	public void deleteBulkStockistProductUploadBeforeModify(Long blkreqHdId) throws Exception {
		try {
			StringBuffer sb = new StringBuffer();
			sb.append("delete BLK_STK_REQ_PRODUCTS where BLK_STK_REQ_ID =:id ");
			Query query = entityManager.createNativeQuery(sb.toString(), Tuple.class);
			query.setParameter("id", blkreqHdId);
			query.executeUpdate();
		} catch (Exception e) {
			throw e;
		}
	}

	@Override
	public List<BulkStkReqHeader> getReqNoByStatus(String status, String iscnfrmd) throws Exception {
		List<BulkStkReqHeader> list = new ArrayList<BulkStkReqHeader>();
		try {
			CriteriaBuilder builder = entityManager.getCriteriaBuilder();
			CriteriaQuery<BulkStkReqHeader> criteriaQuery = builder.createQuery(BulkStkReqHeader.class);
			Root<BulkStkReqHeader> root = criteriaQuery.from(BulkStkReqHeader.class);
			criteriaQuery.select(root).where(builder.equal(root.get("blkstk_appr_ind"), status),
					builder.equal(root.get("blkstk_confirm_ind"), iscnfrmd));
			list = entityManager.createQuery(criteriaQuery).getResultList();
		} catch (Exception e) {
			throw e;
		}
		return list;
	}

	@Override
	public BulkStkReqHeader getBlkStkReqHdrById(Long hdrId) throws Exception {
		return this.entityManager.find(BulkStkReqHeader.class, hdrId);
	}

	@Override
	public List<Object[]> getProdListForCsvGeneration(Long reqId) throws Exception {
		EntityManager em = null;
		List<Object[]> list = new ArrayList<Object[]>();
		try {
			em = emf.createEntityManager();
			StringBuffer sb = new StringBuffer();
			sb.append(" SELECT PROD_ID,PROD_NAME,SMP_PROD_CD,STD_QTY  FROM BLK_STK_REQ_PRODUCTS P");
			sb.append(" WHERE P.BLK_STK_REQ_ID = :bulkReqHdrId");
			Query query = em.createNativeQuery(sb.toString());
			query.setParameter("bulkReqHdrId", reqId);

			list = query.getResultList();
		} catch (Exception e) {
			throw e;
		} finally {
			if (em != null)
				em.close();
		}
		return list;
	}

	@Override
	public List<Object[]> getHdrAndStksForCsvDownload(Long blkStkReqHdrId) throws Exception {
		EntityManager em = null;
		List<Object[]> list = new ArrayList<Object[]>();
		try {
			em = emf.createEntityManager();
			StringBuffer sb = new StringBuffer();
			sb.append(" select H.BLK_STK_REQ_ID BULK_UNIQUE_ID,H.BLK_STK_REQ_NO,H.BLK_STK_REQ_DATE,CM.CUST_CD SG_CUST_CD,CM.ERP_CUST_CD,");
			sb.append(" CM.CUST_NAME_BILLTO,CM.ENT_CUST_CD,ISNULL(CM.ADDR1_SHIPTO,' ' ) Address_1,");
			sb.append(" ISNULL(CM.ADDR2_SHIPTO,' ' ) Address_2,ISNULL(CM.ADDR3_SHIPTO,' ' ) Address_3,");
			sb.append(" ISNULL(CM.ADDR4_SHIPTO,' ' ) Address_4,ISNULL(CM.DESTINATION_SHIPTO,' ' ) Destination,");
			sb.append(" CM.PIN_CODE PIN_code,ISNULL(CM.MOBILE,' ' ) Mobile,ISNULL(CM.EMAIL,' ' ) STK_Email,");
			sb.append(
					" ' ' Observer1_Email,' ' Observer2_Email,' ' Observer3_Email,' ' Observer4_Email,' ' Observer5_Email,");
			sb.append(" S.SERV_REQ_NO,S.SAP_INV_NO,S.SAP_INV_DATE,S.SAP_SCHM_REMARKS");
			sb.append(
					" from BLK_STK_REQ_HEADER H INNER JOIN BLK_STK_REQ_STOCKISTS S ON S.BLK_STK_REQ_ID = H.BLK_STK_REQ_ID");
			sb.append(" INNER JOIN FIELDSTAFF F ON S.REQUESTOR_ID = F.FSTAFF_ID LEFT OUTER JOIN CUSTOMER_MASTER CM");
			sb.append(" ON CM.CUST_ID = S.CUST_ID WHERE H.BLK_STK_REQ_ID = :stkblkHdrReqId");
			Query query = em.createNativeQuery(sb.toString());
			query.setParameter("stkblkHdrReqId", blkStkReqHdrId);
			list = query.getResultList();
		} catch (Exception e) {
			throw e;
		} finally {
			if (em != null)
				em.close();
		}
		return list;
	}
	
	
	@Override
	public List<BulkStkReqHeader> getRequestListFromBlkStkHdr(boolean isgen) throws Exception {
		EntityManager em = null;
		List<BulkStkReqHeader> list = new ArrayList<BulkStkReqHeader>();
		try {
			em = emf.createEntityManager();
			StringBuffer sb = new StringBuffer();
			sb.append(" FROM BulkStkReqHeader where blkstk_status = 'N' and blkstk_confirm_ind = 'A'");
			if(isgen) {
				sb.append(" and file_download='Y' and file_upload='Y'"); 
			}
			Query query = em.createQuery(sb.toString());
			
			list  = query.getResultList();
		}
		catch(Exception e) {
			e.printStackTrace();
			throw e;
		}
		finally {
			em.close();
		}
		return list;
	}

	@Override
	public List<BlkStkReqTemp> getStkBlkTempRecordsByBlkHdrId(Long bulkReqId) throws Exception {
		// TODO Auto-generated method stub
		EntityManager em = null;
		List<BlkStkReqTemp> list = new ArrayList<BlkStkReqTemp>();
		try {
			em = emf.createEntityManager();
			StringBuffer sb = new StringBuffer();
			sb.append(" FROM BlkStkReqTemp where blk_stk_req_id = "+bulkReqId);
			Query query = em.createQuery(sb.toString());
			
			list  = query.getResultList();
		}
		catch(Exception e) {
			e.printStackTrace();
			throw e;
		}
		finally {
			em.close();
		}
		return list;
	}

	@Override
	public BulkStkReqHeader getStkBulkRequestHdrByReqNo(String reqNo) throws Exception {
		BulkStkReqHeader hdr =null;
		try {
			CriteriaBuilder builder = entityManager.getCriteriaBuilder();
			CriteriaQuery<BulkStkReqHeader> criteriaQuery = builder.createQuery(BulkStkReqHeader.class);
			Root<BulkStkReqHeader> root = criteriaQuery.from(BulkStkReqHeader.class);
			criteriaQuery.select(root)//max upload_cycle
			.where(builder.equal(root.get("blk_stk_req_no"), reqNo));
			hdr=entityManager.createQuery(criteriaQuery).getSingleResult();
		}
		catch (Exception e) {
			// TODO: handle exception
			throw e;
		}
		return hdr;
		
	}

	@Override
	public List<BlkStkReqProducts> getProductsDetailsById(Long Id) throws Exception {
		List<BlkStkReqProducts> list = new ArrayList<BlkStkReqProducts>();
		EntityManager em = null;
		try {
			em = emf.createEntityManager();
			StringBuffer sb = new StringBuffer();
			sb.append(" select sm.SMP_PROD_ID,sm.smp_prod_cd,sm.smp_prod_name,sm.smp_prod_type,sag.SA_GROUP_NAME, ");
			sb.append(" blp.STD_QTY,sm.PROD_SUB_TYPE_ID,sbstk.stk_qty");
			sb.append(" from SMPITEM sm");
			sb.append(" inner join BLK_STK_REQ_PRODUCTS blp  ");
			sb.append(" on sm.SMP_PROD_ID = blp.PROD_ID");
			sb.append(" inner join SAPRODGRP sag on sag.SA_PROD_GROUP = sm.SMP_SA_PROD_GROUP");
			sb.append(" inner join (select sb.BATCH_PROD_ID,sum(sb.batch_open_stock + sb.batch_in_stock - sb.batch_out_stock - ");
			sb.append(" sb.batch_with_held_qty) stk_qty from smpbatch sb");
			sb.append(" join smpitem si on si.smp_prod_id=sb.batch_prod_id  ");
			sb.append(" AND CONVERT(DATE, Isnull(batch_expiry_dt,'2099-01-01'), 105) >CONVERT(DATE,Getdate() + smp_short_expiry_days, 105)");
			sb.append(" where sb.batch_status='A'");
			sb.append(" group by sb.BATCH_PROD_ID");
			sb.append(" ) sbstk on batch_prod_id=blp.prod_id");
			sb.append(" where blp.BLK_STK_REQ_ID = :id");
			sb.append(" order by sm.smp_prod_name,SA_GROUP_NAME");

			Query query = em.createNativeQuery(sb.toString(),Tuple.class);
			query.setParameter("id", Id);
			List<Tuple> tuples  = query.getResultList();
			
			if (tuples != null && !tuples.isEmpty()) {
				list = new ArrayList<>();
				BlkStkReqProducts object = null;
				for (Tuple t : tuples) {
					object = new BlkStkReqProducts();
					object.setProd_id(t.get("SMP_PROD_ID",Integer.class).longValue());
					object.setSmp_prod_cd(t.get("smp_prod_cd",String.class));
					object.setProd_name(t.get("smp_prod_name",String.class));
					object.setSmp_prod_type(t.get("smp_prod_type",String.class));
					object.setSa_group_name(t.get("SA_GROUP_NAME",String.class));
					object.setStd_qty(t.get("STD_QTY",BigDecimal.class));
					object.setSmp_prod_type_id(t.get("PROD_SUB_TYPE_ID",Integer.class).longValue());
					object.setStk_qty(t.get("stk_qty",BigDecimal.class).longValue());
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
	public void FinalAllocationApprovalProc(Long reqId, String userid, String Ipaddr) throws Exception {
		try {
			StringBuffer sb = new StringBuffer();
			sb.append("exec ALLOCATION_FINAL_APPROVAL_DIRECT_TO_STOCKIST_BULK :blk_stk_req_id,:userid,:user_ip");
			Query query = entityManager.createNativeQuery(sb.toString());
			query.setParameter("blk_stk_req_id", reqId);
			query.setParameter("userid", userid.trim());
			query.setParameter("user_ip", Ipaddr.trim());
			query.executeUpdate();
		}
		catch (Exception e) {
			throw e;
		}
	}

	@Override
	public List<BulkStkReqStockists> getBulkStockistDetailsByHdrReqId(Long hdBlkStkId) throws Exception {
		EntityManager em = null;
		List<BulkStkReqStockists> list = new ArrayList<BulkStkReqStockists>();
		try {
			em = emf.createEntityManager();
			StringBuffer sb = new StringBuffer();
			
			sb.append("select bld.cust_id , bld.cust_name, bld.erp_cust_cd,bld.sap_inv_no, bld.sap_inv_date,");
			sb.append(" bld.sap_schm_remarks");
			sb.append(" from BLK_STK_REQ_STOCKISTS bld");
			sb.append(" where bld.BLK_STK_REQ_ID =:id");
			
			Query query = em.createNativeQuery(sb.toString(),Tuple.class);
			query.setParameter("id", hdBlkStkId);
			List<Tuple> tuples  = query.getResultList();
			
			if (tuples != null && !tuples.isEmpty()) {
				list = new ArrayList<>();
				BulkStkReqStockists object = null;
				for (Tuple t : tuples) {
					object = new BulkStkReqStockists();
					object.setCust_id(Long.valueOf(t.get("cust_id",String.class)));
					object.setCust_name(t.get("cust_name",String.class));
					object.setErp_cust_cd(t.get("erp_cust_cd",String.class));
					object.setSap_inv_no(t.get("sap_inv_no",String.class));
					object.setSap_inv_date(sdf.format(t.get("sap_inv_date",Date.class)));
					object.setSap_schm_remarks(t.get("sap_schm_remarks",String.class));
					
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
	public List<BlkStkReqProducts> getProductsDetailsByBulkHeaderId(Long Id) throws Exception {
		List<BlkStkReqProducts> list = new ArrayList<BlkStkReqProducts>();
		EntityManager em = null;
		try {
			em = emf.createEntityManager();
			StringBuffer sb = new StringBuffer();
				
			sb.append(" select sm.SMP_PROD_ID,sm.smp_prod_cd,sm.smp_prod_name,sm.smp_prod_type,sag.SA_GROUP_NAME, ");
			sb.append(" blp.STD_QTY,sm.PROD_SUB_TYPE_ID,sbstk.stk_qty");
			sb.append(" from SMPITEM sm");
			sb.append(" inner join BLK_STK_REQ_PRODUCTS blp  ");
			sb.append(" on sm.SMP_PROD_ID = blp.PROD_ID");
			sb.append(" inner join SAPRODGRP sag on sag.SA_PROD_GROUP = sm.SMP_SA_PROD_GROUP");
			sb.append(" inner join (select sb.BATCH_PROD_ID,sum(sb.batch_open_stock + sb.batch_in_stock - sb.batch_out_stock - ");
			sb.append(" sb.batch_with_held_qty) stk_qty from smpbatch sb");
			sb.append(" join smpitem si on si.smp_prod_id=sb.batch_prod_id  ");
			sb.append(" AND CONVERT(DATE, Isnull(batch_expiry_dt,'2099-01-01'), 105) >CONVERT(DATE,Getdate() + smp_short_expiry_days, 105)");
			sb.append(" where sb.batch_status='A'");
			sb.append(" group by sb.BATCH_PROD_ID");
			sb.append(" ) sbstk on batch_prod_id=blp.prod_id");
			sb.append(" where blp.BLK_STK_REQ_ID = :id");
			sb.append(" order by sm.smp_prod_name,SA_GROUP_NAME");

			Query query = em.createNativeQuery(sb.toString(),Tuple.class);
			query.setParameter("id", Id);
			List<Tuple> tuples  = query.getResultList();
			
			if (tuples != null && !tuples.isEmpty()) {
				list = new ArrayList<>();
				BlkStkReqProducts object = null;
				for (Tuple t : tuples) {
					object = new BlkStkReqProducts();
					object.setProd_id(t.get("SMP_PROD_ID",Integer.class).longValue());
					object.setSmp_prod_cd(t.get("smp_prod_cd",String.class));
					object.setProd_name(t.get("smp_prod_name",String.class));
					object.setSmp_prod_type(t.get("smp_prod_type",String.class));
					object.setSa_group_name(t.get("SA_GROUP_NAME",String.class));
					object.setStd_qty(t.get("STD_QTY",BigDecimal.class));
					object.setSmp_prod_type_id(t.get("PROD_SUB_TYPE_ID",Integer.class).longValue());
					object.setStk_qty(t.get("stk_qty",BigDecimal.class).longValue());
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
	public String getBulkCsvNameByBulkTempId(Long blkStkTempId) throws Exception {
		EntityManager em = null;
		String blkcsvnm = null;
		try {
			em = emf.createEntityManager();
			StringBuffer sb = new StringBuffer();
			sb.append(" select blk_csv_name from BlkStkReqTemp where blk_stk_req_id = "+blkStkTempId);
			Query query = em.createQuery(sb.toString(),String.class);
			blkcsvnm  =  (String) query.getSingleResult();
		}
		catch(Exception e) {
			e.printStackTrace();
		}
		finally {
			em.close();
		}
		return blkcsvnm;
	}
	
	
	@Override
	public List<BulkStkReqHeader> getRequestListFromBlkHdrForUploaded(Long locId) throws Exception {
		// TODO Auto-generated method stub
		EntityManager em = null;
		List<BulkStkReqHeader> hdrlist = new ArrayList<BulkStkReqHeader>();
		try {
			em = emf.createEntityManager();
			StringBuffer sb = new StringBuffer();
		//	sb.append(" FROM Blk_hcp_req_hdr where blk_status = 'Y' and div_id=:divid");
			sb.append(" select BH_REQ_NO+' ( '+rtrim(convert(varchar, TOTAL_REQ-ISNULL(DSP_COUNT,0)))+' ) ' as BH_REQ_NO,AH_BLK_REQ_ID,");
			sb.append(" TOTAL_REQ-ISNULL(DSP_COUNT,0) AS PENDING");
			sb.append(" FROM(");
			sb.append(" select bh.BLK_STK_REQ_NO BH_REQ_NO,ah.BLK_HCP_REQ_ID AH_BLK_REQ_ID,");
			sb.append(" count(*) AS TOTAL_REQ from ALLOC_header ah");
			sb.append(" join  BLK_STK_REQ_HEADER  bh on bh.BLK_STK_REQ_ID=ah.BLK_HCP_REQ_ID");
			sb.append(" WHERE  ISNULL(ah.BLK_HCP_REQ_ID,0) <>0 and ah.ALLOC_APPR_STATUS='A'");
			sb.append(" AND AH.ALLOC_TYPE='S'");
			sb.append(" AND BH.LOC_ID=:locId");
			sb.append(" group by  bh.BLK_STK_REQ_NO,ah.BLK_HCP_REQ_ID");
			sb.append(" )DTL");
			sb.append(" LEFT JOIN");
			sb.append(" (");
			sb.append(" SELECT DH.BLK_HCP_REQ_ID , COUNT(*) DSP_COUNT");
			sb.append(" FROM DISPATCH_HEADER DH WHERE DH.BLK_HCP_REQ_ID<>0");
			sb.append(" AND DH.DSP_LOC_ID=:locId");
			sb.append(" GROUP BY DH.BLK_HCP_REQ_ID");
			sb.append(" )DH  ON DH.BLK_HCP_REQ_ID =  DTL.ah_blk_req_id");
			sb.append(" where TOTAL_REQ-ISNULL(DSP_COUNT,0)>0");
			
			Query query = em.createNativeQuery(sb.toString(),Tuple.class);
			query.setParameter("locId", locId);
			List<Tuple> list  = query.getResultList();
			if(list!=null && !list.isEmpty()) {
				BulkStkReqHeader hdr= null;
				for(Tuple t :list) {
					hdr = new BulkStkReqHeader(); 
					hdr.setBlk_stk_req_no(t.get("BH_REQ_NO",String.class));
					hdr.setBlk_stk_req_id(t.get("AH_BLK_REQ_ID",BigDecimal.class).longValue());
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

}
