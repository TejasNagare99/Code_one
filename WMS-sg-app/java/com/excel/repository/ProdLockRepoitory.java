package com.excel.repository;

public interface ProdLockRepoitory {

	boolean getProdLockByProdIdLocIdUserId(Long prodId, Long locId) throws Exception;
	boolean deleteByProdIdLocIdUserId(Long prodId, Long locId, String userId) throws Exception;
	boolean deleteProdLockByUserId(String userId) throws Exception;

}
