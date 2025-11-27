package com.excel.service;

import java.io.File;
import java.io.FileOutputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.excel.bean.FstaffMas_PrxBean;
import com.excel.bean.ReportBean;
import com.excel.model.Dg_veeva_emp;
import com.excel.model.Dg_veeva_pos;
import com.excel.model.Dg_veeva_pos_emp;
import com.excel.model.Temp_fstaffmas_prx_error;
import com.excel.model.Usermaster;
import com.excel.repository.UserMasterRepository;
import com.excel.security.PasswordManager;
import com.excel.utility.ApachePoiExcelFormat;
import com.excel.utility.MedicoConstants;
import com.excel.utility.MedicoResources;

import net.lingala.zip4j.core.ZipFile;
import net.lingala.zip4j.model.ZipParameters;
import net.lingala.zip4j.util.Zip4jConstants;

@Service
public class PforceRxReportGenerationImpl implements PforceRxReportGeneration,MedicoConstants{
	@Autowired private UserMasterService usermasterservice;

	@Autowired UserMasterRepository usermasterrepository;
	@Override
	public String GeneratePforceRxFieldstaffInsertErrorReport(List<Temp_fstaffmas_prx_error> lst,ReportBean bean,String company,String empId) throws Exception {
		// TODO Auto-generated method stub
		int rowCount = 0,colCount=0;
		XSSFWorkbook wb=null;
		String filename = "PforceRxfieldstaffErrorReport" + new Date().getTime() + ".xlsx";
		try {

			SimpleDateFormat sdf=new SimpleDateFormat("dd-MM-yyyy");
			String filepath = REPORT_FILE_PATH + filename;
			
				wb=new XSSFWorkbook();
				File f = new File(REPORT_FILE_PATH);
				if (!f.exists()) {
					if (f.mkdirs()) {
					} else {
						wb.close();
						throw new RuntimeException("Could not create directory");
					}
				}
				
			XSSFSheet sheet = wb.createSheet("FieldStaffErrorReport");
			ApachePoiExcelFormat xlsxExcelFormat = new ApachePoiExcelFormat();
			XSSFRow row=null;
			XSSFCell cell=null;
			
			 CellStyle sheetHeading = xlsxExcelFormat.SheetHeading(wb);
			 CellStyle sheetHeadingLeft = xlsxExcelFormat.SheetHeading(wb);
			 sheetHeadingLeft.setAlignment(HorizontalAlignment.LEFT);
			 CellStyle columnHeading = xlsxExcelFormat.columnHeading(wb);
			 CellStyle columnHeadingRight = xlsxExcelFormat.columnHeading(wb);
			 columnHeadingRight.setAlignment(HorizontalAlignment.RIGHT);
			 CellStyle leftAlign = xlsxExcelFormat.dataLeftAlign(wb);
			 
			 String[] heading = {"Error_date","PRX Div Name","Fstaff level Code","Fstaff Nt Id","Date Time","Terr Code","Fstaff Name",
					 "Fstaff Display Name","Employee Number","Employee Address 1","Employee Address 2","Employee Address 3","Employee Address 4",
					 "Fstaff Destination","Fstaff Pin Code","Fstaff Mobile 2","Fstaff Pan No.","Fstaff Tel No.","Fstaff Mobile","Fstaff Email"
					 ,"Fstaff Transporter","Fstaff Resigned","Fstaff Joining Date","Fstaff Leaving Date","Fstaff Samp Disp Ind","Mgr1 Terr code",
					 "Mgr2 Terr Code","Mgr3 Terr Code","Mgr4 Terr Code","Mgr5 Terr Code","Mgr6 Terr Code","Error Msg","Prx Zip File Name",
					 "Prx File Name"};

			 
			 
			 row = sheet.createRow(rowCount);
			 sheet.addMergedRegion(new CellRangeAddress(rowCount, rowCount, colCount, heading.length-1));
			 cell = row.createCell(colCount);
			 cell.setCellValue(company);
			 cell.setCellStyle(sheetHeading);
			 rowCount++;
			 
			 row = sheet.createRow(rowCount);
			 sheet.addMergedRegion(new CellRangeAddress(rowCount, rowCount, colCount,heading.length-1));
			 cell = row.createCell(colCount);
			 cell.setCellValue("PFORCE RX FIELDSTAFF INSERT ERROR REPORT");
			 cell.setCellStyle(sheetHeading);
			 rowCount++;
			 
			 row = sheet.createRow(rowCount);	
			 sheet.addMergedRegion(new CellRangeAddress(rowCount, rowCount, colCount, heading.length-1));
			 cell = row.createCell(colCount);
			 cell.setCellValue("From "+sdf.format(bean.getStartDate())+" To "+sdf.format(bean.getEndDate()));
			 cell.setCellStyle(sheetHeading);
			 rowCount++;
			 

			 colCount=0;
			 row = sheet.createRow(rowCount);
			 
			 for(String s : heading) {
				 cell = row.createCell(colCount);
				 cell.setCellValue(s);
				 cell.setCellStyle(columnHeading);
				 colCount++;
			 }
			 rowCount ++;
			 colCount=0;
			 
			 
			 for(Temp_fstaffmas_prx_error obj : lst) {
				 row = sheet.createRow(rowCount);
				 
				 cell = row.createCell(colCount);
				 cell.setCellValue(obj.getError_date()==null ? "" : sdf.format(obj.getError_date()));
				 cell.setCellStyle(leftAlign);
				 colCount++;
				 
				 cell = row.createCell(colCount);
				 cell.setCellValue(obj.getPrx_div_name()==null ? "" : obj.getPrx_div_name());
				 cell.setCellStyle(leftAlign);
				 colCount++;
				 
				 cell = row.createCell(colCount);
				 cell.setCellValue(obj.getFstaff_level_code()==null ? "" : obj.getFstaff_level_code());
				 cell.setCellStyle(leftAlign);
				 colCount++;
				 
				 cell = row.createCell(colCount);
				 cell.setCellValue(obj.getFstaff_nt_id()==null ? "" : obj.getFstaff_nt_id());
				 cell.setCellStyle(leftAlign);
				 colCount++;
				 
				 cell = row.createCell(colCount);
				 cell.setCellValue(obj.getDate_time_recd()==null ? "" : obj.getDate_time_recd().toString());
				 cell.setCellStyle(leftAlign);
				 colCount++;
				 
				 cell = row.createCell(colCount);
				 cell.setCellValue(obj.getTerr_code()==null ? "" : obj.getTerr_code());
				 cell.setCellStyle(leftAlign);
				 colCount++;
				 
				 cell = row.createCell(colCount);
				 cell.setCellValue(obj.getFstaff_name()==null ? "" : obj.getFstaff_name());
				 cell.setCellStyle(leftAlign);
				 colCount++;
				 
				 cell = row.createCell(colCount);
				 cell.setCellValue(obj.getFstaff_display_name()==null ? "" : obj.getFstaff_display_name());
				 cell.setCellStyle(leftAlign);
				 colCount++; 
				 
				 cell = row.createCell(colCount);
				 cell.setCellValue(obj.getFstaff_employee_no()==null ? "" : obj.getFstaff_employee_no());
				 cell.setCellStyle(leftAlign);
				 colCount++; 
				 
				 cell = row.createCell(colCount);
				 cell.setCellValue(obj.getFstaff_addr1()==null ? "" : obj.getFstaff_addr1());
				 cell.setCellStyle(leftAlign);
				 colCount++; 
				 
				 cell = row.createCell(colCount);
				 cell.setCellValue(obj.getFstaff_addr2()==null ? "" : obj.getFstaff_addr2());
				 cell.setCellStyle(leftAlign);
				 colCount++; 
				 
				 cell = row.createCell(colCount);
				 cell.setCellValue(obj.getFstaff_addr3()==null ? "" : obj.getFstaff_addr3());
				 cell.setCellStyle(leftAlign);
				 colCount++; 
				 
				 cell = row.createCell(colCount);
				 cell.setCellValue(obj.getFstaff_addr4()==null ? "" : obj.getFstaff_addr4());
				 cell.setCellStyle(leftAlign);
				 colCount++; 
				 
				 cell = row.createCell(colCount);
				 cell.setCellValue(obj.getFstaff_destination()==null ? "" : obj.getFstaff_destination());
				 cell.setCellStyle(leftAlign);
				 colCount++; 
				 
				 cell = row.createCell(colCount);
				 cell.setCellValue(obj.getFstaff_pincode()==null ? "" : obj.getFstaff_pincode());
				 cell.setCellStyle(leftAlign);
				 colCount++; 
				 
				 cell = row.createCell(colCount);
				 cell.setCellValue(obj.getFstaff_mobile2()==null ? "" : obj.getFstaff_mobile2());
				 cell.setCellStyle(leftAlign);
				 colCount++; 
				 
				 cell = row.createCell(colCount);
				 cell.setCellValue(obj.getFstaff_pan_no()==null ? "" : obj.getFstaff_pan_no());
				 cell.setCellStyle(leftAlign);
				 colCount++; 
				 
				 cell = row.createCell(colCount);
				 cell.setCellValue(obj.getFstaff_tel_no()==null ? "" : obj.getFstaff_tel_no());
				 cell.setCellStyle(leftAlign);
				 colCount++; 
				 
				 cell = row.createCell(colCount);
				 cell.setCellValue(obj.getFstaff_mobile()==null ? "" : obj.getFstaff_mobile());
				 cell.setCellStyle(leftAlign);
				 colCount++; 
				 
				 cell = row.createCell(colCount);
				 cell.setCellValue(obj.getFstaff_email()==null ? "" : obj.getFstaff_email());
				 cell.setCellStyle(leftAlign);
				 colCount++; 
				 
				 cell = row.createCell(colCount);
				 cell.setCellValue(obj.getFstaff_transporter()==null ? "" : obj.getFstaff_transporter());
				 cell.setCellStyle(leftAlign);
				 colCount++; 
				 
				 cell = row.createCell(colCount);
				 cell.setCellValue(obj.getFstaff_resigned()==null ? "" : obj.getFstaff_resigned());
				 cell.setCellStyle(leftAlign);
				 colCount++; 
				 
				 cell = row.createCell(colCount);
				 cell.setCellValue(obj.getFstaff_joining_date()==null ? "" : sdf.format(obj.getFstaff_joining_date()));
				 cell.setCellStyle(leftAlign);
				 colCount++; 
				 
				 cell = row.createCell(colCount);
				 cell.setCellValue(obj.getFstaff_leaving_date()==null ? "" : sdf.format(obj.getFstaff_leaving_date()));
				 cell.setCellStyle(leftAlign);
				 colCount++; 
				 
				 cell = row.createCell(colCount);
				 cell.setCellValue(obj.getFstaff_samp_disp_ind()==null ? "" : obj.getFstaff_samp_disp_ind());
				 cell.setCellStyle(leftAlign);
				 colCount++; 
				 
				 
				 cell = row.createCell(colCount);
				 cell.setCellValue(obj.getMgr1_terr_code()==null ? "" : obj.getMgr1_terr_code());
				 cell.setCellStyle(leftAlign);
				 colCount++; 
				 
				 cell = row.createCell(colCount);
				 cell.setCellValue(obj.getMgr2_terr_code()==null ? "" : obj.getMgr2_terr_code());
				 cell.setCellStyle(leftAlign);
				 colCount++; 
				 
				 cell = row.createCell(colCount);
				 cell.setCellValue(obj.getMgr3_terr_code()==null ? "" : obj.getMgr3_terr_code());
				 cell.setCellStyle(leftAlign);
				 colCount++; 
				 
				 cell = row.createCell(colCount);
				 cell.setCellValue(obj.getMgr4_terr_code()==null ? "" : obj.getMgr4_terr_code());
				 cell.setCellStyle(leftAlign);
				 colCount++; 
				 
				 cell = row.createCell(colCount);
				 cell.setCellValue(obj.getMgr5_terr_code()==null ? "" : obj.getMgr5_terr_code());
				 cell.setCellStyle(leftAlign);
				 colCount++; 
				 
				 cell = row.createCell(colCount);
				 cell.setCellValue(obj.getMgr6_terr_code()==null ? "" : obj.getMgr6_terr_code());
				 cell.setCellStyle(leftAlign);
				 colCount++; 
				 
				 cell = row.createCell(colCount);
				 cell.setCellValue(obj.getError_msg()==null ? "" : obj.getError_msg());
				 cell.setCellStyle(leftAlign);
				 colCount++; 
				 
				 cell = row.createCell(colCount);
				 cell.setCellValue(obj.getPrx_zip_file_name()==null ? "" : obj.getPrx_zip_file_name());
				 cell.setCellStyle(leftAlign);
				 colCount++; 
				 
				 cell = row.createCell(colCount);
				 cell.setCellValue(obj.getPrx_file_name()==null ? "" : obj.getPrx_file_name());
				 cell.setCellStyle(leftAlign);
				 colCount++; 
				 
				 colCount=0;
				 rowCount++;
			 }
			 
				FileOutputStream fileOutputStream = new FileOutputStream(filepath);
				wb.write(fileOutputStream);
				
				System.out.println("Excel Generated");
				filename=usermasterservice.generateExcellock(filepath, filename,empId,".xlsx");

		}
		catch (Exception e) {
			// TODO: handle exception
			throw e;
		}
		finally {
			if(wb != null) wb.close();
		}
		return filename;
	}

	@Override
	public String GenerateDgVeevaPosEmpReport(List<Dg_veeva_pos_emp> lst, ReportBean bean, String company,String empId)
			throws Exception {
		// TODO Auto-generated method stub
		int rowCount = 0,colCount=0;
		XSSFWorkbook wb=null;
		String filename = "DgVeevaPosEmp" + new Date().getTime()+ ".xlsx";
		try {
			SimpleDateFormat sdf=new SimpleDateFormat("dd-MM-yyyy");
			String filepath = REPORT_FILE_PATH + filename;
			
				wb=new XSSFWorkbook();
				File f = new File(REPORT_FILE_PATH);
				if (!f.exists()) {
					if (f.mkdirs()) {
					} else {
						wb.close();
						throw new RuntimeException("Could not create directory");
					}
				}
				
			XSSFSheet sheet = wb.createSheet("Veeva Pos Emp");
			ApachePoiExcelFormat xlsxExcelFormat = new ApachePoiExcelFormat();
			XSSFRow row=null;
			XSSFCell cell=null;
			
			 CellStyle sheetHeading = xlsxExcelFormat.SheetHeading(wb);
			 CellStyle sheetHeadingLeft = xlsxExcelFormat.SheetHeading(wb);
			 sheetHeadingLeft.setAlignment(HorizontalAlignment.LEFT);
			 CellStyle columnHeading = xlsxExcelFormat.columnHeading(wb);
			 CellStyle columnHeadingRight = xlsxExcelFormat.columnHeading(wb);
			 columnHeadingRight.setAlignment(HorizontalAlignment.RIGHT);
			 CellStyle leftAlign = xlsxExcelFormat.dataLeftAlign(wb);
			 
			 String[] heading = {"Country Code","Source","Src Sys Id","Posn Src Sys Id","Empl Src Sys Id","Posn Type Code","Posn Type Desc.",
					 "EFF Date","End Date","Create Ts","Last UPD Ts","Updated By","Active",
					 "Del Ts","Empl Source","PRX ZIP File Name","PRX File Name","Date Time","Date Recieved"};

			 
			 
			 row = sheet.createRow(rowCount);
			 sheet.addMergedRegion(new CellRangeAddress(rowCount, rowCount, colCount, heading.length-1));
			 cell = row.createCell(colCount);
			 cell.setCellValue(company);
			 cell.setCellStyle(sheetHeading);
			 rowCount++;
			 
			 row = sheet.createRow(rowCount);
			 sheet.addMergedRegion(new CellRangeAddress(rowCount, rowCount, colCount,heading.length-1));
			 cell = row.createCell(colCount);
			 cell.setCellValue("DG VEEVA POS EMP REPORT");
			 cell.setCellStyle(sheetHeading);
			 rowCount++;
			 
			 row = sheet.createRow(rowCount);	
			 sheet.addMergedRegion(new CellRangeAddress(rowCount, rowCount, colCount, heading.length-1));
			 cell = row.createCell(colCount);
			 cell.setCellValue("From "+sdf.format(bean.getStartDate())+" To "+sdf.format(bean.getEndDate()));
			 cell.setCellStyle(sheetHeading);
			 rowCount++;
			 

			 colCount=0;
			 row = sheet.createRow(rowCount);
			 
			 for(String s : heading) {
				 cell = row.createCell(colCount);
				 cell.setCellValue(s);
				 cell.setCellStyle(columnHeading);
				 colCount++;
			 }
			 rowCount ++;
			 colCount=0;
			 
			 
			 for(Dg_veeva_pos_emp obj : lst) {
				 row = sheet.createRow(rowCount);
				 
				 cell = row.createCell(colCount);
				 cell.setCellValue(obj.getCountry_cd()==null ? "" : obj.getCountry_cd());
				 cell.setCellStyle(leftAlign);
				 colCount++;
				 
				 cell = row.createCell(colCount);
				 cell.setCellValue(obj.getSource()==null ? "" : obj.getSource());
				 cell.setCellStyle(leftAlign);
				 colCount++;
				 
				 cell = row.createCell(colCount);
				 cell.setCellValue(obj.getSrc_sys_id()==null ? "" : obj.getSrc_sys_id());
				 cell.setCellStyle(leftAlign);
				 colCount++;
				 
				 cell = row.createCell(colCount);
				 cell.setCellValue(obj.getPosn_src_sys_id()==null ? "" : obj.getPosn_src_sys_id());
				 cell.setCellStyle(leftAlign);
				 colCount++;
				 
				 cell = row.createCell(colCount);
				 cell.setCellValue(obj.getEmpl_src_sys_id()==null ? "" : obj.getEmpl_src_sys_id());
				 cell.setCellStyle(leftAlign);
				 colCount++;

				 cell = row.createCell(colCount);
				 cell.setCellValue(obj.getPosn_type_cd()==null ? "" : obj.getPosn_type_cd());
				 cell.setCellStyle(leftAlign);
				 colCount++;
				 
				 cell = row.createCell(colCount);
				 cell.setCellValue(obj.getPosn_type_desc()==null ? "" : obj.getPosn_type_desc());
				 cell.setCellStyle(leftAlign);
				 colCount++;
				 
				 cell = row.createCell(colCount);
				 cell.setCellValue(obj.getEff_dt()==null ? "" : sdf.format(MedicoResources.convert_YYYY_MM_DD_HH_MM_SS_toDate(obj.getEff_dt())));
				 cell.setCellStyle(leftAlign);
				 colCount++; 
				 
				 cell = row.createCell(colCount);
				 cell.setCellValue(obj.getEnd_dt()==null ? "" : sdf.format(obj.getEnd_dt()));
				 cell.setCellStyle(leftAlign);
				 colCount++; 
				 
				 cell = row.createCell(colCount);
				 cell.setCellValue(obj.getCreate_ts()==null ? "" : obj.getCreate_ts());
				 cell.setCellStyle(leftAlign);
				 colCount++; 
				 
				 cell = row.createCell(colCount);
				 cell.setCellValue(obj.getLast_upd_ts()==null ? "" : obj.getLast_upd_ts());
				 cell.setCellStyle(leftAlign);
				 colCount++; 
				 
				 cell = row.createCell(colCount);
				 cell.setCellValue(obj.getUpdated_by()==null ? "" : obj.getUpdated_by());
				 cell.setCellStyle(leftAlign);
				 colCount++; 
				 
				 cell = row.createCell(colCount);
				 cell.setCellValue(obj.getActive()==null ? "" : obj.getActive());
				 cell.setCellStyle(leftAlign);
				 colCount++; 
				 
				 cell = row.createCell(colCount);
				 cell.setCellValue(obj.getDel_ts()==null ? "" : obj.getDel_ts());
				 cell.setCellStyle(leftAlign);
				 colCount++; 
				 
				 cell = row.createCell(colCount);
				 cell.setCellValue(obj.getEmpl_source()==null ? "" : obj.getEmpl_source());
				 cell.setCellStyle(leftAlign);
				 colCount++; 
				 
				 cell = row.createCell(colCount);
				 cell.setCellValue(obj.getPrx_zip_file_name()==null ? "" : obj.getPrx_zip_file_name());
				 cell.setCellStyle(leftAlign);
				 colCount++; 
				 
				 cell = row.createCell(colCount);
				 cell.setCellValue(obj.getPrx_file_name()==null ? "" : obj.getPrx_file_name());
				 cell.setCellStyle(leftAlign);
				 colCount++; 
				 
				 cell = row.createCell(colCount);
				 cell.setCellValue(obj.getDate_time()==null ? "" : sdf.format(obj.getDate_time()));
				 cell.setCellStyle(leftAlign);
				 colCount++; 
				 
				 cell = row.createCell(colCount);
				 cell.setCellValue(obj.getDate_time_recd()==null ? "" : sdf.format(obj.getDate_time_recd()));
				 cell.setCellStyle(leftAlign);
				 colCount++; 
				 
				
				 colCount=0;
				 rowCount++;
			 }
			 
				
				System.out.println("Excel Generated");
				FileOutputStream fileOutputStream = new FileOutputStream(filepath);
				wb.write(fileOutputStream);
				System.out.println("Excel Generated");
				filename=usermasterservice.generateExcellock(filepath, filename,empId,".xlsx");

		}
		catch (Exception e) {
			// TODO: handle exception
			throw e;
		}
		finally {
			if(wb != null) wb.close();
		}
		return filename;
	}

	@Override
	public String GenerateDgVeevaPosReport(List<Dg_veeva_pos> lst, ReportBean bean, String company,String empId) throws Exception {
		// TODO Auto-generated method stub
		int rowCount = 0,colCount=0;
		XSSFWorkbook wb=null;
		String filename = "DgVeevaPos" + new Date().getTime()+".xlsx" ;
		try {

			SimpleDateFormat sdf=new SimpleDateFormat("dd-MM-yyyy");
			String filepath = REPORT_FILE_PATH + filename;
			
				wb=new XSSFWorkbook();
				File f = new File(REPORT_FILE_PATH);
				if (!f.exists()) {
					if (f.mkdirs()) {
					} else {
						wb.close();
						throw new RuntimeException("Could not create directory");
					}
				}
				
			XSSFSheet sheet = wb.createSheet("Veeva Pos");
			ApachePoiExcelFormat xlsxExcelFormat = new ApachePoiExcelFormat();
			XSSFRow row=null;
			XSSFCell cell=null;
			
			 CellStyle sheetHeading = xlsxExcelFormat.SheetHeading(wb);
			 CellStyle sheetHeadingLeft = xlsxExcelFormat.SheetHeading(wb);
			 sheetHeadingLeft.setAlignment(HorizontalAlignment.LEFT);
			 CellStyle columnHeading = xlsxExcelFormat.columnHeading(wb);
			 CellStyle columnHeadingRight = xlsxExcelFormat.columnHeading(wb);
			 columnHeadingRight.setAlignment(HorizontalAlignment.RIGHT);
			 CellStyle leftAlign = xlsxExcelFormat.dataLeftAlign(wb);
			 
			 String[] heading = {"Country Code","Source","Src Sys Id","Posn Type Code","Posn Code","Posn Name","Posn Desc.",
					"Posn Desc. Short", "SLFC Src Sys Id","SLFC Type Id","SLFC Code","SLFC Name","Bus Unit Code","Bus Unit Name",
					"Parent Src Sys Id","Heir Type","Node Type","EFF Date","End Date","Create Ts","Last UPD Ts","Updated By","Active",
					 "Del Ts","PRX ZIP File Name","PRX File Name","Date Time","Date Recieved"};

			 
			 
			 row = sheet.createRow(rowCount);
			 sheet.addMergedRegion(new CellRangeAddress(rowCount, rowCount, colCount, heading.length-1));
			 cell = row.createCell(colCount);
			 cell.setCellValue(company);
			 cell.setCellStyle(sheetHeading);
			 rowCount++;
			 
			 row = sheet.createRow(rowCount);
			 sheet.addMergedRegion(new CellRangeAddress(rowCount, rowCount, colCount,heading.length-1));
			 cell = row.createCell(colCount);
			 cell.setCellValue("DG VEEVA POS REPORT");
			 cell.setCellStyle(sheetHeading);
			 rowCount++;
			 
			 row = sheet.createRow(rowCount);	
			 sheet.addMergedRegion(new CellRangeAddress(rowCount, rowCount, colCount, heading.length-1));
			 cell = row.createCell(colCount);
			 cell.setCellValue("From "+sdf.format(bean.getStartDate())+" To "+sdf.format(bean.getEndDate()));
			 cell.setCellStyle(sheetHeading);
			 rowCount++;
			 

			 colCount=0;
			 row = sheet.createRow(rowCount);
			 
			 for(String s : heading) {
				 cell = row.createCell(colCount);
				 cell.setCellValue(s);
				 cell.setCellStyle(columnHeading);
				 colCount++;
			 }
			 rowCount ++;
			 colCount=0;
			 
			 
			 for(Dg_veeva_pos obj : lst) {
				 row = sheet.createRow(rowCount);
				 
				 cell = row.createCell(colCount);
				 cell.setCellValue(obj.getCountry_cd()==null ? "" : obj.getCountry_cd());
				 cell.setCellStyle(leftAlign);
				 colCount++;
				 
				 cell = row.createCell(colCount);
				 cell.setCellValue(obj.getSource()==null ? "" : obj.getSource());
				 cell.setCellStyle(leftAlign);
				 colCount++;
				 
				 cell = row.createCell(colCount);
				 cell.setCellValue(obj.getSrc_sys_id()==null ? "" : obj.getSrc_sys_id());
				 cell.setCellStyle(leftAlign);
				 colCount++;
				 
				 cell = row.createCell(colCount);
				 cell.setCellValue(obj.getPosn_type_cd()==null ? "" : obj.getPosn_type_cd());
				 cell.setCellStyle(leftAlign);
				 colCount++;
				 
				 cell = row.createCell(colCount);
				 cell.setCellValue(obj.getPosn_cd()==null ? "" : obj.getPosn_cd());
				 cell.setCellStyle(leftAlign);
				 colCount++;

				 cell = row.createCell(colCount);
				 cell.setCellValue(obj.getPosn_nm()==null ? "" : obj.getPosn_nm());
				 cell.setCellStyle(leftAlign);
				 colCount++;
				 
				 cell = row.createCell(colCount);
				 cell.setCellValue(obj.getPosn_desc()==null ? "" : obj.getPosn_desc());
				 cell.setCellStyle(leftAlign);
				 colCount++;
				 
				 cell = row.createCell(colCount);
				 cell.setCellValue(obj.getPosn_desc_short()==null ? "" : obj.getPosn_desc_short());
				 cell.setCellStyle(leftAlign);
				 colCount++;
				 
				 cell = row.createCell(colCount);
				 cell.setCellValue(obj.getSlfc_src_sys_id()==null ? "" : obj.getSlfc_src_sys_id());
				 cell.setCellStyle(leftAlign);
				 colCount++;
				 
				 
				 cell = row.createCell(colCount);
				 cell.setCellValue(obj.getSlfc_type_cd()==null ? "" : obj.getSlfc_type_cd());
				 cell.setCellStyle(leftAlign);
				 colCount++;
				 
				 
				 
				 cell = row.createCell(colCount);
				 cell.setCellValue(obj.getSlfc_cd()==null ? "" : obj.getSlfc_cd());
				 cell.setCellStyle(leftAlign);
				 colCount++;
				 
				 
				 cell = row.createCell(colCount);
				 cell.setCellValue(obj.getSlfc_nm()==null ? "" : obj.getSlfc_nm());
				 cell.setCellStyle(leftAlign);
				 colCount++;
				 
				 
				 cell = row.createCell(colCount);
				 cell.setCellValue(obj.getBus_unit_cd()==null ? "" : obj.getBus_unit_cd());
				 cell.setCellStyle(leftAlign);
				 colCount++;
				 
				 
				 cell = row.createCell(colCount);
				 cell.setCellValue(obj.getBus_unit_nm()==null ? "" : obj.getBus_unit_nm());
				 cell.setCellStyle(leftAlign);
				 colCount++;
				 
				 cell = row.createCell(colCount);
				 cell.setCellValue(obj.getParent_src_sys_id()==null ? "" : obj.getParent_src_sys_id());
				 cell.setCellStyle(leftAlign);
				 colCount++;
				 
				 cell = row.createCell(colCount);
				 cell.setCellValue(obj.getHier_type()==null ? "" : obj.getHier_type());
				 cell.setCellStyle(leftAlign);
				 colCount++; 
				 
				 cell = row.createCell(colCount);
				 cell.setCellValue(obj.getNode_type()==null ? "" : obj.getNode_type());
				 cell.setCellStyle(leftAlign);
				 colCount++; 
				 
				 cell = row.createCell(colCount);
				 cell.setCellValue(obj.getEff_date()==null ? "" :obj.getEff_date().equals("")? "": sdf.format(MedicoResources.convert_YYYY_MM_DD_HH_MM_SS_toDate(obj.getEff_date())));
				 cell.setCellStyle(leftAlign);
				 colCount++; 
				 
				 cell = row.createCell(colCount);
				 cell.setCellValue(obj.getEnd_date()==null ? "" : obj.getEnd_date());
				 cell.setCellStyle(leftAlign);
				 colCount++; 
				 
				 cell = row.createCell(colCount);
				 cell.setCellValue(obj.getCreate_ts()==null ? "" : obj.getCreate_ts());
				 cell.setCellStyle(leftAlign);
				 colCount++; 
				 
				 cell = row.createCell(colCount);
				 cell.setCellValue(obj.getLast_upd_ts()==null ? "" : obj.getLast_upd_ts());
				 cell.setCellStyle(leftAlign);
				 colCount++; 
				 
				 cell = row.createCell(colCount);
				 cell.setCellValue(obj.getUpdated_by()==null ? "" : obj.getUpdated_by());
				 cell.setCellStyle(leftAlign);
				 colCount++; 
				 
				 cell = row.createCell(colCount);
				 cell.setCellValue(obj.getActive()==null ? "" : obj.getActive());
				 cell.setCellStyle(leftAlign);
				 colCount++; 
				 
				 cell = row.createCell(colCount);
				 cell.setCellValue(obj.getDel_ts()==null ? "" : obj.getDel_ts());
				 cell.setCellStyle(leftAlign);
				 colCount++; 
				 
				 cell = row.createCell(colCount);
				 cell.setCellValue(obj.getPrx_zip_file_name()==null ? "" : obj.getPrx_zip_file_name());
				 cell.setCellStyle(leftAlign);
				 colCount++; 
				 
				 cell = row.createCell(colCount);
				 cell.setCellValue(obj.getPrx_file_name()==null ? "" : obj.getPrx_file_name());
				 cell.setCellStyle(leftAlign);
				 colCount++; 
				 
				 cell = row.createCell(colCount);
				 cell.setCellValue(obj.getDate_time()==null ? "" : sdf.format(obj.getDate_time()));
				 cell.setCellStyle(leftAlign);
				 colCount++; 
				 
				 cell = row.createCell(colCount);
				 cell.setCellValue(obj.getDate_time_recd()==null ? "" : sdf.format(obj.getDate_time_recd()));
				 cell.setCellStyle(leftAlign);
				 colCount++; 
				 
				
				 colCount=0;
				 rowCount++;
			 }
			 
				FileOutputStream fileOutputStream = new FileOutputStream(filepath);
				wb.write(fileOutputStream);
				System.out.println("Excel Generated");
				filename=usermasterservice.generateExcellock(filepath, filename,empId,".xlsx");

		}
		catch (Exception e) {
			// TODO: handle exception
			throw e;
		}
		finally {
			if(wb != null) wb.close();
		}
		return filename;
	}

	@Override
	public String GeneratePrxFieldstaffReport(List<FstaffMas_PrxBean> newlst, List<FstaffMas_PrxBean> updatelst,List<FstaffMas_PrxBean> terrlst,
			ReportBean bean, String company,String empId) throws Exception {
		// TODO Auto-generated method stub
		int rowCount = 0,colCount=0;
		XSSFWorkbook wb=null;
		String filename = "Pforce_SG_Update_" + new Date().getTime() + ".xlsx";
		try {
			SimpleDateFormat sdf=new SimpleDateFormat("dd-MM-yyyy");
			String filepath = REPORT_FILE_PATH + filename;
			
				wb=new XSSFWorkbook();
				File f = new File(REPORT_FILE_PATH);
				if (!f.exists()) {
					if (f.mkdirs()) {
					} else {
						wb.close();
						throw new RuntimeException("Could not create directory");
					}
				}
				
			XSSFSheet sheet =null;
			sheet = wb.createSheet("Updated Fieldstaff");
			ApachePoiExcelFormat xlsxExcelFormat = new ApachePoiExcelFormat();
			XSSFRow row=null;
			XSSFCell cell=null;
			
			 CellStyle sheetHeading = xlsxExcelFormat.SheetHeading(wb);
			 CellStyle sheetHeadingLeft = xlsxExcelFormat.SheetHeading(wb);
			 sheetHeadingLeft.setAlignment(HorizontalAlignment.LEFT);
			 CellStyle columnHeading = xlsxExcelFormat.columnHeading(wb);
			 CellStyle columnHeadingRight = xlsxExcelFormat.columnHeading(wb);
			 columnHeadingRight.setAlignment(HorizontalAlignment.RIGHT);
			 CellStyle leftAlign = xlsxExcelFormat.dataLeftAlign(wb);
			 
			 String[] heading = {"Type of Addition or Modification to Medico SG masters from PForceRx","Division","Territory","FieldStaff Code","Territory Name","Emp No","Map code","Employee Name",
						"ABM ID", "ABM","ABM Name","RBM ID","RBM","RBM Name","Employee Address1",
						"Employee Address2","Employee Address3","Employee Address4","City","Pin Code","Mobile","Joining  Date","Leaving Date","Sample Dispatch",
						 "Status","Designation","Zone","Category","HQ","Insertion Date","Modified Date","Date Received",
						 "Main Master Updated","Date Time Updated","Changed","Team Code","PRX ZIP File Name","PRX File Name","Integ Type Desc"};
				 
			 String[] Newheading = {"New Joinee Or Promotion","Fstaff ID","Division","Territory","Employee code","Fstaff Name","Employee No","Map","Employee Name",
				 						"ABM ID", "ABM Territory","ABM Name","RBM ID","RBM territory","RBM Name","Employee Address1",
				 						"Employee Address2","Employee Address3","Employee Address4","FieldStaff Destination","Pin Code","Mobile","Join Date","Leaving Date","Sample Dispatch",
				 						"Status","Designation","Zone","Category","Hq Name","Date Inserted","Date Modified",
				 						"FSPR Terr ID","Terr Code","Terr Id","Date Time Received","Master Updated","Date Time Updated","New or Changed",
				 						"Team Code","PRX ZIP File Name","PRX File Name","Type of Change"};
				 
			 String[] Terrheading = {"New Territory","Division","Terr Code","Territory Name","Employee Name","Employee No.",
						 "Main Mast Updated","Date Time Updated","Fs New Changed","Team Code","PRX ZIP File Name","PRX File Name","Type of change"};

				 
			 
			 row = sheet.createRow(rowCount);
			 sheet.addMergedRegion(new CellRangeAddress(rowCount, rowCount, colCount, heading.length-1));
			 cell = row.createCell(colCount);
			 cell.setCellValue(company);
			 cell.setCellStyle(sheetHeading);
			 rowCount++;
			 
			 row = sheet.createRow(rowCount);
			 sheet.addMergedRegion(new CellRangeAddress(rowCount, rowCount, colCount,heading.length-1));
			 cell = row.createCell(colCount);
			 cell.setCellValue("Updated FieldStaff Report");
			 cell.setCellStyle(sheetHeading);
			 rowCount++;
			 
			 row = sheet.createRow(rowCount);	
			 sheet.addMergedRegion(new CellRangeAddress(rowCount, rowCount, colCount, heading.length-1));
			 cell = row.createCell(colCount);
			 cell.setCellValue("From "+sdf.format(bean.getStartDate())+" To "+sdf.format(bean.getEndDate()));
			 cell.setCellStyle(sheetHeading);
			 rowCount++;
			 

			 colCount=0;
			 row = sheet.createRow(rowCount);
			 
			 for(String s : heading) {
				 cell = row.createCell(colCount);
				 cell.setCellValue(s);
				 cell.setCellStyle(columnHeading);
				 colCount++;
			 }
			 rowCount ++;
			 colCount=0;
			 
			 
			 //Updated Fieldstaff Sheet
			 if(updatelst !=null && updatelst.size()>0) {
			 for(FstaffMas_PrxBean obj :updatelst) {
				 row = sheet.createRow(rowCount);
				 
				 cell = row.createCell(colCount);
				 cell.setCellValue(obj.getType_of_change_descr()==null ? "" : obj.getType_of_change_descr());
				 cell.setCellStyle(leftAlign);
				 colCount++;
				 
				 cell = row.createCell(colCount);
				 cell.setCellValue(obj.getDiv_disp_nm()==null ? "" : obj.getDiv_disp_nm());
				 cell.setCellStyle(leftAlign);
				 colCount++;
				 
				 cell = row.createCell(colCount);
				 cell.setCellValue(obj.getFstaff_terr_code()==null ? "" : obj.getFstaff_terr_code());
				 cell.setCellStyle(leftAlign);
				 colCount++;
				 
				 cell = row.createCell(colCount);
				 cell.setCellValue(obj.getFstaff_code()==null ? "" : obj.getFstaff_code());
				 cell.setCellStyle(leftAlign);
				 colCount++;
				 
				 cell = row.createCell(colCount);
				 cell.setCellValue(obj.getFstaff_name()==null ? "" : obj.getFstaff_name());
				 cell.setCellStyle(leftAlign);
				 colCount++;
				 
				 cell = row.createCell(colCount);
				 cell.setCellValue(obj.getFstaff_employee_no()==null ? "" : obj.getFstaff_employee_no());
				 cell.setCellStyle(leftAlign);
				 colCount++;
				 
				 cell = row.createCell(colCount);
				 cell.setCellValue(obj.getFstaff_map_code1()==null ? "" : obj.getFstaff_map_code1());
				 cell.setCellStyle(leftAlign);
				 colCount++;
				 
				 cell = row.createCell(colCount);
				 cell.setCellValue(obj.getFstaff_display_name()==null ? "" : obj.getFstaff_display_name());
				 cell.setCellStyle(leftAlign);
				 colCount++;
				 
				 cell = row.createCell(colCount);
				 cell.setCellValue(obj.getFstaff_mgr1_id()==null ? "" : obj.getFstaff_mgr1_id().toString());
				 cell.setCellStyle(leftAlign);
				 colCount++;
				 
				 cell = row.createCell(colCount);
				 cell.setCellValue(obj.getAbm()==null ? "" : obj.getAbm());
				 cell.setCellStyle(leftAlign);
				 colCount++;
				 
				 cell = row.createCell(colCount);
				 cell.setCellValue(obj.getAbm_name()==null ? "" : obj.getAbm_name());
				 cell.setCellStyle(leftAlign);
				 colCount++;
				 
				 cell = row.createCell(colCount);
				 cell.setCellValue(obj.getFstaff_mgr2_id()==null ? "" : obj.getFstaff_mgr2_id().toString());
				 cell.setCellStyle(leftAlign);
				 colCount++;
				 
				 cell = row.createCell(colCount);
				 cell.setCellValue(obj.getRbm()==null ? "" : obj.getRbm());
				 cell.setCellStyle(leftAlign);
				 colCount++;
				 
				 cell = row.createCell(colCount);
				 cell.setCellValue(obj.getRbm_name()==null ? "" : obj.getRbm_name());
				 cell.setCellStyle(leftAlign);
				 colCount++;
				 
				 cell = row.createCell(colCount);
				 cell.setCellValue(obj.getFstaff_addr1()==null ? "" : obj.getFstaff_addr1());
				 cell.setCellStyle(leftAlign);
				 colCount++;
				 
				 cell = row.createCell(colCount);
				 cell.setCellValue(obj.getFstaff_addr2()==null ? "" : obj.getFstaff_addr2());
				 cell.setCellStyle(leftAlign);
				 colCount++;
				 
				 cell = row.createCell(colCount);
				 cell.setCellValue(obj.getFstaff_addr3()==null ? "" : obj.getFstaff_addr3());
				 cell.setCellStyle(leftAlign);
				 colCount++;
				 
				 cell = row.createCell(colCount);
				 cell.setCellValue(obj.getFstaff_addr4()==null ? "" : obj.getFstaff_addr4());
				 cell.setCellStyle(leftAlign);
				 colCount++;
				 
				 cell = row.createCell(colCount);
				 cell.setCellValue(obj.getFstaff_destination()==null ? "" : obj.getFstaff_destination());
				 cell.setCellStyle(leftAlign);
				 colCount++;
				 
				 cell = row.createCell(colCount);
				 cell.setCellValue(obj.getFstaff_pincode()==null ? "" : obj.getFstaff_pincode());
				 cell.setCellStyle(leftAlign);
				 colCount++;
				 
				 cell = row.createCell(colCount);
				 cell.setCellValue(obj.getFstaff_mobile()==null ? "" : obj.getFstaff_mobile());
				 cell.setCellStyle(leftAlign);
				 colCount++;
				 
				 cell = row.createCell(colCount);
				 cell.setCellValue(obj.getFstaff_joining_date()==null ? "" : sdf.format(obj.getFstaff_joining_date()));
				 cell.setCellStyle(leftAlign);
				 colCount++;
				 
				 cell = row.createCell(colCount);
				 cell.setCellValue(obj.getFstaff_leaving_date()==null ? "" : sdf.format(obj.getFstaff_leaving_date()));
				 cell.setCellStyle(leftAlign);
				 colCount++;
				 
				 cell = row.createCell(colCount);
				 cell.setCellValue(obj.getFstaff_samp_disp_ind()==null ? "" : obj.getFstaff_samp_disp_ind());
				 cell.setCellStyle(leftAlign);
				 colCount++;
				 
				 cell = row.createCell(colCount);
				 cell.setCellValue(obj.getFstaff_status()==null ? "" : obj.getFstaff_status());
				 cell.setCellStyle(leftAlign);
				 colCount++;
				 
				 cell = row.createCell(colCount);
				 cell.setCellValue(obj.getFstaff_Desig()==null ? "" : obj.getFstaff_Desig());
				 cell.setCellStyle(leftAlign);
				 colCount++;
				 
				 cell = row.createCell(colCount);
				 cell.setCellValue(obj.getFstaff_ZoneName()==null ? "" : obj.getFstaff_ZoneName());
				 cell.setCellStyle(leftAlign);
				 colCount++;
				 
				 cell = row.createCell(colCount);
				 cell.setCellValue(obj.getFs_Category()==null ? "" : obj.getFs_Category());
				 cell.setCellStyle(leftAlign);
				 colCount++;
				 
				 cell = row.createCell(colCount);
				 cell.setCellValue(obj.getHq_name()==null ? "" : obj.getHq_name());
				 cell.setCellStyle(leftAlign);
				 colCount++;
				 
				 cell = row.createCell(colCount);
				 cell.setCellValue(obj.getFstaff_ins_dt()==null ? "" : sdf.format(obj.getFstaff_ins_dt()));
				 cell.setCellStyle(leftAlign);
				 colCount++;
				 
				 cell = row.createCell(colCount);
				 cell.setCellValue(obj.getFstaff_mod_dt()==null ? "" : sdf.format(obj.getFstaff_mod_dt()));
				 cell.setCellStyle(leftAlign);
				 colCount++;
//				 
//				 cell = row.createCell(colCount);
//				 cell.setCellValue(obj.getInt_Type_Desc()==null ? "" : obj.getInt_Type_Desc());
//				 cell.setCellStyle(leftAlign);
//				 colCount++;
				 
				 cell = row.createCell(colCount);
				 cell.setCellValue(obj.getDate_Time_Recd()==null ? "" : obj.getDate_Time_Recd().toString());
				 cell.setCellStyle(leftAlign);
				 colCount++;
				 
				 cell = row.createCell(colCount);
				 cell.setCellValue(obj.getMain_mast_updated()==null ? "" : obj.getMain_mast_updated());
				 cell.setCellStyle(leftAlign);
				 colCount++;
				 
				 cell = row.createCell(colCount);
				 cell.setCellValue(obj.getDate_time_Updated()==null ? "" : sdf.format(obj.getDate_time_Updated()));
				 cell.setCellStyle(leftAlign);
				 colCount++;
				 
				 cell = row.createCell(colCount);
				 cell.setCellValue(obj.getFs_new_changed()==null ? "" : obj.getFs_new_changed());
				 cell.setCellStyle(leftAlign);
				 colCount++;
				 
				 cell = row.createCell(colCount);
				 cell.setCellValue(obj.getTeam_code()==null ? "0" : obj.getTeam_code());
				 cell.setCellStyle(leftAlign);
				 colCount++;
				 
				 cell= row.createCell(colCount);
				 cell.setCellValue(obj.getPrx_zip_file_name()==null?"":obj.getPrx_zip_file_name());
				 cell.setCellStyle(leftAlign);
				 colCount++;
				 
				 cell= row.createCell(colCount);
				 cell.setCellValue(obj.getPrx_file_name()==null?"":obj.getPrx_file_name());
				 cell.setCellStyle(leftAlign);
				 colCount++;
				 
				 cell = row.createCell(colCount);
				 cell.setCellValue(obj.getInt_Type_Desc()==null ? "" : obj.getInt_Type_Desc());
				 cell.setCellStyle(leftAlign);
				 colCount++;
				 
				 rowCount ++ ;
				 colCount=0;
			 }
			 }
			 sheet = wb.createSheet("New - Promotion Fieldstaff");
			 rowCount = 0;
			 colCount = 0;
			 
			 row = sheet.createRow(rowCount);
			 sheet.addMergedRegion(new CellRangeAddress(rowCount, rowCount, colCount, Newheading.length-1));
			 cell = row.createCell(colCount);
			 cell.setCellValue(company);
			 cell.setCellStyle(sheetHeading);
			 rowCount++;
			 
			 row = sheet.createRow(rowCount);
			 sheet.addMergedRegion(new CellRangeAddress(rowCount, rowCount, colCount,Newheading.length-1));
			 cell = row.createCell(colCount);
			 cell.setCellValue("New FieldStaff/Promotion Report");
			 cell.setCellStyle(sheetHeading);
			 rowCount++;
			 
			 row = sheet.createRow(rowCount);	
			 sheet.addMergedRegion(new CellRangeAddress(rowCount, rowCount, colCount, Newheading.length-1));
			 cell = row.createCell(colCount);
			 cell.setCellValue("From "+sdf.format(bean.getStartDate())+" To "+sdf.format(bean.getEndDate()));
			 cell.setCellStyle(sheetHeading);
			 rowCount++;
			 

			 colCount=0;
			 row = sheet.createRow(rowCount);
			 
			 for(String s : Newheading) {
				 cell = row.createCell(colCount);
				 cell.setCellValue(s);
				 cell.setCellStyle(columnHeading);
				 colCount++;
			 }
			 rowCount ++;
			 colCount=0;
	
			 //New Fieldstaff Sheet
			 if(newlst!=null && newlst.size()>0) {
			 for(FstaffMas_PrxBean obj :newlst) {
				 row = sheet.createRow(rowCount);
				 
				 cell = row.createCell(colCount);
				 cell.setCellValue(obj.getType_of_change_descr()==null ? "" : obj.getType_of_change_descr());
				 cell.setCellStyle(leftAlign);
				 colCount++;
				 
				 cell = row.createCell(colCount);
				 cell.setCellValue(obj.getFstaff_id()==null ? "" : obj.getFstaff_id().toString());
				 cell.setCellStyle(leftAlign);
				 colCount++;
				 
				 cell = row.createCell(colCount);
				 cell.setCellValue(obj.getDiv_disp_nm()==null ? "" : obj.getDiv_disp_nm());
				 cell.setCellStyle(leftAlign);
				 colCount++;
				 
				 cell = row.createCell(colCount);
				 cell.setCellValue(obj.getFstaff_terr_code()==null ? "" : obj.getFstaff_terr_code());
				 cell.setCellStyle(leftAlign);
				 colCount++;
				 
				 cell = row.createCell(colCount);
				 cell.setCellValue(obj.getFstaff_code()==null ? "" : obj.getFstaff_code());
				 cell.setCellStyle(leftAlign);
				 colCount++;
				 
				 cell = row.createCell(colCount);
				 cell.setCellValue(obj.getFstaff_name()==null ? "" : obj.getFstaff_name());
				 cell.setCellStyle(leftAlign);
				 colCount++;
				 
				 cell = row.createCell(colCount);
				 cell.setCellValue(obj.getFstaff_employee_no()==null ? "" : obj.getFstaff_employee_no());
				 cell.setCellStyle(leftAlign);
				 colCount++;
				 
				 cell = row.createCell(colCount);
				 cell.setCellValue(obj.getFstaff_map_code1()==null ? "" : obj.getFstaff_map_code1());
				 cell.setCellStyle(leftAlign);
				 colCount++;
				 
				 cell = row.createCell(colCount);
				 cell.setCellValue(obj.getFstaff_display_name()==null ? "" : obj.getFstaff_display_name());
				 cell.setCellStyle(leftAlign);
				 colCount++;
				 
				 cell = row.createCell(colCount);
				 cell.setCellValue(obj.getFstaff_mgr1_id()==null ? "" : obj.getFstaff_mgr1_id().toString());
				 cell.setCellStyle(leftAlign);
				 colCount++;
				 
				 cell = row.createCell(colCount);
				 cell.setCellValue(obj.getAbm()==null ? "" : obj.getAbm());
				 cell.setCellStyle(leftAlign);
				 colCount++;
				 
				 cell = row.createCell(colCount);
				 cell.setCellValue(obj.getAbm_name()==null ? "" : obj.getAbm_name());
				 cell.setCellStyle(leftAlign);
				 colCount++;
				 
				 cell = row.createCell(colCount);
				 cell.setCellValue(obj.getFstaff_mgr2_id()==null ? "" : obj.getFstaff_mgr2_id().toString());
				 cell.setCellStyle(leftAlign);
				 colCount++;
				 
				 cell = row.createCell(colCount);
				 cell.setCellValue(obj.getRbm()==null ? "" : obj.getRbm());
				 cell.setCellStyle(leftAlign);
				 colCount++;
				 
				 cell = row.createCell(colCount);
				 cell.setCellValue(obj.getRbm_name()==null ? "" : obj.getRbm_name());
				 cell.setCellStyle(leftAlign);
				 colCount++;
				 
				 cell = row.createCell(colCount);
				 cell.setCellValue(obj.getFstaff_addr1()==null ? "" : obj.getFstaff_addr1());
				 cell.setCellStyle(leftAlign);
				 colCount++;
				 
				 cell = row.createCell(colCount);
				 cell.setCellValue(obj.getFstaff_addr2()==null ? "" : obj.getFstaff_addr2());
				 cell.setCellStyle(leftAlign);
				 colCount++;
				 
				 cell = row.createCell(colCount);
				 cell.setCellValue(obj.getFstaff_addr3()==null ? "" : obj.getFstaff_addr3());
				 cell.setCellStyle(leftAlign);
				 colCount++;
				 
				 cell = row.createCell(colCount);
				 cell.setCellValue(obj.getFstaff_addr4()==null ? "" : obj.getFstaff_addr4());
				 cell.setCellStyle(leftAlign);
				 colCount++;
				 
				 cell = row.createCell(colCount);
				 cell.setCellValue(obj.getFstaff_destination()==null ? "" : obj.getFstaff_destination());
				 cell.setCellStyle(leftAlign);
				 colCount++;
				 
				 cell = row.createCell(colCount);
				 cell.setCellValue(obj.getFstaff_pincode()==null ? "" : obj.getFstaff_pincode());
				 cell.setCellStyle(leftAlign);
				 colCount++;
				 
				 cell = row.createCell(colCount);
				 cell.setCellValue(obj.getFstaff_mobile()==null ? "" : obj.getFstaff_mobile());
				 cell.setCellStyle(leftAlign);
				 colCount++;
				 
				 cell = row.createCell(colCount);
				 cell.setCellValue(obj.getFstaff_joining_date()==null ? "" : sdf.format(obj.getFstaff_joining_date()));
				 cell.setCellStyle(leftAlign);
				 colCount++;
				 
				 cell = row.createCell(colCount);
				 cell.setCellValue(obj.getFstaff_leaving_date()==null ? "" : sdf.format(obj.getFstaff_leaving_date()));
				 cell.setCellStyle(leftAlign);
				 colCount++;
				 
				 cell = row.createCell(colCount);
				 cell.setCellValue(obj.getFstaff_samp_disp_ind()==null ? "" : obj.getFstaff_samp_disp_ind());
				 cell.setCellStyle(leftAlign);
				 colCount++;
				 
				 cell = row.createCell(colCount);
				 cell.setCellValue(obj.getFstaff_status()==null ? "" : obj.getFstaff_status());
				 cell.setCellStyle(leftAlign);
				 colCount++;
				 
				 cell = row.createCell(colCount);
				 cell.setCellValue(obj.getFstaff_Desig()==null ? "" : obj.getFstaff_Desig());
				 cell.setCellStyle(leftAlign);
				 colCount++;
				 
				 cell = row.createCell(colCount);
				 cell.setCellValue(obj.getFstaff_ZoneName()==null ? "" : obj.getFstaff_ZoneName());
				 cell.setCellStyle(leftAlign);
				 colCount++;
				 
				 cell = row.createCell(colCount);
				 cell.setCellValue(obj.getFs_Category()==null ? "" : obj.getFs_Category());
				 cell.setCellStyle(leftAlign);
				 colCount++;
				 
				 cell = row.createCell(colCount);
				 cell.setCellValue(obj.getHq_name()==null ? "" : obj.getHq_name());
				 cell.setCellStyle(leftAlign);
				 colCount++;
				 
				 cell = row.createCell(colCount);
				 cell.setCellValue(obj.getFstaff_ins_dt()==null ? "" : sdf.format(obj.getFstaff_ins_dt()));
				 cell.setCellStyle(leftAlign);
				 colCount++;
				 
				 cell = row.createCell(colCount);
				 cell.setCellValue(obj.getFstaff_mod_dt()==null ? "" : sdf.format(obj.getFstaff_mod_dt()));
				 cell.setCellStyle(leftAlign);
				 colCount++;
				 
				 cell = row.createCell(colCount);
				 cell.setCellValue(obj.getFsprterrId()==null ? "" : obj.getFsprterrId().toString());
				 cell.setCellStyle(leftAlign);
				 colCount++;
				 
				 cell = row.createCell(colCount);
				 cell.setCellValue(obj.getTerr_code()==null ? "" : obj.getTerr_code());
				 cell.setCellStyle(leftAlign);
				 colCount++;
				 
				 cell = row.createCell(colCount);
				 cell.setCellValue(obj.getTerrId()==null ? "" : obj.getTerrId().toString());
				 cell.setCellStyle(leftAlign);
				 colCount++;
				 
				 cell = row.createCell(colCount);
				 cell.setCellValue(obj.getDate_Time_Recd()==null ? "" : obj.getDate_Time_Recd().toString());
				 cell.setCellStyle(leftAlign);
				 colCount++;
				 
				 cell = row.createCell(colCount);
				 cell.setCellValue(obj.getMain_mast_updated()==null ? "" : obj.getMain_mast_updated());
				 cell.setCellStyle(leftAlign);
				 colCount++;
				 
				 cell = row.createCell(colCount);
				 cell.setCellValue(obj.getDate_time_Updated()==null ? "" : sdf.format(obj.getDate_time_Updated()));
				 cell.setCellStyle(leftAlign);
				 colCount++;
				 
				 cell = row.createCell(colCount);
				 cell.setCellValue(obj.getFs_new_changed()==null ? "" : obj.getFs_new_changed());
				 cell.setCellStyle(leftAlign);
				 colCount++;
				 
//				 cell = row.createCell(colCount);
//				 cell.setCellValue(obj.getInt_Type_Desc()==null ? "" : obj.getInt_Type_Desc());
//				 cell.setCellStyle(leftAlign);
//				 colCount++;
				 
				 cell = row.createCell(colCount);
				 cell.setCellValue(obj.getTeam_code()==null ? "0" : obj.getTeam_code());
				 cell.setCellStyle(leftAlign);
				 colCount++;
				 
				 cell= row.createCell(colCount);
				 cell.setCellValue(obj.getPrx_zip_file_name()==null?"":obj.getPrx_zip_file_name());
				 cell.setCellStyle(leftAlign);
				 colCount++;
				 
				 cell= row.createCell(colCount);
				 cell.setCellValue(obj.getPrx_file_name()==null?"":obj.getPrx_file_name());
				 cell.setCellStyle(leftAlign);
				 colCount++; 
				 
				 cell = row.createCell(colCount);
				 cell.setCellValue(obj.getInt_Type_Desc()==null ? "" : obj.getInt_Type_Desc());
				 cell.setCellStyle(leftAlign);
				 colCount++;
				 
				 rowCount ++;
				 colCount=0;
			 }
			 }
			 
			 sheet = wb.createSheet("New Territory");
			 rowCount = 0;
			 colCount = 0;
			 
			 row = sheet.createRow(rowCount);
			 sheet.addMergedRegion(new CellRangeAddress(rowCount, rowCount, colCount, Terrheading.length-1));
			 cell = row.createCell(colCount);
			 cell.setCellValue(company);
			 cell.setCellStyle(sheetHeading);
			 rowCount++;
			 
			 row = sheet.createRow(rowCount);
			 sheet.addMergedRegion(new CellRangeAddress(rowCount, rowCount, colCount,Terrheading.length-1));
			 cell = row.createCell(colCount);
			 cell.setCellValue("New Territory Report");
			 cell.setCellStyle(sheetHeading);
			 rowCount++;
			 
			 row = sheet.createRow(rowCount);	
			 sheet.addMergedRegion(new CellRangeAddress(rowCount, rowCount, colCount, Terrheading.length-1));
			 cell = row.createCell(colCount);
			 cell.setCellValue("From "+sdf.format(bean.getStartDate())+" To "+sdf.format(bean.getEndDate()));
			 cell.setCellStyle(sheetHeading);
			 rowCount++;
			 

			 colCount=0;
			 row = sheet.createRow(rowCount);
			 
			 for(String s : Terrheading) {
				 cell = row.createCell(colCount);
				 cell.setCellValue(s);
				 cell.setCellStyle(columnHeading);
				 colCount++;
			 }
			 rowCount ++;
			 colCount=0;

			 //New Fieldstaff Sheet
			 if(terrlst!=null && terrlst.size()>0) {
			 for(FstaffMas_PrxBean obj :terrlst) {
				 row = sheet.createRow(rowCount);
				 
				 cell = row.createCell(colCount);
				 cell.setCellValue(obj.getType_of_change_descr()==null ? "" : obj.getType_of_change_descr());
				 cell.setCellStyle(leftAlign);
				 colCount++;
				 
				 cell = row.createCell(colCount);
				 cell.setCellValue(obj.getPrxDivName()==null ? "" : obj.getPrxDivName());
				 cell.setCellStyle(leftAlign);
				 colCount++;
				 
				 cell = row.createCell(colCount);
				 cell.setCellValue(obj.getTerr_code()==null ? "" : obj.getTerr_code());
				 cell.setCellStyle(leftAlign);
				 colCount++;
				 
				 cell = row.createCell(colCount);
				 cell.setCellValue(obj.getPrx_terr_name()==null ? "" : obj.getPrx_terr_name());
				 cell.setCellStyle(leftAlign);
				 colCount++;
				 
				 cell = row.createCell(colCount);
				 cell.setCellValue(obj.getFstaff_display_name()==null ? "" : obj.getFstaff_display_name());
				 cell.setCellStyle(leftAlign);
				 colCount++;
				 
				 cell = row.createCell(colCount);
				 cell.setCellValue(obj.getFstaff_employee_no()==null ? "" : obj.getFstaff_employee_no());
				 cell.setCellStyle(leftAlign);
				 colCount++;
				 
				 cell = row.createCell(colCount);
				 cell.setCellValue(obj.getMain_mast_updated()==null ? "" : obj.getMain_mast_updated());
				 cell.setCellStyle(leftAlign);
				 colCount++;
				 
				 cell = row.createCell(colCount);
				 cell.setCellValue(obj.getDate_time_Updated()==null ? "" : sdf.format(obj.getDate_time_Updated()));
				 cell.setCellStyle(leftAlign);
				 colCount++;
				 
				 cell = row.createCell(colCount);
				 cell.setCellValue(obj.getFs_new_changed()==null ? "" : obj.getFs_new_changed());
				 cell.setCellStyle(leftAlign);
				 colCount++;
				 
				 cell = row.createCell(colCount);
				 cell.setCellValue(obj.getTeam_code()==null ? "0" : obj.getTeam_code());
				 cell.setCellStyle(leftAlign);
				 colCount++;
				 
				 cell= row.createCell(colCount);
				 cell.setCellValue(obj.getPrx_zip_file_name()==null?"":obj.getPrx_zip_file_name());
				 cell.setCellStyle(leftAlign);
				 colCount++;
				 
				 cell= row.createCell(colCount);
				 cell.setCellValue(obj.getPrx_file_name()==null?"":obj.getPrx_file_name());
				 cell.setCellStyle(leftAlign);
				 colCount++; 
				 
				 cell = row.createCell(colCount);
				 cell.setCellValue(obj.getInt_Type_Desc()==null ? "" : obj.getInt_Type_Desc());
				 cell.setCellStyle(leftAlign);
				 colCount++;
			 
				 rowCount ++;
				 colCount=0;
			 }
			 }
				FileOutputStream fileOutputStream = new FileOutputStream(filepath);
				wb.write(fileOutputStream);
				
				System.out.println("Excel Created");
				
				filename=usermasterservice.generateExcellock(filepath, filename,empId,".xlsx");

	                   
		}
		catch (Exception e) {
			// TODO: handle exception
			throw e;
		}finally {
			if(wb != null) wb.close();
		}
		return filename;
	}
	
	@Override
	public String GenerateDgVeevaEmpReport(List<Dg_veeva_emp> lst, ReportBean bean, String company,String empId) throws Exception {
		// TODO Auto-generated method stub
				int rowCount = 0,colCount=0;
				XSSFWorkbook wb=null;
				String filename = "PforceRxDgVeevaEmpReport" + new Date().getTime() + ".xlsx";
				try {

					SimpleDateFormat sdf=new SimpleDateFormat("dd-MM-yyyy");
					String filepath = REPORT_FILE_PATH + filename;
					
						wb=new XSSFWorkbook();
						File f = new File(REPORT_FILE_PATH);
						if (!f.exists()) {
							if (f.mkdirs()) {
							} else {
								wb.close();
								throw new RuntimeException("Could not create directory");
							}
						}
						
					XSSFSheet sheet = wb.createSheet("DgVeevaEmpReport");
					ApachePoiExcelFormat xlsxExcelFormat = new ApachePoiExcelFormat();
					XSSFRow row=null;
					XSSFCell cell=null;
					
					 CellStyle sheetHeading = xlsxExcelFormat.SheetHeading(wb);
					 CellStyle sheetHeadingLeft = xlsxExcelFormat.SheetHeading(wb);
					 sheetHeadingLeft.setAlignment(HorizontalAlignment.LEFT);
					 CellStyle columnHeading = xlsxExcelFormat.columnHeading(wb);
					 CellStyle columnHeadingRight = xlsxExcelFormat.columnHeading(wb);
					 columnHeadingRight.setAlignment(HorizontalAlignment.RIGHT);
					 CellStyle leftAlign = xlsxExcelFormat.dataLeftAlign(wb);
					 
					 String[] heading = {"COUNTRY CODE","SOURCE","SRC SYS ID","PFZ EMPLOYEE ID","LOGIN ID","OTHER ID","OTHER ID NAME","LOGIN DOMAIN NAME","CRM ROLE",
							 "CRM PROFILE","FIRST NAME","FIRST NAME 2","MDL NAME","LAST NAME","LAST NAME 2","MAIDEN NAME","MAIDEN NAME 2","STATUS","EMPLOYEE TYPE",
							 "JOB TITLE","PFZ EMPLOYEE FLAG","BANKING AGENT","PART TIME","AVG HOURS PER DAY","EFF DATE","END DATE","ADDR LBL","ADDR LB 2","ADDR LBL LONG",
							 "ADDR LBL LONG 2","ADDR EXT LBL","COUNTRY LBL","AREA LBL","LG POST CD","DISTRICT CD","POSTAL CITY","POSTAL CITY 2","INSTREET NUM","REGION CD",
							 "COUNTRY CD","CANTON CD","CITY CD","REGION","COUNTRY","CANTON","CITY","CITY 2","WORK PHONE","MOBILE PHONE ","PAGER","FAX","HOME PHONE",
							 "VOICE MAIL","EMAIL","TIME ZONE","LANG CD","CREATE TS","LAST UPDATED TS","UPDATED BY","ACTIVE","DEL TS","TERMINATION DATE","GENDER",
							 "EMPLOYMENT TYPE CODE","PRX ZIP FILE NAME","PRX FILE NAME","DATE TIME","DATE TIME RECEIVED"};

					 
					 
					 row = sheet.createRow(rowCount);
					 sheet.addMergedRegion(new CellRangeAddress(rowCount, rowCount, colCount, heading.length-1));
					 cell = row.createCell(colCount);
					 cell.setCellValue(company);
					 cell.setCellStyle(sheetHeading);
					 rowCount++;
					 
					 row = sheet.createRow(rowCount);
					 sheet.addMergedRegion(new CellRangeAddress(rowCount, rowCount, colCount,heading.length-1));
					 cell = row.createCell(colCount);
					 cell.setCellValue("PFORCE RX DG VEEVA EMP REPORT");
					 cell.setCellStyle(sheetHeading);
					 rowCount++;
					 
					 row = sheet.createRow(rowCount);	
					 sheet.addMergedRegion(new CellRangeAddress(rowCount, rowCount, colCount, heading.length-1));
					 cell = row.createCell(colCount);
					 cell.setCellValue("From "+sdf.format(bean.getStartDate())+" To "+sdf.format(bean.getEndDate()));
					 cell.setCellStyle(sheetHeading);
					 rowCount++;
					 

					 colCount=0;
					 row = sheet.createRow(rowCount);
					 
					 for(String s : heading) {
						 cell = row.createCell(colCount);
						 cell.setCellValue(s);
						 cell.setCellStyle(columnHeading);
						 colCount++;
					 }
					 rowCount ++;
					 colCount=0;
					 
					 
					 for(Dg_veeva_emp obj : lst) {
						 row = sheet.createRow(rowCount);
						 
//						 cell = row.createCell(colCount);
//						 cell.setCellValue(obj.getError_date()==null ? "" : sdf.format(obj.getError_date()));
//						 cell.setCellStyle(leftAlign);
//						 colCount++;
						 
						 cell = row.createCell(colCount);
						 cell.setCellValue(obj.getCountry_cd()==null ? "" : obj.getCountry_cd());
						 cell.setCellStyle(leftAlign);
						 colCount++;
						 
						 cell = row.createCell(colCount);
						 cell.setCellValue(obj.getSource()==null ? "" : obj.getSource());
						 cell.setCellStyle(leftAlign);
						 colCount++;
						 
						 cell = row.createCell(colCount);
						 cell.setCellValue(obj.getSrc_sys_id()==null ? "" : obj.getSrc_sys_id());
						 cell.setCellStyle(leftAlign);
						 colCount++;
						 
						 cell = row.createCell(colCount);
						 cell.setCellValue(obj.getPfz_empl_id()==null ? "" : obj.getPfz_empl_id());
						 cell.setCellStyle(leftAlign);
						 colCount++;
						 
						 cell = row.createCell(colCount);
						 cell.setCellValue(obj.getLogin_id()==null ? "" : obj.getLogin_id());
						 cell.setCellStyle(leftAlign);
						 colCount++;
						 cell = row.createCell(colCount);
						 cell.setCellValue(obj.getOthr_id()==null ? "" : obj.getOthr_id());
						 cell.setCellStyle(leftAlign);
						 colCount++;
						 
						 cell = row.createCell(colCount);
						 cell.setCellValue(obj.getOthr_id_name()==null ? "" : obj.getOthr_id_name());
						 cell.setCellStyle(leftAlign);
						 colCount++;
						 
						 cell = row.createCell(colCount);
						 cell.setCellValue(obj.getLogin_domain_nm()==null ? "" : obj.getLogin_domain_nm());
						 cell.setCellStyle(leftAlign);
						 colCount++;
						 
						 cell = row.createCell(colCount);
						 cell.setCellValue(obj.getCrm_role()==null ? "" : obj.getCrm_role());
						 cell.setCellStyle(leftAlign);
						 colCount++;
						 
						 cell = row.createCell(colCount);
						 cell.setCellValue(obj.getCrm_profile()==null ? "" : obj.getCrm_profile());
						 cell.setCellStyle(leftAlign);
						 colCount++;
						 
						 cell = row.createCell(colCount);
						 cell.setCellValue(obj.getFrst_nm()==null ? "" : obj.getFrst_nm());
						 cell.setCellStyle(leftAlign);
						 colCount++;
						 
						 cell = row.createCell(colCount);
						 cell.setCellValue(obj.getFrst_nm_2()==null ? "" : obj.getFrst_nm_2());
						 cell.setCellStyle(leftAlign);
						 colCount++;
						 
						 cell = row.createCell(colCount);
						 cell.setCellValue(obj.getMdl_nm()==null ? "" : obj.getMdl_nm());
						 cell.setCellStyle(leftAlign);
						 colCount++;
						 
						 cell = row.createCell(colCount);
						 cell.setCellValue(obj.getLast_nm()==null ? "" : obj.getLast_nm());
						 cell.setCellStyle(leftAlign);
						 colCount++;
						 
						 cell = row.createCell(colCount);
						 cell.setCellValue(obj.getLast_nm_2()==null ? "" : obj.getLast_nm_2());
						 cell.setCellStyle(leftAlign);
						 colCount++;
						 
						 cell = row.createCell(colCount);
						 cell.setCellValue(obj.getMaiden_nm()==null ? "" : obj.getMaiden_nm());
						 cell.setCellStyle(leftAlign);
						 colCount++;
						 
						 cell = row.createCell(colCount);
						 cell.setCellValue(obj.getMaiden_nm_2()==null ? "" : obj.getMaiden_nm_2());
						 cell.setCellStyle(leftAlign);
						 colCount++;
						 
						 cell = row.createCell(colCount);
						 cell.setCellValue(obj.getStatus()==null ? "" : obj.getStatus());
						 cell.setCellStyle(leftAlign);
						 colCount++;
						 
						 cell = row.createCell(colCount);
						 cell.setCellValue(obj.getEmploy_type()==null ? "" : obj.getEmploy_type());
						 cell.setCellStyle(leftAlign);
						 colCount++;
						 
						 cell = row.createCell(colCount);
						 cell.setCellValue(obj.getJob_title()==null ? "" : obj.getJob_title());
						 cell.setCellStyle(leftAlign);
						 colCount++;
						 
						 cell = row.createCell(colCount);
						 cell.setCellValue(obj.getPfz_empl_flg()==null ? "" : obj.getPfz_empl_flg());
						 cell.setCellStyle(leftAlign);
						 colCount++;
						 
						 cell = row.createCell(colCount);
						 cell.setCellValue(obj.getBkng_agnt()==null ? "" : obj.getBkng_agnt());
						 cell.setCellStyle(leftAlign);
						 colCount++;
						 
						 cell = row.createCell(colCount);
						 cell.setCellValue(obj.getPart_time()==null ? "" : obj.getPart_time());
						 cell.setCellStyle(leftAlign);
						 colCount++;
						 
						 cell = row.createCell(colCount);
						 cell.setCellValue(obj.getAvg_hrs_per_day()==null ? "" : obj.getAvg_hrs_per_day());
						 cell.setCellStyle(leftAlign);
						 colCount++;
						 
						 cell = row.createCell(colCount);
						 cell.setCellValue(obj.getEff_dt()==null ? "" : obj.getEff_dt());
						 cell.setCellStyle(leftAlign);
						 colCount++;
						 
						 cell = row.createCell(colCount);
						 cell.setCellValue(obj.getEnd_dt()==null ? "" : obj.getEnd_dt());
						 cell.setCellStyle(leftAlign);
						 colCount++;
						 
						 cell = row.createCell(colCount);
						 cell.setCellValue(obj.getAddr_lbl()==null ? "" : obj.getAddr_lbl());
						 cell.setCellStyle(leftAlign);
						 colCount++;
						 
						 cell = row.createCell(colCount);
						 cell.setCellValue(obj.getAddr_lbl_2()==null ? "" : obj.getAddr_lbl_2());
						 cell.setCellStyle(leftAlign);
						 colCount++;
						 
						 cell = row.createCell(colCount);
						 cell.setCellValue(obj.getAddr_lbl_long()==null ? "" : obj.getAddr_lbl_long());
						 cell.setCellStyle(leftAlign);
						 colCount++;
						 
						 cell = row.createCell(colCount);
						 cell.setCellValue(obj.getAddr_lbl_long_2()==null ? "" : obj.getAddr_lbl_long_2());
						 cell.setCellStyle(leftAlign);
						 colCount++;
						 
						 cell = row.createCell(colCount);
						 cell.setCellValue(obj.getAddr_ext_lbl()==null ? "" : obj.getAddr_ext_lbl());
						 cell.setCellStyle(leftAlign);
						 colCount++;
						 
						 
						 cell = row.createCell(colCount);
						 cell.setCellValue(obj.getCountry_lbl()==null ? "" :obj.getCountry_lbl());
						 cell.setCellStyle(leftAlign);
						 colCount++;
						 
						 cell = row.createCell(colCount);
						 cell.setCellValue(obj.getArea_lbl()==null ? "" : obj.getArea_lbl());
						 cell.setCellStyle(leftAlign);
						 colCount++;
						 
						 cell = row.createCell(colCount);
						 cell.setCellValue(obj.getLg_post_cd()==null ? "" : obj.getLg_post_cd());
						 cell.setCellStyle(leftAlign);
						 colCount++;
						 
						 cell = row.createCell(colCount);
						 cell.setCellValue(obj.getDistrict_cd()==null ? "" : obj.getDistrict_cd());
						 cell.setCellStyle(leftAlign);
						 colCount++;
						 
						 cell = row.createCell(colCount);
						 cell.setCellValue(obj.getPostal_city()==null ? "" : obj.getPostal_city());
						 cell.setCellStyle(leftAlign);
						 colCount++;
						 
						 cell = row.createCell(colCount);
						 cell.setCellValue(obj.getPostal_city_2()==null ? "" : obj.getPostal_city_2());
						 cell.setCellStyle(leftAlign);
						 colCount++;
						 
						 cell = row.createCell(colCount);
						 cell.setCellValue(obj.getInstreet_num()==null ? "" : obj.getInstreet_num());
						 cell.setCellStyle(leftAlign);
						 colCount++;
						 
						 cell = row.createCell(colCount);
						 cell.setCellValue(obj.getRegion_cd()==null ? "" : obj.getRegion_cd());
						 cell.setCellStyle(leftAlign);
						 colCount++;
						 
						 cell = row.createCell(colCount);
						 cell.setCellValue(obj.getCounty_cd()==null ? "" : obj.getCounty_cd());
						 cell.setCellStyle(leftAlign);
						 colCount++;
						 
						 cell = row.createCell(colCount);
						 cell.setCellValue(obj.getCanton_cd()==null ? "" : obj.getCanton_cd());
						 cell.setCellStyle(leftAlign);
						 colCount++;
						 
						 cell = row.createCell(colCount);
						 cell.setCellValue(obj.getCity_cd()==null ? "" : obj.getCity_cd());
						 cell.setCellStyle(leftAlign);
						 colCount++;
						 
						 cell = row.createCell(colCount);
						 cell.setCellValue(obj.getRegion()==null ? "" : obj.getRegion());
						 cell.setCellStyle(leftAlign);
						 colCount++;
						 
						 cell = row.createCell(colCount);
						 cell.setCellValue(obj.getCounty()==null ? "" : obj.getCounty());
						 cell.setCellStyle(leftAlign);
						 colCount++;
						 
						 cell = row.createCell(colCount);
						 cell.setCellValue(obj.getCanton()==null ? "" : obj.getCanton());
						 cell.setCellStyle(leftAlign);
						 colCount++;
						 
						 cell = row.createCell(colCount);
						 cell.setCellValue(obj.getCity()==null ? "" : obj.getCity());
						 cell.setCellStyle(leftAlign);
						 colCount++;
						 
						 cell = row.createCell(colCount);
						 cell.setCellValue(obj.getCity_2()==null ? "" : obj.getCity_2());
						 cell.setCellStyle(leftAlign);
						 colCount++;
						 
						 cell = row.createCell(colCount);
						 cell.setCellValue(obj.getWork_phone()==null ? "" : obj.getWork_phone());
						 cell.setCellStyle(leftAlign);
						 colCount++;
						 
						 cell = row.createCell(colCount);
						 cell.setCellValue(obj.getMobile_phone()==null ? "" : obj.getMobile_phone());
						 cell.setCellStyle(leftAlign);
						 colCount++;
						 
						 cell = row.createCell(colCount);
						 cell.setCellValue(obj.getPager()==null ? "" : obj.getPager());
						 cell.setCellStyle(leftAlign);
						 colCount++;
						 
						 cell = row.createCell(colCount);
						 cell.setCellValue(obj.getFax()==null ? "" : obj.getFax());
						 cell.setCellStyle(leftAlign);
						 colCount++;
						 
						 cell = row.createCell(colCount);
						 cell.setCellValue(obj.getHome_phone()==null ? "" : obj.getHome_phone());
						 cell.setCellStyle(leftAlign);
						 colCount++;
						 
						 cell = row.createCell(colCount);
						 cell.setCellValue(obj.getVoice_mail()==null ? "" : obj.getVoice_mail());
						 cell.setCellStyle(leftAlign);
						 colCount++;
						 
						 cell = row.createCell(colCount);
						 cell.setCellValue(obj.getEmail()==null ? "" : obj.getEmail());
						 cell.setCellStyle(leftAlign);
						 colCount++;
						 
						 cell = row.createCell(colCount);
						 cell.setCellValue(obj.getTime_zone()==null ? "" : obj.getTime_zone());
						 cell.setCellStyle(leftAlign);
						 colCount++;
						 
						 cell = row.createCell(colCount);
						 cell.setCellValue(obj.getLang_cd()==null ? "" : obj.getLang_cd());
						 cell.setCellStyle(leftAlign);
						 colCount++;
						 
						 cell = row.createCell(colCount);
						 cell.setCellValue(obj.getCreate_ts()==null ? "" : obj.getCreate_ts());
						 cell.setCellStyle(leftAlign);
						 colCount++;
						 
						 cell = row.createCell(colCount);
						 cell.setCellValue(obj.getLast_upd_ts()==null ? "" :obj.getLast_upd_ts());
						 cell.setCellStyle(leftAlign);
						 colCount++;
						 
						 cell = row.createCell(colCount);
						 cell.setCellValue(obj.getUpdated_by()==null ? "" : obj.getUpdated_by());
						 cell.setCellStyle(leftAlign);
						 colCount++;
						 
						 cell = row.createCell(colCount);
						 cell.setCellValue(obj.getActive()==null ? "" : obj.getActive());
						 cell.setCellStyle(leftAlign);
						 colCount++;
						 
						 cell = row.createCell(colCount);
						 cell.setCellValue(obj.getDel_ts()==null ? "" : obj.getDel_ts());
						 cell.setCellStyle(leftAlign);
						 colCount++;
						 
						 cell = row.createCell(colCount);
						 cell.setCellValue(obj.getTermination_date()==null ? "" : obj.getTermination_date());
						 cell.setCellStyle(leftAlign);
						 colCount++;
						 
						 cell = row.createCell(colCount);
						 cell.setCellValue(obj.getGender()==null ? "" : obj.getGender());
						 cell.setCellStyle(leftAlign);
						 colCount++;
						 
						 cell = row.createCell(colCount);
						 cell.setCellValue(obj.getEmployment_type_code()==null ? "" : obj.getEmployment_type_code());
						 cell.setCellStyle(leftAlign);
						 colCount++;
						 
						 cell = row.createCell(colCount);
						 cell.setCellValue(obj.getPrx_zip_file_name()==null ? "" : obj.getPrx_zip_file_name());
						 cell.setCellStyle(leftAlign);
						 colCount++; 
						 
						 cell = row.createCell(colCount);
						 cell.setCellValue(obj.getPrx_file_name()==null ? "" : obj.getPrx_file_name());
						 cell.setCellStyle(leftAlign);
						 colCount++;
						 
						 cell = row.createCell(colCount);
						 cell.setCellValue(obj.getDate_time()==null ? "" : sdf.format(obj.getDate_time()));
						 cell.setCellStyle(leftAlign);
						 colCount++;
						 
						 cell = row.createCell(colCount);
						 cell.setCellValue(obj.getDate_time_recd()==null ? "" : sdf.format(obj.getDate_time_recd()));
						 cell.setCellStyle(leftAlign);
						 colCount++;
						 
						 
						 colCount=0;
						 rowCount++;
					 }
					 
						FileOutputStream fileOutputStream = new FileOutputStream(filepath);
						wb.write(fileOutputStream);
						System.out.println("Excel generated................................");
						
						filename=usermasterservice.generateExcellock(filepath, filename,empId,".xlsx");

				}
				catch (Exception e) {
					throw e;
				}
				finally{
					wb.close();
				}
				return filename;
			
	}
	


}
