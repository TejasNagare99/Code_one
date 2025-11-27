package com.excel.service;

import java.io.File;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.excel.bean.ReportBean;
import com.excel.model.Usermaster;
import com.excel.model.dispatch_register_report2;
import com.excel.model.dispatch_register_report2_with_parameters;
import com.excel.repository.UserMasterRepository;
import com.excel.security.PasswordManager;
import com.excel.utility.MedicoConstants;
import com.excel.utility.MedicoResources;
import com.excel.utility.ReportFormats;

import net.lingala.zip4j.core.ZipFile;
import net.lingala.zip4j.model.ZipParameters;
import net.lingala.zip4j.util.Zip4jConstants;
@Service
public class DispatchRegisterReport2Impl implements DispatchRegisterReport2,MedicoConstants{
	@Autowired private UserMasterService usermasterservice;

	@Autowired private UserMasterRepository usermasterrepository;
	@Override
	public String generateDispatchRegisterReport2Excel(List<dispatch_register_report2> list, ReportBean bean,
			String companyname,String empId) throws Exception {
		String filename = "dispatch_register_report_Doctor_supply" + new Date().getTime()+ ".xlsx" ;
		String filepath = REPORT_FILE_PATH + filename;
		System.out.println("filepath:::::::::"+filepath);
		XSSFWorkbook workbook = null;
		try {
			workbook = new XSSFWorkbook();
			File f = new File(REPORT_FILE_PATH);
			if (!f.exists()) {
				if (f.mkdirs()) {
				} else {
					throw new RuntimeException("Could not create directory");
				}
			}
			XSSFSheet sheet = workbook.createSheet("Dispatch Register Report - Doctor Supply");
			
			XSSFRow row = null;
			XSSFCell cell = null;
			int rowCount = 0;
			int colCount = 0;
			String filterList="";
			if(bean.getProductname()!=null && !bean.getProductname().equals("")) {
				filterList+=" Product name::"+bean.getProductname();
			}
			if(bean.getProductType()!=null && !bean.getProductType().equals("")) {
				filterList+=" Product Type::"+bean.getProductType();
			}
			if(bean.getMonthofuse()!=null && !bean.getMonthofuse().equals("")) {
				filterList+=" Month Of Use::"+bean.getMonthofuse();
			}
			if(bean.getcName()!=null && !bean.getcName().equals("")) {
				filterList+=" Courier Name::"+bean.getcName();
			}
			if(bean.getDeliStatus()!=null && !bean.getDeliStatus().equals("")) {
				filterList+=" Delivery Status::"+bean.getDeliStatus();
			}
			if(bean.getSub_team_name()!=null && !bean.getSub_team_name().equals("")) {
				filterList+=" Sub Team Name::"+bean.getSub_team_name();
			}
			if(bean.getChallanNum()!=null && !bean.getChallanNum().equals("")) {
				filterList+=" Challan Number::"+bean.getChallanNum();
			}
			if(bean.getDocName()!=null && !bean.getDocName().equals("")) {
				filterList+=" Doc Number::"+bean.getDocName();
			}
			if(bean.getEmployeeNum()!=null && !bean.getEmployeeNum().equals("")) {
				filterList+=" Employee Number::"+bean.getEmployeeNum();
			}
			if(bean.getChallanDate()!=null && !bean.getChallanDate().equals("")) {
				filterList+=" Challan Date::"+bean.getChallanDate();
			}
			if(bean.getResponse()!=null && !bean.getResponse().equals("")) {
				filterList+=" Response::"+bean.getResponse();
			}
			if(bean.getEmployeeName()!=null && !bean.getEmployeeName().equals("")) {
				filterList+=" Employee Name::"+bean.getEmployeeName();
			}
			if(bean.getLrNum()!=null && !bean.getLrNum().equals("")) {
				filterList+=" LR Num::"+bean.getLrNum();
			}
			if(bean.getRegName()!=null && !bean.getRegName().equals("")) {
				filterList+=" Reg Name::"+bean.getRegName();
			}
			if(bean.getTerrCode()!=null && !bean.getTerrCode().equals("")) {
				filterList+=" Territory Code::"+bean.getTerrCode();
			}
			if(bean.getDivision()!=null && !bean.getDivision().equals("")) {
				filterList+=" Division::"+bean.getDivision();
			}
			if(bean.getTeam()!=null && !bean.getTeam().equals("")) {
				filterList+=" Team::"+bean.getTeam();
			}
			if(bean.getLrDate()!=null && !bean.getLrDate().equals("")) {
				filterList+=" LR Date::"+bean.getLrDate();
			}
			if(bean.getDmName()!=null && !bean.getDmName().equals("")) {
				filterList+=" DM Name::"+bean.getDmName();
			}
			
			String headings[] = {  "Sr.No.","BU","Team Name" ,"Team Code","Requested By","Request Date","Mcl No","Name Of Collegue/Doctor",
					"Destination","Product Code","Product Type","Item Dispatched","Requested Qty","Approved Qty","Dispatch Qty","Batch No","Expiry Date","Challan No",
					"Challan Date","Dr Request Letter","Courier Name","L.R No","L.R Date","Delivery Date","Pod Details","Received By","Dr Ack(Y/N)",
					"Dr Ack Date","Email HCP","Contact Number HCP","Address1","Address2","Address3","Address4","Remarks","Territory Code","Region Name","DM Name","Delivery Status"};
			
			XSSFCellStyle headingCellStyle = ReportFormats.heading(workbook);
			XSSFCellStyle columnHeadingStyle = ReportFormats.columnHeading(workbook);
			XSSFCellStyle cellAlignment = ReportFormats.setCellAlignment(workbook, ALIGN_RIGHT);
			cellAlignment.setDataFormat(workbook.createDataFormat().getFormat("0.00"));
			
			XSSFCellStyle cellAlignment2 = ReportFormats.setCellAlignment(workbook, ALIGN_RIGHT);
			
			row = sheet.createRow(rowCount);
			cell = row.createCell(colCount);
			sheet.addMergedRegion(new CellRangeAddress(rowCount, rowCount, colCount, colCount + headings.length - 1));
			cell.setCellValue(companyname);
			cell.setCellStyle(headingCellStyle);
			rowCount++;
			
			row = sheet.createRow(rowCount);
			cell = row.createCell(colCount);
			sheet.addMergedRegion(new CellRangeAddress(rowCount, rowCount, colCount, colCount + headings.length - 1));
			cell.setCellValue("Dispatch Register Report - Doctor Supply");
			cell.setCellStyle(headingCellStyle);
			rowCount++;

			row = sheet.createRow(rowCount);
			cell = row.createCell(colCount);
			sheet.addMergedRegion(new CellRangeAddress(rowCount, rowCount, colCount, colCount + headings.length - 1));
			cell.setCellValue("Period: "+MedicoResources.convertUtilDateToString_DD_MM_YYYY_(bean.getStartDate())+" to "+MedicoResources.convertUtilDateToString_DD_MM_YYYY_(bean.getEndDate()));
			cell.setCellStyle(headingCellStyle);
		    rowCount++;
			
			row = sheet.createRow(rowCount);
			cell = row.createCell(colCount);
			sheet.addMergedRegion(new CellRangeAddress(rowCount, rowCount, colCount, colCount + headings.length - 1));
			cell.setCellValue("Filters used are: "+filterList);
			cell.setCellStyle(headingCellStyle);
			
			rowCount += 1;
			row = sheet.createRow(rowCount);
			
			for (String heading : headings) {
				cell = ReportFormats.cell(row, colCount, heading, columnHeadingStyle);
				colCount++;
			}
			
	//		rowCount += 1;
			colCount=0;
			
			sheet.groupRow(0, 2);
			sheet.createFreezePane(0, 5);
			
			rowCount += 1;
			int count=1;
			for (dispatch_register_report2 data : list) {
				colCount = 0;
				row = sheet.createRow(rowCount);
				cell = ReportFormats.cell(row, colCount, String.valueOf(count), cellAlignment);
				colCount++;
				
				cell = ReportFormats.cell(row, colCount, data.getBu(), null);
				colCount++;
				
				cell = ReportFormats.cell(row, colCount, data.getTeam_name(), null);
				colCount++;
				
				cell = ReportFormats.cell(row, colCount, data.getTeam_code(), null);
				colCount++;
				
				cell = ReportFormats.cell(row, colCount, data.getRequested_by(), null);
				colCount++;
				
				cell = ReportFormats.cell(row, colCount, data.getRequest_date(), null);
				colCount++;
				
				cell = ReportFormats.cell(row, colCount, data.getAllocated_to_mclno(), null);
				colCount++;
				
				cell = ReportFormats.cell(row, colCount, data.getName_collegue_doctor(), null);
				colCount++;
				
				cell = ReportFormats.cell(row, colCount, data.getDestination(), null);
				colCount++;
				
				cell = ReportFormats.cell(row, colCount, data.getProduct_code(), null);
				colCount++;
				
				cell = ReportFormats.cell(row, colCount, data.getProduct_type(), null);
				colCount++;
				
				cell = ReportFormats.cell(row, colCount, data.getItem_dispatched(), null);
				colCount++;
				//
				cell = ReportFormats.cell(row, colCount, data.getRequested_qty(), null);
				colCount++;
				
				cell = ReportFormats.cell(row, colCount, data.getApproved_qty(), null);
				colCount++;
				
				cell = ReportFormats.cell(row, colCount, data.getDispatched_qty(), null);
				colCount++;
				
				cell = ReportFormats.cell(row, colCount, data.getBatch_no(), null);
				colCount++;
				
				cell = ReportFormats.cell(row, colCount, data.getExp_date(), null);
				colCount++;
				//
				cell = ReportFormats.cell(row, colCount, data.getChallan_no(), null);
				colCount++;
				
				cell = ReportFormats.cell(row, colCount, data.getChallan_date(), null);
				colCount++;
				
				cell = ReportFormats.cell(row, colCount, data.getDr_request_letter(), null);
				colCount++;
				
				cell = ReportFormats.cell(row, colCount, data.getCourier_name(), null);
				colCount++;
				
				cell = ReportFormats.cell(row, colCount, data.getLr_no(), null);
				colCount++;
				
				cell = ReportFormats.cell(row, colCount, data.getLr_dated(), null);
				colCount++;
				
				cell = ReportFormats.cell(row, colCount, data.getDelivery_date(), null);
				colCount++;
				
				cell = ReportFormats.cell(row, colCount, data.getPod_details(), null);
				colCount++;
				
				cell = ReportFormats.cell(row, colCount, data.getRecdby(), null);
				colCount++;
				
				cell = ReportFormats.cell(row, colCount, data.getDr_ack(), null);
				colCount++;
				
				cell = ReportFormats.cell(row, colCount, data.getDr_ack_date(), null);
				colCount++;
				
				cell = ReportFormats.cell(row, colCount, data.getEmail_hcp(), null);
				colCount++;
				
				cell = ReportFormats.cell(row, colCount, data.getContact_number_hcp(), null);
				colCount++;
				
				cell = ReportFormats.cell(row, colCount, data.getAddress1(), null);
				colCount++;
				
				cell = ReportFormats.cell(row, colCount, data.getAddress2(), null);
				colCount++;
				
				cell = ReportFormats.cell(row, colCount, data.getAddress3(), null);
				colCount++;
				
				cell = ReportFormats.cell(row, colCount, data.getAddress4(), null);
				colCount++;
				
				cell = ReportFormats.cell(row, colCount, data.getRemarks(), null);
				colCount++;
				
                cell = ReportFormats.cell(row, colCount, data.getTerritory_code() == null ?"":data.getTerritory_code(), null);
				colCount++;
				
				cell = ReportFormats.cell(row, colCount, data.getRegion(), null);
				colCount++;
				
				cell = ReportFormats.cell(row, colCount, data.getDm(), null);
				colCount++;
				
				cell = ReportFormats.cell(row, colCount, data.getDelivery_status(), null);
				colCount++;
				
			    colCount=0;
				rowCount++;
				count++;
			}
			FileOutputStream fileOutputStream = new FileOutputStream(filepath);
			workbook.write(fileOutputStream);
			
			filename=usermasterservice.generateExcellock(filepath, filename,empId,".xlsx");

		} finally {
			if (workbook != null) { workbook.close(); }
		}
		System.out.println("Dispatch Register report for doctor supply excel report generated :::"+filename);
		return filename;
	}

	@Override
	public String generateDispatchRegisterRevisedReport2ExcelDoctorSupply(
			List<dispatch_register_report2_with_parameters> list, ReportBean bean, String companyname,String empId)
			throws Exception {
		String filename = "dispatch_register_report_Doctor_supply" + new Date().getTime() + ".xlsx";
		String filepath = REPORT_FILE_PATH + filename;
		System.out.println("filepath:::::::::"+filepath);
		XSSFWorkbook workbook = null;
		try {
			workbook = new XSSFWorkbook();
			File f = new File(REPORT_FILE_PATH);
			if (!f.exists()) {
				if (f.mkdirs()) {
				} else {
					throw new RuntimeException("Could not create directory");
				}
			}
			XSSFSheet sheet = workbook.createSheet("Dispatch Register Report - Doctor Supply");
			
			XSSFRow row = null;
			XSSFCell cell = null;
			int rowCount = 0;
			int colCount = 0;
			String filterList="";
			if(bean.getProdName()!=null && !bean.getProdName().equals("")) {
				filterList+=" Product name::"+bean.getProdName();
			}
			if(bean.getProdType()!=null && !bean.getProdType().equals("")) {
				filterList+=" Product Type::"+bean.getProdType();
			}
			if(bean.getPeriod()!=null && !bean.getPeriod().equals("")) {
				filterList+=" Month Of Use::"+bean.getPeriod();
			}
			if(bean.getcName()!=null && !bean.getcName().equals("")) {
				filterList+=" Courier Name::"+bean.getcName();
			}
			if(bean.getDeliStatus()!=null && !bean.getDeliStatus().equals("")) {
				filterList+=" Delivery Status::"+bean.getDeliStatus();
			}
			if(bean.gettName()!=null && !bean.gettName().equals("")) {
				filterList+=" Sub Team Name::"+bean.gettName();
			}
			if(bean.getChallanNum()!=null && !bean.getChallanNum().equals("")) {
				filterList+=" Challan Number::"+bean.getChallanNum();
			}
			if(bean.getDocName()!=null && !bean.getDocName().equals("")) {
				filterList+=" Doc Number::"+bean.getDocName();
			}
			if(bean.getEmpNumber()!=null && !bean.getEmpNumber().equals("")) {
				filterList+=" Employee Number::"+bean.getEmpNumber();
			}
			if(bean.getChallanDate()!=null && !bean.getChallanDate().equals("")) {
				filterList+=" Challan Date::"+bean.getChallanDate();
			}
			if(bean.getDocResponse()!=null && !bean.getDocResponse().equals("")) {
				filterList+=" Response::"+bean.getDocResponse();
			}
			if(bean.getEmpName()!=null && !bean.getEmpName().equals("")) {
				filterList+=" Employee Name::"+bean.getEmpName();
			}
			if(bean.getLrNum()!=null && !bean.getLrNum().equals("")) {
				filterList+=" LR Num::"+bean.getLrNum();
			}
			if(bean.getRegName()!=null && !bean.getRegName().equals("")) {
				filterList+=" Reg Name::"+bean.getRegName();
			}
			if(bean.getDivId()!=null && !bean.getDivId().equals("")) {
				filterList+=" Division::"+bean.getDivId();
			}
			if(bean.getTeam()!=null && !bean.getTeam().equals("")) {
				filterList+=" Team::"+bean.getTeam();
			}
			if(bean.getLrDate()!=null && !bean.getLrDate().equals("")) {
				filterList+=" LR Date::"+bean.getLrDate();
			}
			if(bean.getDmName()!=null && !bean.getDmName().equals("")) {
				filterList+=" DM Name::"+bean.getDmName();
			}
			if(bean.getTerrCode()!=null && !bean.getTerrCode().equals("")) {
				filterList+="Terr Code::"+bean.getTerrCode();
			}
			String headings[] = { "Sr.No.","BU","Team Name" ,"Team Code","Requested By","Requested Number","Request Date","MCL NUMBER","Name Of Doctor",
					"Destination","Product Code","Product Type","Item Dispatched","Requested Qty","Approved Qty","Dispatch Qty","Batch No","Expiry Date","Challan No",
					"Challan Date","Dr Request Letter(Y/N)?","Courier Name","L.R No","L.R Date","Delivery Date","Pod Details","Received By","Dr Ack(Y/N)",
					"Dr Ack Date","Email HCP","Contact Number HCP","Address1","Address2","Address3","Address4","Remarks","Territory Code","Region Name","DM Name","Delivery Status"};
			
			XSSFCellStyle headingCellStyle = ReportFormats.heading(workbook);
			XSSFCellStyle columnHeadingStyle = ReportFormats.columnHeading(workbook);
			XSSFCellStyle cellAlignment = ReportFormats.setCellAlignment(workbook, ALIGN_RIGHT);
			cellAlignment.setDataFormat(workbook.createDataFormat().getFormat("0.00"));
			
			XSSFCellStyle cellAlignment2 = ReportFormats.setCellAlignment(workbook, ALIGN_RIGHT);
			
			row = sheet.createRow(rowCount);
			cell = row.createCell(colCount);
			sheet.addMergedRegion(new CellRangeAddress(rowCount, rowCount, colCount, colCount + headings.length - 1));
			cell.setCellValue(companyname);
			cell.setCellStyle(headingCellStyle);
			rowCount++;
			
			row = sheet.createRow(rowCount);
			cell = row.createCell(colCount);
			sheet.addMergedRegion(new CellRangeAddress(rowCount, rowCount, colCount, colCount + headings.length - 1));
			cell.setCellValue("Dispatch Register Report - Doctor Supply");
			cell.setCellStyle(headingCellStyle);
			rowCount++;

			row = sheet.createRow(rowCount);
			cell = row.createCell(colCount);
			sheet.addMergedRegion(new CellRangeAddress(rowCount, rowCount, colCount, colCount + headings.length - 1));
			cell.setCellValue("Period: "+MedicoResources.convertUtilDateToString_DD_MM_YYYY_(bean.getStartDate())+" to "+MedicoResources.convertUtilDateToString_DD_MM_YYYY_(bean.getEndDate()));
			cell.setCellStyle(headingCellStyle);
		    rowCount++;
			
			row = sheet.createRow(rowCount);
			cell = row.createCell(colCount);
			sheet.addMergedRegion(new CellRangeAddress(rowCount, rowCount, colCount, colCount + headings.length - 1));
			cell.setCellValue("Filters used are: "+filterList);
			cell.setCellStyle(headingCellStyle);
			
			rowCount += 1;
			row = sheet.createRow(rowCount);
			
			for (String heading : headings) {
				cell = ReportFormats.cell(row, colCount, heading, columnHeadingStyle);
				colCount++;
			}
			
	//		rowCount += 1;
			colCount=0;
			
			sheet.groupRow(0, 2);
			sheet.createFreezePane(0, 5);
			
			rowCount += 1;
			int count=1;
			for (dispatch_register_report2_with_parameters data : list) {
				colCount = 0;
				row = sheet.createRow(rowCount);
				cell = ReportFormats.cell(row, colCount, String.valueOf(count), cellAlignment);
				colCount++;
				
				cell = ReportFormats.cell(row, colCount, data.getBu(), null);
				colCount++;
				
				cell = ReportFormats.cell(row, colCount, data.getTeam_name(), null);
				colCount++;
				
				cell = ReportFormats.cell(row, colCount, data.getTeam_code(), null);
				colCount++;
				
				cell = ReportFormats.cell(row, colCount, data.getRequested_by(), null);
				colCount++;
				
				cell = ReportFormats.cell(row, colCount, data.getDr_request_letter(), null);
				colCount++;
				
				cell = ReportFormats.cell(row, colCount, data.getRequest_date(), null);
				colCount++;
				
				cell = ReportFormats.cell(row, colCount, data.getAllocated_to_mclno(), null);
				colCount++;
				
				cell = ReportFormats.cell(row, colCount, data.getName_collegue_doctor(), null);
				colCount++;
				
				cell = ReportFormats.cell(row, colCount, data.getDestination(), null);
				colCount++;
				
				cell = ReportFormats.cell(row, colCount, data.getProduct_code(), null);
				colCount++;
				
				cell = ReportFormats.cell(row, colCount, data.getProduct_type(), null);
				colCount++;
				
				cell = ReportFormats.cell(row, colCount, data.getItem_dispatched(), null);
				colCount++;
				//
				cell = ReportFormats.cell(row, colCount, data.getRequested_qty(), null);
				colCount++;
				
				cell = ReportFormats.cell(row, colCount, data.getApproved_qty(), null);
				colCount++;
				
				cell = ReportFormats.cell(row, colCount, data.getDispatched_qty(), null);
				colCount++;
				
				cell = ReportFormats.cell(row, colCount, data.getBatch_no(), null);
				colCount++;
				
				cell = ReportFormats.cell(row, colCount, data.getExp_date(), null);
				colCount++;
				//
				cell = ReportFormats.cell(row, colCount, data.getChallan_no(), null);
				colCount++;
				
				cell = ReportFormats.cell(row, colCount, data.getChallan_date(), null);
				colCount++;
				
				cell = ReportFormats.cell(row, colCount, data.getDoc_letter(), null);
				colCount++;
				
				cell = ReportFormats.cell(row, colCount, data.getCourier_name(), null);
				colCount++;
				
				cell = ReportFormats.cell(row, colCount, data.getLr_no(), null);
				colCount++;
				
				cell = ReportFormats.cell(row, colCount, data.getLr_dated(), null);
				colCount++;
				
				cell = ReportFormats.cell(row, colCount, data.getDelivery_date(), null);
				colCount++;
				
				cell = ReportFormats.cell(row, colCount, data.getPod_details(), null);
				colCount++;
				
				cell = ReportFormats.cell(row, colCount, data.getRecdby(), null);
				colCount++;
				
				cell = ReportFormats.cell(row, colCount, data.getDr_ack(), null);
				colCount++;
				
				cell = ReportFormats.cell(row, colCount, data.getDr_ack_date(), null);
				colCount++;
				
				cell = ReportFormats.cell(row, colCount, data.getEmail_hcp(), null);
				colCount++;
				
				cell = ReportFormats.cell(row, colCount, data.getContact_number_hcp(), null);
				colCount++;
				
				cell = ReportFormats.cell(row, colCount, data.getAddress1(), null);
				colCount++;
				
				cell = ReportFormats.cell(row, colCount, data.getAddress2(), null);
				colCount++;
				
				cell = ReportFormats.cell(row, colCount, data.getAddress3(), null);
				colCount++;
				
				cell = ReportFormats.cell(row, colCount, data.getAddress4(), null);
				colCount++;
				
				cell = ReportFormats.cell(row, colCount, data.getRemarks(), null);
				colCount++;
				
                cell = ReportFormats.cell(row, colCount, data.getTerritory_code() == null ?"":data.getTerritory_code(), null);
				colCount++;
				
				cell = ReportFormats.cell(row, colCount, data.getRegion(), null);
				colCount++;
				
				cell = ReportFormats.cell(row, colCount, data.getDm(), null);
				colCount++;
				
				cell = ReportFormats.cell(row, colCount, data.getDelivery_status(), null);
				colCount++;
				
			    colCount=0;
				rowCount++;
				count++;
			}
			FileOutputStream fileOutputStream = new FileOutputStream(filepath);
			workbook.write(fileOutputStream);
			
			filename=usermasterservice.generateExcellock(filepath, filename,empId,".xlsx");

		} finally {
			if (workbook != null) { workbook.close(); }
		}
		System.out.println("Dispatch Register report for doctor supply excel report generated :::"+filename);
		return filename;
	}

}
