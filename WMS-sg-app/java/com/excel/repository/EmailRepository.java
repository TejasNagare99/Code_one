package com.excel.repository;

import java.util.List;

import com.excel.model.Email;
import com.excel.model.EmailFrom;

public interface EmailRepository {

	EmailFrom getEmailObject(String trantype) throws Exception;

	void getObjectAspHeader(Long headerId) throws Exception;

	List<Email> getEmailList(String tran_type, String divCode) throws Exception;
}
