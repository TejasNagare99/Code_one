package com.excel.repository;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;

import org.springframework.stereotype.Repository;

import com.excel.bean.MailBean;
import com.excel.bean.Parameter;
import com.excel.bean.UserMasterBean;
import com.excel.model.EmployeeDetails;
import com.excel.model.LoginDetails;
import com.excel.model.Password_Log;
import com.excel.model.SG_d_parameters_details;
import com.excel.model.Usermaster;

@Repository
public interface UserMasterRepository{
			
	Usermaster getUserByUsername(String username) throws Exception;
	Usermaster getUserByUsernameAndPassword(String username, String password) throws Exception;
	void lockOrUnlockUser(String userId, boolean lock) throws Exception;
	void unlockAllUsers() throws Exception;
	List<Usermaster> getLockedUsers(String levelCode) throws Exception;
	void unlockorLockUsersByUsernames(List<String> usernames, String lockUnlock) throws Exception;
	void updatePasswordByUsername(String username, String newPassword) throws Exception;
	void updateUserStatusByFsId(Long fsId, String status) throws Exception;
	Usermaster getUserByFsId(Long fsId) throws Exception;
	List<Usermaster> getAllUsers(String companyCode) throws Exception;
	public HashMap<String, String> getUserInfo(String username, String password) throws Exception;
	HashMap<String, String> getUserDivision(String empId) throws Exception;
	LoginDetails getLoginDetailsByUserName(String userName) throws Exception;
	String getAllowBatchCreateInd(String empId) throws Exception;
	HashMap<String, String> getUserDivisionArray(String empId) throws Exception;
	List<Parameter> getDivAccessedByUser(String userId) throws Exception;
	String getLfPass(String username) throws Exception;
	List<MailBean> getEmpDetailForMail(String userId) throws Exception;
	List<UserMasterBean> getLockUser() throws Exception;
	void unlockUserOnRequest(List<String> userId, boolean lock) throws Exception;
	Usermaster getUserByEmpId(String username) throws Exception;
	EmployeeDetails getEmployeeDetails(String username) throws Exception;
	List<UserMasterBean> getActiveUserList() throws Exception;
	String saveOrModifyHr_M_Employee(String emp_id, String emp_egrp_id, String emp_dept_id, Long emp_loc_id,
			String emp_COMP_id, String emp_desg_id, String emp_sup_emp_id, String emp_fnm, String emp_mnm,
			String emp_lnm, String emp_gender, String emp_birth_dt, String emp_lgn_valid_frm_dt,
			String emp_lgn_valid_to_dt, String emp_join_dt, String emp_resign_dt, BigDecimal emp_sal,
			String emp_abstract, String emp_add, String emp_phno, String emp_mob, String emp_email, String emp_lgn_lock,
			String emp_emg_cont_prsn, String emp_emg_cont_phno, String curr_user_id, String ip_add, String emp_status,
			String save_mode, String fs_id, String rm_id, String dm_id, String sm_id) throws Exception;
	UserMasterBean getUserDetailsByEmpId(String empId) throws Exception;

	void insertPasswordLocktime(String username)throws Exception;
	Integer getLatestAttempts(String username)throws Exception;
	List<SG_d_parameters_details> getPasswordRecoveryQuestions(String logquest) throws Exception;
	
	List<Usermaster> getUsernameData() throws Exception;
	void updateUsernameData(String ld_lgnid)  throws Exception;
	void updateUsernameDatatoN(String ld_lgnid) throws Exception;
	List<Password_Log> getUserPassByempId(String ld_emp_cb_id) throws Exception;
	void LockOrUnlockAmLoginDtl(String userId, boolean lock) throws Exception;
	int getSaltvalByUsername(String username)throws Exception;
	public void EraseOtp(String username)throws Exception;
	List<Password_Log> getUserPass() throws Exception;
	Usermaster getpdfandexcellockind(String emp_cd) throws Exception;
	String getEmpIdByUsername(String username) throws Exception;
	String getEmpIdByEmail(String email) throws Exception;
}
