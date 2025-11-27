package com.excel.restcontroller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.lang.Nullable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.excel.bean.CRMExpenseBean;
import com.excel.model.CRMReqRequestsImage;
import com.excel.model.CRM_Req_Request;
import com.excel.model.CrmSchemeMaster;
import com.excel.model.CustomerMaster;
import com.excel.model.DivMaster;
import com.excel.model.Location;
import com.excel.model.Period;
import com.excel.model.SG_d_parameters_details;
import com.excel.repository.CustomerMasterRepo;
import com.excel.service.CrmSchemeService;
import com.excel.service.DivisionMasterService;
import com.excel.service.DoctorMasterService;
import com.excel.service.LocationService;
import com.excel.service.ParameterService;
import com.excel.service.PeriodMasterService;
import com.excel.utility.MedicoConstants;

@RestController
@RequestMapping("/rest")
public class CrmRequestController implements MedicoConstants{
	
	@Autowired private ParameterService paramService;
	@Autowired private PeriodMasterService perMastServ;
	@Autowired private LocationService locationService;
	@Autowired private DivisionMasterService divisionService;
	@Autowired private CustomerMasterRepo custMasterRepo;
	@Autowired private DoctorMasterService docMastService;
	@Autowired private CrmSchemeService crmSchemeService;
	
	@GetMapping("/getCrmEntryOnloadData")
	public Map<String, Object> getOnLoadElementsForCrmEntry(HttpSession session,@RequestParam ("finYear") String finYear) {
		Map<String, Object> map = new HashMap<>();
		try {
			List<Period>  monthList = perMastServ.getAllPeriodsByFinYear(finYear);
			List<SG_d_parameters_details> scheme_type = this.paramService.getParaDetailsByParaType(SCHEME_TYPE);
			List<SG_d_parameters_details> docclass = this.paramService.getParaDetailsByParaType(DOCTOR_CLASS);
			List<Location> loc_list = this.locationService.getAllLocations();
			List<DivMaster> div_list = this.divisionService.getAllStandardDivisionList();
//			List<Parameters> docspe = parameters.stream().filter(a -> a.getPara_type().equalsIgnoreCase(SPE))
//					.collect(Collectors.toList());
			map.put("monthlist", monthList);
			map.put("scheme_type", scheme_type);
			map.put("docclass", docclass);
			map.put("locationsList", loc_list);
			map.put("divisionsList", div_list);
//			map.put("docspe", docspe);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return map;
	}
	
	@GetMapping("/get-customer-list-by-cust-type")
	public Map<String,Object> getCustListByCustType(
			@RequestParam ("cust_type") String custType,
			@Nullable @RequestParam("loc_id") Long loc_id,
			@RequestParam ("doc_spl") String doc_spl,
			@RequestParam ("doc_class") String doc_class
			){
		List<CustomerMaster> custMaster = null;
		Map<String,Object> map = new HashMap<String,Object>();
		try {
			if(custType.equalsIgnoreCase("W")) {
				custMaster = this.custMasterRepo.getCustomersForLocation(loc_id);
			}
			else if(custType.equalsIgnoreCase("D")) {
				custMaster = this.docMastService.getDoctorsFromMdmBySpecialityAndClass(doc_class.trim(), doc_spl.trim());
			}
			map.put("custMaster", custMaster);
			map.put("STATUS", "Y");
		}
		catch(Exception e) {
			map.put("STATUS", "N");
			e.printStackTrace();
		}
		return map;
	}
	
	
	@GetMapping("/getCRMSchemeDetails")
	public Map<String, Object> getCRMSchemeDetails(HttpSession session,
			@RequestParam("custType") String crm_sch_type ,
			@RequestParam("curBiz") String curBiz,
			@RequestParam("expBiz") String expBiz,
			@RequestParam("locId") Long locId,
			@RequestParam("docspl") String docspl,
			@RequestParam("doccls") String doccls){
		Map<String, Object> map=new HashMap<>();
	List<CrmSchemeMaster> list=null;
	try {
		String companycode=session.getServletContext().getAttribute(COMPANY_CODE).toString();
		list=crmSchemeService.getCRMSchemeDetails(companycode,crm_sch_type,curBiz,expBiz,locId,doccls,docspl);
		
		System.out.println("SchemeDetails :: "+list.size() );
		if (list.size() > 0 && list != null && !list.isEmpty()) {
			map.put("SchemeDetails",list);
			map.put(RESPONSE_MESSAGE, true);
		}
		}catch(Exception e) {
			e.printStackTrace();
			map.put(RESPONSE_MESSAGE, false);
		}
		
	return map;
	}
	
	
	@PostMapping("/confirmCRMImages")
	public Map<String, Object> saveImagesCrm(@RequestParam List<MultipartFile> files, HttpSession session) {
		Map<String, Object> map = new HashMap<String, Object>();
		Map<String, Object> fileobjmap = new HashMap<String, Object>();
		try {
			fileobjmap = crmSchemeService.uploadCrmEntryImage(files);

			for (Entry<String, Object> entry : fileobjmap.entrySet()) {
				System.out.println("Key = " + entry.getKey() + ", Value = " + entry.getValue());
			}

			map.put("FileMap", fileobjmap);
			map.put("msg", MedicoConstants.DATA_SAVED);
		} catch (Exception e) {
			e.printStackTrace();
			map.put("msg", e.getMessage());
		}
		return map;
	}
	
	
	@PostMapping("/saveCrmExpenseRequest")
	public Map<String, Object> saveCrmExpenseRequest(@RequestBody CRMExpenseBean crm, HttpSession session) {
		CRM_Req_Request hdr = null;
		String msg = "";
		Map<String, Object> map = new HashMap<String, Object>();
		try {
			String companycode = session.getServletContext().getAttribute(COMPANY_CODE).toString();
			crm.setCompCode(companycode);
			hdr = crmSchemeService.save(crm);
			msg = "Transaction Saved Sucessfully with transaction No: " + hdr.getCrm_req_no();
			map.put("msg", msg);
			map.put("crmReqId", hdr.getCrm_req_id());

			map.put(RESPONSE_MESSAGE, DATA_SAVED);
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			map.put(RESPONSE_MESSAGE, false);

		}
		return map;
	}
	
	
	@GetMapping("/getCRMViewDetails")
	public Map<String, Object> getCRMViewDetails(HttpSession session, @RequestParam("userid") String userid) {
		Map<String, Object> map = new HashMap<>();
		List<CRM_Req_Request> list = null;
		try {
			String companycode = session.getServletContext().getAttribute(COMPANY_CODE).toString();
			list = crmSchemeService.getviewlist(userid);
			//System.out.println("viewDetails :: " + list.size());
			if ( list != null && list.size() > 0 && !list.isEmpty()) {
				map.put("viewDetails", list);
				map.put(RESPONSE_MESSAGE, true);
			}
		} catch (Exception e) {
			e.printStackTrace();
			map.put(RESPONSE_MESSAGE, false);
		}
		return map;
	}
	
	@RequestMapping("/crmConfirmation")
	public Map<String, Object> crmConfirmation(@RequestParam("crm_req_id") List<Long> crm_req_id,
			@RequestParam("userid") String userid, @RequestParam("finYearId") String finYearId,
			HttpSession session) {
		Map<String, Object> map = new HashMap<>();
		try {
			String companycode = session.getServletContext().getAttribute(COMPANY_CODE).toString();
			map = crmSchemeService.crmConfirmation(crm_req_id, finYearId, userid, companycode);
			map.put("viewDetails", crmSchemeService.getviewlist(userid));
		} catch (Exception e) {
			e.printStackTrace();
		}
		return map;
	}
	
	
	@GetMapping("/getmodifyDetailsByid")
	public Map<String, Object> getmodifyDetailsByid(HttpSession session,
			@RequestParam("crm_req_id") String crm_req_id) {
		Map<String, Object> map = new HashMap<>();
		CRM_Req_Request list = null;
		try {
			String companycode = session.getServletContext().getAttribute(COMPANY_CODE).toString();
			list = crmSchemeService.getmodifyDetailsByid(crm_req_id);
			System.out.println("requestDate :: " + list.getCrm_req_date() + "  ScheduleDate :: " + list.getCrm_req_date());
			map.put("modDetails", list);
			map.put(RESPONSE_MESSAGE, true);

		} catch (Exception e) {
			e.printStackTrace();
			map.put(RESPONSE_MESSAGE, false);
		}

		return map;
	}

	@GetMapping("/getReqImagesBycrmId")
	public Map<String, Object> ImagesBycrmId(@RequestParam("crm_req_id") String crmId, HttpSession session) {
		List<CRMReqRequestsImage> list = new ArrayList<CRMReqRequestsImage>();
		Map<String, Object> map = new HashMap<>();
		try {
			list = crmSchemeService.geImageBycrmId(Long.valueOf(crmId));
			System.out.println("Imagelist :: " + list.size());

			map.put("Imagelist", list);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return map;
	}
	
	

}
