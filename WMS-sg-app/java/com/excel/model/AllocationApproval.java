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
@Table(name = "Allocation_Approval")
@NamedStoredProcedureQuery(
		name="callAllocationApproval",
		procedureName = "ALLOCATION_FINAL_APPROVAL",
		resultClasses = AllocationApproval.class,
		parameters = {
				@StoredProcedureParameter(name="ALCTMP_HDRID", mode = ParameterMode.IN, type = Long.class),
				@StoredProcedureParameter(name="userid", mode = ParameterMode.IN, type = String.class),
				@StoredProcedureParameter(name="user_ip", mode = ParameterMode.IN, type = String.class)
		}
)
public class AllocationApproval {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "ALLOC_TRG_DTL_ID")
	private Long alloc_trg_dtl_id;
	
}
