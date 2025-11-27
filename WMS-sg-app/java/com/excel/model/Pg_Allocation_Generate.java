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
@Table(name = "PG_ALLOCATION_GENERATE")
@NamedStoredProcedureQuery(name = "callPgAllocationGenerate", 
	procedureName = "PG_ALLOCATION_GENERATE",
	parameters = {
			@StoredProcedureParameter(name = "comp_cd" , mode = ParameterMode.IN, type = String.class),
			@StoredProcedureParameter(name = "finyr" , mode = ParameterMode.IN, type = String.class),
			@StoredProcedureParameter(name = "period_cd" , mode = ParameterMode.IN, type = String.class),
			@StoredProcedureParameter(name = "alloc_uniq_no" , mode = ParameterMode.IN, type = String.class),
			@StoredProcedureParameter(name = "userid" , mode = ParameterMode.IN, type = String.class),
			@StoredProcedureParameter(name = "user_ip" , mode = ParameterMode.IN, type = String.class)
			
	}, resultClasses = Pg_Allocation_Generate.class)
public class Pg_Allocation_Generate {

	@Id
	@Column(name = "SLNO")
	private Long slno;

	public Long getSlno() {
		return slno;
	}

	public void setSlno(Long slno) {
		this.slno = slno;
	}
}
