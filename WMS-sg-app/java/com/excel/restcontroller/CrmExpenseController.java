package com.excel.restcontroller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.excel.bean.CrmErrorBean;
import com.excel.bean.CrmTdsBean;
import com.excel.exception.MedicoException;
import com.excel.model.Company;
import com.excel.model.Crm_GenHd;
import com.excel.model.SG_d_parameters_details;
import com.excel.model.Sfa_Field_Master;
import com.excel.model.Sfa_Geographical_Master;
import com.excel.model.Sfa_Retail_Consumer;
import com.excel.model.TdsApplicableProduct;
import com.excel.model.TdsApplicableStatementReport;
import com.excel.model.Tds_Crm_Expense;
import com.excel.model.Tds_crm_Images;
import com.excel.service.CRM_Download_excel_service;
import com.excel.service.CompanyMasterService;
import com.excel.service.CrmExpense_Service;
import com.excel.service.FinancialYearService;
import com.excel.utility.CodifyErrors;
import com.excel.utility.MedicoConstants;

@RestController
@RequestMapping("/rest")
public class CrmExpenseController implements MedicoConstants {

	@Autowired
	CrmExpense_Service crmexpenseservice;

	@Autowired
	CRM_Download_excel_service crm_download_excel_service;

	@Autowired
	CompanyMasterService companyMasterService;

	@Autowired
	FinancialYearService financialyearservice;

	@GetMapping("/getOnLoadElements")
	public Map<String, Object> getOnLoadElements(HttpSession session) {
		Map<String, Object> map = new HashMap<>();
		List<Sfa_Geographical_Master> hqlist = null;
		List<SG_d_parameters_details> list = null;
		try {
			hqlist = crmexpenseservice.gethq();
			String companycode = session.getServletContext().getAttribute(COMPANY_CODE).toString();
			list = crmexpenseservice.getTdsCrmPercentageParameter();
			for (SG_d_parameters_details s : list) {
				if (s.getSgprmdet_nm().trim().equals("01")) {
					map.put("withPanTax", s.getSgprmdet_num1());
				} else if (s.getSgprmdet_nm().trim().equals("02")) {
					map.put("withoutPanTax", s.getSgprmdet_num1());
				}
			}
			System.out.println("Company Code :" + companycode);
			String finyear = financialyearservice.getFinYearForTDSVet(companycode).getFin_year();
			map.put("HqList", hqlist);
			map.put("Finyear", finyear);
		} catch (Exception e) {
			e.printStackTrace();
			e.printStackTrace();
			System.out.println("Error Occurred :" +e.getMessage());// uncomment asneeded --;
		}
		return map;
	}

	@GetMapping("/getSe")
	public Map<String, Object> getSeLists(@RequestParam("LocId") Long geog_lvl1_hq) {
		Map<String, Object> map = new HashMap<>();
		List<Sfa_Field_Master> selist = null;
		try {
			System.out.println("geog_lvl1_hq" + geog_lvl1_hq);
			selist = crmexpenseservice.getse(geog_lvl1_hq);
			System.out.println(selist.size());
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("Error Occurred :" +e.getMessage());// uncomment asneeded --;
		}
		map.put("SeList", selist);
		return map;
	}

	@GetMapping("/getAddressandContact")
	public Map<String, Object> getAddress(@RequestParam("se") Long se, @RequestParam("custType") String custtype) {
		Map<String, Object> map = new HashMap<>();
		List<Sfa_Retail_Consumer> custAdd = null;
		try {
			System.out.println("Address Param:::" + se + " :: " + custtype);
			custAdd = crmexpenseservice.getAddress(se, custtype);
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("Error Occurred :" +e.getMessage());// uncomment asneeded --;
		}
		map.put("AddressList", custAdd);
		return map;
	}

	@PostMapping("/onSubmitCrm")
	public Map<String, Object> saveCrm(@RequestBody CrmTdsBean tdsexp, HttpSession session) {
		String msg = null;
		Map<String, Object> map = new HashMap<String, Object>();
		try {
			System.out.println("ImageMap:  " + tdsexp.getImagefiles().size());
			for (Entry<String, Object> entry : tdsexp.getImagefiles().entrySet()) {
				System.out.println("MapKey = " + entry.getKey() + ", MapValue = " + entry.getValue());
			}

			// tdsexp = new Tds_Crm_Expense();
			msg = crmexpenseservice.SaveCRMEntry(tdsexp);
			map.put("msg", msg);
//			System.out.println("custname : "+tdsexp.getCustname());
//			System.out.println("custname : "+tdsexp.getTdspaid());
		} catch (Exception e) {
			// TODO: handle exception
			map.put("msg", "Error Occurred !! Error Code : " +e.getMessage());
			e.printStackTrace();
			System.out.println("Error Occurred :" +e.getMessage());// uncomment asneeded --;
		}
		return map;
	}

	@PostMapping("/onSubmitCrmImages")
	public Map<String, Object> saveImagesCrm(@RequestParam List<MultipartFile> files, HttpSession session) {
		Map<String, Object> map = new HashMap<String, Object>();
		Map<String, Object> fileobjmap = new HashMap<String, Object>();
		try {
			fileobjmap = crmexpenseservice.uploadCrmEntryImage(files);

			for (Entry<String, Object> entry : fileobjmap.entrySet()) {
				System.out.println("Key = " + entry.getKey() + ", Value = " + entry.getValue());
			}

			map.put("FileMap", fileobjmap);
			map.put("msg", MedicoConstants.DATA_SAVED);
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			System.out.println("Error Occurred :" +e.getMessage());// uncomment asneeded --;
			map.put("msg", "Error Occurred :" +e.getMessage());
		}
		return map;
	}

	@GetMapping("/viewTdscrm")
	public Map<String, Object> viewtdscrm(@RequestParam("empId") String empId) {
		Map<String, Object> map = new HashMap<>();
		try {
			List<Tds_Crm_Expense> list = crmexpenseservice.getviewByEmpId(empId);
			map.put("viewlist", list);
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			System.out.println("Error Occurred :" +e.getMessage());// uncomment asneeded --;
		}
		return map;
	}

	@PostMapping("/updateCrm")
	public Map<String, Object> updateCrm(@RequestBody CrmTdsBean tdsexp, HttpSession session) {
		Map<String, Object> map = new HashMap<>();
		String msg = "";
		try {
			msg = crmexpenseservice.UpdateCRM(tdsexp);
			map.put("msg", msg);
		} catch (Exception e) {
			// TODO: handle exception
			map.put("msg", "Error Occurred !! Error Code : " +e.getMessage());
			e.printStackTrace();
			System.out.println("Error Occurred :" +e.getMessage());// uncomment asneeded --;
		}
		return map;
	}

	@PostMapping("/confirmCrm")
	public Map<String, Object> confirmCrm(@RequestBody String ids, HttpSession session) {
		Map<String, Object> map = new HashMap<>();
		String msg = null;
		try {
			msg = crmexpenseservice.confirmCrm(ids);
			map.put("msg", msg);
		} catch (Exception e) {
			e.printStackTrace();
			map.put("msg", "Error Occurred !! Error Code : " +e.getMessage());
			System.out.println("Error Occurred :" +e.getMessage());// uncomment asneeded --;
		}
		return map;
	}

	@GetMapping("/getImagesBycrmId")
	public Map<String, Object> ImagesBycrmId(@RequestParam String crmId, HttpSession session) {
		List<Tds_crm_Images> list = new ArrayList<Tds_crm_Images>();
		Map<String, Object> map = new HashMap<>();
		try {
			list = crmexpenseservice.geImageBycrmId(Long.valueOf(crmId));
			map.put("Imagelist", list);
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			System.out.println("Error Occurred :" +e.getMessage());// uncomment asneeded --;
		}
		return map;
	}

	@GetMapping("/TdsSummaryReport")
	public Map<String, Object> getTdsSummaryReport(@RequestParam("sub_comp") String sub_comp,
			@RequestParam("custtype") String custType, @RequestParam("tds") Character tdsReq,
			@RequestParam(value = "Pan_req", required = false) String Pan_req, HttpSession session) {

		Map<String, Object> map = new HashMap<>();
		List<TdsApplicableStatementReport> list = null;
		String companycode = session.getServletContext().getAttribute(COMPANY_CODE).toString();
		String finyear = null;
		System.out.println("Pan_req: " + Pan_req);
		try {
			list = crmexpenseservice.getTdsSummaryReport(sub_comp, custType, tdsReq, Pan_req);
			finyear = financialyearservice.getFinYearForTDSVet(companycode).getFin_year();
			if (list!=null && list.size() > 0) {
				map = crm_download_excel_service.generateTdsSummaryReport(list, session);
			}
			map.put("Finyear", finyear);
			map.put("Data", list);
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("Error Occurred :" +e.getMessage());// uncomment asneeded --;
		}

		return map;
	}

	@PostMapping("/getDetailReport")
	public Map<String, Object> getTdsDetailReport(@RequestBody TdsApplicableStatementReport bean,
			@RequestParam("EMP_ID") String emp_id, HttpSession session) {
		Map<String, Object> map = new HashMap<>();
		List<TdsApplicableProduct> list = null;
		String filename = null;
		try {
			System.out.println("EMP_ID:: " + emp_id);
			list = crmexpenseservice.getTdsDetail(bean.getUnique_hcp_id(), emp_id, session);
			System.out.println("List Size:: " + list.size());
			if (list.size() > 0) {
				filename = crm_download_excel_service.generateTdsDetailReport(list, bean, session);
			}
			System.out.println(filename);
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("Error Occurred :" +e.getMessage());// uncomment asneeded --;
		}
		map.put("filename1", filename);
		map.put("detailList", list);
		return map;
	}

	@GetMapping("/getSubCompid")
	public Map<String, Object> getSubCompDetail(@RequestParam("emp_Id") String emp_Id) {
		Map<String, Object> map = new HashMap<>();
		Long subcomp_cd = 0L;
		String id = null;
		try {
			subcomp_cd = companyMasterService.getSubCompanyList(emp_Id).get(0).getSubcomp_id();
			id = "0" + (subcomp_cd.toString());
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("Error Occurred :" +e.getMessage());// uncomment asneeded --;
		}
		map.put("subid", id);

		return map;
	}

	@GetMapping("/getSeBulkUpload")
	public Map<String, Object> getSeLists(@RequestParam("LocIds") String geog_lvl1_hq) {
		Map<String, Object> map = new HashMap<>();
		List<Sfa_Field_Master> selist = null;
		try {
			System.out.println("geog_lvl1_hq" + geog_lvl1_hq);
			selist = crmexpenseservice.getBulkse(geog_lvl1_hq);
			// System.out.println(selist.size());
			map.put("SeList", selist);
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("Error Occurred :" +e.getMessage());// uncomment asneeded --;
		}

		return map;
	}

	@GetMapping("/getBulkAddressandContact")
	public Map<String, Object> getBulkcustomers(@RequestParam("se") String se,
			@RequestParam("custType") String custtype) {
		Map<String, Object> map = new HashMap<>();
		List<Sfa_Retail_Consumer> custAdd = null;
		try {
			System.out.println("Address Param:::" + se + " " + custtype);
			custAdd = crmexpenseservice.getBulkAddress(se, custtype);
			map.put("AddressList", custAdd);
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("Error Occurred :" +e.getMessage());// uncomment asneeded --;
		}
		return map;
	}

	@GetMapping("/DownloadCrmUploadTemplate")
	public Map<String, Object> getDownloadCrmUploadTemplate(@RequestParam("se") String se,
			@RequestParam("custids") String custids, @RequestParam("custType") String custtype,
			@RequestParam("empid") String empid) {
		Map<String, Object> map = new HashMap<>();
		List<Sfa_Retail_Consumer> custAdd = new ArrayList<Sfa_Retail_Consumer>();
		String filename = null;
		try {
			custAdd = crmexpenseservice.getDataForCrmTemplate(custids, se, custtype);
			if (custAdd != null && custAdd.size() > 0) {
				filename = crmexpenseservice.GenerateCrmTemplateUpload(custAdd, custtype);
				crmexpenseservice.writeCrmDownloadFileLog(custAdd, custtype, empid, filename);
			}
			map.put("filename", SPRING_RESOURCE_HANDLER_REPORT_FILE_PATH_PREFIX + "/" + filename);
		} catch (Exception e) {
			e.printStackTrace();
			e.printStackTrace();
			System.out.println("Error Occurred :" +e.getMessage());// uncomment asneeded --;
		}
		return map;
	}

	@PostMapping("/crm-upload")
	public Map<String, Object> uploadCrm(@RequestParam MultipartFile file, @RequestParam String finyr,
			@RequestParam String username, HttpServletRequest request, HttpSession session) {
		Map<String, Object> map = new HashMap<>();
		String msg = null;
		try {
			Company company = (Company) session.getServletContext().getAttribute(COMPANY_DATA);
			System.out.println("file " + file.getOriginalFilename());
			msg = crmexpenseservice.uploadCrmFile(file, finyr, company.getCompany(), username);
			map.put("STATUS", msg);
		} catch (Exception e) {
			e.printStackTrace();
			map.put("STATUS", msg);
			e.printStackTrace();
			System.out.println("Error Occurred :" +e.getMessage());// uncomment asneeded --;
		}
		return map;
	}

	@GetMapping("/get-crm-doc-list-for-user")
	public Map<String, Object> getCrmHdrListByUserId(@RequestParam String userId) {
		Map<String, Object> map = new HashMap<>();
		List<Crm_GenHd> list = null;
		try {
			list = crmexpenseservice.getCrmHdrByUserId(userId);
			if (list != null && list.size() > 0) {
				map.put("crmGenHdList", list);
			}
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("Error Occurred :" +e.getMessage());// uncomment asneeded --;
		}
		return map;
	}

	@GetMapping("/get-tds-exp-crm-dtl")
	public Map<String, Object> getTdsExpsCrmDtlsByGenId(@RequestParam Long crmGenId) {
		Map<String, Object> map = new HashMap<>();
		List<Tds_Crm_Expense> list = null;
		try {
			list = crmexpenseservice.getTdsExpsByCrmGenId(crmGenId);
			if (list != null && list.size() > 0) {
				map.put("TdsExpsDtlList", list);
			}
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("Error Occurred :" +e.getMessage());// uncomment asneeded --;
		}
		return map;
	}

	@PostMapping("/confirm-uploaded-tds-crm-expense")
	public Map<String, Object> confirmUploadedTdsCrmExpenses(@RequestBody List<Long> genDtlIdsList,
			@RequestParam Long crmGenId) {
		Map<String, Object> map = new HashMap<>();
		try {
			this.crmexpenseservice.confirmTdsCrmExpensesByGenHdrAndDtl(crmGenId, genDtlIdsList);
			map.put("msg", "SAVED");
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("Error Occurred :" +e.getMessage());// uncomment asneeded --;
			map.put("msg", "FAILED");
		}
		return map;
	}

	@PostMapping("/direct-crm-upload")
	public Map<String, Object> directuploadCrm(@RequestParam MultipartFile file, @RequestParam String finyr,
			@RequestParam String username, HttpServletRequest request, HttpSession session) {
		Map<String, Object> map = new HashMap<>();
		String msg = "FAILED";

		List<CrmTdsBean> beanlist = new ArrayList<CrmTdsBean>();// bean to set file values
		boolean isok = false; // update flag if file is validated correctly
		List<CrmErrorBean> errorbeanlist = null; // use list to return list of errors in file to UI.
		try {
			Company company = (Company) session.getServletContext().getAttribute(COMPANY_DATA);
			System.out.println("file " + file.getOriginalFilename());
			// Validation of All File
			map = crmexpenseservice.validateAndSetBeanForSave(file, finyr, company.getCompany(), username);
			isok = (boolean) map.get("isok");
			// Save File
			if (isok) {
				// save
				beanlist = (List<CrmTdsBean>) map.get("tdslist");
				map = crmexpenseservice.SaveDirectUpload(beanlist, file.getOriginalFilename());
				// map.put("STATUS", msg);
			} else {
				errorbeanlist = (List<CrmErrorBean>) map.get("errorlist");
				map.put("STATUS", "Uploaded File Has some Errors,Please Resolve and Reupload");
				map.put("errorList", errorbeanlist);
			}
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			map.put("STATUS", msg);
			e.printStackTrace();
			System.out.println("Error Occurred :" +e.getMessage());// uncomment asneeded --;
		}

		return map;
	}

}
