package com.excel.repository;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.ParameterMode;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.StoredProcedureQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.excel.bean.IAABean;
import com.excel.model.IaaDetailForApprovalMail;
import com.excel.model.IaaDetailForMail;
import com.excel.model.Iaa_Map;
import com.excel.model.Iaa_Map_;
import com.excel.model.Stock_Adj_Details;
import com.excel.model.Stock_Adj_Header;

@Repository
public class IAARepositoryImpl implements IAARepository {
	
	@PersistenceContext private EntityManager entityManager;
	@Autowired private EntityManagerFactory emf;

	@Override
	public List<Iaa_Map> getReasonsFromIaaMap() throws Exception {
		EntityManager em = null;
		List<Iaa_Map> list = null;
		try {
			em = emf.createEntityManager();
			CriteriaBuilder builder = em.getCriteriaBuilder();
			CriteriaQuery<Iaa_Map> query = builder.createQuery(Iaa_Map.class);
			Root<Iaa_Map> root = query.from(Iaa_Map.class);
			query.multiselect(root.get(Iaa_Map_.reason_id), root.get(Iaa_Map_.reason_nm)).orderBy(builder.asc(root.get(Iaa_Map_.reason_nm)));
			list = em.createQuery(query).getResultList();
		} finally {
			if(em != null) { em.close(); }
		}
		if(list != null && list.size() > 0) {
			return list;
		}
		return null;
	}

	@SuppressWarnings("unchecked")
	@Override
	public Object getIaaDetailById(Long reasonId) throws Exception {
		EntityManager em = null;
		Object result=null;
		try {
			em = emf.createEntityManager();
			StringBuffer sb = new StringBuffer();
			sb.append("select I1.In_item_accept, I1.In_batch_accept, I1.Out_stock_type, I1.In_stock_type, I1.out_tran_type, ");
			sb.append("I1.in_tran_type, I1.New_batch_accept, I1.In_qty_accept, I1.In_rate_accept, I1.Item_change_accept, ");
			sb.append("CASE WHEN SG1.SGprmdet_SGprm_id IS NULL THEN SG2.SGprmdet_id ELSE SG1.SGprmdet_id END SGprmdet_id, ");
			sb.append("CASE WHEN SG1.SGprmdet_SGprm_id IS NULL THEN SG2.SGprmdet_nm ELSE SG1.SGprmdet_nm END Out_stock_type_a, ");
			sb.append("SG2.SGprmdet_id SGprmdet_id_1, SG2.SGprmdet_nm in_stock_type_a, SG2.SGprmdet_disp_nm instk, ");
			sb.append("CASE WHEN SG1.SGprmdet_SGprm_id IS NULL THEN SG2.SGprmdet_disp_nm ELSE SG1.SGprmdet_disp_nm END outstk ");
			sb.append("from IAA_map I1 LEFT join sg_d_parameters_details SG1 on SG1.SGprmdet_nm=I1.Out_stock_type  ");
			sb.append("AND sg1.SGprmdet_SGprm_id = 16 join sg_d_parameters_details SG2 on SG2.SGprmdet_nm=I1.In_stock_type ");
			sb.append("AND sg2.SGprmdet_SGprm_id = 16 ");
			sb.append("where Reason_ID=").append(reasonId);
			Query query = em.createNativeQuery(sb.toString());
			List<Object> list  = query.getResultList();
			if(list!=null && list.size()>0){
				result=list.get(0);
			}
		} finally {
			if (em != null) { em.close(); }
		}
		return result;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Object> getProductListWithPack(String comp_cd, long sub_comp_id, String prefix, String prod_type,
			long prod_grp, long div_id) throws Exception {
		EntityManager em = null;
		List<Object>  list=null;
		try {
			em = emf.createEntityManager();
			StringBuffer sb = new StringBuffer();
			sb.append(" select smp_prod_id, smp_prod_name, SMP_EXPIRY_RQ_IND, SMP_BATCH_RQ_IND, PACK_DISP_NM, smp_prod_cd, ")
			.append(" gcma_req_ind,gcma_expiry_dt,smp_prod_type_id,smp_prod_type, ")
			.append("  smp_rate,SMP_COSTING_RATE,SMP_MKTG_RATE,SMP_NRV,SMP_DISPLAY_RATE ")
			.append(" from SMPITEM, PACKMASTER where SMP_PROD_NAME like '").append(prefix + "%' and ")
			.append(" SMP_COMPANY = :comp_cd and SMP_SubComp_id = (case :sub_comp_id when 0 then SMP_SubComp_id else ")
			.append(" :sub_comp_id end) and SMP_SA_PROD_GROUP =(case :prod_grp when 0 then SMP_SA_PROD_GROUP else ")
			.append(" :prod_grp end) and SMP_STD_DIV_ID =(case :div_id when 0 then SMP_STD_DIV_ID else :div_id end) ");
			if(prod_type!=null && prod_type.length()>0) {
				sb.append("and SMP_PROD_TYPE like '%"+prod_type+"%'");
			}
			sb.append(" and SMP_PACK_ID = PACK_ID AND SMP_status='A' order by smp_prod_name");
			Query query = em.createNativeQuery(sb.toString());
			query.setParameter("comp_cd", comp_cd);
			query.setParameter("sub_comp_id", sub_comp_id);
			query.setParameter("prod_grp", prod_grp);
			query.setParameter("div_id", div_id);
			list  = query.getResultList();
		} finally {
			if (em != null) { em.close(); }
		}
		return list;
	}

	@SuppressWarnings("unchecked")
	@Override
	public Object getQtyAndValueNS(Long prod_id, String flag, Long loc_id, Long batch_id, String stock_type)
			throws Exception {
		EntityManager em = null;
		Object result=null;
		try {
			em = emf.createEntityManager();
			StoredProcedureQuery query = em.createStoredProcedureQuery("p_get_qty_ns");

			query.registerStoredProcedureParameter(1, Long.class, ParameterMode.IN);
			query.registerStoredProcedureParameter(2, String.class, ParameterMode.IN);
			query.registerStoredProcedureParameter(3, Long.class, ParameterMode.IN);
			query.registerStoredProcedureParameter(4, Long.class, ParameterMode.IN);
			query.registerStoredProcedureParameter(5, String.class, ParameterMode.IN);

			query.setParameter(1, prod_id);
			query.setParameter(2, flag);
			query.setParameter(3, loc_id);
			query.setParameter(4, batch_id);
			query.setParameter(5, stock_type);
			List<Object> resultList=query.getResultList();
			System.out.println("resultList::"+resultList.size());
			if(resultList!=null && resultList.size()>0){
				result=resultList.get(0);
			}
		} finally {
			if (em != null) { em.close(); }
		}
		return result;
	}

	@SuppressWarnings("unchecked")
	@Override
	public Object getQtyAndValue(Long prod_id, String flag, Long loc_id, Long batch_id) throws Exception {
		EntityManager em = null;
		Object result=null;
		try {
			em = emf.createEntityManager();
			StoredProcedureQuery query = em.createStoredProcedureQuery("p_get_qty");

			query.registerStoredProcedureParameter(1, Long.class, ParameterMode.IN);
			query.registerStoredProcedureParameter(2, String.class, ParameterMode.IN);
			query.registerStoredProcedureParameter(3, Long.class, ParameterMode.IN);
			query.registerStoredProcedureParameter(4, Long.class, ParameterMode.IN);

			query.setParameter(1, prod_id);
			query.setParameter(2, flag);
			query.setParameter(3, loc_id);
			query.setParameter(4, batch_id);
			List<Object> resultList=query.getResultList();
			if(resultList!=null && resultList.size()>0){
				result=resultList.get(0);
			}
		} finally {
			if (em != null) { em.close(); }
		}
		return result;
	}

	@SuppressWarnings("unchecked")
	@Override
	public Object getQuarantineQtyAndValue(Long prod_id, String flag, Long loc_id, Long batch_id) throws Exception {
		EntityManager em = null;
		Object result=null;
		try {
			em = emf.createEntityManager();
			StoredProcedureQuery query = em.createStoredProcedureQuery("p_get_qtyQuaReturn");

			query.registerStoredProcedureParameter(1, Long.class, ParameterMode.IN);
			query.registerStoredProcedureParameter(2, String.class, ParameterMode.IN);
			query.registerStoredProcedureParameter(3, Long.class, ParameterMode.IN);
			query.registerStoredProcedureParameter(4, Long.class, ParameterMode.IN);

			query.setParameter(1, prod_id);
			query.setParameter(2, flag);
			query.setParameter(3, loc_id);
			query.setParameter(4, batch_id);
			List<Object> resultList=query.getResultList();
			if(resultList!=null && resultList.size()>0){
				result=resultList.get(0);
			}
		} finally {
			if (em != null) { em.close(); }
		}
		return result;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Object> getBatchListForIAA(Long loc_id, Long prod_id, String stock_type,String stock_type2, String status)
			throws Exception {
		EntityManager em = null;
		List<Object>  list=null;
		System.out.println("loc_id::"+loc_id);
		System.out.println("prod_id::"+prod_id);
		System.out.println("stock_type::"+stock_type);
		System.out.println("status::"+status);
		try {
			em = emf.createEntityManager();
			StringBuffer sb = new StringBuffer();
			if(stock_type.equalsIgnoreCase("01")){
				
				sb.append("SELECT BM.batch_id, BM.BATCH_NO, bm.batch_expiry_dt, bm.batch_rate, ");
				if(stock_type2.equalsIgnoreCase("13")) {
					sb.append("BM.BATCH_NO +'  '+ CONVERT(VARCHAR, BM.BATCH_OPEN_STOCK+BM.BATCH_IN_STOCK-BM.BATCH_OUT_STOCK-BM.BATCH_WITH_HELD_QTY+BM.QUANRANTINE) AS DESCRIPTION ");
				}
				else {
					sb.append("BM.BATCH_NO +'  '+ CONVERT(VARCHAR, BM.BATCH_OPEN_STOCK+BM.BATCH_IN_STOCK-BM.BATCH_OUT_STOCK-BM.BATCH_WITH_HELD_QTY) AS DESCRIPTION ");
				}
					sb.append("FROM SMPBATCH BM, SMPITEM PM ")
					.append("WHERE BM.BATCH_PROD_ID = PM.SMP_PROD_ID ")
					.append("AND PM.SMP_PROD_ID=:prod_id AND BM.BATCH_LOC_ID=:loc_id ")
					.append("AND BM.BATCH_status='A' ");
				if(stock_type2.equalsIgnoreCase("13")) {
					sb.append("and BM.QUANRANTINE>0");
				}
					//.append(" and (BM.BATCH_OPEN_STOCK+BM.BATCH_IN_STOCK-BM.BATCH_OUT_STOCK-BM.BATCH_WITH_HELD_QTY) > 0 ");
				if(status.equalsIgnoreCase("EXP")){
					 sb.append("AND BM.BATCH_EXPIRY_DT < (getdate()+PM.SMP_SHORT_EXPIRY_DAYS) ");
				}else{
					sb.append("AND BM.BATCH_EXPIRY_DT>=getdate() ");
				}
					sb.append("ORDER BY BM.BATCH_NO");
					
			}else if(stock_type.equalsIgnoreCase("10")){
				sb.append("SELECT BM.batch_id, BM.BATCH_NO, bm.batch_expiry_dt, bm.batch_rate, ")
				.append("BM.BATCH_NO +'  '+ CONVERT(VARCHAR, BM.BATCH_OPEN_STOCK+BM.BATCH_IN_STOCK-BM.BATCH_OUT_STOCK-BM.BATCH_WITH_HELD_QTY) AS DESCRIPTION ")
				.append("FROM SMPBATCH BM, SMPITEM PM ")
				.append("WHERE BM.BATCH_PROD_ID = PM.SMP_PROD_ID ")
				.append("AND PM.SMP_PROD_ID=:prod_id AND BM.BATCH_LOC_ID=:loc_id ")
				.append("AND BM.BATCH_status='A' ")
				//.append(" and (BM.BATCH_OPEN_STOCK+BM.BATCH_IN_STOCK-BM.BATCH_OUT_STOCK-BM.BATCH_WITH_HELD_QTY) > 0 ")
				.append("ORDER BY BM.BATCH_NO");
				
			} else {
				sb.append("SELECT BM.batch_id, BM.BATCH_NO, bm.batch_expiry_dt, bm.batch_rate, ")
					.append("BM.BATCH_NO +'  '+ CONVERT(VARCHAR, BM.BATCH_OPEN_STOCK+BM.BATCH_IN_STOCK-BM.BATCH_OUT_STOCK-BM.BATCH_WITH_HELD_QTY+ISNULL(BM.QUANRANTINE,0)) AS DESCRIPTION ")
					.append("FROM SMPBatch_NS BM, SMPITEM PM ")
					.append("WHERE BM.BATCH_PROD_ID = PM.SMP_PROD_ID ")
					.append("AND PM.SMP_PROD_ID=:prod_id AND BM.BATCH_LOC_ID=:loc_id AND BM.STOCK_TYPE=").append(stock_type)
					.append(" AND BM.BATCH_status='A' ");
					//.append(" and (BM.BATCH_OPEN_STOCK+BM.BATCH_IN_STOCK-BM.BATCH_OUT_STOCK-BM.BATCH_WITH_HELD_QTY) > 0 ");
				
				if(!stock_type.equalsIgnoreCase("05")){
					if(status.equalsIgnoreCase("EXP")){
						 sb.append("AND BM.BATCH_EXPIRY_DT < (getdate()+PM.SMP_SHORT_EXPIRY_DAYS) ");
					}else{
						sb.append("AND BM.BATCH_EXPIRY_DT>=getdate() ");
					}
						sb.append("ORDER BY BM.BATCH_NO");
				}
				
			}
			
			Query query = em.createNativeQuery(sb.toString());
			query.setParameter("loc_id", loc_id);
			query.setParameter("prod_id", prod_id);
			list  = query.getResultList();
		} finally {
			if (em != null) { em.close(); }
		}
		return list;
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public Object saveStockAdjHeader(IAABean bean) throws Exception {
		Object[] ret_obj = new Object[2];
		try {
			StoredProcedureQuery query = entityManager.createStoredProcedureQuery("p_iu_STOCK_ADJ_HEADER_JAVA");

			query.registerStoredProcedureParameter(1, Integer.class, ParameterMode.IN);
			query.registerStoredProcedureParameter(2, String.class, ParameterMode.IN);
			query.registerStoredProcedureParameter(3, String.class, ParameterMode.IN);
			query.registerStoredProcedureParameter(4, String.class, ParameterMode.IN);
			query.registerStoredProcedureParameter(5, String.class, ParameterMode.IN);
			query.registerStoredProcedureParameter(6, Long.class, ParameterMode.IN);
			query.registerStoredProcedureParameter(7, String.class, ParameterMode.IN);
			query.registerStoredProcedureParameter(8, String.class, ParameterMode.IN);
			query.registerStoredProcedureParameter(9, String.class, ParameterMode.IN);
			query.registerStoredProcedureParameter(10, String.class, ParameterMode.IN);
			query.registerStoredProcedureParameter(11, String.class, ParameterMode.IN);
			query.registerStoredProcedureParameter(12, String.class, ParameterMode.IN);
			query.registerStoredProcedureParameter(13, Integer.class, ParameterMode.OUT);
			query.registerStoredProcedureParameter(14, Integer.class, ParameterMode.IN);
			query.registerStoredProcedureParameter(15, String.class, ParameterMode.OUT);

			System.out.println("1 "+"0");
			System.out.println("2 "+"");
			System.out.println("3 "+ bean.getCompanyCode());
			System.out.println("4 "+bean.getCurrFinYear());
			System.out.println("5 "+bean.getCurrPeriodCode());
			System.out.println("6 "+bean.getSendLocId());
			System.out.println("7 "+bean.getTrnDate());
			System.out.println("8 "+bean.getHeaderRemark());
			System.out.println("9 "+"");
			System.out.println("10 "+bean.getEmpId());
			System.out.println("11 "+bean.getIpAddress());
			System.out.println("12 "+"A");
			System.out.println("13 "+bean.getAppr_req());
			
			query.setParameter(1, 0);
			query.setParameter(2, "");
			query.setParameter(3, bean.getCompanyCode());
			query.setParameter(4, bean.getCurrFinYear());
			query.setParameter(5, bean.getCurrPeriodCode());
			query.setParameter(6, bean.getSendLocId());
			query.setParameter(7, bean.getTrnDate());
			query.setParameter(8, bean.getHeaderRemark());
			query.setParameter(9, "");
			query.setParameter(10, bean.getEmpId());
			query.setParameter(11, bean.getIpAddress());
			query.setParameter(12, "A");
			query.setParameter(14, Integer.parseInt(bean.getAppr_req()));
			
			query.execute();
			Integer header_id = (Integer) query.getOutputParameterValue(13);
			String return_adj_no = (String) query.getOutputParameterValue(15);
			ret_obj[0]=header_id;
			ret_obj[1]=return_adj_no;
		} finally {
			
		}
		return ret_obj;
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public Long saveStockAdjDetail(IAABean bean) throws Exception {
		Long id=0L;
		try {
			String proc_name="p_iu_STOCK_ADJ_DETAILS_JAVA";
			if(bean.isStock_cfa()) {
				proc_name="p_iu_STOCK_ADJ_DETAILS_JAVA_CFASTOCK";
			}
			StoredProcedureQuery query = entityManager.createStoredProcedureQuery(proc_name);

			query.registerStoredProcedureParameter(1, String.class, ParameterMode.IN);
			query.registerStoredProcedureParameter(2, Long.class, ParameterMode.IN);
			query.registerStoredProcedureParameter(3, Long.class, ParameterMode.IN);
			query.registerStoredProcedureParameter(4, String.class, ParameterMode.IN);
			query.registerStoredProcedureParameter(5, String.class, ParameterMode.IN);
			query.registerStoredProcedureParameter(6, String.class, ParameterMode.IN);
			query.registerStoredProcedureParameter(7, Long.class, ParameterMode.IN);
			query.registerStoredProcedureParameter(8, Long.class, ParameterMode.IN);
			query.registerStoredProcedureParameter(9, Long.class, ParameterMode.IN);
			query.registerStoredProcedureParameter(10, Long.class, ParameterMode.IN);
			query.registerStoredProcedureParameter(11, BigDecimal.class, ParameterMode.IN);
			query.registerStoredProcedureParameter(12, String.class, ParameterMode.IN);
			query.registerStoredProcedureParameter(13, BigDecimal.class, ParameterMode.IN);
			query.registerStoredProcedureParameter(14, Long.class, ParameterMode.IN);
			query.registerStoredProcedureParameter(15, Long.class, ParameterMode.IN);
			query.registerStoredProcedureParameter(16, String.class, ParameterMode.IN);
			query.registerStoredProcedureParameter(17, String.class, ParameterMode.IN);
			query.registerStoredProcedureParameter(18, String.class, ParameterMode.IN);
			query.registerStoredProcedureParameter(19, String.class, ParameterMode.IN);
			query.registerStoredProcedureParameter(20, BigDecimal.class, ParameterMode.IN);
			query.registerStoredProcedureParameter(21, String.class, ParameterMode.IN);
			query.registerStoredProcedureParameter(22, BigDecimal.class, ParameterMode.IN);
			query.registerStoredProcedureParameter(23, String.class, ParameterMode.IN);
			query.registerStoredProcedureParameter(24, String.class, ParameterMode.IN);
			query.registerStoredProcedureParameter(25, String.class, ParameterMode.IN);
			query.registerStoredProcedureParameter(26, String.class, ParameterMode.IN);
			query.registerStoredProcedureParameter(27, String.class, ParameterMode.IN);
			query.registerStoredProcedureParameter(28, String.class, ParameterMode.IN);
			query.registerStoredProcedureParameter(29, String.class, ParameterMode.IN);
			query.registerStoredProcedureParameter(30, String.class, ParameterMode.IN);
			query.registerStoredProcedureParameter(31, String.class, ParameterMode.IN);
			if(bean.isStock_cfa()) {
			query.registerStoredProcedureParameter(32, Integer.class, ParameterMode.IN);
			query.registerStoredProcedureParameter(33, Integer.class, ParameterMode.IN);
			query.registerStoredProcedureParameter(34, String.class, ParameterMode.IN);
			query.registerStoredProcedureParameter(35, String.class, ParameterMode.OUT);
			}

			System.out.println("1 "+bean.getAction());
			System.out.println("2 "+bean.getAdjDtlId());
			System.out.println("3 "+bean.getHeaderId());
			System.out.println("4 "+bean.getCompanyCode());
			System.out.println("5 "+bean.getCurrFinYear());
			System.out.println("6 "+bean.getCurrPeriodCode());
			System.out.println("7 "+ bean.getSendLocId());
			System.out.println("8 "+bean.getReasonId());
			System.out.println("9 "+bean.getSetRemoveProdId());
			System.out.println("10 "+ bean.getSetRemoveBatchId());
			System.out.println("11 "+ bean.getRemoveQty());
			System.out.println("12"+ bean.getOut_stock_type());
			System.out.println("13 "+ bean.getRemoveRate());
			System.out.println("14 "+ bean.getSetAddProdId());
			System.out.println("15 "+ bean.getSetAddBatchId());
			System.out.println("16 "+ bean.getAddBatchId());
			System.out.println("17 "+ "");
			System.out.println("18 "+ "");
			System.out.println("19 "+ "");
			System.out.println("20 "+ bean.getAddQty());
			System.out.println("21 "+ bean.getIn_stock_type());
			System.out.println("22 "+bean.getAddRate());
			System.out.println("23 "+ bean.getTrnDate());
			System.out.println("24 "+ bean.getOut_tran_type());
			System.out.println("25 "+bean.getIn_tran_type());
			System.out.println("26 "+ bean.getBatchInd());
			System.out.println( "27 "+bean.getExpInd());
			System.out.println("28 "+bean.getAddRemark());
			System.out.println("29 "+ bean.getEmpId());
			System.out.println("30 "+ bean.getIpAddress());
			System.out.println("31 "+"A");
			
			query.setParameter(1, bean.getAction());
			query.setParameter(2, bean.getAdjDtlId());
			query.setParameter(3, bean.getHeaderId());
			query.setParameter(4, bean.getCompanyCode());
			query.setParameter(5, bean.getCurrFinYear());
			query.setParameter(6, bean.getCurrPeriodCode());
			query.setParameter(7, bean.getSendLocId());
			query.setParameter(8, bean.getReasonId());
			query.setParameter(9, bean.getSetRemoveProdId());
			query.setParameter(10, bean.getSetRemoveBatchId());
			query.setParameter(11, bean.getRemoveQty());
			query.setParameter(12, bean.getOut_stock_type());
			query.setParameter(13, bean.getRemoveRate());
			query.setParameter(14, bean.getSetAddProdId());
			query.setParameter(15, bean.getSetAddBatchId());
			query.setParameter(16, bean.getAddBatchId());
			query.setParameter(17, "");
			query.setParameter(18, "");
			query.setParameter(19, "");
			query.setParameter(20, bean.getAddQty());
			query.setParameter(21, bean.getIn_stock_type());
			query.setParameter(22, bean.getAddRate());
			query.setParameter(23, bean.getTrnDate());
			query.setParameter(24, bean.getOut_tran_type());
			query.setParameter(25, bean.getIn_tran_type());
			query.setParameter(26, bean.getBatchInd());
			query.setParameter(27, bean.getExpInd());
			query.setParameter(28, bean.getAddRemark());
			query.setParameter(29, bean.getEmpId());
			query.setParameter(30, bean.getIpAddress());
			query.setParameter(31, "A");
			System.out.println("Stock cfa "+bean.isStock_cfa());
			if(bean.isStock_cfa()) {
			query.setParameter(32, Integer.parseInt(bean.getAppr_req()));
			query.setParameter(33, 0);
			query.setParameter(34, "E");
			}
			query.execute();
			id = Long.valueOf((String) query.getOutputParameterValue(35));
		} finally {
			
		}
		return id;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Object> getStockAdjDetailsByHeaderId(Long header_id) throws Exception {
		EntityManager em = null;
		List<Object>  list=null;
		try {
			StringBuffer sb = new StringBuffer();
			sb.append("select adjdtl_id, reason_nm, out_prod_nm, out_batch_no, adjdtl_out_qty, OUT_stock_type_name, ")
					.append("in_prod_nm, in_batch_no, adjdtl_in_qty, IN_stock_type_name,stkadj_Appr_status ")
					.append("from v_STOCK_ADJ_DETAILS ")
					.append("where ADJDTL_STKADJ_ID=").append(header_id)
					.append(" order by ADJDTL_ID ");
			
			Query query = entityManager.createNativeQuery(sb.toString());
			list  = query.getResultList();
		} finally {
			if (em != null) { em.close(); }
		}
		return list;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Object> getIAAHeaderList(String comp_cd, Long fin_yr, String period_cd,String empId) throws Exception {
		EntityManager em = null;
		List<Object>  list=null;
		try {
			em = emf.createEntityManager();
			StringBuffer sb = new StringBuffer();
			sb.append("SELECT STKADJ_ID, STKADJ_NO, CONVERT(VARCHAR(11),STKADJ_DATE,103), loc_nm, ")
			.append(" STKADJ_REMARKS, stkadj_loc_id FROM v_STOCK_ADJ_HEADER ")
			.append(" WHERE STKADJ_STATUS ='A' AND STKADJ_FIN_YEAR=").append(fin_yr)
			.append(" AND STKADJ_PERIOD_CODE=").append(period_cd)
			.append(" AND STKADJ_INS_USR_ID='").append(empId).append("'")
			.append(" AND STKADJ_APPR_STATUS='E' ORDER BY STKADJ_DATE DESC");
			Query query = em.createNativeQuery(sb.toString());
			list  = query.getResultList();
		} finally {
			if (em != null) { em.close(); }
		}
		return list;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Object> getStockAdjDtlByHdrForApproval(Long header_id, int appr_level, String appr_status) throws Exception {
		EntityManager em = null;
		List<Object>  list=null;
		try {
			em = emf.createEntityManager();
			StringBuffer sb = new StringBuffer();
			sb.append("select adjdtl_id, reason_nm, out_prod_cd, out_prod_nm, out_batch_no, adjdtl_out_qty, adjdtl_out_rate,")
				.append(" out_stock_type_name, in_prod_cd, in_prod_nm, in_batch_no, adjdtl_in_qty, adjdtl_in_rate, in_stock_type_name,")
				.append(" out_erp_prod_cd,in_erp_prod_cd ")
				.append(" from V_STOCK_ADJ_DETAILS")
				.append(" where ADJDTL_STKADJ_ID=:header_id")
				.append(" and stkadj_appr_status=:appr_status")
			//	.append(" and stkadj_appr_acq=:appr_level")
				.append(" order by ADJDTL_ID ");
			Query query = em.createNativeQuery(sb.toString());
			query.setParameter("header_id", header_id);
			query.setParameter("appr_status", appr_status);
//			query.setParameter("appr_level", appr_level);
			list  = query.getResultList();
		} finally {
			if (em != null) { em.close(); }
		}
		return list;
	}

	@SuppressWarnings("unchecked")
	@Override
	public Integer getSTKADJ_APPR_REQ(Long stkadj_id) throws Exception {
		int apr_req=0;
		try {
			StringBuffer sb = new StringBuffer();
			sb.append("select STKADJ_APPR_REQ from STOCK_ADJ_HEADER ")
				.append("where STKADJ_ID=").append(stkadj_id);
			Query query = entityManager.createNativeQuery(sb.toString());
			List<Object> list  = query.getResultList();
			if(list!=null && list.size()>0){
				apr_req=(int) list.get(0);
			}
		} finally {
		}
		return apr_req;
	}

	@Override
	public Stock_Adj_Header getObjectById(Long headerId) throws Exception {
		return this.entityManager.find(Stock_Adj_Header.class, headerId);
	}

	@Override
	public Stock_Adj_Details getDetailById(Long detailId) throws Exception {
		return this.entityManager.find(Stock_Adj_Details.class, detailId);
	}

	@Override
	//@Transactional
	public Integer saveStockAdjFinalApproval(Long adj_detail_id, String status, String user_id, String ip_addr) throws Exception {
		
		Integer outpara=2;
		EntityTransaction tx =null;
		EntityManager em =null;
		try {
			
			System.out.println("STATUS : "+status+" adj_detail_id : "+adj_detail_id);
			em =  emf.createEntityManager();
			tx =em.getTransaction();
			tx.begin();
			StoredProcedureQuery query = em.createStoredProcedureQuery("p_iu_STOCK_ADJ_DETAILS_JAVA_HOAPRVD");
			query.registerStoredProcedureParameter(1, String.class, ParameterMode.IN);
			query.registerStoredProcedureParameter(2, Long.class, ParameterMode.IN);
			query.registerStoredProcedureParameter(3, String.class, ParameterMode.IN);
			query.registerStoredProcedureParameter(4, String.class, ParameterMode.IN);
			query.registerStoredProcedureParameter(5, String.class, ParameterMode.IN);
			query.registerStoredProcedureParameter(6, Integer.class, ParameterMode.OUT);
		
			query.setParameter(1, "");
			query.setParameter(2, adj_detail_id);
			query.setParameter(3, user_id);
			query.setParameter(4, ip_addr);
			query.setParameter(5, status);
			query.execute();
			
			outpara = (Integer) query
				    .getOutputParameterValue(6);
			tx.commit();
		}
		catch (Exception e) {
			// TODO: handle exception
			if(tx!=null)tx.rollback();
			
		}
		finally {
			if(em!=null)em.close();
		}
		return outpara;
	}

	@Override
	public Object getStockAdjDetailsByDetId(Long detail_id) throws Exception {
		Object result=null;
		try {
			StringBuffer sb = new StringBuffer();
			sb.append("select out_prod_id, out_batch_id, adjdtl_out_rate, adjdtl_out_qty ")
					.append("from v_STOCK_ADJ_DETAILS ")
					.append("where adjdtl_id=").append(detail_id);
			Query query = entityManager.createNativeQuery(sb.toString());
			List<Object> list  = query.getResultList();
			if(list!=null && list.size()>0){
				result=list.get(0);
			}
		} finally {
		}
		return result;
	}

	@Override
	public List<IaaDetailForMail> getIaaDetailForMail(Long locid, Long frmid, Long toid) throws Exception {
		// TODO Auto-generated method stub
		 List<IaaDetailForMail> list = new ArrayList<IaaDetailForMail>();
		try {
			StoredProcedureQuery query = entityManager.createNamedStoredProcedureQuery("callIaaDetailForMail");
			query.setParameter("loc_id", locid.toString());
			query.setParameter("frm_Iaa_id", frmid.toString());
			query.setParameter("to_Iaa_id", toid.toString());

			list = query.getResultList();
		}
		catch (Exception e) {
			throw e;
			// TODO: handle exception
		}
		return list;
	}

	@Override
	public List<IaaDetailForApprovalMail> getIaaDetailForApprovalMail(Long frmid, Long toid) throws Exception {
		// TODO Auto-generated method stub
		 List<IaaDetailForApprovalMail> list = new ArrayList<IaaDetailForApprovalMail>();
		try {
			StoredProcedureQuery query = entityManager.createNamedStoredProcedureQuery("calliaadetail_aprvmail");
			query.setParameter("loc_id", "0");
			query.setParameter("frm_Iaa_id", frmid.toString());
			query.setParameter("to_Iaa_id", toid.toString());

			list = query.getResultList();
		}
		catch (Exception e) {
			throw e;
			// TODO: handle exception
		}
		return list;
	}



}
