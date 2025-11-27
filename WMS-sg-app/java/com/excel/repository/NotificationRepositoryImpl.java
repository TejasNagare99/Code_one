package com.excel.repository;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.StoredProcedureQuery;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.excel.model.Resign_Notification;
import com.excel.utility.MedicoConstants;

@Repository
public class NotificationRepositoryImpl implements NotificationRepository,MedicoConstants{
	@Autowired private EntityManagerFactory emf;
	@PersistenceContext private EntityManager entityManager;
	@Override
	public List<Resign_Notification> getResignFieldstaffList(String username, String companyCode) throws Exception {
		
		List<Resign_Notification> list = null;
		List<Resign_Notification> resignedList= null;
		EntityManager em = null;
		 
		try {
			resignedList = new ArrayList<>();
			   em = emf.createEntityManager();
			   StoredProcedureQuery procedurecall  = em.createNamedStoredProcedureQuery("callDG_DISP_TO_RESIGN_NOTIFI");
			   procedurecall.setParameter("pNo_of_days",Integer.parseInt("50") );
			  
			   list = procedurecall.getResultList();
			   
//			   for(Resign_Notification l :list) {             //for deleted status list
//				  if(l.getStatus().equals("DELETED")) {
//					  resignedList.add(l);
//				  }
//			   }
			   
				System.out.println("List :: "+list.size()); 
			
		}catch (Exception e) {
		throw e;
		}
		
		return list;
	}
	@Override
	public List<Object[]> getGrnNotifications(String emp_id) {
		// TODO Auto-generated method stub
		EntityManager em = null;
		List<Object[]> grnList = null;
		String sql = "Select 'GRN' DocType , gh.grn_id,CONVERT( VARCHAR , GH.GRN_DT , 105 ) Date , GH.GRN_NO DocNumber ,"
				+" S.SUP_NM Name, 'DISCARDED' Status ,"
				+" gh.GRN_ins_usr_id,RTRIM(HR.emp_fnm)+RTRIM(HR.emp_Mnm)+RTRIM(HR.emp_Lnm) Creator,"
				+" CONVERT( VARCHAR , GH.GRN_INS_DT , 105 ) CREATED_ON ,"
				+" GH.GRN_MOD_usr_id,RTRIM(AP.emp_fnm)+RTRIM(AP.emp_Mnm)+RTRIM(AP.emp_Lnm) Last_Approved_By ,"
				+" CONVERT( VARCHAR , GH.GRN_MOD_DT , 105 ) MODIFIED_ON ,"
				+" GH.REMARKS"
				+" FROM GRN_HEADER GH LEFT OUTER JOIN SUPPLIER S ON S.SUP_ID = GH.GRN_SUPPLIER_ID"
				+" INNER JOIN hr_m_employee HR ON HR.emp_id = GH.GRN_ins_usr_id"
				+"  INNER JOIN hr_m_employee AP ON AP.emp_id = GH.GRN_MOD_usr_id"
				+" WHERE GH.GRN_APPR_STATUS = 'D'"
				+" and GH.GRN_ins_usr_id = '"+emp_id+"' order by GH.grn_dt";
		System.out.println("Query that is fired as a string "+sql);
		try {
			grnList = new ArrayList<Object[]>();
			em = emf.createEntityManager();
			Query q = em.createNativeQuery(sql);
			grnList = q.getResultList();
			System.out.println("GRN NOtifications list is of size::::::"+grnList.size());
		}
		catch(Exception e) {
			throw e;
		}
		return grnList;
	}

}
