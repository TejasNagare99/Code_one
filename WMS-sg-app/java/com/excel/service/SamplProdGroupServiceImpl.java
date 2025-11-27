package com.excel.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.excel.model.SamplProdGroup;
import com.excel.repository.SampleProdGroupRepository;
import com.excel.utility.MedicoConstants;

@Service
public class SamplProdGroupServiceImpl implements SamplProdGroupService, MedicoConstants{
	@Autowired SampleProdGroupRepository sampleprodgrouprepository;

	@Override
	public List<SamplProdGroup> getAllSamplProducts() throws Exception {
		return this.sampleprodgrouprepository.getAllSamplProducts();
	}
}
