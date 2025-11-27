package com.excel.repository;

import java.util.List;

import com.excel.bean.IAABean;
import com.excel.model.IaaDetailForApprovalMail;
import com.excel.model.IaaDetailForMail;
import com.excel.model.Iaa_Map;
import com.excel.model.Stock_Adj_Details;
import com.excel.model.Stock_Adj_Header;

public interface IAARepository {

	List<Iaa_Map> getReasonsFromIaaMap() throws Exception;
	Object getIaaDetailById(Long reasonId) throws Exception;
	List<Object> getProductListWithPack(String comp_cd,long sub_comp_id, String prefix, String prod_type, long prod_grp, long div_id) throws Exception;
	Object getQtyAndValueNS(Long prod_id, String flag, Long loc_id, Long batch_id, String stock_type) throws Exception;
	Object getQtyAndValue(Long prod_id, String flag, Long loc_id, Long batch_id) throws Exception;
	Object getQuarantineQtyAndValue(Long prod_id, String flag,Long loc_id, Long batch_id) throws Exception;
	List<Object> getBatchListForIAA(Long loc_id, Long prod_id, String stock_type,String outstock_type, String status) throws Exception;
	Object saveStockAdjHeader(IAABean bean) throws Exception;
	Long saveStockAdjDetail(IAABean bean) throws Exception;
	List<Object> getStockAdjDetailsByHeaderId(Long header_id) throws Exception ;
	List<Object> getIAAHeaderList(String comp_cd, Long fin_yr, String period_cd, String empId) throws Exception; 
	List<Object> getStockAdjDtlByHdrForApproval(Long header_id, int appr_level, String appr_status) throws Exception;
	Integer getSTKADJ_APPR_REQ(Long stkadj_id) throws Exception;
	Stock_Adj_Header getObjectById(Long headerId) throws Exception;
	Stock_Adj_Details getDetailById(Long detailId) throws Exception;
	Integer saveStockAdjFinalApproval(Long adj_detail_id, String status, String user_id, String ip_addr) throws Exception;
	Object getStockAdjDetailsByDetId(Long detail_id) throws Exception;

	List<IaaDetailForMail> getIaaDetailForMail(Long locid,Long frmid,Long toid)throws Exception;
	List<IaaDetailForApprovalMail> getIaaDetailForApprovalMail(Long frmid,Long toid)throws Exception;
}
