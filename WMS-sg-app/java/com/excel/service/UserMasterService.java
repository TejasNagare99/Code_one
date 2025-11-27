package com.excel.service;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import com.excel.bean.ChangePasswordBean;
import com.excel.bean.MailBean;
import com.excel.bean.Parameter;
import com.excel.bean.UserBean;
import com.excel.bean.UserBrandMapopingBean;
import com.excel.bean.UserMasterBean;
import com.excel.model.EmployeeDetails;
import com.excel.model.LoginDetails;
import com.excel.model.Password_Log;
import com.excel.model.SG_d_parameters_details;
//import com.excel.bean.ChangePasswordBean;
//import com.excel.bean.UserMasterBean;
import com.excel.model.Usermaster;
import com.itextpdf.text.pdf.PdfWriter;

public interface UserMasterService {
	Map<String, Object> authenticateUser(String username, String password, Boolean checkLock, 
			String company_code,String ipaddr,String securityanswer,int attempt) throws Exception;
	void lockOrUnlockUser(String userId, boolean lock) throws Exception;
	void unlockAllUsers() throws Exception;
	//void save(UserMasterBean bean) throws Exception;
	List<Usermaster> getLockedUsers(String levelCode) throws Exception;
	void unlockorLockUsersByUsernames(List<String> usernames, String lockUnlock) throws Exception;
	//List<UserMasterBean> getActiveUsersListByDiv(Long divId, String companyCode, String levelCode) throws Exception;
	void sendPassword(Usermaster user, String password) throws Exception;
	Object getObjectByUsername(String username) throws Exception;
	Usermaster getUserByUsername(String username) throws Exception;
	Usermaster getUserByFsId(Long fsId) throws Exception;
	void fixUserPassword(String companyCode) throws Exception;
	void changePassword(ChangePasswordBean bean) throws Exception;
	Map<String, Object> login(String username, String password, Boolean checkLock, String company_cd,int attempt,String ipaddr,String otp) throws Exception;
	String getAllowBatchCreateInd(String empId) throws Exception;
	public List<Parameter> getDivAccessedByUser(String userId)throws Exception;
	List<MailBean> getEmpDetailForMail(String userId) throws Exception;
	List<UserMasterBean> getLockUser() throws Exception;
	void unlockUserOnRequest(List<String> ids, boolean lock) throws Exception;
	Map<String, Object> saveUserMaster(UserBean bean) throws Exception;
	void saveOrModifyAm_m_login_detail(String ld_email, String ld_pass, String ld_lgnid, String is_temp,
			String curr_user_id, String ld_emp_cb_id, String password_lock, String user_lock, String user_type,
			String ld_usr_typ_flg, String ip_add, String ld_status, String ld_no_of_attempt_flg, String DPTLOC_ID,
			String allow_batch_create, Long ld_max_login_attempts, Long ld_hours_locked, String assistInd,
			String save_ind) throws Exception;
	EmployeeDetails getEmployeeDetails(String username) throws Exception;
	public Usermaster getUserByEmpId(String empId) throws Exception;
	Map<String, Object> updateUserMaster(UserBean bean);
	List<UserMasterBean> getActiveUserList() throws Exception;
	Map<String, Object> saveUserBrandMapping(UserBrandMapopingBean bean) throws Exception;

	void insertPasswordLocktime(String username)throws Exception;
	void setLog(String username, String string, String remoteAddr, int count) throws Exception;
	Integer getLatestAttempts(String username)throws Exception;
	void savePasswordLogs(Password_Log passlog, Usermaster user) throws Exception;
	String changeTemporaryPassword(String username, String password, String secretqstn, String secretanswr)
			throws Exception;
	List<SG_d_parameters_details> getPasswordRecoveryQuestions(String logquest) throws Exception;
	
	List<Usermaster> getUsernameData() throws Exception;
	void updateUsernameData(String ld_lgnid) throws Exception;
	void updateUsernameDatatoN(String ld_lgnid) throws Exception;
	void getSendEmailNotification() throws Exception;
	void setLastAccessedIp(Usermaster user, String ipaddr) throws Exception;
	Boolean unlockUserIfSameIp(String username) throws Exception;
	public void generateotp(Usermaster user, String password) throws Exception;
	public void EraseOtp(String username)throws Exception;
	public String generatenewEncryption(String username)throws Exception;
	public int getSaltvalByUsername(String username)throws Exception;
	void setlockotpsend(String username)throws Exception;
	PdfWriter generatePDFlock(String userid, PdfWriter writer)throws Exception;
	String generateExcellock(String filePath, String filename, String empId, String string)throws Exception;
	String getEmpIdByUsername(String username) throws Exception;
	String getEmpIdByEmail(String email) throws Exception;
	
	String getEmployeeIdFromRequest(HttpServletRequest request) throws Exception;
	public LoginDetails getLoginDetailsByUserName(String userName) throws Exception;
}
