package com.excel.repository;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.excel.model.Event;

@Repository
public interface EventRepository {

	void deleteById(Long eventId) throws Exception;
	List<Event> getEvent(String emp_id);
	

}