package com.excel.service;

import java.util.List;

import com.excel.model.StockAgeingGrnReportView;
import com.excel.model.StockAgeingReportView;

public interface StockAgeing_Report_Service {
		
			String generate_stockAgeingExcel(List<StockAgeingReportView> stockAgeing,
					String to_date, Long slab1, Long slab2, Long slab3,
					Long slab4, Long slab5, Long slab6, Long slab7, Long slab8,
					Long slab9,String ageing_expiry_id,String near_expiry_id,String companyname) throws Exception;

		

			String generate_stockAgeingGrnExcel(List<StockAgeingGrnReportView> stockAgeing, String to_date, Long slab1,
					Long slab2, Long slab3, Long slab4, Long slab5, Long slab6, Long slab7, Long slab8, Long slab9,
					Long slab10, Long slab11, String companyname,String compcode, String reportType,String locName,String empId) throws Exception;
}
