package com.excel.service;

import java.io.File;
import java.io.FileOutputStream;
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

import com.excel.bean.AuditTrailBean;
import com.excel.bean.ReportBean;
import com.excel.model.SupplierMasterModel;
import com.excel.utility.ApachePoiExcelFormat;
import com.excel.utility.CodifyErrors;
import com.excel.utility.MedicoConstants;
import com.excel.utility.Utility;

@Service
public class SupplierMasterDownload_Report_ServiceImpl implements SupplierMasterDownload_Report_Service{
	@Autowired private UserMasterService usermasterservice;

	@Override
	public String GenerateSupplierMasterDowloadReport(List<SupplierMasterModel> list,AuditTrailBean bean,String username,String empId) throws Exception {
		Workbook wwbook = null;
		File ff = null;
		String filename = null;
		StringBuffer path = null;
		try {

			long l = new Date().getTime();
			filename = "supplierAuditTrail_" + l + ".xlsx";
			String filePath = MedicoConstants.REPORT_FILE_PATH;

			ff = new File(filePath);
			path = new StringBuffer(filePath).append("\\");
			path.append(filename);
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
			
			
			wwbook = new XSSFWorkbook();
			Sheet wsheet = wwbook.createSheet("SupplierMasterAuditTrail");

			ApachePoiExcelFormat xlsxExcelFormat = new ApachePoiExcelFormat();

			wsheet.createFreezePane(0, 3);

			CellStyle sheetHeading = xlsxExcelFormat.SheetHeading(wwbook);
			CellStyle columnHeading = xlsxExcelFormat.columnHeading(wwbook);
			CellStyle leftAlign = xlsxExcelFormat.dataLeftAlign(wwbook);
			
 			CellStyle columnHeadingBlue = xlsxExcelFormat.columnSubHeadingBlue(wwbook);
 			
			CellStyle leftAlignB = xlsxExcelFormat.dataBoldLeftAlign(wwbook);
			
			int col = 0;
			short row = 0;
			XSSFRow hrow = (XSSFRow) (XSSFRow) wsheet.createRow(row);
			XSSFCell cell = hrow.createCell(col);
			cell.setCellStyle(sheetHeading);
			cell.setCellValue("Supplier Master Download");
			wsheet.addMergedRegion(new CellRangeAddress(row, row, col, col + 21-5));

			row++;

			hrow = (XSSFRow) wsheet.createRow(row);
			cell = hrow.createCell(col);
			cell.setCellStyle(sheetHeading);
			cell.setCellValue("Report Dated : "
					+ Utility.convertSmpDatetoString(new Date()));
			col++;
			cell = hrow.createCell(col);
			cell.setCellStyle(sheetHeading);
			cell.setCellValue("Period From " +bean.getFrmdt()+" To " +bean.getTodt() );
			wsheet.addMergedRegion(new CellRangeAddress(row, row, col, col + 14));
			col=col+14;
			col++;
			cell = hrow.createCell(col);
			cell.setCellStyle(sheetHeading);
			String userName=username;
			cell.setCellValue("User Name :"+userName);
			
			
			col=0;
			
			row++;
			
			hrow = (XSSFRow) wsheet.createRow(row);

			/*cell = hrow.createCell(col);
			cell.setCellStyle(columnHeading);
			cell.setCellValue("SUP_ID");
			col++;

			cell = hrow.createCell(col);
			cell.setCellStyle(columnHeading);
			cell.setCellValue("SUP_TYPE");
			col++;*/

			cell = hrow.createCell(col);
			cell.setCellStyle(columnHeading);
			cell.setCellValue("SUPPLIER_TYPE");
			col++;

			cell = hrow.createCell(col);
			cell.setCellStyle(columnHeading);
			cell.setCellValue("SUPPLIER_NAME");
			col++;

			cell = hrow.createCell(col);
			cell.setCellStyle(columnHeading);
			cell.setCellValue("TIN_NO");
			col++;

			cell = hrow.createCell(col);
			cell.setCellStyle(columnHeading);
			cell.setCellValue("CST_NO");
			col++;

			cell = hrow.createCell(col);
			cell.setCellStyle(columnHeading);
			cell.setCellValue("TAN_NO");
			col++;

			cell = hrow.createCell(col);
			cell.setCellStyle(columnHeading);
			cell.setCellValue("PAN_NO");
			col++;

			cell = hrow.createCell(col);
			cell.setCellStyle(columnHeading);
			cell.setCellValue("MAP_CODE");
			col++;

		/*	cell = hrow.createCell(col);
			cell.setCellStyle(columnHeading);
			cell.setCellValue("INSERT_USER_ID");
			col++;*/

			cell = hrow.createCell(col);
			cell.setCellStyle(columnHeading);
			cell.setCellValue("INS_USER_NAME");
			col++;

			cell = hrow.createCell(col);
			cell.setCellStyle(columnHeading);
			cell.setCellValue("INSERT_DATE");
			col++;

			cell = hrow.createCell(col);
			cell.setCellStyle(columnHeading);
			cell.setCellValue("SUP_INS_IP_ADD");
			col++;
			
		/*	cell = hrow.createCell(col);
			cell.setCellStyle(columnHeading);
			cell.setCellValue("SUP_MOD_USR_ID");
			col++;*/

			cell = hrow.createCell(col);
			cell.setCellStyle(columnHeading);
			cell.setCellValue("MOD_USER_NAME");
			col++;

			cell = hrow.createCell(col);
			cell.setCellStyle(columnHeading);
			cell.setCellValue("MODIFY_DATE");
			col++;

			cell = hrow.createCell(col);
			cell.setCellStyle(columnHeading);
			cell.setCellValue("SUP_MOD_IP_ADD");
			col++;

			cell = hrow.createCell(col);
			cell.setCellStyle(columnHeading);
			cell.setCellValue("SUP_STATUS");
			col++;

			cell = hrow.createCell(col);
			cell.setCellStyle(columnHeading);
			cell.setCellValue("SUP_NAME_OLD");
			col++;
/*
			cell = hrow.createCell(col);
			cell.setCellStyle(columnHeading);
			cell.setCellValue("SUP_SUBCOMP_ID");
			col++;*/
			
			cell = hrow.createCell(col);
			cell.setCellStyle(columnHeading);
			cell.setCellValue("SUBCOMP_COMPANY");
			col++;

			cell = hrow.createCell(col);
			cell.setCellStyle(columnHeading);
			cell.setCellValue("CURR_STATUS");
			col++;
			String old_sup_type="";
			String new_sup_type="";
			for (SupplierMasterModel common : list) {
				new_sup_type=common.getSupplier_type();
				if(!old_sup_type.equalsIgnoreCase(new_sup_type)){
				row++;
				col=0;
					
				hrow = (XSSFRow) wsheet.createRow(row);

				cell = hrow.createCell(col);
				cell.setCellStyle(columnHeadingBlue);
				cell.setCellValue("Supplier Type : " +new_sup_type);
				col++;
				for(int i = 0;i<16;i++){
					cell = hrow.createCell(col);
					cell.setCellStyle(columnHeadingBlue);
					cell.setCellValue("");
					col++;
				}
				}
				old_sup_type=new_sup_type;
				
				
				
				row++;
				col = 0;
				hrow = (XSSFRow) wsheet.createRow(row);
				if(common.getCurr_status().equalsIgnoreCase("CURRENT")){
				/*cell = hrow.createCell(col);
				cell.setCellStyle(leftAlignB);
				cell.setCellValue((common.getSup_id() == null ? "0" : common.getSup_id().toString()));
				col++;
				
				cell = hrow.createCell(col);
				cell.setCellStyle(leftAlignB);
				cell.setCellValue(common.getSup_type());
				col++;*/
				
				cell = hrow.createCell(col);
				cell.setCellStyle(leftAlignB);
				cell.setCellValue(common.getSupplier_type());
				col++;
				
				cell = hrow.createCell(col);
				cell.setCellStyle(leftAlignB);
				cell.setCellValue(common.getSupplier_name());
				col++;
				}else{
					/*cell = hrow.createCell(col);
					cell.setCellStyle(leftAlign);
					cell.setCellValue((common.getSup_id() == null ? "0" : common.getSup_id().toString()));
					col++;
					
					cell = hrow.createCell(col);
					cell.setCellStyle(leftAlign);
					cell.setCellValue(common.getSup_type());
					col++;*/

					cell = hrow.createCell(col);
					cell.setCellStyle(leftAlign);
					cell.setCellValue(common.getSupplier_type());
					col++;

					cell = hrow.createCell(col);
					cell.setCellStyle(leftAlign);
					cell.setCellValue(common.getSupplier_name());
					col++;
				}
				cell = hrow.createCell(col);
				cell.setCellStyle(leftAlign);
				cell.setCellValue(common.getTin_no());
				col++;

				cell = hrow.createCell(col);
				cell.setCellStyle(leftAlign);
				cell.setCellValue(common.getCst_no());
				col++;

				cell = hrow.createCell(col);
				cell.setCellStyle(leftAlign);
				cell.setCellValue(common.getTan_no());
				col++;

				cell = hrow.createCell(col);
				cell.setCellStyle(leftAlign);
				cell.setCellValue(common.getPan_no());
				col++;

				cell = hrow.createCell(col);
				cell.setCellStyle(leftAlign);
				cell.setCellValue(common.getMap_code());
				col++;
				
			/*	cell = hrow.createCell(col);
				cell.setCellStyle(leftAlign);
				cell.setCellValue(common.getInsert_user_id());
				col++;*/
				
				cell = hrow.createCell(col);
				cell.setCellStyle(leftAlign);
				cell.setCellValue(common.getIns_user_name());
				col++;

				cell = hrow.createCell(col);
				cell.setCellStyle(leftAlign);
				cell.setCellValue(common.getInsert_date());
				col++;

				cell = hrow.createCell(col);
				cell.setCellStyle(leftAlign);
				cell.setCellValue(common.getSup_ins_ip_add());
				col++;
				
				/*cell = hrow.createCell(col);
				cell.setCellStyle(leftAlign);
				cell.setCellValue(common.getSup_mod_usr_id());
				col++;*/
				
				cell = hrow.createCell(col);
				cell.setCellStyle(leftAlign);
				cell.setCellValue(common.getMod_user_name());
				col++;

				cell = hrow.createCell(col);
				cell.setCellStyle(leftAlign);
				cell.setCellValue(common.getModify_date());
				col++;

				cell = hrow.createCell(col);
				cell.setCellStyle(leftAlign);
				cell.setCellValue(common.getSup_mod_ip_add());
				col++;

				cell = hrow.createCell(col);
				cell.setCellStyle(leftAlign);
				cell.setCellValue(common.getSup_status());
				col++;

				cell = hrow.createCell(col);
				cell.setCellStyle(leftAlign);
				cell.setCellValue(common.getSup_name_old());
				col++;
				
				/*cell = hrow.createCell(col);
				cell.setCellStyle(leftAlign);
				cell.setCellValue(common.getSup_subcomp_id());
				col++;*/
				
				cell = hrow.createCell(col);
				cell.setCellStyle(leftAlign);
				cell.setCellValue(common.getSubcomp_company());
				col++;

				cell = hrow.createCell(col);
				cell.setCellStyle(leftAlign);
				cell.setCellValue(common.getCurr_status());
				col++;
				

			}
			FileOutputStream fileOut = new FileOutputStream(path.toString());
			wwbook.write(fileOut);
			filename=usermasterservice.generateExcellock(path.toString(), filename,empId,".xlsx");

			fileOut.close();

			System.out.println("Excel Created " + filePath);

		} catch (Exception e) {
			System.out.println("Error Occurred :" +e.getMessage());// uncomment asneeded --;
		} finally {

		}
		return filename;
	}

}
