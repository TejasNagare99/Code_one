package com.excel.repository;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.excel.model.EmailId_Tranwise;

@Repository
public class EmailTranWiseRepositoryImpl implements EmailTranWiseRepository{
	@Autowired EntityManagerFactory emf;
	@PersistenceContext private EntityManager entityManager;
	
	@Override
	public void DeleteEmailTranwiseByTranId(String trantype, Long tran_id,String finyr) throws Exception {
		// TODO Auto-generated method stub
		try {
			StringBuffer sb = new StringBuffer();
			sb.append("delete EmailId_Tranwise where tran_id = :tran_id and fin_year = :finyr and tran_type=:trantype");
		
			Query q = entityManager.createQuery(sb.toString());
			q.setParameter("tran_id", tran_id);
			q.setParameter("finyr", finyr);
			q.setParameter("trantype", trantype);
			
			q.executeUpdate();
		}
		catch (Exception e) {
			// TODO: handle exception
			throw e;
		}
	}
	
	@Override
	public List<String> getEmailListByTranType(String trantype, String finyrId, Long tranId)
			throws Exception {
		// TODO Auto-generated method stub
		List<EmailId_Tranwise> list = new ArrayList<>();
		List<String> maillist = new ArrayList<>();
		EntityManager em =null;
		try {
			em = emf.createEntityManager();
 			CriteriaBuilder cb = em.getCriteriaBuilder();
			CriteriaQuery<EmailId_Tranwise> query = cb.createQuery(EmailId_Tranwise.class);
			Root<EmailId_Tranwise> root = query.from(EmailId_Tranwise.class);
			query.select(root).where(
							cb.equal(root.get("tran_type"), trantype),
							cb.equal(root.get("fin_year"), finyrId),
							cb.equal(root.get("tran_id"), tranId)
							);
			list = em.createQuery(query).getResultList();
			
			maillist = list.stream().flatMap(e-> Stream.of(e.getEmail_id())).collect(Collectors.toList());
		}
		catch (Exception e) {
			// TODO: handle exception
			throw e;
		}
		finally {
			em.close();
		}
		return maillist;
	}

}
