package com.excel.repository;

import java.util.Date;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.excel.model.BatchStockLog;
import com.excel.model.BatchStockLogNS;
import com.excel.model.BatchStockLogNS_;
import com.excel.model.BatchStockLog_;
import com.excel.utility.MedicoConstants;
import com.excel.utility.MedicoResources;

@Repository
public class BatchStockLogRepositoryImpl implements BatchStockLogRepository,MedicoConstants{
	
	@Autowired EntityManagerFactory emf;
	@PersistenceContext private EntityManager entityManager;
	
	@Override
	public BatchStockLog getRecordByProdIdAndDate(Long ProdId, Long batchId, Long locId) throws Exception {
		EntityManager em = null;
		List<BatchStockLog> list= null;
		try {
			em = emf.createEntityManager();
			CriteriaBuilder builder = em.getCriteriaBuilder();
			CriteriaQuery<BatchStockLog> criteriaQuery = builder.createQuery(BatchStockLog.class);
			Root<BatchStockLog> root = criteriaQuery.from(BatchStockLog.class);
			criteriaQuery.select(root)
			.where(
					builder.and(
							builder.equal(root.get(BatchStockLog_.btchstklg_prod_id), ProdId),
							builder.equal(root.get(BatchStockLog_.btchstklg_batch_id), batchId),
							builder.equal(root.get(BatchStockLog_.btchstklg_loc_id), locId),
							builder.greaterThanOrEqualTo(root.get(BatchStockLog_.btchstklg_date), new java.sql.Date((new java.util.Date()).getTime()))
							));
			list = em.createQuery(criteriaQuery).getResultList();
			if(!list.isEmpty() && list.size() > 0) {
				return list.get(0);
			}
		} finally {
			if (em != null) { em.close(); }
		}
		return null;
	}
	
	@Override
	public BatchStockLog getRecordByProdId(Long ProdId, Long batchId, Long locId) throws Exception {
		EntityManager em = null;
		List<BatchStockLog> list= null;
		try {
			em = emf.createEntityManager();
			CriteriaBuilder builder = em.getCriteriaBuilder();
			CriteriaQuery<BatchStockLog> criteriaQuery = builder.createQuery(BatchStockLog.class);
			Root<BatchStockLog> root = criteriaQuery.from(BatchStockLog.class);
			criteriaQuery.select(root)
			.where(
					builder.and(
							builder.equal(root.get(BatchStockLog_.btchstklg_prod_id), ProdId),
							builder.equal(root.get(BatchStockLog_.btchstklg_batch_id), batchId),
							builder.equal(root.get(BatchStockLog_.btchstklg_loc_id), locId),
							builder.equal(root.get(BatchStockLog_.btchstklg_tran_type),GRN)
							));
			list = em.createQuery(criteriaQuery).getResultList();
			if(!list.isEmpty() && list.size() > 0) {
				return list.get(0);
			}
		} finally {
			if (em != null) { em.close(); }
		}
		return null;
	}
	
	@Override
	public BatchStockLogNS getNSRecordByProdId(Long ProdId, Long batchId, Long locId) throws Exception {
		EntityManager em = null;
		List<BatchStockLogNS> list= null;
		try {
			em = emf.createEntityManager();
			CriteriaBuilder builder = em.getCriteriaBuilder();
			CriteriaQuery<BatchStockLogNS> criteriaQuery = builder.createQuery(BatchStockLogNS.class);
			Root<BatchStockLogNS> root = criteriaQuery.from(BatchStockLogNS.class);
			criteriaQuery.select(root)
			.where(
					builder.and(
							builder.equal(root.get(BatchStockLogNS_.btchstklg_prod_id), ProdId),
							builder.equal(root.get(BatchStockLogNS_.btchstklg_batch_id), batchId),
							builder.equal(root.get(BatchStockLogNS_.btchstklg_loc_id), locId),
							builder.equal(root.get(BatchStockLogNS_.btchstklg_date), new java.sql.Date((new java.util.Date()).getTime()))
							));
			list = em.createQuery(criteriaQuery).getResultList();
			if(!list.isEmpty() && list.size() > 0) {
				return list.get(0);
			}
		} finally {
			if (em != null) { em.close(); }
		}
		return null;
	}
	

	@Override
	public BatchStockLog getRecordByProdIdNew(Long ProdId, Long batchId, Long locId, Date date,String tranType) throws Exception {
		EntityManager em = null;
		List<BatchStockLog> list= null;
		try {
			em = emf.createEntityManager();
			StringBuffer sb = new StringBuffer();
			sb.append(" SELECT * FROM BATCH_STOCK_LOG ");
			sb.append(" WHERE BTCHSTKLG_PROD_ID = :btchstklg_prod_id AND BTCHSTKLG_BATCH_ID = :btchstklg_batch_id");
			sb.append(" AND BTCHSTKLG_LOC_ID = :btchstklg_loc_id AND CONVERT(DATE, :btchstklg_date) = CONVERT(DATE,BTCHSTKLG_DATE) AND BTCHSTKLG_TRAN_TYPE=:trantype");
			Query query = this.entityManager.createNativeQuery(sb.toString(), BatchStockLog.class);
			query.setParameter("btchstklg_prod_id", ProdId);
			query.setParameter("btchstklg_batch_id", batchId);
			query.setParameter("btchstklg_loc_id", locId);
			query.setParameter("trantype", tranType);
			query.setParameter("btchstklg_date", MedicoResources.convert_DD_MM_YYYY_toDate(MedicoResources.convertUtilDateToString_DD_MM_YYYY(date)));
			list = query.getResultList();
			if (list != null && !list.isEmpty()) {
				return list.get(0); 
			}
		} finally {
			if (em != null) { em.close(); }
		}
		return null;
	}
	
	@Override
	public BatchStockLogNS getNSRecordByProdIdNew(Long ProdId, Long batchId, Long locId, Date date, String stockType,String trantype) throws Exception {
		//EntityManager em = null;
		List<BatchStockLogNS> list= null;
		try {
			//em = emf.createEntityManager();
			StringBuffer sb = new StringBuffer();
			sb.append(" SELECT * FROM BATCH_STOCK_LOG_NS ");
			sb.append(" WHERE BTCHSTKLG_PROD_ID = :btchstklg_prod_id AND BTCHSTKLG_BATCH_ID = :btchstklg_batch_id AND BTCHSTKLG_STOCK_TYPE=:stockType ");
			sb.append(" AND BTCHSTKLG_LOC_ID = :btchstklg_loc_id AND CONVERT(DATE, :btchstklg_date) = CONVERT(DATE,BTCHSTKLG_DATE) AND BTCHSTKLG_TRAN_TYPE=:trantype");
			Query query = this.entityManager.createNativeQuery(sb.toString(), BatchStockLogNS.class);
			query.setParameter("btchstklg_prod_id", ProdId);
			query.setParameter("btchstklg_batch_id", batchId);
			query.setParameter("btchstklg_loc_id", locId);
			query.setParameter("stockType", stockType);
			query.setParameter("trantype", trantype);
			query.setParameter("btchstklg_date", MedicoResources.convert_DD_MM_YYYY_toDate(MedicoResources.convertUtilDateToString_DD_MM_YYYY(date)));
		
			list = query.getResultList();
			if (list != null && !list.isEmpty()) {
				return list.get(0); 
			}
		} finally {
			//if (em != null) { em.close(); }
		}
		return null;
	}

	@Override
	public BatchStockLog getRecordByProdIdBatchIdLocIdTranType(Long batch_id, Long prod_id,Long loc_id, Date date, String tran_type) throws Exception {
		List<BatchStockLog> list= null;
			CriteriaBuilder builder = entityManager.getCriteriaBuilder();
			CriteriaQuery<BatchStockLog> criteriaQuery = builder.createQuery(BatchStockLog.class);
			Root<BatchStockLog> root = criteriaQuery.from(BatchStockLog.class);
			criteriaQuery.select(root)
			.where(
					builder.and(
							builder.equal(root.get(BatchStockLog_.btchstklg_batch_id), batch_id),
							builder.equal(root.get(BatchStockLog_.btchstklg_prod_id), prod_id),
							builder.equal(root.get(BatchStockLog_.btchstklg_loc_id), loc_id),
							builder.equal(root.get(BatchStockLog_.btchstklg_tran_type), tran_type),
							builder.greaterThanOrEqualTo(root.get(BatchStockLog_.btchstklg_date), new java.sql.Date((date).getTime()))
							));
			list = entityManager.createQuery(criteriaQuery).getResultList();
			if(!list.isEmpty() && list.size() > 0) {
				return list.get(0);
			}
		return null;
	}


}
