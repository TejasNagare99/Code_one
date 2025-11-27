package com.excel.repository;

import java.util.List;
import java.util.Optional;

import com.excel.model.BlkStkReqProducts;
import com.excel.model.BlkStkReqTemp;
import com.excel.model.Blk_hcp_req_products;
import com.excel.model.BulkStkReqHeader;
import com.excel.model.BulkStkReqStockists;
import com.excel.model.FieldStaff;

public interface StockistBulkRepo {

	public Optional<FieldStaff> getFieldStaffAttchedToLocation(Long loc_id,Long div_id) throws Exception;
	public BulkStkReqHeader getObjById(Long Id) throws Exception;
	public void deleteBulkStockistUploadBeforeModify(Long blkreqHdId) throws Exception;
	public void deleteBulkStockistProductUploadBeforeModify(Long blkreqHdId) throws Exception;
	public List<BulkStkReqHeader> getReqNoByStatus(String status,String iscnfrmd) throws Exception;
	public BulkStkReqHeader getBlkStkReqHdrById(Long hdrId) throws Exception;
	public List<Object[]> getProdListForCsvGeneration(Long reqId) throws Exception;
	public List<Object[]> getHdrAndStksForCsvDownload(Long blkStkReqHdrId) throws Exception;
	public List<BulkStkReqHeader> getRequestListFromBlkStkHdr(boolean isgen) throws Exception;
	public List<BlkStkReqTemp> getStkBlkTempRecordsByBlkHdrId(Long bulkReqId) throws Exception;
	public BulkStkReqHeader getStkBulkRequestHdrByReqNo(String reqNo) throws Exception;
	public List<BlkStkReqProducts> getProductsDetailsById(Long Id) throws Exception;
	public void FinalAllocationApprovalProc(Long reqId, String userid, String Ipaddr) throws Exception;
	public List<BulkStkReqStockists> getBulkStockistDetailsByHdrReqId(Long hdBlkStkId) throws Exception;
	public List<BlkStkReqProducts> getProductsDetailsByBulkHeaderId(Long Id) throws Exception;
	public String getBulkCsvNameByBulkTempId(Long blkStkTempId) throws Exception;
	public List<BulkStkReqHeader> getRequestListFromBlkHdrForUploaded(Long locId) throws Exception;
}
