package com.excel.bean;

import java.util.Date;

public class FieldStaffBean {
	String company;
	String division;
	String team;
	String territoryCode;
	String territoryName;
	String terrId;
	String mapCode1;
	String fieldStaffCategory;
	String fsCategory;
	String trainingInd;
	String hierarchyLevel;
	String designation;
	
	String genericName;
	String fieldStaffName;
	String alternateName;
	String address1;
	String address2;
	String address3;
	String address4;
	String pinCode;
	String dispatchDestination;
	String state;
	String telephone;
	String mobile;
	String email;
	String employeeNo;
	String currentStatus;
	Date joiningDate;
	Date leavingDate;
	
	String transporter;
	String dispatchPsPi;
	String poolIndicator;
	String hq;
	String zone;
	
	String abm;
	String rbm;
	String zbm;
	String dsm;
	String sbuHead;
	String cfa1;
	String cfa2;
	String cfa3;
	
	String mgr1_id;
	String mgr1_name;
	String mgr2_id;
	String mgr2_name;
	String mgr3_id;
	String mgr3_name;
	String mgr4_id;
	String mgr4_name;
	String mgr5_id;
	String mgr5_name;
	String mgr6_id;
	String mgr6_name;
	
	String terr_designation;
	String terr_hq_id;
	String hq_name;
	String terr_districtcd;
	String terr_regioncd;
	String terr_zone_id;
	String zone_name;
	String terr_state_id;
	
	String terr_cfa_loc;
	String loc1;
	String terr_cfa_loc2;
	String loc2;
	String terr_cfa3_loc3;
	String loc3;

	String mapCode2;
	String city;
	String erpSystemCode;
	String panNo;
	String classs;
	String type;
	String districtCode;
	String regionCode;
	String zoneName;
	String productivity;
	String erpSupplyPlantCode;
	String fieldstaffType;
    String mclNo;
    String requestor;
    String productType[];
    private String mobile2;
    private String remark;
    
    private String teamcode;
    private Long allCount;
    private Long mobileEmptyCount;
    private Long mobileNonEmptyCount;
    	
	public Long getAllCount() {
		return allCount;
	}
	public Long getMobileEmptyCount() {
		return mobileEmptyCount;
	}
	public Long getMobileNonEmptyCount() {
		return mobileNonEmptyCount;
	}
	public void setAllCount(Long allCount) {
		this.allCount = allCount;
	}
	public void setMobileEmptyCount(Long mobileEmptyCount) {
		this.mobileEmptyCount = mobileEmptyCount;
	}
	public void setMobileNonEmptyCount(Long mobileNonEmptyCount) {
		this.mobileNonEmptyCount = mobileNonEmptyCount;
	}
    
	
	public String getTeamcode() {
		return teamcode;
	}
	public void setTeamcode(String teamcode) {
		this.teamcode = teamcode;
	}
	public String getCompany() {
		return company;
	}
	public void setCompany(String company) {
		this.company = company;
	}
	public String getDivision() {
		return division;
	}
	public void setDivision(String division) {
		this.division = division;
	}
	public String getTeam() {
		return team;
	}
	public void setTeam(String team) {
		this.team = team;
	}
	public String getMapCode1() {
		return mapCode1;
	}
	public void setMapCode1(String mapCode1) {
		this.mapCode1 = mapCode1;
	}
	public String getFieldStaffCategory() {
		return fieldStaffCategory;
	}
	public void setFieldStaffCategory(String fieldStaffCategory) {
		this.fieldStaffCategory = fieldStaffCategory;
	}
	
	
	public String getFsCategory() {
		return fsCategory;
	}
	public void setFsCategory(String fsCategory) {
		this.fsCategory = fsCategory;
	}
	public String getTrainingInd() {
		return trainingInd;
	}
	public void setTrainingInd(String trainingInd) {
		this.trainingInd = trainingInd;
	}
	public String getHierarchyLevel() {
		return hierarchyLevel;
	}
	public void setHierarchyLevel(String hierarchyLevel) {
		this.hierarchyLevel = hierarchyLevel;
	}
	public String getGenericName() {
		return genericName;
	}
	public void setGenericName(String genericName) {
		this.genericName = genericName;
	}
	public String getFieldStaffName() {
		return fieldStaffName;
	}
	public void setFieldStaffName(String fieldStaffName) {
		this.fieldStaffName = fieldStaffName;
	}
	public String getAlternateName() {
		return alternateName;
	}
	public void setAlternateName(String alternateName) {
		this.alternateName = alternateName;
	}
	public String getAddress1() {
		return address1;
	}
	public void setAddress1(String address1) {
		this.address1 = address1;
	}
	public String getAddress2() {
		return address2;
	}
	public void setAddress2(String address2) {
		this.address2 = address2;
	}
	public String getAddress3() {
		return address3;
	}
	public void setAddress3(String address3) {
		this.address3 = address3;
	}
	public String getAddress4() {
		return address4;
	}
	public void setAddress4(String address4) {
		this.address4 = address4;
	}
	public String getPinCode() {
		return pinCode;
	}
	public void setPinCode(String pinCode) {
		this.pinCode = pinCode;
	}
	public String getDispatchDestination() {
		return dispatchDestination;
	}
	public void setDispatchDestination(String dispatchDestination) {
		this.dispatchDestination = dispatchDestination;
	}
	public String getState() {
		return state;
	}
	public void setState(String state) {
		this.state = state;
	}
	public String getTelephone() {
		return telephone;
	}
	public void setTelephone(String telephone) {
		this.telephone = telephone;
	}
	public String getMobile() {
		return mobile;
	}
	public void setMobile(String mobile) {
		this.mobile = mobile;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getEmployeeNo() {
		return employeeNo;
	}
	public void setEmployeeNo(String employeeNo) {
		this.employeeNo = employeeNo;
	}
	public String getCurrentStatus() {
		return currentStatus;
	}
	public void setCurrentStatus(String currentStatus) {
		this.currentStatus = currentStatus;
	}
	
	public Date getJoiningDate() {
		return joiningDate;
	}
	public void setJoiningDate(Date joiningDate) {
		this.joiningDate = joiningDate;
	}
	public Date getLeavingDate() {
		return leavingDate;
	}
	public void setLeavingDate(Date leavingDate) {
		this.leavingDate = leavingDate;
	}
	public String getDesignation() {
		return designation;
	}
	public void setDesignation(String designation) {
		this.designation = designation;
	}
	public String getTransporter() {
		return transporter;
	}
	public void setTransporter(String transporter) {
		this.transporter = transporter;
	}
	public String getDispatchPsPi() {
		return dispatchPsPi;
	}
	public void setDispatchPsPi(String dispatchPsPi) {
		this.dispatchPsPi = dispatchPsPi;
	}
	public String getPoolIndicator() {
		return poolIndicator;
	}
	public void setPoolIndicator(String poolIndicator) {
		this.poolIndicator = poolIndicator;
	}
	public String getHq() {
		return hq;
	}
	public void setHq(String hq) {
		this.hq = hq;
	}
	public String getZone() {
		return zone;
	}
	public void setZone(String zone) {
		this.zone = zone;
	}
	
	public String getMapCode2() {
		return mapCode2;
	}
	public void setMapCode2(String mapCode2) {
		this.mapCode2 = mapCode2;
	}
	public String getCity() {
		return city;
	}
	public void setCity(String city) {
		this.city = city;
	}
	public String getErpSystemCode() {
		return erpSystemCode;
	}
	public void setErpSystemCode(String erpSystemCode) {
		this.erpSystemCode = erpSystemCode;
	}
	public String getPanNo() {
		return panNo;
	}
	public void setPanNo(String panNo) {
		this.panNo = panNo;
	}
	public String getClasss() {
		return classs;
	}
	public void setClasss(String classs) {
		this.classs = classs;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getDistrictCode() {
		return districtCode;
	}
	public void setDistrictCode(String districtCode) {
		this.districtCode = districtCode;
	}
	public String getRegionCode() {
		return regionCode;
	}
	public void setRegionCode(String regionCode) {
		this.regionCode = regionCode;
	}
	public String getZoneName() {
		return zoneName;
	}
	public void setZoneName(String zoneName) {
		this.zoneName = zoneName;
	}
	public String getProductivity() {
		return productivity;
	}
	public void setProductivity(String productivity) {
		this.productivity = productivity;
	}
	public String getErpSupplyPlantCode() {
		return erpSupplyPlantCode;
	}
	public void setErpSupplyPlantCode(String erpSupplyPlantCode) {
		this.erpSupplyPlantCode = erpSupplyPlantCode;
	}
	public String getFieldstaffType() {
		return fieldstaffType;
	}
	public void setFielstaffType(String fieldstaffType) {
		this.fieldstaffType = fieldstaffType;
	}
	public String getMclNo() {
		return mclNo;
	}
	public void setMclNo(String mclNo) {
		this.mclNo = mclNo;
	}
	public String getRequestor() {
		return requestor;
	}
	public void setRequestor(String requestor) {
		this.requestor = requestor;
	}
	public String[] getProductType() {
		return productType;
	}
	public void setProductType(String[] productType) {
		this.productType = productType;
	}
	public String getMgr1_id() {
		return mgr1_id;
	}
	public void setMgr1_id(String mgr1_id) {
		this.mgr1_id = mgr1_id;
	}
	public String getMgr1_name() {
		return mgr1_name;
	}
	public void setMgr1_name(String mgr1_name) {
		this.mgr1_name = mgr1_name;
	}
	public String getMgr2_id() {
		return mgr2_id;
	}
	public void setMgr2_id(String mgr2_id) {
		this.mgr2_id = mgr2_id;
	}
	public String getMgr2_name() {
		return mgr2_name;
	}
	public void setMgr2_name(String mgr2_name) {
		this.mgr2_name = mgr2_name;
	}
	public String getMgr3_id() {
		return mgr3_id;
	}
	public void setMgr3_id(String mgr3_id) {
		this.mgr3_id = mgr3_id;
	}
	public String getMgr3_name() {
		return mgr3_name;
	}
	public void setMgr3_name(String mgr3_name) {
		this.mgr3_name = mgr3_name;
	}
	public String getMgr4_id() {
		return mgr4_id;
	}
	public void setMgr4_id(String mgr4_id) {
		this.mgr4_id = mgr4_id;
	}
	public String getMgr4_name() {
		return mgr4_name;
	}
	public void setMgr4_name(String mgr4_name) {
		this.mgr4_name = mgr4_name;
	}
	public String getMgr5_id() {
		return mgr5_id;
	}
	public void setMgr5_id(String mgr5_id) {
		this.mgr5_id = mgr5_id;
	}
	public String getMgr5_name() {
		return mgr5_name;
	}
	public void setMgr5_name(String mgr5_name) {
		this.mgr5_name = mgr5_name;
	}
	public String getMgr6_id() {
		return mgr6_id;
	}
	public void setMgr6_id(String mgr6_id) {
		this.mgr6_id = mgr6_id;
	}
	public String getMgr6_name() {
		return mgr6_name;
	}
	public void setMgr6_name(String mgr6_name) {
		this.mgr6_name = mgr6_name;
	}
	public void setFieldstaffType(String fieldstaffType) {
		this.fieldstaffType = fieldstaffType;
	}
	public String getTerritoryCode() {
		return territoryCode;
	}
	public void setTerritoryCode(String territoryCode) {
		this.territoryCode = territoryCode;
	}
	public String getTerrId() {
		return terrId;
	}
	public void setTerrId(String terrId) {
		this.terrId = terrId;
	}
	public String getAbm() {
		return abm;
	}
	public void setAbm(String abm) {
		this.abm = abm;
	}
	public String getRbm() {
		return rbm;
	}
	public void setRbm(String rbm) {
		this.rbm = rbm;
	}
	public String getZbm() {
		return zbm;
	}
	public void setZbm(String zbm) {
		this.zbm = zbm;
	}
	public String getDsm() {
		return dsm;
	}
	public void setDsm(String dsm) {
		this.dsm = dsm;
	}
	public String getSbuHead() {
		return sbuHead;
	}
	public void setSbuHead(String sbuHead) {
		this.sbuHead = sbuHead;
	}
	public String getTerr_designation() {
		return terr_designation;
	}
	public void setTerr_designation(String terr_designation) {
		this.terr_designation = terr_designation;
	}
	public String getTerr_hq_id() {
		return terr_hq_id;
	}
	public void setTerr_hq_id(String terr_hq_id) {
		this.terr_hq_id = terr_hq_id;
	}
	public String getHq_name() {
		return hq_name;
	}
	public void setHq_name(String hq_name) {
		this.hq_name = hq_name;
	}
	public String getTerr_districtcd() {
		return terr_districtcd;
	}
	public void setTerr_districtcd(String terr_districtcd) {
		this.terr_districtcd = terr_districtcd;
	}
	public String getTerr_regioncd() {
		return terr_regioncd;
	}
	public void setTerr_regioncd(String terr_regioncd) {
		this.terr_regioncd = terr_regioncd;
	}
	public String getTerr_zone_id() {
		return terr_zone_id;
	}
	public void setTerr_zone_id(String terr_zone_id) {
		this.terr_zone_id = terr_zone_id;
	}
	public String getTerr_state_id() {
		return terr_state_id;
	}
	public void setTerr_state_id(String terr_state_id) {
		this.terr_state_id = terr_state_id;
	}
	public String getTerr_cfa_loc() {
		return terr_cfa_loc;
	}
	public void setTerr_cfa_loc(String terr_cfa_loc) {
		this.terr_cfa_loc = terr_cfa_loc;
	}
	public String getLoc1() {
		return loc1;
	}
	public void setLoc1(String loc1) {
		this.loc1 = loc1;
	}
	public String getTerr_cfa_loc2() {
		return terr_cfa_loc2;
	}
	public void setTerr_cfa_loc2(String terr_cfa_loc2) {
		this.terr_cfa_loc2 = terr_cfa_loc2;
	}
	public String getLoc2() {
		return loc2;
	}
	public void setLoc2(String loc2) {
		this.loc2 = loc2;
	}
	public String getTerr_cfa3_loc3() {
		return terr_cfa3_loc3;
	}
	public void setTerr_cfa3_loc3(String terr_cfa3_loc3) {
		this.terr_cfa3_loc3 = terr_cfa3_loc3;
	}
	public String getLoc3() {
		return loc3;
	}
	public void setLoc3(String loc3) {
		this.loc3 = loc3;
	}
	public String getCfa1() {
		return cfa1;
	}
	public void setCfa1(String cfa1) {
		this.cfa1 = cfa1;
	}
	public String getCfa2() {
		return cfa2;
	}
	public void setCfa2(String cfa2) {
		this.cfa2 = cfa2;
	}
	public String getCfa3() {
		return cfa3;
	}
	public void setCfa3(String cfa3) {
		this.cfa3 = cfa3;
	}
	public String getZone_name() {
		return zone_name;
	}
	public void setZone_name(String zone_name) {
		this.zone_name = zone_name;
	}
	public String getTerritoryName() {
		return territoryName;
	}
	public void setTerritoryName(String territoryName) {
		this.territoryName = territoryName;
	}
	public String getMobile2() {
		return mobile2;
	}
	public void setMobile2(String mobile2) {
		this.mobile2 = mobile2;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	
	
	
}
