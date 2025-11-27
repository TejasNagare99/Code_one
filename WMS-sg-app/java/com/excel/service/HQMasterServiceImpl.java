package com.excel.service;

import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.transaction.HeuristicMixedException;
import javax.transaction.HeuristicRollbackException;
import javax.transaction.RollbackException;
import javax.transaction.SystemException;
import javax.transaction.Transaction;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.excel.bean.HQBean;
import com.excel.model.HQ_Master;
import com.excel.model.V_HQ_Master;
import com.excel.repository.HQMasterRepository;
import com.excel.repository.TransactionalRepository;


@Service
public class HQMasterServiceImpl implements HQMasterService{
	@Autowired HQMasterRepository hqMasterRepository;
	@Autowired TransactionalRepository transactionalRepository;
	
	
	@Override
	public Boolean checkUniqueHQName(String hqName,String divId) {
		return this.hqMasterRepository.checkUniqueHQName(hqName, divId);
	}
	@Override
	public Boolean checkUniqueHQNameForModify(String hqName,String divId,String hqId) {
		return this.hqMasterRepository.checkUniqueHQNameForModify(hqName, divId, hqId);
	}
	
	@Override
	public Object saveHQDetail(HQBean hqBean,String empId,HttpServletRequest request) throws Exception {
		
		String ip_addr = request.getRemoteAddr();
		Transaction txn=null;
		HQ_Master hq_Master =new HQ_Master();
		hq_Master.setHq_code("");
		hq_Master.setHq_div_id(Long.valueOf(Integer.parseInt(hqBean.getDivision())));
		hq_Master.setHq_map_code(hqBean.getMapCode());
		hq_Master.setHq_name(hqBean.getHqName());
		hq_Master.setHq_state_id(Long.valueOf(hqBean.getState()));
		hq_Master.setHq_no_of_reps(Long.valueOf(hqBean.getRepresentative()));
		hq_Master.setHq_pmp_perf(hqBean.getPerformance() == null ? BigDecimal.ZERO : hqBean.getPerformance());
		hq_Master.setHq_pool_ind((hqBean.getPoolIndicator() !=null && Boolean.parseBoolean(hqBean.getPoolIndicator()) )?"Y":"N" );
		hq_Master.setHq_status(hqBean.getStatus());
		hq_Master.setHq_zone_id(0L);
		hq_Master.setHq_city_id(0L);
		hq_Master.setHq_ins_dt(new Date());
		hq_Master.setHq_ins_ip_add(ip_addr);
		hq_Master.setHq_ins_usr_id(empId);
		//added
		hq_Master.setHq_team_code(hqBean.getTeamcode()==null?"0":hqBean.getTeamcode());
		
		this.transactionalRepository.persist(hq_Master);

		System.out.println("geenrated hq id is :"+hq_Master.getHq_id());
		String hq_code=String.format("%05d",hq_Master.getHq_id());
		System.out.println("new hq code is: "+hq_code);
		hq_Master.setHq_code(hq_code);
		this.transactionalRepository.update(hq_Master);
		return hq_code;
	}
	@Override
	public List<HQ_Master> getHqListForFieldStaffEntry(String divId) {
		return this.hqMasterRepository.getHqListForFieldStaffEntry(divId);
	}
	@Override
	public List<V_HQ_Master> getAllActiveHqMasterDetail() {
		return this.hqMasterRepository.getAllActiveHqMasterDetail();
	}
	@Override
	public List<V_HQ_Master> getHqTextParameterDetail(String name,String parameter,String text) {
		return this.hqMasterRepository.getHqTextParameterDetail(name, parameter, text);
	}
	@Override
	public List<HQ_Master> getHqDetailById(String hqId){
		return this.hqMasterRepository.getHqDetailById(hqId);
	}
	@Override
	public Object updateHQMaster(String hq_id,HQBean hqBean,String empId,HttpServletRequest request) throws Exception {
		
		String ip_addr = request.getRemoteAddr();
		HQ_Master hq_Master =this.hqMasterRepository.getByObjectId(Long.valueOf(hq_id));
		hq_Master.setHq_code("");
		hq_Master.setHq_div_id(Long.valueOf(Integer.parseInt(hqBean.getDivision())));
		hq_Master.setHq_map_code(hqBean.getMapCode());
		hq_Master.setHq_name(hqBean.getHqName());
		hq_Master.setHq_state_id(Long.valueOf(hqBean.getState()));
		hq_Master.setHq_no_of_reps(Long.valueOf(hqBean.getRepresentative()));
		hq_Master.setHq_pmp_perf(hqBean.getPerformance() == null ? BigDecimal.ZERO : hqBean.getPerformance());
		hq_Master.setHq_pool_ind((hqBean.getPoolIndicator() !=null && Boolean.parseBoolean(hqBean.getPoolIndicator()) )?"Y":"N" );
		hq_Master.setHq_status(hqBean.getStatus());
		hq_Master.setHq_zone_id(0L);
		hq_Master.setHq_city_id(0L);
//		hq_Master.setHq_ins_dt(new Date());
//		hq_Master.setHq_ins_ip_add(ip_addr);
//		hq_Master.setHq_ins_usr_id(empId);
		hq_Master.setHq_mod_dt(new Date());
		hq_Master.setHq_mod_ip_add(ip_addr);
		hq_Master.setHq_mod_usr_id(empId);

		hq_Master.setHq_team_code(hqBean.getTeamcode());
		
		this.transactionalRepository.update(hq_Master);

		System.out.println("geenrated hq id is :"+hq_Master.getHq_id());
		String hq_code=String.format("%05d",hq_Master.getHq_id());
		System.out.println("new hq code is: "+hq_code);
		hq_Master.setHq_code(hq_code);
		this.transactionalRepository.update(hq_Master);
		return hq_code;
	}
}
