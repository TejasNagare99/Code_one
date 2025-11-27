package com.excel.service;

public interface ProdLockService {
	
	boolean getProdLockByProdIdLocIdUserId(Long prodId, Long locId) throws Exception;
	boolean setProdLock(Long prodId, Long locId, String userId) throws Exception;
	boolean deleteByProdIdLocIdUserId(Long prodId, Long locId, String empId) throws Exception;
	boolean setProdLockForLoc(Long prodId, Long locId, String userId) throws Exception;
	boolean deleteProdLockByUserId(String userId) throws Exception;
}
