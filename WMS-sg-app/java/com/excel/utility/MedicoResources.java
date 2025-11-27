package com.excel.utility;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Hashtable;
import java.util.List;
import java.util.Locale;

import javax.imageio.ImageIO;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;
import com.google.zxing.qrcode.decoder.ErrorCorrectionLevel;

public class MedicoResources implements MedicoConstants {
	
	public static String convert_DD_MM_YYYY2(String date) throws ParseException {

		SimpleDateFormat inputDateFormat = new SimpleDateFormat("yyyy-MM-dd");
		Date date1 = inputDateFormat.parse(date);

		// Create a SimpleDateFormat object for the output format
		SimpleDateFormat outputDateFormat = new SimpleDateFormat("dd-MM-yyyy");

		// Format the Date object to the desired output format
		String formattedDateTime = outputDateFormat.format(date1);

		return formattedDateTime;
	}

	public static void generateImage(String qrCode, String imagePath, String fileName)
			throws WriterException, IOException {
		imagePath = imagePath + fileName;
		File file = new File(QR_CODE_IMAGE_PATH);
		try {
			if (!file.exists()) {
				if (file.mkdir()) {
				} else {
					throw new RuntimeException("Could not create directory");
				}
			}
		} catch (Exception e) {
			throw new RuntimeException();
		}
		String qrCodeText = qrCode;
		String filePath = imagePath;// "F:\\JD1.jpg"
		int size = 1250;
		String fileType = "jpg";
		File qrFile = new File(filePath);
		createQRImage(qrFile, qrCodeText, size, fileType);
		System.out.println("DONE");
	}
	
	public static String convert_DDMMYYYY(String date) throws ParseException {

		SimpleDateFormat inputDateFormat = new SimpleDateFormat("yyyy-MM-dd");
		Date date1 = inputDateFormat.parse(date);

		// Create a SimpleDateFormat object for the output format
		SimpleDateFormat outputDateFormat = new SimpleDateFormat("dd/MM/yyyy");

		// Format the Date object to the desired output format
		String formattedDateTime = outputDateFormat.format(date1);

		return formattedDateTime;
	}
	
	public static String convert_DD_MM_YYYY(String date) throws ParseException {

		SimpleDateFormat inputDateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");
		Date date1 = inputDateFormat.parse(date);

		// Create a SimpleDateFormat object for the output format
		SimpleDateFormat outputDateFormat = new SimpleDateFormat("yyyy-MM-dd");

		// Format the Date object to the desired output format
		String formattedDateTime = outputDateFormat.format(date1);

		return formattedDateTime;
	}

	private static void createQRImage(File qrFile, String qrCodeText, int size, String fileType)
			throws WriterException, IOException {
		// Create the ByteMatrix for the QR-Code that encodes the given String
		Hashtable<EncodeHintType, ErrorCorrectionLevel> hintMap = new Hashtable<>();
		hintMap.put(EncodeHintType.ERROR_CORRECTION, ErrorCorrectionLevel.L);
		QRCodeWriter qrCodeWriter = new QRCodeWriter();
		BitMatrix byteMatrix = qrCodeWriter.encode(qrCodeText, BarcodeFormat.QR_CODE, size, size, hintMap);
		// Make the BufferedImage that are to hold the QRCode
		int matrixWidth = byteMatrix.getWidth();
		BufferedImage image = new BufferedImage(matrixWidth, matrixWidth, BufferedImage.TYPE_INT_RGB);
		image.createGraphics();

		Graphics2D graphics = (Graphics2D) image.getGraphics();
		graphics.setColor(Color.WHITE);
		graphics.fillRect(0, 0, matrixWidth, matrixWidth);
		// Paint and save the image using the ByteMatrix
		graphics.setColor(Color.BLACK);

		for (int i = 0; i < matrixWidth; i++) {
			for (int j = 0; j < matrixWidth; j++) {
				if (byteMatrix.get(i, j)) {
					graphics.fillRect(i, j, 1, 1);
				}
			}
		}
		ImageIO.write(image, fileType, qrFile);
	}

	public static List<Long> splitUserDivisions(String divisions) {
		int size = divisions.length() / 4;
		int i = 0, len = 0;
		List<Long> list = new ArrayList<Long>();
		while (i < size) {
			list.add(Long.parseLong(divisions.substring(len, len + 4)));
			i++;
			len += 4;
		}
		return list;
	}
	
	public static Date convert_YYYY_DD_MM_toDate(String date) throws ParseException {
		DateFormat df = new SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH);
		return df.parse(date);
	}
	
	public static Date convert_DD_MM_YYYY_HH_MM_toDate(String date) throws ParseException {
	    DateFormat df = new SimpleDateFormat("dd/MM/yyyy HH:mm", Locale.ENGLISH);
	    return df.parse(date);
	}

	public static String convertUtilDateToString_YYYY_MM_DD_NEW_(Date date) {
		DateFormat df = new SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH);
		return df.format(date);
	}

	public static String convertUtilDateToString_DD_MM_YYYY(Date date) {
		DateFormat df = new SimpleDateFormat("dd/MM/yyyy", Locale.ENGLISH);
		return df.format(date);
	}

	public static String convertUtilDateToString(Date date) {
		DateFormat df = new SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH);
		return df.format(date);
	}

	public static Date convert_DD_MM_YYYY_toDate(String date) throws ParseException {
		DateFormat df = new SimpleDateFormat("dd/MM/yyyy", Locale.ENGLISH);
		return df.parse(date);
	}

	public static Date getDateformatYYYYDDMMHH(String dateStr) throws Exception {
		DateFormat formatter = new SimpleDateFormat("dd-MM-yyyy");
		Date date = null;
		if (dateStr != null && dateStr.trim().length() > 0)
			date = formatter.parse(dateStr);
		return date;
	}
	
	public static Date getDateformatYMD(String dateStr) throws Exception {
		DateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
		Date date = null;
		if (dateStr != null && dateStr.trim().length() > 0)
			date = formatter.parse(dateStr);
		return date;
	}
	


	public static Long getLevelofApproval(String levelCode) {
		Long lvlApp = 0L;
		if (levelCode.equalsIgnoreCase("005"))
			lvlApp = 0L;
		else if (levelCode.equalsIgnoreCase("004"))
			lvlApp = 1L;
		else if (levelCode.equalsIgnoreCase("003"))
			lvlApp = 2L;
		else if (levelCode.equalsIgnoreCase("002"))
			lvlApp = 3L;
		else if (levelCode.equalsIgnoreCase("001"))
			lvlApp = 4L;
		return lvlApp;
	}

	public static Date atStartOfDay(Date date) {
		LocalDateTime localDateTime = dateToLocalDateTime(date);
		LocalDateTime startOfDay = localDateTime.with(LocalTime.MIN);
		return localDateTimeToDate(startOfDay);
	}

	public static Date atEndOfDay(Date date) {
		LocalDateTime localDateTime = dateToLocalDateTime(date);
		LocalDateTime endOfDay = localDateTime.with(LocalTime.MAX);
		return localDateTimeToDate(endOfDay);
	}

	private static LocalDateTime dateToLocalDateTime(Date date) {
		return LocalDateTime.ofInstant(date.toInstant(), ZoneId.systemDefault());
	}

	private static Date localDateTimeToDate(LocalDateTime localDateTime) {
		return Date.from(localDateTime.atZone(ZoneId.systemDefault()).toInstant());
	}

	public static LocalDate dateToLocalDate(Date date) {
		return date.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
	}

	public static Date localDateToDate(LocalDate localDate) {
		return Date.from(localDate.atStartOfDay(ZoneId.systemDefault()).toInstant());
	}

	public static Date getMaximumDateByCurrentDateOfMonth(Date date) {
		Calendar c = Calendar.getInstance();
		c.setTime(date);
		c.set(Calendar.DAY_OF_MONTH, c.getActualMaximum(Calendar.DAY_OF_MONTH));
		return c.getTime();
	}

	public static String convertMilisecondsToUtilStrinDate(Long miliseconds) {
		DateFormat simple = new SimpleDateFormat("yyyy-mm-dd HH:mm:ss:SSS");
		Date result = new Date(miliseconds);
		return simple.format(result);
	}

	public static Date convertMilisecondsToUtilDate(Long miliseconds) {
		// DateFormat simple = new SimpleDateFormat("yyyy-mm-dd HH:mm:ss:SSS");
		Date result = new Date(miliseconds);
		return result;
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

	public static String getRptDateFormatddMMyyyy(String date) {
		try {
			String oldFormat = "yyyy-MM-dd";
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

	public static String convertUtilDateToString_DD_MM_YYYY_(Date date) {
		DateFormat df = new SimpleDateFormat("dd-MM-yyyy", Locale.ENGLISH);
		return df.format(date);
	}
	

	public static String convertUtilDateToString_YYYY_MM_DD_(Date date) {
		DateFormat df = new SimpleDateFormat("yyyy/MM/dd", Locale.ENGLISH);
		return df.format(date);
	}

	public static String convertUtilDateToString_YYYY_MM_DD(Date date) {
		DateFormat df = new SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH);
		return df.format(date);
	}

	public static Date convert_YYYY_MM_DD_HH_MM_SS_toDate(String date) throws ParseException {
		DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.ENGLISH);
		return df.parse(date);
	}

	public static Date convert_YYYY_MM_DD_HH_MM_SS_toDate_(String date) throws ParseException {
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		return df.parse(date);
	}

	public static String convertUtilDateToString_YYYY_MM_DD_HH_MM_SS(Date date) {
		DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.ENGLISH);
		return df.format(date);
	}

	public static String convert_DD_MM_YYYY_toDateAndTime(String date) throws ParseException {

		SimpleDateFormat inputDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");
		Date date1 = inputDateFormat.parse(date);

		// Create a SimpleDateFormat object for the output format
		SimpleDateFormat outputDateFormat = new SimpleDateFormat("dd/MM/yyyy hh:mm:ss a");

		// Format the Date object to the desired output format
		String formattedDateTime = outputDateFormat.format(date1);

		return formattedDateTime;
	}
//	public static Long getTourPlanProcessIdByLevelCode(String levelCode) {
//		if (levelCode.trim().equalsIgnoreCase(LEVEL_CODE_005)) {
//			return PROCESS_ID_TOUR_PLAN_APPROVAL_005;
//		} else if (levelCode.trim().equalsIgnoreCase(LEVEL_CODE_004)) {
//			return PROCESS_ID_TOUR_PLAN_APPROVAL_004;
//		} else if (levelCode.trim().equalsIgnoreCase(LEVEL_CODE_003)) {
//			return PROCESS_ID_TOUR_PLAN_APPROVAL_003;
//		} else if (levelCode.trim().equalsIgnoreCase(LEVEL_CODE_002)) {
//			return PROCESS_ID_TOUR_PLAN_APPROVAL_002;
//		} else if (levelCode.trim().equalsIgnoreCase(LEVEL_CODE_001)) {
//			return PROCESS_ID_TOUR_PLAN_APPROVAL_001;
//		}
//		return null;
//	}
//	
//	public static Long getLeaveApplicationProcessIdByLevelCode(String levelCode) {
//		if (levelCode.trim().equalsIgnoreCase(LEVEL_CODE_005)) {
//			return PROCESS_ID_LEAVE_APPLICATION_APPROVAL_005;
//		} else if (levelCode.trim().equalsIgnoreCase(LEVEL_CODE_004)) {
//			return PROCESS_ID_LEAVE_APPLICATION_APPROVAL_004;
//		} else if (levelCode.trim().equalsIgnoreCase(LEVEL_CODE_003)) {
//			return PROCESS_ID_LEAVE_APPLICATION_APPROVAL_003;
//		} else if (levelCode.trim().equalsIgnoreCase(LEVEL_CODE_002)) {
//			return PROCESS_ID_LEAVE_APPLICATION_APPROVAL_002;
//		} else if (levelCode.trim().equalsIgnoreCase(LEVEL_CODE_001)) {
//			return PROCESS_ID_LEAVE_APPLICATION_APPROVAL_001;
//		}
//		return null;
//	}
//	
//	public static Long getLeaveCancellationProcessIdByLevelCode(String levelCode) {
//		if (levelCode.trim().equalsIgnoreCase(LEVEL_CODE_005)) {
//			return PROCESS_ID_LEAVE_CANCELLATION_APPROVAL_005;
//		} else if (levelCode.trim().equalsIgnoreCase(LEVEL_CODE_004)) {
//			return PROCESS_ID_LEAVE_CANCELLATION_APPROVAL_004;
//		} else if (levelCode.trim().equalsIgnoreCase(LEVEL_CODE_003)) {
//			return PROCESS_ID_LEAVE_CANCELLATION_APPROVAL_003;
//		} else if (levelCode.trim().equalsIgnoreCase(LEVEL_CODE_002)) {
//			return PROCESS_ID_LEAVE_CANCELLATION_APPROVAL_002;
//		} else if (levelCode.trim().equalsIgnoreCase(LEVEL_CODE_001)) {
//			return PROCESS_ID_LEAVE_CANCELLATION_APPROVAL_001;
//		}
//		return null;
//	}
//	
//	public static Long getWeeklyBackdatedEntryProcessIdByLevelCode(String levelCode) {
//		if (levelCode.trim().equalsIgnoreCase(LEVEL_CODE_004)) {
//			return PROCESS_ID_WEEKLY_BACKDATED_ENTRY_APPROVAL_004;
//		} else if (levelCode.trim().equalsIgnoreCase(LEVEL_CODE_003)) {
//			return PROCESS_ID_WEEKLY_BACKDATED_ENTRY_APPROVAL_003;
//		}
//		return null;
//	}

	public static Date convert_YYYY_MM_DD_toDate_(String date) throws ParseException {
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
		return df.parse(date);
	}

	public static Date convert_DD_MM_YYYY_toDate_(String date) throws ParseException {
		SimpleDateFormat df = new SimpleDateFormat("dd/MM/yyyy");
		return df.parse(date);
	}

	public static String getRptDateFormat_(String date) {
		try {
			String oldFormat = "yyyy-MM-dd HH:mm:ss";
			String newFormat = "yyyy-MM-dd";

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

	public static String convert_DD_MM_YYYY_toDateAndTime_(String date) throws ParseException {

		SimpleDateFormat parser = new SimpleDateFormat("EEE MMM d HH:mm:ss zzz yyyy");
		Date date1 = parser.parse(date);
		SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
		String formattedDateTime = formatter.format(date1);

		return formattedDateTime;
	}

	public static String getRptDateFormat__(String date) {
		try {
			String oldFormat = "yyyy-MM-dd HH:mm:ss";
			String newFormat = "dd/MM/yyyy";

			SimpleDateFormat sdf1 = new SimpleDateFormat(oldFormat);
			SimpleDateFormat sdf2 = new SimpleDateFormat(newFormat);
			date = sdf2.format(sdf1.parse(date));
		} catch (ParseException pe) {
			pe.printStackTrace();
			System.out.println("Error Occurred :" + new CodifyErrors().getErrorCode(pe));// uncomment asneeded --
																							// System.out.println("Error
																							// Occurred :" + new
																							// CodifyErrors().getErrorCode(e));//
																							// uncomment asneeded --;
		}
		return date;
	}

}
