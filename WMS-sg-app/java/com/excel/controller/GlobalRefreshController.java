package com.excel.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import com.fasterxml.jackson.databind.JsonNode;

@Controller
public class GlobalRefreshController {
	
	private Sso_controller sso_controller;

	public GlobalRefreshController(Sso_controller sso_controller) {
		super();
		this.sso_controller = sso_controller;
	}


	// Put all the angular routing URLs over here to redirect to index.html
	@GetMapping({
		"/medico-user/home", 
		"/masters/**", 
		"/transactions/**",
		"/reports/**",
		"/AuditTrailLogs/**",
		"/login",
		"/admin-menu/**",
		"/approvals/**",
		"/user-profile/**",
		"/grn/**",
		"/gst/**",
		"/print/**",
		"/dispatch/**",
		"/",
		"/error-page",
		"/page-not-found",
		"/pdf/**",
		"/saveApprovalDataList/**",
		"/get-details-for-approval/**",
		"/allocation/**",
		"/stktrf/**",
		"/master/**",
		"/iaa/**",
		"/specialAllocation/**",
		"/stkwithdrawal/**",
		"/approval/**",
		"/Downloads/**",
		"/userUnlock/**",
		"/consolidation/**",
		"/lr/**",
		"/abolished/**"
	})
	public String forward(HttpServletRequest request) {
		// Check if we have the "code" and "state" parameters from the URL
        String code = request.getParameter("code");
        String state = request.getParameter("state");
        if (code != null && state != null) {
        	try {
        		//add SSO logic here
            	//get the response body of the access token request
            	JsonNode responseAccessToken = this.sso_controller.getAccessToken(code, state);
            	if(responseAccessToken!=null) {
            		//SSO access token successful
            		//make request for protected resource
            		JsonNode prot_res = this.sso_controller.getProtectedResource(
            				responseAccessToken.get("access_token").asText());
            		if(prot_res!=null) {
            			//get the user and set the session after logging him in
            		}
            		else {
            			//error-page //forward to an error page in angular
            		}
            	}
            	else {
            		//error-page //forward to an error page in angular
            	}
        	}
        	catch(Exception e) {
        		e.printStackTrace();
        	}
        	
        }
        else {
        	
        }
        return "forward:/index.html";
	}

}
