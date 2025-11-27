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

import com.excel.bean.AuditTrailBean;
import com.excel.configuration.JwtProvider;
import com.excel.model.DispatchAuditTrailBean;
import com.excel.model.Usermaster;
import com.excel.repository.UserMasterRepository;
import com.excel.security.PasswordManager;
import com.excel.utility.ApachePoiExcelFormat;
import com.excel.utility.CodifyErrors;
import com.excel.utility.HeaderPageEvent;
import com.excel.utility.MedicoConstants;
import com.excel.utility.PdfStyle;
import com.excel.utility.Utility;
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
public class DispatchAuditDownloadServiceImpl implements DispatchAuditDownloadService{
	@Autowired
	private UserMasterRepository usermasterrepository;
	@Autowired private UserMasterService usermasterservice;
	@Autowired private JwtProvider tokenProvider;
	@Autowired private UserMasterService userMasterService;


	final private int limit=16;
	float[] bodyColWidth = new float[] { 1.5f, 3.0f, 1.5f, 1.5f, 1.5f, 1.5f, 1.5f, 1.0f, 1.0f, 1.1f, 1.7f, 1.5f, 1.7f};
	
	@Override
	public String GenerateDispatchAuditDownload_Report(AuditTrailBean bean, List<DispatchAuditTrailBean> DataList,
			String companyname, String username, String fromdate, String todate,String empId) throws Exception {
		SXSSFWorkbook wwbook =null;
		StringBuffer path = null;
		//File ff=null;
		 String fileName=null;
		try {
			String comp_name =companyname;
			//Iterator<GrnAuditTrailBean> it = DataList.iterator();
			long l = new Date().getTime();
			fileName = "dispatchAuditTrail_" + new Date().getTime() + ".xlsx";
			String filePath = MedicoConstants.REPORT_FILE_PATH;
		    path = new StringBuffer(filePath).append("\\");
			path.append(fileName);

			//ff = new File(filePath);
			wwbook = new SXSSFWorkbook(1);
			SXSSFSheet wsheet = wwbook.createSheet("DispatchAuditTrail");
			wsheet.createFreezePane(0, 4);
			ApachePoiExcelFormat xlsxExcelFormat = new ApachePoiExcelFormat();
			CellStyle sheetHeading = xlsxExcelFormat.SheetHeading(wwbook);
			 
			//CellStyle columnSubHeading = xlsxExcelFormat.columnSubHeading(wwbook);
			CellStyle columnHeading = xlsxExcelFormat.columnHeading(wwbook);
			//CellStyle rightAlign = xlsxExcelFormat.dataRightAlignWthDecimal(wwbook);
			CellStyle leftAlign = xlsxExcelFormat.dataLeftAlign(wwbook);
			
			CellStyle columnHeadingLemon = xlsxExcelFormat.columnSubHeadingLemon(wwbook);
			//CellStyle rightAlignB = xlsxExcelFormat.dataBoldRightAlign(wwbook);
			CellStyle leftAlignB = xlsxExcelFormat.dataBoldLeftAlign(wwbook);
			//CellStyle rightAlignWthDecimalB = xlsxExcelFormat.dataBoldRightAlignWthDecimal(wwbook);
			CellStyle rightAlignWthDecimal = xlsxExcelFormat
					.dataRightAlignWthDecimal(wwbook);
			int col = 0, row = 0;
			SXSSFRow hrow =  wsheet.createRow(row);
			SXSSFCell cell = hrow.createCell(col);
			cell.setCellStyle(sheetHeading);
			String userName=username;
			cell.setCellValue("User Name :"+userName);
			wsheet.addMergedRegion(new CellRangeAddress(row, row, col, col + 16-2));

			row++;
		
			
			hrow = wsheet.createRow(row);
			cell = hrow.createCell(col);
			cell.setCellStyle(sheetHeading);
			cell.setCellValue("Report Dated : "	+ Utility.convertDatetoString(new Date()));
			wsheet.addMergedRegion(new CellRangeAddress(row, row, col, col + 16-2));
			row++;
			
			hrow =  wsheet.createRow(row);
			cell = hrow.createCell(col);
			cell.setCellStyle(sheetHeading);
			cell.setCellValue("Date : From  "
					+ fromdate +" To " +todate);
			wsheet.addMergedRegion(new CellRangeAddress(row, row, col, col + 16-2));
			row++;
			
			hrow = wsheet.createRow(row);
			 
			cell = hrow.createCell(col);
			cell.setCellStyle(columnHeading);
			cell.setCellValue("Location");
			col++;
			cell = hrow.createCell(col);
			cell.setCellStyle(columnHeading);
			cell.setCellValue("Dispatch No.");
			col++;
			cell = hrow.createCell(col);
			cell.setCellStyle(columnHeading);
			cell.setCellValue("Dispatch Date");
			col++;
			cell = hrow.createCell(col);
			cell.setCellStyle(columnHeading);
			cell.setCellValue("Field Staff Name");
			col++;
			cell = hrow.createCell(col);
			cell.setCellStyle(columnHeading);
			cell.setCellValue("LR No");
			col++;
			cell = hrow.createCell(col);
			cell.setCellStyle(columnHeading);
			cell.setCellValue("LR Date");
			col++;
			cell = hrow.createCell(col);
			cell.setCellStyle(columnHeading);
			cell.setCellValue("Status");
			col++;
			cell = hrow.createCell(col);
			cell.setCellStyle(columnHeading);
			cell.setCellValue("Challan No");
			col++;
			cell = hrow.createCell(col);
			cell.setCellStyle(columnHeading);
			cell.setCellValue("Challan Date");
			col++;			
			
			/*cell = hrow.createCell(col);
			cell.setCellStyle(columnHeading);
			cell.setCellValue("DSP_ins_usr_id");
			col++;*/
			
			cell = hrow.createCell(col);
			cell.setCellStyle(columnHeading);
			cell.setCellValue("INS_NAME");
			col++;
			
			cell = hrow.createCell(col);
			cell.setCellStyle(columnHeading);
			cell.setCellValue("DSP_INS_DT");
			col++;
			
			cell = hrow.createCell(col);
			cell.setCellStyle(columnHeading);
			cell.setCellValue("DSP_ins_ip_add");
			col++;
			
			/*cell = hrow.createCell(col);
			cell.setCellStyle(columnHeading);
			cell.setCellValue("DSP_MOD_usr_id");
			col++;*/
			
			cell = hrow.createCell(col);
			cell.setCellStyle(columnHeading);
			cell.setCellValue("MOD_NAME");
			col++;
			
			cell = hrow.createCell(col);
			cell.setCellStyle(columnHeading);
			cell.setCellValue("DSP_MOD_DT");
			col++;
			
			cell = hrow.createCell(col);
			cell.setCellStyle(columnHeading);
			cell.setCellValue("DSP_MOD_ip_add");
			col++;


			String old_dsp_no="";
			String new_dsp_no="";

			row++;
			 for (int i = 0; i < DataList.size(); i++) {
				 
				 
				 new_dsp_no=DataList.get(i).getDSP_NO();
					//System.out.println("old_dsp_no " +old_dsp_no+" new_dsp_no " +new_dsp_no);
					if(!old_dsp_no.equals(new_dsp_no)){
						
						//Printing Header part Here
						col = 0;

						hrow = wsheet.createRow(row);

						cell = hrow.createCell(col);
						cell.setCellStyle(leftAlignB);
						cell.setCellValue(DataList.get(i).getLOC_NM());
						col++;
						
						cell = hrow.createCell(col);
						cell.setCellStyle(leftAlignB);
						cell.setCellValue(new_dsp_no);
						col++;
						
						cell = hrow.createCell(col);
						cell.setCellStyle(leftAlignB);
						cell.setCellValue(DataList.get(i).getDSP_DT());
						col++;
						
						cell = hrow.createCell(col);
						cell.setCellStyle(leftAlignB);
						cell.setCellValue(DataList.get(i).getFSTAFF_NAME());
						col++;
						
						cell = hrow.createCell(col);
						cell.setCellStyle(leftAlignB);
						cell.setCellValue(DataList.get(i).getDSP_LR_NO());
						col++;
						
						cell = hrow.createCell(col);
						cell.setCellStyle(leftAlignB);
						cell.setCellValue(DataList.get(i).getDSP_LR_DT());
						col++;
						
						cell = hrow.createCell(col);
						cell.setCellStyle(leftAlignB);
						cell.setCellValue(DataList.get(i).getDSP_APPR_STATUS());
						col++;
						
						cell = hrow.createCell(col);
						cell.setCellStyle(leftAlignB);
						cell.setCellValue(DataList.get(i).getDSP_CHALLAN_NO());
						col++;
						
						cell = hrow.createCell(col);
						cell.setCellStyle(leftAlignB);
						cell.setCellValue(DataList.get(i).getDSP_CHALLAN_DT());
						col++;
						
						
						

					
						
						
					/*	cell = hrow.createCell(col);
						cell.setCellStyle(leftAlignB);
						cell.setCellValue(DataList.get(i).getDSP_ins_usr_id());
						col++;*/
						cell = hrow.createCell(col);
						cell.setCellStyle(leftAlignB);
						cell.setCellValue(DataList.get(i).getINS_NAME());
						col++;
						cell = hrow.createCell(col);
						cell.setCellStyle(leftAlignB);
						cell.setCellValue(DataList.get(i).getDSP_INS_DT());
						col++;
						cell = hrow.createCell(col);
						cell.setCellStyle(leftAlignB);
						cell.setCellValue(DataList.get(i).getDSP_INS_IP_ADD());
						col++;
						
						
						
						
						
						
						
					/*	
						cell = hrow.createCell(col);
						cell.setCellStyle(leftAlignB);
						cell.setCellValue(DataList.get(i).getDSP_MOD_usr_id());
						col++;*/
						cell = hrow.createCell(col);
						cell.setCellStyle(leftAlignB);
						cell.setCellValue(DataList.get(i).getMOD_NAME());
						col++;
						cell = hrow.createCell(col);
						cell.setCellStyle(leftAlignB);
						cell.setCellValue(DataList.get(i).getDSP_MOD_DT());
						col++;
						cell = hrow.createCell(col);
						cell.setCellStyle(leftAlignB);
						cell.setCellValue(DataList.get(i).getDSP_MOD_IP_ADD());
						col++;
						
						

						row++;
						col=0;
						
	
						//Printing Detail's Header part
						
						hrow = wsheet.createRow(row);
						 
						cell = hrow.createCell(col);
						cell.setCellStyle(columnHeadingLemon);
						cell.setCellValue(" ");
						col++;
						cell = hrow.createCell(col);
						cell.setCellStyle(columnHeadingLemon);
						cell.setCellValue(" ");
						col++;
						cell = hrow.createCell(col);
						cell.setCellStyle(columnHeadingLemon);
						cell.setCellValue("Item code");
						col++;
						cell = hrow.createCell(col);
						cell.setCellStyle(columnHeadingLemon);
						cell.setCellValue("Item description");
						col++;
						cell = hrow.createCell(col);
						cell.setCellStyle(columnHeadingLemon);
						cell.setCellValue("Batch/Artwork No.");
						col++;
						
						
						cell = hrow.createCell(col);
						cell.setCellStyle(columnHeadingLemon);
						cell.setCellValue("Qty");
						col++;
						cell = hrow.createCell(col);
						cell.setCellStyle(columnHeadingLemon);
						cell.setCellValue("Rate");
						col++;
						cell = hrow.createCell(col);
						cell.setCellStyle(columnHeadingLemon);
						cell.setCellValue("Value");
						col++;
						cell = hrow.createCell(col);
						cell.setCellStyle(columnHeadingLemon);
						cell.setCellValue("Remarks");
						col++;					
					
						cell = hrow.createCell(col);
						cell.setCellStyle(columnHeadingLemon);
						cell.setCellValue("User");
						col++;
						cell = hrow.createCell(col);
						cell.setCellStyle(columnHeadingLemon);
						cell.setCellValue("Date & Time");
						col++;
						cell = hrow.createCell(col);
						cell.setCellStyle(columnHeadingLemon);
						cell.setCellValue("Status");
						col++;
						cell = hrow.createCell(col);
						cell.setCellStyle(columnHeadingLemon);
						cell.setCellValue("Remarks");
						col++;

						cell = hrow.createCell(col);
						cell.setCellStyle(columnHeadingLemon);
						cell.setCellValue("Expiry Date");
						col++;
						cell = hrow.createCell(col);
						cell.setCellStyle(columnHeadingLemon);
						cell.setCellValue(" ");
						col++;
						
						
						row++;
						//=========================================================================================
						

						//Printing Detail Part Here
						col = 0;

						hrow = wsheet.createRow(row);
						
						
						cell = hrow.createCell(col);
						cell.setCellStyle(leftAlign);
						cell.setCellValue("");
						col++;
						
						cell = hrow.createCell(col);
						cell.setCellStyle(leftAlign);
						cell.setCellValue("");
						col++;

						cell = hrow.createCell(col);
						cell.setCellStyle(leftAlign);
						cell.setCellValue(DataList.get(i).getSMP_PROD_CD());
						col++;
						
						cell = hrow.createCell(col);
						cell.setCellStyle(leftAlign);
						cell.setCellValue(DataList.get(i).getSMP_PROD_NAME());
						col++;
						
						cell = hrow.createCell(col);
						cell.setCellStyle(leftAlign);
						cell.setCellValue(DataList.get(i).getBATCH_NO());
						col++;
						
						cell = hrow.createCell(col);
						cell.setCellStyle(rightAlignWthDecimal);
						cell.setCellValue(DataList.get(i).getDSPDTL_DISP_QTY().doubleValue());
						col++;
						
						cell = hrow.createCell(col);
						cell.setCellStyle(rightAlignWthDecimal);
						cell.setCellValue(DataList.get(i).getDSPDTL_RATE().doubleValue());
						col++;
						
						cell = hrow.createCell(col);
						cell.setCellStyle(rightAlignWthDecimal);
						cell.setCellValue(DataList.get(i).getDSP_VALUE().doubleValue());
						col++;
						
						cell = hrow.createCell(col);
						cell.setCellStyle(leftAlign);
						cell.setCellValue(DataList.get(i).getREMARKS());
						col++;
				
						
						
						cell = hrow.createCell(col);
						cell.setCellStyle(leftAlign);
						cell.setCellValue(DataList.get(i).getAPPR_USER_NAME());
						col++;
						
						cell = hrow.createCell(col);
						cell.setCellStyle(leftAlign);
						cell.setCellValue(DataList.get(i).getAPPR_DATE());
						col++;
						
						cell = hrow.createCell(col);
						cell.setCellStyle(leftAlign);
						cell.setCellValue(DataList.get(i).getSTATUS());
						col++;
						
						cell = hrow.createCell(col);
						cell.setCellStyle(leftAlign);
						cell.setCellValue(DataList.get(i).getAPPR_REMARKS());
						col++;
						
						cell = hrow.createCell(col);
						cell.setCellStyle(leftAlign);
						cell.setCellValue(DataList.get(i).getBATCH_EXPIRY_DT());
						col++;
						
						row++;
						
				}else{
					//Printing Detail Part Here
					col = 0;

					hrow = wsheet.createRow(row);
					
					
					cell = hrow.createCell(col);
					cell.setCellStyle(leftAlign);
					cell.setCellValue("");
					col++;
					
					cell = hrow.createCell(col);
					cell.setCellStyle(leftAlign);
					cell.setCellValue("");
					col++;

					cell = hrow.createCell(col);
					cell.setCellStyle(leftAlign);
					cell.setCellValue(DataList.get(i).getSMP_PROD_CD());
					col++;
					
					cell = hrow.createCell(col);
					cell.setCellStyle(leftAlign);
					cell.setCellValue(DataList.get(i).getSMP_PROD_NAME());
					col++;
					
					cell = hrow.createCell(col);
					cell.setCellStyle(leftAlign);
					cell.setCellValue(DataList.get(i).getBATCH_NO());
					col++;
					
					cell = hrow.createCell(col);
					cell.setCellStyle(rightAlignWthDecimal);
					cell.setCellValue(DataList.get(i).getDSPDTL_DISP_QTY().doubleValue());
					col++;
					
					cell = hrow.createCell(col);
					cell.setCellStyle(rightAlignWthDecimal);
					cell.setCellValue(DataList.get(i).getDSPDTL_RATE().doubleValue());
					col++;
					
					cell = hrow.createCell(col);
					cell.setCellStyle(rightAlignWthDecimal);
					cell.setCellValue(DataList.get(i).getDSP_VALUE().doubleValue());
					col++;
					
					cell = hrow.createCell(col);
					cell.setCellStyle(leftAlign);
					cell.setCellValue(DataList.get(i).getREMARKS());
					col++;
					
				
					
					
					cell = hrow.createCell(col);
					cell.setCellStyle(leftAlign);
					cell.setCellValue(DataList.get(i).getAPPR_USER_NAME());
					col++;
					
					cell = hrow.createCell(col);
					cell.setCellStyle(leftAlign);
					cell.setCellValue(DataList.get(i).getAPPR_DATE());
					col++;
					
					cell = hrow.createCell(col);
					cell.setCellStyle(leftAlign);
					cell.setCellValue(DataList.get(i).getSTATUS());
					col++;
					
					cell = hrow.createCell(col);
					cell.setCellStyle(leftAlign);
					cell.setCellValue(DataList.get(i).getAPPR_REMARKS());
					col++;
					
					cell = hrow.createCell(col);
					cell.setCellStyle(leftAlign);
					cell.setCellValue(DataList.get(i).getBATCH_EXPIRY_DT());
					col++;
					row++;
					
				}
					old_dsp_no=new_dsp_no;	
			 
				 }
			FileOutputStream fileOut = new FileOutputStream(
					path.toString());
			wwbook.write(fileOut);
			fileOut.close();

			System.out.println("Excel Created");
			
			fileName=usermasterservice.generateExcellock(path.toString(), fileName,empId,".xlsx");

			} catch (Exception e) {
				System.out.println("Error Occurred :" +e.getMessage());// uncomment asneeded --;
		} finally {
}
		return fileName;
	
	}

	@Override
	public String GenerateDispatchAuditDownload_Pdf(AuditTrailBean bean, List<DispatchAuditTrailBean> list,
			String companyname, String username, String date, String frm_date, String to_date,HttpSession session,HttpServletRequest request) throws Exception {
			
		String path = MedicoConstants.PDF_FILE_PATH;
	    ;
			String comp_name = companyname;
			String user_name = username;
			FileOutputStream os = null;
			PrintStream ps = null;
			long timevar = new Date().getTime();
			String filename = "DispatchAudit" + timevar + ".pdf";
			StringBuffer filepath = new StringBuffer(path).append("\\");
			filepath.append(filename);
			File docfile = new File(path + "\\files\\" + filename);
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
				String new_challan="",old_challan="";
				int rows = 0;
				isnew = true;
				
				//create temp cell for detail line
				PdfPTable temp = new PdfPTable(13);
				temp.setWidths(bodyColWidth);	
				temp.addCell(PdfStyle.createLabelCellWithBGColorNoBold("Item Code",0.9f));
				temp.addCell(PdfStyle.createLabelCellWithBGColorNoBold("Item Description",0.9f));
				temp.addCell(PdfStyle.createLabelCellWithBGColorNoBold("Batch/Art No.",0.9f));
				temp.addCell(PdfStyle.createLabelCellWithBGColorNoBold("Expiry Dt",0.9f));
				temp.addCell(PdfStyle.createLabelCellWithBGColorNoBold("Qty",0.9f));
				temp.addCell(PdfStyle.createLabelCellWithBGColorNoBold("Rate",0.9f));
				temp.addCell(PdfStyle.createLabelCellWithBGColorNoBold("Value",0.9f));
				temp.addCell(PdfStyle.createLabelCellWithBGColorNoBold("Status",0.9f));
				temp.addCell(PdfStyle.createLabelCellWithBGColorNoBold("Level",0.9f));
				temp.addCell(PdfStyle.createLabelCellWithBGColorNoBold("User Id",0.9f));
				temp.addCell(PdfStyle.createLabelCellWithBGColorNoBold("User Name",0.9f));
				temp.addCell(PdfStyle.createLabelCellWithBGColorNoBold("Date Time",0.9f));
				temp.addCell(PdfStyle.createLabelCellWithBGColorNoBold("Remarks",0.9f));
				PdfPCell intCell = new PdfPCell(temp);
				intCell.setBorder(0);
				intCell.setColspan(13);
				
				for (DispatchAuditTrailBean challan : list) {
					new_challan = challan.getDSP_NO();
					
					
					if (rows >= limit) {
						bodytable = newPage(column, bodytable, rows);
						document.newPage();
						rows = 0;
					}

					if (isnew) {
						event.setHeader(createHeader(challan, comp_name,username,frm_date,to_date));
						isnew = false;
						bodytable = new PdfPTable(13);
						bodytable.setWidthPercentage(100);
						bodytable.setWidths(bodyColWidth);					
					}
					
					//System.out.println("old_challan::"+old_challan);
					//System.out.println("new_challan::"+new_challan);
					if (!old_challan.equalsIgnoreCase(new_challan)) {
						//print header		
						String fs_name = challan.getFSTAFF_NAME();
						
						bodytable.addCell(PdfStyle.createValueCellWithHeightBold(new_challan, false, true, 20));
						if(fs_name!=null){
							bodytable.addCell(PdfStyle.createValueCellWithHeightBold(
									challan.getFSTAFF_NAME().length()>46 ? challan.getFSTAFF_NAME().substring(0, 45)
											: challan.getFSTAFF_NAME(), false, true, 20));
						}else{
							bodytable.addCell(PdfStyle.createValueCellWithHeightBold(fs_name, false, true, 20));
						}
						
						bodytable.addCell(PdfStyle.createValueCellWithHeightBold(challan.getDSP_DT(), false, true, 20));
						bodytable.addCell(PdfStyle.createValueCellWithHeightBold(challan.getDSP_LR_NO(), false, true, 20));
						bodytable.addCell(PdfStyle.createValueCellWithHeightBold(challan.getDSP_LR_DT(), false, true, 20));
						bodytable.addCell(PdfStyle.createValueCellWithHeightBold(challan.getDSP_CHALLAN_NO(), false, true, 20));
						bodytable.addCell(PdfStyle.createValueCellWithHeightBold(challan.getDSP_CHALLAN_DT(), false, true, 20));
						bodytable.addCell(PdfStyle.createValueCellWithHeightBold(challan.getDSP_APPR_STATUS(), false, true, 20));
						bodytable.addCell(PdfStyle.createValueCellWithHeightBold("Entered", false, true, 20));
						bodytable.addCell(PdfStyle.createValueCellWithHeightBold(challan.getDSP_INS_USR_ID(), false, true, 20));
						bodytable.addCell(PdfStyle.createValueCellWithHeightBold(challan.getINS_NAME(), false, true, 20));
						bodytable.addCell(PdfStyle.createValueCellWithHeightBold(challan.getDSP_INS_DT(), false, true, 20));
						bodytable.addCell(PdfStyle.createValueCellWithHeightBold(challan.getDSP_INS_IP_ADD(), false, true, 20));
						if(challan.getDSP_MOD_USR_ID()!=null && challan.getDSP_MOD_USR_ID()!=""){
							bodytable.addCell(PdfStyle.createValueCellWithHeightBold("", false, true, 20));
							bodytable.addCell(PdfStyle.createValueCellWithHeightBold("", false, true, 20));
							bodytable.addCell(PdfStyle.createValueCellWithHeightBold("", false, true, 20));
							bodytable.addCell(PdfStyle.createValueCellWithHeightBold("", false, true, 20));
							bodytable.addCell(PdfStyle.createValueCellWithHeightBold("", false, true, 20));
							bodytable.addCell(PdfStyle.createValueCellWithHeightBold("", false, true, 20));
							bodytable.addCell(PdfStyle.createValueCellWithHeightBold("", false, true, 20));
							bodytable.addCell(PdfStyle.createValueCellWithHeightBold("", true, true, 20));
							bodytable.addCell(PdfStyle.createValueCellWithHeightBold("Modified", false, true, 20));
							bodytable.addCell(PdfStyle.createValueCellWithHeightBold(challan.getDSP_MOD_USR_ID(), false, true, 20));
							bodytable.addCell(PdfStyle.createValueCellWithHeightBold(challan.getMOD_NAME(), false, true, 20));
							bodytable.addCell(PdfStyle.createValueCellWithHeightBold(challan.getDSP_MOD_DT(), false, true, 20));
							bodytable.addCell(PdfStyle.createValueCellWithHeightBold(challan.getDSP_MOD_IP_ADD(), false, true, 20));
							rows++;
						}
						//print detail header
						bodytable.addCell(intCell);
						
						rows+=2;
					}
					
					//print detail
					PdfPTable item_dtl = new PdfPTable(13);
					item_dtl.setWidths(bodyColWidth);								
					item_dtl.addCell(PdfStyle.createValueCellWithHeight(challan.getSMP_PROD_CD(), false, true, 27));
					item_dtl.addCell(PdfStyle.createValueCellWithHeight(
							challan.getSMP_PROD_NAME().length()>60 ? challan.getSMP_PROD_NAME().substring(0, 59)
									: challan.getSMP_PROD_NAME(), false, true, 27));
					item_dtl.addCell(PdfStyle.createValueCellWithHeight(challan.getBATCH_NO(), false, true, 27));
					item_dtl.addCell(PdfStyle.createValueCellWithHeight(challan.getBATCH_EXPIRY_DT(), false, true, 27));
					item_dtl.addCell(PdfStyle.createValueCellWithHeight(challan.getDSPDTL_DISP_QTY().toString(), true, true, 27));
					item_dtl.addCell(PdfStyle.createValueCellWithHeight(challan.getDSPDTL_RATE().toString(), true, true, 27));
					item_dtl.addCell(PdfStyle.createValueCellWithHeight(challan.getDSP_VALUE().toString(), true, true, 27));
					item_dtl.addCell(PdfStyle.createValueCellWithHeight(challan.getSTATUS(), false, true, 27));
					item_dtl.addCell(PdfStyle.createValueCellWithHeight(challan.getAPPR_LEVEL(), true, true, 27));
					item_dtl.addCell(PdfStyle.createValueCellWithHeight("", false, true, 27));
					item_dtl.addCell(PdfStyle.createValueCellWithHeight(challan.getAPPR_USER_NAME(), false, true, 27));
					item_dtl.addCell(PdfStyle.createValueCellWithHeight(challan.getAPPR_DATE(), false, true, 27));
					item_dtl.addCell(PdfStyle.createValueCellWithHeight(challan.getAPPR_REMARKS(), false, true, 27));	
					PdfPCell itemCell = new PdfPCell(item_dtl);
					itemCell.setBorder(0);
					itemCell.setColspan(13);
					
					bodytable.addCell(itemCell);
					rows++;
					old_challan=new_challan;
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
			System.out.println("PATH:: " + path + "\\files\\" + filename);
			return filename;
		}
	public PdfPTable newPage(ColumnText column, PdfPTable bodytable, int rows) throws Exception {
		try {
			PdfPCell cell = new PdfPCell();
			column.setSimpleColumn(20, 20, 825, 506);			
			
			cell=new PdfPCell(PdfStyle.createSingleLine());
			cell.setColspan(13);		
			bodytable.addCell(cell);
			
			column.addElement(bodytable);
			column.go();
			bodytable = new PdfPTable(13);
			bodytable.setWidthPercentage(100);
			bodytable.setWidths(this.bodyColWidth);

		} catch (DocumentException e) {
			throw e;
		}
		return bodytable;
	}
	private PdfPTable createHeader(DispatchAuditTrailBean challan, String comp_name,String username,String from_dt,String to_dt)
			throws DocumentException, MalformedURLException, IOException {
		
			PdfPTable header = new PdfPTable(1);  //main header table
			header.getDefaultCell().setPadding(0.0f);
			header.setWidthPercentage(100);
			
			PdfPCell title_1 = PdfStyle.createUltimateCellWithoutBorder(comp_name, 1, 5, 12, 18, false);
			PdfPCell title_2 = PdfStyle.createUltimateCellWithoutBorder("Dispatch Audit Trail", 1, 5, 12, 18, false);
			header.addCell(title_1);
			header.addCell(title_2);
			//header.addCell(PdfStyle.createLabelCellWithoutBorder(""));
			
			PdfPTable h1 = new PdfPTable(3);
			h1.setWidthPercentage(100); 
			h1.setWidths(new float[] {5.4f, 5.4f, 5.4f}); 
			h1.addCell(PdfStyle.createUltimateCellWithoutBorder("Status: A-Approved  D-Discarded",0, 5, 9, 18, false));
			h1.addCell(PdfStyle.createUltimateCellWithoutBorder("Report From "+from_dt+" To "+to_dt,1, 5, 9, 18, false));
			h1.addCell(PdfStyle.createUltimateCellWithoutBorder("User: "+username+", Report Dated: "+Utility.convertSmpDatetoString(new Date()),2, 5, 9, 18, false));
			PdfPTable bodyHeader = new PdfPTable(13);  // set body column name
			bodyHeader.setWidthPercentage(100);
			bodyHeader.setWidths(this.bodyColWidth);
			bodyHeader.addCell(PdfStyle.createLabelCellWithBGColor("Disp No.",0.7f));
			bodyHeader.addCell(PdfStyle.createLabelCellWithBGColor("Field Staff",0.7f));
			bodyHeader.addCell(PdfStyle.createLabelCellWithBGColor("Disp Date",0.7f));
			bodyHeader.addCell(PdfStyle.createLabelCellWithBGColor("LR No.",0.7f));
			bodyHeader.addCell(PdfStyle.createLabelCellWithBGColor("LR Date",0.7f));
			bodyHeader.addCell(PdfStyle.createLabelCellWithBGColor("Challan No",0.7f));
			bodyHeader.addCell(PdfStyle.createLabelCellWithBGColor("Challan Dt",0.7f));
			bodyHeader.addCell(PdfStyle.createLabelCellWithBGColor("Status",0.7f));
			bodyHeader.addCell(PdfStyle.createLabelCellWithBGColor("Activity",0.7f));
			bodyHeader.addCell(PdfStyle.createLabelCellWithBGColor("User Id",0.7f));
			bodyHeader.addCell(PdfStyle.createLabelCellWithBGColor("User Name",0.7f));
			bodyHeader.addCell(PdfStyle.createLabelCellWithBGColor("Date Time",0.7f));
			bodyHeader.addCell(PdfStyle.createLabelCellWithBGColor("IP Used",0.7f));
			
			PdfPCell c1 = new PdfPCell(h1);
			c1.setBorder(0);
			header.addCell(c1);
			header.addCell(bodyHeader);
			
			return header;
	}
}