//package com.excel.service;
//
//public class BatchwiseLedgerReport_ServiceImpl {
//
//}
package com.excel.service;

import java.io.File;
import java.io.FileOutputStream;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.ss.usermodel.BorderStyle;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.DataFormat;
import org.apache.poi.ss.usermodel.FillPatternType;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.VerticalAlignment;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.excel.bean.ReportBean;
import com.excel.model.BatchWiseStockLedgerModel;
import com.excel.model.Usermaster;
import com.excel.repository.UserMasterRepository;
import com.excel.security.PasswordManager;
import com.excel.utility.CodifyErrors;
import com.excel.utility.MedicoConstants;
import com.excel.utility.MedicoResources;

import net.lingala.zip4j.core.ZipFile;
import net.lingala.zip4j.model.ZipParameters;
import net.lingala.zip4j.util.Zip4jConstants;

@Service 
public class BatchwiseLedgerReport_ServiceImpl   implements BatchwiseLedgerReport_Service,MedicoConstants{
	@Autowired private UserMasterService usermasterservice;

	@Autowired private UserMasterRepository usermasterrepository;
	private String filename;
	private Date dt;
	SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.S");//yyyy-MM-dd'T'HH:mm:ss
	SimpleDateFormat sdf1 = new SimpleDateFormat("dd-MM-yyyy");
    SimpleDateFormat output = new SimpleDateFormat("dd/MM/yyyy");
    
	@Override
	public String GenerateBatchwiseLedgerReport(List<BatchWiseStockLedgerModel> list, ReportBean bean,String Companyname,String empId)
			throws Exception {
		Workbook wwbook = null;
		try {
			System.out.println("generateExcel ----------------------------------------------------------------------");
			// Thread.sleep(10000);


			File ff = null;
			String comp_name = Companyname;
			String _hostPath = MedicoConstants.REPORT_FILE_PATH;

			System.out.println("_hostPath -- " + _hostPath);
			filename = "batchWiseStockLedger" + new Date().getTime()+ ".xlsx";

			StringBuffer path = new StringBuffer(_hostPath).append("\\");
			path.append(filename );
			ff = new File(path.toString());
			wwbook = new XSSFWorkbook();
//		 path = REPORT_FILE_PATH;
			String filepath = REPORT_FILE_PATH + filename + ".xlsx";

			File file = new File(_hostPath);
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

			Sheet wsheet = wwbook.createSheet("batchWiseStockLedger");
			wsheet.createFreezePane(0, 4);
			DataFormat format = wwbook.createDataFormat();
			CellStyle alignment = wwbook.createCellStyle();
			alignment.setFillForegroundColor(HSSFColor.HSSFColorPredefined.GREY_25_PERCENT.getIndex());
			alignment.setFillPattern(FillPatternType.SOLID_FOREGROUND);
			Font font = wwbook.createFont();
			font.setFontHeightInPoints((short) 13);
			font.setFontName("ARIAL");
			font.setColor(IndexedColors.BLACK.getIndex());
			font.setBold(true);
			alignment.setFont(font);
			alignment.setAlignment(HorizontalAlignment.CENTER);
			alignment.setVerticalAlignment(VerticalAlignment.CENTER);

			CellStyle alignment1 = wwbook.createCellStyle();
			alignment1.setFillForegroundColor(HSSFColor.HSSFColorPredefined.AQUA.getIndex());
			alignment1.setFillPattern(FillPatternType.SOLID_FOREGROUND);
			font = wwbook.createFont();
			font.setFontHeightInPoints((short) 11);
			font.setFontName("ARIAL");
			font.setColor(IndexedColors.BLACK.getIndex());
			font.setBold(true);
			alignment1.setFont(font);
			alignment1.setBorderBottom(BorderStyle.THIN);
			alignment1.setBorderLeft(BorderStyle.THIN);
			alignment1.setBorderTop(BorderStyle.THIN);
			alignment1.setBorderRight(BorderStyle.THIN);
			alignment1.setAlignment(HorizontalAlignment.LEFT);

			CellStyle alignmentT = wwbook.createCellStyle();
			alignmentT.setFillForegroundColor(HSSFColor.HSSFColorPredefined.AQUA.getIndex());
			alignmentT.setFillPattern(FillPatternType.SOLID_FOREGROUND);
			font = wwbook.createFont();
			font.setFontHeightInPoints((short) 11);
			font.setFontName("ARIAL");
			font.setColor(IndexedColors.BLACK.getIndex());
			font.setBold(true);
			alignmentT.setFont(font);
			alignmentT.setBorderBottom(BorderStyle.THIN);
			alignmentT.setBorderTop(BorderStyle.THIN);
			alignmentT.setAlignment(HorizontalAlignment.LEFT);

			CellStyle alignment2 = wwbook.createCellStyle();
			alignment2.setFillForegroundColor(HSSFColor.HSSFColorPredefined.AQUA.getIndex());
			alignment2.setFillPattern(FillPatternType.SOLID_FOREGROUND);
			font = wwbook.createFont();
			font.setFontHeightInPoints((short) 11);
			font.setFontName("ARIAL");
			font.setBold(true);
			alignment2.setFont(font);
			alignment2.setBorderBottom(BorderStyle.THIN);
			alignment2.setBorderLeft(BorderStyle.THIN);
			alignment2.setBorderTop(BorderStyle.THIN);
			alignment2.setBorderRight(BorderStyle.THIN);
			alignment2.setAlignment(HorizontalAlignment.RIGHT);

			CellStyle rightAlignWthDecimal = wwbook.createCellStyle();
			font = wwbook.createFont();
			font.setFontHeightInPoints((short) 11);
			font.setFontName("ARIAL");
			rightAlignWthDecimal.setFont(font);
			rightAlignWthDecimal.setDataFormat(format.getFormat("0.00"));
			rightAlignWthDecimal.setBorderBottom(BorderStyle.THIN);
			rightAlignWthDecimal.setBorderLeft(BorderStyle.THIN);
			rightAlignWthDecimal.setBorderTop(BorderStyle.THIN);
			rightAlignWthDecimal.setBorderRight(BorderStyle.THIN);
			rightAlignWthDecimal.setAlignment(HorizontalAlignment.RIGHT);

			CellStyle rightAlign = wwbook.createCellStyle();
			font = wwbook.createFont();
			font.setFontHeightInPoints((short) 11);
			font.setFontName("ARIAL");
			rightAlign.setFont(font);
			rightAlign.setBorderBottom(BorderStyle.THIN);
			rightAlign.setBorderLeft(BorderStyle.THIN);
			rightAlign.setBorderTop(BorderStyle.THIN);
			rightAlign.setBorderRight(BorderStyle.THIN);
			rightAlign.setAlignment(HorizontalAlignment.RIGHT);

			CellStyle leftAlign = wwbook.createCellStyle();
			font = wwbook.createFont();
			font.setFontHeightInPoints((short) 11);
			font.setFontName("ARIAL");
			leftAlign.setFont(font);
			leftAlign.setBorderBottom(BorderStyle.THIN);
			leftAlign.setBorderLeft(BorderStyle.THIN);
			leftAlign.setBorderTop(BorderStyle.THIN);
			leftAlign.setBorderRight(BorderStyle.THIN);
			leftAlign.setAlignment(HorizontalAlignment.LEFT);

			int col = 0, row = 0;
			XSSFRow hrow = (XSSFRow) wsheet.createRow(row);
			XSSFCell cell = hrow.createCell(col);
			cell.setCellStyle(alignment);
			cell.setCellValue(comp_name);
			wsheet.addMergedRegion(new CellRangeAddress(row, row, col, col + 14));
			row++;

			hrow = (XSSFRow) wsheet.createRow(row);
			cell = hrow.createCell(col);
			cell.setCellStyle(alignment);
			cell.setCellValue("Batch Wise Stock Ledger Report");
			wsheet.addMergedRegion(new CellRangeAddress(row, row, col, col + 14));
			row++;

			hrow = (XSSFRow) wsheet.createRow(row);
			cell = hrow.createCell(col);
			cell.setCellStyle(alignment);
//			cell.setCellValue(output.format(output.parse(frmDate))+" to "+ output.format(output.parse(toDate)));
			cell.setCellValue("From " + MedicoResources.convertUtilDateToString_DD_MM_YYYY_(bean.getStartDate())
					+ " to " + MedicoResources.convertUtilDateToString_DD_MM_YYYY_(bean.getEndDate()));
			wsheet.addMergedRegion(new CellRangeAddress(row, row, col, col + 14));
			row++;

			hrow = (XSSFRow) wsheet.createRow(row);
			cell = hrow.createCell(col);
			cell.setCellStyle(alignment1);
			cell.setCellValue("Sr.No");
			col++;
			cell = hrow.createCell(col);
			cell.setCellStyle(alignment1);
			cell.setCellValue("Transaction Type");
			col++;
			cell = hrow.createCell(col);
			cell.setCellStyle(alignment1);
			cell.setCellValue("Transaction Number");
			col++;
			cell = hrow.createCell(col);
			cell.setCellStyle(alignment1);
			cell.setCellValue("Transaction Date");
			col++;
			cell = hrow.createCell(col);
			cell.setCellStyle(alignment1);
			cell.setCellValue("Challan Number");
			col++;
			cell = hrow.createCell(col);
			cell.setCellStyle(alignment1);
			cell.setCellValue("Challan Date");
			col++;
			cell = hrow.createCell(col);
			cell.setCellStyle(alignment1);
			cell.setCellValue("Reference Number");
			col++;
			cell = hrow.createCell(col);
			cell.setCellStyle(alignment1);
			cell.setCellValue("Destination");
			col++;
			cell = hrow.createCell(col);
			cell.setCellStyle(alignment1);
			cell.setCellValue("Invoice Number");
			col++;
			cell = hrow.createCell(col);
			cell.setCellStyle(alignment1);
			cell.setCellValue("GRN");
			col++;
			cell = hrow.createCell(col);
			cell.setCellStyle(alignment1);
			cell.setCellValue("Adj/Trf IN Quantity");
			col++;
			cell = hrow.createCell(col);
			cell.setCellStyle(alignment1);
			cell.setCellValue("Dispatch Quantity");
			col++;
			cell = hrow.createCell(col);
			cell.setCellStyle(alignment1);
			cell.setCellValue("Adj/Trf OUT Quantity");
			col++;
			cell = hrow.createCell(col);
			cell.setCellStyle(alignment1);
			cell.setCellValue("Batch Rate");
			col++;
			cell = hrow.createCell(col);
			cell.setCellStyle(alignment1);
			cell.setCellValue("Balance Quantity");
			col++;

			String oldDiv = "";
			String oldItem = "";
			String oldBatch = "";
			String newDiv = "";
			String newItem = "";
			String newBatch = "";
			int cnt = 0;
			String dtft = "";
			
			LocalDate endDate = bean.getEndDate().toInstant()
                    .atZone(ZoneId.systemDefault())
                    .toLocalDate();

			LocalDate cutoffDate = LocalDate.of(2024, 12, 1);

			if (bean.getCompcd().trim().equalsIgnoreCase(PFZ) && endDate.isBefore(cutoffDate)) {

				list = list.stream().peek(data -> {
					if ("CITIUS".equalsIgnoreCase(data.getDiv_disp_nm())
							|| "CITIUS  (ALTIUS - NEW)".equalsIgnoreCase(data.getDiv_disp_nm())) {
						data.setDiv_disp_nm("ALTIUS - NEW");
					}
				}).collect(Collectors.toList());

			}
			
			for (BatchWiseStockLedgerModel lst : list) {
				newDiv = lst.getDiv_disp_nm();
				newItem = lst.getProd_name();
				newBatch = lst.getBatch_no();
				row++;
				cnt++;
				col = 0;
				if (!(newDiv.equalsIgnoreCase(oldDiv))) {
					hrow = (XSSFRow) wsheet.createRow(row);
					cell = hrow.createCell(col);
					cell.setCellStyle(alignmentT);
					cell.setCellValue("Division : " + lst.getDiv_disp_nm());
					wsheet.addMergedRegion(new CellRangeAddress(row, row, col, col + 14));
					row++;
				}
				if (!(newItem.equalsIgnoreCase(oldItem))) {
					hrow = (XSSFRow) wsheet.createRow(row);
					cell = hrow.createCell(col);
					cell.setCellStyle(alignmentT);
					cell.setCellValue("Item : " + lst.getProd_name());
					wsheet.addMergedRegion(new CellRangeAddress(row, row, col, col + 14));
					row++;
				}
				if (!(newBatch.equalsIgnoreCase(oldBatch))) {
					hrow = (XSSFRow) wsheet.createRow(row);
					cell = hrow.createCell(col);
					cell.setCellStyle(alignmentT);
					cell.setCellValue("Batch  : " + lst.getBatch_no());
					wsheet.addMergedRegion(new CellRangeAddress(row, row, col, col + 14));
					row++;
				}

				hrow = (XSSFRow) wsheet.createRow(row);
				cell = hrow.createCell(col);
				cell.setCellStyle(rightAlign);
				cell.setCellValue(cnt);
				col++;
				cell = hrow.createCell(col);
				cell.setCellStyle(leftAlign);
				cell.setCellValue(lst.getTrn_type());
				col++;
				cell = hrow.createCell(col);
				cell.setCellStyle(leftAlign);
				cell.setCellValue(lst.getTrn_num());
				col++;
				if (lst.getTrn_dt() != null) {
					dt = sdf.parse(lst.getTrn_dt());
					dtft = output.format(dt);
					cell = hrow.createCell(col);
					cell.setCellStyle(leftAlign);
					cell.setCellValue(dtft);
					col++;
				} else {
					cell = hrow.createCell(col);
					cell.setCellStyle(leftAlign);
					cell.setCellValue(lst.getTrn_dt());
					col++;
				}
				cell = hrow.createCell(col);
				cell.setCellStyle(leftAlign);
				cell.setCellValue(lst.getChallan_no());
				col++;
				if (lst.getChallan_dt() != null) {
					dt = sdf.parse(lst.getChallan_dt());
					dtft = output.format(dt);
					cell = hrow.createCell(col);
					cell.setCellStyle(leftAlign);
					cell.setCellValue(dtft);
					col++;
				} else {
					cell = hrow.createCell(col);
					cell.setCellStyle(leftAlign);
					cell.setCellValue(lst.getChallan_dt());
					col++;
				}
				cell = hrow.createCell(col);
				cell.setCellStyle(leftAlign);
				cell.setCellValue(lst.getReference_no());
				col++;
				cell = hrow.createCell(col);
				cell.setCellStyle(leftAlign);
				cell.setCellValue(lst.getDestination());
				col++;
				cell = hrow.createCell(col);
				cell.setCellStyle(leftAlign);
				cell.setCellValue(lst.getChallan_no());
				col++;
				cell = hrow.createCell(col);
				cell.setCellStyle(rightAlignWthDecimal);
				cell.setCellValue(Double.parseDouble(lst.getGrn_qty().toString()));
				col++;
				cell = hrow.createCell(col);
				cell.setCellStyle(rightAlignWthDecimal);
				cell.setCellValue(Double.parseDouble(lst.getInadj_qty().toString()));
				col++;
				cell = hrow.createCell(col);
				cell.setCellStyle(rightAlignWthDecimal);
				cell.setCellValue(Double.parseDouble(lst.getDesp_qty().toString()));
				col++;
				cell = hrow.createCell(col);
				cell.setCellStyle(rightAlignWthDecimal);
				cell.setCellValue(Double.parseDouble(lst.getOutadj_qty().toString()));
				col++;
				cell = hrow.createCell(col);
				cell.setCellStyle(rightAlignWthDecimal);
				cell.setCellValue(Double.parseDouble(lst.getBatch_rate().toString()));
				col++;
				cell = hrow.createCell(col);
				cell.setCellStyle(rightAlignWthDecimal);
				cell.setCellValue(lst.getBal_qty());
				col++;

				oldDiv = newDiv;
				oldItem = newItem;
				oldBatch = newBatch;
			}

			row++;
			col = 0;

			FileOutputStream fileOut = new FileOutputStream(path.toString());
			wwbook.write(fileOut);

			filename=usermasterservice.generateExcellock(path.toString(), filename,empId,".xlsx");


			fileOut.close();
			wwbook.close();

			System.out.println("Excel Created");

		} catch (Exception e) {
			System.out.println("Error Occurred :" +e.getMessage());// uncomment asneeded --;
		} finally {

		}
		return filename ;

	}

}