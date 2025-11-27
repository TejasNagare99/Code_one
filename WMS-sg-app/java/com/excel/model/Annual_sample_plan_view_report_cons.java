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
@Table(name = "Annual_sample_plan_view_report_cons")
@NamedStoredProcedureQuery(name = "call_annual_sample_plan_view_report_cons",
procedureName = "ANNUAL_SAMPLE_PLAN_VIEW_REPORT_REVISED_INMKT_cons",
//procedureName = "ANNUAL_SAMPLE_PLAN_VIEW_REPORT_REVISED",
//procedureName = "ANNUAL_SAMPLE_PLAN_VIEW_REPORT",
	parameters= {
			@StoredProcedureParameter(name = "ASP_CONS_TYPE" ,mode = ParameterMode.IN,type=Character.class),
			@StoredProcedureParameter(name = "ASP_ID" ,mode = ParameterMode.IN,type=String.class),

	}, resultClasses = Annual_sample_plan_view_report_cons.class)
public class Annual_sample_plan_view_report_cons {
	
	@Id
	@Column(name = "ROW")
	private Long row;
	
	@Column(name ="ASP_DTL_FIN_YEAR")
	private String asp_dtl_fin_year;
	
	@Column(name ="BU")
	private String bu;
	
	@Column(name ="TEAM")
	private String team;
	
	@Column(name ="PRODUCT_CODE")
	private String product_code;
	
	
	@Column(name ="SA_GROUP_NAME")
	private String sa_group_name;
	
	
	@Column(name ="SMP_PROD_NAME")
	private String smp_prod_name;
	
	
	@Column(name ="PRE_IN_MARKET")
	private String pre_in_market;
	
	
	@Column(name ="SGPRMDET_DISP_NM")
	private String sgprmdet_disp_nm;
	
	@Column(name="COG_RATE_PERUNIT")
	private BigDecimal cog_rate_perunit;
	
	@Column(name="NO_OF_TE")
	private BigDecimal no_of_te;
	
	@Column(name="ASP_BRAND_ID")
	private Long asp_brand_id;
	
	
	@Column(name="ASP_PROD_TYPE")
	private Long asp_prod_type;
	
	@Column(name="ASP_PROD_ID")
	private Long asp_prod_id;
	
	@Column(name="DEC_19")
	private BigDecimal dec_19;
	
	@Column(name="JAN_20")
	private BigDecimal jan_20;
	
	@Column(name = "FEB_20")
	private BigDecimal feb_20;
	
	@Column(name = "MAR_20")
	private BigDecimal mar_20;
	
	@Column(name = "APR_20")
	private BigDecimal apr_20;
	
	
	@Column(name = "MAY_20")
	private BigDecimal may_20;
	
	
	@Column(name = "JUN_20")
	private BigDecimal jun_20;
	
	@Column(name = "JUL_20")
	private BigDecimal jul_20;
	
	@Column(name = "AUG_20")
	private BigDecimal aug_20;
	
	@Column(name = "SEP_20")
	private BigDecimal sep_20;
	
	@Column(name = "OCT_20")
	private BigDecimal oct_20;
	
	@Column(name = "NOV_20")
	private BigDecimal nov_20;

	public Long getRow() {
		return row;
	}

	public void setRow(Long row) {
		this.row = row;
	}

	public String getAsp_dtl_fin_year() {
		return asp_dtl_fin_year;
	}

	public void setAsp_dtl_fin_year(String asp_dtl_fin_year) {
		this.asp_dtl_fin_year = asp_dtl_fin_year;
	}

	public String getBu() {
		return bu;
	}

	public void setBu(String bu) {
		this.bu = bu;
	}

	public String getTeam() {
		return team;
	}

	public void setTeam(String team) {
		this.team = team;
	}

	public String getProduct_code() {
		return product_code;
	}

	public void setProduct_code(String product_code) {
		this.product_code = product_code;
	}

	

	public String getSa_group_name() {
		return sa_group_name;
	}

	public void setSa_group_name(String sa_group_name) {
		this.sa_group_name = sa_group_name;
	}

	public String getSmp_prod_name() {
		return smp_prod_name;
	}

	public void setSmp_prod_name(String smp_prod_name) {
		this.smp_prod_name = smp_prod_name;
	}

	public String getPre_in_market() {
		return pre_in_market;
	}

	public void setPre_in_market(String pre_in_market) {
		this.pre_in_market = pre_in_market;
	}

	public String getSgprmdet_disp_nm() {
		return sgprmdet_disp_nm;
	}

	public void setSgprmdet_disp_nm(String sgprmdet_disp_nm) {
		this.sgprmdet_disp_nm = sgprmdet_disp_nm;
	}

	public BigDecimal getCog_rate_perunit() {
		return cog_rate_perunit;
	}

	public void setCog_rate_perunit(BigDecimal cog_rate_perunit) {
		this.cog_rate_perunit = cog_rate_perunit;
	}

	public BigDecimal getNo_of_te() {
		return no_of_te;
	}

	public void setNo_of_te(BigDecimal no_of_te) {
		this.no_of_te = no_of_te;
	}

	public Long getAsp_brand_id() {
		return asp_brand_id;
	}

	public void setAsp_brand_id(Long asp_brand_id) {
		this.asp_brand_id = asp_brand_id;
	}

	public Long getAsp_prod_type() {
		return asp_prod_type;
	}

	public void setAsp_prod_type(Long asp_prod_type) {
		this.asp_prod_type = asp_prod_type;
	}

	public Long getAsp_prod_id() {
		return asp_prod_id;
	}

	public void setAsp_prod_id(Long asp_prod_id) {
		this.asp_prod_id = asp_prod_id;
	}

	public BigDecimal getDec_19() {
		return dec_19;
	}

	public void setDec_19(BigDecimal dec_19) {
		this.dec_19 = dec_19;
	}

	public BigDecimal getJan_20() {
		return jan_20;
	}

	public void setJan_20(BigDecimal jan_20) {
		this.jan_20 = jan_20;
	}

	public BigDecimal getFeb_20() {
		return feb_20;
	}

	public void setFeb_20(BigDecimal feb_20) {
		this.feb_20 = feb_20;
	}

	public BigDecimal getMar_20() {
		return mar_20;
	}

	public void setMar_20(BigDecimal mar_20) {
		this.mar_20 = mar_20;
	}

	public BigDecimal getApr_20() {
		return apr_20;
	}

	public void setApr_20(BigDecimal apr_20) {
		this.apr_20 = apr_20;
	}

	public BigDecimal getMay_20() {
		return may_20;
	}

	public void setMay_20(BigDecimal may_20) {
		this.may_20 = may_20;
	}

	public BigDecimal getJun_20() {
		return jun_20;
	}

	public void setJun_20(BigDecimal jun_20) {
		this.jun_20 = jun_20;
	}

	public BigDecimal getJul_20() {
		return jul_20;
	}

	public void setJul_20(BigDecimal jul_20) {
		this.jul_20 = jul_20;
	}

	public BigDecimal getAug_20() {
		return aug_20;
	}

	public void setAug_20(BigDecimal aug_20) {
		this.aug_20 = aug_20;
	}

	public BigDecimal getSep_20() {
		return sep_20;
	}

	public void setSep_20(BigDecimal sep_20) {
		this.sep_20 = sep_20;
	}

	public BigDecimal getOct_20() {
		return oct_20;
	}

	public void setOct_20(BigDecimal oct_20) {
		this.oct_20 = oct_20;
	}

	public BigDecimal getNov_20() {
		return nov_20;
	}

	public void setNov_20(BigDecimal nov_20) {
		this.nov_20 = nov_20;
	}
	
	
	


}
