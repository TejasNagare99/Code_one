package com.excel.repository;

import java.math.BigDecimal;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.StoredProcedureQuery;
import javax.persistence.Tuple;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.excel.bean.LrEntryBean;
import com.excel.bean.Lr_download_Execl_Bean;
import com.excel.bean.TRF_BEAN_stocktransfer_download_trdata;
import com.excel.bean.TransporterBean;
import com.excel.model.Company;
import com.excel.model.Location;
import com.excel.model.Lr_csv_upload_java_proc_recised_filter;
import com.excel.model.Lrcsv_RevisedDownload;
import com.excel.model.Lrcsv_RevisedDownload_SG;
import com.excel.model.Transporter_master;
import com.excel.service.CompanyMasterService;
import com.excel.utility.CodifyErrors;

@Repository
public class LrEntryRepositoryImpl implements LrEntryRepository {

	@Autowired
	private EntityManagerFactory emf;
	
	@Autowired
	private CompanyMasterService companyMasterService;
	
	@PersistenceContext
	private EntityManager entityManager;

	@Override
	public List<Lrcsv_RevisedDownload> getLrcsvdownload_Revised_data(String strdate, String enddate, String tbl_ind,
			String cfa, String fsid, String stock_at_cfa_ind, String stk_at_cfa_option, String temp, String deptloc,
			List<String> loc, List<String> div, List<String> dest, String cfa_to_stk_ind) throws Exception {

		List<Lrcsv_RevisedDownload> list = null;
		String procedureToCall = "";
		EntityManager em = null;

		// exec LR_CSV_UPLOAD_PROC_JAVA_REVISED '2020/01/01','2020/10/10','A','0','0'
		try {

			System.out.println("loc :: " + loc.size());
			System.out.println("div :: " + div.size());
			System.out.println("dest :: " + dest.size());

			StringBuffer locations = new StringBuffer();
			for (String a : loc) {
				locations.append(a + ",");
			}
			// System.out.println("locations :: "+locations.toString());
			String location = locations.toString().substring(0, locations.toString().length() - 1);
			System.out.println("locations :: " + location);

			StringBuffer divisions = new StringBuffer();
			for (String a : div) {
				divisions.append(a + ",");
			}
			// System.out.println("locations :: "+locations.toString());
			String division = divisions.toString().substring(0, divisions.toString().length() - 1);
			System.out.println("divisions :: " + division);

			StringBuffer destinations = new StringBuffer();
			for (String a : dest) {
				destinations.append(a + ",");
			}
			// System.out.println("locations :: "+locations.toString());
			String destination = destinations.toString().substring(0, destinations.toString().length() - 1);
			System.out.println("divisions :: " + destination);

			tbl_ind = "A";
			System.out.println("stk_at_cfa_option::" + stk_at_cfa_option);
			System.out.println("stock_at_cfa_ind::" + stock_at_cfa_ind);
			System.out.println("start" + strdate);
			System.out.println("start" + strdate);
//			 strdate = "2020/05/01";
//			 enddate = "2020/05/10";
			if (stk_at_cfa_option == null) {
				stk_at_cfa_option = "N";
			}
			System.out.println("stk_at_cfa_option::" + stk_at_cfa_option);
			if (stk_at_cfa_option.equalsIgnoreCase("Y") && stock_at_cfa_ind.equalsIgnoreCase("Y")) {
				procedureToCall = "call_LR_csv_download_revised_stock_cfa";
			} else if (cfa_to_stk_ind.equalsIgnoreCase("Y")) {
				procedureToCall = "call_LR_csv_revised_download_stockist";
			} else {
				procedureToCall = "call_LR_csv_revised_download";
			}

			System.out.println("tbl_ind::::" + tbl_ind + "::::strdate::::" + strdate + "::::enddate::::" + enddate
					+ "::::cfa::::" + cfa + "::::fsid::::" + fsid);

			em = emf.createEntityManager();

			StoredProcedureQuery procedurecall = em.createNamedStoredProcedureQuery(procedureToCall);
			procedurecall.setParameter("startdt", strdate);
			procedurecall.setParameter("enddt", enddate);
			procedurecall.setParameter("tabl_ind", tbl_ind);

			if (!deptloc.trim().equals("0") && cfa.equals("0")) {
				procedurecall.setParameter("cfa", temp.toString());
			} else {

				if (deptloc.trim().equals("0") && cfa.equals("0")) {
					procedurecall.setParameter("cfa", cfa);
				} else {
					procedurecall.setParameter("cfa", "(" + cfa + ")");
				}
			}
			procedurecall.setParameter("fsid", fsid);

			procedurecall.setParameter("div_id", division);
			procedurecall.setParameter("sumdisp_id", destination);
			procedurecall.setParameter("sumdsp_loc_id", location);

			list = procedurecall.getResultList();
			System.out.println("List :: " + list.size());

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
	public List<Transporter_master> gettransportermaster() throws Exception {
		// TODO Auto-generated method stub
		List<Transporter_master> list = null;
		EntityManager em = null;
		try {
			em = emf.createEntityManager();

			StringBuffer sb = new StringBuffer();

			sb.append(" select TRANSPORTER_NAME from Transporter_master where trans_status='A'");
			Query query = em.createNativeQuery(sb.toString(), Tuple.class);
			List<Tuple> tuples = query.getResultList();
			if (tuples != null && !tuples.isEmpty()) {
				list = new ArrayList<>();
				Transporter_master smp = null;
				for (Tuple t : tuples) {
					smp = new Transporter_master();
					smp.setTransporter_name(t.get("TRANSPORTER_NAME", String.class));
					list.add(smp);
				}
			}
		} catch (Exception e) {
			// TODO: handle exception
			throw e;
		}
		return list;
	}



	@Override
	public List<Transporter_master> gettransportermaster_new() throws Exception {
		// TODO Auto-generated method stub
		List<Transporter_master> list = null;
		 EntityManager em = null;
		try {
			   em = emf.createEntityManager();
		
			
			StringBuffer sb = new StringBuffer();
		
		//sb.append(" select TRANS_ID,TRANSPORTER_NAME,TRANSPORTER_ALTER_NAME from Transporter_master where trans_status='A'");
		sb.append(" select TRANS_ID,TRANSPORTER_NAME,TRANSPORTER_ALTER_NAME,TRANS_PERS_RESP1,TRANS_PERS_RESP2,TRANS_EMAIL1,TRANS_EMAIL2,"
					+ "TRANS_CONTACT_NO1,TRANS_CONTACT_NO2,TRANS_WEBSITE_NAME,TRANS_GST_REG_NO from Transporter_master where trans_status='A' order by TRANSPORTER_NAME asc");
		Query query = em.createNativeQuery(sb.toString(), Tuple.class);
		List<Tuple> tuples = query.getResultList();
		if (tuples != null && !tuples.isEmpty()) {
			list = new ArrayList<>();
			Transporter_master smp = null;
			for (Tuple t : tuples) {
				smp = new Transporter_master();
				smp.setTrans_id(Long.valueOf(t.get("TRANS_ID",Integer.class)));
				smp.setTransporter_name(t.get("TRANSPORTER_NAME",String.class));
				smp.setTransporter_alter_name(t.get("TRANSPORTER_ALTER_NAME",String.class));
				smp.setTrans_pers_resp1(t.get("TRANS_PERS_RESP1",String.class));
				smp.setTrans_pers_resp2(t.get("TRANS_PERS_RESP2",String.class));
				smp.setTrans_email1(t.get("TRANS_EMAIL1",String.class));
				smp.setTrans_email2(t.get("TRANS_EMAIL2",String.class));
				smp.setTrans_contact_no1(t.get("TRANS_CONTACT_NO1",String.class));
				smp.setTrans_contact_no2(t.get("TRANS_CONTACT_NO2",String.class));
				smp.setTrans_website_name(t.get("TRANS_WEBSITE_NAME",String.class));
				smp.setTrans_gst_reg_no(t.get("TRANS_GST_REG_NO",String.class));
				list.add(smp);
			}
		}	
		}catch (Exception e) {
			// TODO: handle exception
			throw e;
		}
		return list;
	}

	
	
	
	@Override
	public Transporter_master getByObjectId(Long trans_id) throws Exception {
		return this.entityManager.find(Transporter_master.class, trans_id);
	}

	@Override
	public List<Tuple> getlistsize(TransporterBean transBean) throws Exception {
		EntityManager em = null;
		List<Tuple> list = null;
		try {
			em = emf.createEntityManager();

			StringBuffer sb = new StringBuffer();

			sb.append(
					" select TRANSPORTER_NAME from Transporter_master where trans_status='A' and TRANSPORTER_NAME =:transporter");

			Query query = em.createNativeQuery(sb.toString(), Tuple.class);
			query.setParameter("transporter", transBean.getTranName());
			list = query.getResultList();

		} finally {
			if (em != null) {
				em.close();
			}
		}
		return list;
	}

	@Override
	public List<Lr_csv_upload_java_proc_recised_filter> getLrcsvdownload_Revised_filter(String strdate, String enddate,
			String tbl_ind, String cfa, String fsid, String stock_at_cfa_ind, String stk_at_cfa_option, String temp,
			String deptloc, String cfa_to_stk_ind) throws Exception { // TODO Auto-generated method stub
		List<Lr_csv_upload_java_proc_recised_filter> list = null;
		EntityManager em = null;
		try {
			tbl_ind = "A";
			if (stk_at_cfa_option == null) {
				stk_at_cfa_option = "N";
			}

			em = emf.createEntityManager();
			StoredProcedureQuery procedurecall = null;

			if (cfa_to_stk_ind.equalsIgnoreCase("Y")) {
				procedurecall = em.createNamedStoredProcedureQuery("call_LR_csv_download_revised_filter_stockist");
			} else {
				procedurecall = em.createNamedStoredProcedureQuery("call_LR_csv_download_revised_filter");
			}

			procedurecall.setParameter("startdt", strdate);
			procedurecall.setParameter("enddt", enddate);
			procedurecall.setParameter("tabl_ind", tbl_ind);
			if (!deptloc.trim().equals("0") && cfa.equals("0")) {
				procedurecall.setParameter("cfa", temp.toString());
			} else {

				if (deptloc.trim().equals("0") && cfa.equals("0")) {
					procedurecall.setParameter("cfa", cfa);
				} else {
					procedurecall.setParameter("cfa", "(" + cfa + ")");
				}
			}
			procedurecall.setParameter("fsid", fsid);

			list = procedurecall.getResultList();
			System.out.println("List :: " + list.size());

		} catch (Exception e) {
			// TODO: handle exception
			System.out.println("Error Occurred :" + e.getMessage());// uncomment asneeded --;
		}
		return list;
	}

	@Override
	public Location getLocationNameByEmployeeId(String eMP_ID) {

		EntityManager em = null;
		Location location = null;
		try {
			em = emf.createEntityManager();
			StringBuffer sb = new StringBuffer();
			sb.append(
					" select l.loc_nm AS loc_nm, l.loc_subcomp_id AS loc_subcomp_id, l.loc_id AS loc_id,l.loc_state_id As loc_state_id from Location l ");
			sb.append(" INNER JOIN EmployeeDetails e on e.emp_loc_id = l.loc_id ");
			sb.append(" WHERE e.emp_id = :emp_id ");
			Query query = em.createQuery(sb.toString(), Tuple.class);
			query.setParameter("emp_id", eMP_ID);
			List<Tuple> tuples = query.getResultList();

			if (tuples != null && !tuples.isEmpty()) {
				for (Tuple t : tuples) {
					location = new Location();
					location.setLoc_nm(t.get("loc_nm", String.class));
					location.setLoc_subcomp_id(t.get("loc_subcomp_id", Long.class));
					location.setLoc_id(t.get("loc_id", Long.class));
					location.setLoc_state_id(t.get("loc_state_id", Long.class));
				}
				if (location != null)
					return location;
			}
		} finally {
			if (em != null) {
				em.close();
			}
		}
		return location;

	}

	@Override
	public List<Lr_download_Execl_Bean> getDataForGenExel(List<String> trf_ids) {
		StringBuffer trf_id = null;

		// for adding the ',' in the id list to put in query
		if (trf_ids.size() == 1) {
			trf_id = new StringBuffer();
			trf_id.append(trf_ids.get(0));
		} else if (trf_ids.size() > 1) {
			trf_id = new StringBuffer();
			for (int i = 0; i <= trf_ids.size() - 1; i++) {
				if ("0".equals(trf_ids.get(i).toString())) {
					trf_id = new StringBuffer("0");
				} else {
					if (i != trf_ids.size() - 1) {
						trf_id.append(trf_ids.get(i) + ",");
					} else {
						trf_id.append(trf_ids.get(i));
					}
				}

			}
		}

		System.err.println(trf_ids + "reclocations ");
		EntityManager em = null;
		em = emf.createEntityManager();
		StringBuffer sb = null;
		Query query = null;
		List<Lr_download_Execl_Bean> dataForGenExcel = new ArrayList<>();

		try {

			sb = new StringBuffer();

			sb.append(
					"select TRANSPORTER_NAME,  sh.TRF_ID AS TRF_ID, sd.TRF_SL_NO AS TRF_SL_NO,sh.LOC_ID AS LOC_ID,sh.RECEIVING_LOC_ID AS RECEIVING_LOC_ID,");
			sb.append(
					" sh.TRF_NO AS TRF_NO,sh.TRF_DATE AS TRF_DATE,sm.SMP_PROD_CD AS SMP_PROD_CD,sm.SMP_PROD_ID AS SMP_PROD_ID,sb.BATCH_ID AS BATCH_ID,");
			sb.append(
					" sb.BATCH_NO AS BATCH_NO,sb.BATCH_MFG_DT AS BATCH_MFG_DT,sb.BATCH_EXPIRY_DT AS BATCH_EXPIRY_DT,sd.SOLD_QTY AS SOLD_QTY,");
			sb.append(" sd.CASES AS CASES,sd.FULL_SHIPPERS AS FULL_SHIPPERS,sd.RATE AS RATE,");
			sb.append(
					" sh.LR_NO AS LR_NO,sh.LR_DATE AS LR_DATE,sl.loc_map_cd as sending_map_cd, rl.loc_map_cd  as receving_loc_map_cd");
			sb.append(" from  STOCK_TRANSFER_HEADER sh inner join STOCK_TRANSFER_DETAILS sd on sh.TRF_ID = sd.TRF_ID");
			sb.append(" inner join SMPITEM sm on sd.PROD_ID = sm.SMP_PROD_ID");
			sb.append(" inner join SMPBatch sb on sd.BATCH_ID = sb.BATCH_ID");
			sb.append(" inner join location sl on sl.loc_id=sh.LOC_ID");
			sb.append(" inner join location rl on rl.loc_id =sh.RECEIVING_LOC_ID");
			sb.append(" where sh.TRF_ID in(" + trf_id.toString() + ")");

//			sb.append("	select sh.TRF_ID,sd.TRF_SL_NO,sh.LOC_ID,sh.RECEIVING_LOC_ID,sh.TRF_NO,sh.TRF_DATE,");
//			sb.append("	sm.SMP_PROD_CD,sm.SMP_PROD_ID,sb.BATCH_ID,sb.BATCH_ERP_BATCH_CD,sb.BATCH_MFG_DT,");
//			sb.append(" sb.BATCH_EXPIRY_DT,sd.SOLD_QTY,sd.CASES,sd.FULL_SHIPPERS,sd.RATE,sh.LR_NO,sh.LR_DATE,"
//					+ "sl.loc_map_cd as sending_map_cd,\r\n" + " rl.loc_map_cd  as receving_loc_map_cd");
//			sb.append("	from  STOCK_TRANSFER_HEADER sh inner join STOCK_TRANSFER_DETAILS sd on sh.TRF_ID = sd.TRF_ID");
//			sb.append(
//					" inner join SMPITEM sm on sd.PROD_ID = sm.SMP_PROD_ID inner join SMPBatch sb on sd.BATCH_ID = sb.BATCH_ID");
//			sb.append(
//					" inner join location sl on sl.loc_id=sh.LOC_ID	inner join location rl on rl.loc_id =sh.RECEIVING_LOC_ID");
//			sb.append("	where sh.TRF_ID in(" + trf_id + ")");

			query = em.createNativeQuery(sb.toString(), Tuple.class);

			List<Tuple> list = query.getResultList();

			list.forEach(l -> {
				Lr_download_Execl_Bean bean = new Lr_download_Execl_Bean();

				bean.setCSPTRF_ID((l.get("TRF_ID", Integer.class)));

				bean.setCSPTRF_SLNO(Long.valueOf(l.get("TRF_SL_NO", Integer.class)));
				bean.setCSPTRF_SENDING_LOC((l.get("LOC_ID", BigDecimal.class).toString()));

				bean.setCSPTRF_RECV_LOC((l.get("RECEIVING_LOC_ID", BigDecimal.class).toString()));

				bean.setCSPTRF_TRF_NO((l.get("TRF_NO", String.class).toString()));
				bean.setCSPTRF_TRF_DATE((l.get("TRF_DATE", Date.class).toString()));
				bean.setCSPTRF_PROD_CD((l.get("SMP_PROD_CD", String.class).toString()));

				bean.setCSPTRF_BATCH_NO((l.get("BATCH_NO", String.class) == null) ? "" : (l.get(9).toString()));
				bean.setCSPTRF_MFGDT((l.get("BATCH_MFG_DT", Date.class).toString()));
				bean.setCSPTRF_EXPIRY_DT((l.get("BATCH_EXPIRY_DT", Date.class).toString()));

				bean.setCSPTRF_QTY(new BigDecimal(l.get("SOLD_QTY", BigDecimal.class).toString()));

				bean.setCSPTRF_NOCASES(
						(l.get("CASES", String.class) == null) ? 0 : Long.valueOf(l.get("CASES", String.class)));

				bean.setCSPTRF_FULLSHIPPER((l.get("FULL_SHIPPERS", String.class) == null) ? 0
						: Long.valueOf(l.get("FULL_SHIPPERS", String.class).toString()));
				bean.setCSPTRF_TRFRATE(new BigDecimal(l.get("RATE", BigDecimal.class).toString()));
				bean.setCSPTRF_LRNO((l.get("LR_NO", String.class).toString()));
				bean.setCSPTRF_LR_DATE((l.get("LR_DATE", Date.class).toString()));
				bean.setCSPTRF_SENDLOC_CD((l.get("sending_map_cd", String.class).toString()));
				bean.setCSPTRF_RECVLOC_CD((l.get("receving_loc_map_cd", String.class).toString()));
				bean.setTRANSPORTER_NAME(l.get("TRANSPORTER_NAME", String.class));

				dataForGenExcel.add(bean);

			});

//			
			dataForGenExcel.forEach(l -> {
				System.err.println(l);
			});

		} catch (Exception e) {
			e.printStackTrace();
			// TODO: handle exception
		} finally {
			if (em != null) {
				em.close();
			}
		}

		return dataForGenExcel;
	}

	@Override
	public List<Tuple> getRecevingLocations(String startDate, String endDate, String location_id, String ind,String finyear_flage) {

		System.out.println("ind :::::::::" + ind);

		List<Tuple> list = null;
		EntityManager em = null;
		try {
			em = emf.createEntityManager();

			/* lrInd */

//			sb.append(" select lc.loc_id , lc.loc_nm from location lc where lc.loc_id in (\r\n"
//					+ "select distinct(receiving_loc_id) from STOCK_TRANSFER_HEADER  where loc_id =:loc and TRFHDR_status = 'A'\r\n"
//					+ "and CONVERT(VARCHAR,TRF_DATE, 103) >=:statDate\r\n"
//					+ "and CONVERT(VARCHAR,TRF_DATE, 103) <= :endDate\r\n" + ")");ewqeqweqw

			
			String table_nanme="";
			if(finyear_flage.equals("CURRENT"))
			{
				table_nanme="STOCK_TRANSFER_HEADER";
			}else {
				table_nanme="STOCK_TRANSFER_HEADER_ARC";
			}
			
			
			StringBuffer sb = null;
			if (ind.equals("E")) {
				sb = new StringBuffer();
				sb.append("select distinct(lc.loc_id),lc.loc_nm from "+table_nanme+"  sh \r\n"
						+ "inner join location lc on lc.loc_id = sh.RECEIVING_LOC_ID \r\n" + "where sh.LOC_ID=:loc \r\n"
						+ "and CONVERT(VARCHAR(10),TRF_DATE, 103) >=:statDate \r\n"
						+ "and CONVERT(VARCHAR(10),TRF_DATE, 103) <= :endDate \r\n" + "and LR_NO is   null");
			} else if (ind.equals("D")) {
				sb = new StringBuffer();
				sb.append("select distinct(lc.loc_id),lc.loc_nm from "+table_nanme+" sh \r\n"
						+ "inner join location lc on lc.loc_id = sh.RECEIVING_LOC_ID \r\n" + "where sh.LOC_ID=:loc \r\n"
						+ "and CONVERT(VARCHAR(10),TRF_DATE, 103) >=:statDate \r\n"
						+ "and CONVERT(VARCHAR(10),TRF_DATE, 103) <= :endDate \r\n" + "and LR_NO is not null");
			}

			Query query = em.createNativeQuery(sb.toString(), Tuple.class);
			query.setParameter("loc", location_id);
			query.setParameter("statDate", startDate);
			query.setParameter("endDate", endDate);

			list = query.getResultList();

			list.forEach(l -> {
				System.err.println(l.get(0) + ":::::" + l.get(1));
			});

		} catch (Exception e) {
			// TODO: handle exception
			throw e;
		} finally {
			if (em != null) {
				em.close();
			}
		}
		return list;
	}

	@Override
	public List<Tuple> getDataForStockkTransferLrEntry(String startDate, String endDate, StringBuffer reclocations,
			String sendLocation, String finyear_flage) {

		List<Tuple> list = null;
		EntityManager em = null;

		try {
			em = emf.createEntityManager();

			StringBuffer sb = new StringBuffer();
			StringBuffer zero = new StringBuffer("0");

			Query query = null;

			System.err.println(zero + "::::::::::" + reclocations);

			System.err.println(zero.toString().equals(reclocations.toString()));

			
			String table_name="";
			if(finyear_flage.equals("CURRENT"))table_name="STOCK_TRANSFER_HEADER"; else table_name="STOCK_TRANSFER_HEADER_ARC";
			
			if (zero.toString().equals(reclocations.toString())) {

				sb.append("select   trf_id,trf_no,TRF_DATE,isnull(lr_no,'') LR_no,\r\n"
						+ "--isnull(lr_DATE,CONVERT(VARCHAR(10),GETDATE(),103)) LR_DATE,\r\n"
						+ "CONVERT(VARCHAR,isnull(lr_DATE,GETDATE()),103) LR_DATE,\r\n"
						+ "ISNULL(TRANSPORTER_NAME,'') TRANSPORTER_NAME\r\n" + "from    "+table_name+"   \r\n" + "\r\n"
						+ "where loc_id=:loc");

				query = em.createNativeQuery(sb.toString(), Tuple.class);
				query.setParameter("loc", sendLocation);
			} else {

				sb.append("select  trf_id,trf_no,TRF_DATE,isnull(lr_no,'') LR_no,\r\n"
						+ "CONVERT(VARCHAR,isnull(lr_DATE,GETDATE()),103) LR_DATE,\r\n"
						+ "ISNULL(TRANSPORTER_NAME,'') TRANSPORTER_NAME\r\n" + "from    "+table_name+"   \r\n"
						+ "where loc_id=:loc \r\n" + "and RECEIVING_LOC_ID IN (" + reclocations + ") \r\n"
						+ "and LR_NO is null ");

				query = em.createNativeQuery(sb.toString(), Tuple.class);
				query.setParameter("loc", sendLocation);
			}

//		query.setParameter("statDate", startDate);
//		query.setParameter("endDate", endDate);

			list = query.getResultList();
			System.err.println(list.size());

			list.forEach(l -> {
				System.err.println(
						l.get(0) + ":::::" + l.get(1) + ":::::" + l.get(2) + "::::" + l.get(3) + "::::::" + l.get(4));
			});

		} catch (Exception e) {
			// TODO: handle exception
			throw e;
		} finally {
			if (em != null) {
				em.close();
			}
		}
		return list;

	}

	@Override
	public List<TRF_BEAN_stocktransfer_download_trdata> getStockTransferList(String startDate, String endDate,
			String locationId, List<String> destinationlist) {
		List<TRF_BEAN_stocktransfer_download_trdata> listTrfData = new ArrayList<>();
		StringBuffer reclocations = null;

		if (destinationlist.size() == 1) {
			reclocations = new StringBuffer();
			reclocations.append(destinationlist.get(0));

		} else if (destinationlist.size() > 1) {

			reclocations = new StringBuffer();

			for (int i = 0; i <= destinationlist.size() - 1; i++) {

				if ("0".equals(destinationlist.get(i).toString())) {
					reclocations = new StringBuffer("");
				} else {

					if (i != destinationlist.size() - 1) {
						reclocations.append(destinationlist.get(i) + ",");
						System.err.println(reclocations + "reclocations ");
					} else {
						reclocations.append(destinationlist.get(i));
					}

				}

				if ("0".equals(destinationlist.get(destinationlist.size() - 1))) {
					reclocations = new StringBuffer("0");
				}
				System.err.println(reclocations + "reclocations ");
			}
		}

		EntityManager em = null;
		em = emf.createEntityManager();
		StringBuffer sb = null;
		Query query = null;

		try {

			if (destinationlist.size() == 1 && "0".equals(destinationlist.get(0).toString())
					|| "0".equals(reclocations.toString())) {

				sb = new StringBuffer();
				sb.append("select TRF_id,TRF_NO from STOCK_TRANSFER_HEADER where LOC_ID=:loc and LR_NO is not null");

				query = em.createNativeQuery(sb.toString(), Tuple.class);
				query.setParameter("loc", locationId);
			} else {
				sb = new StringBuffer();
				System.err.println("rec " + reclocations);
//				sb.append("select TRF_id,TRF_NO from STOCK_TRANSFER_HEADER where LOC_ID=:loc "
//						+ "and LR_NO is not null and RECEIVING_LOC_ID in (" + reclocations + ")");

//				sb.append("select TRF_id,TRF_NO from STOCK_TRANSFER_HEADER where LOC_ID=:loc and LR_NO is not null and RECEIVING_LOC_ID in (" + reclocations + ") ORDER BY TRF_ID  DESC");
				sb.append("select sh.TRF_id,sh.TRF_NO from STOCK_TRANSFER_HEADER sh\r\n"
						+ "	 left outer join GRN_HEADER gh on sh.TRF_NO = gh.GRN_TRANSFER_NO\r\n"
						+ "	 where grn_id is null\r\n"
						+ "	 and sh.LOC_ID=:loc and sh.LR_NO is not null and sh.RECEIVING_LOC_ID in (" + reclocations
						+ ") ORDER BY sh.TRF_ID  DESC");
				query = em.createNativeQuery(sb.toString(), Tuple.class);
				query.setParameter("loc", locationId);

			}

			List<Tuple> list = query.getResultList();
			list.forEach(l -> {

				TRF_BEAN_stocktransfer_download_trdata trf = new TRF_BEAN_stocktransfer_download_trdata();
				trf.setTrf_id(l.get(0).toString());
				trf.setTrf_no(l.get(1).toString());
				listTrfData.add(trf);
			});

		} catch (Exception e) {
			e.printStackTrace();
			// TODO: handle exception
		} finally {
			if (em != null) {
				em.close();
			}
		}

		return listTrfData;
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public int saveDataForStockTransferLr(LrEntryBean bean) throws Exception {

		List<Tuple> list = null;
		int updatedCount = 0;
		
		String table_name="";
		if(bean.getFinYearFlag().equals("CURRENT")) table_name="STOCK_TRANSFER_HEADER" ;else table_name="STOCK_TRANSFER_HEADER_ARC";
		
		try {
			System.err.println("comming:::" + bean.getTransporter());

			Query query = entityManager.createNativeQuery(
					"UPDATE DD SET   F_FORM_NO=:ewaybill, LR_NO=:lr_no,LR_DATE=:lr_date,TRANSPORTER_NAME=:transName,WEIGHT=:weight,FULL_SHIPPERS=:full_shippers FROM  "+table_name+" DD WHERE TRF_ID=:trf_id");

			query.setParameter("lr_no", bean.getLrNumber());
			query.setParameter("lr_date", bean.getLrDate());
			query.setParameter("transName", bean.getTransporter());
			query.setParameter("weight", bean.getWeight());
			query.setParameter("full_shippers", bean.getCaseNo());
			query.setParameter("trf_id", bean.getTrf_Id());
			
			query.setParameter("ewaybill", bean.getWayBillNumber());
			

			updatedCount = query.executeUpdate();

			System.err.println(updatedCount);

		} catch (Exception e) {

			e.printStackTrace();
			throw e;
		}
		return updatedCount;

	}

	@Override
	public String getDpLocId(String loc_id) throws Exception {

		System.out.println(loc_id);
		EntityManager em = null;
		List<Tuple> location = null;
		Query query;

		em = emf.createEntityManager();
		StringBuffer sb = new StringBuffer();
		sb.append("select  LOC_LINK_DPTLOC_ID as  dp_loc_id   from location  where   loc_id=:loc_id ");

		query = em.createNativeQuery(sb.toString(), Tuple.class);
		query.setParameter("loc_id", loc_id);

		location = query.getResultList();
		Tuple t = location.get(0);

		String dp_loc_id = t.get("dp_loc_id").toString();
		System.err.println(dp_loc_id);

		return dp_loc_id;
	}

	@Override
	public List<Transporter_master> gettransportermasterForLocation(Long loc_id) throws Exception {
		// TODO Auto-generated method stub
		List<Transporter_master> list = null;
		 EntityManager em = null;
		try {
			
			Company company = companyMasterService.getCompanyDetails();
			em = emf.createEntityManager();
			StringBuffer sb = new StringBuffer();

			if (company.getCfa_to_stk_ind().equals("N")) {
				sb.append(
						"	 select TRANS_ID,TRANSPORTER_NAME,TRANSPORTER_ALTER_NAME,TRANS_PERS_RESP1,TRANS_PERS_RESP2,TRANS_EMAIL1,TRANS_EMAIL2,\r\n"
								+ "		TRANS_CONTACT_NO1,TRANS_CONTACT_NO2,TRANS_WEBSITE_NAME ,TRANS_GST_REG_NO from Transporter_master where trans_status='A'");
			}

			else {
				sb.append(
						" select TRANS_ID,TRANSPORTER_NAME,TRANSPORTER_ALTER_NAME,TRANS_PERS_RESP1,TRANS_PERS_RESP2,TRANS_EMAIL1,TRANS_EMAIL2,"
								+ "TRANS_CONTACT_NO1,TRANS_CONTACT_NO2,TRANS_WEBSITE_NAME,TRANS_GST_REG_NO from Transporter_master where trans_status='A' and loc_id = :loc_id order by TRANSPORTER_NAME asc");
			}
			

//		
		
		Query query = em.createNativeQuery(sb.toString(), Tuple.class);
		
		if (company.getCfa_to_stk_ind().equals("Y")) {
			query.setParameter("loc_id", loc_id);
		
		}
	
		List<Tuple> tuples = query.getResultList();
		if (tuples != null && !tuples.isEmpty()) {
			list = new ArrayList<>();
			Transporter_master smp = null;
			for (Tuple t : tuples) {
				smp = new Transporter_master();
				smp.setTrans_id(Long.valueOf(t.get("TRANS_ID",Integer.class)));
				smp.setTransporter_name(t.get("TRANSPORTER_NAME",String.class));
				smp.setTransporter_alter_name(t.get("TRANSPORTER_ALTER_NAME",String.class));
				smp.setTrans_pers_resp1(t.get("TRANS_PERS_RESP1",String.class));
				smp.setTrans_pers_resp2(t.get("TRANS_PERS_RESP2",String.class));
				smp.setTrans_email1(t.get("TRANS_EMAIL1",String.class));
				smp.setTrans_email2(t.get("TRANS_EMAIL2",String.class));
				smp.setTrans_contact_no1(t.get("TRANS_CONTACT_NO1",String.class));
				smp.setTrans_contact_no2(t.get("TRANS_CONTACT_NO2",String.class));
				smp.setTrans_website_name(t.get("TRANS_WEBSITE_NAME",String.class));
			
					smp.setTrans_gst_reg_no(t.get("TRANS_GST_REG_NO",String.class));
				
				
			
				list.add(smp);
			}
		}	
		}catch (Exception e) {
			// TODO: handle exception
			throw e;
		}
		return list;
	}
	
	
	@Override
	public Long getLocIdByUsername(String username) throws Exception {

		EntityManager em = null;
		Long location = null;
		Query query;

		em = emf.createEntityManager();
		StringBuffer sb = new StringBuffer();
		sb.append(" select emp_loc_id from hr_m_employee where emp_id =");
		sb.append(" (select ld_emp_cb_id from am_m_login_detail where ld_lgnid = :username)");

		query = em.createNativeQuery(sb.toString(), Tuple.class);
		query.setParameter("username", username);

		Tuple t =  (Tuple) query.getSingleResult();
		location = Long.valueOf((Short) t.get(0) );
		return location;
	}

	@Override
	public List<Lrcsv_RevisedDownload_SG> getLrcsvdownload_Revised_data_for_sg(String strdate, String enddate,
			String tbl_ind, String cfa, String fsid, String stock_at_cfa_ind, String stk_at_cfa_option, String temp,
			String deptloc, List<String> loc, List<String> div, List<String> dest, String cfa_to_stk_ind) {

		
		


		List<Lrcsv_RevisedDownload_SG> list = null;
		String procedureToCall = "";
		EntityManager em = null;

		// exec LR_CSV_UPLOAD_PROC_JAVA_REVISED '2020/01/01','2020/10/10','A','0','0'
		try {

			System.out.println("loc :: " + loc.size());
			System.out.println("div :: " + div.size());
			System.out.println("dest :: " + dest.size());

			StringBuffer locations = new StringBuffer();
			for (String a : loc) {
				locations.append(a + ",");
			}
			// System.out.println("locations :: "+locations.toString());
			String location = locations.toString().substring(0, locations.toString().length() - 1);
			System.out.println("locations :: " + location);

			StringBuffer divisions = new StringBuffer();
			for (String a : div) {
				divisions.append(a + ",");
			}
			// System.out.println("locations :: "+locations.toString());
			String division = divisions.toString().substring(0, divisions.toString().length() - 1);
			System.out.println("divisions :: " + division);

			StringBuffer destinations = new StringBuffer();
			for (String a : dest) {
				destinations.append(a + ",");
			}
			// System.out.println("locations :: "+locations.toString());
			String destination = destinations.toString().substring(0, destinations.toString().length() - 1);
			System.out.println("divisions :: " + destination);

			tbl_ind = "A";
			System.out.println("stk_at_cfa_option::" + stk_at_cfa_option);
			System.out.println("stock_at_cfa_ind::" + stock_at_cfa_ind);
			System.out.println("start" + strdate);
			System.out.println("start" + strdate);
//			 strdate = "2020/05/01";
//			 enddate = "2020/05/10";
			if (stk_at_cfa_option == null) {
				stk_at_cfa_option = "N";
			}
			System.out.println("stk_at_cfa_option::" + stk_at_cfa_option);

				procedureToCall = "call_LR_csv_revised_download_sg";
			

			System.out.println("tbl_ind::::" + tbl_ind + "::::strdate::::" + strdate + "::::enddate::::" + enddate
					+ "::::cfa::::" + cfa + "::::fsid::::" + fsid);

			em = emf.createEntityManager();

			StoredProcedureQuery procedurecall = em.createNamedStoredProcedureQuery(procedureToCall);
			procedurecall.setParameter("startdt", strdate);
			procedurecall.setParameter("enddt", enddate);
			procedurecall.setParameter("tabl_ind", tbl_ind);

			if (!deptloc.trim().equals("0") && cfa.equals("0")) {
				procedurecall.setParameter("cfa", temp.toString());
			} else {

				if (deptloc.trim().equals("0") && cfa.equals("0")) {
					procedurecall.setParameter("cfa", cfa);
				} else {
					procedurecall.setParameter("cfa", "(" + cfa + ")");
				}
			}
			procedurecall.setParameter("fsid", fsid);

			procedurecall.setParameter("div_id", division);
			procedurecall.setParameter("sumdisp_id", destination);
			procedurecall.setParameter("sumdsp_loc_id", location);

			list = procedurecall.getResultList();
			System.out.println("List :: " + list.size());

		} catch (Exception e) {
			throw e;
		} finally {
			if (em != null) {
				em.close();
			}
		}

		return list;
	}

}
