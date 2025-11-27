package com.excel.repository;

import org.springframework.stereotype.Repository;

@Repository
public interface LoginMasterRepository {

	public String getFormatedUserDivision(String UserId) throws Exception;
}
