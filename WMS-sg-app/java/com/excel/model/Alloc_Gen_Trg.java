package com.excel.model;

import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "ALLOC_GEN_TRG")
public class Alloc_Gen_Trg {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "ALLOC_TRG_DTL_ID")
	private Long alloc_trg_dtl_id;
	
	//@ManyToOne(cascade = CascadeType.DETACH)
	@ManyToOne
	@JoinColumn(name = "ALLOC_GEN_ID")
	private Alloc_gen_hd alloc_gen_id;
	//@ManyToOne(cascade = CascadeType.DETACH)
	@ManyToOne
	@JoinColumn(name = "ALLOC_ENT_ID")
	private Alloc_gen_ent alloc_ent_id;
	@Column(name="PRODUCT_TYPE")
	private String product_type;
	@Column(name="ALLOC_QTY1")
	private BigDecimal alloc_qty1;
	@Column(name="ALLOC_QTY2")
	private BigDecimal alloc_qty2;
	@Column(name="ALLOC_QTY3")
	private BigDecimal alloc_qty3;
	@Column(name="ALLOC_QTY4")
	private BigDecimal alloc_qty4;
	public Long getAlloc_trg_dtl_id() {
		return alloc_trg_dtl_id;
	}
	public void setAlloc_trg_dtl_id(Long alloc_trg_dtl_id) {
		this.alloc_trg_dtl_id = alloc_trg_dtl_id;
	}
	
	public String getProduct_type() {
		return product_type;
	}
	public void setProduct_type(String product_type) {
		this.product_type = product_type;
	}
	public BigDecimal getAlloc_qty1() {
		return alloc_qty1;
	}
	public void setAlloc_qty1(BigDecimal alloc_qty1) {
		this.alloc_qty1 = alloc_qty1;
	}
	public BigDecimal getAlloc_qty2() {
		return alloc_qty2;
	}
	public void setAlloc_qty2(BigDecimal alloc_qty2) {
		this.alloc_qty2 = alloc_qty2;
	}
	public BigDecimal getAlloc_qty3() {
		return alloc_qty3;
	}
	public void setAlloc_qty3(BigDecimal alloc_qty3) {
		this.alloc_qty3 = alloc_qty3;
	}
	public BigDecimal getAlloc_qty4() {
		return alloc_qty4;
	}
	public void setAlloc_qty4(BigDecimal alloc_qty4) {
		this.alloc_qty4 = alloc_qty4;
	}
	public void setAlloc_gen_id(Alloc_gen_hd alloc_gen_id) {
		this.alloc_gen_id = alloc_gen_id;
	}
	public void setAlloc_ent_id(Alloc_gen_ent alloc_ent_id) {
		this.alloc_ent_id = alloc_ent_id;
	}

	
	

}
