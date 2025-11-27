package com.excel.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.excel.model.BinMaster;
import com.excel.repository.BinMasterRepository;

@Service
public class BinMasterServiceImpl implements BinMasterService{

	@Autowired BinMasterRepository binmasterRepository;
	
	@Override
	public List<BinMaster> getBinListByLocId(Long locId) throws Exception {
		return this.binmasterRepository.getBinListByLocId(locId);
	}

}
