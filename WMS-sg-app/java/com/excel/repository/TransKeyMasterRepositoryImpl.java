package com.excel.repository;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.springframework.stereotype.Repository;

import com.excel.model.Trans_Key_Master;
import com.excel.model.Trans_key_Master_batch_wthrel;

@Repository
public class TransKeyMasterRepositoryImpl implements TransKeyMasterRepository{

	@PersistenceContext private EntityManager entityManager;
	
	@Override
	public Trans_Key_Master getTransObj(Long loc_id, String fin_year, String comp_cd, Long tran_type, String type)
			throws Exception {
		List<Trans_Key_Master> list = null;
		CriteriaBuilder builder = entityManager.getCriteriaBuilder();
		CriteriaQuery<Trans_Key_Master> query = builder.createQuery(Trans_Key_Master.class);
		Root<Trans_Key_Master> root = query.from(Trans_Key_Master.class);
		List<Predicate> predicates = new ArrayList<>();
		predicates.add(builder.equal(root.get("loc_id"), loc_id));
		predicates.add(builder.equal(root.get("company_cd"), comp_cd));
		predicates.add(builder.equal(root.get("fin_year"), fin_year));
		predicates.add(builder.equal(root.get("tran_type_id"), tran_type));
		if(type!=null){
			predicates.add(builder.equal(root.get("type"), type));
		}
		query.select(root).where(predicates.toArray(new Predicate[0]));
		list = entityManager.createQuery(query).getResultList();
		if(list != null && list.size() > 0) {
			return list.get(0);
		}
		return null;
	}

	@Override
	public Trans_Key_Master getTransObjByPrefix(Long loc_id, String fin_year, String comp_cd, String prefix)
			throws Exception {
		System.out.println("loc_id "+loc_id);
		System.out.println("fin_year "+fin_year);
		System.out.println("comp_cd "+comp_cd);
		System.out.println("prefix "+prefix);
		List<Trans_Key_Master> list = null;
		CriteriaBuilder builder = entityManager.getCriteriaBuilder();
		CriteriaQuery<Trans_Key_Master> query = builder.createQuery(Trans_Key_Master.class);
		Root<Trans_Key_Master> root = query.from(Trans_Key_Master.class);
		List<Predicate> predicates = new ArrayList<>();
		predicates.add(builder.equal(root.get("loc_id"), loc_id));
		predicates.add(builder.equal(root.get("company_cd"), comp_cd));
		predicates.add(builder.equal(root.get("fin_year"), fin_year));
		predicates.add(builder.equal(root.get("prefix"), prefix));
		query.select(root).where(predicates.toArray(new Predicate[0]));
		list = entityManager.createQuery(query).getResultList();
		if(list != null && list.size() > 0) {
			return list.get(0);
		}
		return null;
	}
	@Override
	public void update(Trans_Key_Master obj) throws Exception {
		entityManager.merge(obj);
	}

	@Override
	public Trans_key_Master_batch_wthrel getTranLastNo(Long loc_id, Long stock_point_id, String tran_type_id,
			String fin_year, String heading) throws Exception {
		
		List<Trans_key_Master_batch_wthrel> list = null;
		CriteriaBuilder builder = entityManager.getCriteriaBuilder();
		CriteriaQuery<Trans_key_Master_batch_wthrel> query = builder.createQuery(Trans_key_Master_batch_wthrel.class);
		Root<Trans_key_Master_batch_wthrel> root = query.from(Trans_key_Master_batch_wthrel.class);
		List<Predicate> predicates = new ArrayList<>();
		predicates.add(builder.equal(root.get("loc_id"), loc_id));
		predicates.add(builder.equal(root.get("fin_year"), fin_year));
		predicates.add(builder.equal(root.get("heading"), heading));
		
		query.select(root).where(predicates.toArray(new Predicate[0]));
		list = entityManager.createQuery(query).getResultList();
		if(list != null && list.size() > 0) {
			return list.get(0);
		}
		return null;
	}

}
