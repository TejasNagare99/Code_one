package com.excel.service;

import java.util.List;

import com.excel.model.Location;

public interface LocationService {

	Location getLocationNameByEmployeeId(String empId) throws Exception;
	public Location getObjectById(Long locId) throws Exception;
	List<Location> getAllSubCompanies() throws Exception;
	public List<Location> getAllLocations() throws Exception;
	public List<Location> getlocationforpfzandpip()throws Exception;
	
	public boolean checkAutoDispatchIsLocAvail() throws Exception;
	List<Location> getLocationNarrationBylocId(String locId) throws Exception;
	public void lockUnlockLocations(String status)throws Exception;
	
	//for cfa to stockist
	public boolean checkAutoDispatchIsSpecificLocAvail(Long loc_id) throws Exception;
	public void lockUnlockSpecificLocations(String status,Long loc_id)throws Exception;
	Long getLocIdByEmpId(String empId) throws Exception;
	List<Location> getAllLocationsBySubCompany(Long subCompanyId) throws Exception;
}
