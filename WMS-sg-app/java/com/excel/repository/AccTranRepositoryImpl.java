package com.excel.repository;

import java.math.BigDecimal;
import java.util.ArrayList;
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

import com.excel.bean.CustomAccTranGeneration;
import com.excel.model.Acc_Tran_Type;
import com.excel.model.Acc_Tran_Type.TranNameEnum;

@Repository
public class AccTranRepositoryImpl implements AccTranRepository {

	@Autowired
	private EntityManagerFactory emf;
	@PersistenceContext private EntityManager entityManager;

	@Override
	public String getMaxOfAccTranType(String companyCode) throws Exception {
		String maxTranType = null;
		try {
			Query query = entityManager
					.createNativeQuery("select TRAN_TYPE from ACC_TRAN_TYPE where COMPANY_CD = :comp_code order by TRAN_TYPE desc");
			query.setParameter("comp_code", companyCode);
			query.setMaxResults(1);
			maxTranType = (String) query.getSingleResult();
		} finally {}
		return maxTranType;
	}

	@Override
	public List<CustomAccTranGeneration> getAccTranTypeByLocId(Long loc_id) throws Exception {
		EntityManager em = null;
		List<CustomAccTranGeneration> cust_acc_gen = null;
		try {
			em = this.emf.createEntityManager();

			StringBuffer sb = new StringBuffer();
			sb.append(
					" select ac.FULL_TRAN_NAME as FULL_TRAN_NAME,ac.TRAN_TYPE as TRAN_TYPE,tm.TASK_MASTER_ID as TASK_MASTER_ID,");
			sb.append(" tmdtl.TASK_MASTER_DTL_ID as TASK_MASTER_DTL_ID from location lc");
			sb.append(" inner join acc_tran_type ac on lc.loc_id = ac.LOC_ID");
			sb.append(" left outer join TASKS_MASTER tm on tm.TRAN_TYPE = ac.TRAN_TYPE");
			sb.append(" left outer join TASK_MASTER_DTL tmdtl on tmdtl.TASK_ID = tm.TASK_MASTER_ID");
			sb.append(" where lc.loc_status = 'A'");
			sb.append(" and lc.loc_id = :loc_id");
			sb.append(" order by lc.loc_id,ac.TRAN_TYPE");

			Query query = em.createNativeQuery(sb.toString(), Tuple.class);
			query.setParameter("loc_id", loc_id);

			List<Tuple> tuple_list = query.getResultList();
			if (tuple_list != null && tuple_list.size() > 0) {
				cust_acc_gen = new ArrayList<CustomAccTranGeneration>();
				for (Tuple tup : tuple_list) {
					CustomAccTranGeneration obj = new CustomAccTranGeneration(
							tup.get("FULL_TRAN_NAME", String.class) != null
									? TranNameEnum.valueOf(tup.get("FULL_TRAN_NAME", String.class))
									: null,
							tup.get("TRAN_TYPE", String.class) != null ? tup.get("TRAN_TYPE", String.class) : null,
							tup.get("TASK_MASTER_ID", BigDecimal.class) != null
									? tup.get("TASK_MASTER_ID", BigDecimal.class).longValue()
									: null,
							tup.get("TASK_MASTER_DTL_ID", BigDecimal.class) != null
									? tup.get("TASK_MASTER_DTL_ID", BigDecimal.class).longValue()
									: null);
					cust_acc_gen.add(obj);
				}
			}
		} finally {
			if (em != null)
				em.close();
		}
		return cust_acc_gen;
	}

	@Override
	public Acc_Tran_Type getAccRecordByFullTranNameAndLocId(TranNameEnum full_tran_name, Long loc_id) throws Exception {
		EntityManager em = null;
		try {
			em = this.emf.createEntityManager();
			CriteriaBuilder builder = em.getCriteriaBuilder();
			CriteriaQuery<Acc_Tran_Type> criteria = builder.createQuery(Acc_Tran_Type.class);
			Root<Acc_Tran_Type> root = criteria.from(Acc_Tran_Type.class);
			criteria.select(root).where(builder.and(builder.equal(root.get("full_tran_name"), full_tran_name),
					builder.equal(root.get("loc_id"), loc_id)));
			Acc_Tran_Type ac = em.createQuery(criteria).getSingleResult();
			return ac;
		} finally {
			if (em != null)
				em.close();
		}
	}

}
