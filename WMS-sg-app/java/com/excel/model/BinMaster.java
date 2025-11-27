package com.excel.model;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.DynamicUpdate;

@Entity
@Table(name="BIN_MASTER")
@DynamicUpdate(value=true)
public class BinMaster implements Serializable{

	private static final long serialVersionUID = 1L;

	@Id
	@Column(name="BIN_ID")
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long bin_id;
	
	@Column(name="BIN_COMP_ID")
	private String bin_comp_id;
	
	@Column(name="BIN_LOC_ID")
	private Long bin_loc_id;
	
	@Column(name="BIN_WAREHOUSE_CODE")
	private String bin_warehouse_code;
	
	@Column(name="BIN_STORAGE_TYPE")
	private String bin_storage_type;
	
	@Column(name="BIN_FLOOR_LEVEL")
	private String bin_floor_level;
	
	@Column(name="BIN_STORAGE_AREA")
	private String bin_storage_area;
	
	@Column(name="BIN_RACK_NO")
	private Long bin_rack_no;
	
	@Column(name="BIN_SHELF")
	private Long bin_shelf;
	
	@Column(name="BIN_BIN_NO")
	private Long bin_bin_no;
	
	@Column(name="BIN_CODE")
	private String bin_code;
	
	@Column(name="BIN_BARCODE")
	private String bin_barcode;
	
	@Column(name="BIN_DESCR")
	private String bin_descr;
	
	@Column(name="BIN_STATUS")
	private String bin_status;
	
	@Column(name="BIN_INS_USR_ID")
	private String bin_ins_usr_id;
	
	@Column(name="BIN_MOD_USR_ID")
	private String bin_mod_usr_id;
	
	@Column(name="BIN_INS_DT")
	private Date bin_ins_dt;
	
	@Column(name="BIN_MOD_DT")
	private Date bin_mod_dt;

	@Column(name="BIN_INS_IP_ADD")
	private String bin_ins_ip_add;
	
	@Column(name="BIN_MOD_IP_ADD")
	private String bin_mod_ip_add;
	
	@Column(name="BIN_IN_USE")
	private String bin_in_use;
	
	@Column(name="WAY_BILL_NO")
	private String way_bill_no;

	public Long getBin_id() {
		return bin_id;
	}

	public void setBin_id(Long bin_id) {
		this.bin_id = bin_id;
	}

	public String getBin_comp_id() {
		return bin_comp_id;
	}

	public void setBin_comp_id(String bin_comp_id) {
		this.bin_comp_id = bin_comp_id;
	}

	public Long getBin_loc_id() {
		return bin_loc_id;
	}

	public void setBin_loc_id(Long bin_loc_id) {
		this.bin_loc_id = bin_loc_id;
	}

	public String getBin_warehouse_code() {
		return bin_warehouse_code;
	}

	public void setBin_warehouse_code(String bin_warehouse_code) {
		this.bin_warehouse_code = bin_warehouse_code;
	}

	public String getBin_storage_type() {
		return bin_storage_type;
	}

	public void setBin_storage_type(String bin_storage_type) {
		this.bin_storage_type = bin_storage_type;
	}

	public String getBin_floor_level() {
		return bin_floor_level;
	}

	public void setBin_floor_level(String bin_floor_level) {
		this.bin_floor_level = bin_floor_level;
	}

	public String getBin_storage_area() {
		return bin_storage_area;
	}

	public void setBin_storage_area(String bin_storage_area) {
		this.bin_storage_area = bin_storage_area;
	}

	public Long getBin_rack_no() {
		return bin_rack_no;
	}

	public void setBin_rack_no(Long bin_rack_no) {
		this.bin_rack_no = bin_rack_no;
	}

	public Long getBin_shelf() {
		return bin_shelf;
	}

	public void setBin_shelf(Long bin_shelf) {
		this.bin_shelf = bin_shelf;
	}

	public Long getBin_bin_no() {
		return bin_bin_no;
	}

	public void setBin_bin_no(Long bin_bin_no) {
		this.bin_bin_no = bin_bin_no;
	}

	public String getBin_code() {
		return bin_code;
	}

	public void setBin_code(String bin_code) {
		this.bin_code = bin_code;
	}

	public String getBin_barcode() {
		return bin_barcode;
	}

	public void setBin_barcode(String bin_barcode) {
		this.bin_barcode = bin_barcode;
	}

	public String getBin_descr() {
		return bin_descr;
	}

	public void setBin_descr(String bin_descr) {
		this.bin_descr = bin_descr;
	}

	public String getBin_status() {
		return bin_status;
	}

	public void setBin_status(String bin_status) {
		this.bin_status = bin_status;
	}

	public String getBin_ins_usr_id() {
		return bin_ins_usr_id;
	}

	public void setBin_ins_usr_id(String bin_ins_usr_id) {
		this.bin_ins_usr_id = bin_ins_usr_id;
	}

	public String getBin_mod_usr_id() {
		return bin_mod_usr_id;
	}

	public void setBin_mod_usr_id(String bin_mod_usr_id) {
		this.bin_mod_usr_id = bin_mod_usr_id;
	}
	public Date getBin_ins_dt() {
		return bin_ins_dt;
	}

	public void setBin_ins_dt(Date bin_ins_dt) {
		this.bin_ins_dt = bin_ins_dt;
	}

	public Date getBin_mod_dt() {
		return bin_mod_dt;
	}

	public void setBin_mod_dt(Date bin_mod_dt) {
		this.bin_mod_dt = bin_mod_dt;
	}

	public String getBin_ins_ip_add() {
		return bin_ins_ip_add;
	}

	public void setBin_ins_ip_add(String bin_ins_ip_add) {
		this.bin_ins_ip_add = bin_ins_ip_add;
	}

	public String getBin_mod_ip_add() {
		return bin_mod_ip_add;
	}

	public void setBin_mod_ip_add(String bin_mod_ip_add) {
		this.bin_mod_ip_add = bin_mod_ip_add;
	}

	public String getBin_in_use() {
		return bin_in_use;
	}

	public void setBin_in_use(String bin_in_use) {
		this.bin_in_use = bin_in_use;
	}

	public String getWay_bill_no() {
		return way_bill_no;
	}

	public void setWay_bill_no(String way_bill_no) {
		this.way_bill_no = way_bill_no;
	}

	public BinMaster(Long bin_id, String bin_code) {
		super();
		this.bin_id = bin_id;
		this.bin_code = bin_code;
	}
	
}

