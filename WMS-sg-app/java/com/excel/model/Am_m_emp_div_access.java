package com.excel.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="am_m_emp_div_access")
public class Am_m_emp_div_access {
	@Id
	@Column(name="ediv_emp_id")
	private String ediv_emp_id;
	
	@Column(name="ediv_div_id")
	private int ediv_div_id;
	
	public String getEdiv_emp_id() {
		return ediv_emp_id;
	}
	public void setEdiv_emp_id(String ediv_emp_id) {
		this.ediv_emp_id = ediv_emp_id;
	}
	public int getEdiv_div_id() {
		return ediv_div_id;
	}
	public void setEdiv_div_id(int ediv_div_id) {
		this.ediv_div_id = ediv_div_id;
	}
		
}
