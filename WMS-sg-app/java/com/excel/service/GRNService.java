package com.excel.service;

import java.io.IOException;
import java.math.BigDecimal;
import java.text.ParseException;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.web.multipart.MultipartFile;

import com.excel.bean.GRNBean;
import com.excel.model.AUTO_TRANSFER;
import com.excel.model.GrnDetails;
import com.excel.model.GrnHeader;
import com.excel.model.SmpBatch;
import com.excel.model.SmpBatchNS;
import com.excel.model.V_Grn_Details;

public interface GRNService {
	
	Map<String, Object> saveGRN(GRNBean bean) throws Exception;
	GrnHeader saveGrnHeader(GRNBean bean) throws Exception;
	SmpBatch saveSmpBatch(GrnDetails detail, GRNBean bean) throws Exception;
	GrnDetails saveGrnDetail(GRNBean bean, GrnHeader header) throws Exception;
	void saveBatchStockLog(GRNBean bean, GrnDetails detail,GrnHeader header) throws Exception;
	void saveBatchStockLogNonSalable(GRNBean bean, GrnDetails detail,GrnHeader header) throws Exception;
	SmpBatchNS saveSmpBatchNonSalable(GrnDetails detail, GRNBean bean) throws Exception;
	List<V_Grn_Details> getGrnDetailViewByGrnId(Long grnId) throws Exception;
	boolean checkIfBatchExistsInGrnDetail(Long grnId, Long prodId, Long batchId) throws Exception;
	BigDecimal deleteGrnDetailById(Long grnDetailId, String empId);
	void updateForGST(GrnDetails grnDetails, GrnHeader grnHeader, String comp_cd) throws Exception;
	Map<String, Object> getGrnApprovalSelectedDetail(long grnId);
	
	void updateDetailOnSelfDiscardOfGRN(String empId, long grnId, String remark, HttpServletRequest request,String compcd);
	String uploadFile(MultipartFile file);
	Object viewFile(long grnId) throws Exception;
	void updateDetailOnSelfApproveOfGRN(Long grnId, String user, String email, String remark,
			HttpServletRequest request,List<Long> detailIds,List<BigDecimal> shortQtys,List<BigDecimal> damageQtys,String companyCode) throws Exception;
	void updateDetailOnSelfApproveOfGRN(String empId, long grnId, String remark, HttpServletRequest request);
	void saveGrnApproval(Long grnId, String last_approved_by, String comp_cd, String isapproved, String motivation);;
	List getGrnDetailForGrnVoucherPrint(String sdate, String edate, String supId, String locId,String finyr,String finyrflg);
	Map<String, Object> getGrnApprovalHeaderDetail(String empId, String empLoc, String role,String compCode);
	void confirmGrn(Long grnId,String companyCode) throws Exception;
	Long getSupplierForReturnFromField(Long sub_com_id) throws Exception;

	List<AUTO_TRANSFER> getAuthFromCsv(MultipartFile file, String auth, String username, String ip_Address) throws IOException, ParseException;
	String DeleteWholeGrn(Long grnId,String empId)throws Exception;
	void reverseBatchBeforeModify(GrnDetails detail,GrnHeader header) throws Exception;
	Map<String, Object> validationForFileUpload(String ip_Address, String user_id, String currentLocation,
			String cURR_PERIOD, String finyr, String company, MultipartFile file, String trim, String username) throws IOException;
	Map<String, Object> search_auth_Code(String authenticationCode);
	Map<String, Object> callProcudeureForAutoGrn(String ip_Address, String user_id, String currentLocation,
			String cURR_PERIOD, String finyr, String company, String cSPTRF_SLNO, Map<String, Object> map);
	String getHeaderRemarks(long grnId) throws Exception;
	
}
