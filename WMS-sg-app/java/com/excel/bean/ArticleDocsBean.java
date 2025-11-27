package com.excel.bean;

import java.util.Date;

public class ArticleDocsBean {

	String FileName;
	String FileType;
	String slNo;
	String FilePath;
	String scheme_slno; 
	String DOC_ins_usr_id; 
	String INS_ip_addr; 
	Date DOC_ins_dt;
	
	
	public String getDOC_ins_usr_id() {
		return DOC_ins_usr_id;
	}
	public void setDOC_ins_usr_id(String dOC_ins_usr_id) {
		DOC_ins_usr_id = dOC_ins_usr_id;
	}
	public String getINS_ip_addr() {
		return INS_ip_addr;
	}
	public void setINS_ip_addr(String iNS_ip_addr) {
		INS_ip_addr = iNS_ip_addr;
	}
	public Date getDOC_ins_dt() {
		return DOC_ins_dt;
	}
	public void setDOC_ins_dt(Date dOC_ins_dt) {
		DOC_ins_dt = dOC_ins_dt;
	}
	public String getFilePath() {
		return FilePath;
	}
	public void setFilePath(String filePath) {
		FilePath = filePath;
	}
	public String getSlNo() {
		return slNo;
	}
	public String getScheme_slno() {
		return scheme_slno;
	}
	public void setScheme_slno(String scheme_slno) {
		this.scheme_slno = scheme_slno;
	}
	public void setSlNo(String slNo) {
		this.slNo = slNo;
	}
	public String getFileName() {
		return FileName;
	}
	public void setFileName(String fileName) {
		FileName = fileName;
	}
	public String getFileType() {
		return FileType;
	}
	public void setFileType(String fileType) {
		FileType = fileType;
	}
	@Override
	public String toString() {
		return "ArticleDocsBean [FileName=" + FileName + ", FileType=" + FileType + ", slNo=" + slNo + ", FilePath="
				+ FilePath + "]";
	}
	
	
}
