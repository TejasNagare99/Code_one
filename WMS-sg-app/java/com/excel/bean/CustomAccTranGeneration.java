package com.excel.bean;

import com.excel.model.Acc_Tran_Type.TranNameEnum;

public class CustomAccTranGeneration {
	private TranNameEnum full_tran_name;
	private String tran_type;
	private Long task_master;
	private Long task_master_dtl_id;
	
	
	public TranNameEnum getFull_tran_name() {
		return full_tran_name;
	}
	public void setFull_tran_name(TranNameEnum full_tran_name) {
		this.full_tran_name = full_tran_name;
	}
	public String getTran_type() {
		return tran_type;
	}
	public void setTran_type(String tran_type) {
		this.tran_type = tran_type;
	}
	public Long getTask_master() {
		return task_master;
	}
	public void setTask_master(Long task_master) {
		this.task_master = task_master;
	}
	public Long getTask_master_dtl_id() {
		return task_master_dtl_id;
	}
	public void setTask_master_dtl_id(Long task_master_dtl_id) {
		this.task_master_dtl_id = task_master_dtl_id;
	}
	
	public CustomAccTranGeneration(TranNameEnum full_tran_name, String tran_type, Long task_master,
			Long task_master_dtl_id) {
		super();
		this.full_tran_name = full_tran_name;
		this.tran_type = tran_type;
		this.task_master = task_master;
		this.task_master_dtl_id = task_master_dtl_id;
	}
	
	@Override
	public String toString() {
		return "CustomAccTranGeneration [full_tran_name=" + full_tran_name + ", tran_type=" + tran_type
				+ ", task_master=" + task_master + ", task_master_dtl_id=" + task_master_dtl_id + "]";
	}
	
	
}
