package com.excel.service;

import java.io.File;
import java.io.FileOutputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.ss.usermodel.BorderStyle;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.FillPatternType;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.VerticalAlignment;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.excel.model.Allocation_report_3;
import com.excel.model.Usermaster;
import com.excel.repository.UserMasterRepository;
import com.excel.security.PasswordManager;
import com.excel.utility.CodifyErrors;
import com.excel.utility.MedicoConstants;

import net.lingala.zip4j.core.ZipFile;
import net.lingala.zip4j.model.ZipParameters;
import net.lingala.zip4j.util.Zip4jConstants;

@Service
public class Allocation_report3_ServiceImpl implements Allocation_report3_Service{
	@Autowired private UserMasterService usermasterservice;

	@Autowired private UserMasterRepository usermasterrepository;
	
	@Override
	public String generate_Allocation_Report3_Report(List<Allocation_report_3> list,String empId) throws Exception {

		File ff = null;
		Workbook wwbook = null;
		StringBuffer path = null;
		String filename = null;
		

		try {
			System.out.println("generateExcel ----------------------------------------------------------------------");
			
			Date today = new Date();
			
			
			SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy hh:mm:ss aa");
		
			String todaydate = sdf.format(today);
			
			long l = new Date().getTime();
			filename = "Allocation_3_Report" + l + ".xlsx";
			String filePath = MedicoConstants.REPORT_FILE_PATH;
			 path = new StringBuffer(filePath).append("\\");
			path.append(filename );

			wwbook= new XSSFWorkbook();
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
			
			
			
			Sheet wsheet = wwbook.createSheet("Allocation Sheet");
			wsheet.createFreezePane( 0, 3);
			Font h_font =  wwbook.createFont();
			h_font.setBold(true);
			h_font.setFontHeightInPoints((short)14);
			
			Font font =  wwbook.createFont();
			font.setBold(true);
			font.setFontHeightInPoints((short)12);
			
			CellStyle heading = wwbook.createCellStyle();
			heading.setFillForegroundColor(HSSFColor.HSSFColorPredefined.LIGHT_YELLOW.getIndex());
			heading.setFillPattern(FillPatternType.SOLID_FOREGROUND);
			heading.setAlignment(HorizontalAlignment.CENTER);
			heading.setFont(h_font);
			heading.setBorderBottom(BorderStyle.THIN);
			heading.setBorderLeft(BorderStyle.THIN);
			heading.setBorderTop(BorderStyle.THIN);
			heading.setBorderRight(BorderStyle.THIN);
			
			
			CellStyle header = wwbook.createCellStyle();
			header.setFillForegroundColor(HSSFColor.HSSFColorPredefined.AQUA.getIndex());
			header.setFillPattern(FillPatternType.SOLID_FOREGROUND);
			header.setAlignment(HorizontalAlignment.CENTER);
			header.setVerticalAlignment(VerticalAlignment.CENTER);
			header.setBorderBottom(BorderStyle.THIN);
			header.setBorderLeft(BorderStyle.THIN);
			header.setBorderTop(BorderStyle.THIN);
			header.setBorderRight(BorderStyle.THIN);	
			header.setWrapText(false);
			header.setFont(font);
			
			CellStyle header2 = wwbook.createCellStyle();
			header2.setFillForegroundColor(HSSFColor.HSSFColorPredefined.AQUA.getIndex());
			header2.setFillPattern(FillPatternType.SOLID_FOREGROUND);
			header2.setAlignment(HorizontalAlignment.CENTER);
			header2.setVerticalAlignment(VerticalAlignment.CENTER);
			header2.setBorderBottom(BorderStyle.THIN);
			header2.setBorderLeft(BorderStyle.THIN);
			header2.setBorderTop(BorderStyle.THIN);
			header2.setBorderRight(BorderStyle.THIN);	
			header2.setFont(font);
			
			CellStyle data = wwbook.createCellStyle();
			data.setAlignment(HorizontalAlignment.CENTER);
			data.setVerticalAlignment(VerticalAlignment.CENTER);
			data.setWrapText(false);
			data.setBorderBottom(BorderStyle.THIN);
			data.setBorderLeft(BorderStyle.THIN);
			data.setBorderTop(BorderStyle.THIN);
			data.setBorderRight(BorderStyle.THIN);
			
			CellStyle data2 = wwbook.createCellStyle();
			data2.setAlignment(HorizontalAlignment.LEFT);
			data2.setVerticalAlignment(VerticalAlignment.CENTER);
			data2.setWrapText(false);
			data2.setBorderBottom(BorderStyle.THIN);
			data2.setBorderLeft(BorderStyle.THIN);
			data2.setBorderTop(BorderStyle.THIN);
			data2.setBorderRight(BorderStyle.THIN);
			
			CellStyle numeric = wwbook.createCellStyle();
			numeric.setAlignment(HorizontalAlignment.RIGHT);
			numeric.setVerticalAlignment(VerticalAlignment.CENTER);
			numeric.setWrapText(false);
			numeric.setBorderBottom(BorderStyle.THIN);
			numeric.setBorderLeft(BorderStyle.THIN);
			numeric.setBorderTop(BorderStyle.THIN);
			numeric.setBorderRight(BorderStyle.THIN);
			
			CellStyle numeric_decimal = wwbook.createCellStyle();
			numeric_decimal.setAlignment(HorizontalAlignment.RIGHT);
			numeric_decimal.setVerticalAlignment(VerticalAlignment.CENTER);
			numeric_decimal.setWrapText(false);
			numeric_decimal.setBorderBottom(BorderStyle.THIN);
			numeric_decimal.setBorderLeft(BorderStyle.THIN);
			numeric_decimal.setBorderTop(BorderStyle.THIN);
			numeric_decimal.setBorderRight(BorderStyle.THIN);
			numeric_decimal.setDataFormat(wwbook.createDataFormat().getFormat("0.00"));
			
			
			int col = 0, row = 0;
			
			XSSFRow hrow = (XSSFRow) wsheet.createRow(row);
			
			XSSFCell cell = hrow.createCell(col);
			
			
			
			
			  cell.setCellValue("MONTHLY  DISPATCHES REVIEW  : "+todaydate );
			  wsheet.addMergedRegion(new CellRangeAddress(0,0,0,23)); 
			  cell.setCellStyle(heading);
			  col++;
			  row++;
			  
			  col=0;
			  hrow = (XSSFRow) wsheet.createRow(row);
			  
			 
			  
			  cell = hrow.createCell(col);
			  cell.setCellValue("BU");
			  wsheet.addMergedRegion(new CellRangeAddress(row,row+1,col,col));  
			  cell.setCellStyle(header2);
			  col++;
			  
			  cell = hrow.createCell(col);
			  cell.setCellValue("Team Name");
			  wsheet.addMergedRegion(new CellRangeAddress(row,row+1,col,col));  
			  cell.setCellStyle(header2);
			  col++;
			  
			  cell = hrow.createCell(col);
			  cell.setCellValue("Requested By (Code)");
			  wsheet.addMergedRegion(new CellRangeAddress(row,row+1,col,col));  
			  cell.setCellStyle(header2);
			  col++;
			  
			  cell = hrow.createCell(col);
			  cell.setCellValue("Requested By (Name)");
			  wsheet.addMergedRegion(new CellRangeAddress(row,row+1,col,col));  
			  cell.setCellStyle(header2);
			  col++;
			  
			  cell = hrow.createCell(col);
			  cell.setCellValue("Requested Date");
			  wsheet.addMergedRegion(new CellRangeAddress(row,row+1,col,col));  
			  cell.setCellStyle(header2);
			  col++;
			  
			  cell = hrow.createCell(col);
			  cell.setCellValue("Month of Use");
			  wsheet.addMergedRegion(new CellRangeAddress(row,row+1,col,col));  
			  cell.setCellStyle(header2);
			  col++;
			  
			  cell = hrow.createCell(col);
			  cell.setCellValue("warehouse code / Fcode");
			  wsheet.addMergedRegion(new CellRangeAddress(row,row+1,col,col));  
			  cell.setCellStyle(header2);
			  col++;
			  
			  cell = hrow.createCell(col);
			  cell.setCellValue("Fcode");
			  wsheet.addMergedRegion(new CellRangeAddress(row,row+1,col,col));  
			  cell.setCellStyle(header2);
			  col++;
			  
			  cell = hrow.createCell(col);
			  cell.setCellValue("Product Name");
			  wsheet.addMergedRegion(new CellRangeAddress(row,row+1,col,col));  
			  cell.setCellStyle(header2);
			  col++;
			  
			  cell = hrow.createCell(col);
			  cell.setCellValue("Allocation to (Qty)");
			  wsheet.addMergedRegion(new CellRangeAddress(row,row,col,col = col+3));  
			  cell.setCellStyle(header);
			  col++;
			  
			  cell = hrow.createCell(col);
			  cell.setCellValue("Type of Allocation");
			  wsheet.addMergedRegion(new CellRangeAddress(row,row+1,col,col));  
			  cell.setCellStyle(header2);
			  col++;
			  
				/*
				 * cell = hrow.createCell(col);
				 * cell.setCellValue("Total allocation qty \n (No of colleagues x qty)");
				 * wsheet.addMergedRegion(new CellRangeAddress(row,row+1,col,col));
				 * hrow.setHeightInPoints((3*wsheet.getDefaultRowHeightInPoints()));
				 * //wsheet.autoSizeColumn(col); //wsheet.setColumnWidth(col, 10);
				 * cell.setCellStyle(header); col++;
				 */
			  
			  cell = hrow.createCell(col);
			  cell.setCellValue("Total allocation qty (No of colleagues x qty)");
			  wsheet.addMergedRegion(new CellRangeAddress(row,row+1,col,col));  
			  cell.setCellStyle(header);
			  col++;
			  
				/*
				 * cell = hrow.createCell(col);
				 * cell.setCellValue("Inventory available in \n warehouse as on date: time:");
				 * wsheet.addMergedRegion(new CellRangeAddress(row,row+1,col,col));
				 * hrow.setHeightInPoints((3*wsheet.getDefaultRowHeightInPoints()));
				 * wsheet.autoSizeColumn(col); cell.setCellStyle(header); col++;
				 */
			  
			 
			  
			  cell = hrow.createCell(col);
			  cell.setCellValue("Dispatch quantity");
			  wsheet.addMergedRegion(new CellRangeAddress(row,row+1,col,col));  
			  wsheet.autoSizeColumn(col); 
			  cell.setCellStyle(header);
			  col++;
			  
			  cell = hrow.createCell(col);
			  cell.setCellValue("Current Inventory Qty");
			  wsheet.addMergedRegion(new CellRangeAddress(row,row+1,col,col));  
			  wsheet.autoSizeColumn(col); 
			  cell.setCellStyle(header);
			  col++;
			  
			  cell = hrow.createCell(col);
			  cell.setCellValue("Warehouse comments/suggestion prior to dispatch");
			  wsheet.addMergedRegion(new CellRangeAddress(row,row+1,col,col));  
			  cell.setCellStyle(header);
			  col++;
			  
			  cell = hrow.createCell(col);
			  cell.setCellValue("Confirmation taken from Maketing");
			  wsheet.addMergedRegion(new CellRangeAddress(row,row+1,col,col));  
			  cell.setCellStyle(header);
			  col++;
			  
			  cell = hrow.createCell(col);
			  cell.setCellValue("Challan No: from ____ to _____");
			  wsheet.addMergedRegion(new CellRangeAddress(row,row+1,col,col));  
			  cell.setCellStyle(header);
			  col++;
			  
			  cell = hrow.createCell(col);
			  cell.setCellValue("Date");
			  wsheet.addMergedRegion(new CellRangeAddress(row,row+1,col,col));  
			  cell.setCellStyle(header);
			  col++;
			  
			  cell = hrow.createCell(col);
			  cell.setCellValue("No of Cases");
			  wsheet.addMergedRegion(new CellRangeAddress(row,row+1,col,col));  
			  cell.setCellStyle(header);
			  col++;
			  
				/*
				 * cell = hrow.createCell(col); cell.setCellValue("Dispatch \n status");
				 * wsheet.addMergedRegion(new CellRangeAddress(row,row+1,col,col));
				 * hrow.setHeightInPoints((3*wsheet.getDefaultRowHeightInPoints()));
				 * wsheet.autoSizeColumn(col); cell.setCellStyle(header); col++;
				 */
			  
			  
			  cell = hrow.createCell(col);
			  cell.setCellValue("Dispatch status");
			  wsheet.addMergedRegion(new CellRangeAddress(row,row+1,col,col));  
			  cell.setCellStyle(header);
			  col++;
			  
			  cell = hrow.createCell(col);
			  cell.setCellValue("Last mile delivery timelines");
			  wsheet.addMergedRegion(new CellRangeAddress(row,row+1,col,col));  
			  cell.setCellStyle(header);
			  col++;
			  
			  col=0;
			 // col=7;
			  row ++;
			  hrow = (XSSFRow) wsheet.createRow(row);
			  
			  
			  for(int i=1;i<=9;i++) {
			  cell = hrow.createCell(col);  
			  cell.setCellValue(" ");
			  cell.setCellStyle(header);
			  col++;
			  }
			 
			  
			  
			  cell = hrow.createCell(col);
			  cell.setCellValue("TE");
			  cell.setCellStyle(header);
			  col++;
			  
			  cell = hrow.createCell(col);
			  cell.setCellValue("DM");
			  cell.setCellStyle(header);
			  col++;
			  
			  cell = hrow.createCell(col);
			  cell.setCellValue("RBM");
			  cell.setCellStyle(header);
			  col++;
			  
			  cell = hrow.createCell(col);
			  cell.setCellValue("Other");
			  cell.setCellStyle(header);
			  col++;
			  
			  for(int i=1;i<=11;i++) {
			  cell = hrow.createCell(col);
			  cell.setCellValue(" ");
			  cell.setCellStyle(header);
			  col++;
			  }
			  
			  
			  
			  
			  col =0 ;
			  row++;
			  
			  String new_Team = "new";
			  String old_Team = "old";
			 for (Allocation_report_3 alloc : list) {
				 
				 new_Team = alloc.getTeam_name();		 
				 hrow = (XSSFRow) wsheet.createRow(row); 
				 
				if(!new_Team.equals(old_Team) && !old_Team.equals("old")) {
					  cell = hrow.createCell(col);
					  cell.setCellValue(" ");
					  wsheet.addMergedRegion(new CellRangeAddress(row,row,col,col+23));  
					  cell.setCellStyle(data);
					  col++;
					  
					  for(int i=1;i<=20;i++) {
						  cell = hrow.createCell(col);
						  cell.setCellValue(" ");
						  cell.setCellStyle(data);
						  col++;
					  }
					  
					  col=0;
					  row++;
					  hrow = (XSSFRow) wsheet.createRow(row); 
				}
				
			
				  
				  cell = hrow.createCell(col);
				  cell.setCellValue(alloc.getBu().equals(null)? "" : alloc.getBu());
				  cell.setCellStyle(data2);
				  col++;
				  
				  cell = hrow.createCell(col);
				  cell.setCellValue(alloc.getTeam_name().equals(null)? "":alloc.getTeam_name());
				  cell.setCellStyle(data2);
				  col++;
				  
				  cell = hrow.createCell(col);
				  cell.setCellValue(alloc.getRequest_by().equals(null)?"":alloc.getRequest_by());
				  cell.setCellStyle(data);
				  col++;
				  
				  
				  
				  cell = hrow.createCell(col);
				  cell.setCellValue(alloc.getRequest_by_name().equals(null)?"":alloc.getRequest_by_name());		//added
				  cell.setCellStyle(data);
				  col++;
				  
				  cell = hrow.createCell(col);
				  cell.setCellValue(alloc.getRequest_date().equals(null)?"":alloc.getRequest_date());
				  cell.setCellStyle(data);
				  col++;
				  
				  cell = hrow.createCell(col);
				  cell.setCellValue(alloc.getMonth_of_use().equals(null)?"":alloc.getMonth_of_use());
				  cell.setCellStyle(data);
				  col++;
				  
				  cell = hrow.createCell(col);
				  cell.setCellValue(alloc.getWarehouse_code().equals(null)?"":alloc.getWarehouse_code());
				  cell.setCellStyle(data2);
				  col++;
				  
				  cell = hrow.createCell(col);
				  cell.setCellValue(alloc.getFcode()==null?"":alloc.getFcode());					///added
				  cell.setCellStyle(data2);
				  col++;
				  
				  cell = hrow.createCell(col);
				  cell.setCellValue(alloc.getProduct_name().equals(null)?"":alloc.getProduct_name());
				  cell.setCellStyle(data2);
				  col++;
				  
				  cell = hrow.createCell(col);
				  cell.setCellValue(alloc.getTe_count().equals(null)?0l:alloc.getTe_count());
				  cell.setCellStyle(numeric);
				  col++;
				  
				  
				  cell = hrow.createCell(col);
				  cell.setCellValue(alloc.getDm_count().equals(null)?0l:alloc.getDm_count());
				  cell.setCellStyle(numeric);
				  col++;
				  
				  cell = hrow.createCell(col);
				  cell.setCellValue(alloc.getRbm_count().equals(null)?0l:alloc.getRbm_count());
				  cell.setCellStyle(numeric);
				  col++;
				  
				  cell = hrow.createCell(col);
				  cell.setCellValue(alloc.getOth_count().equals(null)?0l:alloc.getOth_count());
				  cell.setCellStyle(numeric);
				  col++;
				  
				  
				  cell = hrow.createCell(col);
				  cell.setCellValue(alloc.getType_of_alloc().equals(null)?"":alloc.getType_of_alloc());
				  cell.setCellStyle(data);
				  col++;
				  
				  cell = hrow.createCell(col);
				  cell.setCellValue(alloc.getTot_alloc_qty());
				  cell.setCellStyle(numeric);
				  col++;
				  			  

				  cell = hrow.createCell(col);
				  cell.setCellValue(alloc.getDispatch_qty().doubleValue());			///added
				  cell.setCellStyle(numeric);
				  col++;
				  
				  cell = hrow.createCell(col);
				  cell.setCellValue(alloc.getInventory());
				  cell.setCellStyle(numeric);
				  col++;
				  
				  
				  cell = hrow.createCell(col);
				  cell.setCellValue(alloc.getWare_comment());
				  cell.setCellStyle(data);
				  col++;
				  
				  cell = hrow.createCell(col);
				  cell.setCellValue(alloc.getConf_from_mktg());
				  cell.setCellStyle(data);
				  col++;
				  
				  cell = hrow.createCell(col);
				  cell.setCellValue(alloc.getChallan_no());
				  cell.setCellStyle(data);
				  col++;
				  
				  
				  cell = hrow.createCell(col);
				  cell.setCellValue(alloc.getChallan_dt());
				  cell.setCellStyle(data);
				  col++;
				  
				  cell = hrow.createCell(col);
				  cell.setCellValue(alloc.getNo_of_cases());
				  cell.setCellStyle(numeric);
				  col++;
				  
				  cell = hrow.createCell(col);
				  cell.setCellValue(alloc.getDisp_status().equals(null)?"":alloc.getDisp_status());
				  cell.setCellStyle(data);
				  col++;
				  
				  cell = hrow.createCell(col);
				  cell.setCellValue(alloc.getLast_mile());
				  cell.setCellStyle(data);
				  col++;
				  
				  old_Team = new_Team;
				  col = 0;
				  row++ ;
			}
			  
			  
			  
			  FileOutputStream fileOut = new FileOutputStream(path.toString());
				 wwbook.write(fileOut);
				 
					filename=usermasterservice.generateExcellock(path.toString(), filename,empId,".xlsx");


				 
				    fileOut.close();
				    wwbook.close();
			
				System.out.println("Excel Created");
		}
		catch (Exception e) {
//			throw e;
			System.out.println("Error Occurred :" +e.getMessage());// uncomment asneeded --;
		}

		
		return filename;
	}

}
