package com.excel.repository;

import java.util.List;

import com.excel.bean.FieldStaffBean;
import com.excel.model.DivField;
import com.excel.model.FieldStaff;
import com.excel.model.FieldStaffList;
import com.excel.model.FieldStaffListNew;
import com.excel.model.V_Fieldstaff;
import com.excel.model.V_Fieldstaff_JAVA;
import com.excel.model.V_G_FieldStaff;

public interface FieldStaffRepository {
	
	public List<FieldStaffList> getFieldStaffList(String emp_id,Long div_id) throws Exception;
	V_Fieldstaff getDivListByEmpId(String fstaff_samp_disp_ind, Long fstaff_id) throws Exception;
	List<FieldStaff> getFieldStaffsByEmpIdAndHasResigned(Long divId, boolean hasResigned, String companyCode)
			throws Exception;
	Long getDivIdByFsId(Long fstaff_id) throws Exception;
	List<Object> getNoOfStaffByStaffwiseTraining(String lvlCode, String divId, String status, String allocMode,
			String cfalocId, String dist, String fstaffType, String alloc_id, String period_code, String alloc_cycle,
			String prodtype, String fin_year, String company,String sub_team_code);
	List<Object> getState(String divId, String status, String period_code, String fin_year, String company,
			String saveMode, String alloctype, String alloc_id, String alloc_cycle, String prodtype,List<String> brands,String sub_team_code);
	List<Object> getZone(String divId, String status, String period_code, String fin_year, String company,
			String saveMode, String alloctype, String alloc_id, String alloc_cycle, String prodtype,List<String> brands,String sub_team_code);
	Long getNoOfStaffByStaffwise(String lvlCode, String divId, String status, String allocMode, String cfalocId,
			String dist, String fstaffType, String alloc_id, String period_code, String alloc_cycle, String prodtype,
			String fin_year, String company,String sub_team_code);
	StringBuffer getStaffDataQuery(String lvlCode, String cfalocId, String divId, String status, String fieldtype,
			String alloc_id, String period_code, String alloc_cycle, String prodtype, String fin_year, String company,
			String fs_column, String common_fs_ind,String sub_team_code,String alloc_type);
	FieldStaff getObjectByStaffId(Long staff_id) throws Exception;
	List<FieldStaff> getFstaffData(String lvlCode, String divId, String status, String allocMode, String cfalocId,
			String fs_column, String fstaffType, String alloc_id, String alloc_cycle, String fin_year,
			String period_code, String prodtype, String company,String sub_team_code) throws Exception;
	List<Object> getFstaffDataFoDes(String lvlCode, String divId, String status, String allocMode, String cfalocId,
			String fs_column, String fieldtype, String alloc_id, String period_code, String alloc_cycle,
			String prodtype, String fin_year, String company, String common_fs_ind,String sub_team_code,String alloc_type) throws Exception;
	List<DivField> getLevel(String divId, String ind);
	// vruti
	Boolean checkUniqueGenericName(String genName,String divId);
	List<FieldStaff> getRequestor(String divId);
	//List<FieldStaffBean> getMgr(String mgrId);
	List<FieldStaff> getMgr(String mgrId);
	List<FieldStaff> getAllMgr(String level, String divId, String trainingInd);
	public List<V_G_FieldStaff> getAllActiveFieldStaffDetailList();
	List<V_G_FieldStaff> getTextParameterFstaffDetail(String name, String parameter, String text,String empId);
	public List<FieldStaff> getFstaffDetailById(String id);
	List<FieldStaff> getFieldStaffByDivAndLevelcode(Long fstaff_id, String levelCode, String category) throws Exception;
	public List<FieldStaffListNew> getFieldStaffListNew(String emp_id,Long div_id,String allocType) throws Exception;
	List<Object> getStaffDetails(String divId, String status, String lvlCode, String saveMode, String alloctype,
			String alloc_id, String alloc_cycle, String period_code, String fin_year, String company, String prodtype,
			String fieldtype, List<String> brands,String sub_team_code);
	List<FieldStaff> getNotCfaLinkFieldstaff(Long divId);
	List<FieldStaffBean> getFstaffDetail(String mgrId, String level, String divId);
	List<FieldStaff> getFieldStaffByTerrCode(String terrCode) throws Exception;
	List<FieldStaff> getFieldStaffByEmployeeNumber(String fstaff_emp_num, Long fs_div_id) throws Exception;
	
	void updateFieldByTerritoryCode(String territoryccode,String levelcode,String ipaddr,String usrid)throws Exception;
//P&G
	List<Object> getNoOfStaffByStaffwiseTraining(String lvlCode, String divId, String status, String allocMode,
			String cfalocId, String dist, String fstaffType, String alloc_id, String period_code, String alloc_cycle,
			String prodtype, String fin_year, String company,String sub_team_code,String alloc_type);
	
	List<Object> getZone(String divId, String status, String period_code, String fin_year, String company,
			String saveMode, String alloctype, String alloc_id, String alloc_cycle, String prodtype,List<String> brands,String sub_team_code,String alloc_type);
	
	List<Object> getState(String divId, String status, String period_code, String fin_year, String company,
			String saveMode, String alloctype, String alloc_id, String alloc_cycle, String prodtype,List<String> brands,String sub_team_code,String alloc_type);

	List<Object> getStaffDetails(String divId, String status, String lvlCode, String saveMode, String alloctype,
			String alloc_id, String alloc_cycle, String period_code, String fin_year, String company, String prodtype,
			String fieldtype, List<String> brands,String sub_team_code,String alloc_type);
	
	Long getNoOfStaffByStaffwise(String lvlCode, String divId, String status, String allocMode, String cfalocId,
			String dist, String fstaffType, String alloc_id, String period_code, String alloc_cycle, String prodtype,
			String fin_year, String company,String sub_team_code,String alloc_type);
	public FieldStaff getFstaffDetailsFromDocId(Long doc_fstaff_id) throws Exception;
	
	public FieldStaff getFstaffbyFsMclNoAndDivId(String mclNo,Long divId)throws Exception;
	
	public FieldStaff getFsIdByFsCode(String fsCode)throws Exception;
	
	public Long getTerrIdByFsId(Long fsId) throws Exception;
	
	Long getFstaffDivIdForCfa_to_Stk_requestor(Long loc_id) throws Exception;
	public boolean isFileExist(String file) throws Exception;
	public FieldStaff updateMobileNoById(String fstaff_mobile, Long fstaff_id)throws Exception;
	
}
