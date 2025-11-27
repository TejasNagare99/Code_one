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
import javax.persistence.criteria.Subquery;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.excel.model.Company;
import com.excel.model.Location;
import com.excel.model.Sub_Company;
import com.excel.model.Sub_Company_;
import com.excel.utility.CodifyErrors;

@Repository
public class CompanyMasterRepositoryImpl implements CompanyMasterRepository {
	
	@Autowired private EntityManagerFactory emf;
	@PersistenceContext private EntityManager entityManager;

	@Override
	public Sub_Company getSubCompanyObjectById(Long headerId) throws Exception {
		return this.entityManager.find(Sub_Company.class, headerId);
	}

	@Override
	public Company getCompanyDetails() throws Exception {
		EntityManager em = null;
		Company companyMaster = null;
		try {
			em = emf.createEntityManager();
			CriteriaBuilder builder = em.getCriteriaBuilder();
			CriteriaQuery<Company> query = builder.createQuery(Company.class);
			Root<Company> root = query.from(Company.class);
			
			query.select(root);
			List<Company> companyList = em.createQuery(query).getResultList();
			if(companyList != null && !companyList.isEmpty()) {
				companyMaster = companyList.get(0);
			}
		} finally {
			if(em != null) { em.close(); }
		}
		return companyMaster;
	}
	
	@Override
	public List<Sub_Company> getSubCompByCompName(String subCompName) throws Exception {
		EntityManager em = null;
		List<Sub_Company> list = null;
		try {
			em = emf.createEntityManager();
			CriteriaBuilder builder = em.getCriteriaBuilder();
			CriteriaQuery<Sub_Company> query = builder.createQuery(Sub_Company.class);
			Root<Sub_Company> root = query.from(Sub_Company.class);
			query.multiselect(root.get(Sub_Company_.subcomp_id), root.get(Sub_Company_.subcomp_nm)).where(
					builder.and(
							builder.equal(root.get(Sub_Company_.subcomp_status), "A")
							));
			list = em.createQuery(query).getResultList();
		} finally {
			if(em != null) { em.close(); }
		}
		if(list != null && list.size() > 0) {
			return list;
		}
		return null;
	}

	@Override
	public List<Sub_Company> getSubCompanyList(String empId) {
		
		EntityManager em = null;
		List<Sub_Company> list = new ArrayList<Sub_Company>();
		try {
			em = emf.createEntityManager();
			String q="select SubComp_id, SubComp_Nm,subcomp_code from VW_EmployeeWiseSubCompany where emp_id=:empId order by SubComp_Nm asc";
			
			Query query = em.createNativeQuery(q,Tuple.class);
			query.setParameter("empId", empId);
			List<Tuple> tuples = query.getResultList();
			//System.out.println("tuples :: "+tuples.size());
			if (tuples != null && tuples.size()>0) {
				for (Tuple t : tuples) {
					Sub_Company sc=new Sub_Company();					
					sc.setSubcomp_id(Long.valueOf(t.get("SubComp_id",Integer.class)));
					sc.setSubcomp_nm(t.get("SubComp_Nm",String.class));
					sc.setSubcomp_code(t.get("subcomp_code",String.class));
					list.add(sc);
				}
			}
		}
		catch (Exception e) {
			System.out.println("Error Occurred :" +e.getMessage());// uncomment asneeded --;
		}
		finally {
			if(em != null) { em.close(); }
		}
		System.out.println("Listsub :: "+list.get(0).getSubcomp_code());
		return list;
	}
	@Override
	public Character checkMargin(String compCode) {
		
		Character marginIndicator = null;
		EntityManager em = null;
		List<Sub_Company> list = new ArrayList<Sub_Company>();
		try {
			em = emf.createEntityManager();
			String q="select MARGIN_REQD from COMPANY where COMPANY=:compCode";
			
			Query query = em.createNativeQuery(q,Tuple.class);
			query.setParameter("compCode", compCode);
			List<Tuple> tuples = query.getResultList();
			
			if (tuples != null && !tuples.isEmpty()) {
				for (Tuple t : tuples) {
					marginIndicator=t.get("MARGIN_REQD",Character.class);
				}
			}
		}
		catch (Exception e) {
			System.out.println("Error Occurred :" +e.getMessage());// uncomment asneeded --;
		}
		finally {
			if(em != null) { em.close(); }
		}
		return marginIndicator;

	}
	@Override
	public List<Sub_Company> getAllActiveSubCompanyList(String companyCode) {
		System.out.println("company code :"+companyCode);
		EntityManager em = null;
		List<Sub_Company> list = new ArrayList<Sub_Company>();
		try {
			em = emf.createEntityManager();
			String q="select subcomp_id,SubComp_nm from Sub_Company where subComp_company=:companyCode AND subcomp_status='A' " +
					"order by subcomp_nm";
			
			Query query = em.createNativeQuery(q,Tuple.class);
			query.setParameter("companyCode", companyCode);
			List<Tuple> tuples = query.getResultList();
			
			if (tuples != null && !tuples.isEmpty()) {
				for (Tuple t : tuples) {
					Sub_Company sc=new Sub_Company();					
					sc.setSubcomp_id(Long.valueOf(t.get("SubComp_id",Integer.class)));
					sc.setSubcomp_nm(t.get("SubComp_Nm",String.class));
					
					list.add(sc);
				}
			}
		}
		catch (Exception e) {
			System.out.println("Error Occurred :" +e.getMessage());// uncomment asneeded --;
		}
		finally {
			if(em != null) { em.close(); }
		}
		return list;
	}

		@Override
		public List<Company> getCompanyList() throws Exception {
			EntityManager em = null;
			try {
				em = emf.createEntityManager();
				CriteriaBuilder builder = em.getCriteriaBuilder();
				CriteriaQuery<Company> query = builder.createQuery(Company.class);
				Root<Company> root = query.from(Company.class);
				query.select(root);
				List<Company> companyList = em.createQuery(query).getResultList();
				if(companyList != null && !companyList.isEmpty()) {
					return companyList;
				}
			} finally {
				if(em != null) { em.close(); }
			}
			return null;
		}
		
		
		@Override
		@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
		public void UpdateCompanydetails() throws Exception {
			// TODO Auto-generated method stub
			try {
				String query_string = "update Company set wms_is_live='N' ";
				Query query = entityManager.createQuery(query_string);
				query.executeUpdate();
			}
			catch (Exception e) {
				// TODO: handle exception
				throw e;
			}
			
		}
		
		@Override
		public Sub_Company getsubcomCompanyByCode(String subcompcode) throws Exception {
			// TODO Auto-generated method stub
			Sub_Company company=null;
			try {
			//	em = emf.createEntityManager();
				CriteriaBuilder builder = entityManager.getCriteriaBuilder();
				CriteriaQuery<Sub_Company> query = builder.createQuery(Sub_Company.class);
				Root<Sub_Company> root = query.from(Sub_Company.class);
				query.select(root).where(
						builder.and(
								builder.equal(root.get(Sub_Company_.subcomp_code), subcompcode),
								builder.equal(root.get(Sub_Company_.subcomp_status), "A")));
				company = entityManager.createQuery(query).getSingleResult();
				
			}catch (Exception e) {
				// TODO: handle exception
				throw e;
			}
			return company;
		}

		@Override
		public String getSubComnpanyCodeByLocId(Long loc_id) throws Exception {
			EntityManager em = null;
			try {
				em = this.emf.createEntityManager();
				CriteriaBuilder builder = em.getCriteriaBuilder();
				CriteriaQuery<String> criteria = builder.createQuery(String.class);
				Root<Sub_Company> root = criteria.from(Sub_Company.class);
				
				Subquery<String> loc_query = criteria.subquery(String.class);
				Root<Location> subq_loc_root = loc_query.from(Location.class);
				loc_query.select(subq_loc_root.get("loc_subcomp_id"))
				.where(builder.equal(subq_loc_root.get("loc_id"), loc_id));
				
				criteria.select(root.get("subcomp_code"))
				.where(builder.and(builder.equal(loc_query, root.get("subcomp_id")),
						builder.equal(root.get("subcomp_status"),"A")));
				
				return em.createQuery(criteria).getSingleResult();
			}
			finally {
				if(em!=null) em.close();
			}
		}

		@Override
		public Sub_Company getSubCompanyByEmpId(String empId) throws Exception {
			
			EntityManager em = null;
			List<Sub_Company> list = new ArrayList<Sub_Company>();
			try {
				em = emf.createEntityManager();
				StringBuffer sb = new StringBuffer();
				sb.append(" SELECT V.* FROM  VW_EmployeeWiseSubCompany  V");
				sb.append(" JOIN HR_M_EMPLOYEE HRM ON HRM.EMP_ID= V.EMP_ID");
				sb.append(" JOIN LOCATION L ON L.LOC_ID=HRM.emp_loc_id");
				sb.append(" WHERE V.EMP_ID= :empId");
				sb.append(" AND SUBCOMP_ID= L.loc_SubComp_id");
				
				Query query = em.createNativeQuery(sb.toString(),Tuple.class);
				query.setParameter("empId", empId);
				List<Tuple> tuples = query.getResultList();

				if (tuples != null && tuples.size()>0) {
					for (Tuple t : tuples) {
						Sub_Company sc=new Sub_Company();					
						sc.setSubcomp_id(Long.valueOf(t.get("SubComp_id",Integer.class)));
						sc.setSubcomp_nm(t.get("SubComp_Nm",String.class));
						sc.setSubcomp_code(t.get("subcomp_code",String.class));
						list.add(sc);
					}
				}
			}
			catch (Exception e) {
				e.printStackTrace();
				System.out.println("Error Occurred :" +e.getMessage());// uncomment asneeded --;
			}
			finally {
				if(em != null) { em.close(); }
			}
			System.out.println("Listsub :: "+list.get(0).getSubcomp_code());
			return list.get(0);
		}
		
}
