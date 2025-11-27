package com.excel.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedStoredProcedureQuery;
import javax.persistence.ParameterMode;
import javax.persistence.StoredProcedureParameter;
import javax.persistence.Table;

@Entity
@Table(name = "GRN_IAA")
@NamedStoredProcedureQuery(
		name="callGRN_IAA",
		procedureName = "GRN_IAA",
		resultClasses = IaaCreationInGrnApproval.class,
		parameters = {
				@StoredProcedureParameter(name="GRN_HDRID", mode = ParameterMode.IN, type = Long.class),
				@StoredProcedureParameter(name="finyr", mode = ParameterMode.IN, type = String.class),
				@StoredProcedureParameter(name="PERCD", mode = ParameterMode.IN, type = String.class),
				@StoredProcedureParameter(name="userid", mode = ParameterMode.IN, type = String.class),
				@StoredProcedureParameter(name="user_ip", mode = ParameterMode.IN, type = String.class)
		}
)
public class IaaCreationInGrnApproval {

	@Id
	private Long row_id;

	public Long getRow_id() {
		return row_id;
	}

	public void setRow_id(Long row_id) {
		this.row_id = row_id;
	}
}
