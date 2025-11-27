//package com.excel.service;
//
//public interface HqMasterAuditTrail_Report_Service {
//
//}
package com.excel.service;

import java.util.List;

import com.excel.model.HqMasterAuditTrailModel;

public interface HqMasterAuditTrail_Report_Service {

	String Generate_HqMasterAuditTrail_Report(List<HqMasterAuditTrailModel> list,String username,String companyname,String empId)throws Exception;
}