package com.excel.repository;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.excel.model.CustomerMaster;
import com.excel.model.CustomerMaster_;
import com.excel.model.Sfa_Customer_Allocation;
import com.excel.model.Sfa_Customer_Allocation_;
import com.excel.model.Sfa_Customer_Master;
import com.excel.model.Sfa_Customer_Master_;
import com.excel.utility.MedicoConstants;

@Repository
public class StockistMasterRepositoryImpl implements StockistMasterRepository, MedicoConstants{
	
	@PersistenceContext
	private EntityManager entityManager;
	@Autowired private EntityManagerFactory emf;
	
	@Override
	public CustomerMaster getById(Long custId) throws Exception {
		return this.entityManager.find(CustomerMaster.class, custId);
	}
	
	@Override
	public List<CustomerMaster> getcustNameListByCmpCd(String companyCode) throws Exception {
		List<CustomerMaster> list = null;
		EntityManager em = null;
		try {
			em = emf.createEntityManager();
			CriteriaBuilder builder = em.getCriteriaBuilder();
			CriteriaQuery<CustomerMaster> criteriaQuery = builder.createQuery(CustomerMaster.class);
			Root<CustomerMaster> root = criteriaQuery.from(CustomerMaster.class);
			criteriaQuery.multiselect(root.get(CustomerMaster_.cust_id), root.get(CustomerMaster_.cust_name_billto) )
			.where(builder.equal(root.get(CustomerMaster_.company_cd), companyCode));

			list = em.createQuery(criteriaQuery).getResultList();
			System.out.println("Cust Name list " + list.size());
		} finally {
			if(em != null) {
				em.close();
			}
		}
		if (list != null && !list.isEmpty()) {
			return list;
		}
		return null;
	}
	
	@Override
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public Sfa_Customer_Master getCustomerMasterById(Long custId) throws Exception {
		//EntityManager em = null;
		Sfa_Customer_Master detail = null;
				try {
				//	em = this.emf.createEntityManager();
					CriteriaBuilder builder = entityManager.getCriteriaBuilder();
					CriteriaQuery<Sfa_Customer_Master> criteriaQuery = builder.createQuery(Sfa_Customer_Master.class);
					Root<Sfa_Customer_Master> root = criteriaQuery.from(Sfa_Customer_Master.class);
					criteriaQuery.select(root)
					.where(
							builder.and(
									builder.equal(root.get(Sfa_Customer_Master_.cust_id), custId)

									)
							);
					List<Sfa_Customer_Master> list = entityManager.createQuery(criteriaQuery).getResultList();
					if (list != null && !list.isEmpty()) {
						detail = list.get(0);
					}
				} finally {
				//	if (em != null) { em.close(); }
				}
				return detail;
		
		
	}
	
	
	@Override
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public Sfa_Customer_Allocation getCustomerAllocationById(Long custId) throws Exception {
		//EntityManager em = null;
		Sfa_Customer_Allocation detail = null;
				try {
				//	em = this.emf.createEntityManager();
					CriteriaBuilder builder = entityManager.getCriteriaBuilder();
					CriteriaQuery<Sfa_Customer_Allocation> criteriaQuery = builder.createQuery(Sfa_Customer_Allocation.class);
					Root<Sfa_Customer_Allocation> root = criteriaQuery.from(Sfa_Customer_Allocation.class);
					criteriaQuery.select(root)
					.where(
							builder.and(
									builder.equal(root.get(Sfa_Customer_Allocation_.cust_id), custId)

									)
							);
					List<Sfa_Customer_Allocation> list = entityManager.createQuery(criteriaQuery).getResultList();
					if (list != null && !list.isEmpty()) {
						detail = list.get(0);
					}
				} finally {
				//	if (em != null) { em.close(); }
				}
				return detail;
		
		
	}
	
	@Override
	public List<CustomerMaster> getCustnamesToCheck() throws Exception {
		List<CustomerMaster> list = null;
		EntityManager em = null;
		try {
			em = emf.createEntityManager();
			CriteriaBuilder builder = em.getCriteriaBuilder();
			CriteriaQuery<CustomerMaster> criteriaQuery = builder.createQuery(CustomerMaster.class);
			Root<CustomerMaster> root = criteriaQuery.from(CustomerMaster.class);
			criteriaQuery.multiselect(root.get(CustomerMaster_.cust_id), root.get(CustomerMaster_.cust_name_billto) );

			list = em.createQuery(criteriaQuery).getResultList();
			System.out.println("Cust Name list " + list.size());
		} finally {
			if(em != null) {
				em.close();
			}
		}
		if (list != null && !list.isEmpty()) {
			return list;
		}
		return null;
		
	}

}
