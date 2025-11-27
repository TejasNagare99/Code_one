package com.excel.service;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.web.multipart.MultipartFile;

import com.excel.bean.StkBlkCsvUpldBean;
import com.excel.bean.StockistBulkUploadBean;
import com.excel.model.AllocReqHeader;
import com.excel.model.BlkStkReqProducts;
import com.excel.model.BlkStkReqTemp;
import com.excel.model.Blk_hcp_req_products;
import com.excel.model.BulkStkReqHeader;
import com.excel.model.BulkStkReqStockists;
import com.excel.model.FinYear;

public interface SockistBulkAllocUploadService {
	public List<BulkStkReqHeader> getReqNoByStatus(String status, String iscnfrmd) throws Exception;

	public Map<String, Object> saveBulkStockistUpload(StockistBulkUploadBean stockistBulkUploadBean) throws Exception;

	public BulkStkReqHeader saveblk_stk_req_hdr(StockistBulkUploadBean stockistBulkUploadBean, FinYear fin)
			throws Exception;

	public Map<String, Object> saveBulkStockistproducts(StockistBulkUploadBean stockistBulkUploadBean) throws Exception;

	public Map<String, Object> modifyBulkUploadStockists(StockistBulkUploadBean stockistBulkUploadBean)
			throws Exception;

	public Map<String, Object> modifyBulkStkUploadProducts(StockistBulkUploadBean stockistBulkUploadBean)
			throws Exception;

	public void ConfirmStockistBlkAlloc(Long reqId, String empId, String ipaddr) throws Exception;

	public List<Object[]> getHdrAndStksForCsvDownload(Long blkStkReqHdrId) throws Exception;

	public String getExcelForBulkUpload(List<Object[]> hdrAndStkList, List<Object[]> prodList, String empId,
			BulkStkReqHeader blk_stk_headerr) throws Exception;

	public List<BulkStkReqHeader> getRequestListFromBlkStkHdr(boolean isgen) throws Exception;

	public Map<String, Object> validateExcelUploadForTemp(MultipartFile file, Long locId) throws Exception;

	public void saveDataToBulkStkReqTemp(String ip_addr, Long requestorId, BulkStkReqHeader blk_stk_header,
			List<StkBlkCsvUpldBean> data_for_temp_table, String filename, String empId) throws Exception;

	public AllocReqHeader setAllocReqHdrFromStkBlkTempRecordsAndSave(BlkStkReqTemp blkStkTempRecord, String finYear,
			String ipaddress, String periodCode, Long divId) throws Exception;

	public void pushStkBulkRecordsToAllocTables(Long bulkReqId, String finYear, String ipaddress, String periodCode,
			Long divId) throws Exception;

	public void selfApprovalStockistUpload(String reqId, String userRole, String empId, HttpServletRequest request,
			HttpSession session) throws Exception;

	public void finalApproval(Long blkReqId, String last_approved_by, String comp_cd, String isapproved,
			String motivation) throws Exception;
	
	public BulkStkReqHeader getObjById(Long reqId) throws Exception;
	
	public List<BulkStkReqStockists> getBulkStockistDetailsByHdrReqId(Long hdBlkStkId) throws Exception;
	
	public List<BlkStkReqProducts> getProductsDetailsByBulkHeaderId(Long Id) throws Exception;
	
	public String getBulkCsvNameByBulkTempId(Long blkStkTempId) throws Exception;
	
	public String stockistBulkUploadExcelToPdf(String name) throws Exception;
	

}
