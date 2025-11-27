package com.excel.service;

import java.io.File;
import java.io.FileNotFoundException;
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
import com.excel.model.BatchMasterAuditTrailModel;
import com.excel.model.Usermaster;
import com.excel.repository.UserMasterRepository;
import com.excel.security.PasswordManager;
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
public class Batch_Master_AuditTrail_PDF_ServiceImpl implements Batch_Master_AuditTrail_PDF_Service{
	@Autowired
	private UserMasterRepository userMasterRepository;
	@Autowired private UserMasterService usermasterservice;
	@Autowired private JwtProvider tokenProvider;
	@Autowired private UserMasterService userMasterService;
	String filepath;
	StringBuffer path;
	final private int limit=18;
	float[] bodyColWidth = new float[] { 2.0f, 1.5f, 1.5f, 1.5f, 2.0f, 1.3f, 1.3f, 1.3f, 1.3f, 1.3f, 1.9f, 1.8f, 2.0f, 1.3f, 1.5f};
	String filename;
	
	
	@Override
	public String GenerateBatch_Master_Audit_Trail_PDF(List<BatchMasterAuditTrailModel> list, String fromdate,
			String todate, String username,String companyname,HttpSession session,HttpServletRequest request) throws Exception {
		
		String date = Utility.convertSmpDatetoString(new Date());
		
		filepath = MedicoConstants.PDF_FILE_PATH;
		String comp_name = companyname;
		
		String user_name = username;
		
		System.out.println("Inside getPDF==============================================");
		
		//FileOutputStream os = null;
		PrintStream ps = null;
		long timevar = new Date().getTime();
		filename = "BatchMasterAuditTrail" + timevar + ".pdf";
		
		File file1 = new File(filepath);
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
		path = new StringBuffer(filepath).append("\\files\\\\");
		path.append(filename);
		
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
			String new_prod="",old_prod="";
			String new_loc="",old_loc="";
		
			int rows = 0;	
			isnew = true;			
			
			for (BatchMasterAuditTrailModel DataList : list) {
				new_prod = DataList.getProduct();
				new_loc = DataList.getLocation();
				if (rows >= limit) {
					bodytable = newPage(column, bodytable, rows);
					document.newPage();
					rows = 0;
				}

				if (isnew) {
					event.setHeader(createHeader(DataList, comp_name,username,date,fromdate,todate,rows));
					//rows++;
					isnew = false;
					bodytable = new PdfPTable(15);
					bodytable.setWidthPercentage(100);
					bodytable.setWidths(bodyColWidth);					
				}
				
				
				if (!old_prod.equalsIgnoreCase(new_prod)) {
					 bodytable.addCell(PdfStyle.createDatachangeCell("Product : "+new_prod,15,BaseColor.RED));
					 rows++;
					 bodytable.addCell(PdfStyle.createDatachangeCell("Location : "+DataList.getLocation(),15,BaseColor.BLUE));
					 rows++;
				}
				
				if(old_prod.equals(new_prod) && !old_loc.equals(new_loc))
				{
					bodytable.addCell(PdfStyle.createDatachangeCell("Location : "+DataList.getLocation(),15,BaseColor.BLUE));
					 rows++;
				}
				old_loc=new_loc;
				old_prod=new_prod;
				
				bodytable.addCell(PdfStyle.createValueCellWithHeight(DataList.getBatch_no(), false, true, 20));
				bodytable.addCell(PdfStyle.createValueCellWithHeight(DataList.getBatch_mfg_dt(), false, true, 20));
				bodytable.addCell(PdfStyle.createValueCellWithHeight(DataList.getBatch_expiry_dt(), false, true, 20));
				bodytable.addCell(PdfStyle.createValueCellWithHeight(DataList.getBatch_physical_stock(), true, true, 20));
				bodytable.addCell(PdfStyle.createValueCellWithHeight(DataList.getMfg_location(), false, true, 20));
				bodytable.addCell(PdfStyle.createValueCellWithHeight(DataList.getBatch_rate().toString(), true, true, 20));
				bodytable.addCell(PdfStyle.createValueCellWithHeight(DataList.getBatch_costing_rate().toString(), true, true, 20));
				bodytable.addCell(PdfStyle.createValueCellWithHeight(DataList.getBatch_mktg_rate().toString(), true, true, 20));
				bodytable.addCell(PdfStyle.createValueCellWithHeight(DataList.getBatch_nrv().toString(), true, true, 20));
				bodytable.addCell(PdfStyle.createValueCellWithHeight(DataList.getBatch_display_rate().toString(), true, true, 20));
				bodytable.addCell(PdfStyle.createValueCellWithHeight(DataList.getBatch_open_stock().toString(), true, true, 20));
				bodytable.addCell(PdfStyle.createValueCellWithHeight(DataList.getBatch_in_stock().toString(), true, true, 20));
				bodytable.addCell(PdfStyle.createValueCellWithHeight(DataList.getBatch_out_stock().toString(), true, true, 20));
				bodytable.addCell(PdfStyle.createValueCellWithHeight(DataList.getBatch_with_held_qty().toString(), true, true, 20));
//change
				bodytable.addCell(PdfStyle.createValueCellWithHeight(DataList.getBatch_erp_batch_cd()==null?" ":DataList.getBatch_erp_batch_cd().toString(), true, true, 20));
				rows++;
				
			if(DataList.getCurr_status().equalsIgnoreCase("CURRENT")){
				
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
				bodytable.addCell(PdfStyle.createValueCellWithHeight(DataList.getIns_user_name(), false, true, 20));
				bodytable.addCell(PdfStyle.createValueCellWithHeight(DataList.getBatch_ins_dt(), false, true, 20));
				bodytable.addCell(PdfStyle.createValueCellWithHeight(DataList.getBatch_ins_ip_add(), false, true, 20));
				bodytable.addCell(PdfStyle.createValueCellWithHeight(DataList.getBatch_status(), false, true, 20));
				bodytable.addCell(PdfStyle.createValueCellWithHeight(DataList.getCurr_status(), false, true, 20));
				rows++;
				
				} else if(DataList.getCurr_status().equalsIgnoreCase("LOG"))	{
					
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
					bodytable.addCell(PdfStyle.createValueCellWithHeight(DataList.getMod_user_name(), false, true, 20));
					bodytable.addCell(PdfStyle.createValueCellWithHeight(DataList.getBatch_mod_dt(), false, true, 20));
					bodytable.addCell(PdfStyle.createValueCellWithHeight(DataList.getBatch_mod_ip_add(), false, true, 20));
					bodytable.addCell(PdfStyle.createValueCellWithHeight(DataList.getBatch_status(), false, true, 20));
					bodytable.addCell(PdfStyle.createValueCellWithHeight(DataList.getCurr_status(), false, true, 20));
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
//				if (os != null) {
//					os.close();
//				}
//				if (ps != null) {
//					ps.close();
//				}
			} catch (Exception e) {
				System.out.println("Error Occurred :" +e.getMessage());// uncomment asneeded --;
			}
		}
		System.out.println("PATH$%#$#$$$$#$$" + path + "\\files\\" + filename);
		return filename;
	}

	
	public PdfPTable newPage(ColumnText column, PdfPTable bodytable, int rows) throws Exception {
		try {
			PdfPCell cell = new PdfPCell();
			column.setSimpleColumn(20, 20, 825, 486);
			
			cell=new PdfPCell(PdfStyle.createSingleLine());
			cell.setColspan(15);		
			bodytable.addCell(cell);
			
			column.addElement(bodytable);
			column.go();
			bodytable = new PdfPTable(15);
			bodytable.setWidthPercentage(100);
			bodytable.setWidths(this.bodyColWidth);

		} catch (DocumentException e) {
			throw e;
		}
		return bodytable;
	}
	
	private PdfPTable createHeader(BatchMasterAuditTrailModel DataList, String comp_name, String userName, String date, String frm_date, String to_date, int rows)
			throws DocumentException, MalformedURLException, IOException {
		
			PdfPTable header = new PdfPTable(1);  //main header table
			header.getDefaultCell().setPadding(0.0f);
			header.setWidthPercentage(100);
			
			PdfPCell title_1 = PdfStyle.createUltimateCellWithoutBorder(comp_name, 1, 5, 12, 18, false);
			PdfPCell title_2 = PdfStyle.createUltimateCellWithoutBorder("Batch Master Audit Trail", 1, 5, 12, 18, false);
			header.addCell(title_1);
			header.addCell(title_2);
			//header.addCell(PdfStyle.createLabelCellWithoutBorder(""));
			
			PdfPTable h1 = new PdfPTable(3);
			h1.setWidthPercentage(100); 
			h1.setWidths(new float[] {5.4f, 5.4f, 5.4f}); 
			h1.addCell(PdfStyle.createUltimateCellWithoutBorder("Report Dated : "+date,0,5,9,18,false));
			h1.addCell(PdfStyle.createUltimateCellWithoutBorder("From "+frm_date+" to "+to_date,1,5,9,18,false));
			h1.addCell(PdfStyle.createUltimateCellWithoutBorder("User : "+userName,2,5,9,18,false));			
			
			PdfPTable bodyHeader = new PdfPTable(15);  // set body column name
			bodyHeader.setWidthPercentage(100);
			bodyHeader.setWidths(this.bodyColWidth);
			bodyHeader.addCell(PdfStyle.createLabelCellWithBGColor("Batch No.",0.7f));
			bodyHeader.addCell(PdfStyle.createLabelCellWithBGColor("Mfg. Date",0.7f));
			bodyHeader.addCell(PdfStyle.createLabelCellWithBGColor("Exp.Date",0.7f));
			bodyHeader.addCell(PdfStyle.createLabelCellWithBGColor("Physical Stock",0.7f));
			bodyHeader.addCell(PdfStyle.createLabelCellWithBGColor("Mfg. Location",0.7f));
			bodyHeader.addCell(PdfStyle.createLabelCellWithBGColor("Rate",0.7f));
			bodyHeader.addCell(PdfStyle.createLabelCellWithBGColor("COG Rate",0.7f));
			bodyHeader.addCell(PdfStyle.createLabelCellWithBGColor("Mktg. Rate",0.7f));
			bodyHeader.addCell(PdfStyle.createLabelCellWithBGColor("NRV",0.7f));
			bodyHeader.addCell(PdfStyle.createLabelCellWithBGColor("Display Rate",0.7f));
			bodyHeader.addCell(PdfStyle.createLabelCellWithBGColor("Opening Stock",0.7f));
			bodyHeader.addCell(PdfStyle.createLabelCellWithBGColor("Qty. Added",0.7f));
			bodyHeader.addCell(PdfStyle.createLabelCellWithBGColor("Qty. Removed",0.7f));
			bodyHeader.addCell(PdfStyle.createLabelCellWithBGColor("Withheld Qty",0.7f));
			bodyHeader.addCell(PdfStyle.createLabelCellWithBGColor("ERP Code",0.7f));
			
			bodyHeader.addCell(PdfStyle.createLabelCellWithBGColor("",0.7f));
			bodyHeader.addCell(PdfStyle.createLabelCellWithBGColor("",0.7f));
			bodyHeader.addCell(PdfStyle.createLabelCellWithBGColor("",0.7f));
			bodyHeader.addCell(PdfStyle.createLabelCellWithBGColor("",0.7f));
			bodyHeader.addCell(PdfStyle.createLabelCellWithBGColor("",0.7f));
			bodyHeader.addCell(PdfStyle.createLabelCellWithBGColor("",0.7f));
			bodyHeader.addCell(PdfStyle.createLabelCellWithBGColor("",0.7f));
			bodyHeader.addCell(PdfStyle.createLabelCellWithBGColor("",0.7f));
			bodyHeader.addCell(PdfStyle.createLabelCellWithBGColor("",0.7f));
			bodyHeader.addCell(PdfStyle.createLabelCellWithBGColor("Activity",0.7f));
			bodyHeader.addCell(PdfStyle.createLabelCellWithBGColor("User Name",0.7f));
			bodyHeader.addCell(PdfStyle.createLabelCellWithBGColor("Date & Time",0.7f));
			bodyHeader.addCell(PdfStyle.createLabelCellWithBGColor("IP used",0.7f));
			bodyHeader.addCell(PdfStyle.createLabelCellWithBGColor("Status",0.7f));
			bodyHeader.addCell(PdfStyle.createLabelCellWithBGColor("Record Type",0.7f));
			
			
			rows+=2;
			
			PdfPCell c1 = new PdfPCell(h1);
			c1.setBorder(0);
			header.addCell(c1);
			header.addCell(bodyHeader);
			
			return header;
	}
}
