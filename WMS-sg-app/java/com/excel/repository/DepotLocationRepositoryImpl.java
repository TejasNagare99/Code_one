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

import com.excel.bean.AllocationBean;
import com.excel.model.Depot_Locations;
import com.excel.model.Location;
import com.excel.utility.CodifyErrors;

@Repository
public class DepotLocationRepositoryImpl implements DepotLocationRepository {

	@Autowired EntityManagerFactory emf;
	@PersistenceContext private EntityManager entityManager;

	@SuppressWarnings("unchecked")
	@Override
	public List<Depot_Locations> getDepotLocations(Long locId,String depotLoactions) throws Exception {
		EntityManager em = null;
		List<Depot_Locations> divList = new ArrayList<Depot_Locations>();
		try {
			em = emf.createEntityManager();
			StringBuffer sb = new StringBuffer();
			StringBuffer temp = new StringBuffer();
			sb.append(" Select DPTLOC_ID, DPTLOC_NAME from DEPOT_LOCATIONS ");
			sb.append("  WHERE DPT_STATUS='A' ");
			if(locId.compareTo(0l) != 0) {
				sb.append("  AND DPTLOC_SubComp_id = (SELECT L.loc_SubComp_id from location L where L.loc_id =:locId and L.loc_report_status='A' ) ");
			}
			if (!depotLoactions.trim().equals("0")) {
				for (int i = 0; i < depotLoactions.length(); i = i + 4) {
					temp.append(Integer.parseInt(depotLoactions.substring(i, i + 4))).append(",");
				}
				if (temp.length() > 0) {
					temp.deleteCharAt(sb.length() - 1);
				}
				sb.append(" AND DL.DPTLOC_ID in (");
				sb.append(temp.toString());
				sb.append(")");
			}
			Query query = em.createNativeQuery(sb.toString(), Tuple.class);
			if(locId.compareTo(0l) != 0) {
				query.setParameter("locId", locId);
			}
			
			List<Tuple> tuples = query.getResultList();
			if (tuples != null && !tuples.isEmpty()) {
				for(Tuple t : tuples) {
					Depot_Locations dl=new Depot_Locations();
					dl.setDptLoc_id(Long.valueOf(t.get("DPTLOC_ID", Integer.class)));
					dl.setDptLoc_name(t.get("DPTLOC_NAME", String.class));
					divList.add(dl);
				}
			}
		} finally {
			if (em != null) { em.close(); }
		}
		
		return divList;
	}
	
	@Override
	public List<Object> getLocationsByCFA(String divId,String period_code,String fin_year,String company,String saveMode,String planType,String alloc_id,String alloc_cycle,String prodtype,List<String> brands){
		EntityManager em = null;
		List<Object> result = null;
		System.out.println("alloc_id "+alloc_id);
		try{
			em = emf.createEntityManager();
			StringBuffer sb= new StringBuffer();
			sb.append(" select distinct DPTDESTINATION_ID,SGprmdet_disp_nm from fieldstaff f");
			sb.append(" join FIELDSTAFF_Detail fd on f.fstaff_id=fd.FSTAFFD_FSTAFF_ID");
			sb.append(" join DEPOT_LOCATIONS on DPTLOC_ID=FSTAFFD_LOC_ID ");
			sb.append(" join SG_d_parameters_details on DPTDESTINATION_ID=SGprmdet_id");
			sb.append("	where fstaff_div_id=:divId");
			if(!alloc_id.equals("0")){
			sb.append(" and DPTDESTINATION_ID not in(select distinct cfa_destination_id").
			append(" from alloc_gen_ent where div_id=").append(divId).
			append(" and period_code=:period_code").
			append(" and alloc_cycle=:alloc_cycle").
			append(" and product_type=:prodtype").
			append(" and fin_year=:fin_year").append(" and company=:company and alloc_smp_sa_prod_group_id in(:brands))");
			}
			Query query = em.createNativeQuery(sb.toString(), Tuple.class);
			query.setParameter("divId", divId);
			if(!alloc_id.equals("0")){
			  query.setParameter("period_code", period_code);
			  query.setParameter("alloc_cycle", alloc_cycle);
			  query.setParameter("fin_year", fin_year);
			  query.setParameter("company", company);
			  query.setParameter("prodtype",planType.split("_")[1]);
			  query.setParameter("brands",brands);
			}
			List<Tuple> tuples = query.getResultList();
			if (tuples != null && !tuples.isEmpty()) {
				result = new ArrayList<>();
				AllocationBean object = null;
				for (Tuple t : tuples) {
					object = new AllocationBean();
					object.setAllocationById(Long.valueOf(t.get("DPTDESTINATION_ID", Integer.class)));
					object.setAllocationByDesc(t.get("SGprmdet_disp_nm", String.class));
					result.add(object);
				}
			}
				System.out.println("getCFACount"+result.size());
			if(!result.isEmpty() && result.size() > 0)
				return result;
		
			
	
		}
		catch (Exception e) {
			System.out.println("Error Occurred :" +e.getMessage());// uncomment asneeded --;
		}finally {
			if (em != null) { em.close(); }
		}
		return result;
	}
	@Override
	public List<Depot_Locations> getCFALocationsOne(){
		EntityManager em = null;
		List<Depot_Locations> list = new ArrayList<Depot_Locations>();
		try {
			em = emf.createEntityManager();
			String q="SELECT D.DPTLOC_ID CFA1_ID,D.DPTLOC_NAME CFA1 FROM DEPOT_LOCATIONS D " + 
					"WHERE D.DPTLOC_SubComp_id = 1 order by CFA1";
			
			Query query = em.createNativeQuery(q,Tuple.class);
			List<Tuple> tuples = query.getResultList();			
			if (tuples != null && !tuples.isEmpty()) {
				for (Tuple t : tuples) {
					Depot_Locations dl=new Depot_Locations();
					dl.setDptLoc_id(Long.valueOf(t.get("CFA1_ID",Integer.class)));
					dl.setDptLoc_name(t.get("CFA1",String.class));
					list.add(dl);
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
	public List<Depot_Locations> getCFALocationsTwo(){
		EntityManager em = null;
		List<Depot_Locations> list = new ArrayList<Depot_Locations>();
		try {
			em = emf.createEntityManager();
			String q="SELECT D.DPTLOC_ID CFA1_ID,D.DPTLOC_NAME CFA1 FROM DEPOT_LOCATIONS D " + 
					"WHERE D.DPTLOC_SubComp_id = 2 order by CFA1";
			Query query = em.createNativeQuery(q,Tuple.class);
			List<Tuple> tuples = query.getResultList();			
			if (tuples != null && !tuples.isEmpty()) {
				for (Tuple t : tuples) {
					Depot_Locations dl=new Depot_Locations();
					dl.setDptLoc_id(Long.valueOf(t.get("CFA1_ID",Integer.class)));
					dl.setDptLoc_name(t.get("CFA1",String.class));
					list.add(dl);
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
	public List<Depot_Locations> getCFALocationsThree(){
		EntityManager em = null;
		List<Depot_Locations> list = new ArrayList<Depot_Locations>();
		try {
			em = emf.createEntityManager();
			String q="SELECT D.DPTLOC_ID CFA1_ID,D.DPTLOC_NAME CFA1 FROM DEPOT_LOCATIONS D " + 
					"WHERE D.DPTLOC_SubComp_id = 3 order by CFA1";
			Query query = em.createNativeQuery(q,Tuple.class);
			List<Tuple> tuples = query.getResultList();			
			if (tuples != null && !tuples.isEmpty()) {
				for (Tuple t : tuples) {
					Depot_Locations dl=new Depot_Locations();
					dl.setDptLoc_id(Long.valueOf(t.get("CFA1_ID",Integer.class)));
					dl.setDptLoc_name(t.get("CFA1",String.class));
					list.add(dl);
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
	public Depot_Locations getObjectById(Long locId) throws Exception {
		return this.entityManager.find(Depot_Locations.class, locId);	}
}



