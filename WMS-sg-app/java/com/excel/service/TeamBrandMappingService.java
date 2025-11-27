package com.excel.service;

import java.util.List;

import javax.persistence.Tuple;

import com.excel.bean.ProductBean;
import com.excel.bean.TeamBrandMappingBean;
import com.excel.exception.MedicoException;
import com.excel.model.DIV_TEAM_BRAND;
import com.excel.model.DivMaster;
import com.excel.model.Team;

public interface TeamBrandMappingService {

	public DIV_TEAM_BRAND saveTeamData(TeamBrandMappingBean bean, String companyname, String userId, String ip_addr)throws Exception;
	List<Team> getTeamCodeData(String divId) throws Exception;
	List<DivMaster> getDivIdList() throws Exception;
	List<Long> getSelectedBrandList(String empId, String divId, String teamId)throws Exception;
	List<Team> getSelectedTeamId(String divId) throws Exception;
	public List<TeamBrandMappingBean> getTeamBrandReportdata(TeamBrandMappingBean bean) throws Exception;
	public String generate_team_brand_Report(List<TeamBrandMappingBean> lst, String company,String empId) throws Exception;
	public List<TeamBrandMappingBean> getTeamCodeDataForTeamBrandMapping(String divId)throws Exception;
	public Team getTeamCodeByTeamId(String teamId) throws Exception;
	public String getTeamCodeDataForTeamMaster(String divId) throws Exception;
	public String saveTeamMasterData(TeamBrandMappingBean bean) throws Exception,MedicoException;
	public List<Team> getTeamNamesForTeamMaster(String divId) throws Exception;
	public List<Tuple> getDuplicateTeamCode(String divId, String team_code) throws Exception;
	public List<Tuple> getDuplicateTeamName(String divId, String team_name)throws Exception;
	public List<Tuple> getDuplicateTeamShortName(String divId, String shortTeamName)throws Exception;
	
	public List<TeamBrandMappingBean> getBrandsForTeamBrandMapping(String team_code)throws Exception;
	public List<ProductBean> getProductListProductMaster(String divId)throws Exception;
	public List<DivMaster> getDivIdListproductDivMapping()throws Exception;
	
}
