package com.excel.service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.web.multipart.MultipartFile;

import com.excel.bean.SpecialAllocationBean;
import com.excel.bean.SpecialAllocationBean.ProductDetails;
import com.excel.model.AllocReqDet;
import com.excel.model.AllocReqHeader;
import com.excel.model.AllocReqImages;

public interface SpecialAllocationService {

	public List<Object> getDoctorDetails(Long docId) throws Exception;
	public List<Object> getProductDetails(Long divId,Long prodId,String companyCode) throws Exception;
	public Map<String, Object>  saveSpecialAllocation(SpecialAllocationBean bean) throws Exception;
	public AllocReqHeader setAllocReqHeader(SpecialAllocationBean bean) throws Exception;
	public AllocReqDet setAllocReqDet(SpecialAllocationBean bean, AllocReqHeader header, ProductDetails allocDetail)throws Exception;
	public AllocReqImages setAllocReqImages(SpecialAllocationBean bean, AllocReqHeader header) throws Exception;
	Map<String, Object> uploadSpecialAllocImage(MultipartFile file);
	void deleteProduct(Long allocDetailId, Long year) throws Exception;
	void specialAllocationApproval(Long allocId, String user, String email, String remark, HttpServletRequest request,
			HttpSession session,boolean isbulk) throws Exception;
	void sendMailForSpecialAllocation(Long grnId, String user, String apptovalType, String taskId, String status,
			String nextApprovar,Long year, String tranType,String companyCode,String ipaddr) throws Exception;
	List<SpecialAllocationBean> specialAllocationDetails(Long docId, Long requestorId, Long year, String status,String requestType,String recipientStringId);
	List<Object> getLevels(Long divId) throws Exception;
	List<Object> getFieldStaffDetails(String empId, Long fs_id) throws Exception;
	void finalApproval(Long allocationId, String last_approved_by, String comp_cd, String isapproved, String motivation)
			throws Exception;
	List<SpecialAllocationBean> specialAllocationDetailsById(Long alloc_req_id, Long finYear,String status);
	void updateForFrieght(Long alloc_req_id, String type, BigDecimal value) throws Exception;
	public String getServiceNumList(String serviceNo) throws Exception;
	
	public List<AllocReqHeader> getAllocReqHdsBy_BlkReqNo(String blkReqNo)throws Exception;
	public List<SpecialAllocationBean> getExistingSpecialAllocation(String requestType)throws Exception;

}
