package com.excel.restcontroller;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.excel.bean.IAABean;
import com.excel.bean.StockTransferBean;
import com.excel.model.Company;
import com.excel.model.EmailFrom;
import com.excel.model.IaaDetailForMail;
import com.excel.model.Iaa_Map;
import com.excel.model.Location;
import com.excel.model.Period;
import com.excel.model.Stock_Adj_Header;
import com.excel.service.EmailService;
import com.excel.service.IAAService;
import com.excel.service.LocationService;
import com.excel.service.ParameterService;
import com.excel.service.PeriodMasterService;
import com.excel.service.UserMasterService;
import com.excel.utility.CodifyErrors;
import com.excel.utility.MedicoConstants;
import com.excel.utility.MedicoResources;
import com.excel.utility.SendMail;

@RestController
@RequestMapping("/rest")
public class IAARestController implements MedicoConstants {

	@Autowired IAAService iAAService;
	@Autowired LocationService locationService;
	@Autowired PeriodMasterService periodMasterService;
	@Autowired ParameterService parameterService;
	@Autowired EmailService emailService;
	@Autowired private UserMasterService userMasterService;
	
	@GetMapping("/get-data-for-iaa-entry")
	public Map<String, Object> getDataForIaaEntry(HttpServletRequest request ,@RequestParam String trantype,
			HttpSession session) {
		Map<String, Object> map = new HashMap<>();
		try {
			
			String empId = this.userMasterService.getEmployeeIdFromRequest(request);
			Location location = this.locationService.getLocationNameByEmployeeId(empId);
			String companyCode = session.getServletContext().getAttribute(COMPANY_CODE).toString();
			Period period = this.periodMasterService.getPeriodMasterByCompany(companyCode);
			SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
			Date today = new Date();
			Date taskDate = MedicoResources.convert_YYYY_MM_DD_HH_MM_SS_toDate(period.getPeriod_end_date());
			String trnDate="";
			Long receipt_datetime=0l;
			if (today.compareTo(taskDate) > 0) {
				trnDate=dateFormat.format(taskDate);
				receipt_datetime = taskDate.getTime();
			} else {
				trnDate=dateFormat.format(today);
				receipt_datetime = today.getTime();
			}
			List<Iaa_Map> reasonList=this.iAAService.getReasonsFromIaaMap();
		//	List<Object> prodList=iAAService.getProductListWithPack(companyCode,location.getLoc_subcomp_id(), "", "", 0l, 0l);
			map.put("sendLocName", location.getLoc_nm());
			map.put("sendSubCompId", location.getLoc_subcomp_id());
			map.put("sendLocId", location.getLoc_id());
			map.put("trnDate", trnDate);
			map.put("receipt_datetime", receipt_datetime);
			map.put("reasonList", reasonList);
			String emailReqInd = this.parameterService.getSrtAndEmailReqInd("TRANWISE_EMAIL_REQD");
			map.put("EmailReqInd",emailReqInd);
			map.put("DefaultMailList", this.parameterService.getDefaultParameterEmailsByTranType(trantype));
		//	map.put("prodList", prodList);
		} catch (Exception e) {
			System.out.println("Error Occurred :" +e.getMessage());// uncomment asneeded --;
		}
		return map;
	}
	
	@GetMapping("/get-reason-details-by-reason-id")
	public Map<String, Object> getReasonDetailsByReasonId(@RequestParam String reasonId) {
		Map<String, Object> map = new HashMap<>();
		try {
			Object iaa_map = iAAService.getIaaDetailById(Long.valueOf(reasonId));
			Object[] obj = (Object[]) iaa_map;
			IAABean bean=new IAABean();
		    bean.setIn_item_accept(((Boolean)obj[0] == false ? "0" : "1")); 
		    bean.setIn_batch_accept(((Boolean)obj[1]== false ? "0" : "1"));
		    bean.setOut_stock_type((String)obj[2]);
		    bean.setIn_stock_type((String)obj[3]);
		    bean.setOut_tran_type((String)obj[4]);
		    bean.setIn_tran_type((String)obj[5]);
		    bean.setNew_batch_accept(((Boolean)obj[6] == false ? "0" : "1"));
		    bean.setIn_qty_accept(((Boolean)obj[7] == false ? "0" : "1"));
		    bean.setIn_rate_accept(((Boolean)obj[8] == false ? "0" : "1"));
		    bean.setItem_change_accept(((Boolean)obj[9] == false ? "0" : "1"));
		    bean.setInstk((String)obj[14]);
		    bean.setOutstk((String)obj[15]);
		    map.put("reasonData", bean);
		} catch (Exception e) {
			System.out.println("Error Occurred :" +e.getMessage());// uncomment asneeded --;
		}
		return map;
	}
	
	@GetMapping("/get-prod-by-prefix")
	public Map<String, Object> getProdByPrefix(HttpSession session,@RequestParam String prefix,@RequestParam String subCompId) {
		Map<String, Object> map = new HashMap<>();
		try {
			String companyCode = session.getServletContext().getAttribute(COMPANY_CODE).toString();
			//List<Object> prodList=iAAService.getProductListWithPack(companyCode,Long.valueOf(subCompId), prefix, "", 0l, 0l);
			List<Object> prodList=iAAService.getProductListWithPack(companyCode,0l, prefix, "", 0l, 0l);

			 map.put("prodList", prodList);
		} catch (Exception e) {
			System.out.println("Error Occurred :" +e.getMessage());// uncomment asneeded --;
		}
		return map;
	}
	
	@GetMapping("/get-item-batch-qty-val")
	public Map<String, Object> getItemBatchQtyVal(@RequestParam String item_id,@RequestParam String batch_id,
			@RequestParam String loc_id,@RequestParam String flag,@RequestParam String stock_type) {
		Map<String, Object> map = new HashMap<>();
		 Object val = null;
		try {
			System.out.println("stock_type : "+stock_type+" itemId : "+item_id+" flag : "+flag+" batchId : "+batch_id+" locId : "+loc_id);
			if(stock_type.equalsIgnoreCase("QRA")) {
			//	val=iAAService.getQtyAndValueNS(Long.valueOf(item_id), flag, Long.valueOf(loc_id), Long.valueOf(batch_id), QUARANTINE);
				val=iAAService.getQuarantineQtyAndValue(Long.valueOf(item_id), flag, Long.valueOf(loc_id), Long.valueOf(batch_id));
			} else if (stock_type.equalsIgnoreCase(SALEABLE) || stock_type.equalsIgnoreCase(GRN_EXCESS)) {
				 val=iAAService.getQtyAndValue(Long.valueOf(item_id), flag, Long.valueOf(loc_id), Long.valueOf(batch_id));
			 } else if (stock_type.equalsIgnoreCase(QUARANTINE)) {
				 val=iAAService.getQuarantineQtyAndValue(Long.valueOf(item_id), flag, Long.valueOf(loc_id), Long.valueOf(batch_id));
			 } else {
				 val=iAAService.getQtyAndValueNS(Long.valueOf(item_id), flag, Long.valueOf(loc_id), Long.valueOf(batch_id), stock_type);
			}
			 map.put("result_data", val);
		} catch (Exception e) {
			System.out.println("Error Occurred :" +e.getMessage());// uncomment asneeded --;
		}
		return map;
	}
	
	@GetMapping("/get-batch")
	public Map<String, Object> getBatchList(@RequestParam String item_id,@RequestParam String loc_id,
			@RequestParam String stock_type,@RequestParam String stock_type2,@RequestParam String status) {
		Map<String, Object> map = new HashMap<>();
		 List<Object> list=null;
		try {
			list=iAAService.getBatchListForIAA(Long.valueOf(loc_id), Long.valueOf(item_id), stock_type,stock_type2, status);
			 if (list != null && list.size() > 0) { 
				 map.put("isData", true);
			 } else {
				 map.put("isData", false);
			 }
			map.put("list", list);
		} catch (Exception e) {
			System.out.println("Error Occurred :" +e.getMessage());// uncomment asneeded --;
		}
		return map;
	}
	
	@PostMapping("/save-iaa-entry")
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public Map<String, Object> saveIAA(@RequestBody IAABean bean, HttpSession session, HttpServletRequest request) {
		Map<String, Object> iaaMap = new HashMap<>();
		try {
			String empId = this.userMasterService.getEmployeeIdFromRequest(request);
			bean.setEmpId(empId);
			String companyCode = session.getServletContext().getAttribute(COMPANY_CODE).toString();
			bean.setIpAddress(request.getRemoteAddr());
			bean.setCompanyCode(companyCode);
			iaaMap=this.iAAService.saveIaaEntry(bean);
			iaaMap.put(DATA_SAVED, true);
			iaaMap.put(RESPONSE_MESSAGE, "Stock Adjustment Saved Successfully.");
			System.out.println("done----------");
		} catch (Exception e) {
			iaaMap.put(DATA_SAVED, false);
			iaaMap.put(RESPONSE_MESSAGE, "Error Occurred !! Error Code : "+new CodifyErrors().getErrorCode(e));
			e.printStackTrace();
			System.out.println("Error Occurred :" +e.getMessage());// uncomment asneeded --;
		} finally {
			
		}
		return iaaMap;
	}

	@GetMapping("/get-saved-prod-list-for-iaa")
	public Map<String, Object> getSavedProdDetails(@RequestParam String headerId) {
		Map<String, Object> map = new HashMap<>();
		try {
			List<Object> list=iAAService.getStockAdjDetailsByHeaderId(Long.valueOf(headerId));
			map.put("savedProdList", list);
		} catch (Exception e) {
			System.out.println("Error Occurred :" +e.getMessage());// uncomment asneeded --;
		}
		return map;
	}
	
	@GetMapping("/get-iaa-header-data")
	public Map<String, Object> getIAAheaderDataForAppr( 	HttpServletRequest request,HttpSession session,@RequestParam String finYr,@RequestParam String periodCode) {
		Map<String, Object> map = new HashMap<>();
		try {
			
			String empId = this.userMasterService.getEmployeeIdFromRequest(request);
			String companyCode = session.getServletContext().getAttribute(COMPANY_CODE).toString();
			List<Object> list=this.iAAService.getIAAHeaderList(companyCode, Long.valueOf(finYr), periodCode,empId);
			map.put("headerList", list);
		} catch (Exception e) {
			System.out.println("Error Occurred :" +e.getMessage());// uncomment asneeded --;
		}
		return map;
	}
	
	@GetMapping("/get-iaa-detail-data")
	public Map<String, Object> getIAAdetailDataForAppr(@RequestParam String stkAdjHeaderId,@RequestParam String status) {
		Map<String, Object> map = new HashMap<>();
		try {
			List<Object> list=this.iAAService.getStockAdjDtlByHdrForApproval(Long.valueOf(stkAdjHeaderId), 0, status);
			Stock_Adj_Header header = this.iAAService.getObjectById(Long.valueOf(stkAdjHeaderId));
			map.put("detailList", list);
			map.put("file", header.getIaa_img());
		} catch (Exception e) {
			System.out.println("Error Occurred :" +e.getMessage());// uncomment asneeded --;
		}
		return map;
	}
	
	@PostMapping("/iaa-self-approval")
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public Map<String, Object> iaaSelfApprove(@RequestBody IAABean bean, HttpSession session, HttpServletRequest request) {
		Map<String, Object> iaaMap = new HashMap<>();
		try {
			
			String empId = this.userMasterService.getEmployeeIdFromRequest(request);
			bean.setEmpId(empId);
			String companyCode = session.getServletContext().getAttribute(COMPANY_CODE).toString();
			bean.setIpAddress(request.getRemoteAddr());
			bean.setCompanyCode(companyCode);
			this.iAAService.iaaSelfApproval(bean);
			iaaMap.put(DATA_SAVED, true);
			if(bean.getStatus().equalsIgnoreCase("F")) {
				iaaMap.put(RESPONSE_MESSAGE, "IAA forwarded Successfully.");
			} else {
				iaaMap.put(RESPONSE_MESSAGE, "IAA discarded Successfully.");
			}
		} catch (Exception e) {
			iaaMap.put(DATA_SAVED, false);
			iaaMap.put(RESPONSE_MESSAGE, "Error Occurred !! Error Code : "+new CodifyErrors().getErrorCode(e));
			System.out.println("Error Occurred :" +e.getMessage());// uncomment asneeded --;
		} finally {
			
		}
		return iaaMap;
	}
	
	@GetMapping("/iaa-final-approval")
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public Map<String, Object> iaaFinalApprove(HttpSession session, HttpServletRequest request,@RequestParam String stkAdjHeaderId,
			@RequestParam List<String> adjDtlStatus,@RequestParam List<String> adjDtlIds) {
		Map<String, Object> iaaMap = new HashMap<>();
		IAABean bean=null;
		System.out.println("stkAdjHeaderId::"+stkAdjHeaderId);
		System.out.println("adjDtlStatus::"+adjDtlStatus);
		System.out.println("adjDtlIds::"+adjDtlIds);

		try {
			
			String empId = this.userMasterService.getEmployeeIdFromRequest(request);
			System.out.println("empId::"+empId);
			String companyCode = session.getServletContext().getAttribute(COMPANY_CODE).toString();
			bean=new IAABean();
			bean.setStkAdjHeaderId(Long.valueOf(stkAdjHeaderId));
			bean.setAdjDtlStatus(adjDtlStatus);
			bean.setAdjDtlIds(adjDtlIds);
			bean.setEmpId(empId);
			bean.setIpAddress(request.getRemoteAddr());
			bean.setCompanyCode(companyCode);
			this.iAAService.iaaFinalApproval(bean);
			iaaMap.put(DATA_SAVED, true);
			iaaMap.put(RESPONSE_MESSAGE, "IAA approved Successfully.");
		} catch (Exception e) {
			iaaMap.put(DATA_SAVED, false);
			iaaMap.put(RESPONSE_MESSAGE, "Error Occurred !! Error Code : "+new CodifyErrors().getErrorCode(e));
			System.out.println("Error Occurred :" +e.getMessage());// uncomment asneeded --;
		} finally {
			
		}
		return iaaMap;
	}
	
	@PostMapping("/iaa-appr-file-upload")
	public Map<String, Object> iaaUpload(@RequestParam MultipartFile file,@RequestParam String headerId,HttpServletRequest request, HttpSession session) {
		Map<String, Object> map = new HashMap<>();
		try {
			List<MultipartFile> files=new ArrayList<>();
			files.add(file);
			this.iAAService.iaaApprUpload(files, headerId);
			map.put(DATA_SAVED, true);
			map.put(RESPONSE_MESSAGE, "Files Uploaded Successfully.");
		} catch (Exception e) {
			map.put(DATA_SAVED, false);
			map.put(RESPONSE_MESSAGE, "Error Occurred !! Error Code : "+new CodifyErrors().getErrorCode(e));
			System.out.println("Error Occurred :" +e.getMessage());// uncomment asneeded --;
		} finally {
			
		}
		return map;
	}
	
	
	@GetMapping("/iaa-test-mail")
	public Map<String, Object> iaatestMail( HttpSession session, 
			HttpServletRequest request) {
		List<IaaDetailForMail> list = new ArrayList<IaaDetailForMail>();
		try {
			list = iAAService.getIaaDetailForMail(13l, 12l, 12l);
			System.out.println("list"+list.size());
			SendMail sm = new SendMail();
			String mailContent= sm.SendIAACreationMail(list,"");
			
			List<String> mailList = new ArrayList<String>();
		//	mailList.add("pawaskar@excelsof.com");
		//	mailList.add("samruddha@excelsof.com");
			
			EmailFrom mailconfig = emailService.getEmailObject("EMAILFROM");
			ExecutorService executorService = Executors.newCachedThreadPool();
			executorService.execute(() -> {
				
				try {
					SendMail.sendHtmlMail(mailList, "IAA Created", mailContent,
						//	user.getLd_email(), PasswordManager.decrypt(PasswordManager.decrypt(user.getLd_pass_ang())),
							mailconfig.getEmail(),mailconfig.getMail_password(),mailconfig.isAuth(),
							mailconfig.isTls_sls(),mailconfig.getHost(),mailconfig.getPort());
				} catch (Exception e) {
					System.out.println("Error Occurred :" +e.getMessage());// uncomment asneeded --;
				}
			});
		}
		catch (Exception e) {
			// TODO: handle exception
		//	e.printStackTrace();
		}
		return null;
	}
}
