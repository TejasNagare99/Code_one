package com.excel.restcontroller;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.excel.bean.ChangePasswordBean;
import com.excel.bean.RestEmpIdBean;
import com.excel.configuration.JwtProvider;
import com.excel.exception.MedicoException;
import com.excel.model.Company;
import com.excel.model.EmailFrom;
import com.excel.model.Password_Log;
import com.excel.model.SG_d_parameters_details;
import com.excel.model.Usermaster;
import com.excel.model.UsermasterLog;
import com.excel.repository.UserMasterRepository;
import com.excel.security.PasswordManager;
import com.excel.service.CaptchaService;
import com.excel.service.EmailService;
import com.excel.service.FieldStaffService;
import com.excel.service.MailContentService;
import com.excel.service.MenuService;
import com.excel.service.ParameterService;
import com.excel.service.UserMasterService;
import com.excel.utility.AuthenticationRoleConstants;
import com.excel.utility.CodifyErrors;
import com.excel.utility.MedicoConstants;
import com.excel.utility.SendMail;
import com.itextpdf.xmp.impl.Base64;

import cn.apiclub.captcha.Captcha;
import net.bytebuddy.utility.RandomString;

@RestController
@RequestMapping("/rest")
public class LoginController implements MedicoConstants {

	@Autowired
	AuthenticationManager authenticationManager;
	@Autowired
	JwtProvider jwtProvider;
	@Autowired
	private UserMasterService userMasterService;
	@Autowired
	private MenuService menuService;
	@Autowired
	private MailContentService mailContentService;
	@Autowired
	private UserMasterRepository userMasterRepository;
	@Autowired
	private EmailService emailService;
	@Autowired
	private ParameterService parameterservice;
	@Autowired
	private CaptchaService captchaService;
	@Autowired
	private FieldStaffService fstaff_service;

	@PostMapping("/authenticate")
	public Map<String, Object> getUserAuthenticate(@RequestParam("username") String username,
			@RequestParam("password") String password,
			@RequestParam(value = "checkLock", required = false) Boolean checkLock,
			@RequestParam(value = "securityanswer", required = false) String secans,
			@RequestParam(value = "attempt") int attempt, HttpSession session, HttpServletRequest request) {
		Map<String, Object> map = null;
		if (checkLock == null) {
			checkLock = true;
		}
		if (secans == null) {
			secans = "0";
		}
		try {
			String company_cd = (String) session.getServletContext().getAttribute(COMPANY_CODE);
			map = userMasterService.authenticateUser(username, password, checkLock, company_cd, request.getRemoteAddr(),
					secans, attempt);

		} catch (Exception e) {
			System.out.println("Error Occurred :" + e.getMessage());// uncomment asneeded -- //

		}
		return map;
	}

	@PostMapping("/sign-in")
	public Map<String, Object> getUserLogin(@RequestParam("username") String username,
			@RequestParam("password") String password,
			@RequestParam(value = "checkLock", required = false) Boolean checkLock,
			@RequestParam(value = "attempt") String attempt, @RequestParam(value = "otp") String otp,
			HttpSession session, HttpServletRequest request) {
		Map<String, Object> map = null;
		try {
			System.out.println("SIGN ATTEMPT : " + attempt);
			if (attempt.equals("undefined")) {
				attempt = "0";
			}
			if (checkLock == null) {
				checkLock = true;
			}
			
			
			String company_cd = (String) session.getServletContext().getAttribute(COMPANY_CODE);
			map = userMasterService.login(username, password, checkLock, company_cd, Integer.parseInt(attempt),
					request.getRemoteAddr(), otp);

			System.out.println("USER_MAP:::" + map);
			session.setAttribute("LoginEmpId", map.get("LoginEmpId"));

			if ((boolean) map.get("ALLOW")) {
				boolean isTempPassword = (boolean) map.get("IS_TEMP_PWD");
				if (!isTempPassword) {
//					System.err.println("isTempPassword::");
//					Authentication authentication = this.authenticationManager.authenticate(
//							new UsernamePasswordAuthenticationToken(username, password)
//							);
//					
//					System.err.println("Authentication::"+authentication);
//					
//					SecurityContextHolder.getContext().setAuthentication(authentication);
//					
//					String jwt = this.jwtProvider.generateJwtToken(authentication);
					userMasterService.setLog(username, "Successful Login", request.getRemoteAddr(), 0);

					map.put("accessToken", this.jwtProvider.generateJwtToken(map.get("loggedInUser").toString()));
					Company cmpny = (Company) session.getServletContext().getAttribute(COMPANY_DATA);
					map.put("company", cmpny);
					map.put("CFA_TO_STK_REQ_DIV", 33L);

				} else {
					userMasterService.setLog(username, "Blank Username or Password", request.getRemoteAddr(),
							Integer.parseInt(attempt));
				}
			}

		} catch (MedicoException e) {
			map = new HashMap<>();
			map.put("ALLOW", false);
			map.put("LOGIN_MESSAGE", e.getMessage());
		} catch (Exception e) {
			e.printStackTrace();
			// System.out.println("Error Occurred :" +e.getMessage());// uncomment asneeded
			// --;
			System.out.println("Error Occurred :" + e.getMessage());// uncomment asneeded --;
		}
		return map;
	}

	@PostMapping("/logout")
	@PreAuthorize(AuthenticationRoleConstants.AUTHENTICATION_ROLE_USER)
	public Boolean signOut( HttpServletRequest request) {
		Boolean returnMe = true;
		UsermasterLog log = null;
		int count = 0;
		try {
			
			String userId = this.userMasterService.getEmployeeIdFromRequest(request);
			
			userMasterService.lockOrUnlockUser(userId, false);
			System.out.println("called logout::::");
			Usermaster user = this.userMasterRepository.getUserByEmpId(userId);
			userMasterService.setLog(user.getLd_lgnid(), "Successful Logout", request.getRemoteAddr(), count);
			System.out.println("UserId " + userId);
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("Error Occurred :" + e.getMessage());// uncomment asneeded --;
			returnMe = false;
		}
		return returnMe;
	}

	@GetMapping("/get-data-for-user-master")
	@PreAuthorize(AuthenticationRoleConstants.AUTHENTICATION_ROLE_USER)
	public Map<String, Object> getDataForUserEntry(@RequestParam String mode, HttpSession session) {
		Map<String, Object> map = new HashMap<>();
		try {
			String companyCode = session.getServletContext().getAttribute(COMPANY_CODE).toString();
			map.put("roles", menuService.getRoles(companyCode));
		} catch (Exception e) {
			System.out.println("Error Occurred :" + e.getMessage());// uncomment asneeded --;
		}
		return map;
	}

//	@PostMapping("/save-user-master")
//	@PreAuthorize(AuthenticationRoleConstants.AUTHENTICATION_ROLE_USER)
//	public Map<String, Object> saveUserMaster(@RequestBody UserMasterBean bean, HttpSession session) {
//		Map<String, Object> map = new HashMap<>();
//		map.put(DATA_SAVED, true);
//		try {
//			String companyCode = session.getServletContext().getAttribute(COMPANY_CODE).toString();
//			bean.setCompanyCode(companyCode);
//			userMasterService.save(bean);
//			map.put(RESPONSE_MESSAGE, "User saved successfully!");
//		} catch (Exception e) {
//			System.out.println("Error Occurred :" +e.getMessage());// uncomment asneeded --;
//			map.put(DATA_SAVED, false);
//			map.put(RESPONSE_MESSAGE, "Error Occurred !! Error Code : "+new CodifyErrors().getErrorCode(e));
//		}
//		return map;
//	}

	@GetMapping("/get-locked-users-by-level-code")
	@PreAuthorize(AuthenticationRoleConstants.AUTHENTICATION_ROLE_USER)
	public List<Usermaster> getLockedUsersByLevelCode(@RequestParam String levelCode) {
		List<Usermaster> list = null;
		try {
			list = userMasterService.getLockedUsers(levelCode);
		} catch (Exception e) {
			System.out.println("Error Occurred :" + e.getMessage());// uncomment asneeded --;
		}
		return list;
	}

	@PostMapping("/unlock-locked-users")
	@PreAuthorize(AuthenticationRoleConstants.AUTHENTICATION_ROLE_USER)
	public Map<String, Object> unlockLockedUsers(@RequestParam("usernames") String usernames) {
		Map<String, Object> map = new HashMap<>();
		map.put(DATA_SAVED, true);
		try {
			if (usernames.contains(",")) {
				userMasterService.unlockorLockUsersByUsernames(Arrays.asList(usernames.split(",")), N);
			} else {
				userMasterService.unlockorLockUsersByUsernames(Arrays.asList(usernames), N);
			}
			map.put(RESPONSE_MESSAGE, "Users unlocked successfully!");
		} catch (Exception e) {
			System.out.println("Error Occurred :" + e.getMessage());// uncomment asneeded --;
			map.put(DATA_SAVED, false);
			map.put(RESPONSE_MESSAGE, "Error Occurred !! Error Code : " + new CodifyErrors().getErrorCode(e));
		}
		return map;
	}

	@PostMapping("/change-user-password")
	@PreAuthorize(AuthenticationRoleConstants.AUTHENTICATION_ROLE_USER)
	public Map<String, Object> changePassword(@RequestBody ChangePasswordBean bean, HttpSession session) {
		Map<String, Object> map = new HashMap<>();
		map.put(DATA_SAVED, true);
		try {
			// this.userMasterService.changePassword(bean);
			map.put(RESPONSE_MESSAGE, "OTP Sent To your Mail.Please Login with OTP to reset Password");
			// send mail
			Usermaster user = this.userMasterService.getUserByUsername(bean.getUsername());
			if (user == null) {
				throw new MedicoException("That username does not matches any of our records.");
			} else {
				try {
					Password_Log passlog = null;
					userMasterService.savePasswordLogs(passlog, user);
					String password = RandomString.make();
					this.userMasterService.sendPassword(user, password);
					String subject = "New Login password";
					String mailContent = mailContentService.mailContentForSendNewPassword(user.getLd_email(), password);
					List<String> mailList = new ArrayList<>();
					mailList.add(user.getLd_email());
					// mailList.add(user.getLd_email());
					// mailList.add("shivram@excelsof.com");
					// mailList.add("milind.datar@excelsof.com");
					EmailFrom mailconfig = emailService.getEmailObject("EMAILFROM");
					ExecutorService executorService = Executors.newCachedThreadPool();
					executorService.execute(() -> {
						try {

							SendMail.sendHtmlMail(mailList, subject, mailContent,
									// user.getLd_email(),PasswordManager.decrypt(PasswordManager.decrypt(user.getLd_pass_ang())),
									mailconfig.getEmail(), mailconfig.getMail_password(), mailconfig.isAuth(),
									mailconfig.isTls_sls(), mailconfig.getHost(), mailconfig.getPort());
							// updating indicator to n
							this.userMasterService.updateUsernameDatatoN(user.getLd_lgnid());
						} catch (Exception e) {
							System.out.println("Error Occurred :" + e.getMessage());// uncomment asneeded --;
						}
					});
				} catch (Exception e) {
					System.out.println("Error Occurred :" + e.getMessage());// uncomment asneeded --;
				}
			}

		} catch (MedicoException e) {
			System.out.println("Error Occurred :" + e.getMessage());// uncomment asneeded --;
			map.put(DATA_SAVED, false);
			map.put(RESPONSE_MESSAGE, e.getMessage());
		} catch (Exception e) {
			System.out.println("Error Occurred :" + e.getMessage());// uncomment asneeded --;
			map.put(DATA_SAVED, false);
			map.put(RESPONSE_MESSAGE, "Error Occurred !! Error Code : " + new CodifyErrors().getErrorCode(e));
		}
		return map;
	}

	@PostMapping("/send-password")
	public Map<String, Object> sendPassword(@RequestParam String username, @RequestParam String secretq,
			@RequestParam String secretans, HttpServletRequest request) {
		Map<String, Object> map = new HashMap<>();
		map.put(DATA_SAVED, true);
		UsermasterLog log = null;
		int count = 0;
		try {
			userMasterService.setLog(username, "Forgot Password", request.getRemoteAddr(), count);
			Usermaster user = this.userMasterService.getUserByUsername(username);
			if (user == null) {
				throw new MedicoException("Invalid Input");
			} else if (user.getPassword_lock().equals(Y)) {
				throw new MedicoException("User is Locked Please Contact Administrator.");
			} else if (!user.getSecret_question().equals(secretq)
					|| !user.getSecret_answer().toUpperCase().equals(secretans.toUpperCase())) {
				throw new MedicoException("Invalid Input");
			} else {
				try {
					Password_Log passlog = null;
					userMasterService.savePasswordLogs(passlog, user);
					String password = RandomString.make();
					this.userMasterService.sendPassword(user, password);
					String subject = "New Login password";
					String mailContent = mailContentService.mailContentForSendNewPassword(user.getLd_email(), password);
					List<String> mailList = new ArrayList<>();
					mailList.add(user.getLd_email());
					// mailList.add(user.getEmail());
					// mailList.add("shivram@excelsof.com");
					EmailFrom mailconfig = emailService.getEmailObject("EMAILFROM");
					ExecutorService executorService = Executors.newCachedThreadPool();
					executorService.execute(() -> {

						try {
							SendMail.sendHtmlMail(mailList, subject, mailContent,
									// user.getLd_email(),
									// PasswordManager.decrypt(PasswordManager.decrypt(user.getLd_pass_ang())),
									mailconfig.getEmail(), mailconfig.getMail_password(), mailconfig.isAuth(),
									mailconfig.isTls_sls(), mailconfig.getHost(), mailconfig.getPort());
						} catch (Exception e) {
							System.out.println("Error Occurred :" + e.getMessage());// uncomment asneeded --;
						}
					});
				} catch (Exception e) {
					System.out.println("Error Occurred :" + e.getMessage());// uncomment asneeded --;
				}
			}
			map.put(RESPONSE_MESSAGE, "A temporary password has been sent to your registered e-mail address.");
		} catch (MedicoException e) {
			map.put(DATA_SAVED, false);
			map.put(RESPONSE_MESSAGE, e.getMessage());
		} catch (Exception e) {
			System.out.println("Error Occurred :" + e.getMessage());// uncomment asneeded --;
			map.put(DATA_SAVED, false);
			map.put(RESPONSE_MESSAGE, "Error Occurred !! Error Code : " + new CodifyErrors().getErrorCode(e));
		}
		return map;
	}

	@PostMapping("/change-temporary-password")
	public Map<String, Object> changeTemporaryPassword(@RequestParam String username, @RequestParam String password,
			@RequestParam String secretqstn, @RequestParam String secretans) {
		Map<String, Object> map = new HashMap<>();
		map.put(DATA_SAVED, true);
		try {
			String message = this.userMasterService.changeTemporaryPassword(username, password, secretqstn, secretans);
			map.put(RESPONSE_MESSAGE, message);
		} catch (MedicoException e) {
			map.put(DATA_SAVED, false);
			map.put(RESPONSE_MESSAGE, e.getMessage());
			System.out.println("Error Occurred :" + e.getMessage());// uncomment asneeded --;
		} catch (Exception e) {
			System.out.println("Error Occurred :" + e.getMessage());// uncomment asneeded --;
			map.put(DATA_SAVED, false);
			map.put(RESPONSE_MESSAGE, "Error Occurred !! Error Code : " + new CodifyErrors().getErrorCode(e));
		}
		return map;
	}

	@GetMapping("/ksybfelsjcywmkghpsef")
	public Map<String, Object> getObjectByUsername(@RequestParam String username, HttpSession session) {
		Map<String, Object> map = new HashMap<>();
		try {
			// this.userMasterService.getObjectByUsername(username));
			map.put("user", this.userMasterService.generatenewEncryption(username));
		} catch (Exception e) {
			e.printStackTrace();
			// System.out.println("Error Occurred :" +e.getMessage());// uncomment asneeded
			// --;
			System.out.println("Error Occurred :" + e.getMessage());// uncomment asneeded --;
		}
		return map;
	}

	@GetMapping("/fix-user-password")
	public Map<String, Object> fixUserPassword(HttpServletRequest request) {
		Map<String, Object> map = new HashMap<>();
		try {
			String companyCode = request.getServletContext().getAttribute(COMPANY_CODE).toString();
			this.userMasterService.fixUserPassword(companyCode);
			map.put(DATA_SAVED, true);
		} catch (Exception e) {
			System.out.println("Error Occurred :" + e.getMessage());// uncomment asneeded --;
			map.put(DATA_SAVED, false);
		}
		return map;
	}

	@PostMapping("/set-Log")
	public Map<String, Object> setLog(@RequestParam String username, HttpServletRequest request) {
		Map<String, Object> map = new HashMap<>();
		map.put(DATA_SAVED, true);
		UsermasterLog log = null;
		int count = 0;
		try {
			userMasterService.setLog(username, "Forgot Password", request.getRemoteAddr(), count);
		} catch (Exception e) {
			System.out.println("Error Occurred :" + e.getMessage());// uncomment asneeded --;
		}
		return map;
	}

	@PostMapping("/get-Attempts")
	public ResponseEntity<Integer> fixUserPassword(
			//@RequestParam("username") String username, 
			@RequestBody Map<String,String> payload,
			HttpServletRequest request) {
		try {
			Integer attempts = userMasterService.getLatestAttempts(payload.get("username"));
			
			return ResponseEntity.ok(attempts);
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("Error Occurred :" + e.getMessage());// uncomment asneeded --;
			return ResponseEntity.badRequest().build();
		}
		//return map;
	}

	@GetMapping("/unlock-locked-user")
	public Map<String, Object> unlockLockedUser(@RequestParam("usernames") String usernames) {
		Map<String, Object> map = new HashMap<>();
		map.put(DATA_SAVED, true);
		try {
			if (usernames.contains(",")) {
				userMasterService.unlockorLockUsersByUsernames(Arrays.asList(usernames.split(",")), N);
			} else {
				userMasterService.unlockorLockUsersByUsernames(Arrays.asList(usernames), N);
			}
			map.put(RESPONSE_MESSAGE, "Users unlocked successfully!");
		} catch (Exception e) {
			System.out.println("Error Occurred :" + e.getMessage());// uncomment asneeded --;
			map.put(DATA_SAVED, false);
			map.put(RESPONSE_MESSAGE, "Error Occurred !! Error Code : " + new CodifyErrors().getErrorCode(e));
		}
		return map;
	}

	@GetMapping("/getLoginQuestions")
	public Map<String, Object> getLoginQuestions(HttpSession session) {
		Map<String, Object> map = new HashMap<>();
		List<SG_d_parameters_details> spd = new ArrayList<SG_d_parameters_details>();
		try {
			spd = userMasterService.getPasswordRecoveryQuestions(MedicoConstants.LOGQUEST);
			System.out.println("spd list " + spd.toString());
			map.put("spd", spd);
		} catch (Exception e) {
			System.out.println("Error Occurred :" + e.getMessage());// uncomment asneeded --;
		}

		return map;

	}
//	@PostMapping("/email-change-notification")
//	//@PreAuthorize(AuthenticationRoleConstants.AUTHENTICATION_ROLE_USER)
//	public Map<String, Object> changeEmailNotification( HttpSession session) {
//		Map<String, Object> map = new HashMap<>();
//		map.put(DATA_SAVED, true);
//		try {
//			List<Usermaster> filtereduserList = new ArrayList<>();
//			List<Usermaster> userList = this.userMasterService.getUsernameData();
//			LocalDate local1 = LocalDate.now(); //current date
//			//splitting date 
//			for(int dt=0;dt<userList.size();dt++) {
//			String date = userList.get(dt).getPwd_expiry_dt().toString();
//			String[] dateParts = date.split("-");
//			String year = dateParts[0]; 
//			String month = dateParts[1]; 
//			String day = dateParts[2]; 
//			System.out.println("year"+year+":::"+"month"+month+":::"+"day"+day);
//			LocalDate expDate = LocalDate.of(Integer.parseInt(year),Integer.parseInt(month),Integer.parseInt(day));
//			expDate = expDate.minusDays(6); 
//			
//			if(local1.compareTo(expDate) >= 0) {
//				filtereduserList.add(userList.get(dt));
//				}
//			}
//			if(filtereduserList.size()>0) {
//			for(int i=0;i<filtereduserList.size();i++) {
//			//send mail
//				try {
//					String subject = "Reminder -Password will expire soon";
//					String mailContent = mailContentService.mailContentForSendNewPasswordNotification(userList.get(i).getLd_lgnid());
//					List<String> mailList = new ArrayList<>();
//					//mailList.add(((Usermaster) userList).getLd_email());
//					//mailList.add("shivram@excelsof.com");
//					//mailList.add("samruddha@excelsof.com");
//					
//					mailList.add(filtereduserList.get(i).getLd_email());
//					//mailList.add("milind.datar@excelsof.com");
//					Email mailconfig = emailService.getEmailObject("EMAILFROM");
//					ExecutorService executorService = Executors.newCachedThreadPool();
//					executorService.execute(() -> {
//						try {
//							SendMail.sendHtmlMail(mailList, subject,mailContent,
//									mailconfig.getEmail(),mailconfig.getMail_password(),mailconfig.isAuth(),
//											mailconfig.isTls_sls(),mailconfig.getHost(),mailconfig.getPort());
//						} catch (Exception e) {
//							System.out.println("Error Occurred :" +e.getMessage());// uncomment asneeded --;
//						}
//						
//					});
//					
//				} 
//				catch (Exception e) {
//					System.out.println("Error Occurred :" +e.getMessage());// uncomment asneeded --;
//				}
//				//update am_m_login-detail table for indicator
//				this.userMasterService.updateUsernameData(userList.get(i).getLd_lgnid());
//			}
//			}
//			
//		} catch (MedicoException e) {
//			System.out.println("Error Occurred :" +e.getMessage());// uncomment asneeded --;
//			map.put(RESPONSE_MESSAGE, e.getMessage());
//		} catch (Exception e) {
//			System.out.println("Error Occurred :" +e.getMessage());// uncomment asneeded --;
//			map.put(RESPONSE_MESSAGE, "Error Occurred !! Error Code : "+new CodifyErrors().getErrorCode(e));
//		}
//		return map;
//	}

	@PostMapping(value = "/get-secret-question")
	public Map<String, Object> getsecretquestion(@RequestParam String username, HttpSession session) {
		Map<String, Object> map = new HashMap<String, Object>();
		try {
			System.out.println("username : " + username);
			Usermaster user = userMasterService.getUserByUsername(username);
			System.out.println("user : " + user.getSecret_question());
			map.put("userquestion", user.getSecret_question());
		} catch (Exception e) {
			// TODO: handle exception
			System.out.println("Error Occurred :" + e.getMessage());// uncomment asneeded --;
		}
		return map;
	}

	@PostMapping("/unlockUserSameIpLogin")
	public Map<String, Object> updateUserLock(@RequestParam String username) {
		Map<String, Object> map = new HashMap<String, Object>();
		Boolean isUnlocked = false;
		try {
			isUnlocked = this.userMasterService.unlockUserIfSameIp(username);
			map.put("isUnlocked", isUnlocked);
		} catch (Exception e) {
			System.out.println("Error Occurred :" + e.getMessage());// uncomment asneeded --;
		}
		return map;
	}

	@PostMapping("/generateOtp")
	public Map<String, Object> generateOtp(@RequestParam String username) {
		Map<String, Object> map = new HashMap<String, Object>();
		try {
			Usermaster user = this.userMasterService.getUserByUsername(username);
			if (user == null) {
				throw new MedicoException("That username does not matches any of our records.");
			}
			String password = RandomString.make();
			System.out.println("OTP : " + password);
			this.userMasterService.generateotp(user, password);

			String subject = "New Login OTP";
			String mailContent = mailContentService.mailContentForSendNewOTP(user.getLd_lgnid(), password);
			List<String> mailList = new ArrayList<>();
			mailList.add(user.getLd_email());
//			mailList.add("samruddha@excelsof.com");
//			mailList.add("malipravin234@gmail.com");
//			mailList.add("abhishek@excelsof.com");
//			mailList.add("siddheshsarang02@gmail.com");
//			mailList.add("22omkarchaudhari@gmail.com");
//			mailList.add("yhitesh90@gmail.com");
//			mailList.add("pkalambe986@gmail.com");
//			mailList.add("buntykamble789@gmail.com");
//			mailList.add("milind.test.mail@gmail.com");
//			mailList.add("yash@excelsof.com");
//			mailList.add("rihiborse@gmail.com");
//			mailList.add("pawaskar@excelsof.com");
//			mailList.add("milind.datar@excelsof.com");
			EmailFrom mailconfig = emailService.getEmailObject("EMAILFROM");
			ExecutorService executorService = Executors.newCachedThreadPool();
			executorService.execute(() -> {
				try {
					SendMail.sendHtmlMail(mailList, subject, mailContent,
							// user.getLd_email(),PasswordManager.decrypt(PasswordManager.decrypt(user.getLd_pass_ang())),
							mailconfig.getEmail(), mailconfig.getMail_password(), mailconfig.isAuth(),
							mailconfig.isTls_sls(), mailconfig.getHost(), mailconfig.getPort());
				} catch (Exception e) {
					System.out.println("Error Occurred :" + e.getMessage());// uncomment asneeded --;
				}
			});
			map.put("FLAG", true);

		} catch (Exception e) {

			map.put("FLAG", false);
			System.out.println("Error Occurred :" + e.getMessage());// uncomment asneeded --;
		}
		return map;
	}

	@PostMapping("/EraseOtp")
	public Map<String, Object> EraseOtp(@RequestParam String username) {
		Map<String, Object> map = new HashMap<String, Object>();
		try {
			Usermaster user = this.userMasterService.getUserByUsername(username);
			if (user == null) {
				throw new MedicoException("That username does not matches any of our records.");
			}
			userMasterService.EraseOtp(username);
		} catch (Exception e) {
			// TODO: handle exception
			System.out.println("Error Occurred :" + e.getMessage());// uncomment asneeded --;

		}
		return map;
	}

	@PostMapping("/isValidationActive")
	public Map<String, Object> IsOtpCaptchaReq() {
		Map<String, Object> map = new HashMap<String, Object>();
		try {
			map.put(ACCEPT_OTP, parameterservice.IsOtp_Captcha_Req(ACCEPT_OTP).trim());
			map.put(ACCEPT_CAPTCHA, parameterservice.IsOtp_Captcha_Req(ACCEPT_CAPTCHA).trim());
		} catch (Exception e) {
			// TODO: handle exception
			System.out.println("Error Occurred :" + e.getMessage());// uncomment asneeded --;
		}
		return map;
	}

	@GetMapping("/getCaptcha")
	public Map<String, Object> getCapctha() {
		Map<String, Object> map = new HashMap<String, Object>();
		try {
			Captcha cap = this.captchaService.createCapctcha(150, 55);
			String imageData = this.captchaService.encodeCaptchaBase64(cap);
			map.put(DATA_SAVED, DATA_SAVED);
			// this value is the actual text of the captcha //send this to fron end to
			// compare and use
			System.out.println("captcha answer" + cap.getAnswer());
			map.put("imageData", imageData);
			map.put("captchaAnswer", Base64.encode(cap.getAnswer()));
		} catch (Exception e) {
			map.put(ERRORMSG, "Failed to generate captcha. Try reloading the captcha.");
		}
		return map;
	}

	@PostMapping("/get-User-Decrtpted-password")
	public Map<String, Object> getUserDecryptedPassword(@RequestParam("userId") String userid, HttpSession session) {
		Map<String, Object> map = new HashMap<>();
		String userId = userid; // "E00040"; // get it from session
		String decryptedPass = null;
		try {
			Usermaster user = userMasterService.getUserByUsername(userid);
			decryptedPass = user.getLd_pass_ang();
			int salt = userMasterService.getSaltvalByUsername(user.getLd_lgnid());
			System.out.println(
					"decryptedPass:::" + PasswordManager.decrypt(PasswordManager.decrypt(decryptedPass, salt), salt));
			map.put("decryptedPass", PasswordManager.decrypt(PasswordManager.decrypt(decryptedPass, salt), salt));

		} catch (Exception e) {
			System.out.println("Error Occurred :" + e.getMessage());// uncomment asneeded --;
			System.out.println("Error Occurred :" + e.getMessage());// uncomment asneeded --;
		}
		return map;
	}

	@GetMapping("/get-otpattempts")
	public Map<String, Object> fixUserOTP(@RequestParam("username") String username, HttpServletRequest request) {
		Map<String, Object> map = new HashMap<>();
		try {
			System.out.println("username :::: attempts " + username);
			Usermaster user = userMasterService.getUserByUsername(username);
			Long datetmediff = 0l;
			if (user.getOtp_date_time() == null) {
				datetmediff = 0l;
			} else {
				Long currdate = new Date().getTime();

				datetmediff = (currdate - user.getOtp_date_time().getTime()) / 1000;
				System.out.println("datediff ::: " + datetmediff + "   :: " + currdate);
			}

			map.put("ATTEMPT", user.getOtp_max_attempt());
			map.put("DATETMEDIFF", datetmediff);
		} catch (Exception e) {
			// TODO: handle exception
			System.out.println("Error Occurred :" + e.getMessage());// uncomment asneeded --
																	// System.out.println("Error Occurred :"
																	// +e.getMessage());// uncomment asneeded --;
		}
		return map;
	}

	@GetMapping("/set-lockotpsend")
	public Map<String, Object> setlockotpsend(@RequestParam("username") String username, HttpServletRequest request) {
		Map<String, Object> map = new HashMap<>();
		try {
			userMasterService.setlockotpsend(username);

		} catch (Exception e) {

			// TODO: handle exception
			System.out.println("Error Occurred :" + e.getMessage());// uncomment asneeded --
																	// System.out.println("Error Occurred :"
																	// +e.getMessage());// uncomment asneeded --;
		}
		return map;
	}

}
