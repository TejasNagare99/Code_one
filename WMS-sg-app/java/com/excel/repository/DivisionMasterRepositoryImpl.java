package com.excel.repository;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.Tuple;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.excel.model.Alloc_gen_ent;
import com.excel.model.DivMaster;
import com.excel.model.DivMaster_;
import com.excel.model.Usermaster;
import com.excel.model.Usermaster_;
import com.excel.utility.MedicoConstants;

@Repository
public class DivisionMasterRepositoryImpl implements DivisionMasterRepository, MedicoConstants{
	
	@Autowired EntityManagerFactory emf;
	@PersistenceContext private EntityManager entityManager;
	
	@Override
	public List<DivMaster> getStandardDivisionList() throws Exception {
		EntityManager em = null;
		List<DivMaster> list = null;
		try {
			em = emf.createEntityManager();
			CriteriaBuilder builder = em.getCriteriaBuilder();
			CriteriaQuery<DivMaster> criteriaQuery = builder.createQuery(DivMaster.class);
			Root<DivMaster> root = criteriaQuery.from(DivMaster.class);
			criteriaQuery.multiselect(
					root.get(DivMaster_.div_id),
					root.get(DivMaster_.div_disp_nm)
					).where(builder.equal(root.get(DivMaster_.div_status), A))
			.orderBy(builder.asc(root.get(DivMaster_.div_disp_nm)));
			list = em.createQuery(criteriaQuery).getResultList();
		} finally {
			if(em != null) { em.close(); }
		}
		if(list != null && !list.isEmpty()) {
			return list;
		}
		return null;
	}

	@Override
	public List<DivMaster> getDivAccessedByUser(String userId) throws Exception {
		EntityManager em = null;
		List<DivMaster> divList = new ArrayList<DivMaster>();
		try {
			em = emf.createEntityManager();
			StringBuffer sb = new StringBuffer();
			sb.append("select div_code,div_disp_nm,div_id,div_code_nm,div_status from  DIV_MASTER  ");
			sb.append("WHERE div_id in (select ediv_div_id from am_m_emp_div_access where ediv_emp_id=:emp_id and ediv_status='A')  ");
		//	sb.append("AND DIV_status = 'A' ");
			sb.append("order by div_disp_nm "); 
			Query query = em.createNativeQuery(sb.toString(), Tuple.class);
			query.setParameter("emp_id", userId);
			List<Tuple> tuples = query.getResultList();
			if (tuples != null && !tuples.isEmpty()) {
				for(Tuple t : tuples) {
					DivMaster dm=new DivMaster();
					dm.setDiv_code(t.get("div_code", String.class));
					dm.setDiv_id(Long.valueOf(t.get("div_id", Integer.class)));
					dm.setDiv_disp_nm(t.get("div_disp_nm", String.class));
					dm.setDiv_status(t.get("div_status", Character.class).toString());
					divList.add(dm);
				}
			}
		} finally {
			if (em != null) { em.close(); }
		}
	
		return divList;
	}
	@Override
	public String getDivNameByDivID(Long div_id) throws Exception {
		EntityManager em = null;
		String div_name = null;
		try {
			System.out.println("DivId : "+div_id);
			em = emf.createEntityManager();
			CriteriaBuilder builder = em.getCriteriaBuilder();
			CriteriaQuery<String> criteriaQuery = builder.createQuery(String.class);
			Root<DivMaster> root = criteriaQuery.from(DivMaster.class);
			criteriaQuery.multiselect(
					root.get(DivMaster_.div_disp_nm)
					).where(builder.equal(root.get(DivMaster_.div_status), A),builder.equal(root.get(DivMaster_.div_id), div_id))
			.orderBy(builder.asc(root.get(DivMaster_.div_disp_nm)));
			div_name = em.createQuery(criteriaQuery).getSingleResult();
			if(div_name!=null)
				return div_name;
		} finally {
			if(em != null) { em.close(); }
		}
		return null;
	}
	

	@Override
	public List<DivMaster> getActiveList() throws Exception {
		// TODO Auto-generated method stub
		EntityManager em = null;
		List<DivMaster> divList = new ArrayList<DivMaster>();
		try {
			em = emf.createEntityManager();
			StringBuffer sb = new StringBuffer();
			sb.append("select div_id,div_disp_nm from DIV_MASTER where DIV_status = 'A' order by div_disp_nm");
			
			Query query = em.createNativeQuery(sb.toString(), Tuple.class);
			List<Tuple> tuples = query.getResultList();
			if (tuples != null && !tuples.isEmpty()) {
				for(Tuple t : tuples) {
					DivMaster dm=new DivMaster();
					dm.setDiv_id(Long.valueOf((t.get("div_id", Integer.class).toString())));
					dm.setDiv_disp_nm(t.get("div_disp_nm", String.class));
					
					divList.add(dm);
				}
				
				
				System.out.println("divlist::"+divList.size());
			}
			
		}
		catch(Exception e) {
			throw e;
		}
		 finally {
				if (em != null) { em.close(); }
			}
		
		return divList;
	}
	
	@Override
	public DivMaster getObjectById(Long divId) throws Exception {
		return this.entityManager.find(DivMaster.class, divId);
	}

	@Override
	public List<DivMaster> getDivreport(String userId) throws Exception {
		// TODO Auto-generated method stub
		try {
			EntityManager em = null;
			List<DivMaster> divList = new ArrayList<DivMaster>();
			try {
				em = emf.createEntityManager();
				StringBuffer sb = new StringBuffer();
				sb.append("select div_code,div_disp_nm,div_id,div_code_nm,div_status from  DIV_MASTER  ");
				sb.append("WHERE div_id in (select edivr_div_id from am_m_emp_div_report_access where edivr_emp_id=:emp_id and edivr_status='A')  ");
				sb.append("AND DIV_status = 'A' ");
				sb.append("order by div_disp_nm "); 
				Query query = em.createNativeQuery(sb.toString(), Tuple.class);
				query.setParameter("emp_id", userId);
				List<Tuple> tuples = query.getResultList();
				if (tuples != null && !tuples.isEmpty()) {
					for(Tuple t : tuples) {
						DivMaster dm=new DivMaster();
						dm.setDiv_code(t.get("div_code", String.class));
						dm.setDiv_id(Long.valueOf(t.get("div_id", Integer.class)));
						dm.setDiv_disp_nm(t.get("div_disp_nm", String.class));
						divList.add(dm);
					}
				}
			} finally {
				if (em != null) { em.close(); }
			}
		
			return divList;
		}catch (Exception e) {
			// TODO: handle exception
			throw e;
		}
	}

	@Override
	public List<DivMaster> getDivIdList() throws Exception {
		EntityManager em = null;
		List<DivMaster> divList = new ArrayList<DivMaster>();
		try {
			em = emf.createEntityManager();
			StringBuffer sb = new StringBuffer();
			sb.append("select div_code,div_disp_nm,div_id,div_code_nm,div_status,div_map_cd,div_short_nm,team_reqd_ind from  DIV_MASTER  ");
			sb.append("WHERE team_reqd_ind= 'Y'");
			Query query = em.createNativeQuery(sb.toString(), Tuple.class);
		//	query.setParameter("emp_id", userId);
			List<Tuple> tuples = query.getResultList();
			if (tuples != null && !tuples.isEmpty()) {
				for(Tuple t : tuples) {
					DivMaster dm=new DivMaster();
					dm.setDiv_code(t.get("div_code", String.class));
					dm.setDiv_id(Long.valueOf(t.get("div_id", Integer.class)));
					dm.setDiv_disp_nm(t.get("div_disp_nm", String.class));
					dm.setDiv_map_cd(t.get("div_map_cd", String.class));
					dm.setDiv_status(t.get("div_status", Character.class).toString());
					dm.setDiv_short_nm(t.get("div_short_nm", String.class));
					dm.setTeam_reqd_ind(t.get("team_reqd_ind",Character.class).toString());
					divList.add(dm);
				}
			}
		} catch (Exception e) {
			// TODO: handle exception
			throw e;
		}finally {
			if (em != null) { em.close(); }
		}
	
		return divList;
	}
	
	@Override
	public List<DivMaster> getAllDivAccessedByUser(String userId) throws Exception {
		EntityManager em = null;
		List<DivMaster> divList = new ArrayList<DivMaster>();
		try {
			em = emf.createEntityManager();
			StringBuffer sb = new StringBuffer();
			sb.append("select div_code,div_disp_nm,div_id,div_code_nm,div_status,team_reqd_ind from  DIV_MASTER  ");
			sb.append("WHERE div_id in (select ediv_div_id from am_m_emp_div_access where ediv_emp_id=:emp_id)  ");
		//	sb.append("WHERE div_id in (select ediv_div_id from am_m_emp_div_access where ediv_emp_id=:emp_id and ediv_status='A')  ");
		//	sb.append("AND DIV_status = 'A' ");  //CHanged on 14-08-2021 
			sb.append("order by div_disp_nm "); 
			Query query = em.createNativeQuery(sb.toString(), Tuple.class);
			query.setParameter("emp_id", userId);
			List<Tuple> tuples = query.getResultList();
			if (tuples != null && !tuples.isEmpty()) {
				for(Tuple t : tuples) {
					DivMaster dm=new DivMaster();
					dm.setDiv_code(t.get("div_code", String.class));
					dm.setDiv_id(Long.valueOf(t.get("div_id", Integer.class)));
					dm.setDiv_disp_nm(t.get("div_disp_nm", String.class));
					dm.setTeam_reqd_ind(t.get("team_reqd_ind", Character.class).toString());
					divList.add(dm);
				}
			}
		} finally {
			if (em != null) { em.close(); }
		}
	
		return divList;
	}
	
	@Override
	public List<DivMaster> getAllStandardDivisionList() throws Exception {
		EntityManager em = null;
		List<DivMaster> list = null;
		try {
			em = emf.createEntityManager();
			CriteriaBuilder builder = em.getCriteriaBuilder();
			CriteriaQuery<DivMaster> criteriaQuery = builder.createQuery(DivMaster.class);
			Root<DivMaster> root = criteriaQuery.from(DivMaster.class);
			criteriaQuery.multiselect(
					root.get(DivMaster_.div_id),
					root.get(DivMaster_.div_disp_nm)
					)//.where(builder.equal(root.get(DivMaster_.div_status), A))  17-06-2021 Commented
			.orderBy(builder.asc(root.get(DivMaster_.div_disp_nm)));
			list = em.createQuery(criteriaQuery).getResultList();
		} finally {
			if(em != null) { em.close(); }
		}
		if(list != null && !list.isEmpty()) {
			return list;
		}
		return null;
	}

	@Override
	public List<Usermaster> getDecryptedPassword(String userId) throws Exception {
		EntityManager em = null;
		List<Usermaster> list = null;
		try {
			System.out.println("userId:::"+userId);
			em = emf.createEntityManager();
			CriteriaBuilder builder = em.getCriteriaBuilder();
			CriteriaQuery<Usermaster> criteriaQuery = builder.createQuery(Usermaster.class);
			Root<Usermaster> root = criteriaQuery.from(Usermaster.class);
			criteriaQuery.multiselect(
					root.get(Usermaster_.ld_pass_ang)
					).where(builder.equal(root.get(Usermaster_.ld_lgnid), userId));
			list = em.createQuery(criteriaQuery).getResultList();
		} finally {
			if(em != null) { em.close(); }
		}
		if(list != null && !list.isEmpty()) {
			return list;
		}
		return null;
	}

	@Override
	public Long getDivIdByName(String divName) throws Exception {
		// TODO Auto-generated method stub
		EntityManager em = null;
		List<DivMaster> list = null;
		try {
			em = emf.createEntityManager();
			CriteriaBuilder builder = em.getCriteriaBuilder();
			CriteriaQuery<DivMaster> criteriaQuery = builder.createQuery(DivMaster.class);
			Root<DivMaster> root = criteriaQuery.from(DivMaster.class);
			criteriaQuery.select(root)
			.where(builder.equal(root.get(DivMaster_.div_disp_nm), divName))
			.orderBy(builder.asc(root.get(DivMaster_.div_disp_nm)));
			list = em.createQuery(criteriaQuery).getResultList();
			if(list!=null && !list.isEmpty()) {
				return list.get(0).getDiv_id();
			}
		}
		catch (Exception e) {
			// TODO: handle exception
			throw e;
		}
		finally {
			if(em != null) { em.close(); }
		}
		return null;
	}
}
