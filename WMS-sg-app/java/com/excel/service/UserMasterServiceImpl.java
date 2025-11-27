package com.excel.service;

import java.io.File;
import java.io.FileNotFoundException;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.time.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.security.authentication.AuthenticationCredentialsNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.excel.bean.ChangePasswordBean;
import com.excel.bean.MailBean;
import com.excel.bean.Parameter;
import com.excel.bean.UserBean;
import com.excel.bean.UserBrandMapopingBean;
import com.excel.bean.UserMasterBean;
import com.excel.configuration.JwtProvider;
import com.excel.exception.MedicoException;
import com.excel.model.Company;
import com.excel.model.EmailFrom;
import com.excel.model.EmployeeDetails;
import com.excel.model.FinYear;
import com.excel.model.LoginDetails;
import com.excel.model.Password_Log;
import com.excel.model.Period;
import com.excel.model.SG_d_parameters_details;
import com.excel.model.UserBrandMap;
import com.excel.model.Usermaster;
import com.excel.model.UsermasterLog;
import com.excel.repository.FinancialYearRepository;
import com.excel.repository.LocationRepository;
import com.excel.repository.PeriodMasterRepository;
import com.excel.repository.TransactionalRepository;
import com.excel.repository.UserDepartmentRepository;
import com.excel.repository.UserMasterRepository;
import com.excel.security.PasswordManager;
import com.excel.utility.CodifyErrors;
import com.excel.utility.MedicoConstants;
import com.excel.utility.MedicoResources;
import com.excel.utility.SendMail;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.pdf.PdfWriter;

import net.bytebuddy.utility.RandomString;
import net.lingala.zip4j.core.ZipFile;
import net.lingala.zip4j.model.ZipParameters;
import net.lingala.zip4j.util.Zip4jConstants;

@Service
public class UserMasterServiceImpl implements UserMasterService, MedicoConstants {

	@Autowired
	private UserMasterRepository userMasterRepository;
	@Autowired
	private FinancialYearRepository financialYearRepository;
	@Autowired
	private PeriodMasterRepository periodMasterRepository;
	@Autowired
	private TransactionalRepository transactionalRepository;
	@Autowired
	private UserDepartmentRepository userDepartmentRepository;
	@Autowired
	private NotificationService notificationService;
	// @Autowired private LoginMasterRepository master;
	@Autowired
	private MailContentService mailContentService;
	@Autowired
	private EmailService emailservice;
	@Autowired
	private UserMasterService userMasterService;
	@Autowired
	private ParameterService parameterservice;
	@Autowired
	private EmailService emailService;
	@Autowired
	private LocationRepository locationRepository;
	@Autowired
	private CompanyMasterService companyMasterService;
	@Autowired
	private JwtProvider tokenProvider;

	@Override
	public Map<String, Object> authenticateUser(String username, String password, Boolean checkLock,
			String company_code, String ipaddr, String securityanswer, int attempt) throws Exception {
		Map<String, Object> map = new HashMap<>();
		// System.out.println("attempt " + attempt);
		map.put("ALLOW", false);
		int attemmpt = attempt + 1;
		try {
			// System.out.println("password " + password);
			Usermaster user = this.userMasterRepository.getUserByUsername(username);
			if (!password.equals("0")) {
				boolean isTempPassword = false;
				if (user == null) {

					this.setLog(username, "Invalid Username", ipaddr, attemmpt);

					throw new MedicoException("User Not Found.");

				} else {

					if (user.getPassword_lock().equals(Y)) {

						map.put("ISPASSLOCK", true);
						throw new MedicoException("The user is Locked. Please Contact Administrator.");
					} else {
						int salt = this.userMasterRepository.getSaltvalByUsername(user.getLd_lgnid());
						
						System.out.println(
						"database password:::" + PasswordManager.decrypt(PasswordManager.decrypt(user.getLd_pass_ang())));
						
						// System.out.println("Encypt pass "
//								+ PasswordManager.decrypt(PasswordManager.decrypt(user.getLd_pass_ang(), salt), salt));
						
//						if (!password.equals(
//								PasswordManager.decrypt(PasswordManager.decrypt(user.getLd_pass_ang(), salt), salt))) {
							
							if (!password.equals(PasswordManager.decrypt(PasswordManager.decrypt(user.getLd_pass_ang())))) {
							
							
							
							
							map.put("ERROR", "Invalid input");
							if (attemmpt >= 3) {
								userMasterRepository.insertPasswordLocktime(username);
								this.setLog(username, "User Locked", ipaddr, attemmpt);

							} else {
								this.setLog(username, "Invalid Username", ipaddr, attemmpt);

							}
						} else {
							map.put("SUCCESS", "Authenticated");
						}
					}
				}
			} else {
				if (user == null) {
					// System.out.println(username + " " + user.getLd_ins_usr_id());
					map.put("ERROR", "Invalid Login Details");
					map.put("Attempts", attempt);
				} else {
					if (user.getPassword_lock().equals(Y)) {

						map.put("ISPASSLOCK", true);
						throw new MedicoException("The user is Locked. Please Contact Administrator.");
					} else {
						int salt = this.userMasterRepository.getSaltvalByUsername(user.getLd_lgnid());
						// System.out.println("Encypt pass "
//								+ PasswordManager.decrypt(PasswordManager.decrypt(user.getLd_pass_ang(), salt), salt));
						if (!password.equals(
								PasswordManager.decrypt(PasswordManager.decrypt(user.getLd_pass_ang(), salt), salt))
								&& !user.getSecret_answer().equals(securityanswer)) {
							map.put("ERROR", "Invalid Login Details");
						} else {
							map.put("SUCCESS", "Authenticated");
						}
					}

				}
			}
		} catch (Exception e) {

			e.printStackTrace();
			System.out.println("Error Occurred :" + e.getMessage());// uncomment asneeded --;
		}
		return map;
	}

	@Override
	public Map<String, Object> login(String username, String password, Boolean checkLock, String company_code,
			int attempt, String ipaddr, String otp) throws Exception {
		Map<String, Object> map = new HashMap<>();
		Integer attempts = 0;
		map.put("ALLOW", false);
		attempts = attempt + 1;
		Usermaster user = this.userMasterRepository.getUserByUsername(username);
		
		
		boolean isTempPassword = false;
		if (user == null) {
			this.setLog(username, "Invalid Username", ipaddr, attempts);
			throw new MedicoException("User Not Found.");
		}
		if (user.getPassword_lock().equals(Y)) {
			this.setLog(username, "User Locked", ipaddr, attempts);
			map.put("ISPASSLOCK", true);
			throw new MedicoException("The user is Locked. Please Contact Administrator.");
		}
		int salt = this.userMasterRepository.getSaltvalByUsername(user.getLd_lgnid());

//uncomment for testing
		Company company = this.companyMasterService.getCompanyDetails();
		// if(!company.getWms_is_live().equalsIgnoreCase("Y"))
//		System.out.println("database password:::"
//				+ PasswordManager.decrypt(PasswordManager.decrypt(user.getLd_pass_ang(), salt), salt));
		
		
		System.out.println("database password:::"
				+ PasswordManager.decrypt(user.getLd_pass_ang()));
		
		
		if (!password.equals(PasswordManager.decrypt(PasswordManager.decrypt(user.getLd_pass_ang())))) {

//		if (!password.equals(PasswordManager.decrypt(PasswordManager.decrypt(user.getLd_pass_ang(), salt), salt))) {
			// throw new MedicoException("Incorrect Password.");
//			//System.out.println("attempt:::" + attempt);
//			//System.out.println("username:::" + username);
			// int count =attempt+1;
			// session.setAttribute("Attempt",count);
			if (attempts > user.getLd_max_login_attempts().intValue()) {
				userMasterRepository.insertPasswordLocktime(username);
				this.setLog(username, "Login attempt crossed max limit of "+user.getLd_max_login_attempts(), ipaddr, attempts);
				map.put("ISPASSLOCK", true);
				String subject = "Account Locked";
				String mailContent = mailContentService.mailContentForSendLockNotification(username);

				String adminmail = mailContentService.MailContentForAdminUserUnlock(username);
				List<String> adminmailList = new ArrayList<>();
				// adminmailList.add("samruddha@excelsof.com");
				adminmailList.add("milind.datar@excelsof.com");

				List<String> mailList = new ArrayList<>();
				mailList.add(user.getLd_email());
				// mailList.add("milind.datar@excelsof.com");
				EmailFrom mailconfig = emailservice.getEmailObject("EMAILFROM");
				ExecutorService executorService = Executors.newCachedThreadPool();
				executorService.execute(() -> {
					try {
						SendMail.sendHtmlMail(mailList, subject, mailContent, mailconfig.getEmail(),
								mailconfig.getMail_password(), mailconfig.isAuth(), mailconfig.isTls_sls(),
								mailconfig.getHost(), mailconfig.getPort());
					} catch (Exception e) {
						// System.out.println("Error Occurred :" +e.getMessage());// uncomment
						// asneeded --
						// System.out.println("Error Occurred :" +e.getMessage());// uncomment asneeded
						// --;
					}
				});

				ExecutorService executorService1 = Executors.newCachedThreadPool();
				executorService1.execute(() -> {
					try {
						SendMail.sendHtmlMail(adminmailList, subject, adminmail, mailconfig.getEmail(),
								mailconfig.getMail_password(), mailconfig.isAuth(), mailconfig.isTls_sls(),
								mailconfig.getHost(), mailconfig.getPort());
					} catch (Exception e) {
						// System.out.println("Error Occurred :" +e.getMessage());// uncomment
						// asneeded --
						// System.out.println("Error Occurred :" +e.getMessage());// uncomment asneeded
						// --;
					}
				});

				throw new MedicoException("User Locked due to wrong password. Please Contact Administrator.");
			} else if (attempt < user.getLd_max_login_attempts().intValue()) {
				this.setLog(username, "Login Attempt Failed", ipaddr, attempts);
				throw new MedicoException("Login Attempt " + attempts + "/"+ user.getLd_max_login_attempts() +".Invalid input !! Please try again !!");
			} else {
				this.setLog(username, "Login Attempt Failed", ipaddr, 1);
				throw new MedicoException("Login Attempt " + 1 + "/"+ user.getLd_max_login_attempts() +".Invalid input !! Please try again !!");
			}
		}

		String ind = parameterservice.IsOtp_Captcha_Req(ACCEPT_OTP);
		if (ind.equals("Y")) {
			if (user.getOtp() == null) {
				throw new MedicoException("invalid input");
			} else {
				if (!otp.equals(PasswordManager.decrypt(PasswordManager.decrypt(user.getOtp(), salt), salt))) {
					throw new MedicoException("invalid input");
				}
				user.setOtp(null);
				transactionalRepository.update(user);
			}
		}
		if (user.getPwd_expiry_dt() != null && user.getIs_temp().equals("N")) {
			if (MedicoResources.atStartOfDay(new Date())
					.compareTo(MedicoResources.atStartOfDay(user.getPwd_expiry_dt())) >= 0) {
				throw new MedicoException(
						"Your password has expired. Please contact your account administrator or click on Forgot Password.");
			}
		}

		if (!user.getLd_status().equalsIgnoreCase(A)) {
			this.setLog(username, "User Deactivated", ipaddr, attempts);
			throw new MedicoException("The user is deactivated.");
		}

		if (!(user.getUser_lock().equalsIgnoreCase(N) || !checkLock)) {
			// compare both ips and check if same
			// if same then throw different exception
//			if(user.getLast_accessed_ip()!=null && user.getLast_accessed_ip().equals(ipaddr)) {
//				this.setLog(username,"User Logged in from same Machine",ipaddr,attempts);
//				throw new MedicoException("SAMEIP-This user is logged in previously from same machine but was locked");
//			}
			this.setLog(username, "User Logged in from another Machine", ipaddr, attempts);
			throw new MedicoException("The user is logged in from another machine.");
		}
//		if((user.getPassword_lock_dt().compareTo(new Date())>=0)) {
//			throw new MedicoException("The user is Locked.");
//		}

		if (user.getIs_temp().trim().equalsIgnoreCase(Y)) {
			isTempPassword = true;
		}

		map.put("LoginEmpId", user.getLd_emp_cb_id());

		if (!isTempPassword) {
//			//System.out.println("IsTemp False");
			LoginDetails login_details = userMasterRepository.getLoginDetailsByUserName(username);
			HashMap<String, String> data = userMasterRepository.getUserInfo(username, password);
			Period periodData = periodMasterRepository.getPeriodMasterByCompany(company_code);
			FinYear fin_year = financialYearRepository.getCurrentFinancialYear(company_code); // to get the FinYear

			// getting depo_location id

			Long depo_loc_id = null;
			if (data.get("EMP_LOC") != null) {
				depo_loc_id = locationRepository.getDepo_loc_id_by_loc_id(data.get("EMP_LOC"));
			}

//			//System.out.println("User Role :: " + data.get("USER_ROLE"));
			if (fin_year == null) {
				throw new MedicoException("Financial Year not found.");
			}
			map.put("loggedInUser", username);
			map.put("FNAME", data.get("FNAME"));
			map.put("MNAME", data.get("MNAME"));
			map.put("LNAME", data.get("LNAME"));
			map.put("EMP_ID", data.get("EMP_ID"));
			map.put("USER_ROLE", data.get("USER_ROLE"));
			map.put("DEPTLOC", data.get("DEPTLOC"));
			map.put("DEPO_LOC_ID", depo_loc_id);
			map.put("USER_GROUP_ID", data.get("GROUP_ID"));
			map.put("USER_TYPE", data.get("USER_TYPE"));
			map.put("USER_DIVISION", userMasterRepository.getUserDivision(data.get("EMP_ID")));
			map.put("CURR_PERIOD", periodData.getPeriod_code());
			map.put("CURR_FIN_YEAR", periodData.getPeriod_fin_year());
			map.put("LOGIN_DETAILS_SG", login_details);
			map.put("EMP_EMAIL", login_details.getEmp_email());
			// map.put("USER_LOCATION",master.getUserLocation(data.get("EMP_ID")));
			// map.put("USER_DIV_ARRAY",master.getFormatedUserDivision(data.get("EMP_ID")));
			// map.put("LOGIN_EXPIRY_DAY",new
			// //SystemParameterDao().getSpValueBySpName("Login_Expired_Day").getSp_value());
			map.put("Fin_yr_stdt", fin_year.getFin_start_date());
			// this is current period object
			map.put("period_obj", periodData);
			// newly added for GST
			map.put("gst_ind",
					(fin_year.getGst_ind() != null && fin_year.getGst_ind() != "") ? fin_year.getGst_ind() : "N");
			map.put("gst_curr_year", fin_year.getGst_curr_year());
			map.put("gst_start_date", fin_year.getGst_start_date());
			map.put("vat_transition_end", fin_year.getVat_transition_end());
			map.put("EMP_LOC", data.get("EMP_LOC"));
			map.put("stock_at_cfa_ind", "Y");
			map.put("LD_ID", data.get("LD_ID")); // from company master
			map.put("LD_EXEC_ASST_IND", data.get("LD_EXEC_ASST_IND"));
			
			if (fin_year.getFin_company().trim().equals("PFZ")) {
				map.put("FINANCIAL_YEAR", "Dec-" + (Integer.valueOf(periodData.getPeriod_fin_year()) - 1) + " to "
						+ " Nov-" + periodData.getPeriod_fin_year());
			}
			if (fin_year.getFin_company().trim().equals("SER")) {
				map.put("FINANCIAL_YEAR", "Apr-" + (Integer.valueOf(periodData.getPeriod_fin_year())) + " to " + " Mar-"
						+ (Integer.valueOf(periodData.getPeriod_fin_year()) + 1));
			}
			
			if (fin_year.getFin_company().trim().equals("VET")) {
				map.put("FINANCIAL_YEAR", "Jan-" + (Integer.valueOf(periodData.getPeriod_fin_year())) + " to " + " Dec-"
						+ (Integer.valueOf(periodData.getPeriod_fin_year()) + 1));
			}
			
			
			map.put("FSTAFF_ID", data.get("FSTAFF_ID"));
			map.put("EMP_DESG", data.get("EMP_DESG"));
			// System.out.println("UserId " + user.getLd_emp_cb_id());
			this.lockOrUnlockUser(user.getLd_emp_cb_id(), true);
			// notificationService.getNotifications(username, company_code);
			map.put("ISPASSLOCK", false);
			map.put("ALLOW", true);
			map.put("IS_TEMP_PWD", false);
			// map.put("ISSECRETQSTN", false);
		} else {
			// System.out.println("IsTempTrue");
			if (user.getPwd_expiry_dt() != null) {
				if (MedicoResources.atStartOfDay(new Date())
						.compareTo(MedicoResources.atStartOfDay(user.getPwd_expiry_dt())) >= 0) {
					map.put("ISEXPIRED", true);
				} else {
					map.put("ISEXPIRED", false);
				}
			} else {
				map.put("ISEXPIRED", false);
			}
			map.put("ALLOW", true);
			map.put("IS_TEMP_PWD", true);
			map.put("ISPASSLOCK", false);
			if (user.getSecret_answer() == null && user.getSecret_question() == null) {
				map.put("ISSECRETQSTN", true);
			} else {
				map.put("ISSECRETQSTN", false);
			}

		}
		// update usermaster last access ip if login success
		this.setLastAccessedIp(user, ipaddr);
		return map;
	}

	@Override
	public void lockOrUnlockUser(String userId, boolean lock) throws Exception {
		userMasterRepository.lockOrUnlockUser(userId, lock);
	}

	@Override
	public void unlockAllUsers() throws Exception {
		userMasterRepository.unlockAllUsers();
	}

	@Override
	public List<Usermaster> getLockedUsers(String levelCode) throws Exception {
		return userMasterRepository.getLockedUsers(levelCode);
	}

	@Override
	public void unlockorLockUsersByUsernames(List<String> usernames, String lockUnlock) throws Exception {
		userMasterRepository.unlockorLockUsersByUsernames(usernames, lockUnlock);
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public void changePassword(ChangePasswordBean bean) throws Exception {
		Usermaster user = this.userMasterRepository.getUserByUsername(bean.getUsername());
		if (user == null) {
			throw new MedicoException("User not found.");
		}
		int salt = this.userMasterRepository.getSaltvalByUsername(bean.getUsername());
		// System.out.println("user.getLd_pass()::" + user.getLd_pass_ang());
		// System.out.println("bean.getOldPassword()::" + bean.getOldPassword());
		// //System.out.println("getOldPassword()::"+PasswordManager.decrypt(PasswordManager.decrypt(user.getLd_pass_ang())));
		if (!bean.getOldPassword()
				.equals(PasswordManager.decrypt(PasswordManager.decrypt(user.getLd_pass_ang(), salt), salt))) {
			throw new MedicoException("Incorrect old password.");
		}
		if (!bean.getNewPassword().equals(bean.getConfirmNewPassword())) {
			throw new MedicoException("Passwords do not match.");
		}
		this.userMasterRepository.updatePasswordByUsername(bean.getUsername(), bean.getNewPassword());
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public void sendPassword(Usermaster user, String password) throws Exception {
		int salt = userMasterRepository.getSaltvalByUsername(user.getLd_lgnid());
		String encryptedPassword = PasswordManager.encrypt(PasswordManager.encrypt(password, salt), salt);
		Calendar calendar = Calendar.getInstance();
		// calendar.add(Calendar.DATE, user.getExpiry_days().intValue());
		// Date expiryDate = calendar.getTime();
		// user.setExpiry_date(MedicoResources.atStartOfDay(expiryDate));
		user.setLd_pass_ang(encryptedPassword);
		user.setIs_temp("Y");
		this.transactionalRepository.update(user);
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public String changeTemporaryPassword(String username, String password, String secretqstn, String secretanswr)
			throws Exception {
		Map<String, Object> map = new HashMap<>();
		Usermaster user = this.userMasterRepository.getUserByUsername(username);
		if (user == null) {
			throw new MedicoException("That username does not matches any of our records.");
		}
		int salt = this.userMasterRepository.getSaltvalByUsername(user.getLd_lgnid());
		if (user.getIs_temp().equals("Y") && user.getSecret_question() == null && user.getSecret_answer() == null) {
//		//	String encryptedqstn = PasswordManager.encrypt(PasswordManager.encrypt(secretqstn));
//		//	String encryptedAns = PasswordManager.encrypt(PasswordManager.encrypt(secretanswr)); 
			user.setSecret_answer(secretanswr.toUpperCase());
			user.setSecret_question(secretqstn);
		}

		List<Password_Log> passLog = this.userMasterRepository.getUserPassByempId(user.getLd_emp_cb_id());
		for (int i = 0; i <= passLog.size() - 1; i++) {
			String encryptedPasswordOld = PasswordManager
					.decrypt(PasswordManager.decrypt(passLog.get(i).getId_pass_ang(), salt), salt);

			if (encryptedPasswordOld.equals(password)) {
				map.put(DATA_SAVED, false);
				throw new MedicoException(
						"Old password used in last 12 months cannot be entered again,Please enter some other password");
			}
		}
		// int salt = userMasterRepository.getSaltvalByUsername(user.getLd_lgnid());
		String encryptedPassword = PasswordManager.encrypt(PasswordManager.encrypt(password, salt), salt);
		// Calendar calendar = Calendar.getInstance();
		// Date expiryDate = null;
		// if (MedicoResources.atStartOfDay(new
		// Date()).compareTo(MedicoResources.atStartOfDay(user.getPwd_expiry_dt())) >=
		// 0) {
//			calendar.add(Calendar.DATE, 90);
//			expiryDate = calendar.getTime();
//			user.setPwd_expiry_dt(MedicoResources.atStartOfDay(expiryDate));
//			
//		}

		user.setLd_pass_ang(encryptedPassword);
		user.setLd_pass(new BCryptPasswordEncoder().encode(password));
		user.setIs_temp(N);
		user.setPwd_email_ind(N);

		int adddaysTime = 90;
		Date targetTime = Calendar.getInstance().getTime();
		targetTime = DateUtils.addDays(targetTime, adddaysTime);
		user.setPwd_expiry_dt(targetTime);

		this.transactionalRepository.update(user);

		return "Password change successfull. Your password will expire on "
				+ MedicoResources.convertUtilDateToString_DD_MM_YYYY(targetTime) + ". " + "Please login to continue.";
	}

	@Override
	public Object getObjectByUsername(String username) throws Exception {
		return this.userMasterRepository.getUserByUsername(username);
	}

	@Override
	public Usermaster getUserByUsername(String username) throws Exception {
		return this.userMasterRepository.getUserByUsername(username);
	}

	@Override
	public Usermaster getUserByFsId(Long fsId) throws Exception {
		return userMasterRepository.getUserByFsId(fsId);
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public void fixUserPassword(String companyCode) throws Exception {
//		List<Usermaster> users = this.userMasterRepository.getAllUsers(companyCode);
//		if (users != null) {
//			int count = 1;
//			for (Usermaster user : users) {
//				if (user.getUser_password() != null) {
//					//System.out.println(count);
//					user.setEnc_password(PasswordManager.encrypt(PasswordManager.encrypt(user.getUser_password())));
//					user.setHash_password(new BCryptPasswordEncoder().encode(user.getUser_password()));
//					Calendar calendar = Calendar.getInstance();
//					if (user.getExpiry_days() != null && user.getExpiry_days().compareTo(0L) != 0) {
//						calendar.add(Calendar.DATE, user.getExpiry_days().intValue());
//					} else {
//						calendar.add(Calendar.DATE, 90);
//					}
//					user.setExpiry_date(MedicoResources.atStartOfDay(calendar.getTime()));
//					this.transactionalRepository.update(user);
//					count++;
//				}
//			}
//		}
	}

	@Override
	public String getAllowBatchCreateInd(String empId) throws Exception {
		return this.userMasterRepository.getAllowBatchCreateInd(empId);
	}

	@Override
	public List<Parameter> getDivAccessedByUser(String userId) throws Exception {
		return this.userMasterRepository.getDivAccessedByUser(userId);
	}

	@Override
	public List<MailBean> getEmpDetailForMail(String userId) throws Exception {
		// TODO Auto-generated method stub
		return this.userMasterRepository.getEmpDetailForMail(userId);
	}

	@Override
	public List<UserMasterBean> getLockUser() throws Exception {
		// TODO Auto-generated method stub
		return this.userMasterRepository.getLockUser();
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public void unlockUserOnRequest(List<String> ids, boolean lock) throws Exception {
		this.userMasterRepository.unlockUserOnRequest(ids, lock);
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public Map<String, Object> saveUserMaster(UserBean bean) throws Exception {
		Map<String, Object> map = new HashMap<>();
		try {
			String curr_user_id = bean.getEmpId();
			String comp_cd = bean.getCompanyCode();
			String ip_addr = bean.getIpAddress();
			// //System.out.println("fName::"+first_name+"::mName"+middle_name+"::lName::"+last_name);
			Date today = new Date();

			// insert hr_m_employee table
			String emp_id = this.userMasterRepository.saveOrModifyHr_M_Employee("", bean.getEgrp_id(),
					bean.getDept_id(), Long.valueOf(bean.getLoc_id()), comp_cd, bean.getDesg_id(), bean.getMgr_id(),
					bean.getFirst_name(), bean.getMiddle_name(), bean.getLast_name(), bean.getGender(),
					bean.getBirth_date(), bean.getLogin_valid_from(), bean.getLogin_valid_to(), bean.getJoin_date(),
					bean.getResign_date(), BigDecimal.ZERO, bean.getNarration(), bean.getEmp_addr(),
					bean.getEmp_phone(), bean.getEmp_mob(), bean.getEmp_email(), "N", bean.getEmg_cont_pers(),
					bean.getEmg_cont_phon(), curr_user_id, bean.getIpAddress(), "A", "E", bean.getFs_id(),
					bean.getRm_id(), bean.getDm_id(), bean.getSm_id());

			// insert am_m_login_detail table

			// String encrypt_password =
			// PasswordManager.encrypt(PasswordManager.encrypt(bean.getPassword(),salt),salt);
			// String userLock = login_check ? "Y" : "N";

			String password = RandomString.make();

			if (bean.getNo_of_attempt_flg().equalsIgnoreCase("N")) {
				bean.setMax_login_attempts("0");
				bean.setHours_locked("0");
			}
			String Desig = userDepartmentRepository.getDesignationById(bean.getEgrp_id());
			// System.out.println("DESG : " + Desig);
			this.saveOrModifyAm_m_login_detail(bean.getLogin_email(), password, bean.getUser_name(), "N", curr_user_id,
					emp_id, "N", "N", bean.getUserType(), "E", ip_addr, "A", bean.getNo_of_attempt_flg(), "0",
					bean.getAllow_batch_create(), Long.valueOf(bean.getMax_login_attempts()),
					Long.valueOf(bean.getHours_locked()), bean.getAssistInd(), "E");

			// insert am_m_EmpProdType_access table
			if (bean.getProductType() != null && bean.getProductType().size() > 0) {
				for (int i = 0; i < bean.getProductType().size(); i++) {
					// //System.out.println("userProdType_name::"+userProdType_name.get(i));
					// //System.out.println("userProdType_id::"+userProdType_id.get(i));
					this.userDepartmentRepository.saveProductType(emp_id, bean.getProductType().get(i),
							Long.valueOf(bean.getProductType().get(i)), curr_user_id, today, ip_addr);

				}
			}

			// insert am_m_emp_div_access table
			if (bean.getDivId() != null && bean.getDivId().size() > 0) {
				for (int i = 0; i < bean.getDivId().size(); i++) {
					// //System.out.println("div_access_id::"+div_access_id.get(i));
					if (!bean.getDivId().equals("0")) {
						this.userDepartmentRepository.insertEmpDivAccess(emp_id, Long.valueOf(bean.getDivId().get(i)),
								curr_user_id, today, ip_addr);
					}
				}
			}

			// insert am_m_emp_div_report_access table
			if (bean.getReportDivision() != null && bean.getReportDivision().size() > 0) {
				for (int i = 0; i < bean.getReportDivision().size(); i++) {
					// //System.out.println("div_report_access_id::"+div_report_access_id.get(i));
					if (!bean.getReportDivision().equals("0")) {
						this.userDepartmentRepository.insertEmpDivReportAccess(emp_id,
								Long.valueOf(bean.getReportDivision().get(i)), curr_user_id, today, ip_addr);
					}
				}
			}

			// insert location table
			if (bean.getLocations() != null && bean.getLocations().size() > 0) {
				for (int i = 0; i < bean.getLocations().size(); i++) {
					// //System.out.println("loc_access_id::"+loc_access_id.get(i));
					if (!bean.getLocations().equals("0")) {
						this.userDepartmentRepository.insertEmpLocAccess(emp_id,
								Long.valueOf(bean.getLocations().get(i)), curr_user_id, today, ip_addr);
					}
				}
			}
			map.put("message", "User Master Saved Successfully.");
			map.put("dataSaved", true);

			SendMail sm = new SendMail();
			String body = sm.SendUserCreationMail(bean.getFirst_name(), bean.getUser_name(), password, Desig,
					bean.getEmp_email());
			List<String> mailList = new ArrayList<>();
			mailList.add(bean.getEmp_email());

			EmailFrom mailconfig = emailService.getEmailObject("EMAILFROM");
			ExecutorService executorService = Executors.newCachedThreadPool();
			executorService.execute(() -> {
				try {
					SendMail.sendHtmlMail(mailList, "New User created in Medico SG", body,
							// user.getLd_email(),PasswordManager.decrypt(PasswordManager.decrypt(user.getLd_pass_ang())),
							mailconfig.getEmail(), mailconfig.getMail_password(), mailconfig.isAuth(),
							mailconfig.isTls_sls(), mailconfig.getHost(), mailconfig.getPort());
				} catch (Exception e) {
					// System.out.println("Error Occurred :" +e.getMessage());// uncomment asneeded
					// --
					// System.out.println("Error Occurred :" +e.getMessage());// uncomment asneeded
					// --;
				}
			});

		} catch (Exception p) {
			p.printStackTrace();
			// System.out.println("Error Occurred :" + new
			// CodifyErrors().getErrorCode(p));// uncomment asneeded --
			// System.out.println("Error Occurred :" +e.getMessage());// uncomment asneeded
			// --;
			map.put("message", "Unable to save record, some error occured.");
		}

		return map;
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public void saveOrModifyAm_m_login_detail(String ld_email, String ld_pass, String ld_lgnid, String is_temp,
			String curr_user_id, String ld_emp_cb_id, String password_lock, String user_lock, String user_type,
			String ld_usr_typ_flg, String ip_add, String ld_status, String ld_no_of_attempt_flg, String DPTLOC_ID,
			String allow_batch_create, Long ld_max_login_attempts, Long ld_hours_locked, String assistInd,
			String save_ind) throws Exception {
		try {
			if (save_ind.equalsIgnoreCase("E")) {
				Usermaster userMaster = new Usermaster();
				userMaster.setLd_email(ld_email);
				userMaster.setLd_lgnid(ld_lgnid);
				userMaster.setIs_temp(is_temp);
				userMaster.setLd_emp_cb_id(ld_emp_cb_id);// emp_id
				userMaster.setLd_ins_usr_id((curr_user_id));
				userMaster.setLd_ins_dt(new Date());
				userMaster.setLd_ins_ip_add(ip_add);
				userMaster.setLd_status("A");
				userMaster.setPassword_lock("N");
				// userMaster.setPassword_lock_dt("");
				userMaster.setUser_lock(user_lock);
				userMaster.setPass_mod_dt(new Date());
				userMaster.setUser_type(user_type);
				userMaster.setLd_usr_typ_flg(ld_usr_typ_flg); // what data to enter
				userMaster.setLd_no_of_attempt_flg(ld_no_of_attempt_flg);
				userMaster.setDptloc_id((DPTLOC_ID));
				userMaster.setAllow_batch_create(allow_batch_create);
				userMaster.setLd_max_login_attempts(ld_max_login_attempts);
				userMaster.setLd_hours_locked(ld_hours_locked);
				userMaster.setLd_exec_asst_ind(assistInd);

				SimpleDateFormat sdf = new SimpleDateFormat("dd");
				int salt = Integer.parseInt(sdf.format(new Date()));
				// System.out.println("salt : " + salt);
				String encrypt_password = PasswordManager.encrypt(PasswordManager.encrypt(ld_pass, salt), salt);
				userMaster.setLd_pass_ang(encrypt_password);
				userMaster.setLd_pass(new BCryptPasswordEncoder().encode(ld_pass));
				userMaster.setIs_temp("Y");
				userMaster.setPwd_email_ind("N");
				userMaster.setOtp_max_attempt(3);

				int adddaysTime = 90;
				Date targetTime = Calendar.getInstance().getTime();
				targetTime = DateUtils.addDays(targetTime, adddaysTime);
				userMaster.setPwd_expiry_dt(targetTime);

				this.transactionalRepository.persist(userMaster);

			} else {
				// UserMaster umRef = this.getAmLoginPK(hsession, ld_emp_cb_id);
				Usermaster userMaster = this.userMasterRepository.getUserByEmpId(ld_emp_cb_id);
				// System.out.println("ld_emp_cb_id " + ld_emp_cb_id);
				userMaster.setLd_email(ld_email);

//				if (!userMaster.getLd_pass().equalsIgnoreCase(ld_pass)) {
//					userMaster.setPass_mod_dt(new Date());
//					userMaster.setLd_pass(ld_pass);
//					userMaster.setLd_pass_ang(ld_pass);
//				}
				userMaster.setLd_mod_usr_id((curr_user_id));
				userMaster.setLd_mod_dt(new Date());
				userMaster.setLd_mod_ip_add(ip_add);
				userMaster.setUser_lock(user_lock);
				userMaster.setUser_type(user_type);
				// userMaster.setLd_usr_typ_flg(ld_usr_typ_flg); //what data to enter
				userMaster.setLd_no_of_attempt_flg(ld_no_of_attempt_flg);
				// userMaster.setDPTLOC_ID(DPTLOC_ID);
				userMaster.setAllow_batch_create(allow_batch_create);
				userMaster.setLd_max_login_attempts(ld_max_login_attempts);
				userMaster.setLd_hours_locked(ld_hours_locked);
				userMaster.setLd_exec_asst_ind(assistInd);
				this.transactionalRepository.update(userMaster);
			}
		} catch (Exception e) {
			throw e;
		}
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public Map<String, Object> updateUserMaster(UserBean bean) {
		Map<String, Object> map = new HashMap<>();
		try {
			String curr_user_id = bean.getEmpId();
			String comp_cd = bean.getCompanyCode();
			String ip_addr = bean.getIpAddress();
			// //System.out.println("fName::"+first_name+"::mName"+middle_name+"::lName::"+last_name);
			Date today = new Date();

			// insert hr_m_employee table
			String emp_id = this.userMasterRepository.saveOrModifyHr_M_Employee(bean.getMod_empId(), bean.getEgrp_id(),
					bean.getDept_id(), Long.valueOf(bean.getLoc_id()), comp_cd, bean.getDesg_id(), bean.getMgr_id(),
					bean.getFirst_name(), bean.getMiddle_name(), bean.getLast_name(), bean.getGender(),
					bean.getBirth_date(), bean.getLogin_valid_from(), bean.getLogin_valid_to(), bean.getJoin_date(),
					bean.getResign_date(), BigDecimal.ZERO, bean.getNarration(), bean.getEmp_addr(),
					bean.getEmp_phone(), bean.getEmp_mob(), bean.getEmp_email(), "N", bean.getEmg_cont_pers(),
					bean.getEmg_cont_phon(), curr_user_id, bean.getIpAddress(), "A", "M", bean.getFs_id(),
					bean.getRm_id(), bean.getDm_id(), bean.getSm_id());

			// insert am_m_login_detail table
			// int salt = userMasterRepository.getSaltvalByUsername(bean.getUser_name());
			// String encrypt_password =
			// PasswordManager.encrypt(PasswordManager.encrypt(bean.getPassword(), salt),
			// salt);
			// String userLock = login_check ? "Y" : "N";
			// if (bean.getNo_of_attempt_flg().equalsIgnoreCase("N")) {
			// bean.setMax_login_attempts("0");
			// bean.setHours_locked("0");
			// }

			this.saveOrModifyAm_m_login_detail(bean.getLogin_email(), "", bean.getUser_name(), "N", curr_user_id,
					emp_id, "N", "N", bean.getUserType(), "E", ip_addr, "A", bean.getNo_of_attempt_flg(), "0",
					bean.getAllow_batch_create(), Long.valueOf(bean.getMax_login_attempts()),
					Long.valueOf(bean.getHours_locked()), bean.getAssistInd(), "M");

			// delete all recordds
			this.userDepartmentRepository.deleteProductType(emp_id, null);
			this.userDepartmentRepository.deleteEmpLocAccess(emp_id, null);
			this.userDepartmentRepository.deleteEmpDivAccess(emp_id, null);
			this.userDepartmentRepository.deleteEmpDivReportAccess(emp_id, null);

			// insert am_m_EmpProdType_access table
			if (bean.getProductType() != null && bean.getProductType().size() > 0) {
				for (int i = 0; i < bean.getProductType().size(); i++) {
					this.userDepartmentRepository.saveProductType(emp_id, bean.getProductType().get(i),
							Long.valueOf(bean.getProductType().get(i)), curr_user_id, today, ip_addr);

				}
			}

			// insert am_m_emp_div_access table
			if (bean.getDivId() != null && bean.getDivId().size() > 0) {
				for (int i = 0; i < bean.getDivId().size(); i++) {
					// //System.out.println("div_access_id::"+div_access_id.get(i));
					if (!bean.getDivId().equals("0")) {
						this.userDepartmentRepository.insertEmpDivAccess(emp_id, Long.valueOf(bean.getDivId().get(i)),
								curr_user_id, today, ip_addr);
					}
				}
			}

			// insert am_m_emp_div_report_access table
			if (bean.getReportDivision() != null && bean.getReportDivision().size() > 0) {
				for (int i = 0; i < bean.getReportDivision().size(); i++) {
					// //System.out.println("div_report_access_id::"+div_report_access_id.get(i));
					if (!bean.getReportDivision().equals("0")) {
						this.userDepartmentRepository.insertEmpDivReportAccess(emp_id,
								Long.valueOf(bean.getReportDivision().get(i)), curr_user_id, today, ip_addr);
					}
				}
			}

			// insert location table
			if (bean.getLocations() != null && bean.getLocations().size() > 0) {
				for (int i = 0; i < bean.getLocations().size(); i++) {
					// //System.out.println("loc_access_id::"+loc_access_id.get(i));
					if (!bean.getLocations().equals("0")) {
						this.userDepartmentRepository.insertEmpLocAccess(emp_id,
								Long.valueOf(bean.getLocations().get(i)), curr_user_id, today, ip_addr);
					}
				}
			}
			map.put("message", "User Master Updated Successfully.");
			map.put("dataSaved", true);

		} catch (Exception p) {
			// System.out.println("Error Occurred :" + new
			// CodifyErrors().getErrorCode(p));// uncomment asneeded --
			// System.out.println("Error Occurred :" +e.getMessage());// uncomment asneeded
			// --;
			map.put("message", "Unable to save record, some error occured.");
		}

		return map;
	}

	@Override
	public EmployeeDetails getEmployeeDetails(String empId) throws Exception {
		// TODO Auto-generated method stub
		return this.userMasterRepository.getEmployeeDetails(empId);
	}

	@Override
	public Usermaster getUserByEmpId(String empId) throws Exception {
		// TODO Auto-generated method stub
		return this.userMasterRepository.getUserByEmpId(empId);
	}

	@Override
	public List<UserMasterBean> getActiveUserList() throws Exception {

		return this.userMasterRepository.getActiveUserList();
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public Map<String, Object> saveUserBrandMapping(UserBrandMapopingBean bean) throws Exception {
		Map<String, Object> map = new HashMap<>();
		UserBrandMap mapping = null;
		Usermaster user = this.userMasterRepository.getUserByEmpId(bean.getUserEmpId());
		Long userId = user.getLd_id();
		this.userDepartmentRepository.deleteEmpBrandAccess(userId);
		try {
			for (int i = 0; i < bean.getBrandId().size(); i++) {
				if (!bean.getBrandId().equals("0")) {
					mapping = new UserBrandMap();
					mapping.setCompany_code(bean.getCompanyCode());
					mapping.setSmp_prod_id(0L);
					mapping.setSmp_sa_prod_group(Long.valueOf(bean.getBrandId().get(i)));
					mapping.setUser_ins_date(new Date());
					mapping.setUser_ins_ip_add(bean.getIpAddress());
					mapping.setUser_mod_date(new Date());
					mapping.setUserid(userId);
					mapping.setUser_mod_ip_add(bean.getIpAddress());
					mapping.setUser_ins_usr_id(bean.getEmpId());
					mapping.setUser_status("A");
					this.transactionalRepository.persist(mapping);
				}
			}
			map.put("message", "User Brand Mapping Saved Successfully.");
			map.put("dataSaved", true);

		} catch (Exception p) {
			// System.out.println("Error Occurred :" + new
			// CodifyErrors().getErrorCode(p));// uncomment asneeded --
			// System.out.println("Error Occurred :" +e.getMessage());// uncomment asneeded
			// --;
			map.put("message", "Unable to save record, some error occured.");
		}

		return map;
	}

	@Override
	public void insertPasswordLocktime(String username) throws Exception {
		// TODO Auto-generated method stub
		userMasterRepository.insertPasswordLocktime(username);
	}

	@Override
	public void setLog(String username, String status, String remoteAddr, int count) throws Exception {
		UsermasterLog log = new UsermasterLog();
		log.setLogin_id(username);
		log.setLogin_status(status);
		log.setLogin_attempts(Long.valueOf(count).toString());
		log.setMac_address("0.0.0.0");
		log.setIp_address(remoteAddr);
		log.setAttempt_date(new Date());
		this.transactionalRepository.persist(log);

	}

	@Override
	public Integer getLatestAttempts(String username) throws Exception {
		// TODO Auto-generated method stub
		return userMasterRepository.getLatestAttempts(username);
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public void savePasswordLogs(Password_Log passlog, Usermaster user) throws Exception {
		passlog = new Password_Log();
		passlog.setEmp_id(user.getLd_emp_cb_id());
		passlog.setId_pass_ang(user.getLd_pass_ang());
		passlog.setPassword_change_dt(new Date());
		this.transactionalRepository.persist(passlog);
	}

	@Override
	public List<SG_d_parameters_details> getPasswordRecoveryQuestions(String logquest) throws Exception {
		return userMasterRepository.getPasswordRecoveryQuestions(logquest);
	}

	@Override
	public List<Usermaster> getUsernameData() throws Exception {
		return this.userMasterRepository.getUsernameData();
	}

	@Override
	public void updateUsernameData(String ld_lgnid) throws Exception {
		this.userMasterRepository.updateUsernameData(ld_lgnid);

	}

	@Override
	public void updateUsernameDatatoN(String ld_lgnid) throws Exception {
		this.userMasterRepository.updateUsernameDatatoN(ld_lgnid);

	}

	@Override
	public void getSendEmailNotification() throws Exception {
		Map<String, Object> map = new HashMap<>();
		map.put(DATA_SAVED, true);
		try {

			List<Usermaster> filtereduserList = new ArrayList<>();
			List<Usermaster> userList = userMasterRepository.getUsernameData();
			LocalDate local1 = LocalDate.now(); // current date
			// splitting date
			if (userList != null && userList.size() > 0) {
				for (int dt = 0; dt < userList.size(); dt++) {
					String date = userList.get(dt).getPwd_expiry_dt().toString();
					String[] dateParts = date.split("-");
					String year = dateParts[0];
					String month = dateParts[1];
					String day = dateParts[2];
					// //System.out.println("year"+year+":::"+"month"+month+":::"+"day"+day);
					LocalDate expDate = LocalDate.of(Integer.parseInt(year), Integer.parseInt(month),
							Integer.parseInt(day));
					expDate = expDate.minusDays(6);

					if (local1.compareTo(expDate) >= 0) {
						filtereduserList.add(userList.get(dt));
					}
				}
			}
			// System.out.println("Filtered List : " + filtereduserList.size());
			if (filtereduserList.size() > 0) {
				for (int i = 0; i < filtereduserList.size(); i++) {
					// send mail
					try {
						String subject = "Reminder -Password will expire soon";
						String mailContent = mailContentService.mailContentForSendNewPasswordNotification(
								userList.get(i).getLd_lgnid(),
								MedicoResources.convertUtilDateToString_DD_MM_YYYY(userList.get(i).getPwd_expiry_dt()));
						List<String> mailList = new ArrayList<>();
						// mailList.add("shivram@excelsof.com");
						// mailList.add("samruddha@excelsof.com");

						mailList.add(filtereduserList.get(i).getLd_email());
						// mailList.add("milind.datar@excelsof.com");
						EmailFrom mailconfig = emailservice.getEmailObject("EMAILFROM");
						ExecutorService executorService = Executors.newCachedThreadPool();
						executorService.execute(() -> {
							try {
								SendMail.sendHtmlMail(mailList, subject, mailContent, mailconfig.getEmail(),
										mailconfig.getMail_password(), mailconfig.isAuth(), mailconfig.isTls_sls(),
										mailconfig.getHost(), mailconfig.getPort());
							} catch (Exception e) {
								// System.out.println("Error Occurred :" +e.getMessage());// uncomment
								// asneeded
								// --
								// System.out.println("Error Occurred :" +e.getMessage());// uncomment asneeded
								// --;
							}

						});

					} catch (Exception e) {
						throw e;
					}
					// update am_m_login-detail table for indicator
					userMasterRepository.updateUsernameData(userList.get(i).getLd_lgnid());
				}
			}

		} catch (MedicoException e) {

			map.put(RESPONSE_MESSAGE, e.getMessage());
			throw e;
		} catch (Exception e) {

			map.put(RESPONSE_MESSAGE, "Error Occurred !! Error Code : " + e.getMessage());
			throw e;
		}

	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public void setLastAccessedIp(Usermaster user, String ipaddr) throws Exception {
		user.setLast_accessed_ip(ipaddr);
		this.transactionalRepository.update(user);
	}

	@Override
	public Boolean unlockUserIfSameIp(String username) throws Exception {
		Boolean isUnlocked = false;
		Usermaster user = null;
		try {
			user = this.userMasterRepository.getUserByUsername(username);
			if (user != null) {
				this.userMasterRepository.LockOrUnlockAmLoginDtl(user.getLd_id().toString(), false);
				isUnlocked = true;
			}
		} catch (Exception e) {
			// System.out.println("Error Occurred :" +e.getMessage());// uncomment asneeded
			// --
			// System.out.println("Error Occurred :" +e.getMessage());// uncomment asneeded
			// --;
		}
		return isUnlocked;
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public void generateotp(Usermaster user, String password) throws Exception {
		int salt = userMasterRepository.getSaltvalByUsername(user.getLd_lgnid());
		String encryptedPassword = PasswordManager.encrypt(PasswordManager.encrypt(password, salt), salt);
		// Calendar calendar = Calendar.getInstance();
		user.setOtp(encryptedPassword);
		this.transactionalRepository.update(user);
	}

	@Override
	public void EraseOtp(String username) throws Exception {
		// TODO Auto-generated method stub
		userMasterRepository.EraseOtp(username);

	}

	@Override
	public String generatenewEncryption(String username) throws Exception {
		// TODO Auto-generated method stub
		String newDecrypt = null;
		try {
			// userpass decrypt
			//Usermaster user = this.userMasterRepository.getUserByUsername(username);
			// Usermaster user = this.userMasterRepository.getUserByEmpId(username);
			//int salt = userMasterRepository.getSaltvalByUsername(user.getLd_lgnid());
			// newDecrypt =
			// PasswordManager.decrypt(PasswordManager.decrypt(user.getLd_pass_ang(), salt),
			// salt);
			// System.out.println("Newde pass : " + newDecrypt);

//			String demo_password = "Abc@1234";
//			String newEncrypt = PasswordManager.encrypt(PasswordManager.encrypt(demo_password, salt), salt);
//			System.out.println("newEncrypt:::" + newEncrypt);
//			String oldDecrypt = PasswordManager.decrypt(PasswordManager.decrypt(newEncrypt, salt), salt);
//			System.out.println("oldDecrypt:::" + oldDecrypt);
//			user.setLd_pass_ang(newEncrypt);
//			transactionalRepository.update(user);

//			// Update Password encryption
			List<Usermaster>list = userMasterRepository.getAllUsers("MDL");
			for(Usermaster user :list) {
			String oldDecrypt = "Abc@1234";
					//PasswordManager.decrypt(PasswordManager.decrypt(user.getLd_pass_ang()));
			//System.out.println("pass : "+oldDecrypt);
			int salt = userMasterRepository.getSaltvalByUsername(user.getLd_lgnid());
			String newEncrypt =  PasswordManager.encrypt(PasswordManager.encrypt(oldDecrypt,salt),salt);
			newDecrypt = PasswordManager.decrypt(PasswordManager.decrypt(newEncrypt, salt),salt);
			//System.out.println("Newde pass : "+newDecrypt);
			user.setLd_pass_ang(newEncrypt);
			transactionalRepository.update(user);
			}

			// ChangeLog Encryption
//			List<Password_Log> passLog= this.userMasterRepository.getUserPass(); 
//			for(Password_Log log :passLog) {
//				Usermaster user = userMasterRepository.getUserByEmpId(log.getEmp_id());
//				int salt = userMasterRepository.getSaltvalByUsername(user.getLd_lgnid());
//				String oldDecrypt = PasswordManager.decrypt(PasswordManager.decrypt(log.getId_pass_ang()));
//				//System.out.println("pass : "+oldDecrypt);
//				String newEncrypt =  PasswordManager.encrypt(PasswordManager.encrypt(oldDecrypt,salt),salt);
//				newDecrypt = PasswordManager.decrypt(PasswordManager.decrypt(newEncrypt, salt),salt);
//				//System.out.println("Newde pass : "+newDecrypt);
//				
//				//update
//				log.setId_pass_ang(newEncrypt);
//				transactionalRepository.update(log);
//			}
		} catch (Exception e) {
			e.printStackTrace();
			// TODO: handle exception
			throw e;
		}
		return newDecrypt;
	}

	@Override
	public int getSaltvalByUsername(String username) throws Exception {
		// TODO Auto-generated method stub
		return userMasterRepository.getSaltvalByUsername(username);
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public void setlockotpsend(String username) throws Exception {
		Usermaster user = this.userMasterService.getUserByUsername(username);
		user.setOtp_date_time(new Date());
		this.transactionalRepository.update(user);
	}

	public PdfWriter generatePDFlock(String empid, PdfWriter writer)
			throws FileNotFoundException, Exception, DocumentException {
		String newDecrypt;
		try {
			Usermaster ind = userMasterRepository.getpdfandexcellockind(empid);
			System.out.println("pdf :: " + ind.getPdf_lock_ind() + "  excel ::" + ind.getExcel_lock_ind());
			if (ind.getPdf_lock_ind() != null && ind.getPdf_lock_ind().equals('Y')) {
				Usermaster user = this.userMasterRepository.getUserByEmpId(empid);
				int salt = userMasterRepository.getSaltvalByUsername(user.getLd_lgnid());
				newDecrypt = PasswordManager.decrypt(PasswordManager.decrypt(user.getLd_pass_ang(), salt), salt);
				writer.setEncryption("excel@123".getBytes(), newDecrypt.getBytes(), PdfWriter.ALLOW_PRINTING,
						PdfWriter.ENCRYPTION_AES_128);
				writer.createXmpMetadata();
			}
		} catch (Exception e) {
			System.out.println("Error Occurred :" + e.getMessage());// uncomment asneeded --;
			throw e;
		}
		return writer;
	}

	@Override
	public String generateExcellock(String filepath, String filename, String empId, String ext) throws Exception {
		try {

			Usermaster ind = userMasterRepository.getpdfandexcellockind(empId);
			System.out.println(
					"pdf :: " + ind.getPdf_lock_ind() + "  excel ::" + ind.getExcel_lock_ind() + " ext:: " + ext);
			if (ind.getExcel_lock_ind() != null && ind.getExcel_lock_ind().equals('Y')) {
				File xlsxfile = new File(filepath);

				File zipfile = new File(MedicoConstants.REPORT_FILE_PATH + filename + ".zip");
				// This is name and path of zip file to be created
				ZipFile zipFile = new ZipFile(zipfile);

				ZipParameters parameters = new ZipParameters();
				parameters.setCompressionMethod(Zip4jConstants.COMP_DEFLATE); // set compression method to deflate
																				// compression

				// DEFLATE_LEVEL_FASTEST - Lowest compression level but higher speed of
				// compression
				// DEFLATE_LEVEL_FAST - Low compression level but higher speed of compression
				// DEFLATE_LEVEL_NORMAL - Optimal balance between compression level/speed
				// DEFLATE_LEVEL_MAXIMUM - High compression level with a compromise of speed
				// DEFLATE_LEVEL_ULTRA - Highest compression level but low speed
				parameters.setCompressionLevel(Zip4jConstants.DEFLATE_LEVEL_NORMAL);

				// Set the encryption flag to true
				parameters.setEncryptFiles(true);

				// Set the encryption method to AES Zip Encryption
				parameters.setEncryptionMethod(Zip4jConstants.ENC_METHOD_AES);

				// AES_STRENGTH_128 - For both encryption and decryption
				// AES_STRENGTH_192 - For decryption only
				// AES_STRENGTH_256 - For both encryption and decryption
				// Key strength 192 cannot be used for encryption. But if a zip file already has
				// a
				// file encrypted with key strength of 192, then Zip4j can decrypt this file
				parameters.setAesKeyStrength(Zip4jConstants.AES_STRENGTH_256);

				// Set password
				Usermaster user = userMasterRepository.getUserByEmpId(empId);
				int salt = userMasterRepository.getSaltvalByUsername(user.getLd_lgnid());
				parameters.setPassword(
						PasswordManager.decrypt(PasswordManager.decrypt(user.getLd_pass_ang(), salt), salt));

				ArrayList<File> filesToAdd = new ArrayList<File>();
				filesToAdd.add(xlsxfile);
				// Now add files to the zip file
				zipFile.addFiles(filesToAdd, parameters);

			} else {
				return filename;
			}
		} catch (Exception e) {
			throw e;
		}
		System.out.println("zip");
		return filename + ".zip";
	}

	@Override
	@Cacheable(value="EmpIdByUnameCache",key="#username")
	public String getEmpIdByUsername(String username) throws Exception {
		return this.userMasterRepository.getEmpIdByUsername(username);
	}

	@Override
	public String getEmpIdByEmail(String email) throws Exception {
		return this.userMasterRepository.getEmpIdByEmail(email);
	}

	@Override
	public String getEmployeeIdFromRequest(HttpServletRequest request) throws Exception {
		String uname = this.tokenProvider.getUsernameFromRequest(request);
		if (uname == null)
			throw new AuthenticationCredentialsNotFoundException("", null);
		String empId = this.getEmpIdByUsername(uname);
		if (empId == null)
			throw new AuthenticationCredentialsNotFoundException("", null);
		return empId;
	}

	@Override
	public LoginDetails getLoginDetailsByUserName(String userName) throws Exception {
		return this.userMasterRepository.getLoginDetailsByUserName(userName);
	}
}
