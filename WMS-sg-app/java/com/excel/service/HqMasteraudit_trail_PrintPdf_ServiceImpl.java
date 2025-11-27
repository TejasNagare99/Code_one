//package com.excel.service;
//
//public class HqMasteraudit_trail_PrintPdf_ServiceImpl {
//
//}
package com.excel.service;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintStream;
import java.net.MalformedURLException;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.excel.configuration.JwtProvider;
import com.excel.model.HqMasterAuditTrailModel;
import com.excel.repository.UserMasterRepository;
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

@Service
public class HqMasteraudit_trail_PrintPdf_ServiceImpl implements HqMasteraudit_trail_PrintPdf_Service{
	@Autowired
	private UserMasterRepository userMasterRepository;

	@Autowired private UserMasterService usermasterservice;
	@Autowired private JwtProvider tokenProvider;
	@Autowired private UserMasterService userMasterService;

	
	
	String path;
	private String user_name;
	final private int limit=22;
	float[] bodyColWidth = new float[] { 1.0f, 3.0f, 1.5f, 0.8f, 0.8f, 1.0f, 1.2f, 1.0f, 1.5f, 1.5f, 1.5f, 0.8f, 1.0f};

	
	@Override
	public String generateHq_Master_audit_trail_print_pdf(List<HqMasterAuditTrailModel> list,String username,String companyname,HttpSession session,HttpServletRequest request) throws Exception {
		String today = Utility.convertSmpDatetoString(new Date());
		
		path = MedicoConstants.PDF_FILE_PATH;
		String comp_name = companyname;
		//HttpSession session = ServletActionContext.getRequest().getSession();
	//	user_name = (String)session.getAttribute("FNAME") +" "+(String) session.getAttribute("LNAME");
		user_name = username;
		
		FileOutputStream os = null;
		PrintStream ps = null;
		long timevar = new Date().getTime();
		String filename = "HqAudTrail" + timevar + ".pdf";
		
		
		File docfile1=new File(path + "\\files\\");
		if (!docfile1.exists()) {
			if (docfile1.mkdirs()) {
			} else {
				throw new RuntimeException("Could not create directory.");
			}
		}
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
			String old_div="", new_div="";			
			int rows = 0;
			isnew = true;			
			
			for (HqMasterAuditTrailModel dataList : list) {	
				new_div=dataList.getDivision();
				
				if (rows >= limit) {
					bodytable = newPage(column, bodytable, rows);
					document.newPage();
					rows = 0;
				}

				if (isnew) {
					event.setHeader(createHeader(comp_name,user_name,today,rows));
					isnew = false;
					bodytable = new PdfPTable(13);
					bodytable.setWidthPercentage(100);
					bodytable.setWidths(bodyColWidth);					
				}
								
				if(!old_div.equalsIgnoreCase(new_div)){
					bodytable.addCell(PdfStyle.createDatachangeCell("Division :     "+new_div, 13, BaseColor.BLUE));
					rows++;
				}
				old_div=new_div;
				if(dataList.getCurr_status().equalsIgnoreCase("CURRENT")){
					bodytable.addCell(PdfStyle.createValueCellWithHeight(dataList.getHq_code(), false, true, 20));
					bodytable.addCell(PdfStyle.createValueCellWithHeight(dataList.getHq_name(), false, true, 20));
					bodytable.addCell(PdfStyle.createValueCellWithHeight(dataList.getState(), false, true, 20));
					bodytable.addCell(PdfStyle.createValueCellWithHeight(dataList.getHq_pool_ind(), false, true, 20));
					bodytable.addCell(PdfStyle.createValueCellWithHeight(dataList.getHq_no_of_reps(), true, true, 20));
					bodytable.addCell(PdfStyle.createValueCellWithHeight(dataList.getHq_pmp_perf(), true, true, 20));
					bodytable.addCell(PdfStyle.createValueCellWithHeight(dataList.getHq_map_code(), false, true, 20));
					bodytable.addCell(PdfStyle.createValueCellWithHeight("Inserted", false, true, 20));
					bodytable.addCell(PdfStyle.createValueCellWithHeight(dataList.getIns_user_name(), false, true, 20));
					bodytable.addCell(PdfStyle.createValueCellWithHeight(dataList.getInsert_date(), false, true, 20));
					bodytable.addCell(PdfStyle.createValueCellWithHeight(dataList.getHq_ins_ip_add(), false, true, 20));
					bodytable.addCell(PdfStyle.createValueCellWithHeight(dataList.getHq_status(), false, true, 20));
					bodytable.addCell(PdfStyle.createValueCellWithHeight(dataList.getCurr_status(), false, true, 20));
				
				}else if(dataList.getCurr_status().equalsIgnoreCase("LOG")){
					bodytable.addCell(PdfStyle.createValueCellWithHeight(dataList.getHq_code(), false, true, 20));
					bodytable.addCell(PdfStyle.createValueCellWithHeight(dataList.getHq_name(), false, true, 20));
					bodytable.addCell(PdfStyle.createValueCellWithHeight(dataList.getState(), false, true, 20));
					bodytable.addCell(PdfStyle.createValueCellWithHeight(dataList.getHq_pool_ind(), false, true, 20));
					bodytable.addCell(PdfStyle.createValueCellWithHeight(dataList.getHq_no_of_reps(), true, true, 20));
					bodytable.addCell(PdfStyle.createValueCellWithHeight(dataList.getHq_pmp_perf(), true, true, 20));
					bodytable.addCell(PdfStyle.createValueCellWithHeight(dataList.getHq_map_code(), false, true, 20));
					bodytable.addCell(PdfStyle.createValueCellWithHeight("Modified", false, true, 20));
					bodytable.addCell(PdfStyle.createValueCellWithHeight(dataList.getMod_user_name(), false, true, 20));
					bodytable.addCell(PdfStyle.createValueCellWithHeight(dataList.getModify_date(), false, true, 20));
					bodytable.addCell(PdfStyle.createValueCellWithHeight(dataList.getHq_mod_ip_add(), false, true, 20));
					bodytable.addCell(PdfStyle.createValueCellWithHeight(dataList.getHq_status(), false, true, 20));
					bodytable.addCell(PdfStyle.createValueCellWithHeight(dataList.getCurr_status(), false, true, 20));
				}
				
				rows++;
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
		System.out.println("PATH$%#$#$$$$#$$ " + path + "\\files\\" + filename);
		return filename;
	}
	
	
	private PdfPTable createHeader(String comp_name, String userName, String date, int rows)
			throws DocumentException, MalformedURLException, IOException {
		
			PdfPTable header = new PdfPTable(1);  //main header table
			header.getDefaultCell().setPadding(0.0f);
			header.setWidthPercentage(100);
			
			PdfPCell title_1 = PdfStyle.createUltimateCellWithoutBorder(comp_name, 1, 5, 12, 18, false);
			PdfPCell title_2 = PdfStyle.createUltimateCellWithoutBorder("HQ Master Audit Trail", 1, 5, 12, 18, false);
			header.addCell(title_1);
			header.addCell(title_2);
			
			PdfPTable h1 = new PdfPTable(3);
			h1.setWidthPercentage(100); 
			h1.setWidths(new float[] {5.4f, 5.4f, 5.4f}); 
			h1.addCell(PdfStyle.createUltimateCellWithoutBorder("Report Dated : "+date,0,5,9,18,false));
			h1.addCell(PdfStyle.createUltimateCellWithoutBorder("",1,5,9,18,false));
			h1.addCell(PdfStyle.createUltimateCellWithoutBorder("User : "+user_name,2,5,9,18,false));			
			
			PdfPTable bodyHeader = new PdfPTable(13);  // set body column name
			bodyHeader.setWidthPercentage(100);
			bodyHeader.setWidths(this.bodyColWidth);
			bodyHeader.addCell(PdfStyle.createLabelCellWithBGColor("HQ Code",0.7f));
			bodyHeader.addCell(PdfStyle.createLabelCellWithBGColor("HQ Name",0.7f));
			bodyHeader.addCell(PdfStyle.createLabelCellWithBGColor("State",0.7f));
			bodyHeader.addCell(PdfStyle.createLabelCellWithBGColor("Pool Ind",0.7f));
			bodyHeader.addCell(PdfStyle.createLabelCellWithBGColor("No. of Reps",0.7f));
			bodyHeader.addCell(PdfStyle.createLabelCellWithBGColor("PMP Perf",0.7f));
			bodyHeader.addCell(PdfStyle.createLabelCellWithBGColor("Map Code",0.7f));
			bodyHeader.addCell(PdfStyle.createLabelCellWithBGColor("Activity",0.7f));
			bodyHeader.addCell(PdfStyle.createLabelCellWithBGColor("User Name",0.7f));
			bodyHeader.addCell(PdfStyle.createLabelCellWithBGColor("Date & Time",0.7f));
			bodyHeader.addCell(PdfStyle.createLabelCellWithBGColor("IP Used",0.7f));	
			bodyHeader.addCell(PdfStyle.createLabelCellWithBGColor("Status",0.7f));
			bodyHeader.addCell(PdfStyle.createLabelCellWithBGColor("Record Type",0.7f));
			
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