package com.excel.service;

import com.excel.model.Company;
import com.excel.model.Location;
import com.excel.model.NOV_BillOfSupply_Challan;
import com.excel.repository.LocationRepository;
import com.excel.utility.CodifyErrors;
import com.excel.utility.FooterWithoutPageNo;
import com.excel.utility.MedicoConstants;
import com.excel.utility.PdfStyle;
import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Chunk;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.Image;
import com.itextpdf.text.Font.FontFamily;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.Rectangle;
import com.itextpdf.text.pdf.ColumnText;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.io.PrintStream;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;

@Service
public class NOV_BillOfSupply_ChallanPrintingImpl implements NOV_BillOfSupply_ChallanPrinting, MedicoConstants {
	
	@Autowired
	LocationRepository locationrepository;
	 NOV_BillOfSupply_Challan footer;
	    int add = 0;
	    Font fontbold = null;
	    int fontSize = 7;
	    private HashMap<String, String> printLabel;
	    private String path;
	    private Boolean pageflag;
	    private String logo;
	    private String remark;
	    private String narration;
	    private String stockPntName, compCode;
	    private int limit = 14;

	    private float[] bodyTableWidth = new float[] { 4.5f, 1f, 1.5f, 1f, 1f, 1f };
	    private float[] bodyTableWidthForOld = new float[] { 5.5f, 1.5f, 1f, 1f, 1f };
	
	@Override
	 public String getPdfgetPdf(String division, String loc, String frm_challan, String to_challan, String dispatchType,
			 String prodtype, String printtype, List<NOV_BillOfSupply_Challan> challans, String show_amount,HttpSession session,
			    String footer_signature_ind) throws Exception {
		System.out.println("dis type " + dispatchType);
		System.out.println("prod type " + prodtype);

		compCode = ((String) session.getServletContext().getAttribute(COMPANY_CODE)).trim();
	
		// System.out.println("Inside new Pdf for bill of supply");
		String path = MedicoConstants.PDF_FILE_PATH;
		File file1 = new File(path);
		System.out.println("path 11 : " + path);
		FileOutputStream os = null;
		PrintStream ps = null;
		long timevar = new Date().getTime();
		String filename = "challan_" + timevar + ".pdf";
		// System.out.println("filename 11 " +filename);
		
		File docfile1 = new File(path + "\\files\\");
		docfile1.mkdirs();
		File docfile = new File(path + "\\files\\" + filename);
		OutputStream file = new FileOutputStream(docfile);
		Document document = new Document(PageSize.A4);

		PdfWriter writer = PdfWriter.getInstance(document, file);
		Rectangle rect = new Rectangle(20, 28, 580, 840);
		writer.setBoxSize("header", rect);
		rect = new Rectangle(20, 2, 580, 192);
		writer.setBoxSize("footer", rect);
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

			Set<String> challanset = new LinkedHashSet<String>();
			HashMap<String, Boolean> map = new HashMap<>();
			for (NOV_BillOfSupply_Challan challan : challans) {
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
				List<NOV_BillOfSupply_Challan> list_new_page = new ArrayList<NOV_BillOfSupply_Challan>();
				isnew = true;
				for_new_page = true;
				PdfPTable maintable1 = new PdfPTable(1);
				maintable1.setWidthPercentage(100);
				PdfPCell cell = null;
				pageflag = false;
				pagecount = 1;
				boolean isOldInvoice = map.get(num);

				PdfPTable hcell = null;
				int rows = 0;
				int size = 0;
				if (!isOldInvoice)
					size = 6;
				else
					size = 5;
				bodytable = new PdfPTable(size);
				bodytable.getDefaultCell().setPadding(0.0f);
				bodytable.setWidthPercentage(100);
				if (!isOldInvoice)
					bodytable.setWidths(bodyTableWidth);
				else
					bodytable.setWidths(bodyTableWidthForOld);
				total = BigDecimal.ZERO;

				String dtp = "";
				String ship_doc_no = "";

				String date = "";
				String cases = "";
				String weigh = "";
				String dsp_challan_msg = "";
				String remark = "";
				int count_var = 0;
				for (NOV_BillOfSupply_Challan challan : challans) {

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
									total, division, loc, frm_challan, to_challan, dispatchType, prodtype, session,
									challans, show_amount, footer_ind, challan.getDsp_transporter(),
									challan.getShip_doc_no(), challan.getDate(),
									challan.getCases().setScale(0).toString(), challan.getWeigh(),
									challan.getAlloc_remark().length() > 600
											? challan.getAlloc_remark().substring(0, 600)
											: challan.getAlloc_remark(),
									remark, isOldInvoice);

							document.newPage();
							pagecount++;
							rows = 0;

						}
						if (isnew) {
							footer = challan;
							maintable1.addCell(PdfStyle.createLabelCellWithoutBorderSmallFontNoBold(""));
							hcell = createHeader(challan, pagecount, division, loc, frm_challan, to_challan,
									dispatchType, prodtype, session, show_amount, isOldInvoice);
							cell = new PdfPCell(hcell);
							cell.setBorder(0);
							cell.setHorizontalAlignment(Element.ALIGN_CENTER);
							maintable1.addCell(cell);
							event.setHeader(maintable1);
							isnew = false;
							list_new_page.add(challan);
						}
						if (rows == 0) {
							bodytable.addCell(PdfStyle.createValueCellWithHeight((" "), false, true, 17f));
							if (!isOldInvoice)
								bodytable.addCell(PdfStyle.createValueCellWithHeight((""), false, true, 17f));
							bodytable.addCell(PdfStyle.createValueCellWithHeight((""), false, true, 17f));
							bodytable.addCell(PdfStyle.createValueCellWithHeight((""), true, true, 17f));
							bodytable.addCell(PdfStyle.createValueCellWithHeight((""), true, true, 17f));
							bodytable.addCell(PdfStyle.createValueCellWithHeight((""), true, true, 17f));
						}

						bodytable.addCell(PdfStyle.createValueCellWithHeight(
								(challan.getProduct_desc().length() > 100 ? challan.getProduct_desc().substring(0, 100)
										: challan.getProduct_desc()),
								false, true, 27f));
						if (!isOldInvoice)
							bodytable.addCell(
									PdfStyle.createValueCellWithHeight((challan.getHsn_code()), false, true, 18f));
						bodytable
								.addCell(PdfStyle.createValueCellWithHeight((challan.getBatch_no()), false, true, 18f));
						bodytable.addCell(
								PdfStyle.createValueCellWithHeight((challan.getExpiry_date()), false, true, 18f));
						bodytable.addCell(PdfStyle.createValueCellWithHeight((challan.getTotal_qty() == null
								? BigDecimal.ZERO.setScale(2, RoundingMode.HALF_UP).stripTrailingZeros()
								: challan.getTotal_qty().setScale(2, RoundingMode.HALF_UP).stripTrailingZeros())
										.toPlainString()
								+ " ", true, true, 18f));
						if (show_amount.equals("Y")) {
							bodytable.addCell(PdfStyle.createValueCellWithHeight(
									(challan.getValue() == null ? BigDecimal.ZERO.setScale(2, RoundingMode.HALF_UP)
											: challan.getValue().setScale(2, RoundingMode.HALF_UP)).toString(),
									true, true, 18f));
						} else {
							bodytable.addCell(PdfStyle.createValueCellWithHeight("", true, true, 18f));
						}
						total = total.add(challan.getValue());
						rows++;
						dtp = "";
						ship_doc_no = "";
						date = "";
						cases = "";
						weigh = "";
						dsp_challan_msg = "";

						remark = challan.getAlloc_remark();
						dtp = challan.getDsp_transporter();
						ship_doc_no = challan.getShip_doc_no();
						date = challan.getDate();
						cases = challan.getCases().setScale(0).toString();
						weigh = challan.getWeigh();
						// dsp_challan_msg is the remark that is currently
						// printed.
						dsp_challan_msg = challan.getAlloc_remark().length() > 600
								? challan.getAlloc_remark().substring(0, 600)
								: challan.getAlloc_remark();
					}
				}

				footer_ind = true;
				System.out.println("Outside rows>=limit");
				newPage(column, bodytable, event, printLabel, true, rows, pageflag, pagecount, total, division, loc,
						frm_challan, to_challan, dispatchType, prodtype, session, list_new_page, show_amount,
						footer_ind, dtp, ship_doc_no, date, cases, weigh, dsp_challan_msg, remark, isOldInvoice);

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

	private PdfPTable createHeader(NOV_BillOfSupply_Challan challan, int pagecount, String division, String loc,
			String frm_challan, String to_challan, String dispatchType, String prodtype, HttpSession session,
			String show_amount, boolean isOldInvoice) throws Exception {
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
		headertable2.setWidths(new float[] { 4.5f, 2.5f, 1f, 2f });
		headertable3.setWidths(new float[] { 4.5f, 2.5f, 1f, 2f });
		headertable4.setWidths(new float[] { 2f, 2f, 2.5f, 3.5f });

		Font font = new Font(FontFamily.HELVETICA, 12, Font.BOLD, BaseColor.BLACK);
		PdfPTable headertable1 = new PdfPTable(1);
		headertable1.setWidthPercentage(100);
		headertable1.setWidths(new float[] { 5f });
		compCode=compCode.trim();
		// ======================================================================================================================
		// 1

		PdfPCell hcell = null;
		if (isOldInvoice || dispatchType.equalsIgnoreCase("tcfa")) {
			hcell = new PdfPCell(new Phrase("Delivery Challan", font));
		} else if (compCode.equalsIgnoreCase("NIL") || compCode.equalsIgnoreCase("NHP")) {
			hcell = new PdfPCell(new Phrase("Delivery Challan", font));
		} else {
			hcell = new PdfPCell(new Phrase("Bill Of Supply", font));
		}
		System.out.println("Challan date " + challan.getDate());
		hcell.setBorder(Rectangle.NO_BORDER);
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
		String companyName = ((Company) session.getServletContext().getAttribute(COMPANY_DATA)).getCompany_name();

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

		result1.append("State Name: " + challan.getLoc_state());
		result1.append(System.lineSeparator());
		result1.append("State Code: " + challan.getLoc_code());
		result1.append(System.lineSeparator());

		PdfPCell hcell_cn = new PdfPCell(PdfStyle.createLabelCellWithBorderLargeFontNoBold(companyName));
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

		PdfPCell hcella_5 = new PdfPCell(PdfStyle.createLabelCellWithoutBorderSmallFontNoPad("ARN No:"));
		hcella_5.setHorizontalAlignment(Element.ALIGN_LEFT);
		hcella_5.setVerticalAlignment(Element.ALIGN_TOP);
		hcella_5.setBorder(0);

		PdfPCell hcella_4_3 = new PdfPCell(
				PdfStyle.createLabelCellWithBorderSmallFontNoBoldForHeader(challan.getArn_no()));
		hcella_4_3.setHorizontalAlignment(Element.ALIGN_LEFT);
		hcella_4_3.setVerticalAlignment(Element.ALIGN_TOP);
		hcella_4_3.setBorder(0);

		PdfPCell hcella_6 = new PdfPCell(PdfStyle.createLabelCellWithoutBorderSmallFontNoPad("GSTIN :"));
		hcella_6.setHorizontalAlignment(Element.ALIGN_LEFT);
		hcella_6.setVerticalAlignment(Element.ALIGN_TOP);
		hcella_6.setBorder(0);

		PdfPCell hcella_4_4 = new PdfPCell(
				PdfStyle.createLabelCellWithBorderSmallFontNoBoldForHeader(challan.getGst_no()));
		hcella_4_4.setHorizontalAlignment(Element.ALIGN_LEFT);
		hcella_4_4.setVerticalAlignment(Element.ALIGN_TOP);
		hcella_4_4.setBorder(0);

		PdfPCell hcella_5_old = new PdfPCell(PdfStyle.createLabelCellWithoutBorderSmallFontNoPad("VAT Reg No:"));
		hcella_5_old.setHorizontalAlignment(Element.ALIGN_LEFT);
		hcella_5_old.setVerticalAlignment(Element.ALIGN_TOP);
		hcella_5_old.setBorder(0);

		PdfPCell hcella_4_3_old = new PdfPCell(
				PdfStyle.createLabelCellWithBorderSmallFontNoBoldForHeader(challan.getVat_no()));
		hcella_4_3_old.setHorizontalAlignment(Element.ALIGN_LEFT);
		hcella_4_3_old.setVerticalAlignment(Element.ALIGN_TOP);
		hcella_4_3_old.setBorder(0);

//		PdfPCell hcella_6_old = new PdfPCell(PdfStyle.createLabelCellWithoutBorderSmallFontNoPad("CST Reg No:"));
//		hcella_6_old.setHorizontalAlignment(Element.ALIGN_LEFT);
//		hcella_6_old.setVerticalAlignment(Element.ALIGN_TOP);
//		hcella_6_old.setBorder(0);
//
//		PdfPCell hcella_4_4_old = new PdfPCell(
//				PdfStyle.createLabelCellWithBorderSmallFontNoBoldForHeader(challan.getCst_no()));
//		hcella_4_4_old.setHorizontalAlignment(Element.ALIGN_LEFT);
//		hcella_4_4_old.setVerticalAlignment(Element.ALIGN_TOP);
//		hcella_4_4_old.setBorder(0);

		PdfPCell hcella_8 = new PdfPCell(PdfStyle.createLabelCellWithoutBorderSmallFontNoPad("CIN No :"));
		hcella_8.setHorizontalAlignment(Element.ALIGN_LEFT);
		hcella_8.setVerticalAlignment(Element.ALIGN_TOP);
		hcella_8.setBorder(0);

		PdfPCell hcella_4_8 = new PdfPCell(
				PdfStyle.createLabelCellWithBorderSmallFontNoBoldForHeader(challan.getLoc_cinno()));
		hcella_4_8.setHorizontalAlignment(Element.ALIGN_LEFT);
		hcella_4_8.setVerticalAlignment(Element.ALIGN_TOP);
		hcella_4_8.setBorder(0);
		PdfPCell hcella_9 = null;
		// String loc_web_var=challan.getLoc_web_site();
		if (challan.getLoc_web_site().trim().isEmpty()) {
			hcella_9 = new PdfPCell(PdfStyle.createLabelCellWithoutBorderSmallFontNoPad(" "));
			hcella_9.setHorizontalAlignment(Element.ALIGN_LEFT);
			hcella_9.setVerticalAlignment(Element.ALIGN_TOP);
			hcella_9.setBorder(0);
		} else {
			hcella_9 = new PdfPCell(PdfStyle.createLabelCellWithoutBorderSmallFontNoPad("Website :"));
			hcella_9.setHorizontalAlignment(Element.ALIGN_LEFT);
			hcella_9.setVerticalAlignment(Element.ALIGN_TOP);
			hcella_9.setBorder(0);
		}

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

		PdfPCell hcella_11 = new PdfPCell(PdfStyle.createLabelCellWithoutBorderSmallFontNoPad("Email Id :"));
		hcella_11.setHorizontalAlignment(Element.ALIGN_LEFT);
		hcella_11.setVerticalAlignment(Element.ALIGN_TOP);
		hcella_11.setBorder(0);

		PdfPCell hcella_4_11 = new PdfPCell(
				PdfStyle.createLabelCellWithBorderSmallFontNoBoldForHeader(challan.getLoc_emailid()));
		hcella_4_11.setHorizontalAlignment(Element.ALIGN_LEFT);
		hcella_4_11.setVerticalAlignment(Element.ALIGN_TOP);
		hcella_4_11.setBorder(0);

		PdfPCell cell11 = new PdfPCell(addressTbl_a);
		cell11.setBorder(0);

		PdfPTable addressTbl = new PdfPTable(1);
		PdfPTable addressTbl_b = new PdfPTable(2);
		addressTbl_b.setWidths(new float[] { 1.5f, 8.5f });
		addressTbl_b.addCell(hcella_4);
		addressTbl_b.addCell(hcella_4_1);
		addressTbl_b.addCell(hcella_7);
		addressTbl_b.addCell(hcella_4_2);

		if (!isOldInvoice) {
			addressTbl_b.addCell(hcella_6);
			addressTbl_b.addCell(hcella_4_4);
			addressTbl_b.addCell(hcella_5);
			addressTbl_b.addCell(hcella_4_3);
		} else {
			addressTbl_b.addCell(hcella_5_old);
			addressTbl_b.addCell(hcella_4_3_old);
//			addressTbl_b.addCell(hcella_6_old);
//			addressTbl_b.addCell(hcella_4_4_old);

		}

		/*
		 * addressTbl_b.addCell(hcella_8); addressTbl_b.addCell(hcella_4_8);
		 * addressTbl_b.addCell(hcella_9); addressTbl_b.addCell(hcella_4_9);
		 */
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
		// cell1.setColspan(2);

		// headertable2.addCell(cell1);

		// ===== adding logo for non gst report
		if (compCode.equalsIgnoreCase("XXX")) {
			headertable2.addCell(cell1);
			String path = SPRING_RESOURCE_HANDLER_PDF_FILE_PATH_PREFIX;			
					String logoImag = path + "\\images\\medley-logo111.jpg";
			// System.out.println("logoImag : " + logoImag);
			Image img = null;
			try {
				img = Image.getInstance(logoImag);
				img.setAlignment(Image.ALIGN_CENTER);
			} catch (Exception e) {
				System.out.println("Error Occurred :" +e.getMessage());// uncomment asneeded --;
			}
			// img.setAlignment(1);
			PdfPCell imagecell = new PdfPCell(img, false);
			// imagecell.setFixedHeight(100);
			imagecell.setNoWrap(false);
			imagecell.setBorder(0);
			imagecell.setHorizontalAlignment(Element.ALIGN_CENTER);
			imagecell.setVerticalAlignment(Element.ALIGN_MIDDLE);
			// PdfPTable t1= new PdfPTable(1);
			// t1.addCell(imagecell);
			headertable2.addCell(imagecell);
		} else {
			cell1.setColspan(2);
			headertable2.addCell(cell1);
		}

		//

		// ======================================================================================================================
		// 2.2

		/*
		 * PdfPCell hcell8=new
		 * PdfPCell(PdfStyle.createLabelCellWithBorderSmallFontNoBold(" "));
		 * hcell8.setHorizontalAlignment(Element.ALIGN_LEFT); hcell8.setBorder(0);
		 * 
		 * PdfPCell hcellb_1=new
		 * PdfPCell(PdfStyle.createLabelCellWithBorderSmallFontNoBold(" "));
		 * hcellb_1.setHorizontalAlignment(Element.ALIGN_LEFT); hcellb_1.setBorder(0);
		 * 
		 * PdfPCell hcellb_2=new
		 * PdfPCell(PdfStyle.createLabelCellWithBorderSmallFontNoBold(" "));
		 * hcellb_2.setHorizontalAlignment(Element.ALIGN_LEFT); hcellb_2.setBorder(0);
		 * 
		 * 
		 * PdfPCell hcellb_3=new
		 * PdfPCell(PdfStyle.createLabelCellWithBorderSmallFontNoBold(" "));
		 * hcellb_3.setHorizontalAlignment(Element.ALIGN_LEFT); hcellb_3.setBorder(0);
		 * 
		 * 
		 * PdfPCell hcellb_4=new
		 * PdfPCell(PdfStyle.createLabelCellWithBorderSmallFontNoBold(challan.
		 * getLic1())); hcellb_4.setHorizontalAlignment(Element.ALIGN_LEFT);
		 * hcellb_4.setBorder(0);
		 * 
		 * 
		 * PdfPCell hcellb_5=new
		 * PdfPCell(PdfStyle.createLabelCellWithBorderSmallFontNoBold(challan.
		 * getLic2())); hcellb_5.setHorizontalAlignment(Element.ALIGN_LEFT);
		 * hcellb_5.setBorder(0);
		 * 
		 * PdfPCell hcellb_7=new
		 * PdfPCell(PdfStyle.createLabelCellWithBorderSmallFontNoBold(challan.
		 * getLoc_tin_no())); hcellb_7.setHorizontalAlignment(Element.ALIGN_LEFT);
		 * hcellb_7.setBorder(0);
		 * 
		 * PdfPCell hcellb_6=new
		 * PdfPCell(PdfStyle.createLabelCellWithBorderSmallFontNoBold(challan.
		 * getLoc_cst_no())); hcellb_6.setHorizontalAlignment(Element.ALIGN_LEFT);
		 * hcellb_6.setBorder(0);
		 * 
		 * PdfPTable addressTbl1 = new PdfPTable(1); addressTbl1.addCell(hcell8);
		 * addressTbl1.addCell(hcellb_1); addressTbl1.addCell(hcellb_2);
		 * addressTbl1.addCell(hcellb_3); addressTbl1.addCell(hcellb_4);
		 * addressTbl1.addCell(hcellb_5); addressTbl1.addCell(hcellb_7);
		 * addressTbl1.addCell(hcellb_6);
		 * 
		 * PdfPCell cell2 = new PdfPCell(addressTbl1); cell2.setBorder(0);
		 * 
		 * headertable2.addCell(cell2);
		 */

		// ======================================================================================================================

		// ======================================================================================================================
		// 2.3

		PdfPCell hcell16 = new PdfPCell(PdfStyle.createLabelCellWithBorderSmallFont("Date "));
		hcell16.setHorizontalAlignment(Element.ALIGN_LEFT);

		PdfPCell hcell20 = null;
		if (compCode.equalsIgnoreCase("NIL")) {
			hcell20 = new PdfPCell(PdfStyle.createLabelCellWithBorderSmallFont("Challan #"));
			hcell20.setHorizontalAlignment(Element.ALIGN_LEFT);
		} else {
			hcell20 = new PdfPCell(PdfStyle.createLabelCellWithBorderSmallFont("Invoice #"));
			hcell20.setHorizontalAlignment(Element.ALIGN_LEFT);
		}

		PdfPCell hcell21 = new PdfPCell(PdfStyle.createLabelCellWithBorderSmallFont("Customer"));
		hcell21.setHorizontalAlignment(Element.ALIGN_LEFT);

		PdfPCell hcell22 = new PdfPCell(PdfStyle.createLabelCellWithBorderSmallFont("Division "));
		hcell22.setHorizontalAlignment(Element.ALIGN_LEFT);

		PdfPCell hcell23 = new PdfPCell(PdfStyle.createLabelCellWithBorderSmallFont("Team"));
		hcell23.setHorizontalAlignment(Element.ALIGN_LEFT);

		PdfPCell hcell24 = new PdfPCell(PdfStyle.createLabelCellWithBorderSmallFont("Remark"));
		hcell24.setHorizontalAlignment(Element.ALIGN_LEFT);

		PdfPCell hcell17 = new PdfPCell(PdfStyle.createLabelCellWithBorderSmallFontTwiceHeight("Email"));
		hcell17.setHorizontalAlignment(Element.ALIGN_LEFT);

		PdfPCell hcell18 = new PdfPCell(PdfStyle.createLabelCellWithBorderSmallFont("Telephone 1"));
		hcell18.setHorizontalAlignment(Element.ALIGN_LEFT);

		PdfPCell hcell19 = new PdfPCell(PdfStyle.createLabelCellWithBorderSmallFont("Telephone 2"));
		hcell19.setHorizontalAlignment(Element.ALIGN_LEFT);

		PdfPCell hcell19_from_to = new PdfPCell(PdfStyle.createLabelCellWithBorderSmallFont("Dtl. Chl. No: "));
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

		PdfPCell hcell26 = new PdfPCell(PdfStyle
				.createLabelCellWithBorderSmallFontNoBold((challan.getDate() == null ? "" : challan.getDate())));
		hcell26.setHorizontalAlignment(Element.ALIGN_LEFT);

		PdfPCell hcell30 = new PdfPCell(PdfStyle
				.createLabelCellWithBorderSmallFontNoBold((challan.getDoc_no() == null ? "" : challan.getDoc_no())));
		hcell30.setHorizontalAlignment(Element.ALIGN_LEFT);

		PdfPCell hcell31 = new PdfPCell(PdfStyle.createLabelCellWithBorderSmallFontNoBold(
				(challan.getStaff_name().length() > 20 ? challan.getStaff_name().substring(0, 20)
						: challan.getStaff_name())));
		hcell31.setHorizontalAlignment(Element.ALIGN_LEFT);

		PdfPCell hcell32 = new PdfPCell(PdfStyle.createLabelCellWithBorderSmallFontNoBold(
				(challan.getDivision().length() > 28 ? challan.getDivision().substring(0, 28)
						: challan.getDivision())));
		hcell32.setHorizontalAlignment(Element.ALIGN_LEFT);

		PdfPCell hcell33 = new PdfPCell(PdfStyle.createLabelCellWithBorderSmallFontNoBold(
				((challan.getDiv_name() == null ? "" : challan.getDiv_name()).length() > 28
						? challan.getDiv_name().substring(0, 28)
						: challan.getDiv_name() == null ? "" : challan.getDiv_name())));
		hcell33.setHorizontalAlignment(Element.ALIGN_LEFT);

		PdfPCell hcell34 = new PdfPCell(PdfStyle.createLabelCellWithBorderSmallFontNoBold(
				challan.getDsp_challan_msg() == null ? "" : challan.getDsp_challan_msg()));
		hcell34.setHorizontalAlignment(Element.ALIGN_LEFT);

		PdfPCell hcell27 = new PdfPCell(PdfStyle.createLabelCellWithBorderSmallFontNoBoldTwiceHeight(
				(challan.getDspstaff_email().length() > 40 ? challan.getDspstaff_email().substring(0, 40)
						: challan.getDspstaff_email())));
		hcell27.setHorizontalAlignment(Element.ALIGN_LEFT);

		PdfPCell hcell28 = new PdfPCell(PdfStyle.createLabelCellWithBorderSmallFontNoBold(
				(challan.getDspstaff_mobile() == null ? "" : challan.getDspstaff_mobile())));
		hcell28.setHorizontalAlignment(Element.ALIGN_LEFT);

		PdfPCell hcell29 = new PdfPCell(PdfStyle.createLabelCellWithBorderSmallFontNoBold(
				(challan.getDspstaff_mobile() == null ? "" : challan.getDspstaff_mobile())));
		hcell29.setHorizontalAlignment(Element.ALIGN_LEFT);

		String s = challan.getTo_num().length() < 6 ? ""
				: challan.getTo_num().substring(challan.getTo_num().length() - 5);
		PdfPCell hcell29_from_to = new PdfPCell(
				PdfStyle.createLabelCellWithBorderSmallFontNoBold((challan.getFr_num().length() < 6 ? ""
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

		PdfPCell hcell35 = new PdfPCell(PdfStyle.createLabelCellWithBorderSmallFont("Ship To :"));
		hcell35.setHorizontalAlignment(Element.ALIGN_LEFT);

		PdfPCell hcell36 = new PdfPCell(
				PdfStyle.createLabelCellWithoutBorder(challan.getStaff_name() == null ? "" : challan.getStaff_name()));
		hcell36.setHorizontalAlignment(Element.ALIGN_LEFT);
		hcell36.enableBorderSide(Rectangle.LEFT);
		hcell36.enableBorderSide(Rectangle.RIGHT);
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
		/*
		 * result2.append("GSTIN : Unregistered");
		 * result2.append(System.lineSeparator()); result2.append(
		 * "PAN : Not Available"); result2.append(System.lineSeparator());
		 */

		PdfPCell hcell37 = new PdfPCell(PdfStyle
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
		String web_site = (String) company.getWebsite() == null ? " "
				: company.getWebsite();
		String cinno = (String) company.getCinno() == null ? " " : (String) company.getCinno();
		System.out.println("Comp cin " + cinno);
		// System.out.println("web_site " +web_site+ "cinno "+cinno );

		StringBuilder result3 = new StringBuilder();

		// Company name added 27-1-2017
		if (companyName.trim().isEmpty()) {
			result3.append("");
			result3.append(System.lineSeparator());
		} else {
			result3.append(companyName.length() > 30 ? companyName.substring(0, 30) : companyName);
			result3.append(System.lineSeparator());
		}

		result3.append(address1.length() > 33 ? address1.substring(0, 33) : address1);
		result3.append(System.lineSeparator());
		result3.append(address2.length() > 33 ? address2.substring(0, 33) : address2);
		result3.append(System.lineSeparator());
		result3.append(address3.length() > 33 ? address3.substring(0, 33) : address3);
		result3.append(System.lineSeparator());
		result3.append(address4.length() > 33 ? address4.substring(0, 33) : address4);
		result3.append(System.lineSeparator());
		// System.out.println("web_site length : " +web_site.length());
		if (web_site.trim().isEmpty()) {
			result3.append("");
			result3.append(System.lineSeparator());
		} else {
			result3.append(web_site.length() > 33 ? "Website:" + web_site.substring(0, 33).toLowerCase()
					: "Website:" + web_site.toLowerCase());
			result3.append(System.lineSeparator());

		}
		if (cinno.trim().isEmpty()) {
			result3.append("");
			result3.append(System.lineSeparator());
		} else {
			result3.append(cinno.length() > 33 ? "CIN NO:" + cinno.substring(0, 33) : "CIN NO:" + cinno);
			result3.append(System.lineSeparator());
		}
		// result3.append(cinno.length()>33 ? "CIN NO:"+cinno.substring(0, 33) :
		// "CIN NO:"+cinno);

		PdfPCell hcell38 = new PdfPCell(PdfStyle.createLabelCellWithBorderSmallFont("Principal place of business"));
		hcell38.setHorizontalAlignment(Element.ALIGN_LEFT);

		PdfPCell hcell39 = new PdfPCell(PdfStyle.createLabelCellWithoutBorderSmallFontNoBold(
				result3.length() > 180 ? result3.substring(0, 179) : result3.toString()));
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

		PdfPCell hcell43 = new PdfPCell(PdfStyle.createLabelCellWithBorderSmallFont("Note"));
		hcell43.setHorizontalAlignment(Element.ALIGN_CENTER);
		StringBuilder result4 = new StringBuilder();

		result4.append("Goods Not for sale.");
		result4.append(System.lineSeparator());
		if (!compCode.equalsIgnoreCase("NIL")) {
			result4.append("Free samples only.");
		}
		result4.append(System.lineSeparator());
		PdfPCell hcell44 = new PdfPCell(PdfStyle.createLabelCellWithoutBorderSmallFontBold(result4.toString()));
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
			hcell45 = new PdfPCell(PdfStyle.createLabelCellWithBorderSmallFont("Insurance Policy No."));
		} else {
			hcell45 = new PdfPCell(PdfStyle.createLabelCellWithBorderSmallFont(""));
		}
		hcell45.setHorizontalAlignment(Element.ALIGN_LEFT);

		PdfPCell hcell46 = new PdfPCell(PdfStyle.createLabelCellWithoutBorderSmallFontNoBold(" "));
		hcell46.setHorizontalAlignment(Element.ALIGN_LEFT);
		hcell46.enableBorderSide(Rectangle.RIGHT);
		PdfPCell hcell47 = null;
		if (show_amount.equals("Y")) {
			hcell47 = new PdfPCell(PdfStyle.createLabelCellWithBorderSmallFont("Food Licence Nos."));
		} else {
			hcell47 = new PdfPCell(PdfStyle.createLabelCellWithBorderSmallFont(""));
		}
		hcell47.setHorizontalAlignment(Element.ALIGN_LEFT);

		PdfPCell hcell48 = new PdfPCell(PdfStyle.createLabelCellWithoutBorderSmallFontNoBold(" "));
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
		int bodyTableSize = 0;
		if (!isOldInvoice)
			bodyTableSize = 6;
		else
			bodyTableSize = 5;
		bodytable = new PdfPTable(bodyTableSize);

		bodytable.setWidthPercentage(100);
		if (!isOldInvoice)
			bodytable.setWidths(bodyTableWidth);
		else
			bodytable.setWidths(bodyTableWidthForOld);
		bodytable.addCell(PdfStyle.createLabelWithBGColour("Item"));
		if (!isOldInvoice)
			bodytable.addCell(PdfStyle.createLabelWithBGColour("HSN Code"));
		bodytable.addCell(PdfStyle.createLabelWithBGColour("Batch/Art Work No"));
		bodytable.addCell(PdfStyle.createLabelWithBGColour("Expiry Date"));
		bodytable.addCell(PdfStyle.createLabelWithBGColour("Quantity"));
		if (show_amount.equals("Y")) {
			bodytable.addCell(PdfStyle.createLabelWithBGColour("Amount"));
		} else {
			bodytable.addCell(PdfStyle.createLabelWithBGColour(""));
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
		    BigDecimal total, String division, String loc, String frm_challan, String to_challan, String dispatchType,
		    String prodtype, HttpSession session, List<NOV_BillOfSupply_Challan> challan, String show_amount,
		    boolean footer_ind, String dsp_transporter, String ship_doc_no, String date, String cases, String weigh,
		    String dsp_challan_msg, String remark, boolean isOldInvoice) throws DocumentException {
		PdfPCell cell = null;
		try {
		    System.out.println("ship_doc_no " + ship_doc_no);
		    if (limit - (rows) > 0) {
			// add extra empty rows in body table
			cell = new PdfPCell();
			PdfPTable temp = null;
			if (!isOldInvoice) {
			    temp = new PdfPTable(6);
			    temp.setWidths(bodyTableWidth);
			} else {
			    temp = new PdfPTable(5);
			    temp.setWidths(bodyTableWidthForOld);
			}

			temp.addCell(PdfStyle.createValueCellWithHeight((" "), false, true, 27f));
			if (!isOldInvoice)
			    temp.addCell(PdfStyle.createValueCellWithHeight((""), false, true, 18f));
			temp.addCell(PdfStyle.createValueCellWithHeight((""), false, true, 18f));
			temp.addCell(PdfStyle.createValueCellWithHeight((""), true, true, 18f));
			temp.addCell(PdfStyle.createValueCellWithHeight((""), true, true, 18f));
			temp.addCell(PdfStyle.createValueCellWithHeight((""), true, true, 18f));

			cell = new PdfPCell(temp);
			cell.setBorder(0);
			if (!isOldInvoice)
			    cell.setColspan(6);
			else
			    cell.setColspan(5);
			for (int i = 0; i < (limit - rows - 1); i++) {
			    bodytable.addCell(cell);
			}
		    }
		    bodytable.addCell(PdfStyle.createValueCellWithHeight((" "), false, true, 10f));
		    if (!isOldInvoice)
			bodytable.addCell(PdfStyle.createValueCellWithHeight((""), false, true, 10f));
		    bodytable.addCell(PdfStyle.createValueCellWithHeight((""), false, true, 10f));
		    bodytable.addCell(PdfStyle.createValueCellWithHeight((""), true, true, 10f));
		    bodytable.addCell(PdfStyle.createValueCellWithHeight((""), true, true, 10f));
		    bodytable.addCell(PdfStyle.createValueCellWithHeight((""), true, true, 10f));
		    cell = new PdfPCell(PdfStyle.createSingleLine());
		    if (!isOldInvoice)
			cell.setColspan(6);
		    else
			cell.setColspan(5);
		    bodytable.addCell(cell);

		    column.addElement(bodytable);
		    column.setSimpleColumn(20, 0, 580, 572);
		    column.go();

		    int bodyTableSize = 0;
		    if (!isOldInvoice)
			bodyTableSize = 6;
		    else
			bodyTableSize = 5;
		    bodytable = new PdfPTable(bodyTableSize);

		    bodytable.setWidthPercentage(100);
		    if (!isOldInvoice)
			bodytable.setWidths(bodyTableWidth);
		    else
			bodytable.setWidths(bodyTableWidthForOld);

		    event.setFooter(createFooter(printLabel, isFooterData, pageflag, pagecount, total, division, loc,
			    frm_challan, to_challan, dispatchType, prodtype, session, challan, show_amount, footer_ind,
			    dsp_transporter, ship_doc_no, date, cases, weigh, dsp_challan_msg, remark, isOldInvoice));

		} catch (DocumentException e) {
		    throw e;
		}
		return bodytable;
	    }


	private PdfPTable createFooter(HashMap<String, String> printLabel, boolean isFooterData, boolean pageflag,
		    int pagecount, BigDecimal total, String division, String loc, String frm_challan, String to_challan,
		    String dispatchType, String prodtype, HttpSession session, List<NOV_BillOfSupply_Challan> challan,
		    String show_amount, boolean footer_ind, String dsp_transporter, String ship_doc_no, String date,
		    String cases, String weigh, String dsp_challan_msg, String remark, boolean isOldInvoice)
		    throws DocumentException {
		PdfPTable maintable = new PdfPTable(3);

		System.out.println("challan size() " + challan.size());
		try {
		    // for (ViewPrePrintedDetailChallan obj : challan) {
		    PdfPCell cell = null;

		    maintable.setWidthPercentage(100);

		    cell = new PdfPCell();

		    maintable.setWidths(new float[] { 6f, 2f, 2f });

		    List<Location> narrration1 = locationrepository.getLocationNarrationBylocId(loc.toString());
		    String narrrationByLoc=narrration1.get(0).getLoc_narration();
		    System.out.println(narrrationByLoc);
		    String narrration = "";
		    if(compCode.equalsIgnoreCase("NIL") || compCode.equalsIgnoreCase("NHP")) {
			     narrration = "For onward supply to Doctors/Hospital only.";
		    } else {
		    	
			     narrration = "Samples has no commercial value." + "For use of Medical Profession and Not for Sale ";
		    }
		    PdfPCell hcell1 = null;
		    if (isOldInvoice) {
			hcell1 = new PdfPCell(PdfStyle.createLabelCellWithBorderSmallFontNoBold(
				narrrationByLoc.length() > 261 ? narrrationByLoc.substring(0, 261) : narrrationByLoc));
		    } else {
			hcell1 = new PdfPCell(PdfStyle.createLabelCellWithBorderSmallFontNoBold(
				narrration.length() > 261 ? narrration.substring(0, 261) : narrration));
		    }
		    hcell1.setHorizontalAlignment(Element.ALIGN_LEFT);
		    hcell1.setMinimumHeight(36f);
		    PdfPCell hcell2 = null;
		   
		    if(footer_ind || compCode.equalsIgnoreCase("NIL") ){
		    	System.out.println("Inside NHP"+show_amount);
		    	if (show_amount.equals("Y")) {
					hcell2 = new PdfPCell(PdfStyle.createLabelCellWithBorderSmallFont("Total(Rs.)"));
				    } else {
					hcell2 = new PdfPCell(PdfStyle.createLabelCellWithBorderSmallFont("Value(Rs.)"));
				    }
		    }else{
		    	hcell2 = new PdfPCell(PdfStyle.createLabelCellWithBorderSmallFont(""));
		    }
		    hcell2.setHorizontalAlignment(Element.ALIGN_LEFT);
		    hcell2.setMinimumHeight(36f);
		    PdfPCell hcell3 = null;
		   if(footer_ind || compCode.equalsIgnoreCase("NIL"))
		   {
			   if (show_amount.equals("Y")) {
					hcell3 = new PdfPCell(
						PdfStyle.createLabelCellWithBorderSmallFont("" + total.setScale(2, BigDecimal.ROUND_HALF_UP)));
					hcell3.setHorizontalAlignment(Element.ALIGN_RIGHT);
					hcell3.setMinimumHeight(36f);
				    } else {
					hcell3 = new PdfPCell(
						PdfStyle.createLabelCellWithBorderSmallFont(" " + total.setScale(2, BigDecimal.ROUND_HALF_UP)));
					hcell3.setHorizontalAlignment(Element.ALIGN_LEFT);
					hcell3.setMinimumHeight(36f);
				    }
		   }else{
			   hcell3 = new PdfPCell(
						PdfStyle.createLabelCellWithBorderSmallFont(""));
					hcell3.setHorizontalAlignment(Element.ALIGN_RIGHT);
					hcell3.setMinimumHeight(36f);
		   }

		    maintable.addCell(hcell1);
		    maintable.addCell(hcell2);
		    maintable.addCell(hcell3);

		    // =====================================end of
		    // total====================================================

		    PdfPTable childTbl1 = new PdfPTable(4);
		    childTbl1.setWidths(new float[] { 2f, 2f, 2f, 2f });

		    // ------------------------------------------------------------------------------------------------------
		    PdfPCell hcell_t1 = new PdfPCell(PdfStyle.createLabelCellWithoutBorderSmallFont("Transporter Detail :"));
		    hcell_t1.setHorizontalAlignment(Element.ALIGN_LEFT);

		    PdfPCell hcell_t2 = new PdfPCell(PdfStyle.createLabelCellWithoutBorderSmallFontNoBold(""));
		    hcell_t2.setHorizontalAlignment(Element.ALIGN_LEFT);

		    PdfPCell hcell_t3 = new PdfPCell(PdfStyle.createLabelCellWithoutBorderSmallFont("E Way Bill No :"));
		    hcell_t3.setHorizontalAlignment(Element.ALIGN_LEFT);

		    PdfPCell hcell_t_2 = new PdfPCell(PdfStyle.createLabelCellWithoutBorderSmallFontNoBold(""));
		    hcell_t_2.setHorizontalAlignment(Element.ALIGN_LEFT);

		    PdfPCell hcell_t4 = new PdfPCell(PdfStyle.createLabelCellWithoutBorderSmallFont("Name :"));
		    hcell_t4.setHorizontalAlignment(Element.ALIGN_LEFT);

		    PdfPCell hcell_t5 = new PdfPCell(PdfStyle.createLabelCellWithoutBorderSmallFontNoBold(dsp_transporter));
		    hcell_t5.setHorizontalAlignment(Element.ALIGN_LEFT);

		    PdfPCell hcell_t6 = new PdfPCell(PdfStyle.createLabelCellWithoutBorderSmallFont("No :"));
		    hcell_t6.setHorizontalAlignment(Element.ALIGN_LEFT);

		    PdfPCell hcell_t7 = new PdfPCell(PdfStyle.createLabelCellWithoutBorderSmallFontNoBold(" "));
		    hcell_t7.setHorizontalAlignment(Element.ALIGN_LEFT);

		    PdfPCell hcell_t8 = new PdfPCell(PdfStyle.createLabelCellWithoutBorderSmallFont("LR No. "));
		    hcell_t8.setHorizontalAlignment(Element.ALIGN_LEFT);

		    PdfPCell hcell_t9 = new PdfPCell(PdfStyle.createLabelCellWithoutBorderSmallFontNoBold(ship_doc_no));
		    hcell_t9.setHorizontalAlignment(Element.ALIGN_LEFT);

		    PdfPCell hcell_t10 = new PdfPCell(PdfStyle.createLabelCellWithoutBorderSmallFont("Date : "));
		    hcell_t10.setHorizontalAlignment(Element.ALIGN_LEFT);

		    PdfPCell hcell_t11 = new PdfPCell(PdfStyle.createLabelCellWithoutBorderSmallFontNoBold(" "));
		    hcell_t11.setHorizontalAlignment(Element.ALIGN_LEFT);

		    PdfPCell hcell_t_10 = new PdfPCell(PdfStyle.createLabelCellWithoutBorderSmallFont("Date : "));
		    hcell_t_10.setHorizontalAlignment(Element.ALIGN_LEFT);

		    PdfPCell hcell_t_11 = new PdfPCell(
			    PdfStyle.createLabelCellWithoutBorderSmallFontNoBold(date != null ? date : ""));
		    hcell_t_11.setHorizontalAlignment(Element.ALIGN_LEFT);

		    PdfPCell hcell_t12 = new PdfPCell(PdfStyle.createLabelCellWithoutBorderSmallFont("Number of Boxes :  "));
		    hcell_t12.setHorizontalAlignment(Element.ALIGN_LEFT);

		    PdfPCell hcell_t13 = new PdfPCell(PdfStyle.createLabelCellWithoutBorderSmallFontNoBold(cases));
		    hcell_t13.setHorizontalAlignment(Element.ALIGN_LEFT);

		    PdfPCell hcell_t14 = new PdfPCell(PdfStyle.createLabelCellWithoutBorderSmallFontNoBold(" "));
		    hcell_t14.setHorizontalAlignment(Element.ALIGN_LEFT);

		    PdfPCell hcell_t15 = new PdfPCell(PdfStyle.createLabelCellWithoutBorderSmallFontNoBold(" "));
		    hcell_t15.setHorizontalAlignment(Element.ALIGN_LEFT);
		    DecimalFormat format = new DecimalFormat("0.#");
		    PdfPCell hcell_t16 = new PdfPCell(PdfStyle.createLabelCellWithoutBorderSmallFont("Weight: "));

		    hcell_t16.setHorizontalAlignment(Element.ALIGN_LEFT);

		    PdfPCell hcell_t17 = new PdfPCell(PdfStyle
			    .createLabelCellWithoutBorderSmallFontNoBold(format.format(Double.parseDouble(weigh)).toString()));
		    hcell_t17.setHorizontalAlignment(Element.ALIGN_LEFT);

		    PdfPCell hcell_t18 = new PdfPCell(PdfStyle.createLabelCellWithoutBorderSmallFont("Remark : "));
		    hcell_t18.setHorizontalAlignment(Element.ALIGN_LEFT);

		    PdfPCell hcell_t19 = new PdfPCell(PdfStyle.createLabelCellWithoutBorderSmallFontNoBold(remark));
		    hcell_t19.setHorizontalAlignment(Element.ALIGN_LEFT);
		    hcell_t19.setColspan(3);
		    /*
		     * PdfPCell hcell_t20=new
		     * PdfPCell(PdfStyle.createLabelCellWithoutBorderSmallFontNoBold("")
		     * ); hcell_t19.setHorizontalAlignment(Element.ALIGN_LEFT); PdfPCell
		     * hcell_t21=new
		     * PdfPCell(PdfStyle.createLabelCellWithoutBorderSmallFontNoBold("")
		     * ); hcell_t19.setHorizontalAlignment(Element.ALIGN_LEFT);
		     */

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
		    childTbl1.addCell(hcell_t19);
		    // childTbl1.addCell(hcell_t20);
		    // childTbl1.addCell(hcell_t21);

		    PdfPCell hcell4 = new PdfPCell(childTbl1);// left side of footer
		    // hcell4.setBorder(Rectangle.NO_BORDER);

		    PdfPTable childTbl2 = new PdfPTable(1);
		    // childTbl2.setWidths(new float[] { 10f });
		    String companyName = ((Company) session.getServletContext().getAttribute(COMPANY_DATA)).getCompany_name();
		    PdfPCell hcell_childTbl2_t1 = null;
		    if (footer_ind) {
			hcell_childTbl2_t1 = new PdfPCell(
				PdfStyle.createLabelCellWithoutBorderSmallFont("For " + companyName));
		    } else {
			hcell_childTbl2_t1 = new PdfPCell(PdfStyle.createLabelCellWithoutBorderSmallFont(""));
		    }
		    hcell_childTbl2_t1.enableBorderSide(Rectangle.BOTTOM);
		    hcell_childTbl2_t1.enableBorderSide(Rectangle.LEFT);
		    hcell_childTbl2_t1.enableBorderSide(Rectangle.RIGHT);
		    hcell_childTbl2_t1.setHorizontalAlignment(Element.ALIGN_CENTER);

		    PdfPCell hcell_childTbl2_t2 = new PdfPCell(PdfStyle.createLabelCellWithoutBorderSmallFontNoBold(""));
		    hcell_childTbl2_t2.enableBorderSide(Rectangle.LEFT);
		    hcell_childTbl2_t2.enableBorderSide(Rectangle.RIGHT);
		    hcell_childTbl2_t2.setMinimumHeight(45f);

		    PdfPCell hcell_childTbl2_t3 = new PdfPCell(PdfStyle.createLabelCellWithoutBorderSmallFontNoBold(""));
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
				PdfStyle.createLabelCellWithoutBorderSmallFont("Authorized Signatory"));
		    } else {
			hcell_childTbl3_t2 = new PdfPCell(PdfStyle.createLabelCellWithoutBorderSmallFont(""));
		    }

		    hcell_childTbl3_t2.enableBorderSide(Rectangle.RIGHT);
		    hcell_childTbl3_t2.enableBorderSide(Rectangle.BOTTOM);
		    hcell_childTbl3_t2.enableBorderSide(Rectangle.TOP);
		    hcell_childTbl3_t2.enableBorderSide(Rectangle.LEFT);

		    PdfPCell hcell_childTbl3_t4 = new PdfPCell(PdfStyle.createLabelCellWithoutBorderSmallFontNoBold(""));
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

		    PdfPCell hcell5 = new PdfPCell(childTbl2); // right side of footer
		    hcell5.setBorder(Rectangle.NO_BORDER);
		    hcell5.setColspan(2);
		    hcell5.setHorizontalAlignment(Element.ALIGN_CENTER);

		    Font font = new Font(FontFamily.HELVETICA, 8, Font.BOLD, BaseColor.BLACK);

		    // Print changes made for novartis
		    // added boxes in copies description
		    Paragraph p = null;
		    PdfPCell hcell6 = null;
		    if (compCode.equalsIgnoreCase("NIL")) {
			p = new Paragraph();
			Font checkBoxFont = new Font(Font.FontFamily.ZAPFDINGBATS, 14);
			Chunk checkBox = new Chunk("o", checkBoxFont);
			p.add(new Phrase("Copies : ", new Font(Font.FontFamily.TIMES_ROMAN, 7, Font.BOLD)));
			p.add(checkBox);
			p.add(new Phrase(" :ORIGINAL FOR RECIPIENT.  ", new Font(Font.FontFamily.TIMES_ROMAN, 7, Font.BOLD)));
			p.add(checkBox);
			p.add(new Phrase(" :DUPLICATE FOR TRANSPORTER.  ",
				new Font(Font.FontFamily.TIMES_ROMAN, 7, Font.BOLD)));
			p.add(checkBox);
			p.add(new Phrase(" :TRIPLICATE FOR SUPPLIER.  ", new Font(Font.FontFamily.TIMES_ROMAN, 7, Font.BOLD)));
			p.add(checkBox);
			p.add(new Phrase(" :QUADRUPLICATE:  BOOK COPY  ", new Font(Font.FontFamily.TIMES_ROMAN, 7, Font.BOLD)));
			hcell6 = new PdfPCell(PdfStyle.createUltimateCellWithBorderWithDisableBorder(p, Element.ALIGN_CENTER,
				Element.ALIGN_TOP, 9, 14f, true, 3, 1, true, true, false, false, false));
		    } else {
			p = new Paragraph(
				"Copies      1: Original for consignee   2. Duplicate for transporter    3. Triplicate for consignor     4. Quadruplicate:  Book Copy",
				font);
			hcell6 = new PdfPCell(p);
			hcell6.setColspan(3);
			hcell6.setHorizontalAlignment(Element.ALIGN_CENTER);
			hcell6.setVerticalAlignment(Rectangle.ALIGN_MIDDLE);
			hcell6.setFixedHeight(14f);
			// last line in footer
		    }

		    maintable.addCell(hcell4);
		    maintable.addCell(hcell5);
		    if (!isOldInvoice)
			maintable.addCell(hcell6);

		    // ---------------------------------------------------Remark
		    // :-------------------------------------------------------------

		    /*
		     * PdfPCell hcell1_remark0 = new PdfPCell(
		     * PdfStyle.createLabelCellWithoutBorderSmallFontBold("Remark :"));
		     * 
		     * hcell1_remark0.setHorizontalAlignment(Element.ALIGN_LEFT);
		     */

		    PdfPCell hcell1_remark = new PdfPCell(
			    PdfStyle.createLabelCellWithoutBorderSmallFontNoBold(dsp_challan_msg));

		    hcell1_remark.setHorizontalAlignment(Element.ALIGN_LEFT);
		    // hcell1_remark.setMinimumHeight(36f);

		    PdfPTable childTbl4 = new PdfPTable(2);
		    childTbl4.setWidths(new float[] { 1f, 9f });
		    /* childTbl4.addCell(hcell1_remark0); */
		    childTbl4.addCell(hcell1_remark);

		    PdfPCell hcell_childTbl2_t5 = new PdfPCell(childTbl4);
		    hcell_childTbl2_t5.setBorder(Rectangle.NO_BORDER);
		    hcell_childTbl2_t5.setHorizontalAlignment(Element.ALIGN_LEFT);
		    hcell_childTbl2_t5.setColspan(3);

		    maintable.addCell(hcell_childTbl2_t5);

		    // -----------------------------------------------------------------------------------------------------------------

		    if (pageflag) {
			System.out.println("pagecount : " + pagecount);
			cell = new PdfPCell(PdfStyle.createLabelCellWithoutBorderSmallFontNoBold("Page No. " + pagecount));
			cell.setBorder(Rectangle.NO_BORDER);
			cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
			cell.setVerticalAlignment(Element.ALIGN_BOTTOM);
			cell.setColspan(3);
			maintable.addCell(cell);

		    }
		}catch(Exception e) {
			System.out.println("Error Occurred :" +e.getMessage());// uncomment asneeded --;
		}
		return maintable;
	
	}
}
