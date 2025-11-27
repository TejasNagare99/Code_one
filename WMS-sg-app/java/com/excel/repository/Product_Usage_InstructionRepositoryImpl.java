package com.excel.repository;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.Tuple;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.excel.bean.UsageInstructionBean;
import com.excel.model.DivMaster;
import com.excel.model.SmpItem;
import com.excel.model.TerrMaster;
import com.excel.model.Usage_Instruction;
import com.excel.utility.CodifyErrors;

@Repository
public class Product_Usage_InstructionRepositoryImpl implements Product_Usage_InstructionRepository{

	@Autowired EntityManagerFactory emf;
	@PersistenceContext private EntityManager entityManager;
	
	@Override
	public List<DivMaster> getActivedivisionlist()throws Exception{
		// TODO Auto-generated method stub
		EntityManager em = null;
		List<DivMaster> divList = new ArrayList<DivMaster>();
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
					
					divList.add(dm);
				}
				
				
				System.out.println("divlist::"+divList.size());
			}
			
		}
		catch(Exception e) {
			throw e;
		}
		 finally {
				if (em != null) { em.close(); }
			}
		
		return divList;
	}

	@Override
	public List<SmpItem> getproductsbydiv(String divId) throws Exception {
		EntityManager em = null;
		List<SmpItem> itemList = new ArrayList<SmpItem>();
		try {
			em = emf.createEntityManager();
			StringBuffer sb = new StringBuffer();
			sb.append("select smp_prod_id,smp_prod_name,smp_prod_cd from SMPITEM where SMP_status = 'A' and SMP_STD_DIV_ID =:divid order by smp_prod_name");
			Query query = em.createNativeQuery(sb.toString(), Tuple.class);
			query.setParameter("divid", divId);
			List<Tuple> tuples = query.getResultList();
			if (tuples != null && !tuples.isEmpty()) {
				for(Tuple t : tuples) {
					SmpItem item = new SmpItem();
					
					item.setSmp_prod_id(Long.valueOf(t.get("smp_prod_id", Integer.class).toString()));
					item.setSmp_prod_name(t.get("smp_prod_name", String.class));
					item.setSmp_prod_cd(t.get("smp_prod_cd",String.class));
					
					itemList.add(item);
				}
			}
		}
		catch(Exception e) {
			throw e;
		}
		 finally {
				if (em != null) { em.close(); }
			}
		
		return itemList;
	}

	@Override
	public Usage_Instruction checkusageinstruction(String prod_id, String monthofuse, String finyr, String companycode)
			throws Exception {
		EntityManager em = null;
		Usage_Instruction ui = null;
		
		try {
			
			System.out.println("prod_id"+prod_id);
			System.out.println("monthofuse"+monthofuse);
			System.out.println("finyr"+finyr);
			System.out.println("companycode"+companycode);
			
			
			em = emf.createEntityManager();
			StringBuffer sb = new StringBuffer();
			sb.append("select usage_instruction,usage_id from usage_instruction where usage_prod_id =:prod_id and usage_month_use =:month and usage_fin_year =:fin_year and usage_comp_cd =:comp_cd");
			Query query = em.createNativeQuery(sb.toString(), Tuple.class);
			query.setParameter("prod_id", prod_id);
			query.setParameter("month", monthofuse);
			query.setParameter("fin_year", finyr);
			query.setParameter("comp_cd", companycode);
			List<Tuple> tuples = query.getResultList();
			if (tuples != null && !tuples.isEmpty()) {
				for(Tuple t : tuples) {
					ui = new Usage_Instruction();		
					ui.setUsage_instruction(t.get("usage_instruction", String.class));
					ui.setUsage_id(Long.valueOf(t.get("usage_id",Integer.class).toString()));
				}
			}
			
		}
		catch (Exception e) {
			// TODO: handle exception
			System.out.println("Error Occurred :" +e.getMessage());// uncomment asneeded --;
		}finally {
			if (em != null) { em.close(); }
		}

		
		return ui;
	}

	@Override
	public Usage_Instruction getObjectByusageId(Long usage_id) throws Exception {
		// TODO Auto-generated method stub
		return this.entityManager.find(Usage_Instruction.class, usage_id);
	}


}
