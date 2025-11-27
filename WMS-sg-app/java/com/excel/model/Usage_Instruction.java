package com.excel.model;

import java.util.Date;

import javax.annotation.Generated;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "usage_instruction")
public class Usage_Instruction {

private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "USAGE_ID")
	private Long usage_id;
	
	@Column(name = "USAGE_COMP_CD")
	private String usage_comp_cd;
	
	@Column(name = "USAGE_FIN_YEAR")
	private String usage_fin_year;
	
	@Column(name = "USAGE_MONTH_USE")
	private String usage_month_use;
	
	@Column(name = "USAGE_PROD_ID")
	private Long usage_prod_id;
	
	@Column(name = "USAGE_PROD_CODE")
	private String usage_prod_code;
	
	@Column(name = "USAGE_INSTRUCTION")
	private String usage_instruction;
	
	@Column(name = "USAGE_INS_DT")
	private Date usage_ins_dt;
	
	@Column(name = "USAGE_MOD_DT")
	private Date usage_mod_dt;
	
	@Column(name = "USAGE_INS_IP_ADD")
	private String usage_ins_ip_add;
	
	@Column(name = "USAGE_MOD_IP_ADD")
	private String usage_mod_ip_add;

	

	public Long getUsage_id() {
		return usage_id;
	}

	public void setUsage_id(Long usage_id) {
		this.usage_id = usage_id;
	}

	public String getUsage_comp_cd() {
		return usage_comp_cd;
	}

	public void setUsage_comp_cd(String usage_comp_cd) {
		this.usage_comp_cd = usage_comp_cd;
	}

	public String getUsage_fin_year() {
		return usage_fin_year;
	}

	public void setUsage_fin_year(String usage_fin_year) {
		this.usage_fin_year = usage_fin_year;
	}

	public String getUsage_month_use() {
		return usage_month_use;
	}

	public void setUsage_month_use(String usage_month_use) {
		this.usage_month_use = usage_month_use;
	}

	public String getUsage_prod_code() {
		return usage_prod_code;
	}

	public void setUsage_prod_code(String usage_prod_code) {
		this.usage_prod_code = usage_prod_code;
	}

	public String getUsage_instruction() {
		return usage_instruction;
	}

	public void setUsage_instruction(String usage_instruction) {
		this.usage_instruction = usage_instruction;
	}

	public String getUsage_ins_ip_add() {
		return usage_ins_ip_add;
	}

	public void setUsage_ins_ip_add(String usage_ins_ip_add) {
		this.usage_ins_ip_add = usage_ins_ip_add;
	}

	public String getUsage_mod_ip_add() {
		return usage_mod_ip_add;
	}

	public void setUsage_mod_ip_add(String usage_mod_ip_add) {
		this.usage_mod_ip_add = usage_mod_ip_add;
	}

	public Long getUsage_prod_id() {
		return usage_prod_id;
	}

	public void setUsage_prod_id(Long usage_prod_id) {
		this.usage_prod_id = usage_prod_id;
	}

	public Date getUsage_ins_dt() {
		return usage_ins_dt;
	}

	public void setUsage_ins_dt(Date usage_ins_dt) {
		this.usage_ins_dt = usage_ins_dt;
	}

	public Date getUsage_mod_dt() {
		return usage_mod_dt;
	}

	public void setUsage_mod_dt(Date usage_mod_dt) {
		this.usage_mod_dt = usage_mod_dt;
	}
	
	
	
	
	
	
}
