package com.excel.repository;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.Tuple;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.excel.model.HQ_Master;
import com.excel.model.TerrMaster;
import com.excel.model.V_HQ_Master;
import com.excel.model.V_Terr_Master;

@Repository
public class HQMasterRepositoryImpl implements HQMasterRepository{
	@Autowired EntityManagerFactory emf;
	@PersistenceContext private EntityManager entityManager;
	
	@Override
	public Boolean checkUniqueHQName(String hqName,String divId) {
		Boolean data=null;
		EntityManager em = null;
		try {
			em = emf.createEntityManager();
			String q="select HQ_ID,HQ_CITY_ID,HQ_CODE,HQ_DIV_ID,HQ_ins_dt,HQ_ins_ip_add,HQ_ins_usr_id,HQ_MAP_CODE,HQ_mod_dt,HQ_mod_ip_add,HQ_mod_usr_id,HQ_NAME,HQ_NO_OF_REPS,HQ_PMP_PERF,HQ_POOL_IND,HQ_STATE_ID,HQ_STATUS,HQ_ZONE_ID " + 
					" from HQMASTER where HQ_NAME=:hqName and HQ_DIV_ID=:divId";
			Query query = em.createNativeQuery(q,Tuple.class);
			query.setParameter("hqName", hqName);
			query.setParameter("divId", divId);
			List<Tuple> tuples = query.getResultList();
			if (tuples != null && !tuples.isEmpty()) {
				data=true;
			}
			else {data=false;}
			
		}
		finally {
			if(em != null) { em.close(); }
		}
		return data;
	}
	@Override
	public Boolean checkUniqueHQNameForModify(String hqName,String divId,String hqId) {
		Boolean data=null;
		EntityManager em = null;
		try {
			em = emf.createEntityManager();
			String q="select HQ_ID,HQ_CITY_ID,HQ_CODE,HQ_DIV_ID,HQ_ins_dt,HQ_ins_ip_add,HQ_ins_usr_id,HQ_MAP_CODE,HQ_mod_dt,HQ_mod_ip_add,HQ_mod_usr_id,HQ_NAME,HQ_NO_OF_REPS,HQ_PMP_PERF,HQ_POOL_IND,HQ_STATE_ID,HQ_STATUS,HQ_ZONE_ID " + 
					" from HQMASTER where HQ_NAME=:hqName and HQ_DIV_ID=:divId and hq_id<>:hqId";
			Query query = em.createNativeQuery(q,Tuple.class);
			query.setParameter("hqName", hqName);
			query.setParameter("divId", divId);
			query.setParameter("hqId", hqId);
			List<Tuple> tuples = query.getResultList();
			if (tuples != null && !tuples.isEmpty()) {
				data=true;
			}
			else {data=false;}
			
		}
		finally {
			em.close();
		}
		return data;
	}
	@Override
	public List<HQ_Master> getHqListForFieldStaffEntry(String divId) {
		EntityManager em = null;
		List<HQ_Master> list=new ArrayList<HQ_Master>();
		try {
			em = emf.createEntityManager();
			String q="select HQ_ID,HQ_NAME from HQMASTER where HQ_STATUS='A' and HQ_DIV_ID=:divId order by HQ_NAME asc";
			Query query = em.createNativeQuery(q,Tuple.class);
			query.setParameter("divId", divId);
			List<Tuple> tuples = query.getResultList();
			if (tuples != null && !tuples.isEmpty()) {
				for (Tuple t : tuples) {
					HQ_Master hqm=new HQ_Master();
					hqm.setHq_name(t.get("HQ_NAME",String.class));
					hqm.setHq_id(Long.valueOf(t.get("HQ_ID",Integer.class)));
					list.add(hqm);
				}
			}
		}
		finally {
			em.close();
		}
		return list;
	}
	@Override
	public List<V_HQ_Master> getAllActiveHqMasterDetail() {
		EntityManager em = null;
		List<V_HQ_Master> list = new ArrayList<V_HQ_Master>();
		try {
			em = emf.createEntityManager();
			Query query = null;
				
			String q="from V_HQ_Master WHERE HQ_status='A'";
			query = em.createQuery(q);
			list=query.getResultList();
			System.out.println(list.get(0).getHq_code());
			System.out.println(list.get(0).getHq_name());
			} finally {
				if (em != null) { em.close(); }
		}
		return list;
	}
	@Override
	public List<V_HQ_Master> getHqTextParameterDetail(String name,String parameter,String text) {
		EntityManager em = null;
		List<V_HQ_Master> list = new ArrayList<V_HQ_Master>();
		try {
			em = emf.createEntityManager();
			Query query = null;
				
			System.out.println("detail : "+name+"...."+parameter+"..."+text);
			
			if(name.equals("NM")) {
				String q="from V_HQ_Master WHERE HQ_status='A'"
						+ " and HQ_NAME "+parameter+"  '%"+text+"%' order by hq_div_id,hq_name";
				query = em.createQuery(q);
			}
			if(name.equals("CD")) {
				String q="from V_HQ_Master WHERE HQ_status='A'"
						+ " and HQ_CODE "+parameter+"  '%"+text+"%' order by hq_div_id,hq_name";
				query = em.createQuery(q);
			}
			if(name.equals("DIV")){
				String q="from V_HQ_Master WHERE HQ_status='A'"
						+ " and DIV_DISP_NM "+parameter+"  '%"+text+"%' order by hq_div_id,hq_name";
				query = em.createQuery(q);
			}
			if(name.equals("MC")) {
				String q="from V_HQ_Master WHERE HQ_status='A'"
						+ " and HQ_MAP_CODE "+parameter+"  '%"+text+"%' order by hq_div_id,hq_name";
				query = em.createQuery(q);
			}

			
			list=query.getResultList();
			System.out.println(list.get(0).getHq_code());
			System.out.println(list.get(0).getHq_name());
			} finally {
				if (em != null) { em.close(); }
		}
		return list;
	}
	@Override
	public HQ_Master getByObjectId(Long hq_id) throws Exception {
		return this.entityManager.find(HQ_Master.class, hq_id);
	}
	@Override 
	public List<HQ_Master> getHqDetailById(String hqId) {
		EntityManager em = null;
		List<HQ_Master> list = new ArrayList<HQ_Master>();
		try {
			em = emf.createEntityManager();
			Query query = null;
				
			String q="from HQ_Master WHERE HQ_status='A' and HQ_ID="+hqId;
			query = em.createQuery(q);
			list=query.getResultList();
			System.out.println(list.get(0).getHq_code());
			System.out.println(list.get(0).getHq_name());
			} finally {
				if (em != null) { em.close(); }
		}
		return list;
	}
}
