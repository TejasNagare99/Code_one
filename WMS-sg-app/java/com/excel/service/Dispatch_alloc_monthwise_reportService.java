package com.excel.service;

import java.util.List;

import com.excel.model.Dispatch_alloc_monthwise_report;

public interface Dispatch_alloc_monthwise_reportService {

	String generate_dispatch_alloc_monthwise_report(List<Dispatch_alloc_monthwise_report> lst,String startdate,String enddate) throws Exception;
}
