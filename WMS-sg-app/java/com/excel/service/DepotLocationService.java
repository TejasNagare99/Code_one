package com.excel.service;

import java.util.List;

import com.excel.model.Depot_Locations;

public interface DepotLocationService {

	List<Depot_Locations> getDepotLocations(Long locId,String depotLoactions) throws Exception;
	
	List<Depot_Locations> getCFALocationsOne();

	List<Depot_Locations> getCFALocationsTwo();

	List<Depot_Locations> getCFALocationsThree();
}
