package com.excel.service;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import org.apache.poi.ss.usermodel.BorderStyle;
import org.apache.poi.ss.usermodel.CreationHelper;
import org.apache.poi.ss.usermodel.FillPatternType;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.VerticalAlignment;
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

import com.excel.bean.ReportBean;
import com.excel.bean.Sales_prod_details_bean;
import com.excel.model.ArticleSchemeSummaryReportCfa;
import com.excel.utility.MedicoConstants;
import com.excel.utility.ReportFormats;

@Service
public class SchemeSummaryOfCfaServiceImpl implements SchemeSummaryOfCfaService,MedicoConstants {
	@Override
	public String generateSchemeSummaryCfaLocationWise(List<ArticleSchemeSummaryReportCfa> list, String companyname, ReportBean bean) throws IOException {
		String filename = "SchemeSummaryCfaLocationWise" + new Date().getTime() + ".xlsx";
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
			XSSFSheet sheet = workbook.createSheet("Scheme Summary - CFA Location wise");
			
			XSSFRow row = null;
			XSSFCell cell = null;
			int rowCount = 0;
			int colCount = 0;
			int roughcount1 = 10;
			
			System.out.println("filepath " + filepath);
			String hexColor = "#653700";

		    int r = Integer.valueOf(hexColor.substring(1, 3), 16); 
		    int g = Integer.valueOf(hexColor.substring(3, 5), 16); 
		    int b = Integer.valueOf(hexColor.substring(5, 7), 16);

			XSSFCellStyle periodNameStyle = workbook.createCellStyle();
			XSSFColor xssfColor = new XSSFColor(new java.awt.Color(r,g,b), new DefaultIndexedColorMap());
			periodNameStyle.setAlignment(HorizontalAlignment.LEFT);
			XSSFFont headerFont2 = workbook.createFont();
			headerFont2.setFontHeightInPoints((short) 14);
			headerFont2.setColor(xssfColor);
			headerFont2.setBold(true);
			periodNameStyle.setFont(headerFont2);
			

			XSSFCellStyle compNameStyle = workbook.createCellStyle();
			compNameStyle.setAlignment(HorizontalAlignment.LEFT);
			XSSFColor myColor = new XSSFColor(new java.awt.Color(48, 84, 150), new DefaultIndexedColorMap());
			XSSFFont headerFont1 = workbook.createFont();
			headerFont1.setFontHeightInPoints((short) 18);
			headerFont1.setColor(myColor);
			headerFont1.setBold(true);
			compNameStyle.setFont(headerFont1);
			
			
			
			XSSFCellStyle reportNameStyle = workbook.createCellStyle();
			headerFont1 = workbook.createFont();
			headerFont1.setFontHeightInPoints((short) 16);
			headerFont1.setColor(myColor);
			headerFont1.setBold(true);
			reportNameStyle.setFont(headerFont1);
			
			XSSFCellStyle cellAlignment2 = workbook.createCellStyle();
			cellAlignment2.setDataFormat(workbook.createDataFormat().getFormat("0.00"));
			cellAlignment2.setAlignment(HorizontalAlignment.RIGHT);
			
			XSSFCellStyle dateStyle = workbook.createCellStyle();
	        CreationHelper creationHelper = workbook.getCreationHelper();
	        dateStyle.setDataFormat(creationHelper.createDataFormat().getFormat("dd-MM-yyyy"));
			
			row = sheet.createRow(rowCount);
			cell = row.createCell(colCount);
			sheet.addMergedRegion(new CellRangeAddress(rowCount, rowCount, colCount,roughcount1));
			cell.setCellValue(companyname);
			cell.setCellStyle(compNameStyle);
			rowCount++;
			
			row = sheet.createRow(rowCount);
			cell = row.createCell(colCount);
			sheet.addMergedRegion(new CellRangeAddress(rowCount, rowCount, colCount, roughcount1));
			cell.setCellValue("Scheme Summary - CFA Location wise");
			cell.setCellStyle(reportNameStyle);
			rowCount++;
			
//			colCount = 0;
//			row = sheet.createRow(rowCount);
//			cell = row.createCell(colCount);
//			sheet.addMergedRegion(new CellRangeAddress(rowCount, rowCount, colCount, roughcount1));
//			cell.setCellValue("Period:" /* + fromDate + " " + "to" + " "+ toDate */);
//			cell.setCellStyle(periodNameStyle);
			
			XSSFCellStyle paleBlueStyle = workbook.createCellStyle();
			myColor = new XSSFColor(new java.awt.Color(180, 198, 231), new DefaultIndexedColorMap());
			paleBlueStyle.setFillForegroundColor(myColor);
			paleBlueStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);
			
			paleBlueStyle.setAlignment(HorizontalAlignment.CENTER);
			paleBlueStyle.setVerticalAlignment(VerticalAlignment.CENTER);
			headerFont1 = workbook.createFont();
			headerFont1.setFontHeightInPoints((short) 12);
			headerFont1.setBold(true);
			paleBlueStyle.setFont(headerFont1);
			paleBlueStyle.setBorderTop(BorderStyle.THIN);
			paleBlueStyle.setTopBorderColor(IndexedColors.BLACK.index);
			paleBlueStyle.setBorderBottom(BorderStyle.THIN);
			paleBlueStyle.setBottomBorderColor(IndexedColors.BLACK.index);
			paleBlueStyle.setBorderLeft(BorderStyle.THIN);
			paleBlueStyle.setLeftBorderColor(IndexedColors.BLACK.index);
			paleBlueStyle.setBorderRight(BorderStyle.THIN);
			paleBlueStyle.setRightBorderColor(IndexedColors.BLACK.index);
			
			XSSFCellStyle paleGreenStyle = workbook.createCellStyle();
			myColor = new XSSFColor(new java.awt.Color(198, 224, 180), new DefaultIndexedColorMap());
			paleGreenStyle.setFillForegroundColor(myColor);
			paleGreenStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);
			
			paleGreenStyle.setAlignment(HorizontalAlignment.RIGHT);
			paleGreenStyle.setVerticalAlignment(VerticalAlignment.CENTER);
			headerFont1 = workbook.createFont();
			headerFont1.setFontHeightInPoints((short) 12);
			headerFont1.setBold(true);
			paleGreenStyle.setFont(headerFont1);
			
			XSSFCellStyle paleGreenStyleCenter = workbook.createCellStyle();
			myColor = new XSSFColor(new java.awt.Color(198, 224, 180), new DefaultIndexedColorMap());
			paleGreenStyleCenter.setFillForegroundColor(myColor);
			paleGreenStyleCenter.setFillPattern(FillPatternType.SOLID_FOREGROUND);
			
			paleGreenStyleCenter.setAlignment(HorizontalAlignment.CENTER);
			paleGreenStyleCenter.setVerticalAlignment(VerticalAlignment.CENTER);
			headerFont1 = workbook.createFont();
			headerFont1.setFontHeightInPoints((short) 12);
			headerFont1.setBold(true);
			paleGreenStyleCenter.setFont(headerFont1);
			paleGreenStyleCenter.setBorderTop(BorderStyle.THIN);
			paleGreenStyleCenter.setTopBorderColor(IndexedColors.BLACK.index);
			paleGreenStyleCenter.setBorderBottom(BorderStyle.THIN);
			paleGreenStyleCenter.setBottomBorderColor(IndexedColors.BLACK.index);
			paleGreenStyleCenter.setBorderLeft(BorderStyle.THIN);
			paleGreenStyleCenter.setLeftBorderColor(IndexedColors.BLACK.index);
			paleGreenStyleCenter.setBorderRight(BorderStyle.THIN);
			paleGreenStyleCenter.setRightBorderColor(IndexedColors.BLACK.index);
			
			XSSFCellStyle paleGreenStyleLeft = workbook.createCellStyle();
			myColor = new XSSFColor(new java.awt.Color(198, 224, 180), new DefaultIndexedColorMap());
			paleGreenStyleLeft.setFillForegroundColor(myColor);
			paleGreenStyleLeft.setFillPattern(FillPatternType.SOLID_FOREGROUND);
			
			paleGreenStyleLeft.setAlignment(HorizontalAlignment.LEFT);
			paleGreenStyleLeft.setVerticalAlignment(VerticalAlignment.CENTER);
			headerFont1 = workbook.createFont();
			headerFont1.setFontHeightInPoints((short) 12);
			headerFont1.setBold(true);
			paleGreenStyleLeft.setFont(headerFont1);
			
			XSSFFont headerFont = workbook.createFont();
			headerFont.setFontHeightInPoints((short) 12);
			headerFont.setBold(true);
			headerFont.setColor(IndexedColors.BLACK.getIndex()); 
			
			XSSFCellStyle paleBlueStyles = workbook.createCellStyle();
			paleBlueStyles.setFillForegroundColor(myColor);
			paleBlueStyles.setFillPattern(FillPatternType.SOLID_FOREGROUND);
			paleBlueStyles.setFont(headerFont);
			paleBlueStyles.setDataFormat(workbook.createDataFormat().getFormat("0.00"));
			paleBlueStyles.setAlignment(HorizontalAlignment.RIGHT);
			
			XSSFCellStyle paleBlueStylesTotal = workbook.createCellStyle();
			paleBlueStylesTotal.setFillForegroundColor(myColor);
			paleBlueStylesTotal.setFillPattern(FillPatternType.SOLID_FOREGROUND);
			paleBlueStylesTotal.setFont(headerFont);
			paleBlueStylesTotal.setAlignment(HorizontalAlignment.LEFT);
			
//			String newCfaLocation = "";
//			String oldCfaLocation = "";
//			
//			String newTdsSchemeCode = "";
//			String oldTdsSchemeCode = "";
			
			Long totalSaleQty = 0L;
			BigDecimal totalSaleValue = BigDecimal.ZERO;
			Long totalArticleRedQty = 0L;
//			BigDecimal totalArtDspRate = BigDecimal.ZERO;
			BigDecimal totalArtRedValue = BigDecimal.ZERO;
			BigDecimal totalClosingStock = BigDecimal.ZERO;
			BigDecimal totalArtDspQty = BigDecimal.ZERO;
			BigDecimal totalArtDspVal = BigDecimal.ZERO;
			Long totalReqPendingQty = 0L;
			Long totalAvailableStock = 0L;
			
			colCount = 0;
			row = sheet.createRow(rowCount);
			
			cell = row.createCell(colCount);
			sheet.addMergedRegion(new CellRangeAddress(rowCount, rowCount+1, colCount, colCount));
			cell.setCellValue("Scheme Name");
			cell.setCellStyle(paleBlueStyle);
			colCount++;
			
			cell = row.createCell(colCount);
			sheet.addMergedRegion(new CellRangeAddress(rowCount, rowCount, colCount, colCount+1));
			cell.setCellValue("Scheme Validity");
			cell.setCellStyle(paleBlueStyle);
			colCount+=2;
			
			cell = row.createCell(colCount);
			sheet.addMergedRegion(new CellRangeAddress(rowCount, rowCount+1, colCount, colCount));
			cell.setCellValue("Article Name");
			cell.setCellStyle(paleBlueStyle);
			colCount+=1;
			
			rowCount++;
			colCount=1;			
			row = sheet.createRow(rowCount);
			
			cell = row.createCell(colCount);
			cell.setCellValue("Valid From");
			cell.setCellStyle(paleBlueStyle);
			colCount++;
			
			cell = row.createCell(colCount);
			cell.setCellValue("Valid To");
			cell.setCellStyle(paleBlueStyle);
			colCount++;
			
			rowCount++;
			colCount = 0;
			row = sheet.createRow(rowCount);
			
			cell = row.createCell(colCount);
			cell.setCellValue(bean.getTrd_scheme_name());
			colCount++;
			
			cell = row.createCell(colCount);
			cell.setCellValue(bean.getValid_from_dt());
			colCount++;
			
			cell = row.createCell(colCount);
			cell.setCellValue(bean.getValid_to_dt());
			colCount++;
			
			cell = row.createCell(colCount);
			cell.setCellValue(bean.getArticle_name());
			colCount++;
			
			rowCount++;
			colCount = 0;
			row = sheet.createRow(rowCount);
			
			cell = row.createCell(colCount);
			sheet.addMergedRegion(new CellRangeAddress(rowCount, rowCount, colCount, colCount+3));
			cell.setCellValue("Scheme Definition Details");
			cell.setCellStyle(paleGreenStyleCenter);
			colCount++;
			
			rowCount++;
			colCount = 0;
			row = sheet.createRow(rowCount);
			
			cell = row.createCell(colCount);
			cell.setCellValue("Sales SKU Name");
			cell.setCellStyle(paleGreenStyleCenter);
			colCount++;
			
			cell = row.createCell(colCount);
			cell.setCellValue("Brand");
			cell.setCellStyle(paleGreenStyleCenter);
			colCount++;
			
			cell = row.createCell(colCount);
			cell.setCellValue("Per Billed Qty");
			cell.setCellStyle(paleGreenStyleCenter);
			colCount++;
			
			cell = row.createCell(colCount);
			cell.setCellValue("Article Qty");
			cell.setCellStyle(paleGreenStyleCenter);
			colCount++;
			
			
			
			
			for( Sales_prod_details_bean sales_bean:bean.getSales_prod_details()) {
				rowCount++;
				colCount = 0;
				row = sheet.createRow(rowCount);
				
				
				cell = row.createCell(colCount);
				cell.setCellValue(sales_bean.getSale_prod_name());
				colCount++;
				
				cell = row.createCell(colCount);
				cell.setCellValue(sales_bean.getBrand());
				colCount++;
				
				cell = row.createCell(colCount);
//				cell.setCellValue(sales_bean.getBilled());
				ReportFormats.cellLong(row, colCount, Long.valueOf( sales_bean.getBilled()), cellAlignment2);
				colCount++;
				
				cell = row.createCell(colCount);
//				cell.setCellValue(sales_bean.getArticle_Qty());
				ReportFormats.cellLong(row, colCount, Long.valueOf( sales_bean.getArticle_Qty()), cellAlignment2);
				colCount++;
			
				
			}
			
		
			
			
			rowCount++;
			colCount = 0;
			row = sheet.createRow(rowCount);
			cell = row.createCell(colCount);
			sheet.addMergedRegion(new CellRangeAddress(rowCount, rowCount+1, colCount, colCount));
			cell.setCellValue("CFA Location Name");
			cell.setCellStyle(paleBlueStyle);
			colCount++;
			
			cell = row.createCell(colCount);
			sheet.addMergedRegion(new CellRangeAddress(rowCount, rowCount, colCount, colCount+1));
			cell.setCellValue("SAP Invoices Referenced");
			cell.setCellStyle(paleBlueStyle);
			colCount+=2;
			
			cell = row.createCell(colCount);
			sheet.addMergedRegion(new CellRangeAddress(rowCount, rowCount, colCount, colCount+2));
			cell.setCellValue("Article Requests Generated at CFA Locations");
			cell.setCellStyle(paleBlueStyle);
			colCount+=3;
			
			cell = row.createCell(colCount);
			cell.setCellValue("Current Stock");
			cell.setCellStyle(paleBlueStyle);
			colCount+=1;
			
			cell = row.createCell(colCount);
			sheet.addMergedRegion(new CellRangeAddress(rowCount, rowCount, colCount, colCount+1));
			cell.setCellValue("Articles Supplied");
			cell.setCellStyle(paleBlueStyle);
			colCount+=2;
			
			cell = row.createCell(colCount);
			sheet.addMergedRegion(new CellRangeAddress(rowCount, rowCount, colCount, colCount+1));
			cell.setCellValue("Pending Article Supplies");
			cell.setCellStyle(paleBlueStyle);
			colCount+=1;
			
			
			rowCount++;
			colCount=1;			
			row = sheet.createRow(rowCount);
			
			cell = row.createCell(colCount);
			cell.setCellValue("Sales SKU Qty");
			cell.setCellStyle(paleBlueStyle);
			colCount++;
			
			cell = row.createCell(colCount);
			cell.setCellValue("Sales SKU  Value");
			cell.setCellStyle(paleBlueStyle);
			colCount++;
			
			cell = row.createCell(colCount);
			cell.setCellValue("Article Request Qty");
			cell.setCellStyle(paleBlueStyle);
			colCount++;
			
			cell = row.createCell(colCount);
			cell.setCellValue("Article Rate");
			cell.setCellStyle(paleBlueStyle);
			colCount++;
			
			cell = row.createCell(colCount);
			cell.setCellValue("Article Request Value");
			cell.setCellStyle(paleBlueStyle);
			colCount++;
			
			cell = row.createCell(colCount);
			cell.setCellValue("Article Stock at CFA Locations");
			cell.setCellStyle(paleBlueStyle);
			colCount++;
			
			cell = row.createCell(colCount);
			cell.setCellValue("Article Supply Qty");
			cell.setCellStyle(paleBlueStyle);
			colCount++;
			
			cell = row.createCell(colCount);
			cell.setCellValue("Article Supply Value");
			cell.setCellStyle(paleBlueStyle);
			colCount++;
			
			cell = row.createCell(colCount);
			cell.setCellValue("Pending Article Requests");
			cell.setCellStyle(paleBlueStyle);
			colCount++;
			
//			cell = row.createCell(colCount);
//			cell.setCellValue("Stock Availability");
//			cell.setCellStyle(paleBlueStyle);
			
			for(ArticleSchemeSummaryReportCfa assrc :list) {
//				newCfaLocation = assrc.getCfa_location();
//				newTdsSchemeCode = assrc.getTrd_scheme_code();
				
//				System.out.println("newCfaLocation@# "+newCfaLocation);
//				System.out.println("oldCfaLocation@# "+oldCfaLocation);
//				if (!newCfaLocation.equals(oldCfaLocation) || !newTdsSchemeCode.equals(oldTdsSchemeCode)) {
////					if (!oldCfaLocation.equals("")) {
////						rowCount++;
////						colCount = 0;
////						row = sheet.createRow(rowCount);
////						
////						cell = row.createCell(colCount);
////						cell.setCellValue("");
////						colCount++;
////						
////						cell = row.createCell(colCount);
////						cell.setCellValue(totalSaleQty);
////						cell.setCellStyle(paleGreenStyle);
////						colCount++;
////						
////						ReportFormats.cellBigDecimal(row, colCount, totalSaleValue, paleBlueStyles);
////						colCount++;
////						
////						cell = row.createCell(colCount);
////						cell.setCellValue(totalArticleRedQty);
////						cell.setCellStyle(paleGreenStyle);
////						colCount++;
////						
////						ReportFormats.cellBigDecimal(row, colCount, totalArtDspRate, paleBlueStyles);
////						colCount++;
////						
////						ReportFormats.cellBigDecimal(row, colCount, totalArtRedValue, paleBlueStyles);
////						colCount++;
////						
////						ReportFormats.cellBigDecimal(row, colCount, totalClosingStock, paleBlueStyles);
////						colCount++;
////						
////						ReportFormats.cellBigDecimal(row, colCount, totalArtDspQty, paleBlueStyles);
////						colCount++;
////						
////						ReportFormats.cellBigDecimal(row, colCount, totalArtDspVal, paleBlueStyles);
////						colCount++;
////						
////						cell = row.createCell(colCount);
////						cell.setCellStyle(paleGreenStyle);
////						cell.setCellValue("");
////						colCount++;
////						
////						cell = row.createCell(colCount);
////						cell.setCellStyle(paleGreenStyle);
////						cell.setCellValue("");
////						colCount++;
////						
////						
////						totalSaleQty = 0L;
////						totalSaleValue = BigDecimal.ZERO;
////						totalArticleRedQty = 0L;
////						totalArtDspRate = BigDecimal.ZERO;
////						totalArtRedValue = BigDecimal.ZERO;
////						totalClosingStock = BigDecimal.ZERO;
////						totalArtDspQty = BigDecimal.ZERO;
////						totalArtDspVal = BigDecimal.ZERO;
////					}
//					rowCount++;
//					colCount = 0;
//					row = sheet.createRow(rowCount);
//					cell = row.createCell(colCount);
//					sheet.addMergedRegion(new CellRangeAddress(rowCount, rowCount+1, colCount, colCount));
//					cell.setCellValue("CFA Location Name");
//					cell.setCellStyle(paleBlueStyle);
//					colCount++;
//					
//					cell = row.createCell(colCount);
//					sheet.addMergedRegion(new CellRangeAddress(rowCount, rowCount, colCount, colCount+1));
//					cell.setCellValue("SAP Invoices Referenced");
//					cell.setCellStyle(paleBlueStyle);
//					colCount+=2;
//					
//					cell = row.createCell(colCount);
//					sheet.addMergedRegion(new CellRangeAddress(rowCount, rowCount, colCount, colCount+2));
//					cell.setCellValue("Article Requests Generated at CFA Locations");
//					cell.setCellStyle(paleBlueStyle);
//					colCount+=3;
//					
//					cell = row.createCell(colCount);
//					cell.setCellValue("Current Stock");
//					cell.setCellStyle(paleBlueStyle);
//					colCount+=1;
//					
//					cell = row.createCell(colCount);
//					sheet.addMergedRegion(new CellRangeAddress(rowCount, rowCount, colCount, colCount+1));
//					cell.setCellValue("Articles Supplied");
//					cell.setCellStyle(paleBlueStyle);
//					colCount+=2;
//					
//					cell = row.createCell(colCount);
//					sheet.addMergedRegion(new CellRangeAddress(rowCount, rowCount, colCount, colCount+1));
//					cell.setCellValue("Pending Article Supplies");
//					cell.setCellStyle(paleBlueStyle);
//					colCount+=2;
//					
//					
//					rowCount++;
//					colCount=1;			
//					row = sheet.createRow(rowCount);
//					
//					cell = row.createCell(colCount);
//					cell.setCellValue("Sales SKU Qty");
//					cell.setCellStyle(paleBlueStyle);
//					colCount++;
//					
//					cell = row.createCell(colCount);
//					cell.setCellValue("Sales SKU  Value");
//					cell.setCellStyle(paleBlueStyle);
//					colCount++;
//					
//					cell = row.createCell(colCount);
//					cell.setCellValue("Article Request Qty");
//					cell.setCellStyle(paleBlueStyle);
//					colCount++;
//					
//					cell = row.createCell(colCount);
//					cell.setCellValue("Article Rate");
//					cell.setCellStyle(paleBlueStyle);
//					colCount++;
//					
//					cell = row.createCell(colCount);
//					cell.setCellValue("Article Request Value");
//					cell.setCellStyle(paleBlueStyle);
//					colCount++;
//					
//					cell = row.createCell(colCount);
//					cell.setCellValue("Article Stock at CFA Locations");
//					cell.setCellStyle(paleBlueStyle);
//					colCount++;
//					
//					cell = row.createCell(colCount);
//					cell.setCellValue("Article Supply Qty");
//					cell.setCellStyle(paleBlueStyle);
//					colCount++;
//					
//					cell = row.createCell(colCount);
//					cell.setCellValue("Article Supply Value");
//					cell.setCellStyle(paleBlueStyle);
//					colCount++;
//					
//					cell = row.createCell(colCount);
//					cell.setCellValue("Pending Article Requests");
//					cell.setCellStyle(paleBlueStyle);
//					colCount++;
//					
//					cell = row.createCell(colCount);
//					cell.setCellValue("Stock Availability");
//					cell.setCellStyle(paleBlueStyle);
//				}
				
				
				rowCount++;
				row = sheet.createRow(rowCount);
				colCount = 0;
				
				cell = row.createCell(colCount);
				cell.setCellValue(assrc.getCfa_location());
				colCount++;
				
				cell = row.createCell(colCount);
				cell.setCellValue(assrc.getSale_qty());
				colCount++;
				
				ReportFormats.cellBigDecimal(row, colCount, assrc.getSale_value(), cellAlignment2);
				colCount++;
				
				cell = row.createCell(colCount);
				cell.setCellValue(Double.valueOf(assrc.getArticle_req_qty()));
				colCount++;
				
				ReportFormats.cellBigDecimal(row, colCount, assrc.getArt_dsp_rate(), cellAlignment2);
				colCount++;
				
				ReportFormats.cellBigDecimal(row, colCount, assrc.getArticle_req_item_value(), cellAlignment2);
				colCount++;
				
				cell = row.createCell(colCount);
				cell.setCellValue(Double.valueOf(assrc.getClosing_stock().toString()));
				colCount++;
				
				cell = row.createCell(colCount);
				cell.setCellValue(Double.valueOf(assrc.getArt_dsp_qty().toString()));
				colCount++;
				
				ReportFormats.cellBigDecimal(row, colCount, assrc.getArt_dsp_value(), cellAlignment2);
				colCount++;
				
				cell = row.createCell(colCount);
				cell.setCellValue(Double.valueOf(assrc.getReq_pending_qty()));
				colCount++;
				
//				cell = row.createCell(colCount);
//				cell.setCellValue(Double.valueOf(assrc.getAvailable_stock()));
//				colCount++;
				
//				oldCfaLocation=newCfaLocation;
//				oldTdsSchemeCode=newTdsSchemeCode;
				
				totalSaleQty = totalSaleQty+assrc.getSale_qty();
				totalSaleValue = totalSaleValue.add(assrc.getSale_value());
				totalArticleRedQty = totalArticleRedQty+assrc.getArticle_req_qty();
//				totalArtDspRate = totalArtDspRate.add(assrc.getArt_dsp_rate());
				totalArtRedValue = totalArtRedValue.add(assrc.getArticle_req_item_value());
				totalClosingStock = totalClosingStock.add(assrc.getClosing_stock());
				totalArtDspQty = totalArtDspQty.add(assrc.getArt_dsp_qty());
				totalArtDspVal = totalArtDspVal.add(assrc.getArt_dsp_value());
				totalReqPendingQty = totalReqPendingQty+assrc.getReq_pending_qty();
//				totalAvailableStock = totalAvailableStock+assrc.getAvailable_stock();
			}
			
//			if (!oldCfaLocation.equals("")) {
				rowCount++;
				colCount = 0;
				row = sheet.createRow(rowCount);
				
				cell = row.createCell(colCount);
				cell.setCellValue("Total");
				cell.setCellStyle(paleGreenStyleLeft);
				colCount++;
				
				cell = row.createCell(colCount);
				cell.setCellValue(totalSaleQty);
				cell.setCellStyle(paleGreenStyle);
				colCount++;
				
				ReportFormats.cellBigDecimal(row, colCount, totalSaleValue, paleBlueStyles);
				colCount++;
				
				cell = row.createCell(colCount);
				cell.setCellValue(totalArticleRedQty);
				cell.setCellStyle(paleGreenStyle);
				colCount++;
				
				ReportFormats.cellBigDecimal(row, colCount, list.get(0).getArt_dsp_rate(), paleBlueStyles);
				colCount++;
				
				ReportFormats.cellBigDecimal(row, colCount, totalArtRedValue, paleBlueStyles);
				colCount++;
				
				ReportFormats.cellBigDecimal(row, colCount, totalClosingStock, paleBlueStyles);
				colCount++;
				
				ReportFormats.cellBigDecimal(row, colCount, totalArtDspQty, paleBlueStyles);
				colCount++;
				
				ReportFormats.cellBigDecimal(row, colCount, totalArtDspVal, paleBlueStyles);
				colCount++;
				
				cell = row.createCell(colCount);
				cell.setCellStyle(paleGreenStyle);
				cell.setCellValue(totalReqPendingQty);
				colCount++;
				
//				cell = row.createCell(colCount);
//				cell.setCellStyle(paleGreenStyle);
//				cell.setCellValue(totalAvailableStock);
//				colCount++;
				
				
//				totalSaleQty = 0L;
//				totalSaleValue = BigDecimal.ZERO;
//				totalArticleRedQty = 0L;
//				totalArtDspRate = BigDecimal.ZERO;
//				totalArtRedValue = BigDecimal.ZERO;
//				totalClosingStock = BigDecimal.ZERO;
//				totalArtDspQty = BigDecimal.ZERO;
//				totalArtDspVal = BigDecimal.ZERO;
//			}
			
				setBordersToMergedCells(sheet, 2);
			FileOutputStream fileOutputStream = new FileOutputStream(filepath);
			workbook.write(fileOutputStream);
			
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (workbook != null) {
				workbook.close();
			}
		}
		return filename;
	}
	
	public static void setBordersToMergedCells(XSSFSheet sheet, int start) {
		List<CellRangeAddress> mergedRegions = sheet.getMergedRegions();
		for (int i = start; i < mergedRegions.size(); i++) {
			CellRangeAddress rangeAddress = mergedRegions.get(i);
			RegionUtil.setBorderTop(BorderStyle.THIN, rangeAddress, sheet);
			RegionUtil.setBorderLeft(BorderStyle.THIN, rangeAddress, sheet);
			RegionUtil.setBorderRight(BorderStyle.THIN, rangeAddress, sheet);
			RegionUtil.setBorderBottom(BorderStyle.THIN, rangeAddress, sheet);
		}

	}


	
	
}