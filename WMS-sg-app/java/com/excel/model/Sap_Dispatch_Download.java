package com.excel.model;

import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedStoredProcedureQuery;
import javax.persistence.ParameterMode;
import javax.persistence.StoredProcedureParameter;
import javax.persistence.Table;

import com.excel.model.AllocationDetailList;


	@Entity
	@Table(name ="Sap_Dispatch_Download")
	@NamedStoredProcedureQuery(
			name="callSap_Dispatch_Download",
			procedureName = "SAP_DISPATCH_DOWNLOAD",
			resultClasses = Sap_Dispatch_Download.class,
			parameters = {
					@StoredProcedureParameter(name="pvfrmdt", mode = ParameterMode.IN, type = String.class),
					@StoredProcedureParameter(name="pvtodt", mode = ParameterMode.IN, type = String.class)
			}
	)
	public class Sap_Dispatch_Download {
		
		@Id
		@Column(name="plant")
		private String plant;
		
		@Column(name="sloc_from")
		private String sloc_from;
		
		@Column(name="material_no")
		private String material_no;
		
		@Column(name="batch")
		private String batch;
		
		@Column(name="quantity")
		private BigDecimal quantity;
		//private double quantity;
		
		@Column(name="mvt_type")
		private String mvt_type;
		
		@Column(name="reason_cd")
		private String reason_cd;
		
		@Column(name="customer")
		private String customer;
		
		@Column(name="vendor")
		private String vendor;
		
		@Column(name="costcenter")
		private String costcenter;
		
		@Column(name="sloc_to")
		private String sloc_to;
		
		@Column(name="batch2")
		private String batch2;
		
		@Column(name="posting_date")
		private String posting_date;
		
		@Column(name="text")
		private String text;
		
		@Column(name="stock_type")
		private String stock_type;
		
		@Column(name="qi_lot_no")
		private String qi_lot_no;
		
		@Column(name="inbound_deli_no")
		private String inbound_deli_no;
		
		@Column(name="ext_map")
		private String ext_map;
		
		@Column(name="vendor_batch")
		private String vendor_batch;

		public String getPlant() {
			return plant;
		}

		public void setPlant(String plant) {
			this.plant = plant;
		}

		public String getSloc_from() {
			return sloc_from;
		}

		public void setSloc_from(String sloc_from) {
			this.sloc_from = sloc_from;
		}

		public String getMaterial_no() {
			return material_no;
		}

		public void setMaterial_no(String material_no) {
			this.material_no = material_no;
		}

		public String getBatch() {
			return batch;
		}

		public void setBatch(String batch) {
			this.batch = batch;
		}

		public BigDecimal getQuantity() {
			return quantity;
		}

		public void setQuantity(BigDecimal quantity) {
			this.quantity = quantity;
		}

		public String getMvt_type() {
			return mvt_type;
		}

		public void setMvt_type(String mvt_type) {
			this.mvt_type = mvt_type;
		}

		public String getReason_cd() {
			return reason_cd;
		}

		public void setReason_cd(String reason_cd) {
			this.reason_cd = reason_cd;
		}

		public String getCustomer() {
			return customer;
		}

		public void setCustomer(String customer) {
			this.customer = customer;
		}

		public String getVendor() {
			return vendor;
		}

		public void setVendor(String vendor) {
			this.vendor = vendor;
		}

		public String getCostcenter() {
			return costcenter;
		}

		public void setCostcenter(String costcenter) {
			this.costcenter = costcenter;
		}

		public String getSloc_to() {
			return sloc_to;
		}

		public void setSloc_to(String sloc_to) {
			this.sloc_to = sloc_to;
		}

		public String getBatch2() {
			return batch2;
		}

		public void setBatch2(String batch2) {
			this.batch2 = batch2;
		}

		public String getPosting_date() {
			return posting_date;
		}

		public void setPosting_date(String posting_date) {
			this.posting_date = posting_date;
		}

		public String getText() {
			return text;
		}

		public void setText(String text) {
			this.text = text;
		}

		public String getStock_type() {
			return stock_type;
		}

		public void setStock_type(String stock_type) {
			this.stock_type = stock_type;
		}

		public String getQi_lot_no() {
			return qi_lot_no;
		}

		public void setQi_lot_no(String qi_lot_no) {
			this.qi_lot_no = qi_lot_no;
		}

		public String getInbound_deli_no() {
			return inbound_deli_no;
		}

		public void setInbound_deli_no(String inbound_deli_no) {
			this.inbound_deli_no = inbound_deli_no;
		}

		public String getExt_map() {
			return ext_map;
		}

		public void setExt_map(String ext_map) {
			this.ext_map = ext_map;
		}

		public String getVendor_batch() {
			return vendor_batch;
		}

		public void setVendor_batch(String vendor_batch) {
			this.vendor_batch = vendor_batch;
		}
		
		
}