package com.excel.model;

import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedStoredProcedureQuery;
import javax.persistence.ParameterMode;
import javax.persistence.StoredProcedureParameter;
import javax.persistence.Table;
import javax.persistence.Transient;

@Entity
@Table(name = "Csv_File_List")
@NamedStoredProcedureQuery(
		name="callALLOC_CSV_DOWNLOAD",
		procedureName = "ALLOC_CSV_DOWNLOAD",
		resultClasses = Csv_File_List.class,
		parameters = {
				@StoredProcedureParameter(name="gen_id", mode = ParameterMode.IN, type = String.class)
		}
)
public class Csv_File_List   {
	@Id
	@Column(name = "Row")
	private long Row;
	
	@Column(name = "YEAR")
	private String year;
	
	@Column(name = "PER_CODE")
	private String per_code;
	
	@Column(name = "DIVISION")
	private String division;
	
	@Column(name = "FS_CODE")
	private String fs_code;
	
	@Column(name = "FS_NAME")
	private String fs_name;
	
	@Column(name = "SMP_PROD_ID")
	private String smp_prod_id;
	
	@Column(name = "SMP_PROD_NAME")
	private String smp_prod_name;
	
	@Column(name = "qty")
	private BigDecimal qty;
	
	@Column(name = "FSTAFF_LEVEL_CODE")
	private String fstaff_level_code;
	
	@Column(name = "ALLOC_TYPE")
	private String alloc_type;
	
	@Column(name = "SMP_MIN_ALLOC_QTY")
	private BigDecimal smp_min_alloc_qty;
	
	@Column(name = "DIV_CODE")
	private String div_code;
	
	@Column(name = "TOT_ALLOC")
	private String tot_alloc;
	
	@Column(name = "FSTAFF_ID")
	private String fstaff_id;
	
	@Column(name = "SMP_PROD_TYPE")
	private String smp_prod_type;
	
	@Column(name = "DIV_ID")
	private String div_id;
	
	@Column(name = "LEVEL_BRIEF_NAME")
	private String level_brief_name;
	
	@Column(name = "FSTAFF_TRAINING")
	private String fstaff_training;
	
	@Column(name = "FSTAFF_TERRNAME")
	private String fstaff_terrname;
	
	@Column(name = "FSTAFF_EMPLOYEE_NO")
	private String fstaff_employee_no;
	
	@Column(name = "FSTAFFD_SubComp_id")
	private BigDecimal fstaffd_subcomp_id;
	
	@Column(name = "SubComp_COMPANY")
	private String subcomp_company;
	
	@Column(name = "FSTAFFD_LOC_ID")
	private String fstaffd_loc_id;
	
	@Column(name = "DPTLOC_NAME")
	private String dptloc_name;
	
	@Column(name = "ALLOC_GEN_ID")
	private String alloc_gen_id;
	@Column(name = "SRT_NUMBER")
	private String srt_number;
	
	@Column(name = "SRT_DATE")
	private String srt_date;
	
	@Column(name = "SRT_REMARK")
	private String srt_remark;
	
	
	@Column(name = "FSTAFF_STATENAME")
	private String fstaff_statename;

	

	public String getFstaff_statename() {
		return fstaff_statename;
	}

	public void setFstaff_statename(String fstaff_statename) {
		this.fstaff_statename = fstaff_statename;
	}

	
	public String getSrt_remark() {
		return srt_remark;
	}

	public void setSrt_remark(String srt_remark) {
		this.srt_remark = srt_remark;
	}

	public String getSrt_number() {
		return srt_number;
	}

	public void setSrt_number(String srt_number) {
		this.srt_number = srt_number;
	}

	public String getSrt_date() {
		return srt_date;
	}

	public void setSrt_date(String srt_date) {
		this.srt_date = srt_date;
	}

	public long getRow() {
		return Row;
	}

	public void setRow(long row) {
		Row = row;
	}

	public String getYear() {
		return year;
	}

	public void setYear(String year) {
		this.year = year;
	}

	public String getPer_code() {
		return per_code;
	}

	public void setPer_code(String per_code) {
		this.per_code = per_code;
	}

	public String getDivision() {
		return division;
	}

	public void setDivision(String division) {
		this.division = division;
	}

	public String getFs_code() {
		return fs_code;
	}

	public void setFs_code(String fs_code) {
		this.fs_code = fs_code;
	}

	public String getFs_name() {
		return fs_name;
	}

	public void setFs_name(String fs_name) {
		this.fs_name = fs_name;
	}

	public String getSmp_prod_id() {
		return smp_prod_id;
	}

	public void setSmp_prod_id(String smp_prod_id) {
		this.smp_prod_id = smp_prod_id;
	}

	public String getSmp_prod_name() {
		return smp_prod_name;
	}

	public void setSmp_prod_name(String smp_prod_name) {
		this.smp_prod_name = smp_prod_name;
	}

	

	public BigDecimal getQty() {
		return qty;
	}

	public void setQty(BigDecimal qty) {
		this.qty = qty;
	}

	public String getFstaff_level_code() {
		return fstaff_level_code;
	}

	public void setFstaff_level_code(String fstaff_level_code) {
		this.fstaff_level_code = fstaff_level_code;
	}

	public String getAlloc_type() {
		return alloc_type;
	}

	public void setAlloc_type(String alloc_type) {
		this.alloc_type = alloc_type;
	}


	public BigDecimal getSmp_min_alloc_qty() {
		return smp_min_alloc_qty;
	}

	public void setSmp_min_alloc_qty(BigDecimal smp_min_alloc_qty) {
		this.smp_min_alloc_qty = smp_min_alloc_qty;
	}

	public String getDiv_code() {
		return div_code;
	}

	public void setDiv_code(String div_code) {
		this.div_code = div_code;
	}

	public String getTot_alloc() {
		return tot_alloc;
	}

	public void setTot_alloc(String tot_alloc) {
		this.tot_alloc = tot_alloc;
	}

	public String getFstaff_id() {
		return fstaff_id;
	}

	public void setFstaff_id(String fstaff_id) {
		this.fstaff_id = fstaff_id;
	}

	public String getSmp_prod_type() {
		return smp_prod_type;
	}

	public void setSmp_prod_type(String smp_prod_type) {
		this.smp_prod_type = smp_prod_type;
	}

	public String getDiv_id() {
		return div_id;
	}

	public void setDiv_id(String div_id) {
		this.div_id = div_id;
	}

	public String getLevel_brief_name() {
		return level_brief_name;
	}

	public void setLevel_brief_name(String level_brief_name) {
		this.level_brief_name = level_brief_name;
	}

	public String getFstaff_training() {
		return fstaff_training;
	}

	public void setFstaff_training(String fstaff_training) {
		this.fstaff_training = fstaff_training;
	}

	public String getFstaff_terrname() {
		return fstaff_terrname;
	}

	public void setFstaff_terrname(String fstaff_terrname) {
		this.fstaff_terrname = fstaff_terrname;
	}

	public String getFstaff_employee_no() {
		return fstaff_employee_no;
	}

	public void setFstaff_employee_no(String fstaff_employee_no) {
		this.fstaff_employee_no = fstaff_employee_no;
	}



	public BigDecimal getFstaffd_subcomp_id() {
		return fstaffd_subcomp_id;
	}

	public void setFstaffd_subcomp_id(BigDecimal fstaffd_subcomp_id) {
		this.fstaffd_subcomp_id = fstaffd_subcomp_id;
	}

	public String getSubcomp_company() {
		return subcomp_company;
	}

	public void setSubcomp_company(String subcomp_company) {
		this.subcomp_company = subcomp_company;
	}

	public String getFstaffd_loc_id() {
		return fstaffd_loc_id;
	}

	public void setFstaffd_loc_id(String fstaffd_loc_id) {
		this.fstaffd_loc_id = fstaffd_loc_id;
	}

	public String getDptloc_name() {
		return dptloc_name;
	}

	public void setDptloc_name(String dptloc_name) {
		this.dptloc_name = dptloc_name;
	}

	public String getAlloc_gen_id() {
		return alloc_gen_id;
	}

	public void setAlloc_gen_id(String alloc_gen_id) {
		this.alloc_gen_id = alloc_gen_id;
	}
	
	

}


