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

import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.annotations.SelectBeforeUpdate;

@Entity
@Table(name = "STOCK_TRANSFER_HEADER")
@DynamicUpdate(value = true)
@SelectBeforeUpdate(value = true)
public class Stock_Transfer_Header implements Serializable{

	private static final long serialVersionUID = 7958233307279233590L;

	/*@Override
	public String toString() {
		return "Stock_Transfer_Header [trf_id=" + trf_id + ", trf_no=" + trf_no + ", loc_id=" + loc_id + ", trf_dt="
				+ trf_dt + ", party_id=" + party_id + ", lr_no=" + lr_no + ", lr_dt=" + lr_dt + ", lorry_no=" + lorry_no
				+ ", accntg_co_cd=" + accntg_co_cd + ", rec_loc_id=" + rec_loc_id + ", vat_yn=" + vat_yn + ", trf_type="
				+ trf_type + ", road_permit=" + road_permit + ", f_form_no=" + f_form_no + ", full_shippers="
				+ full_shippers + ", loose_shippers=" + loose_shippers + ", weight=" + weight + ", volume=" + volume
				+ ", user_id=" + user_id + ", fin_year_id=" + fin_year_id + ", comp_cd=" + comp_cd + ", cancelled="
				+ cancelled + ", transporter_name=" + transporter_name + ", transport_mode=" + transport_mode
				+ ", consign_type=" + consign_type + ", auto_grn_auth=" + auto_grn_auth + ", transp_id=" + transp_id
				+ ", recv_stock_point=" + recv_stock_point + ", send_stock_point_id=" + send_stock_point_id
				+ ", driver_name=" + driver_name + ", driver_telno=" + driver_telno + ", goods_value=" + goods_value
				+ ", trd_disc_amt=" + trd_disc_amt + ", prod_disc_amt=" + prod_disc_amt + ", schm_disc_amt="
				+ schm_disc_amt + ", pre_tax_amt=" + pre_tax_amt + ", taxable_amt=" + taxable_amt + ", vat_cst_ind="
				+ vat_cst_ind + ", tax_amt_billed=" + tax_amt_billed + ", tax_amt_free=" + tax_amt_free
				+ ", add_tax_amt=" + add_tax_amt + ", cess_bill_amt=" + cess_bill_amt + ", tot_amt=" + tot_amt
				+ ", surchg_bill_amt=" + surchg_bill_amt + ", party_disc_amt=" + party_disc_amt + ", sgst_bill_amt="
				+ sgst_bill_amt + ", cgst_bill_amt=" + cgst_bill_amt + ", igst_bill_amt=" + igst_bill_amt
				+ ", gst_reverse_chg=" + gst_reverse_chg + ", gst_doc_type=" + gst_doc_type + ", trfhdr_ins_usr_id="
				+ trfhdr_ins_usr_id + ", trfhdr_mod_usr_id=" + trfhdr_mod_usr_id + ", trfhdr_ins_dt=" + trfhdr_ins_dt
				+ ", trfhdr_mod_dt=" + trfhdr_mod_dt + ", trfhdr_ins_ip_add=" + trfhdr_ins_ip_add
				+ ", trfhdr_mod_ip_add=" + trfhdr_mod_ip_add + ", trfhdr_status=" + trfhdr_status + "]";
	}*/

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long trf_id;
	
	@Column(name = "TRF_NO")
	private String trf_no;
	
	@Column(name = "LOC_ID")
	private Long loc_id;
	
	@Column(name = "TRF_DATE")
	private Date  trf_dt;
	
	@Column(name = "PARTY_ID")
	private Long party_id;
	
	@Column(name = "LR_NO")
	private String lr_no;
	
	@Column(name = "LR_DATE")
	private Date lr_dt;
	
	@Column(name = "LORRY_NO")
	private String lorry_no;
	
	@Column(name = "ACCNTG_CO_CD")
	private String accntg_co_cd;
	
	@Column(name= "RECEIVING_LOC_ID")
	private Long rec_loc_id;
	
	@Column(name = "VAT_YN")
	private String vat_yn;
	
	@Column(name = "TRF_TYPE")
	private String trf_type;
	
	@Column(name = "ROAD_PERMIT")
	private String road_permit;
	
	@Column(name = "F_FORM_NO")
	private String f_form_no;
	
	@Column(name = "FULL_SHIPPERS")
	private BigDecimal full_shippers;
	
	@Column(name = "LOOSE_SHIPPERS")
	private BigDecimal loose_shippers;
	
	@Column(name = "WEIGHT")
	private BigDecimal weight;
	
	@Column(name = "VOLUME")
	private BigDecimal volume;
	
	@Column(name = "USER_ID")
	private long user_id;
	
	@Column(name = "FIN_YEAR_ID")
	private Long fin_year_id;
	
	@Column(name = "COMPANY_CD")
	private String comp_cd;
	
	@Column(name = "CANCELLLED")
	private String cancelled;
	
	@Column(name = "TRANSPORTER_NAME")
	private String transporter_name;
	
	@Column(name = "TRANSPORT_MODE")
	private Long transport_mode;
	
	@Column(name = "CONSIGNMENT_TYPE")
	private Long consign_type;
	
	@Column(name = "AUTO_GRN_AUTH")
	private String auto_grn_auth;
	
	@Column(name = "TRANSP_ID")
	private Long transp_id;
	
	@Column(name = "RECV_STOCK_POINT")
	private Long recv_stock_point;

	@Column(name = "SEND_STOCK_POINT_ID")
	private Long send_stock_point_id;
	
	@Column(name = "DRIVER_NAME")
	private String driver_name;

	@Column(name = "DRIVER_TELNO")
	private String driver_telno;
	
	@Column(name = "GOODS_VALUE")
	private BigDecimal goods_value;
	
	@Column(name = "TRD_DISC_AMT")
	private BigDecimal trd_disc_amt;
	
	@Column(name = "PROD_DISC_AMT")
	private BigDecimal prod_disc_amt;
	
	@Column(name = "SCHM_DISC_AMT")
	private BigDecimal schm_disc_amt;
	
	@Column(name = "PRE_TAX_AMT")
	private BigDecimal pre_tax_amt;
	
	@Column(name = "TAXABLE_AMT")
	private BigDecimal taxable_amt;
	
	@Column(name = "VAT_CST_IND")
	private String vat_cst_ind;
	
	@Column(name = "TAX_AMT_BILLED")
	private BigDecimal tax_amt_billed;
	
	@Column(name = "TAX_AMT_FREE")
	private BigDecimal tax_amt_free;
	
	@Column(name = "ADD_TAX_AMT")
	private BigDecimal add_tax_amt;
	
	@Column(name = "CESS_BILL_AMT")
	private BigDecimal cess_bill_amt;
	
	@Column(name = "TOT_AMT")
	private BigDecimal tot_amt;
	
	@Column(name = "SURCHG_BILL_AMT")
	private BigDecimal surchg_bill_amt;
	
	@Column(name = "PARTY_DISC_AMT")
	private BigDecimal party_disc_amt;
	
	@Column(name = "SGST_BILL_AMT")
	private BigDecimal sgst_bill_amt;
	
	@Column(name = "CGST_BILL_AMT")
	private BigDecimal cgst_bill_amt;
	
	@Column(name = "IGST_BILL_AMT")
	private BigDecimal igst_bill_amt;
	
	@Column(name = "GST_REVERSE_CHG")
	private String gst_reverse_chg;
	
	@Column(name = "GST_DOC_TYPE")
	private String gst_doc_type;
	
	@Column(name = "TRFHDR_INS_USR_ID")
	private String trfhdr_ins_usr_id;
	
	@Column(name = "TRFHDR_MOD_USR_ID")
	private String trfhdr_mod_usr_id;
	
	@Column(name = "TRFHDR_INS_DT")
	private Date trfhdr_ins_dt;
	
	@Column(name = "TRFHDR_MOD_DT")
	private Date trfhdr_mod_dt;
	
	@Column(name = "TRFHDR_INS_IP_ADD")
	private String trfhdr_ins_ip_add;
	
	@Column(name = "TRFHDR_MOD_IP_ADD")
	private String trfhdr_mod_ip_add;
	
	@Column(name = "TRFHDR_STATUS")
	private String trfhdr_status;
	
	@Column(name="TOTAL_FREE_QTY")
	private BigDecimal total_free_qty;	
	
	@Column(name="FREE_DISPATCH_NO")
	private String free_dispatch_no;	
	
	@Column(name="FREE_SEPARATE_CHALLAN")
	private String free_separate_challan;	
	
	@Column(name="NILGST_SEPARATE_CHALLAN")
	private String nilgst_separate_challan;	
	
	@Column(name="STOCK_SA_OR_NS")
	private String stock_sa_or_ns;	
	
	
	//this column is never used before 27-08-2018
	//now using this column in case of nil_gst_challan to store inv_group_id
	//required for transfer modify
	/*@Column(name="STOCK_PT_ID")
	private Long stock_pt_id;*/
	
	
	
	
	public Long getTrf_id() {
		return trf_id;
	}

	/*public Stock_Transfer_Header(String f_form_no, BigDecimal full_shippers, BigDecimal loose_shippers,
			BigDecimal weight, BigDecimal volume, BigDecimal goods_value, BigDecimal trd_disc_amt,
			BigDecimal prod_disc_amt, BigDecimal schm_disc_amt, BigDecimal pre_tax_amt, BigDecimal taxable_amt,
			String vat_cst_ind, BigDecimal tax_amt_billed, BigDecimal tax_amt_free, BigDecimal add_tax_amt,
			BigDecimal cess_bill_amt, BigDecimal tot_amt, BigDecimal surchg_bill_amt, BigDecimal party_disc_amt,
			BigDecimal sgst_bill_amt, BigDecimal cgst_bill_amt, BigDecimal igst_bill_amt, Date trfhdr_mod_dt,
			BigDecimal total_free_qty, String free_dispatch_no, String free_separate_challan,
			String nilgst_separate_challan) {
		super();
		this.f_form_no = f_form_no;
		this.full_shippers = full_shippers;
		this.loose_shippers = loose_shippers;
		this.weight = weight;
		this.volume = volume;
		this.goods_value = goods_value;
		this.trd_disc_amt = trd_disc_amt;
		this.prod_disc_amt = prod_disc_amt;
		this.schm_disc_amt = schm_disc_amt;
		this.pre_tax_amt = pre_tax_amt;
		this.taxable_amt = taxable_amt;
		this.vat_cst_ind = vat_cst_ind;
		this.tax_amt_billed = tax_amt_billed;
		this.tax_amt_free = tax_amt_free;
		this.add_tax_amt = add_tax_amt;
		this.cess_bill_amt = cess_bill_amt;
		this.tot_amt = tot_amt;
		this.surchg_bill_amt = surchg_bill_amt;
		this.party_disc_amt = party_disc_amt;
		this.sgst_bill_amt = sgst_bill_amt;
		this.cgst_bill_amt = cgst_bill_amt;
		this.igst_bill_amt = igst_bill_amt;
		this.trfhdr_mod_dt = trfhdr_mod_dt;
		this.total_free_qty = total_free_qty;
		this.free_dispatch_no = free_dispatch_no;
		this.free_separate_challan = free_separate_challan;
		this.nilgst_separate_challan = nilgst_separate_challan;
	}*/
	
	public Stock_Transfer_Header(){
		this.f_form_no = "NA";
		this.full_shippers = BigDecimal.ZERO;
		this.loose_shippers = BigDecimal.ZERO;
		this.weight = BigDecimal.ZERO;
		this.volume = BigDecimal.ZERO;
		this.goods_value = BigDecimal.ZERO;
		this.trd_disc_amt = BigDecimal.ZERO;
		this.prod_disc_amt = BigDecimal.ZERO;
		this.schm_disc_amt = BigDecimal.ZERO;
		this.pre_tax_amt = BigDecimal.ZERO;
		this.taxable_amt = BigDecimal.ZERO;
		this.tax_amt_billed = BigDecimal.ZERO;
		this.tax_amt_free = BigDecimal.ZERO;
		this.add_tax_amt = BigDecimal.ZERO;
		this.cess_bill_amt = BigDecimal.ZERO;
		this.tot_amt = BigDecimal.ZERO;
		this.surchg_bill_amt = BigDecimal.ZERO;
		this.party_disc_amt = BigDecimal.ZERO;
		this.sgst_bill_amt = BigDecimal.ZERO;
		this.cgst_bill_amt = BigDecimal.ZERO;
		this.igst_bill_amt = BigDecimal.ZERO;
		this.total_free_qty = BigDecimal.ZERO;
		this.free_separate_challan = "N";
		this.nilgst_separate_challan = "N";
	}

	public void setTrf_id(Long trf_id) {
		this.trf_id = trf_id;
	}

	public String getTrf_no() {
		return trf_no;
	}

	public void setTrf_no(String trf_no) {
		this.trf_no = trf_no;
	}

	public Long getLoc_id() {
		return loc_id;
	}

	public void setLoc_id(Long loc_id) {
		this.loc_id = loc_id;
	}

	public Date getTrf_dt() {
		return trf_dt;
	}

	public void setTrf_dt(Date trf_dt) {
		this.trf_dt = trf_dt;
	}

	public Long getParty_id() {
		return party_id;
	}

	public void setParty_id(Long party_id) {
		this.party_id = party_id;
	}

	public String getLr_no() {
		return lr_no;
	}

	public void setLr_no(String lr_no) {
		this.lr_no = lr_no;
	}

	public Date getLr_dt() {
		return lr_dt;
	}

	public void setLr_dt(Date lr_dt) {
		this.lr_dt = lr_dt;
	}

	public String getLorry_no() {
		return lorry_no;
	}

	public void setLorry_no(String lorry_no) {
		this.lorry_no = lorry_no;
	}

	public String getAccntg_co_cd() {
		return accntg_co_cd;
	}

	public void setAccntg_co_cd(String accntg_co_cd) {
		this.accntg_co_cd = accntg_co_cd;
	}

	public Long getRec_loc_id() {
		return rec_loc_id;
	}

	public void setRec_loc_id(Long rec_loc_id) {
		this.rec_loc_id = rec_loc_id;
	}

	public String getVat_yn() {
		return vat_yn;
	}

	public void setVat_yn(String vat_yn) {
		this.vat_yn = vat_yn;
	}

	public String getTrf_type() {
		return trf_type;
	}

	public void setTrf_type(String trf_type) {
		this.trf_type = trf_type;
	}

	public String getRoad_permit() {
		return road_permit;
	}

	public void setRoad_permit(String road_permit) {
		this.road_permit = road_permit;
	}

	public String getF_form_no() {
		return f_form_no;
	}

	public void setF_form_no(String f_form_no) {
		this.f_form_no = f_form_no;
	}

	public BigDecimal getFull_shippers() {
		return full_shippers;
	}

	public void setFull_shippers(BigDecimal full_shippers) {
		this.full_shippers = full_shippers;
	}

	public BigDecimal getLoose_shippers() {
		return loose_shippers;
	}

	public void setLoose_shippers(BigDecimal loose_shippers) {
		this.loose_shippers = loose_shippers;
	}

	public BigDecimal getWeight() {
		return weight;
	}

	public void setWeight(BigDecimal weight) {
		this.weight = weight;
	}

	public BigDecimal getVolume() {
		return volume;
	}

	public void setVolume(BigDecimal volume) {
		this.volume = volume;
	}

	public long getUser_id() {
		return user_id;
	}

	public void setUser_id(long user_id) {
		this.user_id = user_id;
	}

	public Long getFin_year_id() {
		return fin_year_id;
	}

	public void setFin_year_id(Long fin_year_id) {
		this.fin_year_id = fin_year_id;
	}

	public String getComp_cd() {
		return comp_cd;
	}

	public void setComp_cd(String comp_cd) {
		this.comp_cd = comp_cd;
	}

	public String getCancelled() {
		return cancelled;
	}

	public void setCancelled(String cancelled) {
		this.cancelled = cancelled;
	}

	public String getTransporter_name() {
		return transporter_name;
	}

	public void setTransporter_name(String transporter_name) {
		this.transporter_name = transporter_name;
	}

	public Long getConsign_type() {
		return consign_type;
	}

	public void setConsign_type(Long consign_type) {
		this.consign_type = consign_type;
	}

	public Long getTransport_mode() {
		return transport_mode;
	}

	public void setTransport_mode(Long transport_mode) {
		this.transport_mode = transport_mode;
	}

	public String getAuto_grn_auth() {
		return auto_grn_auth;
	}

	public void setAuto_grn_auth(String auto_grn_auth) {
		this.auto_grn_auth = auto_grn_auth;
	}

	public Long getTransp_id() {
		return transp_id;
	}

	public void setTransp_id(Long transp_id) {
		this.transp_id = transp_id;
	}

	public Long getRecv_stock_point() {
		return recv_stock_point;
	}

	public void setRecv_stock_point(Long recv_stock_point) {
		this.recv_stock_point = recv_stock_point;
	}

	public Long getSend_stock_point_id() {
		return send_stock_point_id;
	}

	public void setSend_stock_point_id(Long send_stock_point_id) {
		this.send_stock_point_id = send_stock_point_id;
	}

	public void setDriver_name(String driver_name) {
		this.driver_name = driver_name;
	}

	public String getDriver_name() {
		return driver_name;
	}

	public void setDriver_telno(String driver_telno) {
		this.driver_telno = driver_telno;
	}

	public String getDriver_telno() {
		return driver_telno;
	}

	public BigDecimal getGoods_value() {
		return goods_value;
	}

	public void setGoods_value(BigDecimal goods_value) {
		this.goods_value = goods_value;
	}

	public BigDecimal getTrd_disc_amt() {
		return trd_disc_amt;
	}

	public void setTrd_disc_amt(BigDecimal trd_disc_amt) {
		this.trd_disc_amt = trd_disc_amt;
	}

	public BigDecimal getProd_disc_amt() {
		return prod_disc_amt;
	}

	public void setProd_disc_amt(BigDecimal prod_disc_amt) {
		this.prod_disc_amt = prod_disc_amt;
	}

	public BigDecimal getSchm_disc_amt() {
		return schm_disc_amt;
	}

	public void setSchm_disc_amt(BigDecimal schm_disc_amt) {
		this.schm_disc_amt = schm_disc_amt;
	}

	public BigDecimal getPre_tax_amt() {
		return pre_tax_amt;
	}

	public void setPre_tax_amt(BigDecimal pre_tax_amt) {
		this.pre_tax_amt = pre_tax_amt;
	}

	public BigDecimal getTaxable_amt() {
		return taxable_amt;
	}

	public void setTaxable_amt(BigDecimal taxable_amt) {
		this.taxable_amt = taxable_amt;
	}

	public String getVat_cst_ind() {
		return vat_cst_ind;
	}

	public void setVat_cst_ind(String vat_cst_ind) {
		this.vat_cst_ind = vat_cst_ind;
	}

	public BigDecimal getTax_amt_billed() {
		return tax_amt_billed;
	}

	public void setTax_amt_billed(BigDecimal tax_amt_billed) {
		this.tax_amt_billed = tax_amt_billed;
	}

	public BigDecimal getTax_amt_free() {
		return tax_amt_free;
	}

	public void setTax_amt_free(BigDecimal tax_amt_free) {
		this.tax_amt_free = tax_amt_free;
	}

	

	public BigDecimal getAdd_tax_amt() {
		return add_tax_amt;
	}

	public void setAdd_tax_amt(BigDecimal add_tax_amt) {
		this.add_tax_amt = add_tax_amt;
	}

	public BigDecimal getCess_bill_amt() {
		return cess_bill_amt;
	}

	public void setCess_bill_amt(BigDecimal cess_bill_amt) {
		this.cess_bill_amt = cess_bill_amt;
	}

	public BigDecimal getTot_amt() {
		return tot_amt;
	}

	public void setTot_amt(BigDecimal tot_amt) {
		this.tot_amt = tot_amt;
	}

	public BigDecimal getSurchg_bill_amt() {
		return surchg_bill_amt;
	}

	public void setSurchg_bill_amt(BigDecimal surchg_bill_amt) {
		this.surchg_bill_amt = surchg_bill_amt;
	}

	public BigDecimal getParty_disc_amt() {
		return party_disc_amt;
	}

	public void setParty_disc_amt(BigDecimal party_disc_amt) {
		this.party_disc_amt = party_disc_amt;
	}

	

	public BigDecimal getSgst_bill_amt() {
		return sgst_bill_amt;
	}

	public void setSgst_bill_amt(BigDecimal sgst_bill_amt) {
		this.sgst_bill_amt = sgst_bill_amt;
	}

	

	public BigDecimal getCgst_bill_amt() {
		return cgst_bill_amt;
	}

	public void setCgst_bill_amt(BigDecimal cgst_bill_amt) {
		this.cgst_bill_amt = cgst_bill_amt;
	}

	

	public BigDecimal getIgst_bill_amt() {
		return igst_bill_amt;
	}

	public void setIgst_bill_amt(BigDecimal igst_bill_amt) {
		this.igst_bill_amt = igst_bill_amt;
	}

	public String getGst_reverse_chg() {
		return gst_reverse_chg;
	}

	public void setGst_reverse_chg(String gst_reverse_chg) {
		this.gst_reverse_chg = gst_reverse_chg;
	}

	public String getGst_doc_type() {
		return gst_doc_type;
	}

	public void setGst_doc_type(String gst_doc_type) {
		this.gst_doc_type = gst_doc_type;
	}

	public String getTrfhdr_ins_usr_id() {
		return trfhdr_ins_usr_id;
	}

	public void setTrfhdr_ins_usr_id(String trfhdr_ins_usr_id) {
		this.trfhdr_ins_usr_id = trfhdr_ins_usr_id;
	}

	public String getTrfhdr_mod_usr_id() {
		return trfhdr_mod_usr_id;
	}

	public void setTrfhdr_mod_usr_id(String trfhdr_mod_usr_id) {
		this.trfhdr_mod_usr_id = trfhdr_mod_usr_id;
	}

	public Date getTrfhdr_ins_dt() {
		return trfhdr_ins_dt;
	}

	public void setTrfhdr_ins_dt(Date trfhdr_ins_dt) {
		this.trfhdr_ins_dt = trfhdr_ins_dt;
	}

	public Date getTrfhdr_mod_dt() {
		return trfhdr_mod_dt;
	}

	public void setTrfhdr_mod_dt(Date trfhdr_mod_dt) {
		this.trfhdr_mod_dt = trfhdr_mod_dt;
	}

	public String getTrfhdr_ins_ip_add() {
		return trfhdr_ins_ip_add;
	}

	public void setTrfhdr_ins_ip_add(String trfhdr_ins_ip_add) {
		this.trfhdr_ins_ip_add = trfhdr_ins_ip_add;
	}

	public String getTrfhdr_mod_ip_add() {
		return trfhdr_mod_ip_add;
	}

	public void setTrfhdr_mod_ip_add(String trfhdr_mod_ip_add) {
		this.trfhdr_mod_ip_add = trfhdr_mod_ip_add;
	}

	public String getTrfhdr_status() {
		return trfhdr_status;
	}

	public void setTrfhdr_status(String trfhdr_status) {
		this.trfhdr_status = trfhdr_status;
	}

	public BigDecimal getTotal_free_qty() {
		return total_free_qty;
	}

	public void setTotal_free_qty(BigDecimal total_free_qty) {
		this.total_free_qty = total_free_qty;
	}

	public String getFree_dispatch_no() {
		return free_dispatch_no;
	}

	public void setFree_dispatch_no(String free_dispatch_no) {
		this.free_dispatch_no = free_dispatch_no;
	}

	public String getFree_separate_challan() {
		return free_separate_challan;
	}

	public void setFree_separate_challan(String free_separate_challan) {
		this.free_separate_challan = free_separate_challan;
	}

	public String getNilgst_separate_challan() {
		return nilgst_separate_challan;
	}

	public void setNilgst_separate_challan(String nilgst_separate_challan) {
		this.nilgst_separate_challan = nilgst_separate_challan;
	}

	public String getStock_sa_or_ns() {
		return stock_sa_or_ns;
	}

	public void setStock_sa_or_ns(String stock_sa_or_ns) {
		this.stock_sa_or_ns = stock_sa_or_ns;
	}
	
	public Stock_Transfer_Header(Long trf_id,String trf_no,String nilgst_separate_challan) {
		this.trf_id=trf_id;
		this.trf_no=trf_no;
		this.nilgst_separate_challan=nilgst_separate_challan;
	}
	
}
