package com.excel.repository;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.StoredProcedureQuery;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.excel.model.GST3B_5;
import com.excel.model.GST3B_Eligible_Itc;
import com.excel.model.GST3B_Supplies_Liable_Bean;
import com.excel.model.GST3B_Supplies_Liable_Bean_2;
import com.excel.utility.MedicoConstants;

@Repository
public class Gst_3B_Repository_Impl implements Gst_3B_Repository,MedicoConstants{
	@Autowired EntityManagerFactory emf;
	
	@Override
	public List<GST3B_Supplies_Liable_Bean> getDataForSuppliesLiable(String fin_year_flag, String fin_id, String comp_cd,
			String st_dt, String end_dt, String loc_id) throws Exception {
		// TODO Auto-generated method stub
		EntityManager em = null;
		List<GST3B_Supplies_Liable_Bean> list = null;
		try {
					
			em = emf.createEntityManager();
			StoredProcedureQuery callGSTR_03B_31  = em.createNamedStoredProcedureQuery("callGSTR_03B_31");
			callGSTR_03B_31.setParameter("FIN_YEAR_FLAG", fin_year_flag);
			callGSTR_03B_31.setParameter("FINID", fin_id);
			callGSTR_03B_31.setParameter("COMP_CD", comp_cd);
			callGSTR_03B_31.setParameter("ST_DT", st_dt);
			callGSTR_03B_31.setParameter("EN_DT", end_dt);
			callGSTR_03B_31.setParameter("LOCID", loc_id);
			list = callGSTR_03B_31.getResultList();
			System.out.println("List size " + list.size());
		}
		catch (Exception e) {
			// TODO: handle exception
			throw e;
		}
		return list;
	}
	
	@Override
	public List<GST3B_Supplies_Liable_Bean_2> getDataForSuppliesLiableTable2(String fin_year_flag, String fin_id, String comp_cd,
			String st_dt, String end_dt, String loc_id) throws Exception {
		// TODO Auto-generated method stub
		EntityManager em = null;
		List<GST3B_Supplies_Liable_Bean_2> list = null;
		try {
					
			em = emf.createEntityManager();
			StoredProcedureQuery callGSTR_03B_32  = em.createNamedStoredProcedureQuery("callGSTR_03B_32");
			callGSTR_03B_32.setParameter("FIN_YEAR_FLAG", fin_year_flag);
			callGSTR_03B_32.setParameter("FINID", fin_id);
			callGSTR_03B_32.setParameter("COMP_CD", comp_cd);
			callGSTR_03B_32.setParameter("ST_DT", st_dt);
			callGSTR_03B_32.setParameter("EN_DT", end_dt);
			callGSTR_03B_32.setParameter("LOCID", loc_id);
			list = callGSTR_03B_32.getResultList();
			System.out.println("List size " + list.size());
		}
		catch (Exception e) {
			// TODO: handle exception
			throw e;
		}
		return list;
	}
	
	@Override
	public List<GST3B_Eligible_Itc> getDataForEligibleItc(String fin_year_flag, String fin_id, String comp_cd,
			String st_dt, String end_dt, String loc_id) throws Exception {
		// TODO Auto-generated method stub
		EntityManager em = null;
		List<GST3B_Eligible_Itc> list = null;
		try {
					
			em = emf.createEntityManager();
			StoredProcedureQuery callGSTR_03B_4  = em.createNamedStoredProcedureQuery("callGSTR_03B_4");
			callGSTR_03B_4.setParameter("FIN_YEAR_FLAG", fin_year_flag);
			callGSTR_03B_4.setParameter("FINID", fin_id);
			callGSTR_03B_4.setParameter("COMP_CD", comp_cd);
			callGSTR_03B_4.setParameter("ST_DT", st_dt);
			callGSTR_03B_4.setParameter("EN_DT", end_dt);
			callGSTR_03B_4.setParameter("LOCID", loc_id);
			list = callGSTR_03B_4.getResultList();
			System.out.println("List size " + list.size());
		}
		catch (Exception e) {
			// TODO: handle exception
			throw e;
		}
		return list;
	}
	
	@Override
	public List<GST3B_5> getDataForExemptInwardSupplies(String fin_year_flag, String fin_id, String comp_cd,
			String st_dt, String end_dt, String loc_id) throws Exception {
		// TODO Auto-generated method stub
		EntityManager em = null;
		List<GST3B_5> list = null;
		try {
					
			em = emf.createEntityManager();
			StoredProcedureQuery callGSTR_03B_5  = em.createNamedStoredProcedureQuery("callGSTR_03B_5");
			callGSTR_03B_5.setParameter("FIN_YEAR_FLAG", fin_year_flag);
			callGSTR_03B_5.setParameter("FINID", fin_id);
			callGSTR_03B_5.setParameter("COMP_CD", comp_cd);
			callGSTR_03B_5.setParameter("ST_DT", st_dt);
			callGSTR_03B_5.setParameter("EN_DT", end_dt);
			callGSTR_03B_5.setParameter("LOCID", loc_id);
			list = callGSTR_03B_5.getResultList();
			System.out.println("List size " + list.size());
		}
		catch (Exception e) {
			// TODO: handle exception
			throw e;
		}
		return list;
	}

}
