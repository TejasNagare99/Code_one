package com.excel.service;

import java.io.File;
import java.io.FileOutputStream;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.SimpleDateFormat;
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
import com.excel.model.StkTrfDetailRepotModel;
import com.excel.model.StkTrfSummaryRepotModel;
import com.excel.model.Usermaster;
import com.excel.model.ViewGRNDetail;
import com.excel.model.ViewGRNSummary;
import com.excel.repository.UserMasterRepository;
import com.excel.security.PasswordManager;
import com.excel.utility.MedicoConstants;
import com.excel.utility.MedicoResources;
import com.excel.utility.ReportFormats;

import net.lingala.zip4j.core.ZipFile;
import net.lingala.zip4j.model.ZipParameters;
import net.lingala.zip4j.util.Zip4jConstants;



@Service
public class StkTrfReportServiceImpl implements StkTrfReportService,MedicoConstants{
	@Autowired private UserMasterService usermasterservice;

	@Autowired private UserMasterRepository usermasterrepository;

	@Override
	public String generateStkTrfSummaryReport(List<StkTrfSummaryRepotModel> list,ReportBean bean,String companyname,String empId) throws Exception {
		String filename = "StockTransferSummaryReport" + new Date().getTime()+ ".xlsx";
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
			XSSFSheet sheet = workbook.createSheet("Stock Transfer Summary Report");

			XSSFRow row = null;
			XSSFCell cell = null;
			int rowCount = 0;
			int colCount = 0;

			String headings[] = { "Location Name", "GST Reg No", "State", "Transfer No", "Transfer Date","CFA Name", "CFA Destination","CFA GST Reg No",
					"CFA State", "Goods Value", "Taxable Amount", "GST Description", "SGST Amount", "CGST Amount","IGST Amount",  
					"Total Amount", "LR No", "LR Date","Lorry No","Transporter Name","Weight","Full Shippers","Loose Shippers"};

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
			cell.setCellValue("Stock transfer Summary Report");
			cell.setCellStyle(headingCellStyle);
			rowCount++;

			row = sheet.createRow(rowCount);
			cell = row.createCell(colCount);
			sheet.addMergedRegion(new CellRangeAddress(rowCount, rowCount, colCount, colCount + headings.length - 1));
			cell.setCellValue("Period: "+MedicoResources.convertUtilDateToString_DD_MM_YYYY_(bean.getStartDate())+" to "+MedicoResources.convertUtilDateToString_DD_MM_YYYY_(bean.getEndDate()));
			cell.setCellStyle(headingCellStyle);

			rowCount += 1;
			row = sheet.createRow(rowCount);

			for (String heading : headings) {
				cell = ReportFormats.cell(row, colCount, heading, columnHeadingStyle);
				colCount++;
			}

			sheet.groupRow(0, 2);
			sheet.createFreezePane(0, 4);

			rowCount ++;
			colCount=0;

			BigDecimal goodsValueTot=BigDecimal.ZERO;
			BigDecimal taxAmtTot=BigDecimal.ZERO;
			BigDecimal sgstTot=BigDecimal.ZERO;
			BigDecimal cgstTot=BigDecimal.ZERO;
			BigDecimal igstTot=BigDecimal.ZERO;
			BigDecimal totAmtTot=BigDecimal.ZERO;
			SimpleDateFormat sdf=new SimpleDateFormat("dd-MM-yyyy");

			for (StkTrfSummaryRepotModel data : list) {

				rowCount++;
				colCount=0; 

				row = sheet.createRow(rowCount);

				cell = ReportFormats.cell(row, colCount, data.getLoc_nm(), null);
				colCount++;

				cell = ReportFormats.cell(row, colCount, data.getLoc_gst_reg_no(), null);
				colCount++;

				cell = ReportFormats.cell(row, colCount, data.getLoc_state_name(), null);
				colCount++;

				cell = ReportFormats.cell(row, colCount, data.getTrf_no(), null);
				colCount++;

				cell = ReportFormats.cell(row, colCount, data.getTrf_date() == null ? "" : sdf.format(data.getTrf_date()), null);
				colCount++;

				cell = ReportFormats.cell(row, colCount, data.getCfa_name(), null);
				colCount++;

				cell = ReportFormats.cell(row, colCount, data.getCfa_destination(), null);
				colCount++;

				cell = ReportFormats.cell(row, colCount, data.getCfa_gst_reg_no(), null);
				colCount++;

				cell = ReportFormats.cell(row, colCount, data.getCfa_state_name(), null);
				colCount++;

				cell = ReportFormats.cellNum(row, colCount, data.getGoods_value() == null ? 0l : data.getGoods_value().doubleValue(), cellAlignment);
				colCount++;

				cell = ReportFormats.cellNum(row, colCount, data.getTaxable_amt() == null ? 0l : data.getTaxable_amt().doubleValue(), cellAlignment);
				colCount++;

				cell = ReportFormats.cell(row, colCount, data.getGst_description(), null);
				colCount++;

				cell = ReportFormats.cellNum(row, colCount, data.getSgst_bill_amt() == null ? 0l : data.getSgst_bill_amt().doubleValue(), cellAlignment);
				colCount++;

				cell = ReportFormats.cellNum(row, colCount, data.getCgst_bill_amt() == null ? 0l :data.getCgst_bill_amt().doubleValue(), cellAlignment);
				colCount++;

				cell = ReportFormats.cellNum(row, colCount, data.getIgst_bill_amt() == null ? 0l : data.getIgst_bill_amt().doubleValue(), cellAlignment);
				colCount++;

				cell = ReportFormats.cellNum(row, colCount, data.getTot_amt() == null ? 0l : data.getTot_amt().doubleValue(), cellAlignment);
				colCount++;

				cell = ReportFormats.cell(row, colCount, data.getLr_no()== null ? "": data.getLr_no(), null);
				colCount++;

				cell = ReportFormats.cell(row, colCount, data.getLr_date()== null ? "": sdf.format(data.getLr_date()), null);
				colCount++;

				cell = ReportFormats.cell(row, colCount, data.getLorry_no()== null ? "" : data.getLorry_no(), null);
				colCount++;

				cell = ReportFormats.cell(row, colCount, data.getTransporter_name() == null ? "": data.getTransporter_name(), null);
				colCount++;

				cell = ReportFormats.cell(row, colCount, data.getWeight() == null ? "" : data.getWeight().toString(), cellAlignment);
				colCount++;

				cell = ReportFormats.cell(row, colCount, data.getFull_shippers() == null ? "" :data.getFull_shippers().toString(), cellAlignment);
				colCount++;

				cell = ReportFormats.cell(row, colCount, data.getLoose_shippers() == null ? "" : data.getLoose_shippers().toString(), cellAlignment);
				colCount++;


				goodsValueTot=goodsValueTot.add(data.getGoods_value()==null ? BigDecimal.ZERO :data.getGoods_value());
				taxAmtTot=taxAmtTot.add(data.getTaxable_amt()==null ? BigDecimal.ZERO :data.getTaxable_amt());
				sgstTot=sgstTot.add(data.getSgst_bill_amt()==null ? BigDecimal.ZERO :data.getSgst_bill_amt());
				cgstTot=cgstTot.add(data.getCgst_bill_amt()==null ? BigDecimal.ZERO :data.getCgst_bill_amt());
				igstTot=igstTot.add(data.getIgst_bill_amt()==null ? BigDecimal.ZERO :data.getIgst_bill_amt());
				totAmtTot=totAmtTot.add(data.getTot_amt()==null ? BigDecimal.ZERO :data.getTot_amt());

			}

			rowCount++;
			colCount=0;

			row = sheet.createRow(rowCount);

			cell = ReportFormats.cell(row, colCount, "Total", cellAlignment);
			colCount++;

			for(int i=0;i<8;i++) {
				cell = ReportFormats.cell(row, colCount, "", cellAlignment);
				colCount++;
			}

			cell = ReportFormats.cell(row, colCount, goodsValueTot.toString(), cellAlignment);
			colCount++;

			cell = ReportFormats.cell(row, colCount, taxAmtTot.toString(), cellAlignment);
			colCount++;

			cell = ReportFormats.cell(row, colCount, "", cellAlignment);
			colCount++;

			cell = ReportFormats.cell(row, colCount, sgstTot.toString(), cellAlignment);
			colCount++;

			cell = ReportFormats.cell(row, colCount, cgstTot.toString(), cellAlignment);
			colCount++;

			cell = ReportFormats.cell(row, colCount, igstTot.toString(), cellAlignment);
			colCount++;

			cell = ReportFormats.cell(row, colCount, totAmtTot.toString(), cellAlignment);
			colCount++;

			for(int i=0;i<7;i++) 
			{
				cell = ReportFormats.cell(row, colCount, "", cellAlignment);
				colCount++;	
			}

			FileOutputStream fileOutputStream = new FileOutputStream(filepath);
			workbook.write(fileOutputStream);
			filename=usermasterservice.generateExcellock(filepath, filename,empId,".xlsx");


		} finally {
			if (workbook != null) { workbook.close(); }
		}
		return filename;
	}

	@Override
	public String generateStkTrfDetailReport(List<StkTrfDetailRepotModel> list,ReportBean bean,String companyname,String empId) throws Exception {
		String filename = "StockTransferDetailReport" + new Date().getTime() +".xlsx";
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
			XSSFSheet sheet = workbook.createSheet("Stock Transfer Detail Report");

			XSSFRow row = null;
			XSSFCell cell = null;
			int rowCount = 0;
			int colCount = 0;

			String headings[] = { "Location Name", "GST Reg No", "State", "Transfer No", "Transfer Date","CFA Name", "CFA Destination","CFA GST Reg No",
					"CFA State","Product Code","Division / ERP Product Code","Product Name", "Product Type", "HSN Code", "Batch No", "Batch Mfg Dt", "Batch Expiry Dt","Transfer Qty",  
					"Free Qty", "Rate", "Goods Value","Taxable Amount","GST Description","SGST %","SGST Amount","CGST %","CGST Amount"
					,"IGST %","IGST Amount","Total Amount","LR No","LR Date","Lorry No","Transporter Name","Weight","Full Shippers","Loose Shippers"};

			XSSFCellStyle headingCellStyle = ReportFormats.heading(workbook);
			XSSFCellStyle columnHeadingStyle = ReportFormats.columnHeading(workbook);
			XSSFCellStyle cellAlignment = ReportFormats.setCellAlignment(workbook, ALIGN_RIGHT);
			XSSFCellStyle cellAlignment_total = ReportFormats.setCellAlignment_total(workbook);
			

			row = sheet.createRow(rowCount);
			cell = row.createCell(colCount);
			sheet.addMergedRegion(new CellRangeAddress(rowCount, rowCount, colCount, colCount + headings.length - 1));
			cell.setCellValue(companyname);
			cell.setCellStyle(headingCellStyle);
			rowCount++;

			row = sheet.createRow(rowCount);
			cell = row.createCell(colCount);
			sheet.addMergedRegion(new CellRangeAddress(rowCount, rowCount, colCount, colCount + headings.length - 1));
			cell.setCellValue("Stock Transfer Detail Report");
			cell.setCellStyle(headingCellStyle);
			rowCount++;

			row = sheet.createRow(rowCount);
			cell = row.createCell(colCount);
			sheet.addMergedRegion(new CellRangeAddress(rowCount, rowCount, colCount, colCount + headings.length - 1));
			cell.setCellValue("Period: "+MedicoResources.convertUtilDateToString_DD_MM_YYYY_(bean.getStartDate())+" to "+MedicoResources.convertUtilDateToString_DD_MM_YYYY_(bean.getEndDate()));
			cell.setCellStyle(headingCellStyle);

			rowCount += 1;
			row = sheet.createRow(rowCount);

			for (String heading : headings) {
				cell = ReportFormats.cell(row, colCount, heading, columnHeadingStyle);
				colCount++;
			}

			sheet.groupRow(0, 2);
			sheet.createFreezePane(0, 4);

			rowCount ++;
			colCount=0;

			BigDecimal goodsValueTot=BigDecimal.ZERO;
			BigDecimal taxAmtTot=BigDecimal.ZERO;
			BigDecimal sgstTot=BigDecimal.ZERO;
			BigDecimal cgstTot=BigDecimal.ZERO;
			BigDecimal igstTot=BigDecimal.ZERO;
			BigDecimal totAmtTot=BigDecimal.ZERO;
			
			SimpleDateFormat sdf=new SimpleDateFormat("dd-MM-yyyy");
			for (StkTrfDetailRepotModel data : list) {


				row = sheet.createRow(rowCount);
				cell = ReportFormats.cell(row, colCount, data.getLoc_nm() == null ?"":data.getLoc_nm(), null);
				colCount++;

				cell = ReportFormats.cell(row, colCount, data.getLoc_gst_reg_no() == null ?"":data.getLoc_gst_reg_no(), null);
				colCount++;

				cell = ReportFormats.cell(row, colCount, data.getLoc_state_name()==null?"":data.getLoc_state_name(), null);
				colCount++;


				cell = ReportFormats.cell(row, colCount, data.getTrf_no()==null ?"":data.getTrf_no(), null);
				colCount++;

				cell = ReportFormats.cell(row, colCount, data.getTrf_date()== null ?"":sdf.format(data.getTrf_date()), null);
				colCount++;

				cell = ReportFormats.cell(row, colCount, data.getCfa_name()== null ?"":data.getCfa_name(), null);
				colCount++;

				cell = ReportFormats.cell(row, colCount, data.getCfa_destination()== null ?"":data.getCfa_destination(), null);
				colCount++;

				cell = ReportFormats.cell(row, colCount, data.getCfa_gst_reg_no() == null ?"":data.getCfa_gst_reg_no(), null);
				colCount++;

				cell = ReportFormats.cell(row, colCount, data.getCfa_state_name() == null ?"":data.getCfa_state_name(), null);
				colCount++;

				cell = ReportFormats.cell(row, colCount, data.getSmp_prod_cd() == null ?"":data.getSmp_prod_cd(), null);
				colCount++;

				cell = ReportFormats.cell(row, colCount, data.getSmp_erp_prod_cd() == null ?"":data.getSmp_erp_prod_cd(), null);
				colCount++;

				cell = ReportFormats.cell(row, colCount, data.getProd_name_pack() == null ?"":data.getProd_name_pack(), null);
				colCount++;

				cell = ReportFormats.cell(row, colCount, data.getSMP_PROD_TYPE() == null ?"":data.getSMP_PROD_TYPE(), null);
				colCount++;

				cell = ReportFormats.cell(row, colCount, data.getHSN_CODE() == null ?"":data.getHSN_CODE().toString(), null);
				colCount++;

				cell = ReportFormats.cell(row, colCount, data.getBatch_no() == null ?"":data.getBatch_no(), null);
				colCount++;

				cell = ReportFormats.cell(row, colCount, data.getBatch_mfg_dt()== null ?"":sdf.format(data.getBatch_mfg_dt()), null);
				colCount++;

				cell = ReportFormats.cell(row, colCount, data.getBatch_expiry_dt() == null ?"": sdf.format(data.getBatch_expiry_dt()), null);
				colCount++;

				cell = ReportFormats.cellNum(row, colCount, data.getSold_qty()== null ?0l:data.getSold_qty().doubleValue(), null);
				colCount++;

				cell = ReportFormats.cellNum(row, colCount, data.getFree_qty()== null ?0l:data.getFree_qty().doubleValue(), null);
				colCount++;

				cell = ReportFormats.cellNum(row, colCount, data.getRate()== null ?0l:data.getRate().doubleValue(), null);
				colCount++;

				cell = ReportFormats.cellNum(row, colCount, data.getGoods_value()== null ?0l:data.getGoods_value().doubleValue(), null);
				colCount++;

				cell = ReportFormats.cellNum(row, colCount, data.getTaxable_amt()== null ?0l:data.getTaxable_amt().doubleValue(), null);
				colCount++;

				cell = ReportFormats.cell(row, colCount, data.getGst_description()== null ?"":data.getGst_description(), null);
				colCount++;

				cell = ReportFormats.cellNum(row, colCount, data.getSgst_rate()== null ?0l:data.getSgst_rate().doubleValue(), null);
				colCount++;

				cell = ReportFormats.cellNum(row, colCount, data.getSgst_bill_amt()== null ?0l:data.getSgst_bill_amt().doubleValue(), null);
				colCount++;

				cell = ReportFormats.cellNum(row, colCount, data.getCgst_rate()== null ?0l:data.getCgst_rate().doubleValue(), null);
				colCount++;

				cell = ReportFormats.cellNum(row, colCount, data.getCgst_bill_amt()== null ?0l:data.getCgst_bill_amt().doubleValue(), null);
				colCount++;

				cell = ReportFormats.cellNum(row, colCount, data.getIgst_rate()== null ?0l:data.getIgst_bill_amt().doubleValue(), null);
				colCount++;
				
				cell = ReportFormats.cellNum(row, colCount, data.getIgst_bill_amt()== null ?0l:data.getIgst_bill_amt().doubleValue(), null);
				colCount++;

				cell = ReportFormats.cellNum(row, colCount, data.getTot_amt()== null ?0l:data.getTot_amt().doubleValue(), null);
				colCount++;
				
				cell = ReportFormats.cell(row, colCount, data.getLr_no()== null ?"":data.getLr_no(), null);
				colCount++;
				
				cell = ReportFormats.cell(row, colCount, data.getLr_date()== null ?"":data.getLr_date().toString(), null);
				colCount++;
				
				cell = ReportFormats.cell(row, colCount, data.getLorry_no()== null ?"":data.getLorry_no().toString(), null);
				colCount++;
				
				cell = ReportFormats.cell(row, colCount, data.getTransporter_name()== null ?"":data.getTransporter_name(), null);
				colCount++;
				
				cell = ReportFormats.cell(row, colCount, data.getWeight()== null ?"":data.getWeight().toString(), null);
				colCount++;
				
				cell = ReportFormats.cell(row, colCount, data.getFull_shippers()== null ?"":data.getFull_shippers().toString(), null);
				colCount++;
				
				cell = ReportFormats.cell(row, colCount, data.getLoose_shippers()== null ?"":data.getLoose_shippers().toString(), null);
				colCount++;

				goodsValueTot=goodsValueTot.add(data.getGoods_value()==null ? BigDecimal.ZERO :data.getGoods_value());
				taxAmtTot=taxAmtTot.add(data.getTaxable_amt()==null ? BigDecimal.ZERO :data.getTaxable_amt());
				sgstTot=sgstTot.add(data.getSgst_bill_amt()==null ? BigDecimal.ZERO :data.getSgst_bill_amt());
				cgstTot=cgstTot.add(data.getCgst_bill_amt()==null ? BigDecimal.ZERO :data.getCgst_bill_amt());
				igstTot=igstTot.add(data.getIgst_bill_amt()==null ? BigDecimal.ZERO :data.getIgst_bill_amt());
				totAmtTot=totAmtTot.add(data.getTot_amt()==null ? BigDecimal.ZERO :data.getTot_amt());
				

				rowCount++;
				colCount=0; 
			}

			rowCount++;
			colCount=0;

			row = sheet.createRow(rowCount);

	
			
			for(int i=0;i<20;i++) {
				
				if(i==17) {
					cell = ReportFormats.cell(row, colCount, "Total", cellAlignment_total);
					
				}else {
					cell = ReportFormats.cell(row, colCount, "", cellAlignment);
				}
			
				colCount++;
			}

			cell = ReportFormats.cell(row, colCount, goodsValueTot.toString(), cellAlignment);
			colCount++;


			cell = ReportFormats.cell(row, colCount, taxAmtTot.toString(), cellAlignment);
			colCount++;

			cell = ReportFormats.cell(row, colCount, "", cellAlignment);
			colCount++;

			cell = ReportFormats.cell(row, colCount, "", cellAlignment);
			colCount++;

			cell = ReportFormats.cell(row, colCount, sgstTot.toString(), cellAlignment);
			colCount++;

			cell = ReportFormats.cell(row, colCount, "", cellAlignment);
			colCount++;

			cell = ReportFormats.cell(row, colCount, cgstTot.toString(), cellAlignment);
			colCount++;

			cell = ReportFormats.cell(row, colCount, "", cellAlignment);
			colCount++;

			cell = ReportFormats.cell(row, colCount, igstTot.toString(), cellAlignment);
			colCount++;

			cell = ReportFormats.cell(row, colCount, totAmtTot.toString(), cellAlignment);
			colCount++;

			for(int i=0;i<7;i++) 
			{
				cell = ReportFormats.cell(row, colCount, "", cellAlignment);
				colCount++;	
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
