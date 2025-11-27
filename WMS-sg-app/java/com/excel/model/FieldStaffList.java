package com.excel.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedStoredProcedureQuery;
import javax.persistence.ParameterMode;
import javax.persistence.StoredProcedureParameter;
import javax.persistence.Table;

@Entity
@Table(name = "FieldStaffList")
@NamedStoredProcedureQuery(
		name="callFieldStaffList",
		procedureName = "p_v_manual_dispatch_header_java",
		resultClasses = FieldStaffList.class,
		parameters = {
				@StoredProcedureParameter(name="pvemp_id", mode = ParameterMode.IN, type = String.class),
				@StoredProcedureParameter(name="pidiv_id", mode = ParameterMode.IN, type = Long.class)
		}
)
public class FieldStaffList {
	
	@Id
	@Column(name="FSTAFF_ID")
	private Long fstaff_id;
	
	@Column(name="FSTAFF_NAME")
	private String fstaff_name;
	
	@Column(name="FSTAFF_DESTINATION")
	private String fstaff_destination;
	
	@Column(name="HQ_NAME")
	private String hq_name;

	public Long getFstaff_id() {
		return fstaff_id;
	}

	public void setFstaff_id(Long fstaff_id) {
		this.fstaff_id = fstaff_id;
	}

	public String getFstaff_name() {
		return fstaff_name;
	}

	public void setFstaff_name(String fstaff_name) {
		this.fstaff_name = fstaff_name;
	}

	public String getFstaff_destination() {
		return fstaff_destination;
	}

	public void setFstaff_destination(String fstaff_destination) {
		this.fstaff_destination = fstaff_destination;
	}

	public String getHq_name() {
		return hq_name;
	}

	public void setHq_name(String hq_name) {
		this.hq_name = hq_name;
	}
	
	
}
