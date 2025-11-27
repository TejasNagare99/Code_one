package com.excel.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import com.excel.model.Location;
import com.excel.repository.LocationRepository;

@Service
public class LocationServiceImpl implements LocationService{

	@Autowired
	LocationRepository locationrepository;
	
	@Override
	public Location getLocationNameByEmployeeId(String empId) throws Exception {
		return this.locationrepository.getLocationNameByEmployeeId(empId);
	}

	@Override
	@Cacheable(value="LocationCache",key="#locId")
	public Location getObjectById(Long locId) throws Exception {
		return locationrepository.getObjectById(locId);
	}
	
	@Override
	@Cacheable(value="AllSubCompanies")
	public List<Location> getAllSubCompanies() throws Exception {
	return this.locationrepository.getAllLocations();
	}
	
	@Override
	@Cacheable(value="AllLocCache")
	public List<Location> getAllLocations() throws Exception{
		return this.locationrepository.getAllLocations();
		
	}
	
	@Override
	public List<Location> getAllLocationsBySubCompany(Long subCompanyId) throws Exception{
		return this.locationrepository.getAllLocationsBySubCompany(subCompanyId);
		
	}

	@Override
	public List<Location> getlocationforpfzandpip() throws Exception {
		return this.locationrepository.getlocationforpfzandpip();
	}

	@Override
	public boolean checkAutoDispatchIsLocAvail() throws Exception {
		return this.locationrepository.checkAutoDispatchIsLocAvail();
	}

	@Override
	public List<Location> getLocationNarrationBylocId(String locId) throws Exception {
		return this.locationrepository.getLocationNarrationBylocId(locId);
	}

	@Override
	public void lockUnlockLocations(String status) throws Exception {
		 this.locationrepository.lockUnlockLocations(status);
		
	}

	@Override
	public boolean checkAutoDispatchIsSpecificLocAvail(Long loc_id) throws Exception {
		return this.locationrepository.checkAutoDispatchIsSpecificLocAvail(loc_id);
	}

	@Override
	public void lockUnlockSpecificLocations(String status, Long loc_id) throws Exception {
		this.locationrepository.lockUnlockSpecificLocations(status, loc_id);
	}

	@Override
	public Long getLocIdByEmpId(String empId) throws Exception {
		return this.locationrepository.getLocIdByEmpId(empId);
	}
}
