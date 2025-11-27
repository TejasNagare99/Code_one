package com.excel.repository;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.excel.model.EmailIdTranwiseHelp;
import com.excel.model.EmailIdTranwiseHelp_;

@Repository
public class EmailTranwiseHelpRepositoryImpl implements EmailTranwiseHelpRepository {
	@Autowired EntityManagerFactory emf;
	@PersistenceContext private EntityManager entityManager;
	@Override
	public List<EmailIdTranwiseHelp> getEmailsForHelp(String tranType, String searchString) throws Exception {
		List<EmailIdTranwiseHelp> list = null;
		try {
			list = new ArrayList<EmailIdTranwiseHelp>();
			CriteriaBuilder builder = entityManager.getCriteriaBuilder();
			CriteriaQuery<EmailIdTranwiseHelp> criteriaQuery = builder.createQuery(EmailIdTranwiseHelp.class);
			Root<EmailIdTranwiseHelp> root = criteriaQuery.from(EmailIdTranwiseHelp.class);
			criteriaQuery.multiselect(root.get(EmailIdTranwiseHelp_.email_id))
			.where(builder.equal(root.get(EmailIdTranwiseHelp_.tran_type),tranType),
					builder.like(root.get(EmailIdTranwiseHelp_.email_id),"%"+searchString.trim()+"%"));
			list = entityManager.createQuery(criteriaQuery).getResultList();
		}
		catch(Exception e) {
			throw e;
		}
		return list;
	}
	
	@Override
	public boolean mailExists(String emailId, String tranType) throws Exception {
		boolean isExists = false;
		List<EmailIdTranwiseHelp> list = null;
		try {
			StringBuffer sb = new StringBuffer();
			sb.append("from EmailIdTranwiseHelp where tran_type = :tranType and email_id = :emailId");
		
			Query q = entityManager.createQuery(sb.toString());
			q.setParameter("tranType", tranType);
			q.setParameter("emailId", emailId);
			
			list = q.getResultList();
			if(list!=null && list.size()>0)
				isExists = true;
		}
		catch (Exception e) {
			throw e;
		}
		return isExists;
	}

}
