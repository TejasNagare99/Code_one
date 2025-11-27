
package com.excel.service;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.math.BigDecimal;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Base64;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.activiti.engine.RuntimeService;
import org.activiti.engine.runtime.ProcessInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.excel.bean.MailBean;
import com.excel.bean.SpecialAllocationBean;
import com.excel.exception.MedicoException;
import com.excel.model.AllocReqDet;
import com.excel.model.AllocReqHeader;
import com.excel.model.AllocReqImages;
import com.excel.model.Company;
import com.excel.model.DoctorMaster;
import com.excel.model.FieldStaff;
import com.excel.model.Location;
import com.excel.model.TaskMaster;
import com.excel.model.Task_Master_Dtl;
import com.excel.repository.AllocHeaderRepository;
import com.excel.repository.AllocationRepository;
import com.excel.repository.FieldStaffRepository;
import com.excel.repository.SpecialAllocationRepository;
import com.excel.repository.TransactionalRepository;
import com.excel.utility.CodifyErrors;
import com.excel.utility.MedicoConstants;
import com.excel.utility.MedicoResources;
import com.excel.utility.SendMail;

@Service
public class SpecialAllocationServiceImpl implements SpecialAllocationService, MedicoConstants {
	@Autowired
	EmailTranwiseHelpService emailHelpService;
	@Autowired
	SpecialAllocationRepository specialAllocationRepository;
	@Autowired
	AllocHeaderRepository allocHeaderRepository;
	@Autowired
	TransactionalRepository transactionalRepository;
	@Autowired
	TaskMasterService taskMasterService;
	@Autowired
	RuntimeService runtimeService;
	@Autowired
	SendMail sendMail;
	@Autowired
	DoctorMasterService doctorMasterService;
	@Autowired
	FieldStaffRepository fieldStaffRepository;
	@Autowired
	FieldStaffService fieldStaffService;
	@Autowired
	LocationService locationService;
	@Autowired
	AllocationRepository allocationRepository;
	@Autowired
	DivisionMasterService divisionMasterService;
	@Autowired
	ParameterService parameterService;
	@Autowired
	CompanyMasterService compMasterService;

	@Autowired
	EmailTranWiseService emailtranwiseservice;

	@Autowired
	UserMasterService usermastService;
	@Autowired
	ApprovalMailValidationService appr_mail_service;

	static String newFilePath = null;

	@Override
	public List<Object> getFieldStaffDetails(String empId, Long fs_id) throws Exception {
		// TODO Auto-generated method stub
		return specialAllocationRepository.getFieldStaffDetails(empId, fs_id);
	}

	@Override
	public List<Object> getDoctorDetails(Long docId) throws Exception {
		// TODO Auto-generated method stub
		return specialAllocationRepository.getDoctorDetails(docId);
	}

	@Override
	public List<Object> getProductDetails(Long divId, Long prodId, String companyCode) throws Exception {
		// TODO Auto-generated method stub
		return specialAllocationRepository.getProductDetails(divId, prodId, companyCode);
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public Map<String, Object> saveSpecialAllocation(SpecialAllocationBean bean) throws Exception {
		Map<String, Object> map = new HashMap<>();
		List<Long> detailIds = new ArrayList<>();
		int i = 0;

		AllocReqHeader header = this.setAllocReqHeader(bean);
		System.out.println("List Size " + bean.getProductDetails().size());
		for (SpecialAllocationBean.ProductDetails allocDetail : bean.getProductDetails()) {
			AllocReqDet detail = this.setAllocReqDet(bean, header, allocDetail);
			detailIds.add(detail.getAlloc_req_dtl_id());
		}
		this.setAllocReqImages(bean, header);
		header.setRequest_no(bean.getRequestType() + "-" + header.getAlloc_req_id());
		this.transactionalRepository.update(header);

		if (bean.getEmailReqInd().trim().equals("Y")) {
			String tranTypeForMailTable = "SP";
			tranTypeForMailTable = tranTypeForMailTable.concat(bean.getRequestType().trim().toUpperCase());
			if (bean.getMode().equals("Modify")) {
				// delete emails after getting them
				emailtranwiseservice.DeleteEmailTranwiseByTranId(tranTypeForMailTable, header.getAlloc_req_id(),
						bean.getFinYear());
			}
			// save mails
			emailtranwiseservice.saveEmailTranWise(bean.getMailList(), tranTypeForMailTable, header.getAlloc_req_id(),
					bean.getFinYear());
			this.emailHelpService.saveMailsToHelpTable(bean.getMailList(), GRN_, bean.getFinYear());
		}

		map.put("requestNumber", header.getRequest_no());
		map.put("allocReqId", header.getAlloc_req_id());
		map.put("detailIds", detailIds);
		map.put(RESPONSE_MESSAGE, "Saved Succesfully");

		return map;
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public AllocReqHeader setAllocReqHeader(SpecialAllocationBean bean) throws Exception {
		AllocReqHeader header = null;
		String div_name = divisionMasterService.getDivNameByDivID(bean.getDiv_id());
		if (bean.getAlloc_req_id().compareTo(0L) == 0 && bean.getMode().equals("Entry")) {
			header = new AllocReqHeader();

			header.setReq_ins_dt(new Date());
			header.setReq_ins_ip_add(bean.getIpAddress());
			header.setReq_ins_user_id(bean.getUserId());
			header.setReq_mod_dt(null);
			header.setReq_mod_ip_add(null);
			header.setReq_mod_user_id(null);
		} else {
			header = this.specialAllocationRepository.getObjectById(bean.getAlloc_req_id());

			header.setReq_mod_dt(new Date());
			header.setReq_mod_ip_add(bean.getIpAddress());
			header.setReq_mod_user_id(bean.getUserId());
		}
		header.setAlloc_req_date(bean.getAlloc_req_date() == null ? null
				: MedicoResources.convert_DD_MM_YYYY_toDate(bean.getAlloc_req_date()));
		header.setAlloc_type(bean.getAllocType());
		header.setCompany(bean.getCompanyCode());
		header.setDivision(div_name);
		header.setFin_year(bean.getFinYear());
		header.setFreight_type(null);
		header.setFs_id(bean.getRecipientId());
		header.setMcl_number(bean.getMclNo());
		header.setMeeting_address(bean.getMeetingAddress());
		header.setMeeting_date(bean.getMeetingDate() == null ? null
				: MedicoResources.convert_DD_MM_YYYY_toDate(bean.getMeetingDate()));
		header.setSupply_date(
				bean.getSupplyDate() == null ? null : MedicoResources.convert_DD_MM_YYYY_toDate(bean.getSupplyDate()));
		header.setMeeting_venue(bean.getMeetingVenue());
		header.setPeriod_code(bean.getPeriodCode());
		header.setRecipient_address1(bean.getAddress1());
		header.setRecipient_address2(bean.getAddress2());
		header.setRecipient_address3(bean.getAddress3());
		header.setRecipient_address4(bean.getAddress4());
		header.setRecipient_city(bean.getCity());
		header.setRecipient_email(bean.getEmail());
		header.setRecipient_name(bean.getRecipientName());
		header.setRecipient_pin(bean.getPincode());
		header.setRecipient_phone(bean.getPhone());
		header.setRecipient_type(bean.getRecipientStringId());
		header.setReq_total_value(BigDecimal.ZERO);
		header.setRequest_no(null);
		header.setRequestor_id(bean.getRequestorId());
		header.setTerritory_id(bean.getTerritoryId());
		header.setRequest_type(bean.getRequestType());
		header.setReq_status('A');
		header.setAlloc_appr_status('E');
		header.setDoc_letter(bean.getDoc_letter());
		header.setService_req_no(bean.getServiceNo());// added
		// header.setAlloc_req_image_path(bean.getAttachedFile());//added shiv 11 june
		// 22
		header.setSrt_number(bean.getSrtNo());
		header.setSrt_date(bean.getSrtDate().equals("Invalid date")  || bean.getSrtDate().trim().isEmpty()
				|| bean.getSrtDate().trim()== null ?null
				: MedicoResources.convert_DD_MM_YYYY_toDate(bean.getSrtDate()));
		header.setSrt_remark(bean.getSrtremark());
		if (bean.getAlloc_req_id().compareTo(0L) == 0 && bean.getMode().equals("Entry")) {
			this.transactionalRepository.persist(header);
		} else {
			this.transactionalRepository.update(header);
		}

		return header;
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public AllocReqDet setAllocReqDet(SpecialAllocationBean bean, AllocReqHeader header,
			SpecialAllocationBean.ProductDetails allocDetail) throws Exception {
		AllocReqDet detail = null;
		String div_name = "";
		System.out.println("Id" + allocDetail.getAlloc_req_detail_id());
		if (allocDetail.getAlloc_req_detail_id() == null || allocDetail.getAlloc_req_detail_id().compareTo(0L) == 0) {
			detail = new AllocReqDet();

			detail.setReqdt_ins_user_id(bean.getEmpId());
			detail.setReqdt_ins_dt(new Date());
			detail.setReqdt_ins_ip_add(bean.getIpAddress());
			detail.setReqdt_ins_user_id(bean.getUserId());
			detail.setReqdt_mod_user_id(null);
			detail.setReqdt_mod_dt(null);
			detail.setReqdt_mod_ip_add(null);
			detail.setReqdt_mod_user_id(null);
		} else {
			detail = this.specialAllocationRepository.getObjectByDetailId(allocDetail.getAlloc_req_detail_id());

			detail.setReqdt_mod_user_id(bean.getUserId());
			detail.setReqdt_mod_dt(new Date());
			detail.setReqdt_mod_ip_add(bean.getIpAddress());
			detail.setReqdt_mod_user_id(bean.getUserId());
		}
		detail.setAlloc_cycle(bean.getAlloc_cycle());
		detail.setAlloc_qty(allocDetail.getAllocQtys());
		detail.setAlloc_rate(allocDetail.getRates());
		detail.setAlloc_req_date(bean.getAlloc_req_date() == null ? null
				: MedicoResources.convert_DD_MM_YYYY_toDate(bean.getAlloc_req_date()));
		detail.setAlloc_req_id(header.getAlloc_req_id());
		detail.setAlloc_value(allocDetail.getValues());
		detail.setAlt_div_id(bean.getDiv_id());
		detail.setCompany(bean.getCompanyCode());
		detail.setDivision(header.getDivision());
		detail.setFin_year(bean.getFinYear());
		detail.setFs_id(bean.getRecipientId());
		detail.setPeriod_code(bean.getPeriodCode());
		detail.setProd_id(allocDetail.getProductIds());
		detail.setProd_sensitive(allocDetail.getSensitives().split("/")[1]);
		detail.setProd_highvalue(allocDetail.getSensitives().split("/")[0]);
		detail.setProd_sub_type(allocDetail.getProdSubType());
		detail.setProd_threshold(allocDetail.getThresholds());
		detail.setProd_type(allocDetail.getProdType());
		detail.setReqdt_status("A");
		detail.setTerritory_id(bean.getTerritoryId());

		if (allocDetail.getAlloc_req_detail_id() == null || allocDetail.getAlloc_req_detail_id().compareTo(0L) == 0) {
			this.transactionalRepository.persist(detail);
		} else {
			this.transactionalRepository.update(detail);
		}

		return detail;
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public AllocReqImages setAllocReqImages(SpecialAllocationBean bean, AllocReqHeader header) throws Exception {
		AllocReqImages image = null;
		if (bean.getAlloc_req_id().compareTo(0L) == 0 && bean.getMode().equals("Entry")) {
			image = new AllocReqImages();
		} else {
			image = this.specialAllocationRepository.getObjectByImageId(bean.getAlloc_req_id());
		}
		image.setAlloc_req_id(header.getAlloc_req_id());
		image.setAlloc_req_date(new Date());
		image.setCompany(bean.getCompanyCode());
		image.setDivision(header.getDivision());
		image.setFin_year(bean.getFinYear());
		image.setPeriod_code(bean.getPeriodCode());
		image.setImage_name(bean.getFileName());
		image.setImage_folder(bean.getFilePath());

		if (bean.getAlloc_req_id().compareTo(0L) == 0 && bean.getMode().equals("Entry")) {
			this.transactionalRepository.persist(image);
		} else {
			this.transactionalRepository.update(image);
		}
		return image;
	}

	@Override
	public Map<String, Object> uploadSpecialAllocImage(MultipartFile file) {

		System.out.println("inside upload file");
		String newFile = null;
		Map<String, Object> map = new HashMap<String, Object>();
		try {
			String rootPath = MedicoConstants.SPECIAL_ALLOC_IMAGE;
			File dir = new File(rootPath);
			if (!dir.exists()) {
				dir.mkdirs();
			}
			File f = new File("file");
			String filename = file.getOriginalFilename().toString();
			System.out.println("orignal file name:" + filename);
			String newFileString = dir.getAbsolutePath() + File.separator + file.getOriginalFilename();
			// frm here
			String str = file.getOriginalFilename();
			String fileFormat = str.substring(str.lastIndexOf('.') + 1);
			System.out.println("fileFormat:::" + fileFormat);
			String[] arrOfStr = null;
			String str1 = file.getOriginalFilename();
			if (fileFormat.equalsIgnoreCase("jpg")) {
				arrOfStr = str1.split(".jpg");
			} else if (fileFormat.equalsIgnoreCase("jpeg")) {
				arrOfStr = str1.split(".jpeg");
			} else if (fileFormat.equalsIgnoreCase("pdf")) {
				arrOfStr = str1.split(".pdf");
			} else if (fileFormat.equalsIgnoreCase("png")) {
				arrOfStr = str1.split(".png");
			}
			for (String a : arrOfStr) {
				long timevar = new Date().getTime();
				newFile = a + timevar + "." + fileFormat;
				break;
			}
			// till here
			newFileString = dir.getAbsolutePath() + File.separator + newFile;
			System.out.println(newFileString);
			byte[] bytes = file.getBytes();
			BufferedOutputStream stream = new BufferedOutputStream(new FileOutputStream(newFileString));
			stream.write(bytes);
			stream.close();
			newFilePath = newFileString;
			System.out.println("new file path is:" + newFileString);

			map.put("filePath", newFilePath);
			map.put("fileName", newFile);

		} catch (Exception e) {
			System.out.println("Error Occurred :" + e.getMessage());// uncomment asneeded --;
		}
		return map;
	}

	@Override
	public void deleteProduct(Long allocDetailId, Long year) throws Exception {
		AllocReqDet allocDetail = this.specialAllocationRepository.getObjectByDetailId(allocDetailId);
		this.transactionalRepository.delete(allocDetail);
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public void specialAllocationApproval(@RequestParam("alloc_req_id") Long allocId,
			@RequestParam("userName") String user, @RequestParam("empEmail") String email,
			@RequestParam("remark") String remark, HttpServletRequest request, HttpSession session, boolean isbulk)
			throws Exception {
		
		
		String approval = "";
		String companyCode = session.getServletContext().getAttribute(COMPANY_CODE).toString();
		String is_live = session.getServletContext().getAttribute(IS_LIVE).toString();
		AllocReqHeader header = this.specialAllocationRepository.getObjectById(allocId);
		Company companyMaster = (Company) request.getServletContext().getAttribute(COMPANY_DATA);
		List<TaskMaster> masters = null;
		
		if (header.getAlloc_appr_status() == 'E') {

			String TASK_FLOW = MedicoConstants.BPM_LEVELWISE;
			Location location = locationService.getLocationNameByEmployeeId(user);

			if (companyMaster.getCfa_to_stk_ind().equalsIgnoreCase("Y")) {
				approval = this.taskMasterService.getTranTypeByLocIdAndApprovalType(location.getLoc_id(), SPLALLOC,
						header.getCompany().trim());
			} else {
				if (MedicoConstants.PIPL_LOC.contains(location.getLoc_id())) {
					approval = MedicoConstants.SPECIAL_APPR_PIPL;
				} else {
					approval = MedicoConstants.SPECIAL_APPR_PFZ;
				}
			}

			if (approval == null || approval.isEmpty()) {
				throw new MedicoException("Could not find Approval Data !");
			}

			if (header.getAlloc_type().trim().equals("D")) {
				allocId = 1l;
				masters = taskMasterService.getTask(null, null, null, approval, null, null, TASK_FLOW, allocId,
						header.getRequest_type());
			} else {
				allocId = 2l;
				masters = taskMasterService.getTask(null, null, null, approval, null, null, TASK_FLOW, allocId,
						header.getRequest_type());
			}

			if (masters.size() == 0) {
				throw new MedicoException("Task master record not found");
			}
			System.out.println("TASK " + masters.get(0).getTask_id());
			TaskMaster taskMaster = masters.get(0);
			List<Task_Master_Dtl> task_Master_Dtls = taskMasterService.getTaskDetail(taskMaster.getTask_id());
			if (task_Master_Dtls.size() == 0) {
				throw new MedicoException("Task master detail record not found");
			}

			Map<String, Object> variables = new HashMap<String, Object>();
			variables.put("initiator", user);
			variables.put("initiator_desc", user);
			variables.put("initiator_email", email);
			variables.put("tran_ref_id", header.getAlloc_req_id());
			variables.put("tran_type", approval);
			variables.put("inv_group", null);
			variables.put("loc_id", 0);
			variables.put("cust_id", header.getRequestor_id());
			variables.put("tran_no", header.getRequest_no());
			variables.put("company_code", companyCode);
			variables.put("totaltask", masters.size());
			variables.put("amount", header.getReq_total_value());
			variables.put("escalatetime", null);
			variables.put("fin_year_id", header.getFin_year());
			variables.put("stock_point_id", 0);
			variables.put("doc_type", header.getRequest_type());
			variables.put("task_flow", TASK_FLOW);
			variables.put("remark", remark);
			if (header.getAlloc_type().trim().equals("D"))
				variables.put("fs_id", allocId);// for doctor request //variables.put("fs_id", allocId);
			else
				variables.put("fs_id", allocId); // for emergecy supply/meeting
			variables.put("approval_type", null);

			ProcessInstance processInstance = runtimeService.startProcessInstanceByKey("specialAllocation", variables);
		}
		header.setAlloc_appr_status('F');
		this.transactionalRepository.update(header);
		// sending mail after self Approval
//		if(!is_live.trim().equalsIgnoreCase("Y")) {
//			companyCode=null; 
//		}
		// companyCode="PFZ";//Temp
		if (!isbulk && companyCode.trim().equals("PFZ")) {
			this.sendMailForSpecialAllocation(header.getAlloc_req_id(), user, "selfApprove", null, "", "nextApproval",
					Long.valueOf(header.getFin_year()), approval, companyCode, request.getRemoteAddr());
		}
	}

	@Override
	public void sendMailForSpecialAllocation(Long allocId, String user, String apptovalType, String taskId,
			String status, String nextApprovar, Long year, String tranType, String companyCode, String ipaddr)
			throws Exception {
		Company company = this.compMasterService.getCompanyDetails();

		System.out.println("IN SELF-APPROVAL-SPE-MAIL");
		List<SpecialAllocationBean> details = null;
		Map<String, String> email_empId_map = new HashMap<String, String>();
		List<String> mailList = new ArrayList<>();
		String emailReqInd = "N";
		List<MailBean> approvalDetails = null;
		Long process_id = 0L;
		String userId = "";
		try {
			emailReqInd = this.parameterService.getSrtAndEmailReqInd("TRANWISE_EMAIL_REQD");
		} catch (Exception e) {
			System.out.println("Error Occurred :" + e.getMessage());// uncomment asneeded --;
		}

		if (nextApprovar != null)
			details = this.specialAllocationRepository.specialAllocationDetailsById(allocId, year, "F");
		else
			details = this.specialAllocationRepository.specialAllocationDetailsById(allocId, year, "A");
		MailBean mail = null;
		String link = null;
		String discardLink = null;
		List<MailBean> list = taskMasterService.getMailSendDetails(allocId, nextApprovar, tranType);
		mail = list.get(0);
		if (taskId != null) {
			approvalDetails = taskMasterService.getApprovalDetails(Long.valueOf(taskId));
			process_id = taskMasterService.getCustomTaskByTaskId(taskId);
		}
//      29nov:old functionality
//		29nov:link = "rest/saveApprovalDataListViaMail?task_id=" + mail.getAct_taskid() + "&decision=approve&userName="+ mail.getAssignee_() + "&remark=" + mail.getAct_taskid() + "&tran_id=" + allocId;
//		29nov:discardLink="rest/saveApprovalDataListViaMail?task_id="+mail.getAct_taskid()+"&decision=discard&userName="+mail.getAssignee_()+"&remark="+mail.getAct_taskid()+"&tran_id="+allocId;
//		29nov:mailList.add(mail.getEmp_email());

		System.out.println("Emp id :: " + mail.getEmp_id() + "Mail :: " + mail.getEmp_email());

		// email_empId_map.put(mail.getEmp_id(),mail.getEmp_email());

		if (nextApprovar == null) {
			mailList.add(mail.getEmp_email());
		} else {
			email_empId_map.put(mail.getEmp_id(), mail.getEmp_email());
		}

		if (companyCode != null && "PFZ".equalsIgnoreCase(companyCode.trim())) {
			if (nextApprovar == null) {
				mailList.add("nikhil.potkile@pfizer.com");
			} else {
				userId = this.usermastService.getEmpIdByEmail("nikhil.potkile@pfizer.com");
//				userId = this.usermastService.getEmpIdByEmail("abhishekg@excelsof.com");
				if (userId != null) {
					email_empId_map.put(userId, "nikhil.potkile@pfizer.com");
				}
			}
		}
		// Fields Staff Mail
		if (nextApprovar == null && !details.get(0).getAllocType().trim().equals("D")) {
			if (details.get(0).getEmail() != null || !details.get(0).getEmail().equals("")) {
				// 29nov:mailList.add(details.get(0).getEmail().toLowerCase());
				System.out.println("details.get(0).getEmail()" + details.get(0).getEmail());
				System.out.println("details.get(0).getEmail().toLowerCase()" + details.get(0).getEmail().toLowerCase());
				// comment while testing
				mailList.add(details.get(0).getEmail().toLowerCase());

			}
		}
		// not req for now
//		if (emailReqInd.trim().equals("Y")) {
//			 mailList.addAll(this.emailtranwiseservice.getEmailListByTranType("SP"+details.get(0).getRequestType().trim(),
//			 year.toString(),allocId));
//		}

		//// 29nov:
		String subject = "";
		if (nextApprovar == null) {
			subject = "Special Allocation Approval ";
			sendMail.sendApprovalMailForSpecialAllocation(details, mailList, status, user,
					"Special Allocation Approval ", link, mail, nextApprovar, approvalDetails, discardLink,
					companyCode);
		} else {
			if (companyCode.trim().equals("PFZ")) {
				Iterator<Map.Entry<String, String>> itr = email_empId_map.entrySet().iterator();

				while (itr.hasNext()) {
					Map.Entry<String, String> entry = itr.next();
					System.out.println("Key = " + entry.getKey() + ", Value = " + entry.getValue());
					System.out.println(entry.getKey());
					String token = Base64.getEncoder().encodeToString(entry.getKey().getBytes(StandardCharsets.UTF_8));
					System.out.println("toekn->" + token);
					System.out.println("decoded->" + new String(Base64.getDecoder().decode(token)));
					link = "rest/saveApprovalDataListViaMail?task_id=" + mail.getAct_taskid()
							+ "&decision=approve&userName=" + mail.getAssignee_() + "&remark=" + mail.getAct_taskid()
							+ "&tran_id=" + allocId + "&token=" + token;

					discardLink = "rest/saveApprovalDataListViaMail?task_id=" + mail.getAct_taskid()
							+ "&decision=discard&userName=" + mail.getAssignee_() + "&remark=" + mail.getAct_taskid()
							+ "&tran_id=" + allocId + "&token=" + token;
					System.out.println("Task Id :::::" + mail.getAct_taskid());

					subject = "Special Allocation Forwarded For Approval ";

					sendMail.sendApprovalMailForSpecialAllocation(details, Arrays.asList(entry.getValue()), status,
							user, subject, link, mail, nextApprovar, approvalDetails, discardLink, companyCode);
					if (mail.getAct_taskid() != null)
						this.appr_mail_service.saveAfterMailSend(Long.valueOf(mail.getAct_taskid()), allocId, tranType,
								process_id, entry.getValue(), entry.getKey(), user, ipaddr);
				}
			}
		}
	}

	@Override
	public List<SpecialAllocationBean> specialAllocationDetails(Long docId, Long requestorId, Long year, String status,
			String requestType,String recipientStringId) {
		return this.specialAllocationRepository.specialAllocationDetails(docId, requestorId, year, status, requestType,recipientStringId);
	}

	// activity call
	@Override
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public void finalApproval(Long allocationId, String last_approved_by, String comp_cd, String isapproved,
			String motivation) throws Exception {
		System.out.println("In final Approval Special Allocation");
		FieldStaff staff = null;
		DoctorMaster docMaster = null;
		try {
			Long fsId = 0L;
			List<AllocReqDet> detailList = null;
			AllocReqHeader header = this.specialAllocationRepository.getObjectById(allocationId);

			// In case of doctor request which eneter by PSO it delete tasks after
			// completion
			if (header.getAlloc_type().trim().equals("D")) {
				if (allocationId != null && allocationId != 0L) {
					// not needed ?
					// this.taskMasterService.deletePreviousTaskMasterDetail(allocationId);
					// this.taskMasterService.deletePreviousTaskMaster(allocationId);
				}
			}
			if (isapproved.equalsIgnoreCase("A")) {
				Long smpId = this.allocationRepository.getSmpId();
				detailList = this.specialAllocationRepository.getDetailListByReqId(allocationId);
				System.out.println("Req ::: " + header.getRequestor_id());
				staff = this.fieldStaffRepository.getObjectByStaffId(header.getRequestor_id());
				if (header.getAlloc_type().equals("D")) {
					docMaster = this.doctorMasterService.getDoctorsDetails(header.getFs_id());
					System.out.println("Req ::: " + header.getFs_id());

					if (docMaster.getDoc_fstaff_id() == null || docMaster.getDoc_fstaff_id() == 0) {
						fsId = this.fieldStaffService.setFiledStafForSpecialAllocation(header, staff, docMaster);

						docMaster.setDoc_fstaff_id(fsId);
						this.transactionalRepository.update(docMaster);
					} else {
						System.out.println("docMaster.getDoc_fstaff_id(); " + docMaster.getDoc_fstaff_id());
						fsId = docMaster.getDoc_fstaff_id();
					}
				} else {
					fsId = this.fieldStaffService.setFiledStafForSpecialAllocation(header, staff, docMaster);
				}
				this.allocHeaderRepository.setAllocDetailsForSpecialAlloc(header, detailList, fsId, smpId);
				header.setAlloc_appr_status('A');
				header.setReq_mod_dt(new Date());
				header.setReq_mod_user_id(last_approved_by);
				header.setReq_mod_ip_add("0");

				this.transactionalRepository.update(header);
			} else {
				header.setAlloc_appr_status('D');
				header.setReq_status('D');
				header.setReq_mod_dt(new Date());
				header.setReq_mod_user_id(last_approved_by);
				header.setReq_mod_ip_add("0");
				this.transactionalRepository.update(header);
			}
		} catch (Exception e) {
			System.out.println("Error Occurred :" + e.getMessage());// uncomment asneeded --;
		}
	}

	@Override
	public List<Object> getLevels(Long divId) throws Exception {
		return this.specialAllocationRepository.getLevels(divId);
	}

	@Override
	public List<SpecialAllocationBean> specialAllocationDetailsById(Long alloc_req_id, Long finYear, String status) {
		return this.specialAllocationRepository.specialAllocationDetailsById(alloc_req_id, finYear, status);
	}

	@Override
	public void updateForFrieght(Long alloc_req_id, String type, BigDecimal value) throws Exception {
		AllocReqHeader header = this.specialAllocationRepository.getObjectById(alloc_req_id);
		header.setFreight_type(type);
		header.setFreight_value(value);
		this.transactionalRepository.update(header);
	}

	@Override
	public String getServiceNumList(String serviceNo) throws Exception {
		return this.specialAllocationRepository.getServiceNumList(serviceNo);
	}

	@Override
	public List<AllocReqHeader> getAllocReqHdsBy_BlkReqNo(String blkReqNo) throws Exception {
		return this.specialAllocationRepository.getAllocReqHdsBy_BlkReqNo(blkReqNo);
	}

	@Override
	public List<SpecialAllocationBean> getExistingSpecialAllocation(String requestType) throws Exception {
		return this.specialAllocationRepository.getExistingSpecialAllocation(requestType);
	}

}
