package com.excel.restcontroller;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.activiti.engine.RuntimeService;
import org.activiti.engine.runtime.ProcessInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.excel.exception.MedicoException;
import com.excel.model.GrnDetails;
import com.excel.model.GrnHeader;
import com.excel.model.TaskMaster;
import com.excel.model.Task_Master_Dtl;
import com.excel.model.V_GRN_Header;
import com.excel.model.V_Grn_Details;
import com.excel.repository.GRNDetailRepository;
import com.excel.repository.GrnRepository;
import com.excel.repository.V_GRN_DetailRepository;
import com.excel.repository.V_GRN_HeaderRepository;
import com.excel.repository.grnHeaderRepository;
import com.excel.service.GRNService;
import com.excel.service.UserMasterService;
import com.excel.utility.MedicoConstants;
import com.excel.utility.SendMail;

@RestController
@RequestMapping("/rest")
public class GrnApprovalRestController implements MedicoConstants {

	@Autowired grnHeaderRepository grnHeaderRepository;
	@Autowired V_GRN_DetailRepository VGrnDetailRepo;
	@Autowired V_GRN_HeaderRepository VGrnHeaderRepo;
	@Autowired GRNDetailRepository grnDetailRepo;
	@Autowired GrnRepository grnRepository;
	@Autowired GRNService grnService;
	@Autowired private UserMasterService userMasterService;
	
	static String newFilePath = null;

	/*
	 * @GetMapping("/get-grn-approval-header-detail") //public Map<String, Object>
	 * getGrnApprovalHeaderDetail(@RequestParam String empId,HttpSession session){
	 * public Map<String, Object> getGrnApprovalHeaderDetail(@RequestParam String
	 * empId, HttpSession session){ Map<String, Object> map=new HashMap<>();
	 * 
	 * System.out.println("private medicoUtility : MedicoUtility,"+empId); String
	 * loginId=empId; System.out.println("Login Id is:"+loginId); List<V_GRN_Header>
	 * list=new ArrayList<>(); try { String companyCode =
	 * session.getServletContext().getAttribute(COMPANY_CODE).toString();
	 * if(loginId.equals("E00040")) {
	 * 
	 * List<V_GRN_Header>
	 * grnHeaderDetailList=VGrnHeaderRepo.findBygrnInsUsrId(empId); for(int
	 * i=0;i<grnHeaderDetailList.size();i++) { V_GRN_Header detail=
	 * grnHeaderDetailList.get(i); if(detail.getGrn_appr_status().equals("E")) {
	 * list.add(detail); } } }
	 * if(loginId.equals("E00118")||loginId.equals("E00119")) { List<V_GRN_Header>
	 * grnHeaderDetailList=VGrnHeaderRepo.findAll(); for(int
	 * i=0;i<grnHeaderDetailList.size();i++) {
	 * 
	 * V_GRN_Header detail= grnHeaderDetailList.get(i);
	 * if(detail.getGrn_appr_status().equals("F")) {
	 * if(detail.getGrn_period_code().equals("01") ||
	 * detail.getGrn_period_code().equals("02")) { list.add(detail);
	 * System.out.println("..."+list); } } } } map.put("loginId", loginId);
	 * map.put("list", list); } catch (Exception e) { System.out.println("Error Occurred :" +e.getMessage());// uncomment asneeded --; } return
	 * map; }
	 * 
	 * @GetMapping("/get-grn-approval-selected-detail") public Map<String,Object>
	 * getGrnApprovalSelectedDetail(@RequestParam long grnId){
	 * System.out.println(grnId); List<V_Grn_Details>
	 * grnDetailList=VGrnDetailRepo.findByGrndGrnId(grnId); List<V_Grn_Details>
	 * list=new ArrayList<>(); Map<String,Object> map=new HashMap<String,Object>();
	 * for(int i=0;i<grnDetailList.size();i++) { V_Grn_Details
	 * detailmodel=grnDetailList.get(i);
	 * //System.out.println(detailmodel.getGrnd_grn_id()); list.add(detailmodel); }
	 * System.out.println(list.size()); map.put("list", list); return map;
	 * 
	 * }
	 * 
	 * @GetMapping("/updateDetailOnSelfApproveOfGRNNew") public void
	 * updateDetailOnSelfApproveOfGRN(@RequestParam long grnId,HttpServletRequest
	 * request) { String ip_addr = request.getRemoteAddr(); List<GrnHeader>
	 * headerList=grnHeaderRepository.findByGrnId(grnId); for(int
	 * i=0;i<headerList.size();i++) { GrnHeader header=headerList.get(i);
	 * System.out.println(header); if(header.getGrn_appr_status().equals("E")) { try
	 * {
	 * 
	 * String TASK_FLOW=MedicoConstants.BPM_LEVELWISE; List<TaskMaster> masters =
	 * taskMasterService.getTask(null,
	 * null,null,MedicoConstants.GRN_APPR_PI,null,null,TASK_FLOW);
	 * 
	 * if(masters.size()==0){ throw new
	 * MedicoException("Task master record not found"); } TaskMaster
	 * taskMaster=masters.get(0); List<Task_Master_Dtl>
	 * task_Master_Dtls=taskMasterService.getTaskDetail(taskMaster.getTask_id());
	 * if(task_Master_Dtls.size()==0){ throw new
	 * MedicoException("Task master detail record not found"); }
	 * System.out.println("header.getGrn_delivery_no()"+header.getGrn_delivery_no())
	 * ;
	 * System.out.println("header.getGrn_delivery_no()"+header.getGrn_delivery_no())
	 * ; System.out.println("header.getGrn_fin_year()"+header.getGrn_fin_year());
	 * System.out.println("header.getGrnId()"+header.getGrnId());
	 * 
	 * Map<String, Object> variables = new HashMap<String, Object>();
	 * variables.put("initiator","demo");
	 * variables.put("initiator_email","demp@gmail.com");
	 * variables.put("tran_ref_id",header.getGrnId());
	 * variables.put("tran_type",MedicoConstants.GRN_APPR_PI);
	 * variables.put("inv_group",null);
	 * variables.put("loc_id",header.getGrn_loc_id()); variables.put("cust_id",0L);
	 * variables.put("tran_no",header.getGrn_delivery_no());
	 * variables.put("company_code","PZA"); variables.put("totaltask",
	 * masters.size()); variables.put("amount",BigDecimal.ZERO);
	 * variables.put("escalatetime",null);
	 * variables.put("fin_year_id",header.getGrn_fin_year());
	 * variables.put("stock_point_id",header.getGrn_loc_id());
	 * variables.put("doc_type","NS"); variables.put("task_flow",TASK_FLOW);
	 * variables.put("assignee_","123"); ProcessInstance processInstance =
	 * runtimeService .startProcessInstanceByKey("grnApproval",variables);
	 * 
	 * 
	 * Status Updated in header header.setGrn_appr_status("F");
	 * header.setGrn_mod_dt(new Date()); header.setGrn_mod_ip_add(ip_addr);
	 * header.setGrn_mod_usr_id("E00040"); grnHeaderRepository.save(header);
	 * System.out.println("appr status changed to F at self Approval"); } catch
	 * (Exception e) { System.out.println("Error Occurred :" +e.getMessage());// uncomment asneeded --; } } List<GrnDetails> detailList=
	 * grnDetailRepo.findBygrndGrnId(grnId); for(int i1=0;i1<detailList.size();i1++)
	 * { GrnDetails detail=detailList.get(i1);
	 * detail.setGrnd_image_path(newFilePath); grnDetailRepo.save(detail); } } }
	 */

	@GetMapping("/updateDetailOnSelfApproveOfGRN")
	public void updateDetailOnSelfApproveOfGRNNew(@RequestParam("grnId") Long grnId,
			@RequestParam("remark") String remark, @RequestParam("empEmail") String email, HttpServletRequest request,
			@RequestParam List<Long> detailIds,@RequestParam List<BigDecimal> shortQtys,
			@RequestParam List<BigDecimal> damageQtys,HttpSession session)
			throws Exception {
		String companyCode = session.getServletContext().getAttribute(COMPANY_CODE).toString();
		
		String empId = this.userMasterService.getEmployeeIdFromRequest(request);
		grnService.updateDetailOnSelfApproveOfGRN(grnId, empId, email, remark, request,detailIds,shortQtys,damageQtys,companyCode);	
	}

	/*
	 * @GetMapping("/updateDetailOnSelfDiscardOfGRN") public void
	 * updateDetailOnSelfDiscardOfGRN(@RequestParam long grnId,HttpServletRequest
	 * request) { String ip_addr = request.getRemoteAddr(); List<GrnHeader>
	 * headerList=grnHeaderRepository.findByGrnId(grnId); for(int
	 * i=0;i<headerList.size();i++) { GrnHeader header=headerList.get(i);
	 * System.out.println(header); if(header.getGrn_appr_status().equals("E")) {
	 * header.setGrn_appr_status("D"); header.setGrn_mod_dt(new Date());
	 * header.setGrn_mod_ip_add(ip_addr); header.setGrn_mod_usr_id("E00040");
	 * grnHeaderRepository.save(header);
	 * System.out.println("appr status changed to D at self Discard"); } } }
	 * 
	 * @PostMapping("/uploadFile") public void uploadFile(@RequestParam
	 * MultipartFile file) throws Exception {
	 * System.out.println("inside upload file");
	 * 
	 * String rootPath=MedicoConstants.GRN_APPR_FILE_PATH; File dir = new
	 * File(rootPath); if (!dir.exists()) {dir.mkdirs();}
	 * 
	 * File f=new File("file"); String filename =
	 * file.getOriginalFilename().toString();
	 * System.out.println("orignoal file name:"+filename); String
	 * newFileString=dir.getAbsolutePath() + File.separator +
	 * file.getOriginalFilename(); System.out.println(newFileString); byte[] bytes =
	 * file.getBytes(); BufferedOutputStream stream = new BufferedOutputStream( new
	 * FileOutputStream(newFileString)); stream.write(bytes); stream.close();
	 * 
	 * 
	 * newFilePath=newFileString;
	 * System.out.println("new file path is:"+newFileString);
	 * 
	 * }
	 * 
	 * @GetMapping("/viewGrnApprovalFile") public Object viewFile(@RequestParam long
	 * grnId) { System.out.println(grnId); Map<String,Object> map=new HashMap<>();
	 * List<GrnDetails> grnDetailList=grnDetailRepo.findBygrndGrnId(grnId); for(int
	 * i=0;i<grnDetailList.size();i++) { GrnDetails grnDetail=grnDetailList.get(i);
	 * String filePath=grnDetail.getGrnd_image_path(); File f=new File(filePath);
	 * String fileName=f.getName(); map.put("fileName", fileName);
	 * System.out.println(map.get("fileName")); } return map; }
	 */

}
