package com.excel.repository;


import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.Tuple;

import org.apache.poi.ss.formula.functions.Even;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.excel.model.Event;
import com.excel.model.Sub_Company;
import com.excel.utility.CodifyErrors;
import com.excel.utility.MedicoResources;

@Repository
public class EventRepositoryImpl  implements EventRepository {

	@Autowired private EntityManagerFactory emf;
	@PersistenceContext private EntityManager entityManager;
	
	@Override
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public void deleteById(Long eventId) throws Exception {
		entityManager.createQuery(" DELETE FROM Event WHERE event_id = :event_id ")
		.setParameter("event_id", eventId)
		.executeUpdate();
	}

	@Override
	public List<Event> getEvent(String emp_id) {
		
        System.out.println(java.time.LocalDate.now()+"  usernam :::::: "+emp_id);   
        LocalDate CurrentDate = java.time.LocalDate.now();
        

//		  Query query = entityManager.createNativeQuery(  "select * from EVENT  where EVENT_DATE=? and  USERNAME=?");  
//		   query.setParameter(1,CurrentDate.toString());  
//		   query.setParameter(2, emp_id);  
//		   Event event = (Event) query.getSingleResult(); 
//		   System.err.println(event);
		   
		   
		   
		   EntityManager em = null;
			List<Event> list = new ArrayList<Event>();
			try {
				em = emf.createEntityManager();
				String q="select * from EVENT  where EVENT_DATE=:CurrentDate and  USERNAME=:empId";
				
				Query query = em.createNativeQuery(q,Tuple.class);
				query.setParameter("CurrentDate", CurrentDate);
				query.setParameter("empId", emp_id);
				
				System.err.println(CurrentDate+":::::"+emp_id);
				
				List<Tuple> tuples = query.getResultList();
			
				if (tuples != null && tuples.size()>0) {
					for (Tuple t : tuples) {
					Event event=new Event();
					String  CurDate=t.get(1).toString().substring(0,10);
					
				  	event.setEvent_id(Long.valueOf(t.get(0).toString()));
				  	System.err.println("hhds"+CurDate);
				  	  	
				  	
				  	String date_s = t.get(1).toString(); 
				  	SimpleDateFormat dt = new SimpleDateFormat("yyyyy-MM-dd hh:mm:ss"); 
				  	Date date = dt.parse(date_s); 
				  	SimpleDateFormat dt1 = new SimpleDateFormat("dd-MM-yyyy");
				  	System.out.println(dt1.format(date));
				  	
				  	  
				  	
					event.setDate(dt1.format(date));
				  	
				  	event.setTitle(t.get(2).toString());
				  	event.setEvent_text(t.get(3).toString());
				  	event.setCompany_code(t.get(4).toString());
				  	event.setUsername(t.get(5).toString());
				  	
						list.add(event);
					}
				}
			}
			catch (Exception e) {
				e.printStackTrace();
				System.out.println("Error Occurred :" +e.getMessage());// uncomment asneeded --;
			}
			finally {
				if(em != null) { em.close(); }
			}
			System.err.println(list.size());
			
			return list;
		
	}

}
