package com.excel.restcontroller;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Base64;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Callable;
import java.util.concurrent.FutureTask;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.activiti.engine.ActivitiException;
import org.activiti.engine.RuntimeService;
import org.activiti.engine.TaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.mvc.support.RedirectAttributesModelMap;

import com.excel.bean.ApprLevelBean;
import com.excel.bean.ApprovalBean;
import com.excel.model.Activiti_FinalAppr_log;
import com.excel.model.Art_Sch_Vld_Ext;
import com.excel.model.ArticleSchemeRequestHeader;
import com.excel.model.Company;
import com.excel.model.trd_scheme_mst_hdr;
import com.excel.repository.ArticleSchemeDeliveryRepo;
import com.excel.repository.Article_Scheme_master_Repository;
import com.excel.repository.TransactionalRepository;
import com.excel.service.AllocationService;
import com.excel.service.ApprovalMailValidationService;
import com.excel.service.Article_Scheme_master_Service_Impl;
import com.excel.service.CompanyMasterService;
import com.excel.service.DispatchService;
import com.excel.service.GRNService;
import com.excel.service.SpecialAllocationService;
import com.excel.service.TaskMasterService;
import com.excel.utility.MedicoConstants;
import com.excel.utility.SendMail;

@RestController
@RequestMapping("/rest")
public class ApprovalController implements MedicoConstants {

	@Autowired
	private ApprovalMailValidationService appr_mail_vali_service;
	@Autowired
	private RuntimeService runtimeService;
	@Autowired
	private TaskService taskService;
	@Autowired
	private TaskMasterService taskMasterService;
	@Autowired
	private GRNService grnService;
	@Autowired
	private AllocationService allocationService;
	@Autowired
	private SpecialAllocationService specialAllocationService;
	@Autowired
	private SendMail sendMail;
	@Autowired
	private DispatchService dispatchService;
	@Autowired
	private CompanyMasterService companyMasterService;
	@Autowired
	private TransactionalRepository transactionalRepository;
	@Autowired
	private ArticleSchemeDeliveryRepo artSchmDelRepo;

	@Autowired
	private Article_Scheme_master_Service_Impl article_scheme_master_service;

	@Autowired
	private Article_Scheme_master_Repository article_Scheme_master_Repository;

//    @GetMapping("/start-process")
//    public String startProcess() {
//        runtimeService.startProcessInstanceByKey("my-process");
//        return "Process started. Number of currently running process instances = " + runtimeService.createProcessInstanceQuery()
//          .count();
//    }

//    @GetMapping("/get-tasks/{processInstanceId}")
//    public List<TaskRepresentation> getTasks(@PathVariable String processInstanceId) {
//        List<Task> usertasks = taskService.createTaskQuery()
//          .processInstanceId(processInstanceId)
//          .list();
//
//        return usertasks.stream()
//          .map(task -> new TaskRepresentation(task.getId(), task.getName(), task.getProcessInstanceId()))
//          .collect(Collectors.toList());
//    }

	@RequestMapping(value = "/saveapprovaldata", method = RequestMethod.POST)
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public String simpleProcessTestData(HttpServletRequest request, final RedirectAttributes redirectAttributes,
			@ModelAttribute(value = "approvaldata") ApprovalBean bean) throws Exception {

		System.out.println("bean" + bean.toString());
		System.out.println("ApprovalController.simpleProcessTestData()" + taskService);
		try {
			String nextApprover = null;

			/// New
			String ipAddr = request.getRemoteAddr();
			String processId = taskMasterService.getCustomTaskByTaskId(bean.getTaskid()).toString();
			ApprovalBean approvalBean = taskMasterService.getTranIdByTaskId(bean.getTaskid());
			System.out.println("approvalBean:::>" + approvalBean.toString());
			Activiti_FinalAppr_log actfinlog = new Activiti_FinalAppr_log();
			actfinlog.setApprover(bean.getApprover());
			actfinlog.setIp_addr(ipAddr);
			actfinlog.setProcess_id(processId);
			actfinlog.setIns_dt(new Date());
			actfinlog.setAcfil_ins_dt(new Date());
			actfinlog.setAcfil_ins_ip_add(ipAddr);
			actfinlog.setAcfil_ins_usr_id(bean.getApprover()); // Approver and UserId will be same
			actfinlog.setAcfil_tran_ref_id(0l);
			transactionalRepository.persist(actfinlog);

			taskMasterService.updateTranDetailsInAppoLog();

			if ("H".equalsIgnoreCase(bean.getApproved())) {

				String alreadyHeldApproval = taskMasterService.getHoldApprovalRemark(bean.getTran_id(),
						bean.getTran_type());
				System.out.println("Hold - " + bean.getMotivation());
				bean.setFin_year_id(Long.parseLong(request.getSession().getAttribute("fin_year_id").toString()));
				if (alreadyHeldApproval == null) {
					taskMasterService.saveHoldApproval(bean);
				} else {
					taskMasterService.updateHoldApproval(bean, false);
				}

			} else if ("A".equalsIgnoreCase(bean.getApproved())) {
				nextApprover = taskMasterService.getNextApproverPOByTaskId(bean.getTaskid(),
						String.valueOf(bean.getTran_type()), bean.getInv_grp());
				System.out.println("::::::nextApprover:::::" + nextApprover);
				if (nextApprover != null) {
					taskService.setAssignee(bean.getTaskid(), nextApprover);
					taskMasterService.updateApprovalLevelCustomTask(bean.getTaskid(), nextApprover);
					System.out.println("Next Approver " + nextApprover);
					
//					sendMail(request, bean.getTaskid(), bean.getApprover(), bean.getApproved(), nextApprover,
//							bean.getRemark());
				} else {
					claimAndCompleteTask(bean, bean.getApprover());
					if (redirectAttributes != null)
						redirectAttributes.addFlashAttribute("msg", "Task completed successfully.");
				}
				if ("A".equalsIgnoreCase(bean.getApproved()) && (approvalBean.getApprovalType().equals("32")
						|| approvalBean.getApprovalType().equals("300"))) {
					String scheme_id = "";
					if (approvalBean.getApprovalType().equals("300")) {
						Art_Sch_Vld_Ext vld_ext = this.article_scheme_master_service
								.getVldExtenById(approvalBean.getTran_id());
						scheme_id = vld_ext.getScheme_slno().toString();
					} else {
						scheme_id = approvalBean.getTran_id().toString();
					}
					List<trd_scheme_mst_hdr> hdrList = this.article_Scheme_master_Repository
							.get_HDR_Data_For_Edit(scheme_id);
					this.article_scheme_master_service.send_Mailfor_After_Discard_Creator(
							hdrList.get(0).getCreated_by(), hdrList.get(0).getLast_mod_by(), bean.getApprover(),
							hdrList.get(0).getTrd_sch_slno(), approvalBean.getApprovalType().equals("300"), true,
							approvalBean.getTran_id());
				}
			} else {
				claimAndCompleteTask(bean, bean.getApprover());
				if (redirectAttributes != null)
					redirectAttributes.addFlashAttribute("msg", "Task completed successfully.");

				if ("D".equalsIgnoreCase(bean.getApproved()) && (approvalBean.getApprovalType().equals("32")
						|| approvalBean.getApprovalType().equals("300"))) {
					String scheme_id = "";
					if (approvalBean.getApprovalType().equals("300")) {
						Art_Sch_Vld_Ext vld_ext = this.article_scheme_master_service
								.getVldExtenById(approvalBean.getTran_id());
						scheme_id = vld_ext.getScheme_slno().toString();
					} else {
						scheme_id = approvalBean.getTran_id().toString();
					}
					List<trd_scheme_mst_hdr> hdrList = this.article_Scheme_master_Repository
							.get_HDR_Data_For_Edit(scheme_id);
					this.article_scheme_master_service.send_Mailfor_After_Discard_Creator(
							hdrList.get(0).getCreated_by(), hdrList.get(0).getLast_mod_by(), bean.getApprover(),
							hdrList.get(0).getTrd_sch_slno(), approvalBean.getApprovalType().equals("300"), false,
							approvalBean.getTran_id());
				}
			}
			
			
			
			
			if(!approvalBean.getApprovalType().equals("32")
						&& !approvalBean.getApprovalType().equals("300")) {
				
				sendMail(request, bean.getTaskid(), bean.getApprover(), bean.getApproved(), nextApprover,
						bean.getRemark());
			}
			
			
			

		} catch (ActivitiException e) {
			e.printStackTrace();
			System.out.println("Activity Error Occurred :" + e.getMessage());// uncomment asneeded
			if (redirectAttributes != null) // --;
				redirectAttributes.addFlashAttribute("errmsg",
						"Task not completed successfully.Error " + e.getMessage());
			throw e;
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("Error Occurred :" + e.getMessage());// uncomment asneeded --;
			if (redirectAttributes != null)
				redirectAttributes.addFlashAttribute("errmsg",
						"Task not completed successfully.Error " + e.getMessage());
			throw e;
		}
		return "redirect:/main";
	}

//	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	private void claimAndCompleteTask(ApprovalBean bean, String username) throws NumberFormatException, Exception {
		Map<String, Object> variables = new HashMap<String, Object>();
		variables.put("motivation", bean.getMotivation());
		variables.put("approved", bean.getApproved());
		variables.put("reason_id", bean.getReason_id());
		variables.put("escalatetime", null);
		variables.put("description", bean.getRemark());

		System.out.println("TASK ID" + bean.getTaskid());
		taskService.claim(bean.getTaskid(), username);
		taskService.complete(bean.getTaskid(), variables);
		// delete record from activiti table ACT_RU_TASK
		taskMasterService.UPDATE_ACT_RU_TASK_ASSIGNEE(Long.parseLong(bean.getTaskid().trim()));
	}

//	private void claimTask(ApprovalBean bean, String username, String nextApprover)
//			throws NumberFormatException, Exception {
//		Map<String, Object> variables = new HashMap<String, Object>();
//		variables.put("motivation", bean.getMotivation());
//		variables.put("approved", bean.getApproved());
//		variables.put("reason_id", bean.getReason_id());
//		variables.put("escalatetime", null);
//		variables.put("description", bean.getRemark());
//		System.out.println("TASK ID" + bean.getTaskid());
//
//		taskService.claim(bean.getTaskid(), username);
//		// delete record from activiti table ACT_RU_TASK
//		taskMasterService.UPDATE_ACT_RU_TASK_ASSIGNEE_MULTI_LEVEL(nextApprover,
//				Long.parseLong(bean.getTaskid().trim()));
//	}

	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	private void claimAndDiscardTask(ApprovalBean bean, String username) throws NumberFormatException, Exception {
		Map<String, Object> variables = new HashMap<String, Object>();
		variables.put("motivation", bean.getMotivation());
		variables.put("approved", bean.getApproved());
		variables.put("reason_id", bean.getReason_id());
		variables.put("escalatetime", null);
		variables.put("description", bean.getRemark());
		System.out.println("TASK ID" + bean.getTaskid());
		// delete record from activiti table ACT_RU_TASK
		taskMasterService.UPDATE_ACT_RU_TASK_ASSIGNEE(Long.parseLong(bean.getTaskid().trim()));
	}

	@RequestMapping(value = "/saveApprovalDataList")
	// @Transactional(propagation = Propagation.REQUIRED, rollbackFor =
	// Exception.class)
	public Map<String, Object> saveApprovalDataList(@RequestParam("task_id") List<String> taskIds,
			@RequestParam("decision") String decision, @RequestParam("userName") String userName,
			@RequestParam("remark") String remark, @RequestParam("tran_id") String tran_id, HttpServletRequest request)
			throws InterruptedException {
		
		
		System.out.println(
				"save approval data list method ******** remark " + remark + taskIds + decision + userName + remark);
		String approvalType = "";
		String msg = "Your response has been submitted successfully";
		Map<String, Object> resultMap = new HashMap<String, Object>();
		
		boolean flag = false;
		if (taskIds != null) {
			System.out.println("List found");
			ApprovalBean approvalBean = new ApprovalBean();
			approvalBean.setMotivation("");
			approvalBean.setApproved("approve".equalsIgnoreCase(decision) ? "A" : "D");
			approvalBean.setApprover(userName);
			approvalBean.setRemark(remark);
			approvalBean.setApprovalType(approvalType);
			try {
				Callable<Boolean> testcall = new Callable<Boolean>() {

					@Override
					public Boolean call() throws Exception {
						boolean completionflg = false;
						try {
							Long l1 = System.currentTimeMillis();
							for (String taskId : taskIds) {
								approvalBean.setTaskid(taskId);
								approvalBean.setInv_grp(null);
								simpleProcessTestData(request, new RedirectAttributesModelMap(), approvalBean);
								System.out.println(
										"Callable Task Id - " + approvalBean.getTaskid() + " has been processed");
							}
							System.out.println(" Callable Time taken for " + taskIds.size() + " task approval in ms - "
									+ (System.currentTimeMillis() - l1));
							completionflg = true;
						} catch (Exception e) {
							// TODO:handle exception
							completionflg = false;
							throw e;
						}
						return completionflg;
					}

				};
				FutureTask<Boolean> userFuture = new FutureTask<>(testcall);
				new Thread(userFuture).start();

				if (userFuture.get()) {
					resultMap.put("status", "success");
					resultMap.put("msg", "Approved Succesfully");
					resultMap.put("decision", decision);
				} else {
					resultMap.put("status", "fail");
					resultMap.put("decision", decision);
					System.out.println("Main Failed 1111");
				}
			} catch (Exception e) {
				// TODO: handle exception
				e.printStackTrace();
				resultMap.put("status", "fail");
				resultMap.put("decision", decision);
				System.out.println("Main Failed");
			}
		}

//		if (taskIds != null) {
//			long startTime = System.currentTimeMillis();
//			ExecutorService executorService = Executors.newCachedThreadPool(); // Use cached thread pool for dynamic
//																				// scaling
//			List<CompletableFuture<Void>> futures = new ArrayList<>();
//			for (String taskId : taskIds) {
//				CompletableFuture<Void> future = CompletableFuture.runAsync(() -> {
//					try {
//						ApprovalBean approvalBean = new ApprovalBean();
//						approvalBean.setMotivation("");
//						approvalBean.setApproved("approve".equalsIgnoreCase(decision) ? "A" : "D");
//						approvalBean.setApprover(userName);
//						approvalBean.setRemark(remark);
//						approvalBean.setApprovalType(approvalType);
//
//						approvalBean.setTaskid(taskId);
//						approvalBean.setInv_grp(null);
//						simpleProcessTestData(request, new RedirectAttributesModelMap(), approvalBean);
//						System.out.println("Task Id - " + approvalBean.getTaskid() + " has been claimed and completed");
//					} catch (Exception e) {
//						e.printStackTrace();
//						
//						System.out.println("Task Id - " + taskId + " has failed");
//					}
//				}, executorService);
//				futures.add(future);
//			}
//			// Wait for all CompletableFuture to complete
//			CompletableFuture.allOf(futures.toArray(new CompletableFuture[0])).thenRun(() -> {
//				System.out.println("All tasks completed successfully");
//			}).exceptionally(ex -> {
//				ex.printStackTrace();
//				return null;
//			}).join();
//			// Shutdown the executor service
//			executorService.shutdown();
//			long endTime = System.currentTimeMillis();
//			long elapsedTime = endTime - startTime;
//			System.out.println("Elapsed time in milliseconds: " + elapsedTime);
//		}

		return resultMap;
	}

	@RequestMapping(value = "/saveApprovalDataListViaMail")
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public String saveApprovalDataListViaMail(@RequestParam("task_id") List<String> taskIds,
			@RequestParam("decision") String decision, @RequestParam("userName") String userName,
			@RequestParam("remark") String remark, @RequestParam("tran_id") String tran_id,
			@RequestParam(name = "token", required = false) String token, HttpServletRequest request)
			throws InterruptedException {
		System.out.println(
				"save approval data list method ******** remark " + remark + taskIds + decision + userName + remark);
		String ipAddr = request.getRemoteAddr();

		// pfizer live static ip = 204.114.196.5
		// excel office static ip 103.142.107.59
		if (!"204.114.196.5".equals(ipAddr.trim())) {
			return "Access Denied.";
		}

		String approvalType = "";
		String msg = "Your response has been submitted successfully";

		// check if already processed
		if (token != null) {
			if (this.appr_mail_vali_service.checkIfTransactionAlreadyProcessed(Long.valueOf(tran_id),
					Long.valueOf(taskIds.get(0)))) {
				msg = "Transaction already processed by another user !!!!";
				return msg;
			}
		}
		// add check to validate user

		Map<String, Object> resultMap = new HashMap<String, Object>();

		if (taskIds != null) {
			System.out.println("List found");
			ApprovalBean approvalBean = new ApprovalBean();
			approvalBean.setMotivation("");
			approvalBean.setApproved("approve".equalsIgnoreCase(decision) ? "A" : "D");
			approvalBean.setApprover(userName);
			approvalBean.setRemark(remark);
			approvalBean.setApprovalType(approvalType);
			try {
				Thread t = new Thread(new MyRunner(taskIds, approvalBean, request, approvalType, null));
				t.start();
				t.join();
				resultMap.put("status", "success");

				// update object
				// get object by task_id , tran_ref_id and decrypted user_id
				if (token != null) {
					this.appr_mail_vali_service.updateAfterProcessing(Long.valueOf(tran_id),
							Long.valueOf(taskIds.get(0)), new String(Base64.getDecoder().decode(token)), ipAddr, "Y");
				}
			} catch (Exception e) {
				// TODO: handle exception
				e.printStackTrace();
				resultMap.put("status", "fail");
				System.out.println("Main Failed");
			}
		}
		resultMap.put("msg", "Approved Succesfully via mail.");
		return msg;
	}

	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	class MyRunner implements Runnable {

		List<String> taskIds;
		ApprovalBean approvalBean;
		HttpServletRequest request;
		String approvalType;
		Long inv_grp;

		public MyRunner(List<String> taskIds, ApprovalBean approvalBean, HttpServletRequest request,
				String approvalType, Long inv_grp) {

			this.approvalBean = approvalBean;
			this.taskIds = taskIds;
			this.request = request;
			this.approvalType = approvalType;
			this.inv_grp = inv_grp;
		}

		@Override
		public void run() {
			try {
				Long l1 = System.currentTimeMillis();
				for (String taskId : taskIds) {
					approvalBean.setTaskid(taskId);
					approvalBean.setInv_grp(inv_grp);
					simpleProcessTestData(request, new RedirectAttributesModelMap(), approvalBean);
					System.out.println("Task Id - " + approvalBean.getTaskid() + " has been claimed and completed");
				}
				System.out.println("Time taken for " + taskIds.size() + " task approval in ms - "
						+ (System.currentTimeMillis() - l1));
			} catch (Exception e) {
				// TODO: handle exception
				Thread t = Thread.currentThread();
				t.getUncaughtExceptionHandler().uncaughtException(t, e);
			}
//    @GetMapping("/complete-task/{processInstanceId}")
//    public void completeTaskA(@PathVariable String processInstanceId) {
//        Task task = taskService.createTaskQuery()
//          .processInstanceId(processInstanceId)
//          .singleResult();
//        taskService.complete(task.getId());
//    }
		}
	}

	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public void sendMail(HttpServletRequest request, String taskId, String approvar, String status, String nextApprovar,
			String remark) throws Exception {
		ApprovalBean approvalBean = taskMasterService.getTranIdByTaskId(taskId);
		String approvalType = approvalBean.getApprovalType();
		System.out.println("approvalType " + approvalType);
		Company company = companyMasterService.getCompanyDetails();

		// get full tran name from acc_tran_type by tran type
		String full_tran_name = approvalBean.getFull_tran_name();

//		if(approvalType.equals(MedicoConstants.GRN_APPR_PFZ_PS)|| approvalType.equals(MedicoConstants.GRN_APPR_PFZ_PI) ||approvalType.equals(MedicoConstants.GRN_APPR_PIPL_PS) || approvalType.equals(MedicoConstants.GRN_APPR_PIPL_PI)) {
//		  this.sendMail.sendMail(approvalBean.getTran_id(),approvar,"higherLevel",taskId,status,nextApprovar,Long.valueOf(approvalBean.getFin_year_id()),approvalType,company.getCompany());
//		}
//		else if(approvalType.equals(MedicoConstants.ALLOC_APPR_ALL_PFZ)|| approvalType.equals(MedicoConstants.ALLOC_APPR_ALL_PIPL)) {
//			this.sendMail.sendMail(approvalBean.getTran_id(),approvar, "higherLevel",taskId,status,nextApprovar,approvalBean.getFin_year_id(),approvalType,company.getCompany());
//		}
//		else if(approvalType.equals(MedicoConstants.SPECIAL_APPR_PFZ)|| approvalType.equals(MedicoConstants.SPECIAL_APPR_PIPL)) {
//			this.specialAllocationService.sendMailForSpecialAllocation(approvalBean.getTran_id(),approvar, "higherLevel",taskId,status,nextApprovar,Long.valueOf(approvalBean.getFin_year_id()),approvalType,company.getCompany(),request.getRemoteAddr());
//			if(remark!=null && remark.split(",").length>1) {
//				System.out.println("length "+remark.length());
//				if(remark.split(",")[2]!="" && remark.split(",")[2]!=null) {
//					String type=remark.split(",")[1];
//					BigDecimal value=BigDecimal.valueOf(Double.valueOf(remark.split(",")[2]));
//					this.specialAllocationService.updateForFrieght(approvalBean.getTran_id(), type,value);
//				}
//			}
//		}
//		else if(approvalType.equals(MedicoConstants.ANNUAL_APPR_PFZ)|| approvalType.equals(MedicoConstants.ANNUAL_APPR_PIPL)) {
//			this.sendMail.sendMail(approvalBean.getTran_id(),approvar, "higherLevel",taskId,status,nextApprovar,approvalBean.getFin_year_id(),approvalType,company.getCompany());
//		}
//		else if(approvalType.equals(MedicoConstants.DSP_APPR_PFZ)|| approvalType.equals(MedicoConstants.DSP_APPR_PIPL)) {
//			String dspType=this.dispatchService.getDispatchType(approvalBean.getTran_id());
//			System.out.println("dspType "+dspType);
//			if(DISPATCH_TYPE.contains(dspType.trim())) {
//				this.sendMail.sendMail(approvalBean.getTran_id(),approvar, "higherLevel",taskId,status,nextApprovar,Long.valueOf(approvalBean.getFin_year_id()),approvalType,company.getCompany());
//			}
//		}
//		else if(approvalType.equals(MedicoConstants.ANNUAL_APPR_TEAM_PFZ)|| approvalType.equals(MedicoConstants.ANNUAL_APPR_TEAM_PIPL)) {
//			this.sendMail.sendMail(approvalBean.getTran_id(),approvar, "higherLevel",taskId,status,nextApprovar,approvalBean.getFin_year_id(),approvalType,company.getCompany());
//		}
//		else if(approvalType.equals(MedicoConstants.IAA_APPR_PFZ)|| approvalType.equals(MedicoConstants.IAA_APPR_PIPL)) {
//			this.sendMail.sendMail(approvalBean.getTran_id(),approvar, "higherLevel",taskId,status,nextApprovar,approvalBean.getFin_year_id(),approvalType,company.getCompany());
//
//		}

		// new logic
		if (full_tran_name.trim().equalsIgnoreCase(GRN_FULL_TRAN_NAME)) {
			this.sendMail.sendMail(approvalBean.getTran_id(), approvar, "higherLevel", taskId, status, nextApprovar,
					Long.valueOf(approvalBean.getFin_year_id()), approvalType, company.getCompany(),
					full_tran_name.trim());
		} else if (full_tran_name.trim().equalsIgnoreCase(ALLOC_TRAN_NAME)) {
			this.sendMail.sendMail(approvalBean.getTran_id(), approvar, "higherLevel", taskId, status, nextApprovar,
					approvalBean.getFin_year_id(), approvalType, company.getCompany(), full_tran_name.trim());
		} else if (full_tran_name.trim().equalsIgnoreCase(SPLALLOC)) {
			this.specialAllocationService.sendMailForSpecialAllocation(approvalBean.getTran_id(), approvar,
					"higherLevel", taskId, status, nextApprovar, Long.valueOf(approvalBean.getFin_year_id()),
					approvalType, company.getCompany(), request.getRemoteAddr());
			if (remark != null && remark.split(",").length > 1) {
				System.out.println("length " + remark.length());
				if (remark.split(",")[2] != "" && remark.split(",")[2] != null) {
					String type = remark.split(",")[1];
					BigDecimal value = BigDecimal.valueOf(Double.valueOf(remark.split(",")[2]));
					this.specialAllocationService.updateForFrieght(approvalBean.getTran_id(), type, value);
				}
			}
		} else if (full_tran_name.trim().equalsIgnoreCase(ANN_SAMPLE_PLAN)) {
			this.sendMail.sendMail(approvalBean.getTran_id(), approvar, "higherLevel", taskId, status, nextApprovar,
					approvalBean.getFin_year_id(), approvalType, company.getCompany(), full_tran_name.trim());
		} else if (full_tran_name.trim().equalsIgnoreCase(DISPATCH_FULL_TRAN_NAME)) {
			String dspType = this.dispatchService.getDispatchType(approvalBean.getTran_id());
			System.out.println("dspType " + dspType);
			if (DISPATCH_TYPE.contains(dspType.trim())) {
				this.sendMail.sendMail(approvalBean.getTran_id(), approvar, "higherLevel", taskId, status, nextApprovar,
						Long.valueOf(approvalBean.getFin_year_id()), approvalType, company.getCompany(),
						full_tran_name.trim());
			}
		} else if (full_tran_name.trim().equalsIgnoreCase(ASP_TEAM_CONSOLIDATION)) {
			this.sendMail.sendMail(approvalBean.getTran_id(), approvar, "higherLevel", taskId, status, nextApprovar,
					approvalBean.getFin_year_id(), approvalType, company.getCompany(), full_tran_name.trim());
		} else if (full_tran_name.trim().equalsIgnoreCase(IAA_TRAN_NAME)) {
			this.sendMail.sendMail(approvalBean.getTran_id(), approvar, "higherLevel", taskId, status, nextApprovar,
					approvalBean.getFin_year_id(), approvalType, company.getCompany(), full_tran_name.trim());
		} else if (full_tran_name.trim().equalsIgnoreCase(SCM_MST_APPR)) {
			List<trd_scheme_mst_hdr> hdrList = this.article_Scheme_master_Repository
					.get_HDR_Data_For_Edit(approvalBean.getTran_id().toString());
			System.out.println(hdrList.get(0));
			article_scheme_master_service.sendmails(hdrList.get(0), SCM_MST_APPR, null, "nextApproval",
					company.getCompany(), nextApprovar, "", false, null);
		} else if (full_tran_name.trim().equalsIgnoreCase(SCM_MST_VLD_EXT)) {
			Art_Sch_Vld_Ext art_sch_vld_ext = this.article_scheme_master_service
					.getVldExtenById(approvalBean.getTran_id());
			List<trd_scheme_mst_hdr> hdrList = this.article_Scheme_master_Repository
					.get_HDR_Data_For_Edit(art_sch_vld_ext.getScheme_slno().toString());
			article_scheme_master_service.sendmails(hdrList.get(0), SCM_MST_APPR, null, "nextApproval",
					company.getCompany(), nextApprovar, "", true, art_sch_vld_ext);
		}
	}

	@GetMapping("/getApprovalDetails")
	public Map<String, Object> getApprovalsByTRansactionTypes(@RequestParam("transactionType") String tranType,
			@RequestParam("divId") Long divId, HttpSession session) throws Exception {
		Map<String, Object> map = new HashMap<>();
		List<ApprLevelBean> list = null;
		List<ApprLevelBean> approvalData = null;
		List<ApprLevelBean> approvers = null;
		String role = "";
		try {
			String comp_code = session.getServletContext().getAttribute(COMPANY_CODE).toString();

			if (comp_code.trim().equals("PFZ")) {
				if (tranType.equals("15") || tranType.equals("16"))
					role = "EG0021";
				else if (tranType.equals("17") || tranType.equals("18"))
					role = "EG0016";
			}

			if (comp_code.trim().equals("SER")) {
				if (tranType.equals("15") || tranType.equals("16"))
					role = "EG0017";
				else if (tranType.equals("17") || tranType.equals("18"))
					role = "EG0012";
			}

			approvers = taskMasterService.getApprovers(divId, role, tranType);
			approvalData = taskMasterService.getApprovalDetails(divId, tranType);

			map.put("approvalData", approvalData);
			map.put("approvers", approvers);
		} catch (Exception e) {
			System.out.println("Error Occurred :" + e.getMessage());// uncomment asneeded --;
		}

		return map;
	}

	@PostMapping("/saveApprovals")
	public Map<String, Object> saveApprovals(@RequestBody ApprLevelBean bean, HttpSession session,
			HttpServletRequest request) throws Exception {
		Map<String, Object> map = new HashMap<>();
		try {
			Company company = (Company) session.getServletContext().getAttribute(COMPANY_DATA);
			String companyCode = session.getServletContext().getAttribute(COMPANY_CODE).toString();
			bean.setIpAddress(request.getRemoteAddr());
			bean.setCompanyCode(companyCode);
			this.taskMasterService.setApprovalFromUi(bean);
			map.put(DATA_SAVED, false);
			map.put(RESPONSE_MESSAGE, "Saved Successfully");
		} catch (Exception e) {
			System.out.println("Error Occurred :" + e.getMessage());// uncomment asneeded --;
			map.put(DATA_SAVED, false);
			map.put(RESPONSE_MESSAGE, "Error Occurred !! Error Code : " + e.getMessage());
		}

		return map;
	}

	@RequestMapping(value = "/saveArticleSchemeApprovalViaMail")
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public String saveArticleSchemeApprovalViaMail(@RequestParam("task_id") List<String> taskIds,
			@RequestParam("decision") String decision, @RequestParam("userName") String userName,
			@RequestParam("remark") String remark, @RequestParam("tran_id") String tran_id,
			@RequestParam(name = "token", required = false) String token, HttpServletRequest request)
			throws NumberFormatException, Exception {
		System.out.println(
				"save approval data list method ******** remark " + remark + taskIds + decision + userName + remark);
		String ipAddr = request.getRemoteAddr();
		// pfizer live static ip = 204.114.196.5
		// excel office static ip 103.142.107.59
		if (!"204.114.196.5".equals(ipAddr.trim()) && !"38.205.190.195".equals(ipAddr.trim())) {
			return "Access Denied.";
		}

		String approvalType = "";
		String msg = "Your response has been submitted successfully";

		// check if already processed
		if (token != null) {
			if (this.appr_mail_vali_service.checkIfTransactionAlreadyProcessed(Long.valueOf(tran_id),
					Long.valueOf(taskIds.get(0)))) {
				msg = "Transaction already processed by another user !!!!";
				return msg;
			} else {
				// check if already approved through UI
				ArticleSchemeRequestHeader req = this.artSchmDelRepo.getHeaderById(Long.valueOf(tran_id));
				if (req != null) {
					if (req.getArtsch_appr_status().equalsIgnoreCase("A")) {
						msg = "Transaction already processed by another user !!!!";
						return msg;
					}
				} else {
					msg = "Transaction already processed by another user !!!!";
					return msg;
				}
			}
		}
		// add check to validate user

		Map<String, Object> resultMap = new HashMap<String, Object>();

		if (taskIds != null) {
			System.out.println("List found");
			ApprovalBean approvalBean = new ApprovalBean();
			approvalBean.setMotivation("");
			approvalBean.setApproved("approve".equalsIgnoreCase(decision) ? "A" : "D");
			approvalBean.setApprover(userName);
			approvalBean.setRemark(remark);
			approvalBean.setApprovalType(approvalType);
			try {
				Thread t = new Thread(new MyRunner(taskIds, approvalBean, request, approvalType, null));
				t.start();
				t.join();
				resultMap.put("status", "success");
				if (token != null) {
					this.appr_mail_vali_service.updateAfterProcessing(Long.valueOf(tran_id),
							Long.valueOf(taskIds.get(0)), new String(Base64.getDecoder().decode(token)), ipAddr, "Y");
				}
			} catch (Exception e) {
				// TODO: handle exception
				e.printStackTrace();
				resultMap.put("status", "fail");
				System.out.println("Main Failed");
			}
		}
		resultMap.put("msg", "Approved Succesfully via mail.");
		return msg;
	}

	// mail -asrticle scheme

	@RequestMapping(value = "/saveArticleSchemeMasterApprovalViaMail")
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public String saveArticleSchemeMasterApprovalViaMail(@RequestParam("task_id") List<String> taskIds,
			@RequestParam("decision") String decision, @RequestParam("userName") String userName,
			@RequestParam("remark") String remark, @RequestParam("tran_id") String tran_id,
			@RequestParam(name = "token", required = false) String token,
			@RequestParam(name = "tranType", required = false) String tranType, HttpServletRequest request,
			HttpSession session) throws NumberFormatException, Exception {
		System.err.println("task id:::" + tranType);
		System.err.println("userName id:::" + userName);

		String ipAddr = request.getRemoteAddr();
		// pfizer live static ip = 204.114.196.5
		// excel office static ip 103.142.107.59
		if (!"204.114.196.5".equals(ipAddr.trim()) && !"38.205.190.195".equals(ipAddr.trim())
				&& !"106.222.207.232".equals(ipAddr.trim()) && !"0:0:0:0:0:0:0:1".equals(ipAddr.trim())) {
			return "Access Denied.";
		}

		String approvalType = "";
		String msg = "Your response has been submitted successfully";
		String companyCode = session.getServletContext().getAttribute(COMPANY_CODE).toString();

		List<trd_scheme_mst_hdr> hdrList = new ArrayList<>();
		trd_scheme_mst_hdr hdr;
		// check if already processed

		if (token != null) {
			if (this.appr_mail_vali_service.checkIfTransactionAlreadyProcessed(Long.valueOf(tran_id),
					Long.valueOf(taskIds.get(0)))) {
				msg = "Transaction already processed by another user !!!!";
				return msg;
			} else {
				hdrList = this.article_Scheme_master_Repository.get_HDR_Data_For_Edit(tran_id.toString());
				// check if already approved through UI
				hdr = hdrList.get(0);
				if (hdr != null) {
					if (hdr.getTrd_sch_status().equalsIgnoreCase("A")) {
						msg = "Transaction already processed by another user !!!!";
						return msg;
					}
				} else {
					msg = "Invalid Transaction";
					return msg;
				}
			}
		}

		// add check to validate user
		Map<String, Object> resultMap = new HashMap<String, Object>();
		if (taskIds != null) {
			System.out.println("List found");
			ApprovalBean approvalBean = new ApprovalBean();
			approvalBean.setMotivation("");
			approvalBean.setApproved("approve".equalsIgnoreCase(decision) ? "A" : "D");
			approvalBean.setApprover(userName);
			approvalBean.setRemark(remark);
			approvalBean.setApprovalType(approvalType);

			System.err.println("approvalBean:" + approvalBean.getUser_id());
			System.err.println("approvalBean:" + approvalBean.getIp_addres());
			System.err.println("approvalBean:" + approvalBean.getUser_id());

			try {
				Thread t = new Thread(new MyRunner(taskIds, approvalBean, request, approvalType, null));
				t.start();
				t.join();

				resultMap.put("status", "success");

			} catch (Exception e) {
				e.printStackTrace();
				resultMap.put("status", "fail");
				System.out.println("Main Failed");
			}
		}

		resultMap.put("msg", "Approved Succesfully via mail.");
		return msg;
	}

	@RequestMapping(value = "/saveSchemeValidityExtensionApprovalViaMail")
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public String saveSchemeValidityExtensionApprovalViaMail(@RequestParam("task_id") List<String> taskIds,
			@RequestParam("decision") String decision, @RequestParam("userName") String userName,
			@RequestParam("remark") String remark, @RequestParam("tran_id") String tran_id,
			@RequestParam(name = "token", required = false) String token,
			@RequestParam(name = "tranType", required = false) String tranType, HttpServletRequest request,
			HttpSession session) throws NumberFormatException, Exception {
		System.err.println("task id:::" + tranType);
		System.err.println("userName id:::" + userName);

		String ipAddr = request.getRemoteAddr();

		if (!"204.114.196.5".equals(ipAddr.trim()) && !"38.205.190.195".equals(ipAddr.trim())
				&& !"106.222.207.232".equals(ipAddr.trim()) && !"0:0:0:0:0:0:0:1".equals(ipAddr.trim())) {
			return "Access Denied.";
		}

		String approvalType = "";
		String msg = "Your response has been submitted successfully";
		String companyCode = session.getServletContext().getAttribute(COMPANY_CODE).toString();

		if (token != null) {
			if (this.appr_mail_vali_service.checkIfTransactionAlreadyProcessed(Long.valueOf(tran_id),
					Long.valueOf(taskIds.get(0)))) {
				msg = "Transaction already processed by another user !!!!";
				return msg;
			} else {
				Art_Sch_Vld_Ext artsch_vldext = this.article_scheme_master_service
						.getVldExtenById(Long.valueOf(tran_id));
				// check if already processed
				if (artsch_vldext != null) {
					if (artsch_vldext.getExt_appr_status().equalsIgnoreCase("A")) {
						msg = "Transaction already processed by another user !!!!";
						return msg;
					}
				} else {
					msg = "Invalid Transaction";
					return msg;
				}
			}
		}

		// add check to validate user
		Map<String, Object> resultMap = new HashMap<String, Object>();
		if (taskIds != null) {
			System.out.println("List found");
			ApprovalBean approvalBean = new ApprovalBean();
			approvalBean.setMotivation("");
			approvalBean.setApproved("approve".equalsIgnoreCase(decision) ? "A" : "D");
			approvalBean.setApprover(userName);
			approvalBean.setRemark(remark);
			approvalBean.setApprovalType(approvalType);

			System.err.println("approvalBean:" + approvalBean.getUser_id());
			System.err.println("approvalBean:" + approvalBean.getIp_addres());
			System.err.println("approvalBean:" + approvalBean.getUser_id());

			try {
				Thread t = new Thread(new MyRunner(taskIds, approvalBean, request, approvalType, null));
				t.start();
				t.join();

				resultMap.put("status", "success");

			} catch (Exception e) {
				e.printStackTrace();
				resultMap.put("status", "fail");
				System.out.println("Main Failed");
			}
		}

		resultMap.put("msg", "Approved Succesfully via mail.");
		return msg;
	}

}
