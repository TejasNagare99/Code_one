package com.excel.service;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Service;

import com.excel.bean.ReportBean;
import com.excel.model.FieldMasterDownload;
import com.excel.model.FieldStaffCsvDownload;
import com.excel.model.Fieldstaff_After_mobile_No_Update_CheckList;
import com.excel.model.ProductMasterAttrib;
import com.excel.utility.ApachePoiExcelFormat;
import com.excel.utility.CodifyErrors;
import com.excel.utility.ExcelFormat;
import com.excel.utility.MedicoConstants;
import com.excel.utility.ReportFormats;
import com.excel.utility.Utility;
import com.opencsv.CSVWriter;
@Service
public class FieldMasterDownloadServiceImpl implements FieldMasterDownloadService{

	@Override
	public String GenerateFieldMasterDownloadReport(ReportBean bean, List<FieldMasterDownload> DataList,String companyname,String username) throws Exception {
		System.out.println("FieldMasterDownload.excelDownload()");
		File ff = null;
		HSSFWorkbook wb = null;
		StringBuffer path = null;
		long l = new Date().getTime();
		String fileName = "FieldMasterDownload_" + l + ".xls";
		String filePath = MedicoConstants.REPORT_FILE_PATH;
		path = new StringBuffer(filePath).append("\\");
		path.append(fileName);
		ff = new File(path.toString());
		System.out.println("FieldMasterDownload.getExcel()");
		try {
			

			 wb = new HSSFWorkbook();

			HSSFSheet ws = wb.createSheet("PRODUCT MASTER");

			ExcelFormat excelFormat = new ExcelFormat();
			CellStyle tabHeading = excelFormat.tabHeading(wb);
			CellStyle decimalformat = excelFormat.decimalformat(wb);
			CellStyle doubleformat = excelFormat.doubleformat(wb);
			CellStyle dataleft = excelFormat.dataleft(wb);
			CellStyle dataright = excelFormat.dataright(wb);

			HSSFRow row = null;
			HSSFCell cell = null;
			int rowCount = 0, col = 0;

			row = ws.createRow(rowCount);
			cell = row.createCell(col);
			cell.setCellValue("FSTAFF_ID");
			cell.setCellStyle(tabHeading);
			col++;
			
			cell = row.createCell(col);
			cell.setCellValue("FSTAFF_DIV_ID");
			cell.setCellStyle(tabHeading);
			col++;
			
			cell = row.createCell(col);
			cell.setCellValue("DIV_DISP_NM");
			cell.setCellStyle(tabHeading);
			col++;
			
			cell = row.createCell(col);
			cell.setCellValue("FSTAFF_LEVEL_CODE");
			cell.setCellStyle(tabHeading);
			col++;

			// added by shivram:05-05-2020
			cell = row.createCell(col);
			cell.setCellValue("FSTAFF_DESIGNATION");
			cell.setCellStyle(tabHeading);
			col++;

			
			cell = row.createCell(col);
			cell.setCellValue("FSTAFF_ZONENAME");
			cell.setCellStyle(tabHeading);
			col++;

			cell = row.createCell(col);
			cell.setCellValue("FSTAFF_EMPLOYEE_NO");
			cell.setCellStyle(tabHeading);
			col++;

			cell = row.createCell(col);
			cell.setCellValue("FSTAFF_NAME");
			cell.setCellStyle(tabHeading);
			col++;

			cell = row.createCell(col);
			cell.setCellValue("FSTAFF_DISPLAY_NAME");
			cell.setCellStyle(tabHeading);
			col++;
		
			cell = row.createCell(col);
			cell.setCellValue("FSTAFF_MAP_CODE1");
			cell.setCellStyle(tabHeading);
			col++;
			
			cell = row.createCell(col);
			cell.setCellValue("FSTAFF_MAP_CODE2");
			cell.setCellStyle(tabHeading);
			col++;
			
			cell = row.createCell(col);
			cell.setCellValue("HQ_NAME");
			cell.setCellStyle(tabHeading);
			col++;
			
			cell = row.createCell(col);
			cell.setCellValue("FSTAFF_NAME RM");
			cell.setCellStyle(tabHeading);
			col++;
			
			cell = row.createCell(col);
			cell.setCellValue("FSTAFF_NAME AM");
			cell.setCellStyle(tabHeading);
			col++;
			
			cell = row.createCell(col);
			cell.setCellValue("FSTAFF_ADDR1");
			cell.setCellStyle(tabHeading);
			col++;
			
			cell = row.createCell(col);
			cell.setCellValue("FSTAFF_ADDR2");
			cell.setCellStyle(tabHeading);
			col++;
			
			cell = row.createCell(col);
			cell.setCellValue("FSTAFF_ADDR3");
			cell.setCellStyle(tabHeading);
			col++;

			
			cell = row.createCell(col);
			cell.setCellValue("FSTAFF_ADDR4");
			cell.setCellStyle(tabHeading);
			col++;

			
			cell = row.createCell(col);
			cell.setCellValue("MOBILE");
			cell.setCellStyle(tabHeading);
			col++;

			
			cell = row.createCell(col);
			cell.setCellValue("FSTAFF_DESTINATION");
			cell.setCellStyle(tabHeading);
			col++;
			
			cell = row.createCell(col);
			cell.setCellValue("FSTAFF EMAIL");
			cell.setCellStyle(tabHeading);
			col++;
			rowCount++;

	
			for (FieldMasterDownload common:DataList) {
			
				col = 0;
				
				row = ws.createRow(rowCount);
				cell = row.createCell(col);
				cell.setCellValue( Integer
						.parseInt(common.getFSTAFF_ID() == null ? "0" : common
								.getFSTAFF_ID()));
				cell.setCellStyle(decimalformat);
				col++;

				cell = row.createCell(col);
				cell.setCellValue( Integer
						.parseInt(common.getFSTAFF_DIV_ID() == null ? "0" : common
								.getFSTAFF_DIV_ID()));
				
				cell.setCellStyle(decimalformat);
				col++;

				cell = row.createCell(col);
				cell.setCellValue( common.getDIV_DISP_NM());
				cell.setCellStyle(dataleft);
				col++;

				cell = row.createCell(col);
				cell.setCellValue( common.getFSTAFF_LEVEL_CODE());
				cell.setCellStyle(dataleft);
				col++;

				// added by shivram:05-05-2020
				cell = row.createCell(col);
				cell.setCellValue( common.getFSTAFF_DESIG());
				cell.setCellStyle(dataleft);
				col++;
				
				cell = row.createCell(col);
				cell.setCellValue( common.getFSTAFF_ZONENAME());
				cell.setCellStyle(dataleft);
				col++;
				
				cell = row.createCell(col);
				cell.setCellValue( common.getFSTAFF_EMPLOYEE_NO());
				cell.setCellStyle(dataleft);
				col++;

				cell = row.createCell(col);
				cell.setCellValue(common.getFSTAFF_NAME());
				cell.setCellStyle(dataleft);
				col++;

				cell = row.createCell(col);
				cell.setCellValue(common.getFSTAFF_DISPLAY_NAME());
				cell.setCellStyle(dataleft);
				col++;

				cell = row.createCell(col);
				cell.setCellValue(common.getFSTAFF_MAP_CODE1());
				cell.setCellStyle(dataleft);
				col++;

				cell = row.createCell(col);
				cell.setCellValue(common.getFSTAFF_MAP_CODE2());
				cell.setCellStyle(dataleft);
				col++;

				cell = row.createCell(col);
				cell.setCellValue(common.getHQ_NAME());
				cell.setCellStyle(dataleft);
				col++;
				
				cell = row.createCell(col);
				cell.setCellValue(common.getRM());
				cell.setCellStyle(dataleft);
				col++;
				
				cell = row.createCell(col);
				cell.setCellValue( common.getAM());
				cell.setCellStyle(dataleft);
				col++;

				cell = row.createCell(col);
				cell.setCellValue( common.getFSTAFF_ADDR1());
				cell.setCellStyle(dataleft);
				col++;

				cell = row.createCell(col);
				cell.setCellValue( common.getFSTAFF_ADDR2());
				cell.setCellStyle(dataleft);
				col++;

				cell = row.createCell(col);
				cell.setCellValue( common.getFSTAFF_ADDR3());
				cell.setCellStyle(dataleft);
				col++;
				
				cell = row.createCell(col);
				cell.setCellValue( common.getFSTAFF_ADDR4());
				cell.setCellStyle(dataleft);
				col++;

				cell = row.createCell(col);
				cell.setCellValue(common.getFSTAFF_MOBILE());
				cell.setCellStyle(dataright);
				col++;

				cell = row.createCell(col);
				cell.setCellValue(common.getFSTAFF_DESTINATION());
				cell.setCellStyle(dataleft);
				col++;
				
				cell = row.createCell(col);
				cell.setCellValue(common.getFSTAFF_EMAIL());
				cell.setCellStyle(dataleft);
				col++;

				rowCount++;
			}

		} catch(Exception e){
			System.out.println("Error Occurred :" +e.getMessage());// uncomment asneeded --;
		}
		finally {
			 FileOutputStream fileOut = new FileOutputStream(path.toString());
			 wb.write(fileOut);
			    fileOut.close();
			    wb.close();
		}
		return fileName;
	}
	
	@Override
	public String generateFstaffCsv(List<FieldStaffCsvDownload> lst) throws Exception {

		SimpleDateFormat sdf = new SimpleDateFormat("yyyy_MM_dd_HH_mm");

		CSVWriter writer = null;
		File ff = null;
		StringBuffer path = null;
		String fileName = "";
		try {

			fileName = "Fstaff_blank_mobile_update_" + sdf.format(new Date()) + ".csv";
			String filePath = MedicoConstants.REPORT_FILE_PATH;
			path = new StringBuffer(filePath).append("\\");
			path.append(fileName);
			ff = new File(path.toString());
			System.out.println("filename " + fileName);
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

			FileWriter fwriter = new FileWriter(path.toString());
			writer = new CSVWriter(fwriter, ',', CSVWriter.NO_QUOTE_CHARACTER);
			String[] header = new String[9];
			header[0] = "DIVISION";
//			header[1] = "FSTAFF LEVEL CODE";
			header[1] = "DESIGNATION";
			header[2] = "FSTAFF ID";
			header[3] = "EMPLOYEE NO";
			header[4] = "TERR CODE";
			header[5] = "DISPLAY NAME";
			header[6] = "MOBILE NO";
			header[7] = "INSERTED DATE";
			header[8] = "MODIFIED DATE";

		
			List<String[]> li = new ArrayList<String[]>();
			li.add(header);
			System.out.println("Number of fstaff" + lst.size());
			for (FieldStaffCsvDownload fs : lst) {
				String[] arr = new String[9];
				arr[0]= fs.getDivision();
//				arr[1] = fs.getFstaff_level_code();
				arr[1] = fs.getDesig();
				arr[2] = fs.getFstaff_id();
				arr[3] = fs.getFstaff_employee_no();
				arr[4] = fs.getFstaff_terr_code();
				arr[5] = fs.getFstaff_display_name();
				arr[6] = fs.getFstaff_mobile();
				
				Date insDt = fs.getFstaff_ins_dt();
				if (insDt == null || "1900".equals(new SimpleDateFormat("yyyy").format(insDt))) {
				    arr[7] = "";
				} else {
				    SimpleDateFormat f = new SimpleDateFormat("yyyy-MM-dd");
				    arr[7] = f.format(insDt);  
				}
	
				Date modDt = fs.getFstaff_mod_dt();
				if (modDt == null || "1900".equals(new SimpleDateFormat("yyyy").format(modDt))) {
				    arr[8] = "";
				} else {
				    SimpleDateFormat f = new SimpleDateFormat("yyyy-MM-dd");
				    arr[8] = f.format(modDt);  
				}

				li.add(arr);
			}
			writer.writeAll(li);

			File csvfile = new File(path.toString());
			FileInputStream fileInputStream = new FileInputStream(csvfile);
			writer.close();

		} catch (Exception e) {
			System.out.println("Error Occurred :" + e.getMessage());// uncomment asneeded --;
			throw e;
		}
		return fileName;
	}
	
	
	@Override
	public String generateUpdatedFstaffMobileNoReport(List<Fieldstaff_After_mobile_No_Update_CheckList> list)
			throws Exception {
		String filename = "FSTAFF_MOBIL_UPDT_CHECKLIST_" + new Date().getTime() + ".xlsx";
		String filePath = MedicoConstants.REPORT_FILE_PATH + filename;
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm");
		XSSFWorkbook workbook = null;
		try {
			workbook = new XSSFWorkbook();
			File f = new File(MedicoConstants.REPORT_FILE_PATH);
			if (!f.exists()) {
				if (f.mkdirs()) {
				} else {
					throw new RuntimeException("Could not create directory");
				}
			}
			XSSFSheet sheet = workbook.createSheet("FSTAFF_MOBILE_UPDT_CHECKLIST");
			
			XSSFRow row = null;
			XSSFCell cell = null;
			int rowCount = 0;
			int colCount = 0;
			
			String headings[] = {"DIVISION", "DESIGNATION", "FSTAFF ID","EMPLOYEE NO", "TERR CODE", "FIELDSTAFF NAME", "FSTAFF MOBILE CSV", "FSTAFF MOBILE", "STATUS"
					, "UPDATED USER ID", "UPDATED BY", "UPDATED DATE", "IP ADDRESS", "CSV FILE NAME"};
			
			XSSFCellStyle headingCellStyle = ReportFormats.heading(workbook);
			XSSFCellStyle columnHeadingStyle = ReportFormats.columnHeading(workbook);
						
			row = sheet.createRow(rowCount);
			cell = row.createCell(colCount);
			sheet.addMergedRegion(new CellRangeAddress(rowCount, rowCount, colCount, colCount + headings.length - 1));
			cell.setCellValue("Fstaff Mobile Update Checklist Report as on "+ sdf.format(new Date()));
			cell.setCellStyle(headingCellStyle);
			rowCount++;

			row = sheet.createRow(rowCount);
			
			for (String heading : headings) {
				cell = ReportFormats.cell(row, colCount, heading, columnHeadingStyle);
				colCount++;
			}
			
			
//			SimpleDateFormat outputFormat = new SimpleDateFormat("dd/MM/yyyy");
			
			rowCount += 1;
			for (Fieldstaff_After_mobile_No_Update_CheckList data : list) {
				colCount = 0;
				row = sheet.createRow(rowCount);
							
				cell = ReportFormats.cell(row, colCount, data.getDivision(), null);
				colCount++;
				
//				cell = ReportFormats.cell(row, colCount, data.getFstaff_level_code(), null);
//				colCount++;
				
				cell = ReportFormats.cell(row, colCount, data.getDesig(), null);
				colCount++;
				
				cell = ReportFormats.cell(row, colCount, data.getFstaff_id(), null);
				colCount++;
				
				cell = ReportFormats.cell(row, colCount, data.getFstaff_employee_no(), null);
				colCount++;
				
				cell = ReportFormats.cell(row, colCount, data.getFstaff_terr_code(), null);
				colCount++;
				
				cell = ReportFormats.cell(row, colCount, data.getFstaff_display_name(), null);
				colCount++;
				
				cell = ReportFormats.cell(row, colCount, data.getFstaff_mobile_csv(), null);
				colCount++;
				
				cell = ReportFormats.cell(row, colCount, data.getFstaff_mobile(), null);
				colCount++;
				
				cell = ReportFormats.cell(row, colCount, data.getFstaff_status(), null);
				colCount++;
				
				cell = ReportFormats.cell(row, colCount, data.getFstaff_ins_usr_id(), null);
				colCount++;
				
				cell = ReportFormats.cell(row, colCount, data.getUser_name(), null);
				colCount++;
				
				cell = ReportFormats.cell(row, colCount, data.getFstaff_ins_dt(), null);
				colCount++;
				
				cell = ReportFormats.cell(row, colCount, data.getFstaff_ins_ip_add(), null);
				colCount++;
				
				cell = ReportFormats.cell(row, colCount, data.getCsv_file_name(), null);
				colCount++;
				
				rowCount++;
			}
			FileOutputStream fileOutputStream = new FileOutputStream(filePath);
			workbook.write(fileOutputStream);
		} finally {
			if (workbook != null) { workbook.close(); }
		}
		return filename;
	}




}