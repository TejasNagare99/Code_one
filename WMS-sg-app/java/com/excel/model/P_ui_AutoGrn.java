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


//@pcaGRN_COMPANY	char(5) = '',
//@pcaGRN_FIN_YEAR	char(4) = '',
//@pcaGRN_PERIOD_CODE	char(2) = '',
//@piaGRN_LOC_ID	int = 0,
//@pvaGRN_usr_id	varchar(10) = '',
//@pvaGRN_ip_add	varchar(15) = '',
//--added on 28-feb-11
//@pccaption char(1) = '',	--(will be either "I" for insert upload or "A" for performing save action)
//@piSLNO int = 0,				--when @pccaption = 'A' then pass "SLNO" mandatory)
//@piCSPSL_xls_id int = 0


@Entity
@Table(name="P_ui_AutoGrn")
@NamedStoredProcedureQuery(name = "p_iu_AutoGRN_JAVA", 
procedureName = "p_iu_AutoGRN_JAVA",
parameters = {
		@StoredProcedureParameter(name = "pcaGRN_COMPANY", mode = ParameterMode.IN, type = String.class),
		@StoredProcedureParameter(name = "pcaGRN_FIN_YEAR", mode = ParameterMode.IN, type = String.class),
		@StoredProcedureParameter(name = "pcaGRN_PERIOD_CODE" , mode = ParameterMode.IN, type = String.class),
		@StoredProcedureParameter(name = "piaGRN_LOC_ID" , mode = ParameterMode.IN, type = Integer.class),
		@StoredProcedureParameter(name = "pvaGRN_usr_id" , mode = ParameterMode.IN, type = String.class),
		@StoredProcedureParameter(name = "pvaGRN_ip_add" , mode = ParameterMode.IN, type = String.class),
		@StoredProcedureParameter(name = "pccaption" , mode = ParameterMode.IN, type = String.class),
		@StoredProcedureParameter(name = "piSLNO" , mode = ParameterMode.IN, type = Integer.class),
		@StoredProcedureParameter(name = "piCSPSL_xls_id" , mode = ParameterMode.IN, type = Integer.class)
}
,resultClasses = P_ui_AutoGrn.class
)


public class P_ui_AutoGrn implements Serializable{

	@Id
	@Column(name ="CSPTRF_ID")
	private long csptrf_id;
		
	@Column(name = "STAT")
	private String stat;
	
	@Column(name = "sendloc")
	private String sendloc;
	
	@Column(name = "recloc")
	private String recloc;
	
	@Column(name = "CSPTRF_TRF_DATE_DISP")
	private String csptrf_trf_date_disp;
	
	@Column(name = "CSPTRF_MFGDT_DISP")
	private String csptrf_mfgdt_disp;
	
	@Column(name = "CSPTRF_EXPIRY_DT_DISP")
	private String csptrf_expiry_dt_disp;
	


	@Column(name = "CSPTRF_SLNO ")
	private String csptrf_slno;
	
	@Column(name = "CSPTRF_SENDING_LOC")
	private String csptrf_sending_loc;

	@Column(name = "CSPTRF_RECV_LOC")
	private String csptrf_recv_loc;
	
	@Column(name = "CSPTRF_TRF_NO ")
	private String csptrf_trf_no;
	
	
	@Column(name = "CSPTRF_TRF_DATE")
	private String csptrf_trf_date;
	
	@Column(name = "CSPTRF_PROD_CD")
	private String csptrf_prod_cd;
	
	@Column(name = "CSPTRF_BATCH_NO")
	private String csptrf_batch_no;
	
	
	@Column(name = "CSPTRF_GIFTIND")
	private String csptrf_giftind;
	
	@Column(name = "CSPTRF_MFGDT")
	private String csptrf_mfgdt;
	
	@Column(name = "CSPTRF_EXPIRY_DT")
	private String csptrf_expiry_dt;
	
	@Column(name = "CSPTRF_QTY")
	private BigDecimal csptrf_qty;
	
	
	@Column(name = "CSPTRF_NOCASES")
	private long csptrf_nocases;
	
	
	@Column(name = "CSPTRF_FULLSHIPPER")
	private long csptrf_fullshipper;
	
	@Column(name = "CSPTRF_TRFRATE")
	private BigDecimal csptrf_trfrate;
	
	@Column(name = "CSPTRF_COGRATE")
	private BigDecimal csptrf_cograte;
	
	@Column(name = "CSPTRF_REF_NO")
	private String csptrf_ref_no;
	
	@Column(name = "CSPTRF_LRNO")
	private String csptrf_lrno;
	
	@Column(name = "CSPTRF_LR_DATE")
	private String csptrf_lr_date;
	
	@Column(name = "CSPTRF_MFG_MAP_CD")
	private String csptrf_mfg_map_cd;
	
	@Column(name = "CSPTRF_PONUMBER")
	private String csptrf_ponumber;
	
	@Column(name = "CSPTRF_PROCESSED")
	private String csptrf_processed;
	
	@Column(name = "CSPTRF_ERR_MSG")
	private String csptrf_err_msg;
	
	@Column(name = "CSPTRF_RMPMRATE")
	private BigDecimal csptrf_rmpmrate;
	
	@Column(name = "CSPTRF_ExciseRATE")
	private BigDecimal csptrf_exciserate;
	
	@Column(name = "CSPTRF_OverheadRATE")
	private BigDecimal csptrf_overheadrate;
	
	@Column(name = "CSPTRF_SENDLOC_CD")
	private String csptrf_sendloc_cd;
	
	@Column(name = "CSPTRF_RECVLOC_CD")
	private String csptrf_recvloc_cd;
	
	@Column(name = "CSPTRF_ins_usr_id")
	private String csptrf_ins_usr_id;
	
	@Column(name = "CSPTRF_mod_usr_id")
	private String csptrf_mod_usr_id;
	
	@Column(name = "CSPTRF_ins_dt")
	private String csptrf_ins_dt;
	
	@Column(name = "CSPTRF_mod_dt")
	private String csptrf_mod_dt;
	
	@Column(name = "CSPTRF_ins_ip_add")
	private String csptrf_ins_ip_add;
	
	@Column(name = "CSPTRF_mod_ip_add")
	private String csptrf_mod_ip_add;
	
	@Column(name = "CSPTRF_status")
	private String csptrf_status;
	
	@Column(name = "AUTH_CODE")
	private String auth_code;
	
	@Column(name = "TRANSPORTER_NAME")
	private String transporter_name;

//	public long getCsptrf_id() {
//		return csptrf_id;
//	}
//
//	public void setCsptrf_id(long csptrf_id) {
//		this.csptrf_id = csptrf_id;
//	}

	public String getStat() {
		return stat;
	}

	public void setStat(String stat) {
		this.stat = stat;
	}

	public String getSendloc() {
		return sendloc;
	}

	public void setSendloc(String sendloc) {
		this.sendloc = sendloc;
	}

	public String getRecloc() {
		return recloc;
	}

	public void setRecloc(String recloc) {
		this.recloc = recloc;
	}

	public String getCsptrf_trf_date_disp() {
		return csptrf_trf_date_disp;
	}

	public void setCsptrf_trf_date_disp(String csptrf_trf_date_disp) {
		this.csptrf_trf_date_disp = csptrf_trf_date_disp;
	}

	public String getCsptrf_mfgdt_disp() {
		return csptrf_mfgdt_disp;
	}

	public void setCsptrf_mfgdt_disp(String csptrf_mfgdt_disp) {
		this.csptrf_mfgdt_disp = csptrf_mfgdt_disp;
	}

	public String getCsptrf_expiry_dt_disp() {
		return csptrf_expiry_dt_disp;
	}

	public void setCsptrf_expiry_dt_disp(String csptrf_expiry_dt_disp) {
		this.csptrf_expiry_dt_disp = csptrf_expiry_dt_disp;
	}

	public String getCsptrf_slno() {
		return csptrf_slno;
	}

	public void setCsptrf_slno(String csptrf_slno) {
		this.csptrf_slno = csptrf_slno;
	}

	public String getCsptrf_sending_loc() {
		return csptrf_sending_loc;
	}

	public void setCsptrf_sending_loc(String csptrf_sending_loc) {
		this.csptrf_sending_loc = csptrf_sending_loc;
	}

	public String getCsptrf_recv_loc() {
		return csptrf_recv_loc;
	}

	public void setCsptrf_recv_loc(String csptrf_recv_loc) {
		this.csptrf_recv_loc = csptrf_recv_loc;
	}

	public String getCsptrf_trf_no() {
		return csptrf_trf_no;
	}

	public void setCsptrf_trf_no(String csptrf_trf_no) {
		this.csptrf_trf_no = csptrf_trf_no;
	}

	public String getCsptrf_trf_date() {
		return csptrf_trf_date;
	}

	public void setCsptrf_trf_date(String csptrf_trf_date) {
		this.csptrf_trf_date = csptrf_trf_date;
	}

	public String getCsptrf_prod_cd() {
		return csptrf_prod_cd;
	}

	public void setCsptrf_prod_cd(String csptrf_prod_cd) {
		this.csptrf_prod_cd = csptrf_prod_cd;
	}

	public String getCsptrf_batch_no() {
		return csptrf_batch_no;
	}

	public void setCsptrf_batch_no(String csptrf_batch_no) {
		this.csptrf_batch_no = csptrf_batch_no;
	}

	public String getCsptrf_giftind() {
		return csptrf_giftind;
	}

	public void setCsptrf_giftind(String csptrf_giftind) {
		this.csptrf_giftind = csptrf_giftind;
	}

	public String getCsptrf_mfgdt() {
		return csptrf_mfgdt;
	}

	public void setCsptrf_mfgdt(String csptrf_mfgdt) {
		this.csptrf_mfgdt = csptrf_mfgdt;
	}

	public String getCsptrf_expiry_dt() {
		return csptrf_expiry_dt;
	}

	public void setCsptrf_expiry_dt(String csptrf_expiry_dt) {
		this.csptrf_expiry_dt = csptrf_expiry_dt;
	}

	public BigDecimal getCsptrf_qty() {
		return csptrf_qty;
	}

	public void setCsptrf_qty(BigDecimal csptrf_qty) {
		this.csptrf_qty = csptrf_qty;
	}

	public long getCsptrf_nocases() {
		return csptrf_nocases;
	}

	public void setCsptrf_nocases(long csptrf_nocases) {
		this.csptrf_nocases = csptrf_nocases;
	}

	public long getCsptrf_fullshipper() {
		return csptrf_fullshipper;
	}

	public void setCsptrf_fullshipper(long csptrf_fullshipper) {
		this.csptrf_fullshipper = csptrf_fullshipper;
	}

	public BigDecimal getCsptrf_trfrate() {
		return csptrf_trfrate;
	}

	public void setCsptrf_trfrate(BigDecimal csptrf_trfrate) {
		this.csptrf_trfrate = csptrf_trfrate;
	}

	public BigDecimal getCsptrf_cograte() {
		return csptrf_cograte;
	}

	public void setCsptrf_cograte(BigDecimal csptrf_cograte) {
		this.csptrf_cograte = csptrf_cograte;
	}

	public String getCsptrf_ref_no() {
		return csptrf_ref_no;
	}

	public void setCsptrf_ref_no(String csptrf_ref_no) {
		this.csptrf_ref_no = csptrf_ref_no;
	}

	public String getCsptrf_lrno() {
		return csptrf_lrno;
	}

	public void setCsptrf_lrno(String csptrf_lrno) {
		this.csptrf_lrno = csptrf_lrno;
	}

	public String getCsptrf_lr_date() {
		return csptrf_lr_date;
	}

	public void setCsptrf_lr_date(String csptrf_lr_date) {
		this.csptrf_lr_date = csptrf_lr_date;
	}

	public String getCsptrf_mfg_map_cd() {
		return csptrf_mfg_map_cd;
	}

	public void setCsptrf_mfg_map_cd(String csptrf_mfg_map_cd) {
		this.csptrf_mfg_map_cd = csptrf_mfg_map_cd;
	}

	public String getCsptrf_ponumber() {
		return csptrf_ponumber;
	}

	public void setCsptrf_ponumber(String csptrf_ponumber) {
		this.csptrf_ponumber = csptrf_ponumber;
	}

	public String getCsptrf_processed() {
		return csptrf_processed;
	}

	public void setCsptrf_processed(String csptrf_processed) {
		this.csptrf_processed = csptrf_processed;
	}

	public String getCsptrf_err_msg() {
		return csptrf_err_msg;
	}

	public void setCsptrf_err_msg(String csptrf_err_msg) {
		this.csptrf_err_msg = csptrf_err_msg;
	}

	public BigDecimal getCsptrf_rmpmrate() {
		return csptrf_rmpmrate;
	}

	public void setCsptrf_rmpmrate(BigDecimal csptrf_rmpmrate) {
		this.csptrf_rmpmrate = csptrf_rmpmrate;
	}

	public BigDecimal getCsptrf_exciserate() {
		return csptrf_exciserate;
	}

	public void setCsptrf_exciserate(BigDecimal csptrf_exciserate) {
		this.csptrf_exciserate = csptrf_exciserate;
	}

	public BigDecimal getCsptrf_overheadrate() {
		return csptrf_overheadrate;
	}

	public void setCsptrf_overheadrate(BigDecimal csptrf_overheadrate) {
		this.csptrf_overheadrate = csptrf_overheadrate;
	}

	public String getCsptrf_sendloc_cd() {
		return csptrf_sendloc_cd;
	}

	public void setCsptrf_sendloc_cd(String csptrf_sendloc_cd) {
		this.csptrf_sendloc_cd = csptrf_sendloc_cd;
	}

	public String getCsptrf_recvloc_cd() {
		return csptrf_recvloc_cd;
	}

	public void setCsptrf_recvloc_cd(String csptrf_recvloc_cd) {
		this.csptrf_recvloc_cd = csptrf_recvloc_cd;
	}

	public String getCsptrf_ins_usr_id() {
		return csptrf_ins_usr_id;
	}

	public void setCsptrf_ins_usr_id(String csptrf_ins_usr_id) {
		this.csptrf_ins_usr_id = csptrf_ins_usr_id;
	}

	public String getCsptrf_mod_usr_id() {
		return csptrf_mod_usr_id;
	}

	public void setCsptrf_mod_usr_id(String csptrf_mod_usr_id) {
		this.csptrf_mod_usr_id = csptrf_mod_usr_id;
	}

	public String getCsptrf_ins_dt() {
		return csptrf_ins_dt;
	}

	public void setCsptrf_ins_dt(String csptrf_ins_dt) {
		this.csptrf_ins_dt = csptrf_ins_dt;
	}

	public String getCsptrf_mod_dt() {
		return csptrf_mod_dt;
	}

	public void setCsptrf_mod_dt(String csptrf_mod_dt) {
		this.csptrf_mod_dt = csptrf_mod_dt;
	}

	public String getCsptrf_ins_ip_add() {
		return csptrf_ins_ip_add;
	}

	public void setCsptrf_ins_ip_add(String csptrf_ins_ip_add) {
		this.csptrf_ins_ip_add = csptrf_ins_ip_add;
	}

	public String getCsptrf_mod_ip_add() {
		return csptrf_mod_ip_add;
	}

	public void setCsptrf_mod_ip_add(String csptrf_mod_ip_add) {
		this.csptrf_mod_ip_add = csptrf_mod_ip_add;
	}

	public String getCsptrf_status() {
		return csptrf_status;
	}

	public void setCsptrf_status(String csptrf_status) {
		this.csptrf_status = csptrf_status;
	}

	public String getAuth_code() {
		return auth_code;
	}

	public void setAuth_code(String auth_code) {
		this.auth_code = auth_code;
	}

	public String getTransporter_name() {
		return transporter_name;
	}

	public void setTransporter_name(String transporter_name) {
		this.transporter_name = transporter_name;
	}
	
	
	
	
	
	

}
