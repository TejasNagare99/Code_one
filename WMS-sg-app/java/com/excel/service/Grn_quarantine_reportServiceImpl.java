package com.excel.service;

import java.io.File;
import java.io.FileOutputStream;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import org.apache.poi.ss.usermodel.BorderStyle;
import org.apache.poi.ss.usermodel.FillPatternType;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.apache.poi.xssf.usermodel.XSSFFont;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.excel.model.Grn_quarantine_prod_summary;
import com.excel.model.GrnquarantineProd_Detail;
import com.excel.model.Usermaster;
import com.excel.repository.UserMasterRepository;
import com.excel.security.PasswordManager;
import com.excel.utility.MedicoConstants;
import com.excel.utility.ReportFormats;
import com.excel.utility.Utility;

import net.lingala.zip4j.core.ZipFile;
import net.lingala.zip4j.model.ZipParameters;
import net.lingala.zip4j.util.Zip4jConstants;

@Service
public class Grn_quarantine_reportServiceImpl implements Grn_quarantine_reportService, MedicoConstants{
	@Autowired private UserMasterService usermasterservice;

	@Autowired private UserMasterRepository usermasterrepository;
	@Override
	public String generateGrn_quarantine_report(List<Grn_quarantine_prod_summary> summarylst,
			List<GrnquarantineProd_Detail> detail_lst,String Startdt,String Enddt,String Companyname,String empId) throws Exception {
		String nameOnly = "GRNDetailReport" + new Date().getTime();
		String filename = "GRNDetailReport" + new Date().getTime() + ".xlsx";
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
			XSSFSheet sheet = workbook.createSheet("GRN Quarantine Detail Report");
			
			XSSFRow row = null;
			XSSFCell cell = null;
			int rowCount = 0;
			int colCount = 0;
			
			//Detail
			String headings[] = { "GRN No.","GRN Type","Location","Supplier","GRN Date","Transfer No","Transfer Date","L.R No","L.R Date","Lorry_No / Transporter Name",
					"Division Name","Supplier Code","PO No.","PO Date","Description","Batch No","Batch Expiry Date","GRN QTY","Batch Rate","Value (Rs.Ps)"," Product Type/Category",
					"Product code","Fcode","GCMA Indicator","GCMA Code","GCMA Exp Date","Mfg. Dt.","No. of Cases","Remark"};
			
			XSSFCellStyle headingCellStyle = ReportFormats.heading(workbook);
			XSSFCellStyle columnHeadingStyle = ReportFormats.columnHeading(workbook);
			XSSFCellStyle cellAlignment = ReportFormats.setCellAlignment(workbook, ALIGN_RIGHT);
			XSSFCellStyle cellAlignment2 = ReportFormats.setCellAlignment(workbook, ALIGN_LEFT);
			
			XSSFCellStyle decimal_format_2 = workbook.createCellStyle();;
			decimal_format_2.setAlignment(HorizontalAlignment.RIGHT);
			decimal_format_2.setDataFormat(workbook.createDataFormat().getFormat("0.00"));
			
			
			
			row = sheet.createRow(rowCount);
			cell = row.createCell(colCount);
			sheet.addMergedRegion(new CellRangeAddress(rowCount, rowCount, colCount, colCount + headings.length - 1));
			cell.setCellValue(Companyname);
			cell.setCellStyle(headingCellStyle);
			rowCount++;
			
			row = sheet.createRow(rowCount);
			cell = row.createCell(colCount);
			sheet.addMergedRegion(new CellRangeAddress(rowCount, rowCount, colCount, colCount + headings.length - 1));
			cell.setCellValue("GRN Detail Report - QUARANTINE");
			cell.setCellStyle(headingCellStyle);
			rowCount++;

			row = sheet.createRow(rowCount);
			cell = row.createCell(colCount);
			sheet.addMergedRegion(new CellRangeAddress(rowCount, rowCount, colCount, colCount + headings.length - 1));
			cell.setCellValue("Period: "+Startdt+" to "+Enddt);
			cell.setCellStyle(headingCellStyle);
			
			rowCount += 1;
			row = sheet.createRow(rowCount);
			
			for (String heading : headings) {
				cell = ReportFormats.cell(row, colCount, heading, columnHeadingStyle);
				colCount++;
			}
			
			sheet.groupRow(0, 2);
			sheet.createFreezePane(0, 4);
			
			
			
			    String newDivision = "";
			    String oldDivision = "";
			    // String newGrnDate = "";
			    // String oldGrnDate = "";
			    // String newGrnNo = "";
			    // String oldGrnNo = "";

			    BigDecimal totalValue = new BigDecimal(0);
			    BigDecimal divisionTotal[] = new BigDecimal[7];
			    BigDecimal reportTotal[] = new BigDecimal[7];

			    // Arrays.fill(totalValue, BigDecimal.ZERO);
			    Arrays.fill(divisionTotal, BigDecimal.ZERO);
			    Arrays.fill(reportTotal, BigDecimal.ZERO);
			
			
			for (GrnquarantineProd_Detail o : detail_lst) {
				
				colCount = 0;
				rowCount++;
				
				newDivision = o.getDivision_name();
//				if (!(newDivision.equalsIgnoreCase(oldDivision)) && !oldDivision.equalsIgnoreCase("")) {
//					
//					row = sheet.createRow(rowCount);
//					
//					cell = ReportFormats.cell(row, colCount,"Division Total".toString(), null);
//					colCount++;
//					
//					for(int i=0;i<=18;i++)
//					{
//						cell = ReportFormats.cell(row, colCount,"", null);
//						colCount++;	
//					}
//					
//					cell = row.createCell(21);
//					cell = ReportFormats.cellNum(row, colCount, divisionTotal[0] == null ? 0l : divisionTotal[0].doubleValue(), cellAlignment);
//					colCount++;
//					
//					cell = row.createCell(22);
//					cell = ReportFormats.cell(row, colCount, "", cellAlignment);
//					colCount++;
//					
//					cell = row.createCell(23);
//					cell = ReportFormats.cellNum(row, colCount, divisionTotal[1] == null ? 0l : divisionTotal[1].doubleValue(), cellAlignment);
//					colCount++;
//					
//					cell = row.createCell(24);
//					cell = ReportFormats.cell(row, colCount, "", cellAlignment);
//					colCount++;
//					
//					cell = row.createCell(25);
//					cell = ReportFormats.cellNum(row, colCount, divisionTotal[2] == null ? 0l : divisionTotal[2].doubleValue(), cellAlignment);
//					colCount++;
//					
//					cell = row.createCell(26);
//					cell = ReportFormats.cell(row, colCount, "", cellAlignment);
//					colCount++;
//					
//					cell = row.createCell(27);
//					cell = ReportFormats.cellNum(row, colCount, divisionTotal[3] == null ? 0l : divisionTotal[3].doubleValue(), cellAlignment);
//					colCount++;
//					
//					cell = row.createCell(28);
//					cell = ReportFormats.cell(row, colCount, "", cellAlignment);
//					colCount++;
//					
//					cell = row.createCell(29);
//					cell = ReportFormats.cellNum(row, colCount, divisionTotal[4] == null ? 0l : divisionTotal[4].doubleValue(), cellAlignment);
//					colCount++;
//					
//					cell = row.createCell(30);
//					cell = ReportFormats.cell(row, colCount, "", cellAlignment);
//					colCount++;
//					
//					cell = row.createCell(31);
//					cell = ReportFormats.cellNum(row, colCount, divisionTotal[5] == null ? 0l : divisionTotal[5].doubleValue(), cellAlignment);
//					colCount++;
//					
//					
//					cell = row.createCell(32);
//					cell = ReportFormats.cellNum(row, colCount, divisionTotal[6] == null ? 0l : divisionTotal[6].doubleValue(), cellAlignment);
//					colCount++;
//					
//					  Arrays.fill(divisionTotal, BigDecimal.ZERO);
//					    colCount = 0;
//					    rowCount++;
//				}
				
//				if (!(newDivision.equalsIgnoreCase(oldDivision))) {
//				    colCount = 0;
//				    row = sheet.createRow(rowCount);
//				    cell = ReportFormats.cell(row, colCount, "Division : " + o.getDivision_name(), null);
//				    colCount = 0;
//				    rowCount++;
//
//				}
			
				
				
				row = sheet.createRow(rowCount);
				cell = ReportFormats.cell(row, colCount, o.getGrn_no(), null);
				colCount++;
				
				cell = ReportFormats.cell(row, colCount,o.getGrn_type(), null);
				colCount++;
				
				cell = ReportFormats.cell(row, colCount,o.getLoc_nm(), null);
				colCount++;
				
				cell = ReportFormats.cell(row, colCount,o.getSup_nm(), null);
				colCount++;	
				
				cell = ReportFormats.cell(row, colCount,o.getGrn_date(), null);
				colCount++;
				
				cell = ReportFormats.cell(row, colCount,o.getTransfer_no(), null);
				colCount++;
				
				cell = ReportFormats.cell(row, colCount,o.getGrn_transfer_dt(), null);
				colCount++;
			
				cell = ReportFormats.cell(row, colCount,o.getGrn_lr_no(), null);
				colCount++;
				
				cell = ReportFormats.cell(row, colCount,o.getGrn_lr_dt(), null);
				colCount++;
				
				cell = ReportFormats.cell(row, colCount,o.getTransporter(), null);
				colCount++;
				
				cell = ReportFormats.cell(row, colCount,o.getDivision_name(), null);
				colCount++;
				
				cell = ReportFormats.cell(row, colCount,o.getSuppliercode(), null);
				colCount++;
				
				cell = ReportFormats.cell(row, colCount,o.getPo_no(), null);
				colCount++;
				
				cell = ReportFormats.cell(row, colCount,o.getPo_date(), null);
				colCount++;
				
				cell = ReportFormats.cell(row, colCount,o.getProduct_name(), null);
				colCount++;
				
				cell = ReportFormats.cell(row, colCount,o.getBatch_no(), null);
				colCount++;
				
				cell = ReportFormats.cell(row, colCount,o.getBatch_expiry_date(), null);
				colCount++;
				
			//	cell = ReportFormats.cell(row, colCount,o.getBin_number(), cellAlignment); //cess 
			//	colCount++;
				
			//	cell = ReportFormats.cellNum(row, colCount,o.getGrn_rate() == null ? 0l : o.getGrn_rate().doubleValue(), cellAlignment);
		//		colCount++;
				//
				cell = ReportFormats.cellNum(row, colCount,o.getGrn_qty() == null ? 0l : o.getGrn_qty().doubleValue(), decimal_format_2);
				colCount++;
				
				cell = ReportFormats.cellNum(row, colCount,o.getGrn_rate() == null ? 0l : o.getGrn_rate().doubleValue(), decimal_format_2);
				colCount++;
				
				cell = ReportFormats.cellNum(row, colCount,o.getValue() == null ? 0l : o.getValue().doubleValue(), decimal_format_2);
				colCount++;
				
		//		cell = ReportFormats.cellNum(row, colCount,o.getTotal_grn_qty() == null ? 0l : o.getTotal_grn_qty().doubleValue(), cellAlignment);
		//		colCount++;
				
		//		cell = ReportFormats.cellNum(row, colCount,o.getValue() == null ? 0l : o.getValue().doubleValue(), cellAlignment);
		//		colCount++;
				
//				cell = ReportFormats.cellNum(row, colCount,o.getCgst_rate() == null ? 0l : o.getCgst_rate().doubleValue(), cellAlignment);
//				colCount++;
//				
//				cell = ReportFormats.cellNum(row, colCount,o.getCgst_bill_amt() == null ? 0l : o.getCgst_bill_amt().doubleValue(), cellAlignment);
//				colCount++;
//				
//				cell = ReportFormats.cellNum(row, colCount,o.getSgst_rate() == null ? 0l : o.getSgst_rate().doubleValue(), cellAlignment);
//				colCount++;
//				
//				cell = ReportFormats.cellNum(row, colCount,o.getSgst_bill_amt() == null ? 0l : o.getSgst_bill_amt().doubleValue(), cellAlignment);
//				colCount++;
//				
//				cell = ReportFormats.cellNum(row, colCount,o.getIgst_rate() == null ? 0l : o.getIgst_rate().doubleValue(), cellAlignment);
//				colCount++;
//				
//				cell = ReportFormats.cellNum(row, colCount,o.getIgst_bill_amt() == null ? 0l : o.getIgst_bill_amt().doubleValue(), cellAlignment);
//				colCount++;
				
				cell = ReportFormats.cell(row, colCount,o.getProduct_type(), null);
				colCount++;
				
				
				cell = ReportFormats.cell(row, colCount,o.getSmp_prod_cd(), null);
				colCount++;
				
				//cell = ReportFormats.cellNum(row, colCount,o.getValue1() == null ? 0l : o.getValue1().doubleValue(), cellAlignment);
			//	colCount++;
				
			
				
		//		cell = ReportFormats.cellNum(row, colCount,o.getValue2() == null ? 0l : o.getValue2().doubleValue(), cellAlignment);
		//		colCount++;
				
				totalValue = totalValue.add(o.getValue().add(o.getCgst_bill_amt()
						.add(o.getSgst_bill_amt().add(o.getIgst_bill_amt().add(o.getValue1().add(o.getValue2()))))));
				
			//	cell = ReportFormats.cellNum(row, colCount,totalValue == null ? 0l : totalValue.doubleValue(), null);
			//	colCount++;
				
				cell = ReportFormats.cell(row, colCount,o.getFcode(), null);
				colCount++;
				
				cell = ReportFormats.cell(row, colCount,o.getGcma_req_ind(), null);
				colCount++;
				
				cell = ReportFormats.cell(row, colCount,o.getGcma_number(), null);
				colCount++;
				
				cell = ReportFormats.cell(row, colCount,o.getGcma_expiry_dt() == null ? "" :Utility.convertStringtoStringDate(o.getGcma_expiry_dt()), null);
				colCount++;
				
				cell = ReportFormats.cell(row, colCount,o.getBatch_mfg_dt(), null);
				colCount++;
				
				cell = ReportFormats.cell(row, colCount,o.getBin_number().toString(), cellAlignment);
				colCount++;
			
				cell = ReportFormats.cell(row, colCount,o.getRemark(), null);
				colCount++;
				
				oldDivision = newDivision;
				
//				divisionTotal[0] = divisionTotal[0].add(o.getValue() == null ? BigDecimal.ZERO : o.getValue());
//				divisionTotal[1] = divisionTotal[1]
//					.add(o.getCgst_bill_amt() == null ? BigDecimal.ZERO : o.getCgst_bill_amt());
//				divisionTotal[2] = divisionTotal[2]
//					.add(o.getSgst_bill_amt() == null ? BigDecimal.ZERO : o.getSgst_bill_amt());
//				divisionTotal[3] = divisionTotal[3]
//					.add(o.getIgst_bill_amt() == null ? BigDecimal.ZERO : o.getIgst_bill_amt());
//				divisionTotal[4] = divisionTotal[4].add(o.getValue1() == null ? BigDecimal.ZERO : o.getValue1());
//				divisionTotal[5] = divisionTotal[5].add(o.getValue2() == null ? BigDecimal.ZERO : o.getValue2());
//				divisionTotal[6] = divisionTotal[6].add(totalValue == null ? BigDecimal.ZERO : totalValue);
//
//				reportTotal[0] = reportTotal[0].add(o.getValue() == null ? BigDecimal.ZERO : o.getValue());
//				reportTotal[1] = reportTotal[1]
//					.add(o.getCgst_bill_amt() == null ? BigDecimal.ZERO : o.getCgst_bill_amt());
//				reportTotal[2] = reportTotal[2]
//					.add(o.getSgst_bill_amt() == null ? BigDecimal.ZERO : o.getSgst_bill_amt());
//				reportTotal[3] = reportTotal[3]
//					.add(o.getIgst_bill_amt() == null ? BigDecimal.ZERO : o.getIgst_bill_amt());
//				reportTotal[4] = reportTotal[4].add(o.getValue1() == null ? BigDecimal.ZERO : o.getValue1());
//				reportTotal[5] = reportTotal[5].add(o.getValue2() == null ? BigDecimal.ZERO : o.getValue2());
//				reportTotal[6] = reportTotal[6].add(totalValue == null ? BigDecimal.ZERO : totalValue);

//				totalValue = new BigDecimal(0);
			}
			
//			rowCount++;
//			colCount=0;
//			
//			row = sheet.createRow(rowCount);
//			
//			cell = ReportFormats.cell(row, colCount,"Division Total", null);
//			colCount++;
//			
//			for(int i=0;i<=18;i++)
//			{
//				cell = ReportFormats.cell(row, colCount,"", null);
//				colCount++;	
//			}
//			
//			cell = row.createCell(21);
//			cell = ReportFormats.cellNum(row, colCount, divisionTotal[0] == null ? 0l : divisionTotal[0].doubleValue(), cellAlignment);
//			colCount++;
//			
//			cell = row.createCell(22);
//			cell = ReportFormats.cell(row, colCount, "", cellAlignment);
//			colCount++;
//			
//			cell = row.createCell(23);
//			cell = ReportFormats.cellNum(row, colCount, divisionTotal[1] == null ? 0l : divisionTotal[1].doubleValue(), cellAlignment);
//			colCount++;
//			
//			cell = row.createCell(24);
//			cell = ReportFormats.cell(row, colCount, "", cellAlignment);
//			colCount++;
//			
//			cell = row.createCell(25);
//			cell = ReportFormats.cellNum(row, colCount, divisionTotal[2] == null ? 0l : divisionTotal[2].doubleValue(), cellAlignment);
//			colCount++;
//			
//			cell = row.createCell(26);
//			cell = ReportFormats.cell(row, colCount, "", cellAlignment);
//			colCount++;
//			
//			cell = row.createCell(27);
//			cell = ReportFormats.cellNum(row, colCount, divisionTotal[3] == null ? 0l : divisionTotal[3].doubleValue(), cellAlignment);
//			colCount++;
//			
//			cell = row.createCell(28);
//			cell = ReportFormats.cell(row, colCount, "", cellAlignment);
//			colCount++;
//			
//			cell = row.createCell(29);
//			cell = ReportFormats.cellNum(row, colCount, divisionTotal[4] == null ? 0l : divisionTotal[4].doubleValue(), cellAlignment);
//			colCount++;
//			
//			cell = row.createCell(30);
//			cell = ReportFormats.cell(row, colCount, "", cellAlignment);
//			colCount++;
//			
//			cell = row.createCell(31);
//			cell = ReportFormats.cellNum(row, colCount, divisionTotal[5] == null ? 0l : divisionTotal[5].doubleValue(), cellAlignment);
//			colCount++;
//			
//			
//			cell = row.createCell(32);
//			cell = ReportFormats.cellNum(row, colCount, divisionTotal[6] == null ? 0l : divisionTotal[6].doubleValue(), cellAlignment);
//			colCount++;
//			
//			rowCount++;
//			colCount=0;
//			
//			
//
//			row = sheet.createRow(rowCount);
//			
//			cell = ReportFormats.cell(row, colCount,"Report Total", null);
//			colCount++;
//			
//			for(int i=0;i<=21;i++)
//			{
//				cell = ReportFormats.cell(row, colCount,"", null);
//				colCount++;	
//			}
//			
//			cell = row.createCell(23);
//			cell = ReportFormats.cellNum(row, colCount, reportTotal[0] == null ? 0l : reportTotal[0].doubleValue(), cellAlignment);
//			colCount++;
//			
//			cell = row.createCell(24);
//			cell = ReportFormats.cell(row, colCount, "", cellAlignment);
//			colCount++;
//			
//			cell = row.createCell(25);
//			cell = ReportFormats.cellNum(row, colCount, reportTotal[1] == null ? 0l : reportTotal[1].doubleValue(), cellAlignment);
//			colCount++;
//			
//			cell = row.createCell(26);
//			cell = ReportFormats.cell(row, colCount, "", cellAlignment);
//			colCount++;
//			
//			cell = row.createCell(27);
//			cell = ReportFormats.cellNum(row, colCount, reportTotal[2] == null ? 0l : reportTotal[2].doubleValue(), cellAlignment);
//			colCount++;
//			
//			cell = row.createCell(28);
//			cell = ReportFormats.cell(row, colCount, "", cellAlignment);
//			colCount++;
//			
//			cell = row.createCell(29);// igst
//			cell = ReportFormats.cellNum(row, colCount, reportTotal[3] == null ? 0l : reportTotal[3].doubleValue(), cellAlignment);
//			colCount++;
//			
//			cell = row.createCell(30);
//			cell = ReportFormats.cell(row, colCount, "", cellAlignment);
//			colCount++;
//			
//			cell = row.createCell(31);
//			cell = ReportFormats.cellNum(row, colCount, reportTotal[4] == null ? 0l : reportTotal[4].doubleValue(), cellAlignment);
//			colCount++;
//			
//			cell = row.createCell(32);
//			cell = ReportFormats.cell(row, colCount, "", cellAlignment);
//			colCount++;
//			
//			cell = row.createCell(33);
//			cell = ReportFormats.cellNum(row, colCount, reportTotal[5] == null ? 0l : reportTotal[5].doubleValue(), cellAlignment);
//			colCount++;
//			
//			/*
//			 * cell = row.createCell(34); cell = ReportFormats.cell(row, colCount, "",
//			 * cellAlignment); colCount++;
//			 */
//			
//			cell = row.createCell(34);
//			cell = ReportFormats.cellNum(row, colCount, reportTotal[6] == null ? 0l : reportTotal[6].doubleValue(), cellAlignment);
//			colCount++;
			
			
			///Summary Report Starts
			
			rowCount =0;
			colCount = 1;
			
			 sheet = workbook.createSheet("Prod wise Summary");
			 
				String Summary_headings[] = {"DIVISION","PRODUCT CODE","PRODUCT TYPE","PRODUCT NAME","BATCH NO","QTY","VALUE"};
				
				XSSFCellStyle decimal_format = workbook.createCellStyle();;
				decimal_format.setBorderRight(BorderStyle.MEDIUM);
				decimal_format.setBorderLeft(BorderStyle.MEDIUM);
				decimal_format.setAlignment(HorizontalAlignment.RIGHT);
				decimal_format.setDataFormat(workbook.createDataFormat().getFormat("0.00"));
				
				XSSFCellStyle align_format = workbook.createCellStyle();
				align_format.setBorderRight(BorderStyle.MEDIUM);
				align_format.setBorderLeft(BorderStyle.MEDIUM);
				align_format.setAlignment(HorizontalAlignment.RIGHT);
				
				XSSFCellStyle align_format2 = workbook.createCellStyle();
				align_format2.setBorderRight(BorderStyle.MEDIUM);
				align_format2.setBorderLeft(BorderStyle.MEDIUM);
				align_format2.setAlignment(HorizontalAlignment.LEFT);
				
				XSSFCellStyle align_format3 = workbook.createCellStyle();
				align_format3.setBorderTop(BorderStyle.MEDIUM);
				align_format3.setAlignment(HorizontalAlignment.LEFT);
				
				
				XSSFFont tableHeaderFont = workbook.createFont();
				tableHeaderFont.setFontHeightInPoints((short) 12);
				tableHeaderFont.setBold(true);
				tableHeaderFont.setColor(IndexedColors.BLACK.index);
				
				XSSFCellStyle heading_style  = workbook.createCellStyle();
				heading_style.setFillForegroundColor(IndexedColors.CORNFLOWER_BLUE.index);
				heading_style.setFillPattern(FillPatternType.SOLID_FOREGROUND);
				heading_style.setAlignment(HorizontalAlignment.CENTER);
				heading_style.setFont(tableHeaderFont);
				heading_style.setBorderTop(BorderStyle.MEDIUM);
				heading_style.setTopBorderColor(IndexedColors.BLACK.index);
				heading_style.setBorderBottom(BorderStyle.MEDIUM);
				heading_style.setBottomBorderColor(IndexedColors.BLACK.index);
				heading_style.setBorderLeft(BorderStyle.MEDIUM);
				heading_style.setLeftBorderColor(IndexedColors.BLACK.index);
				heading_style.setBorderRight(BorderStyle.MEDIUM);
				heading_style.setRightBorderColor(IndexedColors.BLACK.index);
				

				row = sheet.createRow(rowCount);
				cell = row.createCell(colCount);
				sheet.addMergedRegion(new CellRangeAddress(rowCount, rowCount, colCount, colCount + Summary_headings.length - 1));
				cell.setCellValue(Companyname);
				cell.setCellStyle(headingCellStyle);
				rowCount++;
				
				row = sheet.createRow(rowCount);
				cell = row.createCell(colCount);
				sheet.addMergedRegion(new CellRangeAddress(rowCount, rowCount, colCount, colCount + Summary_headings.length - 1));
				cell.setCellValue("GRN Product Batch wise Summary Report - QUARANTINE");
				cell.setCellStyle(headingCellStyle);
				rowCount++;

				row = sheet.createRow(rowCount);
				cell = row.createCell(colCount);
				sheet.addMergedRegion(new CellRangeAddress(rowCount, rowCount, colCount, colCount + Summary_headings.length - 1));
				cell.setCellValue("Period: "+Startdt+" to "+Enddt);
				cell.setCellStyle(headingCellStyle);
				
				rowCount++;
				rowCount++;
				
				
				
				
				row = sheet.createRow(rowCount);
				
				for (String heading : Summary_headings) {
					cell = ReportFormats.cell(row, colCount, heading, heading_style);
					colCount++;
				}
				colCount=1;
				rowCount++;
				
				String old_div="old";
				String new_div="";
				
			
				
			
				
				for (Grn_quarantine_prod_summary o : summarylst) {
					
					row = sheet.createRow(rowCount);
					
					new_div = o.getDivision();
					
					if(!new_div.equalsIgnoreCase(old_div)) {
					cell = ReportFormats.cell(row, colCount, o.getDivision(), align_format2);
					colCount++;
					}
					else {
						cell = ReportFormats.cell(row, colCount," ", align_format2);
						colCount++;
					}
					
					cell = ReportFormats.cell(row, colCount,o.getSmp_prod_cd(), align_format2);
					colCount++;
					
					cell = ReportFormats.cell(row, colCount,o.getSmp_prod_type(), align_format2);
					colCount++;
					
					cell = ReportFormats.cell(row, colCount,o.getSmp_prod_name(), align_format2);
					colCount++;
					
					cell = ReportFormats.cell(row, colCount,o.getBatch_no(), align_format2);
					colCount++;
					
					cell = row.createCell(colCount);
					cell.setCellValue(o.getGrnd_qty().doubleValue());
					cell.setCellStyle(decimal_format);
					colCount++;
					
					cell = row.createCell(colCount);
					cell.setCellValue(o.getGrn_value().doubleValue());
					cell.setCellStyle(decimal_format);
					colCount++;
					
					rowCount++;
					colCount = 1;
					old_div = new_div;
				}
				row = sheet.createRow(rowCount);
				
				for (String heading : Summary_headings) {
					cell = ReportFormats.cell(row, colCount, " ", align_format3);
					colCount++;
				}
				
			
			FileOutputStream fileOutputStream = new FileOutputStream(filepath);
			workbook.write(fileOutputStream);
			
			filename=usermasterservice.generateExcellock(filepath, filename,empId,".xlsx");

			System.out.println("Excel Created");
		} finally {
			if (workbook != null) { workbook.close(); }
		}
		return filename;
		
	}

}
