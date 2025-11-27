package com.excel.restcontroller;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.servlet.http.HttpServletRequest;

import org.activiti.engine.ProcessEngine;
import org.activiti.engine.RuntimeService;
import org.activiti.engine.TaskService;
import org.activiti.engine.runtime.ProcessInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.excel.exception.MedicoException;
import com.excel.model.Dispatch_Header;
import com.excel.model.TaskMaster;
import com.excel.model.Task_Master_Dtl;
import com.excel.repository.DispatchHeaderRepository;
import com.excel.repository.DispatchRepository;
import com.excel.service.DispatchService;
import com.excel.service.ProductMasterService;
import com.excel.service.TaskMasterService;
import com.excel.utility.MedicoConstants;

@RestController
@RequestMapping("/rest")
public class DispatchApprovalController implements MedicoConstants {

	@Autowired DispatchService dispatchService;
	@Autowired RuntimeService runtimeService;
	@Autowired TaskService taskService;
	@Autowired TaskMasterService taskMasterService;
	@Autowired ProcessEngine processEngine;
	@Autowired ProductMasterService productMasterService;
	@Autowired EntityManagerFactory emf;
	@Autowired DispatchHeaderRepository dispatchHeaderRepo;
	@Autowired DispatchRepository dispatchRepository;

	
//	@GetMapping("/getDivisionForDispatchApproval")
//	public Object getDivisionForDispatchApproval() {
//		Map<String,Object> map=new HashMap<>();
//		map=dispatchRepository.getDivisionForDispatchApproval();
//		return map;	
//	}
	/*
	 * @GetMapping("/SummaryChallanNoList") public Object
	 * SummaryChallanNoList(@RequestParam String empId,@RequestParam String
	 * divisionName) { Map<String,Object> map=new HashMap<>();
	 * map=dispatchRepository.SummaryChallanNoList(empId, divisionName); return map;
	 * }
	 */
	
//	@GetMapping("/getHeaderForDispatchApproval")
//	public Object getHeaderForDispatchApproval(@RequestParam String ChallanNo) {
//		System.out.println(ChallanNo);
//		Object mapList=new ArrayList<>();
//		mapList=dispatchRepository.getHeaderForDispatchApproval(ChallanNo);
//		return mapList;
//	}
//	
//	@GetMapping("/getDetailForDispatchApproval")
//	public Object getDetailForDispatchApproval(@RequestParam int dspId) {
//		System.out.println("dsp id in spring boot : "+dspId);
//		Map<String,Object> mapList=dispatchRepository.getDetailForDispatchApproval(dspId);
//		return mapList;	
//	}
	
//	@GetMapping("/updateDispatchHeaderForDispatchSelfApproval")
//	public void updateDispatchHeaderForDispatchSelfApproval(@RequestParam String empId, @RequestParam List<String> challanNumberList,HttpServletRequest request) throws Exception {
//		EntityManager em = emf.createEntityManager();
//		Dispatch_Header header=null;
//		String challanNumber=null;
//		Date date=new Date();
//		System.out.println(challanNumberList);
//		for(int i=0;i<challanNumberList.size();i++) {
//			challanNumber=challanNumberList.get(i);
//			System.out.println(challanNumber);
//			header=dispatchRepository.getDispatchHeaderByChallanNo(challanNumber);
//				if (header.getDsp_appr_status().equals("E")) {
//					try {
//						System.out.println("header.getDsp_id() "+header.getDsp_id());
//						String TASK_FLOW = MedicoConstants.BPM_LEVELWISE;
//						List<TaskMaster> masters = taskMasterService.getTask(null, null, null,
//								MedicoConstants.DSP_APPR_PFZ, null, null, TASK_FLOW);
//	
//						if (masters.size() == 0) {
//							throw new MedicoException("Task master record not found");
//						}
//						TaskMaster taskMaster = masters.get(0);
//						List<Task_Master_Dtl> task_Master_Dtls = taskMasterService.getTaskDetail(taskMaster.getTask_id());
//						if (task_Master_Dtls.size() == 0) {
//							throw new MedicoException("Task master detail record not found");
//						}
//	
//						Map<String, Object> variables = new HashMap<String, Object>();
//						variables.put("initiator", empId);
//						variables.put("initiator_desc", empId);
//						variables.put("initiator_email", "demo");
//						variables.put("tran_ref_id", header.getDsp_id());
//						variables.put("tran_type", MedicoConstants.DSP_APPR_PFZ);
//						variables.put("inv_group", null);
//						variables.put("loc_id", header.getDsp_loc_id());
//						variables.put("cust_id", 0L);
//						variables.put("tran_no", header.getDspChallanNo());
//						variables.put("company_code", "PFZ");
//						variables.put("totaltask", masters.size());
//						variables.put("amount", BigDecimal.ZERO);
//						variables.put("escalatetime", null);
//						variables.put("fin_year_id", header.getDsp_fin_year());
//						variables.put("stock_point_id", header.getDsp_state_id());
//						variables.put("doc_type","NS");
//						variables.put("task_flow", TASK_FLOW);
//						ProcessInstance processInstance = runtimeService.startProcessInstanceByKey("dispatchApproval",variables);
//						
//						//Changing Status		
//						String ip_addr= request.getRemoteAddr();
//						dispatchRepository.updateDispatchHeaderForSelfApproval(challanNumber, date, ip_addr, empId);
//					}
//					catch (Exception e) {
//						System.out.println("Error Occurred :" +e.getMessage());// uncomment asneeded --;
//					}
//				}
//		}
//	}
	

}


