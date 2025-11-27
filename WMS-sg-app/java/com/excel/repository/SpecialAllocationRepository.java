package com.excel.repository;

import java.util.List;
import java.util.Map;

import com.excel.bean.SpecialAllocationBean;
import com.excel.model.AllocReqDet;
import com.excel.model.AllocReqHeader;
import com.excel.model.AllocReqImages;

public interface SpecialAllocationRepository {

	List<Object> getDoctorDetails(Long docId) throws Exception;

	List<Object> getProductDetails(Long divId, Long prodId,String companyCode) throws Exception;

	AllocReqHeader getObjectById(Long allocId) throws Exception;

	AllocReqDet getObjectByDetailId(Long detailId) throws Exception;

	AllocReqImages getObjectByImageId(Long iamgeId) throws Exception;

	List<SpecialAllocationBean> specialAllocationDetails(Long docId, Long requestorId, Long year, String status,String requestType,String recipientStringId);

	List<AllocReqDet> getDetailListByReqId(Long alloc_req_id) throws Exception;

	List<Object> getLevels(Long divId) throws Exception;

	List<Object> getFieldStaffDetails(String empId, Long fs_id) throws Exception;


	List<SpecialAllocationBean> specialAllocationDetailsById(Long alloc_req_id, Long finYear, String status);

	String getServiceNumList(String serviceNo) throws Exception;
	
	public List<AllocReqHeader> getAllocReqHdsBy_BlkReqNo(String blkReqNo) throws Exception;
	
	public List<SpecialAllocationBean> getExistingSpecialAllocation(String requestType)throws Exception;

}
