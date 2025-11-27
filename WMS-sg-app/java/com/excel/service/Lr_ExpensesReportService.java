package com.excel.service;

import java.util.List;

import com.excel.model.Lr_Expenses_Report;

public interface Lr_ExpensesReportService {

	String generate_lr_Expenses_Report(List<Lr_Expenses_Report> lst,String company)throws Exception;
}
