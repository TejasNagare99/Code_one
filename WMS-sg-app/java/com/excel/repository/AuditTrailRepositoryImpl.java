package com.excel.repository;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Query;
import javax.persistence.StoredProcedureQuery;
import javax.persistence.Tuple;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.excel.bean.AuditTrailBean;
import com.excel.exception.MedicoException;
import com.excel.model.Audit_log;
import com.excel.model.BatchMasterAuditTrailModel;
import com.excel.model.DispatchAuditTrailBean;
import com.excel.model.FieldMasterAttrib;
import com.excel.model.FinYear;
import com.excel.model.GrnAuditTrailBean;
import com.excel.model.HqMasterAuditTrailModel;
import com.excel.model.ItemMasterAuditTrail;
import com.excel.model.Supplier;
import com.excel.model.SupplierMasterModel;
import com.excel.model.ViewDownloadIaaAuditTrail;
import com.excel.utility.MedicoResources;

@Repository
public class AuditTrailRepositoryImpl implements AuditTrailRepository{
	
	@Autowired private EntityManagerFactory emf;
	
	
	@Override
	public List<FieldMasterAttrib> getFieldMasterAuditTraildata(AuditTrailBean bean) throws Exception {
		// TODO Auto-generated method stub
		
		List<FieldMasterAttrib> lst = null;
		EntityManager em = null;
		
		System.out.println("LEVEL::"+bean.getLevel());
		System.out.println("AUDIT::"+bean.getAudit());
		System.out.println("FSTAFFTRAINING::"+ bean.getFstafftraining());
		System.out.println("DIVID::"+ bean.getDivId());
		System.out.println("startdt  "+MedicoResources.convertUtilDateToString_DD_MM_YYYY(bean.getStartdate()));
		System.out.println("enddt  "+MedicoResources.convertUtilDateToString_DD_MM_YYYY(bean.getEnddate()));
		
		try {
			em = emf.createEntityManager();
			StoredProcedureQuery query  = em.createNamedStoredProcedureQuery("FieldMaster_Download");
			query.setParameter("LEVEL",bean.getLevel());
			query.setParameter("AUDIT", bean.getAudit());
			query.setParameter("FSTAFFTRAINING", bean.getFstafftraining());
			query.setParameter("DIVID", bean.getDivId());
			query.setParameter("STARTDT", MedicoResources.convertUtilDateToString_DD_MM_YYYY(bean.getStartdate()));
			query.setParameter("ENDDT", MedicoResources.convertUtilDateToString_DD_MM_YYYY(bean.getEnddate()));
			
			lst = query.getResultList();
			
			System.out.println("List ::"+lst.size());
			
		}catch(Exception e) {
			throw e;
		}
		finally {
			if(em != null) { em.close(); }
		}
		if (lst != null && !lst.isEmpty()) {
			return lst;
		}
		return null;
	}
	
	@Override
	public List<ItemMasterAuditTrail> getProductMasterAudit_Trail_Report_Data(AuditTrailBean bean) throws Exception {
		
		List<ItemMasterAuditTrail> list =null;
		EntityManager em = null;
		try {
			em = emf.createEntityManager();
			StoredProcedureQuery query  = em.createNamedStoredProcedureQuery("ItemMasterAuditTrail");
			query.setParameter("Divid",bean.getDivId());
			query.setParameter("SubCompany",bean.getSubcompany());
			query.setParameter("Brand",bean.getBrand());
//			query.setParameter("Brand","103");
			query.setParameter("SmpProdId",bean.getProd_id());
			query.setParameter("SmpProdtype",bean.getProduct_type());
			
			
			
			list = query.getResultList();
			
			System.out.println("List :: "+list.size());
		}catch(Exception e) {
			throw e;
		}finally {
			if(em != null) { em.close(); }
		}
		if (list != null && !list.isEmpty()) {
			return list;
		}
		
		return null;
	}
	
	@Override
	public List<FinYear> getConcatedFinYearListForAuditReport(){
		EntityManager em = null;
		List<FinYear> list = new ArrayList<FinYear>();
		try {
			em = emf.createEntityManager();
			Query query = null;
				
			String q="select FIN_YEAR, FIN_START_DATE,FIN_END_DATE, concat " + 
					"(FIN_START_DATE, FIN_END_DATE) AS MergeFinYear from FINYEAR order by FIN_YEAR ASC";
			query = em.createNativeQuery(q,Tuple.class);
			List<Tuple> tuples = query.getResultList();
			
			if (tuples != null && !tuples.isEmpty()) {
				for (Tuple t : tuples) {
					FinYear f=new FinYear();
					f.setFin_year(t.get("FIN_YEAR",String.class));
					f.setFin_start_date(t.get("FIN_START_DATE",Date.class));
					f.setFin_end_date(t.get("FIN_END_DATE",Date.class));
					f.setFin_current(t.get("MergeFinYear",String.class));
					list.add(f);
				}
			}
			} finally {
				if (em != null) { em.close(); }
		}
		return list;
	}
	
	@Override
	public List<SupplierMasterModel> getSupplierMaster_Reportdata(AuditTrailBean bean) throws Exception {
		
		List<SupplierMasterModel> list = null;
		EntityManager em = null;
		
		try {
			
			em  = emf.createEntityManager();
			
			StoredProcedureQuery query  = em.createNamedStoredProcedureQuery("SupplierMasterModel");
			query.setParameter("SupType",bean.getSuptype());
			query.setParameter("startdate",bean.getFrmdt());
			query.setParameter("endDate",bean.getTodt());
			
			list = query.getResultList();
			System.out.println("List :: "+list.size());
		}catch(Exception e) {
			throw e;
		}finally {
			if(em != null) { em.close(); }
		}
		if (list != null && !list.isEmpty()) {
			return list;
		}
		
		return null;
	}
	@Override
	public List<HqMasterAuditTrailModel> getHqmasteraudittraildata(AuditTrailBean bean) throws Exception {
		List<HqMasterAuditTrailModel>list =  null;
		EntityManager em = null;
		try {
			System.out.println("Hq Master Audit Trail");
			
			System.out.println("divid"+bean.getDivId());
			
			em = emf.createEntityManager();
			
			
			StoredProcedureQuery storedProcedurequery = em.createNamedStoredProcedureQuery("HqMasterAuditTrail");
			storedProcedurequery.setParameter("DIVID",bean.getDivId());
			
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
	
	@Override
	public List<BatchMasterAuditTrailModel> getBatchMasterData(AuditTrailBean bean) throws Exception {
		// TODO Auto-generated method stub
		
		List<BatchMasterAuditTrailModel> list = null;
		EntityManager em = null;
		
		try {
			
			em  = emf.createEntityManager();
			StoredProcedureQuery query  = em.createNamedStoredProcedureQuery("BatchMasterAuditTrail");
			query.setParameter("SmpProdId",bean.getProd_id());
			query.setParameter("LocId",bean.getLocId());
			query.setParameter("startdate",bean.getFrmdt());
			query.setParameter("endDate",bean.getTodt());
			
			
			list = query.getResultList();
			System.out.println("List :: "+list.size());
		}catch(Exception e) {
			throw e;
		}finally {
			if(em != null) { em.close(); }
		}
		if (list != null && !list.isEmpty()) {
			return list;
		}
		
		return list;
	}
	
	@Override
	public List<GrnAuditTrailBean> getGrnAuditDownloadData(AuditTrailBean bean) throws Exception {
		List<GrnAuditTrailBean> list = null;
		EntityManager em = null;
		try {
			
			em = emf.createEntityManager();
			StoredProcedureQuery grnauditdata  = em.createNamedStoredProcedureQuery("callGrnAuditTrailBean");
			grnauditdata.setParameter("startdate", MedicoResources.convertUtilDateToString_DD_MM_YYYY(bean.getFromDate()));
			grnauditdata.setParameter("endDate", MedicoResources.convertUtilDateToString_DD_MM_YYYY(bean.getToDate()));
			grnauditdata.setParameter("apprLevel", bean.getApproval());
			
			list = grnauditdata.getResultList();
		
			  System.out.println("öut :: "+ list.size());
			 
		} finally {
			if(em != null) { em.close(); }
		}
		if (list != null && !list.isEmpty()) {
			return list;
		}
		return null;
	
		// TODO Auto-generated method stub
	}
	@Override
	public List<DispatchAuditTrailBean> getDispatchAuditDownloadData(AuditTrailBean bean) throws Exception {
		List<DispatchAuditTrailBean> list = null;
		EntityManager em = null;
		try {
			
			em = emf.createEntityManager();
			StoredProcedureQuery dispatchauditdata  = em.createNamedStoredProcedureQuery("callDispatchAuditTrailBean");
			dispatchauditdata.setParameter("startdate", MedicoResources.convertUtilDateToString_DD_MM_YYYY(bean.getFromDate()));
			dispatchauditdata.setParameter("endDate", MedicoResources.convertUtilDateToString_DD_MM_YYYY(bean.getToDate()));
			
			list = dispatchauditdata.getResultList();
			System.out.println("dispatch audit trial download List size ::::"+list.size());
		} finally {
			if(em != null) { em.close(); }
		}
		if (list != null && !list.isEmpty()) {
			return list;
		}
		return null;
	
		// TODO Auto-generated method stub
	}
	@Override
	public List<ViewDownloadIaaAuditTrail> getViewIAAVoucherPrintingdata(AuditTrailBean bean) throws Exception {
		List<ViewDownloadIaaAuditTrail> list = null;
		EntityManager em = null;
		
		try {System.out.println("DownloadIaaAuditTrail");
		em = emf.createEntityManager();
		
		StoredProcedureQuery storedProcedurequery = em.createNamedStoredProcedureQuery("callViewDownloadIaaAuditTrail");
		storedProcedurequery.setParameter("startdate",MedicoResources.convertUtilDateToString_DD_MM_YYYY(bean.getStartdate()));
		storedProcedurequery.setParameter("endDate",MedicoResources.convertUtilDateToString_DD_MM_YYYY(bean.getEnddate()));
		
		list = storedProcedurequery.getResultList();
		
		System.out.println("result size " +list.size());}
		catch (Exception e) {
			throw e;
		}
		finally {
			if(em != null) { em.close(); }
		}
		return list;
	}
	
	@Override
	public List<GrnAuditTrailBean> getGrnAuditDownloadDataforPrevious(AuditTrailBean bean) throws Exception {
		List<GrnAuditTrailBean> list = null;
		EntityManager em = null;
		try {
			em = emf.createEntityManager();
			StoredProcedureQuery grnauditdata  = em.createNamedStoredProcedureQuery("callGrnAuditTrailBeanprevious");
			grnauditdata.setParameter("startdate", MedicoResources.convertUtilDateToString_DD_MM_YYYY(bean.getFromDate()));
			grnauditdata.setParameter("endDate", MedicoResources.convertUtilDateToString_DD_MM_YYYY(bean.getToDate()));
			grnauditdata.setParameter("apprLevel", bean.getApproval());
			
			list = grnauditdata.getResultList();
		
			  System.out.println("öut :: "+ list.size());
			 
		} finally {
			if(em != null) { em.close(); }
		}
		if (list != null && !list.isEmpty()) {
			return list;
		}
		return null;
	
		// TODO Auto-generated method stub
	}

	@Override
	public List<DispatchAuditTrailBean> getDispatchAuditDownloadDataforPrevious(AuditTrailBean bean) throws Exception {
		List<DispatchAuditTrailBean> list = null;
		EntityManager em = null;
		try {
			
			em = emf.createEntityManager();
			StoredProcedureQuery dispatchauditdata  = em.createNamedStoredProcedureQuery("callDispatchAuditTrailBeanprevious");
			dispatchauditdata.setParameter("startdate", MedicoResources.convertUtilDateToString_DD_MM_YYYY(bean.getFromDate()));
			dispatchauditdata.setParameter("endDate", MedicoResources.convertUtilDateToString_DD_MM_YYYY(bean.getToDate()));
			
			list = dispatchauditdata.getResultList();
			System.out.println("dispatch audit trial download List size ::::"+list.size());
		} finally {
			if(em != null) { em.close(); }
		}
		if (list != null && !list.isEmpty()) {
			return list;
		}
		return null;
	
		// TODO Auto-generated method stub
	}

	@Override
	public List<Audit_log> getAuditLogDataFromProcedure(String startDate, String endDate, String finYear,
			String finYearFlag) {
		List<Audit_log> list = null;
		EntityManager em = null;
		try {
			
			em = emf.createEntityManager();
			StoredProcedureQuery auditLogData  = em.createNamedStoredProcedureQuery("callAUDIT_LOG_REPORT");
			auditLogData.setParameter("startdate", startDate);
			auditLogData.setParameter("endDate", endDate);
			auditLogData.setParameter("FIN_YEAR_FLAG", finYearFlag);
			auditLogData.setParameter("FIN_YEAR", finYear);
			list = auditLogData.getResultList();
			System.out.println("audit log list size ::::"+list.size());
		} finally {
			if(em != null) { em.close(); }
		}
		if (list != null && !list.isEmpty()) {
			return list;
		}
		return null;
	
	}
	
	@Override
	public List<FieldMasterAttrib> getFieldMasterResignedAuditTraildata(AuditTrailBean bean) throws Exception {
		// TODO Auto-generated method stub
		
		List<FieldMasterAttrib> lst = null;
		EntityManager em = null;
		
		System.out.println("LEVEL::"+bean.getLevel());
		System.out.println("AUDIT::"+bean.getAudit());
		System.out.println("FSTAFFTRAINING::"+ bean.getFstafftraining());
		System.out.println("DIVID::"+ bean.getDivId());
		System.out.println("startdt  "+MedicoResources.convertUtilDateToString_DD_MM_YYYY(bean.getStartdate()));
		System.out.println("enddt  "+MedicoResources.convertUtilDateToString_DD_MM_YYYY(bean.getEnddate()));
		
		try {
			em = emf.createEntityManager();
			StoredProcedureQuery query  = em.createNamedStoredProcedureQuery("FieldMasterResigned_Download");
			query.setParameter("LEVEL",bean.getLevel());
			query.setParameter("AUDIT", bean.getAudit());
			query.setParameter("FSTAFFTRAINING", bean.getFstafftraining());
			query.setParameter("DIVID", bean.getDivId());
			query.setParameter("STARTDT", MedicoResources.convertUtilDateToString_DD_MM_YYYY(bean.getStartdate()));
			query.setParameter("ENDDT", MedicoResources.convertUtilDateToString_DD_MM_YYYY(bean.getEnddate()));
			
			lst = query.getResultList();
			
			System.out.println("List ::"+lst.size());
			
		}catch(Exception e) {
			throw e;
		}
		finally {
			if(em != null) { em.close(); }
		}
		if (lst != null && !lst.isEmpty()) {
			return lst;
		}
		return null;
	}
}
