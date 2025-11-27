package com.excel.service;

import java.time.LocalDateTime;
import java.util.concurrent.ConcurrentHashMap;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import com.excel.model.Usermaster;
import com.excel.restcontroller.SchmDelReqController;

@Service
public class HeartbeatService {
	
	private static final Logger logger = LogManager.getLogger(HeartbeatService.class);

	private final UserMasterService userMastService;
	private final ArticleSchemeDeliveryReqService artSchmDelServi;
	private final Article_Scheme_master_Service article_Scheme_master_Service;

	public HeartbeatService(UserMasterService userMastService, ArticleSchemeDeliveryReqService artSchmDelServi,
			Article_Scheme_master_Service article_Scheme_master_Service) {
		this.userMastService = userMastService;
		this.artSchmDelServi = artSchmDelServi;
		this.article_Scheme_master_Service = article_Scheme_master_Service;
	}

	private static final ConcurrentHashMap<String, LocalDateTime> heartbeats = new ConcurrentHashMap<>();

	public void updateHeartbeat(String employeeId) {
		heartbeats.put(employeeId, LocalDateTime.now());
	}

	public static ConcurrentHashMap<String, LocalDateTime> getHeartbeats() {
		return heartbeats;
	}


	@Scheduled(fixedRate = 60000) // Check every minute
	public void logoutInactiveUsers() {
		LocalDateTime threshold = LocalDateTime.now().minusMinutes(3);
		heartbeats.entrySet().removeIf(entry -> {
			if (entry.getValue().isBefore(threshold)) {
				try {
					logoutUser(entry.getKey());
				} catch (Exception e) {
					e.printStackTrace();
					return false;
				}
				return true;
			}
			return false;
		});
	}

	private void logoutUser(String employeeId) throws Exception {
		
		
		System.err.println("employeeId:::"+employeeId);
		if(this.userMastService.getUserByEmpId(employeeId.trim()).getUser_lock().trim().equalsIgnoreCase("Y")) {
			// Implement user logout logic, e.g., invalidate session
			// remove locked sales products
			this.article_Scheme_master_Service.unlock_article_product(employeeId);
			// remove locked customers
			this.artSchmDelServi.unlockAllCustByUserId(employeeId);
			// logout user
			userMastService.lockOrUnlockUser(employeeId, false);
			System.out.println("called logout::::");
			Usermaster user = this.userMastService.getUserByEmpId(employeeId);
			userMastService.setLog(user.getLd_lgnid(), "Successful Logout from ", "0.0.0.0.0.1", 0);
			logger.info("Logging out employee : " + employeeId);
			System.out.println("Logging out employee : " + employeeId);
		}
		else {
			logger.info("User already logged out : " + employeeId);
			System.out.println("User already logged out : " + employeeId);
		}
		
	}
}
