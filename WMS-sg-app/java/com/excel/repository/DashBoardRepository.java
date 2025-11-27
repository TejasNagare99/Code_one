package com.excel.repository;

import java.text.ParseException;
import java.util.List;

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

public interface DashBoardRepository {

	List<ActivityApproval> getTaskList(List<String> task_ids,String identitylink, String erp_cust_outstanding) throws ParseException;
	List<DashboardCharts> getPIDashboardGraphData(Long locId,String empId) throws Exception;
	List<DashboardCharts> getSampleDashboardGraphData(Long locId,String empId) throws Exception;
	List<Object[]> getDashboardPIDataForGRN(Long locId,Integer noOfDays,String empId) throws Exception;
	List<Object[]> getDashboardSampleDataForGRN(Long locId,Integer noOfDays,String empId) throws Exception;
	List<ActivityNotification> getNotification(String startedBy) throws Exception;
	List<FsDispatchLandingPage> getFsLandingPageData(String divId,String startPcode,String endPCode,
			String finYr,String repType,String logFsId,String prodId,
			String dmTer,String psoTer,String self) throws Exception;
	List<DashboardBean> getFsDetailsByEmpId(String empId) throws Exception;
	List<FieldStaff> getFsdetailsByMgrId(String mgrId,String levelCode) throws Exception;
	List<Period> getPeriodList(String finYear) throws Exception;
	List<DashboardBean> getFsMgrDetails(Long fsId) throws Exception;
	List<DashboardBean> getApprovalTrackerPeriods(String finyr) throws Exception;
	List<ApprovalTrackingData> getApprovalTrackingData(String finyr, String sdate, String edate, String locId,
			String empId) throws Exception;
	List<DashboardCharts2> getDashboardPlanData(Long locId, String empId, String finyr, String periodCd, String div_id) throws Exception;
	
	List<SmpItem> getproductbyempid(String log_user_fstaff_id,String fin_yr,String frm_month,String to_month,String levelcode);

	List<DashboardChartsWithSku> getDashboardPlanDataWithSku(Long locId, String empId, String finyr, String periodCd,
			String div_id, Long brandid) throws Exception;
	List<Object[]> getDashboardPIDetailDataForGRN(Long locId, Integer noOfDays, String empId, String grn_no,
			String product_code, String brand_name) throws Exception;
	List<Object[]> getDashboardSampleDetailDataForGRN(Long locId, Integer noOfDays, String empId, String grn_no,
			String product_code, String brand_name) throws Exception;
	
	List<Transporter_master> getTransporterDetails()throws Exception;
}
