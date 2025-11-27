package com.excel.service;

import java.io.File;
import java.io.FileOutputStream;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.apache.poi.ss.usermodel.BorderStyle;
import org.apache.poi.ss.usermodel.CellStyle;
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

import com.excel.model.Annual_sample_plan_view_report;
import com.excel.model.Annual_sample_plan_view_report_batchwise;
import com.excel.model.Annual_sample_plan_view_report_cons;
import com.excel.model.Annual_sample_plan_view_report_itemwise;
import com.excel.model.Company;
import com.excel.model.Usermaster;
import com.excel.repository.UserMasterRepository;
import com.excel.security.PasswordManager;
import com.excel.utility.ApachePoiExcelFormat;
import com.excel.utility.CodifyErrors;
import com.excel.utility.MedicoConstants;

import net.lingala.zip4j.core.ZipFile;
import net.lingala.zip4j.model.ZipParameters;
import net.lingala.zip4j.util.Zip4jConstants;

@Service
public class Annual_sample_plan_view_ServiceImpl implements Annual_sample_plan_view_Service,MedicoConstants{
	@Autowired private UserMasterService usermasterservice;

	@Autowired HttpSession session;
	@Autowired private UserMasterRepository usermasterrepository;

	@Override
	public String generate_annual_sample_plan_view_report(List<Annual_sample_plan_view_report> list,String companyname,String finyr,String companyCode,String empId) throws Exception {
		//TODO Auto-generated method stub
		
		Workbook wwbook = null;
		File ff = null;
		String filename = null;
		StringBuffer path = null;

		try {
			String finyear =finyr.substring(2);
			Integer year = Integer.parseInt(finyear);
			
			long l = new Date().getTime();
			filename = "Annual_sample_plan_view_report" + l + ".xlsx";
			String filePath = MedicoConstants.REPORT_FILE_PATH;

			ff = new File(filePath);
			path = new StringBuffer(filePath).append("\\");
			path.append(filename );
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
			
			int col = 0;
			int row = 0;
			
			wwbook = new XSSFWorkbook();
		

			ApachePoiExcelFormat xlsxExcelFormat = new ApachePoiExcelFormat();
			
			CellStyle sheetHeading = xlsxExcelFormat.SheetHeading(wwbook);
			CellStyle columnHeading = xlsxExcelFormat.columnHeading(wwbook);
			columnHeading.setAlignment(HorizontalAlignment.CENTER);
			columnHeading.setVerticalAlignment(VerticalAlignment.CENTER);
			
			CellStyle leftAlign = xlsxExcelFormat.dataLeftAlign(wwbook);
			
 			CellStyle columnHeadingBlue = xlsxExcelFormat.columnSubHeadingBlue(wwbook);
 			
 			
			CellStyle leftAlignB = xlsxExcelFormat.dataBoldLeftAlign(wwbook);
			
			
			
			Font font = wwbook.createFont();
			font.setFontHeightInPoints((short) 11);
			font.setFontName("ARIAL");
			font.setBold(true);
			CellStyle decimalstyle = wwbook.createCellStyle();
		//	decimalstyle.setDataFormat(wwbook.createDataFormat().getFormat("0.00"));
			decimalstyle.setAlignment(HorizontalAlignment.RIGHT);
			decimalstyle.setFont(font);
			decimalstyle.setBorderLeft(BorderStyle.THIN);
			decimalstyle.setBorderRight(BorderStyle.THIN);
			
			
			Font font2 = wwbook.createFont();
			font2.setFontHeightInPoints((short) 11);
			font2.setFontName("ARIAL");
			CellStyle rightandleftborder = wwbook.createCellStyle();
			rightandleftborder.setAlignment(HorizontalAlignment.RIGHT);
			rightandleftborder.setBorderLeft(BorderStyle.THIN);
			rightandleftborder.setBorderRight(BorderStyle.THIN);
			rightandleftborder.setFont(font2);
			
			Font font3 = wwbook.createFont();
			font3.setFontHeightInPoints((short) 11);
			font3.setFontName("ARIAL");
			font3.setBold(true);
			CellStyle decimalstyle2 = wwbook.createCellStyle();
		//	decimalstyle2.setDataFormat(wwbook.createDataFormat().getFormat("0.00"));
			decimalstyle2.setAlignment(HorizontalAlignment.RIGHT);
			decimalstyle2.setFont(font);
			decimalstyle2.setBorderLeft(BorderStyle.THIN);
			decimalstyle2.setBorderRight(BorderStyle.THIN);
			decimalstyle2.setBorderBottom(BorderStyle.THIN);
			
			
			Font font4 = wwbook.createFont();
			font4.setFontHeightInPoints((short) 11);
			font4.setFontName("ARIAL");
			font4.setBold(true);
			CellStyle decimalstyle3 = wwbook.createCellStyle();
			decimalstyle3.setDataFormat(wwbook.createDataFormat().getFormat("0.00"));
			decimalstyle3.setAlignment(HorizontalAlignment.RIGHT);
			decimalstyle3.setFont(font4);
			
			
			Font font5 = wwbook.createFont();
			font5.setFontHeightInPoints((short) 11);
			font5.setFontName("ARIAL");
			font5.setBold(true);
			CellStyle decimalstyle4 = wwbook.createCellStyle();
			//decimalstyle4.setDataFormat(wwbook.createDataFormat().getFormat("0.00"));
			decimalstyle4.setAlignment(HorizontalAlignment.RIGHT);
			decimalstyle4.setFont(font5);
			
			
			Font font6 = wwbook.createFont();
			font6.setFontHeightInPoints((short) 11);
			font6.setFontName("ARIAL");
			font6.setBold(true);
			CellStyle decimalstyle5 = wwbook.createCellStyle();
			//decimalstyle5.setDataFormat(wwbook.createDataFormat().getFormat("0.00"));
			decimalstyle5.setAlignment(HorizontalAlignment.RIGHT);
			decimalstyle5.setFont(font);
		//	decimalstyle5.setBorderLeft(BorderStyle.THIN);
		//	decimalstyle5.setBorderRight(BorderStyle.THIN);
			decimalstyle5.setBorderBottom(BorderStyle.THIN);
			
			String[] heading1= {"BU","Team Name","Product Code","Brand","Product Name","Presence in India Market","Types of Samples","COG/Units","No.of TE/TSO"
					," ","Qtr 1","Qtr 2","Qtr 3","Qtr 4","Total"};
			
		//	String[]heading2= {"Dec'18","Jan'19","Feb'19","Mar'19","Apr'19","May'19","Jun'19","Jul'19","Aug'19","Sep'19","Oct'19","Nov'19","Dec'19"};
			
			Sheet wsheet = wwbook.createSheet(list.get(0).getTeam());
			wsheet.createFreezePane(0, 5);								//added
			
			XSSFRow hrow = null;
			XSSFCell cell = null;
			
			BigDecimal tot_unitDec19 = new BigDecimal(0);
			BigDecimal tot_unitJan19 = new BigDecimal(0);
			BigDecimal tot_unitFeb19 = new BigDecimal(0);
			BigDecimal tot_unitMar19 = new BigDecimal(0);
			BigDecimal tot_unitApr19 = new BigDecimal(0);
			BigDecimal tot_unitMay19 = new BigDecimal(0);
			BigDecimal tot_unitJun19 = new BigDecimal(0);
			BigDecimal tot_unitJul19 = new BigDecimal(0);
			BigDecimal tot_unitAug19 = new BigDecimal(0);
			BigDecimal tot_unitSep19 = new BigDecimal(0);
			BigDecimal tot_unitOct19 = new BigDecimal(0);
			BigDecimal tot_unitNov19 = new BigDecimal(0);		
			BigDecimal tot_of_totunit = new BigDecimal(0);
			
			BigDecimal tot_of_pso = new BigDecimal(0);
			
			BigDecimal tot_costDec19 = new BigDecimal(0);
			BigDecimal tot_costJan19 = new BigDecimal(0);
			BigDecimal tot_costFeb19 = new BigDecimal(0);
			BigDecimal tot_costMar19 = new BigDecimal(0);
			BigDecimal tot_costApr19 = new BigDecimal(0);
			BigDecimal tot_costMay19 = new BigDecimal(0);
			BigDecimal tot_costJun19 = new BigDecimal(0);
			BigDecimal tot_costJul19 = new BigDecimal(0);
			BigDecimal tot_costAug19 = new BigDecimal(0);
			BigDecimal tot_costSep19 = new BigDecimal(0);
			BigDecimal tot_costOct19 = new BigDecimal(0);
			BigDecimal tot_costNov19 = new BigDecimal(0);	
			BigDecimal tot_of_totcost = new BigDecimal(0);	
			
			BigDecimal grnd_tot_costDec19 = new BigDecimal(0);
			BigDecimal grnd_tot_costJan19 = new BigDecimal(0);
			BigDecimal grnd_tot_costFeb19 = new BigDecimal(0);
			BigDecimal grnd_tot_costMar19 = new BigDecimal(0);
			BigDecimal grnd_tot_costApr19 = new BigDecimal(0);
			BigDecimal grnd_tot_costMay19 = new BigDecimal(0);
			BigDecimal grnd_tot_costJun19 = new BigDecimal(0);
			BigDecimal grnd_tot_costJul19 = new BigDecimal(0);
			BigDecimal grnd_tot_costAug19 = new BigDecimal(0);
			BigDecimal grnd_tot_costSep19 = new BigDecimal(0);
			BigDecimal grnd_tot_costOct19 = new BigDecimal(0);
			BigDecimal grnd_tot_costNov19 = new BigDecimal(0);	
			BigDecimal grnd_tot_of_grd_totcost = new BigDecimal(0);	
			
			String old_team = "old";
			String new_team ="";
			
			hrow =  (XSSFRow) wsheet.createRow(row);
			cell = hrow.createCell(col);
			cell.setCellStyle(sheetHeading);
			cell.setCellValue("Annual Sample Plan Report");
			wsheet.addMergedRegion(new CellRangeAddress(row, row, col, col + (heading1.length)+7));
			row++;
			
			
			hrow =  (XSSFRow) wsheet.createRow(row);
			cell = hrow.createCell(col);
			cell.setCellStyle(sheetHeading);
			cell.setCellValue(companyname);
			wsheet.addMergedRegion(new CellRangeAddress(row, row, col, col + (heading1.length)+7));
			row++;
			
			
			hrow =  (XSSFRow) wsheet.createRow(row);
			
			cell = hrow.createCell(col);
			cell.setCellStyle(columnHeading);
			cell.setCellValue("BU");
			wsheet.addMergedRegion(new CellRangeAddress(row, row+2, col, col));
			col++;
							
			cell = hrow.createCell(col);
			cell.setCellStyle(columnHeading);
			cell.setCellValue("Team Name");
			wsheet.addMergedRegion(new CellRangeAddress(row, row+2, col, col));
			col++;	
			
			cell = hrow.createCell(col);
			cell.setCellStyle(columnHeading);
			cell.setCellValue("Product Code");
			wsheet.addMergedRegion(new CellRangeAddress(row, row+2, col, col));
			col++;	
			
			cell = hrow.createCell(col);
			cell.setCellStyle(columnHeading);
			cell.setCellValue("Brand");
			wsheet.addMergedRegion(new CellRangeAddress(row, row+2, col, col));
			col++;	
			
			cell = hrow.createCell(col);
			cell.setCellStyle(columnHeading);
			cell.setCellValue("Product Name");
			wsheet.addMergedRegion(new CellRangeAddress(row, row+2, col, col));
			col++;	
			
			cell = hrow.createCell(col);
			cell.setCellStyle(columnHeading);
			cell.setCellValue("Presence in India Market");
			wsheet.addMergedRegion(new CellRangeAddress(row, row+2, col, col));
			col++;	
			
			cell = hrow.createCell(col);
			cell.setCellStyle(columnHeading);
			cell.setCellValue("Type of samples");
			wsheet.addMergedRegion(new CellRangeAddress(row, row+2, col, col));
			col++;	
			
			cell = hrow.createCell(col);
			cell.setCellStyle(columnHeading);
			cell.setCellValue("COG/Unit");
			wsheet.addMergedRegion(new CellRangeAddress(row, row+2, col, col));
			col++;	
			
			cell = hrow.createCell(col);
			cell.setCellStyle(columnHeading);
			cell.setCellValue("No.of TE/TSO");
			wsheet.addMergedRegion(new CellRangeAddress(row, row+2, col, col));
			col++;	
			
			cell = hrow.createCell(col);
			cell.setCellStyle(columnHeading);
			cell.setCellValue("  ");
			wsheet.addMergedRegion(new CellRangeAddress(row, row=row+2, col, col));
			col++;
			
			
			cell = hrow.createCell(col);
			cell.setCellStyle(columnHeading);
			cell.setCellValue("Qtr 1");
			wsheet.addMergedRegion(new CellRangeAddress(2, row-1, col, col+2));
			col++;
			
			cell = hrow.createCell(col);
			cell.setCellStyle(columnHeading);
		//	cell.setCellValue("  ");			
			col++;
			
			
			cell = hrow.createCell(col);
			cell.setCellStyle(columnHeading);						//This Blank cells are for border
		//	cell.setCellValue("  ");		
			col++;	
			
			cell = hrow.createCell(col);
			cell.setCellStyle(columnHeading);
			cell.setCellValue("Qtr 2");
			wsheet.addMergedRegion(new CellRangeAddress(2, row-1, col, col+2));
			col++;	
						
			cell = hrow.createCell(col);
			cell.setCellStyle(columnHeading);
		//	cell.setCellValue("  ");			
			col++;
			
			
			cell = hrow.createCell(col);
			cell.setCellStyle(columnHeading);
		//	cell.setCellValue("  ");	
			col++;
			
			cell = hrow.createCell(col);
			cell.setCellStyle(columnHeading);
			cell.setCellValue("Qtr 3");
			wsheet.addMergedRegion(new CellRangeAddress(2, row-1, col, col+2));
			col++;	
			
			cell = hrow.createCell(col);
			cell.setCellStyle(columnHeading);
		//	cell.setCellValue("  ");			
			col++;
			
			
			cell = hrow.createCell(col);
			cell.setCellStyle(columnHeading);
		//	cell.setCellValue("  ");	
			col++;
			
			cell = hrow.createCell(col);
			cell.setCellStyle(columnHeading);
			cell.setCellValue("Qtr 4");
			wsheet.addMergedRegion(new CellRangeAddress(2, row-1, col, col+2));
			col++;	
			
			cell = hrow.createCell(col);
			cell.setCellStyle(columnHeading);
		//	cell.setCellValue("  ");			
			col++;
			
			
			cell = hrow.createCell(col);
			cell.setCellStyle(columnHeading);
		//	cell.setCellValue("  ");	
			col++;
			
			cell = hrow.createCell(col);
			cell.setCellStyle(columnHeading);
			cell.setCellValue("Total");
			wsheet.addMergedRegion(new CellRangeAddress(2, row, col, col));
			
			//row for border
			hrow =  (XSSFRow) wsheet.createRow(3);
			col=0;
			for(int i=0;i<23;i++) {
				cell = hrow.createCell(col);
				cell.setCellStyle(columnHeading);
			//	cell.setCellValue("  ");	
				col++;
			}
			
	
			col=0;
			
			hrow =  (XSSFRow) wsheet.createRow(row);
			
			for(int i=0;i<10;i++) {
				cell = hrow.createCell(col);
				cell.setCellStyle(columnHeading);
			//	cell.setCellValue("  ");	
				col++;
			}
			if(companyCode.trim().equals("SER")) {
				cell = hrow.createCell(col);
				cell.setCellStyle(columnHeading);
				cell.setCellValue("Apr'"+(year));
				col++;
				
				cell = hrow.createCell(col);
				cell.setCellStyle(columnHeading);
				cell.setCellValue("May'"+(year));
				col++;
				
				cell = hrow.createCell(col);
				cell.setCellStyle(columnHeading);
				cell.setCellValue("Jun'"+(year));
				col++;
				
				cell = hrow.createCell(col);
				cell.setCellStyle(columnHeading);
				cell.setCellValue("Jul'"+(year));
				col++;
				
				cell = hrow.createCell(col);
				cell.setCellStyle(columnHeading);
				cell.setCellValue("Aug'"+(year));
				col++;
				
				
				cell = hrow.createCell(col);
				cell.setCellStyle(columnHeading);
				cell.setCellValue("Sep'"+(year));
				col++;
				
				cell = hrow.createCell(col);
				cell.setCellStyle(columnHeading);
				cell.setCellValue("Oct'"+(year));
				col++;
				
				cell = hrow.createCell(col);
				cell.setCellStyle(columnHeading);
				cell.setCellValue("Nov'"+(year));
				col++;
				
				cell = hrow.createCell(col);
				cell.setCellStyle(columnHeading);
				cell.setCellValue("Dec'"+(year));
				col++;
				
				cell = hrow.createCell(col);
				cell.setCellStyle(columnHeading);
				cell.setCellValue("Jan'"+(year+1));
				col++;
				
				cell = hrow.createCell(col);
				cell.setCellStyle(columnHeading);
				cell.setCellValue("Feb'"+(year+1));
				col++;
				
				cell = hrow.createCell(col);
				cell.setCellStyle(columnHeading);
				cell.setCellValue("Mar'"+(year+1));
				col++;	
			}
			else {
			cell = hrow.createCell(col);
			cell.setCellStyle(columnHeading);
			cell.setCellValue("Dec'"+(year-1));
			col++;
			
			cell = hrow.createCell(col);
			cell.setCellStyle(columnHeading);
			cell.setCellValue("Jan'"+(year));
			col++;
			
			cell = hrow.createCell(col);
			cell.setCellStyle(columnHeading);
			cell.setCellValue("Feb'"+(year));
			col++;
			
			cell = hrow.createCell(col);
			cell.setCellStyle(columnHeading);
			cell.setCellValue("Mar'"+(year));
			col++;
			
			cell = hrow.createCell(col);
			cell.setCellStyle(columnHeading);
			cell.setCellValue("Apr'"+(year));
			col++;
			
			
			cell = hrow.createCell(col);
			cell.setCellStyle(columnHeading);
			cell.setCellValue("May'"+(year));
			col++;
			
			cell = hrow.createCell(col);
			cell.setCellStyle(columnHeading);
			cell.setCellValue("Jun'"+(year));
			col++;
			
			cell = hrow.createCell(col);
			cell.setCellStyle(columnHeading);
			cell.setCellValue("Jul'"+(year));
			col++;
			
			cell = hrow.createCell(col);
			cell.setCellStyle(columnHeading);
			cell.setCellValue("Aug'"+(year));
			col++;
			
			cell = hrow.createCell(col);
			cell.setCellStyle(columnHeading);
			cell.setCellValue("Sep'"+(year));
			col++;
			
			cell = hrow.createCell(col);
			cell.setCellStyle(columnHeading);
			cell.setCellValue("Oct'"+(year));
			col++;
			
			cell = hrow.createCell(col);
			cell.setCellStyle(columnHeading);
			cell.setCellValue("Nov'"+(year));
			col++;
		}

			cell = hrow.createCell(col);
			cell.setCellStyle(columnHeading);
		//	cell.setCellValue("  ");	
		
			row++;
			col=0;
			
			for(Annual_sample_plan_view_report ps : list) {
			
				new_team = ps.getTeam();
				if(!new_team.equalsIgnoreCase(old_team) && !old_team.equalsIgnoreCase("old")) {
					
					 //Grand Total
					 hrow = (XSSFRow) wsheet.createRow(row); // blank row
						
						
						cell = hrow.createCell(col); 
						wsheet.addMergedRegion(new CellRangeAddress(row, row, col, col=col + 8));
						col++;
						
						
						for(int i = col;i<23;i++) {
						cell = hrow.createCell(col); 
						cell.setCellStyle(rightandleftborder);
						col++;
						}
						
						row++;
						col=0;
					
					hrow = (XSSFRow) wsheet.createRow(row); // Grand Total Row1
					

					
					for(int i = col;i<=8;i++) {
						cell = hrow.createCell(col); 
						cell.setCellStyle(decimalstyle5);
						col++;
						}
					
					cell = hrow.createCell(col); 
					cell.setCellStyle(decimalstyle2);
					cell.setCellValue("Grand Total (Rs.)");
					col++;
					
					cell = hrow.createCell(col); 
					cell.setCellStyle(decimalstyle2);
					cell.setCellValue(grnd_tot_costDec19.longValue());
					grnd_tot_of_grd_totcost = grnd_tot_of_grd_totcost.add(grnd_tot_costDec19);
					col++;
					
					cell = hrow.createCell(col); 
					cell.setCellStyle(decimalstyle2);
					cell.setCellValue(grnd_tot_costJan19.longValue());
					grnd_tot_of_grd_totcost = grnd_tot_of_grd_totcost.add(grnd_tot_costJan19);
					col++;
					
					cell = hrow.createCell(col); 
					cell.setCellStyle(decimalstyle2);
					cell.setCellValue(grnd_tot_costFeb19.longValue());
					grnd_tot_of_grd_totcost = grnd_tot_of_grd_totcost.add(grnd_tot_costFeb19);
					col++;
					
					cell = hrow.createCell(col); 
					cell.setCellStyle(decimalstyle2);
					cell.setCellValue(grnd_tot_costMar19.longValue());
					grnd_tot_of_grd_totcost = grnd_tot_of_grd_totcost.add(grnd_tot_costMar19);
					col++;
					
					cell = hrow.createCell(col); 
					cell.setCellStyle(decimalstyle2);
					cell.setCellValue(grnd_tot_costApr19.longValue());
					grnd_tot_of_grd_totcost = grnd_tot_of_grd_totcost.add(grnd_tot_costApr19);
					col++;
					
					cell = hrow.createCell(col); 
					cell.setCellStyle(decimalstyle2);
					cell.setCellValue(grnd_tot_costMay19.longValue());
					grnd_tot_of_grd_totcost = grnd_tot_of_grd_totcost.add(grnd_tot_costMay19);
					col++;
					
					cell = hrow.createCell(col); 
					cell.setCellStyle(decimalstyle2);
					cell.setCellValue(grnd_tot_costJun19.longValue());
					grnd_tot_of_grd_totcost = grnd_tot_of_grd_totcost.add(grnd_tot_costJun19);
					col++;
					
					cell = hrow.createCell(col); 
					cell.setCellStyle(decimalstyle2);
					cell.setCellValue(grnd_tot_costJul19.longValue());
					grnd_tot_of_grd_totcost = grnd_tot_of_grd_totcost.add(grnd_tot_costJul19);
					col++;
					
					cell = hrow.createCell(col); 
					cell.setCellStyle(decimalstyle2);
					cell.setCellValue(grnd_tot_costAug19.longValue());
					grnd_tot_of_grd_totcost = grnd_tot_of_grd_totcost.add(grnd_tot_costAug19);
					col++;
					
					cell = hrow.createCell(col); 
					cell.setCellStyle(decimalstyle2);
					cell.setCellValue(grnd_tot_costSep19.longValue());
					grnd_tot_of_grd_totcost = grnd_tot_of_grd_totcost.add(grnd_tot_costSep19);
					col++;
					
					cell = hrow.createCell(col); 
					cell.setCellStyle(decimalstyle2);
					cell.setCellValue(grnd_tot_costOct19.longValue());
					grnd_tot_of_grd_totcost = grnd_tot_of_grd_totcost.add(grnd_tot_costOct19);
					col++;
					
					cell = hrow.createCell(col); 
					cell.setCellStyle(decimalstyle2);
					cell.setCellValue(grnd_tot_costNov19.longValue());
					grnd_tot_of_grd_totcost = grnd_tot_of_grd_totcost.add(grnd_tot_costNov19);
					col++;
					
					cell = hrow.createCell(col); 
					cell.setCellStyle(decimalstyle2);
					cell.setCellValue(grnd_tot_of_grd_totcost.longValue());
					col++;
					
					 grnd_tot_costDec19 = BigDecimal.ZERO;
					 grnd_tot_costJan19 = BigDecimal.ZERO;
					 grnd_tot_costFeb19 = BigDecimal.ZERO;
					 grnd_tot_costMar19 = BigDecimal.ZERO;
					 grnd_tot_costApr19 = BigDecimal.ZERO;
					 grnd_tot_costMay19 = BigDecimal.ZERO;
					 grnd_tot_costJun19 = BigDecimal.ZERO;
					 grnd_tot_costJul19 = BigDecimal.ZERO;
					 grnd_tot_costAug19 = BigDecimal.ZERO;
					 grnd_tot_costSep19 = BigDecimal.ZERO;
					 grnd_tot_costOct19 = BigDecimal.ZERO;
					 grnd_tot_costNov19 = BigDecimal.ZERO;
					 grnd_tot_of_grd_totcost = BigDecimal.ZERO;
					
					
					
					row = 0;
					col = 0;
					
					wsheet = wwbook.createSheet(ps.getTeam());			//adding new sheet
					wsheet.createFreezePane(0, 5);						//added
					hrow =  (XSSFRow) wsheet.createRow(row);
					
					cell = hrow.createCell(col);
					cell.setCellStyle(sheetHeading);
					cell.setCellValue("Annual Sample Plan Report");
					wsheet.addMergedRegion(new CellRangeAddress(row, row, col, col + (heading1.length)+7));
					row++;
					
					
					hrow =  (XSSFRow) wsheet.createRow(row);
					cell = hrow.createCell(col);
					cell.setCellStyle(sheetHeading);
					cell.setCellValue(companyname);
					wsheet.addMergedRegion(new CellRangeAddress(row, row, col, col + (heading1.length)+7));
					row++;
					
					
					hrow =  (XSSFRow) wsheet.createRow(row);
					
					cell = hrow.createCell(col);
					cell.setCellStyle(columnHeading);
					cell.setCellValue("BU");
					wsheet.addMergedRegion(new CellRangeAddress(row, row+2, col, col));
					col++;
									
					cell = hrow.createCell(col);
					cell.setCellStyle(columnHeading);
					cell.setCellValue("Team Name");
					wsheet.addMergedRegion(new CellRangeAddress(row, row+2, col, col));
					col++;	
					
					cell = hrow.createCell(col);
					cell.setCellStyle(columnHeading);
					cell.setCellValue("Product Code");
					wsheet.addMergedRegion(new CellRangeAddress(row, row+2, col, col));
					col++;	
					
					cell = hrow.createCell(col);
					cell.setCellStyle(columnHeading);
					cell.setCellValue("Brand");
					wsheet.addMergedRegion(new CellRangeAddress(row, row+2, col, col));
					col++;	
					
					cell = hrow.createCell(col);
					cell.setCellStyle(columnHeading);
					cell.setCellValue("Product Name");
					wsheet.addMergedRegion(new CellRangeAddress(row, row+2, col, col));
					col++;	
					
					cell = hrow.createCell(col);
					cell.setCellStyle(columnHeading);
					cell.setCellValue("Presence in India Market");
					wsheet.addMergedRegion(new CellRangeAddress(row, row+2, col, col));
					col++;	
					
					cell = hrow.createCell(col);
					cell.setCellStyle(columnHeading);
					cell.setCellValue("Type of samples");
					wsheet.addMergedRegion(new CellRangeAddress(row, row+2, col, col));
					col++;	
					
					cell = hrow.createCell(col);
					cell.setCellStyle(columnHeading);
					cell.setCellValue("COG/Unit");
					wsheet.addMergedRegion(new CellRangeAddress(row, row+2, col, col));
					col++;	
					
					cell = hrow.createCell(col);
					cell.setCellStyle(columnHeading);
					cell.setCellValue("No.of TE/TSO");
					wsheet.addMergedRegion(new CellRangeAddress(row, row+2, col, col));
					col++;	
					
					cell = hrow.createCell(col);
					cell.setCellStyle(columnHeading);
					cell.setCellValue("  ");
					wsheet.addMergedRegion(new CellRangeAddress(row, row=row+2, col, col));
					col++;
					
					
					cell = hrow.createCell(col);
					cell.setCellStyle(columnHeading);
					cell.setCellValue("Qtr 1");
					wsheet.addMergedRegion(new CellRangeAddress(2, row-1, col, col+2));
					col++;
					
					cell = hrow.createCell(col);
					cell.setCellStyle(columnHeading);
				//	cell.setCellValue("  ");			
					col++;
					
					
					cell = hrow.createCell(col);
					cell.setCellStyle(columnHeading);						//This Blank cells are for border
				//	cell.setCellValue("  ");		
					col++;	
					
					cell = hrow.createCell(col);
					cell.setCellStyle(columnHeading);
					cell.setCellValue("Qtr 2");
					wsheet.addMergedRegion(new CellRangeAddress(2, row-1, col, col+2));
					col++;	
								
					cell = hrow.createCell(col);
					cell.setCellStyle(columnHeading);
				//	cell.setCellValue("  ");			
					col++;
					
					
					cell = hrow.createCell(col);
					cell.setCellStyle(columnHeading);
				//	cell.setCellValue("  ");	
					col++;
					
					cell = hrow.createCell(col);
					cell.setCellStyle(columnHeading);
					cell.setCellValue("Qtr 3");
					wsheet.addMergedRegion(new CellRangeAddress(2, row-1, col, col+2));
					col++;	
					
					cell = hrow.createCell(col);
					cell.setCellStyle(columnHeading);
				//	cell.setCellValue("  ");			
					col++;
					
					
					cell = hrow.createCell(col);
					cell.setCellStyle(columnHeading);
				//	cell.setCellValue("  ");	
					col++;
					
					cell = hrow.createCell(col);
					cell.setCellStyle(columnHeading);
					cell.setCellValue("Qtr 4");
					wsheet.addMergedRegion(new CellRangeAddress(2, row-1, col, col+2));
					col++;	
					
					cell = hrow.createCell(col);
					cell.setCellStyle(columnHeading);
				//	cell.setCellValue("  ");			
					col++;
					
					
					cell = hrow.createCell(col);
					cell.setCellStyle(columnHeading);
				//	cell.setCellValue("  ");	
					col++;
					
					cell = hrow.createCell(col);
					cell.setCellStyle(columnHeading);
					cell.setCellValue("Total");
					wsheet.addMergedRegion(new CellRangeAddress(2, row, col, col));
					
					//row for border
					hrow =  (XSSFRow) wsheet.createRow(3);
					col=0;
					for(int i=0;i<23;i++) {
						cell = hrow.createCell(col);
						cell.setCellStyle(columnHeading);
					//	cell.setCellValue("  ");	
						col++;
					}
					
			
					col=0;
					
					hrow =  (XSSFRow) wsheet.createRow(row);
					
					for(int i=0;i<10;i++) {
						cell = hrow.createCell(col);
						cell.setCellStyle(columnHeading);
					//	cell.setCellValue("  ");	
						col++;
					}
					
					cell = hrow.createCell(col);
					cell.setCellStyle(columnHeading);
					cell.setCellValue("Dec'"+(year-1));
					col++;
					
					cell = hrow.createCell(col);
					cell.setCellStyle(columnHeading);
					cell.setCellValue("Jan'"+year);
					col++;
					
					cell = hrow.createCell(col);
					cell.setCellStyle(columnHeading);
					cell.setCellValue("Feb'"+year);
					col++;
					
					cell = hrow.createCell(col);
					cell.setCellStyle(columnHeading);
					cell.setCellValue("Mar'"+year);
					col++;
					
					cell = hrow.createCell(col);
					cell.setCellStyle(columnHeading);
					cell.setCellValue("Apr'"+year);
					col++;
					
					
					cell = hrow.createCell(col);
					cell.setCellStyle(columnHeading);
					cell.setCellValue("May'"+year);
					col++;
					
					cell = hrow.createCell(col);
					cell.setCellStyle(columnHeading);
					cell.setCellValue("Jun'"+year);
					col++;
					
					cell = hrow.createCell(col);
					cell.setCellStyle(columnHeading);
					cell.setCellValue("Jul'"+year);
					col++;
					
					cell = hrow.createCell(col);
					cell.setCellStyle(columnHeading);
					cell.setCellValue("Aug'"+year);
					col++;
					
					cell = hrow.createCell(col);
					cell.setCellStyle(columnHeading);
					cell.setCellValue("Sep'"+year);
					col++;
					
					cell = hrow.createCell(col);
					cell.setCellStyle(columnHeading);
					cell.setCellValue("Oct'"+year);
					col++;
					
					cell = hrow.createCell(col);
					cell.setCellStyle(columnHeading);
					cell.setCellValue("Nov'"+year);
					col++;
					

					cell = hrow.createCell(col);
					cell.setCellStyle(columnHeading);
				//	cell.setCellValue("  ");	
				
					row++;
					col=0;
				}
			
			

				
				hrow =  (XSSFRow) wsheet.createRow(row);		//blank row
				cell = hrow.createCell(col);
				//	cell.setCellStyle(columnHeading);
					cell.setCellValue(" ");
					col++;
					
					cell = hrow.createCell(col);
					//	cell.setCellStyle(columnHeading);
					cell.setCellValue(" ");
					col++;
					
					cell = hrow.createCell(col);
					//	cell.setCellStyle(columnHeading);
					cell.setCellValue(" ");
					col++;
					
					cell = hrow.createCell(col);
					//	cell.setCellStyle(columnHeading);
					cell.setCellValue(" ");
					col++;
								
					cell = hrow.createCell(col);
					//	cell.setCellStyle(columnHeading);
					cell.setCellValue(" ");
					col++;
					
					cell = hrow.createCell(col);
					//	cell.setCellStyle(columnHeading);
					cell.setCellValue(" ");
					col++;
					
					cell = hrow.createCell(col);
					//	cell.setCellStyle(columnHeading);
					cell.setCellValue(" ");
					col++;
					
					cell = hrow.createCell(col);
					cell.setCellStyle(decimalstyle3);
					cell.setCellValue(" ");
					col++;
					
					cell = hrow.createCell(col);
					cell.setCellStyle(decimalstyle4);
					//wsheet.addMergedRegion(new CellRangeAddress(row, row, col, col+14));		
					cell.setCellValue(" ");
					col++;
					cell = hrow.createCell(col);
					cell.setCellStyle(rightandleftborder);
					//wsheet.addMergedRegion(new CellRangeAddress(row, row, col, col+14));		
					cell.setCellValue(" ");
					col++;
					
					for(int i = col;i<23;i++) {
					cell = hrow.createCell(col);
					cell.setCellStyle(rightandleftborder);
					//wsheet.addMergedRegion(new CellRangeAddress(row, row, col, col+14));		
					cell.setCellValue(" ");
					col++;
					}
					
					row++;
					col=0;
				
				hrow =  (XSSFRow) wsheet.createRow(row);		//row 1
				
				cell = hrow.createCell(col);
			//	cell.setCellStyle(columnHeading);
				cell.setCellValue(ps.getBu());
				col++;
				
				cell = hrow.createCell(col);
				//	cell.setCellStyle(columnHeading);
				cell.setCellValue(ps.getTeam());
				col++;
				
				cell = hrow.createCell(col);
				//	cell.setCellStyle(columnHeading);
				cell.setCellValue(ps.getProduct_code());
				col++;
				
				cell = hrow.createCell(col);
				//	cell.setCellStyle(columnHeading);
				cell.setCellValue(ps.getSa_group_name());
				col++;
							
				cell = hrow.createCell(col);
				//	cell.setCellStyle(columnHeading);
				cell.setCellValue(ps.getSmp_prod_name());
				col++;
				
				cell = hrow.createCell(col);
				//	cell.setCellStyle(columnHeading);
				cell.setCellValue(ps.getPre_in_market());
				col++;
				
				cell = hrow.createCell(col);
				//	cell.setCellStyle(columnHeading);
				cell.setCellValue(ps.getSgprmdet_disp_nm());
				col++;
				
				cell = hrow.createCell(col);
				cell.setCellStyle(decimalstyle3);
				cell.setCellValue(ps.getCog_rate_perunit().doubleValue());
				col++;
				
				cell = hrow.createCell(col);
				cell.setCellStyle(decimalstyle4);
				//wsheet.addMergedRegion(new CellRangeAddress(row, row, col, col+14));		
				cell.setCellValue(ps.getNo_of_te().longValue());
				col++;
				cell = hrow.createCell(col);
				cell.setCellStyle(rightandleftborder);
				//wsheet.addMergedRegion(new CellRangeAddress(row, row, col, col+14));		
				cell.setCellValue(" ");
				col++;
				
				for(int i = col;i<23;i++) {
				cell = hrow.createCell(col);
				cell.setCellStyle(rightandleftborder);
				//wsheet.addMergedRegion(new CellRangeAddress(row, row, col, col+14));		
				cell.setCellValue(" ");
				col++;
				}
				
				
				
				row++;
				col=0;
				hrow =  (XSSFRow) wsheet.createRow(row);		//row 2
				
				cell = hrow.createCell(col);
				//	cell.setCellStyle(columnHeading);
				cell.setCellValue(" ");
				wsheet.addMergedRegion(new CellRangeAddress(row, row, col, col=col+7));
				col++;
				
				cell = hrow.createCell(col);
				//cell.setCellStyle(rightandleftborder);
				cell.setCellValue(" ");
				col++;
				
				cell = hrow.createCell(col);
				cell.setCellStyle(rightandleftborder);
				cell.setCellValue("Per PSO");
				col++;
				
				cell = hrow.createCell(col);
				cell.setCellStyle(rightandleftborder);
				cell.setCellValue(ps.getDec_19().doubleValue());
				tot_unitDec19 = ps.getNo_of_te().multiply(ps.getDec_19());			
				tot_of_pso = tot_of_pso.add(ps.getDec_19());
				col++;
				
				cell = hrow.createCell(col);
				cell.setCellStyle(rightandleftborder);
				cell.setCellValue(ps.getJan_20().doubleValue());
				tot_unitJan19 = ps.getNo_of_te().multiply(ps.getJan_20());
				tot_of_pso = tot_of_pso.add(ps.getJan_20());
				col++;
				
				cell = hrow.createCell(col);
				cell.setCellStyle(rightandleftborder);
				cell.setCellValue(ps.getFeb_20().doubleValue());
				tot_unitFeb19 = ps.getNo_of_te().multiply(ps.getFeb_20());
				tot_of_pso = tot_of_pso.add(ps.getFeb_20());
				col++;
				
				
				cell = hrow.createCell(col);
				cell.setCellStyle(rightandleftborder);
				cell.setCellValue(ps.getMar_20().doubleValue());
				tot_unitMar19 = ps.getNo_of_te().multiply(ps.getMar_20());
				tot_of_pso = tot_of_pso.add(ps.getMar_20());
				col++;
				
				cell = hrow.createCell(col);
				cell.setCellStyle(rightandleftborder);
				cell.setCellValue(ps.getApr_20().doubleValue());
				tot_unitApr19 = ps.getNo_of_te().multiply(ps.getApr_20());
				tot_of_pso = tot_of_pso.add(ps.getApr_20());
				col++;
				
				cell = hrow.createCell(col);
				cell.setCellStyle(rightandleftborder);
				cell.setCellValue(ps.getMay_20().doubleValue());
				tot_unitMay19 = ps.getNo_of_te().multiply(ps.getMay_20());
				tot_of_pso = tot_of_pso.add(ps.getMay_20());
				col++;
				
			
				cell = hrow.createCell(col);
				cell.setCellStyle(rightandleftborder);
				cell.setCellValue(ps.getJun_20().doubleValue());
				tot_unitJun19 = ps.getNo_of_te().multiply(ps.getJun_20());
				tot_of_pso = tot_of_pso.add(ps.getJun_20());
				col++;
				
				cell = hrow.createCell(col);
				cell.setCellStyle(rightandleftborder);
				cell.setCellValue(ps.getJul_20().doubleValue());
				tot_unitJul19 = ps.getNo_of_te().multiply(ps.getJul_20());
				tot_of_pso = tot_of_pso.add(ps.getJul_20());
				col++;
				
				cell = hrow.createCell(col);
				cell.setCellStyle(rightandleftborder);
				cell.setCellValue(ps.getAug_20().doubleValue());
				tot_unitAug19 = ps.getNo_of_te().multiply(ps.getAug_20());
				tot_of_pso = tot_of_pso.add(ps.getAug_20());
				col++;
				
				cell = hrow.createCell(col);
				cell.setCellStyle(rightandleftborder);
				cell.setCellValue(ps.getSep_20().doubleValue());
				tot_unitSep19 = ps.getNo_of_te().multiply(ps.getSep_20());
				tot_of_pso = tot_of_pso.add(ps.getSep_20());
				col++;
				
				cell = hrow.createCell(col);
				cell.setCellStyle(rightandleftborder);
				cell.setCellValue(ps.getOct_20().doubleValue());
				tot_unitOct19 = ps.getNo_of_te().multiply(ps.getOct_20());
				tot_of_pso = tot_of_pso.add(ps.getOct_20());
				col++;
				
				cell = hrow.createCell(col);
				cell.setCellStyle(rightandleftborder);
				cell.setCellValue(ps.getNov_20().doubleValue());
				tot_unitNov19 = ps.getNo_of_te().multiply(ps.getNov_20());
				tot_of_pso = tot_of_pso.add(ps.getNov_20());
				col++;
				
				
				cell = hrow.createCell(col);
				cell.setCellStyle(rightandleftborder);
				cell.setCellValue(tot_of_pso.doubleValue());
				col++;
				
				
				row++;
				col = 0;
				hrow = (XSSFRow) wsheet.createRow(row); // row 3

				cell = hrow.createCell(col); // cell.setCellStyle(columnHeading);
				cell.setCellValue(" ");
				wsheet.addMergedRegion(new CellRangeAddress(row, row, col, col=col + 8));
				col++;

				cell = hrow.createCell(col); 
				cell.setCellStyle(rightandleftborder);
				cell.setCellValue("Total Units");
				col++;

				// formula : perpso * te = total units and cogunit * totalunit

				cell = hrow.createCell(col); 
				cell.setCellStyle(rightandleftborder);
				cell.setCellValue(tot_unitDec19.longValue());
				tot_costDec19 = tot_unitDec19.multiply(ps.getCog_rate_perunit());
				tot_of_totunit = tot_of_totunit.add(tot_unitDec19);
				col++;

				cell = hrow.createCell(col); 
				cell.setCellStyle(rightandleftborder);
				cell.setCellValue(tot_unitJan19.longValue());
				tot_of_totunit = tot_of_totunit.add(tot_unitJan19);
				tot_costJan19 = tot_unitJan19.multiply(ps.getCog_rate_perunit());
				col++;

				cell = hrow.createCell(col); 
				cell.setCellStyle(rightandleftborder);
				cell.setCellValue(tot_unitFeb19.longValue());
				tot_of_totunit = tot_of_totunit.add(tot_unitFeb19);
				tot_costFeb19 = tot_unitFeb19.multiply(ps.getCog_rate_perunit());
				col++;

				cell = hrow.createCell(col);
				cell.setCellStyle(rightandleftborder);
				cell.setCellValue(tot_unitMar19.longValue());
				tot_of_totunit = tot_of_totunit.add(tot_unitMar19);
				tot_costMar19 = tot_unitMar19.multiply(ps.getCog_rate_perunit());
				col++;

				cell = hrow.createCell(col); 
				cell.setCellStyle(rightandleftborder);
				cell.setCellValue(tot_unitApr19.longValue());
				tot_of_totunit = tot_of_totunit.add(tot_unitApr19);
				tot_costApr19 = tot_unitApr19.multiply(ps.getCog_rate_perunit());
				col++;

				cell = hrow.createCell(col); 
				cell.setCellValue(tot_unitMay19.longValue());
				cell.setCellStyle(rightandleftborder);
				tot_of_totunit = tot_of_totunit.add(tot_unitMay19);
				tot_costMay19 = tot_unitMay19.multiply(ps.getCog_rate_perunit());
				col++;

				cell = hrow.createCell(col); // cell.setCellStyle(columnHeading);
				cell.setCellStyle(rightandleftborder);
				cell.setCellValue(tot_unitJun19.longValue());
				tot_of_totunit = tot_of_totunit.add(tot_unitJun19);
				tot_costJun19 = tot_unitJun19.multiply(ps.getCog_rate_perunit());
				col++;

				cell = hrow.createCell(col); // cell.setCellStyle(columnHeading);
				cell.setCellStyle(rightandleftborder);
				cell.setCellValue(tot_unitJul19.longValue());
				tot_of_totunit = tot_of_totunit.add(tot_unitJul19);
				tot_costJul19 = tot_unitJul19.multiply(ps.getCog_rate_perunit());
				col++;

				cell = hrow.createCell(col); // cell.setCellStyle(columnHeading);
				cell.setCellStyle(rightandleftborder);
				cell.setCellValue(tot_unitAug19.longValue());
				tot_of_totunit = tot_of_totunit.add(tot_unitAug19);
				tot_costAug19 = tot_unitAug19.multiply(ps.getCog_rate_perunit());
				col++;

				cell = hrow.createCell(col); // cell.setCellStyle(columnHeading);
				cell.setCellStyle(rightandleftborder);
				cell.setCellValue(tot_unitSep19.longValue());
				tot_of_totunit = tot_of_totunit.add(tot_unitSep19);
				tot_costSep19 = tot_unitSep19.multiply(ps.getCog_rate_perunit());
				col++;

				cell = hrow.createCell(col); // cell.setCellStyle(columnHeading);
				cell.setCellStyle(rightandleftborder);
				cell.setCellValue(tot_unitOct19.longValue());
				tot_of_totunit = tot_of_totunit.add(tot_unitOct19);
				tot_costOct19 = tot_unitOct19.multiply(ps.getCog_rate_perunit());
				col++;

				cell = hrow.createCell(col); // cell.setCellStyle(columnHeading);
				cell.setCellStyle(rightandleftborder);
				cell.setCellValue(tot_unitNov19.longValue());
				tot_of_totunit = tot_of_totunit.add(tot_unitNov19);
				tot_costNov19 = tot_unitNov19.multiply(ps.getCog_rate_perunit());
				col++;

				cell = hrow.createCell(col); // cell.setCellStyle(columnHeading);
				cell.setCellStyle(rightandleftborder);
				cell.setCellValue(tot_of_totunit.longValue());
				col++;

				row++;
				col = 0;
				hrow = (XSSFRow) wsheet.createRow(row); // row 4

				cell = hrow.createCell(col); // cell.setCellStyle(columnHeading);
				cell.setCellValue(" ");
				wsheet.addMergedRegion(new CellRangeAddress(row, row, col, col=col + 8));
				col++;

				cell = hrow.createCell(col);
				 cell.setCellStyle(decimalstyle);
				cell.setCellValue("Total Cost (Rs.)");
				col++;

				cell = hrow.createCell(col); 
				 cell.setCellStyle(decimalstyle);
				cell.setCellValue(tot_costDec19.longValue());
				tot_of_totcost = tot_of_totcost.add(tot_costDec19);
				
				grnd_tot_costDec19 = grnd_tot_costDec19.add(tot_costDec19);
				col++;

				cell = hrow.createCell(col); 
				cell.setCellStyle(decimalstyle);
				cell.setCellValue(tot_costJan19.longValue());
				tot_of_totcost = tot_of_totcost.add(tot_costJan19);
				
				grnd_tot_costJan19 = grnd_tot_costJan19.add(tot_costJan19);
				col++;

				cell = hrow.createCell(col); 
				cell.setCellStyle(decimalstyle);
				cell.setCellValue(tot_costFeb19.longValue());
				tot_of_totcost = tot_of_totcost.add(tot_costFeb19);
				
				grnd_tot_costFeb19 = grnd_tot_costFeb19.add(tot_costFeb19);
				col++;

				cell = hrow.createCell(col); 
				cell.setCellStyle(decimalstyle);
				cell.setCellValue(tot_costMar19.longValue());
				tot_of_totcost = tot_of_totcost.add(tot_costMar19);
				
				grnd_tot_costMar19 = grnd_tot_costMar19.add(tot_costMar19);
				col++;

				cell = hrow.createCell(col); 
				cell.setCellStyle(decimalstyle);
				cell.setCellValue(tot_costApr19.longValue());
				tot_of_totcost = tot_of_totcost.add(tot_costApr19);
				
				grnd_tot_costApr19 = grnd_tot_costApr19.add(tot_costApr19);
				col++;

				cell = hrow.createCell(col); 
				cell.setCellStyle(decimalstyle);
				cell.setCellValue(tot_costMay19.longValue());
				tot_of_totcost = tot_of_totcost.add(tot_costMay19);
				
				grnd_tot_costMay19 = grnd_tot_costMay19.add(tot_costMay19);
				col++;

				cell = hrow.createCell(col); 
				cell.setCellStyle(decimalstyle);
				cell.setCellValue(tot_costJun19.longValue());
				tot_of_totcost = tot_of_totcost.add(tot_costJun19);
				
				grnd_tot_costJun19 = grnd_tot_costJun19.add(tot_costJun19);
				col++;

				cell = hrow.createCell(col); 
				cell.setCellStyle(decimalstyle);
				cell.setCellValue(tot_costJul19.longValue());
				tot_of_totcost = tot_of_totcost.add(tot_costJul19);
				
				grnd_tot_costJul19 = grnd_tot_costJul19.add(tot_costJul19);
				col++;

				cell = hrow.createCell(col); 
				cell.setCellStyle(decimalstyle);
				cell.setCellValue(tot_costAug19.longValue());
				tot_of_totcost = tot_of_totcost.add(tot_costAug19);
				
				grnd_tot_costAug19 = grnd_tot_costAug19.add(tot_costAug19);
				col++;

				cell = hrow.createCell(col); 
				cell.setCellStyle(decimalstyle);
				cell.setCellValue(tot_costSep19.longValue());
				tot_of_totcost = tot_of_totcost.add(tot_costSep19);
				
				grnd_tot_costSep19 = grnd_tot_costSep19.add(tot_costSep19);
				col++;

				cell = hrow.createCell(col); 
				cell.setCellStyle(decimalstyle);
				cell.setCellValue(tot_costOct19.longValue());
				tot_of_totcost = tot_of_totcost.add(tot_costOct19);
				
				grnd_tot_costOct19 = grnd_tot_costOct19.add(tot_costOct19);
				col++;

				cell = hrow.createCell(col); 
				cell.setCellStyle(decimalstyle);
				cell.setCellValue(tot_costNov19.longValue());
				tot_of_totcost = tot_of_totcost.add(tot_costNov19);
				
				grnd_tot_costNov19 = grnd_tot_costNov19.add(tot_costNov19);
				col++;

				cell = hrow.createCell(col); 
				cell.setCellStyle(decimalstyle);
				cell.setCellValue(tot_of_totcost.longValue());
				
				col++; 
				
				col =0;
				
				
				
				 tot_unitDec19 =  BigDecimal.ZERO;
				 tot_unitJan19 =  BigDecimal.ZERO;
				 tot_unitFeb19 =  BigDecimal.ZERO;
				 tot_unitMar19 =  BigDecimal.ZERO;
				 tot_unitApr19 =  BigDecimal.ZERO;
				 tot_unitMay19 =  BigDecimal.ZERO;
				 tot_unitJun19 =  BigDecimal.ZERO;
				 tot_unitJul19 =  BigDecimal.ZERO;
				 tot_unitAug19 =  BigDecimal.ZERO;
				 tot_unitSep19 =  BigDecimal.ZERO;
				 tot_unitOct19 =  BigDecimal.ZERO;
				 tot_unitNov19 =  BigDecimal.ZERO;
				 tot_of_totunit=  BigDecimal.ZERO;
				 tot_of_pso    =  BigDecimal.ZERO;
				
				 
				 tot_costDec19 = BigDecimal.ZERO;
				 tot_costJan19 = BigDecimal.ZERO;
				 tot_costFeb19 = BigDecimal.ZERO;
				 tot_costMar19 = BigDecimal.ZERO;
				 tot_costApr19 = BigDecimal.ZERO;
				 tot_costMay19 = BigDecimal.ZERO;
				 tot_costJun19 = BigDecimal.ZERO;
				 tot_costJul19 = BigDecimal.ZERO;
				 tot_costAug19 = BigDecimal.ZERO;
				 tot_costSep19 = BigDecimal.ZERO;
				 tot_costOct19 = BigDecimal.ZERO;
				 tot_costNov19 = BigDecimal.ZERO;
				 tot_of_totcost= BigDecimal.ZERO;
				 
					row++;
					col=0;
					old_team = new_team;
					
				 
			}
			
			 //Grand Total
			 hrow = (XSSFRow) wsheet.createRow(row); // blank row
				
				
				cell = hrow.createCell(col); 
				wsheet.addMergedRegion(new CellRangeAddress(row, row, col, col=col + 8));
				col++;
				
				
				for(int i = col;i<23;i++) {
				cell = hrow.createCell(col); 
				cell.setCellStyle(rightandleftborder);
				col++;
				}
				
				row++;
				col=0;
			
			hrow = (XSSFRow) wsheet.createRow(row); // Grand Total Row1
			

			
			for(int i = col;i<=8;i++) {
				cell = hrow.createCell(col); 
				cell.setCellStyle(decimalstyle5);
				col++;
				}
			
			cell = hrow.createCell(col); 
			cell.setCellStyle(decimalstyle2);
			cell.setCellValue("Grand Total (Rs.)");
			col++;
			
			cell = hrow.createCell(col); 
			cell.setCellStyle(decimalstyle2);
			cell.setCellValue(grnd_tot_costDec19.longValue());
			grnd_tot_of_grd_totcost = grnd_tot_of_grd_totcost.add(grnd_tot_costDec19);
			col++;
			
			cell = hrow.createCell(col); 
			cell.setCellStyle(decimalstyle2);
			cell.setCellValue(grnd_tot_costJan19.longValue());
			grnd_tot_of_grd_totcost = grnd_tot_of_grd_totcost.add(grnd_tot_costJan19);
			col++;
			
			cell = hrow.createCell(col); 
			cell.setCellStyle(decimalstyle2);
			cell.setCellValue(grnd_tot_costFeb19.longValue());
			grnd_tot_of_grd_totcost = grnd_tot_of_grd_totcost.add(grnd_tot_costFeb19);
			col++;
			
			cell = hrow.createCell(col); 
			cell.setCellStyle(decimalstyle2);
			cell.setCellValue(grnd_tot_costMar19.longValue());
			grnd_tot_of_grd_totcost = grnd_tot_of_grd_totcost.add(grnd_tot_costMar19);
			col++;
			
			cell = hrow.createCell(col); 
			cell.setCellStyle(decimalstyle2);
			cell.setCellValue(grnd_tot_costApr19.longValue());
			grnd_tot_of_grd_totcost = grnd_tot_of_grd_totcost.add(grnd_tot_costApr19);
			col++;
			
			cell = hrow.createCell(col); 
			cell.setCellStyle(decimalstyle2);
			cell.setCellValue(grnd_tot_costMay19.longValue());
			grnd_tot_of_grd_totcost = grnd_tot_of_grd_totcost.add(grnd_tot_costMay19);
			col++;
			
			cell = hrow.createCell(col); 
			cell.setCellStyle(decimalstyle2);
			cell.setCellValue(grnd_tot_costJun19.longValue());
			grnd_tot_of_grd_totcost = grnd_tot_of_grd_totcost.add(grnd_tot_costJun19);
			col++;
			
			cell = hrow.createCell(col); 
			cell.setCellStyle(decimalstyle2);
			cell.setCellValue(grnd_tot_costJul19.longValue());
			grnd_tot_of_grd_totcost = grnd_tot_of_grd_totcost.add(grnd_tot_costJul19);
			col++;
			
			cell = hrow.createCell(col); 
			cell.setCellStyle(decimalstyle2);
			cell.setCellValue(grnd_tot_costAug19.longValue());
			grnd_tot_of_grd_totcost = grnd_tot_of_grd_totcost.add(grnd_tot_costAug19);
			col++;
			
			cell = hrow.createCell(col); 
			cell.setCellStyle(decimalstyle2);
			cell.setCellValue(grnd_tot_costSep19.longValue());
			grnd_tot_of_grd_totcost = grnd_tot_of_grd_totcost.add(grnd_tot_costSep19);
			col++;
			
			cell = hrow.createCell(col); 
			cell.setCellStyle(decimalstyle2);
			cell.setCellValue(grnd_tot_costOct19.longValue());
			grnd_tot_of_grd_totcost = grnd_tot_of_grd_totcost.add(grnd_tot_costOct19);
			col++;
			
			cell = hrow.createCell(col); 
			cell.setCellStyle(decimalstyle2);
			cell.setCellValue(grnd_tot_costNov19.longValue());
			grnd_tot_of_grd_totcost = grnd_tot_of_grd_totcost.add(grnd_tot_costNov19);
			col++;
			
			cell = hrow.createCell(col); 
			cell.setCellStyle(decimalstyle2);
			cell.setCellValue(grnd_tot_of_grd_totcost.longValue());
			col++;
			
			

			FileOutputStream fileOut = new FileOutputStream(path.toString());
			wwbook.write(fileOut);
			fileOut.close();
			
			System.out.println("Excel Created");
			filename=usermasterservice.generateExcellock(path.toString(), filename,empId,".xlsx");

		}
		catch (Exception e) {
			// TODO: handle exception
			System.out.println("Error Occurred :" +e.getMessage());// uncomment asneeded --;
		}
		
		return filename;
	}

	//Batchwise Report
	@Override
	public String generate_annual_sample_plan_view_Batchwise_report(List<Annual_sample_plan_view_report_batchwise> list,
			String companyname) throws Exception {
		//TODO Auto-generated method stub
		
		Workbook wwbook = null;
		File ff = null;
		String filename = null;
		StringBuffer path = null;

		try {

			long l = new Date().getTime();
			filename = "Annual_sample_plan_view_batchwise_report" + l + ".xlsx";
			String filePath = MedicoConstants.REPORT_FILE_PATH;

			ff = new File(filePath);
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
			
			int col = 0;
			int row = 0;
			
			wwbook = new XSSFWorkbook();
		

			ApachePoiExcelFormat xlsxExcelFormat = new ApachePoiExcelFormat();
			
			CellStyle sheetHeading = xlsxExcelFormat.SheetHeading(wwbook);
			CellStyle columnHeading = xlsxExcelFormat.columnHeading(wwbook);
			columnHeading.setAlignment(HorizontalAlignment.CENTER);
			columnHeading.setVerticalAlignment(VerticalAlignment.CENTER);
			
			CellStyle leftAlign = xlsxExcelFormat.dataLeftAlign(wwbook);
			
 			CellStyle columnHeadingBlue = xlsxExcelFormat.columnSubHeadingBlue(wwbook);
 			
 			
			CellStyle leftAlignB = xlsxExcelFormat.dataBoldLeftAlign(wwbook);
			
			
			
			Font font = wwbook.createFont();
			font.setFontHeightInPoints((short) 11);
			font.setFontName("ARIAL");
			font.setBold(true);
			CellStyle decimalstyle = wwbook.createCellStyle();
		//	decimalstyle.setDataFormat(wwbook.createDataFormat().getFormat("0.00"));
			decimalstyle.setAlignment(HorizontalAlignment.RIGHT);
			decimalstyle.setFont(font);
			decimalstyle.setBorderLeft(BorderStyle.THIN);
			decimalstyle.setBorderRight(BorderStyle.THIN);
			
			
			Font font2 = wwbook.createFont();
			font2.setFontHeightInPoints((short) 11);
			font2.setFontName("ARIAL");
			CellStyle rightandleftborder = wwbook.createCellStyle();
			rightandleftborder.setAlignment(HorizontalAlignment.RIGHT);
			rightandleftborder.setBorderLeft(BorderStyle.THIN);
			rightandleftborder.setBorderRight(BorderStyle.THIN);
			rightandleftborder.setFont(font2);
			
			Font font3 = wwbook.createFont();
			font3.setFontHeightInPoints((short) 11);
			font3.setFontName("ARIAL");
			font3.setBold(true);
			CellStyle decimalstyle2 = wwbook.createCellStyle();
		//	decimalstyle2.setDataFormat(wwbook.createDataFormat().getFormat("0.00"));
			decimalstyle2.setAlignment(HorizontalAlignment.RIGHT);
			decimalstyle2.setFont(font);
			decimalstyle2.setBorderLeft(BorderStyle.THIN);
			decimalstyle2.setBorderRight(BorderStyle.THIN);
			decimalstyle2.setBorderBottom(BorderStyle.THIN);
			
			
			Font font4 = wwbook.createFont();
			font4.setFontHeightInPoints((short) 11);
			font4.setFontName("ARIAL");
			font4.setBold(true);
			CellStyle decimalstyle3 = wwbook.createCellStyle();
			decimalstyle3.setDataFormat(wwbook.createDataFormat().getFormat("0.00"));
			decimalstyle3.setAlignment(HorizontalAlignment.RIGHT);
			decimalstyle3.setFont(font4);
			
			
			Font font5 = wwbook.createFont();
			font5.setFontHeightInPoints((short) 11);
			font5.setFontName("ARIAL");
			font5.setBold(true);
			CellStyle decimalstyle4 = wwbook.createCellStyle();
			//decimalstyle4.setDataFormat(wwbook.createDataFormat().getFormat("0.00"));
			decimalstyle4.setAlignment(HorizontalAlignment.RIGHT);
			decimalstyle4.setFont(font5);
			
			
			Font font6 = wwbook.createFont();
			font6.setFontHeightInPoints((short) 11);
			font6.setFontName("ARIAL");
			font6.setBold(true);
			CellStyle decimalstyle5 = wwbook.createCellStyle();
			//decimalstyle5.setDataFormat(wwbook.createDataFormat().getFormat("0.00"));
			decimalstyle5.setAlignment(HorizontalAlignment.RIGHT);
			decimalstyle5.setFont(font6);
		//	decimalstyle5.setBorderLeft(BorderStyle.THIN);
		//	decimalstyle5.setBorderRight(BorderStyle.THIN);
			decimalstyle5.setBorderBottom(BorderStyle.THIN);
			
		
			CellStyle bottomline = wwbook.createCellStyle();
		//	decimalstyle2.setDataFormat(wwbook.createDataFormat().getFormat("0.00"));
			bottomline.setAlignment(HorizontalAlignment.RIGHT);
			bottomline.setBorderBottom(BorderStyle.THIN);
			
			
			CellStyle totalstyle = wwbook.createCellStyle();
			//decimalstyle4.setDataFormat(wwbook.createDataFormat().getFormat("0.00"));
			totalstyle.setAlignment(HorizontalAlignment.RIGHT);
			totalstyle.setBorderBottom(BorderStyle.THIN);
			totalstyle.setBorderTop(BorderStyle.THIN);
			totalstyle.setFont(font5);
			
			CellStyle totalstyle2 = wwbook.createCellStyle();
			//decimalstyle4.setDataFormat(wwbook.createDataFormat().getFormat("0.00"));
			totalstyle2.setAlignment(HorizontalAlignment.RIGHT);
			totalstyle2.setBorderBottom(BorderStyle.THIN);
			totalstyle2.setBorderTop(BorderStyle.THIN);
			totalstyle2.setBorderLeft(BorderStyle.THIN);
			totalstyle2.setBorderRight(BorderStyle.THIN);
			totalstyle2.setFont(font5);
			
			String[] heading1= {"BU","Team Name","Product Code","Brand","Product Name","Presence in India Market","Types of Samples","COG/Units","No.of TE/TSO"
					,"Annual Sample Units","Current Warehouse Stock","Batch No","Expiry Date"};
			
		//	String[]heading2= {"Dec'18","Jan'19","Feb'19","Mar'19","Apr'19","May'19","Jun'19","Jul'19","Aug'19","Sep'19","Oct'19","Nov'19","Dec'19"};
			
			Sheet wsheet = wwbook.createSheet(list.get(0).getTeam());
			wsheet.createFreezePane(0, 3);								//added
			
			XSSFRow hrow = null;
			XSSFCell cell = null;
			
			BigDecimal tot_unitDec19 = new BigDecimal(0);
			BigDecimal tot_unitJan19 = new BigDecimal(0);
			BigDecimal tot_unitFeb19 = new BigDecimal(0);
			BigDecimal tot_unitMar19 = new BigDecimal(0);
			BigDecimal tot_unitApr19 = new BigDecimal(0);
			BigDecimal tot_unitMay19 = new BigDecimal(0);
			BigDecimal tot_unitJun19 = new BigDecimal(0);
			BigDecimal tot_unitJul19 = new BigDecimal(0);
			BigDecimal tot_unitAug19 = new BigDecimal(0);
			BigDecimal tot_unitSep19 = new BigDecimal(0);
			BigDecimal tot_unitOct19 = new BigDecimal(0);
			BigDecimal tot_unitNov19 = new BigDecimal(0);		
			BigDecimal tot_of_totunit = new BigDecimal(0);
			
			BigDecimal tot_of_pso = new BigDecimal(0);
			
			BigDecimal tot_costDec19 = new BigDecimal(0);
			BigDecimal tot_costJan19 = new BigDecimal(0);
			BigDecimal tot_costFeb19 = new BigDecimal(0);
			BigDecimal tot_costMar19 = new BigDecimal(0);
			BigDecimal tot_costApr19 = new BigDecimal(0);
			BigDecimal tot_costMay19 = new BigDecimal(0);
			BigDecimal tot_costJun19 = new BigDecimal(0);
			BigDecimal tot_costJul19 = new BigDecimal(0);
			BigDecimal tot_costAug19 = new BigDecimal(0);
			BigDecimal tot_costSep19 = new BigDecimal(0);
			BigDecimal tot_costOct19 = new BigDecimal(0);
			BigDecimal tot_costNov19 = new BigDecimal(0);	
			BigDecimal tot_of_totcost = new BigDecimal(0);	
			
			BigDecimal grnd_tot_costDec19 = new BigDecimal(0);
			BigDecimal grnd_tot_costJan19 = new BigDecimal(0);
			BigDecimal grnd_tot_costFeb19 = new BigDecimal(0);
			BigDecimal grnd_tot_costMar19 = new BigDecimal(0);
			BigDecimal grnd_tot_costApr19 = new BigDecimal(0);
			BigDecimal grnd_tot_costMay19 = new BigDecimal(0);
			BigDecimal grnd_tot_costJun19 = new BigDecimal(0);
			BigDecimal grnd_tot_costJul19 = new BigDecimal(0);
			BigDecimal grnd_tot_costAug19 = new BigDecimal(0);
			BigDecimal grnd_tot_costSep19 = new BigDecimal(0);
			BigDecimal grnd_tot_costOct19 = new BigDecimal(0);
			BigDecimal grnd_tot_costNov19 = new BigDecimal(0);	
			BigDecimal grnd_tot_of_grd_totcost = new BigDecimal(0);	
			
			BigDecimal curr_warehouse_stock = new BigDecimal(0);
			BigDecimal tot_sample_units = new BigDecimal(0);
			
			
			String old_team = "old";
			String new_team ="";
			
			String old_prod = "old";
			String new_prod = "";
			
			hrow =  (XSSFRow) wsheet.createRow(row);
			cell = hrow.createCell(col);
			cell.setCellStyle(sheetHeading);
			cell.setCellValue("Annual Sample Plan with Batchwise Stock Report");
			wsheet.addMergedRegion(new CellRangeAddress(row, row, col, col + (heading1.length)-1));
			row++;
			
			
			hrow =  (XSSFRow) wsheet.createRow(row);
			cell = hrow.createCell(col);
			cell.setCellStyle(sheetHeading);
			cell.setCellValue(companyname);
			wsheet.addMergedRegion(new CellRangeAddress(row, row, col, col + (heading1.length)-1));
			row++;
			
			
			hrow =  (XSSFRow) wsheet.createRow(row);
			
			cell = hrow.createCell(col);
			cell.setCellStyle(columnHeading);
			cell.setCellValue("BU");
	//		wsheet.addMergedRegion(new CellRangeAddress(row, row+2, col, col));
			col++;
							
			cell = hrow.createCell(col);
			cell.setCellStyle(columnHeading);
			cell.setCellValue("Team Name");
	//		wsheet.addMergedRegion(new CellRangeAddress(row, row+2, col, col));
			col++;	
			
			cell = hrow.createCell(col);
			cell.setCellStyle(columnHeading);
			cell.setCellValue("Product Code");
	//		wsheet.addMergedRegion(new CellRangeAddress(row, row+2, col, col));
			col++;	
			
			cell = hrow.createCell(col);
			cell.setCellStyle(columnHeading);
			cell.setCellValue("Brand");
//			wsheet.addMergedRegion(new CellRangeAddress(row, row+2, col, col));
			col++;	
			
			cell = hrow.createCell(col);
			cell.setCellStyle(columnHeading);
			cell.setCellValue("Product Name");
//			wsheet.addMergedRegion(new CellRangeAddress(row, row+2, col, col));
			col++;	
			
			cell = hrow.createCell(col);
			cell.setCellStyle(columnHeading);
			cell.setCellValue("Presence in India Market");
//			wsheet.addMergedRegion(new CellRangeAddress(row, row+2, col, col));
			col++;	
			
			cell = hrow.createCell(col);
			cell.setCellStyle(columnHeading);
			cell.setCellValue("Type of samples");
//			wsheet.addMergedRegion(new CellRangeAddress(row, row+2, col, col));
			col++;	
			
			cell = hrow.createCell(col);
			cell.setCellStyle(columnHeading);
			cell.setCellValue("COG/Unit");
	//		wsheet.addMergedRegion(new CellRangeAddress(row, row+2, col, col));
			col++;	
			
			cell = hrow.createCell(col);
			cell.setCellStyle(columnHeading);
			cell.setCellValue("No.of TE/TSO");
//			wsheet.addMergedRegion(new CellRangeAddress(row, row+2, col, col));
			col++;	
			
		
			
			cell = hrow.createCell(col);
			cell.setCellStyle(columnHeading);
			cell.setCellValue("Annual Sample Units");
		//	wsheet.addMergedRegion(new CellRangeAddress(2, row, col, col));
			col++;
			
			cell = hrow.createCell(col);
			cell.setCellStyle(columnHeading);
			cell.setCellValue("Current Warehouse Stock");
		//	wsheet.addMergedRegion(new CellRangeAddress(2, row, col, col));
			col++;
			
			cell = hrow.createCell(col);
			cell.setCellStyle(columnHeading);
			cell.setCellValue("Batch No");
		//	wsheet.addMergedRegion(new CellRangeAddress(2, row, col, col));
			col++;
			
			cell = hrow.createCell(col);
			cell.setCellStyle(columnHeading);
			cell.setCellValue("Expiry Date");
		//	wsheet.addMergedRegion(new CellRangeAddress(2, row, col, col));
			col++;
			

		
			row++;
			col=0;
			
			for(Annual_sample_plan_view_report_batchwise ps : list) {
			
				new_team = ps.getTeam();
				new_prod = ps.getSmp_prod_name();
				
				 if(!new_prod.equalsIgnoreCase(old_prod) && !old_prod.equalsIgnoreCase("old"))
				  {
				  
				  col=0; 
				 
				  hrow = (XSSFRow) wsheet.createRow(row);
				  
				  cell = hrow.createCell(col); 
				  cell.setCellStyle(totalstyle); 
				  col++;
				  
				  cell = hrow.createCell(col);
				  cell.setCellStyle(totalstyle);
				  col++;
				  
				  cell = hrow.createCell(col); 
				  cell.setCellStyle(totalstyle); 
				  col++;
				  
				  cell = hrow.createCell(col); 
				  cell.setCellStyle(totalstyle); 
				  col++;
				  
				  cell = hrow.createCell(col); 
				  cell.setCellStyle(totalstyle); 
				  col++;
				  
				  cell = hrow.createCell(col); 
				  cell.setCellStyle(totalstyle); 
				  col++;
				  
				  cell = hrow.createCell(col); 
				  cell.setCellStyle(totalstyle); 
				  col++;
				  
				  cell = hrow.createCell(col); 
				  cell.setCellStyle(totalstyle); 
				  col++;
				  
				  
				  cell = hrow.createCell(col); 
				  cell.setCellStyle(totalstyle);
				  //wsheet.addMergedRegion(new CellRangeAddress(row, row, col, col+14));
				  cell.setCellValue("Total "); 
				  col++;
				  
				  
				  cell = hrow.createCell(col); 
				  cell.setCellStyle(totalstyle2);
				  //wsheet.addMergedRegion(new CellRangeAddress(row, row, col, col+14));
				  cell.setCellValue(tot_sample_units.longValue()); 
				  col++;
				  
				  cell = hrow.createCell(col); 
				  cell.setCellStyle(totalstyle2);
				  //wsheet.addMergedRegion(new CellRangeAddress(row, row, col, col+14));
				  cell.setCellValue(curr_warehouse_stock.longValue()); 
				  col++;
				  
				  cell = hrow.createCell(col); 
				  cell.setCellStyle(totalstyle2);
				  //wsheet.addMergedRegion(new CellRangeAddress(row, row, col, col+14)); 
				  col++;
				  
				  cell = hrow.createCell(col); 
				  cell.setCellStyle(totalstyle2);
				  //wsheet.addMergedRegion(new CellRangeAddress(row, row, col, col+14)); 
				  col++;
				  
				  
				  curr_warehouse_stock = BigDecimal.ZERO;
				  tot_sample_units = BigDecimal.ZERO;
					row++;
					col=0;
				  }
				
				if(!new_team.equalsIgnoreCase(old_team) && !old_team.equalsIgnoreCase("old")) {
					
			
					
					 grnd_tot_costDec19 = BigDecimal.ZERO;
					 grnd_tot_costJan19 = BigDecimal.ZERO;
					 grnd_tot_costFeb19 = BigDecimal.ZERO;
					 grnd_tot_costMar19 = BigDecimal.ZERO;
					 grnd_tot_costApr19 = BigDecimal.ZERO;
					 grnd_tot_costMay19 = BigDecimal.ZERO;
					 grnd_tot_costJun19 = BigDecimal.ZERO;
					 grnd_tot_costJul19 = BigDecimal.ZERO;
					 grnd_tot_costAug19 = BigDecimal.ZERO;
					 grnd_tot_costSep19 = BigDecimal.ZERO;
					 grnd_tot_costOct19 = BigDecimal.ZERO;
					 grnd_tot_costNov19 = BigDecimal.ZERO;
					 grnd_tot_of_grd_totcost = BigDecimal.ZERO;
					
					
					
					row = 0;
					col = 0;
					
					wsheet = wwbook.createSheet(ps.getTeam());			//adding new sheet
					wsheet.createFreezePane(0, 3);						//added
					hrow =  (XSSFRow) wsheet.createRow(row);
					
					cell = hrow.createCell(col);
					cell.setCellStyle(sheetHeading);
					cell.setCellValue("Annual Sample Plan with Batchwise Stock Report");
					wsheet.addMergedRegion(new CellRangeAddress(row, row, col, col + (heading1.length)-1));
					row++;
					
					
					hrow =  (XSSFRow) wsheet.createRow(row);
					cell = hrow.createCell(col);
					cell.setCellStyle(sheetHeading);
					cell.setCellValue(companyname);
					wsheet.addMergedRegion(new CellRangeAddress(row, row, col, col + (heading1.length)-1));
					row++;
					
					
					hrow =  (XSSFRow) wsheet.createRow(row);
					
					cell = hrow.createCell(col);
					cell.setCellStyle(columnHeading);
					cell.setCellValue("BU");
	//				wsheet.addMergedRegion(new CellRangeAddress(row, row+2, col, col));
					col++;
									
					cell = hrow.createCell(col);
					cell.setCellStyle(columnHeading);
					cell.setCellValue("Team Name");
		//			wsheet.addMergedRegion(new CellRangeAddress(row, row+2, col, col));
					col++;	
					
					cell = hrow.createCell(col);
					cell.setCellStyle(columnHeading);
					cell.setCellValue("Product Code");
		//			wsheet.addMergedRegion(new CellRangeAddress(row, row+2, col, col));
					col++;	
					
					cell = hrow.createCell(col);
					cell.setCellStyle(columnHeading);
					cell.setCellValue("Brand");
	//				wsheet.addMergedRegion(new CellRangeAddress(row, row+2, col, col));
					col++;	
					
					cell = hrow.createCell(col);
					cell.setCellStyle(columnHeading);
					cell.setCellValue("Product Name");
	//				wsheet.addMergedRegion(new CellRangeAddress(row, row+2, col, col));
					col++;	
					
					cell = hrow.createCell(col);
					cell.setCellStyle(columnHeading);
					cell.setCellValue("Presence in India Market");
	//				wsheet.addMergedRegion(new CellRangeAddress(row, row+2, col, col));
					col++;	
					
					cell = hrow.createCell(col);
					cell.setCellStyle(columnHeading);
					cell.setCellValue("Type of samples");
	//				wsheet.addMergedRegion(new CellRangeAddress(row, row+2, col, col));
					col++;	
					
					cell = hrow.createCell(col);
					cell.setCellStyle(columnHeading);
					cell.setCellValue("COG/Unit");
	//				wsheet.addMergedRegion(new CellRangeAddress(row, row+2, col, col));
					col++;	
					
					cell = hrow.createCell(col);
					cell.setCellStyle(columnHeading);
					cell.setCellValue("No.of TE/TSO");
//					wsheet.addMergedRegion(new CellRangeAddress(row, row+2, col, col));
					col++;	
					
					cell = hrow.createCell(col);
					cell.setCellStyle(columnHeading);
					cell.setCellValue("Annual Sample Units");
				//	wsheet.addMergedRegion(new CellRangeAddress(2, row, col, col));
					col++;
					
					cell = hrow.createCell(col);
					cell.setCellStyle(columnHeading);
					cell.setCellValue("Current warehouse stocks");
				//	wsheet.addMergedRegion(new CellRangeAddress(2, row, col, col));
					col++;
					
					cell = hrow.createCell(col);
					cell.setCellStyle(columnHeading);
					cell.setCellValue("Batch No");
				//	wsheet.addMergedRegion(new CellRangeAddress(2, row, col, col));
					col++;
					
					cell = hrow.createCell(col);
					cell.setCellStyle(columnHeading);
					cell.setCellValue("Expiry Date");
				//	wsheet.addMergedRegion(new CellRangeAddress(2, row, col, col));
					col++;
					
					row++;
					col=0;
				}
			
			

			
				hrow =  (XSSFRow) wsheet.createRow(row);		//blank row
				cell = hrow.createCell(col);
				//	cell.setCellStyle(columnHeading);
					cell.setCellValue(" ");
					col++;
					
					cell = hrow.createCell(col);
					//	cell.setCellStyle(columnHeading);
					cell.setCellValue(" ");
					col++;
					
					cell = hrow.createCell(col);
					//	cell.setCellStyle(columnHeading);
					cell.setCellValue(" ");
					col++;
					
					cell = hrow.createCell(col);
					//	cell.setCellStyle(columnHeading);
					cell.setCellValue(" ");
					col++;
								
					cell = hrow.createCell(col);
					//	cell.setCellStyle(columnHeading);
					cell.setCellValue(" ");
					col++;
					
					cell = hrow.createCell(col);
					//	cell.setCellStyle(columnHeading);
					cell.setCellValue(" ");
					col++;
								
					cell = hrow.createCell(col);
					//	cell.setCellStyle(columnHeading);
					cell.setCellValue(" ");
					col++;
					
					cell = hrow.createCell(col);
					//	cell.setCellStyle(columnHeading);
					cell.setCellValue(" ");
					col++;
								
					cell = hrow.createCell(col);
					//	cell.setCellStyle(columnHeading);
					cell.setCellValue(" ");
					col++;
					
					for(int i = col;i<13;i++) {
					cell = hrow.createCell(col);
					cell.setCellStyle(rightandleftborder);
					//wsheet.addMergedRegion(new CellRangeAddress(row, row, col, col+14));		
					cell.setCellValue(" ");
					col++;
					}
					
					row++;
					col=0;
				
				hrow =  (XSSFRow) wsheet.createRow(row);		//row 1
				
				tot_unitDec19 = ps.getNo_of_te().multiply(ps.getDec_19());			
				tot_of_pso = tot_of_pso.add(ps.getDec_19());

				tot_unitJan19 = ps.getNo_of_te().multiply(ps.getJan_20());
				tot_of_pso = tot_of_pso.add(ps.getJan_20());

				tot_unitFeb19 = ps.getNo_of_te().multiply(ps.getFeb_20());
				tot_of_pso = tot_of_pso.add(ps.getFeb_20());

				tot_unitMar19 = ps.getNo_of_te().multiply(ps.getMar_20());
				tot_of_pso = tot_of_pso.add(ps.getMar_20());

				tot_unitApr19 = ps.getNo_of_te().multiply(ps.getApr_20());
				tot_of_pso = tot_of_pso.add(ps.getApr_20());

				tot_unitMay19 = ps.getNo_of_te().multiply(ps.getMay_20());
				tot_of_pso = tot_of_pso.add(ps.getMay_20());

				tot_unitJun19 = ps.getNo_of_te().multiply(ps.getJun_20());
				tot_of_pso = tot_of_pso.add(ps.getJun_20());

				tot_unitJul19 = ps.getNo_of_te().multiply(ps.getJul_20());
				tot_of_pso = tot_of_pso.add(ps.getJul_20());

				tot_unitAug19 = ps.getNo_of_te().multiply(ps.getAug_20());
				tot_of_pso = tot_of_pso.add(ps.getAug_20());

				tot_unitSep19 = ps.getNo_of_te().multiply(ps.getSep_20());
				tot_of_pso = tot_of_pso.add(ps.getSep_20());

				tot_unitOct19 = ps.getNo_of_te().multiply(ps.getOct_20());
				tot_of_pso = tot_of_pso.add(ps.getOct_20());

				tot_unitNov19 = ps.getNo_of_te().multiply(ps.getNov_20());
				tot_of_pso = tot_of_pso.add(ps.getNov_20());

				
				
				//total
				tot_costDec19 = tot_unitDec19.multiply(ps.getCog_rate_perunit());
				tot_of_totunit = tot_of_totunit.add(tot_unitDec19);

				tot_of_totunit = tot_of_totunit.add(tot_unitJan19);
				tot_costJan19 = tot_unitJan19.multiply(ps.getCog_rate_perunit());

				tot_of_totunit = tot_of_totunit.add(tot_unitFeb19);
				tot_costFeb19 = tot_unitFeb19.multiply(ps.getCog_rate_perunit());

				tot_of_totunit = tot_of_totunit.add(tot_unitMar19);
				tot_costMar19 = tot_unitMar19.multiply(ps.getCog_rate_perunit());

				tot_of_totunit = tot_of_totunit.add(tot_unitApr19);
				tot_costApr19 = tot_unitApr19.multiply(ps.getCog_rate_perunit());

				tot_of_totunit = tot_of_totunit.add(tot_unitMay19);
				tot_costMay19 = tot_unitMay19.multiply(ps.getCog_rate_perunit());

				tot_of_totunit = tot_of_totunit.add(tot_unitJun19);
				tot_costJun19 = tot_unitJun19.multiply(ps.getCog_rate_perunit());

				tot_of_totunit = tot_of_totunit.add(tot_unitJul19);
				tot_costJul19 = tot_unitJul19.multiply(ps.getCog_rate_perunit());

				tot_of_totunit = tot_of_totunit.add(tot_unitAug19);
				tot_costAug19 = tot_unitAug19.multiply(ps.getCog_rate_perunit());

				tot_of_totunit = tot_of_totunit.add(tot_unitSep19);
				tot_costSep19 = tot_unitSep19.multiply(ps.getCog_rate_perunit());

				tot_of_totunit = tot_of_totunit.add(tot_unitOct19);
				tot_costOct19 = tot_unitOct19.multiply(ps.getCog_rate_perunit());

				tot_of_totunit = tot_of_totunit.add(tot_unitNov19);
				tot_costNov19 = tot_unitNov19.multiply(ps.getCog_rate_perunit());
				
			if(!new_team.equalsIgnoreCase(old_team) || !new_prod.equalsIgnoreCase(old_prod))
			{
				
				
					
				cell = hrow.createCell(col);
			//	cell.setCellStyle(columnHeading);
				cell.setCellValue(ps.getBu());
				col++;
				
				cell = hrow.createCell(col);
				//	cell.setCellStyle(columnHeading);
				cell.setCellValue(ps.getTeam());
				col++;
				
				cell = hrow.createCell(col);
				//	cell.setCellStyle(columnHeading);
				cell.setCellValue(ps.getProduct_code());
				col++;
				
				cell = hrow.createCell(col);
				//	cell.setCellStyle(columnHeading);
				cell.setCellValue(ps.getSa_group_name());
				col++;
							
				cell = hrow.createCell(col);
				//	cell.setCellStyle(columnHeading);
				cell.setCellValue(ps.getSmp_prod_name());
				col++;
				
				cell = hrow.createCell(col);
				//	cell.setCellStyle(columnHeading);
				cell.setCellValue(ps.getPre_in_market());
				col++;
				
				cell = hrow.createCell(col);
				//	cell.setCellStyle(columnHeading);
				cell.setCellValue(ps.getSgprmdet_disp_nm());
				col++;
				
				cell = hrow.createCell(col);
				cell.setCellStyle(decimalstyle3);
				cell.setCellValue(ps.getCog_rate_perunit().doubleValue());
				col++;
				
				cell = hrow.createCell(col);
				cell.setCellStyle(decimalstyle4);
				//wsheet.addMergedRegion(new CellRangeAddress(row, row, col, col+14));		
				cell.setCellValue(ps.getNo_of_te().longValue());
				col++;
				
				cell = hrow.createCell(col); 
				cell.setCellStyle(rightandleftborder);
				cell.setCellValue(tot_of_totunit.longValue());
				tot_sample_units = tot_of_totunit;
				col++;
				
			}
			else {
				cell = hrow.createCell(col);
			//	cell.setCellStyle(columnHeading);
				cell.setCellValue(" ");
				col++;
				
				cell = hrow.createCell(col);
				//	cell.setCellStyle(columnHeading);
				cell.setCellValue(" ");
				col++;
				
				cell = hrow.createCell(col);
				//	cell.setCellStyle(columnHeading);
				cell.setCellValue(" ");
				col++;
				
				cell = hrow.createCell(col);
				//	cell.setCellStyle(columnHeading);
				cell.setCellValue(" ");
				col++;
							
				cell = hrow.createCell(col);
				//	cell.setCellStyle(columnHeading);
				cell.setCellValue(" ");
				col++;
				
				cell = hrow.createCell(col);
				//	cell.setCellStyle(columnHeading);
				cell.setCellValue(" ");
				col++;
				
				cell = hrow.createCell(col);
				//	cell.setCellStyle(columnHeading);
				cell.setCellValue(" ");
				col++;
				
				cell = hrow.createCell(col);
				cell.setCellStyle(decimalstyle3);
				cell.setCellValue(" ");
				col++;
				
				cell = hrow.createCell(col);
				cell.setCellStyle(decimalstyle4);
				//wsheet.addMergedRegion(new CellRangeAddress(row, row, col, col+14));		
				cell.setCellValue(" ");
				col++;
				
				cell = hrow.createCell(col);
				cell.setCellStyle(rightandleftborder);
				//wsheet.addMergedRegion(new CellRangeAddress(row, row, col, col+14));		
				cell.setCellValue(" ");
				col++;
				
			}

				
				cell = hrow.createCell(col); 
				cell.setCellStyle(rightandleftborder);
				cell.setCellValue(ps.getBstock().longValue());
				curr_warehouse_stock = curr_warehouse_stock.add(ps.getBstock());
				col++;
				
				cell = hrow.createCell(col); 
				cell.setCellStyle(rightandleftborder);
				cell.setCellValue(ps.getBatch_no());
				col++;
				
				cell = hrow.createCell(col);
				cell.setCellStyle(rightandleftborder);
				cell.setCellValue(ps.getExpiry_dt());
				col++;
				

				
				 
				 
				
				
				
				 tot_unitDec19 =  BigDecimal.ZERO;
				 tot_unitJan19 =  BigDecimal.ZERO;
				 tot_unitFeb19 =  BigDecimal.ZERO;
				 tot_unitMar19 =  BigDecimal.ZERO;
				 tot_unitApr19 =  BigDecimal.ZERO;
				 tot_unitMay19 =  BigDecimal.ZERO;
				 tot_unitJun19 =  BigDecimal.ZERO;
				 tot_unitJul19 =  BigDecimal.ZERO;
				 tot_unitAug19 =  BigDecimal.ZERO;
				 tot_unitSep19 =  BigDecimal.ZERO;
				 tot_unitOct19 =  BigDecimal.ZERO;
				 tot_unitNov19 =  BigDecimal.ZERO;
				 tot_of_totunit=  BigDecimal.ZERO;
				 tot_of_pso    =  BigDecimal.ZERO;
				
				 
				 tot_costDec19 = BigDecimal.ZERO;
				 tot_costJan19 = BigDecimal.ZERO;
				 tot_costFeb19 = BigDecimal.ZERO;
				 tot_costMar19 = BigDecimal.ZERO;
				 tot_costApr19 = BigDecimal.ZERO;
				 tot_costMay19 = BigDecimal.ZERO;
				 tot_costJun19 = BigDecimal.ZERO;
				 tot_costJul19 = BigDecimal.ZERO;
				 tot_costAug19 = BigDecimal.ZERO;
				 tot_costSep19 = BigDecimal.ZERO;
				 tot_costOct19 = BigDecimal.ZERO;
				 tot_costNov19 = BigDecimal.ZERO;
				 tot_of_totcost= BigDecimal.ZERO;
				 
				 
				 
					row++;
					col=0;
					old_team = new_team;
					old_prod = new_prod;
				 
			}
			
			  hrow = (XSSFRow) wsheet.createRow(row);
			  
			  cell = hrow.createCell(col); 
			  cell.setCellStyle(totalstyle); 
			  col++;
			  
			  cell = hrow.createCell(col);
			  cell.setCellStyle(totalstyle);
			  col++;
			  
			  cell = hrow.createCell(col); 
			  cell.setCellStyle(totalstyle); 
			  col++;
			  
			  cell = hrow.createCell(col); 
			  cell.setCellStyle(totalstyle); 
			  col++;
			  
			  cell = hrow.createCell(col); 
			  cell.setCellStyle(totalstyle); 
			  col++;
			  
			  cell = hrow.createCell(col); 
			  cell.setCellStyle(totalstyle); 
			  col++;
			  
			  cell = hrow.createCell(col); 
			  cell.setCellStyle(totalstyle); 
			  col++;
			  
			  cell = hrow.createCell(col); 
			  cell.setCellStyle(totalstyle); 
			  col++;
			  
			  
			  cell = hrow.createCell(col); 
			  cell.setCellStyle(totalstyle);
			  //wsheet.addMergedRegion(new CellRangeAddress(row, row, col, col+14));
			  cell.setCellValue("Total "); 
			  col++;
			  
			  
			  cell = hrow.createCell(col); 
			  cell.setCellStyle(totalstyle2);
			  //wsheet.addMergedRegion(new CellRangeAddress(row, row, col, col+14));
			  cell.setCellValue(tot_sample_units.longValue()); 
			  col++;
			  
			  cell = hrow.createCell(col); 
			  cell.setCellStyle(totalstyle2);
			  //wsheet.addMergedRegion(new CellRangeAddress(row, row, col, col+14));
			  cell.setCellValue(curr_warehouse_stock.longValue()); 
			  col++;
			  
			  cell = hrow.createCell(col); 
			  cell.setCellStyle(totalstyle2);
			  //wsheet.addMergedRegion(new CellRangeAddress(row, row, col, col+14)); 
			  col++;
			  
			  cell = hrow.createCell(col); 
			  cell.setCellStyle(totalstyle2);
			  //wsheet.addMergedRegion(new CellRangeAddress(row, row, col, col+14)); 
			  col++;
			  
			  
			  curr_warehouse_stock = BigDecimal.ZERO;
			
			row++;
			col=0;
			
//			 hrow = (XSSFRow) wsheet.createRow(row); // blank row
//				
//				
//			 for(int i = col;i<9;i++) {
//					cell = hrow.createCell(col); 
//					cell.setCellStyle(bottomline);
//					col++;
//					}
//				
//				
//				for(int i = col;i<13;i++) {
//				cell = hrow.createCell(col); 
//				cell.setCellStyle(decimalstyle2);
//				col++;
//				}

			FileOutputStream fileOut = new FileOutputStream(path.toString());
			wwbook.write(fileOut);
			fileOut.close();
			
			System.out.println("Excel Created");
			
		}
		catch (Exception e) {
			// TODO: handle exception
			System.out.println("Error Occurred :" +e.getMessage());// uncomment asneeded --;
		}
		
		return filename;
	}

	//Itemwise Report
	@Override
	public String generate_annual_sample_plan_view_Itemwise_report(List<Annual_sample_plan_view_report_itemwise> list,
			String companyname,String empId) throws Exception {
		//TODO Auto-generated method stub
		
		Workbook wwbook = null;
		File ff = null;
		String filename = null;
		StringBuffer path = null;

		try {

			long l = new Date().getTime();
			filename = "Annual_sample_plan_view_itemwise_report" + l + ".xlsx";
			String filePath = MedicoConstants.REPORT_FILE_PATH;

			ff = new File(filePath);
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
			
			int col = 0;
			int row = 0;
			
			wwbook = new XSSFWorkbook();
		

			ApachePoiExcelFormat xlsxExcelFormat = new ApachePoiExcelFormat();
			
			CellStyle sheetHeading = xlsxExcelFormat.SheetHeading(wwbook);
			CellStyle columnHeading = xlsxExcelFormat.columnHeading(wwbook);
			columnHeading.setAlignment(HorizontalAlignment.CENTER);
			columnHeading.setVerticalAlignment(VerticalAlignment.CENTER);
			
			CellStyle leftAlign = xlsxExcelFormat.dataLeftAlign(wwbook);
			
 			CellStyle columnHeadingBlue = xlsxExcelFormat.columnSubHeadingBlue(wwbook);
 			
 			
			CellStyle leftAlignB = xlsxExcelFormat.dataBoldLeftAlign(wwbook);
			
			
			
			Font font = wwbook.createFont();
			font.setFontHeightInPoints((short) 11);
			font.setFontName("ARIAL");
			font.setBold(true);
			CellStyle decimalstyle = wwbook.createCellStyle();
		//	decimalstyle.setDataFormat(wwbook.createDataFormat().getFormat("0.00"));
			decimalstyle.setAlignment(HorizontalAlignment.RIGHT);
			decimalstyle.setFont(font);
			decimalstyle.setBorderLeft(BorderStyle.THIN);
			decimalstyle.setBorderRight(BorderStyle.THIN);
			
			
			Font font2 = wwbook.createFont();
			font2.setFontHeightInPoints((short) 11);
			font2.setFontName("ARIAL");
			CellStyle rightandleftborder = wwbook.createCellStyle();
			rightandleftborder.setAlignment(HorizontalAlignment.RIGHT);
			rightandleftborder.setBorderLeft(BorderStyle.THIN);
			rightandleftborder.setBorderRight(BorderStyle.THIN);
			rightandleftborder.setFont(font2);
			
			Font font3 = wwbook.createFont();
			font3.setFontHeightInPoints((short) 11);
			font3.setFontName("ARIAL");
			font3.setBold(true);
			CellStyle decimalstyle2 = wwbook.createCellStyle();
		//	decimalstyle2.setDataFormat(wwbook.createDataFormat().getFormat("0.00"));
			decimalstyle2.setAlignment(HorizontalAlignment.RIGHT);
			decimalstyle2.setFont(font);
			decimalstyle2.setBorderLeft(BorderStyle.THIN);
			decimalstyle2.setBorderRight(BorderStyle.THIN);
			decimalstyle2.setBorderBottom(BorderStyle.THIN);
			
			
			Font font4 = wwbook.createFont();
			font4.setFontHeightInPoints((short) 11);
			font4.setFontName("ARIAL");
			font4.setBold(true);
			CellStyle decimalstyle3 = wwbook.createCellStyle();
			decimalstyle3.setDataFormat(wwbook.createDataFormat().getFormat("0.00"));
			decimalstyle3.setAlignment(HorizontalAlignment.RIGHT);
			decimalstyle3.setFont(font4);
			
			
			Font font5 = wwbook.createFont();
			font5.setFontHeightInPoints((short) 11);
			font5.setFontName("ARIAL");
			font5.setBold(true);
			CellStyle decimalstyle4 = wwbook.createCellStyle();
			//decimalstyle4.setDataFormat(wwbook.createDataFormat().getFormat("0.00"));
			decimalstyle4.setAlignment(HorizontalAlignment.RIGHT);
			decimalstyle4.setFont(font5);
			
			
			Font font6 = wwbook.createFont();
			font6.setFontHeightInPoints((short) 11);
			font6.setFontName("ARIAL");
			font6.setBold(true);
			CellStyle decimalstyle5 = wwbook.createCellStyle();
			//decimalstyle5.setDataFormat(wwbook.createDataFormat().getFormat("0.00"));
			decimalstyle5.setAlignment(HorizontalAlignment.RIGHT);
			decimalstyle5.setFont(font6);
		//	decimalstyle5.setBorderLeft(BorderStyle.THIN);
		//	decimalstyle5.setBorderRight(BorderStyle.THIN);
			decimalstyle5.setBorderBottom(BorderStyle.THIN);
			
		
			CellStyle bottomline = wwbook.createCellStyle();
		//	decimalstyle2.setDataFormat(wwbook.createDataFormat().getFormat("0.00"));
			bottomline.setAlignment(HorizontalAlignment.RIGHT);
			bottomline.setBorderBottom(BorderStyle.THIN);
			
			String[] heading1= {"BU","Team Name","Product Code","Brand","Product Name","Presence in India Market","Types of Samples","COG/Units","No.of TE/TSO"
					,"Annual Sample Units","Stock"};
			
		//	String[]heading2= {"Dec'18","Jan'19","Feb'19","Mar'19","Apr'19","May'19","Jun'19","Jul'19","Aug'19","Sep'19","Oct'19","Nov'19","Dec'19"};
			
			Sheet wsheet = wwbook.createSheet(list.get(0).getTeam());
			wsheet.createFreezePane(0, 3);								//added
			
			XSSFRow hrow = null;
			XSSFCell cell = null;
			
			BigDecimal tot_unitDec19 = new BigDecimal(0);
			BigDecimal tot_unitJan19 = new BigDecimal(0);
			BigDecimal tot_unitFeb19 = new BigDecimal(0);
			BigDecimal tot_unitMar19 = new BigDecimal(0);
			BigDecimal tot_unitApr19 = new BigDecimal(0);
			BigDecimal tot_unitMay19 = new BigDecimal(0);
			BigDecimal tot_unitJun19 = new BigDecimal(0);
			BigDecimal tot_unitJul19 = new BigDecimal(0);
			BigDecimal tot_unitAug19 = new BigDecimal(0);
			BigDecimal tot_unitSep19 = new BigDecimal(0);
			BigDecimal tot_unitOct19 = new BigDecimal(0);
			BigDecimal tot_unitNov19 = new BigDecimal(0);		
			BigDecimal tot_of_totunit = new BigDecimal(0);
			
			BigDecimal tot_of_pso = new BigDecimal(0);
			
			BigDecimal tot_costDec19 = new BigDecimal(0);
			BigDecimal tot_costJan19 = new BigDecimal(0);
			BigDecimal tot_costFeb19 = new BigDecimal(0);
			BigDecimal tot_costMar19 = new BigDecimal(0);
			BigDecimal tot_costApr19 = new BigDecimal(0);
			BigDecimal tot_costMay19 = new BigDecimal(0);
			BigDecimal tot_costJun19 = new BigDecimal(0);
			BigDecimal tot_costJul19 = new BigDecimal(0);
			BigDecimal tot_costAug19 = new BigDecimal(0);
			BigDecimal tot_costSep19 = new BigDecimal(0);
			BigDecimal tot_costOct19 = new BigDecimal(0);
			BigDecimal tot_costNov19 = new BigDecimal(0);	
			BigDecimal tot_of_totcost = new BigDecimal(0);	
			
			BigDecimal grnd_tot_costDec19 = new BigDecimal(0);
			BigDecimal grnd_tot_costJan19 = new BigDecimal(0);
			BigDecimal grnd_tot_costFeb19 = new BigDecimal(0);
			BigDecimal grnd_tot_costMar19 = new BigDecimal(0);
			BigDecimal grnd_tot_costApr19 = new BigDecimal(0);
			BigDecimal grnd_tot_costMay19 = new BigDecimal(0);
			BigDecimal grnd_tot_costJun19 = new BigDecimal(0);
			BigDecimal grnd_tot_costJul19 = new BigDecimal(0);
			BigDecimal grnd_tot_costAug19 = new BigDecimal(0);
			BigDecimal grnd_tot_costSep19 = new BigDecimal(0);
			BigDecimal grnd_tot_costOct19 = new BigDecimal(0);
			BigDecimal grnd_tot_costNov19 = new BigDecimal(0);	
			BigDecimal grnd_tot_of_grd_totcost = new BigDecimal(0);	
			
			String old_team = "old";
			String new_team ="";
			
			String old_prod = "old";
			String new_prod = "";
			
			hrow =  (XSSFRow) wsheet.createRow(row);
			cell = hrow.createCell(col);
			cell.setCellStyle(sheetHeading);
			cell.setCellValue("Annual Sample Plan with Itemwise Stock Report");
			wsheet.addMergedRegion(new CellRangeAddress(row, row, col, col + (heading1.length)-1));
			row++;
			
			
			hrow =  (XSSFRow) wsheet.createRow(row);
			cell = hrow.createCell(col);
			cell.setCellStyle(sheetHeading);
			cell.setCellValue(companyname);
			wsheet.addMergedRegion(new CellRangeAddress(row, row, col, col + (heading1.length)-1));
			row++;
			
			
			hrow =  (XSSFRow) wsheet.createRow(row);
			
			cell = hrow.createCell(col);
			cell.setCellStyle(columnHeading);
			cell.setCellValue("BU");
	//		wsheet.addMergedRegion(new CellRangeAddress(row, row+2, col, col));
			col++;
							
			cell = hrow.createCell(col);
			cell.setCellStyle(columnHeading);
			cell.setCellValue("Team Name");
	//		wsheet.addMergedRegion(new CellRangeAddress(row, row+2, col, col));
			col++;	
			
			cell = hrow.createCell(col);
			cell.setCellStyle(columnHeading);
			cell.setCellValue("Product Code");
	//		wsheet.addMergedRegion(new CellRangeAddress(row, row+2, col, col));
			col++;	
			
			cell = hrow.createCell(col);
			cell.setCellStyle(columnHeading);
			cell.setCellValue("Brand");
//			wsheet.addMergedRegion(new CellRangeAddress(row, row+2, col, col));
			col++;	
			
			cell = hrow.createCell(col);
			cell.setCellStyle(columnHeading);
			cell.setCellValue("Product Name");
//			wsheet.addMergedRegion(new CellRangeAddress(row, row+2, col, col));
			col++;	
			
			cell = hrow.createCell(col);
			cell.setCellStyle(columnHeading);
			cell.setCellValue("Presence in India Market");
//			wsheet.addMergedRegion(new CellRangeAddress(row, row+2, col, col));
			col++;	
			
			cell = hrow.createCell(col);
			cell.setCellStyle(columnHeading);
			cell.setCellValue("Type of samples");
//			wsheet.addMergedRegion(new CellRangeAddress(row, row+2, col, col));
			col++;	
			
			cell = hrow.createCell(col);
			cell.setCellStyle(columnHeading);
			cell.setCellValue("COG/Unit");
	//		wsheet.addMergedRegion(new CellRangeAddress(row, row+2, col, col));
			col++;	
			
			cell = hrow.createCell(col);
			cell.setCellStyle(columnHeading);
			cell.setCellValue("No.of TE/TSO");
//			wsheet.addMergedRegion(new CellRangeAddress(row, row+2, col, col));
			col++;	
			
		
		
			
			cell = hrow.createCell(col);
			cell.setCellStyle(columnHeading);
			cell.setCellValue("Annual Sample Units");
		//	wsheet.addMergedRegion(new CellRangeAddress(2, row, col, col));
			col++;
			
			cell = hrow.createCell(col);
			cell.setCellStyle(columnHeading);
			cell.setCellValue("Current Warehouse Stocks");
		//	wsheet.addMergedRegion(new CellRangeAddress(2, row, col, col));
			col++;
			
		
			

		
			row++;
			col=0;
			
			for(Annual_sample_plan_view_report_itemwise ps : list) {
			
				new_team = ps.getTeam();
				new_prod = ps.getSmp_prod_name();
				
				if(!new_team.equalsIgnoreCase(old_team) && !old_team.equalsIgnoreCase("old")) {
				
					
					 grnd_tot_costDec19 = BigDecimal.ZERO;
					 grnd_tot_costJan19 = BigDecimal.ZERO;
					 grnd_tot_costFeb19 = BigDecimal.ZERO;
					 grnd_tot_costMar19 = BigDecimal.ZERO;
					 grnd_tot_costApr19 = BigDecimal.ZERO;
					 grnd_tot_costMay19 = BigDecimal.ZERO;
					 grnd_tot_costJun19 = BigDecimal.ZERO;
					 grnd_tot_costJul19 = BigDecimal.ZERO;
					 grnd_tot_costAug19 = BigDecimal.ZERO;
					 grnd_tot_costSep19 = BigDecimal.ZERO;
					 grnd_tot_costOct19 = BigDecimal.ZERO;
					 grnd_tot_costNov19 = BigDecimal.ZERO;
					 grnd_tot_of_grd_totcost = BigDecimal.ZERO;
					
					 hrow = (XSSFRow) wsheet.createRow(row); // blank row

					 for(int i = col;i<9;i++) {
							cell = hrow.createCell(col); 
							cell.setCellStyle(bottomline);
							col++;
							}
					 
						for(int i = col;i<11;i++) {
							cell = hrow.createCell(col); 
							cell.setCellStyle(decimalstyle2);
							col++;
							}
					
					row = 0;
					col = 0;
					
					wsheet = wwbook.createSheet(ps.getTeam());			//adding new sheet
					wsheet.createFreezePane(0, 3);						//added
					hrow =  (XSSFRow) wsheet.createRow(row);
					
					cell = hrow.createCell(col);
					cell.setCellStyle(sheetHeading);
					cell.setCellValue("Annual Sample Plan with Itemwise Stock Report");
					wsheet.addMergedRegion(new CellRangeAddress(row, row, col, col + (heading1.length)-1));
					row++;
					
					
					hrow =  (XSSFRow) wsheet.createRow(row);
					cell = hrow.createCell(col);
					cell.setCellStyle(sheetHeading);
					cell.setCellValue(companyname);
					wsheet.addMergedRegion(new CellRangeAddress(row, row, col, col + (heading1.length)-1));
					row++;
					
					
					hrow =  (XSSFRow) wsheet.createRow(row);
					
					cell = hrow.createCell(col);
					cell.setCellStyle(columnHeading);
					cell.setCellValue("BU");
				//	wsheet.addMergedRegion(new CellRangeAddress(row, row+2, col, col));
					col++;
									
					cell = hrow.createCell(col);
					cell.setCellStyle(columnHeading);
					cell.setCellValue("Team Name");
			//		wsheet.addMergedRegion(new CellRangeAddress(row, row+2, col, col));
					col++;	
					
					cell = hrow.createCell(col);
					cell.setCellStyle(columnHeading);
					cell.setCellValue("Product Code");
				//	wsheet.addMergedRegion(new CellRangeAddress(row, row+2, col, col));
					col++;	
					
					cell = hrow.createCell(col);
					cell.setCellStyle(columnHeading);
					cell.setCellValue("Brand");
			//		wsheet.addMergedRegion(new CellRangeAddress(row, row+2, col, col));
					col++;	
					
					cell = hrow.createCell(col);
					cell.setCellStyle(columnHeading);
					cell.setCellValue("Product Name");
			//		wsheet.addMergedRegion(new CellRangeAddress(row, row+2, col, col));
					col++;	
					
					cell = hrow.createCell(col);
					cell.setCellStyle(columnHeading);
					cell.setCellValue("Presence in India Market");
			//		wsheet.addMergedRegion(new CellRangeAddress(row, row+2, col, col));
					col++;	
					
					cell = hrow.createCell(col);
					cell.setCellStyle(columnHeading);
					cell.setCellValue("Type of samples");
			//		wsheet.addMergedRegion(new CellRangeAddress(row, row+2, col, col));
					col++;	
					
					cell = hrow.createCell(col);
					cell.setCellStyle(columnHeading);
					cell.setCellValue("COG/Unit");
			//		wsheet.addMergedRegion(new CellRangeAddress(row, row+2, col, col));
					col++;	
					
					cell = hrow.createCell(col);
					cell.setCellStyle(columnHeading);
					cell.setCellValue("No.of TE/TSO");
		//			wsheet.addMergedRegion(new CellRangeAddress(row, row+2, col, col));
					col++;	
					

					
					cell = hrow.createCell(col);
					cell.setCellStyle(columnHeading);
					cell.setCellValue("Annual Sample Units");
				//	wsheet.addMergedRegion(new CellRangeAddress(2, row, col, col));
					col++;
					
					cell = hrow.createCell(col);
					cell.setCellStyle(columnHeading);
					cell.setCellValue("Current WareHouse Stocks");
				//	wsheet.addMergedRegion(new CellRangeAddress(2, row, col, col));
					col++;
					
				
					row++;
					col=0;
				}
			
			

				
				hrow =  (XSSFRow) wsheet.createRow(row);		//blank row
				cell = hrow.createCell(col);
				//	cell.setCellStyle(columnHeading);
					cell.setCellValue(" ");
					col++;
					
					cell = hrow.createCell(col);
					//	cell.setCellStyle(columnHeading);
					cell.setCellValue(" ");
					col++;
					
					cell = hrow.createCell(col);
					//	cell.setCellStyle(columnHeading);
					cell.setCellValue(" ");
					col++;
					
					cell = hrow.createCell(col);
					//	cell.setCellStyle(columnHeading);
					cell.setCellValue(" ");
					col++;
								
					cell = hrow.createCell(col);
					//	cell.setCellStyle(columnHeading);
					cell.setCellValue(" ");
					col++;
					
					cell = hrow.createCell(col);
					//	cell.setCellStyle(columnHeading);
					cell.setCellValue(" ");
					col++;
								
					cell = hrow.createCell(col);
					//	cell.setCellStyle(columnHeading);
					cell.setCellValue(" ");
					col++;
					
					cell = hrow.createCell(col);
					//	cell.setCellStyle(columnHeading);
					cell.setCellValue(" ");
					col++;
								
					cell = hrow.createCell(col);
					//	cell.setCellStyle(columnHeading);
					cell.setCellValue(" ");
					col++;
					
					for(int i = col;i<11;i++) {
					cell = hrow.createCell(col);
					cell.setCellStyle(rightandleftborder);
					//wsheet.addMergedRegion(new CellRangeAddress(row, row, col, col+14));		
					cell.setCellValue(" ");
					col++;
					}
					
					row++;
					col=0;
				
				hrow =  (XSSFRow) wsheet.createRow(row);		//row 1
				
			if(!new_team.equalsIgnoreCase(old_team) || !new_prod.equalsIgnoreCase(old_prod))
			{
				
				
					
				cell = hrow.createCell(col);
			//	cell.setCellStyle(columnHeading);
				cell.setCellValue(ps.getBu());
				col++;
				
				cell = hrow.createCell(col);
				//	cell.setCellStyle(columnHeading);
				cell.setCellValue(ps.getTeam());
				col++;
				
				cell = hrow.createCell(col);
				//	cell.setCellStyle(columnHeading);
				cell.setCellValue(ps.getProduct_code());
				col++;
				
				cell = hrow.createCell(col);
				//	cell.setCellStyle(columnHeading);
				cell.setCellValue(ps.getSa_group_name());
				col++;
							
				cell = hrow.createCell(col);
				//	cell.setCellStyle(columnHeading);
				cell.setCellValue(ps.getSmp_prod_name());
				col++;
				
				cell = hrow.createCell(col);
				//	cell.setCellStyle(columnHeading);
				cell.setCellValue(ps.getPre_in_market());
				col++;
				
				cell = hrow.createCell(col);
				//	cell.setCellStyle(columnHeading);
				cell.setCellValue(ps.getSgprmdet_disp_nm());
				col++;
				
				cell = hrow.createCell(col);
				cell.setCellStyle(decimalstyle3);
				cell.setCellValue(ps.getCog_rate_perunit().doubleValue());
				col++;
				
				cell = hrow.createCell(col);
				cell.setCellStyle(decimalstyle4);
				//wsheet.addMergedRegion(new CellRangeAddress(row, row, col, col+14));		
				cell.setCellValue(ps.getNo_of_te().longValue());
				col++;
				
			}
			else {
				cell = hrow.createCell(col);
			//	cell.setCellStyle(columnHeading);
				cell.setCellValue(" ");
				col++;
				
				cell = hrow.createCell(col);
				//	cell.setCellStyle(columnHeading);
				cell.setCellValue(" ");
				col++;
				
				cell = hrow.createCell(col);
				//	cell.setCellStyle(columnHeading);
				cell.setCellValue(" ");
				col++;
				
				cell = hrow.createCell(col);
				//	cell.setCellStyle(columnHeading);
				cell.setCellValue(" ");
				col++;
							
				cell = hrow.createCell(col);
				//	cell.setCellStyle(columnHeading);
				cell.setCellValue(" ");
				col++;
				
				cell = hrow.createCell(col);
				//	cell.setCellStyle(columnHeading);
				cell.setCellValue(" ");
				col++;
				
				cell = hrow.createCell(col);
				//	cell.setCellStyle(columnHeading);
				cell.setCellValue(" ");
				col++;
				
				cell = hrow.createCell(col);
				cell.setCellStyle(decimalstyle3);
				cell.setCellValue(" ");
				col++;
				
				cell = hrow.createCell(col);
				cell.setCellStyle(decimalstyle4);
				cell.setCellValue(" ");
				col++;
				
			}


				tot_unitDec19 = ps.getNo_of_te().multiply(ps.getDec_19());			
				tot_of_pso = tot_of_pso.add(ps.getDec_19());

				tot_unitJan19 = ps.getNo_of_te().multiply(ps.getJan_20());
				tot_of_pso = tot_of_pso.add(ps.getJan_20());

				tot_unitFeb19 = ps.getNo_of_te().multiply(ps.getFeb_20());
				tot_of_pso = tot_of_pso.add(ps.getFeb_20());

				tot_unitMar19 = ps.getNo_of_te().multiply(ps.getMar_20());
				tot_of_pso = tot_of_pso.add(ps.getMar_20());

				tot_unitApr19 = ps.getNo_of_te().multiply(ps.getApr_20());
				tot_of_pso = tot_of_pso.add(ps.getApr_20());

				tot_unitMay19 = ps.getNo_of_te().multiply(ps.getMay_20());
				tot_of_pso = tot_of_pso.add(ps.getMay_20());

				tot_unitJun19 = ps.getNo_of_te().multiply(ps.getJun_20());
				tot_of_pso = tot_of_pso.add(ps.getJun_20());

				tot_unitJul19 = ps.getNo_of_te().multiply(ps.getJul_20());
				tot_of_pso = tot_of_pso.add(ps.getJul_20());

				tot_unitAug19 = ps.getNo_of_te().multiply(ps.getAug_20());
				tot_of_pso = tot_of_pso.add(ps.getAug_20());

				tot_unitSep19 = ps.getNo_of_te().multiply(ps.getSep_20());
				tot_of_pso = tot_of_pso.add(ps.getSep_20());

				tot_unitOct19 = ps.getNo_of_te().multiply(ps.getOct_20());
				tot_of_pso = tot_of_pso.add(ps.getOct_20());

				tot_unitNov19 = ps.getNo_of_te().multiply(ps.getNov_20());
				tot_of_pso = tot_of_pso.add(ps.getNov_20());

				//total
				tot_costDec19 = tot_unitDec19.multiply(ps.getCog_rate_perunit());
				tot_of_totunit = tot_of_totunit.add(tot_unitDec19);

				tot_of_totunit = tot_of_totunit.add(tot_unitJan19);
				tot_costJan19 = tot_unitJan19.multiply(ps.getCog_rate_perunit());

				tot_of_totunit = tot_of_totunit.add(tot_unitFeb19);
				tot_costFeb19 = tot_unitFeb19.multiply(ps.getCog_rate_perunit());

				tot_of_totunit = tot_of_totunit.add(tot_unitMar19);
				tot_costMar19 = tot_unitMar19.multiply(ps.getCog_rate_perunit());

				tot_of_totunit = tot_of_totunit.add(tot_unitApr19);
				tot_costApr19 = tot_unitApr19.multiply(ps.getCog_rate_perunit());

				tot_of_totunit = tot_of_totunit.add(tot_unitMay19);
				tot_costMay19 = tot_unitMay19.multiply(ps.getCog_rate_perunit());

				tot_of_totunit = tot_of_totunit.add(tot_unitJun19);
				tot_costJun19 = tot_unitJun19.multiply(ps.getCog_rate_perunit());

				tot_of_totunit = tot_of_totunit.add(tot_unitJul19);
				tot_costJul19 = tot_unitJul19.multiply(ps.getCog_rate_perunit());

				tot_of_totunit = tot_of_totunit.add(tot_unitAug19);
				tot_costAug19 = tot_unitAug19.multiply(ps.getCog_rate_perunit());

				tot_of_totunit = tot_of_totunit.add(tot_unitSep19);
				tot_costSep19 = tot_unitSep19.multiply(ps.getCog_rate_perunit());

				tot_of_totunit = tot_of_totunit.add(tot_unitOct19);
				tot_costOct19 = tot_unitOct19.multiply(ps.getCog_rate_perunit());

				tot_of_totunit = tot_of_totunit.add(tot_unitNov19);
				tot_costNov19 = tot_unitNov19.multiply(ps.getCog_rate_perunit());


				cell = hrow.createCell(col); 
				cell.setCellStyle(rightandleftborder);
				cell.setCellValue(tot_of_totunit.longValue());
				col++;
				
				cell = hrow.createCell(col); 
				cell.setCellStyle(rightandleftborder);
				cell.setCellValue(ps.getIstock() == null ? 0l : ps.getIstock().longValue());
				col++;
				
				
				

//				row++;
//				col = 0;
//				hrow = (XSSFRow) wsheet.createRow(row); // row 4
//
//				cell = hrow.createCell(col); // cell.setCellStyle(columnHeading);
//				cell.setCellValue(" ");
//				wsheet.addMergedRegion(new CellRangeAddress(row, row, col, col=col + 8));
//				col++;

//				cell = hrow.createCell(col);
//				 cell.setCellStyle(decimalstyle);
//				cell.setCellValue("Total Cost (Rs.)");
//				col++;

//				cell = hrow.createCell(col); 
//				 cell.setCellStyle(decimalstyle);
//				cell.setCellValue(tot_costDec19.longValue());
				tot_of_totcost = tot_of_totcost.add(tot_costDec19);
				
//				grnd_tot_costDec19 = grnd_tot_costDec19.add(tot_costDec19);
//				col++;

//				cell = hrow.createCell(col); 
//				cell.setCellStyle(decimalstyle);
//				cell.setCellValue(tot_costJan19.longValue());
				tot_of_totcost = tot_of_totcost.add(tot_costJan19);
				
//				grnd_tot_costJan19 = grnd_tot_costJan19.add(tot_costJan19);
//				col++;

//				cell = hrow.createCell(col); 
//				cell.setCellStyle(decimalstyle);
//				cell.setCellValue(tot_costFeb19.longValue());
				tot_of_totcost = tot_of_totcost.add(tot_costFeb19);
				
//				grnd_tot_costFeb19 = grnd_tot_costFeb19.add(tot_costFeb19);
//				col++;

//				cell = hrow.createCell(col); 
//				cell.setCellStyle(decimalstyle);
//				cell.setCellValue(tot_costMar19.longValue());
				tot_of_totcost = tot_of_totcost.add(tot_costMar19);
				
				grnd_tot_costMar19 = grnd_tot_costMar19.add(tot_costMar19);
//				col++;

//				cell = hrow.createCell(col); 
//				cell.setCellStyle(decimalstyle);
//				cell.setCellValue(tot_costApr19.longValue());
				tot_of_totcost = tot_of_totcost.add(tot_costApr19);
				
//				grnd_tot_costApr19 = grnd_tot_costApr19.add(tot_costApr19);
//				col++;

//				cell = hrow.createCell(col); 
//				cell.setCellStyle(decimalstyle);
//				cell.setCellValue(tot_costMay19.longValue());
				tot_of_totcost = tot_of_totcost.add(tot_costMay19);
				
//				grnd_tot_costMay19 = grnd_tot_costMay19.add(tot_costMay19);
//				col++;

//				cell = hrow.createCell(col); 
//				cell.setCellStyle(decimalstyle);
//				cell.setCellValue(tot_costJun19.longValue());
				tot_of_totcost = tot_of_totcost.add(tot_costJun19);
				
//				grnd_tot_costJun19 = grnd_tot_costJun19.add(tot_costJun19);
//				col++;

//				cell = hrow.createCell(col); 
//				cell.setCellStyle(decimalstyle);
//				cell.setCellValue(tot_costJul19.longValue());
				tot_of_totcost = tot_of_totcost.add(tot_costJul19);
				
//				grnd_tot_costJul19 = grnd_tot_costJul19.add(tot_costJul19);
//				col++;

//				cell = hrow.createCell(col); 
//				cell.setCellStyle(decimalstyle);
//				cell.setCellValue(tot_costAug19.longValue());
				tot_of_totcost = tot_of_totcost.add(tot_costAug19);
				
//				grnd_tot_costAug19 = grnd_tot_costAug19.add(tot_costAug19);
//				col++;

//				cell = hrow.createCell(col); 
//				cell.setCellStyle(decimalstyle);
//				cell.setCellValue(tot_costSep19.longValue());
				tot_of_totcost = tot_of_totcost.add(tot_costSep19);
				
//				grnd_tot_costSep19 = grnd_tot_costSep19.add(tot_costSep19);
//				col++;

//				cell = hrow.createCell(col); 
//				cell.setCellStyle(decimalstyle);
//				cell.setCellValue(tot_costOct19.longValue());
				tot_of_totcost = tot_of_totcost.add(tot_costOct19);
				
//				grnd_tot_costOct19 = grnd_tot_costOct19.add(tot_costOct19);
//				col++;

//				cell = hrow.createCell(col); 
//				cell.setCellStyle(decimalstyle);
//				cell.setCellValue(tot_costNov19.longValue());
				tot_of_totcost = tot_of_totcost.add(tot_costNov19);
				
				grnd_tot_costNov19 = grnd_tot_costNov19.add(tot_costNov19);
//				col++;

//				cell = hrow.createCell(col); 
//				cell.setCellStyle(decimalstyle);
//				cell.setCellValue(tot_of_totcost.longValue());
//				
//				col++; 
				
	//			col =0;
				
				
				
				 tot_unitDec19 =  BigDecimal.ZERO;
				 tot_unitJan19 =  BigDecimal.ZERO;
				 tot_unitFeb19 =  BigDecimal.ZERO;
				 tot_unitMar19 =  BigDecimal.ZERO;
				 tot_unitApr19 =  BigDecimal.ZERO;
				 tot_unitMay19 =  BigDecimal.ZERO;
				 tot_unitJun19 =  BigDecimal.ZERO;
				 tot_unitJul19 =  BigDecimal.ZERO;
				 tot_unitAug19 =  BigDecimal.ZERO;
				 tot_unitSep19 =  BigDecimal.ZERO;
				 tot_unitOct19 =  BigDecimal.ZERO;
				 tot_unitNov19 =  BigDecimal.ZERO;
				 tot_of_totunit=  BigDecimal.ZERO;
				 tot_of_pso    =  BigDecimal.ZERO;
				
				 
				 tot_costDec19 = BigDecimal.ZERO;
				 tot_costJan19 = BigDecimal.ZERO;
				 tot_costFeb19 = BigDecimal.ZERO;
				 tot_costMar19 = BigDecimal.ZERO;
				 tot_costApr19 = BigDecimal.ZERO;
				 tot_costMay19 = BigDecimal.ZERO;
				 tot_costJun19 = BigDecimal.ZERO;
				 tot_costJul19 = BigDecimal.ZERO;
				 tot_costAug19 = BigDecimal.ZERO;
				 tot_costSep19 = BigDecimal.ZERO;
				 tot_costOct19 = BigDecimal.ZERO;
				 tot_costNov19 = BigDecimal.ZERO;
				 tot_of_totcost= BigDecimal.ZERO;
				 
					row++;
					col=0;
					old_team = new_team;
					old_prod = new_prod;
				 
			}
			
			
			 hrow = (XSSFRow) wsheet.createRow(row); // blank row
				
				
			 for(int i = col;i<9;i++) {
					cell = hrow.createCell(col); 
					cell.setCellStyle(bottomline);
					col++;
					}
				
				
				for(int i = col;i<11;i++) {
				cell = hrow.createCell(col); 
				cell.setCellStyle(decimalstyle2);
				col++;
				}

			FileOutputStream fileOut = new FileOutputStream(path.toString());
			wwbook.write(fileOut);
			fileOut.close();
			
			filename=usermasterservice.generateExcellock(path.toString(), filename,empId,".xlsx");

			                    //Now add files to the zip file
			
			System.out.println("Excel Created");
			
		}
		catch (Exception e) {
			// TODO: handle exception
			System.out.println("Error Occurred :" +e.getMessage());// uncomment asneeded --;
		}
		
		return filename;
	}

	
	
	//con Report
	@Override
	public String generate_annual_sample_plan_con_report(List<Annual_sample_plan_view_report_cons> list,
			String companyname,String finyr) throws Exception {
		//TODO Auto-generated method stub
		
		Workbook wwbook = null;
		File ff = null;
		String filename = null;
		StringBuffer path = null;

		try {
			String finyear = finyr.substring(2);
			Integer year = Integer.parseInt(finyear);
			String companyname1 = ((Company) session.getServletContext().getAttribute(COMPANY_DATA)).getCompany_name();
			
			long l = new Date().getTime();
			filename = "Annual_sample_plan_con_report" + l + ".xlsx";
			String filePath = MedicoConstants.REPORT_FILE_PATH;

			ff = new File(filePath);
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
			
			int col = 0;
			int row = 0;
			
			wwbook = new XSSFWorkbook();
		

			ApachePoiExcelFormat xlsxExcelFormat = new ApachePoiExcelFormat();
			
			CellStyle sheetHeading = xlsxExcelFormat.SheetHeading(wwbook);
			CellStyle columnHeading = xlsxExcelFormat.columnHeading(wwbook);
			columnHeading.setAlignment(HorizontalAlignment.CENTER);
			columnHeading.setVerticalAlignment(VerticalAlignment.CENTER);
			
			CellStyle leftAlign = xlsxExcelFormat.dataLeftAlign(wwbook);
			
 			CellStyle columnHeadingBlue = xlsxExcelFormat.columnSubHeadingBlue(wwbook);
 			
 			
			CellStyle leftAlignB = xlsxExcelFormat.dataBoldLeftAlign(wwbook);
			
			
			
			Font font = wwbook.createFont();
			font.setFontHeightInPoints((short) 11);
			font.setFontName("ARIAL");
			font.setBold(true);
			CellStyle decimalstyle = wwbook.createCellStyle();
		//	decimalstyle.setDataFormat(wwbook.createDataFormat().getFormat("0.00"));
			decimalstyle.setAlignment(HorizontalAlignment.RIGHT);
			decimalstyle.setFont(font);
			decimalstyle.setBorderLeft(BorderStyle.THIN);
			decimalstyle.setBorderRight(BorderStyle.THIN);
			
			
			Font font2 = wwbook.createFont();
			font2.setFontHeightInPoints((short) 11);
			font2.setFontName("ARIAL");
			CellStyle rightandleftborder = wwbook.createCellStyle();
			rightandleftborder.setAlignment(HorizontalAlignment.RIGHT);
			rightandleftborder.setBorderLeft(BorderStyle.THIN);
			rightandleftborder.setBorderRight(BorderStyle.THIN);
			rightandleftborder.setFont(font2);
			
			Font font3 = wwbook.createFont();
			font3.setFontHeightInPoints((short) 11);
			font3.setFontName("ARIAL");
			font3.setBold(true);
			CellStyle decimalstyle2 = wwbook.createCellStyle();
		//	decimalstyle2.setDataFormat(wwbook.createDataFormat().getFormat("0.00"));
			decimalstyle2.setAlignment(HorizontalAlignment.RIGHT);
			decimalstyle2.setFont(font);
			decimalstyle2.setBorderLeft(BorderStyle.THIN);
			decimalstyle2.setBorderRight(BorderStyle.THIN);
			decimalstyle2.setBorderBottom(BorderStyle.THIN);
			
			
			Font font4 = wwbook.createFont();
			font4.setFontHeightInPoints((short) 11);
			font4.setFontName("ARIAL");
			font4.setBold(true);
			CellStyle decimalstyle3 = wwbook.createCellStyle();
			decimalstyle3.setDataFormat(wwbook.createDataFormat().getFormat("0.00"));
			decimalstyle3.setAlignment(HorizontalAlignment.RIGHT);
			decimalstyle3.setFont(font4);
			
			
			Font font5 = wwbook.createFont();
			font5.setFontHeightInPoints((short) 11);
			font5.setFontName("ARIAL");
			font5.setBold(true);
			CellStyle decimalstyle4 = wwbook.createCellStyle();
			//decimalstyle4.setDataFormat(wwbook.createDataFormat().getFormat("0.00"));
			decimalstyle4.setAlignment(HorizontalAlignment.RIGHT);
			decimalstyle4.setFont(font5);
			
			
			Font font6 = wwbook.createFont();
			font6.setFontHeightInPoints((short) 11);
			font6.setFontName("ARIAL");
			font6.setBold(true);
			CellStyle decimalstyle5 = wwbook.createCellStyle();
			//decimalstyle5.setDataFormat(wwbook.createDataFormat().getFormat("0.00"));
			decimalstyle5.setAlignment(HorizontalAlignment.RIGHT);
			decimalstyle5.setFont(font);
		//	decimalstyle5.setBorderLeft(BorderStyle.THIN);
		//	decimalstyle5.setBorderRight(BorderStyle.THIN);
			decimalstyle5.setBorderBottom(BorderStyle.THIN);
			
			String[] heading1= {"BU","Team Name","Product Code","Brand","Product Name","Presence in India Market","Types of Samples","COG/Units","No.of TE/TSO"
					," ","Qtr 1","Qtr 2","Qtr 3","Qtr 4","Total"};
			
		//	String[]heading2= {"Dec'18","Jan'19","Feb'19","Mar'19","Apr'19","May'19","Jun'19","Jul'19","Aug'19","Sep'19","Oct'19","Nov'19","Dec'19"};
			
			Sheet wsheet = wwbook.createSheet(list.get(0).getTeam());
			wsheet.createFreezePane(0, 5);								//added
			
			XSSFRow hrow = null;
			XSSFCell cell = null;
			
			BigDecimal tot_unitDec19 = new BigDecimal(0);
			BigDecimal tot_unitJan19 = new BigDecimal(0);
			BigDecimal tot_unitFeb19 = new BigDecimal(0);
			BigDecimal tot_unitMar19 = new BigDecimal(0);
			BigDecimal tot_unitApr19 = new BigDecimal(0);
			BigDecimal tot_unitMay19 = new BigDecimal(0);
			BigDecimal tot_unitJun19 = new BigDecimal(0);
			BigDecimal tot_unitJul19 = new BigDecimal(0);
			BigDecimal tot_unitAug19 = new BigDecimal(0);
			BigDecimal tot_unitSep19 = new BigDecimal(0);
			BigDecimal tot_unitOct19 = new BigDecimal(0);
			BigDecimal tot_unitNov19 = new BigDecimal(0);		
			BigDecimal tot_of_totunit = new BigDecimal(0);
			
			BigDecimal tot_of_pso = new BigDecimal(0);
			
			BigDecimal tot_costDec19 = new BigDecimal(0);
			BigDecimal tot_costJan19 = new BigDecimal(0);
			BigDecimal tot_costFeb19 = new BigDecimal(0);
			BigDecimal tot_costMar19 = new BigDecimal(0);
			BigDecimal tot_costApr19 = new BigDecimal(0);
			BigDecimal tot_costMay19 = new BigDecimal(0);
			BigDecimal tot_costJun19 = new BigDecimal(0);
			BigDecimal tot_costJul19 = new BigDecimal(0);
			BigDecimal tot_costAug19 = new BigDecimal(0);
			BigDecimal tot_costSep19 = new BigDecimal(0);
			BigDecimal tot_costOct19 = new BigDecimal(0);
			BigDecimal tot_costNov19 = new BigDecimal(0);	
			BigDecimal tot_of_totcost = new BigDecimal(0);	
			
			BigDecimal grnd_tot_costDec19 = new BigDecimal(0);
			BigDecimal grnd_tot_costJan19 = new BigDecimal(0);
			BigDecimal grnd_tot_costFeb19 = new BigDecimal(0);
			BigDecimal grnd_tot_costMar19 = new BigDecimal(0);
			BigDecimal grnd_tot_costApr19 = new BigDecimal(0);
			BigDecimal grnd_tot_costMay19 = new BigDecimal(0);
			BigDecimal grnd_tot_costJun19 = new BigDecimal(0);
			BigDecimal grnd_tot_costJul19 = new BigDecimal(0);
			BigDecimal grnd_tot_costAug19 = new BigDecimal(0);
			BigDecimal grnd_tot_costSep19 = new BigDecimal(0);
			BigDecimal grnd_tot_costOct19 = new BigDecimal(0);
			BigDecimal grnd_tot_costNov19 = new BigDecimal(0);	
			BigDecimal grnd_tot_of_grd_totcost = new BigDecimal(0);	
			
			String old_team = "old";
			String new_team ="";
			
			hrow =  (XSSFRow) wsheet.createRow(row);
			cell = hrow.createCell(col);
			cell.setCellStyle(sheetHeading);
			cell.setCellValue("Annual Sample Plan Report");
			wsheet.addMergedRegion(new CellRangeAddress(row, row, col, col + (heading1.length)+7));
			row++;
			
			
			hrow =  (XSSFRow) wsheet.createRow(row);
			cell = hrow.createCell(col);
			cell.setCellStyle(sheetHeading);
			cell.setCellValue(companyname1);
			wsheet.addMergedRegion(new CellRangeAddress(row, row, col, col + (heading1.length)+7));
			row++;
			
			
			hrow =  (XSSFRow) wsheet.createRow(row);
			
			cell = hrow.createCell(col);
			cell.setCellStyle(columnHeading);
			cell.setCellValue("BU");
			wsheet.addMergedRegion(new CellRangeAddress(row, row+2, col, col));
			col++;
							
			cell = hrow.createCell(col);
			cell.setCellStyle(columnHeading);
			cell.setCellValue("Team Name");
			wsheet.addMergedRegion(new CellRangeAddress(row, row+2, col, col));
			col++;	
			
			cell = hrow.createCell(col);
			cell.setCellStyle(columnHeading);
			cell.setCellValue("Product Code");
			wsheet.addMergedRegion(new CellRangeAddress(row, row+2, col, col));
			col++;	
			
			cell = hrow.createCell(col);
			cell.setCellStyle(columnHeading);
			cell.setCellValue("Brand");
			wsheet.addMergedRegion(new CellRangeAddress(row, row+2, col, col));
			col++;	
			
			cell = hrow.createCell(col);
			cell.setCellStyle(columnHeading);
			cell.setCellValue("Product Name");
			wsheet.addMergedRegion(new CellRangeAddress(row, row+2, col, col));
			col++;	
			
			cell = hrow.createCell(col);
			cell.setCellStyle(columnHeading);
			cell.setCellValue("Presence in India Market");
			wsheet.addMergedRegion(new CellRangeAddress(row, row+2, col, col));
			col++;	
			
			cell = hrow.createCell(col);
			cell.setCellStyle(columnHeading);
			cell.setCellValue("Type of samples");
			wsheet.addMergedRegion(new CellRangeAddress(row, row+2, col, col));
			col++;	
			
			cell = hrow.createCell(col);
			cell.setCellStyle(columnHeading);
			cell.setCellValue("COG/Unit");
			wsheet.addMergedRegion(new CellRangeAddress(row, row+2, col, col));
			col++;	
			
			cell = hrow.createCell(col);
			cell.setCellStyle(columnHeading);
			cell.setCellValue("No.of TE/TSO");
			wsheet.addMergedRegion(new CellRangeAddress(row, row+2, col, col));
			col++;	
			
			cell = hrow.createCell(col);
			cell.setCellStyle(columnHeading);
			cell.setCellValue("  ");
			wsheet.addMergedRegion(new CellRangeAddress(row, row=row+2, col, col));
			col++;
			
			
			cell = hrow.createCell(col);
			cell.setCellStyle(columnHeading);
			cell.setCellValue("Qtr 1");
			wsheet.addMergedRegion(new CellRangeAddress(2, row-1, col, col+2));
			col++;
			
			cell = hrow.createCell(col);
			cell.setCellStyle(columnHeading);
		//	cell.setCellValue("  ");			
			col++;
			
			
			cell = hrow.createCell(col);
			cell.setCellStyle(columnHeading);						//This Blank cells are for border
		//	cell.setCellValue("  ");		
			col++;	
			
			cell = hrow.createCell(col);
			cell.setCellStyle(columnHeading);
			cell.setCellValue("Qtr 2");
			wsheet.addMergedRegion(new CellRangeAddress(2, row-1, col, col+2));
			col++;	
						
			cell = hrow.createCell(col);
			cell.setCellStyle(columnHeading);
		//	cell.setCellValue("  ");			
			col++;
			
			
			cell = hrow.createCell(col);
			cell.setCellStyle(columnHeading);
		//	cell.setCellValue("  ");	
			col++;
			
			cell = hrow.createCell(col);
			cell.setCellStyle(columnHeading);
			cell.setCellValue("Qtr 3");
			wsheet.addMergedRegion(new CellRangeAddress(2, row-1, col, col+2));
			col++;	
			
			cell = hrow.createCell(col);
			cell.setCellStyle(columnHeading);
		//	cell.setCellValue("  ");			
			col++;
			
			
			cell = hrow.createCell(col);
			cell.setCellStyle(columnHeading);
		//	cell.setCellValue("  ");	
			col++;
			
			cell = hrow.createCell(col);
			cell.setCellStyle(columnHeading);
			cell.setCellValue("Qtr 4");
			wsheet.addMergedRegion(new CellRangeAddress(2, row-1, col, col+2));
			col++;	
			
			cell = hrow.createCell(col);
			cell.setCellStyle(columnHeading);
		//	cell.setCellValue("  ");			
			col++;
			
			
			cell = hrow.createCell(col);
			cell.setCellStyle(columnHeading);
		//	cell.setCellValue("  ");	
			col++;
			
			cell = hrow.createCell(col);
			cell.setCellStyle(columnHeading);
			cell.setCellValue("Total");
			wsheet.addMergedRegion(new CellRangeAddress(2, row, col, col));
			
			//row for border
			hrow =  (XSSFRow) wsheet.createRow(3);
			col=0;
			for(int i=0;i<23;i++) {
				cell = hrow.createCell(col);
				cell.setCellStyle(columnHeading);
			//	cell.setCellValue("  ");	
				col++;
			}
			
	
			col=0;
			
			hrow =  (XSSFRow) wsheet.createRow(row);
			
			for(int i=0;i<10;i++) {
				cell = hrow.createCell(col);
				cell.setCellStyle(columnHeading);
			//	cell.setCellValue("  ");	
				col++;
			}
			
			cell = hrow.createCell(col);
			cell.setCellStyle(columnHeading);
			cell.setCellValue("Dec'"+(year-1));
			col++;
			
			cell = hrow.createCell(col);
			cell.setCellStyle(columnHeading);
			cell.setCellValue("Jan'"+(year));
			col++;
			
			cell = hrow.createCell(col);
			cell.setCellStyle(columnHeading);
			cell.setCellValue("Feb'"+(year));
			col++;
			
			cell = hrow.createCell(col);
			cell.setCellStyle(columnHeading);
			cell.setCellValue("Mar'"+(year));
			col++;
			
			cell = hrow.createCell(col);
			cell.setCellStyle(columnHeading);
			cell.setCellValue("Apr'"+(year));
			col++;
			
			
			cell = hrow.createCell(col);
			cell.setCellStyle(columnHeading);
			cell.setCellValue("May'"+(year));
			col++;
			
			cell = hrow.createCell(col);
			cell.setCellStyle(columnHeading);
			cell.setCellValue("Jun'"+(year));
			col++;
			
			cell = hrow.createCell(col);
			cell.setCellStyle(columnHeading);
			cell.setCellValue("Jul'"+(year));
			col++;
			
			cell = hrow.createCell(col);
			cell.setCellStyle(columnHeading);
			cell.setCellValue("Aug'"+(year));
			col++;
			
			cell = hrow.createCell(col);
			cell.setCellStyle(columnHeading);
			cell.setCellValue("Sep'"+(year));
			col++;
			
			cell = hrow.createCell(col);
			cell.setCellStyle(columnHeading);
			cell.setCellValue("Oct'"+(year));
			col++;
			
			cell = hrow.createCell(col);
			cell.setCellStyle(columnHeading);
			cell.setCellValue("Nov'"+(year));
			col++;

			cell = hrow.createCell(col);
			cell.setCellStyle(columnHeading);
		//	cell.setCellValue("  ");	
		
			row++;
			col=0;
			
			for(Annual_sample_plan_view_report_cons ps : list) {
			
				new_team = ps.getTeam();
				if(!new_team.equalsIgnoreCase(old_team) && !old_team.equalsIgnoreCase("old")) {
					
					 //Grand Total
					 hrow = (XSSFRow) wsheet.createRow(row); // blank row
						
						
						cell = hrow.createCell(col); 
						wsheet.addMergedRegion(new CellRangeAddress(row, row, col, col=col + 8));
						col++;
						
						
						for(int i = col;i<23;i++) {
						cell = hrow.createCell(col); 
						cell.setCellStyle(rightandleftborder);
						col++;
						}
						
						row++;
						col=0;
					
					hrow = (XSSFRow) wsheet.createRow(row); // Grand Total Row1
					

					
					for(int i = col;i<=8;i++) {
						cell = hrow.createCell(col); 
						cell.setCellStyle(decimalstyle5);
						col++;
						}
					
					cell = hrow.createCell(col); 
					cell.setCellStyle(decimalstyle2);
					cell.setCellValue("Grand Total (Rs.)");
					col++;
					
					cell = hrow.createCell(col); 
					cell.setCellStyle(decimalstyle2);
					cell.setCellValue(grnd_tot_costDec19.longValue());
					grnd_tot_of_grd_totcost = grnd_tot_of_grd_totcost.add(grnd_tot_costDec19);
					col++;
					
					cell = hrow.createCell(col); 
					cell.setCellStyle(decimalstyle2);
					cell.setCellValue(grnd_tot_costJan19.longValue());
					grnd_tot_of_grd_totcost = grnd_tot_of_grd_totcost.add(grnd_tot_costJan19);
					col++;
					
					cell = hrow.createCell(col); 
					cell.setCellStyle(decimalstyle2);
					cell.setCellValue(grnd_tot_costFeb19.longValue());
					grnd_tot_of_grd_totcost = grnd_tot_of_grd_totcost.add(grnd_tot_costFeb19);
					col++;
					
					cell = hrow.createCell(col); 
					cell.setCellStyle(decimalstyle2);
					cell.setCellValue(grnd_tot_costMar19.longValue());
					grnd_tot_of_grd_totcost = grnd_tot_of_grd_totcost.add(grnd_tot_costMar19);
					col++;
					
					cell = hrow.createCell(col); 
					cell.setCellStyle(decimalstyle2);
					cell.setCellValue(grnd_tot_costApr19.longValue());
					grnd_tot_of_grd_totcost = grnd_tot_of_grd_totcost.add(grnd_tot_costApr19);
					col++;
					
					cell = hrow.createCell(col); 
					cell.setCellStyle(decimalstyle2);
					cell.setCellValue(grnd_tot_costMay19.longValue());
					grnd_tot_of_grd_totcost = grnd_tot_of_grd_totcost.add(grnd_tot_costMay19);
					col++;
					
					cell = hrow.createCell(col); 
					cell.setCellStyle(decimalstyle2);
					cell.setCellValue(grnd_tot_costJun19.longValue());
					grnd_tot_of_grd_totcost = grnd_tot_of_grd_totcost.add(grnd_tot_costJun19);
					col++;
					
					cell = hrow.createCell(col); 
					cell.setCellStyle(decimalstyle2);
					cell.setCellValue(grnd_tot_costJul19.longValue());
					grnd_tot_of_grd_totcost = grnd_tot_of_grd_totcost.add(grnd_tot_costJul19);
					col++;
					
					cell = hrow.createCell(col); 
					cell.setCellStyle(decimalstyle2);
					cell.setCellValue(grnd_tot_costAug19.longValue());
					grnd_tot_of_grd_totcost = grnd_tot_of_grd_totcost.add(grnd_tot_costAug19);
					col++;
					
					cell = hrow.createCell(col); 
					cell.setCellStyle(decimalstyle2);
					cell.setCellValue(grnd_tot_costSep19.longValue());
					grnd_tot_of_grd_totcost = grnd_tot_of_grd_totcost.add(grnd_tot_costSep19);
					col++;
					
					cell = hrow.createCell(col); 
					cell.setCellStyle(decimalstyle2);
					cell.setCellValue(grnd_tot_costOct19.longValue());
					grnd_tot_of_grd_totcost = grnd_tot_of_grd_totcost.add(grnd_tot_costOct19);
					col++;
					
					cell = hrow.createCell(col); 
					cell.setCellStyle(decimalstyle2);
					cell.setCellValue(grnd_tot_costNov19.longValue());
					grnd_tot_of_grd_totcost = grnd_tot_of_grd_totcost.add(grnd_tot_costNov19);
					col++;
					
					cell = hrow.createCell(col); 
					cell.setCellStyle(decimalstyle2);
					cell.setCellValue(grnd_tot_of_grd_totcost.longValue());
					col++;
					
					 grnd_tot_costDec19 = BigDecimal.ZERO;
					 grnd_tot_costJan19 = BigDecimal.ZERO;
					 grnd_tot_costFeb19 = BigDecimal.ZERO;
					 grnd_tot_costMar19 = BigDecimal.ZERO;
					 grnd_tot_costApr19 = BigDecimal.ZERO;
					 grnd_tot_costMay19 = BigDecimal.ZERO;
					 grnd_tot_costJun19 = BigDecimal.ZERO;
					 grnd_tot_costJul19 = BigDecimal.ZERO;
					 grnd_tot_costAug19 = BigDecimal.ZERO;
					 grnd_tot_costSep19 = BigDecimal.ZERO;
					 grnd_tot_costOct19 = BigDecimal.ZERO;
					 grnd_tot_costNov19 = BigDecimal.ZERO;
					 grnd_tot_of_grd_totcost = BigDecimal.ZERO;
					
					
					
					row = 0;
					col = 0;
					
					wsheet = wwbook.createSheet(ps.getTeam());			//adding new sheet
					wsheet.createFreezePane(0, 5);						//added
					hrow =  (XSSFRow) wsheet.createRow(row);
					
					cell = hrow.createCell(col);
					cell.setCellStyle(sheetHeading);
					cell.setCellValue("Annual Sample Plan Report");
					wsheet.addMergedRegion(new CellRangeAddress(row, row, col, col + (heading1.length)+7));
					row++;
					
					
					hrow =  (XSSFRow) wsheet.createRow(row);
					cell = hrow.createCell(col);
					cell.setCellStyle(sheetHeading);
					cell.setCellValue(companyname);
					wsheet.addMergedRegion(new CellRangeAddress(row, row, col, col + (heading1.length)+7));
					row++;
					
					
					hrow =  (XSSFRow) wsheet.createRow(row);
					
					cell = hrow.createCell(col);
					cell.setCellStyle(columnHeading);
					cell.setCellValue("BU");
					wsheet.addMergedRegion(new CellRangeAddress(row, row+2, col, col));
					col++;
									
					cell = hrow.createCell(col);
					cell.setCellStyle(columnHeading);
					cell.setCellValue("Team Name");
					wsheet.addMergedRegion(new CellRangeAddress(row, row+2, col, col));
					col++;	
					
					cell = hrow.createCell(col);
					cell.setCellStyle(columnHeading);
					cell.setCellValue("Product Code");
					wsheet.addMergedRegion(new CellRangeAddress(row, row+2, col, col));
					col++;	
					
					cell = hrow.createCell(col);
					cell.setCellStyle(columnHeading);
					cell.setCellValue("Brand");
					wsheet.addMergedRegion(new CellRangeAddress(row, row+2, col, col));
					col++;	
					
					cell = hrow.createCell(col);
					cell.setCellStyle(columnHeading);
					cell.setCellValue("Product Name");
					wsheet.addMergedRegion(new CellRangeAddress(row, row+2, col, col));
					col++;	
					
					cell = hrow.createCell(col);
					cell.setCellStyle(columnHeading);
					cell.setCellValue("Presence in India Market");
					wsheet.addMergedRegion(new CellRangeAddress(row, row+2, col, col));
					col++;	
					
					cell = hrow.createCell(col);
					cell.setCellStyle(columnHeading);
					cell.setCellValue("Type of samples");
					wsheet.addMergedRegion(new CellRangeAddress(row, row+2, col, col));
					col++;	
					
					cell = hrow.createCell(col);
					cell.setCellStyle(columnHeading);
					cell.setCellValue("COG/Unit");
					wsheet.addMergedRegion(new CellRangeAddress(row, row+2, col, col));
					col++;	
					
					cell = hrow.createCell(col);
					cell.setCellStyle(columnHeading);
					cell.setCellValue("No.of TE/TSO");
					wsheet.addMergedRegion(new CellRangeAddress(row, row+2, col, col));
					col++;	
					
					cell = hrow.createCell(col);
					cell.setCellStyle(columnHeading);
					cell.setCellValue("  ");
					wsheet.addMergedRegion(new CellRangeAddress(row, row=row+2, col, col));
					col++;
					
					
					cell = hrow.createCell(col);
					cell.setCellStyle(columnHeading);
					cell.setCellValue("Qtr 1");
					wsheet.addMergedRegion(new CellRangeAddress(2, row-1, col, col+2));
					col++;
					
					cell = hrow.createCell(col);
					cell.setCellStyle(columnHeading);
				//	cell.setCellValue("  ");			
					col++;
					
					
					cell = hrow.createCell(col);
					cell.setCellStyle(columnHeading);						//This Blank cells are for border
				//	cell.setCellValue("  ");		
					col++;	
					
					cell = hrow.createCell(col);
					cell.setCellStyle(columnHeading);
					cell.setCellValue("Qtr 2");
					wsheet.addMergedRegion(new CellRangeAddress(2, row-1, col, col+2));
					col++;	
								
					cell = hrow.createCell(col);
					cell.setCellStyle(columnHeading);
				//	cell.setCellValue("  ");			
					col++;
					
					
					cell = hrow.createCell(col);
					cell.setCellStyle(columnHeading);
				//	cell.setCellValue("  ");	
					col++;
					
					cell = hrow.createCell(col);
					cell.setCellStyle(columnHeading);
					cell.setCellValue("Qtr 3");
					wsheet.addMergedRegion(new CellRangeAddress(2, row-1, col, col+2));
					col++;	
					
					cell = hrow.createCell(col);
					cell.setCellStyle(columnHeading);
				//	cell.setCellValue("  ");			
					col++;
					
					
					cell = hrow.createCell(col);
					cell.setCellStyle(columnHeading);
				//	cell.setCellValue("  ");	
					col++;
					
					cell = hrow.createCell(col);
					cell.setCellStyle(columnHeading);
					cell.setCellValue("Qtr 4");
					wsheet.addMergedRegion(new CellRangeAddress(2, row-1, col, col+2));
					col++;	
					
					cell = hrow.createCell(col);
					cell.setCellStyle(columnHeading);
				//	cell.setCellValue("  ");			
					col++;
					
					
					cell = hrow.createCell(col);
					cell.setCellStyle(columnHeading);
				//	cell.setCellValue("  ");	
					col++;
					
					cell = hrow.createCell(col);
					cell.setCellStyle(columnHeading);
					cell.setCellValue("Total");
					wsheet.addMergedRegion(new CellRangeAddress(2, row, col, col));
					
					//row for border
					hrow =  (XSSFRow) wsheet.createRow(3);
					col=0;
					for(int i=0;i<23;i++) {
						cell = hrow.createCell(col);
						cell.setCellStyle(columnHeading);
					//	cell.setCellValue("  ");	
						col++;
					}
					
			
					col=0;
					
					hrow =  (XSSFRow) wsheet.createRow(row);
					
					for(int i=0;i<10;i++) {
						cell = hrow.createCell(col);
						cell.setCellStyle(columnHeading);
					//	cell.setCellValue("  ");	
						col++;
					}
					
					cell = hrow.createCell(col);
					cell.setCellStyle(columnHeading);
					cell.setCellValue("Dec'"+(year-1));
					col++;
					
					cell = hrow.createCell(col);
					cell.setCellStyle(columnHeading);
					cell.setCellValue("Jan'"+(year));
					col++;
					
					cell = hrow.createCell(col);
					cell.setCellStyle(columnHeading);
					cell.setCellValue("Feb'"+(year));
					col++;
					
					cell = hrow.createCell(col);
					cell.setCellStyle(columnHeading);
					cell.setCellValue("Mar'"+(year));
					col++;
					
					cell = hrow.createCell(col);
					cell.setCellStyle(columnHeading);
					cell.setCellValue("Apr'"+(year));
					col++;
					
					
					cell = hrow.createCell(col);
					cell.setCellStyle(columnHeading);
					cell.setCellValue("May'"+(year));
					col++;
					
					cell = hrow.createCell(col);
					cell.setCellStyle(columnHeading);
					cell.setCellValue("Jun'"+(year));
					col++;
					
					cell = hrow.createCell(col);
					cell.setCellStyle(columnHeading);
					cell.setCellValue("Jul'"+(year));
					col++;
					
					cell = hrow.createCell(col);
					cell.setCellStyle(columnHeading);
					cell.setCellValue("Aug'"+(year));
					col++;
					
					cell = hrow.createCell(col);
					cell.setCellStyle(columnHeading);
					cell.setCellValue("Sep'"+(year));
					col++;
					
					cell = hrow.createCell(col);
					cell.setCellStyle(columnHeading);
					cell.setCellValue("Oct'"+(year));
					col++;
					
					cell = hrow.createCell(col);
					cell.setCellStyle(columnHeading);
					cell.setCellValue("Nov'"+(year));
					col++;
					

					cell = hrow.createCell(col);
					cell.setCellStyle(columnHeading);
				//	cell.setCellValue("  ");	
				
					row++;
					col=0;
				}
			
			

				
				hrow =  (XSSFRow) wsheet.createRow(row);		//blank row
				cell = hrow.createCell(col);
				//	cell.setCellStyle(columnHeading);
					cell.setCellValue(" ");
					col++;
					
					cell = hrow.createCell(col);
					//	cell.setCellStyle(columnHeading);
					cell.setCellValue(" ");
					col++;
					
					cell = hrow.createCell(col);
					//	cell.setCellStyle(columnHeading);
					cell.setCellValue(" ");
					col++;
					
					cell = hrow.createCell(col);
					//	cell.setCellStyle(columnHeading);
					cell.setCellValue(" ");
					col++;
								
					cell = hrow.createCell(col);
					//	cell.setCellStyle(columnHeading);
					cell.setCellValue(" ");
					col++;
					
					cell = hrow.createCell(col);
					//	cell.setCellStyle(columnHeading);
					cell.setCellValue(" ");
					col++;
					
					cell = hrow.createCell(col);
					//	cell.setCellStyle(columnHeading);
					cell.setCellValue(" ");
					col++;
					
					cell = hrow.createCell(col);
					cell.setCellStyle(decimalstyle3);
					cell.setCellValue(" ");
					col++;
					
					cell = hrow.createCell(col);
					cell.setCellStyle(decimalstyle4);
					//wsheet.addMergedRegion(new CellRangeAddress(row, row, col, col+14));		
					cell.setCellValue(" ");
					col++;
					cell = hrow.createCell(col);
					cell.setCellStyle(rightandleftborder);
					//wsheet.addMergedRegion(new CellRangeAddress(row, row, col, col+14));		
					cell.setCellValue(" ");
					col++;
					
					for(int i = col;i<23;i++) {
					cell = hrow.createCell(col);
					cell.setCellStyle(rightandleftborder);
					//wsheet.addMergedRegion(new CellRangeAddress(row, row, col, col+14));		
					cell.setCellValue(" ");
					col++;
					}
					
					row++;
					col=0;
				
				hrow =  (XSSFRow) wsheet.createRow(row);		//row 1
				
				cell = hrow.createCell(col);
			//	cell.setCellStyle(columnHeading);
				cell.setCellValue(ps.getBu());
				col++;
				
				cell = hrow.createCell(col);
				//	cell.setCellStyle(columnHeading);
				cell.setCellValue(ps.getTeam());
				col++;
				
				cell = hrow.createCell(col);
				//	cell.setCellStyle(columnHeading);
				cell.setCellValue(ps.getProduct_code());
				col++;
				
				cell = hrow.createCell(col);
				//	cell.setCellStyle(columnHeading);
				cell.setCellValue(ps.getSa_group_name());
				col++;
							
				cell = hrow.createCell(col);
				//	cell.setCellStyle(columnHeading);
				cell.setCellValue(ps.getSmp_prod_name());
				col++;
				
				cell = hrow.createCell(col);
				//	cell.setCellStyle(columnHeading);
				cell.setCellValue(ps.getPre_in_market());
				col++;
				
				cell = hrow.createCell(col);
				//	cell.setCellStyle(columnHeading);
				cell.setCellValue(ps.getSgprmdet_disp_nm());
				col++;
				
				cell = hrow.createCell(col);
				cell.setCellStyle(decimalstyle3);
				cell.setCellValue(ps.getCog_rate_perunit().doubleValue());
				col++;
				
				cell = hrow.createCell(col);
				cell.setCellStyle(decimalstyle4);
				//wsheet.addMergedRegion(new CellRangeAddress(row, row, col, col+14));		
				cell.setCellValue(ps.getNo_of_te().longValue());
				col++;
				cell = hrow.createCell(col);
				cell.setCellStyle(rightandleftborder);
				//wsheet.addMergedRegion(new CellRangeAddress(row, row, col, col+14));		
				cell.setCellValue(" ");
				col++;
				
				for(int i = col;i<23;i++) {
				cell = hrow.createCell(col);
				cell.setCellStyle(rightandleftborder);
				//wsheet.addMergedRegion(new CellRangeAddress(row, row, col, col+14));		
				cell.setCellValue(" ");
				col++;
				}
				
				
				
				row++;
				col=0;
				hrow =  (XSSFRow) wsheet.createRow(row);		//row 2
				
				cell = hrow.createCell(col);
				//	cell.setCellStyle(columnHeading);
				cell.setCellValue(" ");
				wsheet.addMergedRegion(new CellRangeAddress(row, row, col, col=col+7));
				col++;
				
				cell = hrow.createCell(col);
				//cell.setCellStyle(rightandleftborder);
				cell.setCellValue(" ");
				col++;
				
				cell = hrow.createCell(col);
				cell.setCellStyle(rightandleftborder);
				cell.setCellValue("Per PSO");
				col++;
				
				cell = hrow.createCell(col);
				cell.setCellStyle(rightandleftborder);
				cell.setCellValue(ps.getDec_19().doubleValue());
				tot_unitDec19 = ps.getNo_of_te().multiply(ps.getDec_19());			
				tot_of_pso = tot_of_pso.add(ps.getDec_19());
				col++;
				
				cell = hrow.createCell(col);
				cell.setCellStyle(rightandleftborder);
				cell.setCellValue(ps.getJan_20().doubleValue());
				tot_unitJan19 = ps.getNo_of_te().multiply(ps.getJan_20());
				tot_of_pso = tot_of_pso.add(ps.getJan_20());
				col++;
				
				cell = hrow.createCell(col);
				cell.setCellStyle(rightandleftborder);
				cell.setCellValue(ps.getFeb_20().doubleValue());
				tot_unitFeb19 = ps.getNo_of_te().multiply(ps.getFeb_20());
				tot_of_pso = tot_of_pso.add(ps.getFeb_20());
				col++;
				
				
				cell = hrow.createCell(col);
				cell.setCellStyle(rightandleftborder);
				cell.setCellValue(ps.getMar_20().doubleValue());
				tot_unitMar19 = ps.getNo_of_te().multiply(ps.getMar_20());
				tot_of_pso = tot_of_pso.add(ps.getMar_20());
				col++;
				
				cell = hrow.createCell(col);
				cell.setCellStyle(rightandleftborder);
				cell.setCellValue(ps.getApr_20().doubleValue());
				tot_unitApr19 = ps.getNo_of_te().multiply(ps.getApr_20());
				tot_of_pso = tot_of_pso.add(ps.getApr_20());
				col++;
				
				cell = hrow.createCell(col);
				cell.setCellStyle(rightandleftborder);
				cell.setCellValue(ps.getMay_20().doubleValue());
				tot_unitMay19 = ps.getNo_of_te().multiply(ps.getMay_20());
				tot_of_pso = tot_of_pso.add(ps.getMay_20());
				col++;
				
			
				cell = hrow.createCell(col);
				cell.setCellStyle(rightandleftborder);
				cell.setCellValue(ps.getJun_20().doubleValue());
				tot_unitJun19 = ps.getNo_of_te().multiply(ps.getJun_20());
				tot_of_pso = tot_of_pso.add(ps.getJun_20());
				col++;
				
				cell = hrow.createCell(col);
				cell.setCellStyle(rightandleftborder);
				cell.setCellValue(ps.getJul_20().doubleValue());
				tot_unitJul19 = ps.getNo_of_te().multiply(ps.getJul_20());
				tot_of_pso = tot_of_pso.add(ps.getJul_20());
				col++;
				
				cell = hrow.createCell(col);
				cell.setCellStyle(rightandleftborder);
				cell.setCellValue(ps.getAug_20().doubleValue());
				tot_unitAug19 = ps.getNo_of_te().multiply(ps.getAug_20());
				tot_of_pso = tot_of_pso.add(ps.getAug_20());
				col++;
				
				cell = hrow.createCell(col);
				cell.setCellStyle(rightandleftborder);
				cell.setCellValue(ps.getSep_20().doubleValue());
				tot_unitSep19 = ps.getNo_of_te().multiply(ps.getSep_20());
				tot_of_pso = tot_of_pso.add(ps.getSep_20());
				col++;
				
				cell = hrow.createCell(col);
				cell.setCellStyle(rightandleftborder);
				cell.setCellValue(ps.getOct_20().doubleValue());
				tot_unitOct19 = ps.getNo_of_te().multiply(ps.getOct_20());
				tot_of_pso = tot_of_pso.add(ps.getOct_20());
				col++;
				
				cell = hrow.createCell(col);
				cell.setCellStyle(rightandleftborder);
				cell.setCellValue(ps.getNov_20().doubleValue());
				tot_unitNov19 = ps.getNo_of_te().multiply(ps.getNov_20());
				tot_of_pso = tot_of_pso.add(ps.getNov_20());
				col++;
				
				
				cell = hrow.createCell(col);
				cell.setCellStyle(rightandleftborder);
				cell.setCellValue(tot_of_pso.doubleValue());
				col++;
				
				
				row++;
				col = 0;
				hrow = (XSSFRow) wsheet.createRow(row); // row 3

				cell = hrow.createCell(col); // cell.setCellStyle(columnHeading);
				cell.setCellValue(" ");
				wsheet.addMergedRegion(new CellRangeAddress(row, row, col, col=col + 8));
				col++;

				cell = hrow.createCell(col); 
				cell.setCellStyle(rightandleftborder);
				cell.setCellValue("Total Units");
				col++;

				// formula : perpso * te = total units and cogunit * totalunit

				cell = hrow.createCell(col); 
				cell.setCellStyle(rightandleftborder);
				cell.setCellValue(tot_unitDec19.longValue());
				tot_costDec19 = tot_unitDec19.multiply(ps.getCog_rate_perunit());
				tot_of_totunit = tot_of_totunit.add(tot_unitDec19);
				col++;

				cell = hrow.createCell(col); 
				cell.setCellStyle(rightandleftborder);
				cell.setCellValue(tot_unitJan19.longValue());
				tot_of_totunit = tot_of_totunit.add(tot_unitJan19);
				tot_costJan19 = tot_unitJan19.multiply(ps.getCog_rate_perunit());
				col++;

				cell = hrow.createCell(col); 
				cell.setCellStyle(rightandleftborder);
				cell.setCellValue(tot_unitFeb19.longValue());
				tot_of_totunit = tot_of_totunit.add(tot_unitFeb19);
				tot_costFeb19 = tot_unitFeb19.multiply(ps.getCog_rate_perunit());
				col++;

				cell = hrow.createCell(col);
				cell.setCellStyle(rightandleftborder);
				cell.setCellValue(tot_unitMar19.longValue());
				tot_of_totunit = tot_of_totunit.add(tot_unitMar19);
				tot_costMar19 = tot_unitMar19.multiply(ps.getCog_rate_perunit());
				col++;

				cell = hrow.createCell(col); 
				cell.setCellStyle(rightandleftborder);
				cell.setCellValue(tot_unitApr19.longValue());
				tot_of_totunit = tot_of_totunit.add(tot_unitApr19);
				tot_costApr19 = tot_unitApr19.multiply(ps.getCog_rate_perunit());
				col++;

				cell = hrow.createCell(col); 
				cell.setCellValue(tot_unitMay19.longValue());
				cell.setCellStyle(rightandleftborder);
				tot_of_totunit = tot_of_totunit.add(tot_unitMay19);
				tot_costMay19 = tot_unitMay19.multiply(ps.getCog_rate_perunit());
				col++;

				cell = hrow.createCell(col); // cell.setCellStyle(columnHeading);
				cell.setCellStyle(rightandleftborder);
				cell.setCellValue(tot_unitJun19.longValue());
				tot_of_totunit = tot_of_totunit.add(tot_unitJun19);
				tot_costJun19 = tot_unitJun19.multiply(ps.getCog_rate_perunit());
				col++;

				cell = hrow.createCell(col); // cell.setCellStyle(columnHeading);
				cell.setCellStyle(rightandleftborder);
				cell.setCellValue(tot_unitJul19.longValue());
				tot_of_totunit = tot_of_totunit.add(tot_unitJul19);
				tot_costJul19 = tot_unitJul19.multiply(ps.getCog_rate_perunit());
				col++;

				cell = hrow.createCell(col); // cell.setCellStyle(columnHeading);
				cell.setCellStyle(rightandleftborder);
				cell.setCellValue(tot_unitAug19.longValue());
				tot_of_totunit = tot_of_totunit.add(tot_unitAug19);
				tot_costAug19 = tot_unitAug19.multiply(ps.getCog_rate_perunit());
				col++;

				cell = hrow.createCell(col); // cell.setCellStyle(columnHeading);
				cell.setCellStyle(rightandleftborder);
				cell.setCellValue(tot_unitSep19.longValue());
				tot_of_totunit = tot_of_totunit.add(tot_unitSep19);
				tot_costSep19 = tot_unitSep19.multiply(ps.getCog_rate_perunit());
				col++;

				cell = hrow.createCell(col); // cell.setCellStyle(columnHeading);
				cell.setCellStyle(rightandleftborder);
				cell.setCellValue(tot_unitOct19.longValue());
				tot_of_totunit = tot_of_totunit.add(tot_unitOct19);
				tot_costOct19 = tot_unitOct19.multiply(ps.getCog_rate_perunit());
				col++;

				cell = hrow.createCell(col); // cell.setCellStyle(columnHeading);
				cell.setCellStyle(rightandleftborder);
				cell.setCellValue(tot_unitNov19.longValue());
				tot_of_totunit = tot_of_totunit.add(tot_unitNov19);
				tot_costNov19 = tot_unitNov19.multiply(ps.getCog_rate_perunit());
				col++;

				cell = hrow.createCell(col); // cell.setCellStyle(columnHeading);
				cell.setCellStyle(rightandleftborder);
				cell.setCellValue(tot_of_totunit.longValue());
				col++;

				row++;
				col = 0;
				hrow = (XSSFRow) wsheet.createRow(row); // row 4

				cell = hrow.createCell(col); // cell.setCellStyle(columnHeading);
				cell.setCellValue(" ");
				wsheet.addMergedRegion(new CellRangeAddress(row, row, col, col=col + 8));
				col++;

				cell = hrow.createCell(col);
				 cell.setCellStyle(decimalstyle);
				cell.setCellValue("Total Cost (Rs.)");
				col++;

				cell = hrow.createCell(col); 
				 cell.setCellStyle(decimalstyle);
				cell.setCellValue(tot_costDec19.longValue());
				tot_of_totcost = tot_of_totcost.add(tot_costDec19);
				
				grnd_tot_costDec19 = grnd_tot_costDec19.add(tot_costDec19);
				col++;

				cell = hrow.createCell(col); 
				cell.setCellStyle(decimalstyle);
				cell.setCellValue(tot_costJan19.longValue());
				tot_of_totcost = tot_of_totcost.add(tot_costJan19);
				
				grnd_tot_costJan19 = grnd_tot_costJan19.add(tot_costJan19);
				col++;

				cell = hrow.createCell(col); 
				cell.setCellStyle(decimalstyle);
				cell.setCellValue(tot_costFeb19.longValue());
				tot_of_totcost = tot_of_totcost.add(tot_costFeb19);
				
				grnd_tot_costFeb19 = grnd_tot_costFeb19.add(tot_costFeb19);
				col++;

				cell = hrow.createCell(col); 
				cell.setCellStyle(decimalstyle);
				cell.setCellValue(tot_costMar19.longValue());
				tot_of_totcost = tot_of_totcost.add(tot_costMar19);
				
				grnd_tot_costMar19 = grnd_tot_costMar19.add(tot_costMar19);
				col++;

				cell = hrow.createCell(col); 
				cell.setCellStyle(decimalstyle);
				cell.setCellValue(tot_costApr19.longValue());
				tot_of_totcost = tot_of_totcost.add(tot_costApr19);
				
				grnd_tot_costApr19 = grnd_tot_costApr19.add(tot_costApr19);
				col++;

				cell = hrow.createCell(col); 
				cell.setCellStyle(decimalstyle);
				cell.setCellValue(tot_costMay19.longValue());
				tot_of_totcost = tot_of_totcost.add(tot_costMay19);
				
				grnd_tot_costMay19 = grnd_tot_costMay19.add(tot_costMay19);
				col++;

				cell = hrow.createCell(col); 
				cell.setCellStyle(decimalstyle);
				cell.setCellValue(tot_costJun19.longValue());
				tot_of_totcost = tot_of_totcost.add(tot_costJun19);
				
				grnd_tot_costJun19 = grnd_tot_costJun19.add(tot_costJun19);
				col++;

				cell = hrow.createCell(col); 
				cell.setCellStyle(decimalstyle);
				cell.setCellValue(tot_costJul19.longValue());
				tot_of_totcost = tot_of_totcost.add(tot_costJul19);
				
				grnd_tot_costJul19 = grnd_tot_costJul19.add(tot_costJul19);
				col++;

				cell = hrow.createCell(col); 
				cell.setCellStyle(decimalstyle);
				cell.setCellValue(tot_costAug19.longValue());
				tot_of_totcost = tot_of_totcost.add(tot_costAug19);
				
				grnd_tot_costAug19 = grnd_tot_costAug19.add(tot_costAug19);
				col++;

				cell = hrow.createCell(col); 
				cell.setCellStyle(decimalstyle);
				cell.setCellValue(tot_costSep19.longValue());
				tot_of_totcost = tot_of_totcost.add(tot_costSep19);
				
				grnd_tot_costSep19 = grnd_tot_costSep19.add(tot_costSep19);
				col++;

				cell = hrow.createCell(col); 
				cell.setCellStyle(decimalstyle);
				cell.setCellValue(tot_costOct19.longValue());
				tot_of_totcost = tot_of_totcost.add(tot_costOct19);
				
				grnd_tot_costOct19 = grnd_tot_costOct19.add(tot_costOct19);
				col++;

				cell = hrow.createCell(col); 
				cell.setCellStyle(decimalstyle);
				cell.setCellValue(tot_costNov19.longValue());
				tot_of_totcost = tot_of_totcost.add(tot_costNov19);
				
				grnd_tot_costNov19 = grnd_tot_costNov19.add(tot_costNov19);
				col++;

				cell = hrow.createCell(col); 
				cell.setCellStyle(decimalstyle);
				cell.setCellValue(tot_of_totcost.longValue());
				
				col++; 
				
				col =0;
				
				
				
				 tot_unitDec19 =  BigDecimal.ZERO;
				 tot_unitJan19 =  BigDecimal.ZERO;
				 tot_unitFeb19 =  BigDecimal.ZERO;
				 tot_unitMar19 =  BigDecimal.ZERO;
				 tot_unitApr19 =  BigDecimal.ZERO;
				 tot_unitMay19 =  BigDecimal.ZERO;
				 tot_unitJun19 =  BigDecimal.ZERO;
				 tot_unitJul19 =  BigDecimal.ZERO;
				 tot_unitAug19 =  BigDecimal.ZERO;
				 tot_unitSep19 =  BigDecimal.ZERO;
				 tot_unitOct19 =  BigDecimal.ZERO;
				 tot_unitNov19 =  BigDecimal.ZERO;
				 tot_of_totunit=  BigDecimal.ZERO;
				 tot_of_pso    =  BigDecimal.ZERO;
				
				 
				 tot_costDec19 = BigDecimal.ZERO;
				 tot_costJan19 = BigDecimal.ZERO;
				 tot_costFeb19 = BigDecimal.ZERO;
				 tot_costMar19 = BigDecimal.ZERO;
				 tot_costApr19 = BigDecimal.ZERO;
				 tot_costMay19 = BigDecimal.ZERO;
				 tot_costJun19 = BigDecimal.ZERO;
				 tot_costJul19 = BigDecimal.ZERO;
				 tot_costAug19 = BigDecimal.ZERO;
				 tot_costSep19 = BigDecimal.ZERO;
				 tot_costOct19 = BigDecimal.ZERO;
				 tot_costNov19 = BigDecimal.ZERO;
				 tot_of_totcost= BigDecimal.ZERO;
				 
					row++;
					col=0;
					old_team = new_team;
					
				 
			}
			
			 //Grand Total
			 hrow = (XSSFRow) wsheet.createRow(row); // blank row
				
				
				cell = hrow.createCell(col); 
				wsheet.addMergedRegion(new CellRangeAddress(row, row, col, col=col + 8));
				col++;
				
				
				for(int i = col;i<23;i++) {
				cell = hrow.createCell(col); 
				cell.setCellStyle(rightandleftborder);
				col++;
				}
				
				row++;
				col=0;
			
			hrow = (XSSFRow) wsheet.createRow(row); // Grand Total Row1
			

			
			for(int i = col;i<=8;i++) {
				cell = hrow.createCell(col); 
				cell.setCellStyle(decimalstyle5);
				col++;
				}
			
			cell = hrow.createCell(col); 
			cell.setCellStyle(decimalstyle2);
			cell.setCellValue("Grand Total (Rs.)");
			col++;
			
			cell = hrow.createCell(col); 
			cell.setCellStyle(decimalstyle2);
			cell.setCellValue(grnd_tot_costDec19.longValue());
			grnd_tot_of_grd_totcost = grnd_tot_of_grd_totcost.add(grnd_tot_costDec19);
			col++;
			
			cell = hrow.createCell(col); 
			cell.setCellStyle(decimalstyle2);
			cell.setCellValue(grnd_tot_costJan19.longValue());
			grnd_tot_of_grd_totcost = grnd_tot_of_grd_totcost.add(grnd_tot_costJan19);
			col++;
			
			cell = hrow.createCell(col); 
			cell.setCellStyle(decimalstyle2);
			cell.setCellValue(grnd_tot_costFeb19.longValue());
			grnd_tot_of_grd_totcost = grnd_tot_of_grd_totcost.add(grnd_tot_costFeb19);
			col++;
			
			cell = hrow.createCell(col); 
			cell.setCellStyle(decimalstyle2);
			cell.setCellValue(grnd_tot_costMar19.longValue());
			grnd_tot_of_grd_totcost = grnd_tot_of_grd_totcost.add(grnd_tot_costMar19);
			col++;
			
			cell = hrow.createCell(col); 
			cell.setCellStyle(decimalstyle2);
			cell.setCellValue(grnd_tot_costApr19.longValue());
			grnd_tot_of_grd_totcost = grnd_tot_of_grd_totcost.add(grnd_tot_costApr19);
			col++;
			
			cell = hrow.createCell(col); 
			cell.setCellStyle(decimalstyle2);
			cell.setCellValue(grnd_tot_costMay19.longValue());
			grnd_tot_of_grd_totcost = grnd_tot_of_grd_totcost.add(grnd_tot_costMay19);
			col++;
			
			cell = hrow.createCell(col); 
			cell.setCellStyle(decimalstyle2);
			cell.setCellValue(grnd_tot_costJun19.longValue());
			grnd_tot_of_grd_totcost = grnd_tot_of_grd_totcost.add(grnd_tot_costJun19);
			col++;
			
			cell = hrow.createCell(col); 
			cell.setCellStyle(decimalstyle2);
			cell.setCellValue(grnd_tot_costJul19.longValue());
			grnd_tot_of_grd_totcost = grnd_tot_of_grd_totcost.add(grnd_tot_costJul19);
			col++;
			
			cell = hrow.createCell(col); 
			cell.setCellStyle(decimalstyle2);
			cell.setCellValue(grnd_tot_costAug19.longValue());
			grnd_tot_of_grd_totcost = grnd_tot_of_grd_totcost.add(grnd_tot_costAug19);
			col++;
			
			cell = hrow.createCell(col); 
			cell.setCellStyle(decimalstyle2);
			cell.setCellValue(grnd_tot_costSep19.longValue());
			grnd_tot_of_grd_totcost = grnd_tot_of_grd_totcost.add(grnd_tot_costSep19);
			col++;
			
			cell = hrow.createCell(col); 
			cell.setCellStyle(decimalstyle2);
			cell.setCellValue(grnd_tot_costOct19.longValue());
			grnd_tot_of_grd_totcost = grnd_tot_of_grd_totcost.add(grnd_tot_costOct19);
			col++;
			
			cell = hrow.createCell(col); 
			cell.setCellStyle(decimalstyle2);
			cell.setCellValue(grnd_tot_costNov19.longValue());
			grnd_tot_of_grd_totcost = grnd_tot_of_grd_totcost.add(grnd_tot_costNov19);
			col++;
			
			cell = hrow.createCell(col); 
			cell.setCellStyle(decimalstyle2);
			cell.setCellValue(grnd_tot_of_grd_totcost.longValue());
			col++;
			
			

			FileOutputStream fileOut = new FileOutputStream(path.toString());
			wwbook.write(fileOut);
			fileOut.close();
			
			System.out.println("Excel Created");
			
		}
		catch (Exception e) {
			// TODO: handle exception
			System.out.println("Error Occurred :" +e.getMessage());// uncomment asneeded --;
		}
		
		return filename;
	}

}
