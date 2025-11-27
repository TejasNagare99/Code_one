package com.excel.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedStoredProcedureQueries;
import javax.persistence.NamedStoredProcedureQuery;
import javax.persistence.ParameterMode;
import javax.persistence.StoredProcedureParameter;
import javax.persistence.Table;

@Entity
@Table(name = "AutoDispatchDropdown")
@NamedStoredProcedureQueries({
	
		@NamedStoredProcedureQuery(name = "callAutoDispatchDropDown", procedureName = "p_fill_allocation_adjustment_dropdown_alloc_type", resultClasses = AutoDispatchDropdown.class, parameters = {
				@StoredProcedureParameter(name = "piSMP_ID", mode = ParameterMode.IN, type = Long.class),
				@StoredProcedureParameter(name = "pcType", mode = ParameterMode.IN, type = String.class),
				@StoredProcedureParameter(name = "piFSTAFF_DIV_ID", mode = ParameterMode.IN, type = Long.class),
				@StoredProcedureParameter(name = "PALLOC_TYPE", mode = ParameterMode.IN, type = String.class),
				@StoredProcedureParameter(name = "pcTeam_code", mode = ParameterMode.IN, type = String.class),
				@StoredProcedureParameter(name = "piBLK_HCP_REQ_ID", mode = ParameterMode.IN, type = Integer.class) }),
		
		@NamedStoredProcedureQuery(name = "callAutoDispatchDropDownStockist", procedureName = "p_fill_allocation_adjustment_dropdown_ALLOC_TYPE_STOCKIST", resultClasses = AutoDispatchDropdown.class, parameters = {
				@StoredProcedureParameter(name = "pcType", mode = ParameterMode.IN, type = String.class),
				@StoredProcedureParameter(name = "PALLOC_TYPE", mode = ParameterMode.IN, type = String.class),
				@StoredProcedureParameter(name = "pi_BLK_HCP_REQ_ID", mode = ParameterMode.IN, type = String.class) }) })

public class AutoDispatchDropdown {

	@Id
	@Column(name = "DRP_ID")
	private Long drp_id;

	@Column(name = "DRP_NAME")
	private String drp_name;

	public Long getDrp_id() {
		return drp_id;
	}

	public void setDrp_id(Long drp_id) {
		this.drp_id = drp_id;
	}

	public String getDrp_name() {
		return drp_name;
	}

	public void setDrp_name(String drp_name) {
		this.drp_name = drp_name;
	}

}
