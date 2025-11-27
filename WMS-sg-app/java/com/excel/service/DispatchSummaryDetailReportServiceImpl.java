package com.excel.service;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.stream.Collectors;

import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.ss.usermodel.BorderStyle;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.FillPatternType;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.VerticalAlignment;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.streaming.SXSSFCell;
import org.apache.poi.xssf.streaming.SXSSFRow;
import org.apache.poi.xssf.streaming.SXSSFSheet;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.apache.poi.xssf.usermodel.XSSFFont;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.excel.bean.ReportBean;
import com.excel.model.AllocationDetailReportModel;
import com.excel.model.BlkChallansGeneratedLog;
import com.excel.model.DispatchDetailGstReport;
import com.excel.model.DispatchDetailReport;
import com.excel.model.DispatchDetailReportRbmdm;
import com.excel.model.DispatchDetailReportRevised;
import com.excel.model.DispatchDetailReportRevisedMdmNo;
import com.excel.model.DispatchDetailReportRevised_SG;
import com.excel.model.DispatchDetailReportRevised_VET;
import com.excel.model.DispatchSummaryDetailReport;
import com.excel.model.DispatchSummaryDetailReport_Revised;
import com.excel.model.SummaryChallanReport;
import com.excel.model.SummaryChallanReportEInvoice;
import com.excel.model.Ter_mst_hierarchy_report_bean;
import com.excel.model.Usermaster;
import com.excel.repository.UserMasterRepository;
import com.excel.security.PasswordManager;
import com.excel.utility.CodifyErrors;
import com.excel.utility.MedicoConstants;
import com.excel.utility.MedicoResources;
import com.excel.utility.ReportFormats;

import net.lingala.zip4j.core.ZipFile;
import net.lingala.zip4j.model.ZipParameters;
import net.lingala.zip4j.util.Zip4jConstants;

@Service
public class DispatchSummaryDetailReportServiceImpl implements DispatchSummaryDetailReportService , MedicoConstants {
	@Autowired private UserMasterService usermasterservice;

	@Autowired UserMasterRepository usermasterrepository;
	@Override
	public String generateDispatchSumDetReport(List<DispatchSummaryDetailReport> list,ReportBean bean,String companyname) throws Exception {
		String filename = "DispatchSummaryReport" + new Date().getTime() + ".xlsx";
		String filepath = REPORT_FILE_PATH + filename;
		XSSFWorkbook workbook = null;
		try {
			workbook = new XSSFWorkbook();
			File f = new File(REPORT_FILE_PATH);
			if (!f.exists()) {
				if (f.mkdirs()) {
				} else {
					throw new RuntimeException("Could not create directory");
				}
			}
			XSSFSheet sheet = workbook.createSheet("Dispatch Summary Report");
			
			XSSFRow row = null;
			XSSFCell cell = null;
			int rowCount = 0;
			int colCount = 0;
			
			String headings[] = { "Sr. No.", "Ware House","Team Name" , "Team Code","Master STN No.","Date of STN","CFA Location",
					"State","Master STN Value","Courier","Master L.R.No.","Master L.R.Date","Cases","Act. WT","Chrg. WT","Employee No.",
					"Employee Name","Region Name","Designation","Mobile No.","DM Name","Territory Code",
					"Territory Name","Challan No.","Challan Date.","Challan Value","Courier","L.R. No.","L.R. Date","Cases",
					"Act. WT","Chrg. WT","Delivery Date","Received by","Remarks"};
			
			XSSFCellStyle headingCellStyle = ReportFormats.heading(workbook);
			XSSFCellStyle columnHeadingStyle = ReportFormats.columnHeading(workbook);
			XSSFCellStyle cellAlignment = ReportFormats.setCellAlignment(workbook, ALIGN_RIGHT);
			
			
			row = sheet.createRow(rowCount);
			cell = row.createCell(colCount);
			sheet.addMergedRegion(new CellRangeAddress(rowCount, rowCount, colCount, colCount + headings.length - 1));
			cell.setCellValue(companyname);
			cell.setCellStyle(headingCellStyle);
			rowCount++;
			
			row = sheet.createRow(rowCount);
			cell = row.createCell(colCount);
			sheet.addMergedRegion(new CellRangeAddress(rowCount, rowCount, colCount, colCount + headings.length - 1));
			cell.setCellValue("Dispatch Summary Statement");
			cell.setCellStyle(headingCellStyle);
			rowCount++;

			row = sheet.createRow(rowCount);
			cell = row.createCell(colCount);
			sheet.addMergedRegion(new CellRangeAddress(rowCount, rowCount, colCount, colCount + headings.length - 1));
			cell.setCellValue("Period: "+MedicoResources.convertUtilDateToString_DD_MM_YYYY_(bean.getStartDate())+" to "+MedicoResources.convertUtilDateToString_DD_MM_YYYY_(bean.getEndDate()));
			cell.setCellStyle(headingCellStyle);
			
			rowCount += 1;
			row = sheet.createRow(rowCount);
			
			for (String heading : headings) {
				cell = ReportFormats.cell(row, colCount, heading, columnHeadingStyle);
				colCount++;
			}
			
			rowCount += 1;
			colCount=0;
			
			row = sheet.createRow(rowCount);
			cell = row.createCell(colCount);
			sheet.addMergedRegion(new CellRangeAddress(rowCount, rowCount, colCount, colCount + 14));
			cell.setCellValue("Master Challan details");
			cell.setCellStyle(headingCellStyle);
			
			cell = row.createCell(colCount+15);
			sheet.addMergedRegion(new CellRangeAddress(rowCount, rowCount, colCount+15, colCount + 34));
			cell.setCellValue("Individual Challan details");
			cell.setCellStyle(headingCellStyle);
			
			sheet.groupRow(0, 2);
			sheet.createFreezePane(7, 5);
			
			rowCount += 1;
			int count=1;
			for (DispatchSummaryDetailReport data : list) {
				colCount = 0;
				row = sheet.createRow(rowCount);
				cell = ReportFormats.cell(row, colCount, String.valueOf(count), cellAlignment);
				colCount++;
				
				cell = ReportFormats.cell(row, colCount, data.getWarehouse(), null);
				colCount++;
				
				cell = ReportFormats.cell(row, colCount, data.getDiv_disp_nm(), null);
				colCount++;
				
				cell = ReportFormats.cell(row, colCount, data.getDiv_map_cd(), null);
				colCount++;
				
				cell = ReportFormats.cell(row, colCount, data.getSumdsp_challan_no(), null);
				colCount++;
				
				cell = ReportFormats.cell(row, colCount, data.getSumdsp_challan_dt(), null);
				colCount++;
				
				cell = ReportFormats.cell(row, colCount, data.getSumdsp_destination(), null);
				colCount++;
				
				cell = ReportFormats.cell(row, colCount, data.getState_name(), null);
				colCount++;
				
				cell = ReportFormats.cellNum(row, colCount, data.getSumdsp_total_goods_val() == null ? 0l : data.getSumdsp_total_goods_val().doubleValue(), cellAlignment);
				colCount++;
				
				cell = ReportFormats.cell(row, colCount, data.getSumdsp_transporter(), null);
				colCount++;
				
				cell = ReportFormats.cell(row, colCount, data.getSumdsp_lr_no(), null);
				colCount++;
				
				cell = ReportFormats.cell(row, colCount, data.getSumdsp_lr_dt(), null);
				colCount++;
				
				cell = ReportFormats.cellNum(row, colCount, data.getSumdsp_totcases() == null ? 0l : data.getSumdsp_totcases().doubleValue(), cellAlignment);
				colCount++;
				
				cell = ReportFormats.cellNum(row, colCount, data.getSum_dsp_act_wt() == null ? 0l : data.getSum_dsp_act_wt().doubleValue(), cellAlignment);
				colCount++;
				
				cell = ReportFormats.cellNum(row, colCount, data.getSum_dsp_bill_wt() == null ? 0l : data.getSum_dsp_bill_wt().doubleValue(), cellAlignment);
				colCount++;
				
				cell = ReportFormats.cell(row, colCount, data.getDspfstaff_employeeno(), null);
				colCount++;
				
				cell = ReportFormats.cell(row, colCount, data.getDspfstaff_displayname(), null);
				colCount++;
				
				cell = ReportFormats.cell(row, colCount, data.getRegion(), null);
				colCount++;
				
				cell = ReportFormats.cell(row, colCount, data.getDspfstaff_desg(), null);
				colCount++;
				
				cell = ReportFormats.cell(row, colCount, data.getMobile(), null);
				colCount++;
				
				
				cell = ReportFormats.cell(row, colCount, data.getAm_name(), null);
				colCount++;
				
				cell = ReportFormats.cell(row, colCount, data.getTerr_code(), null);
				colCount++;
				
				//cell = ReportFormats.cell(row, colCount, data.getPosition(), null);
				//colCount++;
				
				cell = ReportFormats.cell(row, colCount, data.getTerr_name(), null);
				colCount++;
				
				cell = ReportFormats.cell(row, colCount, data.getDsp_challan_no(), null);
				colCount++;
				
				cell = ReportFormats.cell(row, colCount, data.getDsp_challan_dt(), null);
				colCount++;
				
				cell = ReportFormats.cellNum(row, colCount, data.getDsp_total_goods_val() == null ? 0l : data.getDsp_total_goods_val().doubleValue(), cellAlignment);
				colCount++;
				
				cell = ReportFormats.cell(row, colCount, data.getDsp_transporter(), null);
				colCount++;
				
				cell = ReportFormats.cell(row, colCount, data.getDsp_lr_no(), null);
				colCount++;
				
				cell = ReportFormats.cell(row, colCount, data.getDsp_lr_dt(), null);
				colCount++;
				
				cell = ReportFormats.cellNum(row, colCount, data.getDsp_cases() == null ? 0l : data.getDsp_cases().doubleValue(), cellAlignment);
				colCount++;
				
				cell = ReportFormats.cellNum(row, colCount, data.getDsp_act_wt() == null ? 0l : data.getDsp_act_wt().doubleValue(), cellAlignment);
				colCount++;
				
				cell = ReportFormats.cellNum(row, colCount, data.getDsp_bill_wt() == null ? 0l : data.getDsp_bill_wt().doubleValue(), cellAlignment);
				colCount++;
				
				cell = ReportFormats.cell(row, colCount, data.getActual_delivery_date()!= null?data.getActual_delivery_date():"", null);
				colCount++;
				
				cell = ReportFormats.cell(row, colCount, data.getDsp_recd_by(), null);
				colCount++;
				
				cell = ReportFormats.cell(row, colCount, data.getDsp_remark(), null);
				colCount++;
				
				rowCount++;
				count++;
			}
			FileOutputStream fileOutputStream = new FileOutputStream(filepath);
			workbook.write(fileOutputStream);
		} finally {
			if (workbook != null) { workbook.close(); }
		}
		return filename;
	}

	@Override
	public String generateDispatchDetReport(List<DispatchDetailReport> list,ReportBean bean,String companyname) throws Exception {
		String filename = "DispatchDetailReport" + new Date().getTime() + ".xlsx";
		String filepath = REPORT_FILE_PATH + filename;
		XSSFWorkbook workbook = null;
		try {
			workbook = new XSSFWorkbook();
			File f = new File(REPORT_FILE_PATH);
			if (!f.exists()) {
				if (f.mkdirs()) {
				} else {
					throw new RuntimeException("Could not create directory");
				}
			}
			XSSFSheet sheet = workbook.createSheet("Dispatch Detail Report");
			
			XSSFRow row = null;
			XSSFCell cell = null;
			int rowCount = 0;
			int colCount = 0;
			
			String headings[] = { "Sr. No.", "Ware House","Team Name" , "Team Code","Month Of Use","Master STN No.","Date of STN","CFA Location",
					"State","Master STN Value","Courier(to CFA)","Master L.R.No.","Master L.R.Date","Cases(CFA)","Act. WT(CFA)","Chrg. WT(CFA)","Employee No.",
					"Employee Name","Region Name","Designation","Mobile No.","DM Name","Territory Code",
					"Territory Name","Challan No.","Challan Date.","Challan Value","Courier(to PSO)","L.R. No.(to PSO)","L.R. Date(to PSO)","Cases(to PSO)",
					"Act. WT(PSO)","Chrg. WT(PSO)","Product Type","Product Name","Product Code","Batch","Expiry Date",
					"Dispatch Quantity","Rate","Value","Return Quantity"};
			
			XSSFCellStyle headingCellStyle = ReportFormats.heading(workbook);
			XSSFCellStyle columnHeadingStyle = ReportFormats.columnHeading(workbook);
			XSSFCellStyle cellAlignment = ReportFormats.setCellAlignment(workbook, ALIGN_RIGHT);
			
			row = sheet.createRow(rowCount);
			cell = row.createCell(colCount);
			sheet.addMergedRegion(new CellRangeAddress(rowCount, rowCount, colCount, colCount + headings.length - 1));
			cell.setCellValue(companyname);
			cell.setCellStyle(headingCellStyle);
			rowCount++;
			
			row = sheet.createRow(rowCount);
			cell = row.createCell(colCount);
			sheet.addMergedRegion(new CellRangeAddress(rowCount, rowCount, colCount, colCount + headings.length - 1));
			cell.setCellValue("Dispatch Detail Statement");
			cell.setCellStyle(headingCellStyle);
			rowCount++;

			row = sheet.createRow(rowCount);
			cell = row.createCell(colCount);
			sheet.addMergedRegion(new CellRangeAddress(rowCount, rowCount, colCount, colCount + headings.length - 1));
			cell.setCellValue("Period: "+MedicoResources.convertUtilDateToString_DD_MM_YYYY_(bean.getStartDate())+" to "+MedicoResources.convertUtilDateToString_DD_MM_YYYY_(bean.getEndDate()));
			cell.setCellStyle(headingCellStyle);
			
			rowCount += 1;
			row = sheet.createRow(rowCount);
			
			for (String heading : headings) {
				cell = ReportFormats.cell(row, colCount, heading, columnHeadingStyle);
				colCount++;
			}
			
			rowCount += 1;
			colCount=0;
			
			row = sheet.createRow(rowCount);
			cell = row.createCell(colCount);
			sheet.addMergedRegion(new CellRangeAddress(rowCount, rowCount, colCount, colCount + 13));
			cell.setCellValue("Master Challan details");
			cell.setCellStyle(headingCellStyle);
			
			cell = row.createCell(colCount+14);
			sheet.addMergedRegion(new CellRangeAddress(rowCount, rowCount, colCount+14, colCount + 33));
			cell.setCellValue("Individual Challan details");
			cell.setCellStyle(headingCellStyle);
			
			cell = row.createCell(colCount+34);
			sheet.addMergedRegion(new CellRangeAddress(rowCount, rowCount, colCount+34, colCount + 42));
			cell.setCellValue("Product details");
			cell.setCellStyle(headingCellStyle);
			
			sheet.groupRow(0, 2);
			sheet.createFreezePane(7, 5);
			
			rowCount += 1;
			int count=1;
			for (DispatchDetailReport data : list) {
				colCount = 0;
				row = sheet.createRow(rowCount);
				cell = ReportFormats.cell(row, colCount, String.valueOf(count), cellAlignment);
				colCount++;
				
				cell = ReportFormats.cell(row, colCount, data.getWarehouse(), null);
				colCount++;
				
				cell = ReportFormats.cell(row, colCount, data.getDiv_disp_nm(), null);
				colCount++;
				
				cell = ReportFormats.cell(row, colCount, data.getDiv_map_cd(), null);
				colCount++;
				
				cell = ReportFormats.cell(row, colCount, data.getMonth_of_use(), null);
				colCount++;
				
				cell = ReportFormats.cell(row, colCount, data.getSumdsp_challan_no(), null);
				colCount++;
				
				cell = ReportFormats.cell(row, colCount, data.getSumdsp_challan_dt(), null);
				colCount++;
				
				cell = ReportFormats.cell(row, colCount, data.getSumdsp_destination(), null);
				colCount++;
				
				cell = ReportFormats.cell(row, colCount, data.getState_name(), null);
				colCount++;
				
				cell = ReportFormats.cellNum(row, colCount, data.getSumdsp_total_goods_val() == null ? 0l : data.getSumdsp_total_goods_val().doubleValue(), cellAlignment);
				colCount++;
				
				cell = ReportFormats.cell(row, colCount, data.getSumdsp_transporter(), null);
				colCount++;
				
				cell = ReportFormats.cell(row, colCount, data.getSumdsp_lr_no(), null);
				colCount++;
				
				cell = ReportFormats.cell(row, colCount, data.getSumdsp_lr_dt(), null);
				colCount++;
				
				cell = ReportFormats.cellNum(row, colCount, data.getSumdsp_totcases() == null ? 0l : data.getSumdsp_totcases().doubleValue(), cellAlignment);
				colCount++;
				
				cell = ReportFormats.cellNum(row, colCount, data.getSum_dsp_act_wt() == null ? 0l : data.getSum_dsp_act_wt().doubleValue(), cellAlignment);
				colCount++;
				
				cell = ReportFormats.cellNum(row, colCount, data.getSum_dsp_bill_wt() == null ? 0l : data.getSum_dsp_bill_wt().doubleValue(), cellAlignment);
				colCount++;
				
				cell = ReportFormats.cell(row, colCount, data.getDspfstaff_employeeno(), null);
				colCount++;
				
				cell = ReportFormats.cell(row, colCount, data.getDspfstaff_displayname(), null);
				colCount++;
				
				cell = ReportFormats.cell(row, colCount, data.getRegion(), null);
				colCount++;
				
				cell = ReportFormats.cell(row, colCount, data.getDspfstaff_desg(), null);
				colCount++;
				
				cell = ReportFormats.cell(row, colCount, data.getMobile(), null);
				colCount++;
			
				
				cell = ReportFormats.cell(row, colCount, data.getDm_name(), null);
				colCount++;
				
				cell = ReportFormats.cell(row, colCount, data.getTerr_code(), null);
				colCount++;
				
				//cell = ReportFormats.cell(row, colCount, data.getPosition(), null);
				//colCount++;
				
				cell = ReportFormats.cell(row, colCount, data.getTerr_name(), null);
				colCount++;
				
				cell = ReportFormats.cell(row, colCount, data.getDsp_challan_no(), null);
				colCount++;
				
				cell = ReportFormats.cell(row, colCount, data.getDsp_challan_dt(), null);
				colCount++;
				
				cell = ReportFormats.cellNum(row, colCount, data.getDsp_total_goods_val() == null ? 0l : data.getDsp_total_goods_val().doubleValue(), cellAlignment);
				colCount++;
				
				cell = ReportFormats.cell(row, colCount, data.getDsp_transporter(), null);
				colCount++;
				
				cell = ReportFormats.cell(row, colCount, data.getDsp_lr_no(), null);
				colCount++;
				
				cell = ReportFormats.cell(row, colCount, data.getDsp_lr_dt(), null);
				colCount++;
				
				cell = ReportFormats.cellNum(row, colCount, data.getDsp_cases() == null ? 0l : data.getDsp_cases().doubleValue(), cellAlignment);
				colCount++;
				
				cell = ReportFormats.cellNum(row, colCount, data.getDsp_act_wt() == null ? 0l : data.getDsp_act_wt().doubleValue(), cellAlignment);
				colCount++;
				
				cell = ReportFormats.cellNum(row, colCount, data.getDsp_bill_wt() == null ? 0l : data.getDsp_bill_wt().doubleValue(), cellAlignment);
				colCount++;
				
				cell = ReportFormats.cell(row, colCount, data.getSmp_prod_type(), null);
				colCount++;
				
				cell = ReportFormats.cell(row, colCount, data.getSmp_prod_name(), null);
				colCount++;
				
				cell = ReportFormats.cell(row, colCount, data.getSmp_prod_cd(), null);
				colCount++;
				
				cell = ReportFormats.cell(row, colCount, data.getBatch_no(), null);
				colCount++;
				
				cell = ReportFormats.cell(row, colCount, data.getBatch_expiry_dt(), null);
				colCount++;
				
				cell = ReportFormats.cellNum(row, colCount, data.getDspdtl_disp_qty() == null ? 0l : data.getDspdtl_disp_qty().doubleValue(), cellAlignment);
				colCount++;

				cell = ReportFormats.cellNum(row, colCount, data.getDspdtl_rate() == null ? 0l : data.getDspdtl_rate().doubleValue(), cellAlignment);
				colCount++;
				
				cell = ReportFormats.cellNum(row, colCount, data.getValue() == null ? 0l : data.getValue().doubleValue(), cellAlignment);
				colCount++;
				
				cell = ReportFormats.cellNum(row, colCount, data.getReturn_qty() == null ? 0l : data.getReturn_qty().doubleValue(), cellAlignment);
				colCount++;
				rowCount++;
				count++;
			}
			FileOutputStream fileOutputStream = new FileOutputStream(filepath);
			workbook.write(fileOutputStream);
		} finally {
			if (workbook != null) { workbook.close(); }
		}
		return filename;
	}

	@Override
	public String generateSummaryChallanReport(List<SummaryChallanReport> list,ReportBean bean,String companyname,String empId) throws Exception {
		
		
		String filename = "SummaryChallanReport" + new Date().getTime()+ ".xlsx";
		String filepath = REPORT_FILE_PATH + filename ;
		XSSFWorkbook workbook = null;
		try {
			workbook = new XSSFWorkbook();
			File f = new File(REPORT_FILE_PATH);
			if (!f.exists()) {
				if (f.mkdirs()) {
				} else {
					throw new RuntimeException("Could not create directory");
				}
			}
			XSSFSheet sheet = workbook.createSheet("Summary Challans Detail Report - GST");

			XSSFRow row = null;
			XSSFCell cell = null;
			int rowCount = 0;
			int colCount = 0;
			int limit = 28;

			String headings[] = { "Ware House", "Division", "Team Code", "Summary Challan No.", "Challan Date",
					"Destination", "Transporter", "LR No.", "LR Date", "Total Cases", "Product Code", "Product Name",
					"Product Name", "Batch No.", "Expiry Date", "Quantity", "Rate", "Goods Value", "SGST", "CGST",
					"IGST", "HSN CODE", "GSTIN NO", "State Name", "State Code", "Net Amount" };

			XSSFCellStyle headingCellStyle = ReportFormats.heading(workbook);
			XSSFCellStyle columnHeadingStyle = ReportFormats.columnHeading(workbook);
			XSSFCellStyle cellAlignment = ReportFormats.setCellAlignment(workbook, ALIGN_RIGHT);
			XSSFCellStyle heading1CellStyle = ReportFormats.heading1(workbook);

			row = sheet.createRow(rowCount);
			cell = row.createCell(colCount);
			sheet.addMergedRegion(
					new CellRangeAddress(rowCount, rowCount, colCount, ((colCount + headings.length - 1)) + 3));
			cell.setCellValue(companyname);
			cell.setCellStyle(headingCellStyle);
			rowCount++;

			row = sheet.createRow(rowCount);
			cell = row.createCell(colCount);
			sheet.addMergedRegion(
					new CellRangeAddress(rowCount, rowCount, colCount, ((colCount + headings.length - 1)) + 3));
			cell.setCellValue("Summary Challans Detail Report - GST");
			cell.setCellStyle(headingCellStyle);
			rowCount++;

			row = sheet.createRow(rowCount);
			cell = row.createCell(colCount);
			sheet.addMergedRegion(
					new CellRangeAddress(rowCount, rowCount, colCount, ((colCount + headings.length - 1)) + 3));
			cell.setCellValue("Period: " + MedicoResources.convertUtilDateToString_DD_MM_YYYY_(bean.getStartDate())
					+ " to " + MedicoResources.convertUtilDateToString_DD_MM_YYYY_(bean.getEndDate()));
			cell.setCellStyle(headingCellStyle);

			rowCount += 1;
			row = sheet.createRow(rowCount);

			for (int i = 0; i < (headings.length + 3); i++) {
				cell = ReportFormats.cell(row, i, "", columnHeadingStyle);
			}

			int count1 = 0;
			for (String heading : headings) {
				if (count1 == 18 || count1 == 19 || count1 == 20) {
					cell = ReportFormats.cell(row, colCount, heading, columnHeadingStyle);
					sheet.addMergedRegion(new CellRangeAddress(rowCount, rowCount, colCount, colCount + 1));
					colCount += 2;
				} else {
					cell = ReportFormats.cell(row, colCount, heading, columnHeadingStyle);
					sheet.addMergedRegion(new CellRangeAddress(rowCount, rowCount + 1, colCount, colCount));
					colCount++;
				}
				count1++;
			}

			rowCount += 1;
			row = sheet.createRow(rowCount);

			for (int i = 0; i < (headings.length + 3); i++) {
				cell = ReportFormats.cell(row, i, "", columnHeadingStyle);
			}

			colCount = 18;
			cell = ReportFormats.cell(row, colCount, "%", columnHeadingStyle);
			colCount++;
			cell = ReportFormats.cell(row, colCount, "Amount", columnHeadingStyle);
			colCount++;
			cell = ReportFormats.cell(row, colCount, "%", columnHeadingStyle);
			colCount++;
			cell = ReportFormats.cell(row, colCount, "Amount", columnHeadingStyle);
			colCount++;
			cell = ReportFormats.cell(row, colCount, "%", columnHeadingStyle);
			colCount++;
			cell = ReportFormats.cell(row, colCount, "Amount", columnHeadingStyle);
			colCount++;

			sheet.groupRow(0, 2);
			sheet.createFreezePane(7, 5);

			rowCount += 1;
			int count = 1;

			String old_division = "";
			String new_division = "";
			BigDecimal goodsValueTotal = new BigDecimal(0);
			BigDecimal sgstTotal = new BigDecimal(0);
			BigDecimal cgstTotal = new BigDecimal(0);
			BigDecimal igstTotal = new BigDecimal(0);
			BigDecimal netTotal = new BigDecimal(0);

			BigDecimal goodsValueTotalGrand = new BigDecimal(0);
			BigDecimal sgstTotalGrand = new BigDecimal(0);
			BigDecimal cgstTotalGrand = new BigDecimal(0);
			BigDecimal igstTotalGrand = new BigDecimal(0);
			BigDecimal netTotalGrand = new BigDecimal(0);

			BigDecimal netAmount = new BigDecimal(0);

			int i = 0;
			for (SummaryChallanReport data : list) {

				new_division = data.getDiv_disp_nm();
				if (!(new_division.equalsIgnoreCase(old_division))) {
					if (i != 0) {
						row = sheet.createRow(rowCount);
						cell = row.createCell(colCount);
						cell.setCellValue("Division Total : ");
						sheet.addMergedRegion(new CellRangeAddress(rowCount, rowCount, colCount, 16));

						cell = row.createCell(17);
						cell.setCellValue(goodsValueTotal.doubleValue());
						goodsValueTotalGrand = goodsValueTotalGrand.add(goodsValueTotal);

						cell = row.createCell(18);
						cell.setCellValue(sgstTotal.doubleValue());
						sgstTotalGrand = sgstTotalGrand.add(sgstTotal);
						sheet.addMergedRegion(new CellRangeAddress(rowCount, rowCount, 18, 19));

						cell = row.createCell(20);
						cell.setCellValue(cgstTotal.doubleValue());
						cgstTotalGrand = cgstTotalGrand.add(cgstTotal);
						sheet.addMergedRegion(new CellRangeAddress(rowCount, rowCount, 20, 21));

						cell = row.createCell(22);
						cell.setCellValue(igstTotal.doubleValue());
						igstTotalGrand = igstTotalGrand.add(igstTotal);
						sheet.addMergedRegion(new CellRangeAddress(rowCount, rowCount, 22, 23));

						cell = row.createCell(24);
						cell.setCellValue(netTotal.doubleValue());
						netTotalGrand = netTotalGrand.add(netTotal);
						sheet.addMergedRegion(new CellRangeAddress(rowCount, rowCount, 24, 28));

						goodsValueTotal = new BigDecimal(0);
						sgstTotal = new BigDecimal(0);
						cgstTotal = new BigDecimal(0);
						igstTotal = new BigDecimal(0);
						netTotal = new BigDecimal(0);

						rowCount++;
					}
					row = sheet.createRow(rowCount);
					cell = row.createCell(0);
					cell.setCellValue("Division : " + new_division);
					cell.setCellStyle(heading1CellStyle);
					sheet.addMergedRegion(new CellRangeAddress(rowCount, rowCount, 0, limit));
					rowCount++;
				}

				colCount = 0;
				row = sheet.createRow(rowCount);

				cell = ReportFormats.cell(row, colCount, data.getWarehouse(), null);
				colCount++;

				cell = ReportFormats.cell(row, colCount, data.getDiv_disp_nm(), null);
				colCount++;

				cell = ReportFormats.cell(row, colCount, data.getDiv_map_cd(), null);
				colCount++;

				cell = ReportFormats.cell(row, colCount, data.getSumdsp_challan_no(), null);
				colCount++;

				cell = ReportFormats.cell(row, colCount, data.getSumdsp_challan_dt(), null);
				colCount++;

				cell = ReportFormats.cell(row, colCount, data.getSumdsp_destination(), null);
				colCount++;

				cell = ReportFormats.cell(row, colCount, data.getSumdsp_transporter(), null);
				colCount++;

				cell = ReportFormats.cell(row, colCount, data.getSumdsp_lr_no(), null);
				colCount++;

				cell = ReportFormats.cell(row, colCount, data.getSumdsp_lr_dt(), null);
				colCount++;

				cell = ReportFormats.cellNum(row, colCount,
						data.getSumdsp_totcases() == null ? 0l : data.getSumdsp_totcases().doubleValue(),
						cellAlignment);
				colCount++;

				cell = ReportFormats.cell(row, colCount, data.getSmp_prod_cd(), null);
				colCount++;

				cell = ReportFormats.cell(row, colCount, data.getSmp_prod_name(), null);
				colCount++;

				cell = ReportFormats.cell(row, colCount, data.getSmp_prod_type(), null);
				colCount++;

				cell = ReportFormats.cell(row, colCount, data.getBatch_no(), null);
				colCount++;

				cell = ReportFormats.cell(row, colCount, data.getBatch_expiry_dt(), null);
				colCount++;

				cell = ReportFormats.cellNum(row, colCount,
						data.getDspdtl_disp_qty() == null ? 0l : data.getDspdtl_disp_qty().doubleValue(),
						cellAlignment);
				colCount++;

				cell = ReportFormats.cellNum(row, colCount,
						data.getDspdtl_rate() == null ? 0l : data.getDspdtl_rate().doubleValue(), cellAlignment);
				colCount++;

				cell = ReportFormats.cellNum(row, colCount,
						data.getValue() == null ? 0l : data.getValue().doubleValue(), cellAlignment);
				colCount++;

				cell = ReportFormats.cellNum(row, colCount,
						data.getDsgst_rate() == null ? 0l : data.getDsgst_rate().doubleValue(), cellAlignment);
				colCount++;

				cell = ReportFormats.cellNum(row, colCount,
						data.getDsgst_bill_amt() == null ? 0l : data.getDsgst_bill_amt().doubleValue(), cellAlignment);
				colCount++;

				cell = ReportFormats.cellNum(row, colCount,
						data.getDcgst_rate() == null ? 0l : data.getDcgst_rate().doubleValue(), cellAlignment);
				colCount++;

				cell = ReportFormats.cellNum(row, colCount,
						data.getDcgst_bill_amt() == null ? 0l : data.getDcgst_bill_amt().doubleValue(), cellAlignment);
				colCount++;

				cell = ReportFormats.cellNum(row, colCount,
						data.getDigst_rate() == null ? 0l : data.getDigst_rate().doubleValue(), cellAlignment);
				colCount++;

				cell = ReportFormats.cellNum(row, colCount,
						data.getDigst_bill_amt() == null ? 0l : data.getDigst_bill_amt().doubleValue(), cellAlignment);
				colCount++;

				cell = ReportFormats.cell(row, colCount, data.getHsn_code(), null);
				colCount++;

				cell = ReportFormats.cell(row, colCount, data.getGst_reg_no(), null);
				colCount++;

				cell = ReportFormats.cell(row, colCount, data.getStatename(), null);
				colCount++;

				cell = ReportFormats.cell(row, colCount, data.getState_code(), null);
				colCount++;

				netAmount = netAmount.add(data.getValue()
						.add(data.getDsgst_bill_amt().add(data.getDcgst_bill_amt().add(data.getDigst_bill_amt()))));

				cell = ReportFormats.cellNum(row, colCount, netAmount.doubleValue(), cellAlignment);
				colCount++;

				rowCount++;
				colCount = 0;
				count++;
				i++;

				old_division = new_division;

				goodsValueTotal = goodsValueTotal.add(data.getValue());
				sgstTotal = sgstTotal.add(data.getDsgst_bill_amt());
				cgstTotal = cgstTotal.add(data.getDcgst_bill_amt());
				igstTotal = igstTotal.add(data.getDigst_bill_amt());
				netTotal = netTotal.add(data.getValue());
			}

			// System.out.println("Last Row Here"+i);

			row = sheet.createRow(rowCount);
			cell = row.createCell(colCount);
			cell.setCellValue("Division Total : ");
			sheet.addMergedRegion(new CellRangeAddress(rowCount, rowCount, colCount, 16));

			cell = row.createCell(17);
			cell.setCellValue(goodsValueTotal.doubleValue());
			goodsValueTotalGrand = goodsValueTotalGrand.add(goodsValueTotal);

			cell = row.createCell(18);
			cell.setCellValue(sgstTotal.doubleValue());
			sgstTotalGrand = sgstTotalGrand.add(sgstTotal);
			sheet.addMergedRegion(new CellRangeAddress(rowCount, rowCount, 18, 19));

			cell = row.createCell(20);
			cell.setCellValue(cgstTotal.doubleValue());
			cgstTotalGrand = cgstTotalGrand.add(cgstTotal);
			sheet.addMergedRegion(new CellRangeAddress(rowCount, rowCount, 20, 21));

			cell = row.createCell(22);
			cell.setCellValue(igstTotal.doubleValue());
			igstTotalGrand = igstTotalGrand.add(igstTotal);
			sheet.addMergedRegion(new CellRangeAddress(rowCount, rowCount, 22, 23));

			cell = row.createCell(24);
			cell.setCellValue(netTotal.doubleValue());
			netTotalGrand = netTotalGrand.add(netTotal);
			sheet.addMergedRegion(new CellRangeAddress(rowCount, rowCount, 24, 28));

			goodsValueTotal = new BigDecimal(0);
			sgstTotal = new BigDecimal(0);
			cgstTotal = new BigDecimal(0);
			igstTotal = new BigDecimal(0);
			netTotal = new BigDecimal(0);

			rowCount++;
			colCount = 0;

			row = sheet.createRow(rowCount);
			cell = row.createCell(colCount);
			cell.setCellValue("Grand Total : ");
			sheet.addMergedRegion(new CellRangeAddress(rowCount, rowCount, colCount, 16));

			cell = row.createCell(17);
			cell.setCellValue(goodsValueTotalGrand.doubleValue());

			cell = row.createCell(18);
			cell.setCellValue(sgstTotalGrand.doubleValue());
			sheet.addMergedRegion(new CellRangeAddress(rowCount, rowCount, 18, 19));

			cell = row.createCell(20);
			cell.setCellValue(cgstTotalGrand.doubleValue());
			sheet.addMergedRegion(new CellRangeAddress(rowCount, rowCount, 20, 21));

			cell = row.createCell(22);
			cell.setCellValue(igstTotalGrand.doubleValue());
			sheet.addMergedRegion(new CellRangeAddress(rowCount, rowCount, 22, 23));

			cell = row.createCell(24);
			cell.setCellValue(netTotalGrand.doubleValue());
			sheet.addMergedRegion(new CellRangeAddress(rowCount, rowCount, 24, 28));

			FileOutputStream fileOutputStream = new FileOutputStream(filepath);
			workbook.write(fileOutputStream);
			
			filename=usermasterservice.generateExcellock(filepath, filename,empId,".xlsx");

		} finally {
			if (workbook != null) {
				workbook.close();
			}
		}
		return filename;
	}

	@Override
	public String generateDispatchSumDetReportRevised(List<DispatchSummaryDetailReport_Revised> list, ReportBean bean,
			String companyname,String empId) throws Exception {
		
		String nameonly = "DispatchSummaryReport" + new Date().getTime();
		String filename = "DispatchSummaryReport" + new Date().getTime() + ".xlsx";
		String filepath = REPORT_FILE_PATH + filename;
		XSSFWorkbook workbook = null;
		try {
			workbook = new XSSFWorkbook();
			File f = new File(REPORT_FILE_PATH);
			if (!f.exists()) {
				if (f.mkdirs()) {
				} else {
					throw new RuntimeException("Could not create directory");
				}
			}
			XSSFSheet sheet = workbook.createSheet("Dispatch Summary Report");

			XSSFRow row = null;
			XSSFCell cell = null;
			int rowCount = 0;
			int colCount = 0;

			String headings[] = { "Sr. No.", "Ware House", "Team Name", "Team Code", "Master STN No.", "Date of STN",
					"CFA Location", "State", "Master STN Value", "Courier", "Master L.R.No.", "Master L.R.Date",
					"Cases", "Act. WT", "Chrg. WT", "Employee No.", "Employee/Doctor Name", "Region Name",
					"Designation", "Mobile No.", "DM Name", "Territory Code", "Territory Name", "Challan No.",
					"Challan Date.", "Challan Value", "Courier", "L.R. No.", "L.R. Date", "Cases", "Act. WT",
					"Chrg. WT", "Tentative Delivery Date", "Delivery Date", "Recieved By", "Courier Comments",
					"Remarks", "Emergency / DtD Requestor Name", "Total Courier Exp", "Team Code","Srt Number","Srt Date","Srt Remark" };

			XSSFCellStyle headingCellStyle = ReportFormats.heading(workbook);
			XSSFCellStyle columnHeadingStyle = ReportFormats.columnHeading(workbook);
			XSSFCellStyle cellAlignment = ReportFormats.setCellAlignment(workbook, ALIGN_RIGHT);
			cellAlignment.setDataFormat(workbook.createDataFormat().getFormat("0.00"));

			XSSFCellStyle cellAlignment2 = ReportFormats.setCellAlignment(workbook, ALIGN_RIGHT);

			row = sheet.createRow(rowCount);
			cell = row.createCell(colCount);
			sheet.addMergedRegion(new CellRangeAddress(rowCount, rowCount, colCount, colCount + headings.length - 1));
			cell.setCellValue(companyname);
			cell.setCellStyle(headingCellStyle);
			rowCount++;

			row = sheet.createRow(rowCount);
			cell = row.createCell(colCount);
			sheet.addMergedRegion(new CellRangeAddress(rowCount, rowCount, colCount, colCount + headings.length - 1));
			cell.setCellValue("Dispatch Summary Statement");
			cell.setCellStyle(headingCellStyle);
			rowCount++;

			row = sheet.createRow(rowCount);
			cell = row.createCell(colCount);
			sheet.addMergedRegion(new CellRangeAddress(rowCount, rowCount, colCount, colCount + headings.length - 1));
			cell.setCellValue("Period: " + MedicoResources.convertUtilDateToString_DD_MM_YYYY_(bean.getStartDate())
					+ " to " + MedicoResources.convertUtilDateToString_DD_MM_YYYY_(bean.getEndDate()));
			cell.setCellStyle(headingCellStyle);

			rowCount += 1;
			row = sheet.createRow(rowCount);

			for (String heading : headings) {
				cell = ReportFormats.cell(row, colCount, heading, columnHeadingStyle);
				colCount++;
			}

			rowCount += 1;
			colCount = 0;

			row = sheet.createRow(rowCount);
			cell = row.createCell(colCount);
			sheet.addMergedRegion(new CellRangeAddress(rowCount, rowCount, colCount, colCount + 14));
			cell.setCellValue("Master Challan details");
			cell.setCellStyle(headingCellStyle);

			cell = row.createCell(colCount + 15);
			sheet.addMergedRegion(new CellRangeAddress(rowCount, rowCount, colCount + 15, colCount + 39));
			cell.setCellValue("Individual Challan details");
			cell.setCellStyle(headingCellStyle);

			sheet.groupRow(0, 2);
			sheet.createFreezePane(7, 5);

			rowCount += 1;
			int count = 1;
			
			LocalDate endDate = bean.getEndDate().toInstant()
                    .atZone(ZoneId.systemDefault())
                    .toLocalDate();

			LocalDate cutoffDate = LocalDate.of(2024, 12, 1);

			if(bean.getCompcd().trim().equalsIgnoreCase(PFZ) && endDate.isBefore(cutoffDate)) {

				list = list.stream().peek(data -> {
					if ("CITIUS".equalsIgnoreCase(data.getDiv_disp_nm().trim())
							|| "CITIUS  (ALTIUS - NEW)".equalsIgnoreCase(data.getDiv_disp_nm().trim())) {
						data.setDiv_disp_nm("ALTIUS - NEW");
					}
				}).collect(Collectors.toList());

			}
			for (DispatchSummaryDetailReport_Revised data : list) {
				colCount = 0;
				row = sheet.createRow(rowCount);
				cell = ReportFormats.cell(row, colCount, String.valueOf(count), cellAlignment);
				colCount++;

				cell = ReportFormats.cell(row, colCount, data.getWarehouse(), null);
				colCount++;

				cell = ReportFormats.cell(row, colCount, data.getDiv_disp_nm(), null);
				colCount++;

				cell = ReportFormats.cell(row, colCount, data.getDiv_map_cd(), null);
				colCount++;

				cell = ReportFormats.cell(row, colCount, data.getSumdsp_challan_no(), null);
				colCount++;

				cell = ReportFormats.cell(row, colCount, data.getSumdsp_challan_dt(), null);
				colCount++;

				cell = ReportFormats.cell(row, colCount, data.getSumdsp_destination(), null);
				colCount++;

				cell = ReportFormats.cell(row, colCount, data.getState_name(), null);
				colCount++;

				cell = ReportFormats.cellNum(row, colCount,
						data.getSumdsp_total_goods_val() == null ? 0l : data.getSumdsp_total_goods_val().doubleValue(),
						cellAlignment);
				colCount++;

				cell = ReportFormats.cell(row, colCount, data.getSumdsp_transporter(), null);
				colCount++;

				cell = ReportFormats.cell(row, colCount, data.getSumdsp_lr_no(), null);
				colCount++;

				cell = ReportFormats.cell(row, colCount, data.getSumdsp_lr_dt(), null);
				colCount++;

				cell = ReportFormats.cellNum(row, colCount,
						data.getSumdsp_totcases() == null ? 0l : data.getSumdsp_totcases().doubleValue(),
						cellAlignment2);
				colCount++;

				cell = ReportFormats.cellNum(row, colCount,
						data.getSum_dsp_act_wt() == null ? 0l : data.getSum_dsp_act_wt().doubleValue(), cellAlignment);
				colCount++;

				cell = ReportFormats.cellNum(row, colCount,
						data.getSum_dsp_bill_wt() == null ? 0l : data.getSum_dsp_bill_wt().doubleValue(),
						cellAlignment);
				colCount++;

				cell = ReportFormats.cell(row, colCount, data.getDspfstaff_employeeno(), null);
				colCount++;

				cell = ReportFormats.cell(row, colCount, data.getDspfstaff_displayname(), null);
				colCount++;

				cell = ReportFormats.cell(row, colCount, data.getRegion(), null);
				colCount++;

				cell = ReportFormats.cell(row, colCount, data.getDspfstaff_desg(), null);
				colCount++;

				cell = ReportFormats.cell(row, colCount, data.getMobile(), null);
				colCount++;

				cell = ReportFormats.cell(row, colCount, data.getAm_name(), null);
				colCount++;

				cell = ReportFormats.cell(row, colCount, data.getTerr_code(), null);
				colCount++;

				// cell = ReportFormats.cell(row, colCount, data.getPosition(), null);
				// colCount++;

				cell = ReportFormats.cell(row, colCount, data.getTerr_name(), null);
				colCount++;

				cell = ReportFormats.cell(row, colCount, data.getDsp_challan_no(), null);
				colCount++;

				cell = ReportFormats.cell(row, colCount, data.getDsp_challan_dt(), null);
				colCount++;

				cell = ReportFormats.cellNum(row, colCount,
						data.getDsp_total_goods_val() == null ? 0l : data.getDsp_total_goods_val().doubleValue(),
						cellAlignment);
				colCount++;

				cell = ReportFormats.cell(row, colCount, data.getDsp_transporter(), null);
				colCount++;

				cell = ReportFormats.cell(row, colCount, data.getDsp_lr_no(), null);
				colCount++;

				cell = ReportFormats.cell(row, colCount, data.getDsp_lr_dt(), null);
				colCount++;

				cell = ReportFormats.cellNum(row, colCount,
						data.getDsp_cases() == null ? 0l : data.getDsp_cases().doubleValue(), cellAlignment2);
				colCount++;

				cell = ReportFormats.cellNum(row, colCount,
						data.getDsp_act_wt() == null ? 0l : data.getDsp_act_wt().doubleValue(), cellAlignment);
				colCount++;

				cell = ReportFormats.cellNum(row, colCount,
						data.getDsp_bill_wt() == null ? 0l : data.getDsp_bill_wt().doubleValue(), cellAlignment);
				colCount++;

				
				cell = ReportFormats.cell(row, colCount, data.getTentative_delivery_dt(), null);
				colCount++;

				cell = ReportFormats.cell(row, colCount,
						data.getActual_delivery_date() != null ? data.getActual_delivery_date() : "", null);
				colCount++;

				cell = ReportFormats.cell(row, colCount, data.getRecieved_by(), null);
				colCount++;

				cell = ReportFormats.cell(row, colCount, data.getCourier_comment(), null);
				colCount++;

				cell = ReportFormats.cell(row, colCount, data.getRemarks(), null);
				colCount++;

				cell = ReportFormats.cell(row, colCount, data.getEmer_dtd_supplied_to(), null);
				colCount++;

				cell = ReportFormats.cellNum(row, colCount,
						data.getCourier_expenses() == null ? 0l : data.getCourier_expenses().doubleValue(),
						cellAlignment);
				colCount++;

				cell = ReportFormats.cell(row, colCount, data.getTeam_code(), null);
				colCount++;
				
				
				
				
				cell = ReportFormats.cell(row, colCount, data.getSrt_number(), null);
				colCount++;
				
				cell = ReportFormats.cell(row, colCount, data.getSrt_date(), null);
				colCount++;
				
				cell = ReportFormats.cell(row, colCount, data.getSrt_remark(), null);
				colCount++;

				
				
				
				rowCount++;
				count++;
			}
			FileOutputStream fileOutputStream = new FileOutputStream(filepath);
			workbook.write(fileOutputStream);
			filename=usermasterservice.generateExcellock(filepath, filename,empId,".xlsx");

		} finally {
			if (workbook != null) {
				workbook.close();
			}
		}
		return filename ;
	}

//	@Override
//	public String generateDispatchDetReportRevised(List<DispatchDetailReportRevised> list, ReportBean bean,
//			String companyname,String empId, String cfa_to_stockist_ind) throws Exception {
//		String nameonly = "DispatchDetailReport" + new Date().getTime();
//		String filename = "DispatchDetailReport" + new Date().getTime() + ".xlsx";
//		String filepath = REPORT_FILE_PATH + filename;
//		SXSSFWorkbook workbook = null;
//		try {
//			workbook = new SXSSFWorkbook(1);
//			File f = new File(REPORT_FILE_PATH);
//			if (!f.exists()) {
//				if (f.mkdirs()) {
//				} else {
//					throw new RuntimeException("Could not create directory");
//				}
//			}
//			SXSSFSheet sheet = workbook.createSheet("Dispatch Detail Report");
//
//			Row row = null;
//			Cell cell = null;
//			int rowCount = 0;
//			int colCount = 0;
//			String headings[];
//			
//			
////			 headings[] = { "Sr. No.", "Ware House", "Team Name", "Team Code", "Month Of Use", "Master STN No.",
////					"Date of STN", "CFA Location", "State", "Master STN Value", "Courier(to CFA)", "Master L.R.No.",
////					"Master L.R.Date", "Cases(CFA)", "Act. WT(CFA)", "Chrg. WT(CFA)", "Requestor/Employee Number",
////					"Employee/Doctor Name", "Region Name", "Designation", "Mobile No.", "DM Name", "Territory Code",
////					"Territory Name", "Challan No.", "Challan Date.", "Challan Value", "Courier(to PSO)",
////					"L.R. No.(to PSO)", "L.R. Date(to PSO)", "Cases(to PSO)", "Act. WT(PSO)", "Chrg. WT(PSO)",
////					"Product Type", "Product Name", "Product Code", "Batch", "Expiry Date", "Dispatch Quantity", "Rate",
////					"Value", "Return Quantity", "Tentative Delivery Date", "Delivery Date", "Recieved By",
////					"Courier Comments", "Remarks", "Emergency / DtD Requestor Name", "Team Code" ,"srt Number","Srt Date","Srt Remark"};
//			 
//			 
//
//			
//
//				if ("Y".equalsIgnoreCase(cfa_to_stockist_ind)) {
//				    headings = new String[] { "Sr. No.", "Ware House", "Team Name", "Team Code", "Month Of Use", "Master STN No.",
//							"Date of STN", "CFA Location", "State", "Master STN Value", "Courier(to CFA)", "Master L.R.No.",
//							"Master L.R.Date", "Cases(CFA)", "Act. WT(CFA)", "Chrg. WT(CFA)",
//							"Employee/Doctor Name", "Region Name", "Designation", "Mobile No.", "DM Name", "Territory Code",
//							"Territory Name", "Challan No.", "Challan Date.", "Challan Value", "Courier(to PSO)",
//							"L.R. No.(to PSO)", "L.R. Date(to PSO)", "Cases(to PSO)", "Act. WT(PSO)", "Chrg. WT(PSO)",
//							"Product Type", "Product Name", "Product Code", "Batch", "Expiry Date", "Dispatch Quantity", "Rate",
//							"Value"};
//				} else {
//				    headings = new String[] { "Sr. No.", "Ware House", "Team Name", "Team Code", "Month Of Use", "Master STN No.",
//							"Date of STN", "CFA Location", "State", "Master STN Value", "Courier(to CFA)", "Master L.R.No.",
//							"Master L.R.Date", "Cases(CFA)", "Act. WT(CFA)", "Chrg. WT(CFA)", "Requestor/Employee Number",
//							"Employee/Doctor Name", "Region Name", "Designation", "Mobile No.", "DM Name", "Territory Code",
//							"Territory Name", "Challan No.", "Challan Date.", "Challan Value", "Courier(to PSO)",
//							"L.R. No.(to PSO)", "L.R. Date(to PSO)", "Cases(to PSO)", "Act. WT(PSO)", "Chrg. WT(PSO)",
//							"Product Type", "Product Name", "Product Code", "Batch", "Expiry Date", "Dispatch Quantity", "Rate",
//							"Value", "Return Quantity", "Tentative Delivery Date", "Delivery Date", "Recieved By",
//							"Courier Comments", "Remarks", "Emergency / DtD Requestor Name", "Team Code" ,"srt Number","Srt Date","Srt Remark"};
//				}
//			
//			
//			
//			
//			
//
//			CellStyle headingCellStyle = ReportFormats.heading(workbook);
//			CellStyle columnHeadingStyle = ReportFormats.columnHeading(workbook);
//			CellStyle cellAlignment = ReportFormats.setCellAlignment(workbook, ALIGN_RIGHT);
//			cellAlignment.setDataFormat(workbook.createDataFormat().getFormat("0.00"));
//
//			CellStyle cellAlignment2 = ReportFormats.setCellAlignment(workbook, ALIGN_RIGHT);
//
//			row = sheet.createRow(rowCount);
//			cell = row.createCell(colCount);
//			sheet.addMergedRegion(new CellRangeAddress(rowCount, rowCount, colCount, colCount + headings.length - 1));
//			cell.setCellValue(companyname);
//			cell.setCellStyle(headingCellStyle);
//			rowCount++;
//
//			row = sheet.createRow(rowCount);
//			cell = row.createCell(colCount);
//			sheet.addMergedRegion(new CellRangeAddress(rowCount, rowCount, colCount, colCount + headings.length - 1));
//			cell.setCellValue("Dispatch Detail Statement");
//			cell.setCellStyle(headingCellStyle);
//			rowCount++;
//
//			row = sheet.createRow(rowCount);
//			cell = row.createCell(colCount);
//			sheet.addMergedRegion(new CellRangeAddress(rowCount, rowCount, colCount, colCount + headings.length - 1));
//			cell.setCellValue("Period: " + MedicoResources.convertUtilDateToString_DD_MM_YYYY_(bean.getStartDate())
//					+ " to " + MedicoResources.convertUtilDateToString_DD_MM_YYYY_(bean.getEndDate()));
//			cell.setCellStyle(headingCellStyle);
//
//			rowCount += 1;
//			row = sheet.createRow(rowCount);
//
//			for (String heading : headings) {
//				cell = ReportFormats.cell(row, colCount, heading, columnHeadingStyle);
//				colCount++;
//			}
//
//			rowCount += 1;
//			colCount = 0;
//
//			row = sheet.createRow(rowCount);
//			cell = row.createCell(colCount);
//			sheet.addMergedRegion(new CellRangeAddress(rowCount, rowCount, colCount, colCount + 13));
//			
//			if ("Y".equalsIgnoreCase(cfa_to_stockist_ind)) {
//			cell.setCellValue("Packing Slip");
//			}else {
//				cell.setCellValue("Master Challan details");
//			}
//			cell.setCellStyle(headingCellStyle);
//
//			cell = row.createCell(colCount + 14);
//			sheet.addMergedRegion(new CellRangeAddress(rowCount, rowCount, colCount + 14, colCount + 33));
//			cell.setCellValue("Individual Challan details");
//			cell.setCellStyle(headingCellStyle);
//
//			cell = row.createCell(colCount + 34);
//			sheet.addMergedRegion(new CellRangeAddress(rowCount, rowCount, colCount + 34, colCount + 39));
//			cell.setCellValue("Product details");
//			cell.setCellStyle(headingCellStyle);
//
//			sheet.groupRow(0, 2);
//			sheet.createFreezePane(7, 5);
//
//			rowCount += 1;
//			int count = 1;
//			Iterator<DispatchDetailReportRevised> dataiterator = list.iterator();
//			DispatchDetailReportRevised data = null;
//			// for (DispatchDetailReportRevised data : list) {
//			while (dataiterator.hasNext()) {
//				colCount = 0;
//				data = dataiterator.next();
//				row = sheet.createRow(rowCount);
//				cell = ReportFormats.cell(row, colCount, String.valueOf(count), cellAlignment);
//				colCount++;
//
//				cell = ReportFormats.cell(row, colCount, data.getWarehouse(), null);
//				colCount++;
//
//				cell = ReportFormats.cell(row, colCount, data.getDiv_disp_nm(), null);
//				colCount++;
//
//				cell = ReportFormats.cell(row, colCount, data.getDiv_map_cd(), null);
//				colCount++;
//
//				cell = ReportFormats.cell(row, colCount, data.getMonth_of_use(), null);
//				colCount++;
//
//				cell = ReportFormats.cell(row, colCount, data.getSumdsp_challan_no(), null);
//				colCount++;
//
//				cell = ReportFormats.cell(row, colCount, data.getSumdsp_challan_dt(), null);
//				colCount++;
//
//				cell = ReportFormats.cell(row, colCount, data.getSumdsp_destination(), null);
//				colCount++;
//
//				cell = ReportFormats.cell(row, colCount, data.getState_name(), null);
//				colCount++;
//
//				cell = ReportFormats.cellNum(row, colCount,
//						data.getSumdsp_total_goods_val() == null ? 0l : data.getSumdsp_total_goods_val().doubleValue(),
//						cellAlignment);
//				colCount++;
//
//				cell = ReportFormats.cell(row, colCount, data.getSumdsp_transporter(), null);
//				colCount++;
//
//				cell = ReportFormats.cell(row, colCount, data.getSumdsp_lr_no(), null);
//				colCount++;
//
//				cell = ReportFormats.cell(row, colCount, data.getSumdsp_lr_dt(), null);
//				colCount++;
//
//				cell = ReportFormats.cellNum(row, colCount,
//						data.getSumdsp_totcases() == null ? 0l : data.getSumdsp_totcases().doubleValue(),
//						cellAlignment2);
//				colCount++;
//
//				cell = ReportFormats.cellNum(row, colCount,
//						data.getSum_dsp_act_wt() == null ? 0l : data.getSum_dsp_act_wt().doubleValue(), cellAlignment);
//				colCount++;
//
//				cell = ReportFormats.cellNum(row, colCount,
//						data.getSum_dsp_bill_wt() == null ? 0l : data.getSum_dsp_bill_wt().doubleValue(),
//						cellAlignment);
//				colCount++;
//
//				cell = ReportFormats.cell(row, colCount, data.getDspfstaff_employeeno(), null);
//				colCount++;
//
//				
//				
//				if (!"Y".equalsIgnoreCase(cfa_to_stockist_ind)) {
//				cell = ReportFormats.cell(row, colCount, data.getDspfstaff_displayname(), null);
//				colCount++;
//				}
//				cell = ReportFormats.cell(row, colCount, data.getRegion(), null);
//				colCount++;
//
//				cell = ReportFormats.cell(row, colCount, data.getDspfstaff_desg(), null);
//				colCount++;
//
//				cell = ReportFormats.cell(row, colCount, data.getMobile(), null);
//				colCount++;
//
//				cell = ReportFormats.cell(row, colCount, data.getDm_name(), null);
//				colCount++;
//
//				cell = ReportFormats.cell(row, colCount, data.getTerr_code(), null);
//				colCount++;
//
//				// cell = ReportFormats.cell(row, colCount, data.getPosition(), null);
//				// colCount++;
//
//				cell = ReportFormats.cell(row, colCount, data.getTerr_name(), null);
//				colCount++;
//
//				cell = ReportFormats.cell(row, colCount, data.getDsp_challan_no(), null);
//				colCount++;
//
//				cell = ReportFormats.cell(row, colCount, data.getDsp_challan_dt(), null);
//				colCount++;
//
//				cell = ReportFormats.cellNum(row, colCount,
//						data.getDsp_total_goods_val() == null ? 0l : data.getDsp_total_goods_val().doubleValue(),
//						cellAlignment);
//				colCount++;
//
//				cell = ReportFormats.cell(row, colCount, data.getDsp_transporter(), null);
//				colCount++;
//
//				cell = ReportFormats.cell(row, colCount, data.getDsp_lr_no(), null);
//				colCount++;
//
//				cell = ReportFormats.cell(row, colCount, data.getDsp_lr_dt(), null);
//				colCount++;
//
//				cell = ReportFormats.cellNum(row, colCount,
//						data.getDsp_cases() == null ? 0l : data.getDsp_cases().doubleValue(), cellAlignment2);
//				colCount++;
//
//				cell = ReportFormats.cellNum(row, colCount,
//						data.getDsp_act_wt() == null ? 0l : data.getDsp_act_wt().doubleValue(), cellAlignment);
//				colCount++;
//
//				cell = ReportFormats.cellNum(row, colCount,
//						data.getDsp_bill_wt() == null ? 0l : data.getDsp_bill_wt().doubleValue(), cellAlignment);
//				colCount++;
//
//				cell = ReportFormats.cell(row, colCount, data.getSmp_prod_type(), null);
//				colCount++;
//
//				cell = ReportFormats.cell(row, colCount, data.getSmp_prod_name(), null);
//				colCount++;
//
//				cell = ReportFormats.cell(row, colCount, data.getSmp_prod_cd(), null);
//				colCount++;
//
//				cell = ReportFormats.cell(row, colCount, data.getBatch_no(), null);
//				colCount++;
//
//				cell = ReportFormats.cell(row, colCount, data.getBatch_expiry_dt(), null);
//				colCount++;
//
//				cell = ReportFormats.cellNum(row, colCount,
//						data.getDspdtl_disp_qty() == null ? 0l : data.getDspdtl_disp_qty().doubleValue(),
//						cellAlignment);
//				colCount++;
//
//				cell = ReportFormats.cellNum(row, colCount,
//						data.getDspdtl_rate() == null ? 0l : data.getDspdtl_rate().doubleValue(), cellAlignment);
//				colCount++;
//
//				cell = ReportFormats.cellNum(row, colCount,
//						data.getValue() == null ? 0l : data.getValue().doubleValue(), cellAlignment);
//				colCount++;
//
//				
//				if (!"Y".equalsIgnoreCase(cfa_to_stockist_ind)) {
//				cell = ReportFormats.cellNum(row, colCount,
//						data.getReturn_qty() == null ? 0l : data.getReturn_qty().doubleValue(), cellAlignment);
//				colCount++;
//
//				
//				cell = ReportFormats.cell(row, colCount, data.getTentative_delivery_date(), null);
//				colCount++;
//				cell = ReportFormats.cell(row, colCount, data.getActual_delivery_date(), null);
//				colCount++;
//				}
//
//			
//
//				
//				if (!"Y".equalsIgnoreCase(cfa_to_stockist_ind)) {
//				
//				cell = ReportFormats.cell(row, colCount, data.getRecieved_by(), null);
//				colCount++;
//
//				cell = ReportFormats.cell(row, colCount, data.getCourier_comment(), null);
//				colCount++;
//				
//				cell = ReportFormats.cell(row, colCount, data.getEmer_dtd_supplied_to(), null);
//				colCount++;
//
//				}
//
//			
//
//		
//				
//				if (!"Y".equalsIgnoreCase(cfa_to_stockist_ind)) {
//					
//					cell = ReportFormats.cell(row, colCount, data.getRemarks(), null);
//					colCount++;
//					
//					cell = ReportFormats.cell(row, colCount, data.getTeam_code(), null);
//					colCount++;
//					
//				cell = ReportFormats.cell(row, colCount, data.getSrt_number(), null);
//				colCount++;
//				
//				cell = ReportFormats.cell(row, colCount, data.getSrt_date().trim().equals("1900-01-01")?"":data.getSrt_date(), null);
//				colCount++;
//				
//				cell = ReportFormats.cell(row, colCount, data.getSrt_remark(), null);
//				colCount++;
//				}
//				
//
//				rowCount++;
//				count++;
//				dataiterator.remove();
//
//			}
//			FileOutputStream fileOutputStream = new FileOutputStream(filepath);
//			workbook.write(fileOutputStream);
//
//			filename=usermasterservice.generateExcellock(filepath, filename,empId,".xlsx");
//
//		} catch (Throwable e) {
//			// TODO Auto-generated catch block
//			// System.out.println("Error Occurred :" +e.getMessage());// uncomment asneeded --;
//		} finally {
//			if (workbook != null) {
//				workbook.close();
//				workbook.dispose();
//			}
//		}
//		return filename;
//	}
//	
	@Override
	public String generateDispatchDetReportRevised(List<DispatchDetailReportRevised> list, ReportBean bean,
			String companyname,String empId, String cfa_to_stockist_ind) throws Exception {
		String nameonly = "DispatchDetailReport" + new Date().getTime();
		String filename = "DispatchDetailReport" + new Date().getTime() + ".xlsx";
		String filepath = REPORT_FILE_PATH + filename;
		SXSSFWorkbook workbook = null;
		try {
			workbook = new SXSSFWorkbook(1);
			File f = new File(REPORT_FILE_PATH);
			if (!f.exists()) {
				if (f.mkdirs()) {
				} else {
					throw new RuntimeException("Could not create directory");
				}
			}
			SXSSFSheet sheet = workbook.createSheet("Dispatch Detail Report");

			Row row = null;
			Cell cell = null;
			int rowCount = 0;
			int colCount = 0;
			String headings[];
			
			
//			 headings[] = { "Sr. No.", "Ware House", "Team Name", "Team Code", "Month Of Use", "Master STN No.",
//					"Date of STN", "CFA Location", "State", "Master STN Value", "Courier(to CFA)", "Master L.R.No.",
//					"Master L.R.Date", "Cases(CFA)", "Act. WT(CFA)", "Chrg. WT(CFA)", "Requestor/Employee Number",
//					"Employee/Doctor Name", "Region Name", "Designation", "Mobile No.", "DM Name", "Territory Code",
//					"Territory Name", "Challan No.", "Challan Date.", "Challan Value", "Courier(to PSO)",
//					"L.R. No.(to PSO)", "L.R. Date(to PSO)", "Cases(to PSO)", "Act. WT(PSO)", "Chrg. WT(PSO)",
//					"Product Type", "Product Name", "Product Code", "Batch", "Expiry Date", "Dispatch Quantity", "Rate",
//					"Value", "Return Quantity", "Tentative Delivery Date", "Delivery Date", "Recieved By",
//					"Courier Comments", "Remarks", "Emergency / DtD Requestor Name", "Team Code" ,"srt Number","Srt Date","Srt Remark"};
			 
			 

			

				if ("Y".equalsIgnoreCase(cfa_to_stockist_ind)) {
				    headings = new String[] { "Sr. No.", "Ware House", "Master STN No.",
							"Date of STN", "CFA Location", "State", "Master STN Value", 
							"Customer Name","Customer Destination", "Challan No.", "Challan Date.", "Challan Value", "Courier(to PSO)",
							"L.R. No.(to PSO)", "L.R. Date(to PSO)", "Cases",
							"Product Type", "Product Name", "Product Code", "Batch", "Expiry Date", "Dispatch Quantity", "Rate",
							"Value"};
				    
				} else {
				    headings = new String[] { "Sr. No.", "Ware House", "Team Name", "Team Code", "Month Of Use", "Master STN No.",
							"Date of STN", "CFA Location", "State", "Master STN Value", "Courier(to CFA)", "Master L.R.No.",
							"Master L.R.Date", "Cases(CFA)", "Act. WT(CFA)", "Chrg. WT(CFA)", "Requestor/Employee Number",
							"Employee/Doctor Name", "Customer Name","Customer Destination","Region Name", "Designation", "Mobile No.", "DM Name", "Territory Code",
							"Territory Name", "Challan No.", "Challan Date.", "Challan Value", "Courier(to PSO)",
							"L.R. No.(to PSO)", "L.R. Date(to PSO)", "Cases(to PSO)", "Act. WT(PSO)", "Chrg. WT(PSO)",
							"Product Type", "Product Name", "Product Code", "Batch", "Expiry Date", "Dispatch Quantity", "Rate",
							"Value", "Return Quantity", "Tentative Delivery Date", "Delivery Date", "Recieved By",
							"Courier Comments", "Remarks", "Emergency / DtD Requestor Name", "Team Code" ,"srt Number","Srt Date","Srt Remark"};
				}
			
			
			
			
			

			CellStyle headingCellStyle = ReportFormats.heading(workbook);
			CellStyle columnHeadingStyle = ReportFormats.columnHeading(workbook);
			CellStyle cellAlignment = ReportFormats.setCellAlignment(workbook, ALIGN_RIGHT);
			cellAlignment.setDataFormat(workbook.createDataFormat().getFormat("0.00"));

			CellStyle cellAlignment2 = ReportFormats.setCellAlignment(workbook, ALIGN_RIGHT);

			row = sheet.createRow(rowCount);
			cell = row.createCell(colCount);
			sheet.addMergedRegion(new CellRangeAddress(rowCount, rowCount, colCount, colCount + headings.length - 1));
			cell.setCellValue(companyname);
			cell.setCellStyle(headingCellStyle);
			rowCount++;

			row = sheet.createRow(rowCount);
			cell = row.createCell(colCount);
			sheet.addMergedRegion(new CellRangeAddress(rowCount, rowCount, colCount, colCount + headings.length - 1));
			cell.setCellValue("Dispatch Detail Statement");
			cell.setCellStyle(headingCellStyle);
			rowCount++;

			row = sheet.createRow(rowCount);
			cell = row.createCell(colCount);
			sheet.addMergedRegion(new CellRangeAddress(rowCount, rowCount, colCount, colCount + headings.length - 1));
			cell.setCellValue("Period: " + MedicoResources.convertUtilDateToString_DD_MM_YYYY_(bean.getStartDate())
					+ " to " + MedicoResources.convertUtilDateToString_DD_MM_YYYY_(bean.getEndDate()));
			cell.setCellStyle(headingCellStyle);

			rowCount += 1;
			row = sheet.createRow(rowCount);

			for (String heading : headings) {
				cell = ReportFormats.cell(row, colCount, heading, columnHeadingStyle);
				colCount++;
			}

			rowCount += 1;
			colCount = 0;

			row = sheet.createRow(rowCount);
			cell = row.createCell(colCount);
			sheet.addMergedRegion(new CellRangeAddress(rowCount, rowCount, colCount, colCount + 13));
			
			if ("Y".equalsIgnoreCase(cfa_to_stockist_ind)) {
			cell.setCellValue("Packing Slip");
			}else {
				cell.setCellValue("Master Challan details");
			}
			cell.setCellStyle(headingCellStyle);

			cell = row.createCell(colCount + 14);
			sheet.addMergedRegion(new CellRangeAddress(rowCount, rowCount, colCount + 14, colCount + 33));
			cell.setCellValue("Individual Challan details");
			cell.setCellStyle(headingCellStyle);

			cell = row.createCell(colCount + 34);
			sheet.addMergedRegion(new CellRangeAddress(rowCount, rowCount, colCount + 34, colCount + 39));
			cell.setCellValue("Product details");
			cell.setCellStyle(headingCellStyle);

			sheet.groupRow(0, 2);
			sheet.createFreezePane(4, 5);

			rowCount += 1;
			int count = 1;
			Iterator<DispatchDetailReportRevised> dataiterator = list.iterator();
			DispatchDetailReportRevised data = null;
			// for (DispatchDetailReportRevised data : list) {
			while (dataiterator.hasNext()) {
				colCount = 0;
				data = dataiterator.next();
				row = sheet.createRow(rowCount);
				cell = ReportFormats.cell(row, colCount, String.valueOf(count), cellAlignment);
				colCount++;

				cell = ReportFormats.cell(row, colCount, data.getWarehouse(), null);
				colCount++;

				
				if (!"Y".equalsIgnoreCase(cfa_to_stockist_ind)) {
				cell = ReportFormats.cell(row, colCount, data.getDiv_disp_nm(), null);
				colCount++;

				cell = ReportFormats.cell(row, colCount, data.getDiv_map_cd(), null);
				colCount++;

				cell = ReportFormats.cell(row, colCount, data.getMonth_of_use(), null);
				colCount++;
				}
				
				cell = ReportFormats.cell(row, colCount, data.getSumdsp_challan_no(), null);
				colCount++;

				cell = ReportFormats.cell(row, colCount, data.getSumdsp_challan_dt(), null);
				colCount++;

				cell = ReportFormats.cell(row, colCount, data.getSumdsp_destination(), null);
				colCount++;

				cell = ReportFormats.cell(row, colCount, data.getState_name(), null);
				colCount++;
				

				cell = ReportFormats.cellNum(row, colCount,
						data.getSumdsp_total_goods_val() == null ? 0l : data.getSumdsp_total_goods_val().doubleValue(),
						cellAlignment);
				colCount++;
				
				
				
				
				if (!"Y".equalsIgnoreCase(cfa_to_stockist_ind)) {
				cell = ReportFormats.cell(row, colCount, data.getSumdsp_transporter(), null);
				colCount++;

				cell = ReportFormats.cell(row, colCount, data.getSumdsp_lr_no(), null);
				colCount++;

				cell = ReportFormats.cell(row, colCount, data.getSumdsp_lr_dt(), null);
				colCount++;

				cell = ReportFormats.cellNum(row, colCount,
						data.getSumdsp_totcases() == null ? 0l : data.getSumdsp_totcases().doubleValue(),
						cellAlignment2);
				colCount++;

				cell = ReportFormats.cellNum(row, colCount,
						data.getSum_dsp_act_wt() == null ? 0l : data.getSum_dsp_act_wt().doubleValue(), cellAlignment);
				colCount++;

				cell = ReportFormats.cellNum(row, colCount,
						data.getSum_dsp_bill_wt() == null ? 0l : data.getSum_dsp_bill_wt().doubleValue(),
						cellAlignment);
				colCount++;
				

				cell = ReportFormats.cell(row, colCount, data.getDspfstaff_employeeno (), null);
				colCount++;
				
				}
				

				
				/// cust name
				cell = ReportFormats.cell(row, colCount, data.getDspfstaff_displayname(), null);
				colCount++;
				
				//cust des
//				cell = ReportFormats.cell(row, colCount, data.getDspfstaff_destination(), null);
//				colCount++;

				
				
				if (!"Y".equalsIgnoreCase(cfa_to_stockist_ind)) {
				cell = ReportFormats.cell(row, colCount, data.getDspfstaff_displayname(), null);
				colCount++;
				
				cell = ReportFormats.cell(row, colCount, data.getRegion(), null);
				colCount++;

				cell = ReportFormats.cell(row, colCount, data.getDspfstaff_desg(), null);
				colCount++;

				cell = ReportFormats.cell(row, colCount, data.getMobile(), null);
				colCount++;

				cell = ReportFormats.cell(row, colCount, data.getDm_name(), null);
				colCount++;

				cell = ReportFormats.cell(row, colCount, data.getTerr_code(), null);
				colCount++;

				// cell = ReportFormats.cell(row, colCount, data.getPosition(), null);
				// colCount++;

				cell = ReportFormats.cell(row, colCount, data.getTerr_name(), null);
				colCount++;

				
			}
				cell = ReportFormats.cell(row, colCount, data.getDsp_challan_no(), null);
				colCount++;

				cell = ReportFormats.cell(row, colCount, data.getDsp_challan_dt(), null);
				colCount++;

				cell = ReportFormats.cellNum(row, colCount,
						data.getDsp_total_goods_val() == null ? 0l : data.getDsp_total_goods_val().doubleValue(),
						cellAlignment);
				colCount++;

				cell = ReportFormats.cell(row, colCount, data.getDsp_transporter(), null);
				colCount++;

				cell = ReportFormats.cell(row, colCount, data.getDsp_lr_no(), null);
				colCount++;

				cell = ReportFormats.cell(row, colCount, data.getDsp_lr_dt(), null);
				colCount++;

				cell = ReportFormats.cellNum(row, colCount,
						data.getDsp_cases() == null ? 0l : data.getDsp_cases().doubleValue(), cellAlignment2);
				colCount++;
				
				
				
				if (!"Y".equalsIgnoreCase(cfa_to_stockist_ind)) {

				cell = ReportFormats.cellNum(row, colCount,
						data.getDsp_act_wt() == null ? 0l : data.getDsp_act_wt().doubleValue(), cellAlignment);
				colCount++;

				cell = ReportFormats.cellNum(row, colCount,
						data.getDsp_bill_wt() == null ? 0l : data.getDsp_bill_wt().doubleValue(), cellAlignment);
				colCount++;
				
				}

				cell = ReportFormats.cell(row, colCount, data.getSmp_prod_type(), null);
				colCount++;

				cell = ReportFormats.cell(row, colCount, data.getSmp_prod_name(), null);
				colCount++;

				cell = ReportFormats.cell(row, colCount, data.getSmp_prod_cd(), null);
				colCount++;

				cell = ReportFormats.cell(row, colCount, data.getBatch_no(), null);
				colCount++;

				cell = ReportFormats.cell(row, colCount, data.getBatch_expiry_dt(), null);
				colCount++;

				cell = ReportFormats.cellNum(row, colCount,
						data.getDspdtl_disp_qty() == null ? 0l : data.getDspdtl_disp_qty().doubleValue(),
						cellAlignment);
				colCount++;

				cell = ReportFormats.cellNum(row, colCount,
						data.getDspdtl_rate() == null ? 0l : data.getDspdtl_rate().doubleValue(), cellAlignment);
				colCount++;

				cell = ReportFormats.cellNum(row, colCount,
						data.getValue() == null ? 0l : data.getValue().doubleValue(), cellAlignment);
				colCount++;

				
				if (!"Y".equalsIgnoreCase(cfa_to_stockist_ind)) {
				cell = ReportFormats.cellNum(row, colCount,
						data.getReturn_qty() == null ? 0l : data.getReturn_qty().doubleValue(), cellAlignment);
				colCount++;

				
				cell = ReportFormats.cell(row, colCount, data.getTentative_delivery_date(), null);
				colCount++;
				cell = ReportFormats.cell(row, colCount, data.getActual_delivery_date(), null);
				colCount++;
				}

			

				
				if (!"Y".equalsIgnoreCase(cfa_to_stockist_ind)) {
				
				cell = ReportFormats.cell(row, colCount, data.getRecieved_by(), null);
				colCount++;

				cell = ReportFormats.cell(row, colCount, data.getCourier_comment(), null);
				colCount++;
				
				cell = ReportFormats.cell(row, colCount, data.getEmer_dtd_supplied_to(), null);
				colCount++;

				}

			

		
				
				if (!"Y".equalsIgnoreCase(cfa_to_stockist_ind)) {
					
					cell = ReportFormats.cell(row, colCount, data.getRemarks(), null);
					colCount++;
					
					cell = ReportFormats.cell(row, colCount, data.getTeam_code(), null);
					colCount++;
					
				cell = ReportFormats.cell(row, colCount, data.getSrt_number(), null);
				colCount++;
				
				cell = ReportFormats.cell(row, colCount, data.getSrt_date().trim().equals("1900-01-01")?"":data.getSrt_date(), null);
				colCount++;
				
				cell = ReportFormats.cell(row, colCount, data.getSrt_remark(), null);
				colCount++;
				}
				

				rowCount++;
				count++;
				dataiterator.remove();

			}
			FileOutputStream fileOutputStream = new FileOutputStream(filepath);
			workbook.write(fileOutputStream);

			filename=usermasterservice.generateExcellock(filepath, filename,empId,".xlsx");

		} catch (Throwable e) {
			// TODO Auto-generated catch block
			// System.out.println("Error Occurred :" +e.getMessage());// uncomment asneeded --;
		} finally {
			if (workbook != null) {
				workbook.close();
				workbook.dispose();
			}
		}
		return filename;
	}
	
	
	
	@Override
	public String generateDispatchDetReportRbmdm(List<DispatchDetailReportRbmdm> list, ReportBean bean,
			String companyname) throws Exception {
		String filename = "DispatchDetailReport_RMDM" + new Date().getTime() + ".xlsx";
		String filepath = REPORT_FILE_PATH + filename;
		XSSFWorkbook workbook = null;
		try {
			workbook = new XSSFWorkbook();
			File f = new File(REPORT_FILE_PATH);
			if (!f.exists()) {
				if (f.mkdirs()) {
				} else {
					throw new RuntimeException("Could not create directory");
				}
			}
			XSSFSheet sheet = workbook.createSheet("Dispatch Detail Report");
			
			XSSFRow row = null;
			XSSFCell cell = null;
			int rowCount = 0;
			int colCount = 0;
			
			String headings[] = { "Sr. No.", "Ware House","Team Name" , "Team Code","Month Of Use","Master STN No.","Date of STN","CFA Location",
					"State","Master STN Value","Courier(to CFA)","Master L.R.No.","Master L.R.Date","Cases(CFA)","Act. WT(CFA)","Chrg. WT(CFA)","Employee No.",
					"Employee/Doctor Name","Region Name","Designation","Mobile No.","DM Name","Territory Code",
					"Territory Name","Challan No.","Challan Date.","Challan Value","Courier(to PSO)","L.R. No.(to PSO)","L.R. Date(to PSO)","Cases(to PSO)",
					"Act. WT(PSO)","Chrg. WT(PSO)","Product Type","Product Name","Product Code","Batch","Expiry Date",
					"Dispatch Quantity","Rate","Value","Return Quantity","Tentative Delivery Date","Delivery Date","Recieved By","Courier Comments",
					"Remarks","Emergency / DtD Requestor Name"};
			
			XSSFCellStyle headingCellStyle = ReportFormats.heading(workbook);
			XSSFCellStyle columnHeadingStyle = ReportFormats.columnHeading(workbook);
			XSSFCellStyle cellAlignment = ReportFormats.setCellAlignment(workbook, ALIGN_RIGHT);
			cellAlignment.setDataFormat(workbook.createDataFormat().getFormat("0.00"));
			
			XSSFCellStyle cellAlignment2 = ReportFormats.setCellAlignment(workbook, ALIGN_RIGHT);
			
			row = sheet.createRow(rowCount);
			cell = row.createCell(colCount);
			sheet.addMergedRegion(new CellRangeAddress(rowCount, rowCount, colCount, colCount + headings.length - 1));
			cell.setCellValue(companyname);
			cell.setCellStyle(headingCellStyle);
			rowCount++;
			
			row = sheet.createRow(rowCount);
			cell = row.createCell(colCount);
			sheet.addMergedRegion(new CellRangeAddress(rowCount, rowCount, colCount, colCount + headings.length - 1));
			cell.setCellValue("Dispatch Detail Statement");
			cell.setCellStyle(headingCellStyle);
			rowCount++;

			row = sheet.createRow(rowCount);
			cell = row.createCell(colCount);
			sheet.addMergedRegion(new CellRangeAddress(rowCount, rowCount, colCount, colCount + headings.length - 1));
			cell.setCellValue("Period: "+MedicoResources.convertUtilDateToString_DD_MM_YYYY_(bean.getStartDate())+" to "+MedicoResources.convertUtilDateToString_DD_MM_YYYY_(bean.getEndDate()));
			cell.setCellStyle(headingCellStyle);
			
			rowCount += 1;
			row = sheet.createRow(rowCount);
			
			for (String heading : headings) {
				cell = ReportFormats.cell(row, colCount, heading, columnHeadingStyle);
				colCount++;
			}
			
			rowCount += 1;
			colCount=0;
			
			row = sheet.createRow(rowCount);
			cell = row.createCell(colCount);
			sheet.addMergedRegion(new CellRangeAddress(rowCount, rowCount, colCount, colCount + 13));
			cell.setCellValue("Master Challan details");
			cell.setCellStyle(headingCellStyle);
			
			cell = row.createCell(colCount+14);
			sheet.addMergedRegion(new CellRangeAddress(rowCount, rowCount, colCount+14, colCount + 33));
			cell.setCellValue("Individual Challan details");
			cell.setCellStyle(headingCellStyle);
			
			cell = row.createCell(colCount+34);
			sheet.addMergedRegion(new CellRangeAddress(rowCount, rowCount, colCount+34, colCount + 47));
			cell.setCellValue("Product details");
			cell.setCellStyle(headingCellStyle);
			
			sheet.groupRow(0, 2);
			sheet.createFreezePane(7, 5);
			
			rowCount += 1;
			int count=1;
			for (DispatchDetailReportRbmdm data : list) {
				colCount = 0;
				row = sheet.createRow(rowCount);
				cell = ReportFormats.cell(row, colCount, String.valueOf(count), cellAlignment);
				colCount++;
				
				cell = ReportFormats.cell(row, colCount, data.getWarehouse(), null);
				colCount++;
				
				cell = ReportFormats.cell(row, colCount, data.getDiv_disp_nm(), null);
				colCount++;
				
				cell = ReportFormats.cell(row, colCount, data.getDiv_map_cd(), null);
				colCount++;
				
				cell = ReportFormats.cell(row, colCount, data.getMonth_of_use(), null);
				colCount++;
				
				cell = ReportFormats.cell(row, colCount, data.getSumdsp_challan_no(), null);
				colCount++;
				
				cell = ReportFormats.cell(row, colCount, data.getSumdsp_challan_dt(), null);
				colCount++;
				
				cell = ReportFormats.cell(row, colCount, data.getSumdsp_destination(), null);
				colCount++;
				
				cell = ReportFormats.cell(row, colCount, data.getState_name(), null);
				colCount++;
				
				cell = ReportFormats.cellNum(row, colCount, data.getSumdsp_total_goods_val() == null ? 0l : data.getSumdsp_total_goods_val().doubleValue(), cellAlignment);
				colCount++;
				
				cell = ReportFormats.cell(row, colCount, data.getSumdsp_transporter(), null);
				colCount++;
				
				cell = ReportFormats.cell(row, colCount, data.getSumdsp_lr_no(), null);
				colCount++;
				
				cell = ReportFormats.cell(row, colCount, data.getSumdsp_lr_dt(), null);
				colCount++;
				
				cell = ReportFormats.cellNum(row, colCount, data.getSumdsp_totcases() == null ? 0l : data.getSumdsp_totcases().doubleValue(), cellAlignment2);
				colCount++;
				
				cell = ReportFormats.cellNum(row, colCount, data.getSum_dsp_act_wt() == null ? 0l : data.getSum_dsp_act_wt().doubleValue(), cellAlignment);
				colCount++;
				
				cell = ReportFormats.cellNum(row, colCount, data.getSum_dsp_bill_wt() == null ? 0l : data.getSum_dsp_bill_wt().doubleValue(), cellAlignment);
				colCount++;
				
				cell = ReportFormats.cell(row, colCount, data.getDspfstaff_employeeno(), null);
				colCount++;
				
				cell = ReportFormats.cell(row, colCount, data.getDspfstaff_displayname(), null);
				colCount++;
				
				cell = ReportFormats.cell(row, colCount, data.getRegion(), null);
				colCount++;
				
				cell = ReportFormats.cell(row, colCount, data.getDspfstaff_desg(), null);
				colCount++;
				
				cell = ReportFormats.cell(row, colCount, data.getMobile(), null);
				colCount++;
			
				
				cell = ReportFormats.cell(row, colCount, data.getDm_name(), null);
				colCount++;
				
				cell = ReportFormats.cell(row, colCount, data.getTerr_code(), null);
				colCount++;
				
				//cell = ReportFormats.cell(row, colCount, data.getPosition(), null);
				//colCount++;
				
				cell = ReportFormats.cell(row, colCount, data.getTerr_name(), null);
				colCount++;
				
				cell = ReportFormats.cell(row, colCount, data.getDsp_challan_no(), null);
				colCount++;
				
				cell = ReportFormats.cell(row, colCount, data.getDsp_challan_dt(), null);
				colCount++;
				
				cell = ReportFormats.cellNum(row, colCount, data.getDsp_total_goods_val() == null ? 0l : data.getDsp_total_goods_val().doubleValue(), cellAlignment);
				colCount++;
				
				cell = ReportFormats.cell(row, colCount, data.getDsp_transporter(), null);
				colCount++;
				
				cell = ReportFormats.cell(row, colCount, data.getDsp_lr_no(), null);
				colCount++;
				
				cell = ReportFormats.cell(row, colCount, data.getDsp_lr_dt(), null);
				colCount++;
				
				cell = ReportFormats.cellNum(row, colCount, data.getDsp_cases() == null ? 0l : data.getDsp_cases().doubleValue(), cellAlignment2);
				colCount++;
				
				cell = ReportFormats.cellNum(row, colCount, data.getDsp_act_wt() == null ? 0l : data.getDsp_act_wt().doubleValue(), cellAlignment);
				colCount++;
				
				cell = ReportFormats.cellNum(row, colCount, data.getDsp_bill_wt() == null ? 0l : data.getDsp_bill_wt().doubleValue(), cellAlignment);
				colCount++;
				
				cell = ReportFormats.cell(row, colCount, data.getSmp_prod_type(), null);
				colCount++;
				
				cell = ReportFormats.cell(row, colCount, data.getSmp_prod_name(), null);
				colCount++;
				
				cell = ReportFormats.cell(row, colCount, data.getSmp_prod_cd(), null);
				colCount++;
				
				cell = ReportFormats.cell(row, colCount, data.getBatch_no(), null);
				colCount++;
				
				cell = ReportFormats.cell(row, colCount, data.getBatch_expiry_dt(), null);
				colCount++;
				
				cell = ReportFormats.cellNum(row, colCount, data.getDspdtl_disp_qty() == null ? 0l : data.getDspdtl_disp_qty().doubleValue(), cellAlignment);
				colCount++;

				cell = ReportFormats.cellNum(row, colCount, data.getDspdtl_rate() == null ? 0l : data.getDspdtl_rate().doubleValue(), cellAlignment);
				colCount++;
				
				cell = ReportFormats.cellNum(row, colCount, data.getValue() == null ? 0l : data.getValue().doubleValue(), cellAlignment);
				colCount++;
				
				cell = ReportFormats.cellNum(row, colCount, data.getReturn_qty() == null ? 0l : data.getReturn_qty().doubleValue(), cellAlignment);
				colCount++;
				
				
				
				cell = ReportFormats.cell(row, colCount, data.getTentative_delivery_date(), null);
				colCount++;
				
				cell = ReportFormats.cell(row, colCount, data.getActual_delivery_date(), null);
				colCount++;
				
				cell = ReportFormats.cell(row, colCount, data.getRecieved_by(), null);
				colCount++;
				
				cell = ReportFormats.cell(row, colCount, data.getCourier_comment(), null);
				colCount++;
				
				cell = ReportFormats.cell(row, colCount, data.getRemarks(), null);
				colCount++;
				
				cell = ReportFormats.cell(row, colCount, data.getEmer_dtd_supplied_to(), null);
				colCount++;
				
				rowCount++;
				count++;
			}
			FileOutputStream fileOutputStream = new FileOutputStream(filepath);
			workbook.write(fileOutputStream);
		} finally {
			if (workbook != null) { workbook.close(); }
		}
		return filename;
	}

	@Override
	public String generateDispatchDetailGstReport(List<DispatchDetailGstReport> list, ReportBean bean,
			String companyname,String empId) throws Exception {
		// TODO Auto-generated method stub
		String filename = "DipatchDetailGstReport" + new Date().getTime()+ ".xlsx";
		String filepath = REPORT_FILE_PATH + filename ;
		XSSFWorkbook workbook = null;
		try {
			workbook = new XSSFWorkbook();
			File f = new File(REPORT_FILE_PATH);
			if (!f.exists()) {
				if (f.mkdirs()) {
				} else {
					throw new RuntimeException("Could not create directory");
				}
			}
			XSSFSheet sheet = workbook.createSheet("Dispatch Detail Report");

			XSSFRow row = null;
			XSSFCell cell = null;
			int rowCount = 0;
			int colCount = 0;

			String headings[] = { "Warehouse", "Division", "Team Code", "Dispatch Invoice No.", "Invoice Date",
					"Destination", "Transporter", "LR No.", "LR Date", "Total Cases", "Product Code", "Product Name",
					"Product Type", "Batch No.", "Expiry Date", "Quantity", "Rate", "Goods Value", "SGST", "CGST",
					"IGST", "HSN CODE", "GSTIN NO", "State Name", "State Code", "Net Amount" };

			XSSFCellStyle headingCellStyle = ReportFormats.heading(workbook);
			XSSFCellStyle columnHeadingStyle = ReportFormats.columnHeading(workbook);
			XSSFCellStyle columnHeadingStyle1 = ReportFormats.heading1(workbook);
			columnHeadingStyle1.setDataFormat(workbook.createDataFormat().getFormat("0.00"));

			XSSFCellStyle cellAlignment2 = ReportFormats.setCellAlignment(workbook, ALIGN_RIGHT);
			cellAlignment2.setDataFormat(workbook.createDataFormat().getFormat("0.00"));

			XSSFCellStyle columnHeadingStyle2 = ReportFormats.heading1(workbook);
			columnHeadingStyle2.setFillForegroundColor(IndexedColors.AQUA.index);
			columnHeadingStyle2.setFillPattern(FillPatternType.SOLID_FOREGROUND);
			XSSFFont headerFont = workbook.createFont();
			headerFont.setFontHeightInPoints((short) 12);
			headerFont.setBold(true);
			columnHeadingStyle2.setFont(headerFont);
			columnHeadingStyle2.setAlignment(HorizontalAlignment.RIGHT);
			columnHeadingStyle2.setDataFormat(workbook.createDataFormat().getFormat("0.00"));

			XSSFCellStyle totalcolumnHeadingStyle1 = ReportFormats.columnHeading(workbook);
			XSSFFont headerFont1 = workbook.createFont();
			headerFont1.setFontHeightInPoints((short) 12);
			headerFont1.setBold(true);
			totalcolumnHeadingStyle1.setFont(headerFont1);
			totalcolumnHeadingStyle1.setAlignment(HorizontalAlignment.RIGHT);
			totalcolumnHeadingStyle1.setDataFormat(workbook.createDataFormat().getFormat("0.00"));

			row = sheet.createRow(rowCount);
			cell = row.createCell(colCount);
			sheet.addMergedRegion(new CellRangeAddress(rowCount, rowCount, colCount, colCount + 28));
			cell.setCellValue(companyname);
			cell.setCellStyle(headingCellStyle);
			rowCount++;

			row = sheet.createRow(rowCount);
			cell = row.createCell(colCount);
			sheet.addMergedRegion(new CellRangeAddress(rowCount, rowCount, colCount, colCount + 28));
			cell.setCellValue("Dispatch Invoices Detail Report - GST");
			cell.setCellStyle(headingCellStyle);
			rowCount++;

			row = sheet.createRow(rowCount);
			cell = row.createCell(colCount);
			sheet.addMergedRegion(new CellRangeAddress(rowCount, rowCount, colCount, colCount + 28));
			cell.setCellValue("Period: " + MedicoResources.convertUtilDateToString_DD_MM_YYYY_(bean.getStartDate())
					+ " to " + MedicoResources.convertUtilDateToString_DD_MM_YYYY_(bean.getEndDate()));
			cell.setCellStyle(headingCellStyle);
			rowCount++;

			row = sheet.createRow(rowCount);
			for (String heading : headings) {
				cell = ReportFormats.cell(row, colCount, heading, columnHeadingStyle);
				if (heading.equals("SGST") || heading.equals("CGST") || heading.equals("IGST")) {
					sheet.addMergedRegion(new CellRangeAddress(rowCount, rowCount, colCount, colCount + 1));
					colCount += 1;
				} else {
					sheet.addMergedRegion(new CellRangeAddress(rowCount, rowCount + 1, colCount, colCount));
				}
				colCount++;
			}
			rowCount++;
			colCount = 18;
			row = sheet.createRow(rowCount);
			cell = ReportFormats.cell(row, colCount, "%", columnHeadingStyle);
			colCount++;

			cell = ReportFormats.cell(row, colCount, "Amount", columnHeadingStyle);
			colCount++;

			cell = ReportFormats.cell(row, colCount, "%", columnHeadingStyle);
			colCount++;

			cell = ReportFormats.cell(row, colCount, "Amount", columnHeadingStyle);
			colCount++;

			cell = ReportFormats.cell(row, colCount, "%", columnHeadingStyle);
			colCount++;

			cell = ReportFormats.cell(row, colCount, "Amount", columnHeadingStyle);
			colCount++;

			rowCount++;
			colCount = 0;
			BigDecimal netval = BigDecimal.ZERO;
			String new_division = "";
			String old_division = "old";

			BigDecimal NetValtotal = BigDecimal.ZERO;
			BigDecimal goodsValtotal = BigDecimal.ZERO;
			BigDecimal sgstValtotal = BigDecimal.ZERO;
			BigDecimal cgstValtotal = BigDecimal.ZERO;
			BigDecimal igstValtotal = BigDecimal.ZERO;

			BigDecimal GrandNetValtotal = BigDecimal.ZERO;
			BigDecimal GrandgoodsValtotal = BigDecimal.ZERO;
			BigDecimal GrandsgstValtotal = BigDecimal.ZERO;
			BigDecimal GrandcgstValtotal = BigDecimal.ZERO;
			BigDecimal GrandigstValtotal = BigDecimal.ZERO;
			for (DispatchDetailGstReport data : list) {
				colCount = 0;
				new_division = data.getDiv_disp_nm();
				if (!new_division.equals(old_division)) {
					if (!new_division.equals(old_division) && !old_division.equals("old")) {
						row = sheet.createRow(rowCount);
						cell = ReportFormats.cell(row, colCount, "Division Total:", totalcolumnHeadingStyle1);
						sheet.addMergedRegion(new CellRangeAddress(rowCount, rowCount, colCount, colCount + 16));
						colCount += 16;
						colCount++;
						cell = ReportFormats.cellNum(row, colCount,
								goodsValtotal.setScale(2, RoundingMode.HALF_UP).doubleValue(),
								totalcolumnHeadingStyle1);
						colCount++;

						cell = ReportFormats.cell(row, colCount, "", totalcolumnHeadingStyle1);
						colCount++;
						cell = ReportFormats.cellNum(row, colCount,
								sgstValtotal.setScale(2, RoundingMode.HALF_UP).doubleValue(), totalcolumnHeadingStyle1);
						colCount++;

						cell = ReportFormats.cell(row, colCount, "", totalcolumnHeadingStyle1);
						colCount++;
						cell = ReportFormats.cellNum(row, colCount,
								cgstValtotal.setScale(2, RoundingMode.HALF_UP).doubleValue(), totalcolumnHeadingStyle1);
						colCount++;

						cell = ReportFormats.cell(row, colCount, "", totalcolumnHeadingStyle1);
						colCount++;
						cell = ReportFormats.cellNum(row, colCount,
								igstValtotal.setScale(2, RoundingMode.HALF_UP).doubleValue(), totalcolumnHeadingStyle1);
						colCount++;

						cell = ReportFormats.cell(row, colCount, "", totalcolumnHeadingStyle1);
						sheet.addMergedRegion(new CellRangeAddress(rowCount, rowCount, colCount, colCount + 3));
						colCount += 3;
						colCount++;

						cell = ReportFormats.cellNum(row, colCount,
								NetValtotal.setScale(2, RoundingMode.HALF_UP).doubleValue(), totalcolumnHeadingStyle1);
						colCount = 0;
						rowCount++;

						GrandNetValtotal = GrandNetValtotal.add(NetValtotal);
						GrandgoodsValtotal = GrandgoodsValtotal.add(goodsValtotal);
						GrandsgstValtotal = GrandsgstValtotal.add(sgstValtotal);
						GrandcgstValtotal = GrandcgstValtotal.add(cgstValtotal);
						GrandigstValtotal = GrandigstValtotal.add(igstValtotal);

						NetValtotal = BigDecimal.ZERO;
						goodsValtotal = BigDecimal.ZERO;
						sgstValtotal = BigDecimal.ZERO;
						cgstValtotal = BigDecimal.ZERO;
						igstValtotal = BigDecimal.ZERO;

					}
					row = sheet.createRow(rowCount);
					cell = ReportFormats.cell(row, colCount, data.getDiv_disp_nm(), columnHeadingStyle1);
					sheet.addMergedRegion(new CellRangeAddress(rowCount, rowCount, colCount, colCount + 28));
					rowCount++;
				}
				netval = data.getValue()
						.add(data.getDsgst_bill_amt().add(data.getDcgst_bill_amt().add(data.getDigst_bill_amt())));
				NetValtotal = NetValtotal.add(netval);
				goodsValtotal = goodsValtotal.add(data.getValue());
				sgstValtotal = sgstValtotal.add(data.getDsgst_bill_amt());
				cgstValtotal = cgstValtotal.add(data.getDcgst_bill_amt());
				igstValtotal = igstValtotal.add(data.getDigst_bill_amt());
				colCount = 0;
				row = sheet.createRow(rowCount);
				cell = ReportFormats.cell(row, colCount, data.getWarehouse(), null);
				colCount++;

				cell = ReportFormats.cell(row, colCount, data.getDiv_disp_nm(), null);
				colCount++;

				cell = ReportFormats.cell(row, colCount, data.getDiv_map_cd(), null);
				colCount++;

				cell = ReportFormats.cell(row, colCount, data.getDsp_challan_no(), null);
				colCount++;

				cell = ReportFormats.cell(row, colCount, data.getSumdsp_challan_dt(), null);
				colCount++;

				cell = ReportFormats.cell(row, colCount, data.getSumdsp_destination(), null);
				colCount++;

				cell = ReportFormats.cell(row, colCount, data.getDsp_transporter(), null);
				colCount++;

				cell = ReportFormats.cell(row, colCount, data.getDsp_lr_no(), null);
				colCount++;

				cell = ReportFormats.cell(row, colCount, data.getSumdsp_lr_dt(), null);
				colCount++;

				cell = ReportFormats.cell(row, colCount, data.getDsp_cases(), null);
				colCount++;

				cell = ReportFormats.cell(row, colCount, data.getSmp_prod_cd(), null);
				colCount++;

				cell = ReportFormats.cell(row, colCount, data.getSmp_prod_name(), null);
				colCount++;

				cell = ReportFormats.cell(row, colCount, data.getSmp_prod_type(), null);
				colCount++;

				cell = ReportFormats.cell(row, colCount, data.getBatch_no(), null);
				colCount++;

				cell = ReportFormats.cell(row, colCount, data.getBatch_expiry_dt(), null);
				colCount++;

				cell = ReportFormats.cellNum(row, colCount, data.getDsp_dtldisp_qty().doubleValue(), null);
				colCount++;

				cell = ReportFormats.cellNum(row, colCount, data.getDspdtl_rate().doubleValue(), cellAlignment2);
				colCount++;

				cell = ReportFormats.cellNum(row, colCount,
						data.getValue().setScale(2, RoundingMode.HALF_UP).doubleValue(), cellAlignment2);
				colCount++;

				cell = ReportFormats.cellNum(row, colCount,
						data.getDsgst_rate().setScale(2, RoundingMode.HALF_UP).doubleValue(), cellAlignment2);
				colCount++;

				cell = ReportFormats.cellNum(row, colCount,
						data.getDsgst_bill_amt().setScale(2, RoundingMode.HALF_UP).doubleValue(), cellAlignment2);
				colCount++;

				cell = ReportFormats.cellNum(row, colCount,
						data.getDcgst_rate().setScale(2, RoundingMode.HALF_UP).doubleValue(), cellAlignment2);
				colCount++;

				cell = ReportFormats.cellNum(row, colCount,
						data.getDcgst_bill_amt().setScale(2, RoundingMode.HALF_UP).doubleValue(), cellAlignment2);
				colCount++;

				cell = ReportFormats.cellNum(row, colCount,
						data.getDigst_rate().setScale(2, RoundingMode.HALF_UP).doubleValue(), cellAlignment2);
				colCount++;

				cell = ReportFormats.cellNum(row, colCount,
						data.getDigst_bill_amt().setScale(2, RoundingMode.HALF_UP).doubleValue(), cellAlignment2);
				colCount++;

				cell = ReportFormats.cell(row, colCount, data.getHsn_code(), null);
				colCount++;

				cell = ReportFormats.cell(row, colCount, data.getGst_reg_no(), null);
				colCount++;

				cell = ReportFormats.cell(row, colCount, data.getStatename(), null);
				colCount++;

				cell = ReportFormats.cell(row, colCount, data.getState_code(), null);
				colCount++;

				cell = ReportFormats.cellNum(row, colCount, netval.setScale(2, RoundingMode.HALF_UP).doubleValue(),
						cellAlignment2);
				colCount++;

				old_division = new_division;
				rowCount++;
			}
			colCount = 0;

			row = sheet.createRow(rowCount);
			cell = ReportFormats.cell(row, colCount, "Division Total:", totalcolumnHeadingStyle1);
			sheet.addMergedRegion(new CellRangeAddress(rowCount, rowCount, colCount, colCount + 16));
			colCount += 16;
			colCount++;
			cell = ReportFormats.cellNum(row, colCount, goodsValtotal.setScale(2, RoundingMode.HALF_UP).doubleValue(),
					totalcolumnHeadingStyle1);
			colCount++;

			cell = ReportFormats.cell(row, colCount, "", totalcolumnHeadingStyle1);
			colCount++;
			cell = ReportFormats.cellNum(row, colCount, sgstValtotal.setScale(2, RoundingMode.HALF_UP).doubleValue(),
					totalcolumnHeadingStyle1);
			colCount++;

			cell = ReportFormats.cell(row, colCount, "", totalcolumnHeadingStyle1);
			colCount++;
			cell = ReportFormats.cellNum(row, colCount, cgstValtotal.setScale(2, RoundingMode.HALF_UP).doubleValue(),
					totalcolumnHeadingStyle1);
			colCount++;

			cell = ReportFormats.cell(row, colCount, "", totalcolumnHeadingStyle1);
			colCount++;
			cell = ReportFormats.cellNum(row, colCount, igstValtotal.setScale(2, RoundingMode.HALF_UP).doubleValue(),
					totalcolumnHeadingStyle1);
			colCount++;

			cell = ReportFormats.cell(row, colCount, "", totalcolumnHeadingStyle1);
			sheet.addMergedRegion(new CellRangeAddress(rowCount, rowCount, colCount, colCount + 3));
			colCount += 3;
			colCount++;

			cell = ReportFormats.cellNum(row, colCount, NetValtotal.setScale(2, RoundingMode.HALF_UP).doubleValue(),
					totalcolumnHeadingStyle1);
			colCount = 0;
			rowCount++;

			// GranTotal
			GrandNetValtotal = GrandNetValtotal.add(NetValtotal);
			GrandgoodsValtotal = GrandgoodsValtotal.add(goodsValtotal);
			GrandsgstValtotal = GrandsgstValtotal.add(sgstValtotal);
			GrandcgstValtotal = GrandcgstValtotal.add(cgstValtotal);
			GrandigstValtotal = GrandigstValtotal.add(igstValtotal);

			colCount = 0;
			row = sheet.createRow(rowCount);
			cell = ReportFormats.cell(row, colCount, "Grand Total:", columnHeadingStyle2);
			sheet.addMergedRegion(new CellRangeAddress(rowCount, rowCount, colCount, colCount + 16));
			colCount += 16;
			colCount++;
			cell = ReportFormats.cellNum(row, colCount,
					GrandgoodsValtotal.setScale(2, RoundingMode.HALF_UP).doubleValue(), columnHeadingStyle2);
			colCount++;

			cell = ReportFormats.cell(row, colCount, "", columnHeadingStyle2);
			colCount++;
			cell = ReportFormats.cellNum(row, colCount,
					GrandsgstValtotal.setScale(2, RoundingMode.HALF_UP).doubleValue(), columnHeadingStyle2);
			colCount++;

			cell = ReportFormats.cell(row, colCount, "", columnHeadingStyle2);
			colCount++;
			cell = ReportFormats.cellNum(row, colCount,
					GrandcgstValtotal.setScale(2, RoundingMode.HALF_UP).doubleValue(), columnHeadingStyle2);
			colCount++;

			cell = ReportFormats.cell(row, colCount, "", columnHeadingStyle2);
			colCount++;
			cell = ReportFormats.cellNum(row, colCount,
					GrandigstValtotal.setScale(2, RoundingMode.HALF_UP).doubleValue(), columnHeadingStyle2);
			colCount++;

			cell = ReportFormats.cell(row, colCount, "", columnHeadingStyle2);
			sheet.addMergedRegion(new CellRangeAddress(rowCount, rowCount, colCount, colCount + 3));
			colCount += 3;
			colCount++;

			cell = ReportFormats.cellNum(row, colCount,
					GrandNetValtotal.setScale(2, RoundingMode.HALF_UP).doubleValue(), columnHeadingStyle2);
			colCount = 0;

			FileOutputStream fileOutputStream = new FileOutputStream(filepath);
			workbook.write(fileOutputStream);
			filename=usermasterservice.generateExcellock(filepath, filename,empId,".xlsx");

		} catch (Exception e) {
			// TODO: handle exception
			throw e;
		} finally {
			if (workbook != null) {
				workbook.close();
			}
		}
		return filename;
	}

	@Override
	public String generateSummaryChallanReportEInv(List<SummaryChallanReportEInvoice> list, ReportBean bean,
			String companyname) throws Exception {
		String filename = "SummaryChallanReportEInv" + new Date().getTime() + ".xlsx";
		String filepath = REPORT_FILE_PATH + filename;
		XSSFWorkbook workbook = null;
		try {
			workbook = new XSSFWorkbook();
			File f = new File(REPORT_FILE_PATH);
			if (!f.exists()) {
				if (f.mkdirs()) {
				} else {
					throw new RuntimeException("Could not create directory");
				}
			}
			XSSFSheet sheet = workbook.createSheet("Summary Challans Detail Report - GST");
			
			XSSFRow row = null;
			XSSFCell cell = null;
			int rowCount = 0;
			int colCount = 0;
			int limit = 30;
			
			String headings[] = {"Ware House","Division","Team Code","Summary Challan No.","Challan Date","Destination",
			"Transporter","LR No.","LR Date","Total Cases","Product Code","Product Name","Product Name","Batch No.",
			"Expiry Date","Quantity","Rate","Goods Value","SGST","CGST","IGST","HSN CODE","GSTIN NO","State Name",
			"State Code","Net Amount","IRN Number","EInvoice Number"};
			
			XSSFCellStyle headingCellStyle = ReportFormats.heading(workbook);
			XSSFCellStyle columnHeadingStyle = ReportFormats.columnHeading(workbook);
			XSSFCellStyle cellAlignment = ReportFormats.setCellAlignment(workbook, ALIGN_RIGHT);
			XSSFCellStyle heading1CellStyle = ReportFormats.heading1(workbook);

			row = sheet.createRow(rowCount);
			cell = row.createCell(colCount);
			sheet.addMergedRegion(new CellRangeAddress(rowCount, rowCount, colCount, ((colCount + headings.length - 1))+3));
			cell.setCellValue(companyname);
			cell.setCellStyle(headingCellStyle);
			rowCount++;
			
			row = sheet.createRow(rowCount);
			cell = row.createCell(colCount);
			sheet.addMergedRegion(new CellRangeAddress(rowCount, rowCount, colCount, ((colCount + headings.length - 1))+3));
			cell.setCellValue("Summary Challans Detail Report - GST");
			cell.setCellStyle(headingCellStyle);
			rowCount++;

			row = sheet.createRow(rowCount);
			cell = row.createCell(colCount);
			sheet.addMergedRegion(new CellRangeAddress(rowCount, rowCount, colCount, ((colCount + headings.length - 1))+3));
			cell.setCellValue("Period: "+MedicoResources.convertUtilDateToString_DD_MM_YYYY_(bean.getStartDate())+" to "+MedicoResources.convertUtilDateToString_DD_MM_YYYY_(bean.getEndDate()));
			cell.setCellStyle(headingCellStyle);
			
			rowCount += 1;
			row = sheet.createRow(rowCount);
			
			for(int i=0;i<(headings.length + 3);i++) {
				cell = ReportFormats.cell(row, i, "", columnHeadingStyle);
			}
			
			
			
			int count1=0;
			for (String heading : headings) {
				if(count1 == 18 || count1 == 19 || count1 == 20) {
					cell = ReportFormats.cell(row, colCount, heading, columnHeadingStyle);
					sheet.addMergedRegion(new CellRangeAddress(rowCount, rowCount, colCount, colCount + 1));
					colCount+=2;
				} else {
					cell = ReportFormats.cell(row, colCount, heading, columnHeadingStyle);
					sheet.addMergedRegion(new CellRangeAddress(rowCount, rowCount+1, colCount, colCount));
					colCount++;
				}
				count1++;
			}
			
			rowCount += 1;
			row = sheet.createRow(rowCount);
			
			for(int i=0;i<(headings.length + 3);i++) {
				cell = ReportFormats.cell(row, i, "", columnHeadingStyle);
			}
			
			colCount=18;
			cell = ReportFormats.cell(row, colCount, "%", columnHeadingStyle);
			colCount++;
			cell = ReportFormats.cell(row, colCount, "Amount", columnHeadingStyle);
			colCount++;
			cell = ReportFormats.cell(row, colCount, "%", columnHeadingStyle);
			colCount++;
			cell = ReportFormats.cell(row, colCount, "Amount", columnHeadingStyle);
			colCount++;
			cell = ReportFormats.cell(row, colCount, "%", columnHeadingStyle);
			colCount++;
			cell = ReportFormats.cell(row, colCount, "Amount", columnHeadingStyle);
			colCount++;
			
			
		
			
			
			sheet.groupRow(0, 2); sheet.createFreezePane(7, 5);
			 
			
			rowCount += 1;
			int count=1;
			
			String old_division ="";
			String new_division="";
			BigDecimal goodsValueTotal = new BigDecimal(0);
			BigDecimal sgstTotal = new BigDecimal(0);
			BigDecimal cgstTotal = new BigDecimal(0);
			BigDecimal igstTotal = new BigDecimal(0);
			BigDecimal netTotal = new BigDecimal(0);

			BigDecimal goodsValueTotalGrand = new BigDecimal(0);
			BigDecimal sgstTotalGrand = new BigDecimal(0);
			BigDecimal cgstTotalGrand = new BigDecimal(0);
			BigDecimal igstTotalGrand = new BigDecimal(0);
			BigDecimal netTotalGrand = new BigDecimal(0);
			
			BigDecimal netAmount = new BigDecimal(0);
			
			int i = 0;
			for (SummaryChallanReportEInvoice data : list) {
				
				 new_division = data.getDiv_disp_nm();
					if (!(new_division.equalsIgnoreCase(old_division))) {
						if (i != 0) {
							row = sheet.createRow(rowCount);
							cell = row.createCell(colCount);
							cell.setCellValue("Division Total : ");
							sheet.addMergedRegion(new CellRangeAddress(rowCount, rowCount, colCount, 16));

							cell = row.createCell(17);
							cell.setCellValue(goodsValueTotal.doubleValue());
							goodsValueTotalGrand = goodsValueTotalGrand.add(goodsValueTotal);

							cell = row.createCell(18);
							cell.setCellValue(sgstTotal.doubleValue());
							sgstTotalGrand = sgstTotalGrand.add(sgstTotal);
							sheet.addMergedRegion(new CellRangeAddress(rowCount, rowCount, 18, 19));

							cell = row.createCell(20);
							cell.setCellValue(cgstTotal.doubleValue());
							cgstTotalGrand = cgstTotalGrand.add(cgstTotal);
							sheet.addMergedRegion(new CellRangeAddress(rowCount, rowCount, 20, 21));

							cell = row.createCell(22);
							cell.setCellValue(igstTotal.doubleValue());
							igstTotalGrand = igstTotalGrand.add(igstTotal);
							sheet.addMergedRegion(new CellRangeAddress(rowCount, rowCount, 22, 23));

							cell = row.createCell(24);
							cell.setCellValue(netTotal.doubleValue());       
							netTotalGrand = netTotalGrand.add(netTotal);
							sheet.addMergedRegion(new CellRangeAddress(rowCount, rowCount, 24, 28));
							
							cell = row.createCell(29);
							sheet.addMergedRegion(new CellRangeAddress(rowCount, rowCount, 29, 30));

							goodsValueTotal = new BigDecimal(0);
							sgstTotal = new BigDecimal(0);
							cgstTotal = new BigDecimal(0);
							igstTotal = new BigDecimal(0);
							netTotal = new BigDecimal(0);

							rowCount++;
						}
						row = sheet.createRow(rowCount);
						cell = row.createCell(0);
						cell.setCellValue("Division : " + new_division);
						cell.setCellStyle(heading1CellStyle);
						sheet.addMergedRegion(new CellRangeAddress(rowCount, rowCount, 0, limit));
						rowCount++;
					}

					
				colCount = 0;
				row = sheet.createRow(rowCount);
				
				cell = ReportFormats.cell(row, colCount, data.getWarehouse(), null);
				colCount++;
				
				cell = ReportFormats.cell(row, colCount, data.getDiv_disp_nm(), null);
				colCount++;
				
				cell = ReportFormats.cell(row, colCount, data.getDiv_map_cd(), null);
				colCount++;
				
				cell = ReportFormats.cell(row, colCount, data.getSumdsp_challan_no(), null);
				colCount++;
				
				cell = ReportFormats.cell(row, colCount, data.getSumdsp_challan_dt(), null);
				colCount++;
				
				cell = ReportFormats.cell(row, colCount, data.getSumdsp_destination(), null);
				colCount++;
				
				cell = ReportFormats.cell(row, colCount, data.getSumdsp_transporter(), null);
				colCount++;
				
				cell = ReportFormats.cell(row, colCount, data.getSumdsp_lr_no(), null);
				colCount++;
				
				cell = ReportFormats.cell(row, colCount, data.getSumdsp_lr_dt(), null);
				colCount++;
				
				cell = ReportFormats.cellNum(row, colCount, data.getSumdsp_totcases() == null ? 0l : data.getSumdsp_totcases().doubleValue(), cellAlignment);
				colCount++;
				
				cell = ReportFormats.cell(row, colCount, data.getSmp_prod_cd(), null);
				colCount++;
				
				cell = ReportFormats.cell(row, colCount, data.getSmp_prod_name(), null);
				colCount++;
				
				cell = ReportFormats.cell(row, colCount, data.getSmp_prod_type(), null);
				colCount++;
				
				cell = ReportFormats.cell(row, colCount, data.getBatch_no(), null);
				colCount++;
				
				cell = ReportFormats.cell(row, colCount, data.getBatch_expiry_dt(), null);
				colCount++;
				
				cell = ReportFormats.cellNum(row, colCount, data.getDspdtl_disp_qty() == null ? 0l : data.getDspdtl_disp_qty().doubleValue(), cellAlignment);
				colCount++;

				cell = ReportFormats.cellNum(row, colCount, data.getDspdtl_rate() == null ? 0l : data.getDspdtl_rate().doubleValue(), cellAlignment);
				colCount++;
				
				cell = ReportFormats.cellNum(row, colCount, data.getValue() == null ? 0l : data.getValue().doubleValue(), cellAlignment);
				colCount++;
				
				cell = ReportFormats.cellNum(row, colCount, data.getDsgst_rate() == null ? 0l : data.getDsgst_rate().doubleValue(), cellAlignment);
				colCount++;
				
				cell = ReportFormats.cellNum(row, colCount, data.getDsgst_bill_amt() == null ? 0l : data.getDsgst_bill_amt().doubleValue(), cellAlignment);
				colCount++;
				
				cell = ReportFormats.cellNum(row, colCount, data.getDcgst_rate() == null ? 0l : data.getDcgst_rate().doubleValue(), cellAlignment);
				colCount++;
				
				cell = ReportFormats.cellNum(row, colCount, data.getDcgst_bill_amt() == null ? 0l : data.getDcgst_bill_amt().doubleValue(), cellAlignment);
				colCount++;
				
				cell = ReportFormats.cellNum(row, colCount, data.getDigst_rate() == null ? 0l : data.getDigst_rate().doubleValue(), cellAlignment);
				colCount++;
				
				cell = ReportFormats.cellNum(row, colCount, data.getDigst_bill_amt() == null ? 0l : data.getDigst_bill_amt().doubleValue(), cellAlignment);
				colCount++;
				
				cell = ReportFormats.cell(row, colCount, data.getHsn_code(), null);
				colCount++;
				
				cell = ReportFormats.cell(row, colCount, data.getGst_reg_no(), null);
				colCount++;
				
				cell = ReportFormats.cell(row, colCount, data.getStatename(), null);
				colCount++;
				
				cell = ReportFormats.cell(row, colCount, data.getState_code(), null);
				colCount++;
				
				netAmount = netAmount.add(
						data.getValue().add(data.getDsgst_bill_amt().add(data.getDcgst_bill_amt().add(data.getDigst_bill_amt()))));
				
				cell = ReportFormats.cellNum(row, colCount, netAmount.doubleValue(), cellAlignment);
				colCount++;
				
				cell = ReportFormats.cell(row, colCount, data.getTrn_irn_number(), null);
				colCount++;
				
				cell = ReportFormats.cell(row, colCount, data.getTrn_ewaybillno(), null);
				colCount++;
				
				rowCount++;
				colCount=0;
				count++;
				i++;
				
				old_division = new_division;
				
				goodsValueTotal = goodsValueTotal.add(data.getValue());
				sgstTotal = sgstTotal.add(data.getDsgst_bill_amt());
				cgstTotal = cgstTotal.add(data.getDcgst_bill_amt());
				igstTotal = igstTotal.add(data.getDigst_bill_amt());
				netTotal = netTotal.add(data.getValue());
			}
			

			// System.out.println("Last Row Here"+i);

			row = sheet.createRow(rowCount);
			cell = row.createCell(colCount);
			cell.setCellValue("Division Total : ");
			sheet.addMergedRegion(new CellRangeAddress(rowCount, rowCount, colCount, 16));

			cell = row.createCell(17);
			cell.setCellValue(goodsValueTotal.doubleValue());
			goodsValueTotalGrand = goodsValueTotalGrand.add(goodsValueTotal);

			cell = row.createCell(18);
			cell.setCellValue(sgstTotal.doubleValue());
			sgstTotalGrand = sgstTotalGrand.add(sgstTotal);
			sheet.addMergedRegion(new CellRangeAddress(rowCount, rowCount, 18, 19));

			cell = row.createCell(20);
			cell.setCellValue(cgstTotal.doubleValue());
			cgstTotalGrand = cgstTotalGrand.add(cgstTotal);
			sheet.addMergedRegion(new CellRangeAddress(rowCount, rowCount, 20, 21));

			cell = row.createCell(22);
			cell.setCellValue(igstTotal.doubleValue());
			igstTotalGrand = igstTotalGrand.add(igstTotal);
			sheet.addMergedRegion(new CellRangeAddress(rowCount, rowCount, 22, 23));

			cell = row.createCell(24);
			cell.setCellValue(netTotal.doubleValue());
			netTotalGrand = netTotalGrand.add(netTotal);
			sheet.addMergedRegion(new CellRangeAddress(rowCount, rowCount, 24, 28));
			
			cell = row.createCell(29);
			sheet.addMergedRegion(new CellRangeAddress(rowCount, rowCount, 29, 30));

			goodsValueTotal = new BigDecimal(0);
			sgstTotal = new BigDecimal(0);
			cgstTotal = new BigDecimal(0);
			igstTotal = new BigDecimal(0);
			netTotal = new BigDecimal(0);

			rowCount++;
			colCount=0;

			row = sheet.createRow(rowCount);
			cell = row.createCell(colCount);
			cell.setCellValue("Grand Total : ");
			sheet.addMergedRegion(new CellRangeAddress(rowCount, rowCount, colCount, 16));

			cell = row.createCell(17);
			cell.setCellValue(goodsValueTotalGrand.doubleValue());

			cell = row.createCell(18);
			cell.setCellValue(sgstTotalGrand.doubleValue());
			sheet.addMergedRegion(new CellRangeAddress(rowCount, rowCount, 18, 19));

			cell = row.createCell(20);
			cell.setCellValue(cgstTotalGrand.doubleValue());
			sheet.addMergedRegion(new CellRangeAddress(rowCount, rowCount, 20, 21));

			cell = row.createCell(22);
			cell.setCellValue(igstTotalGrand.doubleValue());
			sheet.addMergedRegion(new CellRangeAddress(rowCount, rowCount, 22, 23));

			cell = row.createCell(24);
			cell.setCellValue(netTotalGrand.doubleValue());
			sheet.addMergedRegion(new CellRangeAddress(rowCount, rowCount, 24, 28));
		
			cell = row.createCell(29);
			sheet.addMergedRegion(new CellRangeAddress(rowCount, rowCount, 29, 30));
			
			FileOutputStream fileOutputStream = new FileOutputStream(filepath);
			workbook.write(fileOutputStream);
		} finally {
			if (workbook != null) { workbook.close(); }
		}
		return filename;
	}

	@Override
	public String getBlkChallasnsGeneratedLogFilename(List<BlkChallansGeneratedLog> list) throws Exception {
		String filename = "bulkChallanGenerateLog" + new Date().getTime() + ".xlsx";
		String filepath = REPORT_FILE_PATH + filename;
		XSSFWorkbook workbook = null;
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
		try {

			workbook = new XSSFWorkbook();
			File f = new File(REPORT_FILE_PATH);
			if (!f.exists()) {
				if (f.mkdirs()) {
				} else {
					throw new RuntimeException("Could not create directory");
				}
			}
			XSSFSheet sheet = workbook.createSheet("Bulk Challans Generated Log");

			XSSFRow row = null;
			XSSFCell cell = null;
			int rowCount = 0;
			int colCount = 0;
			int limit = 30;
			XSSFCellStyle cellStyle = ReportFormats.columnSubHeadingright(workbook);
			XSSFCellStyle cellAlignment = ReportFormats.setCellAlignment(workbook, ALIGN_RIGHT);
			

			String headings[] = { "Serial No.","Financial \n Year", "Period", "Division ","Request No", "Total Request", "Product \n Type",
					"Dispatch \n Type", "Challan Number", "Total Challans" };

		//	String subheadings[] = { "From","To"};
			XSSFCellStyle headingCellStyle = ReportFormats.heading(workbook);
			XSSFCellStyle columnHeadingStyle = ReportFormats.columnHeading(workbook);
			

//			row = sheet.createRow(rowCount);
//			cell = row.createCell(colCount);
//			sheet.addMergedRegion(new CellRangeAddress(rowCount, rowCount, colCount, ((colCount + headings.length - 1))));
//			cell.setCellValue(" ");
//			cell.setCellStyle(headingCellStyle);
//			rowCount++;

			row = sheet.createRow(rowCount);
			cell = row.createCell(colCount);
			sheet.addMergedRegion(
					new CellRangeAddress(rowCount, rowCount, colCount, ((colCount + headings.length - 1))));
			cell.setCellValue("Bulk Challans Generated Log");
			cell.setCellStyle(headingCellStyle);
			rowCount++;

			//rowCount += 1;
			colCount = 0;
			
			row = sheet.createRow(rowCount);
			cell = row.createCell(colCount);
			sheet.addMergedRegion(
					new CellRangeAddress(rowCount, rowCount, colCount, ((colCount + headings.length - 1))));
			cell.setCellValue("Reported On :: "+sdf.format(new Date()));
			cell.setCellStyle(headingCellStyle);
			rowCount++;
			
			row = sheet.createRow(rowCount);

//			for (int i = 0; i < (headings.length); i++) {
//				cell = ReportFormats.cell(row, i, "", columnHeadingStyle);
//				if(i==8) {
//					sheet.addMergedRegion(new CellRangeAddress(rowCount, rowCount+1, colCount, colCount));
//					//rowCount += 1;
//				}
//				if(i==8) {
//				//	sheet.addMergedRegion(new CellRangeAddress(rowCount, rowCount, colCount, colCount+1));
//				//	colCount = colCount+1;
//				}
//			}
			

			colCount=0;
			int count1 = 0;
			for (String heading : headings) {
				if (count1 ==8) {
					cell = ReportFormats.cell(row, colCount, heading, columnHeadingStyle);
					//sheet.addMergedRegion(new CellRangeAddress(rowCount, rowCount, colCount, colCount));
					sheet.addMergedRegion(new CellRangeAddress(rowCount, rowCount , colCount, colCount=colCount+1));
					colCount += 1;
				} else {
					cell = ReportFormats.cell(row, colCount, heading, columnHeadingStyle);
					sheet.addMergedRegion(new CellRangeAddress(rowCount, rowCount + 1, colCount, colCount));
					colCount++;
				}
				count1++;
			}
			
			rowCount += 1;
			colCount = 8;
			row = sheet.createRow(rowCount);
			cell = ReportFormats.cell(row, colCount, "From", columnHeadingStyle);
			colCount =colCount+1;
			cell = ReportFormats.cell(row, colCount, "To", columnHeadingStyle);
			

			//rowCount += 1;
			//row = sheet.createRow(rowCount);

			for (int i = 0; i <= 10; i++) {
				if(i!=8 && i!=9) {
					cell = ReportFormats.cell(row, i, "", columnHeadingStyle);
				}
			}
			
			//for (int i = 0; i <= 7; i++) {
				//cell = ReportFormats.cell(row, i, "", columnHeadingStyle);
			//}

//			sheet.groupRow(0, 2);
			sheet.createFreezePane(0, 4);

			rowCount += 1;
			int count = 1;

			String oldId = "0";
			Long total=0l;
			int i = 0;
			int counter = 0;
			for (BlkChallansGeneratedLog data : list) {
				
				String newId = data.getBlk_hcp_req_no();
				if (!oldId.equals(newId)) {
					if (!oldId.equals(newId) && !oldId.equals("0")) {

						colCount = 0;
						row = sheet.createRow(rowCount);
						sheet.addMergedRegion(new CellRangeAddress(rowCount, rowCount, colCount, colCount + 9));
						
						cell = ReportFormats.cell(row, colCount, "Total:-", cellStyle);
						colCount++;
						
						for (int j = 0; j < 9; j++) {
							cell = ReportFormats.cell(row, colCount, "", cellStyle);
							colCount++;
						}
						
						
						cell = ReportFormats.cell(row, colCount, total.toString(), cellStyle);
						colCount++;

						rowCount++;
						colCount = 0;
						count++;
						i++;

						total = 0l;
					}
					counter++;
					colCount = 0;
					row = sheet.createRow(rowCount);
					
					cell = ReportFormats.cell(row, colCount, counter+"", cellAlignment);
					colCount++;

					cell = ReportFormats.cell(row, colCount, data.getDsp_fin_year(), cellAlignment);
					colCount++;

					cell = ReportFormats.cell(row, colCount, data.getDsp_period_code(), cellAlignment);
					colCount++;

					cell = ReportFormats.cell(row, colCount, data.getDiv_disp_nm(), null);
					colCount++;
					
					cell = ReportFormats.cell(row, colCount, data.getBlk_hcp_req_no().toString(), cellAlignment);
					colCount++;

					cell = ReportFormats.cell(row, colCount, data.getTotal_requests().toString(), cellAlignment);
					colCount++;

					cell = ReportFormats.cell(row, colCount, data.getProd_type(), null);
					colCount++;

					cell = ReportFormats.cell(row, colCount, data.getDisp_to_type(), null);
					colCount++;

					cell = ReportFormats.cell(row, colCount, data.getFrom_challan_no(), null);
					colCount++;

					cell = ReportFormats.cell(row, colCount, data.getTo_challan_no(), null);
					colCount++;

					cell = ReportFormats.cell(row, colCount, data.getTotal_challans().toString(), cellAlignment);
					colCount++;

					total = total+data.getTotal_challans();

					rowCount++;

					colCount = 0;
					count++;
					i++;

				} else {

					colCount = 0;
					row = sheet.createRow(rowCount);
					
					cell = ReportFormats.cell(row, colCount, "", cellAlignment);
					colCount++;
					
					cell = ReportFormats.cell(row, colCount, "", null);
					colCount++;

					cell = ReportFormats.cell(row, colCount, "", null);
					colCount++;

					cell = ReportFormats.cell(row, colCount, "".toString(), null);
					colCount++;

					cell = ReportFormats.cell(row, colCount, "", null);
					colCount++;
					
					cell = ReportFormats.cell(row, colCount, "", null);
					colCount++;

					cell = ReportFormats.cell(row, colCount, data.getProd_type(), null);
					colCount++;

					cell = ReportFormats.cell(row, colCount, data.getDisp_to_type(), null);
					colCount++;

					cell = ReportFormats.cell(row, colCount, data.getFrom_challan_no(), null);
					colCount++;

					cell = ReportFormats.cell(row, colCount, data.getTo_challan_no(), null);
					colCount++;

					cell = ReportFormats.cell(row, colCount, data.getTotal_challans().toString(), cellAlignment);
					colCount++;

					total += data.getTotal_challans();

					rowCount++;
					colCount = 0;
					count++;
					i++;

				}

				oldId = newId;

			}


			colCount = 0;
			row = sheet.createRow(rowCount);


			
			sheet.addMergedRegion(new CellRangeAddress(rowCount, rowCount, colCount, colCount + 9));

			
			
			cell = ReportFormats.cell(row, colCount, "Total:-", cellStyle);
			colCount++;
			
			for (int j = 0; j < 9; j++) {
				cell = ReportFormats.cell(row, colCount, "", cellStyle);
				colCount++;
			}
			
			
			cell = ReportFormats.cell(row, colCount, total.toString(), cellStyle);
			colCount++;

			rowCount++;
			colCount = 0;
			count++;
			i++;

			total = 0l;
		
			FileOutputStream fileOutputStream = new FileOutputStream(filepath);
			workbook.write(fileOutputStream);

			System.out.println("filepath : " + filepath);

		} catch (Exception e) {

			e.printStackTrace();
		}

		finally {
			if (workbook != null) {
				workbook.close();
			}
		}

		return filename;
	}

//	@Override
//	public String generateDispatchDetReportRevised_SG(List<DispatchDetailReportRevised_SG> list, ReportBean bean,
//			String companyname,String empId, String cfa_to_stockist_ind) throws Exception {
//		String nameonly = "DispatchDetailReport" + new Date().getTime();
//		String filename = "DispatchDetailReport" + new Date().getTime() + ".xlsx";
//		String filepath = REPORT_FILE_PATH + filename;
//		SXSSFWorkbook workbook = null;
//		try {
//			workbook = new SXSSFWorkbook(1);
//			File f = new File(REPORT_FILE_PATH);
//			if (!f.exists()) {
//				if (f.mkdirs()) {
//				} else {
//					throw new RuntimeException("Could not create directory");
//				}
//			}
//			SXSSFSheet sheet = workbook.createSheet("Dispatch Detail Report");
//
//			Row row = null;
//			Cell cell = null;
//			int rowCount = 0;
//			int colCount = 0;
//			String headings[];
//			
//			
////			 headings[] = { "Sr. No.", "Ware House", "Team Name", "Team Code", "Month Of Use", "Master STN No.",
////					"Date of STN", "CFA Location", "State", "Master STN Value", "Courier(to CFA)", "Master L.R.No.",
////					"Master L.R.Date", "Cases(CFA)", "Act. WT(CFA)", "Chrg. WT(CFA)", "Requestor/Employee Number",
////					"Employee/Doctor Name", "Region Name", "Designation", "Mobile No.", "DM Name", "Territory Code",
////					"Territory Name", "Challan No.", "Challan Date.", "Challan Value", "Courier(to PSO)",
////					"L.R. No.(to PSO)", "L.R. Date(to PSO)", "Cases(to PSO)", "Act. WT(PSO)", "Chrg. WT(PSO)",
////					"Product Type", "Product Name", "Product Code", "Batch", "Expiry Date", "Dispatch Quantity", "Rate",
////					"Value", "Return Quantity", "Tentative Delivery Date", "Delivery Date", "Recieved By",
////					"Courier Comments", "Remarks", "Emergency / DtD Requestor Name", "Team Code" ,"srt Number","Srt Date","Srt Remark"};
//			 
//			 
//
//			
//
//				if ("Y".equalsIgnoreCase(cfa_to_stockist_ind)) {
//				    headings = new String[] { "Sr. No.", "Ware House", "Master STN No.",
//							"Date of STN", "CFA Location", "State", "Master STN Value", 
//							"Customer Name","Customer Destination", "Challan No.", "Challan Date.", "Challan Value", "Courier(to PSO)",
//							"L.R. No.(to PSO)", "L.R. Date(to PSO)", "Cases",
//							"Product Type", "Product Name", "Product Code", "Batch", "Expiry Date", "Dispatch Quantity", "Rate",
//							"Value"};
//				    
//				} else {
//				    headings = new String[] { "Sr. No.", "Ware House", "Team Name", "Team Code", "Month Of Use", "Master STN No.",
//							"Date of STN", "CFA Location", "State", "Master STN Value", "Courier(to CFA)", "Master L.R.No.",
//							"Master L.R.Date", "Cases(CFA)", "Act. WT(CFA)", "Chrg. WT(CFA)", "Requestor/Employee Number",
//							"Employee/Doctor Name", "Customer Name","Customer Destination","Region Name", "Designation", "Mobile No.", "DM Name", "Territory Code",
//							"Territory Name", "Challan No.", "Challan Date.", "Challan Value", "Courier(to PSO)",
//							"L.R. No.(to PSO)", "L.R. Date(to PSO)", "Cases(to PSO)", "Act. WT(PSO)", "Chrg. WT(PSO)",
//							"Product Type", "Product Name", "Product Code", "Batch", "Expiry Date", "Dispatch Quantity", "Rate",
//							"Value", "Return Quantity", "Tentative Delivery Date", "Delivery Date", "Recieved By",
//							"Courier Comments", "Remarks", "Emergency / DtD Requestor Name", "Team Code" ,"srt Number","Srt Date","Srt Remark"};
//				}
//			
//			
//			
//			
//			
//
//			CellStyle headingCellStyle = ReportFormats.heading(workbook);
//			CellStyle columnHeadingStyle = ReportFormats.columnHeading(workbook);
//			CellStyle cellAlignment = ReportFormats.setCellAlignment(workbook, ALIGN_RIGHT);
//			cellAlignment.setDataFormat(workbook.createDataFormat().getFormat("0.00"));
//
//			CellStyle cellAlignment2 = ReportFormats.setCellAlignment(workbook, ALIGN_RIGHT);
//
//			row = sheet.createRow(rowCount);
//			cell = row.createCell(colCount);
//			sheet.addMergedRegion(new CellRangeAddress(rowCount, rowCount, colCount, colCount + headings.length - 1));
//			cell.setCellValue(companyname);
//			cell.setCellStyle(headingCellStyle);
//			rowCount++;
//
//			row = sheet.createRow(rowCount);
//			cell = row.createCell(colCount);
//			sheet.addMergedRegion(new CellRangeAddress(rowCount, rowCount, colCount, colCount + headings.length - 1));
//			cell.setCellValue("Dispatch Detail Statement");
//			cell.setCellStyle(headingCellStyle);
//			rowCount++;
//
//			row = sheet.createRow(rowCount);
//			cell = row.createCell(colCount);
//			sheet.addMergedRegion(new CellRangeAddress(rowCount, rowCount, colCount, colCount + headings.length - 1));
//			cell.setCellValue("Period: " + MedicoResources.convertUtilDateToString_DD_MM_YYYY_(bean.getStartDate())
//					+ " to " + MedicoResources.convertUtilDateToString_DD_MM_YYYY_(bean.getEndDate()));
//			cell.setCellStyle(headingCellStyle);
//
//			rowCount += 1;
//			row = sheet.createRow(rowCount);
//
//			for (String heading : headings) {
//				cell = ReportFormats.cell(row, colCount, heading, columnHeadingStyle);
//				colCount++;
//			}
//
//			rowCount += 1;
//			colCount = 0;
//
//			row = sheet.createRow(rowCount);
//			cell = row.createCell(colCount);
//			sheet.addMergedRegion(new CellRangeAddress(rowCount, rowCount, colCount, colCount + 13));
//			
//			if ("Y".equalsIgnoreCase(cfa_to_stockist_ind)) {
//			cell.setCellValue("Packing Slip");
//			}else {
//				cell.setCellValue("Master Challan details");
//			}
//			cell.setCellStyle(headingCellStyle);
//
//			cell = row.createCell(colCount + 14);
//			sheet.addMergedRegion(new CellRangeAddress(rowCount, rowCount, colCount + 14, colCount + 33));
//			cell.setCellValue("Individual Challan details");
//			cell.setCellStyle(headingCellStyle);
//
//			cell = row.createCell(colCount + 34);
//			sheet.addMergedRegion(new CellRangeAddress(rowCount, rowCount, colCount + 34, colCount + 39));
//			cell.setCellValue("Product details");
//			cell.setCellStyle(headingCellStyle);
//
//			sheet.groupRow(0, 2);
//			sheet.createFreezePane(4, 5);
//
//			rowCount += 1;
//			int count = 1;
//			Iterator<DispatchDetailReportRevised_SG> dataiterator = list.iterator();
//			DispatchDetailReportRevised_SG data = null;
//			// for (DispatchDetailReportRevised data : list) {
//			while (dataiterator.hasNext()) {
//				colCount = 0;
//				data = dataiterator.next();
//				row = sheet.createRow(rowCount);
//				cell = ReportFormats.cell(row, colCount, String.valueOf(count), cellAlignment);
//				colCount++;
//
//				cell = ReportFormats.cell(row, colCount, data.getWarehouse(), null);
//				colCount++;
//
//				
//				if (!"Y".equalsIgnoreCase(cfa_to_stockist_ind)) {
//				cell = ReportFormats.cell(row, colCount, data.getDiv_disp_nm(), null);
//				colCount++;
//
//				cell = ReportFormats.cell(row, colCount, data.getDiv_map_cd(), null);
//				colCount++;
//
//				cell = ReportFormats.cell(row, colCount, data.getMonth_of_use(), null);
//				colCount++;
//				}
//				
//				cell = ReportFormats.cell(row, colCount, data.getSumdsp_challan_no(), null);
//				colCount++;
//
//				cell = ReportFormats.cell(row, colCount, data.getSumdsp_challan_dt(), null);
//				colCount++;
//
//				cell = ReportFormats.cell(row, colCount, data.getSumdsp_destination(), null);
//				colCount++;
//
//				cell = ReportFormats.cell(row, colCount, data.getState_name(), null);
//				colCount++;
//				
//
//				cell = ReportFormats.cellNum(row, colCount,
//						data.getSumdsp_total_goods_val() == null ? 0l : data.getSumdsp_total_goods_val().doubleValue(),
//						cellAlignment);
//				colCount++;
//				
//				
//				
//				
//				if (!"Y".equalsIgnoreCase(cfa_to_stockist_ind)) {
//				cell = ReportFormats.cell(row, colCount, data.getSumdsp_transporter(), null);
//				colCount++;
//
//				cell = ReportFormats.cell(row, colCount, data.getSumdsp_lr_no(), null);
//				colCount++;
//
//				cell = ReportFormats.cell(row, colCount, data.getSumdsp_lr_dt(), null);
//				colCount++;
//
//				cell = ReportFormats.cellNum(row, colCount,
//						data.getSumdsp_totcases() == null ? 0l : data.getSumdsp_totcases().doubleValue(),
//						cellAlignment2);
//				colCount++;
//
//				cell = ReportFormats.cellNum(row, colCount,
//						data.getSum_dsp_act_wt() == null ? 0l : data.getSum_dsp_act_wt().doubleValue(), cellAlignment);
//				colCount++;
//
//				cell = ReportFormats.cellNum(row, colCount,
//						data.getSum_dsp_bill_wt() == null ? 0l : data.getSum_dsp_bill_wt().doubleValue(),
//						cellAlignment);
//				colCount++;
//				
//
//				cell = ReportFormats.cell(row, colCount, data.getDspfstaff_employeeno (), null);
//				colCount++;
//				
//				}
//				
//
//				
//				/// cust name
//				cell = ReportFormats.cell(row, colCount, data.getDspfstaff_displayname(), null);
//				colCount++;
//				
//				//cust des
////				cell = ReportFormats.cell(row, colCount, data.getDspfstaff_destination(), null);
////				colCount++;
//
//				
//				
//				if (!"Y".equalsIgnoreCase(cfa_to_stockist_ind)) {
//				cell = ReportFormats.cell(row, colCount, data.getDspfstaff_displayname(), null);
//				colCount++;
//				
//				cell = ReportFormats.cell(row, colCount, data.getRegion(), null);
//				colCount++;
//
//				cell = ReportFormats.cell(row, colCount, data.getDspfstaff_desg(), null);
//				colCount++;
//
//				cell = ReportFormats.cell(row, colCount, data.getMobile(), null);
//				colCount++;
//
//				cell = ReportFormats.cell(row, colCount, data.getDm_name(), null);
//				colCount++;
//
//				cell = ReportFormats.cell(row, colCount, data.getTerr_code(), null);
//				colCount++;
//
//				// cell = ReportFormats.cell(row, colCount, data.getPosition(), null);
//				// colCount++;
//
//				cell = ReportFormats.cell(row, colCount, data.getTerr_name(), null);
//				colCount++;
//
//				
//			}
//				cell = ReportFormats.cell(row, colCount, data.getDsp_challan_no(), null);
//				colCount++;
//
//				cell = ReportFormats.cell(row, colCount, data.getDsp_challan_dt(), null);
//				colCount++;
//
//				cell = ReportFormats.cellNum(row, colCount,
//						data.getDsp_total_goods_val() == null ? 0l : data.getDsp_total_goods_val().doubleValue(),
//						cellAlignment);
//				colCount++;
//
//				cell = ReportFormats.cell(row, colCount, data.getDsp_transporter(), null);
//				colCount++;
//
//				cell = ReportFormats.cell(row, colCount, data.getDsp_lr_no(), null);
//				colCount++;
//
//				cell = ReportFormats.cell(row, colCount, data.getDsp_lr_dt(), null);
//				colCount++;
//
//				cell = ReportFormats.cellNum(row, colCount,
//						data.getDsp_cases() == null ? 0l : data.getDsp_cases().doubleValue(), cellAlignment2);
//				colCount++;
//				
//				
//				
//				if (!"Y".equalsIgnoreCase(cfa_to_stockist_ind)) {
//
//				cell = ReportFormats.cellNum(row, colCount,
//						data.getDsp_act_wt() == null ? 0l : data.getDsp_act_wt().doubleValue(), cellAlignment);
//				colCount++;
//
//				cell = ReportFormats.cellNum(row, colCount,
//						data.getDsp_bill_wt() == null ? 0l : data.getDsp_bill_wt().doubleValue(), cellAlignment);
//				colCount++;
//				
//				}
//
//				cell = ReportFormats.cell(row, colCount, data.getSmp_prod_type(), null);
//				colCount++;
//
//				cell = ReportFormats.cell(row, colCount, data.getSmp_prod_name(), null);
//				colCount++;
//
//				cell = ReportFormats.cell(row, colCount, data.getSmp_prod_cd(), null);
//				colCount++;
//
//				cell = ReportFormats.cell(row, colCount, data.getBatch_no(), null);
//				colCount++;
//
//				cell = ReportFormats.cell(row, colCount, data.getBatch_expiry_dt(), null);
//				colCount++;
//
//				cell = ReportFormats.cellNum(row, colCount,
//						data.getDspdtl_disp_qty() == null ? 0l : data.getDspdtl_disp_qty().doubleValue(),
//						cellAlignment);
//				colCount++;
//
//				cell = ReportFormats.cellNum(row, colCount,
//						data.getDspdtl_rate() == null ? 0l : data.getDspdtl_rate().doubleValue(), cellAlignment);
//				colCount++;
//
//				cell = ReportFormats.cellNum(row, colCount,
//						data.getValue() == null ? 0l : data.getValue().doubleValue(), cellAlignment);
//				colCount++;
//
//				
//				if (!"Y".equalsIgnoreCase(cfa_to_stockist_ind)) {
//				cell = ReportFormats.cellNum(row, colCount,
//						data.getReturn_qty() == null ? 0l : data.getReturn_qty().doubleValue(), cellAlignment);
//				colCount++;
//
//				
//				cell = ReportFormats.cell(row, colCount, data.getTentative_delivery_date(), null);
//				colCount++;
//				cell = ReportFormats.cell(row, colCount, data.getActual_delivery_date(), null);
//				colCount++;
//				}
//
//			
//
//				
//				if (!"Y".equalsIgnoreCase(cfa_to_stockist_ind)) {
//				
//				cell = ReportFormats.cell(row, colCount, data.getRecieved_by(), null);
//				colCount++;
//
//				cell = ReportFormats.cell(row, colCount, data.getCourier_comment(), null);
//				colCount++;
//				
//				cell = ReportFormats.cell(row, colCount, data.getEmer_dtd_supplied_to(), null);
//				colCount++;
//
//				}
//
//			
//
//		
//				
//				if (!"Y".equalsIgnoreCase(cfa_to_stockist_ind)) {
//					
//					cell = ReportFormats.cell(row, colCount, data.getRemarks(), null);
//					colCount++;
//					
//					cell = ReportFormats.cell(row, colCount, data.getTeam_code(), null);
//					colCount++;
//					
//				cell = ReportFormats.cell(row, colCount, data.getSrt_number(), null);
//				colCount++;
//				
//				cell = ReportFormats.cell(row, colCount, data.getSrt_date().trim().equals("1900-01-01")?"":data.getSrt_date(), null);
//				colCount++;
//				
//				cell = ReportFormats.cell(row, colCount, data.getSrt_remark(), null);
//				colCount++;
//				}
//				
//
//				rowCount++;
//				count++;
//				dataiterator.remove();
//
//			}
//			FileOutputStream fileOutputStream = new FileOutputStream(filepath);
//			workbook.write(fileOutputStream);
//
//			filename=usermasterservice.generateExcellock(filepath, filename,empId,".xlsx");
//
//		} catch (Throwable e) {
//			// TODO Auto-generated catch block
//			// System.out.println("Error Occurred :" +e.getMessage());// uncomment asneeded --;
//		} finally {
//			if (workbook != null) {
//				workbook.close();
//				workbook.dispose();
//			}
//		}
//		return filename;
//	}
	
	
	
	@Override
	public String generateDispatchDetReportRevised_SG(List<DispatchDetailReportRevised_SG> list, ReportBean bean,
			String companyname,String empId) throws Exception {
		String nameonly = "DispatchDetailReport" + new Date().getTime();
		String filename = "DispatchDetailReport" + new Date().getTime() + ".xlsx";
		String filepath = REPORT_FILE_PATH + filename;
		SXSSFWorkbook workbook = null;
		try {
			workbook = new SXSSFWorkbook(1);
			File f = new File(REPORT_FILE_PATH);
			if (!f.exists()) {
				if (f.mkdirs()) {
				} else {
					throw new RuntimeException("Could not create directory");
				}
			}
			SXSSFSheet sheet = workbook.createSheet("Dispatch Detail Report");

			Row row = null;
			Cell cell = null;
			int rowCount = 0;
			int colCount = 0;

			String headings[] = { "Sr. No.", "Ware House", "Team Name", "Team Code", "Month Of Use", "Master STN No.",
					"Date of STN", "CFA Location", "State", "Master STN Value", "Courier(to CFA)", "Master L.R.No.",
					"Master L.R.Date", "Cases(CFA)", "Act. WT(CFA)", "Chrg. WT(CFA)", "Requestor/Employee Number",
					"Employee/Doctor Name", "Region Name", "Designation", "Mobile No.", "DM Name", "Territory Code",
					"Territory Name", "Challan No.", "Challan Date.", "Challan Value", "Courier(to PSO)","Transport Mode",
					"L.R. No.(to PSO)", "L.R. Date(to PSO)", "Cases(to PSO)", "Act. WT(PSO)", "Chrg. WT(PSO)",
					"Product Type", "Product Name", "Product Code", "Batch", "Expiry Date", "Dispatch Quantity", "Rate",
					"Value", "Return Quantity", "Tentative Delivery Date", "Delivery Date", "Recieved By",
					"Courier Comments", "Remarks", "Emergency / DtD Requestor Name", "Team Code" ,"srt Number","Srt Date","Srt Remark"};

			CellStyle headingCellStyle = ReportFormats.heading(workbook);
			CellStyle columnHeadingStyle = ReportFormats.columnHeading(workbook);
			CellStyle cellAlignment = ReportFormats.setCellAlignment(workbook, ALIGN_RIGHT);
			cellAlignment.setDataFormat(workbook.createDataFormat().getFormat("0.00"));

			CellStyle cellAlignment2 = ReportFormats.setCellAlignment(workbook, ALIGN_RIGHT);

			row = sheet.createRow(rowCount);
			cell = row.createCell(colCount);
			sheet.addMergedRegion(new CellRangeAddress(rowCount, rowCount, colCount, colCount + headings.length - 1));
			cell.setCellValue(companyname);
			cell.setCellStyle(headingCellStyle);
			rowCount++;

			row = sheet.createRow(rowCount);
			cell = row.createCell(colCount);
			sheet.addMergedRegion(new CellRangeAddress(rowCount, rowCount, colCount, colCount + headings.length - 1));
			cell.setCellValue("Dispatch Detail Statement");
			cell.setCellStyle(headingCellStyle);
			rowCount++;

			row = sheet.createRow(rowCount);
			cell = row.createCell(colCount);
			sheet.addMergedRegion(new CellRangeAddress(rowCount, rowCount, colCount, colCount + headings.length - 1));
			cell.setCellValue("Period: " + MedicoResources.convertUtilDateToString_DD_MM_YYYY_(bean.getStartDate())
					+ " to " + MedicoResources.convertUtilDateToString_DD_MM_YYYY_(bean.getEndDate()));
			cell.setCellStyle(headingCellStyle);

			rowCount += 1;
			row = sheet.createRow(rowCount);

			for (String heading : headings) {
				cell = ReportFormats.cell(row, colCount, heading, columnHeadingStyle);
				colCount++;
			}

			rowCount += 1;
			colCount = 0;

			row = sheet.createRow(rowCount);
			cell = row.createCell(colCount);
			sheet.addMergedRegion(new CellRangeAddress(rowCount, rowCount, colCount, colCount + 13));
			cell.setCellValue("Master Challan details");
			cell.setCellStyle(headingCellStyle);

			cell = row.createCell(colCount + 14);
			sheet.addMergedRegion(new CellRangeAddress(rowCount, rowCount, colCount + 14, colCount + 33));
			cell.setCellValue("Individual Challan details");
			cell.setCellStyle(headingCellStyle);

			cell = row.createCell(colCount + 34);
			sheet.addMergedRegion(new CellRangeAddress(rowCount, rowCount, colCount + 34, colCount + 47));
			cell.setCellValue("Product details");
			cell.setCellStyle(headingCellStyle);

			sheet.groupRow(0, 2);
			sheet.createFreezePane(7, 5);

			rowCount += 1;
			int count = 1;
			
			LocalDate endDate = bean.getEndDate().toInstant()
                    .atZone(ZoneId.systemDefault())
                    .toLocalDate();

			LocalDate cutoffDate = LocalDate.of(2024, 12, 1);

			if(bean.getCompcd().trim().equalsIgnoreCase(PFZ) && endDate.isBefore(cutoffDate)) {
				list = list.stream().peek(data -> {
					if ("CITIUS".equalsIgnoreCase(data.getDiv_disp_nm().trim())
							|| "CITIUS  (ALTIUS - NEW)".equalsIgnoreCase(data.getDiv_disp_nm().trim())) {
						data.setDiv_disp_nm("ALTIUS - NEW");
					}
				}).collect(Collectors.toList());

			}
			
			Iterator<DispatchDetailReportRevised_SG> dataiterator = list.iterator();
			DispatchDetailReportRevised_SG data = null;
			// for (DispatchDetailReportRevised data : list) {
			while (dataiterator.hasNext()) {
				colCount = 0;
				data = dataiterator.next();
				row = sheet.createRow(rowCount);
				cell = ReportFormats.cell(row, colCount, String.valueOf(count), cellAlignment);
				colCount++;

				cell = ReportFormats.cell(row, colCount, data.getWarehouse(), null);
				colCount++;

				cell = ReportFormats.cell(row, colCount, data.getDiv_disp_nm(), null);
				colCount++;

				cell = ReportFormats.cell(row, colCount, data.getDiv_map_cd(), null);
				colCount++;

				cell = ReportFormats.cell(row, colCount, data.getMonth_of_use(), null);
				colCount++;

				cell = ReportFormats.cell(row, colCount, data.getSumdsp_challan_no(), null);
				colCount++;

				cell = ReportFormats.cell(row, colCount, data.getSumdsp_challan_dt(), null);
				colCount++;

				cell = ReportFormats.cell(row, colCount, data.getSumdsp_destination(), null);
				colCount++;

				cell = ReportFormats.cell(row, colCount, data.getState_name(), null);
				colCount++;

				cell = ReportFormats.cellNum(row, colCount,
						data.getSumdsp_total_goods_val() == null ? 0l : data.getSumdsp_total_goods_val().doubleValue(),
						cellAlignment);
				colCount++;

				cell = ReportFormats.cell(row, colCount, data.getSumdsp_transporter(), null);
				colCount++;

				cell = ReportFormats.cell(row, colCount, data.getSumdsp_lr_no(), null);
				colCount++;

				cell = ReportFormats.cell(row, colCount, data.getSumdsp_lr_dt(), null);
				colCount++;

				cell = ReportFormats.cellNum(row, colCount,
						data.getSumdsp_totcases() == null ? 0l : data.getSumdsp_totcases().doubleValue(),
						cellAlignment2);
				colCount++;

				cell = ReportFormats.cellNum(row, colCount,
						data.getSum_dsp_act_wt() == null ? 0l : data.getSum_dsp_act_wt().doubleValue(), cellAlignment);
				colCount++;

				cell = ReportFormats.cellNum(row, colCount,
						data.getSum_dsp_bill_wt() == null ? 0l : data.getSum_dsp_bill_wt().doubleValue(),
						cellAlignment);
				colCount++;

				cell = ReportFormats.cell(row, colCount, data.getDspfstaff_employeeno(), null);
				colCount++;

				cell = ReportFormats.cell(row, colCount, data.getDspfstaff_displayname(), null);
				colCount++;

				cell = ReportFormats.cell(row, colCount, data.getRegion(), null);
				colCount++;

				cell = ReportFormats.cell(row, colCount, data.getDspfstaff_desg(), null);
				colCount++;

				cell = ReportFormats.cell(row, colCount, data.getMobile(), null);
				colCount++;

				cell = ReportFormats.cell(row, colCount, data.getDm_name(), null);
				colCount++;

				cell = ReportFormats.cell(row, colCount, data.getTerr_code(), null);
				colCount++;

				// cell = ReportFormats.cell(row, colCount, data.getPosition(), null);
				// colCount++;

				cell = ReportFormats.cell(row, colCount, data.getTerr_name(), null);
				colCount++;

				cell = ReportFormats.cell(row, colCount, data.getDsp_challan_no(), null);
				colCount++;

				cell = ReportFormats.cell(row, colCount, data.getDsp_challan_dt(), null);
				colCount++;

				cell = ReportFormats.cellNum(row, colCount,
						data.getDsp_total_goods_val() == null ? 0l : data.getDsp_total_goods_val().doubleValue(),
						cellAlignment);
				colCount++;

				cell = ReportFormats.cell(row, colCount, data.getDsp_transporter(), null);
				colCount++;
				
				cell = ReportFormats.cell(row, colCount, data.getTransport_mode(), null);
				colCount++;
				

				cell = ReportFormats.cell(row, colCount, data.getDsp_lr_no(), null);
				colCount++;

				cell = ReportFormats.cell(row, colCount, data.getDsp_lr_dt(), null);
				colCount++;

				cell = ReportFormats.cellNum(row, colCount,
						data.getDsp_cases() == null ? 0l : data.getDsp_cases().doubleValue(), cellAlignment2);
				colCount++;

				cell = ReportFormats.cellNum(row, colCount,
						data.getDsp_act_wt() == null ? 0l : data.getDsp_act_wt().doubleValue(), cellAlignment);
				colCount++;

				cell = ReportFormats.cellNum(row, colCount,
						data.getDsp_bill_wt() == null ? 0l : data.getDsp_bill_wt().doubleValue(), cellAlignment);
				colCount++;

				cell = ReportFormats.cell(row, colCount, data.getSmp_prod_type(), null);
				colCount++;

				cell = ReportFormats.cell(row, colCount, data.getSmp_prod_name(), null);
				colCount++;

				cell = ReportFormats.cell(row, colCount, data.getSmp_prod_cd(), null);
				colCount++;

				cell = ReportFormats.cell(row, colCount, data.getBatch_no(), null);
				colCount++;

				cell = ReportFormats.cell(row, colCount, data.getBatch_expiry_dt(), null);
				colCount++;

				cell = ReportFormats.cellNum(row, colCount,
						data.getDspdtl_disp_qty() == null ? 0l : data.getDspdtl_disp_qty().doubleValue(),
						cellAlignment);
				colCount++;

				cell = ReportFormats.cellNum(row, colCount,
						data.getDspdtl_rate() == null ? 0l : data.getDspdtl_rate().doubleValue(), cellAlignment);
				colCount++;

				cell = ReportFormats.cellNum(row, colCount,
						data.getValue() == null ? 0l : data.getValue().doubleValue(), cellAlignment);
				colCount++;

				cell = ReportFormats.cellNum(row, colCount,
						data.getReturn_qty() == null ? 0l : data.getReturn_qty().doubleValue(), cellAlignment);
				colCount++;

				cell = ReportFormats.cell(row, colCount, data.getTentative_delivery_date(), null);
				colCount++;

				cell = ReportFormats.cell(row, colCount, data.getActual_delivery_date(), null);
				colCount++;

				cell = ReportFormats.cell(row, colCount, data.getRecieved_by(), null);
				colCount++;

				cell = ReportFormats.cell(row, colCount, data.getCourier_comment(), null);
				colCount++;

				cell = ReportFormats.cell(row, colCount, data.getRemarks(), null);
				colCount++;

				cell = ReportFormats.cell(row, colCount, data.getEmer_dtd_supplied_to(), null);
				colCount++;

				cell = ReportFormats.cell(row, colCount, data.getTeam_code(), null);
				colCount++;
				
				cell = ReportFormats.cell(row, colCount, data.getSrt_number(), null);
				colCount++;
				
				cell = ReportFormats.cell(row, colCount, data.getSrt_date().trim().equals("1900-01-01")?"":data.getSrt_date(), null);
				colCount++;
				
				cell = ReportFormats.cell(row, colCount, data.getSrt_remark(), null);
				colCount++;
				
			

				rowCount++;
				count++;
				dataiterator.remove();

			}
			FileOutputStream fileOutputStream = new FileOutputStream(filepath);
			workbook.write(fileOutputStream);

			filename=usermasterservice.generateExcellock(filepath, filename,empId,".xlsx");

		} catch (Throwable e) {
			// TODO Auto-generated catch block
			// System.out.println("Error Occurred :" + new CodifyErrors().getErrorCode(e));// uncomment asneeded --;
		} finally {
			if (workbook != null) {
				workbook.close();
				workbook.dispose();
			}
		}
		return filename;
	}

	@Override
	public String generateDispatchDetReportRevisedMdmNo(List<DispatchDetailReportRevisedMdmNo> list, ReportBean bean,
			String companyname, String empId) throws Exception {
		String nameonly = "DispatchDetailReportMDMNo" + new Date().getTime();
		String filename = "DispatchDetailReportMDMNo" + new Date().getTime() + ".xlsx";
		String filepath = REPORT_FILE_PATH + filename;
		SXSSFWorkbook workbook = null;
		try {
			workbook = new SXSSFWorkbook(1);
			File f = new File(REPORT_FILE_PATH);
			if (!f.exists()) {
				if (f.mkdirs()) {
				} else {
					throw new RuntimeException("Could not create directory");
				}
			}
			SXSSFSheet sheet = workbook.createSheet("Dispatch Detail Report");

			Row row = null;
			Cell cell = null;
			int rowCount = 0;
			int colCount = 0;

			String headings[] = { "Sr. No.", "Ware House", "Team Name", "Team Code", "Month Of Use", "Master STN No.",
					"Date of STN", "CFA Location", "State", "Master STN Value", "Courier(to CFA)", "Master L.R.No.",
					"Master L.R.Date", "Cases(CFA)", "Act. WT(CFA)", "Chrg. WT(CFA)", "Requestor/Employee Number",
					"Employee/Doctor Name", "Region Name", "Designation", "Mobile No.", "DM Name", "Territory Code",
					"Territory Name", "Challan No.", "Challan Date.", "Challan Value", "Courier(to PSO)",
					"L.R. No.(to PSO)", "L.R. Date(to PSO)", "Cases(to PSO)", "Act. WT(PSO)", "Chrg. WT(PSO)",
					"Product Type", "Product Name", "Product Code", "Batch", "Expiry Date", "Dispatch Quantity", "Rate",
					"Value", "Return Quantity", "Tentative Delivery Date", "Delivery Date", "Recieved By",
					"Courier Comments", "Remarks", "Emergency / DtD Requestor Name", "Team Code" ,"srt Number","Srt Date","Srt Remark","MDM Number","MDM Name"};

			CellStyle headingCellStyle = ReportFormats.heading(workbook);
			CellStyle columnHeadingStyle = ReportFormats.columnHeading(workbook);
			CellStyle cellAlignment = ReportFormats.setCellAlignment(workbook, ALIGN_RIGHT);
			cellAlignment.setDataFormat(workbook.createDataFormat().getFormat("0.00"));

			CellStyle cellAlignment2 = ReportFormats.setCellAlignment(workbook, ALIGN_RIGHT);

			row = sheet.createRow(rowCount);
			cell = row.createCell(colCount);
			sheet.addMergedRegion(new CellRangeAddress(rowCount, rowCount, colCount, colCount + headings.length - 1));
			cell.setCellValue(companyname);
			cell.setCellStyle(headingCellStyle);
			rowCount++;

			row = sheet.createRow(rowCount);
			cell = row.createCell(colCount);
			sheet.addMergedRegion(new CellRangeAddress(rowCount, rowCount, colCount, colCount + headings.length - 1));
			cell.setCellValue("Dispatch Detail Statement");
			cell.setCellStyle(headingCellStyle);
			rowCount++;

			row = sheet.createRow(rowCount);
			cell = row.createCell(colCount);
			sheet.addMergedRegion(new CellRangeAddress(rowCount, rowCount, colCount, colCount + headings.length - 1));
			cell.setCellValue("Period: " + MedicoResources.convertUtilDateToString_DD_MM_YYYY_(bean.getStartDate())
					+ " to " + MedicoResources.convertUtilDateToString_DD_MM_YYYY_(bean.getEndDate()));
			cell.setCellStyle(headingCellStyle);

			rowCount += 1;
			row = sheet.createRow(rowCount);

			for (String heading : headings) {
				cell = ReportFormats.cell(row, colCount, heading, columnHeadingStyle);
				colCount++;
			}

			rowCount += 1;
			colCount = 0;

			row = sheet.createRow(rowCount);
			cell = row.createCell(colCount);
			sheet.addMergedRegion(new CellRangeAddress(rowCount, rowCount, colCount, colCount + 13));
			cell.setCellValue("Master Challan details");
			cell.setCellStyle(headingCellStyle);

			cell = row.createCell(colCount + 14);
			sheet.addMergedRegion(new CellRangeAddress(rowCount, rowCount, colCount + 14, colCount + 33));
			cell.setCellValue("Individual Challan details");
			cell.setCellStyle(headingCellStyle);

			cell = row.createCell(colCount + 34);
			sheet.addMergedRegion(new CellRangeAddress(rowCount, rowCount, colCount + 34, colCount + 47));
			cell.setCellValue("Product details");
			cell.setCellStyle(headingCellStyle);

			sheet.groupRow(0, 2);
			sheet.createFreezePane(7, 5);

			rowCount += 1;
			int count = 1;
			Iterator<DispatchDetailReportRevisedMdmNo> dataiterator = list.iterator();
			DispatchDetailReportRevisedMdmNo data = null;
			// for (DispatchDetailReportRevised data : list) {
			while (dataiterator.hasNext()) {
				colCount = 0;
				data = dataiterator.next();
				row = sheet.createRow(rowCount);
				cell = ReportFormats.cell(row, colCount, String.valueOf(count), cellAlignment);
				colCount++;

				cell = ReportFormats.cell(row, colCount, data.getWarehouse(), null);
				colCount++;

				cell = ReportFormats.cell(row, colCount, data.getDiv_disp_nm(), null);
				colCount++;

				cell = ReportFormats.cell(row, colCount, data.getDiv_map_cd(), null);
				colCount++;

				cell = ReportFormats.cell(row, colCount, data.getMonth_of_use(), null);
				colCount++;

				cell = ReportFormats.cell(row, colCount, data.getSumdsp_challan_no(), null);
				colCount++;

				cell = ReportFormats.cell(row, colCount, data.getSumdsp_challan_dt(), null);
				colCount++;

				cell = ReportFormats.cell(row, colCount, data.getSumdsp_destination(), null);
				colCount++;

				cell = ReportFormats.cell(row, colCount, data.getState_name(), null);
				colCount++;

				cell = ReportFormats.cellNum(row, colCount,
						data.getSumdsp_total_goods_val() == null ? 0l : data.getSumdsp_total_goods_val().doubleValue(),
						cellAlignment);
				colCount++;

				cell = ReportFormats.cell(row, colCount, data.getSumdsp_transporter(), null);
				colCount++;

				cell = ReportFormats.cell(row, colCount, data.getSumdsp_lr_no(), null);
				colCount++;

				cell = ReportFormats.cell(row, colCount, data.getSumdsp_lr_dt(), null);
				colCount++;

				cell = ReportFormats.cellNum(row, colCount,
						data.getSumdsp_totcases() == null ? 0l : data.getSumdsp_totcases().doubleValue(),
						cellAlignment2);
				colCount++;

				cell = ReportFormats.cellNum(row, colCount,
						data.getSum_dsp_act_wt() == null ? 0l : data.getSum_dsp_act_wt().doubleValue(), cellAlignment);
				colCount++;

				cell = ReportFormats.cellNum(row, colCount,
						data.getSum_dsp_bill_wt() == null ? 0l : data.getSum_dsp_bill_wt().doubleValue(),
						cellAlignment);
				colCount++;

				cell = ReportFormats.cell(row, colCount, data.getDspfstaff_employeeno(), null);
				colCount++;

				cell = ReportFormats.cell(row, colCount, data.getDspfstaff_displayname(), null);
				colCount++;

				cell = ReportFormats.cell(row, colCount, data.getRegion(), null);
				colCount++;

				cell = ReportFormats.cell(row, colCount, data.getDspfstaff_desg(), null);
				colCount++;

				cell = ReportFormats.cell(row, colCount, data.getMobile(), null);
				colCount++;

				cell = ReportFormats.cell(row, colCount, data.getDm_name(), null);
				colCount++;

				cell = ReportFormats.cell(row, colCount, data.getTerr_code(), null);
				colCount++;

				// cell = ReportFormats.cell(row, colCount, data.getPosition(), null);
				// colCount++;

				cell = ReportFormats.cell(row, colCount, data.getTerr_name(), null);
				colCount++;

				cell = ReportFormats.cell(row, colCount, data.getDsp_challan_no(), null);
				colCount++;

				cell = ReportFormats.cell(row, colCount, data.getDsp_challan_dt(), null);
				colCount++;

				cell = ReportFormats.cellNum(row, colCount,
						data.getDsp_total_goods_val() == null ? 0l : data.getDsp_total_goods_val().doubleValue(),
						cellAlignment);
				colCount++;

				cell = ReportFormats.cell(row, colCount, data.getDsp_transporter(), null);
				colCount++;

				cell = ReportFormats.cell(row, colCount, data.getDsp_lr_no(), null);
				colCount++;

				cell = ReportFormats.cell(row, colCount, data.getDsp_lr_dt(), null);
				colCount++;

				cell = ReportFormats.cellNum(row, colCount,
						data.getDsp_cases() == null ? 0l : data.getDsp_cases().doubleValue(), cellAlignment2);
				colCount++;

				cell = ReportFormats.cellNum(row, colCount,
						data.getDsp_act_wt() == null ? 0l : data.getDsp_act_wt().doubleValue(), cellAlignment);
				colCount++;

				cell = ReportFormats.cellNum(row, colCount,
						data.getDsp_bill_wt() == null ? 0l : data.getDsp_bill_wt().doubleValue(), cellAlignment);
				colCount++;

				cell = ReportFormats.cell(row, colCount, data.getSmp_prod_type(), null);
				colCount++;

				cell = ReportFormats.cell(row, colCount, data.getSmp_prod_name(), null);
				colCount++;

				cell = ReportFormats.cell(row, colCount, data.getSmp_prod_cd(), null);
				colCount++;

				cell = ReportFormats.cell(row, colCount, data.getBatch_no(), null);
				colCount++;

				cell = ReportFormats.cell(row, colCount, data.getBatch_expiry_dt(), null);
				colCount++;

				cell = ReportFormats.cellNum(row, colCount,
						data.getDspdtl_disp_qty() == null ? 0l : data.getDspdtl_disp_qty().doubleValue(),
						cellAlignment);
				colCount++;

				cell = ReportFormats.cellNum(row, colCount,
						data.getDspdtl_rate() == null ? 0l : data.getDspdtl_rate().doubleValue(), cellAlignment);
				colCount++;

				cell = ReportFormats.cellNum(row, colCount,
						data.getValue() == null ? 0l : data.getValue().doubleValue(), cellAlignment);
				colCount++;

				cell = ReportFormats.cellNum(row, colCount,
						data.getReturn_qty() == null ? 0l : data.getReturn_qty().doubleValue(), cellAlignment);
				colCount++;

				cell = ReportFormats.cell(row, colCount, data.getTentative_delivery_date(), null);
				colCount++;

				cell = ReportFormats.cell(row, colCount, data.getActual_delivery_date(), null);
				colCount++;

				cell = ReportFormats.cell(row, colCount, data.getRecieved_by(), null);
				colCount++;

				cell = ReportFormats.cell(row, colCount, data.getCourier_comment(), null);
				colCount++;

				cell = ReportFormats.cell(row, colCount, data.getRemarks(), null);
				colCount++;

				cell = ReportFormats.cell(row, colCount, data.getEmer_dtd_supplied_to(), null);
				colCount++;

				cell = ReportFormats.cell(row, colCount, data.getTeam_code(), null);
				colCount++;
				
				cell = ReportFormats.cell(row, colCount, data.getSrt_number(), null);
				colCount++;
				
				cell = ReportFormats.cell(row, colCount, data.getSrt_date().trim().equals("1900-01-01")?"":data.getSrt_date(), null);
				colCount++;
				
				cell = ReportFormats.cell(row, colCount, data.getSrt_remark(), null);
				colCount++;
				
				cell = ReportFormats.cell(row, colCount, data.getMdm_number(), null);
				colCount++;
				
				cell = ReportFormats.cell(row, colCount, data.getMdm_name(), null);
				colCount++;

				rowCount++;
				count++;
				dataiterator.remove();

			}
			FileOutputStream fileOutputStream = new FileOutputStream(filepath);
			workbook.write(fileOutputStream);

			filename=usermasterservice.generateExcellock(filepath, filename,empId,".xlsx");

		} catch (Throwable e) {
			// TODO Auto-generated catch block
			// System.out.println("Error Occurred :" + new CodifyErrors().getErrorCode(e));// uncomment asneeded --;
		} finally {
			if (workbook != null) {
				workbook.close();
				workbook.dispose();
			}
		}
		return filename;
	}

	@Override
	public String generateDispatchDetReportRevised_VET(List<DispatchDetailReportRevised_VET> list, ReportBean bean,
			String companyname, String empIdd) throws IOException {
		String nameonly = "DispatchDetailReport" + new Date().getTime();
		String filename = "DispatchDetailReport" + new Date().getTime() + ".xlsx";
		String filepath = REPORT_FILE_PATH + filename;
		SXSSFWorkbook workbook = null;
		try {
			workbook = new SXSSFWorkbook(1);
			File f = new File(REPORT_FILE_PATH);
			if (!f.exists()) {
				if (f.mkdirs()) {
				} else {
					throw new RuntimeException("Could not create directory");
				}
			}
			SXSSFSheet sheet = workbook.createSheet("Dispatch Detail Report");

			Row row = null;
			Cell cell = null;
			int rowCount = 0;
			int colCount = 0;

			String headings[] = { "Sr. No.", "Ware House", "Team Name", "Team Code", "Month Of Use", "Master STN No.",
					"Date of STN", "CFA Location", "State", "Master STN Value", "Courier(to CFA)", "Master L.R.No.",
					"Master L.R.Date", "Cases(CFA)", "Act. WT(CFA)", "Chrg. WT(CFA)", "Requestor/Employee Number",
					"Employee/Doctor Name", "Region Name", "Designation", "Mobile No.", "DM Name", "Territory Code",
					"Territory Name", "Challan No.", "Challan Date.", "Challan Value", "Courier(to PSO)",
					"L.R. No.(to PSO)", "L.R. Date(to PSO)", "Cases(to PSO)", "Act. WT(PSO)", "Chrg. WT(PSO)",
					"Product Type", "Product Name", "Product Code", "Batch", "Expiry Date", "Dispatch Quantity", "Rate",
					"Value", "Return Quantity", "Tentative Delivery Date", "Delivery Date", "Recieved By",
					"Courier Comments", "Remarks", "Emergency / DtD Requestor Name", "Team Code" ,"srt Number","Srt Date","Srt Remark"};

			CellStyle headingCellStyle = ReportFormats.heading(workbook);
			CellStyle columnHeadingStyle = ReportFormats.columnHeading(workbook);
			CellStyle cellAlignment = ReportFormats.setCellAlignment(workbook, ALIGN_RIGHT);
			cellAlignment.setDataFormat(workbook.createDataFormat().getFormat("0.00"));

			CellStyle cellAlignment2 = ReportFormats.setCellAlignment(workbook, ALIGN_RIGHT);

			row = sheet.createRow(rowCount);
			cell = row.createCell(colCount);
			sheet.addMergedRegion(new CellRangeAddress(rowCount, rowCount, colCount, colCount + headings.length - 1));
			cell.setCellValue(companyname);
			cell.setCellStyle(headingCellStyle);
			rowCount++;

			row = sheet.createRow(rowCount);
			cell = row.createCell(colCount);
			sheet.addMergedRegion(new CellRangeAddress(rowCount, rowCount, colCount, colCount + headings.length - 1));
			cell.setCellValue("Dispatch Detail Statement");
			cell.setCellStyle(headingCellStyle);
			rowCount++;

			row = sheet.createRow(rowCount);
			cell = row.createCell(colCount);
			sheet.addMergedRegion(new CellRangeAddress(rowCount, rowCount, colCount, colCount + headings.length - 1));
			cell.setCellValue("Period: " + MedicoResources.convertUtilDateToString_DD_MM_YYYY_(bean.getStartDate())
					+ " to " + MedicoResources.convertUtilDateToString_DD_MM_YYYY_(bean.getEndDate()));
			cell.setCellStyle(headingCellStyle);

			rowCount += 1;
			row = sheet.createRow(rowCount);

			for (String heading : headings) {
				cell = ReportFormats.cell(row, colCount, heading, columnHeadingStyle);
				colCount++;
			}

			rowCount += 1;
			colCount = 0;

			row = sheet.createRow(rowCount);
			cell = row.createCell(colCount);
			sheet.addMergedRegion(new CellRangeAddress(rowCount, rowCount, colCount, colCount + 13));
			cell.setCellValue("Master Challan details");
			cell.setCellStyle(headingCellStyle);

			cell = row.createCell(colCount + 14);
			sheet.addMergedRegion(new CellRangeAddress(rowCount, rowCount, colCount + 14, colCount + 33));
			cell.setCellValue("Individual Challan details");
			cell.setCellStyle(headingCellStyle);

			cell = row.createCell(colCount + 34);
			sheet.addMergedRegion(new CellRangeAddress(rowCount, rowCount, colCount + 34, colCount + 47));
			cell.setCellValue("Product details");
			cell.setCellStyle(headingCellStyle);

			sheet.groupRow(0, 2);
			sheet.createFreezePane(7, 5);

			rowCount += 1;
			int count = 1;
			Iterator<DispatchDetailReportRevised_VET> dataiterator = list.iterator();
			DispatchDetailReportRevised_VET data = null;
			// for (DispatchDetailReportRevised data : list) {
			while (dataiterator.hasNext()) {
				colCount = 0;
				data = dataiterator.next();
				row = sheet.createRow(rowCount);
				cell = ReportFormats.cell(row, colCount, String.valueOf(count), cellAlignment);
				colCount++;

				cell = ReportFormats.cell(row, colCount, data.getWarehouse(), null);
				colCount++;

				cell = ReportFormats.cell(row, colCount, data.getDiv_disp_nm(), null);
				colCount++;

				cell = ReportFormats.cell(row, colCount, data.getDiv_map_cd(), null);
				colCount++;

				cell = ReportFormats.cell(row, colCount, data.getMonth_of_use(), null);
				colCount++;

				cell = ReportFormats.cell(row, colCount, data.getSumdsp_challan_no(), null);
				colCount++;

				cell = ReportFormats.cell(row, colCount, data.getSumdsp_challan_dt(), null);
				colCount++;

				cell = ReportFormats.cell(row, colCount, data.getSumdsp_destination(), null);
				colCount++;

				cell = ReportFormats.cell(row, colCount, data.getState_name(), null);
				colCount++;

				cell = ReportFormats.cellNum(row, colCount,
						data.getSumdsp_total_goods_val() == null ? 0l : data.getSumdsp_total_goods_val().doubleValue(),
						cellAlignment);
				colCount++;

				cell = ReportFormats.cell(row, colCount, data.getSumdsp_transporter(), null);
				colCount++;

				cell = ReportFormats.cell(row, colCount, data.getSumdsp_lr_no(), null);
				colCount++;

				cell = ReportFormats.cell(row, colCount, data.getSumdsp_lr_dt(), null);
				colCount++;

				cell = ReportFormats.cellNum(row, colCount,
						data.getSumdsp_totcases() == null ? 0l : data.getSumdsp_totcases().doubleValue(),
						cellAlignment2);
				colCount++;

				cell = ReportFormats.cellNum(row, colCount,
						data.getSum_dsp_act_wt() == null ? 0l : data.getSum_dsp_act_wt().doubleValue(), cellAlignment);
				colCount++;

				cell = ReportFormats.cellNum(row, colCount,
						data.getSum_dsp_bill_wt() == null ? 0l : data.getSum_dsp_bill_wt().doubleValue(),
						cellAlignment);
				colCount++;

				cell = ReportFormats.cell(row, colCount, data.getDspfstaff_employeeno(), null);
				colCount++;

				cell = ReportFormats.cell(row, colCount, data.getDspfstaff_displayname(), null);
				colCount++;

				cell = ReportFormats.cell(row, colCount, data.getRegion(), null);
				colCount++;

				cell = ReportFormats.cell(row, colCount, data.getDspfstaff_desg(), null);
				colCount++;

				cell = ReportFormats.cell(row, colCount, data.getMobile(), null);
				colCount++;

				cell = ReportFormats.cell(row, colCount, data.getDm_name(), null);
				colCount++;

				cell = ReportFormats.cell(row, colCount, data.getTerr_code(), null);
				colCount++;

				// cell = ReportFormats.cell(row, colCount, data.getPosition(), null);
				// colCount++;

				cell = ReportFormats.cell(row, colCount, data.getTerr_name(), null);
				colCount++;

				cell = ReportFormats.cell(row, colCount, data.getDsp_challan_no(), null);
				colCount++;

				cell = ReportFormats.cell(row, colCount, data.getDsp_challan_dt(), null);
				colCount++;

				cell = ReportFormats.cellNum(row, colCount,
						data.getDsp_total_goods_val() == null ? 0l : data.getDsp_total_goods_val().doubleValue(),
						cellAlignment);
				colCount++;

				cell = ReportFormats.cell(row, colCount, data.getDsp_transporter(), null);
				colCount++;
				

				

				cell = ReportFormats.cell(row, colCount, data.getDsp_lr_no(), null);
				colCount++;

				cell = ReportFormats.cell(row, colCount, data.getDsp_lr_dt(), null);
				colCount++;

				cell = ReportFormats.cellNum(row, colCount,
						data.getDsp_cases() == null ? 0l : data.getDsp_cases().doubleValue(), cellAlignment2);
				colCount++;

				cell = ReportFormats.cellNum(row, colCount,
						data.getDsp_act_wt() == null ? 0l : data.getDsp_act_wt().doubleValue(), cellAlignment);
				colCount++;

				cell = ReportFormats.cellNum(row, colCount,
						data.getDsp_bill_wt() == null ? 0l : data.getDsp_bill_wt().doubleValue(), cellAlignment);
				colCount++;

				cell = ReportFormats.cell(row, colCount, data.getSmp_prod_type(), null);
				colCount++;

				cell = ReportFormats.cell(row, colCount, data.getSmp_prod_name(), null);
				colCount++;

				cell = ReportFormats.cell(row, colCount, data.getSmp_prod_cd(), null);
				colCount++;

				cell = ReportFormats.cell(row, colCount, data.getBatch_no(), null);
				colCount++;

				cell = ReportFormats.cell(row, colCount, data.getBatch_expiry_dt(), null);
				colCount++;

				cell = ReportFormats.cellNum(row, colCount,
						data.getDspdtl_disp_qty() == null ? 0l : data.getDspdtl_disp_qty().doubleValue(),
						cellAlignment);
				colCount++;

				cell = ReportFormats.cellNum(row, colCount,
						data.getDspdtl_rate() == null ? 0l : data.getDspdtl_rate().doubleValue(), cellAlignment);
				colCount++;

				cell = ReportFormats.cellNum(row, colCount,
						data.getValue() == null ? 0l : data.getValue().doubleValue(), cellAlignment);
				colCount++;

				cell = ReportFormats.cellNum(row, colCount,
						data.getReturn_qty() == null ? 0l : data.getReturn_qty().doubleValue(), cellAlignment);
				colCount++;

				cell = ReportFormats.cell(row, colCount, data.getTentative_delivery_date(), null);
				colCount++;

				cell = ReportFormats.cell(row, colCount, data.getActual_delivery_date(), null);
				colCount++;

				cell = ReportFormats.cell(row, colCount, data.getRecieved_by(), null);
				colCount++;

				cell = ReportFormats.cell(row, colCount, data.getCourier_comment(), null);
				colCount++;

				cell = ReportFormats.cell(row, colCount, data.getRemarks(), null);
				colCount++;

				cell = ReportFormats.cell(row, colCount, data.getEmer_dtd_supplied_to(), null);
				colCount++;

				cell = ReportFormats.cell(row, colCount, data.getTeam_code(), null);
				colCount++;
				
				cell = ReportFormats.cell(row, colCount, data.getSrt_number(), null);
				colCount++;
				
				cell = ReportFormats.cell(row, colCount, data.getSrt_date().trim().equals("1900-01-01")?"":data.getSrt_date(), null);
				colCount++;
				
				cell = ReportFormats.cell(row, colCount, data.getSrt_remark(), null);
				colCount++;
				
			

				rowCount++;
				count++;
				dataiterator.remove();

			}
			FileOutputStream fileOutputStream = new FileOutputStream(filepath);
			workbook.write(fileOutputStream);

			filename=usermasterservice.generateExcellock(filepath, filename,empIdd,".xlsx");

		} catch (Throwable e) {
			// TODO Auto-generated catch block
			// System.out.println("Error Occurred :" + new CodifyErrors().getErrorCode(e));// uncomment asneeded --;
		} finally {
			if (workbook != null) {
				workbook.close();
				workbook.dispose();
			}
		}
		return filename;
	}

	@Override
	public String genarate_ter_hyrarchy_Report(List<Ter_mst_hierarchy_report_bean> list, String companyname) {
		File ff = null;
		Workbook wwbook = null;
		StringBuffer path = null;
		String filename = null;
		try {
			SimpleDateFormat timeformat = new SimpleDateFormat("hh:mm:ss a");
			System.out.println("generateExcel ----------------------------------------------------------------------");
			long l = new Date().getTime();
			filename = "Territory_master_hierarch_list" + l + ".xlsx";
			String filePath = MedicoConstants.REPORT_FILE_PATH;
			 path = new StringBuffer(filePath).append("\\");
			path.append(filename);

			
			 ff=new File(path.toString());
			File file = new File(filePath);
			try {
				if (!file.exists()) {
					if (file.mkdirs()) {
					} else {
						throw new RuntimeException("Could not create directory");
					}
				}
				} catch (Exception e) {
					throw new RuntimeException();
				}
			/*
			 * WorkbookSettings ws = new WorkbookSettings(); ws.setLocale(new Locale("en",
			 * "EN")); wwbook = jxl.Workbook.createWorkbook(ff, ws); WritableSheet wsheet =
			 * wwbook.createSheet( "Allocation Sheet", 0);
			 */
		
			wwbook= new XSSFWorkbook();
			Sheet wsheet = wwbook.createSheet("Territory Master Hierarchy List ");
			wsheet.createFreezePane( 0, 4);
			
			Font font = wwbook.createFont();
			font.setFontHeightInPoints((short)8);
		    font.setFontName("ARIAL");
		    
		    Font font2 = wwbook.createFont();	
			font2.setFontHeightInPoints((short)14);	
			font2.setBold(true);	
		    font2.setFontName("ARIAL");
		    
			CellStyle alignment = wwbook.createCellStyle();
			alignment.setWrapText(true);
			alignment.setBorderBottom(BorderStyle.THIN);
			alignment.setBorderLeft(BorderStyle.THIN);
			alignment.setBorderTop(BorderStyle.THIN);
			alignment.setBorderRight(BorderStyle.THIN);
			alignment.setFont(font);
			//alignment.setWrapText(false);
			alignment.setAlignment(HorizontalAlignment.CENTER);
			alignment.setVerticalAlignment(VerticalAlignment.CENTER);
			alignment.setFillForegroundColor(HSSFColor.HSSFColorPredefined.GREY_25_PERCENT.getIndex());
			alignment.setFillPattern(FillPatternType.SOLID_FOREGROUND);
		

			CellStyle alignment2 = wwbook.createCellStyle();	
			alignment2.setFont(font2);	
			alignment2.setWrapText(false);	
			alignment2.setAlignment(HorizontalAlignment.CENTER);	
			alignment2.setVerticalAlignment(VerticalAlignment.CENTER);	
			alignment2.setFillForegroundColor(HSSFColor.HSSFColorPredefined.LIGHT_YELLOW.getIndex());	
			alignment2.setFillPattern(FillPatternType.SOLID_FOREGROUND);
			
			
		//	jxl.write.Label header;
	//		jxl.write.Number number;
			

			CellStyle lvl4Format = wwbook.createCellStyle();
			lvl4Format.setWrapText(true);
			lvl4Format.setFillForegroundColor(HSSFColor.HSSFColorPredefined.LIGHT_GREEN.getIndex());
			lvl4Format.setFillPattern(FillPatternType.SOLID_FOREGROUND);
			lvl4Format.setBorderBottom(BorderStyle.THIN);
			lvl4Format.setBorderLeft(BorderStyle.THIN);
			lvl4Format.setBorderTop(BorderStyle.THIN);
			lvl4Format.setBorderRight(BorderStyle.THIN);
			lvl4Format.setFont(font);
			

			CellStyle lvl3Format = wwbook.createCellStyle();
			lvl3Format.setWrapText(true);
			lvl3Format.setFillForegroundColor(HSSFColor.HSSFColorPredefined.SKY_BLUE.getIndex());
			lvl3Format.setFillPattern(FillPatternType.SOLID_FOREGROUND);
			lvl3Format.setBorderBottom(BorderStyle.THIN);
			lvl3Format.setBorderLeft(BorderStyle.THIN);
			lvl3Format.setBorderTop(BorderStyle.THIN);
			lvl3Format.setBorderRight(BorderStyle.THIN);
			lvl3Format.setFont(font);
			//lvl3Format.setWrapText(false);

			CellStyle lvl2Format = wwbook.createCellStyle();
			lvl2Format.setWrapText(true);
			lvl2Format.setFillForegroundColor(HSSFColor.HSSFColorPredefined.LIGHT_YELLOW.getIndex());
			lvl2Format.setFillPattern(FillPatternType.SOLID_FOREGROUND);
			lvl2Format.setBorderBottom(BorderStyle.THIN);
			lvl2Format.setBorderLeft(BorderStyle.THIN);
			lvl2Format.setBorderTop(BorderStyle.THIN);
			lvl2Format.setBorderRight(BorderStyle.THIN);
			lvl2Format.setFont(font);
			//lvl2Format.setWrapText(false);

			CellStyle lvl1Format =wwbook.createCellStyle();
			lvl1Format.setWrapText(true);
			lvl1Format.setFillForegroundColor(HSSFColor.HSSFColorPredefined.WHITE.getIndex());
			lvl1Format.setFillPattern(FillPatternType.SOLID_FOREGROUND);
			lvl1Format.setBorderBottom(BorderStyle.THIN);
			lvl1Format.setBorderLeft(BorderStyle.THIN);
			lvl1Format.setBorderTop(BorderStyle.THIN);
			lvl1Format.setBorderRight(BorderStyle.THIN);
			lvl1Format.setFont(font);
			//lvl1Format.setWrapText(false);

			int col = 0, row = 0;
			
			XSSFRow hrow = (XSSFRow) wsheet.createRow(row);
			
			XSSFCell cell = hrow.createCell(col);
			
			cell.setCellStyle(alignment2);	
			cell.setCellValue(companyname);	
			wsheet.addMergedRegion(new CellRangeAddress(row, row, col,col+13));	
			row++;	
				
			hrow = (XSSFRow) wsheet.createRow(row);	
			cell = hrow.createCell(col);	
			cell.setCellStyle(alignment2);	
			cell.setCellValue("Territory Master Hierarchy List");	
			wsheet.addMergedRegion(new CellRangeAddress(row, row, col,col+13));	
			row++;	
			
			hrow = (XSSFRow) wsheet.createRow(row);	
			cell = hrow.createCell(col);
			cell.setCellValue(MedicoResources.convertUtilDateToString_DD_MM_YYYY(new Date())+" "+timeformat.format(new Date()));
			wsheet.addMergedRegion(new CellRangeAddress(row, row, col,col+13));	
			cell.setCellStyle(alignment2);
			row++;	
			
//			hrow = (XSSFRow) wsheet.createRow(row);	
//			cell = hrow.createCell(col);	
//			cell.setCellStyle(alignment);	
////			cell.setCellValue(output.format(output.parse(frmDate))+" to "+ output.format(output.parse(toDate)));	
//			cell.setCellValue("");	
//			wsheet.addMergedRegion(new CellRangeAddress(row, row, col,col+14));	
//			row++;	
				
			hrow = (XSSFRow) wsheet.createRow(row);			
		
			
			cell = hrow.createCell(col);
			cell.setCellStyle(alignment);
			cell.setCellValue(" Division Name");
			col++;
			
			/*int widthInChars = 10;
			wsheet.setColumnView(col, widthInChars);*/
			cell = hrow.createCell(col);
			cell.setCellStyle(alignment);
			cell.setCellValue("Territory Id");
			col++;
			
			cell = hrow.createCell(col);
			cell.setCellStyle(alignment);
			cell.setCellValue("Territory Code");
			col++;
		
			cell = hrow.createCell(col);
			cell.setCellStyle(alignment);
			cell.setCellValue("Level Code ");
			col++;
			
			
			cell = hrow.createCell(col);
			cell.setCellStyle(alignment);
			cell.setCellValue("Level Name ");
			col++;
			
			
			cell = hrow.createCell(col);
			cell.setCellStyle(alignment);
			cell.setCellValue(" Territory Name");
			col++;
		
			cell = hrow.createCell(col);
			cell.setCellStyle(alignment);
			cell.setCellValue(" ABM Terr Name ");
			col++;
			
			cell = hrow.createCell(col);
			cell.setCellStyle(alignment);
			cell.setCellValue("RBM Terr Name");
			col++;
		
			cell = hrow.createCell(col);
			cell.setCellStyle(alignment);
			cell.setCellValue("ZBM Terr Name");
			col++;
			
	
			cell = hrow.createCell(col);
			cell.setCellStyle(alignment);
			cell.setCellValue("STATE NAME ");
			col++;
			
			
			cell = hrow.createCell(col);
			cell.setCellStyle(alignment);
			cell.setCellValue("ZONE NAME");
			col++;
			
			
		//	int widthInChars = 12;

			/*wsheet.addCell(new Label(col++, row, "Designation",alignment));
			int widthInChars = 25;
			wsheet.setColumnView(col, widthInChars);*/

			cell = hrow.createCell(col);
			cell.setCellStyle(alignment);
			cell.setCellValue("  HQ NAME ");
			col++;
			
			cell = hrow.createCell(col);
			cell.setCellStyle(alignment);
			cell.setCellValue("CFA 1");
			col++;
			
			cell = hrow.createCell(col);
			cell.setCellStyle(alignment);
			cell.setCellValue("CFA 2");
			col++;
		
	
			
		

			
			
			col = 0;

			
			String div_name="";
			for (Ter_mst_hierarchy_report_bean summary : list) {
				if(!div_name.equals(summary.getTerr_div_name().trim()) && !div_name.equals("")) {
			
					
//					for(int j=0;j<1;j++) {
//						for(int i=0;i<=13;i++) {
//							cell = hrow.createCell(i);
//							cell.setCellStyle(null);
//							cell.setCellValue("");
//				
//							col++;
//						}
//						row++;
//						
//						}
					row+=2;
					
					row++;
					hrow = (XSSFRow) wsheet.createRow(row);
					col = 0;
					CellStyle current_style = wwbook.createCellStyle();

					if (Integer.valueOf(summary.getTerr_level_code()) == 4) {
						current_style = lvl4Format;
					}
					else if (Integer.valueOf(summary.getTerr_level_code()) == 3) {
						current_style = lvl3Format;
					} else if (Integer.valueOf(summary.getTerr_level_code()) == 2) {
						current_style = lvl2Format;
					} else {
						current_style = lvl1Format;
					}
					
						
						cell = hrow.createCell(col);
						cell.setCellStyle(current_style);
						cell.setCellValue(summary.getTerr_div_name());
						col++;
						
						cell = hrow.createCell(col);
						cell.setCellStyle(current_style);
						cell.setCellValue(summary.getTerr_id());
						col++;
									
						cell = hrow.createCell(col);
						cell.setCellStyle(current_style);
						cell.setCellValue(summary.getTerr_code());
						col++;			
						
						cell = hrow.createCell(col);
						cell.setCellStyle(current_style);
						cell.setCellValue( summary.getTerr_level_code());
						col++;		
						
						
						cell = hrow.createCell(col);
						cell.setCellStyle(current_style);
						cell.setCellValue( summary.getLevel_name());
						col++;	
						
						cell = hrow.createCell(col);
						cell.setCellStyle(current_style);
						cell.setCellValue(summary.getTerr_name());
						col++;
						
						cell = hrow.createCell(col);
						cell.setCellStyle(current_style);
						cell.setCellValue(summary.getAbm_terr_name());
						col++;
					
						
						cell = hrow.createCell(col);
						cell.setCellStyle(current_style);
						cell.setCellValue(summary.getRbm_terr_name());
						col++;
					
						cell = hrow.createCell(col);
						cell.setCellStyle(current_style);
						cell.setCellValue(summary.getZbm_terr_name());
						col++;
								
						cell = hrow.createCell(col);
						cell.setCellStyle(current_style);
						cell.setCellValue(summary.getState_name());
						col++;
						
						
						cell = hrow.createCell(col);
						cell.setCellStyle(current_style);
						cell.setCellValue(summary.getZone_name());
						col++;
					
						cell = hrow.createCell(col);
						cell.setCellStyle(current_style);
						cell.setCellValue(summary.getHq_name());
						col++;
						
						cell = hrow.createCell(col);
						cell.setCellStyle(current_style);
						cell.setCellValue(summary.getCfa_1());
						col++;
						
						cell = hrow.createCell(col);
						cell.setCellStyle(current_style);
						cell.setCellValue( summary.getCfa_2());
						col++;

					
			
					
					
				}else {
					
				
				row++;
				hrow = (XSSFRow) wsheet.createRow(row);
				col = 0;
				CellStyle current_style = wwbook.createCellStyle();

				if (Integer.valueOf(summary.getTerr_level_code()) == 4) {
					current_style = lvl4Format;
				}
				else if (Integer.valueOf(summary.getTerr_level_code()) == 3) {
					current_style = lvl3Format;
				} else if (Integer.valueOf(summary.getTerr_level_code()) == 2) {
					current_style = lvl2Format;
				} else {
					current_style = lvl1Format;
				}
				
					
					cell = hrow.createCell(col);
					cell.setCellStyle(current_style);
					cell.setCellValue(summary.getTerr_div_name());
					col++;
					
					cell = hrow.createCell(col);
					cell.setCellStyle(current_style);
					cell.setCellValue(summary.getTerr_id());
					col++;
								
					cell = hrow.createCell(col);
					cell.setCellStyle(current_style);
					cell.setCellValue(summary.getTerr_code());
					col++;			
					
					cell = hrow.createCell(col);
					cell.setCellStyle(current_style);
					cell.setCellValue( summary.getTerr_level_code());
					col++;		
					
					
					cell = hrow.createCell(col);
					cell.setCellStyle(current_style);
					cell.setCellValue( summary.getLevel_name());
					col++;	
					
					cell = hrow.createCell(col);
					cell.setCellStyle(current_style);
					cell.setCellValue(summary.getTerr_name());
					col++;
					
					cell = hrow.createCell(col);
					cell.setCellStyle(current_style);
					cell.setCellValue(summary.getAbm_terr_name());
					col++;
				
					
					cell = hrow.createCell(col);
					cell.setCellStyle(current_style);
					cell.setCellValue(summary.getRbm_terr_name());
					col++;
				
					cell = hrow.createCell(col);
					cell.setCellStyle(current_style);
					cell.setCellValue(summary.getZbm_terr_name());
					col++;
							
					cell = hrow.createCell(col);
					cell.setCellStyle(current_style);
					cell.setCellValue(summary.getState_name());
					col++;
					
					
					cell = hrow.createCell(col);
					cell.setCellStyle(current_style);
					cell.setCellValue(summary.getZone_name());
					col++;
				
					cell = hrow.createCell(col);
					cell.setCellStyle(current_style);
					cell.setCellValue(summary.getHq_name());
					col++;
					
					cell = hrow.createCell(col);
					cell.setCellStyle(current_style);
					cell.setCellValue(summary.getCfa_1());
					col++;
					
					cell = hrow.createCell(col);
					cell.setCellStyle(current_style);
					cell.setCellValue( summary.getCfa_2());
					col++;
					
				}
				
				div_name=summary.getTerr_div_name().trim();
				
				
			}
			
			 FileOutputStream fileOut = new FileOutputStream(path.toString());
			 wwbook.write(fileOut);
			 
				filename=usermasterservice.generateExcellock(path.toString(), filename,"",".xlsx");


			 
			    fileOut.close();
			    wwbook.close();
		
			System.out.println("Excel Created");
		} catch (Exception e) {
			System.out.println("Error Occurred :" +e.getMessage());// uncomment asneeded --;
			
		} 
		
		return filename;
	}
	
	
	
	
	
	
	
	
	
	
	
	
}
