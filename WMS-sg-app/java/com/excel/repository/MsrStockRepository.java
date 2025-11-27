package com.excel.repository;

import com.excel.model.Msr_Stock;

public interface MsrStockRepository {

	Msr_Stock getMsr_StockObj(Long fs_id, String period_code, Long product_id, String year) throws Exception;

	void saveArchieve() throws Exception;

}
