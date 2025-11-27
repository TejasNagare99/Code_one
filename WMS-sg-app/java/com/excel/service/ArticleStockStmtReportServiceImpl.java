package com.excel.service;

import java.io.File;
import java.io.FileOutputStream;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import org.apache.poi.ss.usermodel.BorderStyle;
import org.apache.poi.ss.usermodel.CreationHelper;
import org.apache.poi.ss.usermodel.DataFormat;
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

import com.excel.model.Article_Stock_Statement;
import com.excel.model.Article_Stock_Statement_Itemwise;
import com.excel.model.SmpItem;
import com.excel.utility.MedicoConstants;
import com.excel.utility.MedicoResources;
import com.excel.utility.ReportFormats;

@Service
public class ArticleStockStmtReportServiceImpl implements ArticleStockStmtReportService, MedicoConstants {

	@Override
	public String generateArticleStockStmtReport(List<SmpItem> smpItemList, List<Article_Stock_Statement> list, List<Article_Stock_Statement_Itemwise> articleStockStatementItemwiseList, String companyname,
			String companycode, String empId, Date finyear_startDate) throws Exception {

		String filename = "Article_Inventory_Report" + new Date().getTime() + ".xlsx";
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
			XSSFSheet sheet = workbook.createSheet("Article Inventory ItemWise Report");

			XSSFRow row = null;
			XSSFCell cell = null;
			int rowCount = 0;
			int colCount = 0;
			int totalColCount = 20;
			XSSFFont headerFont = null;
			
			XSSFCellStyle compNameStyle = workbook.createCellStyle();
			compNameStyle.setAlignment(HorizontalAlignment.LEFT);
			XSSFColor myColor = new XSSFColor(new java.awt.Color(48, 84, 150), new DefaultIndexedColorMap());
			headerFont = workbook.createFont();
			headerFont.setFontHeightInPoints((short) 22);
			headerFont.setColor(myColor);
			headerFont.setBold(true);
			compNameStyle.setFont(headerFont);
			
			XSSFCellStyle reportNameStyle = workbook.createCellStyle();
			headerFont = workbook.createFont();
			headerFont.setFontHeightInPoints((short) 16);
			headerFont.setColor(myColor);
			headerFont.setBold(true);
			reportNameStyle.setFont(headerFont);
			
			
			XSSFCellStyle columnHeadingStyle = workbook.createCellStyle();
			myColor = new XSSFColor(new java.awt.Color(180, 198, 231), new DefaultIndexedColorMap());
			columnHeadingStyle.setFillForegroundColor(myColor);
			columnHeadingStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);
			
			columnHeadingStyle.setAlignment(HorizontalAlignment.CENTER);
			columnHeadingStyle.setVerticalAlignment(VerticalAlignment.CENTER);
			headerFont = workbook.createFont();
			headerFont.setFontHeightInPoints((short) 12);
			headerFont.setBold(true);
			columnHeadingStyle.setFont(headerFont);
			columnHeadingStyle.setBorderTop(BorderStyle.THIN);
			columnHeadingStyle.setTopBorderColor(IndexedColors.BLACK.index);
			columnHeadingStyle.setBorderBottom(BorderStyle.THIN);
			columnHeadingStyle.setBottomBorderColor(IndexedColors.BLACK.index);
			columnHeadingStyle.setBorderLeft(BorderStyle.THIN);
			columnHeadingStyle.setLeftBorderColor(IndexedColors.BLACK.index);
			columnHeadingStyle.setBorderRight(BorderStyle.THIN);
			columnHeadingStyle.setRightBorderColor(IndexedColors.BLACK.index);
//			columnHeadingStyle.setWrapText(true);
			
//			XSSFCellStyle plainLeft = workbook.createCellStyle();
//			plainLeft.setAlignment(HorizontalAlignment.LEFT);
//			headerFont = workbook.createFont();
//			headerFont.setFontHeightInPoints((short) 11);
//			plainLeft.setFont(headerFont);
			
			XSSFCellStyle plainRight = workbook.createCellStyle();
			plainRight.setAlignment(HorizontalAlignment.RIGHT);		
					
			XSSFCellStyle boldWithoutColorCenter = workbook.createCellStyle();
			boldWithoutColorCenter.setAlignment(HorizontalAlignment.CENTER);
			headerFont = workbook.createFont();
			headerFont.setFontHeightInPoints((short) 11);
			headerFont.setBold(true);
			boldWithoutColorCenter.setFont(headerFont);
			
			XSSFCellStyle boldWithoutColorRight = workbook.createCellStyle();
			boldWithoutColorCenter.setAlignment(HorizontalAlignment.RIGHT);
			headerFont = workbook.createFont();
			headerFont.setFontHeightInPoints((short) 11);
			headerFont.setBold(true);
			boldWithoutColorRight.setFont(headerFont);
			
			XSSFCellStyle boldWithoutColorLeft = workbook.createCellStyle();
			boldWithoutColorLeft.setAlignment(HorizontalAlignment.LEFT);
			boldWithoutColorLeft.setFont(headerFont);
						
			DataFormat format = workbook.createDataFormat();
			XSSFCellStyle decimalColor = workbook.createCellStyle();
			decimalColor.setFillForegroundColor(myColor);
			decimalColor.setFont(headerFont);
			decimalColor.setFillPattern(FillPatternType.SOLID_FOREGROUND);
			decimalColor.setDataFormat(format.getFormat("0.00"));
		    
			XSSFCellStyle decimalWithoutColor = workbook.createCellStyle();
			decimalWithoutColor.setAlignment(HorizontalAlignment.RIGHT);
			decimalWithoutColor.setDataFormat(format.getFormat("0.00"));
			
			XSSFCellStyle dateStyle = workbook.createCellStyle();
	        CreationHelper creationHelper = workbook.getCreationHelper();
	        dateStyle.setDataFormat(creationHelper.createDataFormat().getFormat("dd-MM-yyyy"));
			
			List<String> headings = new ArrayList<>();
			
			
			headings.add("Article Code");
			headings.add("Article name");
			headings.add("CFA Location");
			headings.add("Rate");
			// opening 
			headings.add("Opening  Qty");
			headings.add("Opening  Value");
			//GRN
			headings.add("GRN Qty");
//			headings.add("GRN Value");
			
			
			headings.add("Adjustment In Qty");
	
			
			
			//out---------->>
			//dispatch  
			headings.add("Dispatch Qty");
//			headings.add("Dispatch Value");
			headings.add("Adjustment Out Qty");
			headings.add("Transfer Out Qty");
			//closing stock
			headings.add("Closing Qty");
			headings.add("Closing Value");
			headings.add("Pending");
			
//			headings.add("Linked Scheme Name");

			
			
			row = sheet.createRow(rowCount);
			cell = row.createCell(colCount);
			sheet.addMergedRegion(new CellRangeAddress(rowCount, rowCount, colCount, headings.size() - 1));
			cell.setCellValue(companyname);
			cell.setCellStyle(compNameStyle);
			rowCount++;
			
			row = sheet.createRow(rowCount);
			cell = row.createCell(colCount);
			
			sheet.addMergedRegion(new CellRangeAddress(rowCount, rowCount, colCount, headings.size() - 1));
			cell.setCellValue("Article Inventory ItemWise Report");
			cell.setCellStyle(reportNameStyle);
			rowCount++;
			
			row = sheet.createRow(rowCount);
			cell = row.createCell(colCount);
//			   LocalDate currentDate = LocalDate.now();
//		        System.out.println("Current Date: " + currentDate);
			
			sheet.addMergedRegion(new CellRangeAddress(rowCount, rowCount, colCount, headings.size() - 1));
			
	        SimpleDateFormat outputFormat = new SimpleDateFormat("dd-MM-yyyy");
//	        String toDateFormatted = outputFormat.format(toDateParsed);
	        
	        
			cell.setCellValue("Period :"+outputFormat.format(finyear_startDate) +" To "+outputFormat.format( new Date()));
			cell.setCellStyle(reportNameStyle);
			rowCount++;
			//Headings
			//row1			
			row = sheet.createRow(rowCount);
			colCount = 0;
			
//			cell = row.createCell(colCount);
//			sheet.addMergedRegion(new CellRangeAddress(rowCount, rowCount + 1, colCount, colCount));
//			cell.setCellValue("Article name");
//			cell.setCellStyle(columnHeadingStyle);
//			colCount++;
//				
//			cell = row.createCell(colCount);
//			sheet.addMergedRegion(new CellRangeAddress(rowCount, rowCount + 1, colCount, colCount));
//			cell.setCellValue("CFA Location");
//			cell.setCellStyle(columnHeadingStyle);
//			colCount++;
//					
//			cell = row.createCell(colCount);
//			sheet.addMergedRegion(new CellRangeAddress(rowCount, rowCount + 1, colCount, colCount));
//			cell.setCellValue("Batch");
//			cell.setCellStyle(columnHeadingStyle);
//			colCount++;
//			
//			cell = row.createCell(colCount);
//			sheet.addMergedRegion(new CellRangeAddress(rowCount, rowCount + 1, colCount, colCount));
//			cell.setCellValue("Rate");
//			cell.setCellStyle(columnHeadingStyle);
//			colCount++;
			
			cell = row.createCell(colCount);
			sheet.addMergedRegion(new CellRangeAddress(rowCount, rowCount, colCount, colCount + 3));
			cell.setCellValue("");
			cell.setCellStyle(columnHeadingStyle);
//			colCount+=4;
			
			for (int i = 0; i <= 3; i++) {
				cell = ReportFormats.cell(row, colCount, "", columnHeadingStyle);
				colCount++;
			}
			
			cell = row.createCell(colCount);
			sheet.addMergedRegion(new CellRangeAddress(rowCount, rowCount, colCount, colCount + 1));
			cell.setCellValue("Opening Stock");
			cell.setCellStyle(columnHeadingStyle);
			colCount++;
			
			cell = ReportFormats.cell(row, colCount, "", columnHeadingStyle);
			colCount++;
			
			cell = row.createCell(colCount);
			sheet.addMergedRegion(new CellRangeAddress(rowCount, rowCount, colCount, colCount + 1));
			cell.setCellValue("In");
			cell.setCellStyle(columnHeadingStyle);
			colCount++;
			
			
			
			
	
			
			
			cell = ReportFormats.cell(row, colCount, "", columnHeadingStyle);
			colCount++;
//			
		
			
			
			
//			cell = ReportFormats.cell(row, colCount, "", columnHeadingStyle);
//			colCount++;
			
	
			cell = row.createCell(colCount);
			sheet.addMergedRegion(new CellRangeAddress(rowCount, rowCount, colCount, colCount + 2));
			cell.setCellValue("Out");
			cell.setCellStyle(columnHeadingStyle);
			colCount++;
			
			for (int i = 1; i <= 2; i++) {
				cell = ReportFormats.cell(row, colCount, "", columnHeadingStyle);
				colCount++;
			}
			
			
			cell = row.createCell(colCount);
			sheet.addMergedRegion(new CellRangeAddress(rowCount, rowCount, colCount, colCount+1));
			cell.setCellValue("Closing Stock");
			cell.setCellStyle(columnHeadingStyle);
			colCount++;
			
			cell = ReportFormats.cell(row, colCount, "", columnHeadingStyle);
			colCount++;
			
			cell = ReportFormats.cell(row, colCount, "", columnHeadingStyle);
			colCount++;
			
//			cell = row.createCell(colCount);
//			sheet.addMergedRegion(new CellRangeAddress(rowCount, rowCount + 1, colCount, colCount));
//			cell.setCellValue("Linked Scheme Name");
//			cell.setCellStyle(columnHeadingStyle);
//			colCount++;
			
//			cell = row.createCell(colCount);
//			sheet.addMergedRegion(new CellRangeAddress(rowCount, rowCount, colCount, colCount + 3));
//			cell.setCellValue("");
//			cell.setCellStyle(columnHeadingStyle);
//			colCount++;
			
//			for (int i = 1; i <= 3; i++) {
//				cell = ReportFormats.cell(row, colCount, "", columnHeadingStyle);
//				colCount++;
//			}
//
//			
//			cell = row.createCell(colCount);
//			sheet.addMergedRegion(new CellRangeAddress(rowCount, rowCount, colCount, colCount + 1));
//			cell.setCellValue("Scheme Validity");
//			cell.setCellStyle(columnHeadingStyle);
//			colCount++;
//			
//			cell = ReportFormats.cell(row, colCount, "", columnHeadingStyle);
			
			//row2
			rowCount++;
			row = sheet.createRow(rowCount);
			colCount = 0;
			
//			for (int i = 1; i <= 4; i++) {
//				cell = ReportFormats.cell(row, colCount, "", columnHeadingStyle);
//				colCount++;
//				
//			}
			
			
			
			
			
			for (String string : headings) {
				cell = ReportFormats.cell(row, colCount, string, columnHeadingStyle);
				colCount++;
			}
			
//			for (int i = 1; i <= 4; i++) {
//				cell = ReportFormats.cell(row, colCount, "Qty", columnHeadingStyle);
//				colCount++;
//				
//				cell = ReportFormats.cell(row, colCount, "Value", columnHeadingStyle);
//				colCount++;
//			}
//			
//			cell = ReportFormats.cell(row, colCount, "Linked Scheme Name", columnHeadingStyle);
//			colCount++;
//			
//			cell = ReportFormats.cell(row, colCount, "Start Date", columnHeadingStyle);
//			colCount++;
//			
//			cell = ReportFormats.cell(row, colCount, "End Date", columnHeadingStyle);
//			colCount++;
			
			
			rowCount++;
			
			Long oldLoc = 0l;
			Long newLoc = 0l;

			Long oldProdId = 0l;
			Long newProdId = 0l;
			
			BigDecimal totOpenVal = BigDecimal.ZERO;
			BigDecimal totGrnVal = BigDecimal.ZERO;
			BigDecimal totDispatchVal = BigDecimal.ZERO;
			BigDecimal totCloseVal = BigDecimal.ZERO;
			
			for (Article_Stock_Statement_Itemwise obj : articleStockStatementItemwiseList) {
			
				
				newLoc = obj.getLoc_id();
				
				newProdId = obj.getSmp_prod_id();
				
				row = sheet.createRow(rowCount);
				colCount = 0;
				
				
				if (oldLoc.compareTo(newLoc) != 0 || oldProdId.compareTo(newProdId) != 0) {

					cell = ReportFormats.cell(row, colCount, obj.getProduct_code(), boldWithoutColorLeft);
					colCount++;
					
					cell = ReportFormats.cell(row, colCount, obj.getArticle_name(), boldWithoutColorLeft);
					colCount++;
					
					cell = ReportFormats.cell(row, colCount, obj.getLoc_name(), null);
					colCount++;
							
//					cell = ReportFormats.cell(row, colCount, obj.getBatch_no(), plainRight);
//					colCount++;
					
//					if (obj.getExpry_date() == null || obj.getExpry_date().trim().equals("")) {
//						cell = ReportFormats.cell(row, colCount, "", null);
//					} else {
//						cell = ReportFormats.cellDate(row, colCount,
//								MedicoResources.convert_DD_MM_YYYY_toDate(obj.getExpry_date()), dateStyle);
//					}
//					colCount++;
					
					cell = ReportFormats.cellNum(row, colCount,obj.getUnit_price() == null ?0l  :obj.getUnit_price().doubleValue(), decimalWithoutColor);
					colCount++;
					
					cell = ReportFormats.cellNum(row, colCount,obj.getOpening_qty() == null ?0l  :obj.getOpening_qty().doubleValue(), boldWithoutColorRight);
					colCount++;
					
					cell = ReportFormats.cellNum(row, colCount,obj.getOpening_value() == null ?0l  :obj.getOpening_value().doubleValue(), decimalWithoutColor);
					colCount++;
					
					
					cell = ReportFormats.cellNum(row, colCount,obj.getGrnqty() == null ?0l  :obj.getGrnqty().doubleValue(), boldWithoutColorRight);
					colCount++;
					
//					cell = ReportFormats.cellNum(row, colCount,obj.getGrn_value() == null ?0l  :obj.getGrn_value().doubleValue(), decimalWithoutColor);
//					colCount++;
					
					
					cell = ReportFormats.cellLong(row, colCount,obj.getAdjdtl_in_qty() == null ?0l  :obj.getAdjdtl_in_qty().longValue(), boldWithoutColorRight);
					colCount++;
					
		
					
					
					
					cell = ReportFormats.cellNum(row, colCount,obj.getDisp_qty() == null ?0l  :obj.getDisp_qty().doubleValue(), boldWithoutColorRight);
					colCount++;
					
//					cell = ReportFormats.cellNum(row, colCount,obj.getDsp_value() == null ?0l  :obj.getDsp_value().doubleValue(), decimalWithoutColor);
//					colCount++;
					
					
					cell = ReportFormats.cellLong(row, colCount,obj.getAdjdtl_out_qty() == null ?0l  :obj.getAdjdtl_out_qty().longValue(), boldWithoutColorRight);
					colCount++;
					cell = ReportFormats.cellLong(row, colCount,obj.getTrf_out_qty() == null ?0l  :obj.getTrf_out_qty().longValue(), boldWithoutColorRight);
					colCount++;
							
					cell = ReportFormats.cellNum(row, colCount,obj.getCl_stock_qty() == null ?0l  :obj.getCl_stock_qty().doubleValue(), boldWithoutColorRight);
					colCount++;
					
					cell = ReportFormats.cellNum(row, colCount,obj.getCl_stock_val() == null ?0l  :obj.getCl_stock_val().doubleValue(), decimalWithoutColor);
					colCount++;
					
					cell = ReportFormats.cellLong(row, colCount,obj.getPending() == null ?0l  :obj.getPending().longValue(), boldWithoutColorRight);
					colCount++;
//					
//					cell = ReportFormats.cellLong(row, colCount,obj.getGrn_qty() == null ?0l  :obj.getGrn_qty().longValue(), boldWithoutColorRight);
//					colCount++;
//					
//					cell = ReportFormats.cellLong(row, colCount,obj.getDisp_qty() == null ?0l  :obj.getDisp_qty().longValue(), boldWithoutColorRight);
//					colCount++;
					
			
				
					totOpenVal = totOpenVal.add(obj.getOpening_value());   
					totGrnVal = totGrnVal.add(obj.getGrn_value());    
					totDispatchVal = totDispatchVal.add(obj.getDsp_value());
					totCloseVal = totCloseVal.add(obj.getCl_stock_val());  
					
					
					
				} else {
					for (int i = 1; i <= 17; i++) {
						cell = ReportFormats.cell(row, colCount, "", null);
						colCount++;
					}
					
				}
			
				

				
				
				oldLoc = newLoc;
				oldProdId = newProdId;
				
				rowCount++;
						
			}
			
			row = sheet.createRow(rowCount);
			colCount = 0;
						
			cell = ReportFormats.cell(row, colCount, "Value Totals", decimalColor);
			colCount++;
			
			for (int i = 1; i <= 4; i++) {
				cell = ReportFormats.cell(row, colCount, "", decimalColor);
				colCount++;
			}
			
			cell = ReportFormats.cellNum(row, colCount,totOpenVal == null ?0l  :totOpenVal.doubleValue(), decimalColor);
			colCount++;
			
//			cell = ReportFormats.cell(row, colCount, "", decimalColor);
//			colCount++;
			
//			cell = ReportFormats.cellNum(row, colCount,totGrnVal == null ?0l  :totGrnVal.doubleValue(), decimalColor);
//			colCount++;
			
//			for (int i = 1; i <= 2; i++) {
//				cell = ReportFormats.cell(row, colCount, "", decimalColor);
//				colCount++;
//			}
			
//			cell = ReportFormats.cellNum(row, colCount,totDispatchVal == null ?0l  :totDispatchVal.doubleValue(), decimalColor);
//			colCount++;
			
			for (int i = 1; i <= 6; i++) {
				cell = ReportFormats.cell(row, colCount, "", decimalColor);
				colCount++;
			}
			
			cell = ReportFormats.cellNum(row, colCount,totCloseVal == null ?0l  :totCloseVal.doubleValue(), decimalColor);
			colCount++;
			
			cell = ReportFormats.cell(row, colCount, "", decimalColor);
						
			sheet = workbook.createSheet("Article Inventory Batchwise Report");

			row = null;
			cell = null;
			rowCount = 0;
			colCount = 0;
			
			headings = new ArrayList<>();
			
			
			headings.add("Article Code");
			headings.add("Article name");
			headings.add("CFA Location");
			headings.add("Batch");
			headings.add("Batch Expiry Date");
			headings.add("Rate");
			// opening 
			headings.add("Opening  Qty");
			headings.add("Opening  Value");
			//GRN
			headings.add("GRN Qty");
//			headings.add("GRN Value");
			
			
			headings.add("Adjustment In Qty");
	
			
			
			//out---------->>
			//dispatch  
			headings.add("Dispatch Qty");
//			headings.add("Dispatch Value");
			headings.add("Adjustment Out Qty");
			headings.add("Transfer Out Qty");
			//closing stock
			headings.add("Closing Qty");
			headings.add("Closing Value");
			
			

			
			
			row = sheet.createRow(rowCount);
			cell = row.createCell(colCount);
			sheet.addMergedRegion(new CellRangeAddress(rowCount, rowCount, colCount, headings.size() - 1));
			cell.setCellValue(companyname);
			cell.setCellStyle(compNameStyle);
			rowCount++;
			
			row = sheet.createRow(rowCount);
			cell = row.createCell(colCount);
			
			sheet.addMergedRegion(new CellRangeAddress(rowCount, rowCount, colCount, headings.size() - 1));
			cell.setCellValue("Article Inventory Batchwise Report");
			cell.setCellStyle(reportNameStyle);
			rowCount++;
			
			row = sheet.createRow(rowCount);
			cell = row.createCell(colCount);
//			   LocalDate currentDate = LocalDate.now();
//		        System.out.println("Current Date: " + currentDate);
			
			sheet.addMergedRegion(new CellRangeAddress(rowCount, rowCount, colCount, headings.size() - 1));
		
//	        String toDateFormatted = outputFormat.format(toDateParsed);
	        
	        
			cell.setCellValue("Period :"+outputFormat.format(finyear_startDate) +" To "+outputFormat.format( new Date()));
			cell.setCellStyle(reportNameStyle);
			rowCount++;
			//Headings
			//row1			
			row = sheet.createRow(rowCount);
			colCount = 0;
			

			
			cell = row.createCell(colCount);
			sheet.addMergedRegion(new CellRangeAddress(rowCount, rowCount, colCount, colCount + 5));
			cell.setCellValue("");
			cell.setCellStyle(columnHeadingStyle);
//			colCount+=4;
			
			for (int i = 0; i <= 5; i++) {
				cell = ReportFormats.cell(row, colCount, "", columnHeadingStyle);
				colCount++;
			}
			
			cell = row.createCell(colCount);
			sheet.addMergedRegion(new CellRangeAddress(rowCount, rowCount, colCount, colCount + 1));
			cell.setCellValue("Opening Stock");
			cell.setCellStyle(columnHeadingStyle);
			colCount++;
			
			cell = ReportFormats.cell(row, colCount, "", columnHeadingStyle);
			colCount++;
			
			cell = row.createCell(colCount);
			sheet.addMergedRegion(new CellRangeAddress(rowCount, rowCount, colCount, colCount + 1));
			cell.setCellValue("In");
			cell.setCellStyle(columnHeadingStyle);
			colCount++;
			
			
			
			
	
			
			
			cell = ReportFormats.cell(row, colCount, "", columnHeadingStyle);
			colCount++;
//			
		
			
			
			
//			cell = ReportFormats.cell(row, colCount, "", columnHeadingStyle);
//			colCount++;
			
	
			cell = row.createCell(colCount);
			sheet.addMergedRegion(new CellRangeAddress(rowCount, rowCount, colCount, colCount + 2));
			cell.setCellValue("Out");
			cell.setCellStyle(columnHeadingStyle);
			colCount++;
			
			for (int i = 1; i <= 2; i++) {
				cell = ReportFormats.cell(row, colCount, "", columnHeadingStyle);
				colCount++;
			}
			
			
			cell = row.createCell(colCount);
			sheet.addMergedRegion(new CellRangeAddress(rowCount, rowCount, colCount, colCount+1));
			cell.setCellValue("Closing Stock");
			cell.setCellStyle(columnHeadingStyle);
			colCount++;
			
			cell = ReportFormats.cell(row, colCount, "", columnHeadingStyle);
			colCount++;
			

			
			//row2
			rowCount++;
			row = sheet.createRow(rowCount);
			colCount = 0;
			

			
			
			
			for (String string : headings) {
				cell = ReportFormats.cell(row, colCount, string, columnHeadingStyle);
				colCount++;
			}
			

			
			
			rowCount++;
			
			String oldBatch = "";
			String newBatch = "";
			
			oldLoc = 0l;
			newLoc = 0l;

			oldProdId = 0l;
			newProdId = 0l;
			
			totOpenVal = BigDecimal.ZERO;
			totGrnVal = BigDecimal.ZERO;
			totDispatchVal = BigDecimal.ZERO;
			totCloseVal = BigDecimal.ZERO;
			
			for (Article_Stock_Statement obj : list) {
			
				newBatch = obj.getBatch_no();
				
				newLoc = obj.getLoc_id();
				
				newProdId = obj.getSmp_prod_id();
				
				row = sheet.createRow(rowCount);
				colCount = 0;
				
				
				if (oldLoc.compareTo(newLoc) != 0 || oldProdId.compareTo(newProdId) != 0 || !oldBatch.equals(newBatch)) {

					cell = ReportFormats.cell(row, colCount, obj.getProduct_code(), boldWithoutColorLeft);
					colCount++;
					
					cell = ReportFormats.cell(row, colCount, obj.getArticle_name(), boldWithoutColorLeft);
					colCount++;
					
					cell = ReportFormats.cell(row, colCount, obj.getLoc_name(), null);
					colCount++;
							
					cell = ReportFormats.cell(row, colCount, obj.getBatch_no(), plainRight);
					colCount++;
					
					if (obj.getExpry_date() == null || obj.getExpry_date().trim().equals("")) {
						cell = ReportFormats.cell(row, colCount, "", null);
					} else {
						cell = ReportFormats.cellDate(row, colCount,
								MedicoResources.convert_DD_MM_YYYY_toDate(obj.getExpry_date()), dateStyle);
					}
					colCount++;
					
					cell = ReportFormats.cellNum(row, colCount,obj.getUnit_price() == null ?0l  :obj.getUnit_price().doubleValue(), decimalWithoutColor);
					colCount++;
					
					cell = ReportFormats.cellNum(row, colCount,obj.getOpening_qty() == null ?0l  :obj.getOpening_qty().doubleValue(), boldWithoutColorRight);
					colCount++;
					
					cell = ReportFormats.cellNum(row, colCount,obj.getOpening_value() == null ?0l  :obj.getOpening_value().doubleValue(), decimalWithoutColor);
					colCount++;
					
					
					cell = ReportFormats.cellNum(row, colCount,obj.getGrnqty() == null ?0l  :obj.getGrnqty().doubleValue(), boldWithoutColorRight);
					colCount++;
					
//					cell = ReportFormats.cellNum(row, colCount,obj.getGrn_value() == null ?0l  :obj.getGrn_value().doubleValue(), decimalWithoutColor);
//					colCount++;
					
					
					cell = ReportFormats.cellLong(row, colCount,obj.getAdjdtl_in_qty() == null ?0l  :obj.getAdjdtl_in_qty().longValue(), boldWithoutColorRight);
					colCount++;
					
		
					
					
					
					cell = ReportFormats.cellNum(row, colCount,obj.getDisp_qty() == null ?0l  :obj.getDisp_qty().doubleValue(), boldWithoutColorRight);
					colCount++;
					
//					cell = ReportFormats.cellNum(row, colCount,obj.getDsp_value() == null ?0l  :obj.getDsp_value().doubleValue(), decimalWithoutColor);
//					colCount++;
					
					
					cell = ReportFormats.cellLong(row, colCount,obj.getAdjdtl_out_qty() == null ?0l  :obj.getAdjdtl_out_qty().longValue(), boldWithoutColorRight);
					colCount++;
					cell = ReportFormats.cellLong(row, colCount,obj.getPending() == null ?0l  :obj.getTrf_out_qty().longValue(), boldWithoutColorRight);
					colCount++;
							
					cell = ReportFormats.cellNum(row, colCount,obj.getCl_stock_qty() == null ?0l  :obj.getCl_stock_qty().doubleValue(), boldWithoutColorRight);
					colCount++;
					
					cell = ReportFormats.cellNum(row, colCount,obj.getCl_stock_val() == null ?0l  :obj.getCl_stock_val().doubleValue(), decimalWithoutColor);
					colCount++;
					
					
//					
//					cell = ReportFormats.cellLong(row, colCount,obj.getGrn_qty() == null ?0l  :obj.getGrn_qty().longValue(), boldWithoutColorRight);
//					colCount++;
//					
//					cell = ReportFormats.cellLong(row, colCount,obj.getDisp_qty() == null ?0l  :obj.getDisp_qty().longValue(), boldWithoutColorRight);
//					colCount++;
					
			
				
					totOpenVal = totOpenVal.add(obj.getOpening_value());   
					totGrnVal = totGrnVal.add(obj.getGrn_value());    
					totDispatchVal = totDispatchVal.add(obj.getDsp_value());
					totCloseVal = totCloseVal.add(obj.getCl_stock_val());  
					
					
					
				} else {
					for (int i = 1; i <= 17; i++) {
						cell = ReportFormats.cell(row, colCount, "", null);
						colCount++;
					}
					
				}
			
				
//				cell = ReportFormats.cell(row, colCount, obj.getLink_scheme_name(), null);
//				colCount++;
				
//				if (obj.getValid_from() == null || obj.getValid_from().trim().equals("")) {
//					cell = ReportFormats.cell(row, colCount, "", null);
//				} else {
//					cell = ReportFormats.cellDate(row, colCount,
//							MedicoResources.convert_DD_MM_YYYY_toDate(obj.getValid_from()), dateStyle);
//				}
//				colCount++;
//				
//				if (obj.getValid_to() == null || obj.getValid_to().trim().equals("")) {
//					cell = ReportFormats.cell(row, colCount, "", null);
//				} else {
//					cell = ReportFormats.cellDate(row, colCount,
//							MedicoResources.convert_DD_MM_YYYY_toDate(obj.getValid_to()), dateStyle);
//				}
				
				
				
				oldBatch = newBatch;
				oldLoc = newLoc;
				oldProdId = newProdId;
				
				rowCount++;
						
			}
			
			row = sheet.createRow(rowCount);
			colCount = 0;
						
			cell = ReportFormats.cell(row, colCount, "Value Totals", decimalColor);
			colCount++;
			
			for (int i = 1; i <= 6; i++) {
				cell = ReportFormats.cell(row, colCount, "", decimalColor);
				colCount++;
			}
			
			cell = ReportFormats.cellNum(row, colCount,totOpenVal == null ?0l  :totOpenVal.doubleValue(), decimalColor);
			colCount++;
			
			cell = ReportFormats.cell(row, colCount, "", decimalColor);
			colCount++;
			
//			cell = ReportFormats.cellNum(row, colCount,totGrnVal == null ?0l  :totGrnVal.doubleValue(), decimalColor);
//			colCount++;
			
			for (int i = 1; i <= 2; i++) {
				cell = ReportFormats.cell(row, colCount, "", decimalColor);
				colCount++;
			}
			
//			cell = ReportFormats.cellNum(row, colCount,totDispatchVal == null ?0l  :totDispatchVal.doubleValue(), decimalColor);
//			colCount++;
			
			for (int i = 1; i <= 3; i++) {
				cell = ReportFormats.cell(row, colCount, "", decimalColor);
				colCount++;
			}
			
			cell = ReportFormats.cellNum(row, colCount,totCloseVal == null ?0l  :totCloseVal.doubleValue(), decimalColor);
			colCount++;
			
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
