package com.excel.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.NamedNativeQueries;
import javax.persistence.NamedNativeQuery;
import javax.persistence.Table;

@Entity
@Table (name="ProductMasterAttrib") 
public class ProductMasterAttrib  {
   @Id
   @Column(name="DIV_DISP_NM")
   private String DIV_DISP_NM;
   
   @Column(name="SMP_PROD_ID")
   private String SMP_PROD_ID;
   
   @Column(name="SMP_PROD_CD")
   private String SMP_PROD_CD;
   
   @Column(name="SMP_COMPANY")
   private String SMP_COMPANY;
   
   @Column(name="SMP_PROD_NAME")
   private String SMP_PROD_NAME;
   
   @Column(name="SMP_PACK_ID")
   private String SMP_PACK_ID;
   
   @Column(name="SMP_ALTER_NAME")
   private String SMP_ALTER_NAME;
   
   @Column(name="SMP_LAUNCH_DT")
   private String SMP_LAUNCH_DT;
   
   @Column(name="SMP_DISCONT_DT")
   private String SMP_DISCONT_DT;
   
   @Column(name="SMP_INV_GRP_ID")
   private String SMP_INV_GRP_ID;
   
   @Column(name="SMP_UOM_ID")
   private String SMP_UOM_ID;
   
   @Column(name="SMP_FORM_ID")
   private String SMP_FORM_ID;
   
   @Column(name="SMP_SCH_IND")
   private String SMP_SCH_IND;
   
   @Column(name="SMP_DIV_RQ_IND")
   private String SMP_DIV_RQ_IND;
   
   @Column(name="SMP_EXPIRY_RQ_IND")
   private String SMP_EXPIRY_RQ_IND;
   
   @Column(name="SMP_BATCH_RQ_IND")
   private String SMP_BATCH_RQ_IND;
   
   @Column(name="SMP_ALLOC_MULTIPLES")
   private String SMP_ALLOC_MULTIPLES;
   
   @Column(name="SMP_ABC_IND")
   private String SMP_ABC_IND;
   
   @Column(name="SMP_STOCK_DAYS")
   private String SMP_STOCK_DAYS;
   
   @Column(name="SMP_PROD_TYPE")
   private String SMP_PROD_TYPE;
   
   @Column(name="SMP_SAMPLING_TYPE")
   private String SMP_SAMPLING_TYPE;
   
   @Column(name="SMP_SHELF_LIFE")
   private String SMP_SHELF_LIFE;
   
   @Column(name="SMP_MFG_LOC_ID")
   private String SMP_MFG_LOC_ID;
   
   @Column(name="SMP_SHORT_EXPIRY_DAYS")
   private String SMP_SHORT_EXPIRY_DAYS;
   
   @Column(name="SMP_PROD_BATCH_SIZE")
   private String SMP_PROD_BATCH_SIZE;
   
   @Column(name="SMP_ERP_PROD_CD")
   private String SMP_ERP_PROD_CD;
   
   @Column(name="SMP_SA_PROD_GROUP")
   private String SMP_SA_PROD_GROUP;
   
   @Column(name="SMP_THP_GRP_ID")
   private String SMP_THP_GRP_ID;
   
   @Column(name="SMP_THP_SGRP_ID")
   private String SMP_THP_SGRP_ID;
   
   @Column(name="SMP_MOLE_GRP_ID")
   private String SMP_MOLE_GRP_ID;
   
   @Column(name="SMP_MOLE_SGRP_ID")
   private String SMP_MOLE_SGRP_ID;
   
   @Column(name="SMP_PMT_GRP_ID")
   private String SMP_PMT_GRP_ID;
   
   @Column(name="SMP_PMT_SGRP_ID")
   private String SMP_PMT_SGRP_ID;
   
   @Column(name="SMP_QTY_SHIPPER")
   private String SMP_QTY_SHIPPER;
   
   @Column(name="SMP_QTY_BOX")
   private String SMP_QTY_BOX;
   
   @Column(name="SMP_QTY_STRIP")
   private String SMP_QTY_STRIP;
   
   @Column(name="SMP_MAX_ALLOC_QTY")
   private String SMP_MAX_ALLOC_QTY;
   
   @Column(name="SMP_MIN_ALLOC_QTY")
   private String SMP_MIN_ALLOC_QTY;
   
   @Column(name="SMP_STD_ALLOC_QTY")
   private String SMP_STD_ALLOC_QTY;
   
   @Column(name="SMP_SHIPPER_VOL")
   private String SMP_SHIPPER_VOL;
   
   @Column(name="SMP_SHIPPER_NWT")
   private String SMP_SHIPPER_NWT;
   
   @Column(name="SMP_SHIPPER_GWT")
   private String SMP_SHIPPER_GWT;
   
   @Column(name="SMP_EXCLUDE_LOC")
   private String SMP_EXCLUDE_LOC;
   
   @Column(name="SMP_EXCLUDE_PARTY")
   private String SMP_EXCLUDE_PARTY;
   
   @Column(name="SMP_SPEC_DIV_IND")
   private String SMP_SPEC_DIV_IND;
   
   @Column(name="SMP_COG_RATE")
   private String SMP_COG_RATE;
   
   @Column(name="SMP_COG_EXE_RATE")
   private String SMP_COG_EXE_RATE;
   
   @Column(name="SMP_RATE")
   private String SMP_RATE;
   
   @Column(name="SMP_COSTING_RATE")
   private String SMP_COSTING_RATE;
   
   @Column(name="SMP_MKTG_RATE")
   private String SMP_MKTG_RATE;
   
   @Column(name="SMP_NRV")
   private String SMP_NRV;
   
   @Column(name="SMP_DISPLAY_RATE")
   private String SMP_DISPLAY_RATE;
   
   @Column(name="SMP_STD_DIV_ID")
   private String SMP_STD_DIV_ID;
   
   @Column(name="SMP_ins_usr_id")
   private String SMP_ins_usr_id;
   
   @Column(name="SMP_mod_usr_id")
   private String SMP_mod_usr_id;
   
   @Column(name="SMP_ins_dt")
   private String SMP_ins_dt;
   
   @Column(name="SMP_mod_dt")
   private String SMP_mod_dt;
   
   @Column(name="SMP_ins_ip_add")
   private String SMP_ins_ip_add;
   
   @Column(name="SMP_mod_ip_add")
   private String SMP_mod_ip_add;
   
   @Column(name="SMP_status")
   private String SMP_status;
   
   @Column(name="SMP_PROD_NAME_OLD")
   private String  SMP_PROD_NAME_OLD;
   
   @Column(name="SMP_SubComp_id")
   private String SMP_SubComp_id;
   
   @Column(name="INV_GRP_ID")
   private String INV_GRP_ID;
   
   @Column(name="STORAGE_TYPE")
   private String STORAGE_TYPE;
   
   @Column(name="STORAGE_TYPE_ID")
   private String STORAGE_TYPE_ID;
   
   @Column(name="SMP_PROD_TYPE_ID")
   private String SMP_PROD_TYPE_ID;
   
   @Column(name="HSN_CODE")
   private String HSN_CODE;
   
   @Column(name="MARGIN_PERC")
   private String MARGIN_PERC;
   
   @Column(name="PROMO_EXPIRY_ACCEPT_IND")
   private String PROMO_EXPIRY_ACCEPT_IND;
   
   @Column(name="PROD_SUB_TYPE_ID")
   private String PROD_SUB_TYPE_ID;
   
   @Column(name="smp_supply_type_id")
   private String smp_supply_type_id;
   
   @Column(name="PROD_THRESHOLD")
   private String PROD_THRESHOLD;
   
   @Column(name="PROD_HIGHVALUE")
   private String PROD_HIGHVALUE;
   
   @Column(name="PROD_SENSITIVE")
   private String PROD_SENSITIVE;
public String getDIV_DISP_NM() {
	return DIV_DISP_NM;
}
public void setDIV_DISP_NM(String dIV_DISP_NM) {
	DIV_DISP_NM = dIV_DISP_NM;
}
public String getSMP_PROD_ID() {
	return SMP_PROD_ID;
}
public void setSMP_PROD_ID(String sMP_PROD_ID) {
	SMP_PROD_ID = sMP_PROD_ID;
}
public String getSMP_PROD_CD() {
	return SMP_PROD_CD;
}
public void setSMP_PROD_CD(String sMP_PROD_CD) {
	SMP_PROD_CD = sMP_PROD_CD;
}
public String getSMP_COMPANY() {
	return SMP_COMPANY;
}
public void setSMP_COMPANY(String sMP_COMPANY) {
	SMP_COMPANY = sMP_COMPANY;
}
public String getSMP_PROD_NAME() {
	return SMP_PROD_NAME;
}
public void setSMP_PROD_NAME(String sMP_PROD_NAME) {
	SMP_PROD_NAME = sMP_PROD_NAME;
}
public String getSMP_PACK_ID() {
	return SMP_PACK_ID;
}
public void setSMP_PACK_ID(String sMP_PACK_ID) {
	SMP_PACK_ID = sMP_PACK_ID;
}
public String getSMP_ALTER_NAME() {
	return SMP_ALTER_NAME;
}
public void setSMP_ALTER_NAME(String sMP_ALTER_NAME) {
	SMP_ALTER_NAME = sMP_ALTER_NAME;
}
public String getSMP_LAUNCH_DT() {
	return SMP_LAUNCH_DT;
}
public void setSMP_LAUNCH_DT(String sMP_LAUNCH_DT) {
	SMP_LAUNCH_DT = sMP_LAUNCH_DT;
}
public String getSMP_DISCONT_DT() {
	return SMP_DISCONT_DT;
}
public void setSMP_DISCONT_DT(String sMP_DISCONT_DT) {
	SMP_DISCONT_DT = sMP_DISCONT_DT;
}
public String getSMP_INV_GRP_ID() {
	return SMP_INV_GRP_ID;
}
public void setSMP_INV_GRP_ID(String sMP_INV_GRP_ID) {
	SMP_INV_GRP_ID = sMP_INV_GRP_ID;
}
public String getSMP_UOM_ID() {
	return SMP_UOM_ID;
}
public void setSMP_UOM_ID(String sMP_UOM_ID) {
	SMP_UOM_ID = sMP_UOM_ID;
}
public String getSMP_FORM_ID() {
	return SMP_FORM_ID;
}
public void setSMP_FORM_ID(String sMP_FORM_ID) {
	SMP_FORM_ID = sMP_FORM_ID;
}
public String getSMP_SCH_IND() {
	return SMP_SCH_IND;
}
public void setSMP_SCH_IND(String sMP_SCH_IND) {
	SMP_SCH_IND = sMP_SCH_IND;
}
public String getSMP_DIV_RQ_IND() {
	return SMP_DIV_RQ_IND;
}
public void setSMP_DIV_RQ_IND(String sMP_DIV_RQ_IND) {
	SMP_DIV_RQ_IND = sMP_DIV_RQ_IND;
}
public String getSMP_EXPIRY_RQ_IND() {
	return SMP_EXPIRY_RQ_IND;
}
public void setSMP_EXPIRY_RQ_IND(String sMP_EXPIRY_RQ_IND) {
	SMP_EXPIRY_RQ_IND = sMP_EXPIRY_RQ_IND;
}
public String getSMP_BATCH_RQ_IND() {
	return SMP_BATCH_RQ_IND;
}
public void setSMP_BATCH_RQ_IND(String sMP_BATCH_RQ_IND) {
	SMP_BATCH_RQ_IND = sMP_BATCH_RQ_IND;
}
public String getSMP_ALLOC_MULTIPLES() {
	return SMP_ALLOC_MULTIPLES;
}
public void setSMP_ALLOC_MULTIPLES(String sMP_ALLOC_MULTIPLES) {
	SMP_ALLOC_MULTIPLES = sMP_ALLOC_MULTIPLES;
}
public String getSMP_ABC_IND() {
	return SMP_ABC_IND;
}
public void setSMP_ABC_IND(String sMP_ABC_IND) {
	SMP_ABC_IND = sMP_ABC_IND;
}
public String getSMP_STOCK_DAYS() {
	return SMP_STOCK_DAYS;
}
public void setSMP_STOCK_DAYS(String sMP_STOCK_DAYS) {
	SMP_STOCK_DAYS = sMP_STOCK_DAYS;
}
public String getSMP_PROD_TYPE() {
	return SMP_PROD_TYPE;
}
public void setSMP_PROD_TYPE(String sMP_PROD_TYPE) {
	SMP_PROD_TYPE = sMP_PROD_TYPE;
}
public String getSMP_SAMPLING_TYPE() {
	return SMP_SAMPLING_TYPE;
}
public void setSMP_SAMPLING_TYPE(String sMP_SAMPLING_TYPE) {
	SMP_SAMPLING_TYPE = sMP_SAMPLING_TYPE;
}
public String getSMP_SHELF_LIFE() {
	return SMP_SHELF_LIFE;
}
public void setSMP_SHELF_LIFE(String sMP_SHELF_LIFE) {
	SMP_SHELF_LIFE = sMP_SHELF_LIFE;
}
public String getSMP_MFG_LOC_ID() {
	return SMP_MFG_LOC_ID;
}
public void setSMP_MFG_LOC_ID(String sMP_MFG_LOC_ID) {
	SMP_MFG_LOC_ID = sMP_MFG_LOC_ID;
}
public String getSMP_SHORT_EXPIRY_DAYS() {
	return SMP_SHORT_EXPIRY_DAYS;
}
public void setSMP_SHORT_EXPIRY_DAYS(String sMP_SHORT_EXPIRY_DAYS) {
	SMP_SHORT_EXPIRY_DAYS = sMP_SHORT_EXPIRY_DAYS;
}
public String getSMP_PROD_BATCH_SIZE() {
	return SMP_PROD_BATCH_SIZE;
}
public void setSMP_PROD_BATCH_SIZE(String sMP_PROD_BATCH_SIZE) {
	SMP_PROD_BATCH_SIZE = sMP_PROD_BATCH_SIZE;
}
public String getSMP_ERP_PROD_CD() {
	return SMP_ERP_PROD_CD;
}
public void setSMP_ERP_PROD_CD(String sMP_ERP_PROD_CD) {
	SMP_ERP_PROD_CD = sMP_ERP_PROD_CD;
}
public String getSMP_SA_PROD_GROUP() {
	return SMP_SA_PROD_GROUP;
}
public void setSMP_SA_PROD_GROUP(String sMP_SA_PROD_GROUP) {
	SMP_SA_PROD_GROUP = sMP_SA_PROD_GROUP;
}
public String getSMP_THP_GRP_ID() {
	return SMP_THP_GRP_ID;
}
public void setSMP_THP_GRP_ID(String sMP_THP_GRP_ID) {
	SMP_THP_GRP_ID = sMP_THP_GRP_ID;
}
public String getSMP_THP_SGRP_ID() {
	return SMP_THP_SGRP_ID;
}
public void setSMP_THP_SGRP_ID(String sMP_THP_SGRP_ID) {
	SMP_THP_SGRP_ID = sMP_THP_SGRP_ID;
}
public String getSMP_MOLE_GRP_ID() {
	return SMP_MOLE_GRP_ID;
}
public void setSMP_MOLE_GRP_ID(String sMP_MOLE_GRP_ID) {
	SMP_MOLE_GRP_ID = sMP_MOLE_GRP_ID;
}
public String getSMP_MOLE_SGRP_ID() {
	return SMP_MOLE_SGRP_ID;
}
public void setSMP_MOLE_SGRP_ID(String sMP_MOLE_SGRP_ID) {
	SMP_MOLE_SGRP_ID = sMP_MOLE_SGRP_ID;
}
public String getSMP_PMT_GRP_ID() {
	return SMP_PMT_GRP_ID;
}
public void setSMP_PMT_GRP_ID(String sMP_PMT_GRP_ID) {
	SMP_PMT_GRP_ID = sMP_PMT_GRP_ID;
}
public String getSMP_PMT_SGRP_ID() {
	return SMP_PMT_SGRP_ID;
}
public void setSMP_PMT_SGRP_ID(String sMP_PMT_SGRP_ID) {
	SMP_PMT_SGRP_ID = sMP_PMT_SGRP_ID;
}
public String getSMP_QTY_SHIPPER() {
	return SMP_QTY_SHIPPER;
}
public void setSMP_QTY_SHIPPER(String sMP_QTY_SHIPPER) {
	SMP_QTY_SHIPPER = sMP_QTY_SHIPPER;
}
public String getSMP_QTY_BOX() {
	return SMP_QTY_BOX;
}
public void setSMP_QTY_BOX(String sMP_QTY_BOX) {
	SMP_QTY_BOX = sMP_QTY_BOX;
}
public String getSMP_QTY_STRIP() {
	return SMP_QTY_STRIP;
}
public void setSMP_QTY_STRIP(String sMP_QTY_STRIP) {
	SMP_QTY_STRIP = sMP_QTY_STRIP;
}
public String getSMP_MAX_ALLOC_QTY() {
	return SMP_MAX_ALLOC_QTY;
}
public void setSMP_MAX_ALLOC_QTY(String sMP_MAX_ALLOC_QTY) {
	SMP_MAX_ALLOC_QTY = sMP_MAX_ALLOC_QTY;
}
public String getSMP_MIN_ALLOC_QTY() {
	return SMP_MIN_ALLOC_QTY;
}
public void setSMP_MIN_ALLOC_QTY(String sMP_MIN_ALLOC_QTY) {
	SMP_MIN_ALLOC_QTY = sMP_MIN_ALLOC_QTY;
}
public String getSMP_STD_ALLOC_QTY() {
	return SMP_STD_ALLOC_QTY;
}
public void setSMP_STD_ALLOC_QTY(String sMP_STD_ALLOC_QTY) {
	SMP_STD_ALLOC_QTY = sMP_STD_ALLOC_QTY;
}
public String getSMP_SHIPPER_VOL() {
	return SMP_SHIPPER_VOL;
}
public void setSMP_SHIPPER_VOL(String sMP_SHIPPER_VOL) {
	SMP_SHIPPER_VOL = sMP_SHIPPER_VOL;
}
public String getSMP_SHIPPER_NWT() {
	return SMP_SHIPPER_NWT;
}
public void setSMP_SHIPPER_NWT(String sMP_SHIPPER_NWT) {
	SMP_SHIPPER_NWT = sMP_SHIPPER_NWT;
}
public String getSMP_SHIPPER_GWT() {
	return SMP_SHIPPER_GWT;
}
public void setSMP_SHIPPER_GWT(String sMP_SHIPPER_GWT) {
	SMP_SHIPPER_GWT = sMP_SHIPPER_GWT;
}
public String getSMP_EXCLUDE_LOC() {
	return SMP_EXCLUDE_LOC;
}
public void setSMP_EXCLUDE_LOC(String sMP_EXCLUDE_LOC) {
	SMP_EXCLUDE_LOC = sMP_EXCLUDE_LOC;
}
public String getSMP_EXCLUDE_PARTY() {
	return SMP_EXCLUDE_PARTY;
}
public void setSMP_EXCLUDE_PARTY(String sMP_EXCLUDE_PARTY) {
	SMP_EXCLUDE_PARTY = sMP_EXCLUDE_PARTY;
}
public String getSMP_SPEC_DIV_IND() {
	return SMP_SPEC_DIV_IND;
}
public void setSMP_SPEC_DIV_IND(String sMP_SPEC_DIV_IND) {
	SMP_SPEC_DIV_IND = sMP_SPEC_DIV_IND;
}
public String getSMP_COG_RATE() {
	return SMP_COG_RATE;
}
public void setSMP_COG_RATE(String sMP_COG_RATE) {
	SMP_COG_RATE = sMP_COG_RATE;
}
public String getSMP_COG_EXE_RATE() {
	return SMP_COG_EXE_RATE;
}
public void setSMP_COG_EXE_RATE(String sMP_COG_EXE_RATE) {
	SMP_COG_EXE_RATE = sMP_COG_EXE_RATE;
}
public String getSMP_RATE() {
	return SMP_RATE;
}
public void setSMP_RATE(String sMP_RATE) {
	SMP_RATE = sMP_RATE;
}
public String getSMP_COSTING_RATE() {
	return SMP_COSTING_RATE;
}
public void setSMP_COSTING_RATE(String sMP_COSTING_RATE) {
	SMP_COSTING_RATE = sMP_COSTING_RATE;
}
public String getSMP_MKTG_RATE() {
	return SMP_MKTG_RATE;
}
public void setSMP_MKTG_RATE(String sMP_MKTG_RATE) {
	SMP_MKTG_RATE = sMP_MKTG_RATE;
}
public String getSMP_NRV() {
	return SMP_NRV;
}
public void setSMP_NRV(String sMP_NRV) {
	SMP_NRV = sMP_NRV;
}
public String getSMP_DISPLAY_RATE() {
	return SMP_DISPLAY_RATE;
}
public void setSMP_DISPLAY_RATE(String sMP_DISPLAY_RATE) {
	SMP_DISPLAY_RATE = sMP_DISPLAY_RATE;
}
public String getSMP_STD_DIV_ID() {
	return SMP_STD_DIV_ID;
}
public void setSMP_STD_DIV_ID(String sMP_STD_DIV_ID) {
	SMP_STD_DIV_ID = sMP_STD_DIV_ID;
}
public String getSMP_ins_usr_id() {
	return SMP_ins_usr_id;
}
public void setSMP_ins_usr_id(String sMP_ins_usr_id) {
	SMP_ins_usr_id = sMP_ins_usr_id;
}
public String getSMP_mod_usr_id() {
	return SMP_mod_usr_id;
}
public void setSMP_mod_usr_id(String sMP_mod_usr_id) {
	SMP_mod_usr_id = sMP_mod_usr_id;
}
public String getSMP_ins_dt() {
	return SMP_ins_dt;
}
public void setSMP_ins_dt(String sMP_ins_dt) {
	SMP_ins_dt = sMP_ins_dt;
}
public String getSMP_mod_dt() {
	return SMP_mod_dt;
}
public void setSMP_mod_dt(String sMP_mod_dt) {
	SMP_mod_dt = sMP_mod_dt;
}
public String getSMP_ins_ip_add() {
	return SMP_ins_ip_add;
}
public void setSMP_ins_ip_add(String sMP_ins_ip_add) {
	SMP_ins_ip_add = sMP_ins_ip_add;
}
public String getSMP_mod_ip_add() {
	return SMP_mod_ip_add;
}
public void setSMP_mod_ip_add(String sMP_mod_ip_add) {
	SMP_mod_ip_add = sMP_mod_ip_add;
}
public String getSMP_status() {
	return SMP_status;
}
public void setSMP_status(String sMP_status) {
	SMP_status = sMP_status;
}
public String getSMP_PROD_NAME_OLD() {
	return SMP_PROD_NAME_OLD;
}
public void setSMP_PROD_NAME_OLD(String sMP_PROD_NAME_OLD) {
	SMP_PROD_NAME_OLD = sMP_PROD_NAME_OLD;
}
public String getSMP_SubComp_id() {
	return SMP_SubComp_id;
}
public void setSMP_SubComp_id(String sMP_SubComp_id) {
	SMP_SubComp_id = sMP_SubComp_id;
}
public String getINV_GRP_ID() {
	return INV_GRP_ID;
}
public void setINV_GRP_ID(String iNV_GRP_ID) {
	INV_GRP_ID = iNV_GRP_ID;
}
public String getSTORAGE_TYPE() {
	return STORAGE_TYPE;
}
public void setSTORAGE_TYPE(String sTORAGE_TYPE) {
	STORAGE_TYPE = sTORAGE_TYPE;
}
public String getSTORAGE_TYPE_ID() {
	return STORAGE_TYPE_ID;
}
public void setSTORAGE_TYPE_ID(String sTORAGE_TYPE_ID) {
	STORAGE_TYPE_ID = sTORAGE_TYPE_ID;
}
public String getSMP_PROD_TYPE_ID() {
	return SMP_PROD_TYPE_ID;
}
public void setSMP_PROD_TYPE_ID(String sMP_PROD_TYPE_ID) {
	SMP_PROD_TYPE_ID = sMP_PROD_TYPE_ID;
}
public String getHSN_CODE() {
	return HSN_CODE;
}
public void setHSN_CODE(String hSN_CODE) {
	HSN_CODE = hSN_CODE;
}
public String getMARGIN_PERC() {
	return MARGIN_PERC;
}
public void setMARGIN_PERC(String mARGIN_PERC) {
	MARGIN_PERC = mARGIN_PERC;
}
public String getPROMO_EXPIRY_ACCEPT_IND() {
	return PROMO_EXPIRY_ACCEPT_IND;
}
public void setPROMO_EXPIRY_ACCEPT_IND(String pROMO_EXPIRY_ACCEPT_IND) {
	PROMO_EXPIRY_ACCEPT_IND = pROMO_EXPIRY_ACCEPT_IND;
}
public String getPROD_SUB_TYPE_ID() {
	return PROD_SUB_TYPE_ID;
}
public void setPROD_SUB_TYPE_ID(String pROD_SUB_TYPE_ID) {
	PROD_SUB_TYPE_ID = pROD_SUB_TYPE_ID;
}
public String getSmp_supply_type_id() {
	return smp_supply_type_id;
}
public void setSmp_supply_type_id(String smp_supply_type_id) {
	this.smp_supply_type_id = smp_supply_type_id;
}
public String getPROD_THRESHOLD() {
	return PROD_THRESHOLD;
}
public void setPROD_THRESHOLD(String pROD_THRESHOLD) {
	PROD_THRESHOLD = pROD_THRESHOLD;
}
public String getPROD_HIGHVALUE() {
	return PROD_HIGHVALUE;
}
public void setPROD_HIGHVALUE(String pROD_HIGHVALUE) {
	PROD_HIGHVALUE = pROD_HIGHVALUE;
}
public String getPROD_SENSITIVE() {
	return PROD_SENSITIVE;
}
public void setPROD_SENSITIVE(String pROD_SENSITIVE) {
	PROD_SENSITIVE = pROD_SENSITIVE;
}
   
   

	
}
