package com.excel.repository;

import java.util.List;

public interface EmailTranWiseRepository {
	public void DeleteEmailTranwiseByTranId(String trantype, Long tran_id, String finyr) throws Exception;
	public List<String> getEmailListByTranType(String trantype,String finyrId,Long tranId)throws Exception;
}
