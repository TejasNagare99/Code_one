package com.excel.model;

import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedStoredProcedureQuery;
import javax.persistence.ParameterMode;
import javax.persistence.StoredProcedureParameter;
import javax.persistence.Table;

@Entity
@Table(name = "GenerateDispatchDataCfaStock")
@NamedStoredProcedureQuery(
		name="callGenerateDispatchDataCfastock",
		procedureName = "p_gen_dispatch_java_cfastock",
		resultClasses = GenerateDispatchDataCfaStock.class,
		parameters = {
				@StoredProcedureParameter(name="pcDSP_COMPANY", mode = ParameterMode.IN, type = String.class),
				@StoredProcedureParameter(name="pcDSP_FIN_YEAR", mode = ParameterMode.IN, type = String.class),
				@StoredProcedureParameter(name="pcDSP_PERIOD_CODE", mode = ParameterMode.IN, type = String.class),
				@StoredProcedureParameter(name="piDSP_LOC_ID", mode = ParameterMode.IN, type = Long.class),
				@StoredProcedureParameter(name="piDSP_SMP_ID", mode = ParameterMode.IN, type = Long.class),
				@StoredProcedureParameter(name="pirlocid", mode = ParameterMode.IN, type = Long.class),
				@StoredProcedureParameter(name="piabm", mode = ParameterMode.IN, type = Long.class),
				@StoredProcedureParameter(name="pirbm", mode = ParameterMode.IN, type = Long.class),
				@StoredProcedureParameter(name="pistateid", mode = ParameterMode.IN, type = Long.class),
				@StoredProcedureParameter(name="pimsr", mode = ParameterMode.IN, type = Long.class),
				@StoredProcedureParameter(name="pcpendalloc", mode = ParameterMode.IN, type = String.class),
				@StoredProcedureParameter(name="pcaction", mode = ParameterMode.IN, type = String.class),
				@StoredProcedureParameter(name="pvinsusr_id", mode = ParameterMode.IN, type = String.class),
				@StoredProcedureParameter(name="pvinsip", mode = ParameterMode.IN, type = String.class),
				@StoredProcedureParameter(name="pidiv_id", mode = ParameterMode.IN, type = Long.class),
				@StoredProcedureParameter(name="pisamplerbm", mode = ParameterMode.IN, type = Long.class),
				@StoredProcedureParameter(name="pisampleabm", mode = ParameterMode.IN, type = Long.class),
				@StoredProcedureParameter(name="pcsampleflg", mode = ParameterMode.IN, type = String.class),
				@StoredProcedureParameter(name="pvchallan_msg", mode = ParameterMode.IN, type = String.class),
				@StoredProcedureParameter(name="pvchallan_PERIOD_ID", mode = ParameterMode.IN, type = Long.class),
				@StoredProcedureParameter(name="pvDSP_APPR_REQ", mode = ParameterMode.IN, type = String.class),
				@StoredProcedureParameter(name="pirzonid", mode = ParameterMode.IN, type = Long.class),
				@StoredProcedureParameter(name="Pdirect_to_pso_ind", mode = ParameterMode.IN, type = String.class),
				@StoredProcedureParameter(name="Pstock_cfa_ind", mode = ParameterMode.IN, type = String.class),
		}
)
public class GenerateDispatchDataCfaStock {

	
	@Column(name = "alloc_ruleid")
	private Long alloc_ruleid;
	
	@Column(name = "smp_prod_type")
	private String smp_prod_type;
	
	@Id
	@Column(name = "smp_prod_id")
	private Long smp_prod_id;
	
	@Column(name = "smp_prod_name")
	private String smp_prod_name;
	
	@Column(name = "rlp_releaseqty")
	private BigDecimal rlp_releaseqty;
	
	@Column(name = "stock")
	private BigDecimal stock;
	
	@Column(name = "req_stock")
	private BigDecimal req_stock;
	
	@Column(name = "excess/deficit")
	private Integer excess_deficit;
	
	@Column(name = "pack_disp_nm")
	private String pack_disp_nm;

	public Long getAlloc_ruleid() {
		return alloc_ruleid;
	}

	public void setAlloc_ruleid(Long alloc_ruleid) {
		this.alloc_ruleid = alloc_ruleid;
	}

	public String getSmp_prod_type() {
		return smp_prod_type;
	}

	public void setSmp_prod_type(String smp_prod_type) {
		this.smp_prod_type = smp_prod_type;
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

	public BigDecimal getRlp_releaseqty() {
		return rlp_releaseqty;
	}

	public void setRlp_releaseqty(BigDecimal rlp_releaseqty) {
		this.rlp_releaseqty = rlp_releaseqty;
	}

	public BigDecimal getStock() {
		return stock;
	}

	public void setStock(BigDecimal stock) {
		this.stock = stock;
	}

	public BigDecimal getReq_stock() {
		return req_stock;
	}

	public void setReq_stock(BigDecimal req_stock) {
		this.req_stock = req_stock;
	}

	public Integer getExcess_deficit() {
		return excess_deficit;
	}

	public void setExcess_deficit(Integer excess_deficit) {
		this.excess_deficit = excess_deficit;
	}

	public String getPack_disp_nm() {
		return pack_disp_nm;
	}

	public void setPack_disp_nm(String pack_disp_nm) {
		this.pack_disp_nm = pack_disp_nm;
	}
	
	

}
