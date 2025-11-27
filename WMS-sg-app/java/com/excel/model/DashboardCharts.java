package com.excel.model;

import java.io.Serializable;
import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedStoredProcedureQuery;
import javax.persistence.ParameterMode;
import javax.persistence.StoredProcedureParameter;
import javax.persistence.Table;

@Entity
@Table(name = "DashboardCharts")
@NamedStoredProcedureQuery(name="callDBPI",procedureName = "DBOARD_GRAPH_PI_STOCK_VALUES_REVISED",resultClasses = DashboardCharts.class,
parameters = {
		@StoredProcedureParameter(name="pLoc_id", mode = ParameterMode.IN, type = Long.class),
		@StoredProcedureParameter(name="puserid", mode = ParameterMode.IN, type = String.class)
})
@NamedStoredProcedureQuery(name="callDBSample",procedureName = "DBOARD_GRAPH_SAMPLE_STOCK_VALUES_REVISED",resultClasses = DashboardCharts.class,
parameters = {
		@StoredProcedureParameter(name="pLoc_id", mode = ParameterMode.IN, type = Long.class),
		@StoredProcedureParameter(name="puserid", mode = ParameterMode.IN, type = String.class)
})
public class DashboardCharts implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@Id
	@Column(name = "ROWID")
	private Long rowid;
	
	@Column(name = "TYPE")
	private String type;
	
	@Column(name = "LABEL")
	private String label;
	
	@Column(name = "STOCK_VALUE")
	private BigDecimal stock_value;
	

	public Long getRowid() {
		return rowid;
	}

	public void setRowid(Long rowid) {
		this.rowid = rowid;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getLabel() {
		return label;
	}

	public void setLabel(String label) {
		this.label = label;
	}

	public BigDecimal getStock_value() {
		return stock_value;
	}

	public void setStock_value(BigDecimal stock_value) {
		this.stock_value = stock_value;
	}
	
	
	
	
	

}
