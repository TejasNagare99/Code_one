package com.excel.service;

import java.util.List;
import com.excel.bean.NotificationRequestBean;
import com.excel.model.Resign_Notification;

public interface NotificationService {

	List<Resign_Notification> getNotifications(NotificationRequestBean bean) throws Exception;
	
	List<Resign_Notification> getGrnDiscard(NotificationRequestBean bean) throws Exception;
	
	List<Object[]> getGrnNotifications(String emp_id);
}
