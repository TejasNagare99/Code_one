package com.excel.repository;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.StoredProcedureQuery;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.excel.bean.ReportBean;
import com.excel.model.GSTR_1_B2BA_INV;
import com.excel.model.GSTR_1_B2B_INV;
import com.excel.model.ViewGSTR_1_B2CLA_INV;
import com.excel.model.ViewGSTR_1_B2CL_INV;
import com.excel.model.ViewGSTR_1_B2CSA_INV;
import com.excel.model.ViewGSTR_1_B2CS_INV;
import com.excel.model.ViewGSTR_1_HSN_INV;
import com.excel.model.ViewGSTR_1_TRANSFER;
import com.excel.utility.MedicoResources;

@Repository
public class GstR1DownloadsRepositoryImpl implements GstR1DownloadsRepository {

	@Autowired
	private EntityManagerFactory emf;

	@Override
	public List<GSTR_1_B2BA_INV> getB2BAINVReportData(ReportBean bean) {
		List<GSTR_1_B2BA_INV> data = new ArrayList<>();

		EntityManager em = null;
		try {

			em = emf.createEntityManager();


			       

			StoredProcedureQuery query = em.createNamedStoredProcedureQuery("callGSTR_1_B2BA_INV");
				query.setParameter("FIN_YEAR_FLAG", bean.getFinyear());
				query.setParameter("FINID", bean.getFinyearflag());
				query.setParameter("COMP_CD", bean.getCompcd());
				query.setParameter("ST_DT", MedicoResources.convertUtilDateToString(bean.getStartDate()));
				query.setParameter("EN_DT", MedicoResources.convertUtilDateToString(bean.getEndDate()));
				query.setParameter("LOCID", bean.getSendLocId());

			data = query.getResultList();


		} catch (Exception e) {
			e.printStackTrace();
		}
		return data;
	}

	@Override
	public List<GSTR_1_B2B_INV> getB2BReportData(ReportBean bean) {
		List<GSTR_1_B2B_INV> data = new ArrayList<>();

		EntityManager em = null;
		try {

			em = emf.createEntityManager();



			StoredProcedureQuery query = em.createNamedStoredProcedureQuery("callGSTR_1_B2BIN_INV");
				query.setParameter("FIN_YEAR_FLAG", bean.getFinyear());
				query.setParameter("FINID", bean.getFinyearflag());
				query.setParameter("COMP_CD", bean.getCompcd());
				query.setParameter("ST_DT", MedicoResources.convertUtilDateToString(bean.getStartDate()));
				query.setParameter("EN_DT", MedicoResources.convertUtilDateToString(bean.getEndDate()));
				query.setParameter("LOCID", bean.getSendLocId());

			data = query.getResultList();


		} catch (Exception e) {
			e.printStackTrace();
		}
		return data;
	}
	
	@Override
	public List<ViewGSTR_1_B2CS_INV> getB2CSReportData(ReportBean bean) throws Exception {
		
		EntityManager em = null;
		List<ViewGSTR_1_B2CS_INV> list = null;
		try {
			

			em = emf.createEntityManager();
			StoredProcedureQuery query  = em.createNamedStoredProcedureQuery("callGSTR_1_B2CS_INV");
				query.setParameter("FIN_YEAR_FLAG", bean.getFinyear());
				query.setParameter("FINID", bean.getFinyearflag());
				query.setParameter("COMP_CD", bean.getCompcd());
				query.setParameter("ST_DT", MedicoResources.convertUtilDateToString(bean.getStartDate()));
				query.setParameter("EN_DT", MedicoResources.convertUtilDateToString(bean.getEndDate()));
				query.setParameter("LOCID", bean.getSendLocId());
			list = query.getResultList();
			System.out.println("List size " + list.size());
		}
		catch (Exception e) {
			
			throw e;
		}
		return list;
	}
	
	@Override
	public List<ViewGSTR_1_B2CL_INV> getB2CLReportData(ReportBean bean) throws Exception {
		
		EntityManager em = null;
		List<ViewGSTR_1_B2CL_INV> list = null;
		try {
					
			em = emf.createEntityManager();
			StoredProcedureQuery query  = em.createNamedStoredProcedureQuery("callGSTR_1_B2CL_INV");
				query.setParameter("FIN_YEAR_FLAG", bean.getFinyear());
				query.setParameter("FINID", bean.getFinyearflag());
				query.setParameter("COMP_CD", bean.getCompcd());
				query.setParameter("ST_DT", MedicoResources.convertUtilDateToString(bean.getStartDate()));
				query.setParameter("EN_DT", MedicoResources.convertUtilDateToString(bean.getEndDate()));
				query.setParameter("LOCID", bean.getSendLocId());
			list = query.getResultList();
			System.out.println("List size " + list.size());
		}
		catch (Exception e) {
			
			throw e;
		}
		return list;
	}
	
	@Override
	public List<ViewGSTR_1_HSN_INV> getHSNReportData(ReportBean bean) throws Exception {
		
		EntityManager em = null;
		List<ViewGSTR_1_HSN_INV> list = null;
		try {
			

			em = emf.createEntityManager();
			StoredProcedureQuery query  = em.createNamedStoredProcedureQuery("callGSTR_1_HSN_INV");
				query.setParameter("FIN_YEAR_FLAG", bean.getFinyear());
				query.setParameter("FINID", bean.getFinyearflag());
				query.setParameter("COMP_CD", bean.getCompcd());
				query.setParameter("ST_DT", MedicoResources.convertUtilDateToString(bean.getStartDate()));
				query.setParameter("EN_DT", MedicoResources.convertUtilDateToString(bean.getEndDate()));
				query.setParameter("LOCID", bean.getSendLocId());
			list = query.getResultList();
			System.out.println("List size " + list.size());
		}
		catch (Exception e) {
			
			throw e;
		}
		return list;
	}
	
	public List<ViewGSTR_1_B2CLA_INV> getB2CLAReportData(ReportBean bean) throws Exception {
		
		EntityManager em = null;
		List<ViewGSTR_1_B2CLA_INV> list = null;
		try {
					
			em = emf.createEntityManager();
			StoredProcedureQuery query  = em.createNamedStoredProcedureQuery("callGSTR_1_B2CLA_INV");
				query.setParameter("FIN_YEAR_FLAG", bean.getFinyear());
				query.setParameter("FINID", bean.getFinyearflag());
				query.setParameter("COMP_CD", bean.getCompcd());
				query.setParameter("ST_DT", MedicoResources.convertUtilDateToString(bean.getStartDate()));
				query.setParameter("EN_DT", MedicoResources.convertUtilDateToString(bean.getEndDate()));
				query.setParameter("LOCID", bean.getSendLocId());
			list = query.getResultList();
			System.out.println("List size " + list.size());
		}
		catch (Exception e) {
			
			throw e;
		}
		return list;
	}
	
	@Override
	public List<ViewGSTR_1_TRANSFER> getGstR1TransferData(ReportBean bean) throws Exception {
		
		EntityManager em = null;
		List<ViewGSTR_1_TRANSFER> list = null;
		try {
					
			em = emf.createEntityManager();
			StoredProcedureQuery query  = em.createNamedStoredProcedureQuery("callGSTR_1_TRANSFER");
				query.setParameter("FIN_YEAR_FLAG", bean.getFinyear());
				query.setParameter("FINID", bean.getFinyearflag());
				query.setParameter("COMP_CD", bean.getCompcd());
				query.setParameter("ST_DT", MedicoResources.convertUtilDateToString(bean.getStartDate()));
				query.setParameter("EN_DT", MedicoResources.convertUtilDateToString(bean.getEndDate()));
				query.setParameter("LOCID", bean.getSendLocId());
			list = query.getResultList();
			System.out.println("List size " + list.size());
		}
		catch (Exception e) {
			
			throw e;
		}
		return list;
	}

	@Override
	public List<ViewGSTR_1_B2CSA_INV> getGSTR_1_B2CSA_INVData(ReportBean bean) throws Exception {
		
		EntityManager em = null;
		List<ViewGSTR_1_B2CSA_INV> list = null;
		try {
					
			em = emf.createEntityManager();
			StoredProcedureQuery query  = em.createNamedStoredProcedureQuery("callGSTR_1_B2CSA_INV");
				query.setParameter("FIN_YEAR_FLAG", bean.getFinyear());
				query.setParameter("FINID", bean.getFinyearflag());
				query.setParameter("COMP_CD", bean.getCompcd());
				query.setParameter("ST_DT", MedicoResources.convertUtilDateToString(bean.getStartDate()));
				query.setParameter("EN_DT", MedicoResources.convertUtilDateToString(bean.getEndDate()));
				query.setParameter("LOCID", bean.getSendLocId());
			list = query.getResultList();
			System.out.println("List size " + list.size());
		}
		catch (Exception e) {
			
			throw e;
		}
		return list;
	}

}
