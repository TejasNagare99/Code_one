package com.excel.utility;


import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.ss.usermodel.BorderStyle;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.DataFormat;
import org.apache.poi.ss.usermodel.FillPatternType;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.VerticalAlignment;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFFont;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class ApachePoiExcelFormat {

	public CellStyle SheetHeading(Workbook wwbook) throws Exception {

		CellStyle tableHeading = null;

		try {
			tableHeading = wwbook.createCellStyle();
			tableHeading
					.setFillForegroundColor(HSSFColor.HSSFColorPredefined.GREY_25_PERCENT.getIndex());
			tableHeading.setFillPattern(FillPatternType.SOLID_FOREGROUND);
			Font font = wwbook.createFont();
			font.setFontHeightInPoints((short) 13);
			font.setFontName("ARIAL");
			font.setColor(IndexedColors.BLACK.getIndex());
			font.setBold(true);
			tableHeading.setFont(font);
			tableHeading.setAlignment(HorizontalAlignment.CENTER);
			tableHeading.setVerticalAlignment(VerticalAlignment.CENTER);
		} catch (Exception e) {
			System.out.println("Error Occurred :" +e.getMessage());// uncomment asneeded --;
		}

		return tableHeading;
	}

	public CellStyle columnHeading(Workbook wwbook) throws Exception {

		CellStyle columnHeading = null;

		try {
			columnHeading = wwbook.createCellStyle();
			columnHeading.setFillForegroundColor(HSSFColor.HSSFColorPredefined.AQUA.getIndex());
			columnHeading.setFillPattern(FillPatternType.SOLID_FOREGROUND);
			Font font = wwbook.createFont();
			font.setFontHeightInPoints((short) 11);
			font.setFontName("ARIAL");
			font.setColor(IndexedColors.BLACK.getIndex());
			font.setBold(true);
			columnHeading.setFont(font);
			columnHeading.setBorderBottom(BorderStyle.THIN);
			columnHeading.setBorderLeft(BorderStyle.THIN);
			columnHeading.setBorderTop(BorderStyle.THIN);
			columnHeading.setBorderRight(BorderStyle.THIN);
			columnHeading.setAlignment(HorizontalAlignment.CENTER);
		} catch (Exception e) {
			System.out.println("Error Occurred :" +e.getMessage());// uncomment asneeded --;
		}

		return columnHeading;
	}

	public CellStyle dataRightAlignWthDecimal(Workbook wwbook) throws Exception {

		CellStyle rightAlignWthDecimal = null;

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
	public CellStyle dataRightAlignWth3Decimal(Workbook wwbook) throws Exception {

		CellStyle rightAlignWthDecimal = null;

		try {
			rightAlignWthDecimal = wwbook.createCellStyle();
			Font font = wwbook.createFont();
			DataFormat format = wwbook.createDataFormat();
			font.setFontHeightInPoints((short) 11);
			font.setFontName("ARIAL");
			rightAlignWthDecimal.setFont(font);
			rightAlignWthDecimal.setDataFormat(format.getFormat("0.000"));
			//rightAlignWthDecimal.setBorderBottom(CellStyle.BORDER_THIN);
			//rightAlignWthDecimal.setBorderLeft(CellStyle.BORDER_THIN);
			//rightAlignWthDecimal.setBorderTop(CellStyle.BORDER_THIN);
			//rightAlignWthDecimal.setBorderRight(CellStyle.BORDER_THIN);
			rightAlignWthDecimal.setAlignment(HorizontalAlignment.RIGHT);
		} catch (Exception e) {
			System.out.println("Error Occurred :" +e.getMessage());// uncomment asneeded --;
		}

		return rightAlignWthDecimal;
	}
	public CellStyle dataRightAlignWthoutDecimal(Workbook wwbook) throws Exception {

		CellStyle rightAlignWthDecimal = null;

		try {
			rightAlignWthDecimal = wwbook.createCellStyle();
			Font font = wwbook.createFont();
			font.setFontHeightInPoints((short) 11);
			font.setFontName("ARIAL");
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

	public CellStyle dataLeftAlign(Workbook wwbook) throws Exception {

		CellStyle leftAlign = null;

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
	}

	public CellStyle dataBoldLeftAlign(Workbook wwbook) throws Exception {

		CellStyle leftAlign = null;

		try {
			leftAlign = wwbook.createCellStyle();
			Font font = wwbook.createFont();
			font.setFontHeightInPoints((short) 11);
			font.setFontName("ARIAL");
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

	public CellStyle dataBoldRightAlign(Workbook wwbook) throws Exception {

		CellStyle leftAlign = null;

		try {
			leftAlign = wwbook.createCellStyle();
			Font font = wwbook.createFont();
			font.setFontHeightInPoints((short) 11);
			font.setFontName("ARIAL");
			font.setBold(true);
			leftAlign.setFont(font);
			leftAlign.setBorderBottom(BorderStyle.THIN);
			leftAlign.setBorderLeft(BorderStyle.THIN);
			leftAlign.setBorderTop(BorderStyle.THIN);
			leftAlign.setBorderRight(BorderStyle.THIN);
			leftAlign.setAlignment(HorizontalAlignment.RIGHT);
		} catch (Exception e) {
			System.out.println("Error Occurred :" +e.getMessage());// uncomment asneeded --;
		}

		return leftAlign;
	}

	public CellStyle dataBoldRightAlignWthDecimal(Workbook wwbook)
			throws Exception {

		CellStyle rightAlignWthDecimal = null;

		try {
			rightAlignWthDecimal = wwbook.createCellStyle();
			Font font = wwbook.createFont();
			DataFormat format = wwbook.createDataFormat();
			font.setFontHeightInPoints((short) 11);
			font.setFontName("ARIAL");
			font.setBold(true);
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

	// Method to create Excel report sub column heading (i.e : on change of some
	// item need to print the name)**********

	public CellStyle columnSubHeading(Workbook wwbook) throws Exception {

		CellStyle columnSubHeading = null;

		try {
			columnSubHeading = wwbook.createCellStyle();
			columnSubHeading
					.setFillForegroundColor(HSSFColor.HSSFColorPredefined.LIGHT_CORNFLOWER_BLUE.getIndex());
			columnSubHeading.setFillPattern(FillPatternType.SOLID_FOREGROUND);
			Font font = wwbook.createFont();
			font.setFontHeightInPoints((short) 11);
			font.setFontName("ARIAL");
			font.setColor(IndexedColors.BLACK.getIndex());
			font.setBold(true);
			columnSubHeading.setFont(font);
			columnSubHeading.setBorderBottom(BorderStyle.THIN);
			columnSubHeading.setBorderTop(BorderStyle.THIN);
			columnSubHeading.setAlignment(HorizontalAlignment.LEFT);
		} catch (Exception e) {
			System.out.println("Error Occurred :" +e.getMessage());// uncomment asneeded --;
		}

		return columnSubHeading;
	}

	public CellStyle columnSubHeadingRightAlgn(Workbook wwbook)
			throws Exception {

		CellStyle columnSubHeadingRight = null;

		try {
			columnSubHeadingRight = wwbook.createCellStyle();
			DataFormat format = wwbook.createDataFormat();
			columnSubHeadingRight
					.setFillForegroundColor(HSSFColor.HSSFColorPredefined.LIGHT_CORNFLOWER_BLUE.getIndex());
			columnSubHeadingRight.setFillPattern(FillPatternType.SOLID_FOREGROUND);
			Font font = wwbook.createFont();
			font.setFontHeightInPoints((short) 11);
			columnSubHeadingRight.setDataFormat(format.getFormat("0.00"));
			font.setFontName("ARIAL");
			font.setBold(true);
			//font.setBoldweight(Font.BOLDWEIGHT_BOLD);
			columnSubHeadingRight.setFont(font);
			columnSubHeadingRight.setBorderBottom(BorderStyle.THIN);
			columnSubHeadingRight.setBorderTop(BorderStyle.THIN);
			columnSubHeadingRight.setAlignment(HorizontalAlignment.RIGHT);
		} catch (Exception e) {
			System.out.println("Error Occurred :" +e.getMessage());// uncomment asneeded --;
		}

		return columnSubHeadingRight;
	}

			public CellStyle columnSubHeadingLemon(Workbook wwbook) throws Exception {

			CellStyle columnSubHeading = null;

			try {
				columnSubHeading = wwbook.createCellStyle();
				columnSubHeading
						.setFillForegroundColor(HSSFColor.HSSFColorPredefined.LEMON_CHIFFON.getIndex());
				columnSubHeading.setFillPattern(FillPatternType.SOLID_FOREGROUND);
				Font font = wwbook.createFont();
				font.setFontHeightInPoints((short) 11);
				font.setFontName("ARIAL");
				font.setColor(IndexedColors.BLACK.getIndex());
				font.setBold(true);
				columnSubHeading.setFont(font);
				columnSubHeading.setBorderBottom(BorderStyle.THIN);
				columnSubHeading.setBorderTop(BorderStyle.THIN);
				columnSubHeading.setAlignment(HorizontalAlignment.LEFT);
			} catch (Exception e) {
				System.out.println("Error Occurred :" +e.getMessage());// uncomment asneeded --;
			}

			return columnSubHeading;
		}
			
	// add new font 
			public CellStyle columnSubHeadingRed(Workbook wwbook) throws Exception {

				CellStyle columnSubHeading = null;

				try {
					columnSubHeading = wwbook.createCellStyle();
					columnSubHeading
							.setFillForegroundColor(HSSFColor.HSSFColorPredefined.RED.getIndex());
					columnSubHeading.setFillPattern(FillPatternType.FINE_DOTS.SOLID_FOREGROUND);
					Font font = wwbook.createFont();
					font.setFontHeightInPoints((short) 11);
					font.setFontName("ARIAL");
					font.setColor(IndexedColors.BLACK.getIndex());
					font.setBold(true);
					columnSubHeading.setFont(font);
					columnSubHeading.setBorderBottom(BorderStyle.THIN);
					columnSubHeading.setBorderTop(BorderStyle.THIN);
					columnSubHeading.setAlignment(HorizontalAlignment.LEFT);
				} catch (Exception e) {
					System.out.println("Error Occurred :" +e.getMessage());// uncomment asneeded --;
				}

				return columnSubHeading;
			}
			
			
		public CellStyle columnSubHeadingTurq(Workbook wwbook) throws Exception {

			CellStyle columnSubHeading = null;

			try {
				columnSubHeading = wwbook.createCellStyle();
				columnSubHeading
						.setFillForegroundColor(HSSFColor.HSSFColorPredefined.LIGHT_TURQUOISE.getIndex());
				columnSubHeading.setFillPattern(FillPatternType.SOLID_FOREGROUND);
				Font font = wwbook.createFont();
				font.setFontHeightInPoints((short) 11);
				font.setFontName("ARIAL");
				font.setColor(IndexedColors.BLACK.getIndex());
				font.setBold(true);
				columnSubHeading.setFont(font);
				columnSubHeading.setBorderBottom(BorderStyle.THIN);
				columnSubHeading.setBorderTop(BorderStyle.THIN);
				columnSubHeading.setAlignment(HorizontalAlignment.LEFT);
			} catch (Exception e) {
				System.out.println("Error Occurred :" +e.getMessage());// uncomment asneeded --;
			}

			return columnSubHeading;
		}
		public CellStyle columnSubHeadingGold(Workbook wwbook) throws Exception {

			CellStyle columnSubHeading = null;

			try {
				columnSubHeading = wwbook.createCellStyle();
				columnSubHeading
						.setFillForegroundColor(HSSFColor.HSSFColorPredefined.GOLD.getIndex());
				columnSubHeading.setFillPattern(FillPatternType.SOLID_FOREGROUND);
				Font font = wwbook.createFont();
				font.setFontHeightInPoints((short) 11);
				font.setFontName("ARIAL");
				font.setColor(IndexedColors.BLACK.getIndex());
				font.setBold(true);
				columnSubHeading.setFont(font);
				columnSubHeading.setBorderBottom(BorderStyle.THIN);
				columnSubHeading.setBorderTop(BorderStyle.THIN);
				columnSubHeading.setAlignment(HorizontalAlignment.LEFT);
			} catch (Exception e) {
				System.out.println("Error Occurred :" +e.getMessage());// uncomment asneeded --;
			}

			return columnSubHeading;
		}
		public CellStyle columnSubHeadingBlue(Workbook wwbook) throws Exception {

			CellStyle columnSubHeading = null;

			try {
				columnSubHeading = wwbook.createCellStyle();
				columnSubHeading
						.setFillForegroundColor(HSSFColor.HSSFColorPredefined.PALE_BLUE.getIndex());
				columnSubHeading.setFillPattern(FillPatternType.SOLID_FOREGROUND);
				Font font = wwbook.createFont();
				font.setFontHeightInPoints((short) 11);
				font.setFontName("ARIAL");
				font.setColor(IndexedColors.BLACK.getIndex());
				font.setBold(true);
				columnSubHeading.setFont(font);
				columnSubHeading.setBorderBottom(BorderStyle.THIN);
				columnSubHeading.setBorderTop(BorderStyle.THIN);
				columnSubHeading.setAlignment(HorizontalAlignment.LEFT);
			} catch (Exception e) {
				System.out.println("Error Occurred :" +e.getMessage());// uncomment asneeded --;
			}

			return columnSubHeading;
		}
		public CellStyle getCellstyle(XSSFWorkbook wb, boolean isHeader, boolean isDataHeader,
				boolean isNewDivision, boolean isDivisionTotal, boolean isReportTotal, boolean isNumber){

			CellStyle cs=wb.createCellStyle();
			XSSFFont font=wb.createFont();
			DataFormat format = wb.createDataFormat();
			//cs.setDataFormat(format.getFormat("0.00"));
			if(isHeader){
				font.setBold(true);
				cs.setFont(font);
				cs.setAlignment(HorizontalAlignment.CENTER);
				cs.setFillForegroundColor(HSSFColor.HSSFColorPredefined.GREY_25_PERCENT.getIndex());
				cs.setFillPattern(FillPatternType.SOLID_FOREGROUND);
			}
			if(isDataHeader){
				font.setBold(true);
				cs.setFont(font);
				cs.setAlignment(HorizontalAlignment.CENTER);
				cs.setVerticalAlignment(VerticalAlignment.CENTER);
			cs.setFillForegroundColor(HSSFColor.HSSFColorPredefined.AQUA.getIndex());
				cs.setFillPattern(FillPatternType.SOLID_FOREGROUND);
				cs.setBorderBottom(BorderStyle.THIN);
				cs.setBorderTop(BorderStyle.THIN);
				cs.setBorderRight(BorderStyle.THIN);
				cs.setBorderLeft(BorderStyle.THIN);
			}
			if(isNewDivision){
				font.setBold(true);
				cs.setFont(font);
				cs.setAlignment(HorizontalAlignment.LEFT);
				cs.setFillForegroundColor(HSSFColor.HSSFColorPredefined.LEMON_CHIFFON.getIndex());
				cs.setFillPattern(FillPatternType.SOLID_FOREGROUND);
			}
			if(isDivisionTotal){
				cs.setFont(font);
				cs.setFillForegroundColor(HSSFColor.HSSFColorPredefined.AQUA.getIndex());
				cs.setFillPattern(FillPatternType.SOLID_FOREGROUND);
				if(isNumber){
					cs.setDataFormat(format.getFormat("0.00"));
					cs.setAlignment(HorizontalAlignment.RIGHT);
				}else{
					cs.setAlignment(HorizontalAlignment.LEFT);
				}
			}
			if(isReportTotal){
				font.setBold(true);
				cs.setFont(font);
				cs.setFillForegroundColor(HSSFColor.HSSFColorPredefined.LIGHT_GREEN.getIndex());
				cs.setFillPattern(FillPatternType.SOLID_FOREGROUND);
				if(isNumber){
					cs.setDataFormat(format.getFormat("0.00"));
					cs.setAlignment(HorizontalAlignment.RIGHT);
				}else{
					cs.setAlignment(HorizontalAlignment.LEFT);
				}
			}if(isNumber){
				cs.setDataFormat(format.getFormat("0.00"));
				cs.setAlignment(HorizontalAlignment.RIGHT);
			}
			return cs;
		}

		public CellStyle columnDecimalHeading(Workbook wwbook) throws Exception {

			CellStyle columnSubHeading = null;

			try {
				columnSubHeading = wwbook.createCellStyle();
				columnSubHeading.setFillForegroundColor(HSSFColor.HSSFColorPredefined.LIGHT_CORNFLOWER_BLUE.getIndex());
				columnSubHeading.setFillPattern(FillPatternType.SOLID_FOREGROUND);
				Font font = wwbook.createFont();
				DataFormat format = wwbook.createDataFormat();
				font.setFontHeightInPoints((short) 11);
				font.setFontName("ARIAL");
				font.setColor(IndexedColors.BLACK.getIndex());
				font.setBold(true);
				columnSubHeading.setFont(font);
				columnSubHeading.setDataFormat(format.getFormat("0.00"));
				columnSubHeading.setBorderBottom(BorderStyle.THIN);
				columnSubHeading.setBorderTop(BorderStyle.THIN);
				columnSubHeading.setAlignment(HorizontalAlignment.RIGHT);
			} catch (Exception e) {
				System.out.println("Error Occurred :" +e.getMessage());// uncomment asneeded --;
			}

			return columnSubHeading;
		}
		
		public CellStyle dataRightAlign(Workbook wwbook) throws Exception {

			CellStyle leftAlign = null;

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
				leftAlign.setAlignment(HorizontalAlignment.RIGHT);
			} catch (Exception e) {
				System.out.println("Error Occurred :" +e.getMessage());// uncomment asneeded --;
			}

			return leftAlign;
		}
		public CellStyle dataLeftAlign_total(Workbook wwbook) throws Exception {

			CellStyle leftAlign = null;

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

		public CellStyle dataRightAlignWthoutDecimal_total(Workbook wwbook) throws Exception {

			CellStyle rightAlignWthDecimal = null;
		

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
		
}
