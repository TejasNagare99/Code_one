package com.excel.controller;

import java.time.LocalDateTime;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.excel.service.HeartbeatService;
import com.excel.service.UserMasterService;

@RestController
@RequestMapping("/rest")
public class HeartbeatController {
	
	@Autowired private UserMasterService userMasterService;

	private final HeartbeatService heartBeatService;

	public HeartbeatController(HeartbeatService heartBeatService) {
		this.heartBeatService = heartBeatService;
	}

	@PostMapping("/heartbeat")
	public ResponseEntity<Void> receiveHeartbeat(HttpServletRequest request) {

		String empId="";
		try {
			empId = this.userMasterService.getEmployeeIdFromRequest(request);
		} catch (Exception e) {
			
			System.out.println(e.getMessage());
//			e.printStackTrace();
		}
		
		heartBeatService.updateHeartbeat(empId);
		return ResponseEntity.ok().build();
	}
	
	@GetMapping("/show-heartbeat-data")
	public ResponseEntity<ConcurrentHashMap<String, LocalDateTime>> showCurrLoggedIn(){
		 return ResponseEntity.ok(HeartbeatService.getHeartbeats());
	}
}
