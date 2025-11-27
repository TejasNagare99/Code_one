package com.excel.utility;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.ss.usermodel.BorderStyle;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.DataFormat;
import org.apache.poi.ss.usermodel.FillPatternType;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import com.itextpdf.text.BaseColor;
import org.apache.poi.ss.usermodel.VerticalAlignment;
public class ExcelFormat {

	public CellStyle tabHeading(HSSFWorkbook wb)throws Exception {
		CellStyle tabHeading = null;
		
		try {
			
			Font h_font =  wb.createFont();
			h_font.setBold(false);
			h_font.setFontHeightInPoints((short)10);
			   h_font.setFontName("TAHOMA"); 
			   
			 tabHeading = wb.createCellStyle();
			tabHeading.setFont(h_font);
			tabHeading.setWrapText(true);
			tabHeading.setFillForegroundColor(HSSFColor.HSSFColorPredefined.GOLD.getIndex());
			tabHeading.setFillPattern(FillPatternType.SOLID_FOREGROUND);
			tabHeading.setBorderBottom(BorderStyle.MEDIUM);
			tabHeading.setBorderLeft(BorderStyle.MEDIUM);
			tabHeading.setBorderTop(BorderStyle.MEDIUM);
			tabHeading.setBorderRight(BorderStyle.MEDIUM);
			tabHeading.setAlignment(HorizontalAlignment.LEFT);
			tabHeading.setVerticalAlignment(VerticalAlignment.CENTER);
			tabHeading.setWrapText(false);
		} finally
		{
			
		}
		return tabHeading;
	}
	
	public CellStyle tabHeadingCenter(HSSFWorkbook wb)throws Exception {
		CellStyle tabHeading = null;
		try {
			Font h_font =  wb.createFont();
			h_font.setBold(false);
			h_font.setFontHeightInPoints((short)10);
			   h_font.setFontName("TAHOMA"); 
			   
		    tabHeading = wb.createCellStyle();
			tabHeading.setFont(h_font);	
			tabHeading.setWrapText(true);
			tabHeading.setFillForegroundColor(HSSFColor.HSSFColorPredefined.GOLD.getIndex());
			tabHeading.setFillPattern(FillPatternType.SOLID_FOREGROUND);
			tabHeading.setBorderBottom(BorderStyle.MEDIUM);
			tabHeading.setBorderLeft(BorderStyle.MEDIUM);
			tabHeading.setBorderTop(BorderStyle.MEDIUM);
			tabHeading.setBorderRight(BorderStyle.MEDIUM);
			tabHeading.setAlignment(HorizontalAlignment.LEFT);
			tabHeading.setVerticalAlignment(VerticalAlignment.CENTER);
			tabHeading.setWrapText(false);
		} finally
		{
			
		}
		return tabHeading;
	}

	

	public CellStyle errorHeading(HSSFWorkbook wb)throws Exception {
		CellStyle errorHeading = null;
		try {
			Font h_font =  wb.createFont();
			h_font.setBold(true);
			   h_font.setFontName("TAHOMA"); 
			
			errorHeading = wb.createCellStyle();
			errorHeading.setFont(h_font);
			errorHeading.setWrapText(true);
			errorHeading.setFillForegroundColor(HSSFColor.HSSFColorPredefined.RED.getIndex());
			errorHeading.setFillPattern(FillPatternType.SOLID_FOREGROUND);
			errorHeading.setBorderBottom(BorderStyle.MEDIUM);
			errorHeading.setBorderLeft(BorderStyle.MEDIUM);
			errorHeading.setBorderTop(BorderStyle.MEDIUM);
			errorHeading.setBorderRight(BorderStyle.MEDIUM);
			errorHeading.setAlignment(HorizontalAlignment.LEFT);
			errorHeading.setVerticalAlignment(VerticalAlignment.CENTER);
			errorHeading.setWrapText(false);
		} finally
		{
			
		}
		return errorHeading;
	}

	public CellStyle decimalformat(HSSFWorkbook wb)throws Exception {
		CellStyle decimalformat = null;
		try {
			Font h_font =  wb.createFont();
			h_font.setFontHeightInPoints((short)10);
			   h_font.setFontName("TAHOMA"); 
			
			   decimalformat=wb.createCellStyle();
			   decimalformat.setFont(h_font);
			 DataFormat format = wb.createDataFormat();
			 decimalformat.setDataFormat(format.getFormat("##"));
			decimalformat.setAlignment(HorizontalAlignment.RIGHT);
			decimalformat.setVerticalAlignment(VerticalAlignment.CENTER);

		} finally
		{
			
		}
		return decimalformat;
	}
	public CellStyle decimalformatBold(HSSFWorkbook wb)throws Exception {
		CellStyle decimalformat = null;
		try {
			Font h_font =  wb.createFont();
			h_font.setBold(true);
			h_font.setFontHeightInPoints((short)10);
			   h_font.setFontName("TAHOMA"); 
			
			   decimalformat = wb.createCellStyle();
			   decimalformat.setFont(h_font);
			
			   DataFormat format = wb.createDataFormat();
			   decimalformat.setDataFormat(format.getFormat("##"));
			decimalformat.setAlignment(HorizontalAlignment.RIGHT);
			decimalformat.setVerticalAlignment(VerticalAlignment.CENTER);

		} finally
		{
			
		}
		return decimalformat;
	}
	@SuppressWarnings("null")
	public CellStyle doubleformat(HSSFWorkbook wb)throws Exception {
		CellStyle doubleformat = null;
		try {
			Font h_font =  wb.createFont();
			 DataFormat format = wb.createDataFormat();
				/* doubleformat = new CellStyle(n); */
			doubleformat = wb.createCellStyle();
			doubleformat.setAlignment(HorizontalAlignment.RIGHT);
			doubleformat.setVerticalAlignment(VerticalAlignment.CENTER);
			 doubleformat.setDataFormat(format.getFormat("#.00"));

		} finally
		{
			
		}
		return doubleformat;
	}

	public CellStyle dataleft(HSSFWorkbook wb)throws Exception {
		CellStyle dataleft = null;
		try {
			Font h_font =  wb.createFont();
			h_font.setFontHeightInPoints((short)10);
			   h_font.setFontName("TAHOMA"); 
			
			   dataleft = wb.createCellStyle();
			   dataleft.setFont(h_font);
			
			dataleft.setAlignment(HorizontalAlignment.LEFT);
		}  finally
		{
			
		}
		return dataleft;
	}
	public CellStyle dataleftBold(HSSFWorkbook wb)throws Exception {
		CellStyle dataleft = null;
		try {
			Font h_font =  wb.createFont();
			h_font.setBold(true);
			h_font.setFontHeightInPoints((short)10);
			   h_font.setFontName("TAHOMA"); 
			
			   dataleft = wb.createCellStyle();
			   dataleft.setFont(h_font);
			dataleft.setAlignment(HorizontalAlignment.LEFT);
		}  finally
		{
			
		}
		return dataleft;
	}
	public CellStyle dataright(HSSFWorkbook wb)throws Exception {
		CellStyle dataright = null;
		try {
			Font h_font =  wb.createFont();
			h_font.setFontHeightInPoints((short)10);
			   h_font.setFontName("TAHOMA");

			   dataright = wb.createCellStyle();
			   dataright.setFont(h_font);
			dataright.setAlignment(HorizontalAlignment.RIGHT);
		} finally
		{
			
		}
		return dataright;
	}
	
	public CellStyle datarightBold(HSSFWorkbook wb)throws Exception {
		CellStyle dataright = null;
		try {
			Font h_font =  wb.createFont();
			h_font.setBold(true);
			h_font.setFontHeightInPoints((short)10);
			   h_font.setFontName("TAHOMA"); 
			
			   dataright=wb.createCellStyle();
			   dataright.setFont(h_font);			
			dataright.setAlignment(HorizontalAlignment.RIGHT);
		} finally
		{
			
		}
		return dataright;
	}
}