package com.excel.service;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintStream;
import java.net.MalformedURLException;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

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
import com.excel.configuration.JwtProvider;
//import com.excel.model.DispatchAuditTrailBean;
import com.excel.model.GrnAuditTrailBean;
import com.excel.repository.UserMasterRepository;
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
@Service
public class GrnAuditDownloadServiceImpl implements GrnAuditDownloadService {
	
	@Autowired
	private UserMasterRepository usermasterrepository;
	@Autowired private UserMasterService usermasterservice;
	@Autowired private JwtProvider tokenProvider;
	@Autowired private UserMasterService userMasterService;


	 final private int limit=16;
	 float[] bodyColWidth = new float[] { 1.5f, 2.6f, 1.5f, 1.5f, 1.3f, 1.5f, 1.5f, 1.3f, 1.0f, 1.5f, 1.5f, 1.5f, 1.8f };

	@Override
	public String GenerateAuditDownloadReport(AuditTrailBean bean, List<GrnAuditTrailBean> List,String companyname,
			String username,String fromdate,String todate,String empId) throws Exception {
		Workbook wwbook = null;
		StringBuffer path = null;
		 File ff = null;
		
		 
		 String fileName=null;
		try {
		   String comp_name =companyname;
		    Iterator<GrnAuditTrailBean> it = List.iterator();
		    long l = new Date().getTime();
		    fileName = "GrnAuditTrail_" + new Date().getTime()+ ".xlsx";
		    String filePath = MedicoConstants.REPORT_FILE_PATH;
		    path = new StringBuffer(filePath).append("\\");
			path.append(fileName);
		    
		  
		    System.out.println("Excel");
		    // ff = new File(filePath);
		    wwbook = new XSSFWorkbook();
		    Sheet wsheet = wwbook.createSheet("GrnAudittrail");
		    wsheet.createFreezePane(0, 4);
		    ApachePoiExcelFormat xlsxExcelFormat = new ApachePoiExcelFormat();
		    CellStyle sheetHeading = xlsxExcelFormat.SheetHeading(wwbook);

		    CellStyle columnHeading = xlsxExcelFormat.columnHeading(wwbook);
		    CellStyle rightAlign = xlsxExcelFormat.dataRightAlignWthDecimal(wwbook);
		    CellStyle leftAlign = xlsxExcelFormat.dataLeftAlign(wwbook);
		    CellStyle columnHeadingLemon = xlsxExcelFormat.columnSubHeadingLemon(wwbook);
		    CellStyle leftAlignB = xlsxExcelFormat.dataBoldLeftAlign(wwbook);
		    CellStyle rightAlignWthDecimal = xlsxExcelFormat.dataRightAlignWthDecimal(wwbook);
		    short col = 0, row = 0;
		    XSSFRow hrow = (XSSFRow) wsheet.createRow(row);
		    XSSFCell cell = hrow.createCell(col);
		    cell.setCellStyle(sheetHeading);
		  
		    String userName = username;
		    cell.setCellValue("User Name :" + userName);
		    wsheet.addMergedRegion(new CellRangeAddress(row, row, col, col + 16 - 2));

		    row++;

		    hrow = (XSSFRow) wsheet.createRow(row);
		    cell = hrow.createCell(col);
		    cell.setCellStyle(sheetHeading);
		    cell.setCellValue("Report Dated : " + Utility.convertDatetoString(new Date()));
		    wsheet.addMergedRegion(new CellRangeAddress(row, row, col, col + 16 - 2));
		    row++;

		    hrow = (XSSFRow) wsheet.createRow(row);
		    cell = hrow.createCell(col);
		    cell.setCellStyle(sheetHeading);
		    cell.setCellValue("Date : From  " + fromdate + " To " + todate);
		    wsheet.addMergedRegion(new CellRangeAddress(row, row, col, col + 16 - 2));
		    row++;

		    hrow = (XSSFRow) wsheet.createRow(row);

		    cell = hrow.createCell(col);
		    cell.setCellStyle(columnHeading);
		    cell.setCellValue("Location");
		    col++;
		    cell = hrow.createCell(col);
		    cell.setCellStyle(columnHeading);
		    cell.setCellValue("GRN No.");
		    col++;
		    cell = hrow.createCell(col);
		    cell.setCellStyle(columnHeading);
		    cell.setCellValue("GRN Date");
		    col++;
		    cell = hrow.createCell(col);
		    cell.setCellStyle(columnHeading);
		    cell.setCellValue("Supplier Name");
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

		    /*
		     * cell = hrow.createCell(col); cell.setCellStyle(columnHeading);
		     * cell.setCellValue("GRN_ins_usr_id"); col++;
		     */

		    cell = hrow.createCell(col);
		    cell.setCellStyle(columnHeading);
		    cell.setCellValue("INS_NAME");
		    col++;

		    cell = hrow.createCell(col);
		    cell.setCellStyle(columnHeading);
		    cell.setCellValue("GRN_INS_DT");
		    col++;

		    cell = hrow.createCell(col);
		    cell.setCellStyle(columnHeading);
		    cell.setCellValue("GRN_ins_ip_add");
		    col++;

		    /*
		     * cell = hrow.createCell(col); cell.setCellStyle(columnHeading);
		     * cell.setCellValue("GRN_MOD_usr_id"); col++;
		     */

		    cell = hrow.createCell(col);
		    cell.setCellStyle(columnHeading);
		    cell.setCellValue("MOD_NAME");
		    col++;

		    cell = hrow.createCell(col);
		    cell.setCellStyle(columnHeading);
		    cell.setCellValue("GRN_MOD_DT");
		    col++;

		    cell = hrow.createCell(col);
		    cell.setCellStyle(columnHeading);
		    cell.setCellValue("GRN_MOD_ip_add");
		    col++;
			/*
			 * int cols = 0; for (GrnAuditTrailBean common : DataList) { cols =
			 * common.getColcount(); }
			 */
		    // System.out.println("cols in excel sheet code : " +cols);

		    String old_grn_no = "";
		    String new_grn_no = "";

		    row++;
		    /*for (int i = 0; i < DataList.size(); i++) {*/
              for(GrnAuditTrailBean DataList:List) {
			new_grn_no = DataList.getGRN_NO();
			
			if (!old_grn_no.equals(new_grn_no)) {

			    // Printing Header part Here
			    col = 0;

			    hrow = (XSSFRow) wsheet.createRow(row);

			    cell = hrow.createCell(col);
			    cell.setCellStyle(leftAlignB);
			    cell.setCellValue(DataList.getLOC_NM());
			    col++;

			    cell = hrow.createCell(col);
			    cell.setCellStyle(leftAlignB);
			    cell.setCellValue(new_grn_no);
			    col++;

			    cell = hrow.createCell(col);
			    cell.setCellStyle(leftAlignB);
			    cell.setCellValue(DataList.getGRN_DT());
			    col++;

			    cell = hrow.createCell(col);
			    cell.setCellStyle(leftAlignB);
			    cell.setCellValue(DataList.getSUP_NM());
			    col++;

			    cell = hrow.createCell(col);
			    cell.setCellStyle(leftAlignB);
			    cell.setCellValue(DataList.getGRN_LR_NO());
			    col++;

			    cell = hrow.createCell(col);
			    cell.setCellStyle(leftAlignB);
			    cell.setCellValue(DataList.getGRN_LR_DT());
			    col++;

			    cell = hrow.createCell(col);
			    cell.setCellStyle(leftAlignB);
			    cell.setCellValue(DataList.getGRN_APPR_STATUS());
			    col++;

			    cell = hrow.createCell(col);
			    cell.setCellStyle(leftAlignB);
			    cell.setCellValue(DataList.getGRN_TRANSFER_NO());
			    col++;

			    cell = hrow.createCell(col);
			    cell.setCellStyle(leftAlignB);
			    cell.setCellValue(DataList.getGRN_TRANSFER_DT());
			    col++;

			    /*
			     * cell = hrow.createCell(col);
			     * cell.setCellStyle(leftAlignB);
			     * cell.setCellValue(DataList.get(i).getGRN_ins_usr_id());
			     * col++;
			     */
			    cell = hrow.createCell(col);
			    cell.setCellStyle(leftAlignB);
			    cell.setCellValue(DataList.getINS_NAME());
			    col++;
			    cell = hrow.createCell(col);
			    cell.setCellStyle(leftAlignB);
			    cell.setCellValue(DataList.getGRN_INS_DT());
			    col++;
			    cell = hrow.createCell(col);
			    cell.setCellStyle(leftAlignB);
			    cell.setCellValue(DataList.getGRN_ins_ip_add());
			    col++;

			    /*
			     * cell = hrow.createCell(col);
			     * cell.setCellStyle(leftAlignB);
			     * cell.setCellValue(DataList.get(i).getGRN_MOD_usr_id());
			     * col++;
			     */

			    cell = hrow.createCell(col);
			    cell.setCellStyle(leftAlignB);
			    cell.setCellValue(DataList.getMOD_NAME());
			    col++;

			    cell = hrow.createCell(col);
			    cell.setCellStyle(leftAlignB);
			    cell.setCellValue(DataList.getGRN_MOD_DT());
			    col++;

			    cell = hrow.createCell(col);
			    cell.setCellStyle(leftAlignB);
			    cell.setCellValue(DataList.getGRN_MOD_ip_add());
			    col++;

			    row++;
			    col = 0;

			    // Printing Detail's Header part

			    hrow = (XSSFRow) wsheet.createRow(row);

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
			    cell.setCellValue("Approval Cycle");
			    col++;
			    cell = hrow.createCell(col);
			    cell.setCellStyle(columnHeadingLemon);
			    cell.setCellValue("Level");
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
			    cell.setCellValue("Expiry Date ");
			    col++;

			    row++;
			    // =========================================================================================

			    // Printing Detail Part Here
			    col = 0;

			    hrow = (XSSFRow) wsheet.createRow(row);

			    cell = hrow.createCell(col);
			    cell.setCellStyle(leftAlign);
			    cell.setCellValue("");
			    col++;

			    cell = hrow.createCell(col);
			    cell.setCellStyle(leftAlign);
			    cell.setCellValue(DataList.getSMP_PROD_CD());
			    col++;

			    cell = hrow.createCell(col);
			    cell.setCellStyle(leftAlign);
			    cell.setCellValue(DataList.getSMP_PROD_NAME());
			    col++;

			    cell = hrow.createCell(col);
			    cell.setCellStyle(leftAlign);
			    cell.setCellValue(DataList.getBATCH_NO());
			    col++;

			    cell = hrow.createCell(col);
			    cell.setCellStyle(rightAlignWthDecimal);
			    cell.setCellValue(DataList.getGRND_QTY().doubleValue());
			    col++;

			    cell = hrow.createCell(col);
			    cell.setCellStyle(rightAlignWthDecimal);
			    cell.setCellValue(DataList.getGRND_RATE().doubleValue());
			    col++;

			    cell = hrow.createCell(col);
			    cell.setCellStyle(rightAlignWthDecimal);
			    cell.setCellValue(DataList.getGRND_VALUE().doubleValue());
			    col++;

			    cell = hrow.createCell(col);
			    cell.setCellStyle(leftAlign);
			    cell.setCellValue(DataList.getAPPR_REMARKS());
			    col++;

			    cell = hrow.createCell(col);
			    cell.setCellStyle(leftAlign);
			    cell.setCellValue(DataList.getAPPR_LEVEL_CYCLE());
			    col++;

			    cell = hrow.createCell(col);
			    cell.setCellStyle(leftAlign);
			    cell.setCellValue(DataList.getAPPR_LEVEL());
			    col++;

			    cell = hrow.createCell(col);
			    cell.setCellStyle(leftAlign);
			    cell.setCellValue(DataList.getAPPR_USER_NAME());
			    col++;

			    cell = hrow.createCell(col);
			    cell.setCellStyle(leftAlign);
			    cell.setCellValue(DataList.getAPPR_DATE());
			    col++;

			    cell = hrow.createCell(col);
			    cell.setCellStyle(leftAlign);
			    cell.setCellValue(DataList.getSTATUS());
			    col++;

			    cell = hrow.createCell(col);
			    cell.setCellStyle(leftAlign);
			    cell.setCellValue(DataList.getAPPR_REMARKS());
			    col++;

			    cell = hrow.createCell(col);
			    cell.setCellStyle(leftAlign);
			    cell.setCellValue(DataList.getBATCH_EXPIRY_DT());
			    col++;

			    row++;

			} else {
			    // Printing Detail Part Here
			    col = 0;

			    hrow = (XSSFRow) wsheet.createRow(row);

			    cell = hrow.createCell(col);
			    cell.setCellStyle(leftAlign);
			    cell.setCellValue("");
			    col++;

			    cell = hrow.createCell(col);
			    cell.setCellStyle(leftAlign);
			    cell.setCellValue(DataList.getSMP_PROD_CD());
			    col++;

			    cell = hrow.createCell(col);
			    cell.setCellStyle(leftAlign);
			    cell.setCellValue(DataList.getSMP_PROD_NAME());
			    col++;

			    cell = hrow.createCell(col);
			    cell.setCellStyle(leftAlign);
			    cell.setCellValue(DataList.getBATCH_NO());
			    col++;

			    cell = hrow.createCell(col);
			    cell.setCellStyle(rightAlign);
			    cell.setCellValue(DataList.getGRND_QTY().doubleValue());
			    col++;

			    cell = hrow.createCell(col);
			    cell.setCellStyle(rightAlign);
			    cell.setCellValue(DataList.getGRND_RATE().doubleValue());
			    col++;

			    cell = hrow.createCell(col);
			    cell.setCellStyle(rightAlign);
			    cell.setCellValue(DataList.getGRND_VALUE().doubleValue());
			    col++;

			    cell = hrow.createCell(col);
			    cell.setCellStyle(leftAlign);
			    cell.setCellValue(DataList.getAPPR_REMARKS());
			    col++;

			    cell = hrow.createCell(col);
			    cell.setCellStyle(leftAlign);
			    cell.setCellValue(DataList.getAPPR_LEVEL_CYCLE());
			    col++;

			    cell = hrow.createCell(col);
			    cell.setCellStyle(leftAlign);
			    cell.setCellValue(DataList.getAPPR_LEVEL());
			    col++;

			    cell = hrow.createCell(col);
			    cell.setCellStyle(leftAlign);
			    cell.setCellValue(DataList.getAPPR_USER_NAME());
			    col++;

			    cell = hrow.createCell(col);
			    cell.setCellStyle(leftAlign);
			    cell.setCellValue(DataList.getAPPR_DATE());
			    col++;

			    cell = hrow.createCell(col);
			    cell.setCellStyle(leftAlign);
			    cell.setCellValue(DataList.getSTATUS());
			    col++;

			    cell = hrow.createCell(col);
			    cell.setCellStyle(leftAlign);
			    cell.setCellValue(DataList.getAPPR_REMARKS());
			    col++;

			    cell = hrow.createCell(col);
			    cell.setCellStyle(leftAlign);
			    cell.setCellValue(DataList.getBATCH_EXPIRY_DT());
			    col++;
			    row++;

			}
			old_grn_no = new_grn_no;
		    }
		    FileOutputStream fileOut = new FileOutputStream(path.toString());
		    // lockAll(wsheet, "password");
		    wwbook.write(fileOut);
		    fileOut.close();

		    System.out.println("Excel Created");

		    fileName=usermasterservice.generateExcellock(path.toString(), fileName,empId,".xlsx");
			
                  
		} catch (Exception e) {
		    System.out.println("Error Occurred :" +e.getMessage());// uncomment asneeded --;
		} catch (Throwable th) {
		//	System.out.println("Error Occurred :"+new CodifyErrors().getErrorCode(th));//uncomment asneeded -- System.out.println("Error Occurred :" +e.getMessage());// uncomment asneeded --;
		} finally {
		}
		return fileName;
	    }
	
	@Override
	public String GenerateAuditDownloadPdf(AuditTrailBean bean, List<GrnAuditTrailBean> list,String companyname,String username,String date, String frm_date, String to_date,HttpSession session,HttpServletRequest request) throws Exception{
		String path = MedicoConstants.PDF_FILE_PATH;
		String comp_name = companyname;
		String user_name = username;
		
		FileOutputStream os = null;
		PrintStream ps = null;
		long timevar = new Date().getTime();
		String filename = "GrnAuditTrail" + timevar + ".pdf";
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
			String new_grn_no="",old_grn_no="";
			int rows = 0;
			isnew = true;			
			
			for (GrnAuditTrailBean DataList : list) {
				new_grn_no = DataList.getGRN_NO();

				if (rows >= limit) {
					bodytable = newPage(column, bodytable, rows);
					document.newPage();
					rows = 0;
				}

				if (isnew) {
					event.setHeader(createHeader(DataList, comp_name,username,date,frm_date,to_date));
					//rows++;
					isnew = false;
					bodytable = new PdfPTable(13);
					bodytable.setWidthPercentage(100);
					bodytable.setWidths(bodyColWidth);					
				}
				
				if (!old_grn_no.equalsIgnoreCase(new_grn_no)) {
					//print header
					
					bodytable.addCell(PdfStyle.createValueCellWithHeightBold(new_grn_no, false, true, 20));
					bodytable.addCell(PdfStyle.createValueCellWithHeightBold(DataList.getSUP_NM(), false, true, 20));
					bodytable.addCell(PdfStyle.createValueCellWithHeightBold(DataList.getGRN_DT(), false, true, 20));
					bodytable.addCell(PdfStyle.createValueCellWithHeightBold(DataList.getGRN_LR_NO(), false, true, 20));
					bodytable.addCell(PdfStyle.createValueCellWithHeightBold(DataList.getGRN_LR_DT(), false, true, 20));
					bodytable.addCell(PdfStyle.createValueCellWithHeightBold(DataList.getGRN_APPR_STATUS(), false, true, 20));
					bodytable.addCell(PdfStyle.createValueCellWithHeightBold(DataList.getGRN_TRANSFER_NO(), false, true, 20));
					bodytable.addCell(PdfStyle.createValueCellWithHeightBold(DataList.getGRN_TRANSFER_DT(), false, true, 20));
					bodytable.addCell(PdfStyle.createValueCellWithHeightBold("Entered", false, true, 20));
					bodytable.addCell(PdfStyle.createValueCellWithHeightBold(DataList.getGRN_ins_usr_id(), false, true, 20));
					bodytable.addCell(PdfStyle.createValueCellWithHeightBold(DataList.getINS_NAME(), false, true, 20));
					bodytable.addCell(PdfStyle.createValueCellWithHeightBold(DataList.getGRN_INS_DT(), false, true, 20));
					bodytable.addCell(PdfStyle.createValueCellWithHeightBold(DataList.getGRN_ins_ip_add(), false, true, 20));
					rows++;
					if(!(DataList.getGRN_MOD_usr_id().isEmpty() ||DataList.getGRN_MOD_usr_id().equals(null)))
					{
						bodytable.addCell(PdfStyle.createValueCellWithHeight("", false, true, 20));
						bodytable.addCell(PdfStyle.createValueCellWithHeight("",false, true, 20));
						bodytable.addCell(PdfStyle.createValueCellWithHeight("",false, true, 20));
						bodytable.addCell(PdfStyle.createValueCellWithHeight("",false, true, 20));
						bodytable.addCell(PdfStyle.createValueCellWithHeight("",false, true, 20));
						bodytable.addCell(PdfStyle.createValueCellWithHeight("", false, true, 20));
						bodytable.addCell(PdfStyle.createValueCellWithHeight("", false, true, 20));
						bodytable.addCell(PdfStyle.createValueCellWithHeight("", false, true, 20));
						bodytable.addCell(PdfStyle.createValueCellWithHeight("Modified", false, true, 20));
						bodytable.addCell(PdfStyle.createValueCellWithHeight(DataList.getGRN_MOD_usr_id(), false, true, 20));
						bodytable.addCell(PdfStyle.createValueCellWithHeight(DataList.getMOD_NAME(), false, true, 20));
						bodytable.addCell(PdfStyle.createValueCellWithHeight(DataList.getGRN_MOD_DT(), false, true, 20));
						bodytable.addCell(PdfStyle.createValueCellWithHeight(DataList.getGRN_MOD_ip_add(), false, true, 20));
						rows++;
					}
					
					
					//print detail header
					bodytable.addCell(PdfStyle.createLabelCellWithBGColorNoBold("Item code",0.9f));
					bodytable.addCell(PdfStyle.createLabelCellWithBGColorNoBold("Item description",0.9f));
					bodytable.addCell(PdfStyle.createLabelCellWithBGColorNoBold("Batch/Artwork No.",0.9f));
					bodytable.addCell(PdfStyle.createLabelCellWithBGColorNoBold("Expiry Date",0.9f));
					bodytable.addCell(PdfStyle.createLabelCellWithBGColorNoBold("Qty",0.9f));
					bodytable.addCell(PdfStyle.createLabelCellWithBGColorNoBold("Rate",0.9f));
					bodytable.addCell(PdfStyle.createLabelCellWithBGColorNoBold("Value",0.9f));
					bodytable.addCell(PdfStyle.createLabelCellWithBGColorNoBold("Approval Cycle",0.9f));
					bodytable.addCell(PdfStyle.createLabelCellWithBGColorNoBold("Level",0.9f));
					bodytable.addCell(PdfStyle.createLabelCellWithBGColorNoBold("User",0.9f));
					bodytable.addCell(PdfStyle.createLabelCellWithBGColorNoBold("Date & Time",0.9f));
					bodytable.addCell(PdfStyle.createLabelCellWithBGColorNoBold("Status",0.9f));
					bodytable.addCell(PdfStyle.createLabelCellWithBGColorNoBold("Remarks",0.9f));
					
					rows++;
					
				}
				
				//print detail
				bodytable.addCell(PdfStyle.createValueCellWithHeight(DataList.getSMP_PROD_CD(), false, true, 27));
				bodytable.addCell(PdfStyle.createValueCellWithHeight(DataList.getSMP_PROD_NAME().length()>=50 ? DataList.getSMP_PROD_NAME().substring(0, 50) : DataList.getSMP_PROD_NAME(), false, true, 27));
				bodytable.addCell(PdfStyle.createValueCellWithHeight(DataList.getBATCH_NO(), false, true, 27));
				bodytable.addCell(PdfStyle.createValueCellWithHeight(DataList.getBATCH_EXPIRY_DT(), false, true, 27));
				bodytable.addCell(PdfStyle.createValueCellWithHeight(DataList.getGRND_QTY().toString(),true, true, 27));
				bodytable.addCell(PdfStyle.createValueCellWithHeight(DataList.getGRND_RATE().toString(), true, true, 27));
				bodytable.addCell(PdfStyle.createValueCellWithHeight(DataList.getGRND_VALUE().toString(), true, true, 27));
				bodytable.addCell(PdfStyle.createValueCellWithHeight(DataList.getAPPR_LEVEL_CYCLE(), true, true, 27));
				bodytable.addCell(PdfStyle.createValueCellWithHeight(DataList.getAPPR_LEVEL(), true, true, 27));
				bodytable.addCell(PdfStyle.createValueCellWithHeight(DataList.getAPPR_USER_NAME(), false, true, 27));
				bodytable.addCell(PdfStyle.createValueCellWithHeight(DataList.getAPPR_DATE(), false, true, 27));
				bodytable.addCell(PdfStyle.createValueCellWithHeight(DataList.getSTATUS(), false, true, 27));
				bodytable.addCell(PdfStyle.createValueCellWithHeight(DataList.getAPPR_REMARKS(), false, true, 27));				
				
				rows++;
				old_grn_no=new_grn_no;
				//System.err.println(num + ":Detail:"	+ DataList.getProd_name());
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
	
	private PdfPTable createHeader(GrnAuditTrailBean DataList, String comp_name, String userName, String date, String frm_date, String to_date)
			throws DocumentException, MalformedURLException, IOException {
		
			PdfPTable header = new PdfPTable(1);  //main header table
			header.getDefaultCell().setPadding(0.0f);
			header.setWidthPercentage(100);
			
			PdfPCell title_1 = PdfStyle.createUltimateCellWithoutBorder(comp_name, 1, 5, 12, 18, false);
			PdfPCell title_2 = PdfStyle.createUltimateCellWithoutBorder("GRN Audit Trail", 1, 5, 12, 18, false);
			header.addCell(title_1);
			header.addCell(title_2);
			//header.addCell(PdfStyle.createLabelCellWithoutBorder(""));
			
			PdfPTable h1 = new PdfPTable(3);
			h1.setWidthPercentage(100); 
			h1.setWidths(new float[] {5.4f, 5.4f, 5.4f}); 
			h1.addCell(PdfStyle.createUltimateCellWithoutBorder("Status: A-Approved D-Discarded",0,5,9,18,false));
			h1.addCell(PdfStyle.createUltimateCellWithoutBorder("From "+frm_date+" to "+to_date,1,5,9,18,false));
			h1.addCell(PdfStyle.createUltimateCellWithoutBorder("User : "+userName+", Report Dated : "+date,2,5,9,18,false));			
			
			PdfPTable bodyHeader = new PdfPTable(13);  // set body column name
			bodyHeader.setWidthPercentage(100);
			bodyHeader.setWidths(this.bodyColWidth);
			bodyHeader.addCell(PdfStyle.createLabelCellWithBGColor("GRN No.",0.7f));
			bodyHeader.addCell(PdfStyle.createLabelCellWithBGColor("Received from/Supplier",0.7f));
			bodyHeader.addCell(PdfStyle.createLabelCellWithBGColor("GRN Date",0.7f));
			bodyHeader.addCell(PdfStyle.createLabelCellWithBGColor("LR No.",0.7f));
			bodyHeader.addCell(PdfStyle.createLabelCellWithBGColor("LR Date",0.7f));
			bodyHeader.addCell(PdfStyle.createLabelCellWithBGColor("Status",0.7f));
			bodyHeader.addCell(PdfStyle.createLabelCellWithBGColor("Challan No",0.7f));
			bodyHeader.addCell(PdfStyle.createLabelCellWithBGColor("Challan Date",0.7f));
			bodyHeader.addCell(PdfStyle.createLabelCellWithBGColor("Activity",0.7f));
			bodyHeader.addCell(PdfStyle.createLabelCellWithBGColor("User Id",0.7f));
			bodyHeader.addCell(PdfStyle.createLabelCellWithBGColor("User Name",0.7f));
			bodyHeader.addCell(PdfStyle.createLabelCellWithBGColor("Date & Time",0.7f));
			bodyHeader.addCell(PdfStyle.createLabelCellWithBGColor("IP Used",0.7f));
			
			PdfPCell c1 = new PdfPCell(h1);
			c1.setBorder(0);
			header.addCell(c1);
			header.addCell(bodyHeader);
			
			return header;
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

	}
