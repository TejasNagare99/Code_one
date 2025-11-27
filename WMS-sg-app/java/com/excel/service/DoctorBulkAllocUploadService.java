package com.excel.service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.web.multipart.MultipartFile;

import com.excel.bean.DoctorBulkUploadBean;
import com.excel.model.AllocReqHeader;
import com.excel.model.Blk_Challans_Generated_Log;
import com.excel.model.Blk_hcp_req_doctors;
import com.excel.model.Blk_hcp_req_hdr;
import com.excel.model.Blk_hcp_req_products;
import com.excel.model.Blk_hcq_req_temp;
import com.excel.model.SmpItem;

public interface DoctorBulkAllocUploadService {

	public List<SmpItem> getBrands(Long divId, Long prodtype,String userid) throws Exception;

	public List<SmpItem> getProductsForBulkUpload(Long divId, Long prodtype, String brands) throws Exception;

	public Map<String, Object> saveBulkDoctorUpload(DoctorBulkUploadBean doctorBulkUploadBean) throws Exception;

	Blk_hcp_req_hdr saveblk_hcp_req_hdr(DoctorBulkUploadBean doctorBulkUploadBean) throws Exception;

	Map<String, Object> saveBulkDoctorproducts(DoctorBulkUploadBean doctorBulkUploadBean) throws Exception;

	public Map<String, Object> getListOfHcpsFromFileOrStructuralErrors(String divId, String masterType,
			MultipartFile file);

	public List<Object[]> getHdrAndDocListForCsvGeneration(Long reqId) throws Exception;

	public List<Object[]> getProdListForCsvGeneration(Long reqId) throws Exception;

	public String getCsvForBulkUpload(List<Object[]> hdrAndDocList, List<Object[]> prodList) throws Exception;

	public String getExcelForBulkUploadNoMaster(Blk_hcp_req_hdr blk_header, List<Object[]> prodList, Long bulkReqId,
			String empId) throws Exception;

	public Blk_hcp_req_hdr getBlkHcpReqHdrById(Long hdrId) throws Exception;

	public void saveFileToBulkTemp(MultipartFile file, String username) throws Exception;

	public String getExcelForBulkUpload(List<Object[]> hdrAndDocList, List<Object[]> prodList, Long bulkReqId,
			String empId) throws Exception;

	public List<Blk_hcp_req_hdr> getRequestListFromBlkHdr(boolean isgen) throws Exception;

	public void pushBulkRecordsToAllocTables(Long bulkReqId, String FinYear, String ipaddress, String per_code)
			throws Exception;

	public AllocReqHeader setAllocReqHdrFromBlkTempRecordsAndSave(Blk_hcq_req_temp tempRecord, String FinYear,
			String ipaddress, String periodCode) throws Exception;

	public void saveAllocReqDet(AllocReqHeader header, String compCode, Long divId, Long prodId, BigDecimal prod_qty)
			throws Exception;

	public Map<String, Object> deleteBulkDoctorUploadBeforeModify(Long blkreqHdId) throws Exception;

	public Map<String, Object> deleteBulkProductUploadBeforeModify(Long blkreqHdId) throws Exception;

	public Blk_hcp_req_hdr getObjById(Long Id) throws Exception;

	public List<Blk_hcp_req_doctors> getDoctorDetailsById(Long Id) throws Exception;

	public List<Blk_hcp_req_products> getProductsDetailsById(Long Id) throws Exception;

	public List<Blk_hcp_req_hdr> getReqNoByStatus(String string, String isconfirmed) throws Exception;

	public void selfApprovalDoctorUpload(String reqId, String userRole, String empId, HttpServletRequest request,
			HttpSession session) throws Exception;

	public void selfApprovalDoctorUpload(String reqId) throws Exception;

	public List<Blk_hcp_req_hdr> getRequestListFromBlkHdrForUploaded(Long divId) throws Exception;

	public void ConfirmDoctorBlkAlloc(Long reqId, String empId, String ipaddr) throws Exception;

	public List<Blk_hcq_req_temp> getBulkTempByBlkHdrId(Long bulkHdrId) throws Exception;

	public void finalApproval(Long blkReqId, String last_approved_by, String comp_cd, String isapproved,
			String motivation) throws Exception;

	public List<Object[]> getGeneratedRequestList(Long blkReqId) throws Exception;

	public Map<String, Object> validateExcelUploadForTemp(MultipartFile file) throws Exception;

	public String doctorBulkUploadExcelToPdf(String name) throws Exception;

	public List<Blk_Challans_Generated_Log> getBulkUploadLogDetailList(String finyr,String periodCd) throws Exception;

	public String getBulkUploadLogDetailExcel(List<Blk_Challans_Generated_Log> bulkDoctorPdfList) throws Exception;
}
