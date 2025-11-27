package com.excel.repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.Tuple;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

import org.apache.commons.collections4.map.HashedMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.RequestParam;

import com.excel.bean.SupplierBean;
import com.excel.model.HQ_Master;
import com.excel.model.Location;
import com.excel.model.SG_d_parameters_details;
import com.excel.model.Sub_Company;
import com.excel.model.Sub_Company_;
import com.excel.model.Supplier;
import com.excel.model.Supplier_;
import com.excel.model.TerrMaster;
import com.excel.utility.CodifyErrors;

@Repository
public class SupplierRepositoryImpl implements SupplierRepository {
	
	@Autowired EntityManagerFactory emf;
	@PersistenceContext private EntityManager entityManager;
	
	@Override
	public List<Supplier> getSuppliersBySupplierType(String supplierType, Long locId,
			Long subCompId) throws Exception{
		EntityManager em = null;
		List<Supplier> list = null;
		try {
			em = emf.createEntityManager();
			StringBuffer sb = new StringBuffer();
			
			
//			sb.append(" select s.sup_id AS sup_id, s.sup_nm AS sup_nm from Supplier s left join Location l on l.loc_sup_id = s.sup_id ");
//			sb.append(" where s.sup_status = 'A' and s.sup_type=:supp_type and (l.loc_id is null or l.loc_id <> :loc_id) and s.sup_SubComp_id=:sub_comp_id ");
//			sb.append(" order by s.sup_nm asc ");
//			
			sb.append(" select s.gst_reg_no AS gst_reg_no ,  s.sup_id AS sup_id, s.sup_nm AS sup_nm    from Supplier s left join Location l on l.loc_sup_id = s.sup_id ");
			sb.append(" where s.sup_status = 'A' and s.sup_type=:supp_type and (l.loc_id is null or l.loc_id <> :loc_id) and s.sup_SubComp_id=:sub_comp_id ");
			sb.append(" order by s.sup_nm asc ");
			
			
			Query query = em.createQuery(sb.toString(),Tuple.class);
			query.setParameter("supp_type", supplierType);
			query.setParameter("loc_id", locId);
			query.setParameter("sub_comp_id", subCompId);
			List<Tuple> tuples = query.getResultList();
			
			if (tuples != null && !tuples.isEmpty()) {
				list = new ArrayList<>();
				Supplier object = null;
				for (Tuple t : tuples) {
					object = new Supplier();
					object.setSup_id(t.get("sup_id", Long.class));
					object.setSup_nm(t.get("sup_nm", String.class));
					object.setGst_reg_no(t.get("gst_reg_no", String.class));
					list.add(object);
				}
				System.out.println("Supplier"+list.size());
			if(!list.isEmpty() && list.size() > 0)
				return list;
			}
		} finally {
			if (em != null) { em.close(); }
		}
		return list;
	}
	
	
	@Override
	public Supplier supplierStateGstNo(Long sup_id) {
		

		EntityManager em = null;
		Supplier supplier = null;
		try {
			em = emf.createEntityManager();
			CriteriaBuilder builder = em.getCriteriaBuilder();
			CriteriaQuery<Supplier> query = builder.createQuery(Supplier.class);
			Root<Supplier> root = query.from(Supplier.class);
			query.multiselect(root.get(Supplier_.state_id), root.get(Supplier_.gst_reg_no),root.get(Supplier_.gst_req_ind)).where(
					builder.and(
							builder.equal(root.get(Supplier_.sup_id),sup_id)
							));
			List<Supplier> list= em.createQuery(query).getResultList();
			if(list != null && !list.isEmpty()) {
				supplier = list.get(0);
			}
		} finally {
			if(em != null) { em.close(); }
		}
		
		return supplier;
	}
	
	@Override
	public List<Object> getAllActiveSupplier(String subComp) {
		System.out.println("sub company id in repository "+subComp);
		EntityManager em = null;
		List<Object> list = new ArrayList<Object>();
		try {
			em = emf.createEntityManager();
			String q="select sup_id , sup_nm, sup_type from supplier where sup_status='A' and sup_subComp_id=:subComp order by sup_nm asc";
			
			Query query = em.createNativeQuery(q,Tuple.class);
			query.setParameter("subComp", subComp);
			List<Tuple> tuples = query.getResultList();
			
			if (tuples != null && !tuples.isEmpty()) {
				for (Tuple t : tuples) {
					Supplier sup=new Supplier();
					sup.setSup_id(Long.valueOf(t.get("sup_id",Integer.class)));
					sup.setSup_nm(t.get("sup_nm",String.class));
					list.add(sup);
				}
			}
		}
		catch (Exception e) {
			System.out.println("Error Occurred :" +e.getMessage());// uncomment asneeded --;
		}
		finally {
			if(em != null) { em.close(); }
		}
		return list;
	}
	@Override
	public Boolean checkUniqueSupplier(String SupName,String SupType,String SubCompany){
		Boolean data=null;
		
		EntityManager em = null;
		List<Object> list = new ArrayList<Object>();
		try {
			em = emf.createEntityManager();
			String q="select sup_id,GST_REG_NO,GST_REQ_IND,STATE_ID,sup_SubComp_id,SUP_ADDRESS1,SUP_ADDRESS2,SUP_ADDRESS3,sup_cst_no,sup_ins_dt,sup_ins_ip_add,sup_ins_usr_id,sup_map_code,sup_mod_dt,sup_mod_ip_add,sup_mod_usr_id,sup_name_old,sup_nm,sup_pan_no,sup_status,sup_tan_no,sup_tin_no,sup_type,"
					+ "SubComp_id,SubComp_code,SubComp_Nm,SubComp_status from supplier left outer join sub_company on sup_SubComp_id=SubComp_id "
					+ "where sup_nm=:SupName and sup_type=:SupType AND sup_SubComp_id=:SubCompany";
			Query query = em.createNativeQuery(q,Tuple.class);
			query.setParameter("SupName", SupName);
			query.setParameter("SupType", SupType);
			query.setParameter("SubCompany", SubCompany);
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
	public Boolean checkUniqueSupplierNameForModify(String SupName,String SupType,String SubCompany,String SupId){
		Boolean data=null;
		
		EntityManager em = null;
		List<Object> list = new ArrayList<Object>();
		try {
			em = emf.createEntityManager();
			String q="select sup_id,GST_REG_NO,GST_REQ_IND,STATE_ID,sup_SubComp_id,SUP_ADDRESS1,SUP_ADDRESS2,SUP_ADDRESS3,sup_cst_no,sup_ins_dt,sup_ins_ip_add,sup_ins_usr_id,sup_map_code,sup_mod_dt,sup_mod_ip_add,sup_mod_usr_id,sup_name_old,sup_nm,sup_pan_no,sup_status,sup_tan_no,sup_tin_no,sup_type,"
					+ "SubComp_id,SubComp_code,SubComp_Nm,SubComp_status from supplier left outer join sub_company on sup_SubComp_id=SubComp_id "
					+ "where sup_nm=:SupName and sup_type=:SupType AND sup_SubComp_id=:SubCompany and sup_id<>:SupId";
			Query query = em.createNativeQuery(q,Tuple.class);
			query.setParameter("SupName", SupName);
			query.setParameter("SupType", SupType);
			query.setParameter("SubCompany", SubCompany);
			query.setParameter("SupId", SupId);
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
	public Object checkUniqueGST(String Gst) {
		if(Gst.substring(1, 2).equals("27")) {
			System.out.println(true);
		}
		return Gst;
	}
	@Override
	public List<Supplier> getAllActiveSupplierList(String status){
		EntityManager em = null;
		List<Supplier> list = new ArrayList<Supplier>();
		try {
			em = emf.createEntityManager();
			Query query = null;
			status = status.trim();

			String q="select s.sup_id, p.SGprmdet_disp_nm, s.sup_nm, s.sup_map_code from Supplier s " + 
					"left join SG_d_parameters_details p on s.sup_type = p.SGprmdet_nm left join " + 
					"SG_m_parameters m on p.SGprmdet_SGprm_id = m.SGprm_id where m.SGprm_nm='Supplier Type' "
					+ " and s.sup_status='"+status+"' order by s.sup_nm,s.sup_map_code";
			query = em.createNativeQuery(q,Tuple.class);
			List<Tuple> tuples = query.getResultList();
			
			if (tuples != null && !tuples.isEmpty()) {
				for (Tuple t : tuples) {
					Supplier sup=new Supplier();
					sup.setSup_id(Long.valueOf(t.get("sup_id",Integer.class)));
					sup.setSup_nm(t.get("sup_nm",String.class));
					sup.setSup_type(t.get("sgprmdet_disp_nm",String.class));
					sup.setSup_map_code(t.get("sup_map_code",String.class));
					list.add(sup);
				}
			}
			} finally {
				if (em != null) { em.close(); }
		}
		return list;
	}
	@Override
	public List<Supplier> getTextParameterSupplierList(String status, String name, String parameter, String text){
		EntityManager em = null;
		List<Supplier> list = new ArrayList<Supplier>();
		try {
			em = emf.createEntityManager();
			Query query = null;
			
			System.out.println("detail : "+name+"...."+parameter+"..."+text);
			status = status.trim();

			if(name.equals("SN")) {
				String q="select s.sup_id, p.SGprmdet_disp_nm, s.sup_nm, s.sup_map_code from Supplier s " + 
						"left join SG_d_parameters_details p on s.sup_type = p.SGprmdet_nm left join " + 
						"SG_m_parameters m on p.SGprmdet_SGprm_id = m.SGprm_id where m.SGprm_nm='Supplier Type' " +
						" and s.sup_status='"+status+"' and s.sup_nm "+parameter+"  '%"+text+"%' order by s.sup_nm,s.sup_map_code";
				query = em.createNativeQuery(q,Tuple.class);
			}
			if(name.equals("ST")) {
				String q="select s.sup_id, p.SGprmdet_disp_nm, s.sup_nm, s.sup_map_code from Supplier s " + 
						"left join SG_d_parameters_details p on s.sup_type = p.SGprmdet_nm left join " + 
						"SG_m_parameters m on p.SGprmdet_SGprm_id = m.SGprm_id where m.SGprm_nm='Supplier Type' " +
						" and s.sup_status='"+status+"' and p.SGprmdet_disp_nm "+parameter+"  '%"+text+"%' order by s.sup_nm,s.sup_map_code";
				query = em.createNativeQuery(q,Tuple.class);
			}
			if(name.equals("SC")){
				String q="select s.sup_id, p.SGprmdet_disp_nm, s.sup_nm, s.sup_map_code from Supplier s " + 
						"left join SG_d_parameters_details p on s.sup_type = p.SGprmdet_nm left join " + 
						"SG_m_parameters m on p.SGprmdet_SGprm_id = m.SGprm_id where m.SGprm_nm='Supplier Type' " +
						" and s.sup_status='"+status+"' and s.sup_map_code "+parameter+"  '%"+text+"%' order by s.sup_nm,s.sup_map_code";
				query = em.createNativeQuery(q,Tuple.class);
			}
			
			List<Tuple> tuples = query.getResultList();
			
			if (tuples != null && !tuples.isEmpty()) {
				for (Tuple t : tuples) {
					Supplier sup=new Supplier();
					sup.setSup_id(Long.valueOf(t.get("sup_id",Integer.class)));
					sup.setSup_nm(t.get("sup_nm",String.class));
					sup.setSup_type(t.get("sgprmdet_disp_nm",String.class));
					sup.setSup_map_code(t.get("sup_map_code",String.class));
					list.add(sup);
				}
			}
			} finally {
				if (em != null) { em.close(); }
		}
		return list;
	}
	@Override
	public Supplier getObjectById(Long supId) throws Exception {
		return this.entityManager.find(Supplier.class, supId);
	}
	@Override
	public List<Supplier> getSupplierDetailById(String supId) {
		EntityManager em = null;
		List<Supplier> list = new ArrayList<Supplier>();
		try {
			em = emf.createEntityManager();
			Query query = null;
				
			String q="from Supplier WHERE sup_id="+supId;
			query = em.createQuery(q);
			list=query.getResultList();
			System.out.println(list.get(0).getSup_map_code());
			System.out.println(list.get(0).getSup_nm());
			} finally {
				if (em != null) { em.close(); }
		}
		return list;
	}
}
