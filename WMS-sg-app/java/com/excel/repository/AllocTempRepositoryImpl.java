package com.excel.repository;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.NonUniqueResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.excel.model.Alloc_Remark;
import com.excel.model.Alloc_Temp;
import com.excel.model.Alloc_Temp_;
import com.excel.model.Alloc_Temp_Header;
import com.excel.utility.CodifyErrors;

@Repository
public class AllocTempRepositoryImpl implements AllocTempRepository{
	
	@PersistenceContext private EntityManager entityManager;
	
	@Override
	public Long getMaxCycle(String division_id, String period_code,String company, String fin_year) throws Exception {
		EntityManager em = null;
		Integer  count = 0;
		System.out.println(division_id);
		System.out.println(period_code);
		System.out.println(company);
		System.out.println(fin_year);
		try {
		    System.out.println(division_id+period_code+company+fin_year);
			CriteriaBuilder builder = entityManager.getCriteriaBuilder();
			CriteriaQuery<Number> criteriaQuery = builder.createQuery(Number.class);
			Root<Alloc_Temp> root = criteriaQuery.from(Alloc_Temp.class);
			criteriaQuery.select(builder.max(root.get(Alloc_Temp_.upload_cycle)))//max upload_cycle
			.where(builder.equal(root.get(Alloc_Temp_.div_id), division_id),
					builder.equal(root.get(Alloc_Temp_.period_code), period_code),
					builder.equal(root.get(Alloc_Temp_.company), company),
					builder.equal(root.get(Alloc_Temp_.fin_year_id), fin_year));
	
			count =(Integer) entityManager.createQuery(criteriaQuery).getSingleResult();
			if(count!=null)
				return Long.valueOf(count);
			else
				return 0L;
		}catch (Exception e) {
			System.out.println("Error Occurred :" +e.getMessage());// uncomment asneeded --;
		}
		finally {
			if (em != null) { em.close(); }
		}
		return null;
	}
	

	@Override
	public List<Alloc_Temp> getDataByDiv(Long division_id,String period_code,String upload_status,String ucycle,Long alloc_temp_hd_id) {
		EntityManager em = null;
		List<Alloc_Temp>  list = null;
		System.out.println("division_id "+division_id);
		System.out.println("period_code "+period_code);
		System.out.println("upload_status "+upload_status);
		System.out.println("ucycle "+ucycle);
		System.out.println("alloc_temp_hd_id "+alloc_temp_hd_id);
		try {
			CriteriaBuilder builder = entityManager.getCriteriaBuilder();
			CriteriaQuery<Alloc_Temp> criteriaQuery = builder.createQuery(Alloc_Temp.class);
			Root<Alloc_Temp> root = criteriaQuery.from(Alloc_Temp.class);
			criteriaQuery.select(root)//max upload_cycle
			.where(builder.equal(root.get(Alloc_Temp_.div_id), division_id),
					builder.equal(root.get(Alloc_Temp_.period_code), period_code),
					builder.equal(root.get(Alloc_Temp_.upload_cycle), Integer.valueOf(ucycle)),
					builder.equal(root.get(Alloc_Temp_.upload_status), upload_status.charAt(0)),
					builder.equal(root.get(Alloc_Temp_.alloc_Temp_Header), alloc_temp_hd_id));
	
			list=entityManager.createQuery(criteriaQuery).getResultList();
			if(list!=null)
				return list;
			else
				return list;
		}catch (Exception e) {
			System.out.println("Error Occurred :" +e.getMessage());// uncomment asneeded --;
		}
		finally {
			if (em != null) { em.close(); }
		}
		return list;
	}
	
	@Override
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public void deleteRecords(Long division,String period,String upload_status,String ucycle,long alloc_temp_hd_id) throws Exception{
		try {
			StringBuffer buffer = new StringBuffer();
			buffer.append(" delete from Alloc_Temp ");
			buffer.append(" where period_code='").append(period).append("'");
			buffer.append(" and div_id=").append(division);
			buffer.append(" and upload_status='").append(upload_status).append("'");
			buffer.append(" and upload_cycle=").append(ucycle);
			buffer.append(" and alloc_temp_hd_id=").append(alloc_temp_hd_id);
			Query query = entityManager.createNativeQuery(buffer.toString());
			query.executeUpdate();
			buffer = new StringBuffer();
			buffer.append(" delete from ALLOC_TEMP_HEADER ");
			buffer.append(" where alloc_temp_hd_id=").append(alloc_temp_hd_id);
			query = entityManager.createNativeQuery(buffer.toString());
			query.executeUpdate();
			
		}catch (Exception e) {
			throw e;
		}
		
	}
	
	@Override
	public Alloc_Temp_Header getObjectAllocTempHeader(Long headerId) throws Exception {
		return this.entityManager.find(Alloc_Temp_Header.class, headerId);
	}


	@Override
	public Alloc_Remark getAlloc_Remark(Long AllocTempId) throws Exception {
		// TODO Auto-generated method stub
		Alloc_Remark remark = null;
		try {
			CriteriaBuilder builder = entityManager.getCriteriaBuilder();
			CriteriaQuery<Alloc_Remark> criteriaQuery = builder.createQuery(Alloc_Remark.class);
			Root<Alloc_Remark> root = criteriaQuery.from(Alloc_Remark.class);
			criteriaQuery.select(root)
			.where(builder.equal(root.get("alloc_gen_hd_id"), AllocTempId));
			remark=entityManager.createQuery(criteriaQuery).getSingleResult();
		}
		catch (NoResultException e) {
			// TODO: handle exception
			System.out.println("Error Occurred :" +e.getMessage());// uncomment asneeded --;
			return null;
		}
		catch (NonUniqueResultException e) {
			// TODO: handle exception
			System.out.println("Error Occurred :" +e.getMessage());// uncomment asneeded --;
			return null;
		}
		catch (Exception e) {
			// TODO: handle exception
			throw e;
		}
		return remark;
	}




}
