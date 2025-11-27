package com.excel.repository;

import java.util.List;

import javax.persistence.Tuple;

import com.excel.bean.ProductBean;
import com.excel.bean.TeamBrandMappingBean;
import com.excel.model.DIV_TEAM_BRAND;
import com.excel.model.DivMaster;
import com.excel.model.Team;

public interface TeamBrandMappingRepository {
	List<Team> getTeamCodeData(String divId) throws Exception;
	List<DivMaster> getDivIdList() throws Exception;
	List<Long> getSelectedBrandList(String empId, String divId, String teamId)throws Exception;
	List<Team> getSelectedTeamId(String divId) throws Exception;
	List<TeamBrandMappingBean> getTeamBrandReportdata(TeamBrandMappingBean bean) throws Exception;
	List<TeamBrandMappingBean> getTeamCodeDataForTeamBrandMapping(String divId) throws Exception;
	Team getTeamCodeByTeamId(String teamId) throws Exception;
	String getTeamCodeDataForTeamMaster(String divId)throws Exception;
	List<Team> getTeamNamesForTeamMaster(String divId)throws Exception;
//	List<Tuple> getDuplicateTeamCode(TeamBrandMappingBean bean) throws Exception;
	Team getSelectedObjectByTeamId(Long teamId) throws Exception;
	List<Tuple> getDuplicateTeamCode(String divId, String team_code) throws Exception;
	List<Tuple> getDuplicateTeamName(String divId, String team_name) throws Exception;
	List<Tuple> getDuplicateTeamShortName(String divId, String shortTeamName) throws Exception;
	boolean checkTeamcode(String Teamcode) throws Exception;
	
	public List<TeamBrandMappingBean> getBrandsForTeamBrandMapping(String team_code)throws Exception;
	public List<ProductBean> getProductListProductMaster(String divId) throws Exception;
	List<DivMaster> getDivIdListproductDivMapping() throws Exception;
}
