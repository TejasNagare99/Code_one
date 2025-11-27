package com.excel.service;

import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.io.PrintStream;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
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

import com.excel.configuration.JwtProvider;
import com.excel.model.Company;
import com.excel.model.ViewPrePrintedSummaryChallan_GST_EInvoice;
import com.excel.repository.UserMasterRepository;
import com.excel.utility.CodifyErrors;
import com.excel.utility.FooterWithoutPageNo;
import com.excel.utility.MedicoConstants;
import com.excel.utility.MedicoResources;
import com.excel.utility.NumberToWordsBritish;
import com.excel.utility.PdfStyle;
import com.excel.utility.PdfStyleNew;
import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.Image;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.Rectangle;
import com.itextpdf.text.Font.FontFamily;
import com.itextpdf.text.pdf.ColumnText;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;

@Service
public class PrePrintedSummaryChallanEInvoiceServiceImpl implements PrePrintedSummaryChallanEInvoiceService,MedicoConstants {
	
	@Autowired PrintService printservice;
	
	ViewPrePrintedSummaryChallan_GST_EInvoice footer;
	@Autowired
	private UserMasterRepository userMasterRepository;
	@Autowired private UserMasterService usermasterservice;


@Autowired private JwtProvider tokenProvider;
@Autowired private UserMasterService userMasterService;


    int add = 0;
    Font fontbold = null;
    int fontSize = 7;
    private HashMap<String, String> printLabel;
    private String path;
    private Boolean pageflag;
    private String logo;
    private String remark;
    private String narration;
    private String stockPntName;
    private int limit = 13;
    private boolean longDesc;

    private float[] bodyTableWidth = new float[] { 4f, 3f, 2f, 1f, 1f, 1f };
    // private BigDecimal[] totalAmt =
    // {BigDecimal.ZERO,BigDecimal.ZERO,BigDecimal.ZERO,BigDecimal.ZERO};

	@Override
	public String PrePrintedSummaryChallanEInvoicePrint(int division, Integer loc, String frm_challan, String to_challan,
			String dispatchType, String prodtype, String printtype, List<ViewPrePrintedSummaryChallan_GST_EInvoice> challans,
			String show_amount, String footer_signature_ind,String Companycode,String CompanyName,HttpSession session,HttpServletRequest request) throws Exception {

		/*
		 * ServletContext context =
		 * ServletActionContext.getRequest().getServletContext();
		 */
		String path = MedicoConstants.PDF_FILE_PATH;
		String com_code = Companycode;

		System.out.println("Company Code :" + com_code);
		System.out.println("For Pfizer");
		setPath(path);
		System.out.println("path 11 : " + path);
		FileOutputStream os = null;
		PrintStream ps = null;
		long timevar = new Date().getTime();
		String filename = "taxinvoice_qrcode" + timevar + ".pdf";
		System.out.println("filename 11 " + filename);
		File docfile1 = new File(path + "\\files\\");
		docfile1.mkdirs();
		File docfile = new File(path + "\\files\\" + filename);
		OutputStream file = new FileOutputStream(docfile);
		Document document = new Document(PageSize.A4);
		PdfWriter writer = PdfWriter.getInstance(document, file);
		Rectangle rect = new Rectangle(20, 28, 580, 820);//840
		writer.setBoxSize("header", rect);
		rect = new Rectangle(20, 2, 580, 198);//201
		writer.setBoxSize("footer", rect);
		// int pagecount=writer.getPageNumber();
		int pagecount = 1;
		FooterWithoutPageNo event = new FooterWithoutPageNo();
		writer.setPageEvent(event);
		BigDecimal netTotalFinal = new BigDecimal(0);
		String uname = this.tokenProvider.getUsernameFromRequest(request);String empId = this.userMasterService.getEmpIdByUsername(uname);
		 writer = usermasterservice.generatePDFlock(empId, writer);
		document.open();
		document.setMarginMirroringTopBottom(false);
		boolean isnew = false;
		boolean for_new_page = false;
		ColumnText column = new ColumnText(writer.getDirectContent());
		try {
		    BigDecimal total = new BigDecimal(0);
		    SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
		    Set<String> challanset = new LinkedHashSet<String>();
		    for (ViewPrePrintedSummaryChallan_GST_EInvoice challan : challans) {
			challanset.add(challan.getDsp_id().toString());
			/*
			 * System.out.println(challan.getCgst_rate());
			 * System.out.println(challan.getSgst_rate());
			 * System.out.println(challan.getIgst_rate());
			 * System.out.println(challan.getCgst_bill_amt());
			 * System.out.println(challan.getHcgst_bill_amt());
			 * System.out.println(challan.getHigst_bill_amt());
			 * System.out.println(challan.getIgst_bill_amt());
			 * System.out.println(challan.getSgst_bill_amt());
			 */
		    }
		    PdfPTable bodytable = null;
		    boolean footer_ind = false;
		    for (String num : challanset) {
			List<ViewPrePrintedSummaryChallan_GST_EInvoice> list_new_page = new ArrayList<ViewPrePrintedSummaryChallan_GST_EInvoice>();
			isnew = true;
			for_new_page = true;
			PdfPTable maintable1 = new PdfPTable(1);
			maintable1.setWidthPercentage(100);
			PdfPCell cell = null;
			pageflag = false;
			pagecount = 1;

			PdfPTable hcell = null;
			int rows = 0;
			bodytable = new PdfPTable(6);
			bodytable.getDefaultCell().setPadding(0.0f);
			bodytable.setWidthPercentage(100);
			bodytable.setWidths(bodyTableWidth);
			total = BigDecimal.ZERO;

			String dtp = "";
			String ship_doc_no = "";

			String date = "";
			String cases = "";
			String weigh = "";
			String dsp_challan_msg = "";
			int count_var = 0;
			String roundOff = "";
			BigDecimal igstbill = new BigDecimal(0);
			BigDecimal sgstbill = new BigDecimal(0);
			BigDecimal cgstbill = new BigDecimal(0);
			BigDecimal valueTotal = new BigDecimal(0);
			BigDecimal igstheader = new BigDecimal(0);
			BigDecimal cgstheader = new BigDecimal(0);
			BigDecimal sgstheader = new BigDecimal(0);

			for (ViewPrePrintedSummaryChallan_GST_EInvoice challan : challans) {
				date = challan.getShip_doc_date()!=null?sdf.format(challan.getShip_doc_date()):" ";
			    if (num.equalsIgnoreCase(challan.getDsp_id().toString())) {
				if (rows >= limit) {
				    count_var++;

				    if (footer_signature_ind.equalsIgnoreCase("Y")) {
					footer_ind = false;// suppress footer signature
				    } else {
					footer_ind = true;// show footer signature
				    }
				    // System.out.println("getDoc_no "
				    // +challan.getDoc_no());
				    pageflag = true;
				    System.out.println("Inside rows>=limit");
				    System.out.println("count_var " + count_var + "getDoc_no  " + challan.getDoc_no());
				    bodytable = newPage(column, bodytable, event, printLabel, false, rows, pageflag, pagecount,
					    total, division, loc, frm_challan, to_challan, dispatchType, prodtype, path,
					    challans, show_amount, footer_ind, challan.getDsp_transporter(),
					    challan.getShip_doc_no(), date, challan.getCases().toString(),
					    challan.getWeigh().toString(), challan.getRoundoff().toString(),
					    challan.getAlloc_remark().length() > 600
						    ? challan.getAlloc_remark().substring(0, 600) : challan.getAlloc_remark(),
					    challan.getIgst_bill_amt(), challan.getSgst_bill_amt(), challan.getCgst_bill_amt(),
					    challan.getValue(), challan.getHigst_bill_amt(), challan.getHcgst_bill_amt(),
					    challan.getHsgst_bill_amt(), netTotalFinal,CompanyName,Companycode);

				    document.newPage();
				    pagecount++;
				    rows = 0;

				}
				if (isnew) {
				    footer = challan;
				//    maintable1.addCell(PdfStyleNew.createLabelCellWithoutBorderSmallFontNoBold(""));
				    hcell = createHeader(challan, pagecount, division, loc, frm_challan, to_challan,
					    dispatchType, prodtype, path, show_amount,CompanyName,Companycode,session);
				    cell = new PdfPCell(hcell);
				    cell.setBorder(0);
				    cell.setHorizontalAlignment(Element.ALIGN_CENTER);
				  //  cell.setPaddingBottom(5f);//5f
				    maintable1.addCell(cell);
				    event.setHeader(maintable1);
				    isnew = false;
				    list_new_page.add(challan);
				}
				System.out.println("rows val " + rows);
				/*
				 * if(rows==0){ bodytable.addCell(PdfStyleNew.
				 * createValueCellWithHeight3((""),false,true,16f,true,
				 * false,false,null,false));
				 * bodytable.addCell(PdfStyleNew.
				 * createValueCellWithHeight3((""),false,true,16f,true,
				 * false,false,null,false));
				 * bodytable.addCell(PdfStyleNew.
				 * createValueCellWithHeight3((""),false,true,16f,true,
				 * false,false,null,false));
				 * bodytable.addCell(PdfStyleNew.
				 * createValueCellWithHeight3((""),true,true,16f,true,
				 * false,false,null,false));
				 * bodytable.addCell(PdfStyleNew.
				 * createValueCellWithHeight3((""),true,true,16f,true,
				 * false,false,null,false));
				 * bodytable.addCell(PdfStyleNew.
				 * createValueCellWithHeight3((""),true,true,16f,false,
				 * false,false,null,false)); }
				 */

				if (challan.getProduct_desc().length() > 49) {
				    longDesc = true;
				} else {
				    longDesc = false;
				}

				bodytable.addCell(PdfStyleNew.createValueCellWithHeight3(
					(challan.getProduct_desc().length() > 40 ? challan.getProduct_desc().substring(0, 40)
						: challan.getProduct_desc()), 
					false, true, 13f, true, false, false, null, false));
				// bodytable.addCell(PdfStyleNew.createValueCellWithHeight3("AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA",false,true,13f,true,false,false,null,false));
				bodytable.addCell(PdfStyleNew.createValueCellWithHeight3((challan.getBatch_no()), false, true,
					13f, true, false, false, null, false));
				bodytable.addCell(PdfStyleNew.createValueCellWithHeight3((challan.getExpiry_date()), false,
					true, 13f, true, false, false, null, false));
				bodytable.addCell(
					PdfStyleNew.createValueCellWithHeight3((String.valueOf(challan.getTotal_qty())), false,
						true, 13f, true, false, false, null, false));
				bodytable.addCell(PdfStyleNew.createValueCellWithHeight3(String.valueOf(challan.getRate()),
					false, true, 13f, true, false, false, null, false));
				if (show_amount.equals("Y")) {
				    bodytable
					    .addCell(
						    PdfStyleNew
							    .createValueCellWithHeight3(
								    (challan.getValue() == null
									    ? BigDecimal.ZERO.setScale(2, RoundingMode.HALF_UP)
									    : challan.getValue().setScale(2,
										    RoundingMode.HALF_UP)).toString(),
								    true, true, 13f, false, false, false, null, false));
				} else {
				    bodytable.addCell(PdfStyleNew.createValueCellWithHeight3("", true, true, 13f, false, false,
					    false, null, false));
				}

				if (challan.getIgst_bill_amt().compareTo(BigDecimal.ZERO) > 0) {
				    bodytable
					    .addCell(PdfStyleNew.createValueCellWithHeight3(
						    "HSN Code: " + challan.getHsn_code() + ""
							    + "                            IGST%: "
							    + challan.getIgst_rate(),
						    true, true, 13f, true, false, false, null, true));
				    bodytable.addCell(
					    PdfStyleNew.createValueCellWithHeight3("Amount : " + challan.getIgst_bill_amt(),
						    true, true, 13f, true, false, false, null, true));
				    bodytable.addCell(PdfStyleNew.createValueCellWithHeight3("", true, true, 13f, true, false,
					    false, null, true));
				    bodytable.addCell(PdfStyleNew.createValueCellWithHeight3("", true, true, 13f, true, false,
					    false, null, true));
				    bodytable.addCell(PdfStyleNew.createValueCellWithHeight3("", true, true, 13f, true, false,
					    false, null, true));
				    bodytable.addCell(PdfStyleNew.createValueCellWithHeight3("", true, true, 13f, false, false,
					    false, null, true));
				} else {
				    bodytable
					    .addCell(PdfStyleNew.createValueCellWithHeight3(
						    "HSN Code: " + challan.getHsn_code() + ""
							    + "                           CGST%: "
							    + challan.getCgst_rate(),
						    true, true, 13f, true, false, false, null, true));
				    bodytable.addCell(PdfStyleNew.createValueCellWithHeight3(
					    "Amount : " + challan.getCgst_bill_amt() + "" + "                       SGST%: ",
					    true, true, 13f, true, false, false, null, true));
				    bodytable.addCell(PdfStyleNew.createValueCellWithHeight3(challan.getSgst_rate().toString(),
					    true, true, 13f, true, false, false, null, true));
				    bodytable.addCell(PdfStyleNew.createValueCellWithHeight3("Amount : ", true, true, 13f, true,
					    false, false, null, true));
				    bodytable.addCell(
					    PdfStyleNew.createValueCellWithHeight3(challan.getSgst_bill_amt().toString(), true,
						    true, 13f, true, false, false, null, true));
				    bodytable.addCell(PdfStyleNew.createValueCellWithHeight3("", true, true, 13f, false, false,
					    false, null, true));
				}

				total = total.add(challan.getValue());
				rows++;
				dtp = "";
				ship_doc_no = "";
				date = "";
				cases = "";
				weigh = "";
				dsp_challan_msg = "";

				dtp = challan.getDsp_transporter();
				ship_doc_no = challan.getShip_doc_no();
				date = challan.getShip_doc_date()!=null?sdf.format(challan.getShip_doc_date()):" ";
				cases = challan.getCases().toString();
				weigh = challan.getWeigh().toString();
				roundOff = challan.getRoundoff().toString();
				igstbill = challan.getIgst_bill_amt();
				sgstbill = challan.getSgst_bill_amt();
				cgstbill = challan.getCgst_bill_amt();
				valueTotal = challan.getValue();
				igstheader = challan.getHigst_bill_amt();
				cgstheader = challan.getHcgst_bill_amt();
				sgstheader = challan.getHsgst_bill_amt();
				netTotalFinal = total
					.add(igstheader.add(sgstheader.add(cgstheader.add(new BigDecimal(roundOff)))));
				// dsp_challan_msg is the remark that is currently
				// printed.
				dsp_challan_msg = challan.getAlloc_remark().length() > 600
					? challan.getAlloc_remark().substring(0, 600) : challan.getAlloc_remark();
			    }
			}

			footer_ind = true;
			System.out.println("Outside rows>=limit");
			newPage(column, bodytable, event, printLabel, true, rows, pageflag, pagecount, total, division, loc,
				frm_challan, to_challan, dispatchType, prodtype, path, list_new_page, show_amount,
				footer_ind, dtp, ship_doc_no, date, cases, weigh, roundOff, dsp_challan_msg, igstbill, sgstbill,
				cgstbill, valueTotal, igstheader, cgstheader, sgstheader, netTotalFinal,CompanyName,Companycode);

			document.newPage();
		    }

		} catch (Exception e) {
			e.printStackTrace();
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

	 private PdfPTable createHeader(ViewPrePrintedSummaryChallan_GST_EInvoice challan, int pagecount, int division, Integer loc,
			    String frm_challan, String to_challan, String dispatchType, String prodtype, String path,
			    String show_amount,String CompanyName,String CompanyCode,HttpSession session) throws Exception {
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
			headertable2.setWidths(new float[] { 5f, 2f, 1f, 2f });
			headertable3.setWidths(new float[] { 4.5f, 2.5f, 1f, 2f });
			headertable4.setWidths(new float[] { 2f, 2f, 2.5f, 3.5f });

			Font font = new Font(FontFamily.HELVETICA, 12, Font.BOLD, BaseColor.BLACK);
			PdfPTable headertable1 = new PdfPTable(1);
			headertable1.setWidthPercentage(100);
			headertable1.setWidths(new float[] { 5f });

			// ======================================================================================================================
			// 1
			
			
			
			

			PdfPCell hcell = null;
//			if(CompanyCode.trim().equals(VET)) {
//				hcell =new PdfPCell(new Phrase(" ", font));
//			}
//			else {
				hcell =new PdfPCell(new Phrase("Tax Invoice", font));
	//		}
			//hcell.setBorder(1);
			hcell.setHorizontalAlignment(Element.ALIGN_CENTER);

			PdfPCell hcell_1 = new PdfPCell(new Phrase(" ", font));
			hcell_1.setBorder(Rectangle.NO_BORDER);
			hcell_1.setHorizontalAlignment(Element.ALIGN_CENTER);
			headertable01.addCell(hcell);
			// headertable01.addCell(hcell_1);

			// =================================================Blank Cell Added To
			// Remove Table Border==============================

			PdfPCell hcell_blank = new PdfPCell(headertable01);
			hcell_blank.setBorder(Rectangle.NO_BORDER);
			hcell_blank.disableBorderSide(Rectangle.RIGHT);
			hcell_blank.disableBorderSide(Rectangle.BOTTOM);

			// ======================================================================================================================
			// 2.1
			String company_name = CompanyName;

			Font font1 = new Font(FontFamily.HELVETICA, 8, Font.NORMAL, BaseColor.BLACK);
			StringBuilder result1 = new StringBuilder();
			result1.append(
				challan.getLoc_add1().length() > 40 ? challan.getLoc_add1().substring(0, 40) : challan.getLoc_add1());
			result1.append(System.lineSeparator());

			result1.append(
				challan.getLoc_add2().length() > 40 ? challan.getLoc_add2().substring(0, 40) : challan.getLoc_add2());
			result1.append(System.lineSeparator());

			result1.append(
				challan.getLoc_add3().length() > 40 ? challan.getLoc_add3().substring(0, 40) : challan.getLoc_add3());
			result1.append(System.lineSeparator());

			result1.append(
				challan.getLoc_add4().length() > 40 ? challan.getLoc_add4().substring(0, 40) : challan.getLoc_add4());
			result1.append(System.lineSeparator());

			String comp_name_new = "Pfizer Products India Private Limited.";
			String comp_cd = CompanyCode;
			PdfPCell hcell_cn = null;
			System.out.println("doc no :: "+challan.getDoc_no());
			if (challan.getDoc_no().contains("PIP")) {
				System.out.println("comp_cd :: "+comp_cd);
			    if (comp_cd.trim().equalsIgnoreCase("PFZ")) {
			    
				hcell_cn = new PdfPCell(PdfStyle.createLabelCellWithBorderLargeFontNoBold(comp_name_new));
				hcell_cn.setBorder(Rectangle.LEFT);
			    } else {
			    	
				hcell_cn = new PdfPCell(PdfStyle.createLabelCellWithBorderLargeFontNoBold(company_name));
				hcell_cn.setBorder(Rectangle.LEFT);
			    }
			    hcell_cn.setHorizontalAlignment(Element.ALIGN_LEFT);
			    hcell_cn.setBorder(Rectangle.LEFT);
			} else {
				if(CompanyCode.trim().equals(VET)) {
				    hcell_cn = new PdfPCell(PdfStyle.createLabelCellWithBorderLargeFontNoBold(company_name));
				    hcell_cn.setHorizontalAlignment(Element.ALIGN_LEFT);
				    hcell_cn.setBorder(Rectangle.LEFT);
				}
				else {
				    hcell_cn = new PdfPCell(PdfStyle.createLabelCellWithBorderLargeFontNoBold(company_name));
				    hcell_cn.setHorizontalAlignment(Element.ALIGN_LEFT);
				    hcell_cn.setBorder(Rectangle.LEFT);
				}
			}
			hcell_cn.setBorder(Rectangle.LEFT);
			PdfPCell hcell2w = new PdfPCell(
				PdfStyleNew.createLabelCellWithBorderSmallFontNoBoldForHeader(result1.toString()));
			hcell2w.setHorizontalAlignment(Element.ALIGN_LEFT);
			hcell2w.setBorder(Rectangle.LEFT);

			PdfPTable addressTbl_a = new PdfPTable(1);
			addressTbl_a.addCell(hcell_cn);
			addressTbl_a.addCell(hcell2w);

			PdfPCell hcella_4 = new PdfPCell(PdfStyleNew.createLabelCellWithoutBorderSmallFontNoPad("Drug Lic No:"));
			hcella_4.setHorizontalAlignment(Element.ALIGN_LEFT);
			hcella_4.setVerticalAlignment(Element.ALIGN_TOP);
			hcella_4.setBorder(Rectangle.LEFT);

			PdfPCell hcella_4_1 = new PdfPCell(
				PdfStyleNew.createLabelCellWithBorderSmallFontNoBoldForHeader(challan.getLic1()));
			hcella_4_1.setHorizontalAlignment(Element.ALIGN_LEFT);
			hcella_4_1.setVerticalAlignment(Element.ALIGN_TOP);
			hcella_4_1.setBorder(0);

		
			PdfPCell hcella_7 = new PdfPCell(PdfStyleNew.createLabelCellWithoutBorderSmallFontNoPad(" "));
			hcella_7.setHorizontalAlignment(Element.ALIGN_LEFT);
			hcella_7.setVerticalAlignment(Element.ALIGN_TOP);
			hcella_7.setBorder(Rectangle.LEFT);

			PdfPCell hcella_4_2 = new PdfPCell(
				PdfStyleNew.createLabelCellWithBorderSmallFontNoBoldForHeader(challan.getLic2()));
			hcella_4_2.setHorizontalAlignment(Element.ALIGN_LEFT);
			hcella_4_2.setVerticalAlignment(Element.ALIGN_TOP);
			hcella_4_2.setBorder(0);

			PdfPCell hcella_5 = new PdfPCell(PdfStyleNew.createLabelCellWithoutBorderSmallFontNoPad("GST Reg No."));
			hcella_5.setHorizontalAlignment(Element.ALIGN_LEFT);
			hcella_5.setVerticalAlignment(Element.ALIGN_TOP);
			hcella_5.setBorder(Rectangle.LEFT);

			PdfPCell hcella_4_3 = new PdfPCell(
				PdfStyleNew.createLabelCellWithBorderSmallFontNoBoldForHeader(challan.getLgst_reg_no()));
			hcella_4_3.setHorizontalAlignment(Element.ALIGN_LEFT);
			hcella_4_3.setVerticalAlignment(Element.ALIGN_TOP);
			hcella_4_3.setBorder(0);

			/*
			 * PdfPCell hcella_6=new PdfPCell(PdfStyleNew.
			 * createLabelCellWithoutBorderSmallFontNoPad("CST Reg No :"));
			 * hcella_6.setHorizontalAlignment(Element.ALIGN_LEFT);
			 * hcella_6.setVerticalAlignment(Element.ALIGN_TOP);
			 * hcella_6.setBorder(0);
			 */

			/*
			 * PdfPCell hcella_4_4=new PdfPCell(PdfStyleNew.
			 * createLabelCellWithBorderSmallFontNoBoldForHeader(challan.
			 * getLoc_cst_no()));
			 * hcella_4_4.setHorizontalAlignment(Element.ALIGN_LEFT);
			 * hcella_4_4.setVerticalAlignment(Element.ALIGN_TOP);
			 * hcella_4_4.setBorder(0);
			 */

			PdfPCell hcella_8 = new PdfPCell(PdfStyleNew.createLabelCellWithoutBorderSmallFontNoPad("CIN No :"));
			hcella_8.setHorizontalAlignment(Element.ALIGN_LEFT);
			hcella_8.setVerticalAlignment(Element.ALIGN_TOP);
			hcella_8.setBorder(Rectangle.LEFT);

			PdfPCell hcella_4_8 = new PdfPCell(
				PdfStyleNew.createLabelCellWithBorderSmallFontNoBoldForHeader(challan.getLoc_cinno()));
			hcella_4_8.setHorizontalAlignment(Element.ALIGN_LEFT);
			hcella_4_8.setVerticalAlignment(Element.ALIGN_TOP);
			hcella_4_8.setBorder(0);
			PdfPCell hcella_9 = null;
			// String loc_web_var=challan.getLoc_web_site();
			if (null != challan.getLoc_web_site() && challan.getLoc_web_site().trim().isEmpty()) {
			    hcella_9 = new PdfPCell(PdfStyleNew.createLabelCellWithoutBorderSmallFontNoPad(" "));
			    hcella_9.setHorizontalAlignment(Element.ALIGN_LEFT);
			    hcella_9.setVerticalAlignment(Element.ALIGN_TOP);
			    hcella_9.setBorder(0);
			} else {
			    hcella_9 = new PdfPCell(PdfStyleNew.createLabelCellWithoutBorderSmallFontNoPad("Website :"));
			    hcella_9.setHorizontalAlignment(Element.ALIGN_LEFT);
			    hcella_9.setVerticalAlignment(Element.ALIGN_TOP);
			    hcella_9.setBorder(Rectangle.LEFT);
			}

			PdfPCell hcella_4_9 = new PdfPCell(
				PdfStyleNew.createLabelCellWithBorderSmallFontNoBoldForHeader(challan.getLoc_web_site()));
			hcella_4_9.setHorizontalAlignment(Element.ALIGN_LEFT);
			hcella_4_9.setVerticalAlignment(Element.ALIGN_TOP);
			hcella_4_9.setBorder(0);

			PdfPCell hcella_10 = new PdfPCell(PdfStyleNew.createLabelCellWithoutBorderSmallFontNoPad("Tel No :"));
			hcella_10.setHorizontalAlignment(Element.ALIGN_LEFT);
			hcella_10.setVerticalAlignment(Element.ALIGN_TOP);
			hcella_10.setBorder(Rectangle.LEFT);

			PdfPCell hcella_4_10 = new PdfPCell(
				PdfStyleNew.createLabelCellWithBorderSmallFontNoBoldForHeader(challan.getLoc_telephone_no()));
			hcella_4_10.setHorizontalAlignment(Element.ALIGN_LEFT);
			hcella_4_10.setVerticalAlignment(Element.ALIGN_TOP);
			hcella_4_10.setBorder(0);

			PdfPCell hcella_11 = new PdfPCell(PdfStyleNew.createLabelCellWithoutBorderSmallFontNoPad("Email Id :"));
			hcella_11.setHorizontalAlignment(Element.ALIGN_LEFT);
			hcella_11.setVerticalAlignment(Element.ALIGN_TOP);
			hcella_11.setBorder(Rectangle.LEFT);

			PdfPCell hcella_4_11 = new PdfPCell(
				PdfStyleNew.createLabelCellWithBorderSmallFontNoBoldForHeader(challan.getLoc_emailid()));
			hcella_4_11.setHorizontalAlignment(Element.ALIGN_LEFT);
			hcella_4_11.setVerticalAlignment(Element.ALIGN_TOP);
			hcella_4_11.setBorder(0);

			PdfPCell hcella_12 = new PdfPCell(PdfStyleNew.createLabelCellWithoutBorderSmallFontNoPad("State Name:"));
			hcella_12.setHorizontalAlignment(Element.ALIGN_LEFT);
			hcella_12.setVerticalAlignment(Element.ALIGN_TOP);
			hcella_12.setBorder(Rectangle.LEFT);

			PdfPCell hcella_4_12 = new PdfPCell(
				PdfStyleNew.createLabelCellWithBorderSmallFontNoBoldForHeader(challan.getLoc_state()));
			hcella_4_12.setHorizontalAlignment(Element.ALIGN_LEFT);
			hcella_4_12.setVerticalAlignment(Element.ALIGN_TOP);
			hcella_4_12.setBorder(0);

			PdfPCell hcella_13 = new PdfPCell(PdfStyleNew.createLabelCellWithoutBorderSmallFontNoPad("State Code"));
			hcella_13.setHorizontalAlignment(Element.ALIGN_LEFT);
			hcella_13.setVerticalAlignment(Element.ALIGN_TOP);
			hcella_13.setBorder(Rectangle.LEFT);

			PdfPCell hcella_4_13 = new PdfPCell(
				PdfStyleNew.createLabelCellWithBorderSmallFontNoBoldForHeader(String.valueOf(challan.getLoc_code())));
			hcella_4_13.setHorizontalAlignment(Element.ALIGN_LEFT);
			hcella_4_13.setVerticalAlignment(Element.ALIGN_TOP);
			hcella_4_13.setBorder(0);
			
			
			
			PdfPCell hcella_15 = new PdfPCell(PdfStyleNew.createLabelCellWithoutBorderSmallFontNoPad("eWay Bill No."));
			hcella_15.setHorizontalAlignment(Element.ALIGN_LEFT);
			hcella_15.setVerticalAlignment(Element.ALIGN_TOP);
			hcella_15.setBorder(Rectangle.LEFT);

			PdfPCell hcella_4_15 = new PdfPCell(
				PdfStyleNew.createLabelCellWithBorderSmallFontNoBoldForHeader(challan.getTrn_ewaybill()));
			hcella_4_15.setHorizontalAlignment(Element.ALIGN_LEFT);
			hcella_4_15.setVerticalAlignment(Element.ALIGN_TOP);
			hcella_4_15.setBorder(0);
			
			PdfPCell hcella_16 = new PdfPCell(PdfStyleNew.createLabelCellWithoutBorderSmallFontNoPad("IRN No."));
			hcella_16.setHorizontalAlignment(Element.ALIGN_LEFT);
			hcella_16.setVerticalAlignment(Element.ALIGN_TOP);
			hcella_16.setBorder(Rectangle.LEFT);

			PdfPCell hcella_4_16 = new PdfPCell(
				PdfStyleNew.createLabelCellWithBorderSmallFontNoBoldForHeader(challan.getTrn_irn_number()));
			hcella_4_16.setHorizontalAlignment(Element.ALIGN_LEFT);
			hcella_4_16.setVerticalAlignment(Element.ALIGN_TOP);
			hcella_4_16.setBorder(0);

			PdfPCell hcella_14 = new PdfPCell(PdfStyleNew.createLabelCellWithoutBorderSmallFontNoPad("GST Doc Type: "));
			hcella_14.setHorizontalAlignment(Element.ALIGN_LEFT);
			hcella_14.setVerticalAlignment(Element.ALIGN_TOP);
			hcella_14.setBorder(0);

			PdfPCell hcella_4_14 = new PdfPCell(
				PdfStyleNew.createLabelCellWithBorderSmallFontNoBoldForHeader(challan.getGst_doc_type()));
			hcella_4_14.setHorizontalAlignment(Element.ALIGN_LEFT);
			hcella_4_14.setVerticalAlignment(Element.ALIGN_TOP);
			hcella_4_14.setBorder(0);

			PdfPCell cell11 = new PdfPCell(addressTbl_a);
			cell11.setBorder(0);

			

			
			PdfPTable addressTbl = new PdfPTable(1);
			PdfPTable addressTbl_b = new PdfPTable(2);
			addressTbl_b.setWidths(new float[] { 3f, 9f});
			addressTbl_b.addCell(hcella_4);
			addressTbl_b.addCell(hcella_4_1);
//			addressTbl_b.addCell(hcella_7);
//			addressTbl_b.addCell(hcella_4_2);

			addressTbl_b.addCell(hcella_5);
			addressTbl_b.addCell(hcella_4_3);
			/*
			 * addressTbl_b.addCell(hcella_6); addressTbl_b.addCell(hcella_4_4);
			 */

			addressTbl_b.addCell(hcella_8);
			addressTbl_b.addCell(hcella_4_8);
			addressTbl_b.addCell(hcella_9);
			addressTbl_b.addCell(hcella_4_9);
			addressTbl_b.addCell(hcella_10);
			addressTbl_b.addCell(hcella_4_10);
			addressTbl_b.addCell(hcella_11);
			addressTbl_b.addCell(hcella_4_11);
			addressTbl_b.addCell(hcella_12);
			addressTbl_b.addCell(hcella_4_12);
			addressTbl_b.addCell(hcella_13);
			addressTbl_b.addCell(hcella_4_13);
			addressTbl_b.addCell(hcella_15);
			addressTbl_b.addCell(hcella_4_15);
			addressTbl_b.addCell(hcella_16);
			addressTbl_b.addCell(hcella_4_16);
			/*
			 * addressTbl_b.addCell(hcella_14); addressTbl_b.addCell(hcella_4_14);
			 */
			PdfPCell cell12 = new PdfPCell(addressTbl_b);
			cell12.setBorder(0);
			addressTbl.addCell(cell11);
			addressTbl.addCell(cell12);
			PdfPCell cell1 = new PdfPCell(addressTbl);
			cell1.setBorder(0);
			cell1.setColspan(1);

			headertable2.addCell(cell1);
	
			  PdfPCell hcell8 = null;
		  if(comp_cd.trim().equalsIgnoreCase("PFZ")) { 
			  int i =1 ;
			  String logoImag ;
			  if(i==1) {
			   logoImag =path+"\\pfizer_blk_pos.jpg";
			  }
			  else {
				  logoImag = path+"\\excelLogo_blk.png";
			  }
		  System.out.println("logoImag : " + logoImag);
		  Image img=null;
		  try { 
			  img =Image.getInstance(logoImag); img.setAlignment(Image.ALIGN_CENTER); 
			  } 
		  catch(Exception e) 
		  { 
				  System.out.println("Error Occurred :" +e.getMessage());// uncomment asneeded --; 
		  } 
		  img.setAlignment(1); 
			if (challan.getDoc_no().contains("PIP")) {
				 hcell8 =new PdfPCell(new Phrase(""));
			}
			else {
				   hcell8 =new PdfPCell(img, true);
			
			}
		  hcell8.setFixedHeight(100f);
		  //imagecell.setNoWrap(false); hcell8.setBorder(0);
		  hcell8.setHorizontalAlignment(Element.ALIGN_LEFT);
		  hcell8.setVerticalAlignment(Element.ALIGN_CENTER);
		  hcell8.setBorder(Rectangle.NO_BORDER|Rectangle.RIGHT);
		  }
		  else {
//			  if(comp_cd.trim().equals(VET)) {
//				  Font fontinv = new Font(FontFamily.HELVETICA, 14, Font.BOLD,
//							BaseColor.BLACK);
//				  
//				  hcell8 = new PdfPCell(new Phrase("TAX INVOICE", fontinv));
//				  hcell8.setBorder(Rectangle.NO_BORDER|Rectangle.RIGHT);
//				  hcell8.setHorizontalAlignment(Element.ALIGN_LEFT);
//			  }
//			  else {
				  hcell8 = new PdfPCell(new Paragraph("  "));
				  hcell8.setBorder(Rectangle.NO_BORDER|Rectangle.RIGHT);	  
//			  }
		  }
		  
		  String qrCodeImagePath = MedicoConstants.QR_CODE_IMAGE_PATH;
			String filename = "E-Invoice" + new Date().getTime() + ".png";
			MedicoResources.generateImage(challan.getTrn_qr_code(), qrCodeImagePath, filename);
			String imFile = qrCodeImagePath + filename;
			Image image = Image.getInstance(imFile);
			
			PdfPCell imgCell = new PdfPCell(image, true);
//			imgCell.setRowspan(10);
			imgCell.setBorder(0);
			imgCell.setFixedHeight(90f);
			imgCell.setHorizontalAlignment(Element.ALIGN_CENTER);
			imgCell.setPaddingTop(20f);
		  
		  
		  PdfPTable addressTbl1 = new PdfPTable(1);
		  addressTbl1.addCell(imgCell);
		  PdfPCell cell2 = new PdfPCell(addressTbl1); 
		  cell2.setBorder(0);
		  headertable2.addCell(cell2);
			// ======================================================================================================================
			// 2.2

			/*
			 * PdfPCell hcell8=new
			 * PdfPCell(PdfStyleNew.createLabelCellWithBorderSmallFontNoBold(" "));
			 * hcell8.setHorizontalAlignment(Element.ALIGN_LEFT);
			 * hcell8.setBorder(0);
			 * 
			 * PdfPCell hcellb_1=new
			 * PdfPCell(PdfStyleNew.createLabelCellWithBorderSmallFontNoBold(" "));
			 * hcellb_1.setHorizontalAlignment(Element.ALIGN_LEFT);
			 * hcellb_1.setBorder(0);
			 * 
			 * PdfPCell hcellb_2=new
			 * PdfPCell(PdfStyleNew.createLabelCellWithBorderSmallFontNoBold(" "));
			 * hcellb_2.setHorizontalAlignment(Element.ALIGN_LEFT);
			 * hcellb_2.setBorder(0);
			 * 
			 * 
			 * PdfPCell hcellb_3=new
			 * PdfPCell(PdfStyleNew.createLabelCellWithBorderSmallFontNoBold(" "));
			 * hcellb_3.setHorizontalAlignment(Element.ALIGN_LEFT);
			 * hcellb_3.setBorder(0);
			 * 
			 * 
			 * PdfPCell hcellb_4=new
			 * PdfPCell(PdfStyleNew.createLabelCellWithBorderSmallFontNoBold(challan
			 * .getLic1())); hcellb_4.setHorizontalAlignment(Element.ALIGN_LEFT);
			 * hcellb_4.setBorder(0);
			 * 
			 * 
			 * PdfPCell hcellb_5=new
			 * PdfPCell(PdfStyleNew.createLabelCellWithBorderSmallFontNoBold(challan
			 * .getLic2())); hcellb_5.setHorizontalAlignment(Element.ALIGN_LEFT);
			 * hcellb_5.setBorder(0);
			 * 
			 * PdfPCell hcellb_7=new
			 * PdfPCell(PdfStyleNew.createLabelCellWithBorderSmallFontNoBold(challan
			 * .getLoc_tin_no()));
			 * hcellb_7.setHorizontalAlignment(Element.ALIGN_LEFT);
			 * hcellb_7.setBorder(0);
			 * 
			 * PdfPCell hcellb_6=new
			 * PdfPCell(PdfStyleNew.createLabelCellWithBorderSmallFontNoBold(challan
			 * .getLoc_cst_no()));
			 * hcellb_6.setHorizontalAlignment(Element.ALIGN_LEFT);
			 * hcellb_6.setBorder(0);
			 * 
			 * PdfPTable addressTbl1 = new PdfPTable(1);
			 * addressTbl1.addCell(hcell8); addressTbl1.addCell(hcellb_1);
			 * addressTbl1.addCell(hcellb_2); addressTbl1.addCell(hcellb_3);
			 * addressTbl1.addCell(hcellb_4); addressTbl1.addCell(hcellb_5);
			 * addressTbl1.addCell(hcellb_7); addressTbl1.addCell(hcellb_6);
			 * 
			 * PdfPCell cell2 = new PdfPCell(addressTbl1); cell2.setBorder(0);
			 * 
			 * headertable2.addCell(cell2);
			 */

			// ======================================================================================================================

			// ======================================================================================================================
			// 2.3
		
			
			PdfPCell hcell16 = new PdfPCell(PdfStyleNew.createLabelCellWithBorderSmallFont("Date "));
			hcell16.setHorizontalAlignment(Element.ALIGN_LEFT);

			PdfPCell hcell20 = new PdfPCell(PdfStyleNew.createLabelCellWithBorderSmallFont("Invoice #"));
			hcell20.setHorizontalAlignment(Element.ALIGN_LEFT);

			PdfPCell hcell21 = new PdfPCell(PdfStyleNew.createLabelCellWithBorderSmallFont("Customer"));
			hcell21.setHorizontalAlignment(Element.ALIGN_LEFT);

			PdfPCell hcell22 = new PdfPCell(PdfStyleNew.createLabelCellWithBorderSmallFont("Division "));
			hcell22.setHorizontalAlignment(Element.ALIGN_LEFT);

			PdfPCell hcell23 = new PdfPCell(PdfStyleNew.createLabelCellWithBorderSmallFont("Team"));
			hcell23.setHorizontalAlignment(Element.ALIGN_LEFT);

			PdfPCell hcell24 = new PdfPCell(PdfStyleNew.createLabelCellWithBorderSmallFont("Remark"));
			hcell24.setHorizontalAlignment(Element.ALIGN_LEFT);

			PdfPCell hcell17 = new PdfPCell(PdfStyleNew.createLabelCellWithBorderSmallFontTwiceHeight("Email"));
			hcell17.setHorizontalAlignment(Element.ALIGN_LEFT);

			PdfPCell hcell18 = new PdfPCell(PdfStyleNew.createLabelCellWithBorderSmallFont("Telephone 1"));
			hcell18.setHorizontalAlignment(Element.ALIGN_LEFT);

			PdfPCell hcell19 = new PdfPCell(PdfStyleNew.createLabelCellWithBorderSmallFont("Telephone 2"));
			hcell19.setHorizontalAlignment(Element.ALIGN_LEFT);

			PdfPCell hcell19_from_to = new PdfPCell(PdfStyleNew.createLabelCellWithBorderSmallFont("Dtl. Chl. No: "));
			hcell19_from_to.setHorizontalAlignment(Element.ALIGN_LEFT);

			PdfPTable addressTbl2 = new PdfPTable(1);
			addressTbl2.addCell(hcell16);

			addressTbl2.addCell(hcell20);
			addressTbl2.addCell(hcell21);
			addressTbl2.addCell(hcell22);
			addressTbl2.addCell(hcell23);
			// addressTbl2.addCell(hcell24);
			addressTbl2.addCell(hcell17);
			addressTbl2.addCell(hcell18);
			addressTbl2.addCell(hcell19);
			addressTbl2.addCell(hcell19_from_to);
			PdfPCell cell3 = new PdfPCell(addressTbl2);
			cell3.setBorder(0);

			headertable2.addCell(cell3);

			// ======================================================================================================================

			// ======================================================================================================================
			// 2.4
			Font medFont = new Font(Font.FontFamily.HELVETICA, 12, Font.NORMAL, BaseColor.BLACK);

			PdfPCell hcell26 = new PdfPCell(PdfStyleNew
				.createLabelCellWithBorderSmallFontNoBold((challan.getDate() == null ? "" : challan.getDate())));
			hcell26.setHorizontalAlignment(Element.ALIGN_LEFT);

			PdfPCell hcell30 = new PdfPCell(PdfStyleNew
				.createLabelCellWithBorderSmallFontNoBold((challan.getDoc_no() == null ? "" : challan.getDoc_no())));
			hcell30.setHorizontalAlignment(Element.ALIGN_LEFT);

			PdfPCell hcell31 = new PdfPCell(
				PdfStyleNew.createLabelCellWithBorderSmallFontNoBold((challan.getStaff_name().length() > 20
					? challan.getStaff_name().substring(0, 20) : challan.getStaff_name())));
			hcell31.setHorizontalAlignment(Element.ALIGN_LEFT);

			PdfPCell hcell32 = new PdfPCell(
				PdfStyleNew.createLabelCellWithBorderSmallFontNoBold((challan.getDivision().length() > 28
					? challan.getDivision().substring(0, 28) : challan.getDivision())));
			hcell32.setHorizontalAlignment(Element.ALIGN_LEFT);

			PdfPCell hcell33 = new PdfPCell(PdfStyleNew.createLabelCellWithBorderSmallFontNoBold(
				((challan.getDiv_name() == null ? "" : challan.getDiv_name()).length() > 28
					? challan.getDiv_name().substring(0, 28)
					: challan.getDiv_name() == null ? "" : challan.getDiv_name())));
			hcell33.setHorizontalAlignment(Element.ALIGN_LEFT);

			PdfPCell hcell34 = new PdfPCell(PdfStyleNew.createLabelCellWithBorderSmallFontNoBold(
				challan.getDsp_challan_msg() == null ? "" : challan.getDsp_challan_msg()));
			hcell34.setHorizontalAlignment(Element.ALIGN_LEFT);

			PdfPCell hcell27 = new PdfPCell(PdfStyleNew
				.createLabelCellWithBorderSmallFontNoBoldTwiceHeight((challan.getDspstaff_email().length() > 40
					? challan.getDspstaff_email().substring(0, 40) : challan.getDspstaff_email())));
			hcell27.setHorizontalAlignment(Element.ALIGN_LEFT);

			PdfPCell hcell28 = new PdfPCell(PdfStyleNew.createLabelCellWithBorderSmallFontNoBold(
				(challan.getDspstaff_mobile() == null ? "" : challan.getDspstaff_mobile())));
			hcell28.setHorizontalAlignment(Element.ALIGN_LEFT);

			PdfPCell hcell29 = new PdfPCell(PdfStyleNew.createLabelCellWithBorderSmallFontNoBold(
				(challan.getDspstaff_mobile() == null ? "" : challan.getDspstaff_mobile())));
			hcell29.setHorizontalAlignment(Element.ALIGN_LEFT);

			String s = challan.getTo_num().length() < 6 ? ""
				: challan.getTo_num().substring(challan.getTo_num().length() - 5);
			PdfPCell hcell29_from_to = new PdfPCell(
				PdfStyleNew.createLabelCellWithBorderSmallFontNoBold((challan.getFr_num().length() < 6 ? ""
					: challan.getFr_num().substring(challan.getFr_num().length() - 5) + " To " + s)));

			hcell29_from_to.setHorizontalAlignment(Element.ALIGN_LEFT);

			PdfPTable addressTbl3 = new PdfPTable(1);
			addressTbl3.addCell(hcell26);

			addressTbl3.addCell(hcell30);
			addressTbl3.addCell(hcell31);
			addressTbl3.addCell(hcell32);
			addressTbl3.addCell(hcell33);
			addressTbl3.addCell(hcell27);
			addressTbl3.addCell(hcell28);
			addressTbl3.addCell(hcell29);
			addressTbl3.addCell(hcell29_from_to);
			PdfPCell cell4 = new PdfPCell(addressTbl3);
			cell4.setBorder(0);

			headertable2.addCell(cell4);

			// =================================================Blank Cell Added To
			// Remove Table Border==============================

			PdfPCell hcell_blank2 = new PdfPCell(headertable2);
			hcell_blank2.setBorder(Rectangle.NO_BORDER);
			// hcell_blank2.enableBorderSide(Rectangle.RIGHT);
			// hcell_blank2.disableBorderSide(Rectangle.BOTTOM);

			// challan.getLoc_add1().length()>29 ?
			// challan.getLoc_add1().substring(0, 29) : challan.getLoc_add1()
			// =====================================================================================================================

			// =====================================================================================================================
			// 3.1

			PdfPCell hcell35 = new PdfPCell(PdfStyleNew.createLabelCellWithBorderSmallFont("Ship To :"));
			hcell35.setHorizontalAlignment(Element.ALIGN_LEFT);

			PdfPCell hcell36 = new PdfPCell(PdfStyleNew
				.createLabelCellWithoutBorderSmallFontBold(challan.getStaff_name() == null ? "" : challan.getStaff_name()));
			hcell36.setHorizontalAlignment(Element.ALIGN_LEFT);
			hcell36.enableBorderSide(Rectangle.LEFT);
			hcell36.enableBorderSide(Rectangle.RIGHT);
			StringBuilder result2 = new StringBuilder();
			result2.append(challan.getStaff_addr1().length() > 50 ? challan.getStaff_addr1().substring(0, 50).trim()
				: challan.getStaff_addr1().trim());
			result2.append(System.lineSeparator());
			result2.append(challan.getStaff_addr2().length() > 50 ? challan.getStaff_addr2().substring(0, 50).trim()
				: challan.getStaff_addr2().trim());
			result2.append(System.lineSeparator());
			result2.append(challan.getStaff_addr3().length() > 50 ? challan.getStaff_addr3().substring(0, 50).trim()
				: challan.getStaff_addr3().trim());
			result2.append(System.lineSeparator());
			result2.append(challan.getStaff_addr4().length() > 50 ? challan.getStaff_addr4().substring(0, 50).trim()
				: challan.getStaff_addr4().trim());
			result2.append(System.lineSeparator());
			result2.append("GST Reg No: " + challan.getCfgst_reg_no().trim());
			result2.append(System.lineSeparator());
			result2.append("CIN NO: " + challan.getCf_cinno() + "                                                ");		
			result2.append("Website: " + challan.getCf_web_site());
			result2.append(System.lineSeparator());
			result2.append("State Name: " + challan.getState() + "                           ");
	//		result2.append(System.lineSeparator());
			result2.append("State Code : " + challan.getCfstate_code() + "");

			PdfPCell hcell37 = new PdfPCell(PdfStyleNew
				.createLabelCellWithoutBorderSmallFontNoBold(result2.toString() == null ? "" : result2.toString()));
			hcell37.setHorizontalAlignment(Element.ALIGN_LEFT);
			hcell37.enableBorderSide(Rectangle.LEFT);
			hcell37.enableBorderSide(Rectangle.RIGHT);

			PdfPTable custTbl = new PdfPTable(1);
			custTbl.addCell(hcell35);
			custTbl.addCell(hcell36);
			custTbl.addCell(hcell37);

			PdfPCell cell5 = new PdfPCell(custTbl);
			cell5.setBorder(0);

			headertable3.addCell(cell5);

			// ======================================================================================================================

			// ======================================================================================================================
			// 3.2
			Company company = ((Company) session.getServletContext().getAttribute(COMPANY_DATA));
			
			String address1 = company.getAddress1();
			String address2 = company.getAddress2();
			String address3 = company.getAddress3();
			String address4 = company.getAddress4();
			// WEB_SITE cinno
			String web_site = company.getWebsite() == null ? " "
				: company.getWebsite();
			String cinno = company.getCinno() == null ? " " : company.getCinno();
			// System.out.println("web_site " +web_site+ "cinno "+cinno );

			StringBuilder result3 = new StringBuilder();

			// Company name added 27-1-2017
			if (company_name.trim().isEmpty()) {
			    result3.append("");
			    result3.append(System.lineSeparator());
			} else {
				if (challan.getDoc_no().contains("PIP")) {
					 if (comp_cd.trim().equalsIgnoreCase("PFZ")) {
						 result3.append(comp_name_new.length() > 40 ? comp_name_new.substring(0, 40) : comp_name_new);
						    result3.append(System.lineSeparator());	
					 }
					 else {
						  result3.append(company_name.length() > 30 ? company_name.substring(0, 30) : company_name);
						    result3.append(System.lineSeparator());	
					 }
				}
				else {
					  result3.append(company_name.length() > 30 ? company_name.substring(0, 30) : company_name);
					    result3.append(System.lineSeparator());				}
			  
			}

			result3.append(address1.length() > 33 ? address1.substring(0, 33) : address1);
			result3.append(System.lineSeparator());
			result3.append(address2.length() > 33 ? address2.substring(0, 33) : address2);
			result3.append(System.lineSeparator());
			result3.append(address3.length() > 33 ? address3.substring(0, 33) : address3);
			result3.append(System.lineSeparator());
			result3.append(address4.length() > 33 ? address4.substring(0, 33) : address4);
		//	result3.append(System.lineSeparator());
		//	result3.append("State Name: " + challan.getState() + "");
		//	result3.append(System.lineSeparator());
		//	result3.append("State Code : " + challan.getCfstate_code() + "");
		//	result3.append(System.lineSeparator());
			// System.out.println("web_site length : " +web_site.length());
			/*
			 * if(web_site.trim().isEmpty()){ result3.append("");
			 * result3.append(System.lineSeparator()); }else{
			 * result3.append(web_site.length()>33 ?
			 * "Website:"+web_site.substring(0, 33) : "Website:"+web_site);
			 * result3.append(System.lineSeparator());
			 * 
			 * } if(cinno.trim().isEmpty()){ result3.append("");
			 * result3.append(System.lineSeparator()); }else{
			 * result3.append(cinno.length()>33 ? "CIN NO:"+cinno.substring(0, 33) :
			 * "CIN NO:"+challan.getCf_cinno());
			 * result3.append(System.lineSeparator()); }
			 */
			result3.append(System.lineSeparator());
			result3.append("Website : " + web_site);
			result3.append(System.lineSeparator());
			result3.append("Cin No : " + cinno);
		//	result3.append(System.lineSeparator());
			PdfPCell hcell38 = new PdfPCell(PdfStyleNew.createLabelCellWithBorderSmallFont("Principal place of buisness"));
			hcell38.setHorizontalAlignment(Element.ALIGN_LEFT);

			PdfPCell hcell39 = new PdfPCell(PdfStyleNew.createLabelCellWithoutBorderSmallFontNoBold2(
				result3.length() > 155 ? result3.substring(0, 155) : result3.toString()));
			hcell39.setHorizontalAlignment(Element.ALIGN_LEFT);

			hcell39.enableBorderSide(Rectangle.RIGHT);

			PdfPTable custTbl1 = new PdfPTable(1);
			custTbl1.addCell(hcell38);
			custTbl1.addCell(hcell39);

			PdfPCell cell6 = new PdfPCell(custTbl1);
			cell6.setBorder(0);

			headertable3.addCell(cell6);

			// ======================================================================================================================

			// =====================================================================================================================
			// 3.3

			PdfPCell hcell43 = new PdfPCell(PdfStyleNew.createLabelCellWithBorderSmallFont("Note"));
			hcell43.setHorizontalAlignment(Element.ALIGN_CENTER);
			StringBuilder result4 = new StringBuilder();

			result4.append("Goods Not for sale.");
			result4.append(System.lineSeparator());
			result4.append("Free samples only.");
			result4.append(System.lineSeparator());
			PdfPCell hcell44 = new PdfPCell(PdfStyleNew.createLabelCellWithoutBorderSmallFontBold(result4.toString()));
			hcell44.setHorizontalAlignment(Element.ALIGN_CENTER);
			hcell44.setVerticalAlignment(Element.ALIGN_TOP);
			hcell44.enableBorderSide(Rectangle.RIGHT);

			PdfPTable custTbl2 = new PdfPTable(1);
			custTbl2.addCell(hcell43);
			custTbl2.addCell(hcell44);

			PdfPCell cell7 = new PdfPCell(custTbl2);
			cell7.setBorder(0);

			headertable3.addCell(cell7);

			// ======================================================================================================================

			// =====================================================================================================================
			// 3.4
			PdfPCell hcell45 = null;
			if (show_amount.equals("Y")) {
			    hcell45 = new PdfPCell(PdfStyleNew.createLabelCellWithBorderSmallFont("Insurance Policy No."));
			} else {
			    hcell45 = new PdfPCell(PdfStyleNew.createLabelCellWithBorderSmallFont(""));
			}
			hcell45.setHorizontalAlignment(Element.ALIGN_LEFT);

			PdfPCell hcell46 = new PdfPCell(PdfStyleNew.createLabelCellWithoutBorderSmallFontNoBold(" "));
			hcell46.setHorizontalAlignment(Element.ALIGN_LEFT);
			hcell46.enableBorderSide(Rectangle.RIGHT);
			PdfPCell hcell47 = null;
			if (show_amount.equals("Y")) {
			    hcell47 = new PdfPCell(PdfStyleNew.createLabelCellWithBorderSmallFont("Food Licence Nos."));
			} else {
			    hcell47 = new PdfPCell(PdfStyleNew.createLabelCellWithBorderSmallFont(""));
			}
			hcell47.setHorizontalAlignment(Element.ALIGN_LEFT);

			PdfPCell hcell48 = new PdfPCell(PdfStyleNew.createLabelCellWithoutBorderSmallFontNoBold(" "));
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
			// cell8.setNoWrap(true);
			headertable3.addCell(cell8);

			// =================================================Blank Cell Added To
			// Remove Table Border3==============================

			PdfPCell hcell_blank3 = new PdfPCell(headertable3);
			hcell_blank3.setBorder(Rectangle.NO_BORDER);
			// hcell_blank.enableBorderSide(Rectangle.RIGHT);
			// hcell_blank.disableBorderSide(Rectangle.BOTTOM);

			bodytable = new PdfPTable(6);

			bodytable.setWidthPercentage(100);
			bodytable.setWidths(bodyTableWidth);
			bodytable.addCell(PdfStyleNew.createLabelWithBGColour("Item"));
			bodytable.addCell(PdfStyleNew.createLabelWithBGColour("Batch/Art Work No"));
			bodytable.addCell(PdfStyleNew.createLabelWithBGColour("Expiry Date"));
			bodytable.addCell(PdfStyleNew.createLabelWithBGColour("Quantity"));
			bodytable.addCell(PdfStyleNew.createLabelWithBGColour("Rate"));
			if (show_amount.equals("Y")) {
			    bodytable.addCell(PdfStyleNew.createLabelWithBGColour("Amount"));
			} else {
			    bodytable.addCell(PdfStyleNew.createLabelWithBGColour(""));
			}
			// =================================================Blank Cell Added To
			// Remove Table Border4==============================

			PdfPCell hcell_blank4 = new PdfPCell(bodytable);
			hcell_blank4.setBorder(Rectangle.NO_BORDER);
			// hcell_blank2.enableBorderSide(Rectangle.RIGHT);
			// hcell_blank2.disableBorderSide(Rectangle.BOTTOM);
			// hcell_blank3
			// headertable.addCell(headertable01);
			headertable.addCell(hcell_blank);
			// headertable.addCell(headertable2);
			headertable.addCell(hcell_blank2);
			headertable.addCell(hcell_blank3);
			// headertable.addCell(headertable3);
			// headertable.addCell(headertable4);
			// headertable.addCell(bodytable);
			headertable.addCell(hcell_blank4);

			return headertable;
		    }

		    public PdfPTable newPage(ColumnText column, PdfPTable bodytable, FooterWithoutPageNo event,
			    HashMap<String, String> printLabel, boolean isFooterData, int rows, boolean pageflag, int pagecount,
			    BigDecimal total, int division, Integer loc, String frm_challan, String to_challan, String dispatchType,
			    String prodtype, String path, List<ViewPrePrintedSummaryChallan_GST_EInvoice> challan, String show_amount,
			    boolean footer_ind, String dsp_transporter, String ship_doc_no, String date, String cases, String weigh,
			    String roundoff, String dsp_challan_msg, BigDecimal igstbill, BigDecimal sgst, BigDecimal cgstbill,
			    BigDecimal value, BigDecimal igstheader, BigDecimal cgstheader, BigDecimal sgstheader,
			    BigDecimal netTotalFinal,String Companyname,String Companycode) throws DocumentException {

			PdfPCell cell = null;
			PdfPTable temp = new PdfPTable(6);
			try {
			    System.out.println("ship_doc_no " + ship_doc_no);
			    if (limit - (rows) > 0) {
				// add extra empty rows in body table
				cell = new PdfPCell();
				System.out.println("Insidedffff");
				temp.setWidths(bodyTableWidth);

				temp.addCell(PdfStyleNew.createValueCellWithHeight3((" "), false, true, 13f, true, false, false, null,
					false));
				temp.addCell(PdfStyleNew.createValueCellWithHeight3((""), false, true, 13f, true, false, false, null,
					false));
				temp.addCell(PdfStyleNew.createValueCellWithHeight3((""), false, true, 13f, true, false, false, null,
					false));
				temp.addCell(
					PdfStyleNew.createValueCellWithHeight3((""), true, true, 13f, true, false, false, null, false));
				temp.addCell(
					PdfStyleNew.createValueCellWithHeight3((""), true, true, 13f, true, false, false, null, false));
				temp.addCell(PdfStyleNew.createValueCellWithHeight3((""), true, true, 13f, false, false, false, null,
					false));

				temp.addCell(PdfStyleNew.createValueCellWithHeight3((" "), false, true, 13f, true, false, false, null,
					false));
				temp.addCell(PdfStyleNew.createValueCellWithHeight3((""), false, true, 13f, true, false, false, null,
					false));
				temp.addCell(PdfStyleNew.createValueCellWithHeight3((""), false, true, 13f, true, false, false, null,
					false));
				temp.addCell(
					PdfStyleNew.createValueCellWithHeight3((""), true, true, 13f, true, false, false, null, false));
				temp.addCell(
					PdfStyleNew.createValueCellWithHeight3((""), true, true, 13f, true, false, false, null, false));
				temp.addCell(PdfStyleNew.createValueCellWithHeight3((""), true, true, 13f, false, false, false, null,
					false));
				cell = new PdfPCell(temp);
				cell.setBorder(0);
				cell.setColspan(6);

				for (int i = 0; i < (limit - (rows)); i++) {
				    bodytable.addCell(cell);
				}
			    }
			    /*
			     * cell=PdfStyleNew.formatCell("",false,false,rows,igstbill);
			     * bodytable.addCell(cell); bodytable.addCell(cell);
			     * bodytable.addCell(cell); bodytable.addCell(cell);
			     * bodytable.addCell(cell);
			     * cell=PdfStyleNew.formatCell("",false,false,rows,igstbill);
			     * bodytable.addCell(cell);
			     */

			    float height = 0f;
			    if (longDesc) {
				height = 13f;
			    } else {
				height = 18f;
			    }
			    bodytable.addCell(PdfStyleNew.createValueCellWithHeight3((" "), false, true, height, true, false, false,
				    null, false));
			    bodytable.addCell(
				    PdfStyleNew.createValueCellWithHeight3((""), false, true, height, true, false, false, null, false));
			    bodytable.addCell(
				    PdfStyleNew.createValueCellWithHeight3((""), false, true, height, true, false, false, null, false));
			    bodytable.addCell(
				    PdfStyleNew.createValueCellWithHeight3((""), true, true, height, true, false, false, null, false));
			    bodytable.addCell(
				    PdfStyleNew.createValueCellWithHeight3((""), true, true, height, true, false, false, null, false));
			    bodytable.addCell(
				    PdfStyleNew.createValueCellWithHeight3((""), true, true, height, false, false, false, null, false));

			    column.addElement(bodytable);
			    column.setSimpleColumn(20, 0, 580, 557);
			    column.go();

			    bodytable = new PdfPTable(6);
			    bodytable.setWidthPercentage(100);
			    bodytable.setWidths(bodyTableWidth);

			    event.setFooter(createFooter(printLabel, isFooterData, pageflag, pagecount, total, division, loc,
				    frm_challan, to_challan, dispatchType, prodtype, path, challan, show_amount, footer_ind,
				    dsp_transporter, ship_doc_no, date, cases, weigh, roundoff, dsp_challan_msg, igstbill, sgst,
				    cgstbill, value, igstheader, cgstheader, sgstheader, netTotalFinal,Companyname,Companycode));

			} catch (DocumentException e) {
			    throw e;
			}
			return bodytable;
		    }

		    private PdfPTable createFooter(HashMap<String, String> printLabel, boolean isFooterData, boolean pageflag,
			    int pagecount, BigDecimal total, int division, Integer loc, String frm_challan, String to_challan,
			    String dispatchType, String prodtype, String path, List<ViewPrePrintedSummaryChallan_GST_EInvoice> challan,
			    String show_amount, boolean footer_ind, String dsp_transporter, String ship_doc_no, String date,
			    String cases, String weigh, String roundoff, String dsp_challan_msg, BigDecimal i, BigDecimal s,
			    BigDecimal c, BigDecimal valueTotal, BigDecimal igstheader, BigDecimal cgstheader, BigDecimal sgstheader,
			    BigDecimal netTotalFinal,String Companyname,String Companycode) throws DocumentException {
			PdfPTable maintable = new PdfPTable(3);

			System.out.println("challan size() " + challan.size());
			try {
			    // for (ViewPrePrintedDetailChallan_GST obj : challan) {
			    PdfPCell cell = null;

			    maintable.setWidthPercentage(100);

			    cell = new PdfPCell();

			    maintable.setWidths(new float[] { 6f, 2f, 2f });

			
			  String narrration =
			  this.printservice.getLocationNarrationByLocId(Long.parseLong(loc.toString()))
			  ;
			 
			  //  String narrration = "";

			    PdfPCell hcell1 = null;
			    hcell1 = new PdfPCell();
			    PdfPTable tt = new PdfPTable(1);
			    tt.setWidthPercentage(100);
			    PdfPCell dm = new PdfPCell(PdfStyleNew.createLabelCellWithBorderSmallFontNoBold2(
				    narrration.length() > 230 ? narrration.substring(0, 230) : narrration));
			    dm.setBorder(Rectangle.NO_BORDER);
			    tt.addCell(dm);

			    PdfPCell dd = new PdfPCell();
			    PdfPTable table = null;
			    PdfPCell c2 = null;
			    table = new PdfPTable(4);
			    table.setWidthPercentage(100);
			    c2 = PdfStyleNew.formatCell("Rupees In Words:", true, true, true, false, true, false, "", "left");
			    table.addCell(c2);
			    c2 = PdfStyleNew.formatCell(
				    new NumberToWordsBritish().convertNumberToWords(netTotalFinal.longValue()) + " Only/-", false, true,
				    false, false, true, false, "", "left");
			    c2.setColspan(3);
			    table.addCell(c2);
			    dd.addElement(table);
			    hcell1.addElement(tt);
			    hcell1.addElement(table);

			    // hcell1.setMinimumHeight(36f);
			    /*
			     * PdfPCell hcell2=null; if(show_amount.equals("Y")){ hcell2=new
			     * PdfPCell(PdfStyleNew.
			     * createLabelCellWithBorderSmallFont2("Goods Value")); }else{
			     * hcell2=new PdfPCell(PdfStyleNew.
			     * createLabelCellWithBorderSmallFont2("Goods Value")); }
			     * hcell2.setHorizontalAlignment(Element.ALIGN_LEFT);
			     * hcell2.setMinimumHeight(45f);
			     */
			    BigDecimal totalValue = new BigDecimal(0);
			    BigDecimal netTotal = new BigDecimal(0);
			    PdfPCell hcell2 = new PdfPCell();
			    PdfPTable demoTable = new PdfPTable(2);
			    PdfPCell demoCell = null;
			    demoTable.setWidthPercentage(100);
			    if (show_amount.equals("Y")) {
				demoCell = new PdfPCell(PdfStyleNew.createLabelCellWithBorderSmallFont2("Goods Value:", "left"));
			    } else {
				demoCell = new PdfPCell(PdfStyleNew.createLabelCellWithBorderSmallFont2("Goods Value:", "left"));
			    }
			    demoCell.setColspan(2);
			    demoTable.addCell(demoCell);
			    if (!(i.compareTo(BigDecimal.ZERO) > 0)) {
				demoCell = new PdfPCell(PdfStyleNew.createLabelCellWithBorderSmallFont2("CGST :", "left"));
				demoCell.setColspan(2);
				demoTable.addCell(demoCell);
				demoCell = new PdfPCell(PdfStyleNew.createLabelCellWithBorderSmallFont2("SGST :", "left"));
				demoCell.setColspan(2);
				demoTable.addCell(demoCell);
				totalValue = total.add(cgstheader.add(sgstheader));
				demoCell = new PdfPCell(PdfStyleNew.createLabelCellWithBorderSmallFont2("Total :", "left"));
				demoCell.setColspan(2);
				demoTable.addCell(demoCell);
			    } else {
				demoCell = new PdfPCell(PdfStyleNew.createLabelCellWithBorderSmallFont2("IGST :", "left"));
				demoCell.setColspan(2);
				demoTable.addCell(demoCell);
				totalValue = total.add(igstheader);
				demoCell = new PdfPCell(PdfStyleNew.createLabelCellWithBorderSmallFont2("Total :", "left"));
				demoCell.setColspan(2);
				demoTable.addCell(demoCell);
			    }
			    demoCell = new PdfPCell(PdfStyleNew.createLabelCellWithBorderSmallFont2("Rounf Off :", "left"));
			    demoCell.setColspan(2);
			    demoTable.addCell(demoCell);
			    demoCell = new PdfPCell(PdfStyleNew.createLabelCellWithBorderSmallFont2("Net Total :", "left"));
			    demoCell.setColspan(2);
			    demoTable.addCell(demoCell);
			    netTotal = totalValue.add(new BigDecimal(roundoff));

			    /*
			     * demoCell=new PdfPCell(PdfStyleNew.
			     * createLabelCellWithBorderSmallFont2("NetTotal : "+netTotal.
			     * setScale(2,RoundingMode.HALF_UP))); demoTable.addCell(demoCell);
			     */

			    hcell2.addElement(demoTable);
			    maintable.addCell(hcell1);
			    maintable.addCell(hcell2);
			    hcell2.setHorizontalAlignment(Element.ALIGN_LEFT);
			    hcell2.setMinimumHeight(45f);
			    hcell2 = new PdfPCell();
			    demoTable = new PdfPTable(2);
			    demoCell = null;
			    demoTable.setWidthPercentage(100);

			    demoCell = new PdfPCell(PdfStyleNew.createLabelCellWithBorderSmallFont2("", "left"));
			    demoTable.addCell(demoCell);
			    demoCell = new PdfPCell(PdfStyleNew
				    .createLabelCellWithBorderSmallFont2(total.setScale(2, RoundingMode.HALF_UP).toString(), "right"));
			    demoTable.addCell(demoCell);
			    if (!(i.compareTo(BigDecimal.ZERO) > 0)) {
				demoCell = new PdfPCell(PdfStyleNew.createLabelCellWithBorderSmallFont2("", "left"));
				demoTable.addCell(demoCell);
				demoCell = new PdfPCell(
					PdfStyleNew.createLabelCellWithBorderSmallFont2(cgstheader.toString(), "right"));
				demoTable.addCell(demoCell);
				demoCell = new PdfPCell(PdfStyleNew.createLabelCellWithBorderSmallFont2("", "left"));
				demoTable.addCell(demoCell);
				demoCell = new PdfPCell(
					PdfStyleNew.createLabelCellWithBorderSmallFont2(sgstheader.toString(), "right"));
				demoTable.addCell(demoCell);
				demoCell = new PdfPCell(PdfStyleNew.createLabelCellWithBorderSmallFont2("", "left"));
				demoTable.addCell(demoCell);
				demoCell = new PdfPCell(PdfStyleNew.createLabelCellWithBorderSmallFont2(
					totalValue.setScale(2, RoundingMode.HALF_UP).toString(), "right"));
				demoTable.addCell(demoCell);
			    } else {
				demoCell = new PdfPCell(PdfStyleNew.createLabelCellWithBorderSmallFont2("", "left"));
				demoTable.addCell(demoCell);
				demoCell = new PdfPCell(
					PdfStyleNew.createLabelCellWithBorderSmallFont2(igstheader.toString(), "right"));
				demoTable.addCell(demoCell);
				demoCell = new PdfPCell(PdfStyleNew.createLabelCellWithBorderSmallFont2("", "left"));
				demoTable.addCell(demoCell);
				demoCell = new PdfPCell(PdfStyleNew.createLabelCellWithBorderSmallFont2(
					totalValue.setScale(2, RoundingMode.HALF_UP).toString(), "right"));
				demoTable.addCell(demoCell);
			    }
			    demoCell = new PdfPCell(PdfStyleNew.createLabelCellWithBorderSmallFont2("", "left"));
			    demoTable.addCell(demoCell);
			    demoCell = new PdfPCell(PdfStyleNew.createLabelCellWithBorderSmallFont2(roundoff, "right"));
			    demoTable.addCell(demoCell);

			    demoCell = new PdfPCell(PdfStyleNew.createLabelCellWithBorderSmallFont2("", "left"));
			    demoTable.addCell(demoCell);
			    demoCell = new PdfPCell(PdfStyleNew.createLabelCellWithBorderSmallFont2(
				    netTotal.setScale(2, BigDecimal.ROUND_HALF_UP).toString(), "right"));
			    demoTable.addCell(demoCell);
			    hcell2.addElement(demoTable);
			    hcell2.setHorizontalAlignment(Element.ALIGN_LEFT);
			    hcell2.setMinimumHeight(45f);

			    maintable.addCell(hcell2);
			    // maintable.addCell(hcell3);

			    /*
			     * PdfPCell parentCell= null; parentCell=new PdfPCell(); PdfPTable
			     * table=null; PdfPCell c2=null; table=new PdfPTable(4);
			     * table.setWidthPercentage(100);
			     * c2=PdfStyleNew.formatCell("Rupees In Words:", true, true, true,
			     * false, true, false, "", "left"); table.addCell(c2);
			     * c2=PdfStyleNew.formatCell(new
			     * NumberToWordsBritish().convertNumberToWords(Long.valueOf(total.
			     * longValue()))+" Only/-", false, true, false, false, true, false,
			     * "", "left"); c2.setColspan(3); table.addCell(c2);
			     * parentCell.addElement(table); maintable.addCell(parentCell);
			     * c2=PdfStyleNew.formatCell("", true, false, true, false, true,
			     * false, "", "left"); maintable.addCell(c2);
			     * c2=PdfStyleNew.formatCell("", true, false, false, false, false,
			     * false, "", "left"); maintable.addCell(c2);
			     */

			    // =====================================end of
			    // total====================================================

			    PdfPTable childTbl1 = new PdfPTable(4);
			    childTbl1.setWidths(new float[] { 2f, 2f, 2f, 2f });

			    // ------------------------------------------------------------------------------------------------------
			    PdfPCell hcell_t1 = new PdfPCell(PdfStyleNew.createLabelCellWithoutBorderSmallFont("Transporter Detail :"));
			    hcell_t1.setHorizontalAlignment(Element.ALIGN_LEFT);

			    PdfPCell hcell_t2 = new PdfPCell(PdfStyleNew.createLabelCellWithoutBorderSmallFontNoBold(""));
			    hcell_t2.setHorizontalAlignment(Element.ALIGN_LEFT);

			    PdfPCell hcell_t3 = new PdfPCell(PdfStyleNew.createLabelCellWithoutBorderSmallFont("Way Bill No :"));
			    hcell_t3.setHorizontalAlignment(Element.ALIGN_LEFT);

			    PdfPCell hcell_t_2 = new PdfPCell(PdfStyleNew.createLabelCellWithoutBorderSmallFontNoBold(""));
			    hcell_t_2.setHorizontalAlignment(Element.ALIGN_LEFT);

			    PdfPCell hcell_t4 = new PdfPCell(PdfStyleNew.createLabelCellWithoutBorderSmallFont("Name :"));
			    hcell_t4.setHorizontalAlignment(Element.ALIGN_LEFT);

			    PdfPCell hcell_t5 = new PdfPCell(PdfStyleNew.createLabelCellWithoutBorderSmallFontNoBold(dsp_transporter));
			    hcell_t5.setHorizontalAlignment(Element.ALIGN_LEFT);

			    PdfPCell hcell_t6 = new PdfPCell(PdfStyleNew.createLabelCellWithoutBorderSmallFont("No :"));
			    hcell_t6.setHorizontalAlignment(Element.ALIGN_LEFT);

			    PdfPCell hcell_t7 = new PdfPCell(PdfStyleNew.createLabelCellWithoutBorderSmallFontNoBold("  "));
			    hcell_t7.setHorizontalAlignment(Element.ALIGN_LEFT);

			    PdfPCell hcell_t8 = new PdfPCell(PdfStyleNew.createLabelCellWithoutBorderSmallFont("LR No.  "));
			    hcell_t8.setHorizontalAlignment(Element.ALIGN_LEFT);

			    PdfPCell hcell_t9 = new PdfPCell(PdfStyleNew.createLabelCellWithoutBorderSmallFontNoBold(ship_doc_no));
			    hcell_t9.setHorizontalAlignment(Element.ALIGN_LEFT);

			    PdfPCell hcell_t10 = new PdfPCell(PdfStyleNew.createLabelCellWithoutBorderSmallFont("Date : "));
			    hcell_t10.setHorizontalAlignment(Element.ALIGN_LEFT);

			    PdfPCell hcell_t11 = new PdfPCell(PdfStyleNew.createLabelCellWithoutBorderSmallFontNoBold("  "));
			    hcell_t11.setHorizontalAlignment(Element.ALIGN_LEFT);

			    PdfPCell hcell_t_10 = new PdfPCell(PdfStyleNew.createLabelCellWithoutBorderSmallFont("Date : "));
			    hcell_t_10.setHorizontalAlignment(Element.ALIGN_LEFT);

			    PdfPCell hcell_t_11 = new PdfPCell(PdfStyleNew.createLabelCellWithoutBorderSmallFontNoBold(date));
			    hcell_t_11.setHorizontalAlignment(Element.ALIGN_LEFT);

			    PdfPCell hcell_t12 = new PdfPCell(PdfStyleNew.createLabelCellWithoutBorderSmallFont("Number of Boxes :  "));
			    hcell_t12.setHorizontalAlignment(Element.ALIGN_LEFT);

			    PdfPCell hcell_t13 = new PdfPCell(PdfStyleNew.createLabelCellWithoutBorderSmallFontNoBold(cases));
			    hcell_t13.setHorizontalAlignment(Element.ALIGN_LEFT);

			    PdfPCell hcell_t14 = new PdfPCell(PdfStyleNew.createLabelCellWithoutBorderSmallFontNoBold("  "));
			    hcell_t14.setHorizontalAlignment(Element.ALIGN_LEFT);

			    PdfPCell hcell_t15 = new PdfPCell(PdfStyleNew.createLabelCellWithoutBorderSmallFontNoBold("  "));
			    hcell_t15.setHorizontalAlignment(Element.ALIGN_LEFT);
			    DecimalFormat format = new DecimalFormat("0.#");
			    PdfPCell hcell_t16 = new PdfPCell(
				    PdfStyleNew.createLabelCellWithoutBorderSmallFont("Weight of Consignment: "));

			    hcell_t16.setHorizontalAlignment(Element.ALIGN_LEFT);

			    PdfPCell hcell_t17 = new PdfPCell(PdfStyleNew
				    .createLabelCellWithoutBorderSmallFontNoBold(format.format(Double.parseDouble(weigh)).toString()));
			    hcell_t17.setHorizontalAlignment(Element.ALIGN_LEFT);
			    PdfPCell hcell_t18 = new PdfPCell(PdfStyleNew.createLabelCellWithoutBorderSmallFont("Remark:  "));
			    hcell_t18.setColspan(4);
			    hcell_t18.setHorizontalAlignment(Element.ALIGN_LEFT);

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
			    childTbl1.addCell(hcell_t18);

			    PdfPCell hcell4 = new PdfPCell(childTbl1);// left side of footer
			    // hcell4.setBorder(Rectangle.NO_BORDER);

			    PdfPTable childTbl2 = new PdfPTable(1);
			    // childTbl2.setWidths(new float[] { 10f });
			    String company_name = Companyname;
			    String comp_cd= Companycode;
			    PdfPCell hcell_childTbl2_t1 = null;
			    if (footer_ind) {
				if (frm_challan.contains("PIP")) {
				    if (comp_cd.trim().equalsIgnoreCase("PFZ")) {
					hcell_childTbl2_t1 = new PdfPCell(PdfStyle.createUltimateCellWithBorderWithDisableBorder(
						("For Pfizer Products India Private Limited."), Element.ALIGN_CENTER, Element.ALIGN_TOP,
						9, 14f, true, 1, 1, false, true, true, false, true));
				    } else {
					hcell_childTbl2_t1 = new PdfPCell(PdfStyle.createUltimateCellWithBorderWithDisableBorder(
						("For " + company_name), Element.ALIGN_CENTER, Element.ALIGN_TOP, 9, 14f, true, 1, 1,
						false, true, true, false, true));
				    }
				} else {
				    hcell_childTbl2_t1 = new PdfPCell(PdfStyle.createUltimateCellWithBorderWithDisableBorder(
					    ("For " + company_name), Element.ALIGN_CENTER, Element.ALIGN_TOP, 9, 14f, true, 1, 1, false,
					    true, true, false, true));
				}
			    } else {
				hcell_childTbl2_t1 = new PdfPCell(PdfStyleNew.createLabelCellWithoutBorderSmallFont(""));
			    }
			    hcell_childTbl2_t1.enableBorderSide(Rectangle.BOTTOM);
			    hcell_childTbl2_t1.enableBorderSide(Rectangle.LEFT);
			    hcell_childTbl2_t1.enableBorderSide(Rectangle.RIGHT);
			    hcell_childTbl2_t1.setHorizontalAlignment(Element.ALIGN_CENTER);

			    PdfPCell hcell_childTbl2_t2 = new PdfPCell(PdfStyleNew.createLabelCellWithoutBorderSmallFontNoBold(""));
			    hcell_childTbl2_t2.enableBorderSide(Rectangle.LEFT);
			    hcell_childTbl2_t2.enableBorderSide(Rectangle.RIGHT);
			    hcell_childTbl2_t2.setMinimumHeight(45f);

			    PdfPCell hcell_childTbl2_t3 = new PdfPCell(PdfStyleNew.createLabelCellWithoutBorderSmallFontNoBold(""));
			    hcell_childTbl2_t3.enableBorderSide(Rectangle.LEFT);
			    hcell_childTbl2_t3.enableBorderSide(Rectangle.RIGHT);

			    // ==============================================AUthorized
			    // Signatory
			    // TAble====================================================
			    PdfPTable childTbl3 = new PdfPTable(1);
			    childTbl3.setWidths(new float[] { 10f });
			    PdfPCell hcell_childTbl3_t2 = null;
			    if (footer_ind) {
				hcell_childTbl3_t2 = new PdfPCell(
					PdfStyleNew.createLabelCellWithoutBorderSmallFont("Authorized Signatory"));
			    } else {
				hcell_childTbl3_t2 = new PdfPCell(PdfStyleNew.createLabelCellWithoutBorderSmallFont(""));
			    }

			    hcell_childTbl3_t2.enableBorderSide(Rectangle.RIGHT);
			    hcell_childTbl3_t2.enableBorderSide(Rectangle.BOTTOM);
			    hcell_childTbl3_t2.enableBorderSide(Rectangle.TOP);
			    hcell_childTbl3_t2.enableBorderSide(Rectangle.LEFT);

			    PdfPCell hcell_childTbl3_t4 = new PdfPCell(PdfStyleNew.createLabelCellWithoutBorderSmallFontNoBold(""));
			    hcell_childTbl3_t4.enableBorderSide(Rectangle.RIGHT);
			    hcell_childTbl3_t4.enableBorderSide(Rectangle.BOTTOM);
			    hcell_childTbl3_t4.enableBorderSide(Rectangle.LEFT);
			    //childTbl3.addCell(hcell_childTbl2_t1);
			    childTbl3.addCell(hcell_childTbl3_t2);

			    childTbl3.addCell(hcell_childTbl3_t4);

			    PdfPCell hcell_childTbl2_t4 = new PdfPCell(childTbl3);
			    hcell_childTbl2_t4.setBorder(Rectangle.NO_BORDER);

			    childTbl2.addCell(hcell_childTbl2_t1);
			    childTbl2.addCell(hcell_childTbl2_t2);
			    childTbl2.addCell(hcell_childTbl2_t3);

			    childTbl2.addCell(hcell_childTbl2_t4);

			    PdfPCell hcell5 = new PdfPCell(childTbl2); // right side of footer
			    hcell5.setBorder(Rectangle.NO_BORDER);
			    hcell5.setColspan(2);
			    hcell5.setHorizontalAlignment(Element.ALIGN_CENTER);

			    maintable.addCell(hcell4);
			    maintable.addCell(hcell5);

			    // ---------------------------------------------------Remark
			    // :-------------------------------------------------------------

			    /*
			     * PdfPCell hcell1_remark0 = new PdfPCell(
			     * PdfStyleNew.createLabelCellWithoutBorderSmallFontBold("Remark :")
			     * );
			     * 
			     * hcell1_remark0.setHorizontalAlignment(Element.ALIGN_LEFT);
			     * 
			     * PdfPCell hcell1_remark = new PdfPCell(
			     * PdfStyleNew.createLabelCellWithoutBorderSmallFontNoBold(
			     * dsp_challan_msg));
			     * 
			     * hcell1_remark.setHorizontalAlignment(Element.ALIGN_LEFT);
			     * //hcell1_remark.setMinimumHeight(36f);
			     * 
			     * 
			     * PdfPTable childTbl4 = new PdfPTable(2); childTbl4.setWidths(new
			     * float[] { 1f,9f}); childTbl4.addCell(hcell1_remark0);
			     * childTbl4.addCell(hcell1_remark);
			     * 
			     * PdfPCell hcell_childTbl2_t5 = new PdfPCell(childTbl4);
			     * hcell_childTbl2_t5.setBorder(Rectangle.NO_BORDER);
			     * hcell_childTbl2_t5.setHorizontalAlignment(Element.ALIGN_LEFT);
			     * hcell_childTbl2_t5.setColspan(3);
			     * 
			     * maintable.addCell(hcell_childTbl2_t5);
			     */

			    // -----------------------------------------------------------------------------------------------------------------

			    if (pageflag) {
				System.out.println("pagecount : " + pagecount);
				cell = new PdfPCell(PdfStyleNew.createLabelCellWithoutBorderSmallFontNoBold("Page No. " + pagecount));
				cell.setBorder(Rectangle.NO_BORDER);
				cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
				cell.setVerticalAlignment(Element.ALIGN_BOTTOM);
				cell.setColspan(3);
				maintable.addCell(cell);

			    }
			    // }//end of for each list
			} catch (Exception e) {
			    System.out.println("Error Occurred :" +e.getMessage());// uncomment asneeded --;
			}
			return maintable;
		    }

		    public void setPath(String path) {
			this.path = path;
		    }

		    public String getPath() {
			return path;
		    }

		    public void setLogo(String logo) {
			this.logo = logo;
		    }

		    public String getLogo() {
			return logo;
		    }

		    public void setNarration(String narration) {
			this.narration = narration;
		    }

		    public String getNarration() {
			return narration;
		    }

		    public void setRemark(String remark) {
			this.remark = remark;
		    }

		    public String getRemark() {
			return remark;
		    }

		    public void setPrintLabel(HashMap<String, String> printLabel) {
			this.printLabel = printLabel;
		    }

		    public HashMap<String, String> getPrintLabel() {
			return printLabel;
		    }

		    public void setLimit(int limit) {
			this.limit = limit;
		    }

		    public int getLimit() {
			return limit;
		    }

		    public void setStockPntName(String stockPntName) {
			this.stockPntName = stockPntName;
		    }

		    public String getStockPntName() {
			return stockPntName;
		    }

		    public void setPageflag(Boolean pageflag) {
			this.pageflag = pageflag;
		    }

		    public Boolean getPageflag() {
			return pageflag;
		    }
}
