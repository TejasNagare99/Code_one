package com.excel.repository;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Query;
import javax.persistence.Tuple;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.excel.model.ProductwiseTaxMaster;
import com.excel.model.SmpItem;

@Repository
public class ProductwiseTaxMasterRepositoryImpl implements ProductwiseTaxMasterRepository{
	
	@Autowired EntityManagerFactory emf;
	
	@Override
	public Boolean checkDetail(String prodCode,String Company) {
		EntityManager em = null;
		Boolean data=false;
		List<Object> list = new ArrayList<Object>();
		try {
			em = emf.createEntityManager();
			String q="select * from PRODUCTWISE_TAX_MASTER where PROD_CODE=:prodCode and COMPANY_CD=:Company";
			Query query = em.createNativeQuery(q,Tuple.class);
			query.setParameter("prodCode", prodCode);
			query.setParameter("Company", Company);
			
			List<Tuple> tuples = query.getResultList();			
			if (tuples != null && !tuples.isEmpty()) {
				if (tuples != null && !tuples.isEmpty()) {
					data=true;
				}
				else {data=false;}
			}
		}
		finally {
			em.close();
		}
		return data;
	}
	@Override
	public ProductwiseTaxMaster checkData(String prodCode,String stateId,String taxCode) {
		System.out.println("detail is :"+prodCode+"..."+stateId+"..."+taxCode);
		Boolean data=null;
		EntityManager em = null;
		ProductwiseTaxMaster obj=null;
		try {
			em = emf.createEntityManager();
			String q="select p from ProductwiseTaxMaster p where  p.prod_code=:prodCode and p.state_id=:stateId";
					//+ "and TAX_CD=:taxCode ";
			Query query = em.createQuery(q,ProductwiseTaxMaster.class);
			query.setParameter("prodCode", prodCode);
			query.setParameter("stateId", Long.valueOf(stateId));
			//query.setParameter("taxCode", taxCode);
			List<ProductwiseTaxMaster> tuples = query.getResultList();
			if (tuples != null && !tuples.isEmpty()) {
				data=true;
				obj=tuples.get((0));
			}
			else {data=false;
			}
		}
		finally {
			em.close();
		}
		return obj;
	}
	@Override
	public Boolean checktaxansateExistForproduct(String taxcode, String stateId, String prodcode) {
		
		
		EntityManager em = null;
		Boolean data=false;
		List<Object> list = new ArrayList<Object>();
		try {
			em = emf.createEntityManager();
			String q="select * from PRODUCTWISE_TAX_MASTER  where PROD_CODE =:prodcode and STATE_ID=:stateId and TAX_CD=:taxcode";
			Query query = em.createNativeQuery(q,Tuple.class);
			query.setParameter("prodcode", prodcode);
			query.setParameter("stateId", stateId);
			query.setParameter("taxcode", taxcode);
			
			List<Tuple> tuples = query.getResultList();			
			if (tuples != null && !tuples.isEmpty()) {
				if (tuples != null && !tuples.isEmpty()) {
					data=true;
				}
				else {data=false;}
			}
		}
		finally {
			em.close();
		}
		return data;
	
		
	}
}
