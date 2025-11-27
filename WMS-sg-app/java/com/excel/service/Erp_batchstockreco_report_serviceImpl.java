package com.excel.service;

import java.io.File;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.FillPatternType;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.usermodel.IndexedColorMap;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.apache.poi.xssf.usermodel.XSSFColor;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.excel.model.Erp_Batch_stk_reco;
import com.excel.model.Erp_Batch_stk_reco_quarantine;
import com.excel.model.Usermaster;
import com.excel.repository.UserMasterRepository;
import com.excel.security.PasswordManager;
import com.excel.utility.ApachePoiExcelFormat;
import com.excel.utility.MedicoConstants;
import com.excel.utility.MedicoResources;
import com.excel.utility.Utility;

import net.lingala.zip4j.core.ZipFile;
import net.lingala.zip4j.model.ZipParameters;
import net.lingala.zip4j.util.Zip4jConstants;

@Service
public class Erp_batchstockreco_report_serviceImpl implements Erp_batchstockreco_report_service{

	@Autowired private UserMasterRepository usermasterrepository;
	@Autowired private UserMasterService usermasterservice;

	@SuppressWarnings("deprecation")
	@Override
	public String generatebatch_stk_reco_report(List<Erp_Batch_stk_reco> list,String companyname,String empId)
			throws Exception {
		// TODO Auto-generated method stub
		
		Workbook wwbook = null;
		File ff = null;
		String filename = null;
		StringBuffer path = null;
		try {

			long l = new Date().getTime();
			filename = "WMs_batch_stk_reco_report_" + l + ".xlsx";
			String filePath = MedicoConstants.REPORT_FILE_PATH ;

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
			Sheet wsheet = wwbook.createSheet("Wms_Batch_stk_reco");
			
			ApachePoiExcelFormat xlsxExcelFormat = new ApachePoiExcelFormat();
			
			CellStyle sheetHeading = xlsxExcelFormat.SheetHeading(wwbook);
			CellStyle columnHeading = xlsxExcelFormat.columnHeading(wwbook);
			
			 IndexedColorMap colorMap = ((XSSFWorkbook) wwbook).getStylesSource().getIndexedColors();
			 XSSFColor light_red = new XSSFColor(new java.awt.Color(242,183,182), colorMap);
			
			XSSFCellStyle redcolorstyle =  (XSSFCellStyle) wwbook.createCellStyle();
			redcolorstyle.setFillForegroundColor(light_red);
			redcolorstyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);
			
			XSSFCellStyle redcolorstyledecimal =  (XSSFCellStyle) wwbook.createCellStyle();
			redcolorstyledecimal.setFillForegroundColor(light_red);
			redcolorstyledecimal.setFillPattern(FillPatternType.SOLID_FOREGROUND);
			redcolorstyledecimal.setAlignment(HorizontalAlignment.RIGHT);
			redcolorstyledecimal.setDataFormat(wwbook.createDataFormat().getFormat("0.00"));
			
			CellStyle styledecimal =  wwbook.createCellStyle();
			styledecimal.setAlignment(HorizontalAlignment.RIGHT);
			styledecimal.setDataFormat(wwbook.createDataFormat().getFormat("0.00"));
			
			wsheet.createFreezePane(0, 4);
			
			int col = 0;
			short row = 0;
			XSSFRow hrow = (XSSFRow) (XSSFRow) wsheet.createRow(row);
			
			XSSFCell cell = hrow.createCell(col);
			cell.setCellStyle(sheetHeading);
			cell.setCellValue(companyname);
			wsheet.addMergedRegion(new CellRangeAddress(row, row, col, col + 15));

			row++;
			
			hrow = (XSSFRow) wsheet.createRow(row);
			cell = hrow.createCell(col);
			cell.setCellStyle(sheetHeading);
			cell.setCellValue("WMS Batchwise Stock Reco Report");
			wsheet.addMergedRegion(new CellRangeAddress(row, row, col, col + 15));

			row++;

			hrow = (XSSFRow) wsheet.createRow(row);
			cell = hrow.createCell(col);
			cell.setCellStyle(sheetHeading);
			cell.setCellValue("Report Dated : "
					+ Utility.convertSmpDatetoString(new Date()));
			wsheet.addMergedRegion(new CellRangeAddress(row, row, col, col + 15));
			
			col++;
			
			col=0;
			
			row++;
			
			hrow = (XSSFRow) wsheet.createRow(row);

			cell = hrow.createCell(col);
			cell.setCellStyle(columnHeading);
			cell.setCellValue("Sl No.");
			col++;
			
			cell = hrow.createCell(col);
			cell.setCellStyle(columnHeading);
			cell.setCellValue("Reco Date");
			col++;
			
			cell = hrow.createCell(col);
			cell.setCellStyle(columnHeading);
			cell.setCellValue("Fin Year");
			col++;
			
			cell = hrow.createCell(col);
			cell.setCellStyle(columnHeading);
			cell.setCellValue("Organization");
			col++;
			
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
			cell.setCellValue("Batch No");
			col++;
			
			cell = hrow.createCell(col);
			cell.setCellStyle(columnHeading);
			cell.setCellValue("Stock Type");
			col++;
			
			cell = hrow.createCell(col);
			cell.setCellStyle(columnHeading);
			cell.setCellValue("Quantity");
			col++;
			
			cell = hrow.createCell(col);
			cell.setCellStyle(columnHeading);
			cell.setCellValue("Medico Organization");
			col++;
			
			cell = hrow.createCell(col);
			cell.setCellStyle(columnHeading);
			cell.setCellValue("Medico Product Code");
			col++;
			
			cell = hrow.createCell(col);
			cell.setCellStyle(columnHeading);
			cell.setCellValue("Medico Product Name");
			col++;
			
			cell = hrow.createCell(col);
			cell.setCellStyle(columnHeading);
			cell.setCellValue("Medico Batch No");
			col++;
			
			cell = hrow.createCell(col);
			cell.setCellStyle(columnHeading);
			cell.setCellValue("Medico Stock Type");
			col++;
			
			cell = hrow.createCell(col);
			cell.setCellStyle(columnHeading);
			cell.setCellValue("Medico Quantity");
			col++;
			
			cell = hrow.createCell(col);
			cell.setCellStyle(columnHeading);
			cell.setCellValue("Difference");
			col++;
			
			col=0;
			row++;
			
			for(Erp_Batch_stk_reco reco:list) {
				hrow = (XSSFRow) wsheet.createRow(row);
				
			if(!(reco.getDifference()== null)) {	
				if(!(reco.getDifference().doubleValue()== 0d)) {
					cell = hrow.createCell(col);
						cell.setCellStyle(redcolorstyle);
						cell.setCellValue(reco.getSlno());
						col++;
						
						cell = hrow.createCell(col);
						cell.setCellStyle(redcolorstyle);
						cell.setCellValue(MedicoResources.convertUtilDateToString_DD_MM_YYYY(reco.getReco_date()));
						col++;	
						
						cell = hrow.createCell(col);
						cell.setCellStyle(redcolorstyle);
						cell.setCellValue(reco.getFin_year());
						col++;	
						
						cell = hrow.createCell(col);
						cell.setCellStyle(redcolorstyle);
						cell.setCellValue(reco.getOrganization());
						col++;	
						
						cell = hrow.createCell(col);
						cell.setCellStyle(redcolorstyle);
						cell.setCellValue(reco.getProduct_code());
						col++;	

						
						cell = hrow.createCell(col);
						cell.setCellStyle(redcolorstyle);
						cell.setCellValue(reco.getProduct_name());
						col++;	

						cell = hrow.createCell(col);
						cell.setCellStyle(redcolorstyle);
						cell.setCellValue(reco.getBatch_no());
						col++;	

						cell = hrow.createCell(col);
						cell.setCellStyle(redcolorstyle);
						cell.setCellValue(reco.getStock_type());
						col++;	
						
						cell = hrow.createCell(col);
						cell.setCellStyle(redcolorstyledecimal);
						cell.setCellValue(reco.getQty().doubleValue());
						col++;	

						cell = hrow.createCell(col);
						cell.setCellStyle(redcolorstyle);
						cell.setCellValue(reco.getMedico_org());
						col++;	
						
						cell = hrow.createCell(col);
						cell.setCellStyle(redcolorstyle);
						cell.setCellValue(reco.getMedico_prod_code());
						col++;	

						cell = hrow.createCell(col);
						cell.setCellStyle(redcolorstyle);
						cell.setCellValue(reco.getMedico_prod_name());
						col++;					

						cell = hrow.createCell(col);
						cell.setCellStyle(redcolorstyle);
						cell.setCellValue(reco.getMedico_batch_no());
						col++;	
						
						cell = hrow.createCell(col);
						cell.setCellStyle(redcolorstyle);
						cell.setCellValue(reco.getMedico_stk_type());
						col++;	
						
						cell = hrow.createCell(col);
						cell.setCellStyle(redcolorstyledecimal);
						cell.setCellValue(reco.getMedico_qty().doubleValue());
						col++;	
							
						cell = hrow.createCell(col);
						cell.setCellStyle(redcolorstyledecimal);
						cell.setCellValue(reco.getDifference()==null?0l:reco.getDifference().doubleValue());
						col++;	
						
				}
				else {
				cell = hrow.createCell(col);
			//	cell.setCellStyle(columnHeading);
				cell.setCellValue(reco.getSlno());
				col++;
				
				cell = hrow.createCell(col);
			//	cell.setCellStyle(columnHeading);
				cell.setCellValue(MedicoResources.convertUtilDateToString_DD_MM_YYYY(reco.getReco_date()));
				col++;	
				
				cell = hrow.createCell(col);
			//	cell.setCellStyle(columnHeading);
				cell.setCellValue(reco.getFin_year());
				col++;	
				
				cell = hrow.createCell(col);
			//	cell.setCellStyle(columnHeading);
				cell.setCellValue(reco.getOrganization());
				col++;	
				
				cell = hrow.createCell(col);
			//	cell.setCellStyle(columnHeading);
				cell.setCellValue(reco.getProduct_code());
				col++;	

				
				cell = hrow.createCell(col);
			//	cell.setCellStyle(columnHeading);
				cell.setCellValue(reco.getProduct_name());
				col++;	

				cell = hrow.createCell(col);
			//	cell.setCellStyle(columnHeading);
				cell.setCellValue(reco.getBatch_no());
				col++;	

				cell = hrow.createCell(col);
			//	cell.setCellStyle(columnHeading);
				cell.setCellValue(reco.getStock_type());
				col++;	
				
				cell = hrow.createCell(col);
				cell.setCellStyle(styledecimal);
				cell.setCellValue(reco.getQty().doubleValue());
				col++;	

				cell = hrow.createCell(col);
			//	cell.setCellStyle(styledecimal);
				cell.setCellValue(reco.getMedico_org());
				col++;	
				
				cell = hrow.createCell(col);
			//	cell.setCellStyle(columnHeading);
				cell.setCellValue(reco.getMedico_prod_code());
				col++;	

				cell = hrow.createCell(col);
				//	cell.setCellStyle(columnHeading);
				cell.setCellValue(reco.getMedico_prod_name());
				col++;					

				cell = hrow.createCell(col);
				//	cell.setCellStyle(columnHeading);
				cell.setCellValue(reco.getMedico_batch_no());
				col++;	
				
				cell = hrow.createCell(col);
				//	cell.setCellStyle(columnHeading);
				cell.setCellValue(reco.getMedico_stk_type());
				col++;	
				
				cell = hrow.createCell(col);
				cell.setCellStyle(styledecimal);
				cell.setCellValue(reco.getMedico_qty().doubleValue());
				col++;	
					
				cell = hrow.createCell(col);
				cell.setCellStyle(styledecimal);
				cell.setCellValue(reco.getDifference()==null?0l:reco.getDifference().doubleValue());
				col++;	
				}
			}
			else {
				cell = hrow.createCell(col);
			//	cell.setCellStyle(columnHeading);
				cell.setCellValue(reco.getSlno());
				col++;
				
				cell = hrow.createCell(col);
			//	cell.setCellStyle(columnHeading);
				cell.setCellValue(MedicoResources.convertUtilDateToString_DD_MM_YYYY(reco.getReco_date()));
				col++;	
				
				cell = hrow.createCell(col);
			//	cell.setCellStyle(columnHeading);
				cell.setCellValue(reco.getFin_year());
				col++;	
				
				cell = hrow.createCell(col);
			//	cell.setCellStyle(columnHeading);
				cell.setCellValue(reco.getOrganization());
				col++;	
				
				cell = hrow.createCell(col);
			//	cell.setCellStyle(columnHeading);
				cell.setCellValue(reco.getProduct_code());
				col++;	

				
				cell = hrow.createCell(col);
			//	cell.setCellStyle(columnHeading);
				cell.setCellValue(reco.getProduct_name());
				col++;	

				cell = hrow.createCell(col);
			//	cell.setCellStyle(columnHeading);
				cell.setCellValue(reco.getBatch_no());
				col++;	

				cell = hrow.createCell(col);
			//	cell.setCellStyle(columnHeading);
				cell.setCellValue(reco.getStock_type());
				col++;	
				
				cell = hrow.createCell(col);
				cell.setCellStyle(styledecimal);
				cell.setCellValue(reco.getQty().doubleValue());
				col++;	

				cell = hrow.createCell(col);
				cell.setCellStyle(styledecimal);
				cell.setCellValue(reco.getMedico_org());
				col++;	
				
				cell = hrow.createCell(col);
			//	cell.setCellStyle(columnHeading);
				cell.setCellValue(reco.getMedico_prod_code());
				col++;	

				cell = hrow.createCell(col);
				//	cell.setCellStyle(columnHeading);
				cell.setCellValue(reco.getMedico_prod_name());
				col++;					

				cell = hrow.createCell(col);
				//	cell.setCellStyle(columnHeading);
				cell.setCellValue(reco.getMedico_batch_no());
				col++;	
				
				cell = hrow.createCell(col);
				//	cell.setCellStyle(columnHeading);
				cell.setCellValue(reco.getMedico_stk_type());
				col++;	
				
				cell = hrow.createCell(col);
				cell.setCellStyle(styledecimal);
				cell.setCellValue(reco.getMedico_qty()==null?0l:reco.getMedico_qty().doubleValue());
				col++;	
					
				cell = hrow.createCell(col);
				cell.setCellStyle(styledecimal);
				cell.setCellValue(reco.getDifference()==null?0l:reco.getDifference().doubleValue());
				col++;	
				}
				
				col = 0;
				row++;
			}
			FileOutputStream fileOut = new FileOutputStream(path.toString());
			wwbook.write(fileOut);
			
			filename=usermasterservice.generateExcellock(path.toString(), filename,empId,".xlsx");

			
			
			fileOut.close();

			System.out.println("Excel Created " + filePath);
			
			
		}
		catch (Exception e) {
			// TODO: handle exception
			throw e;
		}
		return filename ;
	}

	@Override
	public String generate_stockRecoQuarantine_Report(List<Erp_Batch_stk_reco_quarantine> list, String companyname,String empId)
			throws Exception {
		// TODO Auto-generated method stub
		
		Workbook wwbook = null;
		File ff = null;
		String filename = null;
		StringBuffer path = null;
		try {

			long l = new Date().getTime();
			filename = "WMs_batch_stk_reco_quarantine_report_" + l  + ".xlsx";
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
			Sheet wsheet = wwbook.createSheet("Wms_Batch_stk_reco_quarantine");
			wsheet.createFreezePane(0, 4);
			
			ApachePoiExcelFormat xlsxExcelFormat = new ApachePoiExcelFormat();
			
			CellStyle sheetHeading = xlsxExcelFormat.SheetHeading(wwbook);
			CellStyle columnHeading = xlsxExcelFormat.columnHeading(wwbook);
			
			//For light red color 
			 IndexedColorMap colorMap = ((XSSFWorkbook) wwbook).getStylesSource().getIndexedColors();
			 XSSFColor light_red = new XSSFColor(new java.awt.Color(242,183,182), colorMap);
			 
			 XSSFCellStyle redcolorstyle =  (XSSFCellStyle) wwbook.createCellStyle();
			redcolorstyle.setFillForegroundColor(light_red);
			redcolorstyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);
			
			XSSFCellStyle redcolorstyledecimal =  (XSSFCellStyle) wwbook.createCellStyle();
			redcolorstyledecimal.setFillForegroundColor(light_red);
			redcolorstyledecimal.setFillPattern(FillPatternType.SOLID_FOREGROUND);
			redcolorstyledecimal.setAlignment(HorizontalAlignment.RIGHT);
			redcolorstyledecimal.setDataFormat(wwbook.createDataFormat().getFormat("0.00"));
			
			//till here
			CellStyle styledecimal =  wwbook.createCellStyle();
			styledecimal.setAlignment(HorizontalAlignment.RIGHT);
			styledecimal.setDataFormat(wwbook.createDataFormat().getFormat("0.00"));
			
			
			
			int col = 0;
			short row = 0;
			XSSFRow hrow = (XSSFRow) (XSSFRow) wsheet.createRow(row);
			
			XSSFCell cell = hrow.createCell(col);
			cell.setCellStyle(sheetHeading);
			cell.setCellValue(companyname);
			wsheet.addMergedRegion(new CellRangeAddress(row, row, col, col + 15));

			row++;
			
			hrow = (XSSFRow) wsheet.createRow(row);
			cell = hrow.createCell(col);
			cell.setCellStyle(sheetHeading);
			cell.setCellValue("WMS Batchwise Stock Reco Quarantine Report");
			wsheet.addMergedRegion(new CellRangeAddress(row, row, col, col + 15));

			row++;

			hrow = (XSSFRow) wsheet.createRow(row);
			cell = hrow.createCell(col);
			cell.setCellStyle(sheetHeading);
			cell.setCellValue("Report Dated : "
					+ Utility.convertSmpDatetoString(new Date()));
			wsheet.addMergedRegion(new CellRangeAddress(row, row, col, col + 15));
			
			col++;
			
			col=0;
			
			row++;
			
			hrow = (XSSFRow) wsheet.createRow(row);

			cell = hrow.createCell(col);
			cell.setCellStyle(columnHeading);
			cell.setCellValue("Sl No.");
			col++;
			
			cell = hrow.createCell(col);
			cell.setCellStyle(columnHeading);
			cell.setCellValue("Reco Date");
			col++;
			
			cell = hrow.createCell(col);
			cell.setCellStyle(columnHeading);
			cell.setCellValue("Fin Year");
			col++;
			
			cell = hrow.createCell(col);
			cell.setCellStyle(columnHeading);
			cell.setCellValue("Organization");
			col++;
			
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
			cell.setCellValue("Batch No");
			col++;
			
			cell = hrow.createCell(col);
			cell.setCellStyle(columnHeading);
			cell.setCellValue("Stock Type");
			col++;
			
			cell = hrow.createCell(col);
			cell.setCellStyle(columnHeading);
			cell.setCellValue("Quantity");
			col++;
			
			cell = hrow.createCell(col);
			cell.setCellStyle(columnHeading);
			cell.setCellValue("Medico Organization");
			col++;
			
			cell = hrow.createCell(col);
			cell.setCellStyle(columnHeading);
			cell.setCellValue("Medico Product Code");
			col++;
			
			cell = hrow.createCell(col);
			cell.setCellStyle(columnHeading);
			cell.setCellValue("Medico Product Name");
			col++;
			
			cell = hrow.createCell(col);
			cell.setCellStyle(columnHeading);
			cell.setCellValue("Medico Batch No");
			col++;
			
			cell = hrow.createCell(col);
			cell.setCellStyle(columnHeading);
			cell.setCellValue("Medico Stock Type");
			col++;
			
			cell = hrow.createCell(col);
			cell.setCellStyle(columnHeading);
			cell.setCellValue("Medico Quantity");
			col++;
			
			cell = hrow.createCell(col);
			cell.setCellStyle(columnHeading);
			cell.setCellValue("Difference");
			col++;
			
			col=0;
			row++;
			
			for(Erp_Batch_stk_reco_quarantine reco:list) {
				hrow = (XSSFRow) wsheet.createRow(row);
				
			if(!(reco.getDifference()== null)) {	
				if(!(reco.getDifference().doubleValue()== 0d)) {
					cell = hrow.createCell(col);
						cell.setCellStyle(redcolorstyle);
						cell.setCellValue(reco.getSlno());
						col++;
						
						cell = hrow.createCell(col);
						cell.setCellStyle(redcolorstyle);
						cell.setCellValue(MedicoResources.convertUtilDateToString_DD_MM_YYYY(reco.getReco_date()));
						col++;	
						
						cell = hrow.createCell(col);
						cell.setCellStyle(redcolorstyle);
						cell.setCellValue(reco.getFin_year());
						col++;	
						
						cell = hrow.createCell(col);
						cell.setCellStyle(redcolorstyle);
						cell.setCellValue(reco.getOrganization());
						col++;	
						
						cell = hrow.createCell(col);
						cell.setCellStyle(redcolorstyle);
						cell.setCellValue(reco.getProduct_code());
						col++;	

						
						cell = hrow.createCell(col);
						cell.setCellStyle(redcolorstyle);
						cell.setCellValue(reco.getProduct_name());
						col++;	

						cell = hrow.createCell(col);
						cell.setCellStyle(redcolorstyle);
						cell.setCellValue(reco.getBatch_no());
						col++;	

						cell = hrow.createCell(col);
						cell.setCellStyle(redcolorstyle);
						cell.setCellValue(reco.getStock_type());
						col++;	
						
						cell = hrow.createCell(col);
						cell.setCellStyle(redcolorstyledecimal);
						cell.setCellValue(reco.getQty().doubleValue());
						col++;	

						cell = hrow.createCell(col);
						cell.setCellStyle(redcolorstyle);
						cell.setCellValue(reco.getMedico_org());
						col++;	
						
						cell = hrow.createCell(col);
						cell.setCellStyle(redcolorstyle);
						cell.setCellValue(reco.getMedico_prod_code());
						col++;	

						cell = hrow.createCell(col);
						cell.setCellStyle(redcolorstyle);
						cell.setCellValue(reco.getMedico_prod_name());
						col++;					

						cell = hrow.createCell(col);
						cell.setCellStyle(redcolorstyle);
						cell.setCellValue(reco.getMedico_batch_no());
						col++;	
						
						cell = hrow.createCell(col);
						cell.setCellStyle(redcolorstyle);
						cell.setCellValue(reco.getMedico_stk_type());
						col++;	
						
						cell = hrow.createCell(col);
						cell.setCellStyle(redcolorstyledecimal);
						cell.setCellValue(reco.getMedico_qty().doubleValue());
						col++;	
							
						cell = hrow.createCell(col);
						cell.setCellStyle(redcolorstyledecimal);
						cell.setCellValue(reco.getDifference()==null?0l:reco.getDifference().doubleValue());
						col++;	
						
				}
				else {
				cell = hrow.createCell(col);
			//	cell.setCellStyle(columnHeading);
				cell.setCellValue(reco.getSlno());
				col++;
				
				cell = hrow.createCell(col);
			//	cell.setCellStyle(columnHeading);
				cell.setCellValue(MedicoResources.convertUtilDateToString_DD_MM_YYYY(reco.getReco_date()));
				col++;	
				
				cell = hrow.createCell(col);
			//	cell.setCellStyle(columnHeading);
				cell.setCellValue(reco.getFin_year());
				col++;	
				
				cell = hrow.createCell(col);
			//	cell.setCellStyle(columnHeading);
				cell.setCellValue(reco.getOrganization());
				col++;	
				
				cell = hrow.createCell(col);
			//	cell.setCellStyle(columnHeading);
				cell.setCellValue(reco.getProduct_code());
				col++;	

				
				cell = hrow.createCell(col);
			//	cell.setCellStyle(columnHeading);
				cell.setCellValue(reco.getProduct_name());
				col++;	

				cell = hrow.createCell(col);
			//	cell.setCellStyle(columnHeading);
				cell.setCellValue(reco.getBatch_no());
				col++;	

				cell = hrow.createCell(col);
			//	cell.setCellStyle(columnHeading);
				cell.setCellValue(reco.getStock_type());
				col++;	
				
				cell = hrow.createCell(col);
				cell.setCellStyle(styledecimal);
				cell.setCellValue(reco.getQty().doubleValue());
				col++;	

				cell = hrow.createCell(col);
			//	cell.setCellStyle(styledecimal);
				cell.setCellValue(reco.getMedico_org());
				col++;	
				
				cell = hrow.createCell(col);
			//	cell.setCellStyle(columnHeading);
				cell.setCellValue(reco.getMedico_prod_code());
				col++;	

				cell = hrow.createCell(col);
				//	cell.setCellStyle(columnHeading);
				cell.setCellValue(reco.getMedico_prod_name());
				col++;					

				cell = hrow.createCell(col);
				//	cell.setCellStyle(columnHeading);
				cell.setCellValue(reco.getMedico_batch_no());
				col++;	
				
				cell = hrow.createCell(col);
				//	cell.setCellStyle(columnHeading);
				cell.setCellValue(reco.getMedico_stk_type());
				col++;	
				
				cell = hrow.createCell(col);
				cell.setCellStyle(styledecimal);
				cell.setCellValue(reco.getMedico_qty().doubleValue());
				col++;	
					
				cell = hrow.createCell(col);
				cell.setCellStyle(styledecimal);
				cell.setCellValue(reco.getDifference()==null?0l:reco.getDifference().doubleValue());
				col++;	
				}
			}
			else {
				cell = hrow.createCell(col);
			//	cell.setCellStyle(columnHeading);
				cell.setCellValue(reco.getSlno());
				col++;
				
				cell = hrow.createCell(col);
			//	cell.setCellStyle(columnHeading);
				cell.setCellValue(MedicoResources.convertUtilDateToString_DD_MM_YYYY(reco.getReco_date()));
				col++;	
				
				cell = hrow.createCell(col);
			//	cell.setCellStyle(columnHeading);
				cell.setCellValue(reco.getFin_year());
				col++;	
				
				cell = hrow.createCell(col);
			//	cell.setCellStyle(columnHeading);
				cell.setCellValue(reco.getOrganization());
				col++;	
				
				cell = hrow.createCell(col);
			//	cell.setCellStyle(columnHeading);
				cell.setCellValue(reco.getProduct_code());
				col++;	

				
				cell = hrow.createCell(col);
			//	cell.setCellStyle(columnHeading);
				cell.setCellValue(reco.getProduct_name());
				col++;	

				cell = hrow.createCell(col);
			//	cell.setCellStyle(columnHeading);
				cell.setCellValue(reco.getBatch_no());
				col++;	

				cell = hrow.createCell(col);
			//	cell.setCellStyle(columnHeading);
				cell.setCellValue(reco.getStock_type());
				col++;	
				
				cell = hrow.createCell(col);
				cell.setCellStyle(styledecimal);
				cell.setCellValue(reco.getQty().doubleValue());
				col++;	

				cell = hrow.createCell(col);
				cell.setCellStyle(styledecimal);
				cell.setCellValue(reco.getMedico_org());
				col++;	
				
				cell = hrow.createCell(col);
			//	cell.setCellStyle(columnHeading);
				cell.setCellValue(reco.getMedico_prod_code());
				col++;	

				cell = hrow.createCell(col);
				//	cell.setCellStyle(columnHeading);
				cell.setCellValue(reco.getMedico_prod_name());
				col++;					

				cell = hrow.createCell(col);
				//	cell.setCellStyle(columnHeading);
				cell.setCellValue(reco.getMedico_batch_no());
				col++;	
				
				cell = hrow.createCell(col);
				//	cell.setCellStyle(columnHeading);
				cell.setCellValue(reco.getMedico_stk_type());
				col++;	
				
				cell = hrow.createCell(col);
				cell.setCellStyle(styledecimal);
				cell.setCellValue(reco.getMedico_qty()==null?0l:reco.getMedico_qty().doubleValue());
				col++;	
					
				cell = hrow.createCell(col);
				cell.setCellStyle(styledecimal);
				cell.setCellValue(reco.getDifference()==null?0l:reco.getDifference().doubleValue());
				col++;	
				}
				
				col = 0;
				row++;
			}
			FileOutputStream fileOut = new FileOutputStream(path.toString());
			wwbook.write(fileOut);
			
			filename=usermasterservice.generateExcellock(path.toString(), filename,empId,".xlsx");

			
			fileOut.close();

			System.out.println("Excel Created " + filePath);
			
			
		}
		catch (Exception e) {
			// TODO: handle exception
			throw e;
		}
		return filename ;
	}

}
