package com.excel.repository;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.StoredProcedureQuery;
import javax.persistence.Tuple;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.excel.bean.PrintBean;
import com.excel.model.Am_m_System_Parameter;
import com.excel.model.Dispatch_Header;
import com.excel.model.HqMasterAuditTrailModel;
import com.excel.model.Location;
import com.excel.model.NOV_BillOfSupply_Challan;
import com.excel.model.Period;
import com.excel.model.PrePrintedDetailChallan_withgst;
import com.excel.model.PrePrintedDetailChallan_withgstPG;
import com.excel.model.PrePrintedDetailChallan_withgst_pal;
import com.excel.model.SWV_Header;
import com.excel.model.SWV_Header_arc;
import com.excel.model.StockTransferNotePrint;
import com.excel.model.Stock_Adj_Header;
import com.excel.model.Stock_Transfer_Header;
import com.excel.model.Team;
import com.excel.model.ViewGrnGSTVoucherPrinting;
import com.excel.model.ViewGrnLabelPrinting_java;
import com.excel.model.ViewIAAVoucherPrinting;
import com.excel.model.ViewPrePrintedDetailChallan;
import com.excel.model.ViewPrePrintedSummaryChallan_GST;
import com.excel.model.ViewPrePrintedSummaryChallan_GST_EInvoice;
import com.excel.model.ViewPrePrintedSummaryChallan_GST_EInvoiceStockist;
import com.excel.model.ViewStockWithdrawalVoucherPrint;
import com.excel.utility.CodifyErrors;
import com.excel.utility.MedicoConstants;
import com.excel.utility.Utility;

@Repository
public class PrintRepositoryImpl implements PrintRepository,MedicoConstants{

	@Autowired EntityManagerFactory emf;
	@PersistenceContext private EntityManager entityManager;
	@SuppressWarnings("unchecked")
	@Override
	public List<ViewGrnGSTVoucherPrinting> getGrnPrintVoucherGstData(PrintBean bean) throws Exception {
		List<ViewGrnGSTVoucherPrinting> data = null ;
		EntityManager em=null;
		try {
			em = emf.createEntityManager();
			StoredProcedureQuery storedprocquery = em.createNamedStoredProcedureQuery("callGrnVoucherPrinting_java_GST");
			storedprocquery.setParameter("loc_id", bean.getLocationId());
			storedprocquery.setParameter("supp_id", bean.getVendorId());
			storedprocquery.setParameter("frm_Grn_id", bean.getFromGRN());
			storedprocquery.setParameter("to_Grn_id", bean.getToGRN());
			storedprocquery.setParameter("fin_year_flag", bean.getFinyear());
			storedprocquery.setParameter("fin_year_id", bean.getFinyearflag());
			
			data = storedprocquery.getResultList();
		} finally {
			if(em!=null) {em.close();}
		}
		return data;
	}

//	@Override
//	public List<PrePrintedDetailChallan_withgst> getPreprintedDetailedChallanData() throws Exception {
//		List<PrePrintedDetailChallan_withgst> lst = null;
//		EntityManager em=null;
//		try {
//			em = emf.createEntityManager();
//			StoredProcedureQuery storedProcedurequery = em.createNamedStoredProcedureQuery("callPrePrintedDetailChallan_withgst");
//			storedProcedurequery.setParameter("div_id", "48");
//			storedProcedurequery.setParameter("loc_id", "9");
//			storedProcedurequery.setParameter("frm_challan_no", "PIP  1916290");
//			storedProcedurequery.setParameter("to_challan_no", "PIP  1916290");
//			storedProcedurequery.setParameter("dispatchtype", "dtp");
//			storedProcedurequery.setParameter("prod_type", "");
//			storedProcedurequery.setParameter("rep_type", "D");
//			lst = storedProcedurequery.getResultList();
//		}
//		finally {
//			if(em!=null)
//			{
//				em.close();
//			}		
//		}
//		return lst;
//	}
	
	@Override
	public List<Period> getAllFinYearForGSTChallanPrint() {
		EntityManager em = null;
		List<Period> list=new ArrayList<Period>();
		try {
			em = emf.createEntityManager();
			String q="select distinct PERIOD_FIN_YEAR from PERIOD Order By PERIOD_FIN_YEAR DESC";
			Query query = em.createNativeQuery(q, Tuple.class);
			
			List<Tuple> tuples = query.getResultList();
			if(tuples != null && !tuples.isEmpty()) {
				for(Tuple t : tuples) {
					Period period=new Period();
					period.setPeriod_fin_year(t.get("PERIOD_FIN_YEAR",String.class));
					list.add(period);
				}
				if(!list.isEmpty() && list.size() > 0)
					return list;
				}
			
		} finally {
			if(em != null) { em.close(); }
		}
		return list;
	}
	@Override
	public  List<Period>  getListOfPeriodByFinYear(String finYear) {
		EntityManager em = null;
		int fyear=Integer.parseInt(finYear);
		List<Period> list=new ArrayList<Period>();
		try {
			em = emf.createEntityManager();
			String q="select period_id,period_fin_year,period_start_date,period_end_date,period_code,period_alt_name,period_current,period_name from PERIOD where period_fin_year=:fyear order by period_id desc";
			Query query = em.createNativeQuery(q,Tuple.class);
			query.setParameter("fyear", fyear);
			List<Tuple> tuples= query.getResultList();
			SimpleDateFormat format = new SimpleDateFormat("yyyy/MM/dd");
			if (tuples != null && !tuples.isEmpty()) {
				for(Tuple t : tuples) {
					Period period=new Period();
					period.setPeriod_code(t.get("period_code",String.class));
					period.setPeriod_fin_year(t.get("period_fin_year",String.class));
					period.setPeriod_alt_name(t.get("period_alt_name",String.class));
					period.setPeriod_start_date(format.format(t.get("period_start_date",Date.class)));
					period.setPeriod_end_date(format.format(t.get("period_end_date",Date.class)));
					period.setPeriod_current(String.valueOf(t.get("period_current",Character.class) != null ? t.get("period_current",Character.class) : "" ));
					period.setPeriod_name(t.get("period_name",String.class));
					list.add(period);
				}				
				if(!list.isEmpty() && list.size() > 0)
					return list;
				}
			
		}
		catch (Exception e) {
		e.printStackTrace();
		}
		finally {
			em.close();
		}
		return list;
	}
	
	
	@Override
	public List<Dispatch_Header> getChallanListForGSTChallanPrint(String divId,String periodCode,String finYear,String location,String finyrflg){
		
		EntityManager em = null;
		List<Dispatch_Header> list=new ArrayList<Dispatch_Header>();
		try {
			em = emf.createEntityManager();
			StringBuffer q= new StringBuffer();
			
			System.out.println("finyrflg :: "+finyrflg+"  finYear::"+finYear+"divId:::"+divId+"");
			
			if(finyrflg.equals(MedicoConstants.CURRENT)) {
				q.append("select distinct DSP_CHALLAN_NO,DSP_ID from Dispatch_header ");
			}
			else {
				q.append("select distinct DSP_CHALLAN_NO,DSP_ID from Dispatch_header_arc ");
			}
			q.append(" where dsp_div_id=:divId and DSP_LOC_ID=:location and DSP_PERIOD_CODE=:periodCode and DSP_FIN_YEAR=:finYear ");
			q.append(" and DSP_STATUS='A' and DSP_APPR_STATUS='A' and DSP_LBL_PRINT IN ('y','N') order by DSP_CHALLAN_NO asc");
			
			Query query = em.createNativeQuery(q.toString(),Tuple.class);
			query.setParameter("divId", divId);
			query.setParameter("periodCode", periodCode);
			query.setParameter("finYear", finYear);
			query.setParameter("location", location);
			List<Tuple> tuples = query.getResultList();
			if (tuples != null && !tuples.isEmpty()) {
				for(Tuple t : tuples) {
					Dispatch_Header dh=new Dispatch_Header();
					dh.setDspChallanNo(t.get("DSP_CHALLAN_NO",String.class));
					dh.setDsp_id(Long.valueOf(t.get("DSP_ID",Integer.class)));
					list.add(dh);
				}
				if(!list.isEmpty() && list.size() > 0)
					return list;
				}
		}
		finally {
			em.close();
		}
		return list;
	}
	
	@Override
	public List<Dispatch_Header> getChallanListForPickPackSlipPrint(String ro,String divId,String periodCode,String finYear,String location,String finyrflg){
		
		EntityManager em = null;
		List<Dispatch_Header> list=new ArrayList<Dispatch_Header>();
		StringBuffer q = null;
		
		System.out.println("finyrflg :: "+finyrflg+"  finYear::"+finYear);
		try {
			em = emf.createEntityManager();
			if(ro.equals("tcfa")) {
				q=new StringBuffer();
				q.append("SELECT distinct SH.sumdsp_challan_no as DSP_CHALLAN_NO,SH.sumdsp_id as DSP_ID " );
				if(finyrflg.equals(MedicoConstants.CURRENT)) {
				q.append(" FROM  SUM_DISP_HEADER SH left join Dispatch_header DH on SH.SUMDSP_ID=DH.DSP_SUM_DSP_ID ");
				}
				else {
				q.append(" FROM  SUM_DISP_HEADER_ARC SH left join Dispatch_header_ARC DH on SH.SUMDSP_ID=DH.DSP_SUM_DSP_ID ");
				}
				q.append(" WHERE SH.sumdsp_div_id=:divId AND SH.sumdsp_loc_id=:location AND "); 
				q.append(" SH.sumdsp_period_code=:periodCode AND SH.sumdsp_fin_year=:finYear AND SH.SUMDSP_status='A' ");
				q.append(" and DH.dsp_appr_status in ('E','F','A') ORDER By SH.sumdsp_challan_no");
			}
			if(ro.equals("dtp")) {
				q=new StringBuffer();
				if(finyrflg.equals(MedicoConstants.CURRENT)) {
				q.append("select distinct DSP_CHALLAN_NO,DSP_ID from Dispatch_header ");
				}
				else {
				q.append("select distinct DSP_CHALLAN_NO,DSP_ID from Dispatch_header_arc ");
				}
				q.append(" where dsp_div_id=:divId and DSP_LOC_ID=:location and DSP_PERIOD_CODE=:periodCode and DSP_FIN_YEAR=:finYear ");
				q.append(" and DSP_STATUS='A' and DSP_APPR_STATUS IN ('E','F','A') order by DSP_CHALLAN_NO asc");
			}
			Query query = em.createNativeQuery(q.toString(),Tuple.class);
			query.setParameter("divId", divId);
			query.setParameter("periodCode", periodCode);
			query.setParameter("finYear", finYear);
			query.setParameter("location", location);
			List<Tuple> tuples = query.getResultList();
			if (tuples != null && !tuples.isEmpty()) {
				for(Tuple t : tuples) {
					Dispatch_Header dh=new Dispatch_Header();
					dh.setDspChallanNo(t.get("DSP_CHALLAN_NO",String.class));
					dh.setDsp_id(Long.valueOf(t.get("DSP_ID",Integer.class)));
					list.add(dh);
				}
				if(!list.isEmpty() && list.size() > 0)
					return list;
				}
		}
		finally {
			em.close();
		}
		return list;
	}
	
	@Override
	public List<Object> getIAANoListForDocumentPrint(String location,String startDate, String endDate,String finyr,String finyrflg){
		System.out.println("Dates in repository :"+startDate+"..."+endDate);
		
		System.out.println("finyr :"+finyr+"   finyrflg :: "+finyrflg);
		
		EntityManager em = null;
		List<Object> list=new ArrayList<Object>();
		StringBuffer q = new StringBuffer();
		q.append("SELECT CONVERT(VARCHAR(11),SH.STKADJ_DATE,103) , SH.STKADJ_NO , SH.STKADJ_ID ");	
		
		if(finyrflg.equalsIgnoreCase(MedicoConstants.CURRENT)) {
		q.append(" FROM STOCK_ADJ_HEADER SH WHERE STKADJ_APPR_STATUS='A' ");
		}
		else {
			q.append(" FROM STOCK_ADJ_HEADER_ARC SH WHERE STKADJ_APPR_STATUS='A' ");
		}
		q.append(" AND CONVERT(date,SH.STKADJ_DATE,105)>=CONVERT(date,:startDate,105) ");
		q.append(" AND CONVERT(date,SH.STKADJ_DATE,105)<= CONVERT(date,:endDate,105) ");	
		q.append(" AND SH.STKADJ_LOC_ID=:location order by SH.STKADJ_NO ");
		try {
			em = emf.createEntityManager();
			Query query = em.createNativeQuery(q.toString(),Tuple.class);
			query.setParameter("location", location);
			query.setParameter("startDate", startDate);
			query.setParameter("endDate", endDate);
			List<Tuple> tuples = query.getResultList();
			if (tuples != null && !tuples.isEmpty()) {
				for(Tuple t : tuples) {
					Stock_Adj_Header sah=new Stock_Adj_Header();
					sah.setStkadj_id(Long.valueOf(t.get("STKADJ_ID",Integer.class)));
					sah.setStkadj_no(t.get("STKADJ_NO",String.class));
					list.add(sah);
				}
				if(!list.isEmpty() && list.size() > 0)
					return list;
				}
		}
		finally {
			em.close();
		}
		return list;
	}
	@Override
	public List<Dispatch_Header> getChallanListForGSTSummaryChallanPrint(String reportOption,String divId,String periodCode,
			String finYear,String location){
		
		EntityManager em = null;
		List<Dispatch_Header> list=new ArrayList<Dispatch_Header>();
		String q="";
		try {
			em = emf.createEntityManager();
			if(reportOption.equals("tcfa")) {
				q="SELECT distinct SH.sumdsp_challan_no as DSP_CHALLAN_NO,SH.sumdsp_id as DSP_ID " + 
						" FROM  SUM_DISP_HEADER SH left join Dispatch_header DH on SH.SUMDSP_ID=DH.DSP_SUM_DSP_ID " + 
						" WHERE SH.sumdsp_div_id=:divId AND SH.sumdsp_loc_id=:location AND " + 
						" SH.sumdsp_period_code=:periodCode AND SH.sumdsp_fin_year=:finYear AND SH.SUMDSP_status='A' " + 
						" and DH.dsp_appr_status in ('E','F','A') ORDER By SH.sumdsp_challan_no";
			}
			if(reportOption.equals("dtp")) {
				q="select distinct DSP_CHALLAN_NO,DSP_ID from Dispatch_header "
						+ "where dsp_div_id=:divId and DSP_LOC_ID=:location and DSP_PERIOD_CODE=:periodCode and DSP_FIN_YEAR=:finYear "
						+ "and DSP_STATUS='A' and DSP_APPR_STATUS IN ('A','E','F') order by DSP_CHALLAN_NO asc";
			}
			Query query = em.createNativeQuery(q,Tuple.class);
			query.setParameter("divId", divId);
			query.setParameter("periodCode", periodCode);
			query.setParameter("finYear", finYear);
			query.setParameter("location", location);
			List<Tuple> tuples = query.getResultList();
			if (tuples != null && !tuples.isEmpty()) {
				for(Tuple t : tuples) {
					Dispatch_Header dh=new Dispatch_Header();
					dh.setDspChallanNo(t.get("DSP_CHALLAN_NO",String.class));
					dh.setDsp_id(Long.valueOf(t.get("DSP_ID",Integer.class)));
					list.add(dh);
				}
				if(!list.isEmpty() && list.size() > 0)
					return list;
				}
		}
		finally {
			em.close();
		}
		return list;
	}
	

	
	
	
	
	@Override
	public List<ViewPrePrintedDetailChallan> getPreprintedSummaryChallanData(String division, String loc,
			String frm_challan, String to_challan, String prodtype, String rep_type,PrintBean bean) throws Exception {
		List<ViewPrePrintedDetailChallan> lst = null;
		EntityManager em=null;
		System.out.println("PrePrintedSummaryChallan");
		try {
			em = emf.createEntityManager();
			System.out.println("div_id " +division);
			System.out.println("frm " +frm_challan);
			System.out.println("to " +to_challan);
			System.out.println("loc_id " +loc);
			System.out.println("prodtype " +prodtype);
			System.out.println("rep_type " +rep_type);
			
			if(prodtype==null) {
				prodtype = "";
			}
			
			StoredProcedureQuery storedProcedurequery = em.createNamedStoredProcedureQuery("callPrePrintedSummaryChallan");
			storedProcedurequery.setParameter("div_id",division);
			storedProcedurequery.setParameter("loc_id",loc);
			storedProcedurequery.setParameter("frm_challan_no",frm_challan);
			storedProcedurequery.setParameter("to_challan_no",to_challan);
			storedProcedurequery.setParameter("prod_type",prodtype);
			storedProcedurequery.setParameter("rep_type",rep_type);
			storedProcedurequery.setParameter("fin_year_flag",bean.getFinyearflg());
			storedProcedurequery.setParameter("fin_year_id",bean.getFinyearflag());
			
			lst = storedProcedurequery.getResultList();
			
			System.out.println("List Size:::"+lst.size());
		}
		finally {
			if(em!=null)
			{
				em.close();
			}	
		}
		return lst;
	}

	@Override
	public Am_m_System_Parameter getSpValueForFooterSignature() {

		EntityManager em = null; 
		List<Am_m_System_Parameter> list = null;
		Am_m_System_Parameter sp_val = null;
		try {
		em = emf.createEntityManager();
		CriteriaBuilder builder = em.getCriteriaBuilder();
		CriteriaQuery<Am_m_System_Parameter> query = builder.createQuery(Am_m_System_Parameter.class);
		Root<Am_m_System_Parameter> root = query.from(Am_m_System_Parameter.class);
		query.select(root).where(builder.equal(root.get("sp_name"), "CHALLAN_FOOTER_LASTPAGE_ONLY"));	
		list = em.createQuery(query).getResultList();
		
		if(list.size()>0)
			sp_val = list.get(0);
	
	}
		catch (Exception e) {
			throw e;
		}finally {
			if(em!=null)
			{
				em.close();
			}	
		}
		
			return sp_val;
	
	}

	@Override
	public List<ViewPrePrintedSummaryChallan_GST> PrePrintedSummaryChallan_GSTdata(String division, String loc,
			String frm_challan, String to_challan, String prodtype, String rep_type,PrintBean bean) throws Exception {

		List<ViewPrePrintedSummaryChallan_GST> challans = null;
		EntityManager em = null;
		try {

			System.out.println("rep_type " +rep_type);
			System.out.println("div_id " +division);
			System.out.println("frm " +frm_challan);
			System.out.println("to " +to_challan);
			System.out.println("loc_id " +loc);
			//System.out.println("dispatchType " +dispatchType);
			System.out.println("prodtype " +prodtype);
			System.out.println("rep_type " +rep_type);
			
			em = emf.createEntityManager();
			
			StoredProcedureQuery storedprocedurequery = em.createNamedStoredProcedureQuery("callPrePrintedSummaryChallan_GST");
			
			storedprocedurequery.setParameter("div_id", division);
			storedprocedurequery.setParameter("frm_challan_no", frm_challan);
			storedprocedurequery.setParameter("to_challan_no", to_challan);
			storedprocedurequery.setParameter("loc_id", loc);
			storedprocedurequery.setParameter("prod_type", "");
			storedprocedurequery.setParameter("rep_type", rep_type);
			storedprocedurequery.setParameter("fin_year_flag",bean.getFinyearflg());
			storedprocedurequery.setParameter("fin_year_id",bean.getFinyearflag());
			
			challans = storedprocedurequery.getResultList();
		}
		catch (Exception e) {
		throw e;
		}finally {
			if(em!=null)
			{
				em.close();
			}	
		}
		
		return challans;
	
	}
	
	
	@Override
	public List<ViewPrePrintedSummaryChallan_GST_EInvoice> PrePrintedSummaryChallan_GST_EInvoicedata(String division, String loc,
			String frm_challan, String to_challan, String prodtype, String rep_type,PrintBean bean) throws Exception {

		List<ViewPrePrintedSummaryChallan_GST_EInvoice> challans = null;
		EntityManager em = null;
		try {

			System.out.println("rep_type " +rep_type);
			System.out.println("div_id " +division);
			System.out.println("frm " +frm_challan);
			System.out.println("to " +to_challan);
			System.out.println("loc_id " +loc);
			//System.out.println("dispatchType " +dispatchType);
			System.out.println("prodtype " +prodtype);
			System.out.println("rep_type " +rep_type);
			System.out.println("fin_year_flag " +bean.getFinyearflg());
			System.out.println("fin_year_id " +bean.getFinyearflag());
			
			em = emf.createEntityManager();
			
			StoredProcedureQuery storedprocedurequery = em.createNamedStoredProcedureQuery("callPrePrintedSummaryChallan_GST_EInvoice");
			
			storedprocedurequery.setParameter("div_id", division);
			storedprocedurequery.setParameter("frm_challan_no", frm_challan);
			storedprocedurequery.setParameter("to_challan_no", to_challan);
			storedprocedurequery.setParameter("loc_id", loc);
			storedprocedurequery.setParameter("prod_type", "");
			storedprocedurequery.setParameter("rep_type", rep_type);
			storedprocedurequery.setParameter("fin_year_flag",bean.getFinyearflg());
			storedprocedurequery.setParameter("fin_year_id",bean.getFinyearflag());
			
			challans = storedprocedurequery.getResultList();
		}
		catch (Exception e) {
		throw e;
		}finally {
			if(em!=null)
			{
				em.close();
			}	
		}
		
		return challans;
	
	}

	@Override
	public List<PrePrintedDetailChallan_withgst> getPreprintedDetailedChallanData(String division, String loc,
			String from_challan, String to_challan, String dispatchtype, String product_type, String rep_type,PrintBean bean)
			throws Exception {
		List<PrePrintedDetailChallan_withgst> lst = null;
		EntityManager em=null;
		try {
			
			System.out.println("PrePrintedDetailChallan_withgst");
			System.out.println("DivId:::"+division);
			System.out.println("loc_id:"+loc);
			System.out.println("frm_challan_no:"+from_challan);
			System.out.println("to_challan_no:"+to_challan);
			System.out.println("dispatchtype:"+dispatchtype);
			System.out.println("prod_type:"+product_type);
			System.out.println("rep_type:"+rep_type);
			System.out.println("fin_year_flag"+ bean.getFinyearflg());
			System.out.println("fin_year_id"+ bean.getFinyearflag());
			if(product_type==null) {
				product_type = "";
			}
			
			em = emf.createEntityManager();
			if(bean.getCompanyCode().trim().equals(NIL) || bean.getCompanyCode().trim().equals(NHP)){
				StoredProcedureQuery storedProcedurequery = em.createNamedStoredProcedureQuery("callPrePrintedDetailChallan_withgst_NOVA");
				storedProcedurequery.setParameter("div_id", division);
				storedProcedurequery.setParameter("loc_id", loc);
				storedProcedurequery.setParameter("frm_challan_no", from_challan);
				storedProcedurequery.setParameter("to_challan_no", to_challan);
				storedProcedurequery.setParameter("dispatchtype", dispatchtype);
				storedProcedurequery.setParameter("prod_type", product_type);
				storedProcedurequery.setParameter("rep_type", rep_type);
				storedProcedurequery.setParameter("fin_year_flag", bean.getFinyearflg());
				storedProcedurequery.setParameter("fin_year_id", bean.getFinyearflag());
				lst = storedProcedurequery.getResultList();
			}
			else {
				StoredProcedureQuery storedProcedurequery = em.createNamedStoredProcedureQuery("callPrePrintedDetailChallan_withgst");
				storedProcedurequery.setParameter("div_id", division);
				storedProcedurequery.setParameter("loc_id", loc);
				storedProcedurequery.setParameter("frm_challan_no", from_challan);
				storedProcedurequery.setParameter("to_challan_no", to_challan);
				storedProcedurequery.setParameter("dispatchtype", dispatchtype);
				storedProcedurequery.setParameter("prod_type", product_type);
				storedProcedurequery.setParameter("rep_type", rep_type);
				storedProcedurequery.setParameter("fin_year_flag", bean.getFinyearflg());
				storedProcedurequery.setParameter("fin_year_id", bean.getFinyearflag());
				lst = storedProcedurequery.getResultList();
			}
		}
		finally {
			if(em!=null)
			{
				em.close();
			}		
		}
		return lst;
	}

	@Override
	public List<PrePrintedDetailChallan_withgst_pal> getPreprintedDetailedChallanDatapal(String division, String loc,
			String from_challan, String to_challan, String dispatchtype, String product_type, String rep_type,PrintBean bean)
			throws Exception {
		List<PrePrintedDetailChallan_withgst_pal> lst = null;
		EntityManager em=null;
		try {
			
			System.out.println("PrePrintedDetailChallan_withgst_pal");
			System.out.println("DivId:::"+division);
			System.out.println("loc_id:"+loc);
			System.out.println("frm_challan_no:"+from_challan);
			System.out.println("to_challan_no:"+to_challan);
			System.out.println("dispatchtype:"+dispatchtype);
			System.out.println("prod_type:"+product_type);
			System.out.println("rep_type:"+rep_type);
			System.out.println("fin_year_flag"+ bean.getFinyearflg());
			System.out.println("fin_year_id"+ bean.getFinyearflag());
			if(product_type==null) {
				product_type = "";
			}
			
			em = emf.createEntityManager();
			
				StoredProcedureQuery storedProcedurequery = em.createNamedStoredProcedureQuery("PrePrintedDetailChallan_withgst_pal");
				storedProcedurequery.setParameter("div_id", division);
				storedProcedurequery.setParameter("loc_id", loc);
				storedProcedurequery.setParameter("frm_challan_no", from_challan);
				storedProcedurequery.setParameter("to_challan_no", to_challan);
				storedProcedurequery.setParameter("dispatchtype", dispatchtype);
				storedProcedurequery.setParameter("prod_type", product_type);
				storedProcedurequery.setParameter("rep_type", rep_type);
				storedProcedurequery.setParameter("fin_year_flag", bean.getFinyearflg());
				storedProcedurequery.setParameter("fin_year_id", bean.getFinyearflag());
				lst = storedProcedurequery.getResultList();
				
				System.out.println(" ~~~~~~~~~~~~~~~~~~~~~~~~~~ list "+lst.size());
		}
		finally {
			if(em!=null)
			{
				em.close();
			}		
		}
		return lst;
	}
	
	
	@Override
	public String getLocationNarrationByLocId(Long locId) throws Exception {
		String Narration="";
		Location location = null;
		EntityManager em = null;
		try {
			  em = emf.createEntityManager();
			  Query query = em.createQuery("SELECT l.loc_narration FROM Location l WHERE l.loc_id = :loc_id");
		      query.setParameter("loc_id", locId);
		      List<String> resultList = query.getResultList();
		      Narration = resultList.get(0).toString();
		}finally {
			em.close();
		}
		return Narration;
	}

	@Override
	public List<ViewPrePrintedDetailChallan> getViewPrePrintedDetailChallanData(String division, String loc,
			String frm_challan, String to_challan, String dispatchType, String prodtype, String rep_type,PrintBean bean)
			throws Exception {
		List<ViewPrePrintedDetailChallan> challans = null;
		EntityManager em = null;
		try {
			em = emf.createEntityManager();
			System.out.println("PrePrintedDetailChallan");
			System.out.println("div_id " +division);
			System.out.println("loc_id " +loc);
			System.out.println("frm " +frm_challan);
			System.out.println("to " +to_challan);
			System.out.println("dispatchType " +dispatchType);
			System.out.println("prodtype " +prodtype);
			System.out.println("bean.getFinyear() " +bean.getFinyear());
			System.out.println("bean.getFinyearflg() " +bean.getFinyearflg());
			
			System.out.println("bean.getFinyearflag() " +bean.getFinyearflag());
			
			if(prodtype==null) {
				prodtype = "";
			}
			
			StoredProcedureQuery storedProcedurequery = em.createNamedStoredProcedureQuery("callPrePrintedDetailChallan");
			storedProcedurequery.setParameter("div_id",division);
			storedProcedurequery.setParameter("loc_id",loc);
			storedProcedurequery.setParameter("frm_challan_no",frm_challan);
			storedProcedurequery.setParameter("to_challan_no",to_challan);
			storedProcedurequery.setParameter("dispatchtype", dispatchType);
			storedProcedurequery.setParameter("prod_type",prodtype);
			storedProcedurequery.setParameter("rep_type",rep_type);
			storedProcedurequery.setParameter("fin_year_flag",bean.getFinyearflg());
			storedProcedurequery.setParameter("fin_year_id",bean.getFinyearflag());
			challans = storedProcedurequery.getResultList();
			System.out.println("result size " +challans.size());
		}
		catch (Exception e) {
			throw e;
		}
		finally {
			em.close();
		}
		return challans;
	}
	
	@Override
	public List<ViewGrnLabelPrinting_java> getGrnLabelPrintData(String loc_id,String vender_id,String frmgrn,String togrn,String user_type,PrintBean pb) throws Exception {
		List<ViewGrnLabelPrinting_java> list = null;
		EntityManager em = null;
		try {
			System.out.println("Procedure ViewGrnLabelPrinting_java called::");
			System.out.println("loc_id : " + loc_id);
			System.out.println("vender_id : " + vender_id);
			System.out.println("frmgrn : " + frmgrn);
			System.out.println("togrn : " + togrn);
			System.out.println("user_type : " + user_type);
			System.out.println("fin_year_flag : " + pb.getFinyear());
			System.out.println("fin_year_id : " + pb.getFinyearflag());
			
			em = emf.createEntityManager();
			StoredProcedureQuery storedProcedureQuery = em.createNamedStoredProcedureQuery("callViewGrnLabelPrinting_java");
			
			storedProcedureQuery.setParameter("loc_id", loc_id);
			storedProcedureQuery.setParameter("supp_id", vender_id);
			storedProcedureQuery.setParameter("frm_Grn_id", frmgrn);
			storedProcedureQuery.setParameter("to_Grn_id", togrn);
			storedProcedureQuery.setParameter("User_type", user_type);
			storedProcedureQuery.setParameter("fin_year_flag",  pb.getFinyear());
			storedProcedureQuery.setParameter("fin_year_id", pb.getFinyearflag());
			
			list = storedProcedureQuery.getResultList();
			System.out.println("list "+list.size());
		}
		catch (Exception e) {
			System.out.println("Error Occurred :" +e.getMessage());// uncomment asneeded --;
		}
		finally {
			em.close();
		}
		return list;
	}
	
	@Override
	public List<PrintBean> getdetailLabelData(String from, String to,String user_type, String finYearflg)throws Exception{
		StringBuffer sb= new StringBuffer();
		EntityManager em = null;
		List<PrintBean> beanlist = new ArrayList<PrintBean>();
		em = emf.createEntityManager();
		
		try {
			sb.append(" select dv.DIV_DISP_NM+' - '+isnull(t.TEAM_NAME,' ' ) TEAM,fm.FSTAFF_MAP_CODE2 terr_code, fm.FSTAFF_TERRNAME terr_name,");
			sb.append(" fm.FSTAFF_EMPLOYEE_NO , fm.FSTAFF_DISPLAY_NAME , fm.FSTAFF_ADDR1 , fm.FSTAFF_ADDR2 , fm.FSTAFF_ADDR3 , fm.FSTAFF_ADDR4 , ");
			sb.append(" dh.DSP_CHALLAN_NO,convert(varchar,dh.DSP_CHALLAN_DT,103) DSP_CHALLAN_DT , ");
			/*sb.append(" sum( round( dd.dspdtl_disp_qty / PR.smp_qty_shipper, 0 ) ) DSP_CASES , l.loc_nm cfa , st.SGprmdet_disp_nm state,fm.FSTAFF_MOBILE,fm.FSTAFF_DESTINATION,fm.FSTAFF_PINCODE,");*/
			sb.append("dh.DSP_CASES, l.loc_nm cfa , st.SGprmdet_disp_nm state,fm.FSTAFF_MOBILE,fm.FSTAFF_DESTINATION,fm.FSTAFF_PINCODE,");
			sb.append(" dh.DSP_LR_NO,dh.DSP_TRANSPORTER, dh.DSP_ID ");
			System.out.println("finyearflag::::"+finYearflg);
			if(finYearflg.trim().equalsIgnoreCase("CURRENT")) {
				sb.append(" from Dispatch_header dh , DIV_MASTER dv , FIELDSTAFF fm left outer join team t on t.TEAM_CODE = fm.FSTAFF_DOM_EXP , HQMASTER hq ,");				
				sb.append(" Dispatch_detail dd , SMPITEM pr , location l , SG_d_parameters_details st");

			}else
			{
				sb.append(" from Dispatch_Header_arc dh , DIV_MASTER dv , FIELDSTAFF fm left outer join team t on t.TEAM_CODE = fm.FSTAFF_DOM_EXP , HQMASTER hq ,");				
				sb.append(" Dispatch_detail_arc dd , SMPITEM pr , location l , SG_d_parameters_details st");

			}
			
			sb.append(" where dh.DSP_DIV_ID = dv.DIV_ID and dh.DSP_FSTAFF_ID = fm.FSTAFF_ID ");
			sb.append(" and dh.DSP_ID >=:from ");
			sb.append(" and dh.DSP_ID <=:to ");
			sb.append(" and dh.DSP_ID = dd.DSPDTL_DSP_ID");
			sb.append(" and dd.DSPDTL_PROD_ID = pr.SMP_PROD_ID");
			sb.append(" and dh.DSP_LOC_ID = l.loc_id");
			sb.append(" and fm.FSTAFF_HQ_ID = hq.HQ_ID"); 
			sb.append(" and hq.HQ_STATE_ID = st.SGprmdet_id and st.SGprmdet_SGprm_id = '2'");
			if(user_type.equalsIgnoreCase("S")){
				sb.append(" and dh.DSP_LBL_PRINT='Y' ");
			}else{
				sb.append(" and dh.DSP_LBL_PRINT='N' "); 
			}
			sb.append(" group by dv.DIV_DISP_NM ,fm.FSTAFF_MAP_CODE2 , fm.FSTAFF_TERRNAME ,");
			sb.append(" fm.FSTAFF_EMPLOYEE_NO , fm.FSTAFF_DISPLAY_NAME, fm.FSTAFF_ADDR1 , fm.FSTAFF_ADDR2 , fm.FSTAFF_ADDR3 , fm.FSTAFF_ADDR4 ,  fm.FSTAFF_MOBILE,"); 
			sb.append(" dh.DSP_CHALLAN_NO,dh.DSP_CHALLAN_DT, l.loc_nm, st.SGprmdet_disp_nm,fm.FSTAFF_DESTINATION,fm.FSTAFF_PINCODE,dh.DSP_LR_NO,dh.DSP_TRANSPORTER, t.TEAM_NAME,dh.DSP_CASES");
			sb.append("	,dh.DSP_ID order by dv.DIV_DISP_NM,dh.DSP_CHALLAN_NO ");	

			System.out.println("Query::::   "+sb.toString());

		//	ArrayList<DelChallanBean> result = new ArrayList<DelChallanBean>();
			Set<Long> challanset = new LinkedHashSet<Long>();
			List<Tuple> list = null;
			
			Query query = em.createNativeQuery(sb.toString(), Tuple.class);
			query.setParameter("from", from);
		    query.setParameter("to", to);
		    
			list = query.getResultList();
			PrintBean bean = null;
			if (list != null && !list.isEmpty()) {
				for(Tuple t : list) {
					bean = new PrintBean();
					bean.setSumdsp_challan_dt(t.get("DSP_CHALLAN_DT",String.class));
					bean.setSumdsp_challan_no(t.get("DSP_CHALLAN_NO",String.class));
					bean.setCfa(t.get("FSTAFF_DISPLAY_NAME",String.class));
					bean.setTeam(t.get("TEAM",String.class));
					bean.setDptdestination(t.get("FSTAFF_DESTINATION",String.class));
					bean.setSumdsp_lr_no(t.get("DSP_LR_NO",String.class));
					bean.setTransporter(t.get("DSP_TRANSPORTER",String.class));
					bean.setCases(t.get("DSP_CASES",Integer.class));
					beanlist.add(bean);
				}
				}
		}catch (Exception e) {
			throw e;
		}
		finally {
			if(em!=null)
			{
				em.close();
			}	
		}
		return beanlist;
	}

	
	@Override
	public void updateDispatchLblPrintRemark(EntityManager em, String lbl_print_status, String remarks,
			String mod_user_id, String mod_ip, Set<Long> dsp_id) throws Exception {
		// TODO Auto-generated method stub
		  System.out.println("remarks:: " + remarks + "::mod_user_id:: " + mod_user_id + "::mod_ip:: " + mod_ip);

		  StringBuffer buffer = new StringBuffer();
		    buffer.append(
			    " update Dispatch_header set dsp_lbl_print=:lbl_print, REMARKS=:remarks, DSP_mod_usr_id=:mod_user_id, ");
		    buffer.append(" DSP_mod_dt=:mod_date, DSP_mod_ip_add=:mod_ip where DSP_ID in(:dsp_id) ");
		  
		    try{
		    Query query = em.createNativeQuery(buffer.toString());
		  	query.setParameter("dsp_id", dsp_id);
		    query.setParameter("lbl_print", lbl_print_status);
		    query.setParameter("remarks", remarks);
		    query.setParameter("mod_user_id", mod_user_id);
		    query.setParameter("mod_date", new Date());
		    query.setParameter("mod_ip", mod_ip);
		    query.executeUpdate();
		    }
		    catch (Exception e) {
		    	throw e;
		    }
			finally {
				if(em!=null)
				{
					em.close();
				}	
			}
		
		
	}

	@Override
	public List<ViewIAAVoucherPrinting> getViewIAAVoucherPrintingdata(String locId, String frmStkno, String toStkno,PrintBean pb)
			throws Exception {
		List<ViewIAAVoucherPrinting> challans = null;
		EntityManager em = null;
		
		try {
			System.out.println("ViewIAAVoucherPrinting");
			
			System.out.println("Loc_Id::"+locId);
			System.out.println("frm_laa_id ::"+frmStkno);
			System.out.println("to_laa_id ::"+toStkno);
			System.out.println("fin_year_flag ::"+pb.getFinyear());
			System.out.println("fin_year_id ::"+pb.getFinyearflag());
			
			em = emf.createEntityManager();
			
			StoredProcedureQuery storedProcedurequery = em.createNamedStoredProcedureQuery("callViewIAAVoucherPrinting");
			storedProcedurequery.setParameter("loc_id",locId);
			storedProcedurequery.setParameter("frm_Iaa_id",frmStkno);
			storedProcedurequery.setParameter("to_Iaa_id",toStkno);
			storedProcedurequery.setParameter("fin_year_flag",pb.getFinyear());
			storedProcedurequery.setParameter("fin_year_id",pb.getFinyearflag());
			
			challans = storedProcedurequery.getResultList();
			
			System.out.println("result size " +challans.size());
			}
		catch (Exception e) {
			throw e;
		}
		finally {
			em.close();
		}
		return challans;
	}
	@Override
	public List<HqMasterAuditTrailModel> getHqmasteraudittraildata(PrintBean bean) throws Exception {
		List<HqMasterAuditTrailModel>list =  null;
		EntityManager em = null;
		try {
			System.out.println("Hq Master Audit Trail");
			
			System.out.println("divid"+bean.getDivid());
			
			em = emf.createEntityManager();
			
			
			StoredProcedureQuery storedProcedurequery = em.createNamedStoredProcedureQuery("HqMasterAuditTrail");
			storedProcedurequery.setParameter("DIVID",bean.getDivid());
			
			list = storedProcedurequery.getResultList();
			
			System.out.println("list ::"+list.size());
			
		}catch(Exception e) {
			throw e;
		}
		finally {
			em.close();
		}
		return list;
	}
	//Stock Transfer Print
		@Override
		public List<Stock_Transfer_Header> getTrfNoList(String location,String rl,String startDate, String endDate,String fin_year_flag){
			System.out.println("Dates in repository :"+startDate+"..."+endDate);
			System.out.println("Receiving loc :"+rl);
			if(Integer.parseInt(rl)==0){
				System.out.println("..");
			}
			{System.out.println();}
			EntityManager em = null;
			List<Stock_Transfer_Header> list=new ArrayList<Stock_Transfer_Header>();
			String q;
			
			String table_name="";
			if(fin_year_flag.trim().equals("CURRENT")) {
				table_name="STOCK_TRANSFER_HEADER";
			}else {
				table_name="STOCK_TRANSFER_HEADER_ARC";
			}
			
			if(Integer.parseInt(rl)!=0) {
	
				 q="select TRF_NO ,TRF_ID FROM "+table_name+"  WHERE LOC_ID = :location "
							+ "AND PARTY_ID= :rl "
			//				+ "AND TRF_DATE >=:startDate AND TRF_DATE <=:endDate "
							+" AND CONVERT(date,TRF_DATE,105)>=CONVERT(date,:startDate,105) " 
							+" AND CONVERT(date,TRF_DATE,105)<= CONVERT(date,:endDate,105) "
							+ "AND TRFHDR_STATUS='A'  order by TRF_NO";
			}
			else {
				 q="select TRF_NO ,TRF_ID FROM "+table_name+"  WHERE LOC_ID = :location "
//						+ "AND TRF_DATE >=:startDate AND TRF_DATE <=:endDate "
						+" AND CONVERT(date,TRF_DATE,105)>=CONVERT(date,:startDate,105) " 
						+" AND CONVERT(date,TRF_DATE,105)<= CONVERT(date,:endDate,105) "
						+ "AND TRFHDR_STATUS='A'  order by TRF_NO";
			}

			
//			"AND CONVERT(date,SH.STKADJ_DATE,105)>=CONVERT(date,:startDate,105) " + 
//			"AND CONVERT(date,SH.STKADJ_DATE,105)<= CONVERT(date,:endDate,105) "+
			
			
			
			
//			
//			if(Integer.parseInt(rl)!=0) {
//				
//				
//				 q="select TRF_NO ,TRF_ID FROM STOCK_TRANSFER_HEADER_ARC  WHERE LOC_ID = :location "
//							+ "AND PARTY_ID= :rl "
//			//				+ "AND TRF_DATE >=:startDate AND TRF_DATE <=:endDate "
//							+" AND CONVERT(date,TRF_DATE,105)>=CONVERT(date,:startDate,105) " 
//							+" AND CONVERT(date,TRF_DATE,105)<= CONVERT(date,:endDate,105) "
//							+ "AND TRFHDR_STATUS='A'  order by TRF_NO";
//			}
//			else {
//				q="select TRF_NO ,TRF_ID FROM STOCK_TRANSFER_HEADER_ARC  WHERE LOC_ID = :location "
////						+ "AND TRF_DATE >=:startDate AND TRF_DATE <=:endDate "
//						+" AND CONVERT(date,TRF_DATE,105)>=CONVERT(date,:startDate,105) " 
//						+" AND CONVERT(date,TRF_DATE,105)<= CONVERT(date,:endDate,105) "
//						+ "AND TRFHDR_STATUS='A'  order by TRF_NO";
//			}
			
			
			
			try {
				em = emf.createEntityManager();
				Query query = em.createNativeQuery(q,Tuple.class);
				query.setParameter("location", location);
				if(Integer.parseInt(rl)!=0) {query.setParameter("rl", rl);}
				query.setParameter("startDate", startDate);
				query.setParameter("endDate", endDate);
				List<Tuple> tuples = query.getResultList();
				if (tuples != null && !tuples.isEmpty()) {
					for(Tuple t : tuples) {
						Stock_Transfer_Header sth=new Stock_Transfer_Header();
						sth.setTrf_id(Long.valueOf(t.get("TRF_ID",Integer.class)));
						sth.setTrf_no(t.get("TRF_NO",String.class));
						list.add(sth);
					}
					if(!list.isEmpty() && list.size() > 0)
						return list;
					}
			}
			finally {
				em.close();
			}
			return list;
		}
		
		@Override
		public List<StockTransferNotePrint> getStocktransfernoteData(PrintBean bean) throws Exception {
			// TODO Auto-generated method stub
			
			List<StockTransferNotePrint> lst= null;
			EntityManager em = null;
			
			try {
				System.out.println("Stock note ");
				
				em = emf.createEntityManager();
				
				StoredProcedureQuery storedProcedurequery = em.createNamedStoredProcedureQuery("callSTOCK_TRANSFER_PRINT_WITHGST");
				storedProcedurequery.setParameter("loc_id",bean.getLocationId());
				storedProcedurequery.setParameter("cfa",bean.getCfa());
				storedProcedurequery.setParameter("frm_challan_no",bean.getFromChallan());
				storedProcedurequery.setParameter("to_challan_no",bean.getToChallan());
				storedProcedurequery.setParameter("rep_type",bean.getReportOption());
				storedProcedurequery.setParameter("frm_challan_dt",Utility.getDDMMYYYYToYYYYMMDD(bean.getFrm_dt()));
				storedProcedurequery.setParameter("to_challan_dt",Utility.getDDMMYYYYToYYYYMMDD(bean.getTo_dt()));
//				storedProcedurequery.setParameter("frm_challan_dt",bean.getFrm_dt());
//				storedProcedurequery.setParameter("to_challan_dt",bean.getTo_dt());
				
				
				lst = storedProcedurequery.getResultList();
				
				System.out.println("list :: "+lst.size());
				
			
				
			}
			catch (Exception e) {
				throw e;
			}
			finally {
				em.close();
			}
			
			return lst;
		}
		
		@Override
		public List<ViewStockWithdrawalVoucherPrint> getstockwithdrawaldata(PrintBean bean) throws Exception {
			// TODO Auto-generated method stub
			List<ViewStockWithdrawalVoucherPrint> lst= null;
			EntityManager em = null;
			try {
				System.out.println("Stock Withdrawal ");
				System.out.println("loc_id"+bean.getLocationId());
				System.out.println("frm_challan_no"+bean.getFromChallan());
				System.out.println("to_challan_no"+bean.getToChallan());
				System.out.println("Withdrawaltype"+" ");
				System.out.println("rep_type"+bean.getReportOption());
				
				em = emf.createEntityManager();
				if(bean.getFinyearflag().equals("CURRENT")) {
					StoredProcedureQuery storedProcedurequery = em.createNamedStoredProcedureQuery("callView_StockWithdrawalVoucherPrint");
					storedProcedurequery.setParameter("loc_id",bean.getLocationId());
					storedProcedurequery.setParameter("frm_challan_no",bean.getFromChallan());
					storedProcedurequery.setParameter("to_challan_no",bean.getToChallan());
					storedProcedurequery.setParameter("Withdrawaltype"," ");
					storedProcedurequery.setParameter("rep_type",bean.getReportOption());
					lst = storedProcedurequery.getResultList();
				}
				else {//
					StoredProcedureQuery storedProcedurequery = em.createNamedStoredProcedureQuery("callView_StockWithdrawalVoucherPrintPrevious");
					storedProcedurequery.setParameter("loc_id",bean.getLocationId());
					storedProcedurequery.setParameter("frm_challan_no",bean.getFromChallan());
					storedProcedurequery.setParameter("to_challan_no",bean.getToChallan());
					storedProcedurequery.setParameter("Withdrawaltype"," ");
					storedProcedurequery.setParameter("rep_type",bean.getReportOption());
					lst = storedProcedurequery.getResultList();
				}
				System.out.println("list :: "+lst.size());
				
			}
			catch (Exception e) {
				// TODO: handle exception
				System.out.println("Error Occurred :" +e.getMessage());// uncomment asneeded --;
			}
			return lst;
		}

		@Override
		public List<Period> getperiodsbyfinyear(String finyr) {
			// TODO Auto-generated method stub
			List<Period> lst= null;
			EntityManager em = null;
			try {
				em = emf.createEntityManager();
				  Query query = em.createQuery("SELECT l.period_code as period_code,l.period_alt_name as period_alt_name FROM Period l WHERE l.period_fin_year = :finyr");
			      query.setParameter("finyr", finyr);
			      lst= query.getResultList();
			      
			}
			catch (Exception e) {
				// TODO: handle exception
				throw e;
			}
			return lst;
		}

		@Override
		public List<SWV_Header> getstockwithdrawalpdfchallans(Long loc_id, String period_code, String year,
				String page_ind) throws Exception {
			EntityManager em = null;
			List<SWV_Header> lst = null;
			
			System.out.println("loc_id : "+loc_id);
			System.out.println("period_code : "+period_code);
			System.out.println("year : "+year);
			
			try {
				em = emf.createEntityManager();
				StringBuffer sb = new StringBuffer();
				sb.append("select distinct swv_challan_no  from SWV_Header  where swv_loc_id=:locid ");
				sb.append(" and swv_period_code=:period and swv_fin_year=:finyr ");
				sb.append(" and swv_status='A'");
				
				if(page_ind.equals("D")) {
					sb.append(" and swv_appr_status='A'");
				}
				else if (page_ind.equals("P")) {
					sb.append("  and (swv_appr_status='E' or swv_appr_status='F') ");
				}
				sb.append("  order by swv_challan_no asc");	
				
				Query query = em.createQuery(sb.toString());
				 query.setParameter("period", period_code);
				 query.setParameter("finyr", year);
				 query.setParameter("locid", loc_id);
				lst= query.getResultList();
				 
				System.out.println("List : "+lst.size());
			}
			catch (Exception e) {
				// TODO: handle exception
				throw e;
			}
			// TODO Auto-generated method stub
			return lst;
		}

		@Override
		public List<Team> getTeamList(String teamId) throws Exception {
			EntityManager em = null;
			List<Team> list = new ArrayList<Team>();
			try {
				em = emf.createEntityManager();
				String q="Select team_id,team_name,team_code from team where div_id =:divId";
				Query query = em.createNativeQuery(q,Tuple.class);
				query.setParameter("divId", teamId);
				List<Tuple> tuples = query.getResultList();			
				if (tuples != null && !tuples.isEmpty()) {
					for (Tuple t : tuples) {
						Team tm=new Team();
						tm.setTeam_id(Long.valueOf(t.get("team_id",Integer.class)));
						tm.setTeam_name(t.get("team_name",String.class));
						tm.setTeam_code(t.get("team_code",String.class));
						list.add(tm);
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
		public List<Dispatch_Header> getChallanListForGSTChallanPrintByTeamCode(String divId, String periodCode,
				String finYear, String location, String finyrflg, String teamCode) throws Exception {
			
			EntityManager em = null;
			List<Dispatch_Header> list=new ArrayList<Dispatch_Header>();
			try {
				em = emf.createEntityManager();
				StringBuffer q= new StringBuffer();
				
				System.out.println("teamcode:::"+teamCode+":::finyrflg :: "+finyrflg+"  finYear::"+finYear+"divId:::"+divId+"location:::"+location+"periodCode::"+periodCode);
				
				if(finyrflg.equals(MedicoConstants.CURRENT)) {
					q.append("select distinct DSP_CHALLAN_NO,DSP_ID from Dispatch_header ");
				}
				else {
					q.append("select distinct DSP_CHALLAN_NO,DSP_ID from Dispatch_header_arc ");
				}
				q.append(" where dsp_div_id=:divId and DSP_LOC_ID=:location and DSP_PERIOD_CODE=:periodCode and DSP_FIN_YEAR=:finYear ");
				if(!teamCode.equals("0")) {
					q.append(" and TEAM_CODE=:teamCode");  //added 24-08-21
				}
				q.append(" and DSP_STATUS='A' and DSP_APPR_STATUS='A' and DSP_LBL_PRINT IN ('y','N') order by DSP_CHALLAN_NO asc");
				
				Query query = em.createNativeQuery(q.toString(),Tuple.class);
				query.setParameter("divId", divId);
				query.setParameter("periodCode", periodCode);
				query.setParameter("finYear", finYear);
				query.setParameter("location", location);
				if(!teamCode.equals("0")) {
				query.setParameter("teamCode", teamCode);
				}
				List<Tuple> tuples = query.getResultList();
				if (tuples != null && !tuples.isEmpty()) {
					for(Tuple t : tuples) {
						Dispatch_Header dh=new Dispatch_Header();
						dh.setDspChallanNo(t.get("DSP_CHALLAN_NO",String.class));
						dh.setDsp_id(Long.valueOf(t.get("DSP_ID",Integer.class)));
						list.add(dh);
					}
					if(!list.isEmpty() && list.size() > 0)
						return list;
					}
			}
			finally {
				em.close();
			}
			return list;
		}
		
		@Override
		public List<SWV_Header_arc> getstockwithdrawalpdfchallansPreviousYear(Long loc_id, String period_code, String year,
				String page_ind) throws Exception {
			EntityManager em = null;
			List<SWV_Header_arc> lst = null;
			
			System.out.println("loc_id : "+loc_id);
			System.out.println("period_code : "+period_code);
			System.out.println("year : "+year);
			
			try {
				em = emf.createEntityManager();
				StringBuffer sb = new StringBuffer();
				sb.append("select distinct swv_challan_no  from SWV_Header_arc  where swv_loc_id=:locid ");
				sb.append(" and swv_period_code=:period and swv_fin_year=:finyr ");
				sb.append(" and swv_status='A'");
				
				if(page_ind.equals("D")) {
					sb.append(" and swv_appr_status='A'");
				}
				else if (page_ind.equals("P")) {
					sb.append("  and (swv_appr_status='E' or swv_appr_status='F') ");
				}
				sb.append("  order by swv_challan_no asc");	
				
				Query query = em.createQuery(sb.toString());
				 query.setParameter("period", period_code);
				 query.setParameter("finyr", year);
				 query.setParameter("locid", loc_id);
				lst= query.getResultList();
				 
				System.out.println("List : "+lst.size());
			}
			catch (Exception e) {
				// TODO: handle exception
				throw e;
			}
			// TODO Auto-generated method stub
			return lst;
		}
		
		@Override
		public List<Dispatch_Header> getChallanListForGSTSummaryChallanPrintPrevious(String reportOption, String divId,
				String periodCode, String finYear, String location) throws Exception {
			
			EntityManager em = null;
			List<Dispatch_Header> list=new ArrayList<Dispatch_Header>();
			String q="";
			try {
				em = emf.createEntityManager();
				if(reportOption.equals("tcfa")) {
					q="SELECT distinct SH.sumdsp_challan_no as DSP_CHALLAN_NO,SH.sumdsp_id as DSP_ID " + 
							" FROM  SUM_DISP_HEADER_arc SH left join Dispatch_header_arc DH on SH.SUMDSP_ID=DH.DSP_SUM_DSP_ID " + 
							" WHERE SH.sumdsp_div_id=:divId AND SH.sumdsp_loc_id=:location AND " + 
							" SH.sumdsp_period_code=:periodCode AND SH.sumdsp_fin_year=:finYear AND SH.SUMDSP_status='A' " + 
							" and DH.dsp_appr_status in ('E','F','A') ORDER By SH.sumdsp_challan_no";
				}
				if(reportOption.equals("dtp")) {
					q="select distinct DSP_CHALLAN_NO,DSP_ID from Dispatch_header_arc "
							+ "where dsp_div_id=:divId and DSP_LOC_ID=:location and DSP_PERIOD_CODE=:periodCode and DSP_FIN_YEAR=:finYear "
							+ "and DSP_STATUS='A' and DSP_APPR_STATUS IN ('A','E','F') order by DSP_CHALLAN_NO asc";
				}
				Query query = em.createNativeQuery(q,Tuple.class);
				query.setParameter("divId", divId);
				query.setParameter("periodCode", periodCode);
				query.setParameter("finYear", finYear);
				query.setParameter("location", location);
				List<Tuple> tuples = query.getResultList();
				if (tuples != null && !tuples.isEmpty()) {
					for(Tuple t : tuples) {
						Dispatch_Header dh=new Dispatch_Header();
						dh.setDspChallanNo(t.get("DSP_CHALLAN_NO",String.class));
						dh.setDsp_id(Long.valueOf(t.get("DSP_ID",Integer.class)));
						list.add(dh);
					}
					if(!list.isEmpty() && list.size() > 0)
						return list;
					}
			}
			finally {
				em.close();
			}
			return list;
		}

		@Override
		public List<ViewPrePrintedDetailChallan> getDeclarationPrint(Integer divId, Integer locid, String frmchallan,
				String tochallan, String dispatchtype, String prodtype, String reptype, String finyearflag,
				String finId) {
			List<ViewPrePrintedDetailChallan> data = null ;
			System.out.println("reptype "+reptype);
			EntityManager em=null;
			try {
				em = emf.createEntityManager();
				StoredProcedureQuery storedprocquery = em.createNamedStoredProcedureQuery("callPrePrintedDetailChallan");
				storedprocquery.setParameter("div_id", divId.toString());
				storedprocquery.setParameter("loc_id", locid.toString());
				storedprocquery.setParameter("frm_challan_no", frmchallan);
				storedprocquery.setParameter("to_challan_no", tochallan);
				storedprocquery.setParameter("dispatchtype", dispatchtype);
				storedprocquery.setParameter("prod_type", prodtype);
				storedprocquery.setParameter("rep_type", reptype);
				storedprocquery.setParameter("fin_year_flag", finyearflag);
				storedprocquery.setParameter("fin_year_id", finId);
				data = storedprocquery.getResultList();
			}catch(Exception e) {
				System.out.println("Error Occurred :" +e.getMessage());// uncomment asneeded --;
			}
			finally {
				if(em!=null) {em.close();}
			}
			return data;
		}

		@Override
		public List<PrePrintedDetailChallan_withgstPG> getPreprintedDetailedChallanDataPG(String division, String loc,
				String from_challan, String to_challan, String dispatchtype, String product_type, String rep_type,
				PrintBean bean) throws Exception {
			List<PrePrintedDetailChallan_withgstPG> lst = null;
			EntityManager em=null;
			try {
				
				System.out.println("PrePrintedDetailChallan_withgstPg");
				System.out.println("DivId:::"+division);
				System.out.println("loc_id:"+loc);
				System.out.println("frm_challan_no:"+from_challan);
				System.out.println("to_challan_no:"+to_challan);
				System.out.println("dispatchtype:"+dispatchtype);
				System.out.println("prod_type:"+product_type);
				System.out.println("rep_type:"+rep_type);
				System.out.println("fin_year_flag"+ bean.getFinyearflg());
				System.out.println("fin_year_id"+ bean.getFinyearflag());
				if(product_type==null) {
					product_type = "";
				}
				
				em = emf.createEntityManager();
						
					StoredProcedureQuery storedProcedurequery = em.createNamedStoredProcedureQuery("callPrePrintedDetailChallan_withgstPg");
					storedProcedurequery.setParameter("div_id", division);
					storedProcedurequery.setParameter("loc_id", loc);
					storedProcedurequery.setParameter("frm_challan_no", from_challan);
					storedProcedurequery.setParameter("to_challan_no", to_challan);
					storedProcedurequery.setParameter("dispatchtype", dispatchtype);
					storedProcedurequery.setParameter("prod_type", product_type);
					storedProcedurequery.setParameter("rep_type", rep_type);
					storedProcedurequery.setParameter("fin_year_flag", bean.getFinyearflg());
					storedProcedurequery.setParameter("fin_year_id", bean.getFinyearflag());
					lst = storedProcedurequery.getResultList();
				
					System.out.println("List : "+lst.size());
			}
			finally {
				if(em!=null)
				{
					em.close();
				}		
			}
			return lst;
		}
		@Override
		public List<Dispatch_Header> getChallanBtwDtPkgList(String pageIndQuery, String ro, String divId, String periodCode,
				String finYear, String location, String finyrflg, String cnfLocation,
				HttpSession session) {
			EntityManager em = null;
			List<Dispatch_Header> list = null;
			StringBuffer sb = new StringBuffer();
			
			try {
				System.out.println("divId" + divId);
				System.out.println("periodCode" + periodCode);
				System.out.println("finYear" + finYear);
				System.out.println("location" + location);
				System.out.println("finyrflg" + finyrflg);
				
				pageIndQuery = pageIndQuery.trim();
				
				sb.append("Select dh.dsp_challan_no,dh.dsp_id from Dispatch_header dh where dh.dsp_div_id =:divId and dh.dsp_loc_id =:location and ");
				sb.append(" dh.dsp_period_code =:periodCode and  dh.dsp_fin_year =:finYear and dh.dsp_status='A'  ");
				if (pageIndQuery != null) {
					if (pageIndQuery.equals("D")) {
						sb.append(" and dh.dsp_appr_status ='A'");
					} else if (pageIndQuery.equals("P")) {
						sb.append(" and dh.dsp_appr_status ='E' or dh.dsp_appr_status ='F' ");
					} 
				}

//				if (cnfLocation != null && cnfLocation.length() > 0) {
//					if (Integer.parseInt(cnfLocation) > 0) {
//						sb.append(" and dh.dsp_recvg_loc.dptLoc_id =:cnfLocation");
//					}	
//					
//			}
		
				sb.append(" order by dh.dsp_challan_no");

				Query query = entityManager.createNativeQuery(sb.toString(), Tuple.class);
				query.setParameter("divId", divId);
				query.setParameter("location", location);
				query.setParameter("finYear", finYear);
				query.setParameter("periodCode", periodCode);
				
				List<Tuple> tuples = query.getResultList();
				System.out.println("size ::"+tuples.size());
				if (tuples != null && !tuples.isEmpty()) {
					list = new ArrayList<>();
					Dispatch_Header d = null;
					for (Tuple t : tuples) {
						d = new Dispatch_Header();
						d.setDspChallanNo(t.get("dsp_challan_no", String.class));
						d.setDsp_id(Long.valueOf(t.get("dsp_id", Integer.class)));
						list.add(d);
					}
					if (!list.isEmpty() && list.size() > 0)
						return list;
				}

				System.out.println(list.size());

			} finally {
				if (em != null) {
					em.close();
				}
			}
			return list;
		}

		public List<NOV_BillOfSupply_Challan> getDeliveryChallanPrint(PrintBean pb) throws Exception {
			List<NOV_BillOfSupply_Challan> data = null;
			EntityManager em = null;
			try {
				System.out.println("div_id" + pb.getTeamDivision());
				System.out.println("loc_id" + pb.getLocation());
				System.out.println("frm_challan_no" + pb.getFromChallan());
				System.out.println("to_challan_no" + pb.getToChallan());
				System.out.println("dispatchtype" + pb.getReportOption());
				System.out.println("rep_type" + pb.getPageIndQuery());
				em = emf.createEntityManager();
				StoredProcedureQuery storedprocquery = em
						.createNamedStoredProcedureQuery("callPrePrintedDetailChallan_WITHOUTGST");
				storedprocquery.setParameter("div_id", pb.getTeamDivision());
				storedprocquery.setParameter("loc_id", pb.getLocation());
				storedprocquery.setParameter("frm_challan_no", pb.getFromChallan());
				storedprocquery.setParameter("to_challan_no", pb.getToChallan());
				storedprocquery.setParameter("dispatchtype", pb.getReportOption());
				storedprocquery.setParameter("prod_type", pb.getProdType() == null ? "" : pb.getProdType());
				storedprocquery.setParameter("rep_type", "D");

				data = storedprocquery.getResultList();
			} finally {
				if (em != null) {
					em.close();
				}
			}
			return data;
		}

		@Override
		public List<ViewPrePrintedSummaryChallan_GST_EInvoiceStockist> PrePrintedSummaryChallan_GST_EInvoicedataStocksit(
				String division, String loc, String frm_challan, String to_challan, String prodtype, String rep_type,
				PrintBean bean) throws Exception {

			List<ViewPrePrintedSummaryChallan_GST_EInvoiceStockist> challans = null;
			EntityManager em = null;
			try {

				System.out.println("rep_type " +rep_type);
				System.out.println("div_id " +division);
				System.out.println("frm " +frm_challan);
				System.out.println("to " +to_challan);
				System.out.println("loc_id " +loc);
				//System.out.println("dispatchType " +dispatchType);
				System.out.println("prodtype " +prodtype);
				System.out.println("rep_type " +rep_type);
				System.out.println("fin_year_flag " +bean.getFinyearflg());
				System.out.println("fin_year_id " +bean.getFinyearflag());
				
				em = emf.createEntityManager();
				
				StoredProcedureQuery storedprocedurequery = em.createNamedStoredProcedureQuery("callPrePrintedSummaryChallan_GST_EINV_Stk");
				
				storedprocedurequery.setParameter("div_id", division);
				storedprocedurequery.setParameter("frm_challan_no", frm_challan);
				storedprocedurequery.setParameter("to_challan_no", to_challan);
				storedprocedurequery.setParameter("loc_id", loc);
				storedprocedurequery.setParameter("prod_type", "");
				storedprocedurequery.setParameter("rep_type", rep_type);
				storedprocedurequery.setParameter("fin_year_flag",bean.getFinyearflg());
				storedprocedurequery.setParameter("fin_year_id",bean.getFinyearflag());
				
				challans = storedprocedurequery.getResultList();
			}
			catch (Exception e) {
			throw e;
			}finally {
				if(em!=null)
				{
					em.close();
				}	
			}
			
			return challans;
		
		}

		@Override
		public List<Dispatch_Header> getChallanListForGSTSummaryChallanPrintSockist(String reportOption, String divId,
				String periodCode, String finYear, String location) throws Exception {
			
			EntityManager em = null;
			List<Dispatch_Header> list=new ArrayList<Dispatch_Header>();
			String q="";
			try {
				em = emf.createEntityManager();
//				if(reportOption.equals("tcfa")) {
//					q="SELECT distinct SH.sumdsp_challan_no as DSP_CHALLAN_NO,SH.sumdsp_id as DSP_ID " + 
//							" FROM  SUM_DISP_HEADER SH left join Dispatch_header DH on SH.SUMDSP_ID=DH.DSP_SUM_DSP_ID " + 
//							" WHERE SH.sumdsp_div_id=:divId AND SH.sumdsp_loc_id=:location AND " + 
//							" SH.sumdsp_period_code=:periodCode AND SH.sumdsp_fin_year=:finYear AND SH.SUMDSP_status='A' " + 
//							" and DH.dsp_appr_status in ('E','F','A') ORDER By SH.sumdsp_challan_no";
//				}
//				if(reportOption.equals("dtp")) {
					q="select distinct DSP_CHALLAN_NO,DSP_ID from Dispatch_header "
							+ "where dsp_div_id=:divId and DSP_LOC_ID=:location and DSP_PERIOD_CODE=:periodCode and DSP_FIN_YEAR=:finYear "
							+ "and DSP_STATUS='A' and DSP_APPR_STATUS IN ('A','E','F') order by DSP_CHALLAN_NO asc";
//				}
				Query query = em.createNativeQuery(q,Tuple.class);
				query.setParameter("divId", divId);
				query.setParameter("periodCode", periodCode);
				query.setParameter("finYear", finYear);
				query.setParameter("location", location);
				List<Tuple> tuples = query.getResultList();
				if (tuples != null && !tuples.isEmpty()) {
					for(Tuple t : tuples) {
						Dispatch_Header dh=new Dispatch_Header();
						dh.setDspChallanNo(t.get("DSP_CHALLAN_NO",String.class));
						dh.setDsp_id(Long.valueOf(t.get("DSP_ID",Integer.class)));
						list.add(dh);
					}
					if(!list.isEmpty() && list.size() > 0)
						return list;
					}
			}
			finally {
				em.close();
			}
			return list;
		}

		@Override
		public List<Dispatch_Header> getChallanListForGSTSummaryChallanPrintPreviousStockist(String reportOption, String divId,
				String periodCode, String finYear, String location) throws Exception {
			
			EntityManager em = null;
			List<Dispatch_Header> list=new ArrayList<Dispatch_Header>();
			String q="";
			try {
				em = emf.createEntityManager();
//				if(reportOption.equals("tcfa")) {
//					q="SELECT distinct SH.sumdsp_challan_no as DSP_CHALLAN_NO,SH.sumdsp_id as DSP_ID " + 
//							" FROM  SUM_DISP_HEADER_arc SH left join Dispatch_header_arc DH on SH.SUMDSP_ID=DH.DSP_SUM_DSP_ID " + 
//							" WHERE SH.sumdsp_div_id=:divId AND SH.sumdsp_loc_id=:location AND " + 
//							" SH.sumdsp_period_code=:periodCode AND SH.sumdsp_fin_year=:finYear AND SH.SUMDSP_status='A' " + 
//							" and DH.dsp_appr_status in ('E','F','A') ORDER By SH.sumdsp_challan_no";
//				}
//				if(reportOption.equals("dtp")) {
					q="select distinct DSP_CHALLAN_NO,DSP_ID from Dispatch_header_arc "
							+ "where dsp_div_id=:divId and DSP_LOC_ID=:location and DSP_PERIOD_CODE=:periodCode and DSP_FIN_YEAR=:finYear "
							+ "and DSP_STATUS='A' and DSP_APPR_STATUS IN ('A','E','F') order by DSP_CHALLAN_NO asc";
//				}
				Query query = em.createNativeQuery(q,Tuple.class);
				query.setParameter("divId", divId);
				query.setParameter("periodCode", periodCode);
				query.setParameter("finYear", finYear);
				query.setParameter("location", location);
				List<Tuple> tuples = query.getResultList();
				if (tuples != null && !tuples.isEmpty()) {
					for(Tuple t : tuples) {
						Dispatch_Header dh=new Dispatch_Header();
						dh.setDspChallanNo(t.get("DSP_CHALLAN_NO",String.class));
						dh.setDsp_id(Long.valueOf(t.get("DSP_ID",Integer.class)));
						list.add(dh);
					}
					if(!list.isEmpty() && list.size() > 0)
						return list;
					}
			}
			finally {
				em.close();
			}
			return list;
		}


}
