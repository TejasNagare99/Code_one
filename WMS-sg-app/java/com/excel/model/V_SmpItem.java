package com.excel.model;

import javax.persistence.*;

@Entity
@Table(name="v_SMPITEM")
public class V_SmpItem {
	@Id
	@Column(name="SMP_PROD_ID")
	private Long smp_prod_id;
	
	@Column(name="SMP_PROD_NAME")
	private String smp_prod_name;
	
	@Column(name="DIV_DISP_NM")
	private String div_disp_nm;
	
	@Column(name="SA_GROUP_NAME")
	private String sa_group_name;
	
	@Column(name="SMP_PROD_CD")
	private String smp_prod_cd;
	
	@Column(name="SMP_SAMPLING_TYPE")
	private String sampling_type;
	
	@Column(name="SMP_PROD_TYPE")
	private String smp_prod_type;
	
	@Column(name="SubComp_Nm")
	private String subcomp_nm;
	
	@Column(name="SMP_status")
	private String smp_status;

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

	public String getDiv_disp_nm() {
		return div_disp_nm;
	}

	public void setDiv_disp_nm(String div_disp_nm) {
		this.div_disp_nm = div_disp_nm;
	}

	public String getSa_group_name() {
		return sa_group_name;
	}

	public void setSa_group_name(String sa_group_name) {
		this.sa_group_name = sa_group_name;
	}

	public String getSmp_prod_cd() {
		return smp_prod_cd;
	}

	public void setSmp_prod_cd(String smp_prod_cd) {
		this.smp_prod_cd = smp_prod_cd;
	}

	public String getSampling_type() {
		return sampling_type;
	}

	public void setSampling_type(String sampling_type) {
		this.sampling_type = sampling_type;
	}

	public String getSmp_prod_type() {
		return smp_prod_type;
	}

	public void setSmp_prod_type(String smp_prod_type) {
		this.smp_prod_type = smp_prod_type;
	}

	public String getSubcomp_nm() {
		return subcomp_nm;
	}

	public void setSubcomp_nm(String subcomp_nm) {
		this.subcomp_nm = subcomp_nm;
	}

	public String getSmp_status() {
		return smp_status;
	}

	public void setSmp_status(String smp_status) {
		this.smp_status = smp_status;
	}
	
	
}
