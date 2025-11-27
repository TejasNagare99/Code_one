package com.excel.model;

import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="MSR_STOCK")
public class  Msr_Stock{
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "MSR_STOCK_ID")
	private Long msrStockId;
	
	@Column(name="EMP_CODE")
	private String emp_code;
	
	@Column(name="MONTH")
	private String month;
	
	@Column(name="FIN_YEAR")
	private String year;
	
	@Column(name="HQ_POSITION")
	private String hq_position;
	
	@Column(name="TERRITORY_CODE")
	private String territory_code;
	
	@Column(name = "COMPANY")
	private String company;
	
	@Column(name = "PROD_ID")
	private Long prod_id;
	
	@Column(name="PROD_CODE")
	private String prod_code;
	
	@Column(name = "PROD_TYPE")
	private String prod_type;
	
	@Column(name = "OPSTOCK")
	private BigDecimal opstock;
	
	@Column(name = "ACKQTY")
	private BigDecimal ackqty;
	
	@Column(name = "DISBQTY")
	private BigDecimal disbqty;
	
	@Column(name = "CLSTOCK")
	private BigDecimal clstock;
	
	@Column(name = "USER_ID")
	private String user_id;
	
	@Column(name = "UPD_IP_ADD")
	private String upd_ip_add;
	
	@Column(name="UPDATE_DATE")
	private Date update_date;
	
	@Column(name="FS_ID")
	private Long fs_id;
	
	@Column(name="PERIOD_CODE")
	private String period_code; 
	
	@Column(name="EXCESS")
	private int excess;
	
	@Column(name="SHORTAGE")
	private int shortage;
	
	@Column(name="DAMAGE")
	private int damage;
	
	@Column(name="NEAR_EXPIRY")
	private int near_expiry;

	@Column(name = "STATUS")
	private char status;
	
	@Column(name="BATCH")
	private Long batch;
	
	public Long getMsrStockId() {
		return msrStockId;
	}

	public void setMsrStockId(Long msrStockId) {
		this.msrStockId = msrStockId;
	}

	public String getEmp_code() {
		return emp_code;
	}

	public void setEmp_code(String emp_code) {
		this.emp_code = emp_code;
	}

	public String getMonth() {
		return month;
	}

	public void setMonth(String month) {
		this.month = month;
	}

	public String getYear() {
		return year;
	}

	public void setYear(String year) {
		this.year = year;
	}

	public String getHq_position() {
		return hq_position;
	}

	public void setHq_position(String hq_position) {
		this.hq_position = hq_position;
	}

	public String getTerritory_code() {
		return territory_code;
	}

	public void setTerritory_code(String territory_code) {
		this.territory_code = territory_code;
	}

	public String getCompany() {
		return company;
	}

	public void setCompany(String company) {
		this.company = company;
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

	public String getProd_type() {
		return prod_type;
	}

	public void setProd_type(String prod_type) {
		this.prod_type = prod_type;
	}

	public BigDecimal getOpstock() {
		return opstock;
	}

	public void setOpstock(BigDecimal opstock) {
		this.opstock = opstock;
	}

	public BigDecimal getAckqty() {
		return ackqty;
	}

	public void setAckqty(BigDecimal ackqty) {
		this.ackqty = ackqty;
	}

	public BigDecimal getDisbqty() {
		return disbqty;
	}

	public void setDisbqty(BigDecimal disbqty) {
		this.disbqty = disbqty;
	}

	public BigDecimal getClstock() {
		return clstock;
	}

	public void setClstock(BigDecimal clstock) {
		this.clstock = clstock;
	}

	public String getUser_id() {
		return user_id;
	}

	public void setUser_id(String user_id) {
		this.user_id = user_id;
	}

	public String getUpd_ip_add() {
		return upd_ip_add;
	}

	public void setUpd_ip_add(String upd_ip_add) {
		this.upd_ip_add = upd_ip_add;
	}

	public Date getUpdate_date() {
		return update_date;
	}

	public void setUpdate_date(Date update_date) {
		this.update_date = update_date;
	}

	public Long getFs_id() {
		return fs_id;
	}

	public void setFs_id(Long fs_id) {
		this.fs_id = fs_id;
	}

	public String getPeriod_code() {
		return period_code;
	}

	public void setPeriod_code(String period_code) {
		this.period_code = period_code;
	}

	public int getExcess() {
		return excess;
	}

	public void setExcess(int excess) {
		this.excess = excess;
	}

	public int getShortage() {
		return shortage;
	}

	public void setShortage(int shortage) {
		this.shortage = shortage;
	}

	public int getDamage() {
		return damage;
	}

	public void setDamage(int damage) {
		this.damage = damage;
	}

	public int getNear_expiry() {
		return near_expiry;
	}

	public void setNear_expiry(int near_expiry) {
		this.near_expiry = near_expiry;
	}

	public char getStatus() {
		return status;
	}

	public void setStatus(char status) {
		this.status = status;
	}

	public Long getBatch() {
		return batch;
	}

	public void setBatch(Long batch) {
		this.batch = batch;
	}
		
	
}
