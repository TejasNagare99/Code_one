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

import com.excel.bean.ReportBean;
import com.excel.model.AllocationDetailReportModel;
import com.excel.model.Usermaster;
import com.excel.repository.UserMasterRepository;
import com.excel.security.PasswordManager;
import com.excel.utility.CodifyErrors;
import com.excel.utility.MedicoConstants;
import com.excel.utility.MedicoResources;

import net.lingala.zip4j.core.ZipFile;
import net.lingala.zip4j.model.ZipParameters;
import net.lingala.zip4j.util.Zip4jConstants;

@Service
public class AllocationDetailReport_ServiceImpl implements AllocationDetailReport_Service{
	@Autowired private UserMasterService usermasterservice;

	
	@Autowired private UserMasterRepository usermasterrepository;
	
	@Override
	public String GenerateAllocationDetailReport(ReportBean bean, List<AllocationDetailReportModel> list, String Companyname,String companyCode,String empId) {
		File ff = null;
		Workbook wwbook = null;
		StringBuffer path = null;
		String filename = null;
		try {
			SimpleDateFormat timeformat = new SimpleDateFormat("hh:mm:ss a");
			System.out.println("generateExcel ----------------------------------------------------------------------");
			long l = new Date().getTime();
			filename = "AllocationDetail" + l + ".xlsx";
			String filePath = MedicoConstants.REPORT_FILE_PATH;
			 path = new StringBuffer(filePath).append("\\");
			path.append(filename);

			
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
			/*
			 * WorkbookSettings ws = new WorkbookSettings(); ws.setLocale(new Locale("en",
			 * "EN")); wwbook = jxl.Workbook.createWorkbook(ff, ws); WritableSheet wsheet =
			 * wwbook.createSheet( "Allocation Sheet", 0);
			 */
		
			wwbook= new XSSFWorkbook();
			Sheet wsheet = wwbook.createSheet("Allocation Sheet");
			wsheet.createFreezePane( 0, 4);
			
			Font font = wwbook.createFont();
			font.setFontHeightInPoints((short)8);
		    font.setFontName("ARIAL");
		    
		    Font font2 = wwbook.createFont();	
			font2.setFontHeightInPoints((short)14);	
			font2.setBold(true);	
		    font2.setFontName("ARIAL");
		    
			CellStyle alignment = wwbook.createCellStyle();
			alignment.setWrapText(true);
			alignment.setBorderBottom(BorderStyle.THIN);
			alignment.setBorderLeft(BorderStyle.THIN);
			alignment.setBorderTop(BorderStyle.THIN);
			alignment.setBorderRight(BorderStyle.THIN);
			alignment.setFont(font);
			//alignment.setWrapText(false);
			alignment.setAlignment(HorizontalAlignment.CENTER);
			alignment.setVerticalAlignment(VerticalAlignment.CENTER);
			alignment.setFillForegroundColor(HSSFColor.HSSFColorPredefined.GREY_25_PERCENT.getIndex());
			alignment.setFillPattern(FillPatternType.SOLID_FOREGROUND);
		

			CellStyle alignment2 = wwbook.createCellStyle();	
			alignment2.setFont(font2);	
			alignment2.setWrapText(false);	
			alignment2.setAlignment(HorizontalAlignment.CENTER);	
			alignment2.setVerticalAlignment(VerticalAlignment.CENTER);	
			alignment2.setFillForegroundColor(HSSFColor.HSSFColorPredefined.LIGHT_YELLOW.getIndex());	
			alignment2.setFillPattern(FillPatternType.SOLID_FOREGROUND);
			
			
		//	jxl.write.Label header;
	//		jxl.write.Number number;
			


			CellStyle lvl3Format = wwbook.createCellStyle();
			lvl3Format.setWrapText(true);
			lvl3Format.setFillForegroundColor(HSSFColor.HSSFColorPredefined.SKY_BLUE.getIndex());
			lvl3Format.setFillPattern(FillPatternType.SOLID_FOREGROUND);
			lvl3Format.setBorderBottom(BorderStyle.THIN);
			lvl3Format.setBorderLeft(BorderStyle.THIN);
			lvl3Format.setBorderTop(BorderStyle.THIN);
			lvl3Format.setBorderRight(BorderStyle.THIN);
			lvl3Format.setFont(font);
			//lvl3Format.setWrapText(false);

			CellStyle lvl2Format = wwbook.createCellStyle();
			lvl2Format.setWrapText(true);
			lvl2Format.setFillForegroundColor(HSSFColor.HSSFColorPredefined.LIGHT_YELLOW.getIndex());
			lvl2Format.setFillPattern(FillPatternType.SOLID_FOREGROUND);
			lvl2Format.setBorderBottom(BorderStyle.THIN);
			lvl2Format.setBorderLeft(BorderStyle.THIN);
			lvl2Format.setBorderTop(BorderStyle.THIN);
			lvl2Format.setBorderRight(BorderStyle.THIN);
			lvl2Format.setFont(font);
			//lvl2Format.setWrapText(false);

			CellStyle lvl1Format =wwbook.createCellStyle();
			lvl1Format.setWrapText(true);
			lvl1Format.setFillForegroundColor(HSSFColor.HSSFColorPredefined.WHITE.getIndex());
			lvl1Format.setFillPattern(FillPatternType.SOLID_FOREGROUND);
			lvl1Format.setBorderBottom(BorderStyle.THIN);
			lvl1Format.setBorderLeft(BorderStyle.THIN);
			lvl1Format.setBorderTop(BorderStyle.THIN);
			lvl1Format.setBorderRight(BorderStyle.THIN);
			lvl1Format.setFont(font);
			//lvl1Format.setWrapText(false);

			int col = 0, row = 0;
			
			XSSFRow hrow = (XSSFRow) wsheet.createRow(row);
			
			XSSFCell cell = hrow.createCell(col);
			
			cell.setCellStyle(alignment2);	
			cell.setCellValue(Companyname);	
			wsheet.addMergedRegion(new CellRangeAddress(row, row, col,col+27));	
			row++;	
				
			hrow = (XSSFRow) wsheet.createRow(row);	
			cell = hrow.createCell(col);	
			cell.setCellStyle(alignment2);	
			cell.setCellValue("Field Master Hierarchy with Address");	
			wsheet.addMergedRegion(new CellRangeAddress(row, row, col,col+27));	
			row++;	
			
			hrow = (XSSFRow) wsheet.createRow(row);	
			cell = hrow.createCell(col);
			cell.setCellValue(MedicoResources.convertUtilDateToString_DD_MM_YYYY(new Date())+" "+timeformat.format(new Date()));
			wsheet.addMergedRegion(new CellRangeAddress(row, row, col,col+27));	
			cell.setCellStyle(alignment2);
			row++;	
			
//			hrow = (XSSFRow) wsheet.createRow(row);	
//			cell = hrow.createCell(col);	
//			cell.setCellStyle(alignment);	
////			cell.setCellValue(output.format(output.parse(frmDate))+" to "+ output.format(output.parse(toDate)));	
//			cell.setCellValue("");	
//			wsheet.addMergedRegion(new CellRangeAddress(row, row, col,col+14));	
//			row++;	
				
			hrow = (XSSFRow) wsheet.createRow(row);			
		
			cell = hrow.createCell(col);
			cell.setCellStyle(alignment);
			cell.setCellValue("RBM Name");
			col++;
			
			/*int widthInChars = 10;
			wsheet.setColumnView(col, widthInChars);*/
			cell = hrow.createCell(col);
			cell.setCellStyle(alignment);
			cell.setCellValue("DM Name");
			col++;
			
			cell = hrow.createCell(col);
			cell.setCellStyle(alignment);
			cell.setCellValue("CFA Location");
			col++;
		
			cell = hrow.createCell(col);
			cell.setCellStyle(alignment);
			cell.setCellValue("Territory");
			col++;
			
			cell = hrow.createCell(col);
			cell.setCellStyle(alignment);
			cell.setCellValue("Sub Company");
			col++;
		
			cell = hrow.createCell(col);
			cell.setCellStyle(alignment);
			cell.setCellValue("Employee No.");
			col++;
			
			cell = hrow.createCell(col);
			cell.setCellStyle(alignment);
			cell.setCellValue("Year");
			col++;
		
			cell = hrow.createCell(col);
			cell.setCellStyle(alignment);
			cell.setCellValue("Month");
			col++;
			
			cell = hrow.createCell(col);
			cell.setCellStyle(alignment);
			cell.setCellValue("Division Name");
			col++;
			
			cell = hrow.createCell(col);
			cell.setCellStyle(alignment);
			cell.setCellValue("Team Code");
			col++;
			
			
			cell = hrow.createCell(col);
			cell.setCellStyle(alignment);
			cell.setCellValue("Designation");
			col++;
			
			
		//	int widthInChars = 12;

			/*wsheet.addCell(new Label(col++, row, "Designation",alignment));
			int widthInChars = 25;
			wsheet.setColumnView(col, widthInChars);*/

			cell = hrow.createCell(col);
			cell.setCellStyle(alignment);
			cell.setCellValue("FS Code");
			col++;
			
			cell = hrow.createCell(col);
			cell.setCellStyle(alignment);
			cell.setCellValue("Staff Id");
			col++;
			
			cell = hrow.createCell(col);
			cell.setCellStyle(alignment);
			cell.setCellValue("FS Name");
			col++;
		
			cell = hrow.createCell(col);
			cell.setCellStyle(alignment);
			cell.setCellValue("Alloc Type");
			col++;
			
			cell = hrow.createCell(col);
			cell.setCellStyle(alignment);
			cell.setCellValue("Product name as per stock statement (header needed)");
			col++;
			
			
			//25May2020
			cell = hrow.createCell(col);
			cell.setCellStyle(alignment);
			cell.setCellValue("Address1");
			col++;
			
			cell = hrow.createCell(col);
			cell.setCellStyle(alignment);
			cell.setCellValue("Address2");
			col++;
			
			cell = hrow.createCell(col);
			cell.setCellStyle(alignment);
			cell.setCellValue("Address3");
			col++;
			
			cell = hrow.createCell(col);
			cell.setCellStyle(alignment);
			cell.setCellValue("Address4");
			col++;
			
			cell = hrow.createCell(col);
			cell.setCellStyle(alignment);
			cell.setCellValue("Destination");
			col++;
			
			cell = hrow.createCell(col);
			cell.setCellStyle(alignment);
			cell.setCellValue("PIN Code");
			col++;
			
			cell = hrow.createCell(col);
			cell.setCellStyle(alignment);
			cell.setCellValue("State");
			col++;
			
			cell = hrow.createCell(col);
			cell.setCellStyle(alignment);
			cell.setCellValue("Email ID");
			col++;
			
			cell = hrow.createCell(col);
			cell.setCellStyle(alignment);
			cell.setCellValue("Mobile No");
			col++;
			
			cell = hrow.createCell(col);
			cell.setCellStyle(alignment);
			cell.setCellValue("Mobile No2");
			col++;											//added
			
			cell = hrow.createCell(col);
			cell.setCellStyle(alignment);
			cell.setCellValue("CFA");
			col++;
			if(!companyCode.trim().equalsIgnoreCase("SER")) {
			cell = hrow.createCell(col);
			cell.setCellStyle(alignment);
			cell.setCellValue("CFA PIPL");
			col++;
			}
			cell = hrow.createCell(col);
			cell.setCellStyle(alignment);
			cell.setCellValue("Remark");
			col++;
			
			
			
			col = 0;

			
			for (AllocationDetailReportModel summary : list) {
				row++;
				hrow = (XSSFRow) wsheet.createRow(row);
				col = 0;
				if (Integer.valueOf(summary.getLevel_code()) == 3) {
					
					cell = hrow.createCell(col);
					cell.setCellStyle(lvl3Format);
					cell.setCellValue(summary.getRM_NAME());
					col++;
					
					cell = hrow.createCell(col);
					cell.setCellStyle(lvl3Format);
					cell.setCellValue(summary.getAM_NAME());
					col++;
								
					cell = hrow.createCell(col);
					cell.setCellStyle(lvl3Format);
					cell.setCellValue(summary.getLOC_NAME());
					col++;			
					
					cell = hrow.createCell(col);
					cell.setCellStyle(lvl3Format);
					cell.setCellValue( summary.getTerritory());
					col++;		
					
					cell = hrow.createCell(col);
					cell.setCellStyle(lvl3Format);
					cell.setCellValue(summary.getSUBCOMP_NM());
					col++;
					
					cell = hrow.createCell(col);
					cell.setCellStyle(lvl3Format);
					cell.setCellValue(summary.getFSTAFF_EMPLOYEE_NO());
					col++;
				
					
					cell = hrow.createCell(col);
					cell.setCellStyle(lvl3Format);
					cell.setCellValue(summary.getYEAR());
					col++;
				
					cell = hrow.createCell(col);
					cell.setCellStyle(lvl3Format);
					cell.setCellValue(summary.getPER_CODE());
					col++;
							
					cell = hrow.createCell(col);
					cell.setCellStyle(lvl3Format);
					cell.setCellValue(summary.getDIVISION());
					col++;
					
					
					cell = hrow.createCell(col);
					cell.setCellStyle(lvl3Format);
					cell.setCellValue(summary.getTeam_code());
					col++;
				
					cell = hrow.createCell(col);
					cell.setCellStyle(lvl3Format);
					cell.setCellValue(summary.getLEVEL_BRIEF_NAME());
					col++;
					
					cell = hrow.createCell(col);
					cell.setCellStyle(lvl3Format);
					cell.setCellValue(summary.getStaff_code());
					col++;
					
					cell = hrow.createCell(col);
					cell.setCellStyle(lvl3Format);
					cell.setCellValue( summary.getFSTAFF_ID());
					col++;
					
					cell = hrow.createCell(col);
					cell.setCellStyle(lvl3Format);
					cell.setCellValue(summary.getStaff_name());
					col++;
					
					
					cell = hrow.createCell(col);
					cell.setCellStyle(lvl3Format);
					cell.setCellValue("FINAL");
					col++;
					
					cell = hrow.createCell(col);
					cell.setCellStyle(lvl3Format);
					cell.setCellValue("");
					col++;
					
					//25May2020
					cell = hrow.createCell(col);
					cell.setCellStyle(lvl3Format);
					cell.setCellValue(summary.getFstaff_addr1());
					col++;
					
					cell = hrow.createCell(col);
					cell.setCellStyle(lvl3Format);
					cell.setCellValue(summary.getFstaff_addr2());
					col++;
					
					cell = hrow.createCell(col);
					cell.setCellStyle(lvl3Format);
					cell.setCellValue(summary.getFstaff_addr3());
					col++;
					
					cell = hrow.createCell(col);
					cell.setCellStyle(lvl3Format);
					cell.setCellValue(summary.getFstaff_addr4());
					col++;
					
					cell = hrow.createCell(col);
					cell.setCellStyle(lvl3Format);
					cell.setCellValue(summary.getDestination());
					col++;
					
					cell = hrow.createCell(col);
					cell.setCellStyle(lvl3Format);
					cell.setCellValue(summary.getFstaff_pincode());
					col++;
					
					cell = hrow.createCell(col);
					cell.setCellStyle(lvl3Format);
					cell.setCellValue(summary.getState_name());
					col++;
					
					cell = hrow.createCell(col);
					cell.setCellStyle(lvl3Format);
					cell.setCellValue(summary.getFstaff_email());
					col++;
					
					cell = hrow.createCell(col);
					cell.setCellStyle(lvl3Format);
					cell.setCellValue(summary.getFstaff_mobile());
					col++;
					
					cell = hrow.createCell(col);
					cell.setCellStyle(lvl3Format);
					cell.setCellValue(summary.getFstaff_mobile2());
					col++;												//added
					
					cell = hrow.createCell(col);
					cell.setCellStyle(lvl3Format);
					cell.setCellValue(summary.getCfa_pfizer());
					col++;
					if(!companyCode.trim().equalsIgnoreCase("SER")) {
					cell = hrow.createCell(col);
					cell.setCellStyle(lvl3Format);
					cell.setCellValue(summary.getCfa_pipl());
					col++;
					}
					cell = hrow.createCell(col);
					cell.setCellStyle(lvl3Format);
					cell.setCellValue(summary.getRemark());
					col++;
				
				
				} else if (Integer.valueOf(summary.getLevel_code()) == 2) {
					
					cell = hrow.createCell(col);
					cell.setCellStyle(lvl2Format);
					cell.setCellValue(summary.getRM_NAME());
					col++;
				
					
					cell = hrow.createCell(col);
					cell.setCellStyle(lvl2Format);
					cell.setCellValue(summary.getAM_NAME());
					col++;
					
					
					cell = hrow.createCell(col);
					cell.setCellStyle(lvl2Format);
					cell.setCellValue(summary.getLOC_NAME());
					col++;
					
					cell = hrow.createCell(col);
					cell.setCellStyle(lvl2Format);
					cell.setCellValue(summary.getTerritory());
					col++;
								
					cell = hrow.createCell(col);
					cell.setCellStyle(lvl2Format);
					cell.setCellValue(summary.getSUBCOMP_NM());
					col++;
					
					cell = hrow.createCell(col);
					cell.setCellStyle(lvl2Format);
					cell.setCellValue(summary.getFSTAFF_EMPLOYEE_NO());
					col++;
										
					cell = hrow.createCell(col);
					cell.setCellStyle(lvl2Format);
					cell.setCellValue(summary.getYEAR());
					col++;
										
					cell = hrow.createCell(col);
					cell.setCellStyle(lvl2Format);
					cell.setCellValue(summary.getPER_CODE());
					col++;
										
					cell = hrow.createCell(col);
					cell.setCellStyle(lvl2Format);
					cell.setCellValue(summary.getDIVISION());
					col++;
					
					cell = hrow.createCell(col);
					cell.setCellStyle(lvl2Format);
					cell.setCellValue(summary.getTeam_code());
					col++;
										
					cell = hrow.createCell(col);
					cell.setCellStyle(lvl2Format);
					cell.setCellValue(summary.getLEVEL_BRIEF_NAME());
					col++;
										
					cell = hrow.createCell(col);
					cell.setCellStyle(lvl2Format);
					cell.setCellValue(summary.getStaff_code());
					col++;
									
					cell = hrow.createCell(col);
					cell.setCellStyle(lvl2Format);
					cell.setCellValue(summary.getFSTAFF_ID());
					col++;
										
					cell = hrow.createCell(col);
					cell.setCellStyle(lvl2Format);
					cell.setCellValue(summary.getStaff_name());
					col++;
										
					cell = hrow.createCell(col);
					cell.setCellStyle(lvl2Format);
					cell.setCellValue("FINAL");
					col++;
									
					cell = hrow.createCell(col);
					cell.setCellStyle(lvl2Format);
					cell.setCellValue("");
					col++;
					
					//25May2020
					cell = hrow.createCell(col);
					cell.setCellStyle(lvl2Format);
					cell.setCellValue(summary.getFstaff_addr1());
					col++;
					
					cell = hrow.createCell(col);
					cell.setCellStyle(lvl2Format);
					cell.setCellValue(summary.getFstaff_addr2());
					col++;
					
					cell = hrow.createCell(col);
					cell.setCellStyle(lvl2Format);
					cell.setCellValue(summary.getFstaff_addr3());
					col++;
					
					cell = hrow.createCell(col);
					cell.setCellStyle(lvl2Format);
					cell.setCellValue(summary.getFstaff_addr4());
					col++;
					
					cell = hrow.createCell(col);
					cell.setCellStyle(lvl2Format);
					cell.setCellValue(summary.getDestination());
					col++;
					
					cell = hrow.createCell(col);
					cell.setCellStyle(lvl2Format);
					cell.setCellValue(summary.getFstaff_pincode());
					col++;
					
					cell = hrow.createCell(col);
					cell.setCellStyle(lvl2Format);
					cell.setCellValue(summary.getState_name());
					col++;
					
					cell = hrow.createCell(col);
					cell.setCellStyle(lvl2Format);
					cell.setCellValue(summary.getFstaff_email());
					col++;
					
					cell = hrow.createCell(col);
					cell.setCellStyle(lvl2Format);
					cell.setCellValue(summary.getFstaff_mobile());
					col++;
					
					cell = hrow.createCell(col);
					cell.setCellStyle(lvl2Format);
					cell.setCellValue(summary.getFstaff_mobile2());
					col++;												//added
					
					cell = hrow.createCell(col);
					cell.setCellStyle(lvl2Format);
					cell.setCellValue(summary.getCfa_pfizer());
					col++;
					
					if(!companyCode.trim().equalsIgnoreCase("SER")) {
					cell = hrow.createCell(col);
					cell.setCellStyle(lvl2Format);
					cell.setCellValue(summary.getCfa_pipl());
					col++;
					}
					cell = hrow.createCell(col);
					cell.setCellStyle(lvl2Format);
					cell.setCellValue(summary.getRemark());
					col++;
					

				
					
				} else {
					cell = hrow.createCell(col);
					cell.setCellStyle(lvl1Format);
					cell.setCellValue(summary.getRM_NAME());
					col++;
										
					cell = hrow.createCell(col);
					cell.setCellStyle(lvl1Format);
					cell.setCellValue(summary.getAM_NAME());
					col++;
										
					cell = hrow.createCell(col);
					cell.setCellStyle(lvl1Format);
					cell.setCellValue(summary.getLOC_NAME());
					col++;
									
					cell = hrow.createCell(col);
					cell.setCellStyle(lvl1Format);
					cell.setCellValue(summary.getTerritory());
					col++;
							
					cell = hrow.createCell(col);
					cell.setCellStyle(lvl1Format);
					cell.setCellValue(summary.getSUBCOMP_NM());
					col++;
									
					cell = hrow.createCell(col);
					cell.setCellStyle(lvl1Format);
					cell.setCellValue(summary.getFSTAFF_EMPLOYEE_NO());
					col++;
									
					cell = hrow.createCell(col);
					cell.setCellStyle(lvl1Format);
					cell.setCellValue(summary.getYEAR());
					col++;
										
					cell = hrow.createCell(col);
					cell.setCellStyle(lvl1Format);
					cell.setCellValue(summary.getPER_CODE());
					col++;
										
					cell = hrow.createCell(col);
					cell.setCellStyle(lvl1Format);
					cell.setCellValue(summary.getDIVISION());
					col++;
								

					cell = hrow.createCell(col);
					cell.setCellStyle(lvl1Format);
					cell.setCellValue(summary.getTeam_code());
					col++;
					
					cell = hrow.createCell(col);
					cell.setCellStyle(lvl1Format);
					cell.setCellValue(summary.getLEVEL_BRIEF_NAME());
					col++;
					
					cell = hrow.createCell(col);
					cell.setCellStyle(lvl1Format);
					cell.setCellValue(summary.getStaff_code());
					col++;
					
					cell = hrow.createCell(col);
					cell.setCellStyle(lvl1Format);
					cell.setCellValue( summary.getFSTAFF_ID());
					col++;
										
					cell = hrow.createCell(col);
					cell.setCellStyle(lvl1Format);
					cell.setCellValue(summary.getStaff_name());
					col++;
										
					cell = hrow.createCell(col);
					cell.setCellStyle(lvl1Format);
					cell.setCellValue("FINAL");
					col++;
									
					cell = hrow.createCell(col);
					cell.setCellStyle(lvl1Format);
					cell.setCellValue("");
					col++;
					
					//25May2020
					cell = hrow.createCell(col);
					cell.setCellStyle(lvl1Format);
					cell.setCellValue(summary.getFstaff_addr1());
					col++;
					
					cell = hrow.createCell(col);
					cell.setCellStyle(lvl1Format);
					cell.setCellValue(summary.getFstaff_addr2());
					col++;
					
					cell = hrow.createCell(col);
					cell.setCellStyle(lvl1Format);
					cell.setCellValue(summary.getFstaff_addr3());
					col++;
					
					cell = hrow.createCell(col);
					cell.setCellStyle(lvl1Format);
					cell.setCellValue(summary.getFstaff_addr4());
					col++;
					
					cell = hrow.createCell(col);
					cell.setCellStyle(lvl1Format);
					cell.setCellValue(summary.getDestination());
					col++;
					
					cell = hrow.createCell(col);
					cell.setCellStyle(lvl1Format);
					cell.setCellValue(summary.getFstaff_pincode());
					col++;
					
					cell = hrow.createCell(col);
					cell.setCellStyle(lvl1Format);
					cell.setCellValue(summary.getState_name());
					col++;
					
					cell = hrow.createCell(col);
					cell.setCellStyle(lvl1Format);
					cell.setCellValue(summary.getFstaff_email());
					col++;
					
					cell = hrow.createCell(col);
					cell.setCellStyle(lvl1Format);
					cell.setCellValue(summary.getFstaff_mobile());
					col++;
					
					cell = hrow.createCell(col);
					cell.setCellStyle(lvl1Format);
					cell.setCellValue(summary.getFstaff_mobile2());
					col++;														//added
					
					cell = hrow.createCell(col);
					cell.setCellStyle(lvl1Format);
					cell.setCellValue(summary.getCfa_pfizer());
					col++;
					
					if(!companyCode.trim().equalsIgnoreCase("SER")) {
					cell = hrow.createCell(col);
					cell.setCellStyle(lvl1Format);
					cell.setCellValue(summary.getCfa_pipl());
					col++;
					}
					cell = hrow.createCell(col);
					cell.setCellStyle(lvl1Format);
					cell.setCellValue(summary.getRemark());
					col++;
					
					
				}
			}
			
			 FileOutputStream fileOut = new FileOutputStream(path.toString());
			 wwbook.write(fileOut);
			 
				filename=usermasterservice.generateExcellock(path.toString(), filename,empId,".xlsx");


			 
			    fileOut.close();
			    wwbook.close();
		
			System.out.println("Excel Created");
		} catch (Exception e) {
			System.out.println("Error Occurred :" +e.getMessage());// uncomment asneeded --;
			
		} 
		
		return filename;
	}

}