package com.excel.service;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
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

import com.excel.configuration.JwtProvider;
import com.excel.model.Company;
import com.excel.model.PrePrintedDetailChallan_withgst;
import com.excel.model.PrePrintedDetailChallan_withgstPG;
import com.excel.model.ViewPrePrintedDetailChallan;
import com.excel.repository.UserMasterRepository;
import com.excel.utility.CodifyErrors;
import com.excel.utility.FooterWithoutPageNo;
import com.excel.utility.MedicoConstants;
import com.excel.utility.PdfStyle;
import com.excel.utility.Utility;
import com.itextpdf.text.BadElementException;
import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Chunk;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.Font.FontFamily;
import com.itextpdf.text.Image;
import com.itextpdf.text.ListItem;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.Rectangle;
import com.itextpdf.text.Section;
import com.itextpdf.text.pdf.ColumnText;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import com.itextpdf.text.pdf.draw.VerticalPositionMark;

@Service
public class PreprintedDetailedChallanServiceImpl implements PreprintedDetailedChallanService, MedicoConstants {

	@Autowired
	PrintService printService;
	@Autowired
	private UserMasterRepository userMasterRepository;
	@Autowired
	private UserMasterService usermasterservice;

	@Autowired
	private JwtProvider tokenProvider;
	@Autowired
	private UserMasterService userMasterService;

	PrePrintedDetailChallan_withgst footer;
	PrePrintedDetailChallan_withgstPG footerPG;
	ViewPrePrintedDetailChallan footerNoGst;
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
	private String comp_cd;
	private int limit = 7;
	int count;
	String docType = null;
	// private String igstTot;

	private String company_name;
	float[] bodyTableWidth = new float[] { 4.5f, 1f, 1.5f, 1f, 1f, 1f };

	@Override
	public String getPreprintedDetailedChallanPrint(Integer division, Integer loc, String frm_challan,
			String to_challan, String dispatchType, String prodtype, String printtype,
			List<PrePrintedDetailChallan_withgst> challans, String show_amount, String footer_signature_ind,
			String companyCode, String companyName, HttpSession session, HttpServletRequest request) throws Exception {

		int count = 1;
		float[] bodyTableWidth;
		if (companyCode.equals("P&G")) {
			bodyTableWidth = new float[] { 4.5f, 1f, 1.5f, 1f, 1f, 1f, 1f };
		} else if (companyCode.equals("PAL")) {
			bodyTableWidth = new float[] { 0.5f, 1f, 4f, 1f, 1f, 1f, 1f, 1f, 1f, 1f, 1f };
		} else {
			if (show_amount.equals("N")) {
				bodyTableWidth = new float[] { 5f, 1f, 1.5f, 1f, 1.5f };
			} else {
				bodyTableWidth = new float[] { 4.5f, 1f, 1.5f, 1f, 1f, 1f };
			}
		}
		System.out.println("In get pdf ani");
		String path = MedicoConstants.PDF_FILE_PATH;
		File file1 = new File(path);
		file1.mkdirs();
		comp_cd = companyCode;

		company_name = companyName;
		setPath(path);

		System.out.println("PDF Generation Started********************************!");
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
		Rectangle rect = new Rectangle(20, 28, 580, 820);
		writer.setBoxSize("header", rect);
		rect = new Rectangle(20, 2, 580, 242);
		writer.setBoxSize("footer", rect);
		int pagecount = 1;
		FooterWithoutPageNo event = new FooterWithoutPageNo();
		writer.setPageEvent(event);
		String uname = this.tokenProvider.getUsernameFromRequest(request);
		String empId = this.userMasterService.getEmpIdByUsername(uname);
		writer = usermasterservice.generatePDFlock(empId, writer);
		document.open();
		document.setMarginMirroringTopBottom(false);
		boolean isnew = false;
		// boolean for_new_page = false;
		ColumnText column = new ColumnText(writer.getDirectContent());
		try {
			BigDecimal total = new BigDecimal(0);

			Set<String> challanset = new LinkedHashSet<String>();
			for (PrePrintedDetailChallan_withgst challan : challans) {
				challanset.add(challan.getDsp_id().toString());
			}
			PdfPTable bodytable = null;
			boolean footer_ind = false;
			String oldGstDoctype = "";
			for (String num : challanset) {
				List<PrePrintedDetailChallan_withgst> list_new_page = new ArrayList<PrePrintedDetailChallan_withgst>();
				isnew = true;
				// for_new_page=true;
				PdfPTable maintable1 = new PdfPTable(1);
				maintable1.setWidthPercentage(100);
				PdfPCell cell = null;
				pageflag = false;
				pagecount = 1;

				BigDecimal goods_total = BigDecimal.ZERO;
				BigDecimal final_total = BigDecimal.ZERO;
				BigDecimal header_Igst = BigDecimal.ZERO;
				BigDecimal header_Cgst = BigDecimal.ZERO;
				BigDecimal header_Sgst = BigDecimal.ZERO;
				BigDecimal round_off = BigDecimal.ZERO;

				PdfPTable hcell = null;
				int rows = 0;
				if (companyCode.equals("P&G")) {
					bodytable = new PdfPTable(7);
				} else if (companyCode.equals("PAL")) {
					bodytable = new PdfPTable(11);
				} else {
					if (show_amount.equals("N")) {
						bodytable = new PdfPTable(5);
					} else {
						bodytable = new PdfPTable(6);
					}
				}
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
				String gst_doc_type = "";

				String dsp_lorry_no = "";

				BigDecimal igstRate = BigDecimal.ZERO;
				BigDecimal igstAmounts = BigDecimal.ZERO;
				// int count_var=0;
				String newChallanNo;
				for (PrePrintedDetailChallan_withgst challan : challans) {

					String oldChallan = challan.getDsp_challan_no();

					newChallanNo = "";
					if (!oldChallan.equals(newChallanNo) && newChallanNo.equals(" ")) {
						count = 0;

					}
					// System.out.println("Alloc Remark
					// "+challan.getAlloc_remark());

					if (num.equalsIgnoreCase(challan.getDsp_id().toString())) {
						docType = challan.getGst_doc_type();
						if (rows >= limit) {
							// count_var++;

							if (footer_signature_ind.equalsIgnoreCase("Y")) {
								footer_ind = false;// suppress footer signature
							} else {
								footer_ind = true;// show footer signature
							}
							System.out.println("getDoc_no  " + challan.getDsp_challan_no());
							pageflag = true;

							bodytable = newPage(column, bodytable, event, printLabel, false, rows, pageflag, pagecount,
									total, division, loc, frm_challan, to_challan, dispatchType, prodtype, path,
									challans, show_amount, footer_ind, challan.getDsp_transporter(),
									challan.getShip_doc_no(), challan.getShip_doc_date(), challan.getDsp_cases(),
									challan.getWeigh().toString(),
									challan.getAlloc_remark().length() > 600
											? challan.getAlloc_remark().substring(0, 600)
											: challan.getAlloc_remark(),
									goods_total, final_total, header_Igst, header_Cgst, header_Sgst, round_off,
									gst_doc_type, dsp_lorry_no, bodyTableWidth, companyCode, igstAmounts,
									oldGstDoctype);

							document.newPage();
							pagecount++;
							rows = 0;

						}
						if (isnew) {
							footer = challan;
							maintable1.addCell(PdfStyle.createLabelCellWithoutBorderSmallFontNoBold(""));
							hcell = createHeader(challan, pagecount, division, loc, frm_challan, to_challan,
									dispatchType, prodtype, path, show_amount, comp_cd, companyName, session,
									bodyTableWidth, companyCode);
							cell = new PdfPCell(hcell);
							cell.setBorder(0);
							cell.setHorizontalAlignment(Element.ALIGN_CENTER);
							maintable1.addCell(cell);
							event.setHeader(maintable1);
							isnew = false;
							list_new_page.add(challan);
						}

						if (rows == 0) { // changes made for unichem testing
							// need to recheck 2 aug 2017
							if (show_amount.equals("Y")) {
								bodytable.addCell(PdfStyle.createValueCellWithHeight((""), false, true, 18f));
								bodytable.addCell(PdfStyle.createValueCellWithHeight((""), false, true, 18f));
								bodytable.addCell(PdfStyle.createValueCellWithHeight((""), false, true, 18f));
								bodytable.addCell(PdfStyle.createValueCellWithHeight((""), true, true, 18f));
								bodytable.addCell(PdfStyle.createValueCellWithHeight((""), true, true, 18f));
								bodytable.addCell(PdfStyle.createValueCellWithHeight((""), true, true, 18f));
							} else {
								bodytable.addCell(PdfStyle.createValueCellWithHeight((""), false, true, 18f));
								bodytable.addCell(PdfStyle.createValueCellWithHeight((""), false, true, 18f));
								bodytable.addCell(PdfStyle.createValueCellWithHeight((""), false, true, 18f));
								bodytable.addCell(PdfStyle.createValueCellWithHeight((""), true, true, 18f));
								bodytable.addCell(PdfStyle.createValueCellWithHeight((""), true, true, 18f));
							}
							if (companyCode.equals("P&G")) {
								bodytable.addCell(PdfStyle.createValueCellWithHeight((""), true, true, 18f));
							}
							if (companyCode.equals("PAL")) {
								bodytable.addCell(PdfStyle.createValueCellWithHeight((""), false, true, 18f));
								bodytable.addCell(PdfStyle.createValueCellWithHeight((""), false, true, 18f));
								bodytable.addCell(PdfStyle.createValueCellWithHeight((""), false, true, 18f));
								bodytable.addCell(PdfStyle.createValueCellWithHeight((""), true, true, 18f));
								bodytable.addCell(PdfStyle.createValueCellWithHeight((""), true, true, 18f));
							}

						} /// if for palson

						if (companyCode.equalsIgnoreCase("PAL")) {

							bodytable.addCell(PdfStyle.createUltimateCellWithBorderWithDisableBorder((count + ""),
									Element.ALIGN_CENTER, Element.ALIGN_TOP, 8, 18f, true, 1, 1, false, false, false,
									true, false));

							bodytable.addCell(PdfStyle.createUltimateCellWithBorderWithDisableBorder(
									(challan.getSmp_prod_cd()), Element.ALIGN_CENTER, Element.ALIGN_TOP, 8, 18f, true,
									1, 1, false, false, false, true, false));

							bodytable.addCell(PdfStyle.createUltimateCellWithBorderWithDisableBorder(
									(challan.getProd_name_pack()), Element.ALIGN_LEFT, Element.ALIGN_TOP, 8, 18f, true,
									1, 1, false, false, false, true, false));

							bodytable.addCell(PdfStyle.createUltimateCellWithBorderWithDisableBorder(
									(challan.getPack_disp_nm()), Element.ALIGN_RIGHT, Element.ALIGN_TOP, 8, 18f, true,
									1, 1, false, false, false, true, false));

							bodytable.addCell(PdfStyle.createUltimateCellWithBorderWithDisableBorder(
									(challan.getUom_disp_nm()), Element.ALIGN_LEFT, Element.ALIGN_TOP, 8, 18f, true, 1,
									1, false, false, false, true, false));

							bodytable.addCell(PdfStyle.createUltimateCellWithBorderWithDisableBorder(
									(challan.getHsn_code()), Element.ALIGN_LEFT, Element.ALIGN_TOP, 8, 18f, true, 1, 1,
									false, false, false, true, false));

							bodytable.addCell(PdfStyle.createUltimateCellWithBorderWithDisableBorder(
									(challan.getBatch_no()), Element.ALIGN_LEFT, Element.ALIGN_TOP, 8, 18f, true, 1, 1,
									false, false, false, true, false));

							bodytable.addCell(PdfStyle.createUltimateCellWithBorderWithDisableBorder(
									(challan.getBatch_expiry_dt()), Element.ALIGN_CENTER, Element.ALIGN_TOP, 8, 18f,
									true, 1, 1, false, false, false, true, false));

							bodytable
									.addCell(
											PdfStyle.createUltimateCellWithBorderWithDisableBorder(
													(challan.getDspdtl_rate() == null
															? BigDecimal.ZERO.setScale(2, RoundingMode.HALF_UP)
															: challan.getDspdtl_rate().setScale(2,
																	RoundingMode.HALF_UP))
															.toString(),
													Element.ALIGN_RIGHT, Element.ALIGN_TOP, 8, 18f, true, 1, 1, false,
													false, false, true, false));

							bodytable.addCell(PdfStyle.createUltimateCellWithBorderWithDisableBorder(
									(challan.getDspdtl_disp_qty().toString()), Element.ALIGN_RIGHT, Element.ALIGN_TOP,
									8, 18f, true, 1, 1, false, false, false, true, false));

							bodytable.addCell(PdfStyle.createUltimateCellWithBorderWithDisableBorder(
									("0.00").toString(), Element.ALIGN_RIGHT, Element.ALIGN_TOP, 8, 18f, true, 1, 1,
									false, false, false, true, false));
							count++;

						} else {
							bodytable.addCell(PdfStyle.createUltimateCellWithBorderWithDisableBorder(
									(challan.getProd_name_pack()), Element.ALIGN_LEFT, Element.ALIGN_TOP, 8, 18f, false,
									1, 1, false, false, false, true, false));

							bodytable.addCell(PdfStyle.createUltimateCellWithBorderWithDisableBorder(
									(challan.getHsn_code()), Element.ALIGN_LEFT, Element.ALIGN_TOP, 8, 18f, false, 1, 1,
									false, false, false, true, false));

							bodytable.addCell(PdfStyle.createUltimateCellWithBorderWithDisableBorder(
									(challan.getBatch_no()), Element.ALIGN_LEFT, Element.ALIGN_TOP, 8, 18f, false, 1, 1,
									false, false, false, true, false));

							bodytable.addCell(PdfStyle.createUltimateCellWithBorderWithDisableBorder(
									(challan.getBatch_expiry_dt()), Element.ALIGN_CENTER, Element.ALIGN_TOP, 8, 18f,
									false, 1, 1, false, false, false, true, false));

							bodytable.addCell(PdfStyle.createUltimateCellWithBorderWithDisableBorder(
									(challan.getDspdtl_disp_qty().toString()), Element.ALIGN_CENTER, Element.ALIGN_TOP,
									8, 18f, false, 1, 1, false, false, false, true, false));

							if (companyCode.equals("P&G")) {
								bodytable
										.addCell(
												PdfStyle.createUltimateCellWithBorderWithDisableBorder(
														(challan.getDspdtl_rate() == null
																? BigDecimal.ZERO.setScale(2, RoundingMode.HALF_UP)
																: challan.getDspdtl_rate().setScale(2,
																		RoundingMode.HALF_UP))
																.toString(),
														Element.ALIGN_RIGHT, Element.ALIGN_TOP, 8, 18f, false, 1, 1,
														false, false, false, true, false));
							} else if (companyCode.equals("PAL")) {
								bodytable.addCell(PdfStyle.createUltimateCellWithBorderWithDisableBorder(
										("0.0").toString(), Element.ALIGN_RIGHT, Element.ALIGN_TOP, 8, 18f, false, 1, 1,
										false, false, false, true, false));
							} else {
								if (show_amount.equals("Y")) {
									bodytable
											.addCell(
													PdfStyle.createUltimateCellWithBorderWithDisableBorder(
															(challan.getDisp_value() == null
																	? BigDecimal.ZERO.setScale(2, RoundingMode.HALF_UP)
																	: challan.getDisp_value().setScale(2,
																			RoundingMode.HALF_UP))
																	.toString(),
															Element.ALIGN_RIGHT, Element.ALIGN_TOP, 8, 18f, false, 1, 1,
															false, false, false, true, false));
								}
							}
							if (companyCode.equals("P&G")) {
								bodytable.addCell(PdfStyle.createUltimateCellWithBorderWithDisableBorder(
										("0.0").toString(), Element.ALIGN_RIGHT, Element.ALIGN_TOP, 8, 18f, false, 1, 1,
										false, false, false, true, true));
							}
						}

						if (docType.contains("008")) {

							bodytable.addCell(PdfStyle.createUltimateCellWithBorderWithDisableBorder(("IGST% "),
									Element.ALIGN_RIGHT, Element.ALIGN_TOP, 8, 18f, true, 1, 1, false, false, false,
									true, false));

							if (companyCode.equalsIgnoreCase("P&G")) {
								bodytable.addCell(PdfStyle.createUltimateCellWithBorderWithDisableBorder("0.00",
										Element.ALIGN_RIGHT, Element.ALIGN_TOP, 8, 18f, true, 1, 1, false, false, false,
										true, false));
							} else {
								bodytable.addCell(PdfStyle.createUltimateCellWithBorderWithDisableBorder(
										(challan.getIgst_rate()).toString(), Element.ALIGN_RIGHT, Element.ALIGN_TOP, 8,
										18f, true, 1, 1, false, false, false, true, false));
							}

							bodytable.addCell(PdfStyle.createUltimateCellWithBorderWithDisableBorder(("IGST"),
									Element.ALIGN_RIGHT, Element.ALIGN_TOP, 8, 18f, true, 1, 1, false, false, false,
									true, false));

							if (companyCode.equals("P&G")) {
								bodytable.addCell(PdfStyle.createUltimateCellWithBorderWithDisableBorder("0.00",
										Element.ALIGN_RIGHT, Element.ALIGN_TOP, 8, 18f, true, 1, 1, false, false, false,
										true, false));
							} else {
								bodytable
										.addCell(
												PdfStyle.createUltimateCellWithBorderWithDisableBorder(
														(challan.getIgst_bill_amt() == null
																? BigDecimal.ZERO.setScale(2, RoundingMode.HALF_UP)
																: challan.getIgst_bill_amt().setScale(2,
																		RoundingMode.HALF_UP))
																.toPlainString(),
														Element.ALIGN_RIGHT, Element.ALIGN_TOP, 8, 18f, true, 1, 1,
														false, false, false, true, false));

							}

							bodytable.addCell(
									PdfStyle.createUltimateCellWithBorderWithDisableBorder((""), Element.ALIGN_LEFT,
											Element.ALIGN_TOP, 8, 18f, true, 1, 1, false, false, false, true, false));
							if (show_amount.equals("Y")) {
								bodytable.addCell(PdfStyle.createUltimateCellWithBorderWithDisableBorder((""),
										Element.ALIGN_LEFT, Element.ALIGN_TOP, 8, 18f, true, 1, 1, false, false, false,
										true, false));
							}
							if (companyCode.equals("P&G")) {

								bodytable.addCell(PdfStyle.createUltimateCellWithBorderWithDisableBorder((""),
										Element.ALIGN_LEFT, Element.ALIGN_TOP, 8, 18f, true, 1, 1, false, false, false,
										true, false));
							}

						} else {
							if (companyCode.equals("P&G")) {

								bodytable.addCell(PdfStyle.createUltimateCellWithBorderWithDisableBorder(
										("CGST% :" + "0.00" + "                                       CGST :" + "0.00"),
										Element.ALIGN_LEFT, Element.ALIGN_TOP, 8, 18f, true, 1, 1, false, false, false,
										true, false));
							} else {

								bodytable
										.addCell(
												PdfStyle.createUltimateCellWithBorderWithDisableBorder(
														("CGST% :" + challan.getCgst_rate()
																+ "                                       CGST :"
																+ challan.getCgst_bill_amt().setScale(2,
																		RoundingMode.HALF_UP)),
														Element.ALIGN_LEFT, Element.ALIGN_TOP, 8, 18f, true, 1, 1,
														false, false, false, true, false));
							}
							System.out.println("challan.getCgst_rate()" + challan.getCgst_rate());

							bodytable.addCell(PdfStyle.createUltimateCellWithBorderWithDisableBorder(("SGST%"),
									Element.ALIGN_RIGHT, Element.ALIGN_TOP, 8, 18f, true, 1, 1, false, false, false,
									true, false));
							if (companyCode.equals("P&G")) {
								bodytable.addCell(PdfStyle.createUltimateCellWithBorderWithDisableBorder("0.00",
										Element.ALIGN_RIGHT, Element.ALIGN_TOP, 8, 18f, true, 1, 1, false, false, false,
										true, false));
							} else {
								bodytable.addCell(PdfStyle.createUltimateCellWithBorderWithDisableBorder(
										(challan.getSgst_rate().toString()), Element.ALIGN_RIGHT, Element.ALIGN_TOP, 8,
										18f, true, 1, 1, false, false, false, true, false));
							}

							bodytable.addCell(PdfStyle.createUltimateCellWithBorderWithDisableBorder(
									("SGST").toString(), Element.ALIGN_RIGHT, Element.ALIGN_TOP, 8, 18f, true, 1, 1,
									false, false, false, true, false));

							if (companyCode.equals("P&G")) {
								bodytable.addCell(PdfStyle.createUltimateCellWithBorderWithDisableBorder(("0.00"),
										Element.ALIGN_RIGHT, Element.ALIGN_TOP, 8, 18f, true, 1, 1, false, false, false,
										true, false));
							} else {
								bodytable.addCell(PdfStyle.createUltimateCellWithBorderWithDisableBorder(
										(challan.getSgst_bill_amt().toString()), Element.ALIGN_RIGHT, Element.ALIGN_TOP,
										8, 18f, true, 1, 1, false, false, false, true, false));
							}

							bodytable.addCell(
									PdfStyle.createUltimateCellWithBorderWithDisableBorder((""), Element.ALIGN_RIGHT,
											Element.ALIGN_TOP, 8, 18f, true, 1, 1, false, false, false, true, false));

							if (companyCode.equals("P&G")) {
								bodytable.addCell(PdfStyle.createUltimateCellWithBorderWithDisableBorder((""),
										Element.ALIGN_RIGHT, Element.ALIGN_TOP, 8, 18f, true, 1, 1, false, false, false,
										true, false));

							}
							if (companyCode.equals("PAL")) {
								bodytable.addCell(PdfStyle.createUltimateCellWithBorderWithDisableBorder((""),
										Element.ALIGN_RIGHT, Element.ALIGN_TOP, 8, 18f, true, 1, 1, false, false, false,
										true, false));

								bodytable.addCell(PdfStyle.createUltimateCellWithBorderWithDisableBorder((""),
										Element.ALIGN_RIGHT, Element.ALIGN_TOP, 8, 18f, true, 1, 1, false, false, false,
										true, false));
								bodytable.addCell(PdfStyle.createUltimateCellWithBorderWithDisableBorder((""),
										Element.ALIGN_RIGHT, Element.ALIGN_TOP, 8, 18f, true, 1, 1, false, false, false,
										true, false));
								bodytable.addCell(PdfStyle.createUltimateCellWithBorderWithDisableBorder((""),
										Element.ALIGN_RIGHT, Element.ALIGN_TOP, 8, 18f, true, 1, 1, false, false, false,
										true, false));

								bodytable.addCell(PdfStyle.createUltimateCellWithBorderWithDisableBorder((""),
										Element.ALIGN_RIGHT, Element.ALIGN_TOP, 8, 18f, true, 1, 1, false, false, false,
										true, false));
							}
						}

						goods_total = goods_total.add(challan.getDisp_value());
						final_total = goods_total.add(challan.getHigst_bill_amt().add(challan.getHcgst_bill_amt()
								.add(challan.getHsgst_bill_amt().add(challan.getRoundoff()))));
						header_Igst = challan.getHigst_bill_amt();
						header_Sgst = challan.getHsgst_bill_amt();
						header_Cgst = challan.getHcgst_bill_amt();
						round_off = challan.getRoundoff();
						gst_doc_type = challan.getGst_description();

						rows++;
						dtp = "";
						ship_doc_no = "";
						date = "";
						cases = "";
						weigh = "";
						dsp_challan_msg = "";
						dsp_lorry_no = "";

						dtp = challan.getDsp_transporter();
						ship_doc_no = challan.getShip_doc_no();
						date = challan.getShip_doc_date();
						cases = challan.getDsp_cases().toString();
						weigh = challan.getWeigh().toString();
						igstRate = challan.getIgst_bill_amt();
						igstAmounts = igstAmounts.add(igstRate);
						System.out.println("igstRate" + igstRate);
						System.out.println("docTYpes  " + challan.getGst_doc_type());
						System.out.println("igstAmounts" + igstAmounts);

						dsp_challan_msg = challan.getAlloc_remark().length() > 600
								? challan.getAlloc_remark().substring(0, 600)
								: challan.getAlloc_remark();
						dsp_lorry_no = challan.getDsp_lorry_no();
						oldGstDoctype = docType;
					}

				}

				footer_ind = true;

				newPage(column, bodytable, event, printLabel, true, rows, pageflag, pagecount, total, division, loc,
						frm_challan, to_challan, dispatchType, prodtype, path, list_new_page, show_amount, footer_ind,
						dtp, ship_doc_no, date, cases, weigh, dsp_challan_msg, goods_total, final_total, header_Igst,
						header_Cgst, header_Sgst, round_off, gst_doc_type, dsp_lorry_no, bodyTableWidth, companyCode,
						igstAmounts, oldGstDoctype);
				document.newPage();

			}

		} catch (Exception e) {
			System.out.println("Error Occurred :" + e.getMessage());// uncomment asneeded --;
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
				System.out.println("Error Occurred :" + e.getMessage());// uncomment asneeded --;
			}
		}
		return filename;
	}

	private PdfPTable createHeader(PrePrintedDetailChallan_withgst challan, int pagecount, int division, Integer loc,
			String frm_challan, String to_challan, String dispatchType, String prodtype, String path,
			String show_amount, String compcd, String companyName, HttpSession session, float[] bodyTableWidth,
			String companyCode) throws Exception {

		System.out.println("create Header *********" + comp_cd);
		PdfPTable headertable = new PdfPTable(1);
		PdfPTable headertable01 = new PdfPTable(1);// Billing Address
		PdfPTable headertable2 = new PdfPTable(4);
		PdfPTable headertable3 = new PdfPTable(4);
		PdfPTable headertable4 = new PdfPTable(4);
		PdfPTable bodytable = null;
		String FOX = "path/to/resource/fox.png";
		headertable.setWidthPercentage(100);
		headertable01.setWidthPercentage(100);
		headertable2.setWidthPercentage(100);
		headertable3.setWidthPercentage(100);
		headertable4.setWidthPercentage(100);

		headertable01.setWidths(new float[] { 7f });
		headertable2.setWidths(new float[] { 4.5f, 2.5f, 1f, 2f });
		headertable3.setWidths(new float[] { 4.5f, 2.5f, 1f, 2f });
		headertable4.setWidths(new float[] { 2f, 2f, 2.5f, 3.5f });

		Font font = new Font(FontFamily.HELVETICA, 13, Font.BOLD, BaseColor.BLACK);
		Font font2 = new Font(FontFamily.HELVETICA, 9, Font.BOLD, BaseColor.BLACK);
		Font font3 = new Font(FontFamily.HELVETICA, 7.7f, Font.BOLD, BaseColor.BLACK);
		PdfPTable headertable1 = new PdfPTable(1);
		headertable1.setWidthPercentage(100);
		headertable1.setWidths(new float[] { 5f });

// ======================================================================================================================
// 1
		PdfPCell hcell = null;

		if (comp_cd.equalsIgnoreCase("PFZ")) {
			hcell = new PdfPCell(new Phrase("Delivery Challan", font));
		} else if (comp_cd.equalsIgnoreCase("UCM")) {
			hcell = new PdfPCell(new Phrase("TAX INVOICE", font));
		} else if (comp_cd.equalsIgnoreCase("NIL") || comp_cd.equalsIgnoreCase("NHP")) {
			hcell = new PdfPCell(new Phrase("TAX INVOICE", font));
			hcell.disableBorderSide(Rectangle.BOTTOM);
		} else {
			hcell = new PdfPCell(new Phrase("Delivery Challan", font));
		}

		hcell.setHorizontalAlignment(Element.ALIGN_CENTER);
		hcell.setVerticalAlignment(Element.ALIGN_CENTER);

		PdfPCell hcell_1 = new PdfPCell(new Phrase(" ", font));
		hcell_1.setBorder(Rectangle.LEFT);
		hcell_1.setHorizontalAlignment(Element.ALIGN_CENTER);
		headertable01.addCell(hcell);
		PdfPCell hcell2 = null;
		if (comp_cd.equalsIgnoreCase("NIL") || comp_cd.equalsIgnoreCase("NHP")) {
			hcell2 = new PdfPCell(new Phrase("(Rule 46 of CGST Rules,2017)", font2));
			hcell2.setHorizontalAlignment(Element.ALIGN_CENTER);
			hcell2.setVerticalAlignment(Element.ALIGN_CENTER);
			hcell2.disableBorderSide(Rectangle.TOP);
			headertable01.addCell(hcell2);
		}

// =================================================Blank Cell Added To
// Remove Table Border==============================

		PdfPCell hcell_blank = new PdfPCell(headertable01);
		hcell_blank.setBorder(Rectangle.RIGHT);
		hcell_blank.disableBorderSide(Rectangle.BOTTOM);

// ======================================================================================================================
// 2.1
//String company_name = (String) context.getAttribute("company_name");

		StringBuilder result1 = new StringBuilder();
		System.out.println("challan.getLocadd1():::" + challan.getLocadd1());
		result1.append(
				challan.getLocadd1().length() > 40 ? challan.getLocadd1().substring(0, 40) : challan.getLocadd1());
		result1.append(System.lineSeparator());

		result1.append(
				challan.getLocadd2().length() > 48 ? challan.getLocadd2().substring(0, 48) : challan.getLocadd2());
		result1.append(System.lineSeparator());

		result1.append(challan.getLocadd3() != null && challan.getLocadd3().length() > 40
				? challan.getLocadd3().substring(0, 40)
				: challan.getLocadd3());
		result1.append(System.lineSeparator());

		result1.append(challan.getLocadd4() != null && challan.getLocadd4().length() > 40
				? challan.getLocadd4().substring(0, 40)
				: challan.getLocadd4());
		// result1.append(System.lineSeparator());

		result1.append(" State :" + challan.getLoc_statename() + "  State Code :" + challan.getLoc_statecode());
		result1.append(System.lineSeparator());

		String comp_name_new = "Pfizer Products India Private Limited.";
		PdfPCell hcell_cn = null;
		if (challan.getDsp_challan_no().contains("PIP")) {
			if (comp_cd.equalsIgnoreCase("PFZ")) {
				hcell_cn = new PdfPCell(PdfStyle.createLabelCellWithBorderLargeFontNoBold(comp_name_new));
			} else {
				hcell_cn = new PdfPCell(PdfStyle.createLabelCellWithBorderLargeFontNoBold(company_name));
			}
			hcell_cn.setHorizontalAlignment(Element.ALIGN_LEFT);
			hcell_cn.setBorder(Rectangle.LEFT);
		} else {
			hcell_cn = new PdfPCell(PdfStyle.createLabelCellWithBorderLargeFontNoBold(companyName));
			hcell_cn.setHorizontalAlignment(Element.ALIGN_LEFT);
			hcell_cn.setBorder(Rectangle.LEFT);
		}

		PdfPCell hcell2w = new PdfPCell(PdfStyle.createLabelCellWithBorderSmallFontNoBoldForHeader(result1.toString()));
		hcell2w.setHorizontalAlignment(Element.ALIGN_LEFT);
		hcell2w.setBorder(Rectangle.LEFT);

		PdfPTable addressTbl_a = new PdfPTable(1);
		addressTbl_a.addCell(hcell_cn);
		addressTbl_a.addCell(hcell2w);

		PdfPCell hcella_4 = new PdfPCell(PdfStyle.createLabelCellWithoutBorderSmallFontNoPad("Drug Lic No.:"));
		hcella_4.setHorizontalAlignment(Element.ALIGN_LEFT);
		hcella_4.setVerticalAlignment(Element.ALIGN_TOP);
		hcella_4.setBorder(Rectangle.LEFT);

		PdfPCell hcella_4_1 = new PdfPCell(
				PdfStyle.createLabelCellWithBorderSmallFontNoBoldForHeader(challan.getLic1()));
		hcella_4_1.setHorizontalAlignment(Element.ALIGN_LEFT);
		hcella_4_1.setVerticalAlignment(Element.ALIGN_TOP);
		hcella_4_1.setBorder(0);

		PdfPCell hcella_7 = new PdfPCell(PdfStyle.createLabelCellWithoutBorderSmallFontNoPad(" "));
		hcella_7.setHorizontalAlignment(Element.ALIGN_LEFT);
		hcella_7.setVerticalAlignment(Element.ALIGN_TOP);
		hcella_7.setBorder(Rectangle.LEFT);

		PdfPCell hcella_4_2 = new PdfPCell(
				PdfStyle.createLabelCellWithBorderSmallFontNoBoldForHeader(challan.getLic2()));
		hcella_4_2.setHorizontalAlignment(Element.ALIGN_LEFT);
		hcella_4_2.setVerticalAlignment(Element.ALIGN_TOP);
		hcella_4_2.setBorder(0);

		PdfPCell hcella_5 = new PdfPCell(PdfStyle.createLabelCellWithoutBorderSmallFontNoPad("GSTIN :"));
		hcella_5.setHorizontalAlignment(Element.ALIGN_LEFT);
		hcella_5.setVerticalAlignment(Element.ALIGN_TOP);
		hcella_5.setBorder(Rectangle.LEFT);

		PdfPCell hcella_4_3 = new PdfPCell(
				PdfStyle.createLabelCellWithBorderSmallFontNoBoldForHeader(challan.getGst_reg_no()));
		hcella_4_3.setHorizontalAlignment(Element.ALIGN_LEFT);
		hcella_4_3.setVerticalAlignment(Element.ALIGN_TOP);
		hcella_4_3.setBorder(0);
		PdfPCell hcella_6;
		if (compcd.trim().equals("P&G")) {
			hcella_6 = new PdfPCell(PdfStyle.createLabelCellWithoutBorderSmallFontNoPad("MSME No.:"));
			hcella_6.setHorizontalAlignment(Element.ALIGN_LEFT);
			hcella_6.setVerticalAlignment(Element.ALIGN_TOP);
			hcella_6.setBorder(Rectangle.LEFT);
		} else {
			hcella_6 = new PdfPCell(PdfStyle.createLabelCellWithoutBorderSmallFontNoPad("CIN :"));
			hcella_6.setHorizontalAlignment(Element.ALIGN_LEFT);
			hcella_6.setVerticalAlignment(Element.ALIGN_TOP);
			hcella_6.setBorder(Rectangle.LEFT);
		}
		PdfPCell hcella_4_4;
		if (compcd.trim().equals("P&G")) {
			hcella_4_4 = new PdfPCell(
					PdfStyle.createLabelCellWithBorderSmallFontNoBoldForHeader(challan.getLoc_cst_no()));
			hcella_4_4.setHorizontalAlignment(Element.ALIGN_LEFT);
			hcella_4_4.setVerticalAlignment(Element.ALIGN_TOP);
			hcella_4_4.setBorder(0);
		} else {
			hcella_4_4 = new PdfPCell(
					PdfStyle.createLabelCellWithBorderSmallFontNoBoldForHeader(challan.getLoc_cinno()));
			hcella_4_4.setHorizontalAlignment(Element.ALIGN_LEFT);
			hcella_4_4.setVerticalAlignment(Element.ALIGN_TOP);
			hcella_4_4.setBorder(0);
		}

		PdfPCell hcella_8 = new PdfPCell(PdfStyle.createLabelCellWithoutBorderSmallFontNoPad("PAN :"));
		hcella_8.setHorizontalAlignment(Element.ALIGN_LEFT);
		hcella_8.setVerticalAlignment(Element.ALIGN_TOP);
		hcella_8.setBorder(Rectangle.LEFT);

		PdfPCell hcella_4_8 = new PdfPCell(
				PdfStyle.createLabelCellWithBorderSmallFontNoBoldForHeader(challan.getDptpan_no()));// PAN
		// no
		hcella_4_8.setHorizontalAlignment(Element.ALIGN_LEFT);
		hcella_4_8.setVerticalAlignment(Element.ALIGN_TOP);
		hcella_4_8.setBorder(0);
		PdfPCell hcella_9 = null;
		if (challan.getLoc_web_site() == null && challan.getLoc_web_site().trim().isEmpty()) {
			hcella_9 = new PdfPCell(PdfStyle.createLabelCellWithoutBorderSmallFontNoPad(" "));
			hcella_9.setHorizontalAlignment(Element.ALIGN_LEFT);
			hcella_9.setVerticalAlignment(Element.ALIGN_TOP);
			hcella_9.setBorder(Rectangle.LEFT);
		} else {
			if (compcd.trim().equals("P&G")) {
				hcella_9 = new PdfPCell(PdfStyle.createLabelCellWithoutBorderSmallFontNoPad(" "));
				hcella_9.setHorizontalAlignment(Element.ALIGN_LEFT);
				hcella_9.setVerticalAlignment(Element.ALIGN_TOP);
				hcella_9.setBorder(Rectangle.LEFT);
			} else {
				hcella_9 = new PdfPCell(PdfStyle.createLabelCellWithoutBorderSmallFontNoPad("Website"));
				hcella_9.setHorizontalAlignment(Element.ALIGN_LEFT);
				hcella_9.setVerticalAlignment(Element.ALIGN_TOP);
				hcella_9.setBorder(Rectangle.LEFT);
			}
		}

		PdfPCell hcella_4_9 = new PdfPCell(
				PdfStyle.createLabelCellWithBorderSmallFontNoBoldForHeader(challan.getLoc_web_site()));
		hcella_4_9.setHorizontalAlignment(Element.ALIGN_LEFT);
		hcella_4_9.setVerticalAlignment(Element.ALIGN_TOP);
		hcella_4_9.setBorder(0);

		PdfPCell hcella_10;

		hcella_10 = new PdfPCell(PdfStyle.createLabelCellWithoutBorderSmallFontNoPad("Tel No:"));
		hcella_10.setHorizontalAlignment(Element.ALIGN_LEFT);
		hcella_10.setVerticalAlignment(Element.ALIGN_TOP);
		hcella_10.setBorder(Rectangle.LEFT);

		PdfPCell hcella_4_10;
		if (compcd.trim().equals("P&G")) {
			hcella_4_10 = new PdfPCell(
					PdfStyle.createLabelCellWithBorderSmallFontNoBoldForHeader(challan.getLoc_mobile_no()));
			hcella_4_10.setHorizontalAlignment(Element.ALIGN_LEFT);
			hcella_4_10.setVerticalAlignment(Element.ALIGN_TOP);
			hcella_4_10.setBorder(0);
		} else {
			hcella_4_10 = new PdfPCell(
					PdfStyle.createLabelCellWithBorderSmallFontNoBoldForHeader(challan.getLoc_telephone_no()));
			hcella_4_10.setHorizontalAlignment(Element.ALIGN_LEFT);
			hcella_4_10.setVerticalAlignment(Element.ALIGN_TOP);
			hcella_4_10.setBorder(0);
		}

		PdfPCell hcella_11 = new PdfPCell(PdfStyle.createLabelCellWithBorderSmallFontNoPad("Email Id: "));
		hcella_11.setHorizontalAlignment(Element.ALIGN_LEFT);
		hcella_11.setVerticalAlignment(Element.ALIGN_TOP);
		hcella_11.disableBorderSide(Rectangle.TOP);
		hcella_11.disableBorderSide(Rectangle.RIGHT);

		PdfPCell hcella_4_11 = new PdfPCell(
				PdfStyle.createLabelCellWithBorderSmallFontNoBoldForHeader(challan.getLoc_emailid()));
		hcella_4_11.setHorizontalAlignment(Element.ALIGN_LEFT);
		hcella_4_11.setVerticalAlignment(Element.ALIGN_TOP);
		hcella_4_11.disableBorderSide(Rectangle.LEFT);
		hcella_4_11.disableBorderSide(Rectangle.TOP);
		hcella_4_11.disableBorderSide(Rectangle.RIGHT);

		PdfPCell cell11 = new PdfPCell(addressTbl_a);
		cell11.setBorder(0);

		PdfPCell hcella_12 = new PdfPCell(PdfStyle.createLabelCellWithBorderSmallFontNoPad("Bill To :"));
		hcella_12.setHorizontalAlignment(Element.ALIGN_LEFT);
		hcella_12.setVerticalAlignment(Element.ALIGN_TOP);
		hcella_12.disableBorderSide(Rectangle.BOTTOM);
		hcella_12.disableBorderSide(Rectangle.RIGHT);

		PdfPCell hcella_4_12 = new PdfPCell(
//PdfStyle.createLabelCellWithBorderSmallFontNoBoldForHeader(challan.getBilltoname1() == null ? "" : challan.getBilltoname1()));
				PdfStyle.createLabelCellWithBorderSmallFontNoBoldForHeader(
						challan.getBilltoname1() == null ? "" : challan.getBilltoname1()));
		hcella_4_12.setHorizontalAlignment(Element.ALIGN_LEFT);
		hcella_4_12.setVerticalAlignment(Element.ALIGN_TOP);
		hcella_4_12.disableBorderSide(Rectangle.BOTTOM);
		hcella_4_12.disableBorderSide(Rectangle.LEFT);
		hcella_4_12.disableBorderSide(Rectangle.RIGHT);

		PdfPCell hcella_13 = new PdfPCell(PdfStyle.createLabelCellWithoutBorderSmallFontNoPad(""));
		hcella_13.setHorizontalAlignment(Element.ALIGN_LEFT);
		hcella_13.setVerticalAlignment(Element.ALIGN_TOP);
		hcella_13.setBorder(Rectangle.LEFT);

		PdfPCell hcella_4_13 = new PdfPCell(
// PdfStyle.createLabelCellWithBorderSmallFontNoBoldForHeader(challan.getBilltoname2() == null ? "" : challan.getBilltoname2()));  //Testing
				PdfStyle.createLabelCellWithBorderSmallFontNoBoldForHeader(
						challan.getBilltoname2() == null ? "" : challan.getBilltoname2()));
		hcella_4_13.setHorizontalAlignment(Element.ALIGN_LEFT);
		hcella_4_13.setVerticalAlignment(Element.ALIGN_TOP);
		hcella_4_13.setBorder(0);

		PdfPCell hcella_14 = new PdfPCell(PdfStyle.createLabelCellWithoutBorderSmallFontNoPad(""));
		hcella_14.setHorizontalAlignment(Element.ALIGN_LEFT);
		hcella_14.setVerticalAlignment(Element.ALIGN_TOP);
		hcella_14.setBorder(Rectangle.LEFT);

		StringBuffer gstNo = new StringBuffer();
		gstNo.append("GSTIN : " + (challan.getBillto_gst_reg_no() == null ? "" : challan.getBillto_gst_reg_no().trim()))
				.append(" State code : " + (challan.getBillto_statecode() == null ? "" : challan.getBillto_statecode()))
				.append(" - " + (challan.getBillto_statename() == null ? "" : challan.getBillto_statename()));
		PdfPCell hcella_4_14 = new PdfPCell(
				PdfStyle.createLabelCellWithBorderSmallFontNoBoldForHeader(gstNo.toString()));
		hcella_4_14.setHorizontalAlignment(Element.ALIGN_LEFT);
		hcella_4_14.setVerticalAlignment(Element.ALIGN_TOP);
		hcella_4_14.setBorder(0);
		// hcella_4_14.setColspan(2);

		PdfPTable addressTbl = new PdfPTable(1);
		PdfPTable addressTbl_b = new PdfPTable(2);
		addressTbl_b.setWidths(new float[] { 1.5f, 8.5f });
//addressTbl_b.setWidths(new float[] { 1.5f, 3f,5.5f });
		addressTbl_b.addCell(hcella_4);
		addressTbl_b.addCell(hcella_4_1);
		addressTbl_b.addCell(hcella_7);
		addressTbl_b.addCell(hcella_4_2);

		addressTbl_b.addCell(hcella_5);
		addressTbl_b.addCell(hcella_4_3);
		addressTbl_b.addCell(hcella_6);
		addressTbl_b.addCell(hcella_4_4);

		if (!comp_cd.trim().equalsIgnoreCase("NIL") && !comp_cd.trim().equalsIgnoreCase("NHP")) {
			addressTbl_b.addCell(hcella_8);
			addressTbl_b.addCell(hcella_4_8);
			addressTbl_b.addCell(hcella_9);
			addressTbl_b.addCell(hcella_4_9);
		}
		addressTbl_b.addCell(hcella_10);
		addressTbl_b.addCell(hcella_4_10);
		addressTbl_b.addCell(hcella_11);
		addressTbl_b.addCell(hcella_4_11);

		if (comp_cd.equalsIgnoreCase("NIL") || comp_cd.equalsIgnoreCase("NHP")) {
			addressTbl_b.addCell(hcella_12);
			addressTbl_b.addCell(hcella_4_12);
			addressTbl_b.addCell(hcella_13);
			addressTbl_b.addCell(hcella_4_13);
			addressTbl_b.addCell(hcella_14);
			addressTbl_b.addCell(hcella_4_14);
		}
		PdfPCell cell12 = new PdfPCell(addressTbl_b);
		cell12.setBorder(0);
		addressTbl.addCell(cell11);
		addressTbl.addCell(cell12);

		PdfPCell cell1 = new PdfPCell(addressTbl);
		cell1.setBorder(0);

		if (comp_cd.trim().equalsIgnoreCase("MDL")) {
			headertable2.addCell(cell1);
//String path=ServletActionContext.getServletContext().getRealPath("");
//String logoImag = path+"\\pfizer_blk_pos.jpg";
			String logoImag = path + "\\pfizer_blk_pos.png";

			System.out.println("logoImag : " + logoImag);
			Image img = null;
			try {
				img = Image.getInstance(logoImag);
				img.setAlignment(Image.ALIGN_CENTER);
			} catch (Exception e) {
				System.out.println("Error Occurred :" + e.getMessage());// uncomment asneeded --;
			}
			img.setAlignment(1);
			PdfPCell imagecell = new PdfPCell(img, true);
			imagecell.setFixedHeight(10f);
//imagecell.setNoWrap(false);
			imagecell.setBorder(0);
			imagecell.setHorizontalAlignment(Element.ALIGN_LEFT);
			imagecell.setVerticalAlignment(Element.ALIGN_CENTER);
			imagecell.setBorder(Rectangle.NO_BORDER | Rectangle.RIGHT);
//PdfPTable t1= new PdfPTable(1);
//t1.addCell(imagecell);

			headertable2.addCell(imagecell);

		} else if (comp_cd.trim().equalsIgnoreCase("PFZ")) {
			headertable2.addCell(cell1);
//String path=ServletActionContext.getServletContext().getRealPath("");
//String logoImag = path+"\\pfizer_blk_pos.jpg";
			int i = 1;
			String logoImag;
			if (i == 1) {
				logoImag = path + "\\pfizer_blk_pos.png";
			} else {
				logoImag = path + "\\excelLogo_blk.png";
			}
			System.out.println("logoImag : " + logoImag);
			Image img = null;
			try {
				img = Image.getInstance(logoImag);
				img.setAlignment(Image.ALIGN_LEFT);
			} catch (Exception e) {
				System.out.println("Error Occurred :" + e.getMessage());// uncomment asneeded --;
			}
			img.setAlignment(1);
			PdfPCell imagecell = new PdfPCell(img, true);
			imagecell.setFixedHeight(10f);
//imagecell.setNoWrap(false);
			imagecell.setBorder(0);
			imagecell.setHorizontalAlignment(Element.ALIGN_LEFT);
			imagecell.setVerticalAlignment(Element.ALIGN_CENTER);
			imagecell.setBorder(Rectangle.NO_BORDER | Rectangle.RIGHT);
//PdfPTable t1= new PdfPTable(1);
//t1.addCell(imagecell);
			if (challan.getDsp_challan_no().contains("PIP")) {

				PdfPCell cell = new PdfPCell(new Phrase(" "));
				cell.setBorder(Rectangle.NO_BORDER);
				headertable2.addCell(cell);

			} else {
				headertable2.addCell(imagecell);
			}

		}

		else if (companyCode.equals("PAL")) {
			headertable2.addCell(cell1);
			String logoImag;
			logoImag = path + "\\Logo_Palson.png";

			System.out.println("logoImag : " + logoImag);
			Image img = null;
			try {
				img = Image.getInstance(logoImag);
				img.setAlignment(Image.ALIGN_LEFT);
			} catch (Exception e) {
				System.out.println("Error Occurred :" + e.getMessage());// uncomment asneeded --;
			}
			img.setAlignment(1);
			PdfPCell imagecell = new PdfPCell(img, true);
			imagecell.setFixedHeight(3f);
			// imagecell.setNoWrap(false);
			imagecell.setBorder(0);
			imagecell.setHorizontalAlignment(Element.ALIGN_LEFT);
			imagecell.setVerticalAlignment(Element.ALIGN_CENTER);
			imagecell.setBorder(Rectangle.NO_BORDER | Rectangle.RIGHT);
			// PdfPTable t1= new PdfPTable(1);
			// t1.addCell(imagecell);

			headertable2.addCell(imagecell);

		} else {
			cell1.setColspan(2);
			headertable2.addCell(cell1);
		}

//TODO cell2 image add for medley by vivek 14-nov-2018

// ======================================================================================================================
// 2.3

//comp_cd = (String) context.getAttribute("comp_code");

		PdfPCell hcell16 = null;
		if (comp_cd.equalsIgnoreCase("NIL") || comp_cd.equalsIgnoreCase("NHP")) {
			hcell16 = new PdfPCell(PdfStyle.createLabelCellWithBorderSmallFont("Tax Inv Date"));
		} else {
			hcell16 = new PdfPCell(PdfStyle.createLabelCellWithBorderSmallFont("Date "));
		}
		hcell16.setHorizontalAlignment(Element.ALIGN_LEFT);

		PdfPCell hcell20 = null;

		if (comp_cd.equalsIgnoreCase("PFZ")) {
			hcell20 = new PdfPCell(PdfStyle.createLabelCellWithBorderSmallFont("Challan #"));
			hcell20.setHorizontalAlignment(Element.ALIGN_LEFT);

		} else if (comp_cd.equalsIgnoreCase("UCM")) {
			hcell20 = new PdfPCell(PdfStyle.createLabelCellWithBorderSmallFont("GST INV No#"));
			hcell20.setHorizontalAlignment(Element.ALIGN_LEFT);

		} else if (comp_cd.equalsIgnoreCase("NIL") || comp_cd.equalsIgnoreCase("NHP")) {
			hcell20 = new PdfPCell(PdfStyle.createLabelCellWithBorderSmallFont("Tax Invoice #"));
			hcell20.setHorizontalAlignment(Element.ALIGN_LEFT);
		} else {
			hcell20 = new PdfPCell(PdfStyle.createLabelCellWithBorderSmallFont("Challan #"));
			hcell20.setHorizontalAlignment(Element.ALIGN_LEFT);
		}

		PdfPCell hcell21 = new PdfPCell(PdfStyle.createLabelCellWithBorderSmallFont("Customer"));
		hcell21.setHorizontalAlignment(Element.ALIGN_LEFT);

		PdfPCell hcell22 = new PdfPCell(PdfStyle.createLabelCellWithBorderSmallFont("Division "));
		hcell22.setHorizontalAlignment(Element.ALIGN_LEFT);

		PdfPCell hcell23 = new PdfPCell(PdfStyle.createLabelCellWithBorderSmallFont("Team"));
		hcell23.setHorizontalAlignment(Element.ALIGN_LEFT);

//PdfPCell hcell24 = new PdfPCell(PdfStyle.createLabelCellWithBorderSmallFont("Remark"));
//hcell24.setHorizontalAlignment(Element.ALIGN_LEFT);

		PdfPCell hcell17 = new PdfPCell(PdfStyle.createLabelCellWithBorderSmallFontTwiceHeight("Email"));
		hcell17.setHorizontalAlignment(Element.ALIGN_LEFT);

		PdfPCell hcell18 = new PdfPCell(PdfStyle.createLabelCellWithBorderSmallFont("Telephone 1"));
		hcell18.setHorizontalAlignment(Element.ALIGN_LEFT);

		PdfPCell hcell19;
		PdfPCell hcell19_from_to;

		if (comp_cd.trim().equalsIgnoreCase(PFZ)) {

			hcell19 = new PdfPCell(PdfStyle.createLabelCellWithBorderSmallFont("Req Name"));
			hcell19.setHorizontalAlignment(Element.ALIGN_LEFT);

			hcell19_from_to = new PdfPCell(PdfStyle.createLabelCellWithBorderSmallFont("FS Contact"));
			hcell19_from_to.setHorizontalAlignment(Element.ALIGN_LEFT);

		} else {

			hcell19 = new PdfPCell(PdfStyle.createLabelCellWithBorderSmallFont("Telephone 2"));
			hcell19.setHorizontalAlignment(Element.ALIGN_LEFT);

			hcell19_from_to = new PdfPCell(PdfStyle.createLabelCellWithBorderSmallFont("Unique No :"));
			hcell19_from_to.setHorizontalAlignment(Element.ALIGN_LEFT);

		}

		PdfPTable addressTbl2 = new PdfPTable(1);
		addressTbl2.addCell(hcell16);

		addressTbl2.addCell(hcell20);
		addressTbl2.addCell(hcell21);
		addressTbl2.addCell(hcell22);
		addressTbl2.addCell(hcell23);
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
// Font medFont = new Font(Font.FontFamily.HELVETICA, 12,Font.NORMAL,
// BaseColor.BLACK);

		PdfPCell hcell26 = new PdfPCell(PdfStyle.createLabelCellWithBorderSmallFontNoBold(
				(challan.getDsp_challan_dt() == null ? "" : challan.getDsp_challan_dt())));
		hcell26.setHorizontalAlignment(Element.ALIGN_LEFT);

		PdfPCell hcell30 = new PdfPCell(
				PdfStyle.createLabelCellWithBorderSmallFontNoBold((challan.getDsp_challan_no() == null ? ""
						: challan.getDsp_challan_no() + "    ( " + challan.getPri_no() + " )")));
		hcell30.setHorizontalAlignment(Element.ALIGN_LEFT);

		PdfPCell hcell_30 = new PdfPCell(PdfStyle.createLabelCellWithBorderSmallFontNoBold(
				(challan.getDsp_challan_no() == null ? "" : challan.getDsp_challan_no())));
		hcell_30.setHorizontalAlignment(Element.ALIGN_LEFT);

		PdfPCell hcell31 = new PdfPCell(PdfStyle.createLabelCellWithBorderSmallFontNoBold(
				(challan.getDspfstaff_displayname().length() > 20 ? challan.getDspfstaff_displayname().substring(0, 20)
						: challan.getDspfstaff_displayname())));
		hcell31.setHorizontalAlignment(Element.ALIGN_LEFT);

		PdfPCell hcell32 = new PdfPCell(PdfStyle.createLabelCellWithBorderSmallFontNoBold(
				(challan.getDivision().length() > 28 ? challan.getDivision().substring(0, 28)
						: challan.getDivision())));
		hcell32.setHorizontalAlignment(Element.ALIGN_LEFT);

		System.out.println("challan.getTeam()" + challan.getTeam());
		PdfPCell hcell33 = new PdfPCell(PdfStyle.createLabelCellWithBorderSmallFontNoBold(
				((challan.getTeam() == null ? "" : challan.getTeam()).length() > 30 ? challan.getTeam().substring(0, 30)
						: challan.getTeam() == null ? "" : challan.getTeam())));
		hcell33.setHorizontalAlignment(Element.ALIGN_LEFT);

		PdfPCell hcell34 = new PdfPCell(PdfStyle.createLabelCellWithBorderSmallFontNoBold(
				challan.getDsp_challan_msg() == null ? "" : challan.getDsp_challan_msg()));
		hcell34.setHorizontalAlignment(Element.ALIGN_LEFT);

		PdfPCell hcell27 = new PdfPCell(PdfStyle.createLabelCellWithBorderSmallFontNoBoldTwiceHeight(
				(challan.getDspfstaff_email().length() > 40 ? challan.getDspfstaff_email().substring(0, 40)
						: challan.getDspfstaff_email())));
		hcell27.setHorizontalAlignment(Element.ALIGN_LEFT);

		PdfPCell hcell28 = new PdfPCell(PdfStyle.createLabelCellWithBorderSmallFontNoBold(
				(challan.getDspfstaff_mobile() == null ? "" : challan.getDspfstaff_mobile())));
		hcell28.setHorizontalAlignment(Element.ALIGN_LEFT);

		PdfPCell hcell29;
		PdfPCell hcell29_from_to;

		if (comp_cd.trim().equalsIgnoreCase(PFZ)) {

			hcell29 = new PdfPCell(PdfStyle.createLabelCellWithBorderSmallFontNoBold(
					(challan.getDspfstaff_mobile() == null ? "" : challan.getFstaff_display_name()))); // fsName
			hcell29.setHorizontalAlignment(Element.ALIGN_LEFT);

			hcell29_from_to = new PdfPCell(PdfStyle.createLabelCellWithBorderSmallFontNoBold(
					(challan.getDspfstaff_mobile() == null ? "" : challan.getFstaff_mobile()))); // fs Contact

			hcell29_from_to.setHorizontalAlignment(Element.ALIGN_LEFT);

			System.out.println("hcell29            " + challan.getFstaff_display_name());
			System.out.println("hcell29_from_to    " + challan.getFstaff_mobile());

		} else {

			hcell29 = new PdfPCell(PdfStyle.createLabelCellWithBorderSmallFontNoBold((""))); // telephone
			// 2
			hcell29.setHorizontalAlignment(Element.ALIGN_LEFT);

			String s = challan.getTo_num().length() < 6 ? ""
					: challan.getTo_num().substring(challan.getTo_num().length() - 5);
			hcell29_from_to = new PdfPCell(
					PdfStyle.createLabelCellWithBorderSmallFontNoBold((challan.getFr_num().length() < 6 ? ""
							: challan.getFr_num().substring(challan.getFr_num().length() - 5) + " To " + s)));

			hcell29_from_to.setHorizontalAlignment(Element.ALIGN_LEFT);
		}

		PdfPTable addressTbl3 = new PdfPTable(1);
		addressTbl3.addCell(hcell26);

		if (companyCode.equals("P&G")) {
			addressTbl3.addCell(hcell30);
		} else {
			addressTbl3.addCell(hcell_30);
		}
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

// =====================================================================================================================

// =====================================================================================================================
// 3.1

// PdfPCell hcell35 = new PdfPCell(PdfStyle.createLabelCellWithBorderSmallFont("Ship To :"));
		PdfPCell hcell35 = new PdfPCell(new Phrase("Ship To :", font3));

		hcell35.setHorizontalAlignment(Element.ALIGN_LEFT);

		PdfPCell hcell36 = new PdfPCell(PdfStyle.createLabelCellWithoutBorder(
				challan.getDspfstaff_displayname() == null ? "" : challan.getDspfstaff_displayname()));
		hcell36.setHorizontalAlignment(Element.ALIGN_LEFT);
		hcell36.enableBorderSide(Rectangle.LEFT);
		hcell36.enableBorderSide(Rectangle.RIGHT);
		StringBuilder result2 = new StringBuilder();
		result2.append(challan.getDspfstaffaddr1().length() > 55 ? challan.getDspfstaffaddr1().substring(0, 55)
				: challan.getDspfstaffaddr1());
		result2.append(System.lineSeparator());
		result2.append(challan.getDspfstaffaddr2().length() > 55 ? challan.getDspfstaffaddr2().substring(0, 55)
				: challan.getDspfstaffaddr2());
		result2.append(System.lineSeparator());
		result2.append(challan.getDspfstaffaddr3().length() > 55 ? challan.getDspfstaffaddr3().substring(0, 55)
				: challan.getDspfstaffaddr3());
		result2.append(System.lineSeparator());
		result2.append(challan.getDspfstaffaddr4().length() > 55 ? challan.getDspfstaffaddr4().substring(0, 55)
				: challan.getDspfstaffaddr4());
		result2.append(System.lineSeparator());
		result2.append("GSTIN : UNREGISTERED");
		result2.append(System.lineSeparator());
		result2.append("PAN : Not Available");
		result2.append(System.lineSeparator());

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
//String address1 = (String) context.getAttribute("address1");
//String address2 = (String) context.getAttribute("address2");
//String address3 = (String) context.getAttribute("address3");
//String address4 = (String) context.getAttribute("address4");
// WEB_SITE cinno
//String web_site = (String) context.getAttribute("web_site") == null ? " "
// : (String) context.getAttribute("web_site");
//String cinno = (String) context.getAttribute("cinno") == null ? " " : (String) context.getAttribute("cinno");
		Company company = ((Company) session.getServletContext().getAttribute(COMPANY_DATA));
		String address1 = company.getAddress1();
		String address2 = company.getAddress2();
		String address3 = company.getAddress3();
		String address4 = company.getAddress4();
		String web_site = company.getWebsite();
		String cinno = company.getCinno();

		StringBuilder result3 = new StringBuilder();

// Company name added 27-1-2017
		if (challan.getDsp_challan_no().contains("PIP")) {

			if (comp_cd.equalsIgnoreCase("PFZ")) {
				result3.append(comp_name_new);

				address1 = ":";
				address2 = ":";
				address3 = ":";
				address4 = ":";
				web_site = ":";
				cinno = ":"; //// Temporary Code 2-6-2020
			} else {
				result3.append(company_name);
			}

			result3.append(System.lineSeparator());
		} else {
			if (company_name.trim().isEmpty()) {
				result3.append(System.lineSeparator());
			} else {
				result3.append(company_name.length() > 30 ? company_name.substring(0, 30) : company_name);
				result3.append(System.lineSeparator());
			}
		}

		result3.append(address1.length() > 40 ? address1.substring(0, 40) : address1);
		result3.append(System.lineSeparator());
		result3.append(address2.length() > 48 ? address2.substring(0, 40) : address2);
		result3.append(System.lineSeparator());
		result3.append(address3.length() > 40 ? address3.substring(0, 40) : address3);
		result3.append(System.lineSeparator());
		result3.append(address4.length() > 40 ? address4.substring(0, 40) : address4);
		result3.append(System.lineSeparator());

		if (web_site.trim().isEmpty() || compcd.trim().equals("P&G")) {
			result3.append(" ");
			result3.append(System.lineSeparator());
		} else {
			result3.append(web_site.length() > 40 ? "Website:" + web_site.substring(0, 40) : "Website:" + web_site);
			result3.append(System.lineSeparator());

		}
//System.out.println(cinno+"#sdfsdfsd");
		if (cinno.trim().isEmpty() || compcd.trim().equals("P&G")) {
			result3.append(" ");
			result3.append(System.lineSeparator());
		} else {
			result3.append(cinno.length() > 33 ? "CIN NO:" + cinno.substring(0, 33) : "CIN NO:" + cinno);
			result3.append(System.lineSeparator());
		}

// PdfPCell hcell38 = new PdfPCell(PdfStyle.createLabelCellWithBorderSmallFont("Principal place of business supplier"));
		PdfPCell hcell38 = new PdfPCell(new Phrase("Principal place of business supplier ", font3));
		hcell38.setHorizontalAlignment(Element.ALIGN_LEFT);

		PdfPCell hcell39 = new PdfPCell(PdfStyle.createLabelCellWithoutBorderSmallFontNoBold(
				result3.length() > 170 ? result3.substring(0, 170) : result3.toString()));
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

		if (!comp_cd.equalsIgnoreCase("NIL") && !comp_cd.equalsIgnoreCase("NHP")) {
			result4.append("Free samples and/"); // Goods not for sale. Free samples
			// and / or promotional inputs only
			// having no commercial value
			result4.append(System.lineSeparator());
			result4.append("or promotional inputs");
			result4.append(System.lineSeparator());
			result4.append("only having no ");
			result4.append(System.lineSeparator());
			result4.append("commercial value.");
			result4.append(System.lineSeparator());
		} else {
			result4.append("                                                "); // Goods not for sale. Free samples
			// and / or promotional inputs only
			// having no commercial value
			result4.append(System.lineSeparator());
			result4.append(System.lineSeparator());
			result4.append(System.lineSeparator());
			result4.append(System.lineSeparator());
			result4.append(System.lineSeparator());
			result4.append(System.lineSeparator());
			result4.append(System.lineSeparator());
			result4.append(System.lineSeparator());
			result4.append(System.lineSeparator());

		}
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

		if (comp_cd.equalsIgnoreCase("PFZ")) {
			if (show_amount.equals("Y")) {
				hcell45 = new PdfPCell(PdfStyle.createLabelCellWithBorderSmallFont("Insurance Policy No."));
			} else {
				hcell45 = new PdfPCell(PdfStyle.createLabelCellWithBorderSmallFont(""));
			}

		} else if (comp_cd.equalsIgnoreCase("UCM") || comp_cd.equals("PAL")) {
			hcell45 = new PdfPCell(PdfStyle.createLabelCellWithBorderSmallFont(""));
		} else {
			hcell45 = new PdfPCell(PdfStyle.createLabelCellWithBorderSmallFont("Insurance Policy No."));

		}

		hcell45.setHorizontalAlignment(Element.ALIGN_LEFT);

		PdfPCell hcell46 = new PdfPCell(PdfStyle.createLabelCellWithoutBorderSmallFontNoBold(" "));
		hcell46.setHorizontalAlignment(Element.ALIGN_LEFT);
		hcell46.enableBorderSide(Rectangle.RIGHT);
		PdfPCell hcell47 = null;

		if (compcd.trim().equals("P&G") || comp_cd.equals("PAL")) {
			hcell47 = new PdfPCell(PdfStyle.createLabelCellWithBorderSmallFont(" "));
		} else {
			hcell47 = new PdfPCell(PdfStyle.createLabelCellWithBorderSmallFont("Food Licence Nos."));
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
		headertable3.addCell(cell8);

// =================================================Blank Cell Added To
// Remove Table Border3==============================

		PdfPCell hcell_blank3 = new PdfPCell(headertable3);
		hcell_blank3.setBorder(Rectangle.NO_BORDER);

		if (companyCode.equals("P&G")) {
			bodytable = new PdfPTable(7);
			bodytable.setWidthPercentage(100);
			bodytable.setWidths(bodyTableWidth);
			bodytable.addCell(PdfStyle.createLabelWithBGColour("Item"));
			bodytable.addCell(PdfStyle.createLabelWithBGColour("HSN Code"));
			bodytable.addCell(PdfStyle.createLabelWithBGColour("Batch/Art Work No"));
			bodytable.addCell(PdfStyle.createLabelWithBGColour("Expiry Date"));
			bodytable.addCell(PdfStyle.createLabelWithBGColour("Qty"));
			if (show_amount.equals("Y")) {
				if (compcd.equals("P&G")) {
					bodytable.addCell(PdfStyle.createLabelWithBGColour("Rate"));
				} else {
					bodytable.addCell(PdfStyle.createLabelWithBGColour("Taxable Value"));
				}
			}
			bodytable.addCell(PdfStyle.createLabelWithBGColour("Taxable Amount"));
		}

		else if (companyCode.equals("PAL")) {
			bodytable = new PdfPTable(11);
			bodytable.setWidthPercentage(100);
			bodytable.setWidths(bodyTableWidth);
			bodytable.addCell(PdfStyle.createLabelWithBGColour("Sl No."));
			bodytable.addCell(PdfStyle.createLabelWithBGColour("Item code"));
			bodytable.addCell(PdfStyle.createLabelWithBGColour("Item Description"));
			bodytable.addCell(PdfStyle.createLabelWithBGColour("Pack"));
			bodytable.addCell(PdfStyle.createLabelWithBGColour("UOM"));
			bodytable.addCell(PdfStyle.createLabelWithBGColour("HSN Code"));
			bodytable.addCell(PdfStyle.createLabelWithBGColour("Batch No."));
			bodytable.addCell(PdfStyle.createLabelWithBGColour("Expiry Date"));
			bodytable.addCell(PdfStyle.createLabelWithBGColour("Rate"));
			bodytable.addCell(PdfStyle.createLabelWithBGColour("Free Qty"));
			bodytable.addCell(PdfStyle.createLabelWithBGColour("Taxable Amount"));

		} else {
			if (show_amount.equals("N")) {
				bodytable = new PdfPTable(5);
			} else {
				bodytable = new PdfPTable(6);
			}
			bodytable.setWidthPercentage(100);
			bodytable.setWidths(bodyTableWidth);
			bodytable.addCell(PdfStyle.createLabelWithBGColour("Item"));
			bodytable.addCell(PdfStyle.createLabelWithBGColour("HSN Code"));
			bodytable.addCell(PdfStyle.createLabelWithBGColour("Batch/Art Work No"));
			bodytable.addCell(PdfStyle.createLabelWithBGColour("Expiry Date"));
			bodytable.addCell(PdfStyle.createLabelWithBGColour("Qty"));
			if (show_amount.equals("Y")) {
				if (compcd.equals("P&G")) {
					bodytable.addCell(PdfStyle.createLabelWithBGColour("Rate"));
				} else {
					bodytable.addCell(PdfStyle.createLabelWithBGColour("Taxable Value"));
				}
			}

		}

// =================================================Blank Cell Added To
// Remove Table Border4==============================

		PdfPCell hcell_blank4 = new PdfPCell(bodytable);
		hcell_blank4.setBorder(Rectangle.NO_BORDER);
		headertable.addCell(hcell_blank);
		headertable.addCell(hcell_blank2);
		headertable.addCell(hcell_blank3);
		headertable.addCell(hcell_blank4);

		return headertable;
	}

	public String getComp_cd() {
		return comp_cd;
	}

	public void setComp_cd(String comp_cd) {
		this.comp_cd = comp_cd;
	}

	public PdfPTable newPage(ColumnText column, PdfPTable bodytable, FooterWithoutPageNo event,
			HashMap<String, String> printLabel, boolean isFooterData, int rows, boolean pageflag, int pagecount,
			BigDecimal total, int division, Integer loc, String frm_challan, String to_challan, String dispatchType,
			String prodtype, String path, List<PrePrintedDetailChallan_withgst> challan, String show_amount,
			boolean footer_ind, String dsp_transporter, String ship_doc_no, String date, String cases, String weigh,
			String dsp_challan_msg, BigDecimal goods_total, BigDecimal final_total, BigDecimal header_Igst,
			BigDecimal header_Cgst, BigDecimal header_Sgst, BigDecimal round_off, String gst_doc_type,
			String dsp_lorry_no, float[] bodyTableWidth, String companyCode, BigDecimal igstAmounts, String oldDocType)
			throws DocumentException {

		PdfPCell cell = null;
		try {
			// System.out.println("limit:"+limit);
			// System.out.println("rows::"+rows);

			if (limit - (rows) > 0) {
// add extra empty rows in body table
				cell = new PdfPCell();

				if (companyCode.equals("P&G")) {
					PdfPTable temp = new PdfPTable(7);
					temp.setWidths(bodyTableWidth);
					temp.addCell(PdfStyle.createValueCellWithHeight((""), false, true, 18f));
					temp.addCell(PdfStyle.createValueCellWithHeight((""), false, true, 18f));
					temp.addCell(PdfStyle.createValueCellWithHeight((""), false, true, 18f));
					temp.addCell(PdfStyle.createValueCellWithHeight((""), true, true, 18f));
					temp.addCell(PdfStyle.createValueCellWithHeight((""), true, true, 18f));
					temp.addCell(PdfStyle.createValueCellWithHeight((""), true, true, 18f));
					temp.addCell(PdfStyle.createValueCellWithHeight((""), true, true, 18f));

					temp.addCell(PdfStyle.createValueCellWithHeight((" "), false, true, 18f));
					temp.addCell(PdfStyle.createValueCellWithHeight((""), false, true, 18f));
					temp.addCell(PdfStyle.createValueCellWithHeight((""), false, true, 18f));
					temp.addCell(PdfStyle.createValueCellWithHeight((""), true, true, 18f));
					temp.addCell(PdfStyle.createValueCellWithHeight((""), true, true, 18f));
					temp.addCell(PdfStyle.createValueCellWithHeight((""), true, true, 18f));
					temp.addCell(PdfStyle.createValueCellWithHeight((""), true, true, 18f));

					cell = new PdfPCell(temp);
					cell.setBorder(0);
					cell.setColspan(7);
				} else if (companyCode.equals("PAL")) {
					PdfPTable temp = new PdfPTable(11);
					temp.setWidths(bodyTableWidth);
					temp.addCell(PdfStyle.createValueCellWithHeight((""), false, true, 18f));
					temp.addCell(PdfStyle.createValueCellWithHeight((""), false, true, 18f));
					temp.addCell(PdfStyle.createValueCellWithHeight((""), false, true, 18f));
					temp.addCell(PdfStyle.createValueCellWithHeight((""), true, true, 18f));
					temp.addCell(PdfStyle.createValueCellWithHeight((""), true, true, 18f));
					temp.addCell(PdfStyle.createValueCellWithHeight((""), true, true, 18f));
					temp.addCell(PdfStyle.createValueCellWithHeight((""), true, true, 18f));
					temp.addCell(PdfStyle.createValueCellWithHeight((""), true, true, 18f));
					temp.addCell(PdfStyle.createValueCellWithHeight((""), true, true, 18f));
					temp.addCell(PdfStyle.createValueCellWithHeight((""), true, true, 18f));
					temp.addCell(PdfStyle.createValueCellWithHeight((""), true, true, 18f));

					temp.addCell(PdfStyle.createValueCellWithHeight((" "), false, true, 18f));
					temp.addCell(PdfStyle.createValueCellWithHeight((""), false, true, 18f));
					temp.addCell(PdfStyle.createValueCellWithHeight((""), false, true, 18f));
					temp.addCell(PdfStyle.createValueCellWithHeight((""), true, true, 18f));
					temp.addCell(PdfStyle.createValueCellWithHeight((""), true, true, 18f));
					temp.addCell(PdfStyle.createValueCellWithHeight((""), true, true, 18f));
					temp.addCell(PdfStyle.createValueCellWithHeight((""), true, true, 18f));
					temp.addCell(PdfStyle.createValueCellWithHeight((""), true, true, 18f));
					temp.addCell(PdfStyle.createValueCellWithHeight((""), true, true, 18f));
					temp.addCell(PdfStyle.createValueCellWithHeight((""), true, true, 18f));
					temp.addCell(PdfStyle.createValueCellWithHeight((""), true, true, 18f));

					cell = new PdfPCell(temp);
					cell.setBorder(0);
					cell.setColspan(11);
				}

				else {
					if (show_amount.equals("Y")) {
						PdfPTable temp = new PdfPTable(6);
						temp.setWidths(bodyTableWidth);

						temp.addCell(PdfStyle.createValueCellWithHeight((""), false, true, 18f));
						temp.addCell(PdfStyle.createValueCellWithHeight((""), false, true, 18f));
						temp.addCell(PdfStyle.createValueCellWithHeight((""), false, true, 18f));
						temp.addCell(PdfStyle.createValueCellWithHeight((""), true, true, 18f));
						temp.addCell(PdfStyle.createValueCellWithHeight((""), true, true, 18f));
						temp.addCell(PdfStyle.createValueCellWithHeight((""), true, true, 18f));

						temp.addCell(PdfStyle.createValueCellWithHeight((" "), false, true, 18f));
						temp.addCell(PdfStyle.createValueCellWithHeight((""), false, true, 18f));
						temp.addCell(PdfStyle.createValueCellWithHeight((""), false, true, 18f));
						temp.addCell(PdfStyle.createValueCellWithHeight((""), true, true, 18f));
						temp.addCell(PdfStyle.createValueCellWithHeight((""), true, true, 18f));
						temp.addCell(PdfStyle.createValueCellWithHeight((""), true, true, 18f));

						cell = new PdfPCell(temp);
						cell.setBorder(0);
						cell.setColspan(6);
					} else {
						PdfPTable temp = new PdfPTable(5);
						temp.setWidths(bodyTableWidth);

						temp.addCell(PdfStyle.createValueCellWithHeight((""), false, true, 18f));
						temp.addCell(PdfStyle.createValueCellWithHeight((""), false, true, 18f));
						temp.addCell(PdfStyle.createValueCellWithHeight((""), false, true, 18f));
						temp.addCell(PdfStyle.createValueCellWithHeight((""), true, true, 18f));
						temp.addCell(PdfStyle.createValueCellWithHeight((""), true, true, 18f));

						temp.addCell(PdfStyle.createValueCellWithHeight((" "), false, true, 18f));
						temp.addCell(PdfStyle.createValueCellWithHeight((""), false, true, 18f));
						temp.addCell(PdfStyle.createValueCellWithHeight((""), false, true, 18f));
						temp.addCell(PdfStyle.createValueCellWithHeight((""), true, true, 18f));
						temp.addCell(PdfStyle.createValueCellWithHeight((""), true, true, 18f));

						cell = new PdfPCell(temp);
						cell.setBorder(0);
						cell.setColspan(5);
					}

				}

// System.out.println("limit-row:::::"+(limit-rows));
				for (int i = 0; i < (limit - rows); i++) {
					// System.out.println("printing row:::"+i);
					bodytable.addCell(cell);
				}
			}

			cell = new PdfPCell(PdfStyle.createSingleLine());
			cell.setColspan(6);
			bodytable.addCell(cell);

			column.addElement(bodytable);
			column.setSimpleColumn(20, 0, 580, 512);
			column.go();

			if (companyCode.equals("P&G")) {
				bodytable = new PdfPTable(7);
			} else if (companyCode.equals("PAL")) {
				bodytable = new PdfPTable(11);
			} else {
				if (show_amount.equals("N")) {
					bodytable = new PdfPTable(5);
				} else
					bodytable = new PdfPTable(6);
			}
			bodytable.setWidthPercentage(100);
			bodytable.setWidths(bodyTableWidth);

			event.setFooter(createFooter(printLabel, isFooterData, pageflag, pagecount, total, division, loc,
					frm_challan, to_challan, dispatchType, prodtype, path, challan, show_amount, footer_ind,
					dsp_transporter, ship_doc_no, date, cases, weigh, dsp_challan_msg, goods_total, final_total,
					header_Igst, header_Cgst, header_Sgst, round_off, gst_doc_type, dsp_lorry_no, companyCode,
					igstAmounts, oldDocType));

		} catch (DocumentException e) {
			throw e;
		}
		return bodytable;
	}

	private PdfPTable createFooter(HashMap<String, String> printLabel, boolean isFooterData, boolean pageflag,
			int pagecount, BigDecimal total, int division, Integer loc, String frm_challan, String to_challan,
			String dispatchType, String prodtype, String path, List<PrePrintedDetailChallan_withgst> challan,
			String show_amount, boolean footer_ind, String dsp_transporter, String ship_doc_no, String date,
			String cases, String weigh, String dsp_challan_msg, BigDecimal goods_total, BigDecimal final_total,
			BigDecimal header_Igst, BigDecimal header_Cgst, BigDecimal header_Sgst, BigDecimal round_off,
			String gst_doc_type, String dsp_lorry_no, String companyCode, BigDecimal igstAmounts, String oldDocType)
			throws DocumentException {

		System.out.println("createFootercaled$$$$$$$$$$$$$$$$$");
		PdfPTable maintable = new PdfPTable(2);
// System.out.println("ship_doc_no::"+ship_doc_no+"dsp_transporter::"+dsp_transporter+"cases:"+cases);
// System.out.println("challan size() " +challan.size());
		try {

			PdfPCell cell = null;
			cell = new PdfPCell();

//Testing
			String narrration = printService.getLocationNarrationByLocId(Long.parseLong(loc.toString()));
			maintable.setWidthPercentage(100);
			maintable.setWidths(new float[] { 5.5f, 4.5f });

			// footer left cell
			PdfPTable foot_left_table = new PdfPTable(1);
			foot_left_table.setWidthPercentage(100);
			foot_left_table.setWidths(new float[] { 10f });

			PdfPCell foot_bottom_cell1 = new PdfPCell(PdfStyle.createUltimateCellWithBorderWithDisableBorder(
					(narrration.substring(1, narrration.length() - 1)), Element.ALIGN_LEFT, Element.ALIGN_TOP, 9, 14f,
					true, 1, 1, false, true, true, false, true));
			foot_left_table.addCell(foot_bottom_cell1);

			if (comp_cd.equals("PAL")) {

				PdfPCell foot_bottom_cell2 = new PdfPCell(
						PdfStyle.createUltimateCellWithoutBorder((""), 2, 5, 9, 18, false));
				foot_left_table.addCell(foot_bottom_cell2);

				PdfPCell foot_bottom_cell3 = new PdfPCell(
						PdfStyle.createUltimateCellWithoutBorder((""), 2, 5, 9, 18, false));
				foot_left_table.addCell(foot_bottom_cell3);

				PdfPCell foot_bottom_cell4 = new PdfPCell(PdfStyle.createUltimateCellWithBorderWithDisableBorder(
						("Value for Insurance purpose only Rs. "
								+ goods_total.setScale(2, BigDecimal.ROUND_HALF_UP).toString()),
						Element.ALIGN_LEFT, Element.ALIGN_TOP, 9, 14f, true, 1, 1, false, true, true, false, true));
				foot_left_table.addCell(foot_bottom_cell4);
			}

//    PdfPCell foot_bottom_cell1 = new PdfPCell(
//    PdfStyle.createUltimateCellWithBorderWithDisableBorder(" ", Element.ALIGN_LEFT,
//    Element.ALIGN_TOP, 9, 14f, true, 1, 1, false, true, true, false, true));
//    foot_left_table.addCell(foot_bottom_cell1);

			// footer right cell
			PdfPTable foot_right_table = new PdfPTable(2);
			foot_right_table.setWidthPercentage(100);
			foot_right_table.setWidths(new float[] { 3f, 2.4f });

			String goodsOrTotal = "";
			if (comp_cd.equalsIgnoreCase("NIL") || comp_cd.equalsIgnoreCase("PAL") || comp_cd.equalsIgnoreCase("NHP"))
				goodsOrTotal = "Total";
			else if (comp_cd.equalsIgnoreCase("P&G")) {
				goodsOrTotal = "Taxable";
			} else
				goodsOrTotal = "Goods";

			// new changes for remove column in pdf cell by vivek based on footer indicator

//   if(footer_ind && comp_cd.equalsIgnoreCase("NIL")){
			if (footer_ind && comp_cd.equalsIgnoreCase("PFZ") || comp_cd.equalsIgnoreCase("P&G")
					|| comp_cd.equalsIgnoreCase("PAL") || comp_cd.equalsIgnoreCase("NIL")
					|| comp_cd.equalsIgnoreCase("NHP")) {

				if (comp_cd.equalsIgnoreCase("P&G") || comp_cd.equalsIgnoreCase("PAL")) {
					PdfPCell foot_bottom_right_cell1 = new PdfPCell(
							PdfStyle.createUltimateCellWithBorderWithDisableBorder((goodsOrTotal + " Amount"),
									Element.ALIGN_LEFT, Element.ALIGN_TOP, 8, 14f, true, 1, 1, false, true, true, false,
									true));
					foot_right_table.addCell(foot_bottom_right_cell1);

					PdfPCell foot_bottom_right_cell2 = new PdfPCell(PdfStyle
							.createUltimateCellWithBorderWithDisableBorder(("0.00".toString()), Element.ALIGN_RIGHT,
									Element.ALIGN_TOP, 8, 14f, true, 1, 1, false, true, true, false, true));
					foot_right_table.addCell(foot_bottom_right_cell2);
				} else {
					if (show_amount.equals("Y")) {
						PdfPCell foot_bottom_right_cell1 = new PdfPCell(
								PdfStyle.createUltimateCellWithBorderWithDisableBorder((goodsOrTotal + " Value"),
										Element.ALIGN_LEFT, Element.ALIGN_TOP, 8, 14f, true, 1, 1, false, true, true,
										false, true));
						foot_right_table.addCell(foot_bottom_right_cell1);

						PdfPCell foot_bottom_right_cell2 = new PdfPCell(
								PdfStyle.createUltimateCellWithBorderWithDisableBorder(
										(goods_total.setScale(2, BigDecimal.ROUND_HALF_UP).toString()),
										Element.ALIGN_RIGHT, Element.ALIGN_TOP, 8, 14f, true, 1, 1, false, true, true,
										false, true));
						foot_right_table.addCell(foot_bottom_right_cell2);
					} else {
						PdfPCell foot_bottom_right_cell1 = new PdfPCell(
								PdfStyle.createUltimateCellWithBorderWithDisableBorder(("\n"), Element.ALIGN_LEFT,
										Element.ALIGN_TOP, 8, 14f, true, 1, 1, false, true, true, false, true));
						foot_right_table.addCell(foot_bottom_right_cell1);

						PdfPCell foot_bottom_right_cell2 = new PdfPCell(
								PdfStyle.createUltimateCellWithBorderWithDisableBorder(("\n"), Element.ALIGN_RIGHT,
										Element.ALIGN_TOP, 8, 14f, true, 1, 1, false, true, true, false, true));
						foot_right_table.addCell(foot_bottom_right_cell2);
					}
				}

				if (oldDocType.contains("008")) {
					System.out.println("inside Inter");
					PdfPCell foot_bottom_right_cell33;
					PdfPCell foot_bottom_right_cell43;
					if (show_amount.equals("Y")) {
						foot_bottom_right_cell33 = new PdfPCell(
								PdfStyle.createUltimateCellWithBorderWithDisableBorder(("IGST"), Element.ALIGN_LEFT,
										Element.ALIGN_TOP, 8, 14f, true, 1, 1, false, true, true, false, true));
						foot_right_table.addCell(foot_bottom_right_cell33);
					} else {
						foot_bottom_right_cell33 = new PdfPCell(
								PdfStyle.createUltimateCellWithBorderWithDisableBorder(("\n"), Element.ALIGN_LEFT,
										Element.ALIGN_TOP, 8, 14f, true, 1, 1, false, true, true, false, true));
						foot_right_table.addCell(foot_bottom_right_cell33);
					}
					if (comp_cd.equals("P&G")) {
						foot_bottom_right_cell43 = new PdfPCell(
								PdfStyle.createUltimateCellWithBorderWithDisableBorder(("0.00"), Element.ALIGN_RIGHT,
										Element.ALIGN_TOP, 8, 14f, true, 1, 1, false, true, true, false, true));
						foot_right_table.addCell(foot_bottom_right_cell43);
					} else {
						if (show_amount.equals("Y")) {
							foot_bottom_right_cell43 = new PdfPCell(
									PdfStyle.createUltimateCellWithBorderWithDisableBorder((igstAmounts.toString()),
											Element.ALIGN_RIGHT, Element.ALIGN_TOP, 8, 14f, true, 1, 1, false, true,
											true, false, true));
							foot_right_table.addCell(foot_bottom_right_cell43);
						} else {
							foot_bottom_right_cell43 = new PdfPCell(
									PdfStyle.createUltimateCellWithBorderWithDisableBorder(("\n"), Element.ALIGN_RIGHT,
											Element.ALIGN_TOP, 8, 14f, true, 1, 1, false, true, true, false, true));
							foot_right_table.addCell(foot_bottom_right_cell43);
						}
					}

				} else {
					PdfPCell foot_bottom_right_cell3;

					foot_bottom_right_cell3 = new PdfPCell(
							PdfStyle.createUltimateCellWithBorderWithDisableBorder(("CGST"), Element.ALIGN_LEFT,
									Element.ALIGN_TOP, 8, 14f, true, 1, 1, false, true, true, false, true));
					foot_right_table.addCell(foot_bottom_right_cell3);

					if (comp_cd.equals("P&G")) {
						PdfPCell foot_bottom_right_cell4 = new PdfPCell(
								PdfStyle.createUltimateCellWithBorderWithDisableBorder(("0.00"), Element.ALIGN_RIGHT,
										Element.ALIGN_TOP, 8, 14f, true, 1, 1, false, true, true, false, true));
						foot_right_table.addCell(foot_bottom_right_cell4);
					} else {
						PdfPCell foot_bottom_right_cell4 = new PdfPCell(
								PdfStyle.createUltimateCellWithBorderWithDisableBorder(
										(header_Cgst.setScale(2, BigDecimal.ROUND_HALF_UP).toString()),
										Element.ALIGN_RIGHT, Element.ALIGN_TOP, 8, 14f, true, 1, 1, false, true, true,
										false, true));
						foot_right_table.addCell(foot_bottom_right_cell4);
					}

					PdfPCell foot_bottom_right_cell5 = new PdfPCell(
							PdfStyle.createUltimateCellWithBorderWithDisableBorder(("SGST"), Element.ALIGN_LEFT,
									Element.ALIGN_TOP, 8, 14f, true, 1, 1, false, true, true, false, true));
					foot_right_table.addCell(foot_bottom_right_cell5);

					if (comp_cd.equals("P&G")) {
						PdfPCell foot_bottom_right_cell6 = new PdfPCell(
								PdfStyle.createUltimateCellWithBorderWithDisableBorder(("0.00"), Element.ALIGN_RIGHT,
										Element.ALIGN_TOP, 8, 14f, true, 1, 1, false, true, true, false, true));
						foot_right_table.addCell(foot_bottom_right_cell6);
					} else {
						PdfPCell foot_bottom_right_cell6 = new PdfPCell(
								PdfStyle.createUltimateCellWithBorderWithDisableBorder(
										(header_Sgst.setScale(2, BigDecimal.ROUND_HALF_UP).toString()),
										Element.ALIGN_RIGHT, Element.ALIGN_TOP, 8, 14f, true, 1, 1, false, true, true,
										false, true));
						foot_right_table.addCell(foot_bottom_right_cell6);
					}

				}

				if (companyCode.contains("P&G") || companyCode.contains("PAL")) {

					PdfPCell foot_bottom_right_cell73 = new PdfPCell(
							PdfStyle.createUltimateCellWithBorderWithDisableBorder((" "), Element.ALIGN_LEFT,
									Element.ALIGN_TOP, 8, 14f, true, 1, 1, false, true, true, false, true));
					foot_right_table.addCell(foot_bottom_right_cell73);

					PdfPCell foot_bottom_right_cell83 = new PdfPCell(
							PdfStyle.createUltimateCellWithBorderWithDisableBorder((" "), Element.ALIGN_RIGHT,
									Element.ALIGN_TOP, 8, 14f, true, 1, 1, false, true, true, false, true));
					foot_right_table.addCell(foot_bottom_right_cell83);
				} else {
					if (show_amount.equals("Y")) {
						PdfPCell foot_bottom_right_cell73 = new PdfPCell(PdfStyle
								.createUltimateCellWithBorderWithDisableBorder(("Round Off (+/-)"), Element.ALIGN_LEFT,
										Element.ALIGN_TOP, 8, 14f, true, 1, 1, false, true, true, false, true));
						foot_right_table.addCell(foot_bottom_right_cell73);

						PdfPCell foot_bottom_right_cell83 = new PdfPCell(
								PdfStyle.createUltimateCellWithBorderWithDisableBorder(
										(round_off.setScale(2, BigDecimal.ROUND_HALF_UP).toString()),
										Element.ALIGN_RIGHT, Element.ALIGN_TOP, 8, 14f, true, 1, 1, false, true, true,
										false, true));
						foot_right_table.addCell(foot_bottom_right_cell83);
					} else {
						PdfPCell foot_bottom_right_cell73 = new PdfPCell(
								PdfStyle.createUltimateCellWithBorderWithDisableBorder(("\n"), Element.ALIGN_LEFT,
										Element.ALIGN_TOP, 8, 14f, true, 1, 1, false, true, true, false, true));
						foot_right_table.addCell(foot_bottom_right_cell73);

						PdfPCell foot_bottom_right_cell83 = new PdfPCell(
								PdfStyle.createUltimateCellWithBorderWithDisableBorder(("\n"), Element.ALIGN_RIGHT,
										Element.ALIGN_TOP, 8, 14f, true, 1, 1, false, true, true, false, true));
						foot_right_table.addCell(foot_bottom_right_cell83);
					}
				}

				// if Total condition without colour for Palson PAL -- change by abhishek

				if (comp_cd.equals("PAL")) {

					PdfPCell foot_bottom_right_cell7 = new PdfPCell(
							PdfStyle.createUltimateCellWithBorderWithDisableBorder(("Total Amount"), Element.ALIGN_LEFT,
									Element.ALIGN_TOP, 8, 7f, true, 1, 1, false, true, true, false, true));
					foot_right_table.addCell(foot_bottom_right_cell7);
				}

				else {
					if (show_amount.equals("Y")) {
						PdfPCell foot_bottom_right_cell7 = new PdfPCell(PdfStyle
								.createUltimateCellWithBorderWithDisableBorder(("Total Amount"), Element.ALIGN_LEFT,
										Element.ALIGN_TOP, 8, 14f, true, 1, 1, false, true, true, false, true));
						foot_bottom_right_cell7.setGrayFill(0.7f);
						foot_right_table.addCell(foot_bottom_right_cell7); // end hear
					} else {
						PdfPCell foot_bottom_right_cell7 = new PdfPCell(
								PdfStyle.createUltimateCellWithBorderWithDisableBorder(("\n"), Element.ALIGN_LEFT,
										Element.ALIGN_TOP, 8, 14f, true, 1, 1, false, true, true, false, true));
						foot_bottom_right_cell7.setGrayFill(0.7f);
						foot_right_table.addCell(foot_bottom_right_cell7);
					}
				}

				if (companyCode.equals("P&G")) {

					PdfPCell foot_bottom_right_cell8 = new PdfPCell(
							PdfStyle.createUltimateCellWithBorderWithDisableBorder(("0.00"), Element.ALIGN_RIGHT,
									Element.ALIGN_TOP, 8, 14f, true, 1, 1, false, true, true, false, true));
					foot_bottom_right_cell8.setGrayFill(0.7f);
					foot_right_table.addCell(foot_bottom_right_cell8);
				}

				if (companyCode.equals("PAL")) {

					PdfPCell foot_bottom_right_cell8 = new PdfPCell(
							PdfStyle.createUltimateCellWithBorderWithDisableBorder(("0.00"), Element.ALIGN_RIGHT,
									Element.ALIGN_TOP, 8, 10f, true, 1, 1, false, true, true, false, true));
					foot_right_table.addCell(foot_bottom_right_cell8);

				} else {
					if (show_amount.equals("Y")) {
						PdfPCell foot_bottom_right_cell8 = new PdfPCell(
								PdfStyle.createUltimateCellWithBorderWithDisableBorder(
										(final_total.setScale(2, BigDecimal.ROUND_HALF_UP).toString()),
										Element.ALIGN_RIGHT, Element.ALIGN_TOP, 8, 14f, true, 1, 1, false, true, true,
										false, true));
						foot_bottom_right_cell8.setGrayFill(0.7f);
						foot_right_table.addCell(foot_bottom_right_cell8);
					} else {
						PdfPCell foot_bottom_right_cell8 = new PdfPCell(
								PdfStyle.createUltimateCellWithBorderWithDisableBorder(("\n"), Element.ALIGN_RIGHT,
										Element.ALIGN_TOP, 8, 14f, true, 1, 1, false, true, true, false, true));
						foot_bottom_right_cell8.setGrayFill(0.7f);
						foot_right_table.addCell(foot_bottom_right_cell8);
					}
				}

				if (companyCode.equals("PAL")) {
					PdfPCell foot_bottom_right_cell7_ = new PdfPCell(
							PdfStyle.createUltimateCellWithBorderWithDisableBorder((" "), Element.ALIGN_LEFT,
									Element.ALIGN_TOP, 8, 7f, true, 1, 1, false, true, true, false, true));
					foot_right_table.addCell(foot_bottom_right_cell7_);
					PdfPCell foot_bottom_right_cell8_ = new PdfPCell(
							PdfStyle.createUltimateCellWithBorderWithDisableBorder((" "), Element.ALIGN_LEFT,
									Element.ALIGN_TOP, 8, 10f, true, 1, 1, false, true, true, false, true));
					foot_right_table.addCell(foot_bottom_right_cell8_);
				}

			} else {
				PdfPCell foot_bottom_right_cell1 = new PdfPCell(
						PdfStyle.createUltimateCellWithBorderWithDisableBorder((""), Element.ALIGN_LEFT,
								Element.ALIGN_TOP, 8, 14f, true, 1, 1, false, true, true, false, true));
				foot_right_table.addCell(foot_bottom_right_cell1);
				PdfPCell foot_bottom_right_cell2 = new PdfPCell(
						PdfStyle.createUltimateCellWithBorderWithDisableBorder((""), Element.ALIGN_RIGHT,
								Element.ALIGN_TOP, 8, 14f, true, 1, 1, false, true, true, false, true));
				foot_right_table.addCell(foot_bottom_right_cell2);

				if (gst_doc_type.contains("INTER")) {
					PdfPCell foot_bottom_right_cell33 = new PdfPCell(
							PdfStyle.createUltimateCellWithBorderWithDisableBorder((""), Element.ALIGN_LEFT,
									Element.ALIGN_TOP, 8, 14f, true, 1, 1, false, true, true, false, true));
					foot_right_table.addCell(foot_bottom_right_cell33);
					PdfPCell foot_bottom_right_cell43 = new PdfPCell(
							PdfStyle.createUltimateCellWithBorderWithDisableBorder((""), Element.ALIGN_RIGHT,
									Element.ALIGN_TOP, 8, 14f, true, 1, 1, false, true, true, false, true));
					foot_right_table.addCell(foot_bottom_right_cell43);
				} else if (gst_doc_type.contains("INTRA")) {
					PdfPCell foot_bottom_right_cell3 = new PdfPCell(
							PdfStyle.createUltimateCellWithBorderWithDisableBorder((""), Element.ALIGN_LEFT,
									Element.ALIGN_TOP, 8, 14f, true, 1, 1, false, true, true, false, true));
					foot_right_table.addCell(foot_bottom_right_cell3);
					PdfPCell foot_bottom_right_cell4 = new PdfPCell(
							PdfStyle.createUltimateCellWithBorderWithDisableBorder((""), Element.ALIGN_RIGHT,
									Element.ALIGN_TOP, 8, 14f, true, 1, 1, false, true, true, false, true));
					foot_right_table.addCell(foot_bottom_right_cell4);
					PdfPCell foot_bottom_right_cell5 = new PdfPCell(
							PdfStyle.createUltimateCellWithBorderWithDisableBorder((""), Element.ALIGN_LEFT,
									Element.ALIGN_TOP, 8, 14f, true, 1, 1, false, true, true, false, true));
					foot_right_table.addCell(foot_bottom_right_cell5);
					PdfPCell foot_bottom_right_cell6 = new PdfPCell(
							PdfStyle.createUltimateCellWithBorderWithDisableBorder((""), Element.ALIGN_RIGHT,
									Element.ALIGN_TOP, 8, 14f, true, 1, 1, false, true, true, false, true));
					foot_right_table.addCell(foot_bottom_right_cell6);
				}

				PdfPCell foot_bottom_right_cell73 = new PdfPCell(
						PdfStyle.createUltimateCellWithBorderWithDisableBorder((""), Element.ALIGN_LEFT,
								Element.ALIGN_TOP, 8, 14f, true, 1, 1, false, true, true, false, true));
				foot_right_table.addCell(foot_bottom_right_cell73);
				PdfPCell foot_bottom_right_cell83 = new PdfPCell(
						PdfStyle.createUltimateCellWithBorderWithDisableBorder((""), Element.ALIGN_RIGHT,
								Element.ALIGN_TOP, 8, 14f, true, 1, 1, false, true, true, false, true));
				foot_right_table.addCell(foot_bottom_right_cell83);

				PdfPCell foot_bottom_right_cell7 = new PdfPCell(
						PdfStyle.createUltimateCellWithBorderWithDisableBorder((""), Element.ALIGN_LEFT,
								Element.ALIGN_TOP, 8, 14f, true, 1, 1, false, true, true, false, true));
				foot_bottom_right_cell7.setGrayFill(0.7f);
				foot_right_table.addCell(foot_bottom_right_cell7); // end hear
				PdfPCell foot_bottom_right_cell8 = new PdfPCell(
						PdfStyle.createUltimateCellWithBorderWithDisableBorder((""), Element.ALIGN_RIGHT,
								Element.ALIGN_TOP, 8, 14f, true, 1, 1, false, true, true, false, true));
				foot_bottom_right_cell8.setGrayFill(0.7f);
				foot_right_table.addCell(foot_bottom_right_cell8);

			}

			// end hear
			// footer bottom2
			PdfPTable foot_bottom_table2 = new PdfPTable(3);
			foot_bottom_table2.setWidthPercentage(100);
			foot_bottom_table2.setWidths(new float[] { 3.33f, 2.17f, 4.5f });

			// String company_name = (String) context.getAttribute("company_name");

			PdfPCell foot_bottom_cell02 = new PdfPCell(PdfStyle.createUltimateCellWithBorderWithDisableBorder(
					("Transporter Details"), Element.ALIGN_CENTER, Element.ALIGN_TOP, 9, 14f, true, 1, 1, false, true,
					true, false, true));
			foot_bottom_table2.addCell(foot_bottom_cell02);

			PdfPCell foot_bottom_cell03 = new PdfPCell(PdfStyle.createUltimateCellWithBorderWithDisableBorder((""),
					Element.ALIGN_LEFT, Element.ALIGN_TOP, 8, 14f, true, 1, 1, false, true, true, false, true));
			foot_bottom_table2.addCell(foot_bottom_cell03);

			PdfPCell foot_bottom_cell04 = null;
			if (frm_challan.contains("PIP")) {
				if (comp_cd.equalsIgnoreCase("PFZ")) {
					foot_bottom_cell04 = new PdfPCell(PdfStyle.createUltimateCellWithBorderWithDisableBorder(
							("For Pfizer Products India Private Limited."), Element.ALIGN_CENTER, Element.ALIGN_TOP, 9,
							14f, true, 1, 1, false, true, true, false, true));
				} else { // adding new footer indicator
					if (footer_ind && comp_cd.equalsIgnoreCase("NIL") || comp_cd.equalsIgnoreCase("NHP")) // new check
																											// for
																											// footer
																											// indicator
																											// and
																											// companey
					// code
					{
						foot_bottom_cell04 = new PdfPCell(PdfStyle.createUltimateCellWithBorderWithDisableBorder(
								("For " + company_name), Element.ALIGN_CENTER, Element.ALIGN_TOP, 9, 14f, true, 1, 1,
								false, true, true, false, true));
					} else {
						foot_bottom_cell04 = new PdfPCell(
								PdfStyle.createUltimateCellWithBorderWithDisableBorder((" "), Element.ALIGN_CENTER,
										Element.ALIGN_TOP, 9, 14f, true, 1, 1, false, true, true, false, true));
					}
				}
				foot_bottom_table2.addCell(foot_bottom_cell04);
			} else {
				if (footer_ind && comp_cd.equalsIgnoreCase("NIL") || comp_cd.equalsIgnoreCase("NHP")) { // new check for
																										// footer
																										// indicator and
																										// companey code
					foot_bottom_cell04 = new PdfPCell(PdfStyle.createUltimateCellWithBorderWithDisableBorder(
							("For " + company_name), Element.ALIGN_CENTER, Element.ALIGN_TOP, 9, 14f, true, 1, 1, false,
							true, true, false, true));
				} else if (comp_cd.equalsIgnoreCase("P&G") || comp_cd.equalsIgnoreCase("PAL")) {
					foot_bottom_cell04 = new PdfPCell(PdfStyle.createUltimateCellWithBorderWithDisableBorder(
							("For " + company_name), Element.ALIGN_CENTER, Element.ALIGN_TOP, 9, 14f, true, 1, 1, false,
							true, true, false, true));

				} else {
					foot_bottom_cell04 = new PdfPCell(
							PdfStyle.createUltimateCellWithBorderWithDisableBorder((" "), Element.ALIGN_CENTER,
									Element.ALIGN_TOP, 9, 14f, true, 1, 1, false, true, true, false, true));
				}

				foot_bottom_table2.addCell(foot_bottom_cell04);

			}

			PdfPCell foot_bottom_cell21 = new PdfPCell(
					PdfStyle.createUltimateCellWithBorderWithDisableBorder(("Transporter Name :" + dsp_transporter),
							Element.ALIGN_LEFT, Element.ALIGN_TOP, 8, 14f, true, 1, 1, false, true, true, false, true));
			foot_bottom_table2.addCell(foot_bottom_cell21);
			PdfPCell foot_bottom_cell22 = new PdfPCell(PdfStyle.createUltimateCellWithBorderWithDisableBorder((""),
					Element.ALIGN_CENTER, Element.ALIGN_TOP, 8, 14f, true, 1, 1, false, true, true, false, true));
			foot_bottom_table2.addCell(foot_bottom_cell22);
			PdfPCell foot_bottom_cell32 = new PdfPCell(PdfStyle.createUltimateCellWithBorderWithDisableBorder((""),
					Element.ALIGN_CENTER, Element.ALIGN_TOP, 8, 14f, true, 1, 1, false, false, true, false, true));
			foot_bottom_table2.addCell(foot_bottom_cell32);

			PdfPCell foot_bottom_cell06 = new PdfPCell(
					PdfStyle.createUltimateCellWithBorderWithDisableBorder(("LR No:" + ship_doc_no), Element.ALIGN_LEFT,
							Element.ALIGN_TOP, 8, 14f, true, 1, 1, false, true, true, false, true));
			foot_bottom_table2.addCell(foot_bottom_cell06);
			PdfPCell foot_bottom_cell42 = new PdfPCell(PdfStyle.createUltimateCellWithBorderWithDisableBorder(
					("LR Date : " + (date != null ? Utility.getRptDateFormat(date) : "")), Element.ALIGN_LEFT,
					Element.ALIGN_TOP, 8, 14f, true, 1, 1, false, true, true, false, true));
			foot_bottom_table2.addCell(foot_bottom_cell42);
			PdfPCell foot_bottom_cell07 = new PdfPCell(PdfStyle.createUltimateCellWithBorderWithDisableBorder((""),
					Element.ALIGN_LEFT, Element.ALIGN_TOP, 8, 14f, true, 1, 1, false, false, true, false, true));
			foot_bottom_table2.addCell(foot_bottom_cell07);

			PdfPCell foot_bottom_cell52 = null;
			System.out.println("dsp_lorry_no::" + dsp_lorry_no);
			if (comp_cd.equalsIgnoreCase("NIL") || comp_cd.equalsIgnoreCase("NHP")) {
				foot_bottom_cell52 = new PdfPCell(PdfStyle.createUltimateCellWithBorderWithDisableBorder(
						(dsp_lorry_no == null ? "E-Way Bill No: " : "E-Way Bill No: " + dsp_lorry_no),
						Element.ALIGN_LEFT, Element.ALIGN_TOP, 8, 14f, true, 1, 1, false, true, true, false, true));
			} else {
				foot_bottom_cell52 = new PdfPCell(
						PdfStyle.createUltimateCellWithBorderWithDisableBorder(("Way Bill No:"), Element.ALIGN_LEFT,
								Element.ALIGN_TOP, 8, 14f, true, 1, 1, false, true, true, false, true));
			}

			foot_bottom_table2.addCell(foot_bottom_cell52);
			PdfPCell foot_bottom_cell62 = new PdfPCell(
					PdfStyle.createUltimateCellWithBorderWithDisableBorder(("Date :"), Element.ALIGN_LEFT,
							Element.ALIGN_TOP, 8, 14f, true, 1, 1, false, true, true, false, true));
			foot_bottom_table2.addCell(foot_bottom_cell62);
			PdfPCell foot_bottom_cell08 = new PdfPCell(PdfStyle.createUltimateCellWithBorderWithDisableBorder((""),
					Element.ALIGN_LEFT, Element.ALIGN_TOP, 8, 14f, true, 1, 1, false, false, true, false, true));
			foot_bottom_table2.addCell(foot_bottom_cell08);

			PdfPCell foot_bottom_cell72 = new PdfPCell(
					PdfStyle.createUltimateCellWithBorderWithDisableBorder(("No Of Cases :" + " " + cases),
							Element.ALIGN_LEFT, Element.ALIGN_TOP, 8, 14f, true, 1, 1, false, true, true, false, true));
			foot_bottom_table2.addCell(foot_bottom_cell72);
			PdfPCell foot_bottom_cell82 = new PdfPCell(
					PdfStyle.createUltimateCellWithBorderWithDisableBorder(("Weight :" + "  " + weigh),
							Element.ALIGN_LEFT, Element.ALIGN_TOP, 8, 14f, true, 1, 1, false, true, true, false, true));
			foot_bottom_table2.addCell(foot_bottom_cell82);
			PdfPCell foot_bottom_cell09 = new PdfPCell(PdfStyle.createUltimateCellWithBorderWithDisableBorder((""),
					Element.ALIGN_LEFT, Element.ALIGN_TOP, 8, 14f, true, 1, 1, false, false, true, false, true));
			foot_bottom_table2.addCell(foot_bottom_cell09);

			PdfPCell foot_bottom_cell92 = new PdfPCell(
					PdfStyle.createUltimateCellWithBorderWithDisableBorder(("Remarks : " + dsp_challan_msg),
							Element.ALIGN_LEFT, Element.ALIGN_TOP, 8, 14f, true, 1, 1, false, true, true, false, true));
			foot_bottom_table2.addCell(foot_bottom_cell92);
			PdfPCell foot_bottom_cell10 = new PdfPCell(PdfStyle.createUltimateCellWithBorderWithDisableBorder((""),
					Element.ALIGN_LEFT, Element.ALIGN_TOP, 8, 14f, true, 1, 1, false, false, true, true, true));
			foot_bottom_table2.addCell(foot_bottom_cell10);

			// new changes for remove column in pdf cell by vivek based on footer indicator

			if (footer_ind) {
				PdfPCell foot_bottom_cell11 = new PdfPCell(PdfStyle.createUltimateCellWithBorderWithDisableBorder(
						("Authorized Signatory"), Element.ALIGN_CENTER, Element.ALIGN_TOP, 9, 14f, true, 1, 1, false,
						true, true, false, true));
				foot_bottom_table2.addCell(foot_bottom_cell11);
			} else {
				PdfPCell foot_bottom_cell11 = new PdfPCell(PdfStyle.createUltimateCellWithBorderWithDisableBorder((""),
						Element.ALIGN_CENTER, Element.ALIGN_TOP, 9, 14f, true, 1, 1, false, true, true, false, true));
				foot_bottom_table2.addCell(foot_bottom_cell11);
			}

			// footer bottom3
			PdfPTable foot_bottom_table3 = new PdfPTable(1);
			foot_bottom_table3.setWidthPercentage(100);
			foot_bottom_table3.setWidths(new float[] { 10f });

			// Print changes made for novartis
			// added boxes in copies description
			PdfPCell foot_bottom_cell31 = null;
			if (comp_cd.equalsIgnoreCase("NIL") || comp_cd.equalsIgnoreCase("NHP")) {
				Paragraph p = new Paragraph();
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
				foot_bottom_cell31 = new PdfPCell(PdfStyle.createUltimateCellWithBorderWithDisableBorder(p,
						Element.ALIGN_CENTER, Element.ALIGN_TOP, 9, 14f, true, 1, 1, true, true, false, false, false));
			} else {
				foot_bottom_cell31 = new PdfPCell(PdfStyle.createUltimateCellWithBorderWithDisableBorder(
						"Copies: 1: original for consignee;   2. duplicate for transporter;   3. triplicate for cosignor;   4. quadruplicate:  book copy ",
						Element.ALIGN_CENTER, Element.ALIGN_TOP, 9, 14f, true, 1, 1, true, true, false, false, false));
			}
			foot_bottom_table3.addCell(foot_bottom_cell31);

			PdfPCell bottom_left_cell = new PdfPCell(foot_left_table);
			PdfPCell bottom_right_cell = new PdfPCell(foot_right_table);
			PdfPCell bottom_2_cell = new PdfPCell(foot_bottom_table2);
			bottom_2_cell.setColspan(2);
			PdfPCell bottom_3_cell = new PdfPCell(foot_bottom_table3);
			bottom_3_cell.setColspan(2);

			maintable.addCell(bottom_left_cell);
			maintable.addCell(bottom_right_cell);
			maintable.addCell(bottom_2_cell);
			maintable.addCell(bottom_3_cell);

			// -----------------------------------------------------------------------------------------------------------------

			if (pageflag) {
// System.out.println("pagecount : " +pagecount);
				cell = new PdfPCell(PdfStyle.createLabelCellWithoutBorderSmallFontNoBold("Page No. " + pagecount));
				cell.setBorder(Rectangle.NO_BORDER);
				cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
				cell.setVerticalAlignment(Element.ALIGN_BOTTOM);
				cell.setColspan(3);
				maintable.addCell(cell);
			}

		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("Error Occurred :" + e.getMessage());// uncomment asneeded --;
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

	@Override
	public String getPreprintedDetailedChallanPrintNoGst(Integer division, Integer loc, String frm_challan,
			String to_challan, String dispatchType, String prodtype, String printtype,
			List<ViewPrePrintedDetailChallan> challans, String show_amount, String footer_signature_ind,
			String companyCode, String companyName, HttpSession session, String loc_narration,
			HttpServletRequest request) throws Exception {
		limit = 13;
		System.out.println("In get pdf ani" + "loc value:::" + loc_narration);
		String path = MedicoConstants.PDF_FILE_PATH;
		File file1 = new File(path);
		file1.mkdirs();
		comp_cd = companyCode;
		company_name = companyName;
		setPath(path);

		System.out.println("PDF Generation Started");
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
		Rectangle rect = new Rectangle(20, 28, 580, 820);
		writer.setBoxSize("header", rect);
		rect = new Rectangle(20, 2, 580, 261);
		writer.setBoxSize("footer", rect);
		int pagecount = 1;
		FooterWithoutPageNo event = new FooterWithoutPageNo();
		writer.setPageEvent(event);

		String uname = this.tokenProvider.getUsernameFromRequest(request);
		String empId = this.userMasterService.getEmpIdByUsername(uname);
		writer = usermasterservice.generatePDFlock(empId, writer);
		document.open();
		document.setMarginMirroringTopBottom(false);
		boolean isnew = false;
		// boolean for_new_page = false;
		ColumnText column = new ColumnText(writer.getDirectContent());
		String imagePath = "";
		try {
			if (comp_cd.trim().equalsIgnoreCase("MDL")) {
				imagePath = "D:\\sg\\files\\medley-logo.jpg";
				Image image = Image.getInstance(imagePath);
				image.setAbsolutePosition(280, 680);
				image.scaleToFit(70, 70);
				document.add(image);
			}

			BigDecimal total = new BigDecimal(0);

			Set<String> challanset = new LinkedHashSet<String>();
			for (ViewPrePrintedDetailChallan challan : challans) {
				challanset.add(challan.getDsp_id().toString());
			}
			PdfPTable bodytable = null;
			boolean footer_ind = false;
			for (String num : challanset) {
				List<ViewPrePrintedDetailChallan> list_new_page = new ArrayList<ViewPrePrintedDetailChallan>();
				isnew = true;
				// for_new_page=true;
				PdfPTable maintable1 = new PdfPTable(1);
				maintable1.setWidthPercentage(100);
				PdfPCell cell = null;
				pageflag = false;
				pagecount = 1;

				BigDecimal goods_total = BigDecimal.ZERO;
				BigDecimal final_total = BigDecimal.ZERO;
				BigDecimal header_Igst = BigDecimal.ZERO;
				BigDecimal header_Cgst = BigDecimal.ZERO;
				BigDecimal header_Sgst = BigDecimal.ZERO;
				BigDecimal round_off = BigDecimal.ZERO;

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
				String gst_doc_type = "";
				String dsp_lorry_no = "";
				// int count_var=0;
				for (ViewPrePrintedDetailChallan challan : challans) {
					// System.out.println("Alloc Remark
					// "+challan.getAlloc_remark());

					if (num.equalsIgnoreCase(challan.getDsp_id().toString())) {
						if (rows >= limit) {
							// count_var++;

							if (footer_signature_ind.equalsIgnoreCase("Y")) {
								footer_ind = false;// suppress footer signature
							} else {
								footer_ind = true;// show footer signature
							}
							// System.out.println("getDoc_no " + challan.getDsp_challan_no());
							pageflag = true;

							bodytable = newPageNoGst(column, bodytable, event, printLabel, false, rows, pageflag,
									pagecount, total, division, loc, frm_challan, to_challan, dispatchType, prodtype,
									path, challans, show_amount, footer_ind, challan.getDsp_transporter(),
									challan.getShip_doc_no(), challan.getShip_doc_date(), challan.getCases().toString(),
									challan.getWeigh().toString(),
									challan.getAlloc_remark().length() > 600
											? challan.getAlloc_remark().substring(0, 600)
											: challan.getAlloc_remark(),
									goods_total, final_total, header_Igst, header_Cgst, header_Sgst, round_off,
									gst_doc_type, dsp_lorry_no, loc_narration, bodyTableWidth, companyCode);

							document.newPage();
							if (comp_cd.trim().equalsIgnoreCase("MDL")) {
								imagePath = "D:\\sg\\files\\medley-logo.jpg";
								Image image = Image.getInstance(imagePath);
								image.setAbsolutePosition(280, 680);
								image.scaleToFit(70, 70);
								document.add(image);
							}
							pagecount++;
							rows = 0;

						}
						if (isnew) {
							footerNoGst = challan;
							maintable1.addCell(PdfStyle.createLabelCellWithoutBorderSmallFontNoBold(""));
							hcell = createHeader(challan, pagecount, division, loc, frm_challan, to_challan,
									dispatchType, prodtype, path, show_amount, companyName, session);
							cell = new PdfPCell(hcell);
							cell.setBorder(0);
							cell.setHorizontalAlignment(Element.ALIGN_CENTER);
							maintable1.addCell(cell);
							event.setHeader(maintable1);
							isnew = false;
							list_new_page.add(challan);
						}

						if (rows == 0) { // changes made for unichem testing
							// need to recheck 2 aug 2017
							bodytable.addCell(PdfStyle.createValueCellWithHeight((""), false, true, 18f));
							bodytable.addCell(PdfStyle.createValueCellWithHeight((""), false, true, 18f));
							bodytable.addCell(PdfStyle.createValueCellWithHeight((""), false, true, 18f));
							bodytable.addCell(PdfStyle.createValueCellWithHeight((""), true, true, 18f));
							bodytable.addCell(PdfStyle.createValueCellWithHeight((""), true, true, 18f));
							bodytable.addCell(PdfStyle.createValueCellWithHeight((""), true, true, 18f));

							/*
							 * bodytable.addCell(PdfStyle. createValueCellWithHeight((" "),false,true,18f));
							 * bodytable.addCell(PdfStyle. createValueCellWithHeight((""),false,true,18f));
							 * bodytable.addCell(PdfStyle. createValueCellWithHeight((""),false,true,18f));
							 * bodytable.addCell(PdfStyle. createValueCellWithHeight((""),true,true,18f));
							 * bodytable.addCell(PdfStyle. createValueCellWithHeight((""),true,true,18f));
							 * bodytable.addCell(PdfStyle. createValueCellWithHeight((""),true,true,18f));
							 */
						}
						bodytable.addCell(PdfStyle.createUltimateCellWithBorderWithDisableBorder(
								(challan.getProduct_desc()), Element.ALIGN_LEFT, Element.ALIGN_TOP, 8, 18f, false, 1, 1,
								false, false, false, true, false));
						bodytable.addCell(PdfStyle.createUltimateCellWithBorderWithDisableBorder(
								(challan.getHsn_code()), Element.ALIGN_LEFT, Element.ALIGN_TOP, 8, 18f, false, 1, 1,
								false, false, false, true, false));
						bodytable.addCell(PdfStyle.createUltimateCellWithBorderWithDisableBorder(
								(challan.getBatch_no()), Element.ALIGN_LEFT, Element.ALIGN_TOP, 8, 18f, false, 1, 1,
								false, false, false, true, false));
						bodytable.addCell(PdfStyle.createUltimateCellWithBorderWithDisableBorder(
								(challan.getExpiry_date()), Element.ALIGN_CENTER, Element.ALIGN_TOP, 8, 18f, false, 1,
								1, false, false, false, true, false));
						bodytable.addCell(PdfStyle.createUltimateCellWithBorderWithDisableBorder(
								(challan.getTotal_qty().toString()), Element.ALIGN_CENTER, Element.ALIGN_TOP, 8, 18f,
								false, 1, 1, false, false, false, true, false));
						bodytable.addCell(PdfStyle.createUltimateCellWithBorderWithDisableBorder(
								(challan.getValue() == null ? BigDecimal.ZERO.setScale(2, RoundingMode.HALF_UP)
										: challan.getValue().setScale(2, RoundingMode.HALF_UP)).toString(),
								Element.ALIGN_RIGHT, Element.ALIGN_TOP, 8, 18f, false, 1, 1, false, false, false, true,
								false));

//     if ((challan.getIgst_bill_amt().compareTo(BigDecimal.ZERO) > 0)) {
//        bodytable.addCell(PdfStyle.createUltimateCellWithBorderWithDisableBorder(("IGST% "),
//        Element.ALIGN_RIGHT, Element.ALIGN_TOP, 8, 18f, true, 1, 1, false, false, false,
//        true, false));
//        bodytable.addCell(PdfStyle.createUltimateCellWithBorderWithDisableBorder(
//        (challan.getIgst_rate()).toString(), Element.ALIGN_RIGHT, Element.ALIGN_TOP, 8,
//        18f, true, 1, 1, false, false, false, true, false));
//        bodytable.addCell(PdfStyle.createUltimateCellWithBorderWithDisableBorder(("IGST"),
//        Element.ALIGN_RIGHT, Element.ALIGN_TOP, 8, 18f, true, 1, 1, false, false, false,
//        true, false));
//        bodytable
//        .addCell(
//        PdfStyle.createUltimateCellWithBorderWithDisableBorder(
//        (challan.getIgst_bill_amt() == null
//        ? BigDecimal.ZERO.setScale(2, RoundingMode.HALF_UP)
//        : challan.getIgst_bill_amt().setScale(2,
//        RoundingMode.HALF_UP)).toPlainString(),
//        Element.ALIGN_RIGHT, Element.ALIGN_TOP, 8, 18f, true, 1, 1, false,
//        false, false, true, false));
//        bodytable.addCell(
//        PdfStyle.createUltimateCellWithBorderWithDisableBorder((""), Element.ALIGN_LEFT,
//        Element.ALIGN_TOP, 8, 18f, true, 1, 1, false, false, false, true, false));
//        bodytable.addCell(
//        PdfStyle.createUltimateCellWithBorderWithDisableBorder((""), Element.ALIGN_LEFT,
//        Element.ALIGN_TOP, 8, 18f, true, 1, 1, false, false, false, true, false));
//     }
//     else {
//        bodytable
//        .addCell(PdfStyle
//        .createUltimateCellWithBorderWithDisableBorder(
//        ("CGST% :" + challan.getCgst_rate()
//        + "                                                                        CGST :"
//        + challan.getCgst_bill_amt().setScale(2,
//        RoundingMode.HALF_UP)),
//        Element.ALIGN_LEFT, Element.ALIGN_TOP, 8, 18f, true, 1, 1, false,
//        false, false, true, false));
//        bodytable.addCell(PdfStyle.createUltimateCellWithBorderWithDisableBorder(("SGST%"),
//        Element.ALIGN_RIGHT, Element.ALIGN_TOP, 8, 18f, true, 1, 1, false, false, false,
//        true, false));
//        bodytable.addCell(PdfStyle.createUltimateCellWithBorderWithDisableBorder(
//        (challan.getSgst_rate().toString()), Element.ALIGN_RIGHT, Element.ALIGN_TOP, 8, 18f,
//        true, 1, 1, false, false, false, true, false));
//        bodytable.addCell(PdfStyle.createUltimateCellWithBorderWithDisableBorder(
//        ("SGST").toString(), Element.ALIGN_RIGHT, Element.ALIGN_TOP, 8, 18f, true, 1, 1,
//        false, false, false, true, false));
//        bodytable.addCell(PdfStyle.createUltimateCellWithBorderWithDisableBorder(
//        (challan.getSgst_bill_amt().toString()), Element.ALIGN_RIGHT, Element.ALIGN_TOP, 8,
//        18f, true, 1, 1, false, false, false, true, false));
//        bodytable.addCell(
//        PdfStyle.createUltimateCellWithBorderWithDisableBorder((""), Element.ALIGN_RIGHT,
//        Element.ALIGN_TOP, 8, 18f, true, 1, 1, false, false, false, true, false));
//     }
						goods_total = goods_total.add(challan.getValue());
//     final_total = goods_total.add(challan.getHigst_bill_amt().add(challan.getHcgst_bill_amt()
//     .add(challan.getHsgst_bill_amt().add(challan.getRoundoff()))));
//     header_Igst = challan.getHigst_bill_amt();
//     header_Sgst = challan.getHsgst_bill_amt();
//     header_Cgst = challan.getHcgst_bill_amt();
//     round_off = challan.getRoundoff();
//     gst_doc_type = challan.getGst_description();

						rows++;
						dtp = "";
						ship_doc_no = "";
						date = "";
						cases = "";
						weigh = "";
						dsp_challan_msg = "";
						dsp_lorry_no = "";

						dtp = challan.getDsp_transporter();
						ship_doc_no = challan.getShip_doc_no();
						date = challan.getShip_doc_date();
						cases = challan.getCases().setScale(2).toString();
						weigh = challan.getWeigh().toString();
						// dsp_challan_msg is the remark that is currently
						// printed.
						dsp_challan_msg = challan.getAlloc_remark().length() > 600
								? challan.getAlloc_remark().substring(0, 600)
								: challan.getAlloc_remark();
						dsp_lorry_no = challan.getDsp_lorry_no();
					}
				}

				footer_ind = true;
				// System.out.println("Outside rows>=limit");
				newPageNoGst(column, bodytable, event, printLabel, true, rows, pageflag, pagecount, total, division,
						loc, frm_challan, to_challan, dispatchType, prodtype, path, list_new_page, show_amount,
						footer_ind, dtp, ship_doc_no, date, cases, weigh, dsp_challan_msg, goods_total, final_total,
						header_Igst, header_Cgst, header_Sgst, round_off, gst_doc_type, dsp_lorry_no, loc_narration,
						bodyTableWidth, companyCode);
				document.newPage();
//				if (comp_cd.trim().equalsIgnoreCase("MDL")) {
//					imagePath = "D:\\sg\\files\\medley-logo.jpg";
//					Image image = Image.getInstance(imagePath);
//					image.setAbsolutePosition(280, 680);
//					image.scaleToFit(70, 70);
//					document.add(image);
//				}
			}

		} catch (Exception e) {
			System.out.println("Error Occurred :" + e.getMessage());// uncomment asneeded --;
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
				System.out.println("Error Occurred :" + e.getMessage());// uncomment asneeded --;
			}
		}
		return filename;

	}

	private PdfPTable createHeader(ViewPrePrintedDetailChallan challan, int pagecount, int division, Integer loc,
			String frm_challan, String to_challan, String dispatchType, String prodtype, String path,
			String show_amount, String companyName, HttpSession session) throws Exception {

		System.out.println("inside  thiss++++++++");
		PdfPTable headertable = new PdfPTable(1);
		PdfPTable headertable01 = new PdfPTable(1);// Billing Address
		PdfPTable headertable2 = new PdfPTable(4);
		PdfPTable headertable3 = new PdfPTable(4);
		PdfPTable headertable4 = new PdfPTable(4);
		PdfPTable bodytable = null;
		String FOX = "path/to/resource/fox.png";
		headertable.setWidthPercentage(100);
		headertable01.setWidthPercentage(100);
		headertable2.setWidthPercentage(100);
		headertable3.setWidthPercentage(100);
		headertable4.setWidthPercentage(100);

		headertable01.setWidths(new float[] { 7f });
		headertable2.setWidths(new float[] { 4.5f, 2.5f, 1f, 2f });
		headertable3.setWidths(new float[] { 4.5f, 2.5f, 1f, 2f });
		headertable4.setWidths(new float[] { 2f, 2f, 2.5f, 3.5f });

		Font font = new Font(FontFamily.HELVETICA, 13, Font.BOLD, BaseColor.BLACK);
		Font font2 = new Font(FontFamily.HELVETICA, 9, Font.BOLD, BaseColor.BLACK);
		Font font3 = new Font(FontFamily.HELVETICA, 8f, Font.BOLD, BaseColor.BLACK);
		PdfPTable headertable1 = new PdfPTable(1);
		headertable1.setWidthPercentage(100);
		headertable1.setWidths(new float[] { 5f });

// ======================================================================================================================
// 1
		PdfPCell hcell = null;

		if (comp_cd.equalsIgnoreCase("PFZ")) {
			hcell = new PdfPCell(new Phrase("Delivery Challan", font));
		} else if (comp_cd.equalsIgnoreCase("UCM")) {
			hcell = new PdfPCell(new Phrase("TAX INVOICE", font));
		} else if (comp_cd.equalsIgnoreCase("NIL") || comp_cd.equalsIgnoreCase("NHP")) {
			hcell = new PdfPCell(new Phrase("TAX INVOICE", font));
			hcell.disableBorderSide(Rectangle.BOTTOM);
		} else {
			hcell = new PdfPCell(new Phrase("Delivery Challan", font));
		}

		hcell.setHorizontalAlignment(Element.ALIGN_CENTER);
		hcell.setVerticalAlignment(Element.ALIGN_CENTER);

		PdfPCell hcell_1 = new PdfPCell(new Phrase(" ", font));
		hcell_1.setBorder(Rectangle.LEFT);
		hcell_1.setHorizontalAlignment(Element.ALIGN_CENTER);
		headertable01.addCell(hcell);
		PdfPCell hcell2 = null;
		if (comp_cd.equalsIgnoreCase("NIL") || comp_cd.equalsIgnoreCase("NHP")) {
			hcell2 = new PdfPCell(new Phrase("(Rule 46 of CGST Rules,2017)", font2));
			hcell2.setHorizontalAlignment(Element.ALIGN_CENTER);
			hcell2.setVerticalAlignment(Element.ALIGN_CENTER);
			hcell2.disableBorderSide(Rectangle.TOP);
			headertable01.addCell(hcell2);
		}

// =================================================Blank Cell Added To
// Remove Table Border==============================

		PdfPCell hcell_blank = new PdfPCell(headertable01);
		hcell_blank.setBorder(Rectangle.RIGHT);
		hcell_blank.disableBorderSide(Rectangle.BOTTOM);

// ======================================================================================================================
// 2.1
//String company_name = (String) context.getAttribute("company_name");

		StringBuilder result1 = new StringBuilder();
		
		if(comp_cd.trim().equalsIgnoreCase("MDL") ||comp_cd.trim().equalsIgnoreCase("SNK")) {
			result1.append(
					challan.getLoc_add1().length() > 65 ? challan.getLoc_add1().substring(0, 65) : challan.getLoc_add1());
					result1.append(System.lineSeparator());
		}


		result1.append(
				challan.getLoc_add2().length() > 65 ? challan.getLoc_add2().substring(0, 65) : challan.getLoc_add2());
		result1.append(System.lineSeparator());

		result1.append(
				challan.getLoc_add3().length() > 65 ? challan.getLoc_add3().substring(0, 65) : challan.getLoc_add3());
		result1.append(System.lineSeparator());

		result1.append(
				challan.getLoc_add4().length() > 65 ? challan.getLoc_add4().substring(0, 65) : challan.getLoc_add4());
		result1.append(System.lineSeparator());

//result1.append("State :" + challan.getState() + "  State Code :" );
		result1.append(System.lineSeparator());

		String comp_name_new = "Pfizer Products India Private Limited.";
		PdfPCell hcell_cn = null;
// if (challan.getDsp_challan_no().contains("PIP")) {
//    if (comp_cd.equalsIgnoreCase("PFZ")) {
// hcell_cn = new PdfPCell(PdfStyle.createLabelCellWithBorderLargeFontNoBold(comp_name_new));
//    } else {
// hcell_cn = new PdfPCell(PdfStyle.createLabelCellWithBorderLargeFontNoBold(company_name));
//    }
//    hcell_cn.setHorizontalAlignment(Element.ALIGN_LEFT);
//    hcell_cn.setBorder(Rectangle.LEFT);
// } else {
		hcell_cn = new PdfPCell(PdfStyle.createLabelCellWithBorderLargeFontNoBold(companyName));
		hcell_cn.setHorizontalAlignment(Element.ALIGN_LEFT);
		hcell_cn.setBorder(Rectangle.LEFT);
// }

		PdfPCell hcell2w = new PdfPCell(PdfStyle.createLabelCellWithBorderSmallFontNoBoldForHeader(result1.toString()));
		hcell2w.setHorizontalAlignment(Element.ALIGN_LEFT);
		hcell2w.setBorder(Rectangle.LEFT);

		PdfPTable addressTbl_a = new PdfPTable(1);
		addressTbl_a.addCell(hcell_cn);
		addressTbl_a.addCell(hcell2w);

		PdfPCell hcella_4 = new PdfPCell(PdfStyle.createLabelCellWithoutBorderSmallFontNoPad("Drug Lic No:"));
		hcella_4.setHorizontalAlignment(Element.ALIGN_LEFT);
		hcella_4.setVerticalAlignment(Element.ALIGN_BOTTOM);
		hcella_4.setBorder(Rectangle.LEFT);

		PdfPCell hcella_4_1 = new PdfPCell(
				PdfStyle.createLabelCellWithBorderSmallFontNoBoldForHeader(challan.getLic1()));
		hcella_4_1.setHorizontalAlignment(Element.ALIGN_LEFT);
		hcella_4_1.setVerticalAlignment(Element.ALIGN_TOP);
		hcella_4_1.setBorder(0);

		PdfPCell hcella_7 = new PdfPCell(PdfStyle.createLabelCellWithoutBorderSmallFontNoPad(" "));
		hcella_7.setHorizontalAlignment(Element.ALIGN_LEFT);
		hcella_7.setVerticalAlignment(Element.ALIGN_TOP);
		hcella_7.setBorder(Rectangle.LEFT);

		PdfPCell hcella_4_2 = new PdfPCell(
				PdfStyle.createLabelCellWithBorderSmallFontNoBoldForHeader(challan.getLic2()));
		hcella_4_2.setHorizontalAlignment(Element.ALIGN_LEFT);
		hcella_4_2.setVerticalAlignment(Element.ALIGN_TOP);
		hcella_4_2.setBorder(0);

		
		
		PdfPCell hcella_5 = null;
		
		if(comp_cd.trim().equalsIgnoreCase("MDL") ||comp_cd.trim().equalsIgnoreCase("SNK")) {
			hcella_5 = new PdfPCell(PdfStyle.createLabelCellWithoutBorderSmallFontNoPad("CST Reg :"));
		}else {
			hcella_5 = new PdfPCell(PdfStyle.createLabelCellWithoutBorderSmallFontNoPad("GSTIN :"));
		}
		
		
	
		
		
		hcella_5.setHorizontalAlignment(Element.ALIGN_LEFT);
		hcella_5.setVerticalAlignment(Element.ALIGN_TOP);
		hcella_5.setBorder(Rectangle.LEFT);
		
		

		PdfPCell hcella_4_3 = null;
		
		if(comp_cd.trim().equalsIgnoreCase("MDL") ||comp_cd.trim().equalsIgnoreCase("SNK")) {
			
			 hcella_4_3 = new PdfPCell(
						PdfStyle.createLabelCellWithBorderSmallFontNoBoldForHeader(challan.getLoc_cst_no()));
		}else {
			 hcella_4_3 = new PdfPCell(
					PdfStyle.createLabelCellWithBorderSmallFontNoBoldForHeader(challan.getGst_no()));
			
		}
		
		
		hcella_4_3.setHorizontalAlignment(Element.ALIGN_LEFT);
		hcella_4_3.setVerticalAlignment(Element.ALIGN_TOP);
		hcella_4_3.setBorder(0);
		
		
		
		

		PdfPCell hcella_6 = new PdfPCell(PdfStyle.createLabelCellWithoutBorderSmallFontNoPad("CIN :"));
		hcella_6.setHorizontalAlignment(Element.ALIGN_LEFT);
		hcella_6.setVerticalAlignment(Element.ALIGN_TOP);
		hcella_6.setBorder(Rectangle.LEFT);

		PdfPCell hcella_4_4 = new PdfPCell(
				PdfStyle.createLabelCellWithBorderSmallFontNoBoldForHeader(challan.getLoc_cinno()));
		hcella_4_4.setHorizontalAlignment(Element.ALIGN_LEFT);
		hcella_4_4.setVerticalAlignment(Element.ALIGN_TOP);
		hcella_4_4.setBorder(0);

		PdfPCell hcella_8 = new PdfPCell(PdfStyle.createLabelCellWithoutBorderSmallFontNoPad("PAN :"));
		hcella_8.setHorizontalAlignment(Element.ALIGN_LEFT);
		hcella_8.setVerticalAlignment(Element.ALIGN_TOP);
		hcella_8.setBorder(Rectangle.LEFT);

		PdfPCell hcella_4_8 = new PdfPCell(
				PdfStyle.createLabelCellWithBorderSmallFontNoBoldForHeader(challan.getDptpan_no()));// PAN
		// no
		hcella_4_8.setHorizontalAlignment(Element.ALIGN_LEFT);
		hcella_4_8.setVerticalAlignment(Element.ALIGN_TOP);
		hcella_4_8.setBorder(0);
		PdfPCell hcella_9 = null;
		if (challan.getLoc_web_site() != null && challan.getLoc_web_site().trim().isEmpty()) {
			hcella_9 = new PdfPCell(PdfStyle.createLabelCellWithoutBorderSmallFontNoPad(" "));
			hcella_9.setHorizontalAlignment(Element.ALIGN_LEFT);
			hcella_9.setVerticalAlignment(Element.ALIGN_TOP);
			hcella_9.setBorder(Rectangle.LEFT);
		} else {
			hcella_9 = new PdfPCell(PdfStyle.createLabelCellWithoutBorderSmallFontNoPad("Website :"));
			hcella_9.setHorizontalAlignment(Element.ALIGN_LEFT);
			hcella_9.setVerticalAlignment(Element.ALIGN_TOP);
			hcella_9.setBorder(Rectangle.LEFT);
		}

		PdfPCell hcella_4_9 = new PdfPCell(
				PdfStyle.createLabelCellWithBorderSmallFontNoBoldForHeader(challan.getLoc_web_site().trim()));
		hcella_4_9.setHorizontalAlignment(Element.ALIGN_LEFT);
		hcella_4_9.setVerticalAlignment(Element.ALIGN_TOP);
		hcella_4_9.setBorder(0);

		PdfPCell hcella_10 = new PdfPCell(PdfStyle.createLabelCellWithoutBorderSmallFontNoPad("Tel No :"));
		hcella_10.setHorizontalAlignment(Element.ALIGN_LEFT);
		hcella_10.setVerticalAlignment(Element.ALIGN_TOP);
		hcella_10.setBorder(Rectangle.LEFT);

		PdfPCell hcella_4_10 = new PdfPCell(
				PdfStyle.createLabelCellWithBorderSmallFontNoBoldForHeader(challan.getLoc_telephone_no()));
		hcella_4_10.setHorizontalAlignment(Element.ALIGN_LEFT);
		hcella_4_10.setVerticalAlignment(Element.ALIGN_TOP);
		hcella_4_10.setBorder(0);

		PdfPCell hcella_11 = new PdfPCell(PdfStyle.createLabelCellWithBorderSmallFontNoPad("Email Id :"));
		hcella_11.setHorizontalAlignment(Element.ALIGN_LEFT);
		hcella_11.setVerticalAlignment(Element.ALIGN_TOP);
		hcella_11.disableBorderSide(Rectangle.TOP);
		hcella_11.disableBorderSide(Rectangle.RIGHT);

		PdfPCell hcella_4_11 = new PdfPCell(
				PdfStyle.createLabelCellWithBorderSmallFontNoBoldForHeader(challan.getLoc_emailid()));
		hcella_4_11.setHorizontalAlignment(Element.ALIGN_LEFT);
		hcella_4_11.setVerticalAlignment(Element.ALIGN_TOP);
		hcella_4_11.disableBorderSide(Rectangle.LEFT);
		hcella_4_11.disableBorderSide(Rectangle.TOP);
		hcella_4_11.disableBorderSide(Rectangle.RIGHT);

//added for serdia
		PdfPCell hcella_11_a = new PdfPCell(PdfStyle.createLabelCellWithoutBorderSmallFontNoPad("Food License No:"));
		hcella_11_a.setHorizontalAlignment(Element.ALIGN_LEFT);
		hcella_11_a.setVerticalAlignment(Element.ALIGN_TOP);
		hcella_11_a.setBorder(Rectangle.LEFT);
//hcella_11_a.setBorder(0);

		PdfPCell hcella_4_11_a = new PdfPCell(PdfStyle.createLabelCellWithBorderSmallFontNoBoldForHeader(""));
		hcella_4_11_a.setHorizontalAlignment(Element.ALIGN_LEFT);
		hcella_4_11_a.setVerticalAlignment(Element.ALIGN_TOP);
		hcella_4_11_a.setBorder(0);
//till here
		PdfPCell cell11 = new PdfPCell(addressTbl_a);
		cell11.setBorder(0);

		PdfPCell hcella_12 = new PdfPCell(PdfStyle.createLabelCellWithBorderSmallFontNoPad("Bill To :"));
		hcella_12.setHorizontalAlignment(Element.ALIGN_LEFT);
		hcella_12.setVerticalAlignment(Element.ALIGN_TOP);
		hcella_12.disableBorderSide(Rectangle.BOTTOM);
		hcella_12.disableBorderSide(Rectangle.RIGHT);

		PdfPCell hcella_4_12 = new PdfPCell(
//PdfStyle.createLabelCellWithBorderSmallFontNoBoldForHeader(challan.getBilltoname1() == null ? "" : challan.getBilltoname1()));
				PdfStyle.createLabelCellWithBorderSmallFontNoBoldForHeader("BillName1" == null ? "" : "BillName1"));
		hcella_4_12.setHorizontalAlignment(Element.ALIGN_LEFT);
		hcella_4_12.setVerticalAlignment(Element.ALIGN_TOP);
		hcella_4_12.disableBorderSide(Rectangle.BOTTOM);
		hcella_4_12.disableBorderSide(Rectangle.LEFT);
		hcella_4_12.disableBorderSide(Rectangle.RIGHT);

		PdfPCell hcella_13 = new PdfPCell(PdfStyle.createLabelCellWithoutBorderSmallFontNoPad(""));
		hcella_13.setHorizontalAlignment(Element.ALIGN_LEFT);
		hcella_13.setVerticalAlignment(Element.ALIGN_TOP);
		hcella_13.setBorder(Rectangle.LEFT);

		PdfPCell hcella_4_13 = new PdfPCell(
// PdfStyle.createLabelCellWithBorderSmallFontNoBoldForHeader(challan.getBilltoname2() == null ? "" : challan.getBilltoname2()));  //Testing
				PdfStyle.createLabelCellWithBorderSmallFontNoBoldForHeader("BillName2" == null ? "" : "BillName2"));
		hcella_4_13.setHorizontalAlignment(Element.ALIGN_LEFT);
		hcella_4_13.setVerticalAlignment(Element.ALIGN_TOP);
		hcella_4_13.setBorder(0);

		PdfPCell hcella_14 = new PdfPCell(PdfStyle.createLabelCellWithoutBorderSmallFontNoPad(""));
		hcella_14.setHorizontalAlignment(Element.ALIGN_LEFT);
		hcella_14.setVerticalAlignment(Element.ALIGN_TOP);
		hcella_14.setBorder(Rectangle.LEFT);

		String gstNo = "";
		gstNo = "GSTIN : " + (challan.getGst_no() == null ? "" : challan.getGst_no()) + " " + ", State code :  - "
				+ (challan.getState() == null ? "" : challan.getState());
		PdfPCell hcella_4_14 = new PdfPCell(PdfStyle.createLabelCellWithBorderSmallFontNoBoldForHeader(gstNo));
		hcella_4_14.setHorizontalAlignment(Element.ALIGN_LEFT);
		hcella_4_14.setVerticalAlignment(Element.ALIGN_TOP);
		hcella_4_14.setBorder(0);

		PdfPTable addressTbl = new PdfPTable(1);
		PdfPTable addressTbl_b = new PdfPTable(2);
		addressTbl_b.setWidths(new float[] { 1.5f, 8.5f });
//addressTbl_b.setWidths(new float[] { 1.5f, 3f,5.5f });
		addressTbl_b.addCell(hcella_4);
		addressTbl_b.addCell(hcella_4_1);
		addressTbl_b.addCell(hcella_7);
		addressTbl_b.addCell(hcella_4_2);

		addressTbl_b.addCell(hcella_5);
		addressTbl_b.addCell(hcella_4_3);
		addressTbl_b.addCell(hcella_6);
		addressTbl_b.addCell(hcella_4_4);

		if (!comp_cd.equalsIgnoreCase("NIL") && !comp_cd.equalsIgnoreCase("NHP")) {
			addressTbl_b.addCell(hcella_8);
			addressTbl_b.addCell(hcella_4_8);
			if (!comp_cd.trim().equalsIgnoreCase("SER")) {
				addressTbl_b.addCell(hcella_9);
				addressTbl_b.addCell(hcella_4_9);
			}
		}
		addressTbl_b.addCell(hcella_10);
		addressTbl_b.addCell(hcella_4_10);
		if (comp_cd.trim().equalsIgnoreCase("SER")) {
			addressTbl_b.addCell(hcella_11_a);
			addressTbl_b.addCell(hcella_4_11_a);
		}
		addressTbl_b.addCell(hcella_11);
		addressTbl_b.addCell(hcella_4_11);

		if (comp_cd.equalsIgnoreCase("NIL") || comp_cd.equalsIgnoreCase("NHP")) {
			addressTbl_b.addCell(hcella_12);
			addressTbl_b.addCell(hcella_4_12);
			addressTbl_b.addCell(hcella_13);
			addressTbl_b.addCell(hcella_4_13);
			addressTbl_b.addCell(hcella_14);
			addressTbl_b.addCell(hcella_4_14);
		}
		PdfPCell cell12 = new PdfPCell(addressTbl_b);
		cell12.setBorder(0);
		addressTbl.addCell(cell11);
		addressTbl.addCell(cell12);

		PdfPCell cell1 = new PdfPCell(addressTbl);
		cell1.setBorder(0);

// if(comp_cd.trim().equalsIgnoreCase("MDL")) {
// headertable2.addCell(cell1);
// //String path=ServletActionContext.getServletContext().getRealPath("");
// //String logoImag = path+"\\pfizer_blk_pos.jpg";
// String logoImag = path+"\\pfizer_blk_pos.png";
//
// System.out.println("logoImag : " + logoImag);
// Image img=null;
// try {
// img = Image.getInstance(logoImag);
// img.setAlignment(Image.ALIGN_CENTER);
// } catch (Exception e) {
// System.out.println("Error Occurred :" +e.getMessage());// uncomment asneeded --;
// }
// img.setAlignment(1);
// PdfPCell imagecell = new PdfPCell(img, true);
// imagecell.setFixedHeight(10f);
// //imagecell.setNoWrap(false);
// imagecell.setBorder(0);
// imagecell.setHorizontalAlignment(Element.ALIGN_LEFT);
// imagecell.setVerticalAlignment(Element.ALIGN_CENTER);
// imagecell.setBorder(Rectangle.NO_BORDER|Rectangle.RIGHT);
// //PdfPTable t1= new PdfPTable(1);
// //t1.addCell(imagecell);
//
//
// headertable2.addCell(imagecell);
//
//
// }
// else if(comp_cd.trim().equalsIgnoreCase("PFZ")){
// headertable2.addCell(cell1);
// //String path=ServletActionContext.getServletContext().getRealPath("");
// //String logoImag = path+"\\pfizer_blk_pos.jpg";
// String logoImag = path+"\\pfizer_blk_pos.png";
//
// System.out.println("logoImag : " + logoImag);
// Image img=null;
// try {
// img = Image.getInstance(logoImag);
// img.setAlignment(Image.ALIGN_LEFT);
// } catch (Exception e) {
// System.out.println("Error Occurred :" +e.getMessage());// uncomment asneeded --;
// }
// img.setAlignment(1);
// PdfPCell imagecell = new PdfPCell(img, true);
// imagecell.setFixedHeight(10f);
// //imagecell.setNoWrap(false);
// imagecell.setBorder(0);
// imagecell.setHorizontalAlignment(Element.ALIGN_LEFT);
// imagecell.setVerticalAlignment(Element.ALIGN_CENTER);
// imagecell.setBorder(Rectangle.NO_BORDER|Rectangle.RIGHT);
// //PdfPTable t1= new PdfPTable(1);
// //t1.addCell(imagecell);
//// if(challan.getDsp_challan_no().contains("PIP")) {
//
// PdfPCell cell = new PdfPCell(new Phrase(" "));
// cell.setBorder(Rectangle.NO_BORDER);
// headertable2.addCell(cell);
//
//// }
//// else {
//// headertable2.addCell(imagecell);
//// }
// }
// else
// {
		cell1.setColspan(2);
		headertable2.addCell(cell1);
// }

//TODO cell2 image add for medley by vivek 14-nov-2018

// ======================================================================================================================
// 2.3

//comp_cd = (String) context.getAttribute("comp_code");

		PdfPCell hcell16 = null;
		if (comp_cd.equalsIgnoreCase("NIL") || comp_cd.equalsIgnoreCase("NHP")) {
			hcell16 = new PdfPCell(PdfStyle.createLabelCellWithBorderSmallFont("Tax Inv Date"));
		} else {
			hcell16 = new PdfPCell(PdfStyle.createLabelCellWithBorderSmallFont("Date "));
		}
		hcell16.setHorizontalAlignment(Element.ALIGN_LEFT);

		PdfPCell hcell20 = null;

// if (comp_cd.equalsIgnoreCase("PFZ")) {
//    hcell20 = new PdfPCell(PdfStyle.createLabelCellWithBorderSmallFont("Challan #"));
//    hcell20.setHorizontalAlignment(Element.ALIGN_LEFT);
//
// } else if (comp_cd.equalsIgnoreCase("UCM")) {
//    hcell20 = new PdfPCell(PdfStyle.createLabelCellWithBorderSmallFont("GST INV No#"));
//    hcell20.setHorizontalAlignment(Element.ALIGN_LEFT);
//
// } else if (comp_cd.equalsIgnoreCase("NIL")) {
// hcell20 = new PdfPCell(PdfStyle.createLabelCellWithBorderSmallFont("Tax Invoice #"));
//    hcell20.setHorizontalAlignment(Element.ALIGN_LEFT);
// }else {
		hcell20 = new PdfPCell(PdfStyle.createLabelCellWithBorderSmallFont("Challan #"));
		hcell20.setHorizontalAlignment(Element.ALIGN_LEFT);
// }

		PdfPCell hcell21 = new PdfPCell(PdfStyle.createLabelCellWithBorderSmallFont("Customer"));
		hcell21.setHorizontalAlignment(Element.ALIGN_LEFT);

		PdfPCell hcell22 = new PdfPCell(PdfStyle.createLabelCellWithBorderSmallFont("Division "));
		hcell22.setHorizontalAlignment(Element.ALIGN_LEFT);

		PdfPCell hcell23 = new PdfPCell(PdfStyle.createLabelCellWithBorderSmallFont("Team"));
		hcell23.setHorizontalAlignment(Element.ALIGN_LEFT);

//PdfPCell hcell24 = new PdfPCell(PdfStyle.createLabelCellWithBorderSmallFont("Remark"));
//hcell24.setHorizontalAlignment(Element.ALIGN_LEFT);

		PdfPCell hcell17 = new PdfPCell(PdfStyle.createLabelCellWithBorderSmallFontTwiceHeight("Email"));
		hcell17.setHorizontalAlignment(Element.ALIGN_LEFT);

		PdfPCell hcell18 = new PdfPCell(PdfStyle.createLabelCellWithBorderSmallFont("Telephone 1"));
		hcell18.setHorizontalAlignment(Element.ALIGN_LEFT);

		PdfPCell hcell19 = new PdfPCell(PdfStyle.createLabelCellWithBorderSmallFont("Telephone 2"));
		hcell19.setHorizontalAlignment(Element.ALIGN_LEFT);
		PdfPCell hcell19_from_to = null;

		hcell19_from_to = new PdfPCell(PdfStyle.createLabelCellWithBorderSmallFont("Unique No : "));

		PdfPTable addressTbl2 = new PdfPTable(1);
		addressTbl2.addCell(hcell16);

		addressTbl2.addCell(hcell20);
		addressTbl2.addCell(hcell21);
		addressTbl2.addCell(hcell22);
		addressTbl2.addCell(hcell23);
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
// Font medFont = new Font(Font.FontFamily.HELVETICA, 12,Font.NORMAL,
// BaseColor.BLACK);

//getDsp_challan_dt
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

//Team
		PdfPCell hcell33 = new PdfPCell(PdfStyle.createLabelCellWithBorderSmallFontNoBold(("")));
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

//mobile2
		PdfPCell hcell29 = new PdfPCell(PdfStyle.createLabelCellWithBorderSmallFontNoBold(
				(challan.getDspstaff_mobile() == null ? "" : challan.getDspstaff_mobile()))); // telephone //added
		// 2
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

// =====================================================================================================================

// =====================================================================================================================
// 3.1

		PdfPCell hcell35 = new PdfPCell(PdfStyle.createLabelCellWithBorderSmallFont("Ship To :"));
//PdfPCell hcell35 = new PdfPCell(new Phrase("Ship To :", font3));

		hcell35.setHorizontalAlignment(Element.ALIGN_LEFT);

		PdfPCell hcell36 = new PdfPCell(
				PdfStyle.createLabelCellWithoutBorder(challan.getStaff_name() == null ? "" : challan.getStaff_name()+"-"+challan.getHq_name()));
		
		
		hcell36.setHorizontalAlignment(Element.ALIGN_LEFT);
		hcell36.enableBorderSide(Rectangle.LEFT);
		hcell36.enableBorderSide(Rectangle.RIGHT);
		StringBuilder result2 = new StringBuilder();
		result2.append(challan.getStaff_addr1().length() > 55 ? challan.getStaff_addr1().substring(0, 55)
				: challan.getStaff_addr1());
		result2.append(System.lineSeparator());
		result2.append(challan.getStaff_addr2().length() > 55 ? challan.getStaff_addr2().substring(0, 55)
				: challan.getStaff_addr2());
		result2.append(System.lineSeparator());
		result2.append(challan.getStaff_addr3().length() > 55 ? challan.getStaff_addr3().substring(0, 55)
				: challan.getStaff_addr3());
		result2.append(System.lineSeparator());
		result2.append(challan.getStaff_addr4().length() > 55 ? challan.getStaff_addr4().substring(0, 55)
				: challan.getStaff_addr4());
		result2.append(System.lineSeparator());
		
		if(comp_cd.trim().equalsIgnoreCase("MDL") ||comp_cd.trim().equalsIgnoreCase("SNK")) {
			result2.append("Tel No : " + challan.getDspstaff_mobile());
			result2.append(System.lineSeparator());
			
		}else {
			result2.append("GSTIN : UNREGISTERED");
			result2.append(System.lineSeparator());
			result2.append("PAN : Not Available");
			result2.append(System.lineSeparator());
		}

		PdfPCell hcell37 = new PdfPCell(PdfStyle
				.createLabelCellWithoutBorderSmall7FontNoBold(result2.toString() == null ? "" : result2.toString()));
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
//String address1 = (String) context.getAttribute("address1");
//String address2 = (String) context.getAttribute("address2");
//String address3 = (String) context.getAttribute("address3");
//String address4 = (String) context.getAttribute("address4");
// WEB_SITE cinno
//String web_site = (String) context.getAttribute("web_site") == null ? " "
// : (String) context.getAttribute("web_site");
//String cinno = (String) context.getAttribute("cinno") == null ? " " : (String) context.getAttribute("cinno");
		Company company = ((Company) session.getServletContext().getAttribute(COMPANY_DATA));
		String address1 = company.getAddress1();
		String address2 = company.getAddress2();
		String address3 = company.getAddress3();
		String address4 = company.getAddress4();
		String web_site = company.getWeb_site();
		String cinno = company.getCinno();

		StringBuilder result3 = new StringBuilder();

// Company name added 27-1-2017
// if (challan.getDsp_challan_no().contains("PIP")) {
//
//    if (comp_cd.equalsIgnoreCase("PFZ")) {
// result3.append(comp_name_new);
//
// address1 = ":";
// address2 = ":";
// address3 = ":";
// address4 = ":";
// web_site = ":";
// cinno = ":";     ////Temporary Code 2-6-2020
//    } else {
// result3.append(company_name);
//    }
//
//    result3.append(System.lineSeparator());
// } else {
		System.out.println("company_name::::" + company_name);
		if (company_name.trim().isEmpty()) {
			result3.append(System.lineSeparator());
		} else {
			result3.append(company_name.length() > 30 ? company_name.substring(0, 30) : company_name);
			result3.append(System.lineSeparator());
		}
// }

		result3.append(address1.length() > 33 ? address1.substring(0, 33) : address1);
		result3.append(System.lineSeparator());
		result3.append(address2.length() > 33 ? address2.substring(0, 33) : address2);
		result3.append(System.lineSeparator());
		result3.append(address3.length() > 33 ? address3.substring(0, 33) : address3);
		result3.append(System.lineSeparator());
		result3.append(address4.length() > 33 ? address4.substring(0, 33) : address4);
		result3.append(System.lineSeparator());
		if (web_site.trim().isEmpty()) {
			result3.append("");
			result3.append(System.lineSeparator());
		} else {
			result3.append(web_site.length() > 33 ? "Website:" + web_site.substring(0, 33) : "Website:" + web_site);
			result3.append(System.lineSeparator());

		}
//System.out.println(cinno+"#sdfsdfsd");
		if (cinno.trim().isEmpty()) {
			result3.append("");
			result3.append(System.lineSeparator());
		} else {
			result3.append(cinno.length() > 33 ? "CIN NO:" + cinno.substring(0, 33) : "CIN NO:" + cinno);
			result3.append(System.lineSeparator());
		}

		PdfPCell hcell38 = new PdfPCell(PdfStyle.createLabelCellWithBorderSmallFont("Principal place of business"));
//PdfPCell hcell38 = new PdfPCell(new Phrase("Principal place of business ", font3));
		hcell38.setHorizontalAlignment(Element.ALIGN_LEFT);

		PdfPCell hcell39 = new PdfPCell(PdfStyle.createLabelCellWithoutBorderSmallFontNoBold(
				result3.length() > 170 ? result3.substring(0, 170) : result3.toString()));
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

		if (comp_cd.trim().equalsIgnoreCase("SER")) {
			// result4.append("");
			result4.append(System.lineSeparator());
			result4.append(System.lineSeparator());
			result4.append("                                                "); // Goods not for sale. Free samples
			// and / or promotional inputs only
			// having no commercial value
			result4.append(System.lineSeparator());
			result4.append(System.lineSeparator());
			result4.append(System.lineSeparator());
			result4.append(System.lineSeparator());
			result4.append(System.lineSeparator());
			result4.append(System.lineSeparator());
			result4.append(System.lineSeparator());
			result4.append(System.lineSeparator());
			result4.append(System.lineSeparator());

		} else {
			result4.append("Goods Not for sale.");
			result4.append(System.lineSeparator());

			if (!comp_cd.equalsIgnoreCase("NIL") && !comp_cd.equalsIgnoreCase("NHP")) {
				result4.append("Free samples and/"); // Goods not for sale. Free samples
				// and / or promotional inputs only
				// having no commercial value
				result4.append(System.lineSeparator());
				result4.append("or promotional inputs");
				result4.append(System.lineSeparator());
				result4.append("only having no");
				result4.append(System.lineSeparator());
				result4.append("commercial value.");
				result4.append(System.lineSeparator());
			} else {
				result4.append("                                                "); // Goods not for sale. Free samples
				// and / or promotional inputs only
				// having no commercial value
				result4.append(System.lineSeparator());
				result4.append(System.lineSeparator());
				result4.append(System.lineSeparator());
				result4.append(System.lineSeparator());
				result4.append(System.lineSeparator());
				result4.append(System.lineSeparator());
				result4.append(System.lineSeparator());
				result4.append(System.lineSeparator());
				result4.append(System.lineSeparator());

			}
		}
		PdfPCell hcell44 = new PdfPCell(PdfStyle.createLabelCellWithoutBorderSmallFontBold(result4.toString()));
		hcell44.setHorizontalAlignment(Element.ALIGN_LEFT);
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

		if (comp_cd.equalsIgnoreCase("PFZ")) {
			if (show_amount.equals("Y")) {
				hcell45 = new PdfPCell(PdfStyle.createLabelCellWithBorderSmallFont("Insurance Policy No."));
			} else {
				hcell45 = new PdfPCell(PdfStyle.createLabelCellWithBorderSmallFont(""));
			}

		} else if (comp_cd.equalsIgnoreCase("UCM")) {
			hcell45 = new PdfPCell(PdfStyle.createLabelCellWithBorderSmallFont(""));
		} else {
			if (show_amount.equals("Y")) {
				hcell45 = new PdfPCell(PdfStyle.createLabelCellWithBorderSmallFont("Insurance Policy No."));
			} else {
				hcell45 = new PdfPCell(PdfStyle.createLabelCellWithBorderSmallFont(""));
			}
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
		if (!comp_cd.trim().equalsIgnoreCase("SER")) {
			custTbl3.addCell(hcell47);
			custTbl3.addCell(hcell48);
		}
		PdfPCell cell8 = new PdfPCell(custTbl3);
		cell8.setBorder(0);
		cell8.setPaddingBottom(0);
		cell8.setUseBorderPadding(false);
		headertable3.addCell(cell8);

// =================================================Blank Cell Added To
// Remove Table Border3==============================

		PdfPCell hcell_blank3 = new PdfPCell(headertable3);
		hcell_blank3.setBorder(Rectangle.NO_BORDER);

		if (comp_cd.trim().equals("SER") || comp_cd.trim().equals(VET) || comp_cd.trim().equals("MDL")
				|| comp_cd.trim().equals("SNK")) {
			bodytable = new PdfPTable(6);
			bodytable.setWidthPercentage(100);
			bodytable.setWidths(bodyTableWidth);
			bodytable.addCell(PdfStyle.createLabelWithBGColour("Item"));
			bodytable.addCell(PdfStyle.createLabelWithBGColour("HSN Code"));
			bodytable.addCell(PdfStyle.createLabelWithBGColour("Batch/Art Work No"));
			bodytable.addCell(PdfStyle.createLabelWithBGColour("Expiry Date"));
			bodytable.addCell(PdfStyle.createLabelWithBGColour("Qty"));
			if (show_amount.equals("Y")) {
				bodytable.addCell(PdfStyle.createLabelWithBGColour("Taxable Value"));
			} else {
				bodytable.addCell(PdfStyle.createLabelWithBGColour(" "));
			}
		} else {
			bodytable = new PdfPTable(7);
			bodytable.setWidthPercentage(100);
			bodytable.setWidths(bodyTableWidth);
			bodytable.addCell(PdfStyle.createLabelWithBGColour("Item"));
			bodytable.addCell(PdfStyle.createLabelWithBGColour("HSN Code"));
			bodytable.addCell(PdfStyle.createLabelWithBGColour("Batch/Art Work No"));
			bodytable.addCell(PdfStyle.createLabelWithBGColour("Expiry Date"));
			bodytable.addCell(PdfStyle.createLabelWithBGColour("Qty"));
			if (show_amount.equals("Y")) {
				bodytable.addCell(PdfStyle.createLabelWithBGColour("Taxable Value"));
			} else {
				bodytable.addCell(PdfStyle.createLabelWithBGColour(" "));
			}
			bodytable.addCell(PdfStyle.createLabelWithBGColour("Taxable Amount"));
		}
// =================================================Blank Cell Added To
// Remove Table Border4==============================

		PdfPCell hcell_blank4 = new PdfPCell(bodytable);
		hcell_blank4.setBorder(Rectangle.NO_BORDER);
		headertable.addCell(hcell_blank);
		headertable.addCell(hcell_blank2);
		headertable.addCell(hcell_blank3);
		headertable.addCell(hcell_blank4);

		return headertable;
	}

	
	
	
	private PdfPTable createHeader_mdl(ViewPrePrintedDetailChallan challan, int pagecount, int division, Integer loc,
			String frm_challan, String to_challan, String dispatchType, String prodtype, String path,
			String show_amount, String companyName, HttpSession session) throws Exception {

		System.out.println("inside  thiss++++++++");
		PdfPTable headertable = new PdfPTable(1);
		PdfPTable headertable01 = new PdfPTable(1);// Billing Address
		PdfPTable headertable2 = new PdfPTable(4);
		PdfPTable headertable3 = new PdfPTable(4);
		PdfPTable headertable4 = new PdfPTable(4);
		PdfPTable bodytable = null;
		
		
//		if (comp_cd.trim().equalsIgnoreCase("MDL")) {
//		imagePath = "D:\\sg\\files\\medley-logo.jpg";
//		Image image = Image.getInstance(imagePath);
//		image.setAbsolutePosition(290, 690);
//		image.scaleToFit(70, 70);
//		document.add(image);
//	}
		
		
		
		String FOX = "path/to/resource/fox.png";
		headertable.setWidthPercentage(100);
		headertable01.setWidthPercentage(100);
		headertable2.setWidthPercentage(100);
		headertable3.setWidthPercentage(100);
		headertable4.setWidthPercentage(100);

		headertable01.setWidths(new float[] { 7f });
		headertable2.setWidths(new float[] { 4.5f, 2.5f, 1f, 2f });
		headertable3.setWidths(new float[] { 4.5f, 2.5f, 1f, 2f });
		headertable4.setWidths(new float[] { 2f, 2f, 2.5f, 3.5f });

		Font font = new Font(FontFamily.HELVETICA, 13, Font.BOLD, BaseColor.BLACK);
		Font font2 = new Font(FontFamily.HELVETICA, 9, Font.BOLD, BaseColor.BLACK);
		Font font3 = new Font(FontFamily.HELVETICA, 8f, Font.BOLD, BaseColor.BLACK);
		PdfPTable headertable1 = new PdfPTable(1);
		headertable1.setWidthPercentage(100);
		headertable1.setWidths(new float[] { 5f });

// ======================================================================================================================
// 1
		PdfPCell hcell = null;

		if (comp_cd.equalsIgnoreCase("PFZ")) {
			hcell = new PdfPCell(new Phrase("Delivery Challan", font));
		} else if (comp_cd.equalsIgnoreCase("UCM")) {
			hcell = new PdfPCell(new Phrase("TAX INVOICE", font));
		} else if (comp_cd.equalsIgnoreCase("NIL") || comp_cd.equalsIgnoreCase("NHP")) {
			hcell = new PdfPCell(new Phrase("TAX INVOICE", font));
			hcell.disableBorderSide(Rectangle.BOTTOM);
		} else {
			hcell = new PdfPCell(new Phrase("Delivery Challan", font));
		}

		hcell.setHorizontalAlignment(Element.ALIGN_CENTER);
		hcell.setVerticalAlignment(Element.ALIGN_CENTER);

		PdfPCell hcell_1 = new PdfPCell(new Phrase(" ", font));
		hcell_1.setBorder(Rectangle.LEFT);
		hcell_1.setHorizontalAlignment(Element.ALIGN_CENTER);
		headertable01.addCell(hcell);
		PdfPCell hcell2 = null;
		if (comp_cd.equalsIgnoreCase("NIL") || comp_cd.equalsIgnoreCase("NHP")) {
			hcell2 = new PdfPCell(new Phrase("(Rule 46 of CGST Rules,2017)", font2));
			hcell2.setHorizontalAlignment(Element.ALIGN_CENTER);
			hcell2.setVerticalAlignment(Element.ALIGN_CENTER);
			hcell2.disableBorderSide(Rectangle.TOP);
			headertable01.addCell(hcell2);
		}

// =================================================Blank Cell Added To
// Remove Table Border==============================

		PdfPCell hcell_blank = new PdfPCell(headertable01);
		hcell_blank.setBorder(Rectangle.RIGHT);
		hcell_blank.disableBorderSide(Rectangle.BOTTOM);

// ======================================================================================================================
// 2.1
//String company_name = (String) context.getAttribute("company_name");

		StringBuilder result1 = new StringBuilder();
		
		if(comp_cd.trim().equalsIgnoreCase("MDL") ||comp_cd.trim().equalsIgnoreCase("SNK")) {
			result1.append(
					challan.getLoc_add1().length() > 65 ? challan.getLoc_add1().substring(0, 65) : challan.getLoc_add1());
					result1.append(System.lineSeparator());
		}


		result1.append(
				challan.getLoc_add2().length() > 65 ? challan.getLoc_add2().substring(0, 65) : challan.getLoc_add2());
		result1.append(System.lineSeparator());

		result1.append(
				challan.getLoc_add3().length() > 65 ? challan.getLoc_add3().substring(0, 65) : challan.getLoc_add3());
		result1.append(System.lineSeparator());

		result1.append(
				challan.getLoc_add4().length() > 65 ? challan.getLoc_add4().substring(0, 65) : challan.getLoc_add4());
		result1.append(System.lineSeparator());

//result1.append("State :" + challan.getState() + "  State Code :" );
		result1.append(System.lineSeparator());

		String comp_name_new = "Pfizer Products India Private Limited.";
		PdfPCell hcell_cn = null;
// if (challan.getDsp_challan_no().contains("PIP")) {
//    if (comp_cd.equalsIgnoreCase("PFZ")) {
// hcell_cn = new PdfPCell(PdfStyle.createLabelCellWithBorderLargeFontNoBold(comp_name_new));
//    } else {
// hcell_cn = new PdfPCell(PdfStyle.createLabelCellWithBorderLargeFontNoBold(company_name));
//    }
//    hcell_cn.setHorizontalAlignment(Element.ALIGN_LEFT);
//    hcell_cn.setBorder(Rectangle.LEFT);
// } else {
		hcell_cn = new PdfPCell(PdfStyle.createLabelCellWithBorderLargeFontNoBold(companyName));
		hcell_cn.setHorizontalAlignment(Element.ALIGN_LEFT);
		hcell_cn.setBorder(Rectangle.LEFT);
// }

		PdfPCell hcell2w = new PdfPCell(PdfStyle.createLabelCellWithBorderSmallFontNoBoldForHeader(result1.toString()));
		hcell2w.setHorizontalAlignment(Element.ALIGN_LEFT);
		hcell2w.setBorder(Rectangle.LEFT);

		PdfPTable addressTbl_a = new PdfPTable(1);
		addressTbl_a.addCell(hcell_cn);
		addressTbl_a.addCell(hcell2w);

		PdfPCell hcella_4 = new PdfPCell(PdfStyle.createLabelCellWithoutBorderSmallFontNoPad("Drug Lic No:"));
		hcella_4.setHorizontalAlignment(Element.ALIGN_LEFT);
		hcella_4.setVerticalAlignment(Element.ALIGN_BOTTOM);
		hcella_4.setBorder(Rectangle.LEFT);

		PdfPCell hcella_4_1 = new PdfPCell(
				PdfStyle.createLabelCellWithBorderSmallFontNoBoldForHeader(challan.getLic1()));
		hcella_4_1.setHorizontalAlignment(Element.ALIGN_LEFT);
		hcella_4_1.setVerticalAlignment(Element.ALIGN_TOP);
		hcella_4_1.setBorder(0);

		PdfPCell hcella_7 = new PdfPCell(PdfStyle.createLabelCellWithoutBorderSmallFontNoPad(" "));
		hcella_7.setHorizontalAlignment(Element.ALIGN_LEFT);
		hcella_7.setVerticalAlignment(Element.ALIGN_TOP);
		hcella_7.setBorder(Rectangle.LEFT);

		PdfPCell hcella_4_2 = new PdfPCell(
				PdfStyle.createLabelCellWithBorderSmallFontNoBoldForHeader(challan.getLic2()));
		hcella_4_2.setHorizontalAlignment(Element.ALIGN_LEFT);
		hcella_4_2.setVerticalAlignment(Element.ALIGN_TOP);
		hcella_4_2.setBorder(0);

		
		
		PdfPCell hcella_5 = null;
		
		if(comp_cd.trim().equalsIgnoreCase("MDL") ||comp_cd.trim().equalsIgnoreCase("SNK")) {
			hcella_5 = new PdfPCell(PdfStyle.createLabelCellWithoutBorderSmallFontNoPad("CST Reg :"));
		}else {
			hcella_5 = new PdfPCell(PdfStyle.createLabelCellWithoutBorderSmallFontNoPad("GSTIN :"));
		}
		
		hcella_5.setHorizontalAlignment(Element.ALIGN_LEFT);
		hcella_5.setVerticalAlignment(Element.ALIGN_TOP);
		hcella_5.setBorder(Rectangle.LEFT);
		
		

		PdfPCell hcella_4_3 = null;
		
		if(comp_cd.trim().equalsIgnoreCase("MDL") ||comp_cd.trim().equalsIgnoreCase("SNK")) {
			
			 hcella_4_3 = new PdfPCell(
						PdfStyle.createLabelCellWithBorderSmallFontNoBoldForHeader(challan.getLoc_cst_no()));
		}else {
			 hcella_4_3 = new PdfPCell(
					PdfStyle.createLabelCellWithBorderSmallFontNoBoldForHeader(challan.getGst_no()));
			
		}
		
		
		hcella_4_3.setHorizontalAlignment(Element.ALIGN_LEFT);
		hcella_4_3.setVerticalAlignment(Element.ALIGN_TOP);
		hcella_4_3.setBorder(0);
		
		
		
		

		PdfPCell hcella_6 = new PdfPCell(PdfStyle.createLabelCellWithoutBorderSmallFontNoPad("CIN :"));
		hcella_6.setHorizontalAlignment(Element.ALIGN_LEFT);
		hcella_6.setVerticalAlignment(Element.ALIGN_TOP);
		hcella_6.setBorder(Rectangle.LEFT);

		PdfPCell hcella_4_4 = new PdfPCell(
				PdfStyle.createLabelCellWithBorderSmallFontNoBoldForHeader(challan.getLoc_cinno()));
		hcella_4_4.setHorizontalAlignment(Element.ALIGN_LEFT);
		hcella_4_4.setVerticalAlignment(Element.ALIGN_TOP);
		hcella_4_4.setBorder(0);

		PdfPCell hcella_8 = new PdfPCell(PdfStyle.createLabelCellWithoutBorderSmallFontNoPad("PAN :"));
		hcella_8.setHorizontalAlignment(Element.ALIGN_LEFT);
		hcella_8.setVerticalAlignment(Element.ALIGN_TOP);
		hcella_8.setBorder(Rectangle.LEFT);

		PdfPCell hcella_4_8 = new PdfPCell(
				PdfStyle.createLabelCellWithBorderSmallFontNoBoldForHeader(challan.getDptpan_no()));// PAN
		// no
		hcella_4_8.setHorizontalAlignment(Element.ALIGN_LEFT);
		hcella_4_8.setVerticalAlignment(Element.ALIGN_TOP);
		hcella_4_8.setBorder(0);
		
		
		
		/////////  
		
		
		
//		
//		PdfPCell hcella_9 = null;
//		
//		
//		if (challan.getLoc_web_site() != null && challan.getLoc_web_site().trim().isEmpty()) {
//			hcella_9 = new PdfPCell(PdfStyle.createLabelCellWithoutBorderSmallFontNoPad(" "));
//			hcella_9.setHorizontalAlignment(Element.ALIGN_LEFT);
//			hcella_9.setVerticalAlignment(Element.ALIGN_TOP);
//			hcella_9.setBorder(Rectangle.LEFT);
//		} else {
//			hcella_9 = new PdfPCell(PdfStyle.createLabelCellWithoutBorderSmallFontNoPad("Website :"));
//			hcella_9.setHorizontalAlignment(Element.ALIGN_LEFT);
//			hcella_9.setVerticalAlignment(Element.ALIGN_TOP);
//			hcella_9.setBorder(Rectangle.LEFT);
//		}
//
//		
//		PdfPCell hcella_4_9 = new PdfPCell(
//				PdfStyle.createLabelCellWithBorderSmallFontNoBoldForHeader(challan.getLoc_web_site().trim()));
//		hcella_4_9.setHorizontalAlignment(Element.ALIGN_LEFT);
//		hcella_4_9.setVerticalAlignment(Element.ALIGN_TOP);
//		hcella_4_9.setBorder(0);
//
//		PdfPCell hcella_10 = new PdfPCell(PdfStyle.createLabelCellWithoutBorderSmallFontNoPad("Tel No :"));
//		hcella_10.setHorizontalAlignment(Element.ALIGN_LEFT);
//		hcella_10.setVerticalAlignment(Element.ALIGN_TOP);
//		hcella_10.setBorder(Rectangle.LEFT);
//
//		PdfPCell hcella_4_10 = new PdfPCell(
//				PdfStyle.createLabelCellWithBorderSmallFontNoBoldForHeader(challan.getLoc_telephone_no()));
//		hcella_4_10.setHorizontalAlignment(Element.ALIGN_LEFT);
//		hcella_4_10.setVerticalAlignment(Element.ALIGN_TOP);
//		hcella_4_10.setBorder(0);
//
//		PdfPCell hcella_11 = new PdfPCell(PdfStyle.createLabelCellWithBorderSmallFontNoPad("Email Id :"));
//		hcella_11.setHorizontalAlignment(Element.ALIGN_LEFT);
//		hcella_11.setVerticalAlignment(Element.ALIGN_TOP);
//		hcella_11.disableBorderSide(Rectangle.TOP);
//		hcella_11.disableBorderSide(Rectangle.RIGHT);
//
//		PdfPCell hcella_4_11 = new PdfPCell(
//				PdfStyle.createLabelCellWithBorderSmallFontNoBoldForHeader(challan.getLoc_emailid()));
//		hcella_4_11.setHorizontalAlignment(Element.ALIGN_LEFT);
//		hcella_4_11.setVerticalAlignment(Element.ALIGN_TOP);
//		hcella_4_11.disableBorderSide(Rectangle.LEFT);
//		hcella_4_11.disableBorderSide(Rectangle.TOP);
//		hcella_4_11.disableBorderSide(Rectangle.RIGHT);
		
		
		
		/////////
		
		

//added for serdia
		PdfPCell hcella_11_a = new PdfPCell(PdfStyle.createLabelCellWithoutBorderSmallFontNoPad("Food License No:"));
		hcella_11_a.setHorizontalAlignment(Element.ALIGN_LEFT);
		hcella_11_a.setVerticalAlignment(Element.ALIGN_TOP);
		hcella_11_a.setBorder(Rectangle.LEFT);
//hcella_11_a.setBorder(0);

		PdfPCell hcella_4_11_a = new PdfPCell(PdfStyle.createLabelCellWithBorderSmallFontNoBoldForHeader(""));
		hcella_4_11_a.setHorizontalAlignment(Element.ALIGN_LEFT);
		hcella_4_11_a.setVerticalAlignment(Element.ALIGN_TOP);
		hcella_4_11_a.setBorder(0);
//till here
		PdfPCell cell11 = new PdfPCell(addressTbl_a);
		cell11.setBorder(0);

		PdfPCell hcella_12 = new PdfPCell(PdfStyle.createLabelCellWithBorderSmallFontNoPad("Bill To :"));
		hcella_12.setHorizontalAlignment(Element.ALIGN_LEFT);
		hcella_12.setVerticalAlignment(Element.ALIGN_TOP);
		hcella_12.disableBorderSide(Rectangle.BOTTOM);
		hcella_12.disableBorderSide(Rectangle.RIGHT);

		PdfPCell hcella_4_12 = new PdfPCell(
//PdfStyle.createLabelCellWithBorderSmallFontNoBoldForHeader(challan.getBilltoname1() == null ? "" : challan.getBilltoname1()));
				PdfStyle.createLabelCellWithBorderSmallFontNoBoldForHeader("BillName1" == null ? "" : "BillName1"));
		hcella_4_12.setHorizontalAlignment(Element.ALIGN_LEFT);
		hcella_4_12.setVerticalAlignment(Element.ALIGN_TOP);
		hcella_4_12.disableBorderSide(Rectangle.BOTTOM);
		hcella_4_12.disableBorderSide(Rectangle.LEFT);
		hcella_4_12.disableBorderSide(Rectangle.RIGHT);

		PdfPCell hcella_13 = new PdfPCell(PdfStyle.createLabelCellWithoutBorderSmallFontNoPad(""));
		hcella_13.setHorizontalAlignment(Element.ALIGN_LEFT);
		hcella_13.setVerticalAlignment(Element.ALIGN_TOP);
		hcella_13.setBorder(Rectangle.LEFT);

		PdfPCell hcella_4_13 = new PdfPCell(
// PdfStyle.createLabelCellWithBorderSmallFontNoBoldForHeader(challan.getBilltoname2() == null ? "" : challan.getBilltoname2()));  //Testing
				PdfStyle.createLabelCellWithBorderSmallFontNoBoldForHeader("BillName2" == null ? "" : "BillName2"));
		hcella_4_13.setHorizontalAlignment(Element.ALIGN_LEFT);
		hcella_4_13.setVerticalAlignment(Element.ALIGN_TOP);
		hcella_4_13.setBorder(0);

		PdfPCell hcella_14 = new PdfPCell(PdfStyle.createLabelCellWithoutBorderSmallFontNoPad(""));
		hcella_14.setHorizontalAlignment(Element.ALIGN_LEFT);
		hcella_14.setVerticalAlignment(Element.ALIGN_TOP);
		hcella_14.setBorder(Rectangle.LEFT);

		String gstNo = "";
		gstNo = "GSTIN : " + (challan.getGst_no() == null ? "" : challan.getGst_no()) + " " + ", State code :  - "
				+ (challan.getState() == null ? "" : challan.getState());
		PdfPCell hcella_4_14 = new PdfPCell(PdfStyle.createLabelCellWithBorderSmallFontNoBoldForHeader(gstNo));
		hcella_4_14.setHorizontalAlignment(Element.ALIGN_LEFT);
		hcella_4_14.setVerticalAlignment(Element.ALIGN_TOP);
		hcella_4_14.setBorder(0);

		PdfPTable addressTbl = new PdfPTable(1);
		PdfPTable addressTbl_b = new PdfPTable(2);
		addressTbl_b.setWidths(new float[] { 1.5f, 8.5f });
//addressTbl_b.setWidths(new float[] { 1.5f, 3f,5.5f });
		addressTbl_b.addCell(hcella_4);
		addressTbl_b.addCell(hcella_4_1);
		addressTbl_b.addCell(hcella_7);
		addressTbl_b.addCell(hcella_4_2);

		addressTbl_b.addCell(hcella_5);
		addressTbl_b.addCell(hcella_4_3);
		addressTbl_b.addCell(hcella_6);
		addressTbl_b.addCell(hcella_4_4);

		if (!comp_cd.equalsIgnoreCase("NIL") && !comp_cd.equalsIgnoreCase("NHP")) {
			addressTbl_b.addCell(hcella_8);
			addressTbl_b.addCell(hcella_4_8);
			if (!comp_cd.trim().equalsIgnoreCase("SER")) {
//				addressTbl_b.addCell(hcella_9);
//				addressTbl_b.addCell(hcella_4_9);
			}
		}
//		addressTbl_b.addCell(hcella_10);
//		addressTbl_b.addCell(hcella_4_10);
		if (comp_cd.trim().equalsIgnoreCase("SER")) {
			addressTbl_b.addCell(hcella_11_a);
			addressTbl_b.addCell(hcella_4_11_a);
		}
//		addressTbl_b.addCell(hcella_11);
//		addressTbl_b.addCell(hcella_4_11);

		if (comp_cd.equalsIgnoreCase("NIL") || comp_cd.equalsIgnoreCase("NHP")) {
			addressTbl_b.addCell(hcella_12);
			addressTbl_b.addCell(hcella_4_12);
			addressTbl_b.addCell(hcella_13);
			addressTbl_b.addCell(hcella_4_13);
			addressTbl_b.addCell(hcella_14);
			addressTbl_b.addCell(hcella_4_14);
		}
		PdfPCell cell12 = new PdfPCell(addressTbl_b);
		cell12.setBorder(0);
		addressTbl.addCell(cell11);
		addressTbl.addCell(cell12);

		PdfPCell cell1 = new PdfPCell(addressTbl);
		cell1.setBorder(0);

// if(comp_cd.trim().equalsIgnoreCase("MDL")) {
// headertable2.addCell(cell1);
// //String path=ServletActionContext.getServletContext().getRealPath("");
// //String logoImag = path+"\\pfizer_blk_pos.jpg";
// String logoImag = path+"\\pfizer_blk_pos.png";
//
// System.out.println("logoImag : " + logoImag);
// Image img=null;
// try {
// img = Image.getInstance(logoImag);
// img.setAlignment(Image.ALIGN_CENTER);
// } catch (Exception e) {
// System.out.println("Error Occurred :" +e.getMessage());// uncomment asneeded --;
// }
// img.setAlignment(1);
// PdfPCell imagecell = new PdfPCell(img, true);
// imagecell.setFixedHeight(10f);
// //imagecell.setNoWrap(false);
// imagecell.setBorder(0);
// imagecell.setHorizontalAlignment(Element.ALIGN_LEFT);
// imagecell.setVerticalAlignment(Element.ALIGN_CENTER);
// imagecell.setBorder(Rectangle.NO_BORDER|Rectangle.RIGHT);
// //PdfPTable t1= new PdfPTable(1);
// //t1.addCell(imagecell);
//
//
// headertable2.addCell(imagecell);
//
//
// }
// else if(comp_cd.trim().equalsIgnoreCase("PFZ")){
// headertable2.addCell(cell1);
// //String path=ServletActionContext.getServletContext().getRealPath("");
// //String logoImag = path+"\\pfizer_blk_pos.jpg";
// String logoImag = path+"\\pfizer_blk_pos.png";
//
// System.out.println("logoImag : " + logoImag);
// Image img=null;
// try {
// img = Image.getInstance(logoImag);
// img.setAlignment(Image.ALIGN_LEFT);
// } catch (Exception e) {
// System.out.println("Error Occurred :" +e.getMessage());// uncomment asneeded --;
// }
// img.setAlignment(1);
// PdfPCell imagecell = new PdfPCell(img, true);
// imagecell.setFixedHeight(10f);
// //imagecell.setNoWrap(false);
// imagecell.setBorder(0);
// imagecell.setHorizontalAlignment(Element.ALIGN_LEFT);
// imagecell.setVerticalAlignment(Element.ALIGN_CENTER);
// imagecell.setBorder(Rectangle.NO_BORDER|Rectangle.RIGHT);
// //PdfPTable t1= new PdfPTable(1);
// //t1.addCell(imagecell);
//// if(challan.getDsp_challan_no().contains("PIP")) {
//
// PdfPCell cell = new PdfPCell(new Phrase(" "));
// cell.setBorder(Rectangle.NO_BORDER);
// headertable2.addCell(cell);
//
//// }
//// else {
//// headertable2.addCell(imagecell);
//// }
// }
// else
// {
		cell1.setColspan(1);
		headertable2.addCell(cell1);
		
		
		

		
		///////////
		
		
		
		
		
		
		
		
		
		
		PdfPCell hcella_9 = null;
		
		
		if (challan.getLoc_web_site() != null && challan.getLoc_web_site().trim().isEmpty()) {
			hcella_9 = new PdfPCell(PdfStyle.createLabelCellWithoutBorderSmallFontNoPad(" "));
			hcella_9.setHorizontalAlignment(Element.ALIGN_LEFT);
			hcella_9.setVerticalAlignment(Element.ALIGN_TOP);
//			hcella_9.setBorder(Rectangle.LEFT);
		} else {
			hcella_9 = new PdfPCell(PdfStyle.createLabelCellWithoutBorderSmallFontNoPad("Website :"));
			hcella_9.setHorizontalAlignment(Element.ALIGN_LEFT);
			hcella_9.setVerticalAlignment(Element.ALIGN_TOP);
//			hcella_9.setBorder(Rectangle.LEFT);
		}

		
		PdfPCell hcella_4_9 = new PdfPCell(
				PdfStyle.createLabelCellWithBorderSmallFontNoBoldForHeader(challan.getLoc_web_site().trim()));
		hcella_4_9.setHorizontalAlignment(Element.ALIGN_LEFT);
		hcella_4_9.setVerticalAlignment(Element.ALIGN_TOP);
		hcella_4_9.setBorder(0);

		PdfPCell hcella_10 = new PdfPCell(PdfStyle.createLabelCellWithoutBorderSmallFontNoPad("Tel No :"));
		hcella_10.setHorizontalAlignment(Element.ALIGN_LEFT);
		hcella_10.setVerticalAlignment(Element.ALIGN_TOP);
//		hcella_10.setBorder(Rectangle.LEFT);

		PdfPCell hcella_4_10 = new PdfPCell(
				PdfStyle.createLabelCellWithBorderSmallFontNoBoldForHeader(challan.getLoc_telephone_no()));
		hcella_4_10.setHorizontalAlignment(Element.ALIGN_LEFT);
		hcella_4_10.setVerticalAlignment(Element.ALIGN_TOP);
		hcella_4_10.setBorder(0);

		PdfPCell hcella_11 = new PdfPCell(PdfStyle.createLabelCellWithoutBorderSmallFontNoPad("Email Id :"));
		hcella_11.setHorizontalAlignment(Element.ALIGN_LEFT);
		hcella_11.setVerticalAlignment(Element.ALIGN_TOP);
		
//		hcella_11.disableBorderSide(Rectangle.TOP);
//		hcella_11.disableBorderSide(Rectangle.RIGHT);

		PdfPCell hcella_4_11 = new PdfPCell(
				PdfStyle.createLabelCellWithoutBorderSmallFontNoPad(challan.getLoc_emailid()));
		hcella_4_11.setHorizontalAlignment(Element.ALIGN_LEFT);
		hcella_4_11.setVerticalAlignment(Element.ALIGN_TOP);
		
//		hcella_4_11.disableBorderSide(Rectangle.LEFT);
//		hcella_4_11.disableBorderSide(Rectangle.TOP);
//		hcella_4_11.disableBorderSide(Rectangle.RIGHT);
		
		
		///
		
		
		
		PdfPTable addressTblWebsite1 = new PdfPTable(2);
		for(int i=0;i<20;i++) {
			PdfPCell cellEmpty = new PdfPCell(
					PdfStyle.createLabelCellWithBorderSmallFontNoBoldForHeader(""));
			cellEmpty.setBorder(0);
			
			addressTblWebsite1.addCell(cellEmpty);
		}
		
	
		
		addressTblWebsite1.addCell(hcella_9);
		addressTblWebsite1.addCell(hcella_4_9);
		addressTblWebsite1.addCell(hcella_10);
		addressTblWebsite1.addCell(hcella_4_10);
		addressTblWebsite1.addCell(hcella_11);
		
		addressTblWebsite1.addCell(hcella_4_11);
		
		
		PdfPCell cell1Web1 = new PdfPCell(addressTblWebsite1);
		cell1Web1.setBorder(0);
		headertable2.addCell(cell1Web1);
		
		
		
// }

//TODO cell2 image add for medley by vivek 14-nov-2018

// ======================================================================================================================
// 2.3

//comp_cd = (String) context.getAttribute("comp_code");

		PdfPCell hcell16 = null;
		if (comp_cd.equalsIgnoreCase("NIL") || comp_cd.equalsIgnoreCase("NHP")) {
			hcell16 = new PdfPCell(PdfStyle.createLabelCellWithBorderSmallFont("Tax Inv Date"));
		} else {
			hcell16 = new PdfPCell(PdfStyle.createLabelCellWithBorderSmallFont("Date "));
		}
		hcell16.setHorizontalAlignment(Element.ALIGN_LEFT);

		PdfPCell hcell20 = null;

// if (comp_cd.equalsIgnoreCase("PFZ")) {
//    hcell20 = new PdfPCell(PdfStyle.createLabelCellWithBorderSmallFont("Challan #"));
//    hcell20.setHorizontalAlignment(Element.ALIGN_LEFT);
//
// } else if (comp_cd.equalsIgnoreCase("UCM")) {
//    hcell20 = new PdfPCell(PdfStyle.createLabelCellWithBorderSmallFont("GST INV No#"));
//    hcell20.setHorizontalAlignment(Element.ALIGN_LEFT);
//
// } else if (comp_cd.equalsIgnoreCase("NIL")) {
// hcell20 = new PdfPCell(PdfStyle.createLabelCellWithBorderSmallFont("Tax Invoice #"));
//    hcell20.setHorizontalAlignment(Element.ALIGN_LEFT);
// }else {
		hcell20 = new PdfPCell(PdfStyle.createLabelCellWithBorderSmallFont("Challan #"));
		hcell20.setHorizontalAlignment(Element.ALIGN_LEFT);
// }

		PdfPCell hcell21 = new PdfPCell(PdfStyle.createLabelCellWithBorderSmallFont("Customer"));
		hcell21.setHorizontalAlignment(Element.ALIGN_LEFT);

		PdfPCell hcell22 = new PdfPCell(PdfStyle.createLabelCellWithBorderSmallFont("Division "));
		hcell22.setHorizontalAlignment(Element.ALIGN_LEFT);

		PdfPCell hcell23 = new PdfPCell(PdfStyle.createLabelCellWithBorderSmallFont("Team"));
		hcell23.setHorizontalAlignment(Element.ALIGN_LEFT);

//PdfPCell hcell24 = new PdfPCell(PdfStyle.createLabelCellWithBorderSmallFont("Remark"));
//hcell24.setHorizontalAlignment(Element.ALIGN_LEFT);

		PdfPCell hcell17 = new PdfPCell(PdfStyle.createLabelCellWithBorderSmallFontTwiceHeight("Email"));
		hcell17.setHorizontalAlignment(Element.ALIGN_LEFT);

//		PdfPCell hcell18 = new PdfPCell(PdfStyle.createLabelCellWithBorderSmallFont("Telephone 1"));
//		hcell18.setHorizontalAlignment(Element.ALIGN_LEFT);
//
//		PdfPCell hcell19 = new PdfPCell(PdfStyle.createLabelCellWithBorderSmallFont("Telephone 2"));
//		hcell19.setHorizontalAlignment(Element.ALIGN_LEFT);
//		PdfPCell hcell19_from_to = null;
//
//		hcell19_from_to = new PdfPCell(PdfStyle.createLabelCellWithBorderSmallFont("Unique No : "));
		
		
		
		
		
		
		/// website  and tel no fro medley / abhishek g
	
		
		
		
		
		
		
		
		

		PdfPTable addressTbl2 = new PdfPTable(1);
		addressTbl2.addCell(hcell16);

		addressTbl2.addCell(hcell20);
		addressTbl2.addCell(hcell21);
		addressTbl2.addCell(hcell22);
		addressTbl2.addCell(hcell23);
		addressTbl2.addCell(hcell17);
//		addressTbl2.addCell(hcell18);
//		addressTbl2.addCell(hcell19);
//		addressTbl2.addCell(hcell19_from_to);
		PdfPCell cell3 = new PdfPCell(addressTbl2);
		cell3.setBorder(0);
		headertable2.addCell(cell3);
		

// ======================================================================================================================

// ======================================================================================================================
// 2.4
// Font medFont = new Font(Font.FontFamily.HELVETICA, 12,Font.NORMAL,
// BaseColor.BLACK);

//getDsp_challan_dt
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

//Team
		PdfPCell hcell33 = new PdfPCell(PdfStyle.createLabelCellWithBorderSmallFontNoBold(("")));
		hcell33.setHorizontalAlignment(Element.ALIGN_LEFT);

		PdfPCell hcell34 = new PdfPCell(PdfStyle.createLabelCellWithBorderSmallFontNoBold(
				challan.getDsp_challan_msg() == null ? "" : challan.getDsp_challan_msg()));
		hcell34.setHorizontalAlignment(Element.ALIGN_LEFT);

//		PdfPCell hcell27 = new PdfPCell(PdfStyle.createLabelCellWithBorderSmallFontNoBoldTwiceHeight(
//				(challan.getDspstaff_email().length() > 40 ? challan.getDspstaff_email().substring(0, 40)
//						: challan.getDspstaff_email())));
//		hcell27.setHorizontalAlignment(Element.ALIGN_LEFT);
//
//		PdfPCell hcell28 = new PdfPCell(PdfStyle.createLabelCellWithBorderSmallFontNoBold(
//				(challan.getDspstaff_mobile() == null ? "" : challan.getDspstaff_mobile())));
//		hcell28.setHorizontalAlignment(Element.ALIGN_LEFT);
//
////mobile2
//		PdfPCell hcell29 = new PdfPCell(PdfStyle.createLabelCellWithBorderSmallFontNoBold(
//				(challan.getDspstaff_mobile() == null ? "" : challan.getDspstaff_mobile()))); // telephone //added
//		// 2
//		hcell29.setHorizontalAlignment(Element.ALIGN_LEFT);

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
//		addressTbl3.addCell(hcell27);
//		addressTbl3.addCell(hcell28);
//		addressTbl3.addCell(hcell29);
		addressTbl3.addCell(hcell29_from_to);
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

		PdfPCell hcell35 = new PdfPCell(PdfStyle.createLabelCellWithBorderSmallFont("Ship To :"));
//PdfPCell hcell35 = new PdfPCell(new Phrase("Ship To :", font3));

		hcell35.setHorizontalAlignment(Element.ALIGN_LEFT);

		PdfPCell hcell36 = new PdfPCell(
				PdfStyle.createLabelCellWithoutBorder(challan.getStaff_name() == null ? "" : challan.getStaff_name()+"-"+challan.getHq_name()));
		
		
		hcell36.setHorizontalAlignment(Element.ALIGN_LEFT);
		hcell36.enableBorderSide(Rectangle.LEFT);
		hcell36.enableBorderSide(Rectangle.RIGHT);
		StringBuilder result2 = new StringBuilder();
		result2.append(challan.getStaff_addr1().length() > 55 ? challan.getStaff_addr1().substring(0, 55)
				: challan.getStaff_addr1());
		result2.append(System.lineSeparator());
		result2.append(challan.getStaff_addr2().length() > 55 ? challan.getStaff_addr2().substring(0, 55)
				: challan.getStaff_addr2());
		result2.append(System.lineSeparator());
		result2.append(challan.getStaff_addr3().length() > 55 ? challan.getStaff_addr3().substring(0, 55)
				: challan.getStaff_addr3());
		result2.append(System.lineSeparator());
		result2.append(challan.getStaff_addr4().length() > 55 ? challan.getStaff_addr4().substring(0, 55)
				: challan.getStaff_addr4());
		result2.append(System.lineSeparator());
		
		if(comp_cd.trim().equalsIgnoreCase("MDL") ||comp_cd.trim().equalsIgnoreCase("SNK")) {
			result2.append("Tel No : " + challan.getDspstaff_mobile());
			result2.append(System.lineSeparator());
			
		}else {
			result2.append("GSTIN : UNREGISTERED");
			result2.append(System.lineSeparator());
			result2.append("PAN : Not Available");
			result2.append(System.lineSeparator());
		}

		PdfPCell hcell37 = new PdfPCell(PdfStyle
				.createLabelCellWithoutBorderSmall7FontNoBold(result2.toString() == null ? "" : result2.toString()));
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
//String address1 = (String) context.getAttribute("address1");
//String address2 = (String) context.getAttribute("address2");
//String address3 = (String) context.getAttribute("address3");
//String address4 = (String) context.getAttribute("address4");
// WEB_SITE cinno
//String web_site = (String) context.getAttribute("web_site") == null ? " "
// : (String) context.getAttribute("web_site");
//String cinno = (String) context.getAttribute("cinno") == null ? " " : (String) context.getAttribute("cinno");
		Company company = ((Company) session.getServletContext().getAttribute(COMPANY_DATA));
		String address1 = company.getAddress1();
		String address2 = company.getAddress2();
		String address3 = company.getAddress3();
		String address4 = company.getAddress4();
		String web_site = company.getWeb_site();
		String cinno = company.getCinno();

		StringBuilder result3 = new StringBuilder();

// Company name added 27-1-2017
// if (challan.getDsp_challan_no().contains("PIP")) {
//
//    if (comp_cd.equalsIgnoreCase("PFZ")) {
// result3.append(comp_name_new);
//
// address1 = ":";
// address2 = ":";
// address3 = ":";
// address4 = ":";
// web_site = ":";
// cinno = ":";     ////Temporary Code 2-6-2020
//    } else {
// result3.append(company_name);
//    }
//
//    result3.append(System.lineSeparator());
// } else {
		System.out.println("company_name::::" + company_name);
		if (company_name.trim().isEmpty()) {
			result3.append(System.lineSeparator());
		} else {
			result3.append(company_name.length() > 30 ? company_name.substring(0, 30) : company_name);
			result3.append(System.lineSeparator());
		}
// }

		result3.append(address1.length() > 33 ? address1.substring(0, 33) : address1);
		result3.append(System.lineSeparator());
		result3.append(address2.length() > 33 ? address2.substring(0, 33) : address2);
		result3.append(System.lineSeparator());
		result3.append(address3.length() > 33 ? address3.substring(0, 33) : address3);
		result3.append(System.lineSeparator());
		result3.append(address4.length() > 33 ? address4.substring(0, 33) : address4);
		result3.append(System.lineSeparator());
		if (web_site.trim().isEmpty()) {
			result3.append("");
			result3.append(System.lineSeparator());
		} else {
			result3.append(web_site.length() > 33 ? "Website:" + web_site.substring(0, 33) : "Website:" + web_site);
			result3.append(System.lineSeparator());

		}
//System.out.println(cinno+"#sdfsdfsd");
		if (cinno.trim().isEmpty()) {
			result3.append("");
			result3.append(System.lineSeparator());
		} else {
			result3.append(cinno.length() > 33 ? "CIN NO:" + cinno.substring(0, 33) : "CIN NO:" + cinno);
			result3.append(System.lineSeparator());
		}

		PdfPCell hcell38 = new PdfPCell(PdfStyle.createLabelCellWithBorderSmallFont("Principal place of business"));
//PdfPCell hcell38 = new PdfPCell(new Phrase("Principal place of business ", font3));
		hcell38.setHorizontalAlignment(Element.ALIGN_LEFT);

		PdfPCell hcell39 = new PdfPCell(PdfStyle.createLabelCellWithoutBorderSmallFontNoBold(
				result3.length() > 170 ? result3.substring(0, 170) : result3.toString()));
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

		if (comp_cd.trim().equalsIgnoreCase("SER")) {
			// result4.append("");
			result4.append(System.lineSeparator());
			result4.append(System.lineSeparator());
			result4.append("                                                "); // Goods not for sale. Free samples
			// and / or promotional inputs only
			// having no commercial value
			result4.append(System.lineSeparator());
			result4.append(System.lineSeparator());
			result4.append(System.lineSeparator());
			result4.append(System.lineSeparator());
			result4.append(System.lineSeparator());
			result4.append(System.lineSeparator());
			result4.append(System.lineSeparator());
			result4.append(System.lineSeparator());
			result4.append(System.lineSeparator());

		} else {
			result4.append("Goods Not for sale.");
			result4.append(System.lineSeparator());

			if (!comp_cd.equalsIgnoreCase("NIL") && !comp_cd.equalsIgnoreCase("NHP")) {
				result4.append("Free samples and/"); // Goods not for sale. Free samples
				// and / or promotional inputs only
				// having no commercial value
				result4.append(System.lineSeparator());
				result4.append("or promotional inputs");
				result4.append(System.lineSeparator());
				result4.append("only having no");
				result4.append(System.lineSeparator());
				result4.append("commercial value.");
				result4.append(System.lineSeparator());
			} else {
				result4.append("                                                "); // Goods not for sale. Free samples
				// and / or promotional inputs only
				// having no commercial value
				result4.append(System.lineSeparator());
				result4.append(System.lineSeparator());
				result4.append(System.lineSeparator());
				result4.append(System.lineSeparator());
				result4.append(System.lineSeparator());
				result4.append(System.lineSeparator());
				result4.append(System.lineSeparator());
				result4.append(System.lineSeparator());
				result4.append(System.lineSeparator());

			}
		}
		PdfPCell hcell44 = new PdfPCell(PdfStyle.createLabelCellWithoutBorderSmallFontBold(result4.toString()));
		hcell44.setHorizontalAlignment(Element.ALIGN_LEFT);
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

		if (comp_cd.equalsIgnoreCase("PFZ")) {
			if (show_amount.equals("Y")) {
				hcell45 = new PdfPCell(PdfStyle.createLabelCellWithBorderSmallFont("Insurance Policy No."));
			} else {
				hcell45 = new PdfPCell(PdfStyle.createLabelCellWithBorderSmallFont(""));
			}

		} else if (comp_cd.equalsIgnoreCase("UCM")) {
			hcell45 = new PdfPCell(PdfStyle.createLabelCellWithBorderSmallFont(""));
		} else {
			if (show_amount.equals("Y")) {
				hcell45 = new PdfPCell(PdfStyle.createLabelCellWithBorderSmallFont("Insurance Policy No."));
			} else {
				hcell45 = new PdfPCell(PdfStyle.createLabelCellWithBorderSmallFont(""));
			}
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
		if (!comp_cd.trim().equalsIgnoreCase("SER")) {
			custTbl3.addCell(hcell47);
			custTbl3.addCell(hcell48);
		}
		PdfPCell cell8 = new PdfPCell(custTbl3);
		cell8.setBorder(0);
		cell8.setPaddingBottom(0);
		cell8.setUseBorderPadding(false);
		headertable3.addCell(cell8);

// =================================================Blank Cell Added To
// Remove Table Border3==============================

		PdfPCell hcell_blank3 = new PdfPCell(headertable3);
		hcell_blank3.setBorder(Rectangle.NO_BORDER);

		if (comp_cd.trim().equals("SER") || comp_cd.trim().equals(VET) || comp_cd.trim().equals("MDL")
				|| comp_cd.trim().equals("SNK")) {
			
			
			bodytable = new PdfPTable(6);
			bodytable.setWidthPercentage(100);
			bodytable.setWidths(bodyTableWidth);
			bodytable.addCell(PdfStyle.createLabelWithBGColour("Item"));
			bodytable.addCell(PdfStyle.createLabelWithBGColour("HSN Code"));
			bodytable.addCell(PdfStyle.createLabelWithBGColour("Batch/Art Work No"));
			bodytable.addCell(PdfStyle.createLabelWithBGColour("Expiry Date"));
			bodytable.addCell(PdfStyle.createLabelWithBGColour("Qty"));
			if (show_amount.equals("Y")) {
				bodytable.addCell(PdfStyle.createLabelWithBGColour("Taxable Value"));
			} else {
				bodytable.addCell(PdfStyle.createLabelWithBGColour(" "));
			}
		} else {
			bodytable = new PdfPTable(7);
			bodytable.setWidthPercentage(100);
			bodytable.setWidths(bodyTableWidth);
			bodytable.addCell(PdfStyle.createLabelWithBGColour("Item"));
			bodytable.addCell(PdfStyle.createLabelWithBGColour("HSN Code"));
			bodytable.addCell(PdfStyle.createLabelWithBGColour("Batch/Art Work No"));
			bodytable.addCell(PdfStyle.createLabelWithBGColour("Expiry Date"));
			bodytable.addCell(PdfStyle.createLabelWithBGColour("Qty"));
			if (show_amount.equals("Y")) {
				bodytable.addCell(PdfStyle.createLabelWithBGColour("Taxable Value"));
			} else {
				bodytable.addCell(PdfStyle.createLabelWithBGColour(" "));
			}
			bodytable.addCell(PdfStyle.createLabelWithBGColour("Taxable Amount"));
		}
// =================================================Blank Cell Added To
// Remove Table Border4==============================

		PdfPCell hcell_blank4 = new PdfPCell(bodytable);
		hcell_blank4.setBorder(Rectangle.NO_BORDER);
		headertable.addCell(hcell_blank);
		headertable.addCell(hcell_blank2);
		headertable.addCell(hcell_blank3);
		headertable.addCell(hcell_blank4);

		return headertable;
	}
	
	
	
	
	
	
	
	
	
	public PdfPTable newPageNoGst(ColumnText column, PdfPTable bodytable, FooterWithoutPageNo event,
			HashMap<String, String> printLabel, boolean isFooterData, int rows, boolean pageflag, int pagecount,
			BigDecimal total, int division, Integer loc, String frm_challan, String to_challan, String dispatchType,
			String prodtype, String path, List<ViewPrePrintedDetailChallan> challan, String show_amount,
			boolean footer_ind, String dsp_transporter, String ship_doc_no, String date, String cases, String weigh,
			String dsp_challan_msg, BigDecimal goods_total, BigDecimal final_total, BigDecimal header_Igst,
			BigDecimal header_Cgst, BigDecimal header_Sgst, BigDecimal round_off, String gst_doc_type,
			String dsp_lorry_no, String loc_narration, float[] bodyTableWidth, String companyCode)
			throws DocumentException {

		PdfPCell cell = null;
		try {
			// System.out.println("limit:"+limit);
			// System.out.println("rows::"+rows);

			if (limit - (rows) > 0) {
// add extra empty rows in body table
				cell = new PdfPCell();
				PdfPTable temp = new PdfPTable(6);
				temp.setWidths(bodyTableWidth);

				temp.addCell(PdfStyle.createValueCellWithHeight((""), false, true, 9f));
				temp.addCell(PdfStyle.createValueCellWithHeight((""), false, true, 9f));
				temp.addCell(PdfStyle.createValueCellWithHeight((""), false, true, 9f));
				temp.addCell(PdfStyle.createValueCellWithHeight((""), true, true, 9f));
				temp.addCell(PdfStyle.createValueCellWithHeight((""), true, true, 9f));
				temp.addCell(PdfStyle.createValueCellWithHeight((""), true, true, 9f));

				temp.addCell(PdfStyle.createValueCellWithHeight((" "), false, true, 9f));
				temp.addCell(PdfStyle.createValueCellWithHeight((""), false, true, 9f));
				temp.addCell(PdfStyle.createValueCellWithHeight((""), false, true, 9f));
				temp.addCell(PdfStyle.createValueCellWithHeight((""), true, true, 9f));
				temp.addCell(PdfStyle.createValueCellWithHeight((""), true, true, 9f));
				temp.addCell(PdfStyle.createValueCellWithHeight((""), true, true, 9f));

				cell = new PdfPCell(temp);
				cell.setBorder(0);
				cell.setColspan(6);
// System.out.println("limit-row:::::"+(limit-rows));
				for (int i = 0; i < (limit - rows); i++) {
					// System.out.println("printing row:::"+i);
					bodytable.addCell(cell);
				}
			}

			cell = new PdfPCell(PdfStyle.createSingleLine());
			cell.setColspan(6);
			bodytable.addCell(cell);

			column.addElement(bodytable);
			column.setSimpleColumn(20, 0, 580, 512);
			column.go();

			bodytable = new PdfPTable(6);
			bodytable.setWidthPercentage(100);
			bodytable.setWidths(bodyTableWidth);	

			event.setFooter(createFooterNoGst(printLabel, isFooterData, pageflag, pagecount, total, division, loc,
					frm_challan, to_challan, dispatchType, prodtype, path, challan, show_amount, footer_ind,
					dsp_transporter, ship_doc_no, date, cases, weigh, dsp_challan_msg, goods_total, final_total,
					header_Igst, header_Cgst, header_Sgst, round_off, gst_doc_type, dsp_lorry_no, loc_narration,
					challan.get(0).getLoc_add2(), bodyTableWidth));

		} catch (DocumentException e) {
			throw e;
		}
		return bodytable;
	}

	private PdfPTable createFooterNoGst(HashMap<String, String> printLabel, boolean isFooterData, boolean pageflag,
			int pagecount, BigDecimal total, int division, Integer loc, String frm_challan, String to_challan,
			String dispatchType, String prodtype, String path, List<ViewPrePrintedDetailChallan> challan,
			String show_amount, boolean footer_ind, String dsp_transporter, String ship_doc_no, String date,
			String cases, String weigh, String dsp_challan_msg, BigDecimal goods_total, BigDecimal final_total,
			BigDecimal header_Igst, BigDecimal header_Cgst, BigDecimal header_Sgst, BigDecimal round_off,
			String gst_doc_type, String dsp_lorry_no, String loc_narration, String loc_addr2, float[] bodyTableWidth)
			throws DocumentException {
		
		PdfPTable maintable = new PdfPTable(2);
		
		maintable.setSpacingBefore(30f);
		
		try {

			PdfPCell cell = null;
			cell = new PdfPCell();

//Testing
			String narrration = printService.getLocationNarrationByLocId(Long.parseLong(loc.toString()));
			maintable.setWidthPercentage(100);
			maintable.setWidths(new float[] { 5.5f, 4.5f });

			// footer left cell
			PdfPTable foot_left_table = new PdfPTable(1);
			foot_left_table.setWidthPercentage(100);
			foot_left_table.setWidths(new float[] { 10f });

			PdfPCell foot_bottom_cell1 = new PdfPCell(
					PdfStyle.createUltimateCellWithBorderWithDisableBorder((narrration), Element.ALIGN_LEFT,
							Element.ALIGN_TOP, 9, 14f, true, 1, 1, false, true, true, false, true));
			foot_left_table.addCell(foot_bottom_cell1);

//    PdfPCell foot_bottom_cell1 = new PdfPCell(
//    PdfStyle.createUltimateCellWithBorderWithDisableBorder(" ", Element.ALIGN_LEFT,
//    Element.ALIGN_TOP, 9, 14f, true, 1, 1, false, true, true, false, true));
//    foot_left_table.addCell(foot_bottom_cell1);

			// footer right cell
			PdfPTable foot_right_table = new PdfPTable(2);
			foot_right_table.setWidthPercentage(100);
			foot_right_table.setWidths(new float[] { 5.55f, 4.45f });

			String goodsOrTotal = "";
			if (comp_cd.equalsIgnoreCase("NIL") || comp_cd.equalsIgnoreCase("NHP")
					|| comp_cd.trim().equalsIgnoreCase("SER") || comp_cd.trim().equalsIgnoreCase(VET)
					|| comp_cd.trim().equalsIgnoreCase("MDL") || comp_cd.trim().equalsIgnoreCase("SNK"))
				goodsOrTotal = "Total";
			else
				goodsOrTotal = "Goods";
			// new changes for remove column in pdf cell by vivek based on footer indicator

//   if(footer_ind && comp_cd.equalsIgnoreCase("NIL")){
			if (footer_ind && comp_cd.equalsIgnoreCase("PFZ")) {
				PdfPCell foot_bottom_right_cell1 = new PdfPCell(PdfStyle.createUltimateCellWithBorderWithDisableBorder(
						(goodsOrTotal + " Value"), Element.ALIGN_LEFT, Element.ALIGN_TOP, 8, 14f, true, 1, 1, false,
						true, true, false, true));
				foot_right_table.addCell(foot_bottom_right_cell1);
				PdfPCell foot_bottom_right_cell2 = new PdfPCell(PdfStyle.createUltimateCellWithBorderWithDisableBorder(
						(goods_total.setScale(2, BigDecimal.ROUND_HALF_UP).toString()), Element.ALIGN_RIGHT,
						Element.ALIGN_TOP, 8, 14f, true, 1, 1, false, true, true, false, true));
				foot_right_table.addCell(foot_bottom_right_cell2);

				if (gst_doc_type.contains("INTER")) {
					PdfPCell foot_bottom_right_cell33 = new PdfPCell(
							PdfStyle.createUltimateCellWithBorderWithDisableBorder(("IGST"), Element.ALIGN_LEFT,
									Element.ALIGN_TOP, 8, 14f, true, 1, 1, false, true, true, false, true));
					foot_right_table.addCell(foot_bottom_right_cell33);
					PdfPCell foot_bottom_right_cell43 = new PdfPCell(
							PdfStyle.createUltimateCellWithBorderWithDisableBorder(
									(header_Igst.setScale(2, BigDecimal.ROUND_HALF_UP).toString()), Element.ALIGN_RIGHT,
									Element.ALIGN_TOP, 8, 14f, true, 1, 1, false, true, true, false, true));
					foot_right_table.addCell(foot_bottom_right_cell43);
				} else if (gst_doc_type.contains("INTRA")) {
					PdfPCell foot_bottom_right_cell3 = new PdfPCell(
							PdfStyle.createUltimateCellWithBorderWithDisableBorder(("CGST"), Element.ALIGN_LEFT,
									Element.ALIGN_TOP, 8, 14f, true, 1, 1, false, true, true, false, true));
					foot_right_table.addCell(foot_bottom_right_cell3);
					PdfPCell foot_bottom_right_cell4 = new PdfPCell(
							PdfStyle.createUltimateCellWithBorderWithDisableBorder(
									(header_Cgst.setScale(2, BigDecimal.ROUND_HALF_UP).toString()), Element.ALIGN_RIGHT,
									Element.ALIGN_TOP, 8, 14f, true, 1, 1, false, true, true, false, true));
					foot_right_table.addCell(foot_bottom_right_cell4);
					PdfPCell foot_bottom_right_cell5 = new PdfPCell(
							PdfStyle.createUltimateCellWithBorderWithDisableBorder(("SGST"), Element.ALIGN_LEFT,
									Element.ALIGN_TOP, 8, 14f, true, 1, 1, false, true, true, false, true));
					foot_right_table.addCell(foot_bottom_right_cell5);
					PdfPCell foot_bottom_right_cell6 = new PdfPCell(
							PdfStyle.createUltimateCellWithBorderWithDisableBorder(
									(header_Sgst.setScale(2, BigDecimal.ROUND_HALF_UP).toString()), Element.ALIGN_RIGHT,
									Element.ALIGN_TOP, 8, 14f, true, 1, 1, false, true, true, false, true));
					foot_right_table.addCell(foot_bottom_right_cell6);
				}

				PdfPCell foot_bottom_right_cell73 = new PdfPCell(
						PdfStyle.createUltimateCellWithBorderWithDisableBorder(("Round Off (+/-)"), Element.ALIGN_LEFT,
								Element.ALIGN_TOP, 8, 14f, true, 1, 1, false, true, true, false, true));
				foot_right_table.addCell(foot_bottom_right_cell73);
				PdfPCell foot_bottom_right_cell83 = new PdfPCell(PdfStyle.createUltimateCellWithBorderWithDisableBorder(
						(round_off.setScale(2, BigDecimal.ROUND_HALF_UP).toString()), Element.ALIGN_RIGHT,
						Element.ALIGN_TOP, 8, 14f, true, 1, 1, false, true, true, false, true));
				foot_right_table.addCell(foot_bottom_right_cell83);

				PdfPCell foot_bottom_right_cell7 = new PdfPCell(
						PdfStyle.createUltimateCellWithBorderWithDisableBorder(("Total Amount"), Element.ALIGN_LEFT,
								Element.ALIGN_TOP, 8, 14f, true, 1, 1, false, true, true, false, true));
				foot_bottom_right_cell7.setGrayFill(0.7f);
				foot_right_table.addCell(foot_bottom_right_cell7); // end hear
				PdfPCell foot_bottom_right_cell8 = new PdfPCell(PdfStyle.createUltimateCellWithBorderWithDisableBorder(
						(final_total.setScale(2, BigDecimal.ROUND_HALF_UP).toString()), Element.ALIGN_RIGHT,
						Element.ALIGN_TOP, 8, 14f, true, 1, 1, false, true, true, false, true));
				foot_bottom_right_cell8.setGrayFill(0.7f);
				foot_right_table.addCell(foot_bottom_right_cell8);

			} else if (footer_ind && (comp_cd.trim().equalsIgnoreCase("SER") || comp_cd.trim().equalsIgnoreCase(VET)
					|| comp_cd.trim().equalsIgnoreCase("MDL") || comp_cd.trim().equalsIgnoreCase("SNK"))) {
				PdfPCell foot_bottom_right_cell1 = new PdfPCell(PdfStyle.createUltimateCellWithBorderWithDisableBorder(
						(goodsOrTotal + " Value"), Element.ALIGN_LEFT, Element.ALIGN_TOP, 8, 14f, true, 1, 1, false,
						true, true, false, true));
				foot_right_table.addCell(foot_bottom_right_cell1);
				PdfPCell foot_bottom_right_cell2 = new PdfPCell(PdfStyle.createUltimateCellWithBorderWithDisableBorder(
						(goods_total.setScale(2, BigDecimal.ROUND_HALF_UP).toString()), Element.ALIGN_RIGHT,
						Element.ALIGN_TOP, 8, 14f, true, 1, 1, false, true, true, false, true));
				foot_right_table.addCell(foot_bottom_right_cell2);

				if (gst_doc_type.contains("INTER")) {
					PdfPCell foot_bottom_right_cell33 = new PdfPCell(
							PdfStyle.createUltimateCellWithBorderWithDisableBorder((""), Element.ALIGN_LEFT,
									Element.ALIGN_TOP, 8, 14f, true, 1, 1, false, true, true, false, true));
					foot_right_table.addCell(foot_bottom_right_cell33);
					PdfPCell foot_bottom_right_cell43 = new PdfPCell(
							PdfStyle.createUltimateCellWithBorderWithDisableBorder((""), Element.ALIGN_RIGHT,
									Element.ALIGN_TOP, 8, 14f, true, 1, 1, false, true, true, false, true));
					foot_right_table.addCell(foot_bottom_right_cell43);
				} else if (gst_doc_type.contains("INTRA")) {
					PdfPCell foot_bottom_right_cell3 = new PdfPCell(
							PdfStyle.createUltimateCellWithBorderWithDisableBorder((""), Element.ALIGN_LEFT,
									Element.ALIGN_TOP, 8, 14f, true, 1, 1, false, true, true, false, true));
					foot_right_table.addCell(foot_bottom_right_cell3);
					PdfPCell foot_bottom_right_cell4 = new PdfPCell(
							PdfStyle.createUltimateCellWithBorderWithDisableBorder((""), Element.ALIGN_RIGHT,
									Element.ALIGN_TOP, 8, 14f, true, 1, 1, false, true, true, false, true));
					foot_right_table.addCell(foot_bottom_right_cell4);
					PdfPCell foot_bottom_right_cell5 = new PdfPCell(
							PdfStyle.createUltimateCellWithBorderWithDisableBorder((""), Element.ALIGN_LEFT,
									Element.ALIGN_TOP, 8, 14f, true, 1, 1, false, true, true, false, true));
					foot_right_table.addCell(foot_bottom_right_cell5);
					PdfPCell foot_bottom_right_cell6 = new PdfPCell(
							PdfStyle.createUltimateCellWithBorderWithDisableBorder((""), Element.ALIGN_RIGHT,
									Element.ALIGN_TOP, 8, 14f, true, 1, 1, false, true, true, false, true));
					foot_right_table.addCell(foot_bottom_right_cell6);
				}

				PdfPCell foot_bottom_right_cell73 = new PdfPCell(
						PdfStyle.createUltimateCellWithBorderWithDisableBorder((""), Element.ALIGN_LEFT,
								Element.ALIGN_TOP, 8, 14f, true, 1, 1, false, true, true, false, true));
				foot_right_table.addCell(foot_bottom_right_cell73);
				PdfPCell foot_bottom_right_cell83 = new PdfPCell(
						PdfStyle.createUltimateCellWithBorderWithDisableBorder((""), Element.ALIGN_RIGHT,
								Element.ALIGN_TOP, 8, 14f, true, 1, 1, false, true, true, false, true));
				foot_right_table.addCell(foot_bottom_right_cell83);

				PdfPCell foot_bottom_right_cell7 = new PdfPCell(
						PdfStyle.createUltimateCellWithBorderWithDisableBorder((""), Element.ALIGN_LEFT,
								Element.ALIGN_TOP, 8, 14f, true, 1, 1, false, true, true, false, true));
				foot_bottom_right_cell7.setGrayFill(0.7f);
				foot_right_table.addCell(foot_bottom_right_cell7); // end hear
				PdfPCell foot_bottom_right_cell8 = new PdfPCell(
						PdfStyle.createUltimateCellWithBorderWithDisableBorder((""), Element.ALIGN_RIGHT,
								Element.ALIGN_TOP, 8, 14f, true, 1, 1, false, true, true, false, true));
				foot_bottom_right_cell8.setGrayFill(0.7f);
				foot_right_table.addCell(foot_bottom_right_cell8);

			} else {
				PdfPCell foot_bottom_right_cell1 = new PdfPCell(
						PdfStyle.createUltimateCellWithBorderWithDisableBorder((""), Element.ALIGN_LEFT,
								Element.ALIGN_TOP, 8, 14f, true, 1, 1, false, true, true, false, true));
				foot_right_table.addCell(foot_bottom_right_cell1);
				PdfPCell foot_bottom_right_cell2 = new PdfPCell(
						PdfStyle.createUltimateCellWithBorderWithDisableBorder((""), Element.ALIGN_RIGHT,
								Element.ALIGN_TOP, 8, 14f, true, 1, 1, false, true, true, false, true));
				foot_right_table.addCell(foot_bottom_right_cell2);

				if (gst_doc_type.contains("INTER")) {
					PdfPCell foot_bottom_right_cell33 = new PdfPCell(
							PdfStyle.createUltimateCellWithBorderWithDisableBorder((""), Element.ALIGN_LEFT,
									Element.ALIGN_TOP, 8, 14f, true, 1, 1, false, true, true, false, true));
					foot_right_table.addCell(foot_bottom_right_cell33);
					PdfPCell foot_bottom_right_cell43 = new PdfPCell(
							PdfStyle.createUltimateCellWithBorderWithDisableBorder((""), Element.ALIGN_RIGHT,
									Element.ALIGN_TOP, 8, 14f, true, 1, 1, false, true, true, false, true));
					foot_right_table.addCell(foot_bottom_right_cell43);
				} else if (gst_doc_type.contains("INTRA")) {
					PdfPCell foot_bottom_right_cell3 = new PdfPCell(
							PdfStyle.createUltimateCellWithBorderWithDisableBorder((""), Element.ALIGN_LEFT,
									Element.ALIGN_TOP, 8, 14f, true, 1, 1, false, true, true, false, true));
					foot_right_table.addCell(foot_bottom_right_cell3);
					PdfPCell foot_bottom_right_cell4 = new PdfPCell(
							PdfStyle.createUltimateCellWithBorderWithDisableBorder((""), Element.ALIGN_RIGHT,
									Element.ALIGN_TOP, 8, 14f, true, 1, 1, false, true, true, false, true));
					foot_right_table.addCell(foot_bottom_right_cell4);
					PdfPCell foot_bottom_right_cell5 = new PdfPCell(
							PdfStyle.createUltimateCellWithBorderWithDisableBorder((""), Element.ALIGN_LEFT,
									Element.ALIGN_TOP, 8, 14f, true, 1, 1, false, true, true, false, true));
					foot_right_table.addCell(foot_bottom_right_cell5);
					PdfPCell foot_bottom_right_cell6 = new PdfPCell(
							PdfStyle.createUltimateCellWithBorderWithDisableBorder((""), Element.ALIGN_RIGHT,
									Element.ALIGN_TOP, 8, 14f, true, 1, 1, false, true, true, false, true));
					foot_right_table.addCell(foot_bottom_right_cell6);
				}

				PdfPCell foot_bottom_right_cell73 = new PdfPCell(
						PdfStyle.createUltimateCellWithBorderWithDisableBorder((""), Element.ALIGN_LEFT,
								Element.ALIGN_TOP, 8, 14f, true, 1, 1, false, true, true, false, true));
				foot_right_table.addCell(foot_bottom_right_cell73);
				PdfPCell foot_bottom_right_cell83 = new PdfPCell(
						PdfStyle.createUltimateCellWithBorderWithDisableBorder((""), Element.ALIGN_RIGHT,
								Element.ALIGN_TOP, 8, 14f, true, 1, 1, false, true, true, false, true));
				foot_right_table.addCell(foot_bottom_right_cell83);

				PdfPCell foot_bottom_right_cell7 = new PdfPCell(
						PdfStyle.createUltimateCellWithBorderWithDisableBorder((""), Element.ALIGN_LEFT,
								Element.ALIGN_TOP, 8, 14f, true, 1, 1, false, true, true, false, true));
				foot_bottom_right_cell7.setGrayFill(0.7f);
				foot_right_table.addCell(foot_bottom_right_cell7); // end hear
				PdfPCell foot_bottom_right_cell8 = new PdfPCell(
						PdfStyle.createUltimateCellWithBorderWithDisableBorder((""), Element.ALIGN_RIGHT,
								Element.ALIGN_TOP, 8, 14f, true, 1, 1, false, true, true, false, true));
				foot_bottom_right_cell8.setGrayFill(0.7f);
				foot_right_table.addCell(foot_bottom_right_cell8);

			}

			// end hear
			// footer bottom2
			PdfPTable foot_bottom_table2 = new PdfPTable(3);
			foot_bottom_table2.setWidthPercentage(100);
			foot_bottom_table2.setWidths(new float[] { 3.33f, 2.17f, 4.5f });

			// String company_name = (String) context.getAttribute("company_name");

			PdfPCell foot_bottom_cell02 = new PdfPCell(PdfStyle.createUltimateCellWithBorderWithDisableBorder(
					("Transporter Details"), Element.ALIGN_CENTER, Element.ALIGN_TOP, 9, 14f, true, 1, 1, false, true,
					true, false, true));
			foot_bottom_table2.addCell(foot_bottom_cell02);
			PdfPCell foot_bottom_cell03 = new PdfPCell(PdfStyle.createUltimateCellWithBorderWithDisableBorder((""),
					Element.ALIGN_LEFT, Element.ALIGN_TOP, 8, 14f, true, 1, 1, false, true, true, false, true));
			foot_bottom_table2.addCell(foot_bottom_cell03);
			PdfPCell foot_bottom_cell04 = null;
			if (frm_challan.contains("PIP")) {
				if (comp_cd.equalsIgnoreCase("PFZ")) {
					foot_bottom_cell04 = new PdfPCell(PdfStyle.createUltimateCellWithBorderWithDisableBorder(
							("For Pfizer Products India Private Limited."), Element.ALIGN_CENTER, Element.ALIGN_TOP, 9,
							14f, true, 1, 1, false, true, true, false, true));
				} else { // adding new footer indicator
					if (footer_ind && comp_cd.equalsIgnoreCase("NIL") || comp_cd.equalsIgnoreCase("NHP")) // new check
																											// for
																											// footer
																											// indicator
																											// and
																											// companey
					// code
					{
						foot_bottom_cell04 = new PdfPCell(PdfStyle.createUltimateCellWithBorderWithDisableBorder(
								("For " + company_name), Element.ALIGN_CENTER, Element.ALIGN_TOP, 9, 14f, true, 1, 1,
								false, true, true, false, true));
					} else {
						foot_bottom_cell04 = new PdfPCell(
								PdfStyle.createUltimateCellWithBorderWithDisableBorder((" "), Element.ALIGN_CENTER,
										Element.ALIGN_TOP, 9, 14f, true, 1, 1, false, true, true, false, true));
					}
				}
				foot_bottom_table2.addCell(foot_bottom_cell04);
			} else {
				if (footer_ind && comp_cd.equalsIgnoreCase("NIL") || comp_cd.equalsIgnoreCase("NHP")) { // new check for
																										// footer
																										// indicator and
																										// companey code
					foot_bottom_cell04 = new PdfPCell(PdfStyle.createUltimateCellWithBorderWithDisableBorder(
							("For " + company_name), Element.ALIGN_CENTER, Element.ALIGN_TOP, 9, 14f, true, 1, 1, false,
							true, true, false, true));
				} else if (comp_cd.trim().equalsIgnoreCase("SER") || comp_cd.trim().equalsIgnoreCase(VET)) {
					foot_bottom_cell04 = new PdfPCell(
							PdfStyle.createUltimateCellWithBorderWithDisableBorder((""), Element.ALIGN_CENTER,
									Element.ALIGN_TOP, 9, 14f, true, 1, 1, false, true, true, false, true));
				} else if (comp_cd.trim().equalsIgnoreCase("P&G")) {
					foot_bottom_cell04 = new PdfPCell(PdfStyle.createUltimateCellWithBorderWithDisableBorder(
							("For " + company_name), Element.ALIGN_CENTER, Element.ALIGN_TOP, 9, 14f, true, 1, 1, false,
							true, true, false, true));
				} else {
					foot_bottom_cell04 = new PdfPCell(
							PdfStyle.createUltimateCellWithBorderWithDisableBorder((" "), Element.ALIGN_CENTER,
									Element.ALIGN_TOP, 9, 14f, true, 1, 1, false, true, true, false, true));
				}

				foot_bottom_table2.addCell(foot_bottom_cell04);

			}

			PdfPCell foot_bottom_cell21 = new PdfPCell(
					PdfStyle.createUltimateCellWithBorderWithDisableBorder(("Transporter Name :" + dsp_transporter),
							Element.ALIGN_LEFT, Element.ALIGN_TOP, 8, 14f, true, 1, 1, false, true, true, false, true));
			foot_bottom_table2.addCell(foot_bottom_cell21);
			PdfPCell foot_bottom_cell22 = new PdfPCell(PdfStyle.createUltimateCellWithBorderWithDisableBorder((""),
					Element.ALIGN_CENTER, Element.ALIGN_TOP, 8, 14f, true, 1, 1, false, true, true, false, true));
			foot_bottom_table2.addCell(foot_bottom_cell22);
			PdfPCell foot_bottom_cell32 = new PdfPCell(
					PdfStyle.createUltimateCellWithBorderWithDisableBorder((loc_narration), Element.ALIGN_CENTER,
							Element.ALIGN_TOP, 8, 14f, true, 1, 1, false, false, true, false, true));
			foot_bottom_table2.addCell(foot_bottom_cell32);

			PdfPCell foot_bottom_cell06 = new PdfPCell(
					PdfStyle.createUltimateCellWithBorderWithDisableBorder(("LR No:" + ship_doc_no), Element.ALIGN_LEFT,
							Element.ALIGN_TOP, 8, 14f, true, 1, 1, false, true, true, false, true));
			foot_bottom_table2.addCell(foot_bottom_cell06);
			PdfPCell foot_bottom_cell42 = new PdfPCell(PdfStyle.createUltimateCellWithBorderWithDisableBorder(
					("LR Date : " + (date != null ? Utility.getRptDateFormat(date) : "")), Element.ALIGN_LEFT,
					Element.ALIGN_TOP, 8, 14f, true, 1, 1, false, true, true, false, true));
			foot_bottom_table2.addCell(foot_bottom_cell42);
			// do changes here
			System.out.println("loc_addr2:::" + loc_addr2);
			if (comp_cd.trim().equals(VET) || comp_cd.trim().equals("MDL") || comp_cd.trim().equals("SNK")) {
				PdfPCell foot_bottom_cell07 = new PdfPCell(PdfStyle.createUltimateCellWithBorderWithDisableBorder((" "),
						Element.ALIGN_LEFT, Element.ALIGN_TOP, 8, 14f, true, 1, 1, false, false, true, false, true));
				foot_bottom_table2.addCell(foot_bottom_cell07);
			} else {
				PdfPCell foot_bottom_cell07 = new PdfPCell(
						PdfStyle.createUltimateCellWithBorderWithDisableBorder((loc_addr2), Element.ALIGN_LEFT,
								Element.ALIGN_TOP, 8, 14f, true, 1, 1, false, false, true, false, true));
				foot_bottom_table2.addCell(foot_bottom_cell07);
			}
			PdfPCell foot_bottom_cell52 = null;
			System.out.println("dsp_lorry_no::" + dsp_lorry_no);
			if (comp_cd.equalsIgnoreCase("NIL") || comp_cd.equalsIgnoreCase("NHP")) {
				foot_bottom_cell52 = new PdfPCell(PdfStyle.createUltimateCellWithBorderWithDisableBorder(
						("E-Way Bill No: " + dsp_lorry_no), Element.ALIGN_LEFT, Element.ALIGN_TOP, 8, 14f, true, 1, 1,
						false, true, true, false, true));
			} else {
				foot_bottom_cell52 = new PdfPCell(
						PdfStyle.createUltimateCellWithBorderWithDisableBorder(("Way Bill No:"), Element.ALIGN_LEFT,
								Element.ALIGN_TOP, 8, 14f, true, 1, 1, false, true, true, false, true));
			}

			foot_bottom_table2.addCell(foot_bottom_cell52);
			PdfPCell foot_bottom_cell62 = new PdfPCell(
					PdfStyle.createUltimateCellWithBorderWithDisableBorder(("Date :"), Element.ALIGN_LEFT,
							Element.ALIGN_TOP, 8, 14f, true, 1, 1, false, true, true, false, true));
			foot_bottom_table2.addCell(foot_bottom_cell62);
			PdfPCell foot_bottom_cell08 = new PdfPCell(PdfStyle.createUltimateCellWithBorderWithDisableBorder((""),
					Element.ALIGN_LEFT, Element.ALIGN_TOP, 8, 14f, true, 1, 1, false, false, true, false, true));
			foot_bottom_table2.addCell(foot_bottom_cell08);

			PdfPCell foot_bottom_cell72 = new PdfPCell(
					PdfStyle.createUltimateCellWithBorderWithDisableBorder(("No Of Cases :" + cases),
							Element.ALIGN_LEFT, Element.ALIGN_TOP, 8, 14f, true, 1, 1, false, true, true, false, true));
			foot_bottom_table2.addCell(foot_bottom_cell72);
			PdfPCell foot_bottom_cell82 = new PdfPCell(
					PdfStyle.createUltimateCellWithBorderWithDisableBorder(("Weight :" + weigh), Element.ALIGN_LEFT,
							Element.ALIGN_TOP, 8, 14f, true, 1, 1, false, true, true, false, true));
			foot_bottom_table2.addCell(foot_bottom_cell82);
			PdfPCell foot_bottom_cell09 = new PdfPCell(PdfStyle.createUltimateCellWithBorderWithDisableBorder((""),
					Element.ALIGN_LEFT, Element.ALIGN_TOP, 8, 14f, true, 1, 1, false, false, true, false, true));
			foot_bottom_table2.addCell(foot_bottom_cell09);

			PdfPCell foot_bottom_cell92 = new PdfPCell(
					PdfStyle.createUltimateCellWithBorderWithDisableBorder(("Remarks : " + dsp_challan_msg),
							Element.ALIGN_LEFT, Element.ALIGN_TOP, 8, 14f, true, 1, 1, false, true, true, false, true));
			foot_bottom_table2.addCell(foot_bottom_cell92);
			PdfPCell foot_bottom_cell10 = new PdfPCell(PdfStyle.createUltimateCellWithBorderWithDisableBorder((""),
					Element.ALIGN_LEFT, Element.ALIGN_TOP, 8, 14f, true, 1, 1, false, false, true, true, true));
			foot_bottom_table2.addCell(foot_bottom_cell10);

			// new changes for remove column in pdf cell by vivek based on footer indicator

			if (footer_ind) {
				PdfPCell foot_bottom_cell11 = new PdfPCell(PdfStyle.createUltimateCellWithBorderWithDisableBorder(
						("Authorized Signatory"), Element.ALIGN_CENTER, Element.ALIGN_TOP, 9, 14f, true, 1, 1, false,
						true, true, false, true));
				foot_bottom_table2.addCell(foot_bottom_cell11);
			} else {
				PdfPCell foot_bottom_cell11 = new PdfPCell(PdfStyle.createUltimateCellWithBorderWithDisableBorder((""),
						Element.ALIGN_CENTER, Element.ALIGN_TOP, 9, 14f, true, 1, 1, false, true, true, false, true));
				foot_bottom_table2.addCell(foot_bottom_cell11);
			}

			// footer bottom3
			PdfPTable foot_bottom_table3 = new PdfPTable(1);
			foot_bottom_table3.setWidthPercentage(100);
			foot_bottom_table3.setWidths(new float[] { 10f });

			// Print changes made for novartis
			// added boxes in copies description
			PdfPCell foot_bottom_cell31 = null;
			if (comp_cd.trim().equalsIgnoreCase("SER") || comp_cd.trim().equalsIgnoreCase(VET)
					|| comp_cd.trim().equalsIgnoreCase("MDL") || comp_cd.trim().equalsIgnoreCase("SNK")) {
				foot_bottom_cell31 = new PdfPCell(PdfStyle.createUltimateCellWithBorderWithDisableBorder("",
						Element.ALIGN_CENTER, Element.ALIGN_TOP, 9, 14f, true, 1, 1, true, true, false, false, false));
			} else {
				if (comp_cd.equalsIgnoreCase("NIL") || comp_cd.equalsIgnoreCase("NHP")) {
					Paragraph p = new Paragraph();
					Font checkBoxFont = new Font(Font.FontFamily.ZAPFDINGBATS, 14);
					Chunk checkBox = new Chunk("o", checkBoxFont);
					p.add(new Phrase("Copies : ", new Font(Font.FontFamily.TIMES_ROMAN, 7, Font.BOLD)));
					p.add(checkBox);
					p.add(new Phrase(" :ORIGINAL FOR RECIPIENT.  ",
							new Font(Font.FontFamily.TIMES_ROMAN, 7, Font.BOLD)));
					p.add(checkBox);
					p.add(new Phrase(" :DUPLICATE FOR TRANSPORTER.  ",
							new Font(Font.FontFamily.TIMES_ROMAN, 7, Font.BOLD)));
					p.add(checkBox);
					p.add(new Phrase(" :TRIPLICATE FOR SUPPLIER.  ",
							new Font(Font.FontFamily.TIMES_ROMAN, 7, Font.BOLD)));
					p.add(checkBox);
					p.add(new Phrase(" :QUADRUPLICATE:  BOOK COPY  ",
							new Font(Font.FontFamily.TIMES_ROMAN, 7, Font.BOLD)));
					foot_bottom_cell31 = new PdfPCell(
							PdfStyle.createUltimateCellWithBorderWithDisableBorder(p, Element.ALIGN_CENTER,
									Element.ALIGN_TOP, 9, 14f, true, 1, 1, true, true, false, false, false));
				} else {
					foot_bottom_cell31 = new PdfPCell(PdfStyle.createUltimateCellWithBorderWithDisableBorder(
							"Copies: 1: ORIGINAL FOR CONSIGNEE;   2. DUPLICATE FOR TRANSPORTER;   3. TRIPLICATE FOR COSIGNOR;   4. QUADRUPLICATE:  BOOK COPY ",
							Element.ALIGN_CENTER, Element.ALIGN_TOP, 9, 14f, true, 1, 1, true, true, false, false,
							false));
				}
			}
			foot_bottom_table3.addCell(foot_bottom_cell31);

			PdfPCell bottom_left_cell = new PdfPCell(foot_left_table);
			PdfPCell bottom_right_cell = new PdfPCell(foot_right_table);
			PdfPCell bottom_2_cell = new PdfPCell(foot_bottom_table2);
			bottom_2_cell.setColspan(2);
			PdfPCell bottom_3_cell = new PdfPCell(foot_bottom_table3);
			bottom_3_cell.setColspan(2);

			maintable.addCell(bottom_left_cell);
			maintable.addCell(bottom_right_cell);
			maintable.addCell(bottom_2_cell);
			maintable.addCell(bottom_3_cell);

			// -----------------------------------------------------------------------------------------------------------------

			if (pageflag) {
// System.out.println("pagecount : " +pagecount);
				cell = new PdfPCell(PdfStyle.createLabelCellWithoutBorderSmallFontNoBold("Page No. " + pagecount));
				cell.setBorder(Rectangle.NO_BORDER);
				cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
				cell.setVerticalAlignment(Element.ALIGN_BOTTOM);
				cell.setColspan(3);
				maintable.addCell(cell);
			}

		} catch (Exception e) {
			System.out.println("Error Occurred :" + e.getMessage());// uncomment asneeded --;
		}
		return maintable;
	}

	@Override
	public String PrePrintedDetailedChallanDeclaration(Integer division, Integer loc, String frm_challan,
			String to_challan, String dispatchType, String prodtype, List<ViewPrePrintedDetailChallan> challans,
			HttpSession session) throws Exception {
		FileOutputStream os = null;
		PrintStream ps = null;
		StringBuffer output = new StringBuffer();
		// String path= ServletActionContext.getRequest().getRealPath("");

		String path = MedicoConstants.PDF_FILE_PATH;
		File file1 = new File(path);
		file1.mkdirs();

		System.out.println("path 11 : " + path);
		long timevar = new Date().getTime();
		String filename = "Declaration" + timevar + ".txt";
		String filename_pdf = "Declaration" + timevar + ".pdf";
		File file = new File(path + "\\files\\" + filename);
		try {

			// printReport.createPDF(path,filename_pdf);

			// vogella calls

			Document document = new Document();
			PdfWriter.getInstance(document, new FileOutputStream(path + "\\files\\" + filename_pdf));

			document.open();

			String old = "";
			String new_var = "";
			/* header */
			System.out.println("Size" + challans.size());
			for (ViewPrePrintedDetailChallan challan : challans) {
				System.out.println("Hiiii");
				new_var = challan.getDsp_id().toString();
				if (!old.equalsIgnoreCase(new_var)) {
					Paragraph preface = new Paragraph();
					addMetaData(document);
					addTitlePage(document, challan, preface, session);
					addContent(document, challan, session, preface);
					addFooterForPdf(document, challan, session, preface);
					// Start a new page
					document.newPage();
				}
				old = new_var;
			}

			document.close();

			file.getParentFile().mkdir();
			file.createNewFile();
			os = new FileOutputStream(file);
			ps = new PrintStream(os);

			ps.println(output);

		} catch (Exception e) {
			System.out.println("Error Occurred :" + e.getMessage());// uncomment asneeded --;
//			System.out.println("Error Occurred :" +e.getMessage());// uncomment asneeded --;
		} finally {
			try {
				if (os != null) {
					os.close();
				}
				if (ps != null) {
					ps.close();
				}
			} catch (IOException e) {
				System.out.println("Error Occurred :" + e.getMessage());// uncomment asneeded --;
//				System.out.println("Error Occurred :" +e.getMessage());// uncomment asneeded --;
			}
		}
		return filename_pdf;
	}

	private static Font catFont = new Font(Font.FontFamily.TIMES_ROMAN, 18, Font.BOLD);
	private static Font redFont = new Font(Font.FontFamily.TIMES_ROMAN, 12, Font.NORMAL, BaseColor.RED);
	private static Font subFont = new Font(Font.FontFamily.TIMES_ROMAN, 16, Font.BOLD);
	private static Font smallBold = new Font(Font.FontFamily.TIMES_ROMAN, 12, Font.BOLD);

	private static Font font = new Font(FontFamily.HELVETICA, 12, Font.BOLD, BaseColor.BLACK);
	private static Font font_title = new Font(FontFamily.HELVETICA, 14, Font.BOLD, BaseColor.BLACK);
	private static Font medFont = new Font(Font.FontFamily.HELVETICA, 9, Font.NORMAL, BaseColor.BLACK);

	// iText allows to add metadata to the PDF which can be viewed in your Adobe
	// Reader
	// under File -> Properties
	private static void addMetaData(Document document) {
		document.addTitle("Declaration Form");
		document.addSubject("Using iText");
		document.addKeywords("Java, PDF, iText");
		document.addAuthor("Excel");
		document.addCreator("Excel");
	}

	private static void addTitlePage(Document document, ViewPrePrintedDetailChallan challan, Paragraph preface,
			HttpSession session) throws DocumentException {

		// We add one empty line
		// addEmptyLine(preface, 1,document);
		// Lets write a big header
		String company_name = (String) session.getAttribute("company_name");

		preface = new Paragraph("Declaration", font_title);
		preface.setAlignment(Element.ALIGN_CENTER);

		document.add(preface);
		addEmptyLine(preface, 1, document);
		Chunk glue = new Chunk(new VerticalPositionMark());
		preface = new Paragraph(company_name, font);
		preface.add(new Chunk(glue));
		Chunk c1 = new Chunk("Date : " + challan.getDate(), medFont);
		preface.add(c1);

		document.add(preface);
		// addEmptyLine(preface, 1,document);

		preface = new Paragraph(challan.getLoc_add1(), medFont);
		preface.setAlignment(Element.ALIGN_LEFT);
		document.add(preface);
		preface = new Paragraph(challan.getLoc_add2(), medFont);
		preface.setAlignment(Element.ALIGN_LEFT);
		document.add(preface);
		preface = new Paragraph(challan.getLoc_add3(), medFont);
		preface.setAlignment(Element.ALIGN_LEFT);
		document.add(preface);
		preface = new Paragraph(challan.getLoc_add4(), medFont);
		preface.setAlignment(Element.ALIGN_LEFT);
		document.add(preface);
		preface = new Paragraph("Drug Licence No : " + challan.getLic1(), medFont);
		preface.setAlignment(Element.ALIGN_LEFT);
		document.add(preface);
		addEmptyLine(preface, 1, document);

		// Will create: Report generated by: _name, _date
		preface = new Paragraph("TO WHOMSOEVER IT MAY CONCERN ", medFont);
		preface.setAlignment(Element.ALIGN_CENTER);
		document.add(preface);
		addEmptyLine(preface, 1, document);
		StringBuilder sb = new StringBuilder();
		if (challan.getDsp_transporter().length() == 0) {
			sb.append("               contains :");
		} else {

			sb.append(challan.getDsp_transporter() + " contains :");
		}
		preface = new Paragraph("We hereby certify that " + challan.getCases().setScale(0).toString()
				+ " case/s dispatched by us through " + sb.toString(), medFont);

		preface.setAlignment(Element.ALIGN_LEFT);
		document.add(preface);
//		
//		preface=new Paragraph(" contains :"  , 
//				medFont);
//	 			
//	preface.setAlignment(Element.ALIGN_LEFT);
//	document.add(preface);
		addEmptyLine(preface, 1, document);

		preface = new Paragraph("1. Free samples of harmless medicines intended for free distribution to doctors.",
				medFont);
		preface.setAlignment(Element.ALIGN_LEFT);
		document.add(preface);
		preface = new Paragraph("2. Publicity material(Printed Material) for free distribution to doctors.", medFont);
		preface.setAlignment(Element.ALIGN_LEFT);
		document.add(preface);
		addEmptyLine(preface, 1, document);

	}

	private static void addContent(Document document, ViewPrePrintedDetailChallan challan, HttpSession session,
			Paragraph preface) throws DocumentException {

		// specify column widths
		float[] columnWidths = { 1f, 2f };
		// create PDF table with the given widths
		PdfPTable table = new PdfPTable(columnWidths);
		// set table width a percentage of the page width
		table.setWidthPercentage(100);
		table.setSpacingBefore(10f);

		table.setSpacingAfter(10f);

		// just some random data to fill

		insertCell(table, "Customer Name", Element.ALIGN_LEFT, 1, medFont);
		StringBuilder sb1 = new StringBuilder();
		sb1.append(challan.getFstaff_id() + " ");
		sb1.append(challan.getStaff_name() + "(");
		sb1.append(challan.getDivision() + " ");
		sb1.append(challan.getDiv_name() + ") ");
		// sb1.append(challan.getDiv_name()+")");
		insertCell(table, sb1.toString().length() > 50 ? sb1.toString().substring(0, 50) : sb1.toString(),
				Element.ALIGN_LEFT, 1, medFont);

		insertCell(table, "Ship To", Element.ALIGN_LEFT, 1, medFont);
		StringBuilder result1 = new StringBuilder();
		result1.append(challan.getStaff_name());
		result1.append(System.lineSeparator());
		result1.append(challan.getStaff_addr1());
		result1.append(System.lineSeparator());
		result1.append(challan.getStaff_addr2());
		result1.append(System.lineSeparator());
		result1.append(challan.getStaff_addr3());
		result1.append(System.lineSeparator());
		result1.append(challan.getStaff_addr4());
		result1.append(System.lineSeparator());

		insertCell(table, result1.toString(), Element.ALIGN_LEFT, 1, medFont);

		insertCell(table, "Headquater ", Element.ALIGN_LEFT, 1, medFont);

		insertCell(table, challan.getHq_name(), Element.ALIGN_LEFT, 1, medFont);

		insertCell(table, "State Name ", Element.ALIGN_LEFT, 1, medFont);

		insertCell(table, challan.getState(), Element.ALIGN_LEFT, 1, medFont);

		insertCell(table, "Mobile Number ", Element.ALIGN_LEFT, 1, medFont);

		insertCell(table, challan.getDspstaff_mobile(), Element.ALIGN_LEFT, 1, medFont);

		insertCell(table, "The Material is ", Element.ALIGN_LEFT, 1, medFont);

		insertCell(table, "NOT FOR SALE AND HAS NO COMMERCIAL VALUE", Element.ALIGN_LEFT, 1, medFont);

		insertCell(table, "Invoice # ", Element.ALIGN_LEFT, 1, medFont);

		insertCell(table, challan.getDoc_no(), Element.ALIGN_LEFT, 1, medFont);

		insertCell(table, "L.R.No. :", Element.ALIGN_LEFT, 1, medFont);

		insertCell(table, challan.getShip_doc_no(), Element.ALIGN_LEFT, 1, medFont);

		insertCell(table, "No of Boxes ", Element.ALIGN_LEFT, 1, medFont);

		insertCell(table, challan.getCases().setScale(0).toString(), Element.ALIGN_LEFT, 1, medFont);

		insertCell(table, "Weight ", Element.ALIGN_LEFT, 1, medFont);
		DecimalFormat format = new DecimalFormat("0.#");
		insertCell(table, format.format(Double.parseDouble(challan.getWeigh())).toString(), Element.ALIGN_LEFT, 1,
				medFont);

		// insertCell(table, "For " , Element.ALIGN_LEFT, 1, medFont);

		// insertCell(table, company_name, Element.ALIGN_LEFT, 1, medFont);

		// create a new cell with the specified Text and Font
		// PdfPCell cell = new PdfPCell(new Phrase("For " +company_name, medFont));
		// set the cell alignment
		// cell.setHorizontalAlignment(Element.ALIGN_LEFT);
		// set the cell column span in case you want to merge two or more cells
		// cell.setBorder(0);

		// cell.setPadding(5);
		// table.addCell(cell);

		preface.add(table);
		document.add(preface);

	}

	private static void addFooterForPdf(Document document, ViewPrePrintedDetailChallan challan, HttpSession session,
			Paragraph preface) throws DocumentException {
		// We add one empty line
		addEmptyLine(preface, 1, document);
		// Lets write a big Footer

		String companyname = ((Company) session.getServletContext().getAttribute(COMPANY_DATA)).getCompany_name();
		String address1 = ((Company) session.getServletContext().getAttribute(COMPANY_DATA)).getAddress1();
		String address2 = ((Company) session.getServletContext().getAttribute(COMPANY_DATA)).getAddress2();
		String address3 = ((Company) session.getServletContext().getAttribute(COMPANY_DATA)).getAddress3();
		String address4 = ((Company) session.getServletContext().getAttribute(COMPANY_DATA)).getAddress4();

//		String company_name = (String) session.getAttribute("company_name");

		preface = new Paragraph("For " + companyname, font);
		preface.setAlignment(Element.ALIGN_LEFT);

		document.add(preface);
		addEmptyLine(preface, 4, document);

		preface = new Paragraph("Authorized Signatory", font);
		preface.setAlignment(Element.ALIGN_LEFT);

		document.add(preface);
		// addEmptyLine(preface, 1,document);
		preface = new Paragraph("Registered Office : ", font);
		preface.setAlignment(Element.ALIGN_LEFT);

		document.add(preface);

		preface = new Paragraph(address1, medFont);
		preface.setAlignment(Element.ALIGN_LEFT);
		document.add(preface);

		preface = new Paragraph(address2, medFont);
		preface.setAlignment(Element.ALIGN_LEFT);
		document.add(preface);

		preface = new Paragraph(address3, medFont);
		preface.setAlignment(Element.ALIGN_LEFT);
		document.add(preface);

		preface = new Paragraph(address4, medFont);
		preface.setAlignment(Element.ALIGN_LEFT);
		document.add(preface);

	}

	static void createTable(Section subCatPart) throws BadElementException {
		PdfPTable table = new PdfPTable(3);

		PdfPCell c1 = new PdfPCell(new Phrase("Table Header 1"));
		c1.setHorizontalAlignment(Element.ALIGN_CENTER);
		table.addCell(c1);

		c1 = new PdfPCell(new Phrase("Table Header 2"));
		c1.setHorizontalAlignment(Element.ALIGN_CENTER);
		table.addCell(c1);

		c1 = new PdfPCell(new Phrase("Table Header 3"));
		c1.setHorizontalAlignment(Element.ALIGN_CENTER);
		table.addCell(c1);

		table.setHeaderRows(1);

		table.addCell("1.0");
		table.addCell("1.1");
		table.addCell("1.2");
		table.addCell("2.1");
		table.addCell("2.2");
		table.addCell("2.3");

		subCatPart.add(table);

	}

	static void createList(Section subCatPart) {
		com.itextpdf.text.List list = new com.itextpdf.text.List(true, false, 10);
		list.add(new ListItem("First point"));
		list.add(new ListItem("Second point"));
		list.add(new ListItem("Third point"));
		subCatPart.add(list);
	}

	static void addEmptyLine(Paragraph preface, int number, Document document) {
		try {
			for (int i = 0; i < number; i++) {
				preface = new Paragraph(" ", font);

				document.add(preface);

			}
		} catch (DocumentException e) {
			System.out.println("Error Occurred :" + e.getMessage());// uncomment asneeded --;
		}
	}

	static void insertCell(PdfPTable table, String text, int align, int colspan, Font font) {

		// create a new cell with the specified Text and Font
		PdfPCell cell = new PdfPCell(new Phrase(text.trim(), font));
		// set the cell alignment
		cell.setHorizontalAlignment(align);
		// set the cell column span in case you want to merge two or more cells
		cell.setColspan(colspan);
		cell.setPadding(5);
		// in case there is no text and you wan to create an empty row
		if (text.trim().equalsIgnoreCase("")) {
			cell.setMinimumHeight(10f);
		}
		// add the call to the table
		table.addCell(cell);

	}

	@Override
	public String getPreprintedDetailedChallanPrintPG(Integer division, Integer loc, String frm_challan,
			String to_challan, String dispatchType, String prodtype, String printtype,
			List<PrePrintedDetailChallan_withgstPG> challans, String show_amount, String footer_signature_ind,
			String companyCode, String companyName, HttpSession session, HttpServletRequest request) throws Exception {

		int count = 1;
		float[] bodyTableWidth;
		if (companyCode.equals("P&G")) {
			bodyTableWidth = new float[] { 4.5f, 1f, 1.5f, 1f, 1f, 1f, 1f };
		} else if (companyCode.equals("PAL")) {
			bodyTableWidth = new float[] { 0.5f, 1f, 4f, 1f, 1f, 1f, 1f, 1f, 1f, 1f, 1f };
		} else {
			if (show_amount.equals("N")) {
				bodyTableWidth = new float[] { 5f, 1f, 1.5f, 1f, 1.5f };
			} else {
				bodyTableWidth = new float[] { 4.5f, 1f, 1.5f, 1f, 1f, 1f };
			}
		}
		System.out.println("In get pdf ani");
		String path = MedicoConstants.PDF_FILE_PATH;
		File file1 = new File(path);
		file1.mkdirs();
		comp_cd = companyCode;

		company_name = companyName;
		setPath(path);

		System.out.println("PDF Generation Started********************************");
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
		Rectangle rect = new Rectangle(20, 28, 580, 820);
		writer.setBoxSize("header", rect);
		rect = new Rectangle(20, 2, 580, 242);
		writer.setBoxSize("footer", rect);
		int pagecount = 1;
		FooterWithoutPageNo event = new FooterWithoutPageNo();
		writer.setPageEvent(event);

		String uname = this.tokenProvider.getUsernameFromRequest(request);
		String empId = this.userMasterService.getEmpIdByUsername(uname);
		writer = usermasterservice.generatePDFlock(empId, writer);
		document.open();
		document.setMarginMirroringTopBottom(false);
		boolean isnew = false;
		// boolean for_new_page = false;
		ColumnText column = new ColumnText(writer.getDirectContent());
		try {
			BigDecimal total = new BigDecimal(0);

			Set<String> challanset = new LinkedHashSet<String>();
			for (PrePrintedDetailChallan_withgstPG challan : challans) {
				challanset.add(challan.getDsp_id().toString());
			}
			PdfPTable bodytable = null;
			boolean footer_ind = false;
			for (String num : challanset) {
				List<PrePrintedDetailChallan_withgstPG> list_new_page = new ArrayList<PrePrintedDetailChallan_withgstPG>();
				isnew = true;
				// for_new_page=true;
				PdfPTable maintable1 = new PdfPTable(1);
				maintable1.setWidthPercentage(100);
				PdfPCell cell = null;
				pageflag = false;
				pagecount = 1;

				BigDecimal goods_total = BigDecimal.ZERO;
				BigDecimal final_total = BigDecimal.ZERO;
				BigDecimal header_Igst = BigDecimal.ZERO;
				BigDecimal header_Cgst = BigDecimal.ZERO;
				BigDecimal header_Sgst = BigDecimal.ZERO;
				BigDecimal round_off = BigDecimal.ZERO;

				PdfPTable hcell = null;
				int rows = 0;
				if (companyCode.equals("P&G")) {
					bodytable = new PdfPTable(7);
				} else if (companyCode.equals("PAL")) {
					bodytable = new PdfPTable(11);
				} else {
					if (show_amount.equals("N")) {
						bodytable = new PdfPTable(5);
					} else {
						bodytable = new PdfPTable(6);
					}
				}
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
				String gst_doc_type = "";
				String dsp_lorry_no = "";

				BigDecimal igstRate = BigDecimal.ZERO;
				BigDecimal igstAmounts = BigDecimal.ZERO;
				// int count_var=0;
				String newChallanNo;
				for (PrePrintedDetailChallan_withgstPG challan : challans) {

					String oldChallan = challan.getDsp_challan_no();
					newChallanNo = "";
					if (!oldChallan.equals(newChallanNo) && newChallanNo.equals(" ")) {
						count = 0;

					}
					// System.out.println("Alloc Remark
					// "+challan.getAlloc_remark());
					docType = challan.getGst_doc_type();

					if (num.equalsIgnoreCase(challan.getDsp_id().toString())) {
						if (rows >= limit) {
							// count_var++;

							if (footer_signature_ind.equalsIgnoreCase("Y")) {
								footer_ind = false;// suppress footer signature
							} else {
								footer_ind = true;// show footer signature
							}
							System.out.println("getDoc_no  " + challan.getDsp_challan_no());
							pageflag = true;

							bodytable = newPagePG(column, bodytable, event, printLabel, false, rows, pageflag,
									pagecount, total, division, loc, frm_challan, to_challan, dispatchType, prodtype,
									path, challans, show_amount, footer_ind, challan.getDsp_transporter(),
									challan.getShip_doc_no(), challan.getShip_doc_date(), challan.getDsp_cases(),
									challan.getWeigh().toString(),
									challan.getAlloc_remark().length() > 600
											? challan.getAlloc_remark().substring(0, 600)
											: challan.getAlloc_remark(),
									goods_total, final_total, header_Igst, header_Cgst, header_Sgst, round_off,
									gst_doc_type, dsp_lorry_no, bodyTableWidth, companyCode, igstAmounts);

							document.newPage();
							pagecount++;
							rows = 0;

						}
						if (isnew) {
							footerPG = challan;
							maintable1.addCell(PdfStyle.createLabelCellWithoutBorderSmallFontNoBold(""));
							hcell = createHeaderPG(challan, pagecount, division, loc, frm_challan, to_challan,
									dispatchType, prodtype, path, show_amount, comp_cd, companyName, session,
									bodyTableWidth, companyCode);
							cell = new PdfPCell(hcell);
							cell.setBorder(0);
							cell.setHorizontalAlignment(Element.ALIGN_CENTER);
							maintable1.addCell(cell);
							event.setHeader(maintable1);
							isnew = false;
							list_new_page.add(challan);
						}

						if (rows == 0) { // changes made for unichem testing
							// need to recheck 2 aug 2017
							if (show_amount.equals("Y")) {
								bodytable.addCell(PdfStyle.createValueCellWithHeight((""), false, true, 18f));
								bodytable.addCell(PdfStyle.createValueCellWithHeight((""), false, true, 18f));
								bodytable.addCell(PdfStyle.createValueCellWithHeight((""), false, true, 18f));
								bodytable.addCell(PdfStyle.createValueCellWithHeight((""), true, true, 18f));
								bodytable.addCell(PdfStyle.createValueCellWithHeight((""), true, true, 18f));
								bodytable.addCell(PdfStyle.createValueCellWithHeight((""), true, true, 18f));
							} else {
								bodytable.addCell(PdfStyle.createValueCellWithHeight((""), false, true, 18f));
								bodytable.addCell(PdfStyle.createValueCellWithHeight((""), false, true, 18f));
								bodytable.addCell(PdfStyle.createValueCellWithHeight((""), false, true, 18f));
								bodytable.addCell(PdfStyle.createValueCellWithHeight((""), true, true, 18f));
								bodytable.addCell(PdfStyle.createValueCellWithHeight((""), true, true, 18f));
							}
							if (companyCode.equals("P&G")) {
								bodytable.addCell(PdfStyle.createValueCellWithHeight((""), true, true, 18f));
							}
							if (companyCode.equals("PAL")) {
								bodytable.addCell(PdfStyle.createValueCellWithHeight((""), false, true, 18f));
								bodytable.addCell(PdfStyle.createValueCellWithHeight((""), false, true, 18f));
								bodytable.addCell(PdfStyle.createValueCellWithHeight((""), false, true, 18f));
								bodytable.addCell(PdfStyle.createValueCellWithHeight((""), true, true, 18f));
								bodytable.addCell(PdfStyle.createValueCellWithHeight((""), true, true, 18f));
							}

						} /// if for palson

						if (companyCode.equalsIgnoreCase("PAL")) {

							bodytable.addCell(PdfStyle.createUltimateCellWithBorderWithDisableBorder((count + ""),
									Element.ALIGN_CENTER, Element.ALIGN_TOP, 8, 18f, true, 1, 1, false, false, false,
									true, false));

							bodytable.addCell(PdfStyle.createUltimateCellWithBorderWithDisableBorder(
									(challan.getSmp_prod_cd()), Element.ALIGN_CENTER, Element.ALIGN_TOP, 8, 18f, true,
									1, 1, false, false, false, true, false));

							bodytable.addCell(PdfStyle.createUltimateCellWithBorderWithDisableBorder(
									(challan.getProd_name_pack()), Element.ALIGN_LEFT, Element.ALIGN_TOP, 8, 18f, true,
									1, 1, false, false, false, true, false));

							bodytable.addCell(PdfStyle.createUltimateCellWithBorderWithDisableBorder(
									(challan.getPack_disp_nm()), Element.ALIGN_RIGHT, Element.ALIGN_TOP, 8, 18f, true,
									1, 1, false, false, false, true, false));

							bodytable.addCell(PdfStyle.createUltimateCellWithBorderWithDisableBorder(
									(challan.getUom_disp_nm()), Element.ALIGN_LEFT, Element.ALIGN_TOP, 8, 18f, true, 1,
									1, false, false, false, true, false));

							bodytable.addCell(PdfStyle.createUltimateCellWithBorderWithDisableBorder(
									(challan.getHsn_code()), Element.ALIGN_LEFT, Element.ALIGN_TOP, 8, 18f, true, 1, 1,
									false, false, false, true, false));

							bodytable.addCell(PdfStyle.createUltimateCellWithBorderWithDisableBorder(
									(challan.getBatch_no()), Element.ALIGN_LEFT, Element.ALIGN_TOP, 8, 18f, true, 1, 1,
									false, false, false, true, false));

							bodytable.addCell(PdfStyle.createUltimateCellWithBorderWithDisableBorder(
									(challan.getBatch_expiry_dt()), Element.ALIGN_CENTER, Element.ALIGN_TOP, 8, 18f,
									true, 1, 1, false, false, false, true, false));

							bodytable
									.addCell(
											PdfStyle.createUltimateCellWithBorderWithDisableBorder(
													(challan.getDspdtl_rate() == null
															? BigDecimal.ZERO.setScale(2, RoundingMode.HALF_UP)
															: challan.getDspdtl_rate().setScale(2,
																	RoundingMode.HALF_UP))
															.toString(),
													Element.ALIGN_RIGHT, Element.ALIGN_TOP, 8, 18f, true, 1, 1, false,
													false, false, true, false));

							bodytable.addCell(PdfStyle.createUltimateCellWithBorderWithDisableBorder(
									(challan.getDspdtl_disp_qty().toString()), Element.ALIGN_RIGHT, Element.ALIGN_TOP,
									8, 18f, true, 1, 1, false, false, false, true, false));

							bodytable.addCell(PdfStyle.createUltimateCellWithBorderWithDisableBorder(
									("0.00").toString(), Element.ALIGN_RIGHT, Element.ALIGN_TOP, 8, 18f, true, 1, 1,
									false, false, false, true, false));
							count++;

						} else {
							bodytable.addCell(PdfStyle.createUltimateCellWithBorderWithDisableBorder(
									(challan.getProd_name_pack()), Element.ALIGN_LEFT, Element.ALIGN_TOP, 8, 18f, false,
									1, 1, false, false, false, true, false));

							bodytable.addCell(PdfStyle.createUltimateCellWithBorderWithDisableBorder(
									(challan.getHsn_code()), Element.ALIGN_LEFT, Element.ALIGN_TOP, 8, 18f, false, 1, 1,
									false, false, false, true, false));

							bodytable.addCell(PdfStyle.createUltimateCellWithBorderWithDisableBorder(
									(challan.getBatch_no()), Element.ALIGN_LEFT, Element.ALIGN_TOP, 8, 18f, false, 1, 1,
									false, false, false, true, false));

							bodytable.addCell(PdfStyle.createUltimateCellWithBorderWithDisableBorder(
									(challan.getBatch_expiry_dt()), Element.ALIGN_CENTER, Element.ALIGN_TOP, 8, 18f,
									false, 1, 1, false, false, false, true, false));

							bodytable.addCell(PdfStyle.createUltimateCellWithBorderWithDisableBorder(
									(challan.getDspdtl_disp_qty().toString()), Element.ALIGN_CENTER, Element.ALIGN_TOP,
									8, 18f, false, 1, 1, false, false, false, true, false));

							if (companyCode.equals("P&G")) {
								bodytable
										.addCell(
												PdfStyle.createUltimateCellWithBorderWithDisableBorder(
														(challan.getDspdtl_rate() == null
																? BigDecimal.ZERO.setScale(2, RoundingMode.HALF_UP)
																: challan.getDspdtl_rate().setScale(2,
																		RoundingMode.HALF_UP))
																.toString(),
														Element.ALIGN_RIGHT, Element.ALIGN_TOP, 8, 18f, false, 1, 1,
														false, false, false, true, false));
							} else if (companyCode.equals("PAL")) {
								bodytable.addCell(PdfStyle.createUltimateCellWithBorderWithDisableBorder(
										("0.0").toString(), Element.ALIGN_RIGHT, Element.ALIGN_TOP, 8, 18f, false, 1, 1,
										false, false, false, true, false));
							} else {
								if (show_amount.equals("Y")) {
									bodytable
											.addCell(
													PdfStyle.createUltimateCellWithBorderWithDisableBorder(
															(challan.getDisp_value() == null
																	? BigDecimal.ZERO.setScale(2, RoundingMode.HALF_UP)
																	: challan.getDisp_value().setScale(2,
																			RoundingMode.HALF_UP))
																	.toString(),
															Element.ALIGN_RIGHT, Element.ALIGN_TOP, 8, 18f, false, 1, 1,
															false, false, false, true, false));
								}
							}
							if (companyCode.equals("P&G")) {
								bodytable.addCell(PdfStyle.createUltimateCellWithBorderWithDisableBorder(
										("0.0").toString(), Element.ALIGN_RIGHT, Element.ALIGN_TOP, 8, 18f, false, 1, 1,
										false, false, false, true, true));
							}
						}

						if (docType.contains("008")) {

							bodytable.addCell(PdfStyle.createUltimateCellWithBorderWithDisableBorder(("IGST% "),
									Element.ALIGN_RIGHT, Element.ALIGN_TOP, 8, 18f, true, 1, 1, false, false, false,
									true, false));

							if (companyCode.equalsIgnoreCase("P&G")) {
								bodytable.addCell(PdfStyle.createUltimateCellWithBorderWithDisableBorder("0.00",
										Element.ALIGN_RIGHT, Element.ALIGN_TOP, 8, 18f, true, 1, 1, false, false, false,
										true, false));
							} else {
								bodytable.addCell(PdfStyle.createUltimateCellWithBorderWithDisableBorder(
										(challan.getIgst_rate()).toString(), Element.ALIGN_RIGHT, Element.ALIGN_TOP, 8,
										18f, true, 1, 1, false, false, false, true, false));
							}

							bodytable.addCell(PdfStyle.createUltimateCellWithBorderWithDisableBorder(("IGST"),
									Element.ALIGN_RIGHT, Element.ALIGN_TOP, 8, 18f, true, 1, 1, false, false, false,
									true, false));

							if (companyCode.equals("P&G")) {
								bodytable.addCell(PdfStyle.createUltimateCellWithBorderWithDisableBorder("0.00",
										Element.ALIGN_RIGHT, Element.ALIGN_TOP, 8, 18f, true, 1, 1, false, false, false,
										true, false));
							} else {
								bodytable
										.addCell(
												PdfStyle.createUltimateCellWithBorderWithDisableBorder(
														(challan.getIgst_bill_amt() == null
																? BigDecimal.ZERO.setScale(2, RoundingMode.HALF_UP)
																: challan.getIgst_bill_amt().setScale(2,
																		RoundingMode.HALF_UP))
																.toPlainString(),
														Element.ALIGN_RIGHT, Element.ALIGN_TOP, 8, 18f, true, 1, 1,
														false, false, false, true, false));

							}

							bodytable.addCell(
									PdfStyle.createUltimateCellWithBorderWithDisableBorder((""), Element.ALIGN_LEFT,
											Element.ALIGN_TOP, 8, 18f, true, 1, 1, false, false, false, true, false));
							if (show_amount.equals("Y")) {
								bodytable.addCell(PdfStyle.createUltimateCellWithBorderWithDisableBorder((""),
										Element.ALIGN_LEFT, Element.ALIGN_TOP, 8, 18f, true, 1, 1, false, false, false,
										true, false));
							}
							if (companyCode.equals("P&G")) {

								bodytable.addCell(PdfStyle.createUltimateCellWithBorderWithDisableBorder((""),
										Element.ALIGN_LEFT, Element.ALIGN_TOP, 8, 18f, true, 1, 1, false, false, false,
										true, false));
							}

						} else {
							if (companyCode.equals("P&G")) {

								bodytable.addCell(PdfStyle.createUltimateCellWithBorderWithDisableBorder(
										("CGST% :" + "0.00" + "                                       CGST :" + "0.00"),
										Element.ALIGN_LEFT, Element.ALIGN_TOP, 8, 18f, true, 1, 1, false, false, false,
										true, false));
							} else {

								bodytable
										.addCell(
												PdfStyle.createUltimateCellWithBorderWithDisableBorder(
														("CGST% :" + challan.getCgst_rate()
																+ "                                       CGST :"
																+ challan.getCgst_bill_amt().setScale(2,
																		RoundingMode.HALF_UP)),
														Element.ALIGN_LEFT, Element.ALIGN_TOP, 8, 18f, true, 1, 1,
														false, false, false, true, false));
							}
							System.out.println("challan.getCgst_rate()" + challan.getCgst_rate());

							bodytable.addCell(PdfStyle.createUltimateCellWithBorderWithDisableBorder(("SGST%"),
									Element.ALIGN_RIGHT, Element.ALIGN_TOP, 8, 18f, true, 1, 1, false, false, false,
									true, false));
							if (companyCode.equals("P&G")) {
								bodytable.addCell(PdfStyle.createUltimateCellWithBorderWithDisableBorder("0.00",
										Element.ALIGN_RIGHT, Element.ALIGN_TOP, 8, 18f, true, 1, 1, false, false, false,
										true, false));
							} else {
								bodytable.addCell(PdfStyle.createUltimateCellWithBorderWithDisableBorder(
										(challan.getSgst_rate().toString()), Element.ALIGN_RIGHT, Element.ALIGN_TOP, 8,
										18f, true, 1, 1, false, false, false, true, false));
							}

							bodytable.addCell(PdfStyle.createUltimateCellWithBorderWithDisableBorder(
									("SGST").toString(), Element.ALIGN_RIGHT, Element.ALIGN_TOP, 8, 18f, true, 1, 1,
									false, false, false, true, false));

							if (companyCode.equals("P&G")) {
								bodytable.addCell(PdfStyle.createUltimateCellWithBorderWithDisableBorder(("0.00"),
										Element.ALIGN_RIGHT, Element.ALIGN_TOP, 8, 18f, true, 1, 1, false, false, false,
										true, false));
							} else {
								bodytable.addCell(PdfStyle.createUltimateCellWithBorderWithDisableBorder(
										(challan.getSgst_bill_amt().toString()), Element.ALIGN_RIGHT, Element.ALIGN_TOP,
										8, 18f, true, 1, 1, false, false, false, true, false));
							}

							bodytable.addCell(
									PdfStyle.createUltimateCellWithBorderWithDisableBorder((""), Element.ALIGN_RIGHT,
											Element.ALIGN_TOP, 8, 18f, true, 1, 1, false, false, false, true, false));

							if (companyCode.equals("P&G")) {
								bodytable.addCell(PdfStyle.createUltimateCellWithBorderWithDisableBorder((""),
										Element.ALIGN_RIGHT, Element.ALIGN_TOP, 8, 18f, true, 1, 1, false, false, false,
										true, false));

							}
							if (companyCode.equals("PAL")) {
								bodytable.addCell(PdfStyle.createUltimateCellWithBorderWithDisableBorder((""),
										Element.ALIGN_RIGHT, Element.ALIGN_TOP, 8, 18f, true, 1, 1, false, false, false,
										true, false));

								bodytable.addCell(PdfStyle.createUltimateCellWithBorderWithDisableBorder((""),
										Element.ALIGN_RIGHT, Element.ALIGN_TOP, 8, 18f, true, 1, 1, false, false, false,
										true, false));
								bodytable.addCell(PdfStyle.createUltimateCellWithBorderWithDisableBorder((""),
										Element.ALIGN_RIGHT, Element.ALIGN_TOP, 8, 18f, true, 1, 1, false, false, false,
										true, false));
								bodytable.addCell(PdfStyle.createUltimateCellWithBorderWithDisableBorder((""),
										Element.ALIGN_RIGHT, Element.ALIGN_TOP, 8, 18f, true, 1, 1, false, false, false,
										true, false));

								bodytable.addCell(PdfStyle.createUltimateCellWithBorderWithDisableBorder((""),
										Element.ALIGN_RIGHT, Element.ALIGN_TOP, 8, 18f, true, 1, 1, false, false, false,
										true, false));
							}
						}

						goods_total = goods_total.add(challan.getDisp_value());
						final_total = goods_total.add(challan.getHigst_bill_amt().add(challan.getHcgst_bill_amt()
								.add(challan.getHsgst_bill_amt().add(challan.getRoundoff()))));
						header_Igst = challan.getHigst_bill_amt();
						header_Sgst = challan.getHsgst_bill_amt();
						header_Cgst = challan.getHcgst_bill_amt();
						round_off = challan.getRoundoff();
						gst_doc_type = challan.getGst_description();

						rows++;
						dtp = "";
						ship_doc_no = "";
						date = "";
						cases = "";
						weigh = "";
						dsp_challan_msg = "";
						dsp_lorry_no = "";

						dtp = challan.getDsp_transporter();
						ship_doc_no = challan.getShip_doc_no();
						date = challan.getShip_doc_date();
						cases = challan.getDsp_cases().toString();
						weigh = challan.getWeigh().toString();
						igstRate = challan.getIgst_bill_amt();
						igstAmounts = igstAmounts.add(igstRate);
						System.out.println("igstRate" + igstRate);
						System.out.println("docTYpes  " + challan.getGst_doc_type());
						System.out.println("igstAmounts" + igstAmounts);

						dsp_challan_msg = challan.getAlloc_remark().length() > 600
								? challan.getAlloc_remark().substring(0, 600)
								: challan.getAlloc_remark();
						dsp_lorry_no = challan.getDsp_lorry_no();
					}
				}

				footer_ind = true;

				newPagePG(column, bodytable, event, printLabel, true, rows, pageflag, pagecount, total, division, loc,
						frm_challan, to_challan, dispatchType, prodtype, path, list_new_page, show_amount, footer_ind,
						dtp, ship_doc_no, date, cases, weigh, dsp_challan_msg, goods_total, final_total, header_Igst,
						header_Cgst, header_Sgst, round_off, gst_doc_type, dsp_lorry_no, bodyTableWidth, companyCode,
						igstAmounts);
				document.newPage();
			}

		} catch (Exception e) {
			System.out.println("Error Occurred :" + e.getMessage());// uncomment asneeded --;
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
				System.out.println("Error Occurred :" + e.getMessage());// uncomment asneeded --;
			}
		}
		return filename;
	}

	public PdfPTable newPagePG(ColumnText column, PdfPTable bodytable, FooterWithoutPageNo event,
			HashMap<String, String> printLabel, boolean isFooterData, int rows, boolean pageflag, int pagecount,
			BigDecimal total, int division, Integer loc, String frm_challan, String to_challan, String dispatchType,
			String prodtype, String path, List<PrePrintedDetailChallan_withgstPG> challan, String show_amount,
			boolean footer_ind, String dsp_transporter, String ship_doc_no, String date, String cases, String weigh,
			String dsp_challan_msg, BigDecimal goods_total, BigDecimal final_total, BigDecimal header_Igst,
			BigDecimal header_Cgst, BigDecimal header_Sgst, BigDecimal round_off, String gst_doc_type,
			String dsp_lorry_no, float[] bodyTableWidth, String companyCode, BigDecimal igstAmounts)
			throws DocumentException {

		PdfPCell cell = null;
		try {
			// System.out.println("limit:"+limit);
			// System.out.println("rows::"+rows);

			if (limit - (rows) > 0) {
// add extra empty rows in body table
				cell = new PdfPCell();

				if (companyCode.equals("P&G")) {
					PdfPTable temp = new PdfPTable(7);
					temp.setWidths(bodyTableWidth);
					temp.addCell(PdfStyle.createValueCellWithHeight((""), false, true, 18f));
					temp.addCell(PdfStyle.createValueCellWithHeight((""), false, true, 18f));
					temp.addCell(PdfStyle.createValueCellWithHeight((""), false, true, 18f));
					temp.addCell(PdfStyle.createValueCellWithHeight((""), true, true, 18f));
					temp.addCell(PdfStyle.createValueCellWithHeight((""), true, true, 18f));
					temp.addCell(PdfStyle.createValueCellWithHeight((""), true, true, 18f));
					temp.addCell(PdfStyle.createValueCellWithHeight((""), true, true, 18f));

					temp.addCell(PdfStyle.createValueCellWithHeight((" "), false, true, 18f));
					temp.addCell(PdfStyle.createValueCellWithHeight((""), false, true, 18f));
					temp.addCell(PdfStyle.createValueCellWithHeight((""), false, true, 18f));
					temp.addCell(PdfStyle.createValueCellWithHeight((""), true, true, 18f));
					temp.addCell(PdfStyle.createValueCellWithHeight((""), true, true, 18f));
					temp.addCell(PdfStyle.createValueCellWithHeight((""), true, true, 18f));
					temp.addCell(PdfStyle.createValueCellWithHeight((""), true, true, 18f));

					cell = new PdfPCell(temp);
					cell.setBorder(0);
					cell.setColspan(7);
				} else if (companyCode.equals("PAL")) {
					PdfPTable temp = new PdfPTable(11);
					temp.setWidths(bodyTableWidth);
					temp.addCell(PdfStyle.createValueCellWithHeight((""), false, true, 18f));
					temp.addCell(PdfStyle.createValueCellWithHeight((""), false, true, 18f));
					temp.addCell(PdfStyle.createValueCellWithHeight((""), false, true, 18f));
					temp.addCell(PdfStyle.createValueCellWithHeight((""), true, true, 18f));
					temp.addCell(PdfStyle.createValueCellWithHeight((""), true, true, 18f));
					temp.addCell(PdfStyle.createValueCellWithHeight((""), true, true, 18f));
					temp.addCell(PdfStyle.createValueCellWithHeight((""), true, true, 18f));
					temp.addCell(PdfStyle.createValueCellWithHeight((""), true, true, 18f));
					temp.addCell(PdfStyle.createValueCellWithHeight((""), true, true, 18f));
					temp.addCell(PdfStyle.createValueCellWithHeight((""), true, true, 18f));
					temp.addCell(PdfStyle.createValueCellWithHeight((""), true, true, 18f));

					temp.addCell(PdfStyle.createValueCellWithHeight((" "), false, true, 18f));
					temp.addCell(PdfStyle.createValueCellWithHeight((""), false, true, 18f));
					temp.addCell(PdfStyle.createValueCellWithHeight((""), false, true, 18f));
					temp.addCell(PdfStyle.createValueCellWithHeight((""), true, true, 18f));
					temp.addCell(PdfStyle.createValueCellWithHeight((""), true, true, 18f));
					temp.addCell(PdfStyle.createValueCellWithHeight((""), true, true, 18f));
					temp.addCell(PdfStyle.createValueCellWithHeight((""), true, true, 18f));
					temp.addCell(PdfStyle.createValueCellWithHeight((""), true, true, 18f));
					temp.addCell(PdfStyle.createValueCellWithHeight((""), true, true, 18f));
					temp.addCell(PdfStyle.createValueCellWithHeight((""), true, true, 18f));
					temp.addCell(PdfStyle.createValueCellWithHeight((""), true, true, 18f));

					cell = new PdfPCell(temp);
					cell.setBorder(0);
					cell.setColspan(11);
				}

				else {
					if (show_amount.equals("Y")) {
						PdfPTable temp = new PdfPTable(6);
						temp.setWidths(bodyTableWidth);

						temp.addCell(PdfStyle.createValueCellWithHeight((""), false, true, 18f));
						temp.addCell(PdfStyle.createValueCellWithHeight((""), false, true, 18f));
						temp.addCell(PdfStyle.createValueCellWithHeight((""), false, true, 18f));
						temp.addCell(PdfStyle.createValueCellWithHeight((""), true, true, 18f));
						temp.addCell(PdfStyle.createValueCellWithHeight((""), true, true, 18f));
						temp.addCell(PdfStyle.createValueCellWithHeight((""), true, true, 18f));

						temp.addCell(PdfStyle.createValueCellWithHeight((" "), false, true, 18f));
						temp.addCell(PdfStyle.createValueCellWithHeight((""), false, true, 18f));
						temp.addCell(PdfStyle.createValueCellWithHeight((""), false, true, 18f));
						temp.addCell(PdfStyle.createValueCellWithHeight((""), true, true, 18f));
						temp.addCell(PdfStyle.createValueCellWithHeight((""), true, true, 18f));
						temp.addCell(PdfStyle.createValueCellWithHeight((""), true, true, 18f));

						cell = new PdfPCell(temp);
						cell.setBorder(0);
						cell.setColspan(6);
					} else {
						PdfPTable temp = new PdfPTable(5);
						temp.setWidths(bodyTableWidth);

						temp.addCell(PdfStyle.createValueCellWithHeight((""), false, true, 18f));
						temp.addCell(PdfStyle.createValueCellWithHeight((""), false, true, 18f));
						temp.addCell(PdfStyle.createValueCellWithHeight((""), false, true, 18f));
						temp.addCell(PdfStyle.createValueCellWithHeight((""), true, true, 18f));
						temp.addCell(PdfStyle.createValueCellWithHeight((""), true, true, 18f));

						temp.addCell(PdfStyle.createValueCellWithHeight((" "), false, true, 18f));
						temp.addCell(PdfStyle.createValueCellWithHeight((""), false, true, 18f));
						temp.addCell(PdfStyle.createValueCellWithHeight((""), false, true, 18f));
						temp.addCell(PdfStyle.createValueCellWithHeight((""), true, true, 18f));
						temp.addCell(PdfStyle.createValueCellWithHeight((""), true, true, 18f));

						cell = new PdfPCell(temp);
						cell.setBorder(0);
						cell.setColspan(5);
					}

				}

// System.out.println("limit-row:::::"+(limit-rows));
				for (int i = 0; i < (limit - rows); i++) {
					// System.out.println("printing row:::"+i);
					bodytable.addCell(cell);
				}
			}

			cell = new PdfPCell(PdfStyle.createSingleLine());
			cell.setColspan(6);
			bodytable.addCell(cell);

			column.addElement(bodytable);
			column.setSimpleColumn(20, 0, 580, 512);
			column.go();

			if (companyCode.equals("P&G")) {
				bodytable = new PdfPTable(7);
			} else if (companyCode.equals("PAL")) {
				bodytable = new PdfPTable(11);
			} else {
				if (show_amount.equals("N")) {
					bodytable = new PdfPTable(5);
				} else
					bodytable = new PdfPTable(6);
			}
			bodytable.setWidthPercentage(100);
			bodytable.setWidths(bodyTableWidth);

			event.setFooter(
					createFooterPG(printLabel, isFooterData, pageflag, pagecount, total, division, loc, frm_challan,
							to_challan, dispatchType, prodtype, path, challan, show_amount, footer_ind, dsp_transporter,
							ship_doc_no, date, cases, weigh, dsp_challan_msg, goods_total, final_total, header_Igst,
							header_Cgst, header_Sgst, round_off, gst_doc_type, dsp_lorry_no, companyCode, igstAmounts));

		} catch (DocumentException e) {
			throw e;
		}
		return bodytable;
	}

	private PdfPTable createHeaderPG(PrePrintedDetailChallan_withgstPG challan, int pagecount, int division,
			Integer loc, String frm_challan, String to_challan, String dispatchType, String prodtype, String path,
			String show_amount, String compcd, String companyName, HttpSession session, float[] bodyTableWidth,
			String companyCode) throws Exception {

		System.out.println("create Header *********" + comp_cd);
		PdfPTable headertable = new PdfPTable(1);
		PdfPTable headertable01 = new PdfPTable(1);// Billing Address
		PdfPTable headertable2 = new PdfPTable(4);
		PdfPTable headertable3 = new PdfPTable(4);
		PdfPTable headertable4 = new PdfPTable(4);
		PdfPTable bodytable = null;
		String FOX = "path/to/resource/fox.png";
		headertable.setWidthPercentage(100);
		headertable01.setWidthPercentage(100);
		headertable2.setWidthPercentage(100);
		headertable3.setWidthPercentage(100);
		headertable4.setWidthPercentage(100);

		headertable01.setWidths(new float[] { 7f });
		headertable2.setWidths(new float[] { 4.5f, 2.5f, 1f, 2f });
		headertable3.setWidths(new float[] { 4.5f, 2.5f, 1f, 2f });
		headertable4.setWidths(new float[] { 2f, 2f, 2.5f, 3.5f });

		Font font = new Font(FontFamily.HELVETICA, 13, Font.BOLD, BaseColor.BLACK);
		Font font2 = new Font(FontFamily.HELVETICA, 9, Font.BOLD, BaseColor.BLACK);
		Font font3 = new Font(FontFamily.HELVETICA, 7.7f, Font.BOLD, BaseColor.BLACK);
		PdfPTable headertable1 = new PdfPTable(1);
		headertable1.setWidthPercentage(100);
		headertable1.setWidths(new float[] { 5f });

// ======================================================================================================================
// 1
		PdfPCell hcell = null;

		if (comp_cd.equalsIgnoreCase("PFZ")) {
			hcell = new PdfPCell(new Phrase("Delivery Challan", font));
		} else if (comp_cd.equalsIgnoreCase("UCM")) {
			hcell = new PdfPCell(new Phrase("TAX INVOICE", font));
		} else if (comp_cd.equalsIgnoreCase("NIL") || comp_cd.equalsIgnoreCase("NHP")) {
			hcell = new PdfPCell(new Phrase("TAX INVOICE", font));
			hcell.disableBorderSide(Rectangle.BOTTOM);
		} else {
			hcell = new PdfPCell(new Phrase("Delivery Challan", font));
		}

		hcell.setHorizontalAlignment(Element.ALIGN_CENTER);
		hcell.setVerticalAlignment(Element.ALIGN_CENTER);

		PdfPCell hcell_1 = new PdfPCell(new Phrase(" ", font));
		hcell_1.setBorder(Rectangle.LEFT);
		hcell_1.setHorizontalAlignment(Element.ALIGN_CENTER);
		headertable01.addCell(hcell);
		PdfPCell hcell2 = null;
		if (comp_cd.equalsIgnoreCase("NIL") || comp_cd.equalsIgnoreCase("NHP")) {
			hcell2 = new PdfPCell(new Phrase("(Rule 46 of CGST Rules,2017)", font2));
			hcell2.setHorizontalAlignment(Element.ALIGN_CENTER);
			hcell2.setVerticalAlignment(Element.ALIGN_CENTER);
			hcell2.disableBorderSide(Rectangle.TOP);
			headertable01.addCell(hcell2);
		}

// =================================================Blank Cell Added To
// Remove Table Border==============================

		PdfPCell hcell_blank = new PdfPCell(headertable01);
		hcell_blank.setBorder(Rectangle.RIGHT);
		hcell_blank.disableBorderSide(Rectangle.BOTTOM);

// ======================================================================================================================
// 2.1
//String company_name = (String) context.getAttribute("company_name");

		StringBuilder result1 = new StringBuilder();
		System.out.println("challan.getLocadd1():::" + challan.getLocadd1());
		result1.append(
				challan.getLocadd1().length() > 40 ? challan.getLocadd1().substring(0, 40) : challan.getLocadd1());
		result1.append(System.lineSeparator());

		result1.append(
				challan.getLocadd2().length() > 48 ? challan.getLocadd2().substring(0, 48) : challan.getLocadd2());
		result1.append(System.lineSeparator());

		result1.append(
				challan.getLocadd3().length() > 40 ? challan.getLocadd3().substring(0, 40) : challan.getLocadd3());
		result1.append(System.lineSeparator());

		result1.append(
				challan.getLocadd4().length() > 40 ? challan.getLocadd4().substring(0, 40) : challan.getLocadd4());
		result1.append(System.lineSeparator());

		result1.append("State :" + challan.getLoc_statename() + "  State Code :" + challan.getLoc_statecode());
		result1.append(System.lineSeparator());

		String comp_name_new = "Pfizer Products India Private Limited.";
		PdfPCell hcell_cn = null;
		if (challan.getDsp_challan_no().contains("PIP")) {
			if (comp_cd.equalsIgnoreCase("PFZ")) {
				hcell_cn = new PdfPCell(PdfStyle.createLabelCellWithBorderLargeFontNoBold(comp_name_new));
			} else {
				hcell_cn = new PdfPCell(PdfStyle.createLabelCellWithBorderLargeFontNoBold(company_name));
			}
			hcell_cn.setHorizontalAlignment(Element.ALIGN_LEFT);
			hcell_cn.setBorder(Rectangle.LEFT);
		} else {
			hcell_cn = new PdfPCell(PdfStyle.createLabelCellWithBorderLargeFontNoBold(companyName));
			hcell_cn.setHorizontalAlignment(Element.ALIGN_LEFT);
			hcell_cn.setBorder(Rectangle.LEFT);
		}

		PdfPCell hcell2w = new PdfPCell(PdfStyle.createLabelCellWithBorderSmallFontNoBoldForHeader(result1.toString()));
		hcell2w.setHorizontalAlignment(Element.ALIGN_LEFT);
		hcell2w.setBorder(Rectangle.LEFT);

		PdfPTable addressTbl_a = new PdfPTable(1);
		addressTbl_a.addCell(hcell_cn);
		addressTbl_a.addCell(hcell2w);

		PdfPCell hcella_4 = new PdfPCell(PdfStyle.createLabelCellWithoutBorderSmallFontNoPad("Drug Lic No.:"));
		hcella_4.setHorizontalAlignment(Element.ALIGN_LEFT);
		hcella_4.setVerticalAlignment(Element.ALIGN_TOP);
		hcella_4.setBorder(Rectangle.LEFT);

		PdfPCell hcella_4_1 = new PdfPCell(
				PdfStyle.createLabelCellWithBorderSmallFontNoBoldForHeader(challan.getLic1()));
		hcella_4_1.setHorizontalAlignment(Element.ALIGN_LEFT);
		hcella_4_1.setVerticalAlignment(Element.ALIGN_TOP);
		hcella_4_1.setBorder(0);

		PdfPCell hcella_7 = new PdfPCell(PdfStyle.createLabelCellWithoutBorderSmallFontNoPad(" "));
		hcella_7.setHorizontalAlignment(Element.ALIGN_LEFT);
		hcella_7.setVerticalAlignment(Element.ALIGN_TOP);
		hcella_7.setBorder(Rectangle.LEFT);

		PdfPCell hcella_4_2 = new PdfPCell(
				PdfStyle.createLabelCellWithBorderSmallFontNoBoldForHeader(challan.getLic2()));
		hcella_4_2.setHorizontalAlignment(Element.ALIGN_LEFT);
		hcella_4_2.setVerticalAlignment(Element.ALIGN_TOP);
		hcella_4_2.setBorder(0);

		PdfPCell hcella_5 = new PdfPCell(PdfStyle.createLabelCellWithoutBorderSmallFontNoPad("GSTIN :"));
		hcella_5.setHorizontalAlignment(Element.ALIGN_LEFT);
		hcella_5.setVerticalAlignment(Element.ALIGN_TOP);
		hcella_5.setBorder(Rectangle.LEFT);

		PdfPCell hcella_4_3 = new PdfPCell(
				PdfStyle.createLabelCellWithBorderSmallFontNoBoldForHeader(challan.getGst_reg_no()));
		hcella_4_3.setHorizontalAlignment(Element.ALIGN_LEFT);
		hcella_4_3.setVerticalAlignment(Element.ALIGN_TOP);
		hcella_4_3.setBorder(0);
		PdfPCell hcella_6;
		if (compcd.trim().equals("P&G")) {
			hcella_6 = new PdfPCell(PdfStyle.createLabelCellWithoutBorderSmallFontNoPad("MSME No.:"));
			hcella_6.setHorizontalAlignment(Element.ALIGN_LEFT);
			hcella_6.setVerticalAlignment(Element.ALIGN_TOP);
			hcella_6.setBorder(Rectangle.LEFT);
		} else {
			hcella_6 = new PdfPCell(PdfStyle.createLabelCellWithoutBorderSmallFontNoPad("CIN :"));
			hcella_6.setHorizontalAlignment(Element.ALIGN_LEFT);
			hcella_6.setVerticalAlignment(Element.ALIGN_TOP);
			hcella_6.setBorder(Rectangle.LEFT);
		}
		PdfPCell hcella_4_4;
		if (compcd.trim().equals("P&G")) {
			hcella_4_4 = new PdfPCell(
					PdfStyle.createLabelCellWithBorderSmallFontNoBoldForHeader(challan.getLoc_cst_no()));
			hcella_4_4.setHorizontalAlignment(Element.ALIGN_LEFT);
			hcella_4_4.setVerticalAlignment(Element.ALIGN_TOP);
			hcella_4_4.setBorder(0);
		} else {
			hcella_4_4 = new PdfPCell(
					PdfStyle.createLabelCellWithBorderSmallFontNoBoldForHeader(challan.getLoc_cinno()));
			hcella_4_4.setHorizontalAlignment(Element.ALIGN_LEFT);
			hcella_4_4.setVerticalAlignment(Element.ALIGN_TOP);
			hcella_4_4.setBorder(0);
		}

		PdfPCell hcella_8 = new PdfPCell(PdfStyle.createLabelCellWithoutBorderSmallFontNoPad("PAN :"));
		hcella_8.setHorizontalAlignment(Element.ALIGN_LEFT);
		hcella_8.setVerticalAlignment(Element.ALIGN_TOP);
		hcella_8.setBorder(Rectangle.LEFT);

		PdfPCell hcella_4_8 = new PdfPCell(
				PdfStyle.createLabelCellWithBorderSmallFontNoBoldForHeader(challan.getDptpan_no()));// PAN
		// no
		hcella_4_8.setHorizontalAlignment(Element.ALIGN_LEFT);
		hcella_4_8.setVerticalAlignment(Element.ALIGN_TOP);
		hcella_4_8.setBorder(0);
		PdfPCell hcella_9 = null;
		if (challan.getLoc_web_site() == null && challan.getLoc_web_site().trim().isEmpty()) {
			hcella_9 = new PdfPCell(PdfStyle.createLabelCellWithoutBorderSmallFontNoPad(" "));
			hcella_9.setHorizontalAlignment(Element.ALIGN_LEFT);
			hcella_9.setVerticalAlignment(Element.ALIGN_TOP);
			hcella_9.setBorder(Rectangle.LEFT);
		} else {
			if (compcd.trim().equals("P&G")) {
				hcella_9 = new PdfPCell(PdfStyle.createLabelCellWithoutBorderSmallFontNoPad(" "));
				hcella_9.setHorizontalAlignment(Element.ALIGN_LEFT);
				hcella_9.setVerticalAlignment(Element.ALIGN_TOP);
				hcella_9.setBorder(Rectangle.LEFT);
			} else {
				hcella_9 = new PdfPCell(PdfStyle.createLabelCellWithoutBorderSmallFontNoPad("Website"));
				hcella_9.setHorizontalAlignment(Element.ALIGN_LEFT);
				hcella_9.setVerticalAlignment(Element.ALIGN_TOP);
				hcella_9.setBorder(Rectangle.LEFT);
			}
		}

		PdfPCell hcella_4_9 = new PdfPCell(
				PdfStyle.createLabelCellWithBorderSmallFontNoBoldForHeader(challan.getLoc_web_site()));
		hcella_4_9.setHorizontalAlignment(Element.ALIGN_LEFT);
		hcella_4_9.setVerticalAlignment(Element.ALIGN_TOP);
		hcella_4_9.setBorder(0);

		PdfPCell hcella_10;

		hcella_10 = new PdfPCell(PdfStyle.createLabelCellWithoutBorderSmallFontNoPad("Tel No:"));
		hcella_10.setHorizontalAlignment(Element.ALIGN_LEFT);
		hcella_10.setVerticalAlignment(Element.ALIGN_TOP);
		hcella_10.setBorder(Rectangle.LEFT);

		PdfPCell hcella_4_10;
		if (compcd.trim().equals("P&G")) {
			hcella_4_10 = new PdfPCell(
					PdfStyle.createLabelCellWithBorderSmallFontNoBoldForHeader(challan.getLoc_mobile_no()));
			hcella_4_10.setHorizontalAlignment(Element.ALIGN_LEFT);
			hcella_4_10.setVerticalAlignment(Element.ALIGN_TOP);
			hcella_4_10.setBorder(0);
		} else {
			hcella_4_10 = new PdfPCell(
					PdfStyle.createLabelCellWithBorderSmallFontNoBoldForHeader(challan.getLoc_telephone_no()));
			hcella_4_10.setHorizontalAlignment(Element.ALIGN_LEFT);
			hcella_4_10.setVerticalAlignment(Element.ALIGN_TOP);
			hcella_4_10.setBorder(0);
		}

		PdfPCell hcella_11 = new PdfPCell(PdfStyle.createLabelCellWithBorderSmallFontNoPad("Email Id: "));
		hcella_11.setHorizontalAlignment(Element.ALIGN_LEFT);
		hcella_11.setVerticalAlignment(Element.ALIGN_TOP);
		hcella_11.disableBorderSide(Rectangle.TOP);
		hcella_11.disableBorderSide(Rectangle.RIGHT);

		PdfPCell hcella_4_11 = new PdfPCell(
				PdfStyle.createLabelCellWithBorderSmallFontNoBoldForHeader(challan.getLoc_emailid()));
		hcella_4_11.setHorizontalAlignment(Element.ALIGN_LEFT);
		hcella_4_11.setVerticalAlignment(Element.ALIGN_TOP);
		hcella_4_11.disableBorderSide(Rectangle.LEFT);
		hcella_4_11.disableBorderSide(Rectangle.TOP);
		hcella_4_11.disableBorderSide(Rectangle.RIGHT);

		PdfPCell cell11 = new PdfPCell(addressTbl_a);
		cell11.setBorder(0);

		PdfPCell hcella_12 = new PdfPCell(PdfStyle.createLabelCellWithBorderSmallFontNoPad("Bill To :"));
		hcella_12.setHorizontalAlignment(Element.ALIGN_LEFT);
		hcella_12.setVerticalAlignment(Element.ALIGN_TOP);
		hcella_12.disableBorderSide(Rectangle.BOTTOM);
		hcella_12.disableBorderSide(Rectangle.RIGHT);

		PdfPCell hcella_4_12 = new PdfPCell(
//PdfStyle.createLabelCellWithBorderSmallFontNoBoldForHeader(challan.getBilltoname1() == null ? "" : challan.getBilltoname1()));
				PdfStyle.createLabelCellWithBorderSmallFontNoBoldForHeader(" "));
		hcella_4_12.setHorizontalAlignment(Element.ALIGN_LEFT);
		hcella_4_12.setVerticalAlignment(Element.ALIGN_TOP);
		hcella_4_12.disableBorderSide(Rectangle.BOTTOM);
		hcella_4_12.disableBorderSide(Rectangle.LEFT);
		hcella_4_12.disableBorderSide(Rectangle.RIGHT);

		PdfPCell hcella_13 = new PdfPCell(PdfStyle.createLabelCellWithoutBorderSmallFontNoPad(""));
		hcella_13.setHorizontalAlignment(Element.ALIGN_LEFT);
		hcella_13.setVerticalAlignment(Element.ALIGN_TOP);
		hcella_13.setBorder(Rectangle.LEFT);

		PdfPCell hcella_4_13 = new PdfPCell(
// PdfStyle.createLabelCellWithBorderSmallFontNoBoldForHeader(challan.getBilltoname2() == null ? "" : challan.getBilltoname2()));  //Testing
				PdfStyle.createLabelCellWithBorderSmallFontNoBoldForHeader(" "));
		hcella_4_13.setHorizontalAlignment(Element.ALIGN_LEFT);
		hcella_4_13.setVerticalAlignment(Element.ALIGN_TOP);
		hcella_4_13.setBorder(0);

		PdfPCell hcella_14 = new PdfPCell(PdfStyle.createLabelCellWithoutBorderSmallFontNoPad(""));
		hcella_14.setHorizontalAlignment(Element.ALIGN_LEFT);
		hcella_14.setVerticalAlignment(Element.ALIGN_TOP);
		hcella_14.setBorder(Rectangle.LEFT);

		String gstNo = "";
		gstNo = "GSTIN : " + " " + " " + ", State code : " + " " + " - " + " ";
		PdfPCell hcella_4_14 = new PdfPCell(PdfStyle.createLabelCellWithBorderSmallFontNoBoldForHeader(gstNo));
		hcella_4_14.setHorizontalAlignment(Element.ALIGN_LEFT);
		hcella_4_14.setVerticalAlignment(Element.ALIGN_TOP);
		hcella_4_14.setBorder(0);

		PdfPTable addressTbl = new PdfPTable(1);
		PdfPTable addressTbl_b = new PdfPTable(2);
		addressTbl_b.setWidths(new float[] { 1.5f, 8.5f });
//addressTbl_b.setWidths(new float[] { 1.5f, 3f,5.5f });
		addressTbl_b.addCell(hcella_4);
		addressTbl_b.addCell(hcella_4_1);
		addressTbl_b.addCell(hcella_7);
		addressTbl_b.addCell(hcella_4_2);

		addressTbl_b.addCell(hcella_5);
		addressTbl_b.addCell(hcella_4_3);
		addressTbl_b.addCell(hcella_6);
		addressTbl_b.addCell(hcella_4_4);

		if (!comp_cd.trim().equalsIgnoreCase("NIL") && !comp_cd.trim().equalsIgnoreCase("NHP")) {
			addressTbl_b.addCell(hcella_8);
			addressTbl_b.addCell(hcella_4_8);
			addressTbl_b.addCell(hcella_9);
			addressTbl_b.addCell(hcella_4_9);
		}
		addressTbl_b.addCell(hcella_10);
		addressTbl_b.addCell(hcella_4_10);
		addressTbl_b.addCell(hcella_11);
		addressTbl_b.addCell(hcella_4_11);

		if (comp_cd.equalsIgnoreCase("NIL") || comp_cd.equalsIgnoreCase("NHP")) {
			addressTbl_b.addCell(hcella_12);
			addressTbl_b.addCell(hcella_4_12);
			addressTbl_b.addCell(hcella_13);
			addressTbl_b.addCell(hcella_4_13);
			addressTbl_b.addCell(hcella_14);
			addressTbl_b.addCell(hcella_4_14);
		}
		PdfPCell cell12 = new PdfPCell(addressTbl_b);
		cell12.setBorder(0);
		addressTbl.addCell(cell11);
		addressTbl.addCell(cell12);

		PdfPCell cell1 = new PdfPCell(addressTbl);
		cell1.setBorder(0);

		if (comp_cd.trim().equalsIgnoreCase("MDL")) {
			headertable2.addCell(cell1);
//String path=ServletActionContext.getServletContext().getRealPath("");
//String logoImag = path+"\\pfizer_blk_pos.jpg";
			String logoImag = path + "\\pfizer_blk_pos.png";

			System.out.println("logoImag : " + logoImag);
			Image img = null;
			try {
				img = Image.getInstance(logoImag);
				img.setAlignment(Image.ALIGN_CENTER);
			} catch (Exception e) {
				System.out.println("Error Occurred :" + e.getMessage());// uncomment asneeded --;
			}
			img.setAlignment(1);
			PdfPCell imagecell = new PdfPCell(img, true);
			imagecell.setFixedHeight(10f);
//imagecell.setNoWrap(false);
			imagecell.setBorder(0);
			imagecell.setHorizontalAlignment(Element.ALIGN_LEFT);
			imagecell.setVerticalAlignment(Element.ALIGN_CENTER);
			imagecell.setBorder(Rectangle.NO_BORDER | Rectangle.RIGHT);
//PdfPTable t1= new PdfPTable(1);
//t1.addCell(imagecell);

			headertable2.addCell(imagecell);

		} else if (comp_cd.trim().equalsIgnoreCase("PFZ")) {
			headertable2.addCell(cell1);
//String path=ServletActionContext.getServletContext().getRealPath("");
//String logoImag = path+"\\pfizer_blk_pos.jpg";
			int i = 1;
			String logoImag;
			if (i == 1) {
				logoImag = path + "\\pfizer_blk_pos.png";
			} else {
				logoImag = path + "\\excelLogo_blk.png";
			}
			System.out.println("logoImag : " + logoImag);
			Image img = null;
			try {
				img = Image.getInstance(logoImag);
				img.setAlignment(Image.ALIGN_LEFT);
			} catch (Exception e) {
				System.out.println("Error Occurred :" + e.getMessage());// uncomment asneeded --;
			}
			img.setAlignment(1);
			PdfPCell imagecell = new PdfPCell(img, true);
			imagecell.setFixedHeight(10f);
//imagecell.setNoWrap(false);
			imagecell.setBorder(0);
			imagecell.setHorizontalAlignment(Element.ALIGN_LEFT);
			imagecell.setVerticalAlignment(Element.ALIGN_CENTER);
			imagecell.setBorder(Rectangle.NO_BORDER | Rectangle.RIGHT);
//PdfPTable t1= new PdfPTable(1);
//t1.addCell(imagecell);
			if (challan.getDsp_challan_no().contains("PIP")) {

				PdfPCell cell = new PdfPCell(new Phrase(" "));
				cell.setBorder(Rectangle.NO_BORDER);
				headertable2.addCell(cell);

			} else {
				headertable2.addCell(imagecell);
			}

		}

		else if (companyCode.equals("PAL")) {
			headertable2.addCell(cell1);
			String logoImag;
			logoImag = path + "\\Logo_Palson.png";

			System.out.println("logoImag : " + logoImag);
			Image img = null;
			try {
				img = Image.getInstance(logoImag);
				img.setAlignment(Image.ALIGN_LEFT);
			} catch (Exception e) {
				System.out.println("Error Occurred :" + e.getMessage());// uncomment asneeded --;
			}
			img.setAlignment(1);
			PdfPCell imagecell = new PdfPCell(img, true);
			imagecell.setFixedHeight(3f);
			// imagecell.setNoWrap(false);
			imagecell.setBorder(0);
			imagecell.setHorizontalAlignment(Element.ALIGN_LEFT);
			imagecell.setVerticalAlignment(Element.ALIGN_CENTER);
			imagecell.setBorder(Rectangle.NO_BORDER | Rectangle.RIGHT);
			// PdfPTable t1= new PdfPTable(1);
			// t1.addCell(imagecell);

			headertable2.addCell(imagecell);

		} else {
			cell1.setColspan(2);
			headertable2.addCell(cell1);
		}

//TODO cell2 image add for medley by vivek 14-nov-2018

// ======================================================================================================================
// 2.3

//comp_cd = (String) context.getAttribute("comp_code");

		PdfPCell hcell16 = null;
		if (comp_cd.equalsIgnoreCase("NIL") || comp_cd.equalsIgnoreCase("NHP")) {
			hcell16 = new PdfPCell(PdfStyle.createLabelCellWithBorderSmallFont("Tax Inv Date"));
		} else {
			hcell16 = new PdfPCell(PdfStyle.createLabelCellWithBorderSmallFont("Date "));
		}
		hcell16.setHorizontalAlignment(Element.ALIGN_LEFT);

		PdfPCell hcell20 = null;

		if (comp_cd.equalsIgnoreCase("PFZ")) {
			hcell20 = new PdfPCell(PdfStyle.createLabelCellWithBorderSmallFont("Challan #"));
			hcell20.setHorizontalAlignment(Element.ALIGN_LEFT);

		} else if (comp_cd.equalsIgnoreCase("UCM")) {
			hcell20 = new PdfPCell(PdfStyle.createLabelCellWithBorderSmallFont("GST INV No#"));
			hcell20.setHorizontalAlignment(Element.ALIGN_LEFT);

		} else if (comp_cd.equalsIgnoreCase("NIL") || comp_cd.equalsIgnoreCase("NHP")) {
			hcell20 = new PdfPCell(PdfStyle.createLabelCellWithBorderSmallFont("Tax Invoice #"));
			hcell20.setHorizontalAlignment(Element.ALIGN_LEFT);
		} else {
			hcell20 = new PdfPCell(PdfStyle.createLabelCellWithBorderSmallFont("Challan #"));
			hcell20.setHorizontalAlignment(Element.ALIGN_LEFT);
		}

		PdfPCell hcell21 = new PdfPCell(PdfStyle.createLabelCellWithBorderSmallFont("Customer"));
		hcell21.setHorizontalAlignment(Element.ALIGN_LEFT);

		PdfPCell hcell22 = new PdfPCell(PdfStyle.createLabelCellWithBorderSmallFont("Division "));
		hcell22.setHorizontalAlignment(Element.ALIGN_LEFT);

		PdfPCell hcell23 = new PdfPCell(PdfStyle.createLabelCellWithBorderSmallFont("Team"));
		hcell23.setHorizontalAlignment(Element.ALIGN_LEFT);

//PdfPCell hcell24 = new PdfPCell(PdfStyle.createLabelCellWithBorderSmallFont("Remark"));
//hcell24.setHorizontalAlignment(Element.ALIGN_LEFT);

		PdfPCell hcell17 = new PdfPCell(PdfStyle.createLabelCellWithBorderSmallFontTwiceHeight("Email"));
		hcell17.setHorizontalAlignment(Element.ALIGN_LEFT);

		PdfPCell hcell18 = new PdfPCell(PdfStyle.createLabelCellWithBorderSmallFont("Telephone 1"));
		hcell18.setHorizontalAlignment(Element.ALIGN_LEFT);

		PdfPCell hcell19 = new PdfPCell(PdfStyle.createLabelCellWithBorderSmallFont("Telephone 2"));
		hcell19.setHorizontalAlignment(Element.ALIGN_LEFT);

		PdfPCell hcell19_from_to = new PdfPCell(PdfStyle.createLabelCellWithBorderSmallFont("Unique No :"));
		hcell19_from_to.setHorizontalAlignment(Element.ALIGN_LEFT);

		PdfPTable addressTbl2 = new PdfPTable(1);
		addressTbl2.addCell(hcell16);

		addressTbl2.addCell(hcell20);
		addressTbl2.addCell(hcell21);
		addressTbl2.addCell(hcell22);
		addressTbl2.addCell(hcell23);
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
// Font medFont = new Font(Font.FontFamily.HELVETICA, 12,Font.NORMAL,
// BaseColor.BLACK);

		PdfPCell hcell26 = new PdfPCell(PdfStyle.createLabelCellWithBorderSmallFontNoBold(
				(challan.getDsp_challan_dt() == null ? "" : challan.getDsp_challan_dt())));
		hcell26.setHorizontalAlignment(Element.ALIGN_LEFT);

		PdfPCell hcell30 = new PdfPCell(
				PdfStyle.createLabelCellWithBorderSmallFontNoBold((challan.getDsp_challan_no() == null ? ""
						: challan.getDsp_challan_no() + "    ( " + challan.getPri_no() + " )")));
		hcell30.setHorizontalAlignment(Element.ALIGN_LEFT);

		PdfPCell hcell_30 = new PdfPCell(PdfStyle.createLabelCellWithBorderSmallFontNoBold(
				(challan.getDsp_challan_no() == null ? "" : challan.getDsp_challan_no())));
		hcell_30.setHorizontalAlignment(Element.ALIGN_LEFT);

		PdfPCell hcell31 = new PdfPCell(PdfStyle.createLabelCellWithBorderSmallFontNoBold(
				(challan.getDspfstaff_displayname().length() > 20 ? challan.getDspfstaff_displayname().substring(0, 20)
						: challan.getDspfstaff_displayname())));
		hcell31.setHorizontalAlignment(Element.ALIGN_LEFT);

		PdfPCell hcell32 = new PdfPCell(PdfStyle.createLabelCellWithBorderSmallFontNoBold(
				(challan.getDivision().length() > 28 ? challan.getDivision().substring(0, 28)
						: challan.getDivision())));
		hcell32.setHorizontalAlignment(Element.ALIGN_LEFT);

		System.out.println("challan.getTeam()" + challan.getTeam());
		PdfPCell hcell33 = new PdfPCell(PdfStyle.createLabelCellWithBorderSmallFontNoBold(
				((challan.getTeam() == null ? "" : challan.getTeam()).length() > 30 ? challan.getTeam().substring(0, 30)
						: challan.getTeam() == null ? "" : challan.getTeam())));
		hcell33.setHorizontalAlignment(Element.ALIGN_LEFT);

		PdfPCell hcell34 = new PdfPCell(PdfStyle.createLabelCellWithBorderSmallFontNoBold(
				challan.getDsp_challan_msg() == null ? "" : challan.getDsp_challan_msg()));
		hcell34.setHorizontalAlignment(Element.ALIGN_LEFT);

		PdfPCell hcell27 = new PdfPCell(PdfStyle.createLabelCellWithBorderSmallFontNoBoldTwiceHeight(
				(challan.getDspfstaff_email().length() > 40 ? challan.getDspfstaff_email().substring(0, 40)
						: challan.getDspfstaff_email())));
		hcell27.setHorizontalAlignment(Element.ALIGN_LEFT);

		PdfPCell hcell28 = new PdfPCell(PdfStyle.createLabelCellWithBorderSmallFontNoBold(
				(challan.getDspfstaff_mobile() == null ? "" : challan.getDspfstaff_mobile())));
		hcell28.setHorizontalAlignment(Element.ALIGN_LEFT);

		PdfPCell hcell29 = new PdfPCell(PdfStyle.createLabelCellWithBorderSmallFontNoBold(
				(challan.getDspfstaff_mobile2() == null ? "" : challan.getDspfstaff_mobile2()))); // telephone //added
		// 2
		hcell29.setHorizontalAlignment(Element.ALIGN_LEFT);

		String s = challan.getTo_num().length() < 6 ? ""
				: challan.getTo_num().substring(challan.getTo_num().length() - 5);
		PdfPCell hcell29_from_to = new PdfPCell(
				PdfStyle.createLabelCellWithBorderSmallFontNoBold((challan.getFr_num().length() < 6 ? ""
						: challan.getFr_num().substring(challan.getFr_num().length() - 5) + " To " + s)));

		hcell29_from_to.setHorizontalAlignment(Element.ALIGN_LEFT);

		PdfPTable addressTbl3 = new PdfPTable(1);
		addressTbl3.addCell(hcell26);

		if (companyCode.equals("P&G")) {
			addressTbl3.addCell(hcell30);
		} else {
			addressTbl3.addCell(hcell_30);
		}
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

// =====================================================================================================================

// =====================================================================================================================
// 3.1

// PdfPCell hcell35 = new PdfPCell(PdfStyle.createLabelCellWithBorderSmallFont("Ship To :"));
		PdfPCell hcell35 = new PdfPCell(new Phrase("Ship To :", font3));

		hcell35.setHorizontalAlignment(Element.ALIGN_LEFT);

		PdfPCell hcell36 = new PdfPCell(PdfStyle.createLabelCellWithoutBorder(
				challan.getDspfstaff_displayname() == null ? "" : challan.getDspfstaff_displayname()));
		hcell36.setHorizontalAlignment(Element.ALIGN_LEFT);
		hcell36.enableBorderSide(Rectangle.LEFT);
		hcell36.enableBorderSide(Rectangle.RIGHT);
		StringBuilder result2 = new StringBuilder();
		result2.append(challan.getDspfstaffaddr1().length() > 55 ? challan.getDspfstaffaddr1().substring(0, 55)
				: challan.getDspfstaffaddr1());
		result2.append(System.lineSeparator());
		result2.append(challan.getDspfstaffaddr2().length() > 55 ? challan.getDspfstaffaddr2().substring(0, 55)
				: challan.getDspfstaffaddr2());
		result2.append(System.lineSeparator());
		result2.append(challan.getDspfstaffaddr3().length() > 55 ? challan.getDspfstaffaddr3().substring(0, 55)
				: challan.getDspfstaffaddr3());
		result2.append(System.lineSeparator());
		result2.append(challan.getDspfstaffaddr4().length() > 55 ? challan.getDspfstaffaddr4().substring(0, 55)
				: challan.getDspfstaffaddr4());
		result2.append(System.lineSeparator());
		result2.append("GSTIN : UNREGISTERED");
		result2.append(System.lineSeparator());
		result2.append("PAN : Not Available");
		result2.append(System.lineSeparator());

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
//String address1 = (String) context.getAttribute("address1");
//String address2 = (String) context.getAttribute("address2");
//String address3 = (String) context.getAttribute("address3");
//String address4 = (String) context.getAttribute("address4");
// WEB_SITE cinno
//String web_site = (String) context.getAttribute("web_site") == null ? " "
// : (String) context.getAttribute("web_site");
//String cinno = (String) context.getAttribute("cinno") == null ? " " : (String) context.getAttribute("cinno");
		Company company = ((Company) session.getServletContext().getAttribute(COMPANY_DATA));
		String address1 = company.getAddress1();
		String address2 = company.getAddress2();
		String address3 = company.getAddress3();
		String address4 = company.getAddress4();
		String web_site = company.getWeb_site();
		String cinno = company.getCinno();

		StringBuilder result3 = new StringBuilder();

// Company name added 27-1-2017
		if (challan.getDsp_challan_no().contains("PIP")) {

			if (comp_cd.equalsIgnoreCase("PFZ")) {
				result3.append(comp_name_new);

				address1 = ":";
				address2 = ":";
				address3 = ":";
				address4 = ":";
				web_site = ":";
				cinno = ":"; //// Temporary Code 2-6-2020
			} else {
				result3.append(company_name);
			}

			result3.append(System.lineSeparator());
		} else {
			if (company_name.trim().isEmpty()) {
				result3.append(System.lineSeparator());
			} else {
				result3.append(company_name.length() > 30 ? company_name.substring(0, 30) : company_name);
				result3.append(System.lineSeparator());
			}
		}

		result3.append(address1.length() > 40 ? address1.substring(0, 40) : address1);
		result3.append(System.lineSeparator());
		result3.append(address2.length() > 48 ? address2.substring(0, 40) : address2);
		result3.append(System.lineSeparator());
		result3.append(address3.length() > 40 ? address3.substring(0, 40) : address3);
		result3.append(System.lineSeparator());
		result3.append(address4.length() > 40 ? address4.substring(0, 40) : address4);
		result3.append(System.lineSeparator());

		if (web_site.trim().isEmpty() || compcd.trim().equals("P&G")) {
			result3.append(" ");
			result3.append(System.lineSeparator());
		} else {
			result3.append(web_site.length() > 40 ? "Website:" + web_site.substring(0, 40) : "Website:" + web_site);
			result3.append(System.lineSeparator());

		}
//System.out.println(cinno+"#sdfsdfsd");
		if (cinno.trim().isEmpty() || compcd.trim().equals("P&G")) {
			result3.append(" ");
			result3.append(System.lineSeparator());
		} else {
			result3.append(cinno.length() > 33 ? "CIN NO:" + cinno.substring(0, 33) : "CIN NO:" + cinno);
			result3.append(System.lineSeparator());
		}

// PdfPCell hcell38 = new PdfPCell(PdfStyle.createLabelCellWithBorderSmallFont("Principal place of business supplier"));
		PdfPCell hcell38 = new PdfPCell(new Phrase("Principal place of business supplier ", font3));
		hcell38.setHorizontalAlignment(Element.ALIGN_LEFT);

		PdfPCell hcell39 = new PdfPCell(PdfStyle.createLabelCellWithoutBorderSmallFontNoBold(
				result3.length() > 170 ? result3.substring(0, 170) : result3.toString()));
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

		if (!comp_cd.equalsIgnoreCase("NIL") && !comp_cd.equalsIgnoreCase("NHP")) {
			result4.append("Free samples and/"); // Goods not for sale. Free samples
			// and / or promotional inputs only
			// having no commercial value
			result4.append(System.lineSeparator());
			result4.append("or promotional inputs");
			result4.append(System.lineSeparator());
			result4.append("only having no ");
			result4.append(System.lineSeparator());
			result4.append("commercial value.");
			result4.append(System.lineSeparator());
		} else {
			result4.append("                                                "); // Goods not for sale. Free samples
			// and / or promotional inputs only
			// having no commercial value
			result4.append(System.lineSeparator());
			result4.append(System.lineSeparator());
			result4.append(System.lineSeparator());
			result4.append(System.lineSeparator());
			result4.append(System.lineSeparator());
			result4.append(System.lineSeparator());
			result4.append(System.lineSeparator());
			result4.append(System.lineSeparator());
			result4.append(System.lineSeparator());

		}
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

		if (comp_cd.equalsIgnoreCase("PFZ")) {
			if (show_amount.equals("Y")) {
				hcell45 = new PdfPCell(PdfStyle.createLabelCellWithBorderSmallFont("Insurance Policy No."));
			} else {
				hcell45 = new PdfPCell(PdfStyle.createLabelCellWithBorderSmallFont(""));
			}

		} else if (comp_cd.equalsIgnoreCase("UCM") || comp_cd.equals("PAL")) {
			hcell45 = new PdfPCell(PdfStyle.createLabelCellWithBorderSmallFont(""));
		} else {
			hcell45 = new PdfPCell(PdfStyle.createLabelCellWithBorderSmallFont("Insurance Policy No."));

		}

		hcell45.setHorizontalAlignment(Element.ALIGN_LEFT);

		PdfPCell hcell46 = new PdfPCell(PdfStyle.createLabelCellWithoutBorderSmallFontNoBold(" "));
		hcell46.setHorizontalAlignment(Element.ALIGN_LEFT);
		hcell46.enableBorderSide(Rectangle.RIGHT);
		PdfPCell hcell47 = null;

		if (compcd.trim().equals("P&G") || comp_cd.equals("PAL")) {
			hcell47 = new PdfPCell(PdfStyle.createLabelCellWithBorderSmallFont(" "));
		} else {
			hcell47 = new PdfPCell(PdfStyle.createLabelCellWithBorderSmallFont("Food Licence Nos."));
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
		headertable3.addCell(cell8);

// =================================================Blank Cell Added To
// Remove Table Border3==============================

		PdfPCell hcell_blank3 = new PdfPCell(headertable3);
		hcell_blank3.setBorder(Rectangle.NO_BORDER);

		if (companyCode.equals("P&G")) {
			bodytable = new PdfPTable(7);
			bodytable.setWidthPercentage(100);
			bodytable.setWidths(bodyTableWidth);
			bodytable.addCell(PdfStyle.createLabelWithBGColour("Item"));
			bodytable.addCell(PdfStyle.createLabelWithBGColour("HSN Code"));
			bodytable.addCell(PdfStyle.createLabelWithBGColour("Batch/Art Work No"));
			bodytable.addCell(PdfStyle.createLabelWithBGColour("Expiry Date"));
			bodytable.addCell(PdfStyle.createLabelWithBGColour("Qty"));
			if (show_amount.equals("Y")) {
				if (compcd.equals("P&G")) {
					bodytable.addCell(PdfStyle.createLabelWithBGColour("Rate"));
				} else {
					bodytable.addCell(PdfStyle.createLabelWithBGColour("Taxable Value"));
				}
			}
			bodytable.addCell(PdfStyle.createLabelWithBGColour("Taxable Amount"));
		}

		else if (companyCode.equals("PAL")) {
			bodytable = new PdfPTable(11);
			bodytable.setWidthPercentage(100);
			bodytable.setWidths(bodyTableWidth);
			bodytable.addCell(PdfStyle.createLabelWithBGColour("Sl No."));
			bodytable.addCell(PdfStyle.createLabelWithBGColour("Item code"));
			bodytable.addCell(PdfStyle.createLabelWithBGColour("Item Description"));
			bodytable.addCell(PdfStyle.createLabelWithBGColour("Pack"));
			bodytable.addCell(PdfStyle.createLabelWithBGColour("UOM"));
			bodytable.addCell(PdfStyle.createLabelWithBGColour("HSN Code"));
			bodytable.addCell(PdfStyle.createLabelWithBGColour("Batch No."));
			bodytable.addCell(PdfStyle.createLabelWithBGColour("Expiry Date"));
			bodytable.addCell(PdfStyle.createLabelWithBGColour("Rate"));
			bodytable.addCell(PdfStyle.createLabelWithBGColour("Free Qty"));
			bodytable.addCell(PdfStyle.createLabelWithBGColour("Taxable Amount"));

		} else {
			if (show_amount.equals("N")) {
				bodytable = new PdfPTable(5);
			} else {
				bodytable = new PdfPTable(6);
			}
			bodytable.setWidthPercentage(100);
			bodytable.setWidths(bodyTableWidth);
			bodytable.addCell(PdfStyle.createLabelWithBGColour("Item"));
			bodytable.addCell(PdfStyle.createLabelWithBGColour("HSN Code"));
			bodytable.addCell(PdfStyle.createLabelWithBGColour("Batch/Art Work No"));
			bodytable.addCell(PdfStyle.createLabelWithBGColour("Expiry Date"));
			bodytable.addCell(PdfStyle.createLabelWithBGColour("Qty"));
			if (show_amount.equals("Y")) {
				if (compcd.equals("P&G")) {
					bodytable.addCell(PdfStyle.createLabelWithBGColour("Rate"));
				} else {
					bodytable.addCell(PdfStyle.createLabelWithBGColour("Taxable Value"));
				}
			}

		}

// =================================================Blank Cell Added To
// Remove Table Border4==============================

		PdfPCell hcell_blank4 = new PdfPCell(bodytable);
		hcell_blank4.setBorder(Rectangle.NO_BORDER);
		headertable.addCell(hcell_blank);
		headertable.addCell(hcell_blank2);
		headertable.addCell(hcell_blank3);
		headertable.addCell(hcell_blank4);

		return headertable;
	}

	private PdfPTable createFooterPG(HashMap<String, String> printLabel, boolean isFooterData, boolean pageflag,
			int pagecount, BigDecimal total, int division, Integer loc, String frm_challan, String to_challan,
			String dispatchType, String prodtype, String path, List<PrePrintedDetailChallan_withgstPG> challan,
			String show_amount, boolean footer_ind, String dsp_transporter, String ship_doc_no, String date,
			String cases, String weigh, String dsp_challan_msg, BigDecimal goods_total, BigDecimal final_total,
			BigDecimal header_Igst, BigDecimal header_Cgst, BigDecimal header_Sgst, BigDecimal round_off,
			String gst_doc_type, String dsp_lorry_no, String companyCode, BigDecimal igstAmounts)
			throws DocumentException {

		System.out.println("createFootercaled$$$$$$$$$$$$$$$$$");
		PdfPTable maintable = new PdfPTable(2);
// System.out.println("ship_doc_no::"+ship_doc_no+"dsp_transporter::"+dsp_transporter+"cases:"+cases);
// System.out.println("challan size() " +challan.size());
		try {

			PdfPCell cell = null;
			cell = new PdfPCell();

//Testing
			String narrration = printService.getLocationNarrationByLocId(Long.parseLong(loc.toString()));
			maintable.setWidthPercentage(100);
			maintable.setWidths(new float[] { 5.5f, 4.5f });

			// footer left cell
			PdfPTable foot_left_table = new PdfPTable(1);
			foot_left_table.setWidthPercentage(100);
			foot_left_table.setWidths(new float[] { 10f });

			PdfPCell foot_bottom_cell1 = new PdfPCell(PdfStyle.createUltimateCellWithBorderWithDisableBorder(
					(narrration.substring(1, narrration.length() - 1)), Element.ALIGN_LEFT, Element.ALIGN_TOP, 9, 14f,
					true, 1, 1, false, true, true, false, true));
			foot_left_table.addCell(foot_bottom_cell1);

			if (comp_cd.equals("PAL")) {

				PdfPCell foot_bottom_cell2 = new PdfPCell(
						PdfStyle.createUltimateCellWithoutBorder((""), 2, 5, 9, 18, false));
				foot_left_table.addCell(foot_bottom_cell2);

				PdfPCell foot_bottom_cell3 = new PdfPCell(
						PdfStyle.createUltimateCellWithoutBorder((""), 2, 5, 9, 18, false));
				foot_left_table.addCell(foot_bottom_cell3);

				PdfPCell foot_bottom_cell4 = new PdfPCell(PdfStyle.createUltimateCellWithBorderWithDisableBorder(
						("Value for Insurance purpose only Rs. "
								+ goods_total.setScale(2, BigDecimal.ROUND_HALF_UP).toString()),
						Element.ALIGN_LEFT, Element.ALIGN_TOP, 9, 14f, true, 1, 1, false, true, true, false, true));
				foot_left_table.addCell(foot_bottom_cell4);
			}

//    PdfPCell foot_bottom_cell1 = new PdfPCell(
//    PdfStyle.createUltimateCellWithBorderWithDisableBorder(" ", Element.ALIGN_LEFT,
//    Element.ALIGN_TOP, 9, 14f, true, 1, 1, false, true, true, false, true));
//    foot_left_table.addCell(foot_bottom_cell1);

			// footer right cell
			PdfPTable foot_right_table = new PdfPTable(2);
			foot_right_table.setWidthPercentage(100);
			foot_right_table.setWidths(new float[] { 3f, 2.4f });

			String goodsOrTotal = "";
			if (comp_cd.equalsIgnoreCase("NIL") || comp_cd.equalsIgnoreCase("PAL") || comp_cd.equalsIgnoreCase("NHP"))
				goodsOrTotal = "Total";
			else if (comp_cd.equalsIgnoreCase("P&G")) {
				goodsOrTotal = "Taxable";
			} else
				goodsOrTotal = "Goods";

			// new changes for remove column in pdf cell by vivek based on footer indicator

//   if(footer_ind && comp_cd.equalsIgnoreCase("NIL")){
			if (footer_ind && comp_cd.equalsIgnoreCase("PFZ") || comp_cd.equalsIgnoreCase("P&G")
					|| comp_cd.equalsIgnoreCase("PAL") || comp_cd.equalsIgnoreCase("NIL")
					|| comp_cd.equalsIgnoreCase("NHP")) {

				if (comp_cd.equalsIgnoreCase("P&G") || comp_cd.equalsIgnoreCase("PAL")) {
					PdfPCell foot_bottom_right_cell1 = new PdfPCell(
							PdfStyle.createUltimateCellWithBorderWithDisableBorder((goodsOrTotal + " Amount"),
									Element.ALIGN_LEFT, Element.ALIGN_TOP, 8, 14f, true, 1, 1, false, true, true, false,
									true));
					foot_right_table.addCell(foot_bottom_right_cell1);

					PdfPCell foot_bottom_right_cell2 = new PdfPCell(PdfStyle
							.createUltimateCellWithBorderWithDisableBorder(("0.00".toString()), Element.ALIGN_RIGHT,
									Element.ALIGN_TOP, 8, 14f, true, 1, 1, false, true, true, false, true));
					foot_right_table.addCell(foot_bottom_right_cell2);
				} else {
					if (show_amount.equals("Y")) {
						PdfPCell foot_bottom_right_cell1 = new PdfPCell(
								PdfStyle.createUltimateCellWithBorderWithDisableBorder((goodsOrTotal + " Value"),
										Element.ALIGN_LEFT, Element.ALIGN_TOP, 8, 14f, true, 1, 1, false, true, true,
										false, true));
						foot_right_table.addCell(foot_bottom_right_cell1);

						PdfPCell foot_bottom_right_cell2 = new PdfPCell(
								PdfStyle.createUltimateCellWithBorderWithDisableBorder(
										(goods_total.setScale(2, BigDecimal.ROUND_HALF_UP).toString()),
										Element.ALIGN_RIGHT, Element.ALIGN_TOP, 8, 14f, true, 1, 1, false, true, true,
										false, true));
						foot_right_table.addCell(foot_bottom_right_cell2);
					} else {
						PdfPCell foot_bottom_right_cell1 = new PdfPCell(
								PdfStyle.createUltimateCellWithBorderWithDisableBorder(("\n"), Element.ALIGN_LEFT,
										Element.ALIGN_TOP, 8, 14f, true, 1, 1, false, true, true, false, true));
						foot_right_table.addCell(foot_bottom_right_cell1);

						PdfPCell foot_bottom_right_cell2 = new PdfPCell(
								PdfStyle.createUltimateCellWithBorderWithDisableBorder(("\n"), Element.ALIGN_RIGHT,
										Element.ALIGN_TOP, 8, 14f, true, 1, 1, false, true, true, false, true));
						foot_right_table.addCell(foot_bottom_right_cell2);
					}
				}

				if (docType.contains("008")) {
					System.out.println("inside Inter");
					PdfPCell foot_bottom_right_cell33;
					PdfPCell foot_bottom_right_cell43;
					if (show_amount.equals("Y")) {
						foot_bottom_right_cell33 = new PdfPCell(
								PdfStyle.createUltimateCellWithBorderWithDisableBorder(("IGST"), Element.ALIGN_LEFT,
										Element.ALIGN_TOP, 8, 14f, true, 1, 1, false, true, true, false, true));
						foot_right_table.addCell(foot_bottom_right_cell33);
					} else {
						foot_bottom_right_cell33 = new PdfPCell(
								PdfStyle.createUltimateCellWithBorderWithDisableBorder(("\n"), Element.ALIGN_LEFT,
										Element.ALIGN_TOP, 8, 14f, true, 1, 1, false, true, true, false, true));
						foot_right_table.addCell(foot_bottom_right_cell33);
					}
					if (comp_cd.equals("P&G")) {
						foot_bottom_right_cell43 = new PdfPCell(
								PdfStyle.createUltimateCellWithBorderWithDisableBorder(("0.00"), Element.ALIGN_RIGHT,
										Element.ALIGN_TOP, 8, 14f, true, 1, 1, false, true, true, false, true));
						foot_right_table.addCell(foot_bottom_right_cell43);
					} else {
						if (show_amount.equals("Y")) {
							foot_bottom_right_cell43 = new PdfPCell(
									PdfStyle.createUltimateCellWithBorderWithDisableBorder((igstAmounts.toString()),
											Element.ALIGN_RIGHT, Element.ALIGN_TOP, 8, 14f, true, 1, 1, false, true,
											true, false, true));
							foot_right_table.addCell(foot_bottom_right_cell43);
						} else {
							foot_bottom_right_cell43 = new PdfPCell(
									PdfStyle.createUltimateCellWithBorderWithDisableBorder(("\n"), Element.ALIGN_RIGHT,
											Element.ALIGN_TOP, 8, 14f, true, 1, 1, false, true, true, false, true));
							foot_right_table.addCell(foot_bottom_right_cell43);
						}
					}

				} else {
					PdfPCell foot_bottom_right_cell3;

					foot_bottom_right_cell3 = new PdfPCell(
							PdfStyle.createUltimateCellWithBorderWithDisableBorder(("CGST"), Element.ALIGN_LEFT,
									Element.ALIGN_TOP, 8, 14f, true, 1, 1, false, true, true, false, true));
					foot_right_table.addCell(foot_bottom_right_cell3);

					if (comp_cd.equals("P&G")) {
						PdfPCell foot_bottom_right_cell4 = new PdfPCell(
								PdfStyle.createUltimateCellWithBorderWithDisableBorder(("0.00"), Element.ALIGN_RIGHT,
										Element.ALIGN_TOP, 8, 14f, true, 1, 1, false, true, true, false, true));
						foot_right_table.addCell(foot_bottom_right_cell4);
					} else {
						PdfPCell foot_bottom_right_cell4 = new PdfPCell(
								PdfStyle.createUltimateCellWithBorderWithDisableBorder(
										(header_Cgst.setScale(2, BigDecimal.ROUND_HALF_UP).toString()),
										Element.ALIGN_RIGHT, Element.ALIGN_TOP, 8, 14f, true, 1, 1, false, true, true,
										false, true));
						foot_right_table.addCell(foot_bottom_right_cell4);
					}

					PdfPCell foot_bottom_right_cell5 = new PdfPCell(
							PdfStyle.createUltimateCellWithBorderWithDisableBorder(("SGST"), Element.ALIGN_LEFT,
									Element.ALIGN_TOP, 8, 14f, true, 1, 1, false, true, true, false, true));
					foot_right_table.addCell(foot_bottom_right_cell5);

					if (comp_cd.equals("P&G")) {
						PdfPCell foot_bottom_right_cell6 = new PdfPCell(
								PdfStyle.createUltimateCellWithBorderWithDisableBorder(("0.00"), Element.ALIGN_RIGHT,
										Element.ALIGN_TOP, 8, 14f, true, 1, 1, false, true, true, false, true));
						foot_right_table.addCell(foot_bottom_right_cell6);
					} else {
						PdfPCell foot_bottom_right_cell6 = new PdfPCell(
								PdfStyle.createUltimateCellWithBorderWithDisableBorder(
										(header_Sgst.setScale(2, BigDecimal.ROUND_HALF_UP).toString()),
										Element.ALIGN_RIGHT, Element.ALIGN_TOP, 8, 14f, true, 1, 1, false, true, true,
										false, true));
						foot_right_table.addCell(foot_bottom_right_cell6);
					}

				}

				if (companyCode.contains("P&G") || companyCode.contains("PAL")) {

					PdfPCell foot_bottom_right_cell73 = new PdfPCell(
							PdfStyle.createUltimateCellWithBorderWithDisableBorder((" "), Element.ALIGN_LEFT,
									Element.ALIGN_TOP, 8, 14f, true, 1, 1, false, true, true, false, true));
					foot_right_table.addCell(foot_bottom_right_cell73);

					PdfPCell foot_bottom_right_cell83 = new PdfPCell(
							PdfStyle.createUltimateCellWithBorderWithDisableBorder((" "), Element.ALIGN_RIGHT,
									Element.ALIGN_TOP, 8, 14f, true, 1, 1, false, true, true, false, true));
					foot_right_table.addCell(foot_bottom_right_cell83);
				} else {
					if (show_amount.equals("Y")) {
						PdfPCell foot_bottom_right_cell73 = new PdfPCell(PdfStyle
								.createUltimateCellWithBorderWithDisableBorder(("Round Off (+/-)"), Element.ALIGN_LEFT,
										Element.ALIGN_TOP, 8, 14f, true, 1, 1, false, true, true, false, true));
						foot_right_table.addCell(foot_bottom_right_cell73);

						PdfPCell foot_bottom_right_cell83 = new PdfPCell(
								PdfStyle.createUltimateCellWithBorderWithDisableBorder(
										(round_off.setScale(2, BigDecimal.ROUND_HALF_UP).toString()),
										Element.ALIGN_RIGHT, Element.ALIGN_TOP, 8, 14f, true, 1, 1, false, true, true,
										false, true));
						foot_right_table.addCell(foot_bottom_right_cell83);
					} else {
						PdfPCell foot_bottom_right_cell73 = new PdfPCell(
								PdfStyle.createUltimateCellWithBorderWithDisableBorder(("\n"), Element.ALIGN_LEFT,
										Element.ALIGN_TOP, 8, 14f, true, 1, 1, false, true, true, false, true));
						foot_right_table.addCell(foot_bottom_right_cell73);

						PdfPCell foot_bottom_right_cell83 = new PdfPCell(
								PdfStyle.createUltimateCellWithBorderWithDisableBorder(("\n"), Element.ALIGN_RIGHT,
										Element.ALIGN_TOP, 8, 14f, true, 1, 1, false, true, true, false, true));
						foot_right_table.addCell(foot_bottom_right_cell83);
					}
				}

				// if Total condition without colour for Palson PAL -- change by abhishek

				if (comp_cd.equals("PAL")) {

					PdfPCell foot_bottom_right_cell7 = new PdfPCell(
							PdfStyle.createUltimateCellWithBorderWithDisableBorder(("Total Amount"), Element.ALIGN_LEFT,
									Element.ALIGN_TOP, 8, 7f, true, 1, 1, false, true, true, false, true));
					foot_right_table.addCell(foot_bottom_right_cell7);
				}

				else {
					if (show_amount.equals("Y")) {
						PdfPCell foot_bottom_right_cell7 = new PdfPCell(PdfStyle
								.createUltimateCellWithBorderWithDisableBorder(("Total Amount"), Element.ALIGN_LEFT,
										Element.ALIGN_TOP, 8, 14f, true, 1, 1, false, true, true, false, true));
						foot_bottom_right_cell7.setGrayFill(0.7f);
						foot_right_table.addCell(foot_bottom_right_cell7); // end hear
					} else {
						PdfPCell foot_bottom_right_cell7 = new PdfPCell(
								PdfStyle.createUltimateCellWithBorderWithDisableBorder(("\n"), Element.ALIGN_LEFT,
										Element.ALIGN_TOP, 8, 14f, true, 1, 1, false, true, true, false, true));
						foot_bottom_right_cell7.setGrayFill(0.7f);
						foot_right_table.addCell(foot_bottom_right_cell7);
					}
				}

				if (companyCode.equals("P&G")) {

					PdfPCell foot_bottom_right_cell8 = new PdfPCell(
							PdfStyle.createUltimateCellWithBorderWithDisableBorder(("0.00"), Element.ALIGN_RIGHT,
									Element.ALIGN_TOP, 8, 14f, true, 1, 1, false, true, true, false, true));
					foot_bottom_right_cell8.setGrayFill(0.7f);
					foot_right_table.addCell(foot_bottom_right_cell8);
				}

				if (companyCode.equals("PAL")) {

					PdfPCell foot_bottom_right_cell8 = new PdfPCell(
							PdfStyle.createUltimateCellWithBorderWithDisableBorder(("0.00"), Element.ALIGN_RIGHT,
									Element.ALIGN_TOP, 8, 10f, true, 1, 1, false, true, true, false, true));
					foot_right_table.addCell(foot_bottom_right_cell8);

				} else {
					if (show_amount.equals("Y")) {
						PdfPCell foot_bottom_right_cell8 = new PdfPCell(
								PdfStyle.createUltimateCellWithBorderWithDisableBorder(
										(final_total.setScale(2, BigDecimal.ROUND_HALF_UP).toString()),
										Element.ALIGN_RIGHT, Element.ALIGN_TOP, 8, 14f, true, 1, 1, false, true, true,
										false, true));
						foot_bottom_right_cell8.setGrayFill(0.7f);
						foot_right_table.addCell(foot_bottom_right_cell8);
					} else {
						PdfPCell foot_bottom_right_cell8 = new PdfPCell(
								PdfStyle.createUltimateCellWithBorderWithDisableBorder(("\n"), Element.ALIGN_RIGHT,
										Element.ALIGN_TOP, 8, 14f, true, 1, 1, false, true, true, false, true));
						foot_bottom_right_cell8.setGrayFill(0.7f);
						foot_right_table.addCell(foot_bottom_right_cell8);
					}
				}

				if (companyCode.equals("PAL")) {
					PdfPCell foot_bottom_right_cell7_ = new PdfPCell(
							PdfStyle.createUltimateCellWithBorderWithDisableBorder((" "), Element.ALIGN_LEFT,
									Element.ALIGN_TOP, 8, 7f, true, 1, 1, false, true, true, false, true));
					foot_right_table.addCell(foot_bottom_right_cell7_);
					PdfPCell foot_bottom_right_cell8_ = new PdfPCell(
							PdfStyle.createUltimateCellWithBorderWithDisableBorder((" "), Element.ALIGN_LEFT,
									Element.ALIGN_TOP, 8, 10f, true, 1, 1, false, true, true, false, true));
					foot_right_table.addCell(foot_bottom_right_cell8_);
				}

			} else {
				PdfPCell foot_bottom_right_cell1 = new PdfPCell(
						PdfStyle.createUltimateCellWithBorderWithDisableBorder((""), Element.ALIGN_LEFT,
								Element.ALIGN_TOP, 8, 14f, true, 1, 1, false, true, true, false, true));
				foot_right_table.addCell(foot_bottom_right_cell1);
				PdfPCell foot_bottom_right_cell2 = new PdfPCell(
						PdfStyle.createUltimateCellWithBorderWithDisableBorder((""), Element.ALIGN_RIGHT,
								Element.ALIGN_TOP, 8, 14f, true, 1, 1, false, true, true, false, true));
				foot_right_table.addCell(foot_bottom_right_cell2);

				if (gst_doc_type.contains("INTER")) {
					PdfPCell foot_bottom_right_cell33 = new PdfPCell(
							PdfStyle.createUltimateCellWithBorderWithDisableBorder((""), Element.ALIGN_LEFT,
									Element.ALIGN_TOP, 8, 14f, true, 1, 1, false, true, true, false, true));
					foot_right_table.addCell(foot_bottom_right_cell33);
					PdfPCell foot_bottom_right_cell43 = new PdfPCell(
							PdfStyle.createUltimateCellWithBorderWithDisableBorder((""), Element.ALIGN_RIGHT,
									Element.ALIGN_TOP, 8, 14f, true, 1, 1, false, true, true, false, true));
					foot_right_table.addCell(foot_bottom_right_cell43);
				} else if (gst_doc_type.contains("INTRA")) {
					PdfPCell foot_bottom_right_cell3 = new PdfPCell(
							PdfStyle.createUltimateCellWithBorderWithDisableBorder((""), Element.ALIGN_LEFT,
									Element.ALIGN_TOP, 8, 14f, true, 1, 1, false, true, true, false, true));
					foot_right_table.addCell(foot_bottom_right_cell3);
					PdfPCell foot_bottom_right_cell4 = new PdfPCell(
							PdfStyle.createUltimateCellWithBorderWithDisableBorder((""), Element.ALIGN_RIGHT,
									Element.ALIGN_TOP, 8, 14f, true, 1, 1, false, true, true, false, true));
					foot_right_table.addCell(foot_bottom_right_cell4);
					PdfPCell foot_bottom_right_cell5 = new PdfPCell(
							PdfStyle.createUltimateCellWithBorderWithDisableBorder((""), Element.ALIGN_LEFT,
									Element.ALIGN_TOP, 8, 14f, true, 1, 1, false, true, true, false, true));
					foot_right_table.addCell(foot_bottom_right_cell5);
					PdfPCell foot_bottom_right_cell6 = new PdfPCell(
							PdfStyle.createUltimateCellWithBorderWithDisableBorder((""), Element.ALIGN_RIGHT,
									Element.ALIGN_TOP, 8, 14f, true, 1, 1, false, true, true, false, true));
					foot_right_table.addCell(foot_bottom_right_cell6);
				}

				PdfPCell foot_bottom_right_cell73 = new PdfPCell(
						PdfStyle.createUltimateCellWithBorderWithDisableBorder((""), Element.ALIGN_LEFT,
								Element.ALIGN_TOP, 8, 14f, true, 1, 1, false, true, true, false, true));
				foot_right_table.addCell(foot_bottom_right_cell73);
				PdfPCell foot_bottom_right_cell83 = new PdfPCell(
						PdfStyle.createUltimateCellWithBorderWithDisableBorder((""), Element.ALIGN_RIGHT,
								Element.ALIGN_TOP, 8, 14f, true, 1, 1, false, true, true, false, true));
				foot_right_table.addCell(foot_bottom_right_cell83);

				PdfPCell foot_bottom_right_cell7 = new PdfPCell(
						PdfStyle.createUltimateCellWithBorderWithDisableBorder((""), Element.ALIGN_LEFT,
								Element.ALIGN_TOP, 8, 14f, true, 1, 1, false, true, true, false, true));
				foot_bottom_right_cell7.setGrayFill(0.7f);
				foot_right_table.addCell(foot_bottom_right_cell7); // end hear
				PdfPCell foot_bottom_right_cell8 = new PdfPCell(
						PdfStyle.createUltimateCellWithBorderWithDisableBorder((""), Element.ALIGN_RIGHT,
								Element.ALIGN_TOP, 8, 14f, true, 1, 1, false, true, true, false, true));
				foot_bottom_right_cell8.setGrayFill(0.7f);
				foot_right_table.addCell(foot_bottom_right_cell8);

			}

			// end hear
			// footer bottom2
			PdfPTable foot_bottom_table2 = new PdfPTable(3);
			foot_bottom_table2.setWidthPercentage(100);
			foot_bottom_table2.setWidths(new float[] { 3.33f, 2.17f, 4.5f });

			// String company_name = (String) context.getAttribute("company_name");

			PdfPCell foot_bottom_cell02 = new PdfPCell(PdfStyle.createUltimateCellWithBorderWithDisableBorder(
					("Transporter Details"), Element.ALIGN_CENTER, Element.ALIGN_TOP, 9, 14f, true, 1, 1, false, true,
					true, false, true));
			foot_bottom_table2.addCell(foot_bottom_cell02);

			PdfPCell foot_bottom_cell03 = new PdfPCell(PdfStyle.createUltimateCellWithBorderWithDisableBorder((""),
					Element.ALIGN_LEFT, Element.ALIGN_TOP, 8, 14f, true, 1, 1, false, true, true, false, true));
			foot_bottom_table2.addCell(foot_bottom_cell03);

			PdfPCell foot_bottom_cell04 = null;
			if (frm_challan.contains("PIP")) {
				if (comp_cd.equalsIgnoreCase("PFZ")) {
					foot_bottom_cell04 = new PdfPCell(PdfStyle.createUltimateCellWithBorderWithDisableBorder(
							("For Pfizer Products India Private Limited."), Element.ALIGN_CENTER, Element.ALIGN_TOP, 9,
							14f, true, 1, 1, false, true, true, false, true));
				} else { // adding new footer indicator
					if (footer_ind && comp_cd.equalsIgnoreCase("NIL") || comp_cd.equalsIgnoreCase("NHP")) // new check
																											// for
																											// footer
																											// indicator
																											// and
																											// companey
					// code
					{
						foot_bottom_cell04 = new PdfPCell(PdfStyle.createUltimateCellWithBorderWithDisableBorder(
								("For " + company_name), Element.ALIGN_CENTER, Element.ALIGN_TOP, 9, 14f, true, 1, 1,
								false, true, true, false, true));
					} else {
						foot_bottom_cell04 = new PdfPCell(
								PdfStyle.createUltimateCellWithBorderWithDisableBorder((" "), Element.ALIGN_CENTER,
										Element.ALIGN_TOP, 9, 14f, true, 1, 1, false, true, true, false, true));
					}
				}
				foot_bottom_table2.addCell(foot_bottom_cell04);
			} else {
				if (footer_ind && comp_cd.equalsIgnoreCase("NIL") || comp_cd.equalsIgnoreCase("NHP")) { // new check for
																										// footer
																										// indicator and
																										// companey code
					foot_bottom_cell04 = new PdfPCell(PdfStyle.createUltimateCellWithBorderWithDisableBorder(
							("For " + company_name), Element.ALIGN_CENTER, Element.ALIGN_TOP, 9, 14f, true, 1, 1, false,
							true, true, false, true));
				} else if (comp_cd.equalsIgnoreCase("P&G") || comp_cd.equalsIgnoreCase("PAL")) {
					foot_bottom_cell04 = new PdfPCell(PdfStyle.createUltimateCellWithBorderWithDisableBorder(
							("For " + company_name), Element.ALIGN_CENTER, Element.ALIGN_TOP, 9, 14f, true, 1, 1, false,
							true, true, false, true));

				} else {
					foot_bottom_cell04 = new PdfPCell(
							PdfStyle.createUltimateCellWithBorderWithDisableBorder((" "), Element.ALIGN_CENTER,
									Element.ALIGN_TOP, 9, 14f, true, 1, 1, false, true, true, false, true));
				}

				foot_bottom_table2.addCell(foot_bottom_cell04);

			}

			PdfPCell foot_bottom_cell21 = new PdfPCell(
					PdfStyle.createUltimateCellWithBorderWithDisableBorder(("Transporter Name :" + dsp_transporter),
							Element.ALIGN_LEFT, Element.ALIGN_TOP, 8, 14f, true, 1, 1, false, true, true, false, true));
			foot_bottom_table2.addCell(foot_bottom_cell21);
			PdfPCell foot_bottom_cell22 = new PdfPCell(PdfStyle.createUltimateCellWithBorderWithDisableBorder((""),
					Element.ALIGN_CENTER, Element.ALIGN_TOP, 8, 14f, true, 1, 1, false, true, true, false, true));
			foot_bottom_table2.addCell(foot_bottom_cell22);
			PdfPCell foot_bottom_cell32 = new PdfPCell(PdfStyle.createUltimateCellWithBorderWithDisableBorder((""),
					Element.ALIGN_CENTER, Element.ALIGN_TOP, 8, 14f, true, 1, 1, false, false, true, false, true));
			foot_bottom_table2.addCell(foot_bottom_cell32);

			PdfPCell foot_bottom_cell06 = new PdfPCell(
					PdfStyle.createUltimateCellWithBorderWithDisableBorder(("LR No:" + ship_doc_no), Element.ALIGN_LEFT,
							Element.ALIGN_TOP, 8, 14f, true, 1, 1, false, true, true, false, true));
			foot_bottom_table2.addCell(foot_bottom_cell06);
			PdfPCell foot_bottom_cell42 = new PdfPCell(PdfStyle.createUltimateCellWithBorderWithDisableBorder(
					("LR Date : " + (date != null ? Utility.getRptDateFormat(date) : "")), Element.ALIGN_LEFT,
					Element.ALIGN_TOP, 8, 14f, true, 1, 1, false, true, true, false, true));
			foot_bottom_table2.addCell(foot_bottom_cell42);
			PdfPCell foot_bottom_cell07 = new PdfPCell(PdfStyle.createUltimateCellWithBorderWithDisableBorder((""),
					Element.ALIGN_LEFT, Element.ALIGN_TOP, 8, 14f, true, 1, 1, false, false, true, false, true));
			foot_bottom_table2.addCell(foot_bottom_cell07);

			PdfPCell foot_bottom_cell52 = null;
			System.out.println("dsp_lorry_no::" + dsp_lorry_no);
			if (comp_cd.equalsIgnoreCase("NIL") || comp_cd.equalsIgnoreCase("NHP")) {
				foot_bottom_cell52 = new PdfPCell(PdfStyle.createUltimateCellWithBorderWithDisableBorder(
						(dsp_lorry_no == null ? "E-Way Bill No: " : "E-Way Bill No: " + dsp_lorry_no),
						Element.ALIGN_LEFT, Element.ALIGN_TOP, 8, 14f, true, 1, 1, false, true, true, false, true));
			} else {
				foot_bottom_cell52 = new PdfPCell(
						PdfStyle.createUltimateCellWithBorderWithDisableBorder(("Way Bill No:"), Element.ALIGN_LEFT,
								Element.ALIGN_TOP, 8, 14f, true, 1, 1, false, true, true, false, true));
			}

			foot_bottom_table2.addCell(foot_bottom_cell52);
			PdfPCell foot_bottom_cell62 = new PdfPCell(
					PdfStyle.createUltimateCellWithBorderWithDisableBorder(("Date :"), Element.ALIGN_LEFT,
							Element.ALIGN_TOP, 8, 14f, true, 1, 1, false, true, true, false, true));
			foot_bottom_table2.addCell(foot_bottom_cell62);
			PdfPCell foot_bottom_cell08 = new PdfPCell(PdfStyle.createUltimateCellWithBorderWithDisableBorder((""),
					Element.ALIGN_LEFT, Element.ALIGN_TOP, 8, 14f, true, 1, 1, false, false, true, false, true));
			foot_bottom_table2.addCell(foot_bottom_cell08);

			PdfPCell foot_bottom_cell72 = new PdfPCell(
					PdfStyle.createUltimateCellWithBorderWithDisableBorder(("No Of Cases :" + " " + cases),
							Element.ALIGN_LEFT, Element.ALIGN_TOP, 8, 14f, true, 1, 1, false, true, true, false, true));
			foot_bottom_table2.addCell(foot_bottom_cell72);
			PdfPCell foot_bottom_cell82 = new PdfPCell(
					PdfStyle.createUltimateCellWithBorderWithDisableBorder(("Weight :" + "  " + weigh),
							Element.ALIGN_LEFT, Element.ALIGN_TOP, 8, 14f, true, 1, 1, false, true, true, false, true));
			foot_bottom_table2.addCell(foot_bottom_cell82);
			PdfPCell foot_bottom_cell09 = new PdfPCell(PdfStyle.createUltimateCellWithBorderWithDisableBorder((""),
					Element.ALIGN_LEFT, Element.ALIGN_TOP, 8, 14f, true, 1, 1, false, false, true, false, true));
			foot_bottom_table2.addCell(foot_bottom_cell09);

			PdfPCell foot_bottom_cell92 = new PdfPCell(
					PdfStyle.createUltimateCellWithBorderWithDisableBorder(("Remarks : " + dsp_challan_msg),
							Element.ALIGN_LEFT, Element.ALIGN_TOP, 8, 14f, true, 1, 1, false, true, true, false, true));
			foot_bottom_table2.addCell(foot_bottom_cell92);
			PdfPCell foot_bottom_cell10 = new PdfPCell(PdfStyle.createUltimateCellWithBorderWithDisableBorder((""),
					Element.ALIGN_LEFT, Element.ALIGN_TOP, 8, 14f, true, 1, 1, false, false, true, true, true));
			foot_bottom_table2.addCell(foot_bottom_cell10);

			// new changes for remove column in pdf cell by vivek based on footer indicator

			if (footer_ind) {
				PdfPCell foot_bottom_cell11 = new PdfPCell(PdfStyle.createUltimateCellWithBorderWithDisableBorder(
						("Authorized Signatory"), Element.ALIGN_CENTER, Element.ALIGN_TOP, 9, 14f, true, 1, 1, false,
						true, true, false, true));
				foot_bottom_table2.addCell(foot_bottom_cell11);
			} else {
				PdfPCell foot_bottom_cell11 = new PdfPCell(PdfStyle.createUltimateCellWithBorderWithDisableBorder((""),
						Element.ALIGN_CENTER, Element.ALIGN_TOP, 9, 14f, true, 1, 1, false, true, true, false, true));
				foot_bottom_table2.addCell(foot_bottom_cell11);
			}

			// footer bottom3
			PdfPTable foot_bottom_table3 = new PdfPTable(1);
			foot_bottom_table3.setWidthPercentage(100);
			foot_bottom_table3.setWidths(new float[] { 10f });

			// Print changes made for novartis
			// added boxes in copies description
			PdfPCell foot_bottom_cell31 = null;
			if (comp_cd.equalsIgnoreCase("NIL") || comp_cd.equalsIgnoreCase("NHP")) {
				Paragraph p = new Paragraph();
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
				foot_bottom_cell31 = new PdfPCell(PdfStyle.createUltimateCellWithBorderWithDisableBorder(p,
						Element.ALIGN_CENTER, Element.ALIGN_TOP, 9, 14f, true, 1, 1, true, true, false, false, false));
			} else {
				foot_bottom_cell31 = new PdfPCell(PdfStyle.createUltimateCellWithBorderWithDisableBorder(
						"Copies: 1: original for consignee;   2. duplicate for transporter;   3. triplicate for cosignor;   4. quadruplicate:  book copy ",
						Element.ALIGN_CENTER, Element.ALIGN_TOP, 9, 14f, true, 1, 1, true, true, false, false, false));
			}
			foot_bottom_table3.addCell(foot_bottom_cell31);

			PdfPCell bottom_left_cell = new PdfPCell(foot_left_table);
			PdfPCell bottom_right_cell = new PdfPCell(foot_right_table);
			PdfPCell bottom_2_cell = new PdfPCell(foot_bottom_table2);
			bottom_2_cell.setColspan(2);
			PdfPCell bottom_3_cell = new PdfPCell(foot_bottom_table3);
			bottom_3_cell.setColspan(2);

			maintable.addCell(bottom_left_cell);
			maintable.addCell(bottom_right_cell);
			maintable.addCell(bottom_2_cell);
			maintable.addCell(bottom_3_cell);

			// -----------------------------------------------------------------------------------------------------------------

			if (pageflag) {
// System.out.println("pagecount : " +pagecount);
				cell = new PdfPCell(PdfStyle.createLabelCellWithoutBorderSmallFontNoBold("Page No. " + pagecount));
				cell.setBorder(Rectangle.NO_BORDER);
				cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
				cell.setVerticalAlignment(Element.ALIGN_BOTTOM);
				cell.setColspan(3);
				maintable.addCell(cell);
			}

		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("Error Occurred :" + e.getMessage());// uncomment asneeded --;
		}
		return maintable;
	}

	@Override
	public String getPreprintedDetailedChallanPrintNoGst_MDL(Integer division, Integer loc, String frm_challan,
			String to_challan, String dispatchType, String prodtype, String printtype,
			List<ViewPrePrintedDetailChallan> challans, String show_amount, String footer_signature_ind,
			String companyCode, String companyName, HttpSession session, String loc_narration,
			HttpServletRequest request) throws Exception {
		limit = 20;
		System.out.println("In get pdf ani" + "loc value:::" + loc_narration);
		String path = MedicoConstants.PDF_FILE_PATH;
		File file1 = new File(path);
		file1.mkdirs();
		comp_cd = companyCode;
		company_name = companyName;
		setPath(path);

		System.out.println("PDF Generation Started");
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
		Rectangle rect = new Rectangle(20, 28, 580, 820);
		writer.setBoxSize("header", rect);
		rect = new Rectangle(20, 2, 580, 261);
		writer.setBoxSize("footer", rect);
		int pagecount = 1;
		int pagecount2 = 1;
		
		FooterWithoutPageNo event = new FooterWithoutPageNo();
		writer.setPageEvent(event);

		String uname = this.tokenProvider.getUsernameFromRequest(request);
		String empId = this.userMasterService.getEmpIdByUsername(uname);
		writer = usermasterservice.generatePDFlock(empId, writer);
		document.open();
		document.setMarginMirroringTopBottom(false);
		boolean isnew = false;
		// boolean for_new_page = false;
		ColumnText column = new ColumnText(writer.getDirectContent());
		String imagePath = "";
		try {
			
			if (comp_cd.trim().equalsIgnoreCase("MDL")) {
				imagePath = "D:\\sg\\files\\medley-logo.jpg";
				Image image = Image.getInstance(imagePath);
				image.setAbsolutePosition(320, 720);
				image.scaleToFit(70, 70);
				document.add(image);
			}

			BigDecimal total = new BigDecimal(0);

			Set<String> challanset = new LinkedHashSet<String>();
			for (ViewPrePrintedDetailChallan challan : challans) {
				challanset.add(challan.getDsp_id().toString());
			}
			PdfPTable bodytable = null;
			boolean footer_ind = false;
			for (String num : challanset) {
				List<ViewPrePrintedDetailChallan> list_new_page = new ArrayList<ViewPrePrintedDetailChallan>();
				isnew = true;
				// for_new_page=true;
				PdfPTable maintable1 = new PdfPTable(1);
				maintable1.setWidthPercentage(100);
				PdfPCell cell = null;
				pageflag = false;
				pagecount = 1;

				BigDecimal goods_total = BigDecimal.ZERO;
				BigDecimal final_total = BigDecimal.ZERO;
				BigDecimal header_Igst = BigDecimal.ZERO;
				BigDecimal header_Cgst = BigDecimal.ZERO;
				BigDecimal header_Sgst = BigDecimal.ZERO;
				BigDecimal round_off = BigDecimal.ZERO;

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
				String gst_doc_type = "";
				String dsp_lorry_no = "";
				// int count_var=0;
				for (ViewPrePrintedDetailChallan challan : challans) {
					// System.out.println("Alloc Remark
					// "+challan.getAlloc_remark());

					if (num.equalsIgnoreCase(challan.getDsp_id().toString())) {
						if (rows >= limit) {
							// count_var++;

							if (footer_signature_ind.equalsIgnoreCase("Y")) {
								footer_ind = false;// suppress footer signature
							} else {
								footer_ind = true;// show footer signature
							}
							// System.out.println("getDoc_no " + challan.getDsp_challan_no());
							pageflag = true;
//							(ColumnText column, PdfPTable bodytable, FooterWithoutPageNo event, HashMap<String, String> printLabel, boolean isFooterData, int rows, boolean pageflag
							bodytable = newPageNoGst_MDL(column, bodytable, event, printLabel, false, rows, true,
									pagecount, total, division, loc, frm_challan, to_challan, dispatchType, prodtype,
									path, challans, show_amount, footer_ind, challan.getDsp_transporter(),
									challan.getShip_doc_no(), challan.getShip_doc_date(), challan.getCases().toString(),
									challan.getWeigh().toString(),
									challan.getAlloc_remark().length() > 600
											? challan.getAlloc_remark().substring(0, 600)
											: challan.getAlloc_remark(),
									goods_total, final_total, header_Igst, header_Cgst, header_Sgst, round_off,
									gst_doc_type, dsp_lorry_no, loc_narration, bodyTableWidth, companyCode);

							document.newPage();
							
							if (comp_cd.trim().equalsIgnoreCase("MDL")) {
								imagePath = "D:\\sg\\files\\medley-logo.jpg";
								Image image = Image.getInstance(imagePath);
								image.setAbsolutePosition(320, 720);
								image.scaleToFit(70, 70);
								document.add(image);
							}
							
							
							pagecount++;
							rows = 0;

						}
						if (isnew) {
							footerNoGst = challan;
							maintable1.addCell(PdfStyle.createLabelCellWithoutBorderSmallFontNoBold(""));
							hcell = createHeader_mdl(challan, pagecount, division, loc, frm_challan, to_challan,
									dispatchType, prodtype, path, show_amount, companyName, session);
							
							cell = new PdfPCell(hcell);
							cell.setBorder(1);
							cell.setHorizontalAlignment(Element.ALIGN_CENTER);
							cell.setPadding(0);
							maintable1.addCell(cell);
							event.setHeader(maintable1);
							isnew = false;
							list_new_page.add(challan);
							

						}

						if (rows == 0) { // changes made for unichem testing
							// need to recheck 2 aug 2017
//							bodytable.addCell(PdfStyle.createValueCellWithHeight((""), false, true, 18f));
//							bodytable.addCell(PdfStyle.createValueCellWithHeight((""), false, true, 18f));
//							bodytable.addCell(PdfStyle.createValueCellWithHeight((""), false, true, 18f));
//							bodytable.addCell(PdfStyle.createValueCellWithHeight((""), true, true, 18f));
//							bodytable.addCell(PdfStyle.createValueCellWithHeight((""), true, true, 18f));
//							bodytable.addCell(PdfStyle.createValueCellWithHeight((""), true, true, 18f));


						}
						
						
						bodytable.addCell(PdfStyle.createUltimateCellWithBorderWithDisableBorder(
								(challan.getProduct_desc()), Element.ALIGN_LEFT, Element.ALIGN_TOP, 8, 18f, false, 1, 1,
								false, false, false, true, false));
						bodytable.addCell(PdfStyle.createUltimateCellWithBorderWithDisableBorder(
								(challan.getHsn_code()), Element.ALIGN_LEFT, Element.ALIGN_TOP, 8, 18f, false, 1, 1,
								false, false, false, true, false));
						bodytable.addCell(PdfStyle.createUltimateCellWithBorderWithDisableBorder(
								(challan.getBatch_no()), Element.ALIGN_LEFT, Element.ALIGN_TOP, 8, 18f, false, 1, 1,
								false, false, false, true, false));
						bodytable.addCell(PdfStyle.createUltimateCellWithBorderWithDisableBorder(
								(challan.getExpiry_date()), Element.ALIGN_CENTER, Element.ALIGN_TOP, 8, 18f, false, 1,
								1, false, false, false, true, false));
						bodytable.addCell(PdfStyle.createUltimateCellWithBorderWithDisableBorder(
								(challan.getTotal_qty().toString()), Element.ALIGN_CENTER, Element.ALIGN_TOP, 8, 18f,
								false, 1, 1, false, false, false, true, false));
						bodytable.addCell(PdfStyle.createUltimateCellWithBorderWithDisableBorder(
								(challan.getValue() == null ? BigDecimal.ZERO.setScale(2, RoundingMode.HALF_UP)
										: challan.getValue().setScale(2, RoundingMode.HALF_UP)).toString(),
								Element.ALIGN_RIGHT, Element.ALIGN_TOP, 8, 18f, false, 1, 1, false, false, false, true,
								false));
						
				

				goods_total = goods_total.add(challan.getValue());

						rows++;
						dtp = "";
						ship_doc_no = "";
						date = "";
						cases = "";
						weigh = "";
						dsp_challan_msg = "";
						dsp_lorry_no = "";

						dtp = challan.getDsp_transporter();
						ship_doc_no = challan.getShip_doc_no();
						date = challan.getShip_doc_date();
						cases = challan.getCases().setScale(2).toString();
						weigh = challan.getWeigh().toString();
						// dsp_challan_msg is the remark that is currently
						// printed.
						dsp_challan_msg = challan.getAlloc_remark().length() > 600
								? challan.getAlloc_remark().substring(0, 600)
								: challan.getAlloc_remark();
						dsp_lorry_no = challan.getDsp_lorry_no();
					}
					
					
					
				}

				footer_ind = true;
				// System.out.println("Outside rows>=limit");
				newPageNoGst_MDL(column, bodytable, event, printLabel, true, rows, pageflag, pagecount, total, division,
						loc, frm_challan, to_challan, dispatchType, prodtype, path, list_new_page, show_amount,
						footer_ind, dtp, ship_doc_no, date, cases, weigh, dsp_challan_msg, goods_total, final_total,
						header_Igst, header_Cgst, header_Sgst, round_off, gst_doc_type, dsp_lorry_no, loc_narration,
						bodyTableWidth, companyCode);
				
				pagecount2++;
				
				if (comp_cd.trim().equalsIgnoreCase("MDL")) {
					imagePath = "D:\\sg\\files\\medley-logo.jpg";
					Image image = Image.getInstance(imagePath);
					image.setAbsolutePosition(320, 720);
					image.scaleToFit(70, 70);
					document.add(image);
				}
				
				document.newPage();
				
				
			
			}

		} catch (Exception e) {
			System.out.println("Error Occurred :" + e.getMessage());// uncomment asneeded --;
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
				System.out.println("Error Occurred :" + e.getMessage());// uncomment asneeded --;
			}
		}
		return filename;

	


	
	}

	
	private PdfPTable newPageNoGst_MDL(ColumnText column, PdfPTable bodytable, FooterWithoutPageNo event,
			HashMap<String, String> printLabel, boolean isFooterData, int rows, boolean pageflag, int pagecount,
			BigDecimal total, int division, Integer loc, String frm_challan, String to_challan, String dispatchType,
			String prodtype, String path, List<ViewPrePrintedDetailChallan> challan, String show_amount,
			boolean footer_ind, String dsp_transporter, String ship_doc_no, String date, String cases, String weigh,
			String dsp_challan_msg, BigDecimal goods_total, BigDecimal final_total, BigDecimal header_Igst,
			BigDecimal header_Cgst, BigDecimal header_Sgst, BigDecimal round_off, String gst_doc_type,
			String dsp_lorry_no, String loc_narration, float[] bodyTableWidth, String companyCode) throws DocumentException {

		PdfPCell cell = null;
		try {
		
			if (limit - (rows) > 0) {
				for (int i = 0; i < (limit - rows); i++) {
					// System.out.println("printing row:::"+i);
					bodytable.addCell(PdfStyle.createValueCellWithHeight((""), true, true, 18f));
					bodytable.addCell(PdfStyle.createValueCellWithHeight((""), true, true, 18f));
					bodytable.addCell(PdfStyle.createValueCellWithHeight((""), true, true, 18f));
					bodytable.addCell(PdfStyle.createValueCellWithHeight((""), true, true, 18f));
					bodytable.addCell(PdfStyle.createValueCellWithHeight((""), true, true, 18f));
					bodytable.addCell(PdfStyle.createValueCellWithHeight((""), true, true, 18f));
				}
			}

			cell = new PdfPCell(PdfStyle.createSingleLine());
			cell.setColspan(6);
			bodytable.addCell(cell);

			column.addElement(bodytable);
			column.setSimpleColumn(20, 0, 580, 531);
			column.go();

			bodytable = new PdfPTable(6);
			bodytable.setWidthPercentage(100);
			bodytable.setWidths(bodyTableWidth);	

			event.setFooter(createFooterNoGst_MDL(printLabel, isFooterData, pageflag, pagecount, total, division, loc,
					frm_challan, to_challan, dispatchType, prodtype, path, challan, show_amount, footer_ind,
					dsp_transporter, ship_doc_no, date, cases, weigh, dsp_challan_msg, goods_total, final_total,
					header_Igst, header_Cgst, header_Sgst, round_off, gst_doc_type, dsp_lorry_no, loc_narration,
					challan.get(0).getLoc_add2(), bodyTableWidth));

		} catch (DocumentException e) {
			throw e;
		}
		return bodytable;
	}

	private PdfPTable createFooterNoGst_MDL(HashMap<String, String> printLabel, boolean isFooterData, boolean pageflag,
			int pagecount, BigDecimal total, int division, Integer loc, String frm_challan, String to_challan,
			String dispatchType, String prodtype, String path, List<ViewPrePrintedDetailChallan> challan,
			String show_amount, boolean footer_ind, String dsp_transporter, String ship_doc_no, String date,
			String cases, String weigh, String dsp_challan_msg, BigDecimal goods_total, BigDecimal final_total,
			BigDecimal header_Igst, BigDecimal header_Cgst, BigDecimal header_Sgst, BigDecimal round_off,
			String gst_doc_type, String dsp_lorry_no, String loc_narration, String loc_addr2, float[] bodyTableWidth) {
		
		PdfPTable maintable = new PdfPTable(2);
		
		maintable.setSpacingBefore(90f);
		
		try {

			PdfPCell cell = null;
			cell = new PdfPCell();

//Testing
			String narrration = printService.getLocationNarrationByLocId(Long.parseLong(loc.toString()));
			maintable.setWidthPercentage(100);
			maintable.setWidths(new float[] { 5.5f, 4.5f });

			// footer left cell
			PdfPTable foot_left_table = new PdfPTable(1);
			foot_left_table.setWidthPercentage(100);
			foot_left_table.setWidths(new float[] { 10f });

			PdfPCell foot_bottom_cell1 = new PdfPCell(
					PdfStyle.createUltimateCellWithBorderWithDisableBorder((narrration), Element.ALIGN_LEFT,
							Element.ALIGN_TOP, 9, 14f, true, 1, 1, false, true, true, false, true));
			foot_left_table.addCell(foot_bottom_cell1);

//    PdfPCell foot_bottom_cell1 = new PdfPCell(
//    PdfStyle.createUltimateCellWithBorderWithDisableBorder(" ", Element.ALIGN_LEFT,
//    Element.ALIGN_TOP, 9, 14f, true, 1, 1, false, true, true, false, true));
//    foot_left_table.addCell(foot_bottom_cell1);

			// footer right cell
			PdfPTable foot_right_table = new PdfPTable(2);
			foot_right_table.setWidthPercentage(100);
			foot_right_table.setWidths(new float[] { 5.55f, 4.45f });

			String goodsOrTotal = "";
			if (comp_cd.equalsIgnoreCase("NIL") || comp_cd.equalsIgnoreCase("NHP")
					|| comp_cd.trim().equalsIgnoreCase("SER") || comp_cd.trim().equalsIgnoreCase(VET)
					|| comp_cd.trim().equalsIgnoreCase("MDL") || comp_cd.trim().equalsIgnoreCase("SNK"))
				goodsOrTotal = "Total";
			else
				goodsOrTotal = "Goods";
			// new changes for remove column in pdf cell by vivek based on footer indicator

//   if(footer_ind && comp_cd.equalsIgnoreCase("NIL")){
			if (footer_ind && comp_cd.equalsIgnoreCase("PFZ")) {
				PdfPCell foot_bottom_right_cell1 = new PdfPCell(PdfStyle.createUltimateCellWithBorderWithDisableBorder(
						(goodsOrTotal + " Value"), Element.ALIGN_LEFT, Element.ALIGN_TOP, 8, 14f, true, 1, 1, false,
						true, true, false, true));
				foot_right_table.addCell(foot_bottom_right_cell1);
				PdfPCell foot_bottom_right_cell2 = new PdfPCell(PdfStyle.createUltimateCellWithBorderWithDisableBorder(
						(goods_total.setScale(2, BigDecimal.ROUND_HALF_UP).toString()), Element.ALIGN_RIGHT,
						Element.ALIGN_TOP, 8, 14f, true, 1, 1, false, true, true, false, true));
				foot_right_table.addCell(foot_bottom_right_cell2);

				if (gst_doc_type.contains("INTER")) {
					PdfPCell foot_bottom_right_cell33 = new PdfPCell(
							PdfStyle.createUltimateCellWithBorderWithDisableBorder(("IGST"), Element.ALIGN_LEFT,
									Element.ALIGN_TOP, 8, 14f, true, 1, 1, false, true, true, false, true));
					foot_right_table.addCell(foot_bottom_right_cell33);
					PdfPCell foot_bottom_right_cell43 = new PdfPCell(
							PdfStyle.createUltimateCellWithBorderWithDisableBorder(
									(header_Igst.setScale(2, BigDecimal.ROUND_HALF_UP).toString()), Element.ALIGN_RIGHT,
									Element.ALIGN_TOP, 8, 14f, true, 1, 1, false, true, true, false, true));
					foot_right_table.addCell(foot_bottom_right_cell43);
				} else if (gst_doc_type.contains("INTRA")) {
					PdfPCell foot_bottom_right_cell3 = new PdfPCell(
							PdfStyle.createUltimateCellWithBorderWithDisableBorder(("CGST"), Element.ALIGN_LEFT,
									Element.ALIGN_TOP, 8, 14f, true, 1, 1, false, true, true, false, true));
					foot_right_table.addCell(foot_bottom_right_cell3);
					PdfPCell foot_bottom_right_cell4 = new PdfPCell(
							PdfStyle.createUltimateCellWithBorderWithDisableBorder(
									(header_Cgst.setScale(2, BigDecimal.ROUND_HALF_UP).toString()), Element.ALIGN_RIGHT,
									Element.ALIGN_TOP, 8, 14f, true, 1, 1, false, true, true, false, true));
					foot_right_table.addCell(foot_bottom_right_cell4);
					PdfPCell foot_bottom_right_cell5 = new PdfPCell(
							PdfStyle.createUltimateCellWithBorderWithDisableBorder(("SGST"), Element.ALIGN_LEFT,
									Element.ALIGN_TOP, 8, 14f, true, 1, 1, false, true, true, false, true));
					foot_right_table.addCell(foot_bottom_right_cell5);
					PdfPCell foot_bottom_right_cell6 = new PdfPCell(
							PdfStyle.createUltimateCellWithBorderWithDisableBorder(
									(header_Sgst.setScale(2, BigDecimal.ROUND_HALF_UP).toString()), Element.ALIGN_RIGHT,
									Element.ALIGN_TOP, 8, 14f, true, 1, 1, false, true, true, false, true));
					foot_right_table.addCell(foot_bottom_right_cell6);
				}

				PdfPCell foot_bottom_right_cell73 = new PdfPCell(
						PdfStyle.createUltimateCellWithBorderWithDisableBorder(("Round Off (+/-)"), Element.ALIGN_LEFT,
								Element.ALIGN_TOP, 8, 14f, true, 1, 1, false, true, true, false, true));
				foot_right_table.addCell(foot_bottom_right_cell73);
				PdfPCell foot_bottom_right_cell83 = new PdfPCell(PdfStyle.createUltimateCellWithBorderWithDisableBorder(
						(round_off.setScale(2, BigDecimal.ROUND_HALF_UP).toString()), Element.ALIGN_RIGHT,
						Element.ALIGN_TOP, 8, 14f, true, 1, 1, false, true, true, false, true));
				foot_right_table.addCell(foot_bottom_right_cell83);

				PdfPCell foot_bottom_right_cell7 = new PdfPCell(
						PdfStyle.createUltimateCellWithBorderWithDisableBorder(("Total Amount"), Element.ALIGN_LEFT,
								Element.ALIGN_TOP, 8, 14f, true, 1, 1, false, true, true, false, true));
				foot_bottom_right_cell7.setGrayFill(0.7f);
				foot_right_table.addCell(foot_bottom_right_cell7); // end hear
				PdfPCell foot_bottom_right_cell8 = new PdfPCell(PdfStyle.createUltimateCellWithBorderWithDisableBorder(
						(final_total.setScale(2, BigDecimal.ROUND_HALF_UP).toString()), Element.ALIGN_RIGHT,
						Element.ALIGN_TOP, 8, 14f, true, 1, 1, false, true, true, false, true));
				foot_bottom_right_cell8.setGrayFill(0.7f);
				foot_right_table.addCell(foot_bottom_right_cell8);

			} else if (footer_ind && (comp_cd.trim().equalsIgnoreCase("SER") || comp_cd.trim().equalsIgnoreCase(VET)
					|| comp_cd.trim().equalsIgnoreCase("MDL") || comp_cd.trim().equalsIgnoreCase("SNK"))) {
				PdfPCell foot_bottom_right_cell1 = new PdfPCell(PdfStyle.createUltimateCellWithBorderWithDisableBorder(
						(goodsOrTotal + " Value"), Element.ALIGN_LEFT, Element.ALIGN_TOP, 8, 14f, true, 1, 1, false,
						true, true, false, true));
				foot_right_table.addCell(foot_bottom_right_cell1);
				PdfPCell foot_bottom_right_cell2 = new PdfPCell(PdfStyle.createUltimateCellWithBorderWithDisableBorder(
						(goods_total.setScale(2, BigDecimal.ROUND_HALF_UP).toString()), Element.ALIGN_RIGHT,
						Element.ALIGN_TOP, 8, 14f, true, 1, 1, false, true, true, false, true));
				foot_right_table.addCell(foot_bottom_right_cell2);

				if (gst_doc_type.contains("INTER")) {
					PdfPCell foot_bottom_right_cell33 = new PdfPCell(
							PdfStyle.createUltimateCellWithBorderWithDisableBorder((""), Element.ALIGN_LEFT,
									Element.ALIGN_TOP, 8, 14f, true, 1, 1, false, true, true, false, true));
					foot_right_table.addCell(foot_bottom_right_cell33);
					PdfPCell foot_bottom_right_cell43 = new PdfPCell(
							PdfStyle.createUltimateCellWithBorderWithDisableBorder((""), Element.ALIGN_RIGHT,
									Element.ALIGN_TOP, 8, 14f, true, 1, 1, false, true, true, false, true));
					foot_right_table.addCell(foot_bottom_right_cell43);
				} else if (gst_doc_type.contains("INTRA")) {
					PdfPCell foot_bottom_right_cell3 = new PdfPCell(
							PdfStyle.createUltimateCellWithBorderWithDisableBorder((""), Element.ALIGN_LEFT,
									Element.ALIGN_TOP, 8, 14f, true, 1, 1, false, true, true, false, true));
					foot_right_table.addCell(foot_bottom_right_cell3);
					PdfPCell foot_bottom_right_cell4 = new PdfPCell(
							PdfStyle.createUltimateCellWithBorderWithDisableBorder((""), Element.ALIGN_RIGHT,
									Element.ALIGN_TOP, 8, 14f, true, 1, 1, false, true, true, false, true));
					foot_right_table.addCell(foot_bottom_right_cell4);
					PdfPCell foot_bottom_right_cell5 = new PdfPCell(
							PdfStyle.createUltimateCellWithBorderWithDisableBorder((""), Element.ALIGN_LEFT,
									Element.ALIGN_TOP, 8, 14f, true, 1, 1, false, true, true, false, true));
					foot_right_table.addCell(foot_bottom_right_cell5);
					PdfPCell foot_bottom_right_cell6 = new PdfPCell(
							PdfStyle.createUltimateCellWithBorderWithDisableBorder((""), Element.ALIGN_RIGHT,
									Element.ALIGN_TOP, 8, 14f, true, 1, 1, false, true, true, false, true));
					foot_right_table.addCell(foot_bottom_right_cell6);
				}

				PdfPCell foot_bottom_right_cell73 = new PdfPCell(
						PdfStyle.createUltimateCellWithBorderWithDisableBorder((""), Element.ALIGN_LEFT,
								Element.ALIGN_TOP, 8, 14f, true, 1, 1, false, true, true, false, true));
				foot_right_table.addCell(foot_bottom_right_cell73);
				PdfPCell foot_bottom_right_cell83 = new PdfPCell(
						PdfStyle.createUltimateCellWithBorderWithDisableBorder((""), Element.ALIGN_RIGHT,
								Element.ALIGN_TOP, 8, 14f, true, 1, 1, false, true, true, false, true));
				foot_right_table.addCell(foot_bottom_right_cell83);

				PdfPCell foot_bottom_right_cell7 = new PdfPCell(
						PdfStyle.createUltimateCellWithBorderWithDisableBorder((""), Element.ALIGN_LEFT,
								Element.ALIGN_TOP, 8, 14f, true, 1, 1, false, true, true, false, true));
				foot_bottom_right_cell7.setGrayFill(0.7f);
				foot_right_table.addCell(foot_bottom_right_cell7); // end hear
				PdfPCell foot_bottom_right_cell8 = new PdfPCell(
						PdfStyle.createUltimateCellWithBorderWithDisableBorder((""), Element.ALIGN_RIGHT,
								Element.ALIGN_TOP, 8, 14f, true, 1, 1, false, true, true, false, true));
				foot_bottom_right_cell8.setGrayFill(0.7f);
				foot_right_table.addCell(foot_bottom_right_cell8);

			} else {
				PdfPCell foot_bottom_right_cell1 = new PdfPCell(
						PdfStyle.createUltimateCellWithBorderWithDisableBorder((""), Element.ALIGN_LEFT,
								Element.ALIGN_TOP, 8, 14f, true, 1, 1, false, true, true, false, true));
				foot_right_table.addCell(foot_bottom_right_cell1);
				PdfPCell foot_bottom_right_cell2 = new PdfPCell(
						PdfStyle.createUltimateCellWithBorderWithDisableBorder((""), Element.ALIGN_RIGHT,
								Element.ALIGN_TOP, 8, 14f, true, 1, 1, false, true, true, false, true));
				foot_right_table.addCell(foot_bottom_right_cell2);

				if (gst_doc_type.contains("INTER")) {
					PdfPCell foot_bottom_right_cell33 = new PdfPCell(
							PdfStyle.createUltimateCellWithBorderWithDisableBorder((""), Element.ALIGN_LEFT,
									Element.ALIGN_TOP, 8, 14f, true, 1, 1, false, true, true, false, true));
					foot_right_table.addCell(foot_bottom_right_cell33);
					PdfPCell foot_bottom_right_cell43 = new PdfPCell(
							PdfStyle.createUltimateCellWithBorderWithDisableBorder((""), Element.ALIGN_RIGHT,
									Element.ALIGN_TOP, 8, 14f, true, 1, 1, false, true, true, false, true));
					foot_right_table.addCell(foot_bottom_right_cell43);
				} else if (gst_doc_type.contains("INTRA")) {
					PdfPCell foot_bottom_right_cell3 = new PdfPCell(
							PdfStyle.createUltimateCellWithBorderWithDisableBorder((""), Element.ALIGN_LEFT,
									Element.ALIGN_TOP, 8, 14f, true, 1, 1, false, true, true, false, true));
					foot_right_table.addCell(foot_bottom_right_cell3);
					PdfPCell foot_bottom_right_cell4 = new PdfPCell(
							PdfStyle.createUltimateCellWithBorderWithDisableBorder((""), Element.ALIGN_RIGHT,
									Element.ALIGN_TOP, 8, 14f, true, 1, 1, false, true, true, false, true));
					foot_right_table.addCell(foot_bottom_right_cell4);
					PdfPCell foot_bottom_right_cell5 = new PdfPCell(
							PdfStyle.createUltimateCellWithBorderWithDisableBorder((""), Element.ALIGN_LEFT,
									Element.ALIGN_TOP, 8, 14f, true, 1, 1, false, true, true, false, true));
					foot_right_table.addCell(foot_bottom_right_cell5);
					PdfPCell foot_bottom_right_cell6 = new PdfPCell(
							PdfStyle.createUltimateCellWithBorderWithDisableBorder((""), Element.ALIGN_RIGHT,
									Element.ALIGN_TOP, 8, 14f, true, 1, 1, false, true, true, false, true));
					foot_right_table.addCell(foot_bottom_right_cell6);
				}

				PdfPCell foot_bottom_right_cell73 = new PdfPCell(
						PdfStyle.createUltimateCellWithBorderWithDisableBorder((""), Element.ALIGN_LEFT,
								Element.ALIGN_TOP, 8, 14f, true, 1, 1, false, true, true, false, true));
				foot_right_table.addCell(foot_bottom_right_cell73);
				PdfPCell foot_bottom_right_cell83 = new PdfPCell(
						PdfStyle.createUltimateCellWithBorderWithDisableBorder((""), Element.ALIGN_RIGHT,
								Element.ALIGN_TOP, 8, 14f, true, 1, 1, false, true, true, false, true));
				foot_right_table.addCell(foot_bottom_right_cell83);

				PdfPCell foot_bottom_right_cell7 = new PdfPCell(
						PdfStyle.createUltimateCellWithBorderWithDisableBorder((""), Element.ALIGN_LEFT,
								Element.ALIGN_TOP, 8, 14f, true, 1, 1, false, true, true, false, true));
				foot_bottom_right_cell7.setGrayFill(0.7f);
				foot_right_table.addCell(foot_bottom_right_cell7); // end hear
				PdfPCell foot_bottom_right_cell8 = new PdfPCell(
						PdfStyle.createUltimateCellWithBorderWithDisableBorder((""), Element.ALIGN_RIGHT,
								Element.ALIGN_TOP, 8, 14f, true, 1, 1, false, true, true, false, true));
				foot_bottom_right_cell8.setGrayFill(0.7f);
				foot_right_table.addCell(foot_bottom_right_cell8);

			}

			// end hear
			// footer bottom2
			PdfPTable foot_bottom_table2 = new PdfPTable(3);
			foot_bottom_table2.setWidthPercentage(100);
			foot_bottom_table2.setWidths(new float[] { 3.33f, 2.17f, 4.5f });

			// String company_name = (String) context.getAttribute("company_name");

			PdfPCell foot_bottom_cell02 = new PdfPCell(PdfStyle.createUltimateCellWithBorderWithDisableBorder(
					("Transporter Details"), Element.ALIGN_CENTER, Element.ALIGN_TOP, 9, 14f, true, 1, 1, false, true,
					true, false, true));
			foot_bottom_table2.addCell(foot_bottom_cell02);
			PdfPCell foot_bottom_cell03 = new PdfPCell(PdfStyle.createUltimateCellWithBorderWithDisableBorder((""),
					Element.ALIGN_LEFT, Element.ALIGN_TOP, 8, 14f, true, 1, 1, false, true, true, false, true));
			foot_bottom_table2.addCell(foot_bottom_cell03);
			PdfPCell foot_bottom_cell04 = null;
			if (frm_challan.contains("PIP")) {
				if (comp_cd.equalsIgnoreCase("PFZ")) {
					foot_bottom_cell04 = new PdfPCell(PdfStyle.createUltimateCellWithBorderWithDisableBorder(
							("For Pfizer Products India Private Limited."), Element.ALIGN_CENTER, Element.ALIGN_TOP, 9,
							14f, true, 1, 1, false, true, true, false, true));
				} else { // adding new footer indicator
					if (footer_ind && comp_cd.equalsIgnoreCase("NIL") || comp_cd.equalsIgnoreCase("NHP")) // new check
																											// for
																											// footer
																											// indicator
																											// and
																											// companey
					// code
					{
						foot_bottom_cell04 = new PdfPCell(PdfStyle.createUltimateCellWithBorderWithDisableBorder(
								("For " + company_name), Element.ALIGN_CENTER, Element.ALIGN_TOP, 9, 14f, true, 1, 1,
								false, true, true, false, true));
					} else {
						foot_bottom_cell04 = new PdfPCell(
								PdfStyle.createUltimateCellWithBorderWithDisableBorder((" "), Element.ALIGN_CENTER,
										Element.ALIGN_TOP, 9, 14f, true, 1, 1, false, true, true, false, true));
					}
				}
				foot_bottom_table2.addCell(foot_bottom_cell04);
			} else {
				if (footer_ind && comp_cd.equalsIgnoreCase("NIL") || comp_cd.equalsIgnoreCase("NHP")) { // new check for
																										// footer
																										// indicator and
																										// companey code
					foot_bottom_cell04 = new PdfPCell(PdfStyle.createUltimateCellWithBorderWithDisableBorder(
							("For " + company_name), Element.ALIGN_CENTER, Element.ALIGN_TOP, 9, 14f, true, 1, 1, false,
							true, true, false, true));
				} else if (comp_cd.trim().equalsIgnoreCase("SER") || comp_cd.trim().equalsIgnoreCase(VET)) {
					foot_bottom_cell04 = new PdfPCell(
							PdfStyle.createUltimateCellWithBorderWithDisableBorder((""), Element.ALIGN_CENTER,
									Element.ALIGN_TOP, 9, 14f, true, 1, 1, false, true, true, false, true));
				} else if (comp_cd.trim().equalsIgnoreCase("P&G")) {
					foot_bottom_cell04 = new PdfPCell(PdfStyle.createUltimateCellWithBorderWithDisableBorder(
							("For " + company_name), Element.ALIGN_CENTER, Element.ALIGN_TOP, 9, 14f, true, 1, 1, false,
							true, true, false, true));
				} else {
					foot_bottom_cell04 = new PdfPCell(
							PdfStyle.createUltimateCellWithBorderWithDisableBorder((" "), Element.ALIGN_CENTER,
									Element.ALIGN_TOP, 9, 14f, true, 1, 1, false, true, true, false, true));
				}

				foot_bottom_table2.addCell(foot_bottom_cell04);

			}

			PdfPCell foot_bottom_cell21 = new PdfPCell(
					PdfStyle.createUltimateCellWithBorderWithDisableBorder(("Transporter Name :" + dsp_transporter),
							Element.ALIGN_LEFT, Element.ALIGN_TOP, 8, 14f, true, 1, 1, false, true, true, false, true));
			foot_bottom_table2.addCell(foot_bottom_cell21);
			PdfPCell foot_bottom_cell22 = new PdfPCell(PdfStyle.createUltimateCellWithBorderWithDisableBorder((""),
					Element.ALIGN_CENTER, Element.ALIGN_TOP, 8, 14f, true, 1, 1, false, true, true, false, true));
			foot_bottom_table2.addCell(foot_bottom_cell22);
			PdfPCell foot_bottom_cell32 = new PdfPCell(
					PdfStyle.createUltimateCellWithBorderWithDisableBorder((loc_narration), Element.ALIGN_CENTER,
							Element.ALIGN_TOP, 8, 14f, true, 1, 1, false, false, true, false, true));
			foot_bottom_table2.addCell(foot_bottom_cell32);

			PdfPCell foot_bottom_cell06 = new PdfPCell(
					PdfStyle.createUltimateCellWithBorderWithDisableBorder(("LR No:" + ship_doc_no), Element.ALIGN_LEFT,
							Element.ALIGN_TOP, 8, 14f, true, 1, 1, false, true, true, false, true));
			foot_bottom_table2.addCell(foot_bottom_cell06);
			PdfPCell foot_bottom_cell42 = new PdfPCell(PdfStyle.createUltimateCellWithBorderWithDisableBorder(
					("LR Date : " + (date != null ? Utility.getRptDateFormat(date) : "")), Element.ALIGN_LEFT,
					Element.ALIGN_TOP, 8, 14f, true, 1, 1, false, true, true, false, true));
			foot_bottom_table2.addCell(foot_bottom_cell42);
			// do changes here
			System.out.println("loc_addr2:::" + loc_addr2);
			if (comp_cd.trim().equals(VET) || comp_cd.trim().equals("MDL") || comp_cd.trim().equals("SNK")) {
				PdfPCell foot_bottom_cell07 = new PdfPCell(PdfStyle.createUltimateCellWithBorderWithDisableBorder((" "),
						Element.ALIGN_LEFT, Element.ALIGN_TOP, 8, 14f, true, 1, 1, false, false, true, false, true));
				foot_bottom_table2.addCell(foot_bottom_cell07);
			} else {
				PdfPCell foot_bottom_cell07 = new PdfPCell(
						PdfStyle.createUltimateCellWithBorderWithDisableBorder((loc_addr2), Element.ALIGN_LEFT,
								Element.ALIGN_TOP, 8, 14f, true, 1, 1, false, false, true, false, true));
				foot_bottom_table2.addCell(foot_bottom_cell07);
			}
			PdfPCell foot_bottom_cell52 = null;
			System.out.println("dsp_lorry_no::" + dsp_lorry_no);
			if (comp_cd.equalsIgnoreCase("NIL") || comp_cd.equalsIgnoreCase("NHP")) {
				foot_bottom_cell52 = new PdfPCell(PdfStyle.createUltimateCellWithBorderWithDisableBorder(
						("E-Way Bill No: " + dsp_lorry_no), Element.ALIGN_LEFT, Element.ALIGN_TOP, 8, 14f, true, 1, 1,
						false, true, true, false, true));
			} else {
				foot_bottom_cell52 = new PdfPCell(
						PdfStyle.createUltimateCellWithBorderWithDisableBorder(("Way Bill No:"), Element.ALIGN_LEFT,
								Element.ALIGN_TOP, 8, 14f, true, 1, 1, false, true, true, false, true));
			}

			foot_bottom_table2.addCell(foot_bottom_cell52);
			PdfPCell foot_bottom_cell62 = new PdfPCell(
					PdfStyle.createUltimateCellWithBorderWithDisableBorder(("Date :"), Element.ALIGN_LEFT,
							Element.ALIGN_TOP, 8, 14f, true, 1, 1, false, true, true, false, true));
			foot_bottom_table2.addCell(foot_bottom_cell62);
			PdfPCell foot_bottom_cell08 = new PdfPCell(PdfStyle.createUltimateCellWithBorderWithDisableBorder((""),
					Element.ALIGN_LEFT, Element.ALIGN_TOP, 8, 14f, true, 1, 1, false, false, true, false, true));
			foot_bottom_table2.addCell(foot_bottom_cell08);

			PdfPCell foot_bottom_cell72 = new PdfPCell(
					PdfStyle.createUltimateCellWithBorderWithDisableBorder(("No Of Cases :" + cases),
							Element.ALIGN_LEFT, Element.ALIGN_TOP, 8, 14f, true, 1, 1, false, true, true, false, true));
			foot_bottom_table2.addCell(foot_bottom_cell72);
			PdfPCell foot_bottom_cell82 = new PdfPCell(
					PdfStyle.createUltimateCellWithBorderWithDisableBorder(("Weight :" + weigh), Element.ALIGN_LEFT,
							Element.ALIGN_TOP, 8, 14f, true, 1, 1, false, true, true, false, true));
			foot_bottom_table2.addCell(foot_bottom_cell82);
			PdfPCell foot_bottom_cell09 = new PdfPCell(PdfStyle.createUltimateCellWithBorderWithDisableBorder((""),
					Element.ALIGN_LEFT, Element.ALIGN_TOP, 8, 14f, true, 1, 1, false, false, true, false, true));
			foot_bottom_table2.addCell(foot_bottom_cell09);

			PdfPCell foot_bottom_cell92 = new PdfPCell(
					PdfStyle.createUltimateCellWithBorderWithDisableBorder(("Remarks : " + dsp_challan_msg),
							Element.ALIGN_LEFT, Element.ALIGN_TOP, 8, 14f, true, 1, 1, false, true, true, false, true));
			foot_bottom_table2.addCell(foot_bottom_cell92);
			PdfPCell foot_bottom_cell10 = new PdfPCell(PdfStyle.createUltimateCellWithBorderWithDisableBorder((""),
					Element.ALIGN_LEFT, Element.ALIGN_TOP, 8, 14f, true, 1, 1, false, false, true, true, true));
			foot_bottom_table2.addCell(foot_bottom_cell10);

			// new changes for remove column in pdf cell by vivek based on footer indicator

			if (footer_ind) {
				PdfPCell foot_bottom_cell11 = new PdfPCell(PdfStyle.createUltimateCellWithBorderWithDisableBorder(
						("Authorized Signatory"), Element.ALIGN_CENTER, Element.ALIGN_TOP, 9, 14f, true, 1, 1, false,
						true, true, false, true));
				foot_bottom_table2.addCell(foot_bottom_cell11);
			} else {
				PdfPCell foot_bottom_cell11 = new PdfPCell(PdfStyle.createUltimateCellWithBorderWithDisableBorder((""),
						Element.ALIGN_CENTER, Element.ALIGN_TOP, 9, 14f, true, 1, 1, false, true, true, false, true));
				foot_bottom_table2.addCell(foot_bottom_cell11);
			}

			// footer bottom3
			PdfPTable foot_bottom_table3 = new PdfPTable(1);
			foot_bottom_table3.setWidthPercentage(100);
			foot_bottom_table3.setWidths(new float[] { 10f });

			// Print changes made for novartis
			// added boxes in copies description
			PdfPCell foot_bottom_cell31 = null;
			if (comp_cd.trim().equalsIgnoreCase("SER") || comp_cd.trim().equalsIgnoreCase(VET)
					|| comp_cd.trim().equalsIgnoreCase("MDL") || comp_cd.trim().equalsIgnoreCase("SNK")) {
				foot_bottom_cell31 = new PdfPCell(PdfStyle.createUltimateCellWithBorderWithDisableBorder("",
						Element.ALIGN_CENTER, Element.ALIGN_TOP, 9, 14f, true, 1, 1, true, true, false, false, false));
			} else {
				if (comp_cd.equalsIgnoreCase("NIL") || comp_cd.equalsIgnoreCase("NHP")) {
					Paragraph p = new Paragraph();
					Font checkBoxFont = new Font(Font.FontFamily.ZAPFDINGBATS, 14);
					Chunk checkBox = new Chunk("o", checkBoxFont);
					p.add(new Phrase("Copies : ", new Font(Font.FontFamily.TIMES_ROMAN, 7, Font.BOLD)));
					p.add(checkBox);
					p.add(new Phrase(" :ORIGINAL FOR RECIPIENT.  ",
							new Font(Font.FontFamily.TIMES_ROMAN, 7, Font.BOLD)));
					p.add(checkBox);
					p.add(new Phrase(" :DUPLICATE FOR TRANSPORTER.  ",
							new Font(Font.FontFamily.TIMES_ROMAN, 7, Font.BOLD)));
					p.add(checkBox);
					p.add(new Phrase(" :TRIPLICATE FOR SUPPLIER.  ",
							new Font(Font.FontFamily.TIMES_ROMAN, 7, Font.BOLD)));
					p.add(checkBox);
					p.add(new Phrase(" :QUADRUPLICATE:  BOOK COPY  ",
							new Font(Font.FontFamily.TIMES_ROMAN, 7, Font.BOLD)));
					foot_bottom_cell31 = new PdfPCell(
							PdfStyle.createUltimateCellWithBorderWithDisableBorder(p, Element.ALIGN_CENTER,
									Element.ALIGN_TOP, 9, 14f, true, 1, 1, true, true, false, false, false));
				} else {
					foot_bottom_cell31 = new PdfPCell(PdfStyle.createUltimateCellWithBorderWithDisableBorder(
							"Copies: 1: ORIGINAL FOR CONSIGNEE;   2. DUPLICATE FOR TRANSPORTER;   3. TRIPLICATE FOR COSIGNOR;   4. QUADRUPLICATE:  BOOK COPY ",
							Element.ALIGN_CENTER, Element.ALIGN_TOP, 9, 14f, true, 1, 1, true, true, false, false,
							false));
				}
			}
			foot_bottom_table3.addCell(foot_bottom_cell31);

			PdfPCell bottom_left_cell = new PdfPCell(foot_left_table);
			PdfPCell bottom_right_cell = new PdfPCell(foot_right_table);
			PdfPCell bottom_2_cell = new PdfPCell(foot_bottom_table2);
			bottom_2_cell.setColspan(2);
			PdfPCell bottom_3_cell = new PdfPCell(foot_bottom_table3);
			bottom_3_cell.setColspan(2);

			maintable.addCell(bottom_left_cell);
			maintable.addCell(bottom_right_cell);
			maintable.addCell(bottom_2_cell);
			maintable.addCell(bottom_3_cell);

			// -----------------------------------------------------------------------------------------------------------------

//			if (pageflag) {
// System.out.println("pagecount : " +pagecount);
				cell = new PdfPCell(PdfStyle.createLabelCellWithoutBorderSmallFontNoBold("Page No. " + pagecount));
				cell.setBorder(Rectangle.NO_BORDER);
				cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
				cell.setVerticalAlignment(Element.ALIGN_BOTTOM);
				cell.setColspan(3);
				maintable.addCell(cell);
//			}

		} catch (Exception e) {
			System.out.println("Error Occurred :" + e.getMessage());// uncomment asneeded --;
		}
		return maintable;
	}
}