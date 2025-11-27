package com.excel.repository;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.excel.utility.MedicoConstants;
import com.excel.utility.MedicoResources;

@Repository
public class BrandRepositoryImpl implements BrandRepository , MedicoConstants{
	@PersistenceContext private EntityManager entityManager;
    @Autowired HttpServletRequest request;
    @Autowired private EntityManagerFactory emf;
	
	@Override
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public String saveBrand(String brandName , String emp_Id) throws Exception{
		//String emp_id="";
		try{
			Query query=null;
			Date today = new Date();
			String ip = request.getRemoteAddr();
			StringBuffer buffer = new StringBuffer();
				

				buffer.append(" insert into SAPRODGRP( SA_GROUP_NAME, SA_ins_usr_id ,SA_ins_dt , SA_ins_ip_add , SA_status, ")
				       .append(" SA_MPG_CODE ) values(:brandName ,:emp_Id, :today ,:ip,'A','.') ");
			
			
				query=entityManager.createNativeQuery(buffer.toString());
				query.setParameter("brandName", brandName)
				     .setParameter("emp_Id", emp_Id)
				     .setParameter("today", today)
				     .setParameter("ip", ip);

				System.out.println(query);
		        query.executeUpdate();
			 
		}catch(Exception e){
			throw e;
		}
		return null;
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<Object> getBrandList(String prefix) throws Exception {
		EntityManager em = null;
		List<Object>  list=null;
		try {
//			System.out.println("Prefix " + prefix);
			em = emf.createEntityManager();
			StringBuffer sb = new StringBuffer();
			sb.append(" select RTRIM(SA_GROUP_NAME) from SAPRODGRP where SA_GROUP_NAME like '")
			.append(prefix + "%' ")
			.append(" order by UPPER(SA_GROUP_NAME) asc ");
			
			
			Query query = em.createNativeQuery(sb.toString());
			
			list  = query.getResultList();
			System.out.println("List size " + list.size());
		} finally {
			if (em != null) { em.close(); }
		}
		return list;
	}

}
