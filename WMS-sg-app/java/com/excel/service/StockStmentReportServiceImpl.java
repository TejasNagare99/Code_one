package com.excel.service;

import java.io.File;
import java.io.FileOutputStream;
import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.poi.ss.usermodel.BorderStyle;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.DataFormat;
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

import com.excel.bean.ReportBean;
import com.excel.model.Stock_Statement_Non_Sale;
import com.excel.model.Stock_Statement_Non_Sale_VET;
import com.excel.model.Stock_statement_pfz;
import com.excel.model.Usermaster;
import com.excel.repository.UserMasterRepository;
import com.excel.security.PasswordManager;
import com.excel.utility.ApachePoiExcelFormat;
import com.excel.utility.MedicoConstants;
import com.excel.utility.MedicoResources;
import com.excel.utility.ReportFormats;

import net.lingala.zip4j.core.ZipFile;
import net.lingala.zip4j.model.ZipParameters;
import net.lingala.zip4j.util.Zip4jConstants;

@Service
public class StockStmentReportServiceImpl implements StockStmentReportService,MedicoConstants{
	@Autowired private UserMasterService usermasterservice;

	@Autowired private UserMasterRepository usermasterrepository;
	@Override
	public String generateStockStatmentReport(List<Stock_statement_pfz> list,String companyname,String companycode,String empId,String stk_to_cfa_in) throws Exception {
		String filename = "StockStatment" + new Date().getTime() + ".xlsx";
		String filepath = REPORT_FILE_PATH + filename ;
		XSSFWorkbook workbook = null;
		SimpleDateFormat timeformat = new SimpleDateFormat("hh:mm:ss a");
		try {
			workbook = new XSSFWorkbook();
			File f = new File(REPORT_FILE_PATH);
			if (!f.exists()) {
				if (f.mkdirs()) {
				} else {
					throw new RuntimeException("Could not create directory");
				}
			}
			XSSFSheet sheet = workbook.createSheet("Stock Statement Report");
			
			XSSFRow row = null;
			XSSFCell cell = null;
			int rowCount = 0;
			int colCount = 0;
		
		
			String[] headings;

			if ("Y".equalsIgnoreCase(stk_to_cfa_in)) {
			    headings = new String[]{
			        "Team", "Brand", "Sample / Promotional Inputs", "Type of Storage", "Product Code/ Warehouse Item Code", "FCODE", 
			         "Name of the Product", "Stock as on dt.(Quantity)", "Stock with Held(Quantity)", "Batch No.", 
			        "MFG Date", "Exp. Date", "Unit Price", "Stock as on dt.(Value)",
			         "HSN Code", "SGST", "CGST", "IGST"
			    };
			} else {
			    headings = new String[]{
			        "Team", "Brand", "Sample / Promotional Inputs", "Type of Storage", "Product Code/ Warehouse Item Code", "FCODE", 
			        "GCMA No", "Name of the Product", "Stock as on dt.(Quantity)", "Stock with Held(Quantity)", "Batch No.", 
			        "MFG Date", "Exp. Date", "GCMA Exp Date", "Unit Price", "Stock as on dt.(Value)", "Latest Month & Year in allocated", 
			        "Average Monthly use(Last 12 months)", "HSN Code", "SGST", "CGST", "IGST", "Mono Size", "Shipper Size"
			    };
			}
			
			
			XSSFCellStyle headingCellStyle = ReportFormats.heading(workbook);
			XSSFCellStyle columnHeadingStyle = ReportFormats.columnHeading(workbook);
			XSSFCellStyle cellAlignment = ReportFormats.setCellAlignment(workbook, ALIGN_RIGHT);
			
			XSSFCellStyle dataleftred = workbook.createCellStyle();
		    dataleftred.setAlignment(HorizontalAlignment.LEFT);
		    dataleftred.setFillForegroundColor(IndexedColors.ROSE.index);
		    dataleftred.setFillPattern(FillPatternType.SOLID_FOREGROUND);
		    
			XSSFCellStyle datarighttred = workbook.createCellStyle();
			datarighttred.setAlignment(HorizontalAlignment.RIGHT);
			datarighttred.setFillForegroundColor(IndexedColors.ROSE.index);
			datarighttred.setFillPattern(FillPatternType.SOLID_FOREGROUND);
		    
		    XSSFCellStyle datared = workbook.createCellStyle();
		    datared.setFillForegroundColor(IndexedColors.ROSE.index);
		    datared.setFillPattern(FillPatternType.SOLID_FOREGROUND);
		    
		    XSSFCellStyle olddata = workbook.createCellStyle();
		    olddata.setFillForegroundColor(IndexedColors.RED1.index);
		    olddata.setFillPattern(FillPatternType.SOLID_FOREGROUND);
		    
			XSSFCellStyle dataleft = workbook.createCellStyle();
		    dataleft.setAlignment(HorizontalAlignment.LEFT);
		    
		    Date date = new Date();  
		    
		    SimpleDateFormat df =  new  SimpleDateFormat("dd/MM/yyyy");
			
			row = sheet.createRow(rowCount);
			cell = row.createCell(colCount);
			sheet.addMergedRegion(new CellRangeAddress(rowCount, rowCount, colCount, colCount + headings.length - 1));
			cell.setCellValue(companyname);
			cell.setCellStyle(headingCellStyle);
			rowCount++;
			
			row = sheet.createRow(rowCount);
			cell = row.createCell(colCount);
			sheet.addMergedRegion(new CellRangeAddress(rowCount, rowCount, colCount, colCount + headings.length - 1));
			cell.setCellValue("Stock Statment Report");
			cell.setCellStyle(headingCellStyle);
			rowCount++;

			row = sheet.createRow(rowCount);
			cell = row.createCell(colCount);
			sheet.addMergedRegion(new CellRangeAddress(rowCount, rowCount, colCount, colCount + headings.length - 1));
			cell.setCellValue(MedicoResources.convertUtilDateToString_DD_MM_YYYY(new Date())+" "+timeformat.format(new Date()));
			cell.setCellStyle(headingCellStyle);
			
			rowCount += 1;
			row = sheet.createRow(rowCount);
			
			for (String heading : headings) {
				cell = ReportFormats.cell(row, colCount, heading, columnHeadingStyle);
				colCount++;
			}
			
			sheet.groupRow(0, 2);
			sheet.createFreezePane(0, 4);
			
			rowCount ++;
			colCount=0;
			Long days = null;
			
			for (Stock_statement_pfz data : list) {
				if(data.getExpry_date()!=null) {
				Date expdt = df.parse(data.getExpry_date());
				days= ((expdt.getTime()-date.getTime())/(24*60*60*1000));
				}
				/*
				 * else { days=100l; }
				 */
				//System.out.println("Days:::"+days);
				if(days>0 && days<=30) {

					row = sheet.createRow(rowCount);
					/*cell = ReportFormats.cell(row, colCount, data.getRow().toString(), cellAlignment);
					colCount++;*/
					
					cell = ReportFormats.cell(row, colCount, data.getDivision(), datared);
					colCount++;
					
					cell = ReportFormats.cell(row, colCount, data.getBrand(), datared);
					colCount++;
					
					cell = ReportFormats.cell(row, colCount, data.getSample_PI(), datared);
					colCount++;
					//chnges 
					cell = ReportFormats.cell(row, colCount, data.getType_of_storage(), datared);
					colCount++;
					
					cell = ReportFormats.cell(row, colCount, data.getProduct_code(), datared);
					colCount++;
					//chnges 
					cell = ReportFormats.cell(row, colCount, data.getFcode(), datared);
					colCount++;
					
					
					if (!"Y".equalsIgnoreCase(stk_to_cfa_in)) {
						
						cell = ReportFormats.cell(row, colCount, data.getGcma_number(), datared);
						colCount++;
					}
				
					cell = ReportFormats.cell(row, colCount, data.getProduct_name(), datared);
					colCount++;
					
					cell = ReportFormats.cellNum(row, colCount,data.getStock_qty() == null ?0l  :data.getStock_qty().doubleValue(), datared);
					colCount++;
					
					
					cell = ReportFormats.cellNum(row, colCount,data.getWith_held_qty() == null ?0l: data.getWith_held_qty().doubleValue(), datared);
					colCount++;
					
					
					cell = ReportFormats.cell(row, colCount, data.getBatch_no(), datared);
					colCount++;
					
					cell = ReportFormats.cell(row, colCount, data.getReciept_date(), datared);
					colCount++;
					
					data.setExpry_date(data.getExpry_date());
					if (data.getExpry_date() != null) {
		    		    // System.out.println("date::"+stckobj.getExpry_date());
		    		    if (data.getNear_exp_ind().equals("Y")) {
		    		    cell = ReportFormats.cell(row, colCount, data.getExpry_date(), dataleftred);
		    			colCount++;
		    		    } else {
		    		    	cell = ReportFormats.cell(row, colCount, data.getExpry_date(), dataleftred);
		    			colCount++;
		    		    }
		    		} else {
		    			cell = ReportFormats.cell(row, colCount, "", null);
		    		    colCount++;
		    		}
				//chnges 	
					if (!"Y".equalsIgnoreCase(stk_to_cfa_in)) {
						cell = ReportFormats.cell(row, colCount, data.getGcma_expiry_dt()==null?"":data.getGcma_expiry_dt(), datared);
						colCount++;
						
						}

					if (null == data.getUnit_price()) {
						cell = ReportFormats.cellNum(row, colCount, new BigDecimal(0).doubleValue(), datared);
		    		} else {
		    			cell = ReportFormats.cellNum(row, colCount, data.getUnit_price().doubleValue(), datared);
		    		}
		    		
		    		colCount++;
		    		
		    		if (null == data.getStock_value()) {
		    			cell = ReportFormats.cellNum(row, colCount,new BigDecimal(0).doubleValue(), datared);
		    		   
		    		} else {
		    			cell = ReportFormats.cellNum(row, colCount, data.getStock_value().doubleValue(), datared);
		    		}
		    		colCount++;
					
					
					
		    		if (!"Y".equalsIgnoreCase(stk_to_cfa_in)) {
					cell = ReportFormats.cell(row, colCount, data.getLatest_allocated(), datared);
					colCount++;
					
					cell = ReportFormats.cell(row, colCount, data.getAvg_monthly_use(), datarighttred);
					colCount++;
					
		    		}
					
					
					cell = ReportFormats.cell(row, colCount, data.getHsn_code(), datared);
					colCount++;
					
					cell = ReportFormats.cellNum(row, colCount, data.getSgst() ==null ? 0l : data.getSgst().doubleValue(), datared);
					colCount++;
					
					cell = ReportFormats.cellNum(row, colCount, data.getCgst() ==null ? 0l : data.getCgst().doubleValue(), datared);
					colCount++;
					
					cell = ReportFormats.cellNum(row, colCount, data.getIgst() ==null ? 0l : data.getIgst().doubleValue(), datared);
					colCount++;
					
					
					if (!"Y".equalsIgnoreCase(stk_to_cfa_in)) {
					
					cell = ReportFormats.cell(row, colCount, data.getMono_size() ==null ? "0" : data.getMono_size().toString(), datared);
					colCount++;
					
					cell = ReportFormats.cell(row, colCount, data.getShipper_size() ==null ? "0" : data.getShipper_size().toString(), datared);
					colCount++;
					
					}
					
					rowCount++;
					colCount=0;
				}
				
				else if(days<0){


					row = sheet.createRow(rowCount);
					
					cell = ReportFormats.cell(row, colCount, data.getDivision(), olddata);
					colCount++;
					
					cell = ReportFormats.cell(row, colCount, data.getBrand(), olddata);
					colCount++;
					
					cell = ReportFormats.cell(row, colCount, data.getSample_PI(), olddata);
					colCount++;
					//chnges 
					cell = ReportFormats.cell(row, colCount, data.getType_of_storage(), olddata);
					colCount++;
					
					cell = ReportFormats.cell(row, colCount, data.getProduct_code(), olddata);
					colCount++;
					//chnges 
					cell = ReportFormats.cell(row, colCount, data.getFcode(), olddata);
					colCount++;
					
		if (!"Y".equalsIgnoreCase(stk_to_cfa_in)) {
						
						cell = ReportFormats.cell(row, colCount, data.getGcma_number(), olddata);
						colCount++;
					}
					cell = ReportFormats.cell(row, colCount, data.getProduct_name(), olddata);
					colCount++;
					
					cell = ReportFormats.cellNum(row, colCount,data.getStock_qty() == null ?0l  :data.getStock_qty().doubleValue(), olddata);
					colCount++;
					
					
					cell = ReportFormats.cellNum(row, colCount,data.getWith_held_qty() == null ?0l: data.getWith_held_qty().doubleValue(), olddata);
					colCount++;
					
					
					cell = ReportFormats.cell(row, colCount, data.getBatch_no(), olddata);
					colCount++;
					
					cell = ReportFormats.cell(row, colCount, data.getReciept_date(), olddata);
					colCount++;
					
					data.setExpry_date(data.getExpry_date());
					if (data.getExpry_date() != null) {
		    		    // System.out.println("date::"+stckobj.getExpry_date());
		    		    if (data.getNear_exp_ind().equals("Y")) {
		    		    cell = ReportFormats.cell(row, colCount, data.getExpry_date(), olddata);
		    			colCount++;
		    		    } else {
		    		    	cell = ReportFormats.cell(row, colCount, data.getExpry_date(), olddata);
		    			colCount++;
		    		    }
		    		} else {
		    			cell = ReportFormats.cell(row, colCount, "", null);
		    		    colCount++;
		    		}
				//chnges 	
					if (!"Y".equalsIgnoreCase(stk_to_cfa_in)) {
						cell = ReportFormats.cell(row, colCount, data.getGcma_expiry_dt()==null?"":data.getGcma_expiry_dt(), olddata);
						colCount++;
						
						}

					if (null == data.getUnit_price()) {
						cell = ReportFormats.cellNum(row, colCount, new BigDecimal(0).doubleValue(), olddata);
		    		} else {
		    			cell = ReportFormats.cellNum(row, colCount, data.getUnit_price().doubleValue(), olddata);
		    		}
		    		
		    		colCount++;
		    		
		    		if (null == data.getStock_value()) {
		    			cell = ReportFormats.cellNum(row, colCount,new BigDecimal(0).doubleValue(), olddata);
		    		   
		    		} else {
		    			cell = ReportFormats.cellNum(row, colCount, data.getStock_value().doubleValue(), olddata);
		    		}
		    		colCount++;
					
					
					
					
		    		if (!"Y".equalsIgnoreCase(stk_to_cfa_in)) {
					cell = ReportFormats.cell(row, colCount, data.getLatest_allocated(), olddata);
					colCount++;
					
					cell = ReportFormats.cell(row, colCount, data.getAvg_monthly_use(), olddata);
					colCount++;
					
		    		}
					
					cell = ReportFormats.cell(row, colCount, data.getHsn_code(), olddata);
					colCount++;
					
					cell = ReportFormats.cellNum(row, colCount, data.getSgst() ==null ? 0l : data.getSgst().doubleValue(), olddata);
					colCount++;
					
					cell = ReportFormats.cellNum(row, colCount, data.getCgst() ==null ? 0l : data.getCgst().doubleValue(), olddata);
					colCount++;
					
					cell = ReportFormats.cellNum(row, colCount, data.getIgst() ==null ? 0l : data.getIgst().doubleValue(), olddata);
					colCount++;
					
					
					if (!"Y".equalsIgnoreCase(stk_to_cfa_in)) {
					
					cell = ReportFormats.cell(row, colCount, data.getMono_size() ==null ? "0" : data.getMono_size().toString(), olddata);
					colCount++;
					
					cell = ReportFormats.cell(row, colCount, data.getShipper_size() ==null ? "0" : data.getShipper_size().toString(), olddata);
					colCount++;
					
					}
					
					rowCount++;
					colCount=0;
				
					
				}
				
				else {
				row = sheet.createRow(rowCount);
				/*cell = ReportFormats.cell(row, colCount, data.getRow().toString(), cellAlignment);
				colCount++;*/
				
				cell = ReportFormats.cell(row, colCount, data.getDivision(), null);
				colCount++;
				
				cell = ReportFormats.cell(row, colCount, data.getBrand(), null);
				colCount++;
				
				cell = ReportFormats.cell(row, colCount, data.getSample_PI(), null);
				colCount++;
				//chnges 
				cell = ReportFormats.cell(row, colCount, data.getType_of_storage(), null);
				colCount++;
				
				cell = ReportFormats.cell(row, colCount, data.getProduct_code(), null);
				colCount++;
				//chnges 
				cell = ReportFormats.cell(row, colCount, data.getFcode(), null);
				colCount++;
				
				if (!"Y".equalsIgnoreCase(stk_to_cfa_in)) {
					
					cell = ReportFormats.cell(row, colCount, data.getGcma_number(), null);
					colCount++;
				}
				cell = ReportFormats.cell(row, colCount, data.getProduct_name(), null);
				colCount++;
				
				cell = ReportFormats.cellNum(row, colCount,data.getStock_qty() == null ?0l  :data.getStock_qty().doubleValue(), null);
				colCount++;
				
				
				cell = ReportFormats.cellNum(row, colCount,data.getWith_held_qty() == null ?0l: data.getWith_held_qty().doubleValue(), null);
				colCount++;
				
				
				cell = ReportFormats.cell(row, colCount, data.getBatch_no(), null);
				colCount++;
				
				cell = ReportFormats.cell(row, colCount, data.getReciept_date(), null);
				colCount++;
				
				data.setExpry_date(data.getExpry_date());
				if (data.getExpry_date() != null) {
	    		    // System.out.println("date::"+stckobj.getExpry_date());
	    		    //if (data.getNear_exp_ind().equals("Y")) {
					if(companycode.trim().equals("SER")||companycode.trim().equals("P&G")) {
						if (days>0 && days<=180) {
			    		    cell = ReportFormats.cell(row, colCount, data.getExpry_date(), dataleftred);
			    			colCount++;
			    		    }
							else {
			    		    	cell = ReportFormats.cell(row, colCount, data.getExpry_date(), dataleft);
			    			colCount++;
			    		    }
					}
					else {
					if (days>0 && days<=90) {
	    		    cell = ReportFormats.cell(row, colCount, data.getExpry_date(), dataleftred);
	    			colCount++;
	    		    }
					else {
	    		    	cell = ReportFormats.cell(row, colCount, data.getExpry_date(), dataleft);
	    			colCount++;
	    		    }
					}
	    		} else {
	    			cell = ReportFormats.cell(row, colCount, "", null);
	    		    colCount++;
	    		}
			//chnges 	
				
				if (!"Y".equalsIgnoreCase(stk_to_cfa_in)) {
				cell = ReportFormats.cell(row, colCount, data.getGcma_expiry_dt()==null?"":data.getGcma_expiry_dt(), null);
				colCount++;
				
				}

				if (null == data.getUnit_price()) {
					cell = ReportFormats.cellNum(row, colCount, new BigDecimal(0).doubleValue(), null);
	    		} else {
	    			cell = ReportFormats.cellNum(row, colCount, data.getUnit_price().doubleValue(), null);
	    		}
	    		
	    		colCount++;
	    		
	    		if (null == data.getStock_value()) {
	    			cell = ReportFormats.cellNum(row, colCount,new BigDecimal(0).doubleValue(), null);
	    		   
	    		} else {
	    			cell = ReportFormats.cellNum(row, colCount, data.getStock_value().doubleValue(), null);
	    		}
	    		colCount++;
				
				
				
				
	    		if (!"Y".equalsIgnoreCase(stk_to_cfa_in)) {
				cell = ReportFormats.cell(row, colCount, data.getLatest_allocated(), null);
				colCount++;
				
				cell = ReportFormats.cell(row, colCount, data.getAvg_monthly_use(), null);
				colCount++;
				
	    		}
				cell = ReportFormats.cell(row, colCount, data.getHsn_code(), null);
				colCount++;
				
				cell = ReportFormats.cellNum(row, colCount, data.getSgst() ==null ? 0l : data.getSgst().doubleValue(), null);
				colCount++;
				
				cell = ReportFormats.cellNum(row, colCount, data.getCgst() ==null ? 0l : data.getCgst().doubleValue(), null);
				colCount++;
				
				cell = ReportFormats.cellNum(row, colCount, data.getIgst() ==null ? 0l : data.getIgst().doubleValue(), null);
				colCount++;
				
				
				if (!"Y".equalsIgnoreCase(stk_to_cfa_in)) {
				
				cell = ReportFormats.cell(row, colCount, data.getMono_size() ==null ? "0" : data.getMono_size().toString(), null);
				colCount++;
				
				cell = ReportFormats.cell(row, colCount, data.getShipper_size() ==null ? "0" : data.getShipper_size().toString(), null);
				colCount++;
				
				}
				
				rowCount++;
				colCount=0;
				}
							
			}
			FileOutputStream fileOutputStream = new FileOutputStream(filepath);
			workbook.write(fileOutputStream);
			System.out.println("Excel is Generate for stock statment::::");
			
			filename=usermasterservice.generateExcellock(filepath, filename,empId,".xlsx");

		}
		catch (Exception e) {
			// TODO: handle exception
			throw e;
		}
		finally {
			if (workbook != null) { workbook.close(); }
		}
		return filename;
		}
	
	
//	@Override
//	public String stockstatmentNonsal(List<Stock_Statement_Non_Sale> DataList,ReportBean bean,String companyname,String empId) throws Exception {
//		String filename = "Stkstmt_nonsal" + new Date().getTime()+ ".xlsx";
//		String filepath = REPORT_FILE_PATH + filename  ;
//		XSSFWorkbook workbook = null;
//		DecimalFormat df=new DecimalFormat("0.0000");
//		
//		try {
//			workbook = new XSSFWorkbook();
//			File f = new File(REPORT_FILE_PATH);
//			if (!f.exists()) {
//				if (f.mkdirs()) {
//				} else {
//					throw new RuntimeException("Could not create directory");
//				}
//			}
//			XSSFSheet sheet = workbook.createSheet("Non Saleable Batchwise Stock Statment");
//
//			XSSFRow row = null;
//			XSSFCell cell = null;
//			int rowCount = 0;
//			int colCount = 0;
//
//			String headings[] = { "", "Product Description", "Pack", "Batch/Art Work no", "Opening Stock", "", "", "Inward",
//					"","", "Outward", "", "Closing Stock", "","" };
//
//			String headings1[] = { "Code", "", "", "", "Qty", "Value", "GRN (Reject)", "IAA", "Total", "IAA",
//					"Stock Withdrawal", "Total", "Qty", "Value","WithHeld Qty" };
//
//			XSSFCellStyle headingCellStyle = ReportFormats.heading(workbook);
//			XSSFCellStyle columnHeadingStyle = ReportFormats.columnHeading(workbook);
//			XSSFCellStyle cellAlignment = ReportFormats.setCellAlignment(workbook, ALIGN_RIGHT);
//			
//			ApachePoiExcelFormat xlsxExcelFormat = new ApachePoiExcelFormat();
//			CellStyle sheetHeading = xlsxExcelFormat.SheetHeading(workbook);
//			CellStyle columnHeading = xlsxExcelFormat.columnHeading(workbook);
//			CellStyle leftAlign = xlsxExcelFormat.dataLeftAlign(workbook);
//			CellStyle leftAlignB = xlsxExcelFormat.dataBoldLeftAlign(workbook);
//			CellStyle columnSubHeadingBlue = xlsxExcelFormat.columnSubHeadingBlue(workbook);
//			CellStyle columnSubHeadingLemon = xlsxExcelFormat.columnSubHeadingLemon(workbook);
//			CellStyle columnSubHeadingGold = xlsxExcelFormat.columnSubHeadingGold(workbook);
//			CellStyle dataRightAlignWthDecimal = xlsxExcelFormat.dataRightAlignWthDecimal(workbook);
//			CellStyle dataBoldRightAlign = xlsxExcelFormat.dataBoldRightAlign(workbook);
//
//			XSSFFont tableHeaderFont = workbook.createFont();
//			tableHeaderFont.setFontHeightInPoints((short) 12);
//			tableHeaderFont.setBold(true);
//			tableHeaderFont.setColor(IndexedColors.BLACK.index);
//			CellStyle prodstyle = workbook.createCellStyle();
//			prodstyle.setFillForegroundColor(IndexedColors.CORNFLOWER_BLUE.index);
//			prodstyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);
//			prodstyle.setBorderTop(BorderStyle.THIN);
//			prodstyle.setTopBorderColor(IndexedColors.BLACK.index);
//			prodstyle.setBorderBottom(BorderStyle.THIN);
//			prodstyle.setBottomBorderColor(IndexedColors.BLACK.index);
//			prodstyle.setBorderLeft(BorderStyle.THIN);
//			prodstyle.setLeftBorderColor(IndexedColors.BLACK.index);
//			prodstyle.setBorderRight(BorderStyle.THIN);
//			prodstyle.setRightBorderColor(IndexedColors.BLACK.index);
//			prodstyle.setFont(tableHeaderFont);
//			DataFormat format = workbook.createDataFormat();
//			prodstyle.setDataFormat(format.getFormat("0.00"));
//			prodstyle.setAlignment(HorizontalAlignment.RIGHT);
//			
//			row = sheet.createRow(rowCount);
//			cell = row.createCell(colCount);
//			sheet.addMergedRegion(new CellRangeAddress(rowCount, rowCount, colCount, colCount + headings.length - 1));
//			cell.setCellValue(companyname);
//			cell.setCellStyle(headingCellStyle);
//			rowCount++;
//
//			row = sheet.createRow(rowCount);
//			cell = row.createCell(colCount);
//			sheet.addMergedRegion(new CellRangeAddress(rowCount, rowCount, colCount, colCount + headings.length - 1));
//			cell.setCellValue("Non Saleable Batchwise Stock Statment");
//			cell.setCellStyle(headingCellStyle);
//			rowCount++;
//
//			row = sheet.createRow(rowCount);
//			cell = row.createCell(colCount);
//			sheet.addMergedRegion(new CellRangeAddress(rowCount, rowCount, colCount, colCount + headings.length - 1));
//			cell.setCellValue("Period: "+MedicoResources.convertUtilDateToString_DD_MM_YYYY_(bean.getStartDate())+" to "+MedicoResources.convertUtilDateToString_DD_MM_YYYY_(bean.getEndDate()));
//			cell.setCellStyle(headingCellStyle);
//
//			rowCount += 1;
//			row = sheet.createRow(rowCount);
//
//			for (String heading : headings) {
//				cell = ReportFormats.cell(row, colCount, heading, columnHeadingStyle);
//				colCount++;
//			}
//
//			sheet.groupRow(0, 2);
//			sheet.createFreezePane(0, 5);
//
//			rowCount++;
//			colCount = 0;
//
//			row = sheet.createRow(rowCount);
//
//			for (String head : headings1) {
//				cell = ReportFormats.cell(row, colCount, head, columnHeadingStyle);
//				colCount++;
//			}
//
//			rowCount++;
//			colCount = 0;
//
//			String old_div = "", new_div = "";
//			String new_stock = "", old_stock = "";
//			String new_prod = "", old_prod = "";
//
//			BigDecimal ptot_open_stk = BigDecimal.ZERO;
//			BigDecimal ptot_out_iaa = BigDecimal.ZERO;
//			BigDecimal ptot_out_stk_wth = BigDecimal.ZERO;
//			BigDecimal ptot_out_total = BigDecimal.ZERO;
//			BigDecimal ptot_close_stk = BigDecimal.ZERO;
//
//			BigDecimal gtot_open_stk = BigDecimal.ZERO;
//			BigDecimal gtot_out_iaa = BigDecimal.ZERO;
//			BigDecimal gtot_out_stk_wth = BigDecimal.ZERO;
//			BigDecimal gtot_out_total = BigDecimal.ZERO;
//			BigDecimal gtot_close_stk = BigDecimal.ZERO;
//			
//
//			BigDecimal side_tot_inward = BigDecimal.ZERO;
//			BigDecimal side_tot_outward = BigDecimal.ZERO;
//			BigDecimal tot_withheldQty = BigDecimal.ZERO;
//			BigDecimal gtot_withheldQty = BigDecimal.ZERO;
//			
//			for (Stock_Statement_Non_Sale data : DataList) {
//
//				new_div = data.getTeam() == null ? "" : data.getTeam();
//				new_stock = data.getStock_type_desc() == null ? "" : data.getStock_type_desc();
//				new_prod = data.getProduct_name() == null ? "" : data.getProduct_name();
//				side_tot_inward = BigDecimal.ZERO;
//				side_tot_outward = BigDecimal.ZERO;
//
//				rowCount++;
//
//				if (!old_stock.equalsIgnoreCase(new_stock)
//						|| (old_stock.equalsIgnoreCase(new_stock) && !old_div.equalsIgnoreCase(new_div))) {
//					if (!old_stock.equalsIgnoreCase("")) {
//						colCount = 0;
//						row = sheet.createRow(rowCount);
//
//						cell = ReportFormats.cell(row, colCount, "Product Total", columnHeadingStyle);
//						colCount++;
//
//						for (int i = 0; i <= 3; i++) {
//							cell = ReportFormats.cell(row, colCount, "", columnHeadingStyle);
//							colCount++;
//						}
//
//						cell = ReportFormats.cellNum(row, colCount, ptot_open_stk.doubleValue(), (XSSFCellStyle) prodstyle);
//						colCount++;
//
//						for (int i = 0; i <= 2; i++) {
//							cell = ReportFormats.cell(row, colCount, "", columnHeadingStyle);
//							colCount++;
//						}
//
//						cell = ReportFormats.cell(row, colCount, ptot_out_iaa.toString(), (XSSFCellStyle) prodstyle);
//						colCount++;
//
//						cell = ReportFormats.cell(row, colCount, ptot_out_stk_wth.toString(), (XSSFCellStyle) prodstyle);
//						colCount++;
//
//						cell = ReportFormats.cell(row, colCount, ptot_out_total.toString(), (XSSFCellStyle) prodstyle);
//						colCount++;
//
//						cell = ReportFormats.cell(row, colCount, "", columnHeadingStyle);
//						colCount++;
//
//						cell = ReportFormats.cell(row, colCount, ptot_close_stk.toString(), (XSSFCellStyle) prodstyle);
//						colCount++;
//
//						cell = ReportFormats.cell(row, colCount, tot_withheldQty.toString(), (XSSFCellStyle) prodstyle);
//						colCount++;
//						
//						ptot_open_stk = BigDecimal.ZERO;
//						ptot_out_iaa = BigDecimal.ZERO;
//						ptot_out_stk_wth = BigDecimal.ZERO;
//						ptot_out_total = BigDecimal.ZERO;
//						ptot_close_stk = BigDecimal.ZERO;
//						tot_withheldQty = BigDecimal.ZERO;
//						rowCount++;
//					}
//				}
//
//				if (!old_div.equalsIgnoreCase(new_div)) {
//					colCount = 0;
//					row = sheet.createRow(rowCount);
//
//					cell = ReportFormats.cell(row, colCount, "Division : "+new_div, null);
//					colCount++;
//					colCount++;
//
//					for (int i = 0; i <= 14; i++) {
//						cell = ReportFormats.cell(row, colCount, "", null);
//						colCount++;
//					}
//					colCount++;
//					rowCount++;
//				}
//
//				if (!old_stock.equalsIgnoreCase(new_stock)
//						|| (old_stock.equalsIgnoreCase(new_stock) && !old_div.equalsIgnoreCase(new_div))) {
//
//					colCount = 0;
//					row = sheet.createRow(rowCount);
//
//					cell = ReportFormats.cell(row, colCount, "Stock : " +new_stock, null);
//					colCount++;
//
//					for (int i = 0; i <= 14; i++) {
//						cell = ReportFormats.cell(row, colCount, "", null);
//						colCount++;
//					}
//
//					colCount++;
//					rowCount++;
//
//				}
//
//				side_tot_inward = data.getGrn_rej().add(data.getIaa_in_value());
//				side_tot_outward = data.getSw_out_value().add(data.getIaa_out_value());
//
//				colCount = 0;
//
//				row = sheet.createRow(rowCount);
//
//				cell = ReportFormats.cell(row, colCount, data.getProduct_code(), null);
//				colCount++;
//
//				if (!new_prod.equalsIgnoreCase(old_prod)
//						|| (new_prod.equalsIgnoreCase(old_prod) && !old_div.equalsIgnoreCase(new_div))
//						|| (new_prod.equalsIgnoreCase(old_prod) && !old_stock.equalsIgnoreCase(new_stock))) {
//
//					cell = ReportFormats.cell(row, colCount, data.getProduct_name(), (XSSFCellStyle) leftAlign);
//				} else {
//					cell = ReportFormats.cell(row, colCount, "", (XSSFCellStyle) leftAlign);
//				}
//
//				colCount++;
//
//				cell = ReportFormats.cell(row, colCount, data.getPack_disp_nm(), (XSSFCellStyle) leftAlign);
//				colCount++;
//
//				cell = ReportFormats.cell(row, colCount, data.getBatch_no(), (XSSFCellStyle) leftAlign);
//				colCount++;
//
//				cell = ReportFormats.cellNum(row, colCount, data.getBatch_open_stock().doubleValue(), (XSSFCellStyle) dataRightAlignWthDecimal);
//				colCount++;
//
//				cell = ReportFormats.cellNum(row, colCount, data.getOpen_stock_value().doubleValue(), (XSSFCellStyle) dataRightAlignWthDecimal);
//				colCount++;
//
//				cell = ReportFormats.cellNum(row, colCount, data.getGrn_rej().doubleValue(), (XSSFCellStyle) dataRightAlignWthDecimal);
//				colCount++;
//
//				cell = ReportFormats.cellNum(row, colCount, data.getIaa_in().doubleValue(), (XSSFCellStyle) dataRightAlignWthDecimal);
//				colCount++;
//
//				cell = ReportFormats.cellNum(row, colCount, side_tot_inward.doubleValue(), (XSSFCellStyle) dataRightAlignWthDecimal);
//				colCount++;
//
//				cell = ReportFormats.cellNum(row, colCount, data.getIaa_out().doubleValue(), (XSSFCellStyle) dataRightAlignWthDecimal);
//				colCount++;
//
//				cell = ReportFormats.cellNum(row, colCount, data.getSw_out().doubleValue(), (XSSFCellStyle) dataRightAlignWthDecimal);
//				colCount++;
//
//				cell = ReportFormats.cellNum(row, colCount, side_tot_outward.doubleValue(), (XSSFCellStyle) dataRightAlignWthDecimal);
//				colCount++;
//
//				cell = ReportFormats.cellNum(row, colCount, data.getClos_qty().doubleValue(), (XSSFCellStyle) dataRightAlignWthDecimal);
//				colCount++;
//
//				cell = ReportFormats.cellNum(row, colCount, data.getClos_value().doubleValue(), (XSSFCellStyle) dataRightAlignWthDecimal);
//				colCount++;
//
//				cell = ReportFormats.cellNum(row, colCount, data.getBatch_with_held_qty().doubleValue(), (XSSFCellStyle) dataRightAlignWthDecimal);
//				colCount++;
//				
//				// Calculations
//
//				ptot_open_stk = ptot_open_stk.add(data.getOpen_stock_value());
//				ptot_out_iaa = ptot_out_iaa.add(data.getIaa_out_value());
//				ptot_out_stk_wth = ptot_out_stk_wth.add(data.getSw_out_value());
//				ptot_out_total = ptot_out_total.add(side_tot_outward);
//				ptot_close_stk = ptot_close_stk.add(data.getClos_value());
//
//				gtot_open_stk = gtot_open_stk.add(data.getOpen_stock_value());
//				gtot_out_iaa = gtot_out_iaa.add(data.getIaa_out_value());
//				gtot_out_stk_wth = gtot_out_stk_wth.add(data.getSw_out_value());
//				gtot_out_total = gtot_out_total.add(side_tot_outward);
//				gtot_close_stk = gtot_close_stk.add(data.getClos_value());
//				gtot_withheldQty = gtot_withheldQty.add(data.getBatch_with_held_qty());
//				
//				tot_withheldQty = tot_withheldQty.add(data.getBatch_with_held_qty());
//
//				old_div = new_div;
//				old_stock = new_stock;
//				old_prod = new_prod;
//
//				rowCount++;
//
//			}
//
//			colCount = 0;
//			row = sheet.createRow(rowCount);
//
//			cell = ReportFormats.cell(row, colCount, "Product Total", columnHeadingStyle);
//			colCount++;
//
//			for (int i = 0; i <= 3; i++) {
//				cell = ReportFormats.cell(row, colCount, "", columnHeadingStyle);
//				colCount++;
//			}
//
//			cell = ReportFormats.cellNum(row, colCount, ptot_open_stk.doubleValue(), (XSSFCellStyle) prodstyle);
//			colCount++;
//
//			for (int i = 0; i <= 2; i++) {
//				cell = ReportFormats.cell(row, colCount, "", columnHeadingStyle);
//				colCount++;
//			}
//
//			cell = ReportFormats.cellNum(row, colCount, ptot_out_iaa.doubleValue(), (XSSFCellStyle) prodstyle);
//			colCount++;
//
//			cell = ReportFormats.cellNum(row, colCount, ptot_out_stk_wth.doubleValue(), (XSSFCellStyle) prodstyle);
//			colCount++;
//
//			cell = ReportFormats.cellNum(row, colCount, ptot_out_total.doubleValue(), (XSSFCellStyle) prodstyle);
//			colCount++;
//
//			cell = ReportFormats.cell(row, colCount, "", columnHeadingStyle);
//			colCount++;
//
//			cell = ReportFormats.cellNum(row, colCount, ptot_close_stk.doubleValue(), (XSSFCellStyle) prodstyle);
//			colCount++;
//			
//			cell = ReportFormats.cellNum(row, colCount, tot_withheldQty.doubleValue(), (XSSFCellStyle) prodstyle);
//			colCount++;
//
//			ptot_open_stk = BigDecimal.ZERO;
//			ptot_out_iaa = BigDecimal.ZERO;
//			ptot_out_stk_wth = BigDecimal.ZERO;
//			ptot_out_total = BigDecimal.ZERO;
//			ptot_close_stk = BigDecimal.ZERO;
//
//			rowCount++;
//			colCount = 0;
//
//			row = sheet.createRow(rowCount);
//
//			cell = ReportFormats.cell(row, colCount, "Grand Total", null);
//			colCount++;
//
//			for (int i = 0; i <= 3; i++) {
//				cell = ReportFormats.cell(row, colCount, "", cellAlignment);
//				colCount++;
//			}
//
//			cell = ReportFormats.cellNum(row, colCount, gtot_open_stk.doubleValue(), (XSSFCellStyle) dataRightAlignWthDecimal);
//			colCount++;
//
//			for (int i = 0; i <= 2; i++) {
//				cell = ReportFormats.cell(row, colCount, "", cellAlignment);
//				colCount++;
//			}
//
//			cell = ReportFormats.cellNum(row, colCount, gtot_out_iaa.doubleValue(), (XSSFCellStyle) dataRightAlignWthDecimal);
//			colCount++;
//
//			cell = ReportFormats.cellNum(row, colCount, gtot_out_stk_wth.doubleValue(), (XSSFCellStyle) dataRightAlignWthDecimal);
//			colCount++;
//
//			cell = ReportFormats.cellNum(row, colCount, gtot_out_total.doubleValue(), (XSSFCellStyle) dataRightAlignWthDecimal);
//			colCount++;
//
//			cell = ReportFormats.cell(row, colCount, "", (XSSFCellStyle) dataRightAlignWthDecimal);
//			colCount++;
//
//			String d=df.format(gtot_close_stk.doubleValue());
//			System.out.println("d ::"+d);
//			cell = ReportFormats.cellNum(row, colCount,Double.parseDouble(d), (XSSFCellStyle) dataRightAlignWthDecimal);
//			colCount++;
//			
//			cell = ReportFormats.cellNum(row, colCount,gtot_withheldQty.doubleValue(), (XSSFCellStyle) dataRightAlignWthDecimal);
//			colCount++;
//
//			FileOutputStream fileOutputStream = new FileOutputStream(filepath);
//			workbook.write(fileOutputStream);
//			
//			filename=usermasterservice.generateExcellock(filepath, filename,empId,".xlsx");
//
//		} finally
//
//		{
//			if (workbook != null) {
//				workbook.close();
//			}
//		}
//		return filename;
//	}
	
	
	@Override
	public Map<String, Object> stockstatmentNonsal(List<Stock_Statement_Non_Sale> DataList,ReportBean bean,String companyname,String empId) throws Exception {
		String filename = "Stkstmt_nonsal" + new Date().getTime()+ ".xlsx";
		String filepath = REPORT_FILE_PATH + filename  ;
		
		Map<String, Object> map = new HashMap();
		
		XSSFWorkbook workbook = null;
		DecimalFormat df=new DecimalFormat("0.0000");
		
		try {
			workbook = new XSSFWorkbook();
			File f = new File(REPORT_FILE_PATH);
			if (!f.exists()) {
				if (f.mkdirs()) {
				} else {
					throw new RuntimeException("Could not create directory");
				}
			}
			XSSFSheet sheet = workbook.createSheet("Non Saleable Batchwise Stock Statment");

			XSSFRow row = null;
			XSSFCell cell = null;
			int rowCount = 0;
			int colCount = 0;

			
			String headings[] = { "Product ","", "Pack", "Batch/Art Work no", "Opening Stock","",  "Inward","","",
					"Outward","","",  "Closing Stock","","" };
			
			
//			String headings[] = { "", "Product ", "Pack", "Batch/Art Work no", "Opening Stock", "", "", "Inward",
//					"","", "Outward", "", "Closing Stock", "","" };

			String headings1[] = { "Code", "Description", "", "", "Qty", "Value", "GRN (Reject)", "IAA", "Total", "IAA",
					"Stock Withdrawal", "Total", "Qty", "Value","WithHeld Qty" };

			XSSFCellStyle headingCellStyle = ReportFormats.heading_left(workbook);
			XSSFCellStyle columnHeadingStyle = ReportFormats.columnHeading(workbook);
			XSSFCellStyle cellAlignment = ReportFormats.setCellAlignment(workbook, ALIGN_RIGHT);
			
			ApachePoiExcelFormat xlsxExcelFormat = new ApachePoiExcelFormat();
			CellStyle sheetHeading = xlsxExcelFormat.SheetHeading(workbook);
			CellStyle columnHeading = xlsxExcelFormat.columnHeading(workbook);
			CellStyle leftAlign = xlsxExcelFormat.dataLeftAlign(workbook);
			CellStyle leftAlignB = xlsxExcelFormat.dataBoldLeftAlign(workbook);
			CellStyle columnSubHeadingBlue = xlsxExcelFormat.columnSubHeadingBlue(workbook);
			CellStyle columnSubHeadingLemon = xlsxExcelFormat.columnSubHeadingLemon(workbook);
			CellStyle columnSubHeadingGold = xlsxExcelFormat.columnSubHeadingGold(workbook);
			CellStyle dataRightAlignWthDecimal = xlsxExcelFormat.dataRightAlignWthDecimal(workbook);
			CellStyle dataBoldRightAlign = xlsxExcelFormat.dataBoldRightAlign(workbook);

			XSSFFont tableHeaderFont = workbook.createFont();
			tableHeaderFont.setFontHeightInPoints((short) 12);
			tableHeaderFont.setBold(true);
			tableHeaderFont.setColor(IndexedColors.BLACK.index);
			CellStyle prodstyle = workbook.createCellStyle();
			prodstyle.setFillForegroundColor(IndexedColors.CORNFLOWER_BLUE.index);
			prodstyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);
			prodstyle.setBorderTop(BorderStyle.THIN);
			prodstyle.setTopBorderColor(IndexedColors.BLACK.index);
			prodstyle.setBorderBottom(BorderStyle.THIN);
			prodstyle.setBottomBorderColor(IndexedColors.BLACK.index);
			prodstyle.setBorderLeft(BorderStyle.THIN);
			prodstyle.setLeftBorderColor(IndexedColors.BLACK.index);
			prodstyle.setBorderRight(BorderStyle.THIN);
			prodstyle.setRightBorderColor(IndexedColors.BLACK.index);
			prodstyle.setFont(tableHeaderFont);
			DataFormat format = workbook.createDataFormat();
			prodstyle.setDataFormat(format.getFormat("0.00"));
			prodstyle.setAlignment(HorizontalAlignment.RIGHT);
			
			row = sheet.createRow(rowCount);
			cell = row.createCell(colCount);
			sheet.addMergedRegion(new CellRangeAddress(rowCount, rowCount, colCount, colCount + headings.length));
			cell.setCellValue(companyname);
			cell.setCellStyle(headingCellStyle);
			rowCount++;

			row = sheet.createRow(rowCount);
			cell = row.createCell(colCount);
			sheet.addMergedRegion(new CellRangeAddress(rowCount, rowCount, colCount, colCount + headings.length ));
			cell.setCellValue("Non Saleable Batchwise Stock Statment");
			cell.setCellStyle(headingCellStyle);
			rowCount++;

			row = sheet.createRow(rowCount);
			cell = row.createCell(colCount);
			sheet.addMergedRegion(new CellRangeAddress(rowCount, rowCount, colCount, colCount + headings.length ));
			cell.setCellValue("Period: "+MedicoResources.convertUtilDateToString_DD_MM_YYYY_(bean.getStartDate())+" to "+MedicoResources.convertUtilDateToString_DD_MM_YYYY_(bean.getEndDate()));
			cell.setCellStyle(headingCellStyle);

			rowCount += 1;
			row = sheet.createRow(rowCount);

			
			
			
			
//			 for  merging cell 
			for (int i=0;i<headings.length;i++) {
				
				if(i==0) {
					sheet.addMergedRegion(new CellRangeAddress(rowCount, rowCount, 0, 1));
				}
				if(i==3) {
					sheet.addMergedRegion(new CellRangeAddress(rowCount, rowCount, 4, 5));
				}
				
				if(i==4) {
					sheet.addMergedRegion(new CellRangeAddress(rowCount, rowCount, 6, 8));
				}
				
				
				if(i==5) {
					sheet.addMergedRegion(new CellRangeAddress(rowCount, rowCount, 9, 11));
				}
				
				if(i==6) {
					sheet.addMergedRegion(new CellRangeAddress(rowCount, rowCount, 12, 14));
				}
				
				
				cell = ReportFormats.cell(row, colCount, headings[i], columnHeadingStyle);
				
				colCount++;
			}

//			sheet.addMergedRegion(new CellRangeAddress(rowCount, rowCount, 0, 1));
//			cell = ReportFormats.cell(row, colCount, headings[0], columnHeadingStyle);
//			colCount++;
//			
//			
//
//			cell = ReportFormats.cell(row, colCount, headings[1], columnHeadingStyle);
//			colCount++;
//			
//			cell = ReportFormats.cell(row, colCount, headings[2], columnHeadingStyle);
//			colCount++;
//			
////			sheet.addMergedRegion(new CellRangeAddress(rowCount, rowCount, 4, 5));
//			cell = ReportFormats.cell(row, colCount, headings[3], columnHeadingStyle);
//			colCount++;
//			
////			
////			sheet.addMergedRegion(new CellRangeAddress(rowCount, rowCount, 6, 8));
//			cell = ReportFormats.cell(row, colCount, headings[4], columnHeadingStyle);
//			colCount++;
//			
////			sheet.addMergedRegion(new CellRangeAddress(rowCount, rowCount, 9, 11));
//			cell = ReportFormats.cell(row, colCount, headings[5], columnHeadingStyle);
//			colCount++;
//			
////			sheet.addMergedRegion(new CellRangeAddress(rowCount, rowCount, 13, 15));
//			cell = ReportFormats.cell(row, colCount, headings[6], columnHeadingStyle);
//			colCount++;
			
			
			sheet.groupRow(0, 2);
			sheet.createFreezePane(0, 5);

			rowCount++;
			colCount = 0;

			row = sheet.createRow(rowCount);

			
			
			for (String head : headings1) {
				cell = ReportFormats.cell(row, colCount, head, columnHeadingStyle);
				colCount++;
			}

			rowCount++;
			colCount = 0;

			String old_div = "", new_div = "";
			String new_stock = "", old_stock = "";
			String new_prod = "", old_prod = "";

			BigDecimal ptot_open_stk = BigDecimal.ZERO;
			BigDecimal ptot_out_iaa = BigDecimal.ZERO;
			BigDecimal ptot_out_stk_wth = BigDecimal.ZERO;
			BigDecimal ptot_out_total = BigDecimal.ZERO;
			BigDecimal ptot_close_stk = BigDecimal.ZERO;

			BigDecimal gtot_open_stk = BigDecimal.ZERO;
			BigDecimal gtot_out_iaa = BigDecimal.ZERO;
			BigDecimal gtot_out_stk_wth = BigDecimal.ZERO;
			BigDecimal gtot_out_total = BigDecimal.ZERO;
			BigDecimal gtot_close_stk = BigDecimal.ZERO;
			

			BigDecimal side_tot_inward = BigDecimal.ZERO;
			BigDecimal side_tot_outward = BigDecimal.ZERO;
			BigDecimal tot_withheldQty = BigDecimal.ZERO;
			BigDecimal gtot_withheldQty = BigDecimal.ZERO;
			
			for (Stock_Statement_Non_Sale data : DataList) {

				new_div = data.getTeam() == null ? "" : data.getTeam();
				new_stock = data.getStock_type_desc() == null ? "" : data.getStock_type_desc();
				new_prod = data.getProduct_name() == null ? "" : data.getProduct_name();
				side_tot_inward = BigDecimal.ZERO;
				side_tot_outward = BigDecimal.ZERO;

				rowCount++;

				if (!old_stock.equalsIgnoreCase(new_stock)
						|| (old_stock.equalsIgnoreCase(new_stock) && !old_div.equalsIgnoreCase(new_div))) {
					if (!old_stock.equalsIgnoreCase("")) {
						colCount = 0;
						row = sheet.createRow(rowCount);

						cell = ReportFormats.cell(row, colCount, "Product Total", columnHeadingStyle);
						colCount++;

						for (int i = 0; i <= 3; i++) {
							cell = ReportFormats.cell(row, colCount, "", columnHeadingStyle);
							colCount++;
						}

						cell = ReportFormats.cellNum(row, colCount, ptot_open_stk.doubleValue(), (XSSFCellStyle) prodstyle);
						colCount++;

						for (int i = 0; i <= 2; i++) {
							cell = ReportFormats.cell(row, colCount, "", columnHeadingStyle);
							colCount++;
						}

						cell = ReportFormats.cell(row, colCount, ptot_out_iaa.toString(), (XSSFCellStyle) prodstyle);
						colCount++;

						cell = ReportFormats.cell(row, colCount, ptot_out_stk_wth.toString(), (XSSFCellStyle) prodstyle);
						colCount++;

						cell = ReportFormats.cell(row, colCount, ptot_out_total.toString(), (XSSFCellStyle) prodstyle);
						colCount++;

						cell = ReportFormats.cell(row, colCount, "", columnHeadingStyle);
						colCount++;

						cell = ReportFormats.cell(row, colCount, ptot_close_stk.toString(), (XSSFCellStyle) prodstyle);
						colCount++;

						cell = ReportFormats.cell(row, colCount, tot_withheldQty.toString(), (XSSFCellStyle) prodstyle);
						colCount++;
						
						ptot_open_stk = BigDecimal.ZERO;
						ptot_out_iaa = BigDecimal.ZERO;
						ptot_out_stk_wth = BigDecimal.ZERO;
						ptot_out_total = BigDecimal.ZERO;
						ptot_close_stk = BigDecimal.ZERO;
						tot_withheldQty = BigDecimal.ZERO;
						rowCount++;
					}
				}

				if (!old_div.equalsIgnoreCase(new_div)) {
					colCount = 0;
					row = sheet.createRow(rowCount);

					cell = ReportFormats.cell(row, colCount, "Division : "+new_div, null);
					colCount++;
					colCount++;

					for (int i = 0; i <= 14; i++) {
						cell = ReportFormats.cell(row, colCount, "", null);
						colCount++;
					}
					colCount++;
					rowCount++;
				}

				if (!old_stock.equalsIgnoreCase(new_stock)
						|| (old_stock.equalsIgnoreCase(new_stock) && !old_div.equalsIgnoreCase(new_div))) {

					colCount = 0;
					row = sheet.createRow(rowCount);

					cell = ReportFormats.cell(row, colCount, "Stock : " +new_stock, null);
					colCount++;

					for (int i = 0; i <= 14; i++) {
						cell = ReportFormats.cell(row, colCount, "", null);
						colCount++;
					}

					colCount++;
					rowCount++;

				}

				side_tot_inward = data.getGrn_rej().add(data.getIaa_in_value());
				side_tot_outward = data.getSw_out_value().add(data.getIaa_out_value());

				colCount = 0;

				row = sheet.createRow(rowCount);

				cell = ReportFormats.cell(row, colCount, data.getProduct_code(), null);
				colCount++;

				if (!new_prod.equalsIgnoreCase(old_prod)
						|| (new_prod.equalsIgnoreCase(old_prod) && !old_div.equalsIgnoreCase(new_div))
						|| (new_prod.equalsIgnoreCase(old_prod) && !old_stock.equalsIgnoreCase(new_stock))) {

					cell = ReportFormats.cell(row, colCount, data.getProduct_name(), (XSSFCellStyle) leftAlign);
				} else {
					cell = ReportFormats.cell(row, colCount, "", (XSSFCellStyle) leftAlign);
				}

				colCount++;

				cell = ReportFormats.cell(row, colCount, data.getPack_disp_nm(), (XSSFCellStyle) leftAlign);
				colCount++;

				cell = ReportFormats.cell(row, colCount, data.getBatch_no(), (XSSFCellStyle) leftAlign);
				colCount++;

				cell = ReportFormats.cellNum(row, colCount, data.getBatch_open_stock().doubleValue(), (XSSFCellStyle) dataRightAlignWthDecimal);
				colCount++;

				cell = ReportFormats.cellNum(row, colCount, data.getOpen_stock_value().doubleValue(), (XSSFCellStyle) dataRightAlignWthDecimal);
				colCount++;

				cell = ReportFormats.cellNum(row, colCount, data.getGrn_rej().doubleValue(), (XSSFCellStyle) dataRightAlignWthDecimal);
				colCount++;

				cell = ReportFormats.cellNum(row, colCount, data.getIaa_in().doubleValue(), (XSSFCellStyle) dataRightAlignWthDecimal);
				colCount++;

				cell = ReportFormats.cellNum(row, colCount, side_tot_inward.doubleValue(), (XSSFCellStyle) dataRightAlignWthDecimal);
				colCount++;

				data.setIn_ward_total(side_tot_inward);
				cell = ReportFormats.cellNum(row, colCount, data.getIaa_out().doubleValue(), (XSSFCellStyle) dataRightAlignWthDecimal);
				colCount++;

				cell = ReportFormats.cellNum(row, colCount, data.getSw_out().doubleValue(), (XSSFCellStyle) dataRightAlignWthDecimal);
				colCount++;

				cell = ReportFormats.cellNum(row, colCount, side_tot_outward.doubleValue(), (XSSFCellStyle) dataRightAlignWthDecimal);
				colCount++;
				
				data.setOut_ward_total(side_tot_outward);

				cell = ReportFormats.cellNum(row, colCount, data.getClos_qty().doubleValue(), (XSSFCellStyle) dataRightAlignWthDecimal);
				colCount++;

				cell = ReportFormats.cellNum(row, colCount, data.getClos_value().doubleValue(), (XSSFCellStyle) dataRightAlignWthDecimal);
				colCount++;

				cell = ReportFormats.cellNum(row, colCount, data.getBatch_with_held_qty().doubleValue(), (XSSFCellStyle) dataRightAlignWthDecimal);
				colCount++;
				
				// Calculations

				ptot_open_stk = ptot_open_stk.add(data.getOpen_stock_value());
				ptot_out_iaa = ptot_out_iaa.add(data.getIaa_out_value());
				ptot_out_stk_wth = ptot_out_stk_wth.add(data.getSw_out_value());
				ptot_out_total = ptot_out_total.add(side_tot_outward);
				ptot_close_stk = ptot_close_stk.add(data.getClos_value());

				gtot_open_stk = gtot_open_stk.add(data.getOpen_stock_value());
				gtot_out_iaa = gtot_out_iaa.add(data.getIaa_out_value());
				gtot_out_stk_wth = gtot_out_stk_wth.add(data.getSw_out_value());
				gtot_out_total = gtot_out_total.add(side_tot_outward);
				gtot_close_stk = gtot_close_stk.add(data.getClos_value());
				gtot_withheldQty = gtot_withheldQty.add(data.getBatch_with_held_qty());
				
				tot_withheldQty = tot_withheldQty.add(data.getBatch_with_held_qty());

				old_div = new_div;
				old_stock = new_stock;
				old_prod = new_prod;

				rowCount++;

			}

			colCount = 0;
			row = sheet.createRow(rowCount);

			cell = ReportFormats.cell(row, colCount, "Product Total", columnHeadingStyle);
			colCount++;

			for (int i = 0; i <= 3; i++) {
				cell = ReportFormats.cell(row, colCount, "", columnHeadingStyle);
				colCount++;
			}

			cell = ReportFormats.cellNum(row, colCount, ptot_open_stk.doubleValue(), (XSSFCellStyle) prodstyle);
			colCount++;

			for (int i = 0; i <= 2; i++) {
				cell = ReportFormats.cell(row, colCount, "", columnHeadingStyle);
				colCount++;
			}

			cell = ReportFormats.cellNum(row, colCount, ptot_out_iaa.doubleValue(), (XSSFCellStyle) prodstyle);
			colCount++;

			cell = ReportFormats.cellNum(row, colCount, ptot_out_stk_wth.doubleValue(), (XSSFCellStyle) prodstyle);
			colCount++;

			cell = ReportFormats.cellNum(row, colCount, ptot_out_total.doubleValue(), (XSSFCellStyle) prodstyle);
			colCount++;

			cell = ReportFormats.cell(row, colCount, "", columnHeadingStyle);
			colCount++;

			cell = ReportFormats.cellNum(row, colCount, ptot_close_stk.doubleValue(), (XSSFCellStyle) prodstyle);
			colCount++;
			
			cell = ReportFormats.cellNum(row, colCount, tot_withheldQty.doubleValue(), (XSSFCellStyle) prodstyle);
			colCount++;

			ptot_open_stk = BigDecimal.ZERO;
			ptot_out_iaa = BigDecimal.ZERO;
			ptot_out_stk_wth = BigDecimal.ZERO;
			ptot_out_total = BigDecimal.ZERO;
			ptot_close_stk = BigDecimal.ZERO;

			rowCount++;
			colCount = 0;

			row = sheet.createRow(rowCount);

			cell = ReportFormats.cell(row, colCount, "Grand Total", null);
			colCount++;

			for (int i = 0; i <= 3; i++) {
				cell = ReportFormats.cell(row, colCount, "", cellAlignment);
				colCount++;
			}

			cell = ReportFormats.cellNum(row, colCount, gtot_open_stk.doubleValue(), (XSSFCellStyle) dataRightAlignWthDecimal);
			colCount++;

			for (int i = 0; i <= 2; i++) {
				cell = ReportFormats.cell(row, colCount, "", cellAlignment);
				colCount++;
			}

			cell = ReportFormats.cellNum(row, colCount, gtot_out_iaa.doubleValue(), (XSSFCellStyle) dataRightAlignWthDecimal);
			colCount++;

			cell = ReportFormats.cellNum(row, colCount, gtot_out_stk_wth.doubleValue(), (XSSFCellStyle) dataRightAlignWthDecimal);
			colCount++;

			cell = ReportFormats.cellNum(row, colCount, gtot_out_total.doubleValue(), (XSSFCellStyle) dataRightAlignWthDecimal);
			colCount++;

			cell = ReportFormats.cell(row, colCount, "", (XSSFCellStyle) dataRightAlignWthDecimal);
			colCount++;

			String d=df.format(gtot_close_stk.doubleValue());
			System.out.println("d ::"+d);
			cell = ReportFormats.cellNum(row, colCount,Double.parseDouble(d), (XSSFCellStyle) dataRightAlignWthDecimal);
			colCount++;
			
			cell = ReportFormats.cellNum(row, colCount,gtot_withheldQty.doubleValue(), (XSSFCellStyle) dataRightAlignWthDecimal);
			colCount++;

			FileOutputStream fileOutputStream = new FileOutputStream(filepath);
			workbook.write(fileOutputStream);
			
			filename=usermasterservice.generateExcellock(filepath, filename,empId,".xlsx");
			
	
			map.put("filename", filename);
			map.put("list", DataList);

		} finally

		{
			if (workbook != null) {
				workbook.close();
			}
		}
		return map;
	}


	@Override
	public Map<String, Object> stockstatmentNonsal_VET(List<Stock_Statement_Non_Sale_VET> DataList, ReportBean bean,
			String companyname, String empId) throws Exception {
		String filename = "Stkstmt_nonsal" + new Date().getTime()+ ".xlsx";
		String filepath = REPORT_FILE_PATH + filename  ;
		
		Map<String, Object> map = new HashMap();
		
		XSSFWorkbook workbook = null;
		DecimalFormat df=new DecimalFormat("0.0000");
		
		try {
			workbook = new XSSFWorkbook();
			File f = new File(REPORT_FILE_PATH);
			if (!f.exists()) {
				if (f.mkdirs()) {
				} else {
					throw new RuntimeException("Could not create directory");
				}
			}
			XSSFSheet sheet = workbook.createSheet("Non Saleable Batchwise Stock Statment");

			XSSFRow row = null;
			XSSFCell cell = null;
			int rowCount = 0;
			int colCount = 0;

			
			String headings[] = { "Product ","", "Pack", "Batch/Art Work no", "Opening Stock","",  "Inward","","",
					"Outward","","",  "Closing Stock","","" };
			
			
//			String headings[] = { "", "Product ", "Pack", "Batch/Art Work no", "Opening Stock", "", "", "Inward",
//					"","", "Outward", "", "Closing Stock", "","" };

			String headings1[] = { "Code", "Description", "", "", "Qty", "Value", "GRN (Reject)", "IAA", "Total", "IAA",
					"Stock Withdrawal", "Total", "Qty", "Value" };

			XSSFCellStyle headingCellStyle = ReportFormats.heading_left(workbook);
			XSSFCellStyle columnHeadingStyle = ReportFormats.columnHeading(workbook);
			XSSFCellStyle cellAlignment = ReportFormats.setCellAlignment(workbook, ALIGN_RIGHT);
			
			ApachePoiExcelFormat xlsxExcelFormat = new ApachePoiExcelFormat();
			CellStyle sheetHeading = xlsxExcelFormat.SheetHeading(workbook);
			CellStyle columnHeading = xlsxExcelFormat.columnHeading(workbook);
			CellStyle leftAlign = xlsxExcelFormat.dataLeftAlign(workbook);
			CellStyle leftAlignB = xlsxExcelFormat.dataBoldLeftAlign(workbook);
			CellStyle columnSubHeadingBlue = xlsxExcelFormat.columnSubHeadingBlue(workbook);
			CellStyle columnSubHeadingLemon = xlsxExcelFormat.columnSubHeadingLemon(workbook);
			CellStyle columnSubHeadingGold = xlsxExcelFormat.columnSubHeadingGold(workbook);
			CellStyle dataRightAlignWthDecimal = xlsxExcelFormat.dataRightAlignWthDecimal(workbook);
			CellStyle dataBoldRightAlign = xlsxExcelFormat.dataBoldRightAlign(workbook);

			XSSFFont tableHeaderFont = workbook.createFont();
			tableHeaderFont.setFontHeightInPoints((short) 12);
			tableHeaderFont.setBold(true);
			tableHeaderFont.setColor(IndexedColors.BLACK.index);
			CellStyle prodstyle = workbook.createCellStyle();
			prodstyle.setFillForegroundColor(IndexedColors.CORNFLOWER_BLUE.index);
			prodstyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);
			prodstyle.setBorderTop(BorderStyle.THIN);
			prodstyle.setTopBorderColor(IndexedColors.BLACK.index);
			prodstyle.setBorderBottom(BorderStyle.THIN);
			prodstyle.setBottomBorderColor(IndexedColors.BLACK.index);
			prodstyle.setBorderLeft(BorderStyle.THIN);
			prodstyle.setLeftBorderColor(IndexedColors.BLACK.index);
			prodstyle.setBorderRight(BorderStyle.THIN);
			prodstyle.setRightBorderColor(IndexedColors.BLACK.index);
			prodstyle.setFont(tableHeaderFont);
			DataFormat format = workbook.createDataFormat();
			prodstyle.setDataFormat(format.getFormat("0.00"));
			prodstyle.setAlignment(HorizontalAlignment.RIGHT);
			
			row = sheet.createRow(rowCount);
			cell = row.createCell(colCount);
			sheet.addMergedRegion(new CellRangeAddress(rowCount, rowCount, colCount, colCount + headings.length));
			cell.setCellValue(companyname);
			cell.setCellStyle(headingCellStyle);
			rowCount++;

			row = sheet.createRow(rowCount);
			cell = row.createCell(colCount);
			sheet.addMergedRegion(new CellRangeAddress(rowCount, rowCount, colCount, colCount + headings.length ));
			cell.setCellValue("Non Saleable Batchwise Stock Statment");
			cell.setCellStyle(headingCellStyle);
			rowCount++;

			row = sheet.createRow(rowCount);
			cell = row.createCell(colCount);
			sheet.addMergedRegion(new CellRangeAddress(rowCount, rowCount, colCount, colCount + headings.length ));
			cell.setCellValue("Period: "+MedicoResources.convertUtilDateToString_DD_MM_YYYY_(bean.getStartDate())+" to "+MedicoResources.convertUtilDateToString_DD_MM_YYYY_(bean.getEndDate()));
			cell.setCellStyle(headingCellStyle);

			rowCount += 1;
			row = sheet.createRow(rowCount);

			
			
			
			
//			 for  merging cell 
			for (int i=0;i<headings.length;i++) {
				
				if(i==0) {
					sheet.addMergedRegion(new CellRangeAddress(rowCount, rowCount, 0, 1));
				}
				if(i==3) {
					sheet.addMergedRegion(new CellRangeAddress(rowCount, rowCount, 4, 5));
				}
				
				if(i==4) {
					sheet.addMergedRegion(new CellRangeAddress(rowCount, rowCount, 6, 8));
				}
				
				
				if(i==5) {
					sheet.addMergedRegion(new CellRangeAddress(rowCount, rowCount, 9, 11));
				}
				
				if(i==6) {
					sheet.addMergedRegion(new CellRangeAddress(rowCount, rowCount, 12, 13));
				}
				
				
				cell = ReportFormats.cell(row, colCount, headings[i], columnHeadingStyle);
				
				colCount++;
			}

//			sheet.addMergedRegion(new CellRangeAddress(rowCount, rowCount, 0, 1));
//			cell = ReportFormats.cell(row, colCount, headings[0], columnHeadingStyle);
//			colCount++;
//			
//			
//
//			cell = ReportFormats.cell(row, colCount, headings[1], columnHeadingStyle);
//			colCount++;
//			
//			cell = ReportFormats.cell(row, colCount, headings[2], columnHeadingStyle);
//			colCount++;
//			
////			sheet.addMergedRegion(new CellRangeAddress(rowCount, rowCount, 4, 5));
//			cell = ReportFormats.cell(row, colCount, headings[3], columnHeadingStyle);
//			colCount++;
//			
////			
////			sheet.addMergedRegion(new CellRangeAddress(rowCount, rowCount, 6, 8));
//			cell = ReportFormats.cell(row, colCount, headings[4], columnHeadingStyle);
//			colCount++;
//			
////			sheet.addMergedRegion(new CellRangeAddress(rowCount, rowCount, 9, 11));
//			cell = ReportFormats.cell(row, colCount, headings[5], columnHeadingStyle);
//			colCount++;
//			
////			sheet.addMergedRegion(new CellRangeAddress(rowCount, rowCount, 13, 15));
//			cell = ReportFormats.cell(row, colCount, headings[6], columnHeadingStyle);
//			colCount++;
			
			
			sheet.groupRow(0, 2);
			sheet.createFreezePane(0, 5);

			rowCount++;
			colCount = 0;

			row = sheet.createRow(rowCount);

			
			
			for (String head : headings1) {
				cell = ReportFormats.cell(row, colCount, head, columnHeadingStyle);
				colCount++;
			}

			rowCount++;
			colCount = 0;

			String old_div = "", new_div = "";
			String new_stock = "", old_stock = "";
			String new_prod = "", old_prod = "";

			BigDecimal ptot_open_stk = BigDecimal.ZERO;
			BigDecimal ptot_out_iaa = BigDecimal.ZERO;
			BigDecimal ptot_out_stk_wth = BigDecimal.ZERO;
			BigDecimal ptot_out_total = BigDecimal.ZERO;
			BigDecimal ptot_close_stk = BigDecimal.ZERO;

			BigDecimal gtot_open_stk = BigDecimal.ZERO;
			BigDecimal gtot_out_iaa = BigDecimal.ZERO;
			BigDecimal gtot_out_stk_wth = BigDecimal.ZERO;
			BigDecimal gtot_out_total = BigDecimal.ZERO;
			BigDecimal gtot_close_stk = BigDecimal.ZERO;
			

			BigDecimal side_tot_inward = BigDecimal.ZERO;
			BigDecimal side_tot_outward = BigDecimal.ZERO;
//			BigDecimal tot_withheldQty = BigDecimal.ZERO;
			BigDecimal gtot_withheldQty = BigDecimal.ZERO;
			
			for (Stock_Statement_Non_Sale_VET data : DataList) {

				new_div = data.getTeam() == null ? "" : data.getTeam();
				new_stock = data.getStock_type_desc() == null ? "" : data.getStock_type_desc();
				new_prod = data.getProduct_name() == null ? "" : data.getProduct_name();
				side_tot_inward = BigDecimal.ZERO;
				side_tot_outward = BigDecimal.ZERO;

				rowCount++;

				if (!old_stock.equalsIgnoreCase(new_stock)
						|| (old_stock.equalsIgnoreCase(new_stock) && !old_div.equalsIgnoreCase(new_div))) {
					if (!old_stock.equalsIgnoreCase("")) {
						colCount = 0;
						row = sheet.createRow(rowCount);

						cell = ReportFormats.cell(row, colCount, "Product Total", columnHeadingStyle);
						colCount++;

						for (int i = 0; i <= 3; i++) {
							cell = ReportFormats.cell(row, colCount, "", columnHeadingStyle);
							colCount++;
						}

						cell = ReportFormats.cellNum(row, colCount, ptot_open_stk.doubleValue(), (XSSFCellStyle) prodstyle);
						colCount++;

						for (int i = 0; i <= 2; i++) {
							cell = ReportFormats.cell(row, colCount, "", columnHeadingStyle);
							colCount++;
						}

						cell = ReportFormats.cell(row, colCount, ptot_out_iaa.toString(), (XSSFCellStyle) prodstyle);
						colCount++;

						cell = ReportFormats.cell(row, colCount, ptot_out_stk_wth.toString(), (XSSFCellStyle) prodstyle);
						colCount++;

						cell = ReportFormats.cell(row, colCount, ptot_out_total.toString(), (XSSFCellStyle) prodstyle);
						colCount++;

						cell = ReportFormats.cell(row, colCount, "", columnHeadingStyle);
						colCount++;

						cell = ReportFormats.cell(row, colCount, ptot_close_stk.toString(), (XSSFCellStyle) prodstyle);
						colCount++;

//						cell = ReportFormats.cell(row, colCount, tot_withheldQty.toString(), (XSSFCellStyle) prodstyle);
//						colCount++;
						
						ptot_open_stk = BigDecimal.ZERO;
						ptot_out_iaa = BigDecimal.ZERO;
						ptot_out_stk_wth = BigDecimal.ZERO;
						ptot_out_total = BigDecimal.ZERO;
						ptot_close_stk = BigDecimal.ZERO;
//						tot_withheldQty = BigDecimal.ZERO;
						rowCount++;
					}
				}

				if (!old_div.equalsIgnoreCase(new_div)) {
					colCount = 0;
					row = sheet.createRow(rowCount);

					cell = ReportFormats.cell(row, colCount, "Division : "+new_div, null);
					colCount++;
					colCount++;

					for (int i = 0; i <= 14; i++) {
						cell = ReportFormats.cell(row, colCount, "", null);
						colCount++;
					}
					colCount++;
					rowCount++;
				}

				if (!old_stock.equalsIgnoreCase(new_stock)
						|| (old_stock.equalsIgnoreCase(new_stock) && !old_div.equalsIgnoreCase(new_div))) {

					colCount = 0;
					row = sheet.createRow(rowCount);

					cell = ReportFormats.cell(row, colCount, "Stock : " +new_stock, null);
					colCount++;

					for (int i = 0; i <= 14; i++) {
						cell = ReportFormats.cell(row, colCount, "", null);
						colCount++;
					}

					colCount++;
					rowCount++;

				}

				side_tot_inward = data.getGrn_rej().add(data.getIaa_in_value());
				side_tot_outward = data.getSw_out_value().add(data.getIaa_out_value());

				colCount = 0;

				row = sheet.createRow(rowCount);

				cell = ReportFormats.cell(row, colCount, data.getProduct_code(), null);
				colCount++;

				if (!new_prod.equalsIgnoreCase(old_prod)
						|| (new_prod.equalsIgnoreCase(old_prod) && !old_div.equalsIgnoreCase(new_div))
						|| (new_prod.equalsIgnoreCase(old_prod) && !old_stock.equalsIgnoreCase(new_stock))) {

					cell = ReportFormats.cell(row, colCount, data.getProduct_name(), (XSSFCellStyle) leftAlign);
				} else {
					cell = ReportFormats.cell(row, colCount, "", (XSSFCellStyle) leftAlign);
				}

				colCount++;

				cell = ReportFormats.cell(row, colCount, data.getPack_disp_nm(), (XSSFCellStyle) leftAlign);
				colCount++;

				cell = ReportFormats.cell(row, colCount, data.getBatch_no(), (XSSFCellStyle) leftAlign);
				colCount++;

				cell = ReportFormats.cellNum(row, colCount, data.getBatch_open_stock().doubleValue(), (XSSFCellStyle) dataRightAlignWthDecimal);
				colCount++;

				cell = ReportFormats.cellNum(row, colCount, data.getOpen_stock_value().doubleValue(), (XSSFCellStyle) dataRightAlignWthDecimal);
				colCount++;

				cell = ReportFormats.cellNum(row, colCount, data.getGrn_rej().doubleValue(), (XSSFCellStyle) dataRightAlignWthDecimal);
				colCount++;

				cell = ReportFormats.cellNum(row, colCount, data.getIaa_in().doubleValue(), (XSSFCellStyle) dataRightAlignWthDecimal);
				colCount++;

				cell = ReportFormats.cellNum(row, colCount, side_tot_inward.doubleValue(), (XSSFCellStyle) dataRightAlignWthDecimal);
				colCount++;

				data.setIn_ward_total(side_tot_inward);
				cell = ReportFormats.cellNum(row, colCount, data.getIaa_out().doubleValue(), (XSSFCellStyle) dataRightAlignWthDecimal);
				colCount++;

				cell = ReportFormats.cellNum(row, colCount, data.getSw_out().doubleValue(), (XSSFCellStyle) dataRightAlignWthDecimal);
				colCount++;

				cell = ReportFormats.cellNum(row, colCount, side_tot_outward.doubleValue(), (XSSFCellStyle) dataRightAlignWthDecimal);
				colCount++;
				
				data.setOut_ward_total(side_tot_outward);

				cell = ReportFormats.cellNum(row, colCount, data.getClos_qty().doubleValue(), (XSSFCellStyle) dataRightAlignWthDecimal);
				colCount++;

				cell = ReportFormats.cellNum(row, colCount, data.getClos_value().doubleValue(), (XSSFCellStyle) dataRightAlignWthDecimal);
				colCount++;

//				cell = ReportFormats.cellNum(row, colCount, data.getBatch_with_held_qty().doubleValue(), (XSSFCellStyle) dataRightAlignWthDecimal);
//				colCount++;
				
				// Calculations

				ptot_open_stk = ptot_open_stk.add(data.getOpen_stock_value());
				ptot_out_iaa = ptot_out_iaa.add(data.getIaa_out_value());
				ptot_out_stk_wth = ptot_out_stk_wth.add(data.getSw_out_value());
				ptot_out_total = ptot_out_total.add(side_tot_outward);
				ptot_close_stk = ptot_close_stk.add(data.getClos_value());

				gtot_open_stk = gtot_open_stk.add(data.getOpen_stock_value());
				gtot_out_iaa = gtot_out_iaa.add(data.getIaa_out_value());
				gtot_out_stk_wth = gtot_out_stk_wth.add(data.getSw_out_value());
				gtot_out_total = gtot_out_total.add(side_tot_outward);
				gtot_close_stk = gtot_close_stk.add(data.getClos_value());
//				gtot_withheldQty = gtot_withheldQty.add(data.getBatch_with_held_qty());
				
//				tot_withheldQty = tot_withheldQty.add(data.getBatch_with_held_qty());

				old_div = new_div;
				old_stock = new_stock;
				old_prod = new_prod;

				rowCount++;

			}

			colCount = 0;
			row = sheet.createRow(rowCount);

			cell = ReportFormats.cell(row, colCount, "Product Total", columnHeadingStyle);
			colCount++;

			for (int i = 0; i <= 3; i++) {
				cell = ReportFormats.cell(row, colCount, "", columnHeadingStyle);
				colCount++;
			}

			cell = ReportFormats.cellNum(row, colCount, ptot_open_stk.doubleValue(), (XSSFCellStyle) prodstyle);
			colCount++;

			for (int i = 0; i <= 2; i++) {
				cell = ReportFormats.cell(row, colCount, "", columnHeadingStyle);
				colCount++;
			}

			cell = ReportFormats.cellNum(row, colCount, ptot_out_iaa.doubleValue(), (XSSFCellStyle) prodstyle);
			colCount++;

			cell = ReportFormats.cellNum(row, colCount, ptot_out_stk_wth.doubleValue(), (XSSFCellStyle) prodstyle);
			colCount++;

			cell = ReportFormats.cellNum(row, colCount, ptot_out_total.doubleValue(), (XSSFCellStyle) prodstyle);
			colCount++;

			cell = ReportFormats.cell(row, colCount, "", columnHeadingStyle);
			colCount++;

			cell = ReportFormats.cellNum(row, colCount, ptot_close_stk.doubleValue(), (XSSFCellStyle) prodstyle);
			colCount++;
//			
//			cell = ReportFormats.cellNum(row, colCount, tot_withheldQty.doubleValue(), (XSSFCellStyle) prodstyle);
//			colCount++;

			ptot_open_stk = BigDecimal.ZERO;
			ptot_out_iaa = BigDecimal.ZERO;
			ptot_out_stk_wth = BigDecimal.ZERO;
			ptot_out_total = BigDecimal.ZERO;
			ptot_close_stk = BigDecimal.ZERO;

			rowCount++;
			colCount = 0;

			row = sheet.createRow(rowCount);

			cell = ReportFormats.cell(row, colCount, "Grand Total", null);
			colCount++;

			for (int i = 0; i <= 3; i++) {
				cell = ReportFormats.cell(row, colCount, "", cellAlignment);
				colCount++;
			}

			cell = ReportFormats.cellNum(row, colCount, gtot_open_stk.doubleValue(), (XSSFCellStyle) dataRightAlignWthDecimal);
			colCount++;

			for (int i = 0; i <= 2; i++) {
				cell = ReportFormats.cell(row, colCount, "", cellAlignment);
				colCount++;
			}

			cell = ReportFormats.cellNum(row, colCount, gtot_out_iaa.doubleValue(), (XSSFCellStyle) dataRightAlignWthDecimal);
			colCount++;

			cell = ReportFormats.cellNum(row, colCount, gtot_out_stk_wth.doubleValue(), (XSSFCellStyle) dataRightAlignWthDecimal);
			colCount++;

			cell = ReportFormats.cellNum(row, colCount, gtot_out_total.doubleValue(), (XSSFCellStyle) dataRightAlignWthDecimal);
			colCount++;

			cell = ReportFormats.cell(row, colCount, "", (XSSFCellStyle) dataRightAlignWthDecimal);
			colCount++;

			String d=df.format(gtot_close_stk.doubleValue());
			System.out.println("d ::"+d);
			cell = ReportFormats.cellNum(row, colCount,Double.parseDouble(d), (XSSFCellStyle) dataRightAlignWthDecimal);
			colCount++;
			
			cell = ReportFormats.cellNum(row, colCount,gtot_withheldQty.doubleValue(), (XSSFCellStyle) dataRightAlignWthDecimal);
			colCount++;

			FileOutputStream fileOutputStream = new FileOutputStream(filepath);
			workbook.write(fileOutputStream);
			
			filename=usermasterservice.generateExcellock(filepath, filename,empId,".xlsx");
			
	
			map.put("filename", filename);
			map.put("list", DataList);

		} finally

		{
			if (workbook != null) {
				workbook.close();
			}
		}
		return map;
	}

	
	

}
