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

import com.excel.bean.AuditTrailBean;
import com.excel.configuration.JwtProvider;
import com.excel.model.SupplierMasterModel;
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
public class SupplierMasterDownload_PDF_ServiceImpl implements SupplierMasterDownload_PDF_Service{
	@Autowired
	private UserMasterRepository userMasterRepository;
	@Autowired private UserMasterService usermasterservice;

@Autowired private JwtProvider tokenProvider;
@Autowired private UserMasterService userMasterService;


	final private int limit=20;
	float[] bodyColWidth = new float[] { 3.60f, 3.60f, 1.5f, 1.5f, 1.5f, 1.5f, 1.5f, 2.0f, 1.5f, 1.8f};
	
	
	@Override
	public String GenerateSupplierMasterDowloadPDF(List<SupplierMasterModel> list, AuditTrailBean bean, String username,String companyname,HttpSession session,HttpServletRequest request)
			throws Exception {
		
		
		String h_path = MedicoConstants.PDF_FILE_PATH;
		String comp_name = companyname;
		String date = Utility.convertSmpDatetoString(new Date());
		System.out.println("date :: "+date);
		String user_name = username;
		
		FileOutputStream os = null;
		PrintStream ps = null;
		long timevar = new Date().getTime();
		String filename = "SupplierMasterAuditTrail" + timevar + ".pdf";
		
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
		
		File docfile = new File(h_path + "\\files\\" + filename);
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
			String new_supp_type="",old_supp_type="";
			int rows = 0;
			isnew = true;			
			
			for (SupplierMasterModel DataList : list) {
				new_supp_type = DataList.getSupplier_type();

				if (rows >= limit) {
					bodytable = newPage(column, bodytable, rows);
					document.newPage();
					rows = 0;
				}

				if (isnew) {
					event.setHeader(createHeader(DataList, comp_name,username,date,bean.getFrmdt(),bean.getTodt(),rows));
					//rows++;
					isnew = false;
					bodytable = new PdfPTable(10);
					bodytable.setWidthPercentage(100);
					bodytable.setWidths(bodyColWidth);					
				}
				
				
				if (!old_supp_type.equalsIgnoreCase(new_supp_type)) {
					bodytable.addCell(PdfStyle.createDatachangeCell("Supplier Type : "+new_supp_type,10,BaseColor.RED));
					 rows++;
				}
				old_supp_type=new_supp_type;
				
				bodytable.addCell(PdfStyle.createValueCellWithHeight(DataList.getSupplier_name(), false, true, 20));
				bodytable.addCell(PdfStyle.createValueCellWithHeight(DataList.getSup_name_old(), false, true, 20));
				bodytable.addCell(PdfStyle.createValueCellWithHeight(DataList.getTin_no(), false, true, 20));
				bodytable.addCell(PdfStyle.createValueCellWithHeight(DataList.getCst_no(), false, true, 20));
				bodytable.addCell(PdfStyle.createValueCellWithHeight(DataList.getTan_no(), false, true, 20));
				bodytable.addCell(PdfStyle.createValueCellWithHeight(DataList.getPan_no(), false, true, 20));
				bodytable.addCell(PdfStyle.createValueCellWithHeight(DataList.getMap_code(), false, true, 20));
				bodytable.addCell(PdfStyle.createValueCellWithHeight(DataList.getSubcomp_company(), false, true, 20));
				bodytable.addCell(PdfStyle.createValueCellWithHeight(DataList.getCurr_status(), false, true, 20));
				bodytable.addCell(PdfStyle.createValueCellWithHeight(DataList.getSup_status(), false, true, 20));
				
				rows++;
				
				if(DataList.getCurr_status().equalsIgnoreCase("CURRENT"))
				{
				bodytable.addCell(PdfStyle.createValueCellWithHeight("", false, true, 20));
				bodytable.addCell(PdfStyle.createValueCellWithHeight("", false, true, 20));
				bodytable.addCell(PdfStyle.createValueCellWithHeight("", false, true, 20));
				bodytable.addCell(PdfStyle.createValueCellWithHeight("", false, true, 20));
				bodytable.addCell(PdfStyle.createValueCellWithHeight("", false, true, 20));
				bodytable.addCell(PdfStyle.createValueCellWithHeight("", false, true, 20));
				bodytable.addCell(PdfStyle.createValueCellWithHeight("Inserted", false, true, 20));
				bodytable.addCell(PdfStyle.createValueCellWithHeight(DataList.getIns_user_name(), false, true, 20));
				bodytable.addCell(PdfStyle.createValueCellWithHeight(DataList.getInsert_date(), false, true, 20));
				bodytable.addCell(PdfStyle.createValueCellWithHeight(DataList.getSup_ins_ip_add(), false, true, 20));
				
				rows++;
				}
				
//				if(DataList.getMod_user_name()!=null && !DataList.getMod_user_name().isEmpty())
				if(DataList.getCurr_status().equalsIgnoreCase("LOG"))
				{
					bodytable.addCell(PdfStyle.createValueCellWithHeight("", false, true, 20));
					bodytable.addCell(PdfStyle.createValueCellWithHeight("", false, true, 20));
					bodytable.addCell(PdfStyle.createValueCellWithHeight("", false, true, 20));
					bodytable.addCell(PdfStyle.createValueCellWithHeight("", false, true, 20));
					bodytable.addCell(PdfStyle.createValueCellWithHeight("", false, true, 20));
					bodytable.addCell(PdfStyle.createValueCellWithHeight("", false, true, 20));
					bodytable.addCell(PdfStyle.createValueCellWithHeight("Modified", false, true, 20));
					bodytable.addCell(PdfStyle.createValueCellWithHeight(DataList.getMod_user_name(), false, true, 20));
					bodytable.addCell(PdfStyle.createValueCellWithHeight(DataList.getModify_date(), false, true, 20));
					bodytable.addCell(PdfStyle.createValueCellWithHeight(DataList.getSup_mod_ip_add(), false, true, 20));
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
	
	
	public PdfPTable newPage(ColumnText column, PdfPTable bodytable, int rows) throws Exception {
		try {
			PdfPCell cell = new PdfPCell();
			column.setSimpleColumn(20, 20, 825, 486);
			
			cell=new PdfPCell(PdfStyle.createSingleLine());
			cell.setColspan(13);		
			bodytable.addCell(cell);
			
			column.addElement(bodytable);
			column.go();
			bodytable = new PdfPTable(10);
			bodytable.setWidthPercentage(100);
			bodytable.setWidths(this.bodyColWidth);

		} catch (DocumentException e) {
			throw e;
		}
		
		
		return bodytable;
	}
	
	private PdfPTable createHeader(SupplierMasterModel DataList, String comp_name, String userName, String date, String frm_date, String to_date, int rows)
			throws DocumentException, MalformedURLException, IOException {
		
			PdfPTable header = new PdfPTable(1);  //main header table
			header.getDefaultCell().setPadding(0.0f);
			header.setWidthPercentage(100);
			
			PdfPCell title_1 = PdfStyle.createUltimateCellWithoutBorder(comp_name, 1, 5, 12, 18, false);
			PdfPCell title_2 = PdfStyle.createUltimateCellWithoutBorder("Supplier Master Audit Trail", 1, 5, 12, 18, false);
			header.addCell(title_1);
			header.addCell(title_2);
			//header.addCell(PdfStyle.createLabelCellWithoutBorder(""));
			
			PdfPTable h1 = new PdfPTable(3);
			h1.setWidthPercentage(100); 
			h1.setWidths(new float[] {5.4f, 5.4f, 5.4f}); 
			h1.addCell(PdfStyle.createUltimateCellWithoutBorder("Report Dated : "+date,0,5,9,18,false));
			h1.addCell(PdfStyle.createUltimateCellWithoutBorder("From "+frm_date+" to "+to_date,1,5,9,18,false));
			h1.addCell(PdfStyle.createUltimateCellWithoutBorder("User : "+userName,2,5,9,18,false));			
			
			PdfPTable bodyHeader = new PdfPTable(10);  // set body column name
			bodyHeader.setWidthPercentage(100);
			bodyHeader.setWidths(this.bodyColWidth);
			bodyHeader.addCell(PdfStyle.createLabelCellWithBGColor("Supplier Name",0.7f));
			bodyHeader.addCell(PdfStyle.createLabelCellWithBGColor("Old Name",0.7f));
			bodyHeader.addCell(PdfStyle.createLabelCellWithBGColor("TIN Number",0.7f));
			bodyHeader.addCell(PdfStyle.createLabelCellWithBGColor("CST Number",0.7f));
			bodyHeader.addCell(PdfStyle.createLabelCellWithBGColor("TAN Number",0.7f));
			bodyHeader.addCell(PdfStyle.createLabelCellWithBGColor("PAN Number",0.7f));
			bodyHeader.addCell(PdfStyle.createLabelCellWithBGColor("Map Code",0.7f));
			bodyHeader.addCell(PdfStyle.createLabelCellWithBGColor("Sub Company",0.7f));
			bodyHeader.addCell(PdfStyle.createLabelCellWithBGColor("Record Type",0.7f));
			bodyHeader.addCell(PdfStyle.createLabelCellWithBGColor("Status",0.7f));
			
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
			
			rows+=2;
			
			PdfPCell c1 = new PdfPCell(h1);
			c1.setBorder(0);
			header.addCell(c1);
			header.addCell(bodyHeader);
			
			return header;
	}
}
