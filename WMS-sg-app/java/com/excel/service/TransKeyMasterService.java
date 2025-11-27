package com.excel.service;

import com.excel.model.Trans_Key_Master;

public interface TransKeyMasterService {
	
	public Trans_Key_Master getTransObj(Long loc_id, String fin_year,String comp_cd, Long tran_type, String type) throws Exception;
	public void update(Trans_Key_Master obj) throws Exception;

}
