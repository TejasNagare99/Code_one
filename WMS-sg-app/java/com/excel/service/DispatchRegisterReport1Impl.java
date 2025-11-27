package com.excel.service;

import java.io.File;
import java.io.FileOutputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.streaming.SXSSFSheet;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.excel.bean.ReportBean;
import com.excel.model.DispatchIntimationEmail;
import com.excel.model.dispatch_register_report1;
import com.excel.model.dispatch_register_report1_with_parameters;
import com.excel.repository.UserMasterRepository;
import com.excel.utility.MedicoConstants;
import com.excel.utility.MedicoResources;
import com.excel.utility.ReportFormats;
@Service
public class DispatchRegisterReport1Impl implements DispatchRegisterReport1,MedicoConstants{
	@Autowired private UserMasterService usermasterservice;

	@Autowired private UserMasterRepository usermasterrepository;
	@Override
	public String generateDispatchRegisterReport1Excel(List<dispatch_register_report1> list, ReportBean bean,
			String companyname,String empId) throws Exception {
		String filename = "dispatch_register_report1" + new Date().getTime()+ ".xlsx";
		String filepath = REPORT_FILE_PATH + filename ;
		System.out.println("filepath:::::::::" + filepath);
		SXSSFWorkbook workbook = null;
		try {
			workbook = new SXSSFWorkbook(1);
			File f = new File(REPORT_FILE_PATH);
			if (!f.exists()) {
				if (f.mkdirs()) {
				} else {
					throw new RuntimeException("Could not create directory");
				}
			}
			SXSSFSheet sheet = workbook.createSheet("Dispatch Register Report 1");

			Row row = null;
			Cell cell = null;
			int rowCount = 0;
			int colCount = 0;
			String filterList = "";
			if (bean.getProductname() != null && !bean.getProductname().equals("")) {
				filterList += " Product name::" + bean.getProductname();
			}
			if (bean.getProductType() != null && !bean.getProductType().equals("")) {
				filterList += " Product Type::" + bean.getProductType();
			}
			if (bean.getMonthofuse() != null && !bean.getMonthofuse().equals("")) {
				filterList += " Month Of Use::" + bean.getMonthofuse();
			}
			if (bean.getcName() != null && !bean.getcName().equals("")) {
				filterList += " Courier Name::" + bean.getcName();
			}
			if (bean.getDeliStatus() != null && !bean.getDeliStatus().equals("")) {
				filterList += " Delivery Status::" + bean.getDeliStatus();
			}
			if (bean.getSub_team_name() != null && !bean.getSub_team_name().equals("")) {
				filterList += " Sub Team Name::" + bean.getSub_team_name();
			}
			if (bean.getChallanNum() != null && !bean.getChallanNum().equals("")) {
				filterList += " Challan Number::" + bean.getChallanNum();
			}
			if (bean.getDocName() != null && !bean.getDocName().equals("")) {
				filterList += " Doc Number::" + bean.getDocName();
			}
			if (bean.getEmployeeNum() != null && !bean.getEmployeeNum().equals("")) {
				filterList += " Employee Number::" + bean.getEmployeeNum();
			}
			if (bean.getChallanDate() != null && !bean.getChallanDate().equals("")) {
				filterList += " Challan Date::" + bean.getChallanDate();
			}
			if (bean.getResponse() != null && !bean.getResponse().equals("")) {
				filterList += " Response::" + bean.getResponse();
			}
			if (bean.getEmployeeName() != null && !bean.getEmployeeName().equals("")) {
				filterList += " Employee Name::" + bean.getEmployeeName();
			}
			if (bean.getLrNum() != null && !bean.getLrNum().equals("")) {
				filterList += " LR Num::" + bean.getLrNum();
			}
			if (bean.getRegName() != null && !bean.getRegName().equals("")) {
				filterList += " Reg Name::" + bean.getRegName();
			}
			if (bean.getTerrCode() != null && !bean.getTerrCode().equals("")) {
				filterList += " Territory Code::" + bean.getTerrCode();
			}
			if (bean.getDivision() != null && !bean.getDivision().equals("")) {
				filterList += " Division::" + bean.getDivision();
			}
			if (bean.getTeam() != null && !bean.getTeam().equals("")) {
				filterList += " Team::" + bean.getTeam();
			}
			if (bean.getLrDate() != null && !bean.getLrDate().equals("")) {
				filterList += " LR Date::" + bean.getLrDate();
			}
			if (bean.getDmName() != null && !bean.getDmName().equals("")) {
				filterList += " DM Name::" + bean.getDmName();
			}

			String headings[] = { "Sr.No.", "Team Name", "Sub Team Name", "Month Of Use", "Employee No",
					"Employee Name", "Region Name", "Designation", "Mobile No", "DM Name", "Territory Code",
					"Doctor Name", "Dispatch Type", "Challan No", "Challan Date", "Courier", "L.R No", "L.R Date",
					"Cases", "	Act.Wt(PSO)", "Product Type", "Product Name", "Product Code", "Batch", "Expiry Date",
					"Dispatch Quantity", "Delivery Date", "Delivery Status", "Received By ", "Courier Comments",
					"Remarks", "Emergency/Dtd Requestor Name", "Team Code", "Addr1", "Addr2", "Addr3", "Addr4", "Pin",
					"Destination", "Email Id", "Dsp Remarks" };

			CellStyle headingCellStyle = ReportFormats.headingCellStyle(workbook);
			CellStyle columnHeadingStyle = ReportFormats.columnHeadingNew(workbook);
			CellStyle cellAlignment = ReportFormats.setCellAlignmentNew(workbook, ALIGN_RIGHT);
			cellAlignment.setDataFormat(workbook.createDataFormat().getFormat("0.00"));

			CellStyle cellAlignment2 = ReportFormats.setCellAlignmentNew(workbook, ALIGN_RIGHT);

			row = sheet.createRow(rowCount);
			cell = row.createCell(colCount);
			sheet.addMergedRegion(new CellRangeAddress(rowCount, rowCount, colCount, colCount + headings.length - 1));
			cell.setCellValue(companyname);
			cell.setCellStyle(headingCellStyle);
			rowCount++;

			row = sheet.createRow(rowCount);
			cell = row.createCell(colCount);
			sheet.addMergedRegion(new CellRangeAddress(rowCount, rowCount, colCount, colCount + headings.length - 1));
			cell.setCellValue("Dispatch Register Report1");
			cell.setCellStyle(headingCellStyle);
			rowCount++;

			row = sheet.createRow(rowCount);
			cell = row.createCell(colCount);
			sheet.addMergedRegion(new CellRangeAddress(rowCount, rowCount, colCount, colCount + headings.length - 1));
			cell.setCellValue("Period: " + MedicoResources.convertUtilDateToString_DD_MM_YYYY_(bean.getStartDate())
					+ " to " + MedicoResources.convertUtilDateToString_DD_MM_YYYY_(bean.getEndDate()));
			cell.setCellStyle(headingCellStyle);
			rowCount++;

			row = sheet.createRow(rowCount);
			cell = row.createCell(colCount);
			sheet.addMergedRegion(new CellRangeAddress(rowCount, rowCount, colCount, colCount + headings.length - 1));
			cell.setCellValue("Filters used are: " + filterList);
			cell.setCellStyle(headingCellStyle);

			rowCount += 1;
			row = sheet.createRow(rowCount);

			for (String heading : headings) {
				cell = ReportFormats.cellnew(row, colCount, heading, columnHeadingStyle);
				colCount++;
			}

			// rowCount += 1;
			colCount = 0;

//			row = sheet.createRow(rowCount);
//			cell = row.createCell(colCount);
//			sheet.addMergedRegion(new CellRangeAddress(rowCount, rowCount, colCount, colCount + 13));
//			cell.setCellValue("Master Challan details");
//			cell.setCellStyle(headingCellStyle);
//			
//			cell = row.createCell(colCount+14);
//			sheet.addMergedRegion(new CellRangeAddress(rowCount, rowCount, colCount+14, colCount + 33));
//			cell.setCellValue("Individual Challan details");
//			cell.setCellStyle(headingCellStyle);
//			
//			cell = row.createCell(colCount+34);
//			sheet.addMergedRegion(new CellRangeAddress(rowCount, rowCount, colCount+34, colCount + 47));
//			cell.setCellValue("Product details");
//			cell.setCellStyle(headingCellStyle);

			sheet.groupRow(0, 2);
			sheet.createFreezePane(0, 5);

			rowCount += 1;
			int count = 1;
			int i = 0;
			for (dispatch_register_report1 data : list) {
				colCount = 0;
				// System.out.println("count:::"+i);
				i++;
				row = sheet.createRow(rowCount);
				cell = ReportFormats.cellnew(row, colCount, String.valueOf(count), cellAlignment);
				colCount++;

				cell = ReportFormats.cellnew(row, colCount, data.getTeam_name(), null);
				colCount++;

				cell = ReportFormats.cellnew(row, colCount, data.getSub_team_name(), null);
				colCount++;

				cell = ReportFormats.cellnew(row, colCount, data.getMonth_of_use(), null);
				colCount++;

				cell = ReportFormats.cellnew(row, colCount, data.getEmployee_no(), null);
				colCount++;

				cell = ReportFormats.cellnew(row, colCount, data.getEmployee_name(), null);
				colCount++;

				cell = ReportFormats.cellnew(row, colCount, data.getRegion(), null);
				colCount++;

				cell = ReportFormats.cellnew(row, colCount, data.getDestination(), null);
				colCount++;

				cell = ReportFormats.cellnew(row, colCount, data.getMobile(), null);
				colCount++;

				cell = ReportFormats.cellnew(row, colCount, data.getDm(), null);
				colCount++;

				cell = ReportFormats.cellnew(row, colCount, data.getTerritory_code(), null);
				colCount++;

				cell = ReportFormats.cellnew(row, colCount, data.getDoctor_name(), null);
				colCount++;

				cell = ReportFormats.cellnew(row, colCount, data.getDispatch_type(), null);
				colCount++;

				cell = ReportFormats.cellnew(row, colCount, data.getChallan_no(), null);
				colCount++;

				cell = ReportFormats.cellnew(row, colCount, data.getChallan_date(), null);
				colCount++;

				cell = ReportFormats.cellnew(row, colCount, data.getCourier(), null);
				colCount++;

				cell = ReportFormats.cellnew(row, colCount, data.getLr_no(), null);
				colCount++;

				cell = ReportFormats.cellnew(row, colCount, data.getLr_date(), null);
				colCount++;

				cell = ReportFormats.cellnew(row, colCount, data.getCases(), null);
				colCount++;

				cell = ReportFormats.cellnew(row, colCount, data.getAct_wt(), null);
				colCount++;

				cell = ReportFormats.cellnew(row, colCount, data.getProduct_type(), null);
				colCount++;

				cell = ReportFormats.cellnew(row, colCount, data.getProduct_name().trim(), null);
				colCount++;

				cell = ReportFormats.cellnew(row, colCount, data.getProduct_code(), null);
				colCount++;

				cell = ReportFormats.cellnew(row, colCount, data.getBatch(), null);
				colCount++;

				cell = ReportFormats.cellnew(row, colCount, data.getExpiry(), null);
				colCount++;

				cell = ReportFormats.cellnew(row, colCount, data.getDispatch_quantity(), null);
				colCount++;

				cell = ReportFormats.cellnew(row, colCount, data.getDelivery_date(), null);
				colCount++;

				cell = ReportFormats.cellnew(row, colCount, data.getDelivery_status(), null);
				colCount++;

				cell = ReportFormats.cellnew(row, colCount, data.getReceived_by(), null);
				colCount++;

				cell = ReportFormats.cellnew(row, colCount, data.getCourier_comments(), null);
				colCount++;

				cell = ReportFormats.cellnew(row, colCount, data.getRemarks(), null);
				colCount++;

				cell = ReportFormats.cellnew(row, colCount, data.getEmergency_dtd_requestor_name(), null);
				colCount++;

				cell = ReportFormats.cellnew(row, colCount, data.getTeam_code(), null);
				colCount++;

				cell = ReportFormats.cellnew(row, colCount, data.getAdd1(), null);
				colCount++;

				cell = ReportFormats.cellnew(row, colCount, data.getAdd2(), null);
				colCount++;

				cell = ReportFormats.cellnew(row, colCount, data.getAdd3(), null);
				colCount++;

				cell = ReportFormats.cellnew(row, colCount, data.getAdd4(), null);
				colCount++;

				cell = ReportFormats.cellnew(row, colCount, data.getPin(), null);
				colCount++;

				cell = ReportFormats.cellnew(row, colCount, data.getDestination(), null);
				colCount++;

				cell = ReportFormats.cellnew(row, colCount, data.getEmail_id(), null);
				colCount++;

				cell = ReportFormats.cellnew(row, colCount, data.getDsp_remarks(), null);
				colCount++;
				rowCount++;
				count++;
			}
			FileOutputStream fileOutputStream = new FileOutputStream(filepath);
			workbook.write(fileOutputStream);

			filename=usermasterservice.generateExcellock(filepath, filename,empId,".xlsx");

		} finally {
			if (workbook != null) {
				workbook.close();
			}
		}
		System.out.println("Dispatch Register report1 excel report generated :::" + filename);
		return filename;
	}

	@Override
	public String generateDispatchRegisterRevisedReport1Excel(List<dispatch_register_report1_with_parameters> list,
			ReportBean bean, String companyname,String empId) throws Exception {
		String filename = "dispatch_register_report1" + new Date().getTime() + ".xlsx";
		String filepath = REPORT_FILE_PATH + filename ;
		System.out.println("filepath:::::::::"+filepath);
		SXSSFWorkbook workbook = null;
		try {
			workbook = new SXSSFWorkbook(1);
			File f = new File(REPORT_FILE_PATH);
			if (!f.exists()) {
				if (f.mkdirs()) {
				} else {
					throw new RuntimeException("Could not create directory");
				}
			}
			SXSSFSheet sheet = workbook.createSheet("Dispatch Register Report 1");
			
			Row row = null;
			Cell cell = null;
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
			
			
			String headings[] = { "Sr.No.","Team Name" ,"Sub Team Name","Month Of Use","Employee No","Employee Name","Region Name",
					"Designation","Mobile No","DM Name","Territory Code","Doctor Name","Dispatch Type","Challan No","Challan Date",
					"Courier","L.R No","L.R Date","Cases","	Act.Wt(PSO)","Product Type","Product Name","Product Code","Batch",
					"Expiry Date","Dispatch Quantity","Delivery Date","Delivery Status","Received By ","Courier Comments","Remarks",
					"Emergency/Dtd Requestor Name","Team Code","Addr1","Addr2","Addr3","Addr4","Pin","Destination","Email Id","Dsp Remarks"};
			
			CellStyle headingCellStyle = ReportFormats.headingCellStyle(workbook);
			CellStyle columnHeadingStyle = ReportFormats.columnHeadingNew(workbook);
			CellStyle cellAlignment = ReportFormats.setCellAlignmentNew(workbook, ALIGN_RIGHT);
			cellAlignment.setDataFormat(workbook.createDataFormat().getFormat("0.00"));
			
			CellStyle cellAlignment2 = ReportFormats.setCellAlignmentNew(workbook, ALIGN_RIGHT);
			
			row = sheet.createRow(rowCount);
			cell = row.createCell(colCount);
			sheet.addMergedRegion(new CellRangeAddress(rowCount, rowCount, colCount, colCount + headings.length - 1));
			cell.setCellValue(companyname);
			cell.setCellStyle(headingCellStyle);
			rowCount++;
			
			row = sheet.createRow(rowCount);
			cell = row.createCell(colCount);
			sheet.addMergedRegion(new CellRangeAddress(rowCount, rowCount, colCount, colCount + headings.length - 1));
			cell.setCellValue("Dispatch Register Report1");
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
				cell = ReportFormats.cellnew(row, colCount, heading, columnHeadingStyle);
				colCount++;
			}
			
	//		rowCount += 1;
			colCount=0;
			
//			row = sheet.createRow(rowCount);
//			cell = row.createCell(colCount);
//			sheet.addMergedRegion(new CellRangeAddress(rowCount, rowCount, colCount, colCount + 13));
//			cell.setCellValue("Master Challan details");
//			cell.setCellStyle(headingCellStyle);
//			
//			cell = row.createCell(colCount+14);
//			sheet.addMergedRegion(new CellRangeAddress(rowCount, rowCount, colCount+14, colCount + 33));
//			cell.setCellValue("Individual Challan details");
//			cell.setCellStyle(headingCellStyle);
//			
//			cell = row.createCell(colCount+34);
//			sheet.addMergedRegion(new CellRangeAddress(rowCount, rowCount, colCount+34, colCount + 47));
//			cell.setCellValue("Product details");
//			cell.setCellStyle(headingCellStyle);
			
			sheet.groupRow(0, 2);
			sheet.createFreezePane(0, 5);
			
			rowCount += 1;
			int count=1;
			int i=0;
			for (dispatch_register_report1_with_parameters data : list) {
				colCount = 0;
				//System.out.println("count:::"+i);
				i++;
				row = sheet.createRow(rowCount);
				cell = ReportFormats.cellnew(row, colCount, String.valueOf(count), cellAlignment);
				colCount++;
				
				cell = ReportFormats.cellnew(row, colCount, data.getTeam_name(), null);
				colCount++;
				
				cell = ReportFormats.cellnew(row, colCount, data.getSub_team_name(), null);
				colCount++;
				
				cell = ReportFormats.cellnew(row, colCount, data.getMonth_of_use(), null);
				colCount++;
				
				cell = ReportFormats.cellnew(row, colCount, data.getEmployee_no(), null);
				colCount++;
				
				cell = ReportFormats.cellnew(row, colCount, data.getEmployee_name(), null);
				colCount++;
				
				cell = ReportFormats.cellnew(row, colCount, data.getRegion(), null);
				colCount++;
				
				cell = ReportFormats.cellnew(row, colCount, data.getDesignation(), null);
				colCount++;
				
				cell = ReportFormats.cellnew(row, colCount, data.getMobile(), null);
				colCount++;
				
				cell = ReportFormats.cellnew(row, colCount, data.getDm(), null);
				colCount++;
				
				cell = ReportFormats.cellnew(row, colCount, data.getTerritory_code(), null);
				colCount++;
				
				cell = ReportFormats.cellnew(row, colCount, data.getDoctor_name(), null);
				colCount++;
				
				cell = ReportFormats.cellnew(row, colCount, data.getDispatch_type(), null);
				colCount++;
				
				cell = ReportFormats.cellnew(row, colCount, data.getChallan_no(), null);
				colCount++;
				
				cell = ReportFormats.cellnew(row, colCount, data.getChallan_date(), null);
				colCount++;
				
				cell = ReportFormats.cellnew(row, colCount, data.getCourier(), null);
				colCount++;
				
				cell = ReportFormats.cellnew(row, colCount, data.getLr_no(), null);
				colCount++;
				
				cell = ReportFormats.cellnew(row, colCount, data.getLr_date(), null);
				colCount++;
				
				cell = ReportFormats.cellnew(row, colCount, data.getCases(), null);
				colCount++;
				
				cell = ReportFormats.cellnew(row, colCount, data.getAct_wt(), null);
				colCount++;
				
				cell = ReportFormats.cellnew(row, colCount, data.getProduct_type(), null);
				colCount++;
				
				cell = ReportFormats.cellnew(row, colCount, data.getProduct_name().trim(), null);
				colCount++;
				
				cell = ReportFormats.cellnew(row, colCount, data.getProduct_code(), null);
				colCount++;
				
				cell = ReportFormats.cellnew(row, colCount, data.getBatch(), null);
				colCount++;
				
				cell = ReportFormats.cellnew(row, colCount, data.getExpiry(), null);
				colCount++;
				
				cell = ReportFormats.cellnew(row, colCount, data.getDispatch_quantity(), null);
				colCount++;
				
				cell = ReportFormats.cellnew(row, colCount, data.getDelivery_date(), null);
				colCount++;
				
				cell = ReportFormats.cellnew(row, colCount, data.getDelivery_status(), null);
				colCount++;
				
				cell = ReportFormats.cellnew(row, colCount, data.getReceived_by(), null);
				colCount++;
			
				cell = ReportFormats.cellnew(row, colCount, data.getCourier_comments(), null);
				colCount++;
				
				cell = ReportFormats.cellnew(row, colCount, data.getRemarks(), null);
				colCount++;
				
				cell = ReportFormats.cellnew(row, colCount, data.getEmergency_dtd_requestor_name(), null);
				colCount++;
				
				cell = ReportFormats.cellnew(row, colCount, data.getTeam_code(), null);
				colCount++;
				
				cell = ReportFormats.cellnew(row, colCount, data.getAdd1(), null);
				colCount++;
				
				cell = ReportFormats.cellnew(row, colCount, data.getAdd2(), null);
				colCount++;
				
				cell = ReportFormats.cellnew(row, colCount, data.getAdd3(), null);
				colCount++;
				
				cell = ReportFormats.cellnew(row, colCount, data.getAdd4(), null);
				colCount++;
				
				cell = ReportFormats.cellnew(row, colCount, data.getPin(), null);
				colCount++;
				
				cell = ReportFormats.cellnew(row, colCount, data.getDestination(), null);
				colCount++;
				
				cell = ReportFormats.cellnew(row, colCount, data.getEmail_id(), null);
				colCount++;
				
				cell = ReportFormats.cellnew(row, colCount, data.getDsp_remarks(), null);
				colCount++;
				rowCount++;
				count++;
			}
			FileOutputStream fileOutputStream = new FileOutputStream(filepath);
			workbook.write(fileOutputStream);
			
			filename=usermasterservice.generateExcellock(filepath, filename,empId,".xlsx");

		} finally {
			if (workbook != null) { workbook.close(); }
		}
		System.out.println("Dispatch Register report1 excel report generated :::"+filename);
		return filename;
	}
	
	

	@Override
	public String generateDispatchIntimationEmail(List<DispatchIntimationEmail> list,String stdate, String endate)throws Exception {
		String filename = "Dispatch_Intimation_Email" + new Date().getTime()+ ".xlsx";
		String filepath = REPORT_FILE_PATH + filename ;
		System.out.println("filepath:::::::::" + filepath);
		SXSSFWorkbook workbook = null;
		try {
			workbook = new SXSSFWorkbook(1);
			File f = new File(REPORT_FILE_PATH);
			if (!f.exists()) {
				if (f.mkdirs()) {
				} else {
					throw new RuntimeException("Could not create directory");
				}
			}
			SXSSFSheet sheet = workbook.createSheet("Dispatch_Intimation_Email");

			Row row = null;
			Cell cell = null;
			int rowCount = 0;
			int colCount = 0;

			String headings[] = {"CHALLAN NO","CHALLAN DATE","EMPLOYEE NUMBER","EMPLOYEE NAME","TERRITORY NAME","DIVISION","HQ NAME","FIELD STAFF EMAIL","MONTH OF USE","LR ENTRY DATE & TIME"};

			CellStyle headingCellStyle = ReportFormats.headingCellStyle(workbook);
			CellStyle columnHeadingStyle = ReportFormats.columnHeadingNew(workbook);
			CellStyle cellAlignment = ReportFormats.setCellAlignmentNew(workbook, ALIGN_RIGHT);
			cellAlignment.setDataFormat(workbook.createDataFormat().getFormat("0.00"));

			CellStyle cellAlignment2 = ReportFormats.setCellAlignmentNew(workbook, ALIGN_RIGHT);

			row = sheet.createRow(rowCount);
			cell = row.createCell(colCount);
			sheet.addMergedRegion(new CellRangeAddress(rowCount, rowCount, colCount, colCount + headings.length - 1));
			cell.setCellValue("Pending Dispatch Intimation Email");
			cell.setCellStyle(headingCellStyle);
			rowCount++;
			
			String paisePattern = "yyyy/MM/dd";
			SimpleDateFormat parseDate = new SimpleDateFormat(paisePattern);
			Date stdt= parseDate.parse(stdate);
			Date endt= parseDate.parse(endate);
			String pattern = "dd/MM/yyyy";
			SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);			
			String stdate1 = simpleDateFormat.format(stdt);
			String endate1 = simpleDateFormat.format(endt);
			
			SimpleDateFormat sdfdateTime = new SimpleDateFormat("dd/MM/yyyy HH:mm:SS");
			
			row = sheet.createRow(rowCount);
			cell = row.createCell(colCount);
			sheet.addMergedRegion(new CellRangeAddress(rowCount, rowCount, colCount, colCount + headings.length - 1));
			cell.setCellValue("From " +stdate1+" To "+endate1);
			cell.setCellStyle(headingCellStyle);
			rowCount++;
			
			//rowCount += 1;
			row = sheet.createRow(rowCount);

			for (String heading : headings) {
				cell = ReportFormats.cellnew(row, colCount, heading, columnHeadingStyle);
				colCount++;
			}

			// rowCount += 1;
			colCount = 0;



			sheet.groupRow(0, 2);
			sheet.createFreezePane(0, 3);

			rowCount += 1;
			int count = 1;
			int i = 0;
			String olddsp_challan=" ";
			for (DispatchIntimationEmail data : list) {
				colCount = 0;
				i++;
				String newdsp_challan=data.getDsp_challan_no();
				if(!(olddsp_challan.equals(newdsp_challan))) {				
				row = sheet.createRow(rowCount);
//				cell = ReportFormats.cellnew(row, colCount, String.valueOf(count), cellAlignment);
//				colCount++;

				cell = ReportFormats.cellnew(row, colCount, data.getDsp_challan_no(), null);
				colCount++;

				cell = ReportFormats.cellnew(row, colCount, data.getDsp_date(), null);
				colCount++;
				
				cell = ReportFormats.cellnew(row, colCount, data.getDspfstaff_employeeno(), null);
				colCount++;
				
				cell = ReportFormats.cellnew(row, colCount, data.getDspfstaff_name(), null);
				colCount++;
				
				cell = ReportFormats.cellnew(row, colCount, data.getTerr_name(), null);
				colCount++;
				
				cell = ReportFormats.cellnew(row, colCount, data.getDivision(), null);
				colCount++;
				
				cell = ReportFormats.cellnew(row, colCount, data.getHq_name(), null);
				colCount++;

				cell = ReportFormats.cellnew(row, colCount, data.getFstaff_email(), null);
				colCount++;
				
				cell = ReportFormats.cellnew(row, colCount, data.getMonth_of_use(), null);
				colCount++;
				
				cell = ReportFormats.cellnew(row, colCount,sdfdateTime.format(data.getDsp_mod_dt()), null);
				colCount++;
				
				rowCount++;
				count++;
				}
				olddsp_challan=newdsp_challan;
				
				
			}
			FileOutputStream fileOutputStream = new FileOutputStream(filepath);
			workbook.write(fileOutputStream);

		} finally {
			if (workbook != null) {
				workbook.close();
			}
		}
		System.out.println("Dispatch Intimation Email excel report generated :::" + filename);
		return filename;
	}

}
