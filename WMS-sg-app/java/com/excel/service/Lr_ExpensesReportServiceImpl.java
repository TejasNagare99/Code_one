package com.excel.service;

import java.io.File;
import java.io.FileOutputStream;
import java.util.Date;
import java.util.List;

import org.apache.poi.ss.usermodel.BorderStyle;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.VerticalAlignment;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Service;

import com.excel.model.FieldMasterAttrib;
import com.excel.model.Lr_Expenses_Report;
import com.excel.utility.ApachePoiExcelFormat;
import com.excel.utility.MedicoConstants;
import com.excel.utility.Utility;

@Service
public class Lr_ExpensesReportServiceImpl implements Lr_ExpensesReportService{

	@Override
	public String generate_lr_Expenses_Report(List<Lr_Expenses_Report> lst, String company) throws Exception {
		// TODO Auto-generated method stub
		String filename ;
		Workbook wwbook =null;
		File ff=null;
		try {
				String comp_name= company;
				long l = new Date().getTime();
				filename = "LR_Expenses_Report_" + l + ".xlsx";
			
				String filePath = MedicoConstants.REPORT_FILE_PATH ;
				
				StringBuffer path = null;
				 path = new StringBuffer(filePath).append("\\");
					path.append(filename);
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
				ff = new File(path.toString());
				wwbook = new XSSFWorkbook();
				Sheet wsheet = wwbook.createSheet("Lr_Expenses");
		
				
				wsheet.createFreezePane(0, 4);
				
				int col = 0;
				short row = 0;
				
				String[] heading = {"Location Name","Division","Transporter","LR No","LR Date","Invoice No","Invoice Date","Destination","LR Expenses(Rs.)",
						"Tentative Delivery Date","Actual Delivery Date","Delay Days"};
				
				ApachePoiExcelFormat xlsxExcelFormat = new ApachePoiExcelFormat();
				CellStyle sheetHeading = xlsxExcelFormat.SheetHeading(wwbook);
				CellStyle columnHeading = xlsxExcelFormat.columnHeading(wwbook);
				columnHeading.setVerticalAlignment(VerticalAlignment.CENTER);
				
				
				CellStyle columnHeading2 = xlsxExcelFormat.columnHeading(wwbook);
				columnHeading2.setWrapText(true);
				
				CellStyle decimal =   wwbook.createCellStyle();
				decimal.setAlignment(HorizontalAlignment.RIGHT);
				decimal.setDataFormat(wwbook.createDataFormat().getFormat("0.00"));
				
				CellStyle datestyle =   wwbook.createCellStyle();
				datestyle.setAlignment(HorizontalAlignment.RIGHT);
				
				CellStyle lastline =   wwbook.createCellStyle();
				lastline.setBorderTop(BorderStyle.THIN);
				
				XSSFRow hrow = (XSSFRow) wsheet.createRow(row);
				XSSFCell cell = hrow.createCell(col);
				cell.setCellStyle(sheetHeading);
				cell.setCellValue("LR Expense Report");
				wsheet.addMergedRegion(new CellRangeAddress(row, row, col, heading.length-1));

				row++;
				
				hrow = (XSSFRow) wsheet.createRow(row);
				cell = hrow.createCell(col);
				cell.setCellStyle(sheetHeading);
				cell.setCellValue(company);
				wsheet.addMergedRegion(new CellRangeAddress(row, row, col, heading.length-1));

				row++;
				

				hrow = (XSSFRow) wsheet.createRow(row);
				cell = hrow.createCell(col);
				cell.setCellStyle(sheetHeading);
				cell.setCellValue("Report Dated : "+ Utility.convertSmpDatetoString(new Date()));
				wsheet.addMergedRegion(new CellRangeAddress(row, row, col, heading.length-1));
				col++;
				row++;
				
				
				col = 0;
				
				hrow = (XSSFRow) wsheet.createRow(row);
				int i = 0;
				for(String a: heading){
					cell = hrow.createCell(col);
					if(i==9 || i==10) {
					cell.setCellStyle(columnHeading2);
					}
					else {
						cell.setCellStyle(columnHeading);
					}
					cell.setCellValue(a);
					col++;
					i++;
				}
				
				row++;
				col = 0;
				
				for(Lr_Expenses_Report lr : lst) {
					
					hrow = (XSSFRow) wsheet.createRow(row);
					
				
					
					cell = hrow.createCell(col);
					//cell.setCellStyle(sheetHeading);
					cell.setCellValue(lr.getLoc_nm());
					col++;
					
					cell = hrow.createCell(col);
					//cell.setCellStyle(sheetHeading);
					cell.setCellValue(lr.getDiv_disp_nm());
					col++;
					
					cell = hrow.createCell(col);
					//cell.setCellStyle(sheetHeading);
					cell.setCellValue(lr.getDsp_transporter());
					col++;
					
					cell = hrow.createCell(col);
					//cell.setCellStyle(sheetHeading);
					cell.setCellValue(lr.getDsp_lr_no());
					col++;
					
					cell = hrow.createCell(col);
					cell.setCellStyle(datestyle);
					cell.setCellValue(lr.getDsp_lr_dt());
					col++;
					
					
					cell = hrow.createCell(col);
					//cell.setCellStyle(sheetHeading);
					cell.setCellValue(lr.getDsp_challan_no());
					col++;
					
					
					cell = hrow.createCell(col);
					cell.setCellStyle(datestyle);
					cell.setCellValue(lr.getDsp_challan_dt());
					col++;
					
					
					cell = hrow.createCell(col);
					//cell.setCellStyle(sheetHeading);
					cell.setCellValue(lr.getDspfstaff_destination());
					col++;
					
					cell = hrow.createCell(col);
					cell.setCellStyle(decimal);
					cell.setCellValue(lr.getCourier_expenses().doubleValue());
					col++;
					
					cell = hrow.createCell(col);
					cell.setCellStyle(datestyle);
					cell.setCellValue(lr.getTentative_delivery_dt());
					col++;
					
					cell = hrow.createCell(col);
					cell.setCellStyle(datestyle);
					cell.setCellValue(lr.getActual_delivery_dt());
					col++;

					cell = hrow.createCell(col);
					//cell.setCellStyle(sheetHeading);
					cell.setCellValue(lr.getDelay_days());
					col++;
					row ++;
					col=0;
				}
				
				hrow = (XSSFRow) wsheet.createRow(row);
				cell = hrow.createCell(col);
				for(String a: heading){
					cell = hrow.createCell(col);
					cell.setCellStyle(lastline);
					col++;
				}
				
				FileOutputStream fileOut = new FileOutputStream(ff);
				wwbook.write(fileOut);
				fileOut.close();

				System.out.println("Excel Created");
				
			}catch (Exception e) {
			// TODO: handle exception
			throw e;
		}
		return filename;
	}

}
