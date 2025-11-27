package com.excel.repository;

import java.math.BigDecimal;
import java.util.ArrayList;
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
import com.excel.bean.StockWithdrawalBean;
import com.excel.model.Company;
import com.excel.model.SG_d_parameters_details;
import com.excel.model.SWV_Header;
import com.excel.model.SmpItem;
import com.excel.model.Supplier;
import com.excel.model.V_SWV_Detail;
import com.excel.model.V_SWV_Header;
import com.excel.utility.CodifyErrors;
import com.excel.utility.Utility;

@Repository
public class StockWithdrawalRepositoryImpl implements StockWithdrawalRepository {
	
	@PersistenceContext private EntityManager entityManager;
	@Autowired private EntityManagerFactory emf;
	
	@SuppressWarnings("unchecked")
	@Override
	public List<SmpItem> getProdListForSWV(Long loc_id) throws Exception {
		List<SmpItem> list = new ArrayList<SmpItem>();
		EntityManager em = null;
		try {
			em = emf.createEntityManager();
			StringBuffer sb = new StringBuffer();
			sb.append(" select smp_prod_id, smp_prod_name+' | '+convert(varchar,DTL.balstockqty) as smp_prod_name, smp_std_div_id,dtl.stock_type as stock_type from smpitem smp ");
			sb.append(" join(select sb.stock_type,sb.batch_prod_id,sum(BATCH_OPEN_STOCK+BATCH_IN_STOCK-BATCH_OUT_STOCK-BATCH_WITH_HELD_QTY) AS BALSTOCKQTY ");
			sb.append(" FROM smpbatch_ns sb where batch_status='A' AND batch_loc_id=:loc_id group by sb.stock_type,sb.batch_prod_id ");
			sb.append(" having sum(BATCH_OPEN_STOCK+BATCH_IN_STOCK-BATCH_OUT_STOCK-BATCH_WITH_HELD_QTY)>0)dtl ");
			sb.append(" on dtl.batch_prod_id=smp.smp_prod_id where smp.smp_status='A' order by smp_prod_name ");
			Query query = em.createNativeQuery(sb.toString(), Tuple.class);
			query.setParameter("loc_id", loc_id);
			
			List<Tuple> tuples = query.getResultList();
			SmpItem st = null;
			if (tuples != null && !tuples.isEmpty()) {
				for(Tuple t : tuples) {
					st=new SmpItem();
					st.setSmp_prod_id(Long.valueOf(t.get("smp_prod_id", Integer.class)));
					st.setSmp_prod_name(t.get("smp_prod_name", String.class));
					st.setSmp_std_div_id(Long.valueOf(t.get("smp_std_div_id", Integer.class)));
					st.setStorage_type(t.get("stock_type", String.class));
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

	@SuppressWarnings("unchecked")
	@Override
	public List<Supplier> getSuppliersBySupplierType(String supplierType,Long subCompId) throws Exception {
		EntityManager em = null;
		List<Supplier> list = null;
		try {
			em = emf.createEntityManager();
			StringBuffer sb = new StringBuffer();
			sb.append(" select s.sup_id AS sup_id, s.sup_nm AS sup_nm from Supplier s left join Location l on l.loc_sup_id = s.sup_id ");
			sb.append(" where s.sup_status = 'A' and s.sup_type=:supp_type and sup_SubComp_id=:subCompId");
			sb.append(" order by s.sup_nm asc ");
			Query query = em.createQuery(sb.toString(),Tuple.class);
			query.setParameter("supp_type", supplierType);
			query.setParameter("subCompId", subCompId);
			List<Tuple> tuples = query.getResultList();
			if (tuples != null && !tuples.isEmpty()) {
				list = new ArrayList<>();
				Supplier object = null;
				for (Tuple t : tuples) {
					object = new Supplier();
					object.setSup_id(t.get("sup_id", Long.class));
					object.setSup_nm(t.get("sup_nm", String.class));
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

	@SuppressWarnings("unchecked")
	@Override
	public Supplier supplierAddrById(Long sup_id) throws Exception {
		EntityManager em = null;
		Supplier obj = null;
		try {
			em = emf.createEntityManager();
			StringBuffer sb = new StringBuffer();
			sb.append(" select s.sup_nm As sup_nm, s.state_id As state_id,s.sup_address1 AS sup_address1, s.sup_address2 AS sup_address2,s.sup_address3 AS sup_address3 from Supplier s ");
			sb.append(" where s.sup_id = :sup_id ");
			Query query = em.createQuery(sb.toString(),Tuple.class);
			query.setParameter("sup_id", sup_id);
			List<Tuple> tuples = query.getResultList();
			if (tuples != null && !tuples.isEmpty()) {
				obj=new Supplier();
				for (Tuple t : tuples) {
					obj.setSup_nm(t.get("sup_nm", String.class));
					obj.setSup_address1(t.get("sup_address1", String.class));			
					obj.setSup_address2(t.get("sup_address2", String.class));	
					obj.setSup_address3(t.get("sup_address3", String.class));	
					obj.setState_id(t.get("state_id",Long.class));

				}
			}
		} finally {
			if (em != null) { em.close(); }
		}
		return obj;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<StockWithdrawalBean> getBatchDetailsByProdStkType(Long loc_id, String stk_type, Long prod_id)
			throws Exception {
		EntityManager em = null;
		List<StockWithdrawalBean> list = null;
		try {
			em = emf.createEntityManager();
			StringBuffer sb = new StringBuffer();
			sb.append(" select dtl.batch_id batch_id, dtl.batch_no batch_no,convert(varchar,sb.BATCH_MFG_DT,103) mfg_dt, ");
			sb.append(" convert(varchar,sb.BATCH_EXPIRY_DT,103) exp_dt, convert(varchar,DTL.balstockqty) stock, ");
			sb.append(" sb.BATCH_RATE rate, 0 qty, 0 cases, 0 detail_id,smp.smp_std_div_id divId from smpitem smp join(select sb.batch_prod_id,sb.batch_no,sb.BATCH_ID, ");
			sb.append(" sum(BATCH_OPEN_STOCK+BATCH_IN_STOCK-BATCH_OUT_STOCK-BATCH_WITH_HELD_QTY) AS BALSTOCKQTY ");
			sb.append(" FROM smpbatch_ns sb where batch_status='A' and batch_loc_id = :loc_id and stock_type = :stk_type ");
			sb.append(" group by sb.batch_prod_id,sb.BATCH_ID,sb.batch_no ");
			sb.append(" having sum(BATCH_OPEN_STOCK+BATCH_IN_STOCK-BATCH_OUT_STOCK-BATCH_WITH_HELD_QTY)>0)dtl ");
			sb.append(" on dtl.batch_prod_id=smp.smp_prod_id join smpbatch_ns sb on sb.batch_no=dtl.batch_no ");
			sb.append(" and sb.batch_prod_id=dtl.batch_prod_id where smp.smp_status='A' and smp_prod_id=:prod_id ");
			sb.append(" and sb.stock_type = :stk_type and batch_loc_id = :loc_id");
			Query query = em.createNativeQuery(sb.toString(),Tuple.class);
			query.setParameter("loc_id", loc_id);
			query.setParameter("stk_type", stk_type);
			query.setParameter("prod_id", prod_id);
			List<Tuple> tuples = query.getResultList();
			if (tuples != null && !tuples.isEmpty()) {
				list = new ArrayList<>();
				StockWithdrawalBean bean = null;
				for (Tuple t : tuples) {
					bean = new StockWithdrawalBean();
					bean.setBatch_id(Long.valueOf(t.get("batch_id", Integer.class)));
					bean.setDetail_id(Long.valueOf(t.get("detail_id", Integer.class)));
					bean.setBatch_no(t.get("batch_no", String.class));
					bean.setMfg_dt(t.get("mfg_dt", String.class));
					bean.setExp_dt(t.get("exp_dt", String.class));
					bean.setQty(BigDecimal.valueOf(Double.valueOf(t.get("qty", Integer.class))));
					bean.setRate(t.get("rate", BigDecimal.class));
					bean.setStock(BigDecimal.valueOf(Double.valueOf(t.get("stock", String.class))));
					bean.setCases(t.get("cases", Integer.class));
					bean.setDivId(Long.valueOf(t.get("divId", Integer.class)));
					list.add(bean);
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
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public StockWithdrawalBean saveSWV(StockWithdrawalBean bean) throws Exception {
		StoredProcedureQuery query = entityManager.createStoredProcedureQuery("p_iu_stock_withdrawal_java");
		query.registerStoredProcedureParameter(1, String.class, ParameterMode.IN);
		query.registerStoredProcedureParameter(2, String.class, ParameterMode.IN);
		query.registerStoredProcedureParameter(3, String.class, ParameterMode.IN);
		query.registerStoredProcedureParameter(4, Long.class, ParameterMode.IN);
		query.registerStoredProcedureParameter(5, String.class, ParameterMode.IN);
		query.registerStoredProcedureParameter(6, Long.class, ParameterMode.IN);
		query.registerStoredProcedureParameter(7, String.class, ParameterMode.IN);
		query.registerStoredProcedureParameter(8, String.class, ParameterMode.IN);
		query.registerStoredProcedureParameter(9, String.class, ParameterMode.IN);
		query.registerStoredProcedureParameter(10, String.class, ParameterMode.IN);
		query.registerStoredProcedureParameter(11, String.class, ParameterMode.IN);
		query.registerStoredProcedureParameter(12, BigDecimal.class, ParameterMode.IN);
		query.registerStoredProcedureParameter(13, BigDecimal.class, ParameterMode.IN);
		query.registerStoredProcedureParameter(14, String.class, ParameterMode.IN);
		query.registerStoredProcedureParameter(15, String.class, ParameterMode.IN);
		query.registerStoredProcedureParameter(16, String.class, ParameterMode.IN);
		query.registerStoredProcedureParameter(17, String.class, ParameterMode.IN);
		query.registerStoredProcedureParameter(18, String.class, ParameterMode.IN);
		query.registerStoredProcedureParameter(19, String.class, ParameterMode.IN);
		query.registerStoredProcedureParameter(20, Integer.class, ParameterMode.IN);
		query.registerStoredProcedureParameter(21, String.class, ParameterMode.IN);
		query.registerStoredProcedureParameter(22, Integer.class, ParameterMode.OUT);
		query.registerStoredProcedureParameter(23, Long.class, ParameterMode.IN);
		query.registerStoredProcedureParameter(24, Long.class, ParameterMode.IN);
		query.registerStoredProcedureParameter(25, Long.class, ParameterMode.IN);
		query.registerStoredProcedureParameter(26, Long.class, ParameterMode.IN);
		query.registerStoredProcedureParameter(27, String.class, ParameterMode.IN);
		query.registerStoredProcedureParameter(28, BigDecimal.class, ParameterMode.IN);
		query.registerStoredProcedureParameter(29, BigDecimal.class, ParameterMode.IN);
		query.registerStoredProcedureParameter(30, Integer.class, ParameterMode.IN);
		query.registerStoredProcedureParameter(31, String.class, ParameterMode.IN);
		query.registerStoredProcedureParameter(32, String.class, ParameterMode.IN);
		query.registerStoredProcedureParameter(33, String.class, ParameterMode.OUT);
		query.registerStoredProcedureParameter(34, String.class, ParameterMode.IN);
		query.registerStoredProcedureParameter(35, String.class, ParameterMode.IN);
		query.registerStoredProcedureParameter(36, Date.class, ParameterMode.IN);
		
		query.setParameter(1,bean.getCompanyCode());
		query.setParameter(2,bean.getCurrFinYear());
		query.setParameter(3, bean.getCurrPeriodCode());
		query.setParameter(4, bean.getSendLocId());
		query.setParameter(5, bean.getTransporter() == null ? "" : bean.getTransporter());
		query.setParameter(6, bean.getStateId());
		query.setParameter(7, bean.getSenderName() == null ? "" : bean.getSenderName());
		query.setParameter(8, bean.getAddress1() == null ? "" :  bean.getAddress1());
		query.setParameter(9, bean.getAddress2() == null ? "" :  bean.getAddress2());
		query.setParameter(10, bean.getAddress3() == null ? "" :  bean.getAddress3());
		query.setParameter(11, bean.getDestination() == null ? "" :  bean.getDestination());
		query.setParameter(12, BigDecimal.ZERO);
		query.setParameter(13, BigDecimal.ZERO);
		query.setParameter(14, bean.getLrnumber() == null ? "" :  bean.getLrnumber());
		query.setParameter(15, bean.getNlrdate() == null ? "" :   bean.getNlrdate());
		query.setParameter(16, bean.getDisplayMsg() == null ? "" :  bean.getDisplayMsg());
		query.setParameter(17, bean.getEmpId());
		query.setParameter(18, bean.getIpAddress());
		query.setParameter(19, "A");
		query.setParameter(20, bean.getNoOfCases().intValue());
		query.setParameter(21, bean.getRemarks() == null ? "" :  bean.getRemarks());
		query.setParameter(23, bean.getHeaderId());
		query.setParameter(24, bean.getProductId());
		query.setParameter(25, bean.getDivId());
		query.setParameter(26, bean.getpBatchId());
		query.setParameter(27, bean.getStockType());
		query.setParameter(28, bean.getpBatchQty());
		query.setParameter(29, bean.getpBatchRate());
		query.setParameter(30, bean.getpBatchCases().intValue());
		query.setParameter(31, "I");
		query.setParameter(32, "");
		query.setParameter(34, bean.getAppr_req());
		query.setParameter(35,bean.getStkWithdrawalType() == null ? "" : bean.getStkWithdrawalType());
		query.setParameter(36, Utility.convertStringtoDate(bean.getNstkWithdrawalDate()));
		query.execute();
		
		Integer header_id = (Integer) query.getOutputParameterValue(22);
		String doc_no = (String) query.getOutputParameterValue(33);
		bean.setHeaderId(Long.valueOf(header_id));
		bean.setStkWithdrawalNo(doc_no);
		return bean;
	}

	@Override
	public List<V_SWV_Detail> getSWVDetailsBySWVId(Long swv_id) throws Exception {
		List<V_SWV_Detail> list = null;
		try {
			String q="select * from V_SWV_Detail where swvdtl_swv_id=:id";
			Query query = entityManager.createNativeQuery(q,V_SWV_Detail.class);
			query.setParameter("id", swv_id);
			list = query.getResultList();
		} finally {
		}
		return list;
	}

	@Override
	public List<V_SWV_Header> getSWVListForModify(Long loc_id, String comp_cd, String emp_id, String user_type,
			String from_dt, String to_dt) throws Exception {
		EntityManager em = null;
		List<V_SWV_Header> list = null;
		try {
			em = emf.createEntityManager();
			StringBuffer sb=new StringBuffer();
			sb.append("select * from V_SWV_Header where swv_loc_id=:loc_id and swv_status='A' and swv_appr_status='E' and ");
			sb.append("swv_company=:comp_cd ");
			if(!user_type.equalsIgnoreCase("S")) {
				sb.append(" and swv_ins_usr_id=:emp_id ");
			}
			if(from_dt!=null && from_dt.length()>0){
			sb.append(" and CONVERT(DATETIME,swv_challan_dt,103) >= convert(datetime,'"+from_dt+"',103)  ");
			}
			if(to_dt!=null && to_dt.length()>0){
			sb.append(" and CONVERT(DATETIME,swv_challan_dt,103) <= convert(datetime,'"+to_dt+"',103) ");
			}
			sb.append("order by swv_challan_no");
			Query query = em.createNativeQuery(sb.toString(),V_SWV_Header.class);
			query.setParameter("loc_id", loc_id);
			query.setParameter("comp_cd", comp_cd);
			if(!user_type.equalsIgnoreCase("S")) {
				query.setParameter("emp_id", emp_id);
			}
			list = query.getResultList();
		} finally {
			if(em != null) { em.close(); }
		}
		return list;
	}

	@Override
	public SWV_Header getSWVHeaderById(Long swv_id) throws Exception {
		SWV_Header swv_Header=null;
		try {
			String q="from SWV_Header where swv_id=:id";
			Query query = entityManager.createQuery(q,SWV_Header.class);
			query.setParameter("id", swv_id);
			List<SWV_Header> list = query.getResultList();
			if(list.size()>0) {
				swv_Header=list.get(0);
			}
		} finally {
		}
		return swv_Header;
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public void deleteSWVDetail(String comp_cd, String fin_year, String period_cd, Long swv_id, Long prod_id,
			Long batch_id, String stk_type, String user_id, String ip_addr) throws Exception {
		StoredProcedureQuery query = entityManager.createStoredProcedureQuery("p_iu_STOCK_WITHDRAWAL_DELETE_JAVA");
		query.registerStoredProcedureParameter(1, String.class, ParameterMode.IN);
		query.registerStoredProcedureParameter(2, String.class, ParameterMode.IN);
		query.registerStoredProcedureParameter(3, String.class, ParameterMode.IN);
		query.registerStoredProcedureParameter(4, Long.class, ParameterMode.IN);
		query.registerStoredProcedureParameter(5, Long.class, ParameterMode.IN);
		query.registerStoredProcedureParameter(6, Long.class, ParameterMode.IN);
		query.registerStoredProcedureParameter(7, String.class, ParameterMode.IN);
		query.registerStoredProcedureParameter(8, String.class, ParameterMode.IN);
		query.registerStoredProcedureParameter(9, String.class, ParameterMode.IN);
		
		query.setParameter(1,comp_cd==null?"":comp_cd);
		query.setParameter(2,fin_year==null?"":fin_year);
		query.setParameter(3, period_cd==null?"":period_cd);
		query.setParameter(4, swv_id);
		query.setParameter(5, prod_id);
		query.setParameter(6, batch_id);
		query.setParameter(7, stk_type);
		query.setParameter(8, user_id);
		query.setParameter(9, ip_addr);
		query.execute();
	}

	@SuppressWarnings("unchecked")
	@Override
	public V_SWV_Header getSWVHeaderDetailsById(Long swv_id) throws Exception {
		EntityManager em = null;
		V_SWV_Header swv_Header=null;
		try {
			em = emf.createEntityManager();
			String q="from V_SWV_Header where swv_id=:id";
			Query query = em.createQuery(q,V_SWV_Header.class);
			query.setParameter("id", swv_id);
			List<V_SWV_Header> list = query.getResultList();
			if(list.size()>0) {
				swv_Header=list.get(0);
			}
		}finally {
			if(em != null) { em.close(); }
		}
		return swv_Header;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<StockWithdrawalBean> getModBatchDetailsByProdStkType(Long loc_id, String stk_type, Long prod_id,
			Long swv_id) throws Exception {
		EntityManager em = null;
		List<StockWithdrawalBean> list = null;
		try {
			em = emf.createEntityManager();
			StringBuffer sql = new StringBuffer();
			sql.append(" select batch_id,batch_no,mfg_dt,exp_dt,sum(stock) stock,rate,sum(qty) qty, ");
			sql.append(" sum(cases) cases,sum( detail_id) detail_id,divId from (select dtl.batch_id batch_id, dtl.batch_no batch_no, ");
			sql.append(" convert(varchar,sb.BATCH_MFG_DT,103) mfg_dt ,convert(varchar,sb.BATCH_EXPIRY_DT,103) exp_dt, ");
			sql.append(" BALSTOCKQTY stock,sb.BATCH_RATE rate, 0 qty, 0 cases, 0 detail_id,smp.smp_std_div_id divId from smpitem smp join(select sb.batch_prod_id,sb.batch_no,sb.BATCH_ID, ");
			sql.append(" sum(BATCH_OPEN_STOCK+BATCH_IN_STOCK-BATCH_OUT_STOCK-BATCH_WITH_HELD_QTY) AS BALSTOCKQTY ");
			sql.append(" FROM smpbatch_ns sb where batch_status='A' and batch_loc_id = :loc_id and stock_type = :stk_type ");
			sql.append(" and sb.BATCH_PROD_ID = :prod_id group by sb.batch_prod_id,sb.BATCH_ID,sb.batch_no ");
			sql.append(" having sum(BATCH_OPEN_STOCK+BATCH_IN_STOCK-BATCH_OUT_STOCK-BATCH_WITH_HELD_QTY)>0)dtl ");
			sql.append(" on dtl.batch_prod_id=smp.smp_prod_id join smpbatch_ns sb on sb.batch_no=dtl.batch_no ");
			sql.append(" and sb.batch_prod_id=dtl.batch_prod_id where smp.smp_status='A' and smp_prod_id= :prod_id and sb.stock_type = :stk_type");
			sql.append(" and sb.batch_loc_id=:loc_id union all select sb.batch_id, sb.batch_no,convert(varchar,sb.BATCH_MFG_DT,103) mfg_dt, ");
			sql.append(" convert(varchar,sb.BATCH_EXPIRY_DT,103) exp_dt, sw.SWVDTL_DISP_QTY stock, sb.BATCH_RATE rate, sw.SWVDTL_DISP_QTY qty, ");
			sql.append(" sw.SWVDTL_CASES cases, sw.SWVDTL_ID detail_id,sw.swvdtl_div_id divId  FROM smpbatch_ns sb , SWV_detail sw where sw.SWVDTL_SWV_ID = :swv_id and sw.SWVDTL_BATCH_ID = batch_id ");
			sql.append(" and sw.SWVDTL_STOCK_TYPE = :stk_type and sw.SWVDTL_PROD_ID = :prod_id and sb.stock_type = :stk_type and sw.swvdtl_loc_id=:loc_id) dtl ");
			sql.append(" group by batch_id,batch_no,mfg_dt,exp_dt,rate,divId ");
			
			Query query = em.createNativeQuery(sql.toString(),Tuple.class);
			query.setParameter("loc_id", loc_id);
			query.setParameter("stk_type", stk_type);
			query.setParameter("prod_id", prod_id);
			query.setParameter("swv_id", swv_id);
			List<Tuple> tuples = query.getResultList();
			if (tuples != null && !tuples.isEmpty()) {
				list = new ArrayList<>();
				StockWithdrawalBean bean = null;
				for (Tuple t : tuples) {
					bean = new StockWithdrawalBean();
					bean.setBatch_id(Long.valueOf(t.get("batch_id", Integer.class)));
					bean.setDetail_id(Long.valueOf(t.get("detail_id", Integer.class)));
					bean.setBatch_no(t.get("batch_no", String.class));
					bean.setMfg_dt(t.get("mfg_dt", String.class));
					bean.setExp_dt(t.get("exp_dt", String.class));
					bean.setQty(t.get("qty", BigDecimal.class));
					bean.setRate(t.get("rate", BigDecimal.class));
					bean.setStock(t.get("stock", BigDecimal.class));
					bean.setCases(t.get("cases", Integer.class));
					bean.setDivId(Long.valueOf(t.get("divId", Integer.class)));
					list.add(bean);
				}
			if(!list.isEmpty() && list.size() > 0)
				return list;
			}
		} finally {
			if (em != null) { em.close(); }
		}
		return list;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<SmpItem> getModProdListForSWV(Long loc_id, Long swv_id) throws Exception {
		List<SmpItem> list = new ArrayList<SmpItem>();
		EntityManager em = null;
		try {
			em = emf.createEntityManager();
			StringBuffer sql = new StringBuffer();
			sql.append(" SELECT	DISTINCT swvdtl_prod_id as smp_prod_id,	smp_prod_name + ' | ' +convert(varchar,( ");
			sql.append(" SELECT	SUM(batch_open_stock + batch_in_stock - batch_out_stock - batch_with_held_qty ) ");
			sql.append(" FROM smpbatch_ns sb WHERE sb.batch_prod_id = sv.swvdtl_prod_id	and batch_status = 'A' ");
			sql.append(" and batch_loc_id = :loc_id AND batch_open_stock + batch_in_stock - batch_out_stock - batch_with_held_qty > 0 ");
			sql.append(" )) AS smp_prod_name, swvdtl_div_id as smp_std_div_id FROM v_SWV_DETAIL sv ");
			sql.append(" WHERE sv.SWVDTL_SWV_ID = :swv_id ORDER BY smp_prod_name");
			Query query = em.createNativeQuery(sql.toString(), Tuple.class);
			query.setParameter("loc_id", loc_id);
			query.setParameter("swv_id", swv_id);
			List<Tuple> tuples = query.getResultList();
			SmpItem st = null;
			if (tuples != null && !tuples.isEmpty()) {
				for(Tuple t : tuples) {
					st=new SmpItem();
					st.setSmp_prod_id(Long.valueOf(t.get("smp_prod_id", Integer.class)));
					st.setSmp_prod_name(t.get("smp_prod_name", String.class));
					st.setSmp_std_div_id(Long.valueOf(t.get("smp_std_div_id", Integer.class)));
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

	@SuppressWarnings("unchecked")
	@Override
	public List<SG_d_parameters_details> getStockTypeListByProd(Long swv_id, Long prod_id) throws Exception {
		EntityManager em = null;
		List<SG_d_parameters_details> list = null;
		try {
			em = emf.createEntityManager();
			StringBuffer sb = new StringBuffer();
			sb.append(" from V_SWV_Detail s  where ");
			sb.append(" s.swvdtl_swv_id=:swv_id and s.swvdtl_prod_id=:prod_id");
			Query query = em.createQuery(sb.toString(),V_SWV_Detail.class);
			query.setParameter("swv_id", swv_id);
			query.setParameter("prod_id", prod_id);
			List<V_SWV_Detail> tuples = query.getResultList();
			if (tuples != null && !tuples.isEmpty()) {
				list = new ArrayList<>();
				SG_d_parameters_details object = null;
				for (V_SWV_Detail t : tuples) {
					object = new SG_d_parameters_details();
					object.setSgprmdet_nm(t.getSwvdtl_stock_type());
					object.setSgprmdet_disp_nm(t.getStock_type_name());
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

	@SuppressWarnings("unchecked")
	@Override
	public List<Object> getChallanForLr(Long loc_id) throws Exception {
		EntityManager em = null;
		List<Object> list = null;
		try {
			em = emf.createEntityManager();
			StringBuffer buffer = new StringBuffer();
			buffer.append("SELECT DH.SWV_ID,DH.SWV_CHALLAN_NO, convert(varchar(10),DH.SWV_CHALLAN_DT,103) as SWV_CHALLAN_DT, ")
					.append("DH.SWV_TOTAL_GOODS_VAL, SWV_WT,SWV_CASES, SWV_TRANSPORTER, SWV_LR_NO, ")
					.append("CONVERT(varchar(10),SWV_LR_DT,103) as SWV_LR_DT ")
					.append("FROM SWV_HEADER DH where DH.SWV_status = 'A' and DH.SWV_LR_NO = '' ")
					.append("and DH.SWV_LOC_ID=:loc_id and DH.SWV_APPR_STATUS='A' order by DH.SWV_CHALLAN_NO ");
			Query query = em.createNativeQuery(buffer.toString());
			query.setParameter("loc_id", loc_id);
			list = query.getResultList();
		} finally {
			if (em != null) { em.close(); }
		}
		return list;
	}

}
