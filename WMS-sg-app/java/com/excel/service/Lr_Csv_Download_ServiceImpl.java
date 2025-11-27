package com.excel.service;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.excel.model.LrcsvDownload;
import com.excel.model.LrcsvDownloadReport;
import com.excel.model.LrcsvDownloadReportRevised;
import com.excel.model.Lrcsv_RevisedDownload;
import com.excel.model.Lrcsv_RevisedDownload_SG;
import com.excel.model.Usermaster;
import com.excel.repository.UserMasterRepository;
import com.excel.security.PasswordManager;
import com.excel.utility.CodifyErrors;
import com.excel.utility.MedicoConstants;
import com.excel.utility.Utility;
import com.opencsv.CSVWriter;

import net.lingala.zip4j.core.ZipFile;
import net.lingala.zip4j.model.ZipParameters;
import net.lingala.zip4j.util.Zip4jConstants;

@Service
public class Lr_Csv_Download_ServiceImpl implements Lr_Csv_Download_Service, MedicoConstants {

	@Autowired
	UserMasterRepository usermasterrepository;
	@Autowired
	private UserMasterService usermasterservice;
	@Autowired
	private ParameterService parameterService;

	@Override
	public String Generate_Lr_CSV(List<LrcsvDownload> lst) throws Exception {

		SimpleDateFormat df = new SimpleDateFormat("dd/MM/yyyy");

		CSVWriter writer = null;
		File ff = null;
		StringBuffer path = null;
		String fileName = "";
		try {

			fileName = "LrDetail" + new Date().getTime() + ".csv";
			String filePath = MedicoConstants.REPORT_FILE_PATH;
			path = new StringBuffer(filePath).append("\\");
			path.append(fileName);
			ff = new File(path.toString());
			System.out.println("filename " + fileName);
			File file = new File(filePath);

			try {
				if (!file.exists()) {
					if (file.mkdirs()) {
					} else {
						throw new RuntimeException("Could not create directory");
					}
				}
			} catch (Exception e) {
				throw new RuntimeException();
			}

			FileWriter fwriter = new FileWriter(path.toString());
			writer = new CSVWriter(fwriter, ',', CSVWriter.NO_QUOTE_CHARACTER);
			String[] header = new String[31];
			header[0] = "Summary Header ID";
			header[1] = "Team Name";
			header[2] = "Team code";
			header[3] = "Master STN No.";
			header[4] = "Date of STN";
			header[5] = "Destination Location";
			header[6] = "STN Value";
			header[7] = "Master L.R./Airway No.";
			header[8] = "Master L.R./Airway date";
			header[9] = "Master STN Transporter";
			header[10] = "Weight in Kgs";
			header[11] = "Billable Weight in Kgs";
			header[12] = "Total No. of cases";
			header[13] = "Employee No.";
			header[14] = "Employee Name";
			header[15] = "Individual Challan No.";
			header[16] = "Date of Individual Challan";
			header[17] = "City";
			header[18] = "Transporter";
			header[19] = "L.R. No. Individual challan";
			header[20] = "Date";
			header[21] = "Weight in Kgs";
			header[22] = "Billable Weight in Kgs";
			header[23] = "Total No. of cases";

			// Added Later
			header[24] = "MST DELIVERY DATE";
			header[25] = "MST RECEIVED BY";
			header[26] = "MST REMARK";

			header[27] = "DTL DELIVERY DATE";
			header[28] = "DTL RECEIVED BY";
			header[29] = "WAY BILL NO";
			header[30] = "DTL REMARK";
			/*
			 * header[30] = "ADDRESS 1"; header[31] = "ADDRESS 2"; header[32] = "ADDRESS 3";
			 * header[33] = "ADDRESS 4"; header[34] = "ADDRESS 5"; header[35] = "MOBILE NO";
			 * header[36] = "E-MAIL ID"; header[37] = "REMARKS"; header[38] =
			 * "PSO RESIGNED/STOCK RETURNED DETAILS";
			 */

			List<String[]> li = new ArrayList<String[]>();
			li.add(header);
			System.out.println("Number of recs " + lst.size());
			for (LrcsvDownload lrup : lst) {
				String[] arr = new String[31];
				arr[0] = lrup.getSum_hd_id();
				arr[1] = lrup.getTeam_name();
				arr[2] = lrup.getTeam_code();
				arr[3] = lrup.getMst_stn_no();
				arr[4] = lrup.getMst_stn_dt() != null ? df.format(lrup.getMst_stn_dt()) : "";
				arr[5] = lrup.getMst_destination() == null ? "" : lrup.getMst_destination().trim();
				arr[6] = lrup.getStn_value();
				arr[7] = lrup.getMst_lr_no();
				arr[8] = lrup.getMst_lr_dt() != null ? df.format(lrup.getMst_lr_dt()) : "";
				arr[9] = lrup.getMst_transporter();
				arr[10] = lrup.getMst_grs_wght();
				arr[11] = lrup.getMst_billwght();
				arr[12] = lrup.getMst_tot_case();
				arr[13] = lrup.getDspfstaff_employeeno();
				arr[14] = lrup.getDspfstaff_displayname();
				arr[15] = lrup.getDtl_chln_no();
				arr[16] = lrup.getDtl_chln_dt() != null ? df.format(lrup.getDtl_chln_dt()) : "";
				arr[17] = lrup.getDtl_city();
				arr[18] = lrup.getDtl_transporter();
				arr[19] = lrup.getDtl_lr_no();
				arr[20] = lrup.getDtl_lr_dt() != null ? df.format(lrup.getDtl_lr_dt()) : "";
				arr[21] = lrup.getDtl_stn_grswght();
				arr[22] = lrup.getDtl_stn_billwght();
				arr[23] = lrup.getDtl_tot_case();

				// Added Later
				arr[24] = lrup.getMst_delivery_date() != null ? df.format(lrup.getMst_delivery_date()) : "";
				arr[25] = lrup.getMst_recd_by();
				arr[26] = lrup.getMst_remark();

				arr[27] = lrup.getDtl_delivery_date() != null ? df.format(lrup.getDtl_delivery_date()) : "";
				arr[28] = lrup.getDtl_recd_by();
				arr[29] = lrup.getWay_bill_no();
				arr[30] = lrup.getDtl_remark();
				/*
				 * if (lrup.getDtl_remark() == null || lrup.getDtl_remark().trim().equals(""))
				 * arr[29] = "."; else arr[29] = lrup.getDtl_remark();
				 * 
				 * if (lrup.getAddr1().contains(",")) { String add1 =
				 * lrup.getAddr1().replaceAll(",", ";"); arr[30] = add1; } else { arr[30] =
				 * lrup.getAddr1(); }
				 * 
				 * if (lrup.getAddr2().contains(",")) { String add2 =
				 * lrup.getAddr2().replaceAll(",", ";"); arr[31] = add2; } else { arr[31] =
				 * lrup.getAddr2(); }
				 * 
				 * if (lrup.getAddr3().contains(",")) { String add3 =
				 * lrup.getAddr3().replaceAll(",", ";"); arr[32] = add3; } else { arr[32] =
				 * lrup.getAddr3(); }
				 * 
				 * if (lrup.getAddr4().contains(",")) { String add4 =
				 * lrup.getAddr4().replaceAll(",", ";"); arr[33] = add4; } else { arr[33] =
				 * lrup.getAddr4(); }
				 * 
				 * if (lrup.getAddr5().contains(",")) { String add5 =
				 * lrup.getAddr5().replaceAll(",", ";"); arr[34] = add5; } else { arr[34] =
				 * lrup.getAddr5(); }
				 * 
				 * if (lrup.getMobile().contains(",")) { String mobile =
				 * lrup.getMobile().replaceAll(",", ";"); arr[35] = mobile; } else { arr[35] =
				 * lrup.getMobile(); }
				 * 
				 * if (lrup.getEmail().contains(",")) { String email =
				 * lrup.getEmail().replaceAll(",", ";"); arr[36] = email; } else { arr[36] =
				 * lrup.getEmail(); }
				 * 
				 * if (lrup.getPso_remark().contains(",")) { String psoRemarks =
				 * lrup.getPso_remark().replaceAll(",", ";"); arr[37] = psoRemarks; } else {
				 * arr[37] = lrup.getPso_remark(); }
				 * 
				 * if (lrup.getPso_rtn_details().contains(",")) { String psoRtnDetails =
				 * lrup.getPso_rtn_details().replaceAll(",", ";"); arr[37] = psoRtnDetails; }
				 * else { arr[38] = lrup.getPso_rtn_details(); }
				 */

				li.add(arr);
			}
			writer.writeAll(li);

			File csvfile = new File(path.toString());
			FileInputStream fileInputStream = new FileInputStream(csvfile);
			writer.close();

		} catch (Exception e) {
			System.out.println("Error Occurred :" + e.getMessage());// uncomment asneeded --;
			throw e;
		}
		return fileName;
	}

	@Override
	public String generateLRcsvReport(List<LrcsvDownloadReport> list, String strdate, String enddate, String tbl_ind,
			String cfa_id, String emp_id, String fs_type, String compcode) throws Exception {

		SimpleDateFormat df = new SimpleDateFormat("dd/MM/yyyy");

		CSVWriter writer = null;
		File ff = null;
		StringBuffer path = null;
		String fileName = "";
		String companyCode = compcode;

		try {
			fileName = "LrDetailReport" + new Date().getTime() + ".csv";
			String filePath = MedicoConstants.REPORT_FILE_PATH;
			path = new StringBuffer(filePath).append("\\");
			path.append(fileName);
			ff = new File(path.toString());
			System.out.println("filename " + fileName);
			File file = new File(filePath);

			try {
				if (!file.exists()) {
					if (file.mkdirs()) {
					} else {
						throw new RuntimeException("Could not create directory");
					}
				}
			} catch (Exception e) {
				throw new RuntimeException();
			}
			FileWriter fwriter = new FileWriter(path.toString());
			writer = new CSVWriter(fwriter, ',', CSVWriter.NO_QUOTE_CHARACTER);
			String[] header = new String[39];
			header[0] = "Summary Header ID";
			header[1] = "Team Name";
			header[2] = "Team code";
			header[3] = "Master STN No.";
			header[4] = "Date of STN";
			header[5] = "Destination Location";
			header[6] = "STN Value";
			header[7] = "Master L.R./Airway No.";
			header[8] = "Master L.R./Airway date";
			header[9] = "Master STN Transporter";
			header[10] = "Weight in Kgs";
			header[11] = "Billable Weight in Kgs";
			header[12] = "Total No. of cases";
			header[13] = "Employee No.";
			header[14] = "Employee Name";
			header[15] = "Individual Challan No.";
			header[16] = "Date of Individual Challan";
			header[17] = "City";
			header[18] = "Transporter";
			header[19] = "L.R. No. Individual challan";
			header[20] = "Date";
			header[21] = "Weight in Kgs";
			header[22] = "Billable Weight in Kgs";
			header[23] = "Total No. of cases";

			// Added Later
			header[24] = "MST DELIVERY DATE";
			header[25] = "MST RECEIVED BY";
			header[26] = "MST REMARK";

			header[27] = "DTL DELIVERY DATE";
			header[28] = "DTL RECEIVED BY";
			header[29] = "DTL REMARK";
			header[30] = "ADDRESS 1";
			header[31] = "ADDRESS 2";
			header[32] = "ADDRESS 3";
			header[33] = "ADDRESS 4";
			header[34] = "ADDRESS 5";
			header[35] = "MOBILE NO";
			header[36] = "E-MAIL ID";
			header[37] = "REMARKS";
			if (companyCode.equalsIgnoreCase("NIL") || companyCode.equalsIgnoreCase("NHP")) {
				header[38] = "INVOICE GROSS VALUE";
			} else {
				header[38] = "PSO RESIGNED/STOCK RETURNED DETAILS";
			}

			List<String[]> li = new ArrayList<String[]>();
			li.add(header);
			for (LrcsvDownloadReport lrup : list) {
				String[] arr = new String[39];
				arr[0] = lrup.getSum_hd_id();
				arr[1] = lrup.getTeam_name();
				arr[2] = lrup.getTeam_code();
				arr[3] = lrup.getMst_stn_no();
				arr[4] = lrup.getMst_stn_dt() != null ? Utility.convertStringtoStringDate(lrup.getMst_stn_dt()) : "";
				arr[5] = lrup.getMst_destination().trim();
				arr[6] = lrup.getStn_value();
				arr[7] = lrup.getMst_lr_no();
				arr[8] = lrup.getMst_lr_dt() != null ? Utility.convertStringtoStringDate(lrup.getMst_lr_dt()) : "";
				arr[9] = lrup.getMst_transporter();
				arr[10] = lrup.getMst_grs_wght();
				arr[11] = lrup.getMst_billwght();
				arr[12] = lrup.getMst_tot_case();
				arr[13] = lrup.getDspfstaff_employeeno();
				arr[14] = lrup.getDspfstaff_displayname();
				arr[15] = lrup.getDtl_chln_no();
				arr[16] = lrup.getDtl_chln_dt() != null ? Utility.convertStringtoStringDate(lrup.getDtl_chln_dt()) : "";
				arr[17] = lrup.getDtl_city();
				arr[18] = lrup.getDtl_transporter();
				arr[19] = lrup.getDtl_lr_no();
				arr[20] = lrup.getDtl_lr_dt() != null ? Utility.convertStringtoStringDate(lrup.getDtl_lr_dt()) : "";
				arr[21] = lrup.getDtl_stn_grswght();
				arr[22] = lrup.getDtl_stn_billwght();
				arr[23] = lrup.getDtl_tot_case();

				// Added Later
				arr[24] = lrup.getMst_delivery_date() != null ? df.format(lrup.getMst_delivery_date()) : "";
				arr[25] = lrup.getMst_recd_by();
				arr[26] = lrup.getMst_remark();

				arr[27] = lrup.getDtl_delivery_date() != null ? df.format(lrup.getDtl_delivery_date()) : "";
				arr[28] = lrup.getDtl_recd_by();

				if (lrup.getDtl_remark() == null || lrup.getDtl_remark().trim().equals(""))
					arr[29] = ".";
				else
					arr[29] = lrup.getDtl_remark();

				if (lrup.getAddr1().contains(",")) {
					String add1 = lrup.getAddr1().replaceAll(",", ";");
					arr[30] = add1;
				} else {
					arr[30] = lrup.getAddr1();
				}

				if (lrup.getAddr2().contains(",")) {
					String add2 = lrup.getAddr2().replaceAll(",", ";");
					arr[31] = add2;
				} else {
					arr[31] = lrup.getAddr2();
				}

				if (lrup.getAddr3().contains(",")) {
					String add3 = lrup.getAddr3().replaceAll(",", ";");
					arr[32] = add3;
				} else {
					arr[32] = lrup.getAddr3();
				}

				if (lrup.getAddr4().contains(",")) {
					String add4 = lrup.getAddr4().replaceAll(",", ";");
					arr[33] = add4;
				} else {
					arr[33] = lrup.getAddr4();
				}

				if (lrup.getAddr5().contains(",")) {
					String add5 = lrup.getAddr5().replaceAll(",", ";");
					arr[34] = add5;
				} else {
					arr[34] = lrup.getAddr5();
				}

				if (lrup.getMobile().contains(",")) {
					String mobile = lrup.getMobile().replaceAll(",", ";");
					arr[35] = mobile;
				} else {
					arr[35] = lrup.getMobile();
				}

				if (lrup.getEmail().contains(",")) {
					String email = lrup.getEmail().replaceAll(",", ";");
					arr[36] = email;
				} else {
					arr[36] = lrup.getEmail();
				}

				if (lrup.getPso_remark().contains(",")) {
					String psoRemarks = lrup.getPso_remark().replaceAll(",", ";");
					arr[37] = psoRemarks;
				} else {
					arr[37] = lrup.getPso_remark();
				}

				if (lrup.getPso_rtn_details().contains(",")) {
					String psoRtnDetails = lrup.getPso_rtn_details().replaceAll(",", ";");
					arr[37] = psoRtnDetails;
				} else {
					arr[38] = lrup.getPso_rtn_details();
				}

				li.add(arr);
			}
			writer.writeAll(li);

			File csvfile = new File(path.toString());
			FileInputStream fileInputStream = new FileInputStream(csvfile);
			writer.close();

			System.out.println("Csv Generated");

		} catch (Exception e) {
			throw e;
		}

		return fileName;
	}

	@Override
	public String generateLR_Revised_csvReport(List<LrcsvDownloadReportRevised> lst, String strdate, String enddate,
			String tbl_ind, String cfa_id, String emp_id, String fs_type, String compcode, String role, String username,
			String transprt, String empId) throws Exception {

		SimpleDateFormat df = new SimpleDateFormat("dd/MM/yyyy");

		CSVWriter writer = null;
		File ff = null;
		StringBuffer path = null;
		String fileName = "";
		String companyCode = compcode;

		try {
			if (MedicoConstants.ROLE_COUR.equalsIgnoreCase(role)) {
				fileName = "LrDetailReport" + new Date().getTime() + "_COUR_" + username.toUpperCase() + "" + ".csv";
			} else {
				fileName = "LrDetailReport" + new Date().getTime() + "_COUR_" + transprt.toUpperCase() + "" + ".csv";
			}
			String filePath = MedicoConstants.REPORT_FILE_PATH;
			path = new StringBuffer(filePath).append("\\");
			path.append(fileName);
			ff = new File(path.toString());
			System.out.println("filename " + fileName);
			File file = new File(filePath);

			try {
				if (!file.exists()) {
					if (file.mkdirs()) {
					} else {
						throw new RuntimeException("Could not create directory");
					}
				}
			} catch (Exception e) {
				throw new RuntimeException();
			}
			FileWriter fwriter = new FileWriter(path.toString());
			writer = new CSVWriter(fwriter, ',', CSVWriter.NO_QUOTE_CHARACTER);
			String[] header;
			if (companyCode.trim().equals("PFZ")) {
				header = new String[50];
				header[0] = "Summary Header ID";
				header[1] = "Team Name";
				header[2] = "Team code";
				header[3] = "Master STN No.";
				header[4] = "Date of STN";
				header[5] = "Destination Location";
				header[6] = "STN Value";
				header[7] = "Master L.R./Airway No.";
				header[8] = "Master L.R./Airway date";
				header[9] = "Master STN Transporter";
				header[10] = "Weight in Kgs";
				header[11] = "Billable Weight in Kgs";
				header[12] = "Total No. of cases";

				header[13] = "Master Tentative Delivery Date";
				header[14] = "MST RECEIVED BY";
				header[15] = "MST ACTUAL DELIVERY DATE"; // Changed
				header[16] = "MST WAY BILL NO"; // Changed
				header[17] = "Master LR_Charges(including Taxes)";
				header[18] = "MST COURIER REMARK"; // Changed

				header[19] = "Employee No.";
				header[20] = "Employee Name";
				header[21] = "Individual Challan No.";
				header[22] = "Date of Individual Challan";
				header[23] = "City";
				header[24] = "Transporter";
				header[25] = "L.R. No. Individual challan";
				header[26] = "DTL L.R. Date";

				header[27] = "DTL Tentative Delivery Date";

				header[28] = "Weight in Kgs";
				header[29] = "Billable Weight in Kgs";
				header[30] = "Total No. of cases";
				// Added Later
				header[31] = "DTL ACTUAL DELIVERY DATE"; // Changed
				header[32] = "DTL RECEIVED BY";
				header[33] = "DTL WAY BILL NO"; // Changed
				header[34] = "DTL LR_Charges(including Taxes)";
				header[35] = "PSO RESIGNED/STOCK RETURNED DETAILS"; // Changed

				header[36] = "FINYEAR";

				if (companyCode.equalsIgnoreCase("NIL")) {
					header[37] = "INVOICE GROSS VALUE";
				} else {
					header[37] = "DTL COURIER REMARK";
				}
				header[38] = "ADDRESS 1";
				header[39] = "ADDRESS 2";
				header[40] = "ADDRESS 3";
				header[41] = "ADDRESS 4";
				header[42] = "ADDRESS 5";
//				header[43] = "PINCODE";
				header[43] = "MOBILE NO";
				header[44] = "E-MAIL ID";
				header[45] = "REMARKS";
				header[46] = "DTL VALUE";
				header[47] = "DSP PROD TYPE";
				header[48] = "TRANSPORT_MODE";
				header[49] = "PINCODE";
//				header[50] = "DTL_TRANSPORT_MODE";
//				header[51] = "PSO_RESG_STKRETU";
				
			} else {
				header = new String[49];
				header[0] = "Summary Header ID";
				header[1] = "Team Name";
				header[2] = "Team code";
				header[3] = "Master STN No.";
				header[4] = "Date of STN";
				header[5] = "Destination Location";
				header[6] = "STN Value";
				header[7] = "Master L.R./Airway No.";
				header[8] = "Master L.R./Airway date";
				header[9] = "Master STN Transporter";
				header[10] = "Weight in Kgs";
				header[11] = "Billable Weight in Kgs";
				header[12] = "Total No. of cases";

				header[13] = "Master Tentative Delivery Date";
				header[14] = "MST RECEIVED BY";
				header[15] = "MST ACTUAL DELIVERY DATE"; // Changed
				header[16] = "MST WAY BILL NO"; // Changed
				header[17] = "Master LR_Charges(including Taxes)";
				header[18] = "MST COURIER REMARK"; // Changed

				header[19] = "Employee No.";
				header[20] = "Employee Name";
				header[21] = "Individual Challan No.";
				header[22] = "Date of Individual Challan";
				header[23] = "City";
				header[24] = "Transporter";
				header[25] = "L.R. No. Individual challan";
				header[26] = "DTL L.R. Date";

				header[27] = "DTL Tentative Delivery Date";

				header[28] = "Weight in Kgs";
				header[29] = "Billable Weight in Kgs";
				header[30] = "Total No. of cases";
				// Added Later
				header[31] = "DTL ACTUAL DELIVERY DATE"; // Changed
				header[32] = "DTL RECEIVED BY";
				header[33] = "DTL WAY BILL NO"; // Changed
				header[34] = "DTL LR_Charges(including Taxes)";
				header[35] = "PSO RESIGNED/STOCK RETURNED DETAILS"; // Changed

				header[36] = "FINYEAR";

				if (companyCode.equalsIgnoreCase("NIL")) {
					header[37] = "INVOICE GROSS VALUE";
				} else {
					header[37] = "DTL COURIER REMARK";
				}
				header[38] = "ADDRESS 1";
				header[39] = "ADDRESS 2";
				header[40] = "ADDRESS 3";
				header[41] = "ADDRESS 4";
				header[42] = "ADDRESS 5";
				header[43] = "PINCODE";
				header[44] = "MOBILE NO";
				header[45] = "E-MAIL ID";
				header[46] = "REMARKS";
				header[47] = "DTL VALUE";
				header[48] = "DSP PROD TYPE";
			}

			List<String[]> li = new ArrayList<String[]>();
			li.add(header);
			for (LrcsvDownloadReportRevised lrup : lst) {
				if (companyCode.trim().equals("PFZ")) {
					String[] arr = new String[50];
					arr[0] = lrup.getSum_hd_id();
					arr[1] = lrup.getTeam_name() != null
							? lrup.getTeam_name().replaceAll(",", ".").replaceAll("\\s+", " ").trim()
							: "";
					arr[2] = lrup.getTeam_code() != null ? lrup.getTeam_code().replaceAll("\\s+", " ").trim() : "";
					arr[3] = lrup.getMst_stn_no() != null ? lrup.getMst_stn_no() : "";
					arr[4] = lrup.getMst_stn_dt() != null ? Utility.convertStringtoStringDate(lrup.getMst_stn_dt())
							: "";
					arr[5] = lrup.getMst_destination() != null
							? lrup.getMst_destination().trim().replaceAll(",", ".").replaceAll("\\s+", " ").trim()
							: "";
					arr[6] = lrup.getStn_value() != null ? lrup.getStn_value() : "";
					arr[7] = lrup.getMst_lr_no() != null ? lrup.getMst_lr_no().replaceAll("\\s+", " ").trim() : "";
					arr[8] = lrup.getMst_lr_dt() != null ? Utility.convertStringtoStringDate(lrup.getMst_lr_dt()) : "";
					//
					//
					arr[9] = lrup.getMst_transporter() != null
							? lrup.getMst_transporter().replaceAll(",", ".").replaceAll("\\s+", " ").trim()
							: "";
					arr[10] = lrup.getMst_grs_wght() != null ? lrup.getMst_grs_wght() : "";
					arr[11] = lrup.getMst_billwght() != null ? lrup.getMst_billwght() : "";
					arr[12] = lrup.getMst_tot_case() != null ? lrup.getMst_tot_case() : "";

					arr[13] = lrup.getMst_tent_delivery_date() != null
							? Utility.convertStringtoStringDate(lrup.getMst_tent_delivery_date())
							: "";
					arr[14] = lrup.getMst_recd_by() != null
							? lrup.getMst_recd_by().replaceAll(",", ".").replaceAll("\\s+", " ").trim()
							: "";

					arr[15] = lrup.getMst_actual_delivery_date() != null ? df.format(lrup.getMst_actual_delivery_date())
							: ""; // Changed
					arr[16] = lrup.getMst_way_bill_no() != null ? lrup.getMst_way_bill_no() : ""; // mst Waybill_no

					arr[17] = lrup.getMst_courier_expenses() != null
							? lrup.getMst_courier_expenses().setScale(2).toString()
							: "";
					if (lrup.getMst_courier_remark() == null || lrup.getMst_courier_remark().trim().equals("")) // Changed
						arr[18] = ".";
					else
						arr[18] = lrup.getMst_courier_remark() != null
								? lrup.getMst_courier_remark().replaceAll(",", ".").replaceAll("\\s+", " ").trim()
								: ""; // Changed //Changed

					arr[19] = lrup.getDspfstaff_employeeno() != null
							? lrup.getDspfstaff_employeeno().replaceAll(",", ".").replaceAll("\\s+", " ").trim()
							: "";
					arr[20] = lrup.getDspfstaff_displayname() != null
							? lrup.getDspfstaff_displayname().replaceAll(",", ".").replaceAll("\\s+", " ").trim()
							: "";
					arr[21] = lrup.getDtl_chln_no() != null ? lrup.getDtl_chln_no() : "";
					arr[22] = lrup.getDtl_chln_dt() != null ? Utility.convertStringtoStringDate(lrup.getDtl_chln_dt())
							: "";
					arr[23] = lrup.getDtl_city() != null
							? lrup.getDtl_city().replaceAll(",", ".").replaceAll("\\s+", " ").trim()
							: "";
					arr[24] = lrup.getDtl_transporter() != null
							? lrup.getDtl_transporter().replaceAll(",", ".").replaceAll("\\s+", " ").trim()
							: "";
					arr[25] = lrup.getDtl_lr_no() != null ? lrup.getDtl_lr_no() : "";
					arr[26] = lrup.getDtl_lr_dt() != null ? Utility.convertStringtoStringDate(lrup.getDtl_lr_dt()) : "";

					arr[27] = lrup.getDtl_tent_delivery_date() != null
							? Utility.convertStringtoStringDate(lrup.getDtl_tent_delivery_date())
							: "";

					arr[28] = lrup.getDtl_stn_grswght();
					arr[29] = lrup.getDtl_stn_billwght();
					arr[30] = lrup.getDtl_tot_case();

					arr[31] = lrup.getDtl_actual_delivery_date() != null
							? Utility.convertStringtoStringDate(lrup.getDtl_actual_delivery_date())
							: ""; // Changed
					arr[32] = lrup.getDtl_recd_by() != null
							? lrup.getDtl_recd_by().replaceAll(",", ".").replaceAll("\\s+", " ").trim()
							: "";
					arr[33] = lrup.getWay_bill_no();
					arr[34] = lrup.getDtl_courier_expenses() != null
							? lrup.getDtl_courier_expenses().setScale(2).toString()
							: "";

					arr[35] = lrup.getPso_resg_stkretu(); // Changed

					arr[36] = lrup.getDsp_fin_year();

					if (lrup.getDtl_courier_remark() == null || lrup.getDtl_courier_remark().trim().equals("")) // Changed
						arr[37] = ".";
					else
						arr[37] = lrup.getDtl_courier_remark().replaceAll("\\s+", " ").trim();

					if (lrup.getAddr1().contains(",")) {
						String add1 = lrup.getAddr1().replaceAll(",", ";").trim();
						arr[38] = add1.replaceAll("\\s+", " ");
						;
					} else {
						arr[38] = lrup.getAddr1().replaceAll("\\s+", " ").trim();
					}

					if (lrup.getAddr2().contains(",")) {
						String add2 = lrup.getAddr2().replaceAll(",", ";").trim();
						arr[39] = add2.replaceAll("\\s+", " ");
					} else {
						arr[39] = lrup.getAddr2().replaceAll("\\s+", " ").trim();
					}

					if (lrup.getAddr3().contains(",")) {
						String add3 = lrup.getAddr3().replaceAll(",", ";").trim();
						arr[40] = add3.replaceAll("\\s+", " ");
					} else {
						arr[40] = lrup.getAddr3().replaceAll("\\s+", " ").trim();
					}

					if (lrup.getAddr4().contains(",")) {
						String add4 = lrup.getAddr4().replaceAll(",", ";").trim();
						arr[41] = add4.replaceAll("\\s+", " ");
					} else {
						arr[41] = lrup.getAddr4().replaceAll("\\s+", " ").trim();
					}

					if (lrup.getAddr5().contains(",")) {
						String add5 = lrup.getAddr5().replaceAll(",", ";").trim();
						arr[42] = add5.replaceAll("\\s+", " ");
					} else {
						arr[42] = lrup.getAddr5().replaceAll("\\s+", " ").trim();
					}
//					arr[43] = lrup.getPincode();

					if (lrup.getMobile().contains(",")) {
						String mobile = lrup.getMobile().replaceAll(",", ";").replaceAll("\\s+", " ").trim();
						arr[43] = mobile;
					} else {
						arr[43] = lrup.getMobile().replaceAll("\\s+", " ").trim();
					}

					if (lrup.getEmail().contains(",")) {
						String email = lrup.getEmail().replaceAll(",", ";").replaceAll("\\s+", " ").trim();
						arr[44] = email;
					} else {
						arr[44] = lrup.getEmail().replaceAll("\\s+", " ").trim();
					}

					if (lrup.getPso_remark().contains(",")) {
						String psoRemarks = lrup.getPso_remark().replaceAll(",", ";").replaceAll("\\s+", " ").trim();
						arr[45] = psoRemarks;
					} else {
						arr[45] = lrup.getPso_remark().replaceAll("\\s+", " ").trim();
					}

					arr[46] = lrup.getDtl_value() != null ? lrup.getDtl_value().setScale(2).toString() : "";
					arr[47] = lrup.getDsp_prod_type() != null ? lrup.getDsp_prod_type() : "";
					arr[48] = lrup.getDtl_transport_mode() != null ? lrup.getDtl_transport_mode() : "";
					arr[49] = lrup.getPincode();

					
					li.add(arr);
				} else {
					String[] arr = new String[49];
					arr[0] = lrup.getSum_hd_id();
					arr[1] = lrup.getTeam_name() != null
							? lrup.getTeam_name().replaceAll(",", ".").replaceAll("\\s+", " ").trim()
							: "";
					arr[2] = lrup.getTeam_code() != null ? lrup.getTeam_code().replaceAll("\\s+", " ").trim() : "";
					arr[3] = lrup.getMst_stn_no() != null ? lrup.getMst_stn_no() : "";
					arr[4] = lrup.getMst_stn_dt() != null ? Utility.convertStringtoStringDate(lrup.getMst_stn_dt())
							: "";
					arr[5] = lrup.getMst_destination() != null
							? lrup.getMst_destination().trim().replaceAll(",", ".").replaceAll("\\s+", " ").trim()
							: "";
					arr[6] = lrup.getStn_value() != null ? lrup.getStn_value() : "";
					arr[7] = lrup.getMst_lr_no() != null ? lrup.getMst_lr_no().replaceAll("\\s+", " ").trim() : "";
					arr[8] = lrup.getMst_lr_dt() != null ? Utility.convertStringtoStringDate(lrup.getMst_lr_dt()) : "";
					//
					//
					arr[9] = lrup.getMst_transporter() != null
							? lrup.getMst_transporter().replaceAll(",", ".").replaceAll("\\s+", " ").trim()
							: "";
					arr[10] = lrup.getMst_grs_wght() != null ? lrup.getMst_grs_wght() : "";
					arr[11] = lrup.getMst_billwght() != null ? lrup.getMst_billwght() : "";
					arr[12] = lrup.getMst_tot_case() != null ? lrup.getMst_tot_case() : "";

					arr[13] = lrup.getMst_tent_delivery_date() != null
							? Utility.convertStringtoStringDate(lrup.getMst_tent_delivery_date())
							: "";
					arr[14] = lrup.getMst_recd_by() != null
							? lrup.getMst_recd_by().replaceAll(",", ".").replaceAll("\\s+", " ").trim()
							: "";

					arr[15] = lrup.getMst_actual_delivery_date() != null ? df.format(lrup.getMst_actual_delivery_date())
							: ""; // Changed
					arr[16] = lrup.getMst_way_bill_no() != null ? lrup.getMst_way_bill_no() : ""; // mst Waybill_no

					arr[17] = lrup.getMst_courier_expenses() != null
							? lrup.getMst_courier_expenses().setScale(2).toString()
							: "";
					if (lrup.getMst_courier_remark() == null || lrup.getMst_courier_remark().trim().equals("")) // Changed
						arr[18] = ".";
					else
						arr[18] = lrup.getMst_courier_remark() != null
								? lrup.getMst_courier_remark().replaceAll(",", ".").replaceAll("\\s+", " ").trim()
								: ""; // Changed //Changed

					arr[19] = lrup.getDspfstaff_employeeno() != null
							? lrup.getDspfstaff_employeeno().replaceAll(",", ".").replaceAll("\\s+", " ").trim()
							: "";
					arr[20] = lrup.getDspfstaff_displayname() != null
							? lrup.getDspfstaff_displayname().replaceAll(",", ".").replaceAll("\\s+", " ").trim()
							: "";
					arr[21] = lrup.getDtl_chln_no() != null ? lrup.getDtl_chln_no() : "";
					arr[22] = lrup.getDtl_chln_dt() != null ? Utility.convertStringtoStringDate(lrup.getDtl_chln_dt())
							: "";
					arr[23] = lrup.getDtl_city() != null
							? lrup.getDtl_city().replaceAll(",", ".").replaceAll("\\s+", " ").trim()
							: "";
					arr[24] = lrup.getDtl_transporter() != null
							? lrup.getDtl_transporter().replaceAll(",", ".").replaceAll("\\s+", " ").trim()
							: "";
					arr[25] = lrup.getDtl_lr_no() != null ? lrup.getDtl_lr_no() : "";
					arr[26] = lrup.getDtl_lr_dt() != null ? Utility.convertStringtoStringDate(lrup.getDtl_lr_dt()) : "";

					arr[27] = lrup.getDtl_tent_delivery_date() != null
							? Utility.convertStringtoStringDate(lrup.getDtl_tent_delivery_date())
							: "";

					arr[28] = lrup.getDtl_stn_grswght();
					arr[29] = lrup.getDtl_stn_billwght();
					arr[30] = lrup.getDtl_tot_case();

					arr[31] = lrup.getDtl_actual_delivery_date() != null
							? Utility.convertStringtoStringDate(lrup.getDtl_actual_delivery_date())
							: ""; // Changed
					arr[32] = lrup.getDtl_recd_by() != null
							? lrup.getDtl_recd_by().replaceAll(",", ".").replaceAll("\\s+", " ").trim()
							: "";
					arr[33] = lrup.getWay_bill_no();
					arr[34] = lrup.getDtl_courier_expenses() != null
							? lrup.getDtl_courier_expenses().setScale(2).toString()
							: "";

					arr[35] = lrup.getPso_resg_stkretu(); // Changed

					arr[36] = lrup.getDsp_fin_year();

					if (lrup.getDtl_courier_remark() == null || lrup.getDtl_courier_remark().trim().equals("")) // Changed
						arr[37] = ".";
					else
						arr[37] = lrup.getDtl_courier_remark().replaceAll("\\s+", " ").trim();

					if (lrup.getAddr1().contains(",")) {
						String add1 = lrup.getAddr1().replaceAll(",", ";").trim();
						arr[38] = add1.replaceAll("\\s+", " ");
						;
					} else {
						arr[38] = lrup.getAddr1().replaceAll("\\s+", " ").trim();
					}

					if (lrup.getAddr2().contains(",")) {
						String add2 = lrup.getAddr2().replaceAll(",", ";").trim();
						arr[39] = add2.replaceAll("\\s+", " ");
					} else {
						arr[39] = lrup.getAddr2().replaceAll("\\s+", " ").trim();
					}

					if (lrup.getAddr3().contains(",")) {
						String add3 = lrup.getAddr3().replaceAll(",", ";").trim();
						arr[40] = add3.replaceAll("\\s+", " ");
					} else {
						arr[40] = lrup.getAddr3().replaceAll("\\s+", " ").trim();
					}

					if (lrup.getAddr4().contains(",")) {
						String add4 = lrup.getAddr4().replaceAll(",", ";").trim();
						arr[41] = add4.replaceAll("\\s+", " ");
					} else {
						arr[41] = lrup.getAddr4().replaceAll("\\s+", " ").trim();
					}

					if (lrup.getAddr5().contains(",")) {
						String add5 = lrup.getAddr5().replaceAll(",", ";").trim();
						arr[42] = add5.replaceAll("\\s+", " ");
					} else {
						arr[42] = lrup.getAddr5().replaceAll("\\s+", " ").trim();
					}
					arr[43] = lrup.getPincode();

					if (lrup.getMobile().contains(",")) {
						String mobile = lrup.getMobile().replaceAll(",", ";").replaceAll("\\s+", " ").trim();
						arr[44] = mobile;
					} else {
						arr[44] = lrup.getMobile().replaceAll("\\s+", " ").trim();
					}

					if (lrup.getEmail().contains(",")) {
						String email = lrup.getEmail().replaceAll(",", ";").replaceAll("\\s+", " ").trim();
						arr[45] = email;
					} else {
						arr[45] = lrup.getEmail().replaceAll("\\s+", " ").trim();
					}

					if (lrup.getPso_remark().contains(",")) {
						String psoRemarks = lrup.getPso_remark().replaceAll(",", ";").replaceAll("\\s+", " ").trim();
						arr[46] = psoRemarks;
					} else {
						arr[46] = lrup.getPso_remark().replaceAll("\\s+", " ").trim();
					}

					arr[47] = lrup.getDtl_value() != null ? lrup.getDtl_value().setScale(2).toString() : "";
					arr[48] = lrup.getDsp_prod_type() != null ? lrup.getDsp_prod_type() : "";
					
					//added by nilesh 18 Jan
//					arr[49] = lrup.getMst_transport_mode() != null ? lrup.getMst_transport_mode() : ""; 
//					arr[50] = lrup.getDtl_transport_mode() != null ? lrup.getDtl_transport_mode() : "";
//					arr[51] = lrup.getPso_resg_stkretu() != null ? lrup.getPso_resg_stkretu() : "";
					
					li.add(arr);
				}

			}
			writer.writeAll(li);

			File csvfile = new File(path.toString());
			FileInputStream fileInputStream = new FileInputStream(csvfile);
			writer.close();

			System.out.println("Csv Generated");

			fileName = usermasterservice.generateExcellock(path.toString(), fileName, empId, ".csv");

		} catch (Exception e) {
			throw e;
		}

		return fileName;
	}

	@Override
	public String Generate_Lr_Revised_CSV(List<Lrcsv_RevisedDownload> lst, String username, String role,
			String indicator, String compcd, String empId) throws Exception {

		String gprmInd = parameterService.getGprmIndicator();
		SimpleDateFormat df = new SimpleDateFormat("dd/MM/yyyy");

		CSVWriter writer = null;
		File ff = null;
		StringBuffer path = null;
		String fileName = "";
		try {
			if (indicator.equalsIgnoreCase("D")) {
				if (role.trim().equals(MedicoConstants.ROLE_COUR)) {
					fileName = "LrDetail" + new Date().getTime() + "_for_Cour_" + username + "" + ".csv";
				} else {
					fileName = "LrDetail" + new Date().getTime() + "" + ".csv";
				}
			} else if (indicator.equalsIgnoreCase("U")) {
				if (role.trim().equals(MedicoConstants.ROLE_COUR)) {
					fileName = "LrDetailReport" + new Date().getTime() + "_COUR_" + username.toUpperCase()
							+ "_WITH_LR_DETAIL.csv";
				} else {
					fileName = "LrDetailReport" + new Date().getTime() + "_COUR_ALL_WITH_LR_DETAIL.csv";
				}
			}
			String filePath = MedicoConstants.REPORT_FILE_PATH;
			path = new StringBuffer(filePath).append("\\");
			path.append(fileName);
			ff = new File(path.toString());
			System.out.println("filename " + fileName);
			File file = new File(filePath);

			try {
				if (!file.exists()) {
					if (file.mkdirs()) {
					} else {
						throw new RuntimeException("Could not create directory");
					}
				}
			} catch (Exception e) {
				throw new RuntimeException();
			}

			FileWriter fwriter = new FileWriter(path.toString());
			writer = new CSVWriter(fwriter, ',', CSVWriter.NO_QUOTE_CHARACTER);
			String[] header = null;
			if (compcd.trim().equals(VET)) {
				header = new String[41];
				header[0] = "Summary Header ID";
				header[1] = "Team Name";
				header[2] = "Team code";
				header[3] = "Master STN No.";
				header[4] = "Date of STN";
				header[5] = "Destination Location";
				header[6] = "STN Value";
				header[7] = "Master L.R./Airway No.";
				header[8] = "Master L.R./Airway date";

				header[9] = "Master STN Transporter";
				header[10] = "Weight in Kgs";
				header[11] = "Billable Weight in Kgs";
				header[12] = "Total No. of cases";

				header[13] = "Master Tentative Delivery Date";
				header[14] = "MST RECEIVED BY";
				header[15] = "MST ACTUAL DELIVERY DATE"; // Changed
				header[16] = "MST WAY BILL NO"; // Changed
				header[17] = "Master LR_Charges(including Taxes)";
				header[18] = "MST COURIER REMARK"; // Changed

				header[19] = "Employee No.";
				header[20] = "Employee Name";
				header[21] = "Individual Challan No.";
				header[22] = "Date of Individual Challan";
				header[23] = "City";
				header[24] = "Transporter";
				header[25] = "L.R. No. Individual challan";
				header[26] = "DTL L.R. Date";

				header[27] = "DTL Tentative Delivery Date";

				header[28] = "Weight in Kgs";
				header[29] = "Billable Weight in Kgs";
				header[30] = "Total No. of cases";

				// Added Later
				header[31] = "DTL ACTUAL DELIVERY DATE"; // Changed
				header[32] = "DTL RECEIVED BY";
				header[33] = "DTL WAY BILL NO"; // Changed
				header[34] = "DTL LR_Charges(including Taxes)";
				header[35] = "DTL COURIER REMARK"; // Changed
				header[36] = "FINYEAR";

				header[37] = "MST TRANSPORTER GSTREGNO";
				header[38] = "MST VEHICLE NO";
				header[39] = "DTL TRANSPORTER GSTREGNO";
				header[40] = "DTL VEHICLE NO";

			} else if (compcd.trim().equals(PFZ)) { // added by nilesh 14 Jan
				if (gprmInd.equalsIgnoreCase(Y)) {
					header = new String[40];
				} else {
					header = new String[39];
				}
				header[0] = "Summary Header ID";
				header[1] = "Team Name";
				header[2] = "Team code";
				header[3] = "Master STN No.";
				header[4] = "Date of STN";
				header[5] = "Destination Location";
				header[6] = "STN Value";
				header[7] = "Master L.R./Airway No.";
				header[8] = "Master L.R./Airway date";

				header[9] = "Master STN Transporter";
				header[10] = "Weight in Kgs";
				header[11] = "Billable Weight in Kgs";
				header[12] = "Total No. of cases";

				header[13] = "Master Tentative Delivery Date";
				header[14] = "MST RECEIVED BY";
				header[15] = "MST ACTUAL DELIVERY DATE"; // Changed
				header[16] = "MST WAY BILL NO"; // Changed
				header[17] = "Master LR_Charges(including Taxes)";
				header[18] = "MST COURIER REMARK"; // Changed

				header[19] = "Employee No.";
				header[20] = "Employee Name";
				header[21] = "Individual Challan No.";
				header[22] = "Date of Individual Challan";
				header[23] = "City";
				header[24] = "Transporter";
				header[25] = "L.R. No. Individual challan";
				header[26] = "DTL L.R. Date";

				header[27] = "DTL Tentative Delivery Date";

				header[28] = "Weight in Kgs";
				header[29] = "Billable Weight in Kgs";
				header[30] = "Total No. of cases";

				// Added Later
				header[31] = "DTL ACTUAL DELIVERY DATE";
				header[32] = "DTL RECEIVED BY";
				header[33] = "DTL WAY BILL NO";
				header[34] = "DTL LR_Charges(including Taxes)";
				header[35] = "PSO RESIGNED/STOCK RETURNED DETAILS";
				header[36] = "FINYEAR";
				if (gprmInd.equalsIgnoreCase(Y)) {
					header[37] = "DSP PROD TYPE";
					header[38] = "TRANSPORTER GST REG NO";
					header[39] = "TRANSPORT MODE";
				} else {
					header[37] = "TRANSPORTER GST REG NO";
					header[38] = "TRANSPORT MODE";

				}

			} else {
				if (gprmInd.equalsIgnoreCase(Y)) {
					header = new String[39];// changed yash 15 jul +1
				} else {
					header = new String[38];// changed yash 15 jul +1
				}
				header[0] = "Summary Header ID";
				header[1] = "Team Name";
				header[2] = "Team code";
				header[3] = "Master STN No.";
				header[4] = "Date of STN";
				header[5] = "Destination Location";
				header[6] = "STN Value";
				header[7] = "Master L.R./Airway No.";
				header[8] = "Master L.R./Airway date";

				header[9] = "Master STN Transporter";
				header[10] = "Weight in Kgs";
				header[11] = "Billable Weight in Kgs";
				header[12] = "Total No. of cases";

				header[13] = "Master Tentative Delivery Date";
				header[14] = "MST RECEIVED BY";
				header[15] = "MST ACTUAL DELIVERY DATE"; // Changed
				header[16] = "MST WAY BILL NO"; // Changed
				header[17] = "Master LR_Charges(including Taxes)";
				header[18] = "MST COURIER REMARK"; // Changed

				header[19] = "Employee No.";
				header[20] = "Employee Name";
				header[21] = "Individual Challan No.";
				header[22] = "Date of Individual Challan";
				header[23] = "City";
				header[24] = "Transporter";
				header[25] = "L.R. No. Individual challan";
				header[26] = "DTL L.R. Date";

				header[27] = "DTL Tentative Delivery Date";

				header[28] = "Weight in Kgs";
				header[29] = "Billable Weight in Kgs";
				header[30] = "Total No. of cases";

				// Added Later
				header[31] = "DTL ACTUAL DELIVERY DATE";
				header[32] = "DTL RECEIVED BY";
				header[33] = "DTL WAY BILL NO";
				header[34] = "DTL LR_Charges(including Taxes)";
				if (compcd.trim().equals(PFZ))
					header[35] = "PSO RESIGNED/STOCK RETURNED DETAILS";
				else
					header[35] = "DTL COURIER REMARK";
				header[36] = "FINYEAR";
				if (gprmInd.equalsIgnoreCase(Y)) {
					header[37] = "DSP PROD TYPE";
					header[38] = "TRANSPORTER GST REG NO";// changed yash 15 jul
				} else {
					header[37] = "TRANSPORTER GST REG NO";// changed yash 15 jul
				}
			}
			/*
			 * header[30] = "ADDRESS 1"; header[31] = "ADDRESS 2"; header[32] = "ADDRESS 3";
			 * header[33] = "ADDRESS 4"; header[34] = "ADDRESS 5"; header[35] = "MOBILE NO";
			 * header[36] = "E-MAIL ID"; header[37] = "REMARKS"; header[38] =
			 * "PSO RESIGNED/STOCK RETURNED DETAILS";
			 */

			List<String[]> li = new ArrayList<String[]>();
			li.add(header);
			System.out.println("Number of recs " + lst.size());
			for (Lrcsv_RevisedDownload lrup : lst) {
				String[] arr = null;
				if (compcd.trim().equals(VET)) {
					arr = new String[41];
				} else {
					if (gprmInd.equalsIgnoreCase(Y)) {
						arr = new String[39];// changed yash 15 jul
					} else {
						arr = new String[38];// changed yash 15 jul
					}

				}
				arr[0] = lrup.getSum_hd_id();
				arr[1] = lrup.getTeam_name() != null
						? lrup.getTeam_name().replaceAll(",", ".").replaceAll("\\s+", " ").trim()
						: "";
				arr[2] = lrup.getTeam_code();
				arr[3] = lrup.getMst_stn_no();
				arr[4] = lrup.getMst_stn_dt() != null ? df.format(lrup.getMst_stn_dt()) : "";
				arr[5] = lrup.getMst_destination() == null ? ""
						: lrup.getMst_destination().trim().replaceAll(",", ".").replaceAll("\\s+", " ").trim();
				arr[6] = lrup.getStn_value();
				arr[7] = lrup.getMst_lr_no();
				arr[8] = lrup.getMst_lr_dt() != null ? df.format(lrup.getMst_lr_dt()) : "";

				arr[9] = lrup.getMst_transporter() != null
						? lrup.getMst_transporter().replaceAll(",", ".").replaceAll("\\s+", " ").trim()
						: "";
				arr[10] = lrup.getMst_grs_wght();
				arr[11] = lrup.getMst_billwght();
				arr[12] = lrup.getMst_tot_case();

				arr[13] = lrup.getMst_tent_delivery_date() != null ? df.format(lrup.getMst_tent_delivery_date()) : "";
				arr[14] = lrup.getMst_recd_by() != null
						? lrup.getMst_recd_by().replaceAll(",", ".").replaceAll("\\s+", " ").trim()
						: "";
				arr[15] = lrup.getMst_actual_delivery_date() != null ? df.format(lrup.getMst_actual_delivery_date())
						: "";
				arr[16] = lrup.getMst_way_bill_no(); // Mst WayBill
				arr[17] = lrup.getMst_courier_expenses();
				if (lrup.getMst_courier_remark() == null || lrup.getMst_courier_remark().trim().equals("")) // Changed
					arr[18] = ".";
				else
					arr[18] = lrup.getMst_courier_remark(); // Changed

				arr[19] = lrup.getDspfstaff_employeeno();
				arr[20] = lrup.getDspfstaff_displayname() != null
						? lrup.getDspfstaff_displayname().replaceAll(",", ".").replaceAll("\\s+", " ").trim()
						: "";
				arr[21] = lrup.getDtl_chln_no();
				arr[22] = lrup.getDtl_chln_dt() != null ? df.format(lrup.getDtl_chln_dt()) : "";
				arr[23] = lrup.getDtl_city() != null
						? lrup.getDtl_city().replaceAll(",", ".").replaceAll("\\s+", " ").trim()
						: "";
				arr[24] = lrup.getDtl_transporter() != null
						? lrup.getDtl_transporter().replaceAll(",", ".").replaceAll("\\s+", " ").trim()
						: "";
				arr[25] = lrup.getDtl_lr_no();
				arr[26] = lrup.getDtl_lr_dt() != null ? df.format(lrup.getDtl_lr_dt()) : "";

				arr[27] = lrup.getDtl_tent_delivery_date() != null ? df.format(lrup.getDtl_tent_delivery_date()) : "";

				arr[28] = lrup.getDtl_stn_grswght();
				arr[29] = lrup.getDtl_stn_billwght();
				arr[30] = lrup.getDtl_tot_case();

				arr[31] = lrup.getDtl_actual_delivery_date() != null ? df.format(lrup.getDtl_actual_delivery_date())
						: "";
				arr[32] = lrup.getDtl_recd_by() != null
						? lrup.getDtl_recd_by().replaceAll(",", ".").replaceAll("\\s+", " ").trim()
						: "";
				arr[33] = lrup.getWay_bill_no();
				arr[34] = lrup.getDtl_courier_expenses();
				if (lrup.getDtl_courier_remark() == null || lrup.getDtl_courier_remark().trim().equals("")) // Changed
					arr[35] = ".";
				else
					arr[35] = lrup.getDtl_courier_remark(); // Changed

				arr[36] = lrup.getDsp_fin_year();

				if (compcd.trim().equals(VET)) {
					arr[37] = "";
					arr[38] = "";
					arr[39] = "";
					arr[40] = ".";
				} else {
					if (gprmInd.equalsIgnoreCase(Y)) {
						arr[37] = lrup.getDsp_prod_type();
						arr[38] = lrup.getDsp_trans_gst_reg_no();// changed yash 15 jul

						if (compcd.trim().equals(PFZ)) { // added by nilesh 14 Jan
							arr[39] = (lrup.getDtl_transport_mode() == null || lrup.getDtl_transport_mode().trim().isEmpty())
									? ""
									: lrup.getDtl_transport_mode();
						}
					}
					// ----changed yash 15 jul
					else {
						arr[37] = lrup.getDsp_trans_gst_reg_no();
						if (compcd.trim().equals(PFZ)) { // added by nilesh 14 Jan
							arr[38] = (lrup.getDtl_transport_mode() == null || lrup.getDtl_transport_mode().trim().isEmpty())
									? ""
									: lrup.getDtl_transport_mode();
						}
					}
					// ----changed yash 15 jul

				}
				/*
				 * if (lrup.getDtl_remark() == null || lrup.getDtl_remark().trim().equals(""))
				 * arr[29] = "."; else arr[29] = lrup.getDtl_remark();
				 * 
				 * if (lrup.getAddr1().contains(",")) { String add1 =
				 * lrup.getAddr1().replaceAll(",", ";"); arr[30] = add1; } else { arr[30] =
				 * lrup.getAddr1(); }
				 * 
				 * if (lrup.getAddr2().contains(",")) { String add2 =
				 * lrup.getAddr2().replaceAll(",", ";"); arr[31] = add2; } else { arr[31] =
				 * lrup.getAddr2(); }
				 * 
				 * if (lrup.getAddr3().contains(",")) { String add3 =
				 * lrup.getAddr3().replaceAll(",", ";"); arr[32] = add3; } else { arr[32] =
				 * lrup.getAddr3(); }
				 * 
				 * if (lrup.getAddr4().contains(",")) { String add4 =
				 * lrup.getAddr4().replaceAll(",", ";"); arr[33] = add4; } else { arr[33] =
				 * lrup.getAddr4(); }
				 * 
				 * if (lrup.getAddr5().contains(",")) { String add5 =
				 * lrup.getAddr5().replaceAll(",", ";"); arr[34] = add5; } else { arr[34] =
				 * lrup.getAddr5(); }
				 * 
				 * if (lrup.getMobile().contains(",")) { String mobile =
				 * lrup.getMobile().replaceAll(",", ";"); arr[35] = mobile; } else { arr[35] =
				 * lrup.getMobile(); }
				 * 
				 * if (lrup.getEmail().contains(",")) { String email =
				 * lrup.getEmail().replaceAll(",", ";"); arr[36] = email; } else { arr[36] =
				 * lrup.getEmail(); }
				 * 
				 * if (lrup.getPso_remark().contains(",")) { String psoRemarks =
				 * lrup.getPso_remark().replaceAll(",", ";"); arr[37] = psoRemarks; } else {
				 * arr[37] = lrup.getPso_remark(); }
				 * 
				 * if (lrup.getPso_rtn_details().contains(",")) { String psoRtnDetails =
				 * lrup.getPso_rtn_details().replaceAll(",", ";"); arr[37] = psoRtnDetails; }
				 * else { arr[38] = lrup.getPso_rtn_details(); }
				 */

				li.add(arr);
			}
			writer.writeAll(li);

			File csvfile = new File(path.toString());
			FileInputStream fileInputStream = new FileInputStream(csvfile);
			writer.close();

			fileName = usermasterservice.generateExcellock(path.toString(), fileName, empId, ".csv");

		} catch (Exception e) {
			System.out.println("Error Occurred :" + e.getMessage());// uncomment asneeded --;
			throw e;
		}
		return fileName;
	}

	@Override
	public String Generate_Lr_Revised_CSV_SG(List<Lrcsv_RevisedDownload_SG> lst, String username, String role,
			String indicator, String compcd, String empId) throws Exception {

		String gprmInd = parameterService.getGprmIndicator();
		SimpleDateFormat df = new SimpleDateFormat("dd/MM/yyyy");

		CSVWriter writer = null;
		File ff = null;
		StringBuffer path = null;
		String fileName = "";
		try {
			if (indicator.equalsIgnoreCase("D")) {
				if (role.trim().equals(MedicoConstants.ROLE_COUR)) {
					fileName = "LrDetail" + new Date().getTime() + "_for_Cour_" + username + "" + ".csv";
				} else {
					fileName = "LrDetail" + new Date().getTime() + "" + ".csv";
				}
			} else if (indicator.equalsIgnoreCase("U")) {
				if (role.trim().equals(MedicoConstants.ROLE_COUR)) {
					fileName = "LrDetailReport" + new Date().getTime() + "_COUR_" + username.toUpperCase()
							+ "_WITH_LR_DETAIL.csv";
				} else {
					fileName = "LrDetailReport" + new Date().getTime() + "_COUR_ALL_WITH_LR_DETAIL.csv";
				}
			}
			String filePath = MedicoConstants.REPORT_FILE_PATH;
			path = new StringBuffer(filePath).append("\\");
			path.append(fileName);
			ff = new File(path.toString());
			System.out.println("filename " + fileName);
			File file = new File(filePath);

			try {
				if (!file.exists()) {
					if (file.mkdirs()) {
					} else {
						throw new RuntimeException("Could not create directory");
					}
				}
			} catch (Exception e) {
				throw new RuntimeException();
			}

			FileWriter fwriter = new FileWriter(path.toString());
			writer = new CSVWriter(fwriter, ',', CSVWriter.NO_QUOTE_CHARACTER);
			String[] header = null;
			if (compcd.trim().equals(VET)) {
				header = new String[41];
				header[0] = "Summary Header ID";
				header[1] = "Team Name";
				header[2] = "Team code";
				header[3] = "Master STN No.";
				header[4] = "Date of STN";
				header[5] = "Destination Location";
				header[6] = "STN Value";
				header[7] = "Master L.R./Airway No.";
				header[8] = "Master L.R./Airway date";

				header[9] = "Master STN Transporter";
				header[10] = "Weight in Kgs";
				header[11] = "Billable Weight in Kgs";
				header[12] = "Total No. of cases";

				header[13] = "Master Tentative Delivery Date";
				header[14] = "MST RECEIVED BY";
				header[15] = "MST ACTUAL DELIVERY DATE"; // Changed
				header[16] = "MST WAY BILL NO"; // Changed
				header[17] = "Master LR_Charges(including Taxes)";
				header[18] = "MST COURIER REMARK"; // Changed

				header[19] = "Employee No.";
				header[20] = "Employee Name";
				header[21] = "Individual Challan No.";
				header[22] = "Date of Individual Challan";
				header[23] = "City";
				header[24] = "Transporter";
				header[25] = "L.R. No. Individual challan";
				header[26] = "DTL L.R. Date";

				header[27] = "DTL Tentative Delivery Date";

				header[28] = "Weight in Kgs";
				header[29] = "Billable Weight in Kgs";
				header[30] = "Total No. of cases";

				// Added Later
				header[31] = "DTL ACTUAL DELIVERY DATE"; // Changed
				header[32] = "DTL RECEIVED BY";
				header[33] = "DTL WAY BILL NO"; // Changed
				header[34] = "DTL LR_Charges(including Taxes)";
				header[35] = "DTL COURIER REMARK"; // Changed
				header[36] = "FINYEAR";

				header[37] = "MST TRANSPORTER GSTREGNO";
				header[38] = "MST VEHICLE NO";
				header[39] = "DTL TRANSPORTER GSTREGNO";
				header[40] = "DTL VEHICLE NO";

			} else if (compcd.trim().equals(PFZ)) { //added by nilesh 14 Jan

				if (gprmInd.equalsIgnoreCase(Y)) {
					header = new String[40];
				} else {
					header = new String[39];
				}
				header[0] = "Summary Header ID";
				header[1] = "Team Name";
				header[2] = "Team code";
				header[3] = "Master STN No.";
				header[4] = "Date of STN";
				header[5] = "Destination Location";
				header[6] = "STN Value";
				header[7] = "Master L.R./Airway No.";
				header[8] = "Master L.R./Airway date";

				header[9] = "Master STN Transporter";
				header[10] = "Weight in Kgs";
				header[11] = "Billable Weight in Kgs";
				header[12] = "Total No. of cases";

				header[13] = "Master Tentative Delivery Date";
				header[14] = "MST RECEIVED BY";
				header[15] = "MST ACTUAL DELIVERY DATE"; // Changed
				header[16] = "MST WAY BILL NO"; // Changed
				header[17] = "Master LR_Charges(including Taxes)";
				header[18] = "MST COURIER REMARK"; // Changed

				header[19] = "Employee No.";
				header[20] = "Employee Name";
				header[21] = "Individual Challan No.";
				header[22] = "Date of Individual Challan";
				header[23] = "City";
				header[24] = "Transporter";
				header[25] = "L.R. No. Individual challan";
				header[26] = "DTL L.R. Date";

				header[27] = "DTL Tentative Delivery Date";

				header[28] = "Weight in Kgs";
				header[29] = "Billable Weight in Kgs";
				header[30] = "Total No. of cases";

				// Added Later
				header[31] = "DTL ACTUAL DELIVERY DATE";
				header[32] = "DTL RECEIVED BY";
				header[33] = "DTL WAY BILL NO";
				header[34] = "DTL LR_Charges(including Taxes)";
				header[35] = "PSO RESIGNED/STOCK RETURNED DETAILS";
				header[36] = "FINYEAR";
				if (gprmInd.equalsIgnoreCase(Y)) {
					header[37] = "DSP PROD TYPE";
//					header[38] = "TRANSPORTER GST REG NO";
					header[38] = "TRANSPORT MODE";
					header[39] = "PINCODE";
				} else {
//					header[37] = "TRANSPORTER GST REG NO";
					header[37] = "TRANSPORT MODE";
					header[38] = "PINCODE";

				}

			} else {
				if (gprmInd.equalsIgnoreCase(Y)) {
					header = new String[38];// changed yash 15 jul +1  //changed nilesh 14 Jan -1
				} else {
					header = new String[37];// changed yash 15 jul +1 //changed nilesh 14 Jan -1
				}
				header[0] = "Summary Header ID";
				header[1] = "Team Name";
				header[2] = "Team code";
				header[3] = "Master STN No.";
				header[4] = "Date of STN";
				header[5] = "Destination Location";
				header[6] = "STN Value";
				header[7] = "Master L.R./Airway No.";
				header[8] = "Master L.R./Airway date";

				header[9] = "Master STN Transporter";
				header[10] = "Weight in Kgs";
				header[11] = "Billable Weight in Kgs";
				header[12] = "Total No. of cases";

				header[13] = "Master Tentative Delivery Date";
				header[14] = "MST RECEIVED BY";
				header[15] = "MST ACTUAL DELIVERY DATE"; // Changed
				header[16] = "MST WAY BILL NO"; // Changed
				header[17] = "Master LR_Charges(including Taxes)";
				header[18] = "MST COURIER REMARK"; // Changed

				header[19] = "Employee No.";
				header[20] = "Employee Name";
				header[21] = "Individual Challan No.";
				header[22] = "Date of Individual Challan";
				header[23] = "City";
				header[24] = "Transporter";
				header[25] = "L.R. No. Individual challan";
				header[26] = "DTL L.R. Date";

				header[27] = "DTL Tentative Delivery Date";

				header[28] = "Weight in Kgs";
				header[29] = "Billable Weight in Kgs";
				header[30] = "Total No. of cases";

				// Added Later
				header[31] = "DTL ACTUAL DELIVERY DATE";
				header[32] = "DTL RECEIVED BY";
				header[33] = "DTL WAY BILL NO";
				header[34] = "DTL LR_Charges(including Taxes)";
				if (compcd.trim().equals(PFZ))
					header[35] = "PSO RESIGNED/STOCK RETURNED DETAILS";
				else
					header[35] = "DTL COURIER REMARK";
				header[36] = "FINYEAR";
				if (gprmInd.equalsIgnoreCase(Y)) {
					header[37] = "DSP PROD TYPE";
//					header[38] = "TRANSPORTER GST REG NO";// changed yash 15 jul // changed nilesh 14 Jan 
				} else {
//					header[37] = "TRANSPORTER GST REG NO";// changed yash 15 jul // changed nilesh 14 Jan 
				}
			}
			/*
			 * header[30] = "ADDRESS 1"; header[31] = "ADDRESS 2"; header[32] = "ADDRESS 3";
			 * header[33] = "ADDRESS 4"; header[34] = "ADDRESS 5"; header[35] = "MOBILE NO";
			 * header[36] = "E-MAIL ID"; header[37] = "REMARKS"; header[38] =
			 * "PSO RESIGNED/STOCK RETURNED DETAILS";
			 */

			List<String[]> li = new ArrayList<String[]>();
			li.add(header);
			System.out.println("Number of recs " + lst.size());
			for (Lrcsv_RevisedDownload_SG lrup : lst) {
				String[] arr = null;
				if (compcd.trim().equals(VET)) {
					arr = new String[41];
				} else {
					if (gprmInd.equalsIgnoreCase(Y)) {
						arr = new String[40];// changed yash 15 jul
					} else {
						arr = new String[38];// changed yash 15 jul
					}

				}
				arr[0] = lrup.getSum_hd_id();
				arr[1] = lrup.getTeam_name() != null
						? lrup.getTeam_name().replaceAll(",", ".").replaceAll("\\s+", " ").trim()
						: "";
				arr[2] = lrup.getTeam_code();
				arr[3] = lrup.getMst_stn_no();
				arr[4] = lrup.getMst_stn_dt() != null ? df.format(lrup.getMst_stn_dt()) : "";
				arr[5] = lrup.getMst_destination() == null ? ""
						: lrup.getMst_destination().trim().replaceAll(",", ".").replaceAll("\\s+", " ").trim();
				arr[6] = lrup.getStn_value();
				arr[7] = lrup.getMst_lr_no();
				arr[8] = lrup.getMst_lr_dt() != null ? df.format(lrup.getMst_lr_dt()) : "";

				arr[9] = lrup.getMst_transporter() != null
						? lrup.getMst_transporter().replaceAll(",", ".").replaceAll("\\s+", " ").trim()
						: "";
				arr[10] = lrup.getMst_grs_wght();
				arr[11] = lrup.getMst_billwght();
				arr[12] = lrup.getMst_tot_case();

				arr[13] = lrup.getMst_tent_delivery_date() != null ? df.format(lrup.getMst_tent_delivery_date()) : "";
				arr[14] = lrup.getMst_recd_by() != null
						? lrup.getMst_recd_by().replaceAll(",", ".").replaceAll("\\s+", " ").trim()
						: "";
				arr[15] = lrup.getMst_actual_delivery_date() != null ? df.format(lrup.getMst_actual_delivery_date())
						: "";
				arr[16] = lrup.getMst_way_bill_no(); // Mst WayBill
				arr[17] = lrup.getMst_courier_expenses();
				if (lrup.getMst_courier_remark() == null || lrup.getMst_courier_remark().trim().equals("")) // Changed
					arr[18] = ".";
				else
					arr[18] = lrup.getMst_courier_remark(); // Changed

				arr[19] = lrup.getDspfstaff_employeeno();
				arr[20] = lrup.getDspfstaff_displayname() != null
						? lrup.getDspfstaff_displayname().replaceAll(",", ".").replaceAll("\\s+", " ").trim()
						: "";
				arr[21] = lrup.getDtl_chln_no();
				arr[22] = lrup.getDtl_chln_dt() != null ? df.format(lrup.getDtl_chln_dt()) : "";
				arr[23] = lrup.getDtl_city() != null
						? lrup.getDtl_city().replaceAll(",", ".").replaceAll("\\s+", " ").trim()
						: "";
				arr[24] = lrup.getDtl_transporter() != null
						? lrup.getDtl_transporter().replaceAll(",", ".").replaceAll("\\s+", " ").trim()
						: "";
				arr[25] = lrup.getDtl_lr_no();
				arr[26] = lrup.getDtl_lr_dt() != null ? df.format(lrup.getDtl_lr_dt()) : "";

				arr[27] = lrup.getDtl_tent_delivery_date() != null ? df.format(lrup.getDtl_tent_delivery_date()) : "";

				arr[28] = lrup.getDtl_stn_grswght();
				arr[29] = lrup.getDtl_stn_billwght();
				arr[30] = lrup.getDtl_tot_case();

				arr[31] = lrup.getDtl_actual_delivery_date() != null ? df.format(lrup.getDtl_actual_delivery_date())
						: "";
				arr[32] = lrup.getDtl_recd_by() != null
						? lrup.getDtl_recd_by().replaceAll(",", ".").replaceAll("\\s+", " ").trim()
						: "";
				arr[33] = lrup.getWay_bill_no();
				arr[34] = lrup.getDtl_courier_expenses();
				if (lrup.getDtl_courier_remark() == null || lrup.getDtl_courier_remark().trim().equals("")) // Changed
					arr[35] = ".";
				else
					arr[35] = lrup.getDtl_courier_remark(); // Changed

				arr[36] = lrup.getDsp_fin_year();

				if (compcd.trim().equals(VET)) {
					arr[37] = "";
					arr[38] = "";
					arr[39] = "";
					arr[40] = ".";
				} else {
					if (gprmInd.equalsIgnoreCase(Y)) {
						arr[37] = lrup.getDsp_prod_type();
//					arr[38] = lrup.getDsp_trans_gst_reg_no();//changed yash 15 jul 
						
						if (compcd.trim().equals(PFZ)) {  //changed nilesh 14 Jan
							arr[38] = (lrup.getDtl_transport_mode() == null || lrup.getDtl_transport_mode().trim().isEmpty()) ? "" : lrup.getDtl_transport_mode();
							arr[39] = (lrup.getDtl_pincode() == null || lrup.getDtl_pincode().trim().isEmpty()) ? "" : lrup.getDtl_pincode();
						}
					}
					// ----changed yash 15 jul
					else {
//					arr[37] = lrup.getDsp_trans_gst_reg_no();
						
						if (compcd.trim().equals(PFZ)) {  //changed nilesh 14 Jan
							arr[37] = (lrup.getDtl_transport_mode() == null || lrup.getDtl_transport_mode().trim().isEmpty()) ? "" : lrup.getDtl_transport_mode();
						}
					}
					// ----changed yash 15 jul

				}
				/*
				 * if (lrup.getDtl_remark() == null || lrup.getDtl_remark().trim().equals(""))
				 * arr[29] = "."; else arr[29] = lrup.getDtl_remark();
				 * 
				 * if (lrup.getAddr1().contains(",")) { String add1 =
				 * lrup.getAddr1().replaceAll(",", ";"); arr[30] = add1; } else { arr[30] =
				 * lrup.getAddr1(); }
				 * 
				 * if (lrup.getAddr2().contains(",")) { String add2 =
				 * lrup.getAddr2().replaceAll(",", ";"); arr[31] = add2; } else { arr[31] =
				 * lrup.getAddr2(); }
				 * 
				 * if (lrup.getAddr3().contains(",")) { String add3 =
				 * lrup.getAddr3().replaceAll(",", ";"); arr[32] = add3; } else { arr[32] =
				 * lrup.getAddr3(); }
				 * 
				 * if (lrup.getAddr4().contains(",")) { String add4 =
				 * lrup.getAddr4().replaceAll(",", ";"); arr[33] = add4; } else { arr[33] =
				 * lrup.getAddr4(); }
				 * 
				 * if (lrup.getAddr5().contains(",")) { String add5 =
				 * lrup.getAddr5().replaceAll(",", ";"); arr[34] = add5; } else { arr[34] =
				 * lrup.getAddr5(); }
				 * 
				 * if (lrup.getMobile().contains(",")) { String mobile =
				 * lrup.getMobile().replaceAll(",", ";"); arr[35] = mobile; } else { arr[35] =
				 * lrup.getMobile(); }
				 * 
				 * if (lrup.getEmail().contains(",")) { String email =
				 * lrup.getEmail().replaceAll(",", ";"); arr[36] = email; } else { arr[36] =
				 * lrup.getEmail(); }
				 * 
				 * if (lrup.getPso_remark().contains(",")) { String psoRemarks =
				 * lrup.getPso_remark().replaceAll(",", ";"); arr[37] = psoRemarks; } else {
				 * arr[37] = lrup.getPso_remark(); }
				 * 
				 * if (lrup.getPso_rtn_details().contains(",")) { String psoRtnDetails =
				 * lrup.getPso_rtn_details().replaceAll(",", ";"); arr[37] = psoRtnDetails; }
				 * else { arr[38] = lrup.getPso_rtn_details(); }
				 */

				li.add(arr);
			}
			writer.writeAll(li);

			File csvfile = new File(path.toString());
			FileInputStream fileInputStream = new FileInputStream(csvfile);
			writer.close();

			fileName = usermasterservice.generateExcellock(path.toString(), fileName, empId, ".csv");

		} catch (Exception e) {
			System.out.println("Error Occurred :" + e.getMessage());// uncomment asneeded --;
			throw e;
		}
		return fileName;
	}

}
