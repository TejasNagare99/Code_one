package com.excel.model;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedStoredProcedureQuery;
import javax.persistence.ParameterMode;
import javax.persistence.StoredProcedureParameter;
import javax.persistence.Table;

@Entity
@Table(name = "PpgNextNumberCust")
@NamedStoredProcedureQuery(name = "callPpgNextNumberCust", 
	procedureName = "PPG_NEXT_NUMBER_CUST",
	parameters = {
			@StoredProcedureParameter(name = "avField_Nm" , mode = ParameterMode.IN, type = String.class),
			@StoredProcedureParameter(name = "avTable_Nm" , mode = ParameterMode.IN, type = String.class),
			@StoredProcedureParameter(name = "avCharDigit" , mode = ParameterMode.IN, type = String.class),
			@StoredProcedureParameter(name = "avwherecondn" , mode = ParameterMode.IN, type = String.class),
			@StoredProcedureParameter(name = "avNextNumber_Out" , mode = ParameterMode.OUT, type = String.class)
	}, resultClasses = PpgNextNumberCust.class)
public class PpgNextNumberCust implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 7392865843344881582L;

	@Id
	private String grnId;

	public String getGrnId() {
		return grnId;
	}

	public void setGrnId(String grnId) {
		this.grnId = grnId;
	}
	
}
