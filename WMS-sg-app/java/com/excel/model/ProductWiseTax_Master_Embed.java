package com.excel.model;

import java.io.Serializable;

import javax.persistence.Embeddable;

@Embeddable
public class ProductWiseTax_Master_Embed implements Serializable {

	private static final long serialVersionUID = 7948768356734548490L;


	private String  prod_code;
	
	private Long state_id;

	
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((prod_code == null) ? 0 : prod_code.hashCode());

		result = prime * result + state_id.intValue();
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		ProductWiseTax_Master_Embed other = (ProductWiseTax_Master_Embed) obj;
		if (prod_code == null) {
			if (other.prod_code != null)
				return false;
		} else if (!prod_code.equals(other.prod_code))
			return false;


		if (state_id.compareTo(other.state_id)!=0)
			return false;
		 return true;

	}
	
	
	
	public String getProd_code() {
		return prod_code;
	}


	public void setProd_code(String prod_code) {
		this.prod_code = prod_code;
	}


	public Long getState_id() {
		return state_id;
	}


	public void setState_id(Long state_id) {
		this.state_id = state_id;
	}


	
}
