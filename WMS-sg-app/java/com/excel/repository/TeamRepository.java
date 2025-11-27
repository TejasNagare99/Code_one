package com.excel.repository;

import java.util.List;

import com.excel.model.Team;

public interface TeamRepository{

	List<Team> getTeamList(Long divId) throws Exception;

	Team getTeamByDivisionAndTeamCode(Long divId, String team_code) throws Exception;

	String getTeamReqInd(Long divId) throws Exception;

	
}
