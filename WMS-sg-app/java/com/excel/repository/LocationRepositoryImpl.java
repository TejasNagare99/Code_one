package com.excel.repository;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.Tuple;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Path;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import javax.persistence.criteria.Subquery;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.excel.model.Company;
import com.excel.model.EmployeeDetails;
import com.excel.model.EmployeeDetails_;
import com.excel.model.Location;
import com.excel.model.Location_;
import com.excel.model.SmpBatch;
import com.excel.model.SmpBatch_;
import com.excel.model.SmpItem;
import com.excel.model.SmpItem_;
import com.excel.service.CompanyMasterService;

@Repository
public class LocationRepositoryImpl implements LocationRepository {

	@Autowired
	EntityManagerFactory emf;
	@PersistenceContext
	private EntityManager entityManager;
	@Autowired
	private CompanyMasterService companyMasterService;

	@Override
	public Location getLocationNameByEmployeeId(String empId) throws Exception {
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
			query.setParameter("emp_id", empId);
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
	public List<Location> getAllSubCompanies() throws Exception {
		EntityManager em = null;
		List<Location> list = null;

		try {
			em = emf.createEntityManager();
			CriteriaBuilder builder = em.getCriteriaBuilder();
			CriteriaQuery<Location> query = builder.createQuery(Location.class);
			Root<Location> root = query.from(Location.class);
			query.multiselect(root.get(Location_.loc_subcomp_id), root.get(Location_.loc_nm),
					root.get(Location_.loc_id)).where(builder.and(builder.equal(root.get(Location_.loc_status), 'A')));
			list = em.createQuery(query).getResultList();

			System.out.println("List " + list.size());
		} finally {
			if (em != null) {
				em.close();
			}
		}
		if (list != null && list.size() > 0) {
			return list;
		}
		return null;
	}

	@Override
	public Location getObjectById(Long locId) throws Exception {
		return this.entityManager.find(Location.class, locId);
	}

	@Override
	public List<Location> getAllLocations() throws Exception {
		EntityManager em = null;
		List<Location> list = new ArrayList<Location>();
		Company company = companyMasterService.getCompanyDetails();
		String q="";
		
		try {
			em = emf.createEntityManager();
			
			if( company.stock_at_cfa .equals("Y")) {
				 q = "select loc_id,loc_godwadd1 as loc_nm,loc_SubComp_id,loc_map_cd from location l where loc_status='A'";
			}
			else {
				 q = "select loc_id,loc_NM as loc_nm,loc_SubComp_id,loc_map_cd from location  where loc_status='A'";
			}
			
			 
			
			

			Query query = em.createNativeQuery(q, Tuple.class);
			List<Tuple> tuples = query.getResultList();

			if (tuples != null && !tuples.isEmpty()) {
				for (Tuple t : tuples) {
					Location loc = new Location();
					loc.setLoc_id(Long.valueOf(t.get("loc_id", Integer.class)));
					loc.setLoc_nm(t.get("loc_nm", String.class));
					loc.setLoc_subcomp_id(Long.valueOf(t.get("loc_SubComp_id", Integer.class)));
					loc.setLoc_map_cd(t.get("loc_map_cd", String.class));
					list.add(loc);
				}
			}
		} catch (Exception e) {
			System.out.println("Error Occurred :" + e.getMessage());// uncomment asneeded --;
		} finally {
			if (em != null) {
				em.close();
			}
		}
		return list;
	}
	
	@Override
	public List<Location> getAllLocationsBySubCompany(Long subCompanyId) throws Exception {
		EntityManager em = null;
		List<Location> list = new ArrayList<Location>();
		try {
			em = emf.createEntityManager();
			String q = "select loc_id,loc_godwadd1 as loc_nm,loc_SubComp_id,loc_map_cd from location l where loc_subcomp_id=:subCompanyId and loc_status='A'";

			Query query = em.createNativeQuery(q, Tuple.class);
			query.setParameter("subCompanyId", subCompanyId);
			List<Tuple> tuples = query.getResultList();

			if (tuples != null && !tuples.isEmpty()) {
				for (Tuple t : tuples) {
					Location loc = new Location();
					loc.setLoc_id(Long.valueOf(t.get("loc_id", Integer.class)));
					loc.setLoc_nm(t.get("loc_nm", String.class));
					loc.setLoc_subcomp_id(Long.valueOf(t.get("loc_SubComp_id", Integer.class)));
					loc.setLoc_map_cd(t.get("loc_map_cd", String.class));
					list.add(loc);
				}
			}
		} catch (Exception e) {
			System.out.println("Error Occurred :" + e.getMessage());// uncomment asneeded --;
		} finally {
			if (em != null) {
				em.close();
			}
		}
		return list;
	}

	@Override
	public List<Location> getlocationforpfzandpip() throws Exception {
		// TODO Auto-generated method stub
		EntityManager em = null;
		List<Location> list = new ArrayList<Location>();
		try {
			em = emf.createEntityManager();
			String q = "select loc_id,loc_nm,loc_SubComp_id from location where loc_status='A' and loc_id in(1,9)";
			Query query = em.createNativeQuery(q, Tuple.class);
			List<Tuple> tuples = query.getResultList();
			if (tuples != null && !tuples.isEmpty()) {
				for (Tuple t : tuples) {
					Location loc = new Location();
					loc.setLoc_id(Long.valueOf(t.get("loc_id", Integer.class)));
					loc.setLoc_nm(t.get("loc_nm", String.class));
					loc.setLoc_subcomp_id(Long.valueOf(t.get("loc_SubComp_id", Integer.class)));
					list.add(loc);
				}
			}

		} catch (Exception e) {
			// TODO: handle exception
			throw e;
		}
		return list;
	}

	@Override
	public boolean checkAutoDispatchIsLocAvail() throws Exception {
		// TODO Auto-generated method stub
		EntityManager em = null;
		List<Location> list = new ArrayList<Location>();
		boolean flag = true;
		try {
			em = emf.createEntityManager();
			CriteriaBuilder builder = em.getCriteriaBuilder();
			CriteriaQuery<Location> query = builder.createQuery(Location.class);
			Root<Location> root = query.from(Location.class);
			query.select(root).where(builder.equal(root.get(Location_.autodesp_inprocess), "Y"));
			list = em.createQuery(query).getResultList();

			if (list != null && list.size() > 0) {
				flag = true;
			} else {
				flag = false;
			}
		} catch (Exception e) {
			// TODO: handle exception
			throw e;
		}
		return flag;
	}

	@Override
	public List<Location> getLocationNarrationBylocId(String locId) throws Exception {
		EntityManager em = null;
		List<Location> list = new ArrayList<Location>();
		try {
			em = emf.createEntityManager();
			String q = "select loc_id,loc_nm,loc_SubComp_id,loc_narrationfooter from location where loc_status='A' and loc_id=:locId";

			Query query = em.createNativeQuery(q, Tuple.class);
			query.setParameter("locId", locId);
			List<Tuple> tuples = query.getResultList();

			if (tuples != null && !tuples.isEmpty()) {
				for (Tuple t : tuples) {
					Location loc = new Location();
					loc.setLoc_id(Long.valueOf(t.get("loc_id", Integer.class)));
					loc.setLoc_nm(t.get("loc_nm", String.class));
					loc.setLoc_subcomp_id(Long.valueOf(t.get("loc_SubComp_id", Integer.class)));
					loc.setLoc_narrationfooter(t.get("loc_narrationfooter", String.class));
					list.add(loc);
				}
			}
		} catch (Exception e) {
			System.out.println("Error Occurred :" + e.getMessage());// uncomment asneeded --;
		} finally {
			if (em != null) {
				em.close();
			}
		}
		System.out.println("list::" + list.size());
		return list;
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public void lockUnlockLocations(String status) throws Exception {
		// TODO Auto-generated method stub

		try {
			StringBuffer sb = new StringBuffer();
			sb.append("update Location l set l.autodesp_inprocess=:status");
			Query query = entityManager.createQuery(sb.toString());
			query.setParameter("status", status);

			query.executeUpdate();
		} catch (Exception e) {
			// TODO: handle exception
			throw e;
		}
	}

	@Override
	public List<Location> getLocationsWithoutBatch(String batch_no, Long smp_prod_id, Long maker_loc_id)
			throws Exception {
		List<Location> loc_list = null;
		try {
			CriteriaBuilder builder = entityManager.getCriteriaBuilder();
			// from location
			CriteriaQuery<Location> criteriaQuery_location = builder.createQuery(Location.class);
			Root<Location> root = criteriaQuery_location.from(Location.class);

			// from smpbatch
			Subquery<Long> subquery_batch = criteriaQuery_location.subquery(Long.class);
			Root<SmpBatch> subqueryRoot_batch = subquery_batch.from(SmpBatch.class);
			// select batch_loc_id
			subquery_batch.select(subqueryRoot_batch.get(SmpBatch_.batch_loc_id));
			// where batch no =
			List<Predicate> predicates_batch = new ArrayList<>();
			predicates_batch.add(builder.equal(subqueryRoot_batch.get(SmpBatch_.batch_no), batch_no));
			subquery_batch.where(predicates_batch.toArray(new Predicate[] {}));

			// from smpitem
			Subquery<Long> subquery_item = criteriaQuery_location.subquery(Long.class);
			Root<SmpItem> subqueryRoot_item = subquery_item.from(SmpItem.class);
			// select subcompid
			subquery_item.select(subqueryRoot_item.get(SmpItem_.smp_subcomp_id));
			// where smpprodid =
			List<Predicate> predicates_item = new ArrayList<>();
			predicates_item.add(builder.equal(subqueryRoot_item.get(SmpItem_.smp_prod_id), smp_prod_id));
			subquery_item.where(predicates_item.toArray(new Predicate[] {}));

			// conditions for locations
			List<Predicate> predicates = new ArrayList<>();
			// where locstatus = 'A'
			predicates.add(builder.equal(root.get(Location_.loc_status), 'A'));
			// loc_id not in batch_loc_id
			predicates.add(builder.not(root.get(Location_.loc_id).in(subquery_batch)));
			// locsubcomp = smpitem.subcomp
			predicates.add(builder.equal(root.get(Location_.loc_subcomp_id), subquery_item));
			// and loc not equal to
			predicates.add(builder.equal(root.get(Location_.loc_id), maker_loc_id).not());

			// select loc_id , loc_name
			criteriaQuery_location.multiselect(root.get(Location_.loc_nm), root.get(Location_.loc_id))
					.where(predicates.toArray(new Predicate[] {}));
			TypedQuery<Location> typedQuery = entityManager.createQuery(criteriaQuery_location);
			loc_list = typedQuery.getResultList();

			return loc_list;
		} finally {
		}
	}

	@Override
	public String getLocPrefixById(Long loc_id) throws Exception {
		EntityManager em = null;
		String loc_prefix = null;
		try {
			em = this.emf.createEntityManager();
			CriteriaBuilder builder = em.getCriteriaBuilder();
			CriteriaQuery<String> criteria = builder.createQuery(String.class);
			Root<Location> root = criteria.from(Location.class);
			Path<String> path = root.get("loc_prefix");
			criteria.select(path).where(builder.equal(root.get("loc_id"), loc_id));

			loc_prefix = em.createQuery(criteria).getSingleResult();
		} finally {
			if (em != null)
				em.close();
		}

		return loc_prefix;
	}

	@Override
	public boolean checkAutoDispatchIsSpecificLocAvail(Long loc_id) throws Exception {
		EntityManager em = null;
		Location loc = null;
		boolean flag = true;
		try {
			em = emf.createEntityManager();
			CriteriaBuilder builder = em.getCriteriaBuilder();
			CriteriaQuery<Location> query = builder.createQuery(Location.class);
			Root<Location> root = query.from(Location.class);
			query.select(root).where(builder.equal(root.get(Location_.loc_id), loc_id));
			loc = em.createQuery(query).getSingleResult();

			if (loc != null) {
				if (loc.getAutodesp_inprocess().equalsIgnoreCase("Y")) {
					flag = true;
				} else {
					flag = false;
				}
			} else {
				flag = true;
			}

		} finally {
			if (em != null)
				em.close();
		}
		return flag;
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public void lockUnlockSpecificLocations(String status, Long loc_id) throws Exception {
		try {
			StringBuffer sb = new StringBuffer();
			sb.append("update Location l set l.autodesp_inprocess=:status where l.loc_id = :loc_id");
			Query query = entityManager.createQuery(sb.toString());
			query.setParameter("status", status);
			query.setParameter("loc_id", loc_id);
			query.executeUpdate();
		} catch (Exception e) {
			throw e;
		}
	}

	@Override
	public Long getLocIdByEmpId(String empId) throws Exception {
		EntityManager em = null;
		Long loc_id = null;
		String loc_id_string = null;
		try {
			em = emf.createEntityManager();
			CriteriaBuilder builder = em.getCriteriaBuilder();
			CriteriaQuery<String> cri_query = builder.createQuery(String.class);
			Root<EmployeeDetails> cri_root = cri_query.from(EmployeeDetails.class);
			Path<String> cri_path = cri_root.get(EmployeeDetails_.emp_loc_id);

			cri_query.select(cri_path).where(builder.equal(cri_root.get(EmployeeDetails_.emp_id), empId));
			loc_id_string = em.createQuery(cri_query).getSingleResult();

			if (loc_id_string != null) {
				loc_id = Long.valueOf(loc_id_string);
			}
		} finally {
			if (em != null)
				em.close();
		}
		return loc_id;
	}

	@Override
	public Long getDepo_loc_id_by_loc_id(String locId) {
		EntityManager em = null;
		Long depo_loc_id = null;
		List<Location> list = new ArrayList<Location>();
		try {
			em = emf.createEntityManager();
			String q ="select LOC_LINK_DPTLOC_ID from location where loc_id=:locId";

			Query query = em.createNativeQuery(q);
			query.setParameter("locId", locId);
			 depo_loc_id = ((Number) query.getSingleResult()).longValue();
			 
			 
			
		} catch (Exception e) {
			System.out.println("Error Occurred :" + e.getMessage());// uncomment asneeded --;
		} finally {
			if (em != null) {
				em.close();
			}
		}


		return depo_loc_id;
		
	}
}
