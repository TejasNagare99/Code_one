package com.excel.service;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.ss.usermodel.BorderStyle;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.FillPatternType;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.VerticalAlignment;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.excel.bean.FinalApprovalLogBean;
import com.excel.model.Allocation_report_1;
import com.excel.model.Allocation_report_2;
import com.excel.model.Stock_Reco_With_sfa;
import com.excel.repository.UserMasterRepository;
import com.excel.utility.CodifyErrors;
import com.excel.utility.MedicoConstants;
import com.excel.utility.MedicoResources;
import com.excel.utility.ReportFormats;

@Service
public class Allocation_Report1_ServiceImpl implements Allocation_Report1_2_Service,MedicoConstants{
	@Autowired private UserMasterService usermasterservice;

	@Autowired private UserMasterRepository usermasterrepository;
	@Override
	public String generate_Angular_allocation1_report(List<Allocation_report_1> lst, String companyname,String empId)
			throws Exception {
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
			
			filename = "Allocation_1_Report" + l + ".xlsx";
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
			wsheet.createFreezePane( 0, 4);
			
			Font font =  wwbook.createFont();
			font.setBold(true);
			font.setFontHeightInPoints((short)12);
			
			Font h_font =  wwbook.createFont();
			h_font.setBold(true);
			h_font.setFontHeightInPoints((short)14);
			
			CellStyle heading = wwbook.createCellStyle();
			heading.setFillForegroundColor(HSSFColor.HSSFColorPredefined.LIGHT_YELLOW.getIndex());
			heading.setFillPattern(FillPatternType.SOLID_FOREGROUND);
			heading.setAlignment(HorizontalAlignment.CENTER);
			heading.setFont(h_font);
			//heading.setBorderBottom(BorderStyle.THIN);
		//	heading.setBorderLeft(BorderStyle.THIN);
		//	heading.setBorderTop(BorderStyle.THIN);
		//	heading.setBorderRight(BorderStyle.THIN);
			
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
			
			  cell.setCellValue(companyname);
			  wsheet.addMergedRegion(new CellRangeAddress(row,row,0,25)); 
			  cell.setCellStyle(heading);
			 
			  row++;
			  col=0;
			  hrow = (XSSFRow) wsheet.createRow(row);
			 
			  cell = hrow.createCell(col);
			  cell.setCellValue("PROMOTIONAL INPUTS SPECIAL/MEETING/EMERGENCY  DISPATCHES REVIEW :"+todaydate);
			  wsheet.addMergedRegion(new CellRangeAddress(row,row,0,25)); 
			  cell.setCellStyle(heading);
			
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
			  cell.setCellValue("Requested By");
			  wsheet.addMergedRegion(new CellRangeAddress(row,row+1,col,col));  
			  cell.setCellStyle(header2);
			  col++;
			  
			  cell = hrow.createCell(col);
			  cell.setCellValue("Requested Date");
			  wsheet.addMergedRegion(new CellRangeAddress(row,row+1,col,col));  
			  cell.setCellStyle(header2);
			  col++;
			  
			  cell = hrow.createCell(col);
			  cell.setCellValue("Allocated To");
			  wsheet.addMergedRegion(new CellRangeAddress(row,row,col, col+2));  
			  cell.setCellStyle(header2);
			  col++;
			  
			  cell = hrow.createCell(col);
			  cell.setCellStyle(header2);
			  col++;
			  
			  cell = hrow.createCell(col);
			  cell.setCellStyle(header2);
			  col++;
			  
			  cell = hrow.createCell(col);
			  cell.setCellValue("Meeting Date");
			  wsheet.addMergedRegion(new CellRangeAddress(row,row+1,col,col));  
			  cell.setCellStyle(header2);
			  col++;
			  
			  cell = hrow.createCell(col);
			  cell.setCellValue("Meeting Name if Any");
			  wsheet.addMergedRegion(new CellRangeAddress(row,row+1,col,col));  
			  cell.setCellStyle(header2);
			  col++;
			  
			  cell = hrow.createCell(col);
			  cell.setCellValue("Product Code");
			  wsheet.addMergedRegion(new CellRangeAddress(row,row+1,col,col));  
			  cell.setCellStyle(header2);
			  col++;
			  
			  cell = hrow.createCell(col);
			  cell.setCellValue("Item Dispatched");
			  wsheet.addMergedRegion(new CellRangeAddress(row,row+1,col,col));  
			  cell.setCellStyle(header2);
			  col++;
			  
			  cell = hrow.createCell(col);
			  cell.setCellValue("Requested Qty");
			  wsheet.addMergedRegion(new CellRangeAddress(row,row+1,col,col));  
			  cell.setCellStyle(header2);
			  col++;
			  
			  cell = hrow.createCell(col);
			  cell.setCellValue("Approved Qty");
			  wsheet.addMergedRegion(new CellRangeAddress(row,row+1,col,col));  
			  cell.setCellStyle(header2);
			  col++;
			  
			  cell = hrow.createCell(col);
			  cell.setCellValue("Dispatched Qty");
			  wsheet.addMergedRegion(new CellRangeAddress(row,row+1,col,col));  
			  cell.setCellStyle(header2);
			  col++;
			  
			  cell = hrow.createCell(col);
			  cell.setCellValue("Challan no");
			  wsheet.addMergedRegion(new CellRangeAddress(row,row+1,col,col));  
			  cell.setCellStyle(header2);
			  col++;
			  
			  cell = hrow.createCell(col);
			  cell.setCellValue("Challan Date");
			  wsheet.addMergedRegion(new CellRangeAddress(row,row+1,col,col));  
			  cell.setCellStyle(header2);
			  col++;
			  
			  cell = hrow.createCell(col);
			  cell.setCellValue("No of Cases");
			  wsheet.addMergedRegion(new CellRangeAddress(row,row+1,col,col));  
			  cell.setCellStyle(header2);
			  col++;
			  
			  cell = hrow.createCell(col);
			  cell.setCellValue("Dispatch Status");
			  wsheet.addMergedRegion(new CellRangeAddress(row,row+1,col,col));  
			  cell.setCellStyle(header2);
			  col++;
			  
			  cell = hrow.createCell(col);
			  cell.setCellValue("Tentative delivery Timelines");
			  wsheet.addMergedRegion(new CellRangeAddress(row,row+1,col,col));  
			  cell.setCellStyle(header2);
			  col++;
			  
			  
			  cell = hrow.createCell(col);
			  cell.setCellValue("Courier Name");
			  wsheet.addMergedRegion(new CellRangeAddress(row,row+1,col,col));  
			  cell.setCellStyle(header2);
			  col++;
			  
			  cell = hrow.createCell(col);
			  cell.setCellValue("Mode");
			  wsheet.addMergedRegion(new CellRangeAddress(row,row+1,col,col));  
			  cell.setCellStyle(header2);
			  col++;
			  
			  cell = hrow.createCell(col);
			  cell.setCellValue("Lr No");
			  wsheet.addMergedRegion(new CellRangeAddress(row,row+1,col,col));  
			  cell.setCellStyle(header2);
			  col++;
			  
			  cell = hrow.createCell(col);
			  cell.setCellValue("Lr Date");
			  wsheet.addMergedRegion(new CellRangeAddress(row,row+1,col,col));  
			  cell.setCellStyle(header2);
			  col++;

			  cell = hrow.createCell(col);
			  cell.setCellValue("Delivery Date");
			  wsheet.addMergedRegion(new CellRangeAddress(row,row+1,col,col));  
			  cell.setCellStyle(header2);
			  col++;
			  
			  cell = hrow.createCell(col);
			  cell.setCellValue("POD Details");
			  wsheet.addMergedRegion(new CellRangeAddress(row,row+1,col,col));  
			  cell.setCellStyle(header2);
			  col++;
			  
			  cell = hrow.createCell(col);
			  cell.setCellValue("Remarks");
			  wsheet.addMergedRegion(new CellRangeAddress(row,row+1,col,col));  
			  cell.setCellStyle(header2);
			  col++;
			  
			  col=0;
				 // col=7;
			 row ++;
			 hrow = (XSSFRow) wsheet.createRow(row);
			 
			 for(int i=1;i<=4;i++) {
				  cell = hrow.createCell(col);  
				  cell.setCellValue(" ");
				  cell.setCellStyle(header);
				  col++;
				  }
			 
			  cell = hrow.createCell(col);
			  cell.setCellValue("Empl No");
			  cell.setCellStyle(header);
			  col++;
			  
			  cell = hrow.createCell(col);
			  cell.setCellValue("Name of collegue/others");
			  cell.setCellStyle(header);
			  col++;
			  
			  cell = hrow.createCell(col);
			  cell.setCellValue("Destination");
			  cell.setCellStyle(header);
			  col++;
			  
			  
			  for(int i=1;i<=19;i++) {
				  cell = hrow.createCell(col);  
				  cell.setCellValue(" ");
				  cell.setCellStyle(header);
				  col++;
				  }
			  
			  
			  
			 col=0;
			 row ++;
			 hrow = (XSSFRow) wsheet.createRow(row);
			 
			 String old_team = "old";
			 String new_team = "";
			for(Allocation_report_1 alloc : lst) {
				 new_team = alloc.getTeam_name();		 
				 hrow = (XSSFRow) wsheet.createRow(row); 
				 
				 if(!new_team.equals(old_team) && !old_team.equals("old")) {
					  cell = hrow.createCell(col);
					  cell.setCellValue(" ");
					  wsheet.addMergedRegion(new CellRangeAddress(row,row,col,col+25));  
					  cell.setCellStyle(data);
					  col++;
					  
						/*
						 * for(int i=1;i<=20;i++) { cell = hrow.createCell(col); cell.setCellValue(" ");
						 * cell.setCellStyle(data); col++; }
						 */
					  
					  col=0;
					  row++;
					  hrow = (XSSFRow) wsheet.createRow(row); 
				}
				 
				 
				 
				  cell = hrow.createCell(col);
				  cell.setCellValue(alloc.getBu());
				  cell.setCellStyle(data2);
				  col++;
				  
				  cell = hrow.createCell(col);
				  cell.setCellValue(alloc.getTeam_name());
				  cell.setCellStyle(data2);
				  col++;
				  
				  cell = hrow.createCell(col);
				  cell.setCellValue(alloc.getRequest_by());
				  cell.setCellStyle(data2);
				  col++;
				  
				  cell = hrow.createCell(col);
				  cell.setCellValue(alloc.getReq_date());
				  cell.setCellStyle(data);
				  col++;
				  
				  cell = hrow.createCell(col);
				  cell.setCellValue(alloc.getEmpl_no());
				  cell.setCellStyle(data);
				  col++;
				  
				  cell = hrow.createCell(col);
				  cell.setCellValue(alloc.getName_of_colleg());
				  cell.setCellStyle(data2);
				  col++;
				  
				  cell = hrow.createCell(col);
				  cell.setCellValue(alloc.getDestination());
				  cell.setCellStyle(data2);
				  col++;
				  
				  cell = hrow.createCell(col);
				  cell.setCellValue(alloc.getMeeting_date());
				  cell.setCellStyle(data);
				  col++;
				  
				  cell = hrow.createCell(col);
				  cell.setCellValue(alloc.getMeeting_name());
				  cell.setCellStyle(data2);
				  col++;
				  
				  
				  cell = hrow.createCell(col);
				  cell.setCellValue(alloc.getProd_code());
				  cell.setCellStyle(data);
				  col++;
				  
				  cell = hrow.createCell(col);
				  cell.setCellValue(alloc.getItem_dispatched());
				  cell.setCellStyle(data2);
				  col++;
				  
				  cell = hrow.createCell(col);
				  cell.setCellValue(alloc.getRequest_qty().doubleValue());
				  cell.setCellStyle(numeric);
				  col++;
				  
				  cell = hrow.createCell(col);
				  cell.setCellValue(alloc.getAlloc_qty().doubleValue());
				  cell.setCellStyle(numeric);
				  col++;
				  
				  cell = hrow.createCell(col);
				  cell.setCellValue(alloc.getDispatch_qty().doubleValue());
				  cell.setCellStyle(numeric);
				  col++;
				  
				  cell = hrow.createCell(col);
				  cell.setCellValue(alloc.getChallan_no());
				  cell.setCellStyle(data2);
				  col++;
				  
				  cell = hrow.createCell(col);
				  cell.setCellValue(alloc.getChallan_date());
				  cell.setCellStyle(data);
				  col++;
				  
				  cell = hrow.createCell(col);
				  cell.setCellValue(alloc.getNo_of_cases());
				  cell.setCellStyle(numeric);
				  col++;
				  
				  cell = hrow.createCell(col);
				  cell.setCellValue(alloc.getDispatch_status());
				  cell.setCellStyle(data2);
				  col++;
				  
				  cell = hrow.createCell(col);
				  cell.setCellValue(alloc.getTime_line());
				  cell.setCellStyle(data2);
				  col++;
				  				  
				  cell = hrow.createCell(col);
				  cell.setCellValue(alloc.getCourier_name());
				  cell.setCellStyle(data2);
				  col++;
				  
				  cell = hrow.createCell(col);
				  cell.setCellValue(alloc.getMode());
				  cell.setCellStyle(data2);
				  col++;
				  
				  cell = hrow.createCell(col);
				  cell.setCellValue(alloc.getLr_no());
				  cell.setCellStyle(data2);
				  col++;
				  
				  cell = hrow.createCell(col);
				  cell.setCellValue(alloc.getLr_date());
				  cell.setCellStyle(data);
				  col++;
				  
				  cell = hrow.createCell(col);
				  cell.setCellValue(alloc.getDelivery_date());
				  cell.setCellStyle(data);
				  col++;
				  
				  cell = hrow.createCell(col);
				  cell.setCellValue(alloc.getPod_detail());
				  cell.setCellStyle(data2);
				  col++;
				  
				  cell = hrow.createCell(col);
				  cell.setCellValue(alloc.getRemark());
				  cell.setCellStyle(data2);
				  col++;
				  
				  col = 0;
				  row++;
				  old_team = new_team;
			}
			
			 FileOutputStream fileOut = new FileOutputStream(path.toString());
			 wwbook.write(fileOut);
			 
				filename=usermasterservice.generateExcellock(path.toString(), filename,empId,".xlsx");

			    fileOut.close();
			    wwbook.close();
		
			System.out.println("Excel Created");
		}
		catch (Exception e) {
			// TODO: handle exception
			throw e;
		}
		return filename;
	}

	public String generate_Stock_Reco_SFA_report(List<Stock_Reco_With_sfa> lst) {
		String filename = "Stock_Reco_SFA" + new Date().getTime() + ".xlsx";
		String filepath = REPORT_FILE_PATH + filename;
		System.out.println("file path :::" + filepath);
		XSSFWorkbook workbook = null;

		try {
			Date today = new Date();

			SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");

			String todaydate = sdf.format(today);
			workbook = new XSSFWorkbook();
			File f = new File(REPORT_FILE_PATH);
			if (!f.exists()) {
				if (f.mkdirs()) {
				} else {
					throw new RuntimeException("Could not create directory");
				}
			}
			XSSFSheet sheet = workbook.createSheet("Stock_Reco_SFA");

			List<String> headings = new ArrayList<>();
			headings.add("Sr No.");
			headings.add("Division");
			headings.add("Product Id");
			headings.add("Product Code");
			headings.add("ERP Product Code");
			headings.add("Product Type");
			headings.add("Product Name");
			headings.add("Opening Stock Qty.");
			headings.add("In Stock Qty.");
			headings.add("Out Stock Qty.");
			headings.add("WithHeld Qty.");
			headings.add("SG CL Stock Qty.");
			headings.add("SG CL Stock Value");
			headings.add("STGET CL Stock Qty.");
			headings.add("STGET CL Stock Value");

			XSSFRow row = null;
			XSSFCell cell = null;
			int rowCount = 0;
			int colCount = 0;

			Font font = workbook.createFont();

			font.setBold(true);

			XSSFCellStyle headingCellStyle = ReportFormats.heading2(workbook);
			XSSFCellStyle columnHeadingStyle = ReportFormats.columnHeadingGrey(workbook);
			XSSFCellStyle cellAlignment = ReportFormats.setCellAlignment(workbook, ALIGN_RIGHT);
			cellAlignment.setDataFormat(workbook.createDataFormat().getFormat("0.00"));

			CellStyle decimalstyle2 = workbook.createCellStyle();
			decimalstyle2.setDataFormat(workbook.createDataFormat().getFormat("0.00"));
			// decimalstyle2.setDataFormat(wwbook.createDataFormat().getFormat("0.00"));
			decimalstyle2.setFillForegroundColor(IndexedColors.GREY_25_PERCENT.index);
			decimalstyle2.setFillPattern(FillPatternType.SOLID_FOREGROUND);
			decimalstyle2.setAlignment(HorizontalAlignment.RIGHT);
			decimalstyle2.setFont(font);
			decimalstyle2.setBorderLeft(BorderStyle.THIN);
			decimalstyle2.setBorderRight(BorderStyle.THIN);
			decimalstyle2.setBorderBottom(BorderStyle.THIN);

			row = sheet.createRow(rowCount);
			cell = row.createCell(colCount);
			sheet.addMergedRegion(new CellRangeAddress(rowCount, rowCount, colCount, colCount + headings.size() - 1));
			cell.setCellValue("Stock Reconciliation with SFA as on " + todaydate);
			cell.setCellStyle(headingCellStyle);

			rowCount += 1;
			row = sheet.createRow(rowCount);
			colCount = 0;
			for (String heading : headings) {
				System.out.println(heading);
				cell = ReportFormats.cell(row, colCount, heading, columnHeadingStyle);
				colCount++;
			}
			sheet.groupRow(0, 2);
			sheet.createFreezePane(0, 2);
			rowCount += 1;
			BigDecimal total[] = new BigDecimal[2];
			Arrays.fill(total, BigDecimal.ZERO);
			for (Stock_Reco_With_sfa data : lst) {

				colCount = 0;

				row = sheet.createRow(rowCount);

				cell = ReportFormats.cell(row, colCount, String.valueOf(data.getRow()), null);
				colCount++;

				cell = ReportFormats.cell(row, colCount, data.getDiv_disp_nm(), null);
				colCount++;

				cell = ReportFormats.cell(row, colCount, String.valueOf(data.getSmp_prod_id()), null);
				colCount++;

				cell = ReportFormats.cell(row, colCount, data.getSmp_prod_cd(), null);
				colCount++;

				cell = ReportFormats.cell(row, colCount, data.getSmp_erp_prod_cd(), null);
				colCount++;

				cell = ReportFormats.cell(row, colCount, data.getSmp_prod_type(), null);
				colCount++;

				cell = ReportFormats.cell(row, colCount, data.getSmp_prod_name(), null);
				colCount++;

				cell = ReportFormats.cellNum(row, colCount, Double.valueOf(data.getBatch_open_stock().toString()),
						cellAlignment);
				colCount++;

				cell = ReportFormats.cellNum(row, colCount, Double.valueOf(data.getBatch_in_stock().toString()),
						cellAlignment);
				colCount++;

				cell = ReportFormats.cellNum(row, colCount, Double.valueOf(data.getBatch_out_stock().toString()),
						cellAlignment);
				colCount++;

				cell = ReportFormats.cellNum(row, colCount, Double.valueOf(data.getBatch_with_held_qty().toString()),
						cellAlignment);
				colCount++;

				cell = ReportFormats.cellNum(row, colCount, Double.valueOf(data.getSg_cl_stock_qty().toString()),
						cellAlignment);
				colCount++;

				cell = ReportFormats.cellNum(row, colCount, Double.valueOf(data.getSg_cl_stock_val().toString()),
						cellAlignment);
				colCount++;
				System.out.println(data.getSg_cl_stock_val());
				total[0] = total[0].add(data.getSg_cl_stock_val());
//						: total[0].add(BigDecimal.ZERO);
				cell = ReportFormats.cellNum(row, colCount, Double.valueOf(data.getStget_cl_stock_qty().toString()),
						cellAlignment);
				colCount++;

				cell = ReportFormats.cellNum(row, colCount, Double.valueOf(data.getStget_cl_stock_val().toString()),
						cellAlignment);
				colCount++;
				rowCount++;
				total[1] = data.getStget_cl_stock_val()!=null?total[1].add(data.getStget_cl_stock_val()): total[1].add(BigDecimal.ZERO);
			}

			colCount = 0;

			row = sheet.createRow(rowCount);
			cell = row.createCell(colCount);
			cell.setCellValue("Grand Total : ");
			cell.setCellStyle(decimalstyle2);
			sheet.addMergedRegion(new CellRangeAddress(rowCount, rowCount, colCount, colCount + 11));
			colCount += 12;
			
			cell = ReportFormats.cellNum(row, colCount,  Double.valueOf(total[0].toString()), cellAlignment);
			cell.setCellStyle(decimalstyle2);
			colCount++;
			cell = ReportFormats.cell(row, colCount,  "", cellAlignment);
			cell.setCellStyle(decimalstyle2);
			colCount++;
			cell = ReportFormats.cellNum(row, colCount,  Double.valueOf(total[1].toString()), cellAlignment);
			cell.setCellStyle(decimalstyle2);
			colCount++;
			FileOutputStream fileOutputStream = new FileOutputStream(filepath);
			workbook.write(fileOutputStream);
		} catch (Exception e) {
			System.out.println("Error Occurred :" +e.getMessage());// uncomment asneeded --;
		} finally {
			if (workbook != null) {
				try {
					workbook.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					System.out.println("Error Occurred :" +e.getMessage());// uncomment asneeded --;
				}
			}
		}

		return filename;
	}
	
	@Override
	public String generate_Angular_allocation2_report(List<Allocation_report_2> lst, String companyname,String empId)
			throws Exception {
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
				filename = "Allocation_2_Report" + l +".xlsx";
				String filePath = MedicoConstants.REPORT_FILE_PATH;
				 path = new StringBuffer(filePath).append("\\");
				path.append(filename);

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
				wsheet.createFreezePane( 0, 4);
				
				Font font =  wwbook.createFont();
				font.setBold(true);
				font.setFontHeightInPoints((short)12);
				
				Font h_font =  wwbook.createFont();
				h_font.setBold(true);
				h_font.setFontHeightInPoints((short)14);
				
				CellStyle heading = wwbook.createCellStyle();
				heading.setFillForegroundColor(HSSFColor.HSSFColorPredefined.LIGHT_YELLOW.getIndex());
				heading.setFillPattern(FillPatternType.SOLID_FOREGROUND);
				heading.setAlignment(HorizontalAlignment.CENTER);
				heading.setFont(h_font);
				//heading.setBorderBottom(BorderStyle.THIN);
			//	heading.setBorderLeft(BorderStyle.THIN);
			//	heading.setBorderTop(BorderStyle.THIN);
			//	heading.setBorderRight(BorderStyle.THIN);
				
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
				
				  cell.setCellValue(companyname);
				  wsheet.addMergedRegion(new CellRangeAddress(row,row,0,25)); 
				  cell.setCellStyle(heading);
				 
				  row++;
				  col=0;
				  hrow = (XSSFRow) wsheet.createRow(row);
				 
				  cell = hrow.createCell(col);
				  cell.setCellValue("PRODUCT SAMPLE SPECIAL DISPATCHES REVIEW : "+todaydate);
				  wsheet.addMergedRegion(new CellRangeAddress(row,row,0,25)); 
				  cell.setCellStyle(heading);
				
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
				  cell.setCellValue("Request No");
				  wsheet.addMergedRegion(new CellRangeAddress(row,row+1,col,col));  
				  cell.setCellStyle(header2);
				  col++;
				  
				  cell = hrow.createCell(col);
				  cell.setCellValue("Requested By");
				  wsheet.addMergedRegion(new CellRangeAddress(row,row+1,col,col));  
				  cell.setCellStyle(header2);
				  col++;
				  
				  cell = hrow.createCell(col);
				  cell.setCellValue("Approval Status");
				  wsheet.addMergedRegion(new CellRangeAddress(row,row+1,col,col));  
				  cell.setCellStyle(header2);
				  col++;
				  
				  cell = hrow.createCell(col);
				  cell.setCellValue("Requested Date");
				  wsheet.addMergedRegion(new CellRangeAddress(row,row+1,col,col));  
				  cell.setCellStyle(header2);
				  col++;
				  
				  cell = hrow.createCell(col);
				  cell.setCellValue("Allocated To");
				  wsheet.addMergedRegion(new CellRangeAddress(row,row,col, col+2));  
				  cell.setCellStyle(header2);
				  col++;
				  
				  cell = hrow.createCell(col);
				  cell.setCellStyle(header2);
				  col++;
				  
				  cell = hrow.createCell(col);
				  cell.setCellStyle(header2);
				  col++;
				  
				  
				  cell = hrow.createCell(col);
				  cell.setCellValue("Product Code - Fcode");
				  wsheet.addMergedRegion(new CellRangeAddress(row,row+1,col,col));  
				  cell.setCellStyle(header2);
				  col++;
				  
				  cell = hrow.createCell(col);
				  cell.setCellValue("Item Dispatched");
				  wsheet.addMergedRegion(new CellRangeAddress(row,row+1,col,col));  
				  cell.setCellStyle(header2);
				  col++;
				  
				  cell = hrow.createCell(col);
				  cell.setCellValue("Requested Qty");
				  wsheet.addMergedRegion(new CellRangeAddress(row,row+1,col,col));  
				  cell.setCellStyle(header2);
				  col++;
				  
				  cell = hrow.createCell(col);
				  cell.setCellValue("Approved Qty");
				  wsheet.addMergedRegion(new CellRangeAddress(row,row+1,col,col));  
				  cell.setCellStyle(header2);
				  col++;
				  
				  cell = hrow.createCell(col);
				  cell.setCellValue("Dispatched Qty");
				  wsheet.addMergedRegion(new CellRangeAddress(row,row+1,col,col));  
				  cell.setCellStyle(header2);
				  col++;
				  			  
				  cell = hrow.createCell(col);
				  cell.setCellValue("Batch No");
				  wsheet.addMergedRegion(new CellRangeAddress(row,row+1,col,col));  
				  cell.setCellStyle(header2);
				  col++;
				  
				  cell = hrow.createCell(col);
				  cell.setCellValue("Exp Date");
				  wsheet.addMergedRegion(new CellRangeAddress(row,row+1,col,col));  
				  cell.setCellStyle(header2);
				  col++;
				  
				  cell = hrow.createCell(col);
				  cell.setCellValue("Challan no");
				  wsheet.addMergedRegion(new CellRangeAddress(row,row+1,col,col));  
				  cell.setCellStyle(header2);
				  col++;
				  
				  cell = hrow.createCell(col);
				  cell.setCellValue("Challan date ");
				  wsheet.addMergedRegion(new CellRangeAddress(row,row+1,col,col));  
				  cell.setCellStyle(header2);
				  col++;
				  
				  cell = hrow.createCell(col);
				  cell.setCellValue("Dispatch Status");
				  wsheet.addMergedRegion(new CellRangeAddress(row,row+1,col,col));  
				  cell.setCellStyle(header2);
				  col++;
				  
				  cell = hrow.createCell(col);
				  cell.setCellValue("No of Cases");
				  wsheet.addMergedRegion(new CellRangeAddress(row,row+1,col,col));  
				  cell.setCellStyle(header2);
				  col++;
				  
				  cell = hrow.createCell(col);
				  cell.setCellValue("Tentative Delivery Timelines");
				  wsheet.addMergedRegion(new CellRangeAddress(row,row+1,col,col));  
				  cell.setCellStyle(header2);
				  col++;
				  
				  cell = hrow.createCell(col);
				  cell.setCellValue("Courier Name");
				  wsheet.addMergedRegion(new CellRangeAddress(row,row+1,col,col));  
				  cell.setCellStyle(header2);
				  col++;
				  
				  cell = hrow.createCell(col);
				  cell.setCellValue("Mode");
				  wsheet.addMergedRegion(new CellRangeAddress(row,row+1,col,col));  
				  cell.setCellStyle(header2);
				  col++;
				  
				  cell = hrow.createCell(col);
				  cell.setCellValue("LR No");
				  wsheet.addMergedRegion(new CellRangeAddress(row,row+1,col,col));  
				  cell.setCellStyle(header2);
				  col++;
				  
				  cell = hrow.createCell(col);
				  cell.setCellValue("LR Dated");
				  wsheet.addMergedRegion(new CellRangeAddress(row,row+1,col,col));  
				  cell.setCellStyle(header2);
				  col++;
				  
				  cell = hrow.createCell(col);
				  cell.setCellValue("Delivery Date");
				  wsheet.addMergedRegion(new CellRangeAddress(row,row+1,col,col));  
				  cell.setCellStyle(header2);
				  col++;
				  
				  cell = hrow.createCell(col);
				  cell.setCellValue("POD Details");
				  wsheet.addMergedRegion(new CellRangeAddress(row,row+1,col,col));  
				  cell.setCellStyle(header2);
				  col++;
				  
				  cell = hrow.createCell(col);
				  cell.setCellValue("Remak");
				  wsheet.addMergedRegion(new CellRangeAddress(row,row+1,col,col));  
				  cell.setCellStyle(header2);
				  col++;
				  
				  
				  
				  
				  
				     col=0;
					 row ++;
					 hrow = (XSSFRow) wsheet.createRow(row);
					 
					 
					 for(int i=1;i<=6;i++) {
						  cell = hrow.createCell(col);  
						  cell.setCellStyle(header);
						  col++;
						  }
					 
					  cell = hrow.createCell(col);
					  cell.setCellValue("MCL No");
					  cell.setCellStyle(header);
					  col++;
					  
					  cell = hrow.createCell(col);
					  cell.setCellValue("Name of collegue/Doctor");
					  cell.setCellStyle(header);
					  col++;
					  
					  cell = hrow.createCell(col);
					  cell.setCellValue("Destination");
					  cell.setCellStyle(header);
					  col++;
					  
					  
					  for(int i=1;i<=19;i++) {
						  cell = hrow.createCell(col);  
						  cell.setCellStyle(header);
						  col++;
						  }
					  
					  row++;
					  col =0;
					 
					 String old_team = "old";
					 String new_team = "";
				  for(Allocation_report_2 alloc : lst) {
					  new_team = alloc.getTeam_name();		 
						 hrow = (XSSFRow) wsheet.createRow(row); 
						 
						 if(!new_team.equals(old_team) && !old_team.equals("old")) {
							  cell = hrow.createCell(col);
							  cell.setCellValue(" ");
							  wsheet.addMergedRegion(new CellRangeAddress(row,row,col,col+25));  
							  cell.setCellStyle(data);
							  col++;
							  
								/*
								 * for(int i=1;i<=20;i++) { cell = hrow.createCell(col); cell.setCellValue(" ");
								 * cell.setCellStyle(data); col++; }
								 */
							  
							  col=0;
							  row++;
							  hrow = (XSSFRow) wsheet.createRow(row); 
						}
						 
						  cell = hrow.createCell(col);
						  cell.setCellValue(alloc.getBu());
						  cell.setCellStyle(data2);
						  col++;
						  						  
						  cell = hrow.createCell(col);
						  cell.setCellValue(alloc.getTeam_name());
						  cell.setCellStyle(data2);
						  col++;
						  
						  cell = hrow.createCell(col);
						  cell.setCellValue(alloc.getRequest_no());
						  cell.setCellStyle(data2);
						  col++;
						  
						  cell = hrow.createCell(col);
						  cell.setCellValue(alloc.getRequest_by());
						  cell.setCellStyle(data2);
						  col++;
						  
						  cell = hrow.createCell(col);
						  cell.setCellValue(alloc.getReq_appr_status());
						  cell.setCellStyle(data2);
						  col++;
						  
						  cell = hrow.createCell(col);
						  cell.setCellValue(alloc.getReq_date());
						  cell.setCellStyle(data);
						  col++;
						  
						  cell = hrow.createCell(col);
						  cell.setCellValue(alloc.getMcl_no());
						  cell.setCellStyle(data2);
						  col++;
						  
						  cell = hrow.createCell(col);
						  cell.setCellValue(alloc.getName_of_doctor());
						  cell.setCellStyle(data2);
						  col++;
						  
						  cell = hrow.createCell(col);
						  cell.setCellValue(alloc.getDestination());
						  cell.setCellStyle(data2);
						  col++;
						  						  
						  cell = hrow.createCell(col);
						  cell.setCellValue(alloc.getProd_code());
						  cell.setCellStyle(data2);
						  col++;
						  
						  cell = hrow.createCell(col);
						  cell.setCellValue(alloc.getItem_dispatched());
						  cell.setCellStyle(data2);
						  col++;
						  
						  cell = hrow.createCell(col);
						  cell.setCellValue(alloc.getRequest_qty().doubleValue());
						  cell.setCellStyle(numeric);
						  col++;
						  
						  cell = hrow.createCell(col);
						  cell.setCellValue(alloc.getAlloc_qty().doubleValue());
						  cell.setCellStyle(numeric);
						  col++;
						  
						  cell = hrow.createCell(col);
						  cell.setCellValue(alloc.getDispatch_qty().doubleValue());
						  cell.setCellStyle(numeric);
						  col++;
						  
						  cell = hrow.createCell(col);
						  cell.setCellValue(alloc.getBatch_no());
						  cell.setCellStyle(data2);
						  col++;
						  
						  cell = hrow.createCell(col);
						  cell.setCellValue(alloc.getExpiry_date());
						  cell.setCellStyle(data);
						  col++;
						  
						  cell = hrow.createCell(col);
						  cell.setCellValue(alloc.getChallan_no());
						  cell.setCellStyle(data2);
						  col++;
						  
						  cell = hrow.createCell(col);
						  cell.setCellValue(alloc.getChallan_date());
						  cell.setCellStyle(data);
						  col++;
						  
						  cell = hrow.createCell(col);
						  cell.setCellValue(alloc.getDispatch_status());
						  cell.setCellStyle(data2);
						  col++;
						  
						  cell = hrow.createCell(col);
						  cell.setCellValue(alloc.getNo_of_cases());
						  cell.setCellStyle(data2);
						  col++;
						  
						  cell = hrow.createCell(col);
						  cell.setCellValue(alloc.getTime_line());
						  cell.setCellStyle(data2);
						  col++;
						  
						  cell = hrow.createCell(col);
						  cell.setCellValue(alloc.getCourier_name());
						  cell.setCellStyle(data2);
						  col++;
						  
						  cell = hrow.createCell(col);
						  cell.setCellValue(alloc.getMode());
						  cell.setCellStyle(data2);
						  col++;
						  
						  cell = hrow.createCell(col);
						  cell.setCellValue(alloc.getLr_no());
						  cell.setCellStyle(data2);
						  col++;
						  
						  cell = hrow.createCell(col);
						  cell.setCellValue(alloc.getLr_date());
						  cell.setCellStyle(data);
						  col++;
						  
						  cell = hrow.createCell(col);
						  cell.setCellValue(alloc.getDelivery_date());
						  cell.setCellStyle(data);
						  col++;
						  
						  cell = hrow.createCell(col);
						  cell.setCellValue(alloc.getPod_detail());
						  cell.setCellStyle(data2);
						  col++;
						
						  cell = hrow.createCell(col);
						  cell.setCellValue(alloc.getRemark());
						  cell.setCellStyle(data2);
						  col++;
						  
						  row++;
						  col = 0;
						  old_team = new_team;
				  }
				  
				  FileOutputStream fileOut = new FileOutputStream(path.toString());
					 wwbook.write(fileOut);
					 
						filename=usermasterservice.generateExcellock(path.toString(), filename,empId,".xlsx");

					    fileOut.close();
					    wwbook.close();
				
					System.out.println("Excel Created");
				  
			}
			catch (Exception e) {
				throw e;
			}
			
			return filename;
		}
	
	@Override
	public String generate_final_approval_log_report(List<FinalApprovalLogBean> lst) {
		
		String filename = "Final Approval Log" + new Date().getTime() + ".xlsx";
		String filepath = REPORT_FILE_PATH + filename;
		System.out.println("file path :::" + filepath);
		XSSFWorkbook workbook = null;
		
		try {
			Date today = new Date();

			SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");

			String todaydate = sdf.format(today);
			workbook = new XSSFWorkbook();
			File f = new File(REPORT_FILE_PATH);
			if (!f.exists()) {
				if (f.mkdirs()) {
				} else {
					throw new RuntimeException("Could not create directory");
				}
			}
			XSSFSheet sheet = workbook.createSheet("Final_Approval_Log");

			List<String> headings = new ArrayList<>();
			headings.add("Transaction Type");
			headings.add("Approval Type");
			headings.add("Appr Req No.");
			headings.add("Request Date");
			headings.add("Product Type");
			headings.add("Employee ID");
			headings.add("Employee Name");
			headings.add("Email ID of Approver");
			headings.add("Approval Status");
			headings.add("Approval Received from IP Address");
			headings.add("Approver User ID");
			headings.add("Date & Time at which Approver Received Email");
			headings.add("Date & Time at which Approver made the approval");
			headings.add("Transaction Ref");

			XSSFRow row = null;
			XSSFCell cell = null;
			int rowCount = 0;
			int colCount = 0;

			Font font = workbook.createFont();

			font.setBold(true);

			XSSFCellStyle headingCellStyle = ReportFormats.heading2WithColor(workbook,IndexedColors.PALE_BLUE.index);
			XSSFCellStyle columnHeadingStyle = ReportFormats.columnHeadingLightBlue(workbook);
			XSSFCellStyle cellAlignment = ReportFormats.setCellAlignment(workbook, ALIGN_RIGHT);
			cellAlignment.setDataFormat(workbook.createDataFormat().getFormat("0.00"));

			CellStyle decimalstyle2 = workbook.createCellStyle();
			decimalstyle2.setDataFormat(workbook.createDataFormat().getFormat("0.00"));
			// decimalstyle2.setDataFormat(wwbook.createDataFormat().getFormat("0.00"));
			decimalstyle2.setFillForegroundColor(IndexedColors.GREY_25_PERCENT.index);
			decimalstyle2.setFillPattern(FillPatternType.SOLID_FOREGROUND);
			decimalstyle2.setAlignment(HorizontalAlignment.RIGHT);
			decimalstyle2.setFont(font);
			decimalstyle2.setBorderLeft(BorderStyle.THIN);
			decimalstyle2.setBorderRight(BorderStyle.THIN);
			decimalstyle2.setBorderBottom(BorderStyle.THIN);

			row = sheet.createRow(rowCount);
			cell = row.createCell(colCount);
			sheet.addMergedRegion(new CellRangeAddress(rowCount, rowCount, colCount, colCount + headings.size() - 1));
			cell.setCellValue("Medico SG - Approval Log as on " + todaydate);
			cell.setCellStyle(headingCellStyle);

			rowCount += 1;
			row = sheet.createRow(rowCount);
			colCount = 0;
			for (String heading : headings) {
				System.out.println(heading);
				cell = ReportFormats.cell(row, colCount, heading, columnHeadingStyle);
				
				colCount++;
			}
			sheet.groupRow(0, 2);
			sheet.createFreezePane(0, 2);
			rowCount += 1;
			BigDecimal total[] = new BigDecimal[2];
			Arrays.fill(total, BigDecimal.ZERO);
			
			String oldApprReqNo = "";
			String newApprReqNo = "";
			boolean firstRow = true;
			
			for (FinalApprovalLogBean data : lst) {
				newApprReqNo = data.getAlloc_request_no();
				if(!newApprReqNo.equalsIgnoreCase(oldApprReqNo) && !firstRow) {
					rowCount++;
				}
				colCount = 0;

				row = sheet.createRow(rowCount);

				cell = ReportFormats.cell(row, colCount, data.getTran_type(), null);
				colCount++;

				cell = ReportFormats.cell(row, colCount, data.getApproval_type(), null);
				colCount++;

				cell = ReportFormats.cell(row, colCount, data.getAlloc_request_no(), null);
				colCount++;

				cell = ReportFormats.cell(row, colCount, MedicoResources.convertUtilDateToString_DD_MM_YYYY(data.getAlloc_request_date()), null);
				colCount++;

				cell = ReportFormats.cell(row, colCount, data.getProduct_type(), null);
				colCount++;

				cell = ReportFormats.cell(row, colCount, data.getEmployee_id(), null);
				colCount++;

				cell = ReportFormats.cell(row, colCount, data.getEmployee_name(), null);
				colCount++;
				
				cell = ReportFormats.cell(row, colCount, data.getEmailid_of_approver(), null);
				colCount++;
				
				cell = ReportFormats.cell(row, colCount, data.getApproval_status(), null);
				colCount++;
				
				cell = ReportFormats.cell(row, colCount, data.getAppr_recd_from_ip(), null);
				colCount++;
				
				cell = ReportFormats.cell(row, colCount, data.getApprover_user_id(), null);
				colCount++;
				
				cell = ReportFormats.cell(row, colCount, data.getApproval_email_send_datetime()==null?"":MedicoResources.convert_DD_MM_YYYY_toDateAndTime(data.getApproval_email_send_datetime().toString())
						, null);
				colCount++;
				
				cell = ReportFormats.cell(row, colCount, data.getApprover_made_approval_date_time()==null?"":MedicoResources.convert_DD_MM_YYYY_toDateAndTime(data.getApprover_made_approval_date_time().toString())
						, null);
				colCount++;
				
				cell = ReportFormats.cell(row, colCount, data.getTransaction_ref().toString(), null);
				colCount++;
				
				oldApprReqNo = newApprReqNo;
				firstRow = false;
				rowCount++;
			}

			
			FileOutputStream fileOutputStream = new FileOutputStream(filepath);
			workbook.write(fileOutputStream);
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("Error Occurred :" +e.getMessage());// uncomment asneeded --;
		} finally {
			if (workbook != null) {
				try {
					workbook.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					System.out.println("Error Occurred :" +e.getMessage());// uncomment asneeded --;
				}
			}
		}
		
		
		return filename;
	}

}
