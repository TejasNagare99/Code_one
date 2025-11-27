package com.excel.service;

import java.util.List;
import java.util.Map;

import org.springframework.web.multipart.MultipartFile;

import com.excel.bean.IAABean;
import com.excel.model.BatchWithholdReleaseHDR;
import com.excel.model.IaaDetailForApprovalMail;
import com.excel.model.IaaDetailForMail;
import com.excel.model.Iaa_Map;
import com.excel.model.Stock_Adj_Details;
import com.excel.model.Stock_Adj_Header;

public interface IAAService {

	List<Iaa_Map> getReasonsFromIaaMap() throws Exception;
	Object getIaaDetailById(Long reasonId) throws Exception;
	List<Object> getProductListWithPack(String comp_cd,long sub_comp_id, String prefix, String prod_type, long prod_grp, long div_id) throws Exception;
	Object getQtyAndValueNS(Long prod_id, String flag, Long loc_id, Long batch_id, String stock_type) throws Exception;
	Object getQtyAndValue(Long prod_id, String flag, Long loc_id, Long batch_id) throws Exception;
	Object getQuarantineQtyAndValue(Long prod_id, String flag,Long loc_id, Long batch_id) throws Exception;
	List<Object> getBatchListForIAA(Long loc_id, Long prod_id, String stock_type,String outstock_type, String status) throws Exception;
	Map<String, Object> saveIaaEntry(IAABean bean) throws Exception;
	Long saveBatchWithHoleHeader(IAABean bean) throws Exception;
	void saveBatchWithHodlDtl(IAABean bean) throws Exception;
	List<Object> getStockAdjDetailsByHeaderId(Long header_id) throws Exception ;
	List<Object> getStockAdjDtlByHdrForApproval(Long header_id, int appr_level, String appr_status) throws Exception;
	void iaaSelfApproval(IAABean bean) throws Exception;
	BatchWithholdReleaseHDR saveBatchWithHoldHeaderForIAAAppr(IAABean bean,Stock_Adj_Header header) throws Exception;
	void saveBatchWithHodlDtlForIAAAppr(IAABean bean,Object[] stkDtl,BatchWithholdReleaseHDR bwHeader) throws Exception;
	void iaaFinalApproval(IAABean bean) throws Exception;
	void finalApproval(Long stkAdjHeaderId,String last_approved_by,String comp_cd,String isapproved,String motivation) throws Exception;
	void iaaApprUpload(List<MultipartFile> file, String headerId) throws Exception;
	Stock_Adj_Header getObjectById(Long headerId) throws Exception;
	List<Object> getIAAHeaderList(String comp_cd, Long fin_yr, String period_cd, String empId) throws Exception;
	
	List<IaaDetailForMail> getIaaDetailForMail(Long locid,Long frmid,Long toid)throws Exception;
	List<IaaDetailForApprovalMail> getIaaDetailForApprovalMail(Long frmid,Long toid)throws Exception;
}
