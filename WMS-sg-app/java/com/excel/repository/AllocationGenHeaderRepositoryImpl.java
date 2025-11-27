package com.excel.repository;

import java.util.Date;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.PersistenceContext;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.excel.model.Alloc_gen_hd;
import com.excel.model.Stock_Transfer_Header;

@Repository
public class AllocationGenHeaderRepositoryImpl implements AllocationGenHeaderRepository {
	@Autowired EntityManagerFactory emf;
	@PersistenceContext private EntityManager entityManager;
	
	
	@Override
	public Alloc_gen_hd getObjectById(Long headerId) throws Exception {
		return this.entityManager.find(Alloc_gen_hd.class, headerId);
	}

	


}
