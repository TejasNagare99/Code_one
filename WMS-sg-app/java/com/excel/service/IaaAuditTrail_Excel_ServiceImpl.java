package com.excel.service;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.ss.usermodel.BorderStyle;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.FillPatternType;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.excel.model.Audit_log;
import com.excel.model.Usermaster;
import com.excel.model.ViewDownloadIaaAuditTrail;
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
public class IaaAuditTrail_Excel_ServiceImpl implements IaaAuditTrail_Excel_Service{
	SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
	DecimalFormat df = new DecimalFormat("####0.00");
	SimpleDateFormat parse = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	@Autowired private UserMasterService usermasterservice;

	@Autowired UserMasterRepository usermasterrepository;
	
	@Override
	public String generateIaaAuditExcel(List<ViewDownloadIaaAuditTrail> lst, String companyname, String usernname,
			String frm_date, String to_date,String empId) throws Exception {
		// TODO Auto-generated method stub
		Workbook wwbook = null;
		File ff = null;
		String filename = null;
		StringBuffer path = null;
		try {

			long l = new Date().getTime();
			filename = "IaaAuditTrail" + l+ ".xlsx";
			String filePath = MedicoConstants.REPORT_FILE_PATH;

			ff = new File(filePath);
			path = new StringBuffer(filePath).append("\\");
			path.append(filename );
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
			Sheet wsheet = wwbook.createSheet("IaaAuditTrail");

			ApachePoiExcelFormat xlsxExcelFormat = new ApachePoiExcelFormat();
			
			Font font = wwbook.createFont();
			font.setFontHeightInPoints((short) 11);
			font.setFontName("ARIAL");
			font.setColor(IndexedColors.BLACK.getIndex());
			font.setBold(true);
			

			wsheet.createFreezePane(0, 4);
			CellStyle sheetHeading = xlsxExcelFormat.SheetHeading(wwbook);
			CellStyle columnHeading =wwbook.createCellStyle();
			columnHeading.setFillForegroundColor(HSSFColor.HSSFColorPredefined.AQUA.getIndex());
			columnHeading.setFillPattern(FillPatternType.SOLID_FOREGROUND);
			columnHeading.setFont(font);
			columnHeading.setBorderBottom(BorderStyle.THIN);
			columnHeading.setBorderLeft(BorderStyle.THIN);
			columnHeading.setBorderTop(BorderStyle.THIN);
			columnHeading.setBorderRight(BorderStyle.THIN);
			columnHeading.setAlignment(HorizontalAlignment.CENTER);
			
			CellStyle sheetHeading2_Left = xlsxExcelFormat.SheetHeading(wwbook);
			sheetHeading2_Left.setAlignment(HorizontalAlignment.LEFT);
			
			CellStyle sheetHeading2_Right = xlsxExcelFormat.SheetHeading(wwbook);
			sheetHeading2_Right.setAlignment(HorizontalAlignment.RIGHT);
			
			CellStyle columnHeading2_left = wwbook.createCellStyle();
//			columnHeading.setFillForegroundColor(HSSFColor.HSSFColorPredefined.AQUA.getIndex());
//			columnHeading.setFillPattern(FillPatternType.SOLID_FOREGROUND);
			columnHeading2_left.setFont(font);
			columnHeading2_left.setBorderBottom(BorderStyle.THIN);
			columnHeading2_left.setBorderLeft(BorderStyle.THIN);
			columnHeading2_left.setBorderTop(BorderStyle.THIN);
			columnHeading2_left.setBorderRight(BorderStyle.THIN);
			columnHeading2_left.setAlignment(HorizontalAlignment.LEFT);
			
			
			CellStyle columnHeading2 = wwbook.createCellStyle();
//			columnHeading.setFillForegroundColor(HSSFColor.HSSFColorPredefined.AQUA.getIndex());
//			columnHeading.setFillPattern(FillPatternType.SOLID_FOREGROUND);
			columnHeading2.setFont(font);
			columnHeading2.setBorderBottom(BorderStyle.THIN);
			columnHeading2.setBorderLeft(BorderStyle.THIN);
			columnHeading2.setBorderTop(BorderStyle.THIN);
			columnHeading2.setBorderRight(BorderStyle.THIN);
			columnHeading2.setAlignment(HorizontalAlignment.CENTER);
			
			
			CellStyle NormalCellStyle_Right = wwbook.createCellStyle();
			NormalCellStyle_Right.setAlignment(HorizontalAlignment.RIGHT);
			NormalCellStyle_Right.setDataFormat(wwbook.createDataFormat().getFormat("0.00"));
			
			CellStyle NormalCellStyle_left = wwbook.createCellStyle();
			NormalCellStyle_left.setAlignment(HorizontalAlignment.LEFT);
			
			int col = 0;
			short row = 0;
			int index=0;
			XSSFRow hrow = (XSSFRow) (XSSFRow) wsheet.createRow(row);
			
			XSSFCell cell = hrow.createCell(col);
			cell.setCellStyle(sheetHeading);
			cell.setCellValue(companyname);
			wsheet.addMergedRegion(new CellRangeAddress(row, row, col, col + 17));
			row++;
			
			hrow = (XSSFRow) (XSSFRow) wsheet.createRow(row);
			cell = hrow.createCell(col);
			cell.setCellStyle(sheetHeading);
			cell.setCellValue("Stock Adjustment (Inventory Adjustment Advice) Audit Trail");
			wsheet.addMergedRegion(new CellRangeAddress(row, row, col, col + 17));
			row++;
			
			hrow = (XSSFRow) (XSSFRow) wsheet.createRow(row);
			cell = hrow.createCell(col);
			cell.setCellStyle(sheetHeading2_Left);
			cell.setCellValue("Status: A-Approved D-Discarded");
			wsheet.addMergedRegion(new CellRangeAddress(row, row, col, col = col + 4));
			col++;
			
			cell = hrow.createCell(col);
			cell.setCellStyle(sheetHeading);
			cell.setCellValue("Report From "+ frm_date+" To "+to_date);
			wsheet.addMergedRegion(new CellRangeAddress(row, row, col, col = col + 7));
			col++;
			
			cell = hrow.createCell(col);
			cell.setCellStyle(sheetHeading2_Right);
			cell.setCellValue("User:"+usernname+",Report Dated:"+Utility.convertSmpDatetoString(new Date()));
			wsheet.addMergedRegion(new CellRangeAddress(row, row, col, col = col + 4));
			col++;
			
			col =0 ;
			row++;
			
			hrow = (XSSFRow) (XSSFRow) wsheet.createRow(row);
			cell = hrow.createCell(col);
			cell.setCellStyle(columnHeading);
			cell.setCellValue("Date");
			col++;
			
			cell = hrow.createCell(col);
			cell.setCellStyle(columnHeading);
			cell.setCellValue("IAA Num");
			col++;
			
			for(int i=col;i<13;i++) {
				cell = hrow.createCell(i);
				cell.setCellStyle(columnHeading);
			}
			
			cell = hrow.createCell(col);
			cell.setCellStyle(columnHeading);
			cell.setCellValue("Remarks");
			wsheet.addMergedRegion(new CellRangeAddress(row, row, col, col = col + 10));
			col++;
			
			cell = hrow.createCell(col);
			cell.setCellStyle(columnHeading);
			cell.setCellValue("Status");
			col++;
			
			cell = hrow.createCell(col);
			cell.setCellStyle(columnHeading);
			cell.setCellValue("UserId");
			col++;
			
			cell = hrow.createCell(col);
			cell.setCellStyle(columnHeading);
			cell.setCellValue("UserName");
			col++;
			
			cell = hrow.createCell(col);
			cell.setCellStyle(columnHeading);
			cell.setCellValue("Date Time");
			col++;
			
			cell = hrow.createCell(col);
			cell.setCellStyle(columnHeading);
			cell.setCellValue("IP Used");
			col++;
			
			row++;
			col = 0;
			
			
			Long new_challan, old_challan=0L;
			
			for(ViewDownloadIaaAuditTrail challan:lst) {
				new_challan = challan.getSTKADJ_ID();
				
				
				if (old_challan.compareTo(new_challan)!=0) {
					index=0;	
					hrow = (XSSFRow) (XSSFRow) wsheet.createRow(row);
					cell = hrow.createCell(col);
					cell.setCellStyle(columnHeading2_left);
					cell.setCellValue(challan.getSTKADJ_DT());
					col++;
					
					cell = hrow.createCell(col);
					cell.setCellStyle(columnHeading2_left);
					cell.setCellValue(challan.getSTKADJ_NO());
					col++;
					
					for(int i=col;i<13;i++) {
						cell = hrow.createCell(i);
						cell.setCellStyle(columnHeading2_left);
					}
					
					cell = hrow.createCell(col);
					cell.setCellStyle(columnHeading2_left);
					cell.setCellValue(challan.getSTKADJ_REMARKS());
					wsheet.addMergedRegion(new CellRangeAddress(row, row, col, col = col + 10));
					col++;
					
					cell = hrow.createCell(col);
					cell.setCellStyle(columnHeading2_left);
					cell.setCellValue("Entered");
					col++;
					
					cell = hrow.createCell(col);
					cell.setCellStyle(columnHeading2_left);
					cell.setCellValue(challan.getSTKADJ_INS_USR_ID());
					col++;
					
					cell = hrow.createCell(col);
					cell.setCellStyle(columnHeading2_left);
					cell.setCellValue(challan.getINS_NAME()==null?" ":challan.getINS_NAME().length()>16 ? challan.getINS_NAME().substring(0, 15): challan.getINS_NAME());
					col++;
					
					cell = hrow.createCell(col);
					cell.setCellStyle(columnHeading2_left);
					cell.setCellValue(challan.getSTKADJ_INS_DT());
					col++;
					
				
					cell = hrow.createCell(col);
					cell.setCellStyle(columnHeading2_left);
					cell.setCellValue(challan.getSTKADJ_INS_IP_ADD());
					col++;
					
					row++;
					col = 0;
					
					
					hrow = (XSSFRow) (XSSFRow) wsheet.createRow(row);
					cell = hrow.createCell(col);
					cell.setCellValue("Sr.");
					cell.setCellStyle(columnHeading2);
					col++;
					
					cell = hrow.createCell(col);
					cell.setCellValue("Moved");
					cell.setCellStyle(columnHeading2);
					col++;
					
					cell = hrow.createCell(col);
					cell.setCellValue("Prod Code");
					cell.setCellStyle(columnHeading2);
					col++;
					
					for(int i=col;i<col+2;i++) {
						cell = hrow.createCell(i);
						cell.setCellStyle(columnHeading2);
					}
					
					cell = hrow.createCell(col);
					cell.setCellValue("Prod Description");
					cell.setCellStyle(columnHeading2);
					wsheet.addMergedRegion(new CellRangeAddress(row, row, col, col = col + 1));
					col++;
					
					cell = hrow.createCell(col);
					cell.setCellValue("Division");
					cell.setCellStyle(columnHeading2);
					col++;
					
					cell = hrow.createCell(col);
					cell.setCellValue("Pack");
					cell.setCellStyle(columnHeading2);
					col++;
					
					cell = hrow.createCell(col);
					cell.setCellValue("Batch");
					cell.setCellStyle(columnHeading2);
					col++;
					
					cell = hrow.createCell(col);
					cell.setCellValue("Expiry");
					cell.setCellStyle(columnHeading2);
					col++;
					
					cell = hrow.createCell(col);
					cell.setCellValue("Stock Type");
					cell.setCellStyle(columnHeading2);
					col++;
					
					cell = hrow.createCell(col);
					cell.setCellValue("Qty");
					cell.setCellStyle(columnHeading2);
					col++;
					
					cell = hrow.createCell(col);
					cell.setCellValue("Rate");
					cell.setCellStyle(columnHeading2);
					col++;
					
					cell = hrow.createCell(col);
					cell.setCellValue("Value");
					cell.setCellStyle(columnHeading2);
					col++;
					
					cell = hrow.createCell(col);
					cell.setCellValue("Activity");
					cell.setCellStyle(columnHeading2);
					col++;
					
					cell = hrow.createCell(col);
					cell.setCellValue("User Id");
					cell.setCellStyle(columnHeading2);
					col++;
					cell = hrow.createCell(col);
					cell.setCellValue("Username");
					cell.setCellStyle(columnHeading2);
					col++;
					cell = hrow.createCell(col);
					cell.setCellValue("Date Time");
					cell.setCellStyle(columnHeading2);
					col++;
					cell = hrow.createCell(col);
					cell.setCellValue("IP Used");
					cell.setCellStyle(columnHeading2);
					col++;
					
					row ++;
					col =0;
				}
				
				
				hrow = (XSSFRow) (XSSFRow) wsheet.createRow(row);
				
				if(challan.getIND().equalsIgnoreCase("1")){
					cell = hrow.createCell(col);
					//cell.setCellStyle(sheetHeading);
					cell.setCellValue((++index)+"");
					col++;
				}else {
					cell = hrow.createCell(col);
					//cell.setCellStyle(sheetHeading);
					cell.setCellValue(" ");
					col++;
				}
					
				
				cell = hrow.createCell(col);
				//cell.setCellStyle(sheetHeading);
				cell.setCellValue(challan.getMOVE_TYPE());
				col++;
				
				cell = hrow.createCell(col);
				//cell.setCellStyle(sheetHeading);
				cell.setCellValue(challan.getSMP_PROD_CD());
				col++;
				
								
				cell = hrow.createCell(col);
				//cell.setCellStyle(sheetHeading);
				cell.setCellValue(challan.getSMP_PROD_NAME().length()>60 ? challan.getSMP_PROD_NAME().substring(0, 59): challan.getSMP_PROD_NAME());
				wsheet.addMergedRegion(new CellRangeAddress(row, row, col, col = col + 1));
				col++;
				
				cell = hrow.createCell(col);
				//cell.setCellStyle(sheetHeading);
				cell.setCellValue(challan.getDIV_DISP_NM());
				col++;
				
				cell = hrow.createCell(col);
				//cell.setCellStyle(sheetHeading);
				cell.setCellValue(challan.getPACK_DISP_NM());
				col++;
				
				
				cell = hrow.createCell(col);
				//cell.setCellStyle(sheetHeading);
				cell.setCellValue(challan.getBATCH_NO());
				col++;
				
				
				cell = hrow.createCell(col);
				//cell.setCellStyle(sheetHeading);
				cell.setCellValue(challan.getBATCH_EXPIRY_DT());
				col++;
				
				
				
				cell = hrow.createCell(col);
				//cell.setCellStyle(sheetHeading);
				cell.setCellValue(challan.getSTOCK_TYPE_DESC().length()>30 ? challan.getSTOCK_TYPE_DESC().substring(0, 29): challan.getSTOCK_TYPE_DESC());
				col++;
				
				cell = hrow.createCell(col);
				cell.setCellStyle(NormalCellStyle_Right);
				cell.setCellValue(challan.getADJ_QTY()!=null ? challan.getADJ_QTY().doubleValue() : 0d);
				col++;
				
				cell = hrow.createCell(col);
				cell.setCellStyle(NormalCellStyle_Right);
				cell.setCellValue(challan.getRATE()!=null ? challan.getRATE().doubleValue() : 0d);
				col++;
				
				cell = hrow.createCell(col);
				cell.setCellStyle(NormalCellStyle_Right);
				cell.setCellValue(challan.getSTKADJ_VALUE()!=null ? challan.getSTKADJ_VALUE().doubleValue() : 0d);
				col++;
				
				cell = hrow.createCell(col);
				//cell.setCellStyle(sheetHeading);
				cell.setCellValue("Entered");
				col++;
				
				cell = hrow.createCell(col);
				//cell.setCellStyle(sheetHeading);
				cell.setCellValue(challan.getSTKADJ_INS_USR_ID());
				col++;
				
				cell = hrow.createCell(col);
				//cell.setCellStyle(sheetHeading);
				cell.setCellValue(challan.getINS_NAME()==null?" ":challan.getINS_NAME().length()>20 ? challan.getINS_NAME().substring(0, 19): challan.getINS_NAME());
				col++;
				
				cell = hrow.createCell(col);
				//cell.setCellStyle(sheetHeading);
				cell.setCellValue(challan.getSTKADJ_INS_DT());
				col++;
				
				cell = hrow.createCell(col);
				//cell.setCellStyle(sheetHeading);
				cell.setCellValue(challan.getSTKADJ_INS_IP_ADD());
				col++;
				
				old_challan=new_challan;
				row++;
				col=0;
			}
			FileOutputStream fileOut = new FileOutputStream(path.toString());
			wwbook.write(fileOut);
			fileOut.close();

			System.out.println("Excel Created");
			
			filename=usermasterservice.generateExcellock(path.toString(), filename,empId,".xlsx");

		}
		catch (Exception e) {
			// TODO: handle exception
			throw e;
		}
		
		
		return filename;
	}

	@Override
	public String generateAuditLogExcel(List<Audit_log> list, String companyname, String fromDate, String toDate)
			throws IOException {
		// TODO Auto-generated method stub
		Workbook wwbook = null;
		File ff = null;
		String filename = null;
		StringBuffer path = null;
		try {
			long l = new Date().getTime();
			filename = "AuditLog" + l + ".xlsx";
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
			Sheet wsheet = wwbook.createSheet("Audit Log");

			
			Date approved_date;
			Date issue_date;
			Date ref_date;
			Date doc_date;
			
			ApachePoiExcelFormat xlsxExcelFormat = new ApachePoiExcelFormat();
			
			//headings
			String headings[] = {"Doc. Type","Alloc. Type","Doc. No.","Doc. Date","Received From",
					"Issued To","Ref. No","Ref. Date","Item Div","Item Code","Item Name","Item Qty.",
					"Item Value","Issued By","Issued Date","Approved By","Approval Date"};
			
			wsheet.createFreezePane(0, 4);
			
			Font font = wwbook.createFont();
			font.setFontHeightInPoints((short) 11);
			font.setFontName("ARIAL");
			font.setColor(IndexedColors.BLACK.getIndex());
			font.setBold(true);
			CellStyle rightAlign = wwbook.createCellStyle();
			rightAlign.setAlignment(HorizontalAlignment.RIGHT);
			
			CellStyle sheetHeading = xlsxExcelFormat.SheetHeading(wwbook);
			CellStyle columnHeading =wwbook.createCellStyle();
			columnHeading.setFillForegroundColor(HSSFColor.HSSFColorPredefined.AQUA.getIndex());
			columnHeading.setFillPattern(FillPatternType.SOLID_FOREGROUND);
			columnHeading.setFont(font);
			columnHeading.setBorderBottom(BorderStyle.THIN);
			columnHeading.setBorderLeft(BorderStyle.THIN);
			columnHeading.setBorderTop(BorderStyle.THIN);
			columnHeading.setBorderRight(BorderStyle.THIN);
			columnHeading.setAlignment(HorizontalAlignment.CENTER);
			
			int col = 0;
			int row = 0;
			int index=0;
			XSSFRow hrow = (XSSFRow) (XSSFRow) wsheet.createRow(row);
			XSSFCell cell = hrow.createCell(col);
			cell.setCellStyle(sheetHeading);
			cell.setCellValue(companyname);
			wsheet.addMergedRegion(new CellRangeAddress(row, row, col, col + 17));
			row++;
			
			hrow = (XSSFRow) (XSSFRow) wsheet.createRow(row);
			cell = hrow.createCell(col);
			cell.setCellStyle(sheetHeading);
			cell.setCellValue("Audit Log Report");
			wsheet.addMergedRegion(new CellRangeAddress(row, row, col, col + 17));
			row++;
			
			hrow = (XSSFRow) (XSSFRow) wsheet.createRow(row);
			cell = hrow.createCell(col);
			cell.setCellStyle(sheetHeading);
			cell.setCellValue("Report From "+ fromDate+" To "+toDate);
			wsheet.addMergedRegion(new CellRangeAddress(row, row, col, col +17));
			row++;
			
			//add headers row
			hrow = (XSSFRow) (XSSFRow) wsheet.createRow(row);
			for(String head : headings) {
				cell = hrow.createCell(col);
				cell.setCellStyle(columnHeading);
				cell.setCellValue(head);
				col++;
			}
			
			
			//write data here
			for(Audit_log auditLogRowItem : list) {
				col = 0 ;
				row++;
				hrow = (XSSFRow) (XSSFRow) wsheet.createRow(row);
				
				cell = hrow.createCell(col);
				cell.setCellValue(auditLogRowItem.getDoc_type());
				col++;
				
				cell = hrow.createCell(col);
				cell.setCellValue(auditLogRowItem.getAlloc_type());
				col++;
				
				cell = hrow.createCell(col);
				cell.setCellValue(auditLogRowItem.getDoc_no());
				col++;
				
				cell = hrow.createCell(col);
				cell.setCellValue(auditLogRowItem.getDoc_date());
				cell.setCellStyle(rightAlign);
				col++;
				
				cell = hrow.createCell(col);
				cell.setCellValue(auditLogRowItem.getRecd_from());
				col++;
				
				cell = hrow.createCell(col);
				cell.setCellValue(auditLogRowItem.getIssued_to());
				col++;
				
				cell = hrow.createCell(col);
				cell.setCellValue(auditLogRowItem.getRef_no());
				col++;
				
				cell = hrow.createCell(col);
				String date = sdf.format(parse.parse(auditLogRowItem.getRef_date()));
				cell.setCellValue(date.trim().equals("01-01-1900") ? "":date);
				cell.setCellStyle(rightAlign);
				col++;
				
				cell = hrow.createCell(col);
				cell.setCellValue(auditLogRowItem.getItem_div());
				col++;
				
				cell = hrow.createCell(col);
				cell.setCellValue(auditLogRowItem.getSmp_prod_cd());
				col++;
				
				cell = hrow.createCell(col);
				cell.setCellValue(auditLogRowItem.getSmp_prod_name());
				col++;
				
				cell = hrow.createCell(col);
				cell.setCellValue(Long.valueOf(auditLogRowItem.getAlloc_qty().setScale(0, RoundingMode.DOWN).toString()));
				col++;
				
				cell = hrow.createCell(col);
				cell.setCellValue(Double.parseDouble(auditLogRowItem.getItem_value().setScale(2, RoundingMode.DOWN).toString()));
				col++;
				
				cell = hrow.createCell(col);
				cell.setCellValue(auditLogRowItem.getIssue_by());
				col++;
				
				cell = hrow.createCell(col);
				cell.setCellValue(auditLogRowItem.getIssue_date());
				cell.setCellStyle(rightAlign);
				col++;
				
				cell = hrow.createCell(col);
				cell.setCellValue(auditLogRowItem.getApproved_by());
				col++;
				
				cell = hrow.createCell(col);
				cell.setCellValue(auditLogRowItem.getApproved_date());
				cell.setCellStyle(rightAlign);
				col++;
				
			}
			FileOutputStream fileOut = new FileOutputStream(path.toString());
			wwbook.write(fileOut);
			fileOut.close();

			System.out.println("Excel Created");
			
		}
		catch(Exception e) {
			System.out.println("Error Occurred :" +e.getMessage());// uncomment asneeded --;
		}	
		finally {
			if (wwbook != null) { wwbook.close(); }
		}
		return filename;
	}

}
