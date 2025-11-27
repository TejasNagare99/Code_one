package com.excel.service;

public interface DirectToDoctorService {
	public String saveDoctorResponse(Long dspId,String action)throws Exception;
	public String saveStockistResponse(Long dspId,String action)throws Exception;
}
