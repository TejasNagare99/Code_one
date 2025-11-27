package com.excel.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedStoredProcedureQuery;
import javax.persistence.ParameterMode;
import javax.persistence.StoredProcedureParameter;
import javax.persistence.Table;

@Entity
@Table(name = "P_v_dispatch2_java")
@NamedStoredProcedureQuery(name = "callP_v_dispatch2_java", 
	procedureName = "p_v_dispatch2_java",
	parameters = {
			@StoredProcedureParameter(name = "pidsp_id" , mode = ParameterMode.IN, type = Long.class),
			@StoredProcedureParameter(name = "piloc_id" , mode = ParameterMode.IN, type = Long.class)
	}, resultClasses = P_v_dispatch2_java.class)
public class P_v_dispatch2_java {
	
//	@Id
//	@Column(name="Row")
//	private Long row;
	
	@Column(name="SMP_PROD_TYPE")
	public String smp_prod_type;

	@Column(name="SMP_SAMPLING_TYPE")
	public String smp_sampling_type;
	
	
	@Column(name="SMP_PROD_ID")
	public Long smp_prod_id;

	@Column(name="SMP_PROD_NAME")
	public String smp_prod_name;

	@Id
	@Column(name="DSPDTL_ID")
	public Long dspdtl_id;

	@Column(name="DSP_ID")
	public Long dsp_id;

	@Column(name="DSP_SUM_DSP_ID")
	public Long dsp_sum_dsp_id;

	public String getSmp_prod_type() {
		return smp_prod_type;
	}

	public void setSmp_prod_type(String smp_prod_type) {
		this.smp_prod_type = smp_prod_type;
	}

	public String getSmp_sampling_type() {
		return smp_sampling_type;
	}

	public void setSmp_sampling_type(String smp_sampling_type) {
		this.smp_sampling_type = smp_sampling_type;
	}

	public Long getSmp_prod_id() {
		return smp_prod_id;
	}

	public void setSmp_prod_id(Long smp_prod_id) {
		this.smp_prod_id = smp_prod_id;
	}

	public String getSmp_prod_name() {
		return smp_prod_name;
	}

	public void setSmp_prod_name(String smp_prod_name) {
		this.smp_prod_name = smp_prod_name;
	}

	public Long getDspdtl_id() {
		return dspdtl_id;
	}

	public void setDspdtl_id(Long dspdtl_id) {
		this.dspdtl_id = dspdtl_id;
	}

	public Long getDsp_id() {
		return dsp_id;
	}

	public void setDsp_id(Long dsp_id) {
		this.dsp_id = dsp_id;
	}

	public Long getDsp_sum_dsp_id() {
		return dsp_sum_dsp_id;
	}

	public void setDsp_sum_dsp_id(Long dsp_sum_dsp_id) {
		this.dsp_sum_dsp_id = dsp_sum_dsp_id;
	}

//	public Long getRow() {
//		return row;
//	}
//
//	public void setRow(Long row) {
//		this.row = row;
//	}
	
	
}
