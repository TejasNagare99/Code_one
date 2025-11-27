package com.excel.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.annotations.SelectBeforeUpdate;

@Entity
@Table(name = "TRANS_KEY_MASTER")
@DynamicUpdate(value = true)
@SelectBeforeUpdate(value = true)
public class Trans_Key_Master implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "SLNO")
	private Long slno;

	@Column(name = "COMPANY_CD")
	private String company_cd;

	@Column(name = "LOC_ID")
	private Long loc_id;

	@Column(name = "INV_GRP_ID")
	private Long inv_grp_id;

	@Column(name = "TRAN_TYPE_ID")
	private Long tran_type_id;

	@Column(name = "PREFIX")
	private String prefix;

	@Column(name = "OUT_STATE_IND")
	private Character out_state_ind;

	@Column(name = "LAST_NUM")
	private String last_num;

	@Column(name = "HEADING")
	private String heading;

	@Column(name = "FIN_YEAR")
	private String fin_year;

	@Column(name = "POST_GL_ID")
	private Long post_gl_id;

	@Column(name = "LC")
	private String lc;
	@Column(name = "TYPE")
	private String type;	
	
	@Column(name = "STOCK_POINT_ID")
	private Long stock_point_id;

	public Long getSlno() {
		return slno;
	}

	public void setSlno(Long slno) {
		this.slno = slno;
	}

	public String getCompany_cd() {
		return company_cd;
	}

	public void setCompany_cd(String company_cd) {
		this.company_cd = company_cd;
	}

	public Long getLoc_id() {
		return loc_id;
	}

	public void setLoc_id(Long loc_id) {
		this.loc_id = loc_id;
	}

	public Long getInv_grp_id() {
		return inv_grp_id;
	}

	public void setInv_grp_id(Long inv_grp_id) {
		this.inv_grp_id = inv_grp_id;
	}

	public Long getTran_type_id() {
		return tran_type_id;
	}

	public void setTran_type_id(Long tran_type_id) {
		this.tran_type_id = tran_type_id;
	}

	public String getPrefix() {
		return prefix;
	}

	public void setPrefix(String prefix) {
		this.prefix = prefix;
	}

	public Character getOut_state_ind() {
		return out_state_ind;
	}

	public void setOut_state_ind(Character out_state_ind) {
		this.out_state_ind = out_state_ind;
	}

	public String getLast_num() {
		return last_num;
	}

	public void setLast_num(String last_num) {
		this.last_num = last_num;
	}

	public String getHeading() {
		return heading;
	}

	public void setHeading(String heading) {
		this.heading = heading;
	}

	public String getFin_year() {
		return fin_year;
	}

	public void setFin_year(String fin_year) {
		this.fin_year = fin_year;
	}

	public Long getPost_gl_id() {
		return post_gl_id;
	}

	public void setPost_gl_id(Long post_gl_id) {
		this.post_gl_id = post_gl_id;
	}

	public String getLc() {
		return lc;
	}

	public void setLc(String lc) {
		this.lc = lc;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public Long getStock_point_id() {
		return stock_point_id;
	}

	public void setStock_point_id(Long stock_point_id) {
		this.stock_point_id = stock_point_id;
	}
		
	
}
