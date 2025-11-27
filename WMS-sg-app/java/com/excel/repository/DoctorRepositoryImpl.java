package com.excel.repository;

import java.math.BigDecimal;
import java.util.ArrayList;
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

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.excel.bean.BulkUpldBean;
import com.excel.model.CustomerMaster;
import com.excel.model.Dispatch_alloc_monthwise_report;
import com.excel.model.DivMaster;
import com.excel.model.DoctorMaster;
import com.excel.model.DoctorMaster_;
import com.excel.model.EmployeeDetails;
import com.excel.model.EmployeeDetails_;
import com.excel.model.FieldStaff;
import com.excel.model.Hcp_prod_details;

@Repository
public class DoctorRepositoryImpl implements DoctorRepository {
	
	@Autowired EntityManagerFactory emf;
	@PersistenceContext private EntityManager entityManager;


	@Override
	public List<DoctorMaster> getDoctorsForFieldStaff(String emp_id) throws Exception {
		EntityManager em = null;
		List<DoctorMaster> list = null;
		try {
			System.out.println("EmpId "+emp_id);
			em = emf.createEntityManager();
			CriteriaBuilder builder = em.getCriteriaBuilder();
			CriteriaQuery<DoctorMaster> criteriaQuery = builder.createQuery(DoctorMaster.class);
			Root<DoctorMaster> root = criteriaQuery.from(DoctorMaster.class);
			criteriaQuery.multiselect(
					root.get(DoctorMaster_.doc_id),
					root.get(DoctorMaster_.doc_name),
					root.get(DoctorMaster_.mcl_no),
					root.get(DoctorMaster_.doc_phone),
					root.get(DoctorMaster_.doc_prefix)
					
					).where(builder.and(builder.equal(root.get(DoctorMaster_.fstaff_id), emp_id)),
							builder.and(builder.equal(root.get(DoctorMaster_.doc_status), 'A'))).orderBy(builder.asc(root.get(DoctorMaster_.doc_name)));
			list = em.createQuery(criteriaQuery).getResultList();
		} finally {
			if(em != null) { em.close(); }
		}
		if(list != null && !list.isEmpty()) {
			return list;
		}
		return null;
	}
	
	@Override
	public DoctorMaster getDoctorsDetails(Long docId) throws Exception {
		EntityManager em = null;
		List<DoctorMaster> list = null;
		try {
			em = emf.createEntityManager();
			CriteriaBuilder builder = em.getCriteriaBuilder();
			CriteriaQuery<DoctorMaster> criteriaQuery = builder.createQuery(DoctorMaster.class);
			Root<DoctorMaster> root = criteriaQuery.from(DoctorMaster.class);
			criteriaQuery.select(root).where(builder.and(builder.equal(root.get(DoctorMaster_.doc_id), docId)),
							builder.and(builder.equal(root.get(DoctorMaster_.doc_status), 'A'))).orderBy(builder.asc(root.get(DoctorMaster_.doc_name)));
			list = em.createQuery(criteriaQuery).getResultList();
			if(list!=null && list.size()>0)
				return list.get(0);
		} finally {
			if(em != null) { em.close(); }
		}
		return null;
	}
	@Override
	public DoctorMaster getDoctorsDetailsByRequestorId(Long docId,Long requestor_id) throws Exception {
		EntityManager em = null;
		List<DoctorMaster> list = null;
		try {
			em = emf.createEntityManager();
			CriteriaBuilder builder = em.getCriteriaBuilder();
			CriteriaQuery<DoctorMaster> criteriaQuery = builder.createQuery(DoctorMaster.class);
			Root<DoctorMaster> root = criteriaQuery.from(DoctorMaster.class);
			criteriaQuery.select(root).where(builder.and(builder.equal(root.get(DoctorMaster_.doc_id), docId)),
							builder.and(builder.equal(root.get(DoctorMaster_.doc_status), 'A')),
									builder.and(builder.equal(root.get(DoctorMaster_.fstaff_id),requestor_id))).orderBy(builder.asc(root.get(DoctorMaster_.doc_name)));
			list = em.createQuery(criteriaQuery).getResultList();
			if(list!=null && list.size()>0)
				return list.get(0);
		} finally {
			if(em != null) { em.close(); }
		}
		return null;
	}

	@Override
	public List<FieldStaff> getDoctorsForFieldStaffEntry(String divId){
		EntityManager em = null;
		List<FieldStaff> list=new ArrayList<FieldStaff>();
		try {
			em = emf.createEntityManager();
			String q="Select FSTAFF_ID,FSTAFF_DISPLAY_NAME,FSTAFF_EMPLOYEE_NO from fieldstaff where FSTAFF_DIV_ID  =:divId and FSTAFF_status ='A' and fs_category not in('E','D') and FSTAFF_LEVEL_CODE = '001' order by FSTAFF_DISPLAY_NAME";
			
			System.out.println("Div id ::"+divId);
			Query query = em.createNativeQuery(q,Tuple.class);
			query.setParameter("divId", divId);
			List<Tuple> tuples = query.getResultList();
			if (tuples != null && !tuples.isEmpty()) {
				for (Tuple t : tuples) {
					FieldStaff hqm=new FieldStaff();
					hqm.setFstaff_id(Long.valueOf(t.get("FSTAFF_ID",Integer.class)));
					hqm.setFstaff_display_name(t.get("FSTAFF_DISPLAY_NAME",String.class));
					hqm.setFstaff_emp_num(t.get("FSTAFF_EMPLOYEE_NO",String.class));
					list.add(hqm);				
				}
			}
			System.out.println("field staff list::"+list.size());
		}
		finally {
			em.close();
		}
		return list;
	}


	@Override
	public List<DoctorMaster> getAllActiveDoctorMasterDetail() throws Exception {
		EntityManager em = null;
		List<DoctorMaster> list = new ArrayList<DoctorMaster>();
		try {
			em = emf.createEntityManager();
			Query query = null;
				
			String q="from DoctorMaster WHERE doc_status='A'";
			query = em.createQuery(q);
			list=query.getResultList();
			System.out.println(list.get(0).getDoc_name());
			System.out.println(list.get(0).getMcl_no());
			System.out.println(list.get(0).getFstaff_emp_id());
			
			
			} finally {
				if (em != null) { em.close(); }
		}
		return list;
	}

	@Override
	public List<DoctorMaster> getDoctorTextParameterDetail(String name, String parameter,String text,String empId)
			throws Exception {
		EntityManager em = null;
		List<DoctorMaster> list = new ArrayList<DoctorMaster>();
		try {
			em = emf.createEntityManager();
			Query query = null;
				
			System.out.println("PARAMETERS:: : "+empId+"......"+name+"...."+parameter+"..."+text);
			
			if(name.equals("name")) {
				String q="from DoctorMaster WHERE doc_status='A'"
					//	+" and fstaff_id = ( SELECT fstaff_id FROM EmployeeDetails WHERE emp_id = '"+empId+"' ) and doc_name "+parameter+"  '%"+text+"%' order by doc_id";
						+"   and doc_name "+parameter+"  '%"+text+"%' order by doc_id";
				query = em.createQuery(q);
			}
			if(name.equals("mclNo")) {
				String q="from DoctorMaster WHERE doc_status='A'"
					//	+" and fstaff_id = ( SELECT fstaff_id FROM EmployeeDetails WHERE emp_id = '"+empId+"' )  and mcl_no "+parameter+"  '%"+text+"%' order by doc_id";
						+" and mcl_no "+parameter+"  '%"+text+"%' order by doc_id";
				query = em.createQuery(q);
			}
			if(name.equals("fstaffId")){
				String q="from DoctorMaster WHERE doc_status='A'"
					//	+" and fstaff_id = ( SELECT fstaff_id FROM EmployeeDetails WHERE emp_id = '"+empId+"' )  and fstaff_id "+parameter+"  '%"+text+"%' order by doc_id";
						+"  and fstaff_id "+parameter+"  '%"+text+"%' order by doc_id";
				query = em.createQuery(q);
			}
			
			  if(name.equals("empId")) { 	
				  String  q="from DoctorMaster WHERE doc_status='A'" 
				 //     +" and fstaff_id = ( SELECT fstaff_id FROM EmployeeDetails WHERE emp_id = '"+empId+"' ) and fstaff_emp_id "+parameter+"  '%"+text+"%' order by doc_id";
						  +" and fstaff_emp_id "+parameter+"  '%"+text+"%' order by doc_id";
			  query = em.createQuery(q); }
			  
			  if(name.equals("mobile")) { 	
				  String  q="from DoctorMaster WHERE doc_status='A'" 
				 //     +" and fstaff_id = ( SELECT fstaff_id FROM EmployeeDetails WHERE emp_id = '"+empId+"' ) and fstaff_emp_id "+parameter+"  '%"+text+"%' order by doc_id";
						  +" and   ( ( doc_phone "+parameter+" '%"+text+"%') OR ( doc_phone2 "+parameter+" '%"+text+"%') ) order by doc_id";
			  query = em.createQuery(q); 
			  }
			                           

			
			list=query.getResultList();
			
			} finally {
				if (em != null) { em.close(); }
		}
		return list;
	
		
	//	return null;
	}

	@Override
	public DoctorMaster getDoctorDetailById(String doc_id) throws Exception {
		EntityManager em = null;
		List<DoctorMaster> list = new ArrayList<DoctorMaster>();
		
		DoctorMaster dm = new DoctorMaster();
		try {
			em = emf.createEntityManager();
			Query query = null;
				
			String q="from DoctorMaster WHERE doc_status='A' and doc_id="+doc_id;
			query = em.createQuery(q);
			list=query.getResultList();
			} finally {
				if (em != null) { em.close(); }
		}
		
		dm = list.get(0);
		return dm;
	}
	
	@Override
	public DoctorMaster getByObjectId(Long doc_id) throws Exception {
		return this.entityManager.find(DoctorMaster.class, doc_id);
	}


	
	
	@Override
	public List<DivMaster> getStandardDivisionList() throws Exception {
		EntityManager em = null;
		List<DivMaster> list = new ArrayList<>();
		try {
			
			em = emf.createEntityManager();
			StringBuffer sb = new StringBuffer();
			sb.append("select div_id,div_disp_nm from DIV_MASTER where DIV_status = 'A' order by div_disp_nm");
			
			Query query = em.createNativeQuery(sb.toString(), Tuple.class);
			List<Tuple> tuples = query.getResultList();
			if (tuples != null && !tuples.isEmpty()) {
				for(Tuple t : tuples) {
					DivMaster dm=new DivMaster();
					dm.setDiv_id(Long.valueOf((t.get("div_id", Integer.class).toString())));
					dm.setDiv_disp_nm(t.get("div_disp_nm", String.class));
					
					list.add(dm);
				}
				
				
				System.out.println("divlist::"+list.size());
		} 
		}finally {
			if(em != null) { em.close(); }
		}
		if(list != null && !list.isEmpty()) {
			return list;
		}
		return null;
	}

	@Override
	public List<EmployeeDetails> getEmpIdByFStaffId(Long fstaff_id) throws Exception {
		EntityManager em = null;
		List<EmployeeDetails> lst= new ArrayList<>(); 
		
		em = emf.createEntityManager();
		String q="SELECT USR.EMP_ID" + 
				" FROM HR_M_EMPLOYEE USR JOIN FIELDSTAFF FS ON USR.fstaff_id =:fstaff_id" + 
				" WHERE USR.FSTAFF_ID IS NOT NULL AND USR.FSTAFF_ID <> 0";

	//	System.out.println("Fstaff id ::"+fstaff_id);
		Query query = em.createNativeQuery(q,Tuple.class);
		query.setParameter("fstaff_id", fstaff_id);
		List<Tuple> tuples = query.getResultList();
		for (Tuple t : tuples) {
			EmployeeDetails hqm=new EmployeeDetails();
			hqm.setEmp_id(t.get("EMP_ID",String.class));
			lst.add(hqm);				
		}
		return lst;
		
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
					builder.equal(root.get(EmployeeDetails_.fstaff_id), Long.valueOf(empId))
					);
			List<EmployeeDetails> list = entityManager.createQuery(criteriaQuery).getResultList();
			System.out.println("userlist :: "+list.size());
			if(list != null && !list.isEmpty()) {
				user = list.get(0);
			}
		} finally {
			//if(em != null) { em.close(); }
		}
		return user;
	}
	
	@Override
	public DoctorMaster getDoctorsByMclNumber(Long mcl_no) throws Exception {
		System.out.println("MCL NO "+mcl_no);
		
		List<DoctorMaster> list = null;
		try {
	
			CriteriaBuilder builder = entityManager.getCriteriaBuilder();
			CriteriaQuery<DoctorMaster> criteriaQuery = builder.createQuery(DoctorMaster.class);
			Root<DoctorMaster> root = criteriaQuery.from(DoctorMaster.class);
			criteriaQuery.select(root).where(builder.and(builder.equal(root.get(DoctorMaster_.mcl_no), mcl_no)),
							builder.and(builder.equal(root.get(DoctorMaster_.doc_status), 'A'))).orderBy(builder.asc(root.get(DoctorMaster_.doc_name)));
			list = entityManager.createQuery(criteriaQuery).getResultList();
			if(list!=null && list.size()>0)
				return list.get(0);
		} finally {
			
		}
		return null;
	}

	@Override
	public List<BulkUpldBean> getDoctorsForBulkUpload(String div_id, String fs_id, String compareString)
			throws Exception {
		// TODO Auto-generated method stub
		EntityManager em = null;
		List<BulkUpldBean> list = null;
		StringBuilder sb = new StringBuilder();
		try {
			list = new ArrayList<BulkUpldBean>();
			em = emf.createEntityManager();
			sb.append(" select  DM.DOC_EMAIL,DM.DOC_PHONE,DM.DOC_ADDRESS1,DM.DOC_ADDRESS2 ,DM.DOC_ADDRESS3,DM.DOC_ADDRESS4 "
					+ ",  DM.SLNO AS SLNO,DM.MCL_NO as MCL_NO ,DM.HCP_UNIQUE_ID AS ")
			.append(" HCP_UNIQUE_ID,CONCAT(DM.DOC_PREFIX,CONCAT(' ',DM.DOC_NAME)) DOC_NM, DM.DOC_CITY,DM.DOC_PINCODE,  ")
			.append(" FM.FSTAFF_DISPLAY_NAME,FM.FSTAFF_CODE,FM.FSTAFF_EMPLOYEE_NO,MAP.FSTAFF_ID, ")
			.append(" case when DM.ACTIVE = 'TRUE' then 'A' else 'I' end ACTIVE ,MAP.FSTAFF_TERR_CODE ")
			.append(" from DOCTOR_MASTER_MDM DM ")
			.append(" LEFT JOIN DOCTOR_MASTER_MDM_TERR_MAP MAP ON MAP.HCP_UNIQUE_ID=DM.HCP_UNIQUE_ID,FIELDSTAFF FM ")
			.append(" where FM.FSTAFF_ID = MAP.FSTAFF_ID ")
			//.append("AND DM.DOC_STATUS = 'A' ")
			.append(" AND FM.FSTAFF_DIV_ID = :divId ")
			.append(" AND FM.FSTAFF_LEVEL_CODE = '001' ")
			.append(" AND FM.FS_CATEGORY = 'F' ")
			.append(" AND FM.FSTAFF_status = 'A' ")
			.append(" AND FM.FSTAFF_LEAVING_DATE is null ")
			//.append("AND DM.DOC_NAME like '"+compareString+"%' ");
			.append(" AND MAP.SOURCE = 'PA' ")
			.append(" AND (RTRIM(DM.DOC_NAME) like '"+compareString+"%' ")
			.append(" OR RTRIM(DM.HCP_UNIQUE_ID) = '"+compareString+"') ");
			
			if(fs_id != null && !fs_id.trim().isEmpty() && !fs_id.trim().equals("null") && !Long.valueOf(fs_id.trim()).equals(0L)) {
				sb.append(" AND FM.FSTAFF_ID = :fstaff_id ");
			}
			sb.append(" ORDER BY DM.DOC_NAME");
			Query query = em.createNativeQuery(sb.toString(),Tuple.class);
			query.setParameter("divId", div_id);
			
			if(fs_id!=null && !fs_id.trim().isEmpty() && !fs_id.trim().equals("null") && !Long.valueOf(fs_id.trim()).equals(0L)) {
				query.setParameter("fstaff_id", fs_id);
			}
			//query.setParameter("compareString", compareString);
			
			List<Tuple> tuples = query.getResultList();
			System.out.println("doctorList.size:::"+tuples.size());
			for (Tuple t : tuples) {
				BulkUpldBean blkUpldBn=new BulkUpldBean();
				blkUpldBn.setSlno(Long.valueOf(t.get("SLNO",BigDecimal.class).toString()));
				blkUpldBn.setHcp_unique_id(t.get("HCP_UNIQUE_ID",String.class));
				blkUpldBn.setMcl_no(t.get("MCL_NO",String.class));
				blkUpldBn.setDoc_nm(t.get("DOC_NM",String.class));
				blkUpldBn.setDoc_city(t.get("DOC_CITY",String.class));
				blkUpldBn.setDoc_pincode(t.get("DOC_PINCODE",String.class));
				blkUpldBn.setFstaff_disp_name(t.get("FSTAFF_DISPLAY_NAME",String.class));
				blkUpldBn.setFstaff_code(t.get("FSTAFF_CODE",String.class));
				blkUpldBn.setFstaff_id(Long.valueOf(t.get("FSTAFF_ID",Integer.class).toString()));
				blkUpldBn.setFstaff_employee_no(t.get("FSTAFF_EMPLOYEE_NO",String.class));
				blkUpldBn.setDoc_status(t.get("ACTIVE",String.class).trim());
				blkUpldBn.setFstaff_terr_code(t.get("FSTAFF_TERR_CODE",String.class).trim());
				blkUpldBn.setAddres1(t.get("DOC_ADDRESS1",String.class).trim());
				blkUpldBn.setAddres2(t.get("DOC_ADDRESS2",String.class).trim());
				blkUpldBn.setAddres3(t.get("DOC_ADDRESS3",String.class).trim());
				blkUpldBn.setAddres4(t.get("DOC_ADDRESS4",String.class).trim());
				blkUpldBn.setPhone(t.get("DOC_PHONE",String.class).trim());
				blkUpldBn.setEmail(t.get("DOC_EMAIL",String.class).trim());
				
				
				
				list.add(blkUpldBn);
			}
		}
		catch(Exception e) {
			throw e;
		}
		return list;
	}

	@Override
	public List<CustomerMaster> getDoctorsFromMdmBySpecialityAndClass(String doc_class, String doc_spec) throws Exception {
		EntityManager em = null;
		List<CustomerMaster> list = null;
		StringBuilder sb = new StringBuilder();
		try {
			em = emf.createEntityManager();
			sb.append(" select dm.slno as cust_id,concat(dm.DOC_PREFIX,dm.DOC_NAME) as cust_name, ")
			.append(" dm.DOC_ADDRESS1 as addr1,dm.DOC_ADDRESS2 as addr2,dm.DOC_ADDRESS3 as addr3,dm.DOC_ADDRESS4 as addr4,")
			.append(" dm.DOC_EMAIL as email,'' as panno,dm.HCP_UNIQUE_ID as cust_cd,dm.DOC_PHONE as mobile,dm.doc_city as city")
			.append(" from DOCTOR_MASTER_MDM dm where dm.DOC_CLASS = :doc_class and dm.DOC_SPECIALITY = :doc_spec");
			
			Query query = em.createNativeQuery(sb.toString(),Tuple.class);
			query.setParameter("doc_class", doc_class);
			query.setParameter("doc_spec", doc_spec);
			
			List<Tuple> tuples = query.getResultList();
			System.out.println("doctorList.size:::"+tuples.size());
			list = new ArrayList<CustomerMaster>();
			for (Tuple t : tuples) {
				CustomerMaster cust_master = new CustomerMaster();
				cust_master.setCust_id((t.get("cust_id",BigDecimal.class)).longValue());
				cust_master.setCust_name_billto(t.get("cust_name",String.class));
				cust_master.setAddr1_shipto(t.get("addr1",String.class));
				cust_master.setAddr2_shipto(t.get("addr2",String.class));
				cust_master.setAddr3_shipto(t.get("addr3",String.class));
				cust_master.setAddr4_shipto(t.get("addr4",String.class));
				cust_master.setEmail(t.get("email",String.class));
				cust_master.setPan_no(t.get("panno",String.class));
				cust_master.setErp_cust_cd(t.get("cust_cd",String.class));
				cust_master.setMobile(t.get("mobile",String.class));
				cust_master.setDestination(t.get("city",String.class));
				list.add(cust_master);
			}
			}
		finally {
			if(em!= null) em.close();
		}
		return list;
	}

	@Override
	public List<Hcp_prod_details> getProduct_details(String id) {
		
		
		EntityManager em = null;
		List<Hcp_prod_details> list = null;

		try {
			em = emf.createEntityManager();
			StoredProcedureQuery procedurecall = em
					.createNamedStoredProcedureQuery("call_dg_blk_hcp_alloc_stock_showing");
			procedurecall.setParameter("MBLK_HCP_REQ_ID", Long.valueOf(id));
			list = procedurecall.getResultList();
			System.out.println("List :: " + list.size());
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
