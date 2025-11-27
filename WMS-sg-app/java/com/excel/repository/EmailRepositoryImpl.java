package com.excel.repository;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

import org.hibernate.criterion.MatchMode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.excel.model.Alloc_gen_hd;
import com.excel.model.Alloc_gen_hd_;
import com.excel.model.AspHeader;
import com.excel.model.Email;
import com.excel.model.EmailFrom;
import com.excel.model.EmailFrom_;

@Repository
public class EmailRepositoryImpl implements EmailRepository {
	@Autowired
	private EntityManagerFactory emf;
	@PersistenceContext private EntityManager entityManager;

	

	@Override
	public EmailFrom getEmailObject(String trantype){
		EntityManager em = null;
		List<EmailFrom> list=null;
		try {
			System.out.println("entityManager "+entityManager);
			CriteriaBuilder builder = entityManager.getCriteriaBuilder();
			CriteriaQuery<EmailFrom> criteriaQuery = builder.createQuery(EmailFrom.class);
			Root<EmailFrom> root = criteriaQuery.from(EmailFrom.class);
			criteriaQuery.select(root).where(builder.equal(root.get(EmailFrom_.tran_type),trantype));
			list=entityManager.createQuery(criteriaQuery).getResultList();
			if(list != null && list.size()>0) {
				return list.get(0);
			} else {
				return null;
			}
		} finally {
			if (em != null) { em.close(); }
		}
	}
	
	@Override
	public void getObjectAspHeader(Long headerId) throws Exception {
		 this.entityManager.find(AspHeader.class, 5);
	}

	@Override
	public List<Email> getEmailList(String tran_type, String divCode) throws Exception {
		// TODO Auto-generated method stub
		List<Email> list=new ArrayList<Email>();
		List<Email> flist=new ArrayList<Email>();
		try {
			CriteriaBuilder builder = entityManager.getCriteriaBuilder();
			CriteriaQuery<Email> criteriaQuery = builder.createQuery(Email.class);
			Root<Email> root = criteriaQuery.from(Email.class);
			criteriaQuery.select(root).where(builder.equal(root.get("tran_type"),tran_type),
											 builder.like(root.get("allow_div_map_cd"),divCode));
			list=entityManager.createQuery(criteriaQuery).getResultList();
			
		    for (int i = 0; i < list.size(); i++) {
				Email e = list.get(i);
				String allAllowedDivisions[] = e.getAllow_div_map_cd().split("#");
				if (!Arrays.asList(allAllowedDivisions).contains(divCode))
					flist.remove(i);
			    }
		}
		catch (Exception e) {
			// TODO: handle exception
			throw e;
		}
		return flist;
		
	}
}
