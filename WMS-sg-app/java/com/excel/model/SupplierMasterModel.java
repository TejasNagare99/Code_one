package com.excel.model;


import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedStoredProcedureQuery;
import javax.persistence.ParameterMode;
import javax.persistence.StoredProcedureParameter;
import javax.persistence.Table;

import org.hibernate.annotations.NamedNativeQueries;
import org.hibernate.annotations.NamedNativeQuery;

@Entity
@Table(name = "SupplierMasterAuditTrail")
@NamedStoredProcedureQuery(
		name="SupplierMasterModel",
		procedureName = "DOWNLOAD_SUPPLIER_AUDITTRAIL",
		resultClasses = SupplierMasterModel.class,
		parameters = {
				@StoredProcedureParameter(name="SupType", mode = ParameterMode.IN, type = String.class),
				@StoredProcedureParameter(name="startdate", mode = ParameterMode.IN, type = String.class),
				@StoredProcedureParameter(name="endDate", mode = ParameterMode.IN, type = String.class)
		}
)
public class SupplierMasterModel implements Serializable {
	
private static final long serialVersionUID = 8208024432839752958L;

	@Id
	@Column(name = "row")
	private Long row;

	@Column(name = "sup_id")
	private Long sup_id;
	
	@Column(name = "sup_type")
	private String sup_type;
	
	@Column(name = "SUPPLIER_TYPE")
	private String supplier_type;
	
	@Column(name = "SUPPLIER_NAME")
	private String supplier_name;
	
	@Column(name = "TIN_NO")
	private String tin_no;
	
	@Column(name = "CST_NO")
	private String cst_no;
	
	@Column(name = "TAN_NO")
	private String tan_no;
	
	@Column(name = "PAN_NO")
	private String pan_no;
	
	@Column(name = "MAP_CODE")
	private String map_code;
	
	@Column(name = "INSERT_USER_ID")
	private String insert_user_id;
	
	@Column(name = "INS_USER_NAME")
	private String ins_user_name;
	
	@Column(name = "INSERT_DATE")
	private String insert_date;
	
	@Column(name = "sup_INS_ip_add")
	private String sup_ins_ip_add;
	
	@Column(name = "sup_mod_usr_id")
	private String sup_mod_usr_id;
	
	@Column(name = "MOD_USER_NAME")
	private String mod_user_name;
	
	@Column(name = "MODIFY_DATE")
	private String modify_date;
	
	@Column(name = "sup_mod_ip_add")
	private String sup_mod_ip_add;
	
	@Column(name = "sup_status")
	private String sup_status;
	
	@Column(name = "sup_name_old")
	private String sup_name_old;
	
	@Column(name = "sup_SubComp_id")
	private String sup_subcomp_id;
	 
	@Column(name = "SubComp_COMPANY")
	private String subcomp_company;
	
	@Column(name = "CURR_STATUS")
	private String curr_status;
	
	@Column(name = "IND")
	private String ind;

	public Long getRow() {
		return row;
	}

	public void setRow(Long row) {
		this.row = row;
	}

	public Long getSup_id() {
		return sup_id;
	}

	public void setSup_id(Long sup_id) {
		this.sup_id = sup_id;
	}

	public String getSup_type() {
		return sup_type;
	}

	public void setSup_type(String sup_type) {
		this.sup_type = sup_type;
	}

	public String getSupplier_type() {
		return supplier_type;
	}

	public void setSupplier_type(String supplier_type) {
		this.supplier_type = supplier_type;
	}

	public String getSupplier_name() {
		return supplier_name;
	}

	public void setSupplier_name(String supplier_name) {
		this.supplier_name = supplier_name;
	}

	public String getTin_no() {
		return tin_no;
	}

	public void setTin_no(String tin_no) {
		this.tin_no = tin_no;
	}

	public String getCst_no() {
		return cst_no;
	}

	public void setCst_no(String cst_no) {
		this.cst_no = cst_no;
	}

	public String getTan_no() {
		return tan_no;
	}

	public void setTan_no(String tan_no) {
		this.tan_no = tan_no;
	}

	public String getPan_no() {
		return pan_no;
	}

	public void setPan_no(String pan_no) {
		this.pan_no = pan_no;
	}

	public String getMap_code() {
		return map_code;
	}

	public void setMap_code(String map_code) {
		this.map_code = map_code;
	}

	public String getInsert_user_id() {
		return insert_user_id;
	}

	public void setInsert_user_id(String insert_user_id) {
		this.insert_user_id = insert_user_id;
	}

	public String getIns_user_name() {
		return ins_user_name;
	}

	public void setIns_user_name(String ins_user_name) {
		this.ins_user_name = ins_user_name;
	}

	public String getInsert_date() {
		return insert_date;
	}

	public void setInsert_date(String insert_date) {
		this.insert_date = insert_date;
	}

	public String getSup_ins_ip_add() {
		return sup_ins_ip_add;
	}

	public void setSup_ins_ip_add(String sup_ins_ip_add) {
		this.sup_ins_ip_add = sup_ins_ip_add;
	}

	public String getSup_mod_usr_id() {
		return sup_mod_usr_id;
	}

	public void setSup_mod_usr_id(String sup_mod_usr_id) {
		this.sup_mod_usr_id = sup_mod_usr_id;
	}

	public String getMod_user_name() {
		return mod_user_name;
	}

	public void setMod_user_name(String mod_user_name) {
		this.mod_user_name = mod_user_name;
	}

	public String getModify_date() {
		return modify_date;
	}

	public void setModify_date(String modify_date) {
		this.modify_date = modify_date;
	}

	public String getSup_mod_ip_add() {
		return sup_mod_ip_add;
	}

	public void setSup_mod_ip_add(String sup_mod_ip_add) {
		this.sup_mod_ip_add = sup_mod_ip_add;
	}

	public String getSup_status() {
		return sup_status;
	}

	public void setSup_status(String sup_status) {
		this.sup_status = sup_status;
	}

	public String getSup_name_old() {
		return sup_name_old;
	}

	public void setSup_name_old(String sup_name_old) {
		this.sup_name_old = sup_name_old;
	}

	public String getSup_subcomp_id() {
		return sup_subcomp_id;
	}

	public void setSup_subcomp_id(String sup_subcomp_id) {
		this.sup_subcomp_id = sup_subcomp_id;
	}

	public String getSubcomp_company() {
		return subcomp_company;
	}

	public void setSubcomp_company(String subcomp_company) {
		this.subcomp_company = subcomp_company;
	}

	public String getCurr_status() {
		return curr_status;
	}

	public void setCurr_status(String curr_status) {
		this.curr_status = curr_status;
	}

	public String getInd() {
		return ind;
	}

	public void setInd(String ind) {
		this.ind = ind;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	
	
	
 

}