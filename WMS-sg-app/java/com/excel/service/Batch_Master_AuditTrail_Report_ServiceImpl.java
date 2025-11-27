package com.excel.service;

import java.io.File;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.streaming.SXSSFCell;
import org.apache.poi.xssf.streaming.SXSSFRow;
import org.apache.poi.xssf.streaming.SXSSFSheet;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.excel.model.BatchMasterAuditTrailModel;
import com.excel.model.Usermaster;
import com.excel.repository.UserMasterRepository;
import com.excel.security.PasswordManager;
import com.excel.utility.ApachePoiExcelFormat;
import com.excel.utility.CodifyErrors;
import com.excel.utility.MedicoConstants;
import com.excel.utility.Utility;

import net.lingala.zip4j.core.ZipFile;
import net.lingala.zip4j.model.ZipParameters;
import net.lingala.zip4j.util.Zip4jConstants;

@Service
public class Batch_Master_AuditTrail_Report_ServiceImpl implements Batch_Master_AuditTrail_Report_Service{
	@Autowired private UserMasterService usermasterservice;

	@Autowired UserMasterRepository usermasterrepository;
	
	@Override
	public String GenerateBatch_Master_Audit_Trail_Report(List<BatchMasterAuditTrailModel> list,String fromdate,String todate,
			String username,String empId) {
		String filename = null;
		StringBuffer path = null;
		SXSSFWorkbook wwbook = null;
		File ff=null;
		try {
			System.out.println("Excel Report");
			long l = new Date().getTime();
			filename = "batchAuditTrail_" + l+ ".xlsx";
			String filePath = MedicoConstants.REPORT_FILE_PATH ;
			
			path = new StringBuffer(filePath).append("\\"+filename );		//change

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

			ff = new File(filePath);
			wwbook = new SXSSFWorkbook(1);
			SXSSFSheet wsheet = wwbook.createSheet("BatchMasterAudittrail");
			
			wsheet.createFreezePane(0, 3);
			ApachePoiExcelFormat xlsxExcelFormat = new ApachePoiExcelFormat();
			CellStyle sheetHeading = xlsxExcelFormat.SheetHeading(wwbook);
			CellStyle columnHeading = xlsxExcelFormat.columnHeading(wwbook);
			CellStyle leftAlign = xlsxExcelFormat.dataLeftAlign(wwbook);
			CellStyle columnHeadingLemon = xlsxExcelFormat.columnSubHeadingLemon(wwbook);
			CellStyle leftAlignB = xlsxExcelFormat.dataBoldLeftAlign(wwbook);
		
			CellStyle columnHeadingBlue = xlsxExcelFormat.columnSubHeadingBlue(wwbook);
			CellStyle rightAlignWthDecimal = xlsxExcelFormat
					.dataRightAlignWthDecimal(wwbook);
			
			int col = 0;
			int row = 0;//change
			SXSSFRow hrow = wsheet.createRow(row);
			SXSSFCell cell = hrow.createCell(col);
			cell.setCellStyle(sheetHeading);
			cell.setCellValue("Batch Master Download");
			wsheet.addMergedRegion(new CellRangeAddress(row, row, col, col + 27));

			row++;

			hrow = wsheet.createRow(row);
			cell = hrow.createCell(col);
			cell.setCellStyle(sheetHeading);
			cell.setCellValue("Report Dated : "
					+ Utility.convertSmpDatetoString(new Date()));
			col++;
			cell = hrow.createCell(col);
			cell.setCellStyle(sheetHeading);
			cell.setCellValue("Period From " +fromdate+" To " +todate );
			wsheet.addMergedRegion(new CellRangeAddress(row, row, col, col + 25));
			col=col+25;
			col++;
			cell = hrow.createCell(col);
			cell.setCellStyle(sheetHeading);
			String userName=username;
			cell.setCellValue("User Name :"+userName);
			
			
			col=0;
			row++;
			
			
			hrow = wsheet.createRow(row);

		/*	cell = hrow.createCell(col);
			cell.setCellStyle(columnHeading);
			cell.setCellValue("BATCH_ID");
			col++;

			cell = hrow.createCell(col);
			cell.setCellStyle(columnHeading);
			cell.setCellValue("BATCH_PROD_ID");
			col++;*/

			cell = hrow.createCell(col);
			cell.setCellStyle(columnHeading);
			cell.setCellValue("PRODUCT");
			col++;

			/*cell = hrow.createCell(col);
			cell.setCellStyle(columnHeading);
			cell.setCellValue("BATCH_LOC_ID");
			col++;*/

			cell = hrow.createCell(col);
			cell.setCellStyle(columnHeading);
			cell.setCellValue("LOCATION");
			col++;

			cell = hrow.createCell(col);
			cell.setCellStyle(columnHeading);
			cell.setCellValue("BATCH_NO");
			col++;

			cell = hrow.createCell(col);
			cell.setCellStyle(columnHeading);
			cell.setCellValue("BATCH_MFG_DT");
			col++;

			cell = hrow.createCell(col);
			cell.setCellStyle(columnHeading);
			cell.setCellValue("BATCH_EXPIRY_DT");
			col++;

			cell = hrow.createCell(col);
			cell.setCellStyle(columnHeading);
			cell.setCellValue("BATCH_PHYSICAL_STOCK");
			col++;

			cell = hrow.createCell(col);
			cell.setCellStyle(columnHeading);
			cell.setCellValue("BATCH_Narration");
			col++;

			/*cell = hrow.createCell(col);
			cell.setCellStyle(columnHeading);
			cell.setCellValue("BATCH_MFG_LOC_ID");
			col++;*/

			cell = hrow.createCell(col);
			cell.setCellStyle(columnHeading);
			cell.setCellValue("MFG_LOCATION");
			col++;
			
			cell = hrow.createCell(col);
			cell.setCellStyle(columnHeading);
			cell.setCellValue("BATCH_RATE");
			col++;

			cell = hrow.createCell(col);
			cell.setCellStyle(columnHeading);
			cell.setCellValue("BATCH_COSTING_RATE");
			col++;

			cell = hrow.createCell(col);
			cell.setCellStyle(columnHeading);
			cell.setCellValue("BATCH_MKTG_RATE");
			col++;

			cell = hrow.createCell(col);
			cell.setCellStyle(columnHeading);
			cell.setCellValue("BATCH_NRV");
			col++;
			
			cell = hrow.createCell(col);
			cell.setCellStyle(columnHeading);
			cell.setCellValue("BATCH_DISPLAY_RATE");
			col++;
			

			cell = hrow.createCell(col);
			cell.setCellStyle(columnHeading);
			cell.setCellValue("BATCH_OPEN_STOCK");
			col++;

			cell = hrow.createCell(col);
			cell.setCellStyle(columnHeading);
			cell.setCellValue("BATCH_IN_STOCK");
			col++;
			
			cell = hrow.createCell(col);
			cell.setCellStyle(columnHeading);
			cell.setCellValue("BATCH_OUT_STOCK");
			col++;
			
			cell = hrow.createCell(col);
			cell.setCellStyle(columnHeading);
			cell.setCellValue("BATCH_EXCLUDE_LOC");
			col++;
			
			cell = hrow.createCell(col);
			cell.setCellStyle(columnHeading);
			cell.setCellValue("BATCH_EXCLUDE_PARTY");
			col++;
			
			cell = hrow.createCell(col);
			cell.setCellStyle(columnHeading);
			cell.setCellValue("BATCH_WITH_HELD_QTY");
			col++;
			
			cell = hrow.createCell(col);
			cell.setCellStyle(columnHeading);
			cell.setCellValue("BATCH_ERP_BATCH_CD");
			col++;
			
		/*	cell = hrow.createCell(col);
			cell.setCellStyle(columnHeading);
			cell.setCellValue("BATCH_ins_usr_id");
			col++;*/
			
			cell = hrow.createCell(col);
			cell.setCellStyle(columnHeading);
			cell.setCellValue("INS_USER_NAME");
			col++;
			
			cell = hrow.createCell(col);
			cell.setCellStyle(columnHeading);
			cell.setCellValue("BATCH_ins_dt");
			col++;
			
			cell = hrow.createCell(col);
			cell.setCellStyle(columnHeading);
			cell.setCellValue("BATCH_ins_ip_add");
			col++;
			
			/*cell = hrow.createCell(col);
			cell.setCellStyle(columnHeading);
			cell.setCellValue("BATCH_mod_usr_id");
			col++;*/
			
			cell = hrow.createCell(col);
			cell.setCellStyle(columnHeading);
			cell.setCellValue("MOD_USER_NAME");
			col++;
			
			cell = hrow.createCell(col);
			cell.setCellStyle(columnHeading);
			cell.setCellValue("BATCH_mod_dt");
			col++;
			
			cell = hrow.createCell(col);
			cell.setCellStyle(columnHeading);
			cell.setCellValue("BATCH_mod_ip_add");
			col++;
			
			cell = hrow.createCell(col);
			cell.setCellStyle(columnHeading);
			cell.setCellValue("BATCH_status");
			col++;
			
			cell = hrow.createCell(col);
			cell.setCellStyle(columnHeading);
			cell.setCellValue("CURR_STATUS");
			col++;
			
			
			String old_prod="";
			 String new_prod="";
			 
				
				String old_loc="";
				 String new_loc="";
				 
				for(BatchMasterAuditTrailModel common: list){
					
					
					
					new_prod=common.getProduct().trim();
					new_loc=common.getLocation().trim();
					if(!old_prod.equalsIgnoreCase(new_prod)){
						
						row++;
						col=0;
							
						hrow = wsheet.createRow(row);

						cell = hrow.createCell(col);
						cell.setCellStyle(columnHeadingBlue);
						cell.setCellValue("Product : " +new_prod);
						col++;
						for(int i = 0;i<27;i++){
							cell = hrow.createCell(col);
							cell.setCellStyle(columnHeadingBlue);
							cell.setCellValue("");
							col++;
						}
						
						row++;
						col=0;
							
					//	System.out.println("row ::"+row);
						hrow = wsheet.createRow(row);

						cell = hrow.createCell(col);
						cell.setCellStyle(columnHeadingLemon);
						cell.setCellValue("Location : " +new_loc);
						col++;
						for(int i = 0;i<27;i++){
							cell = hrow.createCell(col);
							cell.setCellStyle(columnHeadingLemon);
							cell.setCellValue("");
							col++;
						}
					}
					
			
					
					
					if(old_prod.equalsIgnoreCase(new_prod)){
					if(!old_loc.equalsIgnoreCase(new_loc)){
						
						row++;
						col=0;
							
						hrow = wsheet.createRow(row);

						cell = hrow.createCell(col);
						cell.setCellStyle(columnHeadingLemon);
						cell.setCellValue("Location : " +new_loc);
						col++;
						for(int i = 0;i<27;i++){
							cell = hrow.createCell(col);
							cell.setCellStyle(columnHeadingLemon);
							cell.setCellValue("");
							col++;
						}
					}
					}
					old_prod=new_prod;
					old_loc=new_loc;
				
					row++;
					col = 0;
					hrow =  wsheet.createRow(row);
					if(common.getCurr_status().equalsIgnoreCase("CURRENT")){
				 
				
				
			/*	cell = hrow.createCell(col);
				cell.setCellStyle(leftAlignB);
				cell.setCellValue((common.getBatch_id()== null ? " " : common.getBatch_id().toString()));
				col++;
				
				cell = hrow.createCell(col);
				cell.setCellStyle(leftAlignB);
				cell.setCellValue((common.getBatch_prod_id() == null ? " " : common.getBatch_prod_id().toString()));
				col++;*/
				
				cell = hrow.createCell(col);
				cell.setCellStyle(leftAlignB);
				cell.setCellValue((common.getProduct()));
				col++;
				
				/*cell = hrow.createCell(col);
				cell.setCellStyle(leftAlignB);
				cell.setCellValue((common.getBatch_loc_id() == null ? " " : common.getBatch_loc_id().toString()));
				col++;*/
				
				
					}else{
						
					/*
						cell = hrow.createCell(col);
						cell.setCellStyle(leftAlign);
						cell.setCellValue((common.getBatch_id() == null ? " " : common.getBatch_id().toString()));
						col++;
						
						cell = hrow.createCell(col);
						cell.setCellStyle(leftAlign);
						cell.setCellValue((common.getBatch_prod_id() == null ? " " : common.getBatch_prod_id().toString()));
						col++;*/
						
						cell = hrow.createCell(col);
						cell.setCellStyle(leftAlign);
						cell.setCellValue((common.getProduct()));
						col++;
						
						/*cell = hrow.createCell(col);
						cell.setCellStyle(leftAlign);
						cell.setCellValue((common.getBatch_loc_id() == null ? " " : common.getBatch_loc_id().toString()));
						col++;
						*/
						
						
							
						
					}
					
					cell = hrow.createCell(col);
					cell.setCellStyle(leftAlign);
					cell.setCellValue((common.getLocation()));
					col++;
					
					cell = hrow.createCell(col);
					cell.setCellStyle(leftAlign);
					cell.setCellValue((common.getBatch_no()));
					col++;
					
				
					cell = hrow.createCell(col);
					cell.setCellStyle(leftAlign);
					cell.setCellValue((common.getBatch_mfg_dt()));
					col++;
					
				
					cell = hrow.createCell(col);
					cell.setCellStyle(leftAlign);
					cell.setCellValue((common.getBatch_expiry_dt()));
					col++;
					
					cell = hrow.createCell(col);
					cell.setCellStyle(leftAlign);
					cell.setCellValue((common.getBatch_physical_stock()));
					col++;
					
					cell = hrow.createCell(col);
					cell.setCellStyle(leftAlign);
					cell.setCellValue((common.getBatch_narration()));
					col++;
					
				/*	cell = hrow.createCell(col);
					cell.setCellStyle(leftAlign);
					cell.setCellValue((common.getBatch_mfg_loc_id() == null ? " " : common.getBatch_mfg_loc_id().toString()));
					col++;*/
					
					cell = hrow.createCell(col);
					cell.setCellStyle(leftAlign);
					cell.setCellValue((common.getMfg_location()));
					col++;
					
					cell = hrow.createCell(col);
					cell.setCellStyle(leftAlign);
					cell.setCellValue((common.getBatch_rate() == null ? " " : common.getBatch_rate().toString()));
					col++;
					
					cell = hrow.createCell(col);
					cell.setCellStyle(leftAlign);
					cell.setCellValue((common.getBatch_costing_rate() == null ? " " : common.getBatch_costing_rate().toString()));
					col++;
					
					cell = hrow.createCell(col);
					cell.setCellStyle(rightAlignWthDecimal);
					cell.setCellValue((common.getBatch_mktg_rate() == null ? " " : common.getBatch_mktg_rate().toString()));
					col++;
					
					cell = hrow.createCell(col);
					cell.setCellStyle(rightAlignWthDecimal);
					cell.setCellValue((common.getBatch_nrv() == null ? " " : common.getBatch_nrv().toString()));
					col++;
					
					cell = hrow.createCell(col);
					cell.setCellStyle(rightAlignWthDecimal);
					cell.setCellValue((common.getBatch_display_rate() == null ? " " : common.getBatch_display_rate().toString()));
					col++;
					
					cell = hrow.createCell(col);
					cell.setCellStyle(rightAlignWthDecimal);
					cell.setCellValue((common.getBatch_open_stock() == null ? " " : common.getBatch_open_stock().toString()));
					col++;
					
					cell = hrow.createCell(col);
					cell.setCellStyle(rightAlignWthDecimal);
					cell.setCellValue((common.getBatch_in_stock() == null ? " " : common.getBatch_in_stock().toString()));
					col++;
					
					cell = hrow.createCell(col);
					cell.setCellStyle(rightAlignWthDecimal);
					cell.setCellValue((common.getBatch_out_stock() == null ? " " : common.getBatch_out_stock().toString()));
					col++;
					
					cell = hrow.createCell(col);
					cell.setCellStyle(leftAlign);
					cell.setCellValue((common.getBatch_exclude_loc() == null ? " " : common.getBatch_exclude_loc().toString()));
					col++;
					
					cell = hrow.createCell(col);
					cell.setCellStyle(leftAlign);
					cell.setCellValue((common.getBatch_exclude_party()));
					col++;
					
					cell = hrow.createCell(col);
					cell.setCellStyle(leftAlign);
					cell.setCellValue((common.getBatch_with_held_qty() == null ? " " : common.getBatch_with_held_qty().toString()));
					col++;
					
					cell = hrow.createCell(col);
					cell.setCellStyle(leftAlign);
					cell.setCellValue((common.getBatch_erp_batch_cd()));
					col++;
					
					/*cell = hrow.createCell(col);
					cell.setCellStyle(leftAlign);
					cell.setCellValue((common.getBatch_ins_usr_id() == null ? " " : common.getBatch_ins_usr_id().toString()));
					col++;
					*/
					cell = hrow.createCell(col);
					cell.setCellStyle(leftAlign);
					cell.setCellValue((common.getIns_user_name()));
					col++;
					
					cell = hrow.createCell(col);
					cell.setCellStyle(leftAlign);
					cell.setCellValue((common.getBatch_ins_dt()));
					col++;
					
					cell = hrow.createCell(col);
					cell.setCellStyle(leftAlign);
					cell.setCellValue((common.getBatch_ins_ip_add()));
					col++;
					
				/*	cell = hrow.createCell(col);
					cell.setCellStyle(leftAlign);
					cell.setCellValue((common.getBatch_mod_usr_id() == null ? " " : common.getBatch_mod_usr_id().toString()));
					col++;
					
					*/
					
					
					
					
					cell = hrow.createCell(col);
					cell.setCellStyle(leftAlign);
					cell.setCellValue((common.getMod_user_name()));
					col++;
					
					cell = hrow.createCell(col);
					cell.setCellStyle(leftAlign);
					cell.setCellValue((common.getBatch_mod_dt()));
					col++;
					
					cell = hrow.createCell(col);
					cell.setCellStyle(leftAlign);
					cell.setCellValue((common.getBatch_mod_ip_add()));
					col++;
					
					cell = hrow.createCell(col);
					cell.setCellStyle(leftAlign);
					cell.setCellValue((common.getBatch_status()));
					col++;
					
					cell = hrow.createCell(col);
					cell.setCellStyle(leftAlign);
					cell.setCellValue((common.getCurr_status()) );
					col++;
				
			}
			FileOutputStream fileOut = new FileOutputStream(path.toString());
			wwbook.write(fileOut);
			fileOut.close();

			System.out.println("Excel Created");
			
			filename=usermasterservice.generateExcellock(path.toString(), filename,empId,".xlsx");

                  
			} catch (Exception e) {
				System.out.println("Error Occurred :" +e.getMessage());// uncomment asneeded --;
		} finally {
			
		}
		return filename;
	}



}
