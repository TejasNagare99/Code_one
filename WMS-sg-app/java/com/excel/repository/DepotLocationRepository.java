package com.excel.repository;

import java.util.List;

import com.excel.model.Depot_Locations;
import com.excel.model.Location;

public interface DepotLocationRepository {

	List<Depot_Locations> getDepotLocations(Long locId,String depotLoactions) throws Exception;


	List<Object> getLocationsByCFA(String divId, String period_code, String fin_year, String company, String saveMode,
			String planType, String alloc_id, String alloc_cycle, String prodtype, List<String> brands);
	List<Depot_Locations> getCFALocationsOne();

	List<Depot_Locations> getCFALocationsTwo();

	List<Depot_Locations> getCFALocationsThree();
	
	Depot_Locations getObjectById(Long locId) throws Exception;

	 
}
