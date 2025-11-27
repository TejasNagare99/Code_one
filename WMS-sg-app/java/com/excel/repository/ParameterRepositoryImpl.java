package com.excel.repository;

import java.util.ArrayList;
import java.util.Arrays;
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

import com.excel.bean.Parameter;
import com.excel.model.Alloc_Temp;
import com.excel.model.Alloc_Temp_;
import com.excel.model.CrmSchemeMaster;
import com.excel.model.CrmSchemeMaster_;
import com.excel.model.DivMaster_;
import com.excel.model.GrnHeader;
import com.excel.model.SG_d_parameters_details;
import com.excel.model.SG_d_parameters_details_;
import com.excel.model.SG_m_parameters;
import com.excel.model.Sfa_Field_Master;
import com.excel.model.Sfa_Field_Master_;
import com.excel.utility.CodifyErrors;

@Repository
public class ParameterRepositoryImpl implements ParameterRepository {

	@PersistenceContext
	private EntityManager entityManager;
	@Autowired
	private EntityManagerFactory emf;

	@Override
	public SG_d_parameters_details getObjectById(Long SGprmdet_id) throws Exception {
		return this.entityManager.find(SG_d_parameters_details.class, SGprmdet_id);
	}

	@Override
	public List<SG_d_parameters_details> getGrnTypes() throws Exception {
		EntityManager em = null;
		List<SG_d_parameters_details> list = null;
		try {
			em = emf.createEntityManager();
			StringBuffer sb = new StringBuffer();
			sb.append(
					" SELECT sd.sg_prmdet_id AS sg_prmdet_id, sd.sgprmdet_nm AS sgprmdet_nm, sd.sgprmdet_disp_nm AS sgprmdet_disp_nm FROM SG_d_parameters_details sd ");
			sb.append(" JOIN SG_m_parameters sg on sd.sgprmdet_sgprm_id=sg.sgprm_id ");
			sb.append(" WHERE sg.sgprm_disp_nm = 'GRN_TYPES' AND sd.sgprmdet_status='A' ORDER BY sd.sgprmdet_disp_nm");
			Query query = em.createQuery(sb.toString(), Tuple.class);
			List<Tuple> tuples = query.getResultList();

			if (tuples != null && !tuples.isEmpty()) {
				list = new ArrayList<>();
				SG_d_parameters_details object = null;
				for (Tuple t : tuples) {
					object = new SG_d_parameters_details();
					object.setSg_prmdet_id(t.get("sg_prmdet_id", Long.class));
					object.setSgprmdet_nm(t.get("sgprmdet_nm", String.class));
					object.setSgprmdet_disp_nm(t.get("sgprmdet_disp_nm", String.class));
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
	public List<SG_d_parameters_details> getSupplierTypeByGrnType(String grnType) throws Exception {
		EntityManager em = null;
		List<SG_d_parameters_details> list = null;
		System.out.println("GRN_TYPE " + grnType);
		try {
			em = emf.createEntityManager();
			StringBuffer sb = new StringBuffer();
			sb.append(
					" SELECT sd.sg_prmdet_id AS sg_prmdet_id, sd.sgprmdet_nm AS sgprmdet_nm, sd.sgprmdet_disp_nm AS sgprmdet_disp_nm, sd.sgprmdet_text2 as sgprmdet_text2, sd.sgprmdet_text1 as sgprmdet_text1 FROM SG_d_parameters_details sd ");
			sb.append(" JOIN SG_m_parameters sg on sd.sgprmdet_sgprm_id=sg.sgprm_id ");
			sb.append(" WHERE sg.sgprm_disp_nm = 'Supplier Type' AND sd.sgprmdet_status='A' ");
			sb.append(
					" AND (SUBSTRING(sd.sgprmdet_text1,1,2) = :grnType OR  SUBSTRING(sd.sgprmdet_text1,4,2) = :grnType ) ORDER BY sd.sgprmdet_disp_nm");
			Query query = em.createQuery(sb.toString(), Tuple.class);
			query.setParameter("grnType", grnType);
			List<Tuple> tuples = query.getResultList();

			if (tuples != null && !tuples.isEmpty()) {
				list = new ArrayList<>();
				SG_d_parameters_details object = null;
				for (Tuple t : tuples) {
					object = new SG_d_parameters_details();
					object.setSg_prmdet_id(t.get("sg_prmdet_id", Long.class));
					object.setSgprmdet_nm(t.get("sgprmdet_nm", String.class));
					object.setSgprmdet_disp_nm(t.get("sgprmdet_disp_nm", String.class));
					object.setSgprmdet_text1(t.get("sgprmdet_text1", String.class));
					object.setSgprmdet_text2(t.get("sgprmdet_text2", String.class));
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
	public List<SG_d_parameters_details> getProductTypeList() throws Exception {
		EntityManager em = null;
		List<SG_d_parameters_details> list = null;
		try {
			em = emf.createEntityManager();
			StringBuffer sb = new StringBuffer();
			sb.append(" SELECT sd.sg_prmdet_id AS sg_prmdet_id, sd.sgprmdet_text1 AS sgprmdet_text1, ");
			sb.append(
					" sd.sgprmdet_text2 AS sgprmdet_text2,sd.sgprmdet_num1 AS sgprmdet_num1,sd.sgprmdet_short_nm AS sgprmdet_short_nm,sd.sgprmdet_nm AS sgprmdet_nm, sd.sgprmdet_disp_nm AS sgprmdet_disp_nm from SG_d_parameters_details sd ");
			sb.append(
					" WHERE sd.sgprmdet_sgprm_id = (SELECT sg.sgprm_id FROM SG_m_parameters sg WHERE sg.sgprm_nm = 'Product Types') AND sd.sgprmdet_status='A' ");
			sb.append(" ORDER BY sd.sgprmdet_disp_nm");
			Query query = em.createQuery(sb.toString(), Tuple.class);
			List<Tuple> tuples = query.getResultList();

			if (tuples != null && !tuples.isEmpty()) {
				list = new ArrayList<>();
				SG_d_parameters_details object = null;
				for (Tuple t : tuples) {
					object = new SG_d_parameters_details();
					object.setSg_prmdet_id(t.get("sg_prmdet_id", Long.class));
					object.setSgprmdet_nm(t.get("sgprmdet_nm", String.class));
					object.setSgprmdet_disp_nm(t.get("sgprmdet_disp_nm", String.class));
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
	public List<SG_d_parameters_details> getStockTypes() throws Exception {
		EntityManager em = null;
		List<SG_d_parameters_details> list = null;
		try {
			em = emf.createEntityManager();
			StringBuffer sb = new StringBuffer();
			sb.append(
					" SELECT sd.sg_prmdet_id AS sg_prmdet_id, sd.sgprmdet_nm AS sgprmdet_nm, sd.sgprmdet_disp_nm AS sgprmdet_disp_nm, sd.sgprmdet_text2 as sgprmdet_text2 FROM SG_d_parameters_details sd ");
			sb.append(" JOIN SG_m_parameters sg on sd.sgprmdet_sgprm_id=sg.sgprm_id ");
			sb.append(
					" WHERE sg.sgprm_disp_nm = 'Stock Types' AND sd.sgprmdet_status='A' ORDER BY sd.sgprmdet_disp_nm ASC ");
			Query query = em.createQuery(sb.toString(), Tuple.class);
			List<Tuple> tuples = query.getResultList();

			if (tuples != null && !tuples.isEmpty()) {
				list = new ArrayList<>();
				SG_d_parameters_details object = null;
				for (Tuple t : tuples) {
					object = new SG_d_parameters_details();
					object.setSg_prmdet_id(t.get("sg_prmdet_id", Long.class));
					object.setSgprmdet_nm(t.get("sgprmdet_nm", String.class));
					object.setSgprmdet_disp_nm(t.get("sgprmdet_disp_nm", String.class));
					object.setSgprmdet_text2(t.get("sgprmdet_text2", String.class));
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
	public List<SG_d_parameters_details> getState() {

		EntityManager em = null;
		List<SG_d_parameters_details> list = new ArrayList<SG_d_parameters_details>();
		try {
			em = emf.createEntityManager();
			String q = "select SGprmdet_id, SGprmdet_Text1,SGprmdet_Text2,SGprmdet_Num1,SGprmdet_short_nm,SGprmdet_nm,SGprmdet_disp_nm from SG_d_parameters_details where SGprmdet_status='A' and SGprmdet_SGprm_id = (select SGprm_id from SG_m_parameters where SGprm_nm='State') order by SGprmdet_disp_nm asc";
			Query query = em.createNativeQuery(q, Tuple.class);
			List<Tuple> tuples = query.getResultList();
			if (tuples != null && !tuples.isEmpty()) {
				for (Tuple t : tuples) {
					Integer id = t.get("SGprmdet_id", Integer.class);
					SG_d_parameters_details sgdp = new SG_d_parameters_details();
					sgdp.setSg_prmdet_id(Long.valueOf(id));
					sgdp.setSgprmdet_disp_nm(t.get("SGprmdet_disp_nm", String.class));
					list.add(sgdp);
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
	public List<SG_d_parameters_details> getSupplierTypeForSupplierEntry() {

		EntityManager em = null;
		List<SG_d_parameters_details> list = new ArrayList<SG_d_parameters_details>();
		try {
			em = emf.createEntityManager();
			String q = "select SGprmdet_id, SGprmdet_Text1,SGprmdet_Text2,SGprmdet_Num1,SGprmdet_short_nm,SGprmdet_nm,SGprmdet_disp_nm from SG_d_parameters_details where SGprmdet_status='A' and SGprmdet_SGprm_id = (select SGprm_id from SG_m_parameters where SGprm_nm='Supplier Type') order by SGprmdet_disp_nm asc";
			Query query = em.createNativeQuery(q, Tuple.class);
			List<Tuple> tuples = query.getResultList();
			if (tuples != null && !tuples.isEmpty()) {
				for (Tuple t : tuples) {
					SG_d_parameters_details sgdpd = new SG_d_parameters_details();
					sgdpd.setSg_prmdet_id(Long.valueOf(t.get("SGprmdet_id", Integer.class)));
					sgdpd.setSgprmdet_nm(t.get("SGprmdet_nm", String.class));
					sgdpd.setSgprmdet_disp_nm(t.get("SGprmdet_disp_nm", String.class));
					list.add(sgdpd);
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
	public List<SG_d_parameters_details> getForm() {
		EntityManager em = null;
		List<SG_d_parameters_details> list = new ArrayList<SG_d_parameters_details>();
		try {
			em = emf.createEntityManager();
			String q = "select SGprmdet_id, SGprmdet_Text1,SGprmdet_Text2,SGprmdet_Num1,SGprmdet_short_nm,SGprmdet_nm,SGprmdet_disp_nm from SG_d_parameters_details where SGprmdet_status='A' and SGprmdet_SGprm_id = (select SGprm_id from SG_m_parameters where SGprm_nm='FORM') order by SGprmdet_disp_nm asc";
			Query query = em.createNativeQuery(q, Tuple.class);
			List<Tuple> tuples = query.getResultList();
			if (tuples != null && !tuples.isEmpty()) {
				for (Tuple t : tuples) {
					SG_d_parameters_details sgdpd = new SG_d_parameters_details();
					sgdpd.setSg_prmdet_id(Long.valueOf(t.get("SGprmdet_id", Integer.class)));
					sgdpd.setSgprmdet_nm(t.get("SGprmdet_nm", String.class));
					sgdpd.setSgprmdet_disp_nm(t.get("SGprmdet_disp_nm", String.class));
					list.add(sgdpd);
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
	public List<SG_d_parameters_details> getManufacturingLocation() {
		EntityManager em = null;
		List<SG_d_parameters_details> list = new ArrayList<SG_d_parameters_details>();
		try {
			em = emf.createEntityManager();
			String q = "select SGprmdet_id, SGprmdet_Text1,SGprmdet_Text2,SGprmdet_Num1,SGprmdet_short_nm,SGprmdet_nm,SGprmdet_disp_nm from SG_d_parameters_details where SGprmdet_status='A' and SGprmdet_SGprm_id = (select SGprm_id from SG_m_parameters where SGprm_nm='Manufacturing Location') order by SGprmdet_disp_nm asc";
			Query query = em.createNativeQuery(q, Tuple.class);
			List<Tuple> tuples = query.getResultList();
			if (tuples != null && !tuples.isEmpty()) {
				for (Tuple t : tuples) {
					SG_d_parameters_details sgdpd = new SG_d_parameters_details();
					sgdpd.setSg_prmdet_id(Long.valueOf(t.get("SGprmdet_id", Integer.class)));
					sgdpd.setSgprmdet_nm(t.get("SGprmdet_nm", String.class));
					sgdpd.setSgprmdet_disp_nm(t.get("SGprmdet_disp_nm", String.class));
					list.add(sgdpd);
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
	public List<SG_d_parameters_details> getTheropathyGroup() {
		EntityManager em = null;
		List<SG_d_parameters_details> list = new ArrayList<SG_d_parameters_details>();
		try {
			em = emf.createEntityManager();
			String q = "select SGprmdet_id, SGprmdet_Text1,SGprmdet_Text2,SGprmdet_Num1,SGprmdet_short_nm,SGprmdet_nm,SGprmdet_disp_nm from SG_d_parameters_details where SGprmdet_status='A' and SGprmdet_SGprm_id = (select SGprm_id from SG_m_parameters where SGprm_nm='Theropathy Group') order by SGprmdet_disp_nm asc";
			Query query = em.createNativeQuery(q, Tuple.class);
			List<Tuple> tuples = query.getResultList();
			if (tuples != null && !tuples.isEmpty()) {
				for (Tuple t : tuples) {
					SG_d_parameters_details sgdpd = new SG_d_parameters_details();
					sgdpd.setSg_prmdet_id(Long.valueOf(t.get("SGprmdet_id", Integer.class)));
					sgdpd.setSgprmdet_nm(t.get("SGprmdet_nm", String.class));
					sgdpd.setSgprmdet_disp_nm(t.get("SGprmdet_disp_nm", String.class));
					list.add(sgdpd);
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
	public List<SG_d_parameters_details> getTheropathySubGroup() {
		EntityManager em = null;
		List<SG_d_parameters_details> list = new ArrayList<SG_d_parameters_details>();
		try {
			em = emf.createEntityManager();
			String q = "select SGprmdet_id, SGprmdet_Text1,SGprmdet_Text2,SGprmdet_Num1,SGprmdet_short_nm,SGprmdet_nm,SGprmdet_disp_nm from SG_d_parameters_details where SGprmdet_status='A' and SGprmdet_SGprm_id = (select SGprm_id from SG_m_parameters where SGprm_nm='Theropathy SubGroup') order by SGprmdet_disp_nm asc";
			Query query = em.createNativeQuery(q, Tuple.class);
			List<Tuple> tuples = query.getResultList();
			if (tuples != null && !tuples.isEmpty()) {
				for (Tuple t : tuples) {
					SG_d_parameters_details sgdpd = new SG_d_parameters_details();
					sgdpd.setSg_prmdet_id(Long.valueOf(t.get("SGprmdet_id", Integer.class)));
					sgdpd.setSgprmdet_nm(t.get("SGprmdet_nm", String.class));
					sgdpd.setSgprmdet_disp_nm(t.get("SGprmdet_disp_nm", String.class));
					list.add(sgdpd);
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
	public List<SG_d_parameters_details> getMoleGroup() {
		EntityManager em = null;
		List<SG_d_parameters_details> list = new ArrayList<SG_d_parameters_details>();
		try {
			em = emf.createEntityManager();
			String q = "select SGprmdet_id, SGprmdet_Text1,SGprmdet_Text2,SGprmdet_Num1,SGprmdet_short_nm,SGprmdet_nm,SGprmdet_disp_nm from SG_d_parameters_details where SGprmdet_status='A' and SGprmdet_SGprm_id = (select SGprm_id from SG_m_parameters where SGprm_nm='Mole Group') order by SGprmdet_disp_nm asc";
			Query query = em.createNativeQuery(q, Tuple.class);
			List<Tuple> tuples = query.getResultList();
			if (tuples != null && !tuples.isEmpty()) {
				for (Tuple t : tuples) {
					SG_d_parameters_details sgdpd = new SG_d_parameters_details();
					sgdpd.setSg_prmdet_id(Long.valueOf(t.get("SGprmdet_id", Integer.class)));
					sgdpd.setSgprmdet_nm(t.get("SGprmdet_nm", String.class));
					sgdpd.setSgprmdet_disp_nm(t.get("SGprmdet_disp_nm", String.class));
					list.add(sgdpd);
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
	public List<SG_d_parameters_details> getMoleSubGroup() {
		EntityManager em = null;
		List<SG_d_parameters_details> list = new ArrayList<SG_d_parameters_details>();
		try {
			em = emf.createEntityManager();
			String q = "select SGprmdet_id, SGprmdet_Text1,SGprmdet_Text2,SGprmdet_Num1,SGprmdet_short_nm,SGprmdet_nm,SGprmdet_disp_nm from SG_d_parameters_details where SGprmdet_status='A' and SGprmdet_SGprm_id = (select SGprm_id from SG_m_parameters where SGprm_nm='Mole SubGroup') order by SGprmdet_disp_nm asc";
			Query query = em.createNativeQuery(q, Tuple.class);
			List<Tuple> tuples = query.getResultList();
			if (tuples != null && !tuples.isEmpty()) {
				for (Tuple t : tuples) {
					SG_d_parameters_details sgdpd = new SG_d_parameters_details();
					sgdpd.setSg_prmdet_id(Long.valueOf(t.get("SGprmdet_id", Integer.class)));
					sgdpd.setSgprmdet_nm(t.get("SGprmdet_nm", String.class));
					sgdpd.setSgprmdet_disp_nm(t.get("SGprmdet_disp_nm", String.class));
					list.add(sgdpd);
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
	public List<SG_d_parameters_details> getPMTGroup() {
		EntityManager em = null;
		List<SG_d_parameters_details> list = new ArrayList<SG_d_parameters_details>();
		try {
			em = emf.createEntityManager();
			String q = "select SGprmdet_id, SGprmdet_Text1,SGprmdet_Text2,SGprmdet_Num1,SGprmdet_short_nm,SGprmdet_nm,SGprmdet_disp_nm from SG_d_parameters_details where SGprmdet_status='A' and SGprmdet_SGprm_id = (select SGprm_id from SG_m_parameters where SGprm_nm='PMT Group') order by SGprmdet_disp_nm asc";
			Query query = em.createNativeQuery(q, Tuple.class);
			List<Tuple> tuples = query.getResultList();
			if (tuples != null && !tuples.isEmpty()) {
				for (Tuple t : tuples) {
					SG_d_parameters_details sgdpd = new SG_d_parameters_details();
					sgdpd.setSg_prmdet_id(Long.valueOf(t.get("SGprmdet_id", Integer.class)));
					sgdpd.setSgprmdet_nm(t.get("SGprmdet_nm", String.class));
					sgdpd.setSgprmdet_disp_nm(t.get("SGprmdet_disp_nm", String.class));
					list.add(sgdpd);
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
	public List<SG_d_parameters_details> getPMTSubGroup() {
		EntityManager em = null;
		List<SG_d_parameters_details> list = new ArrayList<SG_d_parameters_details>();
		try {
			em = emf.createEntityManager();
			String q = "select SGprmdet_id, SGprmdet_Text1,SGprmdet_Text2,SGprmdet_Num1,SGprmdet_short_nm,SGprmdet_nm,SGprmdet_disp_nm from SG_d_parameters_details where SGprmdet_status='A' and SGprmdet_SGprm_id = (select SGprm_id from SG_m_parameters where SGprm_nm='PMT SubGroup') order by SGprmdet_disp_nm asc";
			Query query = em.createNativeQuery(q, Tuple.class);
			List<Tuple> tuples = query.getResultList();
			if (tuples != null && !tuples.isEmpty()) {
				for (Tuple t : tuples) {
					SG_d_parameters_details sgdpd = new SG_d_parameters_details();
					sgdpd.setSg_prmdet_id(Long.valueOf(t.get("SGprmdet_id", Integer.class)));
					sgdpd.setSgprmdet_nm(t.get("SGprmdet_nm", String.class));
					sgdpd.setSgprmdet_disp_nm(t.get("SGprmdet_disp_nm", String.class));
					list.add(sgdpd);
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
	public List<SG_d_parameters_details> getStorageType() {
		EntityManager em = null;
		List<SG_d_parameters_details> list = new ArrayList<SG_d_parameters_details>();
		try {
			em = emf.createEntityManager();
			String q = "select SGprmdet_id, SGprmdet_Text1,SGprmdet_Text2,SGprmdet_Num1,SGprmdet_short_nm,SGprmdet_nm,SGprmdet_disp_nm from SG_d_parameters_details where SGprmdet_status='A' and SGprmdet_SGprm_id = (select SGprm_id from SG_m_parameters where SGprm_nm='STORAGE_TYPE') order by SGprmdet_disp_nm asc";
			Query query = em.createNativeQuery(q, Tuple.class);
			List<Tuple> tuples = query.getResultList();
			if (tuples != null && !tuples.isEmpty()) {
				for (Tuple t : tuples) {
					SG_d_parameters_details sgdpd = new SG_d_parameters_details();
					sgdpd.setSg_prmdet_id(Long.valueOf(t.get("SGprmdet_id", Integer.class)));
					sgdpd.setSgprmdet_nm(t.get("SGprmdet_nm", String.class));
					sgdpd.setSgprmdet_disp_nm(t.get("SGprmdet_disp_nm", String.class));
					list.add(sgdpd);
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
	public List<SG_d_parameters_details> getProductTypes() {
		EntityManager em = null;
		List<SG_d_parameters_details> list = new ArrayList<SG_d_parameters_details>();
		try {
			em = emf.createEntityManager();
			String q = "select sgprmdet_id,sgprmdet_disp_nm,SGprmdet_short_nm from sg_d_parameters_details join SG_M_PARAMETERS sgm on sgm.sgprm_id=sgprmdet_sgprm_id \r\n"
					+ "where sgprm_disp_nm='Product Types' and sgprmdet_status='A' order by sgprmdet_disp_nm";
			Query query = em.createNativeQuery(q, Tuple.class);
			List<Tuple> tuples = query.getResultList();
			if (tuples != null && !tuples.isEmpty()) {
				for (Tuple t : tuples) {
					SG_d_parameters_details sgdpd = new SG_d_parameters_details();
					sgdpd.setSg_prmdet_id(Long.valueOf(t.get("sgprmdet_id", Integer.class)));
					sgdpd.setSgprmdet_disp_nm(t.get("sgprmdet_disp_nm", String.class));
					sgdpd.setSgprmdet_short_nm(t.get("SGprmdet_short_nm", String.class));
					list.add(sgdpd);
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
	public List<SG_d_parameters_details> getProductSubType() {
		EntityManager em = null;
		List<SG_d_parameters_details> list = new ArrayList<SG_d_parameters_details>();
		try {
			em = emf.createEntityManager();
			String q = "select sgprmdet_id,sgprmdet_disp_nm from sg_d_parameters_details join SG_M_PARAMETERS sgm on sgm.sgprm_id=sgprmdet_sgprm_id "
					+ "where sgprm_disp_nm='PRODUCT_SUB_TYPE'  order by sgprmdet_disp_nm";
			Query query = em.createNativeQuery(q, Tuple.class);

			List<Tuple> tuples = query.getResultList();
			if (tuples != null && !tuples.isEmpty()) {
				for (Tuple t : tuples) {
					SG_d_parameters_details sgdpd = new SG_d_parameters_details();
					sgdpd.setSg_prmdet_id(Long.valueOf(t.get("sgprmdet_id", Integer.class)));
					// sgdpd.setSgprmdet_nm(t.get("SGprmdet_nm",String.class));
					sgdpd.setSgprmdet_disp_nm(t.get("sgprmdet_disp_nm", String.class));
					list.add(sgdpd);
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
	public List<SG_d_parameters_details> getProductSubType(String shortName) {
		EntityManager em = null;
		List<SG_d_parameters_details> list = new ArrayList<SG_d_parameters_details>();
		try {
			em = emf.createEntityManager();
			String q = "select sgprmdet_id,sgprmdet_disp_nm from sg_d_parameters_details join SG_M_PARAMETERS sgm on sgm.sgprm_id=sgprmdet_sgprm_id\r\n"
					+ "where sgprm_disp_nm='PRODUCT_SUB_TYPE' and SGprmdet_Text1=:shortName order by sgprmdet_disp_nm";
			Query query = em.createNativeQuery(q, Tuple.class);
			query.setParameter("shortName", shortName);
			List<Tuple> tuples = query.getResultList();
			if (tuples != null && !tuples.isEmpty()) {
				for (Tuple t : tuples) {
					SG_d_parameters_details sgdpd = new SG_d_parameters_details();
					sgdpd.setSg_prmdet_id(Long.valueOf(t.get("sgprmdet_id", Integer.class)));
					// sgdpd.setSgprmdet_nm(t.get("SGprmdet_nm",String.class));
					sgdpd.setSgprmdet_disp_nm(t.get("sgprmdet_disp_nm", String.class));
					list.add(sgdpd);
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
	public List<SG_d_parameters_details> getSampleSupplierType() {
		EntityManager em = null;
		List<SG_d_parameters_details> list = new ArrayList<SG_d_parameters_details>();
		try {
			em = emf.createEntityManager();
			String q = "select sgprmdet_id,sgprmdet_disp_nm from sg_d_parameters_details join SG_M_PARAMETERS sgm on sgm.sgprm_id=sgprmdet_sgprm_id "
					+ "where sgprm_disp_nm='SAMPLE_SUPPLY_TYPE' order by sgprmdet_disp_nm";
			Query query = em.createNativeQuery(q, Tuple.class);
			List<Tuple> tuples = query.getResultList();
			if (tuples != null && !tuples.isEmpty()) {
				for (Tuple t : tuples) {
					SG_d_parameters_details sgdpd = new SG_d_parameters_details();
					sgdpd.setSg_prmdet_id(Long.valueOf(t.get("sgprmdet_id", Integer.class)));
					// sgdpd.setSgprmdet_nm(t.get("SGprmdet_nm",String.class));
					sgdpd.setSgprmdet_disp_nm(t.get("sgprmdet_disp_nm", String.class));
					list.add(sgdpd);
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
	public List<SG_d_parameters_details> getCity() {
		EntityManager em = null;
		List<SG_d_parameters_details> list = new ArrayList<SG_d_parameters_details>();
		try {
			em = emf.createEntityManager();
			String q = "select sgprmdet_id,sgprmdet_disp_nm from sg_d_parameters_details join SG_M_PARAMETERS sgm on sgm.sgprm_id=sgprmdet_sgprm_id "
					+ "where sgprm_disp_nm='City' order by sgprmdet_disp_nm";
			Query query = em.createNativeQuery(q, Tuple.class);
			List<Tuple> tuples = query.getResultList();
			if (tuples != null && !tuples.isEmpty()) {
				for (Tuple t : tuples) {
					SG_d_parameters_details sgdpd = new SG_d_parameters_details();
					sgdpd.setSg_prmdet_id(Long.valueOf(t.get("sgprmdet_id", Integer.class)));
					// sgdpd.setSgprmdet_nm(t.get("SGprmdet_nm",String.class));
					sgdpd.setSgprmdet_disp_nm(t.get("sgprmdet_disp_nm", String.class));
					list.add(sgdpd);
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
	public List<SG_d_parameters_details> getZone(Long divId) {
		EntityManager em = null;
		List<SG_d_parameters_details> list = new ArrayList<SG_d_parameters_details>();
		try {
			em = emf.createEntityManager();
			String q = "select sgprmdet_id,sgprmdet_disp_nm from sg_d_parameters_details join SG_M_PARAMETERS sgm on sgm.sgprm_id=sgprmdet_sgprm_id "
					+ "where sgprm_disp_nm='Zone' and SGprmdet_Num1 =:divId order by sgprmdet_disp_nm";
			Query query = em.createNativeQuery(q, Tuple.class);
			query.setParameter("divId", divId);
			List<Tuple> tuples = query.getResultList();
			if (tuples != null && !tuples.isEmpty()) {
				for (Tuple t : tuples) {
					SG_d_parameters_details sgdpd = new SG_d_parameters_details();
					sgdpd.setSg_prmdet_id(Long.valueOf(t.get("sgprmdet_id", Integer.class)));
					// sgdpd.setSgprmdet_nm(t.get("SGprmdet_nm",String.class));
					sgdpd.setSgprmdet_disp_nm(t.get("sgprmdet_disp_nm", String.class));
					list.add(sgdpd);
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
	public List<SG_d_parameters_details> getClassList() {
		EntityManager em = null;
		List<SG_d_parameters_details> list = new ArrayList<SG_d_parameters_details>();
		try {
			em = emf.createEntityManager();
			String q = "select sgprmdet_id,sgprmdet_disp_nm from sg_d_parameters_details join SG_M_PARAMETERS sgm on sgm.sgprm_id=sgprmdet_sgprm_id "
					+ "where sgprm_disp_nm='Class' order by sgprmdet_disp_nm";
			Query query = em.createNativeQuery(q, Tuple.class);
			List<Tuple> tuples = query.getResultList();
			if (tuples != null && !tuples.isEmpty()) {
				for (Tuple t : tuples) {
					SG_d_parameters_details sgdpd = new SG_d_parameters_details();
					sgdpd.setSg_prmdet_id(Long.valueOf(t.get("sgprmdet_id", Integer.class)));
					// sgdpd.setSgprmdet_nm(t.get("SGprmdet_nm",String.class));
					sgdpd.setSgprmdet_disp_nm(t.get("sgprmdet_disp_nm", String.class));
					list.add(sgdpd);
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
	public List<SG_d_parameters_details> getFsType() {
		EntityManager em = null;
		List<SG_d_parameters_details> list = new ArrayList<SG_d_parameters_details>();
		try {
			em = emf.createEntityManager();
			String q = "select sgprmdet_id,sgprmdet_disp_nm from sg_d_parameters_details join SG_M_PARAMETERS sgm on sgm.sgprm_id=sgprmdet_sgprm_id "
					+ "where sgprm_disp_nm='FS_TYPE' order by sgprmdet_disp_nm";
			Query query = em.createNativeQuery(q, Tuple.class);
			List<Tuple> tuples = query.getResultList();
			if (tuples != null && !tuples.isEmpty()) {
				for (Tuple t : tuples) {
					SG_d_parameters_details sgdpd = new SG_d_parameters_details();
					sgdpd.setSg_prmdet_id(Long.valueOf(t.get("sgprmdet_id", Integer.class)));
					// sgdpd.setSgprmdet_nm(t.get("SGprmdet_nm",String.class));
					sgdpd.setSgprmdet_disp_nm(t.get("sgprmdet_disp_nm", String.class));
					list.add(sgdpd);
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
	public List<SG_d_parameters_details> getFsTypeInd() {
		EntityManager em = null;
		List<SG_d_parameters_details> list = new ArrayList<SG_d_parameters_details>();
		try {
			em = emf.createEntityManager();
			String q = "select sgprmdet_id,sgprmdet_disp_nm,SGprmdet_Text1 from sg_d_parameters_details join SG_M_PARAMETERS sgm on sgprm_id=sgprmdet_sgprm_id"
					+ " where sgprm_disp_nm='FIELDSTAFF_TYPE_IND' order by sgprmdet_disp_nm";
			Query query = em.createNativeQuery(q, Tuple.class);
			List<Tuple> tuples = query.getResultList();
			if (tuples != null && !tuples.isEmpty()) {
				for (Tuple t : tuples) {
					SG_d_parameters_details sgdpd = new SG_d_parameters_details();
					sgdpd.setSg_prmdet_id(Long.valueOf(t.get("sgprmdet_id", Integer.class)));
					// sgdpd.setSgprmdet_nm(t.get("SGprmdet_nm",String.class));
					sgdpd.setSgprmdet_disp_nm(t.get("sgprmdet_disp_nm", String.class));
					sgdpd.setSgprmdet_text1(t.get("sgprmdet_text1", String.class));
					list.add(sgdpd);
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
	public List<Object> getParameterIdName(String type, String text) {
		EntityManager em = null;
		List<Object> list = null;
		try {
			StringBuffer sb = new StringBuffer();
			sb.append(
					" SELECT     SGprmdet_id,SGprmdet_NM,Substring(SGprmdet_disp_nm,1,25)  SGprmdet_disp_nm,SGprmdet_Text1");
			sb.append(" FROM         SG_d_parameters_details");
			sb.append(" JOIN [dbo].[SG_m_parameters] ON SGprmdet_SGprm_id=[SGprm_id]");
			sb.append(" WHERE SGPRM_DISP_NM='" + type + "'");
			sb.append(" AND SGPRMDET_STATUS='A'");
			if (text != null)
				sb.append(" AND  SGprmdet_Text1='" + text + "'   ORDER BY  SGprmdet_disp_nm");
			else
				sb.append(" ORDER BY SGprmdet_NM ");
			Query query = entityManager.createNativeQuery(sb.toString(), Tuple.class);
			List<Tuple> tuples = query.getResultList();
			if (tuples != null && !tuples.isEmpty()) {
				list = new ArrayList<>();
				Parameter object = null;
				for (Tuple t : tuples) {
					object = new Parameter();
					object.setId(t.get("SGprmdet_NM", String.class));
					object.setName(t.get("SGprmdet_disp_nm", String.class));
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
//    @Override
// 	public List<SG_d_parameters_details> getProductSubType1() {
// 		EntityManager em = null;
// 		List<SG_d_parameters_details> list = new ArrayList<SG_d_parameters_details>();
// 		try {
// 			em = emf.createEntityManager();
// 			String q="select sgprmdet_id,sgprmdet_disp_nm from sg_d_parameters_details join SG_M_PARAMETERS sgm on sgprm_id=sgprmdet_sgprm_id where sgprm_disp_nm='PRODUCT_SUB_TYPE' order by sgprmdet_id";
// 			Query query = em.createNativeQuery(q,Tuple.class);
// 			List<Tuple> tuples = query.getResultList();			
// 			if (tuples != null && !tuples.isEmpty()) {
// 				for (Tuple t : tuples) {
// 					SG_d_parameters_details sgdpd=new SG_d_parameters_details();
// 					sgdpd.setSg_prmdet_id(Long.valueOf(t.get("sgprmdet_id",Integer.class)));
// 					//sgdpd.setSgprmdet_nm(t.get("SGprmdet_nm",String.class));
// 					sgdpd.setSgprmdet_disp_nm(t.get("sgprmdet_disp_nm",String.class));
// 					list.add(sgdpd);
// 				}
// 				if(!list.isEmpty() && list.size() > 0)
// 					return list;
// 				}
// 			} finally {
// 				if (em != null) { em.close(); }
// 		}
// 		return list;
// 	}

//	@Override
//	public List<SG_d_parameters_details> getParaDetailsByParaType(String paraType) throws Exception {
//		EntityManager em = null;
//		List<SG_d_parameters_details> list = null;
//		try {
//			em = emf.createEntityManager();
//			StringBuffer sb = new StringBuffer();
//			sb.append(" SELECT sd.sgprmdet_text1 AS sgprmdet_text1, sd.sgprmdet_nm AS sgprmdet_nm, sd.sgprmdet_disp_nm AS sgprmdet_disp_nm FROM SG_d_parameters_details sd ");
//			sb.append(" JOIN SG_m_parameters sg on sd.sgprmdet_sgprm_id=sg.sgprm_id ");
//			sb.append(" WHERE sg.sgprm_nm = :paraType AND sd.sgprmdet_status='A' ORDER BY sd.sgprmdet_disp_nm");
//			Query query = em.createQuery(sb.toString(),Tuple.class);
//			query.setParameter("paraType", paraType);
//			List<Tuple> tuples = query.getResultList();
//			if (tuples != null && !tuples.isEmpty()) {
//				list = new ArrayList<>();
//				SG_d_parameters_details object = null;
//				for (Tuple t : tuples) {
//					object = new SG_d_parameters_details();
//					object.setSgprmdet_text1(t.get("sgprmdet_text1", String.class));
//					object.setSgprmdet_nm(t.get("sgprmdet_nm", String.class));
//					object.setSgprmdet_disp_nm(t.get("sgprmdet_disp_nm", String.class));
//					list.add(object);
//				}
//			if(!list.isEmpty() && list.size() > 0)
//				return list;
//			}
//		} finally {
//			if (em != null) { em.close(); }
//		}
//		return list;
//	}

	@Override
	public List<SG_d_parameters_details> getParaDetailsByParaType(String paraType) throws Exception {
		EntityManager em = null;
		List<SG_d_parameters_details> list = null;
		try {
			em = emf.createEntityManager();
			StringBuffer sb = new StringBuffer();

//			sb.append(" SELECT sd.sgprmdet_text1 AS sgprmdet_text1, sd.sgprmdet_nm AS sgprmdet_nm, sd.sgprmdet_disp_nm AS sgprmdet_disp_nm FROM SG_d_parameters_details sd ");
//			sb.append(" JOIN SG_m_parameters sg on sd.sgprmdet_sgprm_id=sg.sgprm_id ");
//			sb.append(" WHERE sg.sgprm_nm = :paraType AND sd.sgprmdet_status='A' ORDER BY sd.sgprmdet_disp_nm");

			sb.append(
					"  SELECT sd.sg_prmdet_id  AS sgprmdet_id , sd.sgprmdet_text1 AS sgprmdet_text1, sd.sgprmdet_nm AS sgprmdet_nm, sd.sgprmdet_disp_nm AS sgprmdet_disp_nm");
			sb.append(" FROM SG_d_parameters_details sd");
			sb.append(" JOIN SG_m_parameters sg on sd.sgprmdet_sgprm_id=sg.sgprm_id");
			sb.append(" WHERE UPPER(sg.sgprm_nm) =:paraType AND sd.sgprmdet_status='A' ORDER BY sd.sgprmdet_disp_nm");

//			"SELECT sd.sgprmdet_id,sd.sgprmdet_text1 AS sgprmdet_text1, sd.sgprmdet_nm AS sgprmdet_nm, sd.sgprmdet_disp_nm AS sgprmdet_disp_nm \r\n"
//			+ "FROM SG_d_parameters_details sd\r\n"
//			+ "JOIN SG_m_parameters sg on sd.sgprmdet_sgprm_id=sg.sgprm_id\r\n"
//			+ "WHERE sg.sgprm_nm = 'Manufacturing Location' AND sd.sgprmdet_status='A' ORDER BY sd.sgprmdet_disp_nm"

			Query query = em.createQuery(sb.toString(), Tuple.class);
			query.setParameter("paraType", paraType);
			List<Tuple> tuples = query.getResultList();
			if (tuples != null && !tuples.isEmpty()) {
				list = new ArrayList<>();
				SG_d_parameters_details object = null;
				for (Tuple t : tuples) {
					object = new SG_d_parameters_details();
					object.setSgprmdet_text1(t.get("sgprmdet_text1", String.class));
					object.setSgprmdet_nm(t.get("sgprmdet_nm", String.class));
					object.setSgprmdet_disp_nm(t.get("sgprmdet_disp_nm", String.class));
//					System.out.println(t.get("sgprmdet_id",Long.class));
					object.setSg_prmdet_id(t.get("sgprmdet_id", Long.class));

					list.add(object);
				}
				if (!list.isEmpty() && list.size() > 0)

					System.err.println(list);
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
	public List<SG_d_parameters_details> getActiveProductSubType() {
		EntityManager em = null;
		List<SG_d_parameters_details> list = new ArrayList<SG_d_parameters_details>();
		try {
			em = emf.createEntityManager();
			String q = "SELECT SGprmdet_id,SGprmdet_disp_nm FROM SG_d_parameters_details JOIN [dbo].[SG_m_parameters] ON SGprmdet_SGprm_id=[SGprm_id] "
					+ " WHERE SGPRM_DISP_NM='PRODUCT_SUB_TYPE' AND SGPRMDET_STATUS='A' ORDER BY  SGprmdet_id";
			Query query = em.createNativeQuery(q, Tuple.class);

			List<Tuple> tuples = query.getResultList();
			if (tuples != null && !tuples.isEmpty()) {
				for (Tuple t : tuples) {
					SG_d_parameters_details sgdpd = new SG_d_parameters_details();
					sgdpd.setSg_prmdet_id(Long.valueOf(t.get("sgprmdet_id", Integer.class)));
					// sgdpd.setSgprmdet_nm(t.get("SGprmdet_nm",String.class));
					sgdpd.setSgprmdet_disp_nm(t.get("sgprmdet_disp_nm", String.class));
					list.add(sgdpd);
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
	public List<SG_d_parameters_details> getParameteDetails(String para_type) {
		EntityManager em = null;
		List<SG_d_parameters_details> list = null;
		try {
			CriteriaBuilder builder = entityManager.getCriteriaBuilder();
			CriteriaQuery<SG_d_parameters_details> criteriaQuery = builder.createQuery(SG_d_parameters_details.class);
			Root<SG_d_parameters_details> root = criteriaQuery.from(SG_d_parameters_details.class);
			criteriaQuery.select(root)// max upload_cycle
					.where(builder.equal(root.get(SG_d_parameters_details_.sgprmdet_status), "A"),
							builder.equal(root.get(SG_d_parameters_details_.sgprmdet_sgprm_id), 32L));
			list = entityManager.createQuery(criteriaQuery).getResultList();
			if (list == null)
				return list;
		} catch (Exception e) {
			System.out.println("Error Occurred :" + e.getMessage());// uncomment asneeded --;
		} finally {
			if (em != null) {
				em.close();
			}
		}
		return list;
	}

	@Override
	public List<SG_d_parameters_details> getParameterDetailByDispNm(String paraType) {
		EntityManager em = null;
		List<SG_d_parameters_details> list = new ArrayList<SG_d_parameters_details>();
		try {
			em = emf.createEntityManager();
			String q = "select sgprmdet_id,sgprmdet_disp_nm,SGprmdet_Text1 from sg_d_parameters_details join SG_M_PARAMETERS sgm on sgprm_id=sgprmdet_sgprm_id"
					+ " where sgprm_disp_nm=:paraType order by sgprmdet_disp_nm";
			Query query = em.createNativeQuery(q, Tuple.class);
			query.setParameter("paraType", paraType);
			List<Tuple> tuples = query.getResultList();
			if (tuples != null && !tuples.isEmpty()) {
				for (Tuple t : tuples) {
					SG_d_parameters_details sgdpd = new SG_d_parameters_details();
					sgdpd.setSg_prmdet_id(Long.valueOf(t.get("sgprmdet_id", Integer.class)));
					// sgdpd.setSgprmdet_nm(t.get("SGprmdet_nm",String.class));
					sgdpd.setSgprmdet_disp_nm(t.get("sgprmdet_disp_nm", String.class));
					sgdpd.setSgprmdet_text1(t.get("sgprmdet_text1", String.class));
					list.add(sgdpd);
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
	public SG_d_parameters_details getStateByStateName(String state) throws Exception {
		EntityManager em = null;
		List<SG_d_parameters_details> list = null;
		try {

			StringBuffer sb = new StringBuffer();
			sb.append(
					"SELECT sgd.sg_prmdet_id as sgprmdet_id,UPPER(RTRIM(sgd.sgprmdet_nm)) as sgprmdet_nm FROM SG_d_parameters_details sgd");
			sb.append(" join SG_m_parameters sp on sp.sgprm_id=sgd.sgprmdet_sgprm_id");
			sb.append(" where upper(RTRIM(sp.sgprm_nm))='STATE' AND UPPER(RTRIM(sgd.sgprmdet_nm))=:state");
			Query query = entityManager.createQuery(sb.toString(), Tuple.class);
			query.setParameter("state", state);
			List<Tuple> tuples = query.getResultList();
			if (tuples != null && !tuples.isEmpty()) {
				list = new ArrayList<>();
				SG_d_parameters_details object = null;
				for (Tuple t : tuples) {
					object = new SG_d_parameters_details();
					object.setSg_prmdet_id(t.get("sgprmdet_id", Long.class));
					object.setSgprmdet_nm(t.get("sgprmdet_nm", String.class));
					list.add(object);
				}
				if (!list.isEmpty() && list.size() > 0)
					return list.get(0);
			}
		} finally {

		}
		return null;
	}

	@Override
	public List<String> getstates() throws Exception {
		EntityManager em = null;
		List<String> list = null;
		try {
			em = emf.createEntityManager();
			StringBuffer sb = new StringBuffer();
			sb.append("SELECT UPPER(RTRIM(sgd.sgprmdet_nm)) as sgprmdet_nm FROM SG_d_parameters_details sgd");
			sb.append(" join SG_m_parameters sp on sp.sgprm_id=sgd.sgprmdet_sgprm_id");
			sb.append(" where upper(RTRIM(sp.sgprm_nm))='STATE'");
			Query query = em.createQuery(sb.toString(), Tuple.class);
			List<Tuple> tuples = query.getResultList();
			if (tuples != null && !tuples.isEmpty()) {
				list = new ArrayList<>();
				for (Tuple t : tuples) {
					list.add(t.get("sgprmdet_nm", String.class).trim());
				}
				if (!list.isEmpty() && list.size() > 0)
					return list;
			}
		} finally {
			if (em != null) {
				em.close();
			}
		}
		return null;
	}

	@Override
	public List<SG_d_parameters_details> getDispatchParameters() {

		EntityManager em = null;
		List<SG_d_parameters_details> list = new ArrayList<SG_d_parameters_details>();
		try {
			em = emf.createEntityManager();
			StringBuffer sb = new StringBuffer();
			sb.append(
					"select sgd.sgprmdet_nm as sgprmdet_nm,sgd.sgprmdet_disp_nm as sgprmdet_disp_nm from SG_d_parameters_details sgd");
			sb.append(" join SG_m_parameters sm on sm.SGprm_id = sgd.SGprmdet_SGprm_id");
			sb.append(" where upper(sgprm_nm)='SALABLE_NON_SALABLE'");

			Query query = em.createNativeQuery(sb.toString(), Tuple.class);

			List<Tuple> tuples = query.getResultList();
			if (tuples != null && !tuples.isEmpty()) {
				for (Tuple t : tuples) {
					SG_d_parameters_details sgdpd = new SG_d_parameters_details();
					sgdpd.setSgprmdet_disp_nm(t.get("sgprmdet_disp_nm", String.class));
					list.add(sgdpd);
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
	public List<SG_d_parameters_details> getRateParameters() {
		// TODO Auto-generated method stub
		EntityManager em = null;
		List<SG_d_parameters_details> list = new ArrayList<SG_d_parameters_details>();

		try {
			em = emf.createEntityManager();
			StringBuffer sb = new StringBuffer();
			sb.append(
					"select sgd.sgprmdet_nm as sgprmdet_nm,sgd.sgprmdet_disp_nm as sgprmdet_disp_nm from SG_d_parameters_details sgd");
			sb.append(" join SG_m_parameters sm on sm.SGprm_id = sgd.SGprmdet_SGprm_id");
			sb.append(" where upper(sgprm_nm)='REPORTING_RATES'");

			Query query = em.createNativeQuery(sb.toString(), Tuple.class);

			List<Tuple> tuples = query.getResultList();
			if (tuples != null && !tuples.isEmpty()) {
				for (Tuple t : tuples) {
					SG_d_parameters_details sgdpd = new SG_d_parameters_details();
					sgdpd.setSgprmdet_disp_nm(t.get("sgprmdet_disp_nm", String.class));
					sgdpd.setSgprmdet_nm(t.get("sgprmdet_nm", String.class));
					list.add(sgdpd);
				}
				if (!list.isEmpty() && list.size() > 0)
					return list;
			}

		} catch (Exception e) {
			// TODO: handle exception
			throw e;
		}
		return null;
	}

	@Override
	public List<SG_d_parameters_details> getTdsCrmPercentageParameter() throws Exception {
		// TODO Auto-generated method stub
		EntityManager em = null;
		List<SG_d_parameters_details> list = new ArrayList<SG_d_parameters_details>();

		try {
			em = emf.createEntityManager();
			StringBuffer sb = new StringBuffer();
			sb.append(
					"select sgd.SGprmdet_nm as SGprmdet_nm,sgd.SGprmdet_Num1 as SGprmdet_Num1 from SG_d_parameters_details sgd");
			sb.append(" join SG_m_parameters sm on sm.SGprm_id = sgd.SGprmdet_SGprm_id");
			sb.append(" where upper(sgprm_nm)='TDS194R_TAX_PERC'");

			Query query = em.createNativeQuery(sb.toString(), Tuple.class);

			List<Tuple> tuples = query.getResultList();
			if (tuples != null && !tuples.isEmpty()) {
				for (Tuple t : tuples) {
					SG_d_parameters_details sgdpd = new SG_d_parameters_details();
					sgdpd.setSgprmdet_num1(t.get("SGprmdet_Num1", Integer.class).longValue());
					sgdpd.setSgprmdet_nm(t.get("SGprmdet_nm", String.class));
					list.add(sgdpd);
				}
				if (!list.isEmpty() && list.size() > 0)
					return list;
			}

		} catch (Exception e) {
			// TODO: handle exception
			throw e;
		}
		return null;
	}

	@Override
	public List<Sfa_Field_Master> getBulkse(String geog_lvl1_hq) {
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
							root.get(Sfa_Field_Master_.geog_lvl1_hq).in(Arrays.asList(geog_lvl1_hq.split(","))))
					.orderBy(builder.asc(root.get(Sfa_Field_Master_.fs_name)));
			list = em.createQuery(criteriaQuery).getResultList();

		} finally {
			if (em != null) {
				em.close();
			}
		}
		return list;
	}

	@Override
	public List<String> getDefaultParameterEmailsByTranType(String trantype) throws Exception {
		// TODO Auto-generated method stub
		EntityManager em = null;
		List<String> list = null;
		try {
			em = emf.createEntityManager();
			StringBuffer sb = new StringBuffer();
			sb.append("select sgd.SGprmdet_disp_nm as SGprmdet_disp_nm from SG_d_parameters_details sgd");
			sb.append(" join SG_m_parameters sm on sm.SGprm_id = sgd.SGprmdet_SGprm_id");
			sb.append(" where upper(sgprm_nm)=:trantype and sgd.SGprmdet_status='A'");

			Query query = em.createNativeQuery(sb.toString(), Tuple.class);
			query.setParameter("trantype", trantype);
			List<Tuple> tlist = query.getResultList();

			if (tlist != null && !tlist.isEmpty()) {
				list = new ArrayList<String>();
				for (Tuple t : tlist) {
					SG_d_parameters_details sgdpd = new SG_d_parameters_details();
					list.add(t.get("SGprmdet_disp_nm", String.class));
				}
				if (!list.isEmpty() && list.size() > 0)
					return list;
			}

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

	@Override
	public String IsOtp_Captcha_Req(String paratype) throws Exception {
		// TODO Auto-generated method stub
		EntityManager em = null;
		String ind = null;
		try {
			em = emf.createEntityManager();
			StringBuffer sb = new StringBuffer();
			sb.append("select sgd.SGprmdet_Text1");
			sb.append(" from SG_d_parameters_details sgd");
			sb.append(" join SG_m_parameters sgm on sgm.sgprm_id = sgd.SGprmdet_SGprm_id");
			sb.append(" where UPPER(SGprm_disp_nm) = :paratype");
			sb.append(" ORDER BY SGPRMDET_NM");

			Query query = em.createNativeQuery(sb.toString());
			query.setParameter("paratype", paratype);
			ind = (String) query.getSingleResult();
		} catch (Exception e) {
			// TODO: handle exception
			throw e;
		} finally {
			if (em != null) {
				em.close();
			}
		}
		return ind;
	}

	@Override
	public String getSrtAndEmailReqInd(String trantype) throws Exception {
		// TODO Auto-generated method stub
		String ind = null;
		EntityManager em = null;
		try {
			em = emf.createEntityManager();
			StringBuffer sb = new StringBuffer();
			sb.append(" select sgd.SGprmdet_Text1");
			sb.append(" from SG_d_parameters_details sgd");
			sb.append(" join SG_m_parameters sgm on sgm.sgprm_id = sgd.SGprmdet_SGprm_id");
			sb.append(" where UPPER(SGprm_disp_nm) = :trantype");
			sb.append(" ORDER BY SGPRMDET_NM");

			Query query = em.createNativeQuery(sb.toString());
			query.setParameter("trantype", trantype);

			ind = (String) query.getSingleResult();
		} catch (Exception e) {
			// TODO: handle exception
			throw e;
		}
		return ind;
	}

	@Override
	public SG_d_parameters_details getParameterDataByDisplayNameWithJoin(String paraType) {
		EntityManager em = null;
		SG_d_parameters_details sgdpd = null;
		try {
			em = emf.createEntityManager();
			String q = " SELECT SGD.SGprmdet_Text1 FROM SG_d_parameters_details SGD"
					+ " JOIN  SG_m_parameters SM ON SM.SGPRM_ID=SGD.SGprmdet_SGprm_id" + " WHERE SGPRM_NM='" + paraType
					+ "'";
			Query query = em.createNativeQuery(q, Tuple.class);
			List<Tuple> tuples = query.getResultList();
			if (tuples != null && !tuples.isEmpty()) {

				sgdpd = new SG_d_parameters_details();
				sgdpd.setSgprmdet_text1(tuples.get(0).get("SGprmdet_Text1", String.class));
			}

		} finally {
			if (em != null) {
				em.close();
			}
		}
		return sgdpd;
	}

	@Override
	public String getGprmIndicator() throws Exception {
		// TODO Auto-generated method stub
		EntityManager em = null;
		List<SG_d_parameters_details> list = new ArrayList<SG_d_parameters_details>();
		String ind = null;

		try {
			em = emf.createEntityManager();
			StringBuffer sb = new StringBuffer();
			sb.append(" select sgd.SGprmdet_Text1 ");
			sb.append(" from SG_d_parameters_details sgd ");
			sb.append(" join SG_m_parameters sgm on sgm.sgprm_id = sgd.SGprmdet_SGprm_id ");
			sb.append(" where UPPER(SGprm_disp_nm) = 'LR_PRD_TYPE_ALLOW' ");
			sb.append(" ORDER BY SGPRMDET_NM ");

			Query query = em.createNativeQuery(sb.toString());
			ind = (String) query.getSingleResult();

		} catch (Exception e) {
			// TODO: handle exception
			throw e;
		}
		return ind;
	}

	@Override
	public String getGprmIndicatorForPfz() throws Exception {
		// TODO Auto-generated method stub
		EntityManager em = null;
		String ind = null;

		try {
			em = emf.createEntityManager();
			StringBuffer sb = new StringBuffer();
			sb.append(" select sgd.SGprmdet_Text1 ");
			sb.append(" from SG_d_parameters_details sgd ");
			sb.append(" join SG_m_parameters sgm on sgm.sgprm_id = sgd.SGprmdet_SGprm_id ");
			sb.append(" where UPPER(SGprm_disp_nm) = 'VAL_IN_IAA_REPO_REQD' ");
			sb.append(" ORDER BY SGPRMDET_NM ");

			Query query = em.createNativeQuery(sb.toString());
			ind = (String) query.getSingleResult();

		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return ind;
	}

	@Override
	public List<SG_d_parameters_details> getDocClass() throws Exception {
		// TODO Auto-generated method stub
		EntityManager em = null;
		List<SG_d_parameters_details> list = new ArrayList<SG_d_parameters_details>();

		try {
			em = emf.createEntityManager();
			StringBuffer sb = new StringBuffer();
			sb.append("select SGprmdet_nm,SGprmdet_disp_nm from SG_d_parameters_details sgd ");
			sb.append("join SG_m_parameters sgm on sgm.sgprm_id = sgd.SGprmdet_SGprm_id ");
			sb.append("where UPPER(SGprm_disp_nm) = 'DOCTOR_CLASS_CLS' ORDER BY SGPRMDET_NM ");

			Query query = em.createNativeQuery(sb.toString(), Tuple.class);

			List<Tuple> tuples = query.getResultList();
			if (tuples != null && !tuples.isEmpty()) {
				for (Tuple t : tuples) {
					SG_d_parameters_details sgdpd = new SG_d_parameters_details();
					sgdpd.setSgprmdet_disp_nm(t.get("SGprmdet_disp_nm", String.class));
					sgdpd.setSgprmdet_nm(t.get("SGprmdet_nm", String.class));
					list.add(sgdpd);
				}
				if (!list.isEmpty() && list.size() > 0)
					return list;
			}

		} catch (Exception e) {
			// TODO: handle exception
			throw e;
		}
		return null;
	}

	@Override
	public List<SG_d_parameters_details> getSchemeType() throws Exception {
		// TODO Auto-generated method stub
		EntityManager em = null;
		List<SG_d_parameters_details> list = new ArrayList<SG_d_parameters_details>();

		try {
			em = emf.createEntityManager();
			StringBuffer sb = new StringBuffer();
			sb.append("select SGprmdet_nm,SGprmdet_disp_nm from SG_d_parameters_details sgd ");
			sb.append(" join SG_m_parameters sgm on sgm.sgprm_id = sgd.SGprmdet_SGprm_id ");
			sb.append(" where UPPER(SGprm_disp_nm) = 'SCHEME_TYPE_SCY' ORDER BY SGPRMDET_NM ");

			Query query = em.createNativeQuery(sb.toString(), Tuple.class);

			List<Tuple> tuples = query.getResultList();
			if (tuples != null && !tuples.isEmpty()) {
				for (Tuple t : tuples) {
					SG_d_parameters_details sgdpd = new SG_d_parameters_details();
					sgdpd.setSgprmdet_disp_nm(t.get("SGprmdet_disp_nm", String.class));
					sgdpd.setSgprmdet_nm(t.get("SGprmdet_nm", String.class));
					list.add(sgdpd);
				}
				if (!list.isEmpty() && list.size() > 0)
					return list;
			}

		} catch (Exception e) {
			// TODO: handle exception
			throw e;
		}
		return null;
	}

	@Override
	public List<CrmSchemeMaster> getScheme_Code() throws Exception {
		List<CrmSchemeMaster> list = null;
		EntityManager em = null;
		try {
			em = emf.createEntityManager();
			CriteriaBuilder builder = em.getCriteriaBuilder();
			CriteriaQuery<CrmSchemeMaster> criteriaQuery = builder.createQuery(CrmSchemeMaster.class);
			Root<CrmSchemeMaster> root = criteriaQuery.from(CrmSchemeMaster.class);
			criteriaQuery.multiselect(root.get(CrmSchemeMaster_.crm_scheme_code));

			list = em.createQuery(criteriaQuery).getResultList();
			System.out.println("Cust Name list " + list.size());
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
	public String articleAutoApproval() throws Exception {
		EntityManager em = null;
		String art_auto_appr_ind = null;
		try {
			em = emf.createEntityManager();
			StringBuffer sb = new StringBuffer();
			sb.append(" select SGPRMDET_TEXT1");
			sb.append(" from SG_d_parameters_details sgd");
			sb.append(" join SG_m_parameters sgm on sgm.sgprm_id = sgd.SGprmdet_SGprm_id");
			sb.append(" where UPPER(sgm.SGprm_disp_nm) = 'ART_SCM_REQ_SUPR_APPROVAL_IND'");
			sb.append(" ORDER BY sgd.SGPRMDET_NM");
			Query query = em.createNativeQuery(sb.toString());

			art_auto_appr_ind = (String) query.getSingleResult();
		} finally {
			if (em != null) {
				em.close();
			}
		}
		return art_auto_appr_ind;
	}

	@Override
	public String dispatchAutoApproval() throws Exception {
		EntityManager em = null;
		String dsp_auto_appr_ind = null;
		try {
			em = emf.createEntityManager();
			StringBuffer sb = new StringBuffer();
			sb.append(" select SGPRMDET_TEXT1");
			sb.append(" from SG_d_parameters_details sgd");
			sb.append(" join SG_m_parameters sgm on sgm.sgprm_id = sgd.SGprmdet_SGprm_id");
			sb.append(" where UPPER(sgm.SGprm_disp_nm) = 'DISP_SUPR_APPROVAL_IND'");
			sb.append(" ORDER BY sgd.SGPRMDET_NM");
			Query query = em.createNativeQuery(sb.toString());

			dsp_auto_appr_ind = (String) query.getSingleResult();
		} catch (Exception e) {
			// TODO: handle exception
		} finally {
			if (em != null) {
				em.close();
			}
		}
		return dsp_auto_appr_ind;
	}

	@Override
	public String getGrnRefNoInd() throws Exception {
		EntityManager em = null;
		try {
			em = emf.createEntityManager();
			StringBuilder sb = new StringBuilder();
			sb.append(" select SGPRMDET_TEXT1 from SG_d_parameters_details sgd");
			sb.append(" join SG_m_parameters sgm on sgm.sgprm_id = sgd.SGprmdet_SGprm_id ");
			sb.append(" where UPPER(sgm.SGprm_disp_nm) = 'GRN_REF_NO_REQ_IND' ORDER BY sgd.SGPRMDET_NM");
			Query query = em.createNativeQuery(sb.toString());
			return (String) query.getSingleResult();
		} finally {
			if (em != null)
				em.close();
		}
	}
	
	@Override
	public List<SG_d_parameters_details> getVehicleRecdTime() throws Exception {
		EntityManager em = null;
		List<SG_d_parameters_details> list = null;
		try {
			em = emf.createEntityManager();
			StringBuffer sb = new StringBuffer();
			sb.append(" SELECT sgd.SGprmdet_nm,SGprmdet_disp_nm,SGPRMDET_TEXT1 from SG_d_parameters_details sgd ");
			sb.append("join SG_m_parameters sgm on sgm.sgprm_id = sgd.SGprmdet_SGprm_id ");
			sb.append("where UPPER(sgm.SGprm_disp_nm) = 'VEHICLE RECEIVED TIME' ORDER BY sgd.SGPRMDET_NM");
			
			Query query = em.createNativeQuery(sb.toString(), Tuple.class);
			List<Tuple> tuples = query.getResultList();

			if (tuples != null && !tuples.isEmpty()) {
				list = new ArrayList<>();
				SG_d_parameters_details object = null;
				for (Tuple t : tuples) {
					object = new SG_d_parameters_details();
					object.setSgprmdet_nm(t.get("SGprmdet_nm", String.class));
					object.setSgprmdet_disp_nm(t.get("SGprmdet_disp_nm", String.class));
					object.setSgprmdet_text1(t.get("SGPRMDET_TEXT1", String.class));
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
}
