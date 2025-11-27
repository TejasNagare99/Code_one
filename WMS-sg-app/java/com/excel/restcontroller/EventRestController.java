package com.excel.restcontroller;


import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.excel.bean.EventBean;
import com.excel.configuration.JwtProvider;
import com.excel.model.Event;
import com.excel.service.EventService;
import com.excel.service.UserMasterService;
import com.excel.utility.AuthenticationRoleConstants;
import com.excel.utility.MedicoConstants;


@RequestMapping("/rest")
@PreAuthorize(AuthenticationRoleConstants.AUTHENTICATION_ROLE_USER)
@RestController
public class EventRestController implements MedicoConstants {
	
	@Autowired
 private EventService eventService;
	@Autowired private JwtProvider tokenProvider;
	@Autowired private UserMasterService userMasterService; 
	
	@PostMapping("/save-event")
	public Map<String, Object> save(@RequestBody EventBean bean, HttpSession session) {
		
		System.out.println(bean);
		Map<String, Object> map = new HashMap<String, Object>();
		map.put(DATA_SAVED, true);
		try {
			String companyCode = session.getServletContext().getAttribute(COMPANY_CODE).toString();
			bean.setCompanyCode(companyCode);
			
			eventService.save(bean);
			map.put(RESPONSE_MESSAGE, "Event created.");
			
			List<Event> event =eventService.getReminder(bean.getUsername());
			map.put("ListOfReminder",event.size());
			
			
		} catch (Exception e) {
			e.printStackTrace();
			map.put(DATA_SAVED, false);
			map.put(RESPONSE_MESSAGE, RESPONSE_ERROR_MESSAGE);
		}
		return map;
	}
	
	@PostMapping("/delete-single-event")
	public Map<String, Object> deleteEvent(@RequestParam Long eventId) {
		Map<String, Object> map = new HashMap<>();
		map.put(DATA_SAVED, true);
		try {
			eventService.delete(eventId);
			map.put(RESPONSE_MESSAGE, "Event Deleted.");
		} catch (Exception e) {
			e.printStackTrace();
			map.put(DATA_SAVED, false);
			map.put(RESPONSE_MESSAGE, RESPONSE_ERROR_MESSAGE);
		}
		return map;
	}
	
	
//	@PostMapping("/get-reminder")
//	public List<Event> getReminder(HttpServletRequest request) throws Exception {
//		String uname = this.tokenProvider.getUsernameFromRequest(request);
//		String empId = this.userMasterService.getEmpIdByUsername(uname);
//		List<Event> event =eventService.getReminder(empId);
//		return event;
//	}



}
