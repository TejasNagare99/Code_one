package com.excel.service;

import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.io.PrintStream;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.excel.configuration.JwtProvider;
import com.excel.model.ViewGrnLabelPrinting_java;
import com.excel.repository.UserMasterRepository;
import com.excel.utility.CodifyErrors;
import com.excel.utility.MedicoConstants;
import com.excel.utility.PdfStyle;
import com.itextpdf.text.Document;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Rectangle;
import com.itextpdf.text.pdf.ColumnText;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;

@Service
public class generateLabelPdfServiceImpl implements generateLabelPdfService{
	@Autowired
	private UserMasterRepository userMasterRepository;
	@Autowired private UserMasterService usermasterservice;
	@Autowired private JwtProvider tokenProvider;
	@Autowired private UserMasterService userMasterService;

	int add = 0;
	private String path;
	private Boolean pageflag;
	private int limit=8;
	Rectangle colRect = new Rectangle(15, 10, 580, 820);

	@Override
	public String generateLabelPdf(List<ViewGrnLabelPrinting_java> list, String bin_no_id,HttpSession session,HttpServletRequest request) throws Exception {
		float[] bodyTableWidth = new float[] {5.6f, 5.6f};
		float[] internalTableWidth = new float[] {1.9f, 3.7f};
		path = MedicoConstants.PDF_FILE_PATH;
		FileOutputStream os = null;
		PrintStream ps = null;
		long timevar = new Date().getTime();
		String filename = "grnLabel" + timevar + ".pdf";
		System.out.println("filename: " +filename);
		File docfile1 = new File(path + "\\files\\");
		docfile1.mkdirs();
		File docfile = new File(path + "\\files\\" + filename);
		OutputStream file = new FileOutputStream(docfile);
		Document document = new Document(PageSize.A4);
		PdfWriter writer =PdfWriter.getInstance(document, file);
		String uname = this.tokenProvider.getUsernameFromRequest(request);String empId = this.userMasterService.getEmpIdByUsername(uname);
		 writer = usermasterservice.generatePDFlock(empId, writer);
		document.open();
		document.setMarginMirroringTopBottom(false);
		ColumnText column = new ColumnText(writer.getDirectContent());
		
		try {
			PdfPTable bodytable = new PdfPTable(2);
			bodytable.getDefaultCell().setPadding(0.0f);
			bodytable.setWidthPercentage(100);
			bodytable.setWidths(bodyTableWidth);
			int i=0, box=0, super_box=0; //added by krishna:4-02-2017
			PdfPCell emptyLabel=PdfStyle.createUltimateCellWithoutBorder(" ",0, 5, 10, 18, true);
			PdfPCell emptyValue = PdfStyle.createUltimateCellWithoutBorder(" ",0, 5, 10, 18, false);
			PdfPCell item_lbl=PdfStyle.createUltimateCellWithoutBorder("    Item Code : ", 0, 5, 11, 18, true);
			PdfPCell batch_lbl=PdfStyle.createUltimateCellWithoutBorder("    Batch/Artwork : ", 0, 5, 11, 18, true);
			PdfPCell desc_lbl=PdfStyle.createUltimateCellWithoutBorder("    Description : ", 0, 5, 11, 18, true);
			PdfPCell div_lbl=PdfStyle.createUltimateCellWithoutBorder("    BU/Division : ", 0, 5, 11, 18, true);
			PdfPCell box_lbl=PdfStyle.createUltimateCellWithoutBorder("    Box No : ", 0, 5, 11, 18, true);
			PdfPCell bin_lbl=null;
			if(bin_no_id.trim().equalsIgnoreCase("Y")){
			   bin_lbl = PdfStyle.createUltimateCellWithoutBorder("    Bin : ", 0, 5, 11, 18, true);
			}else{
			    bin_lbl = PdfStyle.createUltimateCellWithoutBorder("         ", 0, 5, 11, 18, true);
			}
			for (ViewGrnLabelPrinting_java challan : list) {
				box=challan.getGrnd_noofboxes()!=null ? challan.getGrnd_noofboxes() : 0;
				super_box=super_box+box;
				if(box>=0){
					//System.out.println("box::"+box+"::i::"+i);
					
					String prod_name=challan.getSmp_prod_name();
					String str1="", str2="", str3="";
					if(prod_name.length()>25){
						str1=prod_name.substring(0,25);
						str2=prod_name.substring(25);
						if(str2.length()>25){
							str3=str2.substring(25);
							str2=str2.substring(0, 25);
							if(str3.length()>25) str3=str3.substring(0, 25);
						}
					}else{
						str1=prod_name;
					}
					//System.out.println("Prod1:: "+str1);
					//System.out.println("Prod2:: "+str2);
					//System.out.println("Prod3:: "+str3);
					
					PdfPCell item_val = PdfStyle.createUltimateCellWithoutBorder(challan.getSmp_prod_cd(), 0, 5, 11, 18, false);
					PdfPCell desc_val_1 = PdfStyle.createUltimateCellWithoutBorder(str1,0, 5, 11, 18, false);
					PdfPCell desc_val_2 = PdfStyle.createUltimateCellWithoutBorder(str2,0, 5, 11, 18, false);
					PdfPCell desc_val_3 = PdfStyle.createUltimateCellWithoutBorder(str3,0, 5, 11, 18, false);
					PdfPCell batch_val = PdfStyle.createUltimateCellWithoutBorder(challan.getBatch_no(),0, 5, 11, 18, false);
					PdfPCell div_val = PdfStyle.createUltimateCellWithoutBorder(challan.getDivision(),0, 5, 11, 18, false);
					PdfPCell bin_val =null;
					if(bin_no_id.equalsIgnoreCase("Y"))
					 bin_val = PdfStyle.createUltimateCellWithoutBorder(challan.getBin_number(),0, 5, 11, 18, false);
					else
					 bin_val = PdfStyle.createUltimateCellWithoutBorder("",0, 5, 11, 18, false);
					for(int m=1; m<=box; m++){
						i++;
						
						PdfPTable internalBox = new PdfPTable(2);
						internalBox.setWidths(internalTableWidth);
						internalBox.addCell(emptyLabel);
						internalBox.addCell(emptyValue);
						internalBox.addCell(emptyLabel);
						internalBox.addCell(emptyValue);
						internalBox.addCell(item_lbl);
						internalBox.addCell(item_val);
						internalBox.addCell(batch_lbl);
						internalBox.addCell(batch_val);
						
						internalBox.addCell(desc_lbl);
						internalBox.addCell(desc_val_1);
						internalBox.addCell(emptyLabel);
						internalBox.addCell(desc_val_2);
						internalBox.addCell(emptyLabel);
						internalBox.addCell(desc_val_3);
						internalBox.addCell(div_lbl);
						internalBox.addCell(div_val);
						
						PdfPCell box_val = PdfStyle.createUltimateCellWithoutBorder(m+" of " +box, 0, 5, 11, 18, false);
						internalBox.addCell(box_lbl);
						internalBox.addCell(box_val);
						
						internalBox.addCell(bin_lbl);
						internalBox.addCell(bin_val);
						internalBox.addCell(emptyLabel);
						internalBox.addCell(emptyValue);
						PdfPCell iCell = new PdfPCell(internalBox);
						//iCell.setBorder(0);
						bodytable.addCell(iCell);
						
						System.err.println("m::"+m+"i::"+i);
						if(limit==i){
							System.err.println("LIMITm::"+m+"i::"+i);
							column.setSimpleColumn(colRect);
							column.addElement(bodytable);
							column.go();
							document.newPage();
							
							bodytable = new PdfPTable(2);
							bodytable.getDefaultCell().setPadding(0.0f);
							bodytable.setWidthPercentage(100);
							bodytable.setWidths(bodyTableWidth);
							i=0;
						}
					}
					
					if(limit==i){
						System.err.println("LIMIT:::i::"+i);
						column.setSimpleColumn(colRect);
						column.addElement(bodytable);
						column.go();
						document.newPage();
						
						bodytable = new PdfPTable(2);
						bodytable.getDefaultCell().setPadding(0.0f);
						bodytable.setWidthPercentage(100);
						bodytable.setWidths(bodyTableWidth);
						i=0;
					}
				
				}
				
			}
			if(super_box%2!=0){
				PdfPTable internalBox = new PdfPTable(2);
				internalBox.setWidths(internalTableWidth);
				internalBox.addCell(emptyLabel);
				internalBox.addCell(emptyValue);
				PdfPCell iCell = new PdfPCell(internalBox);
				bodytable.addCell(iCell);
			}
			
			column.setSimpleColumn(colRect);
			column.addElement(bodytable);
			column.go();
			
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
				if(writer!=null){
					writer.flush();
					writer.close();
				}
			} catch (Exception e) {
				System.out.println("Error Occurred :" +e.getMessage());// uncomment asneeded --;
			}
		}
		return filename;
	}

	public void setPath(String path) {
		this.path = path;
	}

	public String getPath() {
		return path;
	}

	public void setLimit(int limit) {
		this.limit = limit;
	}

	public int getLimit() {
		return limit;
	}

	public void setPageflag(Boolean pageflag) {
		this.pageflag = pageflag;
	}

	public Boolean getPageflag() {
		return pageflag;
	}
	
}

