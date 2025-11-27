package com.excel.utility;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;

import java.util.Date;

public class Utility {

	public static String getdDateFormat(String date) {
		try {
			String oldFormat = "yyyy-MM-dd HH:mm:ss";
			String newFormat = "dd/MM/yyyy";

			SimpleDateFormat sdf1 = new SimpleDateFormat(oldFormat);
			SimpleDateFormat sdf2 = new SimpleDateFormat(newFormat);
			date = sdf2.format(sdf1.parse(date));
		} catch (ParseException pe) {
			System.out.println("Error Occurred :" + new CodifyErrors().getErrorCode(pe));// uncomment asneeded --
																							// System.out.println("Error
																							// Occurred :" + new
																							// CodifyErrors().getErrorCode(e));//
																							// uncomment asneeded --;
		}
		return date;
	}

	public static String getRptDateFormat(String date) {
		try {
			String oldFormat = "yyyy-MM-dd HH:mm:ss";
			String newFormat = "dd-MM-yyyy";

			SimpleDateFormat sdf1 = new SimpleDateFormat(oldFormat);
			SimpleDateFormat sdf2 = new SimpleDateFormat(newFormat);
			date = sdf2.format(sdf1.parse(date));
		} catch (ParseException pe) {
			System.out.println("Error Occurred :" + new CodifyErrors().getErrorCode(pe));// uncomment asneeded --
																							// System.out.println("Error
																							// Occurred :" + new
																							// CodifyErrors().getErrorCode(e));//
																							// uncomment asneeded --;
		}
		return date;
	}

	public String getGLCode(String postInd, String compCd, double invGrp, String tranType, double locId, double glAccId,
			double slAccId, double postGlAccId, double postSlAccId, double tranId, double divId, String year,
			String PeriodCd, double stock_point_id) {

		return compCd + Utility.paddedString(invGrp, 3) + year + PeriodCd + Utility.paddedString(locId, 3) + tranType
				+ postInd + Utility.paddedString(glAccId, 5) + Utility.paddedString(slAccId, 5)
				+ Utility.paddedString(postGlAccId, 5) + Utility.paddedString(postSlAccId, 5)
				+ Utility.paddedString(tranId, 5) + Utility.paddedString(stock_point_id, 3);
	}

	public static String getdDateFormatYYYYDDMM(String date) {
		try {
			String oldFormat = "dd/mm/yyyy";
			String newFormat = "yyyy-mm-DD";

			SimpleDateFormat sdf1 = new SimpleDateFormat(oldFormat);
			// System.out.println("Utility.getdDateFormatYYYYDDMM()"+
			// sdf1.parse(date));
			SimpleDateFormat sdf2 = new SimpleDateFormat(newFormat);
			date = sdf2.format(sdf1.parse(date));
		} catch (ParseException pe) {
			System.out.println("Error Occurred :" + new CodifyErrors().getErrorCode(pe));// uncomment asneeded --
																							// System.out.println("Error
																							// Occurred :" + new
																							// CodifyErrors().getErrorCode(e));//
																							// uncomment asneeded --;
		}
		return date;
	}

	public static String getdDateFormatDDMMYY(String date) {

		try {
			String oldFormat = "dd-mm-yyyy";
			String newFormat = "dd/MM/yyyy";

			SimpleDateFormat sdf1 = new SimpleDateFormat(oldFormat);
			// System.out.println("Utility.getdDateFormatYYYYDDMM()"+
			// sdf1.parse(date));
			SimpleDateFormat sdf2 = new SimpleDateFormat(newFormat);
			date = sdf2.format(sdf1.parse(date));
		} catch (ParseException pe) {
			System.out.println("Error Occurred :" + new CodifyErrors().getErrorCode(pe));// uncomment asneeded --
																							// System.out.println("Error
																							// Occurred :" + new
																							// CodifyErrors().getErrorCode(e));//
																							// uncomment asneeded --;
		}
		return date;
	}

	public static Date getDateformatHH(String dateStr) throws Exception {
		DateFormat formatter = new SimpleDateFormat("E MMM dd HH:mm:ss Z yyyy");
		Date date = formatter.parse(dateStr);
		System.out.println(date);
		return date;
	}

	public static Date getDateformatYYYYDDMMHH(String dateStr) throws Exception {
		DateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Date date = formatter.parse(dateStr);
		System.out.println(date);
		return date;
	}

	public static String paddedString(double value, int size) {
		return String.format("%0" + size + "d", (int) value);
	}

	public static Date convertStringtoDate(String strDate) {
		SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
		Date date = new Date();
		try {
			date = formatter.parse(strDate);
		} catch (ParseException e) {
			System.out.println("Error Occurred :" +e.getMessage());// uncomment asneeded --;
		}
		return date;
	}

	public static Date convertStringFormattoDate(String strDate) {
		SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy");
		Date date = new Date();
		try {
			date = formatter.parse(strDate);
		} catch (ParseException e) {
			System.out.println("Error Occurred :" +e.getMessage());// uncomment asneeded --;
		}
		return date;
	}

	public static String convertDatetoString(Date strDate) {
		SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
		String date = null;
		if (strDate != null)
			date = formatter.format(strDate);
		return date;
	}

	public static String convertDatetoStringYYYY_MM_DD(Date strDate) {
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy/MM/dd");
		String date = null;
		if (strDate != null)
			date = formatter.format(strDate);
		return date;
	}

	public static String convertSmpDatetoString(Date strDate) {
		SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy");
		String date = null;
		if (strDate != null)
			date = formatter.format(strDate);
		return date;
	}

	public static String convertStringtoStringDate(String strDate) throws ParseException {
		SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.S");
		Date date = dateFormat.parse(strDate);
		String date1 = formatter.format(date);
		return date1;
	}

	/*
	 * public Long[] getData(String data) {
	 * 
	 * int size = 0; int len = data.length(); if (len > 0) size = len / 4;
	 * System.out.println("Utility.getData()" + len);
	 * System.out.println("Utility.getData()" + size);
	 * 
	 * Long[] arrdata = new Long[size]; int k = 0; for (int i = 0; i < size; i++) {
	 * arrdata[i] = Long.parseLong(data.substring(k, k + 4));
	 * System.out.println(data.substring(k, k + 4)); k = k + 4; } return arrdata; }
	 */
	public String[] getMaxTranIdNoPlusOneForPayVouch(long tranId, String tranNo) {

		Long no = Long.parseLong(tranNo.substring(1));
		if (no < 10) {
			tranNo = "R000" + ++no;
		} else if (no < 100) {
			tranNo = "R00" + ++no;
		} else if (no < 1000) {
			tranNo = "R0" + ++no;
		} else {
			tranNo = "R" + ++no;
		}
		tranId = tranId + 1;
		String[] trans = new String[2];
		trans[0] = String.valueOf(tranId);
		trans[1] = tranNo;
		System.out.println("Utility.getMaxTranIdNoPlusOneForPayVouch():tran_no:" + tranNo);
		return trans;
	}

	DateFormat extended = new SimpleDateFormat("E MMM dd HH:mm:ss z yyyy");
	DateFormat simple = new SimpleDateFormat("dd/MM/yyyy");

	public Date reformat(String extendedString) throws ParseException {
		Date yourDate = extended.parse(extendedString);
		String simpleString = simple.format(yourDate);
		return simple.parse(simpleString);
	}

	public static BigDecimal getValueWithRoundOff(BigDecimal value) {
		BigDecimal totalSupplyValue = new BigDecimal(value.toString()).setScale(2, RoundingMode.HALF_EVEN);
		String tsv = totalSupplyValue.toString().substring(0, totalSupplyValue.toString().indexOf("."));
		BigDecimal newTsv = new BigDecimal(tsv).setScale(2, RoundingMode.HALF_EVEN);
		int roundOff = Integer.parseInt(totalSupplyValue.toString()
				.substring(totalSupplyValue.toString().indexOf(".") + 1, totalSupplyValue.toString().length()));
		if (roundOff > 50) {
			roundOff = 100 - roundOff;
			newTsv = newTsv.add(new BigDecimal(1));
		}
		return newTsv;
	}

	public static BigDecimal getRoundOff(double invAmount) {

		double roundOff = 0.0d;
		java.text.DecimalFormat df2 = new java.text.DecimalFormat("####.00");
		String num = df2.format(invAmount);
		String pattern = "\\.";
		float fraction = Float.parseFloat("." + num.split(pattern)[1]);
		if (fraction < .5) {
			roundOff = 0 - fraction;
		} else {
			roundOff = 1 - fraction;
		}
		return new BigDecimal(df2.format(roundOff));
	}

	public static double Round(double Rval, int Rpl) {
		double p = Math.pow(10, Rpl);
		Rval = Rval * p;
		double tmp = Math.round(Rval);
		return tmp / p;
	}

	public static void checkObject(Object obj, String objType, String dependsOn) throws Exception {
		if (obj == null) {
			throw new Exception(objType + " is not found for " + dependsOn);
		}
	}

	/**
	 * 
	 * 
	 * @author krishna
	 * @since 17-06-2015
	 * @param str The String to truncate
	 * @param max The maximum length
	 * @return The truncated String with specified limit of char
	 */
	public static String getTruncedString(String str, int max) {
		str = str.length() > max ? str.substring(0, max) : str;
		return str;
	}

	public static void main(String args[]) throws Exception {

		/*
		 * SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");
		 * System.out.println(convertDatetoString(new Date())); String d =
		 * format.format(new Date()); System.out.println(d); Date d1 = format.parse(d);
		 * System.out.println(d1.toString()); System.out.println("--"+
		 * convertStringtoStringDate( "2015-02-23 00:00:00.0"));
		 * 
		 * BigDecimal b1=new BigDecimal(1987.29); System.out.println(b1=b1.setScale(0,
		 * RoundingMode.HALF_UP)); b1=new BigDecimal(1987.29);
		 * System.out.println(b1=b1.setScale(0, RoundingMode.HALF_DOWN));
		 * System.out.println(Long.valueOf(b1.toString()));
		 */

		/*
		 * List<String> list = new ArrayList<>(); list.add("1"); list.add("2");
		 * list.add("3"); display(list.toArray());
		 * 
		 * List<Integer> olist = new ArrayList<>(); olist.add(1); olist.add(2);
		 * olist.add(3); display(olist.toArray());
		 * 
		 * 
		 * String[] sArr = {"A","B","C"}; display(sArr);
		 * 
		 * TreeSet<Integer> tList = new TreeSet<>(); tList.add(123); tList.add(122);
		 * tList.add(121); display(tList.toArray());
		 */

	}

	public static BigDecimal getRoundOffValue(BigDecimal value) {
		BigDecimal totalSupplyValue = new BigDecimal(value.toString()).setScale(2, RoundingMode.HALF_EVEN);
		String tsv = totalSupplyValue.toString().substring(0, totalSupplyValue.toString().indexOf("."));
		BigDecimal newTsv = new BigDecimal(tsv).setScale(2, RoundingMode.HALF_EVEN);
		int roundOff = Integer.parseInt(totalSupplyValue.toString()
				.substring(totalSupplyValue.toString().indexOf(".") + 1, totalSupplyValue.toString().length()));
		if (roundOff > 50) {
			roundOff = 100 - roundOff;
			newTsv = newTsv.add(new BigDecimal(1));
		}
		return new BigDecimal("0." + roundOff);
	}

	public static void display(Object[] list) {
		System.out.println("Printing Object Array...");
		for (int i = 0; i < list.length; i++) {
			System.out.println((i) + " :: " + list[i]);
		}
	}

	public static void display(String mapString) {
		// System.out.println(mapString);
		mapString = mapString.substring(1, mapString.length() - 1);
		// System.out.println(mapString);
		String[] a1 = mapString.split(",");
		for (int i = 0; i < a1.length; i++) {
			System.out.println((i) + " :: Key : " + a1[i].split("=")[0] + " Value : " + a1[i].split("=")[1]);
		}
	}

	public static String getDDMMYYYYToYYYYMMDD(String date) {
		String d[] = date.split("-");
		return d[2] + "-" + d[1] + "-" + d[0];
	}

	public static boolean isThisDateValid(String dateToValidate, String dateFromat) {
		if (dateToValidate == null) {
			return false;
		}
		SimpleDateFormat sdf = new SimpleDateFormat(dateFromat);
		sdf.setLenient(false);
		try {
			// if not valid, it will throw ParseException
			Date date = sdf.parse(dateToValidate);
//			System.out.println(date);
		} catch (ParseException e) {
			System.out.println("Error Occurred :" +e.getMessage());// uncomment asneeded --;
			return false;
		}

		return true;
	}

	public static String checkNull(Object inputStr) {
		String addempty = "";
		String finalStr = "";
		if (inputStr != null) {
			finalStr = inputStr.toString();
		} else {
			finalStr = addempty;
		}

		return finalStr;
	}

	public static String convertSmpDatetoString_(Date strDate) {
		SimpleDateFormat formatter = new SimpleDateFormat("d'th' MMMM yyyy");
		String date = null;
		if (strDate != null)
			date = formatter.format(strDate);
		return date;
	}

	public static String getdDateFormatDDMMYY_(String date) {

		try {
			String oldFormat = "dd/MM/yyyy";
			String newFormat = "dd-MM-yyyy";

			SimpleDateFormat sdf1 = new SimpleDateFormat(oldFormat);
			SimpleDateFormat sdf2 = new SimpleDateFormat(newFormat);
			date = sdf2.format(sdf1.parse(date));
		} catch (ParseException pe) {
			System.out.println("Error Occurred :" + new CodifyErrors().getErrorCode(pe));// uncomment asneeded --
																							// System.out.println("Error
																							// Occurred :" + new
																							// CodifyErrors().getErrorCode(e));//
																							// uncomment asneeded --;
		}
		return date;
	}
	
	public static String[] padCsvColumns(String[] columns, int requiredLength) {
	    String[] paddedColumns = new String[requiredLength];
	    
	    
	    for (int i = 0; i < paddedColumns.length; i++) {
	        if (i < columns.length) {
	            paddedColumns[i] = columns[i].trim();  
	        } else {
	            paddedColumns[i] = "";  
	        }
	    }
	    
	    return paddedColumns;
	}
}
