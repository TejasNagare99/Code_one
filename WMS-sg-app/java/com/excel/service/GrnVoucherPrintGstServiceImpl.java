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
import com.excel.model.SG_d_parameters_details;
import com.excel.model.ViewGrnGSTVoucherPrinting;
import com.excel.repository.UserMasterRepository;
import com.excel.utility.CodifyErrors;
import com.excel.utility.FooterWithoutPageNo;
import com.excel.utility.MedicoConstants;
import com.excel.utility.NumberToWordsBritish;
import com.excel.utility.PdfStyle;
import com.excel.utility.Utility;
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
public class GrnVoucherPrintGstServiceImpl implements GrnVoucherPrintGstService {

	@Autowired
	private JwtProvider tokenProvider;
	@Autowired
	private UserMasterService userMasterService;
	@Autowired
	private UserMasterRepository userMasterRepository;
	@Autowired
	private UserMasterService usermasterservice;
	@Autowired
	private ParameterService parameterService;

	ViewGrnGSTVoucherPrinting footer;
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
	private int limit = 8;
	int count2 = 0;
	private float[] bodyTableWidth = new float[] { 0.5f, 0.8f, 2.4f, 0.5f, 1.0f, 0.9f, 0.6f, 0.6f, 0.5f, 0.7f, 0.7f };

	@Override
	public String getGrnVocuherGstPrint(int loc_id, int vender_id, String frmgrn, String togrn,
			List<ViewGrnGSTVoucherPrinting> challans, boolean checkMe, String company_code, String companyName,
			HttpSession session, HttpServletRequest request) throws Exception {

		String path = MedicoConstants.PDF_FILE_PATH;
		setPath(path);
		FileOutputStream os = null;
		PrintStream ps = null;
		long timevar = new Date().getTime();
		String filename = "GrnReceiptNote" + timevar + "_.pdf";
		// System.out.println("filename 10 " +filename);
		File docfile = new File(path + filename);
		OutputStream file = new FileOutputStream(docfile);
		Document document = new Document(PageSize.A4);
		PdfWriter writer = PdfWriter.getInstance(document, file);
		Rectangle rect = new Rectangle(20, 28, 580, 840);
		writer.setBoxSize("header", rect);
		String uname = this.tokenProvider.getUsernameFromRequest(request);
		String empId = this.userMasterService.getEmpIdByUsername(uname);
		writer = usermasterservice.generatePDFlock(empId, writer);

		if (company_code.equalsIgnoreCase("VET")) {
			rect = new Rectangle(20, 2, 580, 214); // 208
		} else {
			rect = new Rectangle(20, 2, 580, 208); // 208
		}

		writer.setBoxSize("footer", rect);
		// int pagecount=writer.getPageNumber();
		int pagecount = 1;
		FooterWithoutPageNo event = new FooterWithoutPageNo();
		writer.setPageEvent(event);

		document.open();
		document.setMarginMirroringTopBottom(false);
		boolean isnew = false;
		boolean for_new_page = false;
		boolean isPurRateAccept = false;
		SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
		Date cut_off_date_palson = sdf.parse("25-05-2023");
		Date grnDate = null;

		ColumnText column = new ColumnText(writer.getDirectContent());
		try {
			BigDecimal total = new BigDecimal(0);
			// System.out.println("getViewPrePrintedSummaryChallan");
			SG_d_parameters_details pur_rate_param = this.parameterService
					.getParameterDataByDisplayNameWithJoin("ACCEPT_PUR_RATE_IN_GRN");
			Set<String> challanset = new LinkedHashSet<String>();
			for (ViewGrnGSTVoucherPrinting challan : challans) {
				challanset.add(challan.getGrn_no());
			}
			String oldt = "";
			String newt = "";
			String old_c = "";
			String new_c = "";
			PdfPTable bodytable = null;
			int count = 0;
			boolean count_var = false;
			for (String num : challanset) {
				int pagecount_tot = 0;
				limit = 8;
				List<ViewGrnGSTVoucherPrinting> list_new_page = new ArrayList<ViewGrnGSTVoucherPrinting>();
				isnew = true;
				for_new_page = true;
				PdfPTable maintable1 = new PdfPTable(1);
				maintable1.setWidthPercentage(100);
				PdfPCell cell = null;
				pageflag = false;
				// pagecount=1;

				PdfPTable hcell = null;
				int rows = 0;
				bodytable = new PdfPTable(11);
				bodytable.getDefaultCell().setPadding(0.0f);
				bodytable.setWidthPercentage(100);
				bodytable.setWidths(bodyTableWidth);
				total = BigDecimal.ZERO;
				count = 0;
				BigDecimal Tot = BigDecimal.ZERO;
				BigDecimal taxable_total = BigDecimal.ZERO;
				BigDecimal final_total = BigDecimal.ZERO;
				BigDecimal header_Igst = BigDecimal.ZERO;
				BigDecimal header_Cgst = BigDecimal.ZERO;
				BigDecimal header_Sgst = BigDecimal.ZERO;
				BigDecimal header_roundOff = BigDecimal.ZERO;

				String headerText1 = "";
				String headerText2 = "";
				BigDecimal headerValue1 = BigDecimal.ZERO;
				BigDecimal headerValue2 = BigDecimal.ZERO;
				String remarks = "";
				for (ViewGrnGSTVoucherPrinting challan : challans) {
					isPurRateAccept = false;
					headerText1 = challan.getHtext1();
					headerText2 = challan.getHtext2();

					
					grnDate = sdf.parse(challan.getGrn_dt().trim());

					if (pur_rate_param != null && pur_rate_param.getSgprmdet_text1().trim().equals("Y")) {
						if (company_code.trim().equals("PAL")) {
							if (grnDate.after(cut_off_date_palson)) {
								isPurRateAccept = true;
							}
						}
						else if(company_code.trim().equals("PFZ")) {
							isPurRateAccept = true;
						}
					}

					if ((rows) >= limit) {
						// System.out.println("rows" +rows);
						pageflag = true;
						bodytable = newPage(column, bodytable, event, printLabel, true, rows, pageflag, pagecount,
								total, path, challan, loc_id, vender_id, frmgrn, togrn, count, Tot, pagecount_tot,
								count_var, taxable_total, final_total, headerText1, headerText2, header_Igst,
								header_Sgst, header_Cgst, header_roundOff, headerValue1, headerValue2, company_code,
								true,remarks);
						document.newPage();
						pagecount++;
						pagecount_tot = pagecount_tot + pagecount;
						rows = 0;
					}

					if (num.equalsIgnoreCase(challan.getGrn_no())) {
						if (isnew) {
							footer = challan;
							maintable1.addCell(PdfStyle.createLabelCellWithoutBorderSmallFontNoBold(""));
							hcell = createHeader(challan, pagecount, path, loc_id, vender_id, frmgrn, togrn,
									companyName);
							cell = new PdfPCell(hcell);
							cell.setBorder(0);
							cell.setHorizontalAlignment(Element.ALIGN_CENTER);
							maintable1.addCell(cell);
							event.setHeader(maintable1);
							isnew = false;
							list_new_page.add(challan);
						}
						newt = challan.getSmp_type();
						new_c = challan.getGrn_no();

						if (!newt.equals(oldt) || !new_c.equals(old_c)) {
							// count=0;
							count++;
							// rows++;
							count_var = true;
						} else {
							count_var = false;
						}

						StringBuilder result0 = new StringBuilder();

						result0.append(
								challan.getSmp_prod_name().length() > 60 ? challan.getSmp_prod_name().substring(0, 60)
										: challan.getSmp_prod_name());
						if (checkMe) {
							result0.append(System.lineSeparator());
							result0.append("Bin no: ");
							result0.append(challan.getBin_number() == null ? "" : challan.getBin_number());
						}

						StringBuilder result_Remarks = new StringBuilder();
						if (rows == 0) {
							PdfPCell cellsmpProdType = PdfStyle.createValueCellWithHeightGroupingTitle(
									(challan.getSmp_prod_type()), false, true, 25f);
							cellsmpProdType.setPaddingTop(-8f); // Set top padding
							cellsmpProdType.setPaddingBottom(-17f);

							bodytable.addCell(cellsmpProdType);
						}
						result_Remarks.append(challan.getBatch_narration().length() > 95
								? challan.getBatch_narration().substring(0, 95)
								: challan.getBatch_narration());

						bodytable.addCell(PdfStyle.createUltimateCellWithBorderWithDisableBorder(
								(challan.getDivision()), Element.ALIGN_LEFT, Element.ALIGN_TOP, 8, 24f, false, 1, 1,
								false, false, false, true, false));
						bodytable.addCell(PdfStyle.createUltimateCellWithBorderWithDisableBorder(
								(challan.getSmp_prod_cd()), Element.ALIGN_LEFT, Element.ALIGN_TOP, 8, 24f, false, 1, 1,
								false, false, false, true, false));

						bodytable.addCell(PdfStyle.createUltimateCellWithBorderWithDisableBorder((result0.toString()),
								Element.ALIGN_LEFT, Element.ALIGN_TOP, 7, 24f, false, 1, 1, false, false, false, true,
								false));
						bodytable.addCell(PdfStyle.createUltimateCellWithBorderWithDisableBorder(
								(challan.getPacking() + "'s"), Element.ALIGN_CENTER, Element.ALIGN_TOP, 8, 24f, false,
								1, 1, false, false, false, true, false));

						bodytable.addCell(PdfStyle.createUltimateCellWithBorderWithDisableBorder(
								(challan.getBatch_no()), Element.ALIGN_CENTER, Element.ALIGN_TOP, 8, 24f, false, 1, 1,
								false, false, false, true, false));
						bodytable.addCell(PdfStyle.createUltimateCellWithBorderWithDisableBorder(
								(challan.getBatch_mfg_dt()), Element.ALIGN_CENTER, Element.ALIGN_TOP, 8, 24f, false, 1,
								1, false, false, false, true, false));
						bodytable.addCell(PdfStyle.createUltimateCellWithBorderWithDisableBorder(
								(challan.getBatch_expiry_dt().substring(3)), Element.ALIGN_CENTER, Element.ALIGN_TOP, 8,
								24f, false, 1, 1, false, false, false, true, false));

						bodytable.addCell(PdfStyle.createUltimateCellWithBorderWithDisableBorder(
								(challan.getRecvd_qty()).toString(), Element.ALIGN_RIGHT, Element.ALIGN_TOP, 8, 24f,
								false, 1, 1, false, false, false, true, false));
						bodytable.addCell(PdfStyle.createUltimateCellWithBorderWithDisableBorder(
								(challan.getGrnd_noofboxes().toString()), Element.ALIGN_RIGHT, Element.ALIGN_TOP, 8,
								24f, false, 1, 1, false, false, false, true, false));
						bodytable.addCell(PdfStyle.createUltimateCellWithBorderWithDisableBorder(
								(isPurRateAccept == true
										? (challan.getGrnd_pur_rate() == null ? BigDecimal.ZERO
												: challan.getGrnd_pur_rate())
										: (challan.getTransfer_rate() == null ? BigDecimal.ZERO
												: challan.getTransfer_rate()))
										.toString(),
								Element.ALIGN_RIGHT, Element.ALIGN_TOP, 8, 24f, false, 1, 1, false, false, false, true,
								false));
						bodytable
								.addCell(
										PdfStyle.createUltimateCellWithBorderWithDisableBorder(
												(isPurRateAccept == true
														? (challan.getGrnd_value_pur() == null
																? BigDecimal.ZERO.setScale(2, RoundingMode.HALF_UP)
																: challan.getGrnd_value_pur().setScale(2,
																		RoundingMode.HALF_UP))
														: (challan.getGrn_value() == null
																? BigDecimal.ZERO.setScale(2, RoundingMode.HALF_UP)
																: challan.getGrn_value().setScale(2,
																		RoundingMode.HALF_UP)))
														.toPlainString(),
												Element.ALIGN_RIGHT, Element.ALIGN_TOP, 8, 24f, false, 1, 1, false,
												false, false, true, false));

						bodytable.addCell(
								PdfStyle.createUltimateCellWithBorderWithDisableBorder((""), Element.ALIGN_LEFT,
										Element.ALIGN_TOP, 8, 24f, true, 1, 1, false, false, false, true, false));
						bodytable.addCell(
								PdfStyle.createUltimateCellWithBorderWithDisableBorder((""), Element.ALIGN_LEFT,
										Element.ALIGN_TOP, 8, 24f, true, 1, 1, false, false, false, true, false));

						if ((challan.getIgst_bill_amt().compareTo(BigDecimal.ZERO) > 0)) {
							bodytable.addCell(PdfStyle.createUltimateCellWithBorderWithDisableBorder(
									("HSN :" + challan.getHsn_code() + "                     IGST% "),
									Element.ALIGN_LEFT, Element.ALIGN_TOP, 8, 12f, true, 1, 1, false, false, false,
									true, false));
							bodytable.addCell(PdfStyle.createUltimateCellWithBorderWithDisableBorder(
									(challan.getIgst_rate()).toString(), Element.ALIGN_RIGHT, Element.ALIGN_TOP, 8, 12f,
									true, 1, 1, false, false, false, true, false));
							bodytable.addCell(PdfStyle.createUltimateCellWithBorderWithDisableBorder(("IGST"),
									Element.ALIGN_RIGHT, Element.ALIGN_TOP, 8, 12f, true, 1, 1, false, false, false,
									true, false));
							bodytable
									.addCell(
											PdfStyle.createUltimateCellWithBorderWithDisableBorder(
													(challan.getIgst_bill_amt() == null
															? BigDecimal.ZERO.setScale(2, RoundingMode.HALF_UP)
															: challan.getIgst_bill_amt().setScale(2,
																	RoundingMode.HALF_UP))
															.toPlainString(),
													Element.ALIGN_RIGHT, Element.ALIGN_TOP, 8, 12f, true, 1, 1, false,
													false, false, true, false));
							bodytable.addCell(
									PdfStyle.createUltimateCellWithBorderWithDisableBorder((""), Element.ALIGN_LEFT,
											Element.ALIGN_TOP, 8, 12f, true, 1, 1, false, false, false, true, false));
							bodytable.addCell(
									PdfStyle.createUltimateCellWithBorderWithDisableBorder((""), Element.ALIGN_LEFT,
											Element.ALIGN_TOP, 8, 12f, true, 1, 1, false, false, false, true, false));
							bodytable.addCell(
									PdfStyle.createUltimateCellWithBorderWithDisableBorder((""), Element.ALIGN_LEFT,
											Element.ALIGN_TOP, 8, 12f, true, 1, 1, false, false, false, true, false));
						} else {
							bodytable.addCell(PdfStyle.createUltimateCellWithBorderWithDisableBorder(
									("HSN :" + challan.getHsn_code() + "         CGST% :" + challan.getCgst_rate()),
									Element.ALIGN_LEFT, Element.ALIGN_TOP, 8, 12f, true, 1, 1, false, false, false,
									true, false));
							bodytable.addCell(PdfStyle.createUltimateCellWithBorderWithDisableBorder(("CGST"),
									Element.ALIGN_RIGHT, Element.ALIGN_TOP, 8, 12f, true, 1, 1, false, false, false,
									true, false));
							bodytable
									.addCell(
											PdfStyle.createUltimateCellWithBorderWithDisableBorder(
													(challan.getCgst_bill_amt() == null
															? BigDecimal.ZERO.setScale(2, RoundingMode.HALF_UP)
															: challan.getCgst_bill_amt().setScale(2,
																	RoundingMode.HALF_UP))
															.toPlainString(),
													Element.ALIGN_RIGHT, Element.ALIGN_TOP, 8, 12f, true, 1, 1, false,
													false, false, true, false));
							bodytable.addCell(PdfStyle.createUltimateCellWithBorderWithDisableBorder(("SGST%"),
									Element.ALIGN_RIGHT, Element.ALIGN_TOP, 8, 12f, true, 1, 1, false, false, false,
									true, false));
							bodytable.addCell(PdfStyle.createUltimateCellWithBorderWithDisableBorder(
									(challan.getSgst_rate() == null ? BigDecimal.ZERO : challan.getSgst_rate())
											.toString(),
									Element.ALIGN_RIGHT, Element.ALIGN_TOP, 8, 12f, true, 1, 1, false, false, false,
									true, false));
							bodytable.addCell(PdfStyle.createUltimateCellWithBorderWithDisableBorder(("SGST"),
									Element.ALIGN_RIGHT, Element.ALIGN_TOP, 8, 12f, true, 1, 1, false, false, false,
									true, false));
							bodytable
									.addCell(
											PdfStyle.createUltimateCellWithBorderWithDisableBorder(
													(challan.getSgst_bill_amt() == null
															? BigDecimal.ZERO.setScale(2, RoundingMode.HALF_UP)
															: challan.getSgst_bill_amt().setScale(2,
																	RoundingMode.HALF_UP))
															.toPlainString(),
													Element.ALIGN_RIGHT, Element.ALIGN_TOP, 8, 12f, true, 1, 1, false,
													false, false, true, false));
						}

						bodytable.addCell(
								PdfStyle.createUltimateCellWithBorderWithDisableBorder((""), Element.ALIGN_LEFT,
										Element.ALIGN_TOP, 8, 12f, true, 1, 1, false, false, false, true, false));
						bodytable.addCell(
								PdfStyle.createUltimateCellWithBorderWithDisableBorder((""), Element.ALIGN_LEFT,
										Element.ALIGN_TOP, 8, 12f, true, 1, 1, false, false, false, true, false));

						oldt = newt;
						old_c = new_c;

						Tot = isPurRateAccept == true ? challan.getGrn_header_total_value_pur()
								: challan.getGrn_total_value();
						taxable_total = Tot.add(challan.getHvalue1()).add(challan.getHvalue2());
						header_roundOff = isPurRateAccept == true ? challan.getRoundoff_pr() : challan.getRoundoff();
						final_total = taxable_total.add(challan.getHigst_bill_amt().add(
								challan.getHcgst_bill_amt().add(challan.getHsgst_bill_amt().add(header_roundOff))));
						header_Igst = challan.getHigst_bill_amt();
						header_Sgst = challan.getHsgst_bill_amt();
						header_Cgst = challan.getHcgst_bill_amt();

						headerValue1 = challan.getHvalue1();
						headerValue2 = challan.getHvalue2();
						remarks = challan.getRemarks();
						rows++;
					}
				}
				newPage(column, bodytable, event, printLabel, true, rows, pageflag, pagecount, total, path, challans.get(challans.size()-1),
						loc_id, vender_id, frmgrn, togrn, count, Tot, pagecount_tot, count_var, taxable_total,
						final_total, headerText1, headerText2, header_Igst, header_Sgst, header_Cgst, header_roundOff,
						headerValue1, headerValue2, company_code, false, remarks);

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

	private PdfPTable createHeader(ViewGrnGSTVoucherPrinting challan, int pagecount, String path, int loc_id,
			int vender_id, String frmgrn, String togrn, String company_name) throws Exception {
		
		String grnRefNo = this.parameterService.getGrnRefNoInd();
		
		PdfPTable headertable = new PdfPTable(1);
		PdfPTable headertable01 = new PdfPTable(3);
		PdfPTable headertable2 = new PdfPTable(4);
		PdfPTable headertable3 = new PdfPTable(4);
		PdfPTable headertable4 = new PdfPTable(4);
		PdfPTable bodytable = null;

		headertable.setWidthPercentage(100);
		headertable01.setWidthPercentage(100);
		headertable2.setWidthPercentage(100);
		headertable3.setWidthPercentage(100);
		headertable4.setWidthPercentage(100);

		headertable01.setWidths(new float[] { 2f, 6f, 2f });
		headertable2.setWidths(new float[] { 2f, 3f, 2.5f, 2.5f });
		headertable3.setWidths(new float[] { 1f, 4f, 2.5f, 2.5f });
		headertable4.setWidths(new float[] { 2f, 2f, 2.5f, 3.5f });

		Font font = new Font(FontFamily.HELVETICA, 12, Font.BOLD, BaseColor.BLACK);
		Font very_small = new Font(FontFamily.HELVETICA, 8, Font.NORMAL, BaseColor.BLACK);
		PdfPTable headertable1 = new PdfPTable(1);
		headertable1.setWidthPercentage(100);
		headertable1.setWidths(new float[] { 5f });

		// ======================================================================================================================
		// 1
		PdfPCell hcell_title1 = new PdfPCell(new Phrase("", font));
		hcell_title1.setBorder(Rectangle.TOP);

		/* String company_name = (String) context.getAttribute("company_name"); */
		// String company_name ="PFZ";
		String new_comp_name = "Pfizer Products India Private Limited.";
		PdfPCell hcell = null;
		// System.out.println("Loc Id for this challan is
		// "+challan.getLoc_id()+" name "+challan.getLoc_nm());
		if (challan.getLoc_id() != 9) {
			hcell = new PdfPCell(new Phrase(company_name, font));
		} else {
			hcell = new PdfPCell(new Phrase(new_comp_name, font));
		}
		hcell.setBorder(Rectangle.TOP);
		hcell.setHorizontalAlignment(Element.ALIGN_CENTER);

		PdfPCell hcell_title2 = new PdfPCell(new Phrase("", font));
		hcell_title2.setBorder(Rectangle.TOP);

		PdfPCell hcell_title3 = new PdfPCell(new Phrase("", font));//////
		hcell_title3.setBorder(Rectangle.NO_BORDER);

		PdfPCell hcell_1 = new PdfPCell(new Phrase("Goods Receipt Note", font));
		hcell_1.setBorder(Rectangle.NO_BORDER);
		hcell_1.setHorizontalAlignment(Element.ALIGN_CENTER);

		PdfPCell hcell_title4 = new PdfPCell(new Phrase("", font));
		hcell_title4.setBorder(Rectangle.NO_BORDER);

		StringBuilder result5 = new StringBuilder();

		result5.append(challan.getLoc_add1() == null ? "" : challan.getLoc_add1() + "");
		result5.append(challan.getLoc_add2() == null ? "" : challan.getLoc_add2() + "");
		result5.append(challan.getLoc_add3() == null ? "" : challan.getLoc_add3() + ""); // commented
		// for
		// the
		// serdia
		// //for
		// pfizer
		// working
		// properly
		result5.append(challan.getLoc_add4() == null ? "" : challan.getLoc_add4() + "");
		result5.append(System.lineSeparator());
		result5.append("GST No : " + challan.getLgst_reg_no());
		result5.append(" ");
		result5.append("   State : " + challan.getLstate_name());
		result5.append(" ");
		result5.append("   State Code : " + challan.getLstate_code());
		PdfPCell hcell_title5 = new PdfPCell(new Phrase("", font));
		hcell_title5.setBorder(Rectangle.BOTTOM);

		PdfPCell hcell_2 = new PdfPCell(new Phrase(result5.toString(), very_small));
		hcell_2.setBorder(Rectangle.BOTTOM);
		hcell_2.setHorizontalAlignment(Element.ALIGN_CENTER);

		PdfPCell hcell_title6 = new PdfPCell(new Phrase("", font));
		hcell_title6.setBorder(Rectangle.BOTTOM);

		headertable01.addCell(hcell_title1);
		headertable01.addCell(hcell);
		headertable01.addCell(hcell_title2);

		headertable01.addCell(hcell_title3);
		headertable01.addCell(hcell_1);
		headertable01.addCell(hcell_title4);
		headertable01.addCell(hcell_title5);
		headertable01.addCell(hcell_2);

		headertable01.addCell(hcell_title6);
		// =================================================Blank Cell Added To
		// Remove Table Border==============================

		PdfPCell hcell_blank = new PdfPCell(headertable01);
		// ======================================================================================================================
		// 2.1
		Font font_bold = new Font(FontFamily.HELVETICA, 8, Font.BOLD, BaseColor.BLACK);
		Font font1 = new Font(FontFamily.HELVETICA, 8, Font.NORMAL, BaseColor.BLACK);
		StringBuilder result3 = new StringBuilder();

		result3.append("GRN No.");
		result3.append(System.lineSeparator());
		result3.append(" ");
		result3.append(System.lineSeparator());
		result3.append("GRN Date");
		result3.append(System.lineSeparator());
		result3.append(" ");
		result3.append(System.lineSeparator());
		result3.append("Received From :");
		result3.append(System.lineSeparator());
		result3.append("");
		result3.append(System.lineSeparator());
		result3.append("");
		result3.append(System.lineSeparator());
		result3.append("");
		result3.append(System.lineSeparator());
		result3.append("");
		result3.append(System.lineSeparator());
		result3.append("GST No :");
		result3.append(System.lineSeparator());
		result3.append(" ");
		result3.append(System.lineSeparator());
		result3.append("State :");
		result3.append(System.lineSeparator());
		result3.append(" ");
		result3.append(System.lineSeparator());
		result3.append("State Code :");
		PdfPCell hcell2 = new PdfPCell(new Phrase(result3.toString(), font_bold));
		hcell2.setHorizontalAlignment(Element.ALIGN_LEFT);
		hcell2.setVerticalAlignment(Element.ALIGN_TOP);
		hcell2.setBorder(Rectangle.LEFT);

		PdfPTable addressTbl = new PdfPTable(1);
		addressTbl.addCell(hcell2);

		PdfPCell cell1 = new PdfPCell(addressTbl);
		cell1.setBorder(Rectangle.BOTTOM);
		headertable2.addCell(cell1);

		// ======================================================================================================================
		// 2.2
		StringBuilder result4 = new StringBuilder();

		result4.append(challan.getGrn_no().length() > 33 ? challan.getGrn_no().substring(0, 33) : challan.getGrn_no());
		result4.append(System.lineSeparator());
		result4.append(" ");
		result4.append(System.lineSeparator());
		result4.append(challan.getGrn_dt().length() > 33 ? challan.getGrn_dt().substring(0, 33) : challan.getGrn_dt());
		result4.append(System.lineSeparator());
		result4.append(" ");
		result4.append(System.lineSeparator());
		result4.append(challan.getSup_nm().length() > 33 ? challan.getSup_nm().substring(0, 33) : challan.getSup_nm());
		result4.append(System.lineSeparator());
		result4.append(challan.getHsup_address1());
		result4.append(System.lineSeparator());
		result4.append(challan.getHsup_address2());
		result4.append(System.lineSeparator());
		result4.append(challan.getHsup_address3());
		result4.append(System.lineSeparator());
		result4.append("");
		result4.append(System.lineSeparator());
		result4.append(challan.getSgst_reg_no());
		result4.append(System.lineSeparator());
		result4.append(" ");
		result4.append(System.lineSeparator());
		result4.append(challan.getSstate_name());
		result4.append(System.lineSeparator());
		result4.append(" ");
		result4.append(System.lineSeparator());
		result4.append(challan.getSstate_code());

		PdfPCell hcell8 = new PdfPCell(PdfStyle.createLabelCellWithoutBorderSmallFontNoBold(result4.toString()));
		hcell8.setHorizontalAlignment(Element.ALIGN_LEFT);
		hcell8.setVerticalAlignment(Element.ALIGN_TOP);

		PdfPTable addressTbl1 = new PdfPTable(1);
		addressTbl1.addCell(hcell8);

		PdfPCell cell2 = new PdfPCell(addressTbl1);
		cell2.setBorder(Rectangle.BOTTOM);

		headertable2.addCell(cell2);
		// ======================================================================================================================

		// ======================================================================================================================
		// 2.3

		PdfPCell hcell16 = new PdfPCell(PdfStyle.createLabelCellWithoutBorderSmallFont("Transporter "));
		hcell16.setHorizontalAlignment(Element.ALIGN_LEFT);

		PdfPCell hcell17 = new PdfPCell(PdfStyle.createLabelCellWithoutBorderSmallFont("LR Number"));
		hcell17.setHorizontalAlignment(Element.ALIGN_LEFT);

		PdfPCell hcell18 = new PdfPCell(PdfStyle.createLabelCellWithoutBorderSmallFont("LR Date"));
		hcell18.setHorizontalAlignment(Element.ALIGN_LEFT);

		PdfPCell hcell19 = new PdfPCell(PdfStyle.createLabelCellWithoutBorderSmallFont("Challan Number"));
		hcell19.setHorizontalAlignment(Element.ALIGN_LEFT);

		PdfPCell hcell20 = new PdfPCell(PdfStyle.createLabelCellWithoutBorderSmallFont("Challan Date"));
		hcell20.setHorizontalAlignment(Element.ALIGN_LEFT);

		PdfPCell hcell21 = new PdfPCell(PdfStyle.createLabelCellWithoutBorderSmallFont("P.O. Number"));
		hcell21.setHorizontalAlignment(Element.ALIGN_LEFT);

		PdfPCell hcell22 = new PdfPCell(PdfStyle.createLabelCellWithoutBorderSmallFont("P.O. Date "));
		hcell22.setHorizontalAlignment(Element.ALIGN_LEFT);

		PdfPTable addressTbl2 = new PdfPTable(1);
		addressTbl2.addCell(hcell16);
		addressTbl2.addCell(hcell17);
		addressTbl2.addCell(hcell18);
		addressTbl2.addCell(hcell19);
		addressTbl2.addCell(hcell20);
		addressTbl2.addCell(hcell21);
		addressTbl2.addCell(hcell22);

		if (grnRefNo!=null && grnRefNo.trim().equalsIgnoreCase("Y")) {

			PdfPCell hcell23 = new PdfPCell(PdfStyle.createLabelCellWithoutBorderSmallFont("IDOC/IBD No."));
			hcell23.setHorizontalAlignment(Element.ALIGN_LEFT);

			PdfPCell hcell24 = new PdfPCell(PdfStyle.createLabelCellWithoutBorderSmallFont("Stock Type"));
			hcell24.setHorizontalAlignment(Element.ALIGN_LEFT);
			addressTbl2.addCell(hcell23);
			addressTbl2.addCell(hcell24);
		}

		PdfPCell cell3 = new PdfPCell(addressTbl2);
		cell3.setBorder(Rectangle.BOTTOM);

		headertable2.addCell(cell3);

		// ======================================================================================================================

		// ======================================================================================================================
		// 2.4
		// Font medFont = new Font(Font.FontFamily.HELVETICA, 12,Font.NORMAL,
		// BaseColor.BLACK);

		PdfPCell hcell26 = new PdfPCell(PdfStyle.createLabelCellWithBorderSmallFontNoBold(
				(challan.getTransporter() == null ? "" : challan.getTransporter())));
		hcell26.setHorizontalAlignment(Element.ALIGN_LEFT);

		PdfPCell hcell27 = new PdfPCell(PdfStyle.createLabelCellWithBorderSmallFontNoBold(
				(challan.getGrn_lr_no().length() > 26 ? challan.getGrn_lr_no().substring(0, 26)
						: challan.getGrn_lr_no())));
		hcell27.setHorizontalAlignment(Element.ALIGN_LEFT);

		PdfPCell hcell28 = new PdfPCell(PdfStyle.createLabelCellWithBorderSmallFontNoBold(
				(challan.getGrn_lr_dt() == null ? "" : challan.getGrn_lr_dt())));
		hcell28.setHorizontalAlignment(Element.ALIGN_LEFT);

		PdfPCell hcell29 = new PdfPCell(PdfStyle.createLabelCellWithBorderSmallFontNoBold(
				(challan.getGrn_transfer_no() == null ? "" : challan.getGrn_transfer_no())));
		hcell29.setHorizontalAlignment(Element.ALIGN_LEFT);

		PdfPCell hcell30 = new PdfPCell(PdfStyle.createLabelCellWithBorderSmallFontNoBold(
				(challan.getGrn_transfer_dt() == null ? "" : challan.getGrn_transfer_dt())));
		hcell30.setHorizontalAlignment(Element.ALIGN_LEFT);

		PdfPCell hcell31 = new PdfPCell(PdfStyle.createLabelCellWithBorderSmallFontNoBold(
				(challan.getPo_num().length() > 20 ? challan.getPo_num().substring(0, 20) : challan.getPo_num())));
		hcell31.setHorizontalAlignment(Element.ALIGN_LEFT);

		PdfPCell hcell32 = new PdfPCell(PdfStyle.createLabelCellWithBorderSmallFontNoBold(
				(challan.getPo_date().length() > 28 ? challan.getPo_date().substring(0, 28) : challan.getPo_date())));
		hcell32.setHorizontalAlignment(Element.ALIGN_LEFT);

		PdfPTable addressTbl3 = new PdfPTable(1);
		addressTbl3.addCell(hcell26);
		addressTbl3.addCell(hcell27);
		addressTbl3.addCell(hcell28);
		addressTbl3.addCell(hcell29);
		addressTbl3.addCell(hcell30);
		addressTbl3.addCell(hcell31);
		addressTbl3.addCell(hcell32);

		if (grnRefNo!=null && grnRefNo.trim().equalsIgnoreCase("Y")) {
			PdfPCell hcell33 = new PdfPCell(PdfStyle.createLabelCellWithBorderSmallFontNoBold(
					(challan.getGrn_ref_no().length() > 20 ? challan.getGrn_ref_no().substring(0, 20)
							: challan.getGrn_ref_no())));
			hcell33.setHorizontalAlignment(Element.ALIGN_LEFT);

			PdfPCell hcell34 = new PdfPCell(PdfStyle.createLabelCellWithBorderSmallFontNoBold(
					(challan.getGrn_type() == null ? "" : challan.getGrn_type())));
			hcell34.setHorizontalAlignment(Element.ALIGN_LEFT);
			addressTbl3.addCell(hcell33);
			addressTbl3.addCell(hcell34);
			
		}

		PdfPCell cell4 = new PdfPCell(addressTbl3);
		cell4.setBorder(Rectangle.BOTTOM);

		headertable2.addCell(cell4);

		// =================================================Blank Cell Added To
		// Remove Table Border==============================

		PdfPCell hcell_blank2 = new PdfPCell(headertable2);
		hcell_blank2.setBorder(Rectangle.NO_BORDER);
		// =====================================================================================================================

		// =====================================================================================================================
		// 3.1

		bodytable = new PdfPTable(11);

		bodytable.setWidthPercentage(100);
		bodytable.setWidths(bodyTableWidth);
		bodytable.addCell(PdfStyle.createLabelWithBGColourAndHeight("Div."));
		bodytable.addCell(PdfStyle.createLabelWithBGColourAndHeight("Product Code"));
		bodytable.addCell(PdfStyle.createLabelWithBGColourAndHeight("Product Name"));
		bodytable.addCell(PdfStyle.createLabelWithBGColourAndHeight("Pack"));

		bodytable.addCell(PdfStyle.createLabelWithBGColourAndHeight("Batch/Art Work No:"));
		bodytable.addCell(PdfStyle.createLabelWithBGColourAndHeight("Mfg Date"));
		bodytable.addCell(PdfStyle.createLabelWithBGColourAndHeight("Exp Date"));
		bodytable.addCell(PdfStyle.createLabelWithBGColourAndHeight("Recvd Qty"));
		bodytable.addCell(PdfStyle.createLabelWithBGColourAndHeight("No of Box"));

		bodytable.addCell(PdfStyle.createLabelWithBGColourAndHeight("Rate"));
		bodytable.addCell(PdfStyle.createLabelWithBGColourAndHeight("Value"));

		// =================================================Blank Cell Added To
		// Remove Table Border4==============================

		PdfPCell hcell_blank4 = new PdfPCell(bodytable);
		hcell_blank4.setBorder(Rectangle.NO_BORDER);
		headertable.addCell(hcell_blank);
		headertable.addCell(hcell_blank2);
		headertable.addCell(hcell_blank4);

		return headertable;
	}

	public PdfPTable newPage(ColumnText column, PdfPTable bodytable, FooterWithoutPageNo event,
			HashMap<String, String> printLabel, boolean isFooterData, int rows, boolean pageflag, int pagecount,
			BigDecimal total, String path, ViewGrnGSTVoucherPrinting challans, int loc_id, int vender_id,
			String frmgrn, String togrn, int count, BigDecimal Tot, int pagecount_tot, boolean count_var,
			BigDecimal taxable_total, BigDecimal final_total, String headerText1, String headerText2,
			BigDecimal header_Igst, BigDecimal header_Sgst, BigDecimal header_Cgst, BigDecimal header_roundOff,
			BigDecimal headerValue1, BigDecimal headerValue2, String company_code, boolean rowsExceeded, String remarks)
			throws DocumentException {
		// System.out.println("count " +count);
		// System.out.println("rows "+rows);
		PdfPCell cell = null;
		try {
			if ((limit - rows) > 0) {
				// add extra empty rows in body table
				cell = new PdfPCell();
				PdfPTable temp = new PdfPTable(11);
				temp.setWidths(bodyTableWidth);

				temp.addCell(PdfStyle.createValueCellWithHeight((""), false, true, 24f));
				temp.addCell(PdfStyle.createValueCellWithHeight((""), false, true, 24f));
				temp.addCell(PdfStyle.createValueCellWithHeight((""), false, true, 24f));
				temp.addCell(PdfStyle.createValueCellWithHeight((""), false, true, 24f));
				temp.addCell(PdfStyle.createValueCellWithHeight((""), false, true, 24f));
				temp.addCell(PdfStyle.createValueCellWithHeight((""), false, true, 24f));
				temp.addCell(PdfStyle.createValueCellWithHeight((""), false, true, 24f));
				temp.addCell(PdfStyle.createValueCellWithHeight((""), true, true, 24f));
				temp.addCell(PdfStyle.createValueCellWithHeight((""), true, true, 24f));
				temp.addCell(PdfStyle.createValueCellWithHeight((""), false, true, 24f));
				temp.addCell(PdfStyle.createValueCellWithHeight((""), false, true, 24f));

				temp.addCell(PdfStyle.createValueCellWithHeight((""), false, true, 24f));
				temp.addCell(PdfStyle.createValueCellWithHeight((""), false, true, 24f));
				temp.addCell(PdfStyle.createValueCellWithHeight((""), false, true, 24f));
				temp.addCell(PdfStyle.createValueCellWithHeight((""), false, true, 24f));
				temp.addCell(PdfStyle.createValueCellWithHeight((""), false, true, 24f));
				temp.addCell(PdfStyle.createValueCellWithHeight((""), false, true, 24f));
				temp.addCell(PdfStyle.createValueCellWithHeight((""), false, true, 24f));
				temp.addCell(PdfStyle.createValueCellWithHeight((""), true, true, 24f));
				temp.addCell(PdfStyle.createValueCellWithHeight((""), true, true, 24f));
				temp.addCell(PdfStyle.createValueCellWithHeight((""), false, true, 24f));
				temp.addCell(PdfStyle.createValueCellWithHeight((""), false, true, 24f));

				cell = new PdfPCell(temp);
				cell.setBorder(0);
				cell.setColspan(11);

				for (int i = 0; i < (limit - rows); i++) {
					bodytable.addCell(cell);
				}

			}

			/*
			 * cell = new PdfPCell(PdfStyle.createSingleLine()); cell.setColspan(11);
			 * bodytable.addCell(cell);
			 */

			column.addElement(bodytable);
			if (company_code.equalsIgnoreCase("VET")) {
				column.setSimpleColumn(20, 40, 580, 624);
			} else {
				column.setSimpleColumn(20, 40, 580, 616);
			}
			// column.setSimpleColumn(20, 40, 580, 624);//increase to take
			// up//initially 616 for veto 622
			column.go();

			bodytable = new PdfPTable(11);
			bodytable.setWidthPercentage(100);
			bodytable.setWidths(bodyTableWidth);

			event.setFooter(createFooter(printLabel, isFooterData, pageflag, pagecount, total, path, challans, loc_id,
					vender_id, frmgrn, togrn, Tot, pagecount_tot, taxable_total, final_total, headerText1, headerText2,
					header_Igst, header_Sgst, header_Cgst, header_roundOff, headerValue1, headerValue2, rowsExceeded, remarks));

		} catch (DocumentException e) {
			throw e;
		}
		return bodytable;
	}

	private PdfPTable createFooter(HashMap<String, String> printLabel, boolean isFooterData, boolean pageflag,
			int pagecount, BigDecimal total, String path, ViewGrnGSTVoucherPrinting challan, int loc_id,
			int vender_id, String frmgrn, String togrn, BigDecimal Tot, int pagecount_tot, BigDecimal taxable_total,
			BigDecimal final_total, String headerText1, String headerText2, BigDecimal header_Igst,
			BigDecimal header_Sgst, BigDecimal header_Cgst, BigDecimal header_roundOff, BigDecimal headerValue1,
			BigDecimal headerValue2, boolean rowsExceeded, String remarks) throws DocumentException {
		PdfPTable maintable = new PdfPTable(2);

		try {
			// for (ViewGrnGSTVoucherPrinting obj : challan) {

			boolean isIgst = false;
			boolean isCgst = false;
			PdfPCell cell = null;

			maintable.setWidthPercentage(100);

			cell = new PdfPCell();

			maintable.setWidths(new float[] { 5.65f, 4.35f });

			int length = (int) (Math.log10(pagecount) + 1);

			// footer left cell
			PdfPTable foot_left_table = new PdfPTable(1);
			foot_left_table.setWidthPercentage(100);
			foot_left_table.setWidths(new float[] { 10f });

			if (rowsExceeded) {
				PdfPCell foot_bottom_cell1 = new PdfPCell(PdfStyle.createUltimateCellWithBorderWithDisableBorder(
						(System.lineSeparator() + System.lineSeparator() + System.lineSeparator()
								+ System.lineSeparator() + System.lineSeparator() + System.lineSeparator()
								+ System.lineSeparator() + "Rupees In Words : " + " Continued on next page"),
						Element.ALIGN_LEFT, Element.ALIGN_TOP, 8, 14f, true, 1, 1, false, true, true, false, true));
				foot_left_table.addCell(foot_bottom_cell1);
			} else {
				PdfPCell foot_bottom_cell1 = new PdfPCell(PdfStyle.createUltimateCellWithBorderWithDisableBorder(
						(System.lineSeparator() + System.lineSeparator() + System.lineSeparator()
								+ System.lineSeparator() + System.lineSeparator() + System.lineSeparator()
								+ System.lineSeparator() + "Rupees In Words : "
								+ new NumberToWordsBritish().convertNumberToWords(
										Utility.getValueWithRoundOff(final_total).longValue())
								+ " Only."),
						Element.ALIGN_LEFT, Element.ALIGN_TOP, 8, 14f, true, 1, 1, false, true, true, false, true));
				foot_left_table.addCell(foot_bottom_cell1);
			}

			PdfPCell foot_bottom_cell1 = new PdfPCell(PdfStyle.createUltimateCellWithBorderWithDisableBorder(
					"Remarks :  " + remarks, Element.ALIGN_LEFT, Element.ALIGN_BOTTOM, 8, 14f, true,
					1, 1, false, true, true, false, true));
			foot_left_table.addCell(foot_bottom_cell1);
			System.err.println(remarks);

			// footer right cell
			PdfPTable foot_right_table = new PdfPTable(2);
			foot_right_table.setWidthPercentage(100);
			foot_right_table.setWidths(new float[] { 5.2f, 4.8f });

			if (rowsExceeded) {
				PdfPCell foot_bottom_right_cell1 = new PdfPCell(
						PdfStyle.createUltimateCellWithBorderWithDisableBorder(("Goods Value"), Element.ALIGN_LEFT,
								Element.ALIGN_TOP, 8, 14f, true, 1, 1, false, true, true, false, true));
				foot_right_table.addCell(foot_bottom_right_cell1);
				PdfPCell foot_bottom_right_cell2 = new PdfPCell(
						PdfStyle.createUltimateCellWithBorderWithDisableBorder("Continued", Element.ALIGN_RIGHT,
								Element.ALIGN_TOP, 8, 14f, true, 1, 1, false, true, true, false, true));
				foot_right_table.addCell(foot_bottom_right_cell2);
				PdfPCell foot_bottom_right_cell3 = new PdfPCell(
						PdfStyle.createUltimateCellWithBorderWithDisableBorder((headerText1), Element.ALIGN_LEFT,
								Element.ALIGN_TOP, 8, 14f, true, 1, 1, false, true, true, false, true));
				foot_right_table.addCell(foot_bottom_right_cell3);
				PdfPCell foot_bottom_right_cell4 = new PdfPCell(
						PdfStyle.createUltimateCellWithBorderWithDisableBorder("Continued", Element.ALIGN_RIGHT,
								Element.ALIGN_TOP, 8, 14f, true, 1, 1, false, true, true, false, true));
				foot_right_table.addCell(foot_bottom_right_cell4);
				PdfPCell foot_bottom_right_cell5 = new PdfPCell(
						PdfStyle.createUltimateCellWithBorderWithDisableBorder((headerText2), Element.ALIGN_LEFT,
								Element.ALIGN_TOP, 8, 14f, true, 1, 1, false, true, true, false, true));
				foot_right_table.addCell(foot_bottom_right_cell5);
				PdfPCell foot_bottom_right_cell6 = new PdfPCell(
						PdfStyle.createUltimateCellWithBorderWithDisableBorder("Continued", Element.ALIGN_RIGHT,
								Element.ALIGN_TOP, 8, 14f, true, 1, 1, false, true, true, false, true));
				foot_right_table.addCell(foot_bottom_right_cell6);
				PdfPCell foot_bottom_right_cell7 = new PdfPCell(
						PdfStyle.createUltimateCellWithBorderWithDisableBorder(("Taxable Amount"), Element.ALIGN_LEFT,
								Element.ALIGN_TOP, 8, 14f, true, 1, 1, false, true, true, false, true));
				foot_bottom_right_cell7.setGrayFill(0.7f);
				foot_right_table.addCell(foot_bottom_right_cell7);
				PdfPCell foot_bottom_right_cell8 = new PdfPCell(
						PdfStyle.createUltimateCellWithBorderWithDisableBorder("Continued", Element.ALIGN_RIGHT,
								Element.ALIGN_TOP, 8, 14f, true, 1, 1, false, true, true, false, true));
				foot_bottom_right_cell8.setGrayFill(0.7f);
				foot_right_table.addCell(foot_bottom_right_cell8);

				/*
				 * if(!header_Igst.equals(0)){ isIgst = true; }else if(!header_Cgst.equals(0)){
				 * isCgst = true; }
				 */

				if (header_Igst.compareTo(BigDecimal.ZERO) > 0) {
					PdfPCell foot_bottom_right_cell9 = new PdfPCell(
							PdfStyle.createUltimateCellWithBorderWithDisableBorder(("IGST"), Element.ALIGN_LEFT,
									Element.ALIGN_TOP, 8, 14f, true, 1, 1, false, true, true, false, true));
					foot_right_table.addCell(foot_bottom_right_cell9);
					PdfPCell foot_bottom_right_cell10 = new PdfPCell(
							PdfStyle.createUltimateCellWithBorderWithDisableBorder("Continued", Element.ALIGN_RIGHT,
									Element.ALIGN_TOP, 8, 14f, true, 1, 1, false, true, true, false, true));
					foot_right_table.addCell(foot_bottom_right_cell10);
				} else {
					PdfPCell foot_bottom_right_cell9 = new PdfPCell(
							PdfStyle.createUltimateCellWithBorderWithDisableBorder(("CGST"), Element.ALIGN_LEFT,
									Element.ALIGN_TOP, 8, 14f, true, 1, 1, false, true, true, false, true));
					foot_right_table.addCell(foot_bottom_right_cell9);
					PdfPCell foot_bottom_right_cell10 = new PdfPCell(
							PdfStyle.createUltimateCellWithBorderWithDisableBorder("Continued", Element.ALIGN_RIGHT,
									Element.ALIGN_TOP, 8, 14f, true, 1, 1, false, true, true, false, true));
					foot_right_table.addCell(foot_bottom_right_cell10);
					PdfPCell foot_bottom_right_cell11 = new PdfPCell(
							PdfStyle.createUltimateCellWithBorderWithDisableBorder(("SGST"), Element.ALIGN_LEFT,
									Element.ALIGN_TOP, 8, 14f, true, 1, 1, false, true, true, false, true));
					foot_right_table.addCell(foot_bottom_right_cell11);
					PdfPCell foot_bottom_right_cell12 = new PdfPCell(
							PdfStyle.createUltimateCellWithBorderWithDisableBorder("Continued", Element.ALIGN_RIGHT,
									Element.ALIGN_TOP, 8, 14f, true, 1, 1, false, true, true, false, true));
					foot_right_table.addCell(foot_bottom_right_cell12);
				}

				PdfPCell foot_bottom_right_cell15 = new PdfPCell(
						PdfStyle.createUltimateCellWithBorderWithDisableBorder(("Round Off ±"), Element.ALIGN_LEFT,
								Element.ALIGN_TOP, 8, 14f, true, 1, 1, false, true, true, false, true));
				foot_right_table.addCell(foot_bottom_right_cell15);
				PdfPCell foot_bottom_right_cell16 = new PdfPCell(
						PdfStyle.createUltimateCellWithBorderWithDisableBorder("Continued", Element.ALIGN_RIGHT,
								Element.ALIGN_TOP, 8, 14f, true, 1, 1, false, true, true, false, true));
				foot_right_table.addCell(foot_bottom_right_cell16);

				PdfPCell foot_bottom_right_cell13 = new PdfPCell(
						PdfStyle.createUltimateCellWithBorderWithDisableBorder(("Total"), Element.ALIGN_LEFT,
								Element.ALIGN_TOP, 8, 14f, true, 1, 1, false, true, true, false, true));
				foot_right_table.addCell(foot_bottom_right_cell13);
				PdfPCell foot_bottom_right_cell14 = new PdfPCell(
						PdfStyle.createUltimateCellWithBorderWithDisableBorder("Continued", Element.ALIGN_RIGHT,
								Element.ALIGN_TOP, 8, 14f, true, 1, 1, false, true, true, false, true));
				foot_right_table.addCell(foot_bottom_right_cell14);
			} else {
				PdfPCell foot_bottom_right_cell1 = new PdfPCell(
						PdfStyle.createUltimateCellWithBorderWithDisableBorder(("Goods Value"), Element.ALIGN_LEFT,
								Element.ALIGN_TOP, 8, 14f, true, 1, 1, false, true, true, false, true));
				foot_right_table.addCell(foot_bottom_right_cell1);
				PdfPCell foot_bottom_right_cell2 = new PdfPCell(PdfStyle.createUltimateCellWithBorderWithDisableBorder(
						(Tot.setScale(2, BigDecimal.ROUND_HALF_UP).toString()), Element.ALIGN_RIGHT, Element.ALIGN_TOP,
						8, 14f, true, 1, 1, false, true, true, false, true));
				foot_right_table.addCell(foot_bottom_right_cell2);
				PdfPCell foot_bottom_right_cell3 = new PdfPCell(
						PdfStyle.createUltimateCellWithBorderWithDisableBorder((headerText1), Element.ALIGN_LEFT,
								Element.ALIGN_TOP, 8, 14f, true, 1, 1, false, true, true, false, true));
				foot_right_table.addCell(foot_bottom_right_cell3);
				PdfPCell foot_bottom_right_cell4 = new PdfPCell(PdfStyle.createUltimateCellWithBorderWithDisableBorder(
						(headerValue1.setScale(2, BigDecimal.ROUND_HALF_UP).toString()), Element.ALIGN_RIGHT,
						Element.ALIGN_TOP, 8, 14f, true, 1, 1, false, true, true, false, true));
				foot_right_table.addCell(foot_bottom_right_cell4);
				PdfPCell foot_bottom_right_cell5 = new PdfPCell(
						PdfStyle.createUltimateCellWithBorderWithDisableBorder((headerText2), Element.ALIGN_LEFT,
								Element.ALIGN_TOP, 8, 14f, true, 1, 1, false, true, true, false, true));
				foot_right_table.addCell(foot_bottom_right_cell5);
				PdfPCell foot_bottom_right_cell6 = new PdfPCell(PdfStyle.createUltimateCellWithBorderWithDisableBorder(
						(headerValue2.setScale(2, BigDecimal.ROUND_HALF_UP).toString()), Element.ALIGN_RIGHT,
						Element.ALIGN_TOP, 8, 14f, true, 1, 1, false, true, true, false, true));
				foot_right_table.addCell(foot_bottom_right_cell6);
				PdfPCell foot_bottom_right_cell7 = new PdfPCell(
						PdfStyle.createUltimateCellWithBorderWithDisableBorder(("Taxable Amount"), Element.ALIGN_LEFT,
								Element.ALIGN_TOP, 8, 14f, true, 1, 1, false, true, true, false, true));
				foot_bottom_right_cell7.setGrayFill(0.7f);
				foot_right_table.addCell(foot_bottom_right_cell7);
				PdfPCell foot_bottom_right_cell8 = new PdfPCell(PdfStyle.createUltimateCellWithBorderWithDisableBorder(
						(taxable_total.setScale(2, BigDecimal.ROUND_HALF_UP).toString()), Element.ALIGN_RIGHT,
						Element.ALIGN_TOP, 8, 14f, true, 1, 1, false, true, true, false, true));
				foot_bottom_right_cell8.setGrayFill(0.7f);
				foot_right_table.addCell(foot_bottom_right_cell8);

				/*
				 * if(!header_Igst.equals(0)){ isIgst = true; }else if(!header_Cgst.equals(0)){
				 * isCgst = true; }
				 */

				if (header_Igst.compareTo(BigDecimal.ZERO) > 0) {
					PdfPCell foot_bottom_right_cell9 = new PdfPCell(
							PdfStyle.createUltimateCellWithBorderWithDisableBorder(("IGST"), Element.ALIGN_LEFT,
									Element.ALIGN_TOP, 8, 14f, true, 1, 1, false, true, true, false, true));
					foot_right_table.addCell(foot_bottom_right_cell9);
					PdfPCell foot_bottom_right_cell10 = new PdfPCell(
							PdfStyle.createUltimateCellWithBorderWithDisableBorder(
									(header_Igst.setScale(2, BigDecimal.ROUND_HALF_UP).toString()), Element.ALIGN_RIGHT,
									Element.ALIGN_TOP, 8, 14f, true, 1, 1, false, true, true, false, true));
					foot_right_table.addCell(foot_bottom_right_cell10);
				} else {
					PdfPCell foot_bottom_right_cell9 = new PdfPCell(
							PdfStyle.createUltimateCellWithBorderWithDisableBorder(("CGST"), Element.ALIGN_LEFT,
									Element.ALIGN_TOP, 8, 14f, true, 1, 1, false, true, true, false, true));
					foot_right_table.addCell(foot_bottom_right_cell9);
					PdfPCell foot_bottom_right_cell10 = new PdfPCell(
							PdfStyle.createUltimateCellWithBorderWithDisableBorder(
									(header_Cgst.setScale(2, BigDecimal.ROUND_HALF_UP).toString()), Element.ALIGN_RIGHT,
									Element.ALIGN_TOP, 8, 14f, true, 1, 1, false, true, true, false, true));
					foot_right_table.addCell(foot_bottom_right_cell10);
					PdfPCell foot_bottom_right_cell11 = new PdfPCell(
							PdfStyle.createUltimateCellWithBorderWithDisableBorder(("SGST"), Element.ALIGN_LEFT,
									Element.ALIGN_TOP, 8, 14f, true, 1, 1, false, true, true, false, true));
					foot_right_table.addCell(foot_bottom_right_cell11);
					PdfPCell foot_bottom_right_cell12 = new PdfPCell(
							PdfStyle.createUltimateCellWithBorderWithDisableBorder(
									(header_Sgst.setScale(2, BigDecimal.ROUND_HALF_UP).toString()), Element.ALIGN_RIGHT,
									Element.ALIGN_TOP, 8, 14f, true, 1, 1, false, true, true, false, true));
					foot_right_table.addCell(foot_bottom_right_cell12);
				}

				PdfPCell foot_bottom_right_cell15 = new PdfPCell(
						PdfStyle.createUltimateCellWithBorderWithDisableBorder(("Round Off ±"), Element.ALIGN_LEFT,
								Element.ALIGN_TOP, 8, 14f, true, 1, 1, false, true, true, false, true));
				foot_right_table.addCell(foot_bottom_right_cell15);
				PdfPCell foot_bottom_right_cell16 = new PdfPCell(PdfStyle.createUltimateCellWithBorderWithDisableBorder(
						(header_roundOff.setScale(2, BigDecimal.ROUND_HALF_UP).toString()), Element.ALIGN_RIGHT,
						Element.ALIGN_TOP, 8, 14f, true, 1, 1, false, true, true, false, true));
				foot_right_table.addCell(foot_bottom_right_cell16);

				PdfPCell foot_bottom_right_cell13 = new PdfPCell(
						PdfStyle.createUltimateCellWithBorderWithDisableBorder(("Total"), Element.ALIGN_LEFT,
								Element.ALIGN_TOP, 8, 14f, true, 1, 1, false, true, true, false, true));
				foot_right_table.addCell(foot_bottom_right_cell13);
				PdfPCell foot_bottom_right_cell14 = new PdfPCell(PdfStyle.createUltimateCellWithBorderWithDisableBorder(
						(final_total.setScale(2, BigDecimal.ROUND_HALF_UP).toString()), Element.ALIGN_RIGHT,
						Element.ALIGN_TOP, 8, 14f, true, 1, 1, false, true, true, false, true));
				foot_right_table.addCell(foot_bottom_right_cell14);
			}

			// bottom table
			PdfPTable foot_bottom_table = new PdfPTable(3);
			foot_bottom_table.setWidthPercentage(100);
			foot_bottom_table.setWidths(new float[] { 33.33f, 33.33f, 33.33f });

			PdfPCell foot_bottom_cell12 = new PdfPCell(
					PdfStyle.createUltimateCellWithBorderWithDisableBorder(("Prepared By"), Element.ALIGN_CENTER,
							Element.ALIGN_TOP, 8, 14f, true, 1, 1, false, true, true, false, true));
			foot_bottom_table.addCell(foot_bottom_cell12);
			PdfPCell foot_bottom_cell2 = new PdfPCell(
					PdfStyle.createUltimateCellWithBorderWithDisableBorder(("Checked By"), Element.ALIGN_CENTER,
							Element.ALIGN_TOP, 8, 14f, true, 1, 1, false, true, true, false, true));
			foot_bottom_table.addCell(foot_bottom_cell2);
			PdfPCell foot_bottom_cell3 = new PdfPCell(
					PdfStyle.createUltimateCellWithBorderWithDisableBorder(("Approved By"), Element.ALIGN_CENTER,
							Element.ALIGN_TOP, 8, 14f, true, 1, 1, false, true, true, false, true));
			foot_bottom_table.addCell(foot_bottom_cell3);
			PdfPCell foot_bottom_cell4 = new PdfPCell(PdfStyle.createUltimateCellWithBorderWithDisableBorder(
					("Date :" + System.lineSeparator() + System.lineSeparator() + System.lineSeparator()
							+ System.lineSeparator() + System.lineSeparator() + System.lineSeparator()),
					Element.ALIGN_LEFT, Element.ALIGN_TOP, 8, 14f, true, 1, 1, false, true, true, false, true));
			foot_bottom_table.addCell(foot_bottom_cell4);
			PdfPCell foot_bottom_cell5 = new PdfPCell(PdfStyle.createUltimateCellWithBorderWithDisableBorder(
					("Date :" + System.lineSeparator() + System.lineSeparator() + System.lineSeparator()
							+ System.lineSeparator() + System.lineSeparator() + System.lineSeparator()),
					Element.ALIGN_LEFT, Element.ALIGN_TOP, 8, 14f, true, 1, 1, false, true, true, false, true));
			foot_bottom_table.addCell(foot_bottom_cell5);
			PdfPCell foot_bottom_cell6 = new PdfPCell(PdfStyle.createUltimateCellWithBorderWithDisableBorder(
					("Date :" + System.lineSeparator() + System.lineSeparator() + System.lineSeparator()
							+ System.lineSeparator() + System.lineSeparator() + System.lineSeparator()),
					Element.ALIGN_LEFT, Element.ALIGN_TOP, 8, 14f, true, 1, 1, false, true, true, false, true));
			foot_bottom_table.addCell(foot_bottom_cell6);

			PdfPCell bottom_left_cell = new PdfPCell(foot_left_table);
			PdfPCell bottom_right_cell = new PdfPCell(foot_right_table);
			PdfPCell bottom_1_cell = new PdfPCell(foot_bottom_table);
			bottom_1_cell.setColspan(2);

			maintable.addCell(bottom_left_cell);
			maintable.addCell(bottom_right_cell);
			maintable.addCell(bottom_1_cell);

		} catch (Exception e) {
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

}
