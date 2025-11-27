package com.excel.service;



import java.text.SimpleDateFormat;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.excel.bean.EventBean;
import com.excel.model.Event;
import com.excel.repository.EventRepository;
import com.excel.repository.TransactionalRepository;
import com.excel.utility.MedicoResources;

@Service
public class EventServiceImpl  implements EventService{

	@Autowired private TransactionalRepository transactionalRepository;
	@Autowired private EventRepository eventRepository;
	
	
	@Override
	public void save(EventBean bean) throws Exception {
		
		Event event = new Event();
		event.setCompany_code(bean.getCompanyCode());
		
		event.setEvent_date(MedicoResources.convert_DD_MM_YYYY_toDate(bean.getEventDate().toString()));
	
		event.setEvent_text(bean.getText());
		event.setTitle(bean.getTitle());
		event.setUsername(bean.getUsername());
		transactionalRepository.persist(event);
		System.out.println("Saved");
		
	}

	@Override
	public void delete(Long eventId) throws Exception {
		eventRepository.deleteById(eventId);
	}

	@Override
	public List<Event> getReminder(String emp_id) {
		
		return eventRepository.getEvent(emp_id);
	}

}
