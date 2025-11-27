package com.excel.repository;

import java.util.List;
import java.util.Map;

import com.excel.bean.BulkUpldBean;
import com.excel.bean.SpecialAllocationBean;
import com.excel.model.Blk_Challans_Generated_Log;
import com.excel.model.Blk_hcp_req_doctors;
import com.excel.model.Blk_hcp_req_hdr;
import com.excel.model.Blk_hcp_req_products;
import com.excel.model.Blk_hcq_req_temp;
import com.excel.model.SmpItem;

public interface DoctorBulkAllocUploadRepository {
	public List<SmpItem> getBrands(Long divId,Long prodtype,String userid)throws Exception; 
	public List<SmpItem> getProductsForBulkUpload(Long divId,Long prodtype,String brands)throws Exception;
	public List<Object[]> getHdrAndDocListForCsvGeneration(Long reqId) throws Exception;
	public List<Object[]> getProdListForCsvGeneration(Long reqId) throws Exception;
	public Blk_hcp_req_hdr getBlkHcpReqHdrById(Long hdrId) throws Exception;
	public List<Blk_hcp_req_hdr> getRequestListFromBlkHdr(boolean isgen)throws Exception;
	public List<Blk_hcq_req_temp> getBlkTempRecordsByBlkHdrId(Long bulkReqId)throws Exception;
	public SpecialAllocationBean getProductDetails(Long divId,Long prodId,String companyCode) throws Exception;
	
	public Map<String, Object> deleteBulkDoctorUploadBeforeModify(Long blkreqHdId) throws Exception;
	public Map<String, Object> deleteBulkProductUploadBeforeModify(Long blkreqHdId) throws Exception;
	public Blk_hcp_req_hdr getObjById(Long Id) throws Exception;
	public List<Object[]> checkFstaffByEmplNoAndDivCheck(String fs_id,String divId,String upldType)throws Exception;
	
	public List<Blk_hcp_req_doctors> getDoctorDetailsById(Long Id)throws Exception;
	public List<Blk_hcp_req_products> getProductsDetailsById(Long Id)throws Exception;
	public List<Blk_hcp_req_hdr> getReqNoByStatus(String status,String iscnfrmd) throws Exception;
	public List<Object[]> checkUniqueHcpid(String hcp_unique_id)throws Exception;
	public List<Object[]> checkFstaffByEmplNo(String fs_id)throws Exception;
	public List<BulkUpldBean> checklinkHcpandFstaff(String hcp_unique_id, String fs_id,String divId)throws Exception;
	public void FinalAllocationApprovalProc(Long reqNo,String userid,String Ipaddr)throws Exception;
	public List<Blk_hcp_req_hdr> getRequestListFromBlkHdrForUploaded(Long divId) throws Exception;
	public List<Blk_hcq_req_temp> getBulkTempByBlkHdrId(Long bulkHdrId) throws Exception;
	public Blk_hcp_req_hdr getBulkRequestHdrByReqNo(String reqNo)throws Exception;
	public List<Object[]> getGeneratedRequestList(Long blkReqId) throws Exception;
	
	public List<Object[]> checkFstaffByEmployeeNoAndTerritoryCode(String fs_empl_no,String terr_code,String upldType)throws Exception;
	
	public List<Blk_Challans_Generated_Log> getBulkUploadLogDetailList(String finyr,String periodCd) throws Exception;
}
