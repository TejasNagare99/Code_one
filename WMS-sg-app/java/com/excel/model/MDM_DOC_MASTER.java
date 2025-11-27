package com.excel.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="DOCTOR_MASTER_MDM")
public class MDM_DOC_MASTER {
	
	@Id
	@Column(name="SLNO")
	private Long slno;

	@Column(name="COMPANY")
	private String company;

	@Column(name="COUNTRY_CD")
	private String country_cd;

	@Column(name="SOURCE")
	private String source;

	@Column(name="HCP_UNIQUE_ID")
	private String hcp_unique_id;

	@Column(name="CDP_ID")
	private String cdp_id;

	@Column(name="FSTAFF_ID")
	private Long fstaff_id;

	@Column(name="MCL_NO")
	private String mcl_no;

	@Column(name="DOC_PREFIX")
	private String doc_prefix;

	@Column(name="DOC_TYPE")
	private String doc_type;

	@Column(name="DOC_NAME")
	private String doc_name;

	@Column(name="DOC_FNAME")
	private String doc_fname;

	@Column(name="DOC_MNAME")
	private String doc_mname;

	@Column(name="DOC_LNAME")
	private String doc_lname;

	@Column(name="DOC_ADDRESS1")
	private String doc_address1;

	@Column(name="DOC_ADDRESS2")
	private String doc_address2;

	@Column(name="DOC_ADDRESS3")
	private String doc_address3;

	@Column(name="DOC_ADDRESS4")
	private String doc_address4;

	@Column(name="DOC_CITY")
	private String doc_city;

	@Column(name="DOC_PINCODE")
	private String doc_pincode;

	@Column(name="DOC_PHONE")
	private String doc_phone;

	@Column(name="DOC_EMAIL")
	private String doc_email;

	@Column(name="DOC_CLASS")
	private String doc_class;

	@Column(name="DOC_SPECIALITY")
	private String doc_speciality;

	@Column(name="DOC_INS_USR_ID")
	private String doc_ins_usr_id;

	@Column(name="DOC_MOD_USR_ID")
	private String doc_mod_usr_id;

	@Column(name="DOC_INS_DT")
	private Date doc_ins_dt;

	@Column(name="DOC_MOD_DT")
	private Date doc_mod_dt;

	@Column(name="DOC_INS_IP_ADD")
	private String doc_ins_ip_add;

	@Column(name="DOC_MOD_IP_ADD")
	private String doc_mod_ip_add;

	@Column(name="DOC_STATUS")
	private String doc_status;

	@Column(name="FSTAFF_EMP_ID")
	private String fstaff_emp_id;

	@Column(name="DOC_FSTAFF_ID")
	private Long doc_fstaff_id;

	@Column(name="DOC_PHONE2")
	private String doc_phone2;

	@Column(name="DOC_FULL_ADR")
	private String doc_full_adr;

	@Column(name="STATE_NAME")
	private String state_name;

	@Column(name="STATE_ID")
	private Long state_id;

	@Column(name="ACTIVE")
	private String active;

	public Long getSlno(){
	 return slno;
	} 

	public void setSlno(Long slno){
	 this.slno = slno;
	}

	public String getCompany(){
	 return company;
	} 

	public void setCompany(String company){
	 this.company = company;
	}

	public String getCountry_cd(){
	 return country_cd;
	} 

	public void setCountry_cd(String country_cd){
	 this.country_cd = country_cd;
	}

	public String getSource(){
	 return source;
	} 

	public void setSource(String source){
	 this.source = source;
	}

	public String getHcp_unique_id(){
	 return hcp_unique_id;
	} 

	public void setHcp_unique_id(String hcp_unique_id){
	 this.hcp_unique_id = hcp_unique_id;
	}

	public String getCdp_id(){
	 return cdp_id;
	} 

	public void setCdp_id(String cdp_id){
	 this.cdp_id = cdp_id;
	}

	public Long getFstaff_id(){
	 return fstaff_id;
	} 

	public void setFstaff_id(Long fstaff_id){
	 this.fstaff_id = fstaff_id;
	}

	public String getMcl_no(){
	 return mcl_no;
	} 

	public void setMcl_no(String mcl_no){
	 this.mcl_no = mcl_no;
	}

	public String getDoc_prefix(){
	 return doc_prefix;
	} 

	public void setDoc_prefix(String doc_prefix){
	 this.doc_prefix = doc_prefix;
	}

	public String getDoc_type(){
	 return doc_type;
	} 

	public void setDoc_type(String doc_type){
	 this.doc_type = doc_type;
	}

	public String getDoc_name(){
	 return doc_name;
	} 

	public void setDoc_name(String doc_name){
	 this.doc_name = doc_name;
	}

	public String getDoc_fname(){
	 return doc_fname;
	} 

	public void setDoc_fname(String doc_fname){
	 this.doc_fname = doc_fname;
	}

	public String getDoc_mname(){
	 return doc_mname;
	} 

	public void setDoc_mname(String doc_mname){
	 this.doc_mname = doc_mname;
	}

	public String getDoc_lname(){
	 return doc_lname;
	} 

	public void setDoc_lname(String doc_lname){
	 this.doc_lname = doc_lname;
	}

	public String getDoc_address1(){
	 return doc_address1;
	} 

	public void setDoc_address1(String doc_address1){
	 this.doc_address1 = doc_address1;
	}

	public String getDoc_address2(){
	 return doc_address2;
	} 

	public void setDoc_address2(String doc_address2){
	 this.doc_address2 = doc_address2;
	}

	public String getDoc_address3(){
	 return doc_address3;
	} 

	public void setDoc_address3(String doc_address3){
	 this.doc_address3 = doc_address3;
	}

	public String getDoc_address4(){
	 return doc_address4;
	} 

	public void setDoc_address4(String doc_address4){
	 this.doc_address4 = doc_address4;
	}

	public String getDoc_city(){
	 return doc_city;
	} 

	public void setDoc_city(String doc_city){
	 this.doc_city = doc_city;
	}

	public String getDoc_pincode(){
	 return doc_pincode;
	} 

	public void setDoc_pincode(String doc_pincode){
	 this.doc_pincode = doc_pincode;
	}

	public String getDoc_phone(){
	 return doc_phone;
	} 

	public void setDoc_phone(String doc_phone){
	 this.doc_phone = doc_phone;
	}

	public String getDoc_email(){
	 return doc_email;
	} 

	public void setDoc_email(String doc_email){
	 this.doc_email = doc_email;
	}

	public String getDoc_class(){
	 return doc_class;
	} 

	public void setDoc_class(String doc_class){
	 this.doc_class = doc_class;
	}

	public String getDoc_speciality(){
	 return doc_speciality;
	} 

	public void setDoc_speciality(String doc_speciality){
	 this.doc_speciality = doc_speciality;
	}

	public String getDoc_ins_usr_id(){
	 return doc_ins_usr_id;
	} 

	public void setDoc_ins_usr_id(String doc_ins_usr_id){
	 this.doc_ins_usr_id = doc_ins_usr_id;
	}

	public String getDoc_mod_usr_id(){
	 return doc_mod_usr_id;
	} 

	public void setDoc_mod_usr_id(String doc_mod_usr_id){
	 this.doc_mod_usr_id = doc_mod_usr_id;
	}

	public Date getDoc_ins_dt(){
	 return doc_ins_dt;
	} 

	public void setDoc_ins_dt(Date doc_ins_dt){
	 this.doc_ins_dt = doc_ins_dt;
	}

	public Date getDoc_mod_dt(){
	 return doc_mod_dt;
	} 

	public void setDoc_mod_dt(Date doc_mod_dt){
	 this.doc_mod_dt = doc_mod_dt;
	}

	public String getDoc_ins_ip_add(){
	 return doc_ins_ip_add;
	} 

	public void setDoc_ins_ip_add(String doc_ins_ip_add){
	 this.doc_ins_ip_add = doc_ins_ip_add;
	}

	public String getDoc_mod_ip_add(){
	 return doc_mod_ip_add;
	} 

	public void setDoc_mod_ip_add(String doc_mod_ip_add){
	 this.doc_mod_ip_add = doc_mod_ip_add;
	}

	public String getDoc_status(){
	 return doc_status;
	} 

	public void setDoc_status(String doc_status){
	 this.doc_status = doc_status;
	}

	public String getFstaff_emp_id(){
	 return fstaff_emp_id;
	} 

	public void setFstaff_emp_id(String fstaff_emp_id){
	 this.fstaff_emp_id = fstaff_emp_id;
	}

	public Long getDoc_fstaff_id(){
	 return doc_fstaff_id;
	} 

	public void setDoc_fstaff_id(Long doc_fstaff_id){
	 this.doc_fstaff_id = doc_fstaff_id;
	}

	public String getDoc_phone2(){
	 return doc_phone2;
	} 

	public void setDoc_phone2(String doc_phone2){
	 this.doc_phone2 = doc_phone2;
	}

	public String getDoc_full_adr(){
	 return doc_full_adr;
	} 

	public void setDoc_full_adr(String doc_full_adr){
	 this.doc_full_adr = doc_full_adr;
	}

	public String getState_name(){
	 return state_name;
	} 

	public void setState_name(String state_name){
	 this.state_name = state_name;
	}

	public Long getState_id(){
	 return state_id;
	} 

	public void setState_id(Long state_id){
	 this.state_id = state_id;
	}

	public String getActive(){
	 return active;
	} 

	public void setActive(String active){
	 this.active = active;
	}
}
