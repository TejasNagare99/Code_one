package com.excel.model;

import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

//   row.getCell(j).getNumericCellValue()

@Entity
@Table(name = "AUTO_TRANSFER")
public class AUTO_TRANSFER {

	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "CSPTRF_SRLNO")
	private long csptrf_srlno;

	@Column(name = "CSPTRF_TRF_NO")
	private String csptrf_trf_no;
	
	@Column(name = "CSPTRF_TRF_DATE")
	private Date csptrf_trf_date;
	
	@Column(name = "CSPTRF_PROD_CD")
	private String csptrf_prod_cd;
	
	@Column(name = "CSPTRF_BATCH_NO")
	private String csptrf_batch_no;
	
	@Column(name = "CSPTRF_EXPIRY_DT")
	private Date csptrf_expiry_dt;
	
	@Column(name = "CSPTRF_MFGDT")
	private Date csptrf_mfgdt;
	
	@Column(name = "CSPTRF_MFG_MAP_CD")
	private String csptrf_mfg_map_cd;
	
	@Column(name = "CSPTRF_RECV_LOC")
	private String csptrf_recv_loc;
	
	@Column(name = "CSPTRF_SENDING_LOC")
	private String csptrf_sending_loc;
	
	@Column(name = "CSPTRF_TRFRATE")
	private BigDecimal csptrf_trfrate;
	
	@Column(name = "CSPTRF_QTY")
	private String csptrf_qty;

	@Column(name = "CSPTRF_COGRATE")
	private BigDecimal csptrf_cograte;
	
	@Column(name = "CSPTRF_RMPMRATE")
	private BigDecimal csptrf_rmpmrate;
	
	@Column(name = "CSPTRF_ExciseRATE")
	private BigDecimal csptrf_exciserate;
	
	@Column(name = "CSPTRF_OverheadRATE")
	private BigDecimal csptrf_overheadrate;
	
	@Column(name = "CSPTRF_LRNO")
	private String csptrf_lrno;
	
	@Column(name = "CSPTRF_LR_DATE")
	private Date csptrf_lr_date;
	
	@Column(name = "CSPTRF_NOCASES")
	private long csptrf_nocases;
	
	@Column(name = "CSPTRF_FULLSHIPPER")
	private long csptrf_fullshipper;
	
	@Column(name = "CSPTRF_TRANSPORTER_NAME")
	private String csptrf_transporter_name;
	
	
	public String getAuth_code() {
		return auth_code;
	}

	public void setAuth_code(String auth_code) {
		this.auth_code = auth_code;
	}

	@Column(name = "AUTH_CODE")           
	private String auth_code;

	public long getCsptrf_srlno() {
		return csptrf_srlno;
	}

	public void setCsptrf_srlno(long csptrf_srlno) {
		this.csptrf_srlno = csptrf_srlno;
	}

	public String getCsptrf_trf_no() {
		return csptrf_trf_no;
	}

	public void setCsptrf_trf_no(String csptrf_trf_no) {
		this.csptrf_trf_no = csptrf_trf_no;
	}

	public Date getCsptrf_trf_date() {
		return csptrf_trf_date;
	}

	public void setCsptrf_trf_date(Date csptrf_trf_date) {
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

	public Date getCsptrf_expiry_dt() {
		return csptrf_expiry_dt;
	}

	public void setCsptrf_expiry_dt(Date csptrf_expiry_dt) {
		this.csptrf_expiry_dt = csptrf_expiry_dt;
	}

	public Date getCsptrf_mfgdt() {
		return csptrf_mfgdt;
	}

	public void setCsptrf_mfgdt(Date csptrf_mfgdt) {
		this.csptrf_mfgdt = csptrf_mfgdt;
	}

	public String getCsptrf_mfg_map_cd() {
		return csptrf_mfg_map_cd;
	}

	public void setCsptrf_mfg_map_cd(String csptrf_mfg_map_cd) {
		this.csptrf_mfg_map_cd = csptrf_mfg_map_cd;
	}

	public String getCsptrf_recv_loc() {
		return csptrf_recv_loc;
	}

	public void setCsptrf_recv_loc(String csptrf_recv_loc) {
		this.csptrf_recv_loc = csptrf_recv_loc;
	}

	public String getCsptrf_sending_loc() {
		return csptrf_sending_loc;
	}

	public void setCsptrf_sending_loc(String csptrf_sending_loc) {
		this.csptrf_sending_loc = csptrf_sending_loc;
	}

	public BigDecimal getCsptrf_trfrate() {
		return csptrf_trfrate;
	}

	public void setCsptrf_trfrate(BigDecimal csptrf_trfrate) {
		this.csptrf_trfrate = csptrf_trfrate;
	}

	public String getCsptrf_qty() {
		return csptrf_qty;
	}

	public void setCsptrf_qty(String csptrf_qty) {
		this.csptrf_qty = csptrf_qty;
	}

	public BigDecimal getCsptrf_cograte() {
		return csptrf_cograte;
	}

	public void setCsptrf_cograte(BigDecimal csptrf_cograte) {
		this.csptrf_cograte = csptrf_cograte;
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

	public String getCsptrf_lrno() {
		return csptrf_lrno;
	}

	public void setCsptrf_lrno(String csptrf_lrno) {
		this.csptrf_lrno = csptrf_lrno;
	}

	public Date getCsptrf_lr_date() {
		return csptrf_lr_date;
	}

	public void setCsptrf_lr_date(Date csptrf_lr_date) {
		this.csptrf_lr_date = csptrf_lr_date;
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

	public String getCsptrf_transporter_name() {
		return csptrf_transporter_name;
	}

	public void setCsptrf_transporter_name(String csptrf_transporter_name) {
		this.csptrf_transporter_name = csptrf_transporter_name;
	}

	@Override
	public String toString() {
		return "AUTO_TRANSFER [csptrf_srlno=" + csptrf_srlno + ", csptrf_trf_no=" + csptrf_trf_no + ", csptrf_trf_date="
				+ csptrf_trf_date + ", csptrf_prod_cd=" + csptrf_prod_cd + ", csptrf_batch_no=" + csptrf_batch_no
				+ ", csptrf_expiry_dt=" + csptrf_expiry_dt + ", csptrf_mfgdt=" + csptrf_mfgdt + ", csptrf_mfg_map_cd="
				+ csptrf_mfg_map_cd + ", csptrf_recv_loc=" + csptrf_recv_loc + ", csptrf_sending_loc="
				+ csptrf_sending_loc + ", csptrf_trfrate=" + csptrf_trfrate + ", csptrf_qty=" + csptrf_qty
				+ ", csptrf_cograte=" + csptrf_cograte + ", csptrf_rmpmrate=" + csptrf_rmpmrate + ", csptrf_exciserate="
				+ csptrf_exciserate + ", csptrf_overheadrate=" + csptrf_overheadrate + ", csptrf_lrno=" + csptrf_lrno
				+ ", csptrf_lr_date=" + csptrf_lr_date + ", csptrf_nocases=" + csptrf_nocases + ", csptrf_fullshipper="
				+ csptrf_fullshipper + ", csptrf_transporter_name=" + csptrf_transporter_name + "]";
	}
	
	
	
	
	
	
}
