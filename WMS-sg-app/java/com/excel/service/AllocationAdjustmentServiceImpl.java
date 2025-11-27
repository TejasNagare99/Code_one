package com.excel.service;

import java.util.List;
import java.util.Map;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.ParameterMode;
import javax.persistence.PersistenceContext;
import javax.persistence.StoredProcedureParameter;
import javax.persistence.StoredProcedureQuery;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.excel.bean.AllocationAdjustmentBean;
import com.excel.bean.Parameter;
import com.excel.model.DivMaster;
import com.excel.model.GenerateDispatchDataCfaStock;
import com.excel.model.SmpItem;
import com.excel.repository.AllocationAdjustmentRepostory;

@Service
public class AllocationAdjustmentServiceImpl implements AllocationAdjustmentService{
	@Autowired private EntityManagerFactory emf;
	@PersistenceContext private EntityManager entityManager;
	@Autowired private AllocationAdjustmentRepostory allocationAdjustmentRepostory;
	
	@Override
	public List<DivMaster> getDivForAllocAdj(String allocid,String compCode,String empId) {
		
		return this.allocationAdjustmentRepostory.getDivForAllocAdj(allocid,compCode,empId);
	}
	
	@Override
	public List<SmpItem> getProdList(String allocid, String divId){
		
		return this.allocationAdjustmentRepostory.getProdList(allocid, divId);
	}
	
	
	@Override
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public 	Map<String,Object> saveAdjustment(AllocationAdjustmentBean bean)throws Exception {
		
		return this.allocationAdjustmentRepostory.saveAdjustment(bean);
	}

	@Override
	public List<Parameter> getFSForAllocAdj(String allocid, String divId, String prodId, String alMode,
			String compcode) {
		// TODO Auto-generated method stub
		return this.allocationAdjustmentRepostory.getFSForAllocAdj(allocid, divId, prodId, alMode, compcode);
	}

	@Override
	public List<Parameter> getAllocList(String compcode) {
		// TODO Auto-generated method stub
		return this.allocationAdjustmentRepostory.getAllocList(compcode);
	}
}
