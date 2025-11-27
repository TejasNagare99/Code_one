package com.excel.service;

import java.util.List;

import com.excel.bean.FinalApprovalLogBean;

public interface FinalApprovalLogPdfService {
	
	public String generateFinalLogApprovalPdf(List<FinalApprovalLogBean> list) throws Exception;
}
