package com.excel.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedStoredProcedureQuery;
import javax.persistence.ParameterMode;
import javax.persistence.StoredProcedureParameter;
import javax.persistence.Table;

@NamedStoredProcedureQuery(
		name="DG_ABOLISH_TERR_CODES",
		procedureName = "DG_ABOLISH_TERR_CODES",
				parameters = {
						@StoredProcedureParameter(name="MCSVFLNAME" , mode=ParameterMode.IN,type=String.class),						
				},
		resultClasses = Dg_AbolishTerr_Codes.class
		)

@Entity
@Table(name = "Dg_AbolishTerr_Codes")
public class Dg_AbolishTerr_Codes {
	
	@Id
	@Column(name="cnt")
	private String cnt;

	public String getCnt() {
		return cnt;
	}

	public void setCnt(String cnt) {
		this.cnt = cnt;
	}
	
	

}
