package com.excel.model;

import java.math.BigDecimal;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedStoredProcedureQuery;
import javax.persistence.ParameterMode;
import javax.persistence.StoredProcedureParameter;
import javax.persistence.Table;
import javax.persistence.Transient;


@Entity
@Table(name = "STOCK_STATEMENT_NONSALEABLE")
@NamedStoredProcedureQuery(name = "STOCK_STATEMENT_NONSALEABLE_VET", 
	procedureName = "STOCK_STATEMENT_NONSALEABLE",
	parameters = {
			@StoredProcedureParameter(name = "startdate", mode = ParameterMode.IN, type = String.class),
			@StoredProcedureParameter(name = "endDate", mode = ParameterMode.IN, type = String.class),
			@StoredProcedureParameter(name = "div_id" , mode = ParameterMode.IN, type = String.class),
			@StoredProcedureParameter(name = "loc_id" , mode = ParameterMode.IN, type = String.class),
			@StoredProcedureParameter(name = "nilstock" , mode = ParameterMode.IN, type = String.class),
			@StoredProcedureParameter(name = "ProdTypeId" , mode = ParameterMode.IN, type = String.class),
			@StoredProcedureParameter(name = "ProdId" , mode = ParameterMode.IN, type = String.class),
			@StoredProcedureParameter(name = "StockType" , mode = ParameterMode.IN, type = String.class),
			@StoredProcedureParameter(name = "USER_ID" , mode = ParameterMode.IN, type = String.class),
			
			@StoredProcedureParameter(name = "fin_year_flag" , mode = ParameterMode.IN, type = String.class),
			@StoredProcedureParameter(name = "fin_year_id" , mode = ParameterMode.IN, type = String.class),
			
	}, resultClasses = Stock_Statement_Non_Sale_VET.class)
public class Stock_Statement_Non_Sale_VET {
	
	@Id
	@Column(name = "Row")
	private String row;
	
	@Column(name = "SMP_STD_DIV_ID")
	private String smp_std_div_id;
	
	@Column(name = "Team")
	private String team;
	
	@Column(name = "Loc_name")
	private String loc_name;
	
	@Column(name = "Prod_type")
	private String prod_type;
	
	@Column(name = "SMP_PROD_ID")
	private String smp_prod_id;
	
	@Column(name = "Product_code")
	private String product_code;
	
	@Column(name = "SMP_PACK_ID")
	private String smp_pack_id;
	
	@Column(name = "PACK_DISP_NM")
	private String pack_disp_nm;
	
	@Column(name = "Product_name")
	private String product_name;
	
	@Column(name = "Batch_No")
	private String batch_no;
	
	@Column(name = "Stock_Type")
	private String stock_type;
	
	@Column(name = "Stock_Type_Desc")
	private String stock_type_desc;
	
	@Column(name = "BATCH_OPEN_STOCK")
	private BigDecimal batch_open_stock;
	
	@Column(name = "OPEN_STOCK_VALUE")
	private BigDecimal open_stock_value;
	
	@Column(name = "GRN_REJ")
	private BigDecimal grn_rej;
	
	@Column(name = "GRN_REJ_VALUE")
	private BigDecimal grn_rej_value;
	
	@Column(name = "IAA_IN")
	private BigDecimal iaa_in;
	
	@Column(name = "IAA_IN_VALUE")
	private BigDecimal iaa_in_value;

	@Column(name = "IAA_OUT")
	private BigDecimal iaa_out;
	
	@Column(name = "IAA_OUT_VALUE")
	private BigDecimal iaa_out_value;
	
	@Column(name = "SW_OUT")
	private BigDecimal sw_out;
	
	@Column(name = "SW_OUT_VALUE")
	private BigDecimal sw_out_value;
	
	@Column(name = "CLOS_QTY")
	private BigDecimal clos_qty;

	@Column(name = "CLOS_VALUE")
	private BigDecimal clos_value;
	
	@Column(name = "UNIT_PRICE")
	private BigDecimal unit_price;

	@Column(name = "Receipt_Date")
	private String receipt_date;
	
	@Column(name = "Expiry_Date")
	private String expiry_date;

//	@Column(name = "BATCH_WITH_HELD_QTY")
//	private BigDecimal batch_with_held_qty;
	
	
	
	
	@Transient
	private BigDecimal in_ward_total;
	@Transient
	private BigDecimal out_ward_total;
	
	@Override
	public String toString() {
		return "Stock_Statement_Non_Sale [row=" + row + ", smp_std_div_id=" + smp_std_div_id + ", team=" + team
				+ ", loc_name=" + loc_name + ", prod_type=" + prod_type + ", smp_prod_id=" + smp_prod_id
				+ ", product_code=" + product_code + ", smp_pack_id=" + smp_pack_id + ", pack_disp_nm=" + pack_disp_nm
				+ ", product_name=" + product_name + ", batch_no=" + batch_no + ", stock_type=" + stock_type
				+ ", stock_type_desc=" + stock_type_desc + ", batch_open_stock=" + batch_open_stock
				+ ", open_stock_value=" + open_stock_value + ", grn_rej=" + grn_rej + ", grn_rej_value=" + grn_rej_value
				+ ", iaa_in=" + iaa_in + ", iaa_in_value=" + iaa_in_value + ", iaa_out=" + iaa_out + ", iaa_out_value="
				+ iaa_out_value + ", sw_out=" + sw_out + ", sw_out_value=" + sw_out_value + ", clos_qty=" + clos_qty
				+ ", clos_value=" + clos_value + ", unit_price=" + unit_price + ", receipt_date=" + receipt_date
				+ ", expiry_date=" + expiry_date +  ", in_ward_total="
				+ in_ward_total + ", out_ward_total=" + out_ward_total + "]";
	}



	public BigDecimal getIn_ward_total() {
		return in_ward_total;
	}

	public void setIn_ward_total(BigDecimal in_ward_total) {
		this.in_ward_total = in_ward_total;
	}

	public BigDecimal getOut_ward_total() {
		return out_ward_total;
	}

	public void setOut_ward_total(BigDecimal out_ward_total) {
		this.out_ward_total = out_ward_total;
	}



	public String getRow() {
		return row;
	}

	public void setRow(String row) {
		this.row = row;
	}

	public String getSmp_std_div_id() {
		return smp_std_div_id;
	}

	public void setSmp_std_div_id(String smp_std_div_id) {
		this.smp_std_div_id = smp_std_div_id;
	}

	public String getTeam() {
		return team;
	}

	public void setTeam(String team) {
		this.team = team;
	}

	public String getLoc_name() {
		return loc_name;
	}

	public void setLoc_name(String loc_name) {
		this.loc_name = loc_name;
	}

	public String getProd_type() {
		return prod_type;
	}

	public void setProd_type(String prod_type) {
		this.prod_type = prod_type;
	}

	public String getSmp_prod_id() {
		return smp_prod_id;
	}

	public void setSmp_prod_id(String smp_prod_id) {
		this.smp_prod_id = smp_prod_id;
	}

	public String getProduct_code() {
		return product_code;
	}

	public void setProduct_code(String product_code) {
		this.product_code = product_code;
	}

	public String getSmp_pack_id() {
		return smp_pack_id;
	}

	public void setSmp_pack_id(String smp_pack_id) {
		this.smp_pack_id = smp_pack_id;
	}

	public String getPack_disp_nm() {
		return pack_disp_nm;
	}

	public void setPack_disp_nm(String pack_disp_nm) {
		this.pack_disp_nm = pack_disp_nm;
	}

	public String getProduct_name() {
		return product_name;
	}

	public void setProduct_name(String product_name) {
		this.product_name = product_name;
	}

	public String getBatch_no() {
		return batch_no;
	}

	public void setBatch_no(String batch_no) {
		this.batch_no = batch_no;
	}

	public String getStock_type() {
		return stock_type;
	}

	public void setStock_type(String stock_type) {
		this.stock_type = stock_type;
	}

	public String getStock_type_desc() {
		return stock_type_desc;
	}

	public void setStock_type_desc(String stock_type_desc) {
		this.stock_type_desc = stock_type_desc;
	}

	public BigDecimal getBatch_open_stock() {
		return batch_open_stock;
	}

	public void setBatch_open_stock(BigDecimal batch_open_stock) {
		this.batch_open_stock = batch_open_stock;
	}

	public BigDecimal getOpen_stock_value() {
		return open_stock_value;
	}

	public void setOpen_stock_value(BigDecimal open_stock_value) {
		this.open_stock_value = open_stock_value;
	}

	public BigDecimal getGrn_rej() {
		return grn_rej;
	}

	public void setGrn_rej(BigDecimal grn_rej) {
		this.grn_rej = grn_rej;
	}

	public BigDecimal getGrn_rej_value() {
		return grn_rej_value;
	}

	public void setGrn_rej_value(BigDecimal grn_rej_value) {
		this.grn_rej_value = grn_rej_value;
	}

	public BigDecimal getIaa_in() {
		return iaa_in;
	}

	public void setIaa_in(BigDecimal iaa_in) {
		this.iaa_in = iaa_in;
	}

	public BigDecimal getIaa_in_value() {
		return iaa_in_value;
	}

	public void setIaa_in_value(BigDecimal iaa_in_value) {
		this.iaa_in_value = iaa_in_value;
	}

	public BigDecimal getIaa_out() {
		return iaa_out;
	}

	public void setIaa_out(BigDecimal iaa_out) {
		this.iaa_out = iaa_out;
	}

	public BigDecimal getIaa_out_value() {
		return iaa_out_value;
	}

	public void setIaa_out_value(BigDecimal iaa_out_value) {
		this.iaa_out_value = iaa_out_value;
	}

	public BigDecimal getSw_out() {
		return sw_out;
	}

	public void setSw_out(BigDecimal sw_out) {
		this.sw_out = sw_out;
	}

	public BigDecimal getSw_out_value() {
		return sw_out_value;
	}

	public void setSw_out_value(BigDecimal sw_out_value) {
		this.sw_out_value = sw_out_value;
	}

	public BigDecimal getClos_qty() {
		return clos_qty;
	}

	public void setClos_qty(BigDecimal clos_qty) {
		this.clos_qty = clos_qty;
	}

	public BigDecimal getClos_value() {
		return clos_value;
	}

	public void setClos_value(BigDecimal clos_value) {
		this.clos_value = clos_value;
	}

	public BigDecimal getUnit_price() {
		return unit_price;
	}

	public void setUnit_price(BigDecimal unit_price) {
		this.unit_price = unit_price;
	}

	public String getReceipt_date() {
		return receipt_date;
	}

	public void setReceipt_date(String receipt_date) {
		this.receipt_date = receipt_date;
	}

	public String getExpiry_date() {
		return expiry_date;
	}

	public void setExpiry_date(String expiry_date) {
		this.expiry_date = expiry_date;
	}

}
