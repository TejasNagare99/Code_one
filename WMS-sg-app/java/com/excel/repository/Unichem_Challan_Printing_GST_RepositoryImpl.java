package com.excel.repository;

import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.io.PrintStream;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.excel.configuration.JwtProvider;
import com.excel.model.PrePrintedDetailChallan_withgst;
import com.excel.service.PrintService;
import com.excel.service.UserMasterService;
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

@Repository
public class Unichem_Challan_Printing_GST_RepositoryImpl implements Unichem_Challan_Printing_GST_Repository {
	@Autowired
	private UserMasterRepository userMasterRepository;
	@Autowired private UserMasterService usermasterservice;

@Autowired private JwtProvider tokenProvider;
@Autowired private UserMasterService userMasterService;

	PrePrintedDetailChallan_withgst footer;
	@Autowired PrintService printservice;
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
	private float[] bodyTableWidth = new float[] { 4.5f, 1f, 1.5f, 1f, 1f, 1f };
	private String CompanyName;

	@Override
	public String getPdf(int division, Long loc, String frm_challan, String to_challan, String dispatchType,
			String prodtype, String printtype, List<PrePrintedDetailChallan_withgst> challans, String show_amount,
			String footer_signature_ind, String Companycode, String Companyname,HttpSession session,HttpServletRequest request) throws Exception {


		//ServletContext context = ServletActionContext.getRequest().getServletContext();
		
		String path = MedicoConstants.PDF_FILE_PATH;
		comp_cd = Companycode;
		CompanyName = Companyname;
		setPath(path);

		// System.out.println("path 11 : " + path);
		FileOutputStream os = null;
		PrintStream ps = null;
		long timevar = new Date().getTime();
		String filename = "challan_" + timevar + ".pdf";
		// System.out.println("filename 11 " +filename);
		File docfile = new File(path + "\\files\\" + filename);
		OutputStream file = new FileOutputStream(docfile);
		Document document = new Document(PageSize.A4);
		PdfWriter writer = PdfWriter.getInstance(document, file);
		Rectangle rect = new Rectangle(20, 28, 580, 820);
		writer.setBoxSize("header", rect);
		rect = new Rectangle(20, 2, 580, 247);
		writer.setBoxSize("footer", rect);
		int pagecount = 1;
		FooterWithoutPageNo event = new FooterWithoutPageNo();
		writer.setPageEvent(event);
		String uname = this.tokenProvider.getUsernameFromRequest(request);String empId = this.userMasterService.getEmpIdByUsername(uname);
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
				bodytable = new PdfPTable(6);
				bodytable.getDefaultCell().setPadding(0.0f);
				bodytable.setWidthPercentage(100);
				bodytable.setWidths(bodyTableWidth);
				total = BigDecimal.ZERO;

				String dtp = "";
				String ship_doc_no = "";
				
				String gst_doc_type="";
				String date = "";
				String cases = "";
				String weigh = "";
				String dsp_challan_msg = "";
				// int count_var=0;
				for (PrePrintedDetailChallan_withgst challan : challans) {

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

							bodytable = newPage(column, bodytable, event, printLabel, false, rows, pageflag, pagecount,
									total, division, loc, frm_challan, to_challan, dispatchType, prodtype, path,
									challans, show_amount, footer_ind, challan.getDsp_transporter(),
									challan.getShip_doc_no(), challan.getShip_doc_date(), challan.getDsp_cases(),
									challan.getWeigh().toString(),
									challan.getAlloc_remark().length() > 600
											? challan.getAlloc_remark().substring(0, 600) : challan.getAlloc_remark(),
									goods_total, final_total, header_Igst, header_Cgst, header_Sgst, round_off, gst_doc_type);

							document.newPage();
							pagecount++;
							rows = 0;

						}
						if (isnew) {
							footer = challan;
							maintable1.addCell(PdfStyle.createLabelCellWithoutBorderSmallFontNoBold(""));
							hcell = createHeader(challan, pagecount, division, loc, frm_challan, to_challan,
									dispatchType, prodtype, path, show_amount,Companyname);
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
							/*
							 * bodytable.addCell(PdfStyle.
							 * createValueCellWithHeight((""),false,true,18f));
							 * bodytable.addCell(PdfStyle.
							 * createValueCellWithHeight((""),false,true,18f));
							 * bodytable.addCell(PdfStyle.
							 * createValueCellWithHeight((""),false,true,18f));
							 * bodytable.addCell(PdfStyle.
							 * createValueCellWithHeight((""),true,true,18f));
							 * bodytable.addCell(PdfStyle.
							 * createValueCellWithHeight((""),true,true,18f));
							 * bodytable.addCell(PdfStyle.
							 * createValueCellWithHeight((""),true,true,18f));
							 */

							/*
							 * bodytable.addCell(PdfStyle.
							 * createValueCellWithHeight((" "),false,true,18f));
							 * bodytable.addCell(PdfStyle.
							 * createValueCellWithHeight((""),false,true,18f));
							 * bodytable.addCell(PdfStyle.
							 * createValueCellWithHeight((""),false,true,18f));
							 * bodytable.addCell(PdfStyle.
							 * createValueCellWithHeight((""),true,true,18f));
							 * bodytable.addCell(PdfStyle.
							 * createValueCellWithHeight((""),true,true,18f));
							 * bodytable.addCell(PdfStyle.
							 * createValueCellWithHeight((""),true,true,18f));
							 */
						}
						bodytable.addCell(PdfStyle.createUltimateCellWithBorderWithDisableBorder(
								(challan.getProd_name_pack()), Element.ALIGN_LEFT, Element.ALIGN_TOP, 8, 18f, false, 1,
								1, false, false, false, true, false));
						bodytable.addCell(PdfStyle.createUltimateCellWithBorderWithDisableBorder(
								(challan.getHsn_code()), Element.ALIGN_LEFT, Element.ALIGN_TOP, 8, 18f, false, 1, 1,
								false, false, false, true, false));
						bodytable.addCell(PdfStyle.createUltimateCellWithBorderWithDisableBorder(
								(challan.getBatch_no()), Element.ALIGN_LEFT, Element.ALIGN_TOP, 8, 18f, false, 1, 1,
								false, false, false, true, false));
						bodytable.addCell(PdfStyle.createUltimateCellWithBorderWithDisableBorder(
								(challan.getBatch_expiry_dt()), Element.ALIGN_CENTER, Element.ALIGN_TOP, 8, 18f, false,
								1, 1, false, false, false, true, false));
						bodytable.addCell(PdfStyle.createUltimateCellWithBorderWithDisableBorder(
								(challan.getDspdtl_disp_qty().toString()), Element.ALIGN_CENTER, Element.ALIGN_TOP, 8,
								18f, false, 1, 1, false, false, false, true, false));
						bodytable.addCell(PdfStyle.createUltimateCellWithBorderWithDisableBorder(
								(challan.getDisp_value() == null ? BigDecimal.ZERO.setScale(2, RoundingMode.HALF_UP)
										: challan.getDisp_value().setScale(2, RoundingMode.HALF_UP)).toString(),
								Element.ALIGN_RIGHT, Element.ALIGN_TOP, 8, 18f, false, 1, 1, false, false, false, true,
								false));
						System.out.println("GST DESC "+challan.getGst_description());
						if ((challan.getGst_description().contains("INTER"))) {
							bodytable.addCell(PdfStyle.createUltimateCellWithBorderWithDisableBorder(("IGST% "),
									Element.ALIGN_RIGHT, Element.ALIGN_TOP, 8, 18f, true, 1, 1, false, false, false,
									true, false));
							bodytable.addCell(PdfStyle.createUltimateCellWithBorderWithDisableBorder(
									(challan.getIgst_rate()).toString(), Element.ALIGN_RIGHT, Element.ALIGN_TOP, 8,
									18f, true, 1, 1, false, false, false, true, false));
							bodytable.addCell(PdfStyle.createUltimateCellWithBorderWithDisableBorder(("IGST"),
									Element.ALIGN_RIGHT, Element.ALIGN_TOP, 8, 18f, true, 1, 1, false, false, false,
									true, false));
							bodytable
									.addCell(
											PdfStyle.createUltimateCellWithBorderWithDisableBorder(
													(challan.getIgst_bill_amt() == null
															? BigDecimal.ZERO.setScale(2, RoundingMode.HALF_UP)
															: challan.getIgst_bill_amt().setScale(2,
																	RoundingMode.HALF_UP)).toPlainString(),
													Element.ALIGN_RIGHT, Element.ALIGN_TOP, 8, 18f, true, 1, 1, false,
													false, false, true, false));
							bodytable.addCell(
									PdfStyle.createUltimateCellWithBorderWithDisableBorder((""), Element.ALIGN_LEFT,
											Element.ALIGN_TOP, 8, 18f, true, 1, 1, false, false, false, true, false));
							bodytable.addCell(
									PdfStyle.createUltimateCellWithBorderWithDisableBorder((""), Element.ALIGN_LEFT,
											Element.ALIGN_TOP, 8, 18f, true, 1, 1, false, false, false, true, false));
						} else if(challan.getGst_description().contains("INTRA")) {
							bodytable
									.addCell(PdfStyle
											.createUltimateCellWithBorderWithDisableBorder(
													("CGST% :" + challan.getCgst_rate()
															+ "                                                                        CGST :"
															+ challan.getCgst_bill_amt().setScale(2,
																	RoundingMode.HALF_UP)),
													Element.ALIGN_LEFT, Element.ALIGN_TOP, 8, 18f, true, 1, 1, false,
													false, false, true, false));
							bodytable.addCell(PdfStyle.createUltimateCellWithBorderWithDisableBorder(("SGST%"),
									Element.ALIGN_RIGHT, Element.ALIGN_TOP, 8, 18f, true, 1, 1, false, false, false,
									true, false));
							bodytable.addCell(PdfStyle.createUltimateCellWithBorderWithDisableBorder(
									(challan.getSgst_rate().toString()), Element.ALIGN_RIGHT, Element.ALIGN_TOP, 8, 18f,
									true, 1, 1, false, false, false, true, false));
							bodytable.addCell(PdfStyle.createUltimateCellWithBorderWithDisableBorder(
									("SGST").toString(), Element.ALIGN_RIGHT, Element.ALIGN_TOP, 8, 18f, true, 1, 1,
									false, false, false, true, false));
							bodytable.addCell(PdfStyle.createUltimateCellWithBorderWithDisableBorder(
									(challan.getSgst_bill_amt().toString()), Element.ALIGN_RIGHT, Element.ALIGN_TOP, 8,
									18f, true, 1, 1, false, false, false, true, false));
							bodytable.addCell(
									PdfStyle.createUltimateCellWithBorderWithDisableBorder((""), Element.ALIGN_RIGHT,
											Element.ALIGN_TOP, 8, 18f, true, 1, 1, false, false, false, true, false));
						}
						goods_total = goods_total.add(challan.getDisp_value());
						final_total = goods_total.add(challan.getHigst_bill_amt().add(challan.getHcgst_bill_amt()
								.add(challan.getHsgst_bill_amt().add(challan.getRoundoff()))));
						header_Igst = challan.getHigst_bill_amt();
						header_Sgst = challan.getHsgst_bill_amt();
						header_Cgst = challan.getHcgst_bill_amt();
						round_off = challan.getRoundoff();
						gst_doc_type =  challan.getGst_description();

						rows++;
						dtp = "";
						ship_doc_no = "";
						date = "";
						cases = "";
						weigh = "";
						dsp_challan_msg = "";

						dtp = challan.getDsp_transporter();
						ship_doc_no = challan.getShip_doc_no();
						date = challan.getShip_doc_date();
						cases = challan.getDsp_cases();
						weigh = challan.getWeigh().toString();
						// dsp_challan_msg is the remark that is currently
						// printed.
						dsp_challan_msg = challan.getAlloc_remark().length() > 600
								? challan.getAlloc_remark().substring(0, 600) : challan.getAlloc_remark();
					}
				}

				footer_ind = true;
				// System.out.println("Outside rows>=limit");
				newPage(column, bodytable, event, printLabel, true, rows, pageflag, pagecount, total, division, loc,
						frm_challan, to_challan, dispatchType, prodtype, path, list_new_page, show_amount,
						footer_ind, dtp, ship_doc_no, date, cases, weigh, dsp_challan_msg, goods_total, final_total,
						header_Igst, header_Cgst, header_Sgst, round_off,gst_doc_type);
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
	

	

	private PdfPTable createHeader(PrePrintedDetailChallan_withgst challan, int pagecount, int division, Long loc,
			String frm_challan, String to_challan, String dispatchType, String prodtype, String path,
			String show_amount ,String Companyname) throws Exception {
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
		headertable2.setWidths(new float[] { 3.5f, 3.5f, 1f, 2f });
		headertable3.setWidths(new float[] { 4.5f, 2.5f, 1f, 2f });
		headertable4.setWidths(new float[] { 2f, 2f, 2.5f, 3.5f });

		Font font = new Font(FontFamily.HELVETICA, 13, Font.BOLD, BaseColor.BLACK);
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
		} else {
			hcell = new PdfPCell(new Phrase("Delivery Challan", font));
		}

		hcell.setHorizontalAlignment(Element.ALIGN_CENTER);
		hcell.setVerticalAlignment(Element.ALIGN_CENTER);

		PdfPCell hcell_1 = new PdfPCell(new Phrase(" ", font));
		hcell_1.setBorder(Rectangle.LEFT);
		hcell_1.setHorizontalAlignment(Element.ALIGN_CENTER);
		headertable01.addCell(hcell);

		// =================================================Blank Cell Added To
		// Remove Table Border==============================

		PdfPCell hcell_blank = new PdfPCell(headertable01);
		hcell_blank.setBorder(Rectangle.RIGHT);
		hcell_blank.disableBorderSide(Rectangle.BOTTOM);

		// ======================================================================================================================
		// 2.1
		String company_name =Companyname;
		/*
		 * System.out.println("ad 1 "+challan.getLocadd1());
		 * System.out.println("ad 2 "+challan.getLocadd2());
		 * System.out.println("ad 3 "+challan.getLocadd3());
		 * System.out.println("ad 4 "+challan.getLocadd4());
		 */

		StringBuilder result1 = new StringBuilder();
		/*result1.append(
				challan.getLocadd1().length() > 40 ? challan.getLocadd1().substring(0, 40) : challan.getLocadd1());
		result1.append(System.lineSeparator());*/

		/*
		 * result1.append(challan.getLocadd2().length()>40 ?
		 * challan.getLocadd2().substring(0, 40) : challan.getLocadd2());
		 * result1.append(System.lineSeparator());
		 */
		// result1.append(challan.getLocadd2()+" ");

		// hardcoded these changes as per user requirement on 7th aug 2017 need
		// to check later
		/*System.out.println("Type of product " + challan.getSmp_prod_type());
		if (challan.getSmp_prod_type().trim().equalsIgnoreCase("Promotional Input")) {
			result1.append("BLDG NO. E 6, GALA NO. 1 to 4");
			result1.append(System.lineSeparator());
		} else {
			result1.append("BLDG NO. E 6, GALA NO. 1 to 8 FIRST FLOOR");
			result1.append(System.lineSeparator());
		}
		result1.append("SHREE ARIHANT GODOWN COMPLEX");
		result1.append(System.lineSeparator());*/

		/*result1.append(challan.getLocadd3().length() > 40 ? challan.getLocadd3().trim().substring(0, 40)
				: challan.getLocadd3().trim());
		result1.append(System.lineSeparator());

		result1.append(
				challan.getLocadd4().length() > 40 ? challan.getLocadd4().substring(0, 40) : challan.getLocadd4());
		result1.append(System.lineSeparator());*/
		result1.append(challan.getLocadd1().trim());
		result1.append(System.lineSeparator());
		result1.append(challan.getLocadd2().trim());
		result1.append(System.lineSeparator());
		result1.append(challan.getLocadd3().trim());
		result1.append(System.lineSeparator());
		result1.append(challan.getLocadd4().trim());
		result1.append(System.lineSeparator());
		

		result1.append("State :" + challan.getLoc_statename() + "  State Code :" + challan.getLoc_statecode());
		result1.append(System.lineSeparator());

		PdfPCell hcell_cn = new PdfPCell(PdfStyle.createLabelCellWithBorderLargeFontNoBold(company_name));
		hcell_cn.setHorizontalAlignment(Element.ALIGN_LEFT);
		hcell_cn.setBorder(Rectangle.LEFT);

		PdfPCell hcell2w = new PdfPCell(PdfStyle.createLabelCellWithBorderSmallFontNoBoldForHeader(result1.toString()));
		hcell2w.setHorizontalAlignment(Element.ALIGN_LEFT);
		hcell2w.setBorder(Rectangle.LEFT);

		PdfPTable addressTbl_a = new PdfPTable(1);
		addressTbl_a.addCell(hcell_cn);
		addressTbl_a.addCell(hcell2w);

		PdfPCell hcella_4 = new PdfPCell(PdfStyle.createLabelCellWithoutBorderSmallFontNoPad("Drug Lic No:"));
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
		System.out.println("pan num receviced "+challan.getDptpan_no());
		PdfPCell hcella_4_8 = new PdfPCell(PdfStyle.createLabelCellWithBorderSmallFontNoBoldForHeader("AAACU0551B"));// PAN
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
				PdfStyle.createLabelCellWithBorderSmallFontNoBoldForHeader(challan.getLoc_web_site()));
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

		PdfPCell hcella_11 = new PdfPCell(PdfStyle.createLabelCellWithoutBorderSmallFontNoPad("Email Id :"));
		hcella_11.setHorizontalAlignment(Element.ALIGN_LEFT);
		hcella_11.setVerticalAlignment(Element.ALIGN_TOP);
		hcella_11.setBorder(Rectangle.LEFT);

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

		// ======================================================================================================================
		// 2.3

		comp_cd = Companyname;

		PdfPCell hcell16 = new PdfPCell(PdfStyle.createLabelCellWithBorderSmallFont("Date "));
		hcell16.setHorizontalAlignment(Element.ALIGN_LEFT);

		PdfPCell hcell20 = null;

		if (comp_cd.equalsIgnoreCase("PFZ")) {
			hcell20 = new PdfPCell(PdfStyle.createLabelCellWithBorderSmallFont("Challan #"));
			hcell20.setHorizontalAlignment(Element.ALIGN_LEFT);

		} else if (comp_cd.equalsIgnoreCase("UCM")) {
			hcell20 = new PdfPCell(PdfStyle.createLabelCellWithBorderSmallFont("GST INV No#"));
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

		PdfPCell hcell24 = new PdfPCell(PdfStyle.createLabelCellWithBorderSmallFont("Remark"));
		hcell24.setHorizontalAlignment(Element.ALIGN_LEFT);

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

		PdfPCell hcell30 = new PdfPCell(PdfStyle.createLabelCellWithBorderSmallFontNoBold(
				(challan.getDsp_challan_no() == null ? "" : challan.getDsp_challan_no())));
		hcell30.setHorizontalAlignment(Element.ALIGN_LEFT);

		PdfPCell hcell31 = new PdfPCell(
				PdfStyle.createLabelCellWithBorderSmallFontNoBold((challan.getDspfstaff_displayname().length() > 20
						? challan.getDspfstaff_displayname().substring(0, 20) : challan.getDspfstaff_displayname())));
		hcell31.setHorizontalAlignment(Element.ALIGN_LEFT);

		PdfPCell hcell32 = new PdfPCell(
				PdfStyle.createLabelCellWithBorderSmallFontNoBold((challan.getDivision().length() > 28
						? challan.getDivision().substring(0, 28) : challan.getDivision())));
		hcell32.setHorizontalAlignment(Element.ALIGN_LEFT);

		PdfPCell hcell33 = new PdfPCell(PdfStyle.createLabelCellWithBorderSmallFontNoBold(
				((challan.getTeam() == null ? "" : challan.getTeam()).length() > 28 ? challan.getTeam().substring(0, 28)
						: challan.getTeam() == null ? "" : challan.getTeam())));
		hcell33.setHorizontalAlignment(Element.ALIGN_LEFT);

		PdfPCell hcell34 = new PdfPCell(PdfStyle.createLabelCellWithBorderSmallFontNoBold(
				challan.getDsp_challan_msg() == null ? "" : challan.getDsp_challan_msg()));
		hcell34.setHorizontalAlignment(Element.ALIGN_LEFT);

		PdfPCell hcell27 = new PdfPCell(
				PdfStyle.createLabelCellWithBorderSmallFontNoBoldTwiceHeight((challan.getDspfstaff_email().length() > 40
						? challan.getDspfstaff_email().substring(0, 40) : challan.getDspfstaff_email())));
		hcell27.setHorizontalAlignment(Element.ALIGN_LEFT);

		PdfPCell hcell28 = new PdfPCell(PdfStyle.createLabelCellWithBorderSmallFontNoBold(
				(challan.getDspfstaff_mobile() == null ? "" : challan.getDspfstaff_mobile())));
		hcell28.setHorizontalAlignment(Element.ALIGN_LEFT);

		PdfPCell hcell29 = new PdfPCell(PdfStyle.createLabelCellWithBorderSmallFontNoBold((""))); // telephone
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
		hcell35.setHorizontalAlignment(Element.ALIGN_LEFT);

		PdfPCell hcell36 = new PdfPCell(PdfStyle.createLabelCellWithoutBorder(
				challan.getDspfstaff_displayname() == null ? "" : challan.getDspfstaff_displayname()));
		hcell36.setHorizontalAlignment(Element.ALIGN_LEFT);
		hcell36.enableBorderSide(Rectangle.LEFT);
		hcell36.enableBorderSide(Rectangle.RIGHT);
		StringBuilder result2 = new StringBuilder();
		result2.append(challan.getDspfstaffaddr1().length() > 50 ? challan.getDspfstaffaddr1().substring(0, 50)
				: challan.getDspfstaffaddr1());
		result2.append(System.lineSeparator());
		result2.append(challan.getDspfstaffaddr2().length() > 50 ? challan.getDspfstaffaddr2().substring(0, 50)
				: challan.getDspfstaffaddr2());
		result2.append(System.lineSeparator());
		result2.append(challan.getDspfstaffaddr3().length() > 50 ? challan.getDspfstaffaddr3().substring(0, 50)
				: challan.getDspfstaffaddr3());
		result2.append(System.lineSeparator());
		/*
		 * result2.append(challan.getDspfstaffaddr4().length()>50 ?
		 * challan.getDspfstaffaddr4().substring(0, 50) :
		 * challan.getDspfstaffaddr4()); result2.append(System.lineSeparator());
		 */
		result2.append(challan.getDspfstaffaddr4());
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
		String address1 = challan.getLocadd1();
		//String address2 = (String) context.getAttribute("address2");
		String address2 =challan.getLocadd2();
		String address3 = challan.getLocadd3();
		String address4 = challan.getLocadd4();
		// WEB_SITE cinno
		String web_site = challan.getCf_web_site() == null ? " "
				: challan.getCf_web_site();
		String cinno = challan.getCf_cinno() == null ? " " : challan.getCf_cinno();
		/*System.out.println("website "+(String)context.getAttribute("web_site"));
		System.out.println("cin no "+(String)context.getAttribute("cinno"));*/
		StringBuilder result3 = new StringBuilder();

		// Company name added 27-1-2017
		if (company_name.trim().isEmpty()) {
			result3.append(System.lineSeparator());
		} else {
			result3.append(company_name.length() > 30 ? company_name.substring(0, 30) : company_name);
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
		if (web_site.trim().isEmpty()) {
			result3.append("");
			result3.append(System.lineSeparator());
		} else {
			result3.append(web_site.length() > 33 ? "Website:" + web_site.substring(0, 33) : "Website:" + web_site);
			result3.append(System.lineSeparator());

		}
		if (cinno.trim().isEmpty()) {
			result3.append("");
			result3.append(System.lineSeparator());
		} else {
			result3.append(cinno.length() > 33 ? "CIN NO:" + cinno.substring(0, 33) : "CIN NO:" + cinno);
			result3.append(System.lineSeparator());
		}

		PdfPCell hcell38 = new PdfPCell(PdfStyle.createLabelCellWithBorderSmallFont("Principal place of buisness"));
		hcell38.setHorizontalAlignment(Element.ALIGN_LEFT);

		PdfPCell hcell39 = new PdfPCell(PdfStyle.createLabelCellWithoutBorderSmallFontNoBold(
				result3.length() > 180 ? result3.substring(0, 180) : result3.toString()));
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
		result4.append("Free samples and/"); // Goods not for sale. Free samples
												// and / or promotional inputs
												// only having no commercial
												// value
		result4.append(System.lineSeparator());
		result4.append("or promotional inputs");
		result4.append(System.lineSeparator());
		result4.append("only having no");
		result4.append(System.lineSeparator());
		result4.append("commercial value.");
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
			bodytable.addCell(PdfStyle.createLabelWithBGColour(""));
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
			BigDecimal total, int division, Long loc, String frm_challan, String to_challan, String dispatchType,
			String prodtype, String path, List<PrePrintedDetailChallan_withgst> challan,
			String show_amount, boolean footer_ind, String dsp_transporter, String ship_doc_no, String date,
			String cases, String weigh, String dsp_challan_msg, BigDecimal goods_total, BigDecimal final_total,
			BigDecimal header_Igst, BigDecimal header_Cgst, BigDecimal header_Sgst, BigDecimal round_off, String gst_doc_type)
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
			column.setSimpleColumn(20, 0, 580, 500);
			column.go();

			bodytable = new PdfPTable(6);
			bodytable.setWidthPercentage(100);
			bodytable.setWidths(bodyTableWidth);

			event.setFooter(createFooter(printLabel, isFooterData, pageflag, pagecount, total, division, loc,
					frm_challan, to_challan, dispatchType, prodtype, path, challan, show_amount, footer_ind,
					dsp_transporter, ship_doc_no, date, cases, weigh, dsp_challan_msg, goods_total, final_total,
					header_Igst, header_Cgst, header_Sgst, round_off, gst_doc_type,CompanyName));

		} catch (DocumentException e) {
			throw e;
		}
		return bodytable;
	}

	private PdfPTable createFooter(HashMap<String, String> printLabel, boolean isFooterData, boolean pageflag,
			int pagecount, BigDecimal total, int division, Long loc, String frm_challan, String to_challan,
			String dispatchType, String prodtype, String path,
			List<PrePrintedDetailChallan_withgst> challan, String show_amount, boolean footer_ind,
			String dsp_transporter, String ship_doc_no, String date, String cases, String weigh, String dsp_challan_msg,
			BigDecimal goods_total, BigDecimal final_total, BigDecimal header_Igst, BigDecimal header_Cgst,
			BigDecimal header_Sgst, BigDecimal round_off, String gst_doc_type,String Companyname) throws DocumentException {
		PdfPTable maintable = new PdfPTable(2);
		System.out.println("ship_doc_no::"+ship_doc_no+"dsp_transporter::"+dsp_transporter+"cases:"+cases+"date:::");
		String tempDate="";
		if(null!=date){
			tempDate=getDate(date.substring(0,10));
		}
		// System.out.println("challan size() " +challan.size());
		try {

			PdfPCell cell = null;
			cell = new PdfPCell();

			String narrration = printservice.getLocationNarrationByLocId(loc);
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

			// footer right cell
			PdfPTable foot_right_table = new PdfPTable(2);
			foot_right_table.setWidthPercentage(100);
			foot_right_table.setWidths(new float[] { 5.55f, 4.45f });

			PdfPCell foot_bottom_right_cell1 = new PdfPCell(
					PdfStyle.createUltimateCellWithBorderWithDisableBorder(("Goods Value"), Element.ALIGN_LEFT,
							Element.ALIGN_TOP, 8, 14f, true, 1, 1, false, true, true, false, true));
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
				PdfPCell foot_bottom_right_cell43 = new PdfPCell(PdfStyle.createUltimateCellWithBorderWithDisableBorder(
						(header_Igst.setScale(2, BigDecimal.ROUND_HALF_UP).toString()), Element.ALIGN_RIGHT,
						Element.ALIGN_TOP, 8, 14f, true, 1, 1, false, true, true, false, true));
				foot_right_table.addCell(foot_bottom_right_cell43);
			} else if(gst_doc_type.contains("INTRA")) {
				PdfPCell foot_bottom_right_cell3 = new PdfPCell(
						PdfStyle.createUltimateCellWithBorderWithDisableBorder(("CGST"), Element.ALIGN_LEFT,
								Element.ALIGN_TOP, 8, 14f, true, 1, 1, false, true, true, false, true));
				foot_right_table.addCell(foot_bottom_right_cell3);
				PdfPCell foot_bottom_right_cell4 = new PdfPCell(PdfStyle.createUltimateCellWithBorderWithDisableBorder(
						(header_Cgst.setScale(2, BigDecimal.ROUND_HALF_UP).toString()), Element.ALIGN_RIGHT,
						Element.ALIGN_TOP, 8, 14f, true, 1, 1, false, true, true, false, true));
				foot_right_table.addCell(foot_bottom_right_cell4);
				PdfPCell foot_bottom_right_cell5 = new PdfPCell(
						PdfStyle.createUltimateCellWithBorderWithDisableBorder(("SGST"), Element.ALIGN_LEFT,
								Element.ALIGN_TOP, 8, 14f, true, 1, 1, false, true, true, false, true));
				foot_right_table.addCell(foot_bottom_right_cell5);
				PdfPCell foot_bottom_right_cell6 = new PdfPCell(PdfStyle.createUltimateCellWithBorderWithDisableBorder(
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
			foot_right_table.addCell(foot_bottom_right_cell7);
			PdfPCell foot_bottom_right_cell8 = new PdfPCell(PdfStyle.createUltimateCellWithBorderWithDisableBorder(
					(final_total.setScale(2, BigDecimal.ROUND_HALF_UP).toString()), Element.ALIGN_RIGHT,
					Element.ALIGN_TOP, 8, 14f, true, 1, 1, false, true, true, false, true));
			foot_bottom_right_cell8.setGrayFill(0.7f);
			foot_right_table.addCell(foot_bottom_right_cell8);

			// footer bottom2
			PdfPTable foot_bottom_table2 = new PdfPTable(3);
			foot_bottom_table2.setWidthPercentage(100);
			foot_bottom_table2.setWidths(new float[] { 3.33f, 2.17f, 4.5f });

			String company_name = Companyname;

			PdfPCell foot_bottom_cell02 = new PdfPCell(PdfStyle.createUltimateCellWithBorderWithDisableBorder(
					("Transporter Details"), Element.ALIGN_CENTER, Element.ALIGN_TOP, 9, 14f, true, 1, 1, false, true,
					true, false, true));
			foot_bottom_table2.addCell(foot_bottom_cell02);
			PdfPCell foot_bottom_cell03 = new PdfPCell(PdfStyle.createUltimateCellWithBorderWithDisableBorder((""),
					Element.ALIGN_LEFT, Element.ALIGN_TOP, 8, 14f, true, 1, 1, false, true, true, false, true));
			foot_bottom_table2.addCell(foot_bottom_cell03);
			PdfPCell foot_bottom_cell04 = new PdfPCell(PdfStyle.createUltimateCellWithBorderWithDisableBorder(
					("For " + company_name), Element.ALIGN_CENTER, Element.ALIGN_TOP, 9, 14f, true, 1, 1, false, true,
					true, false, true));
			foot_bottom_table2.addCell(foot_bottom_cell04);

			PdfPCell foot_bottom_cell21 = new PdfPCell(
					PdfStyle.createUltimateCellWithBorderWithDisableBorder(("Transporter Name : "+dsp_transporter), Element.ALIGN_LEFT,
							Element.ALIGN_TOP, 8, 14f, true, 1, 1, false, true, true, false, true));
			foot_bottom_table2.addCell(foot_bottom_cell21);
			PdfPCell foot_bottom_cell22 = new PdfPCell(PdfStyle.createUltimateCellWithBorderWithDisableBorder((""),
					Element.ALIGN_CENTER, Element.ALIGN_TOP, 8, 14f, true, 1, 1, false, true, true, false, true));
			foot_bottom_table2.addCell(foot_bottom_cell22);
			PdfPCell foot_bottom_cell32 = new PdfPCell(PdfStyle.createUltimateCellWithBorderWithDisableBorder((""),
					Element.ALIGN_CENTER, Element.ALIGN_TOP, 8, 14f, true, 1, 1, false, false, true, false, true));
			foot_bottom_table2.addCell(foot_bottom_cell32);

			PdfPCell foot_bottom_cell06 = new PdfPCell(
					PdfStyle.createUltimateCellWithBorderWithDisableBorder(("LR No: "+ship_doc_no), Element.ALIGN_LEFT,
							Element.ALIGN_TOP, 8, 14f, true, 1, 1, false, true, true, false, true));
			foot_bottom_table2.addCell(foot_bottom_cell06);
			PdfPCell foot_bottom_cell42 = new PdfPCell(
					PdfStyle.createUltimateCellWithBorderWithDisableBorder(("LR Date : "+tempDate), Element.ALIGN_LEFT,
							Element.ALIGN_TOP, 8, 14f, true, 1, 1, false, true, true, false, true));
			foot_bottom_table2.addCell(foot_bottom_cell42);
			PdfPCell foot_bottom_cell07 = new PdfPCell(PdfStyle.createUltimateCellWithBorderWithDisableBorder((""),
					Element.ALIGN_LEFT, Element.ALIGN_TOP, 8, 14f, true, 1, 1, false, false, true, false, true));
			foot_bottom_table2.addCell(foot_bottom_cell07);

			PdfPCell foot_bottom_cell52 = new PdfPCell(
					PdfStyle.createUltimateCellWithBorderWithDisableBorder(("Way Bill No: "), Element.ALIGN_LEFT,
							Element.ALIGN_TOP, 8, 14f, true, 1, 1, false, true, true, false, true));
			foot_bottom_table2.addCell(foot_bottom_cell52);
			PdfPCell foot_bottom_cell62 = new PdfPCell(
					PdfStyle.createUltimateCellWithBorderWithDisableBorder(("Date : "), Element.ALIGN_LEFT,
							Element.ALIGN_TOP, 8, 14f, true, 1, 1, false, true, true, false, true));
			foot_bottom_table2.addCell(foot_bottom_cell62);
			PdfPCell foot_bottom_cell08 = new PdfPCell(PdfStyle.createUltimateCellWithBorderWithDisableBorder((""),
					Element.ALIGN_LEFT, Element.ALIGN_TOP, 8, 14f, true, 1, 1, false, false, true, false, true));
			foot_bottom_table2.addCell(foot_bottom_cell08);

			PdfPCell foot_bottom_cell72 = new PdfPCell(
					PdfStyle.createUltimateCellWithBorderWithDisableBorder(("No Of Cases : "+cases), Element.ALIGN_LEFT,
							Element.ALIGN_TOP, 8, 14f, true, 1, 1, false, true, true, false, true));
			foot_bottom_table2.addCell(foot_bottom_cell72);
			PdfPCell foot_bottom_cell82 = new PdfPCell(
					PdfStyle.createUltimateCellWithBorderWithDisableBorder(("Consignment Weight : "+weigh), Element.ALIGN_LEFT,
							Element.ALIGN_TOP, 8, 14f, true, 1, 1, false, true, true, false, true));
			foot_bottom_table2.addCell(foot_bottom_cell82);
			PdfPCell foot_bottom_cell09 = new PdfPCell(PdfStyle.createUltimateCellWithBorderWithDisableBorder((""),
					Element.ALIGN_LEFT, Element.ALIGN_TOP, 8, 14f, true, 1, 1, false, false, true, false, true));
			foot_bottom_table2.addCell(foot_bottom_cell09);

			PdfPCell foot_bottom_cell92 = new PdfPCell(
					PdfStyle.createUltimateCellWithBorderWithDisableBorder(("Remarks : "+dsp_challan_msg), Element.ALIGN_LEFT,
							Element.ALIGN_TOP, 8, 14f, true, 1, 1, false, true, true, false, true));
			foot_bottom_table2.addCell(foot_bottom_cell92);
			PdfPCell foot_bottom_cell10 = new PdfPCell(PdfStyle.createUltimateCellWithBorderWithDisableBorder((""),
					Element.ALIGN_LEFT, Element.ALIGN_TOP, 8, 14f, true, 1, 1, false, false, true, true, true));
			foot_bottom_table2.addCell(foot_bottom_cell10);
			PdfPCell foot_bottom_cell11 = new PdfPCell(PdfStyle.createUltimateCellWithBorderWithDisableBorder(
					("Authorized Signatory"), Element.ALIGN_CENTER, Element.ALIGN_TOP, 9, 14f, true, 1, 1, false, true,
					true, false, true));
			foot_bottom_table2.addCell(foot_bottom_cell11);

			// footer bottom3
			PdfPTable foot_bottom_table3 = new PdfPTable(1);
			foot_bottom_table3.setWidthPercentage(100);
			foot_bottom_table3.setWidths(new float[] { 10f });

			PdfPCell foot_bottom_cell31 = new PdfPCell(PdfStyle.createUltimateCellWithBorderWithDisableBorder(
					("Copy: 1: ORIGINAL FOR CONSIGNEE;   2. DUPLICATE FOR TRANSPORTER;   3. TRIPLICATE FOR COSIGNOR;   4. EXTRA COPY "),
					Element.ALIGN_CENTER, Element.ALIGN_TOP, 9, 14f, true, 1, 1, true, true, false, false, false));
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
			System.out.println("Error Occurred :" +e.getMessage());// uncomment asneeded --;
		}
		return maintable;
	}
	public static String getDate(String date){
		String s[]= date.split("-");
		String newDate = s[2]+"/"+s[1]+"/"+s[0];
		return newDate;
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
