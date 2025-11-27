package com.excel.repository;

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
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.excel.model.ApprovalMailValidation;
import com.excel.model.ApprovalMailValidation_;
import com.excel.utility.CodifyErrors;

@Repository
public class ApprovalMailValidationRepoImpl implements ApprovalMailValidationRepo {

	@Autowired EntityManagerFactory emf;
	@PersistenceContext private EntityManager entityManager;
	
	@Override
	public boolean checkIfTransactionAlreadyProcessed(Long tran_ref_id,Long task_id) {
		EntityManager em = null;
		try {
			em = emf.createEntityManager();
			CriteriaBuilder builder = em.getCriteriaBuilder();
			CriteriaQuery<ApprovalMailValidation> query = builder.createQuery(ApprovalMailValidation.class);
			Root<ApprovalMailValidation> root = query.from(ApprovalMailValidation.class);
			query.select(root).where(
					builder.and(
							builder.equal(root.get(ApprovalMailValidation_.status), "Y"),
							builder.equal(root.get(ApprovalMailValidation_.tranrefid), tran_ref_id),
							builder.equal(root.get(ApprovalMailValidation_.task_id), task_id)
							));
			List<ApprovalMailValidation> appr_mail_vali_list = em.createQuery(query).getResultList();
			if(appr_mail_vali_list != null && !appr_mail_vali_list.isEmpty()) {
				return true;
			}
		}
		catch(Exception e) {
			e.printStackTrace();
		}
		finally {
			if(em!=null) em.close();
		}
		
		return false;
	}
	
	

	@Override
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public void updateAfterProcessing(Long tran_ref_id, Long task_id, String user_id) throws Exception {
		try {
			StringBuffer buffer=new StringBuffer();
			buffer.append("UPDATE appr_mail_vali set status = 'Y', where task_id = :task_id and tranrefid=:tran_ref_id and approver_user_id=:user_id");
			Query query = entityManager.createNativeQuery(buffer.toString());
			query.setParameter("task_id", task_id);
			query.setParameter("tran_ref_id", tran_ref_id);
			query.setParameter("user_id", user_id);
			query.executeUpdate();
		}catch(Exception e) {
			System.out.println("Error Occurred :" +e.getMessage());// uncomment asneeded --;
			throw e;
		}
	}



	@Override
	public ApprovalMailValidation getByTranRefAndTaskId(Long tran_ref_id, Long task_id,String user_id) throws Exception {
		ApprovalMailValidation appr_mail_vali = null;
		EntityManager em = null;
		try {
			em = emf.createEntityManager();
			CriteriaBuilder builder = em.getCriteriaBuilder();
			CriteriaQuery<ApprovalMailValidation> query = builder.createQuery(ApprovalMailValidation.class);
			Root<ApprovalMailValidation> root = query.from(ApprovalMailValidation.class);
			query.select(root).where(
					builder.and(
							builder.equal(root.get(ApprovalMailValidation_.tranrefid), tran_ref_id),
							builder.equal(root.get(ApprovalMailValidation_.task_id), task_id),
							builder.equal(root.get(ApprovalMailValidation_.approver_user_id), user_id)
							));
			appr_mail_vali = em.createQuery(query).getSingleResult();
		}
		catch(Exception e) {
			e.printStackTrace();
		}
		finally {
			if(em!=null) em.close();
		}
		return appr_mail_vali;
	}



}
