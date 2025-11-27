package com.excel.repository;

import java.util.List;

import com.excel.model.Location;

public interface LocationRepository {

	Location getLocationNameByEmployeeId(String empId) throws Exception;

	Location getObjectById(Long locId) throws Exception;

	List<Location> getAllSubCompanies() throws Exception;

	List<Location> getAllLocations() throws Exception;

	public List<Location> getlocationforpfzandpip() throws Exception;

	public boolean checkAutoDispatchIsLocAvail() throws Exception;

	List<Location> getLocationNarrationBylocId(String locId) throws Exception;

	public void lockUnlockLocations(String status) throws Exception;

	public List<Location> getLocationsWithoutBatch(String batch_no, Long smp_prod_id, Long maker_loc_id)
			throws Exception;

	String getLocPrefixById(Long loc_id) throws Exception;

	// for cfa to stockist
	boolean checkAutoDispatchIsSpecificLocAvail(Long loc_id) throws Exception;

	void lockUnlockSpecificLocations(String status, Long loc_id) throws Exception;
	
	Long getLocIdByEmpId(String empId) throws Exception;

	Long getDepo_loc_id_by_loc_id(String string);

	List<Location> getAllLocationsBySubCompany(Long subCompanyId) throws Exception;
}
