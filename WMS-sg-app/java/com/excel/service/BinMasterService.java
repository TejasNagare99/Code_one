package com.excel.service;

import java.util.List;

import com.excel.model.BinMaster;

public interface BinMasterService {

	List<BinMaster> getBinListByLocId(Long locId) throws Exception;
}
