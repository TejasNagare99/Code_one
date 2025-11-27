package com.excel.repository;

public interface TransactionalRepository {
	
	void persist(Object object) throws Exception;
	void delete(Object object) throws Exception;
	void update(Object object) throws Exception;
	void flush() throws Exception;

}
