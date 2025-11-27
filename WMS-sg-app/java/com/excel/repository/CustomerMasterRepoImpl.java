package com.excel.repository;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
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

import com.excel.model.CustLock;
import com.excel.model.CustomerMaster;
import com.excel.model.CustomerMaster_;
import com.excel.model.FieldStaff;

@Repository
public class CustomerMasterRepoImpl implements CustomerMasterRepo {

	@Autowired
	EntityManagerFactory emf;
	@PersistenceContext
	private EntityManager entityManager;

	@Override
	public List<CustomerMaster> getCustomersForLocation(Long loc_id) throws Exception {
		List<CustomerMaster> cust_master = null;
		CriteriaBuilder builder = entityManager.getCriteriaBuilder();
		CriteriaQuery<CustomerMaster> query = builder.createQuery(CustomerMaster.class);
		Root<CustomerMaster> root = query.from(CustomerMaster.class);
		if (loc_id.compareTo(0L) > 0) {
			query.where(builder.and(builder.equal(root.get(CustomerMaster_.loc_id), loc_id)));
		}
		cust_master = entityManager.createQuery(query).getResultList();
		return cust_master;
	}

	@Override
	public CustomerMaster getCustIdByCustCd(String custCd) throws Exception {
		CustomerMaster cust_master = null;
		CriteriaBuilder builder = entityManager.getCriteriaBuilder();
		CriteriaQuery<CustomerMaster> query = builder.createQuery(CustomerMaster.class);
		Root<CustomerMaster> root = query.from(CustomerMaster.class);
		query.multiselect(root.get(CustomerMaster_.cust_id));
		query.where(builder.and(builder.equal(root.get(CustomerMaster_.cust_cd), custCd)));
		cust_master = entityManager.createQuery(query).getSingleResult();
		return cust_master;
	}

	@Override
	public CustomerMaster getErpCustCdByCustCdAndLocation(String custCd, Long locId) throws Exception {
		CustomerMaster cust_master = null;
		CriteriaBuilder builder = entityManager.getCriteriaBuilder();
		CriteriaQuery<CustomerMaster> query = builder.createQuery(CustomerMaster.class);
		Root<CustomerMaster> root = query.from(CustomerMaster.class);
		query.multiselect(root.get(CustomerMaster_.erp_cust_cd));
		query.where(builder.and(builder.equal(root.get(CustomerMaster_.cust_cd), custCd)),
				builder.equal(root.get(CustomerMaster_.loc_id), locId));
		cust_master = entityManager.createQuery(query).getSingleResult();
		return cust_master;
	}

	@Override
	public List<CustomerMaster> getCustomersForLocationNotInCustLock(Long loc_id, String filter) throws Exception {
		EntityManager em = null;
		List<CustomerMaster> cust_master_list = null;
		try {
			em = this.emf.createEntityManager();
			CriteriaBuilder builder = em.getCriteriaBuilder();
			CriteriaQuery<CustomerMaster> criteriaQuery = builder.createQuery(CustomerMaster.class);
			Root<CustomerMaster> root = criteriaQuery.from(CustomerMaster.class);
			Path<Long> path = root.get(CustomerMaster_.cust_id);

//			Subquery<Long> subquery = criteriaQuery.subquery(Long.class);
//			Root<CustLock> subqueryRoot = subquery.from(CustLock.class);
//			subquery.select(subqueryRoot.get("cust_id")).distinct(true);

			List<Predicate> predicates = new ArrayList<>();
			// not needed since we are now checking once he selects the customer
			// predicates.add(builder.in(path).value(subquery).not());
			predicates.add(builder.equal(root.get(CustomerMaster_.discontinued), "N"));
			predicates.add(builder.equal(root.get(CustomerMaster_.loc_id), loc_id));
			predicates.add(builder.isNotNull(root.get(CustomerMaster_.gstin_no)));

			if (filter != null && !filter.isEmpty()) {
				predicates.add(builder.like(builder.upper(root.get(CustomerMaster_.cust_name_shipto)),
						filter.toUpperCase() + "%"));
			}

			criteriaQuery.select(root).where(predicates.toArray(new Predicate[] {}))
					.orderBy(builder.asc(root.get(CustomerMaster_.cust_name_billto)));

			TypedQuery<CustomerMaster> typedQuery = em.createQuery(criteriaQuery);

			cust_master_list = typedQuery.getResultList();

		} finally {
			if (em != null)
				em.close();
		}
		return cust_master_list;
	}

	@Override
	public CustomerMaster getCustomerById(Long cust_id) throws Exception {
		return this.entityManager.find(CustomerMaster.class, cust_id);
	}

	@Override
	public List<FieldStaff> getCustomersForLocationAsFieldStaff(Long loc_id) throws Exception {
		List<FieldStaff> fstaff_list = null;
		StringBuffer sb = new StringBuffer();
		sb.append("SELECT CUST_ID,CUST_NAME_SHIPTO FROM CUSTOMER_MASTER ");
		sb.append("WHERE LOC_ID = :locid AND DISCONTINUED = 'N' ");
		sb.append("ORDER BY CUST_NAME_SHIPTO");

		Query query = entityManager.createNativeQuery(sb.toString(), Tuple.class);
		query.setParameter("locid", loc_id);
		List<Tuple> tuples = query.getResultList();
		if (tuples != null && !tuples.isEmpty()) {
			fstaff_list = new ArrayList<FieldStaff>();
			for (Tuple t : tuples) {
				FieldStaff fs = new FieldStaff();
				fs.setFstaff_id(t.get("CUST_ID", BigDecimal.class).longValue());
				fs.setFstaff_display_name(t.get("CUST_NAME_SHIPTO", String.class));
				fstaff_list.add(fs);
			}
		}
		return fstaff_list;
	}

	@Override
	public List<CustomerMaster> getCustomersByShipToCodeAndNameNotInCustLock(boolean isAllLoc, Long loc_id,
			String filter, String company_cd) throws Exception {
		EntityManager em = null;
		List<CustomerMaster> cust_master_list = null;
		try {
			em = this.emf.createEntityManager();
			CriteriaBuilder builder = em.getCriteriaBuilder();
			CriteriaQuery<CustomerMaster> criteriaQuery = builder.createQuery(CustomerMaster.class);
			Root<CustomerMaster> root = criteriaQuery.from(CustomerMaster.class);
			List<Predicate> predicates = new ArrayList<>();

			if (!isAllLoc)
				predicates.add(builder.equal(root.get(CustomerMaster_.loc_id), loc_id));
			if (filter != null && !filter.isEmpty()) {
				predicates.add(builder.or(
						builder.like(builder.upper(root.get(CustomerMaster_.cust_name_shipto)),
								filter.toUpperCase() + "%"),
						builder.like(builder.upper(root.get(CustomerMaster_.cust_code_shipto)),
								filter.toUpperCase() + "%")));
			}
			predicates.add(builder.equal(root.get(CustomerMaster_.discontinued), "N"));
			predicates.add(builder.isNotNull(root.get(CustomerMaster_.gstin_no)));
			predicates.add(builder.equal(root.get(CustomerMaster_.company_cd),company_cd.trim()));
			criteriaQuery
					// .select(root)
					.multiselect(root.get(CustomerMaster_.loc_id), root.get(CustomerMaster_.cust_id),
							root.get(CustomerMaster_.erp_cust_cd), root.get(CustomerMaster_.cust_name_billto),
							root.get(CustomerMaster_.cust_name_shipto), root.get(CustomerMaster_.destination_shipto),
							root.get(CustomerMaster_.gstin_no), root.get(CustomerMaster_.cust_code_shipto),
							root.get(CustomerMaster_.soldtogstin_no))
					.where(predicates.toArray(new Predicate[] {}))
					.orderBy(builder.asc(root.get(CustomerMaster_.cust_name_shipto)));

			TypedQuery<CustomerMaster> typedQuery = em.createQuery(criteriaQuery);
			if (isAllLoc)
				typedQuery.setMaxResults(10);
			cust_master_list = typedQuery.getResultList();
		} finally {
			if (em != null)
				em.close();
		}
		return cust_master_list;
	}

}
