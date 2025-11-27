package com.excel.utility;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.PreparedStatement;
import java.sql.ResultSetMetaData;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFDataFormat;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.ss.usermodel.BorderStyle;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.Color;
import org.apache.poi.ss.usermodel.FillPatternType;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.apache.poi.ss.util.CellRangeAddress;

import com.excel.bean.EmailFieldStaffModel;

public class EmailToMgr {
	public String EmailToMgr(String empId, Long divId, Long locId,String companyName,List<Object> list) { 
		SendMail sm = new SendMail();
//		Connection con = null;
//		ResultSet rs = null;

		// WritableWorkbook wwbook = null;
		ByteArrayOutputStream outputStream = null;
		// WritableWorkbook workbook = null;

		long timeVar = 0L;

		/*
		 * String filePath =""; String fpath =
		 * ServletActionContext.getRequest().getSession().getServletContext().
		 * getRealPath("")+"\\"+filePath;
		 */
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
		DecimalFormat df = new DecimalFormat("##.##");
		/*
		 * NumberFormat nf2 = new NumberFormat("#0.00"); NumberFormat df = new
		 * NumberFormat("0"); NumberFormat nf = new NumberFormat("#");
		 */
		Date dt = null;
		try {
//		    con = DBConnector.getConnection();
//		    String SPsql = "EXEC dispatch_details_EMAIL_TO_MGR ?,?,?,?,?";
//		    PreparedStatement pstmt = con.prepareStatement(SPsql);
//		    pstmt.setEscapeProcessing(true);
//		    // pstmt.setQueryTimeout(100);
//		    pstmt.setString(1, divId);
//		    pstmt.setString(2, "D");
//		    pstmt.setString(3, locId);
//		    pstmt.setString(4, "A");
//		    pstmt.setString(5, empId);
//		    rs = pstmt.executeQuery();
//
//		    ResultSetMetaData rsMetaData = rs.getMetaData();
//		    int columnCount = rsMetaData.getColumnCount();
//		    ArrayList<Object> result = new ArrayList<Object>();
//
//		    while (rs.next()) {
//			Object[] str = new Object[columnCount];
//			for (int i = 1; i <= columnCount; ++i) {
//			    Object obj = rs.getObject(i);
//			    str[i - 1] = obj;
//			}
//			result.add(str);
//		    }
//		    if (result.size() == 0) {
//			return "Sorry No Data Found for Dispatch...!!!";
//		    }

			
		    EmailFieldStaffModel emailFstaffObj = null;
		    List<EmailFieldStaffModel> model = new ArrayList<EmailFieldStaffModel>();

		    List<Object> objArr = list;
		    List<Date> dateList = new ArrayList<Date>();
		    int counter = 0;
		    counter = 14;
		    String maxDspDate = "", minDspDate = "";
		    for (Object obj : objArr) {
			Object[] object = (Object[]) obj;

			if (object[42] != null) {
			    counter = 14;
			    emailFstaffObj = new EmailFieldStaffModel();
			    emailFstaffObj.setSumdsp_id(object[0] != null ? object[0].toString() : "");
			    emailFstaffObj.setDiv_disp_nm(object[1] != null ? object[1].toString() : "");
			    emailFstaffObj.setDiv_map_cd(object[2] != null ? object[2].toString() : "");
			    emailFstaffObj.setSumdsp_challan_no(object[3] != null ? object[3].toString() : "");
			    emailFstaffObj.setSumdsp_challan_dt(object[4] != null ? object[4].toString() : "");
			    emailFstaffObj.setSumdsp_destination(object[5] != null ? object[5].toString() : "");
			    emailFstaffObj.setSumdsp_total_goods_val(
				    object[6] != null ? Double.parseDouble(object[6].toString()) : 0.0);
			    emailFstaffObj.setSumdsp_transporter(object[7] != null ? object[7].toString() : "");
			    emailFstaffObj.setSumdsp_lr_no(object[8] != null ? object[8].toString() : "");
			    emailFstaffObj.setSumdsp_lr_dt(object[9] != null ? object[9].toString() : "");
			    emailFstaffObj.setSumdsp_totcases(object[10] != null ? object[10].toString() : "");
			    emailFstaffObj.setDspfstaff_employeeno(object[11] != null ? object[11].toString() : "");
			    emailFstaffObj.setDspfstaff_displayname(object[12] != null ? object[12].toString() : "");
			    emailFstaffObj.setDspfstaff_desg(object[13] != null ? object[13].toString() : "");
			    emailFstaffObj.setHq_name(object[counter] != null ? object[counter].toString() : "");
			    counter++;
			    emailFstaffObj.setTer_code(object[counter] != null ? object[counter].toString() : "");
			    counter++;
			    emailFstaffObj.setTer_name(object[counter] != null ? object[counter].toString() : "");
			    counter++;
			    emailFstaffObj.setDsp_challan_no(object[counter] != null ? object[counter].toString() : "");
			    counter++;
			    emailFstaffObj.setDsp_challan_dt(object[counter] != null ? object[counter].toString() : "");
			    counter++;
			    emailFstaffObj.setDsp_total_goods_val(
				    object[counter] != null ? Double.parseDouble(object[counter].toString()) : 0.0);
			    counter++;

			    emailFstaffObj.setDsp_transporter(object[counter] != null ? object[counter].toString() : "");
			    counter++;
			    emailFstaffObj.setDsp_lr_no(object[counter] != null ? object[counter].toString() : "");
			    counter++;
			    emailFstaffObj.setDsp_lr_dt(object[counter] != null ? object[counter].toString() : "");
			    counter++;

			    emailFstaffObj.setDsp_cases(object[counter] != null ? object[counter].toString() : "");
			    counter++;
			    emailFstaffObj.setSmp_prod_cd(object[counter] != null ? object[counter].toString() : "");
			    counter++;
			    emailFstaffObj.setSmp_prod_name(object[counter] != null ? object[counter].toString() : "");
			    counter++;
			    emailFstaffObj.setSmp_prod_type(object[counter] != null ? object[counter].toString() : "");
			    counter++;

			    emailFstaffObj.setBatch_no(object[counter] != null ? object[counter].toString() : "");
			    counter++;
			    emailFstaffObj.setBatch_expiry_dt(object[counter] != null ? object[counter].toString() : "");
			    counter++;

			    emailFstaffObj.setDspdtl_disp_qty(
				    object[counter] != null ? Double.parseDouble(object[counter].toString()) : 0);
			    counter++;
			    emailFstaffObj.setDspdtl_rate(
				    object[counter] != null ? Double.parseDouble(object[counter].toString()) : 0);
			    counter++;
			    emailFstaffObj
				    .setValue(object[counter] != null ? Double.parseDouble(object[counter].toString()) : 0);
			    counter++;
			    emailFstaffObj.setWarehouse(object[counter] != null ? object[counter].toString() : "");
			    counter++;
			    emailFstaffObj.setPosition(object[counter] != null ? object[counter].toString() : "");
			    counter++;
			    emailFstaffObj.setMiobile(object[counter] != null ? object[counter].toString() : "");
			    counter++;
			    emailFstaffObj.setDsp_act_wt(
				    object[counter] != null ? Double.parseDouble(object[counter].toString()) : 0);
			    counter++;
			    emailFstaffObj.setDsp_bill_wt(
				    object[counter] != null ? Double.parseDouble(object[counter].toString()) : 0);
			    counter++;
			    emailFstaffObj.setSum_act_wt(
				    object[counter] != null ? Double.parseDouble(object[counter].toString()) : 0);
			    counter++;
			    emailFstaffObj.setSum_bill_wt(
				    object[counter] != null ? Double.parseDouble(object[counter].toString()) : 0);
			    counter++;
			    emailFstaffObj.setState(object[counter] != null ? object[counter].toString() : "");
			    counter++;
			    emailFstaffObj.setRegion(object[counter] != null ? object[counter].toString() : "");
			    counter++;
			    emailFstaffObj.setAm_name(object[counter] != null ? object[counter].toString() : "");
			    counter++;
			    // System.out.println("###############################COUNTER
			    // LOOP################################"+counter);
			    emailFstaffObj.setRm_name(object[counter] != null ? object[counter].toString() : "");
			    counter++;
			    emailFstaffObj.setEmail1(object[counter] != null ? object[counter].toString() : "");
			    counter++;
			    emailFstaffObj.setEmail2(object[counter] != null ? object[counter].toString() : "");
			    counter++;
			    emailFstaffObj
				    .setFstaff_id(object[counter] != null ? Integer.parseInt(object[counter].toString()) : 0);
			    counter++;
			    emailFstaffObj.setFstaff_mgr1_id(
				    object[counter] != null ? Integer.parseInt(object[counter].toString()) : 0);
			    counter++;
			    emailFstaffObj.setFstaff_mgr2_id(
				    object[counter] != null ? Integer.valueOf(object[counter].toString()) : 0);
			    model.add(emailFstaffObj);
			    dateList.add(sdf.parse(emailFstaffObj.getSumdsp_challan_dt()));
			}
		    }

		    Collections.sort(dateList);

		    System.out.println("after" + dateList);
		    int srno = 1;
		    if (model.size() != 0) {
			int newFstaffId = -1, oldFstaffId = -1, newpsoId = -1, oldpsoId = -1;
			boolean check = false;
			EmailFieldStaffModel eModel;

			HSSFWorkbook workbook = null;
			HSSFSheet sheet = null;
			org.apache.poi.ss.usermodel.Row row = null;
			Cell cell = null;
			int rowNum = 0;

			HSSFCellStyle alignleft = null;
			HSSFCellStyle styleBoldalignLeft = null;
			HSSFCellStyle alignright = null;
			HSSFCellStyle alignCenter = null;
			HSSFCellStyle style = null;
			HSSFDataFormat format = null;
			String newEmail2 = "", oldEmail2 = "";
			String oldDivision = "", oldDivCode = "";
			String newDivision = "", newDivCode = "";
			int mk=0;
			for (EmailFieldStaffModel m : model) {
				mk++; 
				if(mk==226) {
					System.out.println("gg");
				}
			    System.out.println("In list");
			    System.out.println(m);
			    newFstaffId = m.getFstaff_mgr1_id();
			    System.out.println("EmailToMgr.EmailToMgr()sdsadsadasdad");
			    newpsoId = m.getFstaff_id();
			    newEmail2 = m.getEmail2();
			    newDivision = m.getDiv_disp_nm();
			    newDivCode = m.getDiv_map_cd();

			    if (newFstaffId == oldFstaffId) {

				/** Change of Field Staff Name **/
				if (newpsoId != oldpsoId) {
				    row = sheet.createRow(++rowNum);

				    row = sheet.createRow(++rowNum);
				    int rownum = 0;
				    cell = row.createCell(rownum++);
				    cell.setCellValue(srno++);
				    cell.setCellStyle(alignright);

				    cell = row.createCell(rownum++);
				    cell.setCellValue(m.getDiv_disp_nm());
				    cell.setCellStyle(alignleft);

				    cell = row.createCell(rownum++);
				    cell.setCellValue(m.getDiv_map_cd());
				    cell.setCellStyle(alignleft);

				    cell = row.createCell(rownum++);
				    cell.setCellValue(m.getRm_name());
				    cell.setCellStyle(alignleft);

				    cell = row.createCell(rownum++);
				    cell.setCellValue(m.getAm_name());
				    cell.setCellStyle(alignleft);

				    cell = row.createCell(rownum++);//
				    cell.setCellValue(m.getDspfstaff_displayname());
				    cell.setCellStyle(styleBoldalignLeft);

				    cell = row.createCell(rownum++);
				    cell.setCellValue(m.getSumdsp_destination());
				    cell.setCellStyle(alignleft);

				    cell = row.createCell(rownum++);
				    cell.setCellValue(m.getTer_code());
				    cell.setCellStyle(alignleft);

				    cell = row.createCell(rownum++);
				    cell.setCellValue(m.getTer_name());
				    cell.setCellStyle(alignleft);

				    // *************************************//

				    cell = row.createCell(rownum++);//
				    cell.setCellValue(m.getDsp_challan_no());
				    cell.setCellStyle(alignleft);

				    cell = row.createCell(rownum++);
				    cell.setCellValue(m.getDsp_challan_dt());
				    cell.setCellStyle(alignright);

				    cell = row.createCell(rownum++);
				    cell.setCellValue(m.getDsp_total_goods_val());
				    cell.setCellStyle(alignright);

				    cell = row.createCell(rownum++);
				    cell.setCellValue(m.getDsp_lr_no());
				    cell.setCellStyle(alignright);

				    cell = row.createCell(rownum++);
				    cell.setCellValue(m.getDsp_lr_dt());
				    cell.setCellStyle(alignright);

				    cell = row.createCell(rownum++);
				    cell.setCellValue(m.getDsp_cases());
				    cell.setCellStyle(alignright);

				    // ************************************PRODUCTS*********//

				    cell = row.createCell(rownum++);
				    cell.setCellValue(m.getSmp_prod_name());
				    cell.setCellStyle(alignleft);

				    cell = row.createCell(rownum++);
				    cell.setCellValue(m.getSmp_prod_cd());
				    cell.setCellStyle(alignleft);

				    cell = row.createCell(rownum++);
				    cell.setCellValue(m.getBatch_no());
				    cell.setCellStyle(alignright);

				    cell = row.createCell(rownum++);
				    cell.setCellValue(m.getDspdtl_disp_qty());
				    cell.setCellStyle(alignright);

				    cell = row.createCell(rownum++);
				    cell.setCellValue(String.format("%.2f", m.getDspdtl_rate()));
				    cell.setCellStyle(alignright);

				    cell = row.createCell(rownum++);
				    cell.setCellValue(String.format("%.2f", m.getValue()));
				    cell.setCellStyle(alignright);

				} else {

				    row = sheet.createRow(++rowNum);
				    int prodCounter = 15;
				    cell = row.createCell(prodCounter++);
				    cell.setCellValue(m.getSmp_prod_name());
				    cell.setCellStyle(alignleft);

				    cell = row.createCell(prodCounter++);
				    cell.setCellValue(m.getSmp_prod_cd());
				    cell.setCellStyle(alignleft);

				    cell = row.createCell(prodCounter++);
				    cell.setCellValue(m.getBatch_no());
				    cell.setCellStyle(alignleft);

				    cell = row.createCell(prodCounter++);
				    cell.setCellValue(m.getDspdtl_disp_qty());
				    cell.setCellStyle(alignright);

				    cell = row.createCell(prodCounter++);
				    cell.setCellValue(String.format("%.2f", m.getDspdtl_rate()));
				    cell.setCellStyle(alignright);

				    cell = row.createCell(prodCounter++);
				    cell.setCellValue(String.format("%.2f", m.getValue()));
				    cell.setCellStyle(alignright);

				}

			    } else {
				if (check) {
					
				    try {

					outputStream = new ByteArrayOutputStream();
					workbook.write(outputStream);

					System.out.println("Excel written successfully..1");
					// Changes made for division wise email to CCs
					// by rahul 21 march
					// String subject = m.getDiv_disp_nm() +
					// "_Dispatch details as on_";
					String subject = oldDivision + "_Dispatch details as on_";
					// String to = "medicoedss@gmail.com";
					StringBuffer body = new StringBuffer();
					body.append("Dispatch details as on ");

					if (oldEmail2 != null) {
					    List<String> emailList = new ArrayList<String>();
					    emailList.add(oldEmail2); // m.getEmail2()
								      // Changes When
								      // Sending to RM

					    /*
					     * List<Email> edao = new
					     * Email_Dao().getParameteDetailsByCode(
					     * "DSPREPORT"); List<String> emailList =
					     * new ArrayList<String>(); for(Email email
					     * : edao) {
					     * emailList.add(email.getEmail()); }
					     */

					    System.out.println("$#$#$#$#$$#$" + emailList);
					    String cc = "Y";
					    System.out.println("DDDDDD");
					    System.out.println(oldDivision);
					    System.out.println(oldDivCode);
					    sm.sendExcelMail(emailList, subject, body.toString(), outputStream.toByteArray(),
						    oldDivision + "Dispatch details", cc, oldDivCode);
					}
				    } catch (FileNotFoundException e) {
					System.out.println("Error Occurred :" +e.getMessage());// uncomment asneeded --;
				    } catch (IOException e) {
					System.out.println("Error Occurred :" +e.getMessage());// uncomment asneeded --;
				    } catch (Exception e) {
					System.out.println("Error Occurred :" +e.getMessage());// uncomment asneeded --;
				    }

				}
				check = true;
				rowNum = 0;
				srno = 1;
				workbook = new HSSFWorkbook();
				
				
				
				sheet = workbook.createSheet("Sample sheet");

				format = workbook.createDataFormat();

				alignright = workbook.createCellStyle();
				alignright.setAlignment(HorizontalAlignment.RIGHT);

				alignleft = workbook.createCellStyle();
				alignleft.setAlignment(HorizontalAlignment.LEFT);

				alignCenter = workbook.createCellStyle();
				alignCenter.setAlignment(HorizontalAlignment.RIGHT);
				// alignCenter.setFont(HSSFFont.);

				Font font = workbook.createFont();
				font.setFontHeight((short) 320);
				style = workbook.createCellStyle();
				style.setFont(font);
				style.setAlignment(HorizontalAlignment.CENTER);

		//		HSSFColor lightGray = setColor(workbook, (byte) 0xC0, (byte) 0xC0, (byte) 0xC0);
				style.setFillForegroundColor(HSSFColor.HSSFColorPredefined.GREY_25_PERCENT.getIndex());
				style.setFillPattern(FillPatternType.SOLID_FOREGROUND);

				Font fontBold = workbook.createFont();
				fontBold.setBold(true);
				styleBoldalignLeft = workbook.createCellStyle();
				styleBoldalignLeft.setFont(fontBold);
				styleBoldalignLeft.setAlignment(HorizontalAlignment.LEFT);

				org.apache.poi.ss.usermodel.Row header = sheet.createRow(rowNum);
				cell = header.createCell(0);
				cell.setCellValue(companyName);
				cell.setCellStyle(style);
				// cell.setCellStyle();

				sheet.addMergedRegion(new CellRangeAddress(0, 2, 0, 20));

				rowNum += 3;

				header = sheet.createRow(rowNum++);
				cell = header.createCell(0);
				cell.setCellValue("RM  " + " Dispatch details as on " + sdf.format(dateList.get(0)) + "-"
					+ sdf.format(dateList.get(dateList.size() - 1)));
				cell.setCellStyle(style);

				sheet.addMergedRegion(new CellRangeAddress(3, 4, 0, 20));

				rowNum++;
				style = workbook.createCellStyle();
				//lightGray = setColor(workbook, (byte) 0xBA, (byte) 0xD7, (byte) 0x8E);

				style.setFillForegroundColor(HSSFColor.HSSFColorPredefined.GREY_25_PERCENT.getIndex());
				style.setFillPattern(FillPatternType.SOLID_FOREGROUND);

				style.setBorderRight(BorderStyle.MEDIUM);
				style.setBorderBottom(BorderStyle.MEDIUM);

				header = sheet.createRow(++rowNum);
				int headercount = 0;
				header.createCell(headercount++).setCellValue("Sr. No.");
				header.createCell(headercount++).setCellValue("Team Name");
				header.createCell(headercount++).setCellValue("Team code");
				header.createCell(headercount++).setCellValue("RM NAME");
				header.createCell(headercount++).setCellValue("AM NAME");
				header.createCell(headercount++).setCellValue("Field Force Name");
				header.createCell(headercount++).setCellValue("Destination Location");
				header.createCell(headercount++).setCellValue("Territory code");
				header.createCell(headercount++).setCellValue("Territory Name");
				header.createCell(headercount++).setCellValue("Individual Challan No.");
				header.createCell(headercount++).setCellValue("Date of Individual Challan ");
				header.createCell(headercount++).setCellValue("Individual challan value ");
				header.createCell(headercount++).setCellValue("L.R. No. Individual challan ");
				header.createCell(headercount++).setCellValue("Date ");
				header.createCell(headercount++).setCellValue("Total No. of cases ");
				header.createCell(headercount++).setCellValue("Product Name ");
				header.createCell(headercount++).setCellValue("Product code ");
				header.createCell(headercount++).setCellValue("Batch ");
				header.createCell(headercount++).setCellValue("Qty	 ");
				header.createCell(headercount++).setCellValue("Rate ");
				header.createCell(headercount).setCellValue("Value ");

				for (int i = 0; i <= headercount; i++) {
				    header.getCell(i).setCellStyle(style);
				    sheet.autoSizeColumn(i + 1);
				}

				row = sheet.createRow(++rowNum);
				int cellcount = 0;
				cell = row.createCell(cellcount++);
				cell.setCellValue(srno++);
				cell.setCellStyle(alignright);

				cell = row.createCell(cellcount++);
				cell.setCellValue(m.getDiv_disp_nm());
				cell.setCellStyle(alignleft);

				cell = row.createCell(cellcount++);
				cell.setCellValue(m.getDiv_map_cd());
				cell.setCellStyle(alignleft);

				cell = row.createCell(cellcount++);
				cell.setCellValue(m.getRm_name());
				cell.setCellStyle(alignleft);

				cell = row.createCell(cellcount++);
				cell.setCellValue(m.getAm_name());
				cell.setCellStyle(alignleft);

				cell = row.createCell(cellcount++);//
				cell.setCellValue(m.getDspfstaff_displayname());
				cell.setCellStyle(styleBoldalignLeft);

				cell = row.createCell(cellcount++);
				cell.setCellValue(m.getSumdsp_destination());
				cell.setCellStyle(alignleft);

				cell = row.createCell(cellcount++);
				cell.setCellValue(m.getTer_code());
				cell.setCellStyle(alignleft);

				cell = row.createCell(cellcount++);
				cell.setCellValue(m.getTer_name());
				cell.setCellStyle(alignleft);

				// *************************************//

				cell = row.createCell(cellcount++);//
				cell.setCellValue(m.getDsp_challan_no());
				cell.setCellStyle(alignleft);

				cell = row.createCell(cellcount++);
				cell.setCellValue(m.getDsp_challan_dt());
				cell.setCellStyle(alignright);

				cell = row.createCell(cellcount++);
				cell.setCellValue(m.getDsp_total_goods_val());
				cell.setCellStyle(alignright);

				cell = row.createCell(cellcount++);
				cell.setCellValue(m.getDsp_lr_no());
				cell.setCellStyle(alignright);

				cell = row.createCell(cellcount++);
				cell.setCellValue(m.getDsp_lr_dt());
				cell.setCellStyle(alignright);

				cell = row.createCell(cellcount++);
				cell.setCellValue(m.getDsp_cases());
				cell.setCellStyle(alignright);

				// ************************************PRODUCTS*********//

				cell = row.createCell(cellcount++);
				cell.setCellValue(m.getSmp_prod_name());
				cell.setCellStyle(alignleft);

				cell = row.createCell(cellcount++);
				cell.setCellValue(m.getSmp_prod_cd());
				cell.setCellStyle(alignleft);

				cell = row.createCell(cellcount++);
				cell.setCellValue(m.getBatch_no());
				cell.setCellStyle(alignright);

				cell = row.createCell(cellcount++);
				cell.setCellValue(m.getDspdtl_disp_qty());
				cell.setCellStyle(alignright);

				cell = row.createCell(cellcount++);
				cell.setCellValue(String.format("%.2f", m.getDspdtl_rate()));
				cell.setCellStyle(alignright);

				cell = row.createCell(cellcount++);
				cell.setCellValue(String.format("%.2f", m.getValue()));
				cell.setCellStyle(alignright);

				// row = sheet.createRow(++rowNum);

			    }
			    oldFstaffId = newFstaffId;
			    oldpsoId = newpsoId;
			    oldEmail2 = newEmail2;
			    oldDivision = newDivision;
			    oldDivCode = newDivCode;
			}

			try {
			    outputStream = new ByteArrayOutputStream();
			    workbook.write(outputStream);

			    System.out.println("Excel written successfully..2");

			    EmailFieldStaffModel m = model.get(model.size() - 1);
			    /*
			     * String subject = m.getDiv_disp_nm() +
			     * "Dispatch  Details as on " + sdf.format(dateList.get(0))
			     * + "-" + sdf.format(dateList.get(dateList.size() - 1));
			     */
			    String subject = oldDivision + "Dispatch  Details as on " + sdf.format(dateList.get(0)) + "-"
				    + sdf.format(dateList.get(dateList.size() - 1));
			    String to = "medicoedss@gmail.com";
			    StringBuffer body = new StringBuffer();
			    // body.append("Details of Dispatches
			    // ").append(stdt).append(" To ").append(enddt);
			    // List<Email> edao = new
			    // Email_Dao().getParameteDetailsByCode("DSPREPORT");
			    /*
			     * List<Email> edao = new
			     * Email_Dao().getParameteDetailsByCode("DSPREPORT");
			     * List<String> emailList = new ArrayList<String>();
			     * for(Email email : edao) {
			     * emailList.add(email.getEmail()); }
			     */
			    if (oldEmail2 != null) {
				List<String> emailList = new ArrayList<String>();
				emailList.add(oldEmail2); // m.getEmail2() Changes
							      // When Sending to RM

				System.out.println("$#$#$#$#$$#$" + emailList);
				String cc = "Y";
				// }
				System.out.println("DDDDDD");
				System.out.println(oldDivision);
				System.out.println(oldDivCode);
				sm.sendExcelMail(emailList, subject, body.toString(), outputStream.toByteArray(),
					oldDivision + " Dispatch details", cc, oldDivCode);
				System.out.println("Mail Sent");
			    }
			} catch (FileNotFoundException e) {
			    System.out.println("Error Occurred :" +e.getMessage());// uncomment asneeded --;
			} catch (IOException e) {
			    System.out.println("Error Occurred :" +e.getMessage());// uncomment asneeded --;
			}catch (Exception e) {
				System.out.println("Error Occurred :" +e.getMessage());// uncomment asneeded --;
			    }
		    }

		    /** FOr DM Sending Mails **/

		    srno = 1;
		    if (model.size() != 0) {
			int newFstaffId = -1, oldFstaffId = -1, newpsoId = -1, oldpsoId = -1;
			boolean check = false;
			EmailFieldStaffModel eModel;

			HSSFWorkbook workbook = null;
			HSSFSheet sheet = null;
			org.apache.poi.ss.usermodel.Row row = null;
			Cell cell = null;
			int rowNum = 0;

			HSSFCellStyle alignleft = null;
			HSSFCellStyle alignright = null;
			HSSFCellStyle alignCenter = null;
			HSSFCellStyle style = null;
			HSSFCellStyle styleBoldalignLeft = null;
			String oldEmail1 = "", newEmail1 = "";
			String oldDivision = "", oldDivCode = "";
			String newDivision = "", newDivCode = "";

			for (EmailFieldStaffModel m : model) {
			    if (m.getFstaff_mgr1_id() != 0) {
				newFstaffId = m.getFstaff_mgr1_id();
				newpsoId = m.getFstaff_id();
				newEmail1 = m.getEmail1();
				newDivision = m.getDiv_disp_nm();
				newDivCode = m.getDiv_map_cd();
				if (newFstaffId == oldFstaffId) {

				    /** Change of Field Staff Name **/

				    if (newpsoId != oldpsoId) {
					row = sheet.createRow(++rowNum);
					row = sheet.createRow(++rowNum);
					int rownum = 0;
					cell = row.createCell(rownum++);
					cell.setCellValue(srno++);
					cell.setCellStyle(alignright);

					cell = row.createCell(rownum++);
					cell.setCellValue(m.getDiv_disp_nm());
					cell.setCellStyle(alignleft);

					cell = row.createCell(rownum++);
					cell.setCellValue(m.getDiv_map_cd());
					cell.setCellStyle(alignleft);

					cell = row.createCell(rownum++);
					cell.setCellValue(m.getRm_name());
					cell.setCellStyle(alignleft);

					cell = row.createCell(rownum++);
					cell.setCellValue(m.getAm_name());
					cell.setCellStyle(alignleft);

					cell = row.createCell(rownum++);//
					cell.setCellValue(m.getDspfstaff_displayname());
					cell.setCellStyle(styleBoldalignLeft);

					cell = row.createCell(rownum++);
					cell.setCellValue(m.getSumdsp_destination());
					cell.setCellStyle(alignleft);

					cell = row.createCell(rownum++);
					cell.setCellValue(m.getTer_code());
					cell.setCellStyle(alignleft);

					cell = row.createCell(rownum++);
					cell.setCellValue(m.getTer_name());
					cell.setCellStyle(alignleft);

					cell = row.createCell(rownum++);//
					cell.setCellValue(m.getDsp_challan_no());
					cell.setCellStyle(alignleft);

					cell = row.createCell(rownum++);
					cell.setCellValue(m.getDsp_challan_dt());
					cell.setCellStyle(alignright);

					cell = row.createCell(rownum++);
					cell.setCellValue(m.getDsp_total_goods_val());
					cell.setCellStyle(alignright);

					cell = row.createCell(rownum++);
					cell.setCellValue(m.getDsp_lr_no());
					cell.setCellStyle(alignright);

					cell = row.createCell(rownum++);
					cell.setCellValue(m.getDsp_lr_dt());
					cell.setCellStyle(alignright);

					cell = row.createCell(rownum++);
					cell.setCellValue(m.getDsp_cases());
					cell.setCellStyle(alignright);

					// ************************************PRODUCTS*********//

					cell = row.createCell(rownum++);
					cell.setCellValue(m.getSmp_prod_name());
					cell.setCellStyle(alignleft);

					cell = row.createCell(rownum++);
					cell.setCellValue(m.getSmp_prod_cd());
					cell.setCellStyle(alignleft);

					cell = row.createCell(rownum++);
					cell.setCellValue(m.getBatch_no());
					cell.setCellStyle(alignright);

					cell = row.createCell(rownum++);
					cell.setCellValue(m.getDspdtl_disp_qty());
					cell.setCellStyle(alignright);

					cell = row.createCell(rownum++);
					cell.setCellValue(String.format("%.2f", m.getDspdtl_rate()));
					cell.setCellStyle(alignright);

					cell = row.createCell(rownum++);
					cell.setCellValue(String.format("%.2f", m.getValue()));
					cell.setCellStyle(alignright);

				    } else {

					row = sheet.createRow(++rowNum);
					int prodCounter = 15;
					cell = row.createCell(prodCounter++);
					cell.setCellValue(m.getSmp_prod_name());
					cell.setCellStyle(alignleft);

					cell = row.createCell(prodCounter++);
					cell.setCellValue(m.getSmp_prod_cd());
					cell.setCellStyle(alignleft);

					cell = row.createCell(prodCounter++);
					cell.setCellValue(m.getBatch_no());
					cell.setCellStyle(alignleft);

					cell = row.createCell(prodCounter++);
					cell.setCellValue(m.getDspdtl_disp_qty());
					cell.setCellStyle(alignright);

					cell = row.createCell(prodCounter++);
					cell.setCellValue(String.format("%.2f", m.getDspdtl_rate()));
					cell.setCellStyle(alignright);

					cell = row.createCell(prodCounter++);
					cell.setCellValue(String.format("%.2f", m.getValue()));
					cell.setCellStyle(alignright);

				    }

				} else {
				    if (check) {
					try {

					    outputStream = new ByteArrayOutputStream();
					    workbook.write(outputStream);

					    System.out.println("Excel written successfully..3");

					    // String subject = m.getDiv_disp_nm() +
					    // "_Dispatch details as on_";
					    String subject = oldDivision + "_Dispatch details as on_";
					    String to = "medicoedss@gmail.com";
					    StringBuffer body = new StringBuffer();
					    body.append("Dispatch details as on ");

					    if (oldEmail1 != null) {
						List<String> emailList = new ArrayList<String>();
						emailList.add(oldEmail1); // m.getEmail2()
									  // Changes
									  // When
									  // Sending to
									  // RM

						/*
						 * List<Email> edao = new
						 * Email_Dao().getParameteDetailsByCode(
						 * "DSPREPORT"); List<String> emailList
						 * = new ArrayList<String>(); for(Email
						 * email : edao) {
						 * emailList.add(email.getEmail()); }
						 */
						System.out.println("$#$#$#$#$$#$" + emailList);

						String cc = "Y";
						System.out.println("DDDDDD");
						System.out.println(oldDivision);
						System.out.println(oldDivCode);
						sm.sendExcelMail(emailList, subject, body.toString(),
							outputStream.toByteArray(), oldDivision + "Dispatch details", cc,
							oldDivCode);
					    }
					} catch (FileNotFoundException e) {
					    System.out.println("Error Occurred :" +e.getMessage());// uncomment asneeded --;
					} catch (IOException e) {
					    System.out.println("Error Occurred :" +e.getMessage());// uncomment asneeded --;
					}catch (Exception e) {
						System.out.println("Error Occurred :" +e.getMessage());// uncomment asneeded --;
					    }

				    }
				    check = true;
				    rowNum = 0;
				    workbook = new HSSFWorkbook();
				    sheet = workbook.createSheet("Sample sheet");

				    alignright = workbook.createCellStyle();
				    alignright.setAlignment(HorizontalAlignment.RIGHT);

				    alignleft = workbook.createCellStyle();
				    alignleft.setAlignment(HorizontalAlignment.LEFT);

				    alignCenter = workbook.createCellStyle();
				    alignCenter.setAlignment(HorizontalAlignment.CENTER);
				    // alignCenter.setFont(HSSFFont.);

				    Font font = workbook.createFont();
				    font.setFontHeight((short) 320);
				    style = workbook.createCellStyle();
				    style.setFont(font);
				    style.setAlignment(HorizontalAlignment.CENTER);

				    Font fontBold = workbook.createFont();
				    fontBold.setBold(true);
				    styleBoldalignLeft = workbook.createCellStyle();
				    styleBoldalignLeft.setFont(fontBold);
				    styleBoldalignLeft.setAlignment(HorizontalAlignment.LEFT);

				 //   HSSFColor lightGray = setColor(workbook, (byte) 0xC0, (byte) 0xC0, (byte) 0xC0);
				    style.setFillForegroundColor(HSSFColor.HSSFColorPredefined.GREY_25_PERCENT.getIndex());
				    style.setFillPattern(FillPatternType.SOLID_FOREGROUND);

				    org.apache.poi.ss.usermodel.Row header = sheet.createRow(rowNum);
				    cell = header.createCell(0);
				    cell.setCellValue(companyName);
				    cell.setCellStyle(style);
				    // cell.setCellStyle();

				    sheet.addMergedRegion(new CellRangeAddress(0, 2, 0, 20));

				    rowNum += 3;

				    header = sheet.createRow(rowNum++);
				    cell = header.createCell(0);
				    cell.setCellValue("DM  " + " Dispatch details as on " + sdf.format(dateList.get(0)) + "-"
					    + sdf.format(dateList.get(dateList.size() - 1)));
				    cell.setCellStyle(style);

				    sheet.addMergedRegion(new CellRangeAddress(3, 4, 0, 20));

				    rowNum++;
				    style = workbook.createCellStyle();
				    //lightGray = setColor(workbook, (byte) 0xBA, (byte) 0xD7, (byte) 0x8E);

				    style.setFillForegroundColor(HSSFColor.HSSFColorPredefined.GREY_25_PERCENT.getIndex());
				    style.setFillPattern(FillPatternType.SOLID_FOREGROUND);

				    style.setBorderRight(BorderStyle.MEDIUM);
				    style.setBorderBottom(BorderStyle.MEDIUM);

				    header = sheet.createRow(++rowNum);
				    int headercount = 0;
				    header.createCell(headercount++).setCellValue("Sr. No.");
				    header.createCell(headercount++).setCellValue("Team Name");
				    header.createCell(headercount++).setCellValue("Team code");
				    header.createCell(headercount++).setCellValue("RM NAME");
				    header.createCell(headercount++).setCellValue("AM NAME");
				    header.createCell(headercount++).setCellValue("Field Force Name");
				    header.createCell(headercount++).setCellValue("Destination Location");
				    header.createCell(headercount++).setCellValue("Territory code");
				    header.createCell(headercount++).setCellValue("Territory Name");
				    header.createCell(headercount++).setCellValue("Individual Challan No.");
				    header.createCell(headercount++).setCellValue("Date of Individual Challan ");
				    header.createCell(headercount++).setCellValue("Individual challan value ");
				    header.createCell(headercount++).setCellValue("L.R. No. Individual challan ");
				    header.createCell(headercount++).setCellValue("Date ");
				    header.createCell(headercount++).setCellValue("Total No. of cases ");
				    header.createCell(headercount++).setCellValue("Product Name ");
				    header.createCell(headercount++).setCellValue("Product code ");
				    header.createCell(headercount++).setCellValue("Batch ");
				    header.createCell(headercount++).setCellValue("Qty	 ");
				    header.createCell(headercount++).setCellValue("Rate ");
				    header.createCell(headercount).setCellValue("Value ");

				    for (int i = 0; i <= headercount; i++) {
					header.getCell(i).setCellStyle(style);
					sheet.autoSizeColumn(i + 1);
				    }

				    row = sheet.createRow(++rowNum);
				    int cellcount = 0;
				    cell = row.createCell(cellcount++);
				    cell.setCellValue(srno++);
				    cell.setCellStyle(alignright);

				    cell = row.createCell(cellcount++);
				    cell.setCellValue(m.getDiv_disp_nm());
				    cell.setCellStyle(alignleft);

				    cell = row.createCell(cellcount++);
				    cell.setCellValue(m.getDiv_map_cd());
				    cell.setCellStyle(alignleft);

				    cell = row.createCell(cellcount++);
				    cell.setCellValue(m.getRm_name());
				    cell.setCellStyle(alignleft);

				    cell = row.createCell(cellcount++);
				    cell.setCellValue(m.getAm_name());
				    cell.setCellStyle(alignleft);

				    cell = row.createCell(cellcount++);//
				    cell.setCellValue(m.getDspfstaff_displayname());
				    cell.setCellStyle(styleBoldalignLeft);

				    cell = row.createCell(cellcount++);
				    cell.setCellValue(m.getSumdsp_destination());
				    cell.setCellStyle(alignleft);

				    cell = row.createCell(cellcount++);
				    cell.setCellValue(m.getTer_code());
				    cell.setCellStyle(alignleft);

				    cell = row.createCell(cellcount++);
				    cell.setCellValue(m.getTer_name());
				    cell.setCellStyle(alignleft);

				    // *************************************//

				    cell = row.createCell(cellcount++);//
				    cell.setCellValue(m.getDsp_challan_no());
				    cell.setCellStyle(alignleft);

				    cell = row.createCell(cellcount++);
				    cell.setCellValue(m.getDsp_challan_dt());
				    cell.setCellStyle(alignright);

				    cell = row.createCell(cellcount++);
				    cell.setCellValue(m.getDsp_total_goods_val());
				    cell.setCellStyle(alignright);

				    cell = row.createCell(cellcount++);
				    cell.setCellValue(m.getDsp_lr_no());
				    cell.setCellStyle(alignright);

				    cell = row.createCell(cellcount++);
				    cell.setCellValue(m.getDsp_lr_dt());
				    cell.setCellStyle(alignright);

				    cell = row.createCell(cellcount++);
				    cell.setCellValue(m.getDsp_cases());
				    cell.setCellStyle(alignright);

				    // ************************************PRODUCTS*********//

				    cell = row.createCell(cellcount++);
				    cell.setCellValue(m.getSmp_prod_name());
				    cell.setCellStyle(alignleft);

				    cell = row.createCell(cellcount++);
				    cell.setCellValue(m.getSmp_prod_cd());
				    cell.setCellStyle(alignleft);

				    cell = row.createCell(cellcount++);
				    cell.setCellValue(m.getBatch_no());
				    cell.setCellStyle(alignright);

				    cell = row.createCell(cellcount++);
				    cell.setCellValue(m.getDspdtl_disp_qty());
				    cell.setCellStyle(alignright);

				    cell = row.createCell(cellcount++);
				    cell.setCellValue(String.format("%.2f", m.getDspdtl_rate()));
				    cell.setCellStyle(alignright);

				    cell = row.createCell(cellcount++);
				    cell.setCellValue(String.format("%.2f", m.getValue()));
				    cell.setCellStyle(alignright);

				    // row = sheet.createRow(++rowNum);

				}
				oldFstaffId = newFstaffId;
				oldpsoId = newpsoId;
				oldEmail1 = newEmail1;
				oldDivision = newDivision;
				oldDivCode = newDivCode;
			    }
			}

			try {
			    outputStream = new ByteArrayOutputStream();
			    workbook.write(outputStream);

			    System.out.println("Excel written successfully..4");

			    EmailFieldStaffModel m = model.get(model.size() - 1);
			    /*
			     * String subject = m.getDiv_disp_nm() +
			     * "Dispatch  Details as on " + sdf.format(dateList.get(0))
			     * + "-" + sdf.format(dateList.get(dateList.size() - 1));
			     */
			    String subject = oldDivision + "Dispatch  Details as on " + sdf.format(dateList.get(0)) + "-"
				    + sdf.format(dateList.get(dateList.size() - 1));
			    // String to = "medicoedss@gmail.com";
			    StringBuffer body = new StringBuffer();
			    
			    // body.append("Details of Dispatches
			    // ").append(stdt).append(" To ").append(enddt);
			    // List<Email> edao = new
			    // Email_Dao().getParameteDetailsByCode("DSPREPORT");

			    /*
			     * List<Email> edao = new
			     * Email_Dao().getParameteDetailsByCode("DSPREPORT");
			     * List<String> emailList = new ArrayList<String>();
			     * for(Email email : edao) {
			     * emailList.add(email.getEmail()); }
			     */

			    if (oldEmail1 != null) {
				List<String> emailList = new ArrayList<String>();
				emailList.add(oldEmail1);
				System.out.println("$#$#$#$#$$#$" + emailList);
				String cc = "Y";
				System.out.println("DDDDDD");
				System.out.println(oldDivision);
				System.out.println(oldDivCode);
				sm.sendExcelMail(emailList, subject, body.toString(), outputStream.toByteArray(),
					oldDivision + " Dispatch details", cc, oldDivCode);
			    }
			} catch (FileNotFoundException e) {
			    System.out.println("Error Occurred :" +e.getMessage());// uncomment asneeded --;
			} catch (IOException e) {
			    System.out.println("Error Occurred :" +e.getMessage());// uncomment asneeded --;
			}catch (Exception e) {
				System.out.println("Error Occurred :" +e.getMessage());// uncomment asneeded --;
			    }
		    }

		} catch (Exception ex) {
		    //ex.printStackTrace();
		    System.out.println("ERROR!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!");
		    return "Internal Error....!!!";
		} finally {
		    try {
			//rs.close();
			//con.close();
		    } catch (Exception e) {
			System.out.println("Error Occurred :" +e.getMessage());// uncomment asneeded --;
		    }

		}
		// setMsg("Emails Successfully Sent.......");
		return "Emails Successfully Sent.......";
	    }
}
