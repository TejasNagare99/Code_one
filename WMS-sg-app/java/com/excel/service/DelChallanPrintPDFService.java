package com.excel.service;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import com.excel.bean.PrintBean;

public interface DelChallanPrintPDFService {
	 String getPdf(List<PrintBean> challans,HttpSession session,HttpServletRequest request) throws Exception;
}
