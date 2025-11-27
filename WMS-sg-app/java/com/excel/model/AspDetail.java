package com.excel.model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.DynamicUpdate;

@Entity
@Table(name="ASP_DETAIL")
@DynamicUpdate(value=true)
public class AspDetail  implements Serializable{	
	private static final long serialVersionUID = 1L;
	
	
	@Column(name="ASP_ID")
	private Long asp_id;
	
	
	@Id
	@Column(name="ASP_DTL_ID")
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long asp_dtl_id;
	
	@Column(name="ASP_DTL_FIN_YEAR")
	private String asp_dtl_fin_year;
	
	@Column(name="ASP_DIV_ID")
	private Long asp_div_id;
	
	@Column(name="ASP_TEAM_SIZE")
	private Long asp_team_size;
	
	@Column(name="ASP_PROD_ID")
	private Long asp_prod_id;
	
	@Column(name="ASP_COG")
	private BigDecimal asp_cog;
	
	@Column(name="ASP_PRDQTY01")
	private BigDecimal asp_prdqty01;
	
	@Column(name="ASP_PRDQTY02")
	private BigDecimal asp_prdqty02;
	
	@Column(name="ASP_PRDQTY03")
	private BigDecimal asp_prdqty03;
	
	@Column(name="ASP_PRDQTY04")
	private BigDecimal asp_prdqty04;
	
	@Column(name="ASP_PRDQTY05")
	private BigDecimal asp_prdqty05;
	
	@Column(name="ASP_PRDQTY06")
	private BigDecimal asp_prdqty06;
	
	@Column(name="ASP_PRDQTY07")
	private BigDecimal asp_prdqty07;
	
	@Column(name="ASP_PRDQTY08")
	private BigDecimal asp_prdqty08;
	
	@Column(name="ASP_PRDQTY09")
	private BigDecimal asp_prdqty09;
	
	@Column(name="ASP_PRDQTY10")
	private BigDecimal asp_prdqty10;
	
	@Column(name="ASP_PRDQTY11")
	private BigDecimal asp_prdqty11;
	
	@Column(name="ASP_PRDQTY12")
	private BigDecimal asp_prdqty12;
	
	@Column(name="ASP_ins_usr_id")
	private String asp_ins_user_id;
	
	@Column(name="ASP_mod_usr_id")
	private String ASP_mod_user_id;
	
	@Column(name="ASP_ins_dt")
	private Date asp_ins_dt;
	
	@Column(name="ASP_mod_dt")
	private Date asp_mod_dt;
	
	@Column(name="ASP_ins_ip_add")
	private String asp_ins_ip_add;
	
	@Column(name="ASP_mod_ip_add")
	private String asp_mod_ip_add;
	
	@Column(name="ASP_status")
	private String asp_status;
	
	@Column(name="ASP_APPR_STATUS")
	private String asp_appr_status;
	
	
	@Column(name="ASP_PROD_TYPE")
	private Long asp_prod_type;
	
	
	@Column(name="ASP_BRAND_ID")
	private Long asp_brand_id;
	
	@Column(name="ASP_FREQUENCY")
	private String asp_frequency;
	
	 
	public Date getAsp_ins_dt() {
		return asp_ins_dt;
	}

	public void setAsp_ins_dt(Date asp_ins_dt) {
		this.asp_ins_dt = asp_ins_dt;
	}

	public Date getAsp_mod_dt() {
		return asp_mod_dt;
	}

	public void setAsp_mod_dt(Date asp_mod_dt) {
		this.asp_mod_dt = asp_mod_dt;
	}

	public String getAsp_ins_ip_add() {
		return asp_ins_ip_add;
	}

	public void setAsp_ins_ip_add(String asp_ins_ip_add) {
		this.asp_ins_ip_add = asp_ins_ip_add;
	}

	public String getAsp_mod_ip_add() {
		return asp_mod_ip_add;
	}

	public void setAsp_mod_ip_add(String asp_mod_ip_add) {
		this.asp_mod_ip_add = asp_mod_ip_add;
	}

	public String getAsp_status() {
		return asp_status;
	}

	public void setAsp_status(String asp_status) {
		this.asp_status = asp_status;
	}

	public String getAsp_appr_status() {
		return asp_appr_status;
	}

	public void setAsp_appr_status(String asp_appr_status) {
		this.asp_appr_status = asp_appr_status;
	}

	public Long getAsp_id() {
		return asp_id;
	}

	public void setAsp_id(Long asp_id) {
		this.asp_id = asp_id;
	}

	public Long getAsp_dtl_id() {
		return asp_dtl_id;
	}

	public void setAsp_dtl_id(Long asp_dtl_id) {
		this.asp_dtl_id = asp_dtl_id;
	}

	public String getAsp_dtl_fin_year() {
		return asp_dtl_fin_year;
	}

	public void setAsp_dtl_fin_year(String asp_dtl_fin_year) {
		this.asp_dtl_fin_year = asp_dtl_fin_year;
	}

	public Long getAsp_div_id() {
		return asp_div_id;
	}

	public void setAsp_div_id(Long asp_div_id) {
		this.asp_div_id = asp_div_id;
	}

	public Long getAsp_team_size() {
		return asp_team_size;
	}

	public void setAsp_team_size(Long asp_team_size) {
		this.asp_team_size = asp_team_size;
	}

	public Long getAsp_prod_id() {
		return asp_prod_id;
	}

	public void setAsp_prod_id(Long asp_prod_id) {
		this.asp_prod_id = asp_prod_id;
	}

	public BigDecimal getAsp_cog() {
		return asp_cog;
	}

	public void setAsp_cog(BigDecimal asp_cog) {
		this.asp_cog = asp_cog;
	}

	public BigDecimal getAsp_prdqty01() {
		return asp_prdqty01;
	}

	public void setAsp_prdqty01(BigDecimal asp_prdqty01) {
		this.asp_prdqty01 = asp_prdqty01;
	}

	public BigDecimal getAsp_prdqty02() {
		return asp_prdqty02;
	}

	public void setAsp_prdqty02(BigDecimal asp_prdqty02) {
		this.asp_prdqty02 = asp_prdqty02;
	}

	public BigDecimal getAsp_prdqty03() {
		return asp_prdqty03;
	}

	public void setAsp_prdqty03(BigDecimal asp_prdqty03) {
		this.asp_prdqty03 = asp_prdqty03;
	}

	public BigDecimal getAsp_prdqty04() {
		return asp_prdqty04;
	}

	public void setAsp_prdqty04(BigDecimal asp_prdqty04) {
		this.asp_prdqty04 = asp_prdqty04;
	}

	public BigDecimal getAsp_prdqty05() {
		return asp_prdqty05;
	}

	public void setAsp_prdqty05(BigDecimal asp_prdqty05) {
		this.asp_prdqty05 = asp_prdqty05;
	}

	public BigDecimal getAsp_prdqty06() {
		return asp_prdqty06;
	}

	public void setAsp_prdqty06(BigDecimal asp_prdqty06) {
		this.asp_prdqty06 = asp_prdqty06;
	}

	public BigDecimal getAsp_prdqty07() {
		return asp_prdqty07;
	}

	public void setAsp_prdqty07(BigDecimal asp_prdqty07) {
		this.asp_prdqty07 = asp_prdqty07;
	}

	public BigDecimal getAsp_prdqty08() {
		return asp_prdqty08;
	}

	public void setAsp_prdqty08(BigDecimal asp_prdqty08) {
		this.asp_prdqty08 = asp_prdqty08;
	}

	public BigDecimal getAsp_prdqty09() {
		return asp_prdqty09;
	}

	public void setAsp_prdqty09(BigDecimal asp_prdqty09) {
		this.asp_prdqty09 = asp_prdqty09;
	}

	public BigDecimal getAsp_prdqty10() {
		return asp_prdqty10;
	}

	public void setAsp_prdqty10(BigDecimal asp_prdqty10) {
		this.asp_prdqty10 = asp_prdqty10;
	}

	public BigDecimal getAsp_prdqty11() {
		return asp_prdqty11;
	}

	public void setAsp_prdqty11(BigDecimal asp_prdqty11) {
		this.asp_prdqty11 = asp_prdqty11;
	}

	public BigDecimal getAsp_prdqty12() {
		return asp_prdqty12;
	}

	public void setAsp_prdqty12(BigDecimal asp_prdqty12) {
		this.asp_prdqty12 = asp_prdqty12;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public String getAsp_ins_user_id() {
		return asp_ins_user_id;
	}

	public void setAsp_ins_user_id(String asp_ins_user_id) {
		this.asp_ins_user_id = asp_ins_user_id;
	}

	public String getASP_mod_user_id() {
		return ASP_mod_user_id;
	}
	public void setASP_mod_user_id(String aSP_mod_user_id) {
		ASP_mod_user_id = aSP_mod_user_id;
	}

	public AspDetail() {
		
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

	public String getAsp_frequency() {
		return asp_frequency;
	}

	public void setAsp_frequency(String asp_frequency) {
		this.asp_frequency = asp_frequency;
	}
	
}
