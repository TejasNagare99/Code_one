package com.excel.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.excel.utility.MedicoConstants;

@Service
public class MailContentServiceImpl implements MailContentService, MedicoConstants{
	@Autowired private CompanyMasterService companyMasterService;
	
	@Override
	public String mailContentForSendNewPassword(String fs_name, String password) throws Exception{
		StringBuffer body = new StringBuffer();
		body.append("<span> Hi " + fs_name + ",</span><br><br>");
		body.append("<span> We received a request to reset your password.</span><br>");
		body.append("<span> Your temporary password is <b>" + password + "</b></span></br>");
		body.append("<html>");
		body.append("<body> ");
		body.append("<style type=\"text/css\">");
		body.append(" .heading {");
		body.append(" font-size: 16px;");
		body.append(" font-weight: bold;");
		body.append(" text-align: center;");
		body.append(" font-family:Book Antiqua; ");
		body.append("}");
		body.append("</style>");
		body.append(" <h4> This is a system generated mail. Please do not reply.</h4>");
		return body.toString();
	}

	@Override
	public String mailContentForSendLockNotification(String fs_name) throws Exception {
		StringBuffer body = new StringBuffer();
		body.append("<span> Hi " + fs_name + ",</span><br><br>");
		body.append("<span> Your Account has been locked due to wrong password, Please contact Administrator.</span><br>");
		body.append("<html>");
		body.append("<body> ");
		body.append("<style type=\"text/css\">");
		body.append(" .heading {");
		body.append(" font-size: 16px;");
		body.append(" font-weight: bold;");
		body.append(" text-align: center;");
		body.append(" font-family:Book Antiqua; ");
		body.append("}");
		body.append("</style>");
		body.append(" <h4> This is a system generated mail. Please do not reply.</h4>");
		return body.toString();
	}

	@Override
	 public String MailContentForAdminUserUnlock(String fs_name) throws Exception{
		final String approvalLink = companyMasterService.getCompanyDetails().getWeb_site();
		  StringBuffer body = new StringBuffer();
		  String releaselink="rest/unlock-locked-user?usernames="+fs_name;
		  body.append("<span> For  " + fs_name + ",</span><br><br>");
		  body.append("<span> User is Locked . </span><br>");
		  //body.append("<span> Your temporary password is <b>" + password + "</b></span></br>");
			body.append("<br><button style='background-color: green;;'><a style='text-decoration: none ;color: white;'"
					+ " href='"+approvalLink+releaselink+""
					+ "';>Click here to unlock</a></button> ");
		  body.append("<html>");
		  body.append("<body> ");
		  body.append("<style type=\"text/css\">");
		  body.append(" .heading {");
		  body.append(" font-size: 16px;");
		  body.append(" font-weight: bold;");
		  body.append(" text-align: center;");
		  body.append(" font-family:Book Antiqua; ");
		  body.append("}");
		  body.append("</style>");
		  body.append(" <h4> This is a system generated mail. Please do not reply.</h4>");
		  return body.toString();
		  }

	@Override
	public String mailContentForSendNewPasswordNotification(String fs_name,String expirydate) throws Exception {
		StringBuffer body = new StringBuffer();
		body.append("<span> Hi " + fs_name + ",</span><br><br>");
		body.append("<span> This is a reminder that your Pasword is expiring soon.</span><br>");
		body.append("<span> Your password Expiry Date is  <b>" + expirydate + "</b></span></br>");
		body.append("<span> Please Change Your Password.</span></br>");
		body.append("<html>");
		body.append("<body> ");
		body.append("<style type=\"text/css\">");
		body.append(" .heading {");
		body.append(" font-size: 16px;");
		body.append(" font-weight: bold;");
		body.append(" text-align: center;");
		body.append(" font-family:Book Antiqua; ");
		body.append("}");
		body.append("</style>");
		body.append(" <h4> This is a system generated mail. Please do not reply.</h4>");
		return body.toString();
	}

	@Override
	public String mailContentForSendNewOTP(String fs_name, String password) throws Exception{
		StringBuffer body = new StringBuffer();
		body.append("<span> Hi " + fs_name + ",</span><br><br>");
		body.append("<span> Your One Time  Key is <b>" + password + "</b></span></br>");
		body.append("<html>");
		body.append("<body> ");
		body.append("<style type=\"text/css\">");
		body.append(" .heading {");
		body.append(" font-size: 16px;");
		body.append(" font-weight: bold;");
		body.append(" text-align: center;");
		body.append(" font-family:Book Antiqua; ");
		body.append("}");
		body.append("</style>");
		body.append(" <h4> This is a system generated mail. Please do not reply.</h4><br>");
		
		body.append("<b>Thanks and regards</b><br>");
		body.append("<b>Admin team</b><br>");
		body.append("<b>Medico SG</b><br>");
		return body.toString();
	}
}
