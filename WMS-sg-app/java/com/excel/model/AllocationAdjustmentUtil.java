package com.excel.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedStoredProcedureQuery;
import javax.persistence.ParameterMode;
import javax.persistence.StoredProcedureParameter;
import javax.persistence.Table;

@Entity
@Table(name = "AllocationAdjustmentUtil")
@NamedStoredProcedureQuery(
		name="callAllocationAdjustmentUtil",
		procedureName = "p_u_adjust_allocation_util",
		resultClasses = AllocationAdjustmentUtil.class,
		parameters = {
				@StoredProcedureParameter(name="piSMP_ID", mode = ParameterMode.IN, type = Integer.class),
				@StoredProcedureParameter(name="pcType", mode = ParameterMode.IN, type = Character.class),
				@StoredProcedureParameter(name="pivalue", mode = ParameterMode.IN, type = Integer.class),
				@StoredProcedureParameter(name="piadj_val", mode = ParameterMode.IN, type = Integer.class),
				@StoredProcedureParameter(name="pvusr_id", mode = ParameterMode.IN, type = String.class),
				@StoredProcedureParameter(name="pvip_add", mode = ParameterMode.IN, type = String.class),
				@StoredProcedureParameter(name="pcfactor", mode = ParameterMode.IN, type = Character.class),
				@StoredProcedureParameter(name="piprod_id", mode = ParameterMode.IN, type = Integer.class),
				@StoredProcedureParameter(name="pvdiv_id", mode = ParameterMode.IN, type = Integer.class)
		}
)
public class AllocationAdjustmentUtil {


	@Id
	@Column(name = "Id")
	private Long id;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}
}
