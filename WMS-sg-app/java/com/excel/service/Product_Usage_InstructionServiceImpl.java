package com.excel.service;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.excel.bean.UsageInstructionBean;
import com.excel.model.DivMaster;
import com.excel.model.SmpItem;
import com.excel.model.Usage_Instruction;
import com.excel.repository.TransactionalRepository;
import com.excel.repository.Product_Usage_InstructionRepository;

@Service
public class Product_Usage_InstructionServiceImpl implements Product_Usage_InstructionService{

	@Autowired Product_Usage_InstructionRepository product_usage_instructionrepository;
	@Autowired TransactionalRepository transactionalRepository;
	
	@Override
	public List<DivMaster> getActivedivisionlist() throws Exception{
		// TODO Auto-generated method stub
		return product_usage_instructionrepository.getActivedivisionlist();
	}

	@Override
	public List<SmpItem> getproductsbydiv(String divId) throws Exception {
		// TODO Auto-generated method stub
		return product_usage_instructionrepository.getproductsbydiv(divId);
	}

	@Override
	public Usage_Instruction checkusageinstruction(String prod_id, String monthofuse, String finyr, String companycode)
			throws Exception {
		// TODO Auto-generated method stub
		return product_usage_instructionrepository.checkusageinstruction(prod_id, monthofuse, finyr, companycode);
	}

	@Override
//	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public String SaveUsageinstruction(UsageInstructionBean bean) throws Exception {
		
		String Status =null;
			
		try {
		Usage_Instruction uis = new Usage_Instruction();
		
		if(bean.isCheckusage() == true) {
			System.out.println(bean.isCheckusage());			//modify
			
			uis = product_usage_instructionrepository.getObjectByusageId(Long.valueOf(bean.getUsage_id()));
			
			uis.setUsage_comp_cd(bean.getCompanycode());
			uis.setUsage_fin_year(bean.getFinyear());
			uis.setUsage_month_use(bean.getMonthOfUsePeriodCode());
			uis.setUsage_prod_id(Long.valueOf(bean.getProd_id()));
			uis.setUsage_prod_code(bean.getProd_code()); 				//getprodcode
			uis.setUsage_instruction(bean.getUsageofinstruction());
			uis.setUsage_ins_dt(uis.getUsage_ins_dt());							//todayDate
			uis.setUsage_mod_dt(new Date());
			uis.setUsage_ins_ip_add(uis.getUsage_ins_ip_add());
			uis.setUsage_mod_ip_add(bean.getIp_addr());
			
			this.transactionalRepository.update(uis);
			
			Status = "Modify";
			
		}
		if(bean.isCheckusage() == false) {
			System.out.print(bean.isCheckusage());			//save
			
			uis.setUsage_comp_cd(bean.getCompanycode());
			uis.setUsage_fin_year(bean.getFinyear());
			uis.setUsage_month_use(bean.getMonthOfUsePeriodCode());
			uis.setUsage_prod_id(Long.valueOf(bean.getProd_id()));
			uis.setUsage_prod_code(bean.getProd_code()); 				//getprodcode
			uis.setUsage_instruction(bean.getUsageofinstruction());
			uis.setUsage_ins_dt(new Date());							//todayDate
			uis.setUsage_mod_dt(null);
			uis.setUsage_ins_ip_add(bean.getIp_addr());
			uis.setUsage_mod_ip_add(null);
			
			
			this.transactionalRepository.persist(uis);			//save
			Status = "Saved";
		}
		}
		catch (Exception e) {
			throw e;
		}
		return Status;
	}

}
