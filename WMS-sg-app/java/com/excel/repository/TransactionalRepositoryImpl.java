package com.excel.repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Repository
public class TransactionalRepositoryImpl implements TransactionalRepository {
	
	@PersistenceContext private EntityManager entityManager;

	@Override
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public void persist(Object object) throws Exception {
		entityManager.persist(object);
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public void delete(Object object) throws Exception {
		entityManager.remove(object);
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public void update(Object object) throws Exception {
		entityManager.merge(object);
	}
	
	@Override
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public void flush() throws Exception {
		entityManager.flush();
	}

}
