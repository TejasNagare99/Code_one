package com.excel.repository;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.web.multipart.MultipartFile;

import com.excel.model.AUTO_TRANSFER;
import com.excel.model.GrnDetails;
import com.excel.model.GrnHeader;
import com.excel.model.Tax_data_for_grn;
import com.excel.model.V_Grn_Details;

public interface GrnRepository {

	GrnHeader getObjectById(Long grnId) throws Exception;
	Long getMaxGrnId() throws Exception;
	GrnDetails getObjectByDetailId(Long grnDetailId) throws Exception;
	BigDecimal getTotalGrnValueByGrnId(Long grnId, Long grnDetailId) throws Exception;
	String getGrnNumber(String avField_Nm, String avTable_Nm, String avCharDigit, String avwherecondn) throws Exception;
	List<V_Grn_Details> getGrnDetailViewByGrnId(Long grnId) throws Exception;
	boolean checkIfBatchExistsInGrnDetail(Long grnId, Long prodId, Long batchId) throws Exception;
	void deleteById(Long grndetailId) throws Exception;
	void updateBatchForGrn(Long batch_id, BigDecimal grn_qty);
	BigDecimal getTotal_Pur_GrnValueByGrnId(Long grnId, Long grnDetailId) throws Exception;
	List<GrnDetails> getGrnDetailsByGrnId(Long grn_id) throws Exception;
	void updateBatchQuarantine(Long batch_id, BigDecimal qty);
	Map<String, Object> getGrnApprovalSelectedDetail(long grnId);
	public String uploadFile(MultipartFile file);
	void updateDetailOnSelfDiscardOfGRN(String empId, long grnId, String remark, HttpServletRequest request);
	void updateDetailOnSelfApproveOfGRN(String empId, long grnId, String remark, HttpServletRequest request,List<Long> detailIds,List<BigDecimal> shortQtys,List<BigDecimal> damageQtys);
	Object viewFile(long grnId) throws Exception;
	List<Object> getGrnDetailsForMailSend(Long grn_id, String emp_id,String selApprove,String finyrid) throws Exception;
	List getGrnDetailForGrnVoucherPrint(String sdate, String edate, String supId, String locId,String finyr,String finyrflg);
	Map<String, Object> getGrnApprovalHeaderDetail(String empId, String empLoc, String role,String compcode);
	void updateBatchNSForGrn(Long batch_id, BigDecimal grn_qty,Long loc_id,String stock_type,String status,Long prodId) throws Exception;
	void updateBatchNSQuarantine(Long batch_id, BigDecimal qty);
	void IaaCreationInGrnApproval(Long grnId, String finYear, String periodCode, String userId, String ipAddress)
			throws Exception;
	void confirmGrn(long grnId,String companyCode);
	Long getSupplierForReturnFromField(Long sub_com_id) throws Exception;
	List<Object> saveBeans(List<AUTO_TRANSFER> csptrf_beans, String ip_Address, String user_id, String currentLocation, String cURR_PERIOD, String finyr, String company) throws Exception;
	Map<String, Object> search_auth_Code(String authenticationCode);
	Map<String, Object> checkErrMessages_fro_the_trf_number(String authenticationCode);
	Map<String, Object> callProcudeureForAutoGrn(String ip_Address, String user_id, String user_id2,
			String currentLocation, String cURR_PERIOD, String finyr, String company, String cSPTRF_SLNO,
			Map<String, Object> map);
	Map<String, Object> savedOrNot(String authcode);
	Map<String, Object> checkSavedOrNot(String authenticationCode);
	BigDecimal getTotalCustDutyValueByGrnId(Long grnId, Long grnDetailId)throws Exception;
	BigDecimal getTotalDiscountValueByGrnId(Long grnId, Long grnDetailId)throws Exception;
	String getHeaderRemarks(long grnId) throws Exception;
	Tax_data_for_grn getTaxData_And_total_value(Long grnId) throws Exception;
}
