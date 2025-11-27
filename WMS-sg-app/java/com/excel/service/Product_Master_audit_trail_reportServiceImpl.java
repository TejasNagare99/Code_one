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
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.excel.model.ItemMasterAuditTrail;
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
public class Product_Master_audit_trail_reportServiceImpl implements Product_Master_audit_trail_reportService{

	@Autowired private UserMasterService usermasterservice;

@Autowired private UserMasterRepository usermasterrepository;

	@Override
	public String generate_product_Master_audit_trail_report(List<ItemMasterAuditTrail> list,String username,Long prod_type,String empId)
			throws Exception {

		
		Workbook wwbook =null;
		String filename = null;
		StringBuffer path;
		File ff=null;
		try {
			
			long l = new Date().getTime();
			filename = "itemAuditTrail_" + l + ".xlsx";
			String filePath =  MedicoConstants.REPORT_FILE_PATH;
				
			 path = new StringBuffer(filePath).append("\\");
			 path.append(filename );

				System.out.println("Path::"+path+" filepath::"+filePath);
				ff=new File(path.toString());
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
				
			//ff = new File(filePath);
			wwbook = new XSSFWorkbook();
			Sheet wsheet = wwbook.createSheet("ItemMasterAudittrail");
			
			wsheet.createFreezePane(0, 3);
			ApachePoiExcelFormat xlsxExcelFormat = new ApachePoiExcelFormat();
			CellStyle sheetHeading = xlsxExcelFormat.SheetHeading(wwbook);
			 
			CellStyle columnHeading = xlsxExcelFormat.columnHeading(wwbook);
			CellStyle rightAlign = xlsxExcelFormat.dataRightAlignWthDecimal(wwbook);
			CellStyle leftAlign = xlsxExcelFormat.dataLeftAlign(wwbook);
			CellStyle leftAlignB = xlsxExcelFormat.dataBoldLeftAlign(wwbook);
			CellStyle rightAlignWthDecimal = xlsxExcelFormat.dataRightAlignWthDecimal(wwbook);
			CellStyle columnHeadingLemon = xlsxExcelFormat.columnSubHeadingLemon(wwbook);
			CellStyle columnHeadingBlue = xlsxExcelFormat.columnSubHeadingBlue(wwbook);
			short col = 0, row = 0;
			XSSFRow hrow = null;
			XSSFCell cell =null;
			String userName=username;
			
			/*
			 * hrow = (XSSFRow) (XSSFRow) wsheet.createRow(row); cell =
			 * hrow.createCell(col); cell.setCellStyle(sheetHeading);
			 * cell.setCellValue("User Name :"+userName); wsheet.addMergedRegion(new
			 * CellRangeAddress(row, row, col, col + 66-16)); row++;
			 * 
			 * 
			 * hrow = (XSSFRow) wsheet.createRow(row); cell = hrow.createCell(col);
			 * cell.setCellStyle(sheetHeading); cell.setCellValue("Report Dated : "+
			 * Utility.convertDatetoString(new Date())); wsheet.addMergedRegion(new
			 * CellRangeAddress(row, row, col, col + 66-16)); row++;
			 */
			
			if(prod_type.equals(205l)) {//Sample
			hrow = (XSSFRow) (XSSFRow) wsheet.createRow(row);
			cell = hrow.createCell(col);
			cell.setCellStyle(sheetHeading);
			cell.setCellValue("User Name :"+userName);
			wsheet.addMergedRegion(new CellRangeAddress(row, row, col, col + 66-15));
			row++;
			
			
			hrow = (XSSFRow) wsheet.createRow(row);
			cell = hrow.createCell(col);
			cell.setCellStyle(sheetHeading);
			cell.setCellValue("Report Dated : "+ Utility.convertDatetoString(new Date()));
			wsheet.addMergedRegion(new CellRangeAddress(row, row, col, col + 66-15));
			row++;
			}
			
			if(prod_type.equals(204l)) {//Sample
				hrow = (XSSFRow) (XSSFRow) wsheet.createRow(row);
				cell = hrow.createCell(col);
				cell.setCellStyle(sheetHeading);
				cell.setCellValue("User Name :"+userName);
				wsheet.addMergedRegion(new CellRangeAddress(row, row, col, col + 66-14));
				row++;
				
				
				hrow = (XSSFRow) wsheet.createRow(row);
				cell = hrow.createCell(col);
				cell.setCellStyle(sheetHeading);
				cell.setCellValue("Report Dated : "+ Utility.convertDatetoString(new Date()));
				wsheet.addMergedRegion(new CellRangeAddress(row, row, col, col + 66-14));
				row++;
				}
			
			if(prod_type.equals(0l)) {//Sample
				hrow = (XSSFRow) (XSSFRow) wsheet.createRow(row);
				cell = hrow.createCell(col);
				cell.setCellStyle(sheetHeading);
				cell.setCellValue("User Name :"+userName);
				wsheet.addMergedRegion(new CellRangeAddress(row, row, col, col + 66-13));
				row++;
				
				
				hrow = (XSSFRow) wsheet.createRow(row);
				cell = hrow.createCell(col);
				cell.setCellStyle(sheetHeading);
				cell.setCellValue("Report Dated : "+ Utility.convertDatetoString(new Date()));
				wsheet.addMergedRegion(new CellRangeAddress(row, row, col, col + 66-13));
				row++;
				}
			
			hrow = (XSSFRow) wsheet.createRow(row);

			/*cell = hrow.createCell(col);
			cell.setCellStyle(columnHeading);
			cell.setCellValue("SMP_PROD_ID");
			col++;*/

			cell = hrow.createCell(col);
			cell.setCellStyle(columnHeading);
			cell.setCellValue("SMP_PROD_CD");
			col++;

			cell = hrow.createCell(col);
			cell.setCellStyle(columnHeading);
			cell.setCellValue("SMP_COMPANY");
			col++;

			cell = hrow.createCell(col);
			cell.setCellStyle(columnHeading);
			cell.setCellValue("SMP_PROD_NAME");
			col++;

		/*	cell = hrow.createCell(col);
			cell.setCellStyle(columnHeading);
			cell.setCellValue("SMP_PACK_ID");
			col++;*/
			
			cell = hrow.createCell(col);
			cell.setCellStyle(columnHeading);
			cell.setCellValue("PACK_DISP_NAME");
			col++;

			cell = hrow.createCell(col);
			cell.setCellStyle(columnHeading);
			cell.setCellValue("SMP_ALTER_NAME");
			col++;

		

			cell = hrow.createCell(col);
			cell.setCellStyle(columnHeading);
			cell.setCellValue("SMP_LAUNCH_DT_DISP");
			col++;

		
			cell = hrow.createCell(col);
			cell.setCellStyle(columnHeading);
			cell.setCellValue("SMP_DISCONT_DT_DISP");
			col++;

			/*cell = hrow.createCell(col);
			cell.setCellStyle(columnHeading);
			cell.setCellValue("SMP_INV_GRP_ID");
			col++;

			cell = hrow.createCell(col);
			cell.setCellStyle(columnHeading);
			cell.setCellValue("SMP_UOM_ID");
			col++;
*/
			cell = hrow.createCell(col);
			cell.setCellStyle(columnHeading);
			cell.setCellValue("UOM_DISP_NM");
			col++;

		/*	cell = hrow.createCell(col);
			cell.setCellStyle(columnHeading);
			cell.setCellValue("SMP_FORM_ID");
			col++;*/
			
			cell = hrow.createCell(col);
			cell.setCellStyle(columnHeading);
			cell.setCellValue("form");
			col++;

			cell = hrow.createCell(col);
			cell.setCellStyle(columnHeading);
			cell.setCellValue("SMP_SCH_IND");
			col++;

			cell = hrow.createCell(col);
			cell.setCellStyle(columnHeading);
			cell.setCellValue("SMP_DIV_RQ_IND");
			col++;

			cell = hrow.createCell(col);
			cell.setCellStyle(columnHeading);
			cell.setCellValue("SMP_EXPIRY_RQ_IND");
			col++;
			
			cell = hrow.createCell(col);
			cell.setCellStyle(columnHeading);
			cell.setCellValue("SMP_BATCH_RQ_IND");
			col++;
			
			

			cell = hrow.createCell(col);
			cell.setCellStyle(columnHeading);
			cell.setCellValue("SMP_ALLOC_MULTIPLES");
			col++;

			cell = hrow.createCell(col);
			cell.setCellStyle(columnHeading);
			cell.setCellValue("SMP_ABC_IND");
			col++;
			
			cell = hrow.createCell(col);
			cell.setCellStyle(columnHeading);
			cell.setCellValue("SMP_STOCK_DAYS");
			col++;
			
			cell = hrow.createCell(col);
			cell.setCellStyle(columnHeading);
			cell.setCellValue("SMP_PROD_TYPE");
			col++;
			
			cell = hrow.createCell(col);
			cell.setCellStyle(columnHeading);
			cell.setCellValue("SMP_SAMPLING_TYPE");
			col++;
			
			cell = hrow.createCell(col);
			cell.setCellStyle(columnHeading);
			cell.setCellValue("SMP_SHELF_LIFE");
			col++;
			
			/*cell = hrow.createCell(col);
			cell.setCellStyle(columnHeading);
			cell.setCellValue("SMP_MFG_LOC_ID");
			col++;
			*/
			cell = hrow.createCell(col);
			cell.setCellStyle(columnHeading);
			cell.setCellValue("SMP_SHORT_EXPIRY_DAYS");
			col++;

			cell = hrow.createCell(col);
			cell.setCellStyle(columnHeading);
			cell.setCellValue("SMP_PROD_BATCH_SIZE");
			col++;

			cell = hrow.createCell(col);
			cell.setCellStyle(columnHeading);
			cell.setCellValue("SMP_ERP_PROD_CD");
			col++;

			cell = hrow.createCell(col);
			cell.setCellStyle(columnHeading);
			cell.setCellValue("SMP_SA_PROD_GROUP");
			col++;
			
			cell = hrow.createCell(col);
			cell.setCellStyle(columnHeading);
			cell.setCellValue("SA_GROUP_NAME");
			col++;

			/*cell = hrow.createCell(col);
			cell.setCellStyle(columnHeading);
			cell.setCellValue("SMP_THP_GRP_ID");
			col++;
			
			cell = hrow.createCell(col);
			cell.setCellStyle(columnHeading);
			cell.setCellValue("SMP_THP_SGRP_ID");
			col++;
			
			cell = hrow.createCell(col);
			cell.setCellStyle(columnHeading);
			cell.setCellValue("SMP_MOLE_GRP_ID");
			col++;
			
			cell = hrow.createCell(col);
			cell.setCellStyle(columnHeading);
			cell.setCellValue("SMP_MOLE_SGRP_ID");
			col++;
			
			cell = hrow.createCell(col);
			cell.setCellStyle(columnHeading);
			cell.setCellValue("SMP_PMT_GRP_ID");
			col++;
			
			cell = hrow.createCell(col);
			cell.setCellStyle(columnHeading);
			cell.setCellValue("SMP_PMT_SGRP_ID");
			col++;*/
			
			cell = hrow.createCell(col);
			cell.setCellStyle(columnHeading);
			cell.setCellValue("SMP_QTY_SHIPPER");
			col++;
			
			cell = hrow.createCell(col);
			cell.setCellStyle(columnHeading);
			cell.setCellValue("SMP_QTY_BOX");
			col++;
			
			cell = hrow.createCell(col);
			cell.setCellStyle(columnHeading);
			cell.setCellValue("SMP_QTY_STRIP");
			col++;
			
			cell = hrow.createCell(col);
			cell.setCellStyle(columnHeading);
			cell.setCellValue("SMP_MAX_ALLOC_QTY");
			col++;
			
			cell = hrow.createCell(col);
			cell.setCellStyle(columnHeading);
			cell.setCellValue("SMP_MIN_ALLOC_QTY");
			col++;
			
			cell = hrow.createCell(col);
			cell.setCellStyle(columnHeading);
			cell.setCellValue("SMP_STD_ALLOC_QTY");
			col++;
			
			cell = hrow.createCell(col);
			cell.setCellStyle(columnHeading);
			cell.setCellValue("SMP_SHIPPER_VOL");
			col++;
			
			cell = hrow.createCell(col);
			cell.setCellStyle(columnHeading);
			cell.setCellValue("SMP_SHIPPER_NWT");
			col++;
			
			cell = hrow.createCell(col);
			cell.setCellStyle(columnHeading);
			cell.setCellValue("SMP_SHIPPER_GWT");
			col++;
			
			cell = hrow.createCell(col);
			cell.setCellStyle(columnHeading);
			cell.setCellValue("SMP_EXCLUDE_LOC");
			col++;
			
			cell = hrow.createCell(col);
			cell.setCellStyle(columnHeading);
			cell.setCellValue("SMP_EXCLUDE_PARTY");
			col++;
			
			cell = hrow.createCell(col);
			cell.setCellStyle(columnHeading);
			cell.setCellValue("SMP_SPEC_DIV_IND");
			col++;
			
			cell = hrow.createCell(col);
			cell.setCellStyle(columnHeading);
			cell.setCellValue("SMP_COG_RATE");
			col++;
			
			cell = hrow.createCell(col);
			cell.setCellStyle(columnHeading);
			cell.setCellValue("SMP_COG_EXE_RATE");
			col++;
			
			cell = hrow.createCell(col);
			cell.setCellStyle(columnHeading);
			cell.setCellValue("SMP_RATE");
			col++;
			
			cell = hrow.createCell(col);
			cell.setCellStyle(columnHeading);
			cell.setCellValue("SMP_COSTING_RATE");
			col++;
			
			cell = hrow.createCell(col);
			cell.setCellStyle(columnHeading);
			cell.setCellValue("SMP_MKTG_RATE");
			col++;
			
			cell = hrow.createCell(col);
			cell.setCellStyle(columnHeading);
			cell.setCellValue("SMP_NRV");
			col++;
			
			cell = hrow.createCell(col);
			cell.setCellStyle(columnHeading);
			cell.setCellValue("SMP_DISPLAY_RATE");
			col++;
			
		/*	cell = hrow.createCell(col);
			cell.setCellStyle(columnHeading);
			cell.setCellValue("SMP_STD_DIV_ID");
			col++;
			*/
			cell = hrow.createCell(col);
			cell.setCellStyle(columnHeading);
			cell.setCellValue("division");
			col++;
			
			cell = hrow.createCell(col);
			cell.setCellStyle(columnHeading);
			cell.setCellValue("SMP_status");
			col++;
			
		/*	cell = hrow.createCell(col);
			cell.setCellStyle(columnHeading);
			cell.setCellValue("SMP_SubComp_id");
			col++;*/
			
			
			cell = hrow.createCell(col);
			cell.setCellStyle(columnHeading);
			cell.setCellValue("SubComp_Nm");
			col++;
			
			
		/*	cell = hrow.createCell(col);
			cell.setCellStyle(columnHeading);
			cell.setCellValue("SMP_INS_USR_ID");
			col++;
			*/
			
			cell = hrow.createCell(col);
			cell.setCellStyle(columnHeading);
			cell.setCellValue("INS_USER_NAME");
			col++;
			
			
			cell = hrow.createCell(col);
			cell.setCellStyle(columnHeading);
			cell.setCellValue("SMP_INS_DT");
			col++;
			
		/*	
			cell = hrow.createCell(col);
			cell.setCellStyle(columnHeading);
			cell.setCellValue("SMP_MOD_USR_ID");
			col++;
			
			*/
			cell = hrow.createCell(col);
			cell.setCellStyle(columnHeading);
			cell.setCellValue("MOD_USER_NAME");
			col++;
			
			
			cell = hrow.createCell(col);
			cell.setCellStyle(columnHeading);
			cell.setCellValue("SMP_MOD_DT");
			col++;
			
			
			cell = hrow.createCell(col);
			cell.setCellStyle(columnHeading);
			cell.setCellValue("CURR_STATUS");
			col++;
			
			if(prod_type.equals(205l)) {//Sample
				cell = hrow.createCell(col);
				cell.setCellStyle(columnHeading);
				cell.setCellValue("FCode");
				col++;
			}
			
			if(prod_type.equals(204l)) {//PI
			cell = hrow.createCell(col);
			cell.setCellStyle(columnHeading);
			cell.setCellValue("GCMA No.");
			col++;
			
			cell = hrow.createCell(col);
			cell.setCellStyle(columnHeading);
			cell.setCellValue("GCMA Expiry Date");
			col++;
			}
			
			if(prod_type.equals(0l)) {//All
				cell = hrow.createCell(col);
				cell.setCellStyle(columnHeading);
				cell.setCellValue("F_Code");
				col++;
				
				cell = hrow.createCell(col);
				cell.setCellStyle(columnHeading);
				cell.setCellValue("GCMA_No");
				col++;
				
				cell = hrow.createCell(col);
				cell.setCellStyle(columnHeading);
				cell.setCellValue("GCMA_Expiry_Date");
				col++;
			}
			
			String old_prod="";
			 String new_prod="";
			 
				
				String old_div="";
				 String new_div="";

				for(ItemMasterAuditTrail common: list){
					
					new_prod=common.getSmp_prod_type();
					new_div=common.getDivision();
					if(!old_prod.equalsIgnoreCase(new_prod)){
						
						row++;
						col=0;
							
						hrow = (XSSFRow) wsheet.createRow(row);

						cell = hrow.createCell(col);
						cell.setCellStyle(columnHeadingBlue);
						cell.setCellValue("Product Type : " +new_prod);
						col++;
						/*
						 * for(int i = 0;i<27+23;i++){ cell = hrow.createCell(col);
						 * cell.setCellStyle(columnHeadingBlue); cell.setCellValue(""); col++; }
						 */
						
						if(prod_type.equals(205l)) {//Samples
							for(int i = 0;i<27+24;i++){
								cell = hrow.createCell(col);
								cell.setCellStyle(columnHeadingBlue);
								cell.setCellValue("");
								col++;
							}
							}
							
							if(prod_type.equals(204l)) {//PI
								for(int i = 0;i<27+25;i++){
									cell = hrow.createCell(col);
									cell.setCellStyle(columnHeadingBlue);
									cell.setCellValue("");
									col++;
								}
							}
							
							if(prod_type.equals(0l)) {//All
								for(int i = 0;i<27+26;i++){
									cell = hrow.createCell(col);
									cell.setCellStyle(columnHeadingBlue);
									cell.setCellValue("");
									col++;
								}
							}
						
						row++;
						col=0;
							
						hrow = (XSSFRow) wsheet.createRow(row);

						cell = hrow.createCell(col);
						cell.setCellStyle(columnHeadingLemon);
						cell.setCellValue("Division : " +new_div);
						col++;
						/*
						 * for(int i = 0;i<27+23;i++){ cell = hrow.createCell(col);
						 * cell.setCellStyle(columnHeadingLemon); cell.setCellValue(""); col++; }
						 */
						
						if(prod_type.equals(205l)) {//Samples
							for(int i = 0;i<27+24;i++){
								cell = hrow.createCell(col);
								cell.setCellStyle(columnHeadingLemon);
								cell.setCellValue("");
								col++;
							}
							}
							
							if(prod_type.equals(204l)) {//PI
								for(int i = 0;i<27+25;i++){
									cell = hrow.createCell(col);
									cell.setCellStyle(columnHeadingLemon);
									cell.setCellValue("");
									col++;
								}
							}
							
							if(prod_type.equals(0l)) {//All
								for(int i = 0;i<27+26;i++){
									cell = hrow.createCell(col);
									cell.setCellStyle(columnHeadingLemon);
									cell.setCellValue("");
									col++;
								}
							}
					}
					
			
					
					
					if(old_prod.equalsIgnoreCase(new_prod)){
					if(!old_div.equalsIgnoreCase(new_div)){
						
						row++;
						col=0;
							
						hrow = (XSSFRow) wsheet.createRow(row);

						cell = hrow.createCell(col);
						cell.setCellStyle(columnHeadingLemon);
						cell.setCellValue("Division : " +new_div);
						col++;
						
						/*
						 * for(int i = 0;i<27+23;i++){ cell = hrow.createCell(col);
						 * cell.setCellStyle(columnHeadingLemon); cell.setCellValue(""); col++; }
						 */
						
						if(prod_type.equals(205l)) {//Samples
						for(int i = 0;i<27+24;i++){
							cell = hrow.createCell(col);
							cell.setCellStyle(columnHeadingLemon);
							cell.setCellValue("");
							col++;
						}
						}
						
						if(prod_type.equals(204l)) {//PI
							for(int i = 0;i<27+25;i++){
								cell = hrow.createCell(col);
								cell.setCellStyle(columnHeadingLemon);
								cell.setCellValue("");
								col++;
							}
						}
						
						if(prod_type.equals(0l)) {//All
							for(int i = 0;i<27+26;i++){
								cell = hrow.createCell(col);
								cell.setCellStyle(columnHeadingLemon);
								cell.setCellValue("");
								col++;
							}
						}
						
						
					}
					}
					old_prod=new_prod;
					old_div=new_div;
				
					
					
					row++;
					col = 0;
					hrow = (XSSFRow) wsheet.createRow(row);
					if(common.getCurr_status().equalsIgnoreCase("CURRENT")){
				
				
				cell = hrow.createCell(col);
				cell.setCellStyle(leftAlignB);
				cell.setCellValue((common.getSmp_prod_cd() == null ? "0" : common.getSmp_prod_cd().toString()));
				col++;
				
				cell = hrow.createCell(col);
				cell.setCellStyle(leftAlignB);
				cell.setCellValue(common.getSmp_company());
				col++;
				
				cell = hrow.createCell(col);
				cell.setCellStyle(leftAlignB);
				cell.setCellValue(common.getSmp_prod_name());
				col++;
				
					}else{
						
					
						
						cell = hrow.createCell(col);
						cell.setCellStyle(leftAlign);
						cell.setCellValue((common.getSmp_prod_cd() == null ? "0" : common.getSmp_prod_cd().toString()));
						col++;
						
						cell = hrow.createCell(col);
						cell.setCellStyle(leftAlign);
						cell.setCellValue(common.getSmp_company());
						col++;
						
						cell = hrow.createCell(col);
						cell.setCellStyle(leftAlign);
						cell.setCellValue(common.getSmp_prod_name());
						col++;
							
						
					}
					
			
					cell = hrow.createCell(col);
					cell.setCellStyle(leftAlign);
					cell.setCellValue(common.getPack_disp_nm());
					col++;
					
					cell = hrow.createCell(col);
					cell.setCellStyle(leftAlign);
					cell.setCellValue(common.getSmp_alter_name());
					col++;
					
					cell = hrow.createCell(col);
					cell.setCellStyle(leftAlign);
					cell.setCellValue(common.getSmp_launch_dt_disp());
					col++;
				
					cell = hrow.createCell(col);
					cell.setCellStyle(leftAlign);
					cell.setCellValue(common.getSmp_discont_dt_disp());
					col++;
					
					/*cell = hrow.createCell(col);
					cell.setCellStyle(leftAlign);
					cell.setCellValue(common.getSmp_inv_grp_id());
					col++;
					
					cell = hrow.createCell(col);
					cell.setCellStyle(leftAlign);
					cell.setCellValue(common.getSmp_uom_id());
					col++;*/
					
					cell = hrow.createCell(col);
					cell.setCellStyle(leftAlign);
					cell.setCellValue(common.getUom_disp_nm());
					col++;
					
					/*cell = hrow.createCell(col);
					cell.setCellStyle(leftAlign);
					cell.setCellValue(common.getSmp_form_id());
					col++;*/
					
					cell = hrow.createCell(col);
					cell.setCellStyle(leftAlign);
					cell.setCellValue(common.getForm());
					col++;
					
					cell = hrow.createCell(col);
					cell.setCellStyle(leftAlign);
					cell.setCellValue(common.getSmp_sch_ind());
					col++;
					
					cell = hrow.createCell(col);
					cell.setCellStyle(leftAlign);
					cell.setCellValue(common.getSmp_div_rq_ind());
					col++;
					
					cell = hrow.createCell(col);
					cell.setCellStyle(leftAlign);
					cell.setCellValue(common.getSmp_expiry_rq_ind());
					col++;
					
					cell = hrow.createCell(col);
					cell.setCellStyle(leftAlign);
					cell.setCellValue(common.getSmp_batch_rq_ind());
					col++;
					
					cell = hrow.createCell(col);
					cell.setCellStyle(leftAlign);
					cell.setCellValue(common.getSmp_alloc_multiples());
					col++;
					
					cell = hrow.createCell(col);
					cell.setCellStyle(leftAlign);
					cell.setCellValue(common.getSmp_abc_ind());
					col++;
					
					cell = hrow.createCell(col);
					cell.setCellStyle(leftAlign);
					cell.setCellValue(common.getSmp_stock_days());
					col++;
					
					cell = hrow.createCell(col);
					cell.setCellStyle(leftAlign);
					cell.setCellValue(common.getSmp_prod_type());
					col++;
					
					cell = hrow.createCell(col);
					cell.setCellStyle(leftAlign);
					cell.setCellValue(common.getSmp_sampling_type());
					col++;
					
					cell = hrow.createCell(col);
					cell.setCellStyle(leftAlign);
					cell.setCellValue(common.getSmp_shelf_life());
					col++;
					
					/*cell = hrow.createCell(col);
					cell.setCellStyle(leftAlign);
					cell.setCellValue(common.getSmp_mfg_loc_id());
					col++;*/
					
					cell = hrow.createCell(col);
					cell.setCellStyle(leftAlign);
					cell.setCellValue(common.getSmp_short_expiry_days());
					col++;
					
					cell = hrow.createCell(col);
					cell.setCellStyle(leftAlign);
					cell.setCellValue(common.getSmp_prod_batch_size());
					col++;
					
					cell = hrow.createCell(col);
					cell.setCellStyle(leftAlign);
					cell.setCellValue(common.getSmp_erp_prod_cd());
					col++;
					
					cell = hrow.createCell(col);
					cell.setCellStyle(leftAlign);
					cell.setCellValue(common.getSmp_sa_prod_group());
					col++;
					
					cell = hrow.createCell(col);
					cell.setCellStyle(leftAlign);
					cell.setCellValue(common.getSa_group_name());
					col++;
					
					/*cell = hrow.createCell(col);
					cell.setCellStyle(leftAlign);
					cell.setCellValue(common.getSmp_thp_grp_id());
					col++;
					
					cell = hrow.createCell(col);
					cell.setCellStyle(leftAlign);
					cell.setCellValue(common.getSmp_thp_sgrp_id());
					col++;*/
					
					
					
					
					
					
					
					
					
					
					
					/*
					cell = hrow.createCell(col);
					cell.setCellStyle(leftAlign);
					cell.setCellValue(common.getSmp_mole_grp_id());
					col++;
					
					cell = hrow.createCell(col);
					cell.setCellStyle(leftAlign);
					cell.setCellValue(common.getSmp_mole_sgrp_id());
					col++;
					
					cell = hrow.createCell(col);
					cell.setCellStyle(leftAlign);
					cell.setCellValue(common.getSmp_pmt_grp_id());
					col++;
					
					cell = hrow.createCell(col);
					cell.setCellStyle(leftAlign);
					cell.setCellValue(common.getSmp_pmt_sgrp_id());
					col++;*/
					
					cell = hrow.createCell(col);
					cell.setCellStyle(rightAlign);
					cell.setCellValue(common.getSmp_qty_shipper());
					col++;
					
					cell = hrow.createCell(col);
					cell.setCellStyle(rightAlign);
					cell.setCellValue(common.getSmp_qty_box());
					col++;
					
					cell = hrow.createCell(col);
					cell.setCellStyle(rightAlign);
					cell.setCellValue(common.getSmp_qty_strip());
					col++;
					
					cell = hrow.createCell(col);
					cell.setCellStyle(rightAlign);
					cell.setCellValue(common.getSmp_max_alloc_qty().toString());
					col++;
					
					cell = hrow.createCell(col);
					cell.setCellStyle(rightAlign);
					cell.setCellValue(common.getSmp_min_alloc_qty().toString());
					col++;
					
					cell = hrow.createCell(col);
					cell.setCellStyle(rightAlign);
					cell.setCellValue(common.getSmp_std_alloc_qty().toString());
					col++;
					
					cell = hrow.createCell(col);
					cell.setCellStyle(rightAlign);
					cell.setCellValue(common.getSmp_shipper_vol());
					col++;
					
					
					
					
					
					
					cell = hrow.createCell(col);
					cell.setCellStyle(rightAlign);
					cell.setCellValue(common.getSmp_shipper_nwt());
					col++;
					
					cell = hrow.createCell(col);
					cell.setCellStyle(rightAlign);
					cell.setCellValue(common.getSmp_shipper_gwt());
					col++;
					
					cell = hrow.createCell(col);
					cell.setCellStyle(leftAlign);
					cell.setCellValue(common.getSmp_exclude_loc());
					col++;
					
					cell = hrow.createCell(col);
					cell.setCellStyle(leftAlign);
					cell.setCellValue(common.getSmp_exclude_party());
					col++;
					
					cell = hrow.createCell(col);
					cell.setCellStyle(leftAlign);
					cell.setCellValue(common.getSmp_spec_div_ind());
					col++;
					
					cell = hrow.createCell(col);
					cell.setCellStyle(rightAlignWthDecimal);
					cell.setCellValue(common.getSmp_cog_rate().toString());
					col++;
					
					cell = hrow.createCell(col);
					cell.setCellStyle(rightAlignWthDecimal);
					cell.setCellValue(common.getSmp_cog_exe_rate().toString());
					col++;
					
					cell = hrow.createCell(col);
					cell.setCellStyle(rightAlignWthDecimal);
					cell.setCellValue(common.getSmp_rate().toString());
					col++;
					
					cell = hrow.createCell(col);
					cell.setCellStyle(rightAlignWthDecimal);
					cell.setCellValue(common.getSmp_costing_rate().toString());
					col++;
					
					cell = hrow.createCell(col);
					cell.setCellStyle(rightAlignWthDecimal);
					cell.setCellValue(common.getSmp_mktg_rate().toString());
					col++;
					
					cell = hrow.createCell(col);
					cell.setCellStyle(rightAlignWthDecimal);
					cell.setCellValue(common.getSmp_nrv().toString());
					col++;
					
					
					cell = hrow.createCell(col);
					cell.setCellStyle(rightAlignWthDecimal);
					cell.setCellValue(common.getSmp_display_rate().toString());
					col++;
					
					/*cell = hrow.createCell(col);
					cell.setCellStyle(leftAlign);
					cell.setCellValue(common.getSmp_std_div_id());
					col++;*/
					
					cell = hrow.createCell(col);
					cell.setCellStyle(leftAlign);
					cell.setCellValue(common.getDivision());
					col++;
					
					cell = hrow.createCell(col);
					cell.setCellStyle(leftAlign);
					cell.setCellValue(common.getSmp_status());
					col++;
					
					/*cell = hrow.createCell(col);
					cell.setCellStyle(leftAlign);
					cell.setCellValue(common.getSmp_subcomp_id());
					col++;
					*/
					cell = hrow.createCell(col);
					cell.setCellStyle(leftAlign);
					cell.setCellValue(common.getSubcomp_nm());
					col++;
					
					/*cell = hrow.createCell(col);
					cell.setCellStyle(leftAlign);
					cell.setCellValue(common.getSmp_ins_usr_id());
					col++;*/
					
					cell = hrow.createCell(col);
					cell.setCellStyle(leftAlign);
					cell.setCellValue(common.getIns_user_name());
					col++;
					
					cell = hrow.createCell(col);
					cell.setCellStyle(leftAlign);
					cell.setCellValue(common.getSmp_ins_dt());
					col++;
					
				/*	cell = hrow.createCell(col);
					cell.setCellStyle(leftAlign);
					cell.setCellValue(common.getSmp_mod_usr_id());
					col++;*/
					
					
					
					
					cell = hrow.createCell(col);
					cell.setCellStyle(leftAlign);
					cell.setCellValue(common.getMod_user_name());
					col++;
					
					cell = hrow.createCell(col);
					cell.setCellStyle(leftAlign);
					cell.setCellValue(common.getSmp_mod_dt());
					col++;
					
					cell = hrow.createCell(col);
					cell.setCellStyle(leftAlign);
					cell.setCellValue(common.getCurr_status());
					col++;
					
					
					if(prod_type.equals(205l)) {//Sample
						cell = hrow.createCell(col);
						cell.setCellStyle(leftAlign);
						cell.setCellValue(common.getProd_fcode());
						col++;
					}
					
					if(prod_type.equals(204l)) {//PI
						
						cell = hrow.createCell(col);
						cell.setCellStyle(leftAlign);
						cell.setCellValue(common.getGcma_number()!=null?"":common.getGcma_number());
						col++;
						
						cell = hrow.createCell(col);
						cell.setCellStyle(leftAlign);
						cell.setCellValue(common.getGcma_expiry_dt()!=null?"":common.getGcma_expiry_dt());
						col++;
					}
					
					if(prod_type.equals(0l)) {//All
						cell = hrow.createCell(col);
						cell.setCellStyle(leftAlign);
						cell.setCellValue(common.getProd_fcode());
						col++;
						
						cell = hrow.createCell(col);
						cell.setCellStyle(leftAlign);
						cell.setCellValue(common.getGcma_number());
						col++;
						
						cell = hrow.createCell(col);
						cell.setCellStyle(leftAlign);
						cell.setCellValue(common.getGcma_expiry_dt()!=null?"":common.getGcma_expiry_dt());
						col++;
					}
				
			}	
			FileOutputStream fileOut = new FileOutputStream(path.toString());
			wwbook.write(fileOut);
			
			
			fileOut.close();

			filename=usermasterservice.generateExcellock(path.toString(), filename,empId,".xlsx");

                        
			System.out.println("Excel Created");
			
			
			} catch (Exception e) {
				System.out.println("Error Occurred :" +e.getMessage());// uncomment asneeded --;
		} finally {
			wwbook.close();
		}
		return filename;
	
	}
	
	

}
