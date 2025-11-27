package com.excel.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
@Entity
@Table(name="E_INVOICE_HEADER_ARC")
public class EInvoiceHeader_Arc {

	@Id	
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="ARCHIVE_ID")
	private Long archive_id;
	
	@Column(name="SLNO")
	private Long slno;
	
	@Column(name="COMP_CODE")
	private String comp_code;
	
	@Column(name="FIN_YEAR_ID")
	private Long fin_year_id;
	
	@Column(name="TRN_TYPE")
	private String trn_type;
	
	@Column(name="TRN_ID")
	private Long trn_id;
	
	@Column(name="TRN_NUMBER")
	private String trn_number;
	
	@Column(name="TRN_ACTION_TYPE")
	private String trn_action_type;
	
	@Column(name="TRN_DATE")
	private Date trn_date;
	
	@Column(name="TRN_RESULT")
	private String trn_result;
	
	@Column(name="TRN_ACK_NO")
	private String trn_ack_no;
	
	@Column(name="TRN_ACK_DATE")
	private Date trn_ack_date;
	
	@Column(name="TRN_IRN_NUMBER")
	private String trn_irn_number;
	
	@Column(name="TRN_INV_SIGN")
	private String trn_inv_sign;
	
	@Column(name="TRN_QR_CODE")
	private String trn_qr_code;
	
	@Column(name="TRN_STATUS")
	private String trn_status;
	
	@Column(name="TRN_EWAYBILLNO")
	private String trn_ewaybillno;
	
	@Column(name="TRN_EWAYBILLDT")
	private Date trn_ewaybilldt ;
	
	@Column(name="TRN_EWAYBILLVALID")
	private Date trn_ewaybillvalid;
	
	@Column(name="EWAY_BILL_CANCEL")
	private String ewaybillcancel;
	
	@Column(name="IRN_CANCEL")
	private String irncancel;
	
	@Column(name="DIV_ID")
	private Long div_id;

	@Column(name="EINVOICE_TRANID")
	private String einvoice_tranid;
	
	public Long getSlno() {
		return slno;
	}

	public void setSlno(Long slno) {
		this.slno = slno;
	}

	public String getComp_code() {
		return comp_code;
	}

	public void setComp_code(String comp_code) {
		this.comp_code = comp_code;
	}

	public Long getFin_year_id() {
		return fin_year_id;
	}

	public void setFin_year_id(Long fin_year_id) {
		this.fin_year_id = fin_year_id;
	}

	public String getTrn_type() {
		return trn_type;
	}

	public void setTrn_type(String trn_type) {
		this.trn_type = trn_type;
	}

	public Long getTrn_id() {
		return trn_id;
	}

	public void setTrn_id(Long trn_id) {
		this.trn_id = trn_id;
	}

	public String getTrn_number() {
		return trn_number;
	}

	public void setTrn_number(String trn_number) {
		this.trn_number = trn_number;
	}

	public String getTrn_action_type() {
		return trn_action_type;
	}

	public void setTrn_action_type(String trn_action_type) {
		this.trn_action_type = trn_action_type;
	}

	public Date getTrn_date() {
		return trn_date;
	}

	public void setTrn_date(Date trn_date) {
		this.trn_date = trn_date;
	}

	public String getTrn_result() {
		return trn_result;
	}

	public void setTrn_result(String trn_result) {
		this.trn_result = trn_result;
	}

	public String getTrn_ack_no() {
		return trn_ack_no;
	}

	public void setTrn_ack_no(String trn_ack_no) {
		this.trn_ack_no = trn_ack_no;
	}

	public Date getTrn_ack_date() {
		return trn_ack_date;
	}

	public void setTrn_ack_date(Date trn_ack_date) {
		this.trn_ack_date = trn_ack_date;
	}

	public String getTrn_irn_number() {
		return trn_irn_number;
	}

	public void setTrn_irn_number(String trn_irn_number) {
		this.trn_irn_number = trn_irn_number;
	}

	public String getTrn_inv_sign() {
		return trn_inv_sign;
	}

	public void setTrn_inv_sign(String trn_inv_sign) {
		this.trn_inv_sign = trn_inv_sign;
	}

	public String getTrn_qr_code() {
		return trn_qr_code;
	}

	public void setTrn_qr_code(String trn_qr_code) {
		this.trn_qr_code = trn_qr_code;
	}

	public String getTrn_status() {
		return trn_status;
	}

	public void setTrn_status(String trn_status) {
		this.trn_status = trn_status;
	}

	public String getTrn_ewaybillno() {
		return trn_ewaybillno;
	}

	public void setTrn_ewaybillno(String trn_ewaybillno) {
		this.trn_ewaybillno = trn_ewaybillno;
	}

	public Date getTrn_ewaybilldt() {
		return trn_ewaybilldt;
	}

	public void setTrn_ewaybilldt(Date trn_ewaybilldt) {
		this.trn_ewaybilldt = trn_ewaybilldt;
	}

	public Date getTrn_ewaybillvalid() {
		return trn_ewaybillvalid;
	}

	public void setTrn_ewaybillvalid(Date trn_ewaybillvalid) {
		this.trn_ewaybillvalid = trn_ewaybillvalid;
	}

	public String getEwaybillcancel() {
		return ewaybillcancel;
	}

	public void setEwaybillcancel(String ewaybillcancel) {
		this.ewaybillcancel = ewaybillcancel;
	}

	public String getIrncancel() {
		return irncancel;
	}

	public void setIrncancel(String irncancel) {
		this.irncancel = irncancel;
	}

	public Long getDiv_id() {
		return div_id;
	}

	public void setDiv_id(Long div_id) {
		this.div_id = div_id;
	}

	public String getEinvoice_tranid() {
		return einvoice_tranid;
	}

	public void setEinvoice_tranid(String einvoice_tranid) {
		this.einvoice_tranid = einvoice_tranid;
	}

	public Long getArchive_id() {
		return archive_id;
	}

	public void setArchive_id(Long archive_id) {
		this.archive_id = archive_id;
	}

	

	
}
