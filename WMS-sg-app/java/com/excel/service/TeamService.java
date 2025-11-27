package com.excel.service;

import java.util.List;

import com.excel.model.Team;

public interface TeamService{

	List<Team> getTeamList(Long divId) throws Exception;
	
	String getTeamReqInd(Long divId) throws Exception;

}
