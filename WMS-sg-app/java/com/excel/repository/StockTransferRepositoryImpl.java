package com.excel.repository;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.ParameterMode;
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

import com.excel.bean.StockTransferBean;
import com.excel.model.Stock_Transfer_Details;
import com.excel.model.Stock_Transfer_Details_;
import com.excel.model.Stock_Transfer_Header;
import com.excel.model.Stock_Transfer_Header_;
import com.excel.model.Sub_Company_;
import com.excel.model.V_Grn_Details_;
import com.excel.utility.CodifyErrors;
import com.excel.utility.MedicoConstants;

@Repository
public class StockTransferRepositoryImpl implements StockTransferRepository, MedicoConstants{

	@PersistenceContext private EntityManager entityManager;
	@Autowired private EntityManagerFactory emf;
	
	@Override
	public List<StockTransferBean> getProductListBySendrecLoc(Long sendLocId, Long sendSubCompId, Long sendStateId,
			String nilGstInd,String stockType)  throws Exception {
		List<StockTransferBean> list = new ArrayList<StockTransferBean>();
		EntityManager em = null;
		try {
			em = emf.createEntityManager();
			StringBuffer sb = new StringBuffer();
			
			if(stockType.equals("")) {
			sb.append("select smp_prod_id, smp_prod_name + ' - ' +convert(varchar(20),stock) as smp_prod_name from ( 	");					
			sb.append("select sp.smp_prod_id,sp.smp_prod_name, sp.smp_prod_cd, isnull(SUM((B.BATCH_OPEN_STOCK + B.BATCH_IN_STOCK) - (B.BATCH_OUT_STOCK + B.BATCH_WITH_HELD_QTY)),0) as STOCK ");
			sb.append("from SMPITEM SP LEFT JOIN SMPBatch B ON SP.SMP_PROD_ID = B.BATCH_PROD_ID ");
			sb.append("and (case when (SP.SMP_BATCH_RQ_IND = '' or SP.SMP_BATCH_RQ_IND= 'N') then DATEADD(dd,1,dateadd(mi, 30, dateadd(hh, 5, getutcdate()))) ");
			sb.append("else DATEADD(dd,- SP.SMP_SHORT_EXPIRY_DAYS,B.BATCH_EXPIRY_DT) end) "); 
			sb.append("> convert(datetime,convert(varchar(10),dateadd(mi, 30, dateadd(hh, 5, getutcdate())),103),104) ");
			sb.append("and B.BATCH_LOC_ID = :loc_id and B.BATCH_status = 'A' "); 
			sb.append("where SP.SMP_status = 'A' and  SMP_SubComp_id=:sub_comp_id ");
			sb.append("group by SP.SMP_PROD_NAME,sp.smp_prod_cd,sp.smp_prod_id) dtl ");
			
			sb.append(" INNER JOIN productwise_tax_master pt ON PT.prod_code = dtl.smp_prod_cd  ");
			sb.append(" AND PT.state_id=:state_id INNER JOIN tax_master tx ON tx.tax_cd = PT.tax_cd where stock>0 ");
			
			if(nilGstInd.equalsIgnoreCase("Y")){
				sb.append(" AND tx.state_id = PT.state_id AND (tx.igst = 0 and tx.sgst = 0 and tx.cgst = 0) ");
			}else{
				sb.append(" AND tx.state_id = PT.state_id AND (tx.igst != 0 and tx.sgst != 0 and tx.cgst != 0) ");
			}
			sb.append(" ORDER  BY smp_prod_name ASC");
			} else {
				
				if(stockType.equals("05") && nilGstInd.equalsIgnoreCase("N")) {
				sb.append("select smp_prod_id, smp_prod_name + ' - ' +convert(varchar(20),stock) as smp_prod_name from ( 	");					
				sb.append("select sp.smp_prod_id,sp.smp_prod_name, sp.smp_prod_cd, isnull(SUM((B.BATCH_OPEN_STOCK + B.BATCH_IN_STOCK) - (B.BATCH_OUT_STOCK + B.BATCH_WITH_HELD_QTY)),0) as STOCK ");
				sb.append("from SMPITEM SP LEFT JOIN SMPBatch_ns B ON SP.SMP_PROD_ID = B.BATCH_PROD_ID ");
				sb.append("and (case when (SP.SMP_BATCH_RQ_IND = '' or SP.SMP_BATCH_RQ_IND= 'N') then DATEADD(dd,1,dateadd(mi, 30, dateadd(hh, 5, getutcdate()))) ");
				sb.append("else DATEADD(dd,0,B.BATCH_EXPIRY_DT) end) "); 
				sb.append("<= convert(datetime,convert(varchar(10),dateadd(mi, 30, dateadd(hh, 5, getutcdate())),103),104) ");
				sb.append("and B.BATCH_LOC_ID = :loc_id and B.BATCH_status = 'A' "); 
				sb.append("where SP.SMP_status = 'A' and  SMP_SubComp_id=:sub_comp_id and stock_type='05' ");
				sb.append("group by SP.SMP_PROD_NAME,sp.smp_prod_cd,sp.smp_prod_id) dtl ");
				sb.append(" INNER JOIN productwise_tax_master pt ON PT.prod_code = dtl.smp_prod_cd  ");
				sb.append(" AND PT.state_id=:state_id INNER JOIN tax_master tx ON tx.tax_cd = PT.tax_cd where stock>0 ");
				sb.append(" AND tx.state_id = PT.state_id AND (tx.igst != 0 and tx.sgst != 0 and tx.cgst != 0) ");
				sb.append(" ORDER  BY smp_prod_name ASC");
				} else if(!stockType.equals("05") && nilGstInd.equalsIgnoreCase("N")){
					sb.append("select smp_prod_id, smp_prod_name + ' - ' +convert(varchar(20),stock) as smp_prod_name from ( 	");					
					sb.append("select sp.smp_prod_id,sp.smp_prod_name, sp.smp_prod_cd, isnull(SUM((B.BATCH_OPEN_STOCK + B.BATCH_IN_STOCK) - (B.BATCH_OUT_STOCK + B.BATCH_WITH_HELD_QTY)),0) as STOCK ");
					sb.append("from SMPITEM SP LEFT JOIN SMPBatch_ns B ON SP.SMP_PROD_ID = B.BATCH_PROD_ID and ");
					sb.append(" B.BATCH_LOC_ID = :loc_id and B.BATCH_status = 'A' "); 
					sb.append("where SP.SMP_status = 'A' and  SMP_SubComp_id=:sub_comp_id and stock_type=:stock_type ");
					sb.append("group by SP.SMP_PROD_NAME,sp.smp_prod_cd,sp.smp_prod_id) dtl ");
					sb.append(" INNER JOIN productwise_tax_master pt ON PT.prod_code = dtl.smp_prod_cd  ");
					sb.append(" AND PT.state_id=:state_id INNER JOIN tax_master tx ON tx.tax_cd = PT.tax_cd where stock>0 ");
					sb.append(" AND tx.state_id = PT.state_id AND (tx.igst != 0 and tx.sgst != 0 and tx.cgst != 0) ");
					sb.append(" ORDER  BY smp_prod_name ASC");
				} else if(stockType.equals("05") && nilGstInd.equalsIgnoreCase("Y")){
					sb.append("select smp_prod_id, smp_prod_name + ' - ' +convert(varchar(20),stock) as smp_prod_name from ( 	");					
					sb.append("select sp.smp_prod_id,sp.smp_prod_name, sp.smp_prod_cd, isnull(SUM((B.BATCH_OPEN_STOCK + B.BATCH_IN_STOCK) - (B.BATCH_OUT_STOCK + B.BATCH_WITH_HELD_QTY)),0) as STOCK ");
					sb.append("from SMPITEM SP LEFT JOIN SMPBatch_ns B ON SP.SMP_PROD_ID = B.BATCH_PROD_ID ");
					sb.append("and (case when (SP.SMP_BATCH_RQ_IND = '' or SP.SMP_BATCH_RQ_IND= 'N') then DATEADD(dd,1,dateadd(mi, 30, dateadd(hh, 5, getutcdate()))) ");
					sb.append("else DATEADD(dd,0,B.BATCH_EXPIRY_DT) end) "); 
					sb.append("<= convert(datetime,convert(varchar(10),dateadd(mi, 30, dateadd(hh, 5, getutcdate())),103),104) ");
					sb.append("and B.BATCH_LOC_ID = :loc_id and B.BATCH_status = 'A' "); 
					sb.append("where SP.SMP_status = 'A' and  SMP_SubComp_id=:sub_comp_id and stock_type='05' ");
					sb.append("group by SP.SMP_PROD_NAME,sp.smp_prod_cd,sp.smp_prod_id) dtl ");
					sb.append(" INNER JOIN productwise_tax_master pt ON PT.prod_code = dtl.smp_prod_cd  ");
					sb.append(" AND PT.state_id=:state_id INNER JOIN tax_master tx ON tx.tax_cd = PT.tax_cd where stock>0 ");
					sb.append(" AND tx.state_id = PT.state_id AND (tx.igst = 0 and tx.sgst = 0 and tx.cgst = 0) ");
					sb.append(" ORDER  BY smp_prod_name ASC");
				} else if(!stockType.equals("05") && nilGstInd.equalsIgnoreCase("Y")){
					sb.append("select smp_prod_id, smp_prod_name + ' - ' +convert(varchar(20),stock) as smp_prod_name from ( 	");					
					sb.append("select sp.smp_prod_id,sp.smp_prod_name, sp.smp_prod_cd, isnull(SUM((B.BATCH_OPEN_STOCK + B.BATCH_IN_STOCK) - (B.BATCH_OUT_STOCK + B.BATCH_WITH_HELD_QTY)),0) as STOCK ");
					sb.append("from SMPITEM SP LEFT JOIN SMPBatch_ns B ON SP.SMP_PROD_ID = B.BATCH_PROD_ID and ");
					sb.append(" B.BATCH_LOC_ID = :loc_id and B.BATCH_status = 'A' "); 
					sb.append("where SP.SMP_status = 'A' and  SMP_SubComp_id=:sub_comp_id and stock_type=:stock_type ");
					sb.append("group by SP.SMP_PROD_NAME,sp.smp_prod_cd,sp.smp_prod_id) dtl ");
					sb.append(" INNER JOIN productwise_tax_master pt ON PT.prod_code = dtl.smp_prod_cd  ");
					sb.append(" AND PT.state_id=:state_id INNER JOIN tax_master tx ON tx.tax_cd = PT.tax_cd where stock>0 ");
					sb.append(" AND tx.state_id = PT.state_id AND (tx.igst = 0 and tx.sgst = 0 and tx.cgst = 0) ");
					sb.append(" ORDER  BY smp_prod_name ASC");
				}
				
			}
			
			Query query = em.createNativeQuery(sb.toString(), Tuple.class);
			query.setParameter("loc_id", sendLocId);
		    query.setParameter("sub_comp_id", sendSubCompId);
		    query.setParameter("state_id", sendStateId);
		    if(!stockType.equals("05") && !stockType.equals("")) {
		    	query.setParameter("stock_type", stockType);
		    }
			List<Tuple> tuples = query.getResultList();
			StockTransferBean st = null;
			if (tuples != null && !tuples.isEmpty()) {
				for(Tuple t : tuples) {
					st=new StockTransferBean();
					st.setProdId(Long.valueOf(t.get("smp_prod_id", Integer.class)));
					st.setProdName(t.get("smp_prod_name", String.class));
					list.add(st);
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
	public List<Object> getSgstIgst(Long dptLocId, Long prodId) {
		List<Object> list = new ArrayList<Object>();
		EntityManager em = null;
		try {
			em = emf.createEntityManager();
			StringBuffer sb = new StringBuffer();
			sb.append("SELECT pwtm.prod_code as prod_code, ISNULL(tm.igst,0) AS igst ");
			sb.append("FROM PRODUCTWISE_TAX_MASTER pwtm,TAX_MASTER tm, SmpItem prod ");
			sb.append("WHERE pwtm.state_id = (select DPTSTATE_ID from DEPOT_LOCATIONS  where DPTLOC_ID =:dptloc_id) ");
			sb.append("AND prod.smp_prod_id =:prod_id AND pwtm.prod_code = prod.smp_prod_cd AND tm.tax_cd = pwtm.tax_cd ");
			
			Query query = em.createNativeQuery(sb.toString());
			query.setParameter("prod_id", prodId);
	        query.setParameter("dptloc_id", dptLocId);
	        
	        list= query.getResultList();
		} catch (Exception e) {
			System.out.println("Error Occurred :" +e.getMessage());// uncomment asneeded --;
		} finally {
			if (em != null) { em.close(); }
		}
		return list;
	}

	@Override
	public List<Object> getbatchByProdAndLoc(Long sendLocId, Long prodId,String tranType,String stockType)  throws Exception {
		List<Object> list = new ArrayList<Object>();
		EntityManager em = null;
		try {
			em = emf.createEntityManager();
			StringBuffer sb = new StringBuffer();
			if(tranType.equals(STK_TRF_SA)) {
				sb.append("SELECT SMPBATCH.batch_id,SMPBATCH.batch_prod_id,SMPBATCH.batch_no, CONVERT(VARCHAR(10),SMPBATCH.BATCH_EXPIRY_DT,103) exp_date,SMPBATCH.batch_loc_id,SMPBATCH.batch_rate,");
				sb.append("isnull(SMPBATCH.batch_open_stock,0) batch_open_stock,isnull(SMPBATCH.batch_in_stock,0) batch_in_stock,");
				sb.append("isnull(SMPBATCH.batch_out_stock,0) batch_out_stock,isnull(SMPBATCH.batch_with_held_qty,0) batch_with_held_qty, CONVERT(VARCHAR(10),SMPBATCH.BATCH_MFG_DT,103) mfg_date  ");
				sb.append("FROM SMPBATCH,smpitem ");
				sb.append("WHERE SMPBATCH.batch_prod_id =:prod_id AND SMPBATCH.batch_loc_id =:loc_id AND   SMPBATCH.batch_prod_id = smpitem.smp_prod_id ");
				sb.append("and (case when (smpitem.SMP_BATCH_RQ_IND = '' or smpitem.SMP_BATCH_RQ_IND= 'N') then ");
				sb.append("DATEADD(dd,1,dateadd(mi, 30, dateadd(hh, 5, getutcdate()))) ");
				sb.append("else DATEADD(dd,- smpitem.SMP_SHORT_EXPIRY_DAYS,SMPBATCH.BATCH_EXPIRY_DT) end) ");
				sb.append("> convert(datetime,convert(varchar(10),dateadd(mi, 30, dateadd(hh, 5, getutcdate())),103),104) ");
				sb.append("AND ( batch_open_stock+batch_in_stock-batch_out_stock-batch_with_held_qty)>0 ");
				sb.append("ORDER BY SMPBATCH.batch_expiry_dt, SMPBATCH.batch_no");
			} else {
				if(tranType.equals("05")) {
					sb.append("SELECT SMPBatch_NS.batch_id,SMPBatch_NS.batch_prod_id,SMPBatch_NS.batch_no, CONVERT(VARCHAR(10),SMPBatch_NS.BATCH_EXPIRY_DT,103) exp_date,SMPBatch_NS.batch_loc_id,SMPBatch_NS.batch_rate,");
					sb.append("isnull(SMPBatch_NS.batch_open_stock,0) batch_open_stock,isnull(SMPBatch_NS.batch_in_stock,0) batch_in_stock,");
					sb.append("isnull(SMPBatch_NS.batch_out_stock,0) batch_out_stock,isnull(SMPBatch_NS.batch_with_held_qty,0) batch_with_held_qty,CONVERT(VARCHAR(10),SMPBatch_NS.BATCH_MFG_DT,103) mfg_date  ");
					sb.append("FROM SMPBatch_NS,smpitem ");
					sb.append("WHERE SMPBatch_NS.batch_prod_id =:prod_id AND SMPBatch_NS.batch_loc_id =:loc_id AND SMPBatch_NS.stock_type=:stock_type AND   SMPBatch_NS.batch_prod_id = smpitem.smp_prod_id ");
					sb.append("and (case when (smpitem.SMP_BATCH_RQ_IND = '' or smpitem.SMP_BATCH_RQ_IND= 'N') then ");
					sb.append("DATEADD(dd,1,dateadd(mi, 30, dateadd(hh, 5, getutcdate()))) ");
					sb.append("else DATEADD(dd,0,SMPBatch_NS.BATCH_EXPIRY_DT) end) ");
					sb.append("<= convert(datetime,convert(varchar(10),dateadd(mi, 30, dateadd(hh, 5, getutcdate())),103),104) ");
					sb.append("AND ( batch_open_stock+batch_in_stock-batch_out_stock-batch_with_held_qty)>0 ");
					sb.append("ORDER BY SMPBatch_NS.batch_expiry_dt, SMPBatch_NS.batch_no");
				} else {
					sb.append("SELECT SMPBatch_NS.batch_id,SMPBatch_NS.batch_prod_id,SMPBatch_NS.batch_no, CONVERT(VARCHAR(10),SMPBatch_NS.BATCH_EXPIRY_DT,103) exp_date,SMPBatch_NS.batch_loc_id,SMPBatch_NS.batch_rate,");
					sb.append("isnull(SMPBatch_NS.batch_open_stock,0) batch_open_stock,isnull(SMPBatch_NS.batch_in_stock,0) batch_in_stock,");
					sb.append("isnull(SMPBatch_NS.batch_out_stock,0) batch_out_stock,isnull(SMPBatch_NS.batch_with_held_qty,0) batch_with_held_qty, CONVERT(VARCHAR(10),SMPBatch_NS.BATCH_MFG_DT,103) mfg_date  ");
					sb.append("FROM SMPBatch_NS,smpitem ");
					sb.append("WHERE SMPBatch_NS.batch_prod_id =:prod_id AND SMPBatch_NS.batch_loc_id =:loc_id AND SMPBatch_NS.stock_type=:stock_type AND  SMPBatch_NS.batch_prod_id = smpitem.smp_prod_id ");
					sb.append("AND ( batch_open_stock+batch_in_stock-batch_out_stock-batch_with_held_qty)>0 ");
					sb.append("ORDER BY SMPBatch_NS.batch_expiry_dt, SMPBatch_NS.batch_no");
				}
				
			}
			Query query = em.createNativeQuery(sb.toString());
			query.setParameter("prod_id", prodId);
	        query.setParameter("loc_id", sendLocId);
	        if(!tranType.equals(STK_TRF_SA)) {
	        	 query.setParameter("stock_type", stockType);
	        } 
			list= query.getResultList();
		} catch (Exception e) {
			System.out.println("Error Occurred :" +e.getMessage());// uncomment asneeded --;
		} finally {
			if (em != null) { em.close(); }
		}
		return list;
	}

	@Override
	public Stock_Transfer_Header getObjectById(Long headerId) throws Exception {
		return this.entityManager.find(Stock_Transfer_Header.class, headerId);
	}

	@Override
	public List<StockTransferBean> getSTProdDetails(Long header_id, Long sendLocId) throws Exception {
		List<StockTransferBean> list = new ArrayList<StockTransferBean>();
		EntityManager em = null;
		try {
			em = emf.createEntityManager();
			StringBuffer sb = new StringBuffer();
			sb.append(" SELECT s2.TRF_NO, b1.batch_no, p1.smp_prod_name, s1.sold_qty,s1.rate, s1.free_qty, s1.prod_id ");
			sb.append(" FROM stock_transfer_details s1, stock_transfer_header s2, SMPITEM p1, SMPBatch b1 ");
			sb.append(" WHERE s2.LOC_ID = :LOC_ID AND s1.TRF_ID = :TRF_ID and s1.trfdtl_status='A' ");
			sb.append(" AND s2.trf_id = s1.trf_id AND s1.batch_id = b1.batch_id AND s1.prod_id = p1.smp_prod_id ");
			Query query = em.createNativeQuery(sb.toString(), Tuple.class);
			query.setParameter("TRF_ID", header_id);
			query.setParameter("LOC_ID", sendLocId);
			
			List<Tuple> tuples = query.getResultList();
			StockTransferBean st = null;
			if (tuples != null && !tuples.isEmpty()) {
				for(Tuple t : tuples) {
					st=new StockTransferBean();
					st.setStockTrfNo(t.get("TRF_NO", String.class));
					st.setBatchNo(t.get("batch_no", String.class));
					st.setProductName(t.get("smp_prod_name", String.class));
					st.setSoldQty(t.get("sold_qty", BigDecimal.class));
					st.setRate(t.get("rate", BigDecimal.class));
					st.setFreeQtyCd(t.get("free_qty", BigDecimal.class));
					st.setProdId(Long.valueOf(t.get("prod_id", BigDecimal.class).longValue()));
					st.setProdName(t.get("smp_prod_name", String.class));
					list.add(st);
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
	public List<Stock_Transfer_Header> getDtlByDateAndLoc(Long send_loc_id, Long rec_loc_id, Date date,
			String stkTrfType) throws Exception {
		EntityManager em = null;
		List<Stock_Transfer_Header> list = null;
		try {
			Calendar cal = Calendar.getInstance();
			cal.setTime(date);
			em = emf.createEntityManager();
			CriteriaBuilder builder = em.getCriteriaBuilder();
			CriteriaQuery<Stock_Transfer_Header> query = builder.createQuery(Stock_Transfer_Header.class);
			Root<Stock_Transfer_Header> root = query.from(Stock_Transfer_Header.class);
			query.multiselect(root.get(Stock_Transfer_Header_.trf_id), root.get(Stock_Transfer_Header_.trf_no), root.get(Stock_Transfer_Header_.nilgst_separate_challan)).where(
					builder.and(builder.equal(root.get(Stock_Transfer_Header_.loc_id), send_loc_id)),
					builder.and(builder.equal(root.get(Stock_Transfer_Header_.rec_loc_id), rec_loc_id)),
					builder.and(builder.equal(root.get(Stock_Transfer_Header_.trf_dt), cal.getTime())),
					builder.and(builder.equal(root.get(Stock_Transfer_Header_.cancelled), N)),
					builder.and(builder.equal(root.get(Stock_Transfer_Header_.stock_sa_or_ns), stkTrfType)),
					builder.and(builder.isNull(root.get(Stock_Transfer_Header_.lr_no)))
					).orderBy(builder.asc(root.get(Stock_Transfer_Header_.trf_no)));
			list = em.createQuery(query).getResultList();
		} finally {
			if(em != null) { em.close(); }
		}
		if(list != null && list.size() > 0) {
			return list;
		}
		return null;
	}

	@Override
	public List<StockTransferBean> getAddProdListForStkMod(Long sendLocId, String trf_id, Long subCompId,
			String nilGstInd, Long sendStateId, String stockType) throws Exception {
		List<StockTransferBean> list = new ArrayList<StockTransferBean>();
		EntityManager em = null;
		try {
			em = emf.createEntityManager();
			StringBuffer sb = new StringBuffer();
			
			if(stockType.equals("")) {
			sb.append("select smp_prod_id, smp_prod_name + ' - ' +convert(varchar(20),stock) as smp_prod_name from ( 	");					
			sb.append("select sp.smp_prod_id,sp.smp_prod_name, sp.smp_prod_cd, isnull(SUM((B.BATCH_OPEN_STOCK + B.BATCH_IN_STOCK) - (B.BATCH_OUT_STOCK + B.BATCH_WITH_HELD_QTY)),0) as STOCK ");
			sb.append("from SMPITEM SP LEFT JOIN SMPBatch B ON SP.SMP_PROD_ID = B.BATCH_PROD_ID ");
			sb.append("and (case when (SP.SMP_BATCH_RQ_IND = '' or SP.SMP_BATCH_RQ_IND= 'N') then DATEADD(dd,1,dateadd(mi, 30, dateadd(hh, 5, getutcdate()))) ");
			sb.append("else DATEADD(dd,- SP.SMP_SHORT_EXPIRY_DAYS,B.BATCH_EXPIRY_DT) end) "); 
			sb.append("> convert(datetime,convert(varchar(10),dateadd(mi, 30, dateadd(hh, 5, getutcdate())),103),104) ");
			sb.append("and B.BATCH_LOC_ID = :loc_id and B.BATCH_status = 'A' "); 
			sb.append("where SP.SMP_status = 'A' and  SMP_SubComp_id=:sub_comp_id ");
			sb.append(" and SP.smp_prod_id not in( select prod_id from stock_transfer_details where trf_id =:trf_id and trfdtl_status='A') ");
			sb.append("group by SP.SMP_PROD_NAME,sp.smp_prod_cd,sp.smp_prod_id) dtl ");
			
			sb.append(" INNER JOIN productwise_tax_master pt ON PT.prod_code = dtl.smp_prod_cd  ");
			sb.append(" AND PT.state_id=:state_id INNER JOIN tax_master tx ON tx.tax_cd = PT.tax_cd where stock>0 ");
			
			if(nilGstInd.equalsIgnoreCase("Y")){
				sb.append(" AND tx.state_id = PT.state_id AND (tx.igst = 0 and tx.sgst = 0 and tx.cgst = 0) ");
			}else{
				sb.append(" AND tx.state_id = PT.state_id AND (tx.igst != 0 and tx.sgst != 0 and tx.cgst != 0) ");
			}
			sb.append(" ORDER  BY smp_prod_name ASC");
			} else {
				
				if(stockType.equals("05") && nilGstInd.equalsIgnoreCase("N")) {
				sb.append("select smp_prod_id, smp_prod_name + ' - ' +convert(varchar(20),stock) as smp_prod_name from ( 	");					
				sb.append("select sp.smp_prod_id,sp.smp_prod_name, sp.smp_prod_cd, isnull(SUM((B.BATCH_OPEN_STOCK + B.BATCH_IN_STOCK) - (B.BATCH_OUT_STOCK + B.BATCH_WITH_HELD_QTY)),0) as STOCK ");
				sb.append("from SMPITEM SP LEFT JOIN SMPBatch_ns B ON SP.SMP_PROD_ID = B.BATCH_PROD_ID ");
				sb.append("and (case when (SP.SMP_BATCH_RQ_IND = '' or SP.SMP_BATCH_RQ_IND= 'N') then DATEADD(dd,1,dateadd(mi, 30, dateadd(hh, 5, getutcdate()))) ");
				sb.append("else DATEADD(dd,0,B.BATCH_EXPIRY_DT) end) "); 
				sb.append("<= convert(datetime,convert(varchar(10),dateadd(mi, 30, dateadd(hh, 5, getutcdate())),103),104) ");
				sb.append("and B.BATCH_LOC_ID = :loc_id and B.BATCH_status = 'A' "); 
				sb.append("where SP.SMP_status = 'A' and  SMP_SubComp_id=:sub_comp_id and stock_type='05' ");
				sb.append(" and SP.smp_prod_id not in( select prod_id from stock_transfer_details where trf_id =:trf_id and trfdtl_status='A') ");
				sb.append("group by SP.SMP_PROD_NAME,sp.smp_prod_cd,sp.smp_prod_id) dtl ");
				sb.append(" INNER JOIN productwise_tax_master pt ON PT.prod_code = dtl.smp_prod_cd  ");
				sb.append(" AND PT.state_id=:state_id INNER JOIN tax_master tx ON tx.tax_cd = PT.tax_cd where stock>0 ");
				sb.append(" AND tx.state_id = PT.state_id AND (tx.igst != 0 and tx.sgst != 0 and tx.cgst != 0) ");
				sb.append(" ORDER  BY smp_prod_name ASC");
				} else if(!stockType.equals("05") && nilGstInd.equalsIgnoreCase("N")){
					sb.append("select smp_prod_id, smp_prod_name + ' - ' +convert(varchar(20),stock) as smp_prod_name from ( 	");					
					sb.append("select sp.smp_prod_id,sp.smp_prod_name, sp.smp_prod_cd, isnull(SUM((B.BATCH_OPEN_STOCK + B.BATCH_IN_STOCK) - (B.BATCH_OUT_STOCK + B.BATCH_WITH_HELD_QTY)),0) as STOCK ");
					sb.append("from SMPITEM SP LEFT JOIN SMPBatch_ns B ON SP.SMP_PROD_ID = B.BATCH_PROD_ID and ");
					sb.append(" B.BATCH_LOC_ID = :loc_id and B.BATCH_status = 'A' "); 
					sb.append("where SP.SMP_status = 'A' and  SMP_SubComp_id=:sub_comp_id and stock_type=:stock_type ");
					sb.append(" and SP.smp_prod_id not in( select prod_id from stock_transfer_details where trf_id =:trf_id and trfdtl_status='A') ");
					sb.append("group by SP.SMP_PROD_NAME,sp.smp_prod_cd,sp.smp_prod_id) dtl ");
					sb.append(" INNER JOIN productwise_tax_master pt ON PT.prod_code = dtl.smp_prod_cd  ");
					sb.append(" AND PT.state_id=:state_id INNER JOIN tax_master tx ON tx.tax_cd = PT.tax_cd where stock>0 ");
					sb.append(" AND tx.state_id = PT.state_id AND (tx.igst != 0 and tx.sgst != 0 and tx.cgst != 0) ");
					sb.append(" ORDER  BY smp_prod_name ASC");
				} else if(stockType.equals("05") && nilGstInd.equalsIgnoreCase("Y")){
					sb.append("select smp_prod_id, smp_prod_name + ' - ' +convert(varchar(20),stock) as smp_prod_name from ( 	");					
					sb.append("select sp.smp_prod_id,sp.smp_prod_name, sp.smp_prod_cd, isnull(SUM((B.BATCH_OPEN_STOCK + B.BATCH_IN_STOCK) - (B.BATCH_OUT_STOCK + B.BATCH_WITH_HELD_QTY)),0) as STOCK ");
					sb.append("from SMPITEM SP LEFT JOIN SMPBatch_ns B ON SP.SMP_PROD_ID = B.BATCH_PROD_ID ");
					sb.append("and (case when (SP.SMP_BATCH_RQ_IND = '' or SP.SMP_BATCH_RQ_IND= 'N') then DATEADD(dd,1,dateadd(mi, 30, dateadd(hh, 5, getutcdate()))) ");
					sb.append("else DATEADD(dd,0,B.BATCH_EXPIRY_DT) end) "); 
					sb.append("<= convert(datetime,convert(varchar(10),dateadd(mi, 30, dateadd(hh, 5, getutcdate())),103),104) ");
					sb.append("and B.BATCH_LOC_ID = :loc_id and B.BATCH_status = 'A' "); 
					sb.append("where SP.SMP_status = 'A' and  SMP_SubComp_id=:sub_comp_id and stock_type='05' ");
					sb.append(" and SP.smp_prod_id not in( select prod_id from stock_transfer_details where trf_id =:trf_id and trfdtl_status='A') ");
					sb.append("group by SP.SMP_PROD_NAME,sp.smp_prod_cd,sp.smp_prod_id) dtl ");
					sb.append(" INNER JOIN productwise_tax_master pt ON PT.prod_code = dtl.smp_prod_cd  ");
					sb.append(" AND PT.state_id=:state_id INNER JOIN tax_master tx ON tx.tax_cd = PT.tax_cd where stock>0 ");
					sb.append(" AND tx.state_id = PT.state_id AND (tx.igst = 0 and tx.sgst = 0 and tx.cgst = 0) ");
					sb.append(" ORDER  BY smp_prod_name ASC");
				} else if(!stockType.equals("05") && nilGstInd.equalsIgnoreCase("Y")){
					sb.append("select smp_prod_id, smp_prod_name + ' - ' +convert(varchar(20),stock) as smp_prod_name from ( 	");					
					sb.append("select sp.smp_prod_id,sp.smp_prod_name, sp.smp_prod_cd, isnull(SUM((B.BATCH_OPEN_STOCK + B.BATCH_IN_STOCK) - (B.BATCH_OUT_STOCK + B.BATCH_WITH_HELD_QTY)),0) as STOCK ");
					sb.append("from SMPITEM SP LEFT JOIN SMPBatch_ns B ON SP.SMP_PROD_ID = B.BATCH_PROD_ID and ");
					sb.append(" B.BATCH_LOC_ID = :loc_id and B.BATCH_status = 'A' "); 
					sb.append("where SP.SMP_status = 'A' and  SMP_SubComp_id=:sub_comp_id and stock_type=:stock_type ");
					sb.append(" and SP.smp_prod_id not in( select prod_id from stock_transfer_details where trf_id =:trf_id and trfdtl_status='A') ");
					sb.append("group by SP.SMP_PROD_NAME,sp.smp_prod_cd,sp.smp_prod_id) dtl ");
					sb.append(" INNER JOIN productwise_tax_master pt ON PT.prod_code = dtl.smp_prod_cd  ");
					sb.append(" AND PT.state_id=:state_id INNER JOIN tax_master tx ON tx.tax_cd = PT.tax_cd where stock>0 ");
					sb.append(" AND tx.state_id = PT.state_id AND (tx.igst = 0 and tx.sgst = 0 and tx.cgst = 0) ");
					sb.append(" ORDER  BY smp_prod_name ASC");
				}
				
			}
			
			Query query = em.createNativeQuery(sb.toString(), Tuple.class);
			query.setParameter("loc_id", sendLocId);
		    query.setParameter("sub_comp_id", subCompId);
		    query.setParameter("state_id", sendStateId);
		    query.setParameter("trf_id", trf_id);
		    if(!stockType.equals("05") && !stockType.equals("")) {
		    	query.setParameter("stock_type", stockType);
		    }
		    System.out.println("loc_id:"+sendLocId);
		    System.out.println("sub_comp_id:"+subCompId);
		    System.out.println("state_id:"+sendStateId);
		    System.out.println("trf_id:"+trf_id);
		    System.out.println("stock_type:"+stockType);
			List<Tuple> tuples = query.getResultList();
			StockTransferBean st = null;
			if (tuples != null && !tuples.isEmpty()) {
				for(Tuple t : tuples) {
					st=new StockTransferBean();
					st.setProdId(Long.valueOf(t.get("smp_prod_id", Integer.class)));
					st.setProdName(t.get("smp_prod_name", String.class));
					list.add(st);
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
	public List<Object> getStockTransferDtForTrfIDAndProdID(Long trfId, Long prodId) throws Exception {
		List<Object> list = new ArrayList<Object>();
		EntityManager em = null;
		try {
			em = emf.createEntityManager();
			StringBuffer sb = new StringBuffer();
			sb.append("SELECT stock_transfer_details.batch_id,SMPBATCH.batch_no, CONVERT(VARCHAR(10),SMPBATCH.BATCH_EXPIRY_DT,103) exp_date,");
			sb.append("batch_open_stock+batch_in_stock-batch_out_stock-batch_with_held_qty+STOCK_TRANSFER_DETAILS.sold_qty+STOCK_TRANSFER_DETAILS.free_qty AS stock ,");
			sb.append("stock_transfer_details.rate,stock_transfer_details.sold_qty,stock_transfer_details.trf_sl_no,isnull(SMPBATCH.batch_with_held_qty,0) batch_with_held_qty");
			sb.append(" ,STOCK_TRANSFER_DETAILS.free_qty,stock_transfer_details.stock_type,stock_transfer_details.stock_type_id ,CONVERT(VARCHAR(10),SMPBATCH.BATCH_MFG_DT,103) mfg_date  FROM STOCK_TRANSFER_DETAILS,SMPBATCH ");
			sb.append("WHERE STOCK_TRANSFER_DETAILS.PROD_ID = :PROD_ID AND STOCK_TRANSFER_DETAILS.TRF_ID=:TRF_ID");
			sb.append(" AND STOCK_TRANSFER_DETAILS.BATCH_ID= SMPBATCH.BATCH_ID AND SMPBATCH.BATCH_STATUS = 'A' AND STOCK_TRANSFER_DETAILS.TRFDTL_STATUS='A' ");
			sb.append(" ORDER BY SMPBATCH.BATCH_EXPIRY_DT, SMPBATCH.BATCH_NO");
			Query query = em.createNativeQuery(sb.toString());
			query.setParameter("PROD_ID", prodId);
		    query.setParameter("TRF_ID", trfId);
			list= query.getResultList();
		} catch (Exception e) {
			System.out.println("Error Occurred :" +e.getMessage());// uncomment asneeded --;
		} finally {
			if (em != null) { em.close(); }
		}
		return list;
	}

	@Override
	public Stock_Transfer_Details getDetailById(Long detailId) throws Exception {
		return this.entityManager.find(Stock_Transfer_Details.class, detailId);
	}

	@Override
	public List<Long> getDistProdForTrfId(Long header_id) throws Exception {
		List<Long> list = new ArrayList<Long>();
		CriteriaBuilder builder = entityManager.getCriteriaBuilder();
		CriteriaQuery<Long> query = builder.createQuery(Long.class);
		Root<Stock_Transfer_Details> root = query.from(Stock_Transfer_Details.class);
		query.multiselect(root.get(Stock_Transfer_Details_.prod_id)).where(builder.and(builder.equal(root.get(Stock_Transfer_Details_.stockHeader), header_id)));
		query.distinct(true);
		list = entityManager.createQuery(query).getResultList();
		return list;
	
	}

	@Override
	public List<Stock_Transfer_Details> deleteStockTransferDtForTrfIDAndProdID(Long trf_id, Long prod_id) throws Exception {
		List<Stock_Transfer_Details> list = new ArrayList<Stock_Transfer_Details>();
		CriteriaBuilder builder = entityManager.getCriteriaBuilder();
		CriteriaQuery<Stock_Transfer_Details> query = builder.createQuery(Stock_Transfer_Details.class);
		Root<Stock_Transfer_Details> root = query.from(Stock_Transfer_Details.class);
		query.where(builder.and(builder.equal(root.get(Stock_Transfer_Details_.stockHeader), trf_id)),
					builder.and(builder.equal(root.get(Stock_Transfer_Details_.prod_id), prod_id)));
		list =  entityManager.createQuery(query).getResultList();
		return list;
	}

	@Override
	public List<StockTransferBean> getSTProdDetailsForDelete(Long header_id, Long sendLocId) throws Exception {
		List<StockTransferBean> list = new ArrayList<StockTransferBean>();
		EntityManager em = null;
		try {
			em = emf.createEntityManager();
			StringBuffer sb = new StringBuffer();
			sb.append(" SELECT  p1.smp_prod_name, s1.prod_id ");
			sb.append(" FROM stock_transfer_details s1, stock_transfer_header s2, SMPITEM p1, SMPBatch b1 ");
			sb.append(" WHERE s2.LOC_ID = :LOC_ID AND s1.TRF_ID = :TRF_ID and s1.trfdtl_status='A' ");
			sb.append(" AND s2.trf_id = s1.trf_id AND s1.batch_id = b1.batch_id AND s1.prod_id = p1.smp_prod_id ");
			sb.append(" group by p1.smp_prod_name, s1.prod_id  ");

			Query query = em.createNativeQuery(sb.toString(), Tuple.class);
			query.setParameter("TRF_ID", header_id);
			query.setParameter("LOC_ID", sendLocId);
			
			List<Tuple> tuples = query.getResultList();
			StockTransferBean st = null;
			if (tuples != null && !tuples.isEmpty()) {
				for(Tuple t : tuples) {
					st=new StockTransferBean();
					st.setProductName(t.get("smp_prod_name", String.class));
					st.setProdName(t.get("smp_prod_name", String.class));
					st.setProdId(Long.valueOf(t.get("prod_id", BigDecimal.class).longValue()));
					list.add(st);
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
	public List<Object> getStockTransferHdForLREntry(Long rec_loc_id, Long send_loc_id, String year_flag,
			Long fin_year_id, String comp_cd) throws Exception {
		List<Object> list = new ArrayList<Object>();
		EntityManager em = null;
		try {
			em = emf.createEntityManager();
			StringBuffer queryText = new StringBuffer();
			queryText.append("SELECT S.TRF_ID,S.TRF_NO,S.TRF_DATE,S.FULL_SHIPPERS,S.LOOSE_SHIPPERS,T.WEIGHT WEIGHT,S.VOLUME VOLUME,0 TOTAL_WEIGHT,0 TOTAL_VOLUME,S.FULL_SHIPPERS TOTAL_FULL_SHIPPER, ");
			queryText.append("(select sum(loose_shippers) from STOCK_TRANSFER_HEADER where  LOC_ID =1 and RECEIVING_LOC_ID =:loc_id ");
			queryText.append("and lr_no is null and COMPANY_CD=:comp_cd) as total_loose_shipper FROM STOCK_TRANSFER_HEADER S , ");
			queryText.append("(SELECT S.TRF_ID,'0' AS WEIGHT,'0' AS FULL_SHIPPERS,'0' AS VOLUME FROM STOCK_TRANSFER_DETAILS S , SMPITEM P , STOCK_TRANSFER_HEADER H  ");
			queryText.append("WHERE (H.CANCELLLED!='Y' OR H.CANCELLLED IS NULL ) AND H.LOC_ID =:loc_id AND H.RECEIVING_LOC_ID=:rec_loc_id ");
			queryText.append("AND H.LR_NO IS NULL AND H.COMPANY_CD=:comp_cd ");
			queryText.append("AND S.TRF_ID = H.TRF_ID AND S.PROD_ID = P.SMP_PROD_ID   AND S.FIN_YEAR_ID = H.FIN_YEAR_ID GROUP BY S.TRF_ID ) T ");
			queryText.append("where  S.LOC_ID =:loc_id AND S.RECEIVING_LOC_ID=:rec_loc_id AND S.LR_NO IS NULL AND S.COMPANY_CD=:comp_cd ");
			queryText.append("AND S.TRF_ID = T.TRF_ID AND S.FIN_YEAR_ID =:fin_yr ORDER BY S.TRF_NO ");
			
			Query query = em.createNativeQuery(queryText.toString());
			query.setParameter("rec_loc_id", rec_loc_id);
			query.setParameter("loc_id", send_loc_id);
			query.setParameter("comp_cd", comp_cd);
			query.setParameter("fin_yr", fin_year_id);
			list= query.getResultList();
		} catch (Exception e) {
			System.out.println("Error Occurred :" +e.getMessage());// uncomment asneeded --;
		} finally {
			if (em != null) { em.close(); }
		}
		return list;
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public Integer grnInTransitStkTrf(StockTransferBean bean) throws Exception {
		StoredProcedureQuery query = entityManager.createStoredProcedureQuery("p_i_GRN_INTRANSIT_CFASTOCK_STOCKTRF");
		query.registerStoredProcedureParameter(1, Integer.class, ParameterMode.IN);
		query.registerStoredProcedureParameter(2, String.class, ParameterMode.IN);
		query.registerStoredProcedureParameter(3, String.class, ParameterMode.IN);
		query.registerStoredProcedureParameter(4, Integer.class, ParameterMode.IN);
		query.registerStoredProcedureParameter(5, String.class, ParameterMode.IN);
		query.registerStoredProcedureParameter(6, String.class, ParameterMode.IN);
		query.registerStoredProcedureParameter(7, String.class, ParameterMode.IN);
		query.registerStoredProcedureParameter(8, Integer.class, ParameterMode.OUT);
		query.registerStoredProcedureParameter(9, Integer.class, ParameterMode.IN);
		query.registerStoredProcedureParameter(10, Integer.class, ParameterMode.IN);
		query.registerStoredProcedureParameter(11, Integer.class, ParameterMode.IN);
		query.registerStoredProcedureParameter(12, String.class, ParameterMode.IN);
		query.registerStoredProcedureParameter(13, String.class, ParameterMode.IN);
		
		query.setParameter(1,bean.getHeaderId());
		query.setParameter(2,bean.getTrfDate());
		query.setParameter(3,bean.getCompanyCode());
		query.setParameter(4,0);
		query.setParameter(5,bean.getEmpId());
		query.setParameter(6,bean.getIpAddress());
		query.setParameter(7,"A");
		query.setParameter(9,0);
		query.setParameter(10,0);
		query.setParameter(11,0);
		query.setParameter(12,"E");
		query.setParameter(13,"");
		query.execute();
		Integer header_id = (Integer) query.getOutputParameterValue(8);
		return header_id;
	}

} 
