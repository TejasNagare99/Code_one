package com.excel.service;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.excel.bean.RuleBasedAllocationBean;
import com.excel.model.Alloc_Detail;
import com.excel.model.Alloc_Header;
import com.excel.model.Alloc_Remark;
import com.excel.model.Alloc_Temp;
import com.excel.model.Alloc_Temp_Header;
import com.excel.model.Period;
import com.excel.repository.AllocHeaderRepository;
import com.excel.repository.AllocTempArcRepository;
import com.excel.repository.AllocTempRepository;
import com.excel.repository.MsrStockRepository;
import com.excel.repository.RuleBasedAllocationApprovalRepository;
import com.excel.repository.TransactionalRepository;

@Service
public class RuleBasedAllocationApprovalServiceImpl implements RuleBasedAllocationApprovalService {

	@Autowired
	RuleBasedAllocationApprovalRepository ruleBasedAllocationApprovalRepository;
	@Autowired
	AllocTempService allocTempService;
	@Autowired
	AllocTempRepository allocTempRepository;
	@Autowired
	AllocTempArcRepository allocTempArcRepository;
	@Autowired
	AllocHeaderRepository allocHeaderRepository;
	@Autowired
	MsrStockRepository msrStockRepository;
	@Autowired TransactionalRepository transactionalRepository;
	
	@Override
	public List<RuleBasedAllocationBean> approvalDetails(long division, long period, String up_status, 
			String ucycle,Long allocTempId)
			throws Exception {
		// TODO Auto-generated method stub
		return ruleBasedAllocationApprovalRepository.approvalDetails(division, period, up_status, ucycle,allocTempId);
	}

	@Override
	public List<RuleBasedAllocationBean> approvalListForSelfAppr(String empId, String user_role, String locId,String compcd) {
		// TODO Auto-generated method stub
		return ruleBasedAllocationApprovalRepository.approvalListAllocConForSelfAppr(empId, user_role, locId,compcd);
	}

	@Override
	public Map<String, Object> getProductWiseStock(long division, long period, String up_status, String ucycle,Long alloctempHdId)
			throws Exception {
		// TODO Auto-generated method stub
		return ruleBasedAllocationApprovalRepository.getProductWiseStock(division, period, up_status, ucycle,alloctempHdId);
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public Map<String, Object> selfApprovalRuleBasedAllocation(Long alloc_temp_hd_id, String status,String remark) throws Exception {
		try {
			this.allocTempService.updateApprovalStatusWithRemark(alloc_temp_hd_id, status,remark);
			//this.allocTempService.saveAllocationRemark(remark, alloc_temp_hd_id);
		} catch (Exception e) {
			;
			throw e;
		}
		return null;
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public List<Alloc_Temp> saveAndMoveToArchieve(Long division, Period period, String isapproved, String upload_status,
			String ucycle, Long alloc_temp_hd_id, String app_remark, String stk_cfa_ind,String empId,String ipAdd) throws Exception {
		List<Alloc_Temp> list = null;
		try {

			if (isapproved.equals("A")) {
				list = this.allocTempRepository.getDataByDiv(division, period.getPeriod_code(), upload_status, ucycle,
						alloc_temp_hd_id);
				Set<Long> set = new TreeSet<Long>();
				for (Alloc_Temp temp : list) {
					set.add(temp.getFstaff_id());
				}
				this.allocHeaderRepository.setDetailsForRulesBasedAllocation(set, list, app_remark, stk_cfa_ind);
				this.allocTempService.updateApprovalStatus(alloc_temp_hd_id, isapproved);
				this.msrStockRepository.saveArchieve();
				this.allocTempArcRepository.saveArchieve(isapproved, division, period.getPeriod_code(), upload_status,
						ucycle, alloc_temp_hd_id);
				//New archive
				this.allocTempArcRepository.saveArchieveArchive(isapproved, division, period.getPeriod_code(), upload_status,
						ucycle, alloc_temp_hd_id,empId,ipAdd);
				
				this.allocTempRepository.deleteRecords(division, period.getPeriod_code(), upload_status, ucycle,
						alloc_temp_hd_id);
			} else {
				this.allocTempService.updateApprovalStatus(alloc_temp_hd_id, isapproved);
				this.allocTempArcRepository.saveArchieve(isapproved, division, period.getPeriod_code(), upload_status,
						ucycle, alloc_temp_hd_id);
				//New archive
				this.allocTempArcRepository.saveArchieveArchive(isapproved, division, period.getPeriod_code(), upload_status,
						ucycle, alloc_temp_hd_id,empId,ipAdd);
				this.allocTempRepository.deleteRecords(division, period.getPeriod_code(), upload_status, ucycle,
						alloc_temp_hd_id);
			}

		} catch (Exception e) {
			;
			throw e;
		}
		return list;
	}

	public void ApproveMonthlyAllocation(Long allocTempId, String userId, String userIp) throws Exception {

		this.ruleBasedAllocationApprovalRepository.ApproveMonthlyAllocation(allocTempId, userId, userIp);
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public List<Alloc_Temp> saveAndMoveToArchieveByProcedure(Long division, Period period, String isapproved,
			String upload_status, String ucycle, Long alloc_temp_hd_id, String app_remark, String stk_cfa_ind,
			String empId,String ipAdd)
			throws Exception {
		List<Alloc_Temp> list = null;
		try {

			if (isapproved.equals("A")) {
				this.ruleBasedAllocationApprovalRepository.ApproveMonthlyAllocation(alloc_temp_hd_id, empId,ipAdd);
				//this.allocTempService.updateApprovalStatus(alloc_temp_hd_id, isapproved);
				  Alloc_Temp_Header header=this.allocTempRepository.getObjectAllocTempHeader(alloc_temp_hd_id);
				  header.setIns_dt(new Date());
				  this.transactionalRepository.update(header);
				   
				this.msrStockRepository.saveArchieve();
				this.allocTempArcRepository.saveArchieve(isapproved, division, period.getPeriod_code(), upload_status,
						ucycle, alloc_temp_hd_id);
				//New archive
				this.allocTempArcRepository.saveArchieveArchive(isapproved, division, period.getPeriod_code(), upload_status,
						ucycle, alloc_temp_hd_id,empId,ipAdd);
				
				this.allocTempRepository.deleteRecords(division, period.getPeriod_code(), upload_status, ucycle,
						alloc_temp_hd_id);
			} else {
				this.allocTempService.updateApprovalStatus(alloc_temp_hd_id, isapproved);
				this.allocTempArcRepository.saveArchieve(isapproved, division, period.getPeriod_code(), upload_status,
						ucycle, alloc_temp_hd_id);
				//New archive
				this.allocTempArcRepository.saveArchieveArchive(isapproved, division, period.getPeriod_code(), upload_status,
						ucycle, alloc_temp_hd_id,empId,ipAdd);
				
				this.allocTempRepository.deleteRecords(division, period.getPeriod_code(), upload_status, ucycle,
						alloc_temp_hd_id);
			}

		} catch (Exception e) {
			;
			throw e;
		}
		return list;
	}

	@Override
	public String getAllocRemark(Long allocTempId) throws Exception {
		// TODO Auto-generated method stub
		return  allocTempRepository.getObjectAllocTempHeader(allocTempId).getApproval_remark();//allocTempRepository.getAlloc_Remark(temp.getAlloc_header_id());
	}

}
