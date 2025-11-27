package com.excel.service;

import java.io.File;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.apache.commons.lang3.text.WordUtils;
import org.apache.poi.ss.usermodel.BorderStyle;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.FillPatternType;
import org.apache.poi.ss.usermodel.Font;
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

import com.excel.model.Company;
import com.excel.model.Period_Tds;
import com.excel.model.TdsApplicableProduct;
import com.excel.model.TdsApplicableStatementReport;
import com.excel.repository.CrmExpenseRepository;
import com.excel.utility.CodifyErrors;
import com.excel.utility.MedicoConstants;
import com.excel.utility.ReportFormats;
import com.excel.utility.Utility;

@Service
public class CRM_Download_excel_serviceImpl implements CRM_Download_excel_service, MedicoConstants {

	@Autowired
	FinancialYearService financialyearservice;

	@Autowired
	CrmExpenseRepository crmexpenserepository;

	@Override
	public Map<String, Object> generateTdsSummaryReport(List<TdsApplicableStatementReport> list,HttpSession session)
			throws Exception {
		Map<String, Object> map = new HashMap<>();
		String filename = "TDS_SUMMARY_REPORT" + new Date().getTime() + ".xlsx";
		String filepath = REPORT_FILE_PATH + filename;
		System.out.println("file path :::" + filepath);
		XSSFWorkbook workbook = null;
		List<String> productHeader = null;
		String companyCode = session.getServletContext().getAttribute(COMPANY_CODE).toString();
		String companyname = ((Company) session.getServletContext().getAttribute(COMPANY_DATA)).getCompany_name();

		String finyear = financialyearservice.getFinYearForTDSVet(companyCode).getFin_year();
		List<Period_Tds> plist = crmexpenserepository.getPeriod(finyear);
		try {
			workbook = new XSSFWorkbook();
			File f = new File(REPORT_FILE_PATH);
			if (!f.exists()) {
				if (f.mkdirs()) {
				} else {
					throw new RuntimeException("Could not create directory");
				}
			}
			XSSFSheet sheet = workbook.createSheet("TDS_SUMMARY_REPORT");

			List<String> headings = new ArrayList<>();
			headings.add("Sr No.");
			headings.add("Customer Code");
			headings.add("PAN Number (If available)");
			headings.add("Customer Name");
			headings.add("City");
			headings.add("State");
			headings.add("Mob No.");
			headings.add("Email ID");
			for (int i = 0; i < plist.size(); i++) {
				headings.add(plist.get(i).getPeriod_name());
			}
			headings.add("Total");
			headings.add("TDS Payable");
			headings.add("TDS Paid");
			headings.add("Balance");

			XSSFRow row = null;
			XSSFCell cell = null;
			int rowCount = 0;
			int colCount = 0;

			Font font = workbook.createFont();
//			font.setFontHeightInPoints((short) 11);
//			font.setFontName("ARIAL");
			font.setBold(true);

			XSSFCellStyle headingCellStyle = ReportFormats.heading3(workbook);
			XSSFCellStyle columnHeadingStyle = ReportFormats.columnHeadingBlue(workbook);
			XSSFCellStyle cellAlignment = ReportFormats.setCellAlignment(workbook, ALIGN_RIGHT);
			cellAlignment.setDataFormat(workbook.createDataFormat().getFormat("0.00"));
			XSSFCellStyle columnStyle = ReportFormats.cellBold1(workbook);
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
			decimalstyle2.setBorderTop(BorderStyle.THIN);
			
			XSSFCellStyle decimalstyle3 = workbook.createCellStyle();
			decimalstyle3.setFillForegroundColor(IndexedColors.GREY_25_PERCENT.index);
			decimalstyle3.setFillPattern(FillPatternType.SOLID_FOREGROUND);
			decimalstyle3.setAlignment(HorizontalAlignment.CENTER);
			decimalstyle3.setFont(font);
			decimalstyle3.setBorderLeft(BorderStyle.THIN);
			decimalstyle3.setBorderRight(BorderStyle.THIN);
			decimalstyle3.setBorderBottom(BorderStyle.THIN);
			decimalstyle3.setBorderTop(BorderStyle.THIN);
			
			//Company Name 
			row = sheet.createRow(rowCount);
			cell = row.createCell(colCount);
			sheet.addMergedRegion(new CellRangeAddress(rowCount, rowCount, colCount, colCount + headings.size() - 1));
			cell.setCellValue(companyname);
			cell.setCellStyle(headingCellStyle);
			rowCount += 1;

			row = sheet.createRow(rowCount);
			cell = row.createCell(colCount);
			sheet.addMergedRegion(new CellRangeAddress(rowCount, rowCount, colCount, colCount + headings.size() - 1));
			cell.setCellValue("Section 194R TDS applicability Statement - April " + finyear + " to March "
					+ (Long.valueOf(finyear) + 1));
			cell.setCellStyle(headingCellStyle);
			rowCount += 1;
			
			row = sheet.createRow(rowCount);
			cell = row.createCell(colCount);
			sheet.addMergedRegion(new CellRangeAddress(rowCount, rowCount, colCount, colCount + headings.size() - 1));
			cell.setCellValue("");
			cell.setCellStyle(headingCellStyle);
			rowCount += 1;

			colCount = 0;
			row = sheet.createRow(rowCount);
			cell = row.createCell(colCount);
			sheet.addMergedRegion(new CellRangeAddress(rowCount, rowCount, colCount, colCount + 7));
			cell.setCellValue("Customers qualifying for TDS 194R deduction");
			cell.setCellStyle(columnStyle);
			colCount = colCount + 8;
			cell = row.createCell(colCount);
			sheet.addMergedRegion(new CellRangeAddress(rowCount, rowCount, colCount, colCount + 12));
			cell.setCellValue("Supplied Goods Value for TDS (RS.)");
			cell.setCellStyle(columnStyle);
			colCount = colCount + 13;
			cell = row.createCell(colCount);
			sheet.addMergedRegion(new CellRangeAddress(rowCount, rowCount, colCount, colCount + 2));
			cell.setCellValue("TDS 194R (RS.)");
			cell.setCellStyle(columnStyle);

			rowCount += 1;
			row = sheet.createRow(rowCount);
			colCount = 0;
			for (String heading : headings) {

				cell = ReportFormats.cell(row, colCount, heading, columnHeadingStyle);
				colCount++;
			}
			sheet.groupRow(0, 2);
			sheet.createFreezePane(0, 3);
			rowCount += 1;
			int srno = 1;
			double jan = 0.00;
			double feb = 0.00;
			double mar = 0.00;
			double apr = 0.00;
			double may = 0.00;
			double jun = 0.00;
			double jul = 0.00;
			double aug = 0.00;
			double sep = 0.00;
			double oct = 0.00;
			double nov = 0.00;
			double dec = 0.00;
			double tot = 0.00;
			double total = 0.00;
			double taxtot = 0.00;
			double taxpay = 0.00;
			double balance = 0.00;
			for (TdsApplicableStatementReport data : list) {
				colCount = 0;

				row = sheet.createRow(rowCount);
				cell = ReportFormats.cell(row, colCount, String.valueOf(srno), ReportFormats.setCellAlignment(workbook, ALIGN_CENTER));
				colCount++;
				srno++;

				cell = ReportFormats.cell(row, colCount, data.getUnique_hcp_id(), null);
				colCount++;

				cell = ReportFormats.cell(row, colCount,
						data.getHcp_pan_number() == null || data.getHcp_pan_number().equalsIgnoreCase("NA") ? ""
								: data.getHcp_pan_number(),
						null);
				colCount++;

				cell = ReportFormats.cell(row, colCount, WordUtils.capitalizeFully(data.getName_of_hcp()), null);
				colCount++;

				cell = ReportFormats.cell(row, colCount,
						data.getHcp_city() == null ? "" : WordUtils.capitalizeFully(data.getHcp_city()), null);
				colCount++;

				cell = ReportFormats.cell(row, colCount,
						data.getHcp_state() == null ? "" : WordUtils.capitalizeFully(data.getHcp_state()), null);
				colCount++;

				cell = ReportFormats.cell(row, colCount, data.getHcp_mobile_no() == null ? "" : data.getHcp_mobile_no(),
						null);
				colCount++;

				cell = ReportFormats.cell(row, colCount, data.getHcp_email_id() == null ? "" : data.getHcp_email_id(),
						null);
				colCount++;

				cell = ReportFormats.cellNum(row, colCount, Double.valueOf(data.getV_mth1().toString()), cellAlignment);
				colCount++;

				cell = ReportFormats.cellNum(row, colCount, Double.valueOf(data.getV_mth2().toString()), cellAlignment);
				colCount++;

				cell = ReportFormats.cellNum(row, colCount, Double.valueOf(data.getV_mth3().toString()), cellAlignment);
				colCount++;

				cell = ReportFormats.cellNum(row, colCount, Double.valueOf(data.getV_mth4().toString()), cellAlignment);
				colCount++;

				cell = ReportFormats.cellNum(row, colCount, Double.valueOf(data.getV_mth5().toString()), cellAlignment);
				colCount++;

				cell = ReportFormats.cellNum(row, colCount, Double.valueOf(data.getV_mth6().toString()), cellAlignment);
				colCount++;

				cell = ReportFormats.cellNum(row, colCount, Double.valueOf(data.getV_mth7().toString()), cellAlignment);
				colCount++;

				cell = ReportFormats.cellNum(row, colCount, Double.valueOf(data.getV_mth8().toString()), cellAlignment);
				colCount++;

				cell = ReportFormats.cellNum(row, colCount, Double.valueOf(data.getV_mth9().toString()), cellAlignment);
				colCount++;

				cell = ReportFormats.cellNum(row, colCount, Double.valueOf(data.getV_mth10().toString()),
						cellAlignment);
				colCount++;

				cell = ReportFormats.cellNum(row, colCount, Double.valueOf(data.getV_mth11().toString()),
						cellAlignment);
				colCount++;

				cell = ReportFormats.cellNum(row, colCount, Double.valueOf(data.getV_mth12().toString()),
						cellAlignment);
				colCount++;

				total = Double.valueOf(data.getV_mth1().toString()) + Double.valueOf(data.getV_mth2().toString())
						+ Double.valueOf(data.getV_mth3().toString()) + Double.valueOf(data.getV_mth4().toString())
						+ Double.valueOf(data.getV_mth5().toString()) + Double.valueOf(data.getV_mth6().toString())
						+ Double.valueOf(data.getV_mth7().toString()) + Double.valueOf(data.getV_mth8().toString())
						+ Double.valueOf(data.getV_mth9().toString()) + Double.valueOf(data.getV_mth10().toString())
						+ Double.valueOf(data.getV_mth11().toString()) + Double.valueOf(data.getV_mth12().toString());
				apr = apr + Double.valueOf(data.getV_mth1().toString());
				may = may + Double.valueOf(data.getV_mth2().toString());
				jun = jun + Double.valueOf(data.getV_mth3().toString());
				jul = jul + Double.valueOf(data.getV_mth4().toString());
				aug = aug + Double.valueOf(data.getV_mth5().toString());
				sep = sep + Double.valueOf(data.getV_mth6().toString());
				oct = oct + Double.valueOf(data.getV_mth7().toString());
				nov = nov + Double.valueOf(data.getV_mth8().toString());
				dec = dec + Double.valueOf(data.getV_mth9().toString());
				jan = jan + Double.valueOf(data.getV_mth10().toString());
				feb = feb + Double.valueOf(data.getV_mth11().toString());
				mar = mar + Double.valueOf(data.getV_mth12().toString());
				tot = tot + total;
				cell = ReportFormats.cellNum(row, colCount, Double.valueOf(total), cellAlignment);
				colCount++;

				String s = String.format("%.2f", Double.valueOf(data.getTds_payable().toString()));
				cell = ReportFormats.cellNum(row, colCount, Double.valueOf(data.getTds_payable().toString()),
						cellAlignment);
				colCount++;
				taxtot = taxtot + Double.valueOf(data.getTds_payable().toString());
				cell = ReportFormats.cellNum(row, colCount, Double.valueOf(data.getTds_paid().toString()),
						cellAlignment);
				colCount++;
				taxpay = taxpay + Double.valueOf(data.getTds_paid().toString());
				cell = ReportFormats.cellNum(row, colCount, Double.valueOf(data.getTds_balance().toString()),
						cellAlignment);
				colCount++;
				rowCount++;
				total = 0;
				balance = balance + Double.valueOf(data.getTds_balance().toString());

			}

			colCount = 0;
			row = sheet.createRow(rowCount);
			for (int i = 0; i < 8; i++) {
				cell = row.createCell(i);
				cell.setCellStyle(decimalstyle2);
				cell.setCellValue("");
			}

			cell = row.createCell(colCount);
			cell.setCellValue("Grand Total : ");
			cell.setCellStyle(decimalstyle3);
			sheet.addMergedRegion(new CellRangeAddress(rowCount, rowCount, colCount, colCount + 1));
			colCount += 2;
			
			cell = ReportFormats.cell(row, colCount, "", decimalstyle3);
			colCount++;
			
			cell = ReportFormats.cell(row, colCount, "", decimalstyle3);
			colCount++;
			
			cell = ReportFormats.cell(row, colCount, "", decimalstyle3);
			colCount++;

			cell = ReportFormats.cell(row, colCount, "", decimalstyle3);
			colCount++;

			cell = ReportFormats.cell(row, colCount, "", decimalstyle3);
			colCount++;

			cell = ReportFormats.cell(row, colCount, "", decimalstyle3);
			colCount++;

			cell = ReportFormats.cellNum(row, colCount, Double.valueOf(apr), cellAlignment);
			cell.setCellStyle(decimalstyle2);
			colCount++;

			cell = ReportFormats.cellNum(row, colCount, Double.valueOf(may), cellAlignment);
			cell.setCellStyle(decimalstyle2);
			colCount++;

			cell = ReportFormats.cellNum(row, colCount, Double.valueOf(jun), cellAlignment);
			cell.setCellStyle(decimalstyle2);
			colCount++;

			cell = ReportFormats.cellNum(row, colCount, Double.valueOf(jul), cellAlignment);
			cell.setCellStyle(decimalstyle2);
			colCount++;

			cell = ReportFormats.cellNum(row, colCount, Double.valueOf(aug), cellAlignment);
			cell.setCellStyle(decimalstyle2);
			colCount++;

			cell = ReportFormats.cellNum(row, colCount, Double.valueOf(sep), cellAlignment);
			cell.setCellStyle(decimalstyle2);
			colCount++;

			cell = ReportFormats.cellNum(row, colCount, Double.valueOf(oct), cellAlignment);
			cell.setCellStyle(decimalstyle2);
			colCount++;

			cell = ReportFormats.cellNum(row, colCount, Double.valueOf(nov), cellAlignment);
			cell.setCellStyle(decimalstyle2);
			colCount++;

			cell = ReportFormats.cellNum(row, colCount, Double.valueOf(dec), cellAlignment);
			cell.setCellStyle(decimalstyle2);
			colCount++;

			cell = ReportFormats.cellNum(row, colCount, Double.valueOf(jan), cellAlignment);
			cell.setCellStyle(decimalstyle2);
			colCount++;

			cell = ReportFormats.cellNum(row, colCount, Double.valueOf(feb), cellAlignment);
			cell.setCellStyle(decimalstyle2);
			colCount++;

			cell = ReportFormats.cellNum(row, colCount, Double.valueOf(mar), cellAlignment);
			cell.setCellStyle(decimalstyle2);
			colCount++;

			cell = ReportFormats.cellNum(row, colCount, Double.valueOf(tot), cellAlignment);
			cell.setCellStyle(decimalstyle2);
			colCount++;

			cell = ReportFormats.cellNum(row, colCount, Double.valueOf(taxtot), cellAlignment);
			cell.setCellStyle(decimalstyle2);
			colCount++;

			cell = ReportFormats.cellNum(row, colCount, Double.valueOf(taxpay), cellAlignment);
			cell.setCellStyle(decimalstyle2);
			colCount++;

			cell = ReportFormats.cellNum(row, colCount, Double.valueOf(balance), cellAlignment);
			cell.setCellStyle(decimalstyle2);
			colCount++;
			
			sheet.createFreezePane(0, 5);

			FileOutputStream fileOutputStream = new FileOutputStream(filepath);
			workbook.write(fileOutputStream);

			map.put("filename", filename);
			map.put("mth", plist);
		} finally {
			if (workbook != null) {
				workbook.close();
			}
		}
		return map;
	}

	@Override
	public String generateTdsDetailReport(List<TdsApplicableProduct> list, TdsApplicableStatementReport bean,
			HttpSession session) throws Exception {
		String filename = "TDS_DETAIL_REPORT" + new Date().getTime() + ".xlsx";
		String filepath = REPORT_FILE_PATH + filename;
		System.out.println("file path :::" + filepath);
		XSSFWorkbook workbook = null;
		List<String> productHeader = null;
		String old = "old";
		try {
			workbook = new XSSFWorkbook();
			File f = new File(REPORT_FILE_PATH);
			if (!f.exists()) {
				if (f.mkdirs()) {
				} else {
					throw new RuntimeException("Could not create directory");
				}
			}
			XSSFSheet sheet = workbook.createSheet("TDS_Detail_REPORT");

			XSSFRow row = null;
			XSSFCell cell = null;
			int rowCount = 0;
			int colCount = 0;
			XSSFFont headerFont = workbook.createFont();
			headerFont.setBold(true);

			String companyCode = session.getServletContext().getAttribute(COMPANY_CODE).toString();
			String companyname = ((Company) session.getServletContext().getAttribute(COMPANY_DATA)).getCompany_name();

			String finyear = financialyearservice.getFinYearForTDSVet(companyCode).getFin_year();
			List<Period_Tds> plist = crmexpenserepository.getPeriod(finyear);

			XSSFCellStyle headingCellStyle = ReportFormats.heading3_(workbook);
			XSSFCellStyle headingCellStyle1 = ReportFormats.heading4(workbook);
			XSSFCellStyle dataStyle = ReportFormats.datastyle(workbook);
			XSSFCellStyle dataStyleL = ReportFormats.datastyleLeft(workbook);
			XSSFCellStyle dataStyleR = ReportFormats.datastyleRightwithDecimal(workbook);
			XSSFCellStyle dataStyleR_ = ReportFormats.datastyleRight(workbook);
			XSSFCellStyle columnHeadingStyle = ReportFormats.columnHeadingGrey_(workbook);
			XSSFCellStyle columnHeadingStyle1 = ReportFormats.columnHeadingGrey_1(workbook);
			XSSFCellStyle cellAlignment = ReportFormats.setCellAlignmentForNum(workbook, ALIGN_RIGHT);
			cellAlignment.setDataFormat(workbook.createDataFormat().getFormat("0.00"));
			XSSFCellStyle columnStyle = ReportFormats.cellBold_(workbook);
			XSSFCellStyle columnStyle1 = ReportFormats.cellBold_1(workbook);
			XSSFCellStyle cellAlignment1 = ReportFormats.setCellAlignment(workbook, ALIGN_RIGHT);
			XSSFCellStyle cellAlignmentR = ReportFormats.setCellAlignment(workbook, ALIGN_RIGHT);
			XSSFCellStyle cellAlignment2 = ReportFormats.setCellAlignment(workbook, ALIGN_CENTER);
			cellAlignment1.setFont(headerFont);

			XSSFCellStyle Total = ReportFormats.setCellAlignmentForNum(workbook, ALIGN_RIGHT);
			Total.setDataFormat(workbook.createDataFormat().getFormat("0.00"));
			Total.setFont(headerFont);
			List<String> headings = new ArrayList<>();
			headings.add("Sr No.");
			headings.add("Customer Code");
			headings.add("PAN No. (If available)");
			headings.add("Customer Name");
			headings.add("City");
			headings.add("State");

			for (int i = 0; i < plist.size(); i++) {
				headings.add(plist.get(i).getPeriod_name());
			}

			headings.add("Total");
			headings.add("TDS Payable");
			headings.add("TDS Paid");
			headings.add("Balance");
			headings.add("Mob No.");
			headings.add("Email ID");
			headings.add("Address 1");
			headings.add("Address 2");
			headings.add("Address 3");
			headings.add("Address 4");
			String headings2[] = { "Type", "Code", "Product Name", "Date", "Units", "Rate", "Value (RS.)",
					"Challan No.", "Challan Date", "E-Invoice No.", "E-Invoice Date", "EWaybill No.", "EWaybill Dt.",
					"Invoice No.", "Invoice Date" };
			
			//Company Name
			row = sheet.createRow(rowCount);
			cell = row.createCell(colCount);
			sheet.addMergedRegion(new CellRangeAddress(rowCount, rowCount, colCount, colCount + headings.size() - 1));
			cell.setCellValue(companyname);
			cell.setCellStyle(headingCellStyle);
			rowCount += 1;

			row = sheet.createRow(rowCount);
			cell = row.createCell(colCount);
			sheet.addMergedRegion(new CellRangeAddress(rowCount, rowCount, colCount, colCount + headings.size() - 1));
			cell.setCellValue("Section 194R TDS Applicability Statement - April " + finyear + " to March "
					+ (Long.valueOf(finyear) + 1) + " - Detailed Report");
			cell.setCellStyle(headingCellStyle1);
			rowCount += 1;
			
			row = sheet.createRow(rowCount);
			cell = row.createCell(colCount);
			sheet.addMergedRegion(new CellRangeAddress(rowCount, rowCount, colCount, colCount + headings.size() - 1));
			cell.setCellValue("Report Dated: " +Utility.convertSmpDatetoString_(new Date()));
			cell.setCellStyle(headingCellStyle1);
			rowCount += 1;

			colCount = 0;
			row = sheet.createRow(rowCount);
			for (int i = 0; i < 7; i++) {
				cell = row.createCell(i);
				cell.setCellStyle(columnStyle);
				cell.setCellValue("");
			}
			cell = row.createCell(colCount);
			sheet.addMergedRegion(new CellRangeAddress(rowCount, rowCount, colCount, colCount + 5));
			cell.setCellValue("Customers Qualifying For TDS 194R Deduction");
			cell.setCellStyle(columnStyle);
			colCount = colCount + 6;

			cell = row.createCell(colCount);
			sheet.addMergedRegion(new CellRangeAddress(rowCount, rowCount, colCount, colCount + 12));
			cell.setCellValue("Supplied Goods Value for TDS (RS.)");
			cell.setCellStyle(columnStyle);
			colCount = colCount + 13;

			cell = row.createCell(colCount);
			sheet.addMergedRegion(new CellRangeAddress(rowCount, rowCount, colCount, colCount + 2));
			cell.setCellValue("TDS 194R (RS.)");
			cell.setCellStyle(columnStyle);
			colCount = colCount + 3;

			cell = row.createCell(colCount);
			sheet.addMergedRegion(new CellRangeAddress(rowCount, rowCount, colCount, colCount + 5));
			cell.setCellValue("Customer Contact Details");
			cell.setCellStyle(columnStyle);

			rowCount += 1;
			row = sheet.createRow(rowCount);
			colCount = 0;
			for (String heading : headings) {
				cell = ReportFormats.cell(row, colCount, heading, columnHeadingStyle);
				colCount++;
			}
			sheet.groupRow(0, 2);
			sheet.createFreezePane(0, 7);
			rowCount += 1;
			int srno = 1;

			double total = 0.00;
			double grandTot = 0.00;
			colCount = 0;
			row = sheet.createRow(rowCount);

			cell = ReportFormats.cell(row, colCount, "1", dataStyle);
			colCount++;

			cell = ReportFormats.cell(row, colCount, bean.getUnique_hcp_id(), dataStyle);
			colCount++;

			cell = ReportFormats.cell(row, colCount,
					bean.getHcp_pan_number() == null ? "null" : bean.getHcp_pan_number(), dataStyleL);
			colCount++;

			cell = ReportFormats.cell(row, colCount, WordUtils.capitalizeFully(bean.getName_of_hcp()), dataStyleL);
			colCount++;

			cell = ReportFormats.cell(row, colCount,
					bean.getHcp_city() == null ? "" : WordUtils.capitalizeFully(bean.getHcp_city()), dataStyleL);
			colCount++;

			cell = ReportFormats.cell(row, colCount,
					bean.getHcp_state() == null ? "" : WordUtils.capitalizeFully(bean.getHcp_state()), dataStyleL);
			colCount++;

			cell = ReportFormats.cellNum(row, colCount, Double.valueOf(bean.getV_mth1().toString()), dataStyleR);
			colCount++;

			cell = ReportFormats.cellNum(row, colCount, Double.valueOf(bean.getV_mth2().toString()), dataStyleR);
			colCount++;

			cell = ReportFormats.cellNum(row, colCount, Double.valueOf(bean.getV_mth3().toString()), dataStyleR);
			colCount++;

			cell = ReportFormats.cellNum(row, colCount, Double.valueOf(bean.getV_mth4().toString()), dataStyleR);
			colCount++;

			cell = ReportFormats.cellNum(row, colCount, Double.valueOf(bean.getV_mth5().toString()), dataStyleR);
			colCount++;

			cell = ReportFormats.cellNum(row, colCount, Double.valueOf(bean.getV_mth6().toString()), dataStyleR);
			colCount++;

			cell = ReportFormats.cellNum(row, colCount, Double.valueOf(bean.getV_mth7().toString()), dataStyleR);
			colCount++;

			cell = ReportFormats.cellNum(row, colCount, Double.valueOf(bean.getV_mth8().toString()), dataStyleR);
			colCount++;

			cell = ReportFormats.cellNum(row, colCount, Double.valueOf(bean.getV_mth9().toString()), dataStyleR);
			colCount++;

			cell = ReportFormats.cellNum(row, colCount, Double.valueOf(bean.getV_mth10().toString()), dataStyleR);
			colCount++;

			cell = ReportFormats.cellNum(row, colCount, Double.valueOf(bean.getV_mth11().toString()), dataStyleR);
			colCount++;

			cell = ReportFormats.cellNum(row, colCount, Double.valueOf(bean.getV_mth12().toString()), dataStyleR);
			colCount++;

			total = Double.valueOf(bean.getV_mth1().toString()) + Double.valueOf(bean.getV_mth2().toString())
					+ Double.valueOf(bean.getV_mth3().toString()) + Double.valueOf(bean.getV_mth4().toString())
					+ Double.valueOf(bean.getV_mth5().toString()) + Double.valueOf(bean.getV_mth6().toString())
					+ Double.valueOf(bean.getV_mth7().toString()) + Double.valueOf(bean.getV_mth8().toString())
					+ Double.valueOf(bean.getV_mth9().toString()) + Double.valueOf(bean.getV_mth10().toString())
					+ Double.valueOf(bean.getV_mth11().toString()) + Double.valueOf(bean.getV_mth12().toString());

			cell = ReportFormats.cellNum(row, colCount, Double.valueOf(total), dataStyleR);
			colCount++;

			String s = String.format("%.2f", Double.valueOf(bean.getTds_payable().toString()));
			cell = ReportFormats.cellNum(row, colCount, Double.valueOf(bean.getTds_payable().toString()),
					dataStyleR);
			colCount++;

			cell = ReportFormats.cellNum(row, colCount, Double.valueOf(bean.getTds_paid().toString()), dataStyleR);
			colCount++;

			cell = ReportFormats.cellNum(row, colCount, Double.valueOf(bean.getTds_balance().toString()),
					dataStyleR);
			colCount++;

			cell = ReportFormats.cell(row, colCount, bean.getHcp_mobile_no() == null ? "" : bean.getHcp_mobile_no(),
					dataStyleR_);
			colCount++;

			cell = ReportFormats.cell(row, colCount, bean.getHcp_email_id() == null ? "" : bean.getHcp_email_id(),
					dataStyleL);
			colCount++;

			cell = ReportFormats.cell(row, colCount,
					bean.getHcp_address_1() == null ? "" : bean.getHcp_address_1().toString(), dataStyleL);
			colCount++;

			cell = ReportFormats.cell(row, colCount,
					bean.getHcp_address_2() == null ? "" : bean.getHcp_address_2().toString(), dataStyleL);
			colCount++;

			cell = ReportFormats.cell(row, colCount,
					bean.getHcp_address_3() == null ? "" : bean.getHcp_address_3().toString(), dataStyleL);
			colCount++;

			cell = ReportFormats.cell(row, colCount,
					bean.getHcp_address_4() == null ? "" : bean.getHcp_address_4().toString(), dataStyleR_);
			colCount++;

			rowCount += 1;
			row = sheet.createRow(rowCount);

			rowCount += 1;
			colCount = 1;
			row = sheet.createRow(rowCount);

			cell = row.createCell(colCount);
			sheet.addMergedRegion(new CellRangeAddress(rowCount, rowCount, colCount, colCount + 2));
			cell.setCellValue("Articles Supplied Under Scheme");
			cell.setCellStyle(columnStyle1);
			colCount = colCount + 3;

			cell = row.createCell(colCount);
			sheet.addMergedRegion(new CellRangeAddress(rowCount, rowCount, colCount, colCount + 3));
			cell.setCellValue("Delivery /Disbursement Details");
			cell.setCellStyle(columnStyle1);
			colCount = colCount + 4;
			
			cell = row.createCell(colCount);
			sheet.addMergedRegion(new CellRangeAddress(rowCount, rowCount, colCount, colCount + 5));
			cell.setCellValue("Delivery Document Details");
			cell.setCellStyle(columnStyle1);
			colCount = colCount + 6;

			
			cell = row.createCell(colCount);
			sheet.addMergedRegion(new CellRangeAddress(rowCount, rowCount, colCount, colCount + 1));
			cell.setCellValue("Sales Invoice Reference");
			cell.setCellStyle(columnStyle1);

			rowCount += 1;
			row = sheet.createRow(rowCount);
			colCount = 1;

			for (String heading1 : headings2) {
				cell = ReportFormats.cell(row, colCount, heading1, columnHeadingStyle1);
				colCount++;
			}
			Double total1 = 0.00;
			rowCount += 1;
			for (TdsApplicableProduct tds : list) {
				String newDate = tds.getDist_date().substring(3, 10);

				if (!newDate.equals(old) && !old.equals("old")) {

					row = sheet.createRow(rowCount);

					colCount = 1;
					row = sheet.createRow(rowCount);
					cell = row.createCell(colCount);
					sheet.addMergedRegion(new CellRangeAddress(rowCount, rowCount, colCount, colCount + 6));
					cell.setCellValue("Total: ");
					cell.setCellStyle(cellAlignment1);
					colCount = colCount + 7;

					cell = ReportFormats.cellNum(row, colCount, total1, Total);

					total1 = 0.00;
					rowCount += 1;
					row = sheet.createRow(rowCount);

					rowCount += 1;
				}

				colCount = 1;
				row = sheet.createRow(rowCount);

				cell = ReportFormats.cell(row, colCount,
						tds.getType().equals("Promotional Input") ? "PI" : tds.getType().equals("Sample") ? "PS" : "Articles",
								cellAlignment2);
				colCount++;

				cell = ReportFormats.cell(row, colCount, tds.getProd_code(), cellAlignment2);
				colCount++;

				cell = ReportFormats.cell(row, colCount, tds.getProd_name(), cellAlignment2);
				colCount++;

				cell = ReportFormats.cell(row, colCount,(tds.getDist_date() != null ? Utility.getdDateFormatDDMMYY_(tds.getDist_date() ): tds.getDist_date()) , cellAlignment2);
				colCount++;

				cell = ReportFormats.cellNum(row, colCount, Double.valueOf(tds.getUnits()), cellAlignmentR);
				colCount++;

				cell = ReportFormats.cellNum(row, colCount, Double.valueOf(tds.getDist_rate()), cellAlignment);
				colCount++;

				cell = ReportFormats.cellNum(row, colCount, Double.valueOf(tds.getDist_value()), cellAlignment);
				colCount++;

				cell = ReportFormats.cell(row, colCount, tds.getChallan_no(), cellAlignment2);
				colCount++;

				cell = ReportFormats.cell(row, colCount, WordUtils.capitalizeFully(tds.getChallan_date() != null ? Utility.getdDateFormatDDMMYY_(tds.getChallan_date()): tds.getChallan_date()), cellAlignment2);
				colCount++;

				cell = ReportFormats.cell(row, colCount, tds.getE_invoice_no(), cellAlignment2);
				colCount++;

				cell = ReportFormats.cell(row, colCount,(tds.getE_invoice_dt() != null ? Utility.getdDateFormatDDMMYY_(tds.getE_invoice_dt() ): tds.getE_invoice_dt()) , cellAlignment2);
				colCount++;
				
				cell = ReportFormats.cell(row, colCount, tds.getEway_bill_no(), cellAlignment2);
				colCount++;
				
				cell = ReportFormats.cell(row, colCount,(tds.getEway_bill_dt() != null ? Utility.getdDateFormatDDMMYY_(tds.getEway_bill_dt() ): tds.getEway_bill_dt()), cellAlignment2);
				colCount++;
				
				cell = ReportFormats.cell(row, colCount, tds.getInvoice_no(), cellAlignment2);
				colCount++;
				
				cell = ReportFormats.cell(row, colCount,(tds.getInvoice_date() != null ? Utility.getdDateFormatDDMMYY_(tds.getInvoice_date() ): tds.getInvoice_date()), cellAlignment2);
				colCount++;

				rowCount++;
				total1 = total1 + Double.valueOf(tds.getDist_value());
				grandTot = grandTot + Double.valueOf(tds.getDist_value());
				old = newDate;
			}

			colCount = 1;
			row = sheet.createRow(rowCount);
			cell = row.createCell(colCount);
			sheet.addMergedRegion(new CellRangeAddress(rowCount, rowCount, colCount, colCount + 5));
			cell.setCellValue("Total: ");
			cell.setCellStyle(cellAlignment1);
			colCount = colCount + 6;
			cell = ReportFormats.cellNum(row, colCount, total1, Total);

			FileOutputStream fileOutputStream = new FileOutputStream(filepath);
			workbook.write(fileOutputStream);
		} catch (Exception e) {
			System.out.println("Error Occurred :" +e.getMessage());// uncomment asneeded --;
		}
		return filename;
	}

}
