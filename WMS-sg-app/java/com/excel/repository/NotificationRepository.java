package com.excel.repository;

import java.util.List;

import com.excel.model.Resign_Notification;

public interface NotificationRepository {

	List<Resign_Notification> getResignFieldstaffList(String username, String companyCode) throws Exception;

	List<Object[]> getGrnNotifications(String emp_id);
	
}
