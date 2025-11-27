package com.excel.bean;

import java.util.List;

public class EwayBillPrintData {

	private List<Long> ewb_numbers;
	private String print_type;
	public List<Long> getEwb_numbers() {
		return ewb_numbers;
	}
	public void setEwb_numbers(List<Long> ewb_numbers) {
		this.ewb_numbers = ewb_numbers;
	}
	public String getPrint_type() {
		return print_type;
	}
	public void setPrint_type(String print_type) {
		this.print_type = print_type;
	}
	
	
}
