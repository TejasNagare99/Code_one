package com.excel.service;

import java.io.File;
import java.io.FileOutputStream;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.excel.bean.ReportBean;
import com.excel.model.StockWithdrawalReport;
import com.excel.model.Usermaster;
import com.excel.repository.UserMasterRepository;
import com.excel.security.PasswordManager;
import com.excel.utility.ApachePoiExcelFormat;
import com.excel.utility.MedicoConstants;

import net.lingala.zip4j.core.ZipFile;
import net.lingala.zip4j.model.ZipParameters;
import net.lingala.zip4j.util.Zip4jConstants;

@Service
public class StockwithdrawalReportServiceImpl implements StockwithdrawalReportService,MedicoConstants{
	@Autowired private UserMasterRepository usermasterrepository;
	@Autowired private UserMasterService usermasterservice;

	@Override
	public String generateStockwithdrawalReport(ReportBean bean, List<StockWithdrawalReport> lst,String company,String empId) throws Exception {
		int rowCount = 0,colCount=0;

		SimpleDateFormat sdf=new SimpleDateFormat("dd-MM-yyyy");
		XSSFWorkbook wb=null;
		String filename = "stockwithdrawalReport" + new Date().getTime() + ".xlsx";
		String filepath = REPORT_FILE_PATH + filename ;
		try {
			wb=new XSSFWorkbook();
			File f = new File(REPORT_FILE_PATH);
			if (!f.exists()) {
				if (f.mkdirs()) {
				} else {
					throw new RuntimeException("Could not create directory");
				}
			}
			
		XSSFSheet sheet = wb.createSheet("StkWithdrawalReport");
		ApachePoiExcelFormat xlsxExcelFormat = new ApachePoiExcelFormat();
		XSSFRow row=null;
		XSSFCell cell=null;
		 CellStyle sheetHeading = xlsxExcelFormat.SheetHeading(wb);
		 CellStyle sheetHeadingLeft = xlsxExcelFormat.SheetHeading(wb);
		 sheetHeadingLeft.setAlignment(HorizontalAlignment.LEFT);
		 CellStyle columnHeading = xlsxExcelFormat.columnHeading(wb);
		 CellStyle columnHeadingRight = xlsxExcelFormat.columnHeading(wb);
		 columnHeadingRight.setAlignment(HorizontalAlignment.RIGHT);
		 CellStyle rightAlign = xlsxExcelFormat.dataRightAlignWthDecimal(wb);
		 CellStyle leftAlign = xlsxExcelFormat.dataLeftAlign(wb);
		 
		 CellStyle rightAlignWithBold = xlsxExcelFormat.dataRightAlignWthDecimal(wb);
		 CellStyle leftAlignWithBold = xlsxExcelFormat.dataLeftAlign(wb);
		 Font font = wb.createFont();
		 font.setFontHeightInPoints((short) 11);
		 font.setFontName("ARIAL");
		 font.setColor(IndexedColors.BLACK.getIndex());
		// font.setBoldweight(Font.BOLDWEIGHT_BOLD);
		 rightAlignWithBold.setFont(font);
		 leftAlignWithBold.setFont(font);
		 CellStyle columnHeadingLemon = xlsxExcelFormat.columnSubHeadingLemon(wb);
		 CellStyle columnHeadingred = xlsxExcelFormat.columnSubHeadingRed(wb);
		// CellStyle leftAlignB = xlsxExcelFormat.dataBoldLeftAlign(wb);
		 CellStyle rightAlignWth3Decimal = xlsxExcelFormat.dataRightAlignWth3Decimal(wb);
		
		 sheet.createFreezePane(0, 6);
		 
		 row = sheet.createRow(rowCount);
		 sheet.addMergedRegion(new CellRangeAddress(rowCount, rowCount, colCount, colCount+15));
		 cell = row.createCell(colCount);
		 cell.setCellValue(company);
		 cell.setCellStyle(sheetHeading);
		 rowCount++;
		 
		 row = sheet.createRow(rowCount);
		 sheet.addMergedRegion(new CellRangeAddress(rowCount, rowCount, colCount, colCount+15));
		 cell = row.createCell(colCount);
		 cell.setCellValue("Stock Withdrawal Report");
		 cell.setCellStyle(sheetHeading);
		 rowCount++;
		 
		 row = sheet.createRow(rowCount);	
		 sheet.addMergedRegion(new CellRangeAddress(rowCount, rowCount, colCount, colCount+15));
		 cell = row.createCell(colCount);
		 cell.setCellValue("From "+sdf.format(bean.getStartDate())+" To "+sdf.format(bean.getEndDate()));
		 cell.setCellStyle(sheetHeading);
		 rowCount++;
		 
		 row = sheet.createRow(rowCount);
		 sheet.addMergedRegion(new CellRangeAddress(rowCount, rowCount, colCount, colCount+15));
		 cell = row.createCell(colCount);
		 cell.setCellValue("Report Date : "+sdf.format(new Date())+"  Location:"+bean.getLocation() );
		 cell.setCellStyle(sheetHeadingLeft);
		 rowCount++;
		 
		 row = sheet.createRow(rowCount);
		 sheet.addMergedRegion(new CellRangeAddress(rowCount, rowCount, colCount, colCount+1));
		 cell = row.createCell(colCount);
		 cell.setCellValue("Stock Withdrawal");
		 cell.setCellStyle(columnHeading);
		 colCount++;
		 
		 for(int i=0;i<15;i++) {
			 cell = row.createCell(colCount);
			 cell.setCellValue("");
			 cell.setCellStyle(columnHeading);
			 colCount++;
		 }
		 rowCount++;
		 colCount=0;
		 
		 
		 row = sheet.createRow(rowCount);
		 cell = row.createCell(colCount);
		 cell.setCellValue("Challan No.");
		 cell.setCellStyle(columnHeading);
		 colCount++;
		 
		 cell = row.createCell(colCount);
		 cell.setCellValue("Challan Date");
		 cell.setCellStyle(columnHeading);
		 colCount++;
		 
		 cell = row.createCell(colCount);
		 cell.setCellValue("LR No.");
		 cell.setCellStyle(columnHeading);
		 colCount++;
		 
		 cell = row.createCell(colCount);
		 cell.setCellValue("LR Date");
		 cell.setCellStyle(columnHeading);
		 colCount++;
		 
		 cell = row.createCell(colCount);
		 cell.setCellValue("Transporter Name");
		 cell.setCellStyle(columnHeading);
		 colCount++;
		 
		 cell = row.createCell(colCount);
		 cell.setCellValue("No. of Cases");
		 cell.setCellStyle(columnHeadingRight);
		 colCount++;
		 
		 cell = row.createCell(colCount);
		 cell.setCellValue("Weight");
		 cell.setCellStyle(columnHeadingRight);
		 colCount++;
		 
		 cell = row.createCell(colCount);
		 cell.setCellValue("Party Name");
		 cell.setCellStyle(columnHeading);
		 colCount++;
		 
		 cell = row.createCell(colCount);
		 cell.setCellValue("Product Code");
		 cell.setCellStyle(columnHeading);
		 colCount++;
		 
		 cell = row.createCell(colCount);
		 cell.setCellValue("Product Name");
		 cell.setCellStyle(columnHeading);
		 colCount++;
		 
		 cell = row.createCell(colCount);
		 cell.setCellValue("Batch No.");
		 cell.setCellStyle(columnHeading);
		 colCount++;
		 
		 cell = row.createCell(colCount);
		 cell.setCellValue("Batch Expiry Date");
		 cell.setCellStyle(columnHeading);
		 colCount++;
		 
		 cell = row.createCell(colCount);
		 cell.setCellValue("Stock Type");
		 cell.setCellStyle(columnHeading);
		 colCount++;
		 
		 cell = row.createCell(colCount);
		 cell.setCellValue("Withdrawal Qty.");
		 cell.setCellStyle(columnHeadingRight);
		 colCount++;
		 
		 cell = row.createCell(colCount);
		 cell.setCellValue("Rate (Rs.)");
		 cell.setCellStyle(columnHeadingRight);
		 colCount++;
		 
		 cell = row.createCell(colCount);
		 cell.setCellValue("Value (Rs.)");
		 cell.setCellStyle(columnHeadingRight);
		 colCount++;
		 
		 BigDecimal valueTot=BigDecimal.ZERO;
		 for(StockWithdrawalReport obj : lst) {
			 rowCount++;
			 colCount=0;
			 
			 row = sheet.createRow(rowCount);
			 
			 cell = row.createCell(colCount);
			 cell.setCellValue(obj.getSwv_challan_no()==null ? "" : obj.getSwv_challan_no().toString());
			 cell.setCellStyle(leftAlign);
			 colCount++;
			 
			 cell = row.createCell(colCount);
			 cell.setCellValue(obj.getSwv_challan_dt()==null ? "" : sdf.format(obj.getSwv_challan_dt()));
			 cell.setCellStyle(leftAlign);
			 colCount++;
			 
			 cell = row.createCell(colCount);
			 cell.setCellValue(obj.getSwv_lr_no()==null ? "" : obj.getSwv_lr_no());
			 cell.setCellStyle(leftAlign);
			 colCount++;
			 
			 cell = row.createCell(colCount);
			 cell.setCellValue(obj.getSwv_lr_dt()==null ? "" : sdf.format(obj.getSwv_lr_dt()));
			 cell.setCellStyle(leftAlign);
			 colCount++;
			 
			 cell = row.createCell(colCount);
			 cell.setCellValue(obj.getSwv_transporter()==null ? "" : obj.getSwv_transporter());
			 cell.setCellStyle(leftAlign);
			 colCount++;
			 
			 cell = row.createCell(colCount);
			 cell.setCellValue(obj.getSwv_cases()==null ? 0l : obj.getSwv_cases().doubleValue());
			 cell.setCellStyle(rightAlign);
			 colCount++;
			 
			 cell = row.createCell(colCount);
			 cell.setCellValue(obj.getSwv_wt()==null ? 0l : obj.getSwv_wt().doubleValue());
			 cell.setCellStyle(rightAlign);
			 colCount++;
			 
			 cell = row.createCell(colCount);
			 cell.setCellValue(obj.getSwv_sender_name()==null ? "" : obj.getSwv_sender_name());
			 cell.setCellStyle(leftAlign);
			 colCount++;
			 
			 cell = row.createCell(colCount);
			 cell.setCellValue(obj.getSmp_prod_cd()==null ? "" : obj.getSmp_prod_cd());
			 cell.setCellStyle(leftAlign);
			 colCount++;
			 
			 cell = row.createCell(colCount);
			 cell.setCellValue(obj.getSmp_prod_name()==null ? "" : obj.getSmp_prod_name());
			 cell.setCellStyle(leftAlign);
			 colCount++;
			 
			 cell = row.createCell(colCount);
			 cell.setCellValue(obj.getBatch_no()==null ? "" : obj.getBatch_no());
			 cell.setCellStyle(leftAlign);
			 colCount++;
			 
			 cell = row.createCell(colCount);
			 cell.setCellValue(obj.getBatch_expiry_dt()==null ? "" : sdf.format(obj.getBatch_expiry_dt()));
			 cell.setCellStyle(leftAlign);
			 colCount++;
			 
			 cell = row.createCell(colCount);
			 cell.setCellValue(obj.getStock_type()==null ? "" : obj.getStock_type());
			 cell.setCellStyle(leftAlign);
			 colCount++;
			 
			 cell = row.createCell(colCount);
			 cell.setCellValue(obj.getSwvdtl_disp_qty()==null ? 0l : obj.getSwvdtl_disp_qty().doubleValue());
			 cell.setCellStyle(rightAlign);
			 colCount++;
			 
			 cell = row.createCell(colCount);
			 cell.setCellValue(obj.getSwvdtl_rate()==null ? 0l : obj.getSwvdtl_rate().doubleValue());
			 cell.setCellStyle(rightAlign);
			 colCount++;
			 
			 cell = row.createCell(colCount);
			 cell.setCellValue(obj.getValue()==null ? 0l : obj.getValue().doubleValue());
			 cell.setCellStyle(rightAlign);
		
			 valueTot=valueTot.add(obj.getValue() ==null ? BigDecimal.ZERO : obj.getValue());
		 }
		
		 rowCount++;
		 colCount=0;
		 
		 row = sheet.createRow(rowCount);
		 
		 for(int i=0;i<14;i++) {
			 cell = row.createCell(colCount);
			 cell.setCellValue("");
			 cell.setCellStyle(leftAlignWithBold);
			 colCount++;
		 }
		 
		 cell = row.createCell(colCount);
		 cell.setCellValue("Total : ");
		 cell.setCellStyle(leftAlignWithBold);
		 colCount++;
		 
		 cell = row.createCell(colCount);
		 cell.setCellValue(valueTot==null ? 0l : valueTot.doubleValue());
		 cell.setCellStyle(rightAlignWithBold);
		 colCount++;
		 

			FileOutputStream fileOutputStream = new FileOutputStream(filepath);
			wb.write(fileOutputStream);
			
			filename=usermasterservice.generateExcellock(filepath, filename,empId,".xlsx");

		
	} catch (Exception e) {
		throw e;
	}
		finally
		{
			if (wb != null) {
				wb.close();
			}
		}
		
		return filename;
	}

}
