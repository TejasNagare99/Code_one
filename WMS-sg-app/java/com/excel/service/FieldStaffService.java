package com.excel.service;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.web.multipart.MultipartFile;

import com.excel.bean.FieldStaffBean;
import com.excel.model.AllocReqHeader;
import com.excel.model.DivField;
import com.excel.model.DoctorMaster;
import com.excel.model.FieldStaff;
import com.excel.model.FieldStaffList;
import com.excel.model.FieldStaffListNew;
import com.excel.model.V_Fieldstaff;
import com.excel.model.V_G_FieldStaff;

public interface FieldStaffService {
	
	public List<FieldStaffList> getFieldStaffList(String emp_id, Long div_id) throws Exception;
	public V_Fieldstaff getDivListByEmpId(String fstaff_samp_disp_ind, Long fstaff_id)throws Exception;
	List<FieldStaff> getFieldStaffsByEmpIdAndHasResigned(Long divId, boolean hasResigned, String companyCode)
			throws Exception;
	Long getDivIdByFsId(Long fstaff_id) throws Exception;
	List<Object> getNoOfStaffByStaffwiseTraining(String lvlCode, String divId, String status, String allocMode,
			String cfalocId, String dist, String fstaffType, String alloc_id, String period_code, String alloc_cycle,
			String prodtype, String fin_year, String company,String sub_team_code);
	
	List<DivField> getLevel(String divId, String ind);
	Boolean checkUniqueGenericName(String genName, String divId);
	void saveFieldStaff(FieldStaffBean pb, String empId, String locId, HttpSession session, HttpServletRequest request)
			throws Exception;
	List<FieldStaff> getRequestor(String divId);
	//List<FieldStaffBean> getMgr(String mgrId);
	List<FieldStaff> getMgr(String mgrId);
	List<FieldStaff> getAllMgr(String level, String divId, String trainingInd);
	List<V_G_FieldStaff> getAllActiveFieldStaffDetailList();
	public List<V_G_FieldStaff> getTextParameterFstaffDetail(String name, String parameter, String text,String empId);
	public List<FieldStaff> getFstaffDetailById(String id);
	void updateFieldStaff(String fstaffId, FieldStaffBean fb, String empId, String locId, HttpSession session,
			HttpServletRequest request) throws Exception;

	List<FieldStaff> getFieldStaffByDivAndLevelcode(Long fstaff_id, String levelCode, String category) throws Exception;
	Long setFiledStafForSpecialAllocation(AllocReqHeader header, FieldStaff staff, DoctorMaster docMaster)
			throws Exception;
	public List<FieldStaffListNew> getFieldStaffListNew(String emp_id,Long div_id,String allocType) throws Exception;
	Long getCfa_id(Long fstaff_id, Long sub_comp_id) throws Exception;
	FieldStaff getObjectByStaffId(Long staff_id) throws Exception;
	void FsCfaLinkage(Long fstaff_id, Long loc_id, AllocReqHeader header, Long sub_comp_id) throws Exception;
	List<FieldStaff> getNotCfaLinkFieldstaff(Long divId);
	List<FieldStaffBean> getFstaffDetail(String mgrId, String level, String divId);
	Long setFiledStafForPgDoctor(FieldStaff staff, DoctorMaster docMaster) throws Exception;
	public FieldStaff getFsIdByFsCode(String fsCode)throws Exception;
	Long getFstaffDivIdForCfa_to_Stk_requestor(Long loc_id) throws Exception;
	public Map<String, Object> updateFstaffMobileNo(MultipartFile file, String username, String ipAddress)throws Exception;
}	
