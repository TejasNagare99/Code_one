package com.excel.repository;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.Tuple;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.excel.bean.MailBean;
import com.excel.bean.Parameter;
import com.excel.bean.StockTransferBean;
import com.excel.model.SamplProdGroup;
import com.excel.model.UserDepartment;
import com.excel.model.UserDepartment_;
import com.excel.model.UserGroup;
import com.excel.model.Usermaster;
import com.excel.model.Usermaster_;
import com.excel.utility.CodifyErrors;
import com.itextpdf.text.pdf.PRAcroForm;

@Repository
public class UserDepartmentRepositoryImpl implements UserDepartmentRepository{
	@Autowired private EntityManagerFactory emf;
	@PersistenceContext private EntityManager entityManager;
	
	@Override
	public List<UserDepartment> getDepartments() throws Exception {
		EntityManager em = null;
		List<UserDepartment> list = null;
		try {
			em = emf.createEntityManager();
			CriteriaBuilder builder = em.getCriteriaBuilder();
			CriteriaQuery<UserDepartment> criteriaQuery = builder.createQuery(UserDepartment.class);
			Root<UserDepartment> root = criteriaQuery.from(UserDepartment.class);
			criteriaQuery.select(root).where(
					builder.and(
							builder.equal(root.get(UserDepartment_.dept_status), "A")
					));
			list = em.createQuery(criteriaQuery).getResultList();
			if(list != null && !list.isEmpty()) {
				return list;
			}
		} finally {
			if(em != null) { em.close(); }
		}
		return list;
	}
	
	@Override
	public List<Parameter> getActiveUserList() throws Exception{
		EntityManager em = null;
		List<Parameter> list=new ArrayList<>();
		try {
			em = emf.createEntityManager();
			StringBuffer sb = new StringBuffer();
			sb.append(" select emp_id, CONCAT(emp_fnm, ' ', emp_mnm,' ',emp_lnm) as emp_name ");
			sb.append(" from hr_m_employee where emp_status='A' order by emp_fnm ");
			Query query = em.createNativeQuery(sb.toString(), Tuple.class);
			List<Tuple> tuples = query.getResultList();
			Parameter st = null;
			if (tuples != null && !tuples.isEmpty()) {
				for(Tuple t : tuples) {
					st=new Parameter();
					st.setId(t.get("emp_id", String.class));
					st.setName(t.get("emp_name", String.class));
					list.add(st);
				}
			}
		
		} catch (Exception e) {
			System.out.println("Error Occurred :" +e.getMessage());// uncomment asneeded --;
		} finally {
			if (em != null) { em.close(); }
		}
		return list;

	
	}
	
	@Override
	public List<Parameter> getActiveDesignation() throws Exception{
		EntityManager em = null;
		List<Parameter> list=list=new ArrayList<>();
		try {
			em = emf.createEntityManager();
			StringBuffer sb = new StringBuffer();
			sb.append("select desg_id, desg_nm from hr_m_designation  ");
			sb.append("where desg_status='A' order by desg_nm ");
			Query query = em.createNativeQuery(sb.toString(), Tuple.class);
			List<Tuple> tuples = query.getResultList();
			Parameter st = null;
			if (tuples != null && !tuples.isEmpty()) {
				for(Tuple t : tuples) {
					st=new Parameter();
					st.setId(t.get("desg_id", String.class));
					st.setName(t.get("desg_nm", String.class));
					list.add(st);
				}
			}
		
		} catch (Exception e) {
			System.out.println("Error Occurred :" +e.getMessage());// uncomment asneeded --;
		} finally {
			if (em != null) { em.close(); }
		}
		return list;

	
	}

	@Override
	public List<Parameter> getActiveUserDtl() throws Exception{
		EntityManager em = null;
		List<Parameter> list=list=new ArrayList<>();
		try {
			em = emf.createEntityManager();
			StringBuffer sb = new StringBuffer();
			sb.append(" select egrp_id,egrp_nm from  hr_m_employee_group where egrp_status='A' order by egrp_nm");
			Query query = em.createNativeQuery(sb.toString(), Tuple.class);
			List<Tuple> tuples = query.getResultList();
			Parameter st = null;
			if (tuples != null && !tuples.isEmpty()) {
				for(Tuple t : tuples) {
					st=new Parameter();
					st.setId(t.get("egrp_id", String.class));
					st.setName(t.get("egrp_nm", String.class));
					list.add(st);
				}
			}
		
		} catch (Exception e) {
			System.out.println("Error Occurred :" +e.getMessage());// uncomment asneeded --;
		} finally {
			if (em != null) { em.close(); }
		}
		return list;
	}

	
	@Override
	public Integer getMaxUserKey() throws Exception{
		EntityManager em = null;
		Integer userKey=0;
		try {
			em = emf.createEntityManager();
			StringBuffer sb = new StringBuffer();
			sb.append("select max(emp_index) as emp_index from hr_m_employee");
			Query query = em.createNativeQuery(sb.toString(), Tuple.class);
			List<Tuple> tuples = query.getResultList();
			if (tuples != null && !tuples.isEmpty()) {
				for(Tuple t : tuples) {
					userKey=t.get("emp_index", Integer.class);
				}
				return userKey;
			}
		
		} catch (Exception e) {
			System.out.println("Error Occurred :" +e.getMessage());// uncomment asneeded --;
		} finally {
			if (em != null) { em.close(); }
		}
		return userKey;
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public void insertEmpLocAccess( String emp_id, Long loc_id, 
			String ins_user_id, Date ins_date, String ins_ip_addr) throws Exception{
		try{
			StringBuffer buffer = new StringBuffer();
			buffer.append("insert into am_m_emp_loc_access(elra_emp_id, elra_loc_id, ")
					.append("elra_ins_usr_id, elra_ins_dt, elra_ins_ip_add, elra_status) ")
					.append("values(:emp_id, :loc_id, :ins_user_id, :ins_date, :ins_ip_addr, :status) ");
			Query query=entityManager.createNativeQuery(buffer.toString());
			query.setParameter("emp_id", emp_id)
				.setParameter("loc_id", loc_id)
				.setParameter("ins_user_id", ins_user_id)
				.setParameter("ins_date", ins_date)
				.setParameter("ins_ip_addr", ins_ip_addr)
				.setParameter("status", "A");
			query.executeUpdate();
			
		}catch(Exception e){
			throw e;
		}
	}
	
	@Override
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public void insertEmpDivAccess(String emp_id, Long div_id, 
			String ins_user_id, Date ins_date, String ins_ip_addr) throws Exception{
		try{
			StringBuffer buffer = new StringBuffer();
			buffer.append("insert into am_m_emp_div_access(ediv_emp_id, ediv_div_id, ")
					.append("ediv_ins_usr_id, ediv_ins_dt, ediv_ins_ip_add, ediv_status) ")
					.append("values(:emp_id, :div_id, :ins_user_id, :ins_date, :ins_ip_addr, :status) ");
					
			Query query=entityManager.createNativeQuery(buffer.toString());
			query.setParameter("emp_id", emp_id)
				.setParameter("div_id", div_id)
				.setParameter("ins_user_id", ins_user_id)
				.setParameter("ins_date", ins_date)
				.setParameter("ins_ip_addr", ins_ip_addr)
				.setParameter("status", "A");
			query.executeUpdate();
			
		}catch(Exception e){
			throw e;
		}
	}
	
	@Override
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public void insertEmpDivReportAccess(String emp_id, Long div_id, 
			String ins_user_id, Date ins_date, String ins_ip_addr) throws Exception{
		try{
			StringBuffer buffer = new StringBuffer();
			buffer.append("insert into am_m_emp_div_report_access(edivr_emp_id, edivr_div_id, ")
					.append("edivr_ins_usr_id, edivr_ins_dt, edivr_ins_ip_add, edivr_status) ")
					.append("values(:emp_id, :div_id, :ins_user_id, :ins_date, :ins_ip_addr, :status) ");
										
			Query query=entityManager.createNativeQuery(buffer.toString());
			query.setParameter("emp_id", emp_id)
				.setParameter("div_id", div_id)
				.setParameter("ins_user_id", ins_user_id)
				.setParameter("ins_date", ins_date)
				.setParameter("ins_ip_addr", ins_ip_addr)
				.setParameter("status", "A");
			query.executeUpdate();
			
		}catch(Exception e){
			throw e;
		}
	}
	
	@Override
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public void saveProductType(String emp_id, String prod_type, 
			Long prod_type_id, String ins_user_id, Date ins_date, String ins_ip_addr) throws Exception{
		try{
			StringBuffer buffer = new StringBuffer();
			buffer.append("insert into am_m_EmpProdType_access(ProdType_Emp_id, ProdType_SMPProdType, ")
					.append("ProdType_ins_usr_id, ProdType_ins_dt, ProdType_ins_ip_add, ProdType_status, ProdType_SMPProdType_ID) ")
					.append("values(:emp_id, :prod_type, :ins_user_id, :ins_date, :ins_ip_addr, :status, :prod_type_id) ");
					
			Query query=entityManager.createNativeQuery(buffer.toString());
			query.setParameter("emp_id", emp_id)
				.setParameter("prod_type", prod_type)
				.setParameter("ins_user_id", ins_user_id)
				.setParameter("ins_date", ins_date)
				.setParameter("ins_ip_addr", ins_ip_addr)
				.setParameter("status", "A")
				.setParameter("prod_type_id", prod_type_id);
			query.executeUpdate();
			
		}catch(Exception e){
			throw e;
		}
	}
	
	@Override
	public List<Long> getEmpLocAccess( String emp_id) throws Exception{
		List<Long> list=new ArrayList<>();
		Parameter para=null;
		try{
			StringBuffer buffer = new StringBuffer();
			buffer.append("select distinct elra_loc_id as locId from am_m_emp_loc_access where elra_emp_id=:emp_id");
			Query query = entityManager.createNativeQuery(buffer.toString(),Tuple.class);
			query.setParameter("emp_id", emp_id);
			List<Tuple> tuples = query.getResultList();
			if (tuples != null && !tuples.isEmpty()) {
				for(Tuple t : tuples) {
					list.add(Long.valueOf(t.get("locId", Integer.class)));
				}
			}
			
		}catch(Exception e){
			throw e;
		}
		return list;
	}

	@Override
	public List<Long> getEmpDivAccess( String emp_id) throws Exception{
		List<Long> list=new ArrayList<>();
		try{
			StringBuffer buffer = new StringBuffer();
			buffer.append("select distinct ediv_div_id as divId from am_m_emp_div_access where ediv_emp_id=:emp_id");
			Query query = entityManager.createNativeQuery(buffer.toString(),Tuple.class);
			query.setParameter("emp_id", emp_id);
			List<Tuple> tuples = query.getResultList();
			if (tuples != null && !tuples.isEmpty()) {
				for(Tuple t : tuples) {
					list.add(Long.valueOf(t.get("divId", Integer.class)));
				}
			}
			
		}catch(Exception e){
			throw e;
		}
		return list;
	}
	
	@Override
	public List<Long> getEmpDivReportAccess(String emp_id) throws Exception{
		List<Long> list=new ArrayList<>();
		try{
			StringBuffer buffer = new StringBuffer();
			buffer.append("select distinct edivr_div_id as divId from am_m_emp_div_report_access where edivr_emp_id=:emp_id");
			Query query = entityManager.createNativeQuery(buffer.toString(),Tuple.class);
			query.setParameter("emp_id", emp_id);
			List<Tuple> tuples = query.getResultList();
			if (tuples != null && !tuples.isEmpty()) {
				for(Tuple t : tuples) {
					list.add(Long.valueOf(t.get("divId", Integer.class)));
				}
			}
			
		}catch(Exception e){
			throw e;
		}
		return list;
	}
	
	@Override
	public List<Long> getEmpProdTypeAccess(String emp_id) throws Exception{
		List<Long> list=new ArrayList<>();
		try{
			StringBuffer buffer = new StringBuffer();
			buffer.append("select distinct ProdType_SMPProdType_ID as prodctType from am_m_EmpProdType_access where ProdType_Emp_id=:emp_id");
			Query query = entityManager.createNativeQuery(buffer.toString(),Tuple.class);
			query.setParameter("emp_id", emp_id);
			List<Tuple> tuples = query.getResultList();
			if (tuples != null && !tuples.isEmpty()) {
				for(Tuple t : tuples) {
					list.add(Long.valueOf(t.get("prodctType", Integer.class)));
				}
			}
			
		}catch(Exception e){
			throw e;
		}
		return list;
	 }
	
	@Override
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public void deleteProductType(String emp_id, List<String> del_id) throws Exception{
		try{
			StringBuffer buffer = new StringBuffer();
			buffer.append("delete from am_m_EmpProdType_access where ProdType_Emp_id=:emp_id  ");//and ProdType_SMPProdType_ID in (:del_id)
			Query query = entityManager.createNativeQuery(buffer.toString());
			query.setParameter("emp_id", emp_id);
			//query.setParameter("del_id", del_id);
			//System.out.println("deleteProductType: "+buffer);
			query.executeUpdate();
			
		}catch(Exception e){
			throw e;
		}
	}
	
	@Override
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public void deleteEmpLocAccess(String emp_id, List<String> del_id) throws Exception{
		try{
			StringBuffer buffer = new StringBuffer();
			buffer.append("delete from am_m_emp_loc_access where elra_emp_id=:emp_id ");
			Query query = entityManager.createNativeQuery(buffer.toString());
			query.setParameter("emp_id", emp_id);
			//query.setParameter("del_id", del_id);
			//System.out.println("deleteEmpLocAccess: "+buffer);
			query.executeUpdate();
			
		}catch(Exception e){
			throw e;
		}
	}
	
	@Override
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public void deleteEmpDivAccess(String emp_id, List<String> del_id) throws Exception{
		try{
			StringBuffer buffer = new StringBuffer();
			buffer.append("delete from am_m_emp_div_access where ediv_emp_id=:emp_id");
			Query query = entityManager.createNativeQuery(buffer.toString());
			query.setParameter("emp_id", emp_id);
			//query.setParameter("del_id", del_id);
			//System.out.println("deleteEmpDivAccess: "+buffer);
			query.executeUpdate();
			
		}catch(Exception e){
			throw e;
		}
	}
	
	@Override
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public void deleteEmpDivReportAccess(String emp_id, List<String> del_id) throws Exception{
		try{
			StringBuffer buffer = new StringBuffer();
			buffer.append("delete from am_m_emp_div_report_access where edivr_emp_id=:emp_id ");
			Query query = entityManager.createNativeQuery(buffer.toString());
			query.setParameter("emp_id", emp_id);
			//query.setParameter("del_id", del_id);
			//System.out.println("deleteEmpDivReportAccess: "+buffer);
			query.executeUpdate();
			
		}catch(Exception e){
			throw e;
		}
	}

	
	@Override
	public List<Parameter> getUserByRole(String role) throws Exception{
		EntityManager em = null;
		List<Parameter> list=list=new ArrayList<>();
		try {
			em = emf.createEntityManager();
			StringBuffer sb = new StringBuffer();
			sb.append(" select eg.egrp_nm,l.ld_id,l.ld_emp_cb_id,h.emp_fnm,h.emp_lnm,d.desg_nm");
			sb.append(" from am_m_login_detail l , hr_m_employee_group eg,hr_m_employee h left outer join hr_m_designation d on d.desg_id = h.emp_desg_id");
			sb.append(" where l.ld_emp_cb_id = h.emp_id");
			sb.append(" and h.emp_egrp_id =:role and eg.egrp_id = h.emp_egrp_id");
			sb.append(" order by d.desg_nm,h.emp_fnm,h.emp_lnm");
			Query query = em.createNativeQuery(sb.toString(), Tuple.class);
			query.setParameter("role", role);
			List<Tuple> tuples = query.getResultList();
			Parameter st = null;
			if (tuples != null && !tuples.isEmpty()) {
				for(Tuple t : tuples) {
					st=new Parameter();
					st.setId(t.get("ld_emp_cb_id", String.class));
					st.setName(t.get("emp_fnm", String.class)+" "+t.get("emp_lnm", String.class));
					list.add(st);
				}
			}
		
		} catch (Exception e) {
			System.out.println("Error Occurred :" +e.getMessage());// uncomment asneeded --;
		} finally {
			if (em != null) { em.close(); }
		}
		return list;
	}
	
	@Override
	public List<Long> getUserExistingBrand(String userId) throws Exception{
		EntityManager em = null;
		List<Long> list=null;
		System.out.println("userId "+userId);
		try {
			em = emf.createEntityManager();
			StringBuffer sb = new StringBuffer();
		      sb.append(" select sag.SA_PROD_GROUP,sag.SA_GROUP_NAME ");
				sb.append(" from SAPRODGRP sag , USER_SKU_MAP u ");
				sb.append(" where U.SMP_SA_PROD_GROUP = sag.SA_PROD_GROUP ");
				 sb.append(" and u.USERID =( select ld_id from am_m_login_detail where ld_emp_cb_id=:userId) ");
				sb.append("  order by sag.SA_GROUP_NAME ");
			
		
			Query query = em.createNativeQuery(sb.toString(), Tuple.class);
			query.setParameter("userId", userId);
			List<Tuple> tuples = query.getResultList();
			if (tuples != null && !tuples.isEmpty()) {
				list=new ArrayList<>();
				for(Tuple t : tuples) {
					list.add(Long.valueOf(t.get("SA_PROD_GROUP", Integer.class)));
				}
			}
		
		} catch (Exception e) {
			System.out.println("Error Occurred :" +e.getMessage());// uncomment asneeded --;
		} finally {
			if (em != null) { em.close(); }
		}
		return list;
	}

	@Override
	public List<SamplProdGroup> getBrandsByDivisionOfUser(String empId) throws Exception{
		EntityManager em = null;
		List<SamplProdGroup> list=new ArrayList<>();
		System.out.println("EmpID "+empId);
		try {
			em = emf.createEntityManager();
			StringBuffer sb = new StringBuffer();
			sb.append(" select sag.SA_PROD_GROUP,sag.SA_GROUP_NAME");
			sb.append(" from SAPRODGRP sag , am_m_emp_div_access dv , ( select distinct SMP_STD_DIV_ID,SMP_SA_PROD_GROUP from SMPITEM WHERE SMP_status = 'A' ) pr ");
			sb.append(" where dv.ediv_emp_id =:empId");
			sb.append(" and dv.ediv_div_id = pr.SMP_STD_DIV_ID");
			sb.append(" and pr.SMP_SA_PROD_GROUP = sag.SA_PROD_GROUP");
			sb.append(" group by sag.SA_GROUP_NAME,sag.SA_PROD_GROUP");
			Query query = em.createNativeQuery(sb.toString(), Tuple.class);
			query.setParameter("empId", empId);
			List<Tuple> tuples = query.getResultList();
			SamplProdGroup st = null;
			if (tuples != null && !tuples.isEmpty()) {
				for(Tuple t : tuples) {
					st=new SamplProdGroup();
					st.setSa_prod_group(Long.valueOf(t.get("SA_PROD_GROUP", Integer.class)));
					st.setSa_group_name(t.get("SA_GROUP_NAME", String.class));
					list.add(st);
				}
			}
		
		} catch (Exception e) {
			System.out.println("Error Occurred :" +e.getMessage());// uncomment asneeded --;
		} finally {
			if (em != null) { em.close(); }
		}
		return list;
	}
	
	@Override
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public void deleteEmpBrandAccess(Long userid) throws Exception{
		try{
			StringBuffer buffer = new StringBuffer();
			buffer.append("delete from USER_SKU_MAP where userid=:userid");
			Query query = entityManager.createNativeQuery(buffer.toString());
			query.setParameter("userid", userid);
			//query.setParameter("del_id", del_id);
			//System.out.println("deleteEmpDivAccess: "+buffer);
			query.executeUpdate();
			
		}catch(Exception e){
			throw e;
		}
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public void getDispatchLockinRelease() throws Exception {
		try{
			StringBuffer buffer = new StringBuffer();
			buffer.append("Update location set autodesp_inprocess='N' ");
			Query query = entityManager.createNativeQuery(buffer.toString());
			query.executeUpdate();
			
		}catch(Exception e){
			throw e;
		}
	}

	@Override
	public String getDesignationById(String id) throws Exception {
		EntityManager em = null;
		String desg=null;
		try {
			System.out.println("bean.getDesg_id() : "+id);
			em = emf.createEntityManager();
			StringBuffer sb = new StringBuffer();
			sb.append("select egrp_nm from hr_m_employee_group ");
			sb.append(" where egrp_id=:id order by egrp_nm ");
			Query query = em.createNativeQuery(sb.toString());
			query.setParameter("id", id);
			desg = (String) query.getSingleResult();
		} catch (Exception e) {
			System.out.println("Error Occurred :" +e.getMessage());// uncomment asneeded --;
		} finally {
			if (em != null) { em.close(); }
		}
		return desg;

	
	}


	@Override
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public void getDispatch_specific_LockinRelease(List<Long> locId) {
		String locations="";
		for(Long l : locId){locations+=l+",";}
		locations=locations.substring(0,locations.length()-1);
		
		try{
			StringBuffer buffer = new StringBuffer();
			buffer.append("Update location set autodesp_inprocess='N'  where loc_id in ("+locations+")");
			Query query = entityManager.createNativeQuery(buffer.toString());
			query.executeUpdate();
			
		}catch(Exception e){
			e.printStackTrace();
			throw e;
		}
		
	}
}
