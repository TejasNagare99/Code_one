package com.excel.utility;

import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.Font.FontFamily;
import com.itextpdf.text.FontFactory;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.Rectangle;
import com.itextpdf.text.pdf.PdfPCell;

public class PdfStyle {
	
	
	public static PdfPCell createValueCellWithBorder(String text,int fonti,int align,float height) {
		Font font = FontFactory.getFont(FontFactory.TIMES, fonti, Font.BOLD,
				BaseColor.BLACK);
		PdfPCell cell = new PdfPCell(new Phrase(text, font));
		cell.setHorizontalAlignment(align);
		cell.setFixedHeight(height);
		return cell;
	}
	
	public static PdfPCell createValueCellWithBorderwithColor(String text,int fonti,int align,float height,BaseColor color) {
		Font font = FontFactory.getFont(FontFactory.TIMES, fonti, Font.BOLD,
				BaseColor.BLACK);
		PdfPCell cell = new PdfPCell(new Phrase(text, font));
		cell.setHorizontalAlignment(align);
		cell.setFixedHeight(height);
		cell.setBackgroundColor(color);
		return cell;
	}
	
	public static PdfPCell createLabelCell(String text) {
		Font font = new Font(FontFamily.HELVETICA, 9, Font.BOLD,
				BaseColor.BLACK);
		PdfPCell cell = new PdfPCell(new Phrase(text, font));
		labelCellStyle(cell);
		return cell;
	}

	public static PdfPCell createLabelCellWithBGColor(String text, float grayFill) {
		Font font = new Font(FontFamily.HELVETICA, 9, Font.BOLD,
				BaseColor.BLACK);
		PdfPCell cell = new PdfPCell(new Phrase(text, font));
		labelCellStyle(cell);
		cell.setGrayFill(grayFill);
		return cell;
	}
	public static PdfPCell createLabelCellWithBGColorColspan2(String text, float grayFill) {
		Font font = new Font(FontFamily.HELVETICA, 9, Font.BOLD,
				BaseColor.BLACK);
		PdfPCell cell = new PdfPCell(new Phrase(text, font));
		labelCellStyle(cell);
		cell.setColspan(2);
		cell.setGrayFill(grayFill);
		return cell;
	}
	public static PdfPCell createLabelCellWithBGColorColspan3(String text, float grayFill) {
		Font font = new Font(FontFamily.HELVETICA, 9, Font.BOLD,
				BaseColor.BLACK);
		PdfPCell cell = new PdfPCell(new Phrase(text, font));
		labelCellStyle(cell);
		cell.setColspan(3);
		cell.setGrayFill(grayFill);
		return cell;
	}
	
	public static PdfPCell createLabelCellWithBGColorRowspan2(String text, float grayFill) {
		Font font = new Font(FontFamily.HELVETICA, 9, Font.BOLD,
				BaseColor.BLACK);
		PdfPCell cell = new PdfPCell(new Phrase(text, font));
		labelCellStyle(cell);
		cell.setRowspan(2);
		cell.setGrayFill(grayFill);
		return cell;
	}

	public static PdfPCell createLabelCellWithBGColorTopBorder(String text,
			float grayFill) {
		Font font = new Font(FontFamily.HELVETICA, 9, Font.BOLD,
				BaseColor.BLACK);
		PdfPCell cell = new PdfPCell(new Phrase(text, font));
		labelCellStyle(cell);
		cell.setGrayFill(grayFill);
		cell.setBorderWidthTop(0.5f);
		return cell;
	}

	public static PdfPCell createLabelCellWithBGColorNoBold(String text, float grayFill) {
		Font font = new Font(FontFamily.HELVETICA, 9, Font.NORMAL,
				BaseColor.BLACK);
		PdfPCell cell = new PdfPCell(new Phrase(text, font));
		labelCellStyle(cell);
		cell.setGrayFill(grayFill);
		return cell;
	}

	public static PdfPCell createLabelWithBGColour(String text) {
		Font font = new Font(FontFamily.HELVETICA, 9, Font.BOLD,
				BaseColor.BLACK);
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
	
	public static PdfPCell createLabelWithBGColourAndHeight(String text) {
		Font font = new Font(FontFamily.HELVETICA, 9, Font.BOLD,
				BaseColor.BLACK);
		PdfPCell cell = new PdfPCell(new Phrase(text, font));
		cell.setHorizontalAlignment(Element.ALIGN_CENTER);
		cell.setVerticalAlignment(Element.ALIGN_CENTER);
		// cell.setBorder(0);
		cell.setBorderWidthRight(0.5f);
		cell.setMinimumHeight(24f);
		cell.setGrayFill(0.7f);
		cell.setNoWrap(false);
		return cell;
	}

	public static PdfPCell createValueCell(String text, Boolean isNum,
			Boolean isDetailCell) {
		Font font = new Font(FontFamily.HELVETICA, 8, Font.NORMAL,
				BaseColor.BLACK);
		PdfPCell cell = new PdfPCell(new Phrase(text, font));
		valueCellStyle(cell, isNum, isDetailCell);
		return cell;
	}

	public static PdfPCell createLabelCellWithBorder(String text) {
		Font font = new Font(FontFamily.HELVETICA, 9, Font.BOLD,
				BaseColor.BLACK);
		PdfPCell cell = new PdfPCell(new Phrase(text, font));
		labelCellStyle(cell);
		cell.setBorder(1);
		return cell;
	}

	public static PdfPCell createLabelCellWithoutBorder(String text) {
		Font font = new Font(FontFamily.HELVETICA, 9, Font.BOLD,
				BaseColor.BLACK);
		PdfPCell cell = new PdfPCell(new Phrase(text, font));
		labelCellStyle(cell);
		cell.setBorder(0);
		return cell;
	}

	public static PdfPCell createLabelCellWithoutBorderSmallFont(String text) {
		Font font = new Font(FontFamily.HELVETICA, 8, Font.BOLD,
				BaseColor.BLACK);
		PdfPCell cell = new PdfPCell(new Phrase(text, font));
		labelCellStyle(cell);
		cell.setBorder(0);
		cell.setMinimumHeight(15f);
		return cell;
	}

	public static PdfPCell createLabelCellWithoutBorderSmallFontNoPad(String text) {
		Font font = new Font(FontFamily.HELVETICA, 8, Font.BOLD,
				BaseColor.BLACK);
		PdfPCell cell = new PdfPCell(new Phrase(text, font));
		cell.setMinimumHeight(9f);
		labelCellStyle(cell);
		cell.setBorder(0);
		cell.setMinimumHeight(0f);
		return cell;
	}

	public static PdfPCell createLabelCellWithoutBorderSmallFontNoBold(String text) {
		Font font = new Font(FontFamily.HELVETICA, 8, Font.NORMAL,
				BaseColor.BLACK);
		PdfPCell cell = new PdfPCell(new Phrase(text, font));
		labelCellStyle(cell);
		cell.setBorder(0);
		cell.setMinimumHeight(15f);
		return cell;
	}

	public static PdfPCell createLabelCellWithoutBorderSmallFontBold(String text) {
		Font font = new Font(FontFamily.HELVETICA, 8, Font.BOLD,
				BaseColor.BLACK);
		PdfPCell cell = new PdfPCell(new Phrase(text, font));
		labelCellStyle(cell);
		cell.setBorder(0);
		cell.setMinimumHeight(15f);
		return cell;
	}

	public static PdfPCell createLabelCellWithBorderSmallFont(String text) {
		Font font = new Font(FontFamily.HELVETICA, 8, Font.BOLD,
				BaseColor.BLACK);
		PdfPCell cell = new PdfPCell(new Phrase(text, font));
		labelCellStyle(cell);
		cell.setMinimumHeight(15f);
		cell.setHorizontalAlignment(Element.ALIGN_CENTER);
		cell.setPaddingTop(0f);
		cell.setPaddingBottom(7f);
		return cell;
	}

	public static PdfPCell createLabelCellWithBorderSmallFontTwiceHeight(String text) {
		Font font = new Font(FontFamily.HELVETICA, 8, Font.BOLD,
				BaseColor.BLACK);
		PdfPCell cell = new PdfPCell(new Phrase(text, font));
		labelCellStyle(cell);
		cell.setMinimumHeight(30f);
		cell.setHorizontalAlignment(Element.ALIGN_CENTER);
		cell.setPaddingTop(0f);
		cell.setPaddingBottom(7f);
		return cell;
	}

	public static PdfPCell createLabelCellWithBorderSmallFontNoBold(String text) {
		Font font = new Font(FontFamily.HELVETICA, 8, Font.NORMAL,
				BaseColor.BLACK);
		PdfPCell cell = new PdfPCell(new Phrase(text, font));
		labelCellStyle(cell);
		cell.setMinimumHeight(15f);
		cell.setHorizontalAlignment(Element.ALIGN_CENTER);
		cell.setPaddingTop(0f);
		cell.setPaddingBottom(7f);
		return cell;
	}

	public static PdfPCell createLabelCellWithBorderSmallFontNoBoldTwiceHeight(
			String text) {
		Font font = new Font(FontFamily.HELVETICA, 8, Font.NORMAL,
				BaseColor.BLACK);
		PdfPCell cell = new PdfPCell(new Phrase(text, font));
		labelCellStyle(cell);
		cell.setMinimumHeight(30f);
		cell.setHorizontalAlignment(Element.ALIGN_CENTER);
		cell.setPaddingTop(0f);
		cell.setPaddingBottom(7f);
		return cell;
	}

	public static PdfPCell createLabelCellWithBorderSmallFontNoBoldForHeader(
			String text) {
		Font font = new Font(FontFamily.HELVETICA, 8, Font.NORMAL,
				BaseColor.BLACK);
		PdfPCell cell = new PdfPCell(new Phrase(text, font));
		labelCellStyle(cell);
		cell.setMinimumHeight(9f);
		cell.setHorizontalAlignment(Element.ALIGN_CENTER);
		cell.setPaddingTop(0f);
		cell.setPaddingBottom(0f);
		return cell;
	}

	public static PdfPCell createLabelCellWithBorderLargeFontNoBold(String text) {
		Font font = new Font(FontFamily.HELVETICA, 12, Font.NORMAL,
				BaseColor.BLACK);
		PdfPCell cell = new PdfPCell(new Phrase(text, font));
		labelCellStyle(cell);
		cell.setMinimumHeight(15f);
		cell.setHorizontalAlignment(Element.ALIGN_CENTER);
		cell.setPaddingTop(0f);
		cell.setPaddingBottom(7f);
		return cell;
	}
	
	
	public static PdfPCell createLabelCellWithNoBorderLargeFontBoldReducedSizeAndReducedPadding(String text) {
		Font font = new Font(FontFamily.HELVETICA, 10, Font.NORMAL,
				BaseColor.BLACK);
		PdfPCell cell = new PdfPCell(new Phrase(text, font));
		labelCellStyle(cell);
		cell.setMinimumHeight(12f);
		cell.setHorizontalAlignment(Element.ALIGN_CENTER);
		cell.setPaddingTop(2f);
		cell.setPaddingBottom(3f);
		return cell;
	}
	
	
	public static PdfPCell createLabelCellWithBorderMediumFontBoldReducedSizeAndPadding(String text) {
		Font font = new Font(FontFamily.HELVETICA, 10, Font.BOLD,
				BaseColor.BLACK);
		PdfPCell cell = new PdfPCell(new Phrase(text, font));
		labelCellStyle(cell);
		cell.setMinimumHeight(10f);
		cell.setHorizontalAlignment(Element.ALIGN_CENTER);
		return cell;
	}

	public static PdfPCell createLabelCellLeftWithoutBorder(String text) {
		Font font = new Font(FontFamily.HELVETICA, 9, Font.BOLD,
				BaseColor.BLACK);
		PdfPCell cell = new PdfPCell(new Phrase(text, font));
		labelCellLeftStyle(cell);
		cell.setBorder(0);
		return cell;
	}

	public static PdfPCell createLabelCellLeftWithoutBorderNoBold(String text) {
		Font font = new Font(FontFamily.HELVETICA, 9, Font.NORMAL,
				BaseColor.BLACK);
		PdfPCell cell = new PdfPCell(new Phrase(text, font));
		labelCellLeftStyle(cell);
		cell.setBorder(0);
		return cell;
	}

	public static PdfPCell createLabelCellWithoutBorderWithHeightHallign(String text,
			Float ht, int allign) {
		Font font = new Font(FontFamily.HELVETICA, 9, Font.BOLD,
				BaseColor.BLACK);
		PdfPCell cell = new PdfPCell(new Phrase(text, font));
		cell.setBorder(0);
		/*
		 * if(allign==0) cell.setHorizontalAlignment(Element.ALIGN_LEFT);
		 * if(allign==1) cell.setHorizontalAlignment(Element.ALIGN_CENTER );
		 * if(allign==2) cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
		 */
		cell.setHorizontalAlignment(allign);
		if (ht != null)
			cell.setMinimumHeight(ht);
		return cell;
	}

	public static PdfPCell createValueCellWithoutBorder(String text, Boolean isNum,
			Boolean isDetailCell) {
		Font font = new Font(FontFamily.HELVETICA, 11, Font.NORMAL,
				BaseColor.BLACK);
		PdfPCell cell = new PdfPCell(new Phrase(text, font));
		valueCellStyle(cell, isNum, false);
		cell.setBorder(0);
		return cell;
	}

	/*
	 * static PdfPCell createValueCellWithBorder(String text, Boolean
	 * isNum,Boolean isDetailCell) { Font font = new Font(FontFamily.HELVETICA,
	 * 9, Font.NORMAL,BaseColor.BLACK); PdfPCell cell = new PdfPCell(new
	 * Phrase(text, font)); valueCellStyle(cell, isNum,isDetailCell);
	 * cell.setBorder(1); cell.setBorderWidthRight(0.5f);
	 * cell.setBorderWidthLeft(0.5f);
	 * 
	 * return cell; }
	 */

	/**
	 *
	 * @since 12-08-2015
	 * @param text
	 *            Text to be entered
	 * @param fonti
	 *            Font size
	 * @param halign
	 *            Horizontal Alignment eg. ELEMENT.ALIGN_TYPE
	 * @return return the cell with specified params
	 */
	public static PdfPCell createTextCell(String text, int fonti, int halign) {
		Font font = new Font(FontFamily.HELVETICA, fonti, Font.NORMAL,
				BaseColor.BLACK);
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

	public static void labelCellLeftStyle(PdfPCell cell) {
		cell.setHorizontalAlignment(Element.ALIGN_LEFT);
		cell.setVerticalAlignment(Element.ALIGN_LEFT);
		cell.setPaddingTop(0f);
		cell.setPaddingLeft(4.0f);
		cell.setPaddingRight(4.0f);
		cell.setPaddingBottom(4.0f);
		cell.setMinimumHeight(20f);
	}

	public static void valueCellStyle(PdfPCell cell, Boolean isNum,
			Boolean isDetailCell) {
		cell.setHorizontalAlignment(Element.ALIGN_LEFT);
		if (isNum)
			cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
		cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
		cell.setPaddingTop(0f);
		cell.setPaddingLeft(2.0f);
		cell.setPaddingRight(2.0f);
		cell.setPaddingBottom(4.0f);
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
	public static PdfPCell createLabelCellL_A_NoBorder(String text, int hAlign,
			int vAlign, int fonti, float minHeight) {
		Font font = new Font(FontFamily.HELVETICA, fonti, Font.BOLD,
				BaseColor.BLACK);
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
	public static PdfPCell createLabelCellL_A_Border(String text, int hAlign,
			int vAlign, int fonti, float minHeight) {
		Font font = new Font(FontFamily.HELVETICA, fonti, Font.BOLD,
				BaseColor.BLACK);
		PdfPCell cell = new PdfPCell(new Phrase(text, font));
		cell.setHorizontalAlignment(hAlign);
		cell.setVerticalAlignment(vAlign);
		cell.setMinimumHeight(minHeight);
		cell.setBorder(4);
		cell.setBorder(8);
		return cell;
	}

	public static PdfPCell createLabelCellTopBorder(String text) {
		PdfPCell cell = new PdfPCell(new Phrase(text, new Font(
				FontFamily.HELVETICA, 9, Font.NORMAL, BaseColor.BLACK)));
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
	public static PdfPCell createUltimateCellWithoutBorder(String text, int hAlign,
			int vAlign, int fonti, float minHeight, boolean isBold) {
		Font font = new Font(FontFamily.TIMES_ROMAN, fonti, (isBold ? Font.BOLD
				: Font.NORMAL), BaseColor.BLACK);
		PdfPCell cell = new PdfPCell(new Phrase(text, font));
		cell.setHorizontalAlignment(hAlign);
		cell.setVerticalAlignment(vAlign);
		cell.setMinimumHeight(minHeight);
		cell.setBorder(0);
		return cell;
	}
	
	public static PdfPCell createUltimateCellWithBorder(String text, int hAlign,
			int vAlign, int fonti, float minHeight, boolean isBold,int col_span,int row_span,
			boolean topBorder,boolean bottomBorder) {
		Font font = new Font(FontFamily.TIMES_ROMAN, fonti, (isBold ? Font.BOLD
				: Font.NORMAL), BaseColor.BLACK);
		PdfPCell cell = new PdfPCell(new Phrase(text, font));
		cell.setHorizontalAlignment(hAlign);
		cell.setVerticalAlignment(vAlign);
		cell.setMinimumHeight(minHeight);
		cell.setColspan(col_span);
		cell.setRowspan(row_span);
		cell.setBorder(0);
		cell.setBorderWidthLeft(0.5f);
		cell.setBorderWidthRight(0.5f);
		cell.setNoWrap(false);
		if(topBorder)
		cell.setBorderWidthTop(0.25f);
		if(bottomBorder)
		cell.setBorderWidthBottom(0.25f);
		return cell;
	}
	public static PdfPCell createValueCellWithBottomBorder(String text, Boolean isNum,
			Boolean isDetailCell) {
		Font font = new Font(FontFamily.HELVETICA, 9, Font.NORMAL,
				BaseColor.BLACK);
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

	public static PdfPCell createValueCellWithHeight(String text, Boolean isNum,
			Boolean isDetailCell, float ht) {
		Font font = new Font(FontFamily.HELVETICA, 8, Font.NORMAL,
				BaseColor.BLACK);
		PdfPCell cell = new PdfPCell(new Phrase(text, font));
		valueCellStyle(cell, isNum, isDetailCell);
		cell.setMinimumHeight(ht);
		cell.setPaddingRight(2.0f);
		return cell;
	}
	
	public static PdfPCell createValueCellWithHeightAndAlignment(String text, Boolean isNum,
			Boolean isDetailCell, float ht,int hAlign,int vAlign) {
		Font font = new Font(FontFamily.HELVETICA, 8, Font.NORMAL,
				BaseColor.BLACK);
		PdfPCell cell = new PdfPCell(new Phrase(text, font));
		valueCellStyle(cell, isNum, isDetailCell);
		cell.setMinimumHeight(ht);
		cell.setPaddingRight(2.0f);
		cell.setHorizontalAlignment(hAlign);
		cell.setVerticalAlignment(vAlign);
		return cell;
	}

	public static PdfPCell createValueCellWithHeightGroupingTitle(String text,
			Boolean isNum, Boolean isDetailCell, float ht) {
		Font font = new Font(FontFamily.HELVETICA, 8, Font.NORMAL,
				BaseColor.BLACK);
		PdfPCell cell = new PdfPCell(new Phrase(text, font));
		valueCellStyle(cell, isNum, isDetailCell);
		cell.setMinimumHeight(ht);
		cell.setColspan(11);
		cell.setGrayFill(0.7f);
		cell.enableBorderSide(Rectangle.BOTTOM);
		cell.setHorizontalAlignment(Element.ALIGN_CENTER);
		cell.setNoWrap(false);
		return cell;
	}

	public static PdfPCell createValueCellWithHeightInDetail(String text,
			Boolean isNum, Boolean isDetailCell, float ht) {
		Font font = new Font(FontFamily.HELVETICA, 8, Font.NORMAL,
				BaseColor.BLACK);
		PdfPCell cell = new PdfPCell(new Phrase(text, font));
		valueCellStyle(cell, isNum, isDetailCell);
		cell.setMinimumHeight(ht);
		cell.setColspan(10);
		cell.enableBorderSide(Rectangle.BOTTOM);
		cell.setHorizontalAlignment(Element.ALIGN_CENTER);
		cell.setNoWrap(false);
		return cell;
	}

	public static PdfPCell createUltimateCellWithBorderWithDisableBorder(String text, int hAlign,
			int vAlign, int fonti, float minHeight, boolean isBold,int col_span,int row_span,
			boolean topBorder,boolean bottomBorder,boolean disable_border,boolean disableBorderLeft
			,boolean disableBorderRight) {
		
		
		Font font = new Font(FontFamily.TIMES_ROMAN, fonti, (isBold ? Font.BOLD
				: Font.NORMAL), BaseColor.BLACK);
		PdfPCell cell = new PdfPCell(new Phrase(text, font));
		cell.setHorizontalAlignment(hAlign);
		cell.setVerticalAlignment(vAlign);
		cell.setMinimumHeight(minHeight);
		cell.setColspan(col_span);
		cell.setRowspan(row_span);
		cell.setBorder(0);
		cell.setPaddingTop(0);
	//	cell.setPaddingBottom(-10f);
		cell.setBorderWidthLeft(0.5f);
		cell.setBorderWidthRight(0.5f);
		cell.setNoWrap(false);
		if(topBorder){
		cell.setBorderWidthTop(0.25f);
		}else{
			cell.disableBorderSide(Rectangle.TOP);
		}
		if(bottomBorder){
		cell.setBorderWidthBottom(0.25f);
		}else{
			cell.disableBorderSide(Rectangle.BOTTOM);
		}
		if(disable_border){
			if(disableBorderLeft)
		cell.disableBorderSide(Rectangle.LEFT);
			if(disableBorderRight)
		cell.disableBorderSide(Rectangle.RIGHT);
		}
		return cell;
	}
	public static PdfPCell createUltimateCellWithBorderWithDisableBorder(Paragraph p, int hAlign,
		int vAlign, int fonti, float minHeight, boolean isBold,int col_span,int row_span,
		boolean topBorder,boolean bottomBorder,boolean disable_border,boolean disableBorderLeft
		,boolean disableBorderRight) {
	Font font = new Font(FontFamily.TIMES_ROMAN, fonti, (isBold ? Font.BOLD
			: Font.NORMAL), BaseColor.BLACK);
	PdfPCell cell = new PdfPCell(p);
	cell.setHorizontalAlignment(hAlign);
	cell.setVerticalAlignment(vAlign);
	cell.setMinimumHeight(minHeight);
	cell.setColspan(col_span);
	cell.setRowspan(row_span);
	cell.setBorder(0);
	cell.setPaddingTop(0);
//	cell.setPaddingBottom(-10f);
	cell.setBorderWidthLeft(0.5f);
	cell.setBorderWidthRight(0.5f);
	cell.setNoWrap(false);
	if(topBorder){
	cell.setBorderWidthTop(0.25f);
	}else{
		cell.disableBorderSide(Rectangle.TOP);
	}
	if(bottomBorder){
	cell.setBorderWidthBottom(0.25f);
	}else{
		cell.disableBorderSide(Rectangle.BOTTOM);
	}
	if(disable_border){
		if(disableBorderLeft)
	cell.disableBorderSide(Rectangle.LEFT);
		if(disableBorderRight)
	cell.disableBorderSide(Rectangle.RIGHT);
	}
	return cell;
}
	
	public static PdfPCell createValueCellWithHeightBold(String text, Boolean isNum,
			Boolean isDetailCell, float ht) {
		Font font = new Font(FontFamily.HELVETICA, 8, Font.BOLD,
				BaseColor.BLACK);
		PdfPCell cell = new PdfPCell(new Phrase(text, font));
		valueCellStyle(cell, isNum, isDetailCell);
		cell.setMinimumHeight(ht);
		cell.setPaddingRight(2.0f);
		return cell;
	}

	public static PdfPCell createValueCellWithHeightBoldTopBorder(String text,
			Boolean isNum, Boolean isDetailCell, float ht) {
		Font font = new Font(FontFamily.HELVETICA, 8, Font.BOLD,
				BaseColor.BLACK);
		PdfPCell cell = new PdfPCell(new Phrase(text, font));
		valueCellStyle(cell, isNum, isDetailCell);
		cell.setMinimumHeight(ht);
		cell.setPaddingRight(2.0f);
		cell.setBorderWidthTop(0.5f);
		return cell;
	}

	public static PdfPCell createDatachangeCell(String text,
			int colspan, BaseColor bc) {
		Font font = new Font(FontFamily.HELVETICA,9,Font.NORMAL,bc);
		PdfPCell cell = new PdfPCell(new Phrase(text,font));
		cell.setColspan(colspan);
		cell.setGrayFill(0.9f);
		cell.setMinimumHeight(18f);
		return cell;
	}
	
	public static PdfPCell createLabelCellWithBorderSmallFontNoPad(String text) {
		Font font = new Font(FontFamily.HELVETICA, 8, Font.BOLD,
				BaseColor.BLACK);
		PdfPCell cell = new PdfPCell(new Phrase(text, font));
		cell.setMinimumHeight(9f);
		labelCellStyle(cell);
		cell.setMinimumHeight(0f);
		return cell;
	}
	
	public static PdfPCell createLabelCellWithoutBorderSmall7FontNoBold(String text) {
		Font font = new Font(FontFamily.HELVETICA, 7, Font.NORMAL,
				BaseColor.BLACK);
		PdfPCell cell = new PdfPCell(new Phrase(text, font));
		labelCellStyle(cell);
		cell.setBorder(0);
		cell.setMinimumHeight(15f);
		return cell;
	}
	
	public static PdfPCell createLabelCellL_A_BorderBold(String text, int hAlign, int fonti, float minHeight) {
		Font font = new Font(FontFamily.HELVETICA, fonti, Font.BOLD,
				BaseColor.BLACK);
		PdfPCell cell = new PdfPCell(new Phrase(text, font));
		cell.setHorizontalAlignment(hAlign);
		cell.setMinimumHeight(minHeight);
		
		return cell;
	}
	
	public static PdfPCell createLabelCellL_A_Border(String text, int hAlign, int fonti, float minHeight) {
		Font font = new Font(FontFamily.HELVETICA, fonti, Font.NORMAL,
				BaseColor.BLACK);
		PdfPCell cell = new PdfPCell(new Phrase(text, font));
		cell.setHorizontalAlignment(hAlign);
		cell.setMinimumHeight(minHeight);
		
		return cell;
	}
}
// hcell_childTbl2_t1.enableBorderSide(Rectangle.BOTTOM);
// hcell_childTbl2_t1.enableBorderSide(Rectangle.LEFT);
// hcell_childTbl2_t1.enableBorderSide(Rectangle.RIGHT);
// hcell_childTbl2_t1.setHorizontalAlignment(Element.ALIGN_CENTER);