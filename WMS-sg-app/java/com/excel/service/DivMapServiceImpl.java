package com.excel.service;

import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.excel.bean.DivMapBean;
import com.excel.model.DivMap;
import com.excel.repository.DivMapRepository;
import com.excel.repository.TransactionalRepository;
import com.excel.utility.MedicoConstants;

@Service
public class DivMapServiceImpl implements DivMapService, MedicoConstants {

	@Autowired
	private TransactionalRepository transactionalRepository;
	
	@Autowired
	private DivMapRepository divMapRepository;
	
	@Override
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public void saveDivisionMapping(DivMapBean bean, HttpServletRequest request) throws Exception {
		DivMap divMap = null;
		
		System.out.println("prodDivIdService : "+bean.getActiveDiv().size());
		
		for (Long l : bean.getActiveDiv()) {
			divMap = new DivMap();
			System.out.println("L : "+l);
			
			String coreInd = divMapRepository.coreInd(bean.getFieldDivision(), l);
			
			if (coreInd != null && coreInd.equals(N)) {
				
				this.divMapRepository.updateCoreIndToA(bean, l, request, A);
				
				
			} else {
				divMap.setBase_div_id(bean.getFieldDivision());
				divMap.setProd_div_id(l);
				divMap.setCore_ind(A);
				divMap.setIns_dt(new Date());
				divMap.setIns_ip_add(request.getRemoteAddr());
				
				this.transactionalRepository.persist(divMap);
			}
		
		}
		
		if (bean.getInActiveDiv().size() > 0) {
			
			this.divMapRepository.updateCoreIndToN(bean, request, N);
		}
		
		
	}

	@Override
	public List<Long> getDivMapByFsDivision(Long fsDivId) throws Exception {
		
		return divMapRepository.getDivMapByFsDivision(fsDivId);
	}

}
