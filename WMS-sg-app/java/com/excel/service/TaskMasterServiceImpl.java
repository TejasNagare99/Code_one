package com.excel.service;

import java.math.BigDecimal;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.excel.bean.ApprLevelBean;
import com.excel.bean.ApprovalBean;
import com.excel.bean.InboxTask;
import com.excel.bean.MailBean;
import com.excel.model.Location;
import com.excel.model.TaskMaster;
import com.excel.model.Task_Master_Dtl;
import com.excel.repository.TaskMasterRepository;
import com.excel.repository.TransactionalRepository;
import com.excel.utility.MedicoConstants;

@Service
public class TaskMasterServiceImpl implements TaskMasterService {

	@Autowired
	TaskMasterRepository taskMasterRepository;
	@Autowired
	TransactionalRepository transactionalRepository;
	@Autowired
	LocationService locationService;

	@Override
	public List<TaskMaster> getTask(Long inv_group, Long level, Long div_id, String tran_type, BigDecimal amount,
			BigDecimal value, String taskFlow, Long fs_id, String approvalType) {
		
		
		return taskMasterRepository.getTask(inv_group, level, div_id, tran_type, amount, value, taskFlow, fs_id,
				approvalType);
	}

	@Override
	public List<Task_Master_Dtl> getTaskDetail(Long task_id) {
		return taskMasterRepository.getTaskDetail(task_id);
	}

	@Override
	public String getIdentityLinkType(Long div_id) {
		return taskMasterRepository.getIdentityLinkType(div_id);
	}

	@Override
	public List<InboxTask> getTaskList(List<String> task_ids, String identitylink, String erp_cust_outstanding)
			throws ParseException {
		return taskMasterRepository.getTaskList(task_ids, identitylink, erp_cust_outstanding);
	}

	@Override
	public List<InboxTask> getNotificationList(String user_id) throws ParseException {
		return taskMasterRepository.getNotificationList(user_id);
	}

	@Override
	public void saveHoldApproval(ApprovalBean approvalBean) {
		taskMasterRepository.saveHoldApproval(approvalBean);
	}

	@Override
	public void updateHoldApproval(ApprovalBean approvalBean, boolean isInactive) {
		taskMasterRepository.updateHoldApproval(approvalBean, isInactive);
	}

	@Override
	public String getHoldApprovalRemark(Long tranId, Long tranType) {
		return taskMasterRepository.getHoldApprovalRemark(tranId, tranType);
	}

	@Override
	public String getNextApproverPOByTaskId(String taskid, String tran_type, Long inv_grp) {
		return taskMasterRepository.getNextApproverPOByTaskId(taskid, tran_type, inv_grp);
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public void updateApprovalLevelCustomTask(String taskId, String username) throws Exception {
		taskMasterRepository.updateApprovalLevelCustomTask(taskId, username);
	}

	@Override
	public List<Object[]> getInvoicingGroupsByTranTypeAndIdentityLinkTypeVal(String tranType, String username)
			throws Exception {
		return taskMasterRepository.getInvoicingGroupsByTranTypeAndIdentityLinkTypeVal(tranType, username);
	}

	@Override
	public List<String> getTranTypesByUsername(String username) throws Exception {
		return taskMasterRepository.getTranTypesByUsername(username);
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public void UPDATE_ACT_RU_TASK_ASSIGNEE(Long id) throws Exception {
		taskMasterRepository.UPDATE_ACT_RU_TASK_ASSIGNEE(id);
	}

	@Override
	public List<Object> getTaskDetailsByInvoicingGroupsAndTranType(Long tranType, List<Long> invoicingGroups)
			throws Exception {
		return taskMasterRepository.getTaskDetailsByInvoicingGroupsAndTranType(tranType, invoicingGroups);
	}

	@Override
	public List<Task_Master_Dtl> getTaskDetailsByTaskIds(List<Long> taskIds) throws Exception {
		return taskMasterRepository.getTaskDetailsByTaskIds(taskIds);
	}

	@Override
	public void revertCustomTask(Long tran_id, String tran_type) throws Exception {
		taskMasterRepository.revertCustomTask(tran_id, tran_type);
	}

	@Override
	public List<InboxTask> getNotificationPendApprList(String user_id, Date start_date, Date end_date, Boolean isHoUser)
			throws ParseException {
		return taskMasterRepository.getNotificationPendApprList(user_id, start_date, end_date, isHoUser);
	}

	@Override
	public List<TaskMaster> getAlternateTask(Long tran_id, Long inv_group, String tran_type) throws Exception {
		return taskMasterRepository.getAlternateTask(tran_id, inv_group, tran_type);
	}

	@Override
	public List<TaskMaster> getTaskBetweenValue1FromAndTo(Long inv_group, String tran_type, BigDecimal currinv_amt,
			String logic_type, Long appr_level) throws Exception {
		return taskMasterRepository.getTaskBetweenValue1FromAndTo(inv_group, tran_type, currinv_amt, logic_type,
				appr_level);
	}

	@Override
	public List<TaskMaster> getTaskWithoutCondition(Long inv_group, String tran_type, String logic_type,
			Long appr_level) throws Exception {
		return taskMasterRepository.getTaskWithoutCondition(inv_group, tran_type, logic_type, appr_level);
	}

	@Override
	public ApprovalBean getTranIdByTaskId(String task_id) {
		// TODO Auto-generated method stub
		return taskMasterRepository.getTranIdByTaskId(task_id);
	}

	@Override
	public List<MailBean> getMailSendDetails(Long id, String nextApprovar, String tranType) throws Exception {
		// TODO Auto-generated method stub
		return taskMasterRepository.getMailSendDetails(id, nextApprovar, tranType);
	}

	@Override
	public List<MailBean> getApprovalDetails(Long taskId) {

		return taskMasterRepository.getApprovalDetails(taskId);
	}

	@Override
	public TaskMaster getObjectById(Long taskId) throws Exception {
		// TODO Auto-generated method stub
		return taskMasterRepository.getObjectById(taskId);
	}

	@Override
	public Task_Master_Dtl getObjectByIdTask_Master_Dtl(Long taskDtlId) throws Exception {
		// TODO Auto-generated method stub
		return taskMasterRepository.getObjectByIdTask_Master_Dtl(taskDtlId);
	}

	@SuppressWarnings("unchecked")
	@Override
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public void createApprovals(Long allocId, String approvalLevels, String user) throws Exception {
		TaskMaster taskMaster = null;
		Task_Master_Dtl detail = null;
		String tranType = null;
		List<String> list = null;
		Long level = 1L;

		Location location = locationService.getLocationNameByEmployeeId(user);
		if (9L == location.getLoc_id()) {
			tranType = MedicoConstants.SPECIAL_APPR_PIPL;
		} else {
			tranType = MedicoConstants.SPECIAL_APPR_PFZ;
		}
		String[] approvals = approvalLevels.split(",");
		list = new ArrayList(Arrays.asList(approvals));
		for (String empId : list) {
			System.out.println("empId " + empId);
			if (empId != null && !empId.isEmpty()) {
				taskMaster = new TaskMaster();
				taskMaster.setCompany_cd("PFZ");
				taskMaster.setDiv_id(1L);
				taskMaster.setTask_name("SpecialAllocation" + allocId);
				taskMaster.setTask_descr("SpecialAllocation" + allocId);
				taskMaster.setApproval_req("Y");
				taskMaster.setCompletion_time(10);
				taskMaster.setReminder_time(4);
				taskMaster.setCreated_date(new Date());
				taskMaster.setCreated_by(user);
				taskMaster.setLast_mod_date(new Date());
				taskMaster.setLast_mod_by(user);
				taskMaster.setApproval_type("001");
				taskMaster.setInv_grp_id(0L);
				taskMaster.setAppr_level(level);
				taskMaster.setValue_range_from(BigDecimal.ZERO);
				taskMaster.setValue_range_to(BigDecimal.ZERO);
				taskMaster.setPercentage_range_from(BigDecimal.ZERO);
				taskMaster.setPercentage_range_to(BigDecimal.ZERO);
				taskMaster.setTran_type(tranType);
				taskMaster.setFs_id(allocId);// tran_id from REQ_HEADER is set here
				this.transactionalRepository.persist(taskMaster);

				detail = new Task_Master_Dtl();
				detail.setTask_id(taskMaster.getTask_id());
				detail.setCompany_cd("PFZ");
				detail.setIdentitylinktype("assignee");
				detail.setIdentitylinktype_val(empId);
				this.transactionalRepository.persist(detail);
				level = level + 1L;
			}
		}
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public void deletePreviousTaskMaster(Long allocDetailId) throws Exception {
		this.taskMasterRepository.deletePreviousTaskMaster(allocDetailId);
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public void deletePreviousTaskMasterDetail(Long allocDetailId) throws Exception {
		this.taskMasterRepository.deletePreviousTaskMasterDetail(allocDetailId);
	}

	@Override
	public List<ApprLevelBean> getApprovalDetails(Long divId, String tranType) throws Exception {
		// TODO Auto-generated method stub
		return this.taskMasterRepository.getApprovalDetails(divId, tranType);
	}

	@Override
	public List<ApprLevelBean> getApprovers(Long divId, String role, String tranType) throws Exception {
		// TODO Auto-generated method stub
		return this.taskMasterRepository.getApprovers(divId, role, tranType);
	}

	@SuppressWarnings("unchecked")
	@Override
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public void setApprovalFromUi(ApprLevelBean bean) throws Exception {
		TaskMaster taskMaster = null;
		Task_Master_Dtl detail = null;
		List<String> list = new ArrayList<>();
		String taskname = null;
		Long level = 1L;
		if (bean.getTransactionType().equals("17")) {
			taskname = "ASP-TEAM-";
			list.add("17");
			list.add("18");
		} else if (bean.getTransactionType().equals("15")) {
			taskname = "ANNUAL-PLAN-";
			list.add("15");
			list.add("16");
		} else if (bean.getTransactionType().equals("32")) {
			taskname = "SCM-MST-APPR-ALL-PFZ";
			list.add("32");
		}
		else if (bean.getTransactionType().equals("300")) {
			taskname = "SCM-EXV-APPR-ALL-PFZ";
			list.add("300");
		}

		for (String tranType : list) {
			this.taskMasterRepository.deletePreviousTaskMasterDetail(Long.valueOf(bean.getDivision()), tranType);
			this.taskMasterRepository.deletePreviousTaskMaster(Long.valueOf(bean.getDivision()), tranType);
			if (bean.getTransactionType().equals("32") ||
					bean.getTransactionType().equals("300")) {
				taskname = taskname+"-" + bean.getDivision();
			}
			else {
				taskname = taskname + "-" + tranType + "-" + bean.getDivision();
			}

			for (String empId : bean.getApproverIds()) {
				System.out.println("empId " + empId);
				if (empId != null && !empId.isEmpty() && !empId.equals("0")) {
					taskMaster = new TaskMaster();
					taskMaster.setCompany_cd(bean.getCompanyCode());
					taskMaster.setDiv_id(1L);
					taskMaster.setTask_name(taskname);
					taskMaster.setTask_descr(taskname);
					taskMaster.setApproval_req("Y");
					taskMaster.setCompletion_time(10);
					taskMaster.setReminder_time(4);
					taskMaster.setCreated_date(new Date());
					taskMaster.setCreated_by(bean.getEmpId());
					taskMaster.setLast_mod_date(new Date());
					taskMaster.setLast_mod_by(bean.getEmpId());
					taskMaster.setApproval_type("001");
					taskMaster.setInv_grp_id(bean.getDivision() == null ? 0L : Long.valueOf(bean.getDivision()));
					taskMaster.setAppr_level(level);
					taskMaster.setValue_range_from(BigDecimal.ZERO);
					taskMaster.setValue_range_to(BigDecimal.ZERO);
					taskMaster.setPercentage_range_from(BigDecimal.ZERO);
					taskMaster.setPercentage_range_to(BigDecimal.ZERO);
					taskMaster.setTran_type(tranType);
					taskMaster.setFs_id(0L);
					this.transactionalRepository.persist(taskMaster);

					detail = new Task_Master_Dtl();
					detail.setTask_id(taskMaster.getTask_id());
					detail.setCompany_cd(bean.getCompanyCode());
					detail.setIdentitylinktype("assignee");
					detail.setIdentitylinktype_val(empId);
					this.transactionalRepository.persist(detail);
					level = level + 1L;
				}
			}
		}
	}

	@Override
	public Long getCustomTaskByTaskId(String taskid) throws Exception {
		// TODO Auto-generated method stub
		return taskMasterRepository.getCustomTaskByTaskId(taskid);
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public void updateTranDetailsInAppoLog() throws Exception {
		// TODO Auto-generated method stub
		taskMasterRepository.updateTranDetailsInAppoLog();
	}

	@Override
	public String getTranTypeByLocIdAndApprovalType(Long loc_id, String tran_name, String company_code)
			throws Exception {
		return taskMasterRepository.getTranTypeByLocIdAndApprovalType(loc_id, tran_name, company_code);
	}

	@Override
	public String getEmailIdOfApprover(Long loc_id, String company_cd, String full_tran_name) throws Exception {
		return taskMasterRepository.getEmailIdOfApprover(loc_id, company_cd, full_tran_name);
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public void UPDATE_ACT_RU_TASK_ASSIGNEE_MULTI_LEVEL(String assignee, Long id) throws Exception {
		taskMasterRepository.UPDATE_ACT_RU_TASK_ASSIGNEE_MULTI_LEVEL(assignee, id);
	}
	
	@Override
	public Long getActTaskIdByTranTypeAndId(Long tran_ref_id, String tran_type) throws Exception{
		return taskMasterRepository.getActTaskIdByTranTypeAndId(tran_ref_id, tran_type);
	}

}
