package com.excel.repository;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.excel.model.DoctorMaster;
import com.excel.model.DoctorMaster_;
import com.excel.model.FieldStaff;
import com.excel.model.FieldStaff_;

@Repository
public class FieldstaffAllocationUploadRepositoryImpl implements FieldstaffAllocationUploadRepository{

	@Autowired EntityManagerFactory emf;
	@PersistenceContext private EntityManager entityManager;


	
	@Override
	public FieldStaff getFieldStaffByMapCode2(String mapcode2,Long divId) throws Exception {
		// TODO Auto-generated method stub
		List<FieldStaff> list = null;
		System.out.println("mapcode2 "+mapcode2);
		try {
			CriteriaBuilder builder = entityManager.getCriteriaBuilder();
			CriteriaQuery<FieldStaff> criteriaQuery = builder.createQuery(FieldStaff.class);
			Root<FieldStaff> root = criteriaQuery.from(FieldStaff.class);
			criteriaQuery.select(root).where(
					builder.and(builder.equal(root.get(FieldStaff_.fstaff_map_code2), mapcode2)),
							builder.and(builder.equal(root.get(FieldStaff_.fs_div_id), divId)),
							builder.and(builder.equal(root.get(FieldStaff_.status), "A"))).orderBy(builder.asc(root.get(FieldStaff_.fstaff_name)));
			list = entityManager.createQuery(criteriaQuery).getResultList();
			if(list!=null && list.size()>0)
				return list.get(0);

		}
		catch (Exception e) {
			// TODO: handle exception
			throw e;
		}
		return null;
	}

}
