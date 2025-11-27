package com.excel.repository;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

import org.springframework.stereotype.Repository;

import com.excel.model.Alloc_Temp;
import com.excel.model.Alloc_Temp_;
import com.excel.model.Msr_Stock;
import com.excel.model.Msr_Stock_;
import com.excel.utility.CodifyErrors;

@Repository
public class MsrStockRepositoryImpl implements MsrStockRepository{

	@PersistenceContext private EntityManager entityManager;
	
	@Override
	public Msr_Stock getMsr_StockObj(Long fs_id, String period_code,Long product_id,String year) throws Exception {
		EntityManager em = null;
		List<Msr_Stock>  list = null;
		try {
			   
			CriteriaBuilder builder = entityManager.getCriteriaBuilder();
			CriteriaQuery<Msr_Stock> criteriaQuery = builder.createQuery(Msr_Stock.class);
			Root<Msr_Stock> root = criteriaQuery.from(Msr_Stock.class);
			criteriaQuery.select(root)//max upload_cycle
			.where(builder.equal(root.get(Msr_Stock_.fs_id), fs_id),
					builder.equal(root.get(Msr_Stock_.period_code), period_code),
					builder.equal(root.get(Msr_Stock_.prod_id), product_id),
					builder.equal(root.get(Msr_Stock_.year), year));
	
			list =entityManager.createQuery(criteriaQuery).getResultList();
			if(list==null && list.size()>0)
				list.get(0);
		}catch (Exception e) {
			System.out.println("Error Occurred :" +e.getMessage());// uncomment asneeded --;
		}
		finally {
			if (em != null) { em.close(); }
		}
		return null;

	}
	
	@Override
	public void saveArchieve() throws Exception {
		try {
			StringBuffer buffer = new StringBuffer();
			buffer.append("INSERT INTO MSR_STOCK_ARC SELECT EMP_CODE,FS_ID,MONTH,HQ_POSITION, ");
			buffer.append("TERRITORY_CODE,COMPANY,PROD_ID,PROD_CODE,PROD_TYPE,OPSTOCK,ACKQTY, ");
			buffer.append("DISBQTY,CLSTOCK,USER_ID,UPDATE_DATE,UPD_IP_ADD,STATUS,BATCH ");
			buffer.append("FROM MSR_STOCK");
			Query query = entityManager.createNativeQuery(buffer.toString());
			query.executeUpdate();
		} catch (Exception e) {
			throw e;
		}
	}
	
}
