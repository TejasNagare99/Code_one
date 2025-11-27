package com.excel.repository;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.StoredProcedureQuery;
import javax.persistence.Tuple;

import org.apache.commons.collections4.map.HashedMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.excel.bean.AllocationBean;
import com.excel.bean.RuleBasedAllocationBean;
import com.excel.utility.CodifyErrors;
import com.excel.utility.MedicoConstants;
import com.excel.utility.Utility;

@Repository
public class RuleBasedAllocationApprovalRepositoryImpl implements  RuleBasedAllocationApprovalRepository {

	@Autowired EntityManagerFactory emf;
	@PersistenceContext private EntityManager entityManager;

	@Override
	public List<RuleBasedAllocationBean> approvalListForSelfAppr(String empId,String user_role,String locId) {
		List<RuleBasedAllocationBean> approval_LISTs = new ArrayList<RuleBasedAllocationBean>();
		System.out.println("locId "+locId);
		try {
			Query query =null;
			StringBuffer buffer = new StringBuffer(" select distinct dm.DIV_DISP_NM, p.period_alt_name, ld.ld_lgnid, CONVERT(varchar,t.ins_dt,103) UPLOAD_DATE, ");
			buffer.append(" dm.DIV_ID, p.PERIOD_CODE, t.fin_year, t.UPLOAD_STATUS,(case when t.UPLOAD_STATUS = 'M' then 'MONTHLY' ");
			buffer.append(" else 'ADDITIONAL' end) UPLOAD_STATUS_DESC, p.PERIOD_ID, t.UPLOAD_CYCLE, t.alloc_gen_hd_id, ");
			buffer.append(" NULLIF(para.SGprmdet_nm,'') input_type,	case when hd.disp_advice is not null then hd.disp_advice  ");
			buffer.append(" when xhd.disp_advice is not null then xhd.disp_advice else 'NA'	end disp_advice, case ");
			buffer.append(" when hd.alloc_doc_no is not null then hd.alloc_doc_no when xhd.alloc_doc_no is not null then xhd.alloc_doc_no ");
			buffer.append(" else 'NA' end alloc_doc_no,	th.alloc_temp_hd_id, th.input_type input_type_id, 'N' final, ");
			buffer.append(" th.APPROVAL_REMARK,  CONVERT(DATE,t.ins_dt ) ins_dt from alloc_temp_header th inner join alloc_temp t on ");
			buffer.append(" (th.alloc_temp_hd_id = t.alloc_temp_hd_id) inner join div_master dm on(dm.DIV_ID = t.DIV_ID) ");
			buffer.append(" inner join period p on(p.period_code = t.period_code AND P.PERIOD_FIN_YEAR = T.FIN_YEAR) ");
			buffer.append(" inner join am_m_login_detail ld on(ld.ld_emp_cb_id = t.ins_usr_id) inner join ");
			buffer.append(" sg_d_parameters_details para on(para.SGprmdet_id = th.input_type) LEFT JOIN alloc_gen_hd hd on");
			buffer.append(" (hd.alloc_gen_id = t.alloc_gen_hd_id) LEFT JOIN alloc_x_gen xhd ON ");
			buffer.append(" (xhd.ALLOC_X_GEN_ID = th.Alloc_XGEN_HD_ID) ");
		   if(MedicoConstants.ROLE_WAREH.equals(user_role)) {
				buffer.append("  where  p.period_status = 'A' and th.APPROVAL_STATUS = 'N' order by ins_dt, alloc_doc_no  ");
				query = entityManager.createNativeQuery(buffer.toString(), Tuple.class);
			}
			else if(MedicoConstants.ROLE_WARSU.equals(empId)){
				buffer.append("  where  th.ins_usr_id in(select hr.emp_id from hr_m_employee hr join hr_m_employee_group hrg on hr.emp_egrp_id = hrg.egrp_id where hr.emp_egrp_id = 'EG0011' and hr.emp_loc_id =:locId) and p.period_status = 'A' and th.APPROVAL_STATUS = 'F' order by ins_dt, alloc_doc_no  ");
				query = entityManager.createNativeQuery(buffer.toString(), Tuple.class);
				query.setParameter("locId", locId);
			}
			List<Tuple> tuples = query.getResultList();
			if (tuples != null && !tuples.isEmpty()) {
				for (Tuple t : tuples) {
					RuleBasedAllocationBean approval_List=new RuleBasedAllocationBean();
					approval_List.setDivision(t.get("DIV_DISP_NM", String.class));
					approval_List.setPeriod(t.get("period_alt_name", String.class));
					approval_List.setUploaded_by(t.get("ld_lgnid", String.class));
					approval_List.setUploaded_date(t.get("UPLOAD_DATE", String.class));
					approval_List.setDivision_id(Long.valueOf(t.get("DIV_ID", Integer.class)));
					approval_List.setPeriod_code(t.get("PERIOD_CODE", String.class));
					approval_List.setFin_year(t.get("fin_year", String.class));
					approval_List.setUpload_status(t.get("UPLOAD_STATUS", String.class));
					approval_List.setUpload_status_desc(t.get("UPLOAD_STATUS_DESC", String.class));
					approval_List.setPeriod_id(t.get("PERIOD_ID", Short.class));
					approval_List.setUpload_cycle(t.get("UPLOAD_CYCLE", Integer.class).toString());
					approval_List.setAlloc_gen_hd_id(Long.valueOf(t.get("alloc_gen_hd_id", BigInteger.class).toString()));
					approval_List.setInput_type(Utility.checkNull(t.get("input_type", String.class)));
					approval_List.setDisp_advice(Utility.checkNull(t.get("disp_advice", String.class)));
					approval_List.setAlloc_doc_no(Utility.checkNull(t.get("alloc_doc_no", String.class)));
					approval_List.setInput_type_id(t.get("input_type_id", Integer.class));
					approval_List.setIs_final(t.get("final", String.class));
					approval_List.setApp_remark(t.get("APPROVAL_REMARK", String.class));
					approval_List.setAlloc_temp_hd_id(Long.valueOf(t.get("alloc_temp_hd_id", BigInteger.class).toString()));
					approval_LISTs.add(approval_List);
				}
			}
		} catch (Exception e) {
			throw e;
		} finally {
			
		}
		return approval_LISTs;
	}
	
	@Override
	public List<RuleBasedAllocationBean> approvalListAllocConForSelfAppr(String empId,String user_role,String locId,String compcd) {
		List<RuleBasedAllocationBean> approval_LISTs = new ArrayList<RuleBasedAllocationBean>();
		System.out.println("locId "+locId);
		System.out.println("user_role "+user_role);
		try {
			Query query =null;
			StringBuffer buffer = new StringBuffer(" select distinct dm.DIV_DISP_NM, p.period_alt_name, ld.ld_lgnid, CONVERT(varchar,t.ins_dt,103) UPLOAD_DATE,   ");
			buffer.append(" dm.DIV_ID, p.PERIOD_CODE, t.fin_year, t.UPLOAD_STATUS,(case when t.UPLOAD_STATUS = 'M' then 'MONTHLY'  else 'ADDITIONAL' end) ");
			buffer.append(" UPLOAD_STATUS_DESC, p.PERIOD_ID, t.UPLOAD_CYCLE, t.alloc_gen_hd_id,  NULLIF(para.SGprmdet_nm,'') input_type, ");
			buffer.append(" case when hd.disp_advice is not null ");
			buffer.append(" then hd.disp_advice   when xhd.disp_advice is not null then xhd.disp_advice else 'NA' end disp_advice, ");
			buffer.append(" case  when hd.alloc_con_docno is not null ");
			buffer.append(" then hd.alloc_con_docno when xhd.alloc_doc_no is not null then xhd.alloc_doc_no  else 'NA' end alloc_doc, ");
			buffer.append(" t.csv_file_name alloc_doc_no,");
			buffer.append(" th.alloc_temp_hd_id, th.input_type input_type_id, ");
			buffer.append(" 'N' final,  th.APPROVAL_REMARK,  CONVERT(DATE,t.ins_dt ) ins_dt ");
			buffer.append(" from alloc_temp_header th inner join alloc_temp t on  (th.alloc_temp_hd_id = t.alloc_temp_hd_id) ");
			buffer.append(" inner join div_master dm on(dm.DIV_ID = t.DIV_ID)   ");
			buffer.append(" inner join period p on(p.period_code = t.period_code AND P.PERIOD_FIN_YEAR = T.FIN_YEAR) ");
			buffer.append(" inner join am_m_login_detail ld on(ld.ld_emp_cb_id = t.ins_usr_id) ");
			buffer.append(" inner join  sg_d_parameters_details para on(para.SGprmdet_id = th.input_type) ");
			buffer.append(" LEFT JOIN alloc_Con_hd hd on (hd.alloc_con_id = t.alloc_gen_hd_id) ");
			buffer.append(" LEFT JOIN alloc_x_gen xhd ON  (xhd.ALLOC_X_GEN_ID = th.Alloc_XGEN_HD_ID)   ");
			buffer.append(" INNER JOIN V_ALLOCATION_PERIOD VAP ON VAP.PERIOD_FIN_YEAR + VAP.PERIOD_CODE = TH.FIN_YEAR+TH.PERIOD_CODE ");
		
			if(compcd.trim().equals(MedicoConstants.PAL)) {
				buffer.append(" inner join am_m_login_detail ld_APPROVER on(ld_APPROVER.ld_emp_cb_id =:empId)");
				buffer.append(" INNER JOIN am_m_emp_div_access ADIV on adiv.ediv_emp_id = ld_APPROVER.ld_emp_cb_id and t.div_id in (adiv.ediv_div_id)");
			}
			else {
				buffer.append(" INNER JOIN am_m_emp_div_access ADIV on adiv.ediv_emp_id = ld.ld_emp_cb_id and t.div_id in (adiv.ediv_div_id)");
			}
			if(MedicoConstants.ROLE_WAREH.equals(user_role) || MedicoConstants.ROLE_MARKN.equals(user_role)) {
				if(compcd.trim().equals(MedicoConstants.PAL)) {
					buffer.append("  where  p.period_status = 'A' and th.APPROVAL_STATUS = 'N' and ld.ld_emp_cb_id=:empId order by ins_dt, alloc_doc_no  ");
				}
				else {
					buffer.append("  where  p.period_status = 'A' and th.APPROVAL_STATUS = 'N' order by ins_dt, alloc_doc_no  ");
				}
				query = entityManager.createNativeQuery(buffer.toString(), Tuple.class);
				if(compcd.trim().equals(MedicoConstants.PAL)) {
					query.setParameter("empId", empId);
				}
			}
			else if(MedicoConstants.ROLE_WARSU.equals(user_role) || MedicoConstants.ROLE_MARSU.equals(user_role)){
				if(compcd.trim().equals(MedicoConstants.PAL)) {
					
					buffer.append("  where  th.ins_usr_id in(select hr.emp_id from hr_m_employee hr join hr_m_employee_group hrg on hr.emp_egrp_id = hrg.egrp_id where hr.emp_egrp_id = 'EG0012' and hr.emp_loc_id =:locId) and p.period_status = 'A' and th.APPROVAL_STATUS = 'F'  order by ins_dt, alloc_doc_no  ");
				}
				else {
					buffer.append("  where  th.ins_usr_id in(select hr.emp_id from hr_m_employee hr join hr_m_employee_group hrg on hr.emp_egrp_id = hrg.egrp_id where hr.emp_egrp_id = 'EG0011' and hr.emp_loc_id =:locId ) and p.period_status = 'A' and th.APPROVAL_STATUS = 'F'  order by ins_dt, alloc_doc_no  ");	
				}
				query = entityManager.createNativeQuery(buffer.toString(), Tuple.class);
				query.setParameter("locId", locId);
				if(compcd.trim().equals(MedicoConstants.PAL)) {
					query.setParameter("empId", empId);
				}
			}
			else {
				query = entityManager.createNativeQuery(buffer.toString(), Tuple.class);
			}
			
			System.out.println("Query : "+buffer.toString());
			List<Tuple> tuples = query.getResultList();
			if (tuples != null && !tuples.isEmpty()) {
				for (Tuple t : tuples) {
					RuleBasedAllocationBean approval_List=new RuleBasedAllocationBean();
					approval_List.setDivision(t.get("DIV_DISP_NM", String.class));
					approval_List.setPeriod(t.get("period_alt_name", String.class));
					approval_List.setUploaded_by(t.get("ld_lgnid", String.class));
					approval_List.setUploaded_date(t.get("UPLOAD_DATE", String.class));
					approval_List.setDivision_id(Long.valueOf(t.get("DIV_ID", Integer.class)));
					approval_List.setPeriod_code(t.get("PERIOD_CODE", String.class));
					approval_List.setFin_year(t.get("fin_year", String.class));
					approval_List.setUpload_status(t.get("UPLOAD_STATUS", String.class));
					approval_List.setUpload_status_desc(t.get("UPLOAD_STATUS_DESC", String.class));
					approval_List.setPeriod_id(t.get("PERIOD_ID", Short.class));
					approval_List.setUpload_cycle(t.get("UPLOAD_CYCLE", Integer.class).toString());
					approval_List.setAlloc_gen_hd_id(Long.valueOf(t.get("alloc_gen_hd_id", BigInteger.class).toString()));
					approval_List.setInput_type(Utility.checkNull(t.get("input_type", String.class)));
					approval_List.setDisp_advice(Utility.checkNull(t.get("disp_advice", String.class)));
					approval_List.setAlloc_doc_no(Utility.checkNull(t.get("alloc_doc_no", String.class).split("CSV")[0]));
					approval_List.setInput_type_id(t.get("input_type_id", Integer.class));
					approval_List.setIs_final(t.get("final", String.class));
					approval_List.setApp_remark(t.get("APPROVAL_REMARK", String.class));
					approval_List.setAlloc_temp_hd_id(Long.valueOf(t.get("alloc_temp_hd_id", BigInteger.class).toString()));
					approval_LISTs.add(approval_List);
				}
			}
		} catch (Exception e) {
			throw e;
		} finally {
			
		}
		return approval_LISTs;
	}
	
	@Override
	public List<RuleBasedAllocationBean> approvalDetails(long division,long period,String up_status,
			String ucycle,Long allocTempId) throws Exception {
		List<RuleBasedAllocationBean> approval_LISTs = new ArrayList<RuleBasedAllocationBean>();
		try {
			StringBuffer buffer = new StringBuffer(
					" SELECT DM.DIV_DISP_NM as DIV_NAME,T.FSCODE,T.FSNAME,SIT.SMP_PROD_CD,SIT.SMP_PROD_NAME,T.FINAL_ALLOCQTY,");
			buffer.append(" T.ALLOC_RATE,T.ALLOC_QTY,T.MSR_STOCK,S_DM.DIV_DISP_NM as PRODUCT_DIV_NAME, ");
			buffer.append(" T.ALLOC_CYCLE, SIT.SMP_MIN_ALLOC_QTY,th.APPROVAL_REMARK FROM ALLOC_TEMP_HEADER TH INNER JOIN ALLOC_TEMP T on(th.ALLOC_TEMP_HD_ID=t.ALLOC_TEMP_HD_ID) ");
			buffer.append(" INNER JOIN DIV_MASTER DM ");
			buffer.append(" ON (T.DIV_ID=DM.DIV_ID) ");
			buffer.append(" INNER JOIN PERIOD P ");
			buffer.append(" ON (P.PERIOD_CODE=T.PERIOD_CODE) ");
			buffer.append(" INNER JOIN AM_M_LOGIN_DETAIL LD ");
			buffer.append(" ON (LD.LD_EMP_CB_ID=T.INS_USR_ID) ");
			buffer.append(" INNER JOIN DIV_MASTER S_DM ");
			buffer.append(" ON (S_DM.DIV_ID=T.ALT_DIV_ID)");
			buffer.append(" INNER JOIN SMPITEM SIT ");
			buffer.append(" on(SIT.SMP_PROD_ID=T.PROD_ID)");
			buffer.append(" where DM.DIV_ID=:division");
			buffer.append(" AND T.UPLOAD_STATUS=:up_status");
			buffer.append(" AND T.UPLOAD_CYCLE=:ucycle");
			buffer.append(" AND P.PERIOD_ID=:period ");
			buffer.append(" AND TH.APPROVAL_STATUS<>'D'");
			buffer.append(" AND T.alloc_temp_hd_id =:allocTempId");
			buffer.append(" order by T.ALLOC_TEMP_ID");
			Query query = entityManager.createNativeQuery(buffer.toString(), Tuple.class);
			query.setParameter("division", division);
			query.setParameter("up_status", up_status);
			query.setParameter("ucycle", ucycle);
			query.setParameter("period", period);
			query.setParameter("allocTempId", allocTempId);
			
			List<Tuple> tuples = query.getResultList();
			if (tuples != null && !tuples.isEmpty()) {
				for (Tuple t : tuples) {
				RuleBasedAllocationBean approval_List=new RuleBasedAllocationBean();
				approval_List.setDivision(t.get("DIV_NAME", String.class));
				approval_List.setFs_code(t.get("FSCODE", String.class));
				approval_List.setFs_name(t.get("FSNAME", String.class));
				approval_List.setProduct_code(t.get("SMP_PROD_CD", String.class));
				approval_List.setProduct_name(t.get("SMP_PROD_NAME", String.class));
				approval_List.setFinal_alloc_qty(t.get("FINAL_ALLOCQTY", BigDecimal.class).setScale(0).toString());
				approval_List.setRate(t.get("ALLOC_RATE", BigDecimal.class).toString());
				approval_List.setQty(t.get("ALLOC_QTY", BigDecimal.class).setScale(0).toString());
				approval_List.setMsrstock(t.get("MSR_STOCK", BigDecimal.class).setScale(0).toString());
				approval_List.setProddivision(t.get("PRODUCT_DIV_NAME", String.class));
				approval_List.setCycle(t.get("ALLOC_CYCLE", Integer.class).toString());
				approval_List.setMinallocqty(t.get("SMP_MIN_ALLOC_QTY", BigDecimal.class).setScale(0).toString());
				approval_List.setApp_remark(t.get("APPROVAL_REMARK", String.class));
				approval_LISTs.add(approval_List);
			}
		 }
		} catch (Exception e) {
			throw e;
		} finally {
		}
		return approval_LISTs;
	}
	
	@Override
	public Map<String,Object> getProductWiseStock(long division,long period,String up_status,String ucycle,Long alloctempHdId)throws Exception{
		List<RuleBasedAllocationBean> list = new ArrayList<RuleBasedAllocationBean>();
		Map<String,Object> map=new HashedMap<>();
		boolean allowApproval=true;
		try {
			StringBuffer buffer = 
					new StringBuffer("SELECT SIT.SMP_PROD_CD,");
					buffer.append(" SIT.SMP_PROD_NAME,");
					buffer.append(" SUM(coalesce(T.FINAL_ALLOCQTY,0)) final,");
					buffer.append(" coalesce(stock,0) stock");
					buffer.append(" FROM ALLOC_TEMP_HEADER TH INNER JOIN ALLOC_TEMP T on(th.ALLOC_TEMP_HD_ID=t.ALLOC_TEMP_HD_ID) ");
					buffer.append(" INNER JOIN DIV_MASTER DM ");
					buffer.append(" ON (T.DIV_ID=DM.DIV_ID) ");
					buffer.append(" INNER JOIN PERIOD P ");
					buffer.append(" ON (P.PERIOD_CODE=T.PERIOD_CODE) ");
					buffer.append(" INNER JOIN AM_M_LOGIN_DETAIL LD ");
					buffer.append(" ON (LD.LD_EMP_CB_ID=T.INS_USR_ID) ");
					buffer.append(" INNER JOIN DIV_MASTER S_DM ");
					buffer.append(" ON (S_DM.DIV_ID=T.ALT_DIV_ID)");
					buffer.append(" INNER JOIN SMPITEM SIT ");
					buffer.append(" on(SIT.SMP_PROD_ID=T.PROD_ID)");
					buffer.append(" LEFT OUTER JOIN ");
					buffer.append(" (select sb.batch_prod_id,SUM( sb.BATCH_OPEN_STOCK+sb.BATCH_IN_STOCK-sb.BATCH_OUT_STOCK-sb.BATCH_WITH_HELD_QTY ) stock");
					buffer.append(" from SMPBatch sb  group by sb.batch_prod_id ) sbb ");
					buffer.append(" ON SIT.SMP_PROD_ID=sbb.batch_prod_id ");
					buffer.append(" where DM.DIV_ID=:division");
					buffer.append(" AND T.UPLOAD_STATUS=:up_status");
					buffer.append(" AND T.UPLOAD_CYCLE=:ucycle");
					buffer.append(" AND P.PERIOD_ID=:period ");
					buffer.append(" AND T.alloc_temp_hd_id =:allochdId");
					buffer.append(" group by SIT.SMP_PROD_CD,SIT.SMP_PROD_NAME,stock ORDER BY SIT.SMP_PROD_NAME");
					
					
			System.out.println("Division "+division);
			System.out.println("up_status "+up_status);
			System.out.println("ucycle "+ucycle);
			System.out.println("period "+period);
			System.out.println("allochdId "+alloctempHdId);
			System.out.println("SUPRIYA==================="+buffer.toString());
			Query query = entityManager.createNativeQuery(buffer.toString(), Tuple.class);
			query.setParameter("division", division);
			query.setParameter("up_status", up_status);
			query.setParameter("ucycle", ucycle);
			query.setParameter("period", period);
			query.setParameter("allochdId", alloctempHdId);
			List<Tuple> tuples = query.getResultList();
			BigDecimal total_product_qty=new BigDecimal(0);
			if (tuples != null && !tuples.isEmpty()) {
				for (Tuple t : tuples) {
					
					RuleBasedAllocationBean stock=new RuleBasedAllocationBean();
					stock.setProduct_code(t.get("SMP_PROD_CD", String.class));
					stock.setProduct_name(t.get("SMP_PROD_NAME", String.class));
					stock.setQty(t.get("FINAL", BigDecimal.class).setScale(0).toString());
					total_product_qty=total_product_qty.add(t.get("FINAL", BigDecimal.class));
					stock.setStock(t.get("stock", BigDecimal.class).setScale(0).toString());
					stock.setBalanceStock(t.get("stock", BigDecimal.class).subtract(t.get("FINAL", BigDecimal.class)).setScale(0).toString());
					if(t.get("FINAL", BigDecimal.class).compareTo(t.get("stock", BigDecimal.class)) == 1) {
						 allowApproval=false;
					}
					
					
				
					
					
				list.add(stock);
			}
		  }
			map.put("productWiseDetail", list);
			map.put("total_product_qty", total_product_qty);
			map.put("allowApproval", allowApproval);
		  
		} catch (Exception e) {
			throw e;
		} finally {
		}
		return map;
	}
	
	@Override
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public void ApproveMonthlyAllocation(Long allocTempId,String userid,String user_ip) throws Exception {
		try {
			System.out.println("ALCTMP_HDRID"+allocTempId);
			System.out.println("userid"+userid);
			System.out.println("user_ip"+user_ip);
			
			StoredProcedureQuery callDisptachSumDetReport  = entityManager.createNamedStoredProcedureQuery("callAllocationApproval");
			callDisptachSumDetReport.setParameter("ALCTMP_HDRID",allocTempId);
			callDisptachSumDetReport.setParameter("userid",userid);
			callDisptachSumDetReport.setParameter("user_ip",user_ip);
			callDisptachSumDetReport.executeUpdate();
		}
		catch (Exception e) {
			System.out.println("Error Occurred :" +e.getMessage());// uncomment asneeded --;
		}
	}
	
}
