package com.excel.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.excel.bean.GoapptiveTeamBrandbean;
import com.excel.model.Team;
import com.excel.repository.TeamRepository;

@Service
public class TeamServiceImpl implements TeamService{
	@Autowired TeamRepository teamRepository;
	
	@Override
	public List<Team> getTeamList(Long divId) throws Exception {
		return this.teamRepository.getTeamList(divId);
	}
	
	@Override
	public String getTeamReqInd(Long divId) throws Exception {
		return this.teamRepository.getTeamReqInd(divId);
	}

}
