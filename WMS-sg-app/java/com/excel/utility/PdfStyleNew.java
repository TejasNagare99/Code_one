package com.excel.utility;

import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Chunk;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.Font.FontFamily;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.Rectangle;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;

public class PdfStyleNew {

	public static PdfPCell createLabelCell(String text) {
		Font font = new Font(FontFamily.HELVETICA, 9, Font.BOLD, BaseColor.BLACK);
		PdfPCell cell = new PdfPCell(new Phrase(text, font));
		labelCellStyle(cell);
		return cell;
	}

	public static PdfPCell createLabelCellWithBGColor(String text, float grayFill) {
		Font font = new Font(FontFamily.HELVETICA, 9, Font.BOLD, BaseColor.BLACK);
		PdfPCell cell = new PdfPCell(new Phrase(text, font));
		labelCellStyle(cell);
		cell.setGrayFill(grayFill);
		return cell;
	}

	public static PdfPCell createLabelCellWithBGColorColspan2(String text, float grayFill) {
		Font font = new Font(FontFamily.HELVETICA, 9, Font.BOLD, BaseColor.BLACK);
		PdfPCell cell = new PdfPCell(new Phrase(text, font));
		labelCellStyle(cell);
		cell.setColspan(2);
		cell.setGrayFill(grayFill);
		return cell;
	}

	public static PdfPCell createLabelCellWithBGColorColspan3(String text, float grayFill) {
		Font font = new Font(FontFamily.HELVETICA, 9, Font.BOLD, BaseColor.BLACK);
		PdfPCell cell = new PdfPCell(new Phrase(text, font));
		labelCellStyle(cell);
		cell.setColspan(3);
		cell.setGrayFill(grayFill);
		return cell;
	}

	public static PdfPCell createLabelCellWithBGColorRowspan2(String text, float grayFill) {
		Font font = new Font(FontFamily.HELVETICA, 9, Font.BOLD, BaseColor.BLACK);
		PdfPCell cell = new PdfPCell(new Phrase(text, font));
		labelCellStyle(cell);
		cell.setRowspan(2);
		cell.setGrayFill(grayFill);
		return cell;
	}

	public static PdfPCell createLabelCellWithBGColorTopBorder(String text, float grayFill) {
		Font font = new Font(FontFamily.HELVETICA, 9, Font.BOLD, BaseColor.BLACK);
		PdfPCell cell = new PdfPCell(new Phrase(text, font));
		labelCellStyle(cell);
		cell.setGrayFill(grayFill);
		cell.setBorderWidthTop(0.5f);
		return cell;
	}

	public static PdfPCell createLabelCellWithBGColorNoBold(String text, float grayFill) {
		Font font = new Font(FontFamily.HELVETICA, 9, Font.NORMAL, BaseColor.BLACK);
		PdfPCell cell = new PdfPCell(new Phrase(text, font));
		labelCellStyle(cell);
		cell.setGrayFill(grayFill);
		return cell;
	}

	public static PdfPCell createLabelWithBGColour(String text) {
		Font font = new Font(FontFamily.HELVETICA, 9, Font.BOLD, BaseColor.BLACK);
		PdfPCell cell = new PdfPCell(new Phrase(text, font));
		cell.setHorizontalAlignment(Element.ALIGN_CENTER);
		cell.setVerticalAlignment(Element.ALIGN_CENTER);
		// cell.setBorder(0);
		cell.setBorderWidthRight(0.5f);
		cell.setMinimumHeight(18f);
		cell.setGrayFill(0.7f);
		cell.setNoWrap(false);
		return cell;
	}
	
	public static PdfPCell createLabelWithBGColourRowspan(String text) {
		Font font = new Font(FontFamily.HELVETICA, 9, Font.BOLD, BaseColor.BLACK);
		PdfPCell cell = new PdfPCell(new Phrase(text, font));
		cell.setHorizontalAlignment(Element.ALIGN_CENTER);
		cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
		// cell.setBorder(0);
		cell.setBorderWidthRight(0.5f);
		cell.setMinimumHeight(18f);
		cell.setGrayFill(0.7f);
		cell.setNoWrap(false);
		cell.setRowspan(2);
		return cell;
	}
	
	public static PdfPCell createLabelWithBGColourColspan(String text) {
		Font font = new Font(FontFamily.HELVETICA, 9, Font.BOLD, BaseColor.BLACK);
		PdfPCell cell = new PdfPCell(new Phrase(text, font));
		cell.setHorizontalAlignment(Element.ALIGN_CENTER);
		cell.setVerticalAlignment(Element.ALIGN_CENTER);
		// cell.setBorder(0);
		cell.setBorderWidthRight(0.5f);
		cell.setMinimumHeight(18f);
		cell.setGrayFill(0.7f);
		cell.setNoWrap(false);
		cell.setColspan(2);
		return cell;
	}

	public static PdfPCell createValueCell(String text, Boolean isNum, Boolean isDetailCell) {
		Font font = new Font(FontFamily.HELVETICA, 8, Font.NORMAL, BaseColor.BLACK);
		PdfPCell cell = new PdfPCell(new Phrase(text, font));
		valueCellStyle(cell, isNum, isDetailCell);
		return cell;
	}

	public static PdfPCell createLabelCellWithBorder(String text) {
		Font font = new Font(FontFamily.HELVETICA, 9, Font.BOLD, BaseColor.BLACK);
		PdfPCell cell = new PdfPCell(new Phrase(text, font));
		labelCellStyle(cell);
		cell.setBorder(1);
		return cell;
	}

	public static PdfPCell createLabelCellWithoutBorder(String text) {
		Font font = new Font(FontFamily.HELVETICA, 9, Font.BOLD, BaseColor.BLACK);
		PdfPCell cell = new PdfPCell(new Phrase(text, font));
		labelCellStyle(cell);
		cell.setBorder(0);
		return cell;
	}

	public static PdfPCell createLabelCellWithoutBorderSmallFont(String text) {
		Font font = new Font(FontFamily.HELVETICA, 8, Font.BOLD, BaseColor.BLACK);
		PdfPCell cell = new PdfPCell(new Phrase(text, font));
		labelCellStyle(cell);
		cell.setBorder(0);
		cell.setMinimumHeight(11f);
		return cell;
	}

	public static PdfPCell createLabelCellWithoutBorderSmallFontNoPad(String text) {
		Font font = new Font(FontFamily.HELVETICA, 8, Font.BOLD, BaseColor.BLACK);
		PdfPCell cell = new PdfPCell(new Phrase(text, font));
		cell.setMinimumHeight(9f);
		labelCellStyle(cell);
		cell.setBorder(0);
		cell.setMinimumHeight(0f);
		return cell;
	}

	public static PdfPCell createLabelCellWithoutBorderSmallFontNoBold(String text) {
		Font font = new Font(FontFamily.HELVETICA, 6, Font.NORMAL, BaseColor.BLACK);
		PdfPCell cell = new PdfPCell(new Phrase(text, font));
		labelCellStyle(cell);
		cell.setBorder(0);
		cell.setMinimumHeight(15f);
		return cell;
	}
	
	public static PdfPCell createLabelCellWithoutBorderMediumFontNoBold(String text) {
		Font font = new Font(FontFamily.HELVETICA, 8.5f, Font.NORMAL, BaseColor.BLACK);
		PdfPCell cell = new PdfPCell(new Phrase(text, font));
		labelCellStyle(cell);
		cell.setBorder(0);
		cell.setMinimumHeight(15f);
		return cell;
	}
	
	public static PdfPCell createLabelCellWithoutBorderSmallFontNoBoldForFooter(String text) {
		Font font = new Font(FontFamily.HELVETICA, 7, Font.NORMAL, BaseColor.BLACK);
		PdfPCell cell = new PdfPCell(new Phrase(text, font));
		labelCellStyle(cell);
		cell.setBorder(0);
		cell.setMinimumHeight(11f);
		return cell;
	}
	
	public static PdfPCell createLabelCellWithoutBorderTinyFontNoBoldForFooter(String text) {
		Font font = new Font(FontFamily.HELVETICA,6.2f, Font.NORMAL, BaseColor.BLACK);
		PdfPCell cell = new PdfPCell(new Phrase(text, font));
		labelCellStyle(cell);
		cell.setBorder(0);
		cell.setMinimumHeight(11f);
		return cell;
	}
	
	public static PdfPCell createLabelCellWithoutBorderSmallFontBoldForFooter(String text) {
		Font font = new Font(FontFamily.HELVETICA, 7, Font.BOLD, BaseColor.BLACK);
		PdfPCell cell = new PdfPCell(new Phrase(text, font));
		labelCellStyle(cell);
		cell.setBorder(0);
		cell.setMinimumHeight(11f);
		return cell;
	}
	
	
	public static PdfPCell createLabelCellWithoutBorderModerateFontNoBold(String text) {
		Font font = new Font(FontFamily.HELVETICA, 7, Font.NORMAL, BaseColor.BLACK);
		PdfPCell cell = new PdfPCell(new Phrase(text, font));
		labelCellStyle(cell);
		cell.setBorder(0);
		cell.setMinimumHeight(15f);
		return cell;
	}

	public static PdfPCell createLabelCellWithoutBorderSmallFontNoBoldNEW(String text) {
		Font font = new Font(FontFamily.HELVETICA, 6, Font.NORMAL, BaseColor.BLACK);
		PdfPCell cell = new PdfPCell(new Phrase(text, font));
		labelCellStyleNEW(cell);
		cell.setBorder(0);
		return cell;
	}

	public static PdfPCell createLabelCellWithoutBorderSmallFontNoBold2(String text) {
		Font font = new Font(FontFamily.HELVETICA, 7, Font.NORMAL, BaseColor.BLACK);
		PdfPCell cell = new PdfPCell(new Phrase(text, font));
		labelCellStyle(cell);
		cell.setBorder(0);
		cell.setMinimumHeight(25f);
		return cell;
	}

	public static PdfPCell createLabelCellWithoutBorderSmallFontNoBold2NEW(String text) {
		Font font = new Font(FontFamily.HELVETICA, 7, Font.NORMAL, BaseColor.BLACK);
		PdfPCell cell = new PdfPCell(new Phrase(text, font));
		labelCellStyleNEW(cell);
		cell.setBorder(0);
		return cell;
	}

	public static PdfPCell createLabelCellWithoutBorderSmallFontBold(String text) {
		Font font = new Font(FontFamily.HELVETICA, 8, Font.BOLD, BaseColor.BLACK);
		PdfPCell cell = new PdfPCell(new Phrase(text, font));
		labelCellStyle(cell);
		cell.setBorder(0);
		cell.setMinimumHeight(15f);
		return cell;
	}
	
	public static PdfPCell createLabelCellWithoutBorderSmallerFontBold(String text) {
		Font font = new Font(FontFamily.HELVETICA, 8, Font.BOLD, BaseColor.BLACK);
		PdfPCell cell = new PdfPCell(new Phrase(text, font));
		labelCellStyle(cell);
		cell.setBorder(0);
		cell.setMinimumHeight(10f);
		return cell;
	}

	public static PdfPCell createLabelCellWithBorderSmallFont(String text) {
		Font font = new Font(FontFamily.HELVETICA, 7, Font.BOLD, BaseColor.BLACK);
		PdfPCell cell = new PdfPCell(new Phrase(text, font));
		labelCellStyle(cell);
		cell.setMinimumHeight(15f);
		cell.setHorizontalAlignment(Element.ALIGN_LEFT);
		cell.setPaddingTop(0f);
		cell.setPaddingBottom(7f);
		return cell;
	}
	
	
	public static PdfPCell createLabelCellWithBorderSmallFont17Ht(String text) {
		Font font = new Font(FontFamily.HELVETICA, 7.5f, Font.BOLD, BaseColor.BLACK);
		PdfPCell cell = new PdfPCell(new Phrase(text, font));
		labelCellStyle(cell);
		cell.setMinimumHeight(17f);
		cell.setHorizontalAlignment(Element.ALIGN_LEFT);
		cell.setPaddingTop(0f);
		cell.setPaddingBottom(7f);
		return cell;
	}
	
	
	
	public static PdfPCell createLabelCellWithBorderSmallFont25Ht(String text) {
		Font font = new Font(FontFamily.HELVETICA, 7, Font.BOLD, BaseColor.BLACK);
		PdfPCell cell = new PdfPCell(new Phrase(text, font));
		labelCellStyle(cell);
		cell.setMinimumHeight(25f);
		cell.setHorizontalAlignment(Element.ALIGN_LEFT);
		return cell;
	}

	public static PdfPCell createLabelCellWithBorderTinyFont(String text) {
		Font font = new Font(FontFamily.HELVETICA, 6, Font.BOLD, BaseColor.BLACK);
		PdfPCell cell = new PdfPCell(new Phrase(text, font));
		labelCellStyle(cell);
		cell.setMinimumHeight(15f);
		cell.setHorizontalAlignment(Element.ALIGN_LEFT);
		cell.setPaddingTop(0f);
		cell.setPaddingBottom(7f);
		return cell;
	}
	
	public static PdfPCell createLabelCellWithBorderTinyFont17Ht(String text) {
		Font font = new Font(FontFamily.HELVETICA, 6, Font.BOLD, BaseColor.BLACK);
		PdfPCell cell = new PdfPCell(new Phrase(text, font));
		labelCellStyle(cell);
		cell.setMinimumHeight(17f);
		cell.setHorizontalAlignment(Element.ALIGN_LEFT);
		cell.setPaddingTop(0f);
		cell.setPaddingBottom(7f);
		return cell;
	}

	public static PdfPCell createLabelCellWithBorderSmallFont2(String text, String align) {
		Font font = new Font(FontFamily.HELVETICA, 7, Font.BOLD, BaseColor.BLACK);
		PdfPCell cell = new PdfPCell(new Phrase(text, font));
		labelCellStyle2(cell);
		cell.setMinimumHeight(10f);
		if (align.equalsIgnoreCase("left")) {
			cell.setHorizontalAlignment(Element.ALIGN_LEFT);
		} else if (align.equalsIgnoreCase("center")) {
			cell.setHorizontalAlignment(Element.ALIGN_CENTER);
		} else {
			cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
		}
		cell.setPaddingTop(0f);
		cell.setBorder(Rectangle.NO_BORDER);
		return cell;
	}

	public static PdfPCell createLabelCellWithBorderSmallFontTwiceHeight(String text) {
		Font font = new Font(FontFamily.HELVETICA, 8, Font.BOLD, BaseColor.BLACK);
		PdfPCell cell = new PdfPCell(new Phrase(text, font));
		labelCellStyle(cell);
		cell.setMinimumHeight(30f);
		cell.setHorizontalAlignment(Element.ALIGN_CENTER);
		cell.setPaddingTop(0f);
		cell.setPaddingBottom(7f);
		return cell;
	}

	public static PdfPCell createLabelCellWithBorderSmallFontNoBold(String text) {
		Font font = new Font(FontFamily.HELVETICA, 8, Font.NORMAL, BaseColor.BLACK);
		PdfPCell cell = new PdfPCell(new Phrase(text, font));
		labelCellStyle(cell);
		cell.setMinimumHeight(15f);
		cell.setHorizontalAlignment(Element.ALIGN_CENTER);
		cell.setPaddingTop(0f);
		cell.setPaddingBottom(7f);
		return cell;
	}
	
	public static PdfPCell createLabelCellWithBorderSmallFontNoBold17Ht(String text) {
		Font font = new Font(FontFamily.HELVETICA, 8, Font.NORMAL, BaseColor.BLACK);
		PdfPCell cell = new PdfPCell(new Phrase(text, font));
		labelCellStyle(cell);
		cell.setMinimumHeight(17f);
		cell.setHorizontalAlignment(Element.ALIGN_CENTER);
		cell.setPaddingTop(0f);
		cell.setPaddingBottom(7f);
		return cell;
	}
	
	public static PdfPCell createLabelCellWithBorderSmallerFontNoBold17Ht(String text) {
		Font font = new Font(FontFamily.HELVETICA, 7, Font.NORMAL, BaseColor.BLACK);
		PdfPCell cell = new PdfPCell(new Phrase(text, font));
		labelCellStyle(cell);
		cell.setMinimumHeight(17f);
		cell.setHorizontalAlignment(Element.ALIGN_CENTER);
		cell.setPaddingTop(0f);
		cell.setPaddingBottom(7f);
		return cell;
	}
	
	public static PdfPCell createLabelCellWithBorderTinyFontNoBold17Ht(String text) {
		Font font = new Font(FontFamily.HELVETICA, 6.5f, Font.NORMAL, BaseColor.BLACK);
		PdfPCell cell = new PdfPCell(new Phrase(text, font));
		labelCellStyle(cell);
		cell.setMinimumHeight(17f);
		cell.setHorizontalAlignment(Element.ALIGN_CENTER);
		cell.setPaddingTop(0f);
		cell.setPaddingBottom(7f);
		return cell;
	}
	
	public static PdfPCell createLabelCellWithBorderSmallFontNoBold16Ht(String text) {
		Font font = new Font(FontFamily.HELVETICA, 8, Font.NORMAL, BaseColor.BLACK);
		PdfPCell cell = new PdfPCell(new Phrase(text, font));
		labelCellStyle(cell);
		cell.setMinimumHeight(16f);
		cell.setHorizontalAlignment(Element.ALIGN_CENTER);
		cell.setPaddingTop(0f);
		cell.setPaddingBottom(7f);
		return cell;
	}

	public static PdfPCell createLabelCellWithBorderSmallFontNoBold7(String text) {
		Font font = new Font(FontFamily.HELVETICA, 7, Font.NORMAL, BaseColor.BLACK);
		PdfPCell cell = new PdfPCell(new Phrase(text, font));
		labelCellStyle(cell);
		cell.setMinimumHeight(15f);
		cell.setHorizontalAlignment(Element.ALIGN_CENTER);
		cell.setPaddingTop(0f);
		cell.setPaddingBottom(7f);
		return cell;
	}

	public static PdfPCell createLabelCellWithBorderSmallFontNoBold2(String text) {
		Font font = new Font(FontFamily.HELVETICA, 7, Font.NORMAL, BaseColor.BLACK);
		Paragraph p = new Paragraph(text, font);
		p.setAlignment(Paragraph.ALIGN_LEFT);
		PdfPCell cell = new PdfPCell();
		cell.addElement(p);
		// labelCellStyle(cell);
		cell.setHorizontalAlignment(Element.ALIGN_CENTER);

		return cell;
	}

	public static PdfPCell createLabelCellWithBorderSmallFontNoBolder(String text) {
		Font font = new Font(FontFamily.HELVETICA, 8, Font.BOLD, BaseColor.BLACK);
		Paragraph p = new Paragraph(text, font);
		p.setAlignment(Paragraph.ALIGN_LEFT);
		PdfPCell cell = new PdfPCell();
		cell.addElement(p);
		// labelCellStyle(cell);
		cell.setHorizontalAlignment(Element.ALIGN_CENTER);

		return cell;
	}

	public static PdfPCell formatCell(String text, boolean isBold, boolean disableAllBorders, boolean right,
			boolean left, boolean top, boolean bottom, String vAlign, String align) {
		Font font = new Font(FontFamily.HELVETICA, 8, Font.BOLD, BaseColor.BLACK);
		Font font2 = new Font(FontFamily.HELVETICA, 8, Font.NORMAL, BaseColor.BLACK);
		PdfPCell cell = new PdfPCell();
		Paragraph p = null;
		if (isBold) {
			p = new Paragraph(text, font);
		} else {
			p = new Paragraph(text, font2);
		}
		cell.addElement(p);
		if (disableAllBorders) {
			cell.setBorder(Rectangle.NO_BORDER);
		} else {
			if (right)
				cell.disableBorderSide(Rectangle.RIGHT);
			if (left)
				cell.disableBorderSide(Rectangle.LEFT);
			if (top)
				cell.disableBorderSide(Rectangle.TOP);
			if (bottom)
				cell.disableBorderSide(Rectangle.BOTTOM);
		}
		if (align.equalsIgnoreCase("left")) {
			cell.setHorizontalAlignment(Element.ALIGN_LEFT);
			p.setAlignment(Paragraph.ALIGN_LEFT);
		} else if (align.equalsIgnoreCase("right")) {
			cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
			p.setAlignment(Paragraph.ALIGN_RIGHT);
		} else {
			cell.setHorizontalAlignment(Element.ALIGN_CENTER);
			p.setAlignment(Paragraph.ALIGN_CENTER);
		}
		if (align.equalsIgnoreCase("top")) {
			cell.setHorizontalAlignment(Element.ALIGN_TOP);
		} else if (align.equalsIgnoreCase("middle")) {
			cell.setHorizontalAlignment(Element.ALIGN_MIDDLE);
		} else {
			cell.setHorizontalAlignment(Element.ALIGN_BOTTOM);
		}
		return cell;

	}

	public static PdfPCell createLabelCellWithBorderSmallFontNoBoldTwiceHeight(String text) {
		Font font = new Font(FontFamily.HELVETICA, 8, Font.NORMAL, BaseColor.BLACK);
		PdfPCell cell = new PdfPCell(new Phrase(text, font));
		labelCellStyle(cell);
		cell.setMinimumHeight(30f);
		cell.setHorizontalAlignment(Element.ALIGN_CENTER);
		cell.setPaddingTop(0f);
		cell.setPaddingBottom(7f);
		return cell;
	}
	
	public static PdfPCell createLabelCellWithBorderTinyFontNoBoldTwiceHeight(String text) {
		Font font = new Font(FontFamily.HELVETICA, 6, Font.NORMAL, BaseColor.BLACK);
		PdfPCell cell = new PdfPCell(new Phrase(text, font));
		labelCellStyle(cell);
		cell.setMinimumHeight(30f);
		cell.setHorizontalAlignment(Element.ALIGN_CENTER);
		cell.setPaddingTop(0f);
		cell.setPaddingBottom(7f);
		return cell;
	}

	public static PdfPCell createLabelCellWithBorderSmallFontNoBoldForHeader(String text) {
		Font font = new Font(FontFamily.HELVETICA, 8, Font.NORMAL, BaseColor.BLACK);
		PdfPCell cell = new PdfPCell(new Phrase(text, font));
		labelCellStyle(cell);
		cell.setMinimumHeight(9f);
		cell.setHorizontalAlignment(Element.ALIGN_CENTER);
		cell.setPaddingTop(0f);
		cell.setPaddingBottom(0f);
		return cell;
	}
	
	public static PdfPCell createLabelCellWithBorderTinierFontNoBoldForHeader(String text) {
		Font font = new Font(FontFamily.HELVETICA, 7, Font.NORMAL, BaseColor.BLACK);
		PdfPCell cell = new PdfPCell(new Phrase(text, font));
		labelCellStyle(cell);
		cell.setMinimumHeight(9f);
		cell.setHorizontalAlignment(Element.ALIGN_CENTER);
		cell.setPaddingTop(0f);
		cell.setPaddingBottom(0f);
		return cell;
	}
	

	public static PdfPCell createLabelCellWithBorderTinyFontNoBoldForHeader(String text) {
		Font font = new Font(FontFamily.HELVETICA, 6, Font.NORMAL, BaseColor.BLACK);
		PdfPCell cell = new PdfPCell(new Phrase(text, font));
		labelCellStyle(cell);
		cell.setMinimumHeight(9f);
		cell.setHorizontalAlignment(Element.ALIGN_CENTER);
		cell.setPaddingTop(0f);
		cell.setPaddingBottom(0f);
		return cell;
	}

	public static PdfPCell createLabelCellWithBorderLargeFontNoBold(String text) {
		Font font = new Font(FontFamily.HELVETICA, 12, Font.NORMAL, BaseColor.BLACK);
		PdfPCell cell = new PdfPCell(new Phrase(text, font));
		labelCellStyle(cell);
		cell.setMinimumHeight(15f);
		cell.setHorizontalAlignment(Element.ALIGN_CENTER);
		cell.setPaddingTop(0f);
		cell.setPaddingBottom(7f);
		return cell;
	}

	public static PdfPCell createLabelCellLeftWithoutBorder(String text) {
		Font font = new Font(FontFamily.HELVETICA, 9, Font.BOLD, BaseColor.BLACK);
		PdfPCell cell = new PdfPCell(new Phrase(text, font));
		labelCellLeftStyle(cell);
		cell.setBorder(0);
		return cell;
	}

	public static PdfPCell createLabelCellLeftWithoutBorderNoBold(String text) {
		Font font = new Font(FontFamily.HELVETICA, 9, Font.NORMAL, BaseColor.BLACK);
		PdfPCell cell = new PdfPCell(new Phrase(text, font));
		labelCellLeftStyle(cell);
		cell.setBorder(0);
		return cell;
	}

	public static PdfPCell createLabelCellWithoutBorderWithHeightHallign(String text, Float ht, int allign) {
		Font font = new Font(FontFamily.HELVETICA, 9, Font.BOLD, BaseColor.BLACK);
		PdfPCell cell = new PdfPCell(new Phrase(text, font));
		cell.setBorder(0);
		/*
		 * if(allign==0) cell.setHorizontalAlignment(Element.ALIGN_LEFT); if(allign==1)
		 * cell.setHorizontalAlignment(Element.ALIGN_CENTER ); if(allign==2)
		 * cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
		 */
		cell.setHorizontalAlignment(allign);
		if (ht != null)
			cell.setMinimumHeight(ht);
		return cell;
	}

	public static PdfPCell createValueCellWithoutBorder(String text, Boolean isNum, Boolean isDetailCell) {
		Font font = new Font(FontFamily.HELVETICA, 11, Font.NORMAL, BaseColor.BLACK);
		PdfPCell cell = new PdfPCell(new Phrase(text, font));
		valueCellStyle(cell, isNum, false);
		cell.setBorder(0);
		return cell;
	}

	/*
	 * static PdfPCell createValueCellWithBorder(String text, Boolean isNum,Boolean
	 * isDetailCell) { Font font = new Font(FontFamily.HELVETICA, 9,
	 * Font.NORMAL,BaseColor.BLACK); PdfPCell cell = new PdfPCell(new Phrase(text,
	 * font)); valueCellStyle(cell, isNum,isDetailCell); cell.setBorder(1);
	 * cell.setBorderWidthRight(0.5f); cell.setBorderWidthLeft(0.5f);
	 * 
	 * return cell; }
	 */

	/**
	 *
	 * @since 12-08-2015
	 * @param text   Text to be entered
	 * @param fonti  Font size
	 * @param halign Horizontal Alignment eg. ELEMENT.ALIGN_TYPE
	 * @return return the cell with specified params
	 */
	public static PdfPCell createTextCell(String text, int fonti, int halign) {
		Font font = new Font(FontFamily.HELVETICA, fonti, Font.NORMAL, BaseColor.BLACK);
		PdfPCell cell = new PdfPCell(new Phrase(text, font));
		cell.setHorizontalAlignment(halign);
		cell.setPaddingTop(0f);
		cell.setBorder(0);
		cell.setMinimumHeight(18f);
		return cell;
	}

	// creates cell with only top border of 0.5f
	public static PdfPCell createSingleLine() {
		PdfPCell cell = new PdfPCell();
		cell.setPaddingTop(0f);
		cell.setBorder(1);
		// cell.setBorderWidthTop(0.5f);
		cell.setMinimumHeight(18f);

		return cell;
	}

	public static void headerCellStyle(PdfPCell cell) {
		cell.setHorizontalAlignment(Element.ALIGN_CENTER);
		// cell.setPaddingTop(0f);
		// cell.setPaddingBottom(7f);
		// cell.setBackgroundColor(new BaseColor(0,121,182));
		// cell.setBorder(0);
		// cell.setBorderWidthRight(0.5f);
		// cell.setBorderWidthBottom(1f);
	}

	public static void headerCellWithBorder(PdfPCell cell) {
		cell.setHorizontalAlignment(Element.ALIGN_CENTER);
		cell.setPaddingTop(0f);
		cell.setPaddingBottom(7f);
		// cell.setBackgroundColor(new BaseColor(0,121,182));
		// cell.setBorderWidthRight(0.5f);
		// cell.setBorderWidthBottom(1f);
	}

	public static void labelCellStyle(PdfPCell cell) {
		cell.setHorizontalAlignment(Element.ALIGN_CENTER);
		cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
		cell.setPaddingLeft(3f);
		cell.setPaddingTop(0f);
		cell.setMinimumHeight(20f);
	}

	public static void labelCellStyleNEW(PdfPCell cell) {
		cell.setHorizontalAlignment(Element.ALIGN_CENTER);
		cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
		cell.setPaddingLeft(3f);
		cell.setPaddingTop(0f);
	}

	public static void labelCellStyle2(PdfPCell cell) {
		cell.setHorizontalAlignment(Element.ALIGN_LEFT);
		cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
		cell.setPaddingLeft(3f);
		cell.setPaddingTop(0f);
		// cell.setMinimumHeight(20f);
	}

	public static void labelCellLeftStyle(PdfPCell cell) {
		cell.setHorizontalAlignment(Element.ALIGN_LEFT);
		cell.setVerticalAlignment(Element.ALIGN_LEFT);
		cell.setPaddingTop(0f);
		cell.setPaddingLeft(4.0f);
		cell.setPaddingRight(4.0f);
		cell.setPaddingBottom(4.0f);
		cell.setMinimumHeight(20f);
	}

	public static void valueCellStyle(PdfPCell cell, Boolean isNum, Boolean isDetailCell) {
		cell.setHorizontalAlignment(Element.ALIGN_LEFT);
		if (isNum)
			cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
		cell.setVerticalAlignment(Element.ALIGN_BOTTOM);
		cell.setPaddingTop(0f);
		cell.setPaddingLeft(2.0f);
		cell.setPaddingRight(2.0f);
		cell.setPaddingBottom(4.0f);
		// cell.setPaddingLeft(2.0f);
		// cell.setMinimumHeight(18f);
		if (isDetailCell) {
			cell.setBorder(0);
			cell.setBorderWidthLeft(0.5f);
			cell.setBorderWidthRight(0.5f);
		}
	}
	
	public static void valueCellStyleCenterAlign(PdfPCell cell, Boolean isNum, Boolean isDetailCell) {
		cell.setHorizontalAlignment(Element.ALIGN_CENTER);

		cell.setVerticalAlignment(Element.ALIGN_BOTTOM);
		cell.setPaddingTop(0f);
		cell.setPaddingLeft(2.0f);
		cell.setPaddingRight(2.0f);
		cell.setPaddingBottom(4.0f);
		// cell.setPaddingLeft(2.0f);
		// cell.setMinimumHeight(18f);
		if (isDetailCell) {
			cell.setBorder(0);
			cell.setBorderWidthLeft(0.5f);
			cell.setBorderWidthRight(0.5f);
		}
	}
	

	// do not delete or change this
	/**
	 * @author Krishna:05-10-2015
	 * @param text
	 * @param hAlign
	 * @param vAlign
	 * @param fonti
	 * @param minHeight
	 * @return
	 */
	public static PdfPCell createLabelCellL_A_NoBorder(String text, int hAlign, int vAlign, int fonti,
			float minHeight) {
		Font font = new Font(FontFamily.HELVETICA, fonti, Font.BOLD, BaseColor.BLACK);
		PdfPCell cell = new PdfPCell(new Phrase(text, font));
		cell.setHorizontalAlignment(hAlign);
		cell.setVerticalAlignment(vAlign);
		cell.setPaddingLeft(3f);
		cell.setPaddingTop(0f);
		cell.setMinimumHeight(minHeight);
		cell.setBorder(0);
		return cell;
	}

	// added by krishna:05-10-2015
	// cell with border
	// do not delete or change this
	public static PdfPCell createLabelCellL_A_Border(String text, int hAlign, int vAlign, int fonti, float minHeight) {
		Font font = new Font(FontFamily.HELVETICA, fonti, Font.BOLD, BaseColor.BLACK);
		PdfPCell cell = new PdfPCell(new Phrase(text, font));
		cell.setHorizontalAlignment(hAlign);
		cell.setVerticalAlignment(vAlign);
		cell.setMinimumHeight(minHeight);
		cell.setBorder(4);
		cell.setBorder(8);
		return cell;
	}

	public static PdfPCell createLabelCellTopBorder(String text) {
		PdfPCell cell = new PdfPCell(new Phrase(text, new Font(FontFamily.HELVETICA, 9, Font.NORMAL, BaseColor.BLACK)));
		cell.setPaddingTop(0f);
		cell.setBorder(1);
		cell.setMinimumHeight(18f);
		return cell;
	}

	/**
	 * @param text
	 * @param hAlign
	 * @param vAlign
	 * @param fonti
	 * @param minHeight
	 * @return
	 */
	static PdfPCell createUltimateCellWithoutBorder(String text, int hAlign, int vAlign, int fonti, float minHeight,
			boolean isBold) {
		Font font = new Font(FontFamily.TIMES_ROMAN, fonti, (isBold ? Font.BOLD : Font.NORMAL), BaseColor.BLACK);
		PdfPCell cell = new PdfPCell(new Phrase(text, font));
		cell.setHorizontalAlignment(hAlign);
		cell.setVerticalAlignment(vAlign);
		cell.setMinimumHeight(minHeight);
		cell.setBorder(0);
		return cell;
	}

	public static PdfPCell createValueCellWithBottomBorder(String text, Boolean isNum, Boolean isDetailCell) {
		Font font = new Font(FontFamily.HELVETICA, 9, Font.NORMAL, BaseColor.BLACK);
		PdfPCell cell = new PdfPCell(new Phrase(text, font));
		cell.setHorizontalAlignment(Element.ALIGN_LEFT);
		if (isNum)
			cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
		cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
		cell.setPaddingTop(0f);
		cell.setPaddingLeft(2.0f);
		cell.setPaddingRight(2.0f);
		cell.setPaddingBottom(4.0f);
		cell.setMinimumHeight(18f);
		cell.setBorder(0);
		cell.setBorderWidthBottom(0.5f);
		if (isDetailCell) {
			cell.setBorderWidthLeft(0.5f);
			cell.setBorderWidthRight(0.5f);
		}
		return cell;
	}

	public static PdfPCell createValueCellWithHeight(String text, Boolean isNum, Boolean isDetailCell, float ht) {
		Font font = new Font(FontFamily.HELVETICA, 8, Font.NORMAL, BaseColor.BLACK);
		PdfPCell cell = new PdfPCell(new Phrase(text, font));
		valueCellStyle(cell, isNum, isDetailCell);
		cell.setMinimumHeight(ht);
		cell.setPaddingRight(2.0f);
		return cell;

	}

	public static PdfPCell createValueCellWithHeight3(String text, Boolean isNum, Boolean isDetailCell, float ht,
			boolean right, boolean left, boolean isTable, PdfPTable table, boolean isBold) {
		Font font = null;
		if (isBold) {
			font = new Font(FontFamily.HELVETICA, 7, Font.BOLD, BaseColor.BLACK);
		} else {
			font = new Font(FontFamily.HELVETICA, 7, Font.NORMAL, BaseColor.BLACK);
		}
		PdfPCell cell = null;
		if (!isTable) {
			cell = new PdfPCell(new Phrase(text, font));
		} else {
			cell = new PdfPCell();
			cell.addElement(table);
			// cell.setFixedHeight(10f);
		}
		valueCellStyle(cell, isNum, isDetailCell);
		cell.setMinimumHeight(ht);
		if (right)
			cell.disableBorderSide(Rectangle.RIGHT);
		if (left)
			cell.disableBorderSide(Rectangle.LEFT);
		cell.setPaddingRight(2.0f);
		return cell;
	}
	
	
	
	public static PdfPCell createValueCellWithHeight3WithMiddleAlignment(String text, Boolean isNum, Boolean isDetailCell, float ht,
			boolean right, boolean left, boolean isTable, PdfPTable table, boolean isBold) {
		Font font = null;
		if (isBold) {
			font = new Font(FontFamily.HELVETICA, 7, Font.BOLD, BaseColor.BLACK);
		} else {
			font = new Font(FontFamily.HELVETICA, 7, Font.NORMAL, BaseColor.BLACK);
		}
		PdfPCell cell = null;
		if (!isTable) {
			cell = new PdfPCell(new Phrase(text, font));
		} else {
			cell = new PdfPCell();
			cell.addElement(table);
			// cell.setFixedHeight(10f);
		}
		valueCellStyleCenterAlign(cell, isNum, isDetailCell);
		cell.setMinimumHeight(ht);
		if (right)
			cell.disableBorderSide(Rectangle.RIGHT);
		if (left)
			cell.disableBorderSide(Rectangle.LEFT);
		cell.setPaddingRight(2.0f);
		return cell;
	}

	public static PdfPCell createValueCellWithHeight3TinyFont(String text, Boolean isNum, Boolean isDetailCell,
			float ht, boolean right, boolean left, boolean isTable, PdfPTable table, boolean isBold) {
		Font font = null;
		if (isBold) {
			font = new Font(FontFamily.HELVETICA, 6, Font.BOLD, BaseColor.BLACK);
		} else {
			font = new Font(FontFamily.HELVETICA, 6, Font.NORMAL, BaseColor.BLACK);
		}
		PdfPCell cell = null;
		if (!isTable) {
			cell = new PdfPCell(new Phrase(text, font));
		} else {
			cell = new PdfPCell();
			cell.addElement(table);
			// cell.setFixedHeight(10f);
		}

		valueCellStyle(cell, isNum, isDetailCell);
		cell.setMinimumHeight(ht);
		if (right)
			cell.disableBorderSide(Rectangle.RIGHT);
		if (left)
			cell.disableBorderSide(Rectangle.LEFT);
		cell.setPaddingRight(2.0f);
		return cell;
	}

	public static PdfPCell createForGst(Boolean isNum, Boolean isDetailCell, float ht, boolean right, boolean left,
			PdfPTable table, String prodName, String igstPer, String igstAmt ,boolean isCentral) {
		Font font = new Font(FontFamily.HELVETICA, 6, Font.NORMAL, BaseColor.BLACK);
		Font boldfont = new Font(FontFamily.HELVETICA, 6, Font.BOLD, BaseColor.BLACK);

		Phrase ph = new Phrase();
		if(!isCentral) {
			ph.add(new Chunk(prodName + " | ",font));
			ph.add(new Chunk("IGST%: ", boldfont));
			ph.add(new Chunk(igstPer + " ",font));
			ph.add(new Chunk("IGST Amt: ", boldfont));
			ph.add(new Chunk(igstAmt + " ",font));
		}
		else {
			ph.add(new Chunk(prodName + " | ",font));
			
			ph.add(new Chunk("CGST%: ", boldfont));
			ph.add(new Chunk(igstPer + " ",font));
			ph.add(new Chunk("CGST Amt: ", boldfont));
			ph.add(new Chunk(igstAmt + " ",font));
			
			ph.add(new Chunk(" , SGST%: ", boldfont));
			ph.add(new Chunk(igstPer + " ",font));
			ph.add(new Chunk("SGST Amt: ", boldfont));
			ph.add(new Chunk(igstAmt + " ",font));
		}
		
		

		PdfPCell cell = null;
		cell = new PdfPCell(ph);

		valueCellStyle(cell, isNum, isDetailCell);
		cell.setMinimumHeight(ht);
		if (right)
			cell.disableBorderSide(Rectangle.RIGHT);
		if (left)
			cell.disableBorderSide(Rectangle.LEFT);
		cell.setPaddingRight(2.0f);
		return cell;
	}

	public static PdfPCell createValueCellWithHeightGroupingTitle(String text, Boolean isNum, Boolean isDetailCell,
			float ht) {
		Font font = new Font(FontFamily.HELVETICA, 8, Font.NORMAL, BaseColor.BLACK);
		PdfPCell cell = new PdfPCell(new Phrase(text, font));
		valueCellStyle(cell, isNum, isDetailCell);
		cell.setMinimumHeight(ht);
		cell.setColspan(10);
		cell.setGrayFill(0.7f);
		cell.enableBorderSide(Rectangle.BOTTOM);
		cell.setHorizontalAlignment(Element.ALIGN_CENTER);
		cell.setNoWrap(false);
		return cell;
	}

	public static PdfPCell createValueCellWithHeightInDetail(String text, Boolean isNum, Boolean isDetailCell,
			float ht) {
		Font font = new Font(FontFamily.HELVETICA, 8, Font.NORMAL, BaseColor.BLACK);
		PdfPCell cell = new PdfPCell(new Phrase(text, font));
		valueCellStyle(cell, isNum, isDetailCell);
		cell.setMinimumHeight(ht);
		cell.setColspan(10);
		cell.enableBorderSide(Rectangle.BOTTOM);
		cell.setHorizontalAlignment(Element.ALIGN_CENTER);
		cell.setNoWrap(false);
		return cell;
	}

	public static PdfPCell createValueCellWithHeightBold(String text, Boolean isNum, Boolean isDetailCell, float ht) {
		Font font = new Font(FontFamily.HELVETICA, 8, Font.BOLD, BaseColor.BLACK);
		PdfPCell cell = new PdfPCell(new Phrase(text, font));
		valueCellStyle(cell, isNum, isDetailCell);
		cell.setMinimumHeight(ht);
		cell.setPaddingRight(2.0f);
		return cell;
	}

	public static PdfPCell createValueCellWithHeightBoldTopBorder(String text, Boolean isNum, Boolean isDetailCell,
			float ht) {
		Font font = new Font(FontFamily.HELVETICA, 8, Font.BOLD, BaseColor.BLACK);
		PdfPCell cell = new PdfPCell(new Phrase(text, font));
		valueCellStyle(cell, isNum, isDetailCell);
		cell.setMinimumHeight(ht);
		cell.setPaddingRight(2.0f);
		cell.setBorderWidthTop(0.5f);
		return cell;
	}

	public static PdfPCell createDatachangeCell(String text, int colspan, BaseColor bc) {
		Font font = new Font(FontFamily.HELVETICA, 9, Font.NORMAL, bc);
		PdfPCell cell = new PdfPCell(new Phrase(text, font));
		cell.setColspan(colspan);
		cell.setGrayFill(0.9f);
		cell.setMinimumHeight(18f);
		return cell;
	}

}
