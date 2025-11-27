package com.excel.bean;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

public class TerritoryData {
	@NotEmpty(message = "Team Name can't be empty")
    private String teamName;
    
	@NotEmpty(message = "Territory code can't be empty")
    @Pattern(regexp = "^[a-zA-Z0-9]+$", message = "Territory code must contain only letters and numbers")
    private String territoryCode;
    
	@NotEmpty(message = "Remarks can't be empty")
    @Size(max = 50, message = "Remarks cannot be more than 50 characters")
    private String remarks;
	
	@NotEmpty(message = "Abolish fieldStaff Ind cant't be empty")
	private String abol_fieldstaff_ind; 


    public String getTeamName() {
        return teamName;
    }

    public void setTeamName(String teamName) {
        this.teamName = teamName;
    }

    public String getTerritoryCode() {
        return territoryCode;
    }

    public void setTerritoryCode(String territoryCode) {
        this.territoryCode = territoryCode;
    }

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }

	public String getAbol_fieldstaff_ind() {
		return abol_fieldstaff_ind;
	}

	public void setAbol_fieldstaff_ind(String abol_fieldstaff_ind) {
		this.abol_fieldstaff_ind = abol_fieldstaff_ind;
	}
    
}
