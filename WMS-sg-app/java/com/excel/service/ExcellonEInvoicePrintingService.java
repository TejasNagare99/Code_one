package com.excel.service;

import java.io.FileOutputStream;
import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.Date;
import java.util.List;

import org.springframework.stereotype.Service;

import com.excel.model.ViewPrePrintedSummaryChallan_GST_EInvoice;
import com.excel.model.ViewPrePrintedSummaryChallan_GST_EInvoiceStockist;
import com.excel.utility.MedicoConstants;
import com.excel.utility.MedicoResources;
import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.FontFactory;
import com.itextpdf.text.Image;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;

@Service
public class ExcellonEInvoicePrintingService {
	private static final DecimalFormat decfor = new DecimalFormat("0.00");
	BigDecimal goodstotal = BigDecimal.ZERO;
	BigDecimal goodst = BigDecimal.ZERO;
	BigDecimal sgsttotal = BigDecimal.ZERO;
	BigDecimal igsttotal = BigDecimal.ZERO;
	BigDecimal cgsttotal = BigDecimal.ZERO;

	// give changes here
	public String getPdf(List<ViewPrePrintedSummaryChallan_GST_EInvoiceStockist> list) throws Exception {
		String filename = "EWayBill_print" + new Date().getTime() + ".pdf";
		String path = MedicoConstants.PDF_FILE_PATH + "//files//" + filename;
//			String path = "C:\\\\Users\\\\Admin\\\\Desktop\\\\PDF_in_java\\\\helloPdf1" + new Date().getSeconds() + ".pdf";
		System.out.println(path);
		Document document = null;
		PdfPTable Ptable = new PdfPTable(1);
		PdfPTable table = new PdfPTable(1);
		PdfPTable table2 = new PdfPTable(3);
		PdfPTable table3 = new PdfPTable(3);
		PdfPTable table4 = new PdfPTable(13);
		PdfPTable table5 = new PdfPTable(2);
		PdfPCell space = null;

		try {
			Ptable.setWidthPercentage(100);
			float[] columnWidth = { 100f };
			Ptable.setWidths(columnWidth);

			Ptable.getDefaultCell().setBorderWidthTop(0);
			Ptable.getDefaultCell().setBorderWidthBottom(0);
			Ptable.getDefaultCell().setPadding(0);

			document = new Document(PageSize.A4, 15, 15, 6, 5);
			PdfWriter pdfWriter = PdfWriter.getInstance(document, new FileOutputStream(path));

			document.open();

			String old_ewaybill_no = "";
			String new_ewaybill_no = "";
			boolean addpage = true;

			ViewPrePrintedSummaryChallan_GST_EInvoiceStockist data1 = null;
			for (ViewPrePrintedSummaryChallan_GST_EInvoiceStockist data : list) {
				new_ewaybill_no = data.getDoc_no();

				if (!old_ewaybill_no.equalsIgnoreCase(new_ewaybill_no)) {

					if (addpage == false) {
						this.getFooter(Ptable, table5, data, goodstotal);
					}
					document.newPage();
					this.getTopHeader(Ptable, table2, table3, data);
					data1 = data;
				}
				// else {
				goodstotal = goodstotal.add(data.getValue());
				this.detail(Ptable, table3, data);
				// }
				old_ewaybill_no = new_ewaybill_no;
				addpage = false;

			}
			this.getFooter(Ptable, table5, data1, goodstotal);
			document.add(Ptable);
			document.close();

		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}

		return filename;
	}

	private void getTopHeader(PdfPTable Ptable, PdfPTable table2, PdfPTable table3,
			ViewPrePrintedSummaryChallan_GST_EInvoiceStockist list) {
		try {
			PdfPTable table = new PdfPTable(1);
			table.setWidthPercentage(100);
			float[] columnWidths = { 100f };
			table.setWidths(columnWidths);

			String qrCodeImagePath = MedicoConstants.QR_CODE_IMAGE_PATH;
			String filename = "E-Way-Bill" + new Date().getTime() + ".png";
			MedicoResources.generateImage(list.getTrn_ewaybill(), qrCodeImagePath, filename);
			String imFile = qrCodeImagePath + filename;
			Image image = Image.getInstance(imFile);

			PdfPCell imgCell = new PdfPCell(image, true);
			imgCell.setPadding(4f);
			imgCell.setRowspan(6);
			imgCell.setBorder(0);
			imgCell.setBorderWidthBottom(1f);
			imgCell.setFixedHeight(35f);
			imgCell.setHorizontalAlignment(Element.ALIGN_CENTER);
			imgCell.setVerticalAlignment(Element.ALIGN_MIDDLE);

			Font customFont0 = new Font(Font.FontFamily.TIMES_ROMAN, 15, Font.NORMAL);
			Paragraph paragraph = new Paragraph("E-Way Bill", customFont0);
			paragraph.setAlignment(Element.ALIGN_CENTER);

			PdfPCell titleCell = new PdfPCell(paragraph);
			titleCell.setHorizontalAlignment(Element.ALIGN_CENTER);
			titleCell.setBackgroundColor(new BaseColor(250, 247, 247));
			titleCell.setPaddingBottom(10);
			table.addCell(titleCell);

			table2 = new PdfPTable(7);
			table2.setWidthPercentage(100);
			float[] Widths = { 14.5f, 12f, 14.5f, 13.5f, 10f, 10.5f, 25f };
//				float[] Widths = { 10f,10f,10f,10f,10f};
			table2.setWidths(Widths);

			PdfPCell t2_1 = new PdfPCell(
					new Phrase("1. E-Way BILL Details", FontFactory.getFont(FontFactory.TIMES_ROMAN, 9, Font.BOLD)));
			t2_1.setBorder(0);
			t2_1.setPaddingBottom(10);
			t2_1.setColspan(6);
			table2.addCell(t2_1);

			t2_1 = new PdfPCell(imgCell);
			table2.addCell(t2_1);

			t2_1 = new PdfPCell(
					new Phrase("E-Way Bill No:", FontFactory.getFont(FontFactory.TIMES_ROMAN, 8, Font.BOLD)));
			t2_1.setBorder(0);
			t2_1.setHorizontalAlignment(Element.ALIGN_LEFT);
			table2.addCell(t2_1);

			t2_1 = new PdfPCell(
					new Phrase(list.getTrn_ewaybill(), FontFactory.getFont(FontFactory.TIMES_ROMAN, 8, Font.NORMAL)));
			t2_1.setHorizontalAlignment(Element.ALIGN_RIGHT);
			t2_1.setBorder(0);

			table2.addCell(t2_1);

			t2_1 = new PdfPCell(
					new Phrase("Generated Date", FontFactory.getFont(FontFactory.TIMES_ROMAN, 8, Font.BOLD)));
			t2_1.setBorder(0);
			t2_1.setPaddingLeft(5f);
			t2_1.setHorizontalAlignment(Element.ALIGN_LEFT);
			table2.addCell(t2_1);

			t2_1 = new PdfPCell(new Phrase(MedicoResources.convert_DD_MM_YYYY_toDateAndTime_(new Date().toString()),
					FontFactory.getFont(FontFactory.TIMES_ROMAN, 8, Font.NORMAL)));
			t2_1.setHorizontalAlignment(Element.ALIGN_RIGHT);
			t2_1.setBorder(0);
			table2.addCell(t2_1);

			t2_1 = new PdfPCell(new Phrase("Valid Upto", FontFactory.getFont(FontFactory.TIMES_ROMAN, 8, Font.BOLD)));
			t2_1.setHorizontalAlignment(Element.ALIGN_LEFT);
			t2_1.setBorder(0);
			t2_1.setPaddingLeft(5f);
			table2.addCell(t2_1);

			t2_1 = new PdfPCell(new Phrase(list.getE_waybill_valid_upto() != null
					? MedicoResources.getRptDateFormat__(list.getE_waybill_valid_upto())
					: "", FontFactory.getFont(FontFactory.TIMES_ROMAN, 8, Font.NORMAL)));
			t2_1.setHorizontalAlignment(Element.ALIGN_RIGHT);
			t2_1.setBorder(0);
			table2.addCell(t2_1);

//				t2_1= new PdfPCell(imgCell);
//				table2.addCell(t2_1);

//				t2_1 = new PdfPCell(new Phrase("", FontFactory.getFont(FontFactory.TIMES_ROMAN, 8, Font.NORMAL)));
//				t2_1.setBorder(0);
//				table2.addCell(t2_1);

			t2_1 = new PdfPCell(new Phrase("Mode", FontFactory.getFont(FontFactory.TIMES_ROMAN, 8, Font.BOLD)));
			t2_1.setBorder(0);
			t2_1.setHorizontalAlignment(Element.ALIGN_LEFT);
			table2.addCell(t2_1);

			t2_1 = new PdfPCell(new Phrase("Road", FontFactory.getFont(FontFactory.TIMES_ROMAN, 8, Font.NORMAL)));
			t2_1.setHorizontalAlignment(Element.ALIGN_RIGHT);
			t2_1.setBorder(0);
			t2_1.setColspan(1);
			table2.addCell(t2_1);

			t2_1 = new PdfPCell(new Phrase("Generated By", FontFactory.getFont(FontFactory.TIMES_ROMAN, 8, Font.BOLD)));
			t2_1.setBorder(0);
			t2_1.setPaddingLeft(5f);
			t2_1.setHorizontalAlignment(Element.ALIGN_LEFT);
			table2.addCell(t2_1);

			t2_1 = new PdfPCell(
					new Phrase(list.getLgst_reg_no(), FontFactory.getFont(FontFactory.TIMES_ROMAN, 8, Font.NORMAL)));
			t2_1.setHorizontalAlignment(Element.ALIGN_RIGHT);
			t2_1.setBorder(0);
			table2.addCell(t2_1);

			t2_1 = new PdfPCell(new Phrase("", FontFactory.getFont(FontFactory.TIMES_ROMAN, 8, Font.NORMAL)));
			t2_1.setBorder(0);
			t2_1.setColspan(2);
			table2.addCell(t2_1);

			t2_1 = new PdfPCell(
					new Phrase("Approx Distance", FontFactory.getFont(FontFactory.TIMES_ROMAN, 8, Font.BOLD)));
			t2_1.setBorder(0);
			t2_1.setHorizontalAlignment(Element.ALIGN_LEFT);
			table2.addCell(t2_1);

			t2_1 = new PdfPCell(new Phrase("", FontFactory.getFont(FontFactory.TIMES_ROMAN, 8, Font.NORMAL)));
			t2_1.setBorder(0);
			t2_1.setHorizontalAlignment(Element.ALIGN_RIGHT);
			table2.addCell(t2_1);

			t2_1 = new PdfPCell(
					new Phrase("Transaction Type", FontFactory.getFont(FontFactory.TIMES_ROMAN, 8, Font.BOLD)));
			t2_1.setBorder(0);
			t2_1.setPaddingLeft(5f);
			t2_1.setHorizontalAlignment(Element.ALIGN_LEFT);
			table2.addCell(t2_1);

			t2_1 = new PdfPCell(new Phrase("Regular", FontFactory.getFont(FontFactory.TIMES_ROMAN, 8, Font.NORMAL)));
			t2_1.setBorder(0);
			t2_1.setHorizontalAlignment(Element.ALIGN_RIGHT);
			table2.addCell(t2_1);

			t2_1 = new PdfPCell(new Phrase("Type", FontFactory.getFont(FontFactory.TIMES_ROMAN, 8, Font.BOLD)));
			t2_1.setBorder(0);
			t2_1.setPaddingLeft(5f);
			t2_1.setHorizontalAlignment(Element.ALIGN_LEFT);
			table2.addCell(t2_1);

			t2_1 = new PdfPCell(
					new Phrase("Outward Supply", FontFactory.getFont(FontFactory.TIMES_ROMAN, 8, Font.NORMAL)));
			t2_1.setBorder(0);
			t2_1.setHorizontalAlignment(Element.ALIGN_RIGHT);
			table2.addCell(t2_1);

//				t2_1 = new PdfPCell(new Phrase("", FontFactory.getFont(FontFactory.TIMES_ROMAN, 8, Font.NORMAL)));
//				t2_1.setBorder(0);
//				table2.addCell(t2_1);

			t2_1 = new PdfPCell(
					new Phrase("Document Details", FontFactory.getFont(FontFactory.TIMES_ROMAN, 8, Font.BOLD)));
			t2_1.setBorder(0);
			t2_1.setHorizontalAlignment(Element.ALIGN_LEFT);
			table2.addCell(t2_1);

			t2_1 = new PdfPCell(new Phrase(
					"TAX INVOICE -" + list.getDoc_no().replaceAll(" ", "") + "-\n"
							+ MedicoResources.getRptDateFormat(list.getDate()),
					FontFactory.getFont(FontFactory.TIMES_ROMAN, 8, Font.NORMAL)));
			t2_1.setBorder(0);
			t2_1.setNoWrap(false);
			t2_1.setHorizontalAlignment(Element.ALIGN_RIGHT);
			table2.addCell(t2_1);

			t2_1 = new PdfPCell(new Phrase("E-Invoice No", FontFactory.getFont(FontFactory.TIMES_ROMAN, 8, Font.BOLD)));
			t2_1.setBorder(0);
			t2_1.setPaddingLeft(5f);
			t2_1.setHorizontalAlignment(Element.ALIGN_TOP);
			t2_1.setHorizontalAlignment(Element.ALIGN_LEFT);
			table2.addCell(t2_1);

			t2_1 = new PdfPCell(new Phrase(list.getDoc_no().replaceAll(" ", ""),
					FontFactory.getFont(FontFactory.TIMES_ROMAN, 8, Font.NORMAL)));
			t2_1.setBorder(0);
			t2_1.setHorizontalAlignment(Element.ALIGN_TOP);
			t2_1.setHorizontalAlignment(Element.ALIGN_RIGHT);
			table2.addCell(t2_1);

			t2_1 = new PdfPCell(new Phrase("E-Inv Date:", FontFactory.getFont(FontFactory.TIMES_ROMAN, 8, Font.BOLD)));
			t2_1.setBorder(0);
			t2_1.setHorizontalAlignment(Element.ALIGN_LEFT);
			t2_1.setHorizontalAlignment(Element.ALIGN_TOP);
			t2_1.setPaddingLeft(5f);
			table2.addCell(t2_1);

			t2_1 = new PdfPCell(new Phrase(MedicoResources.getRptDateFormat__(list.getE_inv_gen_dt()),
					FontFactory.getFont(FontFactory.TIMES_ROMAN, 8, Font.NORMAL)));
			t2_1.setBorder(0);
			t2_1.setHorizontalAlignment(Element.ALIGN_RIGHT);
//				t2_1.setHorizontalAlignment(Element.ALIGN_TOP); not working
			table2.addCell(t2_1);

//				t2_1 = new PdfPCell(new Phrase("", FontFactory.getFont(FontFactory.TIMES_ROMAN, 8, Font.NORMAL)));
//				t2_1.setBorder(0);
//				table2.addCell(t2_1);

			t2_1 = new PdfPCell(new Phrase("IRN Number:", FontFactory.getFont(FontFactory.TIMES_ROMAN, 8, Font.BOLD)));
			t2_1.setBorder(0);
			t2_1.setBorderWidthLeft(0);
			t2_1.setBorderWidthTop(0);
			t2_1.setBorderWidthRight(0);
			t2_1.setBorderWidthBottom(1);
			t2_1.setPaddingBottom(13);
			table2.addCell(t2_1);

			t2_1 = new PdfPCell(
					new Phrase(list.getTrn_irn_number(), FontFactory.getFont(FontFactory.TIMES_ROMAN, 8, Font.NORMAL)));
			t2_1.setBorderWidthLeft(0);
			t2_1.setBorderWidthTop(0);
			t2_1.setBorderWidthRight(0);
			t2_1.setBorderWidthBottom(1);
			t2_1.setColspan(5);
			table2.addCell(t2_1);

			table3 = new PdfPTable(8);
			table3.setWidthPercentage(100);
//				float[] Width = { 20f, 30f, 12.5f, 12.5f, 12.5f, 12.5f };
//				float[] Width = { 15f, 40f, 12.5f, 9.5f, 17.5f, 11.5f };
			float[] Width = { 12.5f, 12.5f, 12.5f, 12.5f, 14f, 11f, 12.6f, 12.4f };
			table3.setWidths(Width);

			t2_1 = new PdfPCell(
					new Phrase("2. Address Details", FontFactory.getFont(FontFactory.TIMES_ROMAN, 8, Font.BOLD)));
			t2_1.setBorder(0);
			t2_1.setColspan(8);
			table3.addCell(t2_1);

			t2_1 = new PdfPCell(new Phrase("Billed From:", FontFactory.getFont(FontFactory.TIMES_ROMAN, 8, Font.BOLD)));
			t2_1.setBorder(0);
			t2_1.setColspan(4);
			table3.addCell(t2_1);

			t2_1 = new PdfPCell(new Phrase("Billed To:", FontFactory.getFont(FontFactory.TIMES_ROMAN, 8, Font.BOLD)));
			t2_1.setBorder(0);
			t2_1.setColspan(4);
			table3.addCell(t2_1);

			t2_1 = new PdfPCell(new Phrase("GSTIN :" + list.getLgst_reg_no(),
					FontFactory.getFont(FontFactory.TIMES_ROMAN, 8, Font.NORMAL)));
			t2_1.setBorder(0);
			t2_1.setColspan(4);
			table3.addCell(t2_1);

			t2_1 = new PdfPCell(new Phrase("GSTIN :" + list.getCfgst_reg_no(),
					FontFactory.getFont(FontFactory.TIMES_ROMAN, 8, Font.NORMAL)));
			t2_1.setBorder(0);
			t2_1.setColspan(4);
			table3.addCell(t2_1);

			t2_1 = new PdfPCell(
					new Phrase(list.getCfa_name(), FontFactory.getFont(FontFactory.TIMES_ROMAN, 8, Font.NORMAL)));
			t2_1.setBorder(0);
			t2_1.setColspan(4);
			table3.addCell(t2_1);

			t2_1 = new PdfPCell(
					new Phrase(list.getStaff_name(), FontFactory.getFont(FontFactory.TIMES_ROMAN, 8, Font.NORMAL)));
			t2_1.setBorder(0);
			t2_1.setColspan(4);
			t2_1.setPaddingBottom(2);
			table3.addCell(t2_1);

			t2_1 = new PdfPCell(new Phrase("Reference Invoice No. : " + list.getSrt_number(),
					FontFactory.getFont(FontFactory.TIMES_ROMAN, 8, Font.BOLD)));
			t2_1.setBorder(0);
			t2_1.setColspan(4);
			t2_1.setPaddingBottom(2);
			table3.addCell(t2_1);

			t2_1 = new PdfPCell(new Phrase("", FontFactory.getFont(FontFactory.TIMES_ROMAN, 8, Font.NORMAL)));
			t2_1.setBorder(0);
			t2_1.setColspan(4);
			t2_1.setPaddingBottom(2);
			table3.addCell(t2_1);

			t2_1 = new PdfPCell(
					new Phrase("Dispatched From:", FontFactory.getFont(FontFactory.TIMES_ROMAN, 8, Font.BOLD)));
			t2_1.setBorder(0);
			t2_1.setColspan(4);
			table3.addCell(t2_1);

			t2_1 = new PdfPCell(new Phrase("Ship To:", FontFactory.getFont(FontFactory.TIMES_ROMAN, 8, Font.BOLD)));
			t2_1.setBorder(0);
			t2_1.setColspan(4);
			table3.addCell(t2_1);

			t2_1 = new PdfPCell(new Phrase("GSTIN :" + list.getLgst_reg_no(),
					FontFactory.getFont(FontFactory.TIMES_ROMAN, 8, Font.NORMAL)));
			t2_1.setBorder(0);
			t2_1.setColspan(4);
			table3.addCell(t2_1);

			t2_1 = new PdfPCell(new Phrase("GSTIN :" + list.getCfgst_reg_no(),
					FontFactory.getFont(FontFactory.TIMES_ROMAN, 8, Font.NORMAL)));
			t2_1.setBorder(0);
			t2_1.setColspan(4);
			table3.addCell(t2_1);

			t2_1 = new PdfPCell(
					new Phrase(list.getCfa_name(), FontFactory.getFont(FontFactory.TIMES_ROMAN, 8, Font.NORMAL)));
			t2_1.setBorder(0);
			t2_1.setColspan(4);
			table3.addCell(t2_1);

			t2_1 = new PdfPCell(
					new Phrase(list.getStaff_name(), FontFactory.getFont(FontFactory.TIMES_ROMAN, 8, Font.NORMAL)));
			t2_1.setBorder(0);
			t2_1.setColspan(4);
			table3.addCell(t2_1);

			t2_1 = new PdfPCell(
					new Phrase(list.getLoc_add1(), FontFactory.getFont(FontFactory.TIMES_ROMAN, 8, Font.NORMAL)));
			t2_1.setBorder(0);
			t2_1.setColspan(4);
			table3.addCell(t2_1);

			t2_1 = new PdfPCell(
					new Phrase(list.getStaff_addr1(), FontFactory.getFont(FontFactory.TIMES_ROMAN, 8, Font.NORMAL)));
			t2_1.setBorder(0);
			t2_1.setColspan(4);
			table3.addCell(t2_1);

			t2_1 = new PdfPCell(
					new Phrase(list.getLoc_add2(), FontFactory.getFont(FontFactory.TIMES_ROMAN, 8, Font.NORMAL)));
			t2_1.setBorder(0);
			t2_1.setColspan(4);
			table3.addCell(t2_1);

			t2_1 = new PdfPCell(
					new Phrase(list.getStaff_addr2(), FontFactory.getFont(FontFactory.TIMES_ROMAN, 8, Font.NORMAL)));
			t2_1.setBorder(0);
			t2_1.setColspan(4);
			table3.addCell(t2_1);

			t2_1 = new PdfPCell(
					new Phrase(list.getLoc_add3(), FontFactory.getFont(FontFactory.TIMES_ROMAN, 8, Font.NORMAL)));
			t2_1.setBorder(0);
			t2_1.setColspan(4);
			table3.addCell(t2_1);

			t2_1 = new PdfPCell(
					new Phrase(list.getStaff_addr3(), FontFactory.getFont(FontFactory.TIMES_ROMAN, 8, Font.NORMAL)));
			t2_1.setBorder(0);
			t2_1.setColspan(4);
			table3.addCell(t2_1);

			t2_1 = new PdfPCell(
					new Phrase(list.getLoc_add4(), FontFactory.getFont(FontFactory.TIMES_ROMAN, 8, Font.NORMAL)));
			t2_1.setBorder(0);
			t2_1.setColspan(4);
			table3.addCell(t2_1);

			t2_1 = new PdfPCell(
					new Phrase(list.getStaff_addr4(), FontFactory.getFont(FontFactory.TIMES_ROMAN, 8, Font.NORMAL)));
			t2_1.setBorder(0);
			t2_1.setColspan(4);
			table3.addCell(t2_1);

			t2_1 = new PdfPCell(new Phrase("Telephone: " + list.getLoc_telephone_no(),
					FontFactory.getFont(FontFactory.TIMES_ROMAN, 8, Font.NORMAL)));
			t2_1.setBorder(0);
			t2_1.setColspan(4);
			t2_1.setPaddingBottom(5f);
			t2_1.setBorderWidthBottom(1);
			table3.addCell(t2_1);

			t2_1 = new PdfPCell(new Phrase("Telephone: " + list.getDpttel_no(),
					FontFactory.getFont(FontFactory.TIMES_ROMAN, 8, Font.NORMAL)));
			t2_1.setBorder(0);
			t2_1.setColspan(4);
			t2_1.setPaddingBottom(5f);
			t2_1.setBorderWidthBottom(1);
			table3.addCell(t2_1);

			t2_1 = new PdfPCell(
					new Phrase("3. Goods Details", FontFactory.getFont(FontFactory.TIMES_ROMAN, 8, Font.BOLD)));
			t2_1.setBorder(0);
			t2_1.setColspan(8);
			t2_1.setPaddingBottom(5f);
			table3.addCell(t2_1);

			t2_1 = new PdfPCell(new Phrase("HSN CODE", FontFactory.getFont(FontFactory.TIMES_ROMAN, 8, Font.BOLD)));
			t2_1.setRowspan(2);
			t2_1.setHorizontalAlignment(Element.ALIGN_CENTER);
			table3.addCell(t2_1);

			t2_1 = new PdfPCell(new Phrase("Product Name and Description",
					FontFactory.getFont(FontFactory.TIMES_ROMAN, 8, Font.BOLD)));
			t2_1.setRowspan(2);
			t2_1.setColspan(3);
			t2_1.setHorizontalAlignment(Element.ALIGN_CENTER);
			table3.addCell(t2_1);

			t2_1 = new PdfPCell(new Phrase("Quantity", FontFactory.getFont(FontFactory.TIMES_ROMAN, 8, Font.BOLD)));
			t2_1.setRowspan(2);
			t2_1.setHorizontalAlignment(Element.ALIGN_CENTER);
			table3.addCell(t2_1);

			t2_1 = new PdfPCell(new Phrase("Units", FontFactory.getFont(FontFactory.TIMES_ROMAN, 8, Font.BOLD)));
			t2_1.setRowspan(2);
			t2_1.setHorizontalAlignment(Element.ALIGN_CENTER);
			table3.addCell(t2_1);

			t2_1 = new PdfPCell(new Phrase("Tax Rate", FontFactory.getFont(FontFactory.TIMES_ROMAN, 8, Font.BOLD)));
			t2_1.setHorizontalAlignment(Element.ALIGN_CENTER);
			table3.addCell(t2_1);

			t2_1 = new PdfPCell(new Phrase("Taxable Amt", FontFactory.getFont(FontFactory.TIMES_ROMAN, 8, Font.BOLD)));
			t2_1.setRowspan(2);
			t2_1.setHorizontalAlignment(Element.ALIGN_CENTER);
			table3.addCell(t2_1);

			t2_1 = new PdfPCell(new Phrase("C+S+I+Cess", FontFactory.getFont(FontFactory.TIMES_ROMAN, 8, Font.BOLD)));
			t2_1.setHorizontalAlignment(Element.ALIGN_CENTER);
			table3.addCell(t2_1);

			Ptable.addCell(table);
			Ptable.addCell(table2);
			Ptable.addCell(table3);
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	private void getFooter(PdfPTable Ptable, PdfPTable table5, ViewPrePrintedSummaryChallan_GST_EInvoiceStockist list,
			BigDecimal goodstotal) throws DocumentException {

		BigDecimal taxTotal = list.getHcgst_bill_amt().add(list.getHsgst_bill_amt().add(list.getHigst_bill_amt()));
		table5 = new PdfPTable(8);
		table5.setWidthPercentage(100);
//			float[] columnWidths = { 13.5f, 15f, 11.5f, 14.4f, 16.5f, 12f, 17f, 13f };
		float[] columnWidths = { 13f, 12.5f, 12.5f, 12.5f, 14f, 10.5f, 13f, 12f };
		table5.setWidths(columnWidths);

		PdfPCell t2_1 = new PdfPCell(new Phrase("", FontFactory.getFont(FontFactory.TIMES_ROMAN, 8, Font.BOLD)));
		t2_1.setBorder(0);
		t2_1.setColspan(8);
		t2_1.setBorderWidthTop(0.1f);
		table5.addCell(t2_1);

		t2_1 = new PdfPCell(new Phrase("", FontFactory.getFont(FontFactory.TIMES_ROMAN, 8, Font.BOLD)));
		t2_1.setBorder(0);
		t2_1.setColspan(3);
		table5.addCell(t2_1);

		t2_1 = new PdfPCell(new Phrase("Total SGST", FontFactory.getFont(FontFactory.TIMES_ROMAN, 8, Font.NORMAL)));
		t2_1.setBorder(0);
		t2_1.setHorizontalAlignment(Element.ALIGN_LEFT);

		table5.addCell(t2_1);

		t2_1 = new PdfPCell(new Phrase(list.getHsgst_bill_amt().toString(),
				FontFactory.getFont(FontFactory.TIMES_ROMAN, 8, Font.BOLD)));
		t2_1.setBorder(0);
		t2_1.setHorizontalAlignment(Element.ALIGN_RIGHT);

		table5.addCell(t2_1);

		t2_1 = new PdfPCell(new Phrase("", FontFactory.getFont(FontFactory.TIMES_ROMAN, 8, Font.BOLD)));
		t2_1.setBorder(0);
		t2_1.setHorizontalAlignment(Element.ALIGN_RIGHT);

		table5.addCell(t2_1);

		t2_1 = new PdfPCell(
				new Phrase("Total Taxable Amt", FontFactory.getFont(FontFactory.TIMES_ROMAN, 7, Font.BOLD)));
		t2_1.setBorder(0);
		t2_1.setHorizontalAlignment(Element.ALIGN_LEFT);

		table5.addCell(t2_1);

		t2_1 = new PdfPCell(
				new Phrase(decfor.format(goodstotal), FontFactory.getFont(FontFactory.TIMES_ROMAN, 8, Font.BOLD)));
		t2_1.setBorder(0);
		t2_1.setHorizontalAlignment(Element.ALIGN_RIGHT);

		table5.addCell(t2_1);

		t2_1 = new PdfPCell(new Phrase("", FontFactory.getFont(FontFactory.TIMES_ROMAN, 8, Font.BOLD)));
		t2_1.setBorder(0);

		t2_1.setColspan(8);
		table5.addCell(t2_1);

		t2_1 = new PdfPCell(new Phrase("", FontFactory.getFont(FontFactory.TIMES_ROMAN, 8, Font.BOLD)));
		t2_1.setBorder(0);

		t2_1.setColspan(3);
		table5.addCell(t2_1);

		t2_1 = new PdfPCell(new Phrase("Total CGST", FontFactory.getFont(FontFactory.TIMES_ROMAN, 7, Font.NORMAL)));
		t2_1.setBorder(0);
		t2_1.setHorizontalAlignment(Element.ALIGN_LEFT);

		table5.addCell(t2_1);

		t2_1 = new PdfPCell(new Phrase(list.getHcgst_bill_amt().toPlainString(),
				FontFactory.getFont(FontFactory.TIMES_ROMAN, 8, Font.BOLD)));
		t2_1.setBorder(0);
		t2_1.setHorizontalAlignment(Element.ALIGN_RIGHT);

		table5.addCell(t2_1);

		t2_1 = new PdfPCell(new Phrase("", FontFactory.getFont(FontFactory.TIMES_ROMAN, 8, Font.BOLD)));
		t2_1.setBorder(0);
		t2_1.setHorizontalAlignment(Element.ALIGN_RIGHT);

		table5.addCell(t2_1);

		t2_1 = new PdfPCell(
				new Phrase("Total Goods Value", FontFactory.getFont(FontFactory.TIMES_ROMAN, 7, Font.BOLD)));
		t2_1.setBorder(0);
		t2_1.setHorizontalAlignment(Element.ALIGN_LEFT);

		table5.addCell(t2_1);

		t2_1 = new PdfPCell(
				new Phrase(decfor.format(goodstotal), FontFactory.getFont(FontFactory.TIMES_ROMAN, 8, Font.BOLD)));
		t2_1.setBorder(0);
		t2_1.setHorizontalAlignment(Element.ALIGN_RIGHT);

		table5.addCell(t2_1);

		t2_1 = new PdfPCell(new Phrase("", FontFactory.getFont(FontFactory.TIMES_ROMAN, 8, Font.BOLD)));
		t2_1.setBorder(0);

		t2_1.setColspan(8);
		table5.addCell(t2_1);

		t2_1 = new PdfPCell(new Phrase("", FontFactory.getFont(FontFactory.TIMES_ROMAN, 8, Font.BOLD)));
		t2_1.setBorder(0);

		t2_1.setColspan(3);
		table5.addCell(t2_1);

		t2_1 = new PdfPCell(new Phrase("Total IGST", FontFactory.getFont(FontFactory.TIMES_ROMAN, 7, Font.NORMAL)));
		t2_1.setBorder(0);
		t2_1.setHorizontalAlignment(Element.ALIGN_LEFT);

		table5.addCell(t2_1);

		t2_1 = new PdfPCell(new Phrase(list.getHigst_bill_amt().toPlainString(),
				FontFactory.getFont(FontFactory.TIMES_ROMAN, 8, Font.BOLD)));
		t2_1.setBorder(0);
		t2_1.setHorizontalAlignment(Element.ALIGN_RIGHT);

		table5.addCell(t2_1);

		t2_1 = new PdfPCell(new Phrase("", FontFactory.getFont(FontFactory.TIMES_ROMAN, 8, Font.BOLD)));
		t2_1.setBorder(0);
		t2_1.setHorizontalAlignment(Element.ALIGN_LEFT);

		table5.addCell(t2_1);

		t2_1 = new PdfPCell(new Phrase("Total GST Value", FontFactory.getFont(FontFactory.TIMES_ROMAN, 7, Font.BOLD)));
		t2_1.setBorder(0);
		t2_1.setHorizontalAlignment(Element.ALIGN_LEFT);

		table5.addCell(t2_1);

		t2_1 = new PdfPCell(
				new Phrase(decfor.format(taxTotal), FontFactory.getFont(FontFactory.TIMES_ROMAN, 8, Font.BOLD)));
		t2_1.setBorder(0);
		t2_1.setHorizontalAlignment(Element.ALIGN_RIGHT);

		table5.addCell(t2_1);

		t2_1 = new PdfPCell(new Phrase("", FontFactory.getFont(FontFactory.TIMES_ROMAN, 8, Font.BOLD)));
		t2_1.setBorder(0);

		t2_1.setColspan(8);
		table5.addCell(t2_1);

		t2_1 = new PdfPCell(new Phrase("", FontFactory.getFont(FontFactory.TIMES_ROMAN, 8, Font.BOLD)));
		t2_1.setBorder(0);

		t2_1.setColspan(3);
		table5.addCell(t2_1);

		t2_1 = new PdfPCell(new Phrase("Total Cess", FontFactory.getFont(FontFactory.TIMES_ROMAN, 7, Font.NORMAL)));
		t2_1.setBorder(0);
		t2_1.setHorizontalAlignment(Element.ALIGN_LEFT);
		table5.addCell(t2_1);

		t2_1 = new PdfPCell(new Phrase("₹ 0.00", FontFactory.getFont(FontFactory.TIMES_ROMAN, 8, Font.BOLD)));
		t2_1.setBorder(0);
		t2_1.setHorizontalAlignment(Element.ALIGN_RIGHT);
		table5.addCell(t2_1);

		t2_1 = new PdfPCell(new Phrase("", FontFactory.getFont(FontFactory.TIMES_ROMAN, 8, Font.BOLD)));
		t2_1.setBorder(0);
		t2_1.setHorizontalAlignment(Element.ALIGN_RIGHT);
		table5.addCell(t2_1);

		t2_1 = new PdfPCell(
				new Phrase("Total Other Charges", FontFactory.getFont(FontFactory.TIMES_ROMAN, 7, Font.BOLD)));
		t2_1.setBorder(0);
		t2_1.setHorizontalAlignment(Element.ALIGN_LEFT);
		table5.addCell(t2_1);

		t2_1 = new PdfPCell(new Phrase("₹ 0.00", FontFactory.getFont(FontFactory.TIMES_ROMAN, 8, Font.BOLD)));
		t2_1.setBorder(0);
		t2_1.setHorizontalAlignment(Element.ALIGN_RIGHT);
		table5.addCell(t2_1);

		t2_1 = new PdfPCell(new Phrase("", FontFactory.getFont(FontFactory.TIMES_ROMAN, 8, Font.BOLD)));
		t2_1.setColspan(6);
		t2_1.setBorder(0);
		table5.addCell(t2_1);

		t2_1 = new PdfPCell(new Phrase("Total Value", FontFactory.getFont(FontFactory.TIMES_ROMAN, 7, Font.BOLD)));
		t2_1.setHorizontalAlignment(Element.ALIGN_LEFT);
		table5.addCell(t2_1);

		t2_1 = new PdfPCell(new Phrase(decfor.format(goodstotal.add(taxTotal)),
				FontFactory.getFont(FontFactory.TIMES_ROMAN, 8, Font.BOLD)));
		t2_1.setHorizontalAlignment(Element.ALIGN_RIGHT);
		table5.addCell(t2_1);

		t2_1 = new PdfPCell(new Phrase(" ", FontFactory.getFont(FontFactory.TIMES_ROMAN, 8, Font.BOLD)));
		t2_1.setColspan(8);
		t2_1.setPaddingBottom(15);
		t2_1.setBorder(0);
		t2_1.setBorderWidthBottom(1);
		table5.addCell(t2_1);

		t2_1 = new PdfPCell(
				new Phrase("4. Transporter Details", FontFactory.getFont(FontFactory.TIMES_ROMAN, 8, Font.BOLD)));
		t2_1.setColspan(8);
		t2_1.setBorder(0);
		table5.addCell(t2_1);

		t2_1 = new PdfPCell(new Phrase("Transporter ID", FontFactory.getFont(FontFactory.TIMES_ROMAN, 8, Font.BOLD)));
		t2_1.setBorder(0);
		table5.addCell(t2_1);

		t2_1 = new PdfPCell(
				new Phrase(list.getTransporter_gstin(), FontFactory.getFont(FontFactory.TIMES_ROMAN, 8, Font.NORMAL)));
		t2_1.setBorder(0);
		t2_1.setHorizontalAlignment(Element.ALIGN_RIGHT);
		t2_1.setColspan(2);
		table5.addCell(t2_1);

		t2_1 = new PdfPCell(new Phrase("Transporter Name", FontFactory.getFont(FontFactory.TIMES_ROMAN, 8, Font.BOLD)));
		t2_1.setBorder(0);
		table5.addCell(t2_1);

		t2_1 = new PdfPCell(
				new Phrase(list.getDsp_transporter(), FontFactory.getFont(FontFactory.TIMES_ROMAN, 8, Font.NORMAL)));
		t2_1.setBorder(0);
		t2_1.setColspan(4);
		table5.addCell(t2_1);

		t2_1 = new PdfPCell(
				new Phrase("Transporter Doc No", FontFactory.getFont(FontFactory.TIMES_ROMAN, 8, Font.BOLD)));
		t2_1.setBorder(0);
		t2_1.setPaddingBottom(5f);
		t2_1.setBorderWidthBottom(1);
		table5.addCell(t2_1);

		t2_1 = new PdfPCell(new Phrase(list.getLr_no(), FontFactory.getFont(FontFactory.TIMES_ROMAN, 8, Font.NORMAL)));
		t2_1.setBorder(0);
		t2_1.setColspan(2);
		t2_1.setPaddingBottom(5f);
		t2_1.setHorizontalAlignment(Element.ALIGN_RIGHT);
		t2_1.setBorderWidthBottom(1);
		table5.addCell(t2_1);

		t2_1 = new PdfPCell(new Phrase("Transp Doc Date", FontFactory.getFont(FontFactory.TIMES_ROMAN, 8, Font.BOLD)));
		t2_1.setBorder(0);
		t2_1.setPaddingBottom(5f);
		t2_1.setBorderWidthBottom(1);
		table5.addCell(t2_1);

		t2_1 = new PdfPCell(new Phrase(MedicoResources.getRptDateFormat__(list.getLr_date()),
				FontFactory.getFont(FontFactory.TIMES_ROMAN, 8, Font.NORMAL)));
		t2_1.setBorder(0);
		t2_1.setColspan(4);
		t2_1.setPaddingBottom(5f);
		t2_1.setBorderWidthBottom(1);
		table5.addCell(t2_1);

		t2_1 = new PdfPCell(
				new Phrase("5. Vehicle Details", FontFactory.getFont(FontFactory.TIMES_ROMAN, 8, Font.BOLD)));
		t2_1.setColspan(8);
		t2_1.setBorder(0);
		t2_1.setPaddingBottom(5f);
		t2_1.setBorderWidthBottom(1);
		table5.addCell(t2_1);

		t2_1 = new PdfPCell(new Phrase("Mode", FontFactory.getFont(FontFactory.TIMES_ROMAN, 8, Font.BOLD)));
		table5.addCell(t2_1);

		t2_1 = new PdfPCell(new Phrase("Vehicle Number", FontFactory.getFont(FontFactory.TIMES_ROMAN, 8, Font.BOLD)));
		table5.addCell(t2_1);

		t2_1 = new PdfPCell(new Phrase("From City", FontFactory.getFont(FontFactory.TIMES_ROMAN, 8, Font.BOLD)));
		table5.addCell(t2_1);

		t2_1 = new PdfPCell(new Phrase("Entered Date", FontFactory.getFont(FontFactory.TIMES_ROMAN, 8, Font.BOLD)));
		table5.addCell(t2_1);

		t2_1 = new PdfPCell(new Phrase("Entered Bys", FontFactory.getFont(FontFactory.TIMES_ROMAN, 8, Font.BOLD)));
		table5.addCell(t2_1);

		t2_1 = new PdfPCell(new Phrase("CEWB Number", FontFactory.getFont(FontFactory.TIMES_ROMAN, 8, Font.BOLD)));
		t2_1.setHorizontalAlignment(Element.ALIGN_CENTER);
		t2_1.setColspan(3);
		table5.addCell(t2_1);

		t2_1 = new PdfPCell(new Phrase("Road", FontFactory.getFont(FontFactory.TIMES_ROMAN, 8, Font.NORMAL)));
		t2_1.setBorder(0);
		t2_1.setPaddingBottom(5f);
		t2_1.setBorderWidthBottom(1);
		table5.addCell(t2_1);

		t2_1 = new PdfPCell(
				new Phrase(list.getDsp_lorry_no(), FontFactory.getFont(FontFactory.TIMES_ROMAN, 8, Font.NORMAL)));
		t2_1.setBorder(0);
		t2_1.setPaddingBottom(5f);
		t2_1.setBorderWidthBottom(1);
		table5.addCell(t2_1);

		t2_1 = new PdfPCell(
				new Phrase(list.getSending_loc_city(), FontFactory.getFont(FontFactory.TIMES_ROMAN, 8, Font.NORMAL)));
		t2_1.setBorder(0);
		t2_1.setPaddingBottom(5f);
		t2_1.setBorderWidthBottom(1);
		table5.addCell(t2_1);

		t2_1 = new PdfPCell(new Phrase(MedicoResources.getRptDateFormat__(list.getLr_date()),
				FontFactory.getFont(FontFactory.TIMES_ROMAN, 8, Font.NORMAL)));
		t2_1.setBorder(0);
		t2_1.setPaddingBottom(5f);
		t2_1.setBorderWidthBottom(1);
		table5.addCell(t2_1);

		t2_1 = new PdfPCell(
				new Phrase(list.getLgst_reg_no(), FontFactory.getFont(FontFactory.TIMES_ROMAN, 8, Font.NORMAL)));
		t2_1.setBorder(0);
		t2_1.setPaddingBottom(5f);
		t2_1.setBorderWidthBottom(1);
		table5.addCell(t2_1);

		t2_1 = new PdfPCell(new Phrase("", FontFactory.getFont(FontFactory.TIMES_ROMAN, 8, Font.BOLD)));
		t2_1.setHorizontalAlignment(Element.ALIGN_CENTER);
		t2_1.setColspan(3);
		t2_1.setPaddingBottom(5f);
		t2_1.setBorder(0);
		t2_1.setBorderWidthBottom(1);
		table5.addCell(t2_1);

		Ptable.addCell(table5);

		t2_1 = new PdfPCell(new Phrase("Generated by: Medico SG from Excel Software and Systems Private Limited",
				FontFactory.getFont(FontFactory.TIMES_ROMAN, 6, Font.BOLD)));
		t2_1.setHorizontalAlignment(Element.ALIGN_LEFT);
		t2_1.setColspan(3);
		t2_1.setBorder(0);

		Ptable.addCell(t2_1);

	}

	private void detail(PdfPTable Ptable, PdfPTable table3, ViewPrePrintedSummaryChallan_GST_EInvoiceStockist list)
			throws DocumentException {

		table3 = new PdfPTable(8);
		table3.setWidthPercentage(100);
//			float[] Width = { 20f, 30f, 12.5f, 12.5f, 12.5f, 12.5f };
//			float[] Width = { 15f, 40f, 12.5f, 9.5f, 17.5f, 11.5f };
		float[] Width = { 12.5f, 12.5f, 12.5f, 12.5f, 14f, 11f, 12.5f, 12.5f };
		table3.setWidths(Width);

		PdfPCell t2_1 = new PdfPCell(
				new Phrase(list.getHsn_code(), FontFactory.getFont(FontFactory.TIMES_ROMAN, 8, Font.NORMAL)));
		t2_1.setRowspan(2);
		t2_1.setBorder(0);
		t2_1.setHorizontalAlignment(Element.ALIGN_CENTER);
		t2_1.setPaddingBottom(5f);
		table3.addCell(t2_1);

		t2_1 = new PdfPCell(new Phrase(
				list.getProduct_desc().length() > 45 ? list.getProduct_desc().substring(0, 45) : list.getProduct_desc(),
				FontFactory.getFont(FontFactory.TIMES_ROMAN, 7.5f, Font.NORMAL)));
		t2_1.setRowspan(2);
		t2_1.setColspan(3);
		t2_1.setBorder(0);
		t2_1.setHorizontalAlignment(Element.ALIGN_LEFT);
		t2_1.setPaddingBottom(5f);
		table3.addCell(t2_1);

		t2_1 = new PdfPCell(
				new Phrase(list.getTotal_qty(), FontFactory.getFont(FontFactory.TIMES_ROMAN, 8, Font.NORMAL)));
		t2_1.setRowspan(2);
		t2_1.setBorder(0);
		t2_1.setHorizontalAlignment(Element.ALIGN_RIGHT);
		t2_1.setPaddingBottom(5f);
		table3.addCell(t2_1);

		t2_1 = new PdfPCell(new Phrase("Box", FontFactory.getFont(FontFactory.TIMES_ROMAN, 8, Font.NORMAL)));
		t2_1.setRowspan(2);
		t2_1.setBorder(0);
		t2_1.setHorizontalAlignment(Element.ALIGN_CENTER);
		t2_1.setPaddingBottom(5f);
		table3.addCell(t2_1);

		t2_1 = new PdfPCell(new Phrase(
				list.getCgst_rate().stripTrailingZeros() + "+" + list.getSgst_rate().stripTrailingZeros() + "+"
						+ list.getIgst_rate().stripTrailingZeros() + "+0",
				FontFactory.getFont(FontFactory.TIMES_ROMAN, 8, Font.NORMAL)));
		t2_1.setHorizontalAlignment(Element.ALIGN_CENTER);
		t2_1.setRowspan(2);
		t2_1.setBorder(0);
//			t2_1.setBorderWidthRight(1);
		t2_1.setPaddingBottom(5f);
		table3.addCell(t2_1);

		t2_1 = new PdfPCell(new Phrase("₹ " + decfor.format(list.getValue()),
				FontFactory.getFont(FontFactory.TIMES_ROMAN, 8, Font.NORMAL)));
		t2_1.setRowspan(2);
		t2_1.setBorder(0);
		t2_1.setBorderWidthLeft(1);
		t2_1.setHorizontalAlignment(Element.ALIGN_RIGHT);
		t2_1.setPaddingBottom(5f);
		table3.addCell(t2_1);
		Ptable.addCell(table3);
	}

}
