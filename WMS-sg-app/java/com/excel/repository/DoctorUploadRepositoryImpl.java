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
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

import org.apache.commons.collections4.map.HashedMap;
import org.apache.xmlbeans.impl.soap.Detail;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.excel.bean.RuleBasedAllocationBean;
import com.excel.model.Pg_Doc_Alloc_Template;
import com.excel.model.Pg_Doc_Alloc_Template_;
import com.excel.model.Pg_Doc_Alloc_Template_Error;
import com.excel.model.Pg_Doc_Alloc_Template_Error_;
import com.excel.utility.CodifyErrors;
import com.excel.utility.MedicoConstants;
import com.excel.utility.Utility;

@Repository
public class DoctorUploadRepositoryImpl implements DoctorUploadRepository {
	@Autowired
	EntityManagerFactory emf;
	@PersistenceContext
	private EntityManager entityManager;

	@Override
	public void generateAllocationPg(String comp_cd, String finyr, String period_cd, String alloc_uniq_no,
			String userid, String userip) throws Exception {
		EntityManager em = null;
		try {
			System.out.println("comp_cd " + comp_cd);
			System.out.println("finyr " + finyr);
			System.out.println("period_cd " + period_cd);
			System.out.println("alloc_uniq_no " + alloc_uniq_no);
			System.out.println("userid " + userid);
			System.out.println("user_ip " + userip);

			em = emf.createEntityManager();
			StoredProcedureQuery query = em.createNamedStoredProcedureQuery("callPgAllocationGenerate");
			query.setParameter("comp_cd", comp_cd);
			query.setParameter("finyr", finyr);
			query.setParameter("period_cd", period_cd);
			query.setParameter("alloc_uniq_no", alloc_uniq_no);
			query.setParameter("userid", userid);
			query.setParameter("user_ip", userip);
			query.execute();
		} finally {
			if (em != null) {
				em.close();
			}
		}
	}

	@Override
	public Long getAllocationCycleNumberPg(String period_code, String fin_year) throws Exception {
		EntityManager em = null;
		Long count = null;
		try {

			StringBuffer sb = new StringBuffer();
			sb.append(" select ISNULL(max(a.ALLOC_CYCLE),0) from PG_DOC_ALLOC_TEMPLATE a where  ");
			sb.append(" FIN_YEAR=:year  and  PERIOD_CODE=:month ");
			Query query = entityManager.createNativeQuery(sb.toString());
			query.setParameter("year", fin_year);
			query.setParameter("month", period_code);
			count = Long.valueOf(query.getSingleResult().toString());

			System.out.println("Count " + count);

		} catch (Exception e) {
			System.out.println("Error Occurred :" + e.getMessage());// uncomment asneeded --;
		} finally {
			if (em != null) {
				em.close();
			}
		}
		return count + 1;
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public void deleteDoctorUploadByUniqueNumber(Long alloc_uniq_no) throws Exception {

		EntityManager em = null;
		try {
			StringBuffer buffer = new StringBuffer();
			buffer.append(" DELETE from  PG_DOC_ALLOC_TEMPLATE  where alloc_uniq_no='" + alloc_uniq_no + "'");
			Query query = entityManager.createNativeQuery(buffer.toString());
			query.executeUpdate();

		} catch (Exception e) {
			System.out.println("Error Occurred :" + e.getMessage());// uncomment asneeded --;
			throw e;
		} finally {
			if (em != null) {
				em.close();
			}
		}
	}

	@Override
	public List<RuleBasedAllocationBean> approvalLisForSelfAppr(String empId, String user_role, String locId) {
		List<RuleBasedAllocationBean> approval_LISTs = new ArrayList<RuleBasedAllocationBean>();
		System.out.println("locId " + locId);
		try {
			Query query = null;
			StringBuffer buffer = new StringBuffer(
					" SELECT DISTINCT dm.div_disp_nm,p.period_alt_name,ld.ld_lgnid,CONVERT(VARCHAR, t.allocdtl_ins_dt, 103) UPLOAD_DATE, ");
			buffer.append(" dm.div_id,p.period_code,t.allocdtl_fin_year AS fin_year,'' upload_status, ");
			buffer.append(
					" 'DOCTOR ALLOCATION' UPLOAD_STATUS_DESC,p.period_id,''  upload_cycle,NULLIF(para.sgprmdet_nm, '')    input_type, ");
			buffer.append(" '' AS  disp_advice,TH.ALLOC_UNIQ_NO AS alloc_doc_no, th.alloc_type input_type_id, ");
			buffer.append(" 'N' final,'' approval_remark ");
			buffer.append(
					" FROM   alloc_header th INNER JOIN ALLOC_DETAIL t ON ( th.alloc_id = t.allocdtl_alloc_id ) ");
			buffer.append("  INNER JOIN div_master dm ON( dm.div_id = t.allocdtl_div_id ) ");
			buffer.append(
					" INNER JOIN period p ON( p.period_code = t.allocdtl_period_code AND P.period_fin_year = T.allocdtl_fin_year ) ");
			buffer.append(" INNER JOIN am_m_login_detail ld ON( ld.ld_emp_cb_id = t.allocdtl_ins_usr_id ) ");
			buffer.append(" INNER JOIN sg_d_parameters_details para ON( para.sgprmdet_id = 288 )   ");

			if (MedicoConstants.ROLE_WAREH.equals(user_role)) {
				buffer.append(" WHERE  p.period_status = 'A' AND th.alloc_appr_status = 'E' ORDER  BY   alloc_doc_no ");
				query = entityManager.createNativeQuery(buffer.toString(), Tuple.class);
			} else if (MedicoConstants.ROLE_WARSU.equals(user_role)) {
				buffer.append(
						"  where   p.period_status = 'A' and th.alloc_appr_status = 'F' order by  alloc_doc_no  ");
				query = entityManager.createNativeQuery(buffer.toString(), Tuple.class);
				// query.setParameter("locId", locId);
			}
			List<Tuple> tuples = query.getResultList();
			if (tuples != null && !tuples.isEmpty()) {
				for (Tuple t : tuples) {
					RuleBasedAllocationBean approval_List = new RuleBasedAllocationBean();
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
					approval_List.setUpload_cycle("0");
					// approval_List.setAlloc_gen_hd_id(Long.valueOf(t.get("alloc_gen_hd_id",
					// BigInteger.class).toString()));
					approval_List.setInput_type(Utility.checkNull(t.get("input_type", String.class)));
					approval_List.setDisp_advice(Utility.checkNull(t.get("disp_advice", String.class)));
					approval_List.setAlloc_doc_no(Utility.checkNull(t.get("alloc_doc_no", String.class)));
					// approval_List.setInput_type_id(t.get("input_type_id", Integer.class));
					// approval_List.setIs_final(t.get("final", String.class));
					approval_List.setApp_remark(t.get("APPROVAL_REMARK", String.class));
					approval_List.setAlloc_temp_hd_id(Long.valueOf(t.get("alloc_doc_no", String.class)));
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
	public Map<String, Object> getProductWiseStock(long division, long period, String up_status, String ucycle,
			Long unique_no) throws Exception {
		List<RuleBasedAllocationBean> list = new ArrayList<RuleBasedAllocationBean>();
		Map<String, Object> map = new HashedMap<>();
		boolean allowApproval = true;
		try {
			StringBuffer buffer = new StringBuffer(
					" SELECT SIT.smp_prod_cd,sit.SMP_PROD_NAME,Sum(COALESCE(T.allocdtl_curr_alloc_qty-t.allocdtl_supply_qty, 0)) final, ");
			buffer.append(" COALESCE(stock, 0) stock FROM   alloc_header TH ");
			buffer.append(" INNER JOIN alloc_detail T ON( th.alloc_id = t.allocdtl_alloc_id ) ");
			buffer.append(" INNER JOIN div_master DM ON ( T.allocdtl_div_id = DM.div_id ) ");
			buffer.append(" INNER JOIN period P ON ( P.period_code = T.allocdtl_period_code ) ");
			buffer.append(" INNER JOIN am_m_login_detail LD ON ( LD.ld_emp_cb_id = T.allocdtl_ins_usr_id ) ");
			buffer.append(" INNER JOIN div_master S_DM ON ( S_DM.div_id = T.allocdtl_alt_div_id ) ");
			buffer.append(" INNER JOIN smpitem SIT ON( SIT.smp_prod_id = T.allocdtl_prod_id ) ");
			buffer.append(
					" LEFT OUTER JOIN (SELECT sb.batch_prod_id,Sum(sb.batch_open_stock + sb.batch_in_stock - sb.batch_out_stock - sb.batch_with_held_qty) stock ");
			buffer.append(
					" FROM   smpbatch sb GROUP  BY sb.batch_prod_id) sbb   ON SIT.smp_prod_id = sbb.batch_prod_id ");
			buffer.append(" WHERE th.ALLOC_UNIQ_NO= :unique_no AND P.period_id =:period ");
			buffer.append(" GROUP  BY SIT.smp_prod_cd, SIT.smp_prod_name,stock ");
			buffer.append(" ORDER  BY SIT.smp_prod_name  ");
			System.out.println("Division " + division);
			System.out.println("up_status " + up_status);
			System.out.println("ucycle " + ucycle);
			System.out.println("period " + period);
			System.out.println("unique_no " + unique_no);
			System.out.println("==Doctor Uplaod=" + buffer.toString());
			Query query = entityManager.createNativeQuery(buffer.toString(), Tuple.class);
//			query.setParameter("division", division);
//			query.setParameter("up_status", up_status);
//			query.setParameter("ucycle", ucycle);
			query.setParameter("period", period);
			query.setParameter("unique_no", unique_no);
			List<Tuple> tuples = query.getResultList();
			if (tuples != null && !tuples.isEmpty()) {
				for (Tuple t : tuples) {
					RuleBasedAllocationBean stock = new RuleBasedAllocationBean();
					stock.setProduct_code(t.get("SMP_PROD_CD", String.class));
					stock.setProduct_name(t.get("SMP_PROD_NAME", String.class));
					stock.setQty(t.get("FINAL", BigDecimal.class).setScale(0).toString());
					stock.setStock(t.get("stock", BigDecimal.class).setScale(0).toString());
					stock.setBalanceStock(t.get("stock", BigDecimal.class).subtract(t.get("FINAL", BigDecimal.class))
							.setScale(0).toString());
					if (t.get("FINAL", BigDecimal.class).compareTo(t.get("stock", BigDecimal.class)) == 1) {
						allowApproval = false;
					}

					list.add(stock);
				}
			}
			map.put("productWiseDetail", list);
			map.put("allowApproval", allowApproval);
			System.out.println("Detail " + list.size());

		} catch (Exception e) {
			throw e;
		} finally {
		}
		return map;
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public void updateApprovalStatus(Long alloc_uniq_no, String status) throws Exception {

		EntityManager em = null;
		try {
			StringBuffer buffer = new StringBuffer();
			buffer.append(" Update  Alloc_header set ALLOC_APPR_STATUS='" + status + "' where alloc_uniq_no='"
					+ alloc_uniq_no + "'");
			Query query = entityManager.createNativeQuery(buffer.toString());
			query.executeUpdate();

		} catch (Exception e) {
			System.out.println("Error Occurred :" + e.getMessage());// uncomment asneeded --;
			throw e;
		} finally {
			if (em != null) {
				em.close();
			}
		}
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public void updateAllocDetailApprovalStatus(Long alloc_uniq_no, String status) throws Exception {

		EntityManager em = null;
		try {
			StringBuffer buffer = new StringBuffer();
			buffer.append(" Update  Alloc_Detail set ALLOCDTL_APPR_STATUS='" + status
					+ "' where ALLOCDTL_ALLOC_ID=( select alloc_id from  Alloc_header where  alloc_uniq_no='"
					+ alloc_uniq_no + "'");
			Query query = entityManager.createNativeQuery(buffer.toString());
			query.executeUpdate();

		} catch (Exception e) {
			System.out.println("Error Occurred :" + e.getMessage());// uncomment asneeded --;
			throw e;
		} finally {
			if (em != null) {
				em.close();
			}
		}
	}

	@Override
	public Pg_Doc_Alloc_Template getObjectById(Long slno) throws Exception {
		return this.entityManager.find(Pg_Doc_Alloc_Template.class, slno);

	}

	@Override
	public List<Pg_Doc_Alloc_Template> getListofPgDocAllocTemp(String allocuniquenum) throws Exception {
		// TODO Auto-generated method stub
		List<Pg_Doc_Alloc_Template> list = null;
		try {
			CriteriaBuilder builder = entityManager.getCriteriaBuilder();
			CriteriaQuery<Pg_Doc_Alloc_Template> criteriaQuery = builder.createQuery(Pg_Doc_Alloc_Template.class);
			Root<Pg_Doc_Alloc_Template> root = criteriaQuery.from(Pg_Doc_Alloc_Template.class);
			criteriaQuery.select(root)
					.where(builder.equal(root.get(Pg_Doc_Alloc_Template_.alloc_uniq_no), allocuniquenum));
			list = entityManager.createQuery(criteriaQuery).getResultList();
		} catch (Exception e) {
			// TODO: handle exception
			System.out.println("Error Occurred :" + e.getMessage());// uncomment asneeded --;
		}
		return list;
	}

	@Override
	public List<Pg_Doc_Alloc_Template_Error> getListofPgDocAllocTempErrorList(String allocuniquenum) throws Exception {
		// TODO Auto-generated method stub
		List<Pg_Doc_Alloc_Template_Error> list = null;
		Pg_Doc_Alloc_Template_Error bean = null;
		try {
			CriteriaBuilder builder = entityManager.getCriteriaBuilder();
			CriteriaQuery<Pg_Doc_Alloc_Template_Error> criteriaQuery = builder
					.createQuery(Pg_Doc_Alloc_Template_Error.class);
			Root<Pg_Doc_Alloc_Template_Error> root = criteriaQuery.from(Pg_Doc_Alloc_Template_Error.class);
			criteriaQuery.select(root)
					.where(builder.equal(root.get(Pg_Doc_Alloc_Template_Error_.alloc_uniq_no), allocuniquenum));
			list = entityManager.createQuery(criteriaQuery).getResultList();
		} catch (Exception e) {
			// TODO: handle exception
			System.out.println("Error Occurred :" + e.getMessage());// uncomment asneeded --;
		}
		return list;
	}

	@Override
	public List<RuleBasedAllocationBean> approvalDetails(Long period, Long unique_number) throws Exception {
		List<RuleBasedAllocationBean> approval_LISTs = new ArrayList<RuleBasedAllocationBean>();
		try {
			StringBuffer buffer = new StringBuffer("");
			buffer.append(
					" SELECT DM.div_disp_nm   AS DIV_NAME,fs.FSTAFF_MCL_NO as fscode,fs.fstaff_display_name as fsname,");
			buffer.append(
					" SIT.smp_prod_cd,SIT.smp_prod_name,T.allocdtl_curr_alloc_qty as final_allocqty,T.ALLOCDTL_RATE as alloc_rate,");
			buffer.append(" T.ALLOCDTL_ALLOC_QTY as alloc_qty,0 as msr_stock,S_DM.div_disp_nm AS PRODUCT_DIV_NAME,");
			buffer.append(" T.ALLOCDTL_CYCLE,SIT.smp_min_alloc_qty,'' as approval_remark");
			buffer.append(" FROM   alloc_header TH");
			buffer.append(" INNER JOIN alloc_detail T ON( th.ALLOC_ID = t.ALLOCDTL_ALLOC_ID )");
			buffer.append(" INNER JOIN div_master DM ON ( T.allocdtl_div_id = DM.div_id )");
			buffer.append(" INNER JOIN period P ON ( P.period_code = ALLOCDTL_PERIOD_CODE )");
			buffer.append(" INNER JOIN am_m_login_detail LD ON ( LD.ld_emp_cb_id = T.allocdtl_ins_usr_id )");
			buffer.append(" INNER JOIN div_master S_DM ON ( S_DM.div_id = T.ALLOCDTL_ALT_DIV_ID )");
			buffer.append(" INNER JOIN smpitem SIT ON( SIT.smp_prod_id = T.ALLOCDTL_PROD_ID )");
			buffer.append(" INNER JOIN fieldstaff fs ON( fs.fstaff_id = T.ALLOCDTL_fstaff_ID )");
			buffer.append(" WHERE  ");
			buffer.append(" P.period_id = :period ");
			buffer.append(" and th.alloc_uniq_no=:unique_number  ");
			buffer.append(" and (t.ALLOCDTL_CURR_ALLOC_QTY-t.ALLOCDTL_SUPPLY_QTY)>0");
			buffer.append(" ORDER  BY Th.alloc_uniq_no,t.allocdtl_id");
			Query query = entityManager.createNativeQuery(buffer.toString(), Tuple.class);
			query.setParameter("unique_number", unique_number);
			query.setParameter("period", period);

			List<Tuple> tuples = query.getResultList();
			if (tuples != null && !tuples.isEmpty()) {
				for (Tuple t : tuples) {
					RuleBasedAllocationBean approval_List = new RuleBasedAllocationBean();
					approval_List.setDivision(t.get("DIV_NAME", String.class));
					approval_List.setFs_code(t.get("FSCODE", String.class));
					approval_List.setFs_name(t.get("FSNAME", String.class));
					approval_List.setProduct_code(t.get("SMP_PROD_CD", String.class));
					approval_List.setProduct_name(t.get("SMP_PROD_NAME", String.class));
					approval_List.setFinal_alloc_qty(t.get("FINAL_ALLOCQTY", BigDecimal.class).setScale(0).toString());
					approval_List.setRate(t.get("ALLOC_RATE", BigDecimal.class).toString());
					approval_List.setQty(t.get("ALLOC_QTY", BigDecimal.class).setScale(0).toString());
					approval_List.setMsrstock(t.get("MSR_STOCK", Integer.class).toString());
					approval_List.setProddivision(t.get("PRODUCT_DIV_NAME", String.class));
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
	public void updateDocFsIdsforDoctorUpload(Long divId, String uniqueno) throws Exception {
		// TODO Auto-generated method stub
		try {
			StringBuffer sb = new StringBuffer();

			sb.append(" UPDATE T SET T.DOC_FS_ID=FS.FSTAFF_ID from PG_DOC_ALLOC_TEMPLATE T");
			sb.append(" JOIN FIELDSTAFF FS ON RTRIM(FS.FSTAFF_MCL_NO)=RTRIM(T.CUSTID)");
			sb.append(" WHERE ALLOC_UNIQ_NO=:uniq");
			sb.append(" AND FS.FS_CATEGORY='D'");
			sb.append(" AND FS.FSTAFF_DIV_ID=:divId");

			Query query = entityManager.createNativeQuery(sb.toString());
			query.setParameter("uniq", uniqueno);
			query.setParameter("divId", divId);

			query.executeUpdate();
		} catch (Exception e) {
			// TODO: handle exception
			throw e;
		}
	}

}
