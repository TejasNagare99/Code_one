package com.excel.bean;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

public class RestEmpIdBean {

	@NotNull(message = "Parameter must not be null")
	@Pattern(regexp="^E\\d{5}$",message = "Invalid Data")
	private String empId;

	public String getEmpId() {
		return empId;
	}

	public void setEmpId(String empId) {
		this.empId = empId;
	}
	
	
}
