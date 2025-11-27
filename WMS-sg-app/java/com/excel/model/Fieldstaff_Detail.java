package com.excel.model;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.NamedStoredProcedureQuery;
import javax.persistence.StoredProcedureParameter;
import javax.persistence.ParameterMode;

import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.annotations.SelectBeforeUpdate;

@Entity
@Table(name = "fieldstaff_detail")
@DynamicUpdate(value=true)
@SelectBeforeUpdate(value=true)

@NamedStoredProcedureQuery(name = "FieldstaffMaster_Detail", procedureName = "Fieldstaff_detail_CFA_LINK",
parameters= {
		@StoredProcedureParameter(name="pFSTAFF_ID" , mode=ParameterMode.IN,type=Integer.class),
		@StoredProcedureParameter(name="pCFAPFZ" , mode=ParameterMode.IN,type=Integer.class),
		@StoredProcedureParameter(name="pCFAPIPL" , mode=ParameterMode.IN,type=Integer.class),
		@StoredProcedureParameter(name="pCFAWYTH" , mode=ParameterMode.IN,type=Integer.class)
		
		
		},resultClasses=Fieldstaff_Detail.class)

public class Fieldstaff_Detail {
		@Id
		@GeneratedValue(strategy = GenerationType.IDENTITY)
		@Column(name="FSTAFFD_ID")
		private Long fstaff_id;
		
		@Column(name="FSTAFFD_FSTAFF_ID")
		private Long fstaffd_fstaff_id;
		
		@Column(name="FSTAFFD_SubComp_id")
		private Long fstaffd_subcomp_id;
		
		@Column(name="FSTAFFD_LOC_ID")
		private Long ftaffd_loc_id;
		
		@Column(name="FSTAFFD_ins_usr_id")
		private String fstaffd_ins_usr_id;
		
		@Column(name="FSTAFFD_mod_usr_id")
		private String fstaffd_mod_usr_id;
		
		@Column(name="FSTAFFD_ins_dt")
		private Date fstaffd_ins_dt;
		
		@Column(name="FSTAFFD_mod_dt")
		private Date fstaffd_mod_dt;
		
		
		@Column(name="FSTAFFD_INS_IP_ADD")
		private String fstaffd_ins_ip_add;
		
		@Column(name="FSTAFFD_MOD_IP_ADD")
		private String fstaffd_mod_ip_add;	
		
		@Column(name="FSTAFFD_status")
		private String fstaffd_status;

		public Long getFstaff_id() {
			return fstaff_id;
		}

		public void setFstaff_id(Long fstaff_id) {
			this.fstaff_id = fstaff_id;
		}

		public Long getFstaffd_fstaff_id() {
			return fstaffd_fstaff_id;
		}

		public void setFstaffd_fstaff_id(Long fstaffd_fstaff_id) {
			this.fstaffd_fstaff_id = fstaffd_fstaff_id;
		}

		public Long getFstaffd_subcomp_id() {
			return fstaffd_subcomp_id;
		}

		public void setFstaffd_subcomp_id(Long fstaffd_subcomp_id) {
			this.fstaffd_subcomp_id = fstaffd_subcomp_id;
		}

		public Long getFtaffd_loc_id() {
			return ftaffd_loc_id;
		}

		public void setFtaffd_loc_id(Long ftaffd_loc_id) {
			this.ftaffd_loc_id = ftaffd_loc_id;
		}

		public String getFstaffd_ins_usr_id() {
			return fstaffd_ins_usr_id;
		}

		public void setFstaffd_ins_usr_id(String fstaffd_ins_usr_id) {
			this.fstaffd_ins_usr_id = fstaffd_ins_usr_id;
		}

		public String getFstaffd_mod_usr_id() {
			return fstaffd_mod_usr_id;
		}

		public void setFstaffd_mod_usr_id(String fstaffd_mod_usr_id) {
			this.fstaffd_mod_usr_id = fstaffd_mod_usr_id;
		}

		public Date getFstaffd_ins_dt() {
			return fstaffd_ins_dt;
		}

		public void setFstaffd_ins_dt(Date fstaffd_ins_dt) {
			this.fstaffd_ins_dt = fstaffd_ins_dt;
		}

		public Date getFstaffd_mod_dt() {
			return fstaffd_mod_dt;
		}

		public void setFstaffd_mod_dt(Date fstaffd_mod_dt) {
			this.fstaffd_mod_dt = fstaffd_mod_dt;
		}

		public String getFstaffd_ins_ip_add() {
			return fstaffd_ins_ip_add;
		}

		public void setFstaffd_ins_ip_add(String fstaffd_ins_ip_add) {
			this.fstaffd_ins_ip_add = fstaffd_ins_ip_add;
		}

		public String getFstaffd_mod_ip_add() {
			return fstaffd_mod_ip_add;
		}

		public void setFstaffd_mod_ip_add(String fstaffd_mod_ip_add) {
			this.fstaffd_mod_ip_add = fstaffd_mod_ip_add;
		}

		public String getFstaffd_status() {
			return fstaffd_status;
		}

		public void setFstaffd_status(String fstaffd_status) {
			this.fstaffd_status = fstaffd_status;
		}
		
}
