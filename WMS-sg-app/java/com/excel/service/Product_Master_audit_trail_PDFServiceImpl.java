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
import com.excel.model.ItemMasterAuditTrail;
import com.excel.repository.UserMasterRepository;
import com.excel.utility.CodifyErrors;
import com.excel.utility.HeaderPageEvent;
import com.excel.utility.MedicoConstants;
import com.excel.utility.PdfStyle;
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
public class Product_Master_audit_trail_PDFServiceImpl implements Product_Master_audit_trail_PDFService{
	@Autowired
	private UserMasterRepository userMasterRepository;
	@Autowired private UserMasterService usermasterservice;

@Autowired private JwtProvider tokenProvider;
@Autowired private UserMasterService userMasterService;


	 private int limit=0;
	 private String user_name;
	 
	@Override
	public String generate_product_Master_audit_trail_pdf(List<ItemMasterAuditTrail> list,String companyname, String username,long div_id, long sub_comp_id,String brand_str,long prod_id,Long prod_type,boolean checkMe,String date,HttpSession session,HttpServletRequest request)
			throws Exception {
		float[] bodyColWidth = new float[] { 1.0f, 4.0f, 1.5f, 1.5f, 1.0f,     1.0f, 1.0f, 1.0f, 1.0f, 1.0f,     1.5f, 1.0f, 1.0f, 1.5f, 1.5f, 1.5f, 1.5f};
		if(checkMe){
			limit=16;
		}else{
			limit=16;
		}
		
		String filepath = MedicoConstants.PDF_FILE_PATH;
		String comp_name = companyname;
		
		user_name = username;
		
		System.out.println("Inside getPDF==============================================");
		
		FileOutputStream os = null;
		PrintStream ps = null;
		long timevar = new Date().getTime();
		String filename = "ItemMasterAuditTrail" + timevar + ".pdf";
		
		
		StringBuffer path = null;
		 path = new StringBuffer(filepath).append("\\");
			path.append(filename);
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
		
		if(prod_type.equals(205l)) {//Samples
			bodyColWidth = addX(bodyColWidth.length, bodyColWidth, 1.0f);
		}
		
		if(prod_type.equals(204l)) {//PI
			bodyColWidth = addX(bodyColWidth.length, bodyColWidth, 1.2f);
		}
		if(prod_type.equals(0l)) {//PI
			bodyColWidth = addX(bodyColWidth.length, bodyColWidth, 1.0f);
			bodyColWidth = addX(bodyColWidth.length, bodyColWidth, 1.2f);
		}
		
		System.out.println("bodyColWidth :: "+ bodyColWidth.length);
		
		File docfile = new File(filepath + "\\files\\" + filename);
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
			String new_div="",old_div="";
			int rows = 0;
			isnew = true;			
			
			for (ItemMasterAuditTrail DataList : list) {
				new_prod = DataList.getSmp_prod_type();
				new_div = DataList.getDivision();
				if (rows >= limit) {
					bodytable = newPage(column, bodytable, rows,prod_type,bodyColWidth);
					document.newPage();
					rows = 0;
				}

				if (isnew) {
					event.setHeader(createHeader(DataList, comp_name,username,date,rows,prod_type,bodyColWidth));
					//rows++;
					isnew = false;
					//bodytable = new PdfPTable(17);
					
					if(prod_type.equals(205l)) {//Samples
						bodytable = new PdfPTable(18);
					}
					
					if(prod_type.equals(204l)) {//PI
						bodytable = new PdfPTable(18);
					}
					
					if(prod_type.equals(0l)) {//All
						bodytable = new PdfPTable(19);
					}
					
					bodytable.setWidthPercentage(100);
					bodytable.setWidths(bodyColWidth);					
				}
				
				
				if (!old_prod.equalsIgnoreCase(new_prod)) {
				//	 bodytable.addCell(PdfStyle.createDatachangeCell("Product Type : "+new_prod,17,BaseColor.RED));
					 
						if(prod_type.equals(205l)) {//Samples
							bodytable.addCell(PdfStyle.createDatachangeCell("Product Type : "+new_prod,18,BaseColor.RED));
						}
						if(prod_type.equals(204l)) {//PI
							bodytable.addCell(PdfStyle.createDatachangeCell("Product Type : "+new_prod,18,BaseColor.RED));
						}
						
						if(prod_type.equals(0l)) {//All
							bodytable.addCell(PdfStyle.createDatachangeCell("Product Type : "+new_prod,19,BaseColor.RED));
						}
						
					 rows++;
					// bodytable.addCell(PdfStyle.createDatachangeCell("Division : "+DataList.getDivision(),17,BaseColor.BLUE));
					 
					 if(prod_type.equals(205l)) {//Samples
						 bodytable.addCell(PdfStyle.createDatachangeCell("Division : "+DataList.getDivision(),18,BaseColor.BLUE));
					 }
						if(prod_type.equals(204l)) {//PI
							 bodytable.addCell(PdfStyle.createDatachangeCell("Division : "+DataList.getDivision(),18,BaseColor.BLUE));
						}
						 if(prod_type.equals(0l)) {//Samples
							 bodytable.addCell(PdfStyle.createDatachangeCell("Division : "+DataList.getDivision(),19,BaseColor.BLUE));
						 }
					 
					 rows++;
				}
				
				if(old_prod.equals(new_prod) && !old_div.equals(new_div))
				{
					//bodytable.addCell(PdfStyle.createDatachangeCell("Division : "+DataList.getDivision(),17,BaseColor.BLUE));
					
					 if(prod_type.equals(205l)) {//Samples
						 bodytable.addCell(PdfStyle.createDatachangeCell("Division : "+DataList.getDivision(),18,BaseColor.BLUE));
					 }
					 if(prod_type.equals(204l)) {//PI
						 bodytable.addCell(PdfStyle.createDatachangeCell("Division : "+DataList.getDivision(),18,BaseColor.BLUE));
					 }
					 if(prod_type.equals(0l)) {//Samples
						 bodytable.addCell(PdfStyle.createDatachangeCell("Division : "+DataList.getDivision(),19,BaseColor.BLUE));
					 }
					 
					 
					 rows++;
				}
				old_div=new_div;
				old_prod=new_prod;
				
				bodytable.addCell(PdfStyle.createValueCellWithHeightBoldTopBorder(DataList.getSmp_prod_cd(), false, true, 20));
				bodytable.addCell(PdfStyle.createValueCellWithHeightBoldTopBorder(DataList.getSmp_prod_name(), false, true, 20));
				bodytable.addCell(PdfStyle.createValueCellWithHeightBoldTopBorder(DataList.getPack_disp_nm(), false, true, 20));
				bodytable.addCell(PdfStyle.createValueCellWithHeightBoldTopBorder(DataList.getUom_disp_nm(), false, true, 20));
				bodytable.addCell(PdfStyle.createValueCellWithHeightBoldTopBorder(DataList.getForm(), false, true, 20));
				bodytable.addCell(PdfStyle.createValueCellWithHeightBoldTopBorder(DataList.getSmp_sch_ind(), false, true, 20));
				bodytable.addCell(PdfStyle.createValueCellWithHeightBoldTopBorder(DataList.getSmp_div_rq_ind(), false, true, 20));
				bodytable.addCell(PdfStyle.createValueCellWithHeightBoldTopBorder(DataList.getSmp_shelf_life(), true, true, 20));
				bodytable.addCell(PdfStyle.createValueCellWithHeightBoldTopBorder(DataList.getSmp_short_expiry_days(), true, true, 20));
				bodytable.addCell(PdfStyle.createValueCellWithHeightBoldTopBorder(DataList.getSmp_erp_prod_cd(), false, true, 20));
				bodytable.addCell(PdfStyle.createValueCellWithHeightBoldTopBorder(DataList.getSa_group_name(), false, true, 20));
				bodytable.addCell(PdfStyle.createValueCellWithHeightBoldTopBorder(DataList.getSmp_cog_rate().toString(), true, true, 20));
				bodytable.addCell(PdfStyle.createValueCellWithHeightBoldTopBorder(DataList.getSmp_cog_exe_rate().toString(), true, true, 20));
				bodytable.addCell(PdfStyle.createValueCellWithHeightBoldTopBorder(DataList.getSmp_rate().toString(), true, true, 20));
				bodytable.addCell(PdfStyle.createValueCellWithHeightBoldTopBorder(DataList.getSmp_status(), false, true, 20));
				
				
				bodytable.addCell(PdfStyle.createValueCellWithHeightBoldTopBorder(DataList.getSubcomp_nm(), false, true, 20));
				bodytable.addCell(PdfStyle.createValueCellWithHeightBoldTopBorder(DataList.getCurr_status(), false, true, 20));
				
				if(prod_type.equals(205l)) {//Samples
					bodytable.addCell(PdfStyle.createValueCellWithHeight(DataList.getProd_fcode(), false, true, 20));
				}
				
				if(prod_type.equals(204l)) {//PI
					bodytable.addCell(PdfStyle.createValueCellWithHeight(DataList.getGcma_number(), false, true, 20));
				}
				
				if(prod_type.equals(0l)) {//PI
					bodytable.addCell(PdfStyle.createValueCellWithHeight(DataList.getProd_fcode(), false, true, 20));
					bodytable.addCell(PdfStyle.createValueCellWithHeight(DataList.getGcma_number(), false, true, 20));
				}
				
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
				bodytable.addCell(PdfStyle.createValueCellWithHeight("", false, true, 20));
				bodytable.addCell(PdfStyle.createValueCellWithHeight("Inserted", false, true, 20));
				bodytable.addCell(PdfStyle.createValueCellWithHeight(DataList.getIns_user_name(), false, true, 20));
				bodytable.addCell(PdfStyle.createValueCellWithHeight(DataList.getSmp_ins_dt(), false, true, 20));
				bodytable.addCell(PdfStyle.createValueCellWithHeight(DataList.getSmp_ins_ip_add(), false, true, 20));
				
				if(prod_type.equals(205l)) {//Samples
					bodytable.addCell(PdfStyle.createValueCellWithHeight("", false, true, 20));
				}
				
				if(prod_type.equals(204l)) {//PI
					bodytable.addCell(PdfStyle.createValueCellWithHeight(DataList.getGcma_expiry_dt(), false, true, 20));
				}
				
				if(prod_type.equals(0l)) {//All
					bodytable.addCell(PdfStyle.createValueCellWithHeight("", false, true, 20));
					bodytable.addCell(PdfStyle.createValueCellWithHeight(DataList.getGcma_expiry_dt(), false, true, 20));
				}
				
				rows++;
				
				if(DataList.getCurr_status().equalsIgnoreCase("LOG"))
				{
					bodytable.addCell(PdfStyle.createValueCellWithHeight(DataList.getSmp_prod_cd(), false, true, 20));
					bodytable.addCell(PdfStyle.createValueCellWithHeight(DataList.getSmp_prod_name(), false, true, 20));
					bodytable.addCell(PdfStyle.createValueCellWithHeight(DataList.getPack_disp_nm(), false, true, 20));
					bodytable.addCell(PdfStyle.createValueCellWithHeight(DataList.getUom_disp_nm(), false, true, 20));
					bodytable.addCell(PdfStyle.createValueCellWithHeight(DataList.getForm(), false, true, 20));
					bodytable.addCell(PdfStyle.createValueCellWithHeight(DataList.getSmp_sch_ind(), false, true, 20));
					bodytable.addCell(PdfStyle.createValueCellWithHeight(DataList.getSmp_div_rq_ind(), false, true, 20));
					bodytable.addCell(PdfStyle.createValueCellWithHeight(DataList.getSmp_shelf_life(), true, true, 20));
					bodytable.addCell(PdfStyle.createValueCellWithHeight(DataList.getSmp_short_expiry_days(), true, true, 20));
					bodytable.addCell(PdfStyle.createValueCellWithHeight(DataList.getSmp_erp_prod_cd(), false, true, 20));
					bodytable.addCell(PdfStyle.createValueCellWithHeight(DataList.getSa_group_name(), false, true, 20));
					bodytable.addCell(PdfStyle.createValueCellWithHeight(DataList.getSmp_cog_rate().toString(), true, true, 20));
					bodytable.addCell(PdfStyle.createValueCellWithHeight(DataList.getSmp_cog_exe_rate().toString(), true, true, 20));
					bodytable.addCell(PdfStyle.createValueCellWithHeight(DataList.getSmp_rate().toString(), true, true, 20));
					bodytable.addCell(PdfStyle.createValueCellWithHeight(DataList.getSmp_status(), false, true, 20));
					
					
					bodytable.addCell(PdfStyle.createValueCellWithHeight(DataList.getSubcomp_nm(), false, true, 20));
					bodytable.addCell(PdfStyle.createValueCellWithHeight(DataList.getCurr_status(), false, true, 20));
					
					if(prod_type.equals(205l)) {//Samples
						bodytable.addCell(PdfStyle.createValueCellWithHeight(DataList.getProd_fcode(), false, true, 20));
					}
					
					if(prod_type.equals(204l)) {//PI
						bodytable.addCell(PdfStyle.createValueCellWithHeight(DataList.getGcma_number(), false, true, 20));
					}
					
					if(prod_type.equals(0l)) {//PI
						bodytable.addCell(PdfStyle.createValueCellWithHeight(DataList.getProd_fcode(), false, true, 20));
						bodytable.addCell(PdfStyle.createValueCellWithHeight(DataList.getGcma_number(), false, true, 20));
					}
					
					
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
					bodytable.addCell(PdfStyle.createValueCellWithHeight("", false, true, 20));
					bodytable.addCell(PdfStyle.createValueCellWithHeight("Modified", false, true, 20));
					bodytable.addCell(PdfStyle.createValueCellWithHeight(DataList.getMod_user_name(), false, true, 20));
					bodytable.addCell(PdfStyle.createValueCellWithHeight(DataList.getSmp_mod_dt(), false, true, 20));
					bodytable.addCell(PdfStyle.createValueCellWithHeight(DataList.getSmp_mod_ip_add(), false, true, 20));
					
					if(prod_type.equals(205l)) {//Samples
						bodytable.addCell(PdfStyle.createValueCellWithHeight("", false, true, 20));
					}
					
					if(prod_type.equals(204l)) {//PI
						bodytable.addCell(PdfStyle.createValueCellWithHeight(DataList.getGcma_expiry_dt(), false, true, 20));
					}
					
					if(prod_type.equals(0l)) {//All
						bodytable.addCell(PdfStyle.createValueCellWithHeight("", false, true, 20));
						bodytable.addCell(PdfStyle.createValueCellWithHeight(DataList.getGcma_expiry_dt(), false, true, 20));
					}
					rows++;
				}
				//for detail part, print if checked
			if(checkMe){
				bodytable.addCell(PdfStyle.createLabelCellWithBGColorNoBold("",0.9f));
				bodytable.addCell(PdfStyle.createLabelCellWithBGColorNoBold("Alternate Name",0.9f));
				bodytable.addCell(PdfStyle.createLabelCellWithBGColorNoBold("Form",0.9f));
				bodytable.addCell(PdfStyle.createLabelCellWithBGColorNoBold("Stock Days",0.9f));
				bodytable.addCell(PdfStyle.createLabelCellWithBGColorNoBold("Exp Reqd?",0.9f));
				bodytable.addCell(PdfStyle.createLabelCellWithBGColorNoBold("Batch Reqd?",0.9f));
				bodytable.addCell(PdfStyle.createLabelCellWithBGColorNoBold("Shipper Qty",0.9f));
				bodytable.addCell(PdfStyle.createLabelCellWithBGColorNoBold("Box Qty",0.9f));
				bodytable.addCell(PdfStyle.createLabelCellWithBGColorNoBold("Strip Qty",0.9f));
				bodytable.addCell(PdfStyle.createLabelCellWithBGColorNoBold("Max Alloc",0.9f));
				bodytable.addCell(PdfStyle.createLabelCellWithBGColorNoBold("Min Alloc",0.9f));
				bodytable.addCell(PdfStyle.createLabelCellWithBGColorNoBold("STD Alloc",0.9f));
				bodytable.addCell(PdfStyle.createLabelCellWithBGColorNoBold("Shipper Vol",0.9f));
				bodytable.addCell(PdfStyle.createLabelCellWithBGColorNoBold("Shipper Net Wt",0.9f));
				bodytable.addCell(PdfStyle.createLabelCellWithBGColorNoBold("Shipper GWT",0.9f));
				bodytable.addCell(PdfStyle.createLabelCellWithBGColorNoBold("Specific Div?",0.9f));
				bodytable.addCell(PdfStyle.createLabelCellWithBGColorNoBold("Alloc Multiples?",0.9f));//17
				
				if(prod_type.equals(205l)) {//Samples
					bodytable.addCell(PdfStyle.createLabelCellWithBGColorNoBold("",0.9f));//17
				}
				
				if(prod_type.equals(204l)) {//PI
					bodytable.addCell(PdfStyle.createLabelCellWithBGColorNoBold("",0.9f));//17
				}
				
				if(prod_type.equals(0l)) {//All
					bodytable.addCell(PdfStyle.createLabelCellWithBGColorNoBold("",0.9f));//18
					bodytable.addCell(PdfStyle.createLabelCellWithBGColorNoBold("",0.9f));//19
				}
				
				rows++;
				//---------------------------------------------------------------------------------------------
				
				bodytable.addCell(PdfStyle.createValueCellWithHeight(" ", false, true, 20));
				bodytable.addCell(PdfStyle.createValueCellWithHeight(DataList.getSmp_alter_name(), false, true, 20));
				bodytable.addCell(PdfStyle.createValueCellWithHeight(DataList.getForm(), false, true, 20));
				bodytable.addCell(PdfStyle.createValueCellWithHeight(DataList.getSmp_expiry_rq_ind(), false, true, 20));
				bodytable.addCell(PdfStyle.createValueCellWithHeight(DataList.getSmp_batch_rq_ind(), false, true, 20));
				bodytable.addCell(PdfStyle.createValueCellWithHeight(DataList.getSmp_qty_shipper(), true, true, 20));
				bodytable.addCell(PdfStyle.createValueCellWithHeight(DataList.getSmp_qty_box(), true, true, 20));
				bodytable.addCell(PdfStyle.createValueCellWithHeight(DataList.getSmp_qty_strip(), true, true, 20));
				bodytable.addCell(PdfStyle.createValueCellWithHeight(DataList.getSmp_max_alloc_qty().toString(), true, true, 20));
				bodytable.addCell(PdfStyle.createValueCellWithHeight(DataList.getSmp_min_alloc_qty().toString(), true, true, 20));
				bodytable.addCell(PdfStyle.createValueCellWithHeight(DataList.getSmp_std_alloc_qty().toString(), true, true, 20));
				bodytable.addCell(PdfStyle.createValueCellWithHeight(DataList.getSmp_shipper_vol().toString(), true, true, 20));
				bodytable.addCell(PdfStyle.createValueCellWithHeight(DataList.getSmp_shipper_nwt().toString(), true, true, 20));
				bodytable.addCell(PdfStyle.createValueCellWithHeight(DataList.getSmp_shipper_gwt().toString(), true, true, 20));
				bodytable.addCell(PdfStyle.createValueCellWithHeight(DataList.getSmp_alloc_multiples().toString(), false, true, 20));
				
				
				bodytable.addCell(PdfStyle.createValueCellWithHeight("", false, true, 20));
				bodytable.addCell(PdfStyle.createValueCellWithHeight("", false, true, 20));
				
				if(prod_type.equals(205l)) {//Samples
					bodytable.addCell(PdfStyle.createValueCellWithHeight("", false, true, 20));
				}
				
				if(prod_type.equals(204l)) {//PI
					bodytable.addCell(PdfStyle.createValueCellWithHeight("", false, true, 20));
				}
				
				if(prod_type.equals(0l)) {//All
					bodytable.addCell(PdfStyle.createValueCellWithHeight("", false, true, 20));
					bodytable.addCell(PdfStyle.createValueCellWithHeight("", false, true, 20));
				}
				
				rows++;
			}
			}
			newPage(column, bodytable, rows,prod_type,bodyColWidth);
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
			}finally {
				bodyColWidth = null;
			}
		}
		System.out.println("PATH$%#$#$$$$#$$" + path + "\\files\\" + filename);
		return filename;
	}

	
	
	public PdfPTable newPage(ColumnText column, PdfPTable bodytable, int rows,Long prod_type,float[] bodyColWidth) throws Exception {
		try {
			PdfPCell cell = new PdfPCell();
			column.setSimpleColumn(20, 20, 825, 477);
			
			cell=new PdfPCell(PdfStyle.createSingleLine());
			//cell.setColspan(17);	
			if(prod_type.equals(205l)) {//Samples
			cell.setColspan(18);		
			}
			if(prod_type.equals(204l)) {//PI
				cell.setColspan(18);		
			}
			if(prod_type.equals(0l)) {//All
				cell.setColspan(19);		
			}
			
			
			bodytable.addCell(cell);
			
			column.addElement(bodytable);
			column.go();
		//	bodytable = new PdfPTable(17);
			if(prod_type.equals(205l)) {//Samples
				bodytable = new PdfPTable(18);
				}
				if(prod_type.equals(204l)) {//PI
					bodytable = new PdfPTable(18);		
				}
				if(prod_type.equals(0l)) {//All
					bodytable = new PdfPTable(19);
				}
			bodytable.setWidthPercentage(100);
			bodytable.setWidths(bodyColWidth);

		} catch (DocumentException e) {
			throw e;
		}
		return bodytable;
	}
	
	private PdfPTable createHeader(ItemMasterAuditTrail DataList, String comp_name, String userName, String date,int rows,Long prod_type,float[] bodyColWidth)
			throws DocumentException, MalformedURLException, IOException {
		
			PdfPTable header = new PdfPTable(1);  //main header table
			header.getDefaultCell().setPadding(0.0f);
			header.setWidthPercentage(100);
			
			PdfPCell title_1 = PdfStyle.createUltimateCellWithoutBorder(comp_name, 1, 5, 12, 18, false);
			PdfPCell title_2 = PdfStyle.createUltimateCellWithoutBorder("Item Master Audit Trail", 1, 5, 12, 18, false);
			header.addCell(title_1);
			header.addCell(title_2);
			//header.addCell(PdfStyle.createLabelCellWithoutBorder(""));
			
			PdfPTable h1 = new PdfPTable(3);
			h1.setWidthPercentage(100); 
			h1.setWidths(new float[] {5.4f, 5.4f, 5.4f}); 
			h1.addCell(PdfStyle.createUltimateCellWithoutBorder("Report Dated : "+date,0,5,9,18,false));
			h1.addCell(PdfStyle.createUltimateCellWithoutBorder(" ",1,5,9,18,false));
			h1.addCell(PdfStyle.createUltimateCellWithoutBorder("User : "+user_name,2,5,9,18,false));			
			
			
			PdfPTable bodyHeader = null;
			//bodyHeader = new PdfPTable(17);  // set body column name
			
			if(prod_type.equals(205l)) {//Samples
			bodyHeader = new PdfPTable(18);  // set body column name
			}
			if(prod_type.equals(204l)) {//PI
			bodyHeader = new PdfPTable(18);  // set body column name
			}
			
			if(prod_type.equals(0l)) {//All
			bodyHeader = new PdfPTable(19);  // set body column name
			}
			
			bodyHeader.setWidthPercentage(100);
			System.out.println("bodyColWidth header: "+bodyColWidth.length);
			bodyHeader.setWidths(bodyColWidth);
			bodyHeader.addCell(PdfStyle.createLabelCellWithBGColor("Code",0.7f));
			bodyHeader.addCell(PdfStyle.createLabelCellWithBGColor("Product Name",0.7f));
			bodyHeader.addCell(PdfStyle.createLabelCellWithBGColor("Pack",0.7f));
			bodyHeader.addCell(PdfStyle.createLabelCellWithBGColor("UOM",0.7f));
			bodyHeader.addCell(PdfStyle.createLabelCellWithBGColor("Form",0.7f));
			bodyHeader.addCell(PdfStyle.createLabelCellWithBGColor("Sch/Non ",0.7f));
			bodyHeader.addCell(PdfStyle.createLabelCellWithBGColor("Div Reqd?",0.7f));
			bodyHeader.addCell(PdfStyle.createLabelCellWithBGColor("Shelf Life",0.7f));
			bodyHeader.addCell(PdfStyle.createLabelCellWithBGColor("Short Exp",0.7f));
			bodyHeader.addCell(PdfStyle.createLabelCellWithBGColor("ERP Code",0.7f));
			bodyHeader.addCell(PdfStyle.createLabelCellWithBGColor("Brand",0.7f));
			bodyHeader.addCell(PdfStyle.createLabelCellWithBGColor("COG Rate",0.7f));
			bodyHeader.addCell(PdfStyle.createLabelCellWithBGColor("COG Excise",0.7f));
			bodyHeader.addCell(PdfStyle.createLabelCellWithBGColor("Rate",0.7f));
			bodyHeader.addCell(PdfStyle.createLabelCellWithBGColor("Status",0.7f));
			bodyHeader.addCell(PdfStyle.createLabelCellWithBGColor("Sub Company",0.7f));
			bodyHeader.addCell(PdfStyle.createLabelCellWithBGColor("Rec Type",0.7f));//17
			
			
			
			if(prod_type.equals(205l)) {//Samples
				bodyHeader.addCell(PdfStyle.createLabelCellWithBGColor("fcode",0.7f));//18
			}
			
			if(prod_type.equals(204l)) {//PI
				bodyHeader.addCell(PdfStyle.createLabelCellWithBGColor("Gcma",0.7f));//18
			}
			
			if(prod_type.equals(0l)) {//All
				bodyHeader.addCell(PdfStyle.createLabelCellWithBGColor("fcode",0.7f));//18
				bodyHeader.addCell(PdfStyle.createLabelCellWithBGColor("Gcma",0.7f));//19
			}
			rows++;
			
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
			bodyHeader.addCell(PdfStyle.createLabelCellWithBGColor("Activity",0.7f));
			bodyHeader.addCell(PdfStyle.createLabelCellWithBGColor("Username",0.7f));
			bodyHeader.addCell(PdfStyle.createLabelCellWithBGColor("Date & time",0.7f));
			bodyHeader.addCell(PdfStyle.createLabelCellWithBGColor("IP used",0.7f));//17
			
			if(prod_type.equals(205l)) {//Samples
				bodyHeader.addCell(PdfStyle.createLabelCellWithBGColor("",0.7f));//18
			}
			if(prod_type.equals(204l)) {//PI
				bodyHeader.addCell(PdfStyle.createLabelCellWithBGColor("No & Date",0.7f));//18
			}
			
			if(prod_type.equals(0l)) {//All
				bodyHeader.addCell(PdfStyle.createLabelCellWithBGColor("",0.7f));//18
				bodyHeader.addCell(PdfStyle.createLabelCellWithBGColor("No & Date",0.7f));//19
			}
			
			
			rows+=2;
			
			PdfPCell c1 = new PdfPCell(h1);
			c1.setBorder(0);
			header.addCell(c1);
			header.addCell(bodyHeader);
			
			
		
			return header;
	}
	
	
	public static float[] addX(int n, float arr[], float x) 
    { 
        int i; 
  
        // create a new array of size n+1 
        float newarr[] = new float[n + 1]; 
  
        // insert the elements from 
        // the old array into the new array 
        // insert all elements till n 
        // then insert x at n+1 
        for (i = 0; i < n; i++) 
            newarr[i] = arr[i]; 
  
        newarr[n] = x; 
  
        return newarr; 
    } 
}
