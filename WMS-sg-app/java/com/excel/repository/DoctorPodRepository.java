package com.excel.repository;

import com.excel.model.DoctorPodMail;
import com.excel.model.StockistPodMail;

public interface DoctorPodRepository {
	DoctorPodMail getDoctorPodByDspId(Long dspId)throws Exception;
	StockistPodMail getStockistPodByDspId(Long dspId)throws Exception;
}
