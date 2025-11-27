package com.excel.service;

import java.io.File;
import java.io.FileOutputStream;
import java.math.BigDecimal;
import java.util.Comparator;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.poi.ss.usermodel.BorderStyle;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Service;

import com.excel.model.Allocation_download;
import com.excel.model.Dispatch_alloc_monthwise_report;
import com.excel.utility.ApachePoiExcelFormat;
import com.excel.utility.MedicoConstants;

@Service
public class Dispatch_alloc_monthwise_reportServiceImpl implements Dispatch_alloc_monthwise_reportService{

	@Override
	public String generate_dispatch_alloc_monthwise_report(List<Dispatch_alloc_monthwise_report> lst,String startdate,String enddate) throws Exception {
		// TODO Auto-generated method stub
		
		Workbook wwbook = null;
		File ff = null;
		String filename = null;
		StringBuffer path = null;

		try {

			long l = new Date().getTime();
			filename = "Dispatch_monthwise_report" + l +"from_"+startdate+"_to_"+enddate+".xlsx";
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
			
			int col = 0;
			int row = 0;
			String[] heading1= {"Division","Product Group","Product Name","Nov","Dec","Jan","Feb","Mar","Apr","May","Jun","Jul","Aug","Sep","Oct","Total"};
			
			Map<String, Integer> prodmap=new LinkedHashMap<>();
			Set<String> Monthset = new LinkedHashSet<String>();
			Monthset.add("November");
			Monthset.add("December");
			Monthset.add("January");
			Monthset.add("February");
			Monthset.add("March");
			Monthset.add("April");
			Monthset.add("May");
			Monthset.add("June");
			Monthset.add("July");
			Monthset.add("August");
			Monthset.add("September");
			Monthset.add("October");
			
//			for (Dispatch_alloc_monthwise_report file_List : lst) {
//				Monthset.add(file_List.getDsp_mthnm());
//			}
			
			String[] monthAry=new String[Monthset.size()];
			int z=0;
			int prodCount=3;
			for (String product : Monthset) {
			//	productHeader.add(product);
				monthAry[z++]=product;
				prodmap.put(product,prodCount);
				prodCount++;
			}
			
			System.out.println("monthAry :: "+monthAry.length);
			
			wwbook = new XSSFWorkbook();
			
			ApachePoiExcelFormat xlsxExcelFormat = new ApachePoiExcelFormat();
			
			CellStyle sheetHeading = xlsxExcelFormat.SheetHeading(wwbook);
			CellStyle columnHeading = xlsxExcelFormat.columnHeading(wwbook);
			
			CellStyle decimalstyle2 = wwbook.createCellStyle();
		//	decimalstyle2.setDataFormat(wwbook.createDataFormat().getFormat("0.00"));
			decimalstyle2.setAlignment(HorizontalAlignment.RIGHT);
			decimalstyle2.setBorderLeft(BorderStyle.THIN);
			decimalstyle2.setBorderRight(BorderStyle.THIN);
			decimalstyle2.setBorderBottom(BorderStyle.THIN);
			decimalstyle2.setBorderTop(BorderStyle.THIN);
			
			
			CellStyle prodgrpstyle = wwbook.createCellStyle();
			prodgrpstyle.setBorderTop(BorderStyle.THIN);
			
			CellStyle divprg = wwbook.createCellStyle();
			divprg.setBorderTop(BorderStyle.DOUBLE);
			
			CellStyle divprg2 = wwbook.createCellStyle();
			divprg2.setBorderTop(BorderStyle.DOUBLE);
			divprg2.setBorderBottom(BorderStyle.THIN);
			divprg2.setBorderLeft(BorderStyle.THIN);
			divprg2.setBorderRight(BorderStyle.THIN);
			
			
			CellStyle divprg3 = wwbook.createCellStyle();
			divprg3.setBorderTop(BorderStyle.DOUBLE);
			divprg3.setBorderBottom(BorderStyle.THIN);
			
			CellStyle styleblank1 = wwbook.createCellStyle();
			styleblank1.setBorderTop(BorderStyle.THIN);
			
			CellStyle styleblank2 = wwbook.createCellStyle();
			styleblank2.setBorderBottom(BorderStyle.DOUBLE);
			
			CellStyle prodcell = wwbook.createCellStyle();
			//	decimalstyle2.setDataFormat(wwbook.createDataFormat().getFormat("0.00"));
			prodcell.setAlignment(HorizontalAlignment.LEFT);
			prodcell.setBorderLeft(BorderStyle.THIN);
			prodcell.setBorderRight(BorderStyle.THIN);
			prodcell.setBorderBottom(BorderStyle.THIN);
			prodcell.setBorderTop(BorderStyle.THIN);
			
			Sheet wsheet = wwbook.createSheet("Dispatch_alloc_monthwise");
			
		
			wsheet.createFreezePane(3, 3);								//added
			
			XSSFRow hrow = null;
			XSSFCell cell = null;
			
			
			hrow =  (XSSFRow) wsheet.createRow(row);
			cell = hrow.createCell(col);
			cell.setCellStyle(sheetHeading);
			cell.setCellValue("Dispatch- MonthWise Report");
			wsheet.addMergedRegion(new CellRangeAddress(row, row, col, col + (heading1.length)-1));
			row++;
			
			
			hrow =  (XSSFRow) wsheet.createRow(row);
			cell = hrow.createCell(col);
			cell.setCellStyle(sheetHeading);
			cell.setCellValue("From  "+startdate+"  To  "+enddate);
			wsheet.addMergedRegion(new CellRangeAddress(row, row, col, col + (heading1.length)-1));
			row++;
			
			hrow =  (XSSFRow) wsheet.createRow(row);
			cell = hrow.createCell(col);
			cell.setCellStyle(columnHeading);
			cell.setCellValue("Division");
			col++;
			
			cell = hrow.createCell(col);
			cell.setCellStyle(columnHeading);
			cell.setCellValue("Product Group");
			col++;
			
			cell = hrow.createCell(col);
			cell.setCellStyle(columnHeading);
			cell.setCellValue("Product Name");
			col++;
			
			cell = hrow.createCell(col);
			cell.setCellStyle(columnHeading);
			cell.setCellValue("Nov");
			col++;
			
			cell = hrow.createCell(col);
			cell.setCellStyle(columnHeading);
			cell.setCellValue("Dec");
			col++;
			
			cell = hrow.createCell(col);
			cell.setCellStyle(columnHeading);
			cell.setCellValue("Jan");
			col++;
			
			cell = hrow.createCell(col);
			cell.setCellStyle(columnHeading);
			cell.setCellValue("Feb");
			col++;
			
			cell = hrow.createCell(col);
			cell.setCellStyle(columnHeading);
			cell.setCellValue("Mar");
			col++;
			
			cell = hrow.createCell(col);
			cell.setCellStyle(columnHeading);
			cell.setCellValue("Apr");
			col++;
			
			cell = hrow.createCell(col);
			cell.setCellStyle(columnHeading);
			cell.setCellValue("May");
			col++;
			
			cell = hrow.createCell(col);
			cell.setCellStyle(columnHeading);
			cell.setCellValue("Jun");
			col++;
			
			cell = hrow.createCell(col);
			cell.setCellStyle(columnHeading);
			cell.setCellValue("Jul");
			col++;
			
			cell = hrow.createCell(col);
			cell.setCellStyle(columnHeading);
			cell.setCellValue("Aug");
			col++;
			
			cell = hrow.createCell(col);
			cell.setCellStyle(columnHeading);
			cell.setCellValue("Sep");
			col++;
			
			cell = hrow.createCell(col);
			cell.setCellStyle(columnHeading);
			cell.setCellValue("Oct");
			col++;
			
			cell = hrow.createCell(col);
			cell.setCellStyle(columnHeading);
			cell.setCellValue("Total Dispatch Qty");
			col++;
			
			row ++ ;
			col =0;
			String old_div = "old";
			String new_div = "";
			
			String old_prod ="old";
			String new_prod = "";
			
			String old_prod_grp ="old";
			String new_prod_grp = "";
			
			boolean flg = false;
			int counter = 0;
			
			
			Map<String, BigDecimal> months=null; 
			Map<String, Map<String, BigDecimal>> fsmonthData=new LinkedHashMap<>();
			Map<String, Map<String, Map<String, BigDecimal>>> divData = new LinkedHashMap<>();
			
			
			BigDecimal total = new BigDecimal(0);
			
		//	lst.sort(Comparator.comparing(e -> e.getSmp_prod_id()));
			
			String sec_new_div = "";
			String sec_old_div = "old";
			
			for (Dispatch_alloc_monthwise_report data : lst) {
				
				sec_new_div = data.getDiv_disp_nm();
				if(divData.containsKey(data.getDiv_disp_nm())) {
					if(fsmonthData.containsKey(data.getSmp_prod_id())) {	
						months.put( data.getDsp_mthnm(), data.getDisp_qty());
						fsmonthData.put(data.getSmp_prod_id(), months);
						divData.put(data.getDiv_disp_nm(), fsmonthData);
						}
					else {
						months=new LinkedHashMap<>();
						months.put( data.getDsp_mthnm(), data.getDisp_qty());
						fsmonthData.put(data.getSmp_prod_id(), months);
						divData.put(data.getDiv_disp_nm(), fsmonthData);
					}
					
				}
				else {
					months=new LinkedHashMap<>();
					months.put( data.getDsp_mthnm(), data.getDisp_qty());
					fsmonthData = new LinkedHashMap<>();
					fsmonthData.put(data.getSmp_prod_id(), months);
					divData.put(data.getDiv_disp_nm(), fsmonthData);
				}
				
				sec_old_div = sec_new_div;
			}
			
			
		//	System.out.println("products :: "+ products);
		//	System.out.println("fsProdData :: "+ fsProdData);
		//	System.out.println("divData :: "+ divData);
			
			List<String[]> arrofmonth;
			
			String month_name= "";

			for(Dispatch_alloc_monthwise_report dsp:lst) {
		
				flg =false;
				new_div = dsp.getDiv_disp_nm();
				new_prod = dsp.getSmp_prod_name();
				new_prod_grp = dsp.getSa_group_name();
				
				if(!new_div.equalsIgnoreCase(old_div) || !new_prod.equalsIgnoreCase(old_prod) || !new_prod_grp.equalsIgnoreCase(old_prod_grp)) {
					

				if(!new_div.equalsIgnoreCase(old_div) && !old_prod.equalsIgnoreCase("old")) {
					
					hrow =  (XSSFRow) wsheet.createRow(row);
					col = 0;
					for(int j = 0 ; j <heading1.length;j++) {
						cell = hrow.createCell(col);
						//cell.setCellValue();
						cell.setCellStyle(styleblank1);
						col++;
					}
					col=0 ;
					row++;
					
					
					hrow =  (XSSFRow) wsheet.createRow(row);
					col = 0;
					for(int j = 0 ; j <heading1.length;j++) {
						cell = hrow.createCell(col);
						//cell.setCellValue();
						cell.setCellStyle(styleblank2);
						col++;
					}
					col=0 ;
					row++;
				
				}
					
				if(!new_div.equalsIgnoreCase(old_div) ) {
				//	System.out.println("in Div");
					hrow =  (XSSFRow) wsheet.createRow(row);
					
					
					cell = hrow.createCell(col);
					cell.setCellStyle(divprg);
					cell.setCellValue(dsp.getDiv_disp_nm());
					col++;
					
					cell = hrow.createCell(col);
					cell.setCellStyle(divprg);
					cell.setCellValue(dsp.getSa_group_name());
					col++;
					
					
					cell = hrow.createCell(col);
					cell.setCellStyle(prodcell);
					cell.setCellValue(dsp.getSmp_prod_name());
					col++;	
					flg = true;
					
				}			
				if(!new_prod_grp.equalsIgnoreCase(old_prod_grp) && !old_prod_grp.equalsIgnoreCase("old") && flg == false) {
				
					hrow =  (XSSFRow) wsheet.createRow(row);
					cell = hrow.createCell(col);
					cell.setCellStyle(prodgrpstyle);
					cell.setCellValue(" ");
					col++;
					
					cell = hrow.createCell(col);
					cell.setCellStyle(prodgrpstyle);
					cell.setCellValue(dsp.getSa_group_name());
					col++;
					
				//	System.out.println("in Prod");
					cell = hrow.createCell(col);
					cell.setCellStyle(prodcell);
					cell.setCellValue(dsp.getSmp_prod_name());
					col++;
					
					flg = true;
				}
	
				
				if(!new_prod.equalsIgnoreCase(old_prod) && !old_prod.equalsIgnoreCase("old") && flg == false) {
					hrow =  (XSSFRow) wsheet.createRow(row);
					cell = hrow.createCell(col);
					//cell.setCellStyle(columnHeading);
					cell.setCellValue(" ");
					col++;
					
					cell = hrow.createCell(col);
					//cell.setCellStyle(columnHeading);
					cell.setCellValue(" ");
					col++;
					
				//	System.out.println("in Prod");
					cell = hrow.createCell(col);
					cell.setCellStyle(prodcell);
					cell.setCellValue(dsp.getSmp_prod_name());
					col++;
					flg = true;
				}
				
				
				

						
				int cou = 0 ;
				BigDecimal val ;
				
				for(int j = 0 ; j <12;j++) {
					cell = hrow.createCell(col);
					//cell.setCellValue();
					cell.setCellStyle(decimalstyle2);
					col++;
				}
				
				
				Map<String, Map<String, BigDecimal>> divs=divData.get(dsp.getDiv_disp_nm());
				Map<String, BigDecimal> prods=divs.get(dsp.getSmp_prod_id());	

				for(int k=0;k<monthAry.length;k++)
				{
					for(Map.Entry<String, BigDecimal> d: prods.entrySet()) {
						month_name = d.getKey();
		
					if(month_name.trim().equalsIgnoreCase(monthAry[k].trim()))
					{
						 cou = prodmap.get(month_name);
						 val = d.getValue();
						
				//		System.out.println("cou :: "+ cou+"  val :: "+val);
						
							 cell = hrow.createCell(cou);
								cell.setCellValue(val.longValue());
								cell.setCellStyle(decimalstyle2);
								total = total.add(val);
						 
					
					//	col = cou;
					//	col++;
					}
					
					
					}
				
				}
				
				
				cell = hrow.createCell(col);
				cell.setCellValue(total.longValue());
				cell.setCellStyle(decimalstyle2);
				col++;
				 
				
				total = BigDecimal.ZERO;
				old_prod = new_prod;
				old_div = new_div;
				old_prod_grp = new_prod_grp;
				row++;
				col = 0;
				}
				
				
			}
		
			hrow =  (XSSFRow) wsheet.createRow(row);
			col = 0;
			for(int j = 0 ; j <heading1.length;j++) {
				cell = hrow.createCell(col);
				//cell.setCellValue();
				cell.setCellStyle(styleblank1);
				col++;
			}
			col=0 ;
			
			FileOutputStream fileOut = new FileOutputStream(path.toString());
			wwbook.write(fileOut);
			fileOut.close();
			
			System.out.println("Excel Created");
	}
	catch (Exception e) {
		// TODO: handle exception
		throw e;
	}
	return filename;
}
}


