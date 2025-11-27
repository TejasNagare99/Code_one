package com.excel.model;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedStoredProcedureQuery;
import javax.persistence.ParameterMode;
import javax.persistence.StoredProcedureParameter;
import javax.persistence.Table;

@Entity
@Table(name = "MonthList")
@NamedStoredProcedureQuery(
		name="callMonthList",
		procedureName = "P_GN_GETDATA",
		resultClasses = MonthList.class,
		parameters = {
				@StoredProcedureParameter(name="vstablename", mode = ParameterMode.IN, type = String.class),
				@StoredProcedureParameter(name="vsfieldlist", mode = ParameterMode.IN, type = String.class),
				@StoredProcedureParameter(name="vssearch", mode = ParameterMode.IN, type = String.class),
				@StoredProcedureParameter(name="vsorderby", mode = ParameterMode.IN, type = String.class)
		}
)
public class MonthList {
	@Id
	@Column(name = "PERIOD_ID")
	private Long period_id;
	
	@Column(name = "PERIOD_NAME")
	private String period_name;

	public Long getPeriod_id() {
		return period_id;
	}
	public void setPeriod_id(Long period_id) {
		this.period_id = period_id;
	}
	public String getPeriod_name() {
		return period_name;
	}
	public void setPeriod_name(String period_name) {
		this.period_name = period_name;
	}
}
