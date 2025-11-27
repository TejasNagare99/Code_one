//package com.excel.service;
//
//public class HqMasterAuditTrail_Report_ServiceImpl {
//
//}
package com.excel.service;

import java.io.File;
import java.io.FileOutputStream;
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
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.excel.model.HqMasterAuditTrailModel;
import com.excel.model.Usermaster;
import com.excel.repository.UserMasterRepository;
import com.excel.security.PasswordManager;
import com.excel.utility.CodifyErrors;
import com.excel.utility.MedicoConstants;
import com.excel.utility.Utility;

import net.lingala.zip4j.core.ZipFile;
import net.lingala.zip4j.model.ZipParameters;
import net.lingala.zip4j.util.Zip4jConstants;

@Service
public class HqMasterAuditTrail_Report_ServiceImpl implements HqMasterAuditTrail_Report_Service{
	@Autowired private UserMasterService usermasterservice;

	@Autowired private UserMasterRepository usermasterrepository;
	String filename ; 
	@Override
	public String Generate_HqMasterAuditTrail_Report(List<HqMasterAuditTrailModel> list,String username,String Companyname,String empId) 
			throws Exception {
		System.out.println("generateExcel ----------------------------------------------------------------------");
		//Thread.sleep(10000);
	

		Workbook wwbook =null;
		File ff=null;
		try {
			String _hostPath = MedicoConstants.REPORT_FILE_PATH; 
			System.out.println("_hostPath -- "+_hostPath);
			 filename="HqMasterAuditTrail"+ new Date().getTime()+".xlsx";
		String xlsPath=_hostPath+filename;
		System.out.println("xlsPath -- "+xlsPath);
		StringBuffer path = new StringBuffer(_hostPath).append("\\");
		path.append(filename);
		 ff=new File(path.toString());
		wwbook= new XSSFWorkbook();
	
		
			File file = new File(_hostPath);
			try {
				if (!file.exists()) {
					if (file.mkdirs()) {
						
					} else {
						throw new RuntimeException("Could not create directory");
					}
				}
				} catch (Exception e) {
					System.out.println("Error Occurred :" +e.getMessage());// uncomment asneeded --;
				}
			
			Sheet wsheet = wwbook.createSheet("HqAuditTrailReport");
			wsheet.createFreezePane( 0, 4);
	
			String date = Utility.convertSmpDatetoString(new Date());
			String[] heading = {"HQ Code","HQ Name","State","Pool Ind","No.of Reps","PMP Perf","Map Code","Activity","User Name","Date & Time","IP Used","Status","Record Type"};

			int col = 0, row = 0;
			
			Font font =  wwbook.createFont();
			font.setBold(true);
			font.setFontHeightInPoints((short)12);
			
			CellStyle   alignment1=  wwbook.createCellStyle();
			alignment1.setFillForegroundColor(HSSFColor.HSSFColorPredefined.GOLD.getIndex());
			alignment1.setFillPattern(FillPatternType.SOLID_FOREGROUND);
		    alignment1.setAlignment(HorizontalAlignment.CENTER);
		    alignment1.setFont(font);
		    
		    CellStyle   alignment1right=  wwbook.createCellStyle();
		    alignment1right.setFillForegroundColor(HSSFColor.HSSFColorPredefined.GOLD.getIndex());
		    alignment1right.setFillPattern(FillPatternType.SOLID_FOREGROUND);
		    alignment1right.setAlignment(HorizontalAlignment.RIGHT);
		    alignment1right.setFont(font);
		    
		    CellStyle   alignment1LEFT=  wwbook.createCellStyle();
		    alignment1LEFT.setFillForegroundColor(HSSFColor.HSSFColorPredefined.GOLD.getIndex());
		    alignment1LEFT.setFillPattern(FillPatternType.SOLID_FOREGROUND);
		    alignment1LEFT.setAlignment(HorizontalAlignment.LEFT);
		    alignment1LEFT.setFont(font);
		
			
			CellStyle   headingstyle=  wwbook.createCellStyle();
			headingstyle.setFillForegroundColor(HSSFColor.HSSFColorPredefined.AQUA.getIndex());
			headingstyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);
			headingstyle.setBorderBottom(BorderStyle.THIN);
			headingstyle.setBorderLeft(BorderStyle.THIN);
			headingstyle.setBorderTop(BorderStyle.THIN);
			headingstyle.setBorderRight(BorderStyle.THIN);
			headingstyle.setAlignment(HorizontalAlignment.CENTER);
			headingstyle.setWrapText(false);
			headingstyle.setFont(font);
			
		
			
			CellStyle   alignRight=  wwbook.createCellStyle();
			alignRight.setBorderBottom(BorderStyle.THIN);
			alignRight.setBorderLeft(BorderStyle.THIN);
			alignRight.setBorderTop(BorderStyle.THIN);
			alignRight.setBorderRight(BorderStyle.THIN);
			alignRight.setAlignment(HorizontalAlignment.RIGHT);
			
			
			CellStyle   alignLeft=  wwbook.createCellStyle();
			alignLeft.setBorderBottom(BorderStyle.THIN);
			alignLeft.setBorderLeft(BorderStyle.THIN);
			alignLeft.setBorderTop(BorderStyle.THIN);
			alignLeft.setBorderRight(BorderStyle.THIN);
			alignLeft.setAlignment(HorizontalAlignment.LEFT);
			
		    
			
			XSSFRow hrow = (XSSFRow) wsheet.createRow(row);
			XSSFCell cell = hrow.createCell(col);
			
			cell = hrow.createCell(col);
			wsheet.addMergedRegion(new CellRangeAddress(row, row, col,heading.length-1));
			cell.setCellStyle(alignment1);
			cell.setCellValue(Companyname);
			row ++ ;
			
			hrow = (XSSFRow) wsheet.createRow(row);	
					
			cell = hrow.createCell(col);
			wsheet.addMergedRegion(new CellRangeAddress(row, row, col,heading.length-1));
			cell.setCellStyle(alignment1);
			cell.setCellValue("HQ Master Audit Trail");
			row ++ ;
			
			hrow = (XSSFRow) wsheet.createRow(row);
			cell = hrow.createCell(col);
			wsheet.addMergedRegion(new CellRangeAddress(row, row, col,col = col + 6));
			cell.setCellValue("Report Dated :"+date);
			cell.setCellStyle(alignment1LEFT);
			col ++;
		
			
			
			cell = hrow.createCell(col);	
			wsheet.addMergedRegion(new CellRangeAddress(row, row, col,heading.length-1));
			cell.setCellStyle(alignment1right);
			cell.setCellValue("Username :"+username);
		
			row++;
			
			col = 0;
			hrow = (XSSFRow) wsheet.createRow(row);
			for(int i = 0 ; i<heading.length;i++) {
				cell = hrow.createCell(col);
				cell.setCellValue(heading[i]);
				cell.setCellStyle(headingstyle);
				col++;
			}
			
			
			row ++;	
			col = 0;
			
			String olddiv = "old";
			String newdiv = "";
			
			for(HqMasterAuditTrailModel hq : list) {
				
				
				
				newdiv = hq.getDivision();
				
				if(!newdiv.equals(olddiv) && !olddiv.equals("old")) {
					hrow = (XSSFRow) wsheet.createRow(row);
					cell = hrow.createCell(col);
					cell.setCellValue("Division : "+hq.getDivision());
					wsheet.addMergedRegion(new CellRangeAddress(row, row, col,heading.length-1));
					cell.setCellStyle(alignment1LEFT);
					col++;
					row ++;
				}
				
				col = 0;
				hrow = (XSSFRow) wsheet.createRow(row);
				
				cell = hrow.createCell(col);
				cell.setCellValue(hq.getHq_code());
				cell.setCellStyle(alignLeft);
				col++;
				
				cell = hrow.createCell(col);
				cell.setCellValue(hq.getHq_name());
				cell.setCellStyle(alignLeft);
				col++;
				
				cell = hrow.createCell(col);
				cell.setCellValue(hq.getState());
				cell.setCellStyle(alignLeft);
				col++;
				
				cell = hrow.createCell(col);
				cell.setCellValue(hq.getHq_pool_ind());
				cell.setCellStyle(alignLeft);
				col++;
				
				
				cell = hrow.createCell(col);
				cell.setCellValue(hq.getHq_no_of_reps());
				cell.setCellStyle(alignRight);
				col++;
				
				cell = hrow.createCell(col);
				cell.setCellValue(hq.getHq_pmp_perf());
				cell.setCellStyle(alignRight);
				col++;
				
				cell = hrow.createCell(col);
				cell.setCellValue(hq.getHq_map_code());
				cell.setCellStyle(alignLeft);
				
				col++;
				
			if(hq.getCurr_status().equalsIgnoreCase("CURRENT")) {
				cell = hrow.createCell(col);
				cell.setCellValue("Inserted");
				cell.setCellStyle(alignLeft);
				col++;
				
				cell = hrow.createCell(col);
				cell.setCellValue(hq.getIns_user_name());
				cell.setCellStyle(alignLeft);
				col++;
				
				cell = hrow.createCell(col);
				cell.setCellValue(hq.getInsert_date());
				cell.setCellStyle(alignLeft);
				col++;
				
				cell = hrow.createCell(col);
				cell.setCellValue(hq.getHq_ins_ip_add());
				cell.setCellStyle(alignLeft);
				col++;
			}
			else if(hq.getCurr_status().equalsIgnoreCase("LOG")){
				
				cell = hrow.createCell(col);
				cell.setCellValue("Modified");
				cell.setCellStyle(alignLeft);
				col++;
				
				cell = hrow.createCell(col);
				cell.setCellValue(hq.getMod_user_name());
				cell.setCellStyle(alignLeft);
				col++;
				
				cell = hrow.createCell(col);
				cell.setCellValue(hq.getModify_date());
				cell.setCellStyle(alignLeft);
				col++;
				
				cell = hrow.createCell(col);
				cell.setCellValue(hq.getHq_mod_ip_add());
				cell.setCellStyle(alignLeft);
				col++;
				
			}
			
			cell = hrow.createCell(col);
			cell.setCellValue(hq.getHq_status());
			cell.setCellStyle(alignLeft);
			col++;
			
			
			cell = hrow.createCell(col);
			cell.setCellValue(hq.getCurr_status());
			cell.setCellStyle(alignLeft);
			col++;
			
			olddiv = newdiv;
			row++;
			col = 0;
			
			}
			
			 FileOutputStream fileOut = new FileOutputStream(path.toString());
			 wwbook.write(fileOut);
			    fileOut.close();
				/*
				 * setFileInputStream(new FileInputStream(ff)); setSize(ff.length());
				 */
				filename=usermasterservice.generateExcellock(path.toString(), filename,empId,".xlsx");

	                        
			System.out.println("Excel Created");
		}
		catch (Exception e) {
			throw e;
		}
		finally {
			wwbook.close();
		}
			
		return filename;
	}

}