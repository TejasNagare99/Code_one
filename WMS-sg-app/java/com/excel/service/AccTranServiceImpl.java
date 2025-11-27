package com.excel.service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.EnumSet;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.excel.bean.CustomAccTranGeneration;
import com.excel.model.Acc_Tran_Type;
import com.excel.model.Acc_Tran_Type.TranNameEnum;
import com.excel.model.TaskMaster;
import com.excel.model.Task_Master_Dtl;
import com.excel.repository.AccTranRepository;
import com.excel.repository.CompanyMasterRepository;
import com.excel.repository.LocationRepository;
import com.excel.repository.TaskMasterRepository;
import com.excel.repository.TransactionalRepository;
import com.excel.utility.MedicoConstants;

@Service
public class AccTranServiceImpl implements AccTranService,MedicoConstants {

	@Autowired
	private AccTranRepository accTranRepo;
	@Autowired
	private LocationRepository locRepo;
	@Autowired
	private TaskMasterRepository taskMastRepo;
	@Autowired
	private TransactionalRepository transactionalRepository;
	@Autowired private CompanyMasterRepository compMasterRepo;

	@Override
	public String getMaxOfAccTranType(String companyCode) throws Exception {
		return accTranRepo.getMaxOfAccTranType(companyCode);
	}

	@Override
	public List<CustomAccTranGeneration> getAccTranTypeByLocId(Long loc_id) throws Exception {
		return accTranRepo.getAccTranTypeByLocId(loc_id);
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public void saveApprTransForLocation(Long loc_id, String approver_id, String companyCd) throws Exception {
		List<TranNameEnum> all_tran_types = new ArrayList<>(EnumSet.allOf(TranNameEnum.class));
		List<CustomAccTranGeneration> list_of_trans = this.accTranRepo.getAccTranTypeByLocId(loc_id);

		String loc_prefix = this.locRepo.getLocPrefixById(loc_id);
		if (loc_prefix == null) {
			throw new Exception("Loc Prefix is null.");
		}
		String sub_comp_code = this.compMasterRepo.getSubComnpanyCodeByLocId(loc_id);
		if (sub_comp_code == null) {
			throw new Exception("Sub Company Code is null.");
		}
		
		if (list_of_trans == null || list_of_trans.size() == 0) {
			// save all transactions
			for (TranNameEnum tt : all_tran_types) {
				this.saveAccTranTypes(tt, loc_id, loc_prefix, approver_id, companyCd,sub_comp_code);
			}
		} else if (list_of_trans.size() > 0) {
			// check which tran types are missing and save them first
			List<TranNameEnum> present_trans = list_of_trans.stream().map(elem -> elem.getFull_tran_name())
					.collect(Collectors.toList());

			List<TranNameEnum> missing_trans = all_tran_types.stream().filter(elem -> !present_trans.contains(elem))
					.collect(Collectors.toList());

			for (TranNameEnum tt : missing_trans) {
				this.saveAccTranTypes(tt, loc_id, loc_prefix, approver_id, companyCd,sub_comp_code);
			}

			// check which trans have null task master ids and then save them
			List<TranNameEnum> trans_without_task_master = list_of_trans.stream()
					.filter(elem -> elem.getTask_master() == null || elem.getTask_master() == 0L)
					.map(elem -> elem.getFull_tran_name()).collect(Collectors.toList());

			for (TranNameEnum tt : trans_without_task_master) {
				// get acc tran by full tran tran name and loc_id
				Acc_Tran_Type ac = this.getAccRecordByFullTranNameAndLocId(tt, loc_id);
				this.saveTaskMasterByAccType(ac, approver_id);
			}

			// check which trans have task master but null task master dtls
			List<TranNameEnum> trans_without_task_master_dtl = list_of_trans.stream()
					.filter(elem -> elem.getTask_master() != null && elem.getTask_master() != 0L
							&& (elem.getTask_master_dtl_id() == null || elem.getTask_master_dtl_id() == 0L))
					.map(elem -> elem.getFull_tran_name()).collect(Collectors.toList());

			for (TranNameEnum tt : trans_without_task_master_dtl) {
				// get task_master by acc_tran_type
				TaskMaster tm = this.taskMastRepo.getTaskMasterByTranTypeFromAccTran(tt, loc_id);
				this.saveTaskMasterDtlByTaskMaster(tm, approver_id);
			}
		}
	}

	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	private void saveAccTranTypes(TranNameEnum tran, Long loc_id, String loc_prefix, String approver_id,
			String companyCd,String subcompany) throws Exception {
		// get max tran no
		String maxAccTranType = this.getMaxOfAccTranType(companyCd);
		Long max_tran_as_numeric = Long.valueOf(maxAccTranType) + 1L;
		String next_tran = max_tran_as_numeric < 10L ? "0".concat(max_tran_as_numeric.toString())
				: max_tran_as_numeric.toString();
		
		Acc_Tran_Type acc_tran = new Acc_Tran_Type();
		acc_tran.setTran_type(next_tran);
		acc_tran.setTran_type_description(
				tran.toString().concat("-APPR-").concat(subcompany.trim()).concat("-").concat(loc_prefix));
		acc_tran.setOpen_tran_type(next_tran);
		acc_tran.setCompany_cd(companyCd);
		acc_tran.setFull_tran_name(tran);
		acc_tran.setLoc_id(loc_id);
		acc_tran.setTran_type_display_name(tran_type_display_names_map.get(tran.toString()));
		this.transactionalRepository.persist(acc_tran);
		// code to save
		this.saveTaskMasterByAccType(acc_tran, approver_id);
	}

	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	private void saveTaskMasterByAccType(Acc_Tran_Type acc_tran, String approver_id) throws Exception {
		TaskMaster task_master = new TaskMaster();
		task_master.setCompany_cd(acc_tran.getCompany_cd());
		task_master.setDiv_id(1L);
		task_master.setTask_name(acc_tran.getTran_type_description());
		task_master.setTask_descr(acc_tran.getTran_type_description());
		task_master.setApproval_req("Y");
		task_master.setCompletion_time(10);
		task_master.setReminder_time(4);
		task_master.setCreated_date(new Date());
		task_master.setCreated_by("administrator");
		task_master.setLast_mod_date(new Date());
		task_master.setLast_mod_by("administrator");
		task_master.setApproval_type("001");
		task_master.setInv_grp_id(1394L);
		task_master.setAppr_level(1L);
		task_master.setValue_range_from(BigDecimal.ZERO);
		task_master.setValue_range_to(BigDecimal.ZERO);
		task_master.setTran_type(acc_tran.getTran_type());
		task_master.setPercentage_range_from(BigDecimal.ZERO);
		task_master.setPercentage_range_to(BigDecimal.ZERO);
		task_master.setFs_id(0L);
		this.transactionalRepository.persist(task_master);
		// code to save
		this.saveTaskMasterDtlByTaskMaster(task_master, approver_id);
	}

	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	private void saveTaskMasterDtlByTaskMaster(TaskMaster task_master, String approver_id) throws Exception {
		// code to save
		Task_Master_Dtl task_Master_Dtl = new Task_Master_Dtl();
		task_Master_Dtl.setTask_id(task_master.getTask_id());
		task_Master_Dtl.setCompany_cd(task_master.getCompany_cd());
		task_Master_Dtl.setIdentitylinktype("assignee");
		task_Master_Dtl.setIdentitylinktype_val(approver_id);
		
		this.transactionalRepository.persist(task_Master_Dtl);
	}

	@Override
	public Acc_Tran_Type getAccRecordByFullTranNameAndLocId(TranNameEnum full_tran_name, Long loc_id) throws Exception {
		return accTranRepo.getAccRecordByFullTranNameAndLocId(full_tran_name, loc_id);
	}

}
