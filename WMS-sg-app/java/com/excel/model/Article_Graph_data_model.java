package com.excel.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedStoredProcedureQuery;
import javax.persistence.ParameterMode;
import javax.persistence.StoredProcedureParameter;
import javax.persistence.Table;


@Entity
@Table(name = "DBOARD_GRAPH_ARTICLE_SCHEME")


@NamedStoredProcedureQuery(name = "callArticle_Graph_data_model", procedureName = "DBOARD_GRAPH_ARTICLE_SCHEME", parameters = {
		@StoredProcedureParameter(name = "pLoc_id", mode = ParameterMode.IN, type = Long.class),
		@StoredProcedureParameter(name = "puserid", mode = ParameterMode.IN, type = String.class),
		@StoredProcedureParameter(name = "psub_comp_cd", mode = ParameterMode.IN, type = String.class),}, 
		resultClasses = Article_Graph_data_model.class)


public class Article_Graph_data_model {

	@Id
	@Column(name = "ROWID")
	private Long rowid;
	
	@Column(name = "TYPE")
	private String type;
	
	@Column(name = "LABEL")
	private String label;
	
	@Column(name = "CNT")
	private Long cnt;

	public Long getRowid() {
		return rowid;
	}

	public void setRowid(Long rowid) {
		this.rowid = rowid;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getLabel() {
		return label;
	}

	public void setLabel(String label) {
		this.label = label;
	}

	public Long getCnt() {
		return cnt;
	}

	public void setCnt(Long cnt) {
		this.cnt = cnt;
	}

	@Override
	public String toString() {
		return "Article_Graph_data_model [rowid=" + rowid + ", type=" + type + ", label=" + label + ", cnt=" + cnt
				+ "]";
	}
	
	
	
}
