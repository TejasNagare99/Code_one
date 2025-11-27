package com.excel.model;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "ALLOC_TEMP")
public class Alloc_Temp implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "ALLOC_TEMP_ID", unique = true, nullable = false)
	private Long alloctmp_id;

	@ManyToOne
	@JoinColumn(name="alloc_temp_hd_id")
	private Alloc_Temp_Header alloc_Temp_Header;
	
	@Column(name = "FIN_YEAR")
	private Long fin_year_id;

	@Column(name = "PERIOD_CODE")
	private String period_code;

	@Column(name = "COMPANY")
	private String company;

	@Column(name = "FSCODE")
	private String fscode;

	@Column(name = "FSNAME")
	private String fsname;

	@Column(name = "FSTAFF_ID")
	private Long fstaff_id;

	@Column(name = "DIV_ID")
	private Long div_id;

	@Column(name = "PROD_ID")
	private Long prod_id;

	@Column(name = "PROD_CODE")
	private String prod_code;

	@Column(name = "PROD_NAME")
	private String prod_name;

	@Column(name = "ALLOC_TYPE")
	private String alloc_type;

	@Column(name = "ALLOC_CYCLE")
	private int cycle;

	@Column(name = "ALLOC_RATE")
	private BigDecimal rate;

	@Column(name = "ALLOC_QTY")
	private BigDecimal alloc_qty;

	@Column(name = "MSR_STOCK")
	private BigDecimal msr_stock;

	@Column(name = "FINAL_ALLOCQTY")
	private BigDecimal final_allocqty;

	@Column(name = "ALT_DIV_ID")
	private Long alt_div_id;

	@Column(name = "INV_GRP")
	private Long inv_grp;

	@Column(name = "ins_usr_id")
	private String ins_usr_id;

	@Column(name = "ins_ip_add")
	private String ins_ip_add;

	@Column(name = "status")
	private char status;

	@Column(name = "UPLOAD_STATUS")
	private char upload_status;

	@Column(name = "UPLOAD_CYCLE")
	private Integer upload_cycle;

	@Column(name = "Alloc_gen_hd_id")
	private Long alloc_header_id;

	@Column(name = "csv_file_name")
	private String csv_file_name;
	
	@Column(name = "TEAM_CODE")
	private String team_code;

	@Column(name = "mod_usr_id")
	private String mod_usr_id;
	
	@Column(name = "mod_dt")
	private Date mod_dt;
	
	@Column(name = "mod_ip_add")
	private String mod_ip_add;

	public String getMod_usr_id() {
		return mod_usr_id;
	}

	public void setMod_usr_id(String mod_usr_id) {
		this.mod_usr_id = mod_usr_id;
	}

	public Date getMod_dt() {
		return mod_dt;
	}

	public void setMod_dt(Date mod_dt) {
		this.mod_dt = mod_dt;
	}

	public String getMod_ip_add() {
		return mod_ip_add;
	}

	public void setMod_ip_add(String mod_ip_add) {
		this.mod_ip_add = mod_ip_add;
	}

	public Long getAlloctmp_id() {
		return alloctmp_id;
	}

	public void setAlloctmp_id(Long alloctmp_id) {
		this.alloctmp_id = alloctmp_id;
	}

	public Long getFin_year_id() {
		return fin_year_id;
	}

	public void setFin_year_id(Long fin_year_id) {
		this.fin_year_id = fin_year_id;
	}

	public String getPeriod_code() {
		return period_code;
	}

	public void setPeriod_code(String period_code) {
		this.period_code = period_code;
	}

	public String getCompany() {
		return company;
	}

	public void setCompany(String company) {
		this.company = company;
	}

	public String getFscode() {
		return fscode;
	}

	public void setFscode(String fscode) {
		this.fscode = fscode;
	}

	public String getFsname() {
		return fsname;
	}

	public void setFsname(String fsname) {
		this.fsname = fsname;
	}

	public Long getFstaff_id() {
		return fstaff_id;
	}

	public void setFstaff_id(Long fstaff_id) {
		this.fstaff_id = fstaff_id;
	}

	public Long getDiv_id() {
		return div_id;
	}

	public void setDiv_id(Long div_id) {
		this.div_id = div_id;
	}

	public Long getProd_id() {
		return prod_id;
	}

	public void setProd_id(Long prod_id) {
		this.prod_id = prod_id;
	}

	public String getProd_code() {
		return prod_code;
	}

	public void setProd_code(String prod_code) {
		this.prod_code = prod_code;
	}

	public String getProd_name() {
		return prod_name;
	}

	public void setProd_name(String prod_name) {
		this.prod_name = prod_name;
	}

	public String getAlloc_type() {
		return alloc_type;
	}

	public void setAlloc_type(String alloc_type) {
		this.alloc_type = alloc_type;
	}

	public int getCycle() {
		return cycle;
	}

	public void setCycle(int cycle) {
		this.cycle = cycle;
	}

	public BigDecimal getRate() {
		return rate;
	}

	public void setRate(BigDecimal rate) {
		this.rate = rate;
	}

	public BigDecimal getAlloc_qty() {
		return alloc_qty;
	}

	public void setAlloc_qty(BigDecimal alloc_qty) {
		this.alloc_qty = alloc_qty;
	}

	public BigDecimal getMsr_stock() {
		return msr_stock;
	}

	public void setMsr_stock(BigDecimal msr_stock) {
		this.msr_stock = msr_stock;
	}

	public BigDecimal getFinal_allocqty() {
		return final_allocqty;
	}

	public void setFinal_allocqty(BigDecimal final_allocqty) {
		this.final_allocqty = final_allocqty;
	}

	public Long getAlt_div_id() {
		return alt_div_id;
	}

	public void setAlt_div_id(Long alt_div_id) {
		this.alt_div_id = alt_div_id;
	}

	public Long getInv_grp() {
		return inv_grp;
	}

	public void setInv_grp(Long inv_grp) {
		this.inv_grp = inv_grp;
	}

	public String getIns_usr_id() {
		return ins_usr_id;
	}

	public void setIns_usr_id(String ins_usr_id) {
		this.ins_usr_id = ins_usr_id;
	}

	public char getStatus() {
		return status;
	}

	public void setStatus(char status) {
		this.status = status;
	}

	public void setIns_ip_add(String ins_ip_add) {
		this.ins_ip_add = ins_ip_add;
	}

	public String getIns_ip_add() {
		return ins_ip_add;
	}

	public char getUpload_status() {
		return upload_status;
	}

	public void setUpload_status(char upload_status) {
		this.upload_status = upload_status;
	}

	public Integer getUpload_cycle() {
		return upload_cycle;
	}

	public void setUpload_cycle(Integer upload_cycle) {
		this.upload_cycle = upload_cycle;
	}

	public Long getAlloc_header_id() {
		return alloc_header_id;
	}

	public void setAlloc_header_id(Long alloc_header_id) {
		this.alloc_header_id = alloc_header_id;
	}

	public String getCsv_file_name() {
		return csv_file_name;
	}

	public void setCsv_file_name(String csv_file_name) {
		this.csv_file_name = csv_file_name;
	}

	public Alloc_Temp_Header getAlloc_Temp_Header() {
		return alloc_Temp_Header;
	}

	public void setAlloc_Temp_Header(Alloc_Temp_Header alloc_Temp_Header) {
		this.alloc_Temp_Header = alloc_Temp_Header;
	}
	public Alloc_Temp() {
		
	}
	public Alloc_Temp(Integer upload_cycle) {
		super();
		this.upload_cycle=upload_cycle;
	}

	public String getTeam_code() {
		return team_code;
	}

	public void setTeam_code(String team_code) {
		this.team_code = team_code;
	}

}
