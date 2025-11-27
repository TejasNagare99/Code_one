package com.excel.service;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.DataFormat;
import org.apache.poi.ss.usermodel.FillPatternType;
import org.springframework.stereotype.Service;

import com.excel.bean.ReportBean;
import com.excel.model.Sap_Dispatch_Download;
import com.excel.utility.CodifyErrors;
import com.excel.utility.MedicoConstants;
@Service
public class SapDispatchDownloadServiceImpl implements SapDispatchDownloadService{

	@Override
	public String GeneratesapReport(ReportBean bean, List<Sap_Dispatch_Download> list) throws Exception {


		System.out.println("inside Disptach Download Report");
		File ff = null;
		StringBuffer path = null;
		String fileName = "SAP_DISPATCH_DOWNLOAD " + new Date().getTime() + ".xlsx";
		String filePath = MedicoConstants.REPORT_FILE_PATH;
		 path = new StringBuffer(filePath).append("\\");
		path.append(fileName);
		 ff=new File(path.toString());
		System.out.println("filename "+fileName);
		File file = new File(filePath);
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
		try {

			String oldt,newdt;
			BigDecimal number;

			// FileOutputStream fout = new FileOutputStream(ff);
			int cnt = 0,rowCount = 0,colCount=0;

			HSSFWorkbook wb = new HSSFWorkbook();
			/*	    XSSFSheet sheet = wb.createSheet("Header");
			 */	    HSSFSheet sheet1 = wb.createSheet("703");
			 HSSFSheet sheet2 = wb.createSheet("343");
			HSSFRow row = null;
			HSSFCell cell = null;

			 //ApachePoiExcelFormat xlsxExcelFormat = new ApachePoiExcelFormat();
			// CellStyle sheetHeading = xlsxExcelFormat.SheetHeading(wb);
			 //CellStyle columnHeading = xlsxExcelFormat.columnHeading(wb);
			 //CellStyle rightAlign = xlsxExcelFormat.dataRightAlignWthDecimal(wb);
			 //CellStyle leftAlign = xlsxExcelFormat.dataLeftAlign(wb);
			//// CellStyle columnHeadingLemon = xlsxExcelFormat.columnSubHeadingLemon(wb);  
			 //CellStyle columnHeadingred = xlsxExcelFormat.columnSubHeadingRed(wb);

			// CellStyle leftAlignB = xlsxExcelFormat.dataBoldLeftAlign(wb);
			// CellStyle rightAlignWth3Decimal = xlsxExcelFormat.dataRightAlignWth3Decimal(wb);
			CellStyle columnHeadingLemon =wb.createCellStyle();
			columnHeadingLemon.setFillForegroundColor(HSSFColor.HSSFColorPredefined.LIME.getIndex());
			columnHeadingLemon.setFillPattern(FillPatternType.SOLID_FOREGROUND);
			
			CellStyle columnHeadingred = wb.createCellStyle();
			columnHeadingred.setFillForegroundColor(HSSFColor.HSSFColorPredefined.RED.getIndex());
			columnHeadingred.setFillPattern(FillPatternType.SOLID_FOREGROUND);
			
			CellStyle rightAlignWth3Decimal = wb.createCellStyle();
			DataFormat dataFormat = wb.createDataFormat();
			rightAlignWth3Decimal.setDataFormat(wb.getCreationHelper().createDataFormat().getFormat("0.000"));
			
			 row = sheet1.createRow(rowCount);
			 cell = row.createCell(colCount);
			 cell.setCellValue("Plant");
			 // cell.setCellStyle(columnHeadingLemon);
			 colCount++;
			 cell = row.createCell(colCount);
			 cell.setCellValue("Sloc (From)");
			 //cell.setCellStyle(columnHeadingLemon);
			 colCount++;
			 cell = row.createCell(colCount);
			 cell.setCellValue("Material No.");
			 // cell.setCellStyle(columnHeadingLemon);
			 colCount++;
			 cell = row.createCell(colCount);
			 cell.setCellValue("Batch");
			 // cell.setCellStyle(columnHeadingLemon);
			 colCount++;
			 cell = row.createCell(colCount);
			 cell.setCellValue("Quantity");
			 // cell.setCellStyle(columnHeadingLemon);
			 colCount++;
			 cell = row.createCell(colCount);
			 cell.setCellValue("Mvt. Type");
			 // cell.setCellStyle(columnHeadingLemon);
			 colCount++;
			 cell = row.createCell(colCount);
			 cell.setCellValue(" Reason Cd.");
			 //  cell.setCellStyle(columnHeadingLemon);
			 colCount++;
			 cell = row.createCell(colCount);
			 cell.setCellValue("Customer");
			 cell.setCellStyle(columnHeadingred);
			 colCount++;
			 cell = row.createCell(colCount);
			 cell.setCellValue("Vendor");
			 cell.setCellStyle(columnHeadingred);
			 colCount++;
			 cell = row.createCell(colCount);
			 cell.setCellValue("Cost Center");
			 cell.setCellStyle(columnHeadingred);
			 colCount++;
			 cell = row.createCell(colCount);
			 cell.setCellValue("Sloc (To)");
			 cell.setCellStyle(columnHeadingred);
			 colCount++;
			 cell = row.createCell(colCount);
			 cell.setCellValue("Batch2 (Dest.Batch)");
			 cell.setCellStyle(columnHeadingred);
			 colCount++;
			 cell = row.createCell(colCount);
			 cell.setCellValue("Posting Date");
			 cell.setCellStyle(columnHeadingLemon);
			 colCount++;
			 cell = row.createCell(colCount);
			 cell.setCellValue("Text");
			 //cell.setCellStyle(columnHeadingLemon);
			 colCount++;
			 cell = row.createCell(colCount);
			 cell.setCellValue("Stock Type");
			 cell.setCellStyle(columnHeadingred);
			 colCount++;
			 cell = row.createCell(colCount);
			 cell.setCellValue("QI Lot no.");
			 cell.setCellStyle(columnHeadingred);
			 colCount++;
			 cell = row.createCell(colCount);
			 cell.setCellValue("Inbound Delivery Number");
			 cell.setCellStyle(columnHeadingred);
			 colCount++;
			 cell = row.createCell(colCount);
			 cell.setCellValue("EXT MAT");
			 cell.setCellStyle(columnHeadingred);
			 colCount++;
			 cell = row.createCell(colCount);
			 cell.setCellValue("Vendor Batch");
			 cell.setCellStyle(columnHeadingred);
			 colCount++;

			 colCount = 0 ;
			 rowCount++;
			 System.out.println("result set 1 size ::::"+list.size());
			 for(Sap_Dispatch_Download s:list)
			 {
				 colCount=0;
				 row = sheet1.createRow(rowCount);
				 cell = row.createCell(colCount);
				 cell.setCellValue(s.getPlant());
				 colCount++;
				 
				 cell = row.createCell(colCount);
				 cell.setCellValue(s.getSloc_from());
				 colCount++;
				 
				 cell = row.createCell(colCount);
				 cell.setCellValue(s.getMaterial_no());
				 colCount++;
				 
				 cell = row.createCell(colCount);
				 cell.setCellValue(s.getBatch());
				 colCount++;
				 cell = row.createCell(colCount);
				 cell.setCellValue(s.getQuantity().doubleValue());
				 cell.setCellStyle(rightAlignWth3Decimal);
				 colCount++;
				 
				 cell = row.createCell(colCount);
				 cell.setCellValue(s.getMvt_type());
				 colCount++;
				 
				 cell = row.createCell(colCount);
				 cell.setCellValue(s.getReason_cd());
				 colCount++;
				 
				 cell = row.createCell(colCount);
				 cell.setCellValue(s.getCustomer());
				 colCount++;
				 
				 cell = row.createCell(colCount);
				 cell.setCellValue(s.getVendor());
				 colCount++;
				 
				 cell = row.createCell(colCount);
				 cell.setCellValue(s.getCostcenter());
				 colCount++;
				 
				 cell = row.createCell(colCount);
				 cell.setCellValue(s.getSloc_to());
				 colCount++;
				 
				 cell = row.createCell(colCount);
				 cell.setCellValue(s.getBatch2());
				 colCount++;

				 oldt=s.getPosting_date();
				 newdt=oldt.substring(0, 10);
				 cell = row.createCell(colCount);
				 cell.setCellValue(newdt);
				 colCount++;
				 
				 cell = row.createCell(colCount);
				 cell.setCellValue(s.getText());
				 colCount++;
				 
				 cell = row.createCell(colCount);
				 cell.setCellValue(s.getStock_type());
				 colCount++;
				 
				 cell = row.createCell(colCount);
				 cell.setCellValue(s.getQi_lot_no());
				 colCount++;
				 
				 cell = row.createCell(colCount);
				 cell.setCellValue(s.getInbound_deli_no());
				 colCount++;
				 
				 cell = row.createCell(colCount);
				 cell.setCellValue(s.getExt_map());
				 colCount++;
				 
				 cell = row.createCell(colCount);
				 cell.setCellValue(s.getVendor_batch());
				 colCount++;

				 colCount = 0 ;
				 rowCount++;

			 }

				/*
				 * boolean moreResults = csmt.getMoreResults(); if(moreResults){ rs =
				 * csmt.getResultSet(); }
				 */
			 colCount = 0 ;
			 rowCount=0;
			 row = sheet2.createRow(rowCount);
			 cell = row.createCell(colCount);
			 cell.setCellValue("Plant");
			 // cell.setCellStyle(columnHeadingLemon);
			 colCount++;
			 cell = row.createCell(colCount);
			 cell.setCellValue("Sloc (From)");
			 //cell.setCellStyle(columnHeadingLemon);
			 colCount++;
			 cell = row.createCell(colCount);
			 cell.setCellValue("Material No.");
			 // cell.setCellStyle(columnHeadingLemon);
			 colCount++;
			 cell = row.createCell(colCount);
			 cell.setCellValue("Batch");
			 // cell.setCellStyle(columnHeadingLemon);
			 colCount++;
			 cell = row.createCell(colCount);
			 cell.setCellValue("Quantity");
			 // cell.setCellStyle(columnHeadingLemon);
			 colCount++;
			 cell = row.createCell(colCount);
			 cell.setCellValue("Mvt. Type");
			 // cell.setCellStyle(columnHeadingLemon);
			 colCount++;
			 cell = row.createCell(colCount);
			 cell.setCellValue(" Reason Cd.");
			 //  cell.setCellStyle(columnHeadingLemon);
			 colCount++;
			 cell = row.createCell(colCount);
			 cell.setCellValue("Customer");
			 cell.setCellStyle(columnHeadingred);
			 colCount++;
			 cell = row.createCell(colCount);
			 cell.setCellValue("Vendor");
			 cell.setCellStyle(columnHeadingred);
			 colCount++;
			 cell = row.createCell(colCount);
			 cell.setCellValue("Cost Center");
			 cell.setCellStyle(columnHeadingred);
			 colCount++;
			 cell = row.createCell(colCount);
			 cell.setCellValue("Sloc (To)");
			 cell.setCellStyle(columnHeadingred);
			 colCount++;
			 cell = row.createCell(colCount);
			 cell.setCellValue("Batch2 (Dest.Batch)");
			 cell.setCellStyle(columnHeadingred);
			 colCount++;
			 cell = row.createCell(colCount);
			 cell.setCellValue("Posting Date");
			 cell.setCellStyle(columnHeadingLemon);
			 colCount++;
			 cell = row.createCell(colCount);
			 cell.setCellValue("Text");
			 //cell.setCellStyle(columnHeadingLemon);
			 colCount++;
			 cell = row.createCell(colCount);
			 cell.setCellValue("Stock Type");
			 cell.setCellStyle(columnHeadingred);
			 colCount++;
			 cell = row.createCell(colCount);
			 cell.setCellValue("QI Lot no.");
			 cell.setCellStyle(columnHeadingred);
			 colCount++;
			 cell = row.createCell(colCount);
			 cell.setCellValue("Inbound Delivery Number");
			 cell.setCellStyle(columnHeadingred);
			 colCount++;
			 cell = row.createCell(colCount);
			 cell.setCellValue("EXT MAT");
			 cell.setCellStyle(columnHeadingred);
			 colCount++;
			 cell = row.createCell(colCount);
			 cell.setCellValue("Vendor Batch");
			 cell.setCellStyle(columnHeadingred);
			 colCount++;

			 colCount = 0 ;
			 rowCount++;

		
			// System.out.println("result set 2 size ::::"+res2.size());


			 for(Sap_Dispatch_Download s:list)
			 {
				 colCount=0;
				 row = sheet2.createRow(rowCount);
				 cell = row.createCell(colCount);
				 cell.setCellValue(s.getPlant());
				 colCount++;
				 
				 cell = row.createCell(colCount);
				 cell.setCellValue(s.getSloc_from());
				 colCount++;
				 
				 cell = row.createCell(colCount);
				 cell.setCellValue(s.getMaterial_no());
				 colCount++;
				 
				 cell = row.createCell(colCount);
				 cell.setCellValue(s.getBatch());
				 colCount++;
				 
				 cell = row.createCell(colCount);
				 cell.setCellValue(s.getQuantity().doubleValue());
				 cell.setCellStyle(rightAlignWth3Decimal);
				 colCount++;
				 
				 cell = row.createCell(colCount);
				 cell.setCellValue(s.getMvt_type());
				 colCount++;
				 
				 cell = row.createCell(colCount);
				 cell.setCellValue(s.getReason_cd());
				 colCount++;
				 
				 cell = row.createCell(colCount);
				 cell.setCellValue(s.getCustomer());
				 colCount++;
				 
				 cell = row.createCell(colCount);
				 cell.setCellValue(s.getVendor());
				 colCount++;
				 
				 cell = row.createCell(colCount);
				 cell.setCellValue(s.getCostcenter());
				 colCount++;
				 
				 cell = row.createCell(colCount);
				 cell.setCellValue(s.getSloc_to());
				 colCount++;
				 
				 cell = row.createCell(colCount);
				 cell.setCellValue(s.getBatch2());
				 colCount++;

				 oldt=s.getPosting_date();
				 newdt=oldt.substring(0, 10);
				 cell = row.createCell(colCount);
				 cell.setCellValue(newdt);
				 colCount++;
				 
				 cell = row.createCell(colCount);
				 cell.setCellValue(s.getText());
				 colCount++;
				 
				 cell = row.createCell(colCount);
				 cell.setCellValue(s.getStock_type());
				 colCount++;
				 
				 cell = row.createCell(colCount);
				 cell.setCellValue(s.getQi_lot_no());
				 colCount++;
				 
				 cell = row.createCell(colCount);
				 cell.setCellValue(s.getInbound_deli_no());
				 colCount++;
				 
				 cell = row.createCell(colCount);
				 cell.setCellValue(s.getExt_map());
				 colCount++;
				 
				 cell = row.createCell(colCount);
				 cell.setCellValue(s.getVendor_batch());
				 colCount++;

				 colCount =0;
				 rowCount++;
			 }

			 FileOutputStream fileOut = new FileOutputStream(path.toString());
			 wb.write(fileOut);
			    fileOut.close();
			    wb.close();
			// setFileInputStream(new ByteArrayInputStream(out.toByteArray()));

		} catch (Exception e) {
			System.out.println("Error Occurred :" +e.getMessage());// uncomment asneeded --;
		}
		//stmt = conn.createStatement();
		
		/*
		 * System.out.println("after printing::::"+stdt);
		 * System.out.println("after printing::::"+enddt);
		 */
		       
		/*
		 * String qry = "UPDATE DH SET  UPLOAD_SAP='Y' from dispatch_detail dd"+
		 * " join dispatch_header dh on dh.dsp_id=dd.dspdtl_dsp_id "+
		 * "	join smpitem si on si.smp_prod_id = dd.dspdtl_prod_id "+
		 * "	join smpbatch sb on sb.batch_id=dd.dspdtl_batch_id "+
		 * "	join period pr on rtrim(pr.PERIOD_FIN_YEAR)+rtrim(pr.period_code) = rtrim(dh.DSP_FIN_YEAR)+rtrim(DH.DSP_PERIOD_CODE)"
		 * + "	where "+
		 * "	dsp_dt between convert(datetime,'"+stdt+"',103) and convert(datetime,'"
		 * +enddt+"',103) "+ "	 and dh.direct_to_pso_ind='Y' "+
		 * "	 and si.smp_prod_type='Sample' "+ "	 and dh.dsp_loc_id=1 "+
		 * "	 and dh.dsp_status='A' and dh.DSP_APPR_STATUS='A' "+
		 * "	 and dd.dspdtl_status='A'  and dh.UPLOAD_SAP='N' ";
		 * System.out.println(" Query:" + qry);
		 * 
		 * PreparedStatement ps1= conn.prepareStatement(qry); ps1.executeUpdate();
		 * System.out.println("record update successful"); conn.commit(); ps1.close();
		 */
		return fileName;
	
	}

}