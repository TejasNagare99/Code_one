package com.excel.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedStoredProcedureQuery;
import javax.persistence.ParameterMode;
import javax.persistence.StoredProcedureParameter;
import javax.persistence.Table;

@Entity
@Table(name = "ManualDispatchItemList")
@NamedStoredProcedureQuery(
		name="callManualDispatchItemList",
		procedureName = "p_get_dispatch_item_list_MANU_DISP",
		resultClasses = ManualDispatchItemList.class,
		parameters = {
				@StoredProcedureParameter(name="piFSTAFF_ID", mode = ParameterMode.IN, type = Long.class),
				@StoredProcedureParameter(name="piSMP_ID", mode = ParameterMode.IN, type = Long.class),
				@StoredProcedureParameter(name="PALLOC_TYPE", mode = ParameterMode.IN, type = String.class)
		}
)
public class ManualDispatchItemList {
	
	@Id
	@Column(name="ALLOCDTL_PROD_ID")
	private Long allocdtl_prod_id;
	
	@Column(name="SMP_PROD_NAME")
	private String smp_prod_name;

	public Long getAllocdtl_prod_id() {
		return allocdtl_prod_id;
	}

	public void setAllocdtl_prod_id(Long allocdtl_prod_id) {
		this.allocdtl_prod_id = allocdtl_prod_id;
	}

	public String getSmp_prod_name() {
		return smp_prod_name;
	}

	public void setSmp_prod_name(String smp_prod_name) {
		this.smp_prod_name = smp_prod_name;
	}
	
	
}
