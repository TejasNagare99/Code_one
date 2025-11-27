package com.excel.service;

import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.activiti.engine.RuntimeService;
import org.activiti.engine.runtime.ProcessInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.excel.bean.AllocationConsolidationBean;
import com.excel.bean.AnnualPlanConsolidationBean;
import com.excel.bean.MonthlyAllocConsolidationBean;
import com.excel.exception.MedicoException;
import com.excel.model.AllocConDt;
import com.excel.model.AllocConHd;
import com.excel.model.Alloc_gen_hd;
import com.excel.model.AspHeader;
import com.excel.model.DivMaster;
import com.excel.model.Location;
import com.excel.model.TaskMaster;
import com.excel.model.Task_Master_Dtl;
import com.excel.repository.AllocConsolidationRepository;
import com.excel.repository.AllocationGenHeaderRepository;
import com.excel.repository.AllocationRepository;
import com.excel.repository.DivisionMasterRepository;
import com.excel.repository.TransactionalRepository;
import com.excel.repository.UserMasterRepository;
import com.excel.utility.CodifyErrors;
import com.excel.utility.MedicoConstants;
import com.excel.utility.SendMail;

@Service
public class AllocationConsolidationServiceImpl  implements AllocationConsolidationService,MedicoConstants{

	@Autowired AllocConsolidationRepository allocConsolidationRepository;
	@Autowired AllocationRepository allocationRepository;
	@Autowired TransactionalRepository transactionalRepository;
	@Autowired DivisionMasterRepository divisionMasterRepository;
	@Autowired UserMasterRepository userMasterRepository;
	@Autowired TaskMasterService taskMasterService;
	@Autowired RuntimeService runtimeService;
	@Autowired SendMail sendMail;
	@Autowired LocationService locationService;
	@Autowired AllocationGenHeaderRepository allocationGenHeaderRepository;
	@Override
	public List<Object> getAnnualPlanEnteredManagerByDivision(String empId,Long divId, String year, String status,String compcode) throws Exception {
		// TODO Auto-generated method stub
		return allocConsolidationRepository.getAnnualPlanEnteredManagerByDivision(empId,divId, year, status,compcode);
	}

	@Override
	public String getAnnualPlanEnteredBrandsByHeader(Long aspId) throws Exception {
		// TODO Auto-generated method stub
		return allocConsolidationRepository.getAnnualPlanEnteredBrandsByHeader(aspId);
	}

	@Override
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public Map<String, Object>  saveAnnualPlanTeamConsolidation(AnnualPlanConsolidationBean bean) throws Exception {
		Map<String, Object> map = new HashMap<>();
		AspHeader aspHeader=null; 
		AllocConHd allocConHd=null;
		AllocConDt allocConDt=null;
		boolean checkHeader=true;
		DivMaster div=this.divisionMasterRepository.getObjectById(bean.getTeamId());
		String documentNumber="XT"+"AN"+bean.getFinYear()+"_"+div.getDiv_code_nm();
		try {
			for(Long aspId:bean.getAspIds()) {
				if(aspId!=0L) {
					aspHeader=this.allocationRepository.getObjectAspHeader(aspId);
					if(checkHeader==true) {
						allocConHd=this.allocConsolidationRepository.getAllocConHdForAnnualPlan(aspHeader.getAsp_div_id(), bean.getFinYear(),bean.getConsolidationType());
						if(allocConHd==null) {
							allocConHd=new AllocConHd();
							allocConHd.setAlloc_appr_status('E');
							allocConHd.setAlloc_con_date(new Date());
							allocConHd.setAlloc_con_type(bean.getConsolidationType());
							allocConHd.setAlloc_cycle(0L);
							allocConHd.setAlloc_month("0");
							allocConHd.setAlloc_use_month("0");
							allocConHd.setAssistant_appr_status('E');
							allocConHd.setCompany(bean.getCompanyCode());
							allocConHd.setDISP_ADVICE("");
							allocConHd.setDiv_id(aspHeader.getAsp_div_id());
							allocConHd.setDivision(div.getDiv_disp_nm());
							allocConHd.setEffective_date(new Date());
							allocConHd.setFile_download('D');
							allocConHd.setFile_upload('D');
							allocConHd.setFin_year(bean.getFinYear());
							allocConHd.setStatus('A');
							allocConHd.setUser_id(bean.getEmpId());
							allocConHd.setAlloc_con_docno("0");
							allocConHd.setAlloc_type(bean.getAllocType());
							allocConHd.setUpd_date(new Date());
							allocConHd.setUpd_ip_add(bean.getIpAddress());
							this.transactionalRepository.persist(allocConHd);
							allocConHd.setAlloc_con_docno(documentNumber+"_"+allocConHd.getAlloc_con_id());
							System.out.println("Doc No. "+allocConHd.getAlloc_con_docno());
							this.transactionalRepository.update(allocConHd);
						}
						checkHeader=false;
					}
					allocConDt=new AllocConDt();
					allocConDt.setAlloc_con_date(new Date());
					allocConDt.setAlloc_con_docno(allocConHd.getAlloc_con_docno());
					allocConDt.setAlloc_con_id(allocConHd);
					allocConDt.setAlloc_doc_no(aspHeader.getAsp_alloc_number());
					allocConDt.setAlloc_con_type(bean.getConsolidationType());
					allocConDt.setAlloc_gen_id(aspHeader.getAsp_id());
					allocConDt.setAlloc_type(bean.getAllocType().charAt(0));
					allocConDt.setCompany(bean.getCompanyCode());
					allocConDt.setDiv_id(aspHeader.getAsp_div_id());
					allocConDt.setDivision(div.getDiv_disp_nm());
					allocConDt.setFin_year(aspHeader.getAsp_fin_year());
					allocConDt.setMod_upd_date(new Date());
					allocConDt.setPeriod_code("0");
					allocConDt.setAlloc_con_id_t(0L);
					allocConDt.setAlloc_con_docno_t("0");
					allocConDt.setAlloc_month("0");
					allocConDt.setAlloc_use_month("0");
					allocConDt.setUpd_date(new Date());
					allocConDt.setUpd_ip_add(bean.getIpAddress());
					allocConDt.setUser_id(bean.getEmpId());
					allocConDt.setStatus('A');
					this.transactionalRepository.persist(allocConDt);
					
					aspHeader.setAlloc_con_docno_t(allocConHd.getAlloc_con_docno());
					aspHeader.setAlloc_con_id_t(allocConHd.getAlloc_con_id());
					aspHeader.setAlloc_con_dtl_id_t(allocConDt.getAlloc_con_dtl_id());
					this.transactionalRepository.update(aspHeader);
				}	
			}
			map.put("documentNumber",allocConHd.getAlloc_con_docno());
			map.put("allocConId",allocConHd.getAlloc_con_id());
		}
		catch (Exception e) {
			System.out.println("Error Occurred :" +e.getMessage());// uncomment asneeded --;
		}
		return map;
	}

	@Override
	public List<Object> getConsolatedAnnualPlan(Long LocId, String year) throws Exception {
		// TODO Auto-generated method stub
		return this.allocConsolidationRepository.getConsolatedAnnualPlan(LocId,year);
	}
	
	@Override
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public void sendAnnualAllocationCompany(Long allocConId, String user, String email, String remark,HttpServletRequest request, HttpSession session) throws Exception {

		// String ip_addr = request.getRemoteAddr();
		System.out.println("user " + user);
		String tranType = null;
		String companyCode = session.getServletContext().getAttribute(COMPANY_CODE).toString();
		AllocConHd allocConHd =null;
		try {
			allocConHd=this.allocConsolidationRepository.getObjectAllocConHeader(allocConId);
			if (allocConHd.getAlloc_appr_status()=='E') {
				Location location = locationService.getLocationNameByEmployeeId(user);
//				if (MedicoConstants.PIPL_LOC.contains(location.getLoc_id())) {
//					tranType = MedicoConstants.ANNUAL_APPR_TEAM_PIPL;
//				} else {
//					tranType = MedicoConstants.ANNUAL_APPR_TEAM_PFZ;
//				}
				
				// new alternate logic
				tranType = this.taskMasterService.getTranTypeByLocIdAndApprovalType(location.getLoc_id(), ASP_TEAM_CONSOLIDATION,
						companyCode);
				if (tranType == null || tranType.isEmpty()) {
					throw new MedicoException("Could not find Approval Data !");
				}
				
				String TASK_FLOW = MedicoConstants.BPM_LEVELWISE;
				System.out.println("tranType " + tranType);
				List<TaskMaster> masters = taskMasterService.getTask(allocConHd.getDiv_id(), null, null, tranType, null,null, TASK_FLOW, null, null);

				if (masters.size() == 0) {
					throw new MedicoException("Task master record not found");
				}
				TaskMaster taskMaster = masters.get(0);
				List<Task_Master_Dtl> task_Master_Dtls = taskMasterService.getTaskDetail(taskMaster.getTask_id());
				if (task_Master_Dtls.size() == 0) {
					throw new MedicoException("Task master detail record not found");
				}

				Map<String, Object> variables = new HashMap<String, Object>();
				variables.put("initiator", user);
				variables.put("initiator_desc", user);
				variables.put("initiator_email", email);
				variables.put("tran_ref_id", allocConHd.getAlloc_con_id());
				variables.put("tran_type", tranType);
				variables.put("inv_group",allocConHd.getDiv_id());
				variables.put("loc_id", 0L);
				variables.put("cust_id", 0L);
				variables.put("tran_no", allocConHd.getAlloc_con_docno());
				variables.put("company_code", companyCode);
				variables.put("totaltask", masters.size());
				variables.put("amount", BigDecimal.ZERO);
				variables.put("escalatetime", null);
				variables.put("fin_year_id",allocConHd.getFin_year());
				variables.put("stock_point_id", 0L);
				variables.put("doc_type",ANNUAL_TEAM_DOC_TYPE);
				variables.put("task_flow", TASK_FLOW);
				variables.put("remark", remark);
				ProcessInstance processInstance = runtimeService.startProcessInstanceByKey("annualAllocationTeamApproval",variables);
				// changing approval status
				allocConHd.setAlloc_appr_status('F');
				this.transactionalRepository.update(allocConHd);
			}
			this.sendMail.sendMail(allocConHd.getAlloc_con_id(), user, "selfApprove", null, "", "nextApproval",
					Long.valueOf(allocConHd.getFin_year()), tranType,companyCode,ASP_TEAM_CONSOLIDATION);
		} catch (Exception e) {
			System.out.println("Error Occurred :" +e.getMessage());// uncomment asneeded --;
		}
		// sending mail after self Approval
		//this.sendMail.sendMail(header.getAsp_id(), user, "selfApprove", null, "", "nextApproval",Long.valueOf(header.getAsp_fin_year()), tranType);
	}
	//activity
	@Override
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public void finalAnnualTeamApproval(Long allocCOnId, String last_approved_by, String comp_cd, String isapproved,String motivation) throws Exception {
		System.out.println("In final Approval");
		try {

			if (isapproved.equalsIgnoreCase("A")) {
				AllocConHd header = this.allocConsolidationRepository.getObjectAllocConHeader(allocCOnId);
				header.setAlloc_appr_status('A');
				this.transactionalRepository.update(header);
			} else if (isapproved.equalsIgnoreCase("D")) {
				AspHeader aspHeader=null;
				AllocConHd header = this.allocConsolidationRepository.getObjectAllocConHeader(allocCOnId);
				header.setStatus('D');
				this.transactionalRepository.update(header);
				for(AllocConDt detail:header.getAllocConDt()) {
					aspHeader=allocationRepository.getObjectAspHeader(detail.getAlloc_gen_id());
					aspHeader.setAlloc_con_docno_t(null);
					aspHeader.setAlloc_con_dtl_id_t(null);
					aspHeader.setAlloc_con_id_t(null);
					this.transactionalRepository.update(aspHeader);
				}
			}
		} catch (Exception e) {
			System.out.println("Error Occurred :" +e.getMessage());// uncomment asneeded --;
		}
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public Map<String, Object>  saveAnnualPlanCompanyConsolidation(AnnualPlanConsolidationBean bean) throws Exception {
		Map<String, Object> map = new HashMap<>();
		AspHeader aspHeader=null; 
		AllocConHd allocConHd=null;
		AllocConDt allocConDt=null;
		AllocConHd teamHeader=null;
		boolean checkHeader=true;
		String documentNumber="XC"+"AN"+bean.getFinYear()+"_";
		try {
			for(Long allocConId:bean.getAllocConIds()) {
				System.out.println("alloc con id "+allocConId);
				if(allocConId!=0L) {
					if(checkHeader==true) {
						allocConHd=this.allocConsolidationRepository.getAllocConHdForAnnualPlan(null, bean.getFinYear(),bean.getConsolidationType());
						if(allocConHd==null) {
							allocConHd=new AllocConHd();
							allocConHd.setAlloc_appr_status('E');
							allocConHd.setAlloc_con_date(new Date());
							allocConHd.setAlloc_con_type(bean.getConsolidationType());
							allocConHd.setAlloc_cycle(0L);
							allocConHd.setAlloc_month("0");
							allocConHd.setAlloc_use_month("0");
							allocConHd.setAssistant_appr_status('E');
							allocConHd.setCompany(bean.getCompanyCode());
							allocConHd.setDISP_ADVICE("");
							allocConHd.setDiv_id(null);
							allocConHd.setDivision(null);
							allocConHd.setEffective_date(new Date());
							allocConHd.setFile_download('D');
							allocConHd.setFile_upload('D');
							allocConHd.setFin_year(bean.getFinYear());
							allocConHd.setStatus('A');
							allocConHd.setUser_id(bean.getEmpId());
							allocConHd.setAlloc_con_docno("0");
							allocConHd.setAlloc_type(bean.getAllocType());
							allocConHd.setUpd_date(new Date());
							allocConHd.setUpd_ip_add(bean.getIpAddress());
							this.transactionalRepository.persist(allocConHd);
							allocConHd.setAlloc_con_docno(documentNumber+"_"+allocConHd.getAlloc_con_id());
							System.out.println("Doc No. "+allocConHd.getAlloc_con_docno());
							this.transactionalRepository.update(allocConHd);
						}
						checkHeader=false;
					}
					
					teamHeader=this.allocConsolidationRepository.getObjectAllocConHeader(allocConId);
					List<AllocConDt> allocConDtlist = teamHeader.getAllocConDt().stream().collect(Collectors.toList());
					for (AllocConDt detail : allocConDtlist) {
						System.out.println("status "+detail.getStatus());
						if(detail.getStatus()=='A') {
							allocConDt=new AllocConDt();
							allocConDt.setAlloc_con_date(new Date());
							allocConDt.setAlloc_con_docno(allocConHd.getAlloc_con_docno());
							allocConDt.setAlloc_con_id(allocConHd);
							allocConDt.setAlloc_doc_no(detail.getAlloc_doc_no());
							allocConDt.setAlloc_con_type(bean.getConsolidationType());
							allocConDt.setAlloc_gen_id(detail.getAlloc_gen_id());
							allocConDt.setAlloc_type(bean.getAllocType().charAt(0));
							allocConDt.setCompany(bean.getCompanyCode());
							allocConDt.setDiv_id(detail.getDiv_id());
							allocConDt.setDivision(detail.getDivision());
							allocConDt.setFin_year(bean.getFinYear());
							allocConDt.setAlloc_con_id_t(detail.getAlloc_con_id().getAlloc_con_id());
							allocConDt.setAlloc_con_docno_t(detail.getAlloc_con_docno());
							allocConDt.setMod_upd_date(new Date());
							allocConDt.setPeriod_code("0");
							allocConDt.setAlloc_month("0");
							allocConDt.setAlloc_use_month("0");
							allocConDt.setUpd_date(new Date());
							allocConDt.setUpd_ip_add(bean.getIpAddress());
							allocConDt.setUser_id(bean.getEmpId());
							allocConDt.setUser_id(bean.getEmpId());
							allocConDt.setStatus('A');
							this.transactionalRepository.persist(allocConDt);
							
							aspHeader=this.allocationRepository.getObjectAspHeader(detail.getAlloc_gen_id());
							aspHeader.setAlloc_con_docno_c(allocConHd.getAlloc_con_docno());
							aspHeader.setAlloc_con_id_c(allocConHd.getAlloc_con_id());
							aspHeader.setAlloc_con_dtl_id_c(allocConDt.getAlloc_con_dtl_id());
							this.transactionalRepository.update(aspHeader);
						}
					}
				}	
			}
			map.put("documentNumber",allocConHd.getAlloc_con_docno());
		}
		catch (Exception e) {
			System.out.println("Error Occurred :" +e.getMessage());// uncomment asneeded --;
		}
		return map;
	}

	@Override
	public List<Object> getMonthlyAllocationEnteredManagerByDivision(Long divId, String year, String allocMonth,String allocType,String monthOfUse,String status,String compcode) throws Exception {
		// TODO Auto-generated method stub
		return this.allocConsolidationRepository.getMonthlyAllocationEnteredManagerByDivision(divId, year, allocMonth, allocType,monthOfUse, status,compcode);
	}
	
	@Override
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public Map<String, Object>  saveMonthlyAlloConsolidation(MonthlyAllocConsolidationBean bean) throws Exception {
		Map<String, Object> map = new HashMap<>();
		Alloc_gen_hd alloc_gen_hd=null; 
		AllocConHd allocConHd=null;
		AllocConDt allocConDt=null;
		boolean checkHeader=true;
		DivMaster div=this.divisionMasterRepository.getObjectById(bean.getTeamId());
		String documentNumber="X"+bean.getAllocType()+"_"+bean.getFinYear()+"_"+div.getDiv_code_nm();//AllocDocNum Generation
		try {
			for(Long allocGenId:bean.getAllocGenIds()) {
				if(allocGenId!=0L) {
					alloc_gen_hd=this.allocationGenHeaderRepository.getObjectById(allocGenId);
					if(checkHeader==true) {
						allocConHd=this.allocConsolidationRepository.getAllocConHdForMonthlyAllocation(alloc_gen_hd.getDiv_id(), bean.getFinYear(),bean.getConsolidationType(),alloc_gen_hd.getAlloc_month(),alloc_gen_hd.getAlloc_type());
						if(allocConHd==null || bean.getAllocType().equals("A") || bean.getAllocType().equals("M")) {
							allocConHd=new AllocConHd();
							allocConHd.setAlloc_appr_status('A');
							allocConHd.setAlloc_con_date(new Date());
							allocConHd.setAlloc_con_type(bean.getConsolidationType());
							allocConHd.setAlloc_cycle(alloc_gen_hd.getAlloc_cycle());
							allocConHd.setAlloc_month(alloc_gen_hd.getAlloc_month());
							allocConHd.setAlloc_use_month(alloc_gen_hd.getAlloc_use_month());
							allocConHd.setAssistant_appr_status('E');
							allocConHd.setCompany(bean.getCompanyCode());
							allocConHd.setDISP_ADVICE("");
							allocConHd.setDiv_id(alloc_gen_hd.getDiv_id());
							allocConHd.setDivision(div.getDiv_disp_nm());
							allocConHd.setEffective_date(new Date());
							allocConHd.setFile_download('N');
							allocConHd.setFile_upload('N');
							allocConHd.setFin_year(bean.getFinYear());
							allocConHd.setStatus('A');
							allocConHd.setUser_id(bean.getEmpId());
							allocConHd.setAlloc_con_docno(documentNumber);
							allocConHd.setAlloc_type(bean.getAllocType());
							allocConHd.setUpd_date(new Date());
							allocConHd.setUpd_ip_add(bean.getIpAddress());
							this.transactionalRepository.persist(allocConHd);
							allocConHd.setAlloc_con_docno(documentNumber+"_"+allocConHd.getAlloc_con_id());
							System.out.println("Doc No. "+allocConHd.getAlloc_con_docno());
							this.transactionalRepository.update(allocConHd);
						}
						checkHeader=false;
					}
					if(alloc_gen_hd.getAlloc_con_id()==null) {
						allocConDt=new AllocConDt();
						allocConDt.setAlloc_con_date(new Date());
						allocConDt.setAlloc_con_docno(allocConHd.getAlloc_con_docno());
						allocConDt.setAlloc_con_id(allocConHd);
						allocConDt.setAlloc_doc_no(alloc_gen_hd.getAlloc_doc_no());
						allocConDt.setAlloc_con_type(bean.getConsolidationType());
						allocConDt.setAlloc_gen_id(alloc_gen_hd.getAlloc_gen_id());
						allocConDt.setAlloc_type(bean.getAllocType().charAt(0));
						allocConDt.setCompany(bean.getCompanyCode());
						allocConDt.setDiv_id(alloc_gen_hd.getDiv_id());
						allocConDt.setDivision(div.getDiv_disp_nm());
						allocConDt.setFin_year(alloc_gen_hd.getFin_year());
						allocConDt.setMod_upd_date(new Date());
						allocConDt.setPeriod_code("");
						allocConDt.setAlloc_con_id_t(0L);
						allocConDt.setAlloc_con_docno_t("0");
						allocConDt.setAlloc_month(alloc_gen_hd.getAlloc_month());
						allocConDt.setAlloc_use_month(alloc_gen_hd.getAlloc_use_month());
						allocConDt.setUpd_date(new Date());
						allocConDt.setUpd_ip_add(bean.getIpAddress());
						allocConDt.setUser_id(bean.getEmpId());
						allocConDt.setStatus('A');
						this.transactionalRepository.persist(allocConDt);
					}
					
					alloc_gen_hd.setAlloc_con_date(allocConHd.getAlloc_con_date());
					alloc_gen_hd.setAlloc_con_id((allocConHd.getAlloc_con_id()));
					alloc_gen_hd.setAlloc_con_docno(allocConHd.getAlloc_con_docno());
					alloc_gen_hd.setAlloc_con_dtl_id(allocConDt.getAlloc_con_dtl_id());
					this.transactionalRepository.update(alloc_gen_hd);
					this.allocationRepository.updateAllocDt(allocConHd, allocConDt.getAlloc_con_dtl_id(), alloc_gen_hd);
				}	
			}
			map.put("documentNumber",allocConHd.getAlloc_con_docno());
			map.put("allocConId",allocConHd.getAlloc_con_id());
		}
		catch (Exception e) {
			System.out.println("Error Occurred :" +e.getMessage());// uncomment asneeded --;
		}
		return map;
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public void sendMonthlyAllocationTeam(Long allocConId, String user, String email, String remark,HttpServletRequest request, HttpSession session) throws Exception {

		// String ip_addr = request.getRemoteAddr();
		System.out.println("user " + user);
		String tranType = null;
		String companyCode = session.getServletContext().getAttribute(COMPANY_CODE).toString();
		AllocConHd allocConHd =null;
		try {
			allocConHd=this.allocConsolidationRepository.getObjectAllocConHeader(allocConId);
			if (allocConHd.getAlloc_appr_status()=='E') {
				Location location = locationService.getLocationNameByEmployeeId(user);
				if (MedicoConstants.PIPL_LOC.contains(location.getLoc_id())) {
					tranType = MedicoConstants.MONTHLY_APPR_TEAM_PIPL;
				} else {
					tranType = MedicoConstants.MONTHLY_APPR_TEAM_PFZ;
				}
				String TASK_FLOW = MedicoConstants.BPM_LEVELWISE;
				System.out.println("tranType " + tranType);
				List<TaskMaster> masters = taskMasterService.getTask(null, null, null, tranType, null,null, TASK_FLOW, null, null);

				if (masters.size() == 0) {
					throw new MedicoException("Task master record not found");
				}
				TaskMaster taskMaster = masters.get(0);
				List<Task_Master_Dtl> task_Master_Dtls = taskMasterService.getTaskDetail(taskMaster.getTask_id());
				if (task_Master_Dtls.size() == 0) {
					throw new MedicoException("Task master detail record not found");
				}

				Map<String, Object> variables = new HashMap<String, Object>();
				variables.put("initiator", user);
				variables.put("initiator_desc", user);
				variables.put("initiator_email", email);
				variables.put("tran_ref_id", allocConHd.getAlloc_con_id());
				variables.put("tran_type", tranType);
				variables.put("inv_group",null);
				variables.put("loc_id", 0L);
				variables.put("cust_id", 0L);
				variables.put("tran_no", allocConHd.getAlloc_con_docno());
				variables.put("company_code", companyCode);
				variables.put("totaltask", masters.size());
				variables.put("amount", BigDecimal.ZERO);
				variables.put("escalatetime", null);
				variables.put("fin_year_id",allocConHd.getFin_year());
				variables.put("stock_point_id", 0L);
				variables.put("doc_type",allocConHd.getAlloc_con_type());
				variables.put("task_flow", TASK_FLOW);
				variables.put("remark", remark);
				ProcessInstance processInstance = runtimeService.startProcessInstanceByKey("monthlyAllocationTeamApproval",variables);
				// changing approval status
				allocConHd.setAlloc_appr_status('F');
				this.transactionalRepository.update(allocConHd);
			}
		} catch (Exception e) {
			System.out.println("Error Occurred :" +e.getMessage());// uncomment asneeded --;
		}
		// sending mail after self Approval
		//this.sendMail.sendMail(header.getAsp_id(), user, "selfApprove", null, "", "nextApproval",Long.valueOf(header.getAsp_fin_year()), tranType);
	}
	
	//activiti
	@Override
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public void finalMonthlyTeamApproval(Long allocCOnId, String last_approved_by, String comp_cd, String isapproved,String motivation) throws Exception {
		System.out.println("In final Approval");
		try {

			if (isapproved.equalsIgnoreCase("A")) {
				AllocConHd header = this.allocConsolidationRepository.getObjectAllocConHeader(allocCOnId);
				header.setFile_download('N');
				this.transactionalRepository.update(header);
			} else if (isapproved.equalsIgnoreCase("D")) {
				AllocConHd header = this.allocConsolidationRepository.getObjectAllocConHeader(allocCOnId);
				header.setStatus('D');
				this.transactionalRepository.update(header);}
		} catch (Exception e) {
			System.out.println("Error Occurred :" +e.getMessage());// uncomment asneeded --;
		}
	}
	
	@Override
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public void updateDownload(Long alloc_id) throws Exception {
		AllocConHd header = this.allocConsolidationRepository.getObjectAllocConHeader(Long.valueOf(alloc_id));
		header.setFile_download('Y');
		this.transactionalRepository.update(header);
	}

	@Override
	public List<DivMaster> getDivisionsEnteredForMonthly(String allocMonth, String year, String allocType)
			throws Exception {
		// TODO Auto-generated method stub
		return this.allocConsolidationRepository.getDivisionsEnteredForMonthly(allocMonth, year, allocType);
	}

	@Override
	public List<DivMaster> getDivisionsEnteredForAnnualPlan(String year,String empId) throws Exception {
		// TODO Auto-generated method stub
		return this.allocConsolidationRepository.getDivisionsEnteredForAnnualPlan(year,empId);
	}

	@Override
	public List<AllocationConsolidationBean> getAllocConHeaderByType(String allocConType) throws Exception {
		// TODO Auto-generated method stub
		return this.allocConsolidationRepository.getAllocConHeaderByType(allocConType);
	}

	@Override
	public AllocConHd getAllocConHdForAnnualPlan(Long divId, String fin_year, String allocConType) {
		// TODO Auto-generated method stub
		return this.allocConsolidationRepository.getAllocConHdForAnnualPlan(divId, fin_year, allocConType);
	}

}
