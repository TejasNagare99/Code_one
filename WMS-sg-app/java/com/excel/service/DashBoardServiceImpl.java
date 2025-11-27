package com.excel.service;

import java.io.File;
import java.io.FileOutputStream;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import com.excel.bean.DashboardBean;
import com.excel.model.ActivityApproval;
import com.excel.model.ActivityNotification;
import com.excel.model.ApprovalTrackingData;
import com.excel.model.DashboardCharts;
import com.excel.model.DashboardCharts2;
import com.excel.model.DashboardChartsWithSku;
import com.excel.model.FieldStaff;
import com.excel.model.FsDispatchLandingPage;
import com.excel.model.Period;
import com.excel.model.SmpItem;
import com.excel.model.Transporter_master;
import com.excel.model.trd_scheme_mst_hdr;
import com.excel.repository.Article_Scheme_master_Repository;
import com.excel.repository.DashBoardRepository;
import com.excel.utility.ApachePoiExcelFormat;
import com.excel.utility.CodifyErrors;
import com.excel.utility.MedicoConstants;
import com.excel.utility.Utility;

@Service
public class DashBoardServiceImpl implements DashBoardService ,MedicoConstants{
	
	@Autowired
	DashBoardRepository dashBoard;
	@Autowired
	private Article_Scheme_master_Repository article_Scheme_master_Repository;

	@Override
	public List<ActivityApproval> getTaskList(List<String> task_ids, String identitylink, String erp_cust_outstanding)
			throws Exception {
		
	List<ActivityApproval> list=dashBoard.getTaskList(task_ids, identitylink, erp_cust_outstanding);
		
		for(ActivityApproval  a :list) {
			
			if(a.getFull_tran_name().equals("SCM")) {
		 List<trd_scheme_mst_hdr>	schemeHeader=article_Scheme_master_Repository.get_Header_Scheme_By_Scheme_code(a.getDocument_no());
		 a.setDocument_no(schemeHeader.get(0).getTrd_scheme_name().trim());
			}
			
		}
		
		return list;
 }

	@Override
//	@Cacheable(value="PiDboard",key="#locId",unless="#result == null")
	public List<DashboardCharts> getPIDashboardGraphData(Long locId,String empId) throws Exception {
		return dashBoard.getPIDashboardGraphData(locId,empId);
	}

	@Override
//	@Cacheable(value="SampleDboard",key="#locId",unless="#result == null")
	public List<DashboardCharts> getSampleDashboardGraphData(Long locId,String empId) throws Exception {
		return dashBoard.getSampleDashboardGraphData(locId,empId);
	}

	@Override
//	@Cacheable(value="DboardPiGrn",key="#locId",unless="#result == null")
	public List<Object[]> getDashboardPIDataForGRN(Long locId,Integer noOfDays,String empId) throws Exception {
		return dashBoard.getDashboardPIDataForGRN(locId,noOfDays,empId);
	}

	@Override
//	@Cacheable(value="DboardSampleGrn",key="#locId",unless="#result == null")
	public List<Object[]> getDashboardSampleDataForGRN(Long locId,Integer noOfDays,String empId) throws Exception {
		return dashBoard.getDashboardSampleDataForGRN(locId,noOfDays,empId);
	}
	
	@Override
	public List<ActivityNotification> getNotification(String startedBy) throws Exception{
		return dashBoard.getNotification(startedBy);
	}
	
	@Override
	@Cacheable(value="DboardPlan",key="#empId",unless="#result == null")
	public List<DashboardCharts2> getDashboardPlanData(Long locId,String empId,String finyr,String periodCd,String divId) throws Exception {
		return dashBoard.getDashboardPlanData(locId,empId,finyr,periodCd,divId);
	}

	@Override
	public List<FsDispatchLandingPage> getFsLandingPageData(String divId, String startPcode, String endPCode,
			String finYr, String repType, String logFsId, String prodId, String dmTer, String psoTer, String self)
			throws Exception {
		return dashBoard.getFsLandingPageData(divId, startPcode, endPCode, finYr, repType, logFsId, prodId, dmTer, psoTer, self);
	}

	@Override
	public List<DashboardBean> getFsDetailsByEmpId(String empId) throws Exception {
		return dashBoard.getFsDetailsByEmpId(empId);
	}
	
	
	@Override
	public String Generate_Fsdashboard_Report(List<FsDispatchLandingPage> fsData) throws Exception {
		// TODO Auto-generated method stub
		Workbook wwbook =null;
		String filename = null;
		StringBuffer path;
		File ff=null;
		String filePath=null;
		try {
			
			long l = new Date().getTime();
			filename = "Dispatch_&_Delivery_Details" + l + ".xlsx";
			filePath =  MedicoConstants.REPORT_FILE_PATH;
				
			 path = new StringBuffer(filePath).append("\\");
			 path.append(filename);

				System.out.println("Path::"+path+" filepath::"+filePath);
				ff=new File(path.toString());
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
				
			//ff = new File(filePath);
			wwbook = new XSSFWorkbook();
			Sheet wsheet = wwbook.createSheet("Dispatch & Delivery Details");
			
			wsheet.createFreezePane(0, 2);
			
			
			int col = 0, row = 0;
			String[] heading = {"Month Of Use","RM Name","DM Name","Employee Name","Territory Code","Employee Number","Challan No.","Challan Date.","Courier Name","L.R. No. "
					,"L.R. Date","Cases","Tentative Delivery Date","Courier Comments","Recieved by","Actual Delivery Date","Product Type","Product Code","Batch","Expiry Date","Dispatch Quantity",
					"Rate","Value","Return Qty","Product Name"};
			
			
			
			ApachePoiExcelFormat xlsxExcelFormat = new ApachePoiExcelFormat();
			CellStyle sheetHeading = xlsxExcelFormat.SheetHeading(wwbook);
			CellStyle columnHeading = xlsxExcelFormat.columnHeading(wwbook);
			
			CellStyle fontstyle = wwbook.createCellStyle();
			Font font = wwbook.createFont();
			font.setFontHeightInPoints((short) 8);
			fontstyle.setFont(font);
			
			CellStyle cs = wwbook.createCellStyle();
			wsheet.setColumnWidth(21, 7500);
			
			
			CellStyle decimal = wwbook.createCellStyle();
			decimal.setAlignment(HorizontalAlignment.RIGHT);
			decimal.setDataFormat(wwbook.createDataFormat().getFormat("0.00"));
			
			XSSFRow hrow = (XSSFRow) (XSSFRow) wsheet.createRow(row);
			XSSFCell cell =null;
			
			cell = hrow.createCell(col);
			cell.setCellValue("Dispatch & Delivery Details Report");
			cell.setCellStyle(sheetHeading);
			wsheet.addMergedRegion(new CellRangeAddress(row, row, col, heading.length-1));		
			row++;
			
			hrow = (XSSFRow) (XSSFRow) wsheet.createRow(row);
			cell = hrow.createCell(col);
			cell.setCellValue("Report Dated : "+ Utility.convertDatetoString(new Date()));
			cell.setCellStyle(sheetHeading);
			wsheet.addMergedRegion(new CellRangeAddress(row, row, col, heading.length-1));		
			row++;			
			
			
			hrow = (XSSFRow) (XSSFRow) wsheet.createRow(row);
			for(String a : heading) {
				cell = hrow.createCell(col);
				cell.setCellValue(a);
				cell.setCellStyle(columnHeading);
				wsheet.autoSizeColumn(col);
				col++;		
			}
			
			
			
			for(FsDispatchLandingPage lst:fsData) {
				col = 0;
				row++;
				hrow = (XSSFRow) (XSSFRow) wsheet.createRow(row);
				
				cell = hrow.createCell(col);
				cell.setCellValue(lst.getMonth_use());
				cell.setCellStyle(fontstyle);
				col++;	
				
				cell = hrow.createCell(col);
				cell.setCellValue(lst.getRm_terr());
				cell.setCellStyle(fontstyle);
				col++;	
				
				cell = hrow.createCell(col);
				cell.setCellValue(lst.getDm_terr());
				cell.setCellStyle(fontstyle);
				col++;	
				
				cell = hrow.createCell(col);
				cell.setCellValue(lst.getPso_terr());
				cell.setCellStyle(fontstyle);
				col++;	
				
				cell = hrow.createCell(col);
				cell.setCellValue(lst.getFstaff_terr_code());		//Territory Code
				cell.setCellStyle(fontstyle);
				col++;	
				
				cell = hrow.createCell(col);
				cell.setCellValue(lst.getFstaff_employee_no());		//Employee Number
				cell.setCellStyle(fontstyle);
				col++;	
				
				cell = hrow.createCell(col);
				cell.setCellValue(lst.getDsp_challan_no());
				cell.setCellStyle(fontstyle);
				col++;	
				
				cell = hrow.createCell(col);
				cell.setCellValue(lst.getDsp_challan_dt()==null?"":lst.getDsp_challan_dt());
				cell.setCellStyle(fontstyle);
				col++;	
				
				cell = hrow.createCell(col);
				cell.setCellValue(lst.getCourier());
				cell.setCellStyle(fontstyle);
				col++;	
				
				cell = hrow.createCell(col);
				cell.setCellValue(lst.getDsp_lr_no());
				cell.setCellStyle(fontstyle);
				col++;	
				
				cell = hrow.createCell(col);
				cell.setCellValue(lst.getDsp_lr_dt()==null?"":lst.getDsp_lr_dt());
				cell.setCellStyle(fontstyle);
				col++;	
				
				cell = hrow.createCell(col);
				cell.setCellValue(lst.getDsp_cases());
				cell.setCellStyle(fontstyle);
				col++;	
				
				cell = hrow.createCell(col);
				cell.setCellValue(lst.getDsp_tentative_delivery_date()==null?"":lst.getDsp_tentative_delivery_date()); 		//Delivery Date
				cell.setCellStyle(fontstyle);
				col++;	
				
				cell = hrow.createCell(col);
				cell.setCellValue(lst.getDsp_remark()==null?"":lst.getDsp_remark());
				cell.setCellStyle(fontstyle);
				col++;	
				
				cell = hrow.createCell(col);
				cell.setCellValue(lst.getDsp_recd_by()==null?"":lst.getDsp_recd_by());
				cell.setCellStyle(cs);
				col++;
				
				cell = hrow.createCell(col);
				cell.setCellValue(lst.getActual_delivery_date()==null?"":lst.getActual_delivery_date());
				cell.setCellStyle(cs);
				col++;
							
				cell = hrow.createCell(col);
				cell.setCellValue(lst.getSmp_prod_type());
				cell.setCellStyle(fontstyle);
				col++;	
				
				cell = hrow.createCell(col);
				cell.setCellValue(lst.getSmp_prod_cd());
				cell.setCellStyle(fontstyle);
				col++;	
				
				cell = hrow.createCell(col);
				cell.setCellValue(lst.getBatch_no());
				cell.setCellStyle(fontstyle);
				col++;
				
				cell = hrow.createCell(col);
				cell.setCellValue(lst.getBatch_expiry_dt());
				cell.setCellStyle(fontstyle);
				col++;
				
				cell = hrow.createCell(col);
				cell.setCellValue(lst.getDspdtl_disp_qty().longValue());
				cell.setCellStyle(decimal);
				col++;
				
				cell = hrow.createCell(col);
				cell.setCellValue(lst.getDspdtl_rate().longValue());
				cell.setCellStyle(decimal);
				col++;
				
				cell = hrow.createCell(col);
				cell.setCellValue(lst.getDisp_value().longValue());
				cell.setCellStyle(decimal);
				col++;
				
				cell = hrow.createCell(col);
				cell.setCellValue(lst.getDsp_ret_qty().longValue());
				cell.setCellStyle(fontstyle);
				col++;
				
				cell = hrow.createCell(col);
				cell.setCellValue(lst.getSmp_prod_name());
				cell.setCellStyle(cs);
				col++;
			}
			
			FileOutputStream fileOut = new FileOutputStream(path.toString());
			wwbook.write(fileOut);
			fileOut.close();

			System.out.println("Excel Created");
			
			
		}
		catch (Exception e) {
			// TODO: handle exception
			System.out.println("Error Occurred :" +e.getMessage());// uncomment asneeded --;
		}
		
		return filename;
	}

	@Override
	public List<FieldStaff> getFsdetailsByMgrId(String mgrId, String levelCode) throws Exception {
		return dashBoard.getFsdetailsByMgrId(mgrId, levelCode);
	}

	@Override
	public List<Period> getPeriodList(String finYear) throws Exception {
		return dashBoard.getPeriodList(finYear);
	}

	@Override
	public List<DashboardBean> getFsMgrDetails(Long fsId) throws Exception {
		return dashBoard.getFsMgrDetails(fsId);
	}

	@Override
	@Cacheable(value="apprTrackerPeriods",key="#finyr")
	public List<DashboardBean> getApprovalTrackerPeriods(String finyr) throws Exception {
		return dashBoard.getApprovalTrackerPeriods(finyr);
	}

	@Override
	public List<ApprovalTrackingData> getApprovalTrackingData(String finyr, String sdate, String edate, String locId,String empId)
			throws Exception {
		return dashBoard.getApprovalTrackingData(finyr, sdate, edate, locId,empId);
	}

	@Override
	public String genearetApprTrackinExcel(List<ApprovalTrackingData> data) throws Exception {
		// TODO Auto-generated method stub
		Workbook wwbook =null;
		String filename = null;
		StringBuffer path;
		File ff=null;
		String filePath=null;
		try {
			
			long l = new Date().getTime();
			filename = "ApprovalTracking" + l + ".xlsx";
			filePath =  MedicoConstants.REPORT_FILE_PATH;
				
			 path = new StringBuffer(filePath).append("\\");
			 path.append(filename);

				ff=new File(path.toString());
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
				
			//ff = new File(filePath);
			wwbook = new XSSFWorkbook();
			Sheet wsheet = wwbook.createSheet("Approval Tracking");
			
			wsheet.createFreezePane(0, 3);
			
			
			int col = 0, row = 0;
			String[] heading = {"Transaction Type","Doc Number","Month of use","Division","Creator","Submission","Approval Level","Role","User Name","Approved?","Date of Approval"};
			
			ApachePoiExcelFormat xlsxExcelFormat = new ApachePoiExcelFormat();
			CellStyle sheetHeading = xlsxExcelFormat.SheetHeading(wwbook);
			CellStyle columnHeading = xlsxExcelFormat.columnHeading(wwbook);
			
			
			CellStyle cs = wwbook.createCellStyle();
			wsheet.setColumnWidth(21, 7500);
			
			
			CellStyle decimal = wwbook.createCellStyle();
			decimal.setAlignment(HorizontalAlignment.RIGHT);
			decimal.setDataFormat(wwbook.createDataFormat().getFormat("0.00"));
			
			XSSFRow hrow = (XSSFRow) (XSSFRow) wsheet.createRow(row);
			XSSFCell cell =null;
			
			cell = hrow.createCell(col);
			cell.setCellValue("Approval Tracking");
			cell.setCellStyle(sheetHeading);
			wsheet.addMergedRegion(new CellRangeAddress(row, row, col, heading.length-1));		
			row++;
			
			hrow = (XSSFRow) (XSSFRow) wsheet.createRow(row);
			cell = hrow.createCell(col);
			cell.setCellValue("Report Dated : "+ Utility.convertDatetoString(new Date()));
			cell.setCellStyle(sheetHeading);
			wsheet.addMergedRegion(new CellRangeAddress(row, row, col, heading.length-1));		
			row++;			
			
			
			hrow = (XSSFRow) (XSSFRow) wsheet.createRow(row);
			for(String a : heading) {
				cell = hrow.createCell(col);
				cell.setCellValue(a);
				cell.setCellStyle(columnHeading);
				wsheet.autoSizeColumn(col);
				col++;		
			}
			
			
			
			for(ApprovalTrackingData d:data) {
				col = 0;
				row++;
				hrow = (XSSFRow) (XSSFRow) wsheet.createRow(row);
				
				cell = hrow.createCell(col);
				cell.setCellValue(d.getTransaction_type());
				col++;	
				
				cell = hrow.createCell(col);
				cell.setCellValue(d.getDoc_number());
				col++;
				
				cell = hrow.createCell(col);
				cell.setCellValue(d.getMonth_of_use());
				col++;
				
				cell = hrow.createCell(col);
				cell.setCellValue(d.getDivision());
				col++;
				
				cell = hrow.createCell(col);
				cell.setCellValue(d.getCreator());
				col++;
				
				cell = hrow.createCell(col);
				cell.setCellValue(d.getSubmission());
				col++;
				
				cell = hrow.createCell(col);
				cell.setCellValue(d.getApproval_level());
				col++;	
				
				cell = hrow.createCell(col);
				cell.setCellValue(d.getRole());
				col++;
				
				cell = hrow.createCell(col);
				cell.setCellValue(d.getUser_name());
				col++;
				
				cell = hrow.createCell(col);
				cell.setCellValue(d.getApproved());
				col++;
				
				cell = hrow.createCell(col);
				cell.setCellValue(d.getDate_of_approval());
				col++;
				
			}
			
			FileOutputStream fileOut = new FileOutputStream(path.toString());
			wwbook.write(fileOut);
			fileOut.close();
			System.out.println("Excel Created");
		}
		catch (Exception e) {
			System.out.println("Error Occurred :" +e.getMessage());// uncomment asneeded --;
		}
		return filename;
	}
	


	@Override
	public List<SmpItem> getproductbyempid(String log_user_fstaff_id, String fin_yr, String frm_month, String to_month,
			String levelcode) {
		// TODO Auto-generated method stub
		return dashBoard.getproductbyempid(log_user_fstaff_id, fin_yr, frm_month, to_month, levelcode);
	}

	@Override
	public List<DashboardChartsWithSku> getDashboardPlanDataWithSku(Long locId, String empId, String finyr, String periodCd,
			String div_id, Long brandid) throws Exception {
		// TODO Auto-generated method stub
		return dashBoard.getDashboardPlanDataWithSku(locId, empId, finyr, periodCd, div_id, brandid);
	}

	@Override
	public List<Object[]> getDashboardPIDetailDataForGRN(Long locId, Integer noOfDays, String empId, String grn_no,
			String product_code, String brand_name) throws Exception {
		// TODO Auto-generated method stub
		return dashBoard.getDashboardPIDetailDataForGRN(locId, noOfDays, empId, grn_no, product_code, brand_name);
	}

	@Override
	public List<Object[]> getDashboardSampleDetailDataForGRN(Long locId, Integer noOfDays, String empId, String grn_no,
			String product_code, String brand_name) throws Exception {
		// TODO Auto-generated method stub
		return dashBoard.getDashboardSampleDetailDataForGRN(locId, noOfDays, empId, grn_no, product_code, brand_name);
	}

	@Override
	public List<Transporter_master> getTransporterDetails() throws Exception {
		// TODO Auto-generated method stub
		List<Transporter_master> tmlist = null;
		List<Transporter_master> proc_list = null;
		Transporter_master tempObj = null;
		try {
			tmlist =  dashBoard.getTransporterDetails();
			
			if(tmlist != null && tmlist.size()>0) {
				proc_list = new ArrayList<>();
				for(int i = 0 ;i < tmlist.size();i++) {
					tempObj = new Transporter_master();
					//Person1
					
					tempObj.setTransporter_name(tmlist.get(i).getTransporter_name());
					tempObj.setTrans_pers_resp1(tmlist.get(i).getTrans_pers_resp1());
					tempObj.setTrans_email1(tmlist.get(i).getTrans_email1());
					tempObj.setTrans_contact_no1(tmlist.get(i).getTrans_contact_no1());
					tempObj.setTrans_website_name(tmlist.get(i).getTrans_website_name());
					proc_list.add(tempObj);
					
					//Person2
					if(tmlist.get(i).getTrans_pers_resp2() != null ) {
						tempObj = new Transporter_master();
						tempObj.setTransporter_name("");
						tempObj.setTrans_pers_resp1(tmlist.get(i).getTrans_pers_resp2());
						tempObj.setTrans_email1(tmlist.get(i).getTrans_email2());
						tempObj.setTrans_contact_no1(tmlist.get(i).getTrans_contact_no2());
						tempObj.setTrans_website_name("");
						proc_list.add(tempObj);
					}
				}
			}
		}
		catch (Exception e) {
			// TODO: handle exception
			throw e ;
		}
		return proc_list;
	}
}
