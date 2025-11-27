package com.excel.repository;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.StoredProcedureQuery;
import javax.persistence.Tuple;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

import org.hibernate.NonUniqueResultException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.excel.bean.AllocationBean;
import com.excel.bean.CrmTdsBean;
import com.excel.bean.Parameter;
import com.excel.model.AspDetail;
import com.excel.model.AspDetail_;
import com.excel.model.Crm_GenDtl;
import com.excel.model.Crm_GenHd;
import com.excel.model.ManualDispatchList_;
import com.excel.model.Period;
import com.excel.model.Period_Tds;
import com.excel.model.Period_Tds_;
import com.excel.model.Sfa_Field_Master;
import com.excel.model.Sfa_Field_Master_;
import com.excel.model.Sfa_Geographical_Master;
import com.excel.model.Sfa_Retail_Consumer;
import com.excel.model.TdsApplicableProduct;
import com.excel.model.TdsApplicableStatementReport;
import com.excel.model.Tds_Crm_Expense;
import com.excel.model.Tds_Crm_Upload;
import com.excel.model.Tds_crm_Images;
import com.excel.utility.CodifyErrors;
import com.excel.utility.MedicoConstants;
import com.excel.utility.MedicoResources;

@Repository
public class CrmExpenseRepositoryImpl implements CrmExpenseRepository {

	@Autowired
	private EntityManagerFactory emf;
	@PersistenceContext
	private EntityManager entityManager;
	

	@Override
	public List<Sfa_Geographical_Master> getHQ() {
		List<Sfa_Geographical_Master> list = null;

		try {

			StringBuffer sb = new StringBuffer();
//			sb.append(" SELECT DISTINCT GM.LOC_CODE,GM.LOC_ID,GM.LOC_NAME");
//			sb.append(" FROM SFA_GEOGRAPHICAL_MASTER GM , SFA_FIELD_MASTER FM WHERE GM.LOC_TYPE = '001' ");
//			sb.append(" AND GM.LOC_ID = FM.GEOG_LVL1_HQ AND FM.LEVEL_CODE = '005'  ");
//			sb.append(" ORDER BY GM.LOC_NAME");
			
			sb.append(" select DISTINCT GM.LOC_CODE,GM.LOC_ID,GM.LOC_NAME,CA.SALES_REP_ID,DH.FSTAFF_ID from ");
			sb.append(" SFA_GEOGRAPHICAL_MASTER GM inner join SFA_FIELD_MASTER FM on GM.LOC_ID = FM.GEOG_LVL1_HQ ");
			sb.append(" left outer join SFA_CUSTOMER_ALLOCATION CA ON CA.SALES_REP_ID = FM.FS_ID ");
			sb.append(" left outer join SFA_DOCTOR_HEADER DH ON DH.FSTAFF_ID = FM.FS_ID ");
			sb.append(" where ( DH.FSTAFF_ID is not null or CA.SALES_REP_ID is not null) ");
			sb.append(" and GM.LOC_TYPE = '001' AND FM.LEVEL_CODE = '005' ORDER BY GM.LOC_NAME ");

			Query query = entityManager.createNativeQuery(sb.toString(), Tuple.class);

			List<Tuple> tuples = query.getResultList();
			if (tuples != null && !tuples.isEmpty()) {
				list = new ArrayList<>();
				Sfa_Geographical_Master object = null;
				for (Tuple t : tuples) {
					object = new Sfa_Geographical_Master();
					object.setLoc_code(t.get("LOC_CODE", String.class));
					object.setLoc_id(Long.valueOf((t.get("LOC_ID", BigDecimal.class)).toString()));
					object.setLoc_name(t.get("LOC_NAME", String.class));
					list.add(object);
				}
			}
			System.out.println("getHQ ASize" + list.size());
		} finally {

		}
		return list;
	}

	@Override
	public List<Sfa_Field_Master> getSe(Long geog_lvl1_hq) {
		List<Sfa_Field_Master> list = null;
		EntityManager em = null;
		try {
			em = emf.createEntityManager();
			CriteriaBuilder builder = em.getCriteriaBuilder();
			CriteriaQuery<Sfa_Field_Master> criteriaQuery = builder.createQuery(Sfa_Field_Master.class);
			Root<Sfa_Field_Master> root = criteriaQuery.from(Sfa_Field_Master.class);
			criteriaQuery
					.multiselect(root.get(Sfa_Field_Master_.fs_id), root.get(Sfa_Field_Master_.fs_code),
							root.get(Sfa_Field_Master_.fs_name), root.get(Sfa_Field_Master_.geog_lvl1_hq))
					.where(builder.equal(root.get(Sfa_Field_Master_.level_code), 005),
							builder.equal(root.get(Sfa_Field_Master_.geog_lvl1_hq), geog_lvl1_hq))
					.orderBy(builder.asc(root.get(Sfa_Field_Master_.fs_name)));
			list = em.createQuery(criteriaQuery).getResultList();

		} finally {
			if (em != null) {
				em.close();
			}
		}
		return list;
	}

	public List<Sfa_Retail_Consumer> getcustomerAddRetailer(Long se, String custtype) {
		List<Sfa_Retail_Consumer> list = null;
		try {
			StringBuffer sb = new StringBuffer();

			if (custtype.equalsIgnoreCase("HCP")) {
				sb.append(" SELECT DH.DOC_ID CUST_ID,(DOC_PREFIX+DH.DOC_FNAME) CUST_NAME,ISNULL(DS.DOC_HADDR1,' ' ) ADDR1,");
				sb.append(
						" ISNULL(DS.DOC_HADDR2,' ' ) ADDR2,ISNULL(DS.DOC_HADDR3,' ' ) ADDR3,ISNULL(DS.DOC_HADDR4,' ' ) ADDR4,");
				sb.append(
						" ISNULL(DS.DOC_HCITY,' ' ) CITY,ISNULL(DS.DOC_HPINCODE,' ' ) PINCODE,DS.DOC_MOBILE TEL_NO,ISNULL(DS.DOC_EMAIL,' ' ) EMAIL,");
				sb.append(" ISNULL(DH.PAN_NO,' ' ) PAN_NO,DH.HCP_NO HCP_NO");
				sb.append(" FROM SFA_DOCTOR_HEADER DH , SFA_DOCTOR_SUB DS");
				sb.append(" WHERE DH.FSTAFF_ID =:fsId");
				sb.append(" AND DH.DOC_ID = DS.DOC_ID ORDER BY DH.DOC_FNAME");

				Query query = entityManager.createNativeQuery(sb.toString(), Tuple.class);
				query.setParameter("fsId", se);
				List<Tuple> tuples = query.getResultList();
				
				if (tuples != null && !tuples.isEmpty()) {
					list = new ArrayList<>();
					Sfa_Retail_Consumer object = null;
					for (Tuple t : tuples) {
						object = new Sfa_Retail_Consumer();
						object.setCust_id(Long.valueOf(t.get("Cust_id", BigDecimal.class).toString()));
						object.setCust_name(t.get("Cust_name", String.class));
						object.setAddr1(t.get("Addr1", String.class));
						object.setAddr2(t.get("Addr2", String.class));
						object.setAddr3(t.get("Addr3", String.class));
						object.setAddr4(t.get("Addr4", String.class));
						object.setCity((t.get("City", String.class)));
						object.setPincode(t.get("Pincode", String.class));
						object.setTel_no(t.get("Tel_no", String.class));
						object.setEmail(t.get("Email", String.class));
						object.setPan_no(t.get("Pan_no", String.class));
						object.setHcp_no(t.get("Hcp_no", String.class));
						list.add(object);
					}
					System.out.println("get Address and contact" + list.size());
					if (!list.isEmpty() && list.size() > 0)
						return list;
				}
				
			} else if (custtype.equalsIgnoreCase("RTL")) {
				sb.append(
						" SELECT RC.SR_NO CUST_ID,CONCAT(RC.CONSUMER_NAME,'-', RC.CONSUMER_CD) CUST_NAME,RC.ADDRESS ADDR1,' ' ADDR2,' ' ADDR3,' ' ADDR4,");
				sb.append(
						" ISNULL(RC.DESTINATION,' ' ) CITY,' ' PINCODE,ISNULL(RC.CONTACT_NO ,0 ) TEL_NO,RC.EMAIL_ID EMAIL,");
				sb.append(" RC.PAN_NO,HCP_NO HCP_NO FROM SFA_RETAIL_CONSUMER RC");
				sb.append("	WHERE RC.FS_ID =:fsId");
				sb.append(" ORDER BY RC.CONSUMER_NAME");

				
				Query query = entityManager.createNativeQuery(sb.toString(), Tuple.class);
				query.setParameter("fsId", se);
				List<Tuple> tuples = query.getResultList();
				
				if (tuples != null && !tuples.isEmpty()) {
					list = new ArrayList<>();
					Sfa_Retail_Consumer object = null;
					for (Tuple t : tuples) {
						object = new Sfa_Retail_Consumer();
						object.setCust_id(Long.valueOf(t.get("Cust_id", BigDecimal.class).toString()));
						object.setCust_name(t.get("Cust_name", String.class));
						object.setAddr1(t.get("Addr1", String.class));
						object.setAddr2(t.get("Addr2", String.class));
						object.setAddr3(t.get("Addr3", String.class));
						object.setAddr4(t.get("Addr4", String.class));
						object.setCity((t.get("City", String.class)));
						object.setPincode(t.get("Pincode", String.class));
						object.setTel_no(t.get("Tel_no", BigDecimal.class).setScale(0).toString());
						object.setEmail(t.get("Email", String.class));
						object.setPan_no(t.get("Pan_no", String.class));
						object.setHcp_no(t.get("Hcp_no", String.class));
						list.add(object);
					}
					System.out.println("get Address and contact" + list.size());
					if (!list.isEmpty() && list.size() > 0)
						return list;
				}
				
			} else if (custtype.equalsIgnoreCase("WHL")) {
				sb.append(" SELECT CM.CUST_ID,CM.CUST_NAME,CM.ADDR1,CM.ADDR2,CM.ADDR3,CM.ADDR4,CM.DESTINATION CITY,");
				sb.append(" ' ' PINCODE,CM.TEL_NO,CM.EMAIL,CM.PAN_NO,CM.ERP_CUST_CD HCP_NO,CA.DIV_ID");
				sb.append(" FROM SFA_CUSTOMER_MASTER CM , SFA_CUSTOMER_ALLOCATION CA , SFA_FIELD_MASTER FM");
				sb.append(" WHERE FM.FS_ID =:fsId AND CM.CUST_ID = CA.CUST_ID AND CA.SALES_REP_ID = FM.FS_ID ");
				sb.append(" AND CA.DIV_ID = FM.DIV_ID AND CA.CURR_STATUS = 'A'");
				sb.append(" ORDER BY CUST_NAME");
				
				Query query = entityManager.createNativeQuery(sb.toString(), Tuple.class);
				query.setParameter("fsId", se);
				List<Tuple> tuples = query.getResultList();
				
				if (tuples != null && !tuples.isEmpty()) {
					list = new ArrayList<>();
					Sfa_Retail_Consumer object = null;
					for (Tuple t : tuples) {
						object = new Sfa_Retail_Consumer();
						object.setCust_id(Long.valueOf(t.get("Cust_id", BigDecimal.class).toString()));
						object.setCust_name(t.get("Cust_name", String.class));
						object.setAddr1(t.get("Addr1", String.class));
						object.setAddr2(t.get("Addr2", String.class));
						object.setAddr3(t.get("Addr3", String.class));
						object.setAddr4(t.get("Addr4", String.class));
						object.setCity((t.get("City", String.class)));
						object.setPincode(t.get("Pincode", String.class));
						object.setTel_no(t.get("Tel_no", String.class));
						object.setEmail(t.get("Email", String.class));
						object.setPan_no(t.get("Pan_no", String.class));
						object.setHcp_no(t.get("Hcp_no", String.class));
						list.add(object);
					}
					System.out.println("get Address and contact" + list.size());
					if (!list.isEmpty() && list.size() > 0)
						return list;
				}
			}


		} finally {

		}
		return list;
	}

	@Override
	public Sfa_Field_Master getFsById(Long Id) {
		// TODO Auto-generated method stub
		Sfa_Field_Master sfa =null;
		try {
			System.out.println("FsId : "+Id);
			CriteriaBuilder builder = entityManager.getCriteriaBuilder();
			CriteriaQuery<Sfa_Field_Master> criteriaQuery = builder.createQuery(Sfa_Field_Master.class);
			Root<Sfa_Field_Master> root = criteriaQuery.from(Sfa_Field_Master.class);
			criteriaQuery.select(root).where(builder.equal(root.get(Sfa_Field_Master_.fs_id), Id));
			sfa = entityManager.createQuery(criteriaQuery).getSingleResult();
		}
		catch (NoResultException e ) {
			// TODO: handle exception
			return null;
		}
		catch (NonUniqueResultException e) {
			// TODO: handle exception
			return null;
		}
		catch (Exception e) {
			// TODO: handle exception
			throw e;
		}
		return sfa;
	}

	@Override
	//@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public void SaveTds_crm_master_annual_(Tds_Crm_Expense tdscrm, String Tablename, Period_Tds pr) throws Exception {
		// TODO Auto-generated method stub
	//	EntityManager em = null;
		try {
			//em = emf.createEntityManager();
			StringBuffer sb = new StringBuffer();
			
			String getmonth = pr.getPeriod_code();//tdscrm.getCrm_exps_date().split("-")[1];
			System.out.println("GetMonth : "+getmonth);
			sb.append(" insert into ")
			.append(Tablename);
			sb.append(" (HCP_TYPE,Unique_HCP_ID,SUB_COMP_CODE,Taxyear_ID,Name_of_HCP,HCP_PAN_Number,HCP_Address_1,");
			sb.append(" HCP_Address_2,HCP_Address_3,HCP_Address_4,HCP_PINCODE,HCP_State,HCP_Mobile_No,HCP_Email_ID,");
			sb.append(" PS_VAL_TDSMTH").append(Integer.parseInt(getmonth)).append(",");//.append(Integer.parseInt(pr.getPeriod_code())).append(",");
			sb.append(" TDSPAID_TDSMTH").append(Integer.parseInt(getmonth)).append(",");//.append(Integer.parseInt(pr.getPeriod_code())).append(",");
			sb.append(" PENALTY_PAID_TDSMTH").append(Integer.parseInt(getmonth)).append(",");
			sb.append(" INTEREST_PAID_TDSMTH").append(Integer.parseInt(getmonth)).append(",");
			sb.append(" HCP_TDS_EXEMPT,HCP_CITY)"); 
			sb.append(" VALUES(:hcptype,:uniqhcpid,:subcomp,:finyear,:namehcp,:panno,:hcpaddr1,:hcpaddr2,:hcpaddr3,:hcpaddr4,");
			sb.append(" :hcppincd,:hcpstate,:hcpmobile,:hcpemail,:psval,:tdspaid,:penaltypaid,:interestpaid,:tds_exempt,:hcp_city)");
		//	em.getTransaction().begin();
			Query query = entityManager.createNativeQuery(sb.toString());
			query.setParameter("hcptype", tdscrm.getCrm_cust_type().trim());
			query.setParameter("uniqhcpid", tdscrm.getCrm_cust_hcp_no().trim());
			query.setParameter("subcomp", tdscrm.getCrm_sub_comp_code());
			query.setParameter("finyear", tdscrm.getCrm_finyear());
			query.setParameter("namehcp", tdscrm.getCrm_cust_name());
			query.setParameter("panno", tdscrm.getCrm_cust_panno());
			query.setParameter("hcpaddr1", tdscrm.getCrm_cust_addr1());
			query.setParameter("hcpaddr2", tdscrm.getCrm_cust_addr2());
			query.setParameter("hcpaddr3", tdscrm.getCrm_cust_addr3());
			query.setParameter("hcpaddr4", tdscrm.getCrm_cust_addr4());
			query.setParameter("hcppincd", tdscrm.getCrm_cust_pincode());
			query.setParameter("hcpstate", tdscrm.getState_id());
			query.setParameter("hcpmobile", tdscrm.getCrm_cust_mobile());
			query.setParameter("hcpemail", tdscrm.getCrm_cust_email());
			if(tdscrm.getGrossup_ind().trim().equals("Y")) {
				query.setParameter("psval", tdscrm.getGrossup_crm_value());
			}
			else {
				query.setParameter("psval", tdscrm.getCrm_value());
			}
			query.setParameter("tdspaid", tdscrm.getCrm_tds_paid());
			query.setParameter("penaltypaid", tdscrm.getCrm_tds_penalty());
			query.setParameter("interestpaid",tdscrm.getCrm_tds_interest());
			query.setParameter("tds_exempt","N");
			query.setParameter("hcp_city", tdscrm.getCrm_cust_city());
			int j = query.executeUpdate();
		//	entityManager.getTransaction().commit();
			System.out.println("j : "+j);
		}
		catch (Exception e) {
			// TODO: handle exception
			//em.getTransaction().rollback();
			throw e;
		}
	}

	@Override
	public Object[] findObjByCustType(String tablename, String custId, String custType) throws Exception {
		// TODO Auto-generated method stub
		try {
			System.out.println("hcptype"+ custType);
			System.out.println("hcpid"+custId);
			
			StringBuffer sb = new StringBuffer();
			sb.append("Select * from ");
			sb.append(tablename);
			sb.append(" where HCP_TYPE=:hcptype and ");
			sb.append(" Unique_HCP_ID =:hcpid");
			
			Query query = entityManager.createNativeQuery(sb.toString());
			query.setParameter("hcptype", custType);
			query.setParameter("hcpid", custId);
			
			List<Object[]> list =query.getResultList();
			if(list!=null && list.size()>0) {
				System.out.println("list : "+list.size());
				return list.get(0);
			}
		}
		catch(Exception e) {
			throw e;
		}
		return null;
	}

	@Override
//	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public void UpdateAnnualTdsCrm(String tablename, String custhcpno, String custType, Object[] obj,
			Tds_Crm_Expense tdscrm,Period_Tds pr) throws Exception {
		// TODO Auto-generated method stub
		StringBuffer sb = new StringBuffer();
		try {String getmonth = pr.getPeriod_code();// tdscrm.getCrm_exps_date().split("-")[1];
		System.out.println("GetMonth : " + getmonth);
		System.out.println("tablename : " + tablename);
		System.out.println("psVal : " + new BigDecimal((obj[(14 + (Integer.parseInt(getmonth)))]).toString()));
		System.out.println("psMth : " + new BigDecimal((obj[(26 + (Integer.parseInt(getmonth)))]).toString()));

		sb.append("UPDATE ");
		sb.append(tablename);
		sb.append(" SET PS_VAL_TDSMTH").append(Integer.parseInt(getmonth)).append(" =:psmthval");// .append(Integer.parseInt(pr.getPeriod_code())).append("
																									// =:psmthval");
		sb.append(" , TDSPAID_TDSMTH").append(Integer.parseInt(getmonth)).append(" =:tdspaidmth");// .append(Integer.parseInt(pr.getPeriod_code())).append("
																									// =:tdspaidmth");
		sb.append(" , PENALTY_PAID_TDSMTH").append(Integer.parseInt(getmonth)).append(" =:penalty");
		sb.append(" , INTEREST_PAID_TDSMTH").append(Integer.parseInt(getmonth)).append(" =:interest");
		sb.append(" , HCP_TDS_EXEMPT =:tdsexempt");
		sb.append(" where HCP_TYPE =:hcptype and Unique_HCP_ID=:hcpId ");

		Query query = entityManager.createNativeQuery(sb.toString());
		if (tdscrm.getGrossup_ind().trim().equals("Y")) {
			query.setParameter("psmthval", new BigDecimal((obj[(14 + (Integer.parseInt(getmonth)))]).toString())
					.add(tdscrm.getGrossup_crm_value()));
		} else {
//			System.out.println((obj[(15 + (Integer.parseInt(getmonth)))]).toString());
			BigDecimal psmthval = new BigDecimal((obj[(14 + (Integer.parseInt(getmonth)))]).toString())
					.add(tdscrm.getCrm_value());
			query.setParameter("psmthval", psmthval);
		}
		
	
		query.setParameter("tdspaidmth",new BigDecimal((obj[(26 + (Integer.parseInt(getmonth)))]).toString())
				.add(tdscrm.getCrm_tds_paid()));
		// query.setParameter("psmthval", new BigDecimal((obj[(15+(Integer.parseInt(pr.getPeriod_code())-1))]).toString()).add(tdscrm.getCrm_value()));
		// query.setParameter("tdspaidmth",new BigDecimal((obj[(27+(Integer.parseInt(pr.getPeriod_code())-1))]).toString()).add(tdscrm.getCrm_tds_paid()));
		query.setParameter("penalty", new BigDecimal((obj[(40 + (Integer.parseInt(getmonth)))]).toString())
				.add(tdscrm.getCrm_tds_penalty()));
		query.setParameter("interest", new BigDecimal((obj[(52 + (Integer.parseInt(getmonth)))]).toString())
				.add(tdscrm.getCrm_tds_interest()));
		query.setParameter("tdsexempt", "N");
		query.setParameter("hcptype", custType.trim());
		query.setParameter("hcpId", custhcpno.trim());

		int i = query.executeUpdate();
//		System.out.println("i " + i);
	}
		catch(Exception e) {
			throw e;
		}
	}

	@Override
	public List<Tds_Crm_Expense> getviewByEmpId(String empId) throws Exception {
		// TODO Auto-generated method stub
		 List<Tds_Crm_Expense> list = new ArrayList<Tds_Crm_Expense>();
		try {
			CriteriaBuilder builder = entityManager.getCriteriaBuilder();
			CriteriaQuery<Tds_Crm_Expense> criteriaQuery = builder.createQuery(Tds_Crm_Expense.class);
			Root<Tds_Crm_Expense> root = criteriaQuery.from(Tds_Crm_Expense.class);
			criteriaQuery.select(root).where(builder.equal(root.get("crm_ins_by"), empId),
					builder.equal(root.get("crm_confirm"), "N")).orderBy(builder.desc(root.get("crm_exps_date")));
			list = entityManager.createQuery(criteriaQuery).getResultList();
		}
		catch (Exception e) {
			// TODO: handle exception
			throw e;
		}
		return list;
	}

	@Override
	public Tds_Crm_Expense getObjById(Long Id) throws Exception {
		// TODO Auto-generated method stub
		return entityManager.find(Tds_Crm_Expense.class, Id);
	}

	@Override
	public String UpdateCRM(CrmTdsBean bean) throws Exception {
		// TODO Auto-generated method stub
		StringBuffer sb = new StringBuffer();
		try {
			sb.append("UPDATE TDS_CRM_EXPENSE set ");
			sb.append(" CRM_EXPS_DATE =:expsdate,");
			sb.append(" CRM_ACC_TRAN_NO=:acctranno,");
			sb.append(" CRM_EXPS_DETAILS=:expsdetails,");
			sb.append(" CRM_QTY=:crmqty,");
			sb.append(" CRM_RATE=:crmrate,");
			sb.append(" CRM_VALUE=:crmval,");
			sb.append(" CRM_TDS_PAID=:crmtdspaid,");
			sb.append(" CRM_TDS_PENALTY=:crmpenalty,");
			sb.append(" CRM_TDS_INTEREST=:crminterest,");
			sb.append(" LAST_MOD_DT=:lastmoddt,");
			sb.append(" LAST_MOD_BY=:lastmodby");
			sb.append(" WHERE CRM_ID=:crm_id");
			
			Query query = entityManager.createNativeQuery(sb.toString());
			query.setParameter("expsdate",MedicoResources.convertUtilDateToString_YYYY_MM_DD_HH_MM_SS(bean.getTrandate()));
			query.setParameter("acctranno", bean.getTransactionno());
			query.setParameter("expsdetails", bean.getExpensedetail());
			query.setParameter("crmqty", bean.getQty());
			query.setParameter("crmrate", bean.getRate());
			query.setParameter("crmval", bean.getVal());
			query.setParameter("crmtdspaid", bean.getTdspaid());
			query.setParameter("crmpenalty", bean.getPenaltypaid());
			query.setParameter("crminterest", bean.getIntrestpaid());
			query.setParameter("lastmoddt", new Date());
			query.setParameter("lastmodby", bean.getUsername());
			query.setParameter("crm_id", bean.getCrm_id());
			query.executeUpdate();
		}
		catch (Exception e) {
			// TODO: handle exception
			throw e;
		}
		return null;
	}

	@Override
	public List<Tds_crm_Images> geImageBycrmId(Long crmId) throws Exception {
		// TODO Auto-generated method stub
		List<Tds_crm_Images> list =new ArrayList<Tds_crm_Images>();
		try {
			CriteriaBuilder builder = entityManager.getCriteriaBuilder();
			CriteriaQuery<Tds_crm_Images> criteriaQuery = builder.createQuery(Tds_crm_Images.class);
			Root<Tds_crm_Images> root = criteriaQuery.from(Tds_crm_Images.class);
			criteriaQuery.select(root).where(builder.equal(root.get("crm_id"), crmId));
			list = entityManager.createQuery(criteriaQuery).getResultList();
		}
		catch (Exception e) {
			// TODO: handle exception
			throw e;
		}
		return list;
	}

	@Override
	public void deleteImagesByCrmId(Long crmId) throws Exception {
		// TODO Auto-generated method stub
		StringBuffer sb = new StringBuffer();
		try {
			sb.append("Delete TDS_CRM_IMAGES Where CRM_ID=:crmId");
			
			Query query = entityManager.createNativeQuery(sb.toString());
			query.setParameter("crmId", crmId);
			
			query.executeUpdate();
		}
		catch (Exception e) {
			// TODO: handle exception
			throw e;
		}
	}
	
	public List<TdsApplicableStatementReport> getTdsSummarryData(String sub_comp,String custType,Character tdsReq){
		List<TdsApplicableStatementReport> list=null;
		
		try {
			System.out.println("sub_comp"+sub_comp);
			System.out.println("custtype"+custType);
			System.out.println("tdsreqd"+tdsReq);
			
			StoredProcedureQuery query  = entityManager.createNamedStoredProcedureQuery("callTDS_APPLICABLE_STATEMENT_REPORT");
			query.setParameter("sub_comp",sub_comp);
			query.setParameter("custtype",custType);
			query.setParameter("tdsreqd",tdsReq);
			list=query.getResultList();
			
			System.out.println("Size of Tds Summary :: "+list.size());
			
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("Error Occurred :" +e.getMessage());// uncomment asneeded --;
		}
			finally {
		}		
		return list;
	}
	@Override
	public List<TdsApplicableProduct> getTdsDetailData(String sub_comp, String hcp_cust_id,String finyear) {
		List<TdsApplicableProduct> list=null;
		try {
			System.out.println("sub_comp "+sub_comp);
			System.out.println("hcp_cust_id "+hcp_cust_id);
			//hcp_cust_id="169094";
			//Long fin=Long.valueOf(finyear)-1;
			Long fin=Long.valueOf(finyear);
			StoredProcedureQuery query  = entityManager.createNamedStoredProcedureQuery("callTDS_APPLICABLE_PRODUCT_REPORT");
			query.setParameter("sub_comp","OPF");
			query.setParameter("hcp_cust_id",hcp_cust_id);
			query.setParameter("finyear",String.valueOf(fin));
			//query.setParameter("finyear",String.valueOf("2023"));
			
			list=query.getResultList();
			
			System.out.println("Size of Tds Detail :: "+list.size());
		} catch (Exception e) {
			System.out.println("Error Occurred :" +e.getMessage());// uncomment asneeded --;
		}
			finally {
		}
			
		return list;
	}

	@Override
	public List<Period_Tds> getPeriod(String period_fin_year){
		System.out.println(period_fin_year);
		List<Period_Tds> list =new ArrayList<Period_Tds>();
		try {
			CriteriaBuilder builder = entityManager.getCriteriaBuilder();
			CriteriaQuery<Period_Tds> criteriaQuery = builder.createQuery(Period_Tds.class);
			Root<Period_Tds> root = criteriaQuery.from(Period_Tds.class);
			criteriaQuery.multiselect(root.get(Period_Tds_.period_name)).where(builder.equal(root.get(Period_Tds_.period_status), "A"),
					builder.equal(root.get(Period_Tds_.period_fin_year), period_fin_year)).orderBy(builder.asc(root.get(Period_Tds_.period_code)));
			list = entityManager.createQuery(criteriaQuery).getResultList();
			System.out.println(list.size());
		}
		catch (Exception e) {
			// TODO: handle exception
			 System.out.println("Error Occurred :" +e.getMessage());// uncomment asneeded --;;
		}
		return list;
	}

	@Override
	public List<Sfa_Retail_Consumer> getBulkAddress(String se, String custtype) {
		List<Sfa_Retail_Consumer> list = null;
		List<Long> seids = new ArrayList<Long>();
		try {
			StringBuffer sb = new StringBuffer();
			for(String s: se.split(",")) {
				seids.add(Long.valueOf(s));
			}
			
			if (custtype.equalsIgnoreCase("HCP")) {
//				sb.append(" SELECT DH.DOC_ID CUST_ID,(DOC_PREFIX+DH.DOC_FNAME) CUST_NAME,ISNULL(DS.DOC_HADDR1,' ' ) ADDR1,");
//				sb.append(" ISNULL(DS.DOC_HADDR2,' ' ) ADDR2,ISNULL(DS.DOC_HADDR3,' ' ) ADDR3,ISNULL(DS.DOC_HADDR4,' ' ) ADDR4,");
//				sb.append(" ISNULL(DS.DOC_HCITY,' ' ) CITY,ISNULL(DS.DOC_HPINCODE,' ' ) PINCODE,DS.DOC_MOBILE TEL_NO,ISNULL(DS.DOC_EMAIL,' ' ) EMAIL,");
//				sb.append(" ISNULL(DH.PAN_NO,' ' ) PAN_NO,DH.HCP_NO HCP_NO,FS.FS_NAME");
//				sb.append(" FROM SFA_DOCTOR_HEADER DH inner join SFA_FIELD_MASTER FS ON FS.FS_ID=dH.FSTAFF_ID, SFA_DOCTOR_SUB DS ");
//				sb.append(" WHERE DH.FSTAFF_ID in(:fsId)");
//				sb.append(" AND DH.DOC_ID = DS.DOC_ID ORDER BY DH.DOC_FNAME");
				
				sb.append(" SELECT DH.DOC_ID CUST_ID,(DOC_PREFIX+DH.DOC_FNAME) CUST_NAME,ISNULL(DS.DOC_HADDR1,' ' ) ADDR1,");
				sb.append(" ISNULL(DS.DOC_HADDR2,' ' ) ADDR2,ISNULL(DS.DOC_HADDR3,' ' ) ADDR3,ISNULL(DS.DOC_HADDR4,' ' ) ADDR4,");
				sb.append(" ISNULL(DS.DOC_HCITY,' ' ) CITY,ISNULL(DS.DOC_HPINCODE,' ' ) PINCODE,DS.DOC_MOBILE TEL_NO,ISNULL(DS.DOC_EMAIL,' ' ) EMAIL,");
				sb.append(" ISNULL(DH.PAN_NO,' ' ) PAN_NO,DH.HCP_NO HCP_NO,FS.FS_NAME");
				sb.append("  FROM ( SELECT SD.HCP_NO,MAX(SD.DOC_ID) DOC_ID  FROM SFA_DOCTOR_HEADER SD GROUP BY SD.HCP_NO ) SD ,");
				sb.append(" SFA_DOCTOR_HEADER DH inner join SFA_FIELD_MASTER FS ON FS.FS_ID=dH.FSTAFF_ID, SFA_DOCTOR_SUB DS  ");
				sb.append(" WHERE ");
				if(!seids.contains(0l)) {
					sb.append(" DH.FSTAFF_ID in(:fsId) AND");
				}
				sb.append(" SD.DOC_ID = DH.DOC_ID AND");
				sb.append(" DH.DOC_ID = DS.DOC_ID ORDER BY DH.DOC_FNAME ");

				Query query = entityManager.createNativeQuery(sb.toString(), Tuple.class);
				if(!seids.contains(0l)) {
					query.setParameter("fsId", seids);
				}
				List<Tuple> tuples = query.getResultList();
				
				if (tuples != null && !tuples.isEmpty()) {
					list = new ArrayList<>();
					Sfa_Retail_Consumer object = null;
					for (Tuple t : tuples) {
						object = new Sfa_Retail_Consumer();
						object.setCust_id(Long.valueOf(t.get("Cust_id", BigDecimal.class).toString()));
						object.setCust_name(t.get("Cust_name", String.class));
						object.setAddr1(t.get("Addr1", String.class));
						object.setAddr2(t.get("Addr2", String.class));
						object.setAddr3(t.get("Addr3", String.class));
						object.setAddr4(t.get("Addr4", String.class));
						object.setCity((t.get("City", String.class)));
						object.setPincode(t.get("Pincode", String.class));
						object.setTel_no(t.get("Tel_no", String.class));
						object.setEmail(t.get("Email", String.class));
						object.setPan_no(t.get("Pan_no", String.class));
						object.setHcp_no(t.get("Hcp_no", String.class));
						object.setFs_name(t.get("FS_NAME", String.class));
						list.add(object);
					}
					System.out.println("get Address and contact" + list.size());
					if (!list.isEmpty() && list.size() > 0)
						return list;
				}
				
			} else if (custtype.equalsIgnoreCase("RTL")) {
//				sb.append(" SELECT RC.SR_NO CUST_ID,CONCAT(RC.CONSUMER_NAME,'-', RC.CONSUMER_CD) CUST_NAME,RC.ADDRESS ADDR1,' ' ADDR2,' ' ADDR3,' ' ADDR4,");
//				sb.append(" ISNULL(RC.DESTINATION,' ' ) CITY,' ' PINCODE,ISNULL(RC.CONTACT_NO ,0 ) TEL_NO,RC.EMAIL_ID EMAIL,");
//				sb.append(" RC.PAN_NO,HCP_NO HCP_NO FROM SFA_RETAIL_CONSUMER RC");
//				sb.append("	WHERE RC.FS_ID in(:fsId)");
//				sb.append(" ORDER BY RC.CONSUMER_NAME");

				sb.append(" SELECT RC.SR_NO CUST_ID,CONCAT(RC.CONSUMER_NAME,'-', RC.CONSUMER_CD) CUST_NAME,RC.ADDRESS ADDR1,' ' ADDR2,' ' ADDR3,' ' ADDR4,");
				sb.append(" ISNULL(RC.DESTINATION,' ' ) CITY,' ' PINCODE,ISNULL(RC.CONTACT_NO ,0 ) TEL_NO,RC.EMAIL_ID EMAIL,");
				sb.append(" RC.PAN_NO,RC.HCP_NO HCP_NO");
				sb.append(" FROM ( SELECT SR.HCP_NO,MAX( SR.SR_NO ) SR_NO FROM SFA_RETAIL_CONSUMER SR GROUP BY  SR.HCP_NO ) SR ,");
				sb.append(" SFA_RETAIL_CONSUMER RC");
				sb.append(" WHERE ");
				if(!seids.contains(0l)) {
					sb.append(" RC.FS_ID in(:fsId) AND");
				}
				sb.append(" SR.SR_NO = RC.SR_NO");
				sb.append(" ORDER BY RC.CONSUMER_NAME");
				 
				Query query = entityManager.createNativeQuery(sb.toString(), Tuple.class);
				if(!seids.contains(0l)) {
					query.setParameter("fsId", seids);
				}
				List<Tuple> tuples = query.getResultList();
				
				if (tuples != null && !tuples.isEmpty()) {
					list = new ArrayList<>();
					Sfa_Retail_Consumer object = null;
					for (Tuple t : tuples) {
						object = new Sfa_Retail_Consumer();
						object.setCust_id(Long.valueOf(t.get("Cust_id", BigDecimal.class).toString()));
						object.setCust_name(t.get("Cust_name", String.class));
						object.setAddr1(t.get("Addr1", String.class));
						object.setAddr2(t.get("Addr2", String.class));
						object.setAddr3(t.get("Addr3", String.class));
						object.setAddr4(t.get("Addr4", String.class));
						object.setCity((t.get("City", String.class)));
						object.setPincode(t.get("Pincode", String.class));
						object.setTel_no(t.get("Tel_no", BigDecimal.class).setScale(0).toString());
						object.setEmail(t.get("Email", String.class));
						object.setPan_no(t.get("Pan_no", String.class));
						object.setHcp_no(t.get("Hcp_no", String.class));
						list.add(object);
					}
					System.out.println("get Address and contact" + list.size());
					if (!list.isEmpty() && list.size() > 0)
						return list;
				}
				
			} else if (custtype.equalsIgnoreCase("WHL")) {
//				sb.append(" SELECT CM.CUST_ID,CM.CUST_NAME,CM.ADDR1,CM.ADDR2,CM.ADDR3,CM.ADDR4,CM.DESTINATION CITY,");
//				sb.append(" ' ' PINCODE,CM.TEL_NO,CM.EMAIL,CM.PAN_NO,CM.ERP_CUST_CD HCP_NO,CA.DIV_ID");
//				sb.append(" FROM SFA_CUSTOMER_MASTER CM , SFA_CUSTOMER_ALLOCATION CA , SFA_FIELD_MASTER FM");
//				sb.append(" WHERE ");
//				if(!seids.contains(0l)) {
//					sb.append(" FM.FS_ID in(:fsId) AND ");
//				}
//				sb.append(" CM.CUST_ID = CA.CUST_ID AND CA.SALES_REP_ID = FM.FS_ID ");
//				sb.append(" AND CA.DIV_ID = FM.DIV_ID AND CA.CURR_STATUS = 'A'");
//				sb.append(" ORDER BY CUST_NAME");
				
				sb.append(" SELECT CM.CUST_ID,CM.CUST_NAME,CM.ADDR1,CM.ADDR2,CM.ADDR3,CM.ADDR4,CM.DESTINATION CITY,");
				sb.append(" ' ' PINCODE,CM.TEL_NO,CM.EMAIL,CM.PAN_NO,CM.ERP_CUST_CD HCP_NO,FM.FS_ID");
				sb.append(" FROM SFA_CUSTOMER_MASTER CM , ( SELECT SALES_REP_ID,ERP_PARTY_CD,MAX(CUST_ID) CUST_ID FROM SFA_CUSTOMER_ALLOCATION");
				sb.append(" WHERE CURR_STATUS = 'A' GROUP BY SALES_REP_ID,ERP_PARTY_CD ) CA , SFA_FIELD_MASTER FM");
				sb.append(" WHERE");
				if(!seids.contains(0l)) {
					sb.append(" FM.FS_ID in(:fsId) AND");
				}
				sb.append(" CM.CUST_ID = CA.CUST_ID AND CA.SALES_REP_ID = FM.FS_ID  ");
				sb.append(" ORDER BY HCP_NO,CUST_NAME");
				
		//		System.out.println("fsid : "+seids);
				Query query = entityManager.createNativeQuery(sb.toString(), Tuple.class);
				if(!seids.contains(0l)) {
					query.setParameter("fsId",seids);
				}
				
				List<Tuple> tuples = query.getResultList();
				if (tuples != null && !tuples.isEmpty()) {
					list = new ArrayList<>();
					Sfa_Retail_Consumer object = null;
					for (Tuple t : tuples) {
						object = new Sfa_Retail_Consumer();
						object.setCust_id(Long.valueOf(t.get("Cust_id", BigDecimal.class).toString()));
						object.setCust_name(t.get("Cust_name", String.class));
						object.setAddr1(t.get("Addr1", String.class));
						object.setAddr2(t.get("Addr2", String.class));
						object.setAddr3(t.get("Addr3", String.class));
						object.setAddr4(t.get("Addr4", String.class));
						object.setCity((t.get("City", String.class)));
						object.setPincode(t.get("Pincode", String.class));
						object.setTel_no(t.get("Tel_no", String.class));
						object.setEmail(t.get("Email", String.class));
						object.setPan_no(t.get("Pan_no", String.class));
						object.setHcp_no(t.get("Hcp_no", String.class));
						list.add(object);
					}
					System.out.println("get Address and contact" + list.size());
					if (!list.isEmpty() && list.size() > 0)
						return list;
				}
			}


		} finally {

		}
		return list;
	}

	@Override
	public List<Sfa_Retail_Consumer> getDataForCrmTemplate(String custids, String se, String custtype)
			throws Exception {
		List<Sfa_Retail_Consumer> list = null;
		List<Long> seids = new ArrayList<Long>();
		List<Long> custid = new ArrayList<Long>();
		try {
			
			System.out.println("custids : "+custids);
			System.out.println("se : "+se);
			
			StringBuffer sb = new StringBuffer();
			for(String s: se.split(",")) {
				seids.add(Long.valueOf(s));
			}
			
			for(String s: custids.split(",")) {
				custid.add(Long.valueOf(s));
			}
			
			if (custtype.equalsIgnoreCase("HCP")) {
//				sb.append(" SELECT DH.DOC_ID CUST_ID,(DOC_PREFIX+DH.DOC_FNAME) CUST_NAME,ISNULL(DS.DOC_HADDR1,' ' ) ADDR1,");
//				sb.append(" ISNULL(DS.DOC_HADDR2,' ' ) ADDR2,ISNULL(DS.DOC_HADDR3,' ' ) ADDR3,ISNULL(DS.DOC_HADDR4,' ' ) ADDR4,");
//				sb.append(" ISNULL(DS.DOC_HCITY,' ' ) CITY,ISNULL(DS.DOC_HPINCODE,' ' ) PINCODE,DS.DOC_MOBILE TEL_NO,ISNULL(DS.DOC_EMAIL,' ' ) EMAIL,");
//				sb.append(" ISNULL(DH.PAN_NO,' ' ) PAN_NO,DH.HCP_NO HCP_NO,FS.FS_NAME,gm.LOC_NAME,gm.LOC_ID,FS.FS_ID,FS.DIV_ID");
//				sb.append(" FROM SFA_DOCTOR_HEADER DH inner join SFA_FIELD_MASTER FS ON FS.FS_ID=dH.FSTAFF_ID");
//				sb.append(" inner join SFA_GEOGRAPHICAL_MASTER gm ON gm.LOC_ID=fs.GEOG_LVL1_HQ , SFA_DOCTOR_SUB DS ");
//				sb.append(" WHERE");
//				if(!seids.contains(0l)) {
//					sb.append(" DH.FSTAFF_ID in(:fsId) AND");
//				}
//				sb.append(" DH.DOC_ID = DS.DOC_ID ");
//				
//				if(!custid.contains(0l)) {
//					sb.append(" AND DH.DOC_ID in(:custid)");
//				}
//				sb.append(" ORDER BY  FS.FS_NAME,DH.DOC_FNAME");
				
				sb.append(" SELECT DH.DOC_ID CUST_ID,(DOC_PREFIX+DH.DOC_FNAME) CUST_NAME,ISNULL(DS.DOC_HADDR1,' ' ) ADDR1,");
				sb.append(" ISNULL(DS.DOC_HADDR2,' ' ) ADDR2,ISNULL(DS.DOC_HADDR3,' ' ) ADDR3,ISNULL(DS.DOC_HADDR4,' ' ) ADDR4,");
				sb.append(" ISNULL(DS.DOC_HCITY,' ' ) CITY,ISNULL(DS.DOC_HPINCODE,' ' ) PINCODE,DS.DOC_MOBILE TEL_NO,ISNULL(DS.DOC_EMAIL,' ' ) EMAIL,");
				sb.append(" ISNULL(DH.PAN_NO,' ' ) PAN_NO,DH.HCP_NO HCP_NO,FS.FS_NAME,gm.LOC_NAME,gm.LOC_ID,FS.FS_ID,FS.DIV_ID");
				sb.append(" FROM ( SELECT SD.HCP_NO,MAX(SD.DOC_ID) DOC_ID  FROM SFA_DOCTOR_HEADER SD GROUP BY SD.HCP_NO ) SD ,");
				sb.append(" SFA_DOCTOR_HEADER DH inner join SFA_FIELD_MASTER FS ON FS.FS_ID=dH.FSTAFF_ID");
				sb.append(" inner join SFA_GEOGRAPHICAL_MASTER gm ON gm.LOC_ID=fs.GEOG_LVL1_HQ , SFA_DOCTOR_SUB DS  ");
				sb.append(" WHERE SD.DOC_ID = DH.DOC_ID AND");
				if(!seids.contains(0l)) {
					sb.append(" DH.FSTAFF_ID in(:fsId) AND");
				}
				sb.append(" DH.DOC_ID = DS.DOC_ID  ");
				
				if(!seids.contains(0l)) {
					sb.append(" AND DH.DOC_ID in(:custid)");
				}
				sb.append(" ORDER BY  FS.FS_NAME,DH.DOC_FNAME ");

				Query query = entityManager.createNativeQuery(sb.toString(), Tuple.class);
				if(!seids.contains(0l)) {
					query.setParameter("fsId", seids);
				}
				if(!custid.contains(0l)) {
					query.setParameter("custid", custid);
				}
				List<Tuple> tuples = query.getResultList();
				
				if (tuples != null && !tuples.isEmpty()) {
					list = new ArrayList<>();
					Sfa_Retail_Consumer object = null;
					for (Tuple t : tuples) {
						object = new Sfa_Retail_Consumer();
						object.setCust_id(Long.valueOf(t.get("Cust_id", BigDecimal.class).toString()));
						object.setCust_name(t.get("Cust_name", String.class));
						object.setAddr1(t.get("Addr1", String.class));
						object.setAddr2(t.get("Addr2", String.class));
						object.setAddr3(t.get("Addr3", String.class));
						object.setAddr4(t.get("Addr4", String.class));
						object.setCity((t.get("City", String.class)));
						object.setPincode(t.get("Pincode", String.class));
						object.setTel_no(t.get("Tel_no", String.class));
						object.setEmail(t.get("Email", String.class));
						object.setPan_no(t.get("Pan_no", String.class));
						object.setHcp_no(t.get("Hcp_no", String.class));
						object.setFs_name(t.get("FS_NAME", String.class));
						object.setLocname(t.get("LOC_NAME", String.class));
						object.setLocid(t.get("LOC_ID", BigDecimal.class).longValue());
						object.setFsid(t.get("FS_ID", BigDecimal.class).longValue());
						object.setDivid(t.get("DIV_ID", BigDecimal.class).longValue());
						list.add(object);
					}
					System.out.println("get Address and contact" + list.size());
					if (!list.isEmpty() && list.size() > 0)
						return list;
				}
				
			} else if (custtype.equalsIgnoreCase("RTL")) {
//				sb.append(" SELECT RC.SR_NO CUST_ID,CONCAT(RC.CONSUMER_NAME,'-', RC.CONSUMER_CD) CUST_NAME,RC.ADDRESS ADDR1,' ' ADDR2,' ' ADDR3,' ' ADDR4,");
//				sb.append(" ISNULL(RC.DESTINATION,' ' ) CITY,' ' PINCODE,ISNULL(RC.CONTACT_NO ,0 ) TEL_NO,RC.EMAIL_ID EMAIL,");
//				sb.append(" RC.PAN_NO,HCP_NO HCP_NO,FM.FS_NAME,gm.LOC_NAME,gm.LOC_ID,FM.FS_ID,FM.DIV_ID FROM SFA_RETAIL_CONSUMER RC");
//				sb.append(" inner join SFA_FIELD_MASTER FM on FM.FS_ID = RC.FS_ID");
//				sb.append(" inner join SFA_GEOGRAPHICAL_MASTER gm on gm.LOC_ID = FM.GEOG_LVL1_HQ");
//				
//				sb.append("	WHERE ");
//				if(!seids.contains(0l)) {
//					sb.append(" RC.FS_ID in(:fsId) ");
//				}
//				
//				if(!custid.contains(0l)) {
//					sb.append(" AND RC.SR_NO in(:custid)");
//				}
//				sb.append(" ORDER BY FM.FS_NAME,RC.CONSUMER_NAME");
				
				sb.append(" SELECT RC.SR_NO CUST_ID,CONCAT(RC.CONSUMER_NAME,'-', RC.CONSUMER_CD) CUST_NAME,RC.ADDRESS ADDR1,' ' ADDR2,' ' ADDR3,' ' ADDR4,");
				sb.append(" ISNULL(RC.DESTINATION,' ' ) CITY,' ' PINCODE,ISNULL(RC.CONTACT_NO ,0 ) TEL_NO,RC.EMAIL_ID EMAIL,");
				sb.append(" RC.PAN_NO,RC.HCP_NO HCP_NO,FM.FS_NAME,gm.LOC_NAME,gm.LOC_ID,FM.FS_ID,FM.DIV_ID");
				sb.append(" FROM ( SELECT SR.HCP_NO,MAX( SR.SR_NO ) SR_NO FROM SFA_RETAIL_CONSUMER SR GROUP BY  SR.HCP_NO ) SR");
				sb.append(" INNER JOIN  SFA_RETAIL_CONSUMER RC  ON RC.SR_NO = SR.SR_NO");
				sb.append(" inner join SFA_FIELD_MASTER FM on FM.FS_ID = RC.FS_ID");
				sb.append(" inner join SFA_GEOGRAPHICAL_MASTER gm on gm.LOC_ID = FM.GEOG_LVL1_HQ");
				if(!seids.contains(0l)) {
					sb.append(" RC.FS_ID in(:fsId) ");
				}
				if(!custid.contains(0l)) {
					sb.append(" AND RC.SR_NO in(:custid)");
				}
				sb.append(" ORDER BY FM.FS_NAME,RC.CONSUMER_NAME ");

				Query query = entityManager.createNativeQuery(sb.toString(), Tuple.class);
				if(!seids.contains(0l)) {
					query.setParameter("fsId", seids);
				}
				if(!custid.contains(0l)) {
					query.setParameter("custid", custid);
				}
				List<Tuple> tuples = query.getResultList();
				
				if (tuples != null && !tuples.isEmpty()) {
					list = new ArrayList<>();
					Sfa_Retail_Consumer object = null;
					for (Tuple t : tuples) {
						object = new Sfa_Retail_Consumer();
						object.setCust_id(Long.valueOf(t.get("Cust_id", BigDecimal.class).toString()));
						object.setCust_name(t.get("Cust_name", String.class));
						object.setAddr1(t.get("Addr1", String.class));
						object.setAddr2(t.get("Addr2", String.class));
						object.setAddr3(t.get("Addr3", String.class));
						object.setAddr4(t.get("Addr4", String.class));
						object.setCity((t.get("City", String.class)));
						object.setPincode(t.get("Pincode", String.class));
						object.setTel_no(t.get("Tel_no", BigDecimal.class).setScale(0).toString());
						object.setEmail(t.get("Email", String.class));
						object.setPan_no(t.get("Pan_no", String.class));
						object.setHcp_no(t.get("Hcp_no", String.class));
						object.setFs_name(t.get("FS_NAME", String.class));
						object.setLocname(t.get("LOC_NAME", String.class));
						object.setLocid(t.get("LOC_ID", BigDecimal.class).longValue());
						object.setFsid(t.get("FS_ID", BigDecimal.class).longValue());
						object.setDivid(t.get("DIV_ID", BigDecimal.class).longValue());
						list.add(object);
					}
					System.out.println("get Address and contact" + list.size());
					if (!list.isEmpty() && list.size() > 0)
						return list;
				}
				
			} else if (custtype.equalsIgnoreCase("WHL")) {
//				sb.append(" SELECT CM.CUST_ID,CM.CUST_NAME,CM.ADDR1,CM.ADDR2,CM.ADDR3,CM.ADDR4,CM.DESTINATION CITY,");
//				sb.append(" ' ' PINCODE,CM.TEL_NO,CM.EMAIL,CM.PAN_NO,CM.ERP_CUST_CD HCP_NO,CA.DIV_ID,FM.FS_NAME,gm.LOC_NAME,gm.LOC_ID,FM.FS_ID,FM.DIV_ID as FsDivId");
//				sb.append(" FROM SFA_CUSTOMER_MASTER CM , SFA_CUSTOMER_ALLOCATION CA , SFA_FIELD_MASTER FM");
//				sb.append(" inner join SFA_GEOGRAPHICAL_MASTER gm on gm.LOC_ID=FM.GEOG_LVL1_HQ");
//				sb.append(" WHERE ");
//				if(!seids.contains(0l)) {
//					sb.append(" FM.FS_ID in(:fsId) AND");
//				}
//				sb.append(" CM.CUST_ID = CA.CUST_ID");
//				
//				if(!custid.contains(0l)) {
//					sb.append(" AND CM.CUST_ID in(:custid)");
//				}
//				sb.append(" AND CA.SALES_REP_ID = FM.FS_ID ");
//				sb.append(" AND CA.DIV_ID = FM.DIV_ID AND CA.CURR_STATUS = 'A'");
//				sb.append(" ORDER BY CUST_NAME");
				
				sb.append(" SELECT CM.CUST_ID,CM.CUST_NAME,CM.ADDR1,CM.ADDR2,CM.ADDR3,CM.ADDR4,CM.DESTINATION CITY,");
				sb.append(" ' ' PINCODE,CM.TEL_NO,CM.EMAIL,CM.PAN_NO,CM.ERP_CUST_CD HCP_NO,FM.DIV_ID,FM.FS_NAME,gm.LOC_NAME,gm.LOC_ID,FM.FS_ID,FM.DIV_ID as FsDivId");
				sb.append(" FROM SFA_CUSTOMER_MASTER CM , ( SELECT SALES_REP_ID,ERP_PARTY_CD,MAX(CUST_ID) CUST_ID FROM SFA_CUSTOMER_ALLOCATION");
				sb.append(" WHERE CURR_STATUS = 'A' GROUP BY SALES_REP_ID,ERP_PARTY_CD ) CA , SFA_FIELD_MASTER FM");
				sb.append(" inner join SFA_GEOGRAPHICAL_MASTER gm on gm.LOC_ID=FM.GEOG_LVL1_HQ");
				sb.append(" WHERE");
				if(!seids.contains(0l)) {
					sb.append(" FM.FS_ID in(:fsId) AND");
				}
				sb.append(" CM.CUST_ID = CA.CUST_ID");
				if(!custid.contains(0l)) {
					sb.append(" AND CM.CUST_ID in(:custid)");
				}
				sb.append(" AND CA.SALES_REP_ID = FM.FS_ID");
				sb.append(" ORDER BY CUST_NAME");
			
				Query query = entityManager.createNativeQuery(sb.toString(), Tuple.class);
				if(!seids.contains(0l)) {
					query.setParameter("fsId",seids);
				}
				if(!custid.contains(0l)) {
					query.setParameter("custid", custid);
				}
				List<Tuple> tuples = query.getResultList();
				
				if (tuples != null && !tuples.isEmpty()) {
					list = new ArrayList<>();
					Sfa_Retail_Consumer object = null;
					for (Tuple t : tuples) {
						object = new Sfa_Retail_Consumer();
						object.setCust_id(Long.valueOf(t.get("Cust_id", BigDecimal.class).toString()));
						object.setCust_name(t.get("Cust_name", String.class));
						object.setAddr1(t.get("Addr1", String.class));
						object.setAddr2(t.get("Addr2", String.class));
						object.setAddr3(t.get("Addr3", String.class));
						object.setAddr4(t.get("Addr4", String.class));
						object.setCity((t.get("City", String.class)));
						object.setPincode(t.get("Pincode", String.class));
						object.setTel_no(t.get("Tel_no", String.class));
						object.setEmail(t.get("Email", String.class));
						object.setPan_no(t.get("Pan_no", String.class));
						object.setHcp_no(t.get("Hcp_no", String.class));
						object.setFs_name(t.get("FS_NAME", String.class));
						object.setLocname(t.get("LOC_NAME", String.class));
						object.setLocid(t.get("LOC_ID", BigDecimal.class).longValue());
						object.setFsid(t.get("FS_ID", BigDecimal.class).longValue());
						object.setDivid(t.get("DIV_ID", BigDecimal.class).longValue());
						list.add(object);
					}
					System.out.println("get Address and contact" + list.size());
					if (!list.isEmpty() && list.size() > 0)
						return list;
				}
			}


		} finally {

		}
		return list;
	}

	@Override
	public Long getHqId(String hqname) throws Exception {
		// TODO Auto-generated method stub
		BigDecimal hqid=null;
		try {
			System.out.println("locName : "+hqname);
			StringBuffer sb = new StringBuffer();
			sb.append("select Max(loc_id) from SFA_GEOGRAPHICAL_MASTER where UPPER(LOC_NAME)=:locname");
			Query query = entityManager.createNativeQuery(sb.toString());
			query.setParameter("locname",hqname.toUpperCase().trim());
			hqid= (BigDecimal) query.getSingleResult();
		}
		catch (Exception e) {
			// TODO: handle exception
			throw e;
		}
		return hqid.longValue();
	}

	@Override
	public Long getFsId(String fsname) throws Exception {
		// TODO Auto-generated method stub
		BigDecimal fsid=null;
		try {
			StringBuffer sb = new StringBuffer();
			sb.append("select FS_ID from SFA_FIELD_MASTER where FS_NAME=:fsname");
			Query query = entityManager.createNativeQuery(sb.toString());
			query.setParameter("fsname",fsname);
			fsid= (BigDecimal) query.getSingleResult();
		}
		catch (Exception e) {
			// TODO: handle exception
			throw e;
		}
		return fsid.longValue();
	}

	@Override
	public Long getcustIdforHcpType(String hcpType, String hcpno, Long fsid) throws Exception {
		// TODO Auto-generated method stub
		BigDecimal custid=null;
		try{
			System.out.println("hcpno : "+hcpno);
			if (hcpType.equalsIgnoreCase("HCP")) {
				StringBuffer sb = new StringBuffer();
				sb.append("select Max(DOC_ID) from SFA_DOCTOR_HEADER where HCP_NO= :docnm");
				sb.append(" and FSTAFF_ID = :fsId");
				Query query = entityManager.createNativeQuery(sb.toString());
				query.setParameter("docnm",hcpno);
				query.setParameter("fsId",fsid);
				custid= (BigDecimal) query.getSingleResult();
			}
			if(hcpType.equalsIgnoreCase("WHL")) {
				StringBuffer sb = new StringBuffer();
				sb.append("select Max(CUST_ID) from SFA_CUSTOMER_MASTER where ERP_CUST_CD = :docnm");
			//	sb.append(" and FSTAFF_ID = :fsId");
				Query query = entityManager.createNativeQuery(sb.toString());
				query.setParameter("docnm",hcpno);
			//	query.setParameter("fsId",fsid);
				custid= (BigDecimal) query.getSingleResult();
			}
			if(hcpType.equalsIgnoreCase("RTL")) {
				StringBuffer sb = new StringBuffer();
				sb.append("select Max(SR_NO) from SFA_RETAIL_CONSUMER where HCP_NO= :docnm");
				sb.append(" and FS_ID = :fsId AND STATUS='A'");
				Query query = entityManager.createNativeQuery(sb.toString());
				query.setParameter("docnm",hcpno);
				query.setParameter("fsId",fsid);
				custid= (BigDecimal) query.getSingleResult();
			}
		}
		catch (Exception e) {
			// TODO: handle exception
			throw e;
		}
		return custid.longValue();
	}

	@Override
	public Crm_GenHd getCrmGenhd(String filename) throws Exception {
		// TODO Auto-generated method stub
		Crm_GenHd crmgenhd=null;
		try {
			CriteriaBuilder builder = entityManager.getCriteriaBuilder();
			CriteriaQuery<Crm_GenHd> criteriaQuery = builder.createQuery(Crm_GenHd.class);
			Root<Crm_GenHd> root = criteriaQuery.from(Crm_GenHd.class);
			criteriaQuery.select(root);
			criteriaQuery.where(builder.equal(root.get("crm_doc_no"), filename));
			crmgenhd = entityManager.createQuery(criteriaQuery).getSingleResult();
		}
		catch (Exception e) {
			// TODO: handle exception
			throw e;
		}
		return crmgenhd;
	}

	@Override
	public List<Crm_GenDtl> getCrmGenDt(Long crmhdid) throws Exception {
		// TODO Auto-generated method stub
		List<Crm_GenDtl> dtllist=new ArrayList<Crm_GenDtl>();
		try {
			CriteriaBuilder builder = entityManager.getCriteriaBuilder();
			CriteriaQuery<Crm_GenDtl> criteriaQuery = builder.createQuery(Crm_GenDtl.class);
			Root<Crm_GenDtl> root = criteriaQuery.from(Crm_GenDtl.class);
			criteriaQuery.select(root);
			criteriaQuery.where(builder.equal(root.get("crm_gen_id"), crmhdid));
			dtllist = entityManager.createQuery(criteriaQuery).getResultList();
		}
		catch (Exception e) {
			// TODO: handle exception
			throw e;
		}
		return dtllist;
	}

	@Override
	public Crm_GenDtl getCrmGenDtl(Long crmhdid,String custtype,Long hq,Long fsid,Long divid,Long custid) throws Exception {
		// TODO Auto-generated method stub
		Crm_GenDtl dtl =null;
		try {
			CriteriaBuilder builder = entityManager.getCriteriaBuilder();
			CriteriaQuery<Crm_GenDtl> criteriaQuery = builder.createQuery(Crm_GenDtl.class);
			Root<Crm_GenDtl> root = criteriaQuery.from(Crm_GenDtl.class);
			criteriaQuery.select(root);
			criteriaQuery.where(builder.equal(root.get("crm_gen_id"), crmhdid),
					builder.equal(root.get("crm_cust_type"), custtype),
					builder.equal(root.get("crm_hq_id"), hq),
					builder.equal(root.get("crm_fs_id"), fsid),
					builder.equal(root.get("crm_div_id"), divid),
					builder.equal(root.get("crm_cust_id"), custid));
			dtl = entityManager.createQuery(criteriaQuery).getSingleResult();
		}
		catch (Exception e) {
			// TODO: handle exception
			throw e;
		}
		return dtl;
	}
	
	@Override
	public List<Crm_GenHd> getCrmHdrByUserId(String userId) throws Exception {
		List<Crm_GenHd> returnList = new ArrayList<Crm_GenHd>();
		try {
			StringBuffer sb = new StringBuffer();
			sb.append(" select DISTINCT(TDS.CRM_GEN_ID) GEN_ID,GEN.CRM_DOC_NO CRM_DOC_NO from TDS_CRM_EXPENSE TDS,CRM_GEN_HD GEN");
			sb.append(" where TDS.CRM_INS_BY = :userId and TDS.CRM_CONFIRM = 'N'");
			sb.append(" and TDS.CRM_GEN_ID IS NOT NULL and TDS.CRM_GEN_ID = GEN.CRM_GEN_ID");
			Query query = entityManager.createNativeQuery(sb.toString(), Tuple.class);
			query.setParameter("userId",userId);
			List<Tuple> tuples = query.getResultList();
			
			if(tuples != null && tuples.size()>0) {
				for(Tuple t:tuples) {
					Crm_GenHd newGen = new Crm_GenHd();
					newGen.setCrm_gen_id(Long.valueOf(t.get("GEN_ID", Integer.class)));
					newGen.setCrm_doc_no(t.get("CRM_DOC_NO", String.class));
					returnList.add(newGen);
				}
			}
		}
		catch(Exception e) {
			throw e;
		}
		return returnList;
	}

	@Override
	public List<Tds_Crm_Expense> getTdsExpsByCrmGenId(Long crmGenId) throws Exception {
		List<Tds_Crm_Expense> returnList = null;
		try {
			CriteriaBuilder builder = entityManager.getCriteriaBuilder();
			CriteriaQuery<Tds_Crm_Expense> criteriaQuery = builder.createQuery(Tds_Crm_Expense.class);
			Root<Tds_Crm_Expense> root = criteriaQuery.from(Tds_Crm_Expense.class);
			criteriaQuery.select(root).where(builder.equal(root.get("crm_confirm"), "N"),
					builder.equal(root.get("crm_gen_id"), crmGenId));
			returnList = entityManager.createQuery(criteriaQuery).getResultList();
			System.out.println(returnList.size());
		}
		catch(Exception e) {
			throw e;
		}
		return returnList;
	}


	@Override
	public Tds_Crm_Expense getTdsCrmExpenseGenDtlByDtlAndHdrId(Long crGenHdrId, Long crmGenDtlId)
			throws Exception {
		Tds_Crm_Expense tds_crm_exps=null;
		try {
			CriteriaBuilder builder = entityManager.getCriteriaBuilder();
			CriteriaQuery<Tds_Crm_Expense> criteriaQuery = builder.createQuery(Tds_Crm_Expense.class);
			Root<Tds_Crm_Expense> root = criteriaQuery.from(Tds_Crm_Expense.class);
			criteriaQuery.select(root);
			criteriaQuery.where(builder.equal(root.get("crm_gen_dtl_id"), crmGenDtlId),
					builder.equal(root.get("crm_gen_id"), crGenHdrId));
			tds_crm_exps = entityManager.createQuery(criteriaQuery).getSingleResult();
		}
		catch (Exception e) {
			// TODO: handle exception
			throw e;
		}
		return tds_crm_exps;
	}

	@Override
	public String getMaxCrmSerialNumber(String checker) throws Exception {
		// TODO Auto-generated method stub
		try {
			StringBuffer sb = new StringBuffer();
			sb.append(" select CRM_DOC_NO from CRM_GEN_HD");
			sb.append(" where CRM_DOC_NO like :doc_no");
			sb.append(" order by CRM_DOC_NO desc");
			
			Query query = entityManager.createNativeQuery(sb.toString());
			query.setParameter("doc_no",checker+"%");
			List<String> tuples = query.getResultList();
			
			if(tuples!=null && tuples.size()>0) {
				return tuples.get(0);
			}

		}
		catch (Exception e) {
			// TODO: handle exception
			throw e;
		}
		return null;
	}

	@Override
	public List<Tds_Crm_Expense> getTdsCrmExpenseByGenHdrId(Long crGenHdrId) throws Exception {
		List<Tds_Crm_Expense> tds_crm_exps=null;
		try {
			CriteriaBuilder builder = entityManager.getCriteriaBuilder();
			CriteriaQuery<Tds_Crm_Expense> criteriaQuery = builder.createQuery(Tds_Crm_Expense.class);
			Root<Tds_Crm_Expense> root = criteriaQuery.from(Tds_Crm_Expense.class);
			criteriaQuery.select(root);
			criteriaQuery.where(
					builder.equal(root.get("crm_gen_id"), crGenHdrId));
			tds_crm_exps = entityManager.createQuery(criteriaQuery).getResultList();
		}
		catch (Exception e) {
			// TODO: handle exception
			throw e;
		}
		return tds_crm_exps;
	}

	@Override
	public void truncateCrmTemplate() throws Exception {
		// TODO Auto-generated method stub
		try {
			StringBuffer sb = new StringBuffer();
			sb.append("Truncate Table TDS_CRM_UPLOAD");
			Query query = entityManager.createNativeQuery(sb.toString());
			query.executeUpdate();
		}
		catch (Exception e) {
			// TODO: handle exception
			throw e;
		}
	}

	@Override
	public void setArchiveTable() throws Exception {
		// TODO Auto-generated method stub
		try {
			StringBuffer sb = new StringBuffer();
			sb.append("insert into TDS_CRM_UPLOAD_arc select * from  TDS_CRM_UPLOAD" );
			Query query = entityManager.createNativeQuery(sb.toString());
			query.executeUpdate();

		}
		catch (Exception e) {
			// TODO: handle exception
			throw e;
		}
	}

	@Override
	public boolean checkuploadedstatus() throws Exception {
		// TODO Auto-generated method stub
		List<Tds_Crm_Expense> list = new ArrayList<Tds_Crm_Expense>();
		try {
			StringBuffer sb = new StringBuffer();
			sb.append("from Tds_Crm_Upload where uploaded = 'N'" );
			Query query = entityManager.createQuery(sb.toString());
			list = query.getResultList();

			if(list!=null && list.isEmpty()) {
				return list.isEmpty();
			}
			else {
				return false;
			}
			
		}
		catch (Exception e) {
			// TODO: handle exception
			throw e;
		}
	}

	@Override
	public Long checkIfCustomerExists(String custCode) throws Exception {
		Long count = null;
		Integer tempVar = null;
		try{
			StringBuffer sb = new StringBuffer();
			sb.append("select count(CUST_CD) from SFA_CUSTOMER_MASTER where ERP_CUST_CD = :custCd");
			Query query = entityManager.createNativeQuery(sb.toString());
			query.setParameter("custCd", custCode.trim());
			tempVar = (Integer) query.getSingleResult();
			count = Long.valueOf(tempVar.toString());
		}
		catch(Exception e){
			throw e;
		}
		return count;
	}

	@Override
	public Long checkIfDoctorExists(String custCode) throws Exception {
		Long count = null;
		String tempVar = null;
		try{
			StringBuffer sb = new StringBuffer();
			sb.append("select HCP_NO from SFA_DOCTOR_HEADER where HCP_NO =:custCd and STATUS = 'A'");
			Query query = entityManager.createNativeQuery(sb.toString());
			query.setParameter("custCd", custCode.trim());
			tempVar =  (String) query.getSingleResult();
			count = tempVar == null ? 0L : 1L;
		}
		catch(Exception e){
			throw e;
		}
		return count;
	}

	@Override
	public Long checkIfRetailerExists(String custCode) throws Exception {
		Long count = null;
		Integer tempVar = null;
		try{
			StringBuffer sb = new StringBuffer();
			sb.append("select COUNT(HCP_NO) from SFA_RETAIL_CONSUMER where HCP_NO = :custCd and STATUS = 'A'");
			Query query = entityManager.createNativeQuery(sb.toString());
			query.setParameter("custCd", custCode.trim());
			tempVar = (Integer) query.getSingleResult();
			count = Long.valueOf(tempVar.toString());
		}
		catch(Exception e){
			throw e;
		}
		return count;
	}

	@Override
	public void callcrmExpenseProcedure(String custtype, String subcomp, String finyear, Date crmdate, String userid)
			throws Exception {
		// TODO Auto-generated method stub
		try {
			StringBuffer sb = new StringBuffer();
			sb.append("exec TDS_CRM_GENERATE :custtype,:subcomp,:finyear,:crmdate,:userid");
			Query query = entityManager.createNativeQuery(sb.toString());
			query.setParameter("custtype", custtype.trim());
			query.setParameter("subcomp", subcomp.trim());
			query.setParameter("finyear", finyear.trim());
			query.setParameter("crmdate", crmdate);
			query.setParameter("userid", userid.trim());
			query.executeUpdate();
		}
		catch (Exception e) {
			// TODO: handle exception
			throw e;
		}
	}

	@Override
	public void deleteTdsCrmExpenseOnFileName(String filename) throws Exception {
		// TODO Auto-generated method stub
		try {
			StringBuffer sb = new StringBuffer();
			sb.append("delete TDS_CRM_EXPENSE where FILENAME = :filname");
			Query query = entityManager.createNativeQuery(sb.toString());
			query.setParameter("filname", filename);
			query.executeUpdate();
		}
		catch (Exception e) {
			// TODO: handle exception
			throw e;
		}
	}

	@Override
	public List<Tds_Crm_Upload> checkForUnuploadedRecords(String filename) throws Exception {
		// TODO Auto-generated method stub
		List<Tds_Crm_Upload> list = new ArrayList<Tds_Crm_Upload>();
		try {
			CriteriaBuilder builder = entityManager.getCriteriaBuilder();
			CriteriaQuery<Tds_Crm_Upload> criteriaQuery = builder.createQuery(Tds_Crm_Upload.class);
			Root<Tds_Crm_Upload> root = criteriaQuery.from(Tds_Crm_Upload.class);
			criteriaQuery.select(root);
			criteriaQuery.where(
					builder.equal(root.get("filename"), filename),
					builder.equal(root.get("uploaded"), "N"));
			list = entityManager.createQuery(criteriaQuery).getResultList();
			
		}
		catch (Exception e) {
			// TODO: handle exception
			throw e;
		}
		return list;
	}

	@Override
	public boolean checkfilename(String filename) throws Exception {
		// TODO Auto-generated method stub
		try {
			StringBuffer sb = new StringBuffer();
			sb.append(" select FILENAME from TDS_CRM_UPLOAD_ARC");
			sb.append(" where FILENAME = :filename");
			
			Query query = entityManager.createNativeQuery(sb.toString());
			query.setParameter("filename",filename);
			List<String> tuples = query.getResultList();
			
			if(tuples!=null && tuples.size()>0) {
				return true;
			}
		}
		catch (Exception e) {
			// TODO: handle exception
			throw e;
		}
		return false;
	}


}
