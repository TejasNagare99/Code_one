package com.excel.repository;

import java.math.BigDecimal;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.Tuple;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import javax.persistence.criteria.Subquery;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import com.excel.bean.ApprLevelBean;
import com.excel.bean.ApprovalBean;
import com.excel.bean.InboxTask;
import com.excel.bean.MailBean;
import com.excel.model.Acc_Tran_Type;
import com.excel.model.Acc_Tran_Type.TranNameEnum;
import com.excel.model.TaskMaster;
import com.excel.model.Task_Master_Dtl;
import com.excel.utility.CodifyErrors;
import com.excel.utility.MedicoConstants;

@Repository
public class TaskMasterRepositoyImpl implements TaskMasterRepository {

	@Autowired
	private EntityManagerFactory emf;
	@PersistenceContext
	EntityManager em;
	@PersistenceContext
	private EntityManager entityManager;

	@Override
	public TaskMaster getObjectById(Long taskId) throws Exception {
		return this.entityManager.find(TaskMaster.class, taskId);
	}

	@Override
	public Task_Master_Dtl getObjectByIdTask_Master_Dtl(Long taskDtlId) throws Exception {
		return this.entityManager.find(Task_Master_Dtl.class, taskDtlId);
	}

	@Override
	public List<TaskMaster> getTask(Long inv_group, Long level, Long div_id, String tran_type, BigDecimal amount,
			BigDecimal value, String taskFlow, Long fs_id, String approval_type) {
		System.out.println("inv_group " + inv_group);
		System.out.println("level " + level);
		System.out.println("div_id " + div_id);
		System.out.println("amount " + amount);
		System.out.println("tran_type " + tran_type);
		System.out.println("fs_id " + fs_id);

		List<TaskMaster> list = null;
		EntityManager em = null;

		try {
			em = emf.createEntityManager();
			CriteriaBuilder builder = em.getCriteriaBuilder();
			CriteriaQuery<TaskMaster> query = builder.createQuery(TaskMaster.class);
			Root<TaskMaster> root = query.from(TaskMaster.class);
			List<Predicate> predicates = new ArrayList<>();

			if (inv_group != null)
				predicates.add(builder.equal(root.get("inv_grp_id"), inv_group));

			if (fs_id != null)
				predicates.add(builder.equal(root.get("fs_id"), fs_id));

			if (taskFlow.equals(MedicoConstants.BPM_LEVELWISE)) {
				if (level != null)
					predicates.add(builder.equal(root.get("appr_level"), level));
			} else if (taskFlow.equals(MedicoConstants.BPM_AMOUNTWISE)
					|| taskFlow.equals(MedicoConstants.BPM_AMOUNT_LEVEL)) {
				if (amount != null) {
					predicates.add(builder.equal(root.get("value_range_from"), amount));
					predicates.add(builder.equal(root.get("value_range_to"), amount));
				}
			} else if (taskFlow.equals(MedicoConstants.BPM_PERCENTAGE)) {
				if (value != null) {
					predicates.add(builder.equal(root.get("percentage_range_from"), value));
					predicates.add(builder.equal(root.get("percentage_range_to"), value));
				}
			}
			predicates.add(builder.equal(root.get("tran_type"), tran_type));
			query.where(predicates.toArray(new Predicate[0]));
			list = em.createQuery(query).getResultList();

		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("Error Occurred :" + CodifyErrors.getErrorCode(e));// uncomment asneeded --;
		} finally {
			if (em != null) {
				em.close();
			}
		}

		return list;
	}

	@Override
	public List<Task_Master_Dtl> getTaskDetail(Long task_id) {

		List<Task_Master_Dtl> list = null;
		EntityManager em = null;

		try {
			em = emf.createEntityManager();
			CriteriaBuilder builder = em.getCriteriaBuilder();
			CriteriaQuery<Task_Master_Dtl> query = builder.createQuery(Task_Master_Dtl.class);
			Root<Task_Master_Dtl> root = query.from(Task_Master_Dtl.class);
			query.select(root).where(builder.equal(root.get("task_id"), task_id));
			list = em.createQuery(query).getResultList();

		} catch (Exception e) {
			System.out.println("Error Occurred :" +e.getMessage());// uncomment asneeded --;
		} finally {
			if (em != null) {
				em.close();
			}
		}

		return list;
	}

	@Override
	public String getIdentityLinkType(Long div_id) {
		return "";
	}

	@Override
	public List<InboxTask> getTaskList(List<String> task_ids, String identitylink, String erp_cust_outstanding)
			throws ParseException {
//		StringBuffer buffer=new StringBuffer();	
//
//		if(erp_cust_outstanding.equalsIgnoreCase("Y")) {
//			buffer.append("SELECT cm.CUST_NAME,dp.LOC_NAME,cp.TRAN_TYPE,cp.TRAN_REF_ID,cp.TRAN_NO,ct.APPROVAL_TYPE,actt.ID_ TASK_ID,");
//			buffer.append("ACCTT.TRAN_TYPE_DESCRIPTION,cp.FIN_YEAR_ID FIN_YEAR_ID,cp.STOCK_POINT_ID STOCK_POINT_ID,DP.LOC_ID LOC_ID,");
//			buffer.append("cp.DOC_TYPE DOC_TYPE, cp.PROCESS_INSTANCE_ID PROCESS_INSTANCE_ID, cp.AMOUNT AMOUNT, ");
//			if(identitylink.equals(IdentityLinkType.CANDIDATE))
//				buffer.append("AG.NAME_ GROUP_, ");
//			else
//				buffer.append("'NA' GROUP_, ");
//			buffer.append("cm.DESTINATION DESTINATION, stp.BILLING_NAME BILLING_NAME, ct.RECEIVED_ON RECEIVED_ON,CP.STARTED_BY INITIATOR,");
//			buffer.append("soa.BLOCKING_REASON, IDMS.INV_GROUP_NAME, ");
//
//			buffer.append(" nvl(en.ENT_PRI_CRLMT, 0) credit_limit ");
//
//			buffer.append(" ,SM.VENDOR_NAME, SM.DESTINATION SM_DESTINATION,DECODE(ah.status,'ACTIVE','HOLD',' ') hold_status ");
//			buffer.append(", IDMS.INV_GRP_ID FROM ACT_RU_EXECUTION actp INNER JOIN CUSTOM_PROCESS_INSTANCE cp ON(cp.PROCESS_INSTANCE_ID=actp.ID_) ");
//			buffer.append("INNER JOIN ACT_RU_TASK actt ON actt.PROC_INST_ID_=actp.PROC_INST_ID_	INNER JOIN CUSTOM_TASK ct ");
//			buffer.append("ON actt.id_=ct.ACT_TASKID INNER JOIN ACC_TRAN_TYPE ACCTT ON (ACCTT.TRAN_TYPE=cp.TRAN_TYPE) ");
//			if(identitylink.equals(IdentityLinkType.CANDIDATE)){
//				buffer.append("INNER JOIN ACT_RU_IDENTITYLINK AIL ");
//				buffer.append("ON(AIL.TASK_ID_=actt.id_) ");
//				buffer.append("INNER JOIN ACT_ID_GROUP AG ON(AG.ID_=AIL.GROUP_ID_) ");
//			}else if(identitylink.equals(IdentityLinkType.PARTICIPANT) ){
//				buffer.append("INNER JOIN ACT_RU_IDENTITYLINK AIL ");
//				buffer.append("ON(AIL.PROC_INST_ID_=actp.ID_) ");
//			}
//
//			buffer.append("LEFT JOIN CUSTOMER_MASTER cm ON(cm.CUST_ID=cp.CUST_ID) ");
//			buffer.append(" left outer join ENT_CUST_MASTER en on en.ENT_CUST_ID = cm.ENT_CUST_ID ");
//
//			buffer.append("LEFT JOIN DEPOT_MASTER dp ON(dp.LOC_ID  =cp.LOC_ID) ");
//			buffer.append("LEFT JOIN STOCKPOINT_MST stp on(stp.STOCK_POINT_ID=cp.STOCK_POINT_ID) ");
//
//			buffer.append("LEFT JOIN SALES_ORD_APPROVAL soa on(soa.SLNO=cp.TRAN_REF_ID and (CP.TRAN_TYPE ='10' OR CP.TRAN_TYPE ='11')) ");
//			buffer.append("LEFT JOIN TASKS_MASTER tm on (tm.TASK_NAME=ct.SUBJECT AND tm.TRAN_TYPE=cp.TRAN_TYPE )");
//			buffer.append("LEFT JOIN ( select distinct inv_grp_id,inv_group_name from inv_div_map ) idms ON idms.inv_grp_id = CASE WHEN CP.TRAN_TYPE IN ('10', '11') THEN soa.inv_grOUp_id ELSE tm.inv_grp_id END ");
//			buffer.append("LEFT JOIN SUPPLIER_MASTER SM ON (SM.VENDOR_ID = cp.CUST_ID ) ");
//			buffer.append("LEFT JOIN (SELECT SUM(CREDIT_LIMIT) CREDIT_LIMIT,CUST_ID CUST_ID,INV_GROUP INV_GROUP FROM CUSTOMER_ALLOCATION WHERE CURR_STATUS NOT IN ('H','D') GROUP BY CUST_ID,INV_GROUP) CA ON (cm.CUST_ID = CA.CUST_ID AND tm.INV_GRP_ID = CA.INV_GROUP) ");
//			buffer.append("  left outer join APPROVAL_ON_HOLD ah on ah.tran_id = soa.slno and soa.status = 'F' and ah.tran_type in ( '10', '11' ) ");
//			buffer.append("WHERE actt.ID_ in (:TASKID) ");
//			if(identitylink.equals(IdentityLinkType.ASSIGNEE))
//				buffer.append("AND actt.ASSIGNEE_ IS NOT NULL AND ct.STATUS !='COMPLETED' ");
//
//			buffer.append("ORDER BY ACCTT.TRAN_TYPE_DESCRIPTION,ct.RECEIVED_ON desc, dp.LOC_NAME,stp.BILLING_NAME,cm.CUST_NAME");
//		} else {
//			buffer.append("SELECT cm.CUST_NAME,dp.LOC_NAME,cp.TRAN_TYPE,cp.TRAN_REF_ID,cp.TRAN_NO,ct.APPROVAL_TYPE,actt.ID_ TASK_ID,");
//			buffer.append("ACCTT.TRAN_TYPE_DESCRIPTION,cp.FIN_YEAR_ID FIN_YEAR_ID,cp.STOCK_POINT_ID STOCK_POINT_ID,DP.LOC_ID LOC_ID,");
//			buffer.append("cp.DOC_TYPE DOC_TYPE, cp.PROCESS_INSTANCE_ID PROCESS_INSTANCE_ID, cp.AMOUNT AMOUNT, ");
//			if(identitylink.equals(IdentityLinkType.CANDIDATE))
//				buffer.append("AG.NAME_ GROUP_, ");
//			else
//				buffer.append("'NA' GROUP_, ");
//			buffer.append("cm.DESTINATION DESTINATION, stp.BILLING_NAME BILLING_NAME, ct.RECEIVED_ON RECEIVED_ON,CP.STARTED_BY INITIATOR,");
//			buffer.append("soa.BLOCKING_REASON, IDMS.INV_GROUP_NAME, CA.CREDIT_LIMIT ,SM.VENDOR_NAME, SM.DESTINATION SM_DESTINATION,DECODE(ah.status,'ACTIVE','HOLD',' ') hold_status ");
//			buffer.append(", IDMS.INV_GRP_ID FROM ACT_RU_EXECUTION actp INNER JOIN CUSTOM_PROCESS_INSTANCE cp ON(cp.PROCESS_INSTANCE_ID=actp.ID_) ");
//			buffer.append("INNER JOIN ACT_RU_TASK actt ON actt.PROC_INST_ID_=actp.PROC_INST_ID_	INNER JOIN CUSTOM_TASK ct ");
//			buffer.append("ON actt.id_=ct.ACT_TASKID INNER JOIN ACC_TRAN_TYPE ACCTT ON (ACCTT.TRAN_TYPE=cp.TRAN_TYPE) ");
//			if(identitylink.equals(IdentityLinkType.CANDIDATE)){
//				buffer.append("INNER JOIN ACT_RU_IDENTITYLINK AIL ");
//				buffer.append("ON(AIL.TASK_ID_=actt.id_) ");
//				buffer.append("INNER JOIN ACT_ID_GROUP AG ON(AG.ID_=AIL.GROUP_ID_) ");
//			}else if(identitylink.equals(IdentityLinkType.PARTICIPANT) ){
//				buffer.append("INNER JOIN ACT_RU_IDENTITYLINK AIL ");
//				buffer.append("ON(AIL.PROC_INST_ID_=actp.ID_) ");
//			}
//
//			buffer.append("LEFT JOIN CUSTOMER_MASTER cm ON(cm.CUST_ID=cp.CUST_ID) ");
//			buffer.append("LEFT JOIN DEPOT_MASTER dp ON(dp.LOC_ID  =cp.LOC_ID) ");
//			buffer.append("LEFT JOIN STOCKPOINT_MST stp on(stp.STOCK_POINT_ID=cp.STOCK_POINT_ID) ");
//
//			buffer.append("LEFT JOIN SALES_ORD_APPROVAL soa on(soa.SLNO=cp.TRAN_REF_ID and (CP.TRAN_TYPE ='10' OR CP.TRAN_TYPE ='11')) ");
//			buffer.append("LEFT JOIN TASKS_MASTER tm on (tm.TASK_NAME=ct.SUBJECT AND tm.TRAN_TYPE=cp.TRAN_TYPE AND tm.APPR_LEVEL=ct.APPR_LEVEL)");
//			buffer.append("LEFT JOIN ( select distinct inv_grp_id,inv_group_name from inv_div_map ) idms ON idms.inv_grp_id = CASE WHEN CP.TRAN_TYPE IN ('10', '11') THEN soa.inv_grOUp_id ELSE tm.inv_grp_id END ");
//			buffer.append("LEFT JOIN SUPPLIER_MASTER SM ON (SM.VENDOR_ID = cp.CUST_ID ) ");
//			buffer.append("LEFT JOIN (SELECT SUM(CREDIT_LIMIT) CREDIT_LIMIT,CUST_ID CUST_ID,INV_GROUP INV_GROUP FROM CUSTOMER_ALLOCATION WHERE CURR_STATUS NOT IN ('H','D') GROUP BY CUST_ID,INV_GROUP) CA ON (cm.CUST_ID = CA.CUST_ID AND tm.INV_GRP_ID = CA.INV_GROUP) ");
//			buffer.append("  left outer join APPROVAL_ON_HOLD ah on ah.tran_id = soa.slno and soa.status = 'F' and ah.tran_type in ( '10', '11' ) ");
//			buffer.append("WHERE actt.ID_ in (:TASKID) ");
//			if(identitylink.equals(IdentityLinkType.ASSIGNEE))
//				buffer.append("AND actt.ASSIGNEE_ IS NOT NULL AND ct.STATUS !='COMPLETED' ");
//
//			buffer.append("ORDER BY ACCTT.TRAN_TYPE_DESCRIPTION,ct.RECEIVED_ON desc, dp.LOC_NAME,stp.BILLING_NAME,cm.CUST_NAME");
//		}
//		StringType stringType=new StringType();
//		LongType longType = new LongType();
//		SessionFactory sFact = (SessionFactory) TransactionManager.getTransactionId().get("sessionfactory");
//
//		Query query = sFact.openSession().createSQLQuery(buffer.toString())
//				.addScalar("CUST_NAME", stringType)
//				.addScalar("LOC_NAME", stringType)
//				.addScalar("TRAN_TYPE", stringType)
//				.addScalar("TRAN_REF_ID", stringType)
//				.addScalar("TRAN_NO", stringType)
//				.addScalar("APPROVAL_TYPE", stringType)
//				.addScalar("TASK_ID", stringType)
//				.addScalar("TRAN_TYPE_DESCRIPTION", stringType)
//				.addScalar("FIN_YEAR_ID", stringType)
//				.addScalar("STOCK_POINT_ID", stringType)
//				.addScalar("LOC_ID", stringType)
//				.addScalar("DOC_TYPE", stringType)
//				.addScalar("PROCESS_INSTANCE_ID", stringType)
//				.addScalar("AMOUNT", stringType)
//				.addScalar("GROUP_", stringType)
//				.addScalar("DESTINATION", stringType)
//				.addScalar("BILLING_NAME", stringType)
//				.addScalar("RECEIVED_ON", stringType)
//				.addScalar("INITIATOR", stringType)
//				.addScalar("BLOCKING_REASON", stringType)
//				.addScalar("INV_GROUP_NAME", stringType)
//				.addScalar("CREDIT_LIMIT", stringType)
//				.addScalar("VENDOR_NAME", stringType)
//				.addScalar("SM_DESTINATION", stringType)
//				.addScalar("HOLD_STATUS", stringType)
//				.addScalar("INV_GRP_ID", longType)
//
//		query.setParameterList("TASKID", task_ids);
//		List<Object> objects=query.list();
//		List<InboxTask> inboxTasks=new ArrayList<InboxTask>();
//
//		for(Object object:objects){
//			Object[] obj=(Object[]) object;
//			InboxTask inboxTask=new InboxTask();
//
//			inboxTask.setLoc_name(obj[1]!=null?obj[1].toString():"");
//			inboxTask.setTran_type(obj[2]!=null?obj[2].toString():"");
//			inboxTask.setTran_ref_id(obj[3]!=null?obj[3].toString():"");
//			inboxTask.setTran_no(obj[4]!=null?obj[4].toString():"");
//			inboxTask.setApproval_type(obj[5]!=null?obj[5].toString():"");
//			inboxTask.setTask_id(obj[6]!=null?obj[6].toString():"");
//			inboxTask.setTran_type_description(obj[7]!=null?obj[7].toString():"");
//			inboxTask.setFin_year_id(obj[8]!=null?obj[8].toString():"");
//			inboxTask.setStock_point_id(obj[9]!=null?obj[9].toString():"");
//			inboxTask.setLoc_id(obj[10]!=null?obj[10].toString():"");
//			inboxTask.setDoc_type(obj[11]!=null?obj[11].toString():"");
//			inboxTask.setProcess_instance_id(obj[12]!=null?obj[12].toString():"");
//			inboxTask.setAmount(obj[13]!=null?obj[13].toString():"");
//			inboxTask.setGroup_(obj[14]!=null?obj[14].toString():"");
//			if("80".equalsIgnoreCase(inboxTask.getTran_type())){
//				inboxTask.setCust_name(obj[22]!=null?obj[22].toString():"NA");
//				inboxTask.setCust_dest(obj[23]!=null?obj[23].toString():"NA");
//			}else{
//				inboxTask.setCust_name(obj[0]!=null?obj[0].toString():"NA");
//				inboxTask.setCust_dest(obj[15]!=null?obj[15].toString():"NA");
//			}
//
//			inboxTask.setStock_point_name(obj[16]!=null?obj[16].toString():"");
//			inboxTask.setReceived_on(obj[17]!=null?Utility.getdDateFormat(obj[17].toString()):"");
//			inboxTask.setInitiator(obj[18]!=null?obj[18].toString():"");
//			inboxTask.setApproval_category(obj[19]!=null?obj[19].toString():Utility.getDocType(obj[11]!=null?obj[11].toString():""));
//			inboxTask.setInvoiceGroup(obj[20] != null?obj[20].toString():"");
//			inboxTask.setCreditLimit(obj[21] != null?obj[21].toString():"NA");
//			inboxTask.setHold_status(obj[24].toString());
//			inboxTask.setInv_grp_id(obj[25]!= null?Long.parseLong(obj[25].toString()):0);
//			inboxTasks.add(inboxTask);
//		}
//		return inboxTasks;

		return null;
	}

	@Override
	public List<InboxTask> getNotificationList(String user_id) throws ParseException {
//		StringBuffer buffer=new StringBuffer();
//		buffer.append("SELECT cm.CUST_NAME, dp.LOC_NAME, cp.TRAN_TYPE, cp.TRAN_REF_ID, cp.TRAN_NO, ct.APPROVAL_TYPE, ");
//		buffer.append("ACCTT.TRAN_TYPE_DESCRIPTION, cp.FIN_YEAR_ID FIN_YEAR_ID, cp.STOCK_POINT_ID STOCK_POINT_ID, DP.LOC_ID LOC_ID, ");
//		buffer.append("cp.DOC_TYPE DOC_TYPE, cp.PROCESS_INSTANCE_ID PROCESS_INSTANCE_ID, cp.AMOUNT AMOUNT, cm.DESTINATION DESTINATION, ");
//		buffer.append("stp.BILLING_NAME BILLING_NAME, ct.RECEIVED_ON RECEIVED_ON,CP.STARTED_BY INITIATOR ");
//		buffer.append("FROM ACT_RU_EXECUTION actp INNER JOIN CUSTOM_PROCESS_INSTANCE cp	ON(cp.PROCESS_INSTANCE_ID=actp.ID_) ");
//		buffer.append("INNER JOIN CUSTOM_TASK ct ON cp.PROCESS_ID=ct.PROCESS_ID INNER JOIN CUSTOM_IDENTITYLINK CID ");
//		buffer.append("ON(CID.TASK_ID=ct.TASKID) LEFT JOIN ACT_ID_USER AIU ON(CID.USER_ID=AIU.ID_ AND CID.TYPE='assignee') ");
//		buffer.append("LEFT JOIN ACT_ID_USER AIU_P ON(CID.USER_ID=AIU_P.ID_ AND CID.TYPE='participant') ");
//		buffer.append("LEFT JOIN ACT_ID_GROUP AIG ON(AIG.ID_=CID.GROUP_ID AND CID.TYPE='candidate') ");
//		buffer.append("LEFT JOIN ACT_ID_MEMBERSHIP AIM ON(AIG.ID_=AIM.GROUP_ID_) INNER JOIN ACC_TRAN_TYPE ACCTT ");
//		buffer.append("ON (ACCTT.TRAN_TYPE=cp.TRAN_TYPE) LEFT JOIN CUSTOMER_MASTER cm ON(cm.CUST_ID=cp.CUST_ID) ");
//		buffer.append("LEFT JOIN DEPOT_MASTER dp ON(dp.LOC_ID =cp.LOC_ID) LEFT JOIN STOCKPOINT_MST stp ");
//		buffer.append("ON(stp.STOCK_POINT_ID=cp.STOCK_POINT_ID)	WHERE ct.APPROVAL_TYPE='005' ");
//		buffer.append("AND AIM.USER_ID_ = :USER_ID ");
//
//		StringType stringType=new StringType();
//		SessionFactory sFact = (SessionFactory) TransactionManager.getTransactionId().get("sessionfactory");
//		Query query = sFact.openSession().createSQLQuery(buffer.toString())
//				.addScalar("CUST_NAME", stringType)
//				.addScalar("LOC_NAME", stringType)
//				.addScalar("TRAN_TYPE", stringType)
//				.addScalar("TRAN_REF_ID", stringType)
//				.addScalar("TRAN_NO", stringType)
//				.addScalar("APPROVAL_TYPE", stringType)
//				.addScalar("TRAN_TYPE_DESCRIPTION", stringType)
//				.addScalar("FIN_YEAR_ID", stringType)
//				.addScalar("STOCK_POINT_ID", stringType)
//				.addScalar("LOC_ID", stringType)
//				.addScalar("DOC_TYPE", stringType)
//				.addScalar("PROCESS_INSTANCE_ID", stringType)
//				.addScalar("AMOUNT", stringType)
//				.addScalar("DESTINATION", stringType)
//				.addScalar("BILLING_NAME", stringType)
//				.addScalar("RECEIVED_ON", stringType)
//				.addScalar("INITIATOR", stringType);
//		query.setParameter("USER_ID", user_id);
//		List<Object> objects=query.list();
//		List<InboxTask> inboxTasks=new ArrayList<InboxTask>();
//		for(Object object:objects){
//			Object[] obj=(Object[]) object;
//			InboxTask inboxTask=new InboxTask();
//			inboxTask.setCust_name(obj[0]!=null?obj[0].toString():"");
//			inboxTask.setLoc_name(obj[1]!=null?obj[1].toString():"");
//			inboxTask.setTran_type(obj[2]!=null?obj[2].toString():"");
//			inboxTask.setTran_ref_id(obj[3]!=null?obj[3].toString():"");
//			inboxTask.setTran_no(obj[4]!=null?obj[4].toString():"");
//			inboxTask.setApproval_type(obj[5]!=null?obj[5].toString():"");
//			inboxTask.setTran_type_description(obj[6]!=null?obj[6].toString():"");
//			inboxTask.setFin_year_id(obj[7]!=null?obj[7].toString():"");
//			inboxTask.setStock_point_id(obj[8]!=null?obj[8].toString():"");
//			inboxTask.setLoc_id(obj[9]!=null?obj[9].toString():"");
//			inboxTask.setDoc_type(obj[10]!=null?obj[10].toString():"");
//			inboxTask.setProcess_instance_id(obj[11]!=null?obj[11].toString():"");
//			inboxTask.setAmount(obj[12]!=null?obj[12].toString():"");
//			inboxTask.setCust_dest(obj[13]!=null?obj[13].toString():"");
//			inboxTask.setStock_point_name(obj[14]!=null?obj[14].toString():"");
//			inboxTask.setReceived_on(obj[15]!=null?Utility.getdDateFormat(obj[15].toString()):"");
//			inboxTask.setInitiator(obj[16]!=null?obj[16].toString():"");
//			inboxTasks.add(inboxTask);
//		}
//		return inboxTasks;
		return null;
	}

	@Override
	public void saveHoldApproval(ApprovalBean approvalBean) {
//		Held_Approval heldApproval = new Held_Approval();
//		heldApproval.setTask_id(Long.parseLong(approvalBean.getTaskid()));
//		heldApproval.setTran_id(approvalBean.getTran_id());
//		heldApproval.setTran_type(approvalBean.getTran_type());
//		heldApproval.setFin_year_id(approvalBean.getFin_year_id());
//		heldApproval.setRemarks(approvalBean.getMotivation());
//		heldApproval.setApprover(approvalBean.getApprover());
//		heldApproval.setLoc_id(approvalBean.getLoc_id());
//		heldApproval.setCust_name(approvalBean.getCust_name());
//		heldApproval.setStatus("ACTIVE");
//		session.save(heldApproval);
//		session.flush();
	}

	@Override
	public void updateHoldApproval(ApprovalBean approvalBean, boolean isInactive) {
//		StringBuffer buffer=new StringBuffer();
//		buffer.append("UPDATE APPROVAL_ON_HOLD ha ");
//		if(isInactive){
//			buffer.append("SET ha.STATUS = 'INACTIVE',ha.APPROVER = '" + approvalBean.getApprover() + "' ");
//		}else{
//			buffer.append("SET ha.REMARKS = '" +approvalBean.getMotivation() +"',ha.APPROVER = '" + approvalBean.getApprover() + "' ");
//		}
//
//		buffer.append("WHERE ha.TASK_ID = " + approvalBean.getTaskid());
//
//		Query query = session.createSQLQuery(buffer.toString());
//		query.executeUpdate();

	}

	@Override
	public String getHoldApprovalRemark(Long tranId, Long tranType) {

//		Criteria criteria=session.createCriteria(Held_Approval.class);
//		criteria.add(Restrictions.eq("tran_id", tranId))
//		.add(Restrictions.eq("tran_type", tranType))
//		.add(Restrictions.eq("status", "ACTIVE"));
//		List<Held_Approval> held_Approvals=criteria.list();
//		if(CollectionUtils.isEmpty(held_Approvals)){
//			return null;
//		}else{
//			return held_Approvals.get(0).getRemarks();
//		}
		return null;

	}

	@Override
	public String getNextApproverPOByTaskId(String taskid, String tran_type, Long inv_grp) {

		EntityManager em = null;

		try {
			em = emf.createEntityManager();

			StringBuffer buffer = new StringBuffer();
			buffer.append(" SELECT tmd.IDENTITYLINKTYPE_VAL ");
			buffer.append(" FROM TASK_MASTER_DTL tmd ");
			buffer.append(" LEFT JOIN TASKS_MASTER tm ON tmd.TASK_ID = tm.TASK_MASTER_ID ");
			buffer.append(" LEFT JOIN CUSTOM_TASK cm ON tm.TASK_NAME = cm.SUBJECT ");
			buffer.append(" WHERE cm.ACT_TASKID = " + taskid);
			buffer.append(" AND tm.APPR_LEVEL   = (cm.APPR_LEVEL + 1) ");
			buffer.append(" AND cm.status = 'PENDING' ");
			/*
			 * if(!tran_type.equals(null)) { buffer.append(" AND tm.TRAN_TYPE = '" +
			 * tran_type + "'"); }
			 */
			// buffer.append(" AND cm.status = 'PENDING' ");
			if (inv_grp != null) {
				buffer.append("AND tm.INV_GRP_ID = " + inv_grp);
			}

			List<String> list = em.createNativeQuery(buffer.toString()).getResultList();
			if (!CollectionUtils.isEmpty(list)) {
				return list.get(0);
			}

		} catch (Exception e) {
			System.out.println("Error Occurred :" +e.getMessage());// uncomment asneeded --;
		} finally {
			if (em != null) {
				em.close();
			}
		}

		return null;
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public void updateApprovalLevelCustomTask(String taskId, String username) throws Exception {
		// Using session factory for multiple approval service, as multiple approvals
		// spawns a different thread for processing
		// and doesnt have request session with it as response is already sent back.
		EntityManager em = null;
		try {
			StringBuffer buffer = new StringBuffer();
			buffer.append(" UPDATE CUSTOM_TASK ");
			buffer.append(" SET APPR_LEVEL = APPR_LEVEL+1");
			buffer.append(" WHERE ACT_TASKID = " + taskId);
//			buffer.append(
//					" SET APPR_LEVEL = (select count(*)  from CUSTOM_TASK where PROCESS_ID=(select PROCESS_ID from CUSTOM_TASK where ACT_TASKID="
//							+ taskId + ")) ");
//
//			buffer.append(
//					" WHERE TASKID = (select MAX(TASKID) from CUSTOM_TASK where  PROCESS_ID=(select PROCESS_ID from CUSTOM_TASK where ACT_TASKID="
//							+ taskId + ")) ");
			Query query = entityManager.createNativeQuery(buffer.toString());
			query.executeUpdate();

			buffer = new StringBuffer();
			buffer.append(" UPDATE ACT_RU_VARIABLE    SET LONG_ = LONG_+1");
			buffer.append(
					"	WHERE  NAME_ = 'TaskNumber' and PROC_INST_ID_= (SELECT at.PROC_INST_ID_ from ACT_RU_TASK at where ID_ = "
							+ taskId + " )  ");

			query = entityManager.createNativeQuery(buffer.toString());
			query.executeUpdate();

		} catch (Exception e) {
			System.out.println("Error Occurred :" +e.getMessage());// uncomment asneeded --;
			throw e;
		} finally {
			if (em != null) {
				em.close();
			}
		}
	}

	@Override
	public ApprovalBean getTranIdByTaskId(String taskId) {
		System.out.println("task_id " + taskId);
		EntityManager em = null;
		Query query = null;
		StringBuffer buffer = new StringBuffer();
		ApprovalBean bean = null;
		try {
			buffer.append(
					" select CPI.TRAN_REF_ID,CPI.TRAN_TYPE,CPI.STARTED_BY,CPI.FIN_YEAR_ID,ac.FULL_TRAN_NAME  from CUSTOM_PROCESS_INSTANCE CPI JOIN CUSTOM_TASK CT ")
					.append(" ON ( CPI.PROCESS_ID = CT.PROCESS_ID) ")
					.append(" inner join ACC_TRAN_TYPE ac on ac.TRAN_TYPE = cpi.TRAN_TYPE")
					.append(" WHERE ACT_TASKID=:taskId");
			query = entityManager.createNativeQuery(buffer.toString(), Tuple.class);
			query.setParameter("taskId", taskId);
			List<Tuple> tuples = query.getResultList();
			if (tuples != null && !tuples.isEmpty()) {
				for (Tuple t : tuples) {
					bean = new ApprovalBean();
					bean.setTran_id(Long.valueOf(t.get("TRAN_REF_ID", BigDecimal.class).toString()));
					bean.setApprovalType(t.get("TRAN_TYPE", String.class));
					bean.setStartedBy(t.get("STARTED_BY", String.class));
					if (t.get("FIN_YEAR_ID", BigDecimal.class) != null)
						bean.setFin_year_id(Long.valueOf(t.get("FIN_YEAR_ID", BigDecimal.class).toString()));
					bean.setFull_tran_name(t.get("FULL_TRAN_NAME", String.class));
				}
			}
		} catch (Exception e) {
			System.out.println("Error Occurred :" +e.getMessage());// uncomment asneeded --;
		}
		return bean;

	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Object[]> getInvoicingGroupsByTranTypeAndIdentityLinkTypeVal(String tranType, String username)
			throws Exception {
//		Session session = sessionFactory.openSession();
//		String sql = "SELECT P.PARA_ID, P.PARA_DESCR, h.APPR_LEVEL, h.VALUE_RANGE_FROM, "
//				+ "h.VALUE_RANGE_TO, h.TASK_MASTER_ID FROM TASKS_MASTER H, TASK_MASTER_DTL D, PARAMETERS P " +
//				"WHERE P.PARA_ID = H.INV_GRP_ID AND H.TASK_MASTER_ID = D.TASK_ID AND D.IDENTITYLINKTYPE_VAL = :username " +
//				"AND H.TRAN_TYPE = :tranType AND D.IDENTITYLINKTYPE = 'assignee'";
//		Query query = session.createSQLQuery(sql);
//		query.setParameter("username", username);
//		query.setParameter("tranType", tranType);
//		List<Object[]> list = query.list();
//		session.close();
//		return list;
		return null;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<String> getTranTypesByUsername(String username) throws Exception {
		List<String> list = null;
//		Session session = sessionFactory.openSession();
//		String sql = "SELECT DISTINCT TRAN_TYPE FROM TASKS_MASTER H, TASK_MASTER_DTL D WHERE D.TASK_ID = H.TASK_MASTER_ID " +
//				"AND D.IDENTITYLINKTYPE = 'assignee' AND D.IDENTITYLINKTYPE_VAL = :username";
//		Query query = session.createSQLQuery(sql);
//		query.setParameter("username", username);
//		list = query.list();
//		session.close();
		return list;
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public void UPDATE_ACT_RU_TASK_ASSIGNEE(Long id) throws Exception {
		/* EntityManager em = null; */
		try {
			/* em = emf.createEntityManager(); */
			System.out.println("id::" + id);
			StringBuffer buffer = new StringBuffer("UPDATE ACT_RU_TASK SET ASSIGNEE_ = NULL WHERE ID_ = ").append(id);
			Query query = entityManager.createNativeQuery(buffer.toString());
			query.executeUpdate();

		} catch (Exception e) {
			System.out.println("Error Occurred :" +e.getMessage());// uncomment asneeded --;
			throw e;
		} finally {
			// if(em != null) { em.close(); }
		}

	}
	
	
	
	@Override
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public void UPDATE_ACT_RU_TASK_ASSIGNEE_MULTI_LEVEL(String assignee,Long id) throws Exception {
		/* EntityManager em = null; */
		try {
			/* em = emf.createEntityManager(); */
			System.out.println("id::" + id);
			StringBuffer buffer = new StringBuffer("UPDATE ACT_RU_TASK SET ASSIGNEE_ = '").append(assignee)
					.append("' WHERE ID_ = ").append(id);
			Query query = entityManager.createNativeQuery(buffer.toString());
			query.executeUpdate();

		} catch (Exception e) {
			System.out.println("Error Occurred :" +e.getMessage());// uncomment asneeded --;
			throw e;
		} finally {
			// if(em != null) { em.close(); }
		}

	}

	@SuppressWarnings("unchecked")
	@Override
	public List<String> getUserEmailIDsByTranType(String tranType) throws Exception {
//		return session.createSQLQuery("select u.email_id from usermast u, tasks_master h, task_master_dtl d " +
//				"where h.tran_type = :tran_type and h.task_master_id = d.task_id and d.identitylinktype_val = u.user_name")
//				.setParameter("tran_type", tranType).list();
		return null;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Object> getTaskDetailsByInvoicingGroupsAndTranType(Long tranType, List<Long> invoicingGroups)
			throws Exception {
//		Session session = sessionFactory.openSession();
//		String sql = "select distinct i.INV_GRP_ID, i.INV_GROUP_NAME, "
//				+ "t.TASK_MASTER_ID, t.APPR_LEVEL, t.PERCENTAGE_RANGE_FROM, t.PERCENTAGE_RANGE_TO "
//				+ "from tasks_master t inner join INV_DIV_MAP i "+
//				"on i.INV_GRP_ID = t.INV_GRP_ID "+
//				"and t.tran_type = :tran_type";
//		if(invoicingGroups != null) {
//			sql += " and t.INV_GRP_ID in (:inv_groups) ";
//		}
//		sql += " order by i.INV_GROUP_NAME ";
//		SQLQuery query = session.createSQLQuery(sql);
//		query.setParameter("tran_type", tranType);
//		if(invoicingGroups != null) {
//			query.setParameter("inv_groups", invoicingGroups);
//		}
//		List<Object> list = query.list();
//		session.close();
//		return list;
		return null;
	}

	@Override
	public List<Task_Master_Dtl> getTaskDetailsByTaskIds(List<Long> taskIds) throws Exception {
//		Session session = sessionFactory.openSession();
//		Criteria criteria = session.createCriteria(Task_Master_Dtl.class);
//		criteria.add(Restrictions.in("task_id", taskIds));
//		List<Task_Master_Dtl> list = criteria.list();
//		session.close();
//		return list;
		return null;
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public void updateTaskDetails(List<Task_Master_Dtl> updateList) throws Exception {
//		Session session = sessionFactory.getCurrentSession();
//		for(Task_Master_Dtl task : updateList) {
//			session.update(task);
//		}
	}

	// only for scheduler..
	@SuppressWarnings("unchecked")
	@Override
	public List<Object> getTasksToStartDelegation() throws Exception {
//		Session session = sessionFactory.openSession();
//		List<Object> tasks = session.createSQLQuery("select D.TASK_MASTER_DTL_ID, D.FROM_USERNAME, D.TO_USERNAME, "
//				+ "D.FROM_DATE, D.TO_DATE, "
//				+"H.INV_GRP_ID, H.TRAN_TYPE, H.APPR_LEVEL "
//				+"from TASKS_MASTER H INNER JOIN TASK_MASTER_DTL D ON H.TASK_MASTER_ID = D.TASK_ID "
//				+"and trunc(sysdate) >= trunc(from_date) and trunc(sysdate) <= trunc(to_date) "
//				+"and IDENTITYLINKTYPE_VAL <> TO_USERNAME and IDENTITYLINKTYPE = 'assignee'").list();
//		session.close();
//		return tasks;
		return null;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<TaskMaster> getTaskMasterByDetailIds(List<Long> detailIds) throws Exception {

		List<TaskMaster> list = null;
		// Session session = sessionFactory.openSession();
//		SQLQuery query = session.createSQLQuery("select h.task_master_id as task_id, h.inv_grp_id, h.appr_level "
//				+ "from tasks_master h inner join task_master_dtl d "
//				+"on d.task_id = h.task_master_id and d.task_master_dtl_id IN (:taskIds)");
//		query.setParameterList("taskIds", detailIds);
//		query.addScalar("task_id", StandardBasicTypes.LONG);
//		query.addScalar("inv_grp_id", StandardBasicTypes.LONG);
//		query.addScalar("appr_level", StandardBasicTypes.LONG);
//		query.setResultTransformer(Transformers.aliasToBean(TaskMaster.class));
//		List<TaskMaster> list = query.list();
//		session.close();
		return list;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Object> getTasksToReverseDelegation() throws Exception {
//		Session session = sessionFactory.openSession();
//		List<Object> tasks = session.createSQLQuery("select D.TASK_MASTER_DTL_ID, D.FROM_USERNAME, D.TO_USERNAME, "
//				+ "D.FROM_DATE, D.TO_DATE, "
//				+"H.INV_GRP_ID, H.TRAN_TYPE, H.APPR_LEVEL "
//				+"from TASKS_MASTER H INNER JOIN TASK_MASTER_DTL D ON H.TASK_MASTER_ID = D.TASK_ID "
//				+"and trunc(sysdate) > trunc(to_date) "
//				+"and IDENTITYLINKTYPE = 'assignee'").list();
//		session.close();
		return null;
	}

	@Override
	public void revertCustomTask(Long tran_id, String tran_type) throws Exception {
		EntityManager em = null;
		try {
			em = emf.createEntityManager();
			StringBuffer sb = new StringBuffer(
					"update CUSTOM_TASK set status='PENDING', COMPLETED_BY='' WHERE TASKID =(select ct.TASKID ");
			sb.append("from CUSTOM_TASK ct, act_ru_task actt, ACT_RU_EXECUTION ae, custom_process_instance cp ");
			sb.append("where cp.TRAN_REF_ID =:tran_id and cp.TRAN_TYPE=:tran_type ");
			sb.append("and ct.act_taskid =actt.id_  and ae.ID_=actt.PROC_INST_ID_ ");
			sb.append("and cp.PROCESS_INSTANCE_ID = ae.ID_) ");
			Query query = em.createNativeQuery(sb.toString());
			query.setParameter("tran_id", tran_id);
			query.setParameter("tran_type", tran_type);
			query.executeUpdate();

		} catch (Exception e) {
			System.out.println("Error Occurred :" +e.getMessage());// uncomment asneeded --;
			throw e;
		} finally {
			if (em != null) {
				em.close();
			}
		}
	}

	// added by krishna: 26-07-2018
	// asked by Milind/Uday Sir - show all process pending/approved created by same
	// user
	@Override
	public List<InboxTask> getNotificationPendApprList(String user_id, Date start_date, Date end_date, Boolean isHoUser)
			throws ParseException {
//		StringBuffer buffer=new StringBuffer();
//		buffer.append("SELECT cm.CUST_NAME, dp.LOC_NAME, cp.TRAN_TYPE, cp.TRAN_REF_ID, cp.TRAN_NO, ct.APPROVAL_TYPE, ");
//		buffer.append("ACCTT.TRAN_TYPE_DESCRIPTION, cp.FIN_YEAR_ID FIN_YEAR_ID, cp.STOCK_POINT_ID STOCK_POINT_ID, DP.LOC_ID LOC_ID, ");
//		buffer.append("cp.DOC_TYPE DOC_TYPE, cp.PROCESS_INSTANCE_ID PROCESS_INSTANCE_ID, cp.AMOUNT AMOUNT, cm.DESTINATION DESTINATION, ");
//		buffer.append("stp.BILLING_NAME BILLING_NAME, ct.RECEIVED_ON RECEIVED_ON,CP.STARTED_BY INITIATOR, cp.STATUS, ");
//		buffer.append("case when cp.STATUS = 'COMPLETED' then UM1.USER_DESC else ");
//		buffer.append("(case when ci.group_id is null then UM.USER_DESC else to_char(ag.name_) end) end APPROVER, ct.SUBJECT invgroup ");
//		buffer.append("FROM CUSTOM_PROCESS_INSTANCE cp ");
//		buffer.append("INNER JOIN CUSTOM_TASK ct ON cp.PROCESS_ID=ct.PROCESS_ID ");
//		buffer.append("and ct.taskid in (select max(taskid) from CUSTOM_TASK where PROCESS_ID=cp.PROCESS_ID) ");
//		buffer.append("LEFT JOIN ACT_RU_TASK ACT ON ACT.PROC_INST_ID_=cp.PROCESS_INSTANCE_ID ");
//		buffer.append("INNER JOIN ACC_TRAN_TYPE ACCTT ON (ACCTT.TRAN_TYPE=cp.TRAN_TYPE) ");
//		buffer.append("LEFT JOIN CUSTOMER_MASTER cm ON(cm.CUST_ID=cp.CUST_ID) ");
//		buffer.append("LEFT JOIN DEPOT_MASTER dp ON(dp.LOC_ID =cp.LOC_ID) ");
//		buffer.append("LEFT JOIN STOCKPOINT_MST stp ON(stp.STOCK_POINT_ID=cp.STOCK_POINT_ID) ");
//		buffer.append("left join CUSTOM_IDENTITYLINK ci on ci.ACT_TASK_ID = ACT.ID_ ");
//		buffer.append("left join act_id_group ag on ag.ID_ = ci.GROUP_ID ");
//		buffer.append("LEFT JOIN USERMAST UM ON UM.USER_NAME=to_char(ACT.ASSIGNEE_) ");
//		buffer.append("LEFT JOIN USERMAST UM1 ON UM1.USER_NAME=to_char(ct.COMPLETED_BY) ");
//		if(isHoUser){
//			buffer.append("WHERE cp.TRAN_TYPE='10' ");
//		}else{
//			buffer.append("WHERE cp.STARTED_BY=:user_id ");
//		}
//
//		buffer.append("AND trunc(cp.STARTTIME) BETWEEN (select sysdate-20 from dual) and (select sysdate from dual) ");
//		buffer.append("ORDER BY ct.RECEIVED_ON DESC");
//
//		StringType stringType=new StringType();
//		SessionFactory sFact = (SessionFactory) TransactionManager.getTransactionId().get("sessionfactory");
//		Query query = sFact.openSession().createSQLQuery(buffer.toString())
//				.addScalar("CUST_NAME", stringType)
//				.addScalar("LOC_NAME", stringType)
//				.addScalar("TRAN_TYPE", stringType)
//				.addScalar("TRAN_REF_ID", stringType)
//				.addScalar("TRAN_NO", stringType)
//				.addScalar("APPROVAL_TYPE", stringType)
//				.addScalar("TRAN_TYPE_DESCRIPTION", stringType)
//				.addScalar("FIN_YEAR_ID", stringType)
//				.addScalar("STOCK_POINT_ID", stringType)
//				.addScalar("LOC_ID", stringType)
//				.addScalar("DOC_TYPE", stringType)
//				.addScalar("PROCESS_INSTANCE_ID", stringType)
//				.addScalar("AMOUNT", stringType)
//				.addScalar("DESTINATION", stringType)
//				.addScalar("BILLING_NAME", stringType)
//				.addScalar("RECEIVED_ON", stringType)
//				.addScalar("INITIATOR", stringType)
//				.addScalar("STATUS", stringType)
//				.addScalar("APPROVER", stringType)
//				.addScalar("INVGROUP", stringType);
//
//		if(!isHoUser){
//			System.out.println("start by   ---"+user_id);
//			query.setParameter("user_id", user_id);
//		}
//
//		/*query.setParameter("start_date", Utility.convertDatetoString(start_date));
//		query.setParameter("end_date", Utility.convertDatetoString(end_date));*/
//
//		System.out.println("List Size --"+query.list().size());
//		List<InboxTask> inboxTasks=new ArrayList<InboxTask>();
//		List<Object> objects=query.list();
//
//		for(Object object:objects){
//			Object[] obj=(Object[]) object;
//			InboxTask inboxTask=new InboxTask();
//			inboxTask.setCust_name(obj[0]!=null?obj[0].toString():"");
//			inboxTask.setLoc_name(obj[1]!=null?obj[1].toString():"");
//			inboxTask.setTran_type(obj[2]!=null?obj[2].toString():"");
//			inboxTask.setTran_ref_id(obj[3]!=null?obj[3].toString():"");
//			inboxTask.setTran_no(obj[4]!=null?obj[4].toString():"");
//			inboxTask.setApproval_type(obj[5]!=null?obj[5].toString():"");
//			inboxTask.setTran_type_description(obj[6]!=null?obj[6].toString():"");
//			inboxTask.setFin_year_id(obj[7]!=null?obj[7].toString():"");
//			inboxTask.setStock_point_id(obj[8]!=null?obj[8].toString():"");
//			inboxTask.setLoc_id(obj[9]!=null?obj[9].toString():"");
//			inboxTask.setDoc_type(obj[10]!=null?obj[10].toString():"");
//			inboxTask.setProcess_instance_id(obj[11]!=null?obj[11].toString():"");
//			inboxTask.setAmount(obj[12]!=null?obj[12].toString():"");
//			inboxTask.setCust_dest(obj[13]!=null?obj[13].toString():"");
//			inboxTask.setStock_point_name(obj[14]!=null?obj[14].toString():"");
//			inboxTask.setReceived_on(obj[15]!=null?Utility.getdDateFormat(obj[15].toString()):"");
//			inboxTask.setInitiator(obj[16]!=null?obj[16].toString():"");
//			inboxTask.setStatus(obj[17]!=null?obj[17].toString():"");
//			inboxTask.setApprover(obj[18]!=null?obj[18].toString():"");
//			inboxTask.setInvoiceGroup(obj[19]!=null?obj[19].toString():"NA");
//			inboxTasks.add(inboxTask);
//		}
		return null;
	}

	/*
	 * @Override public List<TaskMaster> getAlternateTask(Long tran_id, Long
	 * inv_group, String tran_type) throws Exception { StringBuffer sb = new
	 * StringBuffer();
	 * System.out.println("tran_id::"+tran_id+"::inv_group::"+inv_group+
	 * "::tran_type::"+tran_type); String orderby ="order by TM.APPR_LEVEL"; sb.
	 * append("SELECT DISTINCT TM.TASK_MASTER_ID as task_id, TM.task_name, TM.completion_time, TM.reminder_time, "
	 * )
	 * .append("TM.escalate_after, TM.escalate_to_type, TM.escalate_to, TM.appr_level "
	 * )
	 * .append("FROM TASKS_MASTER TM, APPROVAL_DECISION_DETAIL AD, SALES_ORD_APPROVAL SO "
	 * ) .append("where TM.TRAN_TYPE=:tran_type ")
	 * .append("AND SO.SLNO=:tran_id AND SO.INV_GROUP_ID=:inv_group AND AD.TRAN_TYPE=:tran_type AND (AD.INV_GRP_ID=:inv_group or AD.INV_GRP_ID = 0 )"
	 * )
	 * .append("AND TM.VALUE_RANGE_FROM <= AD.DECISION_VALUE AND TM.VALUE_RANGE_TO >= AD.DECISION_VALUE "
	 * )
	 * .append("AND AD.VALUE1_FROM <= case when so.cr_limit < 0 then 0 else so.cr_limit end AND AD.VALUE1_TO >= SO.CR_LIMIT "
	 * ); sb.append(" AND (tm.INV_GRP_ID = :inv_group or tm.INV_GRP_ID = 0) ");
	 * 
	 * StringBuffer sb2 = new
	 * StringBuffer("AND AD.VALUE2_FROM <= (SO.CURR_INV_AMT - SO.CR_LIMIT) AND AD.VALUE2_TO >= (SO.CURR_INV_AMT - SO.CR_LIMIT) "
	 * ) //commented as asked by Uday Sir on 4th September, 2018 //.
	 * append("AND AD.PERC1_FROM <= SO.EXT_CRLMT_PERCENT AND AD.PERC1_TO >= SO.EXT_CRLMT_PERCENT "
	 * ) .append(orderby);
	 * 
	 * SQLQuery query = session.createSQLQuery(sb.toString()+sb2.toString());
	 * query.setParameter("tran_id", tran_id); query.setParameter("inv_group",
	 * inv_group); query.setParameter("tran_type", tran_type);
	 * query.addScalar("task_id", StandardBasicTypes.LONG);
	 * query.addScalar("task_name", StandardBasicTypes.STRING);
	 * query.addScalar("completion_time", StandardBasicTypes.INTEGER);
	 * query.addScalar("reminder_time", StandardBasicTypes.INTEGER);
	 * query.addScalar("escalate_after", StandardBasicTypes.STRING);
	 * query.addScalar("escalate_to_type", StandardBasicTypes.STRING);
	 * query.addScalar("escalate_to", StandardBasicTypes.STRING);
	 * query.addScalar("appr_level", StandardBasicTypes.LONG);
	 * query.addScalar("reminder_time", StandardBasicTypes.INTEGER);
	 * query.setResultTransformer(Transformers.aliasToBean(TaskMaster.class));
	 * List<TaskMaster> list = query.list(); if(list==null || list.size()<=0){
	 * SQLQuery query1 = session.createSQLQuery(sb.append(orderby).toString());
	 * query1.setParameter("tran_id", tran_id); query1.setParameter("inv_group",
	 * inv_group); query1.setParameter("tran_type", tran_type);
	 * query1.addScalar("task_id", StandardBasicTypes.LONG);
	 * query1.addScalar("task_name", StandardBasicTypes.STRING);
	 * query1.addScalar("completion_time", StandardBasicTypes.INTEGER);
	 * query1.addScalar("reminder_time", StandardBasicTypes.INTEGER);
	 * query1.addScalar("escalate_after", StandardBasicTypes.STRING);
	 * query1.addScalar("escalate_to_type", StandardBasicTypes.STRING);
	 * query1.addScalar("escalate_to", StandardBasicTypes.STRING);
	 * query1.addScalar("appr_level", StandardBasicTypes.LONG);
	 * query1.addScalar("reminder_time", StandardBasicTypes.INTEGER);
	 * query1.setResultTransformer(Transformers.aliasToBean(TaskMaster.class)); list
	 * = query1.list(); } return list; }
	 */

	@SuppressWarnings("unchecked")
	// CREDIT LIMIT FOR SALES ORDER APPROVAL QUERY
	// ONLY FOR SALES ORDER WITH CREDIT LIMIT CHECK
	// CHETAN
	@Override
	public List<TaskMaster> getAlternateTask(Long tran_id, Long inv_group, String tran_type) throws Exception {
//		StringBuffer sb = new StringBuffer();
//		System.out.println("tran_id::"+tran_id+"::inv_group::"+inv_group+"::tran_type::"+tran_type);
//		sb.append(" SELECT TASK_MASTER_ID task_id, TASK_NAME, COMPLETION_TIME, REMINDER_TIME, ");
//		sb.append(" ESCALATE_AFTER, ESCALATE_TO_TYPE, ESCALATE_TO, APPR_LEVEL, CONSIDER_LIMIT, PERC1_TO FROM ( ");
//		sb.append(" SELECT TM.TASK_MASTER_ID, TM.TASK_NAME, TM.COMPLETION_TIME, TM.REMINDER_TIME, ");
//		sb.append(" TM.ESCALATE_AFTER, TM.ESCALATE_TO_TYPE, TM.ESCALATE_TO, TM.APPR_LEVEL , ");
//		sb.append(" CASE WHEN ( ( ( AD.PERC1_TO / 100 ) * SO.ACTUAL_CR_LIMIT ) < ( SO.CURR_INV_AMT - SO.CR_LIMIT ) ) ");
//		sb.append(" THEN ( SO.CURR_INV_AMT - SO.CR_LIMIT ) ELSE ( ( AD.PERC1_TO / 100 ) * SO.ACTUAL_CR_LIMIT ) END CONSIDER_LIMIT, ");
//		sb.append(" AD.VALUE1_FROM, AD.VALUE1_TO, AD.VALUE2_FROM , AD.VALUE2_TO, AD.APPROVER_ID, AD.PERC1_TO ");
//		sb.append(" FROM APPROVAL_DECISION_DETAIL AD ");
//		sb.append(" INNER JOIN TASKS_MASTER TM ON TM.TRAN_TYPE = AD.TRAN_TYPE AND TM.VALUE_RANGE_FROM <= AD.DECISION_VALUE ");
//		sb.append(" AND TM.VALUE_RANGE_TO >= AD.DECISION_VALUE ");
//		sb.append(" INNER JOIN SALES_ORD_APPROVAL SO ON SO.INV_GROUP_ID IN ( TM.INV_GRP_ID, 0 ) ");
//		sb.append(" AND SO.INV_GROUP_ID IN ( AD.INV_GRP_ID, 0 ) ");
//		sb.append(" WHERE AD.TRAN_TYPE = :tran_type AND TM.TRAN_TYPE = :tran_type AND TM.APPR_LEVEL = AD.LEVEL_NO AND SO.INV_GROUP_ID = :inv_group AND ");
//		sb.append(" SO.SLNO = :tran_id AND SO.ACTUAL_CR_LIMIT >= AD.VALUE1_FROM AND AD.VALUE1_TO >= SO.ACTUAL_CR_LIMIT ) ");
//		sb.append(" WHERE CONSIDER_LIMIT >= VALUE2_FROM AND CONSIDER_LIMIT <= VALUE2_TO ");
//
//		SQLQuery query = session.createSQLQuery(sb.toString());
//		query.setParameter("tran_id", tran_id);
//		query.setParameter("inv_group", inv_group);
//		query.setParameter("tran_type", tran_type);
//		query.addScalar("task_id", StandardBasicTypes.LONG);
//		query.addScalar("task_name", StandardBasicTypes.STRING);
//		query.addScalar("completion_time", StandardBasicTypes.INTEGER);
//		query.addScalar("reminder_time", StandardBasicTypes.INTEGER);
//		query.addScalar("escalate_after", StandardBasicTypes.STRING);
//		query.addScalar("escalate_to_type", StandardBasicTypes.STRING);
//		query.addScalar("escalate_to", StandardBasicTypes.STRING);
//		query.addScalar("appr_level", StandardBasicTypes.LONG);
//		query.addScalar("consider_limit", StandardBasicTypes.BIG_DECIMAL);
//		query.addScalar("perc1_to", StandardBasicTypes.BIG_DECIMAL);
//		query.setResultTransformer(Transformers.aliasToBean(TaskMaster.class));
//
//		List<TaskMaster> list = query.list();
//		if (list != null && !list.isEmpty()) {
//			return list;
//		}
		return null;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Long> getDistinctTaskMasterIdsByApproverTranTypeAndLogicType(String username, String tran_type,
			String logic_type, Long inv_grp_id) throws Exception {
//		StringBuffer sb = new StringBuffer();
//		sb.append(" select distinct tm.task_master_id task_ids ");
//		sb.append(" from tasks_master tm, approval_decision_detail d, task_master_dtl td, appr_parameters ap ");
//		sb.append(" where tm.task_master_id = td.task_id ");
//		sb.append(" and td.identitylinktype_val = :username ");
//		sb.append(" and d.tran_type = tm.tran_type and ap.para_type = d.tran_type and ap.para_type = tm.tran_type ");
//		sb.append(" and tm.tran_type = :tran_type and d.tran_type = :tran_type and ap.para_type = :tran_type ");
//		sb.append(" and ap.para_code = :logic_type and d.logic_type = :logic_type ");
//		sb.append(" and d.decision_value >= tm.value_range_from and d.decision_value <= tm.value_range_to ");
//		if(inv_grp_id != null && inv_grp_id.compareTo(0L) != 0) {
//			sb.append(" and tm.inv_grp_id = :inv_grp_id ");
//		}
//		SQLQuery query = sessionFactory.getCurrentSession().createSQLQuery(sb.toString());
//		query.setParameter("username", username);
//		query.setParameter("tran_type", tran_type);
//		query.setParameter("logic_type", logic_type);
//		if(inv_grp_id != null && inv_grp_id.compareTo(0L) != 0) {
//			query.setParameter("inv_grp_id", inv_grp_id);
//		}
//		query.addScalar("task_ids", StandardBasicTypes.LONG);
//		return query.list();
		return null;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<TaskMaster> getTaskBetweenValue1FromAndTo(Long inv_group, String tran_type, BigDecimal currinv_amt,
			String logic_type, Long appr_level) throws Exception {
//		Session session = null;
//		List<TaskMaster> list = null;
//		System.out.println("inv_group::"+inv_group);
//		System.out.println("tran_type::"+tran_type);
//		System.out.println("currinv_amt::"+currinv_amt);
//		System.out.println("logic_type::"+logic_type);
//		System.out.println("appr_level::"+appr_level);
//		try {
//			session = sessionFactory.openSession();
//			StringBuffer sb = new StringBuffer();
//			sb.append(" select distinct tm.task_master_id task_id, tm.escalate_after ");
//			sb.append(" from tasks_master tm, approval_decision_header h, approval_decision_detail d ");
//			sb.append(" where h.status = 'A' and h.tran_type = :tran_type and h.tran_type = tm.tran_type ");
//			sb.append(" and d.tran_type = tm.tran_type and d.tran_type = :tran_type and d.logic_type = :logic_type ");
//			sb.append(" and d.decision_value >= tm.value_range_from and d.decision_value <= tm.value_range_to ");
//			sb.append(" and (tm.inv_grp_id = :inv_group or tm.inv_grp_id = 0) and tm.tran_type = :tran_type ");
//			sb.append(" and d.value1_from <= :amount and d.value1_to >= :amount ");
//			if(appr_level != null) {
//				sb.append(" and tm.appr_level = :appr_level ");
//			}
//			SQLQuery query = session.createSQLQuery(sb.toString());
//			query.setParameter("tran_type", tran_type);
//			query.setParameter("logic_type", logic_type);
//			query.setParameter("amount", currinv_amt);
//			query.setParameter("inv_group", inv_group);
//			if(appr_level != null) {
//				query.setParameter("appr_level", appr_level);
//			}
//			query.addScalar("task_id", StandardBasicTypes.LONG);
//			query.addScalar("escalate_after", StandardBasicTypes.STRING);
//			query.setResultTransformer(Transformers.aliasToBean(TaskMaster.class));
//			list = query.list();
//		} finally {
//			if(session != null) {
//				session.close();
//			}
//		}
//		if(list != null && !list.isEmpty()) {
//			return list;
//		}
		return null;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<TaskMaster> getTaskWithoutCondition(Long inv_group, String tran_type, String logic_type,
			Long appr_level) throws Exception {
//		Session session = null;
//		List<TaskMaster> list = null;
//		try {
//			session = sessionFactory.openSession();
//			StringBuffer sb = new StringBuffer();
//			sb.append(" select distinct tm.task_master_id task_id, tm.escalate_after ");
//			sb.append(" from tasks_master tm, approval_decision_header h, approval_decision_detail d ");
//			sb.append(" where h.status = 'A' and h.tran_type = :tran_type and h.tran_type = tm.tran_type ");
//			sb.append(" and d.tran_type = tm.tran_type and d.tran_type = :tran_type and d.logic_type = :logic_type ");
//			sb.append(" and d.decision_value >= tm.value_range_from and d.decision_value <= tm.value_range_to ");
//			sb.append(" and tm.tran_type = :tran_type ");
//			if(inv_group != null) {
//				sb.append(" (tm.inv_grp_id = :inv_group or tm.inv_grp_id = 0) ");
//			}
//			if(appr_level != null) {
//				sb.append(" and tm.appr_level = :appr_level ");
//			}
//			SQLQuery query = session.createSQLQuery(sb.toString());
//			query.setParameter("tran_type", tran_type);
//			query.setParameter("logic_type", logic_type);
//			if(inv_group != null) {
//				query.setParameter("inv_group", inv_group);
//			}
//			if(appr_level != null) {
//				query.setParameter("appr_level", appr_level);
//			}
//			query.addScalar("task_id", StandardBasicTypes.LONG);
//			query.addScalar("escalate_after", StandardBasicTypes.STRING);
//			query.setResultTransformer(Transformers.aliasToBean(TaskMaster.class));
//			list = query.list();
//		} finally {
//			if(session != null) {
//				session.close();
//			}
//		}
//		if(list != null && !list.isEmpty()) {
//			return list;
//		}
		return null;
	}

	@Override
	public List<MailBean> getMailSendDetails(Long id, String nexapprovar, String tran_type) {
		EntityManager em = null;
		List<MailBean> mailDetails = null;
		try {
			StringBuffer sb = new StringBuffer();
			if (nexapprovar != null) {
				sb.append(
						" select HR.EMP_ID,HR.EMP_EMAIL,RTRIM(HR.EMP_FNM)+' '+RTRIM(HR.emp_lnm) ASSIGNEE_NAME ,HR.EMP_LNM,ct.ACT_TASKID,art.ASSIGNEE_ ,cpi.STARTED_BY,RTRIM(HR_ST.EMP_FNM)+' '+RTRIM(HR_ST.emp_lnm) STARTED_NAME,ct.STATUS from ACT_RU_TASK art  ");
				sb.append(" join  CUSTOM_PROCESS_INSTANCE cpi on CPI.PROCESS_INSTANCE_ID=ART.PROC_INST_ID_  ");
				sb.append(" join  CUSTOM_TASK ct on CPI.PROCESS_ID=ct.PROCESS_ID  ");
				sb.append("  JOIN hr_m_employee HR  ");
				sb.append(" ON  RTRIM(art.assignee_)=RTRIM(HR.EMP_ID)  ");
				sb.append(" JOIN hr_m_employee HR_ST ");
				sb.append(" ON  RTRIM(cpi.STARTED_BY)=RTRIM(HR_ST.EMP_ID)  ");
				sb.append(" WHERE CPI.TRAN_REF_ID=:tran_ref_id and ct.STATUS='PENDING' and CPI.TRAN_TYPE=:tran_type");
				Query query = entityManager.createNativeQuery(sb.toString(), Tuple.class);
				query.setParameter("tran_ref_id", id);
				query.setParameter("tran_type", tran_type);
				System.out.println("Mail Query " + sb.toString());
				List<Tuple> list = query.getResultList();
				if (list != null && !list.isEmpty()) {
					MailBean object = null;
					mailDetails = new ArrayList<>();
					for (Tuple t : list) {
						object = new MailBean();

						object.setAct_taskid(t.get("ACT_TASKID", String.class));
						object.setEmp_email(t.get("EMP_EMAIL", String.class));
						object.setStarted_by_name(t.get("STARTED_NAME", String.class));
						object.setStarted_by_id(t.get("STARTED_BY", String.class));
						object.setAssignee_(t.get("ASSIGNEE_", String.class));
						object.setAssignee_name(t.get("ASSIGNEE_NAME", String.class));
						object.setStatus(t.get("STATUS", String.class));
						object.setEmp_id(t.get("EMP_ID", String.class));
						mailDetails.add(object);
					}
				} else {

				}
				System.out.println("Product List" + list.size());
				if (!list.isEmpty() && list.size() > 0)
					return mailDetails;

			} else {
				sb.append(
						"  select HR.EMP_EMAIL,RTRIM(HR.EMP_FNM)+' '+RTRIM(HR.emp_lnm) ASSIGNEE_NAME, cpi.STARTED_BY,RTRIM(HR.EMP_FNM)+' '+RTRIM(HR.emp_lnm) STARTED_NAME ,  ");
				sb.append("  ct.COMPLETED_BY, ct.STATUS from CUSTOM_PROCESS_INSTANCE cpi    ");
				sb.append("  join  CUSTOM_TASK ct on CPI.PROCESS_ID=ct.PROCESS_ID    ");
				sb.append("  JOIN hr_m_employee HR  ON  RTRIM(cpi.STARTED_BY)=RTRIM(HR.EMP_ID)   ");
				sb.append(" WHERE CPI.TRAN_REF_ID=:tran_ref_id and CPI.TRAN_TYPE=:tran_type order by ct.TASKID desc  ");
				Query query = entityManager.createNativeQuery(sb.toString(), Tuple.class);
				query.setParameter("tran_ref_id", id);
				query.setParameter("tran_type", tran_type);
				System.out.println("Mail Query " + sb.toString());
				List<Tuple> list = query.getResultList();
				if (list != null && !list.isEmpty()) {
					MailBean object = null;
					mailDetails = new ArrayList<>();
					for (Tuple t : list) {
						object = new MailBean();

						object.setEmp_email(t.get("EMP_EMAIL", String.class));
						object.setStarted_by_name(t.get("STARTED_NAME", String.class));
						object.setStarted_by_id(t.get("STARTED_BY", String.class));
						object.setAssignee_name(t.get("ASSIGNEE_NAME", String.class));
						object.setStatus(t.get("STATUS", String.class));
						object.setCompleted_by(t.get("COMPLETED_BY", String.class));
						mailDetails.add(object);
					}
				} else {

				}
				System.out.println("Product List" + list.size());
				if (!list.isEmpty() && list.size() > 0)
					return mailDetails;

			}

		} finally {
			if (em != null) {
				em.close();
			}
		}
		return mailDetails;

	}

	@Override
	public List<MailBean> getApprovalDetails(Long taskId) {
		EntityManager em = null;
		List<MailBean> mailDetails = null;
		try {
			StringBuffer sb = new StringBuffer();
			sb.append(
					"  select ACT_TASKID, COMPLETED_BY, rtrim(hrm.emp_fnm)+' '+rtrim(hrm.emp_mnm)+' '+rtrim(hrm.emp_lnm) emp_name,cpi.STARTED_BY,RTRIM(hrm.EMP_FNM)+' '+RTRIM(hrm.emp_lnm) STARTED_NAME ,");
			sb.append("  hrm.EMP_DESG_ANG,DECISION,APPR_LEVEL,ct.REMARKS");
			sb.append("  from CUSTOM_PROCESS_INSTANCE cpi");
			sb.append("  join  CUSTOM_TASK ct on CPI.PROCESS_ID=ct.PROCESS_ID ");
			sb.append("  join  HR_M_EMPLOYEE hrm on hrm.emp_id=ct.COMPLETED_BY");
			sb.append("  where ct.PROCESS_ID=(select PROCESS_ID from CUSTOM_TASK where ACT_TASKID=:act_taskid)");
			sb.append("  order by APPR_LEVEL,ACT_TASKID");
			Query query = entityManager.createNativeQuery(sb.toString(), Tuple.class);
			query.setParameter("act_taskid", taskId);
			System.out.println("Mail Query " + sb.toString());
			List<Tuple> list = query.getResultList();
			if (list != null && !list.isEmpty()) {
				MailBean object = null;
				mailDetails = new ArrayList<>();
				for (Tuple t : list) {
					object = new MailBean();

					object.setApprovedByName(t.get("emp_name", String.class));
					object.setApprovedBydesignation(t.get("EMP_DESG_ANG", String.class));
					object.setStarted_by_name(t.get("STARTED_NAME", String.class));
					System.out.println("Reamrk " + t.get("REMARKS", String.class));
					if (t.get("REMARKS", String.class) != null && t.get("REMARKS", String.class) != ""
							&& !t.get("REMARKS", String.class).equals("null"))
						object.setRemarks(t.get("REMARKS", String.class).split(",")[0]);
					else
						object.setRemarks("");
					object.setDescision(t.get("DECISION", String.class));
					mailDetails.add(object);
				}

				System.out.println("Product List" + list.size());
				if (!list.isEmpty() && list.size() > 0)
					return mailDetails;

			}

		} finally {
			if (em != null) {
				em.close();
			}
		}
		return mailDetails;
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public void deletePreviousTaskMaster(Long allocId) throws Exception {
		this.entityManager.createQuery("DELETE FROM TaskMaster WHERE fs_id = :allocId").setParameter("allocId", allocId)
				.executeUpdate();
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public void deletePreviousTaskMasterDetail(Long allocId) throws Exception {
		this.entityManager.createQuery(
				"DELETE FROM Task_Master_Dtl  WHERE task_id in(select task_id from TaskMaster WHERE fs_id = :allocId)")
				.setParameter("allocId", allocId).executeUpdate();
	}

	@Override
	public List<ApprLevelBean> getApprovalDetails(Long divId, String tranType) throws Exception {
		EntityManager em = null;
		List<ApprLevelBean> list = null;
		try {
			StringBuffer sb = new StringBuffer();
			sb.append(" SELECT TM.TASK_MASTER_ID,TM.TASK_NAME,TM.INV_GRP_ID DIVISION,TM.APPR_LEVEL,TM.TRAN_TYPE,");
			sb.append(" TD.IDENTITYLINKTYPE_VAL APPR_ID,");
			sb.append(" CONCAT(USR.EMP_FNM,ISNULL(USR.EMP_MNM,' '),ISNULL(USR.EMP_LNM,' ')) APPR_NAME");
			sb.append(" FROM TASKS_MASTER TM JOIN TASK_MASTER_DTL TD ON TM.TASK_MASTER_ID = TD.TASK_ID");
			sb.append(" JOIN hr_m_employee USR ON TD.IDENTITYLINKTYPE_VAL = USR.EMP_ID");
			sb.append(" WHERE TM.INV_GRP_ID =:divId AND TM.TRAN_TYPE =:tranType");
			Query query = entityManager.createNativeQuery(sb.toString(), Tuple.class);
			query.setParameter("divId", divId);
			query.setParameter("tranType", tranType);
			List<Tuple> tuples = query.getResultList();
			if (tuples != null && !tuples.isEmpty()) {
				list = new ArrayList<>();
				ApprLevelBean object = null;
				for (Tuple t : tuples) {
					object = new ApprLevelBean();
					object.setApproverName(t.get("APPR_NAME", String.class));
					object.setEmpId(t.get("APPR_ID", String.class));
					object.setApprLevel(t.get("APPR_LEVEL", BigDecimal.class).toString());
					list.add(object);

				}
				if (!list.isEmpty() && list.size() > 0)
					return list;

			} else {
				list = new ArrayList<>();
				ApprLevelBean object = new ApprLevelBean();
				object.setApprLevel("1");
				object.setApproverName("Not Set");
				list.add(object);
			}

		} finally {
			if (em != null) {
				em.close();
			}
		}
		return list;
	}

	@Override
	public List<ApprLevelBean> getApprovers(Long divId, String role, String tranType) throws Exception {
		EntityManager em = null;
		List<ApprLevelBean> list = null;
		System.out.println("divId " + divId);
		System.out.println("tranType " + tranType);
		try {
			StringBuffer sb = new StringBuffer();
			sb.append(
					" SELECT USR.EMP_ID,CONCAT(USR.EMP_FNM,ISNULL(USR.EMP_MNM,' '),ISNULL(USR.EMP_LNM,' ')) BRAND_MANAGER");
			sb.append(" FROM hr_m_employee USR JOIN am_m_emp_div_access DIV ON USR.EMP_ID = DIV.ediv_emp_id");
			sb.append(" JOIN am_m_login_detail LD ON USR.EMP_ID = LD.LD_EMP_CB_ID WHERE");
			//sb.append(" USR.emp_egrp_id =:role AND");
			sb.append(" DIV.EDIV_DIV_ID =:divId");
			sb.append(" AND LD.LD_EXEC_ASST_IND = 'N'");
			Query query = entityManager.createNativeQuery(sb.toString(), Tuple.class);
			query.setParameter("divId", divId);
			//query.setParameter("role", role);
			List<Tuple> tuples = query.getResultList();
			if (tuples != null && !tuples.isEmpty()) {
				list = new ArrayList<>();
				ApprLevelBean object = null;
				for (Tuple t : tuples) {
					object = new ApprLevelBean();
					object.setApproverName(t.get("BRAND_MANAGER", String.class));
					object.setEmpId(t.get("EMP_ID", String.class));
					list.add(object);

				}
				if (!list.isEmpty() && list.size() > 0)
					return list;

			}

		} finally {
			if (em != null) {
				em.close();
			}
		}
		return list;
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public void deletePreviousTaskMaster(Long div_id, String tranType) throws Exception {
		this.entityManager.createQuery("DELETE FROM TaskMaster WHERE inv_grp_id = :div_id and tran_type=:tran_type")
				.setParameter("div_id", div_id).setParameter("tran_type", tranType).executeUpdate();
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public void deletePreviousTaskMasterDetail(Long div_id, String tranType) throws Exception {
		this.entityManager.createQuery(
				"DELETE FROM Task_Master_Dtl  WHERE task_id in(select task_id from TaskMaster WHERE inv_grp_id = :div_id and tran_type=:tran_type)")
				.setParameter("div_id", div_id).setParameter("tran_type", tranType).executeUpdate();
	}

	@Override
	public Long getCustomTaskByTaskId(String taskid) {
		// TODO Auto-generated method stub
		try {
			StringBuffer sb = new StringBuffer();

			sb.append(" SELECT cm.PROCESS_ID");
			sb.append(" FROM TASK_MASTER_DTL tmd ");
			sb.append(" LEFT JOIN TASKS_MASTER tm ON tmd.TASK_ID = tm.TASK_MASTER_ID ");
			sb.append(" LEFT JOIN CUSTOM_TASK cm ON tm.TASK_NAME = cm.SUBJECT ");
			sb.append(" WHERE");
			sb.append(" cm.ACT_TASKID = " + taskid);
			sb.append(" AND ");
			sb.append(" cm.status = 'PENDING'");

			List<BigDecimal> list = em.createNativeQuery(sb.toString()).getResultList();
			if (!CollectionUtils.isEmpty(list)) {
				return list.get(0).longValue();
			}

		} catch (Exception e) {
			// TODO: handle exception
			throw e;
		}
		return null;
	}

	@Override
	public void updateTranDetailsInAppoLog() throws Exception {
		// TODO Auto-generated method stub
		try {
			StringBuffer sb = new StringBuffer();

			sb.append(" UPDATE DD SET ACFIL_TRAN_TYPE=CPI.TRAN_TYPE, ACFIL_TRAN_REF_ID=CPI.TRAN_REF_ID,");
			sb.append(" ACFIL_TRAN_TYPE_DESC= AT.TRAN_TYPE_DESCRIPTION");
			sb.append(" FROM ACTIVITI_FINALAPPR_LOG DD");
			sb.append(" JOIN CUSTOM_PROCESS_INSTANCE CPI ON CPI.PROCESS_ID=DD.PROCESS_ID");
			sb.append(" JOIN ACC_TRAN_TYPE AT ON AT.TRAN_TYPE = CPI.TRAN_TYPE and DD.ACFIL_TRAN_REF_ID = 0");

			em.createNativeQuery(sb.toString()).executeUpdate();

		} catch (Exception e) {
			// TODO: handle exception
			throw e;
		}
	}

	@Override
	public String getTranTypeByLocIdAndApprovalType(Long loc_id, String tran_name, String company_code)
			throws Exception {
		EntityManager em = null;
		String tran_type = null;
		try {
			StringBuffer sb = new StringBuffer();
			em = this.emf.createEntityManager();

			sb.append(" SELECT TRAN_TYPE FROM ACC_TRAN_TYPE WHERE LOC_ID=:locId AND FULL_TRAN_NAME =:tran_name ");
			sb.append(" AND COMPANY_CD = :company_code ");

			Query query = em.createNativeQuery(sb.toString());
			query.setParameter("locId", loc_id);
			query.setParameter("tran_name", tran_name);
			query.setParameter("company_code", company_code);

			tran_type = (String) query.getSingleResult();

		} finally {
			if (em != null)
				em.close();
		}
		return tran_type;
	}

	@Override
	public String getEmailIdOfApprover(Long loc_id, String company_cd, String full_tran_name) throws Exception {
		EntityManager em = null;
		String approver_email = null;
		try {
			StringBuffer sb = new StringBuffer();
			em = this.emf.createEntityManager();

			sb.append(
					" select amd.ld_email from ACC_TRAN_TYPE ac inner join TASKS_MASTER tm on tm.TRAN_TYPE = ac.tran_type");
			sb.append(
					" inner join TASK_MASTER_DTL dtl on tm.TASK_MASTER_ID = dtl.TASK_ID inner join am_m_login_detail amd ");
			sb.append(" on amd.ld_emp_cb_id = dtl.IDENTITYLINKTYPE_VAL");
			sb.append(" where ac.loc_id = :locId and ac.FULL_TRAN_NAME = :tran_name and ac.COMPANY_CD = :company_code");
			sb.append(" and dtl.IDENTITYLINKTYPE = 'assignee'");

			Query query = em.createNativeQuery(sb.toString());
			query.setParameter("locId", loc_id);
			query.setParameter("tran_name", full_tran_name);
			query.setParameter("company_code", company_cd);

			approver_email = (String) query.getSingleResult();
		} finally {
			if (em != null)
				em.close();
		}
		return approver_email;
	}

	@Override
	public TaskMaster getTaskMasterByTranTypeFromAccTran(TranNameEnum tran_type_enum, Long loc_id) throws Exception {
		EntityManager em = null;
		try {
			em = this.emf.createEntityManager();
			
			CriteriaBuilder builder = em.getCriteriaBuilder();
			CriteriaQuery<TaskMaster> criteria = builder.createQuery(TaskMaster.class);
			Root<TaskMaster> root = criteria.from(TaskMaster.class);
			
			Subquery<String> subquery_acc_tran = criteria.subquery(String.class); 
			Root<Acc_Tran_Type> acc_tran_root = criteria.from(Acc_Tran_Type.class);
			subquery_acc_tran.select(acc_tran_root.get("tran_type"))
			.where(builder.and(builder.equal(root.get("full_tran_name"), tran_type_enum),
					builder.equal(root.get("loc_id"), loc_id)));
			
			criteria.select(root).where(builder.in(subquery_acc_tran).value(root.get("tran_type")));
			
			TaskMaster tm = em.createQuery(criteria).getSingleResult();
			return tm;
		}
		finally {
			if(em!=null) em.close();
		}
	}

	@Override
	public Long getActTaskIdByTranTypeAndId(Long tran_ref_id, String tran_type) throws Exception {
		EntityManager em = null;
		String actTaskId= null;
		Long longVal = null;
		try {
			StringBuffer sb = new StringBuffer();
			em = this.emf.createEntityManager();

			sb.append("select ct.ACT_TASKID  from CUSTOM_TASK ct, act_ru_task actt, ACT_RU_EXECUTION ae, ");
			sb.append("custom_process_instance cp where cp.TRAN_REF_ID =:tran_ref_id and cp.TRAN_TYPE=:tran_type ");
			sb.append("and ct.act_taskid =actt.id_  and ae.ID_=actt.PROC_INST_ID_ and cp.PROCESS_INSTANCE_ID = ae.ID_");
			
			Query query = em.createNativeQuery(sb.toString());
			query.setParameter("tran_ref_id", tran_ref_id);
			query.setParameter("tran_type", tran_type);

			actTaskId =  (String) query.getSingleResult();
			if(actTaskId!=null)
				longVal = Long.valueOf(actTaskId);
		} finally {
			if (em != null)
				em.close();
		}
		return longVal;
	}

}