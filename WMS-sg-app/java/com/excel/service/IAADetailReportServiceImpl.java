package com.excel.service;

import java.io.File;
import java.io.FileOutputStream;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.excel.bean.ReportBean;
import com.excel.model.IAADetailReport;
import com.excel.model.IAADetailReport_SG;
import com.excel.repository.ParameterRepository;
import com.excel.repository.UserMasterRepository;
import com.excel.utility.ApachePoiExcelFormat;
import com.excel.utility.MedicoConstants;
import com.excel.utility.MedicoResources;
@Service
public class IAADetailReportServiceImpl implements IAADetailReportService,MedicoConstants{
	@Autowired private UserMasterService usermasterservice;
	@Autowired private ParameterRepository parameterRepository;
	@Autowired private UserMasterRepository usermasterrepository;
	@Override
	public String GenerateIAADetailReport(List<IAADetailReport> lst, String startDate, String endDate,
			String company,String empId,String gprmInd) throws Exception {

		// TODO Auto-generated method stub
		int rowCount = 0, colCount = 0;
		XSSFWorkbook wb = null;
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
		Date temp1 = sdf.parse(startDate);
		Date temp2 = sdf.parse(endDate);

		// String gprmInd = parameterRepository.getGprmIndicatorForPfz();

		String date1 = MedicoResources.convertUtilDateToString(temp1);
		String date2 = MedicoResources.convertUtilDateToString(temp2);

		String filename = "IAADetailReport" + new Date().getTime() + "_" + date1 + "_" + "To" + "_" + date2 + ".xlsx";
		try {
			String filepath = REPORT_FILE_PATH + filename;
			System.out.println("filepath:::" + filepath);

			wb = new XSSFWorkbook();
			File f = new File(REPORT_FILE_PATH);
			if (!f.exists()) {
				if (f.mkdirs()) {
				} else {
					wb.close();
					throw new RuntimeException("Could not create directory");
				}
			}

			XSSFSheet sheet = wb.createSheet("IAADetailReport");
			ApachePoiExcelFormat xlsxExcelFormat = new ApachePoiExcelFormat();
			XSSFRow row = null;
			XSSFCell cell = null;

			CellStyle sheetHeading = xlsxExcelFormat.SheetHeading(wb);
			CellStyle sheetHeadingLeft = xlsxExcelFormat.SheetHeading(wb);
			sheetHeadingLeft.setAlignment(HorizontalAlignment.LEFT);
			CellStyle columnHeading = xlsxExcelFormat.columnHeading(wb);
			CellStyle columnHeadingRight = xlsxExcelFormat.columnHeading(wb);
			columnHeadingRight.setAlignment(HorizontalAlignment.RIGHT);
			CellStyle leftAlign = xlsxExcelFormat.dataLeftAlign(wb);
			CellStyle leftAlign_left = xlsxExcelFormat.dataLeftAlign_total(wb);
			CellStyle rightAlign = xlsxExcelFormat.dataRightAlignWthDecimal(wb);
			CellStyle rightAlignTotal = xlsxExcelFormat.dataRightAlignWthoutDecimal_total(wb);

			String[] heading;

			if (gprmInd.equalsIgnoreCase("Y")) {
				String[] yValues = { "IAA No.", "Warehouse Name", "IAA Date", "Product Type", "Product Code",
						"Product Name", "Pack Size", "Batch No ", "Batch Expiry Date", "Reason", "Stock Type",
						"Stock Type Description", "Remarks", "Out Qty", "In Qty", "Unit Price", "Total Value",
						"Division" };
				heading = new String[yValues.length];
				System.arraycopy(yValues, 0, heading, 0, yValues.length);
			} else {
				String[] otherValues = { "IAA No.", "Warehouse Name", "IAA Date", "Product Type", "Product Code",
						"Product Name", "Pack Size", "Batch No ", "Batch Expiry Date", "Reason", "Stock Type",
						"Stock Type Description", "Remarks", "Out Qty", "In Qty" };
				heading = new String[otherValues.length];
				System.arraycopy(otherValues, 0, heading, 0, otherValues.length);
			}

			row = sheet.createRow(rowCount);
			sheet.addMergedRegion(new CellRangeAddress(rowCount, rowCount, colCount, heading.length - 1));
			cell = row.createCell(colCount);
			cell.setCellValue(company);
			cell.setCellStyle(sheetHeadingLeft);
			rowCount++;

			row = sheet.createRow(rowCount);
			sheet.addMergedRegion(new CellRangeAddress(rowCount, rowCount, colCount, heading.length - 1));
			cell = row.createCell(colCount);
			cell.setCellValue("IAA DETAIL REPORT");
			cell.setCellStyle(sheetHeadingLeft);
			rowCount++;

			row = sheet.createRow(rowCount);
			sheet.addMergedRegion(new CellRangeAddress(rowCount, rowCount, colCount, heading.length - 1));
			cell = row.createCell(colCount);
			// cell.setCellValue("From "+sdf.format(bean.getStartDate())+" To
			// "+sdf.format(bean.getEndDate()));
			cell.setCellValue("From " + startDate + " To " + endDate);
			cell.setCellStyle(sheetHeadingLeft);
			rowCount++;

			colCount = 0;
			row = sheet.createRow(rowCount);

			for (String s : heading) {
				cell = row.createCell(colCount);
				cell.setCellValue(s);
				cell.setCellStyle(columnHeading);
				colCount++;
			}
			rowCount++;
			colCount = 0;

			sheet.createFreezePane(0, 4);

			String prev_location_name = lst.get(0).getLoc_nm();

			BigDecimal Location_total = new BigDecimal(0);
			BigDecimal Grand_total = new BigDecimal(0);

			for (IAADetailReport obj : lst) {

				if (prev_location_name.equals(obj.getLoc_nm())) {

					Location_total = Location_total.add(obj.getTotal_value());
					row = sheet.createRow(rowCount);

					cell = row.createCell(colCount);
					cell.setCellValue(obj.getStkadj_no() == null ? "" : obj.getStkadj_no());
					cell.setCellStyle(leftAlign);
					colCount++;

					cell = row.createCell(colCount);
					cell.setCellValue(obj.getLoc_nm() == null ? "" : obj.getLoc_nm());
					cell.setCellStyle(leftAlign);
					colCount++;

					cell = row.createCell(colCount);
					cell.setCellValue(obj.getSadj_dt() == null ? "" : obj.getSadj_dt());
					cell.setCellStyle(leftAlign);
					colCount++;

					cell = row.createCell(colCount);
					cell.setCellValue(obj.getSmp_prod_type() == null ? "" : obj.getSmp_prod_type());
					cell.setCellStyle(leftAlign);
					colCount++;
					cell = row.createCell(colCount);
					cell.setCellValue(obj.getSmp_prod_cd() == null ? "" : obj.getSmp_prod_cd());
					cell.setCellStyle(leftAlign);
					colCount++;

					cell = row.createCell(colCount);
					cell.setCellValue(obj.getSmp_prod_name() == null ? "" : obj.getSmp_prod_name());
					cell.setCellStyle(leftAlign);
					colCount++;

					cell = row.createCell(colCount);
					cell.setCellValue(obj.getPacking() == null ? "" : obj.getPacking());
					cell.setCellStyle(leftAlign);
					colCount++;

					cell = row.createCell(colCount);
					cell.setCellValue(obj.getBatch_no() == null ? "" : obj.getBatch_no());
					cell.setCellStyle(leftAlign);
					colCount++;

					cell = row.createCell(colCount);
					cell.setCellValue(obj.getBatch_expiry_dt() == null ? "" : obj.getBatch_expiry_dt());
					cell.setCellStyle(leftAlign);
					colCount++;

					cell = row.createCell(colCount);
					cell.setCellValue(obj.getReason() == null ? "" : obj.getReason());
					cell.setCellStyle(leftAlign);
					colCount++;

					cell = row.createCell(colCount);
					cell.setCellValue(obj.getStock_type() == null ? "" : obj.getStock_type());
					cell.setCellStyle(leftAlign);
					colCount++;

					cell = row.createCell(colCount);
					cell.setCellValue(obj.getStock_type_desc() == null ? "" : obj.getStock_type_desc());
					cell.setCellStyle(leftAlign);
					colCount++;

					cell = row.createCell(colCount);
					cell.setCellValue(obj.getRemarks() == null ? "" : obj.getRemarks());
					cell.setCellStyle(leftAlign);
					colCount++;

					cell = row.createCell(colCount);
					cell.setCellValue(obj.getOut_qty() == null ? (BigDecimal.ZERO).setScale(0).intValue()
							: obj.getOut_qty().setScale(0).intValue());
					cell.setCellStyle(rightAlign);
					colCount++;

					cell = row.createCell(colCount);
					cell.setCellValue(obj.getIn_qty() == null ? (BigDecimal.ZERO).setScale(0).intValue()
							: obj.getIn_qty().setScale(0).intValue());
					cell.setCellStyle(rightAlign);
					colCount++;

					if (gprmInd.equalsIgnoreCase("Y")) {
						/*
						 * if (obj.getIn_qty().toString().equals("0.00")) { cell =
						 * row.createCell(colCount); cell.setCellValue("");
						 * cell.setCellStyle(rightAlign); colCount++;
						 * 
						 * cell = row.createCell(colCount); cell.setCellValue("");
						 * cell.setCellStyle(rightAlign); colCount++; } else {
						 */
						cell = row.createCell(colCount);
						BigDecimal unitPrice = obj.getUnit_price() == null ? BigDecimal.ZERO : obj.getUnit_price();
						cell.setCellValue(unitPrice.doubleValue());
						cell.setCellStyle(rightAlign);
						colCount++;

						cell = row.createCell(colCount);
						BigDecimal totVal = obj.getTotal_value() == null ? BigDecimal.ZERO
								: obj.getTotal_value().setScale(0, RoundingMode.HALF_UP);
						cell.setCellValue(totVal.intValue());
						cell.setCellStyle(rightAlign);
						colCount++;

						cell = row.createCell(colCount);
						cell.setCellValue(obj.getTeam_name() == null ? "" : obj.getTeam_name());
						cell.setCellStyle(leftAlign);
						colCount++;

					}
					colCount = 0;
					rowCount++;

					prev_location_name = obj.getLoc_nm();

				} else {

					row = sheet.createRow(rowCount);
					cell = row.createCell(0);
					cell.setCellValue("Total");
					cell.setCellStyle(leftAlign_left);
					for (int i = 1; i <= 15; i++) {

						cell = row.createCell(i);
						cell.setCellValue("");
						cell.setCellStyle(rightAlignTotal);
					}

					System.err.println("Location_total::::::" + Location_total);
					System.err.println("Location_total::::::" + Location_total.intValue());

					cell = row.createCell(16);
					cell.setCellValue(Location_total.intValue());
					cell.setCellStyle(rightAlignTotal);
					prev_location_name = obj.getLoc_nm();
					Grand_total = Grand_total.add(Location_total);
					Location_total = new BigDecimal(0);

					cell = row.createCell(17);
					cell.setCellValue("");
					cell.setCellStyle(rightAlignTotal);

					rowCount++;

					/////

					Location_total = Location_total.add(obj.getTotal_value());
					row = sheet.createRow(rowCount);

					cell = row.createCell(colCount);
					cell.setCellValue(obj.getStkadj_no() == null ? "" : obj.getStkadj_no());
					cell.setCellStyle(leftAlign);
					colCount++;

					cell = row.createCell(colCount);
					cell.setCellValue(obj.getLoc_nm() == null ? "" : obj.getLoc_nm());
					cell.setCellStyle(leftAlign);
					colCount++;

					cell = row.createCell(colCount);
					cell.setCellValue(obj.getSadj_dt() == null ? "" : obj.getSadj_dt());
					cell.setCellStyle(leftAlign);
					colCount++;

					cell = row.createCell(colCount);
					cell.setCellValue(obj.getSmp_prod_type() == null ? "" : obj.getSmp_prod_type());
					cell.setCellStyle(leftAlign);
					colCount++;
					cell = row.createCell(colCount);
					cell.setCellValue(obj.getSmp_prod_cd() == null ? "" : obj.getSmp_prod_cd());
					cell.setCellStyle(leftAlign);
					colCount++;

					cell = row.createCell(colCount);
					cell.setCellValue(obj.getSmp_prod_name() == null ? "" : obj.getSmp_prod_name());
					cell.setCellStyle(leftAlign);
					colCount++;

					cell = row.createCell(colCount);
					cell.setCellValue(obj.getPacking() == null ? "" : obj.getPacking());
					cell.setCellStyle(leftAlign);
					colCount++;

					cell = row.createCell(colCount);
					cell.setCellValue(obj.getBatch_no() == null ? "" : obj.getBatch_no());
					cell.setCellStyle(leftAlign);
					colCount++;

					cell = row.createCell(colCount);
					cell.setCellValue(obj.getBatch_expiry_dt() == null ? "" : obj.getBatch_expiry_dt());
					cell.setCellStyle(leftAlign);
					colCount++;

					cell = row.createCell(colCount);
					cell.setCellValue(obj.getReason() == null ? "" : obj.getReason());
					cell.setCellStyle(leftAlign);
					colCount++;

					cell = row.createCell(colCount);
					cell.setCellValue(obj.getStock_type() == null ? "" : obj.getStock_type());
					cell.setCellStyle(leftAlign);
					colCount++;

					cell = row.createCell(colCount);
					cell.setCellValue(obj.getStock_type_desc() == null ? "" : obj.getStock_type_desc());
					cell.setCellStyle(leftAlign);
					colCount++;

					cell = row.createCell(colCount);
					cell.setCellValue(obj.getRemarks() == null ? "" : obj.getRemarks());
					cell.setCellStyle(leftAlign);
					colCount++;

					cell = row.createCell(colCount);
					cell.setCellValue(obj.getOut_qty() == null ? (BigDecimal.ZERO).setScale(0).intValue()
							: obj.getOut_qty().setScale(0).intValue());
					cell.setCellStyle(rightAlign);
					colCount++;

					cell = row.createCell(colCount);
					cell.setCellValue(obj.getIn_qty() == null ? (BigDecimal.ZERO).setScale(0).intValue()
							: obj.getIn_qty().setScale(0).intValue());
					cell.setCellStyle(rightAlign);
					colCount++;

					if (gprmInd.equalsIgnoreCase("Y")) {
						/*
						 * if (obj.getIn_qty().toString().equals("0.00")) { cell =
						 * row.createCell(colCount); cell.setCellValue("");
						 * cell.setCellStyle(rightAlign); colCount++;
						 * 
						 * cell = row.createCell(colCount); cell.setCellValue("");
						 * cell.setCellStyle(rightAlign); colCount++; } else {
						 */
						cell = row.createCell(colCount);
						BigDecimal unitPrice = obj.getUnit_price() == null ? BigDecimal.ZERO : obj.getUnit_price();
						cell.setCellValue(unitPrice.doubleValue());
						cell.setCellStyle(rightAlign);
						colCount++;

						cell = row.createCell(colCount);
						BigDecimal totVal = obj.getTotal_value() == null ? BigDecimal.ZERO
								: obj.getTotal_value().setScale(0, RoundingMode.HALF_UP);

						System.err.println("totalvale::::::" + totVal);
						System.err.println("totalvale::::::" + totVal.intValue());
						cell.setCellValue(totVal.intValue());
						cell.setCellStyle(rightAlign);
						colCount++;

						/* } */

						cell = row.createCell(colCount);
						cell.setCellValue(obj.getTeam_name() == null ? "" : obj.getTeam_name());
						cell.setCellStyle(leftAlign);
						colCount++;

					}
					colCount = 0;
					rowCount++;

					///

				}

			}
			row = sheet.createRow(rowCount);
			cell = row.createCell(0);
			cell.setCellValue("Total");
			cell.setCellStyle(leftAlign_left);

			for (int i = 1; i <= 15; i++) {

				cell = row.createCell(i);
				cell.setCellValue("");
				cell.setCellStyle(rightAlignTotal);
			}

			cell = row.createCell(16);
			cell.setCellValue(Location_total.intValue());
			cell.setCellStyle(rightAlignTotal);
			cell = row.createCell(17);
			cell.setCellValue("");
			cell.setCellStyle(rightAlignTotal);

			FileOutputStream fileOutputStream = new FileOutputStream(filepath);
			wb.write(fileOutputStream);
			System.out.println("Excel generated................................");

			filename = usermasterservice.generateExcellock(filepath, filename, empId, ".xlsx");

		} catch (Exception e) {
			throw e;
		} finally {
			wb.close();
		}
		return filename;

	}
	
	@Override
	public String GenerateIAADetailReport_SG(List<IAADetailReport_SG> lstSG, String startDate, String endDate,
			String company, String empId, String gprmInd,ReportBean bean) throws Exception {
		// TODO Auto-generated method stub
		int rowCount = 0, colCount = 0;
		XSSFWorkbook wb = null;
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
		Date temp1 = sdf.parse(startDate);
		Date temp2 = sdf.parse(endDate);

		// String gprmInd = parameterRepository.getGprmIndicatorForPfz();

		String date1 = MedicoResources.convertUtilDateToString(temp1);
		String date2 = MedicoResources.convertUtilDateToString(temp2);

		String filename = "IAADetailReport" + new Date().getTime() + "_" + date1 + "_" + "To" + "_" + date2 + ".xlsx";
		try {
			String filepath = REPORT_FILE_PATH + filename;
			System.out.println("filepath:::" + filepath);

			wb = new XSSFWorkbook();
			File f = new File(REPORT_FILE_PATH);
			if (!f.exists()) {
				if (f.mkdirs()) {
				} else {
					wb.close();
					throw new RuntimeException("Could not create directory");
				}
			}

			XSSFSheet sheet = wb.createSheet("IAADetailReport");
			ApachePoiExcelFormat xlsxExcelFormat = new ApachePoiExcelFormat();
			XSSFRow row = null;
			XSSFCell cell = null;

			CellStyle sheetHeading = xlsxExcelFormat.SheetHeading(wb);
			CellStyle sheetHeadingLeft = xlsxExcelFormat.SheetHeading(wb);
			sheetHeadingLeft.setAlignment(HorizontalAlignment.LEFT);
			CellStyle columnHeading = xlsxExcelFormat.columnHeading(wb);
			CellStyle columnHeadingRight = xlsxExcelFormat.columnHeading(wb);
			columnHeadingRight.setAlignment(HorizontalAlignment.RIGHT);
			CellStyle leftAlign = xlsxExcelFormat.dataLeftAlign(wb);
			CellStyle leftAlign_left = xlsxExcelFormat.dataLeftAlign_total(wb);
			CellStyle rightAlign = xlsxExcelFormat.dataRightAlignWthDecimal(wb);
			CellStyle rightAlignTotal = xlsxExcelFormat.dataRightAlignWthoutDecimal_total(wb);

			String[] heading;

			if (gprmInd.equalsIgnoreCase("Y")) {
				String[] yValues = { "IAA No.", "Warehouse Name", "IAA Date", "Product Type", "Product Code",
						"Product Name", "Pack Size", "Batch No ", "Batch Expiry Date", "Reason", "Stock Type",
						"Stock Type Description", "Remarks", "Out Qty", "In Qty", "Unit Price", "Total Value",
						"Division" };
				heading = new String[yValues.length];
				System.arraycopy(yValues, 0, heading, 0, yValues.length);
			} else {
				String[] otherValues = { "IAA No.", "Warehouse Name", "IAA Date", "Product Type", "Product Code",
						"Product Name", "Pack Size", "Batch No ", "Batch Expiry Date", "Reason", "Stock Type",
						"Stock Type Description", "Remarks", "Out Qty", "In Qty" };
				heading = new String[otherValues.length];
				System.arraycopy(otherValues, 0, heading, 0, otherValues.length);
			}

			row = sheet.createRow(rowCount);
			sheet.addMergedRegion(new CellRangeAddress(rowCount, rowCount, colCount, heading.length - 1));
			cell = row.createCell(colCount);
			cell.setCellValue(company);
			cell.setCellStyle(sheetHeadingLeft);
			rowCount++;

			row = sheet.createRow(rowCount);
			sheet.addMergedRegion(new CellRangeAddress(rowCount, rowCount, colCount, heading.length - 1));
			cell = row.createCell(colCount);
			cell.setCellValue("IAA DETAIL REPORT");
			cell.setCellStyle(sheetHeadingLeft);
			rowCount++;

			row = sheet.createRow(rowCount);
			sheet.addMergedRegion(new CellRangeAddress(rowCount, rowCount, colCount, heading.length - 1));
			cell = row.createCell(colCount);
			// cell.setCellValue("From "+sdf.format(bean.getStartDate())+" To
			// "+sdf.format(bean.getEndDate()));
			cell.setCellValue("From " + startDate + " To " + endDate);
			cell.setCellStyle(sheetHeadingLeft);
			rowCount++;

			colCount = 0;
			row = sheet.createRow(rowCount);

			for (String s : heading) {
				cell = row.createCell(colCount);
				cell.setCellValue(s);
				cell.setCellStyle(columnHeading);
				colCount++;
			}
			rowCount++;
			colCount = 0;

			sheet.createFreezePane(0, 4);

			String prev_location_name = lstSG.get(0).getLoc_nm();

			BigDecimal Location_total = new BigDecimal(0);
			BigDecimal Grand_total = new BigDecimal(0);
			
			LocalDate edDate = bean.getEndDate().toInstant()
                    .atZone(ZoneId.systemDefault())
                    .toLocalDate();

			LocalDate cutoffDate = LocalDate.of(2024, 12, 1);

			if (bean.getCompcd().trim().equalsIgnoreCase(PFZ) && edDate.isBefore(cutoffDate)) {

				lstSG = lstSG.stream().peek(data -> {
					if ("CITIUS".equalsIgnoreCase(data.getTeam_name())
							|| "CITIUS  (ALTIUS - NEW)".equalsIgnoreCase(data.getTeam_name())) {
						data.setTeam_name("ALTIUS - NEW");
					}
				}).collect(Collectors.toList());

			}


			for (IAADetailReport_SG obj : lstSG) {

				if (prev_location_name.equals(obj.getLoc_nm())) {

					Location_total = Location_total.add(obj.getTotal_value());
					row = sheet.createRow(rowCount);

					cell = row.createCell(colCount);
					cell.setCellValue(obj.getStkadj_no() == null ? "" : obj.getStkadj_no());
					cell.setCellStyle(leftAlign);
					colCount++;

					cell = row.createCell(colCount);
					cell.setCellValue(obj.getLoc_nm() == null ? "" : obj.getLoc_nm());
					cell.setCellStyle(leftAlign);
					colCount++;

					cell = row.createCell(colCount);
					cell.setCellValue(obj.getSadj_dt() == null ? "" : obj.getSadj_dt());
					cell.setCellStyle(leftAlign);
					colCount++;

					cell = row.createCell(colCount);
					cell.setCellValue(obj.getSmp_prod_type() == null ? "" : obj.getSmp_prod_type());
					cell.setCellStyle(leftAlign);
					colCount++;
					cell = row.createCell(colCount);
					cell.setCellValue(obj.getSmp_prod_cd() == null ? "" : obj.getSmp_prod_cd());
					cell.setCellStyle(leftAlign);
					colCount++;

					cell = row.createCell(colCount);
					cell.setCellValue(obj.getSmp_prod_name() == null ? "" : obj.getSmp_prod_name());
					cell.setCellStyle(leftAlign);
					colCount++;

					cell = row.createCell(colCount);
					cell.setCellValue(obj.getPacking() == null ? "" : obj.getPacking());
					cell.setCellStyle(leftAlign);
					colCount++;

					cell = row.createCell(colCount);
					cell.setCellValue(obj.getBatch_no() == null ? "" : obj.getBatch_no());
					cell.setCellStyle(leftAlign);
					colCount++;

					cell = row.createCell(colCount);
					cell.setCellValue(obj.getBatch_expiry_dt() == null ? "" : obj.getBatch_expiry_dt());
					cell.setCellStyle(leftAlign);
					colCount++;

					cell = row.createCell(colCount);
					cell.setCellValue(obj.getReason() == null ? "" : obj.getReason());
					cell.setCellStyle(leftAlign);
					colCount++;

					cell = row.createCell(colCount);
					cell.setCellValue(obj.getStock_type() == null ? "" : obj.getStock_type());
					cell.setCellStyle(leftAlign);
					colCount++;

					cell = row.createCell(colCount);
					cell.setCellValue(obj.getStock_type_desc() == null ? "" : obj.getStock_type_desc());
					cell.setCellStyle(leftAlign);
					colCount++;

					cell = row.createCell(colCount);
					cell.setCellValue(obj.getRemarks() == null ? "" : obj.getRemarks());
					cell.setCellStyle(leftAlign);
					colCount++;

					cell = row.createCell(colCount);
					cell.setCellValue(obj.getOut_qty() == null ? (BigDecimal.ZERO).setScale(0).intValue()
							: obj.getOut_qty().setScale(0).intValue());
					cell.setCellStyle(rightAlign);
					colCount++;

					cell = row.createCell(colCount);
					cell.setCellValue(obj.getIn_qty() == null ? (BigDecimal.ZERO).setScale(0).intValue()
							: obj.getIn_qty().setScale(0).intValue());
					cell.setCellStyle(rightAlign);
					colCount++;

					if (gprmInd.equalsIgnoreCase("Y")) {
						/*
						 * if (obj.getIn_qty().toString().equals("0.00")) { cell =
						 * row.createCell(colCount); cell.setCellValue("");
						 * cell.setCellStyle(rightAlign); colCount++;
						 * 
						 * cell = row.createCell(colCount); cell.setCellValue("");
						 * cell.setCellStyle(rightAlign); colCount++; } else {
						 */
						cell = row.createCell(colCount);
						BigDecimal unitPrice = obj.getUnit_price() == null ? BigDecimal.ZERO : obj.getUnit_price();
						cell.setCellValue(unitPrice.doubleValue());
						cell.setCellStyle(rightAlign);
						colCount++;

						cell = row.createCell(colCount);
						BigDecimal totVal = obj.getTotal_value() == null ? BigDecimal.ZERO
								: obj.getTotal_value().setScale(0, RoundingMode.HALF_UP);
						cell.setCellValue(totVal.intValue());
						cell.setCellStyle(rightAlign);
						colCount++;

						cell = row.createCell(colCount);
						cell.setCellValue(obj.getTeam_name() == null ? "" : obj.getTeam_name());
						cell.setCellStyle(leftAlign);
						colCount++;

					}
					colCount = 0;
					rowCount++;

					prev_location_name = obj.getLoc_nm();

				} else {

					row = sheet.createRow(rowCount);
					cell = row.createCell(0);
					cell.setCellValue("Total");
					cell.setCellStyle(leftAlign_left);
					for (int i = 1; i <= 15; i++) {

						cell = row.createCell(i);
						cell.setCellValue("");
						cell.setCellStyle(rightAlignTotal);
					}

					System.err.println("Location_total::::::" + Location_total);
					System.err.println("Location_total::::::" + Location_total.intValue());

					cell = row.createCell(16);
					cell.setCellValue(Location_total.intValue());
					cell.setCellStyle(rightAlignTotal);
					prev_location_name = obj.getLoc_nm();
					Grand_total = Grand_total.add(Location_total);
					Location_total = new BigDecimal(0);

					cell = row.createCell(17);
					cell.setCellValue("");
					cell.setCellStyle(rightAlignTotal);

					rowCount++;

					/////

					Location_total = Location_total.add(obj.getTotal_value());
					row = sheet.createRow(rowCount);

					cell = row.createCell(colCount);
					cell.setCellValue(obj.getStkadj_no() == null ? "" : obj.getStkadj_no());
					cell.setCellStyle(leftAlign);
					colCount++;

					cell = row.createCell(colCount);
					cell.setCellValue(obj.getLoc_nm() == null ? "" : obj.getLoc_nm());
					cell.setCellStyle(leftAlign);
					colCount++;

					cell = row.createCell(colCount);
					cell.setCellValue(obj.getSadj_dt() == null ? "" : obj.getSadj_dt());
					cell.setCellStyle(leftAlign);
					colCount++;

					cell = row.createCell(colCount);
					cell.setCellValue(obj.getSmp_prod_type() == null ? "" : obj.getSmp_prod_type());
					cell.setCellStyle(leftAlign);
					colCount++;
					cell = row.createCell(colCount);
					cell.setCellValue(obj.getSmp_prod_cd() == null ? "" : obj.getSmp_prod_cd());
					cell.setCellStyle(leftAlign);
					colCount++;

					cell = row.createCell(colCount);
					cell.setCellValue(obj.getSmp_prod_name() == null ? "" : obj.getSmp_prod_name());
					cell.setCellStyle(leftAlign);
					colCount++;

					cell = row.createCell(colCount);
					cell.setCellValue(obj.getPacking() == null ? "" : obj.getPacking());
					cell.setCellStyle(leftAlign);
					colCount++;

					cell = row.createCell(colCount);
					cell.setCellValue(obj.getBatch_no() == null ? "" : obj.getBatch_no());
					cell.setCellStyle(leftAlign);
					colCount++;

					cell = row.createCell(colCount);
					cell.setCellValue(obj.getBatch_expiry_dt() == null ? "" : obj.getBatch_expiry_dt());
					cell.setCellStyle(leftAlign);
					colCount++;

					cell = row.createCell(colCount);
					cell.setCellValue(obj.getReason() == null ? "" : obj.getReason());
					cell.setCellStyle(leftAlign);
					colCount++;

					cell = row.createCell(colCount);
					cell.setCellValue(obj.getStock_type() == null ? "" : obj.getStock_type());
					cell.setCellStyle(leftAlign);
					colCount++;

					cell = row.createCell(colCount);
					cell.setCellValue(obj.getStock_type_desc() == null ? "" : obj.getStock_type_desc());
					cell.setCellStyle(leftAlign);
					colCount++;

					cell = row.createCell(colCount);
					cell.setCellValue(obj.getRemarks() == null ? "" : obj.getRemarks());
					cell.setCellStyle(leftAlign);
					colCount++;

					cell = row.createCell(colCount);
					cell.setCellValue(obj.getOut_qty() == null ? (BigDecimal.ZERO).setScale(0).intValue()
							: obj.getOut_qty().setScale(0).intValue());
					cell.setCellStyle(rightAlign);
					colCount++;

					cell = row.createCell(colCount);
					cell.setCellValue(obj.getIn_qty() == null ? (BigDecimal.ZERO).setScale(0).intValue()
							: obj.getIn_qty().setScale(0).intValue());
					cell.setCellStyle(rightAlign);
					colCount++;

					if (gprmInd.equalsIgnoreCase("Y")) {
						/*
						 * if (obj.getIn_qty().toString().equals("0.00")) { cell =
						 * row.createCell(colCount); cell.setCellValue("");
						 * cell.setCellStyle(rightAlign); colCount++;
						 * 
						 * cell = row.createCell(colCount); cell.setCellValue("");
						 * cell.setCellStyle(rightAlign); colCount++; } else {
						 */
						cell = row.createCell(colCount);
						BigDecimal unitPrice = obj.getUnit_price() == null ? BigDecimal.ZERO : obj.getUnit_price();
						cell.setCellValue(unitPrice.doubleValue());
						cell.setCellStyle(rightAlign);
						colCount++;

						cell = row.createCell(colCount);
						BigDecimal totVal = obj.getTotal_value() == null ? BigDecimal.ZERO
								: obj.getTotal_value().setScale(0, RoundingMode.HALF_UP);

						System.err.println("totalvale::::::" + totVal);
						System.err.println("totalvale::::::" + totVal.intValue());
						cell.setCellValue(totVal.intValue());
						cell.setCellStyle(rightAlign);
						colCount++;

						/* } */

						cell = row.createCell(colCount);
						cell.setCellValue(obj.getTeam_name() == null ? "" : obj.getTeam_name());
						cell.setCellStyle(leftAlign);
						colCount++;

					}
					colCount = 0;
					rowCount++;

					///

				}

			}
			row = sheet.createRow(rowCount);
			cell = row.createCell(0);
			cell.setCellValue("Total");
			cell.setCellStyle(leftAlign_left);

			for (int i = 1; i <= 15; i++) {

				cell = row.createCell(i);
				cell.setCellValue("");
				cell.setCellStyle(rightAlignTotal);
			}

			cell = row.createCell(16);
			cell.setCellValue(Location_total.intValue());
			cell.setCellStyle(rightAlignTotal);
			cell = row.createCell(17);
			cell.setCellValue("");
			cell.setCellStyle(rightAlignTotal);

			FileOutputStream fileOutputStream = new FileOutputStream(filepath);
			wb.write(fileOutputStream);
			System.out.println("Excel generated................................");

			filename = usermasterservice.generateExcellock(filepath, filename, empId, ".xlsx");

		} catch (Exception e) {
			throw e;
		} finally {
			wb.close();
		}
		return filename;

	}

	



}
