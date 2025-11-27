package com.excel.service;

import java.io.File;
import java.io.FileOutputStream;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.StringJoiner;
import java.util.TreeSet;
import java.util.stream.Collectors;

import org.apache.poi.ss.usermodel.BorderStyle;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.ss.util.RegionUtil;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.excel.bean.ReportBean;
import com.excel.model.AllocDispatchTracker;
import com.excel.model.Allocation_download;
import com.excel.model.Usermaster;
import com.excel.repository.UserMasterRepository;
import com.excel.security.PasswordManager;
import com.excel.utility.CodifyErrors;
import com.excel.utility.MedicoConstants;
import com.excel.utility.ReportFormats;

import net.lingala.zip4j.core.ZipFile;
import net.lingala.zip4j.model.ZipParameters;
import net.lingala.zip4j.util.Zip4jConstants;

@Service
public class Allocation_Download_excel_ServiceImpl implements Allocation_Download_excel_service, MedicoConstants {
	@Autowired
	private UserMasterRepository usermasterrepository;
	@Autowired
	private UserMasterService usermasterservice;

	@Override
	public String Generate_Allocation_Download_excel(List<Allocation_download> list, ReportBean bean,
			String companyname, String empId) throws Exception {
		String filename = "Allocation(final) before Auto Dispatch Report" + new Date().getTime() + ".xlsx";
		String filepath = REPORT_FILE_PATH + filename;
		System.out.println("file path :::" + filepath);
		XSSFWorkbook workbook = null;
		try {
			workbook = new XSSFWorkbook();
			File f = new File(REPORT_FILE_PATH);
			if (!f.exists()) {
				if (f.mkdirs()) {
				} else {
					throw new RuntimeException("Could not create directory");
				}
			}
			XSSFSheet sheet = workbook.createSheet("Allocation(final) before Auto Dispatch Report");

			XSSFRow row = null;
			XSSFCell cell = null;
			int rowCount = 0;
			int colCount = 0;

			Set<String> set = new TreeSet<String>();
			for (Allocation_download temp : list) {
				// set.add(temp.getFs_code());
				set.add(temp.getFstaff_id());
			}

//			String headings[] = { "Header", "CFA Location", "Territory", "Sub Company", "Employee No.","Year", "Month","Division Name",
//					"Designation","Fs_Code","Staff Id","Fs_Name", "Alloc_Type"};
			List<String> productHeader = null;
			productHeader = new ArrayList<String>();
			productHeader.add("Header");
			productHeader.add("CFA Location");
			productHeader.add("Territory");
			productHeader.add("Sub Company");
			productHeader.add("Employee No.");
			productHeader.add("Year");
			productHeader.add("Month");
			productHeader.add("Division Name");
			productHeader.add("Designation");
			productHeader.add("Fs Code");
			productHeader.add("Staff Id");
			productHeader.add("Field Staff Name");
			productHeader.add("Alloc Type");
			Set<String> productset = new LinkedHashSet<String>();

			Map<String, Integer> prodmap = new LinkedHashMap<>();
			for (Allocation_download file_List : list) {
				productset.add(file_List.getSmp_prod_name());
			}

			String[] productAry = new String[productset.size()];
			int z = 0;
			int prodCount = 13;
			for (String product : productset) {
				productHeader.add(product);
				productAry[z++] = product;
				prodmap.put(product, prodCount);
				prodCount++;
			}

			XSSFCellStyle headingCellStyle = ReportFormats.heading(workbook);
			XSSFCellStyle columnHeadingStyle = ReportFormats.columnHeading(workbook);
			XSSFCellStyle cellAlignment = ReportFormats.setCellAlignment(workbook, ALIGN_RIGHT);

			row = sheet.createRow(rowCount);
			cell = row.createCell(colCount);
			sheet.addMergedRegion(new CellRangeAddress(rowCount, rowCount, colCount,
					// colCount + headings.length - 1)); cell.setCellValue(companyname);
					colCount + 12));
			cell.setCellValue(companyname);
			cell.setCellStyle(headingCellStyle);
			rowCount++;

			row = sheet.createRow(rowCount);
			cell = row.createCell(colCount);
			sheet.addMergedRegion(new CellRangeAddress(rowCount, rowCount, colCount,
					// colCount + headings.length - 1));
					colCount + 12));
			cell.setCellValue("Allocation(final) before Auto Dispatch Report");
			cell.setCellStyle(headingCellStyle);
			// rowCount++;

			/*
			 * row = sheet.createRow(rowCount); cell = row.createCell(colCount);
			 * sheet.addMergedRegion(new CellRangeAddress(rowCount, rowCount, colCount,
			 * colCount + headings.length - 1));
			 * cell.setCellValue("Period: "+MedicoResources.
			 * convertUtilDateToString_DD_MM_YYYY_(bean.getStartDate())+" to "
			 * +MedicoResources.convertUtilDateToString_DD_MM_YYYY_(bean.getEndDate()));
			 * cell.setCellStyle(headingCellStyle);
			 */

			rowCount += 1;
			row = sheet.createRow(rowCount);

			for (String heading : productHeader) {
				cell = ReportFormats.cell(row, colCount, heading, columnHeadingStyle);
				colCount++;
			}

			sheet.groupRow(0, 3);
			sheet.createFreezePane(0, 3);

			rowCount++;
			colCount = 0;

			String oldfstaff_id = "old";
			String newstaff_id = "new";
			String prod_name = "";

			Map<String, Map<String, BigDecimal>> fsProdData = new LinkedHashMap<>();
			Map<String, BigDecimal> products = null;

			list.sort(Comparator.comparing(e -> e.getFs_code()));
			for (Allocation_download data : list) {

				if (fsProdData.containsKey(data.getFs_code())) {
					products.put(data.getSmp_prod_name(), data.getQty());
					fsProdData.put(data.getFs_code(), products);
				} else {
					products = new LinkedHashMap<>();
					products.put(data.getSmp_prod_name(), data.getQty());
					fsProdData.put(data.getFs_code(), products);
				}
			}

			for (Allocation_download data : list) {
				newstaff_id = data.getFs_name();

				// if(!newstaff_id.equals(oldfstaff_id) && !oldfstaff_id.equals("old")) {

				if (!newstaff_id.equals(oldfstaff_id)) {

					// if (data.getFstaff_id().equals(data.getFs_code())) {

					row = sheet.createRow(rowCount);
					cell = ReportFormats.cell(row, colCount, data.getAllocdtl_id() == null ? "" : data.getAllocdtl_id(),
							null);
					colCount++;

					cell = ReportFormats.cell(row, colCount, data.getDptloc_name() == null ? "" : data.getDptloc_name(),
							null);
					colCount++;

					cell = ReportFormats.cell(row, colCount,
							data.getFstaff_terrname() == null ? "" : data.getFstaff_terrname(), null);
					colCount++;

					cell = ReportFormats.cell(row, colCount,
							data.getSubcomp_company() == null ? "" : data.getSubcomp_company(), null);
					colCount++;

					cell = ReportFormats.cell(row, colCount,
							data.getFstaff_employee_no() == null ? "" : data.getFstaff_employee_no(), null);
					colCount++;

					cell = ReportFormats.cell(row, colCount, data.getYear() == null ? "" : data.getYear(), null);
					colCount++;

					cell = ReportFormats.cell(row, colCount, data.getPer_code() == null ? "" : data.getPer_code(),
							null);
					colCount++;

					cell = ReportFormats.cell(row, colCount, data.getDivision() == null ? "" : data.getDivision(),
							null);
					colCount++;

					// cell = ReportFormats.cell(row, colCount, data.getValue() == null
					// ?"":data.getValue().setScale(2, RoundingMode.HALF_UP).toString(),
					// cellAlignment);
					// colCount++;

					cell = ReportFormats.cell(row, colCount,
							data.getLevel_brief_name() == null ? "" : data.getLevel_brief_name(), null);
					colCount++;

					cell = ReportFormats.cell(row, colCount, data.getFs_code() == null ? "" : data.getFs_code(), null);
					colCount++;

					cell = ReportFormats.cell(row, colCount, data.getFstaff_id() == null ? "" : data.getFstaff_id(),
							null);
					colCount++;

					cell = ReportFormats.cell(row, colCount, data.getFs_name() == null ? "" : data.getFs_name(), null);
					colCount++;

					cell = ReportFormats.cell(row, colCount, data.getAlloc_type() == null ? "" : data.getAlloc_type(),
							null);
					colCount++;

					Map<String, BigDecimal> prods = fsProdData.get(data.getFs_code());

					for (int k = 0; k < productAry.length; k++) {
						for (Map.Entry<String, BigDecimal> d : prods.entrySet()) {
							prod_name = d.getKey();
							if (prod_name.trim().equalsIgnoreCase(productAry[k].trim())) {
								// cell = ReportFormats.cell(row, prodmap.get(prod_name),
								// String.valueOf(d.getValue())==null ?" ":String.valueOf(d.getValue()),null );
								cell = row.createCell(prodmap.get(prod_name));
								cell.setCellValue(d.getValue().longValue() == 0l ? 0l : d.getValue().longValue());
								colCount++;
							}
						}
						// cell = ReportFormats.cell(row, colCount,"0",null );
					}
					// oldfstaff_id=newstaff_id;
					oldfstaff_id = newstaff_id;
					rowCount++;
					colCount = 0;
				}

			} // new for loop close
			System.out.println("Excel created...");
			FileOutputStream fileOutputStream = new FileOutputStream(filepath);
			workbook.write(fileOutputStream);

			filename = usermasterservice.generateExcellock(filepath, filename, empId, ".xlsx");

		} finally {
			if (workbook != null) {
				workbook.close();
			}
		}
		return filename;
	}

	@Override
	public String Generate_Dispatch_Allocation_Download_excel(List<AllocDispatchTracker> list, String companyname,
			String startDate, String endDate, String empId) throws Exception {

		String filename = "AllocationToDispatchTracker" + new Date().getTime() + ".xlsx";
		String filepath = REPORT_FILE_PATH + filename;
		System.out.println("file path :::" + filepath);
		XSSFWorkbook workbook = null;
		Map<String, String> allocTypeMap = new HashMap<String, String>();
		allocTypeMap.put("E", "Emergency Supply");
		allocTypeMap.put("D", "Doctor Supply");
		allocTypeMap.put("A", "Additional Allocation");
		allocTypeMap.put("M", "Monthly Allocation");
		// add for monthly and also for additional
		// allocTypeMap.put("T", "Emergency Supply");
		try {
			workbook = new XSSFWorkbook();
			File f = new File(REPORT_FILE_PATH);
			if (!f.exists()) {
				if (f.mkdirs()) {
				} else {
					throw new RuntimeException("Could not create directory");
				}
			}
			XSSFSheet sheet = workbook.createSheet("FieldStaff Wise Dispatch Allocation Tracker ");
			XSSFRow row = null;
			XSSFCell cell = null;
			int rowCount = 0;
			int colCount = 0;

			String oldFstaffName = "";
			String oldDspChallanNo = "";
			String newFstaffName = "";
			String newDspChallanNo = "";
			String prod_name_for_searching = "";
			String oldallocno = "";
			String newallocno = "";
			Long oldallocgenhdid = 0l;
			Long newallocgenhdid = 0l;
			Set<String> setOfCsvNames = new HashSet<String>();
			// for searching
			StringJoiner j = new StringJoiner("");
			Set<String> uniqueStringsForSearching = new HashSet<String>();
			int index_for_searching = 0;
			boolean init_indicator = true;
			Long alloc_gen_hd_id = 0L;
			Long approvedAllocQty = 0L;
			Long dispQty = 0L;
			String csvFname = "";
			String[] colheadings = { "Header", "CSV Filename", "Plant Name", "Territory", "Employee No.", "Year",
					"Month", "Division Name", "Designation", "Fs_Code", "Field Staff Name", "Destination", "Team Name",
					"Alloc. Type", "Allocation No.", "Upload Date", "Approval Date", "Challan No", "Challan Date",
					"Dispatch Approval Date", "LR No", "LR Date", "Courier Name", "No of Cases",
					"DTL ACTUAL DELIVERY DATE" };
			// TreeSet<String> allProdSet =
			// list.stream().map(e->e.getSmp_prod_name()).collect(Collectors.toSet());
			TreeSet<String> allProdSet = new TreeSet<String>(
					list.stream().map(e -> e.getSmp_prod_name()).collect(Collectors.toList()));
			List<String> allProdList = new ArrayList<>(allProdSet);
			AllocDispatchTracker proxyObject = new AllocDispatchTracker();
			// add headers
			XSSFCellStyle headingCellStyle = ReportFormats.heading(workbook);
			XSSFCellStyle columnHeadingStyle = ReportFormats.columnHeading(workbook);
			XSSFCellStyle cellAlignment = ReportFormats.setCellAlignment(workbook, ALIGN_RIGHT);
			XSSFCellStyle subHeadingStyles = ReportFormats.columnSubHeading(workbook);
			XSSFCellStyle subHeadingStyles2 = ReportFormats.columnSubHeading2(workbook);
			XSSFCellStyle subHeadingStyles3 = ReportFormats.columnSubHeading3(workbook);
			row = sheet.createRow(rowCount);
			cell = row.createCell(colCount);
			sheet.addMergedRegion(new CellRangeAddress(rowCount, rowCount, colCount, colCount + 12));
			cell.setCellValue(companyname);
			cell.setCellStyle(headingCellStyle);
			rowCount++;

			row = sheet.createRow(rowCount);
			cell = row.createCell(colCount);
			sheet.addMergedRegion(new CellRangeAddress(rowCount, rowCount, colCount, colCount + 12));
			cell.setCellValue("Allocation to Dispatch Tracker for period " + startDate + " to " + endDate);
			cell.setCellStyle(headingCellStyle);
			rowCount++;
			row = sheet.createRow(rowCount);
			for (String heading : colheadings) {
				cell = ReportFormats.cell(row, colCount, heading, columnHeadingStyle);
				colCount++;
			}
			for (String heading : allProdSet) {
				cell = row.createCell(colCount);
				sheet.addMergedRegion(new CellRangeAddress(rowCount, rowCount, colCount, colCount + 2));
				cell.setCellValue(heading);
				cell.setCellStyle(subHeadingStyles3);
				colCount += 3;
			}
			rowCount++;
			row = sheet.createRow(rowCount);
			colCount = colheadings.length;
			for (String heading : allProdSet) {
				cell = row.createCell(colCount);
				cell.setCellValue("Alloc Qty.");
				cell.setCellStyle(subHeadingStyles3);
				colCount++;
				cell = row.createCell(colCount);
				cell.setCellValue("Appr Qty.");
				cell.setCellStyle(subHeadingStyles3);
				colCount++;
				cell = row.createCell(colCount);
				cell.setCellValue("Disp Qty.");
				cell.setCellStyle(subHeadingStyles3);
				colCount++;
			}
			rowCount++;
			// headers have been added
			List<AllocDispatchTracker> tempListForChecking = null;
			boolean skipCellsIndicator = false;
			int i_=0;
			for (AllocDispatchTracker allocDispTrack : list) {
				i_++;
				if(i_==5) {
					System.out.println(i_);
				}
				newFstaffName = allocDispTrack.getFstaff_display_name();
				newDspChallanNo = allocDispTrack.getDsp_challan_no();
				prod_name_for_searching = allocDispTrack.getSmp_prod_name();
				newallocno = allocDispTrack.getAlloc_no();
				newallocgenhdid = allocDispTrack.getAlloc_gen_hd_id();

				// check if same row has been written and update the indicator upon which we
				// will
				// should check alloc ref no and smp prod name //if found update indicator
				// write data after upload date
				// other wise will not write
				// update indicator for each row
//				tempListForChecking = list.parallelStream()
//						.filter(obj -> obj.getAlloc_no().equals(allocDispTrack.getAlloc_no()) &&
//								obj.getSmp_prod_name().equals(allocDispTrack.getSmp_prod_name()) &&
//								!obj.getRownum().equals(allocDispTrack.getRownum()))
//						.collect(Collectors.toList());
				// get count for all the rows having same alloc ref no and same csv filname
				// in for loop go backwords and make sure that the limit is not reached
				int index = list.indexOf(allocDispTrack);
				skipCellsIndicator = false;
//				if (index > 0) {
//					for (int i = 0; i < index; i++) {
//						if (list.get(i).getAlloc_no().equals(allocDispTrack.getAlloc_no())
//								&& list.get(i).getSmp_prod_name().equals(allocDispTrack.getSmp_prod_name())
//								&& list.get(i).getDsp_challan_no().equals(allocDispTrack.getDsp_challan_no())) {
//							skipCellsIndicator = true;
//							break;
//						}
//					}
//				}

				// use some sort of unique identifier for this purpose
				// add to string[]
				String tempString = new String();
				tempString = tempString.concat(allocDispTrack.getAlloc_no());
				tempString = tempString.concat(allocDispTrack.getSmp_prod_name());
				tempString = tempString.concat(allocDispTrack.getDsp_challan_no());
				tempString = tempString.concat(allocDispTrack.getDsp_challan_dt());
				
				if (uniqueStringsForSearching.contains(tempString)) {
					skipCellsIndicator = true;
				} else {
					uniqueStringsForSearching.add(tempString);
				}

				if (!oldallocno.equals(newallocno) || !oldallocgenhdid.equals(newallocgenhdid) || !newDspChallanNo.equals(oldDspChallanNo)) {

					if (!init_indicator) {
						rowCount++;
						row = sheet.createRow(rowCount);
					}
					// generate csv names by going ahead and adding csv names until alloc no changes
					setOfCsvNames = new TreeSet<String>(list.parallelStream()
							.filter(obj -> obj.getAlloc_no().equals(allocDispTrack.getAlloc_no()))
							.map(x_obj -> x_obj == null ? "" : x_obj.getCsv_file_name()).collect(Collectors.toList()));

					///////////
					colCount = 0;
					row = sheet.createRow(rowCount);

					cell = row.createCell(colCount);
					alloc_gen_hd_id = allocDispTrack.getAlloc_gen_hd_id();
					if (alloc_gen_hd_id == null) {
						cell.setCellValue("");
					} else {
						cell.setCellValue(allocDispTrack.getAlloc_gen_hd_id());
					}
					colCount++;

					cell = row.createCell(colCount);
					cell.setCellValue(String.join(",", setOfCsvNames));
					colCount++;

					cell = row.createCell(colCount);
					cell.setCellValue(allocDispTrack.getLoc_nm());
					colCount++;

					cell = row.createCell(colCount);
					cell.setCellValue(allocDispTrack.getTerritory());
					colCount++;

					cell = row.createCell(colCount);
					cell.setCellValue(allocDispTrack.getFstaff_employee_no());
					colCount++;

					cell = row.createCell(colCount);
					cell.setCellValue(allocDispTrack.getUploaded_date() == null ? " "
							: allocDispTrack.getUploaded_date().substring(6, 10));
					colCount++;

					cell = row.createCell(colCount);
					cell.setCellValue(allocDispTrack.getMonth().substring(0, 3));
					colCount++;

					cell = row.createCell(colCount);
					cell.setCellValue(allocDispTrack.getDivision());
					colCount++;

					cell = row.createCell(colCount);
					cell.setCellValue(allocDispTrack.getDesignation());
					colCount++;

					cell = row.createCell(colCount);
					cell.setCellValue(allocDispTrack.getFstaff_code());
					colCount++;

					cell = row.createCell(colCount);
					cell.setCellValue(allocDispTrack.getFstaff_display_name());
					colCount++;

					cell = row.createCell(colCount);
					cell.setCellValue(allocDispTrack.getFstaff_destination());
					colCount++;
					cell = row.createCell(colCount);

					cell.setCellValue(allocDispTrack.getTeam());

					colCount++;
					///////////
					// colCount = 13;
					cell = row.createCell(colCount);
					cell.setCellValue(allocTypeMap.get(allocDispTrack.getAlloc_type()));

					colCount++;
					cell = row.createCell(colCount);
					cell.setCellValue(allocDispTrack.getAlloc_no());

					colCount++;
					cell = row.createCell(colCount);
					cell.setCellValue(allocDispTrack.getUploaded_date());

					// skip cells from here
					if (!skipCellsIndicator) {
						colCount++;
						cell = row.createCell(colCount);
						cell.setCellValue(allocDispTrack.getApproval_date());

						colCount++;
						cell = row.createCell(colCount);
						cell.setCellValue(allocDispTrack.getDsp_challan_no());

						colCount++;
						cell = row.createCell(colCount);
						cell.setCellValue(allocDispTrack.getDsp_challan_dt());

						colCount++;
						cell = row.createCell(colCount);
						cell.setCellValue(allocDispTrack.getDsp_dt());

						colCount++;
						cell = row.createCell(colCount);
						cell.setCellValue(allocDispTrack.getDsp_lr_no());

						colCount++;
						cell = row.createCell(colCount);
						cell.setCellValue(allocDispTrack.getDsp_lr_dt());

						colCount++;
						cell = row.createCell(colCount);
						cell.setCellValue(allocDispTrack.getCourier_name());

						colCount++;
						cell = row.createCell(colCount);
						cell.setCellValue(allocDispTrack.getDsp_cases());

						colCount++;
						cell = row.createCell(colCount);
						cell.setCellValue(allocDispTrack.getActual_delivery_date());

						colCount++;

						// add product details
						// logic to find colno
						colCount = 25;
						index_for_searching = allProdList.indexOf(prod_name_for_searching);
						colCount += index_for_searching * 3;
						cell = row.createCell(colCount);
						cell.setCellValue(allocDispTrack.getAlloc_qty());
						cell.setCellStyle(cellAlignment);
						colCount++;
						cell = row.createCell(colCount);
						cell.setCellValue(
								Long.valueOf(allocDispTrack.getApproved_alloc_qty().toBigInteger().toString()));
						cell.setCellStyle(cellAlignment);
						colCount++;
						cell = row.createCell(colCount);
						cell.setCellValue(Long.valueOf(allocDispTrack.getDisp_qty().toBigInteger().toString()));
						cell.setCellStyle(cellAlignment);
						colCount++;
					}
				} else if (!skipCellsIndicator) {

					// add product details
					colCount = 25;
					// logic to find colno
					index_for_searching = allProdList.indexOf(prod_name_for_searching);
					colCount += index_for_searching * 3;
					cell = row.createCell(colCount);
					cell.setCellValue(allocDispTrack.getAlloc_qty());
					cell.setCellStyle(cellAlignment);
					colCount++;
					cell = row.createCell(colCount);
					approvedAllocQty = Long.valueOf(allocDispTrack.getApproved_alloc_qty().toBigInteger().toString());
					cell.setCellValue(approvedAllocQty);
					cell.setCellStyle(cellAlignment);
					colCount++;
					cell = row.createCell(colCount);
					dispQty = Long.valueOf(allocDispTrack.getDisp_qty().toBigInteger().toString());
					cell.setCellValue(dispQty);
					cell.setCellStyle(cellAlignment);
					colCount++;

				}
				init_indicator = false;
				oldDspChallanNo = newDspChallanNo;
				oldFstaffName = newFstaffName;
				oldallocno = newallocno;
				oldallocgenhdid = newallocgenhdid;
				// }
			}
//			//add headings
//			//use sets to add data 
//			Set<String> FsNameSet = new HashSet<String>();
//			Set<String> DspChallanSet = new HashSet<String>();
//			Set<String> ProdNameSet = new HashSet<String>();
//			//populate fsname set
//			FsNameSet = list.stream().
//					map(e -> e.getFstaff_display_name()).
//					collect(Collectors.toSet());
//			//for each fsname search and make a challan set 
//			for(String fsname:FsNameSet) {
//				proxyObject = list.stream().filter(e -> e.getFstaff_display_name().equals(fsname)).findAny().orElse(null);
//				DspChallanSet = list.stream().
//						filter(e->e.getFstaff_display_name().equals(fsname)).
//						map(e->e.getDsp_challan_no()).
//						collect(Collectors.toSet());
//				//for each challan no search and add the rows according to challan no and the fsName
//				for(String dspChlnNo:DspChallanSet) {
//					//search according to the selected fsname and the challan no and then make the products set
//					//get the object to print challan details 
//					proxyObject = list.stream().
//							filter(e -> e.getFstaff_display_name().equals(fsname) && e.getDsp_challan_no().equals(dspChlnNo)).
//							findAny().
//							orElse(null);
//					//print challan details 
//					//we need to print prod headers in the same row so dont increment rowCount here
//					ProdNameSet = list.stream()
//				.filter(e->e.getDsp_challan_no().equals(dspChlnNo) && e.getFstaff_display_name().equals(fsname))
//				.map(e->e.getSmp_prod_name())
//				.collect(Collectors.toSet());
//					for(String prodName:ProdNameSet) {
//					//for each prodname get index of the particular product from main prod set and then 
//						//calculate according to index in which columns to write to write 
//						
//					}
//					
//				//rowcount will increment for new challan of same fstaff or for new fstaff and challan
//				}
//			}
			// set borders for merged regions
			List<CellRangeAddress> mergedRegions = sheet.getMergedRegions();
			for (int i = 2; i < mergedRegions.size(); i++) {
				RegionUtil.setBorderTop(BorderStyle.THIN, mergedRegions.get(i), sheet);
				RegionUtil.setBorderLeft(BorderStyle.THIN, mergedRegions.get(i), sheet);
				RegionUtil.setBorderRight(BorderStyle.THIN, mergedRegions.get(i), sheet);
				RegionUtil.setBorderBottom(BorderStyle.THIN, mergedRegions.get(i), sheet);
			}
			sheet.createFreezePane(0, 4);
			System.out.println("Excel created...");
			FileOutputStream fileOutputStream = new FileOutputStream(filepath);
			workbook.write(fileOutputStream);

			filename = usermasterservice.generateExcellock(filepath, filename, empId, ".xlsx");

			fileOutputStream.close();

		} catch (Exception e) {
			//System.out.println("Error Occurred :" +e.getMessage());// uncomment asneeded --;
			throw e;
		} finally {
			if (workbook != null) {
				workbook.close();
			}
		}
		return filename;
	}

}
