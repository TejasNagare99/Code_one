package com.excel.repository;

import java.math.BigDecimal;
import java.text.ParseException;
import java.util.Date;
import java.util.List;

import com.excel.bean.ApprLevelBean;
import com.excel.bean.ApprovalBean;
import com.excel.bean.InboxTask;
import com.excel.bean.MailBean;
import com.excel.model.Acc_Tran_Type.TranNameEnum;
import com.excel.model.TaskMaster;
import com.excel.model.Task_Master_Dtl;

public interface TaskMasterRepository {

	List<TaskMaster> getTask(Long inv_group,Long level,Long div_id,String tran_type,BigDecimal amount, BigDecimal value,String taskFlow,Long fs_id,String approvalType);

	List<Task_Master_Dtl> getTaskDetail(Long task_id);

	List<InboxTask> getTaskList(List<String> task_ids,String identitylink, String erp_cust_outstanding) throws ParseException;

	List<InboxTask> getNotificationList(String user_id) throws ParseException;
	
	public void saveHoldApproval(ApprovalBean approvalBean);
	
	public void updateHoldApproval(ApprovalBean approvalBean,boolean isInactive);
	
	public String getHoldApprovalRemark(Long tranId, Long tranType);
	
	public String getNextApproverPOByTaskId(String taskid, String tran_type, Long inv_grp);
	
	public void updateApprovalLevelCustomTask (String taskId,String username) throws Exception;
	

	String getIdentityLinkType(Long div_id);
	
	List<String> getTranTypesByUsername(String username) throws Exception;
	
	List<Object[]> getInvoicingGroupsByTranTypeAndIdentityLinkTypeVal(String tranType, String username) throws Exception;
	
	void UPDATE_ACT_RU_TASK_ASSIGNEE(Long id) throws Exception;
	
	List<String> getUserEmailIDsByTranType(String tranType) throws Exception;
	
	List<Object> getTaskDetailsByInvoicingGroupsAndTranType(Long tranType, List<Long> invoicingGroups)
			throws Exception;

	List<Task_Master_Dtl> getTaskDetailsByTaskIds(List<Long> taskIds) throws Exception;

	void updateTaskDetails(List<Task_Master_Dtl> updateList) throws Exception;
	
	List<Object> getTasksToStartDelegation() throws Exception;

//	void saveDelegationLogs(List<TaskDelegation> taskDelegations) throws Exception;

	List<TaskMaster> getTaskMasterByDetailIds(List<Long> detailIds) throws Exception;

	List<Object> getTasksToReverseDelegation() throws Exception;

	void revertCustomTask(Long tran_id, String tran_type) throws Exception;

	List<InboxTask> getNotificationPendApprList(String user_id, Date start_date, Date end_date, Boolean isHoUser) throws ParseException;

	List<TaskMaster> getAlternateTask(Long tran_id, Long inv_group, String tran_type) throws Exception;
	

	List<Long> getDistinctTaskMasterIdsByApproverTranTypeAndLogicType(String username, 
			String tran_type, String logic_type, Long inv_grp_id) throws Exception;
	
	List<TaskMaster> getTaskBetweenValue1FromAndTo(Long inv_group, String tran_type, BigDecimal currinv_amt,
			String logic_type, Long appr_level) throws Exception;
	
	List<TaskMaster> getTaskWithoutCondition(Long inv_group, String tran_type, String logic_type, Long appr_level)
			throws Exception;
	ApprovalBean getTranIdByTaskId(String taskId);


	List<MailBean> getMailSendDetails(Long id, String nexapprovar, String tran_type);

	List<MailBean> getApprovalDetails(Long taskId);

	TaskMaster getObjectById(Long grnId) throws Exception;

	Task_Master_Dtl getObjectByIdTask_Master_Dtl(Long taskId) throws Exception;

	void deletePreviousTaskMaster(Long allocDetailId) throws Exception;

	void deletePreviousTaskMasterDetail(Long allocDetailId) throws Exception;

	List<ApprLevelBean> getApprovalDetails(Long divId, String tranType) throws Exception;

	List<ApprLevelBean> getApprovers(Long divId, String role, String tranType) throws Exception;

	void deletePreviousTaskMaster(Long div_id, String tranType) throws Exception;

	void deletePreviousTaskMasterDetail(Long div_id, String tranType) throws Exception;

	public Long getCustomTaskByTaskId(String taskid) throws Exception;

	public void updateTranDetailsInAppoLog() throws Exception;
	
	public String getTranTypeByLocIdAndApprovalType(Long loc_id,String tran_name,String company_code) throws Exception;

	public String getEmailIdOfApprover(Long loc_id, String company_cd, String full_tran_name) throws Exception;
	
	//new 17June 24 for acc tran auto generation
	TaskMaster getTaskMasterByTranTypeFromAccTran(TranNameEnum tran_type_enum,Long loc_id) throws Exception;
	
	//new 6 July for stockist multi level scheme appr
	void UPDATE_ACT_RU_TASK_ASSIGNEE_MULTI_LEVEL(String assignee,Long id) throws Exception;
	
	//for auto approvals
	Long getActTaskIdByTranTypeAndId(Long tran_ref_id,String tran_type) throws Exception;

}
