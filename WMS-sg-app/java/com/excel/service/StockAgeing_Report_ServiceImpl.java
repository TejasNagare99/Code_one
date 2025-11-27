package com.excel.service;

import java.io.File;
import java.io.FileOutputStream;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
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

import com.excel.model.FinYear;
import com.excel.model.StockAgeingGrnReportView;
import com.excel.model.StockAgeingReportView;
import com.excel.model.Usermaster;
import com.excel.repository.UserMasterRepository;
import com.excel.security.PasswordManager;
import com.excel.utility.ApachePoiExcelFormat;
import com.excel.utility.CodifyErrors;
import com.excel.utility.MedicoConstants;
import com.excel.utility.MedicoResources;

import net.lingala.zip4j.core.ZipFile;
import net.lingala.zip4j.model.ZipParameters;
import net.lingala.zip4j.util.Zip4jConstants;

@Service
public class StockAgeing_Report_ServiceImpl implements StockAgeing_Report_Service{
	
	@Autowired private UserMasterService usermasterservice;

	@Autowired FinancialYearService financialyearservice;
	@Autowired UserMasterRepository usermasterrepository;

	@Override
	public String generate_stockAgeingExcel(List<StockAgeingReportView> stockAgeing, String to_date, Long slab1,
			Long slab2, Long slab3, Long slab4, Long slab5, Long slab6, Long slab7, Long slab8, Long slab9,
			String ageing_expiry_id, String near_expiry_id,String companyname) throws Exception {

		Workbook wwbook = null;
		File ff = null;
		String filename=null;
		try {

			System.out.println("generate Stock Ageing Excel ----------------------------------------------------------------------");

			
			String comp_name = companyname;
			String _hostPath = MedicoConstants.REPORT_FILE_PATH;
			System.out.println("_hostPath -- " + _hostPath);
			 filename = "AgeingReport" + new Date().getTime()+ ".xlsx";
		
			//System.out.println("xlsPath -- " + xlsPath);
			StringBuffer path = new StringBuffer(_hostPath).append("\\");
			path.append(filename);
			ff = new File(path.toString());
			wwbook = new XSSFWorkbook();
			File file = new File(_hostPath);
			try {
				if (!file.exists()) {
					if (file.mkdir()) {
					} else {
						throw new RuntimeException("Could not create directory");
					}
				}} catch (Exception e) {
					throw new RuntimeException();
				}

				int totalColumn = 0;
				if(ageing_expiry_id.equalsIgnoreCase("A")){
					totalColumn=13;
				}else{
					totalColumn=13-5;
				}
				Sheet wsheet = wwbook.createSheet("StockAgeingReport");
				wsheet.createFreezePane( 3, 3);
				
				ApachePoiExcelFormat xlsxExcelFormat = new ApachePoiExcelFormat();

				CellStyle sheetHeading = xlsxExcelFormat.SheetHeading(wwbook);
				CellStyle columnHeading = xlsxExcelFormat.columnHeading(wwbook);
				CellStyle rightAlignWthDecimal = xlsxExcelFormat
						.dataRightAlignWthDecimal(wwbook);
				CellStyle rightAlignNoDecimal = xlsxExcelFormat
						.dataRightAlignWthoutDecimal(wwbook);
				CellStyle leftAlign = xlsxExcelFormat.dataLeftAlign(wwbook);
				CellStyle columnSubHeading = xlsxExcelFormat
						.columnSubHeading(wwbook);
				CellStyle columnSubHeadingRight = xlsxExcelFormat
						.columnSubHeadingRightAlgn(wwbook);
				
				int col = 0, row = 0;
				XSSFRow hrow = (XSSFRow) wsheet.createRow(row);
				XSSFCell cell = hrow.createCell(col);
			    cell.setCellStyle(sheetHeading);
				cell.setCellValue(comp_name);
				wsheet.addMergedRegion(new CellRangeAddress(row, row, col,col+totalColumn));
				row++;
				
				hrow = (XSSFRow) wsheet.createRow(row);
				cell = hrow.createCell(col);
				cell.setCellStyle(sheetHeading);
				cell.setCellValue("Stock Ageing Report As on :  "
						+ to_date);
				wsheet.addMergedRegion(new CellRangeAddress(row, row, col, col + totalColumn));
				row++;
				
				hrow = (XSSFRow) wsheet.createRow(row);

				cell = hrow.createCell(col);
				cell.setCellStyle(columnHeading);
				cell.setCellValue("Item Code");
				col++;

				cell = hrow.createCell(col);
				cell.setCellStyle(columnHeading);
				cell.setCellValue("Item Name");
				col++;

				cell = hrow.createCell(col);
				cell.setCellStyle(columnHeading);
				cell.setCellValue("Batch No.");
				col++;

				cell = hrow.createCell(col);
				cell.setCellStyle(columnHeading);
				cell.setCellValue("Rate");
				col++;

				cell = hrow.createCell(col);
				cell.setCellStyle(columnHeading);
				cell.setCellValue("Mfg. Date");
				col++;

				cell = hrow.createCell(col);
				cell.setCellStyle(columnHeading);
				cell.setCellValue("Exp. Date");
				col++;

				cell = hrow.createCell(col);
				cell.setCellStyle(columnHeading);
				cell.setCellValue("No. of Days to Expiry");
				col++;
				if(ageing_expiry_id.equalsIgnoreCase("A")){
				cell = hrow.createCell(col);
				cell.setCellStyle(columnHeading);
				cell.setCellValue(slab1+"-"+slab2+"(Qty.)");
				col++;

				cell = hrow.createCell(col);
				cell.setCellStyle(columnHeading);
				cell.setCellValue(slab3+"-"+slab4+"(Qty.)");
				col++;

				cell = hrow.createCell(col);
				cell.setCellStyle(columnHeading);
				cell.setCellValue(slab5+"-"+slab6+"(Qty.)");
				col++;

				cell = hrow.createCell(col);
				cell.setCellStyle(columnHeading);
				cell.setCellValue(slab7+"-"+slab8+"(Qty.)");
				col++;
				
				cell = hrow.createCell(col);
				cell.setCellStyle(columnHeading);
				cell.setCellValue(">"+slab9+"(Qty.)");
				col++;
				}else{
					cell = hrow.createCell(col);
					cell.setCellStyle(columnHeading);
					cell.setCellValue("Within "+near_expiry_id+" days of Exp Stk Qty");
					col++;
				}
				if(ageing_expiry_id.equalsIgnoreCase("A")){
				cell = hrow.createCell(col);
				cell.setCellStyle(columnHeading);
				cell.setCellValue("Closing Stock Qty.");
				col++;
				cell = hrow.createCell(col);
				cell.setCellStyle(columnHeading);
				cell.setCellValue("Closing Stock value");
				col++;
				
				}else{
					cell = hrow.createCell(col);
					cell.setCellStyle(columnHeading);
					cell.setCellValue(" Stock value");
					col++;
					
					
				}
				
				
				
				String newDiv = "";
				String oldDiv = "";
				Boolean flag = false;
				BigDecimal cl_stk_val_tot = BigDecimal.ZERO;
				BigDecimal cl_stk_val_grndTot = BigDecimal.ZERO;
				
				for(StockAgeingReportView lst :stockAgeing) {
					
					newDiv=lst.getDiv_disp_nm();
					if (!(newDiv.equalsIgnoreCase(oldDiv))) {
						if(flag)
						{
							row++;
							col = 0;
							hrow = (XSSFRow) wsheet.createRow(row);
							
							cell = hrow.createCell(col);
							cell.setCellStyle(columnSubHeading);
							cell.setCellValue("Total : " );
							
							for (int i = 1; i <= (totalColumn-1); i++) {
								cell = hrow.createCell(i);
								cell.setCellStyle(columnSubHeading);
								cell.setCellValue("");
							}
							
							cell = hrow.createCell(totalColumn);
							cell.setCellStyle(columnSubHeadingRight);
							cell.setCellValue(cl_stk_val_tot.doubleValue());
							
							col=0;
						}
						cl_stk_val_tot = BigDecimal.ZERO;
						
						row++;
						col = 0;
						hrow = (XSSFRow) wsheet.createRow(row);

						cell = hrow.createCell(col);
						cell.setCellStyle(columnSubHeading);
						cell.setCellValue("Division :");
						col++;

						cell = hrow.createCell(col);
						cell.setCellStyle(columnSubHeading);
						cell.setCellValue(newDiv);

						for (int i = 2; i <= totalColumn; i++) {
							cell = hrow.createCell(i);
							cell.setCellStyle(columnSubHeading);
							cell.setCellValue("");
						}
					}
					oldDiv = newDiv;
					flag=true;
					
					row++;
					col=0;
					hrow = (XSSFRow) wsheet.createRow(row);
					
					cell = hrow.createCell(col);
					cell.setCellStyle(leftAlign);
					cell.setCellValue(lst.getSmp_prod_cd());
					col++;
					
					cell = hrow.createCell(col);
					cell.setCellStyle(leftAlign);
					cell.setCellValue(lst.getSmp_prod_name());
					col++;
					
					cell = hrow.createCell(col);
					cell.setCellStyle(leftAlign);
					cell.setCellValue(lst.getBatch_no());
					col++;
					
					cell = hrow.createCell(col);
					cell.setCellStyle(rightAlignWthDecimal);
					cell.setCellValue((lst.getBatch_rate() == null ? BigDecimal.ZERO
							: lst.getBatch_rate()).doubleValue());
					col++;
					
					cell = hrow.createCell(col);
					cell.setCellStyle(leftAlign);
					cell.setCellValue(lst.getBatch_mfg_dt());
					col++;
					
					cell = hrow.createCell(col);
					cell.setCellStyle(leftAlign);
					cell.setCellValue(lst.getBatch_expiry_dt());
					col++;
					
					cell = hrow.createCell(col);
					cell.setCellStyle(rightAlignNoDecimal);
					cell.setCellValue(lst.getNo_of_expiry_days()!=null?lst.getNo_of_expiry_days():999L);
					col++;
					if(ageing_expiry_id.equalsIgnoreCase("A")){
					cell = hrow.createCell(col);
					cell.setCellStyle(rightAlignWthDecimal);
					cell.setCellValue((lst.getSlab1() == null ? BigDecimal.ZERO
							: lst.getSlab1()).doubleValue());
					col++;
					
					cell = hrow.createCell(col);
					cell.setCellStyle(rightAlignWthDecimal);
					cell.setCellValue((lst.getSlab2() == null ? BigDecimal.ZERO
							: lst.getSlab2()).doubleValue());
					col++;
					
					cell = hrow.createCell(col);
					cell.setCellStyle(rightAlignWthDecimal);
					cell.setCellValue((lst.getSlab3() == null ? BigDecimal.ZERO
							: lst.getSlab3()).doubleValue());
					col++;
					
					cell = hrow.createCell(col);
					cell.setCellStyle(rightAlignWthDecimal);
					cell.setCellValue((lst.getSlab4() == null ? BigDecimal.ZERO
							: lst.getSlab4()).doubleValue());
					col++;
					
					cell = hrow.createCell(col);
					cell.setCellStyle(rightAlignWthDecimal);
					cell.setCellValue((lst.getSlab5() == null ? BigDecimal.ZERO
							: lst.getSlab5()).doubleValue());
					col++;
					}else{
						cell = hrow.createCell(col);
						cell.setCellStyle(rightAlignWthDecimal);
						cell.setCellValue((lst.getSlab1() == null ? BigDecimal.ZERO
								: lst.getSlab1()).doubleValue());
						col++;
					}
					if(ageing_expiry_id.equalsIgnoreCase("A")){
					cell = hrow.createCell(col);
					cell.setCellStyle(rightAlignWthDecimal);
					cell.setCellValue((lst.getClosing_stock() == null ? BigDecimal.ZERO
							: lst.getClosing_stock()).doubleValue());
					col++;
					}
					cell = hrow.createCell(col);
					cell.setCellStyle(rightAlignWthDecimal);
					cell.setCellValue((lst.getClosing_val() == null ? BigDecimal.ZERO
							: lst.getClosing_val()).doubleValue());
					cl_stk_val_tot = cl_stk_val_tot.add(lst.getClosing_val() == null ? BigDecimal.ZERO
							: lst.getClosing_val());
					cl_stk_val_grndTot = cl_stk_val_grndTot.add(lst.getClosing_val() == null ? BigDecimal.ZERO
							: lst.getClosing_val());
					col++;
				}
				
				row++;
				col = 0;
				hrow = (XSSFRow) wsheet.createRow(row);
				
				cell = hrow.createCell(col);
				cell.setCellStyle(columnSubHeading);
				cell.setCellValue("Total : " );
				
				for (int i = 1; i <= (totalColumn-1); i++) {
					cell = hrow.createCell(i);
					cell.setCellStyle(columnSubHeading);
					cell.setCellValue("");
				}
				
				cell = hrow.createCell(totalColumn);
				cell.setCellStyle(columnSubHeadingRight);
				cell.setCellValue(cl_stk_val_tot.doubleValue());
				
				row++;
				col = 0;
				hrow = (XSSFRow) wsheet.createRow(row);
				
				cell = hrow.createCell(col);
				cell.setCellStyle(columnSubHeading);
				cell.setCellValue("Report Total : " );
				
				for (int i = 1; i <= (totalColumn-1); i++) {
					cell = hrow.createCell(i);
					cell.setCellStyle(columnSubHeading);
					cell.setCellValue("");
				}
				
				cell = hrow.createCell(totalColumn);
				cell.setCellStyle(columnSubHeadingRight);
				cell.setCellValue(cl_stk_val_grndTot.doubleValue());
				
				FileOutputStream fileOut = new FileOutputStream(path.toString());
				 wwbook.write(fileOut);
				 fileOut.close();
			
			
				System.out.println("Excel Created");

		} catch (Exception e) {
			System.out.println("Error Occurred :" +e.getMessage());// uncomment asneeded --;
		}finally{
			/*((FileInputStream) wwbook).close();*/
		//	fileInputStream.close();
		}
		
		
		return filename;
	}

	@Override
	public String generate_stockAgeingGrnExcel(List<StockAgeingGrnReportView> stockAgeing, String to_date, Long slab1,
			Long slab2, Long slab3, Long slab4, Long slab5, Long slab6, Long slab7, Long slab8, Long slab9, Long slab10,
			Long slab11, String companyname,String compcode,String reportType,String locName,String empId) throws Exception {
		
		Workbook wwbook = null;
		File ff = null;
		String filename=null;
		try {
			System.out.println("generate Stock Grn Ageing Excel ----------------------------------------------------------------------");

			
			String comp_name = companyname;
			String _hostPath = MedicoConstants.REPORT_FILE_PATH;
			System.out.println("_hostPath -- " + _hostPath);
			if(reportType.equals("G")) {
				filename = "GrnAgeingReport" + new Date().getTime()+ ".xlsx";
			}
			else {
				 filename = "DispatchAgeingReport" + new Date().getTime()+ ".xlsx";
			}
				StringBuffer path = new StringBuffer(_hostPath).append("\\");
				path.append(filename);
				ff = new File(path.toString());
				wwbook = new XSSFWorkbook();
				File file = new File(_hostPath);
				try {
					if (!file.exists()) {
						if (file.mkdir()) {
						} else {
							throw new RuntimeException("Could not create directory");
						}
					}} catch (Exception e) {
						throw new RuntimeException();
					}

			FinYear finyr = financialyearservice.getCurrentFinancialYear(compcode);
			wwbook = new XSSFWorkbook();
			
			Sheet wsheet = wwbook.createSheet("StockAgeGrnDspDate");
			wsheet.createFreezePane(2, 5);
			
			ApachePoiExcelFormat xlsxExcelFormat = new ApachePoiExcelFormat();
		
			CellStyle sheetHeading =xlsxExcelFormat.SheetHeading(wwbook);
			CellStyle columnHeading = xlsxExcelFormat.columnHeading(wwbook);
			CellStyle columnSubHeading =xlsxExcelFormat.columnSubHeading(wwbook);
			CellStyle rightAlignWthDecimal = xlsxExcelFormat.dataRightAlignWthDecimal(wwbook);
			CellStyle dataRightAlign = xlsxExcelFormat.dataRightAlign(wwbook);
			CellStyle leftAlign = xlsxExcelFormat.dataLeftAlign(wwbook);
			CellStyle columnHeadingNumeric = xlsxExcelFormat.columnDecimalHeading(wwbook);
			//CellStyle dataBoldLeftAlign = xlsxExcelFormat.dataBoldLeftAlign(wwbook);
			//CellStyle dataBoldRightAlign = xlsxExcelFormat.dataBoldRightAlignWthDecimal(wwbook);
			//Boolean show_collection = bean.getSale_ind().equalsIgnoreCase("Y") ?  true :false;
			
			short totalCol = 14;  //total-1
			short col = 0, row = 0;
			XSSFRow hrow = (XSSFRow) (XSSFRow) wsheet.createRow(row);
			XSSFCell cell = hrow.createCell(col);
			cell.setCellStyle(sheetHeading);
			cell.setCellValue(companyname);
			wsheet.addMergedRegion(new CellRangeAddress(row, row, col, col + totalCol));
			row++;
			
			hrow = (XSSFRow) wsheet.createRow(row);
			cell = hrow.createCell(col);
			cell.setCellStyle(sheetHeading);
			cell.setCellValue(locName);
			wsheet.addMergedRegion(new CellRangeAddress(row, row, col, col + totalCol));
			row++;

			hrow = (XSSFRow) wsheet.createRow(row);
			cell = hrow.createCell(col);
			cell.setCellStyle(sheetHeading);
			if(reportType.equals("G")) {
				cell.setCellValue("Ageing of Stock GRN Date wise as on "+to_date);
			}
			else {
				cell.setCellValue("Ageing of Stock Dispatch Date wise as on "+to_date);
			}
			wsheet.addMergedRegion(new CellRangeAddress(row, row, col, col + totalCol));
			row++;

			hrow = (XSSFRow) wsheet.createRow(row);
			cell = hrow.createCell(col);
			cell.setCellStyle(sheetHeading);
			cell.setCellValue("Financial Year From "+MedicoResources.convertUtilDateToString_DD_MM_YYYY(finyr.getFin_start_date()) +" To "+MedicoResources.convertUtilDateToString_DD_MM_YYYY(finyr.getFin_end_date()));
			wsheet.addMergedRegion(new CellRangeAddress(row, row, col, col + totalCol));
			row++;
			
			hrow = (XSSFRow) wsheet.createRow(row);

			cell = hrow.createCell(col);
			cell.setCellStyle(columnHeading);
			cell.setCellValue("Product Code");
			col++;

			cell = hrow.createCell(col);
			cell.setCellStyle(columnHeading);
			cell.setCellValue("Product Name");
			col++;


			cell = hrow.createCell(col);
			cell.setCellStyle(columnHeading);
			cell.setCellValue("Location");
			col++;
			
			cell = hrow.createCell(col);
			cell.setCellStyle(columnHeading);
			cell.setCellValue("Batch No.");
			col++;
			
			cell = hrow.createCell(col);
			cell.setCellStyle(columnHeading);
			cell.setCellValue("Expiry Date");
			col++;

			cell = hrow.createCell(col);
			cell.setCellStyle(columnHeading);
			cell.setCellValue("Rate");
			col++;

			cell = hrow.createCell(col);
			cell.setCellStyle(columnHeading);
			cell.setCellValue("Closing Stock");
			col++;

			cell = hrow.createCell(col);
			cell.setCellStyle(columnHeading);
			cell.setCellValue(slab1+" - "+slab2);
			col++;

			cell = hrow.createCell(col);
			cell.setCellStyle(columnHeading);
			cell.setCellValue(slab3+" - "+slab4);
			col++;

			cell = hrow.createCell(col);
			cell.setCellStyle(columnHeading);
			cell.setCellValue(slab5+" - "+slab6);
			col++;
			
			cell = hrow.createCell(col);
			cell.setCellStyle(columnHeading);
			cell.setCellValue(slab7+" - "+slab8);
			col++;
			
			cell = hrow.createCell(col);
			cell.setCellStyle(columnHeading);
			cell.setCellValue(slab9+" - "+slab10);
			col++;
			
			cell = hrow.createCell(col);
			cell.setCellStyle(columnHeading);
			cell.setCellValue(slab11);
			col++;
			
			cell = hrow.createCell(col);
			cell.setCellStyle(columnHeading);
			cell.setCellValue(reportType.equalsIgnoreCase("G")?"GRN Date":"Dispatch Date");
			col++;
			
			cell = hrow.createCell(col);
			cell.setCellStyle(columnHeading);
			cell.setCellValue("Days");
			col++;
			
			Long new_div=null; 
			Long old_div=0L;
			Long new_prod=null; 
			Long old_prod=0L;
			Long new_loc=null; 
			Long old_loc=0L;
			Boolean isPrintLocTot=true;
			BigDecimal ZERO = BigDecimal.ZERO;
			
			BigDecimal[] div_total_qty = new BigDecimal[7];
			BigDecimal[] div_total_val = new BigDecimal[7];
			BigDecimal[] loc_total_qty = new BigDecimal[7];
			BigDecimal[] loc_total_val = new BigDecimal[7];
			BigDecimal[] item_total_qty = new BigDecimal[7];
			BigDecimal[] item_total_val = new BigDecimal[7];
			
			Arrays.fill(div_total_qty, ZERO);
			Arrays.fill(div_total_val, ZERO);
			Arrays.fill(loc_total_qty, ZERO);
			Arrays.fill(loc_total_val, ZERO);
			Arrays.fill(item_total_qty, ZERO);
			Arrays.fill(item_total_val, ZERO);
			
			
			for (StockAgeingGrnReportView lst : stockAgeing) {
				new_div=lst.getDiv_id();
				new_prod=lst.getProd_id();
				new_loc=lst.getLoc_id();
				
				//prod
				if(new_prod.compareTo(old_prod)!=0){
					if(old_prod.compareTo(0L)!=0){
												
//						row++; col = 0;
//						hrow = (XSSFRow) wsheet.createRow(row);
//						addRowInGRN(hrow, col, cell, columnSubHeading, 
//								columnHeadingNumeric, "Location Qty. Total:", loc_total_qty, wsheet);
//						
//						row++; col = 0;
//						hrow = (XSSFRow) wsheet.createRow(row);
//						addRowInGRN(hrow, col, cell, columnSubHeading, 
//								columnHeadingNumeric, "Location Value Total:", loc_total_val, wsheet);
//						
						row++; col = 0;
						hrow = (XSSFRow) wsheet.createRow(row);
						addRowInGRN(hrow, col, cell, columnSubHeading, 
								columnHeadingNumeric, "Item Qty. Total:", item_total_qty, wsheet);
						
						row++; col = 0;
						hrow = (XSSFRow) wsheet.createRow(row);
						addRowInGRN(hrow, col, cell, columnSubHeading, 
								columnHeadingNumeric, "Item Value Total:", item_total_val, wsheet);
						
					
						Arrays.fill(item_total_qty, ZERO);
						Arrays.fill(item_total_val, ZERO);
						isPrintLocTot=false;
					}
				}
				
				//divison
				if(new_div.compareTo(old_div)!=0){
					if(old_div.compareTo(0L)!=0){
												
//						row++; col = 0;
//						hrow = (XSSFRow) wsheet.createRow(row);
//					
//						
//						row++; col = 0;
//						hrow = (XSSFRow) wsheet.createRow(row);
//						addRowInGRN(hrow, col, cell, columnSubHeading, 
//								columnHeadingNumeric, "Item Qty. Total:", item_total_qty, wsheet);
//						
//						row++; col = 0;
//						hrow = (XSSFRow) wsheet.createRow(row);
//						addRowInGRN(hrow, col, cell, columnSubHeading, 
//								columnHeadingNumeric, "Item Value Total:", item_total_val, wsheet);
						
//						row++; col = 0;
//						hrow = (XSSFRow) wsheet.createRow(row);
//						addRowInGRN(hrow, col, cell, columnSubHeading, 
//								columnHeadingNumeric, "Division Qty. Total:", div_total_qty, wsheet);
						
						row++; col = 0;
						hrow = (XSSFRow) wsheet.createRow(row);
						addRowInGRN(hrow, col, cell, columnSubHeading, 
								columnHeadingNumeric, "Division Value Total:", div_total_val, wsheet);
						
						
						Arrays.fill(div_total_qty, ZERO);
						Arrays.fill(div_total_val, ZERO);
						
						Arrays.fill(item_total_qty, ZERO);
						Arrays.fill(item_total_val, ZERO);
					}
					
					//print div name
					row++; col = 0;
					hrow = (XSSFRow) wsheet.createRow(row);
					
					addRowInGRN(hrow, col, cell, columnSubHeading, 
							columnHeadingNumeric, "Division: "+lst.getDiv_name(), null, wsheet);
				}
				

				
				//location
//				if(new_loc.compareTo(old_loc)!=0){
//					if(isPrintLocTot && old_loc.compareTo(0L)!=0){
//												
//						row++; col = 0;
//						hrow = (XSSFRow) wsheet.createRow(row);
//						addRowInGRN(hrow, col, cell, columnSubHeading, 
//								columnHeadingNumeric, "Location Qty. Total:", loc_total_qty, wsheet);
//						
//						row++; col = 0;
//						hrow = (XSSFRow) wsheet.createRow(row);
//						addRowInGRN(hrow, col, cell, columnSubHeading, 
//								columnHeadingNumeric, "Location Value Total:", loc_total_val, wsheet);
//						
//						
//						Arrays.fill(loc_total_qty, ZERO);
//						Arrays.fill(loc_total_val, ZERO);
//					}
//				}
				
				
				row++; col = 0;
				hrow = (XSSFRow) wsheet.createRow(row);
				
				if(new_prod.compareTo(old_prod)!=0){
					cell = hrow.createCell(col);
					cell.setCellStyle(leftAlign);
					cell.setCellValue(lst.getSmp_prod_cd());
					col++;
					
					cell = hrow.createCell(col);
					cell.setCellStyle(leftAlign);
					cell.setCellValue(lst.getProd_name());
					col++;
					
				
				}else{
					
					
					cell = hrow.createCell(col);
					cell.setCellStyle(leftAlign);
					cell.setCellValue("");
					col++;
					
					cell = hrow.createCell(col);
					cell.setCellStyle(leftAlign);
					cell.setCellValue("");
					col++;
				}
				cell = hrow.createCell(col);
				cell.setCellStyle(leftAlign);
				cell.setCellValue(lst.getLoc_name());
				col++;
//				if(new_loc.compareTo(old_loc)!=0){
//					cell = hrow.createCell(col);
//					cell.setCellStyle(leftAlign);
//					cell.setCellValue(lst.getLoc_name());
//					col++;
//				}else{
//					cell = hrow.createCell(col);
//					cell.setCellStyle(leftAlign);
//					cell.setCellValue("");
//					col++;
//				}
				
				cell = hrow.createCell(col);
				cell.setCellStyle(leftAlign);
				cell.setCellValue(lst.getBatch_no());
				col++;
				
								
				cell = hrow.createCell(col);
				cell.setCellStyle(leftAlign);
				cell.setCellValue(lst.getExpiry_dt());
				col++;
				
				BigDecimal stk_rate=lst.getBatch_rate()!=null ? lst.getBatch_rate() : ZERO;
				BigDecimal clos_stock_qty=lst.getClos_qty()!=null ? lst.getClos_qty() : ZERO;
				BigDecimal clos_stock_val=lst.getClos_val()!=null ? lst.getClos_val() : ZERO;
				BigDecimal slab1_=lst.getSlab1_2()!=null ? lst.getSlab1_2() : ZERO;
				BigDecimal slab2_=lst.getSlab3_4()!=null ? lst.getSlab3_4() : ZERO;
				BigDecimal slab3_=lst.getSlab5_6()!=null ? lst.getSlab5_6() : ZERO;
				BigDecimal slab4_=lst.getSlab7_8()!=null ? lst.getSlab7_8() : ZERO;
				BigDecimal slab5_=lst.getSlab9_10()!=null ? lst.getSlab9_10() : ZERO;
				BigDecimal slab6_=lst.getSlab_11()!=null ? lst.getSlab_11() : ZERO;
				
				cell = hrow.createCell(col);
				cell.setCellStyle(rightAlignWthDecimal);
				cell.setCellValue(stk_rate.doubleValue());
				col++;
									
				cell = hrow.createCell(col);
				cell.setCellStyle(rightAlignWthDecimal);
				cell.setCellValue(clos_stock_qty.doubleValue());
				col++;
				
				cell = hrow.createCell(col);
				cell.setCellStyle(rightAlignWthDecimal);
				cell.setCellValue(lst.getSlab1_2().doubleValue());
				col++;
				
				cell = hrow.createCell(col);
				cell.setCellStyle(rightAlignWthDecimal);
				cell.setCellValue(lst.getSlab3_4().doubleValue());
				col++;
				
				cell = hrow.createCell(col);
				cell.setCellStyle(rightAlignWthDecimal);
				cell.setCellValue(lst.getSlab5_6().doubleValue());
				col++;
				
				cell = hrow.createCell(col);
				cell.setCellStyle(rightAlignWthDecimal);
				cell.setCellValue(lst.getSlab7_8().doubleValue());
				col++;
				
				cell = hrow.createCell(col);
				cell.setCellStyle(rightAlignWthDecimal);
				cell.setCellValue(lst.getSlab9_10().doubleValue());
				col++;
				
				cell = hrow.createCell(col);
				cell.setCellStyle(rightAlignWthDecimal);
				cell.setCellValue(lst.getSlab_11().doubleValue());
				col++;
				
				cell = hrow.createCell(col);
				cell.setCellStyle(leftAlign);
				cell.setCellValue(lst.getDoc_dt());
				col++;
				
				cell = hrow.createCell(col);
				cell.setCellStyle(dataRightAlign);
				cell.setCellValue(lst.getDays());
				col++;
				
				item_total_qty[0] = item_total_qty[0].add(clos_stock_qty);
				item_total_qty[1] = item_total_qty[1].add(slab1_);
				item_total_qty[2] = item_total_qty[2].add(slab2_);
				item_total_qty[3] = item_total_qty[3].add(slab3_);
				item_total_qty[4] = item_total_qty[4].add(slab4_);
				item_total_qty[5] = item_total_qty[5].add(slab5_);
				item_total_qty[6] = item_total_qty[6].add(slab6_);
				
				loc_total_qty[0] = loc_total_qty[0].add(clos_stock_qty);
				loc_total_qty[1] = loc_total_qty[1].add(slab1_);
				loc_total_qty[2] = loc_total_qty[2].add(slab2_);
				loc_total_qty[3] = loc_total_qty[3].add(slab3_);
				loc_total_qty[4] = loc_total_qty[4].add(slab4_);
				loc_total_qty[5] = loc_total_qty[5].add(slab5_);
				loc_total_qty[6] = loc_total_qty[6].add(slab6_);
				
				div_total_qty[0] = div_total_qty[0].add(clos_stock_qty);
				div_total_qty[1] = div_total_qty[1].add(slab1_);
				div_total_qty[2] = div_total_qty[2].add(slab2_);
				div_total_qty[3] = div_total_qty[3].add(slab3_);
				div_total_qty[4] = div_total_qty[4].add(slab4_);
				div_total_qty[5] = div_total_qty[5].add(slab5_);
				div_total_qty[6] = div_total_qty[6].add(slab6_);
				
				item_total_val[0] = item_total_val[0].add(clos_stock_val);
				item_total_val[1] = item_total_val[1].add(slab1_.multiply(stk_rate));
				item_total_val[2] = item_total_val[2].add(slab2_.multiply(stk_rate));
				item_total_val[3] = item_total_val[3].add(slab3_.multiply(stk_rate));
				item_total_val[4] = item_total_val[4].add(slab4_.multiply(stk_rate));
				item_total_val[5] = item_total_val[5].add(slab5_.multiply(stk_rate));
				item_total_val[6] = item_total_val[6].add(slab6_.multiply(stk_rate));
				
				loc_total_val[0] = loc_total_val[0].add(clos_stock_val);
				loc_total_val[1] = loc_total_val[1].add(slab1_.multiply(stk_rate));
				loc_total_val[2] = loc_total_val[2].add(slab2_.multiply(stk_rate));
				loc_total_val[3] = loc_total_val[3].add(slab3_.multiply(stk_rate));
				loc_total_val[4] = loc_total_val[4].add(slab4_.multiply(stk_rate));
				loc_total_val[5] = loc_total_val[5].add(slab5_.multiply(stk_rate));
				loc_total_val[6] = loc_total_val[6].add(slab6_.multiply(stk_rate));
				
				div_total_val[0] = div_total_val[0].add(clos_stock_val);
				div_total_val[1] = div_total_val[1].add(slab1_.multiply(stk_rate));
				div_total_val[2] = div_total_val[2].add(slab2_.multiply(stk_rate));
				div_total_val[3] = div_total_val[3].add(slab3_.multiply(stk_rate));
				div_total_val[4] = div_total_val[4].add(slab4_.multiply(stk_rate));
				div_total_val[5] = div_total_val[5].add(slab5_.multiply(stk_rate));
				div_total_val[6] = div_total_val[6].add(slab6_.multiply(stk_rate));
				
				old_div=new_div;
				old_prod=new_prod;
				old_loc=new_loc;
				isPrintLocTot=true;
				
			}

			//total
			row++; col = 0;
			hrow = (XSSFRow) wsheet.createRow(row);
		
			
			row++; col = 0;
			hrow = (XSSFRow) wsheet.createRow(row);
			addRowInGRN(hrow, col, cell, columnSubHeading, 
					columnHeadingNumeric, "Item Qty. Total:", item_total_qty, wsheet);
			
			row++; col = 0;
			hrow = (XSSFRow) wsheet.createRow(row);
			addRowInGRN(hrow, col, cell, columnSubHeading, 
					columnHeadingNumeric, "Item Value Total:", item_total_val, wsheet);
			
//			row++; col = 0;
//			hrow = (XSSFRow) wsheet.createRow(row);
//			addRowInGRN(hrow, col, cell, columnSubHeading, 
//					columnHeadingNumeric, "Division Qty. Total:", div_total_qty, wsheet);
			
			row++; col = 0;
			hrow = (XSSFRow) wsheet.createRow(row);
			addRowInGRN(hrow, col, cell, columnSubHeading, 
					columnHeadingNumeric, "Division Value Total:", div_total_val, wsheet);
			
//			row++; col = 0;
//			hrow = (XSSFRow) wsheet.createRow(row);
//			addRowInGRN(hrow, col, cell, columnSubHeading, 
//					columnHeadingNumeric, "Location Qty. Total:", loc_total_qty, wsheet);
			
			row++; col = 0;
			hrow = (XSSFRow) wsheet.createRow(row);
			addRowInGRN(hrow, col, cell, columnSubHeading, 
					columnHeadingNumeric, "Location Value Total:", loc_total_val, wsheet);
			
			FileOutputStream fileOut = new FileOutputStream(path.toString());
			 wwbook.write(fileOut);
			 fileOut.close();
		
		
			System.out.println("Excel Created");
			
			filename=usermasterservice.generateExcellock(path.toString(), filename,empId,".xlsx");


		} catch (Exception e) {
			System.out.println("Error Occurred :" +e.getMessage());// uncomment asneeded --;
		} finally {
			try{
				if(wwbook!=null) wwbook.close();
			}catch(Exception e){
				System.out.println("Error Occurred :" +e.getMessage());// uncomment asneeded --;
			}
		}

		return filename;
	}

	private void addRowInGRN(XSSFRow hrow, short col, XSSFCell cell, 
			 CellStyle cellStyleForText, CellStyle cellStyleForNum, String text1, 
			 BigDecimal[] array, Sheet wsheet) throws Exception{
		
		cell = hrow.createCell(col);
		cell.setCellStyle(cellStyleForText);
		cell.setCellValue(text1);
		col++;
		
		cell = hrow.createCell(col);
		cell.setCellStyle(cellStyleForText);
		cell.setCellValue("");
		col++;
		
		cell = hrow.createCell(col);
		cell.setCellStyle(cellStyleForText);
		cell.setCellValue("");
		col++;
		
		cell = hrow.createCell(col);
		cell.setCellStyle(cellStyleForText);
		cell.setCellValue("");
		col++;
		
		cell = hrow.createCell(col);
		cell.setCellStyle(cellStyleForText);
		cell.setCellValue("");
		col++;
		
		cell = hrow.createCell(col);
		cell.setCellStyle(cellStyleForText);
		cell.setCellValue("");
		col++;
				
		if(array!=null){
			for(int i=0; i<7; i++){
				cell = hrow.createCell(col);
				cell.setCellStyle(cellStyleForNum);
				cell.setCellValue(array[i].doubleValue());
				col++;
			}
		}else{
			for(int i=0; i<7; i++){
				cell = hrow.createCell(col);
				cell.setCellStyle(cellStyleForNum);
				cell.setCellValue("");
				col++;
			}
		}
		
		cell = hrow.createCell(col);
		cell.setCellStyle(cellStyleForText);
		cell.setCellValue("");
		col++;
		
		cell = hrow.createCell(col);
		cell.setCellStyle(cellStyleForText);
		cell.setCellValue("");
		col++;
	}
}
