package com.excel.service;

import java.io.File;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.excel.model.Productwise_batchwise_locationwise_stock;
import com.excel.model.Usermaster;
import com.excel.repository.UserMasterRepository;
import com.excel.security.PasswordManager;
import com.excel.utility.ApachePoiExcelFormat;
import com.excel.utility.CodifyErrors;
import com.excel.utility.MedicoConstants;

import net.lingala.zip4j.core.ZipFile;
import net.lingala.zip4j.model.ZipParameters;
import net.lingala.zip4j.util.Zip4jConstants;

@Service
public class Productwise_batchwise_locationwise_stock_Report_ServiceImpl implements Productwise_batchwise_locationwise_stock_Report_Service{
	@Autowired private UserMasterService usermasterservice;

	@Autowired private UserMasterRepository usermasterrepository;
	@Override
	public String generateProductwise_batchwise_locationwise_stock_Report(
			List<Productwise_batchwise_locationwise_stock> list,String companyname,String empId) throws Exception {
		
		File ff = null;
		Workbook wwbook = null;
		StringBuffer path = null;
		String filename = null;
		
		try {
			System.out.println("generateExcel ----------------------------------------------------------------------");
			long l = new Date().getTime();
			filename = "Product_Inventory_Search_Report" + l + ".xlsx";
			String filePath = MedicoConstants.REPORT_FILE_PATH;
			 path = new StringBuffer(filePath).append("\\");
			path.append(filename);
			
			
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
				
				
				wwbook= new XSSFWorkbook();
				Sheet wsheet = wwbook.createSheet("productwise_batchwise_locationwise Sheet");
				wsheet.createFreezePane( 0, 3);
				
				ApachePoiExcelFormat xlsxExcelFormat = new ApachePoiExcelFormat();
				CellStyle sheetHeading = xlsxExcelFormat.SheetHeading(wwbook);
				CellStyle columnHeading = xlsxExcelFormat.columnHeading(wwbook);
				CellStyle RightAlignB = xlsxExcelFormat.dataBoldRightAlign(wwbook);
				
				//Font font = wwbook.createFont();
				//font.setBold(true);
				
			//	CellStyle totalfont = (CellStyle) wwbook.createFont();
			//	totalfont.setFont(font);
			//	totalfont.setAlignment(HorizontalAlignment.LEFT);
				
				
				int col = 0, row = 0;
				
				XSSFRow hrow = (XSSFRow) wsheet.createRow(row);
				
				XSSFCell cell = hrow.createCell(col);
				
				cell.setCellStyle(sheetHeading);	
				cell.setCellValue(companyname);	
				wsheet.addMergedRegion(new CellRangeAddress(row, row, col,col+7));	
				row++;	
					
				hrow = (XSSFRow) wsheet.createRow(row);	
				cell = hrow.createCell(col);	
				cell.setCellStyle(sheetHeading);	
				cell.setCellValue("Product Inventory Search");	
				wsheet.addMergedRegion(new CellRangeAddress(row, row, col,col+7));	
				row++;	
				
				
				hrow = (XSSFRow) wsheet.createRow(row);	
				cell = hrow.createCell(col);
				cell.setCellStyle(sheetHeading);	
				wsheet.addMergedRegion(new CellRangeAddress(row, row, col,col+7));	
				cell.setCellValue("Inventory of Product : "+list.get(0).getSmp_prod_name());	
				
				
				
				String[] heading = {"Product Type","Product code","Product Description","Batch no","Expiry Date","Location","Stock","With held quantity"};
			
				row ++;
				hrow = (XSSFRow) wsheet.createRow(row);	
				for(String a:heading) {
					cell = hrow.createCell(col);
					cell.setCellValue(a);	
					cell.setCellStyle(columnHeading);	
					col ++;
				}
				row ++;
				col = 0;
				
				String oldprod = "";
				String newprod = "";
				
				String oldbatch = "";
				String newbatch = "";
				
				long stocktotal =0;
				long withheldtotal = 0; 
				for(Productwise_batchwise_locationwise_stock ps : list) {
					
					newprod = ps.getSmp_prod_name();
					newbatch = ps.getBatch_no();
		
					
					if(!newprod.equalsIgnoreCase(oldprod)) {
					hrow = (XSSFRow) wsheet.createRow(row);
					cell = hrow.createCell(col);
					cell.setCellValue(ps.getSmp_prod_type());	
					//cell.setCellStyle(columnHeading);	
					col ++;
					
					
					cell = hrow.createCell(col);
					cell.setCellValue(ps.getSmp_prod_cd());	
					//cell.setCellStyle(columnHeading);	
					col ++;
					
					cell = hrow.createCell(col);
					cell.setCellValue(ps.getSmp_prod_name());	
					//cell.setCellStyle(columnHeading);	
					col ++;
					}
					else {
						hrow = (XSSFRow) wsheet.createRow(row);
						cell = hrow.createCell(col);
						cell.setCellValue(" ");	
						//cell.setCellStyle(columnHeading);	
						col ++;
						
						
						cell = hrow.createCell(col);
						cell.setCellValue(" ");	
						//cell.setCellStyle(columnHeading);	
						col ++;
						
						cell = hrow.createCell(col);
						cell.setCellValue(" ");	
						//cell.setCellStyle(columnHeading);	
						col ++;
					}
					
					if(!newbatch.equalsIgnoreCase(oldbatch)) {
						
					cell = hrow.createCell(col);
					cell.setCellValue(ps.getBatch_no());	
					//cell.setCellStyle(columnHeading);	
					col ++;
					
					
					cell = hrow.createCell(col);
					cell.setCellValue(ps.getExpiry_date());	
					//cell.setCellStyle(columnHeading);	
					col ++;
					}
					else {
						cell = hrow.createCell(col);
						cell.setCellValue(" ");	
						//cell.setCellStyle(columnHeading);	
						col ++;
						
						
						cell = hrow.createCell(col);
						cell.setCellValue(" ");	
						//cell.setCellStyle(columnHeading);	
						col ++;
					}
					
					cell = hrow.createCell(col);
					cell.setCellValue(ps.getLoc_nm());	
					//cell.setCellStyle(columnHeading);	
					col ++;
					
					
					cell = hrow.createCell(col);
					cell.setCellValue(ps.getStock().longValue());					
					//cell.setCellStyle(columnHeading);	
					stocktotal = stocktotal + ps.getStock().longValue();
					col ++;
					
					
					cell = hrow.createCell(col);
					cell.setCellValue(ps.getWith_held_qty().longValue());	
					//cell.setCellStyle(columnHeading);	
					withheldtotal = withheldtotal+ps.getWith_held_qty().longValue();
					col ++;
					
					
					oldprod = newprod;
					oldbatch = newbatch;
					
					col = 0;
					row ++;
				}
				
				
				hrow = (XSSFRow) wsheet.createRow(row);
				cell = hrow.createCell(col);
				cell.setCellValue(" ");	
				cell.setCellStyle(RightAlignB);	
				col ++;
				
				
				cell = hrow.createCell(col);
				cell.setCellValue(" ");	
				cell.setCellStyle(RightAlignB);	
				col ++;
				
				cell = hrow.createCell(col);
				cell.setCellValue(" ");	
				cell.setCellStyle(RightAlignB);	
				col ++;
				
				cell = hrow.createCell(col);
				cell.setCellValue("Total");	
				cell.setCellStyle(RightAlignB);	
				col ++;
				
				cell = hrow.createCell(col);
				cell.setCellValue(" ");	
				cell.setCellStyle(RightAlignB);	
				col ++;
				
				cell = hrow.createCell(col);
				cell.setCellValue(" ");	
				cell.setCellStyle(RightAlignB);	
				col ++;

				
				cell = hrow.createCell(col);
				cell.setCellValue(stocktotal);
				cell.setCellStyle(RightAlignB);	
				col++;
				
				cell = hrow.createCell(col);
				cell.setCellValue(withheldtotal);
				cell.setCellStyle(RightAlignB);	
				
				
				
				FileOutputStream fileOut = new FileOutputStream(path.toString());
				wwbook.write(fileOut);
				filename=usermasterservice.generateExcellock(path.toString(), filename,empId,".xlsx");
				fileOut.close();

				System.out.println("Excel Created");
				
		}
		catch (Exception e) {
			System.out.println("Error Occurred :" +e.getMessage());// uncomment asneeded --;
		}
		
		return filename;
	}
	

	
}
