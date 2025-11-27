package com.excel.bean;

import java.util.Date;
import java.util.List;

public class ApprovalBean {
	@Override
	public String toString() {
		return "ApprovalBean [approver=" + approver + ", due_date=" + due_date + ", difficulty=" + difficulty
				+ ", instructions=" + instructions + ", approved=" + approved + ", motivation=" + motivation
				+ ", taskid=" + taskid + ", user_id=" + user_id + ", reason_id=" + reason_id + ", tran_id=" + tran_id
				+ ", tran_type=" + tran_type + ", fin_year_id=" + fin_year_id + ", loc_id=" + loc_id + ", cust_name="
				+ cust_name + ", inv_grp=" + inv_grp + ", stk_adj_id=" + stk_adj_id + ", finYrId=" + finYrId
				+ ", tranIds=" + tranIds + ", isCheck=" + isCheck + ", status=" + status + ", remark=" + remark
				+ ", approvalType=" + approvalType + ", startedBy=" + startedBy + ", serverUrl=" + serverUrl + ", port="
				+ port + ", companycode=" + companycode + ", full_tran_name=" + full_tran_name + "]";
	}

	String approver;
	Date due_date;
	Long difficulty;
	String instructions;
	String approved;
	String motivation;
	private String taskid;
	private String user_id;
	
	private Long reason_id;
	private Long tran_id;
	private Long tran_type;
	private Long fin_year_id;
	private Long loc_id;
	private String cust_name;
	private Long inv_grp;
	private Long stk_adj_id;
	private Long finYrId;
	private List<Long> tranIds;
	private List<Boolean> isCheck; 
	private String status;
	private String remark;
	private String approvalType;
	private String startedBy;
	private String serverUrl;
	private int port;
	private String companycode;
	private String full_tran_name;
	private String ip_addres;
	
	
	
	public String getIp_addres() {
		return ip_addres;
	}

	public void setIp_addres(String ip_addres) {
		this.ip_addres = ip_addres;
	}

	public String getUser_id() {
		return user_id;
	}

	public void setUser_id(String user_id) {
		this.user_id = user_id;
	}

	public String getFull_tran_name() {
		return full_tran_name;
	}

	public void setFull_tran_name(String full_tran_name) {
		this.full_tran_name = full_tran_name;
	}

	public String getCompanycode() {
		return companycode;
	}

	public void setCompanycode(String companycode) {
		this.companycode = companycode;
	}

	public String getApprover() {
		return approver;
	}

	public void setApprover(String approver) {
		this.approver = approver;
	}

	public Date getDue_date() {
		return due_date;
	}

	public void setDue_date(Date due_date) {
		this.due_date = due_date;
	}

	public Long getDifficulty() {
		return difficulty;
	}

	public void setDifficulty(Long difficulty) {
		this.difficulty = difficulty;
	}

	public String getInstructions() {
		return instructions;
	}

	public void setInstructions(String instructions) {
		this.instructions = instructions;
	}

	public void setApproved(String approved) {
		this.approved = approved;
	}
	
	public String getApproved() {
		return approved;
	}

	public String getMotivation() {
		return motivation;
	}

	public void setMotivation(String motivation) {
		this.motivation = motivation;
	}

	public String getTaskid() {
		return taskid;
	}

	public void setTaskid(String taskid) {
		this.taskid = taskid;
	}

	public Long getReason_id() {
		return reason_id;
	}

	public void setReason_id(Long reason_id) {
		this.reason_id = reason_id;
	}
	
	public Long getTran_id() {
		return tran_id;
	}

	public void setTran_id(Long tran_id) {
		this.tran_id = tran_id;
	}

	public Long getTran_type() {
		return tran_type;
	}

	public void setTran_type(Long tran_type) {
		this.tran_type = tran_type;
	}
	
	public Long getFin_year_id() {
		return fin_year_id;
	}

	public void setFin_year_id(Long fin_year_id) {
		this.fin_year_id = fin_year_id;
	}
		
	public Long getLoc_id() {
		return loc_id;
	}

	public void setLoc_id(Long loc_id) {
		this.loc_id = loc_id;
	}

	public String getCust_name() {
		return cust_name;
	}

	public void setCust_name(String cust_name) {
		this.cust_name = cust_name;
	}

	public Long getInv_grp() {
		return inv_grp;
	}

	public void setInv_grp(Long inv_grp) {
		this.inv_grp = inv_grp;
	}

	public Long getStk_adj_id() {
		return stk_adj_id;
	}

	public void setStk_adj_id(Long stk_adj_id) {
		this.stk_adj_id = stk_adj_id;
	}

	public Long getFinYrId() {
		return finYrId;
	}

	public void setFinYrId(Long finYrId) {
		this.finYrId = finYrId;
	}

	public List<Long> getTranIds() {
		return tranIds;
	}

	public void setTranIds(List<Long> tranIds) {
		this.tranIds = tranIds;
	}

	public List<Boolean> getIsCheck() {
		return isCheck;
	}

	public void setIsCheck(List<Boolean> isCheck) {
		this.isCheck = isCheck;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public String getApprovalType() {
		return approvalType;
	}

	public void setApprovalType(String approvalType) {
		this.approvalType = approvalType;
	}

	public String getStartedBy() {
		return startedBy;
	}

	public void setStartedBy(String startedBy) {
		this.startedBy = startedBy;
	}

	public String getServerUrl() {
		return serverUrl;
	}

	public void setServerUrl(String serverUrl) {
		this.serverUrl = serverUrl;
	}

	public int getPort() {
		return port;
	}

	public void setPort(int port) {
		this.port = port;
	}

}
