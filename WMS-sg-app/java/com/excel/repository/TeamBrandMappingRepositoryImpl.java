package com.excel.repository;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.Tuple;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.excel.bean.AllocationBean;
import com.excel.bean.ProductBean;
import com.excel.bean.TeamBrandMappingBean;
import com.excel.model.DIV_TEAM_BRAND;
import com.excel.model.DivMaster;
import com.excel.model.Team;
import com.excel.utility.CodifyErrors;
import com.excel.utility.MedicoConstants;
@Repository
public class TeamBrandMappingRepositoryImpl implements TeamBrandMappingRepository,MedicoConstants{

	@Autowired EntityManagerFactory emf;
	@PersistenceContext private EntityManager entityManager;
	@Override
	public List<Team> getTeamCodeData(String divId) throws Exception {
		// TODO Auto-generated method stub
		EntityManager em = null;
		List<Team> teamList = new ArrayList<Team>();
		try {
			em = emf.createEntityManager();
			StringBuffer sb = new StringBuffer();
			sb.append(" SELECT  team_name,team_id,team_code FROM TEAM");
			//sb.append(" SELECT  MAX(team_code) as LAST_TEAM_CODE FROM TEAM ");
			Query query = em.createNativeQuery(sb.toString(), Tuple.class);
		//	query.setParameter("divId", divId);
			List<Tuple> tuples = query.getResultList();
			if (tuples != null && !tuples.isEmpty()) {
				for(Tuple t : tuples) {
					Team dm=new Team();
					dm.setTeam_name(t.get("team_name", String.class));
					dm.setTeam_id(Long.valueOf(t.get("team_id", Integer.class)));
					dm.setTeam_code(t.get("team_code", String.class));
					teamList.add(dm);
				}
				System.out.println("teamCode::"+teamList);
			}		
		}
		catch(Exception e) {
			throw e;
		}
		 finally {
				if (em != null) { em.close(); }
			}
		
		return teamList;
	}

	@Override
	public List<DivMaster> getDivIdList() throws Exception {
		EntityManager em = null;
		List<DivMaster> divList = new ArrayList<DivMaster>();
		try {
			em = emf.createEntityManager();
			StringBuffer sb = new StringBuffer();
			sb.append("select div_code,div_disp_nm,div_id,div_code_nm,div_status,div_map_cd,div_short_nm,team_reqd_ind from  DIV_MASTER  ");
			sb.append("WHERE  DIV_status = 'A' AND team_reqd_ind= 'Y'");
			sb.append("order by div_disp_nm "); 
			Query query = em.createNativeQuery(sb.toString(), Tuple.class);
		//	query.setParameter("emp_id", userId);
			List<Tuple> tuples = query.getResultList();
			if (tuples != null && !tuples.isEmpty()) {
				for(Tuple t : tuples) {
					DivMaster dm=new DivMaster();
					dm.setDiv_code(t.get("div_code", String.class));
					dm.setDiv_id(Long.valueOf(t.get("div_id", Integer.class)));
					dm.setDiv_disp_nm(t.get("div_disp_nm", String.class));
					dm.setDiv_map_cd(t.get("div_map_cd", String.class));
					dm.setDiv_status(t.get("div_status", Character.class).toString());
					dm.setDiv_short_nm(t.get("div_short_nm", String.class));
					dm.setTeam_reqd_ind(t.get("team_reqd_ind",Character.class).toString());
					divList.add(dm);
				}
			}
		} catch (Exception e) {
			// TODO: handle exception
			throw e;
		}finally {
			if (em != null) { em.close(); }
		}
	
		return divList;
	}

	@Override
	public List<Long> getSelectedBrandList(String empId, String divId,String teamId) throws Exception {
		List<Long> list=new ArrayList<>();
		try{
			StringBuffer buffer = new StringBuffer();
			buffer.append("select distinct sa_prod_group from DIV_TEAM_BRAND where  sa_ins_usr_id=:empId  and sa_status='A' and team_id=:teamId");
			Query query = entityManager.createNativeQuery(buffer.toString(),Tuple.class);
			query.setParameter("empId", empId);
			//query.setParameter("divId", divId);
			query.setParameter("teamId", teamId);
			System.out.println("teamId:::"+teamId);
			List<Tuple> tuples = query.getResultList();
			if (tuples != null && !tuples.isEmpty()) {
				for(Tuple t : tuples) {
					list.add(Long.valueOf(t.get("sa_prod_group", Integer.class)));
				}
				System.out.println("list::"+list.size());	
			}	
		}catch(Exception e){
			throw e;
		}
		return list;
	}

	@Override
	public List<Team> getSelectedTeamId(String divId) throws Exception {
		// TODO Auto-generated method stub
		EntityManager em = null;
		List<Team> teamList = new ArrayList<Team>();
		try {
			em = emf.createEntityManager();
			StringBuffer sb = new StringBuffer();
			sb.append(" SELECT MAX(team_id) as team_id  FROM TEAM WHERE div_id=:divId ");
			//sb.append(" SELECT  MAX(team_code) as LAST_TEAM_CODE FROM TEAM ");
			Query query = em.createNativeQuery(sb.toString(), Tuple.class);
			query.setParameter("divId", divId);
			//query.setParameter("team_code", team_code);
			List<Tuple> tuples = query.getResultList();
			if (tuples != null && !tuples.isEmpty()) {
				for(Tuple t : tuples) {
					Team dm=new Team();
					dm.setTeam_id(Long.valueOf(t.get("team_id", Integer.class)));
					teamList.add(dm);
				}
				System.out.println("teamCode::"+teamList.get(0).getTeam_id());
			}		
		}
		catch(Exception e) {
			throw e;
		}
		 finally {
				if (em != null) { em.close(); }
			}
		
		return teamList;
	}

	@Override
	public List<TeamBrandMappingBean> getTeamBrandReportdata(TeamBrandMappingBean bean) throws Exception {
	//	String company_cd="FRS5424";
		EntityManager em = null; 
		List<TeamBrandMappingBean> list = null;
		try {
			System.out.println("bean.getDivId()::"+bean.getDivId());
			System.out.println("bean.getTeam_id()::"+bean.getTeamCodeList());
			
			StringBuffer sb = new StringBuffer();
		//	sb.append("select ROW_NUMBER() OVER(ORDER BY dtb.div_id ASC,dtb.team_id ASC,SGP.sa_group_name ASC) AS Row, dtb.fin_year_id fin_year,dtb.company company, dv.div_disp_nm division, ");
			sb.append(" select ROW_NUMBER() OVER(ORDER BY dV.div_disp_nm ASC,dtb.team_id ASC,SGP.sa_group_name ASC) AS Row, dtb.fin_year_id fin_year,dtb.company , dv.div_disp_nm division,");
			sb.append(" tm.team_code team_code,tm.team_name team_name,UPPER(sgp.sa_group_name) sa_group_name,case when dtb.sa_status='A' then 'Active' else 'Inactive' end status ");
			sb.append(" from DIV_TEAM_BRAND dtb");
			sb.append(" left join div_master dv on dv.div_id=dtb.div_id");
			sb.append(" left join team tm on tm.team_id=dtb.team_id");
			sb.append(" left join SAPRODGRP sgp on sgp.sa_prod_group=dtb.sa_prod_group");
			sb.append(" WHERE DV.DIV_ID=:divId AND DTB.TEAM_ID IN (:teamid) ");
			Query query = entityManager.createNativeQuery(sb.toString(), Tuple.class);
			query.setParameter("divId", bean.getDivId());
			query.setParameter("teamid", bean.getTeamCodeList());
			List<Tuple> tuples = query.getResultList();
			if (tuples != null && !tuples.isEmpty()) {
				list = new ArrayList<>();
				TeamBrandMappingBean object = null;
				for (Tuple t : tuples) {
					object = new TeamBrandMappingBean();
					
				object.setFinYears(t.get("fin_year",String.class));
				object.setCompany(t.get("company",String.class));
				object.setDivision(t.get("division",String.class));
				object.setTeam_code(t.get("team_code",String.class));
				object.setTeam_name(t.get("team_name",String.class));
				object.setSa_group_name(t.get("sa_group_name",String.class));
				object.setStatus(t.get("status",String.class));
				list.add(object);
				}
			
			if(list!=null && list.size() > 0) {
				System.out.println("list:::"+list.size());
				return list;
			}
			}
			
		}
		catch(Exception e) {
			throw e;
		}
		return null;

}

	@Override
	public List<TeamBrandMappingBean> getTeamCodeDataForTeamBrandMapping(String divId) throws Exception {
		// TODO Auto-generated method stub
		EntityManager em = null;
		List<TeamBrandMappingBean> teamList = new ArrayList<TeamBrandMappingBean>();
		try {
			em = emf.createEntityManager();
			StringBuffer sb = new StringBuffer();
			sb.append(" select distinct dtb.team_id,tm.team_code from DIV_TEAM_BRAND dtb");
			sb.append(" join team tm on tm.team_id=dtb.team_id");
			sb.append(" where dtb.div_id=:divId ");
			sb.append(" order by tm.team_code");
			
			
			Query query = em.createNativeQuery(sb.toString(), Tuple.class);
			query.setParameter("divId", divId);
			List<Tuple> tuples = query.getResultList();
			if (tuples != null && !tuples.isEmpty()) {
				for(Tuple t : tuples) {
					TeamBrandMappingBean dm=new TeamBrandMappingBean();
					
					dm.setTeam_id(Long.valueOf(t.get("team_id", Integer.class)));
					dm.setTeam_code(t.get("team_code", String.class));
					teamList.add(dm);
				}
				System.out.println("teamCode::"+teamList);
			}		
		}
		catch(Exception e) {
			throw e;
		}
		 finally {
				if (em != null) { em.close(); }
			}
		
		return teamList;
	}

	@Override
	public Team getTeamCodeByTeamId(String teamId) throws Exception {
		// TODO Auto-generated method stub
		EntityManager em = null;
		List<Team> list = new ArrayList<Team>();
		try {
			em = emf.createEntityManager();
			Query query = null;
			String q="from Team WHERE team_id = :id";
			query = em.createQuery(q);
			query.setParameter("id", Long.valueOf(teamId));
			list=query.getResultList();
			
			if(list!=null && list.size()>0) {
				return list.get(0);
			}
		}
		catch (Exception e) {
			// TODO: handle exception
			throw e;
		}
		finally {
			if (em != null) { em.close(); }
		}
		return null;
	}
	
	@Override
	public String getTeamCodeDataForTeamMaster(String divId) throws Exception {
		// TODO Auto-generated method stub
		EntityManager em = null;
		List<Team> teamList = new ArrayList<Team>();
		try {
			em = emf.createEntityManager();
			StringBuffer sb = new StringBuffer();
			sb.append(" SELECT  MAX(team_code) as LAST_TEAM_CODE FROM TEAM WHERE div_id=:divId");
			Query query = em.createNativeQuery(sb.toString(), Tuple.class);
			query.setParameter("divId", divId);
			List<Tuple> tuples = query.getResultList();
			if (tuples != null && !tuples.isEmpty()) {
				for(Tuple t : tuples) {
					Team dm=new Team();
					dm.setTeam_code(t.get("LAST_TEAM_CODE", String.class));
					teamList.add(dm);
				}
				System.out.println("teamCode::"+teamList.get(0).getTeam_code());
			}		
		}
		catch(Exception e) {
			throw e;
		}
		 finally {
				if (em != null) { em.close(); }
			}
		
		return teamList.get(0).getTeam_code();
	}

	@Override
	public List<Team> getTeamNamesForTeamMaster(String divId) throws Exception {
		// TODO Auto-generated method stub
		EntityManager em = null;
		List<Team> teamList = new ArrayList<Team>();
		try {
			em = emf.createEntityManager();
			StringBuffer sb = new StringBuffer();
			sb.append(" SELECT team_id,team_name,team_code,team_shortname,team_status FROM TEAM WHERE div_id=:divId");
			Query query = em.createNativeQuery(sb.toString(), Tuple.class);
			query.setParameter("divId", divId);
			List<Tuple> tuples = query.getResultList();
			if (tuples != null && !tuples.isEmpty()) {
				for(Tuple t : tuples) {
					Team dm=new Team();
					dm.setTeam_id(Long.valueOf(t.get("team_id", Integer.class)));
					dm.setTeam_name(t.get("team_name", String.class));
					dm.setTeam_code(t.get("team_code", String.class));
					dm.setTeam_shortname(t.get("team_shortname", String.class));
					dm.setTeam_status(t.get("team_status", Character.class).toString());
					teamList.add(dm);
				}
				System.out.println("teamCode::"+teamList);
			}		
		}
		catch(Exception e) {
			throw e;
		}
		 finally {
				if (em != null) { em.close(); }
			}
		
		return teamList;
	}

	@Override
	public List<Tuple> getDuplicateTeamCode(String divId,String Team_code) throws Exception {
		EntityManager em = null;
		List<Tuple> list = null;
		try {
			em = emf.createEntityManager();
			System.out.println("team_code::"+Team_code);
			System.out.println("div_id::"+divId);
			StringBuffer sb = new StringBuffer();
			sb.append(" select team_code from team where team_code=:team_code");
			Query query = em.createNativeQuery(sb.toString(), Tuple.class);
			query.setParameter("team_code", Team_code);
	//		query.setParameter("div_id", divId);
			list = query.getResultList();

		} finally {
			if (em != null) {
				em.close();
			}
		}
		return list;
	}

	@Override
	public Team getSelectedObjectByTeamId(Long teamId) throws Exception {
		// TODO Auto-generated method stub
		EntityManager em = null;
		List<Team> teamList = new ArrayList<Team>();
		try {
			em = emf.createEntityManager();
			StringBuffer sb = new StringBuffer();
			sb.append(" select team_id,team_code,team_name,team_shortname,div_id,team_status from Team WHERE team_id = :teamId");
			Query query = em.createNativeQuery(sb.toString(), Tuple.class);
			query.setParameter("teamId", teamId);
			List<Tuple> tuples = query.getResultList();//data update aya he v
			if (tuples != null && !tuples.isEmpty()) {
				for(Tuple t : tuples) {
					Team dm=new Team();
					dm.setTeam_id(Long.valueOf(t.get("team_id", Integer.class)));
					dm.setTeam_name(t.get("team_name", String.class));
					dm.setTeam_code(t.get("team_code", String.class));
					dm.setTeam_shortname(t.get("team_shortname", String.class));
					dm.setDiv_id(Long.valueOf(t.get("div_id", Integer.class)));
					dm.setTeam_status(t.get("team_status", Character.class).toString());
					teamList.add(dm);
				}
				System.out.println("teamCode::"+teamList.get(0).getTeam_name());
			}		
		}
		catch(Exception e) {
			throw e;
		}
		 finally {
				if (em != null) { em.close(); }
			}
		
		return teamList.get(0);
	}

	@Override
	public List<Tuple> getDuplicateTeamName(String divId, String team_name) throws Exception {
		EntityManager em = null;
		List<Tuple> list = null;
		try {
			em = emf.createEntityManager();
			System.out.println("team_name::"+team_name);
			System.out.println("div_id::"+divId);
			StringBuffer sb = new StringBuffer();
			sb.append(" select team_name from team where team_name=:team_name and DIV_ID=:div_id");
			Query query = em.createNativeQuery(sb.toString(), Tuple.class);
			query.setParameter("team_name", team_name);
			query.setParameter("div_id", divId);
			list = query.getResultList();

		} finally {
			if (em != null) {
				em.close();
			}
		}
		return list;
	}

	@Override
	public List<Tuple> getDuplicateTeamShortName(String divId, String shortTeamName) throws Exception {
		EntityManager em = null;
		List<Tuple> list = null;
		try {
			em = emf.createEntityManager();
			System.out.println("shortTeamName::"+shortTeamName);
			System.out.println("div_id::"+divId);
			StringBuffer sb = new StringBuffer();
			sb.append(" select team_shortname from team where team_shortname=:shortTeamName and DIV_ID=:div_id");
			Query query = em.createNativeQuery(sb.toString(), Tuple.class);
			query.setParameter("shortTeamName", shortTeamName);
			query.setParameter("div_id", divId);
			list = query.getResultList();

		} finally {
			if (em != null) {
				em.close();
			}
		}
		return list;
	}

	@Override
	public boolean checkTeamcode(String Teamcode) throws Exception {
		// TODO Auto-generated method stub
		EntityManager em = null;
		List<Team> teamList = new ArrayList<Team>();
		try {
			em = emf.createEntityManager();
			StringBuffer sb = new StringBuffer();
			sb.append(" SELECT team_code FROM TEAM WHERE team_code=:Teamcode");
			
			Query query = em.createNativeQuery(sb.toString(), Tuple.class);
			query.setParameter("Teamcode", Teamcode);
			List<Tuple> tuples = query.getResultList();

			if(tuples!=null && tuples.size()>0) {
				return true;
			}
		}
		catch(Exception e) {
			throw e;
		}
		 finally {
				if (em != null) { em.close(); }
			}
		
		return false;
	}

	@Override
	public List<TeamBrandMappingBean> getBrandsForTeamBrandMapping(String team_code) throws Exception {
		// TODO Auto-generated method stub
		EntityManager em = null;
		List<TeamBrandMappingBean> teamList = new ArrayList<TeamBrandMappingBean>();
		try {
			em = emf.createEntityManager();
			StringBuffer sb = new StringBuffer();
			sb.append("SELECT DTL.SA_PROD_GROUP as SA_PROD_GROUP,UPPER(SA_GROUP_NAME) as SA_GROUP_NAME,DTL.TEAM_ID as TEAM_ID");
			sb.append(" FROM(SELECT SG.SA_PROD_GROUP,UPPER(SA_GROUP_NAME) SA_GROUP_NAME,DTB.TEAM_ID");
			sb.append(" FROM DIV_TEAM_BRAND DTB");
			sb.append(" JOIN  SAPRODGRP SG  ON DTB.SA_PROD_GROUP=SG.SA_PROD_GROUP");
			sb.append(" WHERE SG.SA_STATUS='A' AND DTB.TEAM_ID=:teamId");
			sb.append(" UNION ALL SELECT SG.SA_PROD_GROUP,UPPER(SA_GROUP_NAME) SA_GROUP_NAME,0 AS TEAM_ID");
			sb.append(" FROM SAPRODGRP SG WHERE SG.SA_PROD_GROUP NOT IN");
			sb.append(" (SELECT SG.SA_PROD_GROUP FROM DIV_TEAM_BRAND DTB ");
			sb.append(" JOIN  SAPRODGRP SG  ON DTB.SA_PROD_GROUP=SG.SA_PROD_GROUP");
			sb.append(" WHERE SG.SA_STATUS='A'");
			sb.append(" AND DTB.TEAM_ID=:teamId)");
			sb.append(" )DTL ORDER BY DTL.TEAM_ID DESC,DTL.SA_GROUP_NAME");
			
			System.out.println("team_code : "+team_code);
			Query query = em.createNativeQuery(sb.toString(), Tuple.class);
			query.setParameter("teamId", team_code);
			List<Tuple> tuples = query.getResultList();
			
			TeamBrandMappingBean bean = null;
			if(tuples!=null && tuples.size()>0) {
				for(Tuple t : tuples) {
					bean = new TeamBrandMappingBean();
					bean.setSmp_sa_prod_group(Long.valueOf(t.get("SA_PROD_GROUP",Integer.class).toString()));
					bean.setSa_group_name(t.get("SA_GROUP_NAME",String.class));
					
					teamList.add(bean);
				}
			}
			teamList.sort((TeamBrandMappingBean o1, TeamBrandMappingBean o2) ->  o1.getSa_group_name().compareTo( o2.getSa_group_name()));
		}
		catch (Exception e) {
			// TODO: handle exception
			System.out.println("Error Occurred :" +e.getMessage());// uncomment asneeded --;
		}
		return teamList;
	}

	@Override
	public List<ProductBean> getProductListProductMaster(String divId) throws Exception {
		// TODO Auto-generated method stub
		EntityManager em = null;
		List<ProductBean> productList = new ArrayList<ProductBean>();
		try {
			em = emf.createEntityManager();
			StringBuffer sb = new StringBuffer();
			sb.append(" select DMAP.PROD_DIV_ID,DV.DIV_DISP_NM PROD_DIV_DISP_NM from divmap DMAP");
			sb.append(" JOIN DIV_MASTER DV ON DV.DIV_ID=DMAP.PROD_DIV_ID");
			sb.append(" where DMAP.base_div_id=:divId");
			sb.append(" order by DV.DIV_DISP_NM");
			Query query = em.createNativeQuery(sb.toString(), Tuple.class);
			query.setParameter("divId", divId);
			List<Tuple> tuples = query.getResultList();
			if (tuples != null && !tuples.isEmpty()) {
				for(Tuple t : tuples) {
					ProductBean pb=new ProductBean();
					pb.setProd_div_id(Long.valueOf(t.get("PROD_DIV_ID", Integer.class)));
					pb.setProd_div_disp_nm(t.get("PROD_DIV_DISP_NM", String.class));
					productList.add(pb);
				}
				System.out.println("productList::"+productList.size());
			}		
		}
		catch(Exception e) {
			throw e;
		}
		 finally {
				if (em != null) { em.close(); }
			}
		
		return productList;
	}
	
	@Override
	public List<DivMaster> getDivIdListproductDivMapping() throws Exception {
		EntityManager em = null;
		List<DivMaster> divList = new ArrayList<DivMaster>();
		try {
			em = emf.createEntityManager();
			StringBuffer sb = new StringBuffer();
			sb.append("select div_code,div_disp_nm,div_id,div_code_nm,div_status,div_map_cd,div_short_nm,team_reqd_ind from  DIV_MASTER  ");
			sb.append("WHERE  DIV_status = 'A' ");
			sb.append("order by div_disp_nm "); 
			Query query = em.createNativeQuery(sb.toString(), Tuple.class);
		//	query.setParameter("emp_id", userId);
			List<Tuple> tuples = query.getResultList();
			if (tuples != null && !tuples.isEmpty()) {
				for(Tuple t : tuples) {
					DivMaster dm=new DivMaster();
					dm.setDiv_code(t.get("div_code", String.class));
					dm.setDiv_id(Long.valueOf(t.get("div_id", Integer.class)));
					dm.setDiv_disp_nm(t.get("div_disp_nm", String.class));
					dm.setDiv_map_cd(t.get("div_map_cd", String.class));
					dm.setDiv_status(t.get("div_status", Character.class).toString());
					dm.setDiv_short_nm(t.get("div_short_nm", String.class));
					dm.setTeam_reqd_ind(t.get("team_reqd_ind",Character.class).toString());
					divList.add(dm);
				}
			}
		} catch (Exception e) {
			// TODO: handle exception
			throw e;
		}finally {
			if (em != null) { em.close(); }
		}
	
		return divList;
	}

	
}
