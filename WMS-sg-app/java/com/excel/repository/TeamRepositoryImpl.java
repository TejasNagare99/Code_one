package com.excel.repository;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Query;
import javax.persistence.StoredProcedureQuery;
import javax.persistence.Tuple;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.excel.bean.GoapptiveTeamBrandbean;
import com.excel.model.FieldStaffList;
import com.excel.model.Team;
import com.excel.model.TerrMaster;

@Repository
public class TeamRepositoryImpl implements TeamRepository{
	@Autowired private EntityManagerFactory emf;
	
	@Override
	public List<Team> getTeamList(Long divId) throws Exception {
		EntityManager em = null;
		List<Team> list = new ArrayList<Team>();
		try {
			em = emf.createEntityManager();
			String q="Select team_id,team_name,team_code from team where div_id =:divId";
			Query query = em.createNativeQuery(q,Tuple.class);
			query.setParameter("divId", divId);
			List<Tuple> tuples = query.getResultList();			
			if (tuples != null && !tuples.isEmpty()) {
				for (Tuple t : tuples) {
					Team tm=new Team();
					tm.setTeam_id(Long.valueOf(t.get("team_id",Integer.class)));
					tm.setTeam_name(t.get("team_name",String.class));
					tm.setTeam_code(t.get("team_code",String.class));
					list.add(tm);
				}
				if(!list.isEmpty() && list.size() > 0)
					return list;
				}
			} finally {
				if (em != null) { em.close(); }
		}
		return list;
	}
	
	@Override
	public Team getTeamByDivisionAndTeamCode(Long divId,String team_code) throws Exception {
		EntityManager em = null;
		List<Team> list = new ArrayList<Team>();
		try {
			em = emf.createEntityManager();
			String q="Select team_id,team_name,team_code from team where div_id =:divId and team_code=:team_code";
			Query query = em.createNativeQuery(q,Tuple.class);
			query.setParameter("divId", divId);
			query.setParameter("team_code", team_code);
			List<Tuple> tuples = query.getResultList();			
			if (tuples != null && !tuples.isEmpty()) {
				for (Tuple t : tuples) {
					Team tm=new Team();
					tm.setTeam_id(Long.valueOf(t.get("team_id",Integer.class)));
					tm.setTeam_name(t.get("team_name",String.class));
					tm.setTeam_code(t.get("team_code",String.class));
					list.add(tm);
				}
				if(!list.isEmpty() && list.size() > 0)
					return list.get(0);
				}
			} finally {
				if (em != null) { em.close(); }
		}
		return null;
	}
	
	@Override
	public String getTeamReqInd(Long divId) throws Exception {
	    EntityManager em = null;
	    String teamReqInd = ""; 
	    try {
	        em = emf.createEntityManager();
	        String q = "SELECT TEAM_REQD_IND FROM div_master WHERE div_id = :divId";
	        Query query = em.createNativeQuery(q);
	        query.setParameter("divId", divId);
	        
	        Character result = (Character) query.getSingleResult();
	        if (result != null) {
	            teamReqInd = result.toString(); 
	            System.out.println("teamreq "+ teamReqInd);
	        }

	    } catch (Exception e) {
	        throw e;
	    } finally {
	        if (em != null) {
	            em.close();
	        }
	    }
	    
	    return teamReqInd; 
	}
	
	
}
