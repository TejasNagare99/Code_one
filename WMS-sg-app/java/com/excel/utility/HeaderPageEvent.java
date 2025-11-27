package com.excel.utility;


import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Document;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.Rectangle;
import com.itextpdf.text.Font.FontFamily;
import com.itextpdf.text.pdf.ColumnText;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfPageEventHelper;
import com.itextpdf.text.pdf.PdfWriter;

public class HeaderPageEvent extends PdfPageEventHelper {
	protected PdfPTable header;

	int pageNum;
	private static Font font = new Font(FontFamily.HELVETICA, 8, Font.NORMAL,
			BaseColor.BLACK);

	public void setHeader(PdfPTable header) {
		this.header = header;
	}

	@Override
	public void onStartPage(PdfWriter writer, Document document) {
		pageNum++;
	};

	@Override
	public void onEndPage(PdfWriter writer, Document document) {
		try {

			Rectangle rect = writer.getBoxSize("header");
			ColumnText column = new ColumnText(writer.getDirectContent());
			column.addElement(header);
			column.setSimpleColumn(rect);
			column.go();

			// printing page no
			ColumnText.showTextAligned(
					writer.getDirectContent(),
					Element.ALIGN_RIGHT,
					new Phrase(String.format("Page No. %d",
							writer.getPageNumber()), font),
					(rect.getLeft() + rect.getRight()) / 2,
					rect.getBottom() - 18, 0);

		} catch (Exception e) {
			System.out.println("Error Occurred :" +e.getMessage());// uncomment asneeded --;
		}
	}
}
