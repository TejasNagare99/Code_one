package com.excel.restcontroller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.token.TokenService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.excel.bean.NotificationRequestBean;
import com.excel.configuration.JwtProvider;
import com.excel.model.Resign_Notification;
import com.excel.service.NotificationService;
import com.excel.service.UserMasterService;
import com.excel.utility.MedicoConstants;

@RestController
@RequestMapping("/rest")
public class NotificationRestController implements MedicoConstants {

	@Autowired private NotificationService notificationService;
	@Autowired private JwtProvider tokenProvider;
	@Autowired private UserMasterService userMasterService;

	@PostMapping("/get-notifications")
		public Map<String, Object> notifications(HttpSession session,HttpServletRequest request) {
		Map<String, Object> map = new HashMap<>();
		String compCode = session.getServletContext().getAttribute(COMPANY_CODE).toString();
		List<Object[]> grnList = null;
		try {
			String uname = this.tokenProvider.getUsernameFromRequest(request);
			String empId = this.userMasterService.getEmpIdByUsername(uname);
			grnList = new ArrayList<Object[]>();
			NotificationRequestBean bean = new NotificationRequestBean();
			List<Resign_Notification> list = notificationService.getNotifications(bean);
			if(compCode.trim().equals("PAL")) {
				grnList = notificationService.getGrnNotifications(empId);
			}
			map.put("list", list);
			map.put("grnList",grnList);
		} catch (Exception e) {
			System.out.println("Error Occurred :" +e.getMessage());// uncomment asneeded --;
		}
		return map;
	}
	
}
