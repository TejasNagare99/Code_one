package com.excel.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="DIVMAP")
public class DivMap {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="MAP_ID")
	public Long map_id;
	
	@Column(name="BASE_DIV_ID")
	public Long base_div_id;
	
	@Column(name="PROD_DIV_ID")
	public Long prod_div_id;
	
	@Column(name="CORE_IND")
	public String core_ind;
	
	@Column(name="INS_DT")
	public Date ins_dt;
	
	@Column(name="INS_IP_ADD")
	public String ins_ip_add;
	
	@Column(name="MOD_DT")
	public Date mod_dt;
	
	@Column(name="MOD_IP_ADD")
	public String mod_ip_add;

	public Long getMap_id() {
		return map_id;
	}

	public void setMap_id(Long map_id) {
		this.map_id = map_id;
	}

	public Long getBase_div_id() {
		return base_div_id;
	}

	public void setBase_div_id(Long base_div_id) {
		this.base_div_id = base_div_id;
	}

	public Long getProd_div_id() {
		return prod_div_id;
	}

	public void setProd_div_id(Long prod_div_id) {
		this.prod_div_id = prod_div_id;
	}

	public String getCore_ind() {
		return core_ind;
	}

	public void setCore_ind(String core_ind) {
		this.core_ind = core_ind;
	}

	public Date getIns_dt() {
		return ins_dt;
	}

	public void setIns_dt(Date ins_dt) {
		this.ins_dt = ins_dt;
	}

	public String getIns_ip_add() {
		return ins_ip_add;
	}

	public void setIns_ip_add(String ins_ip_add) {
		this.ins_ip_add = ins_ip_add;
	}

	public Date getMod_dt() {
		return mod_dt;
	}

	public void setMod_dt(Date mod_dt) {
		this.mod_dt = mod_dt;
	}

	public String getMod_ip_add() {
		return mod_ip_add;
	}

	public void setMod_ip_add(String mod_ip_add) {
		this.mod_ip_add = mod_ip_add;
	}

	



	public DivMap(String core_ind) {
		super();
		this.core_ind = core_ind;
	}

	public DivMap() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	
	

}
