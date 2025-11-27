package com.excel.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.excel.model.Depot_Locations;
import com.excel.repository.DepotLocationRepository;

@Service
public class DepotLocationServiceImpl implements DepotLocationService {
	
	@Autowired DepotLocationRepository depotLocationRepository; 

	@Override
	public List<Depot_Locations> getDepotLocations(Long locId,String depotLoactions) throws Exception {
		return depotLocationRepository.getDepotLocations(locId,depotLoactions);
	}
	@Override
	public List<Depot_Locations> getCFALocationsOne(){
		return this.depotLocationRepository.getCFALocationsOne();
	}
	@Override
	public List<Depot_Locations> getCFALocationsTwo(){
		return this.depotLocationRepository.getCFALocationsTwo();
	}
	@Override
	public List<Depot_Locations> getCFALocationsThree(){
		return this.depotLocationRepository.getCFALocationsThree();
	}
}
