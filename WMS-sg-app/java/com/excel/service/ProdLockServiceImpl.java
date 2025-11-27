package com.excel.service;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.excel.exception.MedicoException;
import com.excel.model.ProdLock;
import com.excel.repository.ProdLockRepoitory;
import com.excel.repository.TransactionalRepository;

@Service
public class ProdLockServiceImpl implements ProdLockService{
	
	@Autowired TransactionalRepository transactionalaRepository;
	@Autowired ProdLockRepoitory prodLockRepository;
	
	@Override
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public boolean setProdLock(Long prodId, Long locId, String userId) throws Exception{
		boolean isPresent = this.prodLockRepository.getProdLockByProdIdLocIdUserId(prodId, locId);
		if(isPresent) {
			throw new MedicoException("Product is locked by another user. Please select different product");
		} else {
			ProdLock lock = new ProdLock();
			lock.setLoc_id(locId);
			lock.setLog_time(new Date());
			lock.setProd_id(prodId);
			lock.setUser_id(userId);
			transactionalaRepository.persist(lock);
		}		
		return false;		
	}

	@Override
	public boolean deleteByProdIdLocIdUserId(Long prodId, Long locId, String userId) throws Exception {
		return this.prodLockRepository.deleteByProdIdLocIdUserId(prodId, locId, userId);
	}

	@Override
	public boolean getProdLockByProdIdLocIdUserId(Long prodId, Long locId) throws Exception {
		return this.prodLockRepository.getProdLockByProdIdLocIdUserId(prodId, locId);
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public boolean setProdLockForLoc(Long prodId, Long locId, String userId) throws Exception{
		boolean isPresent = this.prodLockRepository.getProdLockByProdIdLocIdUserId(prodId, locId);
		boolean flag=false;
		if(isPresent) {
			flag=false;
			throw new MedicoException("Product is locked by another user. Please select different product");
		} else {
			ProdLock lock = new ProdLock();
			lock.setLoc_id(locId);
			lock.setLog_time(new Date());
			lock.setProd_id(prodId);
			lock.setUser_id(userId);
			transactionalaRepository.persist(lock);
			flag=true;
		}		
		return flag;		
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public boolean deleteProdLockByUserId(String userId) throws Exception {
		return this.prodLockRepository.deleteProdLockByUserId(userId);
	}

}
