package com.excel.repository;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.StoredProcedureQuery;
import javax.persistence.Tuple;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.excel.model.Alloc_gen_hd_;
import com.excel.model.AspDetail;
import com.excel.model.Dispatch_Header;
import com.excel.model.DivMaster;
import com.excel.model.EInvoiceGenerateData;
import com.excel.model.EInvoiceHeader;
import com.excel.model.EInvoiceHeaderStockist;
import com.excel.model.EInvoiceHeader_Arc;
import com.excel.model.EInvoiceHeader_Arc_Stockist;
import com.excel.model.E_invoice_report;
import com.excel.model.Period;
import com.excel.model.Period_;
import com.excel.model.Sum_Disp_Detail;
import com.excel.model.Sum_Disp_Header;
import com.excel.utility.MedicoConstants;

@Repository
public class EInvoiceRepositoryImpl implements EInvoiceRepository {

	@Autowired
	EntityManagerFactory emf;
	@PersistenceContext
	private EntityManager entityManager;

	@Override
	public List<DivMaster> getDivandTeams(String empID) throws Exception {
		List<DivMaster> list = new ArrayList<DivMaster>();

		System.out.println("empID " + empID);
		try {
			StringBuffer buffer = new StringBuffer(
					"select div_code,div_disp_nm,div_id,div_code_nm,div_status from  DIV_MASTER ");
			buffer.append("where div_id in (select ediv_div_id from am_m_emp_div_access where ediv_emp_id =:empID ");
			buffer.append("and ediv_status='A') order by div_disp_nm ");

			Query query = entityManager.createNativeQuery(buffer.toString(), Tuple.class);
			query.setParameter("empID", empID);
			List<Tuple> tuples = query.getResultList();
			if (tuples != null && !tuples.isEmpty()) {
				for (Tuple t : tuples) {
					DivMaster div = new DivMaster();
					div.setDiv_code(t.get("div_code", String.class));
					div.setDiv_disp_nm(t.get("div_disp_nm", String.class));
					div.setDiv_id(Long.valueOf(t.get("div_id", Integer.class)));
					div.setDiv_code_nm(t.get("div_code_nm", String.class));
					div.setDiv_status(String.valueOf(t.get("div_status", Character.class)));

					list.add(div);
				}

			}
			System.out.println("TeamList " + list.size());
		} catch (Exception e) {
			throw e;
		} finally {

		}
		return list;
	}

	@Override
	public List<Period> getperiod() throws Exception {
		List<Period> periodlist = null;
		EntityManager em = null;
		try {
			em = emf.createEntityManager();
			CriteriaBuilder builder = em.getCriteriaBuilder();
			CriteriaQuery<Period> criteriaQuery = builder.createQuery(Period.class);
			Root<Period> root = criteriaQuery.from(Period.class);
			criteriaQuery.multiselect(root.get(Period_.period_fin_year)).distinct(true)
					.orderBy(builder.desc(root.get(Period_.period_fin_year)));
			periodlist = entityManager.createQuery(criteriaQuery).getResultList();
		} catch (Exception e) {
			throw e;
		} finally {

		}
		return periodlist;

	}

	@Override
	public Period getdate() throws Exception {
		// select * from PERIOD where PERIOD_FIN_YEAR=2022 and PERIOD_CURRENT='Y'
		Period period = null;
		EntityManager em = null;
		try {
			em = emf.createEntityManager();
			CriteriaBuilder builder = em.getCriteriaBuilder();
			CriteriaQuery<Period> criteriaQuery = builder.createQuery(Period.class);
			Root<Period> root = criteriaQuery.from(Period.class);
			criteriaQuery.multiselect(root.get(Period_.period_id), root.get(Period_.period_start_date))
					.where(builder.equal(root.get(Period_.period_current), "Y"));
			period = entityManager.createQuery(criteriaQuery).getSingleResult();
		} catch (Exception e) {
			throw e;
		} finally {

		}

		return period;
	}

	@Override
	public List<Object> getIrnCancelData(String fromTrnId, String toTrnId, String divId, String finyearflag)
			throws Exception {
		StringBuffer sb = new StringBuffer();

		if (finyearflag.equals(MedicoConstants.CURRENT)) {
			sb.append(
					"select TRN_IRN_NUMBER,TRN_NUMBER from E_INVOICE_HEADER where TRN_ID>=:frmTrnId and TRN_ID<=:toTrnId AND DIV_ID=:divId AND  IRN_CANCEL='N' order by TRN_ID asc");
		} else if (finyearflag.equals(MedicoConstants.PREVIOUS)) {
			sb.append(
					"select TRN_IRN_NUMBER,TRN_NUMBER from E_INVOICE_HEADER_ARC where TRN_ID>=:frmTrnId and TRN_ID<=:toTrnId AND DIV_ID=:divId AND  IRN_CANCEL='N' order by TRN_ID asc");
		}

		Query query = entityManager.createNativeQuery(sb.toString());
		query.setParameter("frmTrnId", fromTrnId);
		query.setParameter("toTrnId", toTrnId);
		query.setParameter("divId", divId);
		List<Object> results = query.getResultList();
		return results;
	}

	@Override
	public List<EInvoiceGenerateData> getEInvoiceGenerateData(String finflag, String finyear, String divId,
			String frdspid, String todspid) throws Exception {
		// TODO Auto-generated method stub
		EntityManager em = null;
		List<EInvoiceGenerateData> list = null;
		try {

			System.out.println("finflag" + finflag);
			System.out.println("finyear" + finyear);
			System.out.println("divid" + divId);
			System.out.println("frdspid" + frdspid);
			System.out.println("todspid" + todspid);

			em = emf.createEntityManager();
			StoredProcedureQuery callDisptachSumDetReport = em
					.createNamedStoredProcedureQuery("callE_INVOICING_SUMCHL");
			callDisptachSumDetReport.setParameter("finflag", finflag);
			callDisptachSumDetReport.setParameter("finyear", finyear);
			callDisptachSumDetReport.setParameter("divid", divId);
			callDisptachSumDetReport.setParameter("frdspid", frdspid);
			callDisptachSumDetReport.setParameter("todspid", todspid);
			list = callDisptachSumDetReport.getResultList();
		} catch (Exception e) {
			// TODO: handle exception
			throw e;
		}
		return list;
	}

	@Override
	public EInvoiceHeader getObjectByID(Long headerId) throws Exception {
		// TODO Auto-generated method stub
		List<EInvoiceHeader> einvlist = null;
		EntityManager em = null;
		try {
			em = emf.createEntityManager();
			CriteriaBuilder builder = em.getCriteriaBuilder();
			CriteriaQuery<EInvoiceHeader> criteriaQuery = builder.createQuery(EInvoiceHeader.class);
			Root<EInvoiceHeader> root = criteriaQuery.from(EInvoiceHeader.class);
			criteriaQuery.select(root);
			criteriaQuery.where(builder.and(builder.equal(root.get("trn_id"), headerId)));
			einvlist = entityManager.createQuery(criteriaQuery).getResultList();
			if (einvlist != null && einvlist.size() == 1) {
				return einvlist.get(0);
			}
		} catch (Exception e) {
			throw e;
		} finally {

		}
		return null;
	}

	@Override
	public List<Object> getChallanDetailsHavingIrn(String fromTrnId, String toTrnId, String finYear, String divId)
			throws Exception {
		List<Object> results = null;
		EntityManager em = null;
		try {
			em = emf.createEntityManager();
			StringBuffer sb = new StringBuffer();
			sb.append(" SELECT ROW_NUMBER() OVER(ORDER BY E.EINVOICE_TRANID) AS Row,");
			sb.append(
					" E.TRN_IRN_NUMBER Irn,0 Distance,1 TransMode,DH.TRANS_GST_REG_NO  TransId,DH.SUMDSP_TRANSPORTER TransName,Format(DH.SUMDSP_LR_DT,'dd/MM/yyyy') TransDocDt,DH.SUMDSP_LR_NO TransDocNo,");
			sb.append(" CASE WHEN DH.SUMDSP_LORRY_NO = ' ' OR DH.SUMDSP_LORRY_NO IS NULL THEN 'KA123456'");
			sb.append(
					" WHEN LEN(RTRIM(DH.SUMDSP_LORRY_NO)) <= 7 THEN LEFT(CAST(RTRIM(DH.SUMDSP_LORRY_NO) AS varchar(15))+replicate('-',15),15)");
			sb.append(" ELSE SUBSTRING( DH.SUMDSP_LORRY_NO , 1 , 15 ) END VehNo,");
			sb.append(
					" 'R' VehType,DL.DPTADDR1 ExpShipDtlsAddr1,DL.DPTADDR2 ExpShipDtlsAddr2,DL.DPTDESTINATION ExpShipDtlsLoc,");
			sb.append(" DL.DPTPINCODE ExpShipDtlsPin,fst.SGprmdet_Text1 ExpShipDtlsStcd,E.TRN_NUMBER as TRN_NUMBER");
			// sb.append(" FROM E_INVOICE_HEADER E , SUM_DISP_HEADER DH , location L,
			// DEPOT_LOCATIONS DL LEFT OUTER JOIN SG_d_parameters_details fst on
			// fst.SGprmdet_id = DL.DPTSTATE_ID");
			sb.append(" FROM E_INVOICE_HEADER E , SUM_DISP_HEADER DH ,");
			// sb.append(" location L, ");
			sb.append(
					" DEPOT_LOCATIONS DL LEFT OUTER JOIN SG_d_parameters_details  fst on fst.SGprmdet_id = DL.DPTSTATE_ID ");
			sb.append(" WHERE E.TRN_ID = DH.SUMDSP_ID");
			sb.append(" AND DH.SUMDSP_RECVG_LOC_ID = DL.DPTLOC_ID");
			sb.append(
					" AND E.TRN_ID>=:frmtrnId and E.TRN_ID<=:totrnId AND E.TRN_EWAYBILLNO is null AND E.FIN_YEAR_ID=:finyrId AND E.DIV_ID=:divId");

			System.out.println("qury:" + sb.toString());
			Query qry = em.createNativeQuery(sb.toString());
			qry.setParameter("finyrId", finYear);
			qry.setParameter("frmtrnId", fromTrnId);
			qry.setParameter("totrnId", toTrnId);
			qry.setParameter("divId", divId);
			results = qry.getResultList();
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			throw e;
		} finally {
			if (em != null)
				em.close();
		}
		// SQLQuery sqlQuery=session.createSQLQuery(sb.toString());
		// List<Object> results = sqlQuery.list();
		// session.close();
		return results;
	}

	@Override
	public List<Sum_Disp_Header> getChallanDetails(String finYear, String divId, String finyrflag, Long loc_id)
			throws Exception {
		// TODO Auto-generated method stub
		List<Sum_Disp_Header> listhd = new ArrayList<Sum_Disp_Header>();
		try {
			SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
			System.out.println("finyrId" + finYear);
			System.out.println("divId" + divId);

			StringBuffer sb = new StringBuffer();
			if (finyrflag.equals(MedicoConstants.CURRENT)) {
				sb.append(
						" SELECT SUMDSP_ID,SUMDSP_CHALLAN_NO,FORMAT( SUMDSP_CHALLAN_DT,'dd/MM/yyyy') SUMDSP_CHALLAN_DT,SUMDSP_DIV_ID,E_INV_REQ");
				sb.append(" FROM ( ");
				sb.append(" SELECT SUMDSP_ID,SUMDSP_CHALLAN_NO,SUMDSP_CHALLAN_DT,EINV.TRN_ID,SUMDSP_DIV_ID,E_INV_REQ ");
				sb.append(
						" FROM SUM_DISP_HEADER DH LEFT OUTER JOIN E_INVOICE_HEADER EINV ON EINV.TRN_ID = DH.SUMDSP_ID AND EINV.FIN_YEAR_ID = DH.SUMDSP_FIN_YEAR ");
				sb.append(" WHERE SUMDSP_FIN_YEAR =:finyrId ");
				sb.append(" AND CONVERT( DATE , DH.SUMDSP_CHALLAN_DT ) >= CONVERT( DATE , '2020-11-01' ) ");
				sb.append(
						" AND DH.SUMDSP_LR_NO IS NOT NULL AND DH.SUMDSP_LR_DT IS NOT NULL and DH.SUMDSP_LOC_ID = :loc_id");
				sb.append(" ) D ");
				sb.append(" WHERE TRN_ID IS NULL AND SUMDSP_DIV_ID=:divId AND E_INV_REQ='Y' ");
				sb.append(" ORDER BY SUMDSP_CHALLAN_DT,SUMDSP_CHALLAN_NO ");
				
				
			} else if (finyrflag.equals(MedicoConstants.PREVIOUS)) {
				sb.append(
						" SELECT SUMDSP_ID,SUMDSP_CHALLAN_NO,FORMAT( SUMDSP_CHALLAN_DT,'dd/MM/yyyy') SUMDSP_CHALLAN_DT,SUMDSP_DIV_ID,E_INV_REQ");
				sb.append(" FROM ( ");
				sb.append(" SELECT SUMDSP_ID,SUMDSP_CHALLAN_NO,SUMDSP_CHALLAN_DT,EINV.TRN_ID,SUMDSP_DIV_ID,E_INV_REQ ");
				sb.append(
						" FROM SUM_DISP_HEADER_ARC DH LEFT OUTER JOIN E_INVOICE_HEADER EINV ON EINV.TRN_ID = DH.SUMDSP_ID AND EINV.FIN_YEAR_ID = DH.SUMDSP_FIN_YEAR ");
				sb.append(" WHERE SUMDSP_FIN_YEAR =:finyrId ");
				sb.append(" AND CONVERT( DATE , DH.SUMDSP_CHALLAN_DT ) >= CONVERT( DATE , '2020-11-01' ) ");
				sb.append(
						" AND DH.SUMDSP_LR_NO IS NOT NULL AND DH.SUMDSP_LR_DT IS NOT NULL and DH.SUMDSP_LOC_ID = :loc_id");
				sb.append(" ) D ");
				sb.append(" WHERE TRN_ID IS NULL AND SUMDSP_DIV_ID=:divId AND E_INV_REQ='Y' ");
				sb.append(" ORDER BY SUMDSP_CHALLAN_DT,SUMDSP_CHALLAN_NO ");
			}

			System.out.println("qury:" + sb.toString());
			Query qry = entityManager.createNativeQuery(sb.toString(), Tuple.class);
			qry.setParameter("finyrId", finYear);
			qry.setParameter("divId", divId);
			qry.setParameter("loc_id", loc_id);
			List<Tuple> results = qry.getResultList();

			if (results != null && results.size() > 0) {
				Sum_Disp_Header hd = new Sum_Disp_Header();
				for (Tuple t : results) {
					hd = new Sum_Disp_Header();
					hd.setSumdsp_id(t.get("SUMDSP_ID", Integer.class).longValue());
					hd.setSumdsp_challan_no(t.get("SUMDSP_CHALLAN_NO", String.class));
					hd.setSumdsp_challan_dt(sdf.parse(t.get("SUMDSP_CHALLAN_DT", String.class)));
					// hd.setSumdspdiv_id(t.get("SUMDSP_DIV_ID",BigDecimal.class).longValue());

					listhd.add(hd);
				}

				System.out.println("list : " + listhd.size());
			}
		} catch (Exception e) {
			// TODO: handle exception
			throw e;
		}
		return listhd;
	}

	@Override
	public EInvoiceHeader getEInvoiceHeaderByIrn(String irn) throws Exception {
		// TODO Auto-generated method stub
		EInvoiceHeader hdr = null;
		try {
			CriteriaBuilder builder = entityManager.getCriteriaBuilder();
			CriteriaQuery<EInvoiceHeader> criteriaQuery = builder.createQuery(EInvoiceHeader.class);
			Root<EInvoiceHeader> root = criteriaQuery.from(EInvoiceHeader.class);
			criteriaQuery.select(root).where(builder.equal(root.get("trn_irn_number"), irn));
			hdr = entityManager.createQuery(criteriaQuery).getSingleResult();
		} catch (NoResultException e) {
			// TODO: handle exception
			return null;
		} catch (Exception e) {
			// TODO: handle exception
			throw e;
		}
		return hdr;
	}

	@Override
	public EInvoiceHeader getEInvoiceHeaderByEWB(String ewbNo) throws Exception {
		// TODO Auto-generated method stub
		EInvoiceHeader hdr = null;
		try {
			CriteriaBuilder builder = entityManager.getCriteriaBuilder();
			CriteriaQuery<EInvoiceHeader> criteriaQuery = builder.createQuery(EInvoiceHeader.class);
			Root<EInvoiceHeader> root = criteriaQuery.from(EInvoiceHeader.class);
			criteriaQuery.select(root).where(builder.equal(root.get("trn_ewaybillno"), ewbNo));
			hdr = entityManager.createQuery(criteriaQuery).getSingleResult();
		} catch (NoResultException e) {
			// TODO: handle exception
			return null;
		} catch (Exception e) {
			// TODO: handle exception
			throw e;
		}
		return hdr;
	}

	@Override
	public List<EInvoiceHeader> getIrnChallansWithoutEway(String finYear, String divId, String finYearFlag, Long loc_id)
			throws Exception {
		// TODO Auto-generated method stub
		List<EInvoiceHeader> hdr = new ArrayList<EInvoiceHeader>();
		try {
			StringBuffer sb = new StringBuffer();
			// sb.append("select trn_id,trn_number from E_INVOICE_HEADER where
			// fin_year_id=:finyrId and irn_cancel='N' AND trn_ewaybillno is null AND
			// div_id=:divId order by trn_id asc");
			sb.append(" select eh.trn_id,eh.trn_number from E_INVOICE_HEADER eh");
			sb.append(" inner join SUM_DISP_HEADER dsp on dsp.SUMDSP_ID = eh.TRN_ID");
			sb.append(" where dsp.SUMDSP_LOC_ID = :loc_id");
			sb.append(" and eh.fin_year_id=:finyrId");
			sb.append(" and eh.irn_cancel='N'");
			sb.append(" AND eh.trn_ewaybillno is null");
			sb.append(" AND eh.div_id=:divId");
			sb.append(" order by eh.trn_id asc");
			Query qry = entityManager.createNativeQuery(sb.toString(), Tuple.class);
			qry.setParameter("finyrId", Long.valueOf(finYear));
			qry.setParameter("divId", Long.valueOf(divId));
			qry.setParameter("loc_id", loc_id);
			List<Tuple> list = qry.getResultList();

			if (list != null & list.size() > 0) {
				EInvoiceHeader hd = new EInvoiceHeader();
				for (Tuple t : list) {
					hd = new EInvoiceHeader();
					hd.setTrn_id(t.get("trn_id", BigDecimal.class).longValue());
					hd.setTrn_number(t.get("trn_number", String.class));
					hdr.add(hd);
				}
			}
		} catch (Exception e) {
			// TODO: handle exception
			throw e;
		}
		return hdr;
	}

	@Override
	public List<EInvoiceHeader> getIrnGencancelData(String finYear, String divId, Long loc_id) throws Exception {
		// TODO Auto-generated method stub
		List<EInvoiceHeader> list = new ArrayList<EInvoiceHeader>();
		try {
			StringBuffer sb = new StringBuffer();
			sb.append(" select TRN_ID,TRN_NUMBER from E_INVOICE_HEADER eh");
			sb.append(" inner join SUM_DISP_HEADER dsp on dsp.sumdsp_id = eh.TRN_ID");
			sb.append(" where dsp.SUMDSP_LOC_ID = :loc_id");
			sb.append(" and eh.FIN_YEAR_ID=:finyrId and eh.IRN_CANCEL='N' and");
			sb.append(" (eh.EWAY_BILL_CANCEL='Y' OR  eh.TRN_EWAYBILLNO is null)");
			sb.append(" AND eh.DIV_ID=:divId order by eh.TRN_ID asc");
			Query qry = entityManager.createNativeQuery(sb.toString(), Tuple.class);
			qry.setParameter("finyrId", Long.valueOf(finYear));
			qry.setParameter("divId", Long.valueOf(divId));
			qry.setParameter("loc_id", loc_id);
			List<Tuple> tlist = qry.getResultList();

			if (tlist != null & tlist.size() > 0) {
				EInvoiceHeader hd = new EInvoiceHeader();
				for (Tuple t : tlist) {
					hd = new EInvoiceHeader();
					hd.setTrn_id(t.get("TRN_ID", BigDecimal.class).longValue());
					hd.setTrn_number(t.get("TRN_NUMBER", String.class));
					list.add(hd);
				}
			}
		} catch (Exception e) {
			// TODO: handle exception
			throw e;
		}
		return list;
	}

	@Override
	public List<EInvoiceHeader> getEwayBillCancel(String finYear, String divId, String finYearFlag, Long loc_id) {
		// TODO Auto-generated method stub
		List<EInvoiceHeader> list = new ArrayList<EInvoiceHeader>();
		try {
			StringBuffer sb = new StringBuffer();
			if (finYearFlag.trim().equals(MedicoConstants.CURRENT)) {
				// sb.append("select TRN_ID,TRN_NUMBER,TRN_EWAYBILLNO from E_INVOICE_HEADER
				// where FIN_YEAR_ID=:finyear and EWAY_BILL_CANCEL='N' and DIV_ID=:divId AND
				// TRN_EWAYBILLNO is not null order by TRN_ID asc");
				sb.append(" select TRN_ID,TRN_NUMBER,TRN_EWAYBILLNO from E_INVOICE_HEADER eh");
				sb.append(" inner join SUM_DISP_HEADER dsp on dsp.sumdsp_id = eh.TRN_ID");
				sb.append(" where dsp.SUMDSP_LOC_ID = :loc_id");
				sb.append(" and eh.FIN_YEAR_ID=:finyear");
				sb.append(" and eh.EWAY_BILL_CANCEL='N'");
				sb.append(" and eh.DIV_ID=:divId");
				sb.append(" AND eh.TRN_EWAYBILLNO is not null");
				sb.append(" order by eh.TRN_ID asc");
			} else if (finYearFlag.trim().equals(MedicoConstants.PREVIOUS)) {
				// sb.append("select TRN_ID,TRN_NUMBER,TRN_EWAYBILLNO from E_INVOICE_HEADER_ARC
				// where FIN_YEAR_ID=:finyear and EWAY_BILL_CANCEL='N' and DIV_ID=:divId AND
				// TRN_EWAYBILLNO is not null order by TRN_ID asc");
				sb.append(" select TRN_ID,TRN_NUMBER,TRN_EWAYBILLNO from E_INVOICE_HEADER_ARC eh");
				sb.append(" inner join SUM_DISP_HEADER_ARC dsp on dsp.sumdsp_id = eh.TRN_ID");
				sb.append(" where dsp.SUMDSP_LOC_ID = :loc_id");
				sb.append(" and eh.FIN_YEAR_ID=:finyear");
				sb.append(" and eh.EWAY_BILL_CANCEL='N'");
				sb.append(" and eh.DIV_ID=:divId");
				sb.append(" AND eh.TRN_EWAYBILLNO is not null");
				sb.append(" order by eh.TRN_ID asc");
			}
			Query qry = entityManager.createNativeQuery(sb.toString(), Tuple.class);
			qry.setParameter("finyear", Long.valueOf(finYear));
			qry.setParameter("divId", Long.valueOf(divId));
			qry.setParameter("loc_id", loc_id);
			List<Tuple> tlist = qry.getResultList();
			if (tlist != null & tlist.size() > 0) {
				EInvoiceHeader hd = new EInvoiceHeader();
				for (Tuple t : tlist) {
					hd = new EInvoiceHeader();
					hd.setTrn_id(t.get("TRN_ID", BigDecimal.class).longValue());
					hd.setTrn_number(t.get("TRN_NUMBER", String.class));
					hd.setTrn_ewaybillno(t.get("TRN_EWAYBILLNO", String.class));
					list.add(hd);
				}
			}
		} catch (Exception e) {
			// TODO: handle exception
			throw e;
		}
		return list;
	}

	@Override
	public List<EInvoiceHeader> getIrnDownloadData(String finYear, String divId, String finYearFlag, Long loc_id)
			throws Exception {
		// TODO Auto-generated method stub
		List<EInvoiceHeader> list = new ArrayList<EInvoiceHeader>();
		try {
			StringBuffer sb = new StringBuffer();
			if (finYearFlag.equals(MedicoConstants.CURRENT)) {
				// sb.append("select TRN_ID,TRN_NUMBER from E_INVOICE_HEADER where
				// FIN_YEAR_ID=:finyear and IRN_CANCEL='N' AND DIV_ID=:divId order by TRN_ID
				// asc");
				sb.append(" select eh.trn_id,eh.trn_number from E_INVOICE_HEADER eh");
				sb.append(" inner join SUM_DISP_HEADER dsp on dsp.SUMDSP_ID = eh.TRN_ID");
				sb.append(" where dsp.SUMDSP_LOC_ID = :loc_id");
				sb.append(" and eh.fin_year_id=:finyrId");
				sb.append(" and eh.irn_cancel='N'");
				sb.append(" AND eh.div_id=:divId");
				sb.append(" order by eh.trn_id asc");
			} else if (finYearFlag.equals(MedicoConstants.PREVIOUS)) {
				// sb.append("select TRN_ID,TRN_NUMBER from E_INVOICE_HEADER_ARC where
				// FIN_YEAR_ID=:finyear and IRN_CANCEL='N' AND DIV_ID=:divId order by TRN_ID
				// asc");
				sb.append(" select eh.trn_id,eh.trn_number from E_INVOICE_HEADER_ARC eh");
				sb.append(" inner join SUM_DISP_HEADER_ARC dsp on dsp.SUMDSP_ID = eh.TRN_ID");
				sb.append(" where dsp.SUMDSP_LOC_ID = :loc_id");
				sb.append(" and eh.fin_year_id=:finyrId");
				sb.append(" and eh.irn_cancel='N'");
				sb.append(" AND eh.div_id=:divId");
				sb.append(" order by eh.trn_id asc");
			}
			Query qry = entityManager.createNativeQuery(sb.toString(), Tuple.class);
			qry.setParameter("finyear", Long.valueOf(finYear));
			qry.setParameter("divId", Long.valueOf(divId));
			qry.setParameter("loc_id", Long.valueOf(loc_id));
			List<Tuple> tlist = qry.getResultList();
			if (tlist != null & tlist.size() > 0) {
				EInvoiceHeader hd = new EInvoiceHeader();
				for (Tuple t : tlist) {
					hd = new EInvoiceHeader();
					hd.setTrn_id(t.get("TRN_ID", BigDecimal.class).longValue());
					hd.setTrn_number(t.get("TRN_NUMBER", String.class));
					list.add(hd);
				}
			}
		} catch (Exception e) {
			// TODO: handle exception
			throw e;
		}
		return list;
	}

	@Override
	public List<Object> getEwayBillDownloadNumbers(String finYear, String divId, String fromTrn, String toTrn,
			String finYearFlag) {
		// TODO Auto-generated method stub
		List<Object> results = new ArrayList<Object>();
		try {
			StringBuffer sb = new StringBuffer();
			if (finYearFlag.trim().equals(MedicoConstants.CURRENT)) {
				sb.append(
						"select TRN_EWAYBILLNO,TRN_NUMBER from E_INVOICE_HEADER where TRN_ID>=:frmTrnId and TRN_ID<=:toTrnId AND DIV_ID=:divId AND EWAY_BILL_CANCEL='N' AND TRN_EWAYBILLNO is not null  order by TRN_ID asc");
			} else if (finYearFlag.trim().equals(MedicoConstants.PREVIOUS)) {
				sb.append(
						"select TRN_EWAYBILLNO,TRN_NUMBER from E_INVOICE_HEADER_ARC where TRN_ID>=:frmTrnId and TRN_ID<=:toTrnId AND DIV_ID=:divId AND EWAY_BILL_CANCEL='N' AND TRN_EWAYBILLNO is not null  order by TRN_ID asc");
			}
			Query query = entityManager.createNativeQuery(sb.toString());
			query.setParameter("frmTrnId", fromTrn);
			query.setParameter("toTrnId", toTrn);
			query.setParameter("divId", divId);
			results = query.getResultList();
		} catch (Exception e) {
			// TODO: handle exception
			throw e;
		}
		return results;
	}

	@Override
	public EInvoiceHeader_Arc getObjectByIDAndFinYear(Long headerId, String finYear) throws Exception {
		List<EInvoiceHeader_Arc> einvlist = null;
		EntityManager em = null;
		try {
			em = emf.createEntityManager();
			CriteriaBuilder builder = em.getCriteriaBuilder();
			CriteriaQuery<EInvoiceHeader_Arc> criteriaQuery = builder.createQuery(EInvoiceHeader_Arc.class);
			Root<EInvoiceHeader_Arc> root = criteriaQuery.from(EInvoiceHeader_Arc.class);
			criteriaQuery.select(root);
			criteriaQuery.where(builder.and(builder.equal(root.get("trn_id"), headerId),
					builder.equal(root.get("fin_year_id"), finYear)));
			einvlist = entityManager.createQuery(criteriaQuery).getResultList();
			if (einvlist != null && einvlist.size() == 1) {
				return einvlist.get(0);
			}
		} catch (Exception e) {
			throw e;
		} finally {

		}
		return null;
	}

	@Override
	public EInvoiceHeader getEInvoiceHeaderByEWayBillNo(String ewaybill) throws Exception {
		// TODO Auto-generated method stub
		EInvoiceHeader hdr = null;
		try {
			CriteriaBuilder builder = entityManager.getCriteriaBuilder();
			CriteriaQuery<EInvoiceHeader> criteriaQuery = builder.createQuery(EInvoiceHeader.class);
			Root<EInvoiceHeader> root = criteriaQuery.from(EInvoiceHeader.class);
			criteriaQuery.select(root).where(builder.equal(root.get("trn_ewaybillno"), ewaybill));
			hdr = entityManager.createQuery(criteriaQuery).getSingleResult();
		} catch (NoResultException e) {
			// TODO: handle exception
			return null;
		} catch (Exception e) {
			// TODO: handle exception
			throw e;
		}
		return hdr;
	}

	@Override
	public List<Object> getChallanDetailsHavingIrnSuperTax(String fromTrnId, String toTrnId, String finYear,
			String divId) throws Exception {
		List<Object> results = null;
		EntityManager em = null;
		try {
			em = emf.createEntityManager();
			StringBuffer sb = new StringBuffer();
			sb.append(" SELECT ROW_NUMBER() OVER(ORDER BY E.EINVOICE_TRANID) AS Row,");
			sb.append(
					" E.TRN_IRN_NUMBER Irn,DH.SUMDSP_CHALLAN_NO DocNo , Format(DH.SUMDSP_DSP_DT,'dd/MM/yyyy') DocDt , 'INV' DocTyp ,");
			sb.append(
					" L.GST_REG_NO SellerGSTIN ,DH.TRANS_GST_REG_NO  TransId,DH.SUMDSP_TRANSPORTER TransName,1 TransMode,0 Distance,");
			sb.append(" DH.SUMDSP_LR_NO TransDocNo,Format(DH.SUMDSP_LR_DT,'dd/MM/yyyy') TransDocDt,");
			sb.append(" CASE WHEN DH.SUMDSP_LORRY_NO = ' ' OR DH.SUMDSP_LORRY_NO IS NULL THEN 'KA123456'");
			sb.append(
					" WHEN LEN(RTRIM(DH.SUMDSP_LORRY_NO)) <= 7 THEN LEFT(CAST(RTRIM(DH.SUMDSP_LORRY_NO) AS varchar(15))+replicate('-',15),15)");
			sb.append(" ELSE SUBSTRING( DH.SUMDSP_LORRY_NO , 1 , 15 ) END VehNo,'R' VehType");
			sb.append(
					" FROM E_INVOICE_HEADER E , SUM_DISP_HEADER DH , location L, DEPOT_LOCATIONS DL LEFT OUTER JOIN SG_d_parameters_details  fst on fst.SGprmdet_id = DL.DPTSTATE_ID");
			sb.append(" WHERE E.TRN_ID = DH.SUMDSP_ID");
			sb.append(" AND DH.SUMDSP_RECVG_LOC_ID = DL.DPTLOC_ID");
			sb.append(" AND E.TRN_ID>=:frmtrnId and E.TRN_ID<=:totrnId");
			sb.append(" AND E.TRN_EWAYBILLNO is null");
			sb.append(" AND E.FIN_YEAR_ID=:finyrId AND E.DIV_ID=:divId");

			System.out.println("qury:" + sb.toString());
			Query qry = em.createNativeQuery(sb.toString());
			qry.setParameter("finyrId", finYear);
			qry.setParameter("frmtrnId", fromTrnId);
			qry.setParameter("totrnId", toTrnId);
			qry.setParameter("divId", divId);
			results = qry.getResultList();
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			throw e;
		} finally {
			if (em != null)
				em.close();
		}
		// SQLQuery sqlQuery=session.createSQLQuery(sb.toString());
		// List<Object> results = sqlQuery.list();
		// session.close();
		return results;
	}

	@Override
	public List<EInvoiceGenerateData> getEInvoiceGenerateDataStockist(String finflag, String finyear, String divId,
			String frdspid, String todspid,String locid) throws Exception {
		// TODO Auto-generated method stub
		EntityManager em = null;
		List<EInvoiceGenerateData> list = null;
		try {

			System.out.println("finflag" + finflag);
			System.out.println("finyear" + finyear);
			System.out.println("divid" + divId);
			System.out.println("frdspid" + frdspid);
			System.out.println("todspid" + todspid);
			System.out.println("locid" + locid);

			em = emf.createEntityManager();
			StoredProcedureQuery callEInvSumChlnStk = em
					.createNamedStoredProcedureQuery("callE_INVOICING_SUMCHL_STOCKIST");
			callEInvSumChlnStk.setParameter("finflag", finflag);
			callEInvSumChlnStk.setParameter("finyear", finyear);
			callEInvSumChlnStk.setParameter("divid", divId);
			callEInvSumChlnStk.setParameter("frdspid", frdspid);
			callEInvSumChlnStk.setParameter("todspid", todspid);
			callEInvSumChlnStk.setParameter("locid", locid);
			list = callEInvSumChlnStk.getResultList();
		} catch (Exception e) {
			// TODO: handle exception
			throw e;
		}
		return list;
	}

	@Override
	public EInvoiceHeaderStockist getObjectByIDStk(Long headerId) throws Exception {
		List<EInvoiceHeaderStockist> einvlist = null;
		EntityManager em = null;
		try {
			em = emf.createEntityManager();
			CriteriaBuilder builder = em.getCriteriaBuilder();
			CriteriaQuery<EInvoiceHeaderStockist> criteriaQuery = builder.createQuery(EInvoiceHeaderStockist.class);
			Root<EInvoiceHeaderStockist> root = criteriaQuery.from(EInvoiceHeaderStockist.class);
			criteriaQuery.select(root);
			criteriaQuery.where(builder.and(builder.equal(root.get("trn_id"), headerId)));
			einvlist = entityManager.createQuery(criteriaQuery).getResultList();
			if (einvlist != null && einvlist.size() == 1) {
				return einvlist.get(0);
			}
		} catch (Exception e) {
			throw e;
		} finally {
			if (em != null)
				em.close();
		}
		return null;
	}

	@Override
	public EInvoiceHeader_Arc_Stockist getObjectByIDAndFinYearStk(Long headerId, String finYear) throws Exception {
		List<EInvoiceHeader_Arc_Stockist> einvlist = null;
		EntityManager em = null;
		try {
			em = emf.createEntityManager();
			CriteriaBuilder builder = em.getCriteriaBuilder();
			CriteriaQuery<EInvoiceHeader_Arc_Stockist> criteriaQuery = builder
					.createQuery(EInvoiceHeader_Arc_Stockist.class);
			Root<EInvoiceHeader_Arc_Stockist> root = criteriaQuery.from(EInvoiceHeader_Arc_Stockist.class);
			criteriaQuery.select(root);
			criteriaQuery.where(builder.and(builder.equal(root.get("trn_id"), headerId),
					builder.equal(root.get("fin_year_id"), finYear)));
			einvlist = entityManager.createQuery(criteriaQuery).getResultList();
			if (einvlist != null && einvlist.size() == 1) {
				return einvlist.get(0);
			}
		} catch (Exception e) {
			throw e;
		} finally {

		}
		return null;
	}

	@Override
	public EInvoiceHeaderStockist getEInvoiceHeaderStockistByIrn(String irn) throws Exception {
		// TODO Auto-generated method stub
		EInvoiceHeaderStockist hdr = null;
		try {
			CriteriaBuilder builder = entityManager.getCriteriaBuilder();
			CriteriaQuery<EInvoiceHeaderStockist> criteriaQuery = builder.createQuery(EInvoiceHeaderStockist.class);
			Root<EInvoiceHeaderStockist> root = criteriaQuery.from(EInvoiceHeaderStockist.class);
			criteriaQuery.select(root).where(builder.equal(root.get("trn_irn_number"), irn));
			hdr = entityManager.createQuery(criteriaQuery).getSingleResult();
		} catch (NoResultException e) {
			// TODO: handle exception
			return null;
		} catch (Exception e) {
			// TODO: handle exception
			throw e;
		}
		return hdr;
	}

	@Override
	public EInvoiceHeaderStockist getEInvoiceHeaderStockistByEWB(String ewbNo) throws Exception {
		// TODO Auto-generated method stub
		EInvoiceHeaderStockist hdr = null;
		try {
			CriteriaBuilder builder = entityManager.getCriteriaBuilder();
			CriteriaQuery<EInvoiceHeaderStockist> criteriaQuery = builder.createQuery(EInvoiceHeaderStockist.class);
			Root<EInvoiceHeaderStockist> root = criteriaQuery.from(EInvoiceHeaderStockist.class);
			criteriaQuery.select(root).where(builder.equal(root.get("trn_ewaybillno"), ewbNo));
			hdr = entityManager.createQuery(criteriaQuery).getSingleResult();
		} catch (NoResultException e) {
			// TODO: handle exception
			return null;
		} catch (Exception e) {
			// TODO: handle exception
			throw e;
		}
		return hdr;
	}

	@Override
	public List<Object> getChallanDetailsHavingIrnStockist(Long loc_id, String fromTrnId, String toTrnId,
			String finYear, String divId) throws Exception {
		List<Object> results = null;
		EntityManager em = null;
		try {
			em = emf.createEntityManager();
			StringBuffer sb = new StringBuffer();
			
//			sb.append(" SELECT ROW_NUMBER() OVER(ORDER BY E.EINVOICE_TRANID) AS Row, E.TRN_IRN_NUMBER Irn,0 Distance,1 TransMode,");
//			sb.append(" DH.DSP_TRANS_GST_REG_NO  TransId,DH.DSP_TRANSPORTER TransName, Format(DH.DSP_LR_DT,'dd/MM/yyyy') TransDocDt,");
//			sb.append(" DH.DSP_LR_NO TransDocNo, CASE WHEN DH.DSP_LORRY_NO = ' ' OR DH.DSP_LORRY_NO IS NULL THEN 'KA123456'");
//			sb.append(" WHEN LEN(RTRIM(DH.DSP_LORRY_NO)) <= 7 THEN LEFT(CAST(RTRIM(DH.DSP_LORRY_NO) AS varchar(15))+replicate('-',15),15)");
//			sb.append(" ELSE SUBSTRING( DH.DSP_LORRY_NO , 1 , 15 ) END VehNo,'R' VehType,dh.DSPFSTAFF_ADDR1 ExpShipDtlsAddr1,dh.DSPFSTAFF_ADDR2");
//			sb.append(" ExpShipDtlsAddr2,dh.DSPFSTAFF_DESTINATION ExpShipDtlsLoc,right(rtrim(dh.dspfstaff_addr4),6) ExpShipDtlsPin,");
//			sb.append(" fst.SGprmdet_Text1 ExpShipDtlsStcd,E.TRN_NUMBER as TRN_NUMBER FROM E_INVOICE_HEADER_STOCKIST E ,");
//			sb.append(" DEPOT_LOCATIONS DL , DISPATCH_HEADER DH  LEFT OUTER JOIN SG_d_parameters_details  fst on");
//			sb.append(" fst.SGprmdet_id = dh.dsp_STATE_ID WHERE DH.DSP_LOC_ID = :locId AND E.TRN_ID = DH.DSP_ID");
//			sb.append(" AND DH.DSP_RECVG_LOC_ID = DL.DPTLOC_ID AND E.TRN_ID>=:frmtrnId and E.TRN_ID<=:totrnId AND E.TRN_EWAYBILLNO is null");
//			sb.append(" AND E.FIN_YEAR_ID=:finyrId AND E.DIV_ID=:divId");
			
			sb.append(" SELECT ROW_NUMBER() OVER(ORDER BY E.EINVOICE_TRANID) AS Row, E.TRN_IRN_NUMBER Irn,ART.DISTANCE Distance,1 TransMode,");
			sb.append(" DH.DSP_TRANS_GST_REG_NO  TransId,DH.DSP_TRANSPORTER TransName, Format(DH.DSP_LR_DT,'dd/MM/yyyy') TransDocDt,");
			sb.append(" DH.DSP_LR_NO TransDocNo, CASE WHEN DH.DSP_LORRY_NO = ' ' OR DH.DSP_LORRY_NO IS NULL THEN 'KA123456'");
			sb.append(" WHEN LEN(RTRIM(DH.DSP_LORRY_NO)) <= 7 THEN LEFT(CAST(RTRIM(DH.DSP_LORRY_NO) AS varchar(15))+replicate('-',15),15)");
			sb.append(" ELSE SUBSTRING( DH.DSP_LORRY_NO , 1 , 15 ) END VehNo,'R' VehType,dh.DSPFSTAFF_ADDR1 ExpShipDtlsAddr1,dh.DSPFSTAFF_ADDR2");
			sb.append(" ExpShipDtlsAddr2,dh.DSPFSTAFF_DESTINATION ExpShipDtlsLoc,right(rtrim(dh.dspfstaff_addr4),6) ExpShipDtlsPin,");
			sb.append(" fst.SGprmdet_Text1 ExpShipDtlsStcd,E.TRN_NUMBER as TRN_NUMBER FROM E_INVOICE_HEADER_STOCKIST E ,");
			sb.append(" DEPOT_LOCATIONS DL , DISPATCH_HEADER DH ");
			sb.append(" INNER JOIN ARTICLE_SCHREQ_HDR ART on ART.ARTICLE_REQ_ID = DH.BLK_HCP_REQ_ID");
			sb.append(" INNER JOIN CUSTOMER_MASTER CU ON CU.CUST_ID = DH.CUST_ID");
			sb.append(" LEFT OUTER JOIN SG_d_parameters_details  fst on");
			sb.append(" fst.SGprmdet_id = CU.STATE_ID_SHIPTO WHERE DH.DSP_LOC_ID = :locId AND E.TRN_ID = DH.DSP_ID");
			sb.append(" AND DH.DSP_RECVG_LOC_ID = DL.DPTLOC_ID AND E.TRN_ID>=:frmtrnId and E.TRN_ID<=:totrnId AND E.TRN_EWAYBILLNO is null");
			sb.append(" AND E.FIN_YEAR_ID=:finyrId AND E.DIV_ID=:divId");
			
			System.out.println("qury:" + sb.toString());
			Query qry = em.createNativeQuery(sb.toString());
			qry.setParameter("locId", loc_id);
			qry.setParameter("finyrId", finYear);
			qry.setParameter("frmtrnId", fromTrnId);
			qry.setParameter("totrnId", toTrnId);
			qry.setParameter("divId", divId);
			results = qry.getResultList();
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			throw e;
		} finally {
			if (em != null)
				em.close();
		}
		// SQLQuery sqlQuery=session.createSQLQuery(sb.toString());
		// List<Object> results = sqlQuery.list();
		// session.close();
		return results;
	}

	@Override
	public List<Object> getIrnCancelDataStockist(Long loc_id, String fromTrnId, String toTrnId, String divId,
			String finyearflag, String fin_year) throws Exception {
		StringBuffer sb = new StringBuffer();

		if (finyearflag.equals(MedicoConstants.CURRENT)) {
			sb.append(" select TRN_IRN_NUMBER,TRN_NUMBER from E_INVOICE_HEADER_stockist eh");
			sb.append(" inner join DISPATCH_HEADER DH on DH.DSP_ID = EH.TRN_ID");
			sb.append(" where DSP_LOC_ID = :loc_id AND TRN_ID>=:frmTrnId and TRN_ID<=:toTrnId");
			sb.append(" AND DIV_ID=:divId AND  IRN_CANCEL='N' order by TRN_ID asc");
		} else if (finyearflag.equals(MedicoConstants.PREVIOUS)) {
			sb.append("select TRN_IRN_NUMBER,TRN_NUMBER from E_INVOICE_HEADER_arc_stockist eh");
			sb.append("inner join DISPATCH_HEADER_ARC DH on DH.DSP_ID = EH.TRN_ID");
			sb.append("where DSP_LOC_ID = :loc_id AND DSP_FIN_YEAR = :finYear");
			sb.append("AND TRN_ID>=:frmTrnId and TRN_ID<=:toTrnId");
			sb.append("AND DIV_ID=:divId AND  IRN_CANCEL='N' order by TRN_ID asc");
		}

		Query query = entityManager.createNativeQuery(sb.toString());
		query.setParameter("frmTrnId", fromTrnId);
		query.setParameter("toTrnId", toTrnId);
		query.setParameter("divId", divId);
		query.setParameter("loc_id", loc_id);

		if (finyearflag.equals(MedicoConstants.PREVIOUS)) {
			query.setParameter("finYear", fin_year);
		}
		List<Object> results = query.getResultList();
		return results;
	}

	@Override
	public List<Dispatch_Header> getChallanDetailsStockist(String finYear, String divId, String finyearflag,
			Long loc_id) throws Exception {
		// TODO Auto-generated method stub
		List<Dispatch_Header> listhd = new ArrayList<Dispatch_Header>();
		try {
			SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
			System.out.println("finyrId" + finYear);
			System.out.println("divId" + divId);

			StringBuffer sb = new StringBuffer();
			if (finyearflag.equals(MedicoConstants.CURRENT)) {
				sb.append(
						" SELECT DSP_ID,DSP_CHALLAN_NO,FORMAT( DSP_CHALLAN_DT,'dd/MM/yyyy') DSP_CHALLAN_DT,DSP_DIV_ID,E_INV_REQ");
				sb.append(" FROM ( ");
				sb.append(" SELECT DSP_ID,DSP_CHALLAN_NO,DSP_CHALLAN_DT,EINV.TRN_ID,DSP_DIV_ID,E_INV_REQ ");
				sb.append(
						" FROM DISPATCH_HEADER DH LEFT OUTER JOIN E_INVOICE_HEADER_STOCKIST EINV ON EINV.TRN_ID = DH.DSP_ID");
				sb.append(" AND EINV.FIN_YEAR_ID = DH.DSP_FIN_YEAR ");
				sb.append(" WHERE DSP_FIN_YEAR =:finyrId ");
				sb.append(" AND CONVERT( DATE , DH.DSP_CHALLAN_DT ) >= CONVERT( DATE , '2020-11-01' ) ");
				//sb.append(" AND DH.DSP_LR_NO IS NOT NULL AND DH.DSP_LR_DT IS NOT NULL and DH.DSP_LOC_ID = :loc_id");
				//sb.append(" AND DH.DSP_TRANS_GST_REG_NO IS NOT NULL");
				sb.append(" AND DH.DSP_TRANSPORTER IS NOT NULL and DH.DSP_LOC_ID = :loc_id and DH.DSP_APPR_STATUS='A'");
				sb.append(" AND DSP_STATUS <> 'I') D ");
				sb.append(" WHERE TRN_ID IS NULL AND DSP_DIV_ID=:divId AND E_INV_REQ='Y' ");
				sb.append(" ORDER BY DSP_CHALLAN_DT,DSP_CHALLAN_NO ");
			} else if (finyearflag.equals(MedicoConstants.PREVIOUS)) {
				sb.append(
						" SELECT DSP_ID,DSP_CHALLAN_NO,FORMAT( DSP_CHALLAN_DT,'dd/MM/yyyy') DSP_CHALLAN_DT,DSP_DIV_ID,E_INV_REQ");
				sb.append(" FROM ( ");
				sb.append(" SELECT DSP_ID,DSP_CHALLAN_NO,DSP_CHALLAN_DT,EINV.TRN_ID,DSP_DIV_ID,E_INV_REQ ");
				sb.append(
						" FROM DISPATCH_HEADER_ARC DH LEFT OUTER JOIN E_INVOICE_HEADER_arc_stockist EINV ON EINV.TRN_ID = DH.DSP_ID");
				sb.append("  AND EINV.FIN_YEAR_ID = DH.DSP_FIN_YEAR ");
				sb.append(" WHERE DSP_FIN_YEAR =:finyrId ");
				sb.append(" AND CONVERT( DATE , DH.DSP_CHALLAN_DT ) >= CONVERT( DATE , '2020-11-01' ) ");
				//sb.append(" AND DH.DSP_LR_NO IS NOT NULL AND DH.DSP_LR_DT IS NOT NULL and DH.DSP_LOC_ID = :loc_id");
				//sb.append(" AND DH.DSP_TRANS_GST_REG_NO IS NOT NULL");
				sb.append(" AND DH.DSP_TRANSPORTER IS NOT NULL and DH.DSP_LOC_ID = :loc_id and DH.DSP_APPR_STATUS='A'");
		
				sb.append(" AND DSP_STATUS <> 'I') D ");
				sb.append(" WHERE TRN_ID IS NULL AND DSP_DIV_ID=:divId AND E_INV_REQ='Y' ");
				sb.append(" ORDER BY DSP_CHALLAN_DT,DSP_CHALLAN_NO ");
			}

			System.out.println("qury:" + sb.toString());
			Query qry = entityManager.createNativeQuery(sb.toString(), Tuple.class);
			qry.setParameter("finyrId", finYear);
			qry.setParameter("divId", divId);
			qry.setParameter("loc_id", loc_id);
			List<Tuple> results = qry.getResultList();

			if (results != null && results.size() > 0) {
				Dispatch_Header hd = new Dispatch_Header();
				for (Tuple t : results) {
					hd = new Dispatch_Header();
					hd.setDsp_id(t.get("DSP_ID", Integer.class).longValue());
					hd.setDspChallanNo(t.get("DSP_CHALLAN_NO", String.class));
					hd.setDsp_challan_dt(sdf.parse(t.get("DSP_CHALLAN_DT", String.class)));
					// hd.setSumdspdiv_id(t.get("SUMDSP_DIV_ID",BigDecimal.class).longValue());

					listhd.add(hd);
				}

				System.out.println("list : " + listhd.size());
			}
		} catch (Exception e) {
			// TODO: handle exception
			throw e;
		}
		return listhd;
	}

	@Override
	public List<EInvoiceHeaderStockist> getIrnChallansWithoutEwayStockist(String finYear, String divId,
			String finYearFlag, Long loc_id) throws Exception {
		List<EInvoiceHeaderStockist> hdr = new ArrayList<EInvoiceHeaderStockist>();
		try {
			StringBuffer sb = new StringBuffer();
			sb.append(" select eh.trn_id,eh.trn_number from E_INVOICE_HEADER_STOCKIST eh");
			sb.append(" inner join DISPATCH_HEADER dsp on dsp.DSP_ID = eh.TRN_ID");
			sb.append(" where dsp.DSP_LOC_ID = :loc_id");
			sb.append(" and eh.fin_year_id=:finyrId");
			sb.append(" and eh.irn_cancel='N'");
			sb.append(" AND eh.trn_ewaybillno is null");
			sb.append(" AND eh.div_id=:divId");
			sb.append(" order by eh.trn_id asc");

			Query qry = entityManager.createNativeQuery(sb.toString(), Tuple.class);
			qry.setParameter("finyrId", Long.valueOf(finYear));
			qry.setParameter("divId", Long.valueOf(divId));
			qry.setParameter("loc_id", loc_id);
			List<Tuple> list = qry.getResultList();

			if (list != null & list.size() > 0) {
				EInvoiceHeaderStockist hd = new EInvoiceHeaderStockist();
				for (Tuple t : list) {
					hd = new EInvoiceHeaderStockist();
					hd.setTrn_id(t.get("trn_id", BigDecimal.class).longValue());
					hd.setTrn_number(t.get("trn_number", String.class));
					hdr.add(hd);
				}
			}
		} catch (Exception e) {
			// TODO: handle exception
			throw e;
		}
		return hdr;
	}

	@Override
	public List<EInvoiceHeaderStockist> getIrnGencancelDataStockist(String finYear, String divId, Long loc_id)
			throws Exception {
		List<EInvoiceHeaderStockist> list = new ArrayList<EInvoiceHeaderStockist>();
		try {
			StringBuffer sb = new StringBuffer();
			sb.append(" select TRN_ID,TRN_NUMBER from E_INVOICE_HEADER_STOCKIST eh");
			sb.append(" inner join DISPATCH_HEADER dsp on dsp.dsp_id = eh.TRN_ID");
			sb.append(" where dsp.DSP_LOC_ID = :loc_id");
			sb.append(" and eh.FIN_YEAR_ID=:finyrId and eh.IRN_CANCEL='N' and");
			sb.append(" (eh.EWAY_BILL_CANCEL='Y' OR  eh.TRN_EWAYBILLNO is null)");
			sb.append(" AND eh.DIV_ID=:divId order by eh.TRN_ID asc");
			Query qry = entityManager.createNativeQuery(sb.toString(), Tuple.class);
			qry.setParameter("finyrId", Long.valueOf(finYear));
			qry.setParameter("divId", Long.valueOf(divId));
			qry.setParameter("loc_id", loc_id);
			List<Tuple> tlist = qry.getResultList();

			if (tlist != null & tlist.size() > 0) {
				EInvoiceHeaderStockist hd = new EInvoiceHeaderStockist();
				for (Tuple t : tlist) {
					hd = new EInvoiceHeaderStockist();
					hd.setTrn_id(t.get("TRN_ID", BigDecimal.class).longValue());
					hd.setTrn_number(t.get("TRN_NUMBER", String.class));
					list.add(hd);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
		return list;
	}

	@Override
	public List<EInvoiceHeaderStockist> getEwayBillCancelStockist(String finYear, String divId, String finYearFlag,
			Long loc_id) {
		// TODO Auto-generated method stub
		List<EInvoiceHeaderStockist> list = new ArrayList<EInvoiceHeaderStockist>();
		try {
			StringBuffer sb = new StringBuffer();
			
			if (finYearFlag.trim().equals(MedicoConstants.CURRENT)) {
				sb.append(" select TRN_ID,TRN_NUMBER,TRN_EWAYBILLNO from E_INVOICE_HEADER_STOCKIST eh");
				sb.append(" inner join DISPATCH_HEADER dsp on dsp.dsp_id = eh.TRN_ID");
				sb.append(" where dsp.DSP_LOC_ID = :loc_id");
				sb.append(" and eh.FIN_YEAR_ID=:finyear");
				sb.append(" and eh.EWAY_BILL_CANCEL='N'");
				sb.append(" and eh.DIV_ID=:divId");
				sb.append(" AND eh.TRN_EWAYBILLNO is not null");
				sb.append(" order by eh.TRN_ID asc");
			} else if (finYearFlag.trim().equals(MedicoConstants.PREVIOUS)) {
				sb.append(" select TRN_ID,TRN_NUMBER,TRN_EWAYBILLNO from E_INVOICE_HEADER_ARC_STOCKIST eh");
				sb.append(" inner join DISPATCH_HEADER_ARC dsp on dsp.dsp_id = eh.TRN_ID");
				sb.append(" where dsp.DSP_LOC_ID = :loc_id");
				sb.append(" and eh.FIN_YEAR_ID=:finyear");
				sb.append(" and eh.EWAY_BILL_CANCEL='N'");
				sb.append(" and eh.DIV_ID=:divId");
				sb.append(" AND eh.TRN_EWAYBILLNO is not null");
				sb.append(" order by eh.TRN_ID asc");
			}
			
			Query qry = entityManager.createNativeQuery(sb.toString(), Tuple.class);
			qry.setParameter("finyear", Long.valueOf(finYear));
			qry.setParameter("divId", Long.valueOf(divId));
			qry.setParameter("loc_id", loc_id);
			List<Tuple> tlist = qry.getResultList();
			if (tlist != null & tlist.size() > 0) {
				EInvoiceHeaderStockist hd = new EInvoiceHeaderStockist();
				for (Tuple t : tlist) {
					hd = new EInvoiceHeaderStockist();
					hd.setTrn_id(t.get("TRN_ID", BigDecimal.class).longValue());
					hd.setTrn_number(t.get("TRN_NUMBER", String.class));
					hd.setTrn_ewaybillno(t.get("TRN_EWAYBILLNO", String.class));
					list.add(hd);
				}
			}
		} catch (Exception e) {
			// TODO: handle exception
			throw e;
		}
		return list;
	}

	@Override
	public List<E_invoice_report> getE_invoiceReportData(String div_id,String start_dt,String end_dt)throws Exception{
		

		// TODO Auto-generated method stub
		EntityManager em = null;
		List<E_invoice_report> list = null;
		try {
			em = emf.createEntityManager();
			StoredProcedureQuery callEInvSumChlnStk = em
					.createNamedStoredProcedureQuery("calle_invoice_data_medley");
			callEInvSumChlnStk.setParameter("div_id", div_id);
			callEInvSumChlnStk.setParameter("trf_start_dt", start_dt);
			callEInvSumChlnStk.setParameter("trf_end_dt", end_dt);
			list = callEInvSumChlnStk.getResultList();
		} catch (Exception e) {
			// TODO: handle exception
			throw e;
		}
		return list;
	}
	

}
