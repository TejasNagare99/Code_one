package com.excel.service;

import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.io.PrintStream;
import java.math.BigDecimal;
import java.math.RoundingMode;
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
import com.excel.model.ViewPrePrintedDetailChallan;
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
public class PackSlipPrintPDFServiceImpl  implements PackSlipPrintPDF{
	@Autowired
	private UserMasterRepository userMasterRepository;
	@Autowired private UserMasterService usermasterservice;

@Autowired private JwtProvider tokenProvider;
@Autowired private UserMasterService userMasterService;

    ViewPrePrintedDetailChallan footer;
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
    private int limit = 17;

    private float[] bodyTableWidth = new float[] { 5f, 1f, 2f, 2f };
	
	@Override
	public String getPdf(Integer division, Integer loc, String frm_challan, String to_challan, String dispatchType,
			String prodtype, String printtype, List<ViewPrePrintedDetailChallan> challans, String footer_signature_ind,String companyname,String companycode,HttpSession session,HttpServletRequest request)
			throws Exception {

		
		String path = MedicoConstants.PDF_FILE_PATH;
		setPath(path);
		System.out.println("path 11 : " + path);
		FileOutputStream os = null;
		PrintStream ps = null;
		long timevar = new Date().getTime();
		String filename = "";
		if (printtype.equals("tcfa")) {
		    filename = "pickSlip" + timevar + ".pdf";
		} else {
		    filename = "packingSlip" + timevar + ".pdf";
		}
		System.out.println("filename 11 " + filename);
		File file1 =  new File(path + "\\files\\");
		file1.mkdirs();
		File docfile = new File(path + "\\files\\" + filename);
		OutputStream file = new FileOutputStream(docfile);
		Document document = new Document(PageSize.A4);
		PdfWriter writer = PdfWriter.getInstance(document, file);
		Rectangle rect = new Rectangle(20, 28, 580, 840);
		writer.setBoxSize("header", rect);
		rect = new Rectangle(20, 2, 580, 62);
		writer.setBoxSize("footer", rect);
		String uname = this.tokenProvider.getUsernameFromRequest(request);String empId = this.userMasterService.getEmpIdByUsername(uname);
		 writer = usermasterservice.generatePDFlock(empId, writer);
		// int pagecount=writer.getPageNumber();
		int pagecount = 1;
		FooterWithoutPageNo event = new FooterWithoutPageNo();
		writer.setPageEvent(event);

		document.open();
		document.setMarginMirroringTopBottom(false);
		boolean isnew = false;
		boolean for_new_page = false;
		ColumnText column = new ColumnText(writer.getDirectContent());
		try {
		    BigDecimal total = new BigDecimal(0);

		    /*
		     * List<ViewPrePrintedDetailChallan> challans = null;
		     * 
		     * challans = new PrePrintedChallanDao()
		     * .getViewPrePrintedDetailChallan(division, loc, frm_challan,
		     * to_challan, dispatchType, prodtype);
		     */
		    System.out.println("getViewPrePrintedSummaryChallan");

		    Set<String> challanset = new LinkedHashSet<String>();
		    HashMap<String, Boolean> map = new HashMap<>();
		    for (ViewPrePrintedDetailChallan challan : challans) {
			System.out.println("challan.getDsp_id() " + challan.getDsp_id());
			challanset.add(challan.getDsp_id().toString());
			boolean isOldInvoice = false;
			challanset.add(challan.getDsp_id().toString());
			SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
			Date d1 = sdf.parse("01-07-2017");
			Date d2 = sdf.parse(challan.getDate());
			if (!(d2.compareTo(d1) >= 0)) {
			    isOldInvoice = true;
			}
			map.put(challan.getDsp_id().toString(), isOldInvoice);

		    }
		    PdfPTable bodytable = null;
		    boolean footer_ind = false;
		    for (String num : challanset) {
			List<ViewPrePrintedDetailChallan> list_new_page = new ArrayList<ViewPrePrintedDetailChallan>();
			isnew = true;
			for_new_page = true;
			PdfPTable maintable1 = new PdfPTable(1);
			maintable1.setWidthPercentage(100);
			PdfPCell cell = null;
			pageflag = false;
			// pagecount=1;

			PdfPTable hcell = null;
			int rows = 0;
			bodytable = new PdfPTable(4);
			bodytable.getDefaultCell().setPadding(0.0f);
			bodytable.setWidthPercentage(100);
			bodytable.setWidths(bodyTableWidth);
			total = BigDecimal.ZERO;
			for (ViewPrePrintedDetailChallan challan : challans) {

			    if (num.equalsIgnoreCase(challan.getDsp_id().toString())) {
				// ..Amey
				System.out.println("footer_signature_ind " + footer_signature_ind);
				if (rows >= limit) {
				    if (footer_signature_ind.equalsIgnoreCase("Y")) {
					footer_ind = false;// suppress footer signature
				    } else {
					footer_ind = true;// show footer signature
				    }
				    pageflag = true;
				    bodytable = newPage(column, bodytable, event, printLabel, false, rows, pageflag, pagecount,
					    total, division, loc, frm_challan, to_challan, dispatchType, prodtype, path,
					    challans, printtype, footer_ind);
				    document.newPage();
				    pagecount++;
				    rows = 0;
				}
				if (isnew) {
				    footer = challan;
				    maintable1.addCell(PdfStyle.createLabelCellWithoutBorderSmallFontNoBold(""));
				    boolean isOldInvoice = map.get(num);
				    hcell = createHeader(challan, pagecount, division, loc, frm_challan, to_challan,
					    dispatchType, prodtype, path, printtype, isOldInvoice,companyname,companycode);
				    cell = new PdfPCell(hcell);
				    cell.setBorder(0);
				    cell.setHorizontalAlignment(Element.ALIGN_CENTER);
				    maintable1.addCell(cell);
				    event.setHeader(maintable1);
				    isnew = false;
				    list_new_page.add(challan);
				}

				bodytable.addCell(PdfStyle.createValueCellWithHeight((challan.getProduct_desc().length() > 100
					? challan.getProduct_desc().substring(0, 100) : challan.getProduct_desc()), false, true,
					27f));

				bodytable.addCell(PdfStyle.createValueCellWithHeight((challan.getTotal_qty() == null
					? BigDecimal.ZERO.setScale(2, RoundingMode.HALF_UP).stripTrailingZeros()
					: challan.getTotal_qty().setScale(2, RoundingMode.HALF_UP).stripTrailingZeros())
						.toPlainString()
					+ " ", true, true, 18f));
				bodytable
					.addCell(PdfStyle.createValueCellWithHeight((challan.getBatch_no()), false, true, 18f));

				// bodytable.addCell(PdfStyle.createValueCell((challan.getDsp_challan_msg()==null?"":challan.getDsp_challan_msg()),
				// false,true));
				bodytable.addCell(PdfStyle.createValueCellWithHeight((" "), false, true, 18f));
				rows++;

			    }
			}
			footer_ind = true;
			newPage(column, bodytable, event, printLabel, true, rows, pageflag, pagecount, total, division, loc,
				frm_challan, to_challan, dispatchType, prodtype, path, list_new_page, printtype, footer_ind);

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

	 private PdfPTable createHeader(ViewPrePrintedDetailChallan challan, int pagecount, int division, Integer loc,
			    String frm_challan, String to_challan, String dispatchType, String prodtype, String path,
			    String printtype, boolean isOldInvoice,String companyname,String companycode) throws Exception {
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
			headertable2.setWidths(new float[] { 3.5f, 3.5f, 1f, 2f });
			headertable3.setWidths(new float[] { 5f, 5f });
			headertable4.setWidths(new float[] { 2f, 2f, 2.5f, 3.5f });

			Font font = new Font(FontFamily.HELVETICA, 12, Font.BOLD, BaseColor.BLACK);
			Font font_big = new Font(FontFamily.HELVETICA, 14, Font.BOLD, BaseColor.BLACK);
			PdfPTable headertable1 = new PdfPTable(1);
			headertable1.setWidthPercentage(100);
			headertable1.setWidths(new float[] { 5f });

			// ======================================================================================================================
			// 1
			PdfPCell hcell = null;
			if (printtype.equals("tcfa")) {
			    hcell = new PdfPCell(new Phrase("Pick Slip", font_big));
			} else {
			    hcell = new PdfPCell(new Phrase("Packing Slip", font_big));
			}
			hcell.setBorder(Rectangle.NO_BORDER);
			hcell.setHorizontalAlignment(Element.ALIGN_CENTER);

			PdfPCell hcell_1 = new PdfPCell(new Phrase(" ", font));
			hcell_1.setBorder(Rectangle.NO_BORDER);
			hcell_1.setHorizontalAlignment(Element.ALIGN_CENTER);
			headertable01.addCell(hcell);
			headertable01.addCell(hcell_1);

			// =================================================Blank Cell Added To
			// Remove Table Border==============================

			PdfPCell hcell_blank = new PdfPCell(headertable01);
			hcell_blank.setBorder(Rectangle.NO_BORDER);
			hcell_blank.disableBorderSide(Rectangle.RIGHT);
			hcell_blank.disableBorderSide(Rectangle.BOTTOM);

			// ======================================================================================================================
			// 2.1

			String company_name = companyname;
			String company_code = companycode;
			System.out.println("Comp code " + company_code);

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

			PdfPCell hcell_cn = new PdfPCell(PdfStyle.createLabelCellWithBorderLargeFontNoBold(company_name));
			hcell_cn.setHorizontalAlignment(Element.ALIGN_LEFT);
			hcell_cn.setBorder(0);

			PdfPCell hcell2w = new PdfPCell(PdfStyle.createLabelCellWithBorderSmallFontNoBoldForHeader(result1.toString()));
			hcell2w.setHorizontalAlignment(Element.ALIGN_LEFT);
			hcell2w.setBorder(0);

			PdfPTable addressTbl_a = new PdfPTable(1);
			addressTbl_a.addCell(hcell_cn);
			addressTbl_a.addCell(hcell2w);

			PdfPCell hcella_4 = new PdfPCell(PdfStyle.createLabelCellWithoutBorderSmallFontNoPad("Drug Lic No:"));
			hcella_4.setHorizontalAlignment(Element.ALIGN_LEFT);
			hcella_4.setVerticalAlignment(Element.ALIGN_TOP);
			hcella_4.setBorder(0);

			PdfPCell hcella_4_1 = new PdfPCell(
				PdfStyle.createLabelCellWithBorderSmallFontNoBoldForHeader(challan.getLic1()));
			hcella_4_1.setHorizontalAlignment(Element.ALIGN_LEFT);
			hcella_4_1.setVerticalAlignment(Element.ALIGN_TOP);
			hcella_4_1.setBorder(0);

			PdfPCell hcella_7 = new PdfPCell(PdfStyle.createLabelCellWithoutBorderSmallFontNoPad(" "));
			hcella_7.setHorizontalAlignment(Element.ALIGN_LEFT);
			hcella_7.setVerticalAlignment(Element.ALIGN_TOP);
			hcella_7.setBorder(0);

			PdfPCell hcella_4_2 = new PdfPCell(
				PdfStyle.createLabelCellWithBorderSmallFontNoBoldForHeader(challan.getLic2()));
			hcella_4_2.setHorizontalAlignment(Element.ALIGN_LEFT);
			hcella_4_2.setVerticalAlignment(Element.ALIGN_TOP);
			hcella_4_2.setBorder(0);
			PdfPCell hcella_5 = null;
			PdfPCell hcella_4_3 = null;
			PdfPCell hcella_6 = null;
			PdfPCell hcella_4_4 = null;
			if (company_code.equalsIgnoreCase("NIL") || company_code.equalsIgnoreCase("NHP")) {
			    if (isOldInvoice) {
				hcella_5 = new PdfPCell(PdfStyle.createLabelCellWithoutBorderSmallFontNoPad("VAT Reg No:"));
				hcella_5.setHorizontalAlignment(Element.ALIGN_LEFT);
				hcella_5.setVerticalAlignment(Element.ALIGN_TOP);
				hcella_5.setBorder(0);

				hcella_4_3 = new PdfPCell(
					PdfStyle.createLabelCellWithBorderSmallFontNoBoldForHeader(challan.getLoc_tin_no()));
				hcella_4_3.setHorizontalAlignment(Element.ALIGN_LEFT);
				hcella_4_3.setVerticalAlignment(Element.ALIGN_TOP);
				hcella_4_3.setBorder(0);

				hcella_6 = new PdfPCell(PdfStyle.createLabelCellWithoutBorderSmallFontNoPad("CST Reg No :"));
				hcella_6.setHorizontalAlignment(Element.ALIGN_LEFT);
				hcella_6.setVerticalAlignment(Element.ALIGN_TOP);
				hcella_6.setBorder(0);

				hcella_4_4 = new PdfPCell(
					PdfStyle.createLabelCellWithBorderSmallFontNoBoldForHeader(challan.getLoc_cst_no()));
				hcella_4_4.setHorizontalAlignment(Element.ALIGN_LEFT);
				hcella_4_4.setVerticalAlignment(Element.ALIGN_TOP);
				hcella_4_4.setBorder(0);
			    } else {
				hcella_5 = new PdfPCell(PdfStyle.createLabelCellWithoutBorderSmallFontNoPad("GSTIN:"));
				hcella_5.setHorizontalAlignment(Element.ALIGN_LEFT);
				hcella_5.setVerticalAlignment(Element.ALIGN_TOP);
				hcella_5.setBorder(0);

				hcella_4_3 = new PdfPCell(
					PdfStyle.createLabelCellWithBorderSmallFontNoBoldForHeader(challan.getGst_no()));
				hcella_4_3.setHorizontalAlignment(Element.ALIGN_LEFT);
				hcella_4_3.setVerticalAlignment(Element.ALIGN_TOP);
				hcella_4_3.setBorder(0);

				hcella_6 = new PdfPCell(PdfStyle.createLabelCellWithoutBorderSmallFontNoPad("ARN No :"));
				hcella_6.setHorizontalAlignment(Element.ALIGN_LEFT);
				hcella_6.setVerticalAlignment(Element.ALIGN_TOP);
				hcella_6.setBorder(0);

				hcella_4_4 = new PdfPCell(
					PdfStyle.createLabelCellWithBorderSmallFontNoBoldForHeader(challan.getLoc_tin_no()));
				hcella_4_4.setHorizontalAlignment(Element.ALIGN_LEFT);
				hcella_4_4.setVerticalAlignment(Element.ALIGN_TOP);
				hcella_4_4.setBorder(0);
			    }
			} else {
			    hcella_5 = new PdfPCell(PdfStyle.createLabelCellWithoutBorderSmallFontNoPad("VAT Reg No:"));
			    hcella_5.setHorizontalAlignment(Element.ALIGN_LEFT);
			    hcella_5.setVerticalAlignment(Element.ALIGN_TOP);
			    hcella_5.setBorder(0);

			    hcella_4_3 = new PdfPCell(
				    PdfStyle.createLabelCellWithBorderSmallFontNoBoldForHeader(challan.getLoc_tin_no()));
			    hcella_4_3.setHorizontalAlignment(Element.ALIGN_LEFT);
			    hcella_4_3.setVerticalAlignment(Element.ALIGN_TOP);
			    hcella_4_3.setBorder(0);

			    hcella_6 = new PdfPCell(PdfStyle.createLabelCellWithoutBorderSmallFontNoPad("CST Reg No :"));
			    hcella_6.setHorizontalAlignment(Element.ALIGN_LEFT);
			    hcella_6.setVerticalAlignment(Element.ALIGN_TOP);
			    hcella_6.setBorder(0);

			    hcella_4_4 = new PdfPCell(
				    PdfStyle.createLabelCellWithBorderSmallFontNoBoldForHeader(challan.getLoc_cst_no()));
			    hcella_4_4.setHorizontalAlignment(Element.ALIGN_LEFT);
			    hcella_4_4.setVerticalAlignment(Element.ALIGN_TOP);
			    hcella_4_4.setBorder(0);
			}

			PdfPCell hcella_8 = new PdfPCell(PdfStyle.createLabelCellWithoutBorderSmallFontNoPad("CIN No :"));
			hcella_8.setHorizontalAlignment(Element.ALIGN_LEFT);
			hcella_8.setVerticalAlignment(Element.ALIGN_TOP);
			hcella_8.setBorder(0);

			PdfPCell hcella_4_8 = new PdfPCell(
				PdfStyle.createLabelCellWithBorderSmallFontNoBoldForHeader(challan.getLoc_cinno()));
			hcella_4_8.setHorizontalAlignment(Element.ALIGN_LEFT);
			hcella_4_8.setVerticalAlignment(Element.ALIGN_TOP);
			hcella_4_8.setBorder(0);

			PdfPCell hcella_9 = new PdfPCell(PdfStyle.createLabelCellWithoutBorderSmallFontNoPad("Website :"));
			hcella_9.setHorizontalAlignment(Element.ALIGN_LEFT);
			hcella_9.setVerticalAlignment(Element.ALIGN_TOP);
			hcella_9.setBorder(0);

			PdfPCell hcella_4_9 = new PdfPCell(
				PdfStyle.createLabelCellWithBorderSmallFontNoBoldForHeader(challan.getLoc_web_site()));
			hcella_4_9.setHorizontalAlignment(Element.ALIGN_LEFT);
			hcella_4_9.setVerticalAlignment(Element.ALIGN_TOP);
			hcella_4_9.setBorder(0);

			PdfPCell hcella_10 = new PdfPCell(PdfStyle.createLabelCellWithoutBorderSmallFontNoPad("Tel No :"));
			hcella_10.setHorizontalAlignment(Element.ALIGN_LEFT);
			hcella_10.setVerticalAlignment(Element.ALIGN_TOP);
			hcella_10.setBorder(0);

			PdfPCell hcella_4_10 = new PdfPCell(
				PdfStyle.createLabelCellWithBorderSmallFontNoBoldForHeader(challan.getLoc_telephone_no()));
			hcella_4_10.setHorizontalAlignment(Element.ALIGN_LEFT);
			hcella_4_10.setVerticalAlignment(Element.ALIGN_TOP);
			hcella_4_10.setBorder(0);

			PdfPCell cell11 = new PdfPCell(addressTbl_a);
			cell11.setBorder(0);

			PdfPTable addressTbl = new PdfPTable(1);
			PdfPTable addressTbl_b = new PdfPTable(2);
			addressTbl_b.setWidths(new float[] { 1.5f, 8.5f });
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

			/*
			 * String company_name = (String) context.getAttribute("company_name");
			 * 
			 * StringBuilder result1 = new StringBuilder();
			 * result1.append(challan.getLoc_add1().length()>40 ?
			 * challan.getLoc_add1().substring(0, 40) : challan.getLoc_add1());
			 * result1.append(System.lineSeparator()); result1.append(" ");
			 * result1.append(System.lineSeparator());
			 * result1.append(challan.getLoc_add2().length()>40 ?
			 * challan.getLoc_add2().substring(0, 40) : challan.getLoc_add2());
			 * result1.append(System.lineSeparator());
			 * result1.append(challan.getLoc_add3().length()>40 ?
			 * challan.getLoc_add3().substring(0, 40) : challan.getLoc_add3());
			 * result1.append(System.lineSeparator());
			 * result1.append(challan.getLoc_add4().length()>40 ?
			 * challan.getLoc_add4().substring(0, 40) : challan.getLoc_add4());
			 * result1.append(System.lineSeparator());
			 * 
			 * result1.append("Drug Lic No : "+(challan.getLic1()));
			 * result1.append(System.lineSeparator());
			 * 
			 * 
			 * Font font1 = new Font(FontFamily.HELVETICA, 8,
			 * Font.NORMAL,BaseColor.BLACK);
			 * 
			 * PdfPCell hcell2=new PdfPCell(new
			 * Phrase(result1.toString()==null?"":result1.toString(),font1));
			 * hcell2.setHorizontalAlignment(Element.ALIGN_TOP);
			 * hcell2.setBorder(0);
			 * 
			 * PdfPTable addressTbl = new PdfPTable(1);
			 * 
			 * addressTbl.addCell(hcell2);
			 * 
			 * PdfPCell cell1 = new PdfPCell(addressTbl); cell1.setBorder(0);
			 * //cell1.setColspan(2); headertable2.addCell(cell1);
			 */

			// ======================================================================================================================
			// 2.2

			/*
			 * PdfPCell hcell8=new
			 * PdfPCell(PdfStyle.createLabelCellWithoutBorderSmallFontNoBold(" "));
			 * hcell8.setHorizontalAlignment(Element.ALIGN_LEFT);
			 * 
			 * PdfPCell hcell9=new
			 * PdfPCell(PdfStyle.createLabelCellWithoutBorderSmallFontNoBold(""));
			 * hcell9.setHorizontalAlignment(Element.ALIGN_LEFT);
			 * 
			 * PdfPCell hcell10=new
			 * PdfPCell(PdfStyle.createLabelCellWithoutBorderSmallFontNoBold(""));
			 * hcell10.setHorizontalAlignment(Element.ALIGN_LEFT);
			 * 
			 * PdfPCell hcell11=new
			 * PdfPCell(PdfStyle.createLabelCellWithoutBorderSmallFontNoBold(""));
			 * hcell11.setHorizontalAlignment(Element.ALIGN_LEFT);
			 * 
			 * PdfPCell hcell12=new
			 * PdfPCell(PdfStyle.createLabelCellWithoutBorderSmallFontNoBold(""));
			 * hcell12.setHorizontalAlignment(Element.ALIGN_LEFT);
			 * 
			 * PdfPCell hcell13=new
			 * PdfPCell(PdfStyle.createLabelCellWithoutBorderSmallFontNoBold(""));
			 * hcell13.setHorizontalAlignment(Element.ALIGN_LEFT);
			 * 
			 * PdfPCell hcell14=new
			 * PdfPCell(PdfStyle.createLabelCellWithoutBorderSmallFontNoBold(""));
			 * hcell14.setHorizontalAlignment(Element.ALIGN_LEFT);
			 * 
			 * PdfPTable addressTbl1 = new PdfPTable(1);
			 * addressTbl1.addCell(hcell8); addressTbl1.addCell(hcell9);
			 * addressTbl1.addCell(hcell10); addressTbl1.addCell(hcell11);
			 * addressTbl1.addCell(hcell12); addressTbl1.addCell(hcell13);
			 * addressTbl1.addCell(hcell14);
			 * 
			 * PdfPCell cell2 = new PdfPCell(addressTbl1); cell2.setBorder(0);
			 * 
			 * headertable2.addCell(cell2);
			 */

			// ======================================================================================================================

			// ======================================================================================================================
			// 2.3

			PdfPCell hcell16 = new PdfPCell(PdfStyle.createLabelCellWithoutBorderSmallFont("Date "));
			hcell16.setHorizontalAlignment(Element.ALIGN_LEFT);

			PdfPCell hcell17 = new PdfPCell(PdfStyle.createLabelCellWithoutBorderSmallFont("Order#"));
			hcell17.setHorizontalAlignment(Element.ALIGN_LEFT);

			PdfPCell hcell18 = new PdfPCell(PdfStyle.createLabelCellWithoutBorderSmallFont("No of Boxes"));
			hcell18.setHorizontalAlignment(Element.ALIGN_LEFT);

			PdfPCell hcell19 = new PdfPCell(PdfStyle.createLabelCellWithoutBorderSmallFont("Weight"));
			hcell19.setHorizontalAlignment(Element.ALIGN_LEFT);
			hcell19.setVerticalAlignment(Element.ALIGN_TOP);

			PdfPCell hcell20 = new PdfPCell(PdfStyle.createLabelCellWithoutBorderSmallFont("Remark "));
			hcell20.setHorizontalAlignment(Element.ALIGN_LEFT);
			hcell20.setVerticalAlignment(Element.ALIGN_TOP);

			PdfPTable addressTbl2 = new PdfPTable(1);
			addressTbl2.addCell(hcell16);
			addressTbl2.addCell(hcell17);
			addressTbl2.addCell(hcell18);
			addressTbl2.addCell(hcell19);
			addressTbl2.addCell(hcell20);

			PdfPCell cell3 = new PdfPCell(addressTbl2);
			cell3.setBorder(0);

			headertable2.addCell(cell3);

			// ======================================================================================================================

			// ======================================================================================================================
			// 2.4
			Font medFont = new Font(Font.FontFamily.HELVETICA, 12, Font.NORMAL, BaseColor.BLACK);

			PdfPCell hcell26 = new PdfPCell(PdfStyle
				.createLabelCellWithoutBorderSmallFontNoBold((challan.getDate() == null ? "" : challan.getDate())));
			hcell26.setHorizontalAlignment(Element.ALIGN_LEFT);

			PdfPCell hcell27 = new PdfPCell(PdfStyle.createLabelCellWithoutBorderSmallFontNoBold(
				(challan.getDoc_no().length() > 26 ? challan.getDoc_no().substring(0, 26) : challan.getDoc_no())));
			hcell27.setHorizontalAlignment(Element.ALIGN_LEFT);

			PdfPCell hcell28 = new PdfPCell(PdfStyle.createLabelCellWithoutBorderSmallFontNoBold(""));
			hcell28.setHorizontalAlignment(Element.ALIGN_LEFT);

			PdfPCell hcell29 = new PdfPCell(PdfStyle.createLabelCellWithoutBorderSmallFontNoBold(""));
			hcell29.setHorizontalAlignment(Element.ALIGN_LEFT);

			PdfPCell hcell34 = new PdfPCell(PdfStyle.createLabelCellWithoutBorderSmallFontNoBold(
				challan.getAlloc_remark() == null ? "" : challan.getAlloc_remark()));
			hcell34.setHorizontalAlignment(Element.ALIGN_LEFT);
			hcell34.setVerticalAlignment(Element.ALIGN_TOP);
			PdfPTable addressTbl3 = new PdfPTable(1);
			addressTbl3.addCell(hcell26);
			addressTbl3.addCell(hcell27);
			addressTbl3.addCell(hcell28);
			addressTbl3.addCell(hcell29);

			addressTbl3.addCell(hcell34);

			PdfPCell cell4 = new PdfPCell(addressTbl3);
			cell4.setBorder(0);

			headertable2.addCell(cell4);

			// =================================================Blank Cell Added To
			// Remove Table Border==============================

			PdfPCell hcell_blank2 = new PdfPCell(headertable2);
			hcell_blank2.setBorder(Rectangle.NO_BORDER);

			// =====================================================================================================================

			// =====================================================================================================================
			// 3.1

			PdfPCell hcell35 = new PdfPCell(PdfStyle.createLabelCellWithoutBorderSmallFont("Bill To :"));
			hcell35.setHorizontalAlignment(Element.ALIGN_LEFT);

			PdfPCell hcell36 = new PdfPCell(PdfStyle.createLabelCellWithoutBorderSmallFontNoPad(
				challan.getStaff_name() == null ? "" : challan.getStaff_name()));
			hcell36.setHorizontalAlignment(Element.ALIGN_LEFT);

			StringBuilder result2 = new StringBuilder();
			result2.append(challan.getStaff_addr1().length() > 50 ? challan.getStaff_addr1().substring(0, 50)
				: challan.getStaff_addr1());
			result2.append(System.lineSeparator());
			result2.append(challan.getStaff_addr2().length() > 50 ? challan.getStaff_addr2().substring(0, 50)
				: challan.getStaff_addr2());
			result2.append(System.lineSeparator());
			result2.append(challan.getStaff_addr3().length() > 50 ? challan.getStaff_addr3().substring(0, 50)
				: challan.getStaff_addr3());
			result2.append(System.lineSeparator());
			result2.append(challan.getStaff_addr4().length() > 50 ? challan.getStaff_addr4().substring(0, 50)
				: challan.getStaff_addr4());
			result2.append(System.lineSeparator());

			PdfPCell hcell37 = new PdfPCell(PdfStyle
				.createLabelCellWithoutBorderSmallFontNoBold(result2.toString() == null ? "" : result2.toString()));
			hcell37.setHorizontalAlignment(Element.ALIGN_LEFT);

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

			PdfPCell hcell38 = new PdfPCell(PdfStyle.createLabelCellWithoutBorderSmallFont("Ship To :"));
			hcell38.setHorizontalAlignment(Element.ALIGN_LEFT);
			PdfPCell hcell36_1 = new PdfPCell(PdfStyle.createLabelCellWithoutBorderSmallFontNoPad(
				challan.getStaff_name() == null ? "" : challan.getStaff_name()));
			hcell36_1.setHorizontalAlignment(Element.ALIGN_LEFT);
			PdfPCell hcell40 = new PdfPCell(PdfStyle
				.createLabelCellWithoutBorderSmallFontNoBold(result2.toString() == null ? "" : result2.toString()));
			hcell40.setHorizontalAlignment(Element.ALIGN_LEFT);

			PdfPTable custTbl1 = new PdfPTable(1);
			custTbl1.addCell(hcell38);
			custTbl1.addCell(hcell36_1);
			custTbl1.addCell(hcell40);

			PdfPCell cell6 = new PdfPCell(custTbl1);
			cell6.setBorder(0);

			headertable3.addCell(cell6);

			// ======================================================================================================================

			// =====================================================================================================================
			// 3.3

			// =================================================Blank Cell Added To
			// Remove Table Border3==============================

			PdfPCell hcell_blank3 = new PdfPCell(headertable3);
			hcell_blank3.setBorder(Rectangle.NO_BORDER);

			bodytable = new PdfPTable(4);

			bodytable.setWidthPercentage(100);
			bodytable.setWidths(bodyTableWidth);
			bodytable.addCell(PdfStyle.createLabelWithBGColour("Item"));
			bodytable.addCell(PdfStyle.createLabelWithBGColour("Quantity"));
			bodytable.addCell(PdfStyle.createLabelWithBGColour("Batch/Art Work Number"));
			bodytable.addCell(PdfStyle.createLabelWithBGColour("Remark"));

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
			    String prodtype, String path, List<ViewPrePrintedDetailChallan> challan, String printtype,
			    boolean footer_ind) throws DocumentException {

			PdfPCell cell = null;
			try {

			    if (limit - rows > 0) {
				// add extra empty rows in body table
				cell = new PdfPCell();
				PdfPTable temp = new PdfPTable(4);
				temp.setWidths(bodyTableWidth);

				temp.addCell(PdfStyle.createValueCellWithHeight((" "), false, true, 27f));
				temp.addCell(PdfStyle.createValueCellWithHeight((""), false, true, 18f));
				temp.addCell(PdfStyle.createValueCellWithHeight((""), false, true, 18f));
				temp.addCell(PdfStyle.createValueCellWithHeight((""), true, true, 18f));

				cell = new PdfPCell(temp);
				cell.setBorder(0);
				cell.setColspan(4);

				for (int i = 0; i < (limit - rows); i++) {
				    bodytable.addCell(cell);
				}
			    }

			    cell = new PdfPCell(PdfStyle.createSingleLine());
			    cell.setColspan(4);
			    bodytable.addCell(cell);

			    column.addElement(bodytable);
			    column.setSimpleColumn(20, 0, 580, 596);
			    column.go();

			    bodytable = new PdfPTable(4);
			    bodytable.setWidthPercentage(100);
			    bodytable.setWidths(bodyTableWidth);

			    event.setFooter(createFooter(printLabel, isFooterData, pageflag, pagecount, total, division, loc,
				    frm_challan, to_challan, dispatchType, prodtype, path, challan, printtype, footer_ind));

			} catch (DocumentException e) {
			    throw e;
			}
			return bodytable;
		    }

		    private PdfPTable createFooter(HashMap<String, String> printLabel, boolean isFooterData, boolean pageflag,
			    int pagecount, BigDecimal total, int division, Integer loc, String frm_challan, String to_challan,
			    String dispatchType, String prodtype, String path, List<ViewPrePrintedDetailChallan> challan,
			    String printtype, boolean footer_ind) throws DocumentException {
			PdfPTable maintable = new PdfPTable(1);

			System.out.println("challan size() " + challan.size());
			try {
			    for (ViewPrePrintedDetailChallan obj : challan) {
				PdfPCell cell = null;

				maintable.setWidthPercentage(100);

				cell = new PdfPCell();

				maintable.setWidths(new float[] { 10f });

				// =====================================end of
				// total====================================================

				PdfPTable childTbl1 = new PdfPTable(2);
				childTbl1.setWidths(new float[] { 9f, 1f });

				// ------------------------------------------------------------------------------------------------------
				PdfPCell hcell_t1 = null;
				PdfPCell hcell_t3 = null;
				if (footer_ind) {
				    hcell_t1 = new PdfPCell(PdfStyle.createLabelCellWithoutBorderSmallFont("Picked by"));
				    hcell_t1.setHorizontalAlignment(Element.ALIGN_LEFT);
				    if (printtype.equals("tcfa")) {
					hcell_t3 = new PdfPCell(PdfStyle.createLabelCellWithoutBorderSmallFont(" "));
				    } else {
					hcell_t3 = new PdfPCell(PdfStyle.createLabelCellWithoutBorderSmallFont("Packed by"));
				    }
				    hcell_t3.setHorizontalAlignment(Element.ALIGN_LEFT);
				} else {
				    hcell_t1 = new PdfPCell(PdfStyle.createLabelCellWithoutBorderSmallFont(""));
				    hcell_t1.setHorizontalAlignment(Element.ALIGN_LEFT);

				    if (printtype.equals("tcfa")) {
					hcell_t3 = new PdfPCell(PdfStyle.createLabelCellWithoutBorderSmallFont(" "));
				    } else {
					hcell_t3 = new PdfPCell(PdfStyle.createLabelCellWithoutBorderSmallFont(""));
				    }
				    hcell_t3.setHorizontalAlignment(Element.ALIGN_LEFT);
				}
				// ------------------------------------------------------------------------------------------------------
				PdfPCell hcell_t1_date = null;
				PdfPCell hcell_t3_date = null;
				if (footer_ind) {
				    hcell_t1_date = new PdfPCell(PdfStyle.createLabelCellWithoutBorderSmallFont("Date"));
				    hcell_t1_date.setHorizontalAlignment(Element.ALIGN_LEFT);

				    if (printtype.equals("tcfa")) {
					hcell_t3_date = new PdfPCell(PdfStyle.createLabelCellWithoutBorderSmallFont(" "));
				    } else {
					hcell_t3_date = new PdfPCell(PdfStyle.createLabelCellWithoutBorderSmallFont("Date"));
				    }
				    hcell_t3_date.setHorizontalAlignment(Element.ALIGN_LEFT);
				} else {
				    hcell_t1_date = new PdfPCell(PdfStyle.createLabelCellWithoutBorderSmallFont(""));
				    hcell_t1_date.setHorizontalAlignment(Element.ALIGN_LEFT);

				    if (printtype.equals("tcfa")) {
					hcell_t3_date = new PdfPCell(PdfStyle.createLabelCellWithoutBorderSmallFont(" "));
				    } else {
					hcell_t3_date = new PdfPCell(PdfStyle.createLabelCellWithoutBorderSmallFont(""));
				    }
				    hcell_t3_date.setHorizontalAlignment(Element.ALIGN_LEFT);
				}

				childTbl1.addCell(hcell_t1);
				childTbl1.addCell(hcell_t3);

				childTbl1.addCell(hcell_t1_date);
				childTbl1.addCell(hcell_t3_date);

				PdfPCell hcell4 = new PdfPCell(childTbl1);
				hcell4.setBorder(Rectangle.NO_BORDER);

				maintable.addCell(hcell4);

				// ---------------------------------------------------Remark
				// :-------------------------------------------------------------

				/*
				 * PdfPCell hcell1_remark0 = new PdfPCell(
				 * PdfStyle.createLabelCellWithoutBorderSmallFontBold("Remark :"
				 * ));
				 * 
				 * hcell1_remark0.setHorizontalAlignment(Element.ALIGN_LEFT);
				 * 
				 * PdfPCell hcell1_remark = new PdfPCell(
				 * PdfStyle.createLabelCellWithoutBorderSmallFontNoBold(obj.
				 * getDsp_challan_msg().length()>600 ?
				 * obj.getDsp_challan_msg().substring(0, 600) :
				 * obj.getDsp_challan_msg()));
				 * 
				 * hcell1_remark.setHorizontalAlignment(Element.ALIGN_LEFT);
				 * //hcell1_remark.setMinimumHeight(36f);
				 * 
				 * 
				 * PdfPTable childTbl4 = new PdfPTable(2);
				 * childTbl4.setWidths(new float[] { 1f,9f});
				 * childTbl4.addCell(hcell1_remark0);
				 * childTbl4.addCell(hcell1_remark);
				 * 
				 * PdfPCell hcell_childTbl2_t5 = new PdfPCell(childTbl4);
				 * hcell_childTbl2_t5.setBorder(Rectangle.NO_BORDER);
				 * hcell_childTbl2_t5.setHorizontalAlignment(Element.ALIGN_LEFT)
				 * ; hcell_childTbl2_t5.setColspan(3);
				 * 
				 * maintable.addCell(hcell_childTbl2_t5);
				 */
				// -----------------------------------------------------------------------------------------------------------------
				// -----------------------------------------------------------------------------------------------------------------

				if (pageflag) {
				    cell = new PdfPCell(PdfStyle.createLabelCellWithoutBorderSmallFontNoBold("Page No. " + pagecount));
				    cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
				    cell.setVerticalAlignment(Element.ALIGN_BOTTOM);
				    maintable.addCell(cell);

				}
				break;
			    } // end of for each list
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
