package com.excel.utility;

import java.io.FileWriter;
import java.io.IOException;
import java.util.AbstractMap;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.apache.poi.ss.usermodel.BorderStyle;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.ss.util.RegionUtil;
import org.apache.poi.xssf.usermodel.XSSFSheet;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public interface MedicoConstants {

	String APPLICATION_NAME = "Medico SG";
	String INV = "Invalid Transaction";
	String COMPANY_DATA = "COMPANY_DATA";
	String COMPANY_CODE = "COMPANY_CODE";
	String IS_LIVE = "IS_LIVE";
	String CFA_TO_STK_IND = "CFA_TO_STK_IND";
	
	String PFZ = "PFZ";
	String NIL = "NIL";
	String NHP = "NHP";
	String CURRENT = "CURRENT";
	String PREVIOUS = "PREVIOUS";

	String MOBILE_REST_API_PREFIX = "/mobile";

	String ROL = "ROL";
	String M = "M";
	String PARA_CODE_001 = "001";

	String NULL = "null";
	String NA = "NA";
	String PENDING_ALLOCATION_IND = "N";
	String SAMPLE_FLAG = "";
	String DIRECT_TO_PSO_IND = "N";
	// BPM Task Flow
	String BPM_LEVELWISE = "LEVEL";
	String BPM_AMOUNTWISE = "AMOUNT";
	String BPM_PERCENTAGE = "PERCENTAGE";
	String BPM_AMOUNT_LEVEL = "AMOUNT_LEVEL";
	String GRN_ = "GRN";

	String PENDING = "PENDING";
	String COMPLETED = "COMPLETED";

	String FIELD = "FIELD";
	String ROLE = "ROLE";

	// for saving forms
	String DATA_SAVED = "DATA_SAVED";
	String RESPONSE_MESSAGE = "RESPONSE_MESSAGE";
	String RESPONSE_ERROR_MESSAGE = "RESPONSE_ERROR_MESSAGE";
	String LOAD_ERROR_MESSAGE = "LOAD_ERROR_MESSAGE";
	String APPROVAL_MESSAGE = "Forwarded for Approvals";
	String RESPONSE_DISCARD_MESSAGE = "Discarded Successfully";

	String[] NORMAL_MONTHS = { "January", "February", "March", "April", "May", "June", "July", "August", "September",
			"October", "November", "December" };
	String SPECIAL_ALLOC_IMAGE = "D://sg/allocation//";
	String REPORT_FILE_PATH = "D://sg//reports//";
	String GRN_APPR_FILE_PATH = "D://sg//grn//";
	String PDF_FILE_PATH = "D://sg//pdf//";
	String UPLOADED_CSV = "D://sg//uploadedCsv//";
	String STK_WTH_FILE_PATH = "D://sg//stkwth//";
	String IAA_FILE_PATH = "D://sg//iaa//";
	String CRM_FILE_PATH = "D://sg//crm//";
	String QR_CODE_IMAGE_PATH = "D://sg//qr_code_image//";
	String BULK_UPLOADED_FILES = "D://sg//bulkUploadedExcel//";
	String ARTICLE_SCHEME_MASTER_UPLOADED_FILES = "D://sg//files//";
	String EINV_JSONS = "D://sg//einv_json//";
	String TERR_CSV_PATH = "D://sg/terrCsv//";
	String FSTAFF_CSV_PATH = "D://Sg/fstaffCSV//";


//	String SPECIAL_ALLOC_IMAGE="E://sg/allocation//";
//	String REPORT_FILE_PATH = "E://sg//reports//";
//	String GRN_APPR_FILE_PATH = "E://sg//grn//";
//	String PDF_FILE_PATH = "E://sg//pdf//";
//	String UPLOADED_CSV = "E://sg//uploadedCsv//";
//	String STK_WTH_FILE_PATH = "E://sg//stkwth//";
//	String IAA_FILE_PATH = "E://sg//iaa//";
//	String CRM_FILE_PATH = "E://sg//crm//";

	String APPROVAL_DECISION_PENDING = "PENDING";
	String APPROVAL_DECISION_APPROVED = "APPROVE";
	String APPROVAL_DECISION_DISCARDED = "DISCARD";
	String APPROVAL_DECISION_ESCALATED = "ESCALATE";

	String APPROVAL_TYPE_FIELDWISE = "FIELD";
	String APPROVAL_TYPE_USER = "USER";
	String APPROVAL_TYPE_GROUP = "GROUP";

	String ALIGN_CENTER = "CENTER";
	String ALIGN_LEFT = "LEFT";
	String ALIGN_RIGHT = "RIGHT";

	String A = "A";
	String Y = "Y";
	String N = "N";
	String E = "E";

	String GRN = "03";

	String ZERO = "ZERO";

	String ENTRY_MODE = "ENTRY";
	String MODIFY_MODE = "MODIFY";

	String SPRING_RESOURCE_HANDLER_REPORT_FILE_PATH_PREFIX = "/show-report";
	String SPRING_RESOURCE_HANDLER_PDF_FILE_PATH_PREFIX = "/show-pdf";
	String Dispatch_Approval = "DISPATCH";
	String DISPATCH_APPR_TRAN = "15";
	// Approval Type
	String GRN_APPR_PFZ_PS = "01";
	String GRN_APPR_PFZ_PI = "02";
	String GRN_APPR_PIPL_PS = "03";
	String GRN_APPR_PIPL_PI = "04";
	String DSP_APPR_PFZ = "05";
	String DSP_APPR_PIPL = "06";
	String ALLOC_APPR_ALL_PFZ = "07";
	String ALLOC_APPR_ALL_PIPL = "08";
	String IAA_APPR_PFZ = "09";
	String IAA_APPR_PIPL = "10";
	String SPECIAL_APPR_PFZ = "11";
	String SPECIAL_APPR_PIPL = "12";
	String STK_WTH_APPR_PFZ = "13";
	String STK_WTH_APPR_PIPL = "14";
	String ANNUAL_APPR_PFZ = "15";
	String ANNUAL_APPR_PIPL = "16";
	String ANNUAL_APPR_TEAM_PFZ = "17";
	String ANNUAL_APPR_TEAM_PIPL = "18";
	String MONTHLY_APPR_TEAM_PFZ = "19";
	String MONTHLY_APPR_TEAM_PIPL = "20";

	String DBLK_APPR_PFZ = "21";
	String DBLK_APPR_PIPL = "22";

	// stockist bulk allocation
	String STK_BLK_APPR_PFZ = "23";
	String STK_BLK_APPR_PIPL = "24";

	// new approvals location wise

	// ahmedabad
	String AM_GRN_APPR_ALL_PFZ = "25";
	String AM_DSP_APPR_ALL_PFZ = "27";
	String AM_IAA_APPR_ALL_PFZ = "28";
	String AM_STK_APPR_ALL_PFZ = "29";

	String D = "D";

	String MONTHLY_ALLOCATION = "M";
	String MONTHLY_TEAM_DOC_TYPE = "XM";
	String ADDITIONAL_TEAM_DOC_TYPE = "XA";
	String ADDITIONAL_ALLOCATION = "A";
	String ANNUAL_ALLOCATION = "N";
	String ANNUAL_TEAM_DOC_TYPE = "XNT";
	String ANNUAL_COMPANY_DOC_TYPE = "XNC";
	String UNIQUE_NUMBER = "UNIQUE_NUMBER";
	String NS = "NS";
	String SA = "SA";
	String STOCK_TYPE_07 = "07";
	String ROLE_MSR = "FLDSF";
	String ROLE_DEMO = "DEMO";
	String ROLE_WAREH = "WAREH";
	String ROLE_WARSU = "WARSU";
	String ROLE_DISTR = "DISTR";
	String ROLE_QA = "QA";
	String ROLE_MARKN = "MARKN";
	String ROLE_MKTG = "MKTG";
	String ROLE_ADM = "ADM";
	String ROLE_TRA = "TRA";
	String ROLE_COUR = "COUR";
	String ROLE_CDIST = "CDIST";
	String ROLE_BRANDMGR = "BRMGR";
	String ROLE_COURIER = "COUR";
	String LOGQUEST = "LOGIN_QUESTIONS";
	String STK_TRF_SA = "85";
	String STK_TRF_NS = "86";
	String DISPATCH = "10";
	String STKADJ = "STADJ";
	String ERRORMSG = "ERRORMSG";
	String SALEABLE = "01";
	String GRN_EXCESS = "10";
	String QUARANTINE = "13";
	String BWT = "BWT";
	String IAA = "IAA";
	String USER_LOCATION = "USER_LOCATION";
	String VET = "VET";
	String PAL = "PAL";
	String PG = "P&G";
	String ROLE_MARSU = "MARSU";

	String MAHARASHTRA = "MAHARASHTRA";

	String DSPREPORTCC = "DSPREPORTCC";
	// String approvalLink="https://www.sampro-pfizerindia.com:R/ng-sg/";
	// String localLink = "http://www.sampro-pfizerindia.com:8100/ng-sg/";
	String PAREKH_LIVE = "jdbc:sqlserver://180.149.244.177:1544;databaseName=PFIZER_SG_JAVAVER_GST_PAREKH_LIVE";
	String DATA_RECIEVED = "DATA_RECIEVED";
	String APPROVAL_LINK = "APPROVAL_LINK";

	String ACCEPT_OTP = "ACCEPT_OTP";
	String ACCEPT_CAPTCHA = "ACCEPT_CAPTCHA";

	String DOCTOR_CLASS = "DOCTOR_CLASS_CLS";
	String SCHEME_TYPE = "SCHEME_TYPE_SCY";

	List<Long> CFA_LINKAGE = Arrays.asList(1L, 3L);
	List<Long> PFZ_LOC = Arrays.asList(1L, 13L);
	List<Long> PIPL_LOC = Arrays.asList(2L,9L, 14L);

	Map<Long, String> grn_loc_wise_approval_map = Stream.of(
			// BHIWANDI PFZ LOCIDS
			new AbstractMap.SimpleImmutableEntry<>(1L, MedicoConstants.GRN_APPR_PFZ_PS),
			new AbstractMap.SimpleImmutableEntry<>(13L, MedicoConstants.GRN_APPR_PFZ_PS),

			// BHIWANDI PIPL LOCIDS
			new AbstractMap.SimpleImmutableEntry<>(9L, MedicoConstants.GRN_APPR_PIPL_PS),
			new AbstractMap.SimpleImmutableEntry<>(14L, MedicoConstants.GRN_APPR_PIPL_PS),

			// AHMEDABAD PF
			new AbstractMap.SimpleImmutableEntry<>(11L, MedicoConstants.AM_GRN_APPR_ALL_PFZ),

			// AHMEDABAD PI
			new AbstractMap.SimpleImmutableEntry<>(12L, ""))
			.collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));

	List<String> DISPATCH_TYPE = Arrays.asList("E", "T", "D");
	List<NotificationType> NOTIFICATION_TYPES_005 = Arrays.asList(new NotificationType("0", "All"),
			new NotificationType(INotificationTypes.REMINDERS, INotificationTypes.NOTIFICATION_TYPE_REMINDER));
	List<NotificationType> NOTIFICATION_TYPES_MANAGERS = Arrays.asList(new NotificationType("0", "All"));
	List<NotificationType> NOTIFICATION_TYPES_MANAGERS_003_002_001_000 = Arrays
			.asList(new NotificationType("0", "All"));

	public interface INotificationTypes {
		String REMINDERS = "RMD";
		String NOTIFICATION_TYPE_REMINDER = "Reminder";
	}

	public class NotificationType {
		private String key;
		private String value;

		public String getValue() {
			return value;
		}

		public void setValue(String value) {
			this.value = value;
		}

		public String getKey() {
			return key;
		}

		public void setKey(String key) {
			this.key = key;
		}

		public NotificationType(String key, String value) {
			super();
			this.key = key;
			this.value = value;
		}
	}

	// stockist bulk related data
	Long PFZ_REQUESTOR_ID_FOR_STK_BLK_UPLD_FSTAFF = 40894L;
	Long PFZ_DIV_ID_FOR_STOCKIST_BULK_UPLD_FSTAFF = 33L;

	// approval codes
	String GRN_FULL_TRAN_NAME = "GRN";
	String DISPATCH_FULL_TRAN_NAME = "DSP";
	String ALLOC_TRAN_NAME = "ALC";
	String IAA_TRAN_NAME = "IAA";
	String SPLALLOC = "SAD";
	String STOCK_WITHDRAW = "STW";
	String ART_DEL_REQUEST = "ASR";
	String ANN_SAMPLE_PLAN = "ASP";
	String ASP_TEAM_CONSOLIDATION = "CON";
	String SCM_MST_APPR = "SCM";
	String SCM_MST_VLD_EXT = "EXV";

	// caches
	String dashboard_graph_cache = "dashboard_graph";

	// tran name descriptions
	String GRN_field = "GRN";
	String DSP_field = "Dispatch";
	String IAA_field = "IAA";
	String STW_field = "Stock Withdrawal";
	String ASR_field = "Article Delivery Request";

	Map<String, String> tran_type_display_names_map = Stream
			.of(new AbstractMap.SimpleImmutableEntry<>("GRN", "GRN"),
					new AbstractMap.SimpleImmutableEntry<>("DSP", "Dispatch"),
					new AbstractMap.SimpleImmutableEntry<>("STW", "Stock Withdrawal"),
					new AbstractMap.SimpleImmutableEntry<>("IAA", "IAA"),
					new AbstractMap.SimpleImmutableEntry<>("ASR", "Article Delivery Request"))
			.collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));

	// ZERO VALUE EINV/CHLN PRINT stockist app - PARAMETER FIELD NAMES
	String ZERO_VAL_EINV_AND_DEL_CHLN = "VALUE_ZERO_IN_DISPINV_QRCODE";

	public static void saveToJson(String filename, String jsonString) {
		try (FileWriter fileWriter = new FileWriter(EINV_JSONS + filename)) {
			fileWriter.write(jsonString);
			System.out.println("Successfully saved JSON data to file.");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static void saveReqResToJson(String filename, Object requObj, Object respObj) {
		// Create a Gson instance with pretty printing enabled
		Gson gson = new GsonBuilder().setPrettyPrinting().create();

		try (FileWriter file = new FileWriter(EINV_JSONS + "requ" + filename)) {
			gson.toJson(requObj, file);
			System.out.println("Successfully saved requJSON data to file.");
		} catch (IOException e) {
			e.printStackTrace();
		}

		try (FileWriter file = new FileWriter(EINV_JSONS + "resp" + filename)) {
			gson.toJson(respObj, file);
			System.out.println("Successfully saved respJSON data to file.");
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	// to camel case
	public static String toCamelCase(String input) {
		// Split the input string by spaces
		String[] words = input.split(" ");

		// Initialize a StringBuilder to build the camel case string
		StringBuilder camelCaseString = new StringBuilder();

		for (int i = 0; i < words.length; i++) {
			// Capitalize the first letter of each word
			camelCaseString.append(capitalizeFirstLetter(words[i]));
			if (i < words.length - 1) {
				// Add a space between words
				camelCaseString.append(" ");
			}
		}

		return camelCaseString.toString();
	}

	public static String capitalizeFirstLetter(String word) {
		if (word == null || word.isEmpty()) {
			return word;
		}
		return Character.toUpperCase(word.charAt(0)) + word.substring(1).toLowerCase();
	}

	public static void addOuterBorderToMerged_XSSF(XSSFSheet sheet, int skipFromStart,
			int skipFromEnd) {
		List<CellRangeAddress> mergedRegions = sheet.getMergedRegions();
		for (int i = skipFromStart; i < mergedRegions.size()-skipFromEnd; i++) {
			RegionUtil.setBorderTop(BorderStyle.THIN, mergedRegions.get(i), sheet);
			RegionUtil.setBorderLeft(BorderStyle.THIN, mergedRegions.get(i), sheet);
			RegionUtil.setBorderRight(BorderStyle.THIN, mergedRegions.get(i), sheet);
			RegionUtil.setBorderBottom(BorderStyle.THIN, mergedRegions.get(i), sheet);
		}
	}

}
