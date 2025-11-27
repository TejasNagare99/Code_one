package com.excel.repository;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Query;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.excel.model.V_GRN_Header;
import com.excel.model.V_GRN_Header_;
import com.excel.utility.MedicoResources;

@Repository
public class V_GRN_HeaderRepositoryImpl implements V_GRN_HeaderRepositoryNew{
	
	@Autowired EntityManagerFactory emf;
	@Autowired EntityManager entityManager;
		
	@Override
	public V_GRN_Header getObjectById(Long grnId) throws Exception {
		return this.entityManager.find(V_GRN_Header.class, grnId);
	}
	
	@Override
	public List<V_GRN_Header> getGrnListByEmpIdAndStatus(String grnStatus, String grnApprStatus, String empId, String companyCode, String finYear) throws Exception {
		EntityManager em = null;
		List<V_GRN_Header> list = null;
		try {
			em = emf.createEntityManager();
			CriteriaBuilder builder = em.getCriteriaBuilder();
			CriteriaQuery<V_GRN_Header> criteriaQuery = builder.createQuery(V_GRN_Header.class);
			Root<V_GRN_Header> root = criteriaQuery.from(V_GRN_Header.class);
			criteriaQuery.select(root)
			.where(builder.equal(root.get(V_GRN_Header_.grnInsUsrId), empId),
					builder.equal(root.get(V_GRN_Header_.grn_status), grnStatus),
					builder.equal(root.get(V_GRN_Header_.grn_appr_status), grnApprStatus),
					builder.equal(root.get(V_GRN_Header_.grn_fin_year), finYear),
					builder.equal(root.get(V_GRN_Header_.grn_company), companyCode));
			list = em.createQuery(criteriaQuery).getResultList();
			return list;
		} finally {
			if (em != null) { em.close(); }
		}
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<V_GRN_Header> getGrnListByLocId(Long locId, String comapnyCode, String empId, String fromDate, String toDate) throws Exception {
		EntityManager em = null;
		List<V_GRN_Header> list= null;
		try {
			em = emf.createEntityManager();
			StringBuffer sb = new StringBuffer();
			sb.append(" SELECT * FROM V_GRN_HEADER ");
			sb.append(" WHERE GRN_LOC_ID = :grn_loc_id AND GRN_COMPANY = :comapnyCode AND GRN_CONFIRM='N' AND GRN_ins_usr_id=:empId ");
			sb.append(" AND CONVERT(DATE,GRN_DT) >= CONVERT(DATE, :fromDate) AND CONVERT(DATE,GRN_DT) <= CONVERT(DATE, :toDate) AND GRN_STATUS='A' ORDER BY GRN_DT, GRN_NO ASC ");
			Query query = this.entityManager.createNativeQuery(sb.toString(), V_GRN_Header.class);
			query.setParameter("grn_loc_id", locId);
			query.setParameter("empId", empId);
			query.setParameter("comapnyCode", comapnyCode);
			query.setParameter("fromDate", MedicoResources.convert_DD_MM_YYYY_toDate(fromDate));
			query.setParameter("toDate", MedicoResources.convert_DD_MM_YYYY_toDate(toDate));
			list = query.getResultList();
			if (list != null && !list.isEmpty()) {
				return list; 
			}
		} finally {
			if (em != null) { em.close(); }
		}
		return null;
	}
	
}
