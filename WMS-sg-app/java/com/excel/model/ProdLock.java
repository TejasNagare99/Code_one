package com.excel.model;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.DynamicUpdate;

@Entity
@Table(name="PROD_LOCK")
@DynamicUpdate(value=true)
public class ProdLock implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -1927192382271020133L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="SLNO")
	private Long slno;
	
	@Column(name="LOC_ID")
	private Long loc_id;
	
	@Column(name="PROD_ID")
	private Long prod_id;
	
	@Column(name="USER_ID")
	private String user_id;
	
	@Column(name="LOG_TIME")
	private Date log_time;

	public Long getSlno() {
		return slno;
	}

	public void setSlno(Long slno) {
		this.slno = slno;
	}

	public Long getLoc_id() {
		return loc_id;
	}

	public void setLoc_id(Long loc_id) {
		this.loc_id = loc_id;
	}

	public Long getProd_id() {
		return prod_id;
	}

	public void setProd_id(Long prod_id) {
		this.prod_id = prod_id;
	}

	public String getUser_id() {
		return user_id;
	}

	public void setUser_id(String user_id) {
		this.user_id = user_id;
	}

	public Date getLog_time() {
		return log_time;
	}

	public void setLog_time(Date log_time) {
		this.log_time = log_time;
	}

}
