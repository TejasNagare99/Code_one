package com.excel.service;

import java.io.File;
import java.io.FileOutputStream;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.DataFormat;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.VerticalAlignment;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFFont;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.excel.model.Usermaster;
import com.excel.repository.UserMasterRepository;
import com.excel.security.PasswordManager;
import com.excel.utility.ApachePoiExcelFormat;
import com.excel.utility.MedicoConstants;
import com.itextpdf.text.Font;

import net.lingala.zip4j.core.ZipFile;
import net.lingala.zip4j.model.ZipParameters;
import net.lingala.zip4j.util.Zip4jConstants;

@Service
public class Pivot_packing_slip_ReportServiceImpl implements Pivot_packing_slip_ReportService{
	@Autowired private UserMasterService usermasterservice;

	@Autowired
	UserMasterRepository usermasterrepository;
	@Override
	public String Generate_Pivot_packing_slip_Report(List<Object> list, List<Object> summaryList,
			List<Object> headerList, List<Object> batchList, List<Object> productList, List<Object> countList,String divText,int tsoCount,String emmpId)
			throws Exception {
		
		File ff = null;
		StringBuffer path = null;
		String fileName = "pivot_packing_slip" + new Date().getTime()+ ".xlsx" ;
		String filePath = MedicoConstants.REPORT_FILE_PATH;
		 path = new StringBuffer(filePath).append("\\");
		path.append(fileName);
		 ff=new File(path.toString());
		System.out.println("filename "+fileName);
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
		
		
		
		int cnt = 0,rowCount = 0,colCount=0;
		SimpleDateFormat sdf=new SimpleDateFormat("dd-MM-yyyy");
		XSSFWorkbook wb=new XSSFWorkbook();
		XSSFSheet sheet = wb.createSheet("Packing Slip Detail");
		ApachePoiExcelFormat xlsxExcelFormat = new ApachePoiExcelFormat();
		XSSFRow row=null;
		XSSFCell cell=null;
		 CellStyle sheetHeading = xlsxExcelFormat.SheetHeading(wb);
		 CellStyle sheetHeadingLeft = xlsxExcelFormat.SheetHeading(wb);
		 sheetHeadingLeft.setAlignment(HorizontalAlignment.LEFT);
		 CellStyle columnHeading = xlsxExcelFormat.columnHeading(wb);
		 CellStyle columnHeadingWrap = xlsxExcelFormat.columnHeading(wb);
		 columnHeadingWrap.setAlignment(HorizontalAlignment.CENTER);
		 columnHeadingWrap.setVerticalAlignment(VerticalAlignment.CENTER);
		 columnHeadingWrap.setWrapText(true);
		 CellStyle columnHeadingRight = xlsxExcelFormat.columnHeading(wb);
		 columnHeadingRight.setAlignment(HorizontalAlignment.RIGHT);
		 CellStyle rightAlign = xlsxExcelFormat.dataRightAlignWthDecimal(wb);
		 CellStyle leftAlign = xlsxExcelFormat.dataLeftAlign(wb);
		 DataFormat format = wb.createDataFormat();
		 CellStyle rightAlignWithBold = xlsxExcelFormat.dataRightAlignWthDecimal(wb);
		 rightAlignWithBold.setDataFormat(format.getFormat("0.00"));
		 CellStyle leftAlignWithBold = xlsxExcelFormat.dataLeftAlign(wb);
		 XSSFFont font = wb.createFont();
		 font.setFontHeightInPoints((short) 11);
		 font.setFontName("ARIAL");
		 font.setColor(IndexedColors.BLACK.getIndex());
		font.setBold(true);
		 rightAlignWithBold.setFont(font);
		 leftAlignWithBold.setFont(font);
		 CellStyle columnHeadingLemon = xlsxExcelFormat.columnSubHeadingLemon(wb);
		 CellStyle columnHeadingred = xlsxExcelFormat.columnSubHeadingRed(wb);
		// CellStyle leftAlignB = xlsxExcelFormat.dataBoldLeftAlign(wb);
		 CellStyle rightAlignWth3Decimal = xlsxExcelFormat.dataRightAlignWth3Decimal(wb);
		
		 sheet.createFreezePane(0, 4);
		 
		 row = sheet.createRow(rowCount);
		 sheet.addMergedRegion(new CellRangeAddress(rowCount, rowCount, colCount, colCount+1));
		 cell = row.createCell(colCount);
		 cell.setCellValue(divText);
		 cell.setCellStyle(leftAlignWithBold);
		 rowCount++;

		 row = sheet.createRow(rowCount);
		 cell = row.createCell(colCount);
		 cell.setCellValue(tsoCount+" TSO Packing Slip");
		 cell.setCellStyle(leftAlignWithBold);
		 colCount++;
		 
		 for(int i=1;i<batchList.size();i++) {
			 cell = row.createCell(colCount);
			 cell.setCellValue("");
			 cell.setCellStyle(leftAlignWithBold);
			 colCount++;
		 }
		 
		 cell = row.createCell(colCount);
		 cell.setCellValue(sdf.format(new Date()));
		 cell.setCellStyle(leftAlignWithBold);
		 
		 rowCount++;
		 colCount=0;
		 
		 row = sheet.createRow(rowCount);
		 cell = row.createCell(colCount);
		 cell.setCellValue("Batch No -->");
		 cell.setCellStyle(columnHeading);
		 colCount++;
		 
		 for(Object o:batchList) {
			 cell = row.createCell(colCount);
			 cell.setCellValue(o.toString().trim());
			 cell.setCellStyle(columnHeading);
			 colCount++;
		 }
		 
		 rowCount++;
		 colCount=0;
		 
		 row = sheet.createRow(rowCount);
		 cell = row.createCell(colCount);
		 cell.setCellValue("Challan No.");
		 cell.setCellStyle(columnHeadingWrap);
		 colCount++;
		 
		 for(Object o:productList) {
			 cell = row.createCell(colCount);
			 cell.setCellValue(o.toString().trim());
			 cell.setCellStyle(columnHeadingWrap);
			 colCount++;
		 }
		
		int count=0;
		BigDecimal[] prodTotal=new BigDecimal[batchList.size()];
		Arrays.fill(prodTotal, BigDecimal.ZERO);
		for(Object oo : list) {
			if(count !=0) {
				 rowCount++;
				 colCount=0;
				 row = sheet.createRow(rowCount);
				 Object[] obj=(Object[]) oo;
				 for(int i=0;i<obj.length;i++) {
					 if(i == 0) {
						 cell = row.createCell(colCount);
						 cell.setCellValue(obj[i] == null ? "" : obj[i].toString());
						 cell.setCellStyle(leftAlign);
						 colCount++;
					 } else {
						 cell = row.createCell(colCount);
						 if(obj[i] == null) {
							 cell.setCellValue("");

						 } else {
							 cell.setCellValue(Double.valueOf(obj[i].toString()));
						 }
						 cell.setCellStyle(rightAlign);
						 colCount++;
						 prodTotal[i-1]=prodTotal[i-1].add((obj[i] == null ?BigDecimal.ZERO : BigDecimal.valueOf(Double.valueOf(obj[i].toString()))));
					 }
					
				 }
			}
			 count++;
		 }
		
		 rowCount++;
		 colCount=0;
		 
		 row = sheet.createRow(rowCount);
		 cell = row.createCell(colCount);
		 cell.setCellValue("Total");
		 cell.setCellStyle(leftAlignWithBold);
		 colCount++;
		 BigDecimal totalval=BigDecimal.ZERO;
		 for(int i=0;i<prodTotal.length;i++) {
			 cell = row.createCell(colCount);
			 cell.setCellValue(Double.valueOf(prodTotal[i].toString()));
			 cell.setCellStyle(rightAlignWithBold);
			 colCount++;
			 totalval=totalval.add(BigDecimal.valueOf(Double.valueOf(prodTotal[i].toString())));
		 }
		 
		 rowCount++;
		 colCount=0;
		 
		 row = sheet.createRow(rowCount);
		 for(int i=0;i<prodTotal.length-1;i++) {
			 cell = row.createCell(colCount);
			 cell.setCellValue("");
			 cell.setCellStyle(rightAlignWithBold);
			 colCount++;
		 }
		 cell = row.createCell(colCount);
		 cell.setCellValue("Total");
		 cell.setCellStyle(leftAlignWithBold);
		 colCount++;
		 cell = row.createCell(colCount);
		 cell.setCellValue(totalval.doubleValue());
		 cell.setCellStyle(rightAlignWithBold);
		 colCount++;
		 
		 // pick slip sheet
		 
		 sheet = wb.createSheet("Pick Slip");
		 rowCount=0;
		 colCount=0;
		 
		 row = sheet.createRow(rowCount);
		 cell = row.createCell(colCount);
		 cell.setCellValue(divText );
		 cell.setCellStyle(leftAlignWithBold);
		 rowCount++;
		 colCount=0;
		 
		 row = sheet.createRow(rowCount);
		 cell = row.createCell(colCount);
		 cell.setCellValue(tsoCount+" TSO Pick Slip");
		 cell.setCellStyle(leftAlignWithBold);
		 colCount++;
		 
		 cell = row.createCell(colCount);
		 cell.setCellValue("");
		 cell.setCellStyle(leftAlignWithBold);
		 colCount++;
		 
		 cell = row.createCell(colCount);
		 cell.setCellValue("");
		 cell.setCellStyle(leftAlignWithBold);
		 colCount++;
		 
		 cell = row.createCell(colCount);
		 cell.setCellValue(sdf.format(new Date()));
		 cell.setCellStyle(leftAlignWithBold);
		 colCount++;
		 
		 
		 colCount=0;
		 rowCount++;
		 sheet.createFreezePane(0, 3);
		 
		 row = sheet.createRow(rowCount);
		 cell = row.createCell(colCount);
		 cell.setCellValue("Product Name");
		 cell.setCellStyle(columnHeading);
		 colCount++;
		 
		 cell = row.createCell(colCount);
		 cell.setCellValue("Batch No.");
		 cell.setCellStyle(columnHeading);
		 colCount++;
		 
		 cell = row.createCell(colCount);
		 cell.setCellValue("Expiry Date");
		 cell.setCellStyle(columnHeading);
		 colCount++;
		 
		 cell = row.createCell(colCount);
		 cell.setCellValue("DSP Qty");
		 cell.setCellStyle(columnHeading);
		
		 BigDecimal dspQtyTot=BigDecimal.ZERO;
		 for(Object oo : summaryList) {
					 rowCount++;
					 colCount=0;
					 row = sheet.createRow(rowCount);
					 Object[] obj=(Object[]) oo;
					 for(int i=0;i<obj.length;i++) {
						 if(i == 3) {
							 cell = row.createCell(colCount);
							 cell.setCellValue(Double.valueOf(obj[i].toString()));
							 cell.setCellStyle(rightAlign);
							 colCount++;
							 dspQtyTot=dspQtyTot.add((obj[i] == null ? BigDecimal.ZERO : BigDecimal.valueOf(Double.valueOf(obj[i].toString()))));
						 } else {
							 cell = row.createCell(colCount);
							 cell.setCellValue(obj[i] == null ? "" : obj[i].toString());
							 cell.setCellStyle(leftAlign);
							 colCount++;
						 }
					 }
			 }
		 
		 rowCount++;
		 colCount=0;
		 
		 row = sheet.createRow(rowCount);
		 
		 cell = row.createCell(colCount);
		 cell.setCellValue("");
		 cell.setCellStyle(leftAlignWithBold);
		 colCount++;
		 cell = row.createCell(colCount);
		 cell.setCellValue("");
		 cell.setCellStyle(leftAlignWithBold);
		 colCount++;
		 
		 cell = row.createCell(colCount);
		 cell.setCellValue("Total");
		 cell.setCellStyle(leftAlignWithBold);
		 colCount++;
	
		 cell = row.createCell(colCount);
		 cell.setCellValue(dspQtyTot.doubleValue());
		 cell.setCellStyle(rightAlignWithBold);
		 colCount++;
		 
		 FileOutputStream out = new FileOutputStream(path.toString());
		 wb.write(out);
		    out.close();
		    wb.close();
		    
		    fileName=usermasterservice.generateExcellock(path.toString(), fileName,emmpId,".xlsx");

		

		    System.out.println("Excel Created");
		    
		return fileName;
	
	}

	
}