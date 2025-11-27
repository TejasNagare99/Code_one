package com.excel.service;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintStream;
import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.streaming.SXSSFCell;
import org.apache.poi.xssf.streaming.SXSSFRow;
import org.apache.poi.xssf.streaming.SXSSFSheet;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.excel.bean.AuditTrailBean;
import com.excel.configuration.JwtProvider;
import com.excel.model.FieldMasterAttrib;
import com.excel.model.Usermaster;
import com.excel.repository.UserMasterRepository;
import com.excel.security.PasswordManager;
import com.excel.utility.ApachePoiExcelFormat;
import com.excel.utility.CodifyErrors;
import com.excel.utility.HeaderPageEvent;
import com.excel.utility.MedicoConstants;
import com.excel.utility.PdfStyle;
import com.excel.utility.Utility;
import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Rectangle;
import com.itextpdf.text.pdf.ColumnText;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;

import net.lingala.zip4j.core.ZipFile;
import net.lingala.zip4j.model.ZipParameters;
import net.lingala.zip4j.util.Zip4jConstants;

@Service
public class FieldMasterAudit_Report_ServiceImpl implements FieldMasterAudit_Report_Service{
	
	@Autowired
	private UserMasterRepository userMasterRepository;
	@Autowired private UserMasterService usermasterservice;

@Autowired private JwtProvider tokenProvider;
@Autowired private UserMasterService userMasterService;



	private String filename;
	private String user_name;
	
	
	
	
	final int limit=12;
	float[] bodyColWidth = new float[] { 0.8f, 0.6f, 1.0f,0.8f, 1.0f,    1.0f, 1.0f, 2.2f,1.0f,0.8f,    0.6f, 0.8f, 1.2f,0.8f,0.6f,	  0.8f  ,0.8f };
	
	
	
	//excel
	@Override
	public String getFieldMasterReport(List<FieldMasterAttrib> list,String companyname,String username,String empId) throws Exception {
		SXSSFWorkbook wwbook =null;
		File ff=null;
		try {
			String comp_name= companyname;
			long l = new Date().getTime();
			filename = "fieldAuditTrail_" + l+ ".xlsx" ;
		
			String filePath = MedicoConstants.REPORT_FILE_PATH ;
			
			StringBuffer path = null;
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
			ff = new File(path.toString());
			wwbook = new SXSSFWorkbook(1);
			SXSSFSheet wsheet = wwbook.createSheet("FieldMasterAudittrail");
			
			wsheet.createFreezePane(0, 3);
			ApachePoiExcelFormat xlsxExcelFormat = new ApachePoiExcelFormat();
			CellStyle sheetHeading = xlsxExcelFormat.SheetHeading(wwbook);
			CellStyle columnHeading = xlsxExcelFormat.columnHeading(wwbook);
			CellStyle leftAlign = xlsxExcelFormat.dataLeftAlign(wwbook);
			CellStyle leftAlignB = xlsxExcelFormat.dataBoldLeftAlign(wwbook);
	
			int col = 0;
			int row = 0;
			SXSSFRow hrow =  wsheet.createRow(row);
			SXSSFCell cell = hrow.createCell(col);
			cell.setCellStyle(sheetHeading);
			cell.setCellValue("Field Master Download");
			wsheet.addMergedRegion(new CellRangeAddress(row, row, col, col + 24));

			row++;

			hrow = wsheet.createRow(row);
			cell = hrow.createCell(col);
			cell.setCellStyle(sheetHeading);
			cell.setCellValue("Report Dated : "
					+ Utility.convertSmpDatetoString(new Date()));
			col++;
			cell = hrow.createCell(col);
			cell.setCellStyle(sheetHeading);
			cell.setCellValue("");
			wsheet.addMergedRegion(new CellRangeAddress(row, row, col, col + 23));
			col=col+21;
			col++;
			cell = hrow.createCell(col);
			cell.setCellStyle(sheetHeading);
			String userName=username;
			cell.setCellValue("User Name :"+userName);
			
			col=0;
			row++;
			
			hrow =  wsheet.createRow(row);

			cell = hrow.createCell(col);
			cell.setCellStyle(columnHeading);
			cell.setCellValue("Division Name");
			col++;

			cell = hrow.createCell(col);
			cell.setCellStyle(columnHeading);
			cell.setCellValue("Level Code");
			col++;

			cell = hrow.createCell(col);
			cell.setCellStyle(columnHeading);
			cell.setCellValue("FSTAFF_NAME");
			col++;

			cell = hrow.createCell(col);
			cell.setCellStyle(columnHeading);
			cell.setCellValue("FSTAFF_MAP_CODE1");
			col++;

			cell = hrow.createCell(col);
			cell.setCellStyle(columnHeading);
			cell.setCellValue("HQ_NAME");
			col++;

			cell = hrow.createCell(col);
			cell.setCellStyle(columnHeading);
			cell.setCellValue("FSTAFF_NAME_RM");
			col++;

			cell = hrow.createCell(col);
			cell.setCellStyle(columnHeading);
			cell.setCellValue("FSTAFF_NAME_AM");
			col++;

			cell = hrow.createCell(col);
			cell.setCellStyle(columnHeading);
			cell.setCellValue("FSTAFF_ADDR1");
			col++;

			cell = hrow.createCell(col);
			cell.setCellStyle(columnHeading);
			cell.setCellValue("FSTAFF_ADDR2");
			col++;

			cell = hrow.createCell(col);
			cell.setCellStyle(columnHeading);
			cell.setCellValue("FSTAFF_ADDR3");
			col++;

			cell = hrow.createCell(col);
			cell.setCellStyle(columnHeading);
			cell.setCellValue("FSTAFF_ADDR4");
			col++;

			cell = hrow.createCell(col);
			cell.setCellStyle(columnHeading);
			cell.setCellValue("MOBILE_NO");
			col++;
			
			cell = hrow.createCell(col);
			cell.setCellStyle(columnHeading);
			cell.setCellValue("FSTAFF_DESTINATION");
			col++;

			cell = hrow.createCell(col);
			cell.setCellStyle(columnHeading);
			cell.setCellValue("FSTAFF_MAP_CODE2");
			col++;

			cell = hrow.createCell(col);
			cell.setCellStyle(columnHeading);
			cell.setCellValue("FSTAFF_DISPLAY_NAME");
			col++;

			cell = hrow.createCell(col);
			cell.setCellStyle(columnHeading);
			cell.setCellValue("FSTAFF_DESIGNATION");
			col++;
			
			cell = hrow.createCell(col);
			cell.setCellStyle(columnHeading);
			cell.setCellValue("FSTAFF_ZONENAME");
			col++;

			cell = hrow.createCell(col);
			cell.setCellStyle(columnHeading);
			cell.setCellValue("FSTAFF_EMPLOYEE_NO");
			col++;

			cell = hrow.createCell(col);
			cell.setCellStyle(columnHeading);
			cell.setCellValue("STATUS");
			col++;
			
			cell = hrow.createCell(col);
			cell.setCellStyle(columnHeading);
			cell.setCellValue("INS_USR_NAME");
			col++;
			
			cell = hrow.createCell(col);
			cell.setCellStyle(columnHeading);
			cell.setCellValue("INS_DATE");
			col++;
			
			cell = hrow.createCell(col);
			cell.setCellStyle(columnHeading);
			cell.setCellValue("MOD_USR_NAME");
			col++;
			
			cell = hrow.createCell(col);
			cell.setCellStyle(columnHeading);
			cell.setCellValue("MOD_DATE");
			col++;

			cell = hrow.createCell(col);
			cell.setCellStyle(columnHeading);
			cell.setCellValue("CURR_STATUS");
			col++;
			
			cell = hrow.createCell(col);
			cell.setCellStyle(columnHeading);
			cell.setCellValue("Remark");
			col++;

				for(FieldMasterAttrib common: list){
				 
				row++;
				col = 0;
				hrow =  wsheet.createRow(row);
				
				if(common.getCurr_status().equalsIgnoreCase("CURRENT")){
			
				cell = hrow.createCell(col);
				cell.setCellStyle(leftAlignB);
				cell.setCellValue(common.getDivision());
				col++;
				
				cell = hrow.createCell(col);
				cell.setCellStyle(leftAlignB);
				cell.setCellValue(common.getFstaff_level_code());
				col++;
				
				cell = hrow.createCell(col);
				cell.setCellStyle(leftAlignB);
				cell.setCellValue(common.getFstaff_name());
				col++;
				}else{
					
					cell = hrow.createCell(col);
					cell.setCellStyle(leftAlign);
					cell.setCellValue(common.getDivision());
					col++;
					
					cell = hrow.createCell(col);
					cell.setCellStyle(leftAlign);
					cell.setCellValue(common.getFstaff_level_code());
					col++;
					
					cell = hrow.createCell(col);
					cell.setCellStyle(leftAlign);
					cell.setCellValue(common.getFstaff_name());
					col++;
					
					
				}
				
				cell = hrow.createCell(col);
				cell.setCellStyle(leftAlign);
				cell.setCellValue(common.getFstaff_map_code1());
				col++;
				
				cell = hrow.createCell(col);
				cell.setCellStyle(leftAlign);
				cell.setCellValue(common.getHq_name());
				col++;
				
				cell = hrow.createCell(col);
				cell.setCellStyle(leftAlign);
				cell.setCellValue(common.getRm());
				col++;
				
				cell = hrow.createCell(col);
				cell.setCellStyle(leftAlign);
				cell.setCellValue(common.getAm());
				col++;
				
				cell = hrow.createCell(col);
				cell.setCellStyle(leftAlign);
				cell.setCellValue(common.getFstaff_addr1());
				col++;
				
				cell = hrow.createCell(col);
				cell.setCellStyle(leftAlign);
				cell.setCellValue(common.getFstaff_addr2());
				col++;
				
				cell = hrow.createCell(col);
				cell.setCellStyle(leftAlign);
				cell.setCellValue(common.getFstaff_addr3());
				col++;
				
				cell = hrow.createCell(col);
				cell.setCellStyle(leftAlign);
				cell.setCellValue(common.getFstaff_addr4());
				col++;
				
				cell = hrow.createCell(col);
				cell.setCellStyle(leftAlign);
				cell.setCellValue(common.getFstaff_mobile());
				col++;
				
				cell = hrow.createCell(col);
				cell.setCellStyle(leftAlign);
				cell.setCellValue(common.getFstaff_destination());
				col++;
				
				cell = hrow.createCell(col);
				cell.setCellStyle(leftAlign);
				cell.setCellValue(common.getFstaff_map_code2());
				col++;
				
				cell = hrow.createCell(col);
				cell.setCellStyle(leftAlign);
				cell.setCellValue(common.getFstaff_display_name());
				col++;
				
				cell = hrow.createCell(col);
				cell.setCellStyle(leftAlign);
				cell.setCellValue(common.getFstaff_desig());
				col++;
				
				cell = hrow.createCell(col);
				cell.setCellStyle(leftAlign);
				cell.setCellValue(common.getFstaff_zonename());
				col++;
				
				cell = hrow.createCell(col);
				cell.setCellStyle(leftAlign);
				cell.setCellValue(common.getFstaff_employee_no());
				col++;
				
				cell = hrow.createCell(col);
				cell.setCellStyle(leftAlign);
				cell.setCellValue(common.getCurr_status());
				col++;
				cell = hrow.createCell(col);
				cell.setCellStyle(leftAlign);
				cell.setCellValue(common.getIns_user_name());
				col++;
				
				cell = hrow.createCell(col);
				cell.setCellStyle(leftAlign);
				cell.setCellValue(common.getFstaff_ins_dt());
				col++;
				
				cell = hrow.createCell(col);
				cell.setCellStyle(leftAlign);
				cell.setCellValue(common.getMod_user_name());
				col++;
				
				cell = hrow.createCell(col);
				cell.setCellStyle(leftAlign);
				cell.setCellValue(common.getFstaff_mod_dt());
				col++;
				
				cell = hrow.createCell(col);
				cell.setCellStyle(leftAlign);
				cell.setCellValue(common.getCurr_status());
				col++;
				
				
				cell = hrow.createCell(col);
				cell.setCellStyle(leftAlign);
				cell.setCellValue(common.getRemarks());								//Remark
				col++;
				
				
			}
			
			FileOutputStream fileOut = new FileOutputStream(ff);
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

	
	//pdf
	@Override
	public String getFieldMasterPrint(List<FieldMasterAttrib> list,String companyname,String username,AuditTrailBean bean,HttpSession session,HttpServletRequest request) throws Exception {
		
		String h_path;
		String today = Utility.convertSmpDatetoString(new Date());
		
		h_path = MedicoConstants.PDF_FILE_PATH;
		String comp_name = companyname;
	
		user_name = username;
		
		FileOutputStream os = null;
		PrintStream ps = null;
		long timevar = new Date().getTime();
		filename = "FieldAudTrail" + timevar + ".pdf";
		StringBuffer path = null;
		 path = new StringBuffer(h_path).append("\\");
			path.append("files\\" +filename);
		File file1 = new File(h_path);
		try {
			if (!file1.exists()) {
				if (file1.mkdirs()) {
				} else {
					throw new RuntimeException("Could not create directory");
				}
			}
			} catch (Exception e) {
				throw new RuntimeException();
			}
		File docfile = new File(path.toString());
		OutputStream file = new FileOutputStream(docfile);
		Document document = new Document(PageSize.A4.rotate());
		PdfWriter writer = PdfWriter.getInstance(document, file);
		Rectangle rect = new Rectangle(20, 20, 825, 580);
		writer.setBoxSize("header", rect);
		String uname = this.tokenProvider.getUsernameFromRequest(request);String empId = this.userMasterService.getEmpIdByUsername(uname);
		 writer = usermasterservice.generatePDFlock(empId, writer);
		HeaderPageEvent event = new HeaderPageEvent();
		writer.setPageEvent(event);
		document.open();
		boolean isnew = false;
		ColumnText column = new ColumnText(writer.getDirectContent());
		try {			
			PdfPTable bodytable = null;
			String old_div="", new_div="";			
			int rows = 0;
			isnew = true;			
			
			for (FieldMasterAttrib dataList : list) {	
				new_div=dataList.getDivision();
				
				if (rows >= limit) {
					bodytable = newPage(column, bodytable, rows);
					document.newPage();
					rows = 0;
				}

				if (isnew) {
					event.setHeader(createHeader(comp_name,user_name,today,rows));
					isnew = false;
					bodytable = new PdfPTable(17);
					bodytable.setWidthPercentage(100);
					bodytable.setWidths(bodyColWidth);					
				}
								
				if(!old_div.equalsIgnoreCase(new_div)){
					bodytable.addCell(PdfStyle.createDatachangeCell("Division :     "+new_div, 17, BaseColor.BLUE));//16
					rows++;
				}
				old_div=new_div;
				 StringBuilder result1 = new StringBuilder();
				    result1.append(dataList.getFstaff_addr1());
				    result1.append(" ");
				 
				    result1.append(dataList.getFstaff_addr2());
				    result1.append(" ");
				   
				    result1.append(dataList.getFstaff_addr3());
				    result1.append(" ");
				  
				    result1.append(dataList.getFstaff_addr4());
				    
				if(dataList.getCurr_status().equalsIgnoreCase("CURRENT"))
				{	
				bodytable.addCell(PdfStyle.createValueCellWithHeightBoldTopBorder(dataList.getDivision(), false, true, 20));
				bodytable.addCell(PdfStyle.createValueCellWithHeightBoldTopBorder(dataList.getFstaff_level_code(), false, true, 20));
				bodytable.addCell(PdfStyle.createValueCellWithHeightBoldTopBorder(dataList.getFstaff_name(), false, true, 20));
				bodytable.addCell(PdfStyle.createValueCellWithHeightBoldTopBorder(dataList.getFstaff_map_code1(), false, true, 20));
				bodytable.addCell(PdfStyle.createValueCellWithHeightBoldTopBorder(dataList.getHq_name(), false, true, 20));
				
				bodytable.addCell(PdfStyle.createValueCellWithHeightBoldTopBorder(dataList.getRm(), false, true, 20));
				bodytable.addCell(PdfStyle.createValueCellWithHeightBoldTopBorder(dataList.getAm(), false, true, 20));
				bodytable.addCell(PdfStyle.createValueCellWithHeightBoldTopBorder(result1.toString().length()>65?result1.toString().substring(0, 66):result1.toString(), false, true, 20));
			
				bodytable.addCell(PdfStyle.createValueCellWithHeightBoldTopBorder(dataList.getFstaff_mobile(), false, true, 20));
				bodytable.addCell(PdfStyle.createValueCellWithHeightBoldTopBorder(dataList.getFstaff_destination(), false, true, 20));
				bodytable.addCell(PdfStyle.createValueCellWithHeightBoldTopBorder(dataList.getFstaff_map_code2(), false, true, 20));
				bodytable.addCell(PdfStyle.createValueCellWithHeightBoldTopBorder(dataList.getFstaff_display_name(), false, true, 20));
				
				
				bodytable.addCell(PdfStyle.createValueCellWithHeightBoldTopBorder(dataList.getFstaff_desig(), false, true, 20));
				bodytable.addCell(PdfStyle.createValueCellWithHeightBoldTopBorder(dataList.getFstaff_zonename(), false, true, 20));
				bodytable.addCell(PdfStyle.createValueCellWithHeightBoldTopBorder(dataList.getFstaff_employee_no(), false, true, 20));
				bodytable.addCell(PdfStyle.createValueCellWithHeightBoldTopBorder(dataList.getFstaff_status(), false, true, 20));

				bodytable.addCell(PdfStyle.createValueCellWithHeightBoldTopBorder(dataList.getRemarks(), false, true, 20));
				


				
				
				rows++;
				//---------------------------------------------------------------------------------------------------------------
				
				
				
				bodytable.addCell(PdfStyle.createValueCellWithHeight("", false, true, 20));
				
				bodytable.addCell(PdfStyle.createValueCellWithHeight("", false, true, 20));
				bodytable.addCell(PdfStyle.createValueCellWithHeight("", false, true, 20));
				bodytable.addCell(PdfStyle.createValueCellWithHeight("", false, true, 20));
				bodytable.addCell(PdfStyle.createValueCellWithHeight("", false, true, 20));
				bodytable.addCell(PdfStyle.createValueCellWithHeight("", false, true, 20));
				bodytable.addCell(PdfStyle.createValueCellWithHeight("", false, true, 20));
				bodytable.addCell(PdfStyle.createValueCellWithHeight("", false, true, 20));
				bodytable.addCell(PdfStyle.createValueCellWithHeight("", false, true, 20));
				bodytable.addCell(PdfStyle.createValueCellWithHeight("", false, true, 20));
				bodytable.addCell(PdfStyle.createValueCellWithHeight("", false, true, 20));
				bodytable.addCell(PdfStyle.createValueCellWithHeight("", false, true, 20));
				bodytable.addCell(PdfStyle.createValueCellWithHeight("Inserted", false, true, 20));
				bodytable.addCell(PdfStyle.createValueCellWithHeight(dataList.getIns_user_name(), false, true, 20));
				bodytable.addCell(PdfStyle.createValueCellWithHeight(dataList.getFstaff_ins_dt(), false, true, 20));
				bodytable.addCell(PdfStyle.createValueCellWithHeight(dataList.getFstaff_ins_ip_add(), false, true, 20));//16
				
				bodytable.addCell(PdfStyle.createValueCellWithHeight("", false, true, 20));
				
				rows++;
				}
				if(dataList.getCurr_status().equalsIgnoreCase("LOG"))
				{
					bodytable.addCell(PdfStyle.createValueCellWithHeightBoldTopBorder(dataList.getDivision(), false, true, 20));
					bodytable.addCell(PdfStyle.createValueCellWithHeightBoldTopBorder(dataList.getFstaff_level_code(), false, true, 20));
					bodytable.addCell(PdfStyle.createValueCellWithHeightBoldTopBorder(dataList.getFstaff_name(), false, true, 20));
					bodytable.addCell(PdfStyle.createValueCellWithHeightBoldTopBorder(dataList.getFstaff_map_code1(), false, true, 20));
					bodytable.addCell(PdfStyle.createValueCellWithHeightBoldTopBorder(dataList.getHq_name(), false, true, 20));
					
					bodytable.addCell(PdfStyle.createValueCellWithHeightBoldTopBorder(dataList.getRm(), false, true, 20));
					bodytable.addCell(PdfStyle.createValueCellWithHeightBoldTopBorder(dataList.getAm(), false, true, 20));
					bodytable.addCell(PdfStyle.createValueCellWithHeightBoldTopBorder(result1.toString().substring(0, 66), false, true, 20));
					
					bodytable.addCell(PdfStyle.createValueCellWithHeightBoldTopBorder(dataList.getFstaff_mobile(), false, true, 20));
					bodytable.addCell(PdfStyle.createValueCellWithHeightBoldTopBorder(dataList.getFstaff_destination(), false, true, 20));
					bodytable.addCell(PdfStyle.createValueCellWithHeightBoldTopBorder(dataList.getFstaff_map_code2(), false, true, 20));
					bodytable.addCell(PdfStyle.createValueCellWithHeightBoldTopBorder(dataList.getFstaff_display_name(), false, true, 20));
					
					
					bodytable.addCell(PdfStyle.createValueCellWithHeightBoldTopBorder(dataList.getFstaff_desig(), false, true, 20));
					bodytable.addCell(PdfStyle.createValueCellWithHeightBoldTopBorder(dataList.getFstaff_zonename(), false, true, 20));
					bodytable.addCell(PdfStyle.createValueCellWithHeightBoldTopBorder(dataList.getFstaff_employee_no(), false, true, 20));
					bodytable.addCell(PdfStyle.createValueCellWithHeightBoldTopBorder(dataList.getCurr_status(), false, true, 20));

					
					rows++;
					
					
					bodytable.addCell(PdfStyle.createValueCellWithHeight("", false, true, 20));
					
					bodytable.addCell(PdfStyle.createValueCellWithHeight("", false, true, 20));
					bodytable.addCell(PdfStyle.createValueCellWithHeight("", false, true, 20));
					bodytable.addCell(PdfStyle.createValueCellWithHeight("", false, true, 20));
					bodytable.addCell(PdfStyle.createValueCellWithHeight("", false, true, 20));
					bodytable.addCell(PdfStyle.createValueCellWithHeight("", false, true, 20));
					bodytable.addCell(PdfStyle.createValueCellWithHeight("", false, true, 20));
					bodytable.addCell(PdfStyle.createValueCellWithHeight("", false, true, 20));
					bodytable.addCell(PdfStyle.createValueCellWithHeight("", false, true, 20));
					bodytable.addCell(PdfStyle.createValueCellWithHeight("", false, true, 20));
					bodytable.addCell(PdfStyle.createValueCellWithHeight("", false, true, 20));
					bodytable.addCell(PdfStyle.createValueCellWithHeight("", false, true, 20));
					bodytable.addCell(PdfStyle.createValueCellWithHeight("Modified", false, true, 20));
					bodytable.addCell(PdfStyle.createValueCellWithHeight(dataList.getMod_user_name(), false, true, 20));
					bodytable.addCell(PdfStyle.createValueCellWithHeight(dataList.getFstaff_mod_dt(), false, true, 20));
					bodytable.addCell(PdfStyle.createValueCellWithHeight(dataList.getFstaff_mod_ip_add(), false, true, 20));
					
					bodytable.addCell(PdfStyle.createValueCellWithHeight("", false, true, 20));//17
					rows++;
				}
				
				 
			}
			newPage(column, bodytable, rows);
			document.newPage();
			
		} catch (Exception e) {
			throw e;
		} finally {
			try {
				if (document != null) {
					document.close();
				}
				if (file != null) {
					file.close();
				}
				if (os != null) {
					os.close();
				}
				if (ps != null) {
					ps.close();
				}
			} catch (Exception e) {
				System.out.println("Error Occurred :" +e.getMessage());// uncomment asneeded --;
			}
		}
		
		
		System.out.println("PATH$%#$#$$$$#$$" + path + "\\files\\" + filename);
		return filename;
	
	}
	
	private PdfPTable createHeader(String comp_name, String username, String date, int rows)
			throws DocumentException, MalformedURLException, IOException {
		
			PdfPTable header = new PdfPTable(1);  //main header table
			header.getDefaultCell().setPadding(0.0f);
			header.setWidthPercentage(100);
			
			PdfPCell title_1 = PdfStyle.createUltimateCellWithoutBorder(comp_name, 1, 5, 12, 18, false);
			PdfPCell title_2 = PdfStyle.createUltimateCellWithoutBorder("Field Master Audit Trail", 1, 5, 12, 18, false);
			header.addCell(title_1);
			header.addCell(title_2);
			
			PdfPTable h1 = new PdfPTable(3);
			h1.setWidthPercentage(100); 
			h1.setWidths(new float[] {5.4f, 5.4f, 5.4f}); 
			h1.addCell(PdfStyle.createUltimateCellWithoutBorder("Report Dated : "+date,0,5,9,18,false));
			h1.addCell(PdfStyle.createUltimateCellWithoutBorder("",1,5,9,18,false));
			h1.addCell(PdfStyle.createUltimateCellWithoutBorder("User : "+username,2,5,9,18,false));			
			
			PdfPTable bodyHeader = new PdfPTable(17);  // set body column name
			bodyHeader.setWidthPercentage(100);
			bodyHeader.setWidths(this.bodyColWidth);
			
			bodyHeader.addCell(PdfStyle.createLabelCellWithBGColor("Div Name",0.7f));
			bodyHeader.addCell(PdfStyle.createLabelCellWithBGColor("Level Code",0.7f));
			bodyHeader.addCell(PdfStyle.createLabelCellWithBGColor("FStaff Name",0.7f));
			bodyHeader.addCell(PdfStyle.createLabelCellWithBGColor("Map code1",0.7f));
			bodyHeader.addCell(PdfStyle.createLabelCellWithBGColor("Hq Name",0.7f));
			
			bodyHeader.addCell(PdfStyle.createLabelCellWithBGColor("RM Name",0.7f));
			bodyHeader.addCell(PdfStyle.createLabelCellWithBGColor("AM Name",0.7f));
			bodyHeader.addCell(PdfStyle.createLabelCellWithBGColor("FStaff Address",0.7f));
			
			bodyHeader.addCell(PdfStyle.createLabelCellWithBGColor("Mobile no",0.7f));
			bodyHeader.addCell(PdfStyle.createLabelCellWithBGColor("FStaff dest",0.7f));
			bodyHeader.addCell(PdfStyle.createLabelCellWithBGColor("Map code2",0.7f));
			bodyHeader.addCell(PdfStyle.createLabelCellWithBGColor("Disp Name",0.7f));

			bodyHeader.addCell(PdfStyle.createLabelCellWithBGColor("FStaff desig",0.7f));
			bodyHeader.addCell(PdfStyle.createLabelCellWithBGColor("FStaff Zone",0.7f));
			bodyHeader.addCell(PdfStyle.createLabelCellWithBGColor("FStaff emp no",0.7f));
			bodyHeader.addCell(PdfStyle.createLabelCellWithBGColor("Status",0.7f));
			
			bodyHeader.addCell(PdfStyle.createLabelCellWithBGColor("Remark",0.7f));//17
			
			//---------------------------------------------------------------------

			
			bodyHeader.addCell(PdfStyle.createLabelCellWithBGColor("",0.7f));
			bodyHeader.addCell(PdfStyle.createLabelCellWithBGColor("",0.7f));
			bodyHeader.addCell(PdfStyle.createLabelCellWithBGColor("",0.7f));
			bodyHeader.addCell(PdfStyle.createLabelCellWithBGColor("",0.7f));
			bodyHeader.addCell(PdfStyle.createLabelCellWithBGColor("",0.7f));
			
			bodyHeader.addCell(PdfStyle.createLabelCellWithBGColor("",0.7f));	
			bodyHeader.addCell(PdfStyle.createLabelCellWithBGColor("",0.7f));
			bodyHeader.addCell(PdfStyle.createLabelCellWithBGColor("",0.7f));
			bodyHeader.addCell(PdfStyle.createLabelCellWithBGColor("",0.7f));
			bodyHeader.addCell(PdfStyle.createLabelCellWithBGColor("",0.7f));

			bodyHeader.addCell(PdfStyle.createLabelCellWithBGColor("",0.7f));
			bodyHeader.addCell(PdfStyle.createLabelCellWithBGColor("",0.7f));
			bodyHeader.addCell(PdfStyle.createLabelCellWithBGColor("",0.7f));

			bodyHeader.addCell(PdfStyle.createLabelCellWithBGColor("Username",0.7f));
			bodyHeader.addCell(PdfStyle.createLabelCellWithBGColor("Date",0.7f));
			bodyHeader.addCell(PdfStyle.createLabelCellWithBGColor("FStaff ip addr",0.7f));
			
			bodyHeader.addCell(PdfStyle.createLabelCellWithBGColor("",0.7f));
	
			
			rows++;
			
			PdfPCell c1 = new PdfPCell(h1);
			c1.setBorder(0);
			header.addCell(c1);
			header.addCell(bodyHeader);
			
			return header;
	}


	public PdfPTable newPage(ColumnText column, PdfPTable bodytable, int rows) throws Exception {
		try {
			PdfPCell cell = new PdfPCell();
			column.setSimpleColumn(20, 20, 825, 505-27);
			
			cell=new PdfPCell(PdfStyle.createSingleLine());
			cell.setColspan(17);		//16
			bodytable.addCell(cell);
			
			column.addElement(bodytable);
			column.go();
			bodytable = new PdfPTable(17);//16
			bodytable.setWidthPercentage(100);
			bodytable.setWidths(this.bodyColWidth);

		} catch (DocumentException e) {
			throw e;
		}
		return bodytable;
	}
	
}