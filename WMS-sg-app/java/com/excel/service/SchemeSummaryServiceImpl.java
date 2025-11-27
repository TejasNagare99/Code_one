package com.excel.service;

import java.io.File;
import java.io.FileOutputStream;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.poi.ss.usermodel.BorderStyle;
import org.apache.poi.ss.usermodel.CreationHelper;
import org.apache.poi.ss.usermodel.FillPatternType;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.VerticalAlignment;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.usermodel.DefaultIndexedColorMap;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.apache.poi.xssf.usermodel.XSSFColor;
import org.apache.poi.xssf.usermodel.XSSFFont;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Service;

import com.excel.bean.articleScheme;
import com.excel.model.Scheme_Summary;
import com.excel.utility.MedicoConstants;
import com.excel.utility.MedicoResources;
import com.excel.utility.ReportFormats;

@Service
public class SchemeSummaryServiceImpl implements SchemeSummaryService, MedicoConstants {

	@Override
	public String generateSchemeSummaryReport(List<Scheme_Summary> listSummary,
			String companyname) throws Exception {
		// TODO Auto-generated method stub
		String filename = "SchemeSummary_without_finyear" + new Date().getTime() + ".xlsx";
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

			XSSFSheet sheet = workbook.createSheet("Scheme Summary Report");

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
			XSSFColor xssfColor = new XSSFColor(new java.awt.Color(r, g, b), new DefaultIndexedColorMap());
			periodNameStyle.setAlignment(HorizontalAlignment.LEFT);
			XSSFFont headerFont2 = workbook.createFont();
			headerFont2.setFontHeightInPoints((short) 14);
			headerFont2.setColor(xssfColor);
			headerFont2.setBold(true);
			periodNameStyle.setFont(headerFont2);

			XSSFCellStyle headingCellStyle = ReportFormats.heading(workbook);
			// XSSFCellStyle columnHeadingStyle = ReportFormats.columnHeading(workbook);

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

			XSSFCellStyle columnstyle = ReportFormats.columnHeadingBlue(workbook);
			sheet.createFreezePane(0, 4); //DOME

			XSSFCellStyle cellAlignment2 = workbook.createCellStyle();
			cellAlignment2.setDataFormat(workbook.createDataFormat().getFormat("0.00"));
			cellAlignment2.setAlignment(HorizontalAlignment.RIGHT);

			XSSFCellStyle dateStyle = workbook.createCellStyle();
			CreationHelper creationHelper = workbook.getCreationHelper();
			dateStyle.setDataFormat(creationHelper.createDataFormat().getFormat("dd-MM-yyyy"));

			row = sheet.createRow(rowCount);
			cell = row.createCell(colCount);
			sheet.addMergedRegion(new CellRangeAddress(rowCount, rowCount, colCount, roughcount1));
			cell.setCellValue("Pfizer India Ltd.");
			cell.setCellStyle(compNameStyle);
			rowCount++;

			row = sheet.createRow(rowCount);
			cell = row.createCell(colCount);
			sheet.addMergedRegion(new CellRangeAddress(rowCount, rowCount, colCount, roughcount1));
			cell.setCellValue("Scheme Summary Report");
			cell.setCellStyle(reportNameStyle);
			rowCount++;

//			colCount = 0;
//			row = sheet.createRow(rowCount);
//			cell = row.createCell(colCount);
//			sheet.addMergedRegion(new CellRangeAddress(rowCount, rowCount, colCount, roughcount1));
//			cell.setCellValue("Period:" + fromDate + " " + "to" + " " + toDate);
//			cell.setCellStyle(periodNameStyle);
//			rowCount++;

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

//			XSSFCellStyle paleBlueStyle = workbook.createCellStyle();
//			paleBlueStyle.setFillForegroundColor(IndexedColors.PALE_BLUE.index);
//			paleBlueStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);
//

//			paleBlueStyle.setAlignment(HorizontalAlignment.CENTER);
//
//			paleBlueStyle.setFont(headerFont);
//			

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

//			 paleBlueStyle.setBorderTop(BorderStyle.THIN);
//				paleBlueStyle.setTopBorderColor(IndexedColors.BLACK.index);
//				paleBlueStyle.setBorderBottom(BorderStyle.THIN);
//				paleBlueStyle.setBottomBorderColor(IndexedColors.BLACK.index);
//				paleBlueStyle.setBorderLeft(BorderStyle.THIN);
//				paleBlueStyle.setLeftBorderColor(IndexedColors.BLACK.index);
//				paleBlueStyle.setBorderRight(BorderStyle.THIN);
//				paleBlueStyle.setRightBorderColor(IndexedColors.BLACK.index);

			colCount = 0;
			row = sheet.createRow(rowCount);
			cell = row.createCell(colCount);
//			sheet.addMergedRegion(new CellRangeAddress(rowCount, rowCount+1, colCount, colCount));
			cell.setCellValue("");
			cell.setCellStyle(paleBlueStyle);
			colCount++;

			cell = row.createCell(colCount);
//			sheet.addMergedRegion(new CellRangeAddress(rowCount, rowCount+1, colCount, colCount));
			cell.setCellValue("");
			cell.setCellStyle(paleBlueStyle);
			colCount++;

			cell = row.createCell(colCount);
			sheet.addMergedRegion(new CellRangeAddress(rowCount, rowCount, colCount, colCount + 2));
			cell.setCellValue("Scheme Validity");
			cell.setCellStyle(paleBlueStyle);
			colCount++;
			
		

//			colCount+=2;

			cell = row.createCell(colCount);
//			sheet.addMergedRegion(new CellRangeAddress(rowCount, rowCount+1, colCount, colCount));
			cell.setCellValue("");
			cell.setCellStyle(paleBlueStyle);
			colCount++;

			cell = row.createCell(colCount);
//			sheet.addMergedRegion(new CellRangeAddress(rowCount, rowCount+1, colCount, colCount));
			cell.setCellValue("");
			cell.setCellStyle(paleBlueStyle);

			colCount++;
			

			cell = row.createCell(colCount);
//			sheet.addMergedRegion(new CellRangeAddress(rowCount, rowCount+1, colCount, colCount));
			cell.setCellValue("");
			cell.setCellStyle(paleBlueStyle);
			colCount++;

			cell = row.createCell(colCount);
//			sheet.addMergedRegion(new CellRangeAddress(rowCount, rowCount+1, colCount, colCount));
			cell.setCellValue("");
			cell.setCellStyle(paleBlueStyle);

			colCount++;
			
			cell = row.createCell(colCount);
			sheet.addMergedRegion(new CellRangeAddress(rowCount, rowCount, colCount, colCount + 4));
			cell.setCellValue("Invoiced Goods(scheme applied)");
			cell.setCellStyle(paleBlueStyle);
			colCount++;
			
			cell = row.createCell(colCount);
			cell.setCellValue("");
			cell.setCellStyle(paleBlueStyle);

			colCount++;

			cell = row.createCell(colCount);
			cell.setCellValue("");
			cell.setCellStyle(paleBlueStyle);

			colCount++;

			cell = row.createCell(colCount);
			cell.setCellValue("");
			cell.setCellStyle(paleBlueStyle);
			colCount++;

			cell = row.createCell(colCount);
			cell.setCellValue("");
			cell.setCellStyle(paleBlueStyle);

			colCount++;

//			colCount+=3;

			cell = row.createCell(colCount);
			sheet.addMergedRegion(new CellRangeAddress(rowCount, rowCount, colCount, colCount + 5));
//			cell.setCellValue("Articles Delivered against eligible invoices");
			cell.setCellValue("");
			cell.setCellStyle(paleBlueStyle);
			colCount++;

			for (int i = 1; i <= 7; i++) {
				cell = row.createCell(colCount);
				cell.setCellValue("");
				cell.setCellStyle(paleBlueStyle);

				colCount++;

			}

//			cell = row.createCell(colCount);
//			cell.setCellValue("");
//			cell.setCellStyle(paleBlueStyle);
//
//			colCount++;
//			
//			cell = row.createCell(colCount);
//			cell.setCellValue("");
//			cell.setCellStyle(paleBlueStyle);

			rowCount++;

			colCount = 0;
			row = sheet.createRow(rowCount);

			cell = row.createCell(colCount);
			cell.setCellValue("Scheme Code");
			cell.setCellStyle(paleBlueStyle);
			colCount++;

			cell = row.createCell(colCount);
			cell.setCellValue("Scheme Name");
			cell.setCellStyle(paleBlueStyle);
			colCount++;

			cell = row.createCell(colCount);
			cell.setCellValue("Valid From");
			cell.setCellStyle(paleBlueStyle);
			colCount++;

			cell = row.createCell(colCount);
			cell.setCellValue("Valid To");
			cell.setCellStyle(paleBlueStyle);

			colCount++;
			
			cell = row.createCell(colCount);
			cell.setCellValue("Extended To");
			cell.setCellStyle(paleBlueStyle);

			colCount++;

			cell = row.createCell(colCount);
			cell.setCellValue("Brand Name");
			cell.setCellStyle(paleBlueStyle);

			colCount++;
			
			cell = row.createCell(colCount);
			cell.setCellValue("Article Name");
			cell.setCellStyle(paleBlueStyle);

			colCount++;

			cell = row.createCell(colCount);
			cell.setCellValue("Item Name ");
			cell.setCellStyle(paleBlueStyle);

			colCount++;
			
			cell = row.createCell(colCount);
			cell.setCellValue("Per Billed Qty");
			cell.setCellStyle(paleBlueStyle);

			colCount++;
			cell = row.createCell(colCount);
			cell.setCellValue("Article Qty");
			cell.setCellStyle(paleBlueStyle);

			colCount++;


			cell = row.createCell(colCount);
			cell.setCellValue("Sale Product Qty ");
			cell.setCellStyle(paleBlueStyle);

			colCount++;

			cell = row.createCell(colCount);
			cell.setCellValue("Sale Product Value ");
			cell.setCellStyle(paleBlueStyle);

			colCount++;

			cell = row.createCell(colCount);
			cell.setCellValue("Article  Rate ");
			cell.setCellStyle(paleBlueStyle);

			colCount++;

			cell = row.createCell(colCount);
			cell.setCellValue("Article  Requested  Qty ");
			cell.setCellStyle(paleBlueStyle);

			colCount++;

			cell = row.createCell(colCount);
			cell.setCellValue("Article Requested Item Value");
			cell.setCellStyle(paleBlueStyle);

			colCount++;

			cell = row.createCell(colCount);
			cell.setCellValue("Article  Delivered  Qty ");
			cell.setCellStyle(paleBlueStyle);

			colCount++;

			cell = row.createCell(colCount);
			cell.setCellValue("Article  Delivered  Value ");
			cell.setCellStyle(paleBlueStyle);

			colCount++;

			cell = row.createCell(colCount);
			cell.setCellValue("Pending Qty");
			cell.setCellStyle(paleBlueStyle);

			colCount++;

			cell = row.createCell(colCount);
			cell.setCellValue("Closing Stock");
			cell.setCellStyle(paleBlueStyle);

			colCount++;

			cell = row.createCell(colCount);
			cell.setCellValue("Stock Availability");
			cell.setCellStyle(paleBlueStyle);

			colCount++;

			rowCount++;

			// Variables to track the current article and its totals
			String currentArticleName = null;
			String prevSchemeName = null;

			BigDecimal totalSaleValue = BigDecimal.ZERO;
			BigDecimal totalArtDspValue = BigDecimal.ZERO;
			BigDecimal totalArtReqItemValue = BigDecimal.ZERO;

			// Variables to track the grand totals
			BigDecimal grandTotalSaleValue = BigDecimal.ZERO;
			BigDecimal grandTotalArtDspValue = BigDecimal.ZERO;
			BigDecimal grandTotalArtReqItemValue = BigDecimal.ZERO;

			for (Scheme_Summary data : listSummary) {
				String articleName = data.getArticle_name();

				// If the article name changes, add a total row
				if (currentArticleName != null && !currentArticleName.equals(articleName)) {
					colCount = 0;
					XSSFRow totalRow = sheet.createRow(rowCount);
					XSSFCell totalCell = totalRow.createCell(colCount);
					sheet.addMergedRegion(new CellRangeAddress(rowCount, rowCount, colCount, 10));
					totalCell.setCellValue(" ");
					totalCell.setCellStyle(paleBlueStylesTotal);

					colCount =11;
					totalCell = totalRow.createCell(colCount);
					ReportFormats.cellBigDecimal(totalRow, colCount, totalSaleValue, paleBlueStyles);

					for (int i = 12; i <= 13; i++) {
						totalCell = totalRow.createCell(i);
						totalCell.setCellStyle(paleBlueStyles);
					}

					colCount = 14;
					// totalCell = totalRow.createCell(colCount);
					ReportFormats.cellBigDecimal(totalRow, colCount, totalArtReqItemValue, paleBlueStyles);
					// totalArtDspValue

					colCount = 15;
					totalCell = totalRow.createCell(colCount);
					totalCell.setCellStyle(paleBlueStyles);

					colCount = 16;
					// totalCell = totalRow.createCell(colCount);
					ReportFormats.cellBigDecimal(totalRow, colCount, totalArtDspValue, paleBlueStyles);

					for (int i = 17; i <= 19; i++) {
						totalCell = totalRow.createCell(i);
						totalCell.setCellValue("");
						totalCell.setCellStyle(paleBlueStyles);
					}

					rowCount++;
					grandTotalSaleValue = grandTotalSaleValue.add(totalSaleValue);
					grandTotalArtDspValue = grandTotalArtDspValue.add(totalArtDspValue);
					grandTotalArtReqItemValue = grandTotalArtReqItemValue.add(totalArtReqItemValue);

					totalSaleValue = BigDecimal.ZERO;
					totalArtDspValue = BigDecimal.ZERO;
					totalArtReqItemValue = BigDecimal.ZERO;
				}

				if (currentArticleName == null || !currentArticleName.equals(articleName)) {
					totalSaleValue = totalSaleValue.add(data.getSale_value());
					totalArtDspValue = totalArtDspValue.add(data.getArt_dsp_value());
					totalArtReqItemValue = totalArtReqItemValue.add(data.getArticle_req_item_value());
				}

				currentArticleName = articleName;
				colCount = 0;
				row = sheet.createRow(rowCount);

				SimpleDateFormat inputFormat = new SimpleDateFormat("yyyy-MM-dd");
				SimpleDateFormat outputFormat = new SimpleDateFormat("dd-MM-yyyy");

				Date fromDateParsed = inputFormat.parse(data.getValid_from_dt());
				Date toDateParsed = inputFormat.parse(data.getValid_to_dt());

				String fromDateFormatted = outputFormat.format(fromDateParsed);
				String toDateFormatted = outputFormat.format(toDateParsed);

				if (data.getTrd_scheme_name().equals(prevSchemeName)) {
					colCount++;
					colCount++;
					colCount++;
					colCount++;
					colCount++;
					row.createCell(colCount++).setCellValue(data.getBrand());

					colCount++;
					row.createCell(colCount++).setCellValue(data.getSale_prod_name());
					row.createCell(colCount++).setCellValue(data.getSale_blled_qty());
					row.createCell(colCount++).setCellValue(data.getArticle_qty());
					row.createCell(colCount++).setCellValue(data.getSale_qty());
					ReportFormats.cellBigDecimal(row, colCount++, data.getSale_value(), cellAlignment2);
				} else {

					row.createCell(colCount++).setCellValue(data.getTrd_scheme_code());
					row.createCell(colCount++).setCellValue(data.getTrd_scheme_name());
//			    	    row.createCell(colCount++).setCellValue(fromDateFormatted);
//			    	    row.createCell(colCount++).setCellValue(toDateFormatted);
//			    	    cell = ReportFormats.cellDate(row, colCount++, MedicoResources.convert_YYYY_DD_MM_toDate(data.getValid_from_dt()), dateStyle);
//						
//						cell = ReportFormats.cellDate(row, colCount++, MedicoResources.convert_YYYY_DD_MM_toDate(data.getValid_to_dt()), dateStyle);
//			    	    

					cell = ReportFormats.cellDate(row, colCount++, outputFormat.parse(data.getValid_from_dt()),
							dateStyle);
//						
					cell = ReportFormats.cellDate(row, colCount++, outputFormat.parse(data.getValid_to_dt()),
							dateStyle);
					
					cell = ReportFormats.cellDate(row, colCount++, data.getValidity_extended_date(),
							 (data.getValidity_extended_date()!=null?dateStyle:null));
//			    	 
					row.createCell(colCount++).setCellValue(data.getBrand());
					
					row.createCell(colCount++).setCellValue(data.getArticle_name());

					row.createCell(colCount++).setCellValue(data.getSale_prod_name());
					
					row.createCell(colCount++).setCellValue(data.getSale_blled_qty());
					row.createCell(colCount++).setCellValue(data.getArticle_qty());
					row.createCell(colCount++).setCellValue(data.getSale_qty());
					
					
					
					ReportFormats.cellBigDecimal(row, colCount++, data.getSale_value(), cellAlignment2);
					ReportFormats.cellBigDecimal(row, colCount++, data.getArt_dsp_rate(), cellAlignment2);
					row.createCell(colCount++).setCellValue(data.getArticle_req_qty());
					ReportFormats.cellBigDecimal(row, colCount++, data.getArticle_req_item_value(), cellAlignment2);
					row.createCell(colCount++).setCellValue(data.getArt_dsp_qty());
					ReportFormats.cellBigDecimal(row, colCount++, data.getArt_dsp_value(), cellAlignment2);
					row.createCell(colCount++).setCellValue(data.getReq_pending_qty());
					row.createCell(colCount++).setCellValue(data.getClosing_stock().longValue());
					row.createCell(colCount++).setCellValue(data.getAvailable_stock());

				}
//				row.createCell(colCount++).setCellValue(data.getSale_prod_name());
//				row.createCell(colCount++).setCellValue(data.getSale_qty());
//				ReportFormats.cellBigDecimal(row, colCount++, data.getSale_value(), cellAlignment2);
//				ReportFormats.cellBigDecimal(row, colCount++, data.getArt_dsp_rate(), cellAlignment2);
//				row.createCell(colCount++).setCellValue(data.getArticle_req_qty());
//				ReportFormats.cellBigDecimal(row, colCount++, data.getArticle_req_item_value(), cellAlignment2);
//				row.createCell(colCount++).setCellValue(data.getArt_dsp_qty());
//				ReportFormats.cellBigDecimal(row, colCount++, data.getArt_dsp_value(), cellAlignment2);
//				row.createCell(colCount++).setCellValue(data.getReq_pending_qty());
//				row.createCell(colCount++).setCellValue(data.getClosing_stock().longValue());
//				row.createCell(colCount++).setCellValue(data.getAvailable_stock());

				prevSchemeName = data.getTrd_scheme_name();

				rowCount++;
			}

			if (currentArticleName != null) {
				colCount = 0;
				XSSFRow totalRow = sheet.createRow(rowCount);
				XSSFCell totalCell = totalRow.createCell(colCount);
				sheet.addMergedRegion(new CellRangeAddress(rowCount, rowCount, colCount, 10));
				totalCell.setCellValue("");
				totalCell.setCellStyle(paleBlueStylesTotal);

				colCount = 11;
				totalCell = totalRow.createCell(colCount);
				ReportFormats.cellBigDecimal(totalRow, colCount, totalSaleValue, paleBlueStyles);

				for (int i = 12; i <= 13; i++) {
					totalCell = totalRow.createCell(i);
					totalCell.setCellStyle(paleBlueStyles);
				}

				colCount = 14;
				totalCell = totalRow.createCell(colCount);
				ReportFormats.cellBigDecimal(totalRow, colCount, totalArtReqItemValue, paleBlueStyles);
//totalArtDspValue
				colCount++;

				totalCell = totalRow.createCell(colCount);
				totalCell.setCellValue("");
				totalCell.setCellStyle(paleBlueStylesTotal);

				colCount = 16;
				totalCell = totalRow.createCell(colCount);
				ReportFormats.cellBigDecimal(totalRow, colCount, totalArtDspValue, paleBlueStyles);

				for (int i = 17; i <= 19; i++) {
					totalCell = totalRow.createCell(i);
					totalCell.setCellStyle(paleBlueStyles);
				}

				rowCount++;
				grandTotalSaleValue = grandTotalSaleValue.add(totalSaleValue);
				grandTotalArtDspValue = grandTotalArtDspValue.add(totalArtDspValue);
				grandTotalArtReqItemValue = grandTotalArtReqItemValue.add(totalArtReqItemValue);
			}
			rowCount++;

			colCount = 0;
			XSSFRow grandTotalRow = sheet.createRow(rowCount);
			XSSFCell grandTotalCell = grandTotalRow.createCell(colCount);
			grandTotalCell.setCellValue("Totals:");
			sheet.addMergedRegion(new CellRangeAddress(rowCount, rowCount, colCount, 10));
			grandTotalCell.setCellStyle(paleBlueStylesTotal);

			colCount = 11;
			grandTotalCell = grandTotalRow.createCell(colCount);
			ReportFormats.cellBigDecimal(grandTotalRow, colCount, grandTotalSaleValue, paleBlueStyles);

			for (int i = 12; i <= 13; i++) {
				grandTotalCell = grandTotalRow.createCell(i);
				grandTotalCell.setCellStyle(paleBlueStyles);
			}

			colCount = 14;
			grandTotalCell = grandTotalRow.createCell(colCount);
			ReportFormats.cellBigDecimal(grandTotalRow, colCount, grandTotalArtReqItemValue, paleBlueStyles);

			colCount++;
			grandTotalCell = grandTotalRow.createCell(colCount);
			grandTotalCell.setCellValue("");
			grandTotalCell.setCellStyle(paleBlueStylesTotal);

//grandTotalArtDspValue
			colCount = 16;
			grandTotalCell = grandTotalRow.createCell(colCount);
			ReportFormats.cellBigDecimal(grandTotalRow, colCount, grandTotalArtDspValue, paleBlueStyles);

			for (int i = 17; i <= 19; i++) {
				grandTotalCell = grandTotalRow.createCell(i);
				grandTotalCell.setCellValue("");
				grandTotalCell.setCellStyle(paleBlueStylesTotal);
			}

			rowCount++;

			FileOutputStream fileOutputStream = new FileOutputStream(filepath);
			workbook.write(fileOutputStream);

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

	@Override
	public String getarticleScheme(List<articleScheme> listSummary) {

//		
//		String filename = "SchemeSummary" + new Date().getTime() + ".xlsx";
//		String filepath = REPORT_FILE_PATH + filename;
//		System.out.println("file path :::" + filepath);
//		XSSFWorkbook workbook = null;
//
//		try {
//			workbook = new XSSFWorkbook();
//			File f = new File(REPORT_FILE_PATH);
//			if (!f.exists()) {
//				if (f.mkdirs()) {
//				} else {
//					throw new RuntimeException("Could not create directory");
//				}
//			}
//
//			XSSFSheet sheet = workbook.createSheet("Scheme Summary Report");
//
//			XSSFRow row = null;
//			XSSFCell cell = null;
//			int rowCount = 0;
//			int colCount = 0;
//			int roughcount1 = 10;
//
//			
//			String hexColor = "#653700";
//
//			int r = Integer.valueOf(hexColor.substring(1, 3), 16);
//			int g = Integer.valueOf(hexColor.substring(3, 5), 16);
//			int b = Integer.valueOf(hexColor.substring(5, 7), 16);
//
//			XSSFCellStyle periodNameStyle = workbook.createCellStyle();
//			XSSFColor xssfColor = new XSSFColor(new java.awt.Color(r, g, b), new DefaultIndexedColorMap());
//			periodNameStyle.setAlignment(HorizontalAlignment.LEFT);
//			XSSFFont headerFont2 = workbook.createFont();
//			headerFont2.setFontHeightInPoints((short) 14);
//			headerFont2.setColor(xssfColor);
//			headerFont2.setBold(true);
//			periodNameStyle.setFont(headerFont2);
//
//			XSSFCellStyle headingCellStyle = ReportFormats.heading(workbook);
//			XSSFCellStyle columnHeadingStyle = ReportFormats.columnHeading(workbook);
//			XSSFCellStyle datastyleRightwithDecimal = ReportFormats.setCellAlignmentWithTwoDecimal(workbook,
//					ALIGN_RIGHT);

//
//			XSSFCellStyle compNameStyle = workbook.createCellStyle();
//			compNameStyle.setAlignment(HorizontalAlignment.LEFT);
//			XSSFColor myColor = new XSSFColor(new java.awt.Color(48, 84, 150), new DefaultIndexedColorMap());
//			XSSFFont headerFont1 = workbook.createFont();
//			headerFont1.setFontHeightInPoints((short) 18);
//			headerFont1.setColor(myColor);
//			headerFont1.setBold(true);
//			compNameStyle.setFont(headerFont1);
//
//			XSSFCellStyle reportNameStyle = workbook.createCellStyle();
//			headerFont1 = workbook.createFont();
//			headerFont1.setFontHeightInPoints((short) 16);
//			headerFont1.setColor(myColor);
//			headerFont1.setBold(true);
//			reportNameStyle.setFont(headerFont1);
//
//			XSSFCellStyle columnstyle = ReportFormats.columnHeadingBlue(workbook);
//
//			XSSFCellStyle cellAlignment2 = workbook.createCellStyle();
//			cellAlignment2.setDataFormat(workbook.createDataFormat().getFormat("0.00"));
//			cellAlignment2.setAlignment(HorizontalAlignment.RIGHT);
//
//			ArrayList<String> names = new ArrayList<>();
//
//			names.add("EXP_ID");
//			names.add("ERR_MSG");
//			names.add("TRD_SCH_SLNO");
//			names.add("ARTICLE_REQ_ID");
//			names.add("ARTICLE_REQ_DTL_ID");
//			names.add("ARTICLE_SCHM_NAME");
//			names.add("ARTICLE_NAME");
//			names.add("ART_SALE_PROD_CD");
//			names.add("ART_SALE_PROD_NAME");
//			names.add("ART_BILLED_QTY");
//			names.add("ART_QTY");
//			names.add("INVOICE_NO");
//			names.add("AP");
//			names.add("SAP_PLANT_CD");
//			names.add("COMPANY_CD");
//			names.add("CFA_LOCATION");
//			names.add("SAP_INV_NO");
//			names.add("SAP_INV_DT");
//			names.add("SAP_CUST_CD");
//			names.add("CUSTOMER_NAME");
//			names.add("CITY");
//			names.add("SAP_SALE_PROD_CD");
//			names.add("SAP_SALE_PROD_NAME");
//			names.add("Billed_Qty");
//			names.add("FREE_QTY");
//			names.add("BILLING_RATE");
//			names.add("BILLING_VALUE");
//			names.add("ARTICLE_SALE_BILL_QTY_ENTERED");
//			names.add("ARTICLE_RATE");
//			names.add("ARTICLE_VALUE");
//			names.add("ERR_BILL_QTY");
//
//			sheet.createFreezePane(0, 1);
//
////				hrow = (XSSFRow) wsheet.createRow(row);
////				cell = hrow.createCell(colCount);
////				cell.setCellStyle(sheetHeading);
////				cell.setCellValue(compNm);
////				wsheet.addMergedRegion(new CellRangeAddress(row, row, colCount, colCount + 44));
////				row++;
//		
////
//			hrow = sheet.createRow(rowCount);
//			for (String columnName : names) {
//
//				cell = hrow.createCell(colCount);
//				cell.setCellStyle(columnHeadingStyle);
//				cell.setCellValue(columnName);
//				colCount++;
//			}
//
//			

		String filename = "articleSchemeException" + new Date().getTime() + ".xlsx";
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

			XSSFSheet sheet = workbook.createSheet("Article Scheme Exception Report");

			XSSFRow row = null;
			XSSFCell cell = null;
			int rowCount = 0;
			int colCount = 0;

			List<String> headings = new ArrayList<>();

			headings.add("Sl.No.");
			headings.add("AP");
			headings.add("SAP Plant Code");
			headings.add("Comapny");
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
			headings.add("Billed Qty");
			headings.add("Rate");
			headings.add("Value");
			headings.add("Error in Billed Qty");
			headings.add("Article Qty Eligible as per SAP inv");
			headings.add("Article Qty Request generated");
			headings.add("Article Qty Discrepancy");
			headings.add("Delivery Challan No.");
			headings.add("Delivery Challan Date");
			headings.add("Discrepancy Type");

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

			row = sheet.createRow(rowCount);
			cell = row.createCell(colCount);
			sheet.addMergedRegion(new CellRangeAddress(rowCount, rowCount, colCount, headings.size() - 1));
			cell.setCellValue("Pfizer Limited");
			cell.setCellStyle(compNameStyle);
			rowCount++;

			row = sheet.createRow(rowCount);
			cell = row.createCell(colCount);
			sheet.addMergedRegion(new CellRangeAddress(rowCount, rowCount, colCount, headings.size() - 1));
			cell.setCellValue("Audit Reconciliation Report - Article Scheme Requests Vs SAP Invoice Billed Items");
			cell.setCellStyle(reportNameStyle);
			rowCount++;

			row = sheet.createRow(rowCount);
			cell = row.createCell(colCount);
			sheet.addMergedRegion(new CellRangeAddress(rowCount, rowCount, colCount, headings.size() - 1));
			cell.setCellValue("Period: " + "01/09/2024" + " to " + "30/09/2024");
			cell.setCellStyle(reportNameStyle);
			rowCount++;

			// headings

			row = sheet.createRow(rowCount);
			colCount = 0;

			cell = row.createCell(colCount);
			sheet.addMergedRegion(new CellRangeAddress(rowCount, rowCount, colCount, colCount + 9));
			cell.setCellValue("");
			cell.setCellStyle(columnHeadingStyle);
			colCount++;

			for (int i = 1; i <= 9; i++) {
				cell = ReportFormats.cell(row, colCount, "", columnHeadingStyle);
				colCount++;
			}

			cell = row.createCell(colCount);
			sheet.addMergedRegion(new CellRangeAddress(rowCount, rowCount, colCount, colCount + 5));
			cell.setCellValue("Sales Product (SKU) Details as per SAP Invoice data");
			cell.setCellStyle(columnHeadingStyle);
			colCount++;

			for (int i = 1; i <= 5; i++) {
				cell = ReportFormats.cell(row, colCount, "", columnHeadingStyle);
				colCount++;
			}

			cell = row.createCell(colCount);
			sheet.addMergedRegion(new CellRangeAddress(rowCount, rowCount, colCount, colCount + 3));
			cell.setCellValue("Entry by CFA for Article Request Eligibility");
			cell.setCellStyle(columnHeadingStyle);
			colCount++;

			for (int i = 1; i <= 3; i++) {
				cell = ReportFormats.cell(row, colCount, "", columnHeadingStyle);
				colCount++;
			}

			cell = row.createCell(colCount);
			sheet.addMergedRegion(new CellRangeAddress(rowCount, rowCount, colCount, colCount + 5));
			cell.setCellValue("");
			cell.setCellStyle(columnHeadingStyle);
			colCount++;

			for (int i = 1; i <= 5; i++) {
				cell = ReportFormats.cell(row, colCount, "", columnHeadingStyle);
				colCount++;
			}
			rowCount++;

			row = sheet.createRow(rowCount);
			colCount = 0;

			for (String string : headings) {
				cell = ReportFormats.cell(row, colCount, string, columnHeadingStyle);
				colCount++;
			}
			rowCount++;

			XSSFCellStyle leftAlign = ReportFormats.setCellAlignment(workbook, ALIGN_LEFT);
			XSSFRow hrow = null;

			XSSFCellStyle headingCellStyle = ReportFormats.heading(workbook);
			XSSFCellStyle datastyleRightwithDecimal = ReportFormats.setCellAlignmentWithTwoDecimal(workbook,
					ALIGN_RIGHT);

			for (articleScheme bean : listSummary) {
				rowCount++;
				colCount = 0;
				hrow = sheet.createRow(rowCount);

				cell = hrow.createCell(colCount++);
				cell.setCellStyle(leftAlign);
				cell.setCellValue(bean.getExp_id());

				cell = hrow.createCell(colCount++);
				cell.setCellStyle(leftAlign);
				cell.setCellValue(bean.getErr_msg() != null ? bean.getErr_msg() : "");

				cell = hrow.createCell(colCount++);
				cell.setCellStyle(leftAlign);
				cell.setCellValue(bean.getTrd_sch_slno() != null ? bean.getTrd_sch_slno() : "");

				cell = hrow.createCell(colCount++);
				cell.setCellStyle(leftAlign);
				cell.setCellValue(bean.getArticle_req_id() != null ? bean.getArticle_req_id() : 0);

				cell = hrow.createCell(colCount++);
				cell.setCellStyle(leftAlign);
				cell.setCellValue(bean.getArticle_req_dtl_id() != null ? bean.getArticle_req_dtl_id() : 0);

				cell = hrow.createCell(colCount++);
				cell.setCellStyle(leftAlign);
				cell.setCellValue(bean.getArticle_schm_name() != null ? bean.getArticle_schm_name() : "");

				cell = hrow.createCell(colCount++);
				cell.setCellStyle(leftAlign);
				cell.setCellValue(bean.getArticle_name() != null ? bean.getArticle_name() : "");

				cell = hrow.createCell(colCount++);
				cell.setCellStyle(leftAlign);
				cell.setCellValue(bean.getArt_sale_prod_cd() != null ? bean.getArt_sale_prod_cd() : "");

				cell = hrow.createCell(colCount++);
				cell.setCellStyle(leftAlign);
				cell.setCellValue(bean.getArt_sale_prod_name() != null ? bean.getArt_sale_prod_name() : "");

				cell = hrow.createCell(colCount++);
				cell.setCellStyle(datastyleRightwithDecimal);
				cell.setCellValue(bean.getArt_billed_qty() != null ? bean.getArt_billed_qty().toString()
						: BigDecimal.ZERO.setScale(2, RoundingMode.HALF_EVEN).toString());

				cell = hrow.createCell(colCount++);
				cell.setCellStyle(datastyleRightwithDecimal);
				cell.setCellValue(bean.getArt_qty() != null ? bean.getArt_qty().toString() : "");

				cell = hrow.createCell(colCount++);
				cell.setCellStyle(leftAlign);
				cell.setCellValue(bean.getInvoice_no() != null ? bean.getInvoice_no() : "");

				cell = hrow.createCell(colCount++);
				cell.setCellStyle(leftAlign);
				cell.setCellValue(bean.getAp() != null ? bean.getAp() : "");

				cell = hrow.createCell(colCount++);
				cell.setCellStyle(leftAlign);
				cell.setCellValue(bean.getSap_plant_cd() != null ? bean.getSap_plant_cd() : "");

				cell = hrow.createCell(colCount++);
				cell.setCellStyle(leftAlign);
				cell.setCellValue(bean.getCompany_cd() != null ? bean.getCompany_cd() : "");

				cell = hrow.createCell(colCount++);
				cell.setCellStyle(leftAlign);
				cell.setCellValue(bean.getCfa_location() != null ? bean.getCfa_location() : "");

				cell = hrow.createCell(colCount++);
				cell.setCellStyle(leftAlign);
				cell.setCellValue(bean.getSap_inv_no() != null ? bean.getSap_inv_no() : "");

				cell = hrow.createCell(colCount++);
				cell.setCellStyle(leftAlign);
				cell.setCellValue(bean.getSap_inv_dt() != null ? bean.getSap_inv_dt().toString() : "");

				cell = hrow.createCell(colCount++);
				cell.setCellStyle(leftAlign);
				cell.setCellValue(bean.getSap_cust_cd() != null ? bean.getSap_cust_cd() : "");

				cell = hrow.createCell(colCount++);
				cell.setCellStyle(leftAlign);
				cell.setCellValue(bean.getCustomer_name() != null ? bean.getCustomer_name() : "");

				cell = hrow.createCell(colCount++);
				cell.setCellStyle(leftAlign);
				cell.setCellValue(bean.getCity() != null ? bean.getCity() : "");

				cell = hrow.createCell(colCount++);
				cell.setCellStyle(leftAlign);
				cell.setCellValue(bean.getSap_sale_prod_cd() != null ? bean.getSap_sale_prod_cd() : "");

				cell = hrow.createCell(colCount++);
				cell.setCellStyle(leftAlign);
				cell.setCellValue(bean.getSap_sale_prod_name() != null ? bean.getSap_sale_prod_name() : "");

				cell = hrow.createCell(colCount++);
				cell.setCellStyle(datastyleRightwithDecimal);
				cell.setCellValue(bean.getBilled_qty() != null ? bean.getBilled_qty().toString()
						: BigDecimal.ZERO.setScale(2, RoundingMode.HALF_EVEN).toString());

				cell = hrow.createCell(colCount++);
				cell.setCellStyle(datastyleRightwithDecimal);
				cell.setCellValue(bean.getFree_qty() != null ? bean.getFree_qty().toString()
						: BigDecimal.ZERO.setScale(2, RoundingMode.HALF_EVEN).toString());

				cell = hrow.createCell(colCount++);
				cell.setCellStyle(datastyleRightwithDecimal);
				cell.setCellValue(bean.getBilling_rate() != null ? bean.getBilling_rate().toString()
						: BigDecimal.ZERO.setScale(2, RoundingMode.HALF_EVEN).toString());

				cell = hrow.createCell(colCount++);
				cell.setCellStyle(datastyleRightwithDecimal);
				cell.setCellValue(bean.getBilling_value() != null ? bean.getBilling_value().toString()
						: BigDecimal.ZERO.setScale(2, RoundingMode.HALF_EVEN).toString());

				cell = hrow.createCell(colCount++);
				cell.setCellStyle(datastyleRightwithDecimal);
				cell.setCellValue(bean.getArticle_sale_bill_qty_entered() != null
						? bean.getArticle_sale_bill_qty_entered().toString()
						: BigDecimal.ZERO.setScale(2, RoundingMode.HALF_EVEN).toString());

				cell = hrow.createCell(colCount++);
				cell.setCellStyle(datastyleRightwithDecimal);
				cell.setCellValue(bean.getArticle_rate() != null ? bean.getArticle_rate().toString()
						: BigDecimal.ZERO.setScale(2, RoundingMode.HALF_EVEN).toString());

				cell = hrow.createCell(colCount++);
				cell.setCellStyle(datastyleRightwithDecimal);
				cell.setCellValue(bean.getArticle_value() != null ? bean.getArticle_value().toString()
						: BigDecimal.ZERO.setScale(2, RoundingMode.HALF_EVEN).toString());

				cell = hrow.createCell(colCount++);
				cell.setCellStyle(datastyleRightwithDecimal);
				cell.setCellValue(bean.getErr_bill_qty() != null
						? bean.getErr_bill_qty().setScale(2, RoundingMode.HALF_EVEN).toString()
						: BigDecimal.ZERO.setScale(2, RoundingMode.HALF_EVEN).toString());

			}

			// System.out.println("actualpath " + actualpath);
			// FileOutputStream fileOut = new FileOutputStream(actualpath.toString());
			// wwbook.write(fileOut);
			// fileOut.close();

			FileOutputStream fileOutputStream = new FileOutputStream(filepath);
			workbook.write(fileOutputStream);

			System.out.println("Excel Created");

		} catch (Exception e) {
			e.printStackTrace();
		} finally {

		}
		return filename;

	}

}
