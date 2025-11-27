package com.excel.repository;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Query;
import javax.persistence.Tuple;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.excel.exception.MedicoException;
import com.excel.model.SG_d_parameters_details;

@Repository
public class LoginMasterRepositoryImpl implements LoginMasterRepository{

	@Autowired private EntityManagerFactory emf;

	@Override
	public String getFormatedUserDivision(String UserId) throws Exception {
		EntityManager em = null;
		StringBuffer buffer = new StringBuffer();
		System.out.println("EntityManagerFactory "+emf);
		try {
			em = emf.createEntityManager();
			StringBuffer sb = new StringBuffer();
			sb.append(" SELECT DM.div_id as div_id, DM.div_disp_nm as div_disc FROM DivMaster DM ,Am_m_emp_div_access DA WHERE DM.div_id = DA.ediv_div_id AND DA.ediv_emp_id =:ediv_emp_id");
			Query query = em.createQuery(sb.toString(), Tuple.class);
			query.setParameter("ediv_emp_id", UserId);
			List<Tuple> tuples = query.getResultList();
			if (tuples != null && !tuples.isEmpty()) {
				SG_d_parameters_details object = null;
				for (Tuple t : tuples) {
					System.out.println("div_id "+t.get("div_id", Long.class));
					buffer.append(t.get("div_id", Long.class));
					buffer.append(",");
				}
			}
			buffer.insert(0, "(");
			buffer.insert(buffer.length(), ")");
		} finally {
			if (em != null) { em.close(); }
		}
		return buffer.toString();
	}
	
	
}
