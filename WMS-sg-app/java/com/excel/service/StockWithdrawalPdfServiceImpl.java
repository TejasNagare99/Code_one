package com.excel.service;

import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.io.PrintStream;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.excel.bean.PrintBean;
import com.excel.configuration.JwtProvider;
import com.excel.model.ViewStockWithdrawalVoucherPrint;
import com.excel.repository.PrintRepository;
import com.excel.repository.UserMasterRepository;
import com.excel.utility.CodifyErrors;
import com.excel.utility.FooterWithoutPageNo;
import com.excel.utility.MedicoConstants;
import com.excel.utility.PdfStyle;
import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.Font.FontFamily;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.Rectangle;
import com.itextpdf.text.pdf.ColumnText;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;

@Service
public class StockWithdrawalPdfServiceImpl implements StockWithdrawalPdfService{

	@Autowired PrintRepository printrepository;
	@Autowired
    private UserMasterRepository userMasterRepository;
	@Autowired private UserMasterService usermasterservice;

@Autowired private JwtProvider tokenProvider;
@Autowired private UserMasterService userMasterService;

	 private HashMap<String, String> printLabel;
	 private float[] bodyTableWidth = new float[] { 3f,2f,2f,1f,1f,1f};
	 private int limit=14;
	 
	private int picklimit=17;
	private float[] pickbodyTableWidth = new float[] { 5f,1f,2f,2f};
	@Override
	public String generateStockwithdrawalpdf(PrintBean pb,
			List<ViewStockWithdrawalVoucherPrint> list,String companyName) throws Exception {
		ViewStockWithdrawalVoucherPrint footer;

		boolean pageflag;
		StringBuffer path = null;
		String filepath = MedicoConstants.PDF_FILE_PATH;
	
		System.out.println("path  : " + path);
		FileOutputStream os = null;
		PrintStream ps = null;
		long timevar = new Date().getTime();
		String filename = "Stockwithdrawalvoucher" + timevar + ".pdf";
		System.out.println("filename 11 " +filename);
		
		 path = new StringBuffer(filepath).append("\\");
			path.append("files\\" +filename);
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
		
		File docfile = new File(path.toString());
		OutputStream file = new FileOutputStream(docfile);
		Document document = new Document(PageSize.A4);
		PdfWriter writer =PdfWriter.getInstance(document, file);
		 Rectangle rect = new Rectangle(20, 28, 580, 855);
	        writer.setBoxSize("header", rect);
	        rect = new Rectangle(20, 2, 580,210);
	        writer.setBoxSize("footer", rect);
	      //  int pagecount=writer.getPageNumber();
	        int pagecount=1;
		FooterWithoutPageNo event = new FooterWithoutPageNo();
	    writer.setPageEvent(event);
	    
	    
		document.open();
		document.setMarginMirroringTopBottom(false);
		boolean isnew = false;
		boolean for_new_page = false;
		ColumnText column = new ColumnText(writer.getDirectContent());
		try {
			BigDecimal total = new BigDecimal(0);
			
			Set<String> challanset = new LinkedHashSet<String>();
			for (ViewStockWithdrawalVoucherPrint challan : list) {
				challanset.add(challan.getSwv_id().toString());
				
			}
			PdfPTable bodytable = null;
			for (String num : challanset) {
				List<ViewStockWithdrawalVoucherPrint> list_new_page = new ArrayList<ViewStockWithdrawalVoucherPrint>();
				isnew = true;
				for_new_page=true;
				PdfPTable maintable1 = new PdfPTable(1);
				maintable1.setWidthPercentage(100);
				PdfPCell cell = null;
				pageflag=false;
				//pagecount=1;

				PdfPTable hcell =null;
				int rows=0;
				bodytable = new PdfPTable(6);
				bodytable.getDefaultCell().setPadding(0.0f);
				bodytable.setWidthPercentage(100);
				bodytable.setWidths(bodyTableWidth);
				total=BigDecimal.ZERO;
				for (ViewStockWithdrawalVoucherPrint challan : list) {
					
					if(rows>=limit){
						pageflag=true;
						bodytable=newPage(column, bodytable, event,printLabel, false, rows,pageflag,pagecount,total,Long.valueOf(pb.getLocationId()),  pb.getFromChallan(),
								 pb.getToChallan(),list,companyName) ;
						document.newPage();
						pagecount++;
						rows=0;
					}
					
					if (num.equalsIgnoreCase(challan.getSwv_id().toString())) {
							if (isnew) {
								footer=challan;
								maintable1.addCell(PdfStyle.createLabelCellWithoutBorderSmallFontNoBold(""));
								hcell = createHeader(challan,pagecount, Long.valueOf(pb.getLocationId()),pb.getFromChallan(),
										 pb.getToChallan(),companyName);
								cell = new PdfPCell(hcell);
								cell.setBorder(0);
								cell.setHorizontalAlignment(Element.ALIGN_CENTER);
								maintable1.addCell(cell);
								event.setHeader(maintable1);
								isnew = false;
								list_new_page.add(challan);
							}
 
							bodytable.addCell(PdfStyle.createValueCellWithHeight((challan.getProd_name_pack().length()>100 ? challan.getProd_name_pack().substring(0, 100) : challan.getProd_name_pack()),false,true,27f));
							bodytable.addCell(PdfStyle.createValueCellWithHeight((challan.getStock_type().length()>100 ? challan.getStock_type().substring(0, 100) : challan.getStock_type()),false,true,27f));
							bodytable.addCell(PdfStyle.createValueCellWithHeight((challan.getBatch_no()),false,true,18f));
							bodytable.addCell(PdfStyle.createValueCellWithHeight((challan.getBatch_expiry_dt()),false,true,18f));
							bodytable.addCell(PdfStyle.createValueCellWithHeight((challan.getQty()==null?BigDecimal.ZERO.setScale(2, RoundingMode.HALF_UP).stripTrailingZeros():challan.getQty().setScale(2, RoundingMode.HALF_UP).stripTrailingZeros()).toPlainString()+"  ",true,true,18f));
							bodytable.addCell(PdfStyle.createValueCellWithHeight((challan.getSwv_value()==null?BigDecimal.ZERO.setScale(2, RoundingMode.HALF_UP):challan.getSwv_value().setScale(2, RoundingMode.HALF_UP)).toString(),true,true,18f));
							total =total.add(challan.getSwv_value());
						rows++;
						
					}
				}
				
				newPage(column, bodytable, event, printLabel, true, rows,pageflag,pagecount,total, Long.valueOf(pb.getLocationId()),  pb.getFromChallan(),
						 pb.getToChallan(),list_new_page,companyName);
				  
				document.newPage();
			}

		} catch (Exception e) {
			System.out.println("Error Occurred :" +e.getMessage());// uncomment asneeded --;
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
		return filename;
	}

	public PdfPTable newPage(ColumnText column,PdfPTable bodytable, FooterWithoutPageNo event, 
			HashMap<String, String> printLabel, boolean isFooterData, int rows,boolean pageflag,int pagecount, BigDecimal total
			, Long loc, String frm_challan,
			String to_challan,List<ViewStockWithdrawalVoucherPrint> challan,String company) throws DocumentException {

		PdfPCell cell = null;
		try {

			if (limit - rows > 0) {
				// add extra empty rows in body table
				cell = new PdfPCell();
				PdfPTable temp = new PdfPTable(6);
				temp.setWidths(bodyTableWidth);
				
				temp.addCell(PdfStyle.createValueCellWithHeight((" "),false,true,27f));
				temp.addCell(PdfStyle.createValueCellWithHeight((" "),false,true,27f));
				temp.addCell(PdfStyle.createValueCellWithHeight((""),false,true,18f));
				temp.addCell(PdfStyle.createValueCellWithHeight((""),false,true,18f));
				temp.addCell(PdfStyle.createValueCellWithHeight((""),true,true,18f));
				temp.addCell(PdfStyle.createValueCellWithHeight((""),true,true,18f));
		
				cell = new PdfPCell(temp);
				cell.setBorder(0);
				cell.setColspan(6);
				
				for (int i = 0; i < (limit - rows); i++) {
					bodytable.addCell(cell);
				}
			}

			cell = new PdfPCell(PdfStyle.createSingleLine());
			cell.setColspan(6);
			bodytable.addCell(cell);

			column.addElement(bodytable);
			column.setSimpleColumn(20, 0, 580,588);
			column.go();
			
			bodytable = new PdfPTable(6);
			bodytable.setWidthPercentage(100);
			bodytable.setWidths(bodyTableWidth);
						
			event.setFooter(createFooter(printLabel, isFooterData,pageflag, pagecount,total,  loc,  frm_challan,
					 to_challan,challan,company));

		} catch (DocumentException e) {
			throw e;
		}
		return bodytable;
	}
	
	private PdfPTable createFooter(HashMap<String, String> printLabel, boolean isFooterData,boolean pageflag,int pagecount, BigDecimal total
			, Long loc, String frm_challan,
			String to_challan, List<ViewStockWithdrawalVoucherPrint> challan,String company) throws DocumentException {
		PdfPTable maintable = new PdfPTable(3);
		
	System.out.println("challan size() " +challan.size());
	try{
	for (ViewStockWithdrawalVoucherPrint obj : challan) {
		PdfPCell cell = null;
		
		maintable.setWidthPercentage(100);
		
		cell = new PdfPCell();
		
		maintable.setWidths(new float[] { 6f,2f,2f });
		
		String narrration = printrepository.getLocationNarrationByLocId(loc);

		PdfPCell hcell1 = new PdfPCell(
				PdfStyle.createLabelCellWithBorderSmallFontNoBold(obj.getSwv_challan_msg().length()>80 ? obj.getSwv_challan_msg().substring(0, 80) : obj.getSwv_challan_msg()));
						
		hcell1.setHorizontalAlignment(Element.ALIGN_LEFT);
		hcell1.setMinimumHeight(36f);
		
		PdfPCell hcell2=new PdfPCell(PdfStyle.createLabelCellWithBorderSmallFont("Total(Rs.)"));
		hcell2.setHorizontalAlignment(Element.ALIGN_LEFT);
		hcell2.setMinimumHeight(36f);
		PdfPCell hcell3=new PdfPCell(PdfStyle.createLabelCellWithBorderSmallFont(""+total.setScale(2, BigDecimal.ROUND_HALF_UP)));
		hcell3.setHorizontalAlignment(Element.ALIGN_RIGHT);
		hcell3.setMinimumHeight(36f);
		
		maintable.addCell(hcell1);
		maintable.addCell(hcell2);
		maintable.addCell(hcell3);
		
		
		
		//=====================================end of total====================================================
		

		PdfPTable childTbl1 = new PdfPTable(4);
		childTbl1.setWidths(new float[] { 2f,2f,2f,2f });
		
		
		//------------------------------------------------------------------------------------------------------
		PdfPCell hcell_t1=new PdfPCell(PdfStyle.createLabelCellWithoutBorderSmallFont("Transporter Detail :"));
		hcell_t1.setHorizontalAlignment(Element.ALIGN_LEFT);
		hcell_t1.enableBorderSide(Rectangle.LEFT);
		
		PdfPCell hcell_t2=new PdfPCell(PdfStyle.createLabelCellWithoutBorderSmallFontNoBold(""));
		hcell_t2.setHorizontalAlignment(Element.ALIGN_LEFT);
		
		PdfPCell hcell_t3=new PdfPCell(PdfStyle.createLabelCellWithoutBorderSmallFont("Way Bill No :"));
		hcell_t3.setHorizontalAlignment(Element.ALIGN_LEFT);
		
		PdfPCell hcell_t_2=new PdfPCell(PdfStyle.createLabelCellWithoutBorderSmallFontNoBold(""));
		hcell_t_2.setHorizontalAlignment(Element.ALIGN_LEFT);
		
		PdfPCell hcell_t4=new PdfPCell(PdfStyle.createLabelCellWithoutBorderSmallFont("Name :"));
		hcell_t4.setHorizontalAlignment(Element.ALIGN_LEFT);
		hcell_t4.enableBorderSide(Rectangle.LEFT);
		
		PdfPCell hcell_t5=new PdfPCell(PdfStyle.createLabelCellWithoutBorderSmallFontNoBold(obj.getSwv_transporter()));
		hcell_t5.setHorizontalAlignment(Element.ALIGN_LEFT);
		
		PdfPCell hcell_t6=new PdfPCell(PdfStyle.createLabelCellWithoutBorderSmallFont("No :"));
		hcell_t6.setHorizontalAlignment(Element.ALIGN_LEFT);
		
		PdfPCell hcell_t7=new PdfPCell(PdfStyle.createLabelCellWithoutBorderSmallFontNoBold("  "));
		hcell_t7.setHorizontalAlignment(Element.ALIGN_LEFT);
		
		
		PdfPCell hcell_t8=new PdfPCell(PdfStyle.createLabelCellWithoutBorderSmallFont("LR No.  "));
		hcell_t8.setHorizontalAlignment(Element.ALIGN_LEFT);
		hcell_t8.enableBorderSide(Rectangle.LEFT);
		
		PdfPCell hcell_t9=new PdfPCell(PdfStyle.createLabelCellWithoutBorderSmallFontNoBold(obj.getSwv_lr_no()));
		hcell_t9.setHorizontalAlignment(Element.ALIGN_LEFT);
		
		PdfPCell hcell_t10=new PdfPCell(PdfStyle.createLabelCellWithoutBorderSmallFont("Date : "));
		hcell_t10.setHorizontalAlignment(Element.ALIGN_LEFT);
		
		PdfPCell hcell_t11=new PdfPCell(PdfStyle.createLabelCellWithoutBorderSmallFontNoBold("  "));
		hcell_t11.setHorizontalAlignment(Element.ALIGN_LEFT);
		
		PdfPCell hcell_t_10=new PdfPCell(PdfStyle.createLabelCellWithoutBorderSmallFont("Date : "));
		hcell_t_10.setHorizontalAlignment(Element.ALIGN_LEFT);
		hcell_t_10.enableBorderSide(Rectangle.LEFT);
    	
		PdfPCell hcell_t_11=new PdfPCell(PdfStyle.createLabelCellWithoutBorderSmallFontNoBold(obj.getLr_dt()));
		hcell_t_11.setHorizontalAlignment(Element.ALIGN_LEFT);
		
		PdfPCell hcell_t12=new PdfPCell(PdfStyle.createLabelCellWithoutBorderSmallFont("Number of Boxes :  "));
		hcell_t12.setHorizontalAlignment(Element.ALIGN_LEFT);
		
		PdfPCell hcell_t13=new PdfPCell(PdfStyle.createLabelCellWithoutBorderSmallFontNoBold(obj.getNo_of_cases().toString()));
		hcell_t13.setHorizontalAlignment(Element.ALIGN_LEFT);
		
		PdfPCell hcell_t14=new PdfPCell(PdfStyle.createLabelCellWithoutBorderSmallFont("Remarks :"));
		hcell_t14.setHorizontalAlignment(Element.ALIGN_LEFT);
		hcell_t14.setVerticalAlignment(Element.ALIGN_TOP);
		hcell_t14.enableBorderSide(Rectangle.LEFT);
		
		PdfPCell hcell_t15=new PdfPCell(PdfStyle.createLabelCellWithoutBorderSmallFontNoBold(obj.getSwv_remarks().length()>100 ? obj.getSwv_remarks().substring(0, 100) : obj.getSwv_remarks()));
		hcell_t15.setHorizontalAlignment(Element.ALIGN_LEFT);
	
		
		DecimalFormat format = new DecimalFormat("0.#");
		PdfPCell hcell_t16=new PdfPCell(PdfStyle.createLabelCellWithoutBorderSmallFont("Weight of Consignment: ")); 
		hcell_t16.setHorizontalAlignment(Element.ALIGN_LEFT);
		
		PdfPCell hcell_t17=new PdfPCell(PdfStyle.createLabelCellWithoutBorderSmallFontNoBold( format.format(Double.parseDouble(obj.getSwv_totwt())).toString()));
		hcell_t17.setHorizontalAlignment(Element.ALIGN_LEFT);
		
		childTbl1.addCell(hcell_t1);
		childTbl1.addCell(hcell_t2);
		childTbl1.addCell(hcell_t3);
		childTbl1.addCell(hcell_t_2);
		childTbl1.addCell(hcell_t4);
		childTbl1.addCell(hcell_t5);
		
		childTbl1.addCell(hcell_t6);
		childTbl1.addCell(hcell_t7);
		childTbl1.addCell(hcell_t8);
		childTbl1.addCell(hcell_t9);
		childTbl1.addCell(hcell_t10);
		
		
		childTbl1.addCell(hcell_t11);
		
		childTbl1.addCell(hcell_t_10);
		
		
		childTbl1.addCell(hcell_t_11);
		childTbl1.addCell(hcell_t12);
		childTbl1.addCell(hcell_t13);
		childTbl1.addCell(hcell_t14);
		childTbl1.addCell(hcell_t15);
		
		childTbl1.addCell(hcell_t16);
		childTbl1.addCell(hcell_t17);
		
		
		PdfPCell hcell4=new PdfPCell(childTbl1);
		hcell4.setBorder(Rectangle.NO_BORDER);
		hcell4.enableBorderSide(Rectangle.BOTTOM);
		
		PdfPTable childTbl2 = new PdfPTable(1);
		//childTbl2.setWidths(new float[] { 10f });
		String company_name = company;
		
		PdfPCell hcell_childTbl2_t1=new PdfPCell(PdfStyle.createLabelCellWithoutBorderSmallFont("For "+company_name));
		hcell_childTbl2_t1.enableBorderSide(Rectangle.BOTTOM);
		hcell_childTbl2_t1.enableBorderSide(Rectangle.LEFT);
		hcell_childTbl2_t1.enableBorderSide(Rectangle.RIGHT);
		hcell_childTbl2_t1.setHorizontalAlignment(Element.ALIGN_CENTER);
		 
		
		PdfPCell hcell_childTbl2_t2=new PdfPCell(PdfStyle.createLabelCellWithoutBorderSmallFontNoBold(""));
		hcell_childTbl2_t2.enableBorderSide(Rectangle.LEFT);
		hcell_childTbl2_t2.enableBorderSide(Rectangle.RIGHT);
		hcell_childTbl2_t2.setMinimumHeight(45f);
		
		PdfPCell hcell_childTbl2_t3=new PdfPCell(PdfStyle.createLabelCellWithoutBorderSmallFontNoBold(""));
		hcell_childTbl2_t3.enableBorderSide(Rectangle.LEFT);
		hcell_childTbl2_t3.enableBorderSide(Rectangle.RIGHT);
		 
		
		
		
//		==============================================AUthorized Signatory TAble====================================================
		PdfPTable childTbl3 = new PdfPTable(1);
		childTbl3.setWidths(new float[] { 10f});
		
		PdfPCell hcell_childTbl3_t2=new PdfPCell(PdfStyle.createLabelCellWithoutBorderSmallFont("Authorized Signatory"));
		hcell_childTbl3_t2.enableBorderSide(Rectangle.RIGHT);
		hcell_childTbl3_t2.enableBorderSide(Rectangle.BOTTOM);
		hcell_childTbl3_t2.enableBorderSide(Rectangle.TOP);
		hcell_childTbl3_t2.enableBorderSide(Rectangle.LEFT);
		
		PdfPCell hcell_childTbl3_t4=new PdfPCell(PdfStyle.createLabelCellWithoutBorderSmallFontNoBold(""));
		hcell_childTbl3_t4.enableBorderSide(Rectangle.RIGHT);
		hcell_childTbl3_t4.enableBorderSide(Rectangle.BOTTOM);
		hcell_childTbl3_t4.enableBorderSide(Rectangle.LEFT);
		

		childTbl3.addCell(hcell_childTbl3_t2);

		childTbl3.addCell(hcell_childTbl3_t4);

		
		PdfPCell hcell_childTbl2_t4 = new PdfPCell(childTbl3);
		hcell_childTbl2_t4.setBorder(Rectangle.NO_BORDER);
		
		
		childTbl2.addCell(hcell_childTbl2_t1);
		childTbl2.addCell(hcell_childTbl2_t2);
		childTbl2.addCell(hcell_childTbl2_t3);
		
		childTbl2.addCell(hcell_childTbl2_t4);
		
		PdfPCell hcell5 = new PdfPCell(childTbl2);
		hcell5.setBorder(Rectangle.NO_BORDER);
		hcell5.setColspan(2);			
		hcell5.setHorizontalAlignment(Element.ALIGN_CENTER);
	
		maintable.addCell(hcell4);
		maintable.addCell(hcell5);
	 
		
		//---------------------------------------------------Remark :-------------------------------------------------------------

		PdfPCell hcell1_remark0 = new PdfPCell(
				PdfStyle.createLabelCellWithoutBorderSmallFontBold(" "));
						
		hcell1_remark0.setHorizontalAlignment(Element.ALIGN_LEFT);
		
		PdfPCell hcell1_remark = new PdfPCell(
				PdfStyle.createLabelCellWithoutBorderSmallFontNoBold(""));
						
		hcell1_remark.setHorizontalAlignment(Element.ALIGN_LEFT);
		//hcell1_remark.setMinimumHeight(36f);
		
		
		PdfPTable childTbl4 = new PdfPTable(2);
		childTbl4.setWidths(new float[] { 1f,9f});
		childTbl4.addCell(hcell1_remark0);
		childTbl4.addCell(hcell1_remark);
		
		PdfPCell hcell_childTbl2_t5 = new PdfPCell(childTbl4);
		hcell_childTbl2_t5.setBorder(Rectangle.NO_BORDER);
		hcell_childTbl2_t5.setHorizontalAlignment(Element.ALIGN_LEFT);
		hcell_childTbl2_t5.setColspan(3);

		maintable.addCell(hcell_childTbl2_t5);
		
		 
		//-----------------------------------------------------------------------------------------------------------------

		

		if(pageflag){
			cell = new PdfPCell(PdfStyle.createLabelCellWithoutBorderSmallFontNoBold("Page No. "+pagecount));
			cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
			cell.setVerticalAlignment(Element.ALIGN_BOTTOM);
			maintable.addCell(cell);
		
		}
	}//end of for each list
	}catch(Exception e){
		System.out.println("Error Occurred :" +e.getMessage());// uncomment asneeded --;
	}
		return maintable;
	}
	
	
	private PdfPTable createHeader(ViewStockWithdrawalVoucherPrint challan, int pagecount, Long loc, String frm_challan,
			String to_challan,String company) throws Exception{
		PdfPTable headertable = new PdfPTable(1);
		PdfPTable headertable01 = new PdfPTable(1);// Billing Address
		PdfPTable headertable2 = new PdfPTable(4);
		PdfPTable headertable3 = new PdfPTable(4);
		PdfPTable headertable4 = new PdfPTable(4);
		PdfPTable bodytable = null;

		
		headertable.setWidthPercentage(100);
		headertable01.setWidthPercentage(100);
		headertable2.setWidthPercentage(100);
		headertable3.setWidthPercentage(100);
		headertable4.setWidthPercentage(100);
		
		headertable01.setWidths(new float[] { 7f });
		headertable2.setWidths(new float[] { 3.5f,3.5f,1f,2f });
		headertable3.setWidths(new float[] { 4.5f,2.5f,1f,2f });
		headertable4.setWidths(new float[] {2f,2f,2.5f,3.5f });
		
		Font font = new Font(FontFamily.HELVETICA, 12, Font.BOLD, BaseColor.BLACK);
		PdfPTable headertable1 = new PdfPTable(1);
		headertable1.setWidthPercentage(100);
		headertable1.setWidths(new float[] { 5f});


		//======================================================================================================================   1
		
		PdfPCell hcell= new PdfPCell(new Phrase("Stock Withdrawal Voucher", font));
		hcell.setBorder(Rectangle.NO_BORDER);
		hcell.setHorizontalAlignment(Element.ALIGN_CENTER);
		
		PdfPCell hcell_1= new PdfPCell(new Phrase(" ", font));
		hcell_1.setBorder(Rectangle.NO_BORDER);
		hcell_1.setHorizontalAlignment(Element.ALIGN_CENTER);
		headertable01.addCell(hcell);
		//headertable01.addCell(hcell_1);
		
		//=================================================Blank Cell Added To Remove Table Border==============================
		
		PdfPCell hcell_blank= new PdfPCell(headertable01);
		hcell_blank.setBorder(Rectangle.NO_BORDER);
		hcell_blank.disableBorderSide(Rectangle.RIGHT);
		hcell_blank.disableBorderSide(Rectangle.BOTTOM);
		
		//======================================================================================================================   2.1
		  String company_name = company;

		    Font font1 = new Font(FontFamily.HELVETICA, 8, Font.NORMAL,BaseColor.BLACK);
		    StringBuilder result1 = new StringBuilder();
		    result1.append(challan.getLoc_add1().length()>40 ? challan.getLoc_add1().substring(0, 40) : challan.getLoc_add1());
		    result1.append(System.lineSeparator());
		 
		    result1.append(challan.getLoc_add2().length()>40 ? challan.getLoc_add2().substring(0, 40) : challan.getLoc_add2());
		    result1.append(System.lineSeparator());
		   
		    result1.append(challan.getLoc_add3().length()>40 ? challan.getLoc_add3().substring(0, 40) : challan.getLoc_add3());
		    result1.append(System.lineSeparator());
		  
		    result1.append(challan.getLoc_add4().length()>40 ? challan.getLoc_add4().substring(0, 40) : challan.getLoc_add4());
		    result1.append(System.lineSeparator());
		    
		    
		    PdfPCell hcell_cn=new PdfPCell(PdfStyle.createLabelCellWithBorderLargeFontNoBold(company_name));
		    hcell_cn.setHorizontalAlignment(Element.ALIGN_LEFT);
		    hcell_cn.setBorder(0);
	 
		PdfPCell hcell2w=new PdfPCell(PdfStyle.createLabelCellWithBorderSmallFontNoBoldForHeader(result1.toString()));
		hcell2w.setHorizontalAlignment(Element.ALIGN_LEFT);
		hcell2w.setBorder(0);

		PdfPTable addressTbl_a = new PdfPTable(1);
		addressTbl_a.addCell(hcell_cn);
		addressTbl_a.addCell(hcell2w);

		PdfPCell hcella_4=new PdfPCell(PdfStyle.createLabelCellWithoutBorderSmallFontNoPad("Drug Lic No:"));
		hcella_4.setHorizontalAlignment(Element.ALIGN_LEFT);
		hcella_4.setVerticalAlignment(Element.ALIGN_BOTTOM);
		hcella_4.setBorder(0);
		
		PdfPCell hcella_4_1=new PdfPCell(PdfStyle.createLabelCellWithBorderSmallFontNoBoldForHeader(challan.getLoc_dlic1()));
		hcella_4_1.setHorizontalAlignment(Element.ALIGN_LEFT);
		hcella_4_1.setVerticalAlignment(Element.ALIGN_TOP);
		hcella_4_1.setBorder(0);
		
		PdfPCell hcella_7=new PdfPCell(PdfStyle.createLabelCellWithoutBorderSmallFontNoPad(" "));
		hcella_7.setHorizontalAlignment(Element.ALIGN_LEFT);
		hcella_7.setVerticalAlignment(Element.ALIGN_TOP);
		hcella_7.setBorder(0);
		
		PdfPCell hcella_4_2=new PdfPCell(PdfStyle.createLabelCellWithBorderSmallFontNoBoldForHeader(challan.getLoc_dlic2()));
		hcella_4_2.setHorizontalAlignment(Element.ALIGN_LEFT);
		hcella_4_2.setVerticalAlignment(Element.ALIGN_TOP);
		hcella_4_2.setBorder(0);
		
		PdfPCell hcella_5=new PdfPCell(PdfStyle.createLabelCellWithoutBorderSmallFontNoPad("VAT Reg No:"));
		hcella_5.setHorizontalAlignment(Element.ALIGN_LEFT);
		hcella_5.setVerticalAlignment(Element.ALIGN_TOP);
		hcella_5.setBorder(0);
		
		
		PdfPCell hcella_4_3=new PdfPCell(PdfStyle.createLabelCellWithBorderSmallFontNoBoldForHeader(challan.getLoc_vatno()));
		hcella_4_3.setHorizontalAlignment(Element.ALIGN_LEFT);
		hcella_4_3.setVerticalAlignment(Element.ALIGN_TOP);
		hcella_4_3.setBorder(0);

		PdfPCell hcella_6=new PdfPCell(PdfStyle.createLabelCellWithoutBorderSmallFontNoPad("CST Reg No :"));
		hcella_6.setHorizontalAlignment(Element.ALIGN_LEFT);
		hcella_6.setVerticalAlignment(Element.ALIGN_TOP);
		hcella_6.setBorder(0);
		
		PdfPCell hcella_4_4=new PdfPCell(PdfStyle.createLabelCellWithBorderSmallFontNoBoldForHeader(challan.getLoc_cstno()));
		hcella_4_4.setHorizontalAlignment(Element.ALIGN_LEFT);
		hcella_4_4.setVerticalAlignment(Element.ALIGN_TOP);
		hcella_4_4.setBorder(0);
		
		PdfPCell hcella_8=new PdfPCell(PdfStyle.createLabelCellWithoutBorderSmallFontNoPad("CIN No :"));
		hcella_8.setHorizontalAlignment(Element.ALIGN_LEFT);
		hcella_8.setVerticalAlignment(Element.ALIGN_TOP);
		hcella_8.setBorder(0);
		
		PdfPCell hcella_4_8=new PdfPCell(PdfStyle.createLabelCellWithBorderSmallFontNoBoldForHeader(challan.getLoc_cinno()));
		hcella_4_8.setHorizontalAlignment(Element.ALIGN_LEFT);
		hcella_4_8.setVerticalAlignment(Element.ALIGN_TOP);
		hcella_4_8.setBorder(0);
		
		
		PdfPCell hcella_9=new PdfPCell(PdfStyle.createLabelCellWithoutBorderSmallFontNoPad("Website :"));
		hcella_9.setHorizontalAlignment(Element.ALIGN_LEFT);
		hcella_9.setVerticalAlignment(Element.ALIGN_TOP);
		hcella_9.setBorder(0);
		
		PdfPCell hcella_4_9=new PdfPCell(PdfStyle.createLabelCellWithBorderSmallFontNoBoldForHeader(challan.getLoc_web_site()));
		hcella_4_9.setHorizontalAlignment(Element.ALIGN_LEFT);
		hcella_4_9.setVerticalAlignment(Element.ALIGN_TOP);
		hcella_4_9.setBorder(0);
		
		
		PdfPCell hcella_10=new PdfPCell(PdfStyle.createLabelCellWithoutBorderSmallFontNoPad("Tel No :"));
		hcella_10.setHorizontalAlignment(Element.ALIGN_LEFT);
		hcella_10.setVerticalAlignment(Element.ALIGN_TOP);
		hcella_10.setBorder(0);
		
		PdfPCell hcella_4_10=new PdfPCell(PdfStyle.createLabelCellWithBorderSmallFontNoBoldForHeader(challan.getLoc_telephone_no()));
		hcella_4_10.setHorizontalAlignment(Element.ALIGN_LEFT);
		hcella_4_10.setVerticalAlignment(Element.ALIGN_TOP);
		hcella_4_10.setBorder(0);
		
		PdfPCell hcella_11=new PdfPCell(PdfStyle.createLabelCellWithoutBorderSmallFontNoPad("Email Id :"));
		hcella_11.setHorizontalAlignment(Element.ALIGN_LEFT);
		hcella_11.setVerticalAlignment(Element.ALIGN_TOP);
		hcella_11.setBorder(0);
		
		PdfPCell hcella_4_11=new PdfPCell(PdfStyle.createLabelCellWithBorderSmallFontNoBoldForHeader(challan.getLoc_emailid()));
		hcella_4_11.setHorizontalAlignment(Element.ALIGN_LEFT);
		hcella_4_11.setVerticalAlignment(Element.ALIGN_TOP);
		hcella_4_11.setBorder(0);
		
		PdfPCell cell11 = new PdfPCell(addressTbl_a);
		cell11.setBorder(0);
		
		
		PdfPTable addressTbl = new PdfPTable(1);
		PdfPTable addressTbl_b = new PdfPTable(2);
		addressTbl_b.setWidths(new float[] { 1.5f,8.5f });
		addressTbl_b.addCell(hcella_4);
		addressTbl_b.addCell(hcella_4_1);
		addressTbl_b.addCell(hcella_7);
		addressTbl_b.addCell(hcella_4_2);
		
		addressTbl_b.addCell(hcella_5);
		addressTbl_b.addCell(hcella_4_3);
		addressTbl_b.addCell(hcella_6);
		addressTbl_b.addCell(hcella_4_4);
		
		addressTbl_b.addCell(hcella_8);
		addressTbl_b.addCell(hcella_4_8);
		addressTbl_b.addCell(hcella_9);
		addressTbl_b.addCell(hcella_4_9);
		addressTbl_b.addCell(hcella_10);
		addressTbl_b.addCell(hcella_4_10);
		addressTbl_b.addCell(hcella_11);
		addressTbl_b.addCell(hcella_4_11);
	
		PdfPCell cell12 = new PdfPCell(addressTbl_b);
		cell12.setBorder(0);
		addressTbl.addCell(cell11);
		addressTbl.addCell(cell12);
		PdfPCell cell1 = new PdfPCell(addressTbl);
		cell1.setBorder(0);
		cell1.setColspan(2);
		
		
		headertable2.addCell(cell1);
		
		
		//======================================================================================================================   2.2

		
		//======================================================================================================================   
		
		
		//======================================================================================================================   2.3
		
			
			
				
			PdfPCell hcell16=new PdfPCell(PdfStyle.createLabelCellWithBorderSmallFont("Date "));
				hcell16.setHorizontalAlignment(Element.ALIGN_LEFT);
				
				
				
				PdfPCell hcell20=new PdfPCell(PdfStyle.createLabelCellWithBorderSmallFont("Voucher #"));
				hcell20.setHorizontalAlignment(Element.ALIGN_LEFT);
				
				PdfPCell hcell20_a=new PdfPCell(PdfStyle.createLabelCellWithBorderSmallFont("SWV Type"));
				hcell20_a.setHorizontalAlignment(Element.ALIGN_LEFT);
				
				PdfPCell hcell21=new PdfPCell(PdfStyle.createLabelCellWithBorderSmallFont("Sender"));
				hcell21.setHorizontalAlignment(Element.ALIGN_LEFT);
				
				PdfPCell hcell17=new PdfPCell(PdfStyle.createLabelCellWithBorderSmallFontTwiceHeight("Email"));
				hcell17.setHorizontalAlignment(Element.ALIGN_LEFT);
				
				PdfPCell hcell18=new PdfPCell(PdfStyle.createLabelCellWithBorderSmallFont("Telephone 1"));
				hcell18.setHorizontalAlignment(Element.ALIGN_LEFT);
				
				PdfPCell hcell19=new PdfPCell(PdfStyle.createLabelCellWithBorderSmallFont("Telephone 2"));
				hcell19.setHorizontalAlignment(Element.ALIGN_LEFT);
				
				
				PdfPTable addressTbl2= new PdfPTable(1);
				addressTbl2.addCell(hcell16);
				
				addressTbl2.addCell(hcell20);
				addressTbl2.addCell(hcell20_a);
				addressTbl2.addCell(hcell21);
				addressTbl2.addCell(hcell17);
				addressTbl2.addCell(hcell18);
				addressTbl2.addCell(hcell19);
				PdfPCell cell3 = new PdfPCell(addressTbl2);
				cell3.setBorder(0);
				
				headertable2.addCell(cell3);
				
				//======================================================================================================================   
				
				
				//======================================================================================================================   2.4
				 Font medFont = new Font(Font.FontFamily.HELVETICA, 12,
						Font.NORMAL, BaseColor.BLACK);
				
						
						
						PdfPCell hcell26=new PdfPCell(PdfStyle.createLabelCellWithBorderSmallFontNoBold((challan.getChallan_dt()==null?"":challan.getChallan_dt())));
						hcell26.setHorizontalAlignment(Element.ALIGN_LEFT);
						
						PdfPCell hcell30=new PdfPCell(PdfStyle.createLabelCellWithBorderSmallFontNoBold((challan.getSwv_challan_no()==null?"":challan.getSwv_challan_no())));
						hcell30.setHorizontalAlignment(Element.ALIGN_LEFT);
						
						PdfPCell hcell31=new PdfPCell(PdfStyle.createLabelCellWithBorderSmallFontNoBold((challan.getWithdrawal_type().length()>20 ? challan.getWithdrawal_type().substring(0, 20) : challan.getWithdrawal_type())));
						hcell31.setHorizontalAlignment(Element.ALIGN_LEFT);

						PdfPCell hcell31_a=new PdfPCell(PdfStyle.createLabelCellWithBorderSmallFontNoBold((challan.getSwv_sender_name().length()>20 ? challan.getSwv_sender_name().substring(0, 20) : challan.getSwv_sender_name())));
						hcell31_a.setHorizontalAlignment(Element.ALIGN_LEFT);

											
						PdfPCell hcell27=new PdfPCell(PdfStyle.createLabelCellWithBorderSmallFontNoBoldTwiceHeight(""));
						hcell27.setHorizontalAlignment(Element.ALIGN_LEFT);
						
						PdfPCell hcell28=new PdfPCell(PdfStyle.createLabelCellWithBorderSmallFontNoBold(""));
						hcell28.setHorizontalAlignment(Element.ALIGN_LEFT);
						
						PdfPCell hcell29=new PdfPCell(PdfStyle.createLabelCellWithBorderSmallFontNoBold(""));
						hcell29.setHorizontalAlignment(Element.ALIGN_LEFT);
							 
						PdfPTable addressTbl3= new PdfPTable(1);
						addressTbl3.addCell(hcell26);
						
						addressTbl3.addCell(hcell30);
						addressTbl3.addCell(hcell31);
						addressTbl3.addCell(hcell31_a);
						addressTbl3.addCell(hcell27);
						addressTbl3.addCell(hcell28);
						addressTbl3.addCell(hcell29);
						PdfPCell cell4 = new PdfPCell(addressTbl3);
						cell4.setBorder(0);
						headertable2.addCell(cell4);
				
				//=================================================Blank Cell Added To Remove Table Border==============================
						
						PdfPCell hcell_blank2= new PdfPCell(headertable2);
						hcell_blank2.setBorder(Rectangle.NO_BORDER);
						//hcell_blank2.enableBorderSide(Rectangle.RIGHT);
						//hcell_blank2.disableBorderSide(Rectangle.BOTTOM);
				
				
				
				//challan.getLoc_add1().length()>29 ? challan.getLoc_add1().substring(0, 29) : challan.getLoc_add1()
				//=====================================================================================================================  
				
				//=====================================================================================================================   3.1
				
				PdfPCell hcell35=new PdfPCell(PdfStyle.createLabelCellWithBorderSmallFont("Ship To :"));
				hcell35.setHorizontalAlignment(Element.ALIGN_LEFT);
				
				PdfPCell hcell36=new PdfPCell(PdfStyle.createLabelCellWithoutBorder(challan.getSwv_sender_name()==null?"":challan.getSwv_sender_name()));
				hcell36.setHorizontalAlignment(Element.ALIGN_LEFT);
				hcell36.enableBorderSide(Rectangle.LEFT);
				hcell36.enableBorderSide(Rectangle.RIGHT);
				 StringBuilder result2 = new StringBuilder();
				    result2.append(challan.getSwv_sender_address1().length()>50 ? challan.getSwv_sender_address1().substring(0, 50) : challan.getSwv_sender_address1());
				    result2.append(System.lineSeparator());
				    result2.append(challan.getSwv_sender_address2().length()>50 ? challan.getSwv_sender_address2().substring(0, 50) : challan.getSwv_sender_address2());
				    result2.append(System.lineSeparator());
				    result2.append(challan.getSwv_sender_address3().length()>50 ? challan.getSwv_sender_address3().substring(0, 50) : challan.getSwv_sender_address3());
				    result2.append(System.lineSeparator());
				  
				  
			
				    
				PdfPCell hcell37=new PdfPCell(PdfStyle.createLabelCellWithoutBorderSmallFontNoBold(result2.toString()==null?"":result2.toString()));
				hcell37.setHorizontalAlignment(Element.ALIGN_LEFT);
				hcell37.enableBorderSide(Rectangle.LEFT);
				hcell37.enableBorderSide(Rectangle.RIGHT);
				
				
				PdfPCell hcell37_a=new PdfPCell(PdfStyle.createLabelCellWithoutBorderSmallFontNoBold(challan.getSwv_destination().toString()==null?"":challan.getSwv_destination().toString()));
				hcell37_a.setHorizontalAlignment(Element.ALIGN_LEFT);
				hcell37_a.enableBorderSide(Rectangle.LEFT);
				hcell37_a.enableBorderSide(Rectangle.RIGHT);
				
				PdfPTable custTbl = new PdfPTable(1);
				custTbl.addCell(hcell35);
				custTbl.addCell(hcell36);
				custTbl.addCell(hcell37);
				custTbl.addCell(hcell37_a);
				
				PdfPCell cell5 = new PdfPCell(custTbl);
				cell5.setBorder(0);
				
				headertable3.addCell(cell5);
				
				//======================================================================================================================  
				
				
				//======================================================================================================================   3.2
				String address1 = challan.getLoc_add1();
				String address2 = challan.getLoc_add2();
				String address3 = challan.getLoc_add3();
				String address4 = challan.getLoc_add4();
				//WEB_SITE cinno
				String web_site = challan.getLoc_web_site();
				String cinno = challan.getLoc_cinno();
				
				System.out.println("web_site " +web_site+ "cinno "+cinno );

				 StringBuilder result3 = new StringBuilder();
				 	
				    result3.append(address1.length()>33 ? address1.substring(0, 33) : address1);
				    result3.append(System.lineSeparator());
				    result3.append(address2.length()>33 ? address2.substring(0, 33) : address2);
				    result3.append(System.lineSeparator());
				    result3.append(address3.length()>33 ? address3.substring(0, 33) : address3);
				    result3.append(System.lineSeparator());
				    result3.append(address4.length()>33 ? address4.substring(0, 33) : address4);
				    result3.append(System.lineSeparator());
				    result3.append(web_site.length()>33 ? "Website:"+web_site.substring(0, 33) : "Website:"+web_site);
				    result3.append(System.lineSeparator());
				    result3.append(cinno.length()>33 ? "CIN NO:"+cinno.substring(0, 33) : "CIN NO:"+cinno);
				    result3.append(System.lineSeparator());
				    
				
				PdfPCell hcell38=new PdfPCell(PdfStyle.createLabelCellWithBorderSmallFont("Principal place of buisness"));
				hcell38.setHorizontalAlignment(Element.ALIGN_LEFT);
				
				PdfPCell hcell39=new PdfPCell(PdfStyle.createLabelCellWithoutBorderSmallFontNoBold(result3.length()>140 ? result3.substring(0, 140) : result3.toString()));
				hcell39.setHorizontalAlignment(Element.ALIGN_LEFT);
				
				hcell39.enableBorderSide(Rectangle.RIGHT);
				
				
				
				PdfPTable custTbl1 = new PdfPTable(1);
				custTbl1.addCell(hcell38);
				custTbl1.addCell(hcell39);
				
				
				
				PdfPCell cell6 = new PdfPCell(custTbl1);
				cell6.setBorder(0);
				
				headertable3.addCell(cell6);
				
				//====================================================================================================================== 
				
				
				//=====================================================================================================================   3.3
				
				PdfPCell hcell43=new PdfPCell(PdfStyle.createLabelCellWithBorderSmallFont("Note"));
				hcell43.setHorizontalAlignment(Element.ALIGN_CENTER);
				 StringBuilder result4 = new StringBuilder();
				 	
				 result4.append("Goods Not for sale.");
				 result4.append(System.lineSeparator());
				 result4.append("Free samples only.");
				 result4.append(System.lineSeparator());
				PdfPCell hcell44=new PdfPCell(PdfStyle.createLabelCellWithoutBorderSmallFontBold(result4.toString()));
				hcell44.setHorizontalAlignment(Element.ALIGN_CENTER);
				hcell44.setVerticalAlignment(Element.ALIGN_TOP);
				hcell44.enableBorderSide(Rectangle.RIGHT);
		
				PdfPTable custTbl2 = new PdfPTable(1);
				custTbl2.addCell(hcell43);
				custTbl2.addCell(hcell44);

				
				PdfPCell cell7 = new PdfPCell(custTbl2);
				cell7.setBorder(0);
				
				headertable3.addCell(cell7);
				
				
				//====================================================================================================================== 
				
				
				//=====================================================================================================================   3.4
				
				PdfPCell hcell45=new PdfPCell(PdfStyle.createLabelCellWithBorderSmallFont("Insurance Policy No."));
				hcell45.setHorizontalAlignment(Element.ALIGN_LEFT);
				
				PdfPCell hcell46=new PdfPCell(PdfStyle.createLabelCellWithoutBorderSmallFontNoBold(" "));
				hcell46.setHorizontalAlignment(Element.ALIGN_LEFT);
				hcell46.enableBorderSide(Rectangle.RIGHT);
				PdfPCell hcell47=new PdfPCell(PdfStyle.createLabelCellWithBorderSmallFont("Food Licence Nos."));
				hcell47.setHorizontalAlignment(Element.ALIGN_LEFT);
				
				PdfPCell hcell48=new PdfPCell(PdfStyle.createLabelCellWithoutBorderSmallFontNoBold(" "));
				hcell48.setHorizontalAlignment(Element.ALIGN_LEFT);
				hcell48.enableBorderSide(Rectangle.RIGHT);
				
				PdfPTable custTbl3 = new PdfPTable(1);
				custTbl3.addCell(hcell45);
				custTbl3.addCell(hcell46);
				custTbl3.addCell(hcell47);
				custTbl3.addCell(hcell48);
				
				PdfPCell cell8 = new PdfPCell(custTbl3);
				cell8.setBorder(0);
				cell8.setPaddingBottom(0);
				cell8.setUseBorderPadding(false);
				//cell8.setNoWrap(true);
				headertable3.addCell(cell8);
				
				//=================================================Blank Cell Added To Remove Table Border3==============================
				
				PdfPCell hcell_blank3= new PdfPCell(headertable3);
				hcell_blank3.setBorder(Rectangle.NO_BORDER);
				//hcell_blank.enableBorderSide(Rectangle.RIGHT);
				//hcell_blank.disableBorderSide(Rectangle.BOTTOM);
		
		bodytable = new PdfPTable(6);

		bodytable.setWidthPercentage(100);
		bodytable.setWidths(bodyTableWidth);
		bodytable.addCell(PdfStyle.createLabelWithBGColour("Item"));
		bodytable.addCell(PdfStyle.createLabelWithBGColour("Stock Type"));
		bodytable.addCell(PdfStyle.createLabelWithBGColour("Batch/Art Work No"));
		bodytable.addCell(PdfStyle.createLabelWithBGColour("Expiry Date"));
		bodytable.addCell(PdfStyle.createLabelWithBGColour("Quantity"));
		bodytable.addCell(PdfStyle.createLabelWithBGColour("Amount"));
		//=================================================Blank Cell Added To Remove Table Border4==============================
		
		PdfPCell hcell_blank4= new PdfPCell(bodytable);
		hcell_blank4.setBorder(Rectangle.NO_BORDER);
		//hcell_blank2.enableBorderSide(Rectangle.RIGHT);
		//hcell_blank2.disableBorderSide(Rectangle.BOTTOM);
		//hcell_blank3
		//headertable.addCell(headertable01);
		headertable.addCell(hcell_blank);
		//headertable.addCell(headertable2);
		headertable.addCell(hcell_blank2);
		headertable.addCell(hcell_blank3);
		//headertable.addCell(headertable3);
		//headertable.addCell(headertable4);
		//headertable.addCell(bodytable);
		headertable.addCell(hcell_blank4);

		
		return headertable;
	}
	
	//Pick PDF

	@Override
	public String generateStockWithVoucherPickPdf(PrintBean pb, List<ViewStockWithdrawalVoucherPrint> list,String companyName,HttpSession session,HttpServletRequest request) throws Exception {
		boolean pageflag;
		ViewStockWithdrawalVoucherPrint footer;
		StringBuffer path = null;
		
		String filepath = MedicoConstants.PDF_FILE_PATH;
	
		FileOutputStream os = null;
		PrintStream ps = null;
		long timevar = new Date().getTime();
		String filename = "StockwithdrawalPick" + timevar + ".pdf";
		System.out.println("filename 11 " +filename);
		
		path = new StringBuffer(filepath).append("\\");
		path.append("files\\" +filename);
		System.out.println("path  : " + path);
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
		
		System.out.println("filename 11 " +filename);
		File docfile = new File(path.toString());
		OutputStream file = new FileOutputStream(docfile);
		Document document = new Document(PageSize.A4);
		PdfWriter writer =PdfWriter.getInstance(document, file);
		String uname = this.tokenProvider.getUsernameFromRequest(request);String empId = this.userMasterService.getEmpIdByUsername(uname);
		 writer = usermasterservice.generatePDFlock(empId, writer);
		 Rectangle rect = new Rectangle(20, 28, 580, 840);
	        writer.setBoxSize("header", rect);
	        rect = new Rectangle(20, 2, 580,62);
	        writer.setBoxSize("footer", rect);
	      //  int pagecount=writer.getPageNumber();
	        int pagecount=1;
		FooterWithoutPageNo event = new FooterWithoutPageNo();
	    writer.setPageEvent(event);
	    
		document.open();
		document.setMarginMirroringTopBottom(false);
		boolean isnew = false;
		boolean for_new_page = false;
		ColumnText column = new ColumnText(writer.getDirectContent());
		try {
			BigDecimal total = new BigDecimal(0);
				System.out.println("getViewPrePrintedSummaryChallan");
			Set<String> challanset = new LinkedHashSet<String>();
			for (ViewStockWithdrawalVoucherPrint challan : list) {
				challanset.add(challan.getSwv_id().toString());	
			}
			PdfPTable bodytable = null;
			for (String num : challanset) {
				List<ViewStockWithdrawalVoucherPrint> list_new_page = new ArrayList<ViewStockWithdrawalVoucherPrint>();
				isnew = true;
				for_new_page=true;
				PdfPTable maintable1 = new PdfPTable(1);
				maintable1.setWidthPercentage(100);
				PdfPCell cell = null;
				pageflag=false;
				//pagecount=1;

				PdfPTable hcell =null;
				int rows=0;
				bodytable = new PdfPTable(4);
				bodytable.getDefaultCell().setPadding(0.0f);
				bodytable.setWidthPercentage(100);
				bodytable.setWidths(pickbodyTableWidth);
				total=BigDecimal.ZERO;
				for (ViewStockWithdrawalVoucherPrint challan : list) {
					
					if(rows>=picklimit){
						pageflag=true;
						bodytable=PicknewPage(column, bodytable, event,printLabel, false, rows,pageflag,pagecount,total,Long.valueOf(pb.getLocationId()),pb.getFromChallan(),
								 pb.getToChallan(),list) ;
						document.newPage();
						pagecount++;
						rows=0;
					}
					
					if (num.equalsIgnoreCase(challan.getSwv_id().toString())) {
							if (isnew) {
								footer=challan;
								maintable1.addCell(PdfStyle.createLabelCellWithoutBorderSmallFontNoBold(""));
								hcell = PickcreateHeader(challan,pagecount,Long.valueOf(pb.getLocationId()),pb.getFromChallan(),pb.getToChallan(),companyName);
								cell = new PdfPCell(hcell);
								cell.setBorder(0);
								cell.setHorizontalAlignment(Element.ALIGN_CENTER);
								maintable1.addCell(cell);
								event.setHeader(maintable1);
								isnew = false;
								list_new_page.add(challan);
							}
							bodytable.addCell(PdfStyle.createValueCellWithHeight((challan.getProd_name_pack().length()>100 ? challan.getProd_name_pack().substring(0, 100) : challan.getProd_name_pack()),false,true,27f));
							bodytable.addCell(PdfStyle.createValueCellWithHeight((challan.getQty()==null?BigDecimal.ZERO.setScale(2, RoundingMode.HALF_UP).stripTrailingZeros():challan.getQty().setScale(2, RoundingMode.HALF_UP).stripTrailingZeros()).toPlainString()+"  ",true,true,18f));
							bodytable.addCell(PdfStyle.createValueCellWithHeight((challan.getStock_type().length()>25 ? challan.getStock_type().substring(0,25) : challan.getStock_type()),false,true,27f));
							bodytable.addCell(PdfStyle.createValueCellWithHeight((challan.getBatch_no()),false,true,18f));

						rows++;
						
					}
				}
				
				PicknewPage(column, bodytable, event, printLabel, true, rows,pageflag,pagecount,total,Long.valueOf(pb.getLocationId()),pb.getFromChallan(),
						 pb.getToChallan(),list_new_page);
				  
				document.newPage();
			}

		} catch (Exception e) {
			System.out.println("Error Occurred :" +e.getMessage());// uncomment asneeded --;
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
		return filename;
	}
	
	public PdfPTable PicknewPage(ColumnText column,PdfPTable bodytable, FooterWithoutPageNo event, 
			HashMap<String, String> printLabel, boolean isFooterData, int rows,boolean pageflag,int pagecount, BigDecimal total
			, Long loc, String frm_challan,
			String to_challan,List<ViewStockWithdrawalVoucherPrint> challan) throws DocumentException {

		PdfPCell cell = null;
		try {

			if (picklimit - rows > 0) {
				// add extra empty rows in body table
				cell = new PdfPCell();
				PdfPTable temp = new PdfPTable(4);
				temp.setWidths(pickbodyTableWidth);
				 
				temp.addCell(PdfStyle.createValueCellWithHeight((" "),false,true,27f));
				temp.addCell(PdfStyle.createValueCellWithHeight((""),false,true,18f));
				temp.addCell(PdfStyle.createValueCellWithHeight((""),false,true,18f));
				temp.addCell(PdfStyle.createValueCellWithHeight((""),true,true,18f));
		
				cell = new PdfPCell(temp);
				cell.setBorder(0);
				cell.setColspan(4);
				
				for (int i = 0; i < (picklimit - rows); i++) {
					bodytable.addCell(cell);
				}
			}

			cell = new PdfPCell(PdfStyle.createSingleLine());
			cell.setColspan(4);
			bodytable.addCell(cell);

			column.addElement(bodytable);
			column.setSimpleColumn(20, 0, 580, 606);
			column.go();
			
			bodytable = new PdfPTable(4);
			bodytable.setWidthPercentage(100);
			bodytable.setWidths(pickbodyTableWidth);
						
			event.setFooter(PickcreateFooter(printLabel, isFooterData,pageflag, pagecount,total,  loc,  frm_challan,
					 to_challan,challan));

		} catch (DocumentException e) {
			throw e;
		}
		return bodytable;
	}
	
	private PdfPTable PickcreateHeader(ViewStockWithdrawalVoucherPrint challan, int pagecount, Long loc, String frm_challan,
			String to_challan,String company) throws Exception{
		PdfPTable headertable = new PdfPTable(1);
		PdfPTable headertable01 = new PdfPTable(1);// Billing Address
		PdfPTable headertable2 = new PdfPTable(4);
		PdfPTable headertable3 = new PdfPTable(2);
		PdfPTable headertable4 = new PdfPTable(4);
		PdfPTable bodytable = null;

		
		headertable.setWidthPercentage(100);
		headertable01.setWidthPercentage(100);
		headertable2.setWidthPercentage(100);
		headertable3.setWidthPercentage(100);
		headertable4.setWidthPercentage(100);
		
		headertable01.setWidths(new float[] { 7f });
		headertable2.setWidths(new float[] { 3.5f,3.5f,1f,2f });
		headertable3.setWidths(new float[] { 5f,5f });
		headertable4.setWidths(new float[] {2f,2f,2.5f,3.5f });
		
		Font font = new Font(FontFamily.HELVETICA, 12, Font.BOLD, BaseColor.BLACK);
		Font font_big = new Font(FontFamily.HELVETICA, 14, Font.BOLD, BaseColor.BLACK);
		PdfPTable headertable1 = new PdfPTable(1);
		headertable1.setWidthPercentage(100);
		headertable1.setWidths(new float[] { 5f});


		//======================================================================================================================   1
		PdfPCell hcell= null;
	
			hcell=new PdfPCell(new Phrase("Pick Slip", font_big));
		
		hcell.setBorder(Rectangle.NO_BORDER);
		hcell.setHorizontalAlignment(Element.ALIGN_CENTER);
		
		PdfPCell hcell_1= new PdfPCell(new Phrase(" ", font));
		hcell_1.setBorder(Rectangle.NO_BORDER);
		hcell_1.setHorizontalAlignment(Element.ALIGN_CENTER);
		headertable01.addCell(hcell);
		headertable01.addCell(hcell_1);
		
		//=================================================Blank Cell Added To Remove Table Border==============================
		
		PdfPCell hcell_blank= new PdfPCell(headertable01);
		hcell_blank.setBorder(Rectangle.NO_BORDER);
		hcell_blank.disableBorderSide(Rectangle.RIGHT);
		hcell_blank.disableBorderSide(Rectangle.BOTTOM);
		
		//======================================================================================================================   2.1
		
		 String company_name = company;

		    Font font1 = new Font(FontFamily.HELVETICA, 8, Font.NORMAL,BaseColor.BLACK);
		    StringBuilder result1 = new StringBuilder();
		    result1.append(challan.getLoc_add1().length()>40 ? challan.getLoc_add1().substring(0, 40) : challan.getLoc_add1());
		    result1.append(System.lineSeparator());
		 
		    result1.append(challan.getLoc_add2().length()>40 ? challan.getLoc_add2().substring(0, 40) : challan.getLoc_add2());
		    result1.append(System.lineSeparator());
		   
		    result1.append(challan.getLoc_add3().length()>40 ? challan.getLoc_add3().substring(0, 40) : challan.getLoc_add3());
		    result1.append(System.lineSeparator());
		  
		    result1.append(challan.getLoc_add4().length()>40 ? challan.getLoc_add4().substring(0, 40) : challan.getLoc_add4());
		    result1.append(System.lineSeparator());
		    
		    PdfPCell hcell_cn=new PdfPCell(PdfStyle.createLabelCellWithBorderLargeFontNoBold(company_name));
		    hcell_cn.setHorizontalAlignment(Element.ALIGN_LEFT);
		    hcell_cn.setBorder(0);
	 
		PdfPCell hcell2w=new PdfPCell(PdfStyle.createLabelCellWithBorderSmallFontNoBoldForHeader(result1.toString()));
		hcell2w.setHorizontalAlignment(Element.ALIGN_LEFT);
		hcell2w.setBorder(0);

		PdfPTable addressTbl_a = new PdfPTable(1);
		addressTbl_a.addCell(hcell_cn);
		addressTbl_a.addCell(hcell2w);

		PdfPCell hcella_4=new PdfPCell(PdfStyle.createLabelCellWithoutBorderSmallFontNoPad("Drug Lic No:"));
		hcella_4.setHorizontalAlignment(Element.ALIGN_LEFT);
		hcella_4.setVerticalAlignment(Element.ALIGN_BOTTOM);
		hcella_4.setBorder(0);
		
		PdfPCell hcella_4_1=new PdfPCell(PdfStyle.createLabelCellWithBorderSmallFontNoBoldForHeader(challan.getLoc_dlic1()));
		hcella_4_1.setHorizontalAlignment(Element.ALIGN_LEFT);
		hcella_4_1.setVerticalAlignment(Element.ALIGN_TOP);
		hcella_4_1.setBorder(0);
		
		PdfPCell hcella_7=new PdfPCell(PdfStyle.createLabelCellWithoutBorderSmallFontNoPad(" "));
		hcella_7.setHorizontalAlignment(Element.ALIGN_LEFT);
		hcella_7.setVerticalAlignment(Element.ALIGN_TOP);
		hcella_7.setBorder(0);
		
		PdfPCell hcella_4_2=new PdfPCell(PdfStyle.createLabelCellWithBorderSmallFontNoBoldForHeader(challan.getLoc_dlic2()));
		hcella_4_2.setHorizontalAlignment(Element.ALIGN_LEFT);
		hcella_4_2.setVerticalAlignment(Element.ALIGN_TOP);
		hcella_4_2.setBorder(0);
		
		PdfPCell hcella_5=new PdfPCell(PdfStyle.createLabelCellWithoutBorderSmallFontNoPad("VAT Reg No:"));
		hcella_5.setHorizontalAlignment(Element.ALIGN_LEFT);
		hcella_5.setVerticalAlignment(Element.ALIGN_TOP);
		hcella_5.setBorder(0);
		
		
		PdfPCell hcella_4_3=new PdfPCell(PdfStyle.createLabelCellWithBorderSmallFontNoBoldForHeader(challan.getLoc_vatno()));
		hcella_4_3.setHorizontalAlignment(Element.ALIGN_LEFT);
		hcella_4_3.setVerticalAlignment(Element.ALIGN_TOP);
		hcella_4_3.setBorder(0);

		PdfPCell hcella_6=new PdfPCell(PdfStyle.createLabelCellWithoutBorderSmallFontNoPad("CST Reg No :"));
		hcella_6.setHorizontalAlignment(Element.ALIGN_LEFT);
		hcella_6.setVerticalAlignment(Element.ALIGN_TOP);
		hcella_6.setBorder(0);
		
		PdfPCell hcella_4_4=new PdfPCell(PdfStyle.createLabelCellWithBorderSmallFontNoBoldForHeader(challan.getLoc_cstno()));
		hcella_4_4.setHorizontalAlignment(Element.ALIGN_LEFT);
		hcella_4_4.setVerticalAlignment(Element.ALIGN_TOP);
		hcella_4_4.setBorder(0);
		
		PdfPCell hcella_8=new PdfPCell(PdfStyle.createLabelCellWithoutBorderSmallFontNoPad("CIN No :"));
		hcella_8.setHorizontalAlignment(Element.ALIGN_LEFT);
		hcella_8.setVerticalAlignment(Element.ALIGN_TOP);
		hcella_8.setBorder(0);
		
		PdfPCell hcella_4_8=new PdfPCell(PdfStyle.createLabelCellWithBorderSmallFontNoBoldForHeader(challan.getLoc_cinno()));
		hcella_4_8.setHorizontalAlignment(Element.ALIGN_LEFT);
		hcella_4_8.setVerticalAlignment(Element.ALIGN_TOP);
		hcella_4_8.setBorder(0);
		
		
		PdfPCell hcella_9=new PdfPCell(PdfStyle.createLabelCellWithoutBorderSmallFontNoPad("Website :"));
		hcella_9.setHorizontalAlignment(Element.ALIGN_LEFT);
		hcella_9.setVerticalAlignment(Element.ALIGN_TOP);
		hcella_9.setBorder(0);
		
		PdfPCell hcella_4_9=new PdfPCell(PdfStyle.createLabelCellWithBorderSmallFontNoBoldForHeader(challan.getLoc_web_site()));
		hcella_4_9.setHorizontalAlignment(Element.ALIGN_LEFT);
		hcella_4_9.setVerticalAlignment(Element.ALIGN_TOP);
		hcella_4_9.setBorder(0);
		
		
		PdfPCell hcella_10=new PdfPCell(PdfStyle.createLabelCellWithoutBorderSmallFontNoPad("Tel No :"));
		hcella_10.setHorizontalAlignment(Element.ALIGN_LEFT);
		hcella_10.setVerticalAlignment(Element.ALIGN_TOP);
		hcella_10.setBorder(0);
		
		PdfPCell hcella_4_10=new PdfPCell(PdfStyle.createLabelCellWithBorderSmallFontNoBoldForHeader(challan.getLoc_telephone_no()));
		hcella_4_10.setHorizontalAlignment(Element.ALIGN_LEFT);
		hcella_4_10.setVerticalAlignment(Element.ALIGN_TOP);
		hcella_4_10.setBorder(0);
		
		PdfPCell cell11 = new PdfPCell(addressTbl_a);
		cell11.setBorder(0);
		
		
		PdfPTable addressTbl = new PdfPTable(1);
		PdfPTable addressTbl_b = new PdfPTable(2);
		addressTbl_b.setWidths(new float[] { 1.5f,8.5f });
		addressTbl_b.addCell(hcella_4);
		addressTbl_b.addCell(hcella_4_1);
		addressTbl_b.addCell(hcella_7);
		addressTbl_b.addCell(hcella_4_2);
		
		addressTbl_b.addCell(hcella_5);
		addressTbl_b.addCell(hcella_4_3);
		addressTbl_b.addCell(hcella_6);
		addressTbl_b.addCell(hcella_4_4);
		
		addressTbl_b.addCell(hcella_8);
		addressTbl_b.addCell(hcella_4_8);
		addressTbl_b.addCell(hcella_9);
		addressTbl_b.addCell(hcella_4_9);
		addressTbl_b.addCell(hcella_10);
		addressTbl_b.addCell(hcella_4_10);
		
	
		PdfPCell cell12 = new PdfPCell(addressTbl_b);
		cell12.setBorder(0);
		addressTbl.addCell(cell11);
		addressTbl.addCell(cell12);
		PdfPCell cell1 = new PdfPCell(addressTbl);
		cell1.setBorder(0);
		cell1.setColspan(2);
		
		
		headertable2.addCell(cell1);
		
		
		
		//======================================================================================================================   
		
		
		//======================================================================================================================   2.3
		
			
			
				
			PdfPCell hcell16=new PdfPCell(PdfStyle.createLabelCellWithoutBorderSmallFont("Date "));
				hcell16.setHorizontalAlignment(Element.ALIGN_LEFT);
				
				PdfPCell hcell17=new PdfPCell(PdfStyle.createLabelCellWithoutBorderSmallFont("Slip#"));
				hcell17.setHorizontalAlignment(Element.ALIGN_LEFT);
				
				
				PdfPCell hcell17_a=new PdfPCell(PdfStyle.createLabelCellWithoutBorderSmallFont("SWV Type"));
				hcell17_a.setHorizontalAlignment(Element.ALIGN_LEFT);
				
				
				PdfPCell hcell18=new PdfPCell(PdfStyle.createLabelCellWithoutBorderSmallFont("No of Boxes"));
				hcell18.setHorizontalAlignment(Element.ALIGN_LEFT);
				
				PdfPCell hcell19=new PdfPCell(PdfStyle.createLabelCellWithoutBorderSmallFont("Weight"));
				hcell19.setHorizontalAlignment(Element.ALIGN_LEFT);
				hcell19.setVerticalAlignment(Element.ALIGN_TOP);
				//PdfPCell hcell20=new PdfPCell(PdfStyle.createLabelCellWithoutBorderSmallFont("Comment "));
				//hcell20.setHorizontalAlignment(Element.ALIGN_LEFT);
				//hcell20.setVerticalAlignment(Element.ALIGN_TOP);
				
				PdfPTable addressTbl2= new PdfPTable(1);
				addressTbl2.addCell(hcell16);
				addressTbl2.addCell(hcell17);
				addressTbl2.addCell(hcell17_a);
				addressTbl2.addCell(hcell18);
				addressTbl2.addCell(hcell19);
				//addressTbl2.addCell(hcell20);
	
				
				PdfPCell cell3 = new PdfPCell(addressTbl2);
				cell3.setBorder(0);
				
				headertable2.addCell(cell3);
				
				//======================================================================================================================   
				
				
				//======================================================================================================================   2.4
				 Font medFont = new Font(Font.FontFamily.HELVETICA, 12,
						Font.NORMAL, BaseColor.BLACK);
				
						
						
						PdfPCell hcell26=new PdfPCell(PdfStyle.createLabelCellWithoutBorderSmallFontNoBold((challan.getChallan_dt()==null?"":challan.getChallan_dt())));
						hcell26.setHorizontalAlignment(Element.ALIGN_LEFT);
						
						PdfPCell hcell27=new PdfPCell(PdfStyle.createLabelCellWithoutBorderSmallFontNoBold((challan.getSwv_challan_no().length()>26 ? challan.getSwv_challan_no().substring(0, 26) : challan.getSwv_challan_no())));
						hcell27.setHorizontalAlignment(Element.ALIGN_LEFT);
						
						PdfPCell hcell27_a=new PdfPCell(PdfStyle.createLabelCellWithoutBorderSmallFontNoBold((challan.getWithdrawal_type().length()>26 ? challan.getWithdrawal_type().substring(0, 26) : challan.getWithdrawal_type())));
						hcell27_a.setHorizontalAlignment(Element.ALIGN_LEFT);
						
						PdfPCell hcell28=new PdfPCell(PdfStyle.createLabelCellWithoutBorderSmallFontNoBold(""));
						hcell28.setHorizontalAlignment(Element.ALIGN_LEFT);
						
						PdfPCell hcell29=new PdfPCell(PdfStyle.createLabelCellWithoutBorderSmallFontNoBold(""));
						hcell29.setHorizontalAlignment(Element.ALIGN_LEFT);
						
						PdfPCell hcell34=new PdfPCell(PdfStyle.createLabelCellWithoutBorderSmallFontNoBold(challan.getSwv_challan_msg()==null?"":challan.getSwv_challan_msg()));
						hcell34.setHorizontalAlignment(Element.ALIGN_LEFT);
						hcell34.setVerticalAlignment(Element.ALIGN_TOP);
						PdfPTable addressTbl3= new PdfPTable(1);
						addressTbl3.addCell(hcell26);
						addressTbl3.addCell(hcell27);
						addressTbl3.addCell(hcell27_a);
						addressTbl3.addCell(hcell28);
						addressTbl3.addCell(hcell29);
						
						//addressTbl3.addCell(hcell34);
						
						PdfPCell cell4 = new PdfPCell(addressTbl3);
						cell4.setBorder(0);
						
						headertable2.addCell(cell4);
				
				
				//=================================================Blank Cell Added To Remove Table Border==============================
						
						PdfPCell hcell_blank2= new PdfPCell(headertable2);
						hcell_blank2.setBorder(Rectangle.NO_BORDER);
				
				
				
				
				//=====================================================================================================================  
				
		
				//=====================================================================================================================   3.1
				
				PdfPCell hcell35=new PdfPCell(PdfStyle.createLabelCellWithoutBorderSmallFont("Bill To :"));
				hcell35.setHorizontalAlignment(Element.ALIGN_LEFT);
				
				PdfPCell hcell36=new PdfPCell(PdfStyle.createLabelCellWithoutBorderSmallFontNoPad(challan.getSwv_sender_name()==null?"":challan.getSwv_sender_name()));
				hcell36.setHorizontalAlignment(Element.ALIGN_LEFT);
				
				 StringBuilder result2 = new StringBuilder();
				  result2.append(challan.getSwv_sender_address1().length()>50 ? challan.getSwv_sender_address1().substring(0, 50) : challan.getSwv_sender_address1());
				    result2.append(System.lineSeparator());
				    result2.append(challan.getSwv_sender_address2().length()>50 ? challan.getSwv_sender_address2().substring(0, 50) : challan.getSwv_sender_address2());
				    result2.append(System.lineSeparator());
				    result2.append(challan.getSwv_sender_address3().length()>50 ? challan.getSwv_sender_address3().substring(0, 50) : challan.getSwv_sender_address3());
				    result2.append(System.lineSeparator());
				    
				PdfPCell hcell37=new PdfPCell(PdfStyle.createLabelCellWithoutBorderSmallFontNoBold(result2.toString()==null?"":result2.toString()));
				hcell37.setHorizontalAlignment(Element.ALIGN_LEFT);
				
				
				PdfPTable custTbl = new PdfPTable(1);
				custTbl.addCell(hcell35);
				custTbl.addCell(hcell36);
				custTbl.addCell(hcell37);
			
				
				PdfPCell cell5 = new PdfPCell(custTbl);
				cell5.setBorder(0);
				
				headertable3.addCell(cell5);
				
				//======================================================================================================================  
				
				
				//======================================================================================================================   3.2
				
				 
				PdfPCell hcell38=new PdfPCell(PdfStyle.createLabelCellWithoutBorderSmallFont("Ship To :"));
				hcell38.setHorizontalAlignment(Element.ALIGN_LEFT);
				PdfPCell hcell36_1=new PdfPCell(PdfStyle.createLabelCellWithoutBorderSmallFontNoPad(challan.getSwv_sender_name()==null?"":challan.getSwv_sender_name()));
				hcell36_1.setHorizontalAlignment(Element.ALIGN_LEFT);
				PdfPCell hcell40=new PdfPCell(PdfStyle.createLabelCellWithoutBorderSmallFontNoBold(result2.toString()==null?"":result2.toString()));
				hcell40.setHorizontalAlignment(Element.ALIGN_LEFT);
				
				PdfPTable custTbl1 = new PdfPTable(1);
				custTbl1.addCell(hcell38);
				custTbl1.addCell(hcell36_1);
				custTbl1.addCell(hcell40);
				
				
				PdfPCell cell6 = new PdfPCell(custTbl1);
				cell6.setBorder(0);
				
				headertable3.addCell(cell6);
				
				//====================================================================================================================== 
				
				
				//=====================================================================================================================   3.3
				
			
				
				//=================================================Blank Cell Added To Remove Table Border3==============================
				
				PdfPCell hcell_blank3= new PdfPCell(headertable3);
				hcell_blank3.setBorder(Rectangle.NO_BORDER);

		
		bodytable = new PdfPTable(4);

		bodytable.setWidthPercentage(100);
		bodytable.setWidths(pickbodyTableWidth);
		bodytable.addCell(PdfStyle.createLabelWithBGColour("Item"));
		bodytable.addCell(PdfStyle.createLabelWithBGColour("Quantity"));
		bodytable.addCell(PdfStyle.createLabelWithBGColour("Stock Type"));
		bodytable.addCell(PdfStyle.createLabelWithBGColour("Batch/Art Work Number"));
		
		//=================================================Blank Cell Added To Remove Table Border4==============================
		
		PdfPCell hcell_blank4= new PdfPCell(bodytable);
		hcell_blank4.setBorder(Rectangle.NO_BORDER);
		//hcell_blank2.enableBorderSide(Rectangle.RIGHT);
		//hcell_blank2.disableBorderSide(Rectangle.BOTTOM);
		//hcell_blank3
		//headertable.addCell(headertable01);
		headertable.addCell(hcell_blank);
		//headertable.addCell(headertable2);
		headertable.addCell(hcell_blank2);
		headertable.addCell(hcell_blank3);
		//headertable.addCell(headertable3);
		//headertable.addCell(headertable4);
		//headertable.addCell(bodytable);
		headertable.addCell(hcell_blank4);

		
		return headertable;
	}
	
	
	private PdfPTable PickcreateFooter(HashMap<String, String> printLabel, boolean isFooterData,boolean pageflag,int pagecount, BigDecimal total
			, Long loc, String frm_challan,
			String to_challan,List<ViewStockWithdrawalVoucherPrint> challan) throws DocumentException {
		PdfPTable maintable = new PdfPTable(1);
		
	System.out.println("challan size() " +challan.size());
	try{
	for (ViewStockWithdrawalVoucherPrint obj : challan) {
		PdfPCell cell = null;
		
		maintable.setWidthPercentage(100);
		
		cell = new PdfPCell();
		
		maintable.setWidths(new float[] { 10f });
		
								
		
		//=====================================end of total====================================================
		

		PdfPTable childTbl1 = new PdfPTable(2);
		childTbl1.setWidths(new float[] { 9f,1f });
		
		
		//------------------------------------------------------------------------------------------------------
		PdfPCell hcell_t1=new PdfPCell(PdfStyle.createLabelCellWithoutBorderSmallFont("Picked by"));
		hcell_t1.setHorizontalAlignment(Element.ALIGN_LEFT);
		
		PdfPCell hcell_t3=null;
	
		hcell_t3=new PdfPCell(PdfStyle.createLabelCellWithoutBorderSmallFont("Packed by"));
		
		hcell_t3.setHorizontalAlignment(Element.ALIGN_LEFT);
		
		//------------------------------------------------------------------------------------------------------
		PdfPCell hcell_t1_date=new PdfPCell(PdfStyle.createLabelCellWithoutBorderSmallFont("Date"));
		hcell_t1_date.setHorizontalAlignment(Element.ALIGN_LEFT);
		
		PdfPCell hcell_t3_date=null;
		
		hcell_t3_date=new PdfPCell(PdfStyle.createLabelCellWithoutBorderSmallFont("Date"));	
		
		hcell_t3_date.setHorizontalAlignment(Element.ALIGN_LEFT);
	
	
		
		childTbl1.addCell(hcell_t1);
		childTbl1.addCell(hcell_t3);
		
		childTbl1.addCell(hcell_t1_date);
		childTbl1.addCell(hcell_t3_date);
	
		
		PdfPCell hcell4=new PdfPCell(childTbl1);
		hcell4.setBorder(Rectangle.NO_BORDER);
		
	
		
	
		maintable.addCell(hcell4);
		

		//-----------------------------------------------------------------------------------------------------------------
		//-----------------------------------------------------------------------------------------------------------------

		

		if(pageflag){
			cell = new PdfPCell(PdfStyle.createLabelCellWithoutBorderSmallFontNoBold("Page No. "+pagecount));
			cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
			cell.setVerticalAlignment(Element.ALIGN_BOTTOM);
			maintable.addCell(cell);
		
		}
		break;
	}//end of for each list
	}catch(Exception e){
		System.out.println("Error Occurred :" +e.getMessage());// uncomment asneeded --;
	}
		return maintable;
	}
}
