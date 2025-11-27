package com.excel.service;

import java.io.File;
import java.io.FileOutputStream;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import javax.validation.Valid;

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
import com.excel.model.ViewGRNDetail;
import com.excel.model.ViewGRNDetail_GST;
import com.excel.model.ViewGRNSummary;
import com.excel.model.ViewGRNSummary_GST;
import com.excel.model.ViewGRNSummary_GST_VET;
import com.excel.model.ViewGRNSummary_VET;
import com.excel.repository.UserMasterRepository;
import com.excel.security.PasswordManager;
import com.excel.utility.MedicoConstants;
import com.excel.utility.MedicoResources;
import com.excel.utility.ReportFormats;
import com.excel.utility.Utility;

import net.lingala.zip4j.core.ZipFile;
import net.lingala.zip4j.model.ZipParameters;
import net.lingala.zip4j.util.Zip4jConstants;

@Service
public class GRNReportServiceImpl implements GRNReportService, MedicoConstants {

	@Autowired
	UserMasterRepository usermasterrepository;
	@Autowired
	private UserMasterService usermasterservice;
	@Autowired private ParameterService paramService;

	
	@Override
	public String generateGRNSummaryReport(List<ViewGRNSummary_GST> list, ReportBean bean, String companyname,
			String empId) throws Exception {
		String filename = "GRNSummaryReport" + new Date().getTime() + ".xlsx";
		String filepath = REPORT_FILE_PATH + filename;
		XSSFWorkbook workbook = null;
		try {
			String grnRefNoInd = this.paramService.getGrnRefNoInd();
			workbook = new XSSFWorkbook();
			File f = new File(REPORT_FILE_PATH);
			if (!f.exists()) {
				if (f.mkdirs()) {
				} else {
					throw new RuntimeException("Could not create directory");
				}
			}
			XSSFSheet sheet = workbook.createSheet("GRN Summary Report");

			XSSFRow row = null;
			XSSFCell cell = null;
			int rowCount = 0;
			int colCount = 0;

			List<String> headings = new ArrayList<>();
			headings.add("GRN No.");
			headings.add("GRN Entered By");
			headings.add("Location");
			headings.add("Supplier Code");
			headings.add("Supplier Name / Received from");
			headings.add("GSTIN No.");
			headings.add("State");
			headings.add("State Code");
			headings.add("GRN Date");
			headings.add("Transfer No.");
			headings.add("Transfer Date");
			headings.add("LR No.");
			headings.add("LR Date");
			headings.add("Value (Rs)");
			headings.add("Lorry_No / Transporter Name");
			headings.add("Division Name");
			headings.add("PO No.");
			headings.add("PO Date");
			headings.add("Additional Charges");
			headings.add("Amount");
			headings.add("Discount");
			headings.add("Amount");
			headings.add("SGST Amount");
			headings.add("CGST Amount");
			headings.add("IGST Amount");
			headings.add("RoundOff");
			headings.add("Net Amount");
			headings.add("REMARKS");

			if(grnRefNoInd != null && grnRefNoInd.equalsIgnoreCase("Y")) {
				if(bean.getCompcd().trim().equalsIgnoreCase("PFZ"))
					headings.add("IDOC/IBD No.");
				else
					headings.add("GRN Ref No.");
			}
			
			XSSFCellStyle headingCellStyle = ReportFormats.heading(workbook);
			XSSFCellStyle columnHeadingStyle = ReportFormats.columnHeading(workbook);
			XSSFCellStyle cellAlignment = ReportFormats.setCellAlignment(workbook, ALIGN_RIGHT);

			row = sheet.createRow(rowCount);
			cell = row.createCell(colCount);
			sheet.addMergedRegion(new CellRangeAddress(rowCount, rowCount, colCount, colCount + headings.size() - 1));
			cell.setCellValue("Pfizer India Ltd.");
			cell.setCellStyle(headingCellStyle);
			rowCount++;

			row = sheet.createRow(rowCount);
			cell = row.createCell(colCount);
			sheet.addMergedRegion(new CellRangeAddress(rowCount, rowCount, colCount, colCount + headings.size() - 1));
			cell.setCellValue("GRN Summary Report");
			cell.setCellStyle(headingCellStyle);
			rowCount++;

			row = sheet.createRow(rowCount);
			cell = row.createCell(colCount);
			sheet.addMergedRegion(new CellRangeAddress(rowCount, rowCount, colCount, colCount + headings.size() - 1));
			cell.setCellValue("Period: " + MedicoResources.convertUtilDateToString_DD_MM_YYYY_(bean.getStartDate())
					+ " to " + MedicoResources.convertUtilDateToString_DD_MM_YYYY_(bean.getEndDate()));
			cell.setCellStyle(headingCellStyle);

			rowCount += 1;
			row = sheet.createRow(rowCount);

			for (String heading : headings) {
				cell = ReportFormats.cell(row, colCount, heading, columnHeadingStyle);
				colCount++;
			}

			sheet.groupRow(0, 2);
			sheet.createFreezePane(0, 4);

			rowCount++;
			colCount = 0;

			BigDecimal goodsValueTotal = new BigDecimal(0);
			BigDecimal addChargesTotal = new BigDecimal(0);
			BigDecimal discountTotal = new BigDecimal(0);
			BigDecimal sgstTotal = new BigDecimal(0);
			BigDecimal cgstTotal = new BigDecimal(0);
			BigDecimal igstTotal = new BigDecimal(0);
			BigDecimal rounfOffTotal = new BigDecimal(0);
			BigDecimal finalTotal = new BigDecimal(0);

			BigDecimal goodsValueTotalGrand = new BigDecimal(0);
			BigDecimal addChargesTotalGrand = new BigDecimal(0);
			BigDecimal discountTotalGrand = new BigDecimal(0);
			BigDecimal sgstTotalGrand = new BigDecimal(0);
			BigDecimal cgstTotalGrand = new BigDecimal(0);
			BigDecimal igstTotalGrand = new BigDecimal(0);
			BigDecimal rounfOffTotalGrand = new BigDecimal(0);
			BigDecimal finalTotalGrand = new BigDecimal(0);

			String new_division = "";
			String old_division = "";

			int i = 0;

			for (ViewGRNSummary_GST data : list) {
				BigDecimal netAmount = new BigDecimal(0);
				new_division = data.getDivision_name();

				if (!(new_division.equalsIgnoreCase(old_division))) {
					if (i != 0) {
						row = sheet.createRow(rowCount);
						cell = ReportFormats.cell(row, colCount, "Division Total", null);
						// sheet.addMergedRegion(new CellRangeAddress(rowCount, rowCount, colCount,
						// 11));
						colCount++;

						for (int x = 0; x <= 10; x++) {
							cell = ReportFormats.cell(row, colCount, "", cellAlignment);
							colCount++;
						}

						cell = row.createCell(12);
						cell = ReportFormats.cellNum(row, colCount,
								goodsValueTotal == null ? 0l : goodsValueTotal.doubleValue(), cellAlignment);
						goodsValueTotalGrand = goodsValueTotalGrand.add(goodsValueTotal);
						colCount++;

						for (int x = 0; x <= 4; x++) {
							cell = ReportFormats.cell(row, colCount, "", cellAlignment);
							colCount++;
						}

						cell = row.createCell(18);
						cell = ReportFormats.cellNum(row, colCount,
								addChargesTotal == null ? 0l : addChargesTotal.doubleValue(), cellAlignment);
						addChargesTotalGrand = addChargesTotalGrand.add(addChargesTotal);
						// sheet.addMergedRegion(new CellRangeAddress(rowCount, rowCount, 13, 18));
						colCount++;

						cell = row.createCell(19);
						cell = ReportFormats.cell(row, colCount, "", cellAlignment);
						colCount++;

						cell = row.createCell(20);
						cell = ReportFormats.cellNum(row, colCount,
								discountTotal == null ? 0l : discountTotal.doubleValue(), cellAlignment);
						discountTotalGrand = discountTotalGrand.add(discountTotal);
						// sheet.addMergedRegion(new CellRangeAddress(rowCount, rowCount, 19, 20));
						colCount++;

						cell = row.createCell(21);
						cell = ReportFormats.cellNum(row, colCount, sgstTotal == null ? 0l : sgstTotal.doubleValue(),
								cellAlignment);
						sgstTotalGrand = sgstTotalGrand.add(sgstTotal);
						colCount++;

						cell = row.createCell(22);
						cell = ReportFormats.cellNum(row, colCount, cgstTotal == null ? 0l : cgstTotal.doubleValue(),
								cellAlignment);
						cgstTotalGrand = cgstTotalGrand.add(cgstTotal);
						colCount++;

						cell = row.createCell(23);
						cell = ReportFormats.cellNum(row, colCount, igstTotal == null ? 0l : igstTotal.doubleValue(),
								cellAlignment);
						igstTotalGrand = igstTotalGrand.add(igstTotal);
						colCount++;

						cell = row.createCell(24);
						cell = ReportFormats.cellNum(row, colCount, igstTotal == null ? 0l : igstTotal.doubleValue(),
								cellAlignment);
						rounfOffTotalGrand = rounfOffTotalGrand.add(rounfOffTotal);
						colCount++;

						cell = row.createCell(25);
						cell = ReportFormats.cellNum(row, colCount, finalTotal == null ? 0l : finalTotal.doubleValue(),
								cellAlignment);
						finalTotalGrand = finalTotalGrand.add(finalTotal);
						colCount++;

						goodsValueTotal = new BigDecimal(0);
						addChargesTotal = new BigDecimal(0);
						discountTotal = new BigDecimal(0);
						sgstTotal = new BigDecimal(0);
						cgstTotal = new BigDecimal(0);
						igstTotal = new BigDecimal(0);
						rounfOffTotal = new BigDecimal(0);
						finalTotal = new BigDecimal(0);

						rowCount++;
						colCount = 0;
					}

					row = sheet.createRow(rowCount);
					cell = ReportFormats.cell(row, colCount, "Division : " + new_division, null);
					// sheet.addMergedRegion(new CellRangeAddress(rowCount, rowCount, colCount,
					// 20));

					rowCount++;
					colCount = 0;
				}

				row = sheet.createRow(rowCount);
				/*
				 * cell = ReportFormats.cell(row, colCount, data.getRow().toString(),
				 * cellAlignment); colCount++;
				 */

				cell = ReportFormats.cell(row, colCount, data.getGrn_no(), null);
				colCount++;
				
				cell = ReportFormats.cell(row, colCount, data.getGrn_entered_by(), null);
				colCount++;

				cell = ReportFormats.cell(row, colCount, data.getSending_Location(), null);
				colCount++;

				cell = ReportFormats.cell(row, colCount, data.getSuppliercode(), null);
				colCount++;

				cell = ReportFormats.cell(row, colCount, data.getSupplier(), null);
				colCount++;

				cell = ReportFormats.cell(row, colCount, data.getGst_no(), null);
				colCount++;

				cell = ReportFormats.cell(row, colCount, data.getState_name(), null);
				colCount++;

				cell = ReportFormats.cell(row, colCount, data.getState_code(), null);
				colCount++;

				cell = ReportFormats.cell(row, colCount, data.getGrn_date(), null);
				colCount++;

				cell = ReportFormats.cell(row, colCount, data.getTransfer_no(), null);
				colCount++;

				cell = ReportFormats.cell(row, colCount, data.getTransfer_date(), null);
				colCount++;

				cell = ReportFormats.cell(row, colCount, data.getLr_no(), cellAlignment);
				colCount++;

				cell = ReportFormats.cell(row, colCount, data.getLr_date(), null);
				colCount++;

				cell = ReportFormats.cellNum(row, colCount,
						data.getGrnvalue() == null ? 0l : data.getGrnvalue().doubleValue(), null);
				goodsValueTotal = goodsValueTotal.add(data.getGrnvalue());
				colCount++;

				cell = ReportFormats.cell(row, colCount, data.getTransporter_Name(), null);
				colCount++;

				cell = ReportFormats.cell(row, colCount, data.getDivision_name(), null);
				colCount++;

				cell = ReportFormats.cell(row, colCount, data.getPo_no(), null);
				colCount++;

				cell = ReportFormats.cell(row, colCount, data.getPo_date(), null);
				colCount++;

				cell = ReportFormats.cellNum(row, colCount, Double.valueOf("0.00"), null);
				colCount++;

				cell = ReportFormats.cellNum(row, colCount,
						data.getHvalue1() == null ? 0l : Double.valueOf("0.00"), null);
				addChargesTotal = addChargesTotal.add(data.getHvalue1());
				colCount++;

				cell = ReportFormats.cellNum(row, colCount, Double.valueOf("0.00"), null);
				colCount++;

				cell = ReportFormats.cellNum(row, colCount,
						data.getHvalue2() == null ? 0l : Double.valueOf("0.00"), null);
				discountTotal = discountTotal.add(data.getHvalue2());
				colCount++;

				cell = ReportFormats.cellNum(row, colCount,
						data.getHsgst_bill_amt() == null ? 0l : data.getHsgst_bill_amt().doubleValue(), null);
				sgstTotal = sgstTotal.add(data.getHsgst_bill_amt());
				colCount++;

				cell = ReportFormats.cellNum(row, colCount,
						data.getHcgst_bill_amt() == null ? 0l : data.getHcgst_bill_amt().doubleValue(), null);
				cgstTotal = cgstTotal.add(data.getHcgst_bill_amt());
				colCount++;

				cell = ReportFormats.cellNum(row, colCount,
						data.getHigst_bill_amt() == null ? 0l : data.getHigst_bill_amt().doubleValue(), null);
				igstTotal = igstTotal.add(data.getHigst_bill_amt());
				colCount++;

				cell = ReportFormats.cellNum(row, colCount,
						data.getHroundoff() == null ? 0l : data.getHroundoff().doubleValue(), null);
				rounfOffTotal = rounfOffTotal.add(data.getHroundoff());
				colCount++;

				netAmount = data.getGrnvalue().add(data.getHvalue1().add(data.getHvalue2().add(data.getHsgst_bill_amt()
						.add(data.getHcgst_bill_amt().add(data.getHigst_bill_amt().add(data.getHroundoff()))))));
				finalTotal = finalTotal.add(netAmount);

				cell = ReportFormats.cellNum(row, colCount, netAmount == null ? 0l : netAmount.doubleValue(), null);
				colCount++;
				
				cell = ReportFormats.cell(row, colCount, data.getRemarks(), null);
				colCount++;
				
				if(grnRefNoInd != null && grnRefNoInd.equalsIgnoreCase("Y")) {
					cell = ReportFormats.cell(row, colCount, data.getGrn_ref_no(), null);
				}

				colCount = 0;
				rowCount++;

				if (i == list.size() - 1) {

					row = sheet.createRow(rowCount);
					cell = ReportFormats.cell(row, colCount, "Division Total", null);
					colCount++;

					for (int x = 0; x <= 10; x++) {
						cell = ReportFormats.cell(row, colCount, "", cellAlignment);
						colCount++;
					}

					cell = row.createCell(12);
					cell = ReportFormats.cellNum(row, colCount,
							goodsValueTotal == null ? 0l : goodsValueTotal.doubleValue(), cellAlignment);
					goodsValueTotalGrand = goodsValueTotalGrand.add(goodsValueTotal);
					colCount++;

					for (int x = 0; x <= 4; x++) {
						cell = ReportFormats.cell(row, colCount, "", cellAlignment);
						colCount++;
					}

					cell = row.createCell(18);
					cell = ReportFormats.cellNum(row, colCount,
							addChargesTotal == null ? 0l : addChargesTotal.doubleValue(), cellAlignment);
					addChargesTotalGrand = addChargesTotalGrand.add(addChargesTotal);
					// sheet.addMergedRegion(new CellRangeAddress(rowCount,rowCount, 13, 18));
					colCount++;

					cell = row.createCell(19);
					cell = ReportFormats.cell(row, colCount, "", cellAlignment);
					colCount++;

					cell = row.createCell(20);
					cell = ReportFormats.cellNum(row, colCount,
							discountTotal == null ? 0l : discountTotal.doubleValue(), cellAlignment);
					discountTotalGrand = discountTotalGrand.add(discountTotal);
					// sheet.addMergedRegion(new CellRangeAddress(rowCount, rowCount, 19, 20));
					colCount++;

					cell = row.createCell(21);
					cell = ReportFormats.cellNum(row, colCount, sgstTotal == null ? 0l : sgstTotal.doubleValue(),
							cellAlignment);
					sgstTotalGrand = sgstTotalGrand.add(sgstTotal);
					colCount++;

					cell = row.createCell(22);
					cell = ReportFormats.cellNum(row, colCount, cgstTotal == null ? 0l : cgstTotal.doubleValue(),
							cellAlignment);
					cgstTotalGrand = cgstTotalGrand.add(cgstTotal);
					colCount++;

					cell = row.createCell(23);
					cell = ReportFormats.cellNum(row, colCount, igstTotal == null ? 0l : igstTotal.doubleValue(),
							cellAlignment);
					igstTotalGrand = igstTotalGrand.add(igstTotal);
					colCount++;

					cell = row.createCell(24);
					cell = ReportFormats.cellNum(row, colCount,
							rounfOffTotal == null ? 0l : rounfOffTotal.doubleValue(), cellAlignment);
					rounfOffTotalGrand = rounfOffTotalGrand.add(rounfOffTotal);
					colCount++;

					cell = row.createCell(25);
					cell = ReportFormats.cellNum(row, colCount, finalTotal == null ? 0l : finalTotal.doubleValue(),
							cellAlignment);
					finalTotalGrand = finalTotalGrand.add(finalTotal);

					goodsValueTotal = new BigDecimal(0);
					addChargesTotal = new BigDecimal(0);
					discountTotal = new BigDecimal(0);
					sgstTotal = new BigDecimal(0);
					cgstTotal = new BigDecimal(0);
					igstTotal = new BigDecimal(0);
					rounfOffTotal = new BigDecimal(0);
					finalTotal = new BigDecimal(0);

					rowCount++;
					colCount = 0;

					row = sheet.createRow(rowCount);

					cell = ReportFormats.cell(row, colCount, "Grand Total", null);
					// sheet.addMergedRegion(new CellRangeAddress(rowCount, rowCount, colCount,
					// 11));
					colCount++;

					for (int x = 0; x <= 10; x++) {
						cell = ReportFormats.cell(row, colCount, "", cellAlignment);
						colCount++;
					}

					cell = row.createCell(12);
					cell = ReportFormats.cellNum(row, colCount,
							goodsValueTotalGrand == null ? 0l : goodsValueTotalGrand.doubleValue(), cellAlignment);
					colCount++;

					for (int x = 0; x <= 4; x++) {
						cell = ReportFormats.cell(row, colCount, "", cellAlignment);
						colCount++;
					}

					cell = row.createCell(18);
					cell = ReportFormats.cellNum(row, colCount,
							addChargesTotalGrand == null ? 0l : addChargesTotalGrand.doubleValue(), cellAlignment);
					// sheet.addMergedRegion(new CellRangeAddress(rowCount, rowCount, 13, 18));
					colCount++;

					cell = row.createCell(19);
					cell = ReportFormats.cell(row, colCount, "", cellAlignment);
					colCount++;

					cell = row.createCell(20);
					cell = ReportFormats.cellNum(row, colCount,
							discountTotalGrand == null ? 0l : discountTotalGrand.doubleValue(), cellAlignment);
					// sheet.addMergedRegion(new CellRangeAddress(rowCount, rowCount, 19, 20));
					colCount++;

					cell = row.createCell(21);
					cell = ReportFormats.cellNum(row, colCount,
							sgstTotalGrand == null ? 0l : sgstTotalGrand.doubleValue(), cellAlignment);
					colCount++;
					// cell = ReportFormats.cell(row, colCount, sgstTotalGrand.toString(),
					// cellAlignment);

					cell = row.createCell(22);
					cell = ReportFormats.cellNum(row, colCount,
							cgstTotalGrand == null ? 0l : cgstTotalGrand.doubleValue(), cellAlignment);
					colCount++;

					cell = row.createCell(23);
					cell = ReportFormats.cellNum(row, colCount,
							igstTotalGrand == null ? 0l : igstTotalGrand.doubleValue(), cellAlignment);
					colCount++;

					cell = row.createCell(24);
					cell = ReportFormats.cellNum(row, colCount,
							rounfOffTotalGrand == null ? 0l : rounfOffTotalGrand.doubleValue(), cellAlignment);
					colCount++;

					cell = row.createCell(25);
					cell = ReportFormats.cellNum(row, colCount,
							finalTotalGrand == null ? 0l : finalTotalGrand.doubleValue(), cellAlignment);
					colCount++;
				}
				i++;
				old_division = new_division;
			}
			FileOutputStream fileOutputStream = new FileOutputStream(filepath);
			workbook.write(fileOutputStream);

			filename = usermasterservice.generateExcellock(filepath, filename, empId, ".xlsx");

		} finally {
			if (workbook != null) {
				workbook.close();
			}
		}
		return filename;
	}

//	@Override
//	public String generateGRNDetailReport(List<ViewGRNDetail_GST> list, ReportBean bean, String companyname,
//			String empId, boolean isCfaToStockist) throws Exception {
//		String filename = "GRNDetailReport" + new Date().getTime() + ".xlsx";
//		String filepath = REPORT_FILE_PATH + filename;
//		XSSFWorkbook workbook = null;
//		try {
//			workbook = new XSSFWorkbook();
//			File f = new File(REPORT_FILE_PATH);
//			if (!f.exists()) {
//				if (f.mkdirs()) {
//				} else {
//					throw new RuntimeException("Could not create directory");
//				}
//			}
//			XSSFSheet sheet = workbook.createSheet("GRN Detail Report");
//
//			XSSFRow row = null;
//			XSSFCell cell = null;
//			int rowCount = 0;
//			int colCount = 0;
//			String headings[] = null;
//			
//			if (isCfaToStockist) {
//				headings = new String[] { "GRN Date.", "GRN No.", "GRN Type", "Name", "GSTIN", "State", "State Code",
//						"Place of Supply", "Supplier Type", "Supply Doc NO", "Supply Doc Date", "LR No", "LR Date",
//						"Product", "Pack", "HSN Code", "Batch No", "No. of Cases", "Rate", "Saleable", "Short Qty",
//						"Spoilt", "Total Grn Qty", "Goods Value", "CGST %", "CGST Value", "SGST %", "SGST Value", "IGST %",
//						"IGST Value", "Additional Charge", "Amount", "Discount", "Amount", "Total Value", "FCODE",
//						"GCMA Indicator", "GCMA CODE", "GCMA EXP DATE", "REMARK", "Is Returned ?",
//						"Stock condition" };
//			} else {
//				headings = new String[] { "GRN Date.", "GRN No.", "GRN Type", "Name", "GSTIN", "State", "State Code",
//						"Place of Supply", "Supplier Type", "Supply Doc NO", "Supply Doc Date", "LR No", "LR Date",
//						"Product", "Pack", "HSN Code", "Batch No", "No. of Cases", "Rate", "Saleable", "Short Qty",
//						"Spoilt", "Total Grn Qty", "Goods Value", "CGST %", "CGST Value", "SGST %", "SGST Value", "IGST %",
//						"REMARK", "Is Returned ?", "Stock condition" };
//			}
//
//			XSSFCellStyle headingCellStyle = ReportFormats.heading_left(workbook);
//			XSSFCellStyle columnHeadingStyle = ReportFormats.columnHeading(workbook);
//			XSSFCellStyle cellAlignment = ReportFormats.setCellAlignment(workbook, ALIGN_RIGHT);
//			XSSFCellStyle cellAlignment2 = ReportFormats.setCellAlignment(workbook, ALIGN_LEFT);
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
//			cell.setCellValue("GRN Detail Report");
//			cell.setCellStyle(headingCellStyle);
//			rowCount++;
//
//			row = sheet.createRow(rowCount);
//			cell = row.createCell(colCount);
//			sheet.addMergedRegion(new CellRangeAddress(rowCount, rowCount, colCount, colCount + headings.length - 1));
//			cell.setCellValue("Period: " + MedicoResources.convertUtilDateToString_DD_MM_YYYY_(bean.getStartDate())
//					+ " to " + MedicoResources.convertUtilDateToString_DD_MM_YYYY_(bean.getEndDate()));
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
//			sheet.createFreezePane(0, 4);
//
//			String newDivision = "";
//			String oldDivision = "";
//			// String newGrnDate = "";
//			// String oldGrnDate = "";
//			// String newGrnNo = "";
//			// String oldGrnNo = "";
//
//			BigDecimal totalValue = new BigDecimal(0);
//			BigDecimal divisionTotal[] = new BigDecimal[7];
//			BigDecimal reportTotal[] = new BigDecimal[7];
//
//			// Arrays.fill(totalValue, BigDecimal.ZERO);
//			Arrays.fill(divisionTotal, BigDecimal.ZERO);
//			Arrays.fill(reportTotal, BigDecimal.ZERO);
//
//			for (ViewGRNDetail_GST o : list) {
//
//				colCount = 0;
//				rowCount++;
//
//				newDivision = o.getDivision_name();
//				if (!(newDivision.equalsIgnoreCase(oldDivision)) && !oldDivision.equalsIgnoreCase("")) {
//
//					row = sheet.createRow(rowCount);
//
//					cell = ReportFormats.cell(row, colCount, "Division Total".toString(), null);
//					colCount++;
//
//					for (int i = 0; i <= 21; i++) {
//						cell = ReportFormats.cell(row, colCount, "", null);
//						colCount++;
//					}
//
//					cell = row.createCell(23);
//					cell = ReportFormats.cellNum(row, colCount,
//							divisionTotal[0] == null ? 0l : divisionTotal[0].doubleValue(), cellAlignment);
//					colCount++;
//
//					cell = row.createCell(24);
//					cell = ReportFormats.cell(row, colCount, "", cellAlignment);
//					colCount++;
//
//					cell = row.createCell(25);
//					cell = ReportFormats.cellNum(row, colCount,
//							divisionTotal[1] == null ? 0l : divisionTotal[1].doubleValue(), cellAlignment);
//					colCount++;
//
//					cell = row.createCell(26);
//					cell = ReportFormats.cell(row, colCount, "", cellAlignment);
//					colCount++;
//
//					cell = row.createCell(27);
//					cell = ReportFormats.cellNum(row, colCount,
//							divisionTotal[2] == null ? 0l : divisionTotal[2].doubleValue(), cellAlignment);
//					colCount++;
//
//					cell = row.createCell(28);
//					cell = ReportFormats.cell(row, colCount, "", cellAlignment);
//					colCount++;
//
//					cell = row.createCell(29);
//					cell = ReportFormats.cellNum(row, colCount,
//							divisionTotal[3] == null ? 0l : divisionTotal[3].doubleValue(), cellAlignment);
//					colCount++;
//
//					cell = row.createCell(30);
//					cell = ReportFormats.cell(row, colCount, "", cellAlignment);
//					colCount++;
//
//					cell = row.createCell(31);
//					cell = ReportFormats.cellNum(row, colCount,
//							divisionTotal[4] == null ? 0l : divisionTotal[4].doubleValue(), cellAlignment);
//					colCount++;
//
//					cell = row.createCell(32);
//					cell = ReportFormats.cell(row, colCount, "", cellAlignment);
//					colCount++;
//
//					cell = row.createCell(33);
//					cell = ReportFormats.cellNum(row, colCount,
//							divisionTotal[5] == null ? 0l : divisionTotal[5].doubleValue(), cellAlignment);
//					colCount++;
//
//					cell = row.createCell(34);
//					cell = ReportFormats.cellNum(row, colCount,
//							divisionTotal[6] == null ? 0l : divisionTotal[6].doubleValue(), cellAlignment);
//					colCount++;
//
//					Arrays.fill(divisionTotal, BigDecimal.ZERO);
//					colCount = 0;
//					rowCount++;
//				}
//
//				if (!(newDivision.equalsIgnoreCase(oldDivision))) {
//					colCount = 0;
//					row = sheet.createRow(rowCount);
//					cell = ReportFormats.cell(row, colCount, "Division : " + o.getDivision_name(), null);
//					colCount = 0;
//					rowCount++;
//
//				}
//
//				row = sheet.createRow(rowCount);
//				cell = ReportFormats.cell(row, colCount, o.getGrn_date(), null);
//				colCount++;
//
//				cell = ReportFormats.cell(row, colCount, o.getGrn_no(), null);
//				colCount++;
//
//				cell = ReportFormats.cell(row, colCount, o.getGrn_type(), null);
//				colCount++;
//
//				cell = ReportFormats.cell(row, colCount, o.getSupplier(), null);
//				colCount++;
//
//				cell = ReportFormats.cell(row, colCount, o.getHgst_reg_no(), null);
//				colCount++;
//
//				cell = ReportFormats.cell(row, colCount, o.getHstate_name(), null);
//				colCount++;
//
//				cell = ReportFormats.cell(row, colCount, o.getHstate_code(), null);
//				colCount++;
//
//				cell = ReportFormats.cell(row, colCount, o.getSending_Location(), null);
//				colCount++;
//
//				cell = ReportFormats.cell(row, colCount, o.getSupply_doc_type(), null);
//				colCount++;
//
//				cell = ReportFormats.cell(row, colCount, o.getTransfer_no(), null);
//				colCount++;
//
//				cell = ReportFormats.cell(row, colCount, o.getTransfer_date(), null);
//				colCount++;
//
//				cell = ReportFormats.cell(row, colCount, o.getLr_no(), null);
//				colCount++;
//
//				cell = ReportFormats.cell(row, colCount, o.getLr_date(), null);
//				colCount++;
//
//				cell = ReportFormats.cell(row, colCount, o.getProduct_name(), null);
//				colCount++;
//
//				cell = ReportFormats.cell(row, colCount, o.getPacking(), null);
//				colCount++;
//
//				cell = ReportFormats.cell(row, colCount, o.getHsncode(), null);
//				colCount++;
//
//				cell = ReportFormats.cell(row, colCount, o.getBatch_no(), null);
//				colCount++;
//
//				cell = ReportFormats.cell(row, colCount, o.getBin_number(), cellAlignment); // cess
//				colCount++;
//
//				cell = ReportFormats.cellNum(row, colCount,
//						o.getGrn_rate() == null ? 0l : o.getGrn_rate().doubleValue(), cellAlignment);
//				colCount++;
//				//
//				cell = ReportFormats.cellNum(row, colCount, o.getGrn_qty() == null ? 0l : o.getGrn_qty().doubleValue(),
//						cellAlignment);
//				colCount++;
//
//				cell = ReportFormats.cellNum(row, colCount,
//						o.getGrn_qty() == null ? 0l : o.getShort_qty().doubleValue(), cellAlignment);
//				colCount++;
//
//				cell = ReportFormats.cellNum(row, colCount,
//						o.getGrn_qty() == null ? 0l : o.getDamage_qty().doubleValue(), cellAlignment);
//				colCount++;
//
//				cell = ReportFormats.cellNum(row, colCount,
//						o.getGrn_qty() == null ? 0l : o.getTotal_grn_qty().doubleValue(), cellAlignment);
//				colCount++;
//
//				cell = ReportFormats.cellNum(row, colCount, o.getValue() == null ? 0l : o.getValue().doubleValue(),
//						cellAlignment);
//				colCount++;
//
//				cell = ReportFormats.cellNum(row, colCount,
//						o.getCgst_rate() == null ? 0l : o.getCgst_rate().doubleValue(), cellAlignment);
//				colCount++;
//
//				cell = ReportFormats.cellNum(row, colCount,
//						o.getCgst_bill_amt() == null ? 0l : o.getCgst_bill_amt().doubleValue(), cellAlignment);
//				colCount++;
//
//				cell = ReportFormats.cellNum(row, colCount,
//						o.getSgst_rate() == null ? 0l : o.getSgst_rate().doubleValue(), cellAlignment);
//				colCount++;
//
//				cell = ReportFormats.cellNum(row, colCount,
//						o.getSgst_bill_amt() == null ? 0l : o.getSgst_bill_amt().doubleValue(), cellAlignment);
//				colCount++;
//
//				cell = ReportFormats.cellNum(row, colCount,
//						o.getIgst_rate() == null ? 0l : o.getIgst_rate().doubleValue(), cellAlignment);
//				colCount++;
//
//				cell = ReportFormats.cellNum(row, colCount,
//						o.getIgst_bill_amt() == null ? 0l : o.getIgst_bill_amt().doubleValue(), cellAlignment);
//				colCount++;
//
//				cell = ReportFormats.cellNum(row, colCount, Double.valueOf("0.00"), null);
//				colCount++;
//
//				cell = ReportFormats.cellNum(row, colCount, o.getValue1() == null ? 0l : Double.valueOf("0.00"),
//						cellAlignment);
//				colCount++;
//
//				cell = ReportFormats.cellNum(row, colCount, Double.valueOf("0.00"), null);
//				colCount++;
//
//				cell = ReportFormats.cellNum(row, colCount, o.getValue2() == null ? 0l : o.getValue2().doubleValue(),
//						cellAlignment);
//				colCount++;
//
//				totalValue = totalValue.add(o.getValue().add(o.getCgst_bill_amt()
//						.add(o.getSgst_bill_amt().add(o.getIgst_bill_amt().add(o.getValue1().add(o.getValue2()))))));
//
//				cell = ReportFormats.cellNum(row, colCount, totalValue == null ? 0l : totalValue.doubleValue(), null);
//				colCount++;
//
//				cell = ReportFormats.cell(row, colCount, o.getFcode(), null);
//				colCount++;
//
//				if (!isCfaToStockist) {
//					cell = ReportFormats.cell(row, colCount, o.getGcma_req_ind(), null);
//					colCount++;
//
//					cell = ReportFormats.cell(row, colCount, o.getGcma_number(), null);
//					colCount++;
//
//					cell = ReportFormats.cell(row, colCount,
//							o.getGcma_expiry_dt() == null ? "" : Utility.convertStringtoStringDate(o.getGcma_expiry_dt()),
//							null);
//					colCount++;
//				}
//
//				cell = ReportFormats.cell(row, colCount, o.getRemark(), null);
//				colCount++;
//
//				cell = ReportFormats.cell(row, colCount, o.getReturn_from_fstaff_name(), null);
//				colCount++;
//
//				cell = ReportFormats.cell(row, colCount, o.getSalable_nonsaleable(), null);
//				colCount++;
//
//				oldDivision = newDivision;
//
//				divisionTotal[0] = divisionTotal[0].add(o.getValue() == null ? BigDecimal.ZERO : o.getValue());
//				divisionTotal[1] = divisionTotal[1]
//						.add(o.getCgst_bill_amt() == null ? BigDecimal.ZERO : o.getCgst_bill_amt());
//				divisionTotal[2] = divisionTotal[2]
//						.add(o.getSgst_bill_amt() == null ? BigDecimal.ZERO : o.getSgst_bill_amt());
//				divisionTotal[3] = divisionTotal[3]
//						.add(o.getIgst_bill_amt() == null ? BigDecimal.ZERO : o.getIgst_bill_amt());
//				divisionTotal[4] = divisionTotal[4].add(o.getValue1() == null ? BigDecimal.ZERO : o.getValue1());
//				divisionTotal[5] = divisionTotal[5].add(o.getValue2() == null ? BigDecimal.ZERO : o.getValue2());
//				divisionTotal[6] = divisionTotal[6].add(totalValue == null ? BigDecimal.ZERO : totalValue);
//
//				reportTotal[0] = reportTotal[0].add(o.getValue() == null ? BigDecimal.ZERO : o.getValue());
//				reportTotal[1] = reportTotal[1]
//						.add(o.getCgst_bill_amt() == null ? BigDecimal.ZERO : o.getCgst_bill_amt());
//				reportTotal[2] = reportTotal[2]
//						.add(o.getSgst_bill_amt() == null ? BigDecimal.ZERO : o.getSgst_bill_amt());
//				reportTotal[3] = reportTotal[3]
//						.add(o.getIgst_bill_amt() == null ? BigDecimal.ZERO : o.getIgst_bill_amt());
//				reportTotal[4] = reportTotal[4].add(o.getValue1() == null ? BigDecimal.ZERO : o.getValue1());
//				reportTotal[5] = reportTotal[5].add(o.getValue2() == null ? BigDecimal.ZERO : o.getValue2());
//				reportTotal[6] = reportTotal[6].add(totalValue == null ? BigDecimal.ZERO : totalValue);
//
//				totalValue = new BigDecimal(0);
//			}
//
//			rowCount++;
//			colCount = 0;
//
//			row = sheet.createRow(rowCount);
//
//			cell = ReportFormats.cell(row, colCount, "Division Total", null);
//			colCount++;
//
//			for (int i = 0; i <= 21; i++) {
//				cell = ReportFormats.cell(row, colCount, "", null);
//				colCount++;
//			}
//
//			cell = row.createCell(23);
//			cell = ReportFormats.cellNum(row, colCount, divisionTotal[0] == null ? 0l : divisionTotal[0].doubleValue(),
//					cellAlignment);
//			colCount++;
//
//			cell = row.createCell(24);
//			cell = ReportFormats.cell(row, colCount, "", cellAlignment);
//			colCount++;
//
//			cell = row.createCell(25);
//			cell = ReportFormats.cellNum(row, colCount, divisionTotal[1] == null ? 0l : divisionTotal[1].doubleValue(),
//					cellAlignment);
//			colCount++;
//
//			cell = row.createCell(26);
//			cell = ReportFormats.cell(row, colCount, "", cellAlignment);
//			colCount++;
//
//			cell = row.createCell(27);
//			cell = ReportFormats.cellNum(row, colCount, divisionTotal[2] == null ? 0l : divisionTotal[2].doubleValue(),
//					cellAlignment);
//			colCount++;
//
//			cell = row.createCell(28);
//			cell = ReportFormats.cell(row, colCount, "", cellAlignment);
//			colCount++;
//
//			cell = row.createCell(29);
//			cell = ReportFormats.cellNum(row, colCount, divisionTotal[3] == null ? 0l : divisionTotal[3].doubleValue(),
//					cellAlignment);
//			colCount++;
//
//			cell = row.createCell(30);
//			cell = ReportFormats.cell(row, colCount, "", cellAlignment);
//			colCount++;
//
//			cell = row.createCell(31);
//			cell = ReportFormats.cellNum(row, colCount, divisionTotal[4] == null ? 0l : divisionTotal[4].doubleValue(),
//					cellAlignment);
//			colCount++;
//
//			cell = row.createCell(32);
//			cell = ReportFormats.cell(row, colCount, "", cellAlignment);
//			colCount++;
//
//			cell = row.createCell(33);
//			cell = ReportFormats.cellNum(row, colCount, divisionTotal[5] == null ? 0l : divisionTotal[5].doubleValue(),
//					cellAlignment);
//			colCount++;
//
//			cell = row.createCell(34);
//			cell = ReportFormats.cellNum(row, colCount, divisionTotal[6] == null ? 0l : divisionTotal[6].doubleValue(),
//					cellAlignment);
//			colCount++;
//
//			rowCount++;
//			colCount = 0;
//
//			row = sheet.createRow(rowCount);
//
//			cell = ReportFormats.cell(row, colCount, "Report Total", null);
//			colCount++;
//
//			for (int i = 0; i <= 21; i++) {
//				cell = ReportFormats.cell(row, colCount, "", null);
//				colCount++;
//			}
//
//			cell = row.createCell(23);
//			cell = ReportFormats.cellNum(row, colCount, reportTotal[0] == null ? 0l : reportTotal[0].doubleValue(),
//					cellAlignment);
//			colCount++;
//
//			cell = row.createCell(24);
//			cell = ReportFormats.cell(row, colCount, "", cellAlignment);
//			colCount++;
//
//			cell = row.createCell(25);
//			cell = ReportFormats.cellNum(row, colCount, reportTotal[1] == null ? 0l : reportTotal[1].doubleValue(),
//					cellAlignment);
//			colCount++;
//
//			cell = row.createCell(26);
//			cell = ReportFormats.cell(row, colCount, "", cellAlignment);
//			colCount++;
//
//			cell = row.createCell(27);
//			cell = ReportFormats.cellNum(row, colCount, reportTotal[2] == null ? 0l : reportTotal[2].doubleValue(),
//					cellAlignment);
//			colCount++;
//
//			cell = row.createCell(28);
//			cell = ReportFormats.cell(row, colCount, "", cellAlignment);
//			colCount++;
//
//			cell = row.createCell(29);// igst
//			cell = ReportFormats.cellNum(row, colCount, reportTotal[3] == null ? 0l : reportTotal[3].doubleValue(),
//					cellAlignment);
//			colCount++;
//
//			cell = row.createCell(30);
//			cell = ReportFormats.cell(row, colCount, "", cellAlignment);
//			colCount++;
//
//			cell = row.createCell(31);
//			cell = ReportFormats.cellNum(row, colCount, reportTotal[4] == null ? 0l : reportTotal[4].doubleValue(),
//					cellAlignment);
//			colCount++;
//
//			cell = row.createCell(32);
//			cell = ReportFormats.cell(row, colCount, "", cellAlignment);
//			colCount++;
//
//			cell = row.createCell(33);
//			cell = ReportFormats.cellNum(row, colCount, reportTotal[5] == null ? 0l : reportTotal[5].doubleValue(),
//					cellAlignment);
//			colCount++;
//
//			/*
//			 * cell = row.createCell(34); cell = ReportFormats.cell(row, colCount, "",
//			 * cellAlignment); colCount++;
//			 */
//
//			cell = row.createCell(34);
//			cell = ReportFormats.cellNum(row, colCount, reportTotal[6] == null ? 0l : reportTotal[6].doubleValue(),
//					cellAlignment);
//			colCount++;
//
//			FileOutputStream fileOutputStream = new FileOutputStream(filepath);
//			workbook.write(fileOutputStream);
//			filename = usermasterservice.generateExcellock(filepath, filename, empId, ".xlsx");
//
//		} finally {
//			if (workbook != null) {
//				workbook.close();
//			}
//		}
//		return filename;
//
//	}
	
	@Override
	public String generateGRNDetailReport(List<ViewGRNDetail_GST> list, ReportBean bean, String companyname,
			String empId, boolean isCfaToStockist) throws Exception {
		String filename = "GRNDetailReport" + new Date().getTime() + ".xlsx";
		String filepath = REPORT_FILE_PATH + filename;
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
			XSSFSheet sheet = workbook.createSheet("GRN Detail Report");

			XSSFRow row = null;
			XSSFCell cell = null;
			int rowCount = 0;
			int colCount = 0;
			String headings[] = null;
			
				headings = new String[] { "GRN Date.", "GRN No.", "GRN Type", 
						"PO Number","PO Date",
						"Vendor Name", "GSTIN", "State", "State Code",
						"CFA Location", "Supplier Type", "Supply Doc NO", "Supply Doc Date", "LR No", "LR Date",
						 "Article CODE","Product Name", "Pack", "HSN Code", "Batch No", "No. of Cases", "Rate", "Saleable", "Short Qty",
						"Damaged", "Total Grn Qty", "Goods Value", "CGST %", "CGST Value", "SGST %", "SGST Value", "IGST %",
						"IGST Value", "Additional Charge", "Amount", "Discount", "Amount", "Total Value",  "REMARK",
						"Stock condition" };
				
			

			XSSFCellStyle headingCellStyle = ReportFormats.heading_left(workbook);
			XSSFCellStyle columnHeadingStyle = ReportFormats.columnHeading(workbook);
			XSSFCellStyle cellAlignment = ReportFormats.setCellAlignment(workbook, ALIGN_RIGHT);
			XSSFCellStyle cellAlignment2 = ReportFormats.setCellAlignment(workbook, ALIGN_LEFT);
			row = sheet.createRow(rowCount);
			cell = row.createCell(colCount);
			sheet.addMergedRegion(new CellRangeAddress(rowCount, rowCount, colCount, colCount + headings.length - 1));
			cell.setCellValue(companyname);
			cell.setCellStyle(headingCellStyle);
			rowCount++;

			row = sheet.createRow(rowCount);
			cell = row.createCell(colCount);
			sheet.addMergedRegion(new CellRangeAddress(rowCount, rowCount, colCount, colCount + headings.length - 1));
			cell.setCellValue("GRN Detail Report");
			cell.setCellStyle(headingCellStyle);
			rowCount++;

			row = sheet.createRow(rowCount);
			cell = row.createCell(colCount);
			sheet.addMergedRegion(new CellRangeAddress(rowCount, rowCount, colCount, colCount + headings.length - 1));
			cell.setCellValue("Period: " + MedicoResources.convertUtilDateToString_DD_MM_YYYY_(bean.getStartDate())
					+ " to " + MedicoResources.convertUtilDateToString_DD_MM_YYYY_(bean.getEndDate()));
			cell.setCellStyle(headingCellStyle);

			rowCount += 1;
			row = sheet.createRow(rowCount);

			for (String heading : headings) {
				cell = ReportFormats.cell(row, colCount, heading, columnHeadingStyle);
				colCount++;
			}

			sheet.groupRow(0, 2);
			sheet.createFreezePane(0, 4);

			String newDivision = "";
			String oldDivision = "";
			// String newGrnDate = "";
			// String oldGrnDate = "";
			// String newGrnNo = "";
			// String oldGrnNo = "";

			BigDecimal totalValue = new BigDecimal(0);
			BigDecimal divisionTotal[] = new BigDecimal[7];
			BigDecimal reportTotal[] = new BigDecimal[7];

			// Arrays.fill(totalValue, BigDecimal.ZERO);
			Arrays.fill(divisionTotal, BigDecimal.ZERO);
			Arrays.fill(reportTotal, BigDecimal.ZERO);

			for (ViewGRNDetail_GST o : list) {

				colCount = 0;
				rowCount++;

				newDivision = o.getDivision_name();
				if (!(newDivision.equalsIgnoreCase(oldDivision)) && !oldDivision.equalsIgnoreCase("")) {

					row = sheet.createRow(rowCount);

					cell = ReportFormats.cell(row, colCount, "Division Total".toString(), null);
					colCount++;

					for (int i = 0; i <= 21; i++) {
						cell = ReportFormats.cell(row, colCount, "", null);
						colCount++;
					}

					cell = row.createCell(23);
					cell = ReportFormats.cellNum(row, colCount,
							divisionTotal[0] == null ? 0l : divisionTotal[0].doubleValue(), cellAlignment);
					colCount++;

					cell = row.createCell(24);
					cell = ReportFormats.cell(row, colCount, "", cellAlignment);
					colCount++;

					cell = row.createCell(25);
					cell = ReportFormats.cellNum(row, colCount,
							divisionTotal[1] == null ? 0l : divisionTotal[1].doubleValue(), cellAlignment);
					colCount++;

					cell = row.createCell(26);
					cell = ReportFormats.cell(row, colCount, "", cellAlignment);
					colCount++;

					cell = row.createCell(27);
					cell = ReportFormats.cellNum(row, colCount,
							divisionTotal[2] == null ? 0l : divisionTotal[2].doubleValue(), cellAlignment);
					colCount++;

					cell = row.createCell(28);
					cell = ReportFormats.cell(row, colCount, "", cellAlignment);
					colCount++;

					cell = row.createCell(29);
					cell = ReportFormats.cellNum(row, colCount,
							divisionTotal[3] == null ? 0l : divisionTotal[3].doubleValue(), cellAlignment);
					colCount++;

					cell = row.createCell(30);
					cell = ReportFormats.cell(row, colCount, "", cellAlignment);
					colCount++;

					cell = row.createCell(31);
					cell = ReportFormats.cellNum(row, colCount,
							divisionTotal[4] == null ? 0l : divisionTotal[4].doubleValue(), cellAlignment);
					colCount++;

					cell = row.createCell(32);
					cell = ReportFormats.cell(row, colCount, "", cellAlignment);
					colCount++;

					cell = row.createCell(33);
					cell = ReportFormats.cellNum(row, colCount,
							divisionTotal[5] == null ? 0l : divisionTotal[5].doubleValue(), cellAlignment);
					colCount++;

					cell = row.createCell(34);
					cell = ReportFormats.cellNum(row, colCount,
							divisionTotal[6] == null ? 0l : divisionTotal[6].doubleValue(), cellAlignment);
					colCount++;

					Arrays.fill(divisionTotal, BigDecimal.ZERO);
					colCount = 0;
					rowCount++;
				}

				if (!(newDivision.equalsIgnoreCase(oldDivision))) {
					colCount = 0;
					row = sheet.createRow(rowCount);
					cell = ReportFormats.cell(row, colCount, "Division : " + o.getDivision_name(), null);
					colCount = 0;
					rowCount++;

				}

				row = sheet.createRow(rowCount);
				cell = ReportFormats.cell(row, colCount, o.getGrn_date(), null);
				colCount++;

				cell = ReportFormats.cell(row, colCount, o.getGrn_no(), null);
				colCount++;

				cell = ReportFormats.cell(row, colCount, o.getGrn_type(), null);
				colCount++;
				
				
				cell = ReportFormats.cell(row, colCount, o.getPo_no(), null);
				colCount++;

				
				
				cell = ReportFormats.cell(row, colCount, o.getPo_date(), null);
				colCount++;
				
				
				cell = ReportFormats.cell(row, colCount, o.getSupplier(), null);
				colCount++;

				cell = ReportFormats.cell(row, colCount, o.getHgst_reg_no(), null);
				colCount++;

				cell = ReportFormats.cell(row, colCount, o.getHstate_name(), null);
				colCount++;

				cell = ReportFormats.cell(row, colCount, o.getHstate_code(), null);
				colCount++;

				cell = ReportFormats.cell(row, colCount, o.getSending_Location(), null);
				colCount++;

				cell = ReportFormats.cell(row, colCount, o.getSupply_doc_type(), null);
				colCount++;

				cell = ReportFormats.cell(row, colCount, o.getTransfer_no(), null);
				colCount++;

				cell = ReportFormats.cell(row, colCount, o.getTransfer_date(), null);
				colCount++;

				cell = ReportFormats.cell(row, colCount, o.getLr_no(), null);
				colCount++;

				cell = ReportFormats.cell(row, colCount, o.getLr_date(), null);
				colCount++;
				
				cell = ReportFormats.cell(row, colCount, o.getFcode(), null);
				colCount++;
				

				cell = ReportFormats.cell(row, colCount, o.getProduct_name(), null);
				colCount++;

				cell = ReportFormats.cell(row, colCount, o.getPacking(), null);
				colCount++;

				cell = ReportFormats.cell(row, colCount, o.getHsncode(), null);
				colCount++;

				cell = ReportFormats.cell(row, colCount, o.getBatch_no(), null);
				colCount++;

				cell = ReportFormats.cell(row, colCount, o.getBin_number(), cellAlignment); // cess
				colCount++;

				cell = ReportFormats.cellNum(row, colCount,
						o.getGrn_rate() == null ? 0l : o.getGrn_rate().doubleValue(), cellAlignment);
				colCount++;
				//
				cell = ReportFormats.cellNum(row, colCount, o.getGrn_qty() == null ? 0l : o.getGrn_qty().doubleValue(),
						cellAlignment);
				colCount++;

				cell = ReportFormats.cellNum(row, colCount,
						o.getGrn_qty() == null ? 0l : o.getShort_qty().doubleValue(), cellAlignment);
				colCount++;

				cell = ReportFormats.cellNum(row, colCount,
						o.getGrn_qty() == null ? 0l : o.getDamage_qty().doubleValue(), cellAlignment);
				colCount++;

				cell = ReportFormats.cellNum(row, colCount,
						o.getGrn_qty() == null ? 0l : o.getTotal_grn_qty().doubleValue(), cellAlignment);
				colCount++;

				cell = ReportFormats.cellNum(row, colCount, o.getValue() == null ? 0l : o.getValue().doubleValue(),
						cellAlignment);
				colCount++;

				cell = ReportFormats.cellNum(row, colCount,
						o.getCgst_rate() == null ? 0l : o.getCgst_rate().doubleValue(), cellAlignment);
				colCount++;

				cell = ReportFormats.cellNum(row, colCount,
						o.getCgst_bill_amt() == null ? 0l : o.getCgst_bill_amt().doubleValue(), cellAlignment);
				colCount++;

				cell = ReportFormats.cellNum(row, colCount,
						o.getSgst_rate() == null ? 0l : o.getSgst_rate().doubleValue(), cellAlignment);
				colCount++;

				cell = ReportFormats.cellNum(row, colCount,
						o.getSgst_bill_amt() == null ? 0l : o.getSgst_bill_amt().doubleValue(), cellAlignment);
				colCount++;

				cell = ReportFormats.cellNum(row, colCount,
						o.getIgst_rate() == null ? 0l : o.getIgst_rate().doubleValue(), cellAlignment);
				colCount++;

				cell = ReportFormats.cellNum(row, colCount,
						o.getIgst_bill_amt() == null ? 0l : o.getIgst_bill_amt().doubleValue(), cellAlignment);
				colCount++;

				cell = ReportFormats.cellNum(row, colCount, Double.valueOf("0.00"), null);
				colCount++;

				cell = ReportFormats.cellNum(row, colCount, o.getValue1() == null ? 0l : Double.valueOf("0.00"),
						cellAlignment);
				colCount++;

				cell = ReportFormats.cellNum(row, colCount, Double.valueOf("0.00"), null);
				colCount++;

				cell = ReportFormats.cellNum(row, colCount, o.getValue2() == null ? 0l : o.getValue2().doubleValue(),
						cellAlignment);
				colCount++;

				totalValue = totalValue.add(o.getValue().add(o.getCgst_bill_amt()
						.add(o.getSgst_bill_amt().add(o.getIgst_bill_amt().add(o.getValue1().add(o.getValue2()))))));

				cell = ReportFormats.cellNum(row, colCount, totalValue == null ? 0l : totalValue.doubleValue(), null);
				colCount++;

		

				if (!isCfaToStockist) {
					cell = ReportFormats.cell(row, colCount, o.getGcma_req_ind(), null);
					colCount++;

					cell = ReportFormats.cell(row, colCount, o.getGcma_number(), null);
					colCount++;

					cell = ReportFormats.cell(row, colCount,
							o.getGcma_expiry_dt() == null ? "" : Utility.convertStringtoStringDate(o.getGcma_expiry_dt()),
							null);
					colCount++;
				}

				cell = ReportFormats.cell(row, colCount, o.getRemark(), null);
				colCount++;

				
				if (!isCfaToStockist) {
				cell = ReportFormats.cell(row, colCount, o.getReturn_from_fstaff_name(), null);
				colCount++;
				}
				cell = ReportFormats.cell(row, colCount, o.getSalable_nonsaleable(), null);
				colCount++;

				oldDivision = newDivision;

				divisionTotal[0] = divisionTotal[0].add(o.getValue() == null ? BigDecimal.ZERO : o.getValue());
				divisionTotal[1] = divisionTotal[1]
						.add(o.getCgst_bill_amt() == null ? BigDecimal.ZERO : o.getCgst_bill_amt());
				divisionTotal[2] = divisionTotal[2]
						.add(o.getSgst_bill_amt() == null ? BigDecimal.ZERO : o.getSgst_bill_amt());
				divisionTotal[3] = divisionTotal[3]
						.add(o.getIgst_bill_amt() == null ? BigDecimal.ZERO : o.getIgst_bill_amt());
				divisionTotal[4] = divisionTotal[4].add(o.getValue1() == null ? BigDecimal.ZERO : o.getValue1());
				divisionTotal[5] = divisionTotal[5].add(o.getValue2() == null ? BigDecimal.ZERO : o.getValue2());
				divisionTotal[6] = divisionTotal[6].add(totalValue == null ? BigDecimal.ZERO : totalValue);

				reportTotal[0] = reportTotal[0].add(o.getValue() == null ? BigDecimal.ZERO : o.getValue());
				reportTotal[1] = reportTotal[1]
						.add(o.getCgst_bill_amt() == null ? BigDecimal.ZERO : o.getCgst_bill_amt());
				reportTotal[2] = reportTotal[2]
						.add(o.getSgst_bill_amt() == null ? BigDecimal.ZERO : o.getSgst_bill_amt());
				reportTotal[3] = reportTotal[3]
						.add(o.getIgst_bill_amt() == null ? BigDecimal.ZERO : o.getIgst_bill_amt());
				reportTotal[4] = reportTotal[4].add(o.getValue1() == null ? BigDecimal.ZERO : o.getValue1());
				reportTotal[5] = reportTotal[5].add(o.getValue2() == null ? BigDecimal.ZERO : o.getValue2());
				reportTotal[6] = reportTotal[6].add(totalValue == null ? BigDecimal.ZERO : totalValue);

				totalValue = new BigDecimal(0);
			}

			rowCount++;
			colCount = 0;

			row = sheet.createRow(rowCount);

			cell = ReportFormats.cell(row, colCount, "Division Total", null);
			colCount++;

			for (int i = 0; i <= 21; i++) {
				cell = ReportFormats.cell(row, colCount, "", null);
				colCount++;
			}

			cell = row.createCell(23);
			cell = ReportFormats.cellNum(row, colCount, divisionTotal[0] == null ? 0l : divisionTotal[0].doubleValue(),
					cellAlignment);
			colCount++;

			cell = row.createCell(24);
			cell = ReportFormats.cell(row, colCount, "", cellAlignment);
			colCount++;

			cell = row.createCell(25);
			cell = ReportFormats.cellNum(row, colCount, divisionTotal[1] == null ? 0l : divisionTotal[1].doubleValue(),
					cellAlignment);
			colCount++;

			cell = row.createCell(26);
			cell = ReportFormats.cell(row, colCount, "", cellAlignment);
			colCount++;

			cell = row.createCell(27);
			cell = ReportFormats.cellNum(row, colCount, divisionTotal[2] == null ? 0l : divisionTotal[2].doubleValue(),
					cellAlignment);
			colCount++;

			cell = row.createCell(28);
			cell = ReportFormats.cell(row, colCount, "", cellAlignment);
			colCount++;

			cell = row.createCell(29);
			cell = ReportFormats.cellNum(row, colCount, divisionTotal[3] == null ? 0l : divisionTotal[3].doubleValue(),
					cellAlignment);
			colCount++;

			cell = row.createCell(30);
			cell = ReportFormats.cell(row, colCount, "", cellAlignment);
			colCount++;

			cell = row.createCell(31);
			cell = ReportFormats.cellNum(row, colCount, divisionTotal[4] == null ? 0l : divisionTotal[4].doubleValue(),
					cellAlignment);
			colCount++;

			cell = row.createCell(32);
			cell = ReportFormats.cell(row, colCount, "", cellAlignment);
			colCount++;

			cell = row.createCell(33);
			cell = ReportFormats.cellNum(row, colCount, divisionTotal[5] == null ? 0l : divisionTotal[5].doubleValue(),
					cellAlignment);
			colCount++;

			cell = row.createCell(34);
			cell = ReportFormats.cellNum(row, colCount, divisionTotal[6] == null ? 0l : divisionTotal[6].doubleValue(),
					cellAlignment);
			colCount++;

			rowCount++;
			colCount = 0;

			row = sheet.createRow(rowCount);

			cell = ReportFormats.cell(row, colCount, "Report Total", null);
			colCount++;

			for (int i = 0; i <= 21; i++) {
				cell = ReportFormats.cell(row, colCount, "", null);
				colCount++;
			}

			cell = row.createCell(23);
			cell = ReportFormats.cellNum(row, colCount, reportTotal[0] == null ? 0l : reportTotal[0].doubleValue(),
					cellAlignment);
			colCount++;

			cell = row.createCell(24);
			cell = ReportFormats.cell(row, colCount, "", cellAlignment);
			colCount++;

			cell = row.createCell(25);
			cell = ReportFormats.cellNum(row, colCount, reportTotal[1] == null ? 0l : reportTotal[1].doubleValue(),
					cellAlignment);
			colCount++;

			cell = row.createCell(26);
			cell = ReportFormats.cell(row, colCount, "", cellAlignment);
			colCount++;

			cell = row.createCell(27);
			cell = ReportFormats.cellNum(row, colCount, reportTotal[2] == null ? 0l : reportTotal[2].doubleValue(),
					cellAlignment);
			colCount++;

			cell = row.createCell(28);
			cell = ReportFormats.cell(row, colCount, "", cellAlignment);
			colCount++;

			cell = row.createCell(29);// igst
			cell = ReportFormats.cellNum(row, colCount, reportTotal[3] == null ? 0l : reportTotal[3].doubleValue(),
					cellAlignment);
			colCount++;

			cell = row.createCell(30);
			cell = ReportFormats.cell(row, colCount, "", cellAlignment);
			colCount++;

			cell = row.createCell(31);
			cell = ReportFormats.cellNum(row, colCount, reportTotal[4] == null ? 0l : reportTotal[4].doubleValue(),
					cellAlignment);
			colCount++;

			cell = row.createCell(32);
			cell = ReportFormats.cell(row, colCount, "", cellAlignment);
			colCount++;

			cell = row.createCell(33);
			cell = ReportFormats.cellNum(row, colCount, reportTotal[5] == null ? 0l : reportTotal[5].doubleValue(),
					cellAlignment);
			colCount++;

			/*
			 * cell = row.createCell(34); cell = ReportFormats.cell(row, colCount, "",
			 * cellAlignment); colCount++;
			 */

			cell = row.createCell(34);
			cell = ReportFormats.cellNum(row, colCount, reportTotal[6] == null ? 0l : reportTotal[6].doubleValue(),
					cellAlignment);
			colCount++;

			FileOutputStream fileOutputStream = new FileOutputStream(filepath);
			workbook.write(fileOutputStream);
			filename = usermasterservice.generateExcellock(filepath, filename, empId, ".xlsx");

		} finally {
			if (workbook != null) {
				workbook.close();
			}
		}
		return filename;

	}
	
	private String generateGRNDetailReport_SG(List<ViewGRNDetail_GST> list, ReportBean bean, String companyname, String empId) throws Exception {
		
		                 String filename = "GRNDetailReport" + new Date().getTime()+ ".xlsx";
		                 String filepath = REPORT_FILE_PATH + filename ;
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
		                         XSSFSheet sheet = workbook.createSheet("GRN Detail Report");
		 
		                         XSSFRow row = null;
		                         XSSFCell cell = null;
		                         int rowCount = 0;
		                         int colCount = 0;
		 
		                         String headings[] = { "GRN Date.", "GRN No.", "GRN Type", "Name", "GSTIN", "State", "State Code",
		                                         "Place of Supply", "Supply Doc Type", "Supply Doc NO", "Supply Doc Date", "LR No", "LR Date",
		                                         "Product", "Pack", "HSN Code", "Batch No", "No. of Cases", "Rate", "Saleable", "Short Qty",
		                                         "Spoilt", "Total Grn Qty", "Goods Value", "CGST %", "CGST Value", "SGST %", "SGST Value", "IGST %",
		                                         "IGST Value", "Additional Charge", "Amount", "Discount", "Amount", "Total Value", "FCODE",
		                                         "GCMA Indicator", "GCMA CODE", "GCMA EXP DATE", "REMARK","Return From FieldStaff","Saleable/Non-Saleable"};
		 
		                         XSSFCellStyle headingCellStyle = ReportFormats.heading(workbook);
		                         XSSFCellStyle columnHeadingStyle = ReportFormats.columnHeading(workbook);
		                         XSSFCellStyle cellAlignment = ReportFormats.setCellAlignment(workbook, ALIGN_RIGHT);
		                         XSSFCellStyle cellAlignment2 = ReportFormats.setCellAlignment(workbook, ALIGN_LEFT);
		 
		                         row = sheet.createRow(rowCount);
		                         cell = row.createCell(colCount);
		                         sheet.addMergedRegion(new CellRangeAddress(rowCount, rowCount, colCount, colCount + headings.length - 1));
		                         cell.setCellValue(companyname);
		                         cell.setCellStyle(headingCellStyle);
		                         rowCount++;
		 
		                         row = sheet.createRow(rowCount);
		                         cell = row.createCell(colCount);
		                         sheet.addMergedRegion(new CellRangeAddress(rowCount, rowCount, colCount, colCount + headings.length - 1));
		                         cell.setCellValue("GRN Detail Report");
		                         cell.setCellStyle(headingCellStyle);
		                         rowCount++;
		 
		                         row = sheet.createRow(rowCount);
		                         cell = row.createCell(colCount);
		                         sheet.addMergedRegion(new CellRangeAddress(rowCount, rowCount, colCount, colCount + headings.length - 1));
		                         cell.setCellValue("Period: " + MedicoResources.convertUtilDateToString_DD_MM_YYYY_(bean.getStartDate())
		                                         + " to " + MedicoResources.convertUtilDateToString_DD_MM_YYYY_(bean.getEndDate()));
		                         cell.setCellStyle(headingCellStyle);
		 
		                         rowCount += 1;
		                         row = sheet.createRow(rowCount);
		 
		                         for (String heading : headings) {
		                                 cell = ReportFormats.cell(row, colCount, heading, columnHeadingStyle);
		                                 colCount++;
		                         }
		 
		                         sheet.groupRow(0, 2);
		                         sheet.createFreezePane(0, 4);
		 
		                         String newDivision = "";
		                         String oldDivision = "";
		                         // String newGrnDate = "";
		                         // String oldGrnDate = "";
		                         // String newGrnNo = "";
		                         // String oldGrnNo = "";
		 
		                         BigDecimal totalValue = new BigDecimal(0);
		                         BigDecimal divisionTotal[] = new BigDecimal[7];
		                         BigDecimal reportTotal[] = new BigDecimal[7];
		 
		                         // Arrays.fill(totalValue, BigDecimal.ZERO);
		                         Arrays.fill(divisionTotal, BigDecimal.ZERO);
		                         Arrays.fill(reportTotal, BigDecimal.ZERO);
		 
		                         for (ViewGRNDetail_GST o : list) {
		 
		                                 colCount = 0;
		                                 rowCount++;
		 
		                                 newDivision = o.getDivision_name();
		                                 if (!(newDivision.equalsIgnoreCase(oldDivision)) && !oldDivision.equalsIgnoreCase("")) {
		 
		                                         row = sheet.createRow(rowCount);
		 
		                                         cell = ReportFormats.cell(row, colCount, "Division Total".toString(), null);
		                                         colCount++;
		 
		                                         for (int i = 0; i <= 18; i++) {
		                                                 cell = ReportFormats.cell(row, colCount, "", null);
		                                                 colCount++;
		                                         }
		 
		                                         cell = row.createCell(21);
		                                         cell = ReportFormats.cellNum(row, colCount,
		                                                         divisionTotal[0] == null ? 0l : divisionTotal[0].doubleValue(), cellAlignment);
		                                         colCount++;
		 
		                                         cell = row.createCell(22);
		                                         cell = ReportFormats.cell(row, colCount, "", cellAlignment);
		                                         colCount++;
		 
		                                         cell = row.createCell(23);
		                                         cell = ReportFormats.cellNum(row, colCount,
		                                                         divisionTotal[1] == null ? 0l : divisionTotal[1].doubleValue(), cellAlignment);
		                                         colCount++;
		 
		                                         cell = row.createCell(24);
		                                         cell = ReportFormats.cell(row, colCount, "", cellAlignment);
		                                         colCount++;
		 
		                                         cell = row.createCell(25);
		                                         cell = ReportFormats.cellNum(row, colCount,
		                                                         divisionTotal[2] == null ? 0l : divisionTotal[2].doubleValue(), cellAlignment);
		                                         colCount++;
		 
		                                         cell = row.createCell(26);
		                                         cell = ReportFormats.cell(row, colCount, "", cellAlignment);
		                                         colCount++;
		 
		                                         cell = row.createCell(27);
		                                         cell = ReportFormats.cellNum(row, colCount,
		                                                         divisionTotal[3] == null ? 0l : divisionTotal[3].doubleValue(), cellAlignment);
		                                         colCount++;
		 
		                                         cell = row.createCell(28);
		                                         cell = ReportFormats.cell(row, colCount, "", cellAlignment);
		                                         colCount++;
		 
		                                         cell = row.createCell(29);
		                                         cell = ReportFormats.cellNum(row, colCount,
		                                                         divisionTotal[4] == null ? 0l : divisionTotal[4].doubleValue(), cellAlignment);
		                                         colCount++;
		 
		                                         cell = row.createCell(30);
		                                         cell = ReportFormats.cell(row, colCount, "", cellAlignment);
		                                         colCount++;
		 
		                                         cell = row.createCell(31);
		                                         cell = ReportFormats.cellNum(row, colCount,
		                                                         divisionTotal[5] == null ? 0l : divisionTotal[5].doubleValue(), cellAlignment);
		                                         colCount++;
		 
		                                         cell = row.createCell(32);
		                                         cell = ReportFormats.cellNum(row, colCount,
		                                                         divisionTotal[6] == null ? 0l : divisionTotal[6].doubleValue(), cellAlignment);
		                                         colCount++;
		 
		                                         Arrays.fill(divisionTotal, BigDecimal.ZERO);
		                                         colCount = 0;
		                                         rowCount++;
		                                 }
		 
		                                 if (!(newDivision.equalsIgnoreCase(oldDivision))) {
		                                         colCount = 0;
		                                         row = sheet.createRow(rowCount);
		                                         cell = ReportFormats.cell(row, colCount, "Division : " + o.getDivision_name(), null);
		                                         colCount = 0;
		                                         rowCount++;
		 
		                                 }
		 
		                                 row = sheet.createRow(rowCount);
		                                 cell = ReportFormats.cell(row, colCount, o.getGrn_date(), null);
		                                 colCount++;
		 
		                                 cell = ReportFormats.cell(row, colCount, o.getGrn_no(), null);
		                                 colCount++;
		 
		                                 cell = ReportFormats.cell(row, colCount, o.getGrn_type(), null);
		                                 colCount++;
		 
		                                 cell = ReportFormats.cell(row, colCount, o.getSupplier(), null);
		                                 colCount++;
		 
		                                 cell = ReportFormats.cell(row, colCount, o.getHgst_reg_no(), null);
		                                 colCount++;
		 
		                                 cell = ReportFormats.cell(row, colCount, o.getHstate_name(), null);
		                                 colCount++;
		 
		                                 cell = ReportFormats.cell(row, colCount, o.getHstate_code(), null);
		                                 colCount++;
		 
		                                 cell = ReportFormats.cell(row, colCount, o.getSending_Location(), null);
		                                 colCount++;
		 
		                                 cell = ReportFormats.cell(row, colCount, o.getSupply_doc_type(), null);
		                                 colCount++;
		 
		                                 cell = ReportFormats.cell(row, colCount, o.getTransfer_no(), null);
		                                 colCount++;
		 
		                                 cell = ReportFormats.cell(row, colCount, o.getTransfer_date(), null);
		                                 colCount++;
		 
		                                 cell = ReportFormats.cell(row, colCount, o.getLr_no(), null);
		                                 colCount++;
		 
		                                 cell = ReportFormats.cell(row, colCount, o.getLr_date(), null);
		                                 colCount++;
		 
		                                 cell = ReportFormats.cell(row, colCount, o.getProduct_name(), null);
		                                 colCount++;
		 
		                                 cell = ReportFormats.cell(row, colCount, o.getPacking(), null);
		                                 colCount++;
		 
		                                 cell = ReportFormats.cell(row, colCount, o.getHsncode(), null);
		                                 colCount++;
		 
		                                 cell = ReportFormats.cell(row, colCount, o.getBatch_no(), null);
		                                 colCount++;
		 
		                                 cell = ReportFormats.cell(row, colCount, o.getBin_number(), cellAlignment); // cess
		                                 colCount++;
		 
		                                 cell = ReportFormats.cellNum(row, colCount,
		                                                 o.getGrn_rate() == null ? 0l : o.getGrn_rate().doubleValue(), cellAlignment);
		                                 colCount++;
		                                 //
		                                 cell = ReportFormats.cellNum(row, colCount, o.getGrn_qty() == null ? 0l : o.getGrn_qty().doubleValue(),
		                                                 cellAlignment);
		                                 colCount++;
		 
		                                 cell = ReportFormats.cellNum(row, colCount,
		                                                 o.getGrn_qty() == null ? 0l : o.getShort_qty().doubleValue(), cellAlignment);
		                                 colCount++;
		 
		                                 cell = ReportFormats.cellNum(row, colCount,
		                                                 o.getGrn_qty() == null ? 0l : o.getDamage_qty().doubleValue(), cellAlignment);
		                                 colCount++;
		 
		                                 cell = ReportFormats.cellNum(row, colCount,
		                                                 o.getGrn_qty() == null ? 0l : o.getTotal_grn_qty().doubleValue(), cellAlignment);
		                                 colCount++;
		 
		                                 cell = ReportFormats.cellNum(row, colCount, o.getValue() == null ? 0l : o.getValue().doubleValue(),
		                                                 cellAlignment);
		                                 colCount++;
		 
		                                 cell = ReportFormats.cellNum(row, colCount,
		                                                 o.getCgst_rate() == null ? 0l : o.getCgst_rate().doubleValue(), cellAlignment);
		                                 colCount++;
		 
		                                 cell = ReportFormats.cellNum(row, colCount,
		                                                 o.getCgst_bill_amt() == null ? 0l : o.getCgst_bill_amt().doubleValue(), cellAlignment);
		                                 colCount++;
		 
		                                 cell = ReportFormats.cellNum(row, colCount,
		                                                 o.getSgst_rate() == null ? 0l : o.getSgst_rate().doubleValue(), cellAlignment);
		                                 colCount++;
		 
		                                 cell = ReportFormats.cellNum(row, colCount,
		                                                 o.getSgst_bill_amt() == null ? 0l : o.getSgst_bill_amt().doubleValue(), cellAlignment);
		                                 colCount++;
		 
		                                 cell = ReportFormats.cellNum(row, colCount,
		                                                 o.getIgst_rate() == null ? 0l : o.getIgst_rate().doubleValue(), cellAlignment);
		                                 colCount++;
		 
		                                 cell = ReportFormats.cellNum(row, colCount,
		                                                 o.getIgst_bill_amt() == null ? 0l : o.getIgst_bill_amt().doubleValue(), cellAlignment);
		                                 colCount++;
		 
		                                 cell = ReportFormats.cell(row, colCount, o.getText1(), null);
		                                 colCount++;
		 
		                                 cell = ReportFormats.cellNum(row, colCount, o.getValue1() == null ? 0l : o.getValue1().doubleValue(),
		                                                 cellAlignment);
		                                 colCount++;
		 
		                                 cell = ReportFormats.cell(row, colCount, o.getText2(), null);
		                                 colCount++;
		 
		                                 cell = ReportFormats.cellNum(row, colCount, o.getValue2() == null ? 0l : o.getValue2().doubleValue(),
		                                                 cellAlignment);
		                                 colCount++;
		 
		                                 totalValue = totalValue.add(o.getValue().add(o.getCgst_bill_amt()
		                                                 .add(o.getSgst_bill_amt().add(o.getIgst_bill_amt().add(o.getValue1().add(o.getValue2()))))));
		 
		                                 cell = ReportFormats.cellNum(row, colCount, totalValue == null ? 0l : totalValue.doubleValue(), null);
		                                 colCount++;
		 
		                                 cell = ReportFormats.cell(row, colCount, o.getFcode(), null);
		                                 colCount++;
		 
		                                 cell = ReportFormats.cell(row, colCount, o.getGcma_req_ind(), null);
		                                 colCount++;
		 
		                                 cell = ReportFormats.cell(row, colCount, o.getGcma_number(), null);
		                                 colCount++;
		 
		                                 cell = ReportFormats.cell(row, colCount,
		                                                 o.getGcma_expiry_dt() == null ? "" : Utility.convertStringtoStringDate(o.getGcma_expiry_dt()),
		                                                 null);
		                                 colCount++;
		 
		                                 cell = ReportFormats.cell(row, colCount, o.getRemark(), null);
		                                 colCount++;
		 
		                                 cell = ReportFormats.cell(row, colCount,o.getReturn_from_fstaff_name(), null);
		                                 colCount++;
		                                 
		                                 cell = ReportFormats.cell(row, colCount,o.getSalable_nonsaleable(), null);
		                                 colCount++;
		                                 
		                                 oldDivision = newDivision;
		 
		                                 divisionTotal[0] = divisionTotal[0].add(o.getValue() == null ? BigDecimal.ZERO : o.getValue());
		                                 divisionTotal[1] = divisionTotal[1]
		                                                 .add(o.getCgst_bill_amt() == null ? BigDecimal.ZERO : o.getCgst_bill_amt());
		                                 divisionTotal[2] = divisionTotal[2]
		                                                 .add(o.getSgst_bill_amt() == null ? BigDecimal.ZERO : o.getSgst_bill_amt());
		                                 divisionTotal[3] = divisionTotal[3]
		                                                 .add(o.getIgst_bill_amt() == null ? BigDecimal.ZERO : o.getIgst_bill_amt());
		                                 divisionTotal[4] = divisionTotal[4].add(o.getValue1() == null ? BigDecimal.ZERO : o.getValue1());
		                                 divisionTotal[5] = divisionTotal[5].add(o.getValue2() == null ? BigDecimal.ZERO : o.getValue2());
		                                 divisionTotal[6] = divisionTotal[6].add(totalValue == null ? BigDecimal.ZERO : totalValue);
		 
		                                 reportTotal[0] = reportTotal[0].add(o.getValue() == null ? BigDecimal.ZERO : o.getValue());
		                                 reportTotal[1] = reportTotal[1]
		                                                 .add(o.getCgst_bill_amt() == null ? BigDecimal.ZERO : o.getCgst_bill_amt());
		                                 reportTotal[2] = reportTotal[2]
		                                                 .add(o.getSgst_bill_amt() == null ? BigDecimal.ZERO : o.getSgst_bill_amt());
		                                 reportTotal[3] = reportTotal[3]
		                                                 .add(o.getIgst_bill_amt() == null ? BigDecimal.ZERO : o.getIgst_bill_amt());
		                                 reportTotal[4] = reportTotal[4].add(o.getValue1() == null ? BigDecimal.ZERO : o.getValue1());
		                                 reportTotal[5] = reportTotal[5].add(o.getValue2() == null ? BigDecimal.ZERO : o.getValue2());
		                                 reportTotal[6] = reportTotal[6].add(totalValue == null ? BigDecimal.ZERO : totalValue);
		 
		                                 totalValue = new BigDecimal(0);
		                         }
		 
		                         rowCount++;
		                         colCount = 0;
		 
		                         row = sheet.createRow(rowCount);
		 
		                         cell = ReportFormats.cell(row, colCount, "Division Total", null);
		                         colCount++;
		 
		                         for (int i = 0; i <= 18; i++) {
		                                 cell = ReportFormats.cell(row, colCount, "", null);
		                                 colCount++;
		                         }
		 
		                         cell = row.createCell(21);
		                         cell = ReportFormats.cellNum(row, colCount, divisionTotal[0] == null ? 0l : divisionTotal[0].doubleValue(),
		                                         cellAlignment);
		                         colCount++;
		 
		                         cell = row.createCell(22);
		                         cell = ReportFormats.cell(row, colCount, "", cellAlignment);
		                         colCount++;
		 
		                         cell = row.createCell(23);
		                         cell = ReportFormats.cellNum(row, colCount, divisionTotal[1] == null ? 0l : divisionTotal[1].doubleValue(),
		                                         cellAlignment);
		                         colCount++;
		 
		                         cell = row.createCell(24);
		                         cell = ReportFormats.cell(row, colCount, "", cellAlignment);
		                         colCount++;
		 
		                         cell = row.createCell(25);
		                         cell = ReportFormats.cellNum(row, colCount, divisionTotal[2] == null ? 0l : divisionTotal[2].doubleValue(),
		                                         cellAlignment);
		                         colCount++;
		 
		                         cell = row.createCell(26);
		                         cell = ReportFormats.cell(row, colCount, "", cellAlignment);
		                         colCount++;
		 
		                         cell = row.createCell(27);
		                         cell = ReportFormats.cellNum(row, colCount, divisionTotal[3] == null ? 0l : divisionTotal[3].doubleValue(),
		                                         cellAlignment);
		                         colCount++;
		 
		                         cell = row.createCell(28);
		                         cell = ReportFormats.cell(row, colCount, "", cellAlignment);
		                         colCount++;
		 
		                         cell = row.createCell(29);
		                         cell = ReportFormats.cellNum(row, colCount, divisionTotal[4] == null ? 0l : divisionTotal[4].doubleValue(),
		                                         cellAlignment);
		                         colCount++;
		 
		                         cell = row.createCell(30);
		                         cell = ReportFormats.cell(row, colCount, "", cellAlignment);
		                         colCount++;
		 
		                         cell = row.createCell(31);
		                         cell = ReportFormats.cellNum(row, colCount, divisionTotal[5] == null ? 0l : divisionTotal[5].doubleValue(),
		                                         cellAlignment);
		                         colCount++;
		 
		                         cell = row.createCell(32);
		                         cell = ReportFormats.cellNum(row, colCount, divisionTotal[6] == null ? 0l : divisionTotal[6].doubleValue(),
		                                         cellAlignment);
		                         colCount++;
		 
		                         rowCount++;
		                         colCount = 0;
		 
		                         row = sheet.createRow(rowCount);
		 
		                         cell = ReportFormats.cell(row, colCount, "Report Total", null);
		                         colCount++;
		 
		                         for (int i = 0; i <= 21; i++) {
		                                 cell = ReportFormats.cell(row, colCount, "", null);
		                                 colCount++;
		                         }
		 
		                         cell = row.createCell(23);
		                         cell = ReportFormats.cellNum(row, colCount, reportTotal[0] == null ? 0l : reportTotal[0].doubleValue(),
		                                         cellAlignment);
		                         colCount++;
		 
		                         cell = row.createCell(24);
		                         cell = ReportFormats.cell(row, colCount, "", cellAlignment);
		                         colCount++;
		 
		                         cell = row.createCell(25);
		                         cell = ReportFormats.cellNum(row, colCount, reportTotal[1] == null ? 0l : reportTotal[1].doubleValue(),
		                                         cellAlignment);
		                         colCount++;
		 
		                         cell = row.createCell(26);
		                         cell = ReportFormats.cell(row, colCount, "", cellAlignment);
		                         colCount++;
		 
		                         cell = row.createCell(27);
		                         cell = ReportFormats.cellNum(row, colCount, reportTotal[2] == null ? 0l : reportTotal[2].doubleValue(),
		                                         cellAlignment);
		                         colCount++;
		 
		                         cell = row.createCell(28);
		                         cell = ReportFormats.cell(row, colCount, "", cellAlignment);
		                         colCount++;
		 
		                         cell = row.createCell(29);// igst
		                         cell = ReportFormats.cellNum(row, colCount, reportTotal[3] == null ? 0l : reportTotal[3].doubleValue(),
		                                         cellAlignment);
		                         colCount++;
		 
		                         cell = row.createCell(30);
		                         cell = ReportFormats.cell(row, colCount, "", cellAlignment);
		                         colCount++;
		 
		                         cell = row.createCell(31);
		                         cell = ReportFormats.cellNum(row, colCount, reportTotal[4] == null ? 0l : reportTotal[4].doubleValue(),
		                                         cellAlignment);
		                         colCount++;
		 
		                         cell = row.createCell(32);
		                         cell = ReportFormats.cell(row, colCount, "", cellAlignment);
		                         colCount++;
		 
		                         cell = row.createCell(33);
		                         cell = ReportFormats.cellNum(row, colCount, reportTotal[5] == null ? 0l : reportTotal[5].doubleValue(),
		                                         cellAlignment);
		                         colCount++;
		 
		                         /*
		                          * cell = row.createCell(34); cell = ReportFormats.cell(row, colCount, "",
		                          * cellAlignment); colCount++;
		                          */
		 
		                         cell = row.createCell(34);
		                         cell = ReportFormats.cellNum(row, colCount, reportTotal[6] == null ? 0l : reportTotal[6].doubleValue(),
		                                         cellAlignment);
		                         colCount++;
		 
		                         FileOutputStream fileOutputStream = new FileOutputStream(filepath);
		                         workbook.write(fileOutputStream);
		                         filename=usermasterservice.generateExcellock(filepath, filename,empId,".xlsx");
		 
		                 } finally {
		                         if (workbook != null) {
		                             workbook.close();
		                         }
		                 }
		                 return filename ;
}

	@Override
	public String generateGRNSummary(List<ViewGRNSummary> list, ReportBean bean, String cfa_to_stk,String companyCode, String companyname, String empId)
			throws Exception {

		String nameonly = "GRNSummaryReport" + new Date().getTime();
		String filename = "GRNSummaryReport" + new Date().getTime() + ".xlsx";
		String filepath = REPORT_FILE_PATH + filename;
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
			XSSFSheet sheet = workbook.createSheet("GRN summary Report");

			XSSFRow row = null;
			XSSFCell cell = null;
			int rowCount = 0;
			int colCount = 0;

			String[] headings;

			if(cfa_to_stk.equalsIgnoreCase("N") && companyCode.trim().equals("PFZ") )
			{
			 headings = new String[]  { "GRN No.","Grn Entered By", "Location", "Supplier", "GRN Date", "Transfer No", "Transfer Date",
					"L.R No", "L.R Date", "Value (Rs.Ps)", "Lorry_No / Transporter Name", "Division Name",
					"Supplier Code", "PO No.", "PO Date","IDOC/IBD No","Remarks"};
			} else {
				 headings = new String[]  { "GRN No.","Grn Entered By", "Location", "Supplier", "GRN Date", "Transfer No", "Transfer Date",
						"L.R No", "L.R Date", "Value (Rs.Ps)", "Lorry_No / Transporter Name", "Division Name",
						"Supplier Code", "PO No.", "PO Date","Remarks" };
			}
			XSSFCellStyle headingCellStyle = ReportFormats.heading(workbook);
			XSSFCellStyle columnHeadingStyle = ReportFormats.columnHeading(workbook);
			XSSFCellStyle cellAlignment = ReportFormats.setCellAlignment(workbook, ALIGN_RIGHT);

			row = sheet.createRow(rowCount);
			cell = row.createCell(colCount);
			sheet.addMergedRegion(new CellRangeAddress(rowCount, rowCount, colCount, colCount + headings.length - 1));
			cell.setCellValue(companyname);
			cell.setCellStyle(headingCellStyle);
			rowCount++;

			row = sheet.createRow(rowCount);
			cell = row.createCell(colCount);
			sheet.addMergedRegion(new CellRangeAddress(rowCount, rowCount, colCount, colCount + headings.length - 1));
			cell.setCellValue("GRN Summary Report");
			cell.setCellStyle(headingCellStyle);
			rowCount++;

			row = sheet.createRow(rowCount);
			cell = row.createCell(colCount);
			sheet.addMergedRegion(new CellRangeAddress(rowCount, rowCount, colCount, colCount + headings.length - 1));
			cell.setCellValue("Period: " + MedicoResources.convertUtilDateToString_DD_MM_YYYY_(bean.getStartDate())
					+ " to " + MedicoResources.convertUtilDateToString_DD_MM_YYYY_(bean.getEndDate()));
			cell.setCellStyle(headingCellStyle);

			rowCount += 1;
			row = sheet.createRow(rowCount);

			for (String heading : headings) {
				cell = ReportFormats.cell(row, colCount, heading, columnHeadingStyle);
				colCount++;
			}

			sheet.groupRow(0, 2);
			sheet.createFreezePane(0, 4);

			rowCount++;
			colCount = 0;
			
			LocalDate endDate = bean.getEndDate().toInstant()
                    .atZone(ZoneId.systemDefault())
                    .toLocalDate();

			LocalDate cutoffDate = LocalDate.of(2024, 12, 1);

			if (bean.getCompcd().trim().equalsIgnoreCase(PFZ) && endDate.isBefore(cutoffDate)) {
				list = list.stream().peek(data -> {
					if ("CITIUS".equalsIgnoreCase(data.getDivision_name().trim()) || "CITIUS  (ALTIUS - NEW)".equalsIgnoreCase(data.getDivision_name().trim())) {
						data.setDivision_name("ALTIUS - NEW");
					}
				}).collect(Collectors.toList());
			}

			for (ViewGRNSummary data : list) {

				row = sheet.createRow(rowCount);
				cell = ReportFormats.cell(row, colCount, data.getGrn_no() == null ? "" : data.getGrn_no(), null);
				colCount++;
				
				cell = ReportFormats.cell(row, colCount, data.getGrn_entered_by() == null  ? "" : data.getGrn_entered_by(), null);
				colCount++;


				cell = ReportFormats.cell(row, colCount,
						data.getSending_Location() == null ? "" : data.getSending_Location(), null);
				colCount++;

				cell = ReportFormats.cell(row, colCount, data.getSupplier() == null ? "" : data.getSupplier(), null);
				colCount++;

				cell = ReportFormats.cell(row, colCount, data.getGrn_date() == null ? "" : data.getGrn_date(), null);
				colCount++;

				cell = ReportFormats.cell(row, colCount, data.getTransfer_no() == null ? "" : data.getTransfer_no(),
						null);
				colCount++;

				cell = ReportFormats.cell(row, colCount, data.getTransfer_date() == null ? "" : data.getTransfer_date(),
						null);
				colCount++;

				cell = ReportFormats.cell(row, colCount, data.getLr_no() == null ? "" : data.getLr_no(), null);
				colCount++;

				cell = ReportFormats.cell(row, colCount, data.getLr_date() == null ? "" : data.getLr_date(), null);
				colCount++;

				cell = ReportFormats.cell(row, colCount,
						data.getValue() == null ? "" : data.getValue().setScale(2, RoundingMode.HALF_UP).toString(),
						cellAlignment);
				colCount++;

				cell = ReportFormats.cell(row, colCount,
						data.getTransporter_Name() == null ? "" : data.getTransporter_Name(), null);
				colCount++;

				cell = ReportFormats.cell(row, colCount, data.getDivision_name() == null ? "" : data.getDivision_name(),
						null);
				colCount++;

				cell = ReportFormats.cell(row, colCount, data.getSuppliercode() == null ? "" : data.getSuppliercode(),
						null);
				colCount++;

				cell = ReportFormats.cell(row, colCount, data.getPo_no() == null ? "" : data.getPo_no(), null);
				colCount++;

				cell = ReportFormats.cell(row, colCount, data.getPo_date() == null ? "" : data.getPo_date(), null);
				colCount++;
				

				if (cfa_to_stk.equalsIgnoreCase("N") && companyCode.trim().equals("PFZ")  ) {
				cell = ReportFormats.cell(row, colCount, data.getGrn_ref_no() == null ? "" : data.getGrn_ref_no(), null);
				colCount++;
				}
				
				cell = ReportFormats.cell(row, colCount, data.getRemarks() == null ||data.getRemarks().equalsIgnoreCase("undefined") ? "" : data.getRemarks(), null);
				colCount++;
				
				

				rowCount++;
				colCount = 0;
			}

			FileOutputStream fileOutputStream = new FileOutputStream(filepath);
			workbook.write(fileOutputStream);

			filename = usermasterservice.generateExcellock(filepath, filename, empId, ".xlsx");

		} finally {
			if (workbook != null) {
				workbook.close();
			}
		}
		return filename;
	}

	@Override
	public String generateGRNDetail(List<ViewGRNDetail> list, ReportBean bean, String compcode, String companyname,
			String empId) throws Exception {

		String nameonly = "GRNDetailReport" + new Date().getTime();
		String filename = "GRNDetailReport" + new Date().getTime() + ".xlsx";
		String filepath = REPORT_FILE_PATH + filename;
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
			XSSFSheet sheet = workbook.createSheet("GRN Detail Report");

			XSSFRow row = null;
			XSSFCell cell = null;
			int rowCount = 0;
			int colCount = 0;

			String headings[] = { "GRN No.", "GRN Type", "Location", "Supplier", "GRN Date", "Transfer No",
					"Transfer Date", "L.R No", "L.R Date", "Lorry_No / Transporter Name", "Division Name",
					"Supplier Code", "PO No.", "PO Date", "Description", "Batch No", "Batch Expiry Date", "GRN QTY",
					"Batch Rate", "Value (Rs.Ps)", "Product Type/Category", "Product code", "Fcode", "GCMA Indicator",
					"GCMA Code", "GCMA Exp Date", "Mfg. Dt.", "Bin Number", "Remark" };

			XSSFCellStyle headingCellStyle = ReportFormats.heading(workbook);
			XSSFCellStyle columnHeadingStyle = ReportFormats.columnHeading(workbook);
			XSSFCellStyle cellAlignment = ReportFormats.setCellAlignment(workbook, ALIGN_RIGHT);
			XSSFCellStyle cellAlignment2 = ReportFormats.setCellAlignment(workbook, ALIGN_LEFT);

			row = sheet.createRow(rowCount);
			cell = row.createCell(colCount);
			sheet.addMergedRegion(new CellRangeAddress(rowCount, rowCount, colCount, colCount + headings.length - 1));
			cell.setCellValue(companyname);
			cell.setCellStyle(headingCellStyle);
			rowCount++;

			row = sheet.createRow(rowCount);
			cell = row.createCell(colCount);
			sheet.addMergedRegion(new CellRangeAddress(rowCount, rowCount, colCount, colCount + headings.length - 1));
			cell.setCellValue("GRN Detail Report");
			cell.setCellStyle(headingCellStyle);
			rowCount++;

			row = sheet.createRow(rowCount);
			cell = row.createCell(colCount);
			sheet.addMergedRegion(new CellRangeAddress(rowCount, rowCount, colCount, colCount + headings.length - 1));
			cell.setCellValue("Period: " + MedicoResources.convertUtilDateToString_DD_MM_YYYY_(bean.getStartDate())
					+ " to " + MedicoResources.convertUtilDateToString_DD_MM_YYYY_(bean.getEndDate()));
			cell.setCellStyle(headingCellStyle);

			rowCount += 1;
			row = sheet.createRow(rowCount);

			for (String heading : headings) {
				if (compcode.trim().equals("PFZ") && heading.equals("Bin Number")) {
					cell = ReportFormats.cell(row, colCount, "No. of Cases", columnHeadingStyle);
				} else {
					cell = ReportFormats.cell(row, colCount, heading, columnHeadingStyle);
				}
				colCount++;
			}

			sheet.groupRow(0, 2);
			sheet.createFreezePane(0, 4);

			rowCount++;
			colCount = 0;
			
			LocalDate endDate = bean.getEndDate().toInstant()
                    .atZone(ZoneId.systemDefault())
                    .toLocalDate();

			LocalDate cutoffDate = LocalDate.of(2024, 12, 1);

			if (bean.getCompcd().trim().equalsIgnoreCase(PFZ) && endDate.isBefore(cutoffDate)) {
				list = list.stream().peek(data -> {
					if ("CITIUS".equalsIgnoreCase(data.getDivision_name().trim()) || "CITIUS  (ALTIUS - NEW)".equalsIgnoreCase(data.getDivision_name().trim())) {
						data.setDivision_name("ALTIUS - NEW");
					}
				}).collect(Collectors.toList());
			}

			for (ViewGRNDetail data : list) {

				row = sheet.createRow(rowCount);
				cell = ReportFormats.cell(row, colCount, data.getGrn_no() == null ? "" : data.getGrn_no(), null);
				colCount++;

				cell = ReportFormats.cell(row, colCount, data.getGrn_type() == null ? "" : data.getGrn_type(), null);
				colCount++;

				cell = ReportFormats.cell(row, colCount,
						data.getSending_Location() == null ? "" : data.getSending_Location(), null);
				colCount++;

				cell = ReportFormats.cell(row, colCount, data.getSupplier() == null ? "" : data.getSupplier(), null);
				colCount++;

				cell = ReportFormats.cell(row, colCount, data.getGrn_date() == null ? "" : data.getGrn_date(), null);
				colCount++;

				cell = ReportFormats.cell(row, colCount, data.getTransfer_no() == null ? "" : data.getTransfer_no(),
						null);
				colCount++;

				cell = ReportFormats.cell(row, colCount, data.getTransfer_date() == null ? "" : data.getTransfer_date(),
						null);
				colCount++;

				cell = ReportFormats.cell(row, colCount, data.getLr_no() == null ? "" : data.getLr_no(), null);
				colCount++;

				cell = ReportFormats.cell(row, colCount, data.getLr_date() == null ? "" : data.getLr_date(), null);
				colCount++;

				cell = ReportFormats.cell(row, colCount,
						data.getTransporter_Name() == null ? "" : data.getTransporter_Name(), null);
				colCount++;

				cell = ReportFormats.cell(row, colCount, data.getDivision_name() == null ? "" : data.getDivision_name(),
						null);
				colCount++;

				cell = ReportFormats.cell(row, colCount, data.getSuppliercode() == null ? "" : data.getSuppliercode(),
						null);
				colCount++;

				cell = ReportFormats.cell(row, colCount, data.getPo_no() == null ? "" : data.getPo_no(), null);
				colCount++;

				cell = ReportFormats.cell(row, colCount, data.getPo_date() == null ? "" : data.getPo_date(), null);
				colCount++;

				cell = ReportFormats.cell(row, colCount, data.getProduct_name() == null ? "" : data.getProduct_name(),
						null);
				colCount++;

				cell = ReportFormats.cell(row, colCount, data.getBatch_no() == null ? "" : data.getBatch_no(), null);
				colCount++;

				cell = ReportFormats.cell(row, colCount,
						data.getBatch_expiry_date() == null ? "" : data.getBatch_expiry_date(), null);
				colCount++;

				cell = ReportFormats.cell(row, colCount,
						data.getGrn_qty() == null ? "" : data.getGrn_qty().setScale(2, RoundingMode.HALF_UP).toString(),
						cellAlignment);
				colCount++;

				cell = ReportFormats.cell(row, colCount, data.getGrn_rate() == null ? ""
						: data.getGrn_rate().setScale(2, RoundingMode.HALF_UP).toString(), cellAlignment);
				colCount++;

				cell = ReportFormats.cell(row, colCount,
						data.getValue() == null ? "" : data.getValue().setScale(2, RoundingMode.HALF_UP).toString(),
						cellAlignment);
				colCount++;

				cell = ReportFormats.cell(row, colCount, data.getProduct_type() == null ? "" : data.getProduct_type(),
						null);
				colCount++;

				cell = ReportFormats.cell(row, colCount, data.getProduct_code() == null ? "" : data.getProduct_code(),
						null);
				colCount++;

				cell = ReportFormats.cell(row, colCount, data.getFcode() == null ? "" : data.getFcode(), null);
				colCount++;

				cell = ReportFormats.cell(row, colCount, data.getGcma_req_ind() == null ? "" : data.getGcma_req_ind(),
						null);
				colCount++;

				cell = ReportFormats.cell(row, colCount, data.getGcma_number() == null ? "" : data.getGcma_number(),
						null);
				colCount++;

				cell = ReportFormats.cell(row, colCount,
						data.getGcma_expiry_dt() == null ? ""
								: data.getGcma_expiry_dt() == "" ? ""
										: Utility.convertStringtoStringDate(data.getGcma_expiry_dt()),
						null);
				colCount++;

				cell = ReportFormats.cell(row, colCount, data.getMfg_dt() == null ? "" : data.getMfg_dt(), null);
				colCount++;

				cell = ReportFormats.cell(row, colCount, data.getBin_number() == null ? "" : data.getBin_number(),
						cellAlignment);
				colCount++;

				cell = ReportFormats.cell(row, colCount, data.getRemark() == null ? "" : data.getRemark(), null);
				colCount++;

				rowCount++;
				colCount = 0;
			}

			FileOutputStream fileOutputStream = new FileOutputStream(filepath);
			workbook.write(fileOutputStream);
			filename = usermasterservice.generateExcellock(filepath, filename, empId, ".xlsx");

		} finally {
			if (workbook != null) {
				workbook.close();
			}
		}
		return filename;
	}

	@Override
	public String generateGRNSummaryReport_VET(List<ViewGRNSummary_GST_VET> list, @Valid ReportBean bean,
			String companyname, String empId) throws Exception {
		String filename = "GRNSummaryReport" + new Date().getTime() + ".xlsx";
		String filepath = REPORT_FILE_PATH + filename;
		XSSFWorkbook workbook = null;
		try {
			String grnRefNoInd = this.paramService.getGrnRefNoInd();
			workbook = new XSSFWorkbook();
			File f = new File(REPORT_FILE_PATH);
			if (!f.exists()) {
				if (f.mkdirs()) {
				} else {
					throw new RuntimeException("Could not create directory");
				}
			}
			XSSFSheet sheet = workbook.createSheet("GRN Summary Report");

			XSSFRow row = null;
			XSSFCell cell = null;
			int rowCount = 0;
			int colCount = 0;

			List<String> headings = new ArrayList<>();
			headings.add("GRN No.");
//			headings.add("GRN Entered By");
			headings.add("Location");
			headings.add("Supplier Code");
			headings.add("Supplier Name / Received from");
			headings.add("GSTIN No.");
			headings.add("State");
			headings.add("State Code");
			headings.add("GRN Date");
			headings.add("Transfer No.");
			headings.add("Transfer Date");
			headings.add("LR No.");
			headings.add("LR Date");
			headings.add("Value (Rs)");
			headings.add("Lorry_No / Transporter Name");
			headings.add("Division Name");
			headings.add("PO No.");
			headings.add("PO Date");
			headings.add("Additional Charges");
			headings.add("Amount");
			headings.add("Discount");
			headings.add("Amount");
			headings.add("SGST Amount");
			headings.add("CGST Amount");
			headings.add("IGST Amount");
			headings.add("RoundOff");
			headings.add("Net Amount");
			headings.add("REMARKS");

			if(grnRefNoInd != null && grnRefNoInd.equalsIgnoreCase("Y")) {
				if(bean.getCompcd().trim().equalsIgnoreCase("PFZ"))
					headings.add("IDOC/IBD No.");
				else
					headings.add("GRN Ref No.");
			}
			
			if(bean.getCompcd().trim().equalsIgnoreCase("PFZ")) {
				headings.add("VEHICLE RECEIVED TIME");
			}
			
			XSSFCellStyle headingCellStyle = ReportFormats.heading(workbook);
			XSSFCellStyle columnHeadingStyle = ReportFormats.columnHeading(workbook);
			XSSFCellStyle cellAlignment = ReportFormats.setCellAlignment(workbook, ALIGN_RIGHT);

			row = sheet.createRow(rowCount);
			cell = row.createCell(colCount);
			sheet.addMergedRegion(new CellRangeAddress(rowCount, rowCount, colCount, colCount + headings.size() - 1));
			cell.setCellValue("Pfizer India Ltd.");
			cell.setCellStyle(headingCellStyle);
			rowCount++;

			row = sheet.createRow(rowCount);
			cell = row.createCell(colCount);
			sheet.addMergedRegion(new CellRangeAddress(rowCount, rowCount, colCount, colCount + headings.size() - 1));
			cell.setCellValue("GRN Summary Report");
			cell.setCellStyle(headingCellStyle);
			rowCount++;

			row = sheet.createRow(rowCount);
			cell = row.createCell(colCount);
			sheet.addMergedRegion(new CellRangeAddress(rowCount, rowCount, colCount, colCount + headings.size() - 1));
			cell.setCellValue("Period: " + MedicoResources.convertUtilDateToString_DD_MM_YYYY_(bean.getStartDate())
					+ " to " + MedicoResources.convertUtilDateToString_DD_MM_YYYY_(bean.getEndDate()));
			cell.setCellStyle(headingCellStyle);

			rowCount += 1;
			row = sheet.createRow(rowCount);

			for (String heading : headings) {
				cell = ReportFormats.cell(row, colCount, heading, columnHeadingStyle);
				colCount++;
			}

			sheet.groupRow(0, 2);
			sheet.createFreezePane(0, 4);

			rowCount++;
			colCount = 0;

			BigDecimal goodsValueTotal = new BigDecimal(0);
			BigDecimal addChargesTotal = new BigDecimal(0);
			BigDecimal discountTotal = new BigDecimal(0);
			BigDecimal sgstTotal = new BigDecimal(0);
			BigDecimal cgstTotal = new BigDecimal(0);
			BigDecimal igstTotal = new BigDecimal(0);
			BigDecimal rounfOffTotal = new BigDecimal(0);
			BigDecimal finalTotal = new BigDecimal(0);

			BigDecimal goodsValueTotalGrand = new BigDecimal(0);
			BigDecimal addChargesTotalGrand = new BigDecimal(0);
			BigDecimal discountTotalGrand = new BigDecimal(0);
			BigDecimal sgstTotalGrand = new BigDecimal(0);
			BigDecimal cgstTotalGrand = new BigDecimal(0);
			BigDecimal igstTotalGrand = new BigDecimal(0);
			BigDecimal rounfOffTotalGrand = new BigDecimal(0);
			BigDecimal finalTotalGrand = new BigDecimal(0);

			String new_division = "";
			String old_division = "";

			int i = 0;
			
			LocalDate endDate = bean.getEndDate().toInstant()
                    .atZone(ZoneId.systemDefault())
                    .toLocalDate();

			LocalDate cutoffDate = LocalDate.of(2024, 12, 1);

			if(bean.getCompcd().trim().equalsIgnoreCase(PFZ) && endDate.isBefore(cutoffDate)) {
				list = list.stream().peek(data -> {
					if ("CITIUS".equalsIgnoreCase(data.getDivision_name())
							|| "CITIUS  (ALTIUS - NEW)".equalsIgnoreCase(data.getDivision_name())) {
						data.setDivision_name("ALTIUS - NEW");
					}
				}).collect(Collectors.toList());

			}

			for (ViewGRNSummary_GST_VET data : list) {
				BigDecimal netAmount = new BigDecimal(0);
				new_division = data.getDivision_name();

				if (!(new_division.equalsIgnoreCase(old_division))) {
					if (i != 0) {
						row = sheet.createRow(rowCount);
						cell = ReportFormats.cell(row, colCount, "Division Total", null);
						// sheet.addMergedRegion(new CellRangeAddress(rowCount, rowCount, colCount,
						// 11));
						colCount++;

						for (int x = 0; x <= 10; x++) {
							cell = ReportFormats.cell(row, colCount, "", cellAlignment);
							colCount++;
						}

						cell = row.createCell(12);
						cell = ReportFormats.cellNum(row, colCount,
								goodsValueTotal == null ? 0l : goodsValueTotal.doubleValue(), cellAlignment);
						goodsValueTotalGrand = goodsValueTotalGrand.add(goodsValueTotal);
						colCount++;

						for (int x = 0; x <= 4; x++) {
							cell = ReportFormats.cell(row, colCount, "", cellAlignment);
							colCount++;
						}

						cell = row.createCell(18);
						cell = ReportFormats.cellNum(row, colCount,
								addChargesTotal == null ? 0l : addChargesTotal.doubleValue(), cellAlignment);
						addChargesTotalGrand = addChargesTotalGrand.add(addChargesTotal);
						// sheet.addMergedRegion(new CellRangeAddress(rowCount, rowCount, 13, 18));
						colCount++;

						cell = row.createCell(19);
						cell = ReportFormats.cell(row, colCount, "", cellAlignment);
						colCount++;

						cell = row.createCell(20);
						cell = ReportFormats.cellNum(row, colCount,
								discountTotal == null ? 0l : discountTotal.doubleValue(), cellAlignment);
						discountTotalGrand = discountTotalGrand.add(discountTotal);
						// sheet.addMergedRegion(new CellRangeAddress(rowCount, rowCount, 19, 20));
						colCount++;

						cell = row.createCell(21);
						cell = ReportFormats.cellNum(row, colCount, sgstTotal == null ? 0l : sgstTotal.doubleValue(),
								cellAlignment);
						sgstTotalGrand = sgstTotalGrand.add(sgstTotal);
						colCount++;

						cell = row.createCell(22);
						cell = ReportFormats.cellNum(row, colCount, cgstTotal == null ? 0l : cgstTotal.doubleValue(),
								cellAlignment);
						cgstTotalGrand = cgstTotalGrand.add(cgstTotal);
						colCount++;

						cell = row.createCell(23);
						cell = ReportFormats.cellNum(row, colCount, igstTotal == null ? 0l : igstTotal.doubleValue(),
								cellAlignment);
						igstTotalGrand = igstTotalGrand.add(igstTotal);
						colCount++;

						cell = row.createCell(24);
						cell = ReportFormats.cellNum(row, colCount, igstTotal == null ? 0l : igstTotal.doubleValue(),
								cellAlignment);
						rounfOffTotalGrand = rounfOffTotalGrand.add(rounfOffTotal);
						colCount++;

						cell = row.createCell(25);
						cell = ReportFormats.cellNum(row, colCount, finalTotal == null ? 0l : finalTotal.doubleValue(),
								cellAlignment);
						finalTotalGrand = finalTotalGrand.add(finalTotal);
						colCount++;

						goodsValueTotal = new BigDecimal(0);
						addChargesTotal = new BigDecimal(0);
						discountTotal = new BigDecimal(0);
						sgstTotal = new BigDecimal(0);
						cgstTotal = new BigDecimal(0);
						igstTotal = new BigDecimal(0);
						rounfOffTotal = new BigDecimal(0);
						finalTotal = new BigDecimal(0);

						rowCount++;
						colCount = 0;
					}

					row = sheet.createRow(rowCount);
					cell = ReportFormats.cell(row, colCount, "Division : " + new_division, null);
					// sheet.addMergedRegion(new CellRangeAddress(rowCount, rowCount, colCount,
					// 20));

					rowCount++;
					colCount = 0;
				}

				row = sheet.createRow(rowCount);
				/*
				 * cell = ReportFormats.cell(row, colCount, data.getRow().toString(),
				 * cellAlignment); colCount++;
				 */

				cell = ReportFormats.cell(row, colCount, data.getGrn_no(), null);
				colCount++;
				
//				cell = ReportFormats.cell(row, colCount, data.getGrn_entered_by(), null);
//				colCount++;

				cell = ReportFormats.cell(row, colCount, data.getSending_Location(), null);
				colCount++;

				cell = ReportFormats.cell(row, colCount, data.getSuppliercode(), null);
				colCount++;

				cell = ReportFormats.cell(row, colCount, data.getSupplier(), null);
				colCount++;

				cell = ReportFormats.cell(row, colCount, data.getGst_no(), null);
				colCount++;

				cell = ReportFormats.cell(row, colCount, data.getState_name(), null);
				colCount++;

				cell = ReportFormats.cell(row, colCount, data.getState_code(), null);
				colCount++;

				cell = ReportFormats.cell(row, colCount, data.getGrn_date(), null);
				colCount++;

				cell = ReportFormats.cell(row, colCount, data.getTransfer_no(), null);
				colCount++;

				cell = ReportFormats.cell(row, colCount, data.getTransfer_date(), null);
				colCount++;

				cell = ReportFormats.cell(row, colCount, data.getLr_no(), cellAlignment);
				colCount++;

				cell = ReportFormats.cell(row, colCount, data.getLr_date(), null);
				colCount++;

				cell = ReportFormats.cellNum(row, colCount,
						data.getGrnvalue() == null ? 0l : data.getGrnvalue().doubleValue(), null);
				goodsValueTotal = goodsValueTotal.add(data.getGrnvalue());
				colCount++;

				cell = ReportFormats.cell(row, colCount, data.getTransporter_Name(), null);
				colCount++;

				cell = ReportFormats.cell(row, colCount, data.getDivision_name(), null);
				colCount++;

				cell = ReportFormats.cell(row, colCount, data.getPo_no(), null);
				colCount++;

				cell = ReportFormats.cell(row, colCount, data.getPo_date(), null);
				colCount++;

				cell = ReportFormats.cellNum(row, colCount, Double.valueOf("0.00"), null);
				colCount++;

				cell = ReportFormats.cellNum(row, colCount,
						data.getHvalue1() == null ? 0l : Double.valueOf("0.00"), null);
				addChargesTotal = addChargesTotal.add(data.getHvalue1());
				colCount++;

				cell = ReportFormats.cellNum(row, colCount, Double.valueOf("0.00"), null);
				colCount++;

				cell = ReportFormats.cellNum(row, colCount,
						data.getHvalue2() == null ? 0l : Double.valueOf("0.00"), null);
				discountTotal = discountTotal.add(data.getHvalue2());
				colCount++;

				cell = ReportFormats.cellNum(row, colCount,
						data.getHsgst_bill_amt() == null ? 0l : data.getHsgst_bill_amt().doubleValue(), null);
				sgstTotal = sgstTotal.add(data.getHsgst_bill_amt());
				colCount++;

				cell = ReportFormats.cellNum(row, colCount,
						data.getHcgst_bill_amt() == null ? 0l : data.getHcgst_bill_amt().doubleValue(), null);
				cgstTotal = cgstTotal.add(data.getHcgst_bill_amt());
				colCount++;

				cell = ReportFormats.cellNum(row, colCount,
						data.getHigst_bill_amt() == null ? 0l : data.getHigst_bill_amt().doubleValue(), null);
				igstTotal = igstTotal.add(data.getHigst_bill_amt());
				colCount++;

				cell = ReportFormats.cellNum(row, colCount,
						data.getHroundoff() == null ? 0l : data.getHroundoff().doubleValue(), null);
				rounfOffTotal = rounfOffTotal.add(data.getHroundoff());
				colCount++;

				netAmount = data.getGrnvalue().add(data.getHvalue1().add(data.getHvalue2().add(data.getHsgst_bill_amt()
						.add(data.getHcgst_bill_amt().add(data.getHigst_bill_amt().add(data.getHroundoff()))))));
				finalTotal = finalTotal.add(netAmount);

				cell = ReportFormats.cellNum(row, colCount, netAmount == null ? 0l : netAmount.doubleValue(), null);
				colCount++;
				
				cell = ReportFormats.cell(row, colCount, data.getRemarks(), null);
				colCount++;
				
				if(grnRefNoInd != null && grnRefNoInd.equalsIgnoreCase("Y")) {
					cell = ReportFormats.cell(row, colCount, data.getGrn_ref_no(), null);
					colCount++;
				}
				
				if (bean.getCompcd().trim().equalsIgnoreCase("PFZ")) {
					if (data.getVehicle_recd_time() == null || data.getVehicle_recd_time() == "" || data.getVehicle_recd_time().startsWith("1900")) {
						cell = ReportFormats.cell(row, colCount, "", null);
					} else {DateTimeFormatter inputFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.S");
			        DateTimeFormatter outputFormatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm");
			        LocalDateTime dateTime = LocalDateTime.parse(data.getVehicle_recd_time(), inputFormatter);
			        String formattedDate = dateTime.format(outputFormatter);

			        cell = ReportFormats.cell(row, colCount, formattedDate, null);}
					colCount++;
				}

				colCount = 0;
				rowCount++;

				if (i == list.size() - 1) {

					row = sheet.createRow(rowCount);
					cell = ReportFormats.cell(row, colCount, "Division Total", null);
					colCount++;

					for (int x = 0; x <= 10; x++) {
						cell = ReportFormats.cell(row, colCount, "", cellAlignment);
						colCount++;
					}

					cell = row.createCell(12);
					cell = ReportFormats.cellNum(row, colCount,
							goodsValueTotal == null ? 0l : goodsValueTotal.doubleValue(), cellAlignment);
					goodsValueTotalGrand = goodsValueTotalGrand.add(goodsValueTotal);
					colCount++;

					for (int x = 0; x <= 4; x++) {
						cell = ReportFormats.cell(row, colCount, "", cellAlignment);
						colCount++;
					}

					cell = row.createCell(18);
					cell = ReportFormats.cellNum(row, colCount,
							addChargesTotal == null ? 0l : addChargesTotal.doubleValue(), cellAlignment);
					addChargesTotalGrand = addChargesTotalGrand.add(addChargesTotal);
					// sheet.addMergedRegion(new CellRangeAddress(rowCount,rowCount, 13, 18));
					colCount++;

					cell = row.createCell(19);
					cell = ReportFormats.cell(row, colCount, "", cellAlignment);
					colCount++;

					cell = row.createCell(20);
					cell = ReportFormats.cellNum(row, colCount,
							discountTotal == null ? 0l : discountTotal.doubleValue(), cellAlignment);
					discountTotalGrand = discountTotalGrand.add(discountTotal);
					// sheet.addMergedRegion(new CellRangeAddress(rowCount, rowCount, 19, 20));
					colCount++;

					cell = row.createCell(21);
					cell = ReportFormats.cellNum(row, colCount, sgstTotal == null ? 0l : sgstTotal.doubleValue(),
							cellAlignment);
					sgstTotalGrand = sgstTotalGrand.add(sgstTotal);
					colCount++;

					cell = row.createCell(22);
					cell = ReportFormats.cellNum(row, colCount, cgstTotal == null ? 0l : cgstTotal.doubleValue(),
							cellAlignment);
					cgstTotalGrand = cgstTotalGrand.add(cgstTotal);
					colCount++;

					cell = row.createCell(23);
					cell = ReportFormats.cellNum(row, colCount, igstTotal == null ? 0l : igstTotal.doubleValue(),
							cellAlignment);
					igstTotalGrand = igstTotalGrand.add(igstTotal);
					colCount++;

					cell = row.createCell(24);
					cell = ReportFormats.cellNum(row, colCount,
							rounfOffTotal == null ? 0l : rounfOffTotal.doubleValue(), cellAlignment);
					rounfOffTotalGrand = rounfOffTotalGrand.add(rounfOffTotal);
					colCount++;

					cell = row.createCell(25);
					cell = ReportFormats.cellNum(row, colCount, finalTotal == null ? 0l : finalTotal.doubleValue(),
							cellAlignment);
					finalTotalGrand = finalTotalGrand.add(finalTotal);

					goodsValueTotal = new BigDecimal(0);
					addChargesTotal = new BigDecimal(0);
					discountTotal = new BigDecimal(0);
					sgstTotal = new BigDecimal(0);
					cgstTotal = new BigDecimal(0);
					igstTotal = new BigDecimal(0);
					rounfOffTotal = new BigDecimal(0);
					finalTotal = new BigDecimal(0);

					rowCount++;
					colCount = 0;

					row = sheet.createRow(rowCount);

					cell = ReportFormats.cell(row, colCount, "Grand Total", null);
					// sheet.addMergedRegion(new CellRangeAddress(rowCount, rowCount, colCount,
					// 11));
					colCount++;

					for (int x = 0; x <= 10; x++) {
						cell = ReportFormats.cell(row, colCount, "", cellAlignment);
						colCount++;
					}

					cell = row.createCell(12);
					cell = ReportFormats.cellNum(row, colCount,
							goodsValueTotalGrand == null ? 0l : goodsValueTotalGrand.doubleValue(), cellAlignment);
					colCount++;

					for (int x = 0; x <= 4; x++) {
						cell = ReportFormats.cell(row, colCount, "", cellAlignment);
						colCount++;
					}

					cell = row.createCell(18);
					cell = ReportFormats.cellNum(row, colCount,
							addChargesTotalGrand == null ? 0l : addChargesTotalGrand.doubleValue(), cellAlignment);
					// sheet.addMergedRegion(new CellRangeAddress(rowCount, rowCount, 13, 18));
					colCount++;

					cell = row.createCell(19);
					cell = ReportFormats.cell(row, colCount, "", cellAlignment);
					colCount++;

					cell = row.createCell(20);
					cell = ReportFormats.cellNum(row, colCount,
							discountTotalGrand == null ? 0l : discountTotalGrand.doubleValue(), cellAlignment);
					// sheet.addMergedRegion(new CellRangeAddress(rowCount, rowCount, 19, 20));
					colCount++;

					cell = row.createCell(21);
					cell = ReportFormats.cellNum(row, colCount,
							sgstTotalGrand == null ? 0l : sgstTotalGrand.doubleValue(), cellAlignment);
					colCount++;
					// cell = ReportFormats.cell(row, colCount, sgstTotalGrand.toString(),
					// cellAlignment);

					cell = row.createCell(22);
					cell = ReportFormats.cellNum(row, colCount,
							cgstTotalGrand == null ? 0l : cgstTotalGrand.doubleValue(), cellAlignment);
					colCount++;

					cell = row.createCell(23);
					cell = ReportFormats.cellNum(row, colCount,
							igstTotalGrand == null ? 0l : igstTotalGrand.doubleValue(), cellAlignment);
					colCount++;

					cell = row.createCell(24);
					cell = ReportFormats.cellNum(row, colCount,
							rounfOffTotalGrand == null ? 0l : rounfOffTotalGrand.doubleValue(), cellAlignment);
					colCount++;

					cell = row.createCell(25);
					cell = ReportFormats.cellNum(row, colCount,
							finalTotalGrand == null ? 0l : finalTotalGrand.doubleValue(), cellAlignment);
					colCount++;
				}
				i++;
				old_division = new_division;
			}
			FileOutputStream fileOutputStream = new FileOutputStream(filepath);
			workbook.write(fileOutputStream);

			filename = usermasterservice.generateExcellock(filepath, filename, empId, ".xlsx");

		} finally {
			if (workbook != null) {
				workbook.close();
			}
		}
		return filename;
	}

	@Override
	public String generateGRNSummary_VET(List<ViewGRNSummary_VET> list, ReportBean bean, String cfa_to_stk,
			String companyCode, String companyname, String empId) throws Exception {

		String nameonly = "GRNSummaryReport" + new Date().getTime();
		String filename = "GRNSummaryReport" + new Date().getTime() + ".xlsx";
		String filepath = REPORT_FILE_PATH + filename;
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
			XSSFSheet sheet = workbook.createSheet("GRN summary Report");

			XSSFRow row = null;
			XSSFCell cell = null;
			int rowCount = 0;
			int colCount = 0;

			String[] 	 headings = new String[]  { "GRN No.", "Location", "Supplier", "GRN Date", "Transfer No", "Transfer Date",
							"L.R No", "L.R Date", "Value (Rs.Ps)", "Lorry_No / Transporter Name", "Division Name",
							"Supplier Code", "PO No.", "PO Date","IDOC/IBD No"};
			
			XSSFCellStyle headingCellStyle = ReportFormats.heading(workbook);
			XSSFCellStyle columnHeadingStyle = ReportFormats.columnHeading(workbook);
			XSSFCellStyle cellAlignment = ReportFormats.setCellAlignment(workbook, ALIGN_RIGHT);

			row = sheet.createRow(rowCount);
			cell = row.createCell(colCount);
			sheet.addMergedRegion(new CellRangeAddress(rowCount, rowCount, colCount, colCount + headings.length - 1));
			cell.setCellValue(companyname);
			cell.setCellStyle(headingCellStyle);
			rowCount++;

			row = sheet.createRow(rowCount);
			cell = row.createCell(colCount);
			sheet.addMergedRegion(new CellRangeAddress(rowCount, rowCount, colCount, colCount + headings.length - 1));
			cell.setCellValue("GRN Summary Report");
			cell.setCellStyle(headingCellStyle);
			rowCount++;

			row = sheet.createRow(rowCount);
			cell = row.createCell(colCount);
			sheet.addMergedRegion(new CellRangeAddress(rowCount, rowCount, colCount, colCount + headings.length - 1));
			cell.setCellValue("Period: " + MedicoResources.convertUtilDateToString_DD_MM_YYYY_(bean.getStartDate())
					+ " to " + MedicoResources.convertUtilDateToString_DD_MM_YYYY_(bean.getEndDate()));
			cell.setCellStyle(headingCellStyle);

			rowCount += 1;
			row = sheet.createRow(rowCount);

			for (String heading : headings) {
				cell = ReportFormats.cell(row, colCount, heading, columnHeadingStyle);
				colCount++;
			}

			sheet.groupRow(0, 2);
			sheet.createFreezePane(0, 4);

			rowCount++;
			colCount = 0;
			
			LocalDate endDate = bean.getEndDate().toInstant()
                    .atZone(ZoneId.systemDefault())
                    .toLocalDate();

			LocalDate cutoffDate = LocalDate.of(2024, 12, 1);

			if (bean.getCompcd().trim().equalsIgnoreCase(PFZ) && endDate.isBefore(cutoffDate)) {
				list = list.stream().peek(data -> {
					if ("CITIUS".equalsIgnoreCase(data.getDivision_name().trim()) || "CITIUS  (ALTIUS - NEW)".equalsIgnoreCase(data.getDivision_name().trim())) {
						data.setDivision_name("ALTIUS - NEW");
					}
				}).collect(Collectors.toList());
			}

			for (ViewGRNSummary_VET data : list) {

				row = sheet.createRow(rowCount);
				cell = ReportFormats.cell(row, colCount, data.getGrn_no() == null ? "" : data.getGrn_no(), null);
				colCount++;
				
//				cell = ReportFormats.cell(row, colCount, data.getGrn_entered_by() == null  ? "" : data.getGrn_entered_by(), null);
//				colCount++;


				cell = ReportFormats.cell(row, colCount,
						data.getSending_Location() == null ? "" : data.getSending_Location(), null);
				colCount++;

				cell = ReportFormats.cell(row, colCount, data.getSupplier() == null ? "" : data.getSupplier(), null);
				colCount++;

				cell = ReportFormats.cell(row, colCount, data.getGrn_date() == null ? "" : data.getGrn_date(), null);
				colCount++;

				cell = ReportFormats.cell(row, colCount, data.getTransfer_no() == null ? "" : data.getTransfer_no(),
						null);
				colCount++;

				cell = ReportFormats.cell(row, colCount, data.getTransfer_date() == null ? "" : data.getTransfer_date(),
						null);
				colCount++;

				cell = ReportFormats.cell(row, colCount, data.getLr_no() == null ? "" : data.getLr_no(), null);
				colCount++;

				cell = ReportFormats.cell(row, colCount, data.getLr_date() == null ? "" : data.getLr_date(), null);
				colCount++;

				cell = ReportFormats.cell(row, colCount,
						data.getValue() == null ? "" : data.getValue().setScale(2, RoundingMode.HALF_UP).toString(),
						cellAlignment);
				colCount++;

				cell = ReportFormats.cell(row, colCount,
						data.getTransporter_Name() == null ? "" : data.getTransporter_Name(), null);
				colCount++;

				cell = ReportFormats.cell(row, colCount, data.getDivision_name() == null ? "" : data.getDivision_name(),
						null);
				colCount++;

				cell = ReportFormats.cell(row, colCount, data.getSuppliercode() == null ? "" : data.getSuppliercode(),
						null);
				colCount++;

				cell = ReportFormats.cell(row, colCount, data.getPo_no() == null ? "" : data.getPo_no(), null);
				colCount++;

				cell = ReportFormats.cell(row, colCount, data.getPo_date() == null ? "" : data.getPo_date(), null);
				colCount++;
				
		
				
				

				rowCount++;
				colCount = 0;
			}

			FileOutputStream fileOutputStream = new FileOutputStream(filepath);
			workbook.write(fileOutputStream);

			filename = usermasterservice.generateExcellock(filepath, filename, empId, ".xlsx");

		} finally {
			if (workbook != null) {
				workbook.close();
			}
		}
		return filename;
	}

	@Override
	public String generateGRNDetailReport_SG(List<ViewGRNDetail_GST> list, @Valid ReportBean bean,
			String companyname, String empId, boolean b) throws Exception {
		
        String filename = "GRNDetailReport" + new Date().getTime()+ ".xlsx";
        String filepath = REPORT_FILE_PATH + filename ;
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
                XSSFSheet sheet = workbook.createSheet("GRN Detail Report");

                XSSFRow row = null;
                XSSFCell cell = null;
                int rowCount = 0;
                int colCount = 0;

                String headings[] = { "GRN Date.", "GRN No.", "GRN Type", "Supplier Name", "GSTIN", "State", "State Code",
                                "Place of Supply", "Supply Doc Type", "Supply Doc NO", "Supply Doc Date", "LR No", "LR Date",
                                "Product", "Pack", "HSN Code", "Batch No", "No. of Cases", "Rate", "Saleable", "Short Qty",
                                "Spoilt", "Total Grn Qty", "Goods Value", "CGST %", "CGST Value", "SGST %", "SGST Value", "IGST %",
                                "IGST Value", "Additional Charge", "Amount", "Discount", "Amount", "Total Value", "FCODE",
                                "GCMA Indicator", "GCMA CODE", "GCMA EXP DATE", "REMARK","Return From FieldStaff","Saleable/Non-Saleable"};

                XSSFCellStyle headingCellStyle = ReportFormats.heading(workbook);
                XSSFCellStyle columnHeadingStyle = ReportFormats.columnHeading(workbook);
                XSSFCellStyle cellAlignment = ReportFormats.setCellAlignment(workbook, ALIGN_RIGHT);
                XSSFCellStyle cellAlignment2 = ReportFormats.setCellAlignment(workbook, ALIGN_LEFT);

                row = sheet.createRow(rowCount);
                cell = row.createCell(colCount);
                sheet.addMergedRegion(new CellRangeAddress(rowCount, rowCount, colCount, colCount + headings.length - 1));
                cell.setCellValue(companyname);
                cell.setCellStyle(headingCellStyle);
                rowCount++;

                row = sheet.createRow(rowCount);
                cell = row.createCell(colCount);
                sheet.addMergedRegion(new CellRangeAddress(rowCount, rowCount, colCount, colCount + headings.length - 1));
                cell.setCellValue("GRN Detail Report");
                cell.setCellStyle(headingCellStyle);
                rowCount++;

                row = sheet.createRow(rowCount);
                cell = row.createCell(colCount);
                sheet.addMergedRegion(new CellRangeAddress(rowCount, rowCount, colCount, colCount + headings.length - 1));
                cell.setCellValue("Period: " + MedicoResources.convertUtilDateToString_DD_MM_YYYY_(bean.getStartDate())
                                + " to " + MedicoResources.convertUtilDateToString_DD_MM_YYYY_(bean.getEndDate()));
                cell.setCellStyle(headingCellStyle);

                rowCount += 1;
                row = sheet.createRow(rowCount);

                for (String heading : headings) {
                        cell = ReportFormats.cell(row, colCount, heading, columnHeadingStyle);
                        colCount++;
                }

                sheet.groupRow(0, 2);
                sheet.createFreezePane(0, 4);

                String newDivision = "";
                String oldDivision = "";
                // String newGrnDate = "";
                // String oldGrnDate = "";
                // String newGrnNo = "";
                // String oldGrnNo = "";

                BigDecimal totalValue = new BigDecimal(0);
                BigDecimal divisionTotal[] = new BigDecimal[7];
                BigDecimal reportTotal[] = new BigDecimal[7];

                // Arrays.fill(totalValue, BigDecimal.ZERO);
                Arrays.fill(divisionTotal, BigDecimal.ZERO);
                Arrays.fill(reportTotal, BigDecimal.ZERO);
                
                LocalDate endDate = bean.getEndDate().toInstant()
                        .atZone(ZoneId.systemDefault())
                        .toLocalDate();

    			LocalDate cutoffDate = LocalDate.of(2024, 12, 1);

    			if(bean.getCompcd().trim().equalsIgnoreCase(PFZ) && endDate.isBefore(cutoffDate)) {
    				list = list.stream().peek(data -> {
    					if ("CITIUS".equalsIgnoreCase(data.getDivision_name())
    							|| "CITIUS  (ALTIUS - NEW)".equalsIgnoreCase(data.getDivision_name())) {
    						data.setDivision_name("ALTIUS - NEW");
    					}
    				}).collect(Collectors.toList());

    			}

                for (ViewGRNDetail_GST o : list) {

                        colCount = 0;
                        rowCount++;

                        newDivision = o.getDivision_name();
                        if (!(newDivision.equalsIgnoreCase(oldDivision)) && !oldDivision.equalsIgnoreCase("")) {

                                row = sheet.createRow(rowCount);

                                cell = ReportFormats.cell(row, colCount, "Division Total".toString(), null);
                                colCount++;

                                for (int i = 0; i <= 18; i++) {
                                        cell = ReportFormats.cell(row, colCount, "", null);
                                        colCount++;
                                }

                                cell = row.createCell(21);
                                cell = ReportFormats.cellNum(row, colCount,
                                                divisionTotal[0] == null ? 0l : divisionTotal[0].doubleValue(), cellAlignment);
                                colCount++;

                                cell = row.createCell(22);
                                cell = ReportFormats.cell(row, colCount, "", cellAlignment);
                                colCount++;

                                cell = row.createCell(23);
                                cell = ReportFormats.cellNum(row, colCount,
                                                divisionTotal[1] == null ? 0l : divisionTotal[1].doubleValue(), cellAlignment);
                                colCount++;

                                cell = row.createCell(24);
                                cell = ReportFormats.cell(row, colCount, "", cellAlignment);
                                colCount++;

                                cell = row.createCell(25);
                                cell = ReportFormats.cellNum(row, colCount,
                                                divisionTotal[2] == null ? 0l : divisionTotal[2].doubleValue(), cellAlignment);
                                colCount++;

                                cell = row.createCell(26);
                                cell = ReportFormats.cell(row, colCount, "", cellAlignment);
                                colCount++;

                                cell = row.createCell(27);
                                cell = ReportFormats.cellNum(row, colCount,
                                                divisionTotal[3] == null ? 0l : divisionTotal[3].doubleValue(), cellAlignment);
                                colCount++;

                                cell = row.createCell(28);
                                cell = ReportFormats.cell(row, colCount, "", cellAlignment);
                                colCount++;

                                cell = row.createCell(29);
                                cell = ReportFormats.cellNum(row, colCount,
                                                divisionTotal[4] == null ? 0l : divisionTotal[4].doubleValue(), cellAlignment);
                                colCount++;

                                cell = row.createCell(30);
                                cell = ReportFormats.cell(row, colCount, "", cellAlignment);
                                colCount++;

                                cell = row.createCell(31);
                                cell = ReportFormats.cellNum(row, colCount,
                                                divisionTotal[5] == null ? 0l : divisionTotal[5].doubleValue(), cellAlignment);
                                colCount++;

                                cell = row.createCell(32);
                                cell = ReportFormats.cellNum(row, colCount,
                                                divisionTotal[6] == null ? 0l : divisionTotal[6].doubleValue(), cellAlignment);
                                colCount++;

                                Arrays.fill(divisionTotal, BigDecimal.ZERO);
                                colCount = 0;
                                rowCount++;
                        }

                        if (!(newDivision.equalsIgnoreCase(oldDivision))) {
                                colCount = 0;
                                row = sheet.createRow(rowCount);
                                cell = ReportFormats.cell(row, colCount, "Division : " + o.getDivision_name(), null);
                                colCount = 0;
                                rowCount++;

                        }

                        row = sheet.createRow(rowCount);
                        cell = ReportFormats.cell(row, colCount, o.getGrn_date(), null);
                        colCount++;

                        cell = ReportFormats.cell(row, colCount, o.getGrn_no(), null);
                        colCount++;

                        cell = ReportFormats.cell(row, colCount, o.getGrn_type(), null);
                        colCount++;

                        cell = ReportFormats.cell(row, colCount, o.getSupplier(), null);
                        colCount++;

                        cell = ReportFormats.cell(row, colCount, o.getHgst_reg_no(), null);
                        colCount++;

                        cell = ReportFormats.cell(row, colCount, o.getHstate_name(), null);
                        colCount++;

                        cell = ReportFormats.cell(row, colCount, o.getHstate_code(), null);
                        colCount++;

                        cell = ReportFormats.cell(row, colCount, o.getSending_Location(), null);
                        colCount++;

                        cell = ReportFormats.cell(row, colCount, o.getSupply_doc_type(), null);
                        colCount++;

                        cell = ReportFormats.cell(row, colCount, o.getTransfer_no(), null);
                        colCount++;

                        cell = ReportFormats.cell(row, colCount, o.getTransfer_date(), null);
                        colCount++;

                        cell = ReportFormats.cell(row, colCount, o.getLr_no(), null);
                        colCount++;

                        cell = ReportFormats.cell(row, colCount, o.getLr_date(), null);
                        colCount++;

                        cell = ReportFormats.cell(row, colCount, o.getProduct_name(), null);
                        colCount++;

                        cell = ReportFormats.cell(row, colCount, o.getPacking(), null);
                        colCount++;

                        cell = ReportFormats.cell(row, colCount, o.getHsncode(), null);
                        colCount++;

                        cell = ReportFormats.cell(row, colCount, o.getBatch_no(), null);
                        colCount++;

                        cell = ReportFormats.cell(row, colCount, o.getBin_number(), cellAlignment); // cess
                        colCount++;

                        cell = ReportFormats.cellNum(row, colCount,
                                        o.getGrn_rate() == null ? 0l : o.getGrn_rate().doubleValue(), cellAlignment);
                        colCount++;
                        //
                        cell = ReportFormats.cellNum(row, colCount, o.getGrn_qty() == null ? 0l : o.getGrn_qty().doubleValue(),
                                        cellAlignment);
                        colCount++;

                        cell = ReportFormats.cellNum(row, colCount,
                                        o.getGrn_qty() == null ? 0l : o.getShort_qty().doubleValue(), cellAlignment);
                        colCount++;

                        cell = ReportFormats.cellNum(row, colCount,
                                        o.getGrn_qty() == null ? 0l : o.getDamage_qty().doubleValue(), cellAlignment);
                        colCount++;

                        cell = ReportFormats.cellNum(row, colCount,
                                        o.getGrn_qty() == null ? 0l : o.getTotal_grn_qty().doubleValue(), cellAlignment);
                        colCount++;

                        cell = ReportFormats.cellNum(row, colCount, o.getValue() == null ? 0l : o.getValue().doubleValue(),
                                        cellAlignment);
                        colCount++;

                        cell = ReportFormats.cellNum(row, colCount,
                                        o.getCgst_rate() == null ? 0l : o.getCgst_rate().doubleValue(), cellAlignment);
                        colCount++;

                        cell = ReportFormats.cellNum(row, colCount,
                                        o.getCgst_bill_amt() == null ? 0l : o.getCgst_bill_amt().doubleValue(), cellAlignment);
                        colCount++;

                        cell = ReportFormats.cellNum(row, colCount,
                                        o.getSgst_rate() == null ? 0l : o.getSgst_rate().doubleValue(), cellAlignment);
                        colCount++;

                        cell = ReportFormats.cellNum(row, colCount,
                                        o.getSgst_bill_amt() == null ? 0l : o.getSgst_bill_amt().doubleValue(), cellAlignment);
                        colCount++;

                        cell = ReportFormats.cellNum(row, colCount,
                                        o.getIgst_rate() == null ? 0l : o.getIgst_rate().doubleValue(), cellAlignment);
                        colCount++;

                        cell = ReportFormats.cellNum(row, colCount,
                                        o.getIgst_bill_amt() == null ? 0l : o.getIgst_bill_amt().doubleValue(), cellAlignment);
                        colCount++;

                        cell = ReportFormats.cell(row, colCount, o.getText1(), null);
                        colCount++;

                        cell = ReportFormats.cellNum(row, colCount, o.getValue1() == null ? 0l : o.getValue1().doubleValue(),
                                        cellAlignment);
                        colCount++;

                        cell = ReportFormats.cell(row, colCount, o.getText2(), null);
                        colCount++;

                        cell = ReportFormats.cellNum(row, colCount, o.getValue2() == null ? 0l : o.getValue2().doubleValue(),
                                        cellAlignment);
                        colCount++;

                        totalValue = totalValue.add(o.getValue().add(o.getCgst_bill_amt()
                                        .add(o.getSgst_bill_amt().add(o.getIgst_bill_amt().add(o.getValue1().add(o.getValue2()))))));

                        cell = ReportFormats.cellNum(row, colCount, totalValue == null ? 0l : totalValue.doubleValue(), null);
                        colCount++;

                        cell = ReportFormats.cell(row, colCount, o.getFcode(), null);
                        colCount++;

                        cell = ReportFormats.cell(row, colCount, o.getGcma_req_ind(), null);
                        colCount++;

                        cell = ReportFormats.cell(row, colCount, o.getGcma_number(), null);
                        colCount++;

                        cell = ReportFormats.cell(row, colCount,
                                        o.getGcma_expiry_dt() == null ? "" : Utility.convertStringtoStringDate(o.getGcma_expiry_dt()),
                                        null);
                        colCount++;

                        cell = ReportFormats.cell(row, colCount, o.getRemark(), null);
                        colCount++;

                        cell = ReportFormats.cell(row, colCount,o.getReturn_from_fstaff_name(), null);
                        colCount++;
                        
                        cell = ReportFormats.cell(row, colCount,o.getSalable_nonsaleable(), null);
                        colCount++;
                        
                        oldDivision = newDivision;

                        divisionTotal[0] = divisionTotal[0].add(o.getValue() == null ? BigDecimal.ZERO : o.getValue());
                        divisionTotal[1] = divisionTotal[1]
                                        .add(o.getCgst_bill_amt() == null ? BigDecimal.ZERO : o.getCgst_bill_amt());
                        divisionTotal[2] = divisionTotal[2]
                                        .add(o.getSgst_bill_amt() == null ? BigDecimal.ZERO : o.getSgst_bill_amt());
                        divisionTotal[3] = divisionTotal[3]
                                        .add(o.getIgst_bill_amt() == null ? BigDecimal.ZERO : o.getIgst_bill_amt());
                        divisionTotal[4] = divisionTotal[4].add(o.getValue1() == null ? BigDecimal.ZERO : o.getValue1());
                        divisionTotal[5] = divisionTotal[5].add(o.getValue2() == null ? BigDecimal.ZERO : o.getValue2());
                        divisionTotal[6] = divisionTotal[6].add(totalValue == null ? BigDecimal.ZERO : totalValue);

                        reportTotal[0] = reportTotal[0].add(o.getValue() == null ? BigDecimal.ZERO : o.getValue());
                        reportTotal[1] = reportTotal[1]
                                        .add(o.getCgst_bill_amt() == null ? BigDecimal.ZERO : o.getCgst_bill_amt());
                        reportTotal[2] = reportTotal[2]
                                        .add(o.getSgst_bill_amt() == null ? BigDecimal.ZERO : o.getSgst_bill_amt());
                        reportTotal[3] = reportTotal[3]
                                        .add(o.getIgst_bill_amt() == null ? BigDecimal.ZERO : o.getIgst_bill_amt());
                        reportTotal[4] = reportTotal[4].add(o.getValue1() == null ? BigDecimal.ZERO : o.getValue1());
                        reportTotal[5] = reportTotal[5].add(o.getValue2() == null ? BigDecimal.ZERO : o.getValue2());
                        reportTotal[6] = reportTotal[6].add(totalValue == null ? BigDecimal.ZERO : totalValue);

                        totalValue = new BigDecimal(0);
                }

                rowCount++;
                colCount = 0;

                row = sheet.createRow(rowCount);

                cell = ReportFormats.cell(row, colCount, "Division Total", null);
                colCount++;

                for (int i = 0; i <= 18; i++) {
                        cell = ReportFormats.cell(row, colCount, "", null);
                        colCount++;
                }

                cell = row.createCell(21);
                cell = ReportFormats.cellNum(row, colCount, divisionTotal[0] == null ? 0l : divisionTotal[0].doubleValue(),
                                cellAlignment);
                colCount++;

                cell = row.createCell(22);
                cell = ReportFormats.cell(row, colCount, "", cellAlignment);
                colCount++;

                cell = row.createCell(23);
                cell = ReportFormats.cellNum(row, colCount, divisionTotal[1] == null ? 0l : divisionTotal[1].doubleValue(),
                                cellAlignment);
                colCount++;

                cell = row.createCell(24);
                cell = ReportFormats.cell(row, colCount, "", cellAlignment);
                colCount++;

                cell = row.createCell(25);
                cell = ReportFormats.cellNum(row, colCount, divisionTotal[2] == null ? 0l : divisionTotal[2].doubleValue(),
                                cellAlignment);
                colCount++;

                cell = row.createCell(26);
                cell = ReportFormats.cell(row, colCount, "", cellAlignment);
                colCount++;

                cell = row.createCell(27);
                cell = ReportFormats.cellNum(row, colCount, divisionTotal[3] == null ? 0l : divisionTotal[3].doubleValue(),
                                cellAlignment);
                colCount++;

                cell = row.createCell(28);
                cell = ReportFormats.cell(row, colCount, "", cellAlignment);
                colCount++;

                cell = row.createCell(29);
                cell = ReportFormats.cellNum(row, colCount, divisionTotal[4] == null ? 0l : divisionTotal[4].doubleValue(),
                                cellAlignment);
                colCount++;

                cell = row.createCell(30);
                cell = ReportFormats.cell(row, colCount, "", cellAlignment);
                colCount++;

                cell = row.createCell(31);
                cell = ReportFormats.cellNum(row, colCount, divisionTotal[5] == null ? 0l : divisionTotal[5].doubleValue(),
                                cellAlignment);
                colCount++;

                cell = row.createCell(32);
                cell = ReportFormats.cellNum(row, colCount, divisionTotal[6] == null ? 0l : divisionTotal[6].doubleValue(),
                                cellAlignment);
                colCount++;

                rowCount++;
                colCount = 0;

                row = sheet.createRow(rowCount);

                cell = ReportFormats.cell(row, colCount, "Report Total", null);
                colCount++;

                for (int i = 0; i <= 21; i++) {
                        cell = ReportFormats.cell(row, colCount, "", null);
                        colCount++;
                }

                cell = row.createCell(23);
                cell = ReportFormats.cellNum(row, colCount, reportTotal[0] == null ? 0l : reportTotal[0].doubleValue(),
                                cellAlignment);
                colCount++;

                cell = row.createCell(24);
                cell = ReportFormats.cell(row, colCount, "", cellAlignment);
                colCount++;

                cell = row.createCell(25);
                cell = ReportFormats.cellNum(row, colCount, reportTotal[1] == null ? 0l : reportTotal[1].doubleValue(),
                                cellAlignment);
                colCount++;

                cell = row.createCell(26);
                cell = ReportFormats.cell(row, colCount, "", cellAlignment);
                colCount++;

                cell = row.createCell(27);
                cell = ReportFormats.cellNum(row, colCount, reportTotal[2] == null ? 0l : reportTotal[2].doubleValue(),
                                cellAlignment);
                colCount++;

                cell = row.createCell(28);
                cell = ReportFormats.cell(row, colCount, "", cellAlignment);
                colCount++;

                cell = row.createCell(29);// igst
                cell = ReportFormats.cellNum(row, colCount, reportTotal[3] == null ? 0l : reportTotal[3].doubleValue(),
                                cellAlignment);
                colCount++;

                cell = row.createCell(30);
                cell = ReportFormats.cell(row, colCount, "", cellAlignment);
                colCount++;

                cell = row.createCell(31);
                cell = ReportFormats.cellNum(row, colCount, reportTotal[4] == null ? 0l : reportTotal[4].doubleValue(),
                                cellAlignment);
                colCount++;

                cell = row.createCell(32);
                cell = ReportFormats.cell(row, colCount, "", cellAlignment);
                colCount++;

                cell = row.createCell(33);
                cell = ReportFormats.cellNum(row, colCount, reportTotal[5] == null ? 0l : reportTotal[5].doubleValue(),
                                cellAlignment);
                colCount++;

                /*
                 * cell = row.createCell(34); cell = ReportFormats.cell(row, colCount, "",
                 * cellAlignment); colCount++;
                 */

                cell = row.createCell(34);
                cell = ReportFormats.cellNum(row, colCount, reportTotal[6] == null ? 0l : reportTotal[6].doubleValue(),
                                cellAlignment);
                colCount++;

                FileOutputStream fileOutputStream = new FileOutputStream(filepath);
                workbook.write(fileOutputStream);
                filename=usermasterservice.generateExcellock(filepath, filename,empId,".xlsx");

        } finally {
                if (workbook != null) {
                    workbook.close();
                }
        }
        return filename ;
}

	@Override
	public String generateGRNSummaryReport_VETO(List<ViewGRNSummary_GST> list, @Valid ReportBean bean,
			String companyname, String empId) throws Exception {
		String filename = "GRNSummaryReport" + new Date().getTime()+ ".xlsx";
		String filepath = REPORT_FILE_PATH + filename ;
		XSSFWorkbook workbook = null;
		try {
			String grnRefNoInd = this.paramService.getGrnRefNoInd();
			workbook = new XSSFWorkbook();
			File f = new File(REPORT_FILE_PATH);
			if (!f.exists()) {
				if (f.mkdirs()) {
				} else {
					throw new RuntimeException("Could not create directory");
				}
			}
			XSSFSheet sheet = workbook.createSheet("GRN Summary Report");

			XSSFRow row = null;
			XSSFCell cell = null;
			int rowCount = 0;
			int colCount = 0;

			List<String> headings = new ArrayList<>();
			headings.add("GRN No.");
			headings.add("Location");
			headings.add("Supplier Code");
			headings.add("Supplier Name / Received from");
			headings.add("GSTIN No.");
			headings.add("State");
			headings.add("State Code");
			headings.add("GRN Date");
			headings.add("Transfer No.");
			headings.add("Transfer Date");
			headings.add("LR No.");
			headings.add("LR Date");
			headings.add("Value (Rs)");
			headings.add("Lorry_No / Transporter Name");
			headings.add("Division Name");
			headings.add("PO No.");
			headings.add("PO Date");
			headings.add("Additional Charges");
			headings.add("Amount");
			headings.add("Discount");
			headings.add("Amount");
			headings.add("SGST Amount");
			headings.add("CGST Amount");
			headings.add("IGST Amount");
			headings.add("RoundOff");
			headings.add("Net Amount");
			headings.add("REMARKS");

			if(grnRefNoInd != null && grnRefNoInd.equalsIgnoreCase("Y")) {
				if(bean.getCompcd().trim().equalsIgnoreCase("PFZ"))
					headings.add("IDOC/IBD No.");
				else
					headings.add("GRN Ref No.");
			}

			XSSFCellStyle headingCellStyle = ReportFormats.heading(workbook);
			XSSFCellStyle columnHeadingStyle = ReportFormats.columnHeading(workbook);
			XSSFCellStyle cellAlignment = ReportFormats.setCellAlignment(workbook, ALIGN_RIGHT);

			row = sheet.createRow(rowCount);
			cell = row.createCell(colCount);
			sheet.addMergedRegion(new CellRangeAddress(rowCount, rowCount, colCount, colCount + headings.size() - 1));
			cell.setCellValue(companyname);
			cell.setCellStyle(headingCellStyle);
			rowCount++;

			row = sheet.createRow(rowCount);
			cell = row.createCell(colCount);
			sheet.addMergedRegion(new CellRangeAddress(rowCount, rowCount, colCount, colCount + headings.size() - 1));
			cell.setCellValue("GRN Summary Report");
			cell.setCellStyle(headingCellStyle);
			rowCount++;

			row = sheet.createRow(rowCount);
			cell = row.createCell(colCount);
			sheet.addMergedRegion(new CellRangeAddress(rowCount, rowCount, colCount, colCount + headings.size() - 1));
			cell.setCellValue("Period: " + MedicoResources.convertUtilDateToString_DD_MM_YYYY_(bean.getStartDate())
					+ " to " + MedicoResources.convertUtilDateToString_DD_MM_YYYY_(bean.getEndDate()));
			cell.setCellStyle(headingCellStyle);

			rowCount += 1;
			row = sheet.createRow(rowCount);

			for (String heading : headings) {
				cell = ReportFormats.cell(row, colCount, heading, columnHeadingStyle);
				colCount++;
			}

			sheet.groupRow(0, 2);
			sheet.createFreezePane(0, 4);

			rowCount++;
			colCount = 0;

			BigDecimal goodsValueTotal = new BigDecimal(0);
			BigDecimal addChargesTotal = new BigDecimal(0);
			BigDecimal discountTotal = new BigDecimal(0);
			BigDecimal sgstTotal = new BigDecimal(0);
			BigDecimal cgstTotal = new BigDecimal(0);
			BigDecimal igstTotal = new BigDecimal(0);
			BigDecimal rounfOffTotal = new BigDecimal(0);
			BigDecimal finalTotal = new BigDecimal(0);

			BigDecimal goodsValueTotalGrand = new BigDecimal(0);
			BigDecimal addChargesTotalGrand = new BigDecimal(0);
			BigDecimal discountTotalGrand = new BigDecimal(0);
			BigDecimal sgstTotalGrand = new BigDecimal(0);
			BigDecimal cgstTotalGrand = new BigDecimal(0);
			BigDecimal igstTotalGrand = new BigDecimal(0);
			BigDecimal rounfOffTotalGrand = new BigDecimal(0);
			BigDecimal finalTotalGrand = new BigDecimal(0);

			String new_division = "";
			String old_division = "";

			int i = 0;

			for (ViewGRNSummary_GST data : list) {
				BigDecimal netAmount = new BigDecimal(0);
				new_division = data.getDivision_name();

				if (!(new_division.equalsIgnoreCase(old_division))) {
					if (i != 0) {
						row = sheet.createRow(rowCount);
						cell = ReportFormats.cell(row, colCount, "Division Total", null);
						// sheet.addMergedRegion(new CellRangeAddress(rowCount, rowCount, colCount,
						// 11));
						colCount++;

						for (int x = 0; x <= 10; x++) {
							cell = ReportFormats.cell(row, colCount, "", cellAlignment);
							colCount++;
						}

						cell = row.createCell(12);
						cell = ReportFormats.cellNum(row, colCount,
								goodsValueTotal == null ? 0l : goodsValueTotal.doubleValue(), cellAlignment);
						goodsValueTotalGrand = goodsValueTotalGrand.add(goodsValueTotal);
						colCount++;

						for (int x = 0; x <= 4; x++) {
							cell = ReportFormats.cell(row, colCount, "", cellAlignment);
							colCount++;
						}

						cell = row.createCell(18);
						cell = ReportFormats.cellNum(row, colCount,
								addChargesTotal == null ? 0l : addChargesTotal.doubleValue(), cellAlignment);
						addChargesTotalGrand = addChargesTotalGrand.add(addChargesTotal);
						// sheet.addMergedRegion(new CellRangeAddress(rowCount, rowCount, 13, 18));
						colCount++;

						cell = row.createCell(19);
						cell = ReportFormats.cell(row, colCount, "", cellAlignment);
						colCount++;

						cell = row.createCell(20);
						cell = ReportFormats.cellNum(row, colCount,
								discountTotal == null ? 0l : discountTotal.doubleValue(), cellAlignment);
						discountTotalGrand = discountTotalGrand.add(discountTotal);
						// sheet.addMergedRegion(new CellRangeAddress(rowCount, rowCount, 19, 20));
						colCount++;

						cell = row.createCell(21);
						cell = ReportFormats.cellNum(row, colCount, sgstTotal == null ? 0l : sgstTotal.doubleValue(),
								cellAlignment);
						sgstTotalGrand = sgstTotalGrand.add(sgstTotal);
						colCount++;

						cell = row.createCell(22);
						cell = ReportFormats.cellNum(row, colCount, cgstTotal == null ? 0l : cgstTotal.doubleValue(),
								cellAlignment);
						cgstTotalGrand = cgstTotalGrand.add(cgstTotal);
						colCount++;

						cell = row.createCell(23);
						cell = ReportFormats.cellNum(row, colCount, igstTotal == null ? 0l : igstTotal.doubleValue(),
								cellAlignment);
						igstTotalGrand = igstTotalGrand.add(igstTotal);
						colCount++;

						cell = row.createCell(24);
						cell = ReportFormats.cellNum(row, colCount, igstTotal == null ? 0l : igstTotal.doubleValue(),
								cellAlignment);
						rounfOffTotalGrand = rounfOffTotalGrand.add(rounfOffTotal);
						colCount++;

						cell = row.createCell(25);
						cell = ReportFormats.cellNum(row, colCount, finalTotal == null ? 0l : finalTotal.doubleValue(),
								cellAlignment);
						finalTotalGrand = finalTotalGrand.add(finalTotal);
						colCount++;

						goodsValueTotal = new BigDecimal(0);
						addChargesTotal = new BigDecimal(0);
						discountTotal = new BigDecimal(0);
						sgstTotal = new BigDecimal(0);
						cgstTotal = new BigDecimal(0);
						igstTotal = new BigDecimal(0);
						rounfOffTotal = new BigDecimal(0);
						finalTotal = new BigDecimal(0);

						rowCount++;
						colCount = 0;
					}

					row = sheet.createRow(rowCount);
					cell = ReportFormats.cell(row, colCount, "Division : " + new_division, null);
					// sheet.addMergedRegion(new CellRangeAddress(rowCount, rowCount, colCount,
					// 20));

					rowCount++;
					colCount = 0;
				}

				row = sheet.createRow(rowCount);
				/*
				 * cell = ReportFormats.cell(row, colCount, data.getRow().toString(),
				 * cellAlignment); colCount++;
				 */

				cell = ReportFormats.cell(row, colCount, data.getGrn_no(), null);
				colCount++;

				cell = ReportFormats.cell(row, colCount, data.getSending_Location(), null);
				colCount++;

				cell = ReportFormats.cell(row, colCount, data.getSuppliercode(), null);
				colCount++;

				cell = ReportFormats.cell(row, colCount, data.getSupplier(), null);
				colCount++;

				cell = ReportFormats.cell(row, colCount, data.getGst_no(), null);
				colCount++;

				cell = ReportFormats.cell(row, colCount, data.getState_name(), null);
				colCount++;

				cell = ReportFormats.cell(row, colCount, data.getState_code(), null);
				colCount++;

				cell = ReportFormats.cell(row, colCount, data.getGrn_date(), null);
				colCount++;

				cell = ReportFormats.cell(row, colCount, data.getTransfer_no(), null);
				colCount++;

				cell = ReportFormats.cell(row, colCount, data.getTransfer_date(), null);
				colCount++;

				cell = ReportFormats.cell(row, colCount, data.getLr_no(), cellAlignment);
				colCount++;

				cell = ReportFormats.cell(row, colCount, data.getLr_date(), null);
				colCount++;

				cell = ReportFormats.cellNum(row, colCount,
						data.getGrnvalue() == null ? 0l : data.getGrnvalue().doubleValue(), null);
				goodsValueTotal = goodsValueTotal.add(data.getGrnvalue());
				colCount++;

				cell = ReportFormats.cell(row, colCount, data.getTransporter_Name(), null);
				colCount++;

				cell = ReportFormats.cell(row, colCount, data.getDivision_name(), null);
				colCount++;

				cell = ReportFormats.cell(row, colCount, data.getPo_no(), null);
				colCount++;

				cell = ReportFormats.cell(row, colCount, data.getPo_date(), null);
				colCount++;

				cell = ReportFormats.cell(row, colCount, data.getHtext1(), null);
				colCount++;

				cell = ReportFormats.cellNum(row, colCount,
						data.getHvalue1() == null ? 0l : data.getHvalue1().doubleValue(), null);
				addChargesTotal = addChargesTotal.add(data.getHvalue1());
				colCount++;

				cell = ReportFormats.cell(row, colCount, data.getHtext2(), null);
				colCount++;

				cell = ReportFormats.cellNum(row, colCount,
						data.getHvalue2() == null ? 0l : data.getHvalue2().doubleValue(), null);
				discountTotal = discountTotal.add(data.getHvalue2());
				colCount++;

				cell = ReportFormats.cellNum(row, colCount,
						data.getHsgst_bill_amt() == null ? 0l : data.getHsgst_bill_amt().doubleValue(), null);
				sgstTotal = sgstTotal.add(data.getHsgst_bill_amt());
				colCount++;

				cell = ReportFormats.cellNum(row, colCount,
						data.getHcgst_bill_amt() == null ? 0l : data.getHcgst_bill_amt().doubleValue(), null);
				cgstTotal = cgstTotal.add(data.getHcgst_bill_amt());
				colCount++;

				cell = ReportFormats.cellNum(row, colCount,
						data.getHigst_bill_amt() == null ? 0l : data.getHigst_bill_amt().doubleValue(), null);
				igstTotal = igstTotal.add(data.getHigst_bill_amt());
				colCount++;

				cell = ReportFormats.cellNum(row, colCount,
						data.getHroundoff() == null ? 0l : data.getHroundoff().doubleValue(), null);
				rounfOffTotal = rounfOffTotal.add(data.getHroundoff());
				colCount++;

				netAmount = data.getGrnvalue().add(data.getHvalue1().add(data.getHvalue2().add(data.getHsgst_bill_amt()
						.add(data.getHcgst_bill_amt().add(data.getHigst_bill_amt().add(data.getHroundoff()))))));
				finalTotal = finalTotal.add(netAmount);

				cell = ReportFormats.cellNum(row, colCount, netAmount == null ? 0l : netAmount.doubleValue(), null);
				colCount++;
				
				cell = ReportFormats.cell(row, colCount, data.getRemarks(), null);
				colCount++;
				
				if(grnRefNoInd != null && grnRefNoInd.equalsIgnoreCase("Y")) {
					cell = ReportFormats.cell(row, colCount, data.getGrn_ref_no(), null);
				}

				colCount = 0;
				rowCount++;

				if (i == list.size() - 1) {

					row = sheet.createRow(rowCount);
					cell = ReportFormats.cell(row, colCount, "Division Total", null);
					colCount++;

					for (int x = 0; x <= 10; x++) {
						cell = ReportFormats.cell(row, colCount, "", cellAlignment);
						colCount++;
					}

					cell = row.createCell(12);
					cell = ReportFormats.cellNum(row, colCount,
							goodsValueTotal == null ? 0l : goodsValueTotal.doubleValue(), cellAlignment);
					goodsValueTotalGrand = goodsValueTotalGrand.add(goodsValueTotal);
					colCount++;

					for (int x = 0; x <= 4; x++) {
						cell = ReportFormats.cell(row, colCount, "", cellAlignment);
						colCount++;
					}

					cell = row.createCell(18);
					cell = ReportFormats.cellNum(row, colCount,
							addChargesTotal == null ? 0l : addChargesTotal.doubleValue(), cellAlignment);
					addChargesTotalGrand = addChargesTotalGrand.add(addChargesTotal);
					// sheet.addMergedRegion(new CellRangeAddress(rowCount,rowCount, 13, 18));
					colCount++;

					cell = row.createCell(19);
					cell = ReportFormats.cell(row, colCount, "", cellAlignment);
					colCount++;

					cell = row.createCell(20);
					cell = ReportFormats.cellNum(row, colCount,
							discountTotal == null ? 0l : discountTotal.doubleValue(), cellAlignment);
					discountTotalGrand = discountTotalGrand.add(discountTotal);
					// sheet.addMergedRegion(new CellRangeAddress(rowCount, rowCount, 19, 20));
					colCount++;

					cell = row.createCell(21);
					cell = ReportFormats.cellNum(row, colCount, sgstTotal == null ? 0l : sgstTotal.doubleValue(),
							cellAlignment);
					sgstTotalGrand = sgstTotalGrand.add(sgstTotal);
					colCount++;

					cell = row.createCell(22);
					cell = ReportFormats.cellNum(row, colCount, cgstTotal == null ? 0l : cgstTotal.doubleValue(),
							cellAlignment);
					cgstTotalGrand = cgstTotalGrand.add(cgstTotal);
					colCount++;

					cell = row.createCell(23);
					cell = ReportFormats.cellNum(row, colCount, igstTotal == null ? 0l : igstTotal.doubleValue(),
							cellAlignment);
					igstTotalGrand = igstTotalGrand.add(igstTotal);
					colCount++;

					cell = row.createCell(24);
					cell = ReportFormats.cellNum(row, colCount,
							rounfOffTotal == null ? 0l : rounfOffTotal.doubleValue(), cellAlignment);
					rounfOffTotalGrand = rounfOffTotalGrand.add(rounfOffTotal);
					colCount++;

					cell = row.createCell(25);
					cell = ReportFormats.cellNum(row, colCount, finalTotal == null ? 0l : finalTotal.doubleValue(),
							cellAlignment);
					finalTotalGrand = finalTotalGrand.add(finalTotal);

					goodsValueTotal = new BigDecimal(0);
					addChargesTotal = new BigDecimal(0);
					discountTotal = new BigDecimal(0);
					sgstTotal = new BigDecimal(0);
					cgstTotal = new BigDecimal(0);
					igstTotal = new BigDecimal(0);
					rounfOffTotal = new BigDecimal(0);
					finalTotal = new BigDecimal(0);

					rowCount++;
					colCount = 0;

					row = sheet.createRow(rowCount);

					cell = ReportFormats.cell(row, colCount, "Grand Total", null);
					// sheet.addMergedRegion(new CellRangeAddress(rowCount, rowCount, colCount,
					// 11));
					colCount++;

					for (int x = 0; x <= 10; x++) {
						cell = ReportFormats.cell(row, colCount, "", cellAlignment);
						colCount++;
					}

					cell = row.createCell(12);
					cell = ReportFormats.cellNum(row, colCount,
							goodsValueTotalGrand == null ? 0l : goodsValueTotalGrand.doubleValue(), cellAlignment);
					colCount++;

					for (int x = 0; x <= 4; x++) {
						cell = ReportFormats.cell(row, colCount, "", cellAlignment);
						colCount++;
					}

					cell = row.createCell(18);
					cell = ReportFormats.cellNum(row, colCount,
							addChargesTotalGrand == null ? 0l : addChargesTotalGrand.doubleValue(), cellAlignment);
					// sheet.addMergedRegion(new CellRangeAddress(rowCount, rowCount, 13, 18));
					colCount++;

					cell = row.createCell(19);
					cell = ReportFormats.cell(row, colCount, "", cellAlignment);
					colCount++;

					cell = row.createCell(20);
					cell = ReportFormats.cellNum(row, colCount,
							discountTotalGrand == null ? 0l : discountTotalGrand.doubleValue(), cellAlignment);
					// sheet.addMergedRegion(new CellRangeAddress(rowCount, rowCount, 19, 20));
					colCount++;

					cell = row.createCell(21);
					cell = ReportFormats.cellNum(row, colCount,
							sgstTotalGrand == null ? 0l : sgstTotalGrand.doubleValue(), cellAlignment);
					colCount++;
					// cell = ReportFormats.cell(row, colCount, sgstTotalGrand.toString(),
					// cellAlignment);

					cell = row.createCell(22);
					cell = ReportFormats.cellNum(row, colCount,
							cgstTotalGrand == null ? 0l : cgstTotalGrand.doubleValue(), cellAlignment);
					colCount++;

					cell = row.createCell(23);
					cell = ReportFormats.cellNum(row, colCount,
							igstTotalGrand == null ? 0l : igstTotalGrand.doubleValue(), cellAlignment);
					colCount++;

					cell = row.createCell(24);
					cell = ReportFormats.cellNum(row, colCount,
							rounfOffTotalGrand == null ? 0l : rounfOffTotalGrand.doubleValue(), cellAlignment);
					colCount++;

					cell = row.createCell(25);
					cell = ReportFormats.cellNum(row, colCount,
							finalTotalGrand == null ? 0l : finalTotalGrand.doubleValue(), cellAlignment);
					colCount++;
				}
				i++;
				old_division = new_division;
			}
			FileOutputStream fileOutputStream = new FileOutputStream(filepath);
			workbook.write(fileOutputStream);

			filename=usermasterservice.generateExcellock(filepath, filename,empId,".xlsx");

		} finally {
			if (workbook != null) {
				workbook.close();
			}
		}
		return filename ;
	}

}
