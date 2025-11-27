package com.excel.utility;

import java.math.BigDecimal;
import java.util.Date;

import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.ss.usermodel.BorderStyle;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.CreationHelper;
import org.apache.poi.ss.usermodel.DataFormat;
import org.apache.poi.ss.usermodel.FillPatternType;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.VerticalAlignment;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;
import org.apache.poi.xssf.usermodel.DefaultIndexedColorMap;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.apache.poi.xssf.usermodel.XSSFColor;
import org.apache.poi.xssf.usermodel.XSSFFont;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class ReportFormats implements MedicoConstants{
	
	public static XSSFCell cellNumLong(XSSFRow row, int colCount, Long cellValue, XSSFCellStyle cellStyle)
			throws Exception {
		XSSFCell cell = row.createCell(colCount);
		cell.setCellValue(cellValue);
		if (cellStyle != null) {
			cell.setCellStyle(cellStyle);
		}
		return cell;
	}
	
	
	
	public static XSSFCellStyle dateStyle(XSSFWorkbook workbook) throws Exception {
		XSSFCellStyle dateStyle = workbook.createCellStyle();
		CreationHelper creationHelper = workbook.getCreationHelper();
		dateStyle.setDataFormat(creationHelper.createDataFormat().getFormat("dd-MM-yyyy"));
		return dateStyle;
	}
	
	
	
	public static XSSFCell cellLong(XSSFRow row, int colCount, Long cellValue, XSSFCellStyle cellStyle) throws Exception {
		XSSFCell cell = row.createCell(colCount);
		cell.setCellValue(cellValue);
		if (cellStyle != null) {
			cell.setCellStyle(cellStyle);
		}
		return cell;
	}
	
	public static XSSFCell cellDate(XSSFRow row, int colCount, Date cellValue, XSSFCellStyle cellStyle) throws Exception {
		XSSFCell cell = row.createCell(colCount);
		cell.setCellValue(cellValue);
		if (cellStyle != null) {
			cell.setCellStyle(cellStyle);
		}
		return cell;
	}
	
	
	public static XSSFCell cellBigDecimal(XSSFRow row, int colCount, BigDecimal cellValue, XSSFCellStyle cellStyle) throws Exception {
		XSSFCell cell = row.createCell(colCount);
		cell.setCellValue(Double.valueOf(cellValue.toString()));
		if (cellStyle != null) {
			cell.setCellStyle(cellStyle);
		}
		return cell;
	}
	
	public static XSSFCellStyle setCellAlignmentWithTwoDecimal(XSSFWorkbook workbook, String alignment) throws Exception {
		XSSFCellStyle cellStyle = workbook.createCellStyle();
	     DataFormat format = workbook.createDataFormat();
		cellStyle.setDataFormat(format.getFormat("0.00"));
		if(alignment.equals(ALIGN_CENTER))
			cellStyle.setAlignment(HorizontalAlignment.CENTER);
		if(alignment.equals(ALIGN_LEFT))
			cellStyle.setAlignment(HorizontalAlignment.LEFT);
		if(alignment.equals(ALIGN_RIGHT))
			cellStyle.setAlignment(HorizontalAlignment.RIGHT);
		return cellStyle;
	}
	
	public static XSSFCellStyle cellBold2(XSSFWorkbook workbook) throws Exception {
		XSSFFont tableHeaderFont = workbook.createFont();
		tableHeaderFont.setFontHeightInPoints((short) 12);
		tableHeaderFont.setBold(true);
		tableHeaderFont.setColor(IndexedColors.BLACK.index);
		
		XSSFColor myColor = new XSSFColor(new java.awt.Color(180, 198, 231), new DefaultIndexedColorMap());


		XSSFCellStyle CellStyle = workbook.createCellStyle();
//		CellStyle.setFillForegroundColor(IndexedColors.PALE_BLUE.index);	
		CellStyle.setFillForegroundColor(myColor);
		CellStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);
		CellStyle.setAlignment(HorizontalAlignment.CENTER);
		CellStyle.setVerticalAlignment(VerticalAlignment.CENTER);
		CellStyle.setFont(tableHeaderFont);
		CellStyle.setBorderLeft(BorderStyle.THIN);
		CellStyle.setLeftBorderColor(IndexedColors.BLACK.index);
		CellStyle.setBorderRight(BorderStyle.THIN);
		CellStyle.setRightBorderColor(IndexedColors.BLACK.index);
		CellStyle.setBorderTop(BorderStyle.THIN);
		CellStyle.setTopBorderColor(IndexedColors.BLACK.index);
		CellStyle.setBorderBottom(BorderStyle.THIN);
		CellStyle.setBottomBorderColor(IndexedColors.BLACK.index);
		return CellStyle;
	}
	
	public static XSSFCellStyle cellBold1(XSSFWorkbook workbook) throws Exception {
		XSSFFont tableHeaderFont = workbook.createFont();
		tableHeaderFont.setFontHeightInPoints((short) 12);
		tableHeaderFont.setBold(true);
		tableHeaderFont.setColor(IndexedColors.BLACK.index);
		
		XSSFColor myColor = new XSSFColor(new java.awt.Color(180, 198, 231), new DefaultIndexedColorMap());


		XSSFCellStyle CellStyle = workbook.createCellStyle();
//		CellStyle.setFillForegroundColor(IndexedColors.PALE_BLUE.index);	
		CellStyle.setFillForegroundColor(myColor);
		CellStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);
		CellStyle.setAlignment(HorizontalAlignment.CENTER);
		CellStyle.setFont(tableHeaderFont);
		CellStyle.setBorderLeft(BorderStyle.THIN);
		CellStyle.setLeftBorderColor(IndexedColors.BLACK.index);
		CellStyle.setBorderRight(BorderStyle.THIN);
		CellStyle.setRightBorderColor(IndexedColors.BLACK.index);
		return CellStyle;
	}
	
	public static XSSFCellStyle columnHeadingBlue(XSSFWorkbook workbook) throws Exception {
		XSSFFont tableHeaderFont = workbook.createFont();
		tableHeaderFont.setFontHeightInPoints((short) 12);
		tableHeaderFont.setBold(true);
		tableHeaderFont.setColor(IndexedColors.BLACK.index);

		XSSFColor myColor = new XSSFColor(new java.awt.Color(180, 198, 231), new DefaultIndexedColorMap());

		XSSFCellStyle headerCellStyle = workbook.createCellStyle();
//		CellStyle.setFillForegroundColor(IndexedColors.PALE_BLUE.index);	
		headerCellStyle.setFillForegroundColor(myColor);
		headerCellStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);
		headerCellStyle.setAlignment(HorizontalAlignment.CENTER);
		headerCellStyle.setFont(tableHeaderFont);
		headerCellStyle.setBorderTop(BorderStyle.THIN);
		headerCellStyle.setTopBorderColor(IndexedColors.BLACK.index);
		headerCellStyle.setBorderBottom(BorderStyle.THIN);
		headerCellStyle.setBottomBorderColor(IndexedColors.BLACK.index);
		headerCellStyle.setBorderLeft(BorderStyle.THIN);
		headerCellStyle.setLeftBorderColor(IndexedColors.BLACK.index);
		headerCellStyle.setBorderRight(BorderStyle.THIN);
		headerCellStyle.setRightBorderColor(IndexedColors.BLACK.index);

		return headerCellStyle;
	}
	
	public static XSSFCellStyle heading3(XSSFWorkbook workbook) throws Exception {

		XSSFColor myColor = new XSSFColor(new java.awt.Color(198, 224, 180), new DefaultIndexedColorMap());

		XSSFCellStyle cellStyle = workbook.createCellStyle();
//		cellStyle.setFillForegroundColor(IndexedColors.LIGHT_CORNFLOWER_BLUE.index);
		cellStyle.setFillForegroundColor(myColor);
		cellStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);
		cellStyle.setAlignment(HorizontalAlignment.LEFT);

		XSSFFont headerFont = workbook.createFont();
		headerFont.setFontHeightInPoints((short) 14);
		headerFont.setBold(true);
		cellStyle.setFont(headerFont);
		return cellStyle;
	}
	
	public static XSSFCellStyle heading3_(XSSFWorkbook workbook) throws Exception {
		XSSFCellStyle cellStyle = workbook.createCellStyle();
		XSSFColor lightGreen = new XSSFColor(new java.awt.Color(226, 239, 218), new DefaultIndexedColorMap());
		cellStyle.setFillForegroundColor(lightGreen);
		cellStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);
		cellStyle.setAlignment(HorizontalAlignment.LEFT);
	
		
		XSSFFont headerFont = workbook.createFont();
		headerFont.setFontHeightInPoints((short) 18);
		headerFont.setBold(true);
		cellStyle.setFont(headerFont);
		return cellStyle;
	}
	
	public static XSSFCellStyle columnSubHeading3(XSSFWorkbook workbook) throws Exception{
		XSSFFont tableHeaderFont = workbook.createFont();
		tableHeaderFont.setFontHeightInPoints((short) 12);
		tableHeaderFont.setBold(true);
		tableHeaderFont.setColor(IndexedColors.BLACK.index);
		
		XSSFCellStyle headerCellStyle = workbook.createCellStyle();
		headerCellStyle.setFillForegroundColor(IndexedColors.LIGHT_GREEN.index);
		headerCellStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);
		headerCellStyle.setAlignment(HorizontalAlignment.CENTER);
		headerCellStyle.setFont(tableHeaderFont);
		headerCellStyle.setBorderTop(BorderStyle.THIN);
		headerCellStyle.setTopBorderColor(IndexedColors.BLACK.index);
		headerCellStyle.setBorderBottom(BorderStyle.THIN);
		headerCellStyle.setBottomBorderColor(IndexedColors.BLACK.index);
		headerCellStyle.setBorderLeft(BorderStyle.THIN);
		headerCellStyle.setLeftBorderColor(IndexedColors.BLACK.index);
		headerCellStyle.setBorderRight(BorderStyle.THIN);
		headerCellStyle.setRightBorderColor(IndexedColors.BLACK.index);
		
		return headerCellStyle;
	}
	public static XSSFCellStyle columnSubHeading2(XSSFWorkbook workbook) throws Exception{
		XSSFFont tableHeaderFont = workbook.createFont();
		tableHeaderFont.setFontHeightInPoints((short) 12);
		tableHeaderFont.setBold(true);
		tableHeaderFont.setColor(IndexedColors.BLACK.index);
		
		XSSFCellStyle headerCellStyle = workbook.createCellStyle();
		headerCellStyle.setFillForegroundColor(IndexedColors.LEMON_CHIFFON.index);
		headerCellStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);
		headerCellStyle.setAlignment(HorizontalAlignment.CENTER);
		headerCellStyle.setFont(tableHeaderFont);
		headerCellStyle.setBorderTop(BorderStyle.THIN);
		headerCellStyle.setTopBorderColor(IndexedColors.BLACK.index);
		headerCellStyle.setBorderBottom(BorderStyle.THIN);
		headerCellStyle.setBottomBorderColor(IndexedColors.BLACK.index);
		headerCellStyle.setBorderLeft(BorderStyle.THIN);
		headerCellStyle.setLeftBorderColor(IndexedColors.BLACK.index);
		headerCellStyle.setBorderRight(BorderStyle.THIN);
		headerCellStyle.setRightBorderColor(IndexedColors.BLACK.index);
		
		return headerCellStyle;
	}
	public static XSSFCellStyle columnSubHeading(XSSFWorkbook workbook) throws Exception{
		XSSFFont tableHeaderFont = workbook.createFont();
		tableHeaderFont.setFontHeightInPoints((short) 12);
		tableHeaderFont.setBold(true);
		tableHeaderFont.setColor(IndexedColors.BLACK.index);
		
		XSSFCellStyle headerCellStyle = workbook.createCellStyle();
		headerCellStyle.setFillForegroundColor(IndexedColors.PALE_BLUE.index);
		headerCellStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);
		headerCellStyle.setAlignment(HorizontalAlignment.LEFT);
		headerCellStyle.setFont(tableHeaderFont);
		headerCellStyle.setBorderTop(BorderStyle.THIN);
		headerCellStyle.setTopBorderColor(IndexedColors.BLACK.index);
		headerCellStyle.setBorderBottom(BorderStyle.THIN);
		headerCellStyle.setBottomBorderColor(IndexedColors.BLACK.index);
		headerCellStyle.setBorderLeft(BorderStyle.THIN);
		headerCellStyle.setLeftBorderColor(IndexedColors.BLACK.index);
		headerCellStyle.setBorderRight(BorderStyle.THIN);
		headerCellStyle.setRightBorderColor(IndexedColors.BLACK.index);
		
		return headerCellStyle;
	}
	
	public static XSSFCellStyle columnHeading(XSSFWorkbook workbook) throws Exception {
		XSSFFont tableHeaderFont = workbook.createFont();
		tableHeaderFont.setFontHeightInPoints((short) 12);
		tableHeaderFont.setBold(true);
		tableHeaderFont.setColor(IndexedColors.BLACK.index);
		
		XSSFCellStyle headerCellStyle = workbook.createCellStyle();
		headerCellStyle.setFillForegroundColor(IndexedColors.CORNFLOWER_BLUE.index);
		headerCellStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);
		headerCellStyle.setAlignment(HorizontalAlignment.CENTER);
		headerCellStyle.setFont(tableHeaderFont);
		headerCellStyle.setBorderTop(BorderStyle.THIN);
		headerCellStyle.setTopBorderColor(IndexedColors.BLACK.index);
		headerCellStyle.setBorderBottom(BorderStyle.THIN);
		headerCellStyle.setBottomBorderColor(IndexedColors.BLACK.index);
		headerCellStyle.setBorderLeft(BorderStyle.THIN);
		headerCellStyle.setLeftBorderColor(IndexedColors.BLACK.index);
		headerCellStyle.setBorderRight(BorderStyle.THIN);
		headerCellStyle.setRightBorderColor(IndexedColors.BLACK.index);
		
		return headerCellStyle;
	}
	
	public static XSSFCellStyle heading(XSSFWorkbook workbook) throws Exception {
		XSSFCellStyle cellStyle = workbook.createCellStyle();
		cellStyle.setFillForegroundColor(IndexedColors.LEMON_CHIFFON.index);
		cellStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);
		cellStyle.setAlignment(HorizontalAlignment.CENTER);
		XSSFFont headerFont = workbook.createFont();
		headerFont.setFontHeightInPoints((short) 14);
		headerFont.setBold(true);
		cellStyle.setFont(headerFont);
		return cellStyle;
	}
	
	
	public static XSSFCellStyle heading_left(XSSFWorkbook workbook) throws Exception {
		XSSFCellStyle cellStyle = workbook.createCellStyle();
		cellStyle.setFillForegroundColor(IndexedColors.LEMON_CHIFFON.index);
		cellStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);
		cellStyle.setAlignment(HorizontalAlignment.LEFT);
		XSSFFont headerFont = workbook.createFont();
		headerFont.setFontHeightInPoints((short) 14);
		headerFont.setBold(true);
		cellStyle.setFont(headerFont);
		return cellStyle;
	}
	
	public static XSSFCellStyle setCellAlignment(XSSFWorkbook workbook, String alignment) throws Exception {
		XSSFCellStyle cellStyle = workbook.createCellStyle();
		if(alignment.equals(ALIGN_CENTER))
			cellStyle.setAlignment(HorizontalAlignment.CENTER);
		if(alignment.equals(ALIGN_LEFT))
			cellStyle.setAlignment(HorizontalAlignment.LEFT);
		if(alignment.equals(ALIGN_RIGHT))
			cellStyle.setAlignment(HorizontalAlignment.RIGHT);
		cellStyle.setFillForegroundColor(IndexedColors.GREY_25_PERCENT.getIndex());
		cellStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);
		return cellStyle;
	}
	
	
	public static XSSFCellStyle setCellAlignment_total(XSSFWorkbook workbook) {
        // Create a new cell style
        XSSFCellStyle style = workbook.createCellStyle();

        // Set alignment
        style.setAlignment(HorizontalAlignment.LEFT);

        // Set background color (light gray in this example)
        style.setFillForegroundColor(IndexedColors.GREY_25_PERCENT.getIndex());
        style.setFillPattern(FillPatternType.SOLID_FOREGROUND);

        // Create a bold font
        XSSFFont font = workbook.createFont();
        font.setBold(true);
        style.setFont(font);

        return style;
    }
	
	
	
	public static XSSFCell cell(XSSFRow row, int colCount, String cellValue, XSSFCellStyle cellStyle) throws Exception {
		XSSFCell cell = row.createCell(colCount);
		cell.setCellValue(cellValue);
		if (cellStyle != null) {
			cell.setCellStyle(cellStyle);
		}
		return cell;
	}
	
	public static XSSFCell cellNum(XSSFRow row, int colCount, Double cellValue, XSSFCellStyle cellStyle) throws Exception {
		XSSFCell cell = row.createCell(colCount);
		cell.setCellValue(cellValue);
		if (cellStyle != null) {
			cell.setCellStyle(cellStyle);
		}
		return cell;
	}
	
	public static XSSFCellStyle heading1(XSSFWorkbook workbook) throws Exception {
		XSSFCellStyle cellStyle = workbook.createCellStyle();
		cellStyle.setFillForegroundColor(IndexedColors.LEMON_CHIFFON.index);
		cellStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);
		cellStyle.setAlignment(HorizontalAlignment.LEFT);
		XSSFFont headerFont = workbook.createFont();
		headerFont.setFontHeightInPoints((short) 14);
		headerFont.setBold(true);
		cellStyle.setFont(headerFont);
		return cellStyle;
	}
	
	public static Cell cell(Row row, int colCount, String cellValue, CellStyle cellStyle) throws Exception {
		Cell cell = row.createCell(colCount);
		cell.setCellValue(cellValue);
		if (cellStyle != null) {
			cell.setCellStyle(cellStyle);
		}
		return cell;
	}
	
	public static Cell cellNum(Row row, int colCount, Double cellValue, CellStyle cellStyle) throws Exception {
		Cell cell = row.createCell(colCount);
		cell.setCellValue(cellValue);
		if (cellStyle != null) {
			cell.setCellStyle(cellStyle);
		}
		return cell;
	}
	
	public static CellStyle setCellAlignment(SXSSFWorkbook workbook, String alignment) throws Exception {
		CellStyle cellStyle = workbook.createCellStyle();
		if(alignment.equals(ALIGN_CENTER))
			cellStyle.setAlignment(HorizontalAlignment.CENTER);
		if(alignment.equals(ALIGN_LEFT))
			cellStyle.setAlignment(HorizontalAlignment.LEFT);
		if(alignment.equals(ALIGN_RIGHT))
			cellStyle.setAlignment(HorizontalAlignment.RIGHT);
		return cellStyle;
	}
	
	public static CellStyle heading(SXSSFWorkbook workbook) throws Exception {
		CellStyle cellStyle = workbook.createCellStyle();
		cellStyle.setFillForegroundColor(IndexedColors.LEMON_CHIFFON.index);
		cellStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);
		cellStyle.setAlignment(HorizontalAlignment.LEFT);
		Font headerFont = workbook.createFont();
		headerFont.setFontHeightInPoints((short) 14);
		headerFont.setBold(true);
		cellStyle.setFont(headerFont);
		return cellStyle;
	}
	
	public static CellStyle columnHeading(SXSSFWorkbook workbook) throws Exception {
		Font tableHeaderFont = workbook.createFont();
		tableHeaderFont.setFontHeightInPoints((short) 12);
		tableHeaderFont.setBold(true);
		tableHeaderFont.setColor(IndexedColors.BLACK.index);
		
		CellStyle headerCellStyle = workbook.createCellStyle();
		headerCellStyle.setFillForegroundColor(IndexedColors.CORNFLOWER_BLUE.index);
		headerCellStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);
		headerCellStyle.setAlignment(HorizontalAlignment.CENTER);
		headerCellStyle.setFont(tableHeaderFont);
		headerCellStyle.setBorderTop(BorderStyle.THIN);
		headerCellStyle.setTopBorderColor(IndexedColors.BLACK.index);
		headerCellStyle.setBorderBottom(BorderStyle.THIN);
		headerCellStyle.setBottomBorderColor(IndexedColors.BLACK.index);
		headerCellStyle.setBorderLeft(BorderStyle.THIN);
		headerCellStyle.setLeftBorderColor(IndexedColors.BLACK.index);
		headerCellStyle.setBorderRight(BorderStyle.THIN);
		headerCellStyle.setRightBorderColor(IndexedColors.BLACK.index);
		
		return headerCellStyle;
	}
	public static Cell cellnew(Row row, int colCount, String cellValue, CellStyle columnHeadingStyle) throws Exception {
		Cell cell = row.createCell(colCount);
		cell.setCellValue(cellValue);
		if (columnHeadingStyle != null) {
			cell.setCellStyle(columnHeadingStyle);
		}
		return cell;
	}
	
	public static CellStyle headingCellStyle(SXSSFWorkbook workbook) throws Exception {
		CellStyle cellStyle = workbook.createCellStyle();
		cellStyle.setFillForegroundColor(IndexedColors.LEMON_CHIFFON.index);
		cellStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);
		cellStyle.setAlignment(HorizontalAlignment.CENTER);
		Font headerFont = workbook.createFont();
		headerFont.setFontHeightInPoints((short) 14);
		headerFont.setBold(true);
		cellStyle.setFont(headerFont);
		return cellStyle;
	}
	
	public static CellStyle columnHeadingNew(SXSSFWorkbook workbook) throws Exception {
		Font tableHeaderFont = workbook.createFont();
		tableHeaderFont.setFontHeightInPoints((short) 12);
		tableHeaderFont.setBold(true);
		tableHeaderFont.setColor(IndexedColors.BLACK.index);
		
		CellStyle headerCellStyle = workbook.createCellStyle();
		headerCellStyle.setFillForegroundColor(IndexedColors.CORNFLOWER_BLUE.index);
		headerCellStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);
		headerCellStyle.setAlignment(HorizontalAlignment.CENTER);
		headerCellStyle.setFont(tableHeaderFont);
		headerCellStyle.setBorderTop(BorderStyle.THIN);
		headerCellStyle.setTopBorderColor(IndexedColors.BLACK.index);
		headerCellStyle.setBorderBottom(BorderStyle.THIN);
		headerCellStyle.setBottomBorderColor(IndexedColors.BLACK.index);
		headerCellStyle.setBorderLeft(BorderStyle.THIN);
		headerCellStyle.setLeftBorderColor(IndexedColors.BLACK.index);
		headerCellStyle.setBorderRight(BorderStyle.THIN);
		headerCellStyle.setRightBorderColor(IndexedColors.BLACK.index);
		
		return headerCellStyle;
	}
	
	public static CellStyle setCellAlignmentNew(SXSSFWorkbook workbook, String alignment) throws Exception {
		CellStyle cellStyle = workbook.createCellStyle();
		if(alignment.equals(ALIGN_CENTER))
			cellStyle.setAlignment(HorizontalAlignment.CENTER);
		if(alignment.equals(ALIGN_LEFT))
			cellStyle.setAlignment(HorizontalAlignment.LEFT);
		if(alignment.equals(ALIGN_RIGHT))
			cellStyle.setAlignment(HorizontalAlignment.RIGHT);
		return cellStyle;
	}
	
	public static XSSFCellStyle heading2(XSSFWorkbook workbook) throws Exception {
		XSSFCellStyle cellStyle = workbook.createCellStyle();
		cellStyle.setFillForegroundColor(IndexedColors.LIGHT_CORNFLOWER_BLUE.index);
		cellStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);
		cellStyle.setAlignment(HorizontalAlignment.CENTER);
	
		
		XSSFFont headerFont = workbook.createFont();
		headerFont.setFontHeightInPoints((short) 14);
		headerFont.setBold(true);
		cellStyle.setFont(headerFont);
		return cellStyle;
	}
	
	
	public static XSSFCellStyle heading2WithColor(XSSFWorkbook workbook,short color) throws Exception {
		XSSFCellStyle cellStyle = workbook.createCellStyle();
		cellStyle.setFillForegroundColor(color);
		cellStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);
		cellStyle.setAlignment(HorizontalAlignment.CENTER);
	
		
		XSSFFont headerFont = workbook.createFont();
		headerFont.setFontHeightInPoints((short) 14);
		headerFont.setBold(true);
		cellStyle.setFont(headerFont);
		return cellStyle;
	}
	
	public static XSSFCellStyle columnHeadingGrey(XSSFWorkbook workbook) throws Exception {
		XSSFFont tableHeaderFont = workbook.createFont();
		tableHeaderFont.setFontHeightInPoints((short) 12);
		tableHeaderFont.setBold(true);
		tableHeaderFont.setColor(IndexedColors.BLACK.index);
		
		XSSFCellStyle headerCellStyle = workbook.createCellStyle();
		headerCellStyle.setFillForegroundColor(IndexedColors.TAN.index);
		headerCellStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);
		headerCellStyle.setAlignment(HorizontalAlignment.CENTER);
		headerCellStyle.setFont(tableHeaderFont);
		headerCellStyle.setBorderTop(BorderStyle.THIN);
		headerCellStyle.setTopBorderColor(IndexedColors.BLACK.index);
		headerCellStyle.setBorderBottom(BorderStyle.THIN);
		headerCellStyle.setBottomBorderColor(IndexedColors.BLACK.index);
		headerCellStyle.setBorderLeft(BorderStyle.THIN);
		headerCellStyle.setLeftBorderColor(IndexedColors.BLACK.index);
		headerCellStyle.setBorderRight(BorderStyle.THIN);
		headerCellStyle.setRightBorderColor(IndexedColors.BLACK.index);
		
		return headerCellStyle;
	}
	
	
	public static XSSFCellStyle columnHeadingLightBlue(XSSFWorkbook workbook) throws Exception {
		XSSFFont tableHeaderFont = workbook.createFont();
		tableHeaderFont.setFontHeightInPoints((short) 12);
		tableHeaderFont.setBold(true);
		tableHeaderFont.setColor(IndexedColors.BLACK.index);
		
		XSSFCellStyle headerCellStyle = workbook.createCellStyle();
		headerCellStyle.setFillForegroundColor(IndexedColors.PALE_BLUE.index);
		headerCellStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);
		headerCellStyle.setAlignment(HorizontalAlignment.CENTER);
		headerCellStyle.setVerticalAlignment(VerticalAlignment.CENTER);
		headerCellStyle.setFont(tableHeaderFont);
		headerCellStyle.setBorderTop(BorderStyle.THIN);
		headerCellStyle.setTopBorderColor(IndexedColors.BLACK.index);
		headerCellStyle.setBorderBottom(BorderStyle.THIN);
		headerCellStyle.setBottomBorderColor(IndexedColors.BLACK.index);
		headerCellStyle.setBorderLeft(BorderStyle.THIN);
		headerCellStyle.setLeftBorderColor(IndexedColors.BLACK.index);
		headerCellStyle.setBorderRight(BorderStyle.THIN);
		headerCellStyle.setRightBorderColor(IndexedColors.BLACK.index);
		headerCellStyle.setWrapText(true);
		
		return headerCellStyle;
	}
	
	public static XSSFCellStyle cellBold(XSSFWorkbook workbook) throws Exception {
		XSSFFont tableHeaderFont = workbook.createFont();
		tableHeaderFont.setFontHeightInPoints((short) 12);
		tableHeaderFont.setBold(true);
		tableHeaderFont.setColor(IndexedColors.BLACK.index);
		
		XSSFCellStyle CellStyle = workbook.createCellStyle();
		CellStyle.setFillForegroundColor(IndexedColors.PALE_BLUE.index);
		CellStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);
		CellStyle.setAlignment(HorizontalAlignment.CENTER);
		CellStyle.setFont(tableHeaderFont);
		CellStyle.setBorderLeft(BorderStyle.THIN);
		CellStyle.setLeftBorderColor(IndexedColors.BLACK.index);
		CellStyle.setBorderRight(BorderStyle.THIN);
		CellStyle.setRightBorderColor(IndexedColors.BLACK.index);
		return CellStyle;
	}
	
	public static XSSFCellStyle setCellAlignmentForNum(XSSFWorkbook workbook, String alignment) throws Exception {
		XSSFCellStyle cellStyle = workbook.createCellStyle();
		DataFormat format = workbook.createDataFormat();
		cellStyle.setDataFormat(format.getFormat("0.00"));
		if(alignment.equals(ALIGN_CENTER))
			cellStyle.setAlignment(HorizontalAlignment.CENTER);
		if(alignment.equals(ALIGN_LEFT))
			cellStyle.setAlignment(HorizontalAlignment.LEFT);
		if(alignment.equals(ALIGN_RIGHT))
			cellStyle.setAlignment(HorizontalAlignment.RIGHT);
		return cellStyle;
	}
	public static XSSFCellStyle columnSubHeadingright(XSSFWorkbook workbook) throws Exception{
		XSSFFont tableHeaderFont = workbook.createFont();
		tableHeaderFont.setFontHeightInPoints((short) 12);
		tableHeaderFont.setBold(true);
		tableHeaderFont.setColor(IndexedColors.BLACK.index);
		
		XSSFCellStyle headerCellStyle = workbook.createCellStyle();
		headerCellStyle.setFillForegroundColor(IndexedColors.PALE_BLUE.index);
		headerCellStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);
		headerCellStyle.setAlignment(HorizontalAlignment.RIGHT);
		headerCellStyle.setFont(tableHeaderFont);
		headerCellStyle.setBorderTop(BorderStyle.THIN);
		headerCellStyle.setTopBorderColor(IndexedColors.BLACK.index);
		headerCellStyle.setBorderBottom(BorderStyle.THIN);
		headerCellStyle.setBottomBorderColor(IndexedColors.BLACK.index);
		headerCellStyle.setBorderLeft(BorderStyle.THIN);
		headerCellStyle.setLeftBorderColor(IndexedColors.BLACK.index);
		headerCellStyle.setBorderRight(BorderStyle.THIN);
		headerCellStyle.setRightBorderColor(IndexedColors.BLACK.index);
		
		return headerCellStyle;
	}
	
	public static XSSFCellStyle heading4(XSSFWorkbook workbook) throws Exception {
		XSSFCellStyle cellStyle = workbook.createCellStyle();
		XSSFColor lightGreen = new XSSFColor(new java.awt.Color(226, 239, 218), new DefaultIndexedColorMap());
		cellStyle.setFillForegroundColor(lightGreen);
		cellStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);
		cellStyle.setAlignment(HorizontalAlignment.LEFT);
	
		
		XSSFFont headerFont = workbook.createFont();
		headerFont.setFontHeightInPoints((short) 14);
		headerFont.setBold(true);
		cellStyle.setFont(headerFont);
		return cellStyle;
	}
	
	public static XSSFCellStyle datastyle(XSSFWorkbook workbook) throws Exception {
		XSSFCellStyle cellStyle = workbook.createCellStyle();
		XSSFColor myColor = new XSSFColor(new java.awt.Color(48, 84, 150), new DefaultIndexedColorMap());//		cellStyle.setFont(null);
		cellStyle.setAlignment(HorizontalAlignment.CENTER);	
		XSSFFont headerFont = workbook.createFont();
		headerFont.setColor(myColor);
		headerFont.setFontHeightInPoints((short) 14);
		headerFont.setBold(true);
		cellStyle.setFont(headerFont);
		return cellStyle;
	}
	
	public static XSSFCellStyle datastyleLeft(XSSFWorkbook workbook) throws Exception {
		XSSFCellStyle cellStyle = workbook.createCellStyle();
		XSSFColor myColor = new XSSFColor(new java.awt.Color(48, 84, 150), new DefaultIndexedColorMap());//		cellStyle.setFont(null);
		cellStyle.setAlignment(HorizontalAlignment.LEFT);	
		XSSFFont headerFont = workbook.createFont();
		headerFont.setColor(myColor);
		headerFont.setFontHeightInPoints((short) 14);
		headerFont.setBold(true);
		cellStyle.setFont(headerFont);
		return cellStyle;
	}
	
	public static XSSFCellStyle datastyleRightwithDecimal(XSSFWorkbook workbook) throws Exception {
		XSSFCellStyle cellStyle = workbook.createCellStyle();
		DataFormat format = workbook.createDataFormat();
		XSSFColor myColor = new XSSFColor(new java.awt.Color(48, 84, 150), new DefaultIndexedColorMap());//		cellStyle.setFont(null);
		cellStyle.setAlignment(HorizontalAlignment.RIGHT);	
		cellStyle.setDataFormat(format.getFormat("0.00"));
		XSSFFont headerFont = workbook.createFont();
		headerFont.setColor(myColor);
		headerFont.setFontHeightInPoints((short) 14);
		headerFont.setBold(true);
		cellStyle.setFont(headerFont);
		return cellStyle;
	}
	
	public static XSSFCellStyle datastyleRight(XSSFWorkbook workbook) throws Exception {
		XSSFCellStyle cellStyle = workbook.createCellStyle();
		XSSFColor myColor = new XSSFColor(new java.awt.Color(48, 84, 150), new DefaultIndexedColorMap());//		cellStyle.setFont(null);
		cellStyle.setAlignment(HorizontalAlignment.RIGHT);	
		XSSFFont headerFont = workbook.createFont();
		headerFont.setColor(myColor);
		headerFont.setFontHeightInPoints((short) 14);
		headerFont.setBold(true);
		cellStyle.setFont(headerFont);
		return cellStyle;
	}
	
	public static XSSFCellStyle columnHeadingGrey_(XSSFWorkbook workbook) throws Exception {
		XSSFFont tableHeaderFont = workbook.createFont();
		tableHeaderFont.setFontHeightInPoints((short) 12);
		tableHeaderFont.setBold(true);
		tableHeaderFont.setColor(IndexedColors.BLACK.index);
		
		XSSFCellStyle headerCellStyle = workbook.createCellStyle();
		XSSFColor myColor = new XSSFColor(new java.awt.Color(180, 198, 231), new DefaultIndexedColorMap());
		headerCellStyle.setFillForegroundColor(myColor);
		headerCellStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);
		headerCellStyle.setAlignment(HorizontalAlignment.CENTER);
		headerCellStyle.setFont(tableHeaderFont);
		headerCellStyle.setBorderTop(BorderStyle.THIN);
		headerCellStyle.setTopBorderColor(IndexedColors.BLACK.index);
		headerCellStyle.setBorderBottom(BorderStyle.THIN);
		headerCellStyle.setBottomBorderColor(IndexedColors.BLACK.index);
		headerCellStyle.setBorderLeft(BorderStyle.THIN);
		headerCellStyle.setLeftBorderColor(IndexedColors.BLACK.index);
		headerCellStyle.setBorderRight(BorderStyle.THIN);
		headerCellStyle.setRightBorderColor(IndexedColors.BLACK.index);
		
		return headerCellStyle;
	}
	
	public static XSSFCellStyle columnHeadingGrey_1(XSSFWorkbook workbook) throws Exception {
		XSSFFont tableHeaderFont = workbook.createFont();
		tableHeaderFont.setFontHeightInPoints((short) 12);
		tableHeaderFont.setBold(true);
		tableHeaderFont.setColor(IndexedColors.BLACK.index);
		
		XSSFCellStyle headerCellStyle = workbook.createCellStyle();
		XSSFColor myColor = new XSSFColor(new java.awt.Color(198, 224, 180), new DefaultIndexedColorMap());
		headerCellStyle.setFillForegroundColor(myColor);
		headerCellStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);
		headerCellStyle.setAlignment(HorizontalAlignment.CENTER);
		headerCellStyle.setFont(tableHeaderFont);
		headerCellStyle.setBorderTop(BorderStyle.THIN);
		headerCellStyle.setTopBorderColor(IndexedColors.BLACK.index);
		headerCellStyle.setBorderBottom(BorderStyle.THIN);
		headerCellStyle.setBottomBorderColor(IndexedColors.BLACK.index);
		headerCellStyle.setBorderLeft(BorderStyle.THIN);
		headerCellStyle.setLeftBorderColor(IndexedColors.BLACK.index);
		headerCellStyle.setBorderRight(BorderStyle.THIN);
		headerCellStyle.setRightBorderColor(IndexedColors.BLACK.index);
		
		return headerCellStyle;
	}
	public static XSSFCellStyle cellBold_(XSSFWorkbook workbook) throws Exception {
		XSSFFont tableHeaderFont = workbook.createFont();
		tableHeaderFont.setFontHeightInPoints((short) 14);
		tableHeaderFont.setBold(true);
		tableHeaderFont.setColor(IndexedColors.BLACK.index);
		
		XSSFCellStyle CellStyle = workbook.createCellStyle();
		XSSFColor myColor = new XSSFColor(new java.awt.Color(180, 198, 231), new DefaultIndexedColorMap());
		CellStyle.setFillForegroundColor(myColor);
		CellStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);
		CellStyle.setAlignment(HorizontalAlignment.CENTER);
		CellStyle.setFont(tableHeaderFont);
		CellStyle.setBorderLeft(BorderStyle.THIN);
		CellStyle.setLeftBorderColor(IndexedColors.BLACK.index);
		CellStyle.setBorderRight(BorderStyle.THIN);
		CellStyle.setRightBorderColor(IndexedColors.BLACK.index);
		return CellStyle;
	}
	public static XSSFCellStyle cellBold_1(XSSFWorkbook workbook) throws Exception {
		XSSFFont tableHeaderFont = workbook.createFont();
		tableHeaderFont.setFontHeightInPoints((short) 12);
		tableHeaderFont.setBold(true);
		tableHeaderFont.setColor(IndexedColors.BLACK.index);
		
		XSSFCellStyle CellStyle = workbook.createCellStyle();
		XSSFColor myColor = new XSSFColor(new java.awt.Color(198, 224, 180), new DefaultIndexedColorMap());
		CellStyle.setFillForegroundColor(myColor);
		CellStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);
		CellStyle.setAlignment(HorizontalAlignment.CENTER);
		CellStyle.setFont(tableHeaderFont);
		CellStyle.setBorderLeft(BorderStyle.THIN);
		CellStyle.setLeftBorderColor(IndexedColors.BLACK.index);
		CellStyle.setBorderRight(BorderStyle.THIN);
		CellStyle.setRightBorderColor(IndexedColors.BLACK.index);
		return CellStyle;
	}
	public static XSSFCellStyle  dataRightAlignWthoutDecimal_total(XSSFWorkbook wwbook) throws Exception {

		XSSFCellStyle rightAlignWthDecimal = null;
	

		try {
			rightAlignWthDecimal = wwbook.createCellStyle();
			rightAlignWthDecimal.setDataFormat(wwbook.createDataFormat().getFormat("0.00"));
			
			Font font = wwbook.createFont();
			font.setFontHeightInPoints((short) 11);
			font.setColor(IndexedColors.BLACK.getIndex());
			font.setBold(true);
			font.setFontName("ARIAL");
			rightAlignWthDecimal.setFillForegroundColor(HSSFColor.HSSFColorPredefined.GREY_25_PERCENT.getIndex());
			rightAlignWthDecimal.setFillPattern(FillPatternType.SOLID_FOREGROUND);
			rightAlignWthDecimal.setFont(font);
			rightAlignWthDecimal.setBorderBottom(BorderStyle.THIN);
			rightAlignWthDecimal.setBorderLeft(BorderStyle.THIN);
			rightAlignWthDecimal.setBorderTop(BorderStyle.THIN);
			rightAlignWthDecimal.setBorderRight(BorderStyle.THIN);
			rightAlignWthDecimal.setAlignment(HorizontalAlignment.RIGHT);

		} catch (Exception e) {
			System.out.println("Error Occurred :" +e.getMessage());// uncomment asneeded --;
		}

		return rightAlignWthDecimal;
	}
	public  static XSSFCellStyle dataLeftAlign_total(XSSFWorkbook wwbook) throws Exception {

		XSSFCellStyle leftAlign = null;

			try {
		
				leftAlign = wwbook.createCellStyle();
				
				leftAlign.setFillForegroundColor(HSSFColor.HSSFColorPredefined.GREY_25_PERCENT.getIndex());
				leftAlign.setFillPattern(FillPatternType.SOLID_FOREGROUND);
				Font font = wwbook.createFont();
				font.setFontHeightInPoints((short) 11);
				font.setFontName("ARIAL");
				font.setColor(IndexedColors.BLACK.getIndex());
				font.setBold(true);
				leftAlign.setFont(font);
				leftAlign.setBorderBottom(BorderStyle.THIN);
				leftAlign.setBorderLeft(BorderStyle.THIN);
				leftAlign.setBorderTop(BorderStyle.THIN);
				leftAlign.setBorderRight(BorderStyle.THIN);
				leftAlign.setAlignment(HorizontalAlignment.LEFT);
			} catch (Exception e) {
				System.out.println("Error Occurred :" +e.getMessage());// uncomment asneeded --;
			}

			return leftAlign;
		}
	
	public static XSSFCellStyle dataLeftAlign(XSSFWorkbook wwbook) throws Exception {

		XSSFCellStyle leftAlign = null;

        try {
                leftAlign = wwbook.createCellStyle();
                Font font = wwbook.createFont();
                font.setFontHeightInPoints((short) 11);
                font.setFontName("ARIAL");
                leftAlign.setFont(font);
                leftAlign.setBorderBottom(BorderStyle.THIN);
                leftAlign.setBorderLeft(BorderStyle.THIN);
                leftAlign.setBorderTop(BorderStyle.THIN);
                leftAlign.setBorderRight(BorderStyle.THIN);
                leftAlign.setAlignment(HorizontalAlignment.LEFT);
        } catch (Exception e) {
                System.out.println("Error Occurred :" +e.getMessage());// uncomment asneeded --;
        }

        return leftAlign;
}public static XSSFCellStyle dataRightAlign(XSSFWorkbook wwbook) throws Exception {

	XSSFCellStyle rightAlignWthDecimal = null;

    try {
            rightAlignWthDecimal = wwbook.createCellStyle();
            Font font = wwbook.createFont();
            DataFormat format = wwbook.createDataFormat();
            font.setFontHeightInPoints((short) 11);
            font.setFontName("ARIAL");
            rightAlignWthDecimal.setFont(font);
			/* rightAlignWthDecimal.setDataFormat(format.getFormat("0.00")); */
            rightAlignWthDecimal.setBorderBottom(BorderStyle.THIN);
            rightAlignWthDecimal.setBorderLeft(BorderStyle.THIN);
            rightAlignWthDecimal.setBorderTop(BorderStyle.THIN);
            rightAlignWthDecimal.setBorderRight(BorderStyle.THIN);
            rightAlignWthDecimal.setAlignment(HorizontalAlignment.RIGHT);
    } catch (Exception e) {
            System.out.println("Error Occurred :" +e.getMessage());// uncomment asneeded --;
    }

    return rightAlignWthDecimal;
}
public static XSSFCellStyle dataRightAlignWthDecimal(XSSFWorkbook wwbook) throws Exception {

	XSSFCellStyle rightAlignWthDecimal = null;

    try {
            rightAlignWthDecimal = wwbook.createCellStyle();
            Font font = wwbook.createFont();
            DataFormat format = wwbook.createDataFormat();
            font.setFontHeightInPoints((short) 11);
            font.setFontName("ARIAL");
            rightAlignWthDecimal.setFont(font);
			 rightAlignWthDecimal.setDataFormat(format.getFormat("0.00")); 
            rightAlignWthDecimal.setBorderBottom(BorderStyle.THIN);
            rightAlignWthDecimal.setBorderLeft(BorderStyle.THIN);
            rightAlignWthDecimal.setBorderTop(BorderStyle.THIN);
            rightAlignWthDecimal.setBorderRight(BorderStyle.THIN);
            rightAlignWthDecimal.setAlignment(HorizontalAlignment.RIGHT);
    } catch (Exception e) {
            System.out.println("Error Occurred :" +e.getMessage());// uncomment asneeded --;
    }

    return rightAlignWthDecimal;
}
}
