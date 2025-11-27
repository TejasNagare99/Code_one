package com.excel.bean;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * @author Admin
 *
 */
/**
 * @author Admin
 *
 */
public class AllocationBean {

	private Long ediv_div_id;
	private String div_disp_nm;
	private Long smp_sa_prod_group;
	private String sa_group_name;
	private Long sa_group_id;
	private Long smp_prod_type_id;
	private Long destination_id;
	private String sgprmdet_disp_nm;
	private Long smp_prod_id;
	private String smp_prod_name;
	private String smp_prod_cd;
	private Long smp_pack_id;
	private String pack_disp_nm;
	private BigDecimal smp_min_alloc_qty;
	private Long batch_prod_id;
	private BigDecimal stock;
	private BigDecimal intransit;
	private BigDecimal so_far_qty;
	private BigDecimal dsp_qty;
	private Long cnt;
	private BigDecimal perpsoqty;
	private List<Object> productList;
	private Long fm_cnt;
	private Long actual_team_size;
	private String levelName;
	private String empId;
	private String ipAddress;
	private Long userId;
	private Long mgr_id;
	private String mgr_name;
	private String mgrEmpId;
	
	//Annual Modify
	private Long dec;
	private Long jan;
	private Long feb;
	private Long march;
	private Long april;
	private Long may;
	private Long june;
	private Long july;
	private Long aug;
	private Long sept;
	private Long oct;
	private Long nov;
	private Long desc;
	private Long asp_dtl_id;
	private String asp_fin_year;
	private String indiaMarket;
	private Long totalUnits;
	private BigDecimal avgMonth;
	private String  asp_alloc_number;
	
	
	/* product details */
	private String period_name;
	private String dspdtl_fin_year;
	private String dspdtl_period_code;
	private Long presentInIndia;
	private String supplyType;
	private BigDecimal smp_cog_rate;

	private BigDecimal currTotalUnits;
	private BigDecimal totalCost;
	
	/* conunt */
	private String smp_erp_prod_cd;
	private BigDecimal prvyrdsp_unit;
	private Long prvyrdspcnt;
	private BigDecimal prvdsp_perpso;
	private BigDecimal commutibeDispatch;
	private String companyCode;
	private Long teamId;
	private Long brandId;
	private Long planType;
	private String frequency;
	private Long productId;
	private Long teamSize;
	private List<BigDecimal> currPerPso;
	private BigDecimal cog;
	private Long  asp_id;

	/* Monthly */
	private Long sgprmdet_id;
	private Long allocationById;
	private String allocationByDesc;
	private Long psoCount;
	private Long dmCount;
	private Long rmbCOunt;
	private List<String> teModel;
	private List<String> dmModel;
	private List<String> rmbModel;
	private List<String> tmModel;
	private List<String> smModel;
	private List<String> productIds;
	private List<String> brandIds;
	private String allocationCycle;
	private String allocationSaveMode;
	private String allocationId;
	private String allocationMonthPeriodCode;
	private String fieldtype;
	private String allocationMonth;
	private String allocationMode;
	private String planTypeDesc;
	private String finYearId;
	private String teamName;//divsion name
	private String coreTeamId;
	private Long tmqty;
	private Long smqty;
	private String monthOfUsePeriodName;
	private String monthOfUsePeriodCode;
	private Long plan_qty;
	private String srtno;
	private String srtdate;
	private String srtremark;
	//Modify
	private String  teQty;
	private String dmQty;
	private String rmQty;
	private String tmQty;
	private String smQty;
	private Long alloc_gen_ent_id;
	private List<Long> alloc_gen_ent_ids;
	private String allocationByName;
	private String allocDocNo;
	private Map<String,String> enteredBrandsForAllocationBy;
	private String allocationDate;
	private String companyName;
	private Long staffId;
	private String staffName;
	private BigDecimal staffAllocatedQty;
	private String staffMapCode;
	private String levelCode;
	private Long allocDetailId;
	private List<Long> staffAllocQtyModel;
	private List<String> staffLevelModel;
    private List<Long>  staffIdModel;
    private List<Long>  allocGenDtlIdModel;
    private List<Long> changedIndexModel;
    private Long index; 
    private BigDecimal totalAllocQty;
    private String state;
    private String cfa_destination;
    private String zone;
    private String district;
    private String rigion;
    private String prod_name;
    private String filePath;
    private String status;
    private String user_role;
    private String team_req_ind;
    private Long sub_team_id;
    private String sub_team_code;

    // addded abhishek 
    private Long alloc_temp_header_id;
    private Long modified_final_qty;
    private Long current_final_qty;
	private List<String> alloc_tem_prod_id;
	private String  modified_user_name;
	private Date  modified_date;
	private Date  Current_modified_date;
    private String fscode;
    private String fileName;
    private String insertUserName;
    private Long gen_hd_id;

    
	

	public String getInsertUserName() {
		return insertUserName;
	}

	public void setInsertUserName(String insertUserName) {
		this.insertUserName = insertUserName;
	}

	public Long getGen_hd_id() {
		return gen_hd_id;
	}

	public void setGen_hd_id(Long gen_hd_id) {
		this.gen_hd_id = gen_hd_id;
	}

	public Date getCurrent_modified_date() {
		return Current_modified_date;
	}

	public void setCurrent_modified_date(Date current_modified_date) {
		Current_modified_date = current_modified_date;
	}

	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	public Date getModified_date() {
		return modified_date;
	}

	public void setModified_date(Date modified_date) {
		this.modified_date = modified_date;
	}

	public String getFscode() {
		return fscode;
	}

	public void setFscode(String fscode) {
		this.fscode = fscode;
	}

	public String getModified_user_name() {
		return modified_user_name;
	}

	public void setModified_user_name(String modified_user_name) {
		this.modified_user_name = modified_user_name;
	}


	public String getTeamName() {
		return teamName;
	}

	public void setTeamName(String teamName) {
		this.teamName = teamName;
	}

	public Long getModified_final_qty() {
		return modified_final_qty;
	}

	public void setModified_final_qty(Long modified_final_qty) {
		this.modified_final_qty = modified_final_qty;
	}

	public Long getCurrent_final_qty() {
		return current_final_qty;
	}

	public void setCurrent_final_qty(Long current_final_qty) {
		this.current_final_qty = current_final_qty;
	}

	public List<String> getAlloc_tem_prod_id() {
		return alloc_tem_prod_id;
	}

	public void setAlloc_tem_prod_id(List<String> alloc_tem_prod_id) {
		this.alloc_tem_prod_id = alloc_tem_prod_id;
	}

	public Long getAlloc_temp_header_id() {
		return alloc_temp_header_id;
	}

	public void setAlloc_temp_header_id(Long alloc_temp_header_id) {
		this.alloc_temp_header_id = alloc_temp_header_id;
	}

	public String getSrtno() {
		return srtno;
	}

	public void setSrtno(String srtno) {
		this.srtno = srtno;
	}

	public String getSrtdate() {
		return srtdate;
	}

	public void setSrtdate(String srtdate) {
		this.srtdate = srtdate;
	}

	
	public String getSrtremark() {
		return srtremark;
	}

	public void setSrtremark(String srtremark) {
		this.srtremark = srtremark;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public String getCfa_destination() {
		return cfa_destination;
	}

	public void setCfa_destination(String cfa_destination) {
		this.cfa_destination = cfa_destination;
	}

	public String getZone() {
		return zone;
	}

	public void setZone(String zone) {
		this.zone = zone;
	}

	public String getDistrict() {
		return district;
	}

	public void setDistrict(String district) {
		this.district = district;
	}

	public String getRigion() {
		return rigion;
	}

	public void setRigion(String rigion) {
		this.rigion = rigion;
	}

	public String getTeQty() {
		return teQty;
	}

	public void setTeQty(String teQty) {
		this.teQty = teQty;
	}

	public String getDmQty() {
		return dmQty;
	}

	public void setDmQty(String dmQty) {
		this.dmQty = dmQty;
	}

	public String getRmQty() {
		return rmQty;
	}

	public void setRmQty(String rmQty) {
		this.rmQty = rmQty;
	}

	public String getTmQty() {
		return tmQty;
	}

	public void setTmQty(String tmQty) {
		this.tmQty = tmQty;
	}

	public String getSmQty() {
		return smQty;
	}

	public void setSmQty(String smQty) {
		this.smQty = smQty;
	}

	public void setTmModel(List<String> tmModel) {
		this.tmModel = tmModel;
	}

	public Long getTeamId() {
		return teamId;
	}

	public void setTeamId(Long teamId) {
		this.teamId = teamId;
	}

	public Long getBrandId() {
		return brandId;
	}

	public void setBrandId(Long brandId) {
		this.brandId = brandId;
	}

	public Long getPlanType() {
		return planType;
	}

	public void setPlanType(Long planType) {
		this.planType = planType;
	}

	public String getFrequency() {
		return frequency;
	}

	public void setFrequency(String frequency) {
		this.frequency = frequency;
	}

	public Long getProductId() {
		return productId;
	}

	public void setProductId(Long productId) {
		this.productId = productId;
	}

	public Long getTeamSize() {
		return teamSize;
	}

	public void setTeamSize(Long teamSize) {
		this.teamSize = teamSize;
	}

	public List<BigDecimal> getCurrPerPso() {
		return currPerPso;
	}

	public void setCurrPerPso(List<BigDecimal> currPerPso) {
		this.currPerPso = currPerPso;
	}

	public BigDecimal getCog() {
		return cog;
	}

	public void setCog(BigDecimal cog) {
		this.cog = cog;
	}

	public String getSmp_erp_prod_cd() {
		return smp_erp_prod_cd;
	}

	public void setSmp_erp_prod_cd(String smp_erp_prod_cd) {
		this.smp_erp_prod_cd = smp_erp_prod_cd;
	}


	public Long getPrvyrdspcnt() {
		return prvyrdspcnt;
	}

	public void setPrvyrdspcnt(Long prvyrdspcnt) {
		this.prvyrdspcnt = prvyrdspcnt;
	}

	public BigDecimal getPrvdsp_perpso() {
		return prvdsp_perpso;
	}

	public void setPrvdsp_perpso(BigDecimal prvdsp_perpso) {
		this.prvdsp_perpso = prvdsp_perpso;
	}

	public AllocationBean() {
		/*
		 * this.prvdsp_perpso=BigDecimal.valueOf(10); 
		 * this.dsp_qty=BigDecimal.ZERO;
		 */
	}
	public BigDecimal getCurrTotalUnits() {
		return currTotalUnits;
	}
	public void setCurrTotalUnits(BigDecimal currTotalUnits) {
		this.currTotalUnits = currTotalUnits;
	}

	public String getSupplyType() {
		return supplyType;
	}
	public void setSupplyType(String supplyType) {
		this.supplyType = supplyType;
	}
	public Long getEdiv_div_id() {
		return ediv_div_id;
	}
	public void setEdiv_div_id(Long ediv_div_id) {
		this.ediv_div_id = ediv_div_id;
	}
	public String getDiv_disp_nm() {
		return div_disp_nm;
	}
	public void setDiv_disp_nm(String div_disp_nm) {
		this.div_disp_nm = div_disp_nm;
	}
	public Long getSmp_sa_prod_group() {
		return smp_sa_prod_group;
	}
	public void setSmp_sa_prod_group(Long smp_sa_prod_group) {
		this.smp_sa_prod_group = smp_sa_prod_group;
	}
	public String getSa_group_name() {
		return sa_group_name;
	}
	public void setSa_group_name(String sa_group_name) {
		this.sa_group_name = sa_group_name;
	}
	public Long getSmp_prod_type_id() {
		return smp_prod_type_id;
	}
	public void setSmp_prod_type_id(Long smp_prod_type_id) {
		this.smp_prod_type_id = smp_prod_type_id;
	}
	public String getSgprmdet_disp_nm() {
		return sgprmdet_disp_nm;
	}
	public void setSgprmdet_disp_nm(String sgprmdet_disp_nm) {
		this.sgprmdet_disp_nm = sgprmdet_disp_nm;
	}
	public Long getSmp_prod_id() {
		return smp_prod_id;
	}
	public void setSmp_prod_id(Long smp_prod_id) {
		this.smp_prod_id = smp_prod_id;
	}
	public String getSmp_prod_name() {
		return smp_prod_name;
	}
	public void setSmp_prod_name(String smp_prod_name) {
		this.smp_prod_name = smp_prod_name;
	}

	public Long getCnt() {
		return cnt;
	}
	public void setCnt(Long cnt) {
		this.cnt = cnt;
	}
	public Long getFm_cnt() {
		return fm_cnt;
	}
	public void setFm_cnt(Long fm_cnt) {
		this.fm_cnt = fm_cnt;
	}
	public BigDecimal getDsp_qty() {
		return dsp_qty;
	}
	public void setDsp_qty(BigDecimal dsp_qty) {
		this.dsp_qty = dsp_qty;
	}
	public BigDecimal getPerpsoqty() {
		return perpsoqty;
	}
	public void setPerpsoqty(BigDecimal perpsoqty) {
		this.perpsoqty = perpsoqty;
	}
	public List<Object> getProductList() {
		return productList;
	}
	public void setProductList(List<Object> productList) {
		this.productList = productList;
	}
	public String getPeriod_name() {
		return period_name;
	}
	public void setPeriod_name(String period_name) {
		this.period_name = period_name;
	}
	public String getDspdtl_period_code() {
		return dspdtl_period_code;
	}
	public void setDspdtl_period_code(String dspdtl_period_code) {
		this.dspdtl_period_code = dspdtl_period_code;
	}
	public BigDecimal getSmp_cog_rate() {
		return smp_cog_rate;
	}
	public void setSmp_cog_rate(BigDecimal smp_cog_rate) {
		this.smp_cog_rate = smp_cog_rate;
	}

	public BigDecimal getTotalCost() {
		return totalCost;
	}

	public void setTotalCost(BigDecimal totalCost) {
		this.totalCost = totalCost;
	}

	public Long getPresentInIndia() {
		return presentInIndia;
	}

	public void setPresentInIndia(Long presentInIndia) {
		this.presentInIndia = presentInIndia;
	}

	public BigDecimal getPrvyrdsp_unit() {
		return prvyrdsp_unit;
	}

	public void setPrvyrdsp_unit(BigDecimal prvyrdsp_unit) {
		this.prvyrdsp_unit = prvyrdsp_unit;
	}

	public String getDspdtl_fin_year() {
		return dspdtl_fin_year;
	}

	public void setDspdtl_fin_year(String dspdtl_fin_year) {
		this.dspdtl_fin_year = dspdtl_fin_year;
	}

	public BigDecimal getCommutibeDispatch() {
		return commutibeDispatch;
	}

	public void setCommutibeDispatch(BigDecimal commutibeDispatch) {
		this.commutibeDispatch = commutibeDispatch;
	}

	public String getCompanyCode() {
		return companyCode;
	}

	public void setCompanyCode(String companyCode) {
		this.companyCode = companyCode;
	}

	public Long getAsp_id() {
		return asp_id;
	}

	public void setAsp_id(Long asp_id) {
		this.asp_id = asp_id;
	}

	public String getLevelName() {
		return levelName;
	}

	public void setLevelName(String levelName) {
		this.levelName = levelName;
	}

	public String getEmpId() {
		return empId;
	}

	public void setEmpId(String empId) {
		this.empId = empId;
	}

	public String getIpAddress() {
		return ipAddress;
	}

	public void setIpAddress(String ipAddress) {
		this.ipAddress = ipAddress;
	}

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public Long getSgprmdet_id() {
		return sgprmdet_id;
	}

	public void setSgprmdet_id(Long sgprmdet_id) {
		this.sgprmdet_id = sgprmdet_id;
	}

	public String getSmp_prod_cd() {
		return smp_prod_cd;
	}

	public void setSmp_prod_cd(String smp_prod_cd) {
		this.smp_prod_cd = smp_prod_cd;
	}

	public String getPack_disp_nm() {
		return pack_disp_nm;
	}

	public void setPack_disp_nm(String pack_disp_nm) {
		this.pack_disp_nm = pack_disp_nm;
	}

	public BigDecimal getSmp_min_alloc_qty() {
		return smp_min_alloc_qty;
	}

	public void setSmp_min_alloc_qty(BigDecimal smp_min_alloc_qty) {
		this.smp_min_alloc_qty = smp_min_alloc_qty;
	}

	public Long getBatch_prod_id() {
		return batch_prod_id;
	}

	public void setBatch_prod_id(Long batch_prod_id) {
		this.batch_prod_id = batch_prod_id;
	}

	public BigDecimal getStock() {
		return stock;
	}

	public void setStock(BigDecimal stock) {
		this.stock = stock;
	}

	public BigDecimal getIntransit() {
		return intransit;
	}

	public void setIntransit(BigDecimal intransit) {
		this.intransit = intransit;
	}

	public BigDecimal getSo_far_qty() {
		return so_far_qty;
	}

	public void setSo_far_qty(BigDecimal so_far_qty) {
		this.so_far_qty = so_far_qty;
	}

	public Long getSmp_pack_id() {
		return smp_pack_id;
	}

	public void setSmp_pack_id(Long smp_pack_id) {
		this.smp_pack_id = smp_pack_id;
	}

	public Long getDestination_id() {
		return destination_id;
	}

	public void setDestination_id(Long destination_id) {
		this.destination_id = destination_id;
	}

	public Long getAllocationById() {
		return allocationById;
	}

	public void setAllocationById(Long allocationById) {
		this.allocationById = allocationById;
	}

	public String getAllocationByDesc() {
		return allocationByDesc;
	}

	public void setAllocationByDesc(String allocationByDesc) {
		this.allocationByDesc = allocationByDesc;
	}

	public Long getPsoCount() {
		return psoCount;
	}

	public void setPsoCount(Long psoCount) {
		this.psoCount = psoCount;
	}

	public Long getDmCount() {
		return dmCount;
	}

	public void setDmCount(Long dmCount) {
		this.dmCount = dmCount;
	}

	public Long getRmbCOunt() {
		return rmbCOunt;
	}

	public void setRmbCOunt(Long rmbCOunt) {
		this.rmbCOunt = rmbCOunt;
	}

	public List<String> getTeModel() {
		return teModel;
	}

	public void setTeModel(List<String> teModel) {
		this.teModel = teModel;
	}

	public List<String> getDmModel() {
		return dmModel;
	}

	public void setDmModel(List<String> dmModel) {
		this.dmModel = dmModel;
	}

	public List<String> getRmbModel() {
		return rmbModel;
	}

	public void setRmbModel(List<String> rmbModel) {
		this.rmbModel = rmbModel;
	}

	public List<String> getProductIds() {
		return productIds;
	}

	public void setProductIds(List<String> productIds) {
		this.productIds = productIds;
	}

	public String getAllocationCycle() {
		return allocationCycle;
	}

	public void setAllocationCycle(String allocationCycle) {
		this.allocationCycle = allocationCycle;
	}

	public String getAllocationSaveMode() {
		return allocationSaveMode;
	}

	public void setAllocationSaveMode(String allocationSaveMode) {
		this.allocationSaveMode = allocationSaveMode;
	}

	public String getAllocationId() {
		return allocationId;
	}

	public void setAllocationId(String allocationId) {
		this.allocationId = allocationId;
	}

	public String getAllocationMonthPeriodCode() {
		return allocationMonthPeriodCode;
	}

	public void setAllocationMonthPeriodCode(String allocationMonthPeriodCode) {
		this.allocationMonthPeriodCode = allocationMonthPeriodCode;
	}

	public String getFieldtype() {
		return fieldtype;
	}

	public void setFieldtype(String fieldtype) {
		this.fieldtype = fieldtype;
	}

	public String getAllocationMonth() {
		return allocationMonth;
	}

	public void setAllocationMonth(String allocationMonth) {
		this.allocationMonth = allocationMonth;
	}

	public String getAllocationMode() {
		return allocationMode;
	}

	public void setAllocationMode(String allocationMode) {
		this.allocationMode = allocationMode;
	}

	public String getPlanTypeDesc() {
		return planTypeDesc;
	}

	public void setPlanTypeDesc(String planTypeDesc) {
		this.planTypeDesc = planTypeDesc;
	}

	public String getFinYearId() {
		return finYearId;
	}

	public void setFinYearId(String finYearId) {
		this.finYearId = finYearId;
	}


//	public String getTeamName() {
//		return teamName;
//	}
//
//	public void setTeamName(String teamName) {
//		this.teamName = teamName;
//	}

	public String getCoreTeamId() {
		return coreTeamId;
	}

	public void setCoreTeamId(String coreTeamId) {
		this.coreTeamId = coreTeamId;
	}

	public List<String> getTmModel() {
		return tmModel;
	}

	public List<String> getSmModel() {
		return smModel;
	}

	public void setSmModel(List<String> smModel) {
		this.smModel = smModel;
	}

	public Long getTmqty() {
		return tmqty;
	}

	public void setTmqty(Long tmqty) {
		this.tmqty = tmqty;
	}

	public Long getSmqty() {
		return smqty;
	}

	public void setSmqty(Long smqty) {
		this.smqty = smqty;
	}

	public Long getMgr_id() {
		return mgr_id;
	}

	public void setMgr_id(Long mgr_id) {
		this.mgr_id = mgr_id;
	}

	public String getMgr_name() {
		return mgr_name;
	}

	public void setMgr_name(String mgr_name) {
		this.mgr_name = mgr_name;
	}

	public String getMgrEmpId() {
		return mgrEmpId;
	}

	public void setMgrEmpId(String mgrEmpId) {
		this.mgrEmpId = mgrEmpId;
	}

	public String getMonthOfUsePeriodName() {
		return monthOfUsePeriodName;
	}

	public void setMonthOfUsePeriodName(String monthOfUsePeriodName) {
		this.monthOfUsePeriodName = monthOfUsePeriodName;
	}

	public String getMonthOfUsePeriodCode() {
		return monthOfUsePeriodCode;
	}

	public void setMonthOfUsePeriodCode(String monthOfUsePeriodCode) {
		this.monthOfUsePeriodCode = monthOfUsePeriodCode;
	}

	public Long getPlan_qty() {
		return plan_qty;
	}

	public void setPlan_qty(Long plan_qty) {
		this.plan_qty = plan_qty;
	}

	public Long getAlloc_gen_ent_id() {
		return alloc_gen_ent_id;
	}

	public void setAlloc_gen_ent_id(Long alloc_gen_ent_id) {
		this.alloc_gen_ent_id = alloc_gen_ent_id;
	}

	public List<Long> getAlloc_gen_ent_ids() {
		return alloc_gen_ent_ids;
	}

	public void setAlloc_gen_ent_ids(List<Long> alloc_gen_ent_ids) {
		this.alloc_gen_ent_ids = alloc_gen_ent_ids;
	}

	public String getAllocationByName() {
		return allocationByName;
	}

	public void setAllocationByName(String allocationByName) {
		this.allocationByName = allocationByName;
	}

	public String getAllocDocNo() {
		return allocDocNo;
	}

	public void setAllocDocNo(String allocDocNo) {
		this.allocDocNo = allocDocNo;
	}

	public Long getSa_group_id() {
		return sa_group_id;
	}

	public void setSa_group_id(Long sa_group_id) {
		this.sa_group_id = sa_group_id;
	}

	public List<String> getBrandIds() {
		return brandIds;
	}

	public void setBrandIds(List<String> brandIds) {
		this.brandIds = brandIds;
	}

	public Map<String,String> getEnteredBrandsForAllocationBy() {
		return enteredBrandsForAllocationBy;
	}

	public void setEnteredBrandsForAllocationBy(Map<String,String> enteredBrandsForAllocationBy) {
		this.enteredBrandsForAllocationBy = enteredBrandsForAllocationBy;
	}

	public String getAllocationDate() {
		return allocationDate;
	}

	public void setAllocationDate(String allocationDate) {
		this.allocationDate = allocationDate;
	}

	public String getCompanyName() {
		return companyName;
	}

	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}

	public String getStaffName() {
		return staffName;
	}

	public void setStaffName(String staffName) {
		this.staffName = staffName;
	}

	public Long getStaffId() {
		return staffId;
	}

	public void setStaffId(Long staffId) {
		this.staffId = staffId;
	}


	public String getStaffMapCode() {
		return staffMapCode;
	}

	public void setStaffMapCode(String staffMapCode) {
		this.staffMapCode = staffMapCode;
	}

	public String getLevelCode() {
		return levelCode;
	}

	public void setLevelCode(String levelCode) {
		this.levelCode = levelCode;
	}

	public BigDecimal getStaffAllocatedQty() {
		return staffAllocatedQty;
	}

	public void setStaffAllocatedQty(BigDecimal staffAllocatedQty) {
		this.staffAllocatedQty = staffAllocatedQty;
	}

	public List<Long> getStaffAllocQtyModel() {
		return staffAllocQtyModel;
	}

	public void setStaffAllocQtyModel(List<Long> staffAllocQtyModel) {
		this.staffAllocQtyModel = staffAllocQtyModel;
	}

	public List<String> getStaffLevelModel() {
		return staffLevelModel;
	}

	public void setStaffLevelModel(List<String> staffLevelModel) {
		this.staffLevelModel = staffLevelModel;
	}

	public List<Long> getStaffIdModel() {
		return staffIdModel;
	}

	public void setStaffIdModel(List<Long> staffIdModel) {
		this.staffIdModel = staffIdModel;
	}

	public List<Long> getAllocGenDtlIdModel() {
		return allocGenDtlIdModel;
	}

	public void setAllocGenDtlIdModel(List<Long> allocGenDtlIdModel) {
		this.allocGenDtlIdModel = allocGenDtlIdModel;
	}

	public Long getAllocDetailId() {
		return allocDetailId;
	}

	public void setAllocDetailId(Long allocDetailId) {
		this.allocDetailId = allocDetailId;
	}

	public List<Long> getChangedIndexModel() {
		return changedIndexModel;
	}

	public void setChangedIndexModel(List<Long> changedIndexModel) {
		this.changedIndexModel = changedIndexModel;
	}

	public Long getIndex() {
		return index;
	}

	public void setIndex(Long index) {
		this.index = index;
	}

	public BigDecimal getTotalAllocQty() {
		return totalAllocQty;
	}

	public void setTotalAllocQty(BigDecimal totalAllocQty) {
		this.totalAllocQty = totalAllocQty;
	}

	public Long getDec() {
		return dec;
	}

	public void setDec(Long dec) {
		this.dec = dec;
	}

	public Long getJan() {
		return jan;
	}

	public void setJan(Long jan) {
		this.jan = jan;
	}

	public Long getFeb() {
		return feb;
	}

	public void setFeb(Long feb) {
		this.feb = feb;
	}

	public Long getMarch() {
		return march;
	}

	public void setMarch(Long march) {
		this.march = march;
	}

	public Long getApril() {
		return april;
	}

	public void setApril(Long april) {
		this.april = april;
	}

	public Long getMay() {
		return may;
	}

	public void setMay(Long may) {
		this.may = may;
	}

	public Long getJune() {
		return june;
	}

	public void setJune(Long june) {
		this.june = june;
	}

	public Long getJuly() {
		return july;
	}

	public void setJuly(Long july) {
		this.july = july;
	}

	public Long getAug() {
		return aug;
	}

	public void setAug(Long aug) {
		this.aug = aug;
	}

	public Long getSept() {
		return sept;
	}

	public void setSept(Long sept) {
		this.sept = sept;
	}

	public Long getOct() {
		return oct;
	}

	public void setOct(Long oct) {
		this.oct = oct;
	}

	public Long getNov() {
		return nov;
	}

	public void setNov(Long nov) {
		this.nov = nov;
	}

	public Long getDesc() {
		return desc;
	}

	public void setDesc(Long desc) {
		this.desc = desc;
	}

	public Long getAsp_dtl_id() {
		return asp_dtl_id;
	}

	public void setAsp_dtl_id(Long asp_dtl_id) {
		this.asp_dtl_id = asp_dtl_id;
	}

	public String getAsp_fin_year() {
		return asp_fin_year;
	}

	public void setAsp_fin_year(String asp_fin_year) {
		this.asp_fin_year = asp_fin_year;
	}

	public String getIndiaMarket() {
		return indiaMarket;
	}

	public void setIndiaMarket(String indiaMarket) {
		this.indiaMarket = indiaMarket;
	}

	public Long getTotalUnits() {
		return totalUnits;
	}

	public void setTotalUnits(Long totalUnits) {
		this.totalUnits = totalUnits;
	}

	public BigDecimal getAvgMonth() {
		return avgMonth;
	}

	public void setAvgMonth(BigDecimal avgMonth) {
		this.avgMonth = avgMonth;
	}

	public String getAsp_alloc_number() {
		return asp_alloc_number;
	}

	public void setAsp_alloc_number(String asp_alloc_number) {
		this.asp_alloc_number = asp_alloc_number;
	}

	public String getProd_name() {
		return prod_name;
	}

	public void setProd_name(String prod_name) {
		this.prod_name = prod_name;
	}

	public String getFilePath() {
		return filePath;
	}

	public void setFilePath(String filePath) {
		this.filePath = filePath;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getUser_role() {
		return user_role;
	}

	public void setUser_role(String user_role) {
		this.user_role = user_role;
	}

	public Long getActual_team_size() {
		return actual_team_size;
	}

	public void setActual_team_size(Long actual_team_size) {
		this.actual_team_size = actual_team_size;
	}

	public String getTeam_req_ind() {
		return team_req_ind;
	}

	public void setTeam_req_ind(String team_req_ind) {
		this.team_req_ind = team_req_ind;
	}

	public Long getSub_team_id() {
		return sub_team_id;
	}

	public void setSub_team_id(Long sub_team_id) {
		this.sub_team_id = sub_team_id;
	}

	public String getSub_team_code() {
		return sub_team_code;
	}

	public void setSub_team_code(String sub_team_code) {
		this.sub_team_code = sub_team_code;
	}



}
