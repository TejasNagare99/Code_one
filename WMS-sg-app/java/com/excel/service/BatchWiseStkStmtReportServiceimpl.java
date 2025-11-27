package com.excel.service;

import java.io.File;
import java.io.FileOutputStream;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import org.apache.poi.ss.usermodel.BorderStyle;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
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
import com.excel.model.BatchwiseStockStmtReport;
import com.excel.model.BatchwiseStockStmtReport_SG;
import com.excel.model.Scheme_Desription_Bean;
import com.excel.repository.UserMasterRepository;
import com.excel.utility.MedicoConstants;
import com.excel.utility.MedicoResources;
import com.excel.utility.ReportFormats;

@Service
public class BatchWiseStkStmtReportServiceimpl  implements BatchWiseStkStmtReportService ,MedicoConstants{
	@Autowired private UserMasterService usermasterservice;

	@Autowired private UserMasterRepository usermasterrepository;
	@Override
	public String generateBatchwiseStockStmtReportReport(List<BatchwiseStockStmtReport> list,ReportBean bean,String companyname,String empId,  List<Scheme_Desription_Bean> listOfSchemDescription) throws Exception {
		String filename = "BatchWiseStkStmtReport" + new Date().getTime() + ".xlsx";
		String filepath = REPORT_FILE_PATH + filename;
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
			XSSFSheet sheet = workbook.createSheet("Batch Wise Stock Statement");

			XSSFRow row = null;
			XSSFCell cell = null;
			int rowCount = 0;
			int colCount = 0;
			int limit = 28;

			String headings[] = { "Division", "Location Name", "Item Code", "Item Type", "Type of Storage",
					"Description", "Batch No", "Opening Stock", "GRN Receipts", "Adjustment Receipts", "TRF Receipts",
					"In Total", "Dispatch Issues", "Adjustment Issues", "TRF Issues", "Out Total", "Closing Stock",
					"WithHeld Quantity", "Price", "Closing Stock Value(Rs.Ps)", "Expiry Date", "In Transit (Qty)",
					"In Transit (value)", "FCODE" };

			XSSFCellStyle headingCellStyle = ReportFormats.heading_left(workbook);
			XSSFCellStyle columnHeadingStyle = ReportFormats.columnHeading(workbook);
			XSSFCellStyle cellAlignment = ReportFormats.setCellAlignment(workbook, ALIGN_RIGHT);
			XSSFCellStyle heading1CellStyle = ReportFormats.datastyleRightwithDecimal(workbook);

			XSSFCellStyle rightAlignTotal = ReportFormats.dataRightAlignWthoutDecimal_total(workbook);
			XSSFCellStyle leftAlign_left = ReportFormats.dataLeftAlign_total(workbook);

			XSSFCellStyle leftAlign = ReportFormats.dataLeftAlign(workbook);
			XSSFCellStyle rightAlign = ReportFormats.dataRightAlign(workbook);
			XSSFCellStyle rightAlignD = ReportFormats.dataRightAlignWthDecimal(workbook);

			/* bold font for with held qty */
			XSSFFont boldFont = workbook.createFont();
			boldFont.setBold(true);
			XSSFCellStyle boldStyle = workbook.createCellStyle();
			boldStyle.setFont(boldFont);
			boldStyle.setBorderBottom(BorderStyle.THIN);
			boldStyle.setBorderLeft(BorderStyle.THIN);
			boldStyle.setBorderTop(BorderStyle.THIN);
			boldStyle.setBorderRight(BorderStyle.THIN);
			boldStyle.setAlignment(HorizontalAlignment.RIGHT);

			row = sheet.createRow(rowCount);
			cell = row.createCell(colCount);
			sheet.addMergedRegion(
					new CellRangeAddress(rowCount, rowCount, colCount, ((colCount + headings.length - 1))));
			cell.setCellValue(companyname);
			cell.setCellStyle(headingCellStyle);
			rowCount++;

			row = sheet.createRow(rowCount);
			cell = row.createCell(colCount);
			sheet.addMergedRegion(
					new CellRangeAddress(rowCount, rowCount, colCount, ((colCount + headings.length - 1))));
			cell.setCellValue("Batchwise Stock Statement");
			cell.setCellStyle(headingCellStyle);
			rowCount++;

			row = sheet.createRow(rowCount);
			cell = row.createCell(colCount);
			sheet.addMergedRegion(
					new CellRangeAddress(rowCount, rowCount, colCount, ((colCount + headings.length - 1))));
			cell.setCellValue("Period: " + MedicoResources.convertUtilDateToString_DD_MM_YYYY_(bean.getStartDate())
					+ " to " + MedicoResources.convertUtilDateToString_DD_MM_YYYY_(bean.getEndDate()));
			cell.setCellStyle(headingCellStyle);
			rowCount += 1;

			row = sheet.createRow(rowCount);
			cell = row.createCell(colCount);
			sheet.addMergedRegion(new CellRangeAddress(rowCount, rowCount, colCount, colCount + headings.length - 1));
			cell.setCellValue(MedicoResources.convertUtilDateToString_DD_MM_YYYY(new Date()) + " "
					+ timeformat.format(new Date()));
			cell.setCellStyle(headingCellStyle);

			rowCount += 1;
			row = sheet.createRow(rowCount);

			for (String heading : headings) {
				cell = ReportFormats.cell(row, colCount, heading, columnHeadingStyle);
				colCount++;
			}

			rowCount += 1;
			colCount = 0;

			sheet.groupRow(0, 2);
			sheet.createFreezePane(0, 5);

			BigDecimal clsoVal = BigDecimal.ZERO;
			BigDecimal tranVal = BigDecimal.ZERO;

			String oldLoc = "";
			String newLoc = "";

			for (BatchwiseStockStmtReport data : list) {

				newLoc = data.getLoc_nm();

				if (!oldLoc.equalsIgnoreCase(newLoc) && !oldLoc.trim().equals("")) {
					row = sheet.createRow(rowCount);
					cell = ReportFormats.cell(row, 0, "Location Total", leftAlign_left);
					for (int i = 1; i <= 18; i++) {
						cell = row.createCell(i);
						cell.setCellValue("");
						cell.setCellStyle(rightAlignTotal);
					}
					cell = ReportFormats.cellNum(row, 19, clsoVal.setScale(2).doubleValue(), rightAlignTotal);
					for (int i = 20; i <= 22; i++) {
						cell = row.createCell(i);
						cell.setCellValue("");
						cell.setCellStyle(rightAlignTotal);
					}
					cell = ReportFormats.cellNum(row, 22, tranVal.setScale(2).doubleValue(), rightAlignTotal);
					cell = row.createCell(23);
					cell.setCellValue("");
					cell.setCellStyle(rightAlignTotal);
					clsoVal = BigDecimal.ZERO;
					tranVal = BigDecimal.ZERO;
					rowCount++;
				}

				oldLoc = newLoc;

				colCount = 0;
				row = sheet.createRow(rowCount);

				cell = ReportFormats.cell(row, colCount, data.getDiv_disp_nm(), leftAlign);
				colCount++;

				cell = ReportFormats.cell(row, colCount, data.getLoc_nm(), leftAlign);
				colCount++;

				cell = ReportFormats.cell(row, colCount, data.getSmp_prod_cd(), leftAlign);
				colCount++;

				cell = ReportFormats.cell(row, colCount, data.getSmp_prod_type(), leftAlign);
				colCount++;
				// chamges
				cell = ReportFormats.cell(row, colCount, data.getStorage_type(), leftAlign);
				colCount++;

				cell = ReportFormats.cell(row, colCount,
						data.getSmp_prod_name() == null ? "" : data.getSmp_prod_name().trim(), leftAlign);
				colCount++;

				cell = ReportFormats.cell(row, colCount, data.getBatch_no(), leftAlign);
				colCount++;

				cell = ReportFormats.cellNum(row, colCount,
						data.getOpen_stock() == null ? 0l : data.getOpen_stock().doubleValue(), rightAlign);
				colCount++;

				cell = ReportFormats.cellNum(row, colCount,
						data.getGrn_receipts() == null ? 0l : data.getGrn_receipts().doubleValue(), rightAlign);
				colCount++;

				cell = ReportFormats.cellNum(row, colCount,
						data.getAdj_receipts() == null ? 0l : data.getAdj_receipts().doubleValue(), rightAlign);
				colCount++;

				cell = ReportFormats.cellNum(row, colCount,
						data.getTrf_receipts() == null ? 0l : data.getTrf_receipts().doubleValue(), rightAlign);
				colCount++;

				cell = ReportFormats.cellNum(row, colCount,
						data.getIn_total() == null ? 0l : data.getIn_total().doubleValue(), rightAlign);
				colCount++;

				cell = ReportFormats.cellNum(row, colCount,
						data.getDsp_issues() == null ? 0l : data.getDsp_issues().doubleValue(), rightAlign);
				colCount++;

				cell = ReportFormats.cellNum(row, colCount,
						data.getAdj_issues() == null ? 0l : data.getAdj_issues().doubleValue(), rightAlign);
				colCount++;

				cell = ReportFormats.cellNum(row, colCount,
						data.getTrf_issues() == null ? 0l : data.getTrf_issues().doubleValue(), rightAlign);
				colCount++;

				cell = ReportFormats.cellNum(row, colCount,
						data.getOut_total() == null ? 0l : data.getOut_total().doubleValue(), rightAlign);
				colCount++;

				cell = ReportFormats.cellNum(row, colCount,
						data.getClosing_stock() == null ? 0l : data.getClosing_stock().doubleValue(), rightAlign);
				colCount++;

				XSSFCell cellforWithHeldQty = row.createCell(colCount);

				if (data.getBatch_with_held_qty() == null) {
					cellforWithHeldQty.setCellValue(01);
				} else {
					if (data.getBatch_with_held_qty().doubleValue() == 0.0) {
						cellforWithHeldQty.setCellValue(data.getBatch_with_held_qty().doubleValue());
					} else {
						cellforWithHeldQty.setCellValue(data.getBatch_with_held_qty().doubleValue());
						cellforWithHeldQty.setCellStyle(boldStyle);
					}

				}
				cellforWithHeldQty.setCellStyle(boldStyle);
				colCount++;

				cell = ReportFormats.cellNum(row, colCount,
						data.getCog_rate() == null ? 0l : data.getCog_rate().doubleValue(), rightAlign);
				colCount++;

				cell = ReportFormats.cellNum(row, colCount,
						data.getClosing_val() == null ? 0l : data.getClosing_val().doubleValue(), rightAlignD);
				colCount++;

				cell = ReportFormats.cell(row, colCount, data.getBatch_expiry_dt(), leftAlign);
				colCount++;

				cell = ReportFormats.cellNum(row, colCount,
						data.getIntransit() == null ? 0l : data.getIntransit().doubleValue(), rightAlign);
				colCount++;

				cell = ReportFormats.cellNum(row, colCount,
						data.getIntransit_val() == null ? 0l : data.getIntransit_val().doubleValue(), rightAlignD);
				colCount++;

				cell = ReportFormats.cell(row, colCount, data.getFcode(), leftAlign);
				colCount++;

				rowCount++;
				colCount = 0;

				clsoVal = clsoVal.add(data.getClosing_val() == null ? BigDecimal.ZERO : data.getClosing_val());
				tranVal = tranVal.add(data.getIntransit_val() == null ? BigDecimal.ZERO : data.getIntransit_val());

			}

			row = sheet.createRow(rowCount);

			row = sheet.createRow(rowCount);
			cell = ReportFormats.cell(row, 0, "Location Total", leftAlign_left);
			for (int i = 1; i <= 18; i++) {
				cell = row.createCell(i);
				cell.setCellValue("");
				cell.setCellStyle(rightAlignTotal);
			}
			cell = ReportFormats.cellNum(row, 19, clsoVal.setScale(2).doubleValue(), rightAlignTotal);
			for (int i = 20; i <= 22; i++) {
				cell = row.createCell(i);
				cell.setCellValue("");
				cell.setCellStyle(rightAlignTotal);
			}
			cell = ReportFormats.cellNum(row, 22, tranVal.setScale(2).doubleValue(), rightAlignTotal);
			cell = row.createCell(23);
			cell.setCellValue("");
			cell.setCellStyle(rightAlignTotal);

			// scheme description
			XSSFSheet sheet2 = workbook.createSheet("SCHEME DESCRIPTION");

			XSSFRow description_row = null;
			XSSFCell description_cell = null;
			int description_rowCount = 0;
			int description_colCount = 0;

			List<String> description_headings = new ArrayList<>();

			// creating heading
			description_headings.add("Sl No");
			description_headings.add("Location Name");
			description_headings.add("Sub Company Code");
			description_headings.add("Scheme Code");
			description_headings.add("Scheme Name");
			description_headings.add("Valid From Date");
			description_headings.add("Valid To Date");
			description_headings.add("Article Product Code");
			description_headings.add("Article Product Name");
			description_headings.add("Sale Product Code");
			description_headings.add("Sale Product Name");
			description_headings.add("Scheme Apply To");

			description_headings.add("Billed Value");
			description_headings.add("Article Qty Per Total Value");
			description_headings.add("Per Sale Qty Total");
			description_headings.add("Article Qty");

			description_row = sheet2.createRow(description_rowCount);
			description_cell = description_row.createCell(description_colCount);

			sheet2.addMergedRegion(
					new CellRangeAddress(description_rowCount, description_rowCount, description_colCount, 15));
			description_cell.setCellValue("SCHEME DESCRIPTION");
			description_cell.setCellStyle(headingCellStyle);
			description_rowCount += 1;

			description_colCount = 0;

			description_row = sheet2.createRow(description_rowCount);
			for (String heading : description_headings) {

				description_cell = description_row.createCell(description_colCount);

				description_cell = ReportFormats.cell(description_row, description_colCount, heading,
						columnHeadingStyle);
				description_colCount++;
			}

			description_rowCount += 1;
			description_colCount = 0;

			String applay_To_Value_Pervious = "";
			Long rowcount = 1L;
			for (Scheme_Desription_Bean description_bean : listOfSchemDescription) {

				description_row = sheet2.createRow(description_rowCount);
				description_colCount = 0;

				description_cell = ReportFormats.cell(description_row, description_colCount, rowcount.toString(), null);
				rowcount++;
				description_colCount++;

				description_cell = ReportFormats.cell(description_row, description_colCount,
						description_bean.getLoc_nm().toString(), null);
				description_colCount++;

				description_cell = ReportFormats.cell(description_row, description_colCount,
						description_bean.getSub_comp_cd().toString(), null);
				description_colCount++;

				description_cell = ReportFormats.cell(description_row, description_colCount,
						description_bean.getTrd_scheme_code().toString(), null);
				description_colCount++;

				// scheme name
//				description_cell = ReportFormats.cell(description_row, description_colCount,
//						description_bean.getTrd_scheme_name().toString(), null);

				description_cell = ReportFormats.cell(description_row, description_colCount,
						MedicoConstants.toCamelCase(description_bean.getTrd_scheme_name().toString()), null);

				description_colCount++;

				description_cell = ReportFormats.cell(description_row, description_colCount,
						description_bean.getValid_from_dt().toString(), null);
				description_colCount++;

				description_cell = ReportFormats.cell(description_row, description_colCount,
						description_bean.getValid_to_dt().toString(), null);
				description_colCount++;

				description_cell = ReportFormats.cell(description_row, description_colCount,
						description_bean.getArticle_prod_cd().toString(), null);
				description_colCount++;

				description_cell = ReportFormats.cell(description_row, description_colCount,
						description_bean.getArticle_name().toString(), null);
				description_colCount++;

				description_cell = ReportFormats.cell(description_row, description_colCount,
						description_bean.getSale_prod_code_sg().toString(), null);
				description_colCount++;

				description_cell = ReportFormats.cell(description_row, description_colCount,
						description_bean.getSale_prod_name().toString(), null);
				description_colCount++;

				description_cell = ReportFormats.cell(description_row, description_colCount,
						description_bean.getApply_to().toString(), null);
				description_colCount++;

				// only showing one time value and article qty and
				if (!applay_To_Value_Pervious.equals(description_bean.getBilled_value().toString())
						&& "V".equals(description_bean.getApply_to())) {

					description_cell = ReportFormats.cellNum(description_row, description_colCount,
							Double.valueOf(description_bean.getBilled_value().toString()),
							ReportFormats.setCellAlignmentWithTwoDecimal(workbook, ALIGN_RIGHT));
					description_colCount++;

					description_cell = ReportFormats.cellNum(description_row, description_colCount,
							Double.valueOf(description_bean.getArticle_qty_per_tot_val().toString()),
							ReportFormats.setCellAlignmentWithTwoDecimal(workbook, ALIGN_RIGHT));

					description_colCount++;

					description_cell = ReportFormats.cellNum(description_row, description_colCount,
							Double.valueOf(description_bean.getPer_sale_qty_tot().toString()),
							ReportFormats.setCellAlignmentWithTwoDecimal(workbook, ALIGN_RIGHT));
					description_colCount++;

					description_cell = ReportFormats.cellNum(description_row, description_colCount,
							Double.valueOf(description_bean.getArticle_qty().toString()),
							ReportFormats.setCellAlignment(workbook, ALIGN_RIGHT));
					applay_To_Value_Pervious = description_bean.getBilled_value().toString();
				}

				// if value and article qty same adding blank row
				else if (applay_To_Value_Pervious.equals(description_bean.getBilled_value().toString())
						&& "V".equals(description_bean.getApply_to())) {

					description_cell = ReportFormats.cell(description_row, description_colCount, "", null);
					description_colCount++;

					description_cell = ReportFormats.cell(description_row, description_colCount, "", null);
					description_colCount++;

					description_cell = ReportFormats.cell(description_row, description_colCount, "", null);
					description_colCount++;

					description_cell = ReportFormats.cell(description_row, description_colCount, "", null);

					applay_To_Value_Pervious = description_bean.getBilled_value().toString();
				}

				// scheme apply to works normal
				else if ("Q".equals(description_bean.getApply_to())) {

					description_cell = ReportFormats.cellNum(description_row, description_colCount,
							Double.valueOf(description_bean.getBilled_value().toString()),
							ReportFormats.setCellAlignmentWithTwoDecimal(workbook, ALIGN_RIGHT));

					description_colCount++;

					description_cell = ReportFormats.cellNum(description_row, description_colCount,
							Double.valueOf(description_bean.getArticle_qty_per_tot_val().toString()),
							ReportFormats.setCellAlignmentWithTwoDecimal(workbook, ALIGN_RIGHT));

					description_colCount++;

					description_cell = ReportFormats.cellNum(description_row, description_colCount,
							Double.valueOf(description_bean.getPer_sale_qty_tot().toString()),
							ReportFormats.setCellAlignmentWithTwoDecimal(workbook, ALIGN_RIGHT));
					description_colCount++;

					description_cell = ReportFormats.cellNum(description_row, description_colCount,
							Double.valueOf(description_bean.getArticle_qty().toString()),
							ReportFormats.setCellAlignment(workbook, ALIGN_RIGHT));
					applay_To_Value_Pervious = description_bean.getBilled_value().toString();
				}

				description_rowCount++;

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
	public String generateBatchwiseStockStmtReportReport_SG(List<BatchwiseStockStmtReport_SG> list_SG, ReportBean bean,
			String companyname, String empId) throws Exception {
		String filename = "BatchWiseStkStmtReport" + new Date().getTime() + ".xlsx";
		String filepath = REPORT_FILE_PATH + filename;
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
			XSSFSheet sheet = workbook.createSheet("Batch Wise Stock Statement");

			XSSFRow row = null;
			XSSFCell cell = null;
			int rowCount = 0;
			int colCount = 0;
			int limit = 28;
			
			String headings[] = {"Division","Item Code","Item Type","Type of Storage","Description","Batch No","Opening Stock","GRN Receipts",
					"Adjustment Receipts","TRF Receipts","In Total","Dispatch Issues","Adjustment Issues","TRF Issues","Out Total",
					"Closing Stock","WithHeld Quantity","Price","Closing Stock Value(Rs.Ps)","Expiry Date","In Transit (Qty)","In Transit (value)","FCODE"};
					
					XSSFCellStyle headingCellStyle = ReportFormats.heading(workbook);
					XSSFCellStyle columnHeadingStyle = ReportFormats.columnHeading(workbook);
					XSSFCellStyle cellAlignment = ReportFormats.setCellAlignment(workbook, ALIGN_RIGHT);
					XSSFCellStyle heading1CellStyle = ReportFormats.heading1(workbook);
					
					/* bold font for with held qty */
					XSSFFont boldFont = workbook.createFont();
					boldFont.setBold(true);
					XSSFCellStyle boldStyle = workbook.createCellStyle();
					boldStyle.setFont(boldFont);

					row = sheet.createRow(rowCount);
					cell = row.createCell(colCount);
					sheet.addMergedRegion(new CellRangeAddress(rowCount, rowCount, colCount, ((colCount + headings.length - 1))));
					cell.setCellValue(companyname);
					cell.setCellStyle(headingCellStyle);
					rowCount++;
					
					row = sheet.createRow(rowCount);
					cell = row.createCell(colCount);
					sheet.addMergedRegion(new CellRangeAddress(rowCount, rowCount, colCount, ((colCount + headings.length - 1))));
					cell.setCellValue("Batchwise Stock Statement");
					cell.setCellStyle(headingCellStyle);
					rowCount++;

					row = sheet.createRow(rowCount);
					cell = row.createCell(colCount);
					sheet.addMergedRegion(new CellRangeAddress(rowCount, rowCount, colCount, ((colCount + headings.length - 1))));
					cell.setCellValue("Period: "+MedicoResources.convertUtilDateToString_DD_MM_YYYY_(bean.getStartDate())+" to "+MedicoResources.convertUtilDateToString_DD_MM_YYYY_(bean.getEndDate()));
					cell.setCellStyle(headingCellStyle);
					rowCount += 1;
					
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
					
					rowCount += 1;
					colCount=0;
					
					
					sheet.groupRow(0, 2);
					sheet.createFreezePane(0, 5);
					
					LocalDate endDate = bean.getEndDate().toInstant()
	                        .atZone(ZoneId.systemDefault())
	                        .toLocalDate();

					LocalDate cutoffDate = LocalDate.of(2024, 12, 1);
	
					if (bean.getCompcd().trim().equalsIgnoreCase(PFZ) && endDate.isBefore(cutoffDate)) {

						list_SG = list_SG.stream().peek(data -> {
							if ("CITIUS".equalsIgnoreCase(data.getDiv_disp_nm().trim())
									|| "CITIUS  (ALTIUS - NEW)".equalsIgnoreCase(data.getDiv_disp_nm().trim())) {
								data.setDiv_disp_nm("ALTIUS - NEW");
							}
						}).collect(Collectors.toList());

					}
					 
				
					for (BatchwiseStockStmtReport_SG data : list_SG) {
						
						colCount = 0;
						row = sheet.createRow(rowCount);
						
						cell = ReportFormats.cell(row, colCount, data.getDiv_disp_nm(), null);
						colCount++;
						
						cell = ReportFormats.cell(row, colCount, data.getSmp_prod_cd(), null);
						colCount++;
						
						cell = ReportFormats.cell(row, colCount, data.getSmp_prod_type(), null);
						colCount++;
						//chamges
						cell = ReportFormats.cell(row, colCount, data.getStorage_type(), null);
						colCount++;
						
						cell = ReportFormats.cell(row, colCount, data.getSmp_prod_name() == null ? "" : data.getSmp_prod_name().trim(), null);
						colCount++;
						
						cell = ReportFormats.cell(row, colCount, data.getBatch_no(), null);
						colCount++;
						
						cell = ReportFormats.cellNum(row, colCount, data.getOpen_stock() == null ? 0l :  data.getOpen_stock().doubleValue() , null);
						colCount++;
						
						cell = ReportFormats.cellNum(row, colCount, data.getGrn_receipts() == null ? 0l :  data.getGrn_receipts().doubleValue() , null);
						colCount++;
						
						cell = ReportFormats.cellNum(row, colCount, data.getAdj_receipts() == null ? 0l :  data.getAdj_receipts().doubleValue() , null);
						colCount++;
						
						cell = ReportFormats.cellNum(row, colCount, data.getTrf_receipts() == null ? 0l :  data.getTrf_receipts().doubleValue() , null);
						colCount++;
						
						cell = ReportFormats.cellNum(row, colCount, data.getIn_total() == null ? 0l :  data.getIn_total().doubleValue() , null);
						colCount++;
						
						cell = ReportFormats.cellNum(row, colCount, data.getDsp_issues() == null ? 0l :  data.getDsp_issues().doubleValue() , null);
						colCount++;
						
						cell = ReportFormats.cellNum(row, colCount, data.getAdj_issues() == null ? 0l :  data.getAdj_issues().doubleValue() , null);
						colCount++;
						
						cell = ReportFormats.cellNum(row, colCount, data.getTrf_issues() == null ? 0l :  data.getTrf_issues().doubleValue() , null);
						colCount++;
						
						cell = ReportFormats.cellNum(row, colCount, data.getOut_total() == null ? 0l :  data.getOut_total().doubleValue() , null);
						colCount++;
						
						
						cell = ReportFormats.cellNum(row, colCount, data.getClosing_stock() == null ? 0l :  data.getClosing_stock().doubleValue() , null);
						colCount++;
						
						XSSFCell cellforWithHeldQty = row.createCell(colCount);
						 
						 if(data.getBatch_with_held_qty() == null) {
							cellforWithHeldQty.setCellValue(01);
						 }
						 else {
							 if(data.getBatch_with_held_qty().doubleValue() == 0.0) {
								 cellforWithHeldQty.setCellValue(data.getBatch_with_held_qty().doubleValue());
							 }
							 else {
								 cellforWithHeldQty.setCellValue(data.getBatch_with_held_qty().doubleValue());
								 cellforWithHeldQty.setCellStyle(boldStyle);
							 }
							 
						 }
						 colCount++;
						
						cell = ReportFormats.cellNum(row, colCount, data.getCog_rate() == null ? 0l :  data.getCog_rate().doubleValue(), null);
						colCount++;
							
						
						cell = ReportFormats.cellNum(row, colCount, data.getClosing_val() == null ? 0l :  data.getClosing_val().doubleValue() , null);
						colCount++;
						
						cell = ReportFormats.cell(row, colCount, data.getBatch_expiry_dt(), null);
						colCount++;
						
						cell = ReportFormats.cellNum(row, colCount, data.getIntransit() == null ? 0l :  data.getIntransit().doubleValue() , null);
						colCount++;
						
						cell = ReportFormats.cellNum(row, colCount, data.getIntransit_val() == null ? 0l :  data.getIntransit_val().doubleValue() , null);
						colCount++;
						
						cell = ReportFormats.cell(row, colCount, data.getFcode(), null);
						colCount++;
						
						rowCount++;
						colCount=0;
						
					}
				
					FileOutputStream fileOutputStream = new FileOutputStream(filepath);
					workbook.write(fileOutputStream);
					
					filename=usermasterservice.generateExcellock(filepath, filename,empId,".xlsx");

					
				} finally {
					if (workbook != null) { workbook.close(); }
				}
				return filename;

	}

}
