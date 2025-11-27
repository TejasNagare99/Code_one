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
@Table(name="Allocation_report_3")
@NamedStoredProcedureQuery(
		name="Allocation_report_3",
	//	procedureName = "ANG_ALLOC_REPORT3_WITHPARM",
				procedureName = "ANG_ALLOC_REPORT3_WITHPARM_23JUL2020",
				parameters = {
						@StoredProcedureParameter(name = "pdiv_id", mode = ParameterMode.IN, type = String.class),
						@StoredProcedureParameter(name = "pfin_year", mode = ParameterMode.IN, type = String.class),
						@StoredProcedureParameter(name = "pperiod_code" , mode = ParameterMode.IN, type = String.class),
						@StoredProcedureParameter(name = "psub_comp_id" , mode = ParameterMode.IN, type = String.class),
						@StoredProcedureParameter(name = "pprod_sub_type_id" , mode = ParameterMode.IN, type = String.class),

				},
		resultClasses = Allocation_report_3.class
)
public class Allocation_report_3 {
	
	@Id
	@Column(name = "Row")
	private Long row;
	
	@Column(name = "BU")
	private String bu;
	
	@Column(name = "TEAM_NAME")
	private String team_name;
	
	@Column(name = "REQUEST_BY")
	private String request_by;
	
	@Column(name = "REQUEST_DATE")
	private String request_date;
	
	@Column(name = "MONTH_OF_USE")
	private String month_of_use;
	
	@Column(name = "WAREHOUSE_CODE")
	private String warehouse_code;
	
	@Column(name = "PRODUCT_NAME")
	private String product_name;
	
	@Column(name = "TE_COUNT")
	private Long te_count;
	
	@Column(name = "DM_COUNT")
	private Long dm_count;
	
	@Column(name = "RBM_COUNT")
	private Long rbm_count;
	
	@Column(name = "OTH_COUNT")
	private Long oth_count;
	
	@Column(name = "TYPE_OF_ALLOC")
	private String type_of_alloc;
	
	@Column(name = "TOT_ALLOC_QTY")
	private Long tot_alloc_qty;
	
	@Column(name = "INVENTORY")
	private Long inventory;
	
	@Column(name = "WARE_COMMENT")
	private String ware_comment;
	
	@Column(name = "CONF_FROM_MKTG")
	private String conf_from_mktg;
	
	@Column(name = "CHALLAN_NO")
	private String challan_no;
	
	@Column(name = "CHALLAN_DT")
	private String challan_dt;
	
	@Column(name = "NO_OF_CASES")
	private Long no_of_cases;
	
	@Column(name = "DISP_STATUS")
	private String disp_status;

	@Column(name = "LAST_MILE")
	private String last_mile;
	
	@Column(name="REQUEST_BY_NAME")
	private String request_by_name;
	
	@Column(name="FCODE")
	private String fcode;
	
	@Column(name="DISPATCH_QTY")
	private BigDecimal dispatch_qty;
	
	
	

	public String getRequest_by_name() {
		return request_by_name;
	}

	public void setRequest_by_name(String request_by_name) {
		this.request_by_name = request_by_name;
	}

	public String getFcode() {
		return fcode;
	}

	public void setFcode(String fcode) {
		this.fcode = fcode;
	}

	public BigDecimal getDispatch_qty() {
		return dispatch_qty;
	}

	public void setDispatch_qty(BigDecimal dispatch_qty) {
		this.dispatch_qty = dispatch_qty;
	}

	public Long getNo_of_cases() {
		return no_of_cases;
	}

	public void setNo_of_cases(Long no_of_cases) {
		this.no_of_cases = no_of_cases;
	}

	public Long getRow() {
		return row;
	}

	public void setRow(Long row) {
		this.row = row;
	}

	public String getBu() {
		return bu;
	}

	public void setBu(String bu) {
		this.bu = bu;
	}

	public String getTeam_name() {
		return team_name;
	}

	public void setTeam_name(String team_name) {
		this.team_name = team_name;
	}

	public String getRequest_by() {
		return request_by;
	}

	public void setRequest_by(String request_by) {
		this.request_by = request_by;
	}

	public String getRequest_date() {
		return request_date;
	}

	public void setRequest_date(String request_date) {
		this.request_date = request_date;
	}

	public String getMonth_of_use() {
		return month_of_use;
	}

	public void setMonth_of_use(String month_of_use) {
		this.month_of_use = month_of_use;
	}

	public String getWarehouse_code() {
		return warehouse_code;
	}

	public void setWarehouse_code(String warehouse_code) {
		this.warehouse_code = warehouse_code;
	}

	public String getProduct_name() {
		return product_name;
	}

	public void setProduct_name(String product_name) {
		this.product_name = product_name;
	}

	public Long getTe_count() {
		return te_count;
	}

	public void setTe_count(Long te_count) {
		this.te_count = te_count;
	}

	public Long getDm_count() {
		return dm_count;
	}

	public void setDm_count(Long dm_count) {
		this.dm_count = dm_count;
	}

	public Long getRbm_count() {
		return rbm_count;
	}

	public void setRbm_count(Long rbm_count) {
		this.rbm_count = rbm_count;
	}

	public Long getOth_count() {
		return oth_count;
	}

	public void setOth_count(Long oth_count) {
		this.oth_count = oth_count;
	}

	public String getType_of_alloc() {
		return type_of_alloc;
	}

	public void setType_of_alloc(String type_of_alloc) {
		this.type_of_alloc = type_of_alloc;
	}

	

	public Long getTot_alloc_qty() {
		return tot_alloc_qty;
	}

	public void setTot_alloc_qty(Long tot_alloc_qty) {
		this.tot_alloc_qty = tot_alloc_qty;
	}

	public Long getInventory() {
		return inventory;
	}

	public void setInventory(Long inventory) {
		this.inventory = inventory;
	}

	public String getWare_comment() {
		return ware_comment;
	}

	public void setWare_comment(String ware_comment) {
		this.ware_comment = ware_comment;
	}

	public String getConf_from_mktg() {
		return conf_from_mktg;
	}

	public void setConf_from_mktg(String conf_from_mktg) {
		this.conf_from_mktg = conf_from_mktg;
	}

	public String getChallan_no() {
		return challan_no;
	}

	public void setChallan_no(String challan_no) {
		this.challan_no = challan_no;
	}

	public String getChallan_dt() {
		return challan_dt;
	}

	public void setChallan_dt(String challan_dt) {
		this.challan_dt = challan_dt;
	}



	public String getDisp_status() {
		return disp_status;
	}

	public void setDisp_status(String disp_status) {
		this.disp_status = disp_status;
	}

	public String getLast_mile() {
		return last_mile;
	}

	public void setLast_mile(String last_mile) {
		this.last_mile = last_mile;
	}
	
	
	
	


}
