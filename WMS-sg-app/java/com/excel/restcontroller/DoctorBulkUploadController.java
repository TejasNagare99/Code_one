package com.excel.restcontroller;

import java.nio.file.Files;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.activiti.engine.TaskService;
import org.apache.commons.collections4.map.HashedMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.excel.bean.DoctorBulkUploadBean;
import com.excel.exception.MedicoException;
import com.excel.model.Blk_hcp_req_hdr;
import com.excel.model.Blk_hcq_req_temp;
import com.excel.model.Period;
import com.excel.repository.DivMasterRepository;
import com.excel.service.AllocationService;
import com.excel.service.DashBoardService;
import com.excel.service.DoctorBulkAllocUploadService;
import com.excel.service.DoctorMasterService;
import com.excel.service.FieldStaffService;
import com.excel.service.ParameterService;
import com.excel.service.PeriodMasterService;
import com.excel.utility.CodifyErrors;
import com.excel.utility.MedicoConstants;

@RestController
@RequestMapping("/rest")
public class DoctorBulkUploadController implements MedicoConstants{

	@Autowired private ParameterService parameterService;
	@Autowired private DoctorBulkAllocUploadService doctorbulkallocuploadservice;
	@Autowired private DivMasterRepository divMasterRepository;
	@Autowired private FieldStaffService fieldStaffService;
	@Autowired private DoctorMasterService docMasterService;
	@Autowired private PeriodMasterService periodMasterService;
	@Autowired private AllocationService allocationService;
	@Autowired private TaskService taskService;
	@Autowired private DashBoardService dashBoardService;
	 
	@GetMapping("/getOnloadDataForBulkUpload")
	public Map<String,Object> getOnloadData(@RequestParam("empId") String empId,
			HttpSession session){
		Map<String,Object> map = null;
		try {
			String comp_code = session.getServletContext().getAttribute(COMPANY_CODE).toString();
			map = new HashMap<String,Object>();
			map.put("productTypes", this.allocationService.getPlanningType(empId));
			map.put("divisionList",this.divMasterRepository.getActiveDivListByEmpId(empId, comp_code));
			map.put("ReqNoList",doctorbulkallocuploadservice.getReqNoByStatus("E",N));
		}
		catch(Exception e) {
			e.printStackTrace();
		}
		return map;
	}
	
	@GetMapping("/get-field-staff-for-bulk-upload")
	public Map<String, Object> getFieldStaffList(@RequestParam("divId") Long divId
			,@RequestParam("levelcode") String levelcode) {
	    Map<String, Object> map = new HashMap<>();
		try {
			map.put("requestorList", this.fieldStaffService.getFieldStaffByDivAndLevelcode(divId, levelcode, "F"));
		} catch (Exception e) {
			System.out.println("Error Occurred :" +e.getMessage());// uncomment asneeded --;
		}
		return map;
	}
	
	
	@GetMapping("/get-doctors")
	public Map<String, Object> getFieldStaffDoctorDetails(@RequestParam("divId") String divId,
			@RequestParam("fsId") String fsId,
			@RequestParam("compareString") String compareString) {
	    Map<String, Object> map = new HashMap<>();
	    System.out.println("values received \n divId::"+divId+"\n fsId::"+fsId+"\n compareString::"+compareString);
		try {
			map.put("doctorsList", this.docMasterService.getDoctorsForBulkUpload(divId,fsId,compareString));
		} catch (Exception e) {
			
			e.printStackTrace();// uncomment asneeded --;
		}
		return map;
	}
	

	@GetMapping("/get-prod-detials-for-hcp")
	public Map<String, Object> getProduct_details(@RequestParam("id") String id) {
	    Map<String, Object> map = new HashMap<>();
	    System.out.println("values received:"+id);
		try {
			map.put("doctorsList", this.docMasterService.getProduct_details(id));
		} catch (Exception e) {
			
			e.printStackTrace();// uncomment asneeded --;
		}
		return map;
	}
	
	
	@GetMapping("/get-BrandFilters")
	public Map<String, Object> getBrandDataforprodDoctorbulkalloc(
			@RequestParam("teamId") String teamId,
			@RequestParam("planType") String planType,
			@RequestParam("userid") String userid) {
		Map<String, Object> map = new HashMap<>();
		try {
			map.put("brands",doctorbulkallocuploadservice.getBrands(Long.valueOf(teamId),Long.valueOf(planType),userid));
		}
		catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return map;
	}
	
	@GetMapping("/get-Products")
	public Map<String, Object> getProductforBulkUpload(@RequestParam("divId") String divId,
			@RequestParam("prodtype") String prodtype,@RequestParam("brands") String brands) {
		Map<String, Object> map = new HashMap<>();
	try {
		System.out.println("divId "+divId);
		System.out.println("prodtype "+prodtype);
		System.out.println("brands "+brands);
		
		map.put("productList", doctorbulkallocuploadservice.getProductsForBulkUpload(Long.valueOf(divId),Long.valueOf(prodtype), brands));
	}
	catch (Exception e) {
		// TODO: handle exception
		e.printStackTrace();
	}
	return map;
	}
	
	
	@PostMapping("/save-alloc-bulk-product")
	public Map<String, Object> SaveAllocBulkUpload(@RequestBody DoctorBulkUploadBean bean,
			HttpSession session,HttpServletRequest request) {
		Map<String, Object> map = new HashMap<>();
		try {
			String comp_code = session.getServletContext().getAttribute(COMPANY_CODE).toString();
			bean.setCompany(comp_code);
			bean.setBlk_ip_add(request.getRemoteAddr());
			map = doctorbulkallocuploadservice.saveBulkDoctorproducts(bean);
			map.put(DATA_SAVED, true);
		}
		catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			map.put(DATA_SAVED, false);
		}
		return map;
		
	}
	
	
	@PostMapping("/save-bulk-upload-doctors")
	public Map<String,Object>saveDoctorBulkUpload(@RequestBody DoctorBulkUploadBean doctorBulkUploadBean,
			HttpSession session,HttpServletRequest request){
		Map<String, Object> map = new HashMap<>();
		map.put(DATA_SAVED, true);
		try {
			String companyCode = session.getServletContext().getAttribute(COMPANY_CODE).toString();
			doctorBulkUploadBean.setBlk_ip_add(request.getRemoteAddr());
			doctorBulkUploadBean.setCompany(companyCode);
			map = doctorbulkallocuploadservice.saveBulkDoctorUpload(doctorBulkUploadBean);
			map.put(DATA_SAVED, true);
		}
		catch(Exception e){
			e.printStackTrace();
			map.put(DATA_SAVED, false);
		}
		
		return map;
	}
	
	@PostMapping("/hcp-selection-via-file")
	public Map<String,Object> uploadDoctorsViaFile(@RequestParam MultipartFile file,
			@RequestParam String empId,
			@RequestParam String username,
			@RequestParam String role,
			@RequestParam String finyr,
			@RequestParam String masterType,
			@RequestParam String divId,
			HttpServletRequest request,
			HttpSession session){
		Map<String,Object> map = null;
		try {
			System.out.println("empId::"+empId);
			System.out.println("username ::"+username);
			System.out.println("masterType::"+masterType);
			System.out.println("file::"+file.getOriginalFilename());
			System.out.println("divId::"+divId);
			
			//generate doctor bean//if structural errors then give errors in xls
			//save file if valid and give warnings where necessary
			//if invalid then give error list to user in a xls
			map = doctorbulkallocuploadservice.getListOfHcpsFromFileOrStructuralErrors(divId, masterType, file);
			
		}
		catch(Exception e) {
			map.put("STATUS", "F");
			e.printStackTrace();
		}
		return map;
	}
	
	@PostMapping("/generate-bulk-upload-csv")
	public Map<String,Object> downloadBulkUploadCsv(@RequestParam String empId,
			@RequestParam String bulkReqNo,@RequestParam String bulkReqId){
		
		Map<String, Object> map = new HashMap<>();
		List<Object[]> hdrAndDocList = null;
		List<Object[]> prodList = null;
		String filename = null;
		
		try {
			hdrAndDocList = this.doctorbulkallocuploadservice
					.getHdrAndDocListForCsvGeneration(Long.valueOf(bulkReqId));
			prodList = this.doctorbulkallocuploadservice.getProdListForCsvGeneration(Long.valueOf(bulkReqId));
			
			if(hdrAndDocList!=null && prodList!=null && !hdrAndDocList.isEmpty() && !prodList.isEmpty()) {
				filename = this.doctorbulkallocuploadservice.getCsvForBulkUpload(hdrAndDocList, prodList);
				if(filename!=null) {
					map.put("fileName", filename);
				}
			}
			else {
				map.put("msg", "No Data Found for this transaction");
			}
			
		}
		catch(Exception e) {
			map.put("msg", "Error Occurred");
			e.printStackTrace();
		}
		return map;
	}
	
	
	
	@PostMapping("/generate-bulk-upload-excel")
	public Map<String,Object> downloadBulkUploadExcel(@RequestParam String empId,
			@RequestParam String bulkReqNo,@RequestParam String bulkReqId){
		
		Map<String, Object> map = new HashMap<>();
		List<Object[]> hdrAndDocList = null;
		List<Object[]> prodList = null;
		String filename = null;
		Blk_hcp_req_hdr blk_header = null;
		System.out.println("bulkReqNo::::"+bulkReqNo+":::bulkReqId:::"+bulkReqId+":::empId:::"+empId);
		try {
			blk_header = this.doctorbulkallocuploadservice.getBlkHcpReqHdrById(Long.valueOf(bulkReqId.trim()));
			prodList = this.doctorbulkallocuploadservice.getProdListForCsvGeneration(Long.valueOf(bulkReqId));
			if(blk_header.getDoc_mst_type().trim().equals("M")) {
				hdrAndDocList = this.doctorbulkallocuploadservice
						.getHdrAndDocListForCsvGeneration(Long.valueOf(bulkReqId));
				if(hdrAndDocList!=null && prodList!=null && !hdrAndDocList.isEmpty() && !prodList.isEmpty()) {
					filename = this.doctorbulkallocuploadservice.getExcelForBulkUpload(hdrAndDocList, prodList,Long.valueOf(bulkReqId.trim()),empId);
					if(filename!=null) {
						map.put("fileName", filename);
					}
				}
				else {
					map.put("msg", "No Data Found for this transaction");
				}	
			}
			else if(blk_header.getDoc_mst_type().trim().equals("N")){
				if(prodList!=null && !prodList.isEmpty()) {
					filename = this.doctorbulkallocuploadservice.getExcelForBulkUploadNoMaster(blk_header, prodList,Long.valueOf(bulkReqId.trim()),empId);
					if(filename!=null) {
						map.put("fileName", filename);
					}
				}
				else {
					map.put("msg", "No Data Found for this transaction");
				}
			}	
		}
		catch(Exception e) {
			map.put("msg", "Transaction Failed");
			e.printStackTrace();
		}
		return map;
	}
	
	@PostMapping("/validate-file-for-upload")
	public Map<String,Object> validateFileBeforeSaving(
			@RequestParam MultipartFile file,
			@RequestParam String username,
			HttpServletRequest request,
			HttpSession session){
		Map<String,Object> map = new HashMap<String,Object>();
		try {
			System.out.println("file "+file.getOriginalFilename());
			if(file.getOriginalFilename().endsWith(".xlsx")) {
				map = this.doctorbulkallocuploadservice.validateExcelUploadForTemp(file);
				if((boolean)map.get("fileHasErrors") == true) {
					//file has errors return with error message
					map.put("msg", "File has errors . Please check the attached error file .");
					map.put("DATA_SAVED",N);
				}
				else if((boolean)map.get("fileHasWarnings") == true) {
					map.put("msg", "File has warnings . Please check the attached file.");
					map.put("DATA_SAVED",N);
				}
				else {
					//go ahead and save to the table
					this.doctorbulkallocuploadservice.saveFileToBulkTemp(file, username);
					map.put("msg", "Transaction Succesful");
					map.put("DATA_SAVED",Y);
				}
			}
			else {
				map.put("msg", "Please upload files in correct format");
			}
		}
		catch(MedicoException me) {
			map.put("msg", me.getMessage());
			map.put("DATA_SAVED",N);
			me.printStackTrace();
		}
		catch(Exception e) {
			map.put("msg", "File Processing Failed");
			map.put("DATA_SAVED",N);
			e.printStackTrace();
		}
		return map;
	}
	
	@PostMapping("/upload-doctor-supply-bulk-excel")
	public Map<String,Object> uploadDoctorSupplyCsv(
			@RequestParam MultipartFile file,
			@RequestParam String username,
			HttpServletRequest request,
			HttpSession session)
	{
		Map<String,Object> map = new HashMap<String,Object>();
		try {
			System.out.println("file "+file.getOriginalFilename());
			if(file.getOriginalFilename().endsWith(".xlsx")) {
				//check if file is valid 
					this.doctorbulkallocuploadservice.saveFileToBulkTemp(file, username);
					map.put("msg", "Transaction Succesful");
				}
				else {
					map.put("msg", "Invalid file format. Please upload only xlsx files.");
				}	
		}
		catch(MedicoException me) {
			map.put("msg", me.getMessage());
			me.printStackTrace();
		}
		catch(Exception e) {
			map.put("msg", "File Processing Failed");
			e.printStackTrace();
		}
		return map;
	}
	
	@GetMapping("/get-request-list")
	public Map<String,Object> getRequests(){
		Map<String,Object> map = new HashMap<String,Object>();
		try {
			map.put("requestList", this.doctorbulkallocuploadservice.getRequestListFromBlkHdr(false));
		}
		catch(Exception e) {
			e.printStackTrace();
		}
		return map;
	}
	
	@GetMapping("/get-onload-data-alloc-generation")
	public Map<String,Object> getOnloadData(HttpSession session){
		Map<String,Object> map = new HashMap<String,Object>();
		try {
			String comp_code = session.getServletContext().getAttribute(COMPANY_CODE).toString();
			Period period = this.periodMasterService.getPeriodMasterByCompany(comp_code);
			
			map.put("monthOfUse", this.allocationService.getPeriodListGreaterThanPeriodCode(period.getPeriod_id().toString()));
			map.put("requestList", this.doctorbulkallocuploadservice.getRequestListFromBlkHdr(true));
		}
		catch(Exception e) {
			e.printStackTrace();
		}
		return map;
	}
	
	@PostMapping("/generate-alloc-from-blk-temp")
	public Map<String,Object> generateAllocationFromTemp(@RequestParam("bulkReqId") String bulkReqId,
			@RequestParam("finYear") String finYear,@RequestParam("period_code") String periodCode,HttpServletRequest request) {
		
		
		Map<String,Object> map = new HashMap<String,Object>();
		List<Object[]>lst = null;
		try {
			String ip_address = request.getRemoteAddr();
			this.doctorbulkallocuploadservice
			.pushBulkRecordsToAllocTables(Long.valueOf(bulkReqId), finYear, ip_address,periodCode);
			
			lst = doctorbulkallocuploadservice.getGeneratedRequestList(Long.valueOf(bulkReqId));
			
			map.put("STATUS", "S");
			map.put("msg", "Transaction completed");
			map.put("reqlst",lst);
		}
		catch(MedicoException e) {
			map.put("STATUS", "F");
			map.put("msg", e.getMessage());
		}
		catch(Exception e) {
			e.printStackTrace();
			map.put("STATUS", "F");
			map.put("msg", "Transaction failed !");
		}
		return map;
	}
	
	@PostMapping("/modify-bulk-upload-doctors")
	public Map<String, Object> ModifyDoctorBulkUpload(@RequestBody DoctorBulkUploadBean doctorBulkUploadBean,
			HttpSession session,HttpServletRequest request) {
		Map<String, Object> map = new HashMap<>();
		map.put(DATA_SAVED, true);
		try {
			String companyCode = session.getServletContext().getAttribute(COMPANY_CODE).toString();
			doctorBulkUploadBean.setBlk_ip_add(request.getRemoteAddr());
			doctorBulkUploadBean.setCompany(companyCode);
			map = doctorbulkallocuploadservice.deleteBulkDoctorUploadBeforeModify(doctorBulkUploadBean.getReqIdForModify());
			
			if(map.get("msg").equals("SUCCESS")) {
				map = doctorbulkallocuploadservice.saveBulkDoctorUpload(doctorBulkUploadBean);
				map.put(DATA_SAVED, true);
			}
			
		}
		catch (Exception e) {
			// TODO: handle exception
			map.put(DATA_SAVED, false);
			e.printStackTrace();
		}
		return map;
	}
	
	
	@PostMapping("/modify-alloc-bulk-product")
	public Map<String, Object> ModifyAllocBulkUploadProd(@RequestBody DoctorBulkUploadBean bean,
			HttpSession session,HttpServletRequest request) {
		Map<String, Object> map = new HashMap<>();
		try {	
			String companyCode = session.getServletContext().getAttribute(COMPANY_CODE).toString();
			bean.setBlk_ip_add(request.getRemoteAddr());
			bean.setCompany(companyCode);
			map = doctorbulkallocuploadservice.deleteBulkProductUploadBeforeModify(bean.getReqIdForModify());
			
			if(map.get("msg").equals("SUCCESS")) {
				doctorbulkallocuploadservice.saveBulkDoctorproducts(bean);
				map.put(DATA_SAVED, true);
			}
		}
		catch (Exception e) {
			// TODO: handle exception
			map.put(DATA_SAVED, false);
			e.printStackTrace();
		}
		return map;
	}
	
	@GetMapping("/get-Alloc_req_details-By-Id") 
	public Map<String, Object> GetReqDetailsforModify(@RequestParam("reqId") String reqId,
			HttpSession session,HttpServletRequest request) {
		Map<String, Object> map=new HashedMap<String, Object>();
		try {
			map.put("HdrDetails", doctorbulkallocuploadservice.getObjById(Long.valueOf(reqId))); 
			map.put("DoctorDetails", doctorbulkallocuploadservice.getDoctorDetailsById(Long.valueOf(reqId))); 
			map.put("ProductDetails", doctorbulkallocuploadservice.getProductsDetailsById(Long.valueOf(reqId))); 
		}
		catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return map;
	}
	
	@PostMapping("/temp-self-approval")
	public Map<String, Object> SelfApprovalDoctorBlkAlloc(@RequestParam("bulkReqId") String reqId,
			@RequestParam("EmpId")String empId,
			@RequestParam("userRole")String userRole,
			HttpServletRequest request,HttpSession session) {
			Map<String,Object> map = new HashedMap<String, Object>();
		try {
			doctorbulkallocuploadservice.selfApprovalDoctorUpload(reqId,userRole,empId,request,session);
			map.put(DATA_SAVED,true);
		}
		catch (Exception e) {
		// TODO: handle exception
			e.printStackTrace();
		}
		return map;
	}
	
//	@PostMapping("/temp-self-approvalaa")
//	public Map<String, Object> SelfApprovalDoctorBlkAlloc(@RequestParam("bulkReqId") String reqId,
//			HttpSession session,HttpServletRequest request) {
//			Map<String,Object> map = new HashedMap<String, Object>();
//		try {
//		doctorbulkallocuploadservice.selfApprovalDoctorUpload(reqId);
//		map.put(DATA_SAVED,true);
//		}
//		catch (Exception e) {
//		// TODO: handle exception
//			e.printStackTrace();
//		}
//		return map;
//	}
	
//	@PostMapping("/temp-final-approval")
//	public Map<String, Object> FinalApprovalDoctorBlkAlloc(@RequestParam("bulkReqId") String reqId,
//			@RequestParam("reqno")String reqno,HttpSession session,HttpServletRequest request) {
//			Map<String,Object> map = new HashedMap<String, Object>();
//		try {
//			
//			//doctorbulkallocuploadservice.finalApproval(reqId, last_approved_by, comp_cd, isapproved, motivation);
//			
//			map.put(DATA_SAVED,true);
//		}
//		catch (Exception e) {
//		// TODO: handle exception
//			e.printStackTrace();
//		}
//		return map;
//	}
	
//	@PostMapping("/temp-final-approval")
//	public Map<String, Object> FinalApprovalDoctorBlkAllocs(@RequestParam("bulkReqId") String reqId,
//			@RequestParam("reqno")String reqno,@RequestParam("empId")String empId,
//			HttpSession session,HttpServletRequest request) {
//			Map<String,Object> map = new HashedMap<String, Object>();
//			List<String> ids = new ArrayList<String>();
//		try {
//			TaskQuery taskQuery = taskService.createTaskQuery()
//					.taskAssignee(empId);
//			
//			for (Task task : taskQuery.list()) {
//				ids.add(task.getId());
//			}
//			
//			if (ids.size() > 0) {
//				//System.out.println("ApprovalController.getAdvanceAdjustments() ids1 "+Arrays.asList(ids).toString());
//				List<ActivityApproval> tasks = dashBoardService.getTaskList(ids, IdentityLinkType.ASSIGNEE, "0");
//				System.out.println("Tasks: "+tasks.size()+" "+tasks.get(0).getTran_ref_id());
//			}
			
			
		//	doctorbulkallocuploadservice.finalApprovalDoctorUpload(reqId,reqno);
			
			
			
//			map.put(DATA_SAVED,true);
	//	}
	//	catch (Exception e) {
		// TODO: handle exception
	//		e.printStackTrace();
	//	}
	//	return map;
//	}
	
	
	@PostMapping("/confirmBulkAllocation")
	public Map<String, Object> ConfirmBulkAllocation(@RequestParam("bulkReqId") String reqId,
			@RequestParam("empId")String empId,HttpSession session,HttpServletRequest request) {
		Map<String,Object> map = new HashedMap<String, Object>();
	try {
		doctorbulkallocuploadservice.ConfirmDoctorBlkAlloc(Long.valueOf(reqId), empId, request.getRemoteAddr());
		map.put(DATA_SAVED,true);
	}
	catch (Exception e) {
		// TODO: handle exception
		map.put(DATA_SAVED,false);
		e.printStackTrace();
	}
	return map;
	}
	
	
	@GetMapping("/getAllocReqList")
	public Map<String, Object> getAllocReqList(@RequestParam("divId") String divId,
			HttpSession session,HttpServletRequest request) {
		Map<String, Object> map=new HashedMap<String, Object>();
		try {
			map.put("AllocReqList", doctorbulkallocuploadservice.getRequestListFromBlkHdrForUploaded(Long.valueOf(divId)));
		}
		catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return map;
	}
	
	@GetMapping("/getReqNosByStatus")
	public Map<String, Object> getReqNoByStatus(@RequestParam("status") String status,
			HttpSession session,HttpServletRequest request) {
		Map<String,Object>map = new HashedMap<String, Object>();
		try {
			String comp_code = session.getServletContext().getAttribute(COMPANY_CODE).toString();
			Period period = this.periodMasterService.getPeriodMasterByCompany(comp_code);
		
			map.put("monthOfUse", this.allocationService.getPeriodListGreaterThanPeriodCode(period.getPeriod_id().toString()));
			map.put("ReqNoList", doctorbulkallocuploadservice.getReqNoByStatus(status,A)); 
		}
		catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return map;
	}
	
	@GetMapping("/getPdf")
	public Map<String, Object> getPdf(@RequestParam Long tranRefId){
		Map<String, Object> map = new HashMap<String,Object>();
		try {
			//get uploaded excel name 
			List<Blk_hcq_req_temp> tempRecs = this.doctorbulkallocuploadservice.getBulkTempByBlkHdrId(tranRefId);
			if(tempRecs!=null && tempRecs.size()>0) {
				String fileName = doctorbulkallocuploadservice.doctorBulkUploadExcelToPdf(tempRecs.get(0).getBlk_csv_name());
				map.put("fileName", fileName);
				map.put(DATA_SAVED, "Y");
			}
		}
		catch(Exception e) {
			e.printStackTrace();
			map.put(DATA_SAVED, "N");
		}
		return map;
	}
}
