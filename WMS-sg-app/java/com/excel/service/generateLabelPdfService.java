package com.excel.service;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import com.excel.model.ViewGrnLabelPrinting_java;

public interface generateLabelPdfService {
	
	String generateLabelPdf(List<ViewGrnLabelPrinting_java> list ,String bin_no_id,HttpSession session,HttpServletRequest request)throws Exception;



}
