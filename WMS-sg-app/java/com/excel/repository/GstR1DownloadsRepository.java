package com.excel.repository;

import java.util.List;

import com.excel.bean.ReportBean;
import com.excel.model.GSTR_1_B2BA_INV;
import com.excel.model.GSTR_1_B2B_INV;
import com.excel.model.ViewGSTR_1_B2CLA_INV;
import com.excel.model.ViewGSTR_1_B2CL_INV;
import com.excel.model.ViewGSTR_1_B2CSA_INV;
import com.excel.model.ViewGSTR_1_B2CS_INV;
import com.excel.model.ViewGSTR_1_HSN_INV;
import com.excel.model.ViewGSTR_1_TRANSFER;

public interface GstR1DownloadsRepository {

	
	List<GSTR_1_B2BA_INV> getB2BAINVReportData(ReportBean bean);
	List<GSTR_1_B2B_INV> getB2BReportData(ReportBean bean);
	
	List<ViewGSTR_1_B2CS_INV> getB2CSReportData(ReportBean bean) throws Exception;
	List<ViewGSTR_1_B2CL_INV> getB2CLReportData(ReportBean bean) throws Exception;
	List<ViewGSTR_1_HSN_INV> getHSNReportData(ReportBean bean) throws Exception;
	List<ViewGSTR_1_B2CLA_INV> getB2CLAReportData(ReportBean bean) throws Exception;
	List<ViewGSTR_1_TRANSFER> getGstR1TransferData(ReportBean bean) throws Exception;

	List<ViewGSTR_1_B2CSA_INV> getGSTR_1_B2CSA_INVData(ReportBean bean ) throws Exception;

}
