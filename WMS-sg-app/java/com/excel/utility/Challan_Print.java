package com.excel.utility;



import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Date;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;
import java.text.DecimalFormat;

import javax.servlet.ServletContext;

import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Chunk;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.Font.FontFamily;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import com.itextpdf.text.pdf.draw.VerticalPositionMark;
import com.excel.model.PrePrintedDetailChallan_withgst;
import com.excel.model.ViewPrePrintedDetailChallan;
import com.itextpdf.text.BadElementException;
import com.itextpdf.text.ListItem;
import com.itextpdf.text.Section;

public class Challan_Print {
	int add = 0;

	public static void main(String args[]) {
		// new Challan_Print().callMain(2, 1, "PZ   1400002", "PZ   1400002",
		// "tcfa");
	}

	public String callMain(int division, int loc, String frm_challan,
			String to_challan, String dispatchType, String prodtype,String rep_type,List<ViewPrePrintedDetailChallan>challans,String Companyname) {
		FileOutputStream os = null;
		PrintStream ps = null;
		StringBuffer output = new StringBuffer();
		// String path= ServletActionContext.getRequest().getRealPath("");
		//ServletContext context = ServletActionContext.getRequest()
			//	.getServletContext();
		String path = MedicoConstants.REPORT_FILE_PATH;//context.getRealPath("/");
		System.out.println("path 11 : " + path);
		long timevar = new Date().getTime();
		String filename = "detail" + timevar + ".txt";
		File file1 = new File(path + "\\files\\");
		file1.mkdirs();
		File file = new File(path + "\\files\\" + filename);
		try {
			Challan_Print print = new Challan_Print();

			
			/*
			 * List<ViewPrePrintedDetailChallan> challans = new PrePrintedChallanDao()
			 * .getViewPrePrintedDetailChallan(division, loc, frm_challan, to_challan,
			 * dispatchType, prodtype,rep_type);
			 */
			Set<String> set = new LinkedHashSet<String>();
			for (ViewPrePrintedDetailChallan challan : challans) {
				set.add(challan.getDoc_no() + challan.getProduct_type());
			}

			boolean isnew = false;
			int capacity = 36;
			int page = 0;
			int countpage = 0;

			for (String num : set) {
				/* header */
				isnew = true;
				page++;
				ViewPrePrintedDetailChallan footer = null;
				int count = 0;
				BigDecimal total_amt = new BigDecimal(0);
				if (countpage != 0) {
					output.append(addLine(2));
				}
				countpage++;
				for (ViewPrePrintedDetailChallan challan : challans) {
					if (num.equals(challan.getDoc_no()
							+ challan.getProduct_type())) {
						// count++;
						if (isnew) {
							// page=0;
							footer = challan;
							print.createHeader(challan, output, page,
									dispatchType);
							isnew = false;
						}
						BigDecimal length = new BigDecimal(challan
								.getProduct_desc().length());
						BigDecimal j = length.divide(new BigDecimal(35 + add),
								RoundingMode.CEILING).setScale(0,
								RoundingMode.CEILING);

						for (int i = 0; i < j.intValue(); i++) {
							count++;
							// if(i==0){
							if (challan.getCode() != null) {
								if (challan.getCode().length() > 6 * i) {
									output.append(checkNullLength(challan
											.getCode().substring(6 * i), 6, 'L'));// 58
								} else {
									output.append(addspace(6));// 7
								}
								output.append(addspace(1));// 7
								// output.append(checkNullLength(challan.getCode(),
								// 6, 'L'));//6
							} else {
								output.append(addspace(7));// 7
							}

							/*
							 * }else{
							 * 
							 * }
							 */

							if (challan.getProduct_desc() != null) {
								if (challan.getProduct_desc().length() > (35 + add)
										* i) {
									output.append(checkNullLength(
											challan.getProduct_desc()
													.substring((35 + add) * i),
											35 + add, 'L'));// 58
								} else {
									output.append(addspace(35 + add));
								}
							} else {
								output.append(addspace(35 + add));
							}

							/*
							 * output.append(checkNullLength(
							 * challan.getProduct_desc().substring(35+add*i) ,
							 * 35+add, 'L'));//42
							 */
							output.append(addspace(1));// 43

							// System.out.println(challan.getBatch_no());
							if (challan.getBatch_no() != null) {
								if (challan.getBatch_no().length() > 15 * i) {
									output.append(checkNullLength(challan
											.getBatch_no().substring(15 * i),
											15, 'L'));// 58
								} else {
									output.append(addspace(15));
								}
							} else {
								output.append(addspace(15));
							}

							if (i == 0) {

								output.append(addspace(1));// 59
								output.append(challan.getMfg_yr() != null ? checkNullLength(
										challan.getMfg_yr().toString(), 5, 'L')
										: addspace(5));// 64
								output.append(addspace(1));// 65
								output.append(checkNullLength(
										challan.getExpiry_date(), 8, 'L'));// 73
								// output.append(addspace(1));
								/*
								 * output.append(challan.getCases() != null ?
								 * checkNullLength( challan.getCases()
								 * .setScale(0, RoundingMode.HALF_UP)
								 * .toString(), 7, 'L') : addspace(7));//80
								 */
								output.append(checkNullLength("", 7, 'L'));
								// output.append(addspace(1));
								output.append(challan.getTotal_qty() != null ? checkNullLength(
										challan.getTotal_qty()
												.setScale(0,
														RoundingMode.HALF_UP)
												.toString(), 8, 'R')
										: addspace(8));// 88
								output.append(addspace(1));// 89
								output.append(challan.getRate() != null ? checkNullLength(
										challan.getRate()
												.setScale(2,
														RoundingMode.HALF_UP)
												.toString(), 8, 'R')
										: addspace(8));// 97
								output.append(addspace(1));// 98
								output.append(challan.getValue() != null ? checkNullLength(
										challan.getValue()
												.setScale(2,
														RoundingMode.HALF_UP)
												.toString(), 9, 'R')
										: addspace(9));
								total_amt = total_amt.add(challan.getValue()
										.setScale(2, RoundingMode.HALF_UP));
							}

							output.append(addLine(1));
							// System.out.println(count - capacity);
							if (count - capacity > 0) {
								page++;
								output.append("\f");
								print.createHeader(challan, output, page,
										dispatchType);
								count = 0;
							}
						}
					}
				}
				// if (num.equals(footer.getDoc_no())) {
				try {
					// output.append(addspace(1));//59
					System.out.println(count);
					if (footer.getDsp_challan_msg() != null
							&& footer.getDsp_challan_msg().trim().length() > 0)
						output.append("Message: ");// 59
					output.append(checkNullLength(footer.getDsp_challan_msg(),
							90, 'L'));
					output.append(addLine(1));
					for (int i = 0; i < 64 - (19 + count + 1); i++) {
						output.append(addLine(1));
					}
					// output.append(addLine(1));
					System.out.println(capacity);
					System.out.println(count);
					System.out.println(capacity - count);
					output.append(addspace(21));
					/*
					 * output.append(footer.getTot_dsp_cases() != null ?
					 * checkNullLength( footer.getTot_dsp_cases() .setScale(0,
					 * RoundingMode.HALF_UP) .toString(), 9, 'L') :
					 * addspace(9));//30
					 */
					output.append(checkNullLength("", 9, 'L'));// 30
					output.append(addspace(1));// 31
					/*
					 * output.append(footer.getWeigh() != null ?
					 * checkNullLength( new BigDecimal(footer.getWeigh())
					 * .setScale(2, RoundingMode.HALF_UP) .toString(), 7, 'L') :
					 * addspace(7));//38
					 */
					output.append(addspace(7));// 38*/
					output.append(addspace(1));// 39
					output.append(addspace(8));// freight 47
					output.append(addspace(1));// 48
					output.append(checkNullLength(footer.getDsp_lorry_no(), 14,
							'L'));// 62
					output.append(addspace(1));// 63
					output.append(checkNullLength(footer.getDsp_transporter(),
							25, 'L'));// 88
					output.append(addspace(10));// 98
					output.append(checkNullLength(
							total_amt.setScale(2, RoundingMode.HALF_UP)
									.toString(), 9, 'R'));// 112

					output.append(addLine(3));

					output.append(addspace(21));// 35
					output.append(checkNullLength(footer.getPermit_no(), 8, 'L'));// 88
					output.append(addspace(1));

					String doc_no = footer.getShip_doc_no() != null ? footer
							.getShip_doc_no() : "";
					String doc_date = footer.getShip_doc_date() != null ? footer
							.getShip_doc_date() : "";
					output.append(checkNullLength(doc_no + " " + doc_date, 15,
							'L'));// 50
					total_amt = BigDecimal.ZERO;
					page = 0;
					// output.append("\n");

					output.append(addLine(4));
					// output.append(addLine(3));
				} catch (Exception e) {
					System.out.println("Error Occurred :" +e.getMessage());// uncomment asneeded --;
				}
				// }

			}

			// String pathName = "D:\\Challan\\detail.txt";
			/*
			 * File file = new File(pathName); file.mkdirs();
			 * file.createNewFile();
			 */

			file.getParentFile().mkdir();
			file.createNewFile();
			os = new FileOutputStream(file);
			ps = new PrintStream(os);

			ps.println(output);

		} catch (Exception e) {
			System.out.println("Error Occurred :" +e.getMessage());// uncomment asneeded --;
		} finally {
			try {
				if (os != null) {
					os.close();
				}
				if (ps != null) {
					ps.close();
				}
			} catch (IOException e) {
				System.out.println("Error Occurred :" +e.getMessage());// uncomment asneeded --;
			}
		}
		return filename;
	}

	public void createHeader(ViewPrePrintedDetailChallan challan,
			StringBuffer output, int page, String dispatchType) {
		output.append(addLine(3));
		/* 4 line */

		output.append(addspace(54 + add));
		output.append(checkNullLength("", 3, 'L'));// 57
		output.append(addspace(1));// 58
		output.append(checkNullLength(challan.getDoc_no(), 10, 'L'));// 66
		output.append(addspace(1));// 67
		output.append(checkNullLength(challan.getDate(), 10, 'L'));// 76
		/* output.append(addspace(10)); */
		output.append(addspace(1));// 77
		output.append(page);// 78

		/* 6 line */
		output.append(addLine(1));
		output.append(addspace(2)); // 2
		output.append("Employee No.: ");
		output.append(checkNullLength(challan.getStaff_emp_no(), 50 + add, 'L'));// 52

		output.append(addLine(1));
		output.append(addspace(2)); // 2
		output.append("Team: ");
		output.append(checkNullLength(challan.getDiv_name(), 44 + add, 'L'));

		output.append(addLine(1));
		output.append(addspace(2)); // 2
		output.append("Territory/HQ Code: ");
		output.append(checkNullLength(challan.getHq_code(), 31 + add, 'L'));

		/* 7 line */
		output.append(addLine(1));
		output.append(addspace(2)); // 2
		output.append(checkNullLength(challan.getStaff_name(), 50 + add, 'L'));// 52
																				// getStaff_name
		output.append(addspace(4));// 56
		output.append(checkNullLength(challan.getCfa_name(), 50 + add, 'L'));

		// //////////

		output.append(addLine(1));
		output.append(addspace(2)); // 2
		output.append(checkNullLength(challan.getStaff_addr1(), 50 + add, 'L')); // getStaff_addr1
		output.append(addspace(4));// 56
		output.append(checkNullLength(challan.getLoc_add1(), 50 + add, 'L'));

		/* 8 line */
		output.append(addLine(1));
		output.append(addspace(2)); // 2
		output.append(checkNullLength(challan.getStaff_addr2(), 50 + add, 'L')); // getStaff_addr2
		output.append(addspace(4));// 56
		output.append(checkNullLength(challan.getLoc_add2(), 50 + add, 'L'));

		/* 9 line */
		output.append(addLine(1));
		output.append(addspace(2)); // 2
		output.append(checkNullLength(challan.getStaff_addr3(), 50 + add, 'L')); // getStaff_addr3
		output.append(addspace(4));// 56
		output.append(checkNullLength(challan.getLoc_add3(), 50 + add, 'L'));

		/* 10 line */
		output.append(addLine(1));
		output.append(addspace(2)); // 2
		output.append(checkNullLength(challan.getStaff_addr4(), 50 + add, 'L')); // getStaff_addr4
		output.append(addspace(4));// 56
		output.append(checkNullLength(challan.getLoc_add4(), 50 + add, 'L'));

		output.append(addLine(1));
		output.append(addspace(2)); // 2
		output.append("Mobile: ");
		output.append(checkNullLength(challan.getDspstaff_mobile(), 42 + add,
				'L'));
		output.append(addspace(4));// 56
		output.append("Drug License 1 : ");
		output.append(checkNullLength(challan.getLic1(), 33 + add, 'L'));

		output.append(addLine(1));
		output.append(addspace(2)); // 2
		output.append("Email: ");
		output.append(checkNullLength(challan.getDspstaff_email(), 43 + add,
				'L'));
		output.append(addspace(4));// 56
		output.append("Drug License 2 : ");
		output.append(checkNullLength(challan.getLic2(), 33 + add, 'L'));

		output.append(addLine(1));
		output.append(addspace(2)); // 2
		output.append(addspace(50 + add));
		output.append(addspace(4));// 56
		output.append("CST No.: ");
		output.append(checkNullLength(challan.getLoc_cst_no(), 41 + add, 'L'));

		output.append(addLine(1));
		output.append(addspace(2)); // 2
		output.append(addspace(50 + add));
		output.append(addspace(4));// 56
		if (dispatchType.equalsIgnoreCase("tcfa")) {
			output.append("LST No.: ");
		} else {
			output.append("TIN No.: ");
		}
		output.append(checkNullLength(challan.getLoc_tin_no(), 42 + add, 'L'));
		if (dispatchType.equalsIgnoreCase("tcfa")) {
			output.append(addLine(1));
			output.append(addspace(2)); // 2
			output.append(addspace(50 + add));
			output.append(addspace(4));// 56
			output.append("PAN/CIN NO.:");
			output.append(checkNullLength(challan.getDptpan_no(), 42 + add, 'L'));
			output.append(addLine(2));
		} else {
			output.append(addLine(3));
		}

		/* 19 line */

		page = 0;
	}

	public static String checkNullLength(String inputStr, int setLen,
			char LRalign) {
		String addempty = "";
		String finalStr = "";
		if (inputStr != null) {
			int strLen = inputStr.length();
			finalStr = inputStr;
			if (strLen > setLen)
				finalStr = inputStr.substring(0, setLen);
			else if (strLen < setLen) {
				for (int i = 0; i < (setLen - strLen); i++)
					addempty = addempty + " ";
				if (LRalign == 'L')
					finalStr = inputStr + addempty;
				else
					finalStr = addempty + inputStr;
			}
		} else {
			for (int i = 0; i < setLen; i++)
				addempty = addempty + " ";

			finalStr = addempty;
		}

		return finalStr;
	}

	public static String addspace(int space) {
		StringBuffer buffer = new StringBuffer();
		for (int i = 0; i < space; i++) {
			buffer.append(" ");
		}
		return buffer.toString();
	}

	public static String addLine(int line) {
		StringBuffer buffer = new StringBuffer();
		for (int i = 0; i < line; i++) {
			buffer.append("\n");
		}
		return buffer.toString();
	}

	// ===============================================================PDF
	// PART==============================================================

	public String callMainForPdf(int division, int loc, String frm_challan,
			String to_challan, String dispatchType, String prodtype,List<ViewPrePrintedDetailChallan> challans,String Companyname) {
		FileOutputStream os = null;
		PrintStream ps = null;
		StringBuffer output = new StringBuffer();
		// String path= ServletActionContext.getRequest().getRealPath("");
		/*
		 * ServletContext context = ServletActionContext.getRequest()
		 * .getServletContext();
		 */
		String path = MedicoConstants.PDF_FILE_PATH;//context.getRealPath("/");
		System.out.println("path 11 : " + path);
		long timevar = new Date().getTime();
		String filename = "detail" + timevar + ".txt";
		String filename_pdf = "detail" + timevar + ".pdf";
		File file1 = new File(path + "\\files\\");
		file1.mkdirs();
		File file = new File(path + "\\files\\" + filename);
		try {
			Challan_Print print = new Challan_Print();

			
					
			// AMey pdf code call here
			Challan_Print printReport = new Challan_Print();

			// printReport.createPDF(path,filename_pdf);

			// vogella calls

			Document document = new Document();
			PdfWriter.getInstance(document, new FileOutputStream(path
					+ "\\files\\" + filename_pdf));

			document.open();

			String old="";
			String new_var="";
				/* header */
				for (ViewPrePrintedDetailChallan challan : challans) {
					new_var=challan.getDsp_id().toString();
						if (!old.equalsIgnoreCase(new_var)) {
							Paragraph preface = new Paragraph();
							addMetaData(document);
							//Context Commented
					
					  addTitlePage(document,challan,preface,path,Companyname);
					  addContent(document,challan,path,preface);
					 addFooterForPdf(document, challan,path,preface,Companyname);
					 
							 // Start a new page
						    document.newPage();
						}
						old=new_var;
				}
			
			document.close();

			file.getParentFile().mkdir();
			file.createNewFile();
			os = new FileOutputStream(file);
			ps = new PrintStream(os);

			ps.println(output);

		} catch (Exception e) {
			System.out.println("Error Occurred :" +e.getMessage());// uncomment asneeded --;
		} finally {
			try {
				if (os != null) {
					os.close();
				}
				if (ps != null) {
					ps.close();
				}
			} catch (IOException e) {
				System.out.println("Error Occurred :" +e.getMessage());// uncomment asneeded --;
			}
		}
		return filename_pdf;
	}

	// Source Code From : http://www.vogella.com/

	private static Font catFont = new Font(Font.FontFamily.TIMES_ROMAN, 18,
			Font.BOLD);
	private static Font redFont = new Font(Font.FontFamily.TIMES_ROMAN, 12,
			Font.NORMAL, BaseColor.RED);
	private static Font subFont = new Font(Font.FontFamily.TIMES_ROMAN, 16,
			Font.BOLD);
	private static Font smallBold = new Font(Font.FontFamily.TIMES_ROMAN, 12,
			Font.BOLD);

	private static Font font = new Font(FontFamily.HELVETICA, 12, Font.BOLD,
			BaseColor.BLACK);
	private static Font font_title = new Font(FontFamily.HELVETICA, 14, Font.BOLD,
			BaseColor.BLACK);
	private static Font medFont = new Font(Font.FontFamily.HELVETICA, 9,
			Font.NORMAL, BaseColor.BLACK);
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

	private static void addTitlePage(Document document,ViewPrePrintedDetailChallan challan,Paragraph preface,String path,String companyname)
			throws DocumentException {
		
		// We add one empty line
		// addEmptyLine(preface, 1,document);
		// Lets write a big header
		String company_name = companyname;//(String) context.getAttribute("company_name");

		
		preface=new Paragraph("Declaration", font_title);
		preface.setAlignment(Element.ALIGN_CENTER);
		
		document.add(preface);
		addEmptyLine(preface, 1,document);
		Chunk glue = new Chunk(new VerticalPositionMark());
		preface=new Paragraph(company_name , 
				font);
		preface.add(new Chunk(glue));
		 Chunk c1 = new Chunk("Date : "+challan.getDate(), medFont);
		preface.add(c1);
		
		document.add(preface);
		//addEmptyLine(preface, 1,document);
		
		preface=new Paragraph(challan.getLoc_add1() , 
				medFont);
		preface.setAlignment(Element.ALIGN_LEFT);
		document.add(preface);
		preface=new Paragraph(challan.getLoc_add2() , 
				medFont);
		preface.setAlignment(Element.ALIGN_LEFT);
		document.add(preface);
		preface=new Paragraph(challan.getLoc_add3() , 
				medFont);
		preface.setAlignment(Element.ALIGN_LEFT);
		document.add(preface);
		preface=new Paragraph(challan.getLoc_add4() , 
				medFont);
		preface.setAlignment(Element.ALIGN_LEFT);
		document.add(preface);
		preface=new Paragraph("Drug Licence No : "+challan.getLic1() , 
				medFont);
		preface.setAlignment(Element.ALIGN_LEFT);
		document.add(preface);
		addEmptyLine(preface, 1,document);
		
		
		// Will create: Report generated by: _name, _date
		preface=new Paragraph("TO WHOMSOEVER IT MAY CONCERN " , 
				medFont);
		preface.setAlignment(Element.ALIGN_CENTER);
		document.add(preface);
		addEmptyLine(preface, 1,document);
		StringBuilder sb = new StringBuilder();
		if(challan.getDsp_transporter().length()==0){
			sb.append("               contains :");	
		}else{
		
		sb.append(challan.getDsp_transporter()+" contains :");
		}
		preface=new Paragraph("We hereby certify that "+challan.getCases().setScale(0).toString()+" case/s dispatched by us through "
					+sb.toString()  , 
					medFont);
		 			
		preface.setAlignment(Element.ALIGN_LEFT);
		document.add(preface);		
//		
//		preface=new Paragraph(" contains :"  , 
//				medFont);
//	 			
//	preface.setAlignment(Element.ALIGN_LEFT);
//	document.add(preface);
	addEmptyLine(preface, 1,document);
		
		
		preface=new Paragraph("1. Free samples of harmless medicines intended for free distribution to doctors."  , 
				medFont);
		preface.setAlignment(Element.ALIGN_LEFT);
		document.add(preface);
		preface=new Paragraph("2. Publicity material(Printed Material) for free distribution to doctors."  , 
				medFont);
		preface.setAlignment(Element.ALIGN_LEFT);
		document.add(preface);
		addEmptyLine(preface, 1,document);
		
		
	}

	private static void addContent(Document document,ViewPrePrintedDetailChallan challan,String path,Paragraph preface) throws DocumentException {

		
 		 //specify column widths
		   float[] columnWidths = {1f, 2f};
		   //create PDF table with the given widths
		   PdfPTable table = new PdfPTable(columnWidths);
		   // set table width a percentage of the page width
		   table.setWidthPercentage(100);
		   table.setSpacingBefore(10f);

		   table.setSpacingAfter(10f);
		   		   
		   //just some random data to fill 
		  
		    insertCell(table, "Customer Name"  , Element.ALIGN_LEFT, 1, medFont);
		    StringBuilder sb1= new StringBuilder();
		    sb1.append(challan.getFstaff_id()+" ");
		    sb1.append(challan.getStaff_name()+"(");
		    sb1.append(challan.getDivision()+" ");
		    sb1.append(challan.getDiv_name()+") ");
		    //sb1.append(challan.getDiv_name()+")");
		    insertCell(table, sb1.toString().length()>50?sb1.toString().substring(0,50):sb1.toString(), Element.ALIGN_LEFT, 1, medFont);
		    
		    insertCell(table, "Ship To"  , Element.ALIGN_LEFT, 1, medFont);
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
		    
		    insertCell(table, "Headquater "  , Element.ALIGN_LEFT, 1, medFont);
		    
		    insertCell(table,challan.getHq_name(), Element.ALIGN_LEFT, 1, medFont);
		    
		    insertCell(table, "State Name "  , Element.ALIGN_LEFT, 1, medFont);
		    
		    insertCell(table, challan.getState(), Element.ALIGN_LEFT, 1, medFont);
		    
		    insertCell(table, "Mobile Number "  , Element.ALIGN_LEFT, 1, medFont);
		    
		    insertCell(table, challan.getDspstaff_mobile(), Element.ALIGN_LEFT, 1, medFont);
		    
		    insertCell(table, "The Material is "  , Element.ALIGN_LEFT, 1, medFont);
		    
		    insertCell(table, "NOT FOR SALE AND HAS NO COMMERCIAL VALUE", Element.ALIGN_LEFT, 1, medFont);
		    
		    insertCell(table, "Invoice # "  , Element.ALIGN_LEFT, 1, medFont);
		    
		    insertCell(table, challan.getDoc_no(), Element.ALIGN_LEFT, 1, medFont);
		    
		    insertCell(table, "L.R.No. :"  , Element.ALIGN_LEFT, 1, medFont);
		    
		    insertCell(table, challan.getShip_doc_no(), Element.ALIGN_LEFT, 1, medFont);
		    
		    insertCell(table, "No of Boxes "  , Element.ALIGN_LEFT, 1, medFont);
		    
		    insertCell(table, challan.getCases().setScale(0).toString(), Element.ALIGN_LEFT, 1, medFont);
		    
		    insertCell(table, "Weight "  , Element.ALIGN_LEFT, 1, medFont);
		    DecimalFormat format = new DecimalFormat("0.#");
		    insertCell(table, format.format(Double.parseDouble(challan.getWeigh())).toString() , Element.ALIGN_LEFT, 1, medFont);
		   
		    
		   // insertCell(table, "For "  , Element.ALIGN_LEFT, 1, medFont);
		    
		    //insertCell(table, company_name, Element.ALIGN_LEFT, 1, medFont);
		    
			
			  //create a new cell with the specified Text and Font
			  //PdfPCell cell = new PdfPCell(new Phrase("For " +company_name, medFont));
			  //set the cell alignment
			  //cell.setHorizontalAlignment(Element.ALIGN_LEFT);
			  //set the cell column span in case you want to merge two or more cells
			  //cell.setBorder(0);
			  
			  //cell.setPadding(5);
			  //table.addCell(cell);
		    
		    preface.add(table);
		    document.add(preface);

	}
	private static void addFooterForPdf(Document document,ViewPrePrintedDetailChallan challan,String path,Paragraph preface,String companyname)
			throws DocumentException {
		// We add one empty line
		addEmptyLine(preface, 1,document);
		// Lets write a big Footer
		
		
		String address1 = challan.getLoc_add1();
		String address2 = challan.getLoc_add2();
		String address3 = challan.getLoc_add3();
		String address4 = challan.getLoc_add4();
		String company_name = companyname;//(String) context.getAttribute("company_name");
		preface=new Paragraph("For "+company_name, font);
		preface.setAlignment(Element.ALIGN_LEFT);
		
		document.add(preface);
		addEmptyLine(preface, 4,document);
		
		
		
		
		preface=new Paragraph("Authorized Signatory", font);
		preface.setAlignment(Element.ALIGN_LEFT);
		
		document.add(preface);
		//addEmptyLine(preface, 1,document);
		preface=new Paragraph("Registered Office : ", font);
		preface.setAlignment(Element.ALIGN_LEFT);
		
		document.add(preface);
		
		
		preface=new Paragraph(address1 , 
				medFont);
		preface.setAlignment(Element.ALIGN_LEFT);
		document.add(preface);
		
		preface=new Paragraph(address2 , 
				medFont);
		preface.setAlignment(Element.ALIGN_LEFT);
		document.add(preface);
		
		preface=new Paragraph(address3 , 
				medFont);
		preface.setAlignment(Element.ALIGN_LEFT);
		document.add(preface);
		
		preface=new Paragraph(address4 , 
				medFont);
		preface.setAlignment(Element.ALIGN_LEFT);
		document.add(preface);
		
		
		
	}
	 static void createTable(Section subCatPart)
			throws BadElementException {
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
		com.itextpdf.text.List list = new com.itextpdf.text.List(true, false,
				10);
		list.add(new ListItem("First point"));
		list.add(new ListItem("Second point"));
		list.add(new ListItem("Third point"));
		subCatPart.add(list);
	}

	 static void addEmptyLine(Paragraph preface, int number,Document document) {
		 try {
		for (int i = 0; i < number; i++) {
 			preface=new Paragraph(" ", font);
			
				document.add(preface);
			
			}
		 	} catch (DocumentException e) {
				System.out.println("Error Occurred :" +e.getMessage());// uncomment asneeded --;
			}
	}
	
	 static void insertCell(PdfPTable table, String text, int align, int colspan, Font font){
		  
		  //create a new cell with the specified Text and Font
		  PdfPCell cell = new PdfPCell(new Phrase(text.trim(), font));
		  //set the cell alignment
		  cell.setHorizontalAlignment(align);
		  //set the cell column span in case you want to merge two or more cells
		  cell.setColspan(colspan);
		  cell.setPadding(5);
		  //in case there is no text and you wan to create an empty row
		  if(text.trim().equalsIgnoreCase("")){
		   cell.setMinimumHeight(10f);
		  }
		  //add the call to the table
		  table.addCell(cell);
		  
		 }

		// Left
		/*Paragraph paragraph = new Paragraph("This is right aligned text");
		paragraph.setAlignment(Element.ALIGN_RIGHT);
		document.add(paragraph);
		// Centered
		paragraph = new Paragraph("This is centered text");
		paragraph.setAlignment(Element.ALIGN_CENTER);
		document.add(paragraph);
		// Left
		paragraph = new Paragraph("This is left aligned text");
		paragraph.setAlignment(Element.ALIGN_LEFT);
		document.add(paragraph);
		// Left with indentation
		paragraph = new Paragraph("This is left aligned text with indentation");
		paragraph.setAlignment(Element.ALIGN_LEFT);
		paragraph.setIndentationLeft(50);
		document.add(paragraph);*/
}

