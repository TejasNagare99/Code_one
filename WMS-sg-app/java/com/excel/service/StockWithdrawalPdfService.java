package com.excel.service;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import com.excel.bean.PrintBean;
import com.excel.model.ViewStockWithdrawalVoucherPrint;

public interface StockWithdrawalPdfService {

	public String  generateStockwithdrawalpdf(PrintBean pb,List<ViewStockWithdrawalVoucherPrint> list,String companyName) throws Exception;
	
	public String generateStockWithVoucherPickPdf(PrintBean pb,List<ViewStockWithdrawalVoucherPrint> list,String companyName,HttpSession sesion,HttpServletRequest request) throws Exception;
}
