package com.excel.bean;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

public class PushAlcToCurrMonth {
	
	@NotNull(message = "Selected month cannot be null.")
    @Pattern(regexp = "^(0[1-9]|1[0-2])$", message = "Selected month must be between 01 and 12.")
	private String selectedMonth;
	
	@NotNull(message = "Pending request numbers cannot be null.")
    @Size(min = 1, message = "Pending request numbers cannot be empty.")
	private Long[] pending_req_nos;

	public String getSelectedMonth() {
		return selectedMonth;
	}

	public void setSelectedMonth(String selectedMonth) {
		this.selectedMonth = selectedMonth;
	}

	public Long[] getPending_req_nos() {
		return pending_req_nos;
	}

	public void setPending_req_nos(Long[] pending_req_nos) {
		this.pending_req_nos = pending_req_nos;
	}

}
