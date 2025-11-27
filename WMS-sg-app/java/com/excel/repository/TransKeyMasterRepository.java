package com.excel.repository;

import com.excel.model.Trans_Key_Master;
import com.excel.model.Trans_key_Master_batch_wthrel;

public interface TransKeyMasterRepository {
	
	public Trans_Key_Master getTransObj(Long loc_id, String fin_year,String comp_cd, Long tran_type, String type) throws Exception;
	public void update(Trans_Key_Master obj) throws Exception;
	public Trans_key_Master_batch_wthrel getTranLastNo(Long loc_id, Long stock_point_id,String tran_type_id, String fin_year,String heading) throws Exception;
	Trans_Key_Master getTransObjByPrefix(Long loc_id, String fin_year, String comp_cd, String prefix) throws Exception; 
}
