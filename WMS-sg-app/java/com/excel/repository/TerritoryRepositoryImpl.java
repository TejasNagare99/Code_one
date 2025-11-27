package com.excel.repository;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.StoredProcedureQuery;
import javax.persistence.Tuple;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.excel.model.Abolish_Terr_Log;
import com.excel.model.DG_TERRMAST_UPLOAD_FOR_GCO;
import com.excel.model.Dg_AbolishTerr_Codes;
import com.excel.model.DivField;
import com.excel.model.FieldStaff;
import com.excel.model.SmpItem;
import com.excel.model.TERR_MASTER_UPLOAD_TEMPLATE;
import com.excel.model.Team;
import com.excel.model.TerrMaster;
import com.excel.model.V_Terr_Master;

@Repository
public class TerritoryRepositoryImpl implements TerritoryRepository{
	@Autowired EntityManagerFactory emf;
	@PersistenceContext private EntityManager entityManager;
	
	@Override
	public TerrMaster getObjectByTerrId(Long terr_id) throws Exception {
		return this.entityManager.find(TerrMaster.class, terr_id);
	}
	
	@Override
	public List<DivField> getLevelForTerritory(String divId) {
		EntityManager em = null;
		List<DivField> list = new ArrayList<DivField>();
		try {
			em = emf.createEntityManager();
			String q="select LEVEL_CODE,LEVEL_NAME,TRAININGIND,FS_CATEGORY,DIV_ID from DIV_FIELD where DIV_ID=:divId  ORDER BY FS_CATEGORY,LEVEL_CODE,LEVEL_BRIEF_NAME";
			Query query = em.createNativeQuery(q,Tuple.class);
			
			query.setParameter("divId", divId);
			List<Tuple> tuples = query.getResultList();			
			if (tuples != null && !tuples.isEmpty()) {
				for (Tuple t : tuples) {
					DivField df=new DivField();
					df.setLevel_code(t.get("LEVEL_CODE",String.class));
					df.setLevel_name(t.get("LEVEL_NAME",String.class));
					df.setTrainingind(t.get("TRAININGIND",Character.class)+"");
					df.setFs_category(t.get("FS_CATEGORY",Character.class)+"");
					df.setDiv_id(Long.valueOf(t.get("DIV_ID",Integer.class)));
					list.add(df);
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
	public List<DivField> getLevelForTerritoryModify(String divId,String fsCategory) {
		EntityManager em = null;
		List<DivField> list = new ArrayList<DivField>();
		try {
			em = emf.createEntityManager();
			String q="select LEVEL_CODE,LEVEL_NAME,TRAININGIND,FS_CATEGORY,DIV_ID from DIV_FIELD where DIV_ID=:divId and fs_category=:fsCategory ORDER BY FS_CATEGORY,LEVEL_CODE,LEVEL_BRIEF_NAME";
			Query query = em.createNativeQuery(q,Tuple.class);
			
			query.setParameter("divId", divId);
			query.setParameter("fsCategory", fsCategory);
			List<Tuple> tuples = query.getResultList();			
			if (tuples != null && !tuples.isEmpty()) {
				for (Tuple t : tuples) {
					DivField df=new DivField();
					df.setLevel_code(t.get("LEVEL_CODE",String.class));
					df.setLevel_name(t.get("LEVEL_NAME",String.class));
					df.setTrainingind(t.get("TRAININGIND",Character.class)+"");
					df.setFs_category(t.get("FS_CATEGORY",Character.class)+"");
					df.setDiv_id(Long.valueOf(t.get("DIV_ID",Integer.class)));
					list.add(df);
				}
				if(!list.isEmpty() && list.size() > 0)
					return list;
				}
			} finally {
				if (em != null) { em.close(); }
		}
		return list;
	}
//	@Override
//	public List<DivField> getLevelForTerritoryModify() {
//		EntityManager em = null;
//		List<DivField> list = new ArrayList<DivField>();
//		try {
//			em = emf.createEntityManager();
//			String q="select LEVEL_CODE,LEVEL_NAME from DIV_FIELD ORDER BY FS_CATEGORY,LEVEL_CODE,LEVEL_BRIEF_NAME";
//			Query query = em.createNativeQuery(q,Tuple.class);
//			
//			List<Tuple> tuples = query.getResultList();			
//			if (tuples != null && !tuples.isEmpty()) {
//				for (Tuple t : tuples) {
//					DivField df=new DivField();
//					df.setLevel_code(t.get("LEVEL_CODE",String.class));
//					df.setLevel_name(t.get("LEVEL_NAME",String.class));
//					list.add(df);
//				}
//				if(!list.isEmpty() && list.size() > 0)
//					return list;
//				}
//			} finally {
//				if (em != null) { em.close(); }
//		}
//		return list;
//	}
	@Override
	public Boolean checkUniqueTerrCode(String divId,String terrCode) {
		EntityManager em = null;
		Boolean data;
		try {
			em = emf.createEntityManager();
			String q="select * from TERR_MASTER where TERR_DIV_ID=:divId and TERR_CODE=:terrCode";
			Query query = em.createNativeQuery(q,Tuple.class);
			
			query.setParameter("divId", divId);
			query.setParameter("terrCode", terrCode);
			List<Tuple> tuples = query.getResultList();			
			if (tuples != null && !tuples.isEmpty()) {
				data=true;
			}
			else {
				data=false;
			}
		} finally {
			if (em != null) { em.close(); }
		}
		return data;
	}
	@Override
	public List<TerrMaster> getTerrCodeList(String level,String divId,String trainingInd){
		
		EntityManager em = null;
		List<TerrMaster> list = new ArrayList<TerrMaster>();
		try {
			em = emf.createEntityManager();
			
			String q="select TERR_ID,TERR_CODE,TERR_LEVEL_CODE,TERR_NAME,TERR_MGR1_ID,TERR_MGR2_ID,TERR_MGR3_ID,TERR_MGR4_ID,TERR_MGR5_ID,TERR_MGR6_ID from TERR_MASTER "
					+ " where TERR_LEVEL_CODE=:level and TERR_DIV_ID=:divId and TERR_TRAINING=:trainingInd "
					+ "and terr_status='A' and terr_id  not in ( select  isnull(fstaff_terr_id,0) fstaff_terr_id    from fieldstaff where FSTAFF_LEVEL_CODE =:level and fstaff_leaving_date is  null   and fstaff_div_id=:divId) order by TERR_CODE";
			
			
			
			// changed abhishek priyanka query 06/10/25
//			StringBuffer sb= new StringBuffer();
//			sb.append("  TERR_MGR1_ID,TERR_MGR2_ID,TERR_MGR3_ID,TERR_MGR4_ID,TERR_MGR5_ID,");
//			sb.append("  select FS.FSTAFF_ID, TERR_ID,TERR_CODE,TERR_LEVEL_CODE,TERR_NAME,");  
//			sb.append("  TERR_MGR6_ID from TERR_MASTER TM");
//			sb.append("   LEFT JOIN FIELDSTAFF FS ON FS.FSTAFF_TERR_ID=TM.TERR_ID AND  FSTAFF_LEVEL_CODE =:level");
//			sb.append("  and fstaff_div_id=:divId");
//			sb.append("  where TERR_LEVEL_CODE=:level and TERR_DIV_ID=:divId and TERR_TRAINING=:trainingInd");
//			sb.append("  AND FS.FSTAFF_ID IS NULL");
//			sb.append(" AND TERR_STATUS='A'");
//			sb.append("  ORDER BY TERR_CODE");
			
			
			Query query = em.createNativeQuery(q,Tuple.class);
			query.setParameter("level", level);
			query.setParameter("divId", divId);
			query.setParameter("trainingInd", trainingInd);
			List<Tuple> tuples = query.getResultList();			
			if (tuples != null && !tuples.isEmpty()) {
				for (Tuple t : tuples) {
					TerrMaster terr=new TerrMaster();
					terr.setTerr_id(Long.valueOf(t.get("TERR_ID",Integer.class)));
					terr.setTerr_code(t.get("TERR_CODE",String.class));
					terr.setTerr_name(t.get("TERR_NAME",String.class));
					list.add(terr);
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
	public List<TerrMaster> getTerrCodeListForModify(String level,String divId,String trainingInd){
		
		EntityManager em = null;
		List<TerrMaster> list = new ArrayList<TerrMaster>();
		System.out.println("level : "+level);
		try {
			em = emf.createEntityManager();
			Query query = null;
				
			String q="from TerrMaster WHERE TERR_LEVEL_CODE=:level and TERR_DIV_ID=:divId and TERR_TRAINING=:trainingInd order by TERR_CODE";
			query = em.createQuery(q);
			query.setParameter("level", level);
			query.setParameter("divId", divId);
			query.setParameter("trainingInd", trainingInd);
			list=query.getResultList();
//			if(list!=null) {
//				System.out.println(list.get(0).getTerr_code());
//				System.out.println(list.get(0).getTerr_name());
//			}
			
			} finally {
				if (em != null) { em.close(); }
		}
		return list;
		

//			String q="select TERR_ID,TERR_CODE,TERR_LEVEL_CODE,TERR_NAME,TERR_MGR1_ID,TERR_MGR2_ID,TERR_MGR3_ID,TERR_MGR4_ID,TERR_MGR5_ID,TERR_MGR6_ID from TERR_MASTER "
//					+ " where TERR_LEVEL_CODE=:level and TERR_DIV_ID=:divId and TERR_TRAINING=:trainingInd order by TERR_CODE";

	}
	@Override
	public List<TerrMaster> getMgr(String levelCode,String trainingInd,String divId) {
		EntityManager em = null;
		List<TerrMaster> list = new ArrayList<TerrMaster>();
		try {
			System.out.println("levelCode"+ levelCode);
			System.out.println("trainingInd"+ trainingInd);
			System.out.println("divId"+ divId);
			
			em = emf.createEntityManager();
			String q="select TERR_ID,TERR_CODE,TERR_NAME,TERR_MGR1_ID,TERR_MGR2_ID,TERR_MGR3_ID,TERR_MGR4_ID,TERR_MGR5_ID,TERR_MGR6_ID,"
					+ "TERR_LEVEL_CODE,TERR_TRAINING,TERR_HQ_ID,TERR_ZONE_ID,TERR_STATE_ID,TERR_CFA_LOC,TERR_CFA_LOC2,TERR_CFA3_LOC3"
					+ " from TERR_MASTER where TERR_LEVEL_CODE=:levelCode and TERR_TRAINING=:trainingInd  and TERR_DIV_ID=:divId and TERR_STATUS='A' order by TERR_CODE";
			Query query = em.createNativeQuery(q,Tuple.class);
			
			query.setParameter("levelCode", levelCode);
			query.setParameter("trainingInd", trainingInd);
			query.setParameter("divId", divId);
			List<Tuple> tuples = query.getResultList();			
			if (tuples != null && !tuples.isEmpty()) {
				for (Tuple t : tuples) {
					TerrMaster terr=new TerrMaster();
					terr.setTerr_id(Long.valueOf(t.get("TERR_ID",Integer.class)));
					terr.setTerr_code(t.get("TERR_CODE",String.class));
					terr.setTerr_name(t.get("TERR_CODE",String.class)+" - "+t.get("TERR_NAME",String.class));
					terr.setTerr_mgr1_id(Long.valueOf(t.get("TERR_MGR1_ID",Integer.class)));
					terr.setTerr_mgr2_id(Long.valueOf(t.get("TERR_MGR2_ID",Integer.class)));
					terr.setTerr_mgr3_id(Long.valueOf(t.get("TERR_MGR3_ID",Integer.class)));
					terr.setTerr_mgr4_id(Long.valueOf(t.get("TERR_MGR4_ID",Integer.class)));
					terr.setTerr_mgr5_id(Long.valueOf(t.get("TERR_MGR5_ID",Integer.class)));
					terr.setTerr_mgr6_id(Long.valueOf(t.get("TERR_MGR6_ID",Integer.class)));
					
					terr.setTerr_hq_id(Long.valueOf(t.get("TERR_HQ_ID",Short.class)));
					terr.setTerr_zone_id(Long.valueOf(t.get("TERR_ZONE_ID",Integer.class)));
					terr.setTerr_state_id(Long.valueOf(t.get("TERR_STATE_ID",Integer.class)));
					
					terr.setTerr_cfa_loc1(Long.valueOf(t.get("TERR_CFA_LOC",Integer.class)));
					terr.setTerr_cfa_loc2(Long.valueOf(t.get("TERR_CFA_LOC2",Integer.class)));
					terr.setTerr_cfa_loc3(Long.valueOf(t.get("TERR_CFA3_LOC3",Integer.class)));
					list.add(terr);
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
	public List<TerrMaster> getMgrTwo(String mgrId) {
		EntityManager em = null;
		List<TerrMaster> list = new ArrayList<TerrMaster>();
		try {
			em = emf.createEntityManager();
			String q="select TERR_ID,TERR_CODE,TERR_NAME,TERR_MGR1_ID,TERR_MGR2_ID,TERR_MGR3_ID,TERR_MGR4_ID,TERR_MGR5_ID,TERR_MGR6_ID,"
					+ "TERR_LEVEL_CODE,TERR_TRAINING,TERR_HQ_ID,TERR_ZONE_ID,TERR_STATE_ID,TERR_CFA_LOC,TERR_CFA_LOC2,TERR_CFA3_LOC3"
					+ " from TERR_MASTER where TERR_ID=:mgrId and TERR_STATUS='A' order by TERR_CODE";
			Query query = em.createNativeQuery(q,Tuple.class);
			query.setParameter("mgrId", mgrId);
			List<Tuple> tuples = query.getResultList();			
			if (tuples != null && !tuples.isEmpty()) {
				for (Tuple t : tuples) {
					TerrMaster terr=new TerrMaster();
					terr.setTerr_id(Long.valueOf(t.get("TERR_ID",Integer.class)));
					terr.setTerr_code(t.get("TERR_CODE",String.class));
					terr.setTerr_name(t.get("TERR_CODE",String.class)+" - "+t.get("TERR_NAME",String.class));
					terr.setTerr_mgr1_id(Long.valueOf(t.get("TERR_MGR1_ID",Integer.class)));
					terr.setTerr_mgr2_id(Long.valueOf(t.get("TERR_MGR2_ID",Integer.class)));
					terr.setTerr_mgr3_id(Long.valueOf(t.get("TERR_MGR3_ID",Integer.class)));
					terr.setTerr_mgr4_id(Long.valueOf(t.get("TERR_MGR4_ID",Integer.class)));
					terr.setTerr_mgr5_id(Long.valueOf(t.get("TERR_MGR5_ID",Integer.class)));
					terr.setTerr_mgr6_id(Long.valueOf(t.get("TERR_MGR6_ID",Integer.class)));
					
					terr.setTerr_hq_id(Long.valueOf(t.get("TERR_HQ_ID",Short.class)));
					terr.setTerr_zone_id(Long.valueOf(t.get("TERR_ZONE_ID",Integer.class)));
					terr.setTerr_state_id(Long.valueOf(t.get("TERR_STATE_ID",Integer.class)));
					
					terr.setTerr_cfa_loc1(Long.valueOf(t.get("TERR_CFA_LOC",Integer.class)));
					terr.setTerr_cfa_loc2(Long.valueOf(t.get("TERR_CFA_LOC2",Integer.class)));
					terr.setTerr_cfa_loc3(Long.valueOf(t.get("TERR_CFA3_LOC3",Integer.class)));
					list.add(terr);
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
	public List<V_Terr_Master> getAllTerrCodeList(String company) {
		
		EntityManager em = null;
		List<V_Terr_Master> list = new ArrayList<V_Terr_Master>();
		System.out.println("company : "+company);
		try {
			em = emf.createEntityManager();
			Query query = null;
				
			String q="from V_Terr_Master WHERE COMPANY = :company";
			query = em.createQuery(q);
			query.setParameter("company", company);
			list=query.getResultList();
			System.out.println(list.get(0).getTerr_code());
			System.out.println(list.get(0).getTerr_name());
			} finally {
				if (em != null) { em.close(); }
		}
		return list;
	}
	@Override
	public List<V_Terr_Master> getTextParameterTerrDetail(String company, String name,String parameter,String text) {
		EntityManager em = null;
		List<V_Terr_Master> list = new ArrayList<V_Terr_Master>();
		try {
			em = emf.createEntityManager();
			Query query = null;
			if(name.equals("NM")) {
				System.out.println("detail : "+name+"...."+parameter+"..."+text);
				String q="from V_Terr_Master WHERE COMPANY = '"+company
						+"' and TERR_NAME "+parameter+"  '%"+text+"%' and terr_status='A'"
						+" order by DIV_DISP_NM,TERR_CODE";
		//				+ "' and TERR_NAME "+parameter+"  '%"+text+"%' and terr_id not in ( select fstaff_terr_id from FieldStaff ) order by DIV_DISP_NM,TERR_CODE";
				query = em.createQuery(q);
			}
			if(name.equals("CD")) {
				String q="from V_Terr_Master WHERE COMPANY = '"+company
						+"' and TERR_CODE "+parameter+"  '%"+text+"%' and terr_status='A'"
						+"order by DIV_DISP_NM,TERR_CODE";
	//					+ "' and TERR_CODE "+parameter+"  '%"+text+"%' and terr_id not in ( select fstaff_terr_id from FieldStaff ) order by DIV_DISP_NM,TERR_CODE";
				query = em.createQuery(q);
			}
			if(name.equals("DIV")){
				String q="from V_Terr_Master WHERE COMPANY = '"+company
						+"' and DIV_DISP_NM "+parameter+"  '%"+text+"%' and terr_status='A'"
						+"order by DIV_DISP_NM,TERR_CODE";
	//					+ "' and DIV_DISP_NM "+parameter+"  '%"+text+"%' and terr_id not in ( select fstaff_terr_id from FieldStaff ) order by DIV_DISP_NM,TERR_CODE";
				query = em.createQuery(q);
			}
			if(name.equals("LC")) {
				String q="from V_Terr_Master WHERE COMPANY = '"+company
						+"' and LEVEL_NAME "+parameter+"  '%"+text+"%' and terr_status='A'"
						+"order by DIV_DISP_NM,TERR_CODE";
	//					+ "' and LEVEL_NAME "+parameter+"  '%"+text+"%' and terr_id not in ( select fstaff_terr_id from FieldStaff ) order by DIV_DISP_NM,TERR_CODE";
				query = em.createQuery(q);
			}

			
			list=query.getResultList();
			} finally {
				if (em != null) { em.close(); }
		}
		return list;
	}
	
	@Override
	public List<TerrMaster> getTerrDetailById(String id){
		EntityManager em = null;
		List<TerrMaster> list = new ArrayList<TerrMaster>();
		System.out.println("selected terr id : "+id);
		try {
			em = emf.createEntityManager();
			Query query = null;
			String q="from TerrMaster WHERE TERR_ID = :id";
			query = em.createQuery(q);
			query.setParameter("id", Long.valueOf(id));
			list=query.getResultList();
			System.out.println(list.get(0).getTerr_code());
			System.out.println(list.get(0).getTerr_name());
			} 
		finally {
				if (em != null) { em.close(); }
		}
		return list;
	}
	
	@Override
	public List<TerrMaster> getTerrDetailByTerrCode(String terr_code){
		EntityManager em = null;
		List<TerrMaster> list = null;
		System.out.println("terr_code : "+terr_code);
		try {
			//em = emf.createEntityManager();
			Query query = null;
			String q="from TerrMaster WHERE RTRIM(TERR_CODE) = :terr_code and TERR_STATUS='A' ";
			query = entityManager.createQuery(q);
			query.setParameter("terr_code",terr_code.trim());
			list=query.getResultList();
		}
		finally {
			//if (em != null) { em.close(); }
		}
		return list;
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
	public Long getTeamIdByFsId(String fsid) throws Exception {
		// TODO Auto-generated method stub
		EntityManager em = null;
		Long teamid=null;
		try {
			em = emf.createEntityManager();
			Query query = null;
			System.out.println("fsId :: "+fsid);
			String q="select tm.team_id from FIELDSTAFF fs join team tm on convert(varchar,tm.div_id)+tm.team_code = convert(varchar,fs.fstaff_div_id)+fs.team_code"
				+" where fs.fstaff_id=:fsid";
			query = em.createNativeQuery(q);
			query.setParameter("fsid", Long.valueOf(fsid));
			teamid=Long.valueOf(((Integer) query.getResultList().stream().findFirst().orElse(0)).toString());
		
			
			System.out.println("TeamID::::"+teamid);
		}
		catch (Exception e) {
			// TODO: handle exception
			throw e;
		}
		finally {
			if (em != null) { em.close(); }
		}
		return teamid;
	}
	
	@Override
	public Long getTeamIdByHqId(String hqid) throws Exception {
		// TODO Auto-generated method stub
		EntityManager em = null;
		Long teamid=null;
		try {
			em = emf.createEntityManager();
			Query query = null;
			System.out.println("hqid :: "+hqid);
			String q="select tm.team_id from HQMASTER hq join team tm on convert(varchar,tm.div_id)+tm.team_code = convert(varchar,hq.hq_div_id)+hq.hq_team_code"
					+" where hq.hq_id=:hqid";
			query = em.createNativeQuery(q);
			query.setParameter("hqid", Long.valueOf(hqid));
			teamid=Long.valueOf(((Integer) query.getResultList().stream().findFirst().orElse(0)).toString());
		
			
			System.out.println("TeamID::::"+teamid);
		}
		catch (Exception e) {
			// TODO: handle exception
			throw e;
		}
		finally {
			if (em != null) { em.close(); }
		}
		return teamid;
	}
	
	@Override
	public TerrMaster getTerrDetailByTerrCode(String terr_code, Long terr_div_id) {

		EntityManager em = null;
		TerrMaster tm = null;
		System.out.println("terr_code : "+terr_code);
		try {
			//em = emf.createEntityManager();
			Query query = null;
			String q="from TerrMaster WHERE RTRIM(TERR_CODE) = :terr_code and TERR_STATUS='A' and TERR_DIV_ID = :terr_div_id";
			query = entityManager.createQuery(q);
			query.setParameter("terr_code",terr_code.trim());
			query.setParameter("terr_div_id",terr_div_id);
			tm= (TerrMaster) query.getSingleResult();
		}
		finally {
			//if (em != null) { em.close(); }
		}
		return tm;
	
	}

	@Override
	public List<Dg_AbolishTerr_Codes> getAbolishTerrCsvCount(String file) throws Exception {
		List<Dg_AbolishTerr_Codes> list = null;
		EntityManager em = null;

		try {
			em = emf.createEntityManager();
			StoredProcedureQuery procedurecall = em.createNamedStoredProcedureQuery("DG_ABOLISH_TERR_CODES");
			procedurecall.setParameter("MCSVFLNAME", file);
			list = procedurecall.getResultList();

		} catch (Exception e) {
			throw e;
		} finally {
			if (em != null) {
				em.close();
			}
		}
		return list;
	}

	@Override
	public boolean isFileExist(String file) throws Exception {
		EntityManager em = null;
		Long count = 0L;
		try {
			em = this.emf.createEntityManager();
			StringBuffer sb = new StringBuffer();
			sb.append("Select count(filename) from ABOLISH_TERR_LOG where filename='").append(file + "'");
			Query query = em.createNativeQuery(sb.toString());
			count = Long.valueOf((Integer) query.getSingleResult());
			System.out.println("count:::" + count);
			if (count > 0)
				return true;
		} finally {
			if (em != null) {
				em.close();
			}
		}
		return false;
	}
	
	@Override
	public List<Abolish_Terr_Log> getReport(String file, String ind) {
	    EntityManager em = null;
	    List<Abolish_Terr_Log> list = new ArrayList<Abolish_Terr_Log>();
	    List<Object[]> resultList = new ArrayList<>();
	    
	    try {
	        em = emf.createEntityManager();
	        
	        StringBuilder sb = new StringBuilder();
	        sb.append("SELECT alog.csv_rownum, alog.div_name, alog.terr_code,alog.err_msg, hr1.emp_fnm + ' ' + hr1.emp_lnm as ins_usr_name ");
	        sb.append("FROM ABOLISH_TERR_LOG alog ");
	        sb.append("LEFT JOIN hr_m_employee hr1 ON hr1.emp_id = alog.abol_ins_usr_id ");
	        sb.append("where alog.filename = '").append(file).append("'");
	        
	        if (ind.equals("NF")) {
		        sb.append("and alog.err_ind='T' and alog.abol_fieldstaff_ind='N'");
			} else if(ind.equals("T")) {
		        sb.append("and alog.err_ind='T' and alog.abol_fieldstaff_ind='Y'");
			}else if(ind.equals("B")) {
		        sb.append("and alog.err_ind='B'");
			}else {
		        sb.append("and alog.err_ind = 'Y'");

			}

	        Query query = em.createNativeQuery(sb.toString());
	        resultList = query.getResultList();
	        
	        for(Object[] row: resultList) {
	        	Abolish_Terr_Log alog = new Abolish_Terr_Log();
	        	alog.setCsv_rownum((int) row[0]);
	        	alog.setDiv_name((String) row[1]);
	        	alog.setTerr_code((String) row[2]);
	        	alog.setErr_msg((String) row[3]);
	            alog.setIns_usr_name((String) row[4]);
	        	list.add(alog);
	        }

	        System.out.println("Result Size: " + resultList.size());

	    } catch (Exception e) {
	        throw e;
	    } finally {
	        if (em != null) {
	            em.close();
	        }
	    }

	    return list;
	}

	@Override
	public Long errCount(String file, String ind) throws Exception {
		EntityManager em = null;
		Long count = 0L;
		try {
			em = this.emf.createEntityManager();
			StringBuffer sb = new StringBuffer();
			
			if (ind.equals("NF")) {
			    sb.append("Select count(*) from ABOLISH_TERR_LOG where ERR_IND='T' AND ABOL_FIELDSTAFF_IND='Y' and filename='").append(file).append("'");
			} else if(ind.equals("T")) {
			    sb.append("Select count(*) from ABOLISH_TERR_LOG where ERR_IND='T' AND ABOL_FIELDSTAFF_IND='N' AND filename='").append(file).append("'");
			}else if(ind.equals("B")) {
			    sb.append("Select count(*) from ABOLISH_TERR_LOG where ERR_IND='B' AND ABOL_FIELDSTAFF_IND='Y'AND filename='").append(file).append("'");
			}else {
			    sb.append("Select count(*) from ABOLISH_TERR_LOG where ERR_IND='Y' AND filename='").append(file).append("'");

			}

			
			Query query = em.createNativeQuery(sb.toString());
			count = Long.valueOf((Integer) query.getSingleResult());
			System.out.println("count:::" + count);

		} catch (Exception e) {
			throw e;
		} finally {
			if (em != null) {
				em.close();
			}
		}
		return count;
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public void truncate_TempTable()throws Exception {
		
		try {
			StringBuffer sb = new StringBuffer();
			sb.append("Truncate Table TERR_MASTER_UPLOAD_TEMPLATE");
			Query query = entityManager.createNativeQuery(sb.toString());
			query.executeUpdate();
		}
		catch (Exception e) {
			// TODO: handle exception
			throw e;
		}
		
		
	}

	@Override
	public List<String> Active_getTerrCodeList_with_div_id(String divId, String terrCodes) {
	    EntityManager em = null;

	    List<String> resultList = new ArrayList<>();
	    
	    try {
	        em = emf.createEntityManager();
	 
	        StringBuilder sb = new StringBuilder();
	        sb.append(" select LTRIM(RTRIM(TERR_CODE)) AS TERR_CODE from Terr_master where terr_code in "+terrCodes+" ");
	        sb.append("  and terr_status='A'AND TERR_DIV_ID=:divId  ");

	        Query query = em.createNativeQuery(sb.toString());
	        query.setParameter("divId", divId);
	        resultList = query.getResultList();
	        

	        System.out.println("Result Size: " + resultList.size());

	    } catch (Exception e) {
	        throw e;
	    } finally {
	        if (em != null) {
	            em.close();
	        }
	    }

	    return resultList;
	}

	@Override
	public boolean checkFileExist_on_terr_master_upload_template_table(String originalFilename) {
		
		
		  EntityManager em = null;

		    List<String> resultList = new ArrayList<>();
		    
		    try {
		        em = emf.createEntityManager();
		 
		        StringBuilder sb = new StringBuilder();
		        sb.append(" select TERR_CODE from TERR_MASTER_UPLOAD_LOG   where FILENAME=:filename");
		

		        Query query = em.createNativeQuery(sb.toString());
		        query.setParameter("filename", originalFilename);
		        resultList = query.getResultList();
		        

		   if(resultList.size()>0) {
			   return true;
		   }
		   else {
			   return false;
		   }

		    } catch (Exception e) {
		        throw e;
		    } finally {
		        if (em != null) {
		            em.close();
		        }
		    }

		    
	}


	@Override
	public List<String> getValid_State_id() {
		EntityManager em = null;
		List<String> resultList = new ArrayList<>();
		try {
			em = emf.createEntityManager();
			StringBuilder sb = new StringBuilder();

			sb.append("select   LTRIM(RTRIM(sgprmdet_id)) AS sgprmdet_id from SG_d_parameters_details ");
			sb.append(" where sgprmdet_sgprm_id=2  ");
			sb.append(" order by sgprmdet_id  ");
			Query query = em.createNativeQuery(sb.toString());
			resultList = query.getResultList();

			System.out.println("Result Size: " + resultList.size());

		} catch (Exception e) {
			throw e;
		} finally {
			if (em != null) {
				em.close();
			}
		}
			return resultList;
		
	}

	@Override
	public List<DG_TERRMAST_UPLOAD_FOR_GCO> get_Uploaded_list(String divId,Boolean eight_char_level_code) {
		// TODO Auto-generated method stub
		List<DG_TERRMAST_UPLOAD_FOR_GCO> list = new ArrayList<>();
		EntityManager em = null;
		try {


			em = emf.createEntityManager();
			StoredProcedureQuery procedureQuery =null;
			
			if(eight_char_level_code) {
				
				procedureQuery = em.createNamedStoredProcedureQuery("CALL_DG_TERRMAST_UPLOAD_8char");
			}else {
				 procedureQuery = em.createNamedStoredProcedureQuery("CALL_DG_TERRMAST_UPLOAD");
			}
			
			
			
			procedureQuery.setParameter("MTERR_DIV_ID", Integer.parseInt(divId));
			list = procedureQuery.getResultList();
			System.out.println("list :: " + list.size());
			

		} catch (Exception e) {
			// TODO: handle exception
			throw e;
		} finally {
			if (em != null) {
				em.close();
			}
		}
		return list;
		
		
	}
}
