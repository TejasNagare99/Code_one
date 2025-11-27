package com.excel.repository;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.Tuple;
import javax.persistence.criteria.CriteriaBuilder.In;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.excel.model.SG_d_parameters_details;
import com.excel.model.TaxMaster;
import com.excel.model.TerrMaster;

@Repository
public class TaxRepositoryImpl implements TaxRepository {

	@Autowired
	private EntityManagerFactory emf;
	@PersistenceContext
	private EntityManager entityManager;

	@Override
	public TaxMaster getObjectById(Long taxId) throws Exception {
		return this.entityManager.find(TaxMaster.class, taxId);
	}

	@Override
	public Boolean checkUniqueDesc(String stateId, String hsnCode) {
		EntityManager em = null;
		Map<String, Object> map = new HashMap<String, Object>();
		Boolean data;
		try {
			em = emf.createEntityManager();
			String q = "select TAX_ID,CGST,COMPANY_CD,EFFECT_DT,HSN_CODE,IC_CHGS,IGST,INPUT_TAX_PARAM,LAST_MOD_BY,LAST_MOD_DATE,OUTPUT_TAX_PARAM,SGST,STATE_ID,TAX_CD,TAX_DESCR from TAX_MASTER where STATE_ID=:stateId and HSN_CODE=:hsnCode";
			Query query = em.createNativeQuery(q, Tuple.class);
			query.setParameter("stateId", stateId);
			query.setParameter("hsnCode", hsnCode);
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
	public List<Object> getTaxDesc(String taxId, String hsnCode) {
		EntityManager em = null;
		List<Object> list = new ArrayList<Object>();
		try {
			em = emf.createEntityManager();
			String q = "select tax_id,tax_cd,tax_descr,sgst,cgst,igst,ADD_TAX,SURCH,CESS,TO_TAX from TAX_MASTER  where tax_id=:taxId and HSN_CODE=:hsnCode";
			Query query = em.createNativeQuery(q, Tuple.class);
			query.setParameter("hsnCode", hsnCode);
			query.setParameter("taxId", taxId);
			List<Tuple> tuples = query.getResultList();
			if (tuples != null && !tuples.isEmpty()) {
				for (Tuple t : tuples) {
					TaxMaster tm = new TaxMaster();
					tm.setTax_id(Long.valueOf(t.get("tax_id", Integer.class)));
					tm.setTax_cd(t.get("tax_cd", String.class));
					tm.setTax_descr(t.get("tax_descr", String.class));
					tm.setSgst(t.get("sgst", BigDecimal.class));
					tm.setCgst(t.get("cgst", BigDecimal.class));
					tm.setIgst(t.get("igst", BigDecimal.class));
					tm.setAdd_tax(t.get("ADD_TAX", BigDecimal.class));
					tm.setSurch(t.get("SURCH", BigDecimal.class));
					tm.setCess(t.get("CESS", BigDecimal.class));
					tm.setTo_tax(t.get("TO_TAX", BigDecimal.class));

					list.add(tm);
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
	public List<TaxMaster> getHsnCodeListByStateId(String stateId) {
		EntityManager em = null;
		List<TaxMaster> list = new ArrayList<TaxMaster>();
		try {
			em = emf.createEntityManager();
			String q = "select tax_id,state_id,hsn_code from TAX_MASTER  where state_id=:stateId order by tax_id desc";
			Query query = em.createNativeQuery(q, Tuple.class);
			query.setParameter("stateId", stateId);
			List<Tuple> tuples = query.getResultList();
			if (tuples != null && !tuples.isEmpty()) {
				for (Tuple t : tuples) {
					TaxMaster tm = new TaxMaster();
					tm.setTax_id(Long.valueOf(t.get("tax_id", Integer.class)));
					tm.setState_id(Long.valueOf(t.get("state_id", BigDecimal.class).toString()));
					tm.setHsn_code(t.get("hsn_code", String.class));
					list.add(tm);
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
//	@Override
//	public List<TaxMaster> getHsnCodeListByStateId(String stateId) {
//		//String q="SELECT tax_id,tax_cd,tax_descr,state_id,hsn_code,effect_dt from TAX_MASTER where STATE_ID=:stateId order by hsn_code asc";
//		EntityManager em = null;
//		List<TaxMaster> list = new ArrayList<TaxMaster>();
//		try {
//			em = emf.createEntityManager();
//			Query query = null;
//			String q="from TaxMaster where STATE_ID=:stateId order by hsn_code asc";
//			query = em.createQuery(q);
//			query.setParameter("stateId", stateId);
//			list=query.getResultList();
//			} finally {
//				if (em != null) { em.close(); }
//		}
//		return list;
//			
//	}

	@Override
	public List<TaxMaster> getAllTaxDetail() {
		EntityManager em = null;
		List<TaxMaster> list = new ArrayList<TaxMaster>();
		try {
			em = emf.createEntityManager();
			Query query = null;
			String q = "from TaxMaster order by hsn_code asc";
			query = em.createQuery(q);
			list = query.getResultList();
		} finally {
			if (em != null) {
				em.close();
			}
		}
		return list;
	}

	@Override
	public List<Object> getAll_tax_code(String hsnCode) {
		EntityManager em = null;
		List<Object> list = new ArrayList<>();
		try {
			em = emf.createEntityManager();
			String q = "SELECT TAX_CD FROM TAX_MASTER WHERE HSN_CODE=:hsn_code";
			Query query = em.createNativeQuery(q);
			query.setParameter("hsn_code", hsnCode);
			list = query.getResultList();

		}

//		catch (Exception e) {
//			
//			e.printStackTrace();
//			throw e;
//		}
		finally {
			if (em != null) {
				em.close();
			}
		}

		if (list.size() > 0) {

			System.err.println("list:::" + list.size());
		}

		return list;
	}

	@Override
	public Map<String, List<Object>> getAll_States_And_Tax_code(String hsnCode) throws Exception {

		EntityManager em = null;
		List<Object> state_list = new ArrayList<>();
		List<Object> tax_list = new ArrayList<>();

		List<Tuple> list = new ArrayList<>();
		Map<String, List<Object>> map = new HashMap();
		try {
			em = emf.createEntityManager();
			String q = "select   state_id,TAX_CD from TAX_MASTER WHERE HSN_CODE=:hsn_code ";
			Query query = em.createNativeQuery(q, Tuple.class);
			query.setParameter("hsn_code", hsnCode);
			list = query.getResultList();

		}

		catch (Exception e) {

			e.printStackTrace();
			throw e;
		} finally {
			if (em != null) {
				em.close();
			}
		}

		for (Tuple t : list) {
//			
			System.out.println("state_id" + t.get("state_id"));
			System.out.println("TAX_CD" + t.get("TAX_CD"));

			state_list.add(t.get("state_id"));
			tax_list.add(t.get("TAX_CD"));
		}

		map.put("state_list", state_list);
		map.put("tax_list", tax_list);

		return map;
	}

	@Override
	public List<TaxMaster> get_TaxMaster_Data(String hsn_code, Long state_id) {

		EntityManager em = null;
		List<TaxMaster> list = new ArrayList<TaxMaster>();
		try {
			em = emf.createEntityManager();
			Query query = null;
			String q = " from TaxMaster where HSN_CODE=:hsn_code and STATE_ID=:state_id";
			query = em.createQuery(q);
			query.setParameter("hsn_code", hsn_code);
			query.setParameter("state_id", state_id);
			list = query.getResultList();
		}

		catch (Exception e) {
			throw e;
		} finally {
			if (em != null) {
				em.close();
			}
		}

		if (list == null) {

			System.err.println("null");
		}

		return list;

	}

	@Override
	public Boolean hsn_Exist_Or_Not(String hsnCode) {
		Long  length = 0L;
		EntityManager em = null;
		try {
			em = emf.createEntityManager();
			Query query = null;
			String q = "select count(*) as length from TAX_MASTER where HSN_CODE=:hsn_code ";
			query = em.createNativeQuery(q);
			query.setParameter("hsn_code", hsnCode);
			length = Long.valueOf( query.getSingleResult().toString());
			
			if (length > 0L) {
				return true;
			} else {
				return false;
			}
		}
		catch (Exception e) {
			throw e;
		} finally {
			if (em != null) {
				em.close();
			}
		}
	}

}
