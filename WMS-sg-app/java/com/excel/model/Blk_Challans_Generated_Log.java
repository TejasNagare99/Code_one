package com.excel.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="BLK_CHALLANS_GENERATED_LOG")
public class Blk_Challans_Generated_Log {

	@Id
	@Column(name="ROW")
	private Long row;

	@Column(name="DSP_FIN_YEAR")
	private String dsp_fin_year;

	@Column(name="DSP_PERIOD_CODE")
	private String dsp_period_code;

	@Column(name="DIV_DISP_NM")
	private String div_disp_nm;

	@Column(name="BLK_HCP_REQ_NO")
	private String blk_hcp_req_no;

	@Column(name="SRT_NUMBER")
	private String srt_number;

	@Column(name="SRT_DATE")
	private Date srt_date;

	@Column(name="TOTAL_REQUESTS")
	private Long total_requests;

	@Column(name="PROD_TYPE")
	private String prod_type;

	@Column(name="DISP_TO_TYPE")
	private String disp_to_type;

	@Column(name="FROM_CHALLAN_NO")
	private String from_challan_no;

	@Column(name="TO_CHALLAN_NO")
	private String to_challan_no;

	@Column(name="TOTAL_CHALLANS")
	private Long total_challans;

	public Long getRow(){
	 return row;
	} 

	public void setRow(Long row){
	 this.row = row;
	}

	public String getDsp_fin_year(){
	 return dsp_fin_year;
	} 

	public void setDsp_fin_year(String dsp_fin_year){
	 this.dsp_fin_year = dsp_fin_year;
	}

	public String getDsp_period_code(){
	 return dsp_period_code;
	} 

	public void setDsp_period_code(String dsp_period_code){
	 this.dsp_period_code = dsp_period_code;
	}

	public String getDiv_disp_nm(){
	 return div_disp_nm;
	} 

	public void setDiv_disp_nm(String div_disp_nm){
	 this.div_disp_nm = div_disp_nm;
	}

	public String getBlk_hcp_req_no(){
	 return blk_hcp_req_no;
	} 

	public void setBlk_hcp_req_no(String blk_hcp_req_no){
	 this.blk_hcp_req_no = blk_hcp_req_no;
	}

	public String getSrt_number(){
	 return srt_number;
	} 

	public void setSrt_number(String srt_number){
	 this.srt_number = srt_number;
	}

	
	

	public Date getSrt_date() {
		return srt_date;
	}

	public void setSrt_date(Date srt_date) {
		this.srt_date = srt_date;
	}

	public Long getTotal_requests(){
	 return total_requests;
	} 

	public void setTotal_requests(Long total_requests){
	 this.total_requests = total_requests;
	}

	public String getProd_type(){
	 return prod_type;
	} 

	public void setProd_type(String prod_type){
	 this.prod_type = prod_type;
	}

	public String getDisp_to_type(){
	 return disp_to_type;
	} 

	public void setDisp_to_type(String disp_to_type){
	 this.disp_to_type = disp_to_type;
	}

	public String getFrom_challan_no(){
	 return from_challan_no;
	} 

	public void setFrom_challan_no(String from_challan_no){
	 this.from_challan_no = from_challan_no;
	}

	public String getTo_challan_no(){
	 return to_challan_no;
	} 

	public void setTo_challan_no(String to_challan_no){
	 this.to_challan_no = to_challan_no;
	}

	public Long getTotal_challans(){
	 return total_challans;
	} 

	public void setTotal_challans(Long total_challans){
	 this.total_challans = total_challans;
	}

	
	
	
	
	

}
