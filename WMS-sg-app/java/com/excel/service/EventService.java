package com.excel.service;

import java.util.List;

import com.excel.bean.EventBean;
import com.excel.model.Event;

public interface EventService {

	void save(EventBean bean) throws Exception;
	void delete(Long eventId) throws Exception;
	List<Event> getReminder(String emp_id);
}
