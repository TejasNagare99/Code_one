package com.excel.service;

import java.util.List;

public interface EmailTranWiseService {

	public void saveEmailTranWise(List<String> emails,String trantype,Long tranId,String finyr)throws Exception;
	public void DeleteEmailTranwiseByTranId(String trantype, Long tran_id, String finyr) throws Exception;
	public List<String> getEmailListByTranType(String trantype,String finyrId,Long tranId)throws Exception;
}
