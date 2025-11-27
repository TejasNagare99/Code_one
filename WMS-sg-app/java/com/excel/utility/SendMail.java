package com.excel.utility;

import java.io.IOException;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.Set;

import javax.mail.BodyPart;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.excel.bean.AllocationBean;
import com.excel.bean.ArtSchemeBeanForApprNewLogic;
import com.excel.bean.DispatchMailBean;
import com.excel.bean.MailBean;
import com.excel.bean.SpecialAllocationBean;
import com.excel.model.Annual_sample_plan_view_report_cons;
import com.excel.model.ArticleSchemeRequestHeader;
import com.excel.model.Email;
import com.excel.model.EmailFrom;
import com.excel.model.IaaDetailForApprovalMail;
import com.excel.model.IaaDetailForMail;
import com.excel.model.Pg_Doc_Alloc_Template;
import com.excel.model.Pg_Doc_Alloc_Template_Error;
import com.excel.model.Resign_Notification;
import com.excel.repository.AllocationRepository;
import com.excel.repository.DispatchRepository;
import com.excel.repository.EmailRepository;
import com.excel.repository.GrnRepository;
import com.excel.restcontroller.ReportRestController;
import com.excel.service.Annual_sample_plan_view_Service;
import com.excel.service.CompanyMasterService;
import com.excel.service.EmailTranWiseService;
import com.excel.service.IAAService;
import com.excel.service.ReportService;
import com.excel.service.TaskMasterService;
import com.excel.service.UserMasterService;

@Service
public class SendMail implements MedicoConstants {

	@Autowired
	AllocationRepository allocationRepository;
	@Autowired
	TaskMasterService taskMasterService;
	@Autowired
	GrnRepository grnRepository;
	@Autowired
	DispatchRepository dispatchRepository;
	@Autowired
	UserMasterService userMasterService;
	@Autowired
	ReportRestController reportRestController;
	@Autowired
	private ReportService reportService;
	@Autowired
	private Annual_sample_plan_view_Service annual_sample_plan_view_service;
	@Autowired
	private EmailRepository emailrepository;
	@Autowired
	private CompanyMasterService companyMasterService;
	@Autowired
	EmailTranWiseService emailtranwiseservice;
	@Autowired
	private IAAService iAAService;

	public void sendMail(Long tranId, String user, String apptovalType, String taskId, String status,
			String nextApprovar, Long year, String tranType, String compcode,String tran_name) throws Exception {
		System.out.println("trandId " + tranId);
		System.out.println("user " + user);
		
		System.out.println("apptovalType " + apptovalType);
		// starts
		List<AllocationBean> details = null;
		List<MailBean> approvalDetails = null;
		List<IaaDetailForApprovalMail> iaadtllist = new ArrayList<IaaDetailForApprovalMail>();
		MailBean mail = null;
		String approvelink = null;
		String discardLink = null;
		List<MailBean> list = taskMasterService.getMailSendDetails(tranId, nextApprovar, tranType);
		mail = list.get(0);
		System.out.println("mail " + mail.getEmp_email());

		if (taskId != null) {// On self Approval it gives error as there is no task id in self approval
			approvalDetails = taskMasterService.getApprovalDetails(Long.valueOf(taskId));
		}
		approvelink = "rest/saveApprovalDataListViaMail?task_id=" + mail.getAct_taskid() + "&decision=approve&userName="
				+ mail.getAssignee_() + "&remark=" + mail.getAct_taskid() + "&tran_id=" + tranId;
		discardLink = "rest/saveApprovalDataListViaMail?task_id=" + mail.getAct_taskid() + "&decision=discard&userName="
				+ mail.getAssignee_() + "&remark=" + mail.getAct_taskid() + "&tran_id=" + tranId;
		//if (tranType.equals(ANNUAL_APPR_PFZ) || tranType.equals(ANNUAL_APPR_PIPL)) 
		if (tran_name.equalsIgnoreCase(ANN_SAMPLE_PLAN)) 	
		{
			details = this.allocationRepository.getSummaryDetailsAnnualPlan(tranId, year.toString(), null);
			// get report
			List<Annual_sample_plan_view_report_cons> detailList = reportService.getDataforannualplanviewcon('T',
					tranId.toString());
			String filename = annual_sample_plan_view_service.generate_annual_sample_plan_con_report(detailList, PFZ,
					year.toString());
			details.get(0).setFilePath(REPORT_FILE_PATH + filename);
			// send mail to approver
			this.sendApprovalMailForAnnualAllocation(details, Arrays.asList(mail.getEmp_email()), status, user,
					"Annual Allocation Forwarded For Approval  ", approvelink, mail, nextApprovar, approvalDetails,
					discardLink);
			// To send mail to manager
			if (nextApprovar != null) {
				String mailId = null;
				List<MailBean> assistant = this.userMasterService.getEmpDetailForMail(details.get(0).getMgrEmpId());
				mailId = assistant.get(0).getEmp_email();
				this.sendApprovalMailForAnnualAllocation(details, Arrays.asList(mailId), status, user,
						"Annual Allocation Forwarded For Approval  ", approvelink, mail, null, approvalDetails,
						discardLink);
			}
		} 
//		else if (tranType.equals(MedicoConstants.GRN_APPR_PFZ_PS) || tranType.equals(MedicoConstants.GRN_APPR_PFZ_PI)
//				|| tranType.equals(MedicoConstants.GRN_APPR_PIPL_PS)
//				|| tranType.equals(MedicoConstants.GRN_APPR_PIPL_PI)) 
		else if(tran_name.equalsIgnoreCase(GRN_FULL_TRAN_NAME))
		{
			List<Object> grnList = this.grnRepository.getGrnDetailsForMailSend(tranId, user, apptovalType,
					year.toString());
			List<String> maillist = emailtranwiseservice.getEmailListByTranType(GRN_, year.toString(), tranId);
			if (maillist == null) {
				maillist = new ArrayList<>();
				maillist.add(mail.getEmp_email());
			} 

			if (nextApprovar == null && compcode.trim().equals("PFZ")) {

				Object[] subjArr = (Object[]) grnList.get(0);
				String subject = subjArr[7] + " Goods Received at Warehouse : " + subjArr[33] + " , GRN : "
						+ subjArr[1] + " , on : " + subjArr[2] + " , Supplier : " + subjArr[5];

				if(maillist!=null && !maillist.isEmpty()) {
					this.sendFinalApprovalMailForGRN(grnList, maillist, status, user, subject, compcode);
				}
			} else {
				this.sendApprovalMailForGRN(grnList, Arrays.asList(mail.getEmp_email()), status, user,
						"GRN Forwarded For Approval  ", approvelink, mail, nextApprovar, approvalDetails,
						discardLink, compcode);
			}
		} 
//		else if (tranType.equals(MedicoConstants.ALLOC_APPR_ALL_PFZ)
//				|| tranType.equals(MedicoConstants.ALLOC_APPR_ALL_PIPL)) 
		else if(tran_name.equalsIgnoreCase(ALLOC_TRAN_NAME))
		{
			List<AllocationBean> allocationList = allocationRepository.getAllocationDetail(tranId, year.toString());
			this.sendApprovalMailForMonthlyAllocation(allocationList, Arrays.asList(mail.getEmp_email()), status, user,
					"Monthly Allocation Forwarded For Approval  ", approvelink, mail, nextApprovar, approvalDetails,
					discardLink);// mail.getEmp_email()
		} 
		//else if (tranType.equals(MedicoConstants.DSP_APPR_PFZ) || tranType.equals(MedicoConstants.DSP_APPR_PIPL))
		else if(tran_name.equalsIgnoreCase(DISPATCH_FULL_TRAN_NAME))
		{
			List<DispatchMailBean> bean = dispatchRepository.getDispatchedMailData(tranId);
			System.out.println("Mail " + bean.get(0).getMail());
			this.sendMailForDispatch(bean, Arrays.asList(bean.get(0).getMail()), status, user, "Dispatched",
					approvelink, mail, nextApprovar, approvalDetails, discardLink);
		} 
		//else if (tranType.equals(ANNUAL_APPR_TEAM_PFZ) || tranType.equals(ANNUAL_APPR_TEAM_PIPL)) 
		else if(tran_name.equalsIgnoreCase(ASP_TEAM_CONSOLIDATION))
		{
			details = this.allocationRepository.getSummaryDetailsAnnualPlanByDivision(tranId, year.toString(),
					ANNUAL_TEAM_DOC_TYPE);
			// getReport
			List<Annual_sample_plan_view_report_cons> detailList = reportService.getDataforannualplanviewcon('C',
					tranId.toString());
			String filename = annual_sample_plan_view_service.generate_annual_sample_plan_con_report(detailList, PFZ,
					year.toString());
			details.get(0).setFilePath(REPORT_FILE_PATH + filename);
			// send mail to approver
			this.sendApprovalMailForAnnualAllocation(details, Arrays.asList(mail.getEmp_email()), status, user,
					"Annual Allocation Forwarded For Approval  ", approvelink, mail, nextApprovar, approvalDetails,
					discardLink);
			// To send mail to manager
			if (nextApprovar != null) {
				String mailId = null;
				List<MailBean> assistant = this.userMasterService.getEmpDetailForMail(details.get(0).getMgrEmpId());
				mailId = assistant.get(0).getEmp_email();
				this.sendApprovalMailForAnnualAllocation(details, Arrays.asList(mailId), status, user,
						"Annual Allocation Forwarded For Approval  ", approvelink, mail, null, approvalDetails,
						discardLink);
			}
		}
		//else if (tranType.equals(MedicoConstants.IAA_APPR_PFZ) || tranType.equals(MedicoConstants.IAA_APPR_PIPL))
		else if(tran_name.equalsIgnoreCase(IAA_TRAN_NAME))
		{
			iaadtllist = this.iAAService.getIaaDetailForApprovalMail(tranId, tranId);
			List<String> maillist = emailtranwiseservice.getEmailListByTranType(IAA, year.toString(), tranId);
			if (maillist == null) {
				maillist = new ArrayList<>();
				 maillist.add(mail.getEmp_email());
			} else {
				 maillist.add(mail.getEmp_email());
			}
			
			if(iaadtllist!=null && iaadtllist.size()>0) {
				this.sendApprovalMailForIaa(iaadtllist, maillist, status, user, "IAA Approval Mail", mail, nextApprovar,
					approvalDetails);
			}
		}
	}

	public String sendApprovalMailForSpecialAllocation(List<SpecialAllocationBean> details, List<String> to_list,
			String status, String userName, String subject, String link, MailBean mailBean, String nextApprovar,
			List<MailBean> approvalDetails, String discardLink, String compcd) throws Exception {
		try {
			System.out.println("ToList :" + to_list.size());
			final String approvalLink = companyMasterService.getCompanyDetails().getWeb_site();
			StringBuffer body = new StringBuffer();
			body.append("<html><body>The following  Allocation have been ");
			if (status.equals("F")) {
				body.append("generated");

			} else if (status.equals("A")) {
				body.append("approved by ");
				body.append("<b>");
				if (approvalDetails != null)
					body.append(approvalDetails.get(approvalDetails.size() - 1).getStarted_by_name());
				else
					body.append(userName);
				body.append(".</b>");

			} else if (status.equals("D")) {
				body.append("discarded by ");
				body.append("<b>");
				if (approvalDetails != null)
					body.append(approvalDetails.get(approvalDetails.size() - 1).getStarted_by_name());
				else
					body.append(userName);
				body.append(".</b>");
				subject = "Special Allocation Discarded";

			} else {
				body.append("send for approval by ");
				body.append("<b>");
				body.append(userName + "-" + mailBean.getStarted_by_name());
				body.append(".</b>");
			}

			StringBuffer tableHeader = new StringBuffer("<table style='background-color:powderblue;' >").append("<tr>")
					.append("<th  align='left' style='padding:10px;padding-right:80px;'>Product Name</th>")
					.append("<th  align='left' style='padding:10px;padding-right:80px;'>Brand Name</th>");

			if (!compcd.equals(PAL)) {
				tableHeader.append("<th  align='left' style='padding:10px;padding-right:80px;'>Alloc. Qty.</th>")
						.append("<th  align='left' style='padding:10px;padding-right:80px;'>Rate</th>");
			}
			tableHeader.append("<th  align='left' style='padding:10px;padding-right:80px;'>Value</th>").append("</tr>");

			StringBuffer tableDetail = new StringBuffer();

			Boolean ind = true;
			String document_no = details.get(0).getRequestNumber();
			BigDecimal totalvalue = BigDecimal.ZERO;
			for (SpecialAllocationBean bean : details) {

				tableDetail.append("<tr>")
						.append("<td align='left' style='padding:10px;padding-right:80px;'>" + bean.getProd_name()
								+ "</td>")
						.append("<td align='center' style='padding:10px;padding-right:80px;'>" + bean.getBrand_name()
								+ "</td>");

				if (!compcd.equals(PAL)) {
					tableDetail
							.append("<td align='center' style='padding:10px;padding-right:80px;'>" + bean.getAllocQty()
									+ "</td>")
							.append("<td align='center' style='padding:10px;padding-right:80px;'>" + bean.getSmp_rate()
									+ "</td>");
				}
				tableDetail.append(
						"<td align='left' style='padding:10px;padding-right:80px;'>" + bean.getAllocValue() + "</td>")
						.append("</tr>");

				totalvalue = totalvalue.add(bean.getAllocValue());
			}
			tableDetail.append("<tr>")
					.append("<td align='left' style='padding:10px;padding-right:80px;'><b>Total</b></td>")
					.append("<td align='center' style='padding:10px;padding-right:80px;'></td>");
			if (!compcd.equals(PAL)) {
				tableDetail.append("<td align='center' style='padding:10px;padding-right:80px;'></td>")
						.append("<td align='center' style='padding:10px;padding-right:80px;'></td>");
			}
			tableDetail
					.append("<td align='left' style='padding:10px;padding-right:80px;'><b>" + totalvalue + "</b></td>")
					.append("</tr>");

			StringBuffer bottomTable = null;
			if (approvalDetails != null) {
				bottomTable = new StringBuffer("<br><table >").append("<tr>")
						.append("<td  width='100px'  align='left' style='padding-top:10px;'><b>Approved by:</b></td>");
				for (MailBean bean : approvalDetails) {
					bottomTable
							.append("<td   align='left' style='padding-top:10px;'><b>" + bean.getApprovedBydesignation()
									+ "</b>:" + bean.getApprovedByName() + "</td>")
							.append("<td  align='left' style='padding-top:10px;'>&nbsp;</td>");
				}
				bottomTable.append("</tr>").append("<tr>")
						.append("<td  width='100px'  align='left' style='padding-top:10px;'><b>Remarks:</b></td>");
				for (MailBean bean : approvalDetails) {
					bottomTable
							.append("<td  align='left' style='padding-top:10px;'><b>" + bean.getApprovedBydesignation()
									+ "</b>:" + bean.getRemarks() + "</td>")
							.append("<td align='left' style='padding-top:10px;'>&nbsp;</td>");
				}
				bottomTable.append("</tr>").append("</table>");

			}
			body.append("<br><label><b>Date : </b>" + details.get(0).getAlloc_req_date()
					+ "</label>&nbsp;<b>Created By : </b>: " + mailBean.getStarted_by_name() + "<br>");
			body.append("<label><b>Requestor Name : </b>" + details.get(0).getRequestorName() + "</label><br>");
			body.append("<label><b>Recipient Name : </b>" + details.get(0).getRecipientName()
					+ "</label>&nbsp; <b>Address : </b>: " + details.get(0).getAddress() + "<br>");
			body.append("<label><b>Phone : </b>" + details.get(0).getPhone() + "</label><br>");
			if (nextApprovar != null) {
				// body.append("&nbsp;<label style='font-size:14px;color:blue;'> Actions
				// :</label><button style='background-color: green;'><a style='text-decoration:
				// none ;color: white;'
				// href='"+MedicoConstants.approvalLink+link+"';>Approve</a></button><button
				// style='background-color:red;color:white;' ><a style='text-decoration: none
				// ;color: white;'
				// href='"+MedicoConstants.approvalLink+"';>Deny</a></button><br>");
				body.append("&nbsp;<label> Actions :</label>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<a href='" + approvalLink + link
						+ "';>Approve</a>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<a href='" + approvalLink + discardLink + "';>Deny</a><br>");
			} else {
				subject = "Allocation Request Approved";
			}
			body.append(tableHeader);
			body.append(tableDetail);
			body.append("</table>");
			if (bottomTable != null)
				body.append(bottomTable);
			body.append("\n\nThis is a system generated mail. Please do not reply.");
			body.append("</body></html> ");

			// inline images
			Map<String, String> inlineImages = null;
			if (!details.get(0).getFilePath().equals("0")) {
				inlineImages = new HashMap<String, String>();
				inlineImages.put("image1", details.get(0).getFilePath());
			}

			if (body.length() > 0) {
				EmailFrom mailconfig = emailrepository.getEmailObject("EMAILFROM");
				sendHtmlMail(to_list,
						subject + " : " + document_no + " Recipient Name :" + details.get(0).getRecipientName()
								+ " Requestor Name: " + details.get(0).getRequestorName(),
						body.toString(), inlineImages, mailconfig.getEmail(), mailconfig.getMail_password(),
						mailconfig.isAuth(), mailconfig.isTls_sls(), mailconfig.getHost(), mailconfig.getPort());
			}

		} catch (Exception e) {
			// e.printStackTrace();
			System.out.println("Error Occurred :" +e.getMessage());// uncomment asneeded --;

		}
		return "success";
	}

	public String sendApprovalMailForMonthlyAllocation(List<AllocationBean> details, List<String> to_list,
			String status, String userName, String subject, String link, MailBean mailBean, String nextApprovar,
			List<MailBean> approvalDetails, String discardLink) throws Exception {

		try {
			final String approvalLink = companyMasterService.getCompanyDetails().getWeb_site();
			StringBuffer body = new StringBuffer();
			body.append("<html><body>The following  Allocation have been ");
			if (status.equals("F")) {
				body.append("generated");

			} else if (status.equals("A")) {
				body.append("approved by ");
				body.append("<b>");
				if (approvalDetails != null)
					body.append(approvalDetails.get(0).getStarted_by_name());
				else
					body.append(userName);
				body.append(".</b>");

			} else if (status.equals("D")) {
				body.append("discarded by ");
				body.append("<b>");
				if (approvalDetails != null)
					body.append(approvalDetails.get(0).getStarted_by_name());
				else
					body.append(userName);
				body.append(".</b>");
				subject = "Monthly Allocation Discarded";

			} else {
				body.append("send for approval by ");
				body.append("<b>");
				if (approvalDetails != null)
					body.append(approvalDetails.get(0).getStarted_by_name());
				else
					body.append(mailBean.getStarted_by_name());
				body.append(".</b>");
			}
			if (nextApprovar != null) {
				// body.append("&nbsp;<br><label style='font-size:14px;color:blue;'> Actions
				// :</label><button style='background-color: green;'><a style='text-decoration:
				// none ;color: white;'
				// href='"+MedicoConstants.approvalLink+link+"';>Approve</a></button><button
				// style='background-color:red;color:white;' ><a style='text-decoration: none
				// ;color: white;'
				// href='"+MedicoConstants.approvalLink+"';>Deny</a></button><br>");
				body.append("&nbsp;<br><label> Actions :</label><a href='" + approvalLink + link
						+ "';>Approve</a>&nbsp;<a href='" + approvalLink + discardLink + "';>Deny</a><br>");
			} else {
				subject = "Monthly Allocation Approved";
				if (status.equals("D"))
					subject = "Monthly Allocation Discarded";
			}
			StringBuffer tableHeader = new StringBuffer("<table style='background-color:powderblue;' >").append("<tr>")
					.append("<th  align='left' width='150px' style='padding:5px;'>Product Name</th>")
					.append("<th  align='left' width='250px' style='padding:5px;'>Brand</th>")
					.append("<th  align='center' width='100px' style='padding:5px;'>TE</th>")
					.append("<th  align='center' width='100px' style='padding:5px;'>DM</th>")
					.append("<th  align='center' width='100px' style='padding:5px;' >RBM</th>")
					.append("<th  align='center' width='100px' style='padding:5px;' >Alloc. Qty</th>")
					.append("<th  align='center' width='100px' style='padding:5px;' >Stock</th>").append("</tr>");

			StringBuffer tableDetail = new StringBuffer();

			Boolean ind = true;
			String document_no = null;
			BigDecimal teQty = BigDecimal.ZERO;
			BigDecimal dmQty = BigDecimal.ZERO;
			BigDecimal rmQty = BigDecimal.ZERO;
			BigDecimal totalAllocQty = BigDecimal.ZERO;
			BigDecimal stock = BigDecimal.ZERO;
			int i = 0;
			for (AllocationBean bean : details) {
				if (ind == true) {
					document_no = bean.getAllocDocNo();
					body.append("<br><label><b>Allocation No.    :</b>" + document_no
							+ "</label>&nbsp;&nbsp;;&nbsp;<label><b>   Allocation Month    :&nbsp;</b>"
							+ bean.getAllocationMonth()
							+ "</label>&nbsp;&nbsp;;&nbsp;<label><b>  Division    :&nbsp;</b>" + bean.getDiv_disp_nm()
							+ "</label>&nbsp;&nbsp;<label><b> Started By    :&nbsp;</b>" + mailBean.getStarted_by_name()
							+ "</label><br>");
					ind = false;
				}
				tableDetail.append("<tr>")
						.append("<td align='left'  width='150px' style='padding:5px;'>" + bean.getSmp_prod_name()
								+ "</td>")
						.append("<td align='left'  width='250px' style='padding:5px;'>" + bean.getSa_group_name()
								+ "</td>")
						.append("<td align='center'  width='100px' style='padding:5px;'>" + bean.getTeQty() + "</td>")
						.append("<td align='center'  width='100px' style='padding:5px;' >" + bean.getDmQty() + "</td>")
						.append("<td align='center'  width='100px' style='padding:5px;'>" + bean.getRmQty() + "</td>")
						.append("<td align='center'  width='100px' style='padding:5px;;'>" + bean.getTotalAllocQty()
								+ "</td>")
						.append("<td align='center'  width='100px' style='padding:5px;;'>" + bean.getStock() + "</td>")
						.append("</tr>");

				teQty = teQty.add(BigDecimal.valueOf(Double.valueOf(bean.getTeQty())));
				dmQty = dmQty.add(BigDecimal.valueOf(Double.valueOf(bean.getDmQty())));
				rmQty = rmQty.add(BigDecimal.valueOf(Double.valueOf(bean.getRmQty())));
				totalAllocQty = totalAllocQty.add(bean.getTotalAllocQty());
				stock = stock.add(bean.getStock());

				i++;
			}
			tableDetail.append("<tr>").append("<td align='left' style='padding:5px;' height='10px'><b>Total</b></td>")
					.append("<td align='left' style='padding:5px;' height='10px'></td>")
					.append("<td align='center' style='padding:5px;' height='10px'><b>" + teQty + "</b></td>")
					.append("<td align='center' style='padding:5px;' height='10px'><b>" + dmQty + "</b></td>")
					.append("<td align='center' style='padding:5px;' height='10px'><b>" + rmQty + "</b></td>")
					.append("<td align='center' style='padding:5px;' height='10px'><b>" + totalAllocQty + "</b></td>")
					.append("<td align='center' style='padding:5px;' height='10px'><b>" + stock + "</b></td>")
					.append("</tr>");

			StringBuffer bottomTable = null;
			if (approvalDetails != null) {
				bottomTable = new StringBuffer("<br><table >").append("<tr>")
						.append("<td  width='100px'  align='left' style='padding-top:10px;'><b>Approved by:</b></td>");
				for (MailBean bean : approvalDetails) {
					bottomTable
							.append("<td  width='200px'  align='left' style='padding-top:10px;'><b>"
									+ bean.getApprovedBydesignation() + "</b>:" + bean.getApprovedByName() + "</td>")
							.append("<td  width='10px'  align='left' style='padding-top:10px;'>&nbsp;</td>");
				}
				bottomTable.append("</tr>").append("</table>");
			}

			body.append(tableHeader);
			body.append(tableDetail);
			body.append("</table>");
			if (bottomTable != null)
				body.append(bottomTable);
			body.append("\n\nThis is a system generated mail. Please do not reply.");
			body.append("</body></html> ");

			// inline images
			Map<String, String> inlineImages = new HashMap<String, String>();
			inlineImages.put("image1", null);

			if (body.length() > 0) {
				EmailFrom mailconfig = emailrepository.getEmailObject("EMAILFROM");
				this.sendHtmlMail(to_list, subject + " : " + document_no, body.toString(), null, mailconfig.getEmail(),
						mailconfig.getMail_password(), mailconfig.isAuth(), mailconfig.isTls_sls(),
						mailconfig.getHost(), mailconfig.getPort());
			}

		} catch (Exception e) {
			System.out.println("Error Occurred :" +e.getMessage());// uncomment asneeded --;
		}
		return "success";
	}

	public String sendMailForDispatch(List<DispatchMailBean> details, List<String> to_list, String status,
			String userName, String subject, String link, MailBean mailBean, String nextApprovar,
			List<MailBean> approvalDetails, String discardLink) throws Exception {

		try {
			final String approvalLink = companyMasterService.getCompanyDetails().getWeb_site();
			StringBuffer body = new StringBuffer();
			body.append("<html><body>The following  Allocation have been ");
			if (status.equals("F")) {
				body.append("generated");

			} else if (status.equals("A")) {
				body.append("approved by ");
				body.append("<b>");
				if (approvalDetails != null)
					body.append(approvalDetails.get(approvalDetails.size() - 1).getStarted_by_name());
				else
					body.append(userName);
				body.append(".</b>");

			} else if (status.equals("D")) {
				body.append("discarded by ");
				body.append("<b>");
				if (approvalDetails != null)
					body.append(approvalDetails.get(approvalDetails.size() - 1).getStarted_by_name());
				else
					body.append(userName);
				body.append(".</b>");
				subject = "Dispatch Discarded";

			} else {
				body.append("send for approved by ");
				body.append("<b>");
				if (approvalDetails != null)
					body.append(approvalDetails.get(approvalDetails.size() - 1).getStarted_by_name());
				else
					body.append(userName);
				body.append(".</b>");
			}
			if (nextApprovar != null) {
				// body.append("&nbsp;<br><label style='font-size:14px;color:blue;'> Actions
				// :</label><button style='background-color: green;'><a style='text-decoration:
				// none ;color: white;'
				// href='"+MedicoConstants.approvalLink+link+"';>Approve</a></button><button
				// style='background-color:red;color:white;' ><a style='text-decoration: none
				// ;color: white;'
				// href='"+MedicoConstants.approvalLink+"';>Deny</a></button><br>");
				body.append("&nbsp;<br><label> Actions :</label><a href='" + approvalLink + link
						+ "';>Approve</a>&nbsp;<a href='" + approvalLink + discardLink + "';>Deny</a><br>");
			} else {
				subject = "Dispatched";
			}
			StringBuffer tableHeader = new StringBuffer("<table style='background-color:powderblue;' >").append("<tr>")
					.append("<th  align='left' width='150px' style='padding:5px;'>FCode</th>")
					.append("<th  align='left' width='250px' style='padding:5px;'>Product Name</th>")
					.append("<th  align='center' width='100px' style='padding:5px;'>Dispatch Qty.</th>")
					.append("<th  align='center' width='100px' style='padding:5px;'>Recipient Name</th>")
					.append("<th  align='center' width='100px' style='padding:5px;' >Courier</th>")
					.append("<th  align='center' width='100px' style='padding:5px;' >Expected Date</th>")
					.append("<th  align='center' width='100px' style='padding:5px;' >Confirm Date</th>")
					.append("</tr>");

			StringBuffer tableDetail = new StringBuffer();

			Boolean ind = true;
			String document_no = null;
			int i = 0;
			for (DispatchMailBean bean : details) {
				if (ind == true) {
					document_no = bean.getDsp_no();
					body.append("<br><label><b>Dispatch No.    :</b>" + document_no
							+ "</label>&nbsp;&nbsp;;&nbsp;<label><b>  Address    :&nbsp;</b>" + bean.getAddress()
							+ "</label>&nbsp;&nbsp;<label><br><b> Started By    :&nbsp;</b>"
							+ mailBean.getStarted_by_name() + "</label><br>");
					ind = false;
				}
				tableDetail.append("<tr>")
						.append("<td align='left'  width='150px' style='padding:5px;'>" + bean.getSmp_erp_prod_cd()
								+ "</td>")
						.append("<td align='left'  width='250px' style='padding:5px;'>" + bean.getSmp_erp_prod_name()
								+ "</td>")
						.append("<td align='center'  width='100px' style='padding:5px;'>" + bean.getDsp_qty() + "</td>")
						.append("<td align='center'  width='100px' style='padding:5px;' >" + bean.getDoctor_name()
								+ "</td>")
						.append("<td align='center'  width='100px' style='padding:5px;;'>" + bean.getCourier_name()
								+ "</td>")
						.append("<td align='center'  width='100px' style='padding:5px;;'>" + bean.getExpected_date()
								+ "</td>")
						.append("<td align='center'  width='100px' style='padding:5px;;'>" + bean.getConfirm_date()
								+ "</td>")
						.append("</tr>");

				i++;
			}

			StringBuffer bottomTable = null;
			if (approvalDetails != null) {
				bottomTable = new StringBuffer("<br><table >").append("<tr>")
						.append("<td  width='100px'  align='left' style='padding-top:10px;'><b>Approved by:</b></td>");
				for (MailBean bean : approvalDetails) {
					bottomTable
							.append("<td  width='200px'  align='left' style='padding-top:10px;'><b>"
									+ bean.getApprovedBydesignation() + "</b>:" + bean.getApprovedByName() + "</td>")
							.append("<td  width='10px'  align='left' style='padding-top:10px;'>&nbsp;</td>");
				}
				bottomTable.append("</tr>").append("</table>");
			}

			body.append(tableHeader);
			body.append(tableDetail);
			body.append("</table>");
			if (bottomTable != null)
				body.append(bottomTable);
			body.append("\n\nThis is a system generated mail. Please do not reply.");
			body.append("</body></html> ");

			// inline images
			Map<String, String> inlineImages = new HashMap<String, String>();
			inlineImages.put("image1", null);

			if (body.length() > 0) {
				EmailFrom mailconfig = emailrepository.getEmailObject("EMAILFROM");
				this.sendHtmlMail(to_list, subject + " : " + document_no, body.toString(), null, mailconfig.getEmail(),
						mailconfig.getMail_password(), mailconfig.isAuth(), mailconfig.isTls_sls(),
						mailconfig.getHost(), mailconfig.getPort());
			}

		} catch (Exception e) {
			System.out.println("Error Occurred :" +e.getMessage());// uncomment asneeded --;
		}
		return "success";
	}

	public static void main(String argss[]) {
		Object[] obj1 = { "A", "B", "C" };
		Object[] obj2 = { "A1", "B1", "C1" };
		Object[] obj3 = { "A2", "B2", "C2" };
		List<Object> list = new ArrayList<Object>();
		list.add(obj1);
		list.add(obj2);
		list.add(obj3);
		System.err.println(Arrays.asList((String[]) list.toArray()));
		// System.err.println(Arrays.asList("AVC", "dfdf"));
	}

	public String sendApprovalMailForAnnualAllocation(List<AllocationBean> details, List<String> to_list, String status,
			String userName, String subject, String link, MailBean mailBean, String nextApprovar,
			List<MailBean> approvalDetails, String discardLink) throws Exception {

		try {
			final String approvalLink = companyMasterService.getCompanyDetails().getWeb_site();
			StringBuffer body = new StringBuffer();
			body.append("<html><body>The following Annual Sample Plan have been ");
			if (status.equals("F")) {
				body.append("generated");

			} else if (status.equals("A")) {
				body.append("approved by ");
				body.append("<b>");
				if (approvalDetails != null)
					body.append(approvalDetails.get(approvalDetails.size() - 1).getStarted_by_name());
				else
					body.append(userName);
				body.append(".</b>");

			} else if (status.equals("D")) {
				body.append("discarded by ");
				body.append("<b>");
				if (approvalDetails != null)
					body.append(approvalDetails.get(approvalDetails.size() - 1).getStarted_by_name());
				else
					body.append(userName);
				body.append(".</b>");
				subject = "Annual Sample Plan Discarded";

			} else {
				body.append("send for Approval by ");
				body.append("<b>");
				body.append(mailBean.getStarted_by_name());
				body.append(".</b><br>");
			}

			StringBuffer tableHeader = new StringBuffer("<table style='background-color:powderblue;' >").append("<tr>")
					.append("<th align='left' width='150px' style='padding:5px;'>Brand Name</th>")
					.append("<th align='right' width='100px' style='padding:5px;'>Value</th>")
					.append("<th align='right' width='100px' style='padding:5px;'>Units</th>")
					.append("<th align='center' width='100px' style='padding:5px;''>Avg/Month</th>")
					.append("<th align='center' width='100px' style='padding:5px;'>Frequency</th>").append("</tr>");

			StringBuffer tableDetail = new StringBuffer();

			Boolean ind = true;
			String document_no = details.get(0).getAsp_alloc_number();
			BigDecimal totalvalue = BigDecimal.ZERO;
			BigDecimal totalUnits = BigDecimal.ZERO;
			BigDecimal totalAverage = BigDecimal.ZERO;
			BigDecimal temp = BigDecimal.ZERO;
			for (AllocationBean bean : details) {

				tableDetail.append("<tr>")
						.append("<td align='left' width='150px' style='padding:5px;'>" + bean.getSa_group_name()
								+ "</td>")
						.append("<td align='right' width='100px' style='padding:5px;'>" + bean.getTotalCost() + "</td>")
						.append("<td align='right' width='100px' style='padding:5px;'>" + bean.getTotalUnits()
								+ "</td>")
						.append("<td align='center' width='100px' style='padding:5px;' >" + bean.getAvgMonth()
								+ "</td>")
						.append("<td align='center' width='100px' style='padding:5px;'>" + bean.getFrequency()
								+ "</td>")
						.append("</tr>");

				totalvalue = totalvalue.add(bean.getTotalCost());
				totalUnits = totalUnits.add(BigDecimal.valueOf(bean.getTotalUnits()));
				totalAverage = totalAverage.add(bean.getAvgMonth());
				temp = temp.add(BigDecimal.ONE);
			}
			System.out.println("totalAverage " + totalAverage);
			System.out.println("temp " + temp);
			tableDetail.append("<tr>")
					.append("<td align='left' style='padding:10px;padding-right:80px;' height='10px'><b>Total</b></td>")
					.append("<td align='right' width='50px' style='padding:5px;'><b>" + totalvalue + "</b></td>")
					.append("<td align='right' width='50px' style='padding:5px;'><b>" + totalUnits + "</b></td>")
					.append("<td align='center' width='50px' style='padding:5px;'><b>"
							+ totalAverage.divide(temp, 2, RoundingMode.HALF_UP) + "</b></td>")
					.append("<td align='center' style='padding:10px;padding-right:80px;' height='10px'></td>")
					.append("</tr>");

			StringBuffer bottomTable = null;
			if (approvalDetails != null) {
				bottomTable = new StringBuffer("<br><table >").append("<tr>")
						.append("<td  width='100px'  align='left' style='padding-top:10px;'><b>Approved by:</b></td>");
				for (MailBean bean : approvalDetails) {
					bottomTable
							.append("<td   align='left' style='padding-top:10px;'><b>" + bean.getApprovedBydesignation()
									+ "</b>:" + bean.getApprovedByName() + "</td>")
							.append("<td  align='left' style='padding-top:10px;'>&nbsp;</td>");
				}
				bottomTable.append("</tr>").append("<tr>")
						.append("<td  width='100px'  align='left' style='padding-top:10px;'><b>Remarks:</b></td>");
				for (MailBean bean : approvalDetails) {
					bottomTable
							.append("<td  align='left' style='padding-top:10px;'><b>" + bean.getApprovedBydesignation()
									+ "</b>:" + bean.getRemarks() + "</td>")
							.append("<td align='left' style='padding-top:10px;'>&nbsp;</td>");
				}
				bottomTable.append("</tr>").append("</table>");

			}
			body.append("<br><label><b>For Year : </b>" + details.get(0).getAsp_fin_year()
					+ "</label>&nbsp; <b>Created By :" + mailBean.getStarted_by_name() + "<br>");
			if (nextApprovar != null) {
				// body.append("<label style='font-size:14px;color:blue;'> Actions
				// :</label><button style='background-color: green;'><a style='text-decoration:
				// none ;color: white;'
				// href='"+MedicoConstants.approvalLink+link+"';>Approve</a></button><button
				// style='background-color:red;color:white;' ><a style='text-decoration: none
				// ;color: white;'
				// href='"+MedicoConstants.approvalLink+"';>Deny</a></button><br>");
				body.append("&nbsp;<label> Actions :</label><a href='" + approvalLink + link
						+ "';>Approve</a>&nbsp;<a href='" + approvalLink + discardLink + "';>Deny</a><br>");
			} else {
				subject = "Annual Sample Plan Approved";
				if (status.equals("D"))
					subject = "Annual Sample Plan Discarded";
			}

			body.append(tableHeader);
			body.append(tableDetail);
			body.append("</table>");
			if (approvalDetails != null)
				body.append(bottomTable);
			body.append("\n\nThis is a system generated mail. Please do not reply.");
			body.append("</body></html> ");

			// inline images
			Map<String, String> inlineImages = null;
			inlineImages = new HashMap<String, String>();
			if (!details.get(0).getFilePath().equals("0")) {
				inlineImages.put("image1", details.get(0).getFilePath());
			}

			if (body.length() > 0) {
				EmailFrom mailconfig = emailrepository.getEmailObject("EMAILFROM");
				this.sendHtmlMail(to_list, subject + " : " + document_no, body.toString(), inlineImages,
						mailconfig.getEmail(), mailconfig.getMail_password(), mailconfig.isAuth(),
						mailconfig.isTls_sls(), mailconfig.getHost(), mailconfig.getPort());
			}

		} catch (Exception e) {
			System.out.println("Error Occurred :" +e.getMessage());// uncomment asneeded --;
		}
		return "success";
	}

	public String sendApprovalMailForGRN(List<Object> details, List<String> to_list, String status, String userName,
			String subject, String link, MailBean mailBean, String nextApprovar, List<MailBean> approvalDetails,
			String iscardLink, String compcd) throws Exception {

		try {

			StringBuffer body = new StringBuffer();
			body.append("<html><body>The following GRN have been ");
			if (status.equals("F")) {
				body.append("generated");

			} else if (status.equals("A")) {
				body.append("approved by ");
				body.append("<b>");
				if (approvalDetails != null)
					body.append(approvalDetails.get(approvalDetails.size() - 1).getStarted_by_name());
				else
					body.append(userName);
				body.append(".</b>");

			} else if (status.equals("D")) {
				body.append("discarded by ");
				body.append("<b>");
				if (approvalDetails != null)
					body.append(approvalDetails.get(approvalDetails.size() - 1).getStarted_by_name());
				else
					body.append(userName);
				body.append(".</b>");
				subject = "GRN Discarded";

			} else {
				body.append("send for approval by ");
				body.append("<b>");
				body.append(mailBean.getStarted_by_name());
				body.append(".</b>");
			}
			if (nextApprovar != null) {
				// body.append("&nbsp;<br><label style='font-size:14px;color:blue;'> Actions
				// :</label><button style='background-color: green;'><a style='text-decoration:
				// none ;color: white;'
				// href='"+MedicoConstants.approvalLink+link+"';>Approve</a></button><button
				// style='background-color:red;color:white;' ><a style='text-decoration: none
				// ;color: white;'
				// href='"+MedicoConstants.approvalLink+discardLink+"';>Deny</a></button><br>");
				// body.append("&nbsp;<label> Actions :</label><a
				// href='"+MedicoConstants.approvalLink+link+"';>Approve</a>&nbsp;<a
				// href='"+MedicoConstants.approvalLink+discardLink+"';>Deny</a><br>");
			} else {
				subject = "GRN Approved";
			}
			StringBuffer tableHeader = new StringBuffer("<table style='background-color:powderblue;' >").append("<tr>")
					.append("<th  align='left' width='150px' style='padding:5px;'>Product Type</th>")
					.append("<th  align='centre' width='250px' style='padding:5px;'>Product Name</th>")
					.append("<th  align='centre' width='100px' style='padding:5px;'>Batch No.</th>");

			if (!compcd.equals(PAL)) {
				tableHeader.append("<th  align='right' width='100px' style='padding:5px;'>Qty</th>")
						.append("<th  align='right' width='100px' style='padding:5px;'>Rate</th>");

			}
			tableHeader.append("<th  align='right' width='100px' style='padding:5px;' >Value</th>").append("</tr>");

			StringBuffer tableDetail = new StringBuffer();

			Boolean ind = true;
			String document_no = "";
			BigDecimal totalvalue = BigDecimal.ZERO;
			BigDecimal totalUnits = BigDecimal.ZERO;
			int i = 0;
			for (Object object : details) {
				Object[] obj = (Object[]) object;
				if (ind == true) {
					body.append("<br><label><b>Date    :</b>" + obj[2]
							+ "</label>&nbsp;&nbsp;;&nbsp;<label><b>     GRN No    :&nbsp;</b>" + obj[1]
							+ "</label>&nbsp;&nbsp;;&nbsp;<label><b>     Challan No     :&nbsp;</b>" + obj[3]
							+ "</label>&nbsp;&nbsp;<label>;<br>");
					document_no = obj[1].toString();
					ind = false;
				}
				tableDetail.append("<tr>")
						.append("<td align='left'  width='150px' style='padding:5px;'>" + obj[7] + "</td>")
						.append("<td align='centre'  width='250px' style='padding:5px;'>" + obj[8] + "</td>")
						.append("<td align='centre'  width='100px' style='padding:5px;'>" + obj[9] + "</td>");

				if (!compcd.equals(PAL)) {
					tableDetail.append("<td align='right'  width='100px' style='padding:5px;' >" + obj[10] + "</td>")
							.append("<td align='right'  width='100px' style='padding:5px;'>" + obj[11] + "</td>");
				}
				tableDetail.append("<td align='right'  width='100px' style='padding:5px;;'>" + obj[12] + "</td>")
						.append("</tr>");

				totalvalue = totalvalue.add(BigDecimal.valueOf(Double.valueOf(obj[12].toString())));
				totalUnits = totalUnits.add(BigDecimal.valueOf(Double.valueOf(obj[10].toString())));
				i++;
			}
			tableDetail.append("<tr>").append("<td align='left' style='padding:5px;' height='10px'><b>Total</b></td>")
					.append("<td align='centre' style='padding:5px;' height='10px'></td>")
					.append("<td align='centre' style='padding:5px;' height='10px'></td>");

			if (!compcd.equals(PAL)) {
				tableDetail
						.append("<td align='right' style='padding:5px;' height='10px'><b>" + totalUnits + "</b></td>")
						.append("<td align='right' style='padding:5px;' height='10px'></td>");
			}
			tableDetail.append("<td align='right' style='padding:5px;' height='10px'><b>" + totalvalue + "</b></td>")
					.append("</tr>");

			StringBuffer bottomTable = null;
			if (approvalDetails != null) {
				bottomTable = new StringBuffer("<br><table >").append("<tr>")
						.append("<td  width='100px'  align='left' style='padding-top:10px;'><b>Approved by:</b></td>");
				for (MailBean bean : approvalDetails) {
					bottomTable
							.append("<td  width='200px'  align='left' style='padding-top:10px;'><b>"
									+ bean.getApprovedBydesignation() + "</b>:" + bean.getApprovedByName() + "</td>")
							.append("<td  width='10px'  align='left' style='padding-top:10px;'>&nbsp;</td>");
				}
				bottomTable.append("</tr>").append("</table>");
			}

			body.append(tableHeader);
			body.append(tableDetail);
			body.append("</table>");
			if (bottomTable != null)
				body.append(bottomTable);
			body.append("\n\nThis is a system generated mail. Please do not reply.");
			body.append("</body></html> ");

			// inline images
			Map<String, String> inlineImages = new HashMap<String, String>();
			inlineImages.put("image1", null);

			if (body.length() > 0) {
				EmailFrom mailconfig = emailrepository.getEmailObject("EMAILFROM");
				this.sendHtmlMail(to_list, subject + " : " + document_no, body.toString(), null, mailconfig.getEmail(),
						mailconfig.getMail_password(), mailconfig.isAuth(), mailconfig.isTls_sls(),
						mailconfig.getHost(), mailconfig.getPort());
			}

		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("Error Occurred :" +e.getMessage());// uncomment asneeded --;
		}
		return "success";
	}

	@SuppressWarnings("unused")
	public void sendHtmlMail(List<String> to_list, String subject, String body, Map<String, String> mapInlineImages,
			String mailusername, String mailpassword, String auth, String tls, String host, String port)
			throws Exception {

		// List<EmailFrom> email_frm = new
		// EmailFromDao().getParameteDetailsByCode(Constants.EMAILFROM);
		// EmailFrom senderDetails = email_frm.get(0);

		/*
		 * final String username = senderDetails.getEmail(); final String password =
		 * senderDetails.getMail_password();
		 */
		Properties props = new Properties();
		/*
		 * props.put("mail.smtp.auth",senderDetails.getAuth());
		 * props.put("mail.smtp.starttls.enable",senderDetails.getTls_ssl());
		 * props.put("mail.smtp.host",senderDetails.getHost());
		 * props.put("mail.smtp.port",senderDetails.getPort());
		 */

//			final String username = "excelmedico@gmail.com";
//			final String password = "excelmedico@123456";
//			props.put("mail.smtp.auth", "true");
//			props.put("mail.smtp.starttls.enable", "true");
//			props.put("mail.smtp.host", "smtp.gmail.com");
//			props.put("mail.smtp.port", "587");

		// final String username = "Pfizerindiasamplesno-reply@sampro-pfizerindia.com";
		// final String password = "mBg$ATRA#3vkK(";
		final String username = mailusername;
		final String password = mailpassword;
		props.put("mail.smtp.auth", auth);// true
		props.put("mail.smtp.starttls.enable", tls);// "true"
		props.put("mail.smtp.host", host);// mail.sampro-pfizerindia.com
		props.put("mail.smtp.port", port);// 587/25

		Session session = Session.getDefaultInstance(props, new javax.mail.Authenticator() {
			protected PasswordAuthentication getPasswordAuthentication() {
				return new PasswordAuthentication(username, password);
			}
		});

		try {
			MimeMessage message = new MimeMessage(session);
			message.setFrom(new InternetAddress(username));
			for (String to : to_list) {
				System.out.println(to);
				message.addRecipient(Message.RecipientType.TO, new InternetAddress(to));
				// message.addRecipients(arg0, arg1)
			}
			message.setSubject(subject);

			if (mapInlineImages != null) {

				// creates message part
				MimeBodyPart messageBodyPart = new MimeBodyPart();
				messageBodyPart.setContent(body, "text/html");
				// creates multi-part
				Multipart multipart = new MimeMultipart();
				multipart.addBodyPart(messageBodyPart);

				// adds inline image attachments
				if (mapInlineImages != null && mapInlineImages.size() > 0) {
					Set<String> setImageID = mapInlineImages.keySet();

					for (String contentId : setImageID) {
						MimeBodyPart imagePart = new MimeBodyPart();
						imagePart.setHeader("Content-ID", "<" + contentId + ">");
						imagePart.setDisposition(MimeBodyPart.INLINE);

						String imageFilePath = mapInlineImages.get(contentId);
						try {
							imagePart.attachFile(imageFilePath);
						} catch (IOException ex) {
							System.out.println("Error Occurred :" + new CodifyErrors().getErrorCode(ex));// uncomment
																											// asneeded
																											// --
																											// System.out.println("Error
																											// Occurred
																											// :" + new
																											// CodifyErrors().getErrorCode(e));//
																											// uncomment
																											// asneeded
																											// --;
						}

						multipart.addBodyPart(imagePart);
					}
				}
				message.setContent(multipart);
			} else {
				message.setContent(body, "text/html");
			}
			Transport.send(message);
			System.out.println("message sent....");
		} catch (MessagingException ex) {
			throw ex;
		}
	}

	public static void sendHtmlMail(List<String> toList, String subject, String body, String senderUsername,
			String senderPassword, String auth, String tls, String host, String port)
			throws MessagingException, Exception {
		if (toList != null && !toList.isEmpty()) {
			Properties props = new Properties();
			// final String username = "excelmedico@gmail.com";
			// final String password = "excelmedico@123456";

			final String username = senderUsername;
			final String password = senderPassword;

			props.put("mail.smtp.auth", auth);
			props.put("mail.smtp.starttls.enable", tls);
			props.put("mail.smtp.host", host);// "smtp.gmail.com"
			props.put("mail.smtp.port", port);// "587"

			Session session = Session.getDefaultInstance(props, new javax.mail.Authenticator() {
				protected PasswordAuthentication getPasswordAuthentication() {
					return new PasswordAuthentication(username, password);
				}
			});
			try {
				MimeMessage message = new MimeMessage(session);
				message.setFrom(new InternetAddress(username));
				for (String to : toList) {
					System.out.println("mail id " + to);
					message.addRecipient(Message.RecipientType.TO, new InternetAddress(to));
				}
				message.setSubject(subject);
				message.setContent(body, "text/html");
				Transport.send(message);
			} catch (MessagingException ex) {
				System.out.println("Error Occurred :" + new CodifyErrors().getErrorCode(ex));// uncomment asneeded --
																								// System.out.println("Error
																								// Occurred :" + new
																								// CodifyErrors().getErrorCode(e));//
																								// uncomment asneeded
																								// --;
				throw ex;
			}
		}
	}

	public String sendApprovalMailForGRN(List<Object> grnList, List<String> to_list, String status, String userName,
			String subject, MailBean mail, String nextApprovar) throws Exception {

		try {
			final String approvalLink = companyMasterService.getCompanyDetails().getWeb_site();
			StringBuffer body = new StringBuffer();
			/*
			 * StringBuffer tableHeader=new
			 * StringBuffer("<table border='1'><tr><th>GRN No.</th><th>GRN Date</th><th>LR No.</th>"
			 * )
			 * .append("<th>LR Date</th><th>STN No.</th><th>STN Date</th><th>Type</th><th>Entered By</th></tr>"
			 * );
			 */

			body.append("<html><body>The following GRN have been ");
			if (status.equals("F")) {
				body.append("generated, Please Log in to Medico Software to Approve or Discard.");

			} else if (status.equals("A")) {
				body.append("approved by ");
				body.append("<b>");
				body.append(userName);
				body.append(".</b>");

			} else if (status.equals("D")) {
				body.append("discarded by ");
				body.append("<b>");
				body.append(userName);
				body.append(".</b>");

			} else {
				body.append("send for approved by ");
				body.append("<b>");
				body.append(mail.getStarted_by_name());
				body.append(".</b>");
			}

			StringBuffer tableHeader = new StringBuffer("<table border='1' style='border-collapse: collapse;'>").append(
					"<tr style='background-color: #808080;color: white;'><th>GRN No.</th><th>GRN Date</th><th>Challan No</th><th colspan='2'>Supplier Name</th><th>Created by</th></tr>");

			StringBuffer tableDetail = new StringBuffer(
					"<tr><th>Product Type</th><th>Product Name</th><th>Art Work/Batch No.</th>")
							.append("<th>Qty.</th><th>Rate</th><th>Value</th></tr>");

			Boolean ind = true;
			for (Object object : grnList) {
				Object[] obj = (Object[]) object;
				if (ind) {
					tableHeader.append("<tr><td>").append(obj[1]).append("</td><td>").append(obj[2]).append("</td><td>")
							.append(obj[3]).append("</td><td colspan='2'>").append(obj[5]).append("</td><td>")
							.append(mail.getStarted_by_name()).append("</td></tr>");

					tableHeader.append("<tr><td style='font-weight:bold;text-align:center'>Remark</td><td colspan='5'>")
							.append(obj[15]).append("</td></tr>");

					ind = false;
				}

				tableDetail.append("<tr><td>").append(obj[7]).append("</td><td>").append(obj[8]).append("</td><td>")
						.append(obj[9]).append("</td><td align='right'>").append(obj[10])
						.append("</td><td align='right'>").append(obj[11]).append("</td><td align='right'>")
						.append(obj[12]).append("</td></tr>");
			}
			if (nextApprovar != null)
				body.append(
						"<br><button style='background-color: green;;'><a style='text-decoration: none ;color: white;' href='"
								+ approvalLink + mail.getLink() + ""
								+ "';>Approve</a></button> <button style='background-color: red ;'><a style='text-decoration: none ;color: white;' href='"
								+ approvalLink + "';>Discard</a></button>");
			body.append(tableHeader);
			body.append(tableDetail);
			body.append("</table>");
			body.append("\n\nThis is a system generated mail. Please do not reply.");
			body.append("</body></html> ");
			if (body.length() > 0) {
				EmailFrom mailconfig = emailrepository.getEmailObject("EMAILFROM");
				sendHtmlMail(to_list, subject, body.toString(), null, mailconfig.getEmail(),
						mailconfig.getMail_password(), mailconfig.isAuth(), mailconfig.isTls_sls(),
						mailconfig.getHost(), mailconfig.getPort());
			}

		} catch (Exception e) {
			System.out.println("Error Occurred :" +e.getMessage());// uncomment asneeded --;
		}
		return "success";
	}

	public String sendApprovalConfirmationOfMonthlyMail(List<AllocationBean> details, List<String> to_list, String link,
			String decision, String role, String subject, String sendBy, String discardLink) throws Exception {

		try {

			StringBuffer body = new StringBuffer();
			body.append("<html><body>The following  Allocation have been ");
			if (decision.equals("F")) {
				body.append("generated by " + sendBy + " Please login to system to see details and approve");

			} else if (decision.equals("A")) {
				body.append("approved by " + sendBy);
				body.append("<b>");
				// body.append(userName);
				body.append(".</b>");

			} else if (decision.equals("D")) {
				body.append("discarded by " + sendBy);
				body.append("<b>");
				// body.append(userName);
				body.append(".</b>");
				subject = "Monthly Allocation Discarded";

			}

			StringBuffer tableHeader = new StringBuffer("<table style='background-color:powderblue;' >").append("<tr>")
					.append("<th  align='left' width='150px' style='padding:5px;'>Product Name</th>")
					.append("<th  align='left' width='250px' style='padding:5px;'>Brand</th>")
					.append("<th  align='center' width='100px' style='padding:5px;'>TE</th>")
					.append("<th  align='center' width='100px' style='padding:5px;'>DM</th>")
					.append("<th  align='center' width='100px' style='padding:5px;' >RBM</th>")
					.append("<th  align='center' width='100px' style='padding:5px;' >Alloc. Qty</th>")
					.append("<th  align='center' width='100px' style='padding:5px;' >Stock</th>").append("</tr>");

			StringBuffer tableDetail = new StringBuffer();

			Boolean ind = true;
			String document_no = null;
			BigDecimal teQty = BigDecimal.ZERO;
			BigDecimal dmQty = BigDecimal.ZERO;
			BigDecimal rmQty = BigDecimal.ZERO;
			BigDecimal totalAllocQty = BigDecimal.ZERO;
			BigDecimal stock = BigDecimal.ZERO;
			int i = 0;
			for (AllocationBean bean : details) {
				if (ind == true) {
					document_no = bean.getAllocDocNo();
					// body.append("<br><label><b>Allocation No.
					// :</b>"+document_no+"</label>&nbsp;&nbsp;;&nbsp;<label><b> Allocation Month
					// :&nbsp;</b>"+bean.getAllocationMonth()+"</label>&nbsp;&nbsp;;&nbsp;<label><b>
					// Division :&nbsp;</b>"+bean.getDiv_disp_nm()+"</label>&nbsp;&nbsp;<label><b>
					// Started By :&nbsp;</b>"+mailBean.getStarted_by_name()+"</label><br>");
					ind = false;
				}
				tableDetail.append("<tr>")
						.append("<td align='left'  width='150px' style='padding:5px;'>" + bean.getSmp_prod_name()
								+ "</td>")
						.append("<td align='left'  width='250px' style='padding:5px;'>" + bean.getSa_group_name()
								+ "</td>")
						.append("<td align='center'  width='100px' style='padding:5px;'>" + bean.getTeQty() + "</td>")
						.append("<td align='center'  width='100px' style='padding:5px;' >" + bean.getDmQty() + "</td>")
						.append("<td align='center'  width='100px' style='padding:5px;'>" + bean.getRmQty() + "</td>")
						.append("<td align='center'  width='100px' style='padding:5px;;'>" + bean.getTotalAllocQty()
								+ "</td>")
						.append("<td align='center'  width='100px' style='padding:5px;;'>" + bean.getStock() + "</td>")
						.append("</tr>");

				teQty = teQty.add(BigDecimal.valueOf(Double.valueOf(bean.getTeQty())));
				dmQty = dmQty.add(BigDecimal.valueOf(Double.valueOf(bean.getDmQty())));
				rmQty = rmQty.add(BigDecimal.valueOf(Double.valueOf(bean.getRmQty())));
				totalAllocQty = totalAllocQty.add(bean.getTotalAllocQty());
				stock = stock.add(bean.getStock());

				i++;
			}
			tableDetail.append("<tr>").append("<td align='left' style='padding:5px;' height='10px'><b>Total</b></td>")
					.append("<td align='left' style='padding:5px;' height='10px'></td>")
					.append("<td align='center' style='padding:5px;' height='10px'><b>" + teQty + "</b></td>")
					.append("<td align='center' style='padding:5px;' height='10px'><b>" + dmQty + "</b></td>")
					.append("<td align='center' style='padding:5px;' height='10px'><b>" + rmQty + "</b></td>")
					.append("<td align='center' style='padding:5px;' height='10px'><b>" + totalAllocQty + "</b></td>")
					.append("<td align='center' style='padding:5px;' height='10px'><b>" + stock + "</b></td>")
					.append("</tr>");

//				if(decision.equals("F"))
//					body.append("<br><button style='background-color: green;;'><a style='text-decoration: none ;color: white;' href='"+approvalLink+link+""
//						+ "';>Approve</a></button> <button style='background-color: red ;'><a style='text-decoration: none ;color: white;' href='"+approvalLink+discardLink+"';>Discard</a></button>");
			body.append(tableHeader);
			body.append(tableDetail);
			body.append("</table>");
			body.append("\n\nThis is a system generated mail. Please do not reply.");
			body.append("</body></html> ");

			// inline images
			Map<String, String> inlineImages = new HashMap<String, String>();
			inlineImages.put("image1", null);

			if (body.length() > 0) {
				EmailFrom mailconfig = emailrepository.getEmailObject("EMAILFROM");
				this.sendHtmlMail(to_list, subject + " : " + document_no, body.toString(), null, mailconfig.getEmail(),
						mailconfig.getMail_password(), mailconfig.isAuth(), mailconfig.isTls_sls(),
						mailconfig.getHost(), mailconfig.getPort());
			}

		} catch (Exception e) {
			System.out.println("Error Occurred :" +e.getMessage());// uncomment asneeded --;
		}
		return "success";
	}

	public String sendApprovalConfirmationMailForAnnualPlan(List<AllocationBean> details, List<String> to_list,
			String link, String decision, String role, String subject, String sendBy, String discardLink)
			throws Exception {

		try {

			StringBuffer body = new StringBuffer();
			body.append("<html><body>The following  Annual Sample Plan  have been ");
			if (decision.equals("F")) {
				body.append("generated by " + sendBy + " Please login to system to see details and approve");

			} else if (decision.equals("A")) {
				body.append("approved by " + sendBy);
				body.append("<b>");
				// body.append(userName);
				body.append(".</b>");

			} else if (decision.equals("D")) {
				body.append("discarded by " + sendBy);
				body.append("<b>");
				// body.append(userName);
				body.append(".</b>");
				subject = "Monthly Annual Sample Plan  Discarded";

			}
			StringBuffer tableHeader = new StringBuffer("<table style='background-color:powderblue;' >").append("<tr>")
					.append("<th align='left' width='150px' style='padding:5px;'>Brand Name</th>")
					.append("<th align='right' width='100px' style='padding:5px;'>Value</th>")
					.append("<th align='right' width='100px' style='padding:5px;'>Units</th>")
					.append("<th align='center' width='100px' style='padding:5px;''>Avg/Month</th>")
					.append("<th align='center' width='100px' style='padding:5px;'>Frequency</th>").append("</tr>");

			StringBuffer tableDetail = new StringBuffer();

			Boolean ind = true;
			String document_no = details.get(0).getAsp_alloc_number();
			BigDecimal totalvalue = BigDecimal.ZERO;
			BigDecimal totalUnits = BigDecimal.ZERO;
			BigDecimal totalAverage = BigDecimal.ZERO;
			BigDecimal temp = BigDecimal.ZERO;
			for (AllocationBean bean : details) {

				tableDetail.append("<tr>")
						.append("<td align='left' width='150px' style='padding:5px;'>" + bean.getSa_group_name()
								+ "</td>")
						.append("<td align='right' width='100px' style='padding:5px;'>" + bean.getTotalCost() + "</td>")
						.append("<td align='right' width='100px' style='padding:5px;'>" + bean.getTotalUnits()
								+ "</td>")
						.append("<td align='center' width='100px' style='padding:5px;' >" + bean.getAvgMonth()
								+ "</td>")
						.append("<td align='center' width='100px' style='padding:5px;'>" + bean.getFrequency()
								+ "</td>")
						.append("</tr>");

				totalvalue = totalvalue.add(bean.getTotalCost());
				totalUnits = totalUnits.add(BigDecimal.valueOf(bean.getTotalUnits()));
				totalAverage = totalAverage.add(bean.getAvgMonth());
				temp = temp.add(BigDecimal.ONE);
			}
			tableDetail.append("<tr>")
					.append("<td align='left' style='padding:10px;padding-right:80px;' height='10px'><b>Total</b></td>")
					.append("<td align='right' width='50px' style='padding:5px;'><b>" + totalvalue + "</b></td>")
					.append("<td align='right' width='50px' style='padding:5px;'><b>" + totalUnits + "</b></td>")
					.append("<td align='center' width='50px' style='padding:5px;'><b>" + totalAverage.divide(temp)
							+ "</b></td>")
					.append("<td align='center' style='padding:10px;padding-right:80px;' height='10px'></td>")
					.append("</tr>");

			body.append("<br><label><b>For Year : </b>" + details.get(0).getAsp_fin_year()
					+ "</label>&nbsp; <b>Created By :" + sendBy + "<br>");
//				if(decision.equals("F"))
//					body.append("<br><button style='background-color: green;;'><a style='text-decoration: none ;color: white;' href='"+approvalLink+link+""
//						+ "';>Approve</a></button> <button style='background-color: red ;'><a style='text-decoration: none ;color: white;' href='"+approvalLink+discardLink+"';>Discard</a></button>");
			body.append(tableHeader);
			body.append(tableDetail);
			body.append("</table>");
			body.append("\n\nThis is a system generated mail. Please do not reply.");
			body.append("</body></html> ");

			// inline images
			Map<String, String> inlineImages = new HashMap<String, String>();
			inlineImages.put("image1", null);

			if (body.length() > 0) {
				EmailFrom mailconfig = emailrepository.getEmailObject("EMAILFROM");
				this.sendHtmlMail(to_list, subject + " : " + document_no, body.toString(), null, mailconfig.getEmail(),
						mailconfig.getMail_password(), mailconfig.isAuth(), mailconfig.isTls_sls(),
						mailconfig.getHost(), mailconfig.getPort());
			}

		} catch (Exception e) {
			System.out.println("Error Occurred :" +e.getMessage());// uncomment asneeded --;
		}
		return "success";
	}

//	    public String sendApprovalMailForMonthlyAllocation(List<AllocationBean> allocDetails, List<String> to_list, String status, String userName, String subject,String link,MailBean mailBean,String nextApprovar) throws Exception {
//			
//			try {
//
//				StringBuffer body=new StringBuffer();
//				body.append("<html><body>The following Monthly Allocation have been ");
//				if (status.equals("F")) {
//					body.append("generated");
//					
//				} else if (status.equals("A")) {
//					body.append("approved by ");
//					body.append("<b>");
//					body.append(userName);
//					body.append(".</b>");
//					
//				} else if (status.equals("D")) {
//					body.append("discarded by ");
//					body.append("<b>");
//					body.append(userName);
//					body.append(".</b>");
//					
//				} else {
//					body.append("send for rectification by ");
//					body.append("<b>");
//					body.append(mailBean.getStarted_by_name());
//					body.append(".</b>");
//				}
//				
//				StringBuffer tableHeader=new StringBuffer("<table border='1' style='border-collapse: collapse;'>")
//				.append("<tr style='background-color: #699AE6 ;color: white;'><th>Allocation No.</th><th>Allocation Month</th><th colspan='2'>Division</th><th colspan='4'>Created by</th></tr>");
//				
//				StringBuffer tableDetail = new StringBuffer("<tr><th>Product Name</th><th>Brand</th>")
//							.append("<th>TE</th><th>DM</th><th>RBM</th><th>Plan Qty</th><th>Stock</th></tr>");
//					
//				Boolean ind = true;
//				String document_no="";
//				for(AllocationBean bean : allocDetails){
//					if(ind){
//						document_no=bean.getAllocDocNo();
//						tableHeader.append("<tr><td>")
//						.append(bean.getAllocDocNo()).append("</td><td>")
//									.append(bean.getAllocationMonth()).append("</td><td colspan='2'>")
//									.append(bean.getDiv_disp_nm()).append("</td><td colspan='4'>")
//									.append(mailBean.getStarted_by_name()).append("</td></tr>");
//									
//						ind=false;
//					}
//					
//					tableDetail.append("<tr><td>")
//								.append(bean.getSmp_prod_name()).append("</td><td>")
//								.append(bean.getSa_group_name()).append("</td><td align='right'>")
//								.append(bean.getTeQty()).append("</td><td align='right'>")
//								.append(bean.getDmQty()).append("</td><td align='right'>")
//								.append(bean.getRmQty()).append("</td><td align='right'>")
//								.append(bean.getTotalAllocQty()).append("</td><td align='right'>")
//								.append(bean.getStock()).append("</td></tr>");
//				}
//				if(nextApprovar!= null)
//					body.append("<br><button style='background-color: green;;'><a style='text-decoration: none ;color: white;' href='"+approvalLink+link+""
//						+ "';>Approve</a></button> <button style='background-color: red ;'><a style='text-decoration: none ;color: white;' href='"+approvalLink+"';>Discard</a></button>");
//				body.append(tableHeader);
//				body.append(tableDetail);
//				body.append("</table>");
//				body.append("\n\nThis is a system generated mail. Please do not reply.");
//				body.append("</body></html> ");
//				if(body.length()>0){
//					sendHtmlMail(to_list, subject+" : "+document_no, body.toString(),null);
//				}
//				
//			} catch (Exception e) {
//				System.out.println("Error Occurred :" +e.getMessage());// uncomment asneeded --;
//			}
//			return "success";
//		}
//	  

	public String resignedFieldstaffDispatch(List<Resign_Notification> details, String subject, String userMail)
			throws Exception {

		try {

			StringBuffer body = new StringBuffer();
			body.append(
					"<html><body>The following Fieldstaff are resigned and dispatches has been genereated for them.");

			StringBuffer tableHeader = new StringBuffer("<table style='background-color:powderblue;' >").append("<tr>")
					.append("<th align='left' width='150px' style='padding:5px;'>Staff Name</th>")
					.append("<th align='right' width='100px' style='padding:5px;'>Employee No</th>")
					.append("<th align='right' width='100px' style='padding:5px;'>Challan Date</th>")
					.append("<th align='center' width='100px' style='padding:5px;''>Challan No</th>")
					.append("<th align='center' width='100px' style='padding:5px;'>Transporter</th>").append("</tr>");

			StringBuffer tableDetail = new StringBuffer();

			Boolean ind = true;
			for (Resign_Notification bean : details) {

				tableDetail.append("<tr>")
						.append("<td align='left' width='150px' style='padding:5px;'>" + bean.getDsp_fstaff_name()
								+ "</td>")
						.append("<td align='right' width='100px' style='padding:5px;'>" + bean.getEmployee_no()
								+ "</td>")
						.append("<td align='right' width='100px' style='padding:5px;'>" + bean.getChallan_date()
								+ "</td>")
						.append("<td align='center' width='100px' style='padding:5px;' >" + bean.getChallan_no()
								+ "</td>")
						.append("<td align='center' width='100px' style='padding:5px;'>" + bean.getTransporter()
								+ "</td>")
						.append("</tr>");

			}
			StringBuffer bottomTable = null;

			subject = "Alert ! Dispatches created for resigned fieldstaff";
			body.append(tableHeader);
			body.append(tableDetail);
			body.append("</table>");
			body.append("\n\nThis is a system generated mail. Please do not reply.");
			body.append("</body></html> ");

			// inline images
			Map<String, String> inlineImages = null;
			inlineImages = new HashMap<String, String>();

			if (body.length() > 0) {
				EmailFrom mailconfig = emailrepository.getEmailObject("EMAILFROM");
				this.sendHtmlMail(Arrays.asList(userMail), subject, body.toString(), inlineImages,
						mailconfig.getEmail(), mailconfig.getMail_password(), mailconfig.isAuth(),
						mailconfig.isTls_sls(), mailconfig.getHost(), mailconfig.getPort());
			}

		} catch (Exception e) {
			System.out.println("Error Occurred :" +e.getMessage());// uncomment asneeded --;
		}
		return "success";
	}

	public String uploadDoctorMail(List<Pg_Doc_Alloc_Template> list, List<Pg_Doc_Alloc_Template_Error> errorlist,
			String subject, String[] userMail, String company, String filePath) throws Exception {

		try {

			StringBuffer body = new StringBuffer();
			body.append("<html><body>The following Allocations are uploaded allocations.");
			if (errorlist != null && errorlist.size() > 0) {
				StringBuffer tableHeader = new StringBuffer("<table style='background-color:powderblue;' >")
						.append("<tr>").append("<th align='left' width='150px' style='padding:5px;'>Doctor Name</th>")
						.append("<th align='right' width='100px' style='padding:5px;'>Speciality</th>")
						.append("<th align='right' width='100px' style='padding:5px;'>Address</th>")
						.append("<th align='center' width='100px' style='padding:5px;''>City</th>")
						.append("<th align='center' width='100px' style='padding:5px;'>Pin Code</th>")
						.append("<th align='center' width='100px' style='padding:5px;'>Mobile</th>")
						.append("<th align='center' width='100px' style='padding:5px;'>Error Date</th>")
						.append("<th align='center' width='100px' style='padding:5px;'>Error Message</th>")
						.append("</tr>");

				StringBuffer tableDetail = new StringBuffer();

				Boolean ind = true;
				for (Pg_Doc_Alloc_Template_Error bean : errorlist) {

					tableDetail.append("<tr>")
							.append("<td align='left' width='150px' style='padding:5px;'>" + bean.getHcpname()
									+ "</td>")
							.append("<td align='right' width='100px' style='padding:5px;'>" + bean.getSpeciality()
									+ "</td>")
							.append("<td align='right' width='100px' style='padding:5px;'>" + bean.getAddress()
									+ "</td>")
							.append("<td align='center' width='100px' style='padding:5px;' >" + bean.getCity()
									+ "</td>")
							.append("<td align='center' width='100px' style='padding:5px;'>" + bean.getPincode()
									+ "</td>")
							.append("<td align='center' width='100px' style='padding:5px;'>" + bean.getMobile()
									+ "</td>")
							.append("<td align='center' width='100px' style='padding:5px;'>" + bean.getError_date()
									+ "</td>")
							.append("<td align='center' width='100px' style='padding:5px;'>" + bean.getError_msg()
									+ "</td>")
							.append("</tr>");

				}

				StringBuffer bottomTable = null;

				// subject="Alert ! Dispatches created for resigned fieldstaff";
				body.append(tableHeader);
				body.append(tableDetail);
				body.append("</table>");
			}
			body.append("\n\nThis is a system generated mail. Please do not reply.");
			body.append("</body></html> ");

			// inline images
			Map<String, String> inlineImages = null;
			inlineImages = new HashMap<String, String>();

			System.out.println("List ::::: " + list.size());
			System.out.println("errorlist ::::: " + errorlist.size());
			System.out.println("company ::::: " + company);

			// String filepath = allocationService.generateDoctorUploadReport(list,
			// errorlist, company);
			System.out.println("filepath : " + filePath);
			inlineImages.put("filename", filePath);
			if (body.length() > 0) {
				EmailFrom mailconfig = emailrepository.getEmailObject("EMAILFROM");
				this.sendHtmlMail(Arrays.asList(userMail), subject, body.toString(), inlineImages,
						mailconfig.getEmail(), mailconfig.getMail_password(), mailconfig.isAuth(),
						mailconfig.isTls_sls(), mailconfig.getHost(), mailconfig.getPort());
			}

		} catch (Exception e) {
			throw e;
		}
		return "success";
	}

	public void grnDiscardNotificationMail(List<String> toList, String subject) throws Exception {
		StringBuffer body = new StringBuffer();
		body.append("<html><body>");
		body.append("\n\nThis is a system generated mail. Please do not reply.");
		body.append("</body>");

		if (body.length() > 0) {
			EmailFrom mailconfig = emailrepository.getEmailObject("EMAILFROM");
			this.sendHtmlMail(toList, subject, body.toString(), null, mailconfig.getEmail(),
					mailconfig.getMail_password(), mailconfig.isAuth(), mailconfig.isTls_sls(), mailconfig.getHost(),
					mailconfig.getPort());
		}
	}

	public static void sendHtmlExcelMedicoMail(List<String> toList, String subject, String body, String senderUsername,
			String senderPassword, String auth, String tls, String host, String port)
			throws MessagingException, Exception {
		if (toList != null && !toList.isEmpty()) {
			Properties props = new Properties();
			// final String username = "excelmedico@gmail.com";
			// final String password = "excelmedico@123456";

//			final String username = senderUsername;
//			final String password = senderPassword;

//			props.put("mail.smtp.auth", auth);
//			props.put("mail.smtp.starttls.enable",tls);
//			props.put("mail.smtp.host", host);//"smtp.gmail.com"
//			props.put("mail.smtp.port", port);//"587"

			final String username = "excelmedico@gmail.com";
			final String password = "excelmedico@123456";
			props.put("mail.smtp.auth", "true");
			props.put("mail.smtp.starttls.enable", "true");
			props.put("mail.smtp.host", "smtp.gmail.com");
			props.put("mail.smtp.port", "587");

			Session session = Session.getDefaultInstance(props, new javax.mail.Authenticator() {
				protected PasswordAuthentication getPasswordAuthentication() {
					return new PasswordAuthentication(username, password);
				}
			});
			try {
				MimeMessage message = new MimeMessage(session);
				message.setFrom(new InternetAddress(username));
				for (String to : toList) {
					System.out.println("mail id " + to);
					message.addRecipient(Message.RecipientType.TO, new InternetAddress(to));
				}
				message.setSubject(subject);
				message.setContent(body, "text/html");
				Transport.send(message);
			} catch (MessagingException ex) {
				System.out.println("Error Occurred :" + new CodifyErrors().getErrorCode(ex));// uncomment asneeded --
																								// System.out.println("Error
																								// Occurred :" + new
																								// CodifyErrors().getErrorCode(e));//
																								// uncomment asneeded
																								// --;
				throw ex;
			}
		}
	}

	// Only For Veto LR Mails
	public void sendExcelMail(List<String> to_list, String subject, String body, byte[] outputStream, String rptName,
			String addCC, String divCode) throws Exception {
		System.out.println("Div Code Received " + divCode);
		System.out.println("Mail.sendExcelMail()-----------------------");
		EmailFrom mailconfig = emailrepository.getEmailObject("EMAILFROM");
		List<String> emailList = new ArrayList<String>();
		final String username = mailconfig.getEmail();
		final String password = mailconfig.getMail_password();
		Properties props = new Properties();

		List<Email> cc_list = emailrepository.getEmailList(DSPREPORTCC, divCode);
		props.put("mail.smtp.auth", mailconfig.isAuth());
		props.put("mail.smtp.starttls.enable", mailconfig.isTls_sls());
		props.put("mail.smtp.host", mailconfig.getHost());
		props.put("mail.smtp.port", mailconfig.getPort());
		// props.put("mail.smtp.starttls.enable", "true");

		Session session = Session.getDefaultInstance(props, new javax.mail.Authenticator() {
			@Override
			protected PasswordAuthentication getPasswordAuthentication() {
				return new PasswordAuthentication(username, password);
			}
		});
		try {
			MimeMessage message = new MimeMessage(session);
			message.setFrom(new InternetAddress(username));

			System.out.println("From Mail" + username);

			for (String to : to_list) {
				System.out.println("%$#$#$#$#$$$#$#$#$Mail.sendExcelMail()**************" + to);
				message.addRecipient(Message.RecipientType.TO, new InternetAddress(to));
			}
			if (addCC.equalsIgnoreCase("Y")) {
				// List<Email> ccdao = new
				// Email_Dao().getCcEmailsForThisDivision(Constants.DSPREPORTCC,divCode.trim());
				// List<String> cc_list = new ArrayList<String>();
				for (Email email : cc_list) {
					emailList.add(email.getEmail()); // For Field Staff Email
				}
				for (String cc : emailList) {
					System.out.println("%$#$#$#$#$$$#$#$#$Mail.sendCC()**************" + cc);
					message.addRecipient(Message.RecipientType.CC, new InternetAddress(cc));
				}
			}
			message.setSubject(subject);
			// Create the message part
			BodyPart messageBodyPart = new MimeBodyPart();
			// Fill the message
			messageBodyPart.setText(body);
			// Create a multipar message
			Multipart multipart = new MimeMultipart();
			MimeBodyPart htmlPart = new MimeBodyPart();
			htmlPart.setContent(
					"Note: Do not reply to this e-mail. For any query / clarification please write to Head Office. ",
					"text/html");
			multipart.addBodyPart(htmlPart);

			MimeBodyPart attachment = new MimeBodyPart();
			attachment.setFileName(rptName + ".xls");
			attachment.setContent(outputStream, "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
			multipart.addBodyPart(attachment);
			try {
				message.setContent(multipart);

				Transport.send(message);
			} catch (Exception e) {
				System.out.println("Error Occurred :" +e.getMessage());// uncomment asneeded --;
			} finally {
				// fileInputStream.close();
			}
			System.out.println("message sent....");
		} catch (MessagingException ex) {
			System.out.println("Error Occurred :" + new CodifyErrors().getErrorCode(ex));// uncomment asneeded --
		}
	}

	public String SendUserCreationMail(String userfname, String username, String temppass, String role, String email)
			throws Exception {
		String msg = null;
		try {
			StringBuffer body = new StringBuffer();
			body.append("<html><body>");
			body.append(" Dear " + userfname + ",<br> <br>");
			body.append("<b> Welcome to Medico SG! </b><br><br>");
			body.append(" A new user has been created for you. <br><br>");

			body.append("<table style='border-collapse: collapse;border: none;' >");
			body.append("<tr>");
			body.append("<td><b>Your Role : </b></td><td>" + role + "</td> ");
			body.append("</tr>");

			body.append("<tr>");
			body.append("<td><b>Your Username :</b></td><td>" + username + "</td>");
			body.append("</tr>");

			body.append("<tr>");
			body.append("<td><b>Your Temporary Password :</b></td><td>" + temppass + "</td>");
			body.append("</tr>");
			body.append("</table><br>");

			body.append(
					"<p>You can use your username and the temporary password to log in for the first time after which you will be prompted to enter your own password.</p>");
			body.append("<p>Your password needs to conform to the security rules enforced. </p>");
			body.append(
					"<p>The password rules will be shown to you while creating the password for your guidance.</p>");
			body.append("<p>Please ensure that you create a strong password and never share it with anyone else. </p>");

			body.append("<br>");

			body.append("<b>Thanks and regards</b><br>");
			body.append("<b>Admin team</b><br>");
			body.append("<b>Medico SG</b><br>");

			body.append("</body></html>");
			msg = body.toString();

		} catch (Exception e) {
			// TODO: handle exception
			throw e;
		}
		return msg;
	}

	public String SendIAACreationMail(List<IaaDetailForMail> list, String createdBy) throws Exception {
		String msg = null;
		try {
			StringBuffer sb = new StringBuffer();

			sb.append("<html>");
			sb.append("<body>");
			sb.append("<p>Hello User,</p>");
			sb.append("<p>IAA Has been created with following</p>");

			sb.append("<table style='border-collapse: collapse;'>");
			sb.append("<tr>");
			sb.append("<th>IAA No :</th> ");
			sb.append("<td>" + list.get(0).getStkadj_no() + " &nbsp;</td> ");
			sb.append(" <th>Location :</th> ");
			sb.append("<td>" + list.get(0).getLoc_nm() + " &nbsp;</td>");
			sb.append("<th>Date : </th> ");
			sb.append("<td>" + list.get(0).getSadj_dt() + " &nbsp;</td>");
			sb.append("<th>Created by :</th>");
			sb.append("<td>" + createdBy + "&nbsp;</td>");
			sb.append(" </tr>");
			sb.append("</table>");

			sb.append("<table border ='1' style='border-collapse: collapse;'>");
			sb.append("<tr>");
			sb.append("<th><b>Product Type</b></th> ");
			sb.append("<th><b>Product Code</b></th>");
			sb.append("<th><b>Product Name</b></th>");
			sb.append("<th><b>Batch no</b></th>");
			sb.append("<th><b>Stock Type Desc</b></th>");
			sb.append("<th><b>Out Qty</b></th>");
			sb.append("<th><b>In Qty</b></th>");
			sb.append("<th><b>Reason</b></th>");
			sb.append("<th><b>Remarks</b></th>");
			sb.append("</tr>");

			Long newadjdtl = 0l;
			Long oldadjdtl = 0l;
			for (IaaDetailForMail obj : list) {
				newadjdtl = obj.getAdjdtl_id();
				if (newadjdtl.compareTo(oldadjdtl) != 0 && oldadjdtl.compareTo(0l) != 0) {
					sb.append("<tr>");
					sb.append("<td colspan ='9'>&nbsp;</td>");
					sb.append("</tr>");
				}

				sb.append("<tr>");
				sb.append("<td>" + obj.getSmp_prod_type() + "</td> ");
				sb.append("<td>" + obj.getSmp_prod_cd() + "</td>");
				sb.append("<td>" + obj.getSmp_prod_name() + "</td>");
				sb.append("<td>" + obj.getBatch_no() + "</td>");
				sb.append("<td>" + obj.getStock_type_desc() + "</td>");
				sb.append("<td align='right'>" + obj.getOut_qty() + "</td>");
				sb.append("<td align='right'>" + obj.getIn_qty() + "</td>");
				sb.append("<td>" + obj.getReason() + "</td>");
				sb.append("<td>" + obj.getRemarks() + "</td>");
				sb.append("</tr>");

				oldadjdtl = newadjdtl;
			}
			sb.append("</table><br>");

			sb.append("<p>This is System Generated Mail Please Do not Reply</p>");
			sb.append("<p><b>Regards,</b></p>");
			sb.append("<p><b>Medico Team</b></p>");
			sb.append("</body>");
			sb.append("</html>");

			msg = sb.toString();
		} catch (Exception e) {
			// TODO: handle exception
			throw e;
		}
		return msg;
	}

	public String sendApprovalMailForIaa(List<IaaDetailForApprovalMail> list, List<String> to_list, String status,
			String userName, String subject, MailBean mailBean, String nextApprovar, List<MailBean> approvalDetails)
			throws Exception {
		StringBuffer sb = new StringBuffer();
		try {
			sb.append("<html>");
			sb.append("<body>");
			sb.append("<p>Hello User,</p>");

			sb.append("<p>IAA with following</p>");

			sb.append("<table style='border-collapse: collapse;'>");
			sb.append("<tr>");
			sb.append("<th>IAA No :</th> ");
			sb.append("<td>" + list.get(0).getStkadj_no() + " &nbsp;</td> ");
			sb.append(" <th>Location :</th> ");
			sb.append("<td>" + list.get(0).getLoc_nm() + " &nbsp;</td>");
			sb.append("<th>Date : </th> ");
			sb.append("<td>" + list.get(0).getSadj_dt() + " &nbsp;</td>");
			sb.append("<th>Approved by :</th>");
			sb.append("<td>" + approvalDetails.get(approvalDetails.size() - 1).getStarted_by_name() + "&nbsp;</td>");
			sb.append(" </tr>");
			sb.append("</table>");

			sb.append("<table border ='1' style='border-collapse: collapse;'>");
			sb.append("<tr>");
			sb.append("<th><b>Product Type</b></th> ");
			sb.append("<th><b>Product Div.</b></th>");
			sb.append("<th><b>Product Code</b></th>");
			sb.append("<th><b>Product Name</b></th>");
			sb.append("<th><b>Batch no</b></th>");
			sb.append("<th><b>Batch Exp. Dt.</b></th>");
			sb.append("<th><b>Stock Type Desc</b></th>");
			sb.append("<th><b>Out Qty</b></th>");
			sb.append("<th><b>In Qty</b></th>");
			sb.append("<th><b>Reason</b></th>");
			sb.append("<th><b>Remarks</b></th>");
			sb.append("<th><b>Approval Status</b></th>");
			sb.append("</tr>");

			Long newadjdtl = 0l;
			Long oldadjdtl = 0l;
			for (IaaDetailForApprovalMail obj : list) {
				newadjdtl = obj.getAdjdtl_id();
				if (newadjdtl.compareTo(oldadjdtl) != 0 && oldadjdtl.compareTo(0l) != 0) {
					sb.append("<tr>");
					sb.append("<td colspan ='9'>&nbsp;</td>");
					sb.append("</tr>");
				}

				sb.append("<tr>");
				sb.append("<td>" + obj.getSmp_prod_type() + "</td> ");
				sb.append("<td>"+obj.getDiv_disp_nm()+"</td>");
				sb.append("<td>" + obj.getSmp_prod_cd() + "</td>");
				sb.append("<td>" + obj.getSmp_prod_name() + "</td>");
				sb.append("<td>" + obj.getBatch_no() + "</td>");
				sb.append("<td>"+obj.getBatch_expiry_dt()+"</td>");
				sb.append("<td>" + obj.getStock_type_desc() + "</td>");
				sb.append("<td align='right'>" + obj.getOut_qty() + "</td>");
				sb.append("<td align='right'>" + obj.getIn_qty() + "</td>");
				sb.append("<td>" + obj.getReason() + "</td>");
				sb.append("<td>" + obj.getRemarks() + "</td>");
				sb.append("<td>" + obj.getStkadj_appr_status() != null
						? obj.getStkadj_appr_status().trim().equalsIgnoreCase("A") ? "<b>Approved</b>"
								: "<b>Discard</b>"
						: "<b>ERROR</b>" + "</td>");
				sb.append("</tr>");

				oldadjdtl = newadjdtl;
			}
			sb.append("</table><br>");

			sb.append("<p>This is System Generated Mail Please Do not Reply</p>");
			sb.append("<p><b>Regards,</b></p>");
			sb.append("<p><b>Medico Team</b></p>");
			sb.append("</body>");
			sb.append("</html>");

			// inline images
			Map<String, String> inlineImages = new HashMap<String, String>();
			inlineImages.put("image1", null);

			if (sb.toString().length() > 0) {
				EmailFrom mailconfig = emailrepository.getEmailObject("EMAILFROM");
				this.sendHtmlMail(to_list, subject + " : " + list.get(0).getStkadj_no(), sb.toString().toString(), null,
						mailconfig.getEmail(), mailconfig.getMail_password(), mailconfig.isAuth(),
						mailconfig.isTls_sls(), mailconfig.getHost(), mailconfig.getPort());
			}
		} catch (Exception e) {
			// TODO: handle exception
			throw e;
		}
		return "sucess";

	}
	
	
	 private SimpleDateFormat dtformat = new SimpleDateFormat("dd/mm/yyyy");
	 private SimpleDateFormat dtparse = new SimpleDateFormat("yyyy-mm-dd");
	 
	 public String sendFinalApprovalMailForGRN(List<Object> details, List<String> to_list, String status,
			 String userName, String subject,String compcd)  throws Exception{
		 
		 try {
			 Object[] headerTable = (Object[])details.get(0);
			 StringBuffer body=new StringBuffer();
			 body.append("<html><body>");
			 
			 body.append("<br><br>");
			 
			 body.append("<table  style='border-collapse:collapse;border:1px solid black;'>");
			 body.append("<tr style='border:1px solid black;'><td colspan=\"4\" style='background-color:silver;'><b>The following goods received at warehouse - <span style='color:red'>"+headerTable[33]+" </b></span>"
			 		+ "</td></tr>");
			 body.append("<tr><td style='border:1px solid black;border-top: none;border-bottom: none' align='left'><b>GRN No.</b></td><td style='border:1px solid black;border-top: none;border-bottom: none' align='left'>"+headerTable[1]+"</td>");
			 body.append("<td style='border:1px solid black;border-top: none;border-bottom: none' align='left'><b>GRN Date</b></td><td style='border:1px solid black;border-top: none;border-bottom: none' align='right'>"+headerTable[2]+"</td></tr>");
			 
			 body.append("<tr><td style='border:1px solid black;border-top: none;border-bottom: none' align='left'><b>Supplier or Empl. Name</b></td><td style='border:1px solid black;border-top: none;border-bottom: none' align='left'>"+headerTable[25]+"</td>");
			 body.append("<td style='border:1px solid black;border-top: none;border-bottom: none' align='left'><b>Supplier or Empl. Code</b></td><td style='border:1px solid black;border-top: none;border-bottom: none' align='left'>"+headerTable[26]+"</td></tr>");
			 
			 body.append("<tr><td style='border:1px solid black;border-top: none;border-bottom: none' align='left'><b>Supplier Invoice / Transfer No</b></td><td style='border:1px solid black;border-top: none;border-bottom: none' align='left'>"+headerTable[3]+"</td>");
			 body.append("<td style='border:1px solid black;border-top: none;border-bottom: none' align='left'><b>Transfer Date</b></td><td style='border:1px solid black;border-top: none;border-bottom: none' align='right'>"+headerTable[28]+"</td></tr>");
			 String PO_no = headerTable[29]==null?"":(String)headerTable[29];
			 
			 String PO_Date = headerTable[30]==null?"":dtformat.format(dtparse.parse(headerTable[30].toString().substring(0,10)));
			 body.append("<tr><td style='border:1px solid black;border-top: none;border-bottom: none' align='left'><b>PO No.</b></td><td style='border:1px solid black;border-top: none;border-bottom: none' align='left'>"+PO_no+"</td>");
			 body.append("<td style='border:1px solid black;border-top: none;border-bottom: none' align='left'><b>PO Date</b></td><td style='border:1px solid black;border-top: none;border-bottom: none' align='right'>"+PO_Date+"</td></tr>");
			 body.append("</table>");
			 
			 body.append("<br><br>");
			 body.append("<table  border=\"1\" style='border-collapse:collapse'>");
			 body.append("<tr><td align='left'><b>SL No.</b></td><td align='left' style='width:350px'><b>Description</b></td><td align='left'><b>Division Name</b></td>");
			 body.append("<td align='left'><b>Product Type/Category</b></td><td align='left'><b>Batch No / P.O No.</b></td><td align='left'><b>Expiry Date</b></td>");
			 body.append("<td align='left'><b>Batch Rate</b></td><td align='left'><b>GRN QTY</b></td><td align='left'><b>Value (Rs.Ps)</b></td>");
			 body.append("<td align='left'><b>No of Cases</b></td><td align='left'><b>Product code</b></td><td align='left'><b>Fcode</b></td>");
			 body.append("<td align='left' style='width:150px'><b>GCMA Code</b></td><td align='left'><b>GCMA Exp Date</b></td><td align='left'><b>Remark</b></td></tr>");
			 
			 Object[] ObjArr = null;
			 String gcmaDt = "";
			 int count = 1;
			 for(Object obj : details) {
				 ObjArr = (Object[]) obj;
				 body.append("<tr>");
				 body.append("<td>"+count+"</td>");
				 body.append("<td>"+ObjArr[8]+"</td>");
				 body.append("<td>"+ObjArr[27]+"</td>");
				 body.append("<td>"+ObjArr[7]+"</td>");
				 body.append("<td>"+ObjArr[9]+"</td>");
				 body.append("<td>"+ObjArr[31]+"</td>");
				 body.append("<td align='right'>"+ObjArr[11]+"</td>");
				 body.append("<td align='right'>"+ObjArr[10].toString().substring(0,ObjArr[10].toString().length()-3)+"</td>");
				 body.append("<td align='right'>"+ObjArr[12]+"</td>");
				 body.append("<td align='right'>"+ObjArr[32]+"</td>");
				 body.append("<td>"+ObjArr[23]+"</td>");
				 body.append("<td>"+ObjArr[24]+"</td>");
				 body.append("<td>"+ObjArr[18]+"</td>");
				 gcmaDt = ObjArr[20]==null?"":(String)ObjArr[20];
				 body.append("<td>"+gcmaDt+"</td>");
				 body.append("<td>"+ObjArr[22]+"</td>");
				 body.append("</tr>");
				 count++;
			 }
			 body.append("</table>");
			 body.append("</body></html>");
			 
			 
			 if(body.length()>0){
					EmailFrom mailconfig = emailrepository.getEmailObject("EMAILFROM");
					this.sendHtmlMail(to_list, subject, body.toString(),null,
							mailconfig.getEmail(),mailconfig.getMail_password(),mailconfig.isAuth(),
							mailconfig.isTls_sls(),mailconfig.getHost(),mailconfig.getPort());
				}
		 }
		 catch(Exception e) {
			 System.out.println("Error Occurred :" +e.getMessage());// uncomment asneeded --;
			 throw e;
		}
		 return "success";
		 
	 }
	 
	 SimpleDateFormat yMd_parser = new SimpleDateFormat("yyyy-MM-dd");
	 SimpleDateFormat dMy_format = new SimpleDateFormat("dd-MM-yyyy");
	 DecimalFormat df = new DecimalFormat("#.##");
	 
	 public void sendApprovalMailForArticleSchemRequest(List<ArtSchemeBeanForApprNewLogic> details, List<String> to_list,
				String status, String userName, String subject, String link, MailBean mailBean, String nextApprovar,
				List<MailBean> approvalDetails, String discardLink, String compcd,ArticleSchemeRequestHeader hdr_for_notifi) throws Exception {
			try {
				System.out.println("ToList :" + to_list.size());
				final String approvalLink = companyMasterService.getCompanyDetails().getWeb_site();
				StringBuffer body = new StringBuffer();
				body.append("<html><body>The following Article Requests have been ");
				if (status.equals("F")) {
					body.append("sent for approval by ");
					body.append("<b>");
					body.append(userName + "-" + mailBean.getStarted_by_name());
					body.append(".</b>");

				} else if (status.equals("A")) {
					body.append("approved by ");
					body.append("<b>");
					if (approvalDetails != null)
						body.append(approvalDetails.get(approvalDetails.size() - 1).getStarted_by_name());
					else
						body.append(userName);
					body.append(".</b>");

				} else if (status.equals("D")) {
					body.append("discarded by ");
					body.append("<b>");
					if (approvalDetails != null)
						body.append(approvalDetails.get(approvalDetails.size() - 1).getStarted_by_name());
					else
						body.append(userName);
					body.append(".</b>");
					subject = "Article Scheme Request Discarded";
				} else {
					body.append("sent for approval by ");
					body.append("<b>");
					body.append(userName + "-" + mailBean.getStarted_by_name());
					body.append(".</b>");
				}
				
				String lrNo = hdr_for_notifi.getLr_number() == null ? "":hdr_for_notifi.getLr_number();
				String lrDate = hdr_for_notifi.getLr_date() != null?
						 dMy_format.format(hdr_for_notifi.getLr_date()) : "" ;
				 //header table
				 body.append("<table border='1' style='border-collapse: collapse; margin-top:10px; width:35%'>");
				 body.append("<tr><th style='padding:4px'>SAP Invoice No.</th><td style='padding:4px'>".concat(hdr_for_notifi.getInvoice_no()).concat("</td></tr>"));
				 body.append("<tr><th style='padding:4px'>SAP Invoice Date</th><td style='padding:4px'>".concat(dMy_format.format(hdr_for_notifi.getInvoice_date())).concat("</td></tr>"));
				 body.append("<tr><th style='padding:4px'>LR Number</th><td style='padding:4px'>".concat(lrNo).concat("</td></tr>"));
				 body.append("<tr><th style='padding:4px'>LR Date</th><td style='padding:4px'>".concat(lrDate).concat("</td></tr>"));
				 body.append("<tr><th style='padding:4px'>Total Value</th><td style='text-align:right;padding:4px'>".concat(df.format(hdr_for_notifi.getArtsch_total_value())).concat("</td></tr>"));
				 body.append("</table>");
				 
				 if (status.equals("F")) {
					//customer details
					 body.append("<table border='1' style='border-collapse: collapse; margin-top:10px ;width:35%'>");
					 body.append("<tr><th style='padding:4px'>Customer Name</th><td style='padding:4px'>".concat(details.get(0).getCust_name_billto()).concat("</td></tr>"));
					 body.append("<tr><th style='padding:4px'>ERP Cust. Code</th><td style='padding:4px'>".concat(details.get(0).getErp_cust_cd()).concat("</td></tr>"));
					 body.append("<tr><th style='padding:4px'>Cust. Shipping Address</th><td style='padding:4px'>".concat( 
					 details.get(0).getAddr1_shipto()).concat(" ,").concat(details.get(0).getAddr2_shipto()).concat(" ,")
							 .concat(details.get(0).getAddr3_shipto()).concat(" ,")
							 .concat(details.get(0).getAddr4_shipto()).concat("</td></tr>"));
					 body.append("<tr><th style='padding:4px'>Cust. Destination</th><td style='padding:4px'>".concat(details.get(0).getDestination_shipto()).concat("</td></tr>"));
					 body.append("</table>");
				 }
				 
				 if (status.equals("F")) {
					 //change this table for new logic
					 body.append("<table border='1' style='border-collapse: collapse; margin-top:10px'>");
					 
					 body.append("<tr>");
					 body.append("<th style='padding:4px' colspan='3'>Scheme Details</th>");
					 body.append("<th style='padding:4px' colspan='4'>Article Product Details</th>");
					 body.append("</tr>");
					 
					 body.append("<tr>");
					 body.append("<th style='padding:4px;width:12%'>Code</th>");
					 body.append("<th style='padding:4px;width:25%'>Name</th>");
					 body.append("<th style='padding:4px;width:8%'>Sales Bill Val.</th>");
					 
					 body.append("<th style='padding:4px;width:12%'>ERP Code</th>");
					 body.append("<th style='padding:4px;width:25%'>Name</th>");
					 body.append("<th style='padding:4px;width:8%'>Article Qty. Total</th>");
					 body.append("<th style='padding:4px;width:10%'>Value</th>");
					 body.append("</tr>");
					 
					 //cannot use for each will have to use logic of changing scheme dtl ids
					 
//					 details.forEach(elem->{
//						 body.append("<tr>");
//						 body.append("<td style='padding:4px'>".concat(elem.getTrd_scheme_code()).concat("</td>"));
//						 body.append("<td style='padding:4px'>".concat(elem.getTrd_scheme_name()).concat("</td>"));
//						 body.append("<td style='padding:4px'>".concat(df.format(elem.getBilled_val_entered_tot())).concat("</td>"));
//						 
//						 body.append("<td style='padding:4px'>".concat(elem.getSmp_erp_prod_cd()).concat("</td>"));
//						 body.append("<td style='text-align:right;padding:4px'>".concat(elem.getSmp_prod_name()).concat("</td>"));
//						 body.append("<td style='text-align:right;padding:4px'>".concat(elem.getArticles_to_give().toString()).concat("</td>"));
//						 body.append("<td style='text-align:right;padding:4px'>".concat(df.format(elem.getArtsch_total_value())).concat("</td>"));
//						 body.append("</tr>");
//						 
//						 
//						 body.append("<tr><td colspan='7' style='padding:0px'>");
//						//make table here
//						 body.append("");
//						 body.append("");
//						 body.append("</td></tr>");
//					 });
					 
					 
					 
					 Long old_art_req_dtl_id = 0L;
					 Long new_art_req_dtl_id = 0L;
					 Long old_art_req_prd_id = 0L;
					 Long new_art_req_prd_id = 0L;
					 boolean first_row = true;
					 for(int i =0 ;i<details.size();i++) {
						 new_art_req_dtl_id = details.get(i).getArticle_req_dtl_id();
						 new_art_req_prd_id = details.get(i).getArticle_req_prd_id();
						 
						 if(new_art_req_dtl_id.compareTo(old_art_req_dtl_id) > 0) {
							 if(!first_row) {
								 body.append("</table>");
								 body.append("</td></tr>");
							 }
							 //add new row for article req dtl
							 body.append("<tr>");
							 body.append("<td style='padding:4px;width:12%'>").append(details.get(i).getTrd_scheme_code()).append("</td>");
							 body.append("<td style='padding:4px;width:25%'>").append(details.get(i).getTrd_scheme_name()).append("</td>");
							 body.append("<td style='padding:4px;width:8%'>")
							 .append(details.get(i).getScheme_applied_to().equals("V")?  details.get(i).getBilled_val_entered_tot() : "NA")
							 .append("</td>");
							 
							 body.append("<td style='padding:4px;width:12%'>").append(details.get(i).getSmp_erp_prod_cd()).append("</td>");
							 body.append("<td style='padding:4px;width:25%'>").append(details.get(i).getSmp_prod_name()).append("</td>");
							 body.append("<td style='padding:4px;width:8%'>").append(details.get(i).getArticles_to_give()).append("</td>");
							 body.append("<td style='padding:4px;width:10%'>").append(details.get(i).getArtsch_total_value()).append("</td>");
							 body.append("</tr>");
							 //add headers of prod table
							 
							 body.append("<tr><td colspan='7' style='padding:0px'>");
							 body.append("<table border='1' style='border-collapse: collapse' width='100%'>");
							 body.append("<tr>");
							 body.append("<th style='padding:4px;width:15%'>Sale Product Code</th>");
							 body.append("<th style='padding:4px;width:35%'>Sale Product Name</th>");
							 body.append("<th style='padding:4px;width:15%'>Per Sale Bill Val.</th>");
							 body.append("<th style='padding:4px;width:15%'>Sale Qty. Billed</th>");
							 body.append("<th style='padding:4px;width:20%'>Article Product Qty Per Sale Product</th>");
							 body.append("</tr>");
						 }
						 if(new_art_req_prd_id.compareTo(old_art_req_prd_id) > 0) {
							 //add new row for prod id
							 body.append("<tr>");
							 body.append("<td style='padding:4px;width:15%'>").append(details.get(i).getSale_prod_code()).append("</td>");
							 body.append("<td style='padding:4px;width:35%'>").append(details.get(i).getSale_prod_name()).append("</td>");
							 body.append("<td style='padding:4px;width:15%'>")
							 .append(details.get(i).getScheme_applied_to().equals("V")?  details.get(i).getPer_sale_billed_val() : "NA").append("</td>");
							 body.append("<td style='padding:4px;width:15%'>")
							 .append(details.get(i).getScheme_applied_to().equals("Q")?  details.get(i).getPer_sale_prod_qty_entered() : "NA").append("</td>");
							 body.append("<td style='padding:4px;width:20%'>")
							 .append(details.get(i).getScheme_applied_to().equals("Q")?  details.get(i).getArticle_qty() : "NA").append("</td>");
							 body.append("</tr>");
						 }
						first_row = false;
						old_art_req_dtl_id = new_art_req_dtl_id;
						old_art_req_prd_id = new_art_req_prd_id;
					 }
					 
					 body.append("</table>");
					 body.append("</td></tr>");
					 
					 body.append("</table>");
				 }
				 
				 if (status.equals("F")) {
						body.append("<div style='margin:10px'>Actions : ");
						body.append("<button style='margin-right:7px' type='button'><a href="+approvalLink+link+">Approve</a></button>");
						body.append("<button style='margin-left:7px;' type='button'><a href="+approvalLink+discardLink+">Discard</a></button>");
						body.append("</div>");
				 }
				 
				 body.append("\n\nThis is a system generated mail. Please do not reply.");
				 body.append("</body></html> ");
				 
				if (body.length() > 0) {
					EmailFrom mailconfig = emailrepository.getEmailObject("EMAILFROM");
					if(approvalDetails != null) {
						subject = subject.concat("Recipient Name :" + approvalDetails.get(approvalDetails.size() - 1).getStarted_by_name());
					}
					sendHtmlMail(to_list,
							subject,
							body.toString(), null, mailconfig.getEmail(), mailconfig.getMail_password(),
							mailconfig.isAuth(), mailconfig.isTls_sls(), mailconfig.getHost(), mailconfig.getPort());
				}

			} catch (Exception e) {
				e.printStackTrace();
				System.out.println("Error Occurred :" +e.getMessage());// uncomment asneeded --;
				throw e;
			}
		}

		@SuppressWarnings("unused")
		public void sendHtmlMailWithCCAndFile(List<String> to_list, String subject, String body, Map<String, String> mapInlineImages,
				String mailusername, String mailpassword, String auth, String tls, String host, String port,List<String> ccList)
				throws Exception {
			Properties props = new Properties();

			final String username = mailusername;
			final String password = mailpassword;
			props.put("mail.smtp.auth", auth);// true
			props.put("mail.smtp.starttls.enable", tls);// "true"
			props.put("mail.smtp.host", host);// mail.sampro-pfizerindia.com
			props.put("mail.smtp.port", port);// 587/25

			Session session = Session.getDefaultInstance(props, new javax.mail.Authenticator() {
				protected PasswordAuthentication getPasswordAuthentication() {
					return new PasswordAuthentication(username, password);
				}
			});

			try {
				MimeMessage message = new MimeMessage(session);
				message.setFrom(new InternetAddress(username));
				
				for (String to : to_list) {
					System.out.println(to);
					message.addRecipient(Message.RecipientType.TO, new InternetAddress(to));
					// message.addRecipients(arg0, arg1)
				}
				
				
				for (String cc : ccList) {
					System.out.println(cc);
					message.addRecipient(Message.RecipientType.CC, new InternetAddress(cc));
					// message.addRecipients(arg0, arg1)
				}
				
				message.setSubject(subject);

				if (mapInlineImages != null) {

					// creates message part
					MimeBodyPart messageBodyPart = new MimeBodyPart();
					messageBodyPart.setContent(body, "text/html");
					// creates multi-part
					Multipart multipart = new MimeMultipart();
					multipart.addBodyPart(messageBodyPart);

					// adds inline image attachments
					if (mapInlineImages != null && mapInlineImages.size() > 0) {
						Set<String> setImageID = mapInlineImages.keySet();

						for (String contentId : setImageID) {
							MimeBodyPart imagePart = new MimeBodyPart();
							imagePart.setHeader("Content-ID", "<" + contentId + ">");
							imagePart.setDisposition(MimeBodyPart.INLINE);

							String imageFilePath = mapInlineImages.get(contentId);
							try {
								imagePart.attachFile(imageFilePath);
							} catch (IOException ex) {
								ex.printStackTrace();
								System.out.println("Error Occurred :" + new CodifyErrors().getErrorCode(ex));// uncomment
																			// --;
							}
							multipart.addBodyPart(imagePart);
						}
					}
					message.setContent(multipart);
				} else {
					message.setContent(body, "text/html");
				}
				Transport.send(message);
				System.out.println("message sent....");
			} catch (MessagingException ex) {
				throw ex;
			}
		}
		
		
		
}