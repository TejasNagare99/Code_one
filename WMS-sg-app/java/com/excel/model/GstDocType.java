package com.excel.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="GST_DOC_TYPE")
public class GstDocType implements Serializable {

	private static final long serialVersionUID = 8719879903927636249L;

	@Id
	@Column(name="sr_no")
	private Long sr_no;
	
	@Column(name="TRAN_TYPE")
	private String	tran_type;
	
	@Column(name="GST_TYPE")
	private String	gst_type;
	
	@Column(name="PARA_CODE")
	private String	para_code;
	
	@Column(name="GST_DESCRIPTION")
	private String	gst_description;

	public Long getSr_no() {
		return sr_no;
	}

	public void setSr_no(Long sr_no) {
		this.sr_no = sr_no;
	}

	public String getTran_type() {
		return tran_type;
	}

	public void setTran_type(String tran_type) {
		this.tran_type = tran_type;
	}

	public String getGst_type() {
		return gst_type;
	}

	public void setGst_type(String gst_type) {
		this.gst_type = gst_type;
	}

	public String getPara_code() {
		return para_code;
	}

	public void setPara_code(String para_code) {
		this.para_code = para_code;
	}

	public String getGst_description() {
		return gst_description;
	}

	public void setGst_description(String gst_description) {
		this.gst_description = gst_description;
	}

	public GstDocType() {
	}
	
	public GstDocType(String gst_type,String para_code) {
		this.gst_type=gst_type;
		this.para_code=para_code;
	}
}
