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
import com.excel.model.ViewDownloadIaaAuditTrail;
import com.excel.repository.UserMasterRepository;
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
public class IaaAuditTrialServiceImpl implements IaaAuditTrialService{
	
	@Autowired private UserMasterService usermasterservice;

	@Autowired
	private UserMasterRepository userMasterRepository;

@Autowired private JwtProvider tokenProvider;
@Autowired private UserMasterService userMasterService;



	String path;
	final private int limit=16;
	float[] bodyColWidth = new float[] {0.8f, 1.2f, 1.5f, 3.3f, 1.2f, 1.2f, 1.5f, 1.0f, 1.5f, 1.0f, 1.0f, 1.5f, 1.0f, 1.5f, 1.5f, 1.5f, 1.5f};
	String from_dt, to_dt;
	private Object user_name;
	@Override
	public String generateIaaAuditTrialPrint(List<ViewDownloadIaaAuditTrail> list, String companyname, String usernname,String frm_date,String to_date,HttpSession session,HttpServletRequest request)
			throws Exception {
	this.from_dt=frm_date;
	this.to_dt=to_date;
	path =MedicoConstants.PDF_FILE_PATH;
	//String comp_name =companyname;
	//user_name = (String)session.getAttribute("FNAME") +" "+(String) session.getAttribute("LNAME");
	FileOutputStream os = null;
	PrintStream ps = null;
	long timevar = new Date().getTime();
	String filename = "IaaAudit" + timevar + ".pdf";
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
		Long new_challan, old_challan=0L;
		int rows = 0;
		isnew = true;
		int index=0;
		//create temp cell for detail line
		PdfPTable temp = new PdfPTable(17);
		temp.setWidths(bodyColWidth);	
		temp.addCell(PdfStyle.createLabelCellWithBGColorNoBold("Sr.",0.9f));
		temp.addCell(PdfStyle.createLabelCellWithBGColorNoBold("Moved",0.9f));
		temp.addCell(PdfStyle.createLabelCellWithBGColorNoBold("Prod Code",0.9f));
		temp.addCell(PdfStyle.createLabelCellWithBGColorNoBold("Prod Description",0.9f));
		temp.addCell(PdfStyle.createLabelCellWithBGColorNoBold("Division",0.9f));
		temp.addCell(PdfStyle.createLabelCellWithBGColorNoBold("Pack",0.9f));
		temp.addCell(PdfStyle.createLabelCellWithBGColorNoBold("Batch",0.9f));
		temp.addCell(PdfStyle.createLabelCellWithBGColorNoBold("Expiry Date",0.9f));
		temp.addCell(PdfStyle.createLabelCellWithBGColorNoBold("Stock Type",0.9f));
		temp.addCell(PdfStyle.createLabelCellWithBGColorNoBold("Qty",0.9f));
		temp.addCell(PdfStyle.createLabelCellWithBGColorNoBold("Rate",0.9f));
		temp.addCell(PdfStyle.createLabelCellWithBGColorNoBold("Value",0.9f));
		temp.addCell(PdfStyle.createLabelCellWithBGColorNoBold("Activity",0.9f));
		temp.addCell(PdfStyle.createLabelCellWithBGColorNoBold("User Id",0.9f));
		temp.addCell(PdfStyle.createLabelCellWithBGColorNoBold("User Name",0.9f));
		temp.addCell(PdfStyle.createLabelCellWithBGColorNoBold("Date Time",0.9f));
		temp.addCell(PdfStyle.createLabelCellWithBGColorNoBold("IP Used",0.9f));
		PdfPCell intCell = new PdfPCell(temp);
		intCell.setBorder(0);
		intCell.setColspan(17);
		
		for (ViewDownloadIaaAuditTrail challan : list) {
			new_challan = challan.getSTKADJ_ID();
			
			//System.out.println("ROWS:: "+rows);
			if (rows >= limit) {
				bodytable = newPage(column, bodytable, rows);
				document.newPage();
				rows = 0;
			}

			if (isnew) {
				event.setHeader(createHeader(companyname,usernname));
				isnew = false;
				bodytable = new PdfPTable(17);
				bodytable.setWidthPercentage(100);
				bodytable.setWidths(bodyColWidth);					
			}
			
			//System.out.println("old_challan::"+old_challan);
			//System.out.println("new_challan::"+new_challan);
			if (old_challan.compareTo(new_challan)!=0) {
				//print header		
				/*String fs_name = challan.getFSTAFF_NAME();
				
				bodytable.addCell(PdfStyle.createValueCellWithHeightBold(new_challan, false, true, 20));
				if(fs_name!=null){
					bodytable.addCell(PdfStyle.createValueCellWithHeightBold(
							challan.getFSTAFF_NAME().length()>46 ? challan.getFSTAFF_NAME().substring(0, 45)
									: challan.getFSTAFF_NAME(), false, true, 20));
				}else{
					bodytable.addCell(PdfStyle.createValueCellWithHeightBold(fs_name, false, true, 20));
				}*/
				index=0;					
				bodytable.addCell(PdfStyle.createValueCellWithHeightBoldTopBorder(challan.getSTKADJ_DT(), false, true, 20));
				bodytable.addCell(PdfStyle.createValueCellWithHeightBoldTopBorder(challan.getSTKADJ_NO(), false, true, 20));
				PdfPCell rem = PdfStyle.createValueCellWithHeightBoldTopBorder(challan.getSTKADJ_REMARKS(), false, true, 20);
				rem.setColspan(10);
				bodytable.addCell(rem);
				bodytable.addCell(PdfStyle.createValueCellWithHeightBoldTopBorder("Entered", false, true, 20));
				bodytable.addCell(PdfStyle.createValueCellWithHeightBoldTopBorder(challan.getSTKADJ_INS_USR_ID(), false, true, 20));
				bodytable.addCell(PdfStyle.createValueCellWithHeightBoldTopBorder(challan.getINS_NAME()==null?" ":challan.getINS_NAME().length()>16 ? challan.getINS_NAME().substring(0, 15): challan.getINS_NAME(), false, true, 20));
				bodytable.addCell(PdfStyle.createValueCellWithHeightBoldTopBorder(challan.getSTKADJ_INS_DT(), false, true, 20));
				bodytable.addCell(PdfStyle.createValueCellWithHeightBoldTopBorder(challan.getSTKADJ_INS_IP_ADD(), false, true, 20));
				/*if(challan.getSTKADJ_MOD_USR_ID()!=null && challan.getSTKADJ_MOD_USR_ID()!=""){
					bodytable.addCell(PdfStyle.createValueCellWithHeightBold("", false, true, 20));
					bodytable.addCell(PdfStyle.createValueCellWithHeightBold("", false, true, 20));
					bodytable.addCell(PdfStyle.createValueCellWithHeightBold("", false, true, 20));
					bodytable.addCell(PdfStyle.createValueCellWithHeightBold("", false, true, 20));
					bodytable.addCell(PdfStyle.createValueCellWithHeightBold("", false, true, 20));
					bodytable.addCell(PdfStyle.createValueCellWithHeightBold("", false, true, 20));
					bodytable.addCell(PdfStyle.createValueCellWithHeightBold("", false, true, 20));
					bodytable.addCell(PdfStyle.createValueCellWithHeightBold("", true, true, 20));
					bodytable.addCell(PdfStyle.createValueCellWithHeightBold("Modified", false, true, 20));
					bodytable.addCell(PdfStyle.createValueCellWithHeightBold(challan.getDSP_MOD_usr_id(), false, true, 20));
					bodytable.addCell(PdfStyle.createValueCellWithHeightBold(challan.getMOD_NAME(), false, true, 20));
					bodytable.addCell(PdfStyle.createValueCellWithHeightBold(challan.getDSP_MOD_DT(), false, true, 20));
					bodytable.addCell(PdfStyle.createValueCellWithHeightBold(challan.getDSP_MOD_ip_add(), false, true, 20));
					rows++;
				}*/
				//print detail header
				bodytable.addCell(intCell);
				rows+=2;
			}
			
			//print detail
			PdfPTable item_dtl = new PdfPTable(17);
			item_dtl.setWidths(bodyColWidth);			
			if(challan.getIND().equalsIgnoreCase("1")){
				item_dtl.addCell(PdfStyle.createValueCellWithHeight((++index)+"", false, true, 27));
			}else{
				item_dtl.addCell(PdfStyle.createValueCellWithHeight("", false, true, 27));
			}
			item_dtl.addCell(PdfStyle.createValueCellWithHeight(challan.getMOVE_TYPE(), false, true, 27));
			item_dtl.addCell(PdfStyle.createValueCellWithHeight(challan.getSMP_PROD_CD(), false, true, 27));
			item_dtl.addCell(PdfStyle.createValueCellWithHeight(
					challan.getSMP_PROD_NAME().length()>60 ? challan.getSMP_PROD_NAME().substring(0, 59)
							: challan.getSMP_PROD_NAME(), false, true, 27));
			item_dtl.addCell(PdfStyle.createValueCellWithHeight(challan.getDIV_DISP_NM(), false, true, 27));
			item_dtl.addCell(PdfStyle.createValueCellWithHeight(challan.getPACK_DISP_NM(), false, true, 27));
			item_dtl.addCell(PdfStyle.createValueCellWithHeight(challan.getBATCH_NO(), false, true, 27));
			item_dtl.addCell(PdfStyle.createValueCellWithHeight(challan.getBATCH_EXPIRY_DT(), false, true, 27));
			item_dtl.addCell(PdfStyle.createValueCellWithHeight(
					challan.getSTOCK_TYPE_DESC().length()>30 ? challan.getSTOCK_TYPE_DESC().substring(0, 29)
							: challan.getSTOCK_TYPE_DESC(), true, true, 27));
			item_dtl.addCell(PdfStyle.createValueCellWithHeight(
					challan.getADJ_QTY()!=null ? challan.getADJ_QTY().toString() : "", true, true, 27));
			
			item_dtl.addCell(PdfStyle.createValueCellWithHeight(
					challan.getRATE()!=null ? challan.getRATE().toString() : "", true, true, 27));
			
			item_dtl.addCell(PdfStyle.createValueCellWithHeight(
					challan.getSTKADJ_VALUE()!=null ? challan.getSTKADJ_VALUE().toString() : "", true, true, 27));
			
			item_dtl.addCell(PdfStyle.createValueCellWithHeight("Entered", true, true, 27));
			item_dtl.addCell(PdfStyle.createValueCellWithHeight(challan.getSTKADJ_INS_USR_ID(), false, true, 27));
			item_dtl.addCell(PdfStyle.createValueCellWithHeight(challan.getINS_NAME()==null?" ":
				challan.getINS_NAME().length()>20 ? challan.getINS_NAME().substring(0, 19)
						: challan.getINS_NAME(), false, true, 27));
			item_dtl.addCell(PdfStyle.createValueCellWithHeight(challan.getSTKADJ_INS_DT(), false, true, 27));	
			item_dtl.addCell(PdfStyle.createValueCellWithHeight(challan.getSTKADJ_INS_IP_ADD(), false, true, 27));	
			PdfPCell itemCell = new PdfPCell(item_dtl);
			itemCell.setBorder(0);
			itemCell.setColspan(17);				
			bodytable.addCell(itemCell);
			
			//code for modified activity
			/*if(challan.getSTKADJ_MOD_USR_ID()!=null && challan.getSTKADJ_MOD_USR_ID()!=""){
				
			}*/
			
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

private PdfPTable createHeader(String comp_name,String username)
		throws DocumentException, MalformedURLException, IOException {
	
		PdfPTable header = new PdfPTable(1);  //main header table
		header.getDefaultCell().setPadding(0.0f);
		header.setWidthPercentage(100);
		
		PdfPCell title_1 = PdfStyle.createUltimateCellWithoutBorder(comp_name, 1, 5, 12, 18, false);
		PdfPCell title_2 = PdfStyle.createUltimateCellWithoutBorder("Stock Adjustment (Inventory Adjustment Advice) Audit Trail", 1, 5, 12, 18, false);
		header.addCell(title_1);
		header.addCell(title_2);
		//header.addCell(PdfStyle.createLabelCellWithoutBorder(""));
		
		PdfPTable h1 = new PdfPTable(3);
		h1.setWidthPercentage(100); 
		h1.setWidths(new float[] {5.4f, 5.4f, 5.4f}); 
		h1.addCell(PdfStyle.createUltimateCellWithoutBorder("Status: A-Approved  D-Discarded",0, 5, 9, 18, false));
		h1.addCell(PdfStyle.createUltimateCellWithoutBorder("Report From "+from_dt+" To "+to_dt,1, 5, 9, 18, false));
		h1.addCell(PdfStyle.createUltimateCellWithoutBorder("User: "+username+", Report Dated: "+Utility.convertSmpDatetoString(new Date()),2, 5, 9, 18, false));
		PdfPTable bodyHeader = new PdfPTable(17);  // set body column name
		bodyHeader.setWidthPercentage(100);
		bodyHeader.setWidths(this.bodyColWidth);
		bodyHeader.addCell(PdfStyle.createLabelCellWithBGColor("Date",0.7f));
		bodyHeader.addCell(PdfStyle.createLabelCellWithBGColor("IAA Num",0.7f));
		PdfPCell rem = PdfStyle.createLabelCellWithBGColor("Remarks",0.7f);
		rem.setColspan(10);
		bodyHeader.addCell(rem);
		bodyHeader.addCell(PdfStyle.createLabelCellWithBGColor("Status",0.7f));
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

public PdfPTable newPage(ColumnText column, PdfPTable bodytable, int rows) throws Exception {
	try {
		PdfPCell cell = new PdfPCell();
		column.setSimpleColumn(20, 20, 825, 506);			
		
		cell=new PdfPCell(PdfStyle.createSingleLine());
		cell.setColspan(17);		
		bodytable.addCell(cell);
		
		column.addElement(bodytable);
		column.go();
		bodytable = new PdfPTable(17);
		bodytable.setWidthPercentage(100);
		bodytable.setWidths(this.bodyColWidth);

	} catch (DocumentException e) {
		throw e;
	}
	return bodytable;
}




}
