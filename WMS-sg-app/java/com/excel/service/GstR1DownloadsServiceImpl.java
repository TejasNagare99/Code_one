package com.excel.service;

import java.io.File;
import java.io.FileOutputStream;
import java.math.BigDecimal;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.excel.bean.ReportBean;
import com.excel.model.GSTR_1_B2BA_INV;
import com.excel.model.GSTR_1_B2B_INV;
import com.excel.model.ViewGSTR_1_B2CLA_INV;
import com.excel.model.ViewGSTR_1_B2CL_INV;
import com.excel.model.ViewGSTR_1_B2CSA_INV;
import com.excel.model.ViewGSTR_1_B2CS_INV;
import com.excel.model.ViewGSTR_1_HSN_INV;
import com.excel.model.ViewGSTR_1_TRANSFER;
import com.excel.repository.GstR1DownloadsRepository;
import com.excel.utility.ApachePoiExcelFormat;
import com.excel.utility.MedicoResources;
import com.excel.utility.Utility;

@Service
public class GstR1DownloadsServiceImpl implements GstR1DownloadsService {

	@Autowired
	private GstR1DownloadsRepository gstR1DownloadsRepository;
	
	
	@Override
	public List<ViewGSTR_1_B2CSA_INV> getGSTR_1_B2CSA_INVData(ReportBean bean) throws Exception {
		// TODO Auto-generated method stub
		return gstR1DownloadsRepository.getGSTR_1_B2CSA_INVData(bean);
	}
	
	@Override
	public List<GSTR_1_B2BA_INV> getB2BAINVReportData(ReportBean bean) {
		// TODO Auto-generated method stub
		return gstR1DownloadsRepository.getB2BAINVReportData(bean);
	}

	@Override
	public List<GSTR_1_B2B_INV> getB2BReportData(ReportBean bean) {
		// TODO Auto-generated method stub
		return gstR1DownloadsRepository.getB2BReportData(bean);
	}
	
	@Override
	public List<ViewGSTR_1_B2CS_INV> getB2CSReportData(ReportBean bean) throws Exception {

		return gstR1DownloadsRepository.getB2CSReportData(bean);
	}
	
	@Override
	public List<ViewGSTR_1_B2CL_INV> getB2CLReportData(ReportBean bean) throws Exception {
		return gstR1DownloadsRepository.getB2CLReportData(bean);
	}
	
	@Override
	public List<ViewGSTR_1_HSN_INV> getHSNReportData(ReportBean bean) throws Exception {
		return gstR1DownloadsRepository.getHSNReportData(bean);
	}
	
	@Override
	public List<ViewGSTR_1_B2CLA_INV> getB2CLAReportData(ReportBean bean ) throws Exception {
		return gstR1DownloadsRepository.getB2CLAReportData(bean);
	}
	
	@Override
	public List<ViewGSTR_1_TRANSFER> getGstR1TransferData(ReportBean bean) throws Exception {
		return gstR1DownloadsRepository.getGstR1TransferData(bean);
	}
	
	@Override
	public String getGstR1Reports(ReportBean bean, String realPath, String compNm, List<ViewGSTR_1_B2CS_INV> b2cs_INV,List<ViewGSTR_1_B2CL_INV> b2cl_inv,List<ViewGSTR_1_HSN_INV> hsn_inv,
			List<ViewGSTR_1_B2CLA_INV> b2cla_inv,List<ViewGSTR_1_TRANSFER> transfer, List<GSTR_1_B2BA_INV> gstr_1_b2ba_invList,
			List<GSTR_1_B2B_INV> gstr_1_b2b_invList, List<ViewGSTR_1_B2CSA_INV> gstr_1_b2csa_inv) {
		String filepath = "GST_R1Excel" + new Date().getTime() + ".xlsx";
		String actualpath = null;
		Workbook wwbook = null;
		try {
			System.out.println(
					"generate B2CSExcel Excel ----------------------------------------------------------------------");
			// Thread.sleep(10000);
			actualpath = null;

			actualpath = realPath + filepath;

			@SuppressWarnings("unused")
			File ff = null;
			ff = new File(actualpath);
			wwbook = new XSSFWorkbook();

			File file = new File(realPath);
			try {
				if (!file.exists()) {
					if (file.mkdir()) {
					} else {
						throw new RuntimeException("Could not create directory");
					}
				}
			} catch (Exception e) {
				throw new RuntimeException();
			}

			

//			Sheet b2ba_invsheet = wwbook.createSheet("B2BA_INV");		
			this.getB2CSExcel(bean, wwbook, b2cs_INV, compNm);
			this.getB2BAExcelData(bean, wwbook, gstr_1_b2ba_invList, compNm);
			this.getB2CLExcelData(bean, wwbook, b2cl_inv, compNm);
			this.getHSNExcelData(bean, wwbook, hsn_inv, compNm);
			this.getB2CSA_INVExcelData(bean, wwbook, gstr_1_b2csa_inv, compNm);
			this.getB2B_INVExcelData(bean, wwbook, gstr_1_b2b_invList, compNm);
			this.getB2CLAExcelData(bean, wwbook, b2cla_inv, compNm);
			this.getGstR1TransferExcelData(bean, wwbook, transfer, compNm);	





			FileOutputStream fileOut = new FileOutputStream(actualpath.toString());
			wwbook.write(fileOut);
			fileOut.close();

			System.out.println("Excel Created");

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if (wwbook != null)
					wwbook.close();
			} catch (Exception t) {
				// ignore
			}
		}

		return filepath;

	}
	
	private Sheet getB2CSExcel(ReportBean bean, Workbook wwbook, List<ViewGSTR_1_B2CS_INV> b2cs_INV, String compNm) {

		Sheet wsheet = null;
		try {
			wsheet = wwbook.createSheet("B2CS_INV");

			wsheet.createFreezePane(0, 6);

			ApachePoiExcelFormat xlsxExcelFormat = new ApachePoiExcelFormat();

			CellStyle sheetHeading = xlsxExcelFormat.SheetHeading(wwbook);
			CellStyle columnHeading = xlsxExcelFormat.columnHeading(wwbook);
			// CellStyle columnSubHeading = xlsxExcelFormat.columnSubHeading(wwbook);
			CellStyle rightAlignWthDecimal = xlsxExcelFormat.dataRightAlignWthDecimal(wwbook);
			CellStyle leftAlign = xlsxExcelFormat.dataLeftAlign(wwbook);
			CellStyle rightAlignWthDecimalBold = xlsxExcelFormat.dataBoldRightAlignWthDecimal(wwbook);
			CellStyle leftAlignBold = xlsxExcelFormat.dataBoldLeftAlign(wwbook);
			// CellStyle rightAlignWthDecimalWithBg =
			// xlsxExcelFormat.columnSubHeadingRightAlgn(wwbook);

			int col = 0, row = 0;
			XSSFRow hrow = (XSSFRow) wsheet.createRow(row);
			XSSFCell cell = hrow.createCell(col);
			cell.setCellStyle(sheetHeading);
			cell.setCellValue(compNm);
			wsheet.addMergedRegion(new CellRangeAddress(row, row, col, col + 5));

			row++;

			hrow = (XSSFRow) wsheet.createRow(row);
			cell = hrow.createCell(col);
			cell.setCellStyle(sheetHeading);
			// System.out.println("obj.getLocation().length() " + obj.getLoc_id());
//			if (obj.getLoc_id().compareTo(0l) == 0)
//				cell.setCellValue("All Locations");

//			else
				cell.setCellValue(bean.getLoc_name());
			wsheet.addMergedRegion(new CellRangeAddress(row, row, col, col + 5));
			row++;

			hrow = (XSSFRow) wsheet.createRow(row);
			cell = hrow.createCell(col);
			cell.setCellStyle(sheetHeading);
			cell.setCellValue("B2CS Report");
			wsheet.addMergedRegion(new CellRangeAddress(row, row, col, col + 5));
			row++;

			hrow = (XSSFRow) wsheet.createRow(row);
			cell = hrow.createCell(col);
			cell.setCellStyle(sheetHeading);
			cell.setCellValue("From "+MedicoResources.convertUtilDateToString_DD_MM_YYYY(bean.getStartDate()) +
					" to "+MedicoResources.convertUtilDateToString_DD_MM_YYYY(bean.getEndDate()));
			wsheet.addMergedRegion(new CellRangeAddress(row, row, col, col + 5));
			row++;

			hrow = (XSSFRow) wsheet.createRow(row);
			cell = hrow.createCell(col);
			cell.setCellStyle(sheetHeading);
			cell.setCellValue("Report Dated : " + Utility.convertDatetoString(new Date()));
			wsheet.addMergedRegion(new CellRangeAddress(row, row, col, col + 5));
			row++;

			hrow = (XSSFRow) wsheet.createRow(row);
			col = 0;

			cell = hrow.createCell(col);
			cell.setCellStyle(columnHeading);
			cell.setCellValue("Type");
			col++;

			cell = hrow.createCell(col);
			cell.setCellStyle(columnHeading);
			cell.setCellValue("Place of Supply");
			col++;
			cell = hrow.createCell(col);
			cell.setCellStyle(columnHeading);
			cell.setCellValue("Rate");
			col++;

			cell = hrow.createCell(col);
			cell.setCellStyle(columnHeading);
			cell.setCellValue("Taxable Rate");
			col++;
			cell = hrow.createCell(col);
			cell.setCellStyle(columnHeading);
			cell.setCellValue("Cess Amount");
			col++;

			cell = hrow.createCell(col);
			cell.setCellStyle(columnHeading);
			cell.setCellValue("E-Commerce GSTIN");
			col++;

			row++;
			col = 0;
			BigDecimal taxableAmtTot = BigDecimal.ZERO;
			BigDecimal cessAmtTot = BigDecimal.ZERO;
			for (ViewGSTR_1_B2CS_INV lst : b2cs_INV) {

				col = 0;

				hrow = (XSSFRow) wsheet.createRow(row);

				cell = hrow.createCell(col);
				cell.setCellStyle(leftAlign);
				cell.setCellValue(lst.getType());
				col++;

				cell = hrow.createCell(col);
				cell.setCellStyle(leftAlign);
				cell.setCellValue(lst.getPlace_of_supply());
				col++;

				cell = hrow.createCell(col);
				cell.setCellStyle(rightAlignWthDecimal);

				cell.setCellValue((Double.parseDouble(lst.getRate().toString()) < 0
						? -Double.parseDouble(lst.getRate().toString())
						: Double.parseDouble(lst.getRate().toString())));

				col++;

				cell = hrow.createCell(col);
				cell.setCellStyle(rightAlignWthDecimal);

				cell.setCellValue((Double.parseDouble(lst.getTaxable_value().toString()) < 0
						? -Double.parseDouble(lst.getTaxable_value().toString())
						: Double.parseDouble(lst.getTaxable_value().toString())));
				col++;

				cell = hrow.createCell(col);
				cell.setCellStyle(rightAlignWthDecimal);

				cell.setCellValue((Double.parseDouble(lst.getCess_amount().toString()) < 0
						? -Double.parseDouble(lst.getCess_amount().toString())
						: Double.parseDouble(lst.getCess_amount().toString())));
				col++;

				cell = hrow.createCell(col);
				cell.setCellStyle(leftAlign);
				cell.setCellValue(lst.getE_commerce_gstin());

				row++;
				taxableAmtTot = taxableAmtTot.add(lst.getTaxable_value());
				cessAmtTot = cessAmtTot.add(lst.getCess_amount());

			} // dataList ends here
			col = 0;

			hrow = (XSSFRow) wsheet.createRow(row);

			cell = hrow.createCell(col);
			cell.setCellStyle(leftAlignBold);
			cell.setCellValue("Total ");
			col++;

			cell = hrow.createCell(col);
			cell.setCellStyle(leftAlignBold);
			cell.setCellValue("");
			col++;

			cell = hrow.createCell(col);
			cell.setCellStyle(leftAlignBold);
			cell.setCellValue("");
			col++;

			cell = hrow.createCell(col);
			cell.setCellStyle(rightAlignWthDecimalBold);

			cell.setCellValue(
					(Double.parseDouble(taxableAmtTot.toString()) < 0 ? -Double.parseDouble(taxableAmtTot.toString())
							: Double.parseDouble(taxableAmtTot.toString())));

			col++;

			cell = hrow.createCell(col);
			cell.setCellStyle(rightAlignWthDecimalBold);

			cell.setCellValue(
					(Double.parseDouble(cessAmtTot.toString()) < 0 ? -Double.parseDouble(cessAmtTot.toString())
							: Double.parseDouble(cessAmtTot.toString())));
			cell = hrow.createCell(col);
			cell.setCellStyle(leftAlignBold);
			cell.setCellValue("");
			col++;
			
			cell = hrow.createCell(col);
			cell.setCellStyle(leftAlignBold);
			cell.setCellValue("");
			col++;

			row++;
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return wsheet;
		
	}

	private Sheet getB2CLExcelData(ReportBean bean, Workbook wwbook, List<ViewGSTR_1_B2CL_INV> dataList,String compNm) {
		Sheet wsheet = null;
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
		DateFormat inputDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.S");
		try {
			System.out.println(
					"generate B2CL_INVReport Excel ----------------------------------------------------------------------");
			// Thread.sleep(10000);

			wsheet = wwbook.createSheet("B2CL_INV");
			wsheet.createFreezePane(0, 6);

			ApachePoiExcelFormat xlsxExcelFormat = new ApachePoiExcelFormat();

			CellStyle sheetHeading = xlsxExcelFormat.SheetHeading(wwbook);
			CellStyle columnHeading = xlsxExcelFormat.columnHeading(wwbook);
			// CellStyle columnSubHeading = xlsxExcelFormat.columnSubHeading(wwbook);
			CellStyle rightAlignWthDecimal = xlsxExcelFormat.dataRightAlignWthDecimal(wwbook);
			CellStyle leftAlign = xlsxExcelFormat.dataLeftAlign(wwbook);
			CellStyle rightAlignWthDecimalBold = xlsxExcelFormat.dataBoldRightAlignWthDecimal(wwbook);
			CellStyle leftAlignBold = xlsxExcelFormat.dataBoldLeftAlign(wwbook);
			// CellStyle rightAlignWthDecimalWithBg =
			// xlsxExcelFormat.columnSubHeadingRightAlgn(wwbook);

			int col = 0, row = 0;
			XSSFRow hrow = (XSSFRow) wsheet.createRow(row);
			XSSFCell cell = hrow.createCell(col);
			cell.setCellStyle(sheetHeading);
			cell.setCellValue(compNm);
			wsheet.addMergedRegion(new CellRangeAddress(row, row, col, col + 7));

			row++;

			hrow = (XSSFRow) wsheet.createRow(row);
			cell = hrow.createCell(col);
			cell.setCellStyle(sheetHeading);
			// System.out.println("obj.getLocation().length() " + obj.getLoc_id());
//			if (obj.getLoc_id().compareTo(0l) == 0)
//				cell.setCellValue("All Locations");

//			else
				cell.setCellValue(bean.getLoc_name());
			wsheet.addMergedRegion(new CellRangeAddress(row, row, col, col + 7));
			row++;

			hrow = (XSSFRow) wsheet.createRow(row);
			cell = hrow.createCell(col);
			cell.setCellStyle(sheetHeading);
			cell.setCellValue("B2CL_INV");
			wsheet.addMergedRegion(new CellRangeAddress(row, row, col, col + 7));
			row++;

			hrow = (XSSFRow) wsheet.createRow(row);
			cell = hrow.createCell(col);
			cell.setCellStyle(sheetHeading);
			cell.setCellValue("From "+MedicoResources.convertUtilDateToString_DD_MM_YYYY(bean.getStartDate()) +
					" to "+MedicoResources.convertUtilDateToString_DD_MM_YYYY(bean.getEndDate()));
			wsheet.addMergedRegion(new CellRangeAddress(row, row, col, col + 7));
			row++;

			hrow = (XSSFRow) wsheet.createRow(row);
			cell = hrow.createCell(col);
			cell.setCellStyle(sheetHeading);
			cell.setCellValue("Report Dated : " + Utility.convertDatetoString(new Date()));
			wsheet.addMergedRegion(new CellRangeAddress(row, row, col, col + 7));
			row++;

			hrow = (XSSFRow) wsheet.createRow(row);
			col = 0;

			cell = hrow.createCell(col);
			cell.setCellStyle(columnHeading);
			cell.setCellValue("Invoice Number");
			col++;

			cell = hrow.createCell(col);
			cell.setCellStyle(columnHeading);
			cell.setCellValue("Invoice date");
			col++;

			cell = hrow.createCell(col);
			cell.setCellStyle(columnHeading);
			cell.setCellValue("Invoice Value");
			col++;

			cell = hrow.createCell(col);
			cell.setCellStyle(columnHeading);
			cell.setCellValue("Place Of Supply");
			col++;

			cell = hrow.createCell(col);
			cell.setCellStyle(columnHeading);
			cell.setCellValue("Rate");
			col++;

			cell = hrow.createCell(col);
			cell.setCellStyle(columnHeading);
			cell.setCellValue("Taxable Value");
			col++;

			cell = hrow.createCell(col);
			cell.setCellStyle(columnHeading);
			cell.setCellValue("Cess Amount");
			col++;

			cell = hrow.createCell(col);
			cell.setCellStyle(columnHeading);
			cell.setCellValue("E-Commerce GSTIN");
			col++;

			row++;
			col = 0;
			BigDecimal invoiceTotal = BigDecimal.ZERO;
			BigDecimal taxableTotal = BigDecimal.ZERO;
			BigDecimal cessTotal = BigDecimal.ZERO;
			for (ViewGSTR_1_B2CL_INV lst : dataList) {

				col = 0;

				hrow = (XSSFRow) wsheet.createRow(row);

				cell = hrow.createCell(col);
				cell.setCellStyle(leftAlign);
				cell.setCellValue(lst.getInvoice_number());
				col++;

				cell = hrow.createCell(col);
				cell.setCellStyle(leftAlign);
				String invDate = lst.getInvoice_date() == null ? ""
						: (lst.getInvoice_date().equals("") ? ""
								: sdf.format(inputDateFormat.parse(lst.getInvoice_date())));
				cell.setCellValue(invDate);
				col++;

				cell = hrow.createCell(col);
				cell.setCellStyle(rightAlignWthDecimal);
				cell.setCellValue(lst.getInvoice_value().doubleValue());
				col++;

				cell = hrow.createCell(col);
				cell.setCellStyle(leftAlign);
				cell.setCellValue(lst.getPlace_of_supply());
				col++;

				cell = hrow.createCell(col);
				cell.setCellStyle(rightAlignWthDecimal);
				cell.setCellValue(lst.getRate().doubleValue());
				col++;

				cell = hrow.createCell(col);
				cell.setCellStyle(rightAlignWthDecimal);
				cell.setCellValue(lst.getTaxable_value().doubleValue());
				col++;

				cell = hrow.createCell(col);
				cell.setCellStyle(rightAlignWthDecimal);
				cell.setCellValue(lst.getCess_amount().doubleValue());
				col++;

				cell = hrow.createCell(col);
				cell.setCellStyle(leftAlign);
				cell.setCellValue(lst.getE_commerce_gstin());

				row++;
				invoiceTotal = invoiceTotal.add(lst.getInvoice_value());
				taxableTotal = taxableTotal.add(lst.getTaxable_value());
				cessTotal = cessTotal.add(lst.getCess_amount());
			}

			col = 0;

			hrow = (XSSFRow) wsheet.createRow(row);

			cell = hrow.createCell(col);
			cell.setCellStyle(leftAlignBold);
			cell.setCellValue("Total");
			col++;

			cell = hrow.createCell(col);
			cell.setCellStyle(leftAlignBold);
			cell.setCellValue("");
			col++;

			cell = hrow.createCell(col);
			cell.setCellStyle(rightAlignWthDecimalBold);
			cell.setCellValue(invoiceTotal.doubleValue());
			col++;

			for (int i = 0; i < 2; i++) {
				cell = hrow.createCell(col);
				cell.setCellStyle(leftAlignBold);
				cell.setCellValue("");
				col++;
			}

			cell = hrow.createCell(col);
			cell.setCellStyle(rightAlignWthDecimalBold);
			cell.setCellValue(taxableTotal.doubleValue());
			col++;

			cell = hrow.createCell(col);
			cell.setCellStyle(rightAlignWthDecimalBold);
			cell.setCellValue(cessTotal.doubleValue());
			col++;

			cell = hrow.createCell(col);
			cell.setCellStyle(leftAlignBold);
			cell.setCellValue("");

		} catch (Exception e) {
			e.printStackTrace();
		}
		return wsheet;
	}
	
	private Sheet getHSNExcelData(ReportBean bean, Workbook wwbook, List<ViewGSTR_1_HSN_INV> dataList,String compNm) {
		Sheet wsheet = null;
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
		DateFormat inputDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.S");
		try {
			System.out.println(
					"generate HSN_INVReport Excel ----------------------------------------------------------------------");
			// Thread.sleep(10000);

			wsheet = wwbook.createSheet("HSN_INV");
			wsheet.createFreezePane(0, 6);

			ApachePoiExcelFormat xlsxExcelFormat = new ApachePoiExcelFormat();

			CellStyle sheetHeading = xlsxExcelFormat.SheetHeading(wwbook);
			CellStyle columnHeading = xlsxExcelFormat.columnHeading(wwbook);
			// CellStyle columnSubHeading = xlsxExcelFormat.columnSubHeading(wwbook);
			CellStyle rightAlignWthDecimal = xlsxExcelFormat.dataRightAlignWthDecimal(wwbook);
			CellStyle leftAlign = xlsxExcelFormat.dataLeftAlign(wwbook);
			CellStyle rightAlignWthDecimalBold = xlsxExcelFormat.dataBoldRightAlignWthDecimal(wwbook);
			CellStyle leftAlignBold = xlsxExcelFormat.dataBoldLeftAlign(wwbook);
			// CellStyle rightAlignWthDecimalWithBg =
			// xlsxExcelFormat.columnSubHeadingRightAlgn(wwbook);

			int col = 0, row = 0;
			XSSFRow hrow = (XSSFRow) wsheet.createRow(row);
			XSSFCell cell = hrow.createCell(col);
			cell.setCellStyle(sheetHeading);
			cell.setCellValue(compNm);
			wsheet.addMergedRegion(new CellRangeAddress(row, row, col, col + 10));

			row++;

			hrow = (XSSFRow) wsheet.createRow(row);
			cell = hrow.createCell(col);
			cell.setCellStyle(sheetHeading);
			// System.out.println("obj.getLocation().length() " + obj.getLoc_id());
//			if (obj.getLoc_id().compareTo(0l) == 0)
//				cell.setCellValue("All Locations");

//			else
				cell.setCellValue(bean.getLoc_name());
			wsheet.addMergedRegion(new CellRangeAddress(row, row, col, col + 10));
			row++;

			hrow = (XSSFRow) wsheet.createRow(row);
			cell = hrow.createCell(col);
			cell.setCellStyle(sheetHeading);
			cell.setCellValue("HSN_INV");
			wsheet.addMergedRegion(new CellRangeAddress(row, row, col, col + 10));
			row++;

			hrow = (XSSFRow) wsheet.createRow(row);
			cell = hrow.createCell(col);
			cell.setCellStyle(sheetHeading);
			cell.setCellValue("From "+MedicoResources.convertUtilDateToString_DD_MM_YYYY(bean.getStartDate()) +
					" to "+MedicoResources.convertUtilDateToString_DD_MM_YYYY(bean.getEndDate()));
			wsheet.addMergedRegion(new CellRangeAddress(row, row, col, col + 10));
			row++;

			hrow = (XSSFRow) wsheet.createRow(row);
			cell = hrow.createCell(col);
			cell.setCellStyle(sheetHeading);
			cell.setCellValue("Report Dated : " + Utility.convertDatetoString(new Date()));
			wsheet.addMergedRegion(new CellRangeAddress(row, row, col, col + 10));
			row++;

			hrow = (XSSFRow) wsheet.createRow(row);
			col = 0;

			cell = hrow.createCell(col);
			cell.setCellStyle(columnHeading);
			cell.setCellValue("HSN");
			col++;

			cell = hrow.createCell(col);
			cell.setCellStyle(columnHeading);
			cell.setCellValue("Description");
			col++;

			cell = hrow.createCell(col);
			cell.setCellStyle(columnHeading);
			cell.setCellValue("UQC");
			col++;

			cell = hrow.createCell(col);
			cell.setCellStyle(columnHeading);
			cell.setCellValue("Total Quantity");
			col++;

			cell = hrow.createCell(col);
			cell.setCellStyle(columnHeading);
			cell.setCellValue("Total Value");
			col++;

			cell = hrow.createCell(col);
			cell.setCellStyle(columnHeading);
			cell.setCellValue("Taxable Value");
			col++;

			cell = hrow.createCell(col);
			cell.setCellStyle(columnHeading); // added
			cell.setCellValue("RATE");
			col++;

			cell = hrow.createCell(col);
			cell.setCellStyle(columnHeading);
			cell.setCellValue("Integrated Tax Amount");
			col++;

			cell = hrow.createCell(col);
			cell.setCellStyle(columnHeading);
			cell.setCellValue("Central Tax Amount");
			col++;

			cell = hrow.createCell(col);
			cell.setCellStyle(columnHeading);
			cell.setCellValue("State/UT Tax Amount");
			col++;

			cell = hrow.createCell(col);
			cell.setCellStyle(columnHeading);
			cell.setCellValue("Cess Amount");
			col++;

			row++;
			col = 0;
			BigDecimal Total_Value = BigDecimal.ZERO;
			BigDecimal Taxable_Value = BigDecimal.ZERO;
			BigDecimal Integrated_Tax = BigDecimal.ZERO;
			BigDecimal Central_Tax = BigDecimal.ZERO;
			BigDecimal StateUT_Tax_Amount = BigDecimal.ZERO;
			BigDecimal Cess_Amount = BigDecimal.ZERO;

			for (ViewGSTR_1_HSN_INV lst : dataList) {

				col = 0;

				hrow = (XSSFRow) wsheet.createRow(row);

				cell = hrow.createCell(col);
				cell.setCellStyle(leftAlign);
				cell.setCellValue(lst.getHsn());
				col++;

				cell = hrow.createCell(col);
				cell.setCellStyle(leftAlign);
				cell.setCellValue(lst.getDescription());
				col++;

				cell = hrow.createCell(col);
				cell.setCellStyle(leftAlign);
				cell.setCellValue(lst.getUqc());
				col++;

				cell = hrow.createCell(col);
				cell.setCellStyle(rightAlignWthDecimal);
				cell.setCellValue(lst.getTotal_quantity().doubleValue());
				col++;

				cell = hrow.createCell(col);
				cell.setCellStyle(rightAlignWthDecimal);
				cell.setCellValue(lst.getTotal_value().doubleValue());
				col++;

				cell = hrow.createCell(col);
				cell.setCellStyle(rightAlignWthDecimal);
				cell.setCellValue(lst.getTaxable_value().doubleValue());
				col++;

				cell = hrow.createCell(col);
				cell.setCellStyle(rightAlignWthDecimal);
				cell.setCellValue(lst.getGst_rate().doubleValue());
				col++;

				cell = hrow.createCell(col);
				cell.setCellStyle(rightAlignWthDecimal);
				cell.setCellValue(lst.getIntegrated_tax_amount().doubleValue());
				col++;

				cell = hrow.createCell(col);
				cell.setCellStyle(rightAlignWthDecimal);
				cell.setCellValue(lst.getCentral_tax_amount().doubleValue());
				col++;

				cell = hrow.createCell(col);
				cell.setCellStyle(rightAlignWthDecimal);
				cell.setCellValue(lst.getState_tax_amount().doubleValue());
				col++;

				cell = hrow.createCell(col);
				cell.setCellStyle(rightAlignWthDecimal);
				cell.setCellValue(lst.getCess_amount().doubleValue());

				row++;

				Total_Value = Total_Value.add(lst.getTotal_value());
				Taxable_Value = Taxable_Value.add(lst.getTaxable_value());
				Integrated_Tax = Integrated_Tax.add(lst.getIntegrated_tax_amount());
				Central_Tax = Central_Tax.add(lst.getCentral_tax_amount());
				StateUT_Tax_Amount = StateUT_Tax_Amount.add(lst.getState_tax_amount());
				Cess_Amount = Cess_Amount.add(lst.getCess_amount());

			}

			col = 0;

			hrow = (XSSFRow) wsheet.createRow(row);

			cell = hrow.createCell(col);
			cell.setCellStyle(leftAlignBold);
			cell.setCellValue("Total");
			col++;

			for (int i = 0; i < 3; i++) {
				cell = hrow.createCell(col);
				cell.setCellStyle(leftAlignBold);
				cell.setCellValue("");
				col++;
			}

			cell = hrow.createCell(col);
			cell.setCellStyle(rightAlignWthDecimalBold);
			cell.setCellValue(Total_Value.doubleValue());
			col++;

			cell = hrow.createCell(col);
			cell.setCellStyle(rightAlignWthDecimalBold);
			cell.setCellValue(Taxable_Value.doubleValue());
			col++;

			cell = hrow.createCell(col);
			cell.setCellStyle(rightAlignWthDecimalBold);
			cell.setCellValue(" ");
			col++;

			cell = hrow.createCell(col);
			cell.setCellStyle(rightAlignWthDecimalBold);
			cell.setCellValue(Integrated_Tax.doubleValue());
			col++;

			cell = hrow.createCell(col);
			cell.setCellStyle(rightAlignWthDecimalBold);
			cell.setCellValue(Central_Tax.doubleValue());
			col++;

			cell = hrow.createCell(col);
			cell.setCellStyle(rightAlignWthDecimalBold);
			cell.setCellValue(StateUT_Tax_Amount.doubleValue());
			col++;

			cell = hrow.createCell(col);
			cell.setCellStyle(rightAlignWthDecimalBold);
			cell.setCellValue(Cess_Amount.doubleValue());

			System.out.println("Excel Created");

		} catch (Exception e) {
			e.printStackTrace();
		}

		return wsheet;
	}
	
	private Sheet getB2CLAExcelData(ReportBean bean, Workbook wwbook, List<ViewGSTR_1_B2CLA_INV> dataList,String compNm) {
		Sheet wsheet = null;
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
		DateFormat inputDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.S");

		try {
			System.out.println(
					"generate B2CLA_REPORT Excel ----------------------------------------------------------------------");
			// Thread.sleep(10000);

			wsheet = wwbook.createSheet("B2CLA_INV");
			wsheet.createFreezePane(0, 6);

			ApachePoiExcelFormat xlsxExcelFormat = new ApachePoiExcelFormat();

			CellStyle sheetHeading = xlsxExcelFormat.SheetHeading(wwbook);
			CellStyle columnHeading = xlsxExcelFormat.columnHeading(wwbook);
			// CellStyle columnSubHeading = xlsxExcelFormat.columnSubHeading(wwbook);
			CellStyle rightAlignWthDecimal = xlsxExcelFormat.dataRightAlignWthDecimal(wwbook);
			CellStyle leftAlign = xlsxExcelFormat.dataLeftAlign(wwbook);
			CellStyle leftAlignBold = xlsxExcelFormat.dataBoldLeftAlign(wwbook);
			CellStyle rightAlignWthDecimalBold = xlsxExcelFormat.dataBoldRightAlignWthDecimal(wwbook);
			// CellStyle rightAlignWthDecimalWithBg =
			// xlsxExcelFormat.columnSubHeadingRightAlgn(wwbook);

			int col = 0, row = 0;
			XSSFRow hrow = (XSSFRow) wsheet.createRow(row);
			XSSFCell cell = hrow.createCell(col);
			cell.setCellStyle(sheetHeading);
			cell.setCellValue(compNm);
			wsheet.addMergedRegion(new CellRangeAddress(row, row, col, col + 9));

			row++;

			hrow = (XSSFRow) wsheet.createRow(row);
			cell = hrow.createCell(col);
			cell.setCellStyle(sheetHeading);
			// System.out.println("obj.getLocation().length() " + obj.getLoc_id());
//			if (obj.getLoc_id().compareTo(0l) == 0)
//				cell.setCellValue("All Locations");

//			else
				cell.setCellValue(bean.getLoc_name());
			wsheet.addMergedRegion(new CellRangeAddress(row, row, col, col + 9));
			row++;

			hrow = (XSSFRow) wsheet.createRow(row);
			cell = hrow.createCell(col);
			cell.setCellStyle(sheetHeading);
			cell.setCellValue("B2CLA");
			wsheet.addMergedRegion(new CellRangeAddress(row, row, col, col + 9));
			row++;

			hrow = (XSSFRow) wsheet.createRow(row);
			cell = hrow.createCell(col);
			cell.setCellStyle(sheetHeading);
			cell.setCellValue("From "+MedicoResources.convertUtilDateToString_DD_MM_YYYY(bean.getStartDate()) +
					" to "+MedicoResources.convertUtilDateToString_DD_MM_YYYY(bean.getEndDate()));
			wsheet.addMergedRegion(new CellRangeAddress(row, row, col, col + 9));
			row++;

			hrow = (XSSFRow) wsheet.createRow(row);
			cell = hrow.createCell(col);
			cell.setCellStyle(sheetHeading);
			cell.setCellValue("Report Dated : " + Utility.convertDatetoString(new Date()));
			wsheet.addMergedRegion(new CellRangeAddress(row, row, col, col + 9));
			row++;

			hrow = (XSSFRow) wsheet.createRow(row);
			col = 0;

			cell = hrow.createCell(col);
			cell.setCellStyle(columnHeading);
			cell.setCellValue("Original Invoice Number");
			col++;

			cell = hrow.createCell(col);
			cell.setCellStyle(columnHeading);
			cell.setCellValue("Original Invoice date");
			col++;

			cell = hrow.createCell(col);
			cell.setCellStyle(columnHeading);
			cell.setCellValue("Original Place Of Supply");
			col++;

			cell = hrow.createCell(col);
			cell.setCellStyle(columnHeading);
			cell.setCellValue("Revised Invoice Number");
			col++;

			cell = hrow.createCell(col);
			cell.setCellStyle(columnHeading);
			cell.setCellValue("Revised Invoice date");
			col++;

			cell = hrow.createCell(col);
			cell.setCellStyle(columnHeading);
			cell.setCellValue("Invoice Value");
			col++;

			cell = hrow.createCell(col);
			cell.setCellStyle(columnHeading);
			cell.setCellValue("Rate");
			col++;

			cell = hrow.createCell(col);
			cell.setCellStyle(columnHeading);
			cell.setCellValue("Taxable Value");
			col++;

			cell = hrow.createCell(col);
			cell.setCellStyle(columnHeading);
			cell.setCellValue("Cess Amount");
			col++;

			cell = hrow.createCell(col);
			cell.setCellStyle(columnHeading);
			cell.setCellValue("E-Commerce GSTIN");

			row++;
			col = 0;

			BigDecimal Invoice_Value = BigDecimal.ZERO;
			BigDecimal Taxable_Value = BigDecimal.ZERO;
			BigDecimal Cess_Amount = BigDecimal.ZERO;

			for (ViewGSTR_1_B2CLA_INV lst : dataList) {

				col = 0;

				hrow = (XSSFRow) wsheet.createRow(row);

				cell = hrow.createCell(col);
				cell.setCellStyle(leftAlign);
				cell.setCellValue(lst.getOriginal_invoice_number());
				col++;

				String ogInvDate = lst.getOriginal_invoice_date() == null ? ""
						: (lst.getOriginal_invoice_date().equals("") ? ""
								: sdf.format(inputDateFormat.parse(lst.getOriginal_invoice_date())));
				cell = hrow.createCell(col);
				cell.setCellStyle(leftAlign);
				cell.setCellValue(ogInvDate);
				col++;

				cell = hrow.createCell(col);
				cell.setCellStyle(leftAlign);
				cell.setCellValue(lst.getOrg_place_of_supply());
				col++;

				cell = hrow.createCell(col);
				cell.setCellStyle(leftAlign);
				cell.setCellValue(lst.getRevised_invoice_number());
				col++;

				String revInvDate = lst.getRevised_invoice_date() == null ? ""
						: (lst.getRevised_invoice_date().equals("") ? ""
								: sdf.format(inputDateFormat.parse(lst.getRevised_invoice_date())));

				cell = hrow.createCell(col);
				cell.setCellStyle(leftAlign);
				cell.setCellValue(revInvDate);
				col++;

				cell = hrow.createCell(col);
				cell.setCellStyle(rightAlignWthDecimal);
				cell.setCellValue(lst.getInvoice_value().doubleValue());
				col++;

				cell = hrow.createCell(col);
				cell.setCellStyle(leftAlign);
				cell.setCellValue(lst.getRate());
				col++;

				cell = hrow.createCell(col);
				cell.setCellStyle(rightAlignWthDecimal);
				cell.setCellValue(lst.getTaxable_value().doubleValue());
				col++;

				cell = hrow.createCell(col);
				cell.setCellStyle(rightAlignWthDecimal);
				cell.setCellValue(lst.getCess_amount().doubleValue());
				col++;

				cell = hrow.createCell(col);
				cell.setCellStyle(leftAlign);
				cell.setCellValue(lst.getE_com_gstin());

				row++;

				Invoice_Value = Invoice_Value.add(lst.getInvoice_value());
				Taxable_Value = Taxable_Value.add(lst.getTaxable_value());
				Cess_Amount = Cess_Amount.add(lst.getCess_amount());

			} // dataList ends here

			col = 0;

			hrow = (XSSFRow) wsheet.createRow(row);

			cell = hrow.createCell(col);
			cell.setCellStyle(leftAlignBold);
			cell.setCellValue("Total ");
			col++;

			for (int i = 0; i < 4; i++) {
				cell = hrow.createCell(col);
				cell.setCellStyle(leftAlignBold);
				cell.setCellValue("");
				col++;

			}

			cell = hrow.createCell(col);
			cell.setCellStyle(rightAlignWthDecimalBold);
			cell.setCellValue(Invoice_Value.doubleValue());
			col++;

			cell = hrow.createCell(col);
			cell.setCellStyle(rightAlignWthDecimalBold);
			cell.setCellValue("");
			col++;

			cell = hrow.createCell(col);
			cell.setCellStyle(rightAlignWthDecimalBold);
			cell.setCellValue(Taxable_Value.doubleValue());
			col++;

			cell = hrow.createCell(col);
			cell.setCellStyle(rightAlignWthDecimalBold);
			cell.setCellValue(Cess_Amount.doubleValue());
			col++;

			cell = hrow.createCell(col);
			cell.setCellStyle(rightAlignWthDecimalBold);
			cell.setCellValue("");
			col++;

			row++;

			System.out.println("Excel Created");

		} catch (Exception e) {
			e.printStackTrace();
		}

		return wsheet;

	}
	
	private Sheet getGstR1TransferExcelData(ReportBean bean, Workbook wwbook, List<ViewGSTR_1_TRANSFER> dataList,String compNm) {

		Sheet wsheet = null;
		try {
			System.out.println(
					"GSTR1_TransferChallanReg CHALLAN REGISTER  Excel ----------------------------------------------------------------------");

			wsheet = wwbook.createSheet("GSTR1_TRANSFER");
			wsheet.createFreezePane(0, 6);

			ApachePoiExcelFormat xlsxExcelFormat = new ApachePoiExcelFormat();
			CellStyle sheetHeading = xlsxExcelFormat.SheetHeading(wwbook);
			CellStyle columnHeading = xlsxExcelFormat.columnHeading(wwbook);
			CellStyle columnSubHeading = xlsxExcelFormat.columnSubHeading(wwbook);
			CellStyle columnSubHeadingLemon = xlsxExcelFormat.columnSubHeadingLemon(wwbook);
			CellStyle columnSubHeadingBlue = xlsxExcelFormat.columnSubHeadingBlue(wwbook);
			CellStyle columnSubHeadingTurq = xlsxExcelFormat.columnSubHeadingTurq(wwbook);
			CellStyle rightAlignWthDecimal = xlsxExcelFormat.dataRightAlignWthDecimal(wwbook);
			CellStyle leftAlign = xlsxExcelFormat.dataLeftAlign(wwbook);
			// CellStyle columnSubHeadingRight =
			// xlsxExcelFormat.columnSubHeadingRightAlgn(wwbook);
//			CellStyle columnSubHeadingRightLemon = xlsxExcelFormat.columnSubHeadingRightAlgnLemon(wwbook);
//			CellStyle columnSubHeadingRightBlue = xlsxExcelFormat.columnSubHeadingRightAlgnBlue(wwbook);
//			CellStyle columnSubHeadingRightTurq = xlsxExcelFormat.columnSubHeadingRightAlgnTurq(wwbook);
			CellStyle rightAlignWthDecimalBold = xlsxExcelFormat.dataBoldRightAlignWthDecimal(wwbook);
			CellStyle leftAlignBold = xlsxExcelFormat.dataBoldLeftAlign(wwbook);

			short col = 0, row = 0;
			XSSFRow hrow = (XSSFRow) wsheet.createRow(row);
			XSSFCell cell = hrow.createCell(col);
			cell.setCellStyle(sheetHeading);
			cell.setCellValue(compNm);
			wsheet.addMergedRegion(new CellRangeAddress(row, row, col, col + 11));
			row++;

			hrow = (XSSFRow) wsheet.createRow(row);
			cell = hrow.createCell(col);
			cell.setCellStyle(sheetHeading);
			cell.setCellValue("Transfer Challan Register Summary Report(Date wise)");

			wsheet.addMergedRegion(new CellRangeAddress(row, row, col, col + 11));
			row++;

			hrow = (XSSFRow) wsheet.createRow(row);
			cell = hrow.createCell(col);
			cell.setCellStyle(sheetHeading);
			cell.setCellValue("From "+MedicoResources.convertUtilDateToString_DD_MM_YYYY(bean.getStartDate()) +
					" to "+MedicoResources.convertUtilDateToString_DD_MM_YYYY(bean.getEndDate()));
			wsheet.addMergedRegion(new CellRangeAddress(row, row, col, col + 11));
			row++;

			hrow = (XSSFRow) wsheet.createRow(row);
			cell = hrow.createCell(col);
			cell.setCellStyle(sheetHeading);
//			if (obj.getLoc_id().compareTo(0l) == 0) {
//				cell.setCellValue("Sending Location : All Locations");
//			} else {
				cell.setCellValue("Sending Location :" + bean.getLoc_name());
//			}
			wsheet.addMergedRegion(new CellRangeAddress(row, row, col, col + 11));
			row++;

			hrow = (XSSFRow) wsheet.createRow(row);
			cell = hrow.createCell(col);
			cell.setCellStyle(sheetHeading);
			cell.setCellValue("Report Dated : " + Utility.convertDatetoString(new Date()));
			wsheet.addMergedRegion(new CellRangeAddress(row, row, col, col + 11));
			row++;

			hrow = (XSSFRow) wsheet.createRow(row);
			col = 0;

			cell = hrow.createCell(col);
			cell.setCellStyle(columnHeading);
			cell.setCellValue("GSTIN/UIN of Recipient");
			col++;

			cell = hrow.createCell(col);
			cell.setCellStyle(columnHeading);
			cell.setCellValue("Receiver Name");
			col++;

			cell = hrow.createCell(col);
			cell.setCellStyle(columnHeading);
			cell.setCellValue("Invoice Number");
			col++;

			cell = hrow.createCell(col);
			cell.setCellStyle(columnHeading);
			cell.setCellValue("Invoice Date");
			col++;

			cell = hrow.createCell(col);
			cell.setCellStyle(columnHeading);
			cell.setCellValue("Invoice Value");
			col++;

			cell = hrow.createCell(col);
			cell.setCellStyle(columnHeading);
			cell.setCellValue("Place of Supply");

			col++;

			cell = hrow.createCell(col);
			cell.setCellStyle(columnHeading);
			cell.setCellValue("Reverse Charge");

			col++;

			cell = hrow.createCell(col);
			cell.setCellStyle(columnHeading);
			cell.setCellValue("Invoice Type");

			col++;

			cell = hrow.createCell(col);
			cell.setCellStyle(columnHeading);
			cell.setCellValue("E-commerce GSTN");

			col++;

			cell = hrow.createCell(col);
			cell.setCellStyle(columnHeading);
			cell.setCellValue("Rate");

			col++;

			cell = hrow.createCell(col);
			cell.setCellStyle(columnHeading);
			cell.setCellValue("Taxable Value");

			col++;

			cell = hrow.createCell(col);
			cell.setCellStyle(columnHeading);
			cell.setCellValue("Cess Amount");
			col++;

			BigDecimal taxableAmtTot = BigDecimal.ZERO;
			BigDecimal cessAmtTot = BigDecimal.ZERO;

			for (ViewGSTR_1_TRANSFER list : dataList) {

				row++;
				col = 0;
				hrow = (XSSFRow) wsheet.createRow(row);

				cell = hrow.createCell(col);
				cell.setCellStyle(leftAlign);
				cell.setCellValue(list.getSupp_gstin());
				col++;

				cell = hrow.createCell(col);
				cell.setCellStyle(leftAlign);
				cell.setCellValue(list.getReceiver_name());
				col++;

				cell = hrow.createCell(col);
				cell.setCellStyle(leftAlign);
				cell.setCellValue(list.getSupply_bill_no());
				col++;

				String invDate = MedicoResources.getRptDateFormatddMMyyyy(list.getSupply_date().trim());
				
				cell = hrow.createCell(col);
				cell.setCellStyle(rightAlignWthDecimal);
				cell.setCellValue(invDate == null ? "" : invDate);
				col++;

				cell = hrow.createCell(col);
				cell.setCellStyle(rightAlignWthDecimal);
				cell.setCellValue((list.getSup_inv_value() == null ? 0l : list.getSup_inv_value().doubleValue()));
				col++;

				cell = hrow.createCell(col);
				cell.setCellStyle(leftAlign);
				cell.setCellValue(list.getPlace_of_supply());
				col++;

				cell = hrow.createCell(col);
				cell.setCellStyle(leftAlign);
				cell.setCellValue(list.getReverse_chgs());
				col++;

				cell = hrow.createCell(col);
				cell.setCellStyle(leftAlign);
				cell.setCellValue(list.getInvoice_type());
				col++;

				cell = hrow.createCell(col);
				cell.setCellStyle(leftAlign);
				cell.setCellValue(list.getE_commmerce_gstin());
				col++;

				cell = hrow.createCell(col);
				cell.setCellStyle(rightAlignWthDecimal);
				cell.setCellValue(list.getRate() == null ? 0l : list.getRate().doubleValue());
				col++;

				cell = hrow.createCell(col);
				cell.setCellStyle(rightAlignWthDecimal);
				cell.setCellValue(list.getTaxable_amt() == null ? 0l : list.getTaxable_amt().doubleValue());
				col++;

				cell = hrow.createCell(col);
				cell.setCellStyle(rightAlignWthDecimal);
				cell.setCellValue(list.getCess_amount() == null ? 0l : list.getCess_amount().doubleValue());
				col++;

				taxableAmtTot = taxableAmtTot
						.add(list.getTaxable_amt() == null ? BigDecimal.ZERO : list.getTaxable_amt());
				cessAmtTot = cessAmtTot.add(list.getCess_amount() == null ? BigDecimal.ZERO : list.getCess_amount());

			}

			// report total
			col = 0;
			row++;

			hrow = (XSSFRow) wsheet.createRow(row);

			cell = hrow.createCell(col);
			cell.setCellStyle(leftAlignBold);
			cell.setCellValue("Total ");
			col++;

			for (int i = 0; i < 9; i++) {
				cell = hrow.createCell(col);
				cell.setCellStyle(leftAlignBold);
				cell.setCellValue("");
				col++;

			}

			cell = hrow.createCell(col);
			cell.setCellStyle(rightAlignWthDecimalBold);
			cell.setCellValue(taxableAmtTot == null ? 0l : taxableAmtTot.doubleValue());
			col++;

			cell = hrow.createCell(col);
			cell.setCellStyle(rightAlignWthDecimalBold);
			cell.setCellValue(cessAmtTot == null ? 0l : cessAmtTot.doubleValue());

		} catch (Exception e) {
			e.printStackTrace();
		}
		return wsheet;

	}

	
	public Sheet getB2BAExcelData(ReportBean bean, Workbook wwbook, List<GSTR_1_B2BA_INV> dataList, String compNm) {
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
		Sheet wsheet = null;
		try {
			System.out.println(
					"generate B2BA_INVReport Excel ----------------------------------------------------------------------");

			@SuppressWarnings("unused")
			File ff = null;
			wsheet = wwbook.createSheet("B2BA_INV");
			wsheet.createFreezePane(0, 6);

			ApachePoiExcelFormat xlsxExcelFormat = new ApachePoiExcelFormat();

			CellStyle sheetHeading = xlsxExcelFormat.SheetHeading(wwbook);
			CellStyle columnHeading = xlsxExcelFormat.columnHeading(wwbook);
			// CellStyle columnSubHeading = xlsxExcelFormat.columnSubHeading(wwbook);
			CellStyle rightAlignWthDecimal = xlsxExcelFormat.dataRightAlignWthDecimal(wwbook);
			CellStyle leftAlign = xlsxExcelFormat.dataLeftAlign(wwbook);
			CellStyle rightAlignWthDecimalBold = xlsxExcelFormat.dataBoldRightAlignWthDecimal(wwbook);
			CellStyle leftAlignBold = xlsxExcelFormat.dataBoldLeftAlign(wwbook);
			// CellStyle rightAlignWthDecimalWithBg =
			// xlsxExcelFormat.columnSubHeadingRightAlgn(wwbook);

			int col = 0, row = 0;
			XSSFRow hrow = (XSSFRow) wsheet.createRow(row);
			XSSFCell cell = hrow.createCell(col);
			cell.setCellStyle(sheetHeading);
			cell.setCellValue(compNm);
			wsheet.addMergedRegion(new CellRangeAddress(row, row, col, col + 13));

			row++;

			hrow = (XSSFRow) wsheet.createRow(row);
			cell = hrow.createCell(col);
			cell.setCellStyle(sheetHeading);
			// System.out.println("obj.getLocation().length() " + obj.getLoc_id());
//			if (obj.getLoc_id().compareTo(0l) == 0)
//				cell.setCellValue("All Locations");

//			else
				cell.setCellValue(bean.getLoc_name());
			wsheet.addMergedRegion(new CellRangeAddress(row, row, col, col + 13));
			row++;

			hrow = (XSSFRow) wsheet.createRow(row);
			cell = hrow.createCell(col);
			cell.setCellStyle(sheetHeading);
			cell.setCellValue("B2BA_INV");
			wsheet.addMergedRegion(new CellRangeAddress(row, row, col, col + 13));
			row++;

			hrow = (XSSFRow) wsheet.createRow(row);
			cell = hrow.createCell(col);
			cell.setCellStyle(sheetHeading);
			cell.setCellValue("From "+MedicoResources.convertUtilDateToString_DD_MM_YYYY(bean.getStartDate()) +
					" to "+MedicoResources.convertUtilDateToString_DD_MM_YYYY(bean.getEndDate()));
			wsheet.addMergedRegion(new CellRangeAddress(row, row, col, col + 13));
			row++;

			hrow = (XSSFRow) wsheet.createRow(row);
			cell = hrow.createCell(col);
			cell.setCellStyle(sheetHeading);
			cell.setCellValue("Report Dated : " + Utility.convertDatetoString(new Date()));
			wsheet.addMergedRegion(new CellRangeAddress(row, row, col, col + 13));
			row++;

			hrow = (XSSFRow) wsheet.createRow(row);
			col = 0;

			cell = hrow.createCell(col);
			cell.setCellStyle(columnHeading);
			cell.setCellValue("GSTIN/UIN of Recipient");
			col++;

			cell = hrow.createCell(col);
			cell.setCellStyle(columnHeading);
			cell.setCellValue("Receiver Name");
			col++;

			cell = hrow.createCell(col);
			cell.setCellStyle(columnHeading);
			cell.setCellValue("Original Invoice Number");
			col++;

			cell = hrow.createCell(col);
			cell.setCellStyle(columnHeading);
			cell.setCellValue("Original Invoice date");
			col++;

			cell = hrow.createCell(col);
			cell.setCellStyle(columnHeading);
			cell.setCellValue("Revised Invoice Number");
			col++;

			cell = hrow.createCell(col);
			cell.setCellStyle(columnHeading);
			cell.setCellValue("Revised Invoice date");
			col++;

			cell = hrow.createCell(col);
			cell.setCellStyle(columnHeading);
			cell.setCellValue("Invoice Value");
			col++;

			cell = hrow.createCell(col);
			cell.setCellStyle(columnHeading);
			cell.setCellValue("Place Of Supply");
			col++;

			cell = hrow.createCell(col);
			cell.setCellStyle(columnHeading);
			cell.setCellValue("Reverse Charge");
			col++;

			cell = hrow.createCell(col);
			cell.setCellStyle(columnHeading);
			cell.setCellValue("Invoice Type");
			col++;

			cell = hrow.createCell(col);
			cell.setCellStyle(columnHeading);
			cell.setCellValue("E-Commerce GSTIN");
			col++;

			cell = hrow.createCell(col);
			cell.setCellStyle(columnHeading);
			cell.setCellValue("Rate");
			col++;

			cell = hrow.createCell(col);
			cell.setCellStyle(columnHeading);
			cell.setCellValue("Taxable Value");
			col++;

			cell = hrow.createCell(col);
			cell.setCellStyle(columnHeading);
			cell.setCellValue("Cess Amount");

			row++;
			col = 0;

			BigDecimal invoiceTotal = BigDecimal.ZERO;
			BigDecimal taxableTotal = BigDecimal.ZERO;
			BigDecimal cessTotal = BigDecimal.ZERO;

			for (GSTR_1_B2BA_INV lst : dataList) {

				col = 0;

				hrow = (XSSFRow) wsheet.createRow(row);

				cell = hrow.createCell(col);
				cell.setCellStyle(leftAlign);
				cell.setCellValue(lst.getGstin_uin_of_recipient());
				col++;

				cell = hrow.createCell(col);
				cell.setCellStyle(leftAlign);
				cell.setCellValue(lst.getReceiver_name());
				col++;

				cell = hrow.createCell(col);
				cell.setCellStyle(leftAlign);
				cell.setCellValue(lst.getOriginal_invoice_number());
				col++;

				cell = hrow.createCell(col);
				cell.setCellStyle(leftAlign);
				String ogInvDate = lst.getOriginal_invoice_date() == null ? ""
						: (lst.getOriginal_invoice_date().equals("") ? "" : sdf.format(lst.getOriginal_invoice_date()));
				cell.setCellValue(ogInvDate);
				col++;

				cell = hrow.createCell(col);
				cell.setCellStyle(leftAlign);
				cell.setCellValue(lst.getRevised_invoice_number());
				col++;

				cell = hrow.createCell(col);
				cell.setCellStyle(leftAlign);
				String rvInvDate = lst.getRevised_invoice_date() == null ? ""
						: (lst.getRevised_invoice_date().equals("") ? "" : sdf.format(lst.getRevised_invoice_date()));
				cell.setCellValue(rvInvDate);
				col++;

				cell = hrow.createCell(col);
				cell.setCellStyle(rightAlignWthDecimal);
				cell.setCellValue(lst.getInvoice_value().doubleValue());
				col++;

				cell = hrow.createCell(col);
				cell.setCellStyle(leftAlign);
				cell.setCellValue(lst.getPlace_of_supply());
				col++;

				cell = hrow.createCell(col);
				cell.setCellStyle(leftAlign);
				cell.setCellValue(lst.getReverse_charge());
				col++;

				cell = hrow.createCell(col);
				cell.setCellStyle(leftAlign);
				cell.setCellValue(lst.getInvoice_type());
				col++;

				cell = hrow.createCell(col);
				cell.setCellStyle(leftAlign);
				cell.setCellValue(lst.getE_commerce_gstin());
				col++;

				cell = hrow.createCell(col);
				cell.setCellStyle(rightAlignWthDecimal);
				cell.setCellValue(lst.getRate().doubleValue());
				col++;

				cell = hrow.createCell(col);
				cell.setCellStyle(rightAlignWthDecimal);
				cell.setCellValue(lst.getTaxable_value().doubleValue());
				col++;

				cell = hrow.createCell(col);
				cell.setCellStyle(rightAlignWthDecimal);
				cell.setCellValue(lst.getCess_amount().doubleValue());
				row++;
				invoiceTotal = invoiceTotal.add(lst.getInvoice_value());
				taxableTotal = taxableTotal.add(lst.getTaxable_value());
				cessTotal = cessTotal.add(lst.getCess_amount());
			}

			col = 0;

			hrow = (XSSFRow) wsheet.createRow(row);

			cell = hrow.createCell(col);
			cell.setCellStyle(leftAlignBold);
			cell.setCellValue("Total");
			col++;

			for (int i = 0; i < 5; i++) {
				cell = hrow.createCell(col);
				cell.setCellStyle(leftAlignBold);
				cell.setCellValue("");
				col++;
			}

			cell = hrow.createCell(col);
			cell.setCellStyle(rightAlignWthDecimalBold);
			cell.setCellValue(invoiceTotal.doubleValue());
			col++;

			for (int i = 0; i < 5; i++) {
				cell = hrow.createCell(col);
				cell.setCellStyle(leftAlignBold);
				cell.setCellValue("");
				col++;
			}

			cell = hrow.createCell(col);
			cell.setCellStyle(rightAlignWthDecimalBold);
			cell.setCellValue(taxableTotal.doubleValue());
			col++;

			cell = hrow.createCell(col);
			cell.setCellStyle(rightAlignWthDecimalBold);
			cell.setCellValue(cessTotal.doubleValue());

			System.out.println("Excel Created");

		} catch (Exception e) {
			e.printStackTrace();
		}
		return wsheet;

	}
	
	
	private Sheet getB2B_INVExcelData(ReportBean bean, Workbook wwbook, List<GSTR_1_B2B_INV> dataList, String compNm) {
		Sheet wsheet = null;
		try {
			System.out.println(
					"generate ViewGSTR_1_B2BIN_INV Excel ----------------------------------------------------------------------");
			// Thread.sleep(10000);

			wsheet = wwbook.createSheet("B2BIN_INV");
			wsheet.createFreezePane(0, 6);

			ApachePoiExcelFormat xlsxExcelFormat = new ApachePoiExcelFormat();

			CellStyle sheetHeading = xlsxExcelFormat.SheetHeading(wwbook);
			CellStyle columnHeading = xlsxExcelFormat.columnHeading(wwbook);
			// CellStyle columnSubHeading = xlsxExcelFormat.columnSubHeading(wwbook);
			CellStyle rightAlignWthDecimal = xlsxExcelFormat.dataRightAlignWthDecimal(wwbook);
			CellStyle leftAlign = xlsxExcelFormat.dataLeftAlign(wwbook);
			// CellStyle rightAlignWthDecimalWithBg =
			// xlsxExcelFormat.columnSubHeadingRightAlgn(wwbook);

			CellStyle rightAlignWthDecimalBold = xlsxExcelFormat.dataBoldRightAlignWthDecimal(wwbook);
			CellStyle leftAlignBold = xlsxExcelFormat.dataBoldLeftAlign(wwbook);

			SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
			DateFormat inputDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.S");
			DateFormat inputDateFormat2 = new SimpleDateFormat("yyyy-MM-dd");

			int col = 0, row = 0;
			XSSFRow hrow = (XSSFRow) wsheet.createRow(row);
			XSSFCell cell = hrow.createCell(col);
			cell.setCellStyle(sheetHeading);
			cell.setCellValue(compNm);
			wsheet.addMergedRegion(new CellRangeAddress(row, row, col, col + 11));

			row++;

			hrow = (XSSFRow) wsheet.createRow(row);
			cell = hrow.createCell(col);
			cell.setCellStyle(sheetHeading);
			// System.out.println("obj.getLocation().length() " + obj.getLoc_id());
//			if (obj.getLoc_id().compareTo(0l) == 0)
//				cell.setCellValue("All Locations");

//			else
				cell.setCellValue(bean.getLoc_name());
			wsheet.addMergedRegion(new CellRangeAddress(row, row, col, col + 11));
			row++;

			hrow = (XSSFRow) wsheet.createRow(row);
			cell = hrow.createCell(col);
			cell.setCellStyle(sheetHeading);
			cell.setCellValue("B2BIN_INV");
			wsheet.addMergedRegion(new CellRangeAddress(row, row, col, col + 11));
			row++;

			hrow = (XSSFRow) wsheet.createRow(row);
			cell = hrow.createCell(col);
			cell.setCellStyle(sheetHeading);
			cell.setCellValue("From "+MedicoResources.convertUtilDateToString_DD_MM_YYYY(bean.getStartDate()) +
					" to "+MedicoResources.convertUtilDateToString_DD_MM_YYYY(bean.getEndDate()));
			wsheet.addMergedRegion(new CellRangeAddress(row, row, col, col + 11));
			row++;

			hrow = (XSSFRow) wsheet.createRow(row);
			cell = hrow.createCell(col);
			cell.setCellStyle(sheetHeading);
			cell.setCellValue("Report Dated : " + Utility.convertDatetoString(new Date()));
			wsheet.addMergedRegion(new CellRangeAddress(row, row, col, col + 11));
			row++;

			hrow = (XSSFRow) wsheet.createRow(row);
			col = 0;

			cell = hrow.createCell(col);
			cell.setCellStyle(columnHeading);
			cell.setCellValue("GSTIN/UIN of Recipient");
			col++;

			cell = hrow.createCell(col);
			cell.setCellStyle(columnHeading);
			cell.setCellValue("Receiver Name");
			col++;

			cell = hrow.createCell(col);
			cell.setCellStyle(columnHeading);
			cell.setCellValue("Invoice Number");
			col++;

			cell = hrow.createCell(col);
			cell.setCellStyle(columnHeading);
			cell.setCellValue("Invoice Date");
			col++;

			cell = hrow.createCell(col);
			cell.setCellStyle(columnHeading);
			cell.setCellValue("Invoice Value");
			col++;

			cell = hrow.createCell(col);
			cell.setCellStyle(columnHeading);
			cell.setCellValue("Place of Supply");

			col++;

			cell = hrow.createCell(col);
			cell.setCellStyle(columnHeading);
			cell.setCellValue("Reverse Charge");

			col++;

			cell = hrow.createCell(col);
			cell.setCellStyle(columnHeading);
			cell.setCellValue("Invoice Type");

			col++;

			cell = hrow.createCell(col);
			cell.setCellStyle(columnHeading);
			cell.setCellValue("E-commerce GSTN");

			col++;

			cell = hrow.createCell(col);
			cell.setCellStyle(columnHeading);
			cell.setCellValue("Rate");

			col++;

			cell = hrow.createCell(col);
			cell.setCellStyle(columnHeading);
			cell.setCellValue("Taxable Value");

			col++;

			cell = hrow.createCell(col);
			cell.setCellStyle(columnHeading);
			cell.setCellValue("Cess Amount");
			col++;

			BigDecimal taxableAmtTot = BigDecimal.ZERO;
			BigDecimal cessAmtTot = BigDecimal.ZERO;

			for (GSTR_1_B2B_INV list : dataList) {

				row++;
				col = 0;
				hrow = (XSSFRow) wsheet.createRow(row);

				cell = hrow.createCell(col);
				cell.setCellStyle(leftAlign);
				cell.setCellValue(list.getGstin_uin_of_recipient());
				col++;

				cell = hrow.createCell(col);
				cell.setCellStyle(leftAlign);
				cell.setCellValue(list.getReceiver_name());
				col++;

				cell = hrow.createCell(col);
				cell.setCellStyle(leftAlign);
				cell.setCellValue(list.getInvoice_number());
				col++;

				String ogInvDate = list.getInvoice_date() == null ? ""
						: (list.getInvoice_date().equals("") ? ""
								: sdf.format(inputDateFormat2.parse(list.getInvoice_date())));
				cell = hrow.createCell(col);
				cell.setCellStyle(rightAlignWthDecimal);
				cell.setCellValue(ogInvDate);
				col++;

				cell = hrow.createCell(col);
				cell.setCellStyle(rightAlignWthDecimal);
				cell.setCellValue((list.getINVOICE_VALUE().doubleValue()));
				col++;

				cell = hrow.createCell(col);
				cell.setCellStyle(leftAlign);
				cell.setCellValue(list.getPlace_of_supply());
				col++;

				cell = hrow.createCell(col);
				cell.setCellStyle(leftAlign);
				cell.setCellValue(list.getReverse_charge());
				col++;

				cell = hrow.createCell(col);
				cell.setCellStyle(leftAlign);
				cell.setCellValue(list.getInvoice_type());
				col++;

				cell = hrow.createCell(col);
				cell.setCellStyle(leftAlign);
				cell.setCellValue(list.getE_commmerce_gstin());
				col++;

				cell = hrow.createCell(col);
				cell.setCellStyle(rightAlignWthDecimal);
				cell.setCellValue(list.getRate().doubleValue());
				col++;

				cell = hrow.createCell(col);
				cell.setCellStyle(rightAlignWthDecimal);

				cell.setCellValue((Double.parseDouble(list.getTaxable_value().toString()) < 0
						? -Double.parseDouble(list.getTaxable_value().toString())
						: Double.parseDouble(list.getTaxable_value().toString())));

				col++;

				cell = hrow.createCell(col);
				cell.setCellStyle(rightAlignWthDecimal);

				cell.setCellValue((Double.parseDouble(list.getCess_amount().toString()) < 0
						? -Double.parseDouble(list.getCess_amount().toString())
						: Double.parseDouble(list.getCess_amount().toString())));
				col++;

				taxableAmtTot = taxableAmtTot.add(list.getTaxable_value());
				cessAmtTot = cessAmtTot.add(list.getCess_amount());

			} // dataList ends here

			col = 0;
			row++;
			hrow = (XSSFRow) wsheet.createRow(row);

			cell = hrow.createCell(col);
			cell.setCellStyle(leftAlignBold);
			cell.setCellValue("Total ");
			col++;

			for (int i = 0; i < 9; i++) {
				cell = hrow.createCell(col);
				cell.setCellStyle(leftAlignBold);
				cell.setCellValue("");
				col++;

			}

			cell = hrow.createCell(col);
			cell.setCellStyle(rightAlignWthDecimalBold);

			cell.setCellValue(
					(Double.parseDouble(taxableAmtTot.toString()) < 0 ? -Double.parseDouble(taxableAmtTot.toString())
							: Double.parseDouble(taxableAmtTot.toString())));

			col++;

			cell = hrow.createCell(col);
			cell.setCellStyle(rightAlignWthDecimalBold);

			cell.setCellValue(
					(Double.parseDouble(cessAmtTot.toString()) < 0 ? -Double.parseDouble(cessAmtTot.toString())
							: Double.parseDouble(cessAmtTot.toString())));
			col++;

			row++;

			System.out.println("Excel Created");

		} catch (Exception e) {
			e.printStackTrace();
		}

		return wsheet;

	}
	
	
	private Sheet getB2CSA_INVExcelData(ReportBean bean, Workbook wwbook, List<ViewGSTR_1_B2CSA_INV> dataList, String compNm) {
		String actualpath = null;
		Sheet wsheet = null;
		try {
			System.out.println(
					"generate B2CSA_INVExcel Excel ----------------------------------------------------------------------");
			// Thread.sleep(10000);
			wsheet = wwbook.createSheet("B2CSA_INV");
			wsheet.createFreezePane(0, 6);

			ApachePoiExcelFormat xlsxExcelFormat = new ApachePoiExcelFormat();

			CellStyle sheetHeading = xlsxExcelFormat.SheetHeading(wwbook);
			CellStyle columnHeading = xlsxExcelFormat.columnHeading(wwbook);
			// CellStyle columnSubHeading = xlsxExcelFormat.columnSubHeading(wwbook);
			CellStyle rightAlignWthDecimal = xlsxExcelFormat.dataRightAlignWthDecimal(wwbook);
			CellStyle leftAlign = xlsxExcelFormat.dataLeftAlign(wwbook);
			CellStyle rightAlignWthDecimalBold = xlsxExcelFormat.dataBoldRightAlignWthDecimal(wwbook);
			CellStyle leftAlignBold = xlsxExcelFormat.dataBoldLeftAlign(wwbook);
			// CellStyle rightAlignWthDecimalWithBg =
			// xlsxExcelFormat.columnSubHeadingRightAlgn(wwbook);

			int col = 0, row = 0;
			XSSFRow hrow = (XSSFRow) wsheet.createRow(row);
			XSSFCell cell = hrow.createCell(col);
			cell.setCellStyle(sheetHeading);
			cell.setCellValue(compNm);
			wsheet.addMergedRegion(new CellRangeAddress(row, row, col, col + 8));

			row++;

			hrow = (XSSFRow) wsheet.createRow(row);
			cell = hrow.createCell(col);
			cell.setCellStyle(sheetHeading);
			// System.out.println("obj.getLocation().length() " + obj.getLoc_id());
//			if (obj.getLoc_id().compareTo(0l) == 0)
//				cell.setCellValue("All Locations");

//			else
				cell.setCellValue(bean.getLoc_name());
			wsheet.addMergedRegion(new CellRangeAddress(row, row, col, col + 8));
			row++;

			hrow = (XSSFRow) wsheet.createRow(row);
			cell = hrow.createCell(col);
			cell.setCellStyle(sheetHeading);
			cell.setCellValue("B2CSA_INV Report");
			wsheet.addMergedRegion(new CellRangeAddress(row, row, col, col + 8));
			row++;

			hrow = (XSSFRow) wsheet.createRow(row);
			cell = hrow.createCell(col);
			cell.setCellStyle(sheetHeading);
			cell.setCellValue("From "+MedicoResources.convertUtilDateToString_DD_MM_YYYY(bean.getStartDate()) +
					" to "+MedicoResources.convertUtilDateToString_DD_MM_YYYY(bean.getEndDate()));
			wsheet.addMergedRegion(new CellRangeAddress(row, row, col, col + 8));
			row++;

			hrow = (XSSFRow) wsheet.createRow(row);
			cell = hrow.createCell(col);
			cell.setCellStyle(sheetHeading);
			cell.setCellValue("Report Dated : " + Utility.convertDatetoString(new Date()));
			wsheet.addMergedRegion(new CellRangeAddress(row, row, col, col + 8));
			row++;

			hrow = (XSSFRow) wsheet.createRow(row);
			col = 0;

			cell = hrow.createCell(col);
			cell.setCellStyle(columnHeading);
			cell.setCellValue("Financial Year");
			col++;

			cell = hrow.createCell(col);
			cell.setCellStyle(columnHeading);
			cell.setCellValue("Original Month");
			col++;

			cell = hrow.createCell(col);
			cell.setCellStyle(columnHeading);
			cell.setCellValue("Original Place Of Supply");
			col++;

			cell = hrow.createCell(col);
			cell.setCellStyle(columnHeading);
			cell.setCellValue("Revised Place Of Supply");
			col++;

			cell = hrow.createCell(col);
			cell.setCellStyle(columnHeading);
			cell.setCellValue("Type");
			col++;

			cell = hrow.createCell(col);
			cell.setCellStyle(columnHeading);
			cell.setCellValue("Rate");
			col++;

			cell = hrow.createCell(col);
			cell.setCellStyle(columnHeading);
			cell.setCellValue("Taxable Rate");
			col++;

			cell = hrow.createCell(col);
			cell.setCellStyle(columnHeading);
			cell.setCellValue("Cess Amount");
			col++;

			cell = hrow.createCell(col);
			cell.setCellStyle(columnHeading);
			cell.setCellValue("E-Commerce GSTIN");
			col++;

			row++;
			col = 0;
			BigDecimal taxableAmtTot = BigDecimal.ZERO;
			BigDecimal cessAmtTot = BigDecimal.ZERO;
			for (ViewGSTR_1_B2CSA_INV lst : dataList) {

				col = 0;

				hrow = (XSSFRow) wsheet.createRow(row);

				cell = hrow.createCell(col);
				cell.setCellStyle(leftAlign);
				cell.setCellValue(lst.getFin_year());
				col++;

				cell = hrow.createCell(col);
				cell.setCellStyle(leftAlign);
				cell.setCellValue(lst.getOrg_month());
				col++;

				cell = hrow.createCell(col);
				cell.setCellStyle(leftAlign);
				cell.setCellValue(lst.getOrg_place_of_supply());
				col++;

				cell = hrow.createCell(col);
				cell.setCellStyle(leftAlign);
				cell.setCellValue(lst.getRev_place_of_supply());
				col++;

				cell = hrow.createCell(col);
				cell.setCellStyle(leftAlign);
				cell.setCellValue(lst.getType());
				col++;

				cell = hrow.createCell(col);
				cell.setCellStyle(rightAlignWthDecimal);

				cell.setCellValue((Double.parseDouble(lst.getRate().toString()) < 0
						? -Double.parseDouble(lst.getRate().toString())
						: Double.parseDouble(lst.getRate().toString())));

				col++;

				cell = hrow.createCell(col);
				cell.setCellStyle(rightAlignWthDecimal);

				cell.setCellValue((Double.parseDouble(lst.getTaxable_value().toString()) < 0
						? -Double.parseDouble(lst.getTaxable_value().toString())
						: Double.parseDouble(lst.getTaxable_value().toString())));
				col++;

				cell = hrow.createCell(col);
				cell.setCellStyle(rightAlignWthDecimal);

				cell.setCellValue((Double.parseDouble(lst.getCess_amount().toString()) < 0
						? -Double.parseDouble(lst.getCess_amount().toString())
						: Double.parseDouble(lst.getCess_amount().toString())));
				col++;

				cell = hrow.createCell(col);
				cell.setCellStyle(leftAlign);
				cell.setCellValue(lst.getE_commerce_gstin());

				row++;
				taxableAmtTot = taxableAmtTot.add(lst.getTaxable_value());
				cessAmtTot = cessAmtTot.add(lst.getCess_amount());

			} // dataList ends here
			col = 0;

			hrow = (XSSFRow) wsheet.createRow(row);

			cell = hrow.createCell(col);
			cell.setCellStyle(leftAlignBold);
			cell.setCellValue("Total ");
			col++;

			cell = hrow.createCell(col);
			cell.setCellStyle(leftAlignBold);
			cell.setCellValue("");
			col++;

			cell = hrow.createCell(col);
			cell.setCellStyle(leftAlignBold);
			cell.setCellValue("");
			col++;

			cell = hrow.createCell(col);
			cell.setCellStyle(leftAlignBold);
			cell.setCellValue("");
			col++;

			cell = hrow.createCell(col);
			cell.setCellStyle(leftAlignBold);
			cell.setCellValue("");
			col++;

			cell = hrow.createCell(col);
			cell.setCellStyle(leftAlignBold);
			cell.setCellValue("");
			col++;

			cell = hrow.createCell(col);
			cell.setCellStyle(rightAlignWthDecimalBold);

			cell.setCellValue(
					(Double.parseDouble(taxableAmtTot.toString()) < 0 ? -Double.parseDouble(taxableAmtTot.toString())
							: Double.parseDouble(taxableAmtTot.toString())));

			col++;

			cell = hrow.createCell(col);
			cell.setCellStyle(rightAlignWthDecimalBold);

			cell.setCellValue(
					(Double.parseDouble(cessAmtTot.toString()) < 0 ? -Double.parseDouble(cessAmtTot.toString())
							: Double.parseDouble(cessAmtTot.toString())));
			col++;
			cell = hrow.createCell(col);
			cell.setCellStyle(leftAlignBold);
			cell.setCellValue("");
			col++;

			row++;
			
			System.out.println("Excel Created");

		} catch (Exception e) {
			e.printStackTrace();
		}

		return wsheet;
	}

}
