package com.excel.repository;

import java.math.BigDecimal;
import java.sql.Time;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.StoredProcedureQuery;
import javax.persistence.Tuple;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

import org.apache.commons.lang3.time.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.excel.bean.MailBean;
import com.excel.bean.Parameter;
import com.excel.bean.UserMasterBean;
import com.excel.model.EmployeeDetails;
import com.excel.model.EmployeeDetails_;
import com.excel.model.LoginDetails;
import com.excel.model.Password_Log;
import com.excel.model.SG_d_parameters_details;
import com.excel.model.Usermaster;
import com.excel.model.Usermaster_;
import com.excel.security.PasswordManager;
import com.excel.utility.CodifyErrors;
import com.excel.utility.MedicoConstants;
import com.excel.utility.MedicoResources;
import com.excel.utility.Utility;


@Repository
public class UserMasterRepositoryImpl implements UserMasterRepository, MedicoConstants {
	
	@Autowired private EntityManagerFactory emf;
	@PersistenceContext private EntityManager entityManager;
	@Autowired private UserDepartmentRepository userDepartmentRepository;
	
	@Override
	public Usermaster getUserByUsername(String username) throws Exception {
		EntityManager em = null;
		Usermaster user = null;
		try {

			CriteriaBuilder builder = entityManager.getCriteriaBuilder();
			CriteriaQuery<Usermaster> criteriaQuery = builder.createQuery(Usermaster.class);
			Root<Usermaster> root = criteriaQuery.from(Usermaster.class);
			criteriaQuery.select(root).where(
					builder.equal(root.get(Usermaster_.ld_lgnid), username)
					);
			List<Usermaster> list = entityManager.createQuery(criteriaQuery).getResultList();
			if(list != null && !list.isEmpty()) {
				user = list.get(0);
			}
		} finally {
			//if(em != null) { em.close(); }
		}
		return user;
	}
	
	@Override
	public String  getLfPass(String username) throws Exception {
		EntityManager em = null;
		String ldPass = null;
		try {

			CriteriaBuilder builder = entityManager.getCriteriaBuilder();
			CriteriaQuery<String> criteriaQuery = builder.createQuery(String.class);
			Root<Usermaster> root = criteriaQuery.from(Usermaster.class);
			criteriaQuery.multiselect((root.get(Usermaster_.ld_pass))).where(
					builder.equal(root.get(Usermaster_.ld_lgnid), username)
					);
			ldPass= entityManager.createQuery(criteriaQuery).getSingleResult();
			
		} finally {
			//if(em != null) { em.close(); }
		}
		return ldPass;
	}


	@Override
	public Usermaster getUserByUsernameAndPassword(String username, String password) throws Exception {
		EntityManager em = null;
		Usermaster user = null;
		try {
			em = emf.createEntityManager();
			CriteriaBuilder builder = em.getCriteriaBuilder();
			CriteriaQuery<Usermaster> criteriaQuery = builder.createQuery(Usermaster.class);
			Root<Usermaster> root = criteriaQuery.from(Usermaster.class);
			criteriaQuery.select(root).where(
					builder.and(
							builder.equal(root.get(Usermaster_.ld_lgnid), username),
							builder.equal(root.get(Usermaster_.ld_pass), password)
							)
					);
			List<Usermaster> list = em.createQuery(criteriaQuery).getResultList();
			if(list != null && !list.isEmpty()) {
				user = list.get(0);
			}
		} finally {
			if(em != null) { em.close(); }
		}
		return user;
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public void lockOrUnlockUser(String userId, boolean lock) throws Exception {
		Query query = entityManager.createQuery("update Usermaster set user_lock = :lock where ld_emp_cb_id = :user_id");
		if(lock) {
			query.setParameter("lock", Y);
		} else {
			query.setParameter("lock", N);
		}
		query.setParameter("user_id", userId);
		query.executeUpdate();
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public void unlockAllUsers() throws Exception {
		Query query = entityManager.createQuery("update Usermaster set user_lock = 'N' where user_lock = 'Y'");
		query.executeUpdate();
	}

	@Override
	public List<Usermaster> getLockedUsers(String levelCode) throws Exception {
		//EntityManager em = null;
		//List<Usermaster> list = null;
//		try {
//			em = emf.createEntityManager();
//			CriteriaBuilder builder = em.getCriteriaBuilder();
//			CriteriaQuery<Usermaster> criteriaQuery = builder.createQuery(Usermaster.class);
//			Root<Usermaster> root = criteriaQuery.from(Usermaster.class);
//			criteriaQuery.multiselect(
//					root.get(UserMaster_.user_id),
//					root.get(UserMaster_.user_name),
//					root.get(UserMaster_.fs_id),
//					root.get(UserMaster_.fs_name),
//					root.get(UserMaster_.level_code)
//					).where(
//					builder.and(
//							builder.equal(root.get(UserMaster_.level_code), levelCode),
//							builder.equal(root.get(UserMaster_.user_lock), Y)
//							)
//					);
//			list = em.createQuery(criteriaQuery).getResultList();
//		} finally {
//			if(em != null) { em.close(); }
//		}
//		if(list != null && !list.isEmpty()) {
//			return list;
//		}
		return null;
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public void unlockorLockUsersByUsernames(List<String> usernames, String lockUnlock) throws Exception {
		entityManager.createQuery(new StringBuffer().append("UPDATE Usermaster SET user_lock = 'N',password_lock='N' WHERE ld_lgnid in (:usernames)").toString())
		.setParameter("usernames", usernames).executeUpdate();
	}

//	@SuppressWarnings("unchecked")
//	@Override
//	public List<UserMasterBean> getActiveUsersListByDiv(Long divId, String companyCode, String levelCode) throws Exception {
//		EntityManager em = null;
//		List<UserMasterBean> list = null;
//		try {
//			em = emf.createEntityManager();
//			StringBuffer sb = new StringBuffer();
//			sb.append(" select um.user_id as user_id, um.fs_name as field_staff_name from Usermaster AS um LEFT JOIN FieldMaster AS fm ");
//			sb.append(" on um.fs_id = fm.fs_id where fm.div_id=:div_id AND um.user_status='A' ");
//			if(levelCode != null) {
//				sb.append(" and fm.level_code=:level_code ");
//			}
//			sb.append(" order by um.fs_name ");
//			Query query = em.createQuery(sb.toString(), Tuple.class);
//			query.setParameter("div_id", divId);
//			if(levelCode != null) {
//				query.setParameter("level_code", levelCode);
//			}
//			List<Tuple> tuples = query.getResultList();
//			if(tuples != null && !tuples.isEmpty()) {
//				list = new ArrayList<UserMasterBean>();
//				for(Tuple t : tuples) {
//					UserMasterBean bean = new UserMasterBean();
//					bean.setField_staff_name(t.get("field_staff_name", String.class));
//					bean.setUserId(t.get("user_id", Long.class));
//					list.add(bean);
//				}
//			}
//		} finally {
//			if (em != null) { em.close(); }
//		}
//		if (list != null && !list.isEmpty()) {
//			return list;
//		}
//		return null;
//	}

	//old query
//	@Override
//	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
//	public void updatePasswordByUsername(String username, String newPassword) throws Exception {
//		entityManager
//		.createQuery(new StringBuffer()
//				.append("UPDATE Usermaster SET enc_password = :enc_password, hash_password = :hash WHERE user_name = :username").toString()
//				)
//		.setParameter("username", username)
//		.setParameter("enc_password", PasswordManager.encrypt(PasswordManager.encrypt(newPassword)))
//		.setParameter("hash", new BCryptPasswordEncoder().encode(newPassword))
//		.executeUpdate();
//	}
	
	@Override
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public void updatePasswordByUsername(String username, String newPassword) throws Exception {
		System.out.println("username::"+username);
		int salt = this.getSaltvalByUsername(username);
//		System.out.println(" encrypted pass::"+PasswordManager.encrypt(PasswordManager.encrypt(newPassword,salt),salt));
//		System.out.println("has code::: "+new BCryptPasswordEncoder().encode(newPassword));
		
		entityManager.createQuery(new StringBuffer()
		.append("UPDATE Usermaster SET ld_pass_ang = :enc_password, ld_pass = :hash WHERE ld_lgnid = :username").toString()
		)
		.setParameter("username", username)
		.setParameter("enc_password", PasswordManager.encrypt(PasswordManager.encrypt(newPassword,salt),salt))
		.setParameter("hash", new BCryptPasswordEncoder().encode(newPassword))
		.executeUpdate();
	}

//	@SuppressWarnings("unchecked")
//	@Override
//	public Long getGrnIndex(String username, String companyCode) throws Exception {
//		EntityManager em = null;
//		Long grnIndex = null;
//		try {
//			em = emf.createEntityManager();
//			StringBuffer sb = new StringBuffer();
//			sb.append(" SELECT u.grn_index AS grn_index FROM Usermaster AS u ");
//			sb.append(" WHERE u.user_name = :username AND u.company_cd = :companyCode ");
//			Query query = em.createQuery(sb.toString(), Tuple.class);
//			query.setParameter("username", username);
//			query.setParameter("companyCode", companyCode);
//			List<Tuple> list = query.getResultList();
//			if (list != null && !list.isEmpty()) {
//				grnIndex = list.get(0).get("grn_index", Long.class);
//			}
//		} finally {
//			if(em != null) { em.close(); }
//		}
//		return grnIndex;
//	}
//
//	@SuppressWarnings("unchecked")
//	@Override
//	public Long getRetIndex(String userId, String companyCode) throws Exception {
//		Long grnIndex = null;
//		StringBuffer sb = new StringBuffer();
//		sb.append(" SELECT u.ret_index AS ret_index FROM Usermaster AS u ");
//		sb.append(" WHERE u.user_id = :userId AND u.company_cd = :companyCode ");
//		Query query = this.entityManager.createQuery(sb.toString(), Tuple.class);
//		query.setParameter("userId", Long.parseLong(userId));
//		query.setParameter("companyCode", companyCode);
//		List<Tuple> list = query.getResultList();
//		if (list != null && !list.isEmpty()) {
//			grnIndex = list.get(0).get("ret_index", Long.class);
//		}
//		return grnIndex;
//	}
//	@Override
//	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
//	public void updateRetIndex(String username, String companyCode, Long lastNum) throws Exception {
//		entityManager
//		.createQuery(new StringBuffer()
//				.append("UPDATE Usermaster SET ret_index = :lastNum WHERE user_name = :username AND company_cd = :companyCode").toString()
//				)
//		.setParameter("username", username)
//		.setParameter("companyCode", companyCode)
//		.setParameter("lastNum", lastNum)
//		.executeUpdate();
//	}
//
//	@Override
//	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
//	public void updateGrnIndex(String username, Long grnIndex, String companyCode) throws Exception {
//		this.entityManager.createQuery(
//				new StringBuffer(" UPDATE Usermaster AS u SET u.grn_index = :grnIndex WHERE ")
//				.append(" u.user_name = :username AND u.company_cd = :companyCode ").toString()
//				)
//		.setParameter("grnIndex", grnIndex)
//		.setParameter("username", username)
//		.setParameter("companyCode", companyCode)
//		.executeUpdate();
//	}
	
//	@Override
//	public Boolean checkUUIDagainstDiffUser(String username, String uuid) throws Exception {
//		EntityManager em = null;
//		Boolean isUUIDpresent = false;
//		try {
//			em = emf.createEntityManager();
//			CriteriaBuilder builder = em.getCriteriaBuilder();
//			CriteriaQuery<Usermaster> criteriaQuery = builder.createQuery(Usermaster.class);
//			Root<Usermaster> root = criteriaQuery.from(Usermaster.class);
//			criteriaQuery.select(root).where(
//					builder.notEqual(root.get(UserMaster_.user_name), username),
//					builder.equal(root.get(UserMaster_.uuid), uuid),
//					builder.equal(root.get(UserMaster_.user_status), A)
//					);
//			List<Usermaster> list = em.createQuery(criteriaQuery).getResultList();
//			if(list != null && !list.isEmpty()) {
//				isUUIDpresent = true;
//			}
//		} finally {
//			if(em != null) { em.close(); }
//		}
//		return isUUIDpresent;
//	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public void updateUserStatusByFsId(Long fsId, String status) throws Exception {
		/*
		 * this.entityManager.
		 * createQuery("UPDATE Usermaster AS u SET u.ld_status = :status WHERE u.fs_id = :fsId"
		 * ) .setParameter("status", status) .setParameter("fsId", fsId)
		 * .executeUpdate();
		 */
	}
	
	@Override
	public Usermaster getUserByFsId(Long fsId) throws Exception {
		//EntityManager em = null;
	Usermaster user = null;
//		try {
//			em = emf.createEntityManager();
//			CriteriaBuilder builder = em.getCriteriaBuilder();
//			CriteriaQuery<Usermaster> criteriaQuery = builder.createQuery(Usermaster.class);
//			Root<Usermaster> root = criteriaQuery.from(Usermaster.class);
//			criteriaQuery.select(root).where(
//					builder.equal(root.get(UserMaster_.fs_id), fsId),
//					builder.equal(root.get(UserMaster_.user_status), A)
//					);
//			List<Usermaster> list = em.createQuery(criteriaQuery).getResultList();
//			if(list != null && !list.isEmpty()) {
//				user = list.get(0);
//			}
//		} finally {
//			if(em != null) { em.close(); }
//		}
		return user;
	}

	@Override
	public List<Usermaster> getAllUsers(String companyCode) throws Exception {
		List<Usermaster> list = null;
		CriteriaBuilder builder = this.entityManager.getCriteriaBuilder();
		CriteriaQuery<Usermaster> criteriaQuery = builder.createQuery(Usermaster.class);
		Root<Usermaster> root = criteriaQuery.from(Usermaster.class);
		criteriaQuery.select(root);//.where(builder.equal(root.get(Usermaster_.emp), companyCode));
		list = this.entityManager.createQuery(criteriaQuery).getResultList();
		if(list != null && !list.isEmpty()) {
			return list;
		}
//		return null;
		return null;
	}
	
	@Override
	public Usermaster getUserByEmpId(String empId) throws Exception {
		EntityManager em = null;
		Usermaster user = null;
		try {

			CriteriaBuilder builder = entityManager.getCriteriaBuilder();
			CriteriaQuery<Usermaster> criteriaQuery = builder.createQuery(Usermaster.class);
			Root<Usermaster> root = criteriaQuery.from(Usermaster.class);
			criteriaQuery.select(root).where(
					builder.equal(root.get(Usermaster_.ld_emp_cb_id), empId)
					);
			List<Usermaster> list = entityManager.createQuery(criteriaQuery).getResultList();
			if(list != null && !list.isEmpty()) {
				user = list.get(0);
			}
		} finally {
			//if(em != null) { em.close(); }
		}
		return user;
	}
	@Override
	@SuppressWarnings("unchecked")
	public HashMap<String, String> getUserInfo(String username, String password)
			throws Exception {
		HashMap<String, String> data = null;
		try {
			StringBuffer buffer = new StringBuffer();
			buffer.append(" select e.emp_fnm as fname,e.emp_mnm as mname,e.emp_lnm as lname,ld.ld_emp_cb_id as emp_id,ld.dptloc_id as deptloc,eg.egrp_short_nm as egrp_short_nm,ld.ld_status as status,ld.is_temp as is_temp,ld.user_lock as user_lock,eg.egrp_id as egrp_id,ld.user_type as user_type, ");
			buffer.append(" ld.password_lock as password_lock,ld.password_lock_dt as password_lock_dt,e.emp_loc_id as emp_loc_id,ld.ld_id as ld_id,ld.ld_exec_asst_ind as ld_exec_asst_ind, e.fstaff_id as fstaff_id,e.emp_desg_ang as emp_desg_ang");
			buffer.append(" from Usermaster ld left outer join EmployeeDetails e ");
			buffer.append(" on(ld.ld_emp_cb_id=e.emp_id) left outer join UserGroup eg ");
			buffer.append(" on(e.emp_egrp_id=eg.egrp_id) ");
			buffer.append(" where ld.ld_lgnid = :username and ld.ld_status='A'");
			Query query = this.entityManager.createQuery(buffer.toString(), Tuple.class);
			query.setParameter("username", username);
			//query.setParameter("password", password);
			List<Tuple> tuples = query.getResultList();
			data = new HashMap<String, String>();
			Date d1=null;
			Date d2=new Date();
			Date t1 = null;
			if(tuples != null && !tuples.isEmpty()) {				
				for(Tuple t : tuples) {
					//System.out.println("FSFSDfsf "+t.get("fname", String.class));
					data.put("FNAME", t.get("fname", String.class));
					data.put("MNAME", t.get("mname", String.class));
					data.put("LNAME",t.get("lname", String.class));
					data.put("EMP_ID", t.get("emp_id", String.class));
					data.put("DEPTLOC", t.get("deptloc", String.class));
					data.put("USER_ROLE", t.get("egrp_short_nm", String.class));
					data.put("LD_STATUS", t.get("status", String.class));
					data.put("IS_TEMP",t.get("is_temp", String.class));
					data.put("USER_LOCK",t.get("user_lock", String.class));
					data.put("GROUP_ID",t.get("egrp_id", String.class));
					data.put("USER_TYPE",t.get("user_type", String.class));
					data.put("PASS_LOCK",t.get("password_lock", String.class));
					data.put("EMP_LOC", t.get("emp_loc_id", String.class));
					data.put("LD_ID", t.get("ld_id", Long.class).toString());
					data.put("LD_EXEC_ASST_IND", t.get("ld_exec_asst_ind", String.class).toString());
					t1=t.get("password_lock_dt", Date.class);
					
					data.put("FSTAFF_ID", t.get("fstaff_id",Long.class).toString());
					data.put("EMP_DESG", t.get("emp_desg_ang",String.class));
				}
					long diffHours=0;
					long diffMin=0l;
					if(t1!=null){
						d1=new Date(t1.getTime());
						Long date_diff=d2.getTime()-d1.getTime();
						 diffHours = date_diff / (60 * 60 * 1000) % 24;
						 if(diffHours<=1)
						  diffMin =(date_diff/(60 * 1000)% 24);
					}
					data.put("PASS_HRS_DIFF",diffHours+"");
					data.put("PASS_MIN_DIFF",diffMin+"");
					
					
			}
		}catch(Exception e){
			throw e;
		}
		return data;
	}
	
	@Override
	@SuppressWarnings("unchecked")
	public HashMap<String, String> getUserDivision(String empId) throws Exception {
		HashMap<String, String> data = new HashMap<String, String>();
		StringBuffer buffer = new StringBuffer();
		try {
			buffer.append(" SELECT dm.div_id as div_id, dm.div_disp_nm as div_name FROM DivMaster dm , DivAccess da WHERE dm.div_id = da.ediv_div_id and da.ediv_emp_id = :empId ");
			Query query = this.entityManager.createQuery(buffer.toString(), Tuple.class);
			query.setParameter("empId", empId);
			List<Tuple> tuples = query.getResultList();
			data = new HashMap<String, String>();
			
			if(tuples != null && !tuples.isEmpty()) {				
				for(Tuple t : tuples) {
					data.put(t.get("div_id", Long.class).toString(), t.get("div_name", String.class));
				}
			}
		}catch(Exception e){
			throw e;
		}
		return data;
	}
	
	@Override
	public LoginDetails getLoginDetailsByUserName(String userName) throws Exception {
		EntityManager em = null;
		LoginDetails result = null;
		try {
			em = emf.createEntityManager();
			StoredProcedureQuery query  = em.createNamedStoredProcedureQuery("callLoginDetails");
			System.out.println("userName::"+userName);
			query.setParameter("pvld_lgnid", userName);
			result = (LoginDetails) query.getSingleResult();
		} finally {
			if (em != null) { em.close(); }
		}
		
		return result;
	}
	
	@Override
	public String getAllowBatchCreateInd(String empId) throws Exception {
		EntityManager em = null;
		String allowInd = null;
		try {
			em = emf.createEntityManager();
			CriteriaBuilder builder = em.getCriteriaBuilder();
			CriteriaQuery<String> criteriaQuery = builder.createQuery(String.class);
			Root<Usermaster> root = criteriaQuery.from(Usermaster.class);
			criteriaQuery.select(root.get(Usermaster_.allow_batch_create))
			.where(builder.equal(root.get(Usermaster_.ld_emp_cb_id), empId));
			allowInd = em.createQuery(criteriaQuery).getSingleResult();
		} finally {
			if (em != null) { em.close(); }
		}
		System.out.println("allowInd : "+allowInd);
		return allowInd;
	}

	@Override
	@SuppressWarnings("unchecked")
	public HashMap<String, String> getUserDivisionArray(String empId) throws Exception {
		HashMap<String, String> data = new HashMap<String, String>();
		StringBuffer buffer = new StringBuffer();
		try {
			buffer.append(" SELECT dm.div_id as div_id, dm.div_disp_nm as div_name FROM DivMaster dm , Am_m_emp_div_access da WHERE dm.div_id = da.ediv_div_id and da.ediv_emp_id = :empId ");
			Query query = this.entityManager.createQuery(buffer.toString(), Tuple.class);
			query.setParameter("empId", empId);
			List<Tuple> tuples = query.getResultList();
			data = new HashMap<String, String>();
			
			if(tuples != null && !tuples.isEmpty()) {				
				for(Tuple t : tuples) {
					data.put(t.get("div_id", String.class), t.get("div_name", String.class));
				}
			}
		}catch(Exception e){
			throw e;
		}
		return data;
	}
	
	@Override
	public List<Parameter> getDivAccessedByUser(String userId) throws Exception {
		List<Parameter> list = new ArrayList<Parameter>();
		EntityManager em = null;
		try {
			em = emf.createEntityManager();
			StringBuffer sb = new StringBuffer();
			sb.append(" select div_code as div_code,div_disp_nm as div_disp_nm,div_id as div_id,div_code_nm as div_code_nm,div_status as div_status from  DIV_MASTER where div_id in (select ediv_div_id from am_m_emp_div_access where ediv_emp_id= :empId ");
			sb.append(" and ediv_status='A') order by div_disp_nm ");
			Query query = em.createNativeQuery(sb.toString(), Tuple.class);
			query.setParameter("empId", userId);
			List<Tuple> tuples = query.getResultList();
			Parameter pm = null;
			if (tuples != null && !tuples.isEmpty()) {
				for(Tuple t : tuples) {
					pm = new Parameter();
					pm.setCode(t.get("div_code", String.class));
					pm.setId(String.valueOf(t.get("div_id", Integer.class)));
					pm.setName(t.get("div_disp_nm", String.class));
					list.add(pm);
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
	public List<MailBean> getEmpDetailForMail(String userId) throws Exception {
		List<MailBean> list = new ArrayList<MailBean>();
		EntityManager em = null;
		try {
			em = emf.createEntityManager();
			StringBuffer sb = new StringBuffer();
			sb.append(" SELECT alusr.ld_emp_cb_id userempid,hrusr.emp_fnm+' '+hrusr.emp_mnm+' '+hrusr.emp_lnm usrname,");
			sb.append(" alusr.ld_email usremail");
			sb.append(" FROM am_m_login_detail alusr JOIN hr_m_employee hrusr on hrusr.emp_id=alusr.ld_emp_cb_id");
			sb.append(" WHERE alusr.ld_emp_cb_id =:userId");
			Query query = em.createNativeQuery(sb.toString(), Tuple.class);
			query.setParameter("userId", userId);
			List<Tuple> tuples = query.getResultList();
			MailBean mail = null;
			if (tuples != null && !tuples.isEmpty()) {
				for(Tuple t : tuples) {
					mail = new MailBean();
					mail.setStarted_by_name(t.get("usrname", String.class));
					mail.setEmp_email(t.get("usremail", String.class));
					list.add(mail);
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
	public List<UserMasterBean> getLockUser() throws Exception {
		List<UserMasterBean> list=new ArrayList<>();
		UserMasterBean bean=null;
		try {
			StringBuffer buffer = new StringBuffer();
			buffer.append(" select e.emp_fnm as fname,e.emp_mnm as mname,e.emp_lnm as lname,ld.ld_emp_cb_id as emp_id,ld.dptloc_id as deptloc,eg.egrp_short_nm as egrp_short_nm,ld.ld_status as status,ld.is_temp as is_temp,ld.user_lock as user_lock,eg.egrp_id as egrp_id,ld.user_type as user_type, ");
			buffer.append(" ld.ld_lgnid as username,e.emp_loc_id as emp_loc_id,ld.ld_id as ld_id,ld.ld_exec_asst_ind as ld_exec_asst_ind");
			buffer.append(" from Usermaster ld left outer join EmployeeDetails e ");
			buffer.append(" on(ld.ld_emp_cb_id=e.emp_id) left outer join UserGroup eg ");
			buffer.append(" on(e.emp_egrp_id=eg.egrp_id) ");
			buffer.append(" where ld.user_lock ='Y' or ld.password_lock='Y' and ld.ld_status='A'");
			Query query = this.entityManager.createQuery(buffer.toString(), Tuple.class);
			//query.setParameter("password", password);
			List<Tuple> tuples = query.getResultList();
			if(tuples != null && !tuples.isEmpty()) {				
				for(Tuple t : tuples) {
					bean=new UserMasterBean();
					//System.out.println("FSFSDfsf "+t.get("fname", String.class));
					bean.setUsername(t.get("username", String.class));
					//int salt = this.getSaltvalByUsername(bean.getUsername());
					bean.setField_staff_name( t.get("fname", String.class)+" "+t.get("lname", String.class));
				//	bean.setPassword(PasswordManager.decrypt(PasswordManager.decrypt( t.get("ld_pass_ang", String.class),salt),salt));
					bean.setEmpId(t.get("emp_id", String.class));
				
					bean.setRole(t.get("egrp_short_nm", String.class));
					list.add(bean);
				}
			}
		}catch(Exception e){
			throw e;
		}
		return list;
	}
	
	@Override
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public void unlockUserOnRequest(List<String> userId, boolean lock) throws Exception {
		System.out.println("List "+userId);
		Query query = entityManager.createQuery("update Usermaster set user_lock = :lock,password_lock=:pslock,password_lock_dt=:passdt where ld_emp_cb_id in(:user_id)");
		if(lock) {
			query.setParameter("lock", Y);
			query.setParameter("pslock", N);
			query.setParameter("passdt", null);
			
		} else {
			query.setParameter("lock", N);
			query.setParameter("pslock", N);
			query.setParameter("passdt", null);
		}
		query.setParameter("user_id", userId);
		query.executeUpdate();
	}
	
	
	@Override
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public String saveOrModifyHr_M_Employee(String emp_id, String emp_egrp_id, String emp_dept_id,
			Long emp_loc_id,  String emp_COMP_id, String emp_desg_id, String emp_sup_emp_id,
			String emp_fnm,  String emp_mnm, String emp_lnm, String emp_gender, String emp_birth_dt,
			String emp_lgn_valid_frm_dt, String emp_lgn_valid_to_dt, String emp_join_dt, String emp_resign_dt,
			BigDecimal emp_sal, String emp_abstract, String emp_add, String emp_phno, String emp_mob,
			String emp_email, String emp_lgn_lock, String emp_emg_cont_prsn, String emp_emg_cont_phno,
			String curr_user_id, String ip_add, String emp_status, String save_mode,String fs_id,String rm_id,String dm_id,String sm_id) throws Exception{
		//String emp_id="";
		try{
			Query query=null;
			Integer emp_index=0;
			StringBuffer buffer = new StringBuffer();
			if(save_mode.equalsIgnoreCase("E")){
				Integer max_key = this.userDepartmentRepository.getMaxUserKey();
				System.out.println("Max Key "+max_key);
				emp_index=max_key+1;
				emp_id = "E" + String.format("%05d", emp_index);
				buffer.append("insert into hr_m_employee(emp_id, emp_egrp_id, emp_dept_id, emp_loc_id, emp_COMP_id, emp_desg_id, ")
						.append("emp_sup_emp_id, emp_fnm, emp_mnm, emp_lnm, emp_gender, emp_birth_dt, emp_lgn_valid_frm_dt, ")
						.append("emp_lgn_valid_to_dt, emp_join_dt, emp_resign_dt, emp_sal, emp_abstract, emp_add, emp_phno, ")
						.append("emp_mob, emp_email, emp_lgn_lock, emp_emg_cont_prsn, emp_emg_cont_phno, emp_ins_usr_id, ")
						.append("emp_ins_dt, emp_ins_ip_add, emp_status, emp_index,fstaff_id,rm_emp_id,dm_emp_id,sm_emp_id) ")
						.append("values(:emp_id, :emp_egrp_id, :emp_dept_id, :emp_loc_id, :emp_COMP_id, :emp_desg_id, ")
						.append(":emp_sup_emp_id, :emp_fnm, :emp_mnm, :emp_lnm, :emp_gender, :emp_birth_dt, :emp_lgn_valid_frm_dt, ")
						.append(":emp_lgn_valid_to_dt, :emp_join_dt, :emp_resign_dt, :emp_sal, :emp_abstract, :emp_add, :emp_phno, ")
						.append(":emp_mob, :emp_email, :emp_lgn_lock, :emp_emg_cont_prsn, :emp_emg_cont_phno, :emp_ins_usr_id, ")
						.append(":emp_ins_dt, :emp_ins_ip_add, :emp_status, :emp_index,:fstaff_id,:rm_emp_id,:dm_emp_id,:sm_emp_id) ");
			}else{
				
				buffer.append("UPDATE hr_m_employee ")
						.append("SET emp_egrp_id=:emp_egrp_id, emp_dept_id=:emp_dept_id, emp_loc_id=:emp_loc_id, ")
						.append("emp_COMP_id=:emp_COMP_id, emp_desg_id=:emp_desg_id, emp_sup_emp_id=:emp_sup_emp_id, emp_fnm=:emp_fnm, ")
						.append("emp_mnm=:emp_mnm, emp_lnm=:emp_lnm, emp_gender=:emp_gender, emp_birth_dt=:emp_birth_dt, ")
						.append("emp_lgn_valid_frm_dt=:emp_lgn_valid_frm_dt, emp_lgn_valid_to_dt=:emp_lgn_valid_to_dt, emp_join_dt=:emp_join_dt, ")
						.append("emp_resign_dt=:emp_resign_dt, emp_abstract=:emp_abstract, emp_add=:emp_add, emp_phno=:emp_phno, ")
						.append("emp_mob=:emp_mob, emp_email=:emp_email, emp_lgn_lock=:emp_lgn_lock, emp_emg_cont_prsn=:emp_emg_cont_prsn, ")
						.append("emp_emg_cont_phno=:emp_emg_cont_phno, emp_mod_usr_id=:emp_mod_usr_id, emp_mod_dt=:emp_mod_dt, ")
						.append("emp_mod_ip_add=:emp_mod_ip_add, emp_status=:emp_status,fstaff_id=:fstaff_id,rm_emp_id=:rm_emp_id,dm_emp_id=:dm_emp_id,sm_emp_id=:sm_emp_id where emp_id=:emp_id");
			}
			query=entityManager.createNativeQuery(buffer.toString());
			System.out.println("EMP_ID::"+emp_id);
			query.setParameter("emp_id", emp_id)
				.setParameter("emp_egrp_id", emp_egrp_id)
				.setParameter("emp_dept_id", emp_dept_id)
				.setParameter("emp_loc_id", emp_loc_id)
				.setParameter("emp_COMP_id", emp_COMP_id)
				.setParameter("emp_desg_id", emp_desg_id)
				.setParameter("emp_sup_emp_id", emp_sup_emp_id)
				.setParameter("emp_fnm", emp_fnm)
				.setParameter("emp_mnm", emp_mnm)
				.setParameter("emp_lnm", emp_lnm)
				.setParameter("emp_gender", emp_gender);
	
				if(emp_birth_dt!=null && !("").equalsIgnoreCase(emp_birth_dt)){
					query.setParameter("emp_birth_dt",MedicoResources.convert_DD_MM_YYYY_toDate(emp_birth_dt));
				}else{
					query.setParameter("emp_birth_dt",null);
				}
				if(emp_lgn_valid_frm_dt!=null && !("").equalsIgnoreCase(emp_lgn_valid_frm_dt)){
					query.setParameter("emp_lgn_valid_frm_dt",MedicoResources.convert_DD_MM_YYYY_toDate(emp_lgn_valid_frm_dt));
				}else{
					query.setParameter("emp_lgn_valid_frm_dt",null);
				}
				if(emp_lgn_valid_to_dt!=null && !("").equalsIgnoreCase(emp_lgn_valid_to_dt)){
					query.setParameter("emp_lgn_valid_to_dt",MedicoResources.convert_DD_MM_YYYY_toDate(emp_lgn_valid_to_dt));
				}else{
					query.setParameter("emp_lgn_valid_to_dt",null);
				}
				if(emp_join_dt!=null && !("").equalsIgnoreCase(emp_join_dt)){
					query.setParameter("emp_join_dt",MedicoResources.convert_DD_MM_YYYY_toDate(emp_join_dt));
				}else{
					query.setParameter("emp_join_dt", null);
				}
				if(emp_resign_dt!=null && !("").equalsIgnoreCase(emp_resign_dt)){
					query.setParameter("emp_resign_dt",MedicoResources.convert_DD_MM_YYYY_toDate(emp_resign_dt));
				}else{
					query.setParameter("emp_resign_dt", null);
				}				
				
				query.setParameter("emp_abstract", emp_abstract)
					.setParameter("emp_add", emp_add)
					.setParameter("emp_phno", emp_phno)
					.setParameter("emp_mob", emp_mob)
					.setParameter("emp_email", emp_email)
					.setParameter("emp_lgn_lock", emp_lgn_lock)
					.setParameter("emp_emg_cont_prsn", emp_emg_cont_prsn)
					.setParameter("emp_emg_cont_phno", emp_emg_cont_phno)
					.setParameter("fstaff_id", fs_id)
					.setParameter("rm_emp_id", rm_id)
					.setParameter("dm_emp_id", dm_id)
					.setParameter("sm_emp_id", sm_id);
				
				if(save_mode.equalsIgnoreCase("E")){
					query.setParameter("emp_ins_usr_id", curr_user_id)
					.setParameter("emp_ins_dt", new Date())
					.setParameter("emp_ins_ip_add", ip_add)
					.setParameter("emp_status", "A")
					.setParameter("emp_sal", emp_sal)
					.setParameter("emp_index",emp_index);
				}else{
					query.setParameter("emp_mod_usr_id", curr_user_id)
						.setParameter("emp_mod_dt", new Date())
						.setParameter("emp_mod_ip_add", ip_add)
						.setParameter("emp_status", emp_status);
				}
				
				System.out.println(query);
		        query.executeUpdate();
			 
		}catch(Exception e){
			throw e;
		}
		return emp_id;
	}
	
	@Override
	public EmployeeDetails getEmployeeDetails(String empId) throws Exception {
		EntityManager em = null;
		EmployeeDetails user = null;
		try {

			CriteriaBuilder builder = entityManager.getCriteriaBuilder();
			CriteriaQuery<EmployeeDetails> criteriaQuery = builder.createQuery(EmployeeDetails.class);
			Root<EmployeeDetails> root = criteriaQuery.from(EmployeeDetails.class);
			criteriaQuery.select(root).where(
					builder.equal(root.get(EmployeeDetails_.emp_id), empId)
					);
			List<EmployeeDetails> list = entityManager.createQuery(criteriaQuery).getResultList();
			if(list != null && !list.isEmpty()) {
				user = list.get(0);
			}
		} finally {
			//if(em != null) { em.close(); }
		}
		return user;
	}

	@Override
	public List<UserMasterBean> getActiveUserList() throws Exception{
		List<UserMasterBean> list =new ArrayList<>();
		try{
			StringBuffer buffer = new StringBuffer();
			buffer.append("select emp_id, CONCAT(emp_fnm, ' ', emp_mnm,' ',emp_lnm) as emp_name ")
					.append("from hr_m_employee where emp_status='A' order by emp_fnm ");
			Query query = entityManager.createNativeQuery(buffer.toString(), Tuple.class);
			List<Tuple> tuples = query.getResultList();
			UserMasterBean pm = null;
			if (tuples != null && !tuples.isEmpty()) {
				for(Tuple t : tuples) {
					pm = new UserMasterBean();
					pm.setEmpId(t.get("emp_id", String.class));
					pm.setField_staff_name(t.get("emp_name", String.class));
					list.add(pm);
				}
			}
			
		}catch(Exception e){
			throw e;
		}
		return list;
	}
	
	@Override
	public UserMasterBean getUserDetailsByEmpId(String empId) throws Exception {
		List<UserMasterBean> list=new ArrayList<>();
		UserMasterBean bean=null;
		try {
			StringBuffer buffer = new StringBuffer();
			buffer.append(" select e.emp_fnm as fname,e.emp_mnm as mname,e.emp_lnm as lname,ld.ld_emp_cb_id as emp_id,ld.dptloc_id as deptloc,eg.egrp_short_nm as egrp_short_nm,ld.ld_status as status,ld.is_temp as is_temp,ld.user_lock as user_lock,eg.egrp_id as egrp_id,ld.user_type as user_type, ");
			buffer.append(" ld.ld_pass_ang as ld_pass_ang,ld.ld_lgnid as username,e.emp_loc_id as emp_loc_id,ld.ld_id as ld_id,ld.ld_exec_asst_ind as ld_exec_asst_ind");
			buffer.append(" from Usermaster ld left outer join EmployeeDetails e ");
			buffer.append(" on(ld.ld_emp_cb_id=e.emp_id) left outer join UserGroup eg ");
			buffer.append(" on(e.emp_egrp_id=eg.egrp_id) ");
			buffer.append(" where ld.ld_status='A' and ld.ld_emp_cb_id=:empId");
			Query query = this.entityManager.createQuery(buffer.toString(), Tuple.class);
			query.setParameter("empId", empId);;
			List<Tuple> tuples = query.getResultList();
			if(tuples != null && !tuples.isEmpty()) {				
				for(Tuple t : tuples) {
					bean=new UserMasterBean();
					//System.out.println("FSFSDfsf "+t.get("fname", String.class));
					bean.setUsername(t.get("username", String.class));
					int salt = this.getSaltvalByUsername(bean.getUsername());
					bean.setField_staff_name( t.get("fname", String.class)+" "+t.get("lname", String.class));
					bean.setPassword(PasswordManager.decrypt(PasswordManager.decrypt( t.get("ld_pass_ang", String.class),salt),salt));
					bean.setEmpId(t.get("emp_id", String.class));
				//	bean.setUsername(t.get("username", String.class));
					bean.setRole(t.get("egrp_short_nm", String.class));
					list.add(bean);
				}
			}
			if(list!=null && list.size()>0)
				return list.get(0);
		}catch(Exception e){
			throw e;
		}
		return null;
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public void insertPasswordLocktime(String username) throws Exception {
		// TODO Auto-generated method stub
		try {
		int addminuteTime =0;
		Date targetTime = Calendar.getInstance().getTime();
		targetTime = DateUtils.addMinutes(targetTime, addminuteTime);
			
		entityManager.createQuery(new StringBuffer().append("UPDATE Usermaster SET password_lock_dt = :locktime,password_lock=:lockStatus WHERE ld_lgnid in (:usernames)").toString())
		.setParameter("usernames", username)
		.setParameter("lockStatus", Y)
//		.setParameter("userlock", Y)
		.setParameter("locktime", targetTime).executeUpdate();
		}
		catch (Exception e) {
			// TODO: handle exception
			System.out.println("Error Occurred :" +e.getMessage());// uncomment asneeded --;
		}
		
	}

	@Override
	public Integer getLatestAttempts(String username) throws Exception {
		Integer attempt = 0;
		try{
			StringBuffer buffer = new StringBuffer();
			buffer.append("select top 1 login_attempts login_attempts from am_m_login_attempts_log where login_id=:username order by id desc");
			Query query = entityManager.createNativeQuery(buffer.toString());
			query.setParameter("username", username);
			attempt = (Integer) query.getSingleResult();
			
			System.out.println("Attemptsss : "+attempt);
		}catch(Exception e){
			//throw e;
			attempt = 0;
		}
		return attempt;
	}
	
	@Override
	public List<SG_d_parameters_details> getPasswordRecoveryQuestions(String logquest)throws Exception {

		EntityManager em = null;
		List<SG_d_parameters_details> list=new ArrayList<SG_d_parameters_details>();
		try {
			em = emf.createEntityManager();
			String q=" select sd.sgprmdet_id,sgprmdet_nm from SG_d_parameters_details sd"
					+ " join SG_m_parameters sp on sp.sgprm_id=sd.sgprmdet_sgprm_id"
					+ " where  upper(sgprm_nm) =:logquest"
					+ " order by sgprmdet_id";
			Query query = em.createNativeQuery(q,Tuple.class);
			query.setParameter("logquest", logquest);
			List<Tuple> tuples = query.getResultList();
			if (tuples != null && !tuples.isEmpty()) {
				for (Tuple t : tuples) {
					SG_d_parameters_details spd=new SG_d_parameters_details();
					spd.setSg_prmdet_id(Long.valueOf(t.get("sgprmdet_id",Integer.class)));
					spd.setSgprmdet_nm(t.get("sgprmdet_nm",String.class));
					list.add(spd);
				}
			}
		}
		catch (Exception e) {
			// TODO: handle exception
			throw e;
		}
		finally {
			em.close();
		}
		return list;
	
		}

	@Override
	public List<Usermaster> getUsernameData() throws Exception {
		List<Usermaster> list=new ArrayList<>();
		Usermaster bean=null;
		try {
			StringBuffer buffer = new StringBuffer();
			buffer.append("select top 10 ld_email,PWD_EXPIRY_DT,ld_lgnid from am_m_login_detail  where  ld_status='A' And pwd_email_ind IS NULL OR pwd_email_ind='N'");
			Query query = this.entityManager.createNativeQuery(buffer.toString(), Tuple.class);
			//query.setParameter("password", password);
			List<Tuple> tuples = query.getResultList();
			if(tuples != null && !tuples.isEmpty()) {				
				for(Tuple t : tuples) {
					bean=new Usermaster();
					bean.setLd_email(t.get("ld_email",String.class));
					bean.setPwd_expiry_dt(t.get("PWD_EXPIRY_DT",Date.class));
					bean.setLd_lgnid(t.get("ld_lgnid",String.class));
					list.add(bean);
				}
			}
		}catch(Exception e){
			throw e;
		}
		return list;
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public void updateUsernameData(String ld_lgnid) throws Exception {
		System.out.println(ld_lgnid);
		  this.entityManager.
		  createNativeQuery("UPDATE am_m_login_detail SET pwd_email_ind = 'Y' WHERE ld_lgnid = :ld_lgnid" ) 
		  .setParameter("ld_lgnid", ld_lgnid)
		  .executeUpdate();
		 
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public void updateUsernameDatatoN(String ld_lgnid) throws Exception {
		System.out.println(ld_lgnid);
		  this.entityManager.
		  createNativeQuery("UPDATE am_m_login_detail SET pwd_email_ind = 'N' WHERE ld_lgnid = :ld_lgnid" ) 
		  .setParameter("ld_lgnid", ld_lgnid)
		  .executeUpdate();
		 
	}

	@Override
	public List<Password_Log> getUserPassByempId(String ld_emp_cb_id) throws Exception {
		List<Password_Log> list=new ArrayList<>();
		Password_Log bean=null;
		try {
			StringBuffer buffer = new StringBuffer();
			buffer.append("SELECT ABS(CONVERT(NUMERIC,PASSWORD_CHANGE_DT-GETDATE()) ) CHG_BEFORE_NOOFDAYS ,"
					+ " L.ld_pass_ang ld_pass_ang,L.emp_id emp_id,L.password_change_dt password_change_dt" + 
					" FROM am_m_login_old_passwords_log L" + 
					" WHERE  CONVERT(NUMERIC,PASSWORD_CHANGE_DT-GETDATE())>=-365 and L.emp_id = :ld_emp_cb_id" + 
					" ORDER BY ID");
			Query query = this.entityManager.createNativeQuery(buffer.toString(), Tuple.class);
			query.setParameter("ld_emp_cb_id", ld_emp_cb_id);
			List<Tuple> tuples = query.getResultList();
			if(tuples != null && !tuples.isEmpty()) {				
				for(Tuple t : tuples) {
					bean=new Password_Log();
					bean.setEmp_id(t.get("emp_id",String.class));
					bean.setPassword_change_dt(t.get("password_change_dt",Date.class));
					bean.setId_pass_ang(t.get("ld_pass_ang",String.class));
					list.add(bean);
				}
			}
		}catch(Exception e){
			throw e;
		}
		return list;
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public void LockOrUnlockAmLoginDtl(String userId, boolean lock) throws Exception {
			Query query = this.entityManager.createNativeQuery("UPDATE am_m_login_detail set user_lock = :lock where ld_id = :user_id");
			if(lock) {
				query.setParameter("lock", Y);
			} else {
				query.setParameter("lock", N);
			}
			query.setParameter("user_id", userId);
			query.executeUpdate();
	}

	@Override
	public int getSaltvalByUsername(String username) throws Exception {
		// TODO Auto-generated method stub
		List<Usermaster> list = null;
		SimpleDateFormat sdf = new SimpleDateFormat("dd");
		try {
			CriteriaBuilder builder = this.entityManager.getCriteriaBuilder();
			CriteriaQuery<Usermaster> criteriaQuery = builder.createQuery(Usermaster.class);
			Root<Usermaster> root = criteriaQuery.from(Usermaster.class);
			criteriaQuery.select(root).where(builder.equal(root.get(Usermaster_.ld_lgnid), username));
			list = this.entityManager.createQuery(criteriaQuery).getResultList();
			if(list != null && !list.isEmpty()) {
				 
				return Integer.parseInt(sdf.format(list.get(0).getLd_ins_dt()));
			}
		}
		catch (Exception e) {
			// TODO: handle exception
			throw e;
		}
		return 0;
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public void EraseOtp(String username) throws Exception {
		// TODO Auto-generated method stub
		try {
			Query query = this.entityManager.createNativeQuery("UPDATE am_m_login_detail set otp = null where ld_lgnid = :user_name");
			
			query.setParameter("user_name", username);
			query.executeUpdate();
		}
		catch (Exception e) {
			// TODO: handle exception
			throw e;
		}
	}

	@Override
	public List<Password_Log> getUserPass() throws Exception {
		List<Password_Log> list=new ArrayList<>();
		//Password_Log bean=null;
		try {
			CriteriaBuilder builder = this.entityManager.getCriteriaBuilder();
			CriteriaQuery<Password_Log> criteriaQuery = builder.createQuery(Password_Log.class);
			Root<Password_Log> root = criteriaQuery.from(Password_Log.class);
			criteriaQuery.select(root);//.where(builder.equal(root.get(Usermaster_.ld_lgnid), username));
			list = this.entityManager.createQuery(criteriaQuery).getResultList();
			
		}catch(Exception e){
			throw e;
		}
		return list;
	}
	@Override
	public Usermaster  getpdfandexcellockind(String emp_cd) throws Exception {
		EntityManager em = null;
		Usermaster ind = null;
		System.out.println("username "+emp_cd);
		try {

			CriteriaBuilder builder = entityManager.getCriteriaBuilder();
			CriteriaQuery<Usermaster> criteriaQuery = builder.createQuery(Usermaster.class);
			Root<Usermaster> root = criteriaQuery.from(Usermaster.class);
			criteriaQuery.select(root).where(
					builder.equal(root.get(Usermaster_.ld_emp_cb_id), emp_cd)
					);
			ind= entityManager.createQuery(criteriaQuery).getSingleResult();
			
		}catch (Exception e) {
		//	e.printStackTrace();
			throw e;
		} finally {
			//if(em != null) { em.close(); }
		}
		return ind;
	}

	@Override
	public String getEmpIdByUsername(String username) throws Exception {
		EntityManager em = null;
		String empCode = null;
		try {

			CriteriaBuilder builder = entityManager.getCriteriaBuilder();
			CriteriaQuery<String> criteriaQuery = builder.createQuery(String.class);
			Root<Usermaster> root = criteriaQuery.from(Usermaster.class);
			criteriaQuery.multiselect((root.get(Usermaster_.ld_emp_cb_id))).where(
					builder.equal(root.get(Usermaster_.ld_lgnid), username)
					);
			empCode= entityManager.createQuery(criteriaQuery).getSingleResult();
			
		} finally {
			//if(em != null) { em.close(); }
		}
		return empCode;
	}
	
	@Override
	public String getEmpIdByEmail(String email) throws Exception {
		EntityManager em = null;
		String empCode = null;
		try {
			CriteriaBuilder builder = entityManager.getCriteriaBuilder();
			CriteriaQuery<String> criteriaQuery = builder.createQuery(String.class);
			Root<Usermaster> root = criteriaQuery.from(Usermaster.class);
			
			criteriaQuery.multiselect((root.get(Usermaster_.ld_emp_cb_id))).where(
					builder.equal(root.get(Usermaster_.ld_email), email)
					);

			empCode= entityManager.createQuery(criteriaQuery).setMaxResults(1).getSingleResult();
			
		} finally {
			//if(em != null) { em.close(); }
		}
		return empCode;
	}
}
