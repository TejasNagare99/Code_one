package com.excel.repository;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.ParameterMode;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceException;
import javax.persistence.Query;
import javax.persistence.StoredProcedureQuery;
import javax.persistence.Tuple;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Order;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import javax.servlet.http.HttpServletRequest;

import org.hibernate.Session;
import org.hibernate.jdbc.Work;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestParam;

import com.excel.bean.DispatchBean;
import com.excel.bean.DispatchMailBean;
import com.excel.bean.ReportBean;
import com.excel.exception.MedicoException;
import com.excel.model.AllocationDetailList;
import com.excel.model.AutoDispatchDropdown;
import com.excel.model.DispatchHeaderData;
import com.excel.model.Dispatch_Detail;
import com.excel.model.Dispatch_Detail_Arc;
import com.excel.model.Dispatch_Header;
import com.excel.model.Dispatch_Header_;
import com.excel.model.Dispatch_Header_Addl;
import com.excel.model.Dispatch_Header_arc;
import com.excel.model.Dispatch_Header_arc_;
import com.excel.model.GenerateDispatchData;
import com.excel.model.GenerateDispatchDataCfaStock;
import com.excel.model.GenerateDispatchData_AllocType;
import com.excel.model.LrCsvUpload;
import com.excel.model.ManualDispatchItemList;
import com.excel.model.ManualDispatchList;
import com.excel.model.ManualDispatchList_;
import com.excel.model.ManulDispatchProdListData;
import com.excel.model.MonthList;
import com.excel.model.P_iu_dispatch_java;
import com.excel.model.P_v_dispatch2_java;
import com.excel.model.Sum_Disp_Detail;
import com.excel.model.Sum_Disp_Detail_Arc;
import com.excel.model.Sum_Disp_Header;
import com.excel.model.Sum_Disp_Header_Addl;
import com.excel.model.Sum_Disp_Header_arc;
import com.excel.model.Sum_Disp_Header_arc_;
import com.excel.model.Transporter_master;
import com.excel.model.Transporter_master_;
import com.excel.utility.CodifyErrors;
import com.excel.utility.MedicoConstants;

@Repository
public class DispatchRepositoryImpl implements DispatchRepository, MedicoConstants {

	@Autowired
	private EntityManagerFactory emf;
	@PersistenceContext
	private EntityManager entityManager;

	@Override
	public AllocationDetailList getAllocationDetailList(String emp_id) throws Exception {
		EntityManager em = null;
		AllocationDetailList result = null;
		try {
			em = emf.createEntityManager();
			StoredProcedureQuery query = em.createNamedStoredProcedureQuery("callAllocationDetailList");
			query.setParameter("pvemp_id", emp_id);
			result = (AllocationDetailList) query.getSingleResult();
		} finally {
			if (em != null) {
				em.close();
			}
		}
		return result;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<MonthList> getMonthList(String tablename, String fieldlist, String search, String orderby)
			throws Exception {
		EntityManager em = null;
		List<MonthList> list = null;
		try {
			em = emf.createEntityManager();
			StoredProcedureQuery query = em.createNamedStoredProcedureQuery("callMonthList");
			query.setParameter("tablename", tablename);
			query.setParameter("fieldlist", fieldlist);
			query.setParameter("search", search);
			query.setParameter("orderby", orderby);
			list = query.getResultList();
		} finally {
			if (em != null) {
				em.close();
			}
		}
		return list;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<AutoDispatchDropdown> getAutoDispatchDropdown(String alloc_smp_id, String dispatchToLabel, String divId,
			String allocType, String subTeamCodeList, String req_Id, boolean isCfa_to_stockist) throws Exception {
		EntityManager em = null;
		List<AutoDispatchDropdown> list = null;
		try {
			em = emf.createEntityManager();
			StoredProcedureQuery query = null;
			if (isCfa_to_stockist) {
				query = em.createNamedStoredProcedureQuery("callAutoDispatchDropDownStockist");
			} else {
				query = em.createNamedStoredProcedureQuery("callAutoDispatchDropDown");
			}
			if (isCfa_to_stockist) {
				query.setParameter("pcType", dispatchToLabel);
				query.setParameter("PALLOC_TYPE", allocType);
				query.setParameter("pi_BLK_HCP_REQ_ID", req_Id);
			} else {
				query.setParameter("piSMP_ID", Long.parseLong(alloc_smp_id));
				query.setParameter("pcType", dispatchToLabel);
				query.setParameter("piFSTAFF_DIV_ID", Long.parseLong(divId));
				query.setParameter("PALLOC_TYPE", allocType);
				query.setParameter("pcTeam_code", subTeamCodeList);
				query.setParameter("piBLK_HCP_REQ_ID", Integer.parseInt(req_Id));
			}

			list = query.getResultList();
		} finally {
			if (em != null) {
				em.close();
			}
		}
		return list;
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public List<GenerateDispatchData> genAutoDispatch(String comp_cd, String fin_year, String period_cd,
			Long dsp_loc_id, Long smp_id, Long pi_loc_id, Long pi_abm, Long pi_rbm, Long pi_stateId, Long pi_msr,
			String pend_alloc, String action, String pvinsusr_id, Long pvinsip, Long pi_div_id, Long sample_rbm,
			Long sample_abm, String sample_flag, String challan_msg, Long chalan_periodId, String req_lvl, Long zone_id,
			String direct_to_pso_ind, boolean stock_at_cfa_ind) throws Exception {
		EntityManager em = null;
		List<GenerateDispatchData> list = null;
		System.out.println("Param : " + comp_cd + ":" + fin_year + ":" + period_cd + ":" + dsp_loc_id + ":" + smp_id
				+ ":" + pi_loc_id + ":" + pi_abm + ":" + pi_rbm + ":" + pi_stateId + ":" + pi_msr + ":" + pend_alloc
				+ ":" + action + ":" + pvinsusr_id + ":" + pvinsip + ":" + pi_div_id + ":" + sample_rbm + ":"
				+ sample_abm + ":" + sample_flag + ":" + challan_msg + ":" + chalan_periodId + ":" + req_lvl + ":"
				+ zone_id + "::::" + direct_to_pso_ind);
		try {
			em = emf.createEntityManager();
			StoredProcedureQuery query = em.createNamedStoredProcedureQuery("callGenerateDispatchData");
			query.setParameter("pcDSP_COMPANY", comp_cd);
			query.setParameter("pcDSP_FIN_YEAR", fin_year);
			query.setParameter("pcDSP_PERIOD_CODE", period_cd);
			query.setParameter("piDSP_LOC_ID", dsp_loc_id);
			query.setParameter("piDSP_SMP_ID", smp_id);
			query.setParameter("pirlocid", pi_loc_id);
			query.setParameter("piabm", pi_abm);
			query.setParameter("pirbm", pi_rbm);
			query.setParameter("pistateid", pi_stateId);
			query.setParameter("pimsr", pi_msr);
			query.setParameter("pcpendalloc", pend_alloc);
			query.setParameter("pcaction", action);
			query.setParameter("pvinsusr_id", pvinsusr_id);
			query.setParameter("pvinsip", pvinsip.toString());
			query.setParameter("pidiv_id", pi_div_id);
			query.setParameter("pisamplerbm", sample_rbm);
			query.setParameter("pisampleabm", sample_abm);
			query.setParameter("pcsampleflg", sample_flag);
			query.setParameter("pvchallan_msg", challan_msg);
			query.setParameter("pvchallan_PERIOD_ID", chalan_periodId);
			query.setParameter("pvDSP_APPR_REQ", req_lvl);
			query.setParameter("pirzonid", zone_id);
			query.setParameter("Pdirect_to_pso_ind", direct_to_pso_ind);
			list = query.getResultList();
		} finally {
			if (em != null) {
				em.close();
			}
		}
		return list;
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public List<GenerateDispatchDataCfaStock> genAutoDispatchCfaStock(String comp_cd, String fin_year, String period_cd,
			Long dsp_loc_id, Long smp_id, Long pi_loc_id, Long pi_abm, Long pi_rbm, Long pi_stateId, Long pi_msr,
			String pend_alloc, String action, String pvinsusr_id, Long pvinsip, Long pi_div_id, Long sample_rbm,
			Long sample_abm, String sample_flag, String challan_msg, Long chalan_periodId, String req_lvl, Long zone_id,
			String direct_to_pso_ind, boolean stock_at_cfa_ind) throws Exception {
		EntityManager em = null;
		List<GenerateDispatchDataCfaStock> list = null;
		try {
			em = emf.createEntityManager();
			StoredProcedureQuery query = em.createNamedStoredProcedureQuery("callGenerateDispatchDataCfastock");
			query.setParameter("pcDSP_COMPANY", comp_cd);
			query.setParameter("pcDSP_FIN_YEAR", fin_year);
			query.setParameter("pcDSP_PERIOD_CODE", period_cd);
			query.setParameter("piDSP_LOC_ID", dsp_loc_id);
			query.setParameter("piDSP_SMP_ID", smp_id);
			query.setParameter("pirlocid", pi_loc_id);
			query.setParameter("piabm", pi_abm);
			query.setParameter("pirbm", pi_rbm);
			query.setParameter("pistateid", pi_stateId);
			query.setParameter("pimsr", pi_msr);
			query.setParameter("pcpendalloc", pend_alloc);
			query.setParameter("pcaction", action);
			query.setParameter("pvinsusr_id", pvinsusr_id);
			query.setParameter("pvinsip", pvinsip);
			query.setParameter("pidiv_id", pi_div_id);
			query.setParameter("pisamplerbm", sample_rbm);
			query.setParameter("pisampleabm", sample_abm);
			query.setParameter("pcsampleflg", sample_flag);
			query.setParameter("pvchallan_msg", challan_msg);
			query.setParameter("pvchallan_PERIOD_ID", chalan_periodId);
			query.setParameter("pvDSP_APPR_REQ", req_lvl);
			query.setParameter("pirzonid", zone_id);
			query.setParameter("Pdirect_to_pso_ind", direct_to_pso_ind);
			list = query.getResultList();
		} finally {
			if (em != null) {
				em.close();
			}
		}
		return list;
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public List<String> getAutoDispatchDetailsUpdated(Long smp_id, Long disp_cycle, Long loc_id, Long div_id,
			boolean stock_at_cfa_ind) throws Exception {
		EntityManager em = null;
		List<String> details = new LinkedList<>();
		try {
			em = emf.createEntityManager();
			System.out.println("para:" + smp_id + ":" + disp_cycle + ":" + loc_id + ":" + div_id);

			Session session = em.unwrap(Session.class);
			session.doWork(new Work() {

				@Override
				public void execute(Connection conn) throws SQLException {
					String SPsql = "";
					// if (stock_at_cfa_ind) {
					// //SPsql = "EXEC p_v_auto_dispatch_detail_cfastock ?,?,?,?";
					// } else {
					SPsql = "EXEC p_v_auto_dispatch_detail ?,?,?,?";
					// }
					PreparedStatement pstmt = conn.prepareStatement(SPsql);

					System.out.println("smp_id : " + smp_id);
					System.out.println("disp_cycle : " + disp_cycle);
					System.out.println("loc_id : " + loc_id);
					System.out.println("div_id : " + div_id);

					pstmt.setLong(1, smp_id);
					pstmt.setLong(2, disp_cycle);
					pstmt.setLong(3, loc_id);
					pstmt.setLong(4, div_id);
					boolean results = pstmt.execute();
					String columnName = "";
					while (results) {
						ResultSet rs = pstmt.getResultSet();
						ResultSetMetaData rsmd = rs.getMetaData();
						while (rs.next()) {
							columnName = rsmd.getColumnName(1).trim();
							if (columnName.equalsIgnoreCase("no_challan_gen")) {
								details.add(rs.getString("no_challan_gen"));
							} else if (columnName.equalsIgnoreCase("disp_start_no")) {
								details.add(rs.getString("disp_start_no"));
								details.add(rs.getString("disp_end_no"));
							} else if (columnName.equalsIgnoreCase("no_sum_disp")) {
								details.add("#");
								details.add(rs.getString("no_sum_disp"));
							} else if (columnName.equalsIgnoreCase("sum_start_no")) {
								details.add(rs.getString("sum_start_no"));
								details.add(rs.getString("sum_end_no"));
							}
						}
						results = pstmt.getMoreResults();
					}
				}
			});

		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		} finally {
			if (em != null) {
				em.close();
			}
		}
		return details;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<ManualDispatchItemList> getdispatchProd_list(Long staff_id, Long smp_id, String allocType)
			throws Exception {
		List<ManualDispatchItemList> list = null;
		EntityManager em = null;
		try {
			em = emf.createEntityManager();
			StoredProcedureQuery query = em.createNamedStoredProcedureQuery("callManualDispatchItemList");
			query.setParameter("piFSTAFF_ID", staff_id);
			query.setParameter("piSMP_ID", smp_id);
			query.setParameter("PALLOC_TYPE", allocType);
			list = query.getResultList();
		} finally {
			if (em != null) {
				em.close();
			}
		}
		return list;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<ManulDispatchProdListData> getdispatchProdDetails(String comp_cd, String dsp_year, String dsp_period_cd,
			Long dsp_loc, Long smp_id, Long prod_id, Long msr_id, String pend_alloc, String allocType)
			throws Exception {
		List<ManulDispatchProdListData> list = null;
		EntityManager em = null;
		System.out.println("para::" + comp_cd + ":" + dsp_year + ":" + dsp_period_cd + ":" + dsp_loc + ":" + smp_id
				+ ":" + prod_id + ":" + msr_id + ":" + pend_alloc + ":" + allocType);
		try {
			em = emf.createEntityManager();
			StoredProcedureQuery query = em.createNamedStoredProcedureQuery("callManulDispatchProdListData");
			query.setParameter("pcDSP_COMPANY", comp_cd);
			query.setParameter("pcDSP_FIN_YEAR", dsp_year);
			query.setParameter("pcDSP_PERIOD_CODE", dsp_period_cd);
			query.setParameter("piDSP_LOC_ID", dsp_loc);
			query.setParameter("pismp_id", smp_id);
			query.setParameter("piprod_id", prod_id);
			query.setParameter("pimsr_id", msr_id);
			query.setParameter("pcpendalloc", pend_alloc);
			query.setParameter("PALLOC_TYPE", allocType);
			list = query.getResultList();
		} finally {
			if (em != null) {
				em.close();
			}
		}
		return list;
	}

	@Override
	public List<P_iu_dispatch_java> saveManualDispatch(String comp_cd, String dsp_year, String dsp_period_cd,
			Long dsp_loc, Long dsp_alloc_id, Long smp_id, Long fstaff_id, Long div_id, Long recv_loc, Long dsp_state_id,
			String pend_alloc, String emp_id, String ip_addr, String dsp_status, Long dsp_cycle, Long dsp_id,
			Long prod_id, Long dsp_batch_id, BigDecimal disp_qty, BigDecimal disp_rate, Long dsp_dtl_alloc_id,
			Long dsp_alt_divid, Long sumdsp_id, String action, String status_flag, BigDecimal prv_smpqty,
			Long prv_allocid, Long prv_allocdtlid, String challan_msg, Long challan_period_id, String req_lvl,
			String direct_to_pso_ind) throws Exception {
		List<P_iu_dispatch_java> list = null;
		EntityManager em = null;
		System.out.println(comp_cd + ":" + dsp_year + ":" + dsp_period_cd + ":" + dsp_loc + ":" + dsp_alloc_id + ":"
				+ smp_id + ":" + fstaff_id + ":" + div_id + ":" + recv_loc + ":" + dsp_state_id + ":" + pend_alloc + ":"
				+ emp_id + ":" + ip_addr + ":" + dsp_status + ":" + dsp_cycle + ":" + dsp_id + ":" + prod_id + ":"
				+ dsp_batch_id + ":" + disp_qty + ":" + disp_rate + ":" + dsp_dtl_alloc_id + ":" + dsp_alt_divid + ":"
				+ sumdsp_id + ":" + action + ":" + status_flag + ":" + prv_smpqty + ":" + prv_allocid + ":"
				+ prv_allocdtlid + ":" + challan_msg + ":" + challan_period_id + ":" + direct_to_pso_ind);
		try {
			em = emf.createEntityManager();
			StoredProcedureQuery query = null;
			if (comp_cd.equals(PAL)) {
				query = em.createNamedStoredProcedureQuery("callSaveManualDispatchTkey");
				query.setParameter("pcDSP_COMPANY", comp_cd);
				query.setParameter("pcDSP_FIN_YEAR", dsp_year);
				query.setParameter("pcDSP_PERIOD_CODE", dsp_period_cd);
				query.setParameter("piDSP_LOC_ID", dsp_loc);
				query.setParameter("piDSP_ALLOC_ID", dsp_alloc_id);
				query.setParameter("piDSP_SMP_ID", smp_id);
				query.setParameter("piDSP_FSTAFF_ID", fstaff_id);
				query.setParameter("piDSP_DIV_ID", div_id);
				query.setParameter("piDSP_RECVG_LOC_ID", recv_loc);
				query.setParameter("piDSP_STATE_ID", dsp_state_id);
				query.setParameter("pcDSP_prev_alloc_flg", pend_alloc);
				query.setParameter("pvDSP_usr_id", emp_id);
				query.setParameter("pvDSP_ip_add", ip_addr);
				query.setParameter("pcDSP_status", dsp_status);
				query.setParameter("psiDISP_CYCLE", dsp_cycle);
				// query.setParameter("piDSP_ID_OUT", "");
				query.setParameter("piDSP_ID", dsp_id);
				query.setParameter("piDSPDTL_PROD_ID", prod_id);
				query.setParameter("piDSPDTL_BATCH_ID", dsp_batch_id);
				query.setParameter("pncDSPDTL_DISP_QTY", disp_qty);
				query.setParameter("pncDSPDTL_RATE", disp_rate);
				query.setParameter("piDSPDTL_ALLOCDTL_ID", dsp_dtl_alloc_id);
				query.setParameter("piDSPDTL_ALT_DIV_ID", dsp_alt_divid);
				query.setParameter("piSUMDSP_ID", sumdsp_id);
				// query.setParameter("piSUMDSP_ID_OUT", "");
				query.setParameter("pcaction", action);
				query.setParameter("pcStatus_flag", status_flag);
				query.setParameter("piprv_smpqty", prv_smpqty);
				query.setParameter("piprv_allocid", prv_allocid);
				query.setParameter("piprv_allocdtlid", prv_allocdtlid);
				query.setParameter("pvchallan_msg", challan_msg);
				query.setParameter("pvchallan_PERIOD_ID", challan_period_id);
				// query.setParameter("pvDSPchallan_no_out", "");
				// query.setParameter("pvSMchallan_no_out", "");
				query.setParameter("pvDSP_APPR_REQ", req_lvl);
				query.setParameter("Pdirect_to_pso_ind", direct_to_pso_ind);
				query.getOutputParameterValue("piDSP_ID_OUT");
				query.getOutputParameterValue("piSUMDSP_ID_OUT");
				query.getOutputParameterValue("pvDSPchallan_no_out");
				query.getOutputParameterValue("pvSMchallan_no_out");
				query.execute();
			} else {
				query = em.createNamedStoredProcedureQuery("callSaveManualDispatch");
				query.setParameter("pcDSP_COMPANY", comp_cd);
				query.setParameter("pcDSP_FIN_YEAR", dsp_year);
				query.setParameter("pcDSP_PERIOD_CODE", dsp_period_cd);
				query.setParameter("piDSP_LOC_ID", dsp_loc);
				query.setParameter("piDSP_ALLOC_ID", dsp_alloc_id);
				query.setParameter("piDSP_SMP_ID", smp_id);
				query.setParameter("piDSP_FSTAFF_ID", fstaff_id);
				query.setParameter("piDSP_DIV_ID", div_id);
				query.setParameter("piDSP_RECVG_LOC_ID", recv_loc);
				query.setParameter("piDSP_STATE_ID", dsp_state_id);
				query.setParameter("pcDSP_prev_alloc_flg", pend_alloc);
				query.setParameter("pvDSP_usr_id", emp_id);
				query.setParameter("pvDSP_ip_add", ip_addr);
				query.setParameter("pcDSP_status", dsp_status);
				query.setParameter("psiDISP_CYCLE", dsp_cycle);
				// query.setParameter("piDSP_ID_OUT", "");
				query.setParameter("piDSP_ID", dsp_id);
				query.setParameter("piDSPDTL_PROD_ID", prod_id);
				query.setParameter("piDSPDTL_BATCH_ID", dsp_batch_id);
				query.setParameter("pncDSPDTL_DISP_QTY", disp_qty);
				query.setParameter("pncDSPDTL_RATE", disp_rate);
				query.setParameter("piDSPDTL_ALLOCDTL_ID", dsp_dtl_alloc_id);
				query.setParameter("piDSPDTL_ALT_DIV_ID", dsp_alt_divid);
				query.setParameter("piSUMDSP_ID", sumdsp_id);
				// query.setParameter("piSUMDSP_ID_OUT", "");
				query.setParameter("pcaction", action);
				query.setParameter("pcStatus_flag", status_flag);
				query.setParameter("piprv_smpqty", prv_smpqty);
				query.setParameter("piprv_allocid", prv_allocid);
				query.setParameter("piprv_allocdtlid", prv_allocdtlid);
				query.setParameter("pvchallan_msg", challan_msg);
				query.setParameter("pvchallan_PERIOD_ID", challan_period_id);
				// query.setParameter("pvDSPchallan_no_out", "");
				// query.setParameter("pvSMchallan_no_out", "");
				query.setParameter("pvDSP_APPR_REQ", req_lvl);
				query.setParameter("Pdirect_to_pso_ind", direct_to_pso_ind);
				query.getOutputParameterValue("piDSP_ID_OUT");
				query.getOutputParameterValue("piSUMDSP_ID_OUT");
				query.getOutputParameterValue("pvDSPchallan_no_out");
				query.getOutputParameterValue("pvSMchallan_no_out");
				query.execute();
			}
			list = new ArrayList<>();
			P_iu_dispatch_java obj = new P_iu_dispatch_java();
			obj.setPiDSP_ID_OUT(Long.parseLong(query.getOutputParameterValue("piDSP_ID_OUT").toString()));
			obj.setPiSUMDSP_ID_OUT(Long.parseLong(query.getOutputParameterValue("piSUMDSP_ID_OUT").toString()));
			obj.setPvDSPchallan_no_out(query.getOutputParameterValue("pvDSPchallan_no_out").toString());
			obj.setPvSMchallan_no_out(query.getOutputParameterValue("pvSMchallan_no_out").toString());
			list.add(obj);
			// list = query.getResultList();
			System.err.println("list:::" + list.size());
		} finally {
			if (em != null) {
				em.close();
			}
		}
		return list;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<P_v_dispatch2_java> getDispatchedProdData(Long dsp_id, Long loc_id) throws Exception {
		List<P_v_dispatch2_java> list = null;
		EntityManager em = null;
		System.out.println("dsp_id::" + dsp_id);
		System.out.println("loc_id::" + loc_id);
		try {
			em = emf.createEntityManager();
			StoredProcedureQuery query = em.createNamedStoredProcedureQuery("callP_v_dispatch2_java");
			query.setParameter("pidsp_id", dsp_id);
			query.setParameter("piloc_id", loc_id);
			list = query.getResultList();
		} finally {
			if (em != null) {
				em.close();
			}
		}
		return list;
	}

	@Override
	public List<ManualDispatchList> getManualDispatchList(Long loc_SubComp_id, String user_divisions, String user_id,
			String user_type, long sum_dsp_id) throws Exception {
		List<ManualDispatchList> list = null;
		EntityManager em = null;
		try {
			List<Long> divList = new ArrayList<Long>();
			List<String> strList = new ArrayList<String>(Arrays.asList(user_divisions.split(",")));

			for (String number : strList) {
				divList.add(Long.parseLong(number));
			}

			em = emf.createEntityManager();
			CriteriaBuilder builder = em.getCriteriaBuilder();

			CriteriaQuery<ManualDispatchList> criteriaQuery = builder.createQuery(ManualDispatchList.class);
			Root<ManualDispatchList> root = criteriaQuery.from(ManualDispatchList.class);
			if (!user_type.equalsIgnoreCase("S")) {
				criteriaQuery.select(root)
						.where(builder.and(builder.equal(root.get(ManualDispatchList_.loc_SubComp_id), loc_SubComp_id),
								builder.equal(root.get(ManualDispatchList_.dsp_status), "A"),
								builder.equal(root.get(ManualDispatchList_.dsp_appr_status), "E"),
								builder.equal(root.get(ManualDispatchList_.dsp_ins_usr_id), user_id),
								root.get(ManualDispatchList_.dsp_div_id).in(divList)))
						.orderBy(builder.asc(root.get(ManualDispatchList_.dsp_challan_no)));
			}

			if (sum_dsp_id != 0) {
				criteriaQuery.select(root)
						.where(builder.and(builder.equal(root.get(ManualDispatchList_.loc_SubComp_id), loc_SubComp_id),
								builder.equal(root.get(ManualDispatchList_.dsp_status), "A"),
								builder.equal(root.get(ManualDispatchList_.dsp_appr_status), "E"),
								builder.equal(root.get(ManualDispatchList_.dsp_sum_dsp_id), sum_dsp_id),
								root.get(ManualDispatchList_.dsp_div_id).in(divList)))
						.orderBy(builder.asc(root.get(ManualDispatchList_.dsp_challan_no)));
			}
			list = em.createQuery(criteriaQuery).getResultList();
			return list;
		} finally {
			if (em != null) {
				em.close();
			}
		}
	}

	@Override
	public List<Sum_Disp_Header> getSummaryChallanListByDiv(Long div_id, String user_id, Long loc_subComp_id,
			int appr_level) throws Exception {
		System.out.println("emp id is" + user_id);
		System.out.println("div id is" + div_id);
		System.out.println("loc_subComp_id id is " + loc_subComp_id);

		List<Sum_Disp_Header> list = new ArrayList<Sum_Disp_Header>();
		EntityManager em = null;
		try {
			em = emf.createEntityManager();
			StringBuffer sb = new StringBuffer();
			sb.append(
					" select distinct sumdsp_id as sumdsp_id,sumdsp_challan_no as sumdsp_challan_no from SUM_DISP_HEADER s left join dbo.location l  ");
			sb.append(" on s.SUMDSP_LOC_ID = l.loc_id left join dispatch_header d on s.sumdsp_id = d.dsp_sum_dsp_id  ");
			sb.append(" where SUMDSP_DIV_ID=:div_id and SUMDSP_status = 'A' ");
			sb.append(" and l.loc_id = :loc_subComp_id  ");

			if (appr_level == 0)
				sb.append(" and d.dsp_appr_status = 'E' and SUMDSP_ins_usr_id = :user_id");
			else
				sb.append(" and d.dsp_appr_status = 'F'");

			sb.append(" order by sumdsp_challan_no  ");
			Query query = em.createNativeQuery(sb.toString(), Tuple.class);
			query.setParameter("div_id", div_id);
			query.setParameter("loc_subComp_id", loc_subComp_id);
			query.setParameter("user_id", user_id);
			List<Tuple> tuples = query.getResultList();
			Sum_Disp_Header sm = null;
			if (tuples != null && !tuples.isEmpty()) {
				for (Tuple t : tuples) {
					sm = new Sum_Disp_Header();
					sm.setSumdsp_id(Long.valueOf(t.get("sumdsp_id", Integer.class)));
					sm.setSumdsp_challan_no(t.get("sumdsp_challan_no", String.class));
					list.add(sm);
				}
			}
		} catch (Exception e) {
			System.out.println("Error Occurred :" + e.getMessage());// uncomment asneeded --;
		} finally {
			if (em != null) {
				em.close();
			}
		}
		return list;

	}

	@Override
	public Dispatch_Header getDispatchHeaderById(Long dsp_id) throws Exception {
		Dispatch_Header h = null;
		EntityManager em = null;
		try {
			em = emf.createEntityManager();
			h = em.find(Dispatch_Header.class, dsp_id);
		} finally {
			if (em != null) {
				em.close();
			}
		}
		return h;
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public void updateHeaderForApproval(String status, Long dsp_id, String user_name, Date appr_date) throws Exception {
		EntityManager em = null;
		try {
			em = emf.createEntityManager();
			StringBuffer buffer = new StringBuffer();
			buffer.append("update Dispatch_Header set dsp_appr_status=:status, dsp_mod_usr_id=:mod_user, ");
			buffer.append("dsp_mod_dt=:mod_date where dsp_id=:dsp_id ");
			Query query = em.createNativeQuery(buffer.toString());
			query.setParameter("status", status);
			query.setParameter("mod_user", user_name);
			query.setParameter("mod_date", appr_date);
			query.setParameter("dsp_id", dsp_id);
			query.executeUpdate();

		} catch (Exception e) {
			System.out.println("Error Occurred :" + e.getMessage());// uncomment asneeded --;
			throw e;
		} finally {
			if (em != null) {
				em.close();
			}
		}
//		if(status.equalsIgnoreCase("A")){
//			sb.append(" ,app_user_name=:app_user_name, appr_date=:appr_date ");
//		}else if(status.equalsIgnoreCase("D")){
//			sb.append(" ,rej_user_name=:rej_user_name, rej_date=:rej_date ");
//		}
	}

	@Override
	public List<ManualDispatchList> getManualDispatchListApproval(String user_id, String user_type, Long sum_dsp_id)
			throws Exception {
		List<ManualDispatchList> list = null;
		EntityManager em = null;

		try {
			em = emf.createEntityManager();
			CriteriaBuilder builder = em.getCriteriaBuilder();
			CriteriaQuery<ManualDispatchList> query = builder.createQuery(ManualDispatchList.class);
			Root<ManualDispatchList> root = query.from(ManualDispatchList.class);
			List<Predicate> predicates = new ArrayList<>();
			predicates.add(builder.equal(root.get(ManualDispatchList_.dsp_status), "A"));
			predicates.add(builder.equal(root.get(ManualDispatchList_.dsp_appr_status), "E"));
			predicates.add(builder.equal(root.get(ManualDispatchList_.dsp_ins_usr_id), user_id));
			if (sum_dsp_id != null) {
				predicates.add(builder.equal(root.get(ManualDispatchList_.dsp_sum_dsp_id), sum_dsp_id));
			}

			query.where(predicates.toArray(new Predicate[0]))
					.orderBy(builder.desc(root.get(ManualDispatchList_.dsp_challan_no)));

			list = em.createQuery(query).getResultList();

		} catch (Exception e) {
			throw e;
		} finally {
			if (em != null) {
				em.close();
			}
		}
		return list;
	}

	@Override
	public List<ReportBean> getProducts(long div_Id, int locfrmId, String stdt, String endt, String fstaff_id,
			String desg_id, String fstaffId_o, String deleted_inv, String emp_id, String emp_id1, String finyearflag,
			String year) throws Exception {
		EntityManager em = null;
		List<ReportBean> list = null;
		List<Tuple> tuples = null;
		try {
			System.out.println("flag : " + finyearflag + " year : " + year);
			em = emf.createEntityManager();
			if (desg_id.equalsIgnoreCase("Y")) {
				if (div_Id == 0) {
					StringBuffer qryString = null;
					if (finyearflag.equals("CURRENT")) {
						qryString = new StringBuffer("select distinct dspdtl_prod_id,SMP_PROD_NAME ").append(
								" from  dispatch_header dh left join dispatch_detail dd on(dd.dspdtl_dsp_id=dh.dsp_id) ")
								.append(" inner join smpitem pr on(dd.DSPDTL_PROD_ID=pr.SMP_PROD_ID) inner join sum_disp_header sh ")
								.append(" on(sh.sumdsp_id=dh.dsp_sum_dsp_id)  ")
								.append(" join location l on l.loc_id=dh.dsp_loc_id where");
					} else {
						qryString = new StringBuffer("select distinct dspdtl_prod_id,SMP_PROD_NAME ").append(
								" from  dispatch_header_arc dh left join dispatch_detail_arc dd on(dd.dspdtl_dsp_id=dh.dsp_id ")
								.append(" and dh.dsp_fin_year = dd.dspdtl_fin_year)")
								.append(" inner join smpitem pr on(dd.DSPDTL_PROD_ID=pr.SMP_PROD_ID) inner join sum_disp_header_arc sh ")
								.append(" on(sh.sumdsp_id=dh.dsp_sum_dsp_id and dh.dsp_fin_year=sh.sumdsp_fin_year)  ")
								.append(" join location l on l.loc_id=dh.dsp_loc_id where dh.dsp_fin_year='")
								.append(year).append("' and");

					}

					if (!(fstaffId_o.equalsIgnoreCase("All") || !(fstaffId_o.equalsIgnoreCase("0")))) {
						qryString.append(
								" dd.DSPDTL_FSTAFF_ID in (select FSTAFF_ID  from fieldstaff where FSTAFF_DESIG= '")
								.append(fstaffId_o).append("') AND ");
					}
					qryString.append("  CONVERT(date,SH.SUMDSP_CHALLAN_DT,105)>=CONVERT(date,'").append(stdt)
							.append("',105) ").append(" AND CONVERT(date,SH.SUMDSP_CHALLAN_DT,105)<=CONVERT(date,'")
							.append(endt).append("',105) ");
					qryString.append(
							" AND DH.DSP_DIV_ID IN  (select ediv_div_id from am_m_emp_div_access where ediv_emp_id= '")
							.append(emp_id1).append("') AND DH.DSP_STATUS= '").append(deleted_inv);
					if (!emp_id.equalsIgnoreCase("0") && !emp_id.isEmpty()) {
						qryString.append("' AND dd.DSPDTL_FSTAFF_ID= '").append(emp_id);

						qryString.append("' and dh.DSP_LOC_ID = l.loc_id ");
						qryString.append(" and l.loc_SubComp_id = CASE WHEN ").append(locfrmId)
								.append("=0 THEN l.loc_SubComp_id ELSE ").append(locfrmId).append(" END");
						qryString.append(" order by SMP_PROD_NAME ");

					} else {
						qryString.append("' and dh.DSP_LOC_ID = l.loc_id ");
						qryString.append(" and l.loc_SubComp_id = CASE WHEN ").append(locfrmId)
								.append("=0 THEN l.loc_SubComp_id ELSE ").append(locfrmId).append(" END");
						qryString.append(" order by SMP_PROD_NAME ");
					}

					Query query = em.createNativeQuery(qryString.toString(), Tuple.class);
					tuples = query.getResultList();
				} else
				// =======================================================================================================
				{
					StringBuffer qryString = null;
					if (finyearflag.equals("CURRENT")) {
						qryString = new StringBuffer("select distinct dspdtl_prod_id,SMP_PROD_NAME ").append(
								" from  dispatch_header dh left join dispatch_detail dd on(dd.dspdtl_dsp_id=dh.dsp_id) ")
								.append(" inner join smpitem pr on(dd.DSPDTL_PROD_ID=pr.SMP_PROD_ID) inner join sum_disp_header sh ")
								.append(" on(sh.sumdsp_id=dh.dsp_sum_dsp_id) ")
								.append(" join location l on l.loc_id=dh.dsp_loc_id where");
					} else {
						qryString = new StringBuffer("select distinct dspdtl_prod_id,SMP_PROD_NAME ").append(
								" from  dispatch_header_arc dh left join dispatch_detail_arc dd on(dd.dspdtl_dsp_id=dh.dsp_id ")
								.append(" and dh.dsp_fin_year = dd.dspdtl_fin_year)")
								.append(" inner join smpitem pr on(dd.DSPDTL_PROD_ID=pr.SMP_PROD_ID) inner join sum_disp_header_arc sh ")
								.append(" on(sh.sumdsp_id=dh.dsp_sum_dsp_id and dh.dsp_fin_year=sh.sumdsp_fin_year)")
								.append(" join location l on l.loc_id=dh.dsp_loc_id where  dh.dsp_fin_year='")
								.append(year).append("' and ");
					}
					if (!(fstaffId_o.equalsIgnoreCase("All") || !(fstaffId_o.equalsIgnoreCase("0")))) {
						qryString.append(
								" dd.DSPDTL_FSTAFF_ID in (select FSTAFF_ID  from fieldstaff where FSTAFF_DESIG= '")
								.append(fstaffId_o).append("') AND ");
					}
					qryString.append("  CONVERT(date,SH.SUMDSP_CHALLAN_DT,105)>=CONVERT(date,'").append(stdt)
							.append("',105) ").append(" AND CONVERT(date,SH.SUMDSP_CHALLAN_DT,105)<=CONVERT(date,'")
							.append(endt).append("',105) ");
					qryString.append(" AND DH.DSP_DIV_ID =").append(div_Id);
					qryString.append(" AND DH.DSP_STATUS= '").append(deleted_inv);
					if (!emp_id.equalsIgnoreCase("0") && !emp_id.isEmpty()) {
						qryString.append("' AND dd.DSPDTL_FSTAFF_ID= '").append(emp_id);

						qryString.append("' and dh.DSP_LOC_ID = l.loc_id ");
						qryString.append(" and l.loc_SubComp_id = CASE WHEN ").append(locfrmId)
								.append("=0 THEN l.loc_SubComp_id ELSE ").append(locfrmId).append(" END");
						qryString.append(" order by SMP_PROD_NAME ");

					} else {
						qryString.append("' and dh.DSP_LOC_ID = l.loc_id ");
						qryString.append(" and l.loc_SubComp_id = CASE WHEN ").append(locfrmId)
								.append("=0 THEN l.loc_SubComp_id ELSE ").append(locfrmId).append(" END");
						qryString.append(" order by SMP_PROD_NAME ");
					}

					Query query = em.createNativeQuery(qryString.toString(), Tuple.class);
					tuples = query.getResultList();

				}

			} else {
				// ===================================================for
				// No====================================================
				if (div_Id == 0) {
					StringBuffer qryString = null;
					if (finyearflag.equals("CURRENT")) {
						qryString = new StringBuffer("select distinct dspdtl_prod_id,SMP_PROD_NAME ").append(
								" from  dispatch_header dh left join dispatch_detail dd on(dd.dspdtl_dsp_id=dh.dsp_id) ")
								.append(" inner join smpitem pr on(dd.DSPDTL_PROD_ID=pr.SMP_PROD_ID) inner join sum_disp_header sh ")
								.append(" on(sh.sumdsp_id=dh.dsp_sum_dsp_id) ")
								.append(" join location l on l.loc_id=dh.dsp_loc_id where");
					} else {
						qryString = new StringBuffer("select distinct dspdtl_prod_id,SMP_PROD_NAME ").append(
								" from  dispatch_header_arc dh left join dispatch_detail_arc dd on(dd.dspdtl_dsp_id=dh.dsp_id ")
								.append(" and dh.dsp_fin_year = dd.dspdtl_fin_year)")
								.append(" inner join smpitem pr on(dd.DSPDTL_PROD_ID=pr.SMP_PROD_ID) inner join sum_disp_header_arc sh ")
								.append(" on(sh.sumdsp_id=dh.dsp_sum_dsp_id and dh.dsp_fin_year=sh.sumdsp_fin_year) ")
								.append(" join location l on l.loc_id=dh.dsp_loc_id where dh.dsp_fin_year='")
								.append(year).append("' and ");
					}
					if (!(fstaff_id.equalsIgnoreCase("0"))) {
						qryString.append(
								" dd.DSPDTL_FSTAFF_ID in (select FSTAFF_ID  from fieldstaff where FSTAFF_LEVEL_CODE= '")
								.append(fstaff_id).append("') AND ");
					}
					qryString.append("  CONVERT(date,SH.SUMDSP_CHALLAN_DT,105)>=CONVERT(date,'").append(stdt)
							.append("',105) ").append(" AND CONVERT(date,SH.SUMDSP_CHALLAN_DT,105)<=CONVERT(date,'")
							.append(endt).append("',105) ");

					qryString.append(
							" AND DH.DSP_DIV_ID IN  (select ediv_div_id from am_m_emp_div_access where ediv_emp_id= '")
							.append(emp_id1).append("') AND DH.DSP_STATUS= '").append(deleted_inv);
					if (!emp_id.equalsIgnoreCase("0") && !emp_id.isEmpty()) {
						qryString.append("' AND dd.DSPDTL_FSTAFF_ID= '").append(emp_id);

						qryString.append("' and dh.DSP_LOC_ID = l.loc_id ");
						qryString.append(" and l.loc_SubComp_id = CASE WHEN ").append(locfrmId)
								.append("=0 THEN l.loc_SubComp_id ELSE ").append(locfrmId).append(" END");
						qryString.append(" order by SMP_PROD_NAME ");

					} else {
						qryString.append("' and dh.DSP_LOC_ID = l.loc_id ");
						qryString.append(" and l.loc_SubComp_id = CASE WHEN ").append(locfrmId)
								.append("=0 THEN l.loc_SubComp_id ELSE ").append(locfrmId).append(" END");
						qryString.append(" order by SMP_PROD_NAME ");
					}

					Query query = em.createNativeQuery(qryString.toString(), Tuple.class);
					tuples = query.getResultList();
				}
				// =======================================================================================================
				else {
					StringBuffer qryString = null;
					if (finyearflag.equals("CURRENT")) {
						qryString = new StringBuffer("select distinct dspdtl_prod_id,SMP_PROD_NAME ").append(
								" from  dispatch_header dh left join dispatch_detail dd on(dd.dspdtl_dsp_id=dh.dsp_id) ")
								.append(" inner join smpitem pr on(dd.DSPDTL_PROD_ID=pr.SMP_PROD_ID) inner join sum_disp_header sh ")
								.append(" on(sh.sumdsp_id=dh.dsp_sum_dsp_id) ")
								.append(" join location l on l.loc_id=dh.dsp_loc_id where");
					} else {
						qryString = new StringBuffer("select distinct dspdtl_prod_id,SMP_PROD_NAME ").append(
								" from  dispatch_header_arc dh left join dispatch_detail_arc dd on(dd.dspdtl_dsp_id=dh.dsp_id ")
								.append(" and dh.dsp_fin_year = dd.dspdtl_fin_year)")
								.append(" inner join smpitem pr on(dd.DSPDTL_PROD_ID=pr.SMP_PROD_ID) inner join sum_disp_header_arc sh ")
								.append(" on(sh.sumdsp_id=dh.dsp_sum_dsp_id and dh.dsp_fin_year=sh.sumdsp_fin_year) ")
								.append(" join location l on l.loc_id=dh.dsp_loc_id where dh.dsp_fin_year='")
								.append(year).append("' and ");
					}
					if (!(fstaff_id.equalsIgnoreCase("0"))) {
						qryString.append(
								" dd.DSPDTL_FSTAFF_ID in (select FSTAFF_ID  from fieldstaff where FSTAFF_LEVEL_CODE= '")
								.append(fstaff_id).append("') AND ");
					}
					qryString.append("  CONVERT(date,SH.SUMDSP_CHALLAN_DT,105)>=CONVERT(date,'").append(stdt)
							.append("',105) ").append(" AND CONVERT(date,SH.SUMDSP_CHALLAN_DT,105)<=CONVERT(date,'")
							.append(endt).append("',105) ");

					qryString.append(" AND DH.DSP_DIV_ID =").append(div_Id);
					qryString.append(" AND DH.DSP_STATUS= '").append(deleted_inv);
					if (!emp_id.equalsIgnoreCase("0") && !emp_id.isEmpty()) {
						qryString.append("' AND dd.DSPDTL_FSTAFF_ID= '").append(emp_id);

						qryString.append("' and dh.DSP_LOC_ID = l.loc_id ");
						qryString.append(" and l.loc_SubComp_id = CASE WHEN ").append(locfrmId)
								.append("=0 THEN l.loc_SubComp_id ELSE ").append(locfrmId).append(" END");
						qryString.append(" order by SMP_PROD_NAME ");

					} else {
						qryString.append("' and dh.DSP_LOC_ID = l.loc_id ");
						qryString.append(" and l.loc_SubComp_id = CASE WHEN ").append(locfrmId)
								.append("=0 THEN l.loc_SubComp_id ELSE ").append(locfrmId).append(" END");
						qryString.append(" order by SMP_PROD_NAME ");
					}

					Query query = em.createNativeQuery(qryString.toString(), Tuple.class);
					tuples = query.getResultList();
				}

			}
			list = new ArrayList<ReportBean>();
			if (tuples != null && !tuples.isEmpty()) {
				for (Tuple t : tuples) {
					ReportBean bean = new ReportBean();
					bean.setProductId(Long.valueOf(t.get("dspdtl_prod_id", Integer.class)));
					bean.setProductName(t.get("SMP_PROD_NAME", String.class));
					list.add(bean);
				}
			}

		} finally {
			if (em != null) {
				em.close();
			}
		}

		return list;
	}

	@Override
	public DispatchHeaderData getDispatchedHeaderData(Long dsp_id, Long loc_id) throws Exception {
		EntityManager em = null;
		DispatchHeaderData result = null;
		try {
			em = emf.createEntityManager();
			StoredProcedureQuery query = em.createNamedStoredProcedureQuery("p_v_dispatch1_java");
			query.setParameter("dsp_id", dsp_id);
			query.setParameter("loc_id", loc_id);
			result = (DispatchHeaderData) query.getSingleResult();
		} finally {
			if (em != null) {
				em.close();
			}
		}
		return result;
	}

	@Override
	public Dispatch_Header getDispatchHeaderByChallanNo(String challan_no) throws Exception {
		EntityManager em = null;
		Dispatch_Header header = null;
		try {
			em = emf.createEntityManager();
			CriteriaBuilder builder = em.getCriteriaBuilder();
			CriteriaQuery<Dispatch_Header> criteriaQuery = builder.createQuery(Dispatch_Header.class);
			Root<Dispatch_Header> root = criteriaQuery.from(Dispatch_Header.class);
			criteriaQuery.select(root).where(builder.equal(root.get(Dispatch_Header_.dspChallanNo), challan_no));
			header = em.createQuery(criteriaQuery).getResultList().get(0);
		} finally {
			if (em != null) {
				em.close();
			}
		}
		return header;
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public void updateDispatchHeaderForSelfApproval(String challanNumber, Date date, String ip_address, String empId) {
		String query = "update dispatch_header set Dsp_appr_status='F', Dsp_mod_dt=:date,Dsp_mod_ip_add=:ip_addr ,Dsp_mod_usr_id=:empId where dsp_challan_no=:challanNumber";

		entityManager.createNativeQuery(query).setParameter("challanNumber", challanNumber).setParameter("date", date)
				.setParameter("ip_addr", ip_address).setParameter("empId", empId).executeUpdate();
	}

//	@Override
//	public Map<String,Object>  getDivisionForDispatchApproval() {
//		Map<String,Object> map=new HashMap<>();
//		List<String> divNameList=new ArrayList<>();
//		
//		String q="select * from v_EMP_DIV_Access where DIV_status='A' and EMP_ID='E00040'";
//		
//		EntityManager em =  emf.createEntityManager();
//		Query query = em.createNativeQuery(q, Tuple.class);
//		List<Tuple> tuples = query.getResultList();
//		if (tuples != null && !tuples.isEmpty()) {
//			for(Tuple t : tuples) {
//				String s=t.get("div_disp_nm", String.class);
//				divNameList.add(s);
//			}
//		}
//		map.put("divNameList", divNameList);
//		return map;	
//	}

	@Override
	public Object getHeaderForDispatchApproval(@RequestParam String ChallanNo) {
		System.out.println(ChallanNo);
		List<ManualDispatchList> manualDispatchList = new ArrayList<>();
		// List<ManualDispatchList> challanDetailList=new ArrayList<>();
		List<Object> challanDetailList = new ArrayList<>();
		Map<String, Object> mapList = new HashMap<>();
		List<String> challanNumberList = new ArrayList<>();
		int dspId = 0;
		EntityManager em = null;
		try {
			em = emf.createEntityManager();
			StringBuffer ch = new StringBuffer();
			if (ChallanNo.split(",").length == 1) {
				ch.append("'" + ChallanNo + "'");
			} else {
				for (int i = 0; i < ChallanNo.split(",").length; i++) {
					if (i == ChallanNo.split(",").length - 1) {
						ch.append("'" + ChallanNo.split(",")[i] + "'");
					} else {
						ch.append("'" + ChallanNo.split(",")[i] + "' ,");
					}
				}
			}
			// String q="select d.dsp_id,alloc_policy_name, fstaff_display_name,
			// m.sumdsp_disp_cycle, m.dsp_dt, m.dsp_div_id,division,d.dsp_cycle,
			// d.DSP_CHALLAN_NO from v_manual_dispatch_list m , Dispatch_header d where
			// d.DSP_SUM_CHALLAN_NO in ( "+ch.toString()+" ) and d.DSP_ID=m.DSP_ID";

			StringBuffer q = new StringBuffer();

			q.append(
					" select d.dsp_id,alloc_policy_name, fstaff_display_name, m.sumdsp_disp_cycle, m.dsp_dt, m.dsp_div_id,division,d.dsp_cycle,");
			q.append(" d.DSP_CHALLAN_NO from v_manual_dispatch_list m ,");
			//q.append(" d.DSP_CHALLAN_NO from v_manual_dispatch_list_corr m ,");
			q.append(
					" (SELECT DSP_ID,DSP_CHALLAN_NO,DSP_CYCLE,DSP_SMP_ID FROM Dispatch_header D  WHERE d.DSP_SUM_CHALLAN_NO");
			q.append(" in ( " + ch.toString() + " ) ) D");
			q.append(" WHERE");
			q.append(" d.DSP_ID=m.DSP_ID");
			q.append(" AND");
			q.append(" M.DSP_ID IN");
			q.append(" (");
			q.append(" SELECT DSP_ID FROM Dispatch_header D  WHERE d.DSP_SUM_CHALLAN_NO");
			q.append(" in ( " + ch.toString() + " ) AND  DSP_APPR_STATUS = 'E' )    ");
			q.append(" ORDER BY D.DSP_ID");
			
			
			

			Query query = em.createNativeQuery(q.toString(), Tuple.class);
			// query.setParameter("ChallanNo", ChallanNo);
			List<Tuple> tuples = query.getResultList();
			if (tuples != null && !tuples.isEmpty()) {
				challanDetailList = new ArrayList<>();
				for (Tuple t : tuples) {
					Map<String, Object> map = new HashMap<>();
					challanNumberList.add(t.get("dsp_challan_no", String.class));
					dspId = t.get("dsp_id", Integer.class);
					map.put("dsp_id", t.get("dsp_id", Integer.class));
					map.put("dsp_cycle", t.get("dsp_cycle", Integer.class));
					map.put("alloc_policy_name", t.get("alloc_policy_name", String.class));
					map.put("division", t.get("division", String.class));
					map.put("sumdsp_disp_cycle", t.get("sumdsp_disp_cycle", Integer.class));
					map.put("dsp_challan_no", t.get("dsp_challan_no", String.class));
					map.put("dsp_dt", t.get("dsp_dt", String.class));
					map.put("fstaff_display_name", t.get("fstaff_display_name", String.class));
					challanDetailList.add(map);
				}

			}
			System.out.println("challanDetailList::" + challanDetailList.size());
			mapList.put("dspId", dspId);
			mapList.put("challanNumberList", challanNumberList);
			mapList.put("challanDetailList", challanDetailList);
		} catch (Exception e) {
			System.out.println("Error Occurred :" + e.getMessage());// uncomment asneeded --;
		} finally {
			if (em != null) {
				em.close();
			}
		}

		return mapList;
	}

	@Override
	public Map<String, Object> getDetailForDispatchApproval(@RequestParam int dspId) {
		System.out.println("dsp id in spring boot : " + dspId);
		Map<String, Object> mapList = new HashMap<>();
		;
		List<Object> detailList = new ArrayList<>();
		EntityManager em = null;
		try {
			em = emf.createEntityManager();
			String q = "select SMP_PROD_NAME as product_name, batch_no, CONVERT(varchar(10),BATCH_EXPIRY_DT,103) as exp_dt, DSPDTL_DISP_QTY as qty from dbo.Dispatch_detail left join smpitem on DSPDTL_PROD_ID=SMP_PROD_ID left join smpbatch  on DSPDTL_BATCH_ID=batch_id where DSPDTL_DSP_ID=:dspId";
			Query query = em.createNativeQuery(q, Tuple.class);
			query.setParameter("dspId", dspId);
			List<Tuple> tuples = query.getResultList();
			Map<String, Object> map = null;
			if (tuples != null && !tuples.isEmpty()) {
				for (Tuple t : tuples) {
					map = new HashMap<>();
					String prodName = t.get("product_name", String.class);
					System.out.println(prodName);
					map.put("product_name", t.get("product_name", String.class));
					map.put("batch_no", t.get("batch_no", String.class));
					map.put("exp_dt", t.get("exp_dt", String.class));
					map.put("qty", t.get("qty", BigDecimal.class));
					detailList.add(map);

				}
				mapList.put("detailList", detailList);
			}
		} catch (Exception e) {
			System.out.println("Error Occurred :" + e.getMessage());// uncomment asneeded --;
		} finally {
			if (em != null) {
				em.close();
			}
		}
		return mapList;
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public void updateDispatchHeaderForDispatchSelfDiscard(String empId, List<String> challanNumberList,
			HttpServletRequest request) {
		try {
			System.out.println(challanNumberList);
			String ChallanNo = String.join(",", challanNumberList);
			StringBuffer ch = new StringBuffer();
			if (ChallanNo.split(",").length == 1) {
				ch.append("'" + ChallanNo + "'");
			} else {
				for (int i = 0; i < ChallanNo.split(",").length; i++) {
					if (i == ChallanNo.split(",").length - 1) {
						ch.append("'" + ChallanNo.split(",")[i] + "'");
					} else {
						ch.append("'" + ChallanNo.split(",")[i] + "' ,");
					}
				}
			}

			Date date = new Date();
			String ip_addr = request.getRemoteAddr();
			String query = "update dispatch_header set Dsp_appr_status='D', Dsp_mod_dt=:date,Dsp_mod_ip_add=:ip_addr ,Dsp_mod_usr_id=:empId where dsp_challan_no in ("
					+ ch.toString() + ")";

			entityManager.createNativeQuery(query).setParameter("date", date).setParameter("ip_addr", ip_addr)
					.setParameter("empId", empId).executeUpdate();
		} catch (Exception e) {
			System.out.println("Error Occurred :" + e.getMessage());// uncomment asneeded --;
		}

	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public void updateDispatchHeaderForDispatchSelfApproval(String empId, List<String> challanNumberList,
			HttpServletRequest request) {
		EntityManager em = emf.createEntityManager();
		System.out.println("challan no list is : " + challanNumberList);
		try {
			em.getTransaction().begin();
			for (int i = 0; i < challanNumberList.size(); i++) {
				String challanNumber = challanNumberList.get(i);
				System.out.println(challanNumber);
				Date date = new Date();
				String ip_addr = request.getRemoteAddr();
				String query = "update dispatch_header set Dsp_appr_status='F', Dsp_mod_dt=:date,Dsp_mod_ip_add=:ip_addr ,Dsp_mod_usr_id=:empId where dsp_challan_no=:challanNumber";

				em.createNativeQuery(query).setParameter("challanNumber", challanNumber).setParameter("date", date)
						.setParameter("ip_addr", ip_addr).setParameter("empId", empId).executeUpdate();

			}
			em.getTransaction().commit();
		} catch (Exception e) {
			System.out.println("Error Occurred :" + e.getMessage());// uncomment asneeded --;
		} finally {
			if (em != null) {
				em.close();
			}
		}
	}

	@Override
	public List<GenerateDispatchData_AllocType> genAutoDispatchAllocType(String comp_cd, String fin_year,
			String period_cd, Long dsp_loc_id, Long smp_id, Long pi_loc_id, Long pi_abm, Long pi_rbm, Long pi_stateId,
			Long pi_msr, String pend_alloc, String action, String pvinsusr_id, Long pvinsip, Long pi_div_id,
			Long sample_rbm, Long sample_abm, String sample_flag, String challan_msg, Long chalan_periodId,
			String req_lvl, Long zone_id, String direct_to_pso_ind, boolean stock_at_cfa_ind, String allocType,
			String prodIds, String prodSubTypes, String subTeamCodeList)
			throws Exception, SQLException, PersistenceException {
		EntityManager em = null;
		List<GenerateDispatchData_AllocType> list = null;
		System.out.println();
		System.out.println("Param : " + comp_cd + ":" + fin_year + ":" + period_cd + ":" + dsp_loc_id + ":" + smp_id
				+ ":" + pi_loc_id + ":" + pi_abm + ":" + pi_rbm + ":" + pi_stateId + ":" + pi_msr + ":" + pend_alloc
				+ ":" + action + ":" + pvinsusr_id + ":" + pvinsip + ":" + pi_div_id + ":" + sample_rbm + ":"
				+ sample_abm + ":" + sample_flag + ":" + challan_msg + ":" + chalan_periodId + ":" + req_lvl + ":"
				+ zone_id + ":" + direct_to_pso_ind + ":" + allocType + ":" + prodIds + ":" + prodSubTypes + ":"
				+ subTeamCodeList);
		try {
			em = emf.createEntityManager();
			System.out.println("p_gen_dispatch_java_ALLOC_TYPE_PRODSELC_PRDSUBTYPEID_TEAM " + subTeamCodeList);
			if (comp_cd.equals(PAL)) {
				StoredProcedureQuery query = em
						.createNamedStoredProcedureQuery("callGenerateDispatchDataAllocType_tkey");
				query.setParameter("pcDSP_COMPANY", comp_cd);
				query.setParameter("pcDSP_FIN_YEAR", fin_year);
				query.setParameter("pcDSP_PERIOD_CODE", period_cd);
				query.setParameter("piDSP_LOC_ID", dsp_loc_id);
				query.setParameter("piDSP_SMP_ID", smp_id);
				query.setParameter("pirlocid", pi_loc_id);
				query.setParameter("piabm", pi_abm);
				query.setParameter("pirbm", pi_rbm);
				query.setParameter("pistateid", pi_stateId);
				query.setParameter("pimsr", pi_msr);
				query.setParameter("pcpendalloc", pend_alloc);
				query.setParameter("pcaction", action);
				query.setParameter("pvinsusr_id", pvinsusr_id);
				query.setParameter("pvinsip", pvinsip.toString());
				query.setParameter("pidiv_id", pi_div_id);
				query.setParameter("pisamplerbm", sample_rbm);
				query.setParameter("pisampleabm", sample_abm);
				query.setParameter("pcsampleflg", sample_flag);
				query.setParameter("pvchallan_msg", challan_msg);
				query.setParameter("pvchallan_PERIOD_ID", chalan_periodId);
				query.setParameter("pvDSP_APPR_REQ", req_lvl);
				query.setParameter("pirzonid", zone_id);
				query.setParameter("Pdirect_to_pso_ind", direct_to_pso_ind);
				query.setParameter("PALLOC_TYPE", allocType);
				query.setParameter("pcProd_id", prodIds);
				query.setParameter("pcProd_subtypid", prodSubTypes);
				query.setParameter("pcTeam_code", subTeamCodeList);

				if (action.equalsIgnoreCase("G")) {
					list = query.getResultList();
				} else {
					query.execute();
				}
			} else if (comp_cd.equals(VET)) {
				System.out.println("callGenerateDispatchDataAllocTypeveto " + subTeamCodeList);
				StoredProcedureQuery query = em
						.createNamedStoredProcedureQuery("callGenerateDispatchDataAllocTypeveto");
				query.setParameter("pcDSP_COMPANY", comp_cd);
				query.setParameter("pcDSP_FIN_YEAR", fin_year);
				query.setParameter("pcDSP_PERIOD_CODE", period_cd);
				query.setParameter("piDSP_LOC_ID", dsp_loc_id);
				query.setParameter("piDSP_SMP_ID", smp_id);
				query.setParameter("pirlocid", pi_loc_id);
				query.setParameter("piabm", pi_abm);
				query.setParameter("pirbm", pi_rbm);
				query.setParameter("pistateid", pi_stateId);
				query.setParameter("pimsr", pi_msr);
				query.setParameter("pcpendalloc", pend_alloc);
				query.setParameter("pcaction", action);
				query.setParameter("pvinsusr_id", pvinsusr_id);
				query.setParameter("pvinsip", pvinsip.toString());
				query.setParameter("pidiv_id", pi_div_id);
				query.setParameter("pisamplerbm", sample_rbm);
				query.setParameter("pisampleabm", sample_abm);
				query.setParameter("pcsampleflg", sample_flag);
				query.setParameter("pvchallan_msg", challan_msg);
				query.setParameter("pvchallan_PERIOD_ID", chalan_periodId);
				query.setParameter("pvDSP_APPR_REQ", req_lvl);
				query.setParameter("pirzonid", zone_id);
				query.setParameter("Pdirect_to_pso_ind", direct_to_pso_ind);
				query.setParameter("PALLOC_TYPE", allocType);
				query.setParameter("pcProd_id", prodIds);
				query.setParameter("pcProd_subtypid", prodSubTypes);
				query.setParameter("pcTeam_code", subTeamCodeList);

				if (action.equalsIgnoreCase("G")) {
					list = query.getResultList();
				} else {
					query.execute();
				}
			} else {
				StoredProcedureQuery query = em.createNamedStoredProcedureQuery("callGenerateDispatchDataAllocType");
				query.setParameter("pcDSP_COMPANY", comp_cd);
				query.setParameter("pcDSP_FIN_YEAR", fin_year);
				query.setParameter("pcDSP_PERIOD_CODE", period_cd);
				query.setParameter("piDSP_LOC_ID", dsp_loc_id);
				query.setParameter("piDSP_SMP_ID", smp_id);
				query.setParameter("pirlocid", pi_loc_id);
				query.setParameter("piabm", pi_abm);
				query.setParameter("pirbm", pi_rbm);
				query.setParameter("pistateid", pi_stateId);
				query.setParameter("pimsr", pi_msr);
				query.setParameter("pcpendalloc", pend_alloc);
				query.setParameter("pcaction", action);
				query.setParameter("pvinsusr_id", pvinsusr_id);
				query.setParameter("pvinsip", pvinsip.toString());
				query.setParameter("pidiv_id", pi_div_id);
				query.setParameter("pisamplerbm", sample_rbm);
				query.setParameter("pisampleabm", sample_abm);
				query.setParameter("pcsampleflg", sample_flag);
				query.setParameter("pvchallan_msg", challan_msg);
				query.setParameter("pvchallan_PERIOD_ID", chalan_periodId);
				query.setParameter("pvDSP_APPR_REQ", req_lvl);
				query.setParameter("pirzonid", zone_id);
				query.setParameter("Pdirect_to_pso_ind", direct_to_pso_ind);
				query.setParameter("PALLOC_TYPE", allocType);
				query.setParameter("pcProd_id", prodIds);
				query.setParameter("pcProd_subtypid", prodSubTypes);
				query.setParameter("pcTeam_code", subTeamCodeList);
				if (action.equalsIgnoreCase("G")) {
					list = query.getResultList();
					System.out.println("list : " + list.size());
				} else {
					query.execute();
				}
			}

			System.out.println("Proc Exited");
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			throw e;
		} finally {
			if (em != null) {
				em.close();
			}
		}
		return list;
	}

	@Override
	public List<Dispatch_Detail> getDtlListByHdrId(Long dspdtl_dsp_id, String status) throws Exception {
		List<Dispatch_Detail> list = null;
		try {
			StringBuffer sb = new StringBuffer();
			sb.append(
					"from Dispatch_Detail d where dspdtlDspId=:dspdtlDspId and dspdtl_status=:dspdtl_status order by dspdtlId");
			Query query = entityManager.createQuery(sb.toString(), Dispatch_Detail.class);
			query.setParameter("dspdtlDspId", dspdtl_dsp_id);
			query.setParameter("dspdtl_status", status);
			list = query.getResultList();
		} finally {
		}
		return list;
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public void deleteDispatchRecord(Long dsp_id, String emp_id, String ip_add, String status) throws Exception {
		StoredProcedureQuery query = entityManager.createStoredProcedureQuery("p_d_dispatch");
		query.registerStoredProcedureParameter("pcaction", String.class, ParameterMode.IN);
		query.registerStoredProcedureParameter("piDSP_ID", Long.class, ParameterMode.IN);
		query.registerStoredProcedureParameter("pvDSP_usr_id", String.class, ParameterMode.IN);
		query.registerStoredProcedureParameter("pvDSP_ip_add", String.class, ParameterMode.IN);
		query.registerStoredProcedureParameter("pcDSP_status", String.class, ParameterMode.IN);

		query.setParameter("pcaction", "D");
		query.setParameter("piDSP_ID", dsp_id);
		query.setParameter("pvDSP_usr_id", emp_id);
		query.setParameter("pvDSP_ip_add", ip_add);
		query.setParameter("pcDSP_status", status);
		query.execute();
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public void deleteDispatchDetail(String comp_cd, String year, String dsp_period_cd, Long dsp_id, Long prod_id,
			String emp_id, String ip_add) throws Exception {
		StoredProcedureQuery procedureCall = entityManager.createStoredProcedureQuery("Dispatch_delete");
		procedureCall.registerStoredProcedureParameter("comp_cd", String.class, ParameterMode.IN);
		procedureCall.registerStoredProcedureParameter("year", String.class, ParameterMode.IN);
		procedureCall.registerStoredProcedureParameter("dsp_period_cd", String.class, ParameterMode.IN);
		procedureCall.registerStoredProcedureParameter("dsp_id", Long.class, ParameterMode.IN);
		procedureCall.registerStoredProcedureParameter("prod_id", Long.class, ParameterMode.IN);
		procedureCall.registerStoredProcedureParameter("emp_id", String.class, ParameterMode.IN);
		procedureCall.registerStoredProcedureParameter("ip_add", String.class, ParameterMode.IN);

		procedureCall.setParameter("comp_cd", comp_cd);
		procedureCall.setParameter("year", year);
		procedureCall.setParameter("dsp_period_cd", dsp_period_cd);
		procedureCall.setParameter("dsp_id", dsp_id);
		procedureCall.setParameter("prod_id", prod_id);
		procedureCall.setParameter("emp_id", emp_id);
		procedureCall.setParameter("ip_add", ip_add);

		procedureCall.execute();
	}

	@Override
	public List<ManulDispatchProdListData> getdispatchProdDetailsEdit(Long dsp_id, Long dspdtl_id, String pend_alloc)
			throws Exception {
		List<ManulDispatchProdListData> list = null;
		EntityManager em = null;
		try {
			em = emf.createEntityManager();
			StoredProcedureQuery query = em.createNamedStoredProcedureQuery("callManulDispatchProdListDataEdit");
			query.setParameter("pidsp_id", dsp_id);
			query.setParameter("pidspdtl_id", dspdtl_id);
			query.setParameter("pcpendalloc", pend_alloc);
			list = query.getResultList();
		} finally {
			if (em != null) {
				em.close();
			}
		}
		return list;
	}

	@Override
	public List<Object> getFieldstaffDetail(Long fsId, Long locId) throws Exception {
		List<Object> list = new ArrayList<Object>();
		try {
			StringBuffer sb = new StringBuffer();
			sb.append(
					"select fstaffd_loc_id from fieldstaff_detail where fstaffd_fstaff_id=:fsId and FSTAFFD_SubComp_id=(select loc_SubComp_id from location where loc_id=:locId)");
			Query query = entityManager.createNativeQuery(sb.toString());
			query.setParameter("fsId", fsId);
			query.setParameter("locId", locId);
			list = query.getResultList();
		} catch (Exception e) {
			System.out.println("Error Occurred :" + e.getMessage());// uncomment asneeded --;
		} finally {
		}
		return list;
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public void uploadlrCsvData(List<Sum_Disp_Header> datasum, List<Dispatch_Header> datadispt, String finyr,
			DispatchBean bean) throws MedicoException {
		int i = 0;
		String msg = "";
		int counter = 0;
		try {
			System.out.println("in uploadlrCsvData:");
			for (Sum_Disp_Header header : datasum) {
				msg = "Table:Sum_Disp_Header- Master Challon No.:" + header.getSumdsp_challan_no();
				i++;
				Sum_Disp_Header sum_Disp_Header = (Sum_Disp_Header) this.entityManager.find(Sum_Disp_Header.class,
						header.getSumdsp_id());

				// if(sum_Disp_Header.getSumdsp_lr_no().equals("") &&
				// sum_Disp_Header.getSumdsp_lr_dt()==null &&sum_Disp_Header.getSumdsp_cases()
				// == 0 ) {
				sum_Disp_Header.setSumdsp_lr_no(header.getSumdsp_lr_no());
				sum_Disp_Header.setSumdsp_lr_dt(header.getSumdsp_lr_dt());
				sum_Disp_Header.setSumdsp_transporter(header.getSumdsp_transporter());
				sum_Disp_Header.setSumdsp_wt(header.getSumdsp_wt());
				sum_Disp_Header.setSumdsp_totwt(header.getSumdsp_totwt());
				sum_Disp_Header.setSumdsp_cases(header.getSumdsp_cases());
				sum_Disp_Header.setSumdsp_totcases(header.getSumdsp_totcases());
				sum_Disp_Header.setSumdsp_delivery_date(header.getSumdsp_delivery_date());
				sum_Disp_Header.setSumdsp_recd_by(header.getSumdsp_recd_by());
				sum_Disp_Header.setSumdsp_remark(header.getSumdsp_remark());
				sum_Disp_Header.setSumdsp_mod_dt(new Date());
				sum_Disp_Header.setSumdsp_mod_ip_add(bean.getIpAddress());
				sum_Disp_Header.setSumdsp_mod_usr_id(bean.getEmpId());

				sum_Disp_Header.setCourier_expenses(header.getCourier_expenses());
				sum_Disp_Header.setActual_delivery_date(header.getActual_delivery_date());
				sum_Disp_Header.setWay_bill_no(header.getWay_bill_no());

				if (bean.getCompanyCode().trim().equals(VET)) {
					sum_Disp_Header.setTrans_gst_reg_no(header.getTrans_gst_reg_no());
					sum_Disp_Header.setSumdsp_lorry_no(header.getSumdsp_lorry_no());
				}
				
				if (bean.getCompanyCode().trim().equals(PFZ)) { // changed by nilesh 15 Jan
					sum_Disp_Header.setTransport_mode(header.getTransport_mode());
				}

				this.entityManager.merge(sum_Disp_Header);
				counter++;
				// }
				System.out.println("Sumdsp_challan_no:" + header.getSumdsp_challan_no());
				if (i % 20 == 0) {
					this.entityManager.flush();
					this.entityManager.clear();
				}
			}

			System.out.println(" Sum_header counter :" + counter);
			counter = 0;
			i = 0;
			for (Dispatch_Header dispheder : datadispt) {
				i++;
				msg = "Dispatch_header- Individual Challon No.:" + dispheder.getDspChallanNo();
				String qury = new String();
				String lr_dt_qry = null;
				String lr_dt_dlv = null;
				String actual_dt = null;
				if (dispheder.getDsp_lr_dt() != null) {
					lr_dt_qry = "'" + dispheder.getDsp_lr_dt() + "'";
				}
				if (dispheder.getDsp_delivery_date() != null) {
					lr_dt_dlv = "'" + dispheder.getDsp_delivery_date() + "'";
				}
				System.out.println("Actual DAte :: " + dispheder.getActual_delivery_date());
				if (dispheder.getActual_delivery_date() != null) {
					actual_dt = "'" + dispheder.getActual_delivery_date() + "'";
				}
//			qury = "update Dispatch_Header set DSP_LR_NO='" + dispheder.getDsp_lr_no() + "', DSP_LR_DT=" + lr_dt_qry
//				+ " ,DSP_TRANSPORTER='" + dispheder.getDsp_transporter() + "',DSP_WT=" + dispheder.getDsp_wt()
//				+ " ,DSP_BILLABLE_WT=" + dispheder.getDsp_billable_wt() + " ,DSP_CASES="
//				+ dispheder.getDsp_cases() + " , DSP_DELIVERY_DATE=" + lr_dt_dlv + " , DSP_RECD_BY='"
//				+ dispheder.getDsp_recd_by() + "' , DSP_REMARK='" + dispheder.getDsp_remark() + "'"
//				+ " , WAY_BILL_NO='" + dispheder.getWay_bill_no() + "'" +" , DSP_MOD_DT=GETDATE()"+ " where DSP_SUM_DSP_ID="
//				+ dispheder.getDsp_sum_dsp_id() + " and DSP_CHALLAN_NO='" + dispheder.getDspChallanNo() + "'";

				qury = "update Dispatch_Header set DSP_LR_NO='" + dispheder.getDsp_lr_no() + "', DSP_LR_DT=" + lr_dt_qry
						+ " ,DSP_TRANSPORTER='" + dispheder.getDsp_transporter() + "',DSP_WT=" + dispheder.getDsp_wt()
						+ " ,DSP_BILLABLE_WT=" + dispheder.getDsp_billable_wt() + " ,DSP_CASES="
						+ dispheder.getDsp_cases() + " , DSP_DELIVERY_DATE=" + lr_dt_dlv + " , DSP_RECD_BY='"
						+ dispheder.getDsp_recd_by() + "' , DSP_REMARK='" + dispheder.getDsp_remark() + "'"
						+ " , WAY_BILL_NO='" + dispheder.getWay_bill_no() + "'" + " , DSP_MOD_DT=GETDATE(),"
						+ "COURIER_EXPENSES ='" + dispheder.getCourier_expenses() + "'," + " ACTUAL_DELIVERY_DATE="
						+ actual_dt + ", Dsp_mod_usr_id='" + bean.getEmpId() + "',DSP_mod_ip_add='"
						+ bean.getIpAddress() + "'";
				if (bean.getCompanyCode().trim().equals(VET)) {
					qury += (" ,DSP_TRANS_GST_REG_NO='" + dispheder.getDsp_trans_gst_reg_no() + "',DSP_LORRY_NO='"
							+ dispheder.getDsp_lorry_no() + "'");
				}else if (bean.getCompanyCode().trim().equals(PFZ)) { // changed by nilesh 15 Jan
					qury += (", transport_mode='" + dispheder.getTransport_mode() + "'");
				} else {// changed by yash 15 Jul
					qury += (" ,DSP_TRANS_GST_REG_NO='" + dispheder.getDsp_trans_gst_reg_no() + "'");
				}
				qury += " where DSP_SUM_DSP_ID=" + dispheder.getDsp_sum_dsp_id() + " and DSP_CHALLAN_NO='"
						+ dispheder.getDspChallanNo() + "'";// and Rtrim(DSP_LR_NO) ='' and DSP_LR_DT "+
				// "is null and DSP_CASES = 0 "; //change 24-8-2020 1:16pm

				System.out.println("qury:" + qury);
				Query qry = entityManager.createNativeQuery(qury);
				qry.executeUpdate();
				if (i % 20 == 0) {
					this.entityManager.flush();
					this.entityManager.clear();
				}

				counter++;
			}

			System.out.println(" Dispatch_Header counter :" + counter);
		} catch (Exception e) {
			System.out.println("Error Occurred :" + e.getMessage());// uncomment asneeded --;
			throw new MedicoException("Error While Updating " + msg + " :" + e.getMessage());
		}
		return;
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public void uploadlrCsvDataInAddl(List<Sum_Disp_Header_Addl> datasum_adl, List<Dispatch_Header_Addl> datadispt_adl)
			throws MedicoException {
		String msg = "";
		try {
			for (Sum_Disp_Header_Addl header : datasum_adl) {
				msg = "Table:Sum_Disp_Header_Addl- Master Challon No.:" + header.getSumdsp_addl_challan_no();
				Query query = entityManager
						.createQuery("from Sum_Disp_Header_Addl where sumdsp_addl_id=:sumdsp_addl_id");
				query.setParameter("sumdsp_addl_id", header.getSumdsp_addl_id());
				List<Sum_Disp_Header_Addl> list = query.getResultList();
				for (int i = 0; i < list.size(); i++) {
					Sum_Disp_Header_Addl sum_Disp_Header_addl = list.get(i);
					sum_Disp_Header_addl.setSumdsp_lr_no(header.getSumdsp_lr_no());
					sum_Disp_Header_addl.setSumdsp_lr_dt(header.getSumdsp_lr_dt());
					sum_Disp_Header_addl.setSumdsp_transporter(header.getSumdsp_transporter());
					sum_Disp_Header_addl.setSumdsp_wt(header.getSumdsp_wt());
					sum_Disp_Header_addl.setSumdsp_totwt(header.getSumdsp_totwt());
					sum_Disp_Header_addl.setSumdsp_cases(header.getSumdsp_cases());
					sum_Disp_Header_addl.setSumdsp_totcases(header.getSumdsp_totcases());
					sum_Disp_Header_addl.setSumdsp_delivery_date(header.getSumdsp_delivery_date());
					sum_Disp_Header_addl.setSumdsp_recd_by(header.getSumdsp_recd_by());
					sum_Disp_Header_addl.setSumdsp_remark(header.getSumdsp_remark());
					entityManager.merge(sum_Disp_Header_addl);
				}

			}
			int i = 0;
			for (Dispatch_Header_Addl dispheder : datadispt_adl) {
				i++;
				msg = "Dispatch_Header_Addl- Individual Challon No.:" + dispheder.getDsp_challan_no();
				String qury = new String();
				String lr_dt_qry = null;
				if (dispheder.getDsp_lr_dt() != null) {
					lr_dt_qry = "'" + dispheder.getDsp_lr_dt() + "'";
				}
				qury = "update Dispatch_Header_Addl set DSP_LR_NO='" + dispheder.getDsp_lr_no() + "', DSP_LR_DT="
						+ lr_dt_qry + " , DSP_TRANSPORTER='" + dispheder.getDsp_transporter() + "', DSP_WT="
						+ dispheder.getDsp_wt() + " ,DSP_BILLABLE_WT=" + dispheder.getDsp_billable_wt()
						+ " , DSP_CASES=" + dispheder.getDsp_cases() + " , WAY_BILL_NO='" + dispheder.getWay_bill_no()
						+ "'" + " where DSP_SUM_DSP_ADDL_ID=" + dispheder.getDsp_sum_dsp_addl_id()
						+ " and dsp_challan_no='" + dispheder.getDsp_challan_no() + "' and DSP_DELIVERY_DATE="
						+ dispheder.getDsp_delivery_date() + " and DSP_RECD_BY='" + dispheder.getDsp_recd_by()
						+ "' and DSP_REMARK='" + dispheder.getDsp_remark() + "'";
				Query qry = entityManager.createNativeQuery(qury);
				qry.executeUpdate();
			}
		} catch (Exception e) {
			System.out.println("Error Occurred :" + e.getMessage());// uncomment asneeded --;
			throw new MedicoException(msg + " :" + e.getMessage());
		}
		return;
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public void truncateLrCSVUpload() throws Exception {
		try {
			Query query = entityManager.createNativeQuery("truncate table LR_CSV_UPLOAD");
			query.executeUpdate();
		} catch (Exception e) {
			throw e;
		}
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public void saveLrCSVUpload(List<LrCsvUpload> li) throws Exception {
		try {
			System.out.println("LrUpdate.saveLrCSVUpload()");
			for (LrCsvUpload download : li) {
				System.out.println("obj::" + download.getSr_no());
				entityManager.persist(download);
			}
		} catch (Exception e) {
			throw e;
		}
	}

	@Override
	public List<DispatchMailBean> getDispatchedMailData(Long dsp_id) throws Exception {
		List<DispatchMailBean> list = new ArrayList<DispatchMailBean>();
		EntityManager em = null;
		try {
			StringBuffer sb = new StringBuffer();
			sb.append(
					" select si.SMP_ERP_PROD_CD,si.SMP_PROD_NAME,dd.DSPDTL_DISP_QTY DISPATCH_QTY,dh.DSPFSTAFF_DISPLAYNAME DOCTOR_NAME,");
			sb.append(
					" dh.DSPFSTAFF_ADDR1,dh.DSPFSTAFF_ADDR2,dh.DSPFSTAFF_ADDR3,dh.DSPFSTAFF_ADDR4,dh.DSPFSTAFF_DESTINATION,");
			sb.append(
					" DH.DSP_TRANSPORTER COURIER_NAME, DH.DSP_LR_NO POD_NO, DH.DSP_LR_DT POD_DATE,'' EXPECTED_DELI_DATE,'' CONF_DELI_DATE,");
			sb.append(" '' REMARKS_BY_COURIER,dh.DSPFSTAFF_EMAIL,dh.DSP_CHALLAN_NO,dh.DSP_NO");
			sb.append(" from dispatch_detail dd");
			sb.append(" join dispatch_header dh on dh.dsp_id=dd.dspdtl_dsp_id");
			sb.append(" join smpitem si on si.smp_prod_id=dspdtl_prod_id");
			sb.append(" where dsp_id=:dsp_id");
			Query query = entityManager.createNativeQuery(sb.toString(), Tuple.class);
			query.setParameter("dsp_id", dsp_id);
			List<Tuple> tuples = query.getResultList();
			DispatchMailBean dsp = null;
			if (tuples != null && !tuples.isEmpty()) {
				for (Tuple t : tuples) {
					dsp = new DispatchMailBean();
					dsp.setSmp_erp_prod_cd(t.get("SMP_ERP_PROD_CD", String.class));
					dsp.setSmp_erp_prod_name(t.get("SMP_PROD_NAME", String.class));
					dsp.setDsp_qty(t.get("DISPATCH_QTY", BigDecimal.class));
					dsp.setDoctor_name(t.get("DOCTOR_NAME", String.class));
					dsp.setAddress(t.get("DSPFSTAFF_ADDR1", String.class) + " , "
							+ t.get("DSPFSTAFF_ADDR2", String.class) + " , " + t.get("DSPFSTAFF_ADDR3", String.class)
							+ " , " + t.get("DSPFSTAFF_ADDR4", String.class));
					dsp.setDestination(t.get("DSPFSTAFF_DESTINATION", String.class));
					dsp.setCourier_name(t.get("COURIER_NAME", String.class));
					dsp.setPod_date(t.get("POD_DATE", String.class));
					dsp.setExpected_date(t.get("EXPECTED_DELI_DATE", String.class));
					dsp.setConfirm_date(t.get("CONF_DELI_DATE", String.class));
					dsp.setRemarks(t.get("REMARKS_BY_COURIER", String.class));
					dsp.setMail(t.get("DSPFSTAFF_EMAIL", String.class));
					dsp.setDsp_no(t.get("DSP_CHALLAN_NO", String.class));
					list.add(dsp);
				}
			}
			System.out.println("LIST Size " + list.size());
		} catch (Exception e) {
			System.out.println("Error Occurred :" + e.getMessage());// uncomment asneeded --;
		} finally {
			if (em != null) {
				em.close();
			}
		}
		return list;

	}

	@Override
	public String getDispatchType(Long dsp_id) throws Exception {
		String dspType = null;
		try {
			StringBuffer sb = new StringBuffer();
			sb.append(" select alloc_type from dispatch_header dh");
			sb.append(" join alloc_header ah on ah.alloc_id=dh.DSP_ALLOC_ID where dsp_id=:dsp_id");
			Query query = entityManager.createNativeQuery(sb.toString());
			query.setParameter("dsp_id", dsp_id);
			dspType = query.getSingleResult().toString();
		} catch (Exception e) {
			System.out.println("Error Occurred :" + e.getMessage());// uncomment asneeded --;
		} finally {
		}
		return dspType;
	}

	@Override
	public List<ReportBean> getEmpDetails(String startdt, String endate, Long cfaLoc, String username, String role) {
		List<ReportBean> list = new ArrayList<ReportBean>();
		String deptloc = "0";// (String) session.getAttribute("DEPTLOC");
		try {

			StringBuffer temp = new StringBuffer();
			StringBuffer qryString = new StringBuffer(
					"select dh.DSP_FSTAFF_ID,dh.DSPFSTAFF_EMPLOYEENO,dh.DSPFSTAFF_NAME,dh.DSPFSTAFF_DISPLAYNAME from Dispatch_header dh");
			qryString.append(" where dh.DSP_status = 'A' ").append(" and CONVERT(date,DH.DSP_DT,105) >=CONVERT(date,'")
					.append(startdt).append("',105) ").append(" and CONVERT(date,DH.DSP_DT,105) <=CONVERT(date,'")
					.append(endate).append("',105) ");
			if (!deptloc.trim().equals("0")) {
				for (int i = 0; i < deptloc.length(); i = i + 4) {
					temp.append(Integer.parseInt(deptloc.substring(i, i + 4))).append(",");
				}
				if (temp.length() > 0) {
					temp.deleteCharAt(temp.length() - 1);
				}
				if (cfaLoc == 0) {
					qryString.append(" and dh.DSP_RECVG_LOC_ID in (").append(temp.toString()).append(")");

				} else {
					qryString.append(" and dh.DSP_RECVG_LOC_ID =").append(cfaLoc);
				}
			} else {
				qryString.append(" and dh.DSP_RECVG_LOC_ID = CASE WHEN ").append(cfaLoc)
						.append("=0 THEN dh.DSP_RECVG_LOC_ID ELSE ").append(cfaLoc).append(" END");
			}

			if (ROLE_COUR.equals(role)) {
				qryString.append(" and UPPER(dh.DSP_Transporter)  = '").append(username.toUpperCase() + "'");
			}
			qryString.append(
					" group by dh.DSP_FSTAFF_ID,dh.DSPFSTAFF_EMPLOYEENO,dh.DSPFSTAFF_NAME,dh.DSPFSTAFF_DISPLAYNAME")
					.append(" order by dh.DSPFSTAFF_NAME,dh.DSPFSTAFF_DISPLAYNAME");

			Query query = entityManager.createNativeQuery(qryString.toString(), Tuple.class);
			List<Tuple> tuples = query.getResultList();
			ReportBean bean = null;
			if (tuples != null && !tuples.isEmpty()) {
				for (Tuple t : tuples) {
					bean = new ReportBean();
					bean.setStaffId(Long.valueOf(t.get("DSP_FSTAFF_ID", Integer.class)));
//					bean.setStaffName(t.get("DSPFSTAFF_EMPLOYEENO",String.class)+"-"+t.get("DSPFSTAFF_NAME",String.class));
					bean.setStaffName(t.get("DSPFSTAFF_DISPLAYNAME", String.class));

					list.add(bean);
				}
			}
			System.out.println("Query:::::" + qryString.toString());

		} catch (Exception e) {
			throw e;
		}
		return list;
	}

	@Override
	public Sum_Disp_Header getObjectById(Long headerId) throws Exception {
		return this.entityManager.find(Sum_Disp_Header.class, headerId);
	}

	@Override
	public List<Sum_Disp_Detail> getSumDtlListByHdrId(Long sum_challan_no) throws Exception {
		List<Sum_Disp_Detail> list = null;
		try {
			StringBuffer sb = new StringBuffer();
			sb.append(
					"from Sum_Disp_Detail d where d.sumdspdtl_sumdsp_id=:sum_challan_no order by d.sumdspdtl_sumdsp_id");
			Query query = entityManager.createQuery(sb.toString(), Sum_Disp_Detail.class);
			query.setParameter("sum_challan_no", sum_challan_no);
			list = query.getResultList();
		} finally {
		}
		return list;
	}

	@Override
	public void updateGstVal(Sum_Disp_Detail details, int counter) throws Exception {
		StringBuffer buffer = new StringBuffer();
		buffer.append(" update Sum_Disp_Detail set SGST_RATE=:SGST_RATE,CGST_RATE=:CGST_RATE,IGST_RATE=:IGST_RATE, ");
		buffer.append(
				"SGST_BILL_AMT=:SGST_BILL_AMT,CGST_BILL_AMT=:CGST_BILL_AMT,IGST_BILL_AMT=:IGST_BILL_AMT,GST_DOC_TYPE=:GST_DOC_TYPE");
		buffer.append(" where sumdspdtl_id=:sumdspdtl_id ");

		Query query = entityManager.createNativeQuery(buffer.toString());
		query.setParameter("SGST_RATE", details.getSGST_RATE());
		query.setParameter("CGST_RATE", details.getCGST_RATE());
		query.setParameter("IGST_RATE", details.getIGST_RATE());
		query.setParameter("SGST_BILL_AMT", details.getSGST_BILL_AMT());
		query.setParameter("CGST_BILL_AMT", details.getCGST_BILL_AMT());
		query.setParameter("IGST_BILL_AMT", details.getIGST_BILL_AMT());
		query.setParameter("GST_DOC_TYPE", details.getGst_doc_type());
		query.setParameter("sumdspdtl_id", details.getSumdspdtl_id());
		query.executeUpdate();
		if (++counter % 20 == 0)
			entityManager.flush();

	}

	@Override
	public void updateSum_disp_hdrGstVal(BigDecimal sgst_sum, BigDecimal cgst_sum, BigDecimal igst_sum,
			String gst_doc_type, Long sum_challan_no, BigDecimal roundof_value) throws Exception {
		StringBuffer buffer = new StringBuffer();
		buffer.append(" update Sum_Disp_Header set ");
		buffer.append(
				" sgst_bill_amt=:sgst_sum, cgst_bill_amt=:cgst_sum, igst_bill_amt=:igst_sum, gst_doc_type=:gst_doc_type,roundoff=:roundoff");
		buffer.append(" where sumdsp_id=:sumdsp_id ");
		Query query = entityManager.createNativeQuery(buffer.toString());
		query.setParameter("sgst_sum", sgst_sum);
		query.setParameter("cgst_sum", cgst_sum);
		query.setParameter("igst_sum", igst_sum);
		query.setParameter("gst_doc_type", gst_doc_type);
		query.setParameter("roundoff", roundof_value);
		query.setParameter("sumdsp_id", sum_challan_no);
		query.executeUpdate();
		entityManager.flush();

	}

	@Override
	public List<Dispatch_Header> getApprStatusBySummChlnId(Long sum_challan_no) throws Exception {
		List<Dispatch_Header> list = null;
		try {
			StringBuffer sb = new StringBuffer();
			sb.append("from Dispatch_Header d where d.dsp_sum_dsp_id=:sum_challan_no ");
			Query query = entityManager.createQuery(sb.toString(), Dispatch_Header.class);
			query.setParameter("sum_challan_no", sum_challan_no);
			list = query.getResultList();
		} finally {
		}
		return list;
	}

	@Override
	public List<Dispatch_Header> getDspHeaderForDelete(String finYr, String currentPeriod, Long divId, String from_dt,
			String to_dt, Long locId) throws Exception {
		EntityManager em = null;
		List<Dispatch_Header> list = null;
		try {
			em = emf.createEntityManager();
			StringBuffer sb = new StringBuffer();
			sb.append(" select dsp_id,dsp_challan_no from dispatch_header DH where DH.DSP_FIN_YEAR ='" + finYr + "' ");
			sb.append(" AND DSP_DIV_ID =" + divId
					+ " AND DH.DSP_STATUS = 'A' AND DH.DSP_APPR_STATUS in('A','F') AND DH.DSP_LR_NO = '' AND DH.DSP_LOC_ID="
					+ locId + " ");
			sb.append(" and CONVERT(DATETIME,dsp_dt,103) >= convert(datetime,'" + from_dt + "',103)  ");
			sb.append(" and CONVERT(DATETIME,dsp_dt,103) <= convert(datetime,'" + to_dt + "',103) ");
			sb.append(" order by DSP_CHALLAN_NO");
			Query query = em.createNativeQuery(sb.toString(), Tuple.class);
			List<Tuple> tuples = query.getResultList();
			if (tuples != null && !tuples.isEmpty()) {
				list = new ArrayList<>();
				for (Tuple t : tuples) {
					Dispatch_Header obj = new Dispatch_Header();
					obj.setDsp_id(Long.valueOf(t.get("dsp_id", Integer.class)));
					obj.setDspChallanNo(t.get("dsp_challan_no", String.class));
					list.add(obj);
				}
			}
		} finally {
			if (em != null) {
				em.close();
			}
		}
		return list;
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public void submitdeleteChallan(DispatchBean bean) throws Exception {
		List<Object> list = null;
		try {
			StoredProcedureQuery query = entityManager.createStoredProcedureQuery("p_d_dispatch_with_alloc");

			query.registerStoredProcedureParameter("pcaction", String.class, ParameterMode.IN);
			query.registerStoredProcedureParameter("piDSP_ID", Long.class, ParameterMode.IN);
			query.registerStoredProcedureParameter("pvDSP_usr_id", String.class, ParameterMode.IN);
			query.registerStoredProcedureParameter("pvDSP_ip_add", String.class, ParameterMode.IN);
			query.registerStoredProcedureParameter("pcDSP_status", String.class, ParameterMode.IN);
		
			query.setParameter("pcaction", "D");
			query.setParameter("piDSP_ID", bean.getDsp_id());
			query.setParameter("pvDSP_usr_id", bean.getEmpId());
			query.setParameter("pvDSP_ip_add", bean.getIpAddress());
			query.setParameter("pcDSP_status", "A");
			list = query.getResultList();
		} finally {
		}
	}

	@Override
	public void uploadlrCsvModifydetailData(List<Sum_Disp_Header> datasum, List<Dispatch_Header> datadispt,
			DispatchBean bean) throws MedicoException {
		int i = 0;
		String msg = "";
		try {
			System.out.println("in uploadlrCsvData:");
			for (Sum_Disp_Header header : datasum) {
				msg = "Table:Sum_Disp_Header- Master Challon No.:" + header.getSumdsp_challan_no();
				i++;
				Sum_Disp_Header sum_Disp_Header = (Sum_Disp_Header) this.entityManager.find(Sum_Disp_Header.class,
						header.getSumdsp_id());
//			sum_Disp_Header.setSumdsp_lr_no(header.getSumdsp_lr_no());
//			sum_Disp_Header.setSumdsp_lr_dt(header.getSumdsp_lr_dt());
//			sum_Disp_Header.setSumdsp_transporter(header.getSumdsp_transporter());
//			sum_Disp_Header.setSumdsp_wt(header.getSumdsp_wt());
//			sum_Disp_Header.setSumdsp_totwt(header.getSumdsp_totwt());
//			sum_Disp_Header.setSumdsp_cases(header.getSumdsp_cases());
//			sum_Disp_Header.setSumdsp_totcases(header.getSumdsp_totcases());
//			sum_Disp_Header.setSumdsp_delivery_date(header.getSumdsp_delivery_date());
				sum_Disp_Header.setSumdsp_recd_by(header.getSumdsp_recd_by());
				sum_Disp_Header.setSumdsp_remark(header.getSumdsp_remark());
				sum_Disp_Header.setSumdsp_mod_ip_add(bean.getIpAddress());
				sum_Disp_Header.setSumdsp_mod_usr_id(bean.getEmpId());
//			sum_Disp_Header.setSumdsp_mod_dt(new Date());
				sum_Disp_Header.setSumdsp_mod_ip_add(header.getSumdsp_mod_ip_add());
				sum_Disp_Header.setCourier_expenses(header.getCourier_expenses());
				sum_Disp_Header.setActual_delivery_date(header.getActual_delivery_date());
//			sum_Disp_Header.setWay_bill_no(header.getWay_bill_no());
				sum_Disp_Header.setDelivery_detail_modify_date(new Date());
				if (bean.getCompanyCode().trim().equals(PFZ)) { // changes 18-02-2025
					sum_Disp_Header.setTransport_mode(header.getTransport_mode());
				}
				System.out.println("Sumdsp_challan_no:" + header.getSumdsp_challan_no());
				this.entityManager.merge(sum_Disp_Header);
				if (i % 20 == 0) {
					this.entityManager.flush();
					this.entityManager.clear();
				}
			}
			i = 0;
			for (Dispatch_Header dispheder : datadispt) {
				i++;
				msg = "Dispatch_header- Individual Challon No.:" + dispheder.getDspChallanNo();
				String qury = new String();
				
				StringBuffer sb = new StringBuffer();
				
				
				String lr_dt_qry = null;
				String lr_dt_dlv = null;
				String actual_dt = null;
				if (dispheder.getDsp_lr_dt() != null) {
					lr_dt_qry = "'" + dispheder.getDsp_lr_dt() + "'";
				}
				if (dispheder.getDsp_delivery_date() != null) {
					lr_dt_dlv = "'" + dispheder.getDsp_delivery_date() + "'";
				}
				System.out.println("Actual DAte :: " + dispheder.getActual_delivery_date());
				if (dispheder.getActual_delivery_date() != null) {
					actual_dt = "'" + dispheder.getActual_delivery_date() + "'";
				}
//			qury = "update Dispatch_Header set DSP_LR_NO='" + dispheder.getDsp_lr_no() + "', DSP_LR_DT=" + lr_dt_qry
//				+ " ,DSP_TRANSPORTER='" + dispheder.getDsp_transporter() + "',DSP_WT=" + dispheder.getDsp_wt()
//				+ " ,DSP_BILLABLE_WT=" + dispheder.getDsp_billable_wt() + " ,DSP_CASES="
//				+ dispheder.getDsp_cases() + " , DSP_DELIVERY_DATE=" + lr_dt_dlv + " , DSP_RECD_BY='"
//				+ dispheder.getDsp_recd_by() + "' , DSP_REMARK='" + dispheder.getDsp_remark() + "'"
//				+ " , WAY_BILL_NO='" + dispheder.getWay_bill_no() + "'" +" , DSP_MOD_DT=GETDATE()"+ " where DSP_SUM_DSP_ID="
//				+ dispheder.getDsp_sum_dsp_id() + " and DSP_CHALLAN_NO='" + dispheder.getDspChallanNo() + "'";

				
				sb.append("update Dispatch_Header set  ");
				
				
				
//				String str = "update Dispatch_Header set  ";
				
				if (bean.getCompanyCode().trim().equals(PFZ)) { // changes 18-02-2025
//					qury = str + ("transport_mode='" + dispheder.getTransport_mode() + "', ");
					sb.append("transport_mode='" + dispheder.getTransport_mode() + "', ");
				}
				
			
//				
//				qury =qury+ "  DSP_RECD_BY='" + dispheder.getDsp_recd_by() + "' , DSP_REMARK='"
//						+ dispheder.getDsp_remark() + "'," + " DELIVERY_DETAIL_MODIFY_DATE = GETDATE(),DSP_mod_ip_add='"
//						+ bean.getIpAddress() + "',DSP_mod_usr_id='" + bean.getEmpId() + "', ACTUAL_DELIVERY_DATE="
//						+ actual_dt + ",COURIER_EXPENSES='" + dispheder.getCourier_expenses() + "'," + " WAY_BILL_NO='"
//						+ dispheder.getWay_bill_no() + "' where DSP_SUM_DSP_ID='" + dispheder.getDsp_sum_dsp_id()
//						+ "' and DSP_CHALLAN_NO='" + dispheder.getDspChallanNo() + "' ";
//				
				
				sb.append("  DSP_RECD_BY='" + dispheder.getDsp_recd_by() + "' , DSP_REMARK='"
						+ dispheder.getDsp_remark() + "'," + " DELIVERY_DETAIL_MODIFY_DATE = GETDATE(),DSP_mod_ip_add='"
						+ bean.getIpAddress() + "',DSP_mod_usr_id='" + bean.getEmpId() + "', ACTUAL_DELIVERY_DATE="
						+ actual_dt + ",COURIER_EXPENSES='" + dispheder.getCourier_expenses() + "'," + " WAY_BILL_NO='"
						+ dispheder.getWay_bill_no() + "' where DSP_SUM_DSP_ID='" + dispheder.getDsp_sum_dsp_id()
						+ "' and DSP_CHALLAN_NO='" + dispheder.getDspChallanNo() + "' ");
				
				

				System.out.println("qury:" + sb.toString());
				
				
				Query qry = entityManager.createNativeQuery(sb.toString());
				qry.executeUpdate();
				if (i % 20 == 0) {
					this.entityManager.flush();
					this.entityManager.clear();
				}

				System.out.println("Counter :: " + i);

			}
		} catch (Exception e) {
			System.out.println("Error Occurred :" + e.getMessage());// uncomment asneeded --;
			throw new MedicoException("Error While Updating " + msg + " :" + e.getMessage());
		}
		return;
	}

	@Override
	public Dispatch_Header getDspHeader(Long sum_dsp_id, String challan_no) {
		List<Dispatch_Header> list = null;
		try {
			CriteriaBuilder builder = entityManager.getCriteriaBuilder();
			CriteriaQuery<Dispatch_Header> criteriaQuery = builder.createQuery(Dispatch_Header.class);
			Root<Dispatch_Header> root = criteriaQuery.from(Dispatch_Header.class);
			criteriaQuery.select(root).where(builder.equal(root.get(Dispatch_Header_.dsp_sum_dsp_id), sum_dsp_id),
					builder.equal(root.get(Dispatch_Header_.dspChallanNo), challan_no));
			list = entityManager.createQuery(criteriaQuery).getResultList();
			if (list != null && list.size() > 0)
				return list.get(0);
		} catch (Exception e) {
			System.out.println("Error Occurred :" + e.getMessage());// uncomment asneeded --;
		}
		return null;
	}

	@Override
	public Sum_Disp_Header_arc getObjectById_and_finyr(Long headerId, String finyr) throws Exception {
		// TODO Auto-generated method stub
		List<Sum_Disp_Header_arc> list = null;
		try {

			System.out.println("headerId :: " + headerId + "  finyr::" + finyr);

			CriteriaBuilder builder = entityManager.getCriteriaBuilder();
			CriteriaQuery<Sum_Disp_Header_arc> criteriaQuery = builder.createQuery(Sum_Disp_Header_arc.class);
			Root<Sum_Disp_Header_arc> root = criteriaQuery.from(Sum_Disp_Header_arc.class);
			criteriaQuery.select(root).where(builder.equal(root.get(Sum_Disp_Header_arc_.sumdsp_id), headerId),
					builder.equal(root.get(Sum_Disp_Header_arc_.sumdsp_fin_year), Integer.parseInt(finyr)));
			list = entityManager.createQuery(criteriaQuery).getResultList();
			if (list != null && list.size() > 0)
				return list.get(0);

		} catch (Exception e) {
			System.out.println("Error Occurred :" + e.getMessage());// uncomment asneeded --;
		}
		return null;
	}

	@Override
	public Dispatch_Header_arc getDspHeader_forarc(Long sum_dsp_id, String challan_no, String finyr) throws Exception {
		List<Dispatch_Header_arc> list = null;
		try {

			System.out.println("sum_dsp_id :: " + sum_dsp_id + "  challan_no::" + challan_no + "  finyr::" + finyr);

			CriteriaBuilder builder = entityManager.getCriteriaBuilder();
			CriteriaQuery<Dispatch_Header_arc> criteriaQuery = builder.createQuery(Dispatch_Header_arc.class);
			Root<Dispatch_Header_arc> root = criteriaQuery.from(Dispatch_Header_arc.class);
			criteriaQuery.select(root).where(builder.equal(root.get(Dispatch_Header_arc_.dsp_sum_dsp_id), sum_dsp_id),
					builder.equal(root.get(Dispatch_Header_arc_.dspChallanNo), challan_no),
					builder.equal(root.get(Dispatch_Header_arc_.dsp_fin_year), finyr));

			list = entityManager.createQuery(criteriaQuery).getResultList();
			if (list != null && list.size() > 0)
				return list.get(0);
		} catch (Exception e) {
			System.out.println("Error Occurred :" + e.getMessage());// uncomment asneeded --;
		}
		return null;
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public void uploadlrCsvData_arc(List<Sum_Disp_Header_arc> datasum, List<Dispatch_Header_arc> datadispt,
			String fin_year, DispatchBean bean) throws MedicoException {
		int i = 0;
		String msg = "";
		int counter = 0;
		try {
			System.out.println("in uploadlrCsvData____ARC:");

			for (Sum_Disp_Header_arc header : datasum) {
				msg = "Table:Sum_Disp_Header- Master Challon No.:" + header.getSumdsp_challan_no();
				i++;
				// getObjectById_and_finyr(Long.valueOf(bean.getSum_dsp_header_no()),bean.getFinYear());
				Sum_Disp_Header_arc sum_Disp_Header = getObjectById_and_finyr(header.getSumdsp_id(), fin_year);

				// if(sum_Disp_Header.getSumdsp_lr_no().equals("") &&
				// sum_Disp_Header.getSumdsp_lr_dt()==null &&sum_Disp_Header.getSumdsp_cases()
				// == 0 ) {
				sum_Disp_Header.setSumdsp_lr_no(header.getSumdsp_lr_no());
				sum_Disp_Header.setSumdsp_lr_dt(header.getSumdsp_lr_dt());
				sum_Disp_Header.setSumdsp_transporter(header.getSumdsp_transporter());
				sum_Disp_Header.setSumdsp_wt(header.getSumdsp_wt());
				sum_Disp_Header.setSumdsp_totwt(header.getSumdsp_totwt());
				sum_Disp_Header.setSumdsp_cases(header.getSumdsp_cases());
				sum_Disp_Header.setSumdsp_totcases(header.getSumdsp_totcases());
				sum_Disp_Header.setSumdsp_delivery_date(header.getSumdsp_delivery_date());
				sum_Disp_Header.setSumdsp_recd_by(header.getSumdsp_recd_by());
				sum_Disp_Header.setSumdsp_remark(header.getSumdsp_remark());
				sum_Disp_Header.setSumdsp_mod_dt(new Date());
				sum_Disp_Header.setSumdsp_mod_ip_add(bean.getIpAddress());
				sum_Disp_Header.setSumdsp_mod_usr_id(bean.getEmpId());

				sum_Disp_Header.setCourier_expenses(header.getCourier_expenses());
				sum_Disp_Header.setActual_delivery_date(header.getActual_delivery_date());
				sum_Disp_Header.setWay_bill_no(header.getWay_bill_no());
				if (bean.getCompanyCode().trim().equals(VET)) {
					sum_Disp_Header.setTrans_gst_reg_no(header.getTrans_gst_reg_no());
					sum_Disp_Header.setSumdsp_lorry_no(header.getSumdsp_lorry_no());
				}
				if (bean.getCompanyCode().trim().equals(PFZ)) { // changed by nilesh 15 Jan
					sum_Disp_Header.setTransport_mode(header.getTransport_mode());
				}
				this.entityManager.merge(sum_Disp_Header);
				counter++;
				// }
				System.out.println("Sumdsp_challan_no:" + header.getSumdsp_challan_no());
				if (i % 20 == 0) {
					this.entityManager.flush();
					this.entityManager.clear();
				}
			}

			System.out.println(" Sum_header counter :" + counter);
			counter = 0;
			i = 0;
			for (Dispatch_Header_arc dispheder : datadispt) {
				i++;
				msg = "Dispatch_header- Individual Challon No.:" + dispheder.getDspChallanNo();
				String qury = new String();
				String lr_dt_qry = null;
				String lr_dt_dlv = null;
				String actual_dt = null;
				if (dispheder.getDsp_lr_dt() != null) {
					lr_dt_qry = "'" + dispheder.getDsp_lr_dt() + "'";
				}
				if (dispheder.getDsp_delivery_date() != null) {
					lr_dt_dlv = "'" + dispheder.getDsp_delivery_date() + "'";
				}
				System.out.println("Actual DAte :: " + dispheder.getActual_delivery_date());
				if (dispheder.getActual_delivery_date() != null) {
					actual_dt = "'" + dispheder.getActual_delivery_date() + "'";
				}
//			qury = "update Dispatch_Header set DSP_LR_NO='" + dispheder.getDsp_lr_no() + "', DSP_LR_DT=" + lr_dt_qry
//				+ " ,DSP_TRANSPORTER='" + dispheder.getDsp_transporter() + "',DSP_WT=" + dispheder.getDsp_wt()
//				+ " ,DSP_BILLABLE_WT=" + dispheder.getDsp_billable_wt() + " ,DSP_CASES="
//				+ dispheder.getDsp_cases() + " , DSP_DELIVERY_DATE=" + lr_dt_dlv + " , DSP_RECD_BY='"
//				+ dispheder.getDsp_recd_by() + "' , DSP_REMARK='" + dispheder.getDsp_remark() + "'"
//				+ " , WAY_BILL_NO='" + dispheder.getWay_bill_no() + "'" +" , DSP_MOD_DT=GETDATE()"+ " where DSP_SUM_DSP_ID="
//				+ dispheder.getDsp_sum_dsp_id() + " and DSP_CHALLAN_NO='" + dispheder.getDspChallanNo() + "'";

				qury = "update Dispatch_Header_arc set DSP_LR_NO='" + dispheder.getDsp_lr_no() + "', DSP_LR_DT="
						+ lr_dt_qry + " ,DSP_TRANSPORTER='" + dispheder.getDsp_transporter() + "',DSP_WT="
						+ dispheder.getDsp_wt() + " ,DSP_BILLABLE_WT=" + dispheder.getDsp_billable_wt() + " ,DSP_CASES="
						+ dispheder.getDsp_cases() + " , DSP_DELIVERY_DATE=" + lr_dt_dlv + " , DSP_RECD_BY='"
						+ dispheder.getDsp_recd_by() + "' , DSP_REMARK='" + dispheder.getDsp_remark() + "'"
						+ " , WAY_BILL_NO='" + dispheder.getWay_bill_no() + "'"
						+ " , DSP_MOD_DT=GETDATE(),DSP_mod_ip_add='" + bean.getIpAddress() + "', DSP_mod_usr_id='"
						+ bean.getEmpId() + "',COURIER_EXPENSES ='" + dispheder.getCourier_expenses() + "',"
						+ " ACTUAL_DELIVERY_DATE=" + actual_dt + "";
				if (bean.getCompanyCode().trim().equals(VET)) {
					qury += (" ,DSP_TRANS_GST_REG_NO='" + dispheder.getDsp_trans_gst_reg_no() + "',DSP_LORRY_NO='"
							+ dispheder.getDsp_lorry_no() + "'");
				} else if (bean.getCompanyCode().trim().equals(PFZ)) { // changed by nilesh 15 Jan
					qury += (", transport_mode='" + dispheder.getTransport_mode() + "'");
				}
				qury += " where DSP_SUM_DSP_ID=" + dispheder.getDsp_sum_dsp_id() + " and DSP_CHALLAN_NO='"
						+ dispheder.getDspChallanNo() + "' and DSP_FIN_YEAR='" + fin_year + "'";// and Rtrim(DSP_LR_NO)
																								// ='' and DSP_LR_DT "+
				// "is null and DSP_CASES = 0 "; //change 24-8-2020 1:16pm

				System.out.println("qury:" + qury);
				Query qry = entityManager.createNativeQuery(qury);
				qry.executeUpdate();
				if (i % 20 == 0) {
					this.entityManager.flush();
					this.entityManager.clear();
				}

				counter++;
			}

			System.out.println(" Dispatch_Header counter :" + counter);

		} catch (Exception e) {
			System.out.println("Error Occurred :" + e.getMessage());// uncomment asneeded --;
			throw new MedicoException("Error While Updating " + msg + " :" + e.getMessage());
		}
		return;
	}

	@Override
	public void uploadlrCsvModifydetailData_arc(List<Sum_Disp_Header_arc> datasum, List<Dispatch_Header_arc> datadispt,
			String finyr, DispatchBean bean) throws MedicoException {
		int i = 0;
		String msg = "";
		try {
			System.out.println("in uploadlrCsvData:____ARC");
			for (Sum_Disp_Header_arc header : datasum) {
				msg = "Table:Sum_Disp_Header- Master Challon No.:" + header.getSumdsp_challan_no();
				i++;
				Sum_Disp_Header_arc sum_Disp_Header = getObjectById_and_finyr(header.getSumdsp_id(), finyr);
				// Sum_Disp_Header_arc sum_Disp_Header =
				// (Sum_Disp_Header)this.entityManager.find(Sum_Disp_Header.class,
				// header.getSumdsp_id());
//			sum_Disp_Header.setSumdsp_lr_no(header.getSumdsp_lr_no());
//			sum_Disp_Header.setSumdsp_lr_dt(header.getSumdsp_lr_dt());
//			sum_Disp_Header.setSumdsp_transporter(header.getSumdsp_transporter());
//			sum_Disp_Header.setSumdsp_wt(header.getSumdsp_wt());
//			sum_Disp_Header.setSumdsp_totwt(header.getSumdsp_totwt());
//			sum_Disp_Header.setSumdsp_cases(header.getSumdsp_cases());
//			sum_Disp_Header.setSumdsp_totcases(header.getSumdsp_totcases());
//			sum_Disp_Header.setSumdsp_delivery_date(header.getSumdsp_delivery_date());
				sum_Disp_Header.setSumdsp_recd_by(header.getSumdsp_recd_by());
				sum_Disp_Header.setSumdsp_remark(header.getSumdsp_remark());
//			sum_Disp_Header.setSumdsp_mod_dt(new Date());
				sum_Disp_Header.setSumdsp_mod_ip_add(header.getSumdsp_mod_ip_add());
				sum_Disp_Header.setSumdsp_mod_usr_id(bean.getEmpId());
				sum_Disp_Header.setCourier_expenses(header.getCourier_expenses());
				sum_Disp_Header.setActual_delivery_date(header.getActual_delivery_date());
//			sum_Disp_Header.setWay_bill_no(header.getWay_bill_no());
				sum_Disp_Header.setDelivery_detail_modify_date(new Date());
				
				if (bean.getCompanyCode().trim().equals(PFZ)) { // changes 18-02-2025
					sum_Disp_Header.setTransport_mode(header.getTransport_mode());
				}
				System.out.println("Sumdsp_challan_no:" + header.getSumdsp_challan_no());
				this.entityManager.merge(sum_Disp_Header);
				if (i % 20 == 0) {
					this.entityManager.flush();
					this.entityManager.clear();
				}
			}
			i = 0;
			for (Dispatch_Header_arc dispheder : datadispt) {
				i++;
				msg = "Dispatch_header- Individual Challon No.:" + dispheder.getDspChallanNo();
				String qury = new String();
				
				
				StringBuffer sb = new StringBuffer();
				
				String lr_dt_qry = null;
				String lr_dt_dlv = null;
				String actual_dt = null;
				if (dispheder.getDsp_lr_dt() != null) {
					lr_dt_qry = "'" + dispheder.getDsp_lr_dt() + "'";
				}
				if (dispheder.getDsp_delivery_date() != null) {
					lr_dt_dlv = "'" + dispheder.getDsp_delivery_date() + "'";
				}
				System.out.println("Actual DAte :: " + dispheder.getActual_delivery_date());
				if (dispheder.getActual_delivery_date() != null) {
					actual_dt = "'" + dispheder.getActual_delivery_date() + "'";
				}
//			qury = "update Dispatch_Header set DSP_LR_NO='" + dispheder.getDsp_lr_no() + "', DSP_LR_DT=" + lr_dt_qry
//				+ " ,DSP_TRANSPORTER='" + dispheder.getDsp_transporter() + "',DSP_WT=" + dispheder.getDsp_wt()
//				+ " ,DSP_BILLABLE_WT=" + dispheder.getDsp_billable_wt() + " ,DSP_CASES="
//				+ dispheder.getDsp_cases() + " , DSP_DELIVERY_DATE=" + lr_dt_dlv + " , DSP_RECD_BY='"
//				+ dispheder.getDsp_recd_by() + "' , DSP_REMARK='" + dispheder.getDsp_remark() + "'"
//				+ " , WAY_BILL_NO='" + dispheder.getWay_bill_no() + "'" +" , DSP_MOD_DT=GETDATE()"+ " where DSP_SUM_DSP_ID="
//				+ dispheder.getDsp_sum_dsp_id() + " and DSP_CHALLAN_NO='" + dispheder.getDspChallanNo() + "'";
				
				
//				String str = "update Dispatch_Header_arc set  ";
				
				sb.append("update Dispatch_Header_arc set  ");
				
				if (bean.getCompanyCode().trim().equals(PFZ)) { // changes 18-02-2025
//					qury = str + ("transport_mode='" + dispheder.getTransport_mode() + " ', ");					
					sb.append("transport_mode='" + dispheder.getTransport_mode() + " ', ");
				}

				
//				
//				qury += "  DSP_RECD_BY='" + dispheder.getDsp_recd_by() + "' , DSP_REMARK='"
//						+ dispheder.getDsp_remark() + "'," + " DELIVERY_DETAIL_MODIFY_DATE = GETDATE(),DSP_mod_ip_add='"
//						+ dispheder.getDsp_mod_ip_add() + "',DSP_mod_usr_id='" + bean.getEmpId()
//						+ "', ACTUAL_DELIVERY_DATE=" + actual_dt + ",COURIER_EXPENSES='"
//						+ dispheder.getCourier_expenses() + "'," + " WAY_BILL_NO='" + dispheder.getWay_bill_no()
//						+ "' where DSP_SUM_DSP_ID=" + dispheder.getDsp_sum_dsp_id() + " and DSP_CHALLAN_NO='"
//						+ dispheder.getDspChallanNo() + "' and DSP_FIN_YEAR='" + finyr + "'";
				
				
				sb.append("  DSP_RECD_BY='" + dispheder.getDsp_recd_by() + "' , DSP_REMARK='"
						+ dispheder.getDsp_remark() + "'," + " DELIVERY_DETAIL_MODIFY_DATE = GETDATE(),DSP_mod_ip_add='"
						+ dispheder.getDsp_mod_ip_add() + "',DSP_mod_usr_id='" + bean.getEmpId()
						+ "', ACTUAL_DELIVERY_DATE=" + actual_dt + ",COURIER_EXPENSES='"
						+ dispheder.getCourier_expenses() + "'," + " WAY_BILL_NO='" + dispheder.getWay_bill_no()
						+ "' where DSP_SUM_DSP_ID=" + dispheder.getDsp_sum_dsp_id() + " and DSP_CHALLAN_NO='"
						+ dispheder.getDspChallanNo() + "' and DSP_FIN_YEAR='" + finyr + "'");
				

				System.out.println("qury:" + sb.toString());
				Query qry = entityManager.createNativeQuery(sb.toString());
				qry.executeUpdate();
				if (i % 20 == 0) {
					this.entityManager.flush();
					this.entityManager.clear();
				}

				System.out.println("Counter :: " + i);

			}
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("Error Occurred :" + e.getMessage());// uncomment asneeded --;
			throw new MedicoException("Error While Updating " + msg + " :" + e.getMessage());
		}
		return;
	}

	@Override
	public Transporter_master getTransporterMaster(String transportername) throws Exception {
		// TODO Auto-generated method stub
		String transporter = null;
		try {
			List<Transporter_master> list = null;
			CriteriaBuilder builder = entityManager.getCriteriaBuilder();
			CriteriaQuery<Transporter_master> criteriaQuery = builder.createQuery(Transporter_master.class);
			Root<Transporter_master> root = criteriaQuery.from(Transporter_master.class);
			criteriaQuery.multiselect(root.get(Transporter_master_.transporter_name))
					.where(builder.equal(root.get(Transporter_master_.transporter_name), transportername));

			list = entityManager.createQuery(criteriaQuery).getResultList();
			if (list != null && list.size() > 0)
				return list.get(0);
		} catch (Exception e) {
			// TODO: handle exception
			throw e;
		}
		return null;
	}

	@Override
	public Dispatch_Header getdspHeaderById(Long dspId) throws Exception {
		// TODO Auto-generated method stub
		return this.entityManager.find(Dispatch_Header.class, dspId);
	}

	@Override
	public Sum_Disp_Detail sumdispatchDtlById(Long dtlId) throws Exception {
		// TODO Auto-generated method stub
		return this.entityManager.find(Sum_Disp_Detail.class, dtlId);
	}

	@Override
	public ArrayList<Object> getdispatch_details_EmailToMgr(String empId, Long divId, Long locId) throws Exception {
		// TODO Auto-generated method stub
		EntityManager em = null;
		ArrayList<Object> result = new ArrayList<Object>();
		try {
			em = emf.createEntityManager();
			Session session = em.unwrap(Session.class);
			session.doWork(new Work() {

				@Override
				public void execute(Connection conn) throws SQLException {
					String SPsql = "";
					System.out.println("dispatch_details_EMAIL_TO_MGR");
					SPsql = "EXEC dispatch_details_EMAIL_TO_MGR ?,?,?,?,?";
					PreparedStatement pstmt = conn.prepareStatement(SPsql);
					pstmt.setString(1, divId.toString());
					pstmt.setString(2, "D");
					pstmt.setString(3, locId.toString());
					pstmt.setString(4, "A");
					pstmt.setString(5, empId);
					// boolean results = pstmt.execute();
					// String columnName = "";
					ResultSet rs = pstmt.getResultSet();
					ResultSetMetaData rsMetaData = rs.getMetaData();
					int columnCount = rsMetaData.getColumnCount();
					while (rs.next()) {
						System.out.println("in Res");
						Object[] str = new Object[columnCount];
						for (int i = 1; i <= columnCount; ++i) {
							System.out.println("in Res obj loop : " + i);
							Object obj = rs.getObject(i);
							str[i - 1] = obj;
						}
						result.add(str);
					}
					System.out.println("dispatch_details_EMAIL_TO_MGR Fired");
				}
			});
		} catch (Exception e) {
			// TODO: handle exception
			System.out.println("Error Occurred :" + e.getMessage());// uncomment asneeded --;
			throw e;
		}
		return result;
	}

	@Override
	public Sum_Disp_Detail_Arc sumdispatchArcDtlBysumdspDtlId(Long dtlId, String finyrId) throws Exception {

		try {
			List<Sum_Disp_Detail_Arc> list = null;
			CriteriaBuilder builder = entityManager.getCriteriaBuilder();
			CriteriaQuery<Sum_Disp_Detail_Arc> criteriaQuery = builder.createQuery(Sum_Disp_Detail_Arc.class);
			Root<Sum_Disp_Detail_Arc> root = criteriaQuery.from(Sum_Disp_Detail_Arc.class);
			criteriaQuery.select(root);
			criteriaQuery.where(builder.and(builder.equal(root.get("sumdspdtl_id"), dtlId),
					builder.equal(root.get("sumdspdtl_fin_year"), finyrId)));
			list = entityManager.createQuery(criteriaQuery).getResultList();
			if (list != null && list.size() > 0)
				return list.get(0);
		} catch (Exception e) {
			// TODO: handle exception
			throw e;
		}
		return null;
	}

	@Override
	public List<GenerateDispatchData_AllocType> genAutoDispatchAllocTypeBulkUpload(String comp_cd, String fin_year,
			String period_cd, Long dsp_loc_id, Long smp_id, Long pi_loc_id, Long pi_abm, Long pi_rbm, Long pi_stateId,
			Long pi_msr, String pend_alloc, String action, String pvinsusr_id, Long pvinsip, Long pi_div_id,
			Long sample_rbm, Long sample_abm, String sample_flag, String challan_msg, Long chalan_periodId,
			String req_lvl, Long zone_id, String direct_to_pso_ind, boolean stock_at_cfa_ind, String allocType,
			String prodIds, String prodSubTypes, String subTeamCodeList, Long reqId)
			throws Exception, SQLException, PersistenceException {
		// TODO Auto-generated method stub
		List<GenerateDispatchData_AllocType> list = null;
		System.out.println("Param : " + comp_cd + ":" + fin_year + ":" + period_cd + ":" + dsp_loc_id + ":" + smp_id
				+ ":" + pi_loc_id + ":" + pi_abm + ":" + pi_rbm + ":" + pi_stateId + ":" + pi_msr + ":" + pend_alloc
				+ ":" + action + ":" + pvinsusr_id + ":" + pvinsip + ":" + pi_div_id + ":" + sample_rbm + ":"
				+ sample_abm + ":" + sample_flag + ":" + challan_msg + ":" + chalan_periodId + ":" + req_lvl + ":"
				+ zone_id + ":" + direct_to_pso_ind + ":" + allocType + ":" + prodIds + ":" + prodSubTypes + ":"
				+ subTeamCodeList);

		try {
			StoredProcedureQuery query = entityManager
					.createNamedStoredProcedureQuery("callGenerateDispatchDataAllocTypebulkDoc");
			query.setParameter("pcDSP_COMPANY", comp_cd);
			query.setParameter("pcDSP_FIN_YEAR", fin_year);
			query.setParameter("pcDSP_PERIOD_CODE", period_cd);
			query.setParameter("piDSP_LOC_ID", dsp_loc_id);
			query.setParameter("piDSP_SMP_ID", smp_id);
			query.setParameter("pirlocid", pi_loc_id);
			query.setParameter("piabm", pi_abm);
			query.setParameter("pirbm", pi_rbm);
			query.setParameter("pistateid", pi_stateId);
			query.setParameter("pimsr", pi_msr);
			query.setParameter("pcpendalloc", pend_alloc);
			query.setParameter("pcaction", action);
			query.setParameter("pvinsusr_id", pvinsusr_id);
			query.setParameter("pvinsip", pvinsip.toString());
			query.setParameter("pidiv_id", pi_div_id);
			query.setParameter("pisamplerbm", sample_rbm);
			query.setParameter("pisampleabm", sample_abm);
			query.setParameter("pcsampleflg", sample_flag);
			query.setParameter("pvchallan_msg", challan_msg);
			query.setParameter("pvchallan_PERIOD_ID", chalan_periodId);
			query.setParameter("pvDSP_APPR_REQ", req_lvl);
			query.setParameter("pirzonid", zone_id);
			query.setParameter("Pdirect_to_pso_ind", direct_to_pso_ind);
			query.setParameter("PALLOC_TYPE", allocType);
			query.setParameter("pcProd_id", prodIds);
			query.setParameter("pcProd_subtypid", prodSubTypes);
			query.setParameter("pcTeam_code", subTeamCodeList);
			query.setParameter("pi_BLK_HCP_REQ_ID", reqId);
			if (action.equalsIgnoreCase("G")) {
				list = query.getResultList();
				System.out.println("list : " + list.size());
			} else {
				query.execute();
			}

		} catch (Exception e) {
			// TODO: handle exception
			throw e;
		}
		return list;
	}

	@Override
	public Sum_Disp_Header getSumDispHeaderbyChallanNo(String challan_no) throws Exception {
		Sum_Disp_Header sum_dsip_header = null;
		try {
			CriteriaBuilder builder = entityManager.getCriteriaBuilder();
			CriteriaQuery<Sum_Disp_Header> criteriaQuery = builder.createQuery(Sum_Disp_Header.class);
			Root<Sum_Disp_Header> root = criteriaQuery.from(Sum_Disp_Header.class);
			criteriaQuery.select(root);
			criteriaQuery.where(builder.and(builder.equal(root.get("sumdsp_challan_no"), challan_no)));
			sum_dsip_header = entityManager.createQuery(criteriaQuery).getSingleResult();
		} catch (Exception e) {
			throw e;
		}
		return sum_dsip_header;
	}

	@Override
	public Sum_Disp_Header_arc sumdispatchArcHdrByChallanNo(String challan_no, String finyrId) throws Exception {
		Sum_Disp_Header_arc header_arc = null;
		try {
			CriteriaBuilder builder = entityManager.getCriteriaBuilder();
			CriteriaQuery<Sum_Disp_Header_arc> criteriaQuery = builder.createQuery(Sum_Disp_Header_arc.class);
			Root<Sum_Disp_Header_arc> root = criteriaQuery.from(Sum_Disp_Header_arc.class);
			criteriaQuery.select(root);
			criteriaQuery.where(builder.and(builder.equal(root.get("sumdsp_challan_no"), challan_no),
					builder.equal(root.get("sumdsp_fin_year"), finyrId)));
			header_arc = entityManager.createQuery(criteriaQuery).getSingleResult();
		} catch (Exception e) {
			// TODO: handle exception
			throw e;
		}
		return header_arc;
	}

	@Override
	public List<Long> getDispatchCyclesForBulkHCP(Long bulk_hcp_req_id, Long dspCycle,Long alloc_smp_id) throws Exception {
		List<Long> result = null;
		try {
			result = new ArrayList<Long>();
			CriteriaBuilder builder = entityManager.getCriteriaBuilder();
			CriteriaQuery<Long> criteriaQuery = builder.createQuery(Long.class);
			Root<Dispatch_Header> root = criteriaQuery.from(Dispatch_Header.class);
			criteriaQuery.select(root.get(Dispatch_Header_.dsp_cycle)).distinct(true);
			List<Order> orderList = new ArrayList();
			if (bulk_hcp_req_id != null && bulk_hcp_req_id.compareTo(0L) > 0) {
				criteriaQuery
						.where(builder.and(
								builder.equal(root.get(Dispatch_Header_.blk_hcp_req_id), bulk_hcp_req_id),
								builder.equal(root.get(Dispatch_Header_.dsp_smp_id), alloc_smp_id)));
			} else {
				criteriaQuery.where(builder.and(builder.equal(root.get(Dispatch_Header_.dsp_cycle), dspCycle),
						builder.equal(root.get(Dispatch_Header_.dsp_smp_id), alloc_smp_id)));
			}
			orderList.add(builder.asc(root.get(Dispatch_Header_.dsp_cycle)));
			result = entityManager.createQuery(criteriaQuery).getResultList();
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			throw e;
		}
		return result;
	}
	
	
	
	

	@Override
	public List<GenerateDispatchData_AllocType> genAutoDispatchAllocTypeBulkUploadStockist(String comp_cd,
			String fin_year, String period_cd, Long dsp_loc_id, Long smp_id, Long pi_loc_id, Long pi_abm, Long pi_rbm,
			Long pi_stateId, Long pi_msr, String pend_alloc, String action, String pvinsusr_id, Long pvinsip,
			Long pi_div_id, Long sample_rbm, Long sample_abm, String sample_flag, String challan_msg,
			Long chalan_periodId, String req_lvl, Long zone_id, String direct_to_pso_ind, boolean stock_at_cfa_ind,
			String allocType, String prodIds, String prodSubTypes, String subCodelist, Long reqId)
			throws Exception, SQLException, PersistenceException {
		// TODO Auto-generated method stub
		List<GenerateDispatchData_AllocType> list = null;
		System.out.println("Param : " + comp_cd + ":" + fin_year + ":" + period_cd + ":" + dsp_loc_id + ":" + smp_id
				+ ":" + pi_loc_id + ":" + pi_abm + ":" + pi_rbm + ":" + pi_stateId + ":" + pi_msr + ":" + pend_alloc
				+ ":" + action + ":" + pvinsusr_id + ":" + pvinsip + ":" + pi_div_id + ":" + sample_rbm + ":"
				+ sample_abm + ":" + sample_flag + ":" + challan_msg + ":" + chalan_periodId + ":" + req_lvl + ":"
				+ zone_id + ":" + direct_to_pso_ind + ":" + allocType + ":" + prodIds + ":" + prodSubTypes + ":"
				+ subCodelist);

		try {
			StoredProcedureQuery query = entityManager
					.createNamedStoredProcedureQuery("callGenerateDispatchDataAllocTypebulkStk");
			query.setParameter("pcDSP_COMPANY", comp_cd);
			query.setParameter("pcDSP_FIN_YEAR", fin_year);
			query.setParameter("pcDSP_PERIOD_CODE", period_cd);
			query.setParameter("piDSP_LOC_ID", dsp_loc_id);
			query.setParameter("piDSP_SMP_ID", smp_id);
			query.setParameter("pirlocid", pi_loc_id);
			query.setParameter("piabm", pi_abm);
			query.setParameter("pirbm", pi_rbm);
			query.setParameter("pistateid", pi_stateId);
			query.setParameter("pimsr", pi_msr);
			query.setParameter("pcpendalloc", pend_alloc);
			query.setParameter("pcaction", action);
			query.setParameter("pvinsusr_id", pvinsusr_id);
			query.setParameter("pvinsip", pvinsip.toString());
			query.setParameter("pidiv_id", pi_div_id);
			query.setParameter("pisamplerbm", sample_rbm);
			query.setParameter("pisampleabm", sample_abm);
			query.setParameter("pcsampleflg", sample_flag);
			query.setParameter("pvchallan_msg", challan_msg);
			query.setParameter("pvchallan_PERIOD_ID", chalan_periodId);
			query.setParameter("pvDSP_APPR_REQ", req_lvl);
			query.setParameter("pirzonid", zone_id);
			query.setParameter("Pdirect_to_pso_ind", direct_to_pso_ind);
			query.setParameter("PALLOC_TYPE", allocType);
			query.setParameter("pcProd_id", prodIds);
			query.setParameter("pcProd_subtypid", prodSubTypes);
			query.setParameter("pcTeam_code", subCodelist);
			query.setParameter("pi_BLK_HCP_REQ_ID", reqId);
			if (action.equalsIgnoreCase("G")) {
				list = query.getResultList();
				System.out.println("list : " + list.size());
			} else {
				query.execute();
			}

		} catch (Exception e) {
			// TODO: handle exception
			throw e;
		}
		return list;
	}

	@Override
	public List<GenerateDispatchData_AllocType> genAutoDispatchAllocTypeSingleStockists(String comp_cd, String fin_year,
			String period_cd, Long dsp_loc_id, Long smp_id, Long pi_loc_id, Long pi_abm, Long pi_rbm, Long pi_stateId,
			Long pi_msr, String pend_alloc, String action, String pvinsusr_id, Long pvinsip, Long pi_div_id,
			Long sample_rbm, Long sample_abm, String sample_flag, String challan_msg, Long chalan_periodId,
			String req_lvl, Long zone_id, String direct_to_pso_ind, boolean stock_at_cfa_ind, String allocType,
			String prodIds, String prodSubTypes, String subTeamCodeList, String reqId)
			throws Exception, SQLException, PersistenceException {
		// TODO Auto-generated method stub
		List<GenerateDispatchData_AllocType> list = null;
		System.out.println("Param : " + comp_cd + ":" + fin_year + ":" + period_cd + ":" + dsp_loc_id + ":" + smp_id
				+ ":" + pi_loc_id + ":" + pi_abm + ":" + pi_rbm + ":" + pi_stateId + ":" + pi_msr + ":" + pend_alloc
				+ ":" + action + ":" + pvinsusr_id + ":" + pvinsip + ":" + pi_div_id + ":" + sample_rbm + ":"
				+ sample_abm + ":" + sample_flag + ":" + challan_msg + ":" + chalan_periodId + ":" + req_lvl + ":"
				+ zone_id + ":" + direct_to_pso_ind + ":" + allocType + ":" + prodIds + ":" + prodSubTypes + ":"
				+ subTeamCodeList);

		try {
			StoredProcedureQuery query = entityManager
					.createNamedStoredProcedureQuery("callGenerateDispatchDataAllocTypeSingleStk");
			query.setParameter("pcDSP_COMPANY", comp_cd);
			query.setParameter("pcDSP_FIN_YEAR", fin_year);
			query.setParameter("pcDSP_PERIOD_CODE", period_cd);
			query.setParameter("piDSP_LOC_ID", dsp_loc_id);
			query.setParameter("piDSP_SMP_ID", smp_id);
			query.setParameter("pirlocid", pi_loc_id);
			query.setParameter("piabm", pi_abm);
			query.setParameter("pirbm", pi_rbm);
			query.setParameter("pistateid", pi_stateId);
			query.setParameter("pimsr", pi_msr);
			query.setParameter("pcpendalloc", pend_alloc);
			query.setParameter("pcaction", action);
			query.setParameter("pvinsusr_id", pvinsusr_id);
			query.setParameter("pvinsip", pvinsip.toString());
			query.setParameter("pidiv_id", pi_div_id);
			query.setParameter("pisamplerbm", sample_rbm);
			query.setParameter("pisampleabm", sample_abm);
			query.setParameter("pcsampleflg", sample_flag);
			query.setParameter("pvchallan_msg", challan_msg);
			query.setParameter("pvchallan_PERIOD_ID", chalan_periodId);
			query.setParameter("pvDSP_APPR_REQ", req_lvl);
			query.setParameter("pirzonid", zone_id);
			query.setParameter("Pdirect_to_pso_ind", direct_to_pso_ind);
			query.setParameter("PALLOC_TYPE", allocType);
			query.setParameter("pcProd_id", prodIds);
			query.setParameter("pcProd_subtypid", prodSubTypes);
			query.setParameter("pcTeam_code", subTeamCodeList);
			query.setParameter("pi_BLK_HCP_REQ_ID", reqId);
			if (action.equalsIgnoreCase("G")) {
				list = query.getResultList();
				System.out.println("list : " + list.size());
			} else {
				query.execute();
			}

		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			throw e;
		}
		return list;
	}

	@Override
	public Dispatch_Detail dispatchDtlById(Long dtlId) throws Exception {
		// TODO Auto-generated method stub
		return this.entityManager.find(Dispatch_Detail.class, dtlId);
	}

	@Override
	public Dispatch_Detail_Arc dispatchArcDtlBydspDtlId(Long dtlId, String finyrId) throws Exception {

		try {
			List<Dispatch_Detail_Arc> list = null;
			CriteriaBuilder builder = entityManager.getCriteriaBuilder();
			CriteriaQuery<Dispatch_Detail_Arc> criteriaQuery = builder.createQuery(Dispatch_Detail_Arc.class);
			Root<Dispatch_Detail_Arc> root = criteriaQuery.from(Dispatch_Detail_Arc.class);
			criteriaQuery.select(root);
			criteriaQuery.where(builder.and(builder.equal(root.get("dspdtlId"), dtlId),
					builder.equal(root.get("dspdtl_fin_year"), finyrId)));
			list = entityManager.createQuery(criteriaQuery).getResultList();
			if (list != null && list.size() > 0)
				return list.get(0);
		} catch (Exception e) {
			// TODO: handle exception
			throw e;
		}
		return null;
	}

	@Override
	public List<Dispatch_Header> getListOfSumDispForEInvoice(Long locid, String date) throws Exception {
		List<Dispatch_Header> returnList = new ArrayList<Dispatch_Header>();
		StringBuffer sb = new StringBuffer();
		sb.append("SELECT DISTINCT DSP_LOC_ID,DSP_CYCLE,DSP_SUM_DSP_ID,DSP_SUM_CHALLAN_NO,DSP_ID,DSP_CHALLAN_NO ");
		sb.append(" FROM DISPATCH_HEADER WHERE DSP_DT=:dspdt AND DSP_LOC_ID=:locid and dsp_appr_status = 'A'");
		sb.append(" ORDER BY  DSP_LOC_ID,DSP_CYCLE,DSP_SUM_CHALLAN_NO,DSP_CHALLAN_NO");

		Query query = this.entityManager.createNativeQuery(sb.toString(), Tuple.class);
		query.setParameter("locid", locid);
		query.setParameter("dspdt", date);
		Long olddspid = 0L;
		Long newdspid = 0L;
		List<Tuple> resultSet = query.getResultList();
		if (resultSet != null && resultSet.size() > 0) {
			for (Tuple tup : resultSet) {
				Dispatch_Header dh = new Dispatch_Header();
				newdspid = tup.get("DSP_SUM_DSP_ID", Integer.class).longValue();
				if (newdspid != olddspid) {
					dh.setBlank(false);
				}
				else {
					dh.setBlank(true);
				}
				dh.setDsp_cycle(tup.get("DSP_CYCLE", Integer.class).longValue());
				dh.setDsp_sum_dsp_id(tup.get("DSP_SUM_DSP_ID", Integer.class).longValue());
				dh.setDspSumChallanNo(tup.get("DSP_SUM_CHALLAN_NO", String.class));
				dh.setDsp_id(tup.get("DSP_ID", Integer.class).longValue());
				dh.setDspChallanNo(tup.get("DSP_CHALLAN_NO", String.class));
				olddspid = newdspid;
				returnList.add(dh);
			}
		}
		return returnList;
	}

}
