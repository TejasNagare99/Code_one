package com.excel.repository;

import java.math.BigDecimal;
import java.util.ArrayList;
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

import com.excel.model.SmpBatch;
import com.excel.model.SmpBatchNS;
import com.excel.model.SmpBatchNS_;
import com.excel.model.SmpBatch_;
import com.excel.model.ViewSmpBatch;
import com.excel.utility.CodifyErrors;
import com.excel.utility.MedicoConstants;

@Repository
public class BatchMasterRepositoryImpl implements BatchMasterRepository, MedicoConstants{

	@PersistenceContext private EntityManager entityManager;
	@Autowired EntityManagerFactory emf;
	
	@Override
	public SmpBatch getObjectById(Long batchId) throws Exception {
		return this.entityManager.find(SmpBatch.class, batchId);
	}
	
	@Override
	public SmpBatchNS getNSObjectById(Long batchId) throws Exception {
		return this.entityManager.find(SmpBatchNS.class, batchId);
	}
	
	@Override
	public SmpBatchNS getNSObjectByIdAndStocktype(Long batchId, Long prodId, Long locId, String stockType) throws Exception {
		//EntityManager em = null;
		List<SmpBatchNS> list = null;
		try {
		//	em = emf.createEntityManager();
			CriteriaBuilder builder = entityManager.getCriteriaBuilder();
			CriteriaQuery<SmpBatchNS> criteriaQuery = builder.createQuery(SmpBatchNS.class);
			Root<SmpBatchNS> root = criteriaQuery.from(SmpBatchNS.class);
			criteriaQuery.select(root)
			.where(builder.and(builder.equal(root.get(SmpBatchNS_.batch_id), batchId),
					builder.equal(root.get(SmpBatchNS_.batch_prod_id), prodId),
					builder.equal(root.get(SmpBatchNS_.batch_loc_id), locId),
					builder.equal(root.get(SmpBatchNS_.batch_status), A),
					builder.equal(root.get(SmpBatchNS_.stock_type), stockType)));
			list = entityManager.createQuery(criteriaQuery).getResultList();
			if(list != null && list.size()>0) {
				return list.get(0);
			} else {
				return null;
			}
		}
		catch (Exception e) {
			// TODO: handle exception
			throw e;
		}
	}	
	
	@Override
	public boolean getSmpBatchByBatchNumbar(String batchNumber) throws Exception {
		EntityManager em = null;
		List<SmpBatch> list = null;
		try {
			em = emf.createEntityManager();
			CriteriaBuilder builder = em.getCriteriaBuilder();
			CriteriaQuery<SmpBatch> criteriaQuery = builder.createQuery(SmpBatch.class);
			Root<SmpBatch> root = criteriaQuery.from(SmpBatch.class);
			criteriaQuery.select(root)
			.where(builder.equal(root.get(SmpBatch_.batch_no), batchNumber));
			list = em.createQuery(criteriaQuery).getResultList();
			if(list != null && list.size()>0) {
				return true;
			} else {
				return false;
			}
		} finally {
			if (em != null) { em.close(); }
		}
	}
	
	@Override
	public boolean checkIfBatchStockIsNegative(Long batchId, Long prodId, Long locId) throws Exception {
	//	EntityManager em = null;
		List<SmpBatch> list = null;
		try {
		//	em = emf.createEntityManager();
			CriteriaBuilder builder = entityManager.getCriteriaBuilder();
			CriteriaQuery<SmpBatch> criteriaQuery = builder.createQuery(SmpBatch.class);
			Root<SmpBatch> root = criteriaQuery.from(SmpBatch.class);
			criteriaQuery.select(root)
			.where(builder.and(builder.equal(root.get(SmpBatch_.batch_id), batchId),
					builder.equal(root.get(SmpBatch_.batch_prod_id), prodId),
					builder.equal(root.get(SmpBatch_.batch_loc_id), locId),
					builder.equal(root.get(SmpBatch_.batch_status), A),
					builder.lessThan(root.get(SmpBatch_.batch_in_stock), BigDecimal.ZERO)));
			list = entityManager.createQuery(criteriaQuery).getResultList();
			if(list != null && list.size()>0) {
				return true;
			} else {
				return false;
			}
		} 
		catch (Exception e) {
			// TODO: handle exception
			throw e;
		}
	}
	
	@Override
	public boolean checkIfNSBatchStockIsNegative(Long batchId, Long prodId, Long locId, String stockType) throws Exception {
		//EntityManager em = null;
		List<SmpBatchNS> list = null;
		try {
			//em = emf.createEntityManager();
			CriteriaBuilder builder = entityManager.getCriteriaBuilder();
			CriteriaQuery<SmpBatchNS> criteriaQuery = builder.createQuery(SmpBatchNS.class);
			Root<SmpBatchNS> root = criteriaQuery.from(SmpBatchNS.class);
			criteriaQuery.select(root)
			.where(builder.and(builder.equal(root.get(SmpBatchNS_.batch_id), batchId),
					builder.equal(root.get(SmpBatchNS_.batch_prod_id), prodId),
					builder.equal(root.get(SmpBatchNS_.batch_loc_id), locId),
					builder.equal(root.get(SmpBatchNS_.batch_status), A),
					builder.equal(root.get(SmpBatchNS_.stock_type), stockType),
					builder.lessThan(root.get(SmpBatchNS_.batch_in_stock), BigDecimal.ZERO)));
			list = entityManager.createQuery(criteriaQuery).getResultList();
			if(list != null && list.size()>0) {
				return true;
			} else {
				return false;
			}
		} catch (Exception e) {
			throw e;
			// TODO: handle exception
		}
	}
	
	@Override
	public boolean checkIfNSBatchStockIsNegativeByBatchIdProdIdLocId(Long batchId, Long prodId, Long locId, String stockType) throws Exception {
	//	EntityManager em = null;
		List<SmpBatchNS> list = null;
		try {
		//	em = emf.createEntityManager();
			StringBuffer sb = new StringBuffer();
			sb.append("from SmpBatchNS ");
			sb.append("where batch_id= :batch_id and batch_prod_id= :batch_prod_id and batch_loc_id= :batch_loc_id and batch_status ='A' and stock_type=:stock_type ");
			sb.append(" and ((batch_open_stock + batch_in_stock) - (batch_with_held_qty + batch_out_stock)) < 0 ");
			
			Query query = entityManager.createQuery(sb.toString(), SmpBatchNS.class);
			query.setParameter("batch_id", batchId);
			query.setParameter("batch_prod_id", prodId);
			query.setParameter("batch_loc_id", locId);
			query.setParameter("stock_type", stockType);
			
			list = query.getResultList();
			if (list != null && !list.isEmpty()) {
				return true;
			}
		}catch (Exception e) {
			// TODO: handle exception
			throw e;
		}
		return false;
	}
	
	@Override
	public boolean checkIfBatchStockIsNegativeByBatchIdProdIdLocId(Long batchId, Long prodId, Long locId) throws Exception {
	//	EntityManager em = null;
		List<SmpBatch> list = null;
		try {
	//		em = emf.createEntityManager();
			StringBuffer sb = new StringBuffer();
			sb.append(" from SmpBatch ");
			sb.append(" where batch_id= :batch_id and batch_prod_id= :batch_prod_id and batch_loc_id= :batch_loc_id and batch_status ='A' ");
			sb.append(" and ((batch_open_stock + batch_in_stock) - (batch_with_held_qty + batch_out_stock)) < 0 ");
			
			Query query = entityManager.createQuery(sb.toString(), SmpBatch.class);
			query.setParameter("batch_id", batchId);
			query.setParameter("batch_prod_id", prodId);
			query.setParameter("batch_loc_id", locId);
			
			list = query.getResultList();
			if (list != null && !list.isEmpty()) {
				return true;
			}
		}
		catch (Exception e) {
			// TODO: handle exception
			throw e;
		}
//		finally {
//			if(em != null) { em.close(); }
//		}
		return false;
	}
	
	@Override
	public Boolean checkDuplicateRecordStk(Long rec_loc_id, String batch_no, Long product_id) throws Exception {
		List<SmpBatch> list = null;
			CriteriaBuilder builder = entityManager.getCriteriaBuilder();
			CriteriaQuery<SmpBatch> criteriaQuery = builder.createQuery(SmpBatch.class);
			Root<SmpBatch> root = criteriaQuery.from(SmpBatch.class);
			criteriaQuery.select(root)
			.where(builder.and(builder.equal(root.get(SmpBatch_.batch_loc_id), rec_loc_id),
					builder.equal(root.get(SmpBatch_.batch_no), batch_no),
					builder.equal(root.get(SmpBatch_.batch_prod_id), product_id),
					builder.equal(root.get(SmpBatch_.batch_status), A)));
			list = entityManager.createQuery(criteriaQuery).getResultList();
			if(list != null && list.size()>0) {
				return true;
			} else {
				return false;
			}
	}

	@Override
	public Boolean checkDuplicateRecordStkNs(Long rec_loc_id, String batch_no, Long product_id, String stockType)
			throws Exception {
		EntityManager em = null;
		List<SmpBatchNS> list = null;
		try {
			em = emf.createEntityManager();
			CriteriaBuilder builder = em.getCriteriaBuilder();
			CriteriaQuery<SmpBatchNS> criteriaQuery = builder.createQuery(SmpBatchNS.class);
			Root<SmpBatchNS> root = criteriaQuery.from(SmpBatchNS.class);
			criteriaQuery.select(root)
			.where(builder.and(builder.equal(root.get(SmpBatchNS_.batch_loc_id), rec_loc_id),
					builder.equal(root.get(SmpBatchNS_.batch_no), batch_no),
					builder.equal(root.get(SmpBatchNS_.batch_prod_id), product_id),
					builder.equal(root.get(SmpBatchNS_.stock_type), stockType),
					builder.equal(root.get(SmpBatchNS_.batch_status), A)));
			list = em.createQuery(criteriaQuery).getResultList();
			if(list != null && list.size()>0) {
				return true;
			} else {
				return false;
			}
		} finally {
			if (em != null) { em.close(); }
		}
	}
	
	@Override
	public List<SmpBatch> getBatchListByProdId(String prodId) {
		EntityManager em = null;
		List<SmpBatch> list = new ArrayList<SmpBatch>();
		try {
			em = emf.createEntityManager();
			String q="";
			if(!prodId.equalsIgnoreCase("0")) {
			 q="SELECT batch_id,batch_no FROM smpbatch WHERE batch_status='A' and BATCH_PROD_ID=:prodId ORDER BY batch_no";
			}
			else {
				 q="SELECT batch_id,batch_no FROM smpbatch WHERE batch_status='A'  ORDER BY batch_no";
			}
			Query query = em.createNativeQuery(q,Tuple.class);
			if(!prodId.equalsIgnoreCase("0")) {
			query.setParameter("prodId", prodId);
			}
			List<Tuple> tuples = query.getResultList();			
			if (tuples != null && !tuples.isEmpty()) {
				for (Tuple t : tuples) {
					SmpBatch sb=new SmpBatch();
					sb.setBatch_id(Long.valueOf(t.get("batch_id",Integer.class)));
					sb.setBatch_no(t.get("batch_no",String.class));
					list.add(sb);
				}
				if(!list.isEmpty() && list.size() > 0)
					return list;
				}
			}
		catch(Exception e) {
			throw e;
		}
		finally {
				if (em != null) { em.close(); }
		}
		return list;
	}
	
	@Override
	public List<ViewSmpBatch> getBatchListByFilter(String search_by, String param_id, String search_text, int loc_id)
			throws Exception {
		EntityManager em = null;
		List<ViewSmpBatch> list = null;
		try {
			em = emf.createEntityManager();
			StringBuffer sb = new StringBuffer();
			sb.append(" from ViewSmpBatch where batch_status='A' and batch_loc_id= "+loc_id);

			if (!search_text.isEmpty()) {
				if (search_by.equalsIgnoreCase("NM")) {
					if (param_id.equals("1")) {
						sb.append(" and  smp_prod_name like '%"+search_text+"%' ");
					} else {
						sb.append(" and  smp_prod_name not like '%"+search_text+"%' ");
					}
				} else if (search_by.equalsIgnoreCase("LC")) {
					if (param_id.equals("1")) {
						sb.append(" and  loc_nm like '%"+search_text+"%' ");
					} else {
						sb.append(" and  loc_nm not like '%"+search_text+"%' ");
					}
				} else if (search_by.equalsIgnoreCase("BN")) {
					if (param_id.equals("1")) {
						sb.append(" and  batch_no like '%"+search_text+"%' ");
					} else {
						sb.append(" and  batch_no not like '%"+search_text+"%' ");
					}
				}
			}
			sb.append(" order by smp_prod_name asc ");
			Query query = em.createQuery(sb.toString(), ViewSmpBatch.class);
			list = query.getResultList();
			if (list != null && !list.isEmpty()) {
				return list;
			}
		} finally {
			if(em != null) { em.close(); }
		}
		return list;
	}
	
	@Override
	public SmpBatch batchByBatchNoProdId(String batch_no, Long product_id) throws Exception {
	    List<SmpBatch> list = null;
	    System.out.println("batch_no "+batch_no);
	    System.out.println("product_id "+product_id);
		 try {
		 	CriteriaBuilder builder = entityManager.getCriteriaBuilder();
			CriteriaQuery<SmpBatch> criteriaQuery = builder.createQuery(SmpBatch.class);
			Root<SmpBatch> root = criteriaQuery.from(SmpBatch.class);
			criteriaQuery.select(root)
			.where(builder.and(
					builder.equal(root.get(SmpBatch_.batch_no), batch_no),
					builder.equal(root.get(SmpBatch_.batch_prod_id), product_id),
					builder.equal(root.get(SmpBatch_.batch_status), A)));
			list = entityManager.createQuery(criteriaQuery).getResultList();
			if(list != null && list.size()>0) 
				return list.get(0);
			}
		catch(Exception e) {
			System.out.println("Error Occurred :" +e.getMessage());// uncomment asneeded --;
		}
		 return null;
	}
	
	@Override
	public SmpBatchNS NsBatchByBatchNoProdId(String batch_no, Long product_id) throws Exception {
	    List<SmpBatchNS> list = null;
	    System.out.println("batch_no "+batch_no);
	    System.out.println("product_id "+product_id);
		 try {
		 	CriteriaBuilder builder = entityManager.getCriteriaBuilder();
			CriteriaQuery<SmpBatchNS> criteriaQuery = builder.createQuery(SmpBatchNS.class);
			Root<SmpBatchNS> root = criteriaQuery.from(SmpBatchNS.class);
			criteriaQuery.select(root)
			.where(builder.and(
					builder.equal(root.get(SmpBatchNS_.batch_no), batch_no),
					builder.equal(root.get(SmpBatchNS_.batch_prod_id), product_id),
					builder.equal(root.get(SmpBatchNS_.batch_status), A)));
			list = entityManager.createQuery(criteriaQuery).getResultList();
			if(list != null && list.size()>0) 
				return list.get(0);
			}
		catch(Exception e) {
			System.out.println("Error Occurred :" +e.getMessage());// uncomment asneeded --;
		}
		 return null;
	}

	@Override
	public List<SmpBatch> getBatchListByProdId_and_LocId(String prodId, String emp_loc) {

		EntityManager em = null;
		List<SmpBatch> list = new ArrayList<SmpBatch>();
		try {
			em = emf.createEntityManager();
			String q="";
			if(!prodId.equalsIgnoreCase("0")) {
			 q="SELECT batch_id,batch_no FROM smpbatch WHERE batch_status='A' and BATCH_PROD_ID=:prodId and  BATCH_LOC_ID =:emp_loc ORDER BY batch_no";
			}
			else {
				 q="SELECT batch_id,batch_no FROM smpbatch WHERE batch_status='A'   ORDER BY batch_no";
			}
			Query query = em.createNativeQuery(q,Tuple.class);
			if(!prodId.equalsIgnoreCase("0")) {
			query.setParameter("prodId", prodId);
			query.setParameter("emp_loc", emp_loc);
//			query.setParameter("emp_loc", emp_loc);
			}
			List<Tuple> tuples = query.getResultList();			
			if (tuples != null && !tuples.isEmpty()) {
				for (Tuple t : tuples) {
					SmpBatch sb=new SmpBatch();
					sb.setBatch_id(Long.valueOf(t.get("batch_id",Integer.class)));
					sb.setBatch_no(t.get("batch_no",String.class));
					list.add(sb);
				}
				if(!list.isEmpty() && list.size() > 0)
					return list;
				}
			}
		catch(Exception e) {
			throw e;
		}
		finally {
				if (em != null) { em.close(); }
		}
		return list;
	}
}
