package com.excel.repository;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.StoredProcedureQuery;
import javax.persistence.Tuple;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Path;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import javax.persistence.criteria.Subquery;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.excel.bean.AllocationBean;
import com.excel.bean.FieldStaffBean;
import com.excel.model.Alloc_gen_ent;
import com.excel.model.Alloc_gen_ent_;
import com.excel.model.CustomerMaster;
import com.excel.model.CustomerMaster_;
import com.excel.model.DivField;
import com.excel.model.FieldStaff;
import com.excel.model.FieldStaffList;
import com.excel.model.FieldStaffListNew;
import com.excel.model.FieldStaff_;
import com.excel.model.Location;
import com.excel.model.V_Fieldstaff;
import com.excel.model.V_Fieldstaff_;
import com.excel.model.V_G_FieldStaff;
import com.excel.utility.CodifyErrors;
import com.excel.utility.MedicoConstants;

@Repository
public class FieldStaffRepositoryImpl implements FieldStaffRepository, MedicoConstants {

	@Autowired
	private EntityManagerFactory emf;
	@PersistenceContext
	private EntityManager entityManager;

	@SuppressWarnings("unchecked")

	@Override
	public FieldStaff getObjectByStaffId(Long staff_id) throws Exception {
		return this.entityManager.find(FieldStaff.class, staff_id);
	}

	@Override
	public List<FieldStaffList> getFieldStaffList(String emp_id, Long div_id) throws Exception {
		EntityManager em = null;
		List<FieldStaffList> list = null;
		try {
			em = emf.createEntityManager();
			StoredProcedureQuery query = em.createNamedStoredProcedureQuery("callFieldStaffList");
			query.setParameter("pvemp_id", emp_id);
			query.setParameter("pidiv_id", div_id);
			list = query.getResultList();
		} finally {
			if (em != null) {
				em.close();
			}
		}
		return list;
	}

	@Override
	public V_Fieldstaff getDivListByEmpId(String fstaff_samp_disp_ind, Long fstaff_id) throws Exception {
		V_Fieldstaff result = null;
		EntityManager em = null;
		try {
			em = emf.createEntityManager();
			CriteriaBuilder builder = em.getCriteriaBuilder();
			CriteriaQuery<V_Fieldstaff> criteriaQuery = builder.createQuery(V_Fieldstaff.class);
			Root<V_Fieldstaff> root = criteriaQuery.from(V_Fieldstaff.class);
			criteriaQuery.select(root)
					.where(builder.and(
							builder.equal(root.get(V_Fieldstaff_.fstaff_samp_disp_ind), fstaff_samp_disp_ind),
							builder.equal(root.get(V_Fieldstaff_.fstaff_id), fstaff_id)));
			result = em.createQuery(criteriaQuery).getResultList().get(0);
			return result;
		} finally {
			if (em != null) {
				em.close();
			}
		}
	}

	@Override
	public List<FieldStaff> getFieldStaffsByEmpIdAndHasResigned(Long divId, boolean hasResigned, String companyCode)
			throws Exception {
		EntityManager em = null;
		List<FieldStaff> list = null;
		try {
			em = emf.createEntityManager();
			CriteriaBuilder builder = em.getCriteriaBuilder();
			CriteriaQuery<FieldStaff> criteriaQuery = builder.createQuery(FieldStaff.class);
			Root<FieldStaff> root = criteriaQuery.from(FieldStaff.class);
			if (hasResigned) {
				criteriaQuery.select(root)
						.where(builder.and(builder.equal(root.get(FieldStaff_.fs_div_id), divId),
								builder.equal(root.get(FieldStaff_.company), companyCode),
								builder.isNotNull(root.get(FieldStaff_.leaving_date))))
						.orderBy(builder.asc(root.get(FieldStaff_.fstaff_display_name)));
			} else {
				criteriaQuery.select(root)
						.where(builder.and(builder.equal(root.get(FieldStaff_.fs_div_id), divId),
								builder.equal(root.get(FieldStaff_.company), companyCode),
								builder.isNull(root.get(FieldStaff_.leaving_date))))
						.orderBy(builder.asc(root.get(FieldStaff_.fstaff_display_name)));
			}

			list = em.createQuery(criteriaQuery).getResultList();
		} finally {
			if (em != null) {
				em.close();
			}
		}
		if (list != null && !list.isEmpty()) {
			return list;
		}
		return null;
	}

	@Override
	public Long getDivIdByFsId(Long fstaff_id) throws Exception {
		EntityManager em = null;
		Long divId = null;
		try {
			em = emf.createEntityManager();
			CriteriaBuilder builder = em.getCriteriaBuilder();
			CriteriaQuery<Long> criteriaQuery = builder.createQuery(Long.class);
			Root<FieldStaff> root = criteriaQuery.from(FieldStaff.class);
			criteriaQuery.select((root.get(FieldStaff_.fs_div_id)))
					.where(builder.and(builder.equal(root.get(FieldStaff_.fstaff_id), fstaff_id)));
			divId = em.createQuery(criteriaQuery).getSingleResult();
		} finally {
			if (em != null) {
				em.close();
			}
		}
		return divId;
	}

	@Override
	public List<Object> getNoOfStaffByStaffwiseTraining(String lvlCode, String divId, String status, String allocMode,
			String cfalocId, String dist, String fstaffType, String alloc_id, String period_code, String alloc_cycle,
			String prodtype, String fin_year, String company, String sub_team_code) {
		EntityManager em = null;
		List<Object> resultList = null;
		Long count = 0L;
		try {
			if (allocMode.equals("0")) {
				Query query = entityManager.createNativeQuery(
						this.getStaffCountQuery(lvlCode, cfalocId, divId, status, dist, allocMode, fstaffType, alloc_id,
								period_code, alloc_cycle, prodtype, fin_year, company, sub_team_code).toString(),
						Tuple.class);
				List<Tuple> tuples = query.getResultList();
				List<Long> countList = query.getResultList();
				if (countList.size() > 0) {
					Number number = countList.get(0);
					count = number.longValue();
					System.out.println(count);
				}

			} else if (allocMode != null) {

				/*
				 * if (alloc_cycle != null) { detachedCriteria = DetachedCriteria
				 * .forClass(Alloc_gen_ent.class);
				 * detachedCriteria.add(Restrictions.eq("fin_year", fin_year));
				 * detachedCriteria.add(Restrictions.eq("alloc_cycle",
				 * Long.parseLong(alloc_cycle)));
				 * detachedCriteria.add(Restrictions.eq("company", company));
				 * detachedCriteria.add(Restrictions.eq("product_type",
				 * prodtype.split("_")[1])); detachedCriteria.add(Restrictions.eq("period_code",
				 * period_code)); detachedCriteria.add(Restrictions.eq("fin_year", fin_year)); }
				 */

				Long cfa_locId = Long.valueOf(cfalocId);
				CriteriaBuilder builder = entityManager.getCriteriaBuilder();
				CriteriaQuery<Object> criteriaQuery = builder.createQuery(Object.class);
				Root<FieldStaff> root = criteriaQuery.from(FieldStaff.class);
				if (dist.equalsIgnoreCase("fstaff_id")) {
					criteriaQuery
							.multiselect(builder.count(root.get(FieldStaff_.fstaff_id)),
									root.get(FieldStaff_.fstaff_training))
							.groupBy(root.get(FieldStaff_.fstaff_training));
//							if(sub_team_code.trim().equals("All")) {
//								criteriaQuery.where(builder.and(builder.equal(root.get(FieldStaff_.fs_div_id), divId),
//										builder.equal(root.get(FieldStaff_.level_code), lvlCode),
//										builder.equal(root.get(FieldStaff_.fstaff_samp_disp_ind), "Y"),
//										builder.not(root.get(FieldStaff_.fs_category).in("E","D")),
//										builder.equal(root.get(FieldStaff_.status), "A"),
//										builder.isNull(root.get(FieldStaff_.leaving_date))));
//							}
//							else {
					criteriaQuery.where(builder.and(builder.equal(root.get(FieldStaff_.fs_div_id), divId),
							builder.equal(root.get(FieldStaff_.level_code), lvlCode),
							builder.equal(root.get(FieldStaff_.fstaff_samp_disp_ind), "Y"),
							builder.not(root.get(FieldStaff_.fs_category).in("E", "D")),
							builder.equal(root.get(FieldStaff_.status), "A"),
							builder.isNull(root.get(FieldStaff_.leaving_date)),
							builder.equal(root.get(FieldStaff_.team_code), sub_team_code)));
//							}

				} else if (dist.equalsIgnoreCase("fstaff_mgr1_id")) {
					criteriaQuery
							.multiselect(builder.count(root.get(FieldStaff_.fstaff_mgr1_id)),
									root.get(FieldStaff_.fstaff_training))
							.groupBy(root.get(FieldStaff_.fstaff_training))
							.where(builder.and(builder.equal(root.get(FieldStaff_.fs_div_id), divId),
									builder.equal(root.get(FieldStaff_.level_code), lvlCode),
									builder.equal(root.get(FieldStaff_.fstaff_samp_disp_ind), "Y"),
									builder.not(root.get(FieldStaff_.fs_category).in("E", "D")),
									builder.equal(root.get(FieldStaff_.status), "A"),
									builder.isNull(root.get(FieldStaff_.leaving_date))));
					// if(!sub_team_code.trim().equalsIgnoreCase("All")) {
					builder.and(builder.equal(root.get(FieldStaff_.team_code), sub_team_code));
					// }
				} else if (dist.equalsIgnoreCase("fstaff_mgr2_id")) {
					criteriaQuery
							.multiselect(builder.count(root.get(FieldStaff_.fstaff_mgr2_id)),
									root.get(FieldStaff_.fstaff_training))
							.groupBy(root.get(FieldStaff_.fstaff_training))
							.where(builder.and(builder.equal(root.get(FieldStaff_.fs_div_id), divId),
									builder.equal(root.get(FieldStaff_.level_code), lvlCode),
									builder.equal(root.get(FieldStaff_.fstaff_samp_disp_ind), "Y"),
									builder.not(root.get(FieldStaff_.fs_category).in("E", "D")),
									builder.equal(root.get(FieldStaff_.status), "A"),
									builder.isNull(root.get(FieldStaff_.leaving_date))));
					// if(!sub_team_code.trim().equalsIgnoreCase("All")) {
					builder.and(builder.equal(root.get(FieldStaff_.team_code), sub_team_code));
					// }
				}
				/*
				 * if (status != null) {
				 * System.out.println("FieldStaffDao.getNoOfStaffByStaffwise() 147"); if
				 * (!status.equalsIgnoreCase("A")) {
				 * System.out.println("FieldStaffDao.getNoOfStaffByStaffwise() 150");
				 * 
				 * builder.equal(root.get(FieldStaff_.fstaff_dom_exp), divId); } } if
				 * (allocMode.equalsIgnoreCase("1")) { if (cfa_locId == 0) { if
				 * (!alloc_id.equals("0")) { detachedCriteria.setProjection(Projections
				 * .distinct(Projections.property("zone_id")));
				 * ctr.add(Subqueries.propertyNotIn("fstaff_zone_id", detachedCriteria)); }
				 * 
				 * } else builder.equal(root.get(FieldStaff_.fstaff_zone_id), divId);
				 * 
				 * } else if (allocMode.equalsIgnoreCase("2")) { if (cfa_locId == 0) { if
				 * (!alloc_id.equals("0")) {
				 * System.out.println("FieldStaffDao.getNoOfStaffByStaffwise()"+ alloc_id);
				 * detachedCriteria.setProjection(Projections .distinct(Projections
				 * .property("state_id"))); ctr.add(Subqueries.propertyNotIn("fstaff_state_id",
				 * detachedCriteria)); } } else
				 * builder.equal(root.get(FieldStaff_.fstaff_state_id), divId); } else if
				 * (allocMode.equalsIgnoreCase("3")) { if (cfa_locId == 0) { if
				 * (!alloc_id.equals("0")) {
				 * detachedCriteria.setProjection(Projections.distinct(Projections.property(
				 * "rbm_id")));
				 * ctr.add(Subqueries.propertyNotIn("fstaff_mgr2_id",detachedCriteria)); } }
				 * else builder.equal(root.get(FieldStaff_.fstaff_mgr2_id), divId); } else if
				 * (allocMode.equalsIgnoreCase("4")) { if (cfa_locId == 0) { if
				 * (!alloc_id.equals("0")) {
				 * detachedCriteria.setProjection(Projections.distinct(Projections.property(
				 * "abm_id")));
				 * ctr.add(Subqueries.propertyNotIn("fstaff_mgr1_id",detachedCriteria)); } }
				 * else builder.equal(root.get(FieldStaff_.fstaff_mgr1_id), divId); } else if
				 * (allocMode.equalsIgnoreCase("5")) { if (cfa_locId == 0) { if
				 * (!alloc_id.equals("0")) {
				 * detachedCriteria.setProjection(Projections.distinct(Projections.property(
				 * "msr_id"))); ctr.add(Subqueries.propertyNotIn("fstaff_id",detachedCriteria));
				 * } } else builder.equal(root.get(FieldStaff_.fstaff_id), divId); }
				 * 
				 * if (!fstaffType.equals("A")) { if (!fstaffType.equals("T"))
				 * builder.equal(root.get(FieldStaff_.fstaff_training), divId); }
				 */

				/*
				 * builder.setProjection(Projections .projectionList()
				 * .add(Projections.count(dist))
				 * .add(Projections.groupProperty("fstaff_training"), "fstaff_training"));
				 * 
				 * resultList = em.createQuery(criteriaQuery).getResultList();
				 */

				resultList = entityManager.createQuery(criteriaQuery).getResultList();
			}

		} catch (Exception ex) {
			System.out.println("Error Occurred :" + new CodifyErrors().getErrorCode(ex));// uncomment asneeded --
																							// System.out.println("Error
																							// Occurred :" + new
																							// CodifyErrors().getErrorCode(e));//
																							// uncomment asneeded --;
		} finally {
			if (em != null) {
				em.close();
			}
		}
		return resultList;

	}

	public StringBuffer getStaffCountQuery(String lvlCode, String cfalocId, String divId, String status, String dist,
			String allocMode, String fstaffType, String alloc_id, String period_code, String alloc_cycle,
			String prodtype, String fin_year, String comapny, String sub_team_code) {
		StringBuffer buffer = new StringBuffer("SELECT COUNT(DISTINCT ").append(dist).append(") ");
		buffer.append(" FROM  FIELDSTAFF this_ WHERE");
		if (!allocMode.equals("-1")) {
			buffer.append("  this_.FSTAFF_ID in");
			buffer.append(" ( select distinct fd.FSTAFFD_FSTAFF_ID from FIELDSTAFF_Detail fd , DEPOT_LOCATIONS d");
			buffer.append(" where fd.FSTAFFD_LOC_ID = d.DPTLOC_ID ");
			if (!cfalocId.equals("0"))
				buffer.append(" and d.DPTDESTINATION_ID =").append(cfalocId);
			else if (!alloc_id.equals("0")) {
				buffer.append(" and DPTDESTINATION_ID not in(select distinct cfa_destination_id")
						.append(" from alloc_gen_ent where div_id=").append(divId).append(" and period_code=")
						.append(period_code).append(" and alloc_cycle=").append(alloc_cycle)
						.append(" and product_type='").append(prodtype.split("_")[1]).append("' and fin_year=")
						.append(fin_year).append(" and company='").append(comapny).append("')");
			}

			buffer.append(")  AND ");
		}
		buffer.append("  this_.FSTAFF_status='A'");
		buffer.append(" AND this_.FSTAFF_SAMP_DISP_IND='Y'");
		buffer.append(" AND this_.FS_CATEGORY not in('E','D')");
		// if(dist.equalsIgnoreCase("fstaff_id")) {
		buffer.append(" AND this_.TEAM_CODE='").append(sub_team_code).append("'");
		// }
		buffer.append(" AND this_.FSTAFF_LEAVING_DATE is null ");
		buffer.append(" AND this_.FSTAFF_DIV_ID =").append(divId);
		// asked by pawaskar 9-Aug-2017for training staff do not consider level code
		if (lvlCode != null) {
			buffer.append(" AND this_.FSTAFF_LEVEL_CODE='").append(lvlCode).append("'");
		}

		if (status != null)
			if (!status.equals("A"))
				buffer.append(" AND fstaff_dom_exp='").append(status).append("'");
		if (!fstaffType.equals("T")) {
			buffer.append(" AND fstaff_training <>'T'");
		} else {
			buffer.append(" AND fstaff_training ='T'");

		}
		System.out.println("FieldStaffDao.getStaffCountQuery()");
		return buffer;
	}

	@Override
	public List<Object> getZone(String divId, String status, String period_code, String fin_year, String company,
			String saveMode, String planType, String alloc_id, String alloc_cycle, String prodtype, List<String> brands,
			String sub_team_code) {
		EntityManager em = null;
		List<Object> result = null;
		try {
			System.out.println("FieldStaffDao.getZone()" + divId + "  ");

			StringBuffer sb = new StringBuffer();
			sb.append(
					"select SGprmdet_id,SGprmdet_disp_nm from sg_d_parameters_details where SGprmdet_SGprm_id = 13 and SGprmdet_id in");
			sb.append("(select distinct  fstaff_zone_id  from FIELDSTAFF where fstaff_div_id=:divId")
					.append(" and FSTAFF_SAMP_DISP_IND='Y' and FS_CATEGORY not in('E','D') ");
			if (!sub_team_code.equals("All")) {
				sb.append(" and TEAM_CODE=:sub_team_code");
			}
			sb.append(" and fstaff_status='A'");
			sb.append(" AND FSTAFF_LEAVING_DATE is null ");
			if (!status.equalsIgnoreCase("A")) {
				sb.append(" and FSTAFF_DOM_EXP=:status ");
			}
			sb.append(")");

			if (!alloc_id.equals("0")) {
				sb.append("	and SGprmdet_id NOT In(  SELECT DISTINCT zone_id  FROM  alloc_gen_ent");
				sb.append(" WHERE  div_id =:divId").append(" and  period_code=:period_code");
				sb.append(" and  alloc_cycle=:alloc_cycle");
				sb.append(" and alloc_gen_id=:alloc_id");
				sb.append(" and  product_type=:prodtype");
				sb.append(" and  fin_year=:fin_year")
						.append("  and  company=:company and alloc_smp_sa_prod_group_id in(:brands))");
			}
			Query query = entityManager.createNativeQuery(sb.toString(), Tuple.class);
			query.setParameter("divId", divId);
			if (!sub_team_code.equals("All")) {
				query.setParameter("sub_team_code", sub_team_code);
			}
			if (!status.equalsIgnoreCase("A")) {
				query.setParameter("status", status);
			}
			if (!alloc_id.equals("0")) {
				query.setParameter("period_code", period_code);
				query.setParameter("alloc_cycle", alloc_cycle);
				query.setParameter("alloc_id", alloc_id);
				query.setParameter("prodtype", planType.split("_")[1]);
				query.setParameter("fin_year", fin_year);
				query.setParameter("company", company);
				query.setParameter("brands", brands);
			}
			List<Tuple> tuples = query.getResultList();
			if (tuples != null && !tuples.isEmpty()) {
				result = new ArrayList<>();
				AllocationBean object = null;
				for (Tuple t : tuples) {
					object = new AllocationBean();
					object.setAllocationById(Long.valueOf(t.get("SGprmdet_id", Integer.class)));
					object.setAllocationByDesc(t.get("SGprmdet_disp_nm", String.class));
					result.add(object);
				}
			}
			if (!result.isEmpty() && result.size() > 0) {
				return result;
			}

		} catch (Exception ex) {
			System.out.println("Error Occurred :" + new CodifyErrors().getErrorCode(ex));// uncomment asneeded --
																							// System.out.println("Error
																							// Occurred :" + new
																							// CodifyErrors().getErrorCode(e));//
																							// uncomment asneeded --;
		} finally {
			if (em != null) {
				em.close();
			}
		}
		return result;
	}

	@Override
	public List<Object> getState(String divId, String status, String period_code, String fin_year, String company,
			String saveMode, String planType, String alloc_id, String alloc_cycle, String prodtype, List<String> brands,
			String sub_team_code) {
		EntityManager em = null;
		List<Object> result = null;
		try {
			System.out.println("FieldStaffDao.getZone()" + divId + "  ");

			StringBuffer sb = new StringBuffer();
			sb.append(
					"select SGprmdet_id,SGprmdet_disp_nm from sg_d_parameters_details where SGprmdet_SGprm_id = 2 and SGprmdet_id in");
			sb.append("(select distinct  FSTAFF_STATE_ID  from FIELDSTAFF where fstaff_div_id=:divId")
					.append(" and FSTAFF_SAMP_DISP_IND='Y' and FS_CATEGORY not in('E','D') ");
			if (!sub_team_code.equals("All")) {
				sb.append(" and TEAM_CODE=:sub_team_code");
			}
			sb.append(" and fstaff_status='A'");
			sb.append(" AND FSTAFF_LEAVING_DATE is null ");
			if (!status.equalsIgnoreCase("A")) {
				sb.append(" and FSTAFF_DOM_EXP=:status");
			}
			sb.append(")");

			if (!alloc_id.equals("0")) {
				sb.append("	and SGprmdet_id NOT In(  SELECT DISTINCT state_id  FROM  alloc_gen_ent");
				sb.append(" WHERE  div_id =:divId").append(" and  period_code=:period_code");
				sb.append(" and  alloc_cycle=:alloc_cycle");
				sb.append(" and alloc_gen_id=:alloc_id");
				sb.append(" and  product_type=:prodtype");
				sb.append(" and  fin_year=:fin_year ")
						.append("  and  company=:company and alloc_smp_sa_prod_group_id in(:brands))");
			}

			em = emf.createEntityManager();
			Query query = em.createNativeQuery(sb.toString(), Tuple.class);
			query.setParameter("divId", divId);
			if (!sub_team_code.equals("All")) {
				query.setParameter("sub_team_code", sub_team_code);
			}
			if (!status.equalsIgnoreCase("A")) {
				query.setParameter("status", status);
			}
			if (!alloc_id.equals("0")) {
				query.setParameter("period_code", period_code);
				query.setParameter("alloc_cycle", alloc_cycle);
				query.setParameter("alloc_id", alloc_id);
				query.setParameter("prodtype", planType.split("_")[1]);
				query.setParameter("fin_year", fin_year);
				query.setParameter("company", company);
				query.setParameter("brands", brands);
			}
			List<Tuple> tuples = query.getResultList();
			if (tuples != null && !tuples.isEmpty()) {
				result = new ArrayList<>();
				AllocationBean object = null;
				for (Tuple t : tuples) {
					object = new AllocationBean();
					object.setAllocationById(Long.valueOf(t.get("SGprmdet_id", Integer.class)));
					object.setAllocationByDesc(t.get("SGprmdet_disp_nm", String.class));
					result.add(object);
				}
			}
			System.out.println("getState" + result.size());
			if (!result.isEmpty() && result.size() > 0) {
				return result;
			}

		} finally {
			if (em != null) {
				em.close();
			}
		}
		return result;
	}

	@Override
	public Long getNoOfStaffByStaffwise(String lvlCode, String divId, String status, String allocMode, String cfalocId,
			String dist, String fstaffType, String alloc_id, String period_code, String alloc_cycle, String prodtype,
			String fin_year, String company, String sub_team_code) {

		Long count = 0L;
		EntityManager em = null;
		System.out.println("Printing --------lvlCode::::::" + lvlCode);
		System.out.println("Printing --------divId::::::" + divId);
		System.out.println("Printing --------status::::::" + status);
		System.out.println("Printing --------allocMode::::::" + allocMode);
		System.out.println("Printing --------cfalocId::::::" + cfalocId);
		System.out.println("Printing --------dist::::::" + dist);
		System.out.println("Printing --------fstaffType::::::" + fstaffType);
		System.out.println("Printing --------alloc_id::::::" + alloc_id);
		System.out.println("Printing --------period_code::::::" + period_code);
		System.out.println("Printing --------alloc_cycle::::::" + alloc_cycle);
		System.out.println("Printing --------prodtype::::::" + prodtype);
		System.out.println("Printing --------fin_year::::::" + fin_year);
		System.out.println("Printing --------company::::::" + company);
		System.out.println("Printing --------Team::::::" + sub_team_code);
		try {
			CriteriaBuilder builder = entityManager.getCriteriaBuilder();
			CriteriaQuery<Long> criteriaQuery = builder.createQuery(Long.class);
			Root<FieldStaff> root = criteriaQuery.from(FieldStaff.class);
			List<Predicate> predicates = new ArrayList<>();

			Subquery<Long> subquery = criteriaQuery.subquery(Long.class);
			Root<Alloc_gen_ent> subqueryRoot = subquery.from(Alloc_gen_ent.class);
			List<Predicate> subqueryPerdicates = new ArrayList<>();

			System.out.println("FieldStaffDao.getNoOfStaffByStaffwise()" + cfalocId + "," + divId + " ," + lvlCode
					+ ", " + fstaffType);
			if (allocMode.equals("0")) {
				Query query = entityManager
						.createNativeQuery(this
								.getStaffCountQuery(lvlCode, cfalocId, divId, status, dist, allocMode, fstaffType,
										alloc_id, period_code, alloc_cycle, prodtype, fin_year, company, sub_team_code)
								.toString());
				count = Long.valueOf(query.getSingleResult().toString());

			} else if (allocMode != null) {

				if (alloc_cycle != null) {
					subqueryPerdicates.add(builder.equal(subqueryRoot.get(Alloc_gen_ent_.fin_year), fin_year));
					if (!alloc_cycle.equals(""))
						subqueryPerdicates.add(builder.equal(subqueryRoot.get(Alloc_gen_ent_.alloc_cycle),
								Long.parseLong(alloc_cycle)));
					subqueryPerdicates.add(builder.equal(subqueryRoot.get(Alloc_gen_ent_.company), company));
					subqueryPerdicates
							.add(builder.equal(subqueryRoot.get(Alloc_gen_ent_.product_type), prodtype.split("_")[1]));
					subqueryPerdicates.add(builder.equal(subqueryRoot.get(Alloc_gen_ent_.period_code), period_code));
					subqueryPerdicates.add(builder.equal(subqueryRoot.get(Alloc_gen_ent_.company), company));
				}

				Long cfa_locId = Long.valueOf(cfalocId);

				predicates.add(builder.equal(root.get(FieldStaff_.fs_div_id), Long.parseLong(divId)));
				if (lvlCode != null) {
					predicates.add(builder.equal(root.get(FieldStaff_.level_code), lvlCode));
				}
				predicates.add(builder.equal(root.get(FieldStaff_.fstaff_samp_disp_ind), "Y"));
				predicates.add(builder.not(root.get(FieldStaff_.fs_category).in("E", "D")));
				predicates.add(builder.equal(root.get(FieldStaff_.status), "A"));
				predicates.add(builder.isNull(root.get(FieldStaff_.leaving_date)));
				// if(dist.equalsIgnoreCase("fstaff_id")) {
				predicates.add(builder.equal(root.get(FieldStaff_.team_code), sub_team_code));
				// }

				if (status != null) {
					if (!status.equalsIgnoreCase("A")) {
						predicates.add(builder.equal(root.get(FieldStaff_.fstaff_dom_exp), status));
					}
				}

				if (allocMode.equalsIgnoreCase("1")) {
					if (cfa_locId == 0) {
						if (!alloc_id.equals("0")) {
							subquery.select(subqueryRoot.get(Alloc_gen_ent_.zone_id)).distinct(true)
									.where(subqueryPerdicates.toArray(new Predicate[] {}));
							predicates.add(builder.not(root.get(FieldStaff_.fstaff_zone_id).in(subquery)));
						}

					} else
						predicates.add(builder.equal(root.get(FieldStaff_.fstaff_zone_id), cfa_locId));

				} else if (allocMode.equalsIgnoreCase("2")) {
					if (cfa_locId == 0) {
						if (!alloc_id.equals("0")) {
							System.out.println("FieldStaffDao.getNoOfStaffByStaffwise()" + alloc_id);
							subquery.select(subqueryRoot.get(Alloc_gen_ent_.state_id)).distinct(true)
									.where(subqueryPerdicates.toArray(new Predicate[] {}));
							predicates.add(builder.in(root.get(FieldStaff_.fstaff_state_id)).value(subquery).not());
						}
					} else
						predicates.add(builder.equal(root.get(FieldStaff_.fstaff_state_id), cfa_locId));
				} else if (allocMode.equalsIgnoreCase("3")) {
					if (cfa_locId == 0) {
						if (!alloc_id.equals("0")) {
							subquery.select(subqueryRoot.get(Alloc_gen_ent_.rbm_id)).distinct(true)
									.where(subqueryPerdicates.toArray(new Predicate[] {}));
							;
							predicates.add(builder.in(root.get(FieldStaff_.fstaff_mgr2_id)).value(subquery).not());
						}
					} else
						predicates.add(builder.equal(root.get(FieldStaff_.fstaff_mgr2_id), cfa_locId));
				} else if (allocMode.equalsIgnoreCase("4")) {
					if (cfa_locId == 0) {
						if (!alloc_id.equals("0")) {
							subquery.select(subqueryRoot.get(Alloc_gen_ent_.abm_id)).distinct(true)
									.where(subqueryPerdicates.toArray(new Predicate[] {}));
							;
							predicates.add(builder.in(root.get(FieldStaff_.fstaff_mgr1_id)).value(subquery).not());
						}
					} else
						predicates.add(builder.equal(root.get(FieldStaff_.fstaff_mgr1_id), cfa_locId));
				} else if (allocMode.equalsIgnoreCase("5")) {
					if (cfa_locId == 0) {
						if (!alloc_id.equals("0")) {
							subquery.select(subqueryRoot.get(Alloc_gen_ent_.msr_id)).distinct(true)
									.where(subqueryPerdicates.toArray(new Predicate[] {}));
							;
							predicates.add(builder.in(root.get(FieldStaff_.fstaff_id)).value(subquery).not());
						}
					} else
						predicates.add(builder.equal(root.get(FieldStaff_.fstaff_id), cfa_locId));
				}
				if (!fstaffType.equals("A")) {
					if (!fstaffType.equals("T"))
						predicates.add(builder.equal(root.get(FieldStaff_.fstaff_training), fstaffType));

				}
				if (dist.equalsIgnoreCase("fstaff_id"))
					criteriaQuery.select(builder.countDistinct(root.get(FieldStaff_.fstaff_id)))
							.where((predicates.toArray(new Predicate[] {})));
				else if (dist.equalsIgnoreCase("fstaff_mgr1_id"))
					criteriaQuery.select(builder.countDistinct(root.get(FieldStaff_.fstaff_mgr1_id))).distinct(true)
							.where((predicates.toArray(new Predicate[] {})));
				else if (dist.equalsIgnoreCase("fstaff_mgr2_id"))
					criteriaQuery.select(builder.countDistinct(root.get(FieldStaff_.fstaff_mgr2_id))).distinct(true)
							.where((predicates.toArray(new Predicate[] {})));

				TypedQuery<Long> q = entityManager.createQuery(criteriaQuery);

				count = q.getSingleResult();
				System.out.println("count --------" + count);
			}

		} catch (Exception ex) {
			System.out.println("Error Occurred :" + new CodifyErrors().getErrorCode(ex));// uncomment asneeded --
																							// System.out.println("Error
																							// Occurred :" + new
																							// CodifyErrors().getErrorCode(e));//
																							// uncomment asneeded --;
		} finally {
			if (em != null) {
				em.close();
			}
		}
		return count;

	}

	@Override
	public List<Object> getStaffDetails(String divId, String status, String lvlCode, String saveMode, String alloctype,
			String alloc_id, String alloc_cycle, String period_code, String fin_year, String company, String prodtype,
			String fieldtype, List<String> brands, String sub_team_code) {
		System.out.println("FieldStaffDao.getStaffDetails()*******" + fieldtype + "_" + divId + "_" + lvlCode + "-"
				+ alloc_id + " " + prodtype);
		List<FieldStaff> list = null;
		List<Object> objectList = null;
		EntityManager em = null;
		try {
			CriteriaBuilder builder = entityManager.getCriteriaBuilder();
			CriteriaQuery<FieldStaff> criteriaQuery = builder.createQuery(FieldStaff.class);
			Root<FieldStaff> root = criteriaQuery.from(FieldStaff.class);
			List<Predicate> predicates = new ArrayList<>();

			Subquery<Long> subquery = criteriaQuery.subquery(Long.class);
			Root<Alloc_gen_ent> subqueryRoot = subquery.from(Alloc_gen_ent.class);
			List<Predicate> subqueryPerdicates = new ArrayList<>();
			subqueryPerdicates.add(builder.equal(subqueryRoot.get(Alloc_gen_ent_.div_id), divId));
			subqueryPerdicates.add(builder.equal(subqueryRoot.get(Alloc_gen_ent_.period_code), period_code));
			subqueryPerdicates.add(builder.equal(subqueryRoot.get(Alloc_gen_ent_.fin_year), fin_year));
			subqueryPerdicates.add(builder.equal(subqueryRoot.get(Alloc_gen_ent_.company), company));
			subqueryPerdicates.add(builder.equal(subqueryRoot.get(Alloc_gen_ent_.product_type), prodtype));
			subqueryPerdicates.add(builder.equal(subqueryRoot.get(Alloc_gen_ent_.alloc_cycle), alloc_cycle));
			subqueryPerdicates.add(builder.and(subqueryRoot.get(Alloc_gen_ent_.alloc_smp_sa_prod_group_id).in(brands)));

			predicates.add(builder.equal(root.get(FieldStaff_.fstaff_samp_disp_ind), "Y"));
			predicates.add(builder.not(root.get(FieldStaff_.fs_category).in("E", "D")));
			predicates.add(builder.isNull(root.get(FieldStaff_.leaving_date)));
			predicates.add(builder.equal(root.get(FieldStaff_.status), "A"));
			predicates.add(builder.equal(root.get(FieldStaff_.fs_div_id), divId));
			if (!sub_team_code.equals("All")) {
				predicates.add(builder.equal(root.get(FieldStaff_.team_code), sub_team_code));
			}

			if (lvlCode.equalsIgnoreCase("004")) {

				predicates.add(builder.equal(root.get(FieldStaff_.level_code), "002"));
				subqueryPerdicates.add(builder.greaterThan(subqueryRoot.get(Alloc_gen_ent_.abm_id), 0l));
				subquery.select(subqueryRoot.get(Alloc_gen_ent_.abm_id)).distinct(true)
						.where(subqueryPerdicates.toArray(new Predicate[] {}));

			} else if (lvlCode.equalsIgnoreCase("005")) {
				predicates.add(builder.equal(root.get(FieldStaff_.level_code), "001"));
				subqueryPerdicates.add(builder.greaterThan(subqueryRoot.get(Alloc_gen_ent_.msr_id), 0l));
				subquery.select(subqueryRoot.get(Alloc_gen_ent_.msr_id)).distinct(true)
						.where(subqueryPerdicates.toArray(new Predicate[] {}));

			} else {
				predicates.add(builder.equal(root.get(FieldStaff_.level_code), lvlCode));
				subqueryPerdicates.add(builder.greaterThan(subqueryRoot.get(Alloc_gen_ent_.rbm_id), 0l));
				subquery.select(subqueryRoot.get(Alloc_gen_ent_.rbm_id)).distinct(true)
						.where(subqueryPerdicates.toArray(new Predicate[] {}));
			}
			if (!status.equalsIgnoreCase("A") && lvlCode.equalsIgnoreCase("001")) {
				///// Level code except 001 has no team attached.asked by Pawaskar sir.
				predicates.add(builder.equal(root.get(FieldStaff_.fstaff_dom_exp), status));
			}
			if (fieldtype.equalsIgnoreCase("T"))
				predicates.add(builder.equal(root.get(FieldStaff_.fstaff_training), fieldtype));
			else
				predicates.add(builder.notEqual(root.get(FieldStaff_.fstaff_training), "T"));

			if (!alloc_id.equals("0")) {
				subqueryPerdicates.add(builder.equal(subqueryRoot.get(Alloc_gen_ent_.alloc_gen_id), alloc_id));
				predicates.add(builder.in(root.get(FieldStaff_.fstaff_id)).value(subquery).not());
			}

			criteriaQuery
					.multiselect(root.get(FieldStaff_.fstaff_id), root.get(FieldStaff_.fstaff_display_name),
							root.get(FieldStaff_.fstaff_name), root.get(FieldStaff_.fstaff_terrname))
					.distinct(true).where((predicates.toArray(new Predicate[] {})))
					.orderBy(builder.asc(root.get(FieldStaff_.fstaff_display_name)));

			TypedQuery<FieldStaff> fieldstaff = entityManager.createQuery(criteriaQuery);

			list = fieldstaff.getResultList();

			if (list != null && !list.isEmpty()) {
				objectList = new ArrayList<>();
				AllocationBean object = null;
				for (FieldStaff f : list) {
					object = new AllocationBean();
					object.setAllocationById(Long.valueOf(f.getFstaff_id()));
					object.setAllocationByDesc(f.getFstaff_display_name());
					objectList.add(object);
				}
			}

			// count = list.size() ctr.addOrder(Order.asc("fstaff_display_name"));;

		} catch (Exception ex) {
			System.out.println("Error Occurred :" + new CodifyErrors().getErrorCode(ex));// uncomment asneeded --
																							// System.out.println("Error
																							// Occurred :" + new
																							// CodifyErrors().getErrorCode(e));//
																							// uncomment asneeded --;
		} finally {
			if (em != null) {
				em.close();
			}
		}
		return objectList;

	}

	@Override
	public List<Object> getFstaffDataFoDes(String lvlCode, String divId, String status, String allocMode,
			String cfalocId, String fs_column, String fieldtype, String alloc_id, String period_code,
			String alloc_cycle, String prodtype, String fin_year, String company, String common_fs_ind,
			String sub_team_code, String alloc_type) throws Exception {

		List<Object> list = null;
		try {

			System.out.println(divId + "lvl_code" + lvlCode + "cfalocId" + cfalocId);
			System.out.println("FieldStaffDao.getFstaffDataFoDes()" + allocMode);
			if (allocMode.equals("0")) {
				StringBuffer buffer = new StringBuffer(this.getStaffDataQuery(lvlCode, cfalocId, divId, status,
						fieldtype, alloc_id, period_code, alloc_cycle, prodtype, fin_year, company, fs_column,
						common_fs_ind, sub_team_code, alloc_type));
				;
				Query query = entityManager.createNativeQuery(buffer.toString());
				list = query.getResultList();
				if (common_fs_ind.equalsIgnoreCase("T")) {
					System.out.println("List Size " + fs_column + " ::::" + list.size());
				}
				/*
				 * System.out.println("All Objects"); for(Object o :
				 * list)System.out.println(o.toString());
				 */
			}
		} catch (Exception e) {
			System.out.println("Error Occurred :" +e.getMessage());// uncomment asneeded --;
		}
		return list;
	}

	@Override
	public StringBuffer getStaffDataQuery(String lvlCode, String cfalocId, String divId, String status,
			String fieldtype, String alloc_id, String period_code, String alloc_cycle, String prodtype, String fin_year,
			String company, String fs_column, String common_fs_ind, String sub_team_code, String alloc_type) {
		System.out.println("FS TRAining Indicator  " + fieldtype);
		StringBuffer buffer = new StringBuffer("SELECT DISTINCT FS.").append(fs_column)
				.append(",MG.FSTAFF_LEVEL_CODE,MG.fstaff_training, MG.FSTAFF_DISPLAY_NAME ");
		buffer.append(" FROM  FIELDSTAFF FS, FIELDSTAFF MG" + " WHERE  FS.FSTAFF_ID in");
		buffer.append(" ( select distinct fd.FSTAFFD_FSTAFF_ID from FIELDSTAFF_Detail fd , DEPOT_LOCATIONS d");
		buffer.append(" where fd.FSTAFFD_LOC_ID = d.DPTLOC_ID ");

		if (!cfalocId.equals("0"))
			buffer.append("and d.DPTDESTINATION_ID =").append(cfalocId);
		else if (!alloc_id.equals("0") && alloc_type.trim().equals(M)) {
			buffer.append(" and DPTDESTINATION_ID not in(select distinct cfa_destination_id")
					.append(" from alloc_gen_ent where div_id=").append(divId).append(" and period_code=")
					.append(period_code).append(" and alloc_cycle=").append(alloc_cycle).append(" and product_type='")
					.append(prodtype.split("_")[1]).append("' and fin_year=").append(fin_year).append(" and company='")
					.append(company).append("')");
		}
		buffer.append(")");
		buffer.append(" AND FS.FSTAFF_SAMP_DISP_IND='Y'");
		buffer.append(" AND FS.FS_CATEGORY not in('E','D')");
		// if(fs_column.equalsIgnoreCase("fstaff_id") ) {
		buffer.append(" AND FS.TEAM_CODE='").append(sub_team_code).append("'");
		// }
		buffer.append(" AND FS.FSTAFF_STATUS='A'");
		buffer.append(" AND FS.FSTAFF_LEAVING_DATE is null ");
		buffer.append(" AND FS.FSTAFF_DIV_ID =").append(divId);
		buffer.append(" AND FS.FSTAFF_LEVEL_CODE='").append(lvlCode).append("'");
		if (!status.equals("A"))
			buffer.append(" AND FS.fstaff_dom_exp='").append(status).append("'");
		if (!fieldtype.equals("A")) {
			if (!fieldtype.equals("T"))
				buffer.append(" AND FS.fstaff_training <>'T'");
		}
		buffer.append(" AND FS.").append(fs_column)
				.append(" = MG.FSTAFF_ID AND MG.FSTAFF_LEAVING_DATE IS NULL AND MG.FSTAFF_SAMP_DISP_IND = 'Y' ");
		if (common_fs_ind.equalsIgnoreCase("T"))
			buffer.append(" AND  FS.fstaff_training = 'T'");
		System.out.println("FieldStaffDao.getStaffDataQuery()" + buffer);
		return buffer;
	}

	@Override
	public List<FieldStaff> getFstaffData(String lvlCode, String divId, String status, String allocMode,
			String cfalocId, String fs_column, String fstaffType, String alloc_id, String alloc_cycle, String fin_year,
			String period_code, String prodtype, String company, String sub_team_code) throws Exception {

		Long cfa_locId = Long.valueOf(cfalocId);
		System.out.println("para::" + cfa_locId + "=" + lvlCode + "=" + divId + "=" + status + "=" + allocMode + "="
				+ cfalocId + "=" + fs_column + "=" + fstaffType + "=" + alloc_id + "=" + alloc_cycle + "=" + fin_year
				+ "=" + period_code + "=" + prodtype + "=" + company);

		EntityManager em = null;
		List<FieldStaff> list = null;
		try {

			CriteriaBuilder builder = entityManager.getCriteriaBuilder();
			CriteriaQuery<FieldStaff> criteriaQuery = builder.createQuery(FieldStaff.class);
			Root<FieldStaff> root = criteriaQuery.from(FieldStaff.class);
			List<Predicate> predicates = new ArrayList<>();

			Subquery<Long> subquery = criteriaQuery.subquery(long.class);
			Root<FieldStaff> subqueryRoot = subquery.from(FieldStaff.class);
			List<Predicate> subqueryPerdicates = new ArrayList<>();

			Subquery<Long> alloSubquery = null;
			Root<Alloc_gen_ent> alloSubqueryRoot = null;
			List<Predicate> alloSubqueryPerdicates = null;

			subqueryPerdicates.add(builder.equal(subqueryRoot.get(FieldStaff_.fs_div_id), Long.parseLong(divId)));
			subqueryPerdicates.add(builder.equal(subqueryRoot.get(FieldStaff_.level_code), lvlCode));
			subqueryPerdicates.add(builder.equal(subqueryRoot.get(FieldStaff_.fstaff_samp_disp_ind), "Y"));
			subqueryPerdicates.add(builder.not(subqueryRoot.get(FieldStaff_.fs_category).in("E", "D")));
			subqueryPerdicates.add(builder.equal(subqueryRoot.get(FieldStaff_.status), "A"));
			subqueryPerdicates.add(builder.isNull(subqueryRoot.get(FieldStaff_.leaving_date)));
			if (fs_column.equalsIgnoreCase("fstaff_id") || !sub_team_code.trim().equalsIgnoreCase("All")) {
				subqueryPerdicates.add(builder.equal(root.get(FieldStaff_.team_code), sub_team_code));
			}
			// .add(Projections.property("level_code"), "level_code")));

			if (!alloc_id.equals("0")) {
				if (!company.trim().equals("SER")) {
					alloSubquery = criteriaQuery.subquery(Long.class);
					alloSubqueryRoot = alloSubquery.from(Alloc_gen_ent.class);
					alloSubqueryPerdicates = new ArrayList<>();
					alloSubqueryPerdicates.add(builder.equal(alloSubqueryRoot.get(Alloc_gen_ent_.fin_year), fin_year));
					// alloSubqueryPerdicates.add(builder.equal(alloSubqueryRoot.get(Alloc_gen_ent_.alloc_cycle),alloc_cycle));
					// alloSubqueryPerdicates.add(builder.equal(alloSubqueryRoot.get(Alloc_gen_ent_.product_type),prodtype.split("_")[1]));
					alloSubqueryPerdicates.add(builder.equal(alloSubqueryRoot.get(Alloc_gen_ent_.company), company));
					alloSubqueryPerdicates
							.add(builder.equal(alloSubqueryRoot.get(Alloc_gen_ent_.period_code), period_code));
				}

			}
			if (!status.equalsIgnoreCase("A")) {
				subqueryPerdicates.add(builder.equal(subqueryRoot.get(FieldStaff_.fstaff_dom_exp), status));
			}

			if (allocMode.equalsIgnoreCase("0") && cfa_locId > 0) {
				subqueryPerdicates
						.add(builder.equal(subqueryRoot.get(FieldStaff_.fstaff_loc_id), cfa_locId.intValue()));
			}
			if (allocMode.equalsIgnoreCase("1")) {
				if (cfa_locId != 0) {
					if (!alloc_id.equals("0")) {
						if (alloSubquery != null) {
							// zonecondition
							alloSubquery.select(alloSubqueryRoot.get(Alloc_gen_ent_.zone_id)).distinct(true)
									.where(alloSubqueryPerdicates.toArray(new Predicate[] {}));
						}
					}

					if (alloSubquery != null)
						// zonecondition
						subqueryPerdicates
								.add(builder.in(root.get(FieldStaff_.fstaff_zone_id)).value(alloSubquery).not());

				} else
					subqueryPerdicates.add(builder.equal(subqueryRoot.get(FieldStaff_.fstaff_zone_id), cfa_locId));
			}

			if (allocMode.equalsIgnoreCase("2")) {
				if (cfa_locId == 0) {
					if (!alloc_id.equals("0")) {
						if (alloSubquery != null)
							alloSubquery.select(alloSubqueryRoot.get(Alloc_gen_ent_.state_id)).distinct(true)
									.where(alloSubqueryPerdicates.toArray(new Predicate[] {}));
					}

					if (alloSubquery != null)
						subqueryPerdicates
								.add(builder.in(root.get(FieldStaff_.fstaff_state_id)).value(alloSubquery).not());

				} else
					subqueryPerdicates
							.add(builder.equal(subqueryRoot.get(FieldStaff_.fstaff_state_id), cfa_locId.longValue()));
			}

			if (allocMode.equalsIgnoreCase("3")) {
				if (cfa_locId == 0) {
					if (!alloc_id.equals("0")) {
						if (alloSubquery != null)
							alloSubquery.select(alloSubqueryRoot.get(Alloc_gen_ent_.rbm_id)).distinct(true)
									.where(alloSubqueryPerdicates.toArray(new Predicate[] {}));
					}

					if (alloSubquery != null)
						subqueryPerdicates
								.add(builder.in(root.get(FieldStaff_.fstaff_mgr2_id)).value(alloSubquery).not());

				} else
					subqueryPerdicates.add(builder.equal(subqueryRoot.get(FieldStaff_.fstaff_mgr2_id), cfa_locId));
			}
			if (allocMode.equalsIgnoreCase("4")) {
				if (cfa_locId == 0) {
					if (!alloc_id.equals("0")) {
						if (alloSubquery != null)
							alloSubquery.select(alloSubqueryRoot.get(Alloc_gen_ent_.abm_id)).distinct(true)
									.where(alloSubqueryPerdicates.toArray(new Predicate[] {}));
					}

					if (alloSubquery != null)
						subqueryPerdicates
								.add(builder.in(root.get(FieldStaff_.fstaff_mgr1_id)).value(alloSubquery).not());

				} else
					subqueryPerdicates.add(builder.equal(subqueryRoot.get(FieldStaff_.fstaff_mgr1_id), cfa_locId));
			}
			if (allocMode.equalsIgnoreCase("5")) {
				if (cfa_locId == 0) {
					if (!alloc_id.equals("0")) {
						if (alloSubquery != null)
							alloSubquery.select(alloSubqueryRoot.get(Alloc_gen_ent_.msr_id)).distinct(true)
									.where(alloSubqueryPerdicates.toArray(new Predicate[] {}));
					}
					if (alloSubquery != null)
						subqueryPerdicates.add(builder.in(root.get(FieldStaff_.fstaff_id)).value(alloSubquery).not());
				} else
					subqueryPerdicates.add(builder.equal(subqueryRoot.get(FieldStaff_.fstaff_id), cfa_locId));
			}

			if (!fstaffType.equals("A")) {
				if (fstaffType.equals("T"))
					// zonecondition
					subqueryPerdicates.add(builder.equal(subqueryRoot.get(FieldStaff_.fstaff_training), fstaffType));
				else
					subqueryPerdicates.add(builder.notEqual(subqueryRoot.get(FieldStaff_.fstaff_training), "T"));
			}

			if (fs_column.equals("fstaff_mgr2_id"))
				subquery.select(subqueryRoot.get("fstaff_mgr2_id")).distinct(true)
						.where(subqueryPerdicates.toArray(new Predicate[] {}));
			if (fs_column.equals("fstaff_mgr1_id"))
				subquery.select(subqueryRoot.get("fstaff_mgr1_id")).distinct(true)
						.where(subqueryPerdicates.toArray(new Predicate[] {}));
			if (fs_column.equals("fstaff_id"))
				subquery.select(subqueryRoot.get("fstaff_id")).distinct(true)
						.where(subqueryPerdicates.toArray(new Predicate[] {}));

			predicates.add(builder.in(root.get(FieldStaff_.fstaff_id)).value(subquery));
			criteriaQuery.multiselect(root.get(FieldStaff_.fstaff_id), root.get(FieldStaff_.fstaff_name))
					.where((predicates.toArray(new Predicate[] {})));

			TypedQuery<FieldStaff> q = entityManager.createQuery(criteriaQuery);
			list = q.getResultList();
			System.out.println("List size " + list.size());

		} catch (Exception ex) {
			System.out.println("Error Occurred :" + new CodifyErrors().getErrorCode(ex));// uncomment asneeded --
																							// System.out.println("Error
																							// Occurred :" + new
																							// CodifyErrors().getErrorCode(e));//
																							// uncomment asneeded --;
		} finally {
			if (em != null) {
				em.close();
			}
		}
		return list;

	}

	@Override
	public List<DivField> getLevel(String divId, String ind) {
		EntityManager em = null;
		List<DivField> list = new ArrayList<DivField>();
		try {
			em = emf.createEntityManager();
			String q = "select LEVEL_CODE,LEVEL_NAME from DIV_FIELD where DIV_ID=:divId and FS_CATEGORY=:ind order by LEVEL_NAME asc";
			Query query = em.createNativeQuery(q, Tuple.class);
			query.setParameter("ind", ind);
			query.setParameter("divId", divId);
			List<Tuple> tuples = query.getResultList();
			if (tuples != null && !tuples.isEmpty()) {
				for (Tuple t : tuples) {
					DivField df = new DivField();
					df.setLevel_code(t.get("LEVEL_CODE", String.class));
					df.setLevel_name(t.get("LEVEL_NAME", String.class));
					list.add(df);
				}
				if (!list.isEmpty() && list.size() > 0)
					return list;
			}
		} finally {
			if (em != null) {
				em.close();
			}
		}
		return list;
	}

	@Override
	public List<FieldStaff> getAllMgr(String level, String divId, String trainingInd) {
		EntityManager em = null;
		List<FieldStaff> list = new ArrayList<FieldStaff>();
		try {
			em = emf.createEntityManager();
			String q = "select FSTAFF_ID,FSTAFF_NAME,FSTAFF_MGR1_ID,FSTAFF_MGR2_ID,FSTAFF_MGR3_ID,FSTAFF_MGR4_ID,FSTAFF_MGR5_ID,FSTAFF_MGR6_ID, "
					+ "FSTAFF_HQ_ID,FSTAFF_ZONE_ID,CF_IDPFZ,CF_IDPIPL,CF_IDWYTH"
					+ " from FIELDSTAFF where FSTAFF_LEVEL_CODE=:level and FSTAFF_DIV_ID=:divId and FSTAFF_TRAINING=:trainingInd order by FSTAFF_NAME";
			Query query = em.createNativeQuery(q, Tuple.class);
			query.setParameter("level", level);
			query.setParameter("divId", divId);
			query.setParameter("trainingInd", trainingInd);
			List<Tuple> tuples = query.getResultList();
			if (tuples != null && !tuples.isEmpty()) {
				for (Tuple t : tuples) {
					FieldStaff fb = new FieldStaff();
					fb.setFstaff_id(Long.valueOf(t.get("FSTAFF_ID", Integer.class)));
					fb.setFstaff_name(t.get("FSTAFF_NAME", String.class));
					fb.setFstaff_mgr1_id(Long.valueOf(t.get("FSTAFF_MGR1_ID", Integer.class)));
					fb.setFstaff_mgr2_id(Long.valueOf(t.get("FSTAFF_MGR2_ID", Integer.class)));
					fb.setFstaff_mgr3_id(Long.valueOf(t.get("FSTAFF_MGR3_ID", Integer.class)));
					fb.setFstaff_mgr4_id(Long.valueOf(t.get("FSTAFF_MGR4_ID", Integer.class)));
					fb.setFstaff_mgr5_id(Long.valueOf(t.get("FSTAFF_MGR5_ID", Integer.class)));
					fb.setFstaff_mgr6_id(Long.valueOf(t.get("FSTAFF_MGR6_ID", Integer.class)));
					fb.setHq_id(Long.valueOf(t.get("FSTAFF_HQ_ID", Short.class)));
					fb.setFstaff_zone_id(Long.valueOf(t.get("FSTAFF_ZONE_ID", Integer.class)));

//					fb.setCf_idpfz(Long.valueOf(t.get("CF_IDPFZ",Integer.class)));
//					fb.setCf_idpipl(Long.valueOf(t.get("CF_IDPIPL",Integer.class)));
//					fb.setCf_idwyth(Long.valueOf(t.get("CF_IDWYTH",Integer.class)));
					list.add(fb);
				}
				if (!list.isEmpty() && list.size() > 0)
					return list;
			}
		} finally {
			if (em != null) {
				em.close();
			}
		}
		return list;
	}

	@Override
	public List<FieldStaff> getMgr(String mgrId) {
		EntityManager em = null;
		List<FieldStaff> list = new ArrayList<FieldStaff>();
		try {
			em = emf.createEntityManager();
			String q = "select FSTAFF_ID,FSTAFF_NAME,FSTAFF_MGR1_ID,FSTAFF_MGR2_ID,FSTAFF_MGR3_ID,FSTAFF_MGR4_ID,FSTAFF_MGR5_ID,FSTAFF_MGR6_ID, "
					+ "FSTAFF_HQ_ID,FSTAFF_ZONE_ID,CF_IDPFZ,CF_IDPIPL,CF_IDWYTH"
					+ " from FIELDSTAFF where FSTAFF_TERR_ID =:mgrId";
			Query query = em.createNativeQuery(q, Tuple.class);
			query.setParameter("mgrId", mgrId);

			List<Tuple> tuples = query.getResultList();
			if (tuples != null && !tuples.isEmpty()) {
				for (Tuple t : tuples) {
					FieldStaff fb = new FieldStaff();
					fb.setFstaff_id(Long.valueOf(t.get("FSTAFF_ID", Integer.class)));
					fb.setFstaff_name(t.get("FSTAFF_NAME", String.class));
					fb.setFstaff_mgr1_id(Long.valueOf(t.get("FSTAFF_MGR1_ID", Integer.class)));
					fb.setFstaff_mgr2_id(Long.valueOf(t.get("FSTAFF_MGR2_ID", Integer.class)));
					fb.setFstaff_mgr3_id(Long.valueOf(t.get("FSTAFF_MGR3_ID", Integer.class)));
					fb.setFstaff_mgr4_id(Long.valueOf(t.get("FSTAFF_MGR4_ID", Integer.class)));
					fb.setFstaff_mgr5_id(Long.valueOf(t.get("FSTAFF_MGR5_ID", Integer.class)));
					fb.setFstaff_mgr6_id(Long.valueOf(t.get("FSTAFF_MGR6_ID", Integer.class)));
					fb.setHq_id(Long.valueOf(t.get("FSTAFF_HQ_ID", Short.class)));
					fb.setFstaff_zone_id(Long.valueOf(t.get("FSTAFF_ZONE_ID", Integer.class)));

					fb.setCf_idpfz(Long.valueOf(t.get("CF_IDPFZ", Integer.class)));
					fb.setCf_idpipl(Long.valueOf(t.get("CF_IDPIPL", Integer.class)));
					fb.setCf_idwyth(Long.valueOf(t.get("CF_IDWYTH", Integer.class)));
					list.add(fb);
				}
				if (!list.isEmpty() && list.size() > 0)
					return list;
			}
		} finally {
			if (em != null) {
				em.close();
			}
		}
		return list;
	}

	@Override
	public List<FieldStaff> getRequestor(String divId) {
		EntityManager em = null;
		List<FieldStaff> list = new ArrayList<FieldStaff>();
		try {
			em = emf.createEntityManager();
			String q = "SELECT fstaff_id,fstaff_display_name FROM FIELDSTAFF where fstaff_level_code='001' and fstaff_div_id=:divId and FSTAFF_LEAVING_DATE is  null and fstaff_samp_disp_ind='Y' and fs_category not in('E','D') ORDER BY FSTAFF_DISPLAY_NAME";
			Query query = em.createNativeQuery(q, Tuple.class);
			query.setParameter("divId", divId);

			List<Tuple> tuples = query.getResultList();
			if (tuples != null && !tuples.isEmpty()) {
				for (Tuple t : tuples) {
					FieldStaff rfs = new FieldStaff();
					rfs.setFstaff_id(Long.valueOf(t.get("fstaff_id", Integer.class)));
					rfs.setFstaff_display_name(t.get("fstaff_display_name", String.class));

					list.add(rfs);
				}
				if (!list.isEmpty() && list.size() > 0)
					return list;
			}
		} finally {
			if (em != null) {
				em.close();
			}
		}
		return list;
	}

	@Override
	public Boolean checkUniqueGenericName(String genName, String divId) {
		EntityManager em = null;
		Boolean data = null;
		try {
			em = emf.createEntityManager();
			String q = "select fstaff_id,fstaff_level_code from fieldstaff  where fstaff_name=:genName and fstaff_div_id=:divId and fstaff_status='A'";
			Query query = em.createNativeQuery(q, Tuple.class);
			query.setParameter("divId", divId);
			query.setParameter("genName", genName);
			List<Tuple> tuples = query.getResultList();
			if (tuples != null && !tuples.isEmpty()) {
				data = true;
			} else {
				data = false;
			}
		} finally {
			if (em != null) {
				em.close();
			}
		}
		return data;
	}

	@Override
	public List<FieldStaffBean> getFstaffDetail(String mgrId, String level, String divId) {
		EntityManager em = null;
		List<FieldStaffBean> list = new ArrayList<FieldStaffBean>();
		try {
			em = emf.createEntityManager();
			String q = "";

			if (level.equals("001")) {
				q = "select A.FSTAFF_ID mgr1_ID,A.FSTAFF_NAME mgr1_name,A.FSTAFF_MGR2_ID MGR2_ID,R.FSTAFF_NAME MGR2_NAME,"
						+ "       A.FSTAFF_MGR3_ID MGR3_ID,Z.FSTAFF_NAME MGR3_NAME,A.FSTAFF_MGR4_ID MGR4_ID,S.FSTAFF_NAME MGR4_NAME,"
						+ "       A.FSTAFF_MGR5_ID MGR5_ID,D.FSTAFF_NAME MGR5_NAME,A.FSTAFF_MGR6_ID MGR6_ID,B.FSTAFF_NAME MGR6_NAME,"
						+ "	   T.TERR_POOL_IND,T.TERR_DESIGNATION,T.TERR_HQ_ID,HQ.HQ_NAME,T.TERR_DISTRICTCD,T.TERR_REGIONCD,"
						+ "	   T.TERR_ZONE_ID,ZN.SGprmdet_nm ZONE_NAME,t.TERR_STATE_ID,ST.SGprmdet_nm STATE,"
						+ "	   t.TERR_CFA_LOC,D1.DPTLOC_NAME LOC1,t.TERR_CFA_LOC2,D2.DPTLOC_NAME LOC2,t.TERR_CFA3_LOC3,D3.DPTLOC_NAME LOC3 "
						+ " from TERR_MASTER T LEFT OUTER JOIN HQMASTER HQ  ON HQ.HQ_ID = T.TERR_HQ_ID"
						+ "                   LEFT OUTER JOIN SG_d_parameters_details ZN ON ZN.SGprmdet_id = T.TERR_ZONE_ID"
						+ "				   LEFT OUTER JOIN SG_d_parameters_details ST ON ST.SGprmdet_id = T.TERR_STATE_ID"
						+ "                   LEFT OUTER JOIN DEPOT_LOCATIONS D1 ON D1.DPTLOC_ID = T.TERR_CFA_LOC"
						+ "                   LEFT OUTER JOIN DEPOT_LOCATIONS D2 ON D2.DPTLOC_ID = T.TERR_CFA_LOC2"
						+ "                   LEFT OUTER JOIN DEPOT_LOCATIONS D3 ON D3.DPTLOC_ID = T.TERR_CFA3_LOC3 ,"
						+ " FIELDSTAFF A     LEFT OUTER JOIN FIELDSTAFF R ON R.FSTAFF_ID = A.FSTAFF_MGR2_ID"
						+ "                   LEFT OUTER JOIN FIELDSTAFF Z ON Z.FSTAFF_ID = A.FSTAFF_MGR3_ID"
						+ "                   LEFT OUTER JOIN FIELDSTAFF S ON S.FSTAFF_ID = A.FSTAFF_MGR4_ID"
						+ "                   LEFT OUTER JOIN FIELDSTAFF D ON D.FSTAFF_ID = A.FSTAFF_MGR5_ID"
						+ "                   LEFT OUTER JOIN FIELDSTAFF B ON B.FSTAFF_ID = A.FSTAFF_MGR6_ID "
						+ " WHERE T.TERR_LEVEL_CODE = '001' AND T.TERR_ID = :mgrId"
						+ " AND A.FSTAFF_TERR_ID = T.TERR_MGR1_ID "
						+ " AND A.FSTAFF_LEVEL_CODE = '002'  AND T.TERR_MGR1_ID > 0 AND T.TERR_DIV_ID =:divId AND A.FSTAFF_STATUS='A'";
			}
			if (level.equals("002")) {
				q = "select 0 mgr1_ID,' '  mgr1_name,R.FSTAFF_ID MGR2_ID,R.FSTAFF_NAME MGR2_NAME,"
						+ "       A.FSTAFF_MGR3_ID MGR3_ID,Z.FSTAFF_NAME MGR3_NAME,A.FSTAFF_MGR4_ID MGR4_ID,S.FSTAFF_NAME MGR4_NAME,"
						+ "       A.FSTAFF_MGR5_ID MGR5_ID,D.FSTAFF_NAME MGR5_NAME,A.FSTAFF_MGR6_ID MGR6_ID,B.FSTAFF_NAME MGR6_NAME,"
						+ "	   T.TERR_POOL_IND,T.TERR_DESIGNATION,T.TERR_HQ_ID,HQ.HQ_NAME,T.TERR_DISTRICTCD,T.TERR_REGIONCD,"
						+ "	   T.TERR_ZONE_ID,ZN.SGprmdet_nm ZONE_NAME,t.TERR_STATE_ID,ST.SGprmdet_nm STATE,"
						+ "	   t.TERR_CFA_LOC,D1.DPTLOC_NAME LOC1,t.TERR_CFA_LOC2,D2.DPTLOC_NAME LOC2,t.TERR_CFA3_LOC3,D3.DPTLOC_NAME LOC3 "
						+ " from TERR_MASTER T LEFT OUTER JOIN HQMASTER HQ  ON HQ.HQ_ID = T.TERR_HQ_ID"
						+ "                   LEFT OUTER JOIN SG_d_parameters_details ZN ON ZN.SGprmdet_id = T.TERR_ZONE_ID"
						+ "				   LEFT OUTER JOIN SG_d_parameters_details ST ON ST.SGprmdet_id = T.TERR_STATE_ID"
						+ "                   LEFT OUTER JOIN DEPOT_LOCATIONS D1 ON D1.DPTLOC_ID = T.TERR_CFA_LOC"
						+ "                   LEFT OUTER JOIN DEPOT_LOCATIONS D2 ON D2.DPTLOC_ID = T.TERR_CFA_LOC2"
						+ "                   LEFT OUTER JOIN DEPOT_LOCATIONS D3 ON D3.DPTLOC_ID = T.TERR_CFA3_LOC3 ,"
						+ " FIELDSTAFF A     LEFT OUTER JOIN FIELDSTAFF R ON R.FSTAFF_ID = A.FSTAFF_ID"
						+ "                   LEFT OUTER JOIN FIELDSTAFF Z ON Z.FSTAFF_ID = A.FSTAFF_MGR3_ID"
						+ "                   LEFT OUTER JOIN FIELDSTAFF S ON S.FSTAFF_ID = A.FSTAFF_MGR4_ID"
						+ "                   LEFT OUTER JOIN FIELDSTAFF D ON D.FSTAFF_ID = A.FSTAFF_MGR5_ID"
						+ "                   LEFT OUTER JOIN FIELDSTAFF B ON B.FSTAFF_ID = A.FSTAFF_MGR6_ID "
						+ " WHERE T.TERR_LEVEL_CODE = '002' AND   T.TERR_ID =:mgrId"
						+ " AND A.FSTAFF_TERR_ID = T.TERR_MGR2_ID "
						+ " AND A.FSTAFF_LEVEL_CODE = '003' AND T.TERR_MGR2_ID  > 0 AND T.TERR_DIV_ID = :divId  AND A.FSTAFF_STATUS='A'";
			}
			if (level.equals("003") || level.equals("004") || level.equals("005")) {
				q = "select 0 mgr1_ID,' '  mgr1_name,0  MGR2_ID,' ' MGR2_NAME,"
						+ "        isnull(A.FSTAFF_MGR3_ID,0) MGR3_ID ,isnull(Z.FSTAFF_NAME ,' ' )" + " MGR3_NAME,"
						+ "        isnull(A.FSTAFF_MGR4_ID,0) MGR4_ID,isnull(S.FSTAFF_NAME,' ' )" + " MGR4_NAME,"
						+ "        isnull(A.FSTAFF_MGR5_ID,0) MGR5_ID,isnull(D.FSTAFF_NAME,' ' )" + " MGR5_NAME,"
						+ "        isnull(A.FSTAFF_MGR6_ID,0) MGR6_ID,isnull(B.FSTAFF_NAME,' ' )" + " MGR6_NAME,"
						+ " T.TERR_POOL_IND,T.TERR_DESIGNATION,T.TERR_HQ_ID,HQ.HQ_NAME,T.TERR_DISTRICTCD,T.TERR_REGIONCD,"
						+ "        T.TERR_ZONE_ID,ZN.SGprmdet_nm" + " ZONE_NAME,t.TERR_STATE_ID,ST.SGprmdet_nm STATE,"
						+ "        t.TERR_CFA_LOC,D1.DPTLOC_NAME"
						+ " LOC1,t.TERR_CFA_LOC2,D2.DPTLOC_NAME LOC2,t.TERR_CFA3_LOC3,D3.DPTLOC_NAME" + " LOC3"
						+ " from TERR_MASTER T" + " LEFT OUTER JOIN HQMASTER HQ  ON HQ.HQ_ID = T.TERR_HQ_ID"
						+ "                    LEFT OUTER JOIN SG_d_parameters_details ZN ON"
						+ " ZN.SGprmdet_id = T.TERR_ZONE_ID"
						+ "                    LEFT OUTER JOIN SG_d_parameters_details ST ON"
						+ " ST.SGprmdet_id = T.TERR_STATE_ID"
						+ "                    LEFT OUTER JOIN DEPOT_LOCATIONS D1 ON D1.DPTLOC_ID =" + " T.TERR_CFA_LOC"
						+ "                    LEFT OUTER JOIN DEPOT_LOCATIONS D2 ON D2.DPTLOC_ID ="
						+ " T.TERR_CFA_LOC2"
						+ "                    LEFT OUTER JOIN DEPOT_LOCATIONS D3 ON D3.DPTLOC_ID ="
						+ " T.TERR_CFA3_LOC3"
						+ " left outer join FIELDSTAFF A on  A.FSTAFF_TERR_ID = T.TERR_MGR3_ID  AND"
						+ " A.FSTAFF_LEVEL_CODE = '004'"
						+ "                    LEFT OUTER JOIN FIELDSTAFF Z ON Z.FSTAFF_ID =" + " A.FSTAFF_MGR3_ID"
						+ "                    LEFT OUTER JOIN FIELDSTAFF S ON S.FSTAFF_ID =" + " A.FSTAFF_MGR4_ID"
						+ "                    LEFT OUTER JOIN FIELDSTAFF D ON D.FSTAFF_ID =" + " A.FSTAFF_MGR5_ID"
						+ "                    LEFT OUTER JOIN FIELDSTAFF B ON B.FSTAFF_ID =" + " A.FSTAFF_MGR6_ID"
						+ " WHERE T.TERR_LEVEL_CODE = :level AND   T.TERR_ID = :mgrId";
			}
			Query query = em.createNativeQuery(q, Tuple.class);
			query.setParameter("mgrId", mgrId);
			if (level.equals("003") || level.equals("004") || level.equals("005")) {
				query.setParameter("level", level);
			}
			if (level.equals("001") || level.equals("002")) {
				query.setParameter("divId", divId);
			}
			List<Tuple> tuples = query.getResultList();
			if (tuples != null && !tuples.isEmpty()) {
				for (Tuple t : tuples) {
					FieldStaffBean fb = new FieldStaffBean();
					fb.setMgr1_id(t.get("MGR1_ID", Integer.class).toString());
					fb.setMgr1_name(t.get("mgr1_name", String.class));
					fb.setMgr2_id(t.get("MGR2_ID", Integer.class).toString());
					fb.setMgr2_name(t.get("MGR2_NAME", String.class));
					fb.setMgr3_id(
							t.get("MGR3_ID", Integer.class) == null ? "" : t.get("MGR3_ID", Integer.class).toString());
					fb.setMgr3_name(t.get("MGR3_NAME", String.class));
					fb.setMgr4_id(t.get("MGR4_ID", Integer.class).toString());
					fb.setMgr4_name(t.get("MGR4_NAME", String.class));
					fb.setMgr5_id(t.get("MGR5_ID", Integer.class).toString());
					fb.setMgr5_name(t.get("MGR5_NAME", String.class));
					fb.setMgr6_id(t.get("MGR6_ID", Integer.class).toString());
					fb.setMgr6_name(t.get("MGR6_NAME", String.class));
					fb.setDesignation(t.get("TERR_DESIGNATION", String.class));
					fb.setTerr_hq_id(t.get("TERR_HQ_ID", Short.class).toString());
					fb.setTerr_districtcd(t.get("TERR_DISTRICTCD", String.class));
					fb.setTerr_regioncd(t.get("TERR_REGIONCD", String.class));
					fb.setTerr_zone_id(t.get("TERR_ZONE_ID", Integer.class).toString());
					fb.setZone_name(t.get("ZONE_NAME", String.class));
					fb.setTerr_state_id(t.get("TERR_STATE_ID", Integer.class).toString());
					fb.setTerr_cfa_loc(t.get("TERR_CFA_LOC", Integer.class).toString());
					fb.setTerr_cfa_loc2(t.get("TERR_CFA_LOC2", Integer.class).toString());
					fb.setTerr_cfa3_loc3(t.get("TERR_CFA3_LOC3", Integer.class).toString());

					list.add(fb);
				}
				if (!list.isEmpty() && list.size() > 0)
					return list;
			}
		} finally {
			if (em != null) {
				em.close();
			}
		}
		return list;
	}

	@Override
	public List<V_G_FieldStaff> getAllActiveFieldStaffDetailList() {
		EntityManager em = null;
		List<V_G_FieldStaff> list = new ArrayList<V_G_FieldStaff>();
		try {
			em = emf.createEntityManager();
			String q = "SELECT fstaff_id,fstaff_code_disp,fstaff_name,fstaff_display_name,div_disp_nm,fstaff_level_code,fstaff_leaving_date "
					+ "FROM [v_g_FIELDSTAFF_JAVA] WHERE Fstaff_status='A' ORDER BY fstaff_id";
			Query query = em.createNativeQuery(q, Tuple.class);
			// query.setParameter("status", status);

			List<Tuple> tuples = query.getResultList();
			if (tuples != null && !tuples.isEmpty()) {
				for (Tuple t : tuples) {
					V_G_FieldStaff vfs = new V_G_FieldStaff();
					vfs.setFstaff_id(Long.valueOf(t.get("fstaff_id", Integer.class)));
					vfs.setFstaff_code_disp(t.get("fstaff_code_disp", String.class));
					vfs.setFstaff_name(t.get("fstaff_name", String.class));
					vfs.setFstaff_display_name(t.get("fstaff_display_name", String.class));
					vfs.setDiv_disp_nm(t.get("div_disp_nm", String.class));
					vfs.setFstaff_level_code(t.get("fstaff_level_code", String.class));
					vfs.setFstaff_leaving_date(t.get("fstaff_leaving_date", Date.class));
					list.add(vfs);
				}
				if (!list.isEmpty() && list.size() > 0)
					return list;
			}
		} finally {
			if (em != null) {
				em.close();
			}
		}
		return list;
	}

	@Override
	public List<V_G_FieldStaff> getTextParameterFstaffDetail(String name, String parameter, String text, String empId) {
		System.out.println("detail : " + name + "...." + parameter + "..." + text);
		EntityManager em = null;
		List<V_G_FieldStaff> list = new ArrayList<V_G_FieldStaff>();
		try {
			em = emf.createEntityManager();
			Query query = null;
			StringBuffer sb = new StringBuffer();

			sb.append(
					" SELECT fstaff_id,fstaff_code_disp,fstaff_name,fstaff_display_name,VGF.div_disp_nm as div_disp_nm,fstaff_level_code,fstaff_leaving_date,fstaff_employee_no");
			sb.append(" FROM [v_g_FIELDSTAFF_JAVA] VGF");
			sb.append(" JOIN DIV_MASTER DV ON DV.DIV_ID=VGF.FSTAFF_DIV_ID");
			sb.append(" JOIN am_m_emp_div_access AME ON AME.ediv_div_id=DV.DIV_ID");
			sb.append(" WHERE Fstaff_status='A' ");

			if (name.equals("CD")) {
				sb.append(" and VGF.fstaff_code_disp " + parameter + " '%" + text + "%' ");
			}
			if (name.equals("NM")) {
				System.out.println("detail : " + name + "...." + parameter + "..." + text);
				sb.append(" and VGF.fstaff_name " + parameter + " '%" + text + "%' ");
			}
			if (name.equals("DN")) {
				sb.append(" and VGF.fstaff_display_name " + parameter + " '%" + text + "%' ");
			}
			if (name.equals("DIV")) {
				sb.append(" and VGF.DIV_DISP_NM " + parameter + " '%" + text + "%' ");
			}
			if (name.equals("LC")) {
				sb.append(" and VGF.fstaff_level_code " + parameter + " '%" + text + "%' ");
			}
			if (name.equals("EN")) {
				sb.append(" and VGF.fstaff_employee_no " + parameter + " '%" + text + "%' ");
			}
			if (name.equals("UI")) {
				sb.append(" and VGF.fstaff_id " + parameter + " '%" + text + "%'  ");
			}
			sb.append(" and VGF.fs_category not in('E','D') ");
			sb.append(" AND EDIV_EMP_ID ='" + empId + "' ");
			sb.append(" ORDER BY fstaff_id");
			query = em.createNativeQuery(sb.toString(), Tuple.class);

			List<Tuple> tuples = query.getResultList();
			if (tuples != null && !tuples.isEmpty()) {
				for (Tuple t : tuples) {
					V_G_FieldStaff vfs = new V_G_FieldStaff();
					vfs.setFstaff_id(Long.valueOf(t.get("fstaff_id", Integer.class)));
					vfs.setFstaff_code_disp(t.get("fstaff_code_disp", String.class));
					vfs.setFstaff_name(t.get("fstaff_name", String.class));
					vfs.setFstaff_display_name(t.get("fstaff_display_name", String.class));
					vfs.setDiv_disp_nm(t.get("div_disp_nm", String.class));
					vfs.setFstaff_level_code(t.get("fstaff_level_code", String.class));
					vfs.setFstaff_leaving_date(t.get("fstaff_leaving_date", Date.class));
					vfs.setFstaff_employee_no(t.get("fstaff_employee_no", String.class));
					list.add(vfs);
				}
				if (!list.isEmpty() && list.size() > 0)
					return list;
			}
		} finally {
			if (em != null) {
				em.close();
			}
		}
		return list;
	}

	@Override
	public List<FieldStaff> getFstaffDetailById(String id) {
		EntityManager em = null;
		List<FieldStaff> list = new ArrayList<FieldStaff>();
		System.out.println("selected fstaff id : " + id);
		try {
			em = emf.createEntityManager();
			Query query = null;

			String q = "from FieldStaff WHERE fstaff_id =:id";
			query = em.createQuery(q);
			query.setParameter("id", Long.valueOf(id));
			list = query.getResultList();
			System.out.println(list.get(0).getFstaff_name());
			System.out.println(list.get(0).getFstaff_code());
		} finally {
			if (em != null) {
				em.close();
			}
		}
		return list;
	}

	@Override
	public List<FieldStaff> getFieldStaffByDivAndLevelcode(Long divId, String levelCode, String category)
			throws Exception {
		List<FieldStaff> result = null;
		EntityManager em = null;
		try {
			em = emf.createEntityManager();
			CriteriaBuilder builder = em.getCriteriaBuilder();
			CriteriaQuery<FieldStaff> criteriaQuery = builder.createQuery(FieldStaff.class);
			Root<FieldStaff> root = criteriaQuery.from(FieldStaff.class);
			criteriaQuery.multiselect(root.get(FieldStaff_.fstaff_id), root.get(FieldStaff_.fstaff_display_name))
					.where(builder.and(builder.equal(root.get(FieldStaff_.fs_category), category),
							builder.equal(root.get(FieldStaff_.level_code), levelCode),
							builder.equal(root.get(FieldStaff_.fs_div_id), divId),
							builder.equal(root.get(FieldStaff_.status), "A"),
							builder.isNull(root.get(FieldStaff_.leaving_date))))
					.orderBy(builder.asc(root.get(FieldStaff_.fstaff_display_name)));
			result = em.createQuery(criteriaQuery).getResultList();
			;
			return result;
		} finally {
			if (em != null) {
				em.close();
			}
		}

	}

	@Override
	public List<FieldStaffListNew> getFieldStaffListNew(String emp_id, Long div_id, String allocType) throws Exception {
		EntityManager em = null;
		List<FieldStaffListNew> list = null;
		try {
			System.out.println("div_id " + div_id);
			em = emf.createEntityManager();
			StoredProcedureQuery query = em.createNamedStoredProcedureQuery("callFieldStaffListNew");
			query.setParameter("pvemp_id", emp_id);
			query.setParameter("pidiv_id", div_id);
			query.setParameter("PALLOC_TYPE", allocType);
			list = query.getResultList();
		} finally {
			if (em != null) {
				em.close();
			}
		}
		return list;
	}

	@Override
	public List<FieldStaff> getNotCfaLinkFieldstaff(Long divId) {
		EntityManager em = null;
		List<FieldStaff> list = null;
		try {
			StringBuffer sb = new StringBuffer();
			sb.append(
					" SELECT  FM.FSTAFF_ID,  FM.FSTAFF_CODE,  FM.FSTAFF_NAME, FM.FSTAFF_EMPLOYEE_NO, coalesce(FM.FSTAFF_TERRNAME,'') FSTAFF_TERRNAME,  ");
			sb.append(
					" FM.SubComp_id, FM.SubComp_Nm filed_company,  FD.FSTAFFD_SubComp_id, SC1.SubComp_Nm subcompany  ");
			sb.append(
					" FROM  ( SELECT  FM.FSTAFF_ID, FM.FSTAFF_CODE, FM.FSTAFF_NAME, FM.FSTAFF_EMPLOYEE_NO,FM.FSTAFF_TERRNAME,SC1.SubComp_id, ");
			sb.append(
					" SC1.SubComp_Nm FROM  Sub_Company SC1 ,FIELDSTAFF FM  where FM.FSTAFF_DIV_ID=:divId AND SC1.SubComp_status='A' AND FM.FSTAFF_SAMP_DISP_IND='Y')  FM  ");
			sb.append(
					" LEFT OUTER JOIN FIELDSTAFF_Detail FD ON  FM.FSTAFF_ID    = FD.FSTAFFD_FSTAFF_ID	AND FM.SubComp_id = FD.FSTAFFD_SubComp_id  ");
			sb.append(
					" LEFT OUTER JOIN Sub_Company SC1	ON  FD.FSTAFFD_SubComp_id = SC1.SubComp_id WHERE  (FD.FSTAFFD_SubComp_id IS NULL OR FSTAFFD_LOC_ID=0)  ");
			sb.append(" ORDER BY  FM.FSTAFF_ID,  FM.SubComp_ID  ");
			Query query = entityManager.createNativeQuery(sb.toString(), Tuple.class);
			query.setParameter("divId", divId);
			List<Tuple> tuples = query.getResultList();
			if (tuples != null && !tuples.isEmpty()) {
				list = new ArrayList<>();
				FieldStaff object = null;
				for (Tuple t : tuples) {
					object = new FieldStaff();
					object.setFstaff_code(t.get("FSTAFF_CODE", String.class));
					object.setFstaff_name(t.get("FSTAFF_NAME", String.class));
					object.setFstaff_terrname(t.get("FSTAFF_TERRNAME", String.class));
					object.setCompany(t.get("subcompany", String.class));
					list.add(object);
				}
				if (!list.isEmpty() && list.size() > 0)
					return list;

			}

		} finally {
			if (em != null) {
				em.close();
			}
		}
		return list;
	}

	@Override
	public List<FieldStaff> getFieldStaffByEmployeeNumber(String fstaff_emp_num, Long fs_div_id) throws Exception {
		List<FieldStaff> result = null;
		EntityManager em = null;
		try {
			// em = emf.createEntityManager();
			CriteriaBuilder builder = entityManager.getCriteriaBuilder();
			CriteriaQuery<FieldStaff> criteriaQuery = builder.createQuery(FieldStaff.class);
			Root<FieldStaff> root = criteriaQuery.from(FieldStaff.class);
			criteriaQuery.select(root)
					.where(builder.and(builder.equal(root.get(FieldStaff_.fstaff_emp_num), fstaff_emp_num),
							builder.equal(root.get(FieldStaff_.fs_div_id), fs_div_id),
							builder.equal(root.get(FieldStaff_.status), "A")))
					.orderBy(builder.asc(root.get(FieldStaff_.fstaff_display_name)));
			result = entityManager.createQuery(criteriaQuery).getResultList();
			return result;
		} finally {
			if (em != null) {
				em.close();
			}
		}
	}

	@Override
	public List<FieldStaff> getFieldStaffByTerrCode(String terrCode) throws Exception {
		List<FieldStaff> result = null;
		EntityManager em = null;
		try {
			// em = emf.createEntityManager();
			CriteriaBuilder builder = entityManager.getCriteriaBuilder();
			CriteriaQuery<FieldStaff> criteriaQuery = builder.createQuery(FieldStaff.class);
			Root<FieldStaff> root = criteriaQuery.from(FieldStaff.class);
			criteriaQuery.select(root)
					.where(builder.and(builder.equal(root.get(FieldStaff_.fstaff_terr_code), terrCode),
							builder.equal(root.get(FieldStaff_.status), "A")))
					.orderBy(builder.asc(root.get(FieldStaff_.fstaff_display_name)));
			result = entityManager.createQuery(criteriaQuery).getResultList();
			return result;
		} finally {
			if (em != null) {
				em.close();
			}
		}
	}

//P&G
	@Override
	public List<Object> getNoOfStaffByStaffwiseTraining(String lvlCode, String divId, String status, String allocMode,
			String cfalocId, String dist, String fstaffType, String alloc_id, String period_code, String alloc_cycle,
			String prodtype, String fin_year, String company, String sub_team_code, String alloc_type) {
		EntityManager em = null;
		List<Object> resultList = null;
		Long count = 0L;
		try {
			if (allocMode.equals("0")) {
				System.out.println("IN IF");
				Query query = entityManager.createNativeQuery(this
						.getStaffCountQuery(lvlCode, cfalocId, divId, status, dist, allocMode, fstaffType, alloc_id,
								period_code, alloc_cycle, prodtype, fin_year, company, sub_team_code, alloc_type)
						.toString(), Tuple.class);
				List<Tuple> tuples = query.getResultList();
				List<Long> countList = query.getResultList();
				if (countList.size() > 0) {
					Number number = countList.get(0);
					count = number.longValue();
					System.out.println(count);
				}

			} else if (allocMode != null) {
				System.out.println("IN ELSE");
				System.out.println("divId " + divId);
				System.out.println("lvlCode " + lvlCode);
				System.out.println("sub_team_code " + sub_team_code);
				System.out.println("alloc_type " + alloc_type);
				/*
				 * if (alloc_cycle != null) { detachedCriteria = DetachedCriteria
				 * .forClass(Alloc_gen_ent.class);
				 * detachedCriteria.add(Restrictions.eq("fin_year", fin_year));
				 * detachedCriteria.add(Restrictions.eq("alloc_cycle",
				 * Long.parseLong(alloc_cycle)));
				 * detachedCriteria.add(Restrictions.eq("company", company));
				 * detachedCriteria.add(Restrictions.eq("product_type",
				 * prodtype.split("_")[1])); detachedCriteria.add(Restrictions.eq("period_code",
				 * period_code)); detachedCriteria.add(Restrictions.eq("fin_year", fin_year)); }
				 */

				Long cfa_locId = Long.valueOf(cfalocId);
				CriteriaBuilder builder = entityManager.getCriteriaBuilder();
				CriteriaQuery<Object> criteriaQuery = builder.createQuery(Object.class);
				Root<FieldStaff> root = criteriaQuery.from(FieldStaff.class);
				List<Predicate> subqueryPerdicates = new ArrayList<>();
				if (dist.equalsIgnoreCase("fstaff_id")) {
					subqueryPerdicates.add(builder.and(builder.equal(root.get(FieldStaff_.fs_div_id), divId),
							builder.equal(root.get(FieldStaff_.level_code), lvlCode),
							builder.equal(root.get(FieldStaff_.fstaff_samp_disp_ind), "Y"),
							builder.equal(root.get(FieldStaff_.status), "A"),
							builder.isNull(root.get(FieldStaff_.leaving_date)),
							builder.equal(root.get(FieldStaff_.team_code), sub_team_code),
							builder.equal(root.get(FieldStaff_.fs_category), alloc_type)));
					criteriaQuery
							.multiselect(builder.count(root.get(FieldStaff_.fstaff_id)),
									root.get(FieldStaff_.fstaff_training))
							.groupBy(root.get(FieldStaff_.fstaff_training))
							.where(subqueryPerdicates.toArray(new Predicate[] {}));

				} else if (dist.equalsIgnoreCase("fstaff_mgr1_id")) {
					subqueryPerdicates.add(builder.and(builder.equal(root.get(FieldStaff_.fs_div_id), divId),
							builder.equal(root.get(FieldStaff_.level_code), lvlCode),
							builder.equal(root.get(FieldStaff_.fstaff_samp_disp_ind), "Y"),
							builder.equal(root.get(FieldStaff_.status), "A"),
							builder.isNull(root.get(FieldStaff_.leaving_date)),
							builder.equal(root.get(FieldStaff_.fs_category), alloc_type)));

					criteriaQuery
							.multiselect(builder.count(root.get(FieldStaff_.fstaff_mgr1_id)),
									root.get(FieldStaff_.fstaff_training))
							.groupBy(root.get(FieldStaff_.fstaff_training))
							.where(subqueryPerdicates.toArray(new Predicate[] {}));
				} else if (dist.equalsIgnoreCase("fstaff_mgr2_id")) {
					subqueryPerdicates.add(builder.and(builder.equal(root.get(FieldStaff_.fs_div_id), divId),
							builder.equal(root.get(FieldStaff_.level_code), lvlCode),
							builder.equal(root.get(FieldStaff_.fstaff_samp_disp_ind), "Y"),
							builder.equal(root.get(FieldStaff_.status), "A"),
							builder.isNull(root.get(FieldStaff_.leaving_date)),
							builder.equal(root.get(FieldStaff_.fs_category), alloc_type)));
					criteriaQuery
							.multiselect(builder.count(root.get(FieldStaff_.fstaff_mgr2_id)),
									root.get(FieldStaff_.fstaff_training))
							.groupBy(root.get(FieldStaff_.fstaff_training))
							.where(subqueryPerdicates.toArray(new Predicate[] {}));
				}
				/*
				 * if (status != null) {
				 * System.out.println("FieldStaffDao.getNoOfStaffByStaffwise() 147"); if
				 * (!status.equalsIgnoreCase("A")) {
				 * System.out.println("FieldStaffDao.getNoOfStaffByStaffwise() 150");
				 * 
				 * builder.equal(root.get(FieldStaff_.fstaff_dom_exp), divId); } } if
				 * (allocMode.equalsIgnoreCase("1")) { if (cfa_locId == 0) { if
				 * (!alloc_id.equals("0")) { detachedCriteria.setProjection(Projections
				 * .distinct(Projections.property("zone_id")));
				 * ctr.add(Subqueries.propertyNotIn("fstaff_zone_id", detachedCriteria)); }
				 * 
				 * } else builder.equal(root.get(FieldStaff_.fstaff_zone_id), divId);
				 * 
				 * } else if (allocMode.equalsIgnoreCase("2")) { if (cfa_locId == 0) { if
				 * (!alloc_id.equals("0")) {
				 * System.out.println("FieldStaffDao.getNoOfStaffByStaffwise()"+ alloc_id);
				 * detachedCriteria.setProjection(Projections .distinct(Projections
				 * .property("state_id"))); ctr.add(Subqueries.propertyNotIn("fstaff_state_id",
				 * detachedCriteria)); } } else
				 * builder.equal(root.get(FieldStaff_.fstaff_state_id), divId); } else if
				 * (allocMode.equalsIgnoreCase("3")) { if (cfa_locId == 0) { if
				 * (!alloc_id.equals("0")) {
				 * detachedCriteria.setProjection(Projections.distinct(Projections.property(
				 * "rbm_id")));
				 * ctr.add(Subqueries.propertyNotIn("fstaff_mgr2_id",detachedCriteria)); } }
				 * else builder.equal(root.get(FieldStaff_.fstaff_mgr2_id), divId); } else if
				 * (allocMode.equalsIgnoreCase("4")) { if (cfa_locId == 0) { if
				 * (!alloc_id.equals("0")) {
				 * detachedCriteria.setProjection(Projections.distinct(Projections.property(
				 * "abm_id")));
				 * ctr.add(Subqueries.propertyNotIn("fstaff_mgr1_id",detachedCriteria)); } }
				 * else builder.equal(root.get(FieldStaff_.fstaff_mgr1_id), divId); } else if
				 * (allocMode.equalsIgnoreCase("5")) { if (cfa_locId == 0) { if
				 * (!alloc_id.equals("0")) {
				 * detachedCriteria.setProjection(Projections.distinct(Projections.property(
				 * "msr_id"))); ctr.add(Subqueries.propertyNotIn("fstaff_id",detachedCriteria));
				 * } } else builder.equal(root.get(FieldStaff_.fstaff_id), divId); }
				 * 
				 * if (!fstaffType.equals("A")) { if (!fstaffType.equals("T"))
				 * builder.equal(root.get(FieldStaff_.fstaff_training), divId); }
				 */

				/*
				 * builder.setProjection(Projections .projectionList()
				 * .add(Projections.count(dist))
				 * .add(Projections.groupProperty("fstaff_training"), "fstaff_training"));
				 * 
				 * resultList = em.createQuery(criteriaQuery).getResultList();
				 */

				resultList = entityManager.createQuery(criteriaQuery).getResultList();
				System.out.println("resultList " + resultList.size());
			}

		} catch (Exception ex) {
			System.out.println("Error Occurred :" + new CodifyErrors().getErrorCode(ex));// uncomment asneeded --
																							// System.out.println("Error
																							// Occurred :" + new
																							// CodifyErrors().getErrorCode(e));//
																							// uncomment asneeded --;
		} finally {
			if (em != null) {
				em.close();
			}
		}
		return resultList;

	}

	public StringBuffer getStaffCountQuery(String lvlCode, String cfalocId, String divId, String status, String dist,
			String allocMode, String fstaffType, String alloc_id, String period_code, String alloc_cycle,
			String prodtype, String fin_year, String comapny, String sub_team_code, String alloc_type) {
		StringBuffer buffer = new StringBuffer("SELECT COUNT(DISTINCT ").append(dist).append(") ");
		buffer.append(" FROM  FIELDSTAFF this_ WHERE");
		if (!allocMode.equals("-1")) {
			buffer.append("  this_.FSTAFF_ID in");
			buffer.append(" ( select distinct fd.FSTAFFD_FSTAFF_ID from FIELDSTAFF_Detail fd , DEPOT_LOCATIONS d");
			buffer.append(" where fd.FSTAFFD_LOC_ID = d.DPTLOC_ID ");
			if (!cfalocId.equals("0"))
				buffer.append(" and d.DPTDESTINATION_ID =").append(cfalocId);
			else if (!alloc_id.equals("0")) {
				buffer.append(" and DPTDESTINATION_ID not in(select distinct cfa_destination_id")
						.append(" from alloc_gen_ent where div_id=").append(divId).append(" and period_code=")
						.append(period_code).append(" and alloc_cycle=").append(alloc_cycle)
						.append(" and product_type='").append(prodtype.split("_")[1]).append("' and fin_year=")
						.append(fin_year).append(" and company='").append(comapny).append("')");
			}

			buffer.append(")  AND ");
		}
		buffer.append("  this_.FSTAFF_status='A'");
		buffer.append(" AND this_.FSTAFF_SAMP_DISP_IND='Y'");
		buffer.append(" AND this_.FS_CATEGORY='" + alloc_type + "'");
		if (dist.equalsIgnoreCase("fstaff_id") || !sub_team_code.trim().equalsIgnoreCase("0")) {
			buffer.append(" AND this_.TEAM_CODE='").append(sub_team_code).append("'");
		}
		buffer.append(" AND this_.FSTAFF_LEAVING_DATE is null ");
		buffer.append(" AND this_.FSTAFF_DIV_ID =").append(divId);
		// asked by pawaskar 9-Aug-2017for training staff do not consider level code
		if (lvlCode != null) {
			buffer.append(" AND this_.FSTAFF_LEVEL_CODE='").append(lvlCode).append("'");
		}

		if (status != null)
			if (!status.equals("A"))
				buffer.append(" AND fstaff_dom_exp='").append(status).append("'");
		if (!fstaffType.equals("T")) {
			buffer.append(" AND fstaff_training <>'T'");
		} else {
			buffer.append(" AND fstaff_training ='T'");

		}
		System.out.println("FieldStaffDao.getStaffCountQuery()");
		return buffer;
	}

	@Override
	public List<Object> getZone(String divId, String status, String period_code, String fin_year, String company,
			String saveMode, String planType, String alloc_id, String alloc_cycle, String prodtype, List<String> brands,
			String sub_team_code, String alloc_type) {
		EntityManager em = null;
		List<Object> result = null;
		try {
			System.out.println("FieldStaffDao.getZone()" + divId + "  ");

			StringBuffer sb = new StringBuffer();
			sb.append(
					"select SGprmdet_id,SGprmdet_disp_nm from sg_d_parameters_details where SGprmdet_SGprm_id = 13 and SGprmdet_id in");
			sb.append("(select distinct  fstaff_zone_id  from FIELDSTAFF where fstaff_div_id=:divId")
					.append(" and FSTAFF_SAMP_DISP_IND='Y' ");
			sb.append(" AND this_.FS_CATEGORY='" + alloc_type + "'");
			if (!sub_team_code.equals("All")) {
				sb.append(" and TEAM_CODE=:sub_team_code");
			}
			sb.append(" and fstaff_status='A'");
			sb.append(" AND FSTAFF_LEAVING_DATE is null ");
			if (!status.equalsIgnoreCase("A")) {
				sb.append(" and FSTAFF_DOM_EXP=:status ");
			}
			sb.append(")");

			if (!alloc_id.equals("0")) {
				sb.append("	and SGprmdet_id NOT In(  SELECT DISTINCT zone_id  FROM  alloc_gen_ent");
				sb.append(" WHERE  div_id =:divId").append(" and  period_code=:period_code");
				sb.append(" and  alloc_cycle=:alloc_cycle");
				sb.append(" and alloc_gen_id=:alloc_id");
				sb.append(" and  product_type=:prodtype");
				sb.append(" and  fin_year=:fin_year")
						.append("  and  company=:company and alloc_smp_sa_prod_group_id in(:brands))");
			}
			Query query = entityManager.createNativeQuery(sb.toString(), Tuple.class);
			query.setParameter("divId", divId);
			if (!sub_team_code.equals("All")) {
				query.setParameter("sub_team_code", sub_team_code);
			}
			if (!status.equalsIgnoreCase("A")) {
				query.setParameter("status", status);
			}
			if (!alloc_id.equals("0")) {
				query.setParameter("period_code", period_code);
				query.setParameter("alloc_cycle", alloc_cycle);
				query.setParameter("alloc_id", alloc_id);
				query.setParameter("prodtype", planType.split("_")[1]);
				query.setParameter("fin_year", fin_year);
				query.setParameter("company", company);
				query.setParameter("brands", brands);
			}
			List<Tuple> tuples = query.getResultList();
			if (tuples != null && !tuples.isEmpty()) {
				result = new ArrayList<>();
				AllocationBean object = null;
				for (Tuple t : tuples) {
					object = new AllocationBean();
					object.setAllocationById(Long.valueOf(t.get("SGprmdet_id", Integer.class)));
					object.setAllocationByDesc(t.get("SGprmdet_disp_nm", String.class));
					result.add(object);
				}
			}
			if (!result.isEmpty() && result.size() > 0) {
				return result;
			}

		} catch (Exception ex) {
			System.out.println("Error Occurred :" + new CodifyErrors().getErrorCode(ex));// uncomment asneeded --
																							// System.out.println("Error
																							// Occurred :" + new
																							// CodifyErrors().getErrorCode(e));//
																							// uncomment asneeded --;
		} finally {
			if (em != null) {
				em.close();
			}
		}
		return result;
	}

	@Override
	public List<Object> getState(String divId, String status, String period_code, String fin_year, String company,
			String saveMode, String planType, String alloc_id, String alloc_cycle, String prodtype, List<String> brands,
			String sub_team_code, String alloc_type) {
		EntityManager em = null;
		List<Object> result = null;
		try {
			System.out.println("FieldStaffDao.getZone()" + divId + "  ");

			StringBuffer sb = new StringBuffer();
			sb.append(
					"select SGprmdet_id,SGprmdet_disp_nm from sg_d_parameters_details where SGprmdet_SGprm_id = 2 and SGprmdet_id in");
			sb.append("(select distinct  FSTAFF_STATE_ID  from FIELDSTAFF where fstaff_div_id=:divId")
					.append(" and FSTAFF_SAMP_DISP_IND='Y' ");
			sb.append(" AND this_.FS_CATEGORY='" + alloc_type + "'");
			if (!sub_team_code.equals("All")) {
				sb.append(" and TEAM_CODE=:sub_team_code");
			}
			sb.append(" and fstaff_status='A'");
			sb.append(" AND FSTAFF_LEAVING_DATE is null ");
			if (!status.equalsIgnoreCase("A")) {
				sb.append(" and FSTAFF_DOM_EXP=:status");
			}
			sb.append(")");

			if (!alloc_id.equals("0")) {
				sb.append("	and SGprmdet_id NOT In(  SELECT DISTINCT state_id  FROM  alloc_gen_ent");
				sb.append(" WHERE  div_id =:divId").append(" and  period_code=:period_code");
				sb.append(" and  alloc_cycle=:alloc_cycle");
				sb.append(" and alloc_gen_id=:alloc_id");
				sb.append(" and  product_type=:prodtype");
				sb.append(" and  fin_year=:fin_year ")
						.append("  and  company=:company and alloc_smp_sa_prod_group_id in(:brands))");
			}

			em = emf.createEntityManager();
			Query query = em.createNativeQuery(sb.toString(), Tuple.class);
			query.setParameter("divId", divId);
			if (!sub_team_code.equals("All")) {
				query.setParameter("sub_team_code", sub_team_code);
			}
			if (!status.equalsIgnoreCase("A")) {
				query.setParameter("status", status);
			}
			if (!alloc_id.equals("0")) {
				query.setParameter("period_code", period_code);
				query.setParameter("alloc_cycle", alloc_cycle);
				query.setParameter("alloc_id", alloc_id);
				query.setParameter("prodtype", planType.split("_")[1]);
				query.setParameter("fin_year", fin_year);
				query.setParameter("company", company);
				query.setParameter("brands", brands);
			}
			List<Tuple> tuples = query.getResultList();
			if (tuples != null && !tuples.isEmpty()) {
				result = new ArrayList<>();
				AllocationBean object = null;
				for (Tuple t : tuples) {
					object = new AllocationBean();
					object.setAllocationById(Long.valueOf(t.get("SGprmdet_id", Integer.class)));
					object.setAllocationByDesc(t.get("SGprmdet_disp_nm", String.class));
					result.add(object);
				}
			}
			System.out.println("getState" + result.size());
			if (!result.isEmpty() && result.size() > 0) {
				return result;
			}

		} finally {
			if (em != null) {
				em.close();
			}
		}
		return result;
	}

	@Override
	public List<Object> getStaffDetails(String divId, String status, String lvlCode, String saveMode, String alloctype,
			String alloc_id, String alloc_cycle, String period_code, String fin_year, String company, String prodtype,
			String fieldtype, List<String> brands, String sub_team_code, String alloc_type) {
		System.out.println("FieldStaffDao.getStaffDetails()*******" + fieldtype + "_" + divId + "_" + lvlCode + "-"
				+ alloc_id + " " + prodtype);
		List<FieldStaff> list = null;
		List<Object> objectList = null;
		EntityManager em = null;
		try {
			CriteriaBuilder builder = entityManager.getCriteriaBuilder();
			CriteriaQuery<FieldStaff> criteriaQuery = builder.createQuery(FieldStaff.class);
			Root<FieldStaff> root = criteriaQuery.from(FieldStaff.class);
			List<Predicate> predicates = new ArrayList<>();

			Subquery<Long> subquery = criteriaQuery.subquery(Long.class);
			Root<Alloc_gen_ent> subqueryRoot = subquery.from(Alloc_gen_ent.class);
			List<Predicate> subqueryPerdicates = new ArrayList<>();
			subqueryPerdicates.add(builder.equal(subqueryRoot.get(Alloc_gen_ent_.div_id), divId));
			subqueryPerdicates.add(builder.equal(subqueryRoot.get(Alloc_gen_ent_.period_code), period_code));
			subqueryPerdicates.add(builder.equal(subqueryRoot.get(Alloc_gen_ent_.fin_year), fin_year));
			subqueryPerdicates.add(builder.equal(subqueryRoot.get(Alloc_gen_ent_.company), company));
			subqueryPerdicates.add(builder.equal(subqueryRoot.get(Alloc_gen_ent_.product_type), prodtype));
			subqueryPerdicates.add(builder.equal(subqueryRoot.get(Alloc_gen_ent_.alloc_cycle), alloc_cycle));
			subqueryPerdicates.add(builder.and(subqueryRoot.get(Alloc_gen_ent_.alloc_smp_sa_prod_group_id).in(brands)));

			predicates.add(builder.equal(root.get(FieldStaff_.fstaff_samp_disp_ind), "Y"));
			predicates.add(builder.isNull(root.get(FieldStaff_.leaving_date)));
			predicates.add(builder.equal(root.get(FieldStaff_.status), "A"));
			predicates.add(builder.equal(root.get(FieldStaff_.fs_div_id), divId));
			predicates.add(builder.equal(root.get(FieldStaff_.team_code), sub_team_code));
			predicates.add(builder.equal(root.get(FieldStaff_.fs_category), alloc_type));

			if (lvlCode.equalsIgnoreCase("004")) {

				predicates.add(builder.equal(root.get(FieldStaff_.level_code), "002"));
				subqueryPerdicates.add(builder.greaterThan(subqueryRoot.get(Alloc_gen_ent_.abm_id), 0l));
				subquery.select(subqueryRoot.get(Alloc_gen_ent_.abm_id)).distinct(true)
						.where(subqueryPerdicates.toArray(new Predicate[] {}));

			} else if (lvlCode.equalsIgnoreCase("005")) {
				predicates.add(builder.equal(root.get(FieldStaff_.level_code), "001"));
				subqueryPerdicates.add(builder.greaterThan(subqueryRoot.get(Alloc_gen_ent_.msr_id), 0l));
				subquery.select(subqueryRoot.get(Alloc_gen_ent_.msr_id)).distinct(true)
						.where(subqueryPerdicates.toArray(new Predicate[] {}));

			} else {
				predicates.add(builder.equal(root.get(FieldStaff_.level_code), lvlCode));
				subqueryPerdicates.add(builder.greaterThan(subqueryRoot.get(Alloc_gen_ent_.rbm_id), 0l));
				subquery.select(subqueryRoot.get(Alloc_gen_ent_.rbm_id)).distinct(true)
						.where(subqueryPerdicates.toArray(new Predicate[] {}));
			}
			if (!status.equalsIgnoreCase("A") && lvlCode.equalsIgnoreCase("001")) {
				///// Level code except 001 has no team attached.asked by Pawaskar sir.
				predicates.add(builder.equal(root.get(FieldStaff_.fstaff_dom_exp), status));
			}
			if (fieldtype.equalsIgnoreCase("T"))
				predicates.add(builder.equal(root.get(FieldStaff_.fstaff_training), fieldtype));
			else
				predicates.add(builder.notEqual(root.get(FieldStaff_.fstaff_training), "T"));

			if (!alloc_id.equals("0")) {
				subqueryPerdicates.add(builder.equal(subqueryRoot.get(Alloc_gen_ent_.alloc_gen_id), alloc_id));
				predicates.add(builder.in(root.get(FieldStaff_.fstaff_id)).value(subquery).not());
			}

			criteriaQuery
					.multiselect(root.get(FieldStaff_.fstaff_id), root.get(FieldStaff_.fstaff_display_name),
							root.get(FieldStaff_.fstaff_name), root.get(FieldStaff_.fstaff_terrname))
					.distinct(true).where((predicates.toArray(new Predicate[] {})))
					.orderBy(builder.asc(root.get(FieldStaff_.fstaff_display_name)));

			TypedQuery<FieldStaff> fieldstaff = entityManager.createQuery(criteriaQuery);

			list = fieldstaff.getResultList();

			if (list != null && !list.isEmpty()) {
				objectList = new ArrayList<>();
				AllocationBean object = null;
				for (FieldStaff f : list) {
					object = new AllocationBean();
					object.setAllocationById(Long.valueOf(f.getFstaff_id()));
					object.setAllocationByDesc(f.getFstaff_display_name());
					objectList.add(object);
				}
			}

			// count = list.size() ctr.addOrder(Order.asc("fstaff_display_name"));;

		} catch (Exception ex) {
			System.out.println("Error Occurred :" + new CodifyErrors().getErrorCode(ex));// uncomment asneeded --
																							// System.out.println("Error
																							// Occurred :" + new
																							// CodifyErrors().getErrorCode(e));//
																							// uncomment asneeded --;
		} finally {
			if (em != null) {
				em.close();
			}
		}
		return objectList;

	}

	@Override
	public Long getNoOfStaffByStaffwise(String lvlCode, String divId, String status, String allocMode, String cfalocId,
			String dist, String fstaffType, String alloc_id, String period_code, String alloc_cycle, String prodtype,
			String fin_year, String company, String sub_team_code, String alloc_type) {

		Long count = 0L;
		EntityManager em = null;
		System.out.println("Printing --------lvlCode::::::" + lvlCode);
		System.out.println("Printing --------divId::::::" + divId);
		System.out.println("Printing --------status::::::" + status);
		System.out.println("Printing --------allocMode::::::" + allocMode);
		System.out.println("Printing --------cfalocId::::::" + cfalocId);
		System.out.println("Printing --------dist::::::" + dist);
		System.out.println("Printing --------fstaffType::::::" + fstaffType);
		System.out.println("Printing --------alloc_id::::::" + alloc_id);
		System.out.println("Printing --------period_code::::::" + period_code);
		System.out.println("Printing --------alloc_cycle::::::" + alloc_cycle);
		System.out.println("Printing --------prodtype::::::" + prodtype);
		System.out.println("Printing --------fin_year::::::" + fin_year);
		System.out.println("Printing --------company::::::" + company);

		try {
			CriteriaBuilder builder = entityManager.getCriteriaBuilder();
			CriteriaQuery<Long> criteriaQuery = builder.createQuery(Long.class);
			Root<FieldStaff> root = criteriaQuery.from(FieldStaff.class);
			List<Predicate> predicates = new ArrayList<>();

			Subquery<Long> subquery = criteriaQuery.subquery(Long.class);
			Root<Alloc_gen_ent> subqueryRoot = subquery.from(Alloc_gen_ent.class);
			List<Predicate> subqueryPerdicates = new ArrayList<>();

			System.out.println("FieldStaffDao.getNoOfStaffByStaffwise()" + cfalocId + "," + divId + " ," + lvlCode
					+ ", " + fstaffType);
			if (allocMode.equals("0")) {
				Query query = entityManager.createNativeQuery(this
						.getStaffCountQuery(lvlCode, cfalocId, divId, status, dist, allocMode, fstaffType, alloc_id,
								period_code, alloc_cycle, prodtype, fin_year, company, sub_team_code, alloc_type)
						.toString());
				count = Long.valueOf(query.getSingleResult().toString());

			} else if (allocMode != null) {

				if (alloc_cycle != null) {
					subqueryPerdicates.add(builder.equal(subqueryRoot.get(Alloc_gen_ent_.fin_year), fin_year));
					if (!alloc_cycle.equals(""))
						subqueryPerdicates.add(builder.equal(subqueryRoot.get(Alloc_gen_ent_.alloc_cycle),
								Long.parseLong(alloc_cycle)));
					subqueryPerdicates.add(builder.equal(subqueryRoot.get(Alloc_gen_ent_.company), company));
					subqueryPerdicates
							.add(builder.equal(subqueryRoot.get(Alloc_gen_ent_.product_type), prodtype.split("_")[1]));
					subqueryPerdicates.add(builder.equal(subqueryRoot.get(Alloc_gen_ent_.period_code), period_code));
					subqueryPerdicates.add(builder.equal(subqueryRoot.get(Alloc_gen_ent_.company), company));
				}

				Long cfa_locId = Long.valueOf(cfalocId);

				predicates.add(builder.equal(root.get(FieldStaff_.fs_div_id), Long.parseLong(divId)));
				if (lvlCode != null) {
					predicates.add(builder.equal(root.get(FieldStaff_.level_code), lvlCode));
				}
				predicates.add(builder.equal(root.get(FieldStaff_.fstaff_samp_disp_ind), "Y"));
				predicates.add(builder.equal(root.get(FieldStaff_.status), "A"));
				predicates.add(builder.isNull(root.get(FieldStaff_.leaving_date)));
				predicates.add(builder.equal(root.get(FieldStaff_.fs_category), alloc_type));
				if (dist.equalsIgnoreCase("fstaff_id") || !sub_team_code.trim().equalsIgnoreCase("0")) {
					predicates.add(builder.equal(root.get(FieldStaff_.team_code), sub_team_code));
				}

				if (status != null) {
					if (!status.equalsIgnoreCase("A")) {
						predicates.add(builder.equal(root.get(FieldStaff_.fstaff_dom_exp), status));
					}
				}

				if (allocMode.equalsIgnoreCase("1")) {
					if (cfa_locId == 0) {
						if (!alloc_id.equals("0")) {
							subquery.select(subqueryRoot.get(Alloc_gen_ent_.zone_id)).distinct(true)
									.where(subqueryPerdicates.toArray(new Predicate[] {}));
							predicates.add(builder.not(root.get(FieldStaff_.fstaff_zone_id).in(subquery)));
						}

					} else
						predicates.add(builder.equal(root.get(FieldStaff_.fstaff_zone_id), cfa_locId));

				} else if (allocMode.equalsIgnoreCase("2")) {
					if (cfa_locId == 0) {
						if (!alloc_id.equals("0")) {
							System.out.println("FieldStaffDao.getNoOfStaffByStaffwise()" + alloc_id);
							subquery.select(subqueryRoot.get(Alloc_gen_ent_.state_id)).distinct(true)
									.where(subqueryPerdicates.toArray(new Predicate[] {}));
							predicates.add(builder.in(root.get(FieldStaff_.fstaff_state_id)).value(subquery).not());
						}
					} else
						predicates.add(builder.equal(root.get(FieldStaff_.fstaff_state_id), cfa_locId));
				} else if (allocMode.equalsIgnoreCase("3")) {
					if (cfa_locId == 0) {
						if (!alloc_id.equals("0")) {
							subquery.select(subqueryRoot.get(Alloc_gen_ent_.rbm_id)).distinct(true)
									.where(subqueryPerdicates.toArray(new Predicate[] {}));
							;
							predicates.add(builder.in(root.get(FieldStaff_.fstaff_mgr2_id)).value(subquery).not());
						}
					} else
						predicates.add(builder.equal(root.get(FieldStaff_.fstaff_mgr2_id), cfa_locId));
				} else if (allocMode.equalsIgnoreCase("4")) {
					if (cfa_locId == 0) {
						if (!alloc_id.equals("0")) {
							subquery.select(subqueryRoot.get(Alloc_gen_ent_.abm_id)).distinct(true)
									.where(subqueryPerdicates.toArray(new Predicate[] {}));
							;
							predicates.add(builder.in(root.get(FieldStaff_.fstaff_mgr1_id)).value(subquery).not());
						}
					} else
						predicates.add(builder.equal(root.get(FieldStaff_.fstaff_mgr1_id), cfa_locId));
				} else if (allocMode.equalsIgnoreCase("5")) {
					if (cfa_locId == 0) {
						if (!alloc_id.equals("0")) {
							subquery.select(subqueryRoot.get(Alloc_gen_ent_.msr_id)).distinct(true)
									.where(subqueryPerdicates.toArray(new Predicate[] {}));
							;
							predicates.add(builder.in(root.get(FieldStaff_.fstaff_id)).value(subquery).not());
						}
					} else
						predicates.add(builder.equal(root.get(FieldStaff_.fstaff_id), cfa_locId));
				}
				if (!fstaffType.equals("A")) {
					if (!fstaffType.equals("T"))
						predicates.add(builder.equal(root.get(FieldStaff_.fstaff_training), fstaffType));

				}
				if (dist.equalsIgnoreCase("fstaff_id"))
					criteriaQuery.select(builder.countDistinct(root.get(FieldStaff_.fstaff_id)))
							.where((predicates.toArray(new Predicate[] {})));
				else if (dist.equalsIgnoreCase("fstaff_mgr1_id"))
					criteriaQuery.select(builder.countDistinct(root.get(FieldStaff_.fstaff_mgr1_id))).distinct(true)
							.where((predicates.toArray(new Predicate[] {})));
				else if (dist.equalsIgnoreCase("fstaff_mgr2_id"))
					criteriaQuery.select(builder.countDistinct(root.get(FieldStaff_.fstaff_mgr2_id))).distinct(true)
							.where((predicates.toArray(new Predicate[] {})));

				TypedQuery<Long> q = entityManager.createQuery(criteriaQuery);

				count = q.getSingleResult();
				System.out.println("count --------" + count);
			}

		} catch (Exception ex) {
			System.out.println("Error Occurred :" + new CodifyErrors().getErrorCode(ex));// uncomment asneeded --
																							// System.out.println("Error
																							// Occurred :" + new
																							// CodifyErrors().getErrorCode(e));//
																							// uncomment asneeded --;
		} finally {
			if (em != null) {
				em.close();
			}
		}
		return count;

	}

	@Override
	public FieldStaff getFstaffDetailsFromDocId(Long doc_fstaff_id) throws Exception {
		EntityManager em = null;
		FieldStaff list = null;
		try {
			em = emf.createEntityManager();
			CriteriaBuilder builder = em.getCriteriaBuilder();
			CriteriaQuery<FieldStaff> criteriaQuery = builder.createQuery(FieldStaff.class);
			Root<FieldStaff> root = criteriaQuery.from(FieldStaff.class);
			criteriaQuery.select(root)
					.where(builder.and(builder.equal(root.get(FieldStaff_.fstaff_id), doc_fstaff_id)));
			list = em.createQuery(criteriaQuery).getSingleResult();
		} finally {
			if (em != null) {
				em.close();
			}
		}
		return list;
	}

	@Override
	public void updateFieldByTerritoryCode(String territorycode, String levelCode, String ipaddr, String usrid)
			throws Exception {
		// TODO Auto-generated method stub
		try {
			System.out.println("territorycode : " + territorycode + "LevelCode : " + levelCode);

			StringBuffer sb = new StringBuffer();
			if (!territorycode.equals(" ")) {
				Integer count = 0;
				try {
					sb.append(" select count(*) ");
					sb.append(" from FIELDSTAFF f , TERR_MASTER t , FIELDSTAFF fm");
					sb.append(" where f.fstaff_terr_id = t.terr_id AND t.TERR_STATUS = 'A'");
					sb.append(" AND f.fstaff_status = 'A' AND f.FSTAFF_LEVEL_CODE <:levelcode");
					if (levelCode.equals("002")) {
						sb.append(" AND t.TERR_MGR1_ID = fm.FSTAFF_TERR_ID AND fm.fstaff_status = 'A'");
					} else if (levelCode.equals("003")) {
						sb.append(" AND t.TERR_MGR2_ID = fm.FSTAFF_TERR_ID AND fm.fstaff_status = 'A'");
					}
					sb.append(" AND FM.FSTAFF_TERR_CODE = :terrcode");
					if (levelCode.equals("002")) {
						sb.append(" AND f.FSTAFF_MGR1_ID != fm.FSTAFF_ID");
					} else if (levelCode.equals("003")) {
						sb.append(" AND f.FSTAFF_MGR2_ID != fm.FSTAFF_ID");
					}
					Query q0 = entityManager.createNativeQuery(sb.toString());
					q0.setParameter("levelcode", levelCode);
					q0.setParameter("terrcode", territorycode);
					count = (Integer) q0.getSingleResult();
				} catch (Exception e) {
					e.printStackTrace();
					throw e;
				}

				if (count.compareTo(0) > 0) {
					if (levelCode.equals("002")) {

						sb = new StringBuffer();
						sb.append(" UPDATE f set f.fstaff_mgr1_id = fm.FSTAFF_ID,f.FSTAFF_mod_dt=:lastmodDt,");
						sb.append(" f.FSTAFF_mod_ip_add=:ipaddr,f.FSTAFF_mod_usr_id=:usrid");
						sb.append(" from FIELDSTAFF f , TERR_MASTER t , FIELDSTAFF fm");
						sb.append(" where f.fstaff_terr_id = t.terr_id AND t.TERR_STATUS = 'A'");
						sb.append(" AND f.fstaff_status = 'A' AND f.FSTAFF_LEVEL_CODE <:levelcode");
						sb.append(" AND t.TERR_MGR1_ID = fm.FSTAFF_TERR_ID AND fm.fstaff_status = 'A'");
						sb.append(" AND FM.FSTAFF_TERR_CODE = :terrcode");
						sb.append(" AND f.FSTAFF_MGR1_ID != fm.FSTAFF_ID");

						Query q = entityManager.createNativeQuery(sb.toString(), Tuple.class);
						q.setParameter("levelcode", levelCode);
						q.setParameter("terrcode", territorycode);
						q.setParameter("lastmodDt", new Date());
						q.setParameter("ipaddr", ipaddr);
						q.setParameter("usrid", usrid);
						q.executeUpdate();

					} else if (levelCode.equals("003")) {

						sb = new StringBuffer();
						sb.append(" update f set f.fstaff_mgr2_id = fm.fstaff_id,f.FSTAFF_mod_dt=:lastmodDt,");
						sb.append(" f.FSTAFF_mod_ip_add=:ipaddr,f.FSTAFF_mod_usr_id=:usrid");
						sb.append(" from FIELDSTAFF f , TERR_MASTER t , FIELDSTAFF fm");
						sb.append(" where f.fstaff_terr_id = t.terr_id AND t.TERR_STATUS = 'A'");
						sb.append(" AND f.fstaff_status = 'A' AND f.FSTAFF_LEVEL_CODE <:levelcode");
						sb.append(" AND t.TERR_MGR2_ID = fm.FSTAFF_TERR_ID AND fm.fstaff_status = 'A'");
						sb.append(" AND  FM.FSTAFF_TERR_CODE = :terrcode");
						sb.append(" AND f.FSTAFF_MGR2_ID != fm.FSTAFF_ID");

						Query q = entityManager.createNativeQuery(sb.toString(), Tuple.class);
						q.setParameter("levelcode", levelCode);
						q.setParameter("terrcode", territorycode);
						q.setParameter("lastmodDt", new Date());
						q.setParameter("ipaddr", ipaddr);
						q.setParameter("usrid", usrid);
						q.executeUpdate();

					}
				}
			}
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			throw e;
		}
	}

	@Override
	public FieldStaff getFstaffbyFsMclNoAndDivId(String mclNo, Long divId) throws Exception {
		// TODO Auto-generated method stub
		try {
			CriteriaBuilder builder = entityManager.getCriteriaBuilder();
			CriteriaQuery<FieldStaff> query = builder.createQuery(FieldStaff.class);
			Root<FieldStaff> root = query.from(FieldStaff.class);
			query.where(builder.and(builder.equal(root.get(FieldStaff_.fs_div_id), divId),
					builder.and(builder.equal(root.get(FieldStaff_.fstaff_mcl_no), mclNo))));
			List<FieldStaff> list = entityManager.createQuery(query).getResultList();

			if (list != null && list.size() > 0) {
				return list.get(0);
			}
		} catch (Exception e) {
			// TODO: handle exception
			System.out.println("Error Occurred :" +e.getMessage());// uncomment asneeded --;
		}
		return null;
	}

	@Override
	public FieldStaff getFsIdByFsCode(String fsCode) throws Exception {
		EntityManager em = null;
		FieldStaff fstaff = null;
		try {
			CriteriaBuilder builder = entityManager.getCriteriaBuilder();
			CriteriaQuery<FieldStaff> query = builder.createQuery(FieldStaff.class);
			Root<FieldStaff> root = query.from(FieldStaff.class);
			query.multiselect(root.get(FieldStaff_.fstaff_id));
			query.where(builder.and(builder.equal(root.get(FieldStaff_.fstaff_code), fsCode),
					builder.and(builder.equal(root.get(FieldStaff_.fs_category), "F")),
					builder.and(builder.equal(root.get(FieldStaff_.level_code), "001")),
					builder.and(builder.isNull(root.get(FieldStaff_.leaving_date)))));

			fstaff = entityManager.createQuery(query).getSingleResult();
		} catch (Exception e) {
			throw e;
		} finally {
			if (em != null) {
				em.close();
			}
		}
		return fstaff;
	}

	@Override
	public Long getTerrIdByFsId(Long fsId) throws Exception {
		EntityManager em = null;
		Long fstaff = null;
		try {
			CriteriaBuilder builder = entityManager.getCriteriaBuilder();
			CriteriaQuery<Long> query = builder.createQuery(Long.class);
			Root<FieldStaff> root = query.from(FieldStaff.class);
			query.select(root.get(FieldStaff_.fstaff_id));
			query.where(builder.and(builder.equal(root.get(FieldStaff_.fstaff_id), fsId)));

			fstaff = entityManager.createQuery(query).getSingleResult();
		} catch (Exception e) {
			throw e;
		} finally {
			if (em != null) {
				em.close();
			}
		}
		return fstaff;
	}

	@Override
	public Long getFstaffDivIdForCfa_to_Stk_requestor(Long loc_id) throws Exception {
		
		if(loc_id.compareTo(0L) == 0)
			return null;
		
		EntityManager em = null;
		Long fstaff_div_id = null;
		try {
			em = this.emf.createEntityManager();
			
			CriteriaBuilder builder = em.getCriteriaBuilder();
			CriteriaQuery<Long> query = builder.createQuery(Long.class);
			Root<FieldStaff> root = query.from(FieldStaff.class);
			Path<Long> path = root.get("fs_div_id");
			
			//subquery
			Subquery<Long> subquery = query.subquery(Long.class);
			Root<Location> subq_root = subquery.from(Location.class);
			Path<Long> subq_path = subq_root.get("requestor_fstaff_id");
			subquery.select(subq_path).where(builder.equal(subq_root.get("loc_id"), loc_id));
			
			query.select(path).where(builder.equal(root.get("fstaff_id"),subquery));
			
			TypedQuery<Long> typedQuery = em.createQuery(query);
			
			fstaff_div_id = typedQuery.getSingleResult();
		}
		finally {
			if(em!=null) em.close();
		}
		return fstaff_div_id;
	}

	@Override
	public boolean isFileExist(String file) throws Exception {
		EntityManager em = null;
		Long count = 0L;
		try {
			em = this.emf.createEntityManager();
			StringBuffer sb = new StringBuffer();
			sb.append("Select count(csv_file_name) from FIELDSTAFF_MOBILENO_UPDATE_LOG where csv_file_name='").append(file + "'");
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
	public FieldStaff updateMobileNoById(String fstaff_mobile, Long fstaff_id) throws Exception {
		
		return this.entityManager.find(FieldStaff.class, fstaff_id);
		
	}

}

