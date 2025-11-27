package com.excel.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.excel.model.Trans_Key_Master;
import com.excel.repository.TransKeyMasterRepository;

@Service
public class TransKeyMasterServiceImpl implements TransKeyMasterService {
	
	@Autowired TransKeyMasterRepository transKeyMasterRepository;

	@Override
	public Trans_Key_Master getTransObj(Long loc_id, String fin_year, String comp_cd, Long tran_type, String type)
			throws Exception {
		return transKeyMasterRepository.getTransObj(loc_id, fin_year, comp_cd, tran_type, type);
	}

	@Override
	public void update(Trans_Key_Master obj) throws Exception {
		transKeyMasterRepository.update(obj);
	}

}
