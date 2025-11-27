package com.excel.repository;

import java.util.List;

import com.excel.model.BinMaster;

public interface BinMasterRepository{

	List<BinMaster> getBinListByLocId(Long locId) throws Exception;

}
