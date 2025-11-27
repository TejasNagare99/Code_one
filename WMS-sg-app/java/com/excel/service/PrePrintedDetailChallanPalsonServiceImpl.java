package com.excel.service;

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

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.excel.model.Company;
import com.excel.model.PrePrintedDetailChallan_withgst_pal;
import com.excel.model.ViewPrePrintedDetailChallan;
import com.excel.utility.CodifyErrors;
import com.excel.utility.FooterWithoutPageNo;
import com.excel.utility.MedicoConstants;
import com.excel.utility.PdfStyle;
import com.excel.utility.Utility;
import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Chunk;
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
public class PrePrintedDetailChallanPalsonServiceImpl implements PrePrintedDetailChallanPalsonService, MedicoConstants {
	@Autowired
	PrintService printService;
	PrePrintedDetailChallan_withgst_pal footer;
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

	private String company_name;

	@Override
	public String getPreprintedDetailedChallanPrintPalson(Integer division, Integer loc, String frm_challan,
			String to_challan, String dispatchType, String prodtype, String printtype,
			List<PrePrintedDetailChallan_withgst_pal> challans, String show_amount, String footer_signature_ind,
			String companyCode, String companyName, HttpSession session) throws Exception {
		PdfPTable MainsTable = null;
		float[] MainsTableWidth = new float[] { 1f };
		float[] bodyTableWidth = new float[] { 1f, 1f, 4f, 1f, 1.3f, 2.2f, 1f, 1f, 1f, 1f, 1f, 1.2f   };
		
		
		
		

		System.out.println("In get pdf ani");
		String path = MedicoConstants.PDF_FILE_PATH;
		File file1 = new File(path);
		file1.mkdirs();
		comp_cd = companyCode;
		company_name = companyName;
		setPath(path);

		System.out.println("PDF PALSON Generation Started********************************");
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
		// Rectangle rect = new Rectangle(20, 24, 580, 820);
		// writer.setBoxSize("header", rect);
		// rect = new Rectangle(20, 2, 580, 242);
		// writer.setBoxSize("footer", rect);
		int pagecount = 1;
		// FooterWithoutPageNo event = new FooterWithoutPageNo();
		// writer.setPageEvent(event);

		document.open();
		document.setMarginMirroringTopBottom(false);
		boolean isnew = false;
		// boolean for_new_page = false;
		// ColumnText column = new ColumnText(writer.getDirectContent());
		try {
			BigDecimal total = new BigDecimal(0);

			MainsTable = new PdfPTable(1);
			MainsTable.getDefaultCell().setPadding(0.0f);
			MainsTable.setWidthPercentage(100);
			MainsTable.getDefaultCell().setBorder(Rectangle.NO_BORDER);
			MainsTable.setWidths(MainsTableWidth);

			Set<String> challanset = new LinkedHashSet<String>();
			for (PrePrintedDetailChallan_withgst_pal challan : challans) {
				challanset.add(challan.getDsp_id().toString());
			}
			PdfPTable bodytable = null;
			boolean footer_ind = false;
			for (String num : challanset) {
				List<PrePrintedDetailChallan_withgst_pal> list_new_page = new ArrayList<PrePrintedDetailChallan_withgst_pal>();
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

				bodytable = new PdfPTable(12);

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
				boolean isHeader=false;
				String newChallanNo="";
				for (PrePrintedDetailChallan_withgst_pal challan : challans) {
					String oldChallan = challan.getDsp_challan_no();
					if (!oldChallan.equals(newChallanNo) || newChallanNo.equals(" ")) {
						count = 1;
					}
				
					// System.out.println("Alloc Remark
					// "+challan.getAlloc_remark());
					docType = challan.getGst_doc_type();

					if (num.equalsIgnoreCase(challan.getDsp_id().toString())) {
					
						System.out.println("rows  outside loop:::::::::"+rows);
						if (rows >= limit) {
						System.out.println("rows  inside loop:::::::::"+rows);
						//isHeader=true;
					
							// count_var++;

							if (footer_signature_ind.equalsIgnoreCase("Y")) {
								footer_ind = false;// suppress footer signature
							} else {
								footer_ind = true;// show footer signature
							}
							pageflag = true;

							bodytable = newPage(bodytable, printLabel, false, rows, pageflag, pagecount, total,
									division, loc, frm_challan, to_challan, dispatchType, prodtype, path, challans,
									show_amount, footer_ind, challan.getDsp_transporter(), challan.getShip_doc_no(),
									challan.getShip_doc_date(), challan.getDsp_cases(), challan.getWeigh().toString(),
									challan.getAlloc_remark().length() > 600
											? challan.getAlloc_remark().substring(0, 600)
											: challan.getAlloc_remark(),
									goods_total, final_total, header_Igst, header_Cgst, header_Sgst, round_off,
									gst_doc_type, dsp_lorry_no, bodyTableWidth, companyCode, igstAmounts, MainsTable);
							document.add(MainsTable);
							MainsTable.flushContent();
							//bodytable.flushContent();
							
							document.newPage();
							isnew=true;
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
							MainsTable.addCell(maintable1);
							document.add(MainsTable);
							MainsTable.addCell(bodytable);
							MainsTable.flushContent();
							bodytable.flushContent();
							maintable1.flushContent();
							// event.setHeader(maintable1);
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
//							bodytable.addCell(PdfStyle.createValueCellWithHeight((""), true, true, 18f));
//							bodytable.addCell(PdfStyle.createValueCellWithHeight((""), false, true, 18f));
//							bodytable.addCell(PdfStyle.createValueCellWithHeight((""), false, true, 18f));
//							bodytable.addCell(PdfStyle.createValueCellWithHeight((""), false, true, 18f));
//							bodytable.addCell(PdfStyle.createValueCellWithHeight((""), true, true, 18f));
//							bodytable.addCell(PdfStyle.createValueCellWithHeight((""), true, true, 18f));
							
					//		MainsTable.addCell(bodytable);

						} /// if for palson
						
						bodytable.addCell(PdfStyle.createUltimateCellWithBorderWithDisableBorder((count +" "), Element.ALIGN_CENTER, Element.ALIGN_TOP, 8, 12f, false, 1,
								1, false, false, false, true, false));
						
						
						bodytable.addCell(PdfStyle.createUltimateCellWithBorderWithDisableBorder((challan.getSmp_prod_cd()), Element.ALIGN_CENTER, Element.ALIGN_TOP, 8, 12f, false, 1,
								1, false, false, false, true, false));
						
				
						bodytable.addCell(PdfStyle.createUltimateCellWithBorderWithDisableBorder((challan.getProd_name_pack()), Element.ALIGN_LEFT, Element.ALIGN_LEFT, 8, 12f, false, 1,
								1, false, false, false, true, false));
						
						bodytable.addCell(PdfStyle.createUltimateCellWithBorderWithDisableBorder((challan.getPack_disp_nm()), Element.ALIGN_CENTER, Element.ALIGN_CENTER, 8, 12f, false, 1,
								1, false, false, false, true, false));
						
						bodytable.addCell(PdfStyle.createUltimateCellWithBorderWithDisableBorder((challan.getUom_disp_nm()), Element.ALIGN_CENTER, Element.ALIGN_CENTER, 8, 12f, false, 1,
								1, false, false, false, true, false));
						
					
						
						bodytable.addCell(PdfStyle.createUltimateCellWithBorderWithDisableBorder((challan.getBatch_no()), Element.ALIGN_RIGHT, Element.ALIGN_TOP, 8, 12f, false, 1, 1,
								false, false, false, true, false));
											
						bodytable.addCell(PdfStyle.createUltimateCellWithBorderWithDisableBorder((challan.getDspdtl_disp_qty().toString()), Element.ALIGN_CENTER, Element.ALIGN_TOP, 8,
								12f, false, 1, 1, false, false, false, true, false));
						
						bodytable.addCell(PdfStyle.createUltimateCellWithBorderWithDisableBorder(("0.00").toString(),
										Element.ALIGN_CENTER, Element.ALIGN_TOP, 8, 12f, false, 1, 1, false,
										false, false, true, false));
		
					
						bodytable.addCell(PdfStyle.createUltimateCellWithBorderWithDisableBorder(("0.00").toString(),Element.ALIGN_CENTER, Element.ALIGN_TOP,
								8, 12f, false, 1, 1, false, false, false,
								true, false));
						
						bodytable.addCell(PdfStyle.createUltimateCellWithBorderWithDisableBorder(("0.00").toString(),Element.ALIGN_CENTER, Element.ALIGN_TOP,
								8, 12f, false, 1, 1, false, false, false,
								true, false));
						
						bodytable.addCell(PdfStyle.createUltimateCellWithBorderWithDisableBorder(("0.00").toString(),Element.ALIGN_CENTER, Element.ALIGN_TOP,
								8, 12f, false, 1, 1, false, false, false,
								true, false));
						
						bodytable.addCell(PdfStyle.createUltimateCellWithBorderWithDisableBorder(("0.00").toString(),Element.ALIGN_CENTER, Element.ALIGN_TOP,
								8, 12f, false, 1, 1, false, false, false,
								true, false));
						
						// 2nd row
						
						
						bodytable.addCell(PdfStyle.createUltimateCellWithBorderWithDisableBorder((""), Element.ALIGN_CENTER, Element.ALIGN_TOP, 8, 12f, false, 1,
								1, false, true, false, true, false));
						
						
						bodytable.addCell(PdfStyle.createUltimateCellWithBorderWithDisableBorder((""), Element.ALIGN_CENTER, Element.ALIGN_TOP, 8, 12f, false, 1,
								1, false, true, false, true, false));
						
						
						
						bodytable.addCell(PdfStyle.createUltimateCellWithBorderWithDisableBorder(("HSN Code: "+challan.getHsn_code()), Element.ALIGN_LEFT, Element.ALIGN_TOP, 8, 12f, false, 1,
								1, false, true, false, true, false));
						
				
						bodytable.addCell(PdfStyle.createUltimateCellWithBorderWithDisableBorder((""), Element.ALIGN_LEFT, Element.ALIGN_LEFT, 8, 12f, false, 1,
								1, false, true, false, true, false));
						
						bodytable.addCell(PdfStyle.createUltimateCellWithBorderWithDisableBorder((""), Element.ALIGN_CENTER, Element.ALIGN_CENTER, 8, 12f, false, 1,
								1, false, true, false, true, false));
						
						bodytable.addCell(PdfStyle.createUltimateCellWithBorderWithDisableBorder((challan.getBatch_expiry_dt()), Element.ALIGN_RIGHT, Element.ALIGN_CENTER, 8, 12f, false, 1,
								1, false, true, false, true, false));
						
					
						
						bodytable.addCell(PdfStyle.createUltimateCellWithBorderWithDisableBorder((""), Element.ALIGN_RIGHT, Element.ALIGN_TOP, 8, 12f, false, 1, 1,
								false, true, false, true, false));
											
						bodytable.addCell(PdfStyle.createUltimateCellWithBorderWithDisableBorder((""), Element.ALIGN_CENTER, Element.ALIGN_TOP, 8,
								12f, false, 1, 1, false, true, false, true, false));
						
						bodytable.addCell(PdfStyle.createUltimateCellWithBorderWithDisableBorder(("").toString(),
										Element.ALIGN_CENTER, Element.ALIGN_TOP, 8, 12f, false, 1, 1, false,
										true, false, true, false));
		
					
						bodytable.addCell(PdfStyle.createUltimateCellWithBorderWithDisableBorder(("").toString(),Element.ALIGN_CENTER, Element.ALIGN_TOP,
								8, 12f, false, 1, 1, false, true, false,
								true, false));
						
						bodytable.addCell(PdfStyle.createUltimateCellWithBorderWithDisableBorder(("").toString(),Element.ALIGN_CENTER, Element.ALIGN_TOP,
								8, 12f, false, 1, 1, false, true, false,
								true, false));
						
						bodytable.addCell(PdfStyle.createUltimateCellWithBorderWithDisableBorder(("").toString(),Element.ALIGN_CENTER, Element.ALIGN_TOP,
								8, 12f, false, 1, 1, false, true, false,
								true, false));

                          count++;rows++;

						goods_total = goods_total.add(challan.getDisp_value());
						final_total = goods_total.add(challan.getHigst_bill_amt().add(challan.getHcgst_bill_amt()
								.add(challan.getHsgst_bill_amt().add(challan.getRoundoff()))));
						header_Igst = challan.getHigst_bill_amt();
						header_Sgst = challan.getHsgst_bill_amt();
						header_Cgst = challan.getHcgst_bill_amt();
						round_off = challan.getRoundoff();
						gst_doc_type = challan.getGst_description();
						
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
						// dsp_challan_msg is the remark that is currently
						// printed.
						dsp_challan_msg = challan.getAlloc_remark().length() > 600
								? challan.getAlloc_remark().substring(0, 600)
								: challan.getAlloc_remark();
						dsp_lorry_no = challan.getDsp_lorry_no();
					}
						
					
					newChallanNo = oldChallan;
				}

				footer_ind = true;
			
				// System.out.println("Outside rows>=limit");
					System.out.println("Inside else header::::::");
					newPage(bodytable, printLabel, true, rows, pageflag, pagecount, total, division, loc, frm_challan,
							to_challan, dispatchType, prodtype, path, list_new_page, show_amount, footer_ind, dtp,
							ship_doc_no, date, cases, weigh, dsp_challan_msg, goods_total, final_total, header_Igst,
							header_Cgst, header_Sgst, round_off, gst_doc_type, dsp_lorry_no, bodyTableWidth, companyCode,
							igstAmounts, MainsTable);		

			}
			document.add(MainsTable);
			document.newPage();

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

	public PdfPTable newPage(PdfPTable bodytable, HashMap<String, String> printLabel, boolean isFooterData, int rows,
			boolean pageflag, int pagecount, BigDecimal total, int division, Integer loc, String frm_challan,
			String to_challan, String dispatchType, String prodtype, String path,
			List<PrePrintedDetailChallan_withgst_pal> challan, String show_amount, boolean footer_ind,
			String dsp_transporter, String ship_doc_no, String date, String cases, String weigh, String dsp_challan_msg,
			BigDecimal goods_total, BigDecimal final_total, BigDecimal header_Igst, BigDecimal header_Cgst,
			BigDecimal header_Sgst, BigDecimal round_off, String gst_doc_type, String dsp_lorry_no,
			float[] bodyTableWidth, String companyCode, BigDecimal igstAmounts, PdfPTable MainsTable)
			throws NumberFormatException, Exception {
		bodytable.getDefaultCell().setPadding(0.0f);
		PdfPCell cell = null;
		try {
			System.out.println(limit+" limit$$$"+ rows);
			if (limit - (rows) > 0) {
				System.out.println("inside limite"+limit);
				// add extra empty rows in body table
								cell = new PdfPCell();
									PdfPTable temp = new PdfPTable(12);
									temp.setWidths(bodyTableWidth);
									temp.addCell(PdfStyle.createUltimateCellWithBorderWithDisableBorder(("").toString(),Element.ALIGN_CENTER, Element.ALIGN_TOP,
											8, 18f, false, 1, 1, false, true, false,
											true, false));
									temp.addCell(PdfStyle.createUltimateCellWithBorderWithDisableBorder(("").toString(),Element.ALIGN_CENTER, Element.ALIGN_TOP,
											8, 18f, false, 1, 1, false, true, false,
											true, false));
									temp.addCell(PdfStyle.createUltimateCellWithBorderWithDisableBorder(("").toString(),Element.ALIGN_CENTER, Element.ALIGN_TOP,
											8, 18f, false, 1, 1, false, true, false,
											true, false));
									temp.addCell(PdfStyle.createUltimateCellWithBorderWithDisableBorder(("").toString(),Element.ALIGN_CENTER, Element.ALIGN_TOP,
											8, 18f, false, 1, 1, false, true, false,
											true, false));
									temp.addCell(PdfStyle.createUltimateCellWithBorderWithDisableBorder(("").toString(),Element.ALIGN_CENTER, Element.ALIGN_TOP,
											8, 18f, false, 1, 1, false, true, false,
											true, false));
									temp.addCell(PdfStyle.createUltimateCellWithBorderWithDisableBorder(("").toString(),Element.ALIGN_CENTER, Element.ALIGN_TOP,
											8, 18f, false, 1, 1, false, true, false,
											true, false));
									temp.addCell(PdfStyle.createUltimateCellWithBorderWithDisableBorder(("").toString(),Element.ALIGN_CENTER, Element.ALIGN_TOP,
											8, 18f, false, 1, 1, false, true, false,
											true, false));
									temp.addCell(PdfStyle.createUltimateCellWithBorderWithDisableBorder(("").toString(),Element.ALIGN_CENTER, Element.ALIGN_TOP,
											8, 18f, false, 1, 1, false, true, false,
											true, false));
									temp.addCell(PdfStyle.createUltimateCellWithBorderWithDisableBorder(("").toString(),Element.ALIGN_CENTER, Element.ALIGN_TOP,
											8, 18f, false, 1, 1, false, true, false,
											true, false));
									temp.addCell(PdfStyle.createUltimateCellWithBorderWithDisableBorder(("").toString(),Element.ALIGN_CENTER, Element.ALIGN_TOP,
											8, 18f, false, 1, 1, false, true, false,
											true, false));
									temp.addCell(PdfStyle.createUltimateCellWithBorderWithDisableBorder(("").toString(),Element.ALIGN_CENTER, Element.ALIGN_TOP,
											8, 18f, false, 1, 1, false, true, false,
											true, false));
									temp.addCell(PdfStyle.createUltimateCellWithBorderWithDisableBorder(("").toString(),Element.ALIGN_CENTER, Element.ALIGN_TOP,
											8, 18f, false, 1, 1, false, true, false,
											true, false));
									temp.getDefaultCell().setPadding(0.0f);

//									temp.addCell(PdfStyle.createValueCellWithHeight((" "), false, true, 18f));
//									temp.addCell(PdfStyle.createValueCellWithHeight((""), false, true, 18f));
//									temp.addCell(PdfStyle.createValueCellWithHeight((""), false, true, 18f));
//									temp.addCell(PdfStyle.createValueCellWithHeight((""), true, true, 18f));
//									temp.addCell(PdfStyle.createValueCellWithHeight((""), true, true, 18f));
//									temp.addCell(PdfStyle.createValueCellWithHeight((""), true, true, 18f));
//									temp.addCell(PdfStyle.createValueCellWithHeight((""), true, true, 18f));
//									temp.addCell(PdfStyle.createValueCellWithHeight((""), true, true, 18f));
//									temp.addCell(PdfStyle.createValueCellWithHeight((""), true, true, 18f));
//									temp.addCell(PdfStyle.createValueCellWithHeight((""), true, true, 18f));
//									temp.addCell(PdfStyle.createValueCellWithHeight((""), true, true, 18f));
//									temp.addCell(PdfStyle.createValueCellWithHeight((" "), false, true, 18f));

									cell = new PdfPCell(temp);
									cell.setBorder(0);
									cell.setColspan(12);
							

				// System.out.println("limit-row:::::"+(limit-rows));
								for (int i = 0; i < (limit - rows); i++) {
									// System.out.println("printing row:::"+i);
									bodytable.addCell(cell);
								}
							}
			
		

			cell = new PdfPCell(PdfStyle.createSingleLine());
			cell.setColspan(6);
			bodytable.addCell(cell);
			MainsTable.addCell(bodytable);
//			column.addElement(bodytable);
//			column.setSimpleColumn(20, 0, 580, 512);
//			column.go();
			bodytable.flushContent();
			bodytable = new PdfPTable(12);

			bodytable.setWidthPercentage(100);
			bodytable.setWidths(bodyTableWidth);
		
			
			// event.setFooter(
			cell = new PdfPCell(
			
					createFooter(printLabel, isFooterData, pageflag, pagecount, total, division, loc, frm_challan,
							to_challan, dispatchType, prodtype, path, challan, show_amount, footer_ind, dsp_transporter,
							ship_doc_no, date, cases, weigh, dsp_challan_msg, goods_total, final_total, header_Igst,
							header_Cgst, header_Sgst, round_off, gst_doc_type, dsp_lorry_no, companyCode, igstAmounts));
		
			cell.setBorder(0);
			cell.setColspan(6);
			//bodytable.addCell(cell);
			MainsTable.addCell(cell);

			
			
			// );

		} catch (DocumentException e) {
			throw e;
		}
		return bodytable;
	}

	private PdfPTable createFooter(HashMap<String, String> printLabel, boolean isFooterData, boolean pageflag,
			int pagecount, BigDecimal total, int division, Integer loc, String frm_challan, String to_challan,
			String dispatchType, String prodtype, String path, List<PrePrintedDetailChallan_withgst_pal> challan,
			String show_amount, boolean footer_ind, String dsp_transporter, String ship_doc_no, String date,
			String cases, String weigh, String dsp_challan_msg, BigDecimal goods_total, BigDecimal final_total,
			BigDecimal header_Igst, BigDecimal header_Cgst, BigDecimal header_Sgst, BigDecimal round_off,
			String gst_doc_type, String dsp_lorry_no, String companyCode, BigDecimal igstAmounts)
			throws NumberFormatException, Exception {

		System.out.println("createFootercaled$$$$$$$$$$$$$$$$$");
		PdfPTable maintable = new PdfPTable(1);
		maintable.setWidthPercentage(100);
		String narrration = printService.getLocationNarrationByLocId(Long.parseLong(loc.toString()));

		try {
		

			PdfPTable footerTable1 = new PdfPTable(6);
			footerTable1.setWidthPercentage(100);
			footerTable1.setWidths(new float[] { 1.8f, 3.8f, 0.95f, 4.090f, 3.7f, 1.1f });

			PdfPTable footerTable2 = new PdfPTable(2);
			footerTable2.setWidthPercentage(100);
			footerTable2.setWidths(new float[] { 4.15f, 1.85f });

			PdfPCell fcell1 = new PdfPCell(PdfStyle.createUltimateCellWithBorderWithDisableBorder((""),
					Element.ALIGN_LEFT, Element.ALIGN_LEFT, 8, 10f, false, 0, 1, false, false, true, true, true));
			footerTable1.addCell(fcell1);

			PdfPCell fcell2 = new PdfPCell(PdfStyle.createUltimateCellWithBorderWithDisableBorder(("Tax Summary"),
					Element.ALIGN_CENTER, Element.ALIGN_CENTER, 8, 10f, false, 0, 1, false, false, true, false, true));
			footerTable1.addCell(fcell2);

			PdfPCell fcell3 = new PdfPCell(PdfStyle.createUltimateCellWithBorderWithDisableBorder((""),
					Element.ALIGN_LEFT, Element.ALIGN_LEFT, 8, 10f, false, 0, 1, false, false, true, true, false));
			footerTable1.addCell(fcell3);

			PdfPCell fcell4 = new PdfPCell(PdfStyle.createUltimateCellWithBorderWithDisableBorder((""),
					Element.ALIGN_LEFT, Element.ALIGN_LEFT, 8, 10f, false, 0, 1, false, false, true, true, true));
			footerTable1.addCell(fcell4);

			PdfPCell fcell5 = new PdfPCell(PdfStyle.createUltimateCellWithBorderWithDisableBorder(
					("Taxable Value of Supply"), Element.ALIGN_LEFT, Element.ALIGN_LEFT, 8, 10f, true, 0, 1, false,
					false, true, false, true));
			footerTable1.addCell(fcell5);

			PdfPCell fcell6 = new PdfPCell(PdfStyle.createUltimateCellWithBorderWithDisableBorder(("0.00"),
					Element.ALIGN_RIGHT, Element.ALIGN_RIGHT, 8, 10f, false, 0, 1, false, false, true, false, true));
			footerTable1.addCell(fcell6);

			PdfPCell fcell7 = new PdfPCell(PdfStyle.createUltimateCellWithBorderWithDisableBorder((""),
					Element.ALIGN_LEFT, Element.ALIGN_LEFT, 8, 10f, false, 0, 1, false, false, true, true, true));
			footerTable1.addCell(fcell7);

			PdfPCell fcell8 = new PdfPCell(PdfStyle.createUltimateCellWithBorderWithDisableBorder(("IGST"),
					Element.ALIGN_CENTER, Element.ALIGN_CENTER, 8, 10f, true, 0, 1, true, true, true, false, false));// with
																														// all
																														// border
			footerTable1.addCell(fcell8);

			PdfPCell fcell9 = new PdfPCell(PdfStyle.createUltimateCellWithBorderWithDisableBorder(("0.00"),
					Element.ALIGN_RIGHT, Element.ALIGN_RIGHT, 8, 10f, false, 0, 1, true, true, true, false, false));
			footerTable1.addCell(fcell9);

			PdfPCell fcell10 = new PdfPCell(PdfStyle.createUltimateCellWithBorderWithDisableBorder((""),
					Element.ALIGN_LEFT, Element.ALIGN_LEFT, 8, 10f, false, 0, 1, false, false, true, true, true));
			footerTable1.addCell(fcell10);

			PdfPCell fcell11 = new PdfPCell(PdfStyle.createUltimateCellWithBorderWithDisableBorder(("Add GST"),
					Element.ALIGN_LEFT, Element.ALIGN_LEFT, 8, 10f, true, 0, 1, false, false, true, false, true));
			footerTable1.addCell(fcell11);

			PdfPCell fcell12 = new PdfPCell(PdfStyle.createUltimateCellWithBorderWithDisableBorder(("0.00"),
					Element.ALIGN_RIGHT, Element.ALIGN_RIGHT, 8, 10f, false, 0, 1, true, true, true, false, true));
			footerTable1.addCell(fcell12);

			// 3d
			PdfPCell fcell13 = new PdfPCell(PdfStyle.createUltimateCellWithBorderWithDisableBorder(("E & OE"),
					Element.ALIGN_LEFT, Element.ALIGN_LEFT, 8, 10f, false, 0, 1, false, false, true, true, true));
			footerTable1.addCell(fcell13);

			PdfPCell fcell14 = new PdfPCell(PdfStyle.createUltimateCellWithBorderWithDisableBorder(("CGST"),
					Element.ALIGN_CENTER, Element.ALIGN_CENTER, 8, 10f, true, 0, 1, true, true, true, false, false));// with
																														// all
																														// border
			footerTable1.addCell(fcell14);

			PdfPCell fcell15 = new PdfPCell(PdfStyle.createUltimateCellWithBorderWithDisableBorder(("0.00"),
					Element.ALIGN_RIGHT, Element.ALIGN_RIGHT, 8, 10f, false, 0, 1, true, true, true, false, false));
			footerTable1.addCell(fcell15);

			PdfPCell fcell16 = new PdfPCell(PdfStyle.createUltimateCellWithBorderWithDisableBorder((""),
					Element.ALIGN_LEFT, Element.ALIGN_LEFT, 8, 10f, false, 0, 1, false, false, true, true, true));
			footerTable1.addCell(fcell16);

			PdfPCell fcell17 = new PdfPCell(
					PdfStyle.createUltimateCellWithBorderWithDisableBorder(("Add/Less Round Off"), Element.ALIGN_LEFT,
							Element.ALIGN_LEFT, 8, 10f, true, 0, 1, false, false, true, false, true));
			footerTable1.addCell(fcell17);

			PdfPCell fcell18 = new PdfPCell(PdfStyle.createUltimateCellWithBorderWithDisableBorder(("0.00"),
					Element.ALIGN_RIGHT, Element.ALIGN_RIGHT, 8, 10f, false, 0, 1, true, true, true, false, true));
			footerTable1.addCell(fcell18);

			// 4d

			PdfPCell fcell19 = new PdfPCell(PdfStyle.createUltimateCellWithBorderWithDisableBorder((""),
					Element.ALIGN_LEFT, Element.ALIGN_LEFT, 8, 10f, false, 0, 1, false, false, true, true, true));
			footerTable1.addCell(fcell19);

			PdfPCell fcell20 = new PdfPCell(PdfStyle.createUltimateCellWithBorderWithDisableBorder(("SGST"),
					Element.ALIGN_CENTER, Element.ALIGN_CENTER, 8, 10f, true, 0, 1, true, false, true, false, false));// with
																														// all
																														// border
			footerTable1.addCell(fcell20);

			PdfPCell fcell21 = new PdfPCell(PdfStyle.createUltimateCellWithBorderWithDisableBorder(("0.00"),
					Element.ALIGN_RIGHT, Element.ALIGN_RIGHT, 8, 10f, false, 0, 1, true, false, true, false, false));
			footerTable1.addCell(fcell21);

			PdfPCell fcell22 = new PdfPCell(PdfStyle.createUltimateCellWithBorderWithDisableBorder((""),
					Element.ALIGN_LEFT, Element.ALIGN_LEFT, 8, 10f, false, 0, 1, false, false, true, true, true));
			footerTable1.addCell(fcell22);

			PdfPCell fcell23 = new PdfPCell(PdfStyle.createUltimateCellWithBorderWithDisableBorder(
					("Total Value of Supply"), Element.ALIGN_LEFT, Element.ALIGN_LEFT, 8, 10f, true, 0, 1, false, false,
					true, false, true));
			footerTable1.addCell(fcell23);

			PdfPCell fcell24 = new PdfPCell(PdfStyle.createUltimateCellWithBorderWithDisableBorder(("0.00"),
					Element.ALIGN_RIGHT, Element.ALIGN_RIGHT, 8, 10f, false, 0, 1, true, false, true, false, true));
			footerTable1.addCell(fcell24);

			// 2md footer table

			PdfPCell fcell25 = new PdfPCell(PdfStyle.createUltimateCellWithBorderWithDisableBorder(
					("Value for insurance purposes only: "
							+ goods_total.setScale(2, BigDecimal.ROUND_HALF_UP).toString()),
					Element.ALIGN_LEFT, Element.ALIGN_LEFT, 8, 10f, true, 0, 1, false, false, true, true, true));
			footerTable2.addCell(fcell25);

			PdfPCell fcell26 = new PdfPCell(PdfStyle.createUltimateCellWithBorderWithDisableBorder(
					("For Palsons Derma Private Limited"), Element.ALIGN_RIGHT, Element.ALIGN_RIGHT, 10, 10f, true, 0,
					1, false, false, true, false, true));
			footerTable2.addCell(fcell26);

			PdfPCell fcell27 = new PdfPCell(PdfStyle.createUltimateCellWithBorderWithDisableBorder((narrration.substring(1, 53)+"\r\n" +narrration.substring(55, 137)+"\r\n"+
					narrration.substring(138, 198)),
					Element.ALIGN_LEFT, Element.ALIGN_LEFT, 8, 10f, false, 0, 1, false, false, true, true, true));
			footerTable2.addCell(fcell27);

			PdfPCell fcell30 = new PdfPCell(PdfStyle.createUltimateCellWithBorderWithDisableBorder((""),
					Element.ALIGN_LEFT, Element.ALIGN_LEFT, 8, 10f, false, 0, 1, false, false, true, false, true));
			footerTable2.addCell(fcell30);

			PdfPCell fcell29 = new PdfPCell(PdfStyle.createUltimateCellWithBorderWithDisableBorder(
					("Copies: Original / Duplicate / Triplicate"), Element.ALIGN_BOTTOM, Element.ALIGN_BOTTOM, 8, 10f,
					true, 0, 1, true, false, true, true, true));
			footerTable2.addCell(fcell29);

			PdfPCell fcell28 = new PdfPCell(PdfStyle.createUltimateCellWithBorderWithDisableBorder(
					("Authorised Signatory"), Element.ALIGN_CENTER, Element.ALIGN_BOTTOM, 8, 12f, true, 0, 1, false,
					false, true, false, true));
			footerTable2.addCell(fcell28);
			footerTable1.getDefaultCell().setPadding(0.0f);
			footerTable2.getDefaultCell().setPadding(0.0f);
			maintable.addCell(footerTable1);
			maintable.addCell(footerTable2);

		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("Error Occurred :" +e.getMessage());// uncomment asneeded --;
		}
		return maintable;
	}

	private PdfPTable createHeader(PrePrintedDetailChallan_withgst_pal challan, int pagecount, int division, Integer loc,
			String frm_challan, String to_challan, String dispatchType, String prodtype, String path,
			String show_amount, String compcd, String companyName, HttpSession session, float[] bodyTableWidth,
			String companyCode) throws Exception {
		System.out.println("Challan@@ "+challan.toString());
		PdfPTable bodytable = null;
		System.out.println("create Header *********");
		PdfPTable headertable = new PdfPTable(1);
		PdfPTable headertable001 = new PdfPTable(1);
		PdfPTable headertable01 = new PdfPTable(2);// Billing Address
		PdfPTable headertable02 = new PdfPTable(3);
		
		PdfPTable headertable03 = new PdfPTable(2);
		PdfPTable headerPdf = new PdfPTable(6);
		PdfPTable headerPdf1 = new PdfPTable(8);
		PdfPTable headerPdf2 = new PdfPTable(8);
		PdfPTable headerPdf3 = new PdfPTable(6);
		PdfPTable headerPdf4 = new PdfPTable(6);
		PdfPTable headerPdf5 = new PdfPTable(6);
		// tables for consignor
		PdfPTable headerPdf6 = new PdfPTable(2);
		PdfPTable headerPdf7 = new PdfPTable(2);
		PdfPTable headerPdf8 = new PdfPTable(2);
		PdfPTable headerPdf9 = new PdfPTable(2);
		PdfPTable headerPdf10 = new PdfPTable(2);
		PdfPTable headerPdf11 = new PdfPTable(2);
		PdfPTable headerPdf12 = new PdfPTable(2);

		PdfPTable headerPdf13 = new PdfPTable(2);
		PdfPTable headerPdf14 = new PdfPTable(2);
		PdfPTable headerPdf15 = new PdfPTable(2);
		PdfPTable headerPdf16 = new PdfPTable(2);
		PdfPTable headerPdf17 = new PdfPTable(2);
		PdfPTable headerPdf18 = new PdfPTable(2);
		PdfPTable headerPdf19 = new PdfPTable(2);
		PdfPTable headerPdf20 = new PdfPTable(2);

		PdfPTable headertable2 = new PdfPTable(12);
		PdfPTable headertable3 = new PdfPTable(12);
		PdfPTable headertable4 = new PdfPTable(12);
		PdfPTable headertable5 = new PdfPTable(3);

		PdfPTable addressTbl = new PdfPTable(1);

		String FOX = "path/to/resource/fox.png";
		headertable.setWidthPercentage(100);
		headertable01.setWidthPercentage(100);
		headertable02.setWidthPercentage(100);
		headertable03.setWidthPercentage(100);
		headertable2.setWidthPercentage(100);
		headertable3.setWidthPercentage(100);
		headertable4.setWidthPercentage(100);
		headerPdf.setWidthPercentage(100);
		headerPdf1.setWidthPercentage(100);
		headerPdf2.setWidthPercentage(100);
		headerPdf3.setWidthPercentage(100);
		headerPdf4.setWidthPercentage(100);
		headerPdf5.setWidthPercentage(100);
		headerPdf6.setWidthPercentage(100);
		headerPdf7.setWidthPercentage(100);
		headerPdf8.setWidthPercentage(100);
		headerPdf9.setWidthPercentage(100);
		headerPdf10.setWidthPercentage(100);
		headerPdf11.setWidthPercentage(100);
		headerPdf12.setWidthPercentage(100);
		headerPdf13.setWidthPercentage(100);
		headerPdf14.setWidthPercentage(100);
		headerPdf15.setWidthPercentage(100);
		headerPdf16.setWidthPercentage(100);
		headerPdf17.setWidthPercentage(100);
		headerPdf18.setWidthPercentage(100);
		headerPdf19.setWidthPercentage(100);
		headerPdf20.setWidthPercentage(100);
		headertable001.setWidthPercentage(100);

		headertable.setWidths(new float[] { 12f });
		headertable001.setWidths(new float[] { 12f });
		headerPdf.setWidths(new float[] { 2.2f, 5.2f, 0.60f, 3.25f, 1f, 2.2f });
		headerPdf1.setWidths(new float[] { 2f, 1.5f, 1f, 1.8f, 1.2f, 2f, 1f, 2.4f });
		headerPdf2.setWidths(new float[] { 1.5f, 5f, 1.5f, 2.5f, 1.5f, 2f, 1.6f, 2.8f });
		headerPdf4.setWidths(new float[] { 1f, 1f, 1f, 1f, 1f, 1f });
		headerPdf5.setWidths(new float[] { 1f, 1f, 1f, 1f, 1f, 1f });

		headerPdf3.setWidths(new float[] { 0.8f, 5.2f, 0.8f, 1.2f, 1.1f, 3.5f });
		headerPdf6.setWidths(new float[] { 0.8f, 1.2f });
		headerPdf7.setWidths(new float[] { 0.6f, 1.4f });
		headerPdf8.setWidths(new float[] { 1f, 1f });
		headerPdf9.setWidths(new float[] { 1f, 1f });
		headerPdf10.setWidths(new float[] { 1.1f, 0.9f });
		headerPdf11.setWidths(new float[] { 1f, 1f });
		headerPdf12.setWidths(new float[] { 1f, 1f });

		headerPdf13.setWidths(new float[] { 1f, 1f });
		headerPdf14.setWidths(new float[] { 1f, 1f });
		headerPdf15.setWidths(new float[] { 1.2f, 0.8f });
		headerPdf16.setWidths(new float[] { 1f, 1f });
		headerPdf17.setWidths(new float[] { 1.2f, 0.8f });
		headerPdf18.setWidths(new float[] { 1f, 1f });
		headerPdf19.setWidths(new float[] { 1f, 1f });
		headerPdf20.setWidths(new float[] { 1f, 1f });

		headertable01.setWidths(new float[] { 9f, 3f });
		headertable02.setWidths(new float[] { 6.37f, 3.22f, 3.2f });
		headertable03.setWidths(new float[] { 5f, 5f });
		headertable2.setWidths(new float[] { 3f, 3.5f, 1f, 4f, 1.5f, 3f, 1f, 2.5f, 3f, 2f, 2f, 2f });
		headertable3.setWidths(new float[] { 3f, 2f, 2f, 2.5f, 2f, 4f, 1.5f, 4f, 1f, 1f, 1f, 1f });
		headertable4.setWidths(new float[] { 2f, 5f, 2f, 2.5f, 3f, 3f, 2f, 2f, 2f, 2f, 2f, 2f });
		headertable5.setWidths(new float[] { 10f, 2f, 2f });
		
		headertable2.getDefaultCell().setPadding(0.0f);
		headerPdf.getDefaultCell().setPadding(0.f);
		
		Font font = new Font(FontFamily.HELVETICA, 13, Font.BOLD, BaseColor.BLACK);
		Font font2 = new Font(FontFamily.HELVETICA, 9, Font.BOLD, BaseColor.BLACK);
		Font font3 = new Font(FontFamily.HELVETICA, 7.7f, Font.BOLD, BaseColor.BLACK);
		PdfPTable headertable1 = new PdfPTable(1);
		headertable1.setWidthPercentage(100);
		headertable1.setWidths(new float[] { 5f });

		PdfPCell hcell = new PdfPCell(PdfStyle.createUltimateCellWithBorderWithDisableBorder(
				("Palsons Derma Private Limited"), Element.ALIGN_CENTER, Element.ALIGN_CENTER, 14, 18f, true, 0, 1,
				false, false, true, true, true));
		headertable01.addCell(hcell);

//		PdfPCell hcel2 = new PdfPCell(PdfStyle.createUltimateCellWithBorderWithDisableBorder(("2"), Element.ALIGN_LEFT,
//				Element.ALIGN_LEFT, 6, 18f, false, 0, 1, false, false, true, false, true));
//		headertable01.addCell(hcel2);

	//	PdfPCell hcel2 = new PdfPCell(addressTbl);
		//hcel2.setRowspan(4);
		  

	
		String logoImag;
		logoImag = path + "\\Logo_Palson.png";
		
		System.out.println("logoImag : " + logoImag);
		Image img = null;
		try {
			img = Image.getInstance(logoImag);
			img.setAlignment(Image.ALIGN_CENTER);
	} catch (Exception e) {
			System.out.println("Error Occurred :" +e.getMessage());// uncomment asneeded --;
	}
		img.setAlignment(1);
		PdfPCell imagecell = new PdfPCell(img, true);
		imagecell.setFixedHeight(5f);
imagecell.setNoWrap(false);
		//imagecell.setBorder(0);
		
		imagecell.setBorderWidthLeft(1);
		imagecell.setBorderWidthRight(0);
		imagecell.setBorderWidthTop(0.2f);
		imagecell.setBorderWidthBottom(0);
		imagecell.setHorizontalAlignment(Element.ALIGN_CENTER);
		imagecell.setVerticalAlignment(Element.ALIGN_CENTER);
		//imagecell.setBorder(Rectangle.NO_BORDER | Rectangle.RIGHT);
		imagecell.setRowspan(5);
		headertable01.addCell(imagecell);
	//	headertable01.addCell(hcel2);
// PdfPTable t1= new PdfPTable(1);
//	
//  t1.addCell(imagecell);


	
		
		

		/// 2nd row
		PdfPCell hcel3 = new PdfPCell();
		PdfPCell header_Cell1 = new PdfPCell(
				PdfStyle.createUltimateCellWithBorderWithDisableBorder(("Registered Office:"), Element.ALIGN_LEFT,
						Element.ALIGN_LEFT, 6, 10f, true, 0, 1, false, false, true, true, true));
		headerPdf.addCell(header_Cell1);

		PdfPCell header_Cell2 = new PdfPCell(
				PdfStyle.createUltimateCellWithBorderWithDisableBorder((challan.getLocadd2().substring(0, 40)),
						Element.ALIGN_LEFT, Element.ALIGN_LEFT, 6, 10f, false, 0, 1, false, false, true, true, true));
		headerPdf.addCell(header_Cell2);

		PdfPCell header_Cell3 = new PdfPCell(PdfStyle.createUltimateCellWithBorderWithDisableBorder(("CIN"),
				Element.ALIGN_LEFT, Element.ALIGN_LEFT, 6, 10f, true, 0, 1, false, false, true, true, true));
		headerPdf.addCell(header_Cell3);

		PdfPCell header_Cell4 = new PdfPCell(
				PdfStyle.createUltimateCellWithBorderWithDisableBorder((challan.getLoc_cinno()), Element.ALIGN_LEFT,
						Element.ALIGN_LEFT, 6, 10f, false, 0, 1, false, false, true, true, true));
		headerPdf.addCell(header_Cell4);

		PdfPCell header_Cell13 = new PdfPCell(PdfStyle.createUltimateCellWithBorderWithDisableBorder(("GST No."),
				Element.ALIGN_LEFT, Element.ALIGN_LEFT, 6, 10f, true, 0, 1, false, false, true, true, true));
		headerPdf.addCell(header_Cell13);

		PdfPCell header_Cell5 = new PdfPCell(
				PdfStyle.createUltimateCellWithBorderWithDisableBorder((challan.getGst_reg_no()), Element.ALIGN_LEFT,
						Element.ALIGN_LEFT, 6, 10f, false, 0, 1, false, false, true, true, true));
		headerPdf.addCell(header_Cell5);

		hcel3.addElement(headerPdf);
		hcel3.setBorder(0);
	
	
		headertable01.addCell(hcel3);
		

///second 

//		PdfPCell hcel4 = new PdfPCell(PdfStyle.createUltimateCellWithBorderWithDisableBorder(("4"),
//				Element.ALIGN_BOTTOM, Element.ALIGN_BOTTOM, 8, 10f, false, 0, 1, false, false, true, false, true));
		
		/// third

		PdfPCell hcel5 = new PdfPCell();
		PdfPCell header_Cell11 = new PdfPCell(PdfStyle.createUltimateCellWithBorderWithDisableBorder(("Phone Number:"),
				Element.ALIGN_LEFT, Element.ALIGN_LEFT, 7, 10f, true, 0, 1, false, false, true, true, true));
		headerPdf1.addCell(header_Cell11);

		PdfPCell header_Cell21 = new PdfPCell(
				PdfStyle.createUltimateCellWithBorderWithDisableBorder((challan.getLoc_telephone_no()),
						Element.ALIGN_LEFT, Element.ALIGN_LEFT, 7, 10f, false, 0, 1, false, false, true, true, true));
		headerPdf1.addCell(header_Cell21);

		PdfPCell header_Cell24 = new PdfPCell(PdfStyle.createUltimateCellWithBorderWithDisableBorder(("PAN:"),
				Element.ALIGN_LEFT, Element.ALIGN_LEFT, 7, 10f, true, 0, 1, false, false, true, true, true));
		headerPdf1.addCell(header_Cell24);

		PdfPCell header_Cell25 = new PdfPCell(
				PdfStyle.createUltimateCellWithBorderWithDisableBorder(("AAECP5629D"), Element.ALIGN_LEFT,
						Element.ALIGN_LEFT, 7, 10f, false, 0, 1, false, false, true, true, true));
		headerPdf1.addCell(header_Cell25);

		PdfPCell header_Cell22 = new PdfPCell(PdfStyle.createUltimateCellWithBorderWithDisableBorder(("Fax No."),
				Element.ALIGN_LEFT, Element.ALIGN_LEFT, 6, 10f, true, 0, 1, false, false, true, true, true));
		headerPdf1.addCell(header_Cell22);

		PdfPCell header_Cell23 = new PdfPCell(PdfStyle.createUltimateCellWithBorderWithDisableBorder(("2452-7446"),
				Element.ALIGN_LEFT, Element.ALIGN_LEFT, 7, 10f, false, 0, 1, false, false, true, true, true));
		headerPdf1.addCell(header_Cell23);

		PdfPCell header_Cell16 = new PdfPCell(PdfStyle.createUltimateCellWithBorderWithDisableBorder(("Email:"),
				Element.ALIGN_LEFT, Element.ALIGN_LEFT, 7, 10f, true, 0, 1, false, false, true, true, true));
		headerPdf1.addCell(header_Cell16);

		PdfPCell header_Cell7 = new PdfPCell(
				PdfStyle.createUltimateCellWithBorderWithDisableBorder(("info@palsonsderma.com"), Element.ALIGN_LEFT,
						Element.ALIGN_LEFT, 6, 10f, false, 0, 1, false, false, true, true, true));
		headerPdf1.addCell(header_Cell7);

		hcel5.addElement(headerPdf1);
		hcel5.setBorder(0);
	//	headertable01.addCell(hcel4);
		headertable01.addCell(hcel5);
		
		
//		PdfPCell hcel6 = new PdfPCell(PdfStyle.createUltimateCellWithBorderWithDisableBorder(("6"),
//				Element.ALIGN_BOTTOM, Element.ALIGN_BOTTOM, 8, 10f, false, 0, 1, false, false, true, false, true));
//		headertable01.addCell(hcel6);


//		PdfPCell hcel_5 = new PdfPCell(PdfStyle.createUltimateCellWithBorderWithDisableBorder(("5"),
//				Element.ALIGN_BOTTOM, Element.ALIGN_BOTTOM, 8, 18f, false, 0, 1, false, true, true, true, true));
//		headertable01.addCell(hcel_5);

		PdfPCell hcel_5 = new PdfPCell();
		PdfPCell header_Cell31 = new PdfPCell(PdfStyle.createUltimateCellWithBorderWithDisableBorder(("Factory:"),
				Element.ALIGN_LEFT, Element.ALIGN_LEFT, 7, 10f, true, 0, 1, false, false, true, true, true));
		headerPdf2.addCell(header_Cell31);

		PdfPCell header_Cell32 = new PdfPCell(
				PdfStyle.createUltimateCellWithBorderWithDisableBorder(("E 7-92/New Biren Roy Road(West)P.O. "),
						Element.ALIGN_LEFT, Element.ALIGN_LEFT, 6, 10f, false, 0, 1, false, false, true, true, true));
		headerPdf2.addCell(header_Cell32);

		PdfPCell header_Cell32_ = new PdfPCell(PdfStyle.createUltimateCellWithBorderWithDisableBorder(("Phone:"),
				Element.ALIGN_LEFT, Element.ALIGN_LEFT, 6, 10f, true, 0, 1, false, false, true, true, true));
		headerPdf2.addCell(header_Cell32_);

		PdfPCell header_Cell33 = new PdfPCell(
				PdfStyle.createUltimateCellWithBorderWithDisableBorder((challan.getDspfstaff_mobile()),
						Element.ALIGN_LEFT, Element.ALIGN_LEFT, 7, 10f, false, 0, 1, false, false, true, true, true));
		headerPdf2.addCell(header_Cell33);

		PdfPCell header_Cell34 = new PdfPCell(PdfStyle.createUltimateCellWithBorderWithDisableBorder(("Fax No."),
				Element.ALIGN_LEFT, Element.ALIGN_LEFT, 7, 10f, true, 0, 1, false, false, true, true, true));
		headerPdf2.addCell(header_Cell34);

		PdfPCell header_Cell35 = new PdfPCell(PdfStyle.createUltimateCellWithBorderWithDisableBorder(("2452-7446"),
				Element.ALIGN_LEFT, Element.ALIGN_LEFT, 7, 10f, false, 0, 1, false, false, true, true, true));
		headerPdf2.addCell(header_Cell35);

		PdfPCell header_Cell36 = new PdfPCell(PdfStyle.createUltimateCellWithBorderWithDisableBorder(("Cos Lic:"),
				Element.ALIGN_LEFT, Element.ALIGN_LEFT, 6, 10f, true, 0, 1, false, false, true, true, true));
		headerPdf2.addCell(header_Cell36);

		PdfPCell header_Cell37 = new PdfPCell(
				PdfStyle.createUltimateCellWithBorderWithDisableBorder(("CL-1049-M "), Element.ALIGN_LEFT,
						Element.ALIGN_LEFT, 7, 10f, false, 0, 1, false, false, true, true, true));
		headerPdf2.addCell(header_Cell37);

		hcel_5.addElement(headerPdf2);
		hcel_5.setBorder(0);
		headertable01.addCell(hcel_5);
	
//		PdfPCell hcel_6 = new PdfPCell(PdfStyle.createUltimateCellWithBorderWithDisableBorder(("6"),
//				Element.ALIGN_BOTTOM, Element.ALIGN_BOTTOM, 8, 10f, false, 0, 1, false, false, true, false, true));
//		headertable01.addCell(hcel_6);
//cell7

		PdfPCell hcel7 = new PdfPCell();
		PdfPCell header_Cell41 = new PdfPCell(PdfStyle.createUltimateCellWithBorderWithDisableBorder(("Depot:"),
				Element.ALIGN_LEFT, Element.ALIGN_LEFT, 6, 10f, true, 0, 1, false, true, true, true, true));
		headerPdf3.addCell(header_Cell41);

		PdfPCell header_Cell42 = new PdfPCell(
				PdfStyle.createUltimateCellWithBorderWithDisableBorder((challan.getLocadd2()), Element.ALIGN_LEFT,
						Element.ALIGN_LEFT, 6, 10f, false, 0, 1, false, true, true, true, true));
		headerPdf3.addCell(header_Cell42);

		PdfPCell header_Cell43 = new PdfPCell(PdfStyle.createUltimateCellWithBorderWithDisableBorder(("Phone:"),
				Element.ALIGN_LEFT, Element.ALIGN_LEFT, 6, 10f, true, 0, 1, false, true, true, true, true));
		headerPdf3.addCell(header_Cell43);

		PdfPCell header_Cell44 = new PdfPCell(
				PdfStyle.createUltimateCellWithBorderWithDisableBorder((challan.getDspfstaff_mobile()),
						Element.ALIGN_LEFT, Element.ALIGN_LEFT, 6, 10f, false, 0, 1, false, true, true, true, true));
		headerPdf3.addCell(header_Cell44);

		PdfPCell header_Cell45 = new PdfPCell(PdfStyle.createUltimateCellWithBorderWithDisableBorder(("Drug Lic:"),
				Element.ALIGN_LEFT, Element.ALIGN_LEFT, 6, 10f, true, 0, 1, false, true, true, true, true));
		headerPdf3.addCell(header_Cell45);

		PdfPCell header_Cell46 = new PdfPCell(
				PdfStyle.createUltimateCellWithBorderWithDisableBorder((challan.getLic1()), Element.ALIGN_LEFT,
						Element.ALIGN_LEFT, 6, 10f, false, 0, 1, false, true, true, true, true));
		headerPdf3.addCell(header_Cell46);

		hcel7.addElement(headerPdf3);
		hcel7.setBorder(0);
		headertable01.addCell(hcel7);
		
//		
//		PdfPCell hcel8 = new PdfPCell(PdfStyle.createUltimateCellWithBorderWithDisableBorder(("8"),
//				Element.ALIGN_BOTTOM, Element.ALIGN_BOTTOM, 8, 10f, false, 0, 1, false, true, true, false, true));
//		headertable01.addCell(hcel8);

		// fifth
		PdfPCell hcel9 = new PdfPCell(PdfStyle.createUltimateCellWithBorderWithDisableBorder(
				("Bill of Supply (Physician Sample)"), Element.ALIGN_CENTER, Element.ALIGN_CENTER, 12, 15f, true, 0, 1,
				false, true, true, true, true));
		headertable01.addCell(hcel9);

		PdfPCell hcel10 = new PdfPCell(PdfStyle.createUltimateCellWithBorderWithDisableBorder(
				("Invoice No." + "         " + challan.getDsp_challan_no()), Element.ALIGN_BOTTOM, Element.ALIGN_BOTTOM,
				9, 18f, true, 0, 1, false, false, true, false, true));
		headertable01.addCell(hcel10);

		// sixth
		PdfPCell hcel9_ = new PdfPCell(PdfStyle.createUltimateCellWithBorderWithDisableBorder(
				("	"), Element.ALIGN_LEFT, Element.ALIGN_LEFT, 10, 10f,
				true, 0, 1, false, false, true, true, true));
		headertable01.addCell(hcel9_);

		PdfPCell hcel10_ = new PdfPCell(PdfStyle.createUltimateCellWithBorderWithDisableBorder(
				("Invoice Date: " + "            " + challan.getDsp_challan_dt()), Element.ALIGN_BOTTOM,
				Element.ALIGN_BOTTOM, 8, 10f, true, 0, 1, false, false, true, false, true));
		headertable01.addCell(hcel10_);
		
		
	//	headertable01.getDefaultCell().setBorder(Rectangle.NO_BORDER);
		//headertable02.getDefaultCell().setBorder(Rectangle.NO_BORDER);
		// headertable02
		PdfPCell hcel11 = new PdfPCell(PdfStyle.createUltimateCellWithBorderWithDisableBorder(
				("NAME :        Palsons Derma Private Limited"), Element.ALIGN_LEFT, Element.ALIGN_LEFT, 8, 10f, true,
				0, 1, false, false, true, true, true));
		headertable02.addCell(hcel11);

//		PdfPCell hcel12 = new PdfPCell(PdfStyle.createUltimateCellWithBorderWithDisableBorder(("GST No."),
//				Element.ALIGN_BOTTOM, Element.ALIGN_BOTTOM, 8, 12f, true, 0, 1, false, false, true, false, true));
//		headertable02.addCell(hcel12);

		PdfPCell hcel12 = new PdfPCell();

		PdfPCell header_Cell71 = new PdfPCell(PdfStyle.createUltimateCellWithBorderWithDisableBorder(("GST No"),
				Element.ALIGN_LEFT, Element.ALIGN_LEFT, 7, 10f, true, 0, 1, false, false, true, true, true));
		headerPdf6.addCell(header_Cell71);

		PdfPCell header_Cell72 = new PdfPCell(
				PdfStyle.createUltimateCellWithBorderWithDisableBorder((challan.getGst_reg_no()), Element.ALIGN_LEFT,
						Element.ALIGN_LEFT, 7, 10f, false, 0, 1, false, false, true, true, true));
		headerPdf6.addCell(header_Cell72);

		hcel12.addElement(headerPdf6);
		hcel12.setBorderWidthLeft(1);
		hcel12.setBorderWidthRight(0);
		hcel12.setBorderWidthTop(0);
		hcel12.setBorderWidthBottom(0);
		headertable02.addCell(hcel12);

		PdfPCell hcel13 = new PdfPCell(PdfStyle.createUltimateCellWithBorderWithDisableBorder(
				("Reverse charge ?" + "               " + "NO"), Element.ALIGN_LEFT, Element.ALIGN_LEFT, 8, 10f, true, 0, 1,
				false, true, true, false, true));
		headertable02.addCell(hcel13);

		// 2
		PdfPCell hcel14 = new PdfPCell(PdfStyle.createUltimateCellWithBorderWithDisableBorder(
				("Address:  " + challan.getLocadd2()), Element.ALIGN_BOTTOM, Element.ALIGN_BOTTOM, 8, 10f, true, 0, 1,
				false, false, true, true, true));
		headertable02.addCell(hcel14);

//		PdfPCell hcel15 = new PdfPCell(PdfStyle.createUltimateCellWithBorderWithDisableBorder(("CIN:"),
//				Element.ALIGN_BOTTOM, Element.ALIGN_BOTTOM, 8, 12f, false, 0, 1, false, false, true, false, true));
//		headertable02.addCell(hcel15);

		PdfPCell hcel15 = new PdfPCell();

		PdfPCell header_Cell73 = new PdfPCell(PdfStyle.createUltimateCellWithBorderWithDisableBorder(("CIN:"),
				Element.ALIGN_LEFT, Element.ALIGN_LEFT, 7, 10f, true, 0, 1, false, false, true, true, true));
		headerPdf7.addCell(header_Cell73);

		PdfPCell header_Cell74 = new PdfPCell(
				PdfStyle.createUltimateCellWithBorderWithDisableBorder((challan.getLoc_cinno()), Element.ALIGN_LEFT,
						Element.ALIGN_LEFT, 7, 10f, false, 0, 1, false, false, true, true, true));
		headerPdf7.addCell(header_Cell74);

		hcel15.addElement(headerPdf7);
		hcel15.setBorderWidthLeft(1);
		hcel15.setBorderWidthRight(0);
		hcel15.setBorderWidthTop(0);
		hcel15.setBorderWidthBottom(0);
		headertable02.addCell(hcel15);

//		PdfPCell hcel16 = new PdfPCell(PdfStyle.createUltimateCellWithBorderWithDisableBorder(("Abhishek"),
//				Element.ALIGN_BOTTOM, Element.ALIGN_BOTTOM, 8, 12f, false, 0, 1, false, false, true, false, true));
//		headertable02.addCell(hcel16);

		PdfPCell hcel16 = new PdfPCell();
		PdfPCell header_Cell93 = new PdfPCell(PdfStyle.createUltimateCellWithBorderWithDisableBorder(("Courier Name"),
				Element.ALIGN_LEFT, Element.ALIGN_LEFT, 8, 10f, true, 0, 1, false, false, true, true, true));
		headerPdf14.addCell(header_Cell93);

		PdfPCell header_Cell94 = new PdfPCell(PdfStyle.createUltimateCellWithBorderWithDisableBorder(("Unique Cargo"),
				Element.ALIGN_LEFT, Element.ALIGN_LEFT, 8, 10f, false, 0, 1, false, false, true, true, true));
		headerPdf14.addCell(header_Cell94);

		hcel16.addElement(headerPdf14);
		hcel16.setBorderWidthLeft(1);
		hcel16.setBorderWidthRight(0);
		hcel16.setBorderWidthTop(0);
		hcel16.setBorderWidthBottom(0);

		headertable02.addCell(hcel16);

		// 3

		PdfPCell hcel17 = new PdfPCell(PdfStyle.createUltimateCellWithBorderWithDisableBorder(("                      "+challan.getLocadd3()),
				Element.ALIGN_LEFT, Element.ALIGN_LEFT, 8, 10f, true, 0, 1, false, false, true, true, true));
		headertable02.addCell(hcel17);

//		PdfPCell hcel18 = new PdfPCell(PdfStyle.createUltimateCellWithBorderWithDisableBorder(("18"),
//				Element.ALIGN_BOTTOM, Element.ALIGN_BOTTOM, 8, 12f, false, 0, 1, false, false, true, false, true));
//		headertable02.addCell(hcel18);

		PdfPCell hcel18 = new PdfPCell();
		PdfPCell header_Cell75 = new PdfPCell(PdfStyle.createUltimateCellWithBorderWithDisableBorder(("PAN No.: "),
				Element.ALIGN_LEFT, Element.ALIGN_LEFT, 8, 10f, true, 0, 1, false, false, true, true, true));
		headerPdf8.addCell(header_Cell75);

		PdfPCell header_Cell76 = new PdfPCell(
				PdfStyle.createUltimateCellWithBorderWithDisableBorder(("AAECP5629D"), Element.ALIGN_LEFT,
						Element.ALIGN_LEFT, 8, 10f, false, 0, 1, false, false, true, true, true));
		headerPdf8.addCell(header_Cell76);

		hcel18.addElement(headerPdf8);
		hcel18.setBorderWidthLeft(1);
		hcel18.setBorderWidthRight(0);
		hcel18.setBorderWidthTop(0);
		hcel18.setBorderWidthBottom(0);
		headertable02.addCell(hcel18);

//		PdfPCell hcel19 = new PdfPCell(PdfStyle.createUltimateCellWithBorderWithDisableBorder(("19"),
//				Element.ALIGN_BOTTOM, Element.ALIGN_BOTTOM, 8, 12f, false, 0, 1, false, false, true, false, true));
//		headertable02.addCell(hcel19);

		PdfPCell hcel19 = new PdfPCell();
		PdfPCell header_Cell95 = new PdfPCell(
				PdfStyle.createUltimateCellWithBorderWithDisableBorder(("Consignment No.: "), Element.ALIGN_LEFT,
						Element.ALIGN_LEFT, 8, 10f, true, 0, 1, false, false, true, true, true));
		headerPdf15.addCell(header_Cell95);

		PdfPCell header_Cell96 = new PdfPCell(PdfStyle.createUltimateCellWithBorderWithDisableBorder((" "),
				Element.ALIGN_LEFT, Element.ALIGN_LEFT, 8, 10f, false, 0, 1, false, false, true, true, true));
		headerPdf15.addCell(header_Cell96);

		hcel19.addElement(headerPdf15);
		hcel19.setBorderWidthLeft(1);
		hcel19.setBorderWidthRight(0);
		hcel19.setBorderWidthTop(0);
		hcel19.setBorderWidthBottom(0);
		headertable02.addCell(hcel19);

		// 4

//	PdfPCell hcel20 = new PdfPCell(PdfStyle.createUltimateCellWithBorderWithDisableBorder(("20"),
//				Element.ALIGN_BOTTOM, Element.ALIGN_BOTTOM, 8, 12f, false, 0, 1, false, false, true, true, true));
//		headertable02.addCell(hcel20);

		PdfPCell hcel20 = new PdfPCell();
		PdfPCell header_Cell51 = new PdfPCell(PdfStyle.createUltimateCellWithBorderWithDisableBorder(("City:"),
				Element.ALIGN_LEFT, Element.ALIGN_LEFT, 8, 10f, true, 0, 1, false, false, true, true, true));
		headerPdf4.addCell(header_Cell51);

		String [] city = challan.getLocadd3().split("-");
		PdfPCell header_Cell52 = new PdfPCell(
				PdfStyle.createUltimateCellWithBorderWithDisableBorder((city[0]), Element.ALIGN_LEFT,
						Element.ALIGN_LEFT, 6, 10f, false, 0, 1, false, false, true, true, true));
		headerPdf4.addCell(header_Cell52);

		PdfPCell header_Cell53 = new PdfPCell(PdfStyle.createUltimateCellWithBorderWithDisableBorder(("Pin Code:"),
				Element.ALIGN_LEFT, Element.ALIGN_LEFT, 8, 10f, true, 0, 1, false, false, true, true, true));
		headerPdf4.addCell(header_Cell53);

		PdfPCell header_Cell54 = new PdfPCell(
				PdfStyle.createUltimateCellWithBorderWithDisableBorder((challan.getLocadd3().substring(8, 15)),
						Element.ALIGN_LEFT, Element.ALIGN_LEFT, 8, 10f, false, 0, 1, false, false, true, true, true));
		headerPdf4.addCell(header_Cell54);

		PdfPCell header_Cell55 = new PdfPCell(PdfStyle.createUltimateCellWithBorderWithDisableBorder(("State code:"),
				Element.ALIGN_LEFT, Element.ALIGN_LEFT, 8, 10f, true, 0, 1, false, false, true, true, true));
		headerPdf4.addCell(header_Cell55);

		PdfPCell header_Cell56 = new PdfPCell(
				PdfStyle.createUltimateCellWithBorderWithDisableBorder((challan.getLoc_statecode()), Element.ALIGN_LEFT,
						Element.ALIGN_LEFT, 8, 10f, false, 0, 1, false, false, true, true, true));
		headerPdf4.addCell(header_Cell56);

		hcel20.addElement(headerPdf4);
		hcel20.setBorder(0);
		headertable02.addCell(hcel20);

//		PdfPCell hcel21 = new PdfPCell(PdfStyle.createUltimateCellWithBorderWithDisableBorder(("21"),
//				Element.ALIGN_BOTTOM, Element.ALIGN_BOTTOM, 8, 12f, false, 0, 1, false, false, true, false, true));
//		headertable02.addCell(hcel21);

		PdfPCell hcel21 = new PdfPCell();
		PdfPCell header_Cell77 = new PdfPCell(PdfStyle.createUltimateCellWithBorderWithDisableBorder(("Drug Lic 1:"),
				Element.ALIGN_LEFT, Element.ALIGN_LEFT, 8, 10f, true, 0, 1, false, false, true, true, true));
		headerPdf16.addCell(header_Cell77);

		PdfPCell header_Cell78 = new PdfPCell(
				PdfStyle.createUltimateCellWithBorderWithDisableBorder((challan.getDptdrug_lic1()), Element.ALIGN_LEFT,
						Element.ALIGN_LEFT, 8, 10f, false, 0, 1, false, false, true, true, true));
		headerPdf16.addCell(header_Cell78);

		hcel21.addElement(headerPdf16);
		hcel21.setBorderWidthLeft(1);
		hcel21.setBorderWidthRight(0);
		hcel21.setBorderWidthTop(0);
		hcel21.setBorderWidthBottom(0);

		headertable02.addCell(hcel21);

//		PdfPCell hcel22 = new PdfPCell(PdfStyle.createUltimateCellWithBorderWithDisableBorder(("22"),
//				Element.ALIGN_BOTTOM, Element.ALIGN_BOTTOM, 8, 12f, false, 0, 1, false, false, true, false, true));
//		headertable02.addCell(hcel22);

		PdfPCell hcel22 = new PdfPCell();
		PdfPCell header_Cell951 = new PdfPCell(
				PdfStyle.createUltimateCellWithBorderWithDisableBorder(("Consignment Da: "), Element.ALIGN_LEFT,
						Element.ALIGN_LEFT, 8, 10f, true, 0, 1, false, false, true, true, true));
		headerPdf17.addCell(header_Cell951);

		if(challan.getShip_doc_date()!= null) {
		PdfPCell header_Cell961 = new PdfPCell(
				PdfStyle.createUltimateCellWithBorderWithDisableBorder((Utility.getdDateFormat(challan.getShip_doc_date())),
						Element.ALIGN_LEFT, Element.ALIGN_LEFT, 8, 12f, false, 0, 1, false, false, true, true, true));
		headerPdf17.addCell(header_Cell961);
		}
	

		hcel22.addElement(headerPdf17);
		hcel22.setBorderWidthLeft(1);
		hcel22.setBorderWidthRight(0);
		hcel22.setBorderWidthTop(0);
		hcel22.setBorderWidthBottom(0);

		headertable02.addCell(hcel22);

		// 5

//	PdfPCell hcel23 = new PdfPCell(PdfStyle.createUltimateCellWithBorderWithDisableBorder(("20"),
//				Element.ALIGN_BOTTOM, Element.ALIGN_BOTTOM, 8, 12f, false, 0, 1, false, false, true, true, true));
//		headertable02.addCell(hcel23);
		/// consigne details
		PdfPCell hcel23 = new PdfPCell();
		PdfPCell header_Cell61 = new PdfPCell(PdfStyle.createUltimateCellWithBorderWithDisableBorder(("State:"),
				Element.ALIGN_LEFT, Element.ALIGN_LEFT, 8, 12f, true, 0, 1, false, false, true, true, true));
		headerPdf5.addCell(header_Cell61);

		PdfPCell header_Cell62 = new PdfPCell(
				PdfStyle.createUltimateCellWithBorderWithDisableBorder((challan.getState()), Element.ALIGN_LEFT,
						Element.ALIGN_LEFT, 8, 12f, false, 0, 1, false, false, true, true, true));
		headerPdf5.addCell(header_Cell62);

		PdfPCell header_Cell63 = new PdfPCell(PdfStyle.createUltimateCellWithBorderWithDisableBorder(("State code:"),
				Element.ALIGN_LEFT, Element.ALIGN_LEFT, 8, 12f, true, 0, 1, false, false, true, true, true));
		headerPdf5.addCell(header_Cell63);

		PdfPCell header_Cell64 = new PdfPCell(
				PdfStyle.createUltimateCellWithBorderWithDisableBorder((challan.getLoc_statecode()), Element.ALIGN_LEFT,
						Element.ALIGN_LEFT, 8, 12f, false, 0, 1, false, false, true, true, true));
		headerPdf5.addCell(header_Cell64);

		PdfPCell header_Cell65 = new PdfPCell(PdfStyle.createUltimateCellWithBorderWithDisableBorder(("phone:"),
				Element.ALIGN_LEFT, Element.ALIGN_LEFT, 8, 12f, true, 0, 1, false, false, true, true, true));
		headerPdf5.addCell(header_Cell65);

		PdfPCell header_Cell66 = new PdfPCell(
				PdfStyle.createUltimateCellWithBorderWithDisableBorder((challan.getCf_tel_no()), Element.ALIGN_LEFT,
						Element.ALIGN_LEFT, 8, 12f, false, 0, 1, false, false, true, true, true));
		headerPdf5.addCell(header_Cell66);

		hcel23.addElement(headerPdf5);
		hcel23.setBorder(0);
		headertable02.addCell(hcel23);
//
//		PdfPCell hcel24 = new PdfPCell(PdfStyle.createUltimateCellWithBorderWithDisableBorder(("21"),
//				Element.ALIGN_BOTTOM, Element.ALIGN_BOTTOM, 8, 12f, false, 0, 1, false, false, true, false, true));
//		headertable02.addCell(hcel24);

		PdfPCell hcel24 = new PdfPCell();
		PdfPCell header_Cell79 = new PdfPCell(PdfStyle.createUltimateCellWithBorderWithDisableBorder(("Valid upto:"),
				Element.ALIGN_LEFT, Element.ALIGN_LEFT, 8, 12f, true, 0, 1, false, false, true, true, true));
		headerPdf10.addCell(header_Cell79);

		PdfPCell header_Cell80 = new PdfPCell(PdfStyle.createUltimateCellWithBorderWithDisableBorder((" "),
				Element.ALIGN_LEFT, Element.ALIGN_LEFT, 8, 12f, false, 0, 1, false, false, true, true, true));
		headerPdf10.addCell(header_Cell80);

		hcel24.addElement(headerPdf10);
		hcel24.setBorderWidthLeft(1);
		hcel24.setBorderWidthRight(0);
		hcel24.setBorderWidthTop(0);
		hcel24.setBorderWidthBottom(0);

		headertable02.addCell(hcel24);

//
//		PdfPCell hcel25 = new PdfPCell(PdfStyle.createUltimateCellWithBorderWithDisableBorder(("22"),
//				Element.ALIGN_BOTTOM, Element.ALIGN_BOTTOM, 8, 12f, false, 0, 1, false, false, true, false, true));
//		headertable02.addCell(hcel25);

		PdfPCell hcel25 = new PdfPCell();
		PdfPCell header_Cell12 = new PdfPCell(PdfStyle.createUltimateCellWithBorderWithDisableBorder(("Eway Bill No."),
				Element.ALIGN_LEFT, Element.ALIGN_LEFT, 8, 12f, true, 0, 1, false, false, true, true, true));
		headerPdf18.addCell(header_Cell12);

		PdfPCell header_Cell14 = new PdfPCell(PdfStyle.createUltimateCellWithBorderWithDisableBorder((" "),
				Element.ALIGN_LEFT, Element.ALIGN_LEFT, 8, 12f, false, 0, 1, false, false, true, true, true));
		headerPdf18.addCell(header_Cell14);

		hcel25.addElement(headerPdf18);
		hcel25.setBorderWidthLeft(1);
		hcel25.setBorderWidthRight(0);
		hcel25.setBorderWidthTop(0);
		hcel25.setBorderWidthBottom(0);
		headertable02.addCell(hcel25);

		// 6

		PdfPCell hcel26 = new PdfPCell(PdfStyle.createUltimateCellWithBorderWithDisableBorder(
				("EMAIL: " + "   " + "info@palsonsderma.com"), Element.ALIGN_BOTTOM, Element.ALIGN_BOTTOM, 8, 12f, true,
				0, 1, false, false, true, true, true));
		headertable02.addCell(hcel26);

//		PdfPCell hcel27 = new PdfPCell(PdfStyle.createUltimateCellWithBorderWithDisableBorder(("24"),
//				Element.ALIGN_BOTTOM, Element.ALIGN_BOTTOM, 8, 12f, false, 0, 1, false, false, true, false, true));
//		headertable02.addCell(hcel27);

		PdfPCell hcel27 = new PdfPCell();
		PdfPCell header_Cell81 = new PdfPCell(PdfStyle.createUltimateCellWithBorderWithDisableBorder(("Drug Lic 2:"),
				Element.ALIGN_LEFT, Element.ALIGN_LEFT, 8, 12f, true, 0, 1, false, false, true, true, true));
		headerPdf11.addCell(header_Cell81);

		PdfPCell header_Cell82 = new PdfPCell(
				PdfStyle.createUltimateCellWithBorderWithDisableBorder((challan.getDptdrug_lic2()), Element.ALIGN_LEFT,
						Element.ALIGN_LEFT, 8, 12f, false, 0, 1, false, false, true, true, true));
		headerPdf11.addCell(header_Cell82);

		hcel27.addElement(headerPdf11);
		hcel27.setBorderWidthLeft(1);
		hcel27.setBorderWidthRight(0);
		hcel27.setBorderWidthTop(0);
		hcel27.setBorderWidthBottom(0);
		headertable02.addCell(hcel27);

//
//		PdfPCell hcel28 = new PdfPCell(PdfStyle.createUltimateCellWithBorderWithDisableBorder(("25"),
//				Element.ALIGN_BOTTOM, Element.ALIGN_BOTTOM, 8, 12f, false, 0, 1, false, false, true, false, true));
//		headertable02.addCell(hcel28);

		PdfPCell hcel28 = new PdfPCell();
		PdfPCell header_Cell85 = new PdfPCell(PdfStyle.createUltimateCellWithBorderWithDisableBorder(("No. of cases:"),
				Element.ALIGN_LEFT, Element.ALIGN_LEFT, 8, 12f, true, 0, 1, false, false, true, true, true));
		headerPdf19.addCell(header_Cell85);

		PdfPCell header_Cell86 = new PdfPCell(PdfStyle.createUltimateCellWithBorderWithDisableBorder(("000"),
				Element.ALIGN_LEFT, Element.ALIGN_LEFT, 8, 12f, false, 0, 1, false, false, true, true, true));
		headerPdf19.addCell(header_Cell86);

		hcel28.addElement(headerPdf19);

		hcel28.setBorderWidthLeft(1);
		hcel28.setBorderWidthRight(0);
		hcel28.setBorderWidthTop(0);
		hcel28.setBorderWidthBottom(0);
		headertable02.addCell(hcel28);

		// 7
		PdfPCell hcel29 = new PdfPCell(PdfStyle.createUltimateCellWithBorderWithDisableBorder((" "),
				Element.ALIGN_BOTTOM, Element.ALIGN_BOTTOM, 8, 12f, false, 0, 1, false, false, true, true, true));
		headertable02.addCell(hcel29);

//		PdfPCell hcel30 = new PdfPCell(PdfStyle.createUltimateCellWithBorderWithDisableBorder(("27"),
//				Element.ALIGN_BOTTOM, Element.ALIGN_BOTTOM, 8, 12f, false, 0, 1, false, false, true, false, true));
//		headertable02.addCell(hcel30);

		PdfPCell hcel30 = new PdfPCell();
		PdfPCell header_Cell84 = new PdfPCell(PdfStyle.createUltimateCellWithBorderWithDisableBorder(("Valid upto:"),
				Element.ALIGN_LEFT, Element.ALIGN_LEFT, 8, 12f, true, 0, 1, false, false, true, true, true));
		headerPdf12.addCell(header_Cell84);

		PdfPCell header_Cell83 = new PdfPCell(
				PdfStyle.createUltimateCellWithBorderWithDisableBorder((challan.getDptdrug_lic2()), Element.ALIGN_LEFT,
						Element.ALIGN_LEFT, 8, 12f, false, 0, 1, false, false, true, true, true));
		headerPdf12.addCell(header_Cell83);

		hcel30.addElement(headerPdf12);
		hcel30.setBorderWidthLeft(1);
		hcel30.setBorderWidthRight(0);
		hcel30.setBorderWidthTop(0);
		hcel30.setBorderWidthBottom(0);
		headertable02.addCell(hcel30);

//
//		PdfPCell hcel31 = new PdfPCell(PdfStyle.createUltimateCellWithBorderWithDisableBorder(("28"),
//				Element.ALIGN_BOTTOM, Element.ALIGN_BOTTOM, 8, 12f, false, 0, 1, false, false, true, false, true));
//		headertable02.addCell(hcel31);

		PdfPCell hcel31 = new PdfPCell();
		PdfPCell header_Cell89 = new PdfPCell(PdfStyle.createUltimateCellWithBorderWithDisableBorder(("Weight:"),
				Element.ALIGN_LEFT, Element.ALIGN_LEFT, 8, 12f, true, 0, 1, false, false, true, true, true));
		headerPdf20.addCell(header_Cell89);

		PdfPCell header_Cell90 = new PdfPCell(PdfStyle.createUltimateCellWithBorderWithDisableBorder(("00"),
				Element.ALIGN_LEFT, Element.ALIGN_LEFT, 8, 12f, false, 0, 1, false, false, true, true, true));
		headerPdf20.addCell(header_Cell90);

		hcel31.addElement(headerPdf20);
		hcel31.setBorderWidthLeft(1);
		hcel31.setBorderWidthRight(0);
		hcel31.setBorderWidthTop(0);
		hcel31.setBorderWidthBottom(0);
		headertable02.addCell(hcel31);

		// headertable03
		PdfPTable headertable32 = new PdfPTable(1);
		headertable32.setWidthPercentage(100);
		headertable32.setWidths(new float[] { 1f });
		
		PdfPTable headertable33 = new PdfPTable(1);
		headertable33.setWidthPercentage(100);
		headertable33.setWidths(new float[] { 1f });
		
		PdfPCell hcel132_ = new PdfPCell();
		PdfPCell hcel132 = new PdfPCell(PdfStyle.createUltimateCellWithBorderWithDisableBorder(
				("Billing Address of Recipient"), Element.ALIGN_CENTER, Element.ALIGN_CENTER, 10, 13f, true, 0, 1,
				false, true, true, true, true));
		headertable32.addCell(hcel132);
		headertable32.getDefaultCell().setPadding(0);
		hcel132_.setBorderWidthLeft(0);
		hcel132_.setBorderWidthRight(1);
		hcel132_.setBorderWidthTop(0);
		hcel132_.setBorderWidthBottom(0);
		hcel132_.addElement(headertable32);
		headertable03.addCell(hcel132_);
		headertable03.getDefaultCell().setPadding(0);
		
		
	
		PdfPCell hcel133_ = new PdfPCell();
		PdfPCell hcel133 = new PdfPCell(PdfStyle.createUltimateCellWithBorderWithDisableBorder(
				("Shipping Address of Recipient"), Element.ALIGN_CENTER, Element.ALIGN_CENTER, 10, 13f, true, 0, 1,
				false, true, true, true, true));
		headertable33.addCell(hcel133);
		hcel133_.setBorderWidthLeft(0);
		hcel133_.setBorderWidthRight(0);
		hcel133_.setBorderWidthTop(0);
		hcel133_.setBorderWidthBottom(0);
		hcel133_.addElement(headertable33);
		headertable03.addCell(hcel133_);

		PdfPTable	headertable41 = new PdfPTable(1);
		headertable41.setWidthPercentage(100);
		headertable41.setWidths(new float[] { 9f});
		
		PdfPCell hcel134 = new PdfPCell();
		PdfPCell hcel134_= new PdfPCell(PdfStyle.createUltimateCellWithBorderWithDisableBorder(
				("Name:" + "       " + challan.getDspfstaff_displayname()), Element.ALIGN_LEFT, Element.ALIGN_LEFT, 7,
				12f, true, 0, 1, false, false, true, true, true));
	
		headertable41.addCell(hcel134_);
		
		hcel134.setBorderWidthLeft(0);
		hcel134.setBorderWidthRight(1);
		hcel134.setBorderWidthTop(0);
		hcel134.setBorderWidthBottom(0);
		hcel134.addElement(headertable41);
		headertable03.addCell(hcel134);
		
		

		PdfPCell hcel135 = new PdfPCell(PdfStyle.createUltimateCellWithBorderWithDisableBorder(
				("Name:" + "       " + challan.getDspfstaff_displayname()), Element.ALIGN_BOTTOM, Element.ALIGN_BOTTOM,
				7, 12f, true, 0, 1, false, false, true, true, true));
		headertable03.addCell(hcel135);

		/// tables for billing adress

		PdfPTable headertable21 = new PdfPTable(2);
		PdfPTable headertable22 = new PdfPTable(2);
		PdfPTable headertable23 = new PdfPTable(6);
		PdfPTable headertable24 = new PdfPTable(6);
		PdfPTable headertable25 = new PdfPTable(6);
		PdfPTable headertable26 = new PdfPTable(6);
		PdfPTable headertable27 = new PdfPTable(4);
		PdfPTable headertable28 = new PdfPTable(4);
		PdfPTable headertable29 = new PdfPTable(1);
		PdfPTable headertable30 = new PdfPTable(1);
		
		PdfPTable headertable31 = new PdfPTable(1);

		headertable21.setWidthPercentage(100);
		headertable22.setWidthPercentage(100);
		headertable23.setWidthPercentage(100);
		headertable24.setWidthPercentage(100);
		headertable25.setWidthPercentage(100);
		headertable26.setWidthPercentage(100);
		headertable27.setWidthPercentage(100);
		headertable28.setWidthPercentage(100);
		headertable29.setWidthPercentage(100);
		headertable30.setWidthPercentage(100);
		headertable31.setWidthPercentage(100);
      
		headertable21.setWidths(new float[] { 0.20f, 1f });
		headertable22.setWidths(new float[] { 0.20f, 1f });
		headertable23.setWidths(new float[] { 1f, 1f, 1f, 1f, 1f, 1f });
		headertable24.setWidths(new float[] { 1f, 1f, 1f, 1f, 1f, 1f });
		headertable25.setWidths(new float[] { 1f, 1f, 1f, 1f, 1f, 1f });
		headertable26.setWidths(new float[] { 1f, 1f, 1f, 1f, 1f, 1f });
		headertable27.setWidths(new float[] { 1f, 1f, 0.5f, 1.5f });
		headertable28.setWidths(new float[] { 1f, 1f, 0.5f, 1.5f });
		headertable29.setWidths(new float[] { 1f });
		headertable30.setWidths(new float[] { 1f });
		headertable31.setWidths(new float[] { 1f });
		

//		PdfPCell hcel136 = new PdfPCell(PdfStyle.createUltimateCellWithBorderWithDisableBorder(
//				("3"), Element.ALIGN_CENTER, Element.ALIGN_CENTER, 7, 12f, true, 0, 1,
//				false, false, true, true, false));
//		headertable03.addCell(hcel136);

		PdfPCell hcel136 = new PdfPCell();
		PdfPCell header_Cell91 = new PdfPCell(PdfStyle.createUltimateCellWithBorderWithDisableBorder(("Address:"),
				Element.ALIGN_LEFT, Element.ALIGN_LEFT, 8, 12f, true, 0, 1, false, false, true, true, true));
		headertable21.addCell(header_Cell91);

		PdfPCell header_Cell92 = new PdfPCell(
				PdfStyle.createUltimateCellWithBorderWithDisableBorder((challan.getDspfstaffaddr1()),
						Element.ALIGN_LEFT, Element.ALIGN_LEFT, 7, 12f, false, 0, 1, false, false, true, true, true));
		headertable21.addCell(header_Cell92);

		hcel136.addElement(headertable21);
		hcel136.setBorderWidthLeft(0);
		hcel136.setBorderWidthRight(1);
		hcel136.setBorderWidthTop(0);
		hcel136.setBorderWidthBottom(0);
		headertable03.addCell(hcel136);

//		PdfPCell hcel137 = new PdfPCell(PdfStyle.createUltimateCellWithBorderWithDisableBorder(
//				("4"), Element.ALIGN_BOTTOM, Element.ALIGN_BOTTOM, 7, 12f, true, 0, 1,
//				false, false, true, true, true));
//		headertable03.addCell(hcel137);

		PdfPCell hcel137 = new PdfPCell();
		PdfPCell header_Cell101 = new PdfPCell(PdfStyle.createUltimateCellWithBorderWithDisableBorder(("Address:"),
				Element.ALIGN_LEFT, Element.ALIGN_LEFT, 8, 12f, true, 0, 1, false, false, true, true, true));
		headertable22.addCell(header_Cell101);

		PdfPCell header_Cell102 = new PdfPCell(
				PdfStyle.createUltimateCellWithBorderWithDisableBorder((challan.getDspfstaffaddr1()),
						Element.ALIGN_LEFT, Element.ALIGN_LEFT, 7, 12f, false, 0, 1, false, false, true, true, true));
		headertable22.addCell(header_Cell102);

		hcel137.addElement(headertable22);
		hcel137.setBorderWidthLeft(0);
		hcel137.setBorderWidthRight(0);
		hcel137.setBorderWidthTop(0);
		hcel137.setBorderWidthBottom(0);
		headertable03.addCell(hcel137);

		
		PdfPCell hcel138_ = new PdfPCell();
		PdfPCell hcel138 = new PdfPCell(PdfStyle.createUltimateCellWithBorderWithDisableBorder(
				("     " + "               " + challan.getDspfstaffaddr2()), Element.ALIGN_LEFT,
				Element.ALIGN_LEFT, 7, 12f, false, 0, 1, false, false, true, true, true));
		headertable29.addCell(hcel138);
		hcel138_.addElement(headertable29);
		hcel138_.setBorderWidthLeft(0);
		hcel138_.setBorderWidthRight(1);
		hcel138_.setBorderWidthTop(0);
		hcel138_.setBorderWidthBottom(0);
		headertable03.addCell(hcel138_);
		
		
	//	PdfPCell hcel139_ = new PdfPCell();
		PdfPCell hcel139 = new PdfPCell(PdfStyle.createUltimateCellWithBorderWithDisableBorder(
				("     " + "             " + challan.getDspfstaffaddr2()), Element.ALIGN_LEFT,
				Element.ALIGN_LEFT, 7, 12f, false, 0, 1, false, false, true, true, true));
		
		//headertable29.addCell(hcel139);
//		hcel139_.addElement(headertable29);
//		hcel139_.setBorderWidthLeft(0);
//		hcel139_.setBorderWidthRight(0);
//		hcel139_.setBorderWidthTop(0);
//		hcel139_.setBorderWidthBottom(0);
		headertable03.addCell(hcel139);
		
		
		PdfPCell hcel140_ = new PdfPCell();

		PdfPCell hcel140 = new PdfPCell(PdfStyle.createUltimateCellWithBorderWithDisableBorder(
				("         " + "               " + challan.getDspfstaffaddr3()), Element.ALIGN_LEFT, Element.ALIGN_LEFT,
				7, 12f, false, 0, 1, false, false, true, true, true));
		
		headertable30.addCell(hcel140);
		hcel140_.addElement(headertable30);
		hcel140_.setBorderWidthLeft(0);
		hcel140_.setBorderWidthRight(1);
		hcel140_.setBorderWidthTop(0);
		hcel140_.setBorderWidthBottom(0);
		headertable03.addCell(hcel140_);
		
		
		PdfPCell hcel141 = new PdfPCell(PdfStyle.createUltimateCellWithBorderWithDisableBorder(
				("         " + "               " + challan.getDspfstaffaddr3()), Element.ALIGN_LEFT, Element.ALIGN_LEFT,
				7, 12f, false, 0, 1, false, false, true, true, true));
		headertable03.addCell(hcel141);
//		hcel141.setBorderWidthLeft(1);
//		hcel141.setBorderWidthRight(1);
//		hcel141.setBorderWidthTop(0);
//		hcel141.setBorderWidthBottom(0);

//
//		PdfPCell hcel142 = new PdfPCell(PdfStyle.createUltimateCellWithBorderWithDisableBorder(("9"),
//				Element.ALIGN_CENTER, Element.ALIGN_CENTER, 7, 12f, true, 0, 1, false, false, true, true, false));
//		headertable03.addCell(hcel142);

		PdfPCell hcel142 = new PdfPCell();
		PdfPCell header_Cell105 = new PdfPCell(PdfStyle.createUltimateCellWithBorderWithDisableBorder(("City:"),
				Element.ALIGN_LEFT, Element.ALIGN_LEFT, 8, 12f, true, 0, 1, false, false, true, true, true));
		headertable23.addCell(header_Cell105);
		
		
		String[] arr = challan.getDptdestination().split("-");
		
		

		PdfPCell header_Cell106 = new PdfPCell(
				PdfStyle.createUltimateCellWithBorderWithDisableBorder((arr[0]), Element.ALIGN_LEFT,
						Element.ALIGN_LEFT, 7, 12f, false, 0, 1, false, false, true, true, true));
		headertable23.addCell(header_Cell106);

		PdfPCell header_Cell107 = new PdfPCell(PdfStyle.createUltimateCellWithBorderWithDisableBorder(("State:"),
				Element.ALIGN_LEFT, Element.ALIGN_LEFT, 7, 12f, true, 0, 1, false, false, true, true, true));
		headertable23.addCell(header_Cell107);

		PdfPCell header_Cell108 = new PdfPCell(
				PdfStyle.createUltimateCellWithBorderWithDisableBorder((challan.getState()), Element.ALIGN_RIGHT,
						Element.ALIGN_RIGHT, 7, 12f, false, 0, 1, false, false, true, true, true));
		headertable23.addCell(header_Cell108);

		PdfPCell header_Cell109 = new PdfPCell(PdfStyle.createUltimateCellWithBorderWithDisableBorder(("State code"),
				Element.ALIGN_RIGHT, Element.ALIGN_RIGHT, 7, 12f, true, 0, 1, false, false, true, true, true));
		headertable23.addCell(header_Cell109);

		PdfPCell header_Cell110 = new PdfPCell(
				PdfStyle.createUltimateCellWithBorderWithDisableBorder((challan.getState_code()),
						Element.ALIGN_RIGHT, Element.ALIGN_RIGHT, 7, 12f, false, 0, 1, false, false, true, true, true));
		headertable23.addCell(header_Cell110);

		hcel142.addElement(headertable23);
		hcel142.setBorderWidthLeft(0);
		hcel142.setBorderWidthRight(1);
		hcel142.setBorderWidthTop(0);
		hcel142.setBorderWidthBottom(0);
		headertable03.addCell(hcel142);

//		PdfPCell hcel143 = new PdfPCell(PdfStyle.createUltimateCellWithBorderWithDisableBorder(("10"),
//				Element.ALIGN_BOTTOM, Element.ALIGN_BOTTOM, 7, 12f, true, 0, 1, false, false, true, true, true));
//		headertable03.addCell(hcel143);

		PdfPCell hcel143 = new PdfPCell();
		PdfPCell header_Cell111 = new PdfPCell(PdfStyle.createUltimateCellWithBorderWithDisableBorder(("City:"),
				Element.ALIGN_LEFT, Element.ALIGN_LEFT, 8, 12f, true, 0, 1, false, false, true, true, true));
		headertable24.addCell(header_Cell111);

		PdfPCell header_Cell112 = new PdfPCell(
				PdfStyle.createUltimateCellWithBorderWithDisableBorder((arr[0]), Element.ALIGN_LEFT,
						Element.ALIGN_LEFT, 7, 12f, false, 0, 1, false, false, true, true, true));
		headertable24.addCell(header_Cell112);

		PdfPCell header_Cell113 = new PdfPCell(PdfStyle.createUltimateCellWithBorderWithDisableBorder(("State:"),
				Element.ALIGN_LEFT, Element.ALIGN_LEFT, 7, 12f, true, 0, 1, false, false, true, true, true));
		headertable24.addCell(header_Cell113);

		PdfPCell header_Cell114 = new PdfPCell(
				PdfStyle.createUltimateCellWithBorderWithDisableBorder((challan.getState()), Element.ALIGN_RIGHT,
						Element.ALIGN_RIGHT, 7, 12f, false, 0, 1, false, false, true, true, true));
		headertable24.addCell(header_Cell114);

		PdfPCell header_Cell115 = new PdfPCell(PdfStyle.createUltimateCellWithBorderWithDisableBorder(("State code"),
				Element.ALIGN_LEFT, Element.ALIGN_LEFT, 7, 12f, true, 0, 1, false, false, true, true, true));
		headertable24.addCell(header_Cell115);

		PdfPCell header_Cell116 = new PdfPCell(
				PdfStyle.createUltimateCellWithBorderWithDisableBorder((challan.getState_code()),
						Element.ALIGN_RIGHT, Element.ALIGN_RIGHT, 7, 12f, false, 0, 1, false, false, true, true, true));
		headertable24.addCell(header_Cell116);

		hcel143.addElement(headertable23);
		hcel143.setBorderWidthLeft(0);
		hcel143.setBorderWidthRight(0);
		hcel143.setBorderWidthTop(0);
		hcel143.setBorderWidthBottom(0);
		headertable03.addCell(hcel143);

//		PdfPCell hcel144 = new PdfPCell(PdfStyle.createUltimateCellWithBorderWithDisableBorder(("11"),
//				Element.ALIGN_CENTER, Element.ALIGN_CENTER, 7, 12f, true, 0, 1, false, false, true, true, false));
//		headertable03.addCell(hcel144);

		PdfPCell hcel144 = new PdfPCell();
		PdfPCell header_Cell117 = new PdfPCell(PdfStyle.createUltimateCellWithBorderWithDisableBorder(("GST No."),
				Element.ALIGN_LEFT, Element.ALIGN_LEFT, 8, 12f, true, 0, 1, false, false, true, true, true));
		headertable25.addCell(header_Cell117);

		PdfPCell header_Cell118 = new PdfPCell(PdfStyle.createUltimateCellWithBorderWithDisableBorder(("Unregistered"),
				Element.ALIGN_LEFT, Element.ALIGN_LEFT, 7, 12f, false, 0, 1, false, false, true, true, true));
		headertable25.addCell(header_Cell118);

		PdfPCell header_Cell120 = new PdfPCell(PdfStyle.createUltimateCellWithBorderWithDisableBorder(("Phone No."),
				Element.ALIGN_RIGHT, Element.ALIGN_RIGHT, 7, 12f, true, 0, 1, false, false, true, true, true));
		headertable25.addCell(header_Cell120);

		PdfPCell header_Cell121 = new PdfPCell(
				PdfStyle.createUltimateCellWithBorderWithDisableBorder((challan.getDspfstaff_mobile()),
						Element.ALIGN_RIGHT, Element.ALIGN_RIGHT, 7, 12f, false, 0, 1, false, false, true, true, true));
		headertable25.addCell(header_Cell121);

		PdfPCell header_Cell122 = new PdfPCell(PdfStyle.createUltimateCellWithBorderWithDisableBorder(("Pin Code:"),
				Element.ALIGN_RIGHT, Element.ALIGN_RIGHT, 7, 12f, true, 0, 1, false, false, true, true, true));
		headertable25.addCell(header_Cell122);

		PdfPCell header_Cell119 = new PdfPCell(
				PdfStyle.createUltimateCellWithBorderWithDisableBorder((arr[1]),
						Element.ALIGN_RIGHT, Element.ALIGN_RIGHT, 7, 12f, false, 0, 1, false, false, true, true, true));
		headertable25.addCell(header_Cell119);

		hcel144.addElement(headertable25);
		hcel144.setBorderWidthLeft(0);
		hcel144.setBorderWidthRight(1);
		hcel144.setBorderWidthTop(0);
		hcel144.setBorderWidthBottom(0);
		headertable03.addCell(hcel144);

//		PdfPCell hcel145 = new PdfPCell(PdfStyle.createUltimateCellWithBorderWithDisableBorder(("12"),
//				Element.ALIGN_BOTTOM, Element.ALIGN_BOTTOM, 7, 12f, true, 0, 1, false, false, true, true, true));
//		headertable03.addCell(hcel145);

		PdfPCell hcel145 = new PdfPCell();
		PdfPCell header_Cell123 = new PdfPCell(PdfStyle.createUltimateCellWithBorderWithDisableBorder(("GST No."),
				Element.ALIGN_LEFT, Element.ALIGN_LEFT, 8, 12f, true, 0, 1, false, false, true, true, true));
		headertable26.addCell(header_Cell123);

		PdfPCell header_Cell124 = new PdfPCell(PdfStyle.createUltimateCellWithBorderWithDisableBorder(("Unregistered"),
				Element.ALIGN_LEFT, Element.ALIGN_LEFT, 7, 12f, false, 0, 1, false, false, true, true, true));
		headertable26.addCell(header_Cell124);

		PdfPCell header_Cell125 = new PdfPCell(PdfStyle.createUltimateCellWithBorderWithDisableBorder(("Phone No."),
				Element.ALIGN_RIGHT, Element.ALIGN_RIGHT, 7, 12f, true, 0, 1, false, false, true, true, true));
		headertable26.addCell(header_Cell125);

		PdfPCell header_Cell126 = new PdfPCell(
				PdfStyle.createUltimateCellWithBorderWithDisableBorder((challan.getDspfstaff_mobile()),
						Element.ALIGN_RIGHT, Element.ALIGN_RIGHT, 7, 12f, false, 0, 1, false, false, true, true, true));
		headertable26.addCell(header_Cell126);

		PdfPCell header_Cell127 = new PdfPCell(PdfStyle.createUltimateCellWithBorderWithDisableBorder(("Pin Code:"),
				Element.ALIGN_RIGHT, Element.ALIGN_RIGHT, 7, 12f, true, 0, 1, false, false, true, true, true));
		headertable26.addCell(header_Cell127);

		PdfPCell header_Cell128 = new PdfPCell(
				PdfStyle.createUltimateCellWithBorderWithDisableBorder((arr[1]),
						Element.ALIGN_RIGHT, Element.ALIGN_RIGHT, 7, 12f, false, 0, 1, false, false, true, true, true));
		headertable26.addCell(header_Cell128);

		hcel145.addElement(headertable26);
		hcel145.setBorderWidthLeft(0);
		hcel145.setBorderWidthRight(0);
		hcel145.setBorderWidthTop(0);
		hcel145.setBorderWidthBottom(0);
		headertable03.addCell(hcel145);

//		PdfPCell hcel146 = new PdfPCell(PdfStyle.createUltimateCellWithBorderWithDisableBorder(("13")--,
//				Element.ALIGN_CENTER, Element.ALIGN_CENTER, 7, 12f, true, 0, 1, false, false, true, true, false));
//		headertable03.addCell(hcel146);
//		

		PdfPCell hcel146 = new PdfPCell();
		PdfPCell header_Cell129 = new PdfPCell(PdfStyle.createUltimateCellWithBorderWithDisableBorder(("PAN No."),
				Element.ALIGN_LEFT, Element.ALIGN_LEFT, 8, 12f, true, 0, 1, false, false, true, true, true));
		headertable27.addCell(header_Cell129);

		PdfPCell header_Cell130 = new PdfPCell(PdfStyle.createUltimateCellWithBorderWithDisableBorder(("Not Available"),
				Element.ALIGN_LEFT, Element.ALIGN_LEFT, 7, 12f, false, 0, 1, false, false, true, true, true));
		headertable27.addCell(header_Cell130);

		PdfPCell header_Cell131 = new PdfPCell(PdfStyle.createUltimateCellWithBorderWithDisableBorder(("Email:"),
				Element.ALIGN_RIGHT, Element.ALIGN_RIGHT, 7, 12f, true, 0, 1, false, false, true, true, true));
		headertable27.addCell(header_Cell131);

		PdfPCell header_Cell132 = new PdfPCell(
				PdfStyle.createUltimateCellWithBorderWithDisableBorder((challan.getDspfstaff_email()),
						Element.ALIGN_RIGHT, Element.ALIGN_RIGHT, 7, 12f, false, 0, 1, false, false, true, true, true));
		headertable27.addCell(header_Cell132);

		hcel146.addElement(headertable27);
		hcel146.setBorderWidthLeft(0);
		hcel146.setBorderWidthRight(1);
		hcel146.setBorderWidthTop(0);
		hcel146.setBorderWidthBottom(0);
		headertable03.addCell(hcel146);

//		PdfPCell hcel147 = new PdfPCell(PdfStyle.createUltimateCellWithBorderWithDisableBorder(("14"),
//				Element.ALIGN_BOTTOM, Element.ALIGN_BOTTOM, 7, 12f, true, 0, 1, false, false, true, true, true));
//		headertable03.addCell(hcel147);

		PdfPCell hcel147 = new PdfPCell();
		PdfPCell header_Cell133 = new PdfPCell(PdfStyle.createUltimateCellWithBorderWithDisableBorder(("PAN No."),
				Element.ALIGN_LEFT, Element.ALIGN_LEFT, 8, 12f, true, 0, 1, false, false, true, true, true));
		headertable28.addCell(header_Cell133);

		PdfPCell header_Cell135 = new PdfPCell(PdfStyle.createUltimateCellWithBorderWithDisableBorder(("Not Available"),
				Element.ALIGN_LEFT, Element.ALIGN_LEFT, 7, 12f, false, 0, 1, false, false, true, true, true));
		headertable28.addCell(header_Cell135);

		PdfPCell header_Cell136 = new PdfPCell(PdfStyle.createUltimateCellWithBorderWithDisableBorder(("Email:"),
				Element.ALIGN_RIGHT, Element.ALIGN_RIGHT, 7, 12f, true, 0, 1, false, false, true, true, true));
		headertable28.addCell(header_Cell136);

		PdfPCell header_Cell137 = new PdfPCell(
				PdfStyle.createUltimateCellWithBorderWithDisableBorder((challan.getDspfstaff_email()),
						Element.ALIGN_RIGHT, Element.ALIGN_RIGHT, 7, 12f, false, 0, 1, false, false, true, true, true));
		headertable28.addCell(header_Cell137);

		hcel147.addElement(headertable28);
		hcel147.setBorderWidthLeft(0);
		hcel147.setBorderWidthRight(0);
		hcel147.setBorderWidthTop(0);
		hcel147.setBorderWidthBottom(0);
		headertable03.addCell(hcel147);

///headings
		bodytable = new PdfPTable(12);
		bodytable.getDefaultCell().setPadding(0.0f);
	
		bodytable.setWidthPercentage(100);
		bodytable.setWidths(new float[] { 1f, 1f, 4f, 1f, 1.3f, 2.2f, 1f, 1f, 1f, 1f, 1f, 1.2f });
		
		PdfPCell header_Cell138 = new PdfPCell(PdfStyle.createUltimateCellWithBorderWithDisableBorder(("Sl."), Element.ALIGN_CENTER, Element.ALIGN_TOP, 8, 12f, true, 1,1, false, false, true,false , false));
	
		header_Cell138.setBackgroundColor(BaseColor.GRAY);
		header_Cell138.setBorderWidth(0f);
		header_Cell138.setPadding(0f);
		bodytable.addCell(header_Cell138);

		
		PdfPCell header_Cell139 = new PdfPCell(PdfStyle.createUltimateCellWithBorderWithDisableBorder((" Item"), Element.ALIGN_CENTER, Element.ALIGN_TOP, 8, 12f, true, 1,1, false, false, false,true , true));
		header_Cell139.setBackgroundColor(BaseColor.GRAY);
		header_Cell139.setPadding(0f);
		header_Cell139.setBorderWidth(0f);
		bodytable.addCell(header_Cell139);


	
		PdfPCell header_Cell140 = new PdfPCell(PdfStyle.createUltimateCellWithBorderWithDisableBorder((" Item Description"), Element.ALIGN_LEFT, Element.ALIGN_LEFT, 8, 12f, true, 1,1, false, false, false,true , true));
		
		header_Cell140.setBackgroundColor(BaseColor.GRAY);
		header_Cell140.setPadding(0f);
		header_Cell140.setBorderWidth(0f);
		bodytable.addCell(header_Cell140);



		PdfPCell header_Cell141 = new PdfPCell(PdfStyle.createUltimateCellWithBorderWithDisableBorder(("Pack"), Element.ALIGN_CENTER, Element.ALIGN_TOP, 8, 12f, true, 1,1, false, false, false,true , true));
	
		header_Cell141.setBackgroundColor(BaseColor.GRAY);
		header_Cell141.setPadding(0f);
		header_Cell141.setBorderWidth(0f);
		bodytable.addCell(header_Cell141);


		
		PdfPCell header_Cell142 = new PdfPCell(PdfStyle.createUltimateCellWithBorderWithDisableBorder(("UOM"), Element.ALIGN_CENTER, Element.ALIGN_TOP, 8, 12f, true, 1,1, false, false, false,true , true));
		
		header_Cell142.setBackgroundColor(BaseColor.GRAY);
		header_Cell142.setPadding(0);
		bodytable.addCell(header_Cell142);

		PdfPCell header_Cell143 = new PdfPCell(PdfStyle.createUltimateCellWithBorderWithDisableBorder((" Batch No."), Element.ALIGN_LEFT, Element.ALIGN_LEFT, 8, 12f, true, 1,1, false, false, false,true , true));
		header_Cell143.setBackgroundColor(BaseColor.GRAY);
		header_Cell143.setPadding(0);
		bodytable.addCell(header_Cell143);


		PdfPCell header_Cell144 = new PdfPCell(PdfStyle.createUltimateCellWithBorderWithDisableBorder(("Free"), Element.ALIGN_CENTER, Element.ALIGN_TOP, 8, 12f, true, 1,1, false, false, false,true , true));
		header_Cell144.setBackgroundColor(BaseColor.GRAY);
		header_Cell144.setPadding(0);
		bodytable.addCell(header_Cell144);


		PdfPCell header_Cell145 = new PdfPCell(PdfStyle.createUltimateCellWithBorderWithDisableBorder(("Rate"), Element.ALIGN_CENTER, Element.ALIGN_TOP, 8, 12f, true, 1,1, false, false, false,true , true));
		header_Cell145.setBackgroundColor(BaseColor.GRAY);
		header_Cell145.setPadding(0);
		bodytable.addCell(header_Cell145);

		
		PdfPCell header_Cell146 = new PdfPCell(PdfStyle.createUltimateCellWithBorderWithDisableBorder(("IGST"), Element.ALIGN_CENTER, Element.ALIGN_TOP, 8, 12f, true, 1,1, false, false, false,true , true));
		header_Cell146.setBackgroundColor(BaseColor.GRAY);
		header_Cell146.setPadding(0);
		bodytable.addCell(header_Cell146);


		PdfPCell header_Cell147 = new PdfPCell(PdfStyle.createUltimateCellWithBorderWithDisableBorder(("CGST"), Element.ALIGN_CENTER, Element.ALIGN_TOP, 8, 12f, true, 1,1, false, false, false,true , true));
		header_Cell147.setBackgroundColor(BaseColor.GRAY);
		header_Cell147.setPadding(0);
		bodytable.addCell(header_Cell147);

		PdfPCell header_Cell148 = new PdfPCell(PdfStyle.createUltimateCellWithBorderWithDisableBorder(("SGST"), Element.ALIGN_CENTER, Element.ALIGN_TOP, 8, 12f, true, 1,1, false, false, false,true , true));
		header_Cell148.setBackgroundColor(BaseColor.GRAY);
		header_Cell148.setPadding(0);
		
		bodytable.addCell(header_Cell148);

	
	
		PdfPCell header_Cell149 = new PdfPCell(PdfStyle.createUltimateCellWithBorderWithDisableBorder(("Taxable"), Element.ALIGN_CENTER, Element.ALIGN_TOP, 8, 12f, true, 1,1, false, false, false,true , true));
		header_Cell149.setBackgroundColor(BaseColor.GRAY);
		header_Cell149.setPadding(0);
		bodytable.addCell(header_Cell149);

	
		PdfPCell header_Cell150 = new PdfPCell(PdfStyle.createUltimateCellWithBorderWithDisableBorder(("No."), Element.ALIGN_CENTER, Element.ALIGN_TOP, 8, 12f, true, 1,1, false, false, false,true , true));
		header_Cell150.setBackgroundColor(BaseColor.GRAY);
		header_Cell150.setPadding(0f);
		bodytable.addCell(header_Cell150);

		PdfPCell header_Cell151 = new PdfPCell(PdfStyle.createUltimateCellWithBorderWithDisableBorder(("code"), Element.ALIGN_CENTER, Element.ALIGN_TOP, 8, 12f, true, 1,1, false, false, false,true , true));
		header_Cell151.setBackgroundColor(BaseColor.GRAY);
		header_Cell151.setPadding(0);
		bodytable.addCell(header_Cell151);

		
		PdfPCell header_Cell152 = new PdfPCell(PdfStyle.createUltimateCellWithBorderWithDisableBorder((" HSN Code"), Element.ALIGN_LEFT, Element.ALIGN_LEFT, 8, 12f, true, 1,1, true, false, false,true , true));
		header_Cell152.setBackgroundColor(BaseColor.GRAY);
		header_Cell152.setPadding(0);
		bodytable.addCell(header_Cell152);

	
	
		
		PdfPCell header_Cell153 = new PdfPCell(PdfStyle.createUltimateCellWithBorderWithDisableBorder((""), Element.ALIGN_CENTER, Element.ALIGN_TOP, 8, 12f, true, 1,1, false, false, false,true , true));
		header_Cell153.setBackgroundColor(BaseColor.GRAY);
		header_Cell153.setPadding(0);
		bodytable.addCell(header_Cell153);

	
		PdfPCell header_Cell154 = new PdfPCell(PdfStyle.createUltimateCellWithBorderWithDisableBorder((""), Element.ALIGN_CENTER, Element.ALIGN_TOP, 8, 12f, true, 1,1, false, false, false,true , true));
		header_Cell154.setBackgroundColor(BaseColor.GRAY);
		header_Cell154.setPadding(0);
		bodytable.addCell(header_Cell154);

	

		PdfPCell header_Cell155 = new PdfPCell(PdfStyle.createUltimateCellWithBorderWithDisableBorder((" Expir Dt"), Element.ALIGN_LEFT, Element.ALIGN_LEFT, 8, 12f, true, 1,1, true, false, false,true , true));
		header_Cell155.setBackgroundColor(BaseColor.GRAY);
		header_Cell155.setPadding(0);
		bodytable.addCell(header_Cell155);

	
		PdfPCell header_Cell156 = new PdfPCell(PdfStyle.createUltimateCellWithBorderWithDisableBorder((""), Element.ALIGN_CENTER, Element.ALIGN_TOP, 8, 12f, true, 1,1, false, false, false,true , true));
		header_Cell156.setBackgroundColor(BaseColor.GRAY);
		header_Cell156.setPadding(0);
		bodytable.addCell(header_Cell156);

	
		PdfPCell header_Cell157 = new PdfPCell(PdfStyle.createUltimateCellWithBorderWithDisableBorder((""), Element.ALIGN_CENTER, Element.ALIGN_TOP, 8, 12f, true, 1,1, false, false, false,true , true));
		header_Cell157.setBackgroundColor(BaseColor.GRAY);
		header_Cell157.setPadding(0);
		bodytable.addCell(header_Cell157);


		PdfPCell header_Cell158 = new PdfPCell(PdfStyle.createUltimateCellWithBorderWithDisableBorder((""), Element.ALIGN_CENTER, Element.ALIGN_TOP, 8, 12f, true, 1,1, false, false, false,true , true));
		header_Cell158.setBackgroundColor(BaseColor.GRAY);
		header_Cell158.setPadding(0);
		bodytable.addCell(header_Cell158);

		
		PdfPCell header_Cell159 = new PdfPCell(PdfStyle.createUltimateCellWithBorderWithDisableBorder((""), Element.ALIGN_CENTER, Element.ALIGN_TOP, 8, 12f, true, 1,1, false, false, false,true , true));
		header_Cell159.setBackgroundColor(BaseColor.GRAY);
		header_Cell159.setPadding(0);
		bodytable.addCell(header_Cell159);
		
		PdfPCell header_Cell159_ = new PdfPCell(PdfStyle.createUltimateCellWithBorderWithDisableBorder((""), Element.ALIGN_CENTER, Element.ALIGN_TOP, 8, 12f, true, 1,1, false, false, false,true , true));
		header_Cell159_.setBackgroundColor(BaseColor.GRAY);
		header_Cell159_.setPadding(0);
		bodytable.addCell(header_Cell159_);

		PdfPCell header_Cell160 = new PdfPCell(PdfStyle.createUltimateCellWithBorderWithDisableBorder(("Amount"), Element.ALIGN_CENTER, Element.ALIGN_TOP, 8, 12f, true, 1,1, false, false, false,true , true));
		header_Cell160.setBackgroundColor(BaseColor.GRAY);
		header_Cell160.setPadding(0);
		bodytable.addCell(header_Cell160);



	
//		bodytable.addCell(PdfStyle.createLabelWithBGColour("Expir"));
//		bodytable.addCell(PdfStyle.createLabelWithBGColour("")).setRowspan(2);
//		bodytable.addCell(PdfStyle.createLabelWithBGColour("")).setRowspan(2);
//		bodytable.addCell(PdfStyle.createLabelWithBGColour("")).setRowspan(2);
//		bodytable.addCell(PdfStyle.createLabelWithBGColour("")).setRowspan(2);
//		bodytable.addCell(PdfStyle.createLabelWithBGColour("")).setRowspan(2);
//		bodytable.addCell(PdfStyle.createLabelWithBGColour("Amount"));

		PdfPCell hcell_blank4 = new PdfPCell(bodytable);
		hcell_blank4.setBorder(0);
	//	headertable03.getDefaultCell().setBorder(Rectangle.NO_BORDER);
		//headertable02.getDefaultCell().setBorder(Rectangle.NO_BORDER);
//		headertable01.getDefaultCell().setBorder(Rectangle.NO_BORDER);
		headertable001.addCell(hcell_blank4);
	//	headertable001.getDefaultCell().setBorder(Rectangle.NO_BORDER);
		
		// headertable001.addCell(bodytable);
		// 5th lines
		
		headertable.getDefaultCell().setPadding(0.0f);
		headertable01.getDefaultCell().setPadding(0.0f);
		headertable02.getDefaultCell().setPadding(0.0f);
		headertable03.getDefaultCell().setPadding(0.0f);
		headertable001.getDefaultCell().setPadding(0.0f);
		
		headertable.addCell(headertable01);
		headertable.addCell(headertable02);

		headertable.addCell(headertable03);
		headertable.addCell(headertable001);
//		headertable.addCell(headertable2);
//		headertable.addCell(headertable3);
//		headertable.addCell(headertable4);
//		headertable.addCell(headertable5);

		PdfPCell hcell2 = null;

		return headertable;
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
