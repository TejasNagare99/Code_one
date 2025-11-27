package com.excel.service;

import java.awt.Color;
import java.io.File;
import java.io.FileOutputStream;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.apache.poi.ss.usermodel.BorderStyle;
import org.apache.poi.ss.usermodel.CreationHelper;
import org.apache.poi.ss.usermodel.DataFormat;
import org.apache.poi.ss.usermodel.FillPatternType;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.ss.util.RegionUtil;
import org.apache.poi.xssf.usermodel.DefaultIndexedColorMap;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.apache.poi.xssf.usermodel.XSSFColor;
import org.apache.poi.xssf.usermodel.XSSFFont;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Service;

import com.excel.model.ArticleSchemeExceptionReport;
import com.excel.utility.MedicoConstants;
import com.excel.utility.MedicoResources;
import com.excel.utility.ReportFormats;

@Service
public class ArticleSchemeExceptionReportServiceImpl implements ArticleSchemeExceptionReportService, MedicoConstants {

	@Override
	public String generateArticleExceptionSchemeReport(List<ArticleSchemeExceptionReport> list, String companyname,
			String fromDate, String toDate, String is_correction) throws Exception{

		String filename = "";
		if (is_correction != null && is_correction.equalsIgnoreCase("C")) {
			filename = "articleSchemeCorrection" + new Date().getTime() + ".xlsx";

		} else {
			filename = "articleSchemeException" + new Date().getTime() + ".xlsx";
		}

		String filepath = REPORT_FILE_PATH + filename;
		System.out.println("file path :::" + filepath);
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

			XSSFSheet sheet = null;

			if (is_correction != null && is_correction.equalsIgnoreCase("C")) {
				sheet = workbook.createSheet("Article Scheme Correction Report");
			} else {
				sheet = workbook.createSheet("Article Scheme Exception Report");
			}

			XSSFRow row = null;
			XSSFCell cell = null;
			int rowCount = 0;
			int colCount = 0;

			List<String> headings = new ArrayList<>();

			// headings.add("Sl.No.");
			headings.add("AP");
			headings.add("Plant Code");
			headings.add("Company");
			headings.add("CFA Location");
			headings.add("SAP Invoice No.");
			headings.add("SAP Invoice Date");
			headings.add("SAP Cust Code");
			headings.add("Customer Name");
			headings.add("City");
			headings.add("Code");
			headings.add("SKU Name");
			headings.add("Billed Qty");
			headings.add("Free Qty");
			headings.add("Rate");
			headings.add("Value");
			headings.add("Article Request Date");
			headings.add("Entered SAP Inv No");
			headings.add("Entered SAP Inv Dt");
			headings.add("Billed Qty");
			headings.add("Rate");
			headings.add("Value");
			headings.add("Error in Billed Qty");
			headings.add("Eligibility as per SAP Invoice");
			headings.add("Request Qty generated");
			headings.add("Discrepancy in Article Qty");
			headings.add("Delivery Challan No.");
			headings.add("Delivery Challan Date");
			headings.add("EInvoice ACK No.");
			headings.add("EInvoice ACK Date");
			headings.add("Discrepancy Type");
			headings.add("Correction Challan");
			headings.add("Correction Qty");

			XSSFFont headerFont = null;

			XSSFCellStyle compNameStyle = workbook.createCellStyle();
			compNameStyle.setAlignment(HorizontalAlignment.LEFT);
			headerFont = workbook.createFont();
			headerFont.setFontHeightInPoints((short) 22);
			headerFont.setBold(true);
			compNameStyle.setFont(headerFont);

			XSSFCellStyle reportNameStyle = workbook.createCellStyle();
			headerFont = workbook.createFont();
			headerFont.setFontHeightInPoints((short) 16);
			headerFont.setBold(true);
			reportNameStyle.setFont(headerFont);

			XSSFCellStyle columnHeadingStyle = workbook.createCellStyle();
			XSSFColor myColor = new XSSFColor(new java.awt.Color(180, 198, 231), new DefaultIndexedColorMap());
			headerFont = workbook.createFont();
			headerFont.setBold(true);
			columnHeadingStyle.setFont(headerFont);
			columnHeadingStyle.setFillForegroundColor(myColor);
			columnHeadingStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);
			columnHeadingStyle.setBorderBottom(BorderStyle.THIN);
			columnHeadingStyle.setBorderLeft(BorderStyle.THIN);
			columnHeadingStyle.setBorderTop(BorderStyle.THIN);
			columnHeadingStyle.setBorderRight(BorderStyle.THIN);
			columnHeadingStyle.setAlignment(HorizontalAlignment.CENTER);

			XSSFCellStyle dateStyle = workbook.createCellStyle();
			CreationHelper creationHelper = workbook.getCreationHelper();
			dateStyle.setDataFormat(creationHelper.createDataFormat().getFormat("dd-MM-yyyy"));
			
			XSSFCellStyle redstyle = workbook.createCellStyle();
			XSSFFont font = workbook.createFont();
	        font.setColor(new XSSFColor( Color.decode("#FF0000")));
	        redstyle.setFont(font);
	        
	        DataFormat format = workbook.createDataFormat();
	        XSSFCellStyle decimalWithoutColor = workbook.createCellStyle();
			decimalWithoutColor.setAlignment(HorizontalAlignment.RIGHT);
			decimalWithoutColor.setDataFormat(format.getFormat("0.00"));

			row = sheet.createRow(rowCount);
			cell = row.createCell(colCount);
			sheet.addMergedRegion(new CellRangeAddress(rowCount, rowCount, colCount, headings.size() - 1));
			cell.setCellValue(companyname);
			cell.setCellStyle(compNameStyle);
			rowCount++;

			row = sheet.createRow(rowCount);
			cell = row.createCell(colCount);
			sheet.addMergedRegion(new CellRangeAddress(rowCount, rowCount, colCount, headings.size() - 1));

			if (is_correction != null && is_correction.equalsIgnoreCase("C")) {
				cell.setCellValue("Article Correction Report");
			} else {
				cell.setCellValue("Audit Reconciliation Report - Article Scheme Requests Vs SAP Invoice Billed Items");
			}

			cell.setCellStyle(reportNameStyle);
			rowCount++;

			row = sheet.createRow(rowCount);
			cell = row.createCell(colCount);
			sheet.addMergedRegion(new CellRangeAddress(rowCount, rowCount, colCount, headings.size() - 1));
			cell.setCellValue("Period: " + fromDate + " to " + toDate);
			cell.setCellStyle(reportNameStyle);
			rowCount++;

			// headings

			row = sheet.createRow(rowCount);
			colCount = 0;

			cell = row.createCell(colCount);
			sheet.addMergedRegion(new CellRangeAddress(rowCount, rowCount, colCount, colCount + 3));
			cell.setCellValue("Location, Sales Period");
			cell.setCellStyle(columnHeadingStyle);
			colCount+=4;
			
			cell = row.createCell(colCount);
			sheet.addMergedRegion(new CellRangeAddress(rowCount, rowCount, colCount, colCount + 2));
			cell.setCellValue("SAP Invoice Details from Data");
			cell.setCellStyle(columnHeadingStyle);
			colCount+=3;
			
			cell = row.createCell(colCount);
			sheet.addMergedRegion(new CellRangeAddress(rowCount, rowCount, colCount, colCount + 1));
			cell.setCellValue("Customer Details");
			cell.setCellStyle(columnHeadingStyle);
			colCount+=2;

//			for (int i = 1; i <= 8; i++) {
//				cell = ReportFormats.cell(row, colCount, "", columnHeadingStyle);
//				colCount++;
//			}
			cell = row.createCell(colCount);
			sheet.addMergedRegion(new CellRangeAddress(rowCount, rowCount, colCount, colCount + 5));
			cell.setCellValue("Sales Product (SKU) Details as per SAP Invoice data");
			cell.setCellStyle(columnHeadingStyle);
			colCount+=6;
			

			cell = row.createCell(colCount);
			sheet.addMergedRegion(new CellRangeAddress(rowCount, rowCount, colCount, colCount + 6));
			cell.setCellValue("Article Request Details Entered by CFA");
			cell.setCellStyle(columnHeadingStyle);
			colCount+=7;

			for (int i = 1; i <= 3; i++) {
				cell = ReportFormats.cell(row, colCount, "", columnHeadingStyle);
				colCount++;
			}

//			cell = row.createCell(colCount);
//			sheet.addMergedRegion(new CellRangeAddress(rowCount, rowCount, colCount, colCount + 5));
//			cell.setCellValue("Entry by CFA for Article Request Eligibility");
//			cell.setCellStyle(columnHeadingStyle);
//			colCount+=5;

//			for (int i = 1; i <= 5; i++) {
//				cell = ReportFormats.cell(row, colCount, "", columnHeadingStyle);
//				colCount++;
//			}

			cell = row.createCell(colCount);
			sheet.addMergedRegion(new CellRangeAddress(rowCount, rowCount, colCount, colCount + 3));
			cell.setCellValue("Delivery Details	");
			cell.setCellStyle(columnHeadingStyle);
			colCount+=4;
			
			cell = row.createCell(colCount);
			sheet.addMergedRegion(new CellRangeAddress(rowCount, rowCount, colCount, colCount + 2));
			cell.setCellValue("Discrepancy and Remediation");
			cell.setCellStyle(columnHeadingStyle);
			colCount+=3;

//			for (int i = 1; i <= 7; i++) {
//				cell = ReportFormats.cell(row, colCount, "", columnHeadingStyle);
//				colCount++;
//			}
			rowCount++;

			row = sheet.createRow(rowCount);
			colCount = 0;

			for (String string : headings) {
				cell = ReportFormats.cell(row, colCount, string, columnHeadingStyle);
				colCount++;
			}
			rowCount++;

			boolean is_cor_row = false;
			for (ArticleSchemeExceptionReport obj : list) {

				is_cor_row = (obj.getCorr_ind() != null && obj.getCorr_ind().equalsIgnoreCase("Y")
						&& obj.getCorr_article_qty().compareTo(BigDecimal.ZERO) > 0) ? true : false;

//				if (is_cor_row)
//					obj = new ArticleSchemeExceptionReport(obj.getDsp_challan_no(), obj.getDsp_dt(),
//							"Shortage Corrected", obj.getCorr_ind(), obj.getCorr_article_qty(),
//							obj.getCorr_article_qty().longValue());

				row = sheet.createRow(rowCount);
				colCount = 0;

//				cell = ReportFormats.cellLong(row, colCount, obj.getRownum(), null);
//				colCount++;

				cell = ReportFormats.cell(row, colCount, obj.getAp(), null);
				colCount++;

				cell = ReportFormats.cell(row, colCount, obj.getSap_plant_cd(), null);
				colCount++;

				cell = ReportFormats.cell(row, colCount, obj.getCompany_cd(), null);
				colCount++;

				cell = ReportFormats.cell(row, colCount, obj.getCfa_location(), null);
				colCount++;

				cell = ReportFormats.cell(row, colCount, obj.getSap_inv_no(), null);
				colCount++;

				if (obj.getSap_inv_dt() == null || obj.getSap_inv_dt().trim().equals("")) {
					cell = ReportFormats.cell(row, colCount, "", null);
				} else {
					cell = ReportFormats.cellDate(row, colCount,
							MedicoResources.getDateformatYYYYDDMMHH(obj.getSap_inv_dt()), dateStyle);
				}
				colCount++;

				cell = ReportFormats.cell(row, colCount, obj.getSap_cust_cd(), null);
				colCount++;

				cell = ReportFormats.cell(row, colCount, obj.getCustomer_name(), null);
				colCount++;

				cell = ReportFormats.cell(row, colCount, obj.getCity(), null);
				colCount++;

				cell = ReportFormats.cell(row, colCount, obj.getSap_sale_prod_cd(), null);
				colCount++;

				cell = ReportFormats.cell(row, colCount, obj.getSap_sale_prod_nme(), null);
				colCount++;

				cell = ReportFormats.cellLong(row, colCount, obj.getBilled_qty() != null ? obj.getBilled_qty() : 0L,
						null);
				colCount++;

				cell = ReportFormats.cellLong(row, colCount, 0l, null);
				colCount++;

				cell = ReportFormats.cellNum(row, colCount,
						obj.getBilling_rate() != null ? obj.getBilling_rate().doubleValue() : 0.00, decimalWithoutColor);
				colCount++;

				cell = ReportFormats.cellNum(row, colCount,
						obj.getBilling_value() != null ? obj.getBilling_value().doubleValue() : 0.00, decimalWithoutColor);
				colCount++;
				
				if (obj.getArticle_req_date() == null || obj.getArticle_req_date().trim().equals("")) {
					cell = ReportFormats.cell(row, colCount, "", null);
				} else {
					cell = ReportFormats.cellDate(row, colCount,
							MedicoResources.getDateformatYMD(obj.getArticle_req_date()), dateStyle);
				}
				colCount++;

				cell = ReportFormats.cell(row, colCount, obj.getInvoice_no(), null);
				colCount++;

				if (obj.getInvoice_date() == null || obj.getInvoice_date().trim().equals("")) {
					cell = ReportFormats.cell(row, colCount, "", null);
				} else {
					cell = ReportFormats.cellDate(row, colCount,
							MedicoResources.getDateformatYMD(obj.getInvoice_date()), dateStyle);
				}
				colCount++;

				cell = ReportFormats.cellLong(row, colCount,
						obj.getArticle_sale_bill_qty_entered() != null ? obj.getArticle_sale_bill_qty_entered() : 0L,
						null);
				colCount++;

				cell = ReportFormats.cellNum(row, colCount,
						obj.getArticle_rate() != null ? obj.getArticle_rate().doubleValue() : 0.00, decimalWithoutColor);
				colCount++;

				cell = ReportFormats.cellNum(row, colCount,obj.getArticle_value()!=null?
						obj.getArticle_value().doubleValue():0.00, decimalWithoutColor);
				colCount++;

				cell = ReportFormats.cellLong(row, colCount,obj.getErr_bill_qty()!=null?
						obj.getErr_bill_qty():0, null);
				colCount++;

				cell = ReportFormats.cellLong(row, colCount,obj.getCalc_request_qty()!=null?
						obj.getCalc_request_qty():0L, null);
				colCount++;

				cell = ReportFormats.cellLong(row, colCount, obj.getReq_article_qty(),is_cor_row? redstyle:null);
				colCount++;

				cell = ReportFormats.cellNum(row, colCount,obj.getArticle_qty_diff()!=null? 
						obj.getArticle_qty_diff().doubleValue():0.00, is_cor_row?redstyle:null);
				colCount++;

				cell = ReportFormats.cell(row, colCount, obj.getDsp_challan_no(), is_cor_row?redstyle:null);
				colCount++;

				cell = ReportFormats.cellDate(row, colCount, MedicoResources.getDateformatYYYYDDMMHH(obj.getDsp_dt()),
						dateStyle);
				colCount++;
				
				cell = ReportFormats.cell(row, colCount, obj.getTrn_ack_no(), is_cor_row?redstyle:null);
				colCount++;

				cell = ReportFormats.cellDate(row, colCount, MedicoResources.getDateformatYYYYDDMMHH(obj.getTrn_ack_date()),
						dateStyle);
				colCount++;

				cell = ReportFormats.cell(row, colCount, obj.getDiscrip_msg(), is_cor_row?redstyle:null);
				colCount++;

				cell = ReportFormats.cell(row, colCount, obj.getCorr_ind() != null ? obj.getCorr_ind() : "", null);
				colCount++;

				cell = ReportFormats.cellNum(row, colCount, obj.getCorr_article_qty().doubleValue(), null);
				colCount++;

				rowCount++;
			}
			sheet.createFreezePane(0, 5);
			
			//add borders to all merged regions
			MedicoConstants.addOuterBorderToMerged_XSSF(sheet,3,0);
			
			FileOutputStream fileOutputStream = new FileOutputStream(filepath);
			workbook.write(fileOutputStream);
		} finally {
			if (workbook != null) {
				workbook.close();
			}
		}

		return filename;
	}
}
