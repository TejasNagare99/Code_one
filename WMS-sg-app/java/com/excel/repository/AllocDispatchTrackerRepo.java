package com.excel.repository;

import java.util.List;

import com.excel.model.AllocDispatchTracker;

public interface AllocDispatchTrackerRepo {
	public List<AllocDispatchTracker> getAllocDispatchTrackingDataFromProcedure
	(String loc_id,String startDate,String endDate,String allocType, String divId);
}
